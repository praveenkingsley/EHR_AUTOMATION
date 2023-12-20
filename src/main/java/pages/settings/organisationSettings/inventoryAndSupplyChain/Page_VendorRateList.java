package pages.settings.organisationSettings.inventoryAndSupplyChain;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_VendorRateList extends TestBase{
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_VendorRateList(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }
    @FindBy(xpath = "//div[@id='manage_inventory_vendor_rate_list_master']//div/a")
    public WebElement button_addVendorRate;
    @FindBy(xpath = "//select[@id='vendor_filter']")
    public WebElement dropdown_vendorFilter;
    @FindBy(xpath = "//input[contains(@class,'search-vendor-rate-list')]")
    public WebElement input_searchVendorGroup;
    // Add vendor rate form
    @FindBy(xpath = "//select[@id='vendor_id']")
    public WebElement dropdown_vendorSelect;

    @FindBy(xpath = "//div[@class='main-vendor-rate-list']/div/div[2]")
    public List<WebElement> list_itemNameForVendorRate;

    @FindBy(xpath = "//input[contains(@class,'vendor_item_rate')]")
    public List<WebElement> list_input_itemRate;

    @FindBy(xpath = "//input[@id='save-multiple-vendor-rates']")
    public WebElement button_saveVendorRateList;
    @FindBy(xpath = "//table[@id='manage_inventory_items_table']/tbody/tr/td[1]")
    public List<WebElement> list_itemNameInTable;
    @FindBy(xpath = "//table[@id='manage_inventory_items_table']/tbody/tr/td[6]")
    public List<WebElement> list_itemRateInTable;
    @FindBy(xpath = "//table[@id='manage_inventory_items_table']/tbody/tr/td[7]")
    public List<WebElement> list_itemSellingPriceInTable;
    @FindBy(xpath = "//table[@id='manage_inventory_items_table']/tbody/tr/td[4]")
    public List<WebElement> list_vendorNameInTable;
    @FindBy(xpath = "//table[@id='manage_inventory_items_table']/tbody/tr/td[5]")
    public List<WebElement> list_vendorGroupNameInTable;
    @FindBy(xpath = "//table[@id='manage_inventory_items_table']/tbody/tr/td[8]/a")
    public List<WebElement> list_editItemRate;

    //edit vendor rate form elements
    @FindBy(xpath = "//select[@id='inventory_vendor_rate_vendor_group_id']")
    public WebElement dropdown_vendorGroupName;
    @FindBy(xpath = "//input[@id='inventory_vendor_rate_rate']")
    public WebElement input_editRate;
    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveChanges;

    // store elements
    @FindBy(xpath = "//div[contains(@class,'inventory-item-toolbar')]/nav/div/div[2]/div/a")
    public WebElement button_transactionPurchaseNew;

    @FindBy(xpath = "//span[@aria-labelledby='select2-vendor_id-container']")
    public WebElement dropdown_selectVendorInStore;

    @FindBy(xpath = "//ul[@id='select2-vendor_id-results']/li")
    public List<WebElement> list_selectVendorInStore;
    @FindBy(xpath = "//h4[text()='Add New Stock to Inventory']")
    public WebElement header_addNewStock;
    @FindBy(xpath = "//table[@class='table items-variants-table']//tr/td[2]")
    public List<WebElement> list_itemNameInPurchaseStore;
    @FindBy(xpath = "//h4[contains(text(),'Add Lot')]")
    public WebElement header_addNewLot;
    @FindBy(xpath = "//input[@id='unit_cost_without_tax']")
    public WebElement input_unitCostWOTax;

    @FindBy(xpath = "//input[@id='search-item-variants']")
    public WebElement input_searchItemVariant;
    @FindBy(xpath = "//select[@id='vendor_filter']")
    public WebElement select_vendorFilter;

}
