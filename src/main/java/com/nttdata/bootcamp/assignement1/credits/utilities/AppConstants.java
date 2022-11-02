package com.nttdata.bootcamp.assignement1.credits.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConstants {

    public static String baseUrl = "https://bootcamp37-credits.azurewebsites.net";
    public static String baseUrlCostumer = "https://bootcamp37-costumers.azurewebsites.net";
    public static String baseUrlCredits = "https://bootcamp37-credits.azurewebsites.net";
    public static String getCurrentUrl() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "application.properties";

        String currentPort = "80";
        StringBuilder url  = new StringBuilder();
        url.append(AppConstants.baseUrl);
        url.append(":");
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
            currentPort = appProps.getProperty("server.port");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            url.append(currentPort);
        }
        return url.toString();
    }
}
