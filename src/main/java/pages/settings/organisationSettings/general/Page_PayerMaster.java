package pages.settings.organisationSettings.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_PayerMaster extends TestBase {

    public Page_PayerMaster(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//td[text()='No Payers Added']")
    public WebElement text_noPayerAdded;

    @FindBy(xpath = "//a[contains(text(),'Add Payer Master')]")
    public WebElement button_addPayerMaster;
    @FindBy(xpath = "//h4[contains(text(),'Add Payer Form')]")
    public WebElement label_addPayerForm;
    @FindBy(xpath = "//select[@id='payer_master_facility_id']")
    public WebElement select_facilityAddPayerForm;
    @FindBy(xpath = "//select[@id='payer_master_contact_group_id']")
    public WebElement select_insuranceAddPayerForm;
    @FindBy(xpath = "//select[@id='payer_master_payer_type_id']")
    public WebElement select_payerTypeMasterAddPayerForm;
    @FindBy(xpath = "//input[@id='payer_master_display_name']")
    public WebElement input_displayNameAddPayerForm;
    @FindBy(xpath = "//input[@value='Save Payer']")
    public WebElement button_savePayerAddPayerForm;
    @FindBy(xpath = "//a[contains(@href,'payer_masters/clone_multiple')]")
    public WebElement button_cloneAllAddPayer;
    @FindBy(xpath = "//table[@id='facility_list_table']//tr/td[2]/a")
    public List<WebElement> list_linkPayerLink;
    @FindBy(xpath = "//span[@class='pm_facility_name']")
    public List<WebElement> list_textFacilityName;
    @FindBy(xpath = "//a[contains(text(),'Disable')]")
    public WebElement button_disablePayer;
    @FindBy(xpath = "//a[contains(@class,'edit-btn-payer_master') and text()='Edit']")
    public WebElement button_editPayer;
    @FindBy(xpath = "//a[text()='Enable']")
    public WebElement button_enablePayer;
    @FindBy(xpath = "//a[contains(@class,'edit-btn-payer_master') and text()='Clone']")
    public WebElement button_clonePayer;
}
