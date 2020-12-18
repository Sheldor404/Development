package eventmain;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
public static boolean isallowed;
private static Main plugin;
ArrayList<Player> players;
String bypass = "";
	public void onEnable() {
		Config file = new Config();
        file.setconfig();		
        
        plugin = this;
		PluginManager plmanager = Bukkit.getPluginManager();
		plmanager.registerEvents( this, this);
	}

	
public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
	Player player = (Player) sender;
	if (command.getName().equalsIgnoreCase("boatevent")) {
		if(args[0].equals("tptostart")) {
			players.addAll(Bukkit.getOnlinePlayers());
			Location startloc = new Location(player.getWorld(), 5, 70, 5);
			
			for (int i = 0; i < players.size(); i++) {
				if(!players.get(i).hasPermission(bypass)) {
					players.get(i).teleport(startloc);
					
				}
			}
			if(isallowed) isallowed = false;
			else isallowed = true;		
		}else if(args[0].equals("tpselected")) {
			
		}


	}
	return false;
	}



@EventHandler
public static void boatmoveevent(VehicleMoveEvent event) {
	
//	if (event.getVehicle().getEntityId()) {
		Player player =(Player) event.getVehicle().getPassenger();
		player.sendMessage(		event.getVehicle().getType().equals("BOAT") + " ");
	
	//}
}

public static Main getPlugin() {
	return plugin;
}

}