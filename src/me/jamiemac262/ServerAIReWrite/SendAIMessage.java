package me.jamiemac262.ServerAIReWrite;

import java.awt.Toolkit;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class SendAIMessage {
	ChatColor RED = ChatColor.RED;
	ChatColor WHITE = ChatColor.WHITE;
  Toolkit toolkit;
  String MTS1 = "default";
  String MTS2 = "default";
  String MTS3 = "default";
  String RMSG = "default";
  Timer timer;

  public SendAIMessage(double d, String message1, String message2, String message3) { //can i ask you a question?
    toolkit = Toolkit.getDefaultToolkit();
    timer = new Timer();
    timer.schedule(new RemindTask(message1, message2, message3), (long) (d * 1000));
  }
  
  class RemindTask extends TimerTask {
	    public RemindTask(String message1, String message2, String message3) {
	    	MTS1 = message1;
	    	MTS2 = message2;
	    	MTS3 = message3;
	    }
		public void run() {
			Random random = new Random();
		     int MessageNum = random.nextInt(3)+1;
		     if(MessageNum == 1){
		    	 RMSG = MTS1;
		     }else if(MessageNum == 2){
		    	 RMSG = MTS2;
		     }else if(MessageNum == 3){
		    	 RMSG = MTS3;
		     }
		      Bukkit.getServer().broadcastMessage(RED + "[SAI] " + WHITE + RMSG);
		      timer.cancel();
	    }
	  }
}
