package me.jamiemac262.ServerAIReWrite;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jamiemac262.ServerAIReWrite.ServerAI;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("deprecation")
public class LoginListener implements Listener{

	public static ServerAI plugin;
	public FileConfiguration playerStat = null;
	public File playerStatFile = null;
	public LoginListener(ServerAI instance){
		plugin = instance;
	}
	@EventHandler(priority = EventPriority.HIGHEST) //Event Priority
	public void onPlayerLogin(PlayerLoginEvent event){
		System.out.print("this is proving that the damned event works");
		new SendAIMessage(0.5, "welcome to the server, " + event.getPlayer().getDisplayName() + " I will be your host", "welcome to the server, " + event.getPlayer().getDisplayName() + " I will be your host", "welcome to the server, " + event.getPlayer().getDisplayName() + " I will be your host");
		Player player = event.getPlayer();
		String playerName = player.getDisplayName();
		reloadplayerstat(playerName);
		playerStatFile = new File("plugins" + File.separator + "ServerAI" + File.separator + "Players", playerName + ".yml");
		if(!playerStatFile.exists()){
		}
		
}
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		  if(player.hasPermission("sai.admin") && ServerAI.update)
		  {
			  new SendPrivateAIMessage(player, 0.5,"I have an update available!, please ask me to update","My AI has been improved, If you ask, i can update?","There is an update available for my Systems. You need to ask me to download and install it?");
		    // Will look like - An update is available: AntiCheat v1.3.6 (93738 bytes)
		    player.sendMessage("Type a sentence with the words 'sai' and 'update' if you would like to update.");
		  }

	}
	

	public void reloadplayerstat(String PlayerName) {
	    if (playerStatFile == null) {
	    playerStatFile = new File("plugins" + File.separator + "ServerAI" + File.separator + "Players", PlayerName + ".yml");
	    }
	    if(!playerStatFile.exists()){
	    	try {
				playerStatFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	System.out.print("Creating file for new player");
	    	Player p = null;
	    	@SuppressWarnings("null")
			Location spawn = p.getWorld().getSpawnLocation();
	    	p.teleport(spawn);
	    }
	    playerStat = YamlConfiguration.loadConfiguration(playerStatFile);
	    if(!playerStat.contains("notNeededYet")){
	    	playerStat.set("notNeededYet", true);
		}
		if(!playerStat.contains("NotNeededYet")){
			playerStat.set("NotNeededYet", true);
		}
		saveplayerStat();
	    }
	
	public FileConfiguration getplayerStat() {
	    if (playerStat == null) {
	        reloadplayerstat(null);
	    }
	    return playerStat;
	}
	
	public void saveplayerStat(){
	    if (playerStat == null || playerStat == null) {
	    return;
	    }
	    try {
	        playerStat.save(playerStatFile);
	    } catch (IOException ex) {
	        Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save config to " + playerStatFile, ex);
	    }
	}
}
