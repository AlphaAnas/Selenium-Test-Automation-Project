package tests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class searchHotel {
    
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

        
        /*
         * Structure of Test cases in the file is :
         *          \
         *          |_ ONE ROOM ONE NIGH
         *          |_ ONE ROOM MULTI NIGHT
         *          |_ MULTI ROOM ONE NIGHT
         *          |_ MULTI ROOM MULTI NIGHT
         *          |_ INVALID DATE RANGE (PAST)
         *          |_ INVALID DATE RANGE (FUTURE)
         *          |_ INVALID DATE FORMAT (FUTURE)
         *          |_ DROPDOWNS DATA
         *          |_ VERIFYING GUI AGAINST WIREFRAMES
         *          \
         */

        // ====================== ONE ROOM ONE NIGHT ==========================
            @Test
            public void TC1_StandardRoom_1_Night_Current_Month(){
                
                mybrowser = loginToApp("abidselenium", "6D39GW"); // Use instance variable

                //  =============USE DYNAMIC DATES DIRECTLY 
                String today =  java.time.LocalDate.now().toString(); // YYYY-MM-DD
                // convert the DD/MM/YYYY
                String[] todayParts = today.split("-");
                today = todayParts[2] + "/" + todayParts[1] + "/" + todayParts[0];
                String tomorrow = java.time.LocalDate.now().plusDays(1).toString(); // YYYY-MM-DD
                String[] tomorrowParts = tomorrow.split("-");
                tomorrow = tomorrowParts[2] + "/" + tomorrowParts[1] + "/" + tomorrowParts[0];
                // ========================================


                // LOCATION IN DROPDOWN
                    WebElement locationDropDown = mybrowser.findElement((By.id("location")));
                    Select loc_dropdown = new Select(locationDropDown);
                    loc_dropdown.selectByVisibleText("Sydney");
                // HOTEL NAME IN DROPDOWN

                    WebElement hotelNameDropdown = mybrowser.findElement((By.id("hotels")));
                    Select hotel_dropdown = new Select(hotelNameDropdown);
                    hotel_dropdown.selectByVisibleText("Hotel Creek");

                // ROOM TYPE IN DROPDOWN
                    WebElement roomTypeDropdown = mybrowser.findElement((By.id("room_type")));
                    Select room_dropdown = new Select(roomTypeDropdown);
                    room_dropdown.selectByVisibleText("Standard");

                // NUMBER OF ROOMS IN DROPDOWN
                    WebElement numberOfRoomsDropDown = mybrowser.findElement((By.id("room_nos")));
                    Select num_rooms = new Select(numberOfRoomsDropDown);
                    num_rooms.selectByVisibleText("1 - One");

                // CHECK-IN DATE (TEXT FIELD)
                    WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                    checkIn.clear();
                    checkIn.sendKeys(today);


                // CHECKOUT DATE (TEXT)
                    WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                    checkOut.clear();
                    checkOut.sendKeys(tomorrow);

                // ADULTS PER ROOM DROPDOWN

                    WebElement noOfAdults = mybrowser.findElement(By.id("adult_room"));
                    Select num_adults = new Select(noOfAdults);
                    num_adults.selectByVisibleText("1 - One");

                // PRESS SEARCH BUTTON
                    mybrowser.findElement(By.id("Submit")).click();

                // verify REDIRECT TO VIEW HOTEL PAGE
                // VERIFY SEARCH RESULT PAGE
                Assert.assertEquals(mybrowser.getTitle(), "Adactin.com - Select Hotel");


                // ====================================
                    // CHECK THE DATA IN THE GRID

                // LOCATION • HOTEL • ROOM • CHECKIN • CHECKOUT • RATE • TOTAL
                    // VERIFY DATA IN GRID
                String hotelName = mybrowser.findElement(By.id("hotel_name_0")).getAttribute("value");
                String location = mybrowser.findElement(By.id("location_0")).getAttribute("value");
                String roomType = mybrowser.findElement(By.id("room_type_0")).getAttribute("value");
                String checkInDate = mybrowser.findElement(By.id("arr_date_0")).getAttribute("value");
                String checkOutDate = mybrowser.findElement(By.id("dep_date_0")).getAttribute("value");
                String rate = mybrowser.findElement(By.id("price_night_0")).getAttribute("value");
                String total = mybrowser.findElement(By.id("total_price_0")).getAttribute("value");

                Assert.assertEquals(location, "Sydney");
                Assert.assertEquals(hotelName, "Hotel Creek");
                Assert.assertEquals(roomType, "Standard");
                Assert.assertEquals(checkInDate, today);
                Assert.assertEquals(checkOutDate, tomorrow);
                Assert.assertEquals(rate, "AUD $ 125");
                Assert.assertEquals(total, "AUD $ 125");

                // Browser will be closed automatically by @AfterMethod
            }
            @Test
            public void TC2_DoubleRoom_1_Night_NextYear(){
                
                mybrowser = loginToApp("abidselenium", "6D39GW"); // Use instance variable

                // LOCATION IN DROPDOWN
                    WebElement locationDropDown = mybrowser.findElement((By.id("location")));
                    Select loc_dropdown = new Select(locationDropDown);
                    loc_dropdown.selectByVisibleText("Sydney");
                // HOTEL NAME IN DROPDOWN

                    WebElement hotelNameDropdown = mybrowser.findElement((By.id("hotels")));
                    Select hotel_dropdown = new Select(hotelNameDropdown);
                    hotel_dropdown.selectByVisibleText("Hotel Creek");

                // ROOM TYPE IN DROPDOWN
                    WebElement roomTypeDropdown = mybrowser.findElement((By.id("room_type")));
                    Select room_dropdown = new Select(roomTypeDropdown);
                    room_dropdown.selectByVisibleText("Double");

                // NUMBER OF ROOMS IN DROPDOWN
                    WebElement numberOfRoomsDropDown = mybrowser.findElement((By.id("room_nos")));
                    Select num_rooms = new Select(numberOfRoomsDropDown);
                    num_rooms.selectByVisibleText("1 - One");

                // CHECK-IN DATE (TEXT FIELD)
                    WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                    checkIn.clear();
                    checkIn.sendKeys("22/12/2025");


                // CHECKOUT DATE (TEXT)
                    WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                    checkOut.clear();
                    checkOut.sendKeys("23/12/2025");

                // ADULTS PER ROOM DROPDOWN

                    WebElement noOfAdults = mybrowser.findElement(By.id("adult_room"));
                    Select num_adults = new Select(noOfAdults);
                    num_adults.selectByVisibleText("1 - One");

                // PRESS SEARCH BUTTON
                    mybrowser.findElement(By.id("Submit")).click();

                // verify REDIRECT TO VIEW HOTEL PAGE
                // VERIFY SEARCH RESULT PAGE
                Assert.assertEquals(mybrowser.getTitle(), "Adactin.com - Select Hotel");


                // ====================================
                    // CHECK THE DATA IN THE GRID

                // LOCATION • HOTEL • ROOM • CHECKIN • CHECKOUT • RATE • TOTAL
                    // VERIFY DATA IN GRID
                String hotelName = mybrowser.findElement(By.id("hotel_name_0")).getAttribute("value");
                String location = mybrowser.findElement(By.id("location_0")).getAttribute("value");
                String roomType = mybrowser.findElement(By.id("room_type_0")).getAttribute("value");
                String checkInDate = mybrowser.findElement(By.id("arr_date_0")).getAttribute("value");
                String checkOutDate = mybrowser.findElement(By.id("dep_date_0")).getAttribute("value");
                String rate = mybrowser.findElement(By.id("price_night_0")).getAttribute("value");
                String total = mybrowser.findElement(By.id("total_price_0")).getAttribute("value");

                Assert.assertEquals(location, "Sydney");
                Assert.assertEquals(hotelName, "Hotel Creek");
                Assert.assertEquals(roomType, "Double");
                Assert.assertEquals(checkInDate, "22/12/2025");
                Assert.assertEquals(checkOutDate, "23/12/2025");
                Assert.assertEquals(rate, "AUD $ 225");
                Assert.assertEquals(total, "AUD $ 225");

                // Browser will be closed automatically by @AfterMethod
            }

          



            


}
