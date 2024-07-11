package qtrip.pageObject;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import qtrip.SeleniumWrapper;

public class AdventurePage {

	public WebDriver driver;

    @FindBy(xpath = "//input[@id='search-adventures']")
    WebElement search_adventure;

    // @FindBy(xpath = "//input[@id='search-adventures']/following-sibling::div")
    // WebElement clearsearchbar;

    @FindBy(className = "activity-card")
    WebElement adventure_select;

    
    public AdventurePage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
        PageFactory.initElements(factory,this);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    public void selectAdventure(String AdvName) throws InterruptedException{
  
        SeleniumWrapper.enterText(AdvName, search_adventure);
        //Thread.sleep(4000);
        // adventure_select.click();
        SeleniumWrapper.clickAction(driver, adventure_select);
    
    }
}
