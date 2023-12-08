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

    public ExtensionLoader(JavaPlugin plugin) {
        this.plugin = plugin;
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
                        Extension extension = loadExtension(extensionFile, paperLogger);
                        if (extension != null) {
                            extensions.add(extension);

                            extension.onEnable();
                            plugin.getLogger().info(String.format("Registered: %s, Author: %s", extension.getExtensionName(), extension.getAuthor()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private static Extension loadExtension(File jarFile, Logger paperLogger) throws Exception {
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
                            loadedExtension.setLogger(paperLogger);
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
