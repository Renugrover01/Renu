package com.shine.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import com.shine.base.TestBaseSetup;

public class LogAnalyzer extends TestBaseSetup {

    private static Logger DB_APP_LOGS = Logger.getLogger("DBAppLogger");
    private static final String DATE_FORMAT = "dd MMM yyyy, hh:mm:ss a";
    private static final String LINE_BREAK = "\n\n ";

    /**
     * @param driver
     */
    public static void analyzeLog(WebDriver driver) {
        String url = "N/A";
        try {
            url = driver.getCurrentUrl();
            APP_LOGS.debug("---------->> " + url + "  <<----------------");
            getLogger(driver, url);

        } catch (Exception ex) {
            getLogger(driver, url);
            APP_LOGS.fatal(ex.getMessage());
        }
    }


    /**
     * get logger: Get log from browser console
     *
     * @param driver
     */
    public static void getLogger(WebDriver driver, String url) {
        System.setProperty("dblogfilename", System.getProperty("user.dir") + "/logs/DBApplication.log");
        DB_APP_LOGS = Logger.getLogger("DBAppLogger");
        PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/test/resources/config/logDB.properties");
        try {
            String formattedDate = new SimpleDateFormat(DATE_FORMAT).format(new Date());
            LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
            for (LogEntry entry : logEntries) {
                String debugLevel = entry.getLevel().toString().trim();
                String debugMessage = entry.getMessage().trim();
                APP_LOGS.debug(formattedDate + LINE_BREAK + debugLevel + LINE_BREAK + debugMessage);
                if (debugMessage.contains(baseUrl) || debugMessage.contains("static1.shine.com")) {
                    int statusCode = getStatusCode(debugMessage);
                    if (statusCode != 0) {
                        String errorUrl = StringUtils.substringBefore(debugMessage, " ");
                        DB_APP_LOGS.info("Source: " + url + ", found: " + errorUrl + ", status: " + getStatusCode(debugMessage));
                    }
                }
            }
            APP_LOGS.debug("-----------------------------END----------------------------");
        } catch (Exception ex) {
            APP_LOGS.fatal(ex.getMessage());
        }
        /*******ERROR PAGE PARSER*******/
        try {
            DB_APP_LOGS.debug("---------->> " + url + "  <<----------------");
            String pageSource = driver.getPageSource().toLowerCase();
            if (pageSource.contains("reference") || pageSource.contains("access denied")) {
                DB_APP_LOGS.debug("Reference code: " + pageSource);
            }
            DB_APP_LOGS.debug("-----------------------------END----------------------------");
        } catch (Exception ex) {
            DB_APP_LOGS.fatal(ex.getMessage());
        }
    }


    /**
     * @param driver
     */
    public static boolean analyzeSpecificRequest(WebDriver driver) {
        String formattedDate = new SimpleDateFormat(DATE_FORMAT).format(new Date());
        LogEntries logEntries = driver.manage().logs().get(LogType.PERFORMANCE);
        int c1 = 0;
        int c2 = 0;
        int c3 = 0;
        for (LogEntry entry : logEntries) {
            String log = entry.getMessage();
            if (log.contains("bat.bing.com/bat.js")) {
                APP_LOGS.debug(formattedDate + LINE_BREAK + entry.getLevel() + LINE_BREAK + entry.getMessage());
                c1++;
            }
            if (log.contains("bat.bing.com/action")) {
                APP_LOGS.debug(formattedDate + LINE_BREAK + entry.getLevel() + LINE_BREAK + entry.getMessage());
                c2++;
            }
            if (log.contains("1037687402")) {
                APP_LOGS.debug(formattedDate + LINE_BREAK + entry.getLevel() + LINE_BREAK + entry.getMessage());
                c3++;
            }
            if (c1 >= 1 && c2 >= 1 && c3 >= 1) {
                APP_LOGS.debug("Pixel Match Count>> " + c1 + " : " + c2 + " : " + c3);
                return true;
            }

        }
        APP_LOGS.debug("Match Count>> " + c1 + " : " + c2);
        return false;
    }

    public static int getStatusCode(String message) {
        int status = 0;
        if (message.indexOf("404") >= 0) {
            return 404;
        }
        if (message.indexOf("400") >= 0) {
            return 400;
        }
        if (message.indexOf("401") >= 0) {
            return 401;
        }
        if (message.indexOf("502") >= 0) {
            return 502;
        }
        if (message.indexOf("403") >= 0) {
            return 403;
        }
        if (message.indexOf("500") >= 0) {
            return 500;
        }
        if (message.indexOf("503") >= 0) {
            return 503;
        }
        if (message.indexOf("504") >= 0) {
            return 504;
        }
        if (message.indexOf("302") >= 0) {
            return 302;
        }
        if (message.indexOf("301") >= 0) {
            return 301;
        }
        return status;

    }


    /**
     * @param driver
     */
    public static boolean analyzePageViewRequest(WebDriver driver) {
        String formattedDate = new SimpleDateFormat(DATE_FORMAT).format(new Date());
        LogEntries logEntries = driver.manage().logs().get(LogType.PERFORMANCE);
        int pageViewFoundFlag = 0;
        for (LogEntry entry : logEntries) {
            String log = entry.getMessage();
            if (log.contains("google-analytics.com") && log.contains("collect")) {
                APP_LOGS.debug(formattedDate + LINE_BREAK + entry.getLevel() + LINE_BREAK + entry.getMessage());
                if (log.contains("t=pageview")) {
                    pageViewFoundFlag++;
                }
            }

        }
        if (pageViewFoundFlag >= 1) {
            APP_LOGS.debug("Page view Match Count>> " + pageViewFoundFlag);
            return true;
        }
        APP_LOGS.debug("Page view Match Count>> " + pageViewFoundFlag);
        return false;
    }

}
