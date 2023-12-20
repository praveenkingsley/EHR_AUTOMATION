package pages.settings.organisationSettings.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import data.settingsData.OrganisationSettings_Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_TemplateCustomHeaderSettings extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_TemplateCustomHeaderSettings(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

   @FindBy(xpath = "//button[@id='pharmacy-department-button']")
    public WebElement button_pharmacyDepartmentSetting;

    @FindBy(xpath = "//a[@title='Edit PHARMACY INVOICES View']")
    public WebElement button_pharmacyEditSetting;

    @FindBy(xpath = "//h4[contains(text(),'Pharmacy Invoices')]")
    public WebElement header_mainModalPharmacy;

    @FindBy(xpath = "//div[@class='pharmacy-invoice-view']/div/div/div")
    public List<WebElement> list_viewPharmacyInvoiceFieldsOrder;

    @FindBy(xpath = "//div[@class='row selected_list']/div/div[1]")
    public List<WebElement> list_editPharmacyInvoiceFieldsOrder;

    @FindBy(xpath = "//div[@class='row selected_list']/div/div[2]/button[@title='Move Down']")
    public List<WebElement> list_buttonMoveDownPharmacyInvoiceFieldsOrder;

    @FindBy(xpath = "//div[@class='row selected_list']/div/div[2]/button[@title='Remove field']")
    public List<WebElement> list_buttonRemovePharmacyInvoiceFields;

    @FindBy(xpath = "//div[@class='row unselected_list']/div/div[1]")
    public List<WebElement> list_removedPharmacyInvoiceFields;

    @FindBy(xpath = "//div[@class='row unselected_list']/div/div[2]")
    public List<WebElement> list_buttonReSelectPharmacyInvoiceFields;

    @FindBy(xpath = "//input[@value='Save']")
    public WebElement button_savePharmacyOrder;

    // OPD Module Elements In Template Custom Header

    @FindBy(xpath = "//button[@id='opd-department-button']")
    public WebElement button_opd;

    @FindBy(xpath = "//span[text() ='OPD TEMPLATES SETTINGS']")
    public WebElement text_opdTemplateSettingHeader;

    @FindBy(xpath = "//span[text() ='OPD INVOICES SETTINGS']")
    public WebElement text_opdInvoicesSettingHeader;

    @FindBy(xpath = "//a[@id='edit-opd-template-view']/i")
    public WebElement button_editOpdTemplateSetting;

    @FindBy(xpath = "//a[@id='edit-opd-invoices-view']/i")
    public WebElement button_editOpdInvoicesSetting;

    @FindBy(xpath = "//h4[text()='Opd Templates Custom Print Settings ']")
    public WebElement text_opdTemplatePrintSetting;

    @FindBy(xpath = "//h4[text()='Opd Invoices Custom Print Settings ']")
    public WebElement text_opdInvoicesPrintSetting;

    @FindBy(xpath = "//div[@class='opd-template-view']/div/div/div")
    public List<WebElement> list_opdTemplateSettingViewHeadersList;

    @FindBy(xpath = "//div[@class='opd-invoice-view']/div/div/div")
    public List<WebElement> list_opdInvoicesSettingViewHeadersList;

    // IPD Module Elements In Template Custom Header

    @FindBy(xpath = "//button[@id='ipd-department-button']")
    public WebElement button_ipd;

    @FindBy(xpath = "//span[text() ='IPD INVOICES SETTINGS']")
    public WebElement text_ipdInvoicesSettingHeader;

    @FindBy(xpath = "//span[text() ='IPD TEMPLATES SETTINGS']")
    public WebElement text_ipdTemplateSettingHeader;

    @FindBy(xpath = "//a[@id='edit-ipd-template-view']/i")
    public WebElement button_editIpdTemplateSetting;

    @FindBy(xpath = "//a[@id='edit-ipd-invoices-view']/i")
    public WebElement button_editIpdInvoicesSetting;


    @FindBy(xpath = "//h4[text()='Ipd Invoices Custom Print Settings ']")
    public WebElement text_ipdInvoicesPrintSetting;

    @FindBy(xpath = "//h4[text()='Ipd Templates Custom Print Settings ']")
    public WebElement text_ipdTemplatePrintSetting;

    @FindBy(xpath = "//div[@class='ipd-template-view']/div/div/div")
    public List<WebElement> list_ipdTemplateSettingViewHeadersList;

    @FindBy(xpath = "//div[@class='ipd-invoice-view']/div/div/div")
    public List<WebElement> list_ipdInvoicesSettingViewHeadersList;

    // Edit OPD and IPD Module Common Elements

    @FindBy(xpath = "//div[@class='row selected_list']/div/div[2]/button[@title='Remove field']")
    public List<WebElement> list_buttonRemoveSelectedField;

    @FindBy(xpath = "//div[@class='row selected_list']/div/div[1]")
    public List<WebElement> list_editSelectedFieldsOrder;

    @FindBy(xpath = "//div[@class='row selected_list']/div/div[2]/button[@title='Move Down']")
    public List<WebElement> list_buttonMoveDownFieldsOrder;

    @FindBy(xpath = "//div[@class='row unselected_list']/div/div[1]")
    public List<WebElement> list_unselectedOrdersFieldsName;

    @FindBy(xpath = "//div[@class='row unselected_list']/div/div[2]")
    public List<WebElement> list_buttonsReselectInUnselectedList;

    @FindBy(xpath = "//input[@id='save-custom-template-header']")
    public WebElement button_saveCustomTemplate;

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

    @FindBy(xpath = "//div[@class='row selected_list']/div/div/b//span[2]")
    public List<WebElement> name_selectedLabel;

    @FindBy(xpath = "//div[@class='row unselected_list']/div/div[2]/..//button")
    public WebElement button_reselect;

    @FindBy(xpath = "(//span[text()='Patient'])[2]")
    public WebElement label_patient;

    @FindBy(xpath = "(//span[text()='" + OrganisationSettings_Data.sLabelToRemove + "'])[2]/../../..//div[2]/button[3]")
    public WebElement span_removeSelectedLabel;

    @FindBy(xpath = "//div[@class='row unselected_list']/div/div/b//span")
    public WebElement label_unSelectedField;

    @FindBy(xpath = "//input[@id='save-custom-template-header']")
    public WebElement input_saveSettings;

    @FindBy(xpath = "//div[@class='optical-invoice-view']//div[@class='col-sm-5']/div/div[1]")
    public List<WebElement> label_summaryOptical;

    @FindBy(xpath = "(//button[@title='Move Down'])[1]")
    public WebElement button_firstDownArrow;

    @FindBy(xpath = "//button[text()='Close']")
    public WebElement button_close;

    @FindBy(xpath = "//div[@class='row unselected_list']/div/div[1]//b")
    public List<WebElement> textList_UnselectedLabels;

    @FindBy(xpath = "//div[@class='row unselected_list']/div/div[2]/..//button")
    public List<WebElement> buttonList_Reselect;

}
