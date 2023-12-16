package me.nuclearteam.statistics.cogs.Logger;

import me.nuclearteam.statistics.cogs.extension.ExtensionInfoManager;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ExtFormatter extends Formatter {

    private static final ExtensionInfoManager config = new ExtensionInfoManager();
    @Override
    public String format(LogRecord record) {

        StringBuilder logMessage = new StringBuilder();
        logMessage.append("[Statistics] [").append(config.getName()).append("] ");
        logMessage.append(formatMessage(record)).append("\n");

        return logMessage.toString();
    }
}
