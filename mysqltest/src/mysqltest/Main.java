package mysqltest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.print.DocFlavor.STRING;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin implements Listener{
	ArrayList<STRING> emptystring = new ArrayList<STRING>();
	ArrayList<Boolean> emptyboolean = new ArrayList<Boolean>();

	public void onEnable() {
        MySQL.connect();	
        PluginManager pl = Bukkit.getPluginManager();
        pl.registerEvents(this,this);
        
	}
	
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
		Player player = (Player) sender;
		if (command.getName().equalsIgnoreCase("punkte")) {			
			if (args.length >= 1) {
				Player target = Bukkit.getServer().getPlayer(args[1]);	
			switch (args[0]) {
			case "add":
				if (args.length  == 3) {
				if(selectstring("SELECT * FROM Punkte WHERE Playername='"+args[1]+"'","Playername" ).equals(emptystring)) {
						executecommand("INSERT INTO Punkte (Playername,punkte,newpoints)VALUES('" + args[1]+"'," + args[2]+",true)");
						player.sendMessage("Der Spieler "+args[1] +" hat "+ args[2] +" Punkte");

						}else {
							int punkte = selectint("SELECT * FROM Punkte WHERE Playername ='" +args[1] +"'" , "punkte").get(0)+ Integer.parseInt(args[2]);
								executecommand("UPDATE Punkte SET punkte=" +punkte +" ,newpoints=true WHERE Playername='" +args[1] +"'");
							player.sendMessage("Der Spieler "+args[1] +" hat "+punkte+" Punkte");
							}
						}
				break;
			case "show":
				if(args[1].equalsIgnoreCase("all")) {
					for (int i = 0; i < selectint("SELECT punkte FROM Punkte ", "punkte").size(); i++) {
						player.sendMessage("Der Spieler "+ selectstring("SELECT Playername FROM Punkte ", "Playername").get(i) + " hat " + selectint("SELECT punkte FROM Punkte ", "punkte").get(i) + " Punkte");
					}
					
				}else{
					  int punkte = selectint("SELECT * FROM Punkte WHERE Playername ='" +args[1] +"'" , "punkte").get(0);
					  player.sendMessage("Der Spieler "+args[1] +" hat "+punkte+" Punkte");}
				break;
			case "remove":
				if (args.length == 3) {
				int punktesubstracted = selectint("SELECT * FROM Punkte WHERE Playername ='" +args[1] +"'" , "punkte").get(0)- Integer.parseInt(args[2]);
				executecommand("UPDATE Punkte SET punkte=" + punktesubstracted +" WHERE Playername='" +args[1] +"'");
				player.sendMessage("Der Spieler "+args[1] +" hat "+punktesubstracted+" Punkte");

				}
				break;
			}
		}			
	}
		return false;
		}
	
	public static ArrayList<Integer> selectint(String command, String spalte) {
		try {
			Connection con = MySQL.getCon();
			PreparedStatement select = con.prepareStatement(command);
			
			ResultSet res = select.executeQuery();
			ArrayList<Integer> array = new ArrayList<Integer>();
			while (res.next()) {
				array.add(res.getInt(spalte));
			}
			return array;
			
		}catch (SQLException e) {
			System.out.println(e);	
			}
		return null;
	}
	
	
	public static ArrayList<String> selectstring(String command, String spalte) {
		try {
			Connection con = MySQL.getCon();
			PreparedStatement select = con.prepareStatement(command);
			
			ResultSet res = select.executeQuery();
			ArrayList<String> array = new ArrayList<String>();
			while (res.next()) {
				array.add(res.getString(spalte));
			}
			return array;
			
		}catch (SQLException e) {
			System.out.println(e);	
			}
		return null;
	}
	public static ArrayList<Boolean> selectboolean(String command, String spalte) {
		try {
			Connection con = MySQL.getCon();
			PreparedStatement select = con.prepareStatement(command);
			
			ResultSet res = select.executeQuery();
			ArrayList<Boolean> array = new ArrayList<Boolean>();
			while (res.next()) {
				array.add(res.getBoolean(spalte));
			}
			return array;
			
		}catch (SQLException e) {
			System.out.println(e);	
			}
		return null;
	}
	
	public static void executecommand(String command) {
		try {
			Connection con = MySQL.getCon();
			PreparedStatement cmd = con.prepareStatement(command);
			cmd.executeUpdate();
		}catch (SQLException e) {
			System.out.println(e);	
			}
		}
	

	
	@EventHandler
	public void joinevent(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		java.util.Timer timer = new java.util.Timer();
		timer.schedule(new java.util.TimerTask() {
			
			@Override
			public void run() {
				if(selectboolean("SELECT * FROM Punkte WHERE Playername='"+player.getName() +"'", "newpoints") != emptyboolean) {
					if(selectboolean("SELECT * FROM Punkte WHERE Playername='"+player.getName() +"'", "newpoints").get(0)){
					player.sendMessage("du kriegst: "+ selectint("SELECT * FROM Punkte WHERE Playername='"+player.getName() +"'", "punkte").get(0) + " Punkte");
					executecommand("UPDATE Punkte SET punkte = 0 ,newpoints = false");
						}
					}
				}
			},5000);				
		}
	}
