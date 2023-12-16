package me.nuclearteam.statistics.cogs.Logger;

import me.nuclearteam.statistics.cogs.extension.ExtensionInfoManager;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class ExtLogger extends Logger {

    private final ExtensionInfoManager config;

    public ExtLogger(ExtensionInfoManager config) {
        super("ExtLogger", null);
        this.config = config;

        // Set the custom formatter
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new ExtFormatter());

        // Add the console handler to the logger
        addHandler(consoleHandler);

        setLoggerPrefix();
    }

    private void setLoggerPrefix() {
        if (config != null) {
            // Log the initialization message with the extension name
            info("Logger initialized");
        }
    }
}
