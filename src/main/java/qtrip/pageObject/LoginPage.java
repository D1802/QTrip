package qtrip.pageObject;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import qtrip.SeleniumWrapper;

public class LoginPage {
	WebDriver driver;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/login/";

    @FindBy(name="email")
    WebElement userName;

    @FindBy(name="password")
    WebElement Password;

    @FindBy(xpath = "//button[text()='Login to QTrip']")
    WebElement Login;

    @FindBy(xpath = "//div[text()='Logout']")
    WebElement Logout;

    @FindBy(xpath = "//a[text()='Login Here']")
    WebElement loginHear;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(factory,this);

    }

    public void navigateToLoginPage() throws InterruptedException {
        SeleniumWrapper.navigate(driver, url);
    }

    // void to change as Boolean
    public void loginUser(String username,String password) throws InterruptedException{
     
        
            SeleniumWrapper.enterText(username, userName);
            SeleniumWrapper.enterText(password, Password);
            SeleniumWrapper.clickAction(driver, Login);
            //Thread.sleep(4000);
    
        
    }
    
 


    public void LogOutUser() throws InterruptedException{

            SeleniumWrapper.clickAction(driver, Logout);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(loginHear));
            if(loginHear.isDisplayed())
            	System.out.println("LoginPage is Displayed");
            else
            	System.out.println("LoginPage is not Displayed");
        
    }


}
