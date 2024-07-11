package qtrip.pageObject;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v122.media.model.Timestamp;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import qtrip.SeleniumWrapper;

public class RegisterPage {
	
	WebDriver driver;
    public  String Email="";

    @FindBy(name="email")
    private WebElement userName;

    @FindBy(name="password")
    private WebElement Password;

    @FindBy(name  ="confirmpassword")
    private WebElement confPassword;

    @FindBy(xpath = "//button[text()='Register Now']")
    private WebElement registerNow;

    public String url="https://qtripdynamic-qa-frontend.vercel.app/pages/register/";
    

    public RegisterPage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(factory,this);
    }
     
    public void navigateRegisterPage(WebDriver driver){
        if(!driver.getCurrentUrl().equals(this.url)){
            driver.get(this.url);
        }
    }

    public Boolean RegisterNewUser(String username,String password,String confPass, boolean makeusernameDynamic) throws InterruptedException{
    
        String test_data_username;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String[] splitusername = username.split("@");
        //Dynamic UserName

        if (makeusernameDynamic) {
            // test_data_username = splitusername[0]+timestamp+splitusername[1]+"@gmail.com";
            test_data_username = splitusername[0]+timestamp+"@"+splitusername[1];
        } else {
            test_data_username =username;
        };
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
   
       

        SeleniumWrapper.enterText(test_data_username, userName);
    
        SeleniumWrapper.enterText(password, Password);
        //Thread.sleep(1000);
        SeleniumWrapper.enterText(password, confPassword);
        //Thread.sleep(1000);
        SeleniumWrapper.clickAction(driver, registerNow);
        wait.until(ExpectedConditions.elementToBeClickable(registerNow));
        this.Email = test_data_username;
        System.out.println(driver.getCurrentUrl());
        //Thread.sleep(4000);
        wait.until(ExpectedConditions.urlContains("/login"));
        return this.driver.getCurrentUrl().endsWith("/login");


    }

}
