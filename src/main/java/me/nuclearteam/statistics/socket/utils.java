package me.nuclearteam.statistics.socket;

import com.google.gson.Gson;
import me.nuclearteam.statistics.Statistics;
import me.nuclearteam.statistics.cogs.Utils;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class utils extends Statistics{
    private final Gson gson = new Gson();
    private final PlSocket plSocket = new PlSocket(getConfig());


    public void updateStatisticsTask() {
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
            plSocket.send(jsonDataBytes);
        } catch (IOException e) {
            getLogger().severe("Error updating statistics: " + e.getMessage());
        }
    }
}

