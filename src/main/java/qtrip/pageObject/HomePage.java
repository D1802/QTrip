package qtrip.pageObject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import qtrip.SeleniumWrapper;

public class HomePage {
	

    WebDriver driver;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/";

    @FindBy(linkText = "Register")
    WebElement RegisterNow;

    @FindBy(id = "autocomplete")
    WebElement SearchCity;

    WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));

    public HomePage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        PageFactory.initElements(factory,this);
    }


    
    public void navigateToHomePage() throws InterruptedException {

        SeleniumWrapper.navigate(driver, url);
    }

    public void clikRegister() throws InterruptedException{
  
        SeleniumWrapper.clickAction(driver,RegisterNow);
    }

   


    public void searchCity(String city) throws InterruptedException{
       SeleniumWrapper.enterText(city, SearchCity);
       
    }
    /* public Boolean assertAutoCompleteText(){
    } */

    public void SelectCity(String city) throws InterruptedException{
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    	
       WebElement citySelection = driver.findElement(By.id(city.toLowerCase()));
       wait.until(ExpectedConditions.visibilityOf(citySelection));
       
       
       //Thread.sleep(1000);
//      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    
       if (citySelection.getText().equalsIgnoreCase("No City Found")) {
            System.out.println("Search result NOT Contain City ");
            
       } else {
        System.out.println("Seach City is Found"+ city);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", citySelection);
        citySelection.click();
     
        //SeleniumWrapper.clickAction(driver, citySelection);
        //
        //wait.until(ExpectedConditions.urlContains(city));
     
        System.out.println(driver.getCurrentUrl());
        if(driver.getCurrentUrl().endsWith(city.toLowerCase()))
        		System.out.println("URL is  Found");
        else
        	System.out.println("CITY IS NOT Found");
        //Assert.assertTrue(driver.getCurrentUrl().endsWith(city.toLowerCase()), "CITY IS NOT Found");
       }
       
    }

    public Boolean assertAutoCompleteText(String city_auto) throws InterruptedException{
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement autoSelectCity = driver.findElement(By.id(city_auto.toLowerCase()));
        wait.until(ExpectedConditions.visibilityOf(autoSelectCity));
        //Thread.sleep(2000);
        if (autoSelectCity.isDisplayed()) {
            return true;
        } else {
            return false;
        }
        
    }

}
