package qtrip;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;

public class ReportSingleton {

	 private static ReportSingleton instanceOfSingletonReport = null;
	    public WebDriver driver;
	    private ExtentReports reports;

	    private ReportSingleton(){

	        reports = new ExtentReports(System.getProperty("user.dir")+"/report"+getTimestamp()+".html",true);
	    }

	    public String getTimestamp(){
	        return null;
	    }

	    public static synchronized  ReportSingleton getInstanceOfSingletonReport() throws MalformedURLException{
	        if (instanceOfSingletonReport == null) 
	        // intialize the driver
	            instanceOfSingletonReport = new ReportSingleton();


	        return instanceOfSingletonReport;
	    }

	    public ExtentReports getReports(){
	        return reports;
	    }
}
