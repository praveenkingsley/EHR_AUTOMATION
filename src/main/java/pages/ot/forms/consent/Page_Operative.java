package pages.ot.forms.consent;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Operative extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_Operative(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }


    // Operative -  Consent Section

    @FindBy(xpath = "//div[text()='Consents']/following-sibling::div//a[contains(text(),'Operative')]")
    public WebElement button_operativeInConsent;

    @FindBy(xpath = "//div[@class='modal-header']//h4[contains(text(),'Consent')]")
    public WebElement header_ConsentWindowName;

    @FindBy(xpath = "//span[@class='select2-selection select2-selection--single']")
    public WebElement selectButton_templateForConsent;

    @FindBy(xpath = "//select[@id='consent-dropdown']")
    public WebElement select_templateForConsent;

    @FindBy(xpath = "//select[@id='consent-dropdown']/option")
    public List<WebElement> selectOptions_templateForConsent;

    @FindBy(xpath = "//div[@class='modal-header']//a[text()='Print']")
    public WebElement button_printTemplate;

    @FindBy(xpath = "//div[@class='modal-header']//button[text()='Close']")
    public WebElement button_closeTemplate;


}