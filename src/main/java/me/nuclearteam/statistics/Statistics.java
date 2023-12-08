package me.nuclearteam.statistics;

import me.nuclearteam.statistics.cogs.Utils;
import me.nuclearteam.statistics.cogs.extension.ExtensionLoader;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public final class Statistics extends JavaPlugin {

    public FileConfiguration config;
    private final Gson gson = new Gson();

    @Override
    public void onEnable() {


        saveDefaultConfig();
        ExtensionLoader extensionLoader = new ExtensionLoader(this);

        config = getConfig();
        extensionLoader.loadExtensions(getLogger(), this.getName());


        getServer().getScheduler().scheduleSyncRepeatingTask(this, this::updateStatisticsTask, 0L, 1200L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // Method to be executed every minute
    private void updateStatisticsTask() {
        try {
            Map<String, Object> statistics = new HashMap<>();

            statistics.put("Memory", Utils.getMemoryUsage());
            statistics.put("CPU", Utils.getCPUUsage());
            statistics.put("SystemInfo", Utils.getSystemInformation());
            statistics.put("JavaRuntime", Utils.getJavaRuntimeInformation());
            statistics.put("PlayerStatistics", Utils.getPlayerStatistics());
            statistics.put("ServerUpTime", Utils.getServerUptime());
            statistics.put("WorldInfo", Utils.getWorldInformation());
            statistics.put("Tps", Utils.getTPS());
            statistics.put("DiskSpace", Utils.getDiskSpace());



            String json = gson.toJson(statistics);
            byte[] jsonDataBytes = json.getBytes("UTF-8");

            // Send the JSON data over the socket
            startSocket(jsonDataBytes);
        } catch (IOException e) {
            getLogger().severe("Error updating statistics: " + e.getMessage());
        }
    }

    // Method to send JSON data over the socket
    private void startSocket(byte[] jsonDataBytes) throws IOException {
        try (Socket socket = new Socket(config.getString("Server.ip"), config.getInt("Server.port"));
             OutputStream os = socket.getOutputStream()) {

            // Send the bytes
            os.write(jsonDataBytes);
        }
    }
}
