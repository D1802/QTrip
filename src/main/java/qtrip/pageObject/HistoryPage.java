package qtrip.pageObject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import qtrip.SeleniumWrapper;

public class HistoryPage {

    public WebDriver driver;    
    public String transactionID;

    @FindBy(xpath = "//table/tbody/tr/th")
    WebElement transID;

    @FindBy(xpath = "//button[text()='Cancel']")
    WebElement cancel;

    @FindBy(id = "no-reservation-banner")
    WebElement NoReservationBanner;


    public HistoryPage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(factory,this);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void getReservations() throws InterruptedException{

        //WebDriverWait wait = new WebDriverWait(driver, 10);
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement reservationsLink = driver.findElement(By.xpath("//a[text()='Reservations']"));
        //reservationsLink.click();
        SeleniumWrapper.clickAction(driver, reservationsLink);
        //Reservation_element.click();
        String TranscationID = transID.getText();
        System.out.println(TranscationID);
        this.transactionID = TranscationID;

    }

    public Boolean cancelReservations() throws InterruptedException{
        //Reservation_element.click();
        Boolean status;
       
        WebElement cancelreg = driver.findElement(By.xpath("//button[text()='Cancel']"));

        //cancel.click();
        //cancelreg.click();
        SeleniumWrapper.clickAction(driver, cancelreg);
        //Thread.sleep(2000);
        status = NoReservationBanner.isDisplayed();
        System.out.println("Registration clear Succesfully");
        

       
        return status;

    }

}
