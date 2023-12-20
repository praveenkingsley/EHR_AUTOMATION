package pages.settings.profileSetting;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page_PasswordResetPage extends TestBase {

    public Page_PasswordResetPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(@class,'recover-password')]//h2")
    public WebElement text_headerMsg;
    @FindBy(xpath = "//input[@id='user_username']")
    public WebElement input_username;
    @FindAll({@FindBy(xpath = "//a[contains(@href,'logout')]"),@FindBy(xpath = "//a[contains(@href,'login')]")})
    public WebElement link_backToLogin;
    @FindBy(xpath = "//span[@id='setpasswordbutton']")
    public WebElement button_createNewPassword;
    @FindBy(id = "user_old_password")
    public WebElement input_currentPassword;
    @FindBy(id = "user_password")
    public WebElement input_newPassword;
    @FindBy(id = "user_password_confirmation")
    public WebElement input_confirmNewPassword;
    @FindBy(xpath = "//label[@for='user_password']")
    public WebElement text_errorMsgNewPassword;
    @FindBy(xpath = "//select[@id='security_question']")
    public WebElement select_securityQuestion;
    @FindBy(xpath = "//input[@id='security_question_answer']")
    public WebElement input_securityAnswer;
    @FindBy(xpath = "//input[@value='Proceed For Now']")
    public WebElement button_proceedForNow;
    @FindBy(xpath = "//h4[text()='Error']")
    public WebElement text_errorNotifyMsg;


    //Third Party Website -->
    @FindBy(xpath = "//input[@type='email']")
    public WebElement tutanota_input_emailAddress;
    @FindBy(xpath = "//input[@type='password']")
    public WebElement tutanota_input_password;
    @FindBy(xpath = "//button[@title='Log in']")
    public WebElement tutanota_button_login;
    @FindBy(xpath = "//ul[contains(@class,'list')]//div[text()='no-reply@healthgraph.in']")
    public WebElement tutanota_link_inboxSentMail;

    //Jenkins
    @FindBy(xpath = "//a[contains(@href,'rake-deployment/build') and @class='task-link ']")
    public WebElement jenkins_link_buildWithParameter;
    @FindBy(xpath = "//textarea[contains(@class,'jenkins-input')]")
    public WebElement jenkins_input_rake;
    @FindBy(xpath = "(//select[@name='value'])[1]")
    public WebElement jenkins_select_env;
    @FindBy(xpath = "//button[@name='Submit']")
    public WebElement jenkins_build;
    @FindBy(xpath = "//input[contains(@id,'username')]")
    public WebElement jenkins_input_username;
    @FindBy(xpath = "//input[contains(@name,'password')]")
    public WebElement jenkins_input_password;
    @FindBy(xpath = "//button[contains(text(),'Sign in')]")
    public WebElement jenkins_button_signIn;
    @FindBy(xpath = "//span[@class='genytxt']")
    public WebElement yopMail_textEmailId;
    @FindBy(xpath = "//span[contains(text(),'New')]/parent::button")
    public WebElement yopMail_newEmailId;
    @FindBy(xpath = "//span[text()='Check Inbox']")
    public WebElement yopMail_checkInbox;
    @FindBy(css = "#refresh")
    public WebElement yopMail_refreshButton;
    @FindBy(xpath = "//span[text()='no-reply@healthgraph.in']")
    public WebElement yopMail_emailSub;
    @FindBy(xpath = "//a[text()='Activate Account']")
    public WebElement yopMail_emailActivateAccount;
    @FindBy(xpath = "//input[@id='user_username']")
    public WebElement input_userNameActivation;
    @FindBy(xpath = "//input[@id='user_password']")
    public WebElement input_temporaryPasswordActivation;
    @FindBy(xpath = "//input[@id='user_password_confirmation']")
    public WebElement input_confirmTemporaryPasswordActivation;
    @FindBy(xpath = "//input[@id='setpasswordbutton']")
    public WebElement button_setPasswordActivation;

}
