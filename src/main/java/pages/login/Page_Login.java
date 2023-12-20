package pages.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_Login extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_Login(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//input[@placeholder='Username']")
	public WebElement login_username;

	@FindBy(xpath = "//input[@placeholder='Password']")
	public WebElement login_password;

	@FindBy(xpath = "//input[@id='signinbutton']")
	public WebElement login_button;

	@FindBy(xpath = "//img[@class='foss_circle img-responsive']")
	public WebElement healthgraph_logo;

	@FindBy(xpath = "//div[@class='g-recaptcha']//iframe")
	public WebElement iFrame_captchaWindow;

	@FindBy(xpath = "//div[@id='rc-imageselect']")
	public WebElement window_captcha;

	@FindBy(xpath = "//label[contains(text(),'not a robot')]/parent::div/parent::div/preceding-sibling::div//span")
	public WebElement checkBox_captchaWindow;

	@FindBy(xpath = "//a[contains(@href,'password_resets')]")
	public WebElement link_forgotPassword;

	@FindBy(xpath = "//a[text()='Logout']")
	public WebElement button_logoutFromErrorPage;
}
