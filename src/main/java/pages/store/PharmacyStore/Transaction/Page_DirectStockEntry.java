package pages.store.PharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_DirectStockEntry extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_DirectStockEntry(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = " //div[@class='mainpanel']//h4")
    public WebElement header_panelOnLeft;

    @FindBy(xpath = "//ul[@class='nav nav-pills nav-stacked nav-bracket']/li/a")
    public List<WebElement> list_ParentOptionsOnLeft;

    @FindBy(xpath = "//ul[@class='nav nav-pills nav-stacked nav-bracket']/li/a/span")
    public List<WebElement> list_namesParentOptionsOnLeft;

    @FindBy(xpath = "//ul[@class='nav nav-pills nav-stacked nav-bracket']/li//li")
    public List<WebElement> list_ChildOptionsOnLeft;

    @FindBy(xpath = "//i[@class='fa fa-plus']")
    public WebElement button_addNew;

    @FindBy(xpath = "//span[@aria-labelledby='select2-vendor_id-container']")
    public WebElement dropdown_selectVendorInStore;

    @FindBy(xpath = "//ul[@id='select2-vendor_id-results']/li")
    public List<WebElement> list_selectVendorInStore;

    @FindBy(xpath = "//h4[contains(text(),'Direct Stock Entry')]")
    public WebElement header_DirectStockEntry;

    @FindBy(xpath = "//input[@id='variants_search']")
    public WebElement input_variantSearch;

    @FindBy(xpath = "//table[@class='table items-variants-table']//tr/td[2]")
    public List<WebElement> list_itemNameInStore;

    @FindBy(xpath = "//h4[contains(text(),'Add Lot')]")
    public WebElement header_addNewLot;
    @FindBy(xpath = "//input[contains(@id,'direct_stock_transaction_date')]")
    public WebElement input_transactionDate;

    @FindBy(xpath = "//input[contains(@id,'direct_stock_transaction_time_picker')]")
    public WebElement input_transactionTime;

    @FindBy(xpath = "//input[@name='commit']")
    public WebElement button_saveChanges;

    @FindBy(xpath = "//label[contains(@id,'bill_type-error')]")
    public WebElement label_billTypeRequired;

    @FindBy(xpath = "//label[@id='inventory_transaction_direct_stock_total_cost-error']")
    public WebElement label_totalCostRequired;

    @FindBy(xpath = "//label[@id='inventory_transaction_direct_stock_net_amount-error']")
    public WebElement label_netAmountRequired;

    @FindBy(xpath = "//select[@id='sub_store_id']")
    public WebElement select_subStore;

    @FindBy(xpath ="//input[@id='expiry']")
    public WebElement input_expiryDate;

    @FindBy(xpath = "//input[@id='unit_cost_without_tax']")
    public WebElement input_unitCostWOTax;

    @FindBy(xpath = "//input[@id='list_price_pack']")
    public WebElement input_sellingPrice;
    @FindBy(xpath = "//input[@id='stock_unit']")
    public WebElement input_packageQuantity;

    @FindBy(xpath = "//button[text()='Cancel']")
    public WebElement button_closeTemplateWithoutSaving;

    @FindBy(xpath = "//input[@id='unit_cost']")
    public WebElement text_unitCostWithTax;

    @FindBy(xpath = "//input[@id='total_cost']")
    public WebElement input_totalCost;

    @FindBy(xpath = "(//input[@name='commit'])[2]")
    public WebElement button_saveLot;

    @FindBy(xpath = "//input[@id='inventory_transaction_direct_stock_note']")
    public WebElement input_transactionNote;

    @FindBy(xpath = "//select[@id='inventory_transaction_direct_stock_bill_type']")
    public WebElement select_billType;

    @FindBy(xpath = "//input[@id='inventory_transaction_direct_stock_bill_number']")
    public WebElement input_billNumber;

    @FindBy(xpath = "//input[@id='inventory_transaction_direct_stock_bill_date']")
    public WebElement input_billDate;

    @FindBy(xpath = "//input[@id='inventory_transaction_direct_stock_challan_number']")
    public WebElement input_challanNumber;

    @FindBy(xpath = "//input[@id='inventory_transaction_direct_stock_challan_date']")
    public WebElement input_challanDate;

    @FindBy(xpath = "//input[@id='inventory_search']")
    public WebElement input_itemNameSearch;

    @FindBy(xpath = "//div[@class='inventory-items']//table/tbody/tr")
    public List<WebElement> list_directStockEntryTransactions;

    @FindBy(xpath = "//a[text()='Approve']")
    public WebElement button_Approve;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tbody/tr/td[1]")
    public WebElement text_variantDescription;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tbody/tr/td[5]")
    public WebElement text_Quantity;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tbody/tr/td[6]")
    public WebElement text_totalPrice;

    @FindBy(xpath = "//div/b[contains(text(),'Taxable Amount')]/parent::div//following-sibling::div[2]")
    public WebElement text_taxableAmount;

    @FindBy(xpath = "//input[@id='inventory_transaction_direct_stock_vendor_location_address']")
    public WebElement input_vendorNameField;

    @FindBy(xpath = "//input[@id='inventory_transaction_direct_stock_total_cost']")
    public WebElement input_totalAmountIncludingTax;
    @FindBy(xpath = "//input[@id='inventory_transaction_direct_stock_discount']")
    public WebElement input_discountBox;
    @FindBy(xpath = "//input[@id='inventory_transaction_direct_stock_net_amount']")
    public WebElement input_netAmount;
    @FindBy(xpath = "//div[@class='inventory-show-panel']//h3")
    public WebElement header_viewDirectStockEntryNoteHeader;
    @FindBy(xpath = "//b[contains(text(),'Status:')]")
    public WebElement text_statusKeyAndValue;
    @FindBy(xpath = "//div[@class='inventory-show-panel']/div/div/div[2]")
    public WebElement text_billNumberAndDateKeyAndValue;
    @FindBy(xpath = "//div[@class='inventory-show-panel']/div/div[2]//b")
    public WebElement text_transactionIDKeyAndValue;
    @FindBy(xpath = "//a[contains(text() ,'Cancel')]")
    public WebElement button_cancelDSEButton;
    @FindBy(xpath = "//div[@id='confirmation-modal']//p")
    public WebElement text_confirmationMessage;

    @FindBy(xpath = "//a[@class='btn btn-primary btn-xs']/i")
    public WebElement button_editDirectStockEntryButton;
    @FindBy(xpath = "//button[@class='btn btn-xs btn-danger cancel-stock-opening']/i")
    public WebElement button_cancelDirectStockEntryButton;
    @FindBy(xpath = "//input[@id='search_vendor_address']")
    public WebElement input_searchVendorBox;
    @FindBy(xpath = "//a[contains(text(),'DOPEI VENDOR - Address1')]")
    public WebElement text_vendorNameInVendorDropdown;







}
