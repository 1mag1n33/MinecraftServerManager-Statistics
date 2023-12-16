package me.nuclearteam.statistics;

import me.nuclearteam.statistics.cogs.Utils;
import me.nuclearteam.statistics.cogs.extension.ExtensionInfoManager;
import me.nuclearteam.statistics.cogs.extension.ExtensionLoader;
import me.nuclearteam.statistics.socket.PlSocket;
import me.nuclearteam.statistics.socket.utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;

public class Statistics extends JavaPlugin {

    public FileConfiguration config;
    private final Gson gson = new Gson();
    private static Statistics instance;

    private PlSocket plSocket = new PlSocket(getConfig());

    private static utils Utils;
    private static final ExtensionInfoManager info = new ExtensionInfoManager();

    @Override
    public void onEnable() {
        Utils = new utils();
        instance = this;
        saveDefaultConfig();
        ExtensionLoader extensionLoader = new ExtensionLoader(this, info);

        config = getConfig();
        extensionLoader.loadExtensions(getLogger(), this.getName());


        getServer().getScheduler().scheduleSyncRepeatingTask(this, Utils::updateStatisticsTask, 0L, 1200L);
    }

    @Override
    public void onDisable() {
    }

    public static Statistics getInstance() {
        return instance;
    }


}
