package eventmain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.naming.ldap.StartTlsRequest;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftBoat;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_12_R1.WorldGenEndCity.Start;

public class Main extends JavaPlugin implements Listener{
private static Main plugin;
public static ArrayList<Player> allplayers = new ArrayList<Player>();
public static ArrayList<Player> haveplayed = new ArrayList<Player>();
public static ArrayList<Player> willplay = new ArrayList<Player>();
public static ArrayList<Player> winners = new ArrayList<Player>();
public static ArrayList<Long> times = new ArrayList<Long>();
static long starttime;
int eventplayers =5 ;
String bypass = "";
	public void onEnable() {
		Config file = new Config();
        file.setconfig();		
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),"kill @e[type=Boat]");
        plugin = this;
		PluginManager plmanager = Bukkit.getPluginManager();
		plmanager.registerEvents( this, this);
	}

	
public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
	final Player player = (Player) sender;
	if (command.getName().equalsIgnoreCase("boatevent")) {
		if(args[0].equals("tptostart")) {
			
			allplayers.addAll(Bukkit.getOnlinePlayers());
			Location startloc = new Location(player.getWorld(), 5, 70, 5);		
			for (int i = 0; i < allplayers.size(); i++) {
				if(allplayers.get(i).hasPermission(bypass)) {
					allplayers.get(i).teleport(startloc);
				}
			}
			
		}else if(args[0].equalsIgnoreCase("tpselected")) {
			starttime = System.currentTimeMillis();
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),"kill @e[type=Boat]");
			
				for (int i = 0; i <= 2 && i <= willplay.size(); i++) {
					if(willplay.size() > 0) {
						int randomNum = ThreadLocalRandom.current().nextInt(0, willplay.size() );
						Location loc = new Location(player.getWorld(),-3-2*i,65,45 );
						loc.setYaw(180);
						Boat boat = player.getWorld().spawn(loc, Boat.class);
						boat.setCustomName("boat"+i);
						boat.setPassenger(willplay.get(randomNum));
						haveplayed.add(willplay.get(randomNum));
						willplay.remove(randomNum);
						player.sendMessage(haveplayed + "  " + willplay + "  "+ haveplayed.contains(player));
					}
				}
			}
		}
	return false;
	}



@EventHandler
public static void boatmoveevent(VehicleMoveEvent event) {
	if (event.getVehicle().getType().equals(EntityType.BOAT)) {
		Player player =(Player) event.getVehicle().getPassenger();
		Vehicle vehicle = event.getVehicle();
		Location endloc = new Location(player.getWorld(), Config.cfg.getInt("endx"), Config.cfg.getInt("endy"), Config.cfg.getInt("endz"));

		int x = vehicle.getLocation().getBlockX();
		int z = vehicle.getLocation().getBlockZ();
		if ( Config.cfg.getInt("checkx1")  <= x && x <= Config.cfg.getInt("checkx2") && Config.cfg.getInt("checkz1") <= z && z <= Config.cfg.getInt("checkz2")) {
			members.members.put(player, true);
		}
		if ( Config.cfg.getInt("targetx1") <= x && x <= Config.cfg.getInt("targetx2") && Config.cfg.getInt("targetz1") <= z && z <= Config.cfg.getInt("targetz2") && members.members.get(player)) {
			player.sendMessage("target");
			winners.add(player);
			times.add( System.currentTimeMillis());
		}
		if(	winners.size() == 2 || winners.size() == willplay.size()){
			bubblesrt(times, winners);
			for (int i = 0; i < winners.size(); i++) {
				winners.get(i).teleport(endloc);
				Bukkit.broadcastMessage(i+" " + winners.get(i) + " " + (times.get(i)-starttime));
			winners.clear();
			times.clear();
			}
		}	
	}
}

@EventHandler
public void joinevent(PlayerJoinEvent event) {
	Player player = event.getPlayer();
	player.sendMessage("" + haveplayed);
	if(player.hasPermission(bypass)) {
		if (!haveplayed.contains(player)) willplay.add(player);			
	}
}

@EventHandler
public void quitevent(PlayerQuitEvent event) {
	Player player = event.getPlayer();
		if(willplay.contains(player)) willplay.remove(player);
}

public static void bubblesrt(ArrayList<Long> list,ArrayList<Player> players){
	for (int j = 0; j < list.size(); j++) {
		
		for (int i = 0; i < list.size()-1; i++) {
			if(list.get(i) > list.get(i + 1)) {
				long bigger = list.get(i);
				long smaler = list.get(i + 1);
				Player first = players.get(i);
				Player second = players.get(i + 1);
				list.set(i,smaler);
				list.set(i + 1,bigger);
				players.set(i + 1,first);
				players.set(i,second);
			}		
		}    		
	}
}


public static Main getPlugin() {
	return plugin;
}

}

/*
* Edit by Kyleonaut
* */