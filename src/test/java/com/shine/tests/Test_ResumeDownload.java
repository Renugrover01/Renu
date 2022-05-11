package com.shine.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shine.base.TestBaseSetup;

public class Test_ResumeDownload extends TestBaseSetup{
	WebDriver _ResumeDownloadDriver;
	String resume_name = "";
	String resume_name_with_extension = "";
	String download_path = "";

	@BeforeClass(alwaysRun=true)
	public void TestSetup() {
		download_path = System.getProperty("user.dir")+"/downloads";
		APP_LOGS.debug("Resume download path >> "+download_path);
		clean_download_folder_files(download_path);
		_ResumeDownloadDriver = getDriver(_ResumeDownloadDriver);
		loggedInShine(_ResumeDownloadDriver, email_main, pass_new);
	}


	@Test (priority=0)
	public void test_resume_download() {
		_Utility.Thread_Sleep(3000);
		_ResumeDownloadDriver.navigate().to(baseUrl+"/myshine/myprofile/");
		_Utility.Thread_Sleep(5000);
		List<WebElement> resume_list = _ResumeDownloadDriver.findElements(By.cssSelector(".cls_resume_list li em a"));
		WebElement firstLink = resume_list.get(0);
		APP_LOGS.debug("Link href = "+firstLink.getAttribute("outerHTML"));
		resume_name = firstLink.getText();
		APP_LOGS.debug("Resume Name >> "+firstLink.getText());
		firstLink.click();
		_Utility.Thread_Sleep(3000);	
	}

	@Test (priority=1)
	public void verify_resume_download() {
		_Utility.Thread_Sleep(3000);
		final File folder = new File(download_path);
		verify_downloaded_files(folder, resume_name);

	}

	@Test (priority=2)
	public  void read_word_file() {
		String data = read(download_path+"/"+resume_name_with_extension);
		assertTrue(data.contains("To seek a Professional career with an established organization in the realm of Test Engineer, looking for a long-term association where Individual skills, hard work and Honesty are recognized and conductive work culture is provided."));
		assertTrue(data.contains("Involved in preparing test case designs based on business requirements."));
		assertTrue(data.contains("9876556789"));
	}

	@Test (priority=3)
	public  void match_word_file_byte_codes() {
		boolean result = getFileMatchResult(download_path+"/"+resume_name_with_extension);
		assertEquals(result, true, "Files content are not equal.");
	}




	@AfterClass(alwaysRun=true)
	public void quitbrowser(){
		if(_ResumeDownloadDriver!=null)
			_ResumeDownloadDriver.quit();

	}


	@AfterMethod(alwaysRun=true)
	public void takeScreenshot(ITestResult testResult) {
		TestBaseSetup.takeScreenshotOnFailure(testResult, _ResumeDownloadDriver);
	}


	/**
	 * 
	 * @param folder
	 * @param expected_file_name
	 */
	public void verify_downloaded_files(final File folder, String expected_file_name) {
		for (final File fileEntry : folder.listFiles()) {
			if (!fileEntry.isDirectory()) {
				String file_name = fileEntry.getName();
				String actual_file_name = org.apache.commons.lang3.StringUtils.substringBefore(file_name, ".");
				expected_file_name = expected_file_name.replace(":", "_");
				APP_LOGS.debug(fileEntry.getPath());
				APP_LOGS.debug("Resume download actual_file_name: "+actual_file_name+" : "+fileEntry.getPath());
				assertEquals(actual_file_name, expected_file_name);
				resume_name_with_extension = file_name;
			}
		}
	}


	/**
	 * 
	 * @param directory
	 */
	private void clean_download_folder_files(String directory) {
		try {
			FileUtils.cleanDirectory(new File(directory));
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}


	/**
	 * Read downloaded resume file
	 * @param path
	 * @return
	 */
	public String read(String path)  {
		String text = "";
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(path)); //really a binary OLE2 Word file
			System.out.println(FileMagic.valueOf(is));
			if (FileMagic.valueOf(is) == FileMagic.OLE2) {
				WordExtractor ex = new WordExtractor(is);
				text = ex.getText();
				ex.close();
			} else if(FileMagic.valueOf(is) == FileMagic.OOXML) {
				XWPFDocument doc = new XWPFDocument(is);
				XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
				text = extractor.getText();
				extractor.close();
			}
			is.close();
		}catch(Exception exc) {
			exc.printStackTrace();
		}
		APP_LOGS.debug("******************* RESUME START****************");
		APP_LOGS.debug(text);
		APP_LOGS.debug("******************* RESUME END ****************");
		return text;

	}



	/**
	 * 
	 * @param filename
	 * @return
	 */
	public boolean getFileMatchResult(String filename) {
		String dir = System.getProperty("user.dir");
		File actual_file = new File(filename);
		File expected_file = new File(dir+"/src/test/resources/data/Resume.docx");
		File expected_file_2 = new File(dir+"/src/test/resources/data/Resume_v2.docx");
		boolean result = false;
		if(actual_file.exists()&&expected_file.exists()) {
			result = fileMatcher(actual_file, expected_file);
			if(result==false){
				APP_LOGS.debug("Files content are not equal.");
				result = fileMatcher(actual_file, expected_file_2);
			}
			return result;
		}
		else 
			return false;
	}

	/**
	 * 
	 * @param actual_file
	 * @param expected_file
	 * @return
	 */
	public boolean fileMatcher(File actual_file, File expected_file) {
		boolean result = false;
		try {
			result = FileUtils.contentEquals(actual_file, expected_file);
			APP_LOGS.debug(FileUtils.getFile(actual_file).length()+" : "+FileUtils.getFile(expected_file).length()+" >> Files content match = "+result);
		} catch (IOException e) {
			result = false;
			APP_LOGS.debug("Files content matching not done>> "+e.getMessage());
		}
		return result;

	}



}
