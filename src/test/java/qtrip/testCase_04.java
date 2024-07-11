package qtrip;

import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import qtrip.pageObject.AdventureDetailsPage;
import qtrip.pageObject.AdventurePage;
import qtrip.pageObject.HistoryPage;
import qtrip.pageObject.HomePage;
import qtrip.pageObject.LoginPage;
import qtrip.pageObject.RegisterPage;
import resources.DP;

public class testCase_04 {

	

    static WebDriver driver;
    static ExtentReports reports;
    static ExtentTest test;
  
    public static String lastGenratedUsername;

    public static void logStatus(String type, String message, String status) {

        System.out.println(String.format("%s |  %s  |  %s | %s", String.valueOf(java.time.LocalDateTime.now()), type,
                message, status));
    }
    
    @BeforeTest(enabled = true)
    public static void createDriver() throws MalformedURLException {
    	logStatus("Driver", "Intialise the Driver 2 time", "Started");
    	WebDriverManager.chromedriver().setup();
    	driver = new ChromeDriver();
    	ReportSingleton rpt = ReportSingleton.getInstanceOfSingletonReport();
        reports = rpt.getReports();
        System.out.println("createDriver()");
         driver.manage().window().maximize();
        logStatus("driver", "Intializing Driver 2nd Time", "Success");
    
        
    }

    @Test(dataProvider = "data-provider", dataProviderClass = DP.class, priority = 4,groups = {"Reliability Flow"} ,enabled =  true)
    public void TestCase04(String username,String password,String DataSet1,String DataSet2,String DataSet3){
        Boolean status;
        try {
            test = reports.startTest("Reliablity Flow");
            logStatus("START TESTCASE 03 ", "Test Case 3: Verfication of Testcase03 : ", "Pass");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            RegisterPage registration = new RegisterPage(driver);
            registration.navigateRegisterPage(driver);
            //status = registration.RegisterNewUser("testu@hgjjfk", "abc@123", "abc@123",true);
            status = registration.RegisterNewUser(username, password, password,true);
            if(status){
                logStatus("Test Step", "User Perform Login Successfull ",  "PASS");
    
            }else{
                logStatus("Test Step", "User Perform Login NOT Successfull ",  "Failed");
            }
            lastGenratedUsername = registration.Email;
            LoginPage login = new LoginPage(driver);
            login.loginUser(lastGenratedUsername, password);

            // Conversion of Data Set Into the List of Data Set
            String[] DATA1 = DataSet1.split(";");
            String[] DATA2 = DataSet2.split(";");
            String[] DATA3 = DataSet3.split(";");

            List<String> data1 = Arrays.asList(DATA1);
            List<String> data2 = Arrays.asList(DATA2);
            List<String> data3 = Arrays.asList(DATA3);

            List<List<String>>userData = new ArrayList<>();
            userData.add(data1);
            userData.add(data2);
            userData.add(data3);
            HistoryPage history = new HistoryPage(driver);

            for (int i = 0; i <userData.size(); i++) {
                List<String> firstdata = new ArrayList<>();

                for (String element : userData.get(i)) {
                    firstdata.add(element);
                }


                    
                HomePage home = new HomePage(driver);
                home.searchCity(firstdata.get(0));
                home.SelectCity(firstdata.get(0));
                Thread.sleep(2000);

                AdventurePage adv = new AdventurePage(driver);
                adv.selectAdventure(firstdata.get(1));
                Thread.sleep(3000);

                AdventureDetailsPage advDetail = new AdventureDetailsPage(driver);
                advDetail.bookAdventure(firstdata.get(2), firstdata.get(3), firstdata.get(4));
                status = advDetail.isBookingSuccessfull();
                logStatus("Test Step", "Adventure Booking Successfull" ,status ? "PASS" :"Failed");
            //Assert.assertTrue(status, "Booking is not Successfull");
                history.getReservations();
                home.navigateToHomePage();
            }
            history.getReservations();
        
            List<WebElement> verifyReg = driver.findElements(By.xpath("//table/tbody/tr/th"));
            status = verifyReg.size()==userData.size();
            logStatus("Test Step", "Verify that Booking history is Viewed", status ? "Pass":"Failed");

            logStatus("TestCase End", "Booking History Flow Successully Verified", "Passed");
            driver.close();
        } catch (Exception e) {
            //TODO: handle exception
            logStatus("PageTest04", "TestCase04 Validation", "Failed");
            e.printStackTrace();
        }
    }

    @AfterTest(enabled = true)
    public static void quitDriver() {
        System.out.println("quit()");
        driver.quit();
        reports.flush();
    }

}
