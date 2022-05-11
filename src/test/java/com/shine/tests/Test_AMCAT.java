package com.shine.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.shine.base.TestBaseSetup;

public class Test_AMCAT extends TestBaseSetup {
	private WebDriver _Driver;
	private WebDriverWait _Wait;

	private By _MyProfile = By.xpath("//*[@id=\"ReactContainer\"]/div/header/nav/div/div/ul[1]/li[4]/a");
	private By _ImportSkills = By.xpath("//*[contains(text(),'Import Skills from AMCAT')]");
	private By _OKButton = By.cssSelector("[class='submitbutton Addbutton cls_okbtnn']");
	private By skillSection = By.id("id_skills");

	@BeforeClass()
	public void testSetup() {
		_Driver = getDriver(_Driver);
		_Wait = new WebDriverWait(_Driver, 15);
		_Driver.get(baseUrl);
		_Driver.manage().window().maximize();

	}

	/*
	 * This function will test the Import functionality of Non AMCAT user.
	 */

	@Test(priority = 0)
	public void test_nonAMCAT_import_skills() {
		/* Login Non AMCAT user */
		loggedInShine(_Driver, "manvitestamcat@gmail.com", "123456");
		// To test non AMCAT flow
		click_on_import_skill_on_profile();
		// to handle popup of Import Skills
		assertPopupMessage(
				"We did not find any other certification and skills in your AMCAT profile. Verify your skill with AMCAT Certification now\n"
						+ "Schedule AMCAT Skill test");
		waitForPopupDisappearance();
	}

	/*
	 * This function will test the Schdule Test functionality of New user which is
	 * Non AMCAT user.
	 */
	@Test(priority = 1)
	public void test_nonAMCAT_validate_skills() {
		_Driver.navigate().refresh();

		// Scroll till Skills and Certifications
		_Utility.scrollTOElement(skillSection, _Driver);
		_Utility.Thread_Sleep(1000);

		// Click on Validate with AMCAT
		_Driver.findElement(By.xpath("//*[contains(text(),'Validate')]")).click();
		_Utility.Thread_Sleep(1000);
		// To handle popup
		_Driver.findElement(By.cssSelector("[class='ui-dialog ui-widget ui-widget-content ui-corner-all']")).click();

		// To verify text of SCHEDULE AMCAT button
		String button_text = _Driver.findElement(By.xpath("//*[@id=\"id_form_certification_list\"]/ul/li[1]/span/a"))
				.getText();
		assertEquals(button_text, "Schedule AMCAT Skill test");
		APP_LOGS.info(button_text);

		// Click in Schedule AMCAT button
		_Driver.findElement(By.xpath("//*[@id=\"id_form_certification_list\"]/ul/li[1]/span/a")).click();

		// Route to Learning site and Close that learning browser
		String ParentWindow = _Driver.getWindowHandle();
		Set<String> set = _Driver.getWindowHandles();

		assertTrue(set.size() > 1, "No new tab/ window opened - " + set.size());
		for (String childWindow : set) {
			// Compare whether the main windows is not equal to child window. If not equal,
			// we will close.
			if (!ParentWindow.equals(childWindow)) {
				_Driver.switchTo().window(childWindow);
				APP_LOGS.info(_Driver.switchTo().window(childWindow).getTitle());
				_Driver.close();
			}
		}

		// This is to switch to the main window
		_Driver.switchTo().window(ParentWindow);

		// To close the popup on My Profile page
		_Driver.findElement(By.cssSelector("[class='ui-icon ui-icon-closethick']")).click();
		_Utility.Thread_Sleep(1000);
		_Wait.until(ExpectedConditions
				.invisibilityOfElementLocated(By.cssSelector("[class='ui-icon ui-icon-closethick']")));

	}

	/*
	 * This function will test the Import functionality of AMCAT user.
	 */
	/*
	 * @Test(priority = 2) public void test_AMCAT() { logout(); Login AMCAT user
	 * loggedInShine(_Driver, email_new, pass_new);
	 * click_on_import_skill_on_profile(); //to handle popup of Import Skills
	 * //_Driver.findElement(By.cssSelector("[class='pop_amcat']")).click();
	 * assertPopupMessage("Skills and corresponding Certificates have been added to My Profile"
	 * ); waitForPopupDisappearance(); }
	 */

	@AfterMethod
	public void test_Take_Screenshot(ITestResult _ITestResult) {
		takeScreenshotOnFailure(_ITestResult, _Driver);
	}

	@AfterClass
	public void closeBrowser() {
		if (_Driver != null) {
			_Driver.quit();
		}

	}

	/**
	 * logout from shine.com
	 */
	private void logout() {
		_Driver.navigate().to(baseUrl + "/myshine/logout/");
	}

	/**
	 * click_on_import_skill_on_profile
	 */
	private void click_on_import_skill_on_profile() {
		_Utility.Thread_Sleep(1500);
		// Go to My Profile
		_Driver.findElement(_MyProfile).click();
		_Utility.Thread_Sleep(1500);
		// Scroll till Skills and Certifications
		_Utility.scrollTOElement(By.id("id_skills"), _Driver);
		_Utility.Thread_Sleep(1500);
		// To Click on Import button
		_Driver.findElement(_ImportSkills).click();
		_Utility.Thread_Sleep(1000);
	}

	/**
	 *
	 * @param expected_message
	 */
	private void assertPopupMessage(String expected_message) {
		String popupTitle = _Driver.findElement(By.cssSelector("[class='ui-dialog-title']")).getText().trim();
		assertEquals(popupTitle, "Certifications and Skills from AMCAT");
		String actual_message = _Driver.findElement(By.xpath("//*[@id=\"id_import_skill_message\"]/ul/li")).getText();
		APP_LOGS.info("Popup message " + actual_message);
		assertEquals(actual_message, expected_message);
	}

	/**
	 * wait For Popup Disappearance
	 */
	private void waitForPopupDisappearance() {
		_Driver.findElement(_OKButton).click();
		_Utility.Thread_Sleep(1000);
		_Wait.until(ExpectedConditions.invisibilityOfElementLocated(_OKButton));
	}

}
