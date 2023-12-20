package pages.settings.profileSetting;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_ProfileSetting extends TestBase {
    public Page_ProfileSetting(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(@href,'settings/profile')]/parent::li")
    public WebElement link_profileSetting;
    @FindBy(xpath = "//a[contains(text(),'Personal Information')]")
    public WebElement link_profileInformation;
    @FindBy(xpath = "//a[contains(@href,'edit_user_profile')]")
    public WebElement button_editProfileInformation;
    @FindBy(xpath = "//input[contains(@id,'user_salutation')]")
    public WebElement input_salutationEditProfileInformation;
    @FindBy(xpath = "//input[contains(@id,'user_fullname')]")
    public WebElement input_fullNameEditProfileInformation;
    @FindBy(xpath = "//button[normalize-space()='Close']")
    public WebElement button_closeEditProfileInformation;
    @FindBy(xpath = "//a[contains(text(),'Login and Security')]")
    public WebElement link_loginAndSecurity;
    @FindBy(xpath = "//h1[contains(text(),'Login and Security')]")
    public WebElement text_headerLoginAndSecurity;
    @FindBy(xpath = "//a[contains(text(),'Audit Trials')]")
    public WebElement tab_auditTrails;
    @FindBy(xpath = "//a[contains(text(),'Security Questions')]")
    public WebElement tab_securityQuestion;
    @FindBy(xpath = "//a[contains(text(),'Change Password')]")
    public WebElement tab_changePassword;
    @FindBy(xpath = "//*[contains(text(),'expire on')]")
    public WebElement text_passwordExpireOn;
    @FindBy(xpath = "//*[text()='Edit']")
    public WebElement button_editSecurityQuestion;
    @FindBy(xpath = "//select[@id='reset_security_question_code']")
    public WebElement select_securityQuestionSetSQTab;
    @FindBy(xpath = "//input[@id='reset_security_question_answer']")
    public WebElement input_securityAnswerSetSQTab;
    @FindBy(xpath = "//input[@id='password']")
    public WebElement input_passwordSetSQTab;
    @FindBy(xpath = "//span[contains(@class,'save_security_question')]")
    public WebElement button_saveChangesSetSQTab;
    @FindBy(xpath = "//input[contains(@id,'user_username')]")
    public WebElement input_userNameCPTab;
    @FindBy(xpath = "//input[@id='user_old_password']")
    public WebElement input_currentPasswordCPTab;
    @FindBy(xpath = "//input[@id='user_password']")
    public WebElement input_newPasswordCPTab;
    @FindBy(xpath = "//input[@id='user_password_confirmation']")
    public WebElement input_confirmPasswordCPTab;
    @FindBy(xpath = "//*[@id='setpasswordbutton']")
    public WebElement button_changePasswordCPTab;
    @FindBy(xpath = "//select[@id='security_question']")
    public WebElement select_securityQuestionCPTab;
    @FindBy(xpath = "//input[@id='security_question_answer']")
    public WebElement input_securityAnswerCPTab;
    @FindBy(xpath = "//li[@class='facility_name']")
    public WebElement text_currentFacilityName;
    @FindBy(xpath = "//*[@id='audit_trail']//table/thead//th")
    public List<WebElement> list_tableHeaderAuditTrails;
    @FindBy(xpath = "//*[@id='audit_trail']//table/tbody/tr")
    public List<WebElement> list_tableRowAuditTrails;

}
