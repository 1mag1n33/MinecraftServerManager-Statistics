package me.nuclearteam.statistics.socket;

import com.google.gson.Gson;
import me.nuclearteam.statistics.Statistics;
import me.nuclearteam.statistics.cogs.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PlSocket {

    private final FileConfiguration config;
    private final JavaPlugin plugin;

    public PlSocket(FileConfiguration config, JavaPlugin plugin) {
        this.config = config;
        this.plugin = plugin;
    }

    public void saveConfig() {
        plugin.saveConfig();
    }

    // Method to send JSON data over the socket
    public void send(byte[] jsonDataBytes) throws IOException {
        try (Socket socket = new Socket(config.getString("proxy-server.ip"), config.getInt("proxy-server.port"));
             OutputStream os = socket.getOutputStream()) {

            // Send the bytes
            os.write(jsonDataBytes);
        }
    }

    public void receive() throws IOException {
        try (Socket socket = new Socket(config.getString("proxy-server.ip"), config.getInt("proxy-server.port"));
             InputStream is = socket.getInputStream()) {

            // Receive server ID from the Python server
            byte[] idBytes = new byte[36];  // Assuming server ID is a UUID
            is.read(idBytes);
            String serverId = new String(idBytes, "UTF-8").trim();

            // Remove double quotes and single quotes
            serverId = serverId.replace("\"", "").replace("'", "");

            config.set("proxy-server.id", serverId);
            saveConfig();

            // Use the received server ID as needed
            System.out.println("Received server ID: " + serverId);

            // Add your logic to receive additional data if required
        }
    }

}
