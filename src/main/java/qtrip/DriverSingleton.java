package qtrip;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverSingleton {

	  private WebDriver driver;
	    // private instance of SingleTon Class
	    // Private Constructor is used to restrict the object creation  outside the class 
	    // intialize the driver Single time  insted of Multiplet times 
	    // Whenever we create the Object the Driver Get intialised


	    private static DriverSingleton instanceOfSingleton;
	    private DriverSingleton () throws MalformedURLException{
	        
	      WebDriverManager.chromedriver().setup();
	      driver = new ChromeDriver();
	        System.out.println("createDriver()");
	        driver.manage().window().maximize();
	    }
	    //Updatation of Synchronisation
	    public static DriverSingleton getInstanceOfSingleTonBrowserClass() throws MalformedURLException{

	        if (instanceOfSingleton == null) 
	             // intialize the driver
	             instanceOfSingleton = new DriverSingleton();


	        return instanceOfSingleton;

	    }

	    // Public Method to access the driver Outside the class
	    
	    public WebDriver getDriver(){
	        return driver;
	    }
}
