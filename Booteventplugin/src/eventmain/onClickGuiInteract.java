package eventmain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
public class onClickGuiInteract implements Listener {

    public static ArrayList<Player> playerArrayList = new ArrayList<>();
    public static ArrayList<String> spielerArrayList = new ArrayList<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase("§bAlle Spieler")) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            //Wenn Enchantment.BINDING_CURSE auf 1 ist, ist die Farbe Rot, bei 2 ist sie grün und 3 ist weiter
            int item = 0;
            try {
                item = event.getCurrentItem().getItemMeta().getEnchantLevel(Enchantment.BINDING_CURSE);
            } catch (NullPointerException e) {
                return;
            }
            if (item == 1) {
                Player p = Bukkit.getPlayer(event.getInventory().getItem(event.getSlot()).getItemMeta().getDisplayName().substring(2));
                ItemStack glasspane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
                ItemMeta panemeta = glasspane.getItemMeta();

                panemeta.setDisplayName("§b" + p.getDisplayName());

                ArrayList<String> loreKopf = new ArrayList<>();
                loreKopf.add("§d➥ Herzen: " + p.getHealth() / 2);
                loreKopf.add("§d➥ Hungerbalken: " + p.getFoodLevel() / 2);
                loreKopf.add("§d➥ Gamemode: " + p.getGameMode());
                loreKopf.add("§d➥ UUID: " + p.getUniqueId());
                loreKopf.add("§d➥ IP: " + p.getAddress());
                panemeta.setLore(loreKopf);

                panemeta.addEnchant(Enchantment.BINDING_CURSE, 2, true);
                panemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                glasspane.setItemMeta(panemeta);
                event.getView().setItem(event.getSlot(), glasspane);

                if (playerArrayList.contains(p)) {
                    return;
                } else {
                    playerArrayList.add(p);
                }


            } else {
                if (item == 2) {
                    Player p = Bukkit.getPlayer(event.getInventory().getItem(event.getSlot()).getItemMeta().getDisplayName().substring(2));
                    ItemStack glasspane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
                    ItemMeta panemeta = glasspane.getItemMeta();

                    panemeta.setDisplayName("§b" + p.getDisplayName());

                    ArrayList<String> loreKopf = new ArrayList<>();
                    loreKopf.add("§d➥ Herzen: " + p.getHealth() / 2);
                    loreKopf.add("§d➥ Hungerbalken: " + p.getFoodLevel() / 2);
                    loreKopf.add("§d➥ Gamemode: " + p.getGameMode());
                    loreKopf.add("§d➥ UUID: " + p.getUniqueId());
                    loreKopf.add("§d➥ IP: " + p.getAddress());
                    panemeta.setLore(loreKopf);

                    panemeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
                    panemeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);


                    glasspane.setItemMeta(panemeta);
                    event.getView().setItem(event.getSlot(), glasspane);
                    playerArrayList.remove(p);
                }  
                    //Wenn 3, dann mit den MassAction starten
                    else {
                        if (item == 4) {
                            for (Player p : playerArrayList) {
                                if (!Bukkit.getServer().getOnlinePlayers().contains(p)) {
                                    playerArrayList.remove(p);
                                }
                            }
                        }
                    }if (item == 0) {
                    	inventory.openGUI(player,"Boatevent");
                    }
            }
        }     else if(event.getInventory().getTitle().equalsIgnoreCase("Boatevent")) {
            		event.setCancelled(true);
            		event.setResult(Event.Result.DENY);
            		player.closeInventory();
            		
            		switch (event.getSlot()) {
					case 0:player.chat("/boatevent tpselected");;
					break;
					case 1:player.chat("/boatevent stop");;
					break;
					case 2:            		
					 Main.starttime = System.currentTimeMillis();
          			 for(Entity e : Bukkit.getServer().getWorlds().get(0).getEntities()) {
          				  if(e instanceof Boat) e.remove();
          				}
          			 
          			 	int willplaysize = playerArrayList.size();
          				for (int i = 0; i < willplaysize; i++) {
          					if(willplaysize > 0) {
          						Location loc = new Location(player.getWorld(),Config.cfg.getInt("gamestartx")-2*i,Config.cfg.getInt("gamestarty"),Config.cfg.getInt("gamestartz") );
          						loc.setYaw(180);
          						Boat boat = player.getWorld().spawn(loc, Boat.class);
          						boat.setCustomName("boat"+i);
          						boat.setPassenger(playerArrayList.get(i));
          						if(!Main.haveplayed.contains(playerArrayList.get(i)))Main.haveplayed.add(playerArrayList.get(i).getUniqueId());
          						Main.currentlyplaying.add(playerArrayList.get(i));
          						Main.willplay.remove(playerArrayList.get(i));
          					}
          				}
					break;
					case 3:inventory.openplayerselectgui(player);
					break;
					case 4:					
							Main.haveplayed.clear();
							Main.allplayers.addAll(Bukkit.getOnlinePlayers());
							for (int i = 0; i < Main.allplayers.size(); i++) {
								if (Main.allplayers.get(i).hasPermission(Main.bypass)) {
									Main.willplay.add(Main.allplayers.get(i));
								}		
								members.members.clear();
								Main.times.clear();
								Main.winners.clear();
								Main.currentlyplaying.clear();
						        for(Entity e : Bukkit.getServer().getWorlds().get(0).getEntities()) {
									  if(e instanceof Boat) e.remove();
									}  
							}
		
					break;
					}
            }
    }
}