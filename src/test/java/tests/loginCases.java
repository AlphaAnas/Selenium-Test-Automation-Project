package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class loginCases {
  @Test
  public void TC1_LoginWithValidCredentials() {
	  // Test Data
	  
	  
	  // Test Steps	
//		  1. Open browser and navigate to .....
//		  	-Call WebDriver method to open and navigate...
	  
	  WebDriver myBrowser = new ChromeDriver();
	  // launch that browser and go and navigate to the following url
	  myBrowser.get("https://adactinhotelapp.com/index.php");
	  
//	  2. In the Username field, type ....
//	  - Identify (Locate) Username field : id="username"
	  
	  WebElement usernameField = myBrowser.findElement(By.id("username"));//findElement is a parser method and By is a class
	  usernameField.sendKeys("abidselenium");//sendKeys is a method to type text in the
//	  - Type .....
	  
//	   In the Password field, type ...
//	  - Identify (Locate) Password field: name="password"
	  WebElement passwordField = myBrowser.findElement(By.name("password"));
	  passwordField.sendKeys("6D39GW");//sendKeys is a method to type text in the
//	  - Type ....

	  //	  3. Click on Login button
	  //	  - Identify (Locate) Login button: class="login_button"
	  WebElement loginButton = myBrowser.findElement(By.id("login"));
	  loginButton.click();//click() is a method to perform click action on the identified element

	  //	  4. Verify that user is successfully logged in
	  String actualPageTitle = myBrowser.getTitle();	
	  String PageTitle = "Adactin.com - Search Hotel";
	   Assert.assertEquals(actualPageTitle, PageTitle);
		
		// Page Heading
		
		String actualPageHeading = myBrowser.findElement(By.className("login_title")).getText();
		String PageHeading = "Search Hotel";
		Assert.assertEquals(actualPageHeading, PageHeading);
		
		// User Heading
		
		String actualUserHeading = myBrowser.findElement(By.id("username_show")).getAttribute("value");
		String UserHeading = "abidselenium";
		Assert.assertEquals(actualUserHeading,UserHeading);
//	  
//		 5. Close the browser
	  myBrowser.quit();
		
	  	
  }
  
  @Test
  public void TC2_LoginWithInvalidCredentials() {
	
	  	WebDriver myBrowser = new ChromeDriver();
	  	myBrowser.get("https://adactinhotelapp.com/index.php");
	  	
	  	WebElement usernameField = myBrowser.findElement(By.id("username"));
	  	usernameField.sendKeys("invalidUsername");
	  	
	  	WebElement passwordField = myBrowser.findElement(By.name("password"));
	  	passwordField.sendKeys("invalidPassword");
	  	
	  	WebElement loginButton = myBrowser.findElement(By.id("login"));
	  	
	  	
//	  	the error message should appear after clicking the login button
	  	loginButton.click();
	  	
	  	WebElement errorMessage = myBrowser.findElement(By.className("auth_error"));
	  	String actualErrorMessageText = errorMessage.getText();
	  	String expectedErrorMessageText = "Invalid Login details or Your Password might have expired. Click here to reset your password";
	  	
	  	Assert.assertEquals(actualErrorMessageText, expectedErrorMessageText);
	  	
	  	myBrowser.quit();
	  
  }
}
