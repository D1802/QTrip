package qtrip;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.MalformedURLException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;
import qtrip.pageObject.AdventureDetailsPage;
import qtrip.pageObject.AdventurePage;
import qtrip.pageObject.HistoryPage;
import qtrip.pageObject.HomePage;
import qtrip.pageObject.LoginPage;
import qtrip.pageObject.RegisterPage;
import resources.DP;

public class testCase_03 {

	  static WebDriver driver;
	    static ExtentReports reports;
	    static ExtentTest test;
	    public static String lasttransactionID;
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
	    @Test(description = "Booking and Cancellation Flow",dataProvider = "data-provider", dataProviderClass = DP.class, priority = 3,groups = {"Booking and Cancellation Flow"} ,enabled =  true)
	 
	   public  void TestCase03(String NewUserName,String password,String searchcity,String adventurName,String GuestName,String DATE,String COUNT){
	   // @Test
	    //public  void TestCase03(){
	        Boolean status;
	        try {
	            test = reports.startTest("Booking and Cancellation Flow");
	            logStatus("START TESTCASE 03 ", "Test Case 3: Verfication of Testcase03 : ", "Pass");

	            RegisterPage registration = new RegisterPage(driver);
	            registration.navigateRegisterPage(driver);
	            //status = registration.RegisterNewUser("TC3_3@gmail.com", "abc@123", "abc@123",true);
	            status = registration.RegisterNewUser(NewUserName, password, password,true);
	            if(status){
	                //logStatus("Test Step", "User Perform Login Successfull ",  "PASS");
	                test.log(LogStatus.PASS,"User Perform Registation Successfull");
	    
	            }else{
	                //logStatus("Test Step", "User Perform Login NOT Successfull ",  "Failed");
	            	test.log(LogStatus.FAIL, "User Perform Registration Unsuccessfull");
	                //test.log(LogStatus.FAIL,SeleniumWrapper.capture(driver)+"User Perform Registration Unsuccessfull");
	            }
	            lastGenratedUsername = registration.Email;
	            LoginPage login = new LoginPage(driver);
	            login.loginUser(lastGenratedUsername, password);
	            //login.loginUser(lastGenratedUsername, "abc@123");
	            HomePage home = new HomePage(driver);
	             home.searchCity(searchcity);
	             home.SelectCity(searchcity);

	            //   home.searchCity("Bangkok");
	            //           // Create Actions instanc
	            //   home.SelectCity("Bangkok");
	             Thread.sleep(2000);

	            AdventurePage adv = new AdventurePage(driver);
	            adv.selectAdventure(adventurName);
	            //adv.selectAdventure("Tifwales Ferry");
	            Thread.sleep(3000);

	            AdventureDetailsPage advDetail = new AdventureDetailsPage(driver);
	            advDetail.bookAdventure(GuestName, DATE, COUNT);
	            //advDetail.bookAdventure("prabhu", "26-12-2025", "1");
	            status = advDetail.isBookingSuccessfull();
	            if (status) {
	                test.log(LogStatus.PASS, "Booking is SuccessFull");
	            } else {
	                test.log(LogStatus.FAIL, "Booking is NOT SuccessFull");
	            }
	            //logStatus("Test Step", "Adventure Booking Successfull" ,status ? "PASS" :"Failed");
	            //Assert.assertTrue(status, "Booking is not Successfull");

	            HistoryPage history = new HistoryPage(driver);
	            history.getReservations();

	            lasttransactionID = history.transactionID;
	            status = history.cancelReservations();

	            if (status) {
	                test.log(LogStatus.PASS, "Booking CANCEL SuccessFull");
	            } else {
	                test.log(LogStatus.FAIL, "Booking is NOT cANCEL SuccessFull");
	            }
	            //logStatus("Test Step", "Cancel Adventure Booking Successfull" ,status ? "PASS" :"Failed");

	            logStatus("End of TestCase03", "TestCase3 end Successfully" , "PASS");
	            


	            
	        } catch (Exception e) {
	            //TODO: handle exception
	            logStatus("PageTest03", "TestCase03 Validation", "Failed");
	            e.printStackTrace();
	        }
	    

	    }

	      
	    @AfterTest(enabled =true)
	    public static void quitDriver() {
	        System.out.println("quit()");
	        driver.quit();
	        reports.flush();
	    }


}
