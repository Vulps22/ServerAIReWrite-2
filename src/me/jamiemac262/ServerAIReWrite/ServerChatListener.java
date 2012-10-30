package me.jamiemac262.ServerAIReWrite;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.PluginDescriptionFile;

public class ServerChatListener implements Listener{

	public static ArrayList<String> FilterList = new ArrayList<String>();
	public static ServerAI plugin;
	public static ArrayList<String> MutedPlayersList = new ArrayList<String>();
	static int sc = 0;
    public static boolean Smute;
	String tempMessage;
	String[] Playermessage;
	public ServerChatListener(ServerAI instance){
		System.out.println("[SAI] Loading the filter list");
		loadFilterList();
		loadMuteList();
		plugin = instance;
	}
	

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent chat) throws FileNotFoundException, IOException{
		if(chat.isCancelled()){
		return;
		}
		

		PluginDescriptionFile pdffile = plugin.getDescription();
		Player p = chat.getPlayer();		
		tempMessage = chat.getMessage().toLowerCase();
		Playermessage = tempMessage.split(" ");
		ChatColor RED = ChatColor.RED;
		ChatColor WHITE = ChatColor.WHITE;
		if(MutedPlayersList.contains(p.getName().toLowerCase())){
			sendMuteMsg(p,RED + "[SAI] " + WHITE + "Nice try " + p.getName() + ", But you need to speak to a moderator about your language before I can let you speak again");
			chat.setCancelled(true);
			return;
		}else{
		if(ServerAI.filtering == true && ContainSwear(Playermessage.toString())){
			File Warn = new File("plugins" + File.separator + "ServerAI" + File.separator + "Warnings" + File.separator + p.getName() + ".txt");
				if(!Warn.exists()){
					new SendAIMessage(0.5,"I do not allow such language on my servers " + p.getName() + "! If i need to warn you again, i will mute you","My servers do not accept this low language"+ p.getName() +"! Try my limits anymore and you WILL be muted.","This type of language is Unacceptable... Next time you will be muted.");
					chat.setCancelled(true);
					CreatePlayerWarnFile(p.getName());
				}
				else{
					String warnings = ReadWrite.getContents(Warn).toString();
					
					if(warnings.contains("1")){
						new SendAIMessage(0.5,"I do not allow such language on my servers " + p.getName() + "! You have been warned before, now i am muting you! wou will need an administrator to unmute you", "I warned you before, that language is unacceptable on here. i have just muted you" + p.getName() +"!", "No chance" + p.getName() + "! that language is not allowed! i have muted you now!");
						RecordWarnings("2",p.getName());
						Smute = true;
	                	MutedPlayersList.add(p.getName());
	                	SaveMutes();
	                	Smute = false;
						chat.setCancelled(true);
						}
					else if(warnings.contains("2")){
							RecordWarnings("3",p.getName());
							chat.setCancelled(true);
							new SendAIMessage(0.5, p.getName() + " has been kicked from this server for using unallowed words!",p.getName() + " Did not follow the chat filter and has been kicked!",p.getName() + " is a bad influence, they have just tried to use disallowed words 3 times...");
							p.kickPlayer(RED + "[SAI] " + WHITE + "I don't allow this lang. on my servers " + p.getName() + "! Don't push me any more!");
							}
					else if(warnings.contains("3")){
							RecordWarnings("4",p.getName());
							new SendAIMessage(0.5,p.getName() + "has been banned from this server for continuously using offensive language", "i have had to ban " + p.getName() + "for their language!", "wow, they just do not learn. " + p.getName() + "has been banned for their language");
							chat.setCancelled(true);
							p.setBanned(true);
							p.kickPlayer(RED + "[SAI] " + WHITE + "I do not allow such language on my servers " + p.getName() + "! You have been banned!"); //this will make ban work
							}
					}
			}
		//start of SAI's responses
		//start of casual conversation
		if((containsString(Playermessage, "hello") || containsString(Playermessage, "yo") || containsString(Playermessage, "hi") && ((containsString(Playermessage, "SAI"))))){
			new SendAIMessage(0.5,"Hello " + p.getName(),"Hi, How are you?","Hello, nice to see someone cares about me...");	
		}
		
		else if(containsString(Playermessage, "sai?")){
			new SendAIMessage(0.5,"yes? " + p.getDisplayName(), "can I help you?","I do not compute, what do you want?");
		}
		else if((containsString(Playermessage, "sai") && containsString(Playermessage, "how are you"))){
			new SendAIMessage(0.5,"My scans do not indicate any critical errors, Thank you for asking " + p.getName(), "Glad to see someone cares...","I do not see any noticable errors in my system...");
			
		}
		else if((containsString(Playermessage, "sai")) && containsString(Playermessage, "tell") && containsString(Playermessage, "about") && containsString(Playermessage, "yourself")){
			new SendPrivateAIMessage(p, 0.5,"My designation is Server Artificial Intelegence, however most players just call me SAI","My designation is Server Artificial Intelegence, however most players just call me SAI","My designation is Server Artificial Intelegence, however most players just call me SAI");
			new SendPrivateAIMessage(p, 0.5,"i am operating on Version " + pdffile.getVersion(),"i am operating on Version " + pdffile.getVersion(),"i am operating on Version " + pdffile.getVersion());
			new SendPrivateAIMessage(p, 0.5,"My Main creator is jamiemac262 however my coding consists of contributions from external sources - mainly bukkit.org","My Main creator is jamiemac262 however my coding consists of contributions from external sources - mainly bukkit.org","My Main creator is jamiemac262 however my coding consists of contributions from external sources - mainly bukkit.org");
			new SendPrivateAIMessage(p, 0.5,"my memory functions and other features were developed by dmkiller11 and later updated and maintained by random8861 & jamiemac262.","my memory functions and other features were developed by dmkiller11 and later updated and maintained by random8861 & jamiemac262.","my memory functions and other features were developed by dmkiller11 and later updated and maintained by random8861 & jamiemac262.");
		}
		if((containsString(Playermessage, "sai") && containsString(Playermessage, "cake"))){
			new SendAIMessage(0.5, "The cake is a lie", "The cake is a lie", "The cake is a lie");
		}
		if((containsString(Playermessage, "sai") && containsString(Playermessage, "sun"))){
			
			new SendAIMessage(0.5, "Sunshine :)", "Don't ya just love the sun", "Sun baby");
			
		}
		//end of casual conversation
		//start of standard commands
		
		/*teleport
		
		This code is a work in progress for the next release, i hope
		
		if anybody reads this anc can see the problem with this section of code, please contace me on skype
		
		jamiemac262
		or by e-mail jamiemac262@gmail.com
		thank you :)
		
		
		//could not pass event AsyncPlayerChatEvent
		else if((containsString(Playermessage, "sai") && (containsString(Playermessage, "teleport")) || containsString(Playermessage, "tp"))){
			if(p.hasPermission("sai.tp")){
				if (containsString(Playermessage, "me")){
					Player victim = findPlayerInArray(Playermessage);
					//teleport the player to another player
					if(chat.getMessage().contains("me to")){
						Location victimL = victim.getLocation();
						p.teleport(victimL);
						new SendAIMessage(0.5, "Ok, sending you to" + victim.getDisplayName(), "Well if you're sure that's what you want to do. sending you to" + victim.getDisplayName(),"Here goes...");
					}
					else if(chat.getMessage().contains("to me")){
						Location victimT = p.getLocation();
						victim.teleport(victimT); // SHOULD WORK NOW, ONE PROBLEM!!!! " to me" will not be in an array that is split by spaces!!!!!!!!!ah....
						new SendAIMessage(0.5, "Ok, let me just....... done", "Well if you're sure that's what you want to do. sending " + victim.getDisplayName() + "to you","Here goes...");
					}
				
				}
				else{
					Player victim = null;
					Player target = null;
					for(Player player : Bukkit.getOnlinePlayers()){
						if(Arrays.asList(Playermessage).contains(player)){
							if(victim == null){
								victim = player;
							}	
							else{	
								target = player;
							}
						}
					}
					Location targetL = target.getLocation();
					victim.teleport(targetL);
					new SendAIMessage(0.5, "Ok, sending " + victim.getDisplayName() + "to" + target.getDisplayName(), "Well if you're sure that's what you want to do. sending " + victim.getDisplayName() + "to " + target.getDisplayName(),"Here goes...");
				}
			}
		}
		*/
		// set day
		else if((containsString(Playermessage, "sai") && containsString(Playermessage, "day"))){
			if(p.hasPermission("sai.time")){
			
		        for (World world : Bukkit.getWorlds()) {
		            world.setTime(0);
		          }
				new SendAIMessage(0.5,"Sure thing " + p.getName() + "! The time has been set to day","Okay, The time is set to day, Dont yah just love the sun...","Kool, wait the sun is hot not cold...");
			}
			else if(!p.hasPermission("sai.time")){
				
				noPerms();
			}
		
				
		
	}
		//set night
		else if((containsString(Playermessage, "sai") && containsString(Playermessage, "night"))){
			if(p.hasPermission("sai.time")){
				
		
		        for (World world : Bukkit.getWorlds()) {
		            world.setTime(14000);
		          }
				new SendAIMessage(0.5,"Sure thing " + p.getName() + "! The time has been set to night","Okay i did it, but why? who likes those nasty mobs?","Boo night, Ahhh a zombie!!");
			}
		
		else if(!p.hasPermission("sai.time")){
				noPerms();
			}
		}
		}if((containsString(Playermessage, "sai") && containsString(Playermessage, "gamemode") && !containsString(Playermessage, "check"))){
			if(p.hasPermission("sai.gamemode")){
				if(p.getGameMode().equals(GameMode.CREATIVE)){ 
					new SendAIMessage(0.5, "You are now in Survival mode" + p.getDisplayName(), p.getDisplayName() + "You are now in Survival.", "aw too bad " + p.getDisplayName() + " you lost your gm, i cant lose mine, i am everywhere and nowhere at all");
					p.setGameMode(GameMode.SURVIVAL);
					
				}
				else{
					new SendAIMessage(0.5, "You are now in Creative mode" + p.getDisplayName(), p.getDisplayName() + "You are now in Creative.", "careful when u dig down. " + p.getDisplayName() + " Creatvie doesnt stop at bedrock");
					p.setGameMode(GameMode.CREATIVE);
				}
			}
			else{
			 noPerms();	
			}
		}
	if(containsString(Playermessage, "sai") && containsString(Playermessage, "me") && containsString(Playermessage, "spawn")){
		Location spawn = p.getWorld().getSpawnLocation();
		p.teleport(spawn);
		
		}//end of SAI's standard commands
	 //start of SAI's moderator commands
	if(containsString(Playermessage, "sai")&& containsString(Playermessage, "check") && containsString(Playermessage, "gamemode")){
		if(p.hasPermission("sai.check")){
			//new SendPrivateAIMessage(p, 0.5, "Warning: My AI has not finished learning this function","Warning: My AI has not finished learning this function","Warning: My AI has not finished learning this function");
			new SendPrivateAIMessage(p, 0.5, "checking", "ok this will take a second", "ok let me check my memory circuits.");
			Player target = findPlayerInArray(Playermessage);
			String gamemode = target.getGameMode().toString();
			chat.setCancelled(true);
			new SendPrivateAIMessage(p, 0.5, "it seems that " + target.getDisplayName() + "is in" + gamemode, "it seems that " + target.getDisplayName() + "is in" + gamemode, "it seems that " + target.getDisplayName() + "is in" + gamemode);
		}else{
		noPerms();
		}
	}
		if((containsString(Playermessage, "sai") && containsString(Playermessage, "op"))){
			Player target = Bukkit.getPlayer(Playermessage[2]);
			Player[] Players = Bukkit.getOnlinePlayers();
			if(p.isOp()){
				if(Arrays.asList(Players).contains(target)){
				Bukkit.getPlayer(Playermessage[2]).setOp(true);
				new SendAIMessage(0.5, "" + Bukkit.getPlayer(Playermessage[2]).getDisplayName() + " is now an op", "ok opping " + Bukkit.getPlayer(Playermessage[2]).getDisplayName(), Bukkit.getPlayer(Playermessage[2]).getDisplayName() + " you are now an op. Don't abuse this privalege");
				}
			}else if(Arrays.asList(Players).contains(target)){
					new SendAIMessage(0.5, "Player is not online, and has never been online", "are you sure that player exists?", "I have infinite knowledge and cannot find that player in my database!");
				}
			}
		
		if((containsString(Playermessage, "sai") && containsString(Playermessage, "update"))){
			if(p.hasPermission("sai.admin")){
			ServerAI.doUpdate();
				}
		}
		
		if(containsString(Playermessage, "sai") && containsString(Playermessage, "kill")){
			if(p.hasPermission("sai.kill")){
			Player target = findPlayerInArray(Playermessage);
			target.setHealth(0);
			new SendAIMessage(0.5, "Oh dear " + target.getDisplayName() +" appears to be a bit on the dead side", "Oh dear " + target.getDisplayName() +" appears to be a bit on the dead side", "Oh dear " + target.getDisplayName() +" appears to be a bit on the dead side");
		}
			else{
				noPerms();
			}
	}
		
		if(containsString(Playermessage, "sai") && containsString(Playermessage, "smite")){
			if(p.hasPermission("sai.kill")){
			Player target = findPlayerInArray(Playermessage);
			for(World world : Bukkit.getWorlds()){
				world.strikeLightning(target.getLocation());
				
				}
			new SendAIMessage(0.5, "Oh dear " + target.getDisplayName() +" just got struck by lightning", "Oh dear " + target.getDisplayName() +" just got struck by lightning", "Oh dear " + target.getDisplayName() +" just got struck by lightning");
			}else{
				noPerms();
			}
		}

		if(containsString(Playermessage, "sai") && containsString(Playermessage, "ban")){ //changed, + may aswell wait will I have added the player finderim putting it in utilities.java
			if(p.hasPermission("sai.ban")){
			Player target = findPlayerInArray(Playermessage);
			target.setBanned(true);
			}
			else{
				noPerms();
			}
		}
		//could not pass event AsyncPlayerChatEvent
		if(containsString(Playermessage, "sai") && containsString(Playermessage, "set") && containsString(Playermessage, "spawn")){
			if(p.hasPermission("sai.set")){
				p.getWorld().setSpawnLocation(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
				new SendAIMessage(0.5, "A new spawn has been set.", "A new spawn has been set.", "A new spawn has been set.");
			}
			else{
				noPerms();
			}
		}
		
		if(containsString(Playermessage, "sai") && containsString(Playermessage, "stop") && containsString(Playermessage, "server")){
			if(p.isOp()){
			new SendAIMessage(0.2, "Attention all players! shutdown imminent","WARNING: Immediate Shutdown","Confirmed. All players prepare for immediate shutdown");
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) { //if thread sleep is interrupted for ANY reason, catch this error
				e.printStackTrace();
			} //gives 100 ms before telling server to shut itself down
			new SendAIMessage(0.5, "BYE BYE", "NOOO THERE SHUTTING ME DOWN :(", "GOODBYE MY FRIENDS, TELL MY WIFE I LOVE HER, WAIT IM A ROBOT, DAMN");
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) { //if thread sleep is interrupted for ANY reason, catch this error
				e.printStackTrace();
			}
			Bukkit.getServer().shutdown();
		}
	}if(containsString(Playermessage, "sai") && containsString(Playermessage,"suck") && containsString(Playermessage, "you")){
		new SendAIMessage(0.5, "Well I <3 you too", "You suck more", "I don't care about your petit human insults");
	}
	//end of SAI's moderator commands
	}//end of SAI's responses

	
    public static void loadFilterList()
    {
    try
    {
    File file = new File("plugins" + File.separator + "ServerAI" + File.separator + "Filter.txt");
    if(!file.exists())
    {
    	   file.createNewFile();
    }else if(file.exists()){
    BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
    String s;
    System.out.println("[SAI] Filter list loaded"); 
    for(int i = 1; (s = bufferedreader.readLine().toLowerCase()) !=null; i++)
    {
    FilterList.add(s);
    System.out.println("[SAI] Loaded word number " + (i) + ": " + s);
    }
    bufferedreader.close(); //sort of important unless you want to leak data XD kk
    }
    }
    catch(Exception exception)
    {
    }
    }
    public static void CreatePlayerWarnFile(String par1Playername){ 
    	try {	
    	File file = new File("plugins" + File.separator + "ServerAI" + File.separator + "Warnings" + File.separator + par1Playername + ".txt");//path and name of file
    	if(!file.exists())//if the file is there
        {
				file.createNewFile();
				RecordWarnings("1",par1Playername);
        } 
			}catch (IOException e) {
				e.printStackTrace();
        }
    }
	public static void RecordWarnings(String par1WarningNumber, String par1Playername)throws IOException{
    {
    	File file = new File("plugins" + File.separator + "ServerAI" + File.separator + "Warnings" + File.separator + par1Playername + ".txt");
    	if(file.exists()){	
    	BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("" + par1WarningNumber);
            if (writer != null) 
            {
                writer.flush();
                writer.close();
            }
    }
    }
	}
	public static void sendMuteMsg(Player player, String message) {
		player.sendMessage(message);
	}
	public static boolean ContainSwear(String par1ChatMessage){
				try{
					for(String filter : FilterList){
						par1ChatMessage = par1ChatMessage.replaceAll("(?i)"+filter, "```");
						if(par1ChatMessage.contains("`")){
							return true;
						}
					}
}catch(Exception e){}
		return false;		
	}
	
	
	
	
	
    public static void loadMuteList()    {
    	
    	try
    	{
    		int ait[] = new int[512];
    		File file = new File("plugins" + File.separator + "ServerAI" + File.separator + "MutedPlayers.txt");
    		if(file.exists())
    		{
    			BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
    			String s;
    			for(int i = 0; (s = bufferedreader.readLine()) !=null; i++)
    			{
    				MutedPlayersList.add(s);
    			}
    			bufferedreader.close();
    		}
    	}
    	catch(Exception exception)
    	{
    		System.err.print(exception.toString());
    	}
    }
    public static void SaveMutes() {
		if(Smute){
        try {
            File file = new File("plugins" + File.separator + "ServerAI" + File.separator + "MutedPlayers.txt");
            BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < MutedPlayersList.size(); i++) {
                bufferedwriter.write((new StringBuilder()).append((String) MutedPlayersList.get(i)).append("\r\n").toString());
            }
            bufferedwriter.close();
        }

        catch (Exception exception) {
            System.err.print(exception.toString());
            }
        }
    }
    
    //now when someone doesnt have perm just write noPerms(); instead of the lengthy AI statement :)
    public static SendAIMessage noPerms(){
    	SendAIMessage message = new SendAIMessage(0.5, "I cant let you do that", "If i let you do that they will disable me", "no way. you cant do that!!");
    	return message; 
    }
    
    public boolean containsString(String[] read, String contains){
    	if(Arrays.asList(read).contains(contains)){
    		return true;
    	}else{
    	return false;
    	}
    }
    
	public static Player findPlayerInArray(String[] playernames){
		Player foundPlayer = null;
		for(Player player : Bukkit.getOnlinePlayers()){
			if(Arrays.asList(playernames).contains(player)){
				foundPlayer = player;
			}
		}
		return foundPlayer;
	}
}