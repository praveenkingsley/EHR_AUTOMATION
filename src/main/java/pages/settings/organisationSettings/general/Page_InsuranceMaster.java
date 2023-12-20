package pages.settings.organisationSettings.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_InsuranceMaster extends TestBase {
    public Page_InsuranceMaster(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(text(),'Add Insurance')]")
    public WebElement button_addInsurance;

    @FindBy(xpath = "//h4[contains(text(),'Add Insurance')]")
    public WebElement text_addInsuranceLabel;

    @FindBy(xpath = "//input[@id='finance_insurance_master_name']")
    public WebElement inputField_insuranceName;

    @FindBy(xpath = "//input[@id='finance_insurance_master_description']")
    public WebElement inputField_insuranceDescription;

    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveChanges;

    @FindBy(xpath = "//button[text()='Close']")
    public WebElement button_Close;

    @FindBy(xpath = "//tbody[@id='insurance_master-list']/tr/td[1]/b")
    public List<WebElement> list_insuranceName;

    @FindBy(xpath = "//tbody[@id='insurance_master-list']/tr/td[2]/b")
    public List<WebElement> list_insuranceDescription;

    @FindBy(xpath = "//tr[contains(@class,'insurance_master_tr')]//a[text()='Edit']")
    public List<WebElement> list_editButton;


    @FindBy(xpath = "//div[@id='confirmation-modal']//a[text()='Confirm']")
    public WebElement button_confirm;

    @FindBy(xpath = "//tbody[@id='insurance_master-list']//a[text()='Active']")
    public List<WebElement> list_activeButton;
    @FindBy(xpath = "//tbody[@id='insurance_master-list']//div[contains(text(),'Disable')]")
    public List<WebElement> list_diableButton;

}
