package com.heshanthenura.libro.services.database;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    private static String HOST;
    private static String PORT;
    private static String DBNAME;
    private static String USERNAME;
    private static String PASSWORD;
    private static String URL;

    public static Connection getConnection() throws SQLException {
        if (URL == null || URL.isEmpty()) {
            URL = "jdbc:mysql://" + HOST.trim() + ":" + PORT.trim() + "/" + DBNAME.trim();
        }
        return DriverManager.getConnection(URL, USERNAME.trim(), PASSWORD.trim());
    }

    public static String testConnection() {
        try (Connection connection = getConnection()) {
            return "Connection was successful"; // Connection was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage(); // Return the error message
        }
    }

    public static void saveConfiguration() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("host", HOST);
        properties.setProperty("port", PORT);
        properties.setProperty("dbname", DBNAME);
        properties.setProperty("username", USERNAME);
        properties.setProperty("password", PASSWORD);

        String os = System.getProperty("os.name").toLowerCase();
        String configFile;
        if (os.contains("win")) {
            configFile = System.getenv("APPDATA") + File.separator + "Libro" + File.separator + "config.properties";
        } else if (os.contains("mac")) {
            configFile = System.getProperty("user.home") + "/Library/Application Support/Libro/config.properties";
        } else {
            configFile = System.getProperty("user.home") + "/.config/Libro/config.properties";
        }

        File file = new File(configFile);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (OutputStream output = new FileOutputStream(file)) {
            properties.store(output, "Database Configuration");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadConfiguration() throws IOException {
        Properties properties = new Properties();
        String os = System.getProperty("os.name").toLowerCase();
        String configFile;

        if (os.contains("win")) {
            configFile = System.getenv("APPDATA") + File.separator + "Libro" + File.separator + "config.properties";
        } else if (os.contains("mac")) {
            configFile = System.getProperty("user.home") + "/Library/Application Support/Libro/config.properties";
        } else {
            configFile = System.getProperty("user.home") + "/.config/Libro/config.properties";
        }

        try (FileInputStream input = new FileInputStream(configFile)) {
            properties.load(input);

            HOST = properties.getProperty("host").trim();
            PORT = properties.getProperty("port").trim();
            DBNAME = properties.getProperty("dbname").trim();
            USERNAME = properties.getProperty("username").trim();
            PASSWORD = properties.getProperty("password").trim();

            // Debugging statements
            System.out.println("Loaded configuration:");
            System.out.println("HOST: " + HOST);
            System.out.println("PORT: " + PORT);
            System.out.println("DBNAME: " + DBNAME);
            System.out.println("USERNAME: " + USERNAME);
            // Do not print password for security reasons

        } catch (IOException e) {
            throw new IOException("Error loading configuration: " + e.getMessage());
        }
    }

    // Getter and Setter methods
    public static String getHOST() {
        return HOST;
    }

    public static void setHOST(String HOST) {
        Database.HOST = HOST;
    }

    public static String getPORT() {
        return PORT;
    }

    public static void setPORT(String PORT) {
        Database.PORT = PORT;
    }

    public static String getDBNAME() {
        return DBNAME;
    }

    public static void setDBNAME(String DBNAME) {
        Database.DBNAME = DBNAME;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static void setUSERNAME(String USERNAME) {
        Database.USERNAME = USERNAME;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static void setPASSWORD(String PASSWORD) {
        Database.PASSWORD = PASSWORD;
    }

    public static String getURL() {
        return URL;
    }

    public static void setURL(String URL) {
        Database.URL = URL;
    }
}
