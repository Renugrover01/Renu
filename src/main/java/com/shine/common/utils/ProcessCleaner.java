package com.shine.common.utils;

import java.util.Date;

import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class ProcessCleaner extends TestBaseSetup{

	@Test(alwaysRun=true)
	public static void cleanInstance(){
		String OS = System.getProperty("os.name").toString().toLowerCase();
		if(OS.indexOf("windows") >= 0) {
			try{
				Process p = Runtime.getRuntime().exec("cmd /c start /wait Cleaner.bat");
				APP_LOGS.debug("Waiting for batch file ..."+new Date().getTime());
				p.waitFor();
				APP_LOGS.debug("Batch file done : CHROME & CHROMEDRIVER INSTANCE CLEANED "+new Date().getTime());
			}catch(Throwable t){
				APP_LOGS.fatal("Process killer failed: "+t.getMessage());
			}
		}


	}

}
