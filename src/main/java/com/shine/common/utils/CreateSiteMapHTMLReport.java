package com.shine.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import com.shine.common.utils.CommonUtils;


public class CreateSiteMapHTMLReport {



	public int create_html_report(Map<String, Integer> distinctWordCounts) {
		File file = new File(System.getProperty("user.dir") + "/src/test/resources/reports/sitemap_report.html");
		StringBuilder contentBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/test/resources/reports/sitemap_report_template.html"));
			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
			in.close();
		} catch (IOException e) {
		}
		String content = contentBuilder.toString();

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);
			String htmlPage = content;
			htmlPage =  htmlPage.replace("{{project-name}}", "Shine Sitemap Analysis Report "+getDateAsString());
			
		
			
			htmlPage =  htmlPage.replace("{{timestamp}}", getDateAsString());
			htmlPage =  htmlPage.replace("{{testid}}", "#"+getRandomString());
			String html_result = "";
			String html_header = "<table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" class=\"x_testOverview\" style=\"/* border-radius:9px; */max-width:600px;font-size:15px;background-color:#fff;border: 1px solid #cccccc;padding: 3px 0px 4px 12px;\">\n" + 
					"        <tbody>";
			String html_body ="";
			html_body = html_body + "<tr><th align=\"left\">URL Pattern</th><th align=\"left\">Occurrence</th></tr>";
			html_body = html_body + "<tr><td colspan=\"5\"><hr style=\"display:block;border:0;border-top:1px solid #948f8f\"></td></tr>";
			int count = 0;
		
			for (Entry<String, Integer> entry : distinctWordCounts.entrySet()) {
				System.out.println(entry.getKey() + " occurs " + entry.getValue() + " times.");
				count = count + entry.getValue();
				html_body = html_body + "<tr><td>"+entry.getKey()+"</td><td>"+entry.getValue()+" times</td></tr>";
				html_body = html_body + "<tr><td colspan=\"5\"><hr style=\"display:block;border:0;border-top:1px solid #948f8f\"></td></tr>";
			}
			html_result = html_header+html_body+" </tbody></table>";
		//	htmlPage =  htmlPage.replace("{{expected}}", "17 to 25");
			htmlPage =  htmlPage.replace("{{expected}}", "20");
			String result_status ="";
			String header_bar_color = "";
			String header_bar_border_color = "";
			if(count < 20) {
				htmlPage =  htmlPage.replace("{{actual}}", "<span style='color:salmon;'>"+String.valueOf(count)+"</span>");
				htmlPage =  htmlPage.replace("{{actual-text}}", "<span style='color:salmon;'>Actual</span>");
				result_status = "with failure!!!";
				header_bar_color = "#fa8678";
				header_bar_border_color = "red";
			}
			
			else if(count == 20) {
				htmlPage =  htmlPage.replace("{{actual}}", "<span style='color:#4fc14f;'>"+String.valueOf(count)+"</span>");
				htmlPage =  htmlPage.replace("{{actual-text}}", "<span style='color:#4fc14f;'>Actual</span>");
				result_status = "successfully!!!";
				header_bar_color = "#85d485";
				header_bar_border_color = "#4fc14f";   
			}
			
/*			else if(count >= 7 && count <= 10) {
				htmlPage =  htmlPage.replace("{{actual}}", "<span style='color:#4fc14f;'>"+String.valueOf(count)+"</span>");
				htmlPage =  htmlPage.replace("{{actual-text}}", "<span style='color:#4fc14f;'>Actual</span>");
				result_status = "successfully!!!";
				header_bar_color = "#85d485";
				header_bar_border_color = "#4fc14f";   
			}   */
			else if(count > 20) {
				htmlPage =  htmlPage.replace("{{actual}}", "<span style='color:salmon;'>"+String.valueOf(count)+"</span>");
				htmlPage =  htmlPage.replace("{{actual-text}}", "<span style='color:salmon;'>Actual</span>");
				result_status = "with failure!!!";
				header_bar_color = "#fa8678";
				header_bar_border_color = "red";
			}  
			
			
			htmlPage =  htmlPage.replace("{{status}}", "<b>Your test run completed "+result_status+"</b>");
			
			htmlPage =  htmlPage.replace("{{header-bar-color}}" , header_bar_color);
			htmlPage =  htmlPage.replace("{{header-bar-border-color}}" , header_bar_border_color);
			htmlPage =  htmlPage.replace("{{seprator-color}}" , header_bar_border_color);
			
     		if(html_result.contains("company")) {
				html_result =  html_result.replace("company","<span style='color:#ffce01;'>company</span>");
			}
			if(html_result.contains("course")) {
				html_result =  html_result.replace("course","<span style='color:#2886d7;'>course</span>");
			}
			if(html_result.contains("industry")) {
				html_result =  html_result.replace("industry","<span style='color:#ce890a;'>industry</span>");
			}
			if(html_result.contains("location")) {
				html_result =  html_result.replace("location","<span style='color:#9c27b0;'>location</span>");
			}
			if(html_result.contains("experience")) {
				html_result =  html_result.replace("experience","<span style='color:#2196f3;'>experience</span>");
			}
			if(html_result.contains("functional")) {
				html_result =  html_result.replace("functional","<span style='color:#f44336;'>functional</span>");
			}
			if(html_result.contains("skill")) {
				html_result =  html_result.replace("skill","<span style='color:#e91e63;'>skill</span>");
			}
			if(html_result.contains("title")) {
				html_result =  html_result.replace("title","<span style='color:#00bcd4;'>title</span>");
			}
			if(html_result.contains("education")) {
				html_result =  html_result.replace("education","<span style='color:#072bf3;'>education</span>");
			}


			htmlPage =  htmlPage.replace("{{result}}",html_result);
			bufferedWriter.write(htmlPage);
			System.out.println("Html page created");
			bufferedWriter.flush();
			fileWriter.flush();
			return count;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {

				bufferedWriter.close();
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return 0;




	}


	/**
	 * Method to get Date as String
	 * @return
	 */
	private String getDateAsString() {
		DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a");
		Date date = new Date();
		return dateFormat.format(date);
	}

	/**
	 * 
	 * @return
	 */
	protected static String getRandomString() {
		String range = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder data = new StringBuilder();
		Random rnd = new Random();
		while (data.length() < 9) {
			int index = (int) (rnd.nextFloat() * range.length());
			data.append(range.charAt(index));
		}
		return data.toString();

	}



}
