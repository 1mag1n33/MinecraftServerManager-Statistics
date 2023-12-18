package me.nuclearteam.statistics;

import me.nuclearteam.statistics.cogs.Utils;
import me.nuclearteam.statistics.cogs.extension.ExtensionInfoManager;
import me.nuclearteam.statistics.cogs.extension.ExtensionLoader;
import me.nuclearteam.statistics.socket.PlSocket;
import me.nuclearteam.statistics.socket.utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;

import java.io.IOException;

public class Statistics extends JavaPlugin {

    public FileConfiguration config;
    private final Gson gson = new Gson();
    private static Statistics instance;

    private static PlSocket plSocket ;

    private static utils Utils;
    private static final ExtensionInfoManager info = new ExtensionInfoManager();

    @Override
    public void onEnable() {

        instance = this;
        saveDefaultConfig();
        plSocket = new PlSocket(getConfig(), this);
        config = getConfig();




        ExtensionLoader extensionLoader = new ExtensionLoader(this, info);
        Utils = new utils(plSocket);

        extensionLoader.loadExtensions(getLogger(), this.getName());
        try {
            String json = gson.toJson("Loaded");
            byte[] data = json.getBytes("UTF-8");
            plSocket.send(data);
            plSocket.receive();
            getLogger().info("Attempting to connect to Python server at " + config.getString("proxy-server.ip") + ":" + config.getInt("proxy-server.port"));
        } catch (IOException e) {
            getLogger().severe("Error receiving data: " + e.getMessage());
            e.printStackTrace();
        }

        getServer().getScheduler().scheduleSyncRepeatingTask(this, Utils::updateStatisticsTask, 0L, 1200L);
    }

    @Override
    public void onDisable() {
    }

    public static Statistics getInstance() {
        return instance;
    }


}
