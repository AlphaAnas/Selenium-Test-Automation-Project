package tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
    // ================ TEST CASES FOR INVALID DATE RANGE (FUTURE) ===================\

            @Test
            public void TC1_1_StandardRoom_Checkin_After_365_Days(){
                String USERNAME = "abidselenium";
                String PASSWORD = "6D39GW";

                mybrowser = loginToApp(USERNAME, PASSWORD);

                String[] dates = generateDateRange(366, 1); // Check-in after 365 days, 1 night stay

                mybrowser.findElement(By.id("location")).sendKeys("Sydney");
                mybrowser.findElement(By.id("hotels")).sendKeys("Hotel Creek");
                mybrowser.findElement(By.id("room_type")).sendKeys("Standard");
                mybrowser.findElement(By.id("room_nos")).sendKeys("1 - One");
                mybrowser.findElement(By.id("datepick_in")).clear();
                mybrowser.findElement(By.id("datepick_in")).sendKeys(dates[0]);
                mybrowser.findElement(By.id("datepick_out")).clear();
                mybrowser.findElement(By.id("datepick_out")).sendKeys(dates[1]);
                mybrowser.findElement(By.id("adult_room")).sendKeys("2 - Two");
                mybrowser.findElement(By.id("child_room")).sendKeys("0 - None");
                mybrowser.findElement(By.id("Submit")).click();

            //    Error 
       
            }
}
