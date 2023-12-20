package pages.settings.organisationSettings.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import data.settingsData.OrganisationSettings_Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_OpticalInvoiceSettings extends TestBase {

    public Page_OpticalInvoiceSettings(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@id='manage_custom_template_headers_id']")
    public WebElement link_templateCustomerHeader;

    @FindBy(xpath = "//button[text()='OPTICAL']")
    public WebElement button_Optical;

    @FindBy(xpath = "//div[@class='col-sm-3 button-section']")
    public List<WebElement> button_department;

    @FindBy(xpath = "//a[@id='edit-optical-invoices-view']")
    public WebElement button_editOpticalInvoice;

    @FindBy(xpath = "//h4[text()='Optical Invoices Custom Print Settings ']")
    public WebElement header_opticalInvoiceSettingsEdit;

    @FindBy(xpath = "//span[@class='display_name']")
    public List<WebElement> name_labelInInvoice;

    @FindBy(xpath = "//div[@class='row selected_list']/div/div/b")
    public List<WebElement> name_selectedLabel;

    @FindBy(xpath = "//div[@class='row unselected_list']/div/div[2]/..//button")
    public WebElement button_reselect;

    @FindBy(xpath = "(//span[text()='Patient'])[2]")
    public WebElement label_patient;

    @FindBy(xpath = "(//span[text()='" + OrganisationSettings_Data.sLabelToRemove + "'])[2]/../../..//div[2]/button[3]")
    public WebElement span_removeSelectedOrder;

    @FindBy(xpath = "//div[@class='row unselected_list']/div/div/b//span")
    public WebElement label_unSelectedField;

    @FindBy(xpath = "//input[@id='save-custom-template-header']")
    public WebElement input_saveSettings;

    @FindBy(xpath = "//div[@class='optical-invoice-view']//div[@class='col-sm-5']/div/div[1]")
    public List<WebElement> label_summaryOptical;

    @FindBy(xpath = "(//button[@title='Move Down'])[1]")
    public WebElement button_downArrow;

    @FindBy(xpath = "//button[text()='Close']")
    public WebElement button_close;

}
