package mysqltest;

import java.io.File;
import java.io.IOException;
import java.sql.*;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class MySQL {
	public static String host;
    public static String port;
    public static String database;
    public static String username;
    public static String password;
    public static Connection con;

    public static void connect(){
        FileConfiguration config = getFileConfiguration();

        MySQL.host = config.getString("host");
        MySQL.port = config.getString("port");
        MySQL.database = config.getString("database");
        MySQL.username = config.getString("username");
        MySQL.password = config.getString("password");
        setStandard();
        if (!isConnected()){
            try {
                con = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database,username,password);
                System.out.println("[MySQL] Verbindung aufgebaut!");
            } catch (SQLException throwables) {
                throwables.printStackTrace();

            }
        }
    }
    

    public static void disconnect(){
        if (isConnected()){
            try {
                con.close();
                System.out.println("[MySQL] Verbindung getrennt!");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static boolean isConnected(){
        return (con==null ? false : true);
    }

    public static Connection getCon() {
        return con;
    }
    
    
    private static FileConfiguration getFileConfiguration(){
        return YamlConfiguration.loadConfiguration(getFile());
    }
    
    private static File getFile(){
        return new File("plugins/sqltest","mysql.yml");
    }
    
    public static void setStandard(){
        FileConfiguration config = getFileConfiguration();

        config.options().copyDefaults(true);
        config.addDefault("host","");
        config.addDefault("port","");
        config.addDefault("database","");
        config.addDefault("username","");
        config.addDefault("password","");


        try {
            config.save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}