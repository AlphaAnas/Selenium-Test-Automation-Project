package tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class Invalid_date_SearcHotel {
    /*
         *          |_ INVALID DATE RANGE (PAST)
         *          |_ INVALID DATE RANGE (FUTURE)
         *          |_ INVALID DATE FORMAT (FUTURE)
         *          |_ DROPDOWNS DATA
         *          |_ VERIFYING GUI AGAINST WIREFRAMES
         *          \
         */
    
    
            private WebDriver mybrowser; // Instance variable to store driver
    
        public WebDriver loginToApp(String username, String password) {


                WebDriver driver = new ChromeDriver();
                driver.get("https://adactinhotelapp.com/index.php");

                driver.findElement(By.id("username")).sendKeys(username);
                driver.findElement(By.name("password")).sendKeys(password);
                driver.findElement(By.id("login")).click();
                return driver;
            }
        
        /**
         * This method runs after each test method.
         * It ensures the browser closes even if the test fails.
         * This allows TestNG to generate reports properly.
         */
        @AfterMethod(alwaysRun = true)
        public void tearDown() {
            if (mybrowser != null) {
                try {
                    mybrowser.quit();
                } catch (Exception e) {
                    System.out.println("Error closing browser: " + e.getMessage());
                }
            }
        }

        /**
         * Generates a date range for hotel booking.
         * @param startDaysFromNow Number of days from today for check-in (0 = today, 1 = tomorrow, etc.)
         * @param numberOfNights Number of nights to stay
         * @return String array with [0] = check-in date, [1] = check-out date in DD/MM/YYYY format
         */
        public String[] generateDateRange(int startDaysFromNow, int numberOfNights){
            LocalDate checkIn = LocalDate.now().plusDays(startDaysFromNow);
            LocalDate checkOut = checkIn.plusDays(numberOfNights);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            return new String[]{
                checkIn.format(formatter),
                checkOut.format(formatter)
            };
        }

    // ================= TEST CASES FOR INVALID DATE RANGE (PAST) ====================

            @Test
            public void TC1_1_Standard_1_Night_Past_Date() {
                String USERNAME = "abidselenium";
                String PASSWORD = "6D39GW";

                mybrowser = loginToApp(USERNAME, PASSWORD);

                // Check-in yesterday, 1 night stay
                String[] dates = generateDateRange(-1, 1);

                mybrowser.findElement(By.id("location")).sendKeys("Sydney");
                mybrowser.findElement(By.id("hotels")).sendKeys("Hotel Creek");
                mybrowser.findElement(By.id("room_type")).sendKeys("Standard");
                mybrowser.findElement(By.id("room_nos")).sendKeys("1 - One");
                
                WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                checkIn.clear();
                checkIn.sendKeys(dates[0]);
                
                WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                checkOut.clear();
                checkOut.sendKeys(dates[1]);
                
                mybrowser.findElement(By.id("adult_room")).sendKeys("2 - Two");
                mybrowser.findElement(By.id("child_room")).sendKeys("0 - None");
                mybrowser.findElement(By.id("Submit")).click();

                // Capture and verify error
                WebElement errorElement = mybrowser.findElement(By.xpath("//span[@id='checkin_span']"));
                String checkinError = errorElement.getText();
                Assert.assertEquals(checkinError.trim(), "Check-In Date cannot be in the past");

                         
                // User should remain on the same page
                Assert.assertEquals(mybrowser.getTitle(), "Adactin.com - Select Hotel");
            }
            @Test
            public void TC2_1_Double_5_Night_Today_minus_50() {
                String USERNAME = "abidselenium";
                String PASSWORD = "6D39GW";

                mybrowser = loginToApp(USERNAME, PASSWORD);

                // Check-in yesterday, 1 night stay
                String[] dates = generateDateRange(-50, 5);

                mybrowser.findElement(By.id("location")).sendKeys("Sydney");
                mybrowser.findElement(By.id("hotels")).sendKeys("Hotel Creek");
                mybrowser.findElement(By.id("room_type")).sendKeys("Double");
                mybrowser.findElement(By.id("room_nos")).sendKeys("1 - One");
                
                WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                checkIn.clear();
                checkIn.sendKeys(dates[0]);
                
                WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                checkOut.clear();
                checkOut.sendKeys(dates[1]);
                
                mybrowser.findElement(By.id("adult_room")).sendKeys("2 - Two");
                mybrowser.findElement(By.id("child_room")).sendKeys("0 - None");
                mybrowser.findElement(By.id("Submit")).click();

                
                // Capture and verify error
                WebElement errorElement = mybrowser.findElement(By.xpath("//span[@id='checkin_span']"));
                String checkinError = errorElement.getText();
                Assert.assertEquals(checkinError.trim(), "Check-In Date cannot be in the past");


                // User should remain on the same page
                Assert.assertEquals(mybrowser.getTitle(), "Adactin.com - Select Hotel");
            }

            @Test
            public void TC3_3_Deluxe_1_Night_Today_minus_60() {
                String USERNAME = "abidselenium";
                String PASSWORD = "6D39GW";

                mybrowser = loginToApp(USERNAME, PASSWORD);

                // Check-in yesterday, 1 night stay
                String[] dates = generateDateRange(-50, 1);

                mybrowser.findElement(By.id("location")).sendKeys("Sydney");
                mybrowser.findElement(By.id("hotels")).sendKeys("Hotel Creek");
                mybrowser.findElement(By.id("room_type")).sendKeys("Deluxe");
                mybrowser.findElement(By.id("room_nos")).sendKeys("3 - Three");
                
                WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                checkIn.clear();
                checkIn.sendKeys(dates[0]);
                
                WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                checkOut.clear();
                checkOut.sendKeys(dates[1]);
                
                mybrowser.findElement(By.id("adult_room")).sendKeys("2 - Two");
                mybrowser.findElement(By.id("child_room")).sendKeys("0 - None");
                mybrowser.findElement(By.id("Submit")).click();

                // Capture and verify error
                WebElement errorElement = mybrowser.findElement(By.xpath("//span[@id='checkin_span']"));
                String checkinError = errorElement.getText();
                Assert.assertEquals(checkinError.trim(), "Check-In Date cannot be in the past");


                         
                // User should remain on the same page
                Assert.assertEquals(mybrowser.getTitle(), "Adactin.com - Select Hotel");
            }
            @Test
            public void TC4_10_SuperDeluxe_10_Night_Today_minus_2() {
                String USERNAME = "abidselenium";
                String PASSWORD = "6D39GW";

                mybrowser = loginToApp(USERNAME, PASSWORD);

                // Check-in yesterday, 1 night stay
                String[] dates = generateDateRange(-2, 10);

                mybrowser.findElement(By.id("location")).sendKeys("Sydney");
                mybrowser.findElement(By.id("hotels")).sendKeys("Hotel Creek");
                mybrowser.findElement(By.id("room_type")).sendKeys("Super Deluxe");
                mybrowser.findElement(By.id("room_nos")).sendKeys("10 - Ten");
                
                WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                checkIn.clear();
                checkIn.sendKeys(dates[0]);
                
                WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                checkOut.clear();
                checkOut.sendKeys(dates[1]);
                
                mybrowser.findElement(By.id("adult_room")).sendKeys("2 - Two");
                mybrowser.findElement(By.id("child_room")).sendKeys("0 - None");
                mybrowser.findElement(By.id("Submit")).click();

                // Capture and verify error
                WebElement errorElement = mybrowser.findElement(By.xpath("//span[@id='checkin_span']"));
                String checkinError = errorElement.getText();
                Assert.assertEquals(checkinError.trim(), "Check-In Date cannot be in the past");


                         
                // User should remain on the same page
                Assert.assertEquals(mybrowser.getTitle(), "Adactin.com - Select Hotel");
            }




    // ================ TEST CASES FOR INVALID DATE RANGE (FUTURE) ===================

            @Test
            public void TC5_1_Standard_1_Night_366_Days_Advance() {
                String USERNAME = "abidselenium";
                String PASSWORD = "6D39GW";

                mybrowser = loginToApp(USERNAME, PASSWORD);

                // Check-in 366 days from now, 1 night stay (exceeds 365-day booking limit)
                String[] dates = generateDateRange(366, 1);

                mybrowser.findElement(By.id("location")).sendKeys("Sydney");
                mybrowser.findElement(By.id("hotels")).sendKeys("Hotel Creek");
                mybrowser.findElement(By.id("room_type")).sendKeys("Standard");
                mybrowser.findElement(By.id("room_nos")).sendKeys("1 - One");
                
                WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                checkIn.clear();
                checkIn.sendKeys(dates[0]);
                
                WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                checkOut.clear();
                checkOut.sendKeys(dates[1]);
                
                mybrowser.findElement(By.id("adult_room")).sendKeys("1 - One");
                mybrowser.findElement(By.id("child_room")).sendKeys("0 - None");
                mybrowser.findElement(By.id("Submit")).click();

                // Capture and verify error
                WebElement errorElement = mybrowser.findElement(By.xpath("//span[@id='checkin_span']"));
                String checkinError = errorElement.getText();
                Assert.assertEquals(checkinError.trim(), "Booking cannot be made more than 365 days in advance");
                
                // User should remain on the same page
                Assert.assertEquals(mybrowser.getTitle(), "Adactin.com - Search Hotel");
            }

            @Test
            public void TC6_1_Double_5_Night_370_Days_Advance() {
                String USERNAME = "abidselenium";
                String PASSWORD = "6D39GW";

                mybrowser = loginToApp(USERNAME, PASSWORD);

                // Check-in 370 days from now, 5 nights stay (exceeds 365-day booking limit)
                String[] dates = generateDateRange(370, 5);

                mybrowser.findElement(By.id("location")).sendKeys("Sydney");
                mybrowser.findElement(By.id("hotels")).sendKeys("Hotel Creek");
                mybrowser.findElement(By.id("room_type")).sendKeys("Double");
                mybrowser.findElement(By.id("room_nos")).sendKeys("1 - One");
                
                WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                checkIn.clear();
                checkIn.sendKeys(dates[0]);
                
                WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                checkOut.clear();
                checkOut.sendKeys(dates[1]);
                
                mybrowser.findElement(By.id("adult_room")).sendKeys("2 - Two");
                mybrowser.findElement(By.id("child_room")).sendKeys("0 - None");
                mybrowser.findElement(By.id("Submit")).click();

                // Capture and verify error
                WebElement errorElement = mybrowser.findElement(By.xpath("//span[@id='checkin_span']"));
                String checkinError = errorElement.getText();
                Assert.assertEquals(checkinError.trim(), "Booking cannot be made more than 365 days in advance");
                
                // User should remain on the same page
                Assert.assertEquals(mybrowser.getTitle(), "Adactin.com - Search Hotel");
            }

            @Test
            public void TC7_3_Deluxe_10_Night_360_Days_Advance() {
                String USERNAME = "abidselenium";
                String PASSWORD = "6D39GW";

                mybrowser = loginToApp(USERNAME, PASSWORD);

                // Check-in 360 days from now, 10 nights stay (checkout at 370 days, exceeds limit)
                String[] dates = generateDateRange(360, 10);

                mybrowser.findElement(By.id("location")).sendKeys("Sydney");
                mybrowser.findElement(By.id("hotels")).sendKeys("Hotel Creek");
                mybrowser.findElement(By.id("room_type")).sendKeys("Deluxe");
                mybrowser.findElement(By.id("room_nos")).sendKeys("3 - Three");
                
                WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                checkIn.clear();
                checkIn.sendKeys(dates[0]);
                
                WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                checkOut.clear();
                checkOut.sendKeys(dates[1]);
                
                mybrowser.findElement(By.id("adult_room")).sendKeys("2 - Two");
                mybrowser.findElement(By.id("child_room")).sendKeys("0 - None");
                mybrowser.findElement(By.id("Submit")).click();

                // Capture and verify error
                WebElement errorElement = mybrowser.findElement(By.xpath("//span[@id='checkin_span']"));
                String checkinError = errorElement.getText();
                Assert.assertEquals(checkinError.trim(), "Booking cannot be made more than 365 days in advance");
                
                // User should remain on the same page
                Assert.assertEquals(mybrowser.getTitle(), "Adactin.com - Search Hotel");
            }

            @Test
            public void TC8_10_SuperDeluxe_370_Night_Past_to_Future() {
                String USERNAME = "abidselenium";
                String PASSWORD = "6D39GW";

                mybrowser = loginToApp(USERNAME, PASSWORD);

                // Check-in 2 days ago, 370 nights stay (checkout at 368 days in future)
                String[] dates = generateDateRange(-2, 370);

                mybrowser.findElement(By.id("location")).sendKeys("Sydney");
                mybrowser.findElement(By.id("hotels")).sendKeys("Hotel Creek");
                mybrowser.findElement(By.id("room_type")).sendKeys("Super Deluxe");
                mybrowser.findElement(By.id("room_nos")).sendKeys("10 - Ten");
                
                WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                checkIn.clear();
                checkIn.sendKeys(dates[0]);
                
                WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                checkOut.clear();
                checkOut.sendKeys(dates[1]);
                
                mybrowser.findElement(By.id("adult_room")).sendKeys("4 - Four");
                mybrowser.findElement(By.id("child_room")).sendKeys("0 - None");
                mybrowser.findElement(By.id("Submit")).click();

                // Capture and verify error - could be either past date error or advance booking error
                // Testing both possible error messages
                WebElement errorElement = mybrowser.findElement(By.xpath("//span[@id='checkin_span']"));
                String checkinError = errorElement.getText().trim();
                
                boolean isValidError = checkinError.equals("Check-In Date cannot be in the past") || 
                                      checkinError.equals("Booking cannot be made more than 365 days in advance");
                
                Assert.assertTrue(isValidError, "Expected error message about past date or advance booking, but got: " + checkinError);
                
                // User should remain on the same page
                Assert.assertEquals(mybrowser.getTitle(), "Adactin.com - Search Hotel");
            }

}
