package pages.ot.forms.consent;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_CustomConsents extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_CustomConsents(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    // Custom Consents -  Consent Section
    @FindBy(xpath = "//div[text()='Consents']/following-sibling::div//a[contains(text(),'Custom Consents')]")
    public WebElement button_customConsentInConsent;

    @FindBy(xpath = "//div[@class='modal-header']//h4[text()='Consent']")
    public WebElement header_customConsent;

    @FindBy(xpath = "//div[@class='modal-body']//select[@id='specialty_id']")
    public WebElement select_specialityInCustomConsent;

    @FindBy(xpath = "//div[@class='modal-body']//select[@id='specialty_id']/option")
    public List<WebElement> selectOptions_specialityInCustomConsent;

    @FindBy(xpath = "//div[@class='modal-body']//select[@id='specialty_id']/following-sibling::span")
    public WebElement selectButton_specialityInCustomConsent;

    @FindBy(xpath = "//div[@class='modal-body']//b[text()='Consent Name']/following-sibling::select")
    public WebElement select_consentNameInCustomConsent;

    @FindBy(xpath = "//div[@class='modal-body']//b[text()='Consent Name']/following-sibling::select/option")
    public List<WebElement> selectOptions_consentNameInCustomConsent;

    @FindBy(xpath = "//div[@class='modal-body']//b[text()='Consent Name']/following-sibling::select/following-sibling::span")
    public WebElement selectButton_consentNameInCustomConsent;

    @FindBy(xpath = "//div[@class='modal-body']//b[text()='Language']/following-sibling::select")
    public WebElement select_languageInCustomConsent;

    @FindBy(xpath = "//div[@class='modal-body']//b[text()='Language']/following-sibling::select/option")
    public List<WebElement> selectOptions_languageInCustomConsent;

    @FindBy(xpath = "//div[@class='modal-body']//b[text()='Language']/following-sibling::select/following-sibling::span")
    public WebElement selectButton_languageInCustomConsent;

    @FindBy(xpath = "//div[@class='modal-header']//button[text()='Close']")
    public WebElement button_closeTemplate;


}