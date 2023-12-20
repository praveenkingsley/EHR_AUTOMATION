package pages.store.PharmacyStore.Items;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Variant extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_Variant(WebDriver driver) {
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
    public WebElement input_itemNameSearchInItemVariant;
    @FindBy(xpath = "//tbody[@id='inventory_table_body']//tr/td[2]")
    public List<WebElement> list_itemDescriptionNameOnItemVariant;

    @FindBy(xpath = "//tbody[@id='inventory_table_body']//tr/td[3]")
    public List<WebElement> list_itemStockOnItemVariant;

    @FindBy(xpath = "//div/span[text()='Stock:']//parent::div/following-sibling::div")
    public WebElement text_TotalStock;

    @FindBy(xpath = "//a[text()=' New']")
    public WebElement button_addNew;

    @FindBy(xpath = "//a[@id='edit_inventory_item']")
    public WebElement button_editItem;

    @FindBy(xpath = "//input[@id='inventory_item_description']")
    public WebElement input_itemDescription;

    @FindBy(xpath = "//input[@type='submit']")
    public WebElement button_save;

    @FindBy(xpath = "//button[@id='btn_remove_item_confirm']")
    public WebElement button_deleteVariant;

    @FindBy(xpath = "//strong[contains(@class,'variant_code variant_code_value')]")
    public WebElement text_variantCode;

    @FindBy(xpath = "//input[@id='input_remove_confirmation']")
    public WebElement input_variantCodeForDelete;

    @FindBy(xpath = "//a[@id='verified_item_remove']")
    public WebElement button_confirmDeleteItem;

    @FindBy(xpath = "//div/span[text()='Item Code:']//parent::div/following-sibling::div[1]")
    public WebElement text_itemCode;
    @FindBy(xpath = "//div/span[text()='Barcode:']//parent::div/following-sibling::div")
    public WebElement text_barCode;
    @FindBy(xpath = "//tbody[@id='inventory_table_body']//tr/td[4]")
    public List<WebElement> list_itemCategoryOnItemVariant;

    /////////////////////////////////////////////////////////
    @FindBy(xpath = "//i[@class='fa fa-filter']")
    public WebElement button_filterButton;
    @FindBy(xpath = "//div[contains(text(),'Filter Variant Items')]")
    public WebElement text_filterHeader;
    @FindBy(xpath = "//a[@class='btn btn-success search-variant-filter']")
    public WebElement button_filterApplyButton;
    @FindBy(xpath = "//button[normalize-space()='Close']")
    public WebElement button_filterCloseButton;
    @FindBy(xpath = "//button[normalize-space()='Clear to default']")
    public WebElement button_filterClearButton;
    @FindBy(xpath = "//tr[@class='inventory-list-row variant-stock']/td[3]")
    public List<WebElement> list_searchItemsWithStock;
    @FindBy(xpath = "//tr[@class='inventory-list-row variant-running-low']")
    public List<WebElement> list_searchRunningLowStock;
    @FindBy(xpath = "//tr[@class='inventory-list-row variant-empty']/td[3]")
    public List<WebElement> list_searchOutOfStock;
    @FindBy(xpath = "//tbody[@id='inventory_table_body']/tr/td[4]")
    public List<WebElement> list_searchItemsWithCategory;
    @FindBy(xpath = "//span[@class='clear-filters-tag']")
    public WebElement button_clearVariantFilterButton;
    @FindBy(xpath = "//span[@class='clear_stock clear_btn']")
    public WebElement button_clearFilterItemByStock;
    @FindBy(xpath = "//span[@class='clear_category clear_btn']")
    public WebElement button_clearSortBy;
    @FindBy(xpath = "//tbody[@id='inventory_table_body']/tr/td[2]/b")
    public List<WebElement> list_searchItemsWithDescription;



}
