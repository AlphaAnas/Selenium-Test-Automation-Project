package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;



import pages.LoginPage;

public class loginCases {
	
	private WebDriver myBrowser; // Instance variable to store driver
	
	/**
	 * This method runs after each test method.
	 * It ensures the browser closes even if the test fails.
	 * This allows TestNG to generate reports properly.
	 */
	@AfterMethod
	public void tearDown() {
		if (myBrowser != null) {
			try {
				myBrowser.quit();
			} catch (Exception e) {
				System.out.println("Error closing browser: " + e.getMessage());
			}
		}
	}
	
	@Test
	public void TC1_LoginWithValidCredentials() {
		// Test Data
		
	  String USERNAME = "abidselenium";
	  String PASSWORD = "6D39GW";
	  
	  // Test Steps	
//		  1. Open browser and navigate to .....
//		  	-Call WebDriver method to open and navigate...
	  
	  myBrowser = new ChromeDriver(); // Use instance variable
	  // launch that browser and go and navigate to the following url
	  myBrowser.get("https://adactinhotelapp.com/index.php");
	  
//	  2. In the Username field, type ....
//	  - Identify (Locate) Username field : id="username"
	  
		// Create Page Object for Login Page
	 LoginPage loginPage = new LoginPage(myBrowser);
	 loginPage.usernameField.sendKeys(USERNAME);
	 loginPage.passwordField.sendKeys(PASSWORD);
	 loginPage.loginButton.click();

	  //	  4. Verify that user is successfully logged in
	  String actualPageTitle = myBrowser.getTitle();	
	  String PageTitle = "Adactin.com - Search Hotel";
	   Assert.assertEquals(actualPageTitle, PageTitle);
		
		// Page Heading
		
		String actualPageHeading = myBrowser.findElement(By.className("login_title")).getText();
		String PageHeading = "Search Hotel";
		actualPageHeading = actualPageHeading.split("\\(")[0].trim();
		Assert.assertEquals(actualPageHeading, PageHeading);

		
		// User Heading
		
		String actualUserHeading = myBrowser.findElement(By.id("username_show")).getAttribute("value");
		String UserHeading = "Hello "+USERNAME+"!";
		Assert.assertEquals(actualUserHeading,UserHeading);
//	  
//		 5. Browser will be closed automatically by @AfterMethod
	  	
  }
  
  @Test
  public void TC2_LoginWithInvalidCredentials() {
	
	  	myBrowser = new ChromeDriver(); // Use instance variable
	  	myBrowser.get("https://adactinhotelapp.com/index.php");
	  	
	  	LoginPage loginPage = new LoginPage(myBrowser);
		loginPage.usernameField.sendKeys("invalidUser");
		loginPage.passwordField.sendKeys("invalidPass");
		//	  	the error message should appear after clicking the login button
		loginPage.loginButton.click();
	  	
	  	
	
	  	
	  	WebElement errorMessage = myBrowser.findElement(By.className("auth_error"));
	  	String actualErrorMessageText = errorMessage.getText();
	  	String expectedErrorMessageText = "Invalid Login details or Your Password might have expired. Click here to reset your password";
	  	
	  	Assert.assertEquals(actualErrorMessageText, expectedErrorMessageText);
	  	
	  	// Browser will be closed automatically by @AfterMethod
	  
  }
}
