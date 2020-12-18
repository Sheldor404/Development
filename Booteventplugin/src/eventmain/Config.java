package eventmain;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;



public class Config { 
	public static  FileConfiguration cfg = getFileConfiguration();

public void setconfig(){

    cfg.options().copyDefaults(true);
    cfg.addDefault("startx",0);
    cfg.addDefault("starty",0);
    cfg.addDefault("startz",0);
    cfg.addDefault("endx",0);
    cfg.addDefault("endy",0);
    cfg.addDefault("endz",0);
    cfg.addDefault("targetx",0);
    cfg.addDefault("targety",0);
    cfg.addDefault("targetz",0);


    try {
        cfg.save(getFile());
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private static File getFile(){
    return new File("plugins/Elytraeventplugin","config.yml");
}

private static FileConfiguration getFileConfiguration(){
    return YamlConfiguration.loadConfiguration(getFile());
}

}