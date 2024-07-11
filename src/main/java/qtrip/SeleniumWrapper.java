package qtrip;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumWrapper {

    public static boolean clickAction(WebDriver driver,WebElement elementToClick) throws InterruptedException {
        WebDriverWait wiat = new WebDriverWait(driver,Duration.ofSeconds(30));
        if (elementToClick.isDisplayed()) {
            try{
            // JavascriptExecutor js = (JavascriptExecutor)driver;
            // js.executeScript("argument[0].scrollIntoView(true)", elementToClick);
                    // Create JavaScriptExecutor instance
   
            Actions actions = new Actions(driver);
         
            System.out.println(elementToClick.getText());
            
            actions.moveToElement(elementToClick).click().perform();
            wiat.until(ExpectedConditions.elementToBeClickable(elementToClick));
            System.out.println("Action is Perform"+elementToClick.isEnabled());
            return true;
            }catch(Exception e){
                return false;
            }
        }
        return false;
    }

    public static boolean enterText(String keysToSend ,WebElement inputBox) {
        try {
            inputBox.clear();
            inputBox.sendKeys(keysToSend);
            return true;
        } catch (Exception e) {
            //TODO: handle exception
            return false;
        }
    
    }

    public static boolean navigate(WebDriver driver,String url) throws InterruptedException{
        try {
            if (driver.getCurrentUrl().equals(url)) {
                return true;
            }else{
                driver.get(url);
                return driver.getCurrentUrl().equals(url);
            }
        } catch (Exception e) {
            //TODO: handle exception
            return false;
        }
    }

}
