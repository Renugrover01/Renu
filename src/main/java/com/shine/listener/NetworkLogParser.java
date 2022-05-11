package com.shine.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import com.shine.base.TestBaseSetup;

public class NetworkLogParser extends TestBaseSetup{


	/**
	 * get logger: Get log from browser console
	 * @param driver
	 */
	public String getURL(WebDriver driver, String url) {
		try{
			String formattedDate = new SimpleDateFormat("dd MMM yyyy, hh:mm:ss a").format(new Date()).toString();
			LogEntries logEntries = driver.manage().logs().get(LogType.PERFORMANCE);
			for (LogEntry entry : logEntries) {
				String debugLevel = entry.getLevel().toString().trim();
				String debugMessage = entry.getMessage().toString().trim();
				//APP_LOGS.debug(formattedDate + "\n \n " + debugLevel + "\n\n " + debugMessage);
				if(debugMessage.contains("https://www.shine.com/myshine/login/?tc=")) {
					APP_LOGS.debug(formattedDate + "\n\n " + debugLevel + "\n\n " + debugMessage);
					return jsonParser(debugMessage);
				}
			}
			APP_LOGS.debug("-----------------------------END----------------------------");
		}
		catch(Throwable t){
			APP_LOGS.fatal(t.getMessage());
		}
		return "";
	}

	private String jsonParser(String debugMessage) {
		try {
			Object _JsonObject =  new JSONParser().parse(debugMessage);
			JSONObject jobj = (JSONObject)_JsonObject;
			JSONObject message = (JSONObject)jobj.get("message");
			JSONObject params = (JSONObject)message.get("params");
			String url = (String) params.get("documentURL");
			url = url.replace("www", "pp-www");
			return url;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  "";
		
	}

}
