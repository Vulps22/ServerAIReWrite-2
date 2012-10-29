package me.jamiemac262.ServerAIReWrite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ReadWrite {
	public static List<String> getContents(File aFile){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(aFile));
			List<String> contents = new ArrayList<String>();
			String temp = "";
			while((temp = reader.readLine()) != null)
					contents.add(temp);
			reader.close();
			return contents;
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;
	}
	
	public static void setContents(File aFile, String aContents) throws FileNotFoundException, IOException{
		PrintWriter pw = new PrintWriter(new FileWriter(aFile));
		pw.println(aContents);
		pw.close();
	}
	
	public static String readContents(File afile){
		StringBuilder contents = new StringBuilder();
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			ServerAI.logger.log(Level.SEVERE, "Could not read: " + afile.getName().toString());
		}
		
		return contents.toString();
	}
	
	public static void writeContents(File aFile, String aContents) throws FileNotFoundException, IOException{
		if(aFile == null){
			throw new IllegalArgumentException("File Must Not Be Null");
		}
		if(!aFile.exists()){
			aFile.createNewFile();
		}
		if(!aFile.isFile()){
			throw new IllegalArgumentException(aFile + " Should not be a Directory");
		}
		if(!aFile.canWrite()){
			throw new IllegalArgumentException(aFile + " Could not be written, is it open in another program?");
		}
		
		Writer output = new BufferedWriter(new FileWriter(aFile));
		try{
			output.write( aContents );
		}finally{
			output.close();
		}
	}

}
