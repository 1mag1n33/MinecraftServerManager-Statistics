package me.nuclearteam.statistics.cogs.extension;

import me.nuclearteam.statistics.api.Extension;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.lang.reflect.Modifier;
import java.util.logging.Logger;

public class ExtensionLoader {

    private static JavaPlugin plugin;

    private static ExtensionInfoManager config;

    public ExtensionLoader(JavaPlugin plugin, ExtensionInfoManager config) {
        ExtensionLoader.plugin = plugin;
        ExtensionLoader.config = config;
    }

    public void loadExtensions(Logger paperLogger, String pluginName) {
        File extensionsFolder = new File(String.format("%s/extensions", plugin.getDataFolder()));
        if (!extensionsFolder.exists()) {
            extensionsFolder.mkdirs();
        }
        List<Extension> extensions = new ArrayList<>();

        if (extensionsFolder.exists() && extensionsFolder.isDirectory()) {
            File[] extensionFiles = extensionsFolder.listFiles((dir, name) -> name.endsWith(".jar"));

            if (extensionFiles != null) {
                for (File extensionFile : extensionFiles) {
                    try {
                        Extension extension = loadExtension(extensionFile, config);
                        if (extension != null) {
                            extensions.add(extension);
                            if (config != null) {
                                config.setName(config.getName());
                                config.setAuthor(config.getAuthor());
                                config.setDescription(config.getDescription());
                                config.setVersion(config.getVersion());

                                plugin.getLogger().info(String.format("Registered: %s, Author: %s", config.getName(), config.getAuthor()));
                            } else {
                                plugin.getLogger().warning("ExtensionInfoManager is null.");
                            }
                            extension.onEnable();

                        }
                    } catch (Exception e) {
                        plugin.getLogger().severe("Error loading extension from file: " + extensionFile.getName() + ", " + e.getMessage());
                    }
                }
            }
        }

    }

    private static Extension loadExtension(File jarFile, ExtensionInfoManager config) throws Exception {
        URLClassLoader classLoader = new URLClassLoader(new URL[]{jarFile.toURI().toURL()}, Extension.class.getClassLoader());
        JarFile jar = new JarFile(jarFile);

        Extension loadedExtension = null;

        // Iterate through classes in the JAR file
        for (JarEntry entry : Collections.list(jar.entries())) {
            if (entry.getName().endsWith(".class")) {
                String className = entry.getName().replace("/", ".").replace(".class", "");

                try {
                    Class<?> loadedClass = classLoader.loadClass(className);

                    // Check if the loaded class extends Extension
                    if (Extension.class.isAssignableFrom(loadedClass) && !Modifier.isInterface(loadedClass.getModifiers()) && !Modifier.isAbstract(loadedClass.getModifiers())) {
                        try {
                            loadedExtension = (Extension) loadedClass.getDeclaredConstructor().newInstance();
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return loadedExtension;
    }

}
