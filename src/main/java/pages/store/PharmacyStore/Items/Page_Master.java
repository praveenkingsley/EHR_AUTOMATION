package pages.store.PharmacyStore.Items;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Master extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_Master(WebDriver driver) {
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

    @FindBy(xpath = "//input[@id='inventory_search']")
    public WebElement input_itemNameSearchInItemMaster;
    @FindBy(xpath = "//tbody[@id='inventory_table_body']//tr/td[2]")
    public List<WebElement> list_itemDescriptionNameOnItemMaster;
    @FindBy(xpath = "//tbody[@id='inventory_table_body']//tr/td[3]")
    public List<WebElement> list_itemStockOnItemMaster;

    @FindBy(xpath = "//div/span[text()='Stock:']//parent::div/following-sibling::div")
    public WebElement text_totalStock;

    @FindBy(xpath = "//a[text()=' New']")
    public WebElement button_addNew;

    @FindBy(xpath = "//a[@id='edit_inventory_item']")
    public WebElement button_editItem;

    @FindBy(xpath = "//input[@id='inventory_item_description']")
    public WebElement input_itemDescription;

    @FindBy(xpath = "//input[@type='submit']")
    public WebElement button_save;

    @FindBy(xpath = "//button[@id='btn_remove_item_confirm']")
    public WebElement button_deleteItem;
    @FindBy(xpath = "//strong[contains(@class,'item_code item_code_value')]")
    public WebElement text_itemCode;
    @FindBy(xpath = "//input[@id='input_remove_confirmation']")
    public WebElement input_itemCodeForDelete;
    @FindBy(xpath = "//a[@id='verified_item_remove']")
    public WebElement button_confirmDeleteItem;
    @FindBy(xpath = "//a[text()='Stock Availability']")
    public WebElement button_stockAvailability;
    @FindBy(xpath = "//button[@class='btn btn-link btn-xs btn-inventory-refresh']")
    public WebElement button_refreshItemMasterButton;
    @FindBy(xpath = "//a[@class='btn btn-primary btn-md navbar-btn navbar-btn-hover open_side_modal']")
    public WebElement button_filterButton;
    @FindBy(xpath = "//a[@class='btn btn-primary btn-primary-transparent download-invoice-data']")
    public WebElement button_reportDownloadButton;
    @FindBy(xpath = "//strong[text()='Item Details:']")
    public WebElement text_itemDetailsSection;
    @FindBy(xpath = "//strong[text()='Properties:']")
    public WebElement text_propertiesSection;
    @FindBy(xpath = "//input[@id='inventory_item_tax_inclusive']")
    public WebElement input_taxInclusive;
    @FindBy(xpath = "//strong[text()='Dispensing Unit:']")
    public WebElement text_dispensingUnitSection;
    @FindBy(xpath = "//strong[text()='Low Stock Warning:']")
    public WebElement text_lowScoreWarningSection;
    @FindBy(xpath = "//strong[text()='Class:']")
    public WebElement text_medicineClassSection;
    @FindBy(xpath = "//strong[text()='Generic Composition:']")
    public WebElement text_genericCompositionSection;
    @FindBy(xpath = "//strong[text()='Possible Variants:']")
    public WebElement text_possibleVariantSection;
    @FindBy(xpath = "//a[@title='Add extra fields with possible values']")
    public WebElement button_addPossibleVariantButton;
    @FindBy(xpath = "//thead[@class='inventory-table-head']//td")
    public List<WebElement> list_viewItemMasterTableHeaderList;
    @FindBy(xpath = "//div[@id='item_information']/div/div/div")
    public List<WebElement> list_keysAndValuesInItemViewRHSSide;

    @FindBy(xpath = "//b[text()='Lot Details:']")
    public WebElement text_lotDetailsSectionHeader;
    @FindBy(xpath = "//table[@class='table table-striped table-bordered']/thead/tr/th")
    public List<WebElement> list_lotDetailsTableHeaderList;
    @FindBy(xpath = "//table[@class='table table-striped table-bordered']/tbody/tr")
    public WebElement list_lotDetailsTableNoRowValues;
    @FindBy(xpath = "//table[@class='table table-striped table-bordered']/tbody/tr/td")
    public List<WebElement> list_lotDetailsTableRowValuesList;

    @FindBy(xpath = "//b[text()='Transaction History:']")
    public WebElement text_transactionHistorySectionHeader;
    @FindBy(xpath = "//table[@class='table table-bordered table-striped']/thead/tr/th")
    public List<WebElement> list_transactionHistoryTableHeaderList;
    @FindBy(xpath = "//table[@class='table table-bordered table-striped']/tbody/tr")
    public WebElement list_transactionHistoryTableNoRowValues;
    @FindBy(xpath = "//table[@class='table table-bordered table-striped']/tbody/tr/td")
    public List<WebElement> list_transactionHistoryTableRowValuesList;

    @FindBy(xpath = "//h4[text()='Stock Availability']")
    public WebElement header_stockAvailabilityTitleHeader;
    @FindBy(xpath = "//input[@id='search_batch']")
    public WebElement input_searchBatchInputBox;
    @FindBy(xpath = "//form[@id='vendor_lot_data']//table[@class='table table-striped table-bordered']/thead/tr/th")
    public List<WebElement> list_stockAvailabilityTableHeaderList;
    @FindBy(xpath = "//form[@id='vendor_lot_data']//table[@class='table table-striped table-bordered']/tbody/tr/td")
    public List<WebElement> list_stockAvailabilityTableRowValuesList;

    @FindBy(xpath = "//select[@id='inventory_item_package_type']")
    public WebElement select_selectPackageTypeEditItem;
    @FindBy(xpath = "//select[@id='inventory_item_sub_package_type']")
    public WebElement select_selectSubPackageTypeEditItem;
    @FindBy(xpath = "//select[@id='inventory_item_item_type']")
    public WebElement select_selectUnitSubPackageTypeEditItem;
    @FindBy(xpath = "//input[@id='inventory_item_fixed_threshold']")
    public WebElement input_fixedThresholdEditItem;
    @FindBy(xpath = "//div[contains(text(),'Dispensing Unit:')]/parent::div//strong")
    public WebElement text_dispensingUnit;
    @FindBy(xpath = "//*[contains(text(),'Sub Category:')]/following-sibling::div/strong")
    public WebElement text_subCategory;

}
