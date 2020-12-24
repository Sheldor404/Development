package eventmain;

import java.util.ArrayList;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class inventory implements Listener  {
public static int id = 15;
public static ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);  
	
	public static void openGUI(Player p ,String Gui1name) {	
			Inventory inventory = Bukkit.createInventory(null, 9*1, Gui1name);

			inventory.setItem(0, items("Start game with randoms",Material.STAINED_GLASS_PANE,5,""));
			inventory.setItem(1, items("Stop game",Material.STAINED_GLASS_PANE,14,""));
			inventory.setItem(2, items("Start game with selected",Material.STAINED_GLASS_PANE,13,""));
			inventory.setItem(3, items("Open selectgui",Material.STAINED_GLASS_PANE,9,""));
			inventory.setItem(4, items("Reloade game",Material.STAINED_GLASS_PANE,1,""));


			p.openInventory(inventory);
	}
	
	
	public static ItemStack items(String itemname, Material material, int sid, String lore) {
		ItemStack item = new ItemStack(material, 1, (byte) sid);
		 ArrayList<String> lorelist = new ArrayList<String>();	 lorelist.add(lore);
		ItemMeta meta = item.getItemMeta(); meta.setDisplayName(itemname);meta.setLore(lorelist); item.setItemMeta(meta);
		return item;
	}
	
	
	
	 public static void openplayerselectgui(Player player){
	        Inventory players = Bukkit.createInventory(null,6*9,"§bAlle Spieler");

	        for (Player p : Bukkit.getServer().getOnlinePlayers()){
	            //Glasscheibe in Grün
	            if (onClickGuiInteract.playerArrayList.contains(p)){
	                ItemStack glasspane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
	                ItemMeta panemeta = glasspane.getItemMeta();

	                panemeta.setDisplayName("§b"+p.getDisplayName());

	                ArrayList<String> loreKopf = new ArrayList<>();
	                loreKopf.add("§d➥ Herzen: "+p.getHealth()/2);
	                loreKopf.add("§d➥ Hungerbalken: "+p.getFoodLevel()/2);
	                loreKopf.add("§d➥ Gamemode: "+p.getGameMode());
	                loreKopf.add("§d➥ UUID: "+p.getUniqueId());
	                loreKopf.add("§d➥ IP: "+p.getAddress());
	                panemeta.setLore(loreKopf);

	                panemeta.addEnchant(Enchantment.BINDING_CURSE,2,true);
	                panemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);


	                glasspane.setItemMeta(panemeta);
	                players.addItem(glasspane);
	            }else{
	                //Glasscheibe mit Loren Rot
	                ItemStack glasspane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
	                ItemMeta panemeta = glasspane.getItemMeta();

	                panemeta.setDisplayName("§b"+p.getDisplayName());

	                ArrayList<String> loreKopf = new ArrayList<>();
	                loreKopf.add("§d➥ Herzen: "+p.getHealth()/2);
	                loreKopf.add("§d➥ Hungerbalken: "+p.getFoodLevel()/2);
	                loreKopf.add("§d➥ Gamemode: "+p.getGameMode());
	                loreKopf.add("§d➥ UUID: "+p.getUniqueId());
	                loreKopf.add("§d➥ IP: "+p.getAddress());
	                panemeta.setLore(loreKopf);

	                panemeta.addEnchant(Enchantment.BINDING_CURSE,1,true);
	                panemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);


	                glasspane.setItemMeta(panemeta);
	                players.addItem(glasspane);
	            }


	        }


	        ItemStack observer = new ItemStack(Material.OBSERVER,1);
	        ItemMeta observermeta = observer.getItemMeta();
	        observermeta.setDisplayName("§bOffline-Clear");
	        ArrayList<String> observerLore = new ArrayList<>();
	        observerLore.add("§d➥ Klicke um alle Offline Spieler zu entfernen");
	        observermeta.setLore(observerLore);
	        observermeta.addEnchant(Enchantment.BINDING_CURSE,4,true);
	        observermeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	        observer.setItemMeta(observermeta);



	        players.setItem(52,observer);
	        players.setItem(53, items("Zurrück", Material.BARRIER, 0, ""));
	        player.openInventory(players);	   
	 }
	
}
	

