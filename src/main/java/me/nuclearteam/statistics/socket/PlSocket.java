package me.nuclearteam.statistics.socket;

import com.google.gson.Gson;
import me.nuclearteam.statistics.Statistics;
import me.nuclearteam.statistics.cogs.Utils;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class PlSocket {

    private final FileConfiguration config;

    public PlSocket(FileConfiguration config) {
        this.config = config;
    }



    // Method to send JSON data over the socket
    public void send(byte[] jsonDataBytes) throws IOException {
        try (Socket socket = new Socket(config.getString("Server.ip"), config.getInt("Server.port"));
             OutputStream os = socket.getOutputStream()) {

            // Send the bytes
            os.write(jsonDataBytes);
        }
    }

    public void recive() throws IOException {

    }
}
