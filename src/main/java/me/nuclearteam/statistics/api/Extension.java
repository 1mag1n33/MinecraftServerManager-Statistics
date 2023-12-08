package me.nuclearteam.statistics.api;

import java.util.logging.Logger;

public abstract class Extension {

    protected String extensionName;
    protected String author;
    protected Logger logger;

    public abstract void onEnable();

    public abstract void onDisable();

    public String getExtensionName() {
        return extensionName;
    }

    public String getAuthor() {
        return author;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Logger getLogger() {
        return logger;
    }

    protected void log(String message) {
        if (logger != null) {
            logger.info(String.format("[%s] [%s] %s", logger.getName(), extensionName, message));
        }
    }
}
