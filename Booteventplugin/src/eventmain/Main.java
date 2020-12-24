package eventmain;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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


public class Main extends JavaPlugin implements Listener{
private static Main plugin;
public static ArrayList<Player> allplayers = new ArrayList<Player>();
public static ArrayList<UUID> haveplayed = new ArrayList<UUID>();
public static ArrayList<Player> willplay = new ArrayList<Player>();
public static ArrayList<Player> winners = new ArrayList<Player>();
public static ArrayList<Long> times = new ArrayList<Long>();
public static ArrayList<Player> currentlyplaying = new ArrayList<Player>();
static long starttime;
int eventplayers =5 ;
static String bypass = "";
public static Location endloc;
static int playersinround = Config.cfg.getInt("spieleranzahl"); 

public void onEnable() {
		Config file = new Config();
        file.setconfig();		
        for(Entity e : Bukkit.getServer().getWorlds().get(0).getEntities()) {
			  if(e instanceof Boat) e.remove();
			}  
        plugin = this;
		PluginManager plmanager = Bukkit.getPluginManager();
		plmanager.registerEvents( this, this);
		plmanager.registerEvents(new onClickGuiInteract(),this);
		plmanager.registerEvents(new inventory(),this);

}

public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
	final Player player = (Player) sender;
	 endloc = new Location(player.getWorld(), Config.cfg.getInt("endx"), Config.cfg.getInt("endy"), Config.cfg.getInt("endz"));
	Location startloc = new Location(player.getWorld(), Config.cfg.getInt("startx"), Config.cfg.getInt("starty"), Config.cfg.getInt("startz"));

	if (command.getName().equalsIgnoreCase("boatevent")) {
		if(args.length == 0)inventory.openGUI(player, "Boatevent");
		if(args.length == 1) {
		if(args[0].equals("tptostart")) {
			
			allplayers.addAll(Bukkit.getOnlinePlayers());
			for (int i = 0; i < allplayers.size(); i++) {
				if(allplayers.get(i).hasPermission(bypass)) {
					allplayers.get(i).teleport(startloc);
				}
			}
			
		}else if(args[0].equalsIgnoreCase("tpselected")) {
			starttime = System.currentTimeMillis();
			 for(Entity e : Bukkit.getServer().getWorlds().get(0).getEntities()) {
				  if(e instanceof Boat) e.remove();
				}  
			 	int willplaysize = willplay.size();
				for (int i = 0; i < playersinround && i < willplaysize; i++) {
					if(willplay.size() > 0) {
						int randomNum = ThreadLocalRandom.current().nextInt(0, willplay.size() );
						Location loc = new Location(player.getWorld(),Config.cfg.getInt("gamestartx")-2*i,Config.cfg.getInt("gamestarty"),Config.cfg.getInt("gamestartz") );
						loc.setYaw(180);
						Boat boat = player.getWorld().spawn(loc, Boat.class);
						boat.setCustomName("boat"+i);
						boat.setPassenger(willplay.get(randomNum));
						haveplayed.add(willplay.get(randomNum).getUniqueId());
						currentlyplaying.add(willplay.get(randomNum));
						willplay.remove(randomNum);
					}
				}
				
			}else if(args[0].equalsIgnoreCase("test")) {
				haveplayed.clear();
				willplay.addAll(Bukkit.getOnlinePlayers());
				
			}else if(args[0].equalsIgnoreCase("stop")) {
				bubblesrt(times, winners);
				for (int i = 0; i < winners.size(); i++) {
					Bukkit.broadcastMessage("Platz "+(i+1) +" geht an " + winners.get(i).getName() + " mit " + Math.round(((times.get(i)-starttime)/1000)) + " sekunden"); 	
				}
				for (int i = 0; i < currentlyplaying.size(); i++) {
					if (!winners.contains(currentlyplaying.get(i))) {
						currentlyplaying.get(i).teleport(endloc);
					}
				}
				for(Entity e : Bukkit.getServer().getWorlds().get(0).getEntities()) {
					  if(e instanceof Boat) e.remove();
					} 
				winners.clear();
				times.clear();
				currentlyplaying.clear();
				members.members.clear();
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

		int x = vehicle.getLocation().getBlockX();
		int z = vehicle.getLocation().getBlockZ();
		if ( Config.cfg.getInt("checkx1")  <= x && x <= Config.cfg.getInt("checkx2") && Config.cfg.getInt("checkz1") <= z && z <= Config.cfg.getInt("checkz2")) {
			members.members.put(player, true);
		}
		if(members.members.containsKey(player)) {
		if ( Config.cfg.getInt("targetx1") <= x && x <= Config.cfg.getInt("targetx2") && Config.cfg.getInt("targetz1") <= z && z <= Config.cfg.getInt("targetz2") && members.members.get(player) && !winners.contains(player)) {
			player.teleport(endloc);
			winners.add(player);
			times.add( System.currentTimeMillis());
			vehicle.remove();
		}
		}
		if(winners.size() == currentlyplaying.size()){
			bubblesrt(times, winners);
			for (int i = 0; i < winners.size(); i++) {
				Bukkit.broadcastMessage("Platz "+(i+1) +" geht an " + winners.get(i).getName() + " mit " + Math.round(((times.get(i)-starttime)/1000)) + " sekunden"); 	
			}
			winners.clear();
			times.clear();
			currentlyplaying.clear();
			members.members.clear();

		}	
	}
}

@EventHandler
public void joinevent(PlayerJoinEvent event) {
	Player player = event.getPlayer();
	if(player.hasPermission(bypass)) {
		if (!haveplayed.contains(player.getUniqueId())) {
			willplay.add(player);	
			player.sendMessage(willplay+ "");
		}
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
