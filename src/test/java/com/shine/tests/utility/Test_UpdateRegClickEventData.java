package com.shine.tests.utility;

import com.google.api.GoogleSheet;
import com.shine.base.TestBaseSetup;
import com.shine.common.utils.CreateEventHandlerHTMLReport;
import com.shine.emailer.EventHandlerEmail;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test_UpdateRegClickEventData extends TestBaseSetup{



	@SuppressWarnings("static-access")
	@Test(priority=1)
	public void updateExcel() throws Exception{

		//Get last updated row
		GoogleSheet _GoogleSheet = new GoogleSheet();
		int rowCount = Integer.parseInt(_GoogleSheet.getLastUpdateRow(false));
		if(rowCount>0){
			rowCount = rowCount  + 2; // As excel has fixed row.
			//Get token to hit API
			String token = _GoogleSheet.getToken();
			_GoogleSheet.updateExcel(clickEventList, rowCount, token, false);
		}
	}


	@Test(priority=2)
	public void sendEmail() throws Exception{
		Date today = new Date();  
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(today);  

		calendar.add(Calendar.MONTH, 1);  
		calendar.set(Calendar.DAY_OF_MONTH, 1);  
		calendar.add(Calendar.DATE, -1);  

		Date lastDayOfMonth = calendar.getTime();  
		
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); 
		
		String lastDayOfMonthFormatted = sdf.format(lastDayOfMonth);
		String currentDayOfMonthFormatted =  sdf.format(today);  

 
		System.out.println("Today            : " + currentDayOfMonthFormatted );  
		System.out.println("Last Day of Month: " + lastDayOfMonthFormatted);  
		
		if (lastDayOfMonth.compareTo(today) > 0) {
			System.out.println("today "+currentDayOfMonthFormatted+" is before lastDayOfMonth "+lastDayOfMonthFormatted);
		} else {
			System.out.println(currentDayOfMonthFormatted+" : Date Matched: "+lastDayOfMonthFormatted);
			GoogleSheet _GoogleSheet = new GoogleSheet();
			String responseAPI = _GoogleSheet.getLastUpdateRow(true);
			CreateEventHandlerHTMLReport _CreateEventHandlerHTMLReport = new CreateEventHandlerHTMLReport();
			_CreateEventHandlerHTMLReport.create_html_report(responseAPI);
			EventHandlerEmail _Email = new EventHandlerEmail();
			_Email.sendEmail();
		
		}


	}


}
