package me.jamiemac262.ServerAIReWrite;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Utils {

	//hold random functions here to save space in larger classes
	//are you here?
	
	public static int PlayersOnline(){
		Player[] players = Bukkit.getOnlinePlayers();
		int Online = players.length;
		return Online;
		}
	
	public static int AllPlayers(){
		OfflinePlayer[] allPlayers = Bukkit.getOfflinePlayers();
		int players = allPlayers.length;
		return players;
	}
	

	
}
