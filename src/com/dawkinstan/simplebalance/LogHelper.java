package com.dawkinstan.simplebalance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class LogHelper {
	
	private String latestUpdate;
	private static String LOG_FILE = "log.txt";
	
	public void setUpdateString(String latestUpdate){
		this.latestUpdate = latestUpdate;
	}
	
	public static boolean logData(String data){
		
		File logFile = new File(LOG_FILE);
		try{
			PrintWriter logWriter = new PrintWriter(logFile);
			
			logWriter.println(data);
			
			return true;
		} catch(FileNotFoundException e){
			System.exit(0);
		}
		
		
		return false;
	}
	
}
