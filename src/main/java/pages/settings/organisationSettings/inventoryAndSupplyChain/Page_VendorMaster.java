package pages.settings.organisationSettings.inventoryAndSupplyChain;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_VendorMaster extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_VendorMaster(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//h4[text()='MANAGE INVENTORY VENDOR']")
    public WebElement header_vendorMasterTitle;

    @FindBy(xpath = "//input[@placeholder='Search Vendor Name']")
    public WebElement input_searchVendorNameBox;

    @FindBy(xpath = "//a[text()=' Add Vendor']")
    public WebElement button_addVendorButton;

    @FindBy(xpath = "//tr[@class='each-contact-table-row']/td[3]")
    public List<WebElement> list_buttonColumnLinkedToStores;

    @FindBy(xpath = "//h5[text()='Add New Vendor']")
    public WebElement header_addVendorMasterTemplateTitle;

    @FindBy(xpath = "//input[@id='inventory_vendor_name']")
    public WebElement input_inventoryVendorName;

    @FindBy(xpath = "//label[@id='inventory_vendor_name-error']")
    public WebElement label_inventoryVendorNameRequiredError;

    @FindBy(xpath = "//label[@id='inventory_vendor_mobile_number-error']")
    public WebElement label_inventoryVendorMobileNumberRequiredError;

    @FindBy(xpath = "//span[@aria-labelledby='select2-inventory_vendor_vendor_group_id-container']//span[@class='select2-selection__arrow']")
    public WebElement button_inventoryVendorGroupArrow;

    @FindBy(xpath = "//input[@class='select2-search__field']")
    public WebElement input_inventoryDropdownCommonInputBox;


    @FindBy(xpath = "//ul[@class='select2-results__options']/li")
    public List<WebElement> list_inventoryVendorDropdownResultOptions;

    @FindBy(xpath = "//input[@id='inventory_vendor_mobile_number']")
    public WebElement input_inventoryVendorMobileNumber;

    @FindBy(xpath = "//input[@id='inventory_vendor_company_name']")
    public WebElement input_inventoryVendorCompanyName;

    @FindBy(xpath = "//input[@id='inventory_vendor_contact_person']")
    public WebElement input_inventoryVendorContactPerson;

    @FindBy(xpath = "//input[@id='inventory_vendor_email']")
    public WebElement input_inventoryVendorEmail;

    @FindBy(xpath = "//input[@id='inventory_vendor_secondary_mobile_number']")
    public WebElement input_inventoryVendorSecondaryMobileNumber;

    @FindBy(xpath = "//input[@id='inventory_vendor_pan_number']")
    public WebElement input_inventoryVendorPanNumber;

    @FindBy(xpath = "//input[@id='inventory_vendor_cin_number']")
    public WebElement input_inventoryVendorCinNumber;

    @FindBy(xpath = "//input[@id='inventory_vendor_msme_number']")
    public WebElement input_inventoryVendorMsmeNumber;

    @FindBy(xpath = "//input[@id='inventory_vendor_location_credit_days']")
    public WebElement input_inventoryVendorCreditDays;

    @FindBy(xpath = "//input[@id='inventory_vendor_credit_limit']")
    public WebElement input_inventoryVendorCreditLimit;

    @FindBy(xpath = "//input[@id='inventory_vendor_location_dl_number']")
    public WebElement input_inventoryVendorDlNumber;

    @FindBy(xpath = "//input[@id='inventory_vendor_gst_number']")
    public WebElement input_inventoryVendorGstNumber;

    @FindBy(xpath = "//span[@aria-labelledby='select2-inventory_vendor_country_id-container']//span[@class='select2-selection__arrow']")
    public WebElement button_inventoryVendorCountryArrow;

    @FindBy(xpath = "//input[@id='inventory_vendor_address']")
    public WebElement input_inventoryVendorAddress;

    @FindBy(xpath = "//tbody[@id='vendor_master-list']/tr")
    public List<WebElement> list_vendorRowForVendorMaster;

    @FindBy(xpath = "//tbody[@id='vendor_master-list']/tr/td[1]")
    public List<WebElement> list_vendorNameForVendorMaster;

    @FindBy(xpath = "//tbody[@id='vendor_master-list']/tr/td[3]")
    public List<WebElement> list_vendorActionsForVendorName;

    @FindBy(xpath = "//h4[text()='Link Existing Category']")
    public WebElement header_linkCategoryTemplateTitle;


    @FindBy(xpath = "//h4[text()='UnLink Existing Category']")
    public WebElement header_unlinkCategoryTemplateTitle;

    @FindBy(xpath = "//input[@class='select2-search__field']")
    public WebElement input_selectCategorySearchBox;

    @FindBy(xpath = "//ul[@class='select2-results__options']/li")
    public List<WebElement> list_selectCategoryValuesList;


    @FindBy(xpath = "//input[@onclick='link_unlink_category()']")
    public WebElement button_saveLinkCategoryInVendorMaster;

    @FindBy(xpath = "//input[@onclick='validate_inventory_vendor_form()']")
    public WebElement button_saveInVendorMaster;

    @FindBy(xpath = "//mark[text()='Only .dcm images are supported']")
    public WebElement text_alertMessageInUploadDocument;

    @FindBy(xpath = "//span[@class='btn btn-success fileinput-button dz-clickable']")
    public WebElement button_addFileInUploadDocument;

    @FindBy(xpath = "//input[@value='Upload Files']")
    public WebElement button_uploadFileInUploadDocument;

    @FindBy(xpath = "//h5[text()='Vendor Documents']")
    public WebElement header_viewDocumentTemplateHeader;

    @FindBy(xpath = "//div[@class='modal-content']//button[@data-dismiss='modal']")
    public WebElement button_closeViewDocumentTemplate;

    @FindBy(xpath = "//tbody[@id='vendor_master-list']/tr//b[@class='vendor_name']")
    public List<WebElement> list_text_vendorName;
    @FindBy(xpath = "//a[contains(text(),'Location')]")
    public List<WebElement> list_btn_vendorLocation;

    @FindBy(xpath = "//table[@id='manage_inventory_stores_table']/thead/tr/th[text()='Address']/parent::tr/th")
    public List<WebElement> list_btn_vendorLocationHeader;

    @FindBy(xpath = "//a[contains(text(),' Vendor Location')]")
    public WebElement button_addVendorLocation;

    @FindBy(xpath = "//input[contains(@class,'gst')][@type='text']")
    public WebElement input_gstNo;

    @FindBy(xpath = "//button[text()='Close']")
    public WebElement button_close;
    @FindBy(xpath = "//a[contains(text(),'Link Category')]")
    public List<WebElement> list_btn_linkCategory;
    @FindBy(xpath = "//table[@id='manage_inventory_vendors_table']//a[text()='Edit']")
    public List<WebElement> list_btn_editVendor;
    @FindBy(xpath = "//input[@id='inventory_vendor_fitting_charges']")
    public WebElement input_fittingCharges;
    @FindBy(xpath = "//input[contains(@id,'po_expiry_days')]")
    public WebElement input_expiryPOAfterDays;
}
