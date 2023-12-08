package me.nuclearteam.statistics.cogs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerDataUtil {
    public static List<PlayerData> getPlayerData() {
        List<PlayerData> playerDataList = new ArrayList<>();

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            UUID uuid = player.getUniqueId();
            String name = player.getName();
            playerDataList.add(new PlayerData(uuid, name));
        }

        return playerDataList;
    }

    public static class PlayerData {
        private final UUID uuid;
        private final String name;

        public PlayerData(UUID uuid, String name) {
            this.uuid = uuid;
            this.name = name;
        }

        public UUID getUuid() {
            return uuid;
        }

        public String getName() {
            return name;
        }
    }
}
