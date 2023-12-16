package me.nuclearteam.statistics.api;

import me.nuclearteam.statistics.Statistics;
import me.nuclearteam.statistics.cogs.Logger.ExtLogger;
import me.nuclearteam.statistics.cogs.extension.ExtensionInfoManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public abstract class Extension {
    private final Logger logger;  // Logger is initialized in the constructor
    private final ExtensionInfoManager extensionConfig = new ExtensionInfoManager();

    private final Statistics plugin;

    public Extension() {
        this.plugin = Statistics.getInstance();
        this.logger = new ExtLogger(extensionConfig);
        loadInfo();
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public void loadInfo() {
        InputStream resourceStream = getClass().getResourceAsStream("/ext-info.yml");

        if (resourceStream != null) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(resourceStream));
            extensionConfig.setName(config.getString("name"));
            extensionConfig.setAuthor(config.getString("author"));
            extensionConfig.setDescription(config.getString("description"));
            extensionConfig.setVersion(config.getString("version"));
        } else {
            plugin.getLogger().warning("ext-info.yml not found in the JAR file.");
        }
    }

    public Logger getLogger() {
        return this.logger;
    }
}
