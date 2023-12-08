package me.nuclearteam.statistics.cogs;

import me.nuclearteam.statistics.cogs.PlayerDataUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.lang.management.*;
import java.util.List;

public class Utils {
    public static String getMemoryUsage() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();

        long usedMemory = heapMemoryUsage.getUsed() / (1024 * 1024); // Convert to MB
        long maxMemory = heapMemoryUsage.getMax() / (1024 * 1024); // Convert to MB

        return "Used Memory: " + usedMemory + " MB, Max Memory: " + maxMemory + " MB";
    }

    public static String getCPUUsage() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

        // Some platforms may not support this method
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            double cpuUsage = ((com.sun.management.OperatingSystemMXBean) osBean).getSystemCpuLoad();

            if (cpuUsage >= 0) {
                // CPU usage is a value between 0 and 1
                int cpuUsagePercentage = (int) (cpuUsage * 100);
                return "CPU Usage: " + cpuUsagePercentage + "%";
            } else {
                return "CPU Usage: Not available on this platform";
            }
        } else {
            return "CPU Usage: Not supported on this platform";
        }
    }

    public static String getSystemInformation() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        return "OS Name: " + osBean.getName() +
                "\nOS Version: " + osBean.getVersion() +
                "\nArchitecture: " + osBean.getArch();
    }

    public static String getPlayerStatistics() {
        List<PlayerDataUtil.PlayerData> playerDataList = PlayerDataUtil.getPlayerData();

        StringBuilder playerInfo = new StringBuilder("Online Players:\n");
        for (PlayerDataUtil.PlayerData playerData : playerDataList) {
            playerInfo.append(playerData.getName()).append(" (UUID: ").append(playerData.getUuid()).append(")\n");
        }

        return playerInfo.toString();
    }


    public static String getServerUptime() {
        long uptimeMillis = ManagementFactory.getRuntimeMXBean().getUptime();
        long uptimeSeconds = uptimeMillis / 1000;
        long hours = uptimeSeconds / 3600;
        long minutes = (uptimeSeconds % 3600) / 60;
        long seconds = uptimeSeconds % 60;
        return "Uptime: " + hours + "h " + minutes + "m " + seconds + "s";
    }

    public static String getWorldInformation() {
        World world = Bukkit.getWorlds().get(0); // Assuming only one world
        int loadedChunks = world.getLoadedChunks().length;
        int loadedEntities = world.getEntities().size();
        return "Loaded Chunks: " + loadedChunks +
                "\nLoaded Entities: " + loadedEntities;
    }

    public static String getTPS() {
        double tps = Bukkit.getServer().getTPS()[0];
        return "TPS: " + tps;
    }

    public static String getDiskSpace() {
        File file = new File(".");
        long freeSpace = file.getFreeSpace() / (1024 * 1024); // in MB
        long totalSpace = file.getTotalSpace() / (1024 * 1024); // in MB
        return "Free Disk Space: " + freeSpace + " MB" +
                "\nTotal Disk Space: " + totalSpace + " MB";
    }

    public static String getJavaRuntimeInformation() {
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        return "Java Version: " + System.getProperty("java.version") +
                "\nJava Vendor: " + System.getProperty("java.vendor") +
                "\nJava Runtime Name: " + runtimeBean.getVmName();
    }
}
