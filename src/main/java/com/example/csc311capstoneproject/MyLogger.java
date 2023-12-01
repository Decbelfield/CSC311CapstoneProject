package com.example.csc311capstoneproject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {

    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    public static void makeLog(String msg)
    {
        LOGGER.log(Level.INFO, "Csc311_TaxHome"+msg);

    }
}