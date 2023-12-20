package pages.settings.organisationSettings.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_PatientPayerDataMaster extends TestBase {

    public Page_PatientPayerDataMaster(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(text(),' Add Patient Payer Data')]")
    public WebElement button_addPatientPayerData;

    @FindBy(xpath = "//h4[contains(text(),'Add Patient Payer Data')]")
    public WebElement text_addPatientPayerDataLabel;

    @FindBy(xpath = "//input[@id='finance_patient_payer_data_master_name']")
    public WebElement inputField_patientPayerDataName;

    @FindBy(xpath = "//input[@id='finance_patient_payer_data_master_description']")
    public WebElement inputField_patientPayerDataDescription;

    @FindBy(xpath = "//input[@placeholder='Search By Patient Payer Data Name']")
    public WebElement search_PatientPayerDataName;

    @FindBy(xpath = "//tbody[@id='patient_payer_master-list']//tr/td[1]/b")
    public List<WebElement> list_PatientPayerDataName;

    @FindBy(xpath = "//tbody[@id='patient_payer_master-list']//tr/td[2]/b")
    public List<WebElement> list_PatientPayerDataDescription;

    @FindBy(xpath = "//div[@target-input-id='payer_master_patient_payer_data_details']//button")
    public List<WebElement> list_patientPayerDataButton;

    @FindBy(xpath = "//tbody[@id='patient_payer_master-list']//td[contains(@style,'visible')]//a[text()='Edit']")
    public List<WebElement> list_editButton;
    @FindBy(xpath = "//tbody[@id='patient_payer_master-list']//div[text()='Disable']")
    public List<WebElement> list_disableButton;
    @FindBy(xpath = "//tbody[@id='patient_payer_master-list']//a[text()='Active']")
    public List<WebElement> list_activeButton;

}
