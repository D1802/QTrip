package qtrip;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;
import qtrip.pageObject.HomePage;
import resources.DP;

public class testCase_02 {
	
	static WebDriver driver;

    static ExtentReports reports;
    static ExtentTest test;
    public static String lastGeneratedUserName;



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
         driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        logStatus("driver", "Intializing Driver 2nd Time", "Success");
    	
        
    }

    public   int  search_filters(String Category,String Duration) throws InterruptedException{
       
        int x=0;
        try {
           
            ClearFilter();
            WebElement filterByCategory = driver.findElement(By.id("category-select"));
            WebElement filterByDuration = driver.findElement(By.id("duration-select"));
            //Thread.sleep(1000);
            System.out.println(filterByCategory.isDisplayed());
            Select category = new Select(filterByCategory);
            //Thread.sleep(2000);
            category.selectByVisibleText(Category);
            //Thread.sleep(1000);
           
            Select duration = new Select(filterByDuration);
            //Thread.sleep(2000);
            duration.selectByVisibleText(Duration);
            //Thread.sleep(1000);
            List<WebElement> totalcart = driver.findElements(By.className("activity-card"));
            logStatus("Search Filter", "Start of Filter for Searching Category and Duration", "Pass");
            
            x=totalcart.size();

        } catch (Exception e) {
            //TODO: handle exception

            logStatus("search_Filters ", "TestCase02 Search_Filetes Validation", "Failed");
            e.printStackTrace();
        }
       return x;

    }

    public  void ClearFilter() throws InterruptedException{
        WebElement clear_category = driver.findElement(By.xpath("//select[@id='category-select']/following-sibling::div"));
        //clear_category.click();
        SeleniumWrapper.clickAction(driver, clear_category);
        WebElement clear_duration = driver.findElement(By.xpath("//select[@id='duration-select']/following-sibling::div"));
        //clear_duration.click();
        SeleniumWrapper.clickAction(driver, clear_duration);
        
    }

 

    @Test(description = "Verify Search and Filter", dataProvider = "data-provider", dataProviderClass = DP.class, priority = 2, groups = {"Search and Filter flow"},enabled =  true)
    public  void TestCase02(String CityName, String Category_Filter,String DurationFilter,String ExpectedFilteredResults,String ExpectedUnFilteredResults){
        Boolean status;
        int count;
        try {
            test = reports.startTest("Verify Search and Filter");
            logStatus("START TESTCASE2 ", "Test Case 2: Testcase2 is successfully : ", "Pass");
            HomePage home = new HomePage(driver);
            home.navigateToHomePage();

            home.searchCity(CityName);
            //Thread.sleep(2000);
            status = home.assertAutoCompleteText(CityName);
            //logStatus("Step Case ", "Test Case 2: City name is Found: ", status ? "Pass" :"Failed");
            if (status) {
                test.log(LogStatus.PASS, "City Name is found");
            } else {
                test.log(LogStatus.FAIL, "City Name is Not Found");
            }
            //Thread.sleep(1000);
            home.SelectCity(CityName);
            //Thread.sleep(1000);
            ClearFilter();
            List<WebElement> CartList = driver.findElements(By.className("activity-card"));
            double expectedUnfilteredResultsDouble = Double.parseDouble(ExpectedUnFilteredResults);
            int expectedUnfilteredResultsInt = (int) expectedUnfilteredResultsDouble;
            //status = (CartList.size()== Integer.valueOf(ExpectedUnFilteredResults,0)) ;
            status = (CartList.size()== expectedUnfilteredResultsInt) ;

            // Output the status
            System.out.println("Status: " + status);
            //logStatus("Step Case ", "Test Case 2: Verify the Unfiletered Result ", status ? "Pass" : "Failed");
            if (status) {
                test.log(LogStatus.PASS, "Verify The UnfilterResult");
            } else {
                test.log(LogStatus.FAIL, "Verification of UnfilterResult is Failed");
            }

            count = search_filters(Category_Filter,DurationFilter);
            System.out.println(count);
            status = (count == Integer.parseInt(ExpectedFilteredResults));

            //logStatus("Step Case","Test Case 2 : Verify the Filterd Result", status ? "Pass" : "Failed");
            if (status) {
                test.log(LogStatus.PASS, "Verify the Filterd Result");
            } else {
                test.log(LogStatus.FAIL, "Verification of Filterd Result Failed");
            }

            logStatus("End TesCase","Test Case 2: End of TesCase", "Pass");


        } catch (Exception e) {
            //TODO: handle exception
            logStatus("PageTest02", "TestCase02 Validation", "Failed");
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
