package pages.settings.organisationSettings.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_PatientSettings extends TestBase {
    private WebDriver driver;

    public Page_PatientSettings(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//h4[text()='Patient Form Settings']")
    public WebElement header_formValidationFields;

    @FindBy(xpath = "//div[@id='manage_patient_form_settings']/div[3]//label")
    public List<WebElement> list_formFieldNames;

    @FindBy(xpath = "//div[@id='manage_patient_form_settings']/div[3]//input")
    public List<WebElement> list_checkbox_formFieldNames;

    @FindBy(xpath = "//div[@id='manage_patient_form_settings']/div[3]//button[text()='Save']")
    public WebElement button_saveFormFields;

   }
