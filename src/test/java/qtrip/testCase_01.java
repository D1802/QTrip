package qtrip;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import qtrip.pageObject.LoginPage;
import qtrip.pageObject.RegisterPage;
import resources.DP;

public class testCase_01 {
	  
    static WebDriver driver;
    static ExtentReports reports;
    static ExtentTest test;

    
    public static String lastGeneratedUserName;
    
    @BeforeTest(enabled = true)
    public static void createDriver() throws MalformedURLException {
        // Launch Browser using Zalenium
//    	WebDriverManager.chromedriver().setup();
//    	driver = new ChromeDriver();
        DriverSingleton singleton = DriverSingleton.getInstanceOfSingleTonBrowserClass();
        driver = singleton.getDriver();
    	ReportSingleton rpt = ReportSingleton.getInstanceOfSingletonReport();
        reports = rpt.getReports();
        System.out.println("createDriver()");
         //driver.manage().window().maximize();
        logStatus("driver", "Intializing Driver", "Success");
        
    }


    public static void logStatus(String type, String message, String status) {

        System.out.println(String.format("%s |  %s  |  %s | %s", String.valueOf(java.time.LocalDateTime.now()), type,
                message, status));
    }

    //(description = "Verify user  Registration- Login-Logout",dataProvider = "data-provider",dataProviderClass = DP.class)
    //String username, String password

    @Test(description = "Verify user  Registration- Login-Logout",dataProvider = "data-provider", dataProviderClass = DP.class, priority = 1, groups = {"Login Flow"},enabled =  true)
    public  void TestCase01(String username, String password) throws InterruptedException{
        Boolean status;
        try {
        test = reports.startTest("Verify user  Registration- Login-Logout");
        logStatus("Start TestCase", "Test Case 1: Verify User Registration", "DONE");
     
        RegisterPage registration = new RegisterPage(driver);
        registration.navigateRegisterPage(driver);
        //status = registration.RegisterNewUser("testu@hgjjfk", "abc@123", "abc@123",true);
        status = registration.RegisterNewUser(username, password, password,true);
        if(status){
            //logStatus("Test Step", "User Perform Login Successfull ",  "PASS");
        	test.log(LogStatus.PASS, "User Perform Registation Successfull");

        }else{
            //logStatus("Test Step", "User Perform Login NOT Successfull ",  "Failed");
            test.log(LogStatus.FAIL, "User Perform Login Unsuccessfull");
        }
        // Save the last generated username
        lastGeneratedUserName = registration.Email;

        // Visit the login page and login with the previuosly registered user
        LoginPage login = new LoginPage(driver);
        login.navigateToLoginPage();
        System.out.println(lastGeneratedUserName);
        login.loginUser(lastGeneratedUserName,"abc@123");
        //Assert.assertTrue(status, "Failed to login with registered user");

        //logStatus("Test Step", "User Perform Login Successfull ", "PASS");
        test.log(LogStatus.PASS, "User Perform Login Successfull");

        // Visit the home page and log out the logged in user
    
        login.LogOutUser();
        //logStatus("End TestCase", "Test Case 1: User Logged Out Succesfull :", "PASS");
        test.log(LogStatus.PASS, "Test Case 1: User Logged Out Succesfull ");

    
            
        } catch (Exception e) {
            //TODO: handle exception
            logStatus("PageTest01", "TestCase01 Validation", "Failed");
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
