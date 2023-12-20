package pages.settings.organisationSettings.inventoryAndSupplyChain;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_VendorGroupMaster extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_VendorGroupMaster(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//table[@id='manage_inventory_stores_table']//tr[@class='each-contact-table-row']//td/b[@class='facility_name']")
    public List<WebElement> list_FacilityName;

    @FindBy(xpath = "//table[@id='manage_inventory_stores_table']//tr[@class='each-contact-table-row']//td[3]/table/tbody/tr/td[2]")
    public List<WebElement> list_linkButton;

    @FindBy(xpath = "//tr[@class='each-contact-table-row']//b[@class='facility_name']")
    public List<WebElement> list_facilityNamesForInventory;

    @FindBy(xpath = "//tr[@class='each-contact-table-row']/td[2]")
    public List<WebElement> list_storeColumnLinkedToFacility;

    @FindBy(xpath = "//tr[@class='each-contact-table-row']/td[3]")
    public List<WebElement> list_buttonColumnLinkedToStores;

    @FindBy(xpath = "//a[text()=' Add Vendor Group']")
    public WebElement button_addVendorGroupMaster;

    @FindBy(xpath = "//a[text()=' Add Vendor']")
    public WebElement button_addVendor;

    @FindBy(xpath = "//a[@id='manage_inventory_vendor_master_id']")
    public WebElement select_vendorMaster;

    @FindBy(xpath = "//input[@id='inventory_vendor_group_name']")
    public WebElement input_vendorGroupName;

    @FindBy(xpath = "//input[@id='inventory_vendor_group_description']")
    public WebElement input_vendorGroupDescription;

    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveVendorGroup;

    @FindBy(xpath = "//button[text()='Close']")
    public WebElement button_closeAddVendorGroupPopUp;

    @FindBy(xpath = "//*[@id=\"inventory_vendor_form\"]/div[2]/div[2]/div[2]/span/span[1]/span")
    public WebElement select_vendorGroupField;

    @FindBy(xpath = "//ul[@class='select2-results__options']//li")
    public List<WebElement> list_vendorGroupUnderAddNewVendorPopUp;

    @FindBy(xpath = "//input[@placeholder='Search Vendor Group Name']")
    public WebElement search_searchVendorGroupMaster;

    @FindBy(xpath = "//button[text()='Cancel']")
    public WebElement button_closeAddNewVendorPopUp;

    @FindBy(xpath = "//tbody[@id='vendor_group_master-list ']/tr/td[1]")
    public List<WebElement> list_vendorGroupName;

    @FindBy(xpath = "//tbody[@id='vendor_group_master-list ']//tr/td[2]/b")
    public List<WebElement> list_vendorGroupDescription;

    @FindBy(xpath = "//tbody[@id='vendor_group_master-list ']//tr/td[3]/table/tbody/tr/td[1]/a")
    public List<WebElement> list_editVendorGroupButton;

    @FindBy(xpath = "//tbody[@id='vendor_group_master-list ']//tr/td[3]")
    public List<WebElement> list_actionButtonsVendorGroup;

    @FindBy(xpath = "//tbody[@id='vendor_group_master-list ']//tr/td[3]/table/tbody/tr/td[2]/a")
    public List<WebElement> list_activeVendorGroupButton;

    @FindBy(xpath = "//input[@class='search-vendor-group-master form-control']")
    public WebElement input_searchVendorGroup;

    @FindBy(xpath = "//div[@id='confirmation-modal']//a[text()='Confirm']")
    public WebElement button_confirmDisableVendorGroup;


}
