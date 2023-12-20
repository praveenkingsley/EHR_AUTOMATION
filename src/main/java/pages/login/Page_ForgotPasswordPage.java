package pages.login;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page_ForgotPasswordPage extends TestBase {

    public Page_ForgotPasswordPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='username']")
    public WebElement input_userName;
    @FindBy(xpath = "//input[@id='emailaddress']")
    public WebElement input_emailAddress;
    @FindBy(xpath = "//input[@id='signinbutton']")
    public WebElement button_verifyUser;
    @FindBy(xpath = "//iframe[starts-with(@name, 'a-') and starts-with(@src, 'https://www.google.com/recaptcha')]")
    public WebElement  iframe_captcha;
    @FindBy(xpath = "//div[@class='recaptcha-checkbox-border']")
    public WebElement  checkbox_captcha;
    @FindBy(xpath = "//h2[contains(text(),'Thank you')]")
    public WebElement text_successfulMsg;

}
