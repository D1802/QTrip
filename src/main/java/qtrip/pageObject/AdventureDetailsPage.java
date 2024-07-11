package qtrip.pageObject;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import qtrip.SeleniumWrapper;

public class AdventureDetailsPage {

	 public WebDriver driver;

	    @FindBy(name = "date")
	    WebElement datepicker;

	    @FindBy(name = "name")
	    WebElement NameOfGuest;

	    @FindBy(name = "person")
	    WebElement NoOfCount;

	    @FindBy(xpath = "//button[text()='Reserve']")
	    WebElement reserve;

	    @FindBy(id = "reserved-banner")
	    WebElement msg;


	    public AdventureDetailsPage (WebDriver driver){
	        this.driver = driver;
	        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 10);
	        PageFactory.initElements(factory,this);
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	    }


	    public void bookAdventure(String guestName, String dateofadv, String count) throws InterruptedException{
	 

	        //NameOfGuest.sendKeys(guestName);
	        SeleniumWrapper.enterText(guestName, NameOfGuest);
	   

	        String inputDateString = dateofadv;
	        
	        // Define the formatter for the input date string
	        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	        
	        // Parse the desired date string into a LocalDate object
	         LocalDate desiredDate = LocalDate.parse(inputDateString,inputFormatter);

	        // Format the date in the required format for the date picker
	    
	        // String formattedDate = dateofadv.format(formatter);

	            // Define the formatter for the output date format (MM/dd/yyyy)
	            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	        
	            // Format the input date into the desired format
	        String formattedDate = desiredDate.format(outputFormatter);
	        //Thread.sleep(2000);
	        // Click on the date picker to open it
	        //datepicker.sendKeys(formattedDate);
	        SeleniumWrapper.enterText(formattedDate, datepicker);

	   
	        SeleniumWrapper.enterText(count, NoOfCount);
	         //Thread.sleep(1000);
	         //reserve.click();
	         SeleniumWrapper.clickAction(driver, reserve);
	         //Thread.sleep(1000);

	    }

	    public Boolean isBookingSuccessfull(){
	        Boolean status;
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        wait.until(ExpectedConditions.visibilityOf(msg));
	        if (msg.isDisplayed()) {
	            status = msg.getText().contains("Greetings!"); 
	        } else
	        {
	            System.out.println("Reservation NOT Succesfull");
	            status = false;
	        }

	        return status;
	    }

}
