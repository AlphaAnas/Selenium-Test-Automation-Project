package tests;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        
        /*
         * Structure of Test cases in the file is :
         *          \
         *          |_ ONE ROOM ONE NIGH
         *          |_ ONE ROOM MULTI NIGHT
         *          |_ MULTI ROOM ONE NIGHT
         *          |_ MULTI ROOM MULTI NIGHT
         * 
         *         ( IN INVALID_DATE_SEARCHHOTEL.java file )
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
                
                mybrowser = loginToApp("abidselenium", "6D39GW");

                // Generate dates: check-in today, 1 night stay
                String[] dates = generateDateRange(0, 1);
                String today = dates[0];
                String tomorrow = dates[1];

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
            public void TC2_DoubleRoom_1_Night_NextMonth(){
                
                mybrowser = loginToApp("abidselenium", "6D39GW");

                // Generate dates: check-in 44 days from now (next month), 1 night stay
                String[] dates = generateDateRange(44, 1);

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
                    checkIn.sendKeys(dates[0]);


                // CHECKOUT DATE (TEXT)
                    WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                    checkOut.clear();
                    checkOut.sendKeys(dates[1]);

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
                Assert.assertEquals(checkInDate, dates[0]);
                Assert.assertEquals(checkOutDate, dates[1]);
                Assert.assertEquals(rate, "AUD $ 225");
                Assert.assertEquals(total, "AUD $ 225");

                // Browser will be closed automatically by @AfterMethod
            }
            @Test
            public void TC3_DeluxeRoom_1_Night_NextYear(){
                
                mybrowser = loginToApp("abidselenium", "6D39GW");

                // Generate dates: check-in 165 days from now (next year), 1 night stay
                String[] dates = generateDateRange(165, 1);

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
                    room_dropdown.selectByVisibleText("Deluxe");

                // NUMBER OF ROOMS IN DROPDOWN
                    WebElement numberOfRoomsDropDown = mybrowser.findElement((By.id("room_nos")));
                    Select num_rooms = new Select(numberOfRoomsDropDown);
                    num_rooms.selectByVisibleText("1 - One");

                // CHECK-IN DATE (TEXT FIELD)
                    WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                    checkIn.clear();
                    checkIn.sendKeys(dates[0]);


                // CHECKOUT DATE (TEXT)
                    WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                    checkOut.clear();
                    checkOut.sendKeys(dates[1]);

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
                Assert.assertEquals(roomType, "Deluxe");
                Assert.assertEquals(checkInDate, dates[0]);
                Assert.assertEquals(checkOutDate, dates[1]);
                Assert.assertEquals(rate, "AUD $ 325");
                Assert.assertEquals(total, "AUD $ 325");

                // Browser will be closed automatically by @AfterMethod
            }
            @Test
            public void TC4_SuperDeluxeRoom_1_Night_365th_Day(){
                
                mybrowser = loginToApp("abidselenium", "6D39GW");

                // Generate dates: check-in 364 days from now, 1 night stay
                String[] dates = generateDateRange(364, 1);

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
                    room_dropdown.selectByVisibleText("Super Deluxe");

                // NUMBER OF ROOMS IN DROPDOWN
                    WebElement numberOfRoomsDropDown = mybrowser.findElement((By.id("room_nos")));
                    Select num_rooms = new Select(numberOfRoomsDropDown);
                    num_rooms.selectByVisibleText("1 - One");

                // CHECK-IN DATE (TEXT FIELD)
                    WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                    checkIn.clear();
                    checkIn.sendKeys(dates[0]);


                // CHECKOUT DATE (TEXT)
                    WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                    checkOut.clear();
                    checkOut.sendKeys(dates[1]);

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
                Assert.assertEquals(roomType, "Super Deluxe");
                Assert.assertEquals(checkInDate, dates[0]);
                Assert.assertEquals(checkOutDate, dates[1]);
                Assert.assertEquals(rate, "AUD $ 425");
                Assert.assertEquals(total, "AUD $ 425");

                // Browser will be closed automatically by @AfterMethod
            }


        // ====================== END OF ONE ROOM ONE NIGHT ==========================


        // ====================== ONE ROOM MULTI NIGHT ==========================
            @Test
            public void TC5_StandardRoom_2_Nights_Today(){
                
                mybrowser = loginToApp("abidselenium", "6D39GW");

                // Generate dates: check-in today, 2 nights stay
                String[] dates = generateDateRange(0, 2);

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
                    checkIn.sendKeys(dates[0]);


                // CHECKOUT DATE (TEXT)
                    WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                    checkOut.clear();
                    checkOut.sendKeys(dates[1]);

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
                Assert.assertEquals(checkInDate, dates[0]);
                Assert.assertEquals(checkOutDate, dates[1]);
                Assert.assertEquals(rate, "AUD $ 125");
                Assert.assertEquals(total, "AUD $ 250");

                // Browser will be closed automatically by @AfterMethod
            }
            @Test
            public void TC6_DoubleRoom_10_Nights_NextWeek(){
                mybrowser = loginToApp("abidselenium", "6D39GW");

                // Generate dates: check-in 21 days from now (next week), 10 nights stay
                String[] dates = generateDateRange(21, 10);

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
                    checkIn.sendKeys(dates[0]);


                // CHECKOUT DATE (TEXT)
                    WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                    checkOut.clear();
                    checkOut.sendKeys(dates[1]);

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
                Assert.assertEquals(checkInDate, dates[0]);
                Assert.assertEquals(checkOutDate, dates[1]);
                Assert.assertEquals(rate, "AUD $ 225");
                Assert.assertEquals(total, "AUD $ 2250");

                // Browser will be closed automatically by @AfterMethod
            }
            @Test
            public void TC7_DeluxeRoom_200_Nights_NextMonth(){
                mybrowser = loginToApp("abidselenium", "6D39GW");

                // Generate dates: check-in 44 days from now (next month), 200 nights stay
                String[] dates = generateDateRange(44, 200);

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
                    room_dropdown.selectByVisibleText("Deluxe");

                // NUMBER OF ROOMS IN DROPDOWN
                    WebElement numberOfRoomsDropDown = mybrowser.findElement((By.id("room_nos")));
                    Select num_rooms = new Select(numberOfRoomsDropDown);
                    num_rooms.selectByVisibleText("1 - One");

                // CHECK-IN DATE (TEXT FIELD)
                    WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                    checkIn.clear();
                    checkIn.sendKeys(dates[0]);


                // CHECKOUT DATE (TEXT)
                    WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                    checkOut.clear();
                    checkOut.sendKeys(dates[1]);

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
                Assert.assertEquals(roomType, "Deluxe");
                Assert.assertEquals(checkInDate, dates[0]);
                Assert.assertEquals(checkOutDate, dates[1]);
                Assert.assertEquals(rate, "AUD $ 325");
                Assert.assertEquals(total, "AUD $ 65000");

                // Browser will be closed automatically by @AfterMethod
            }
            @Test
            public void TC8_SuperDeluxeRoom_365_Nights_StartToday(){
                mybrowser = loginToApp("abidselenium", "6D39GW");

                // Generate dates: check-in today, 365 nights stay
                String[] dates = generateDateRange(0, 365);

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
                    room_dropdown.selectByVisibleText("Super Deluxe");

                // NUMBER OF ROOMS IN DROPDOWN
                    WebElement numberOfRoomsDropDown = mybrowser.findElement((By.id("room_nos")));
                    Select num_rooms = new Select(numberOfRoomsDropDown);
                    num_rooms.selectByVisibleText("1 - One");

                // CHECK-IN DATE (TEXT FIELD)
                    WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                    checkIn.clear();
                    checkIn.sendKeys(dates[0]);


                // CHECKOUT DATE (TEXT)
                    WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                    checkOut.clear();
                    checkOut.sendKeys(dates[1]);

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
                Assert.assertEquals(roomType, "Super Deluxe");
                Assert.assertEquals(checkInDate, dates[0]);
                Assert.assertEquals(checkOutDate, dates[1]);
                Assert.assertEquals(rate, "AUD $ 425");
                Assert.assertEquals(total, "AUD $ 155125");

                // Browser will be closed automatically by @AfterMethod
            }

        // ====================== END OF ONE ROOM MULTI NIGHT ==========================


        //====================== MultiROOM ONE NIGHT STAY =============================

            @Test
            public void TC9_2_StandardRoom_1_Night_Today(){
                
                mybrowser = loginToApp("abidselenium", "6D39GW");

                // Generate dates: check-in today, 1 night stay
                String[] dates = generateDateRange(0, 1);

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
                    num_rooms.selectByVisibleText("2 - Two");

                // CHECK-IN DATE (TEXT FIELD)
                    WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                    checkIn.clear();
                    checkIn.sendKeys(dates[0]);

                // CHECK-OUT DATE (TEXT FIELD)
                    WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                    checkOut.clear();
                    checkOut.sendKeys(dates[1]);

                // NUMBER OF ADULTS IN DROPDOWN
                    WebElement numberOfAdultsDropDown = mybrowser.findElement((By.id("adult_room")));
                    Select num_adults = new Select(numberOfAdultsDropDown);
                    num_adults.selectByVisibleText("2 - Two");

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
                Assert.assertEquals(checkInDate, dates[0]);
                Assert.assertEquals(checkOutDate, dates[1]);
                Assert.assertEquals(rate, "AUD $ 125");
                Assert.assertEquals(total, "AUD $ 250");

                // Browser will be closed automatically by @AfterMethod
            }

            @Test
            public void TC10_5_DoubleRoom_1_Night_NextMonth(){

                mybrowser = loginToApp("abidselenium", "6D39GW");

                // Generate dates: check-in 44 days from now (next month), 1 night stay
                String[] dates = generateDateRange(44, 1);

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
                    num_rooms.selectByVisibleText("5 - Five");

                // CHECK-IN DATE (TEXT FIELD)
                    WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                    checkIn.clear();
                    checkIn.sendKeys(dates[0]);

                // CHECK-OUT DATE (TEXT FIELD)
                    WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                    checkOut.clear();
                    checkOut.sendKeys(dates[1]);

                // NUMBER OF ADULTS IN DROPDOWN
                    WebElement numberOfAdultsDropDown = mybrowser.findElement((By.id("adult_room")));
                    Select num_adults = new Select(numberOfAdultsDropDown);
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
                Assert.assertEquals(checkInDate, dates[0]);
                Assert.assertEquals(checkOutDate, dates[1]);
                Assert.assertEquals(rate, "AUD $ 225");
                Assert.assertEquals(total, "AUD $ 1125");

                // Browser will be closed automatically by @AfterMethod
            }
            @Test
            public void TC11_9_DeluxeRoom_1_Night_NextAug(){

                mybrowser = loginToApp("abidselenium", "6D39GW");

                // Generate dates: check-in 266 days from now (next August), 1 night stay
                String[] dates = generateDateRange(266, 1);

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
                    room_dropdown.selectByVisibleText("Deluxe");

                // NUMBER OF ROOMS IN DROPDOWN
                    WebElement numberOfRoomsDropDown = mybrowser.findElement((By.id("room_nos")));
                    Select num_rooms = new Select(numberOfRoomsDropDown);
                    num_rooms.selectByVisibleText("9 - Nine");

                // CHECK-IN DATE (TEXT FIELD)
                    WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                    checkIn.clear();
                    checkIn.sendKeys(dates[0]);

                // CHECK-OUT DATE (TEXT FIELD)
                    WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                    checkOut.clear();
                    checkOut.sendKeys(dates[1]);

                // NUMBER OF ADULTS IN DROPDOWN
                    WebElement numberOfAdultsDropDown = mybrowser.findElement((By.id("adult_room")));
                    Select num_adults = new Select(numberOfAdultsDropDown);
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
                Assert.assertEquals(roomType, "Deluxe");
                Assert.assertEquals(checkInDate, dates[0]);
                Assert.assertEquals(checkOutDate, dates[1]);
                Assert.assertEquals(rate, "AUD $ 325");
                Assert.assertEquals(total, "AUD $ 2925");

                // Browser will be closed automatically by @AfterMethod
            }
            @Test
            public void TC12_10_SuperDeluxeRoom_1_Night_364th_Day(){

                mybrowser = loginToApp("abidselenium", "6D39GW");

                // Generate dates: check-in 364 days from now, 1 night stay
                String[] dates = generateDateRange(364, 1);

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
                    room_dropdown.selectByVisibleText("Super Deluxe");

                // NUMBER OF ROOMS IN DROPDOWN
                    WebElement numberOfRoomsDropDown = mybrowser.findElement((By.id("room_nos")));
                    Select num_rooms = new Select(numberOfRoomsDropDown);
                    num_rooms.selectByVisibleText("10 - Ten");

                // CHECK-IN DATE (TEXT FIELD)
                    WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                    checkIn.clear();
                    checkIn.sendKeys(dates[0]);

                // CHECK-OUT DATE (TEXT FIELD)
                    WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                    checkOut.clear();
                    checkOut.sendKeys(dates[1]);

                // NUMBER OF ADULTS IN DROPDOWN
                    WebElement numberOfAdultsDropDown = mybrowser.findElement((By.id("adult_room")));
                    Select num_adults = new Select(numberOfAdultsDropDown);
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
                Assert.assertEquals(roomType, "Super Deluxe");
                Assert.assertEquals(checkInDate, dates[0]);
                Assert.assertEquals(checkOutDate, dates[1]);
                Assert.assertEquals(rate, "AUD $ 425");
                Assert.assertEquals(total, "AUD $ 4250");

                // Browser will be closed automatically by @AfterMethod
            }
            // ========================== END OF MULTI ROOM ONE NIGHT ============================


            // ========================== MULTI ROOM MULTI NIGHT ============================
            @Test
            public void TC13_2_StandardRoom_5_Night_Today(){

                mybrowser = loginToApp("abidselenium", "6D39GW");

                // Generate dates: check-in today, 5 nights stay
                String[] dates = generateDateRange(0, 5);

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
                    num_rooms.selectByVisibleText("2 - Two");

                // CHECK-IN DATE (TEXT FIELD)
                    WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                    checkIn.clear();
                    checkIn.sendKeys(dates[0]);

                // CHECK-OUT DATE (TEXT FIELD)
                    WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                    checkOut.clear();
                    checkOut.sendKeys(dates[1]);

                // NUMBER OF ADULTS IN DROPDOWN
                    WebElement numberOfAdultsDropDown = mybrowser.findElement((By.id("adult_room")));
                    Select num_adults = new Select(numberOfAdultsDropDown);
                    num_adults.selectByVisibleText("2 - Two");

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
                Assert.assertEquals(checkInDate, dates[0]);
                Assert.assertEquals(checkOutDate, dates[1]);
                Assert.assertEquals(rate, "AUD $ 125");
                Assert.assertEquals(total, "AUD $ 1250");

                // Browser will be closed automatically by @AfterMethod
            }
            @Test
            public void TC14_4_DoubleRoom_10_Night_After_3_Months(){

                mybrowser = loginToApp("abidselenium", "6D39GW");

                // Generate dates: check-in today, 10 nights stay after 4 months
                String[] dates = generateDateRange(120, 10);

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
                    num_rooms.selectByVisibleText("4 - Four");

                // CHECK-IN DATE (TEXT FIELD)
                    WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                    checkIn.clear();
                    checkIn.sendKeys(dates[0]);

                // CHECK-OUT DATE (TEXT FIELD)
                    WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                    checkOut.clear();
                    checkOut.sendKeys(dates[1]);

                // NUMBER OF ADULTS IN DROPDOWN
                    WebElement numberOfAdultsDropDown = mybrowser.findElement((By.id("adult_room")));
                    Select num_adults = new Select(numberOfAdultsDropDown);
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
                Assert.assertEquals(checkInDate, dates[0]);
                Assert.assertEquals(checkOutDate, dates[1]);
                Assert.assertEquals(rate, "AUD $ 225");
                Assert.assertEquals(total, "AUD $ 9000");

                // Browser will be closed automatically by @AfterMethod
            }
            @Test
            public void TC15_5_DeluxeRoom_200_Night_After_1_Month(){

                mybrowser = loginToApp("abidselenium", "6D39GW");

                // Generate dates: check-in today, 200 nights stay after 1 month
                String[] dates = generateDateRange(30, 200);

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
                    room_dropdown.selectByVisibleText("Deluxe");

                // NUMBER OF ROOMS IN DROPDOWN
                    WebElement numberOfRoomsDropDown = mybrowser.findElement((By.id("room_nos")));
                    Select num_rooms = new Select(numberOfRoomsDropDown);
                    num_rooms.selectByVisibleText("5 - Five");

                // CHECK-IN DATE (TEXT FIELD)
                    WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                    checkIn.clear();
                    checkIn.sendKeys(dates[0]);

                // CHECK-OUT DATE (TEXT FIELD)
                    WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                    checkOut.clear();
                    checkOut.sendKeys(dates[1]);

                // NUMBER OF ADULTS IN DROPDOWN
                    WebElement numberOfAdultsDropDown = mybrowser.findElement((By.id("adult_room")));
                    Select num_adults = new Select(numberOfAdultsDropDown);
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
                Assert.assertEquals(roomType, "Deluxe");
                Assert.assertEquals(checkInDate, dates[0]);
                Assert.assertEquals(checkOutDate, dates[1]);
                Assert.assertEquals(rate, "AUD $ 325");
                Assert.assertEquals(total, "AUD $ 325000");

                // Browser will be closed automatically by @AfterMethod
            }
            @Test
            public void TC16_10_SuperDeluxeRoom_365_Night_Today(){

                mybrowser = loginToApp("abidselenium", "6D39GW");

                // Generate dates: check-in today, 365 nights stay
                String[] dates = generateDateRange(0, 365);

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
                    room_dropdown.selectByVisibleText("Super Deluxe");

                // NUMBER OF ROOMS IN DROPDOWN
                    WebElement numberOfRoomsDropDown = mybrowser.findElement((By.id("room_nos")));
                    Select num_rooms = new Select(numberOfRoomsDropDown);
                    num_rooms.selectByVisibleText("10 - Ten");

                // CHECK-IN DATE (TEXT FIELD)
                    WebElement checkIn = mybrowser.findElement(By.id("datepick_in"));
                    checkIn.clear();
                    checkIn.sendKeys(dates[0]);

                // CHECK-OUT DATE (TEXT FIELD)
                    WebElement checkOut = mybrowser.findElement(By.id("datepick_out"));
                    checkOut.clear();
                    checkOut.sendKeys(dates[1]);

                // NUMBER OF ADULTS IN DROPDOWN
                    WebElement numberOfAdultsDropDown = mybrowser.findElement((By.id("adult_room")));
                    Select num_adults = new Select(numberOfAdultsDropDown);
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
                Assert.assertEquals(roomType, "Super Deluxe");
                Assert.assertEquals(checkInDate, dates[0]);
                Assert.assertEquals(checkOutDate, dates[1]);
                Assert.assertEquals(rate, "AUD $ 425");
                Assert.assertEquals(total, "AUD $ 1551250");

                // Browser will be closed automatically by @AfterMethod
            }




     
}
