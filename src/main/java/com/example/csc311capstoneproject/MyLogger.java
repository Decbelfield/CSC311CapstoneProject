package com.example.csc311capstoneproject;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Utility class for logging messages throughout the application.
 * It encapsulates the Java Logging API for easy logging of messages at different levels.
 */
public class MyLogger {

    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Logs a message at the INFO level.
     * This method is a utility function to simplify logging throughout the application.
     *
     * @param msg The message string to be logged.
     */
    public static void makeLog(String msg)
    {
        LOGGER.log(Level.INFO, "Csc311_TaxHome"+msg);

    }

}