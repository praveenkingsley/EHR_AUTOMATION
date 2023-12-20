package pages.settings.organisationSettings.inventoryAndSupplyChain;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_ItemMaster extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_ItemMaster(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }
    @FindBy(xpath = "//a[text()=' Add Item']")
    public WebElement button_addItem;

    @FindBy(xpath = "//input[contains(@id,'hsn_no')]")
    public WebElement input_itemHsnCode;

    @FindBy(xpath = "//input[@id='inventory_item_brand']")
    public WebElement input_itemBrandCompany;

    @FindBy(xpath = "//input[@id='inventory_item_description']")
    public WebElement input_itemDescription;

    @FindBy(xpath = "//input[@id='inventory_item_manufacturer']")
    public WebElement input_itemManufacturerName;

    @FindBy(xpath = "//span[@aria-labelledby='select2-item_sub_category-container']//span[@class='select2-selection__arrow']")
    public WebElement button_subCategoryDropdownArrow;

    @FindBy(xpath = "//ul[@id='select2-item_sub_category-results']/li")
    public List<WebElement> list_inventoryItemSubCategoryList;

    @FindBy(xpath = "//button[@class='close close-item-modal']/span")
    public WebElement button_closeItemMasterTemplate;

    @FindBy(xpath = "//span[@aria-labelledby='select2-item_category-container']//span[@class='select2-selection__arrow']")
    public WebElement dropdown_categoryArrow;

    @FindBy(xpath = "//ul[@id='select2-item_category-results']/li")
    public List<WebElement> list_inventoryItemCategoryList;

    @FindBy(xpath = "//span[@id='select2-inventory_item_dispensing_unit_id-container']")
    public WebElement textbox_dispensingUnitDropdownBox;

    @FindBy(xpath = "//span[@aria-labelledby='select2-inventory_item_dispensing_unit_id-container']//span[@class='select2-selection__arrow']")
    public WebElement dropdown_dispensingUnitArrow;

    @FindBy(xpath = "//span[@class='select2-search select2-search--dropdown']/input[@class='select2-search__field']")
    public WebElement input_itemMasterInputBox;

    @FindBy(xpath = "//ul[@id='select2-inventory_item_dispensing_unit_id-results']/li")
    public List<WebElement> list_inventoryItemDispensingUnitList;

    @FindBy(xpath = "//select[@id='item_category']")
    public WebElement select_itemCategory;

    @FindBy(xpath = "//select[contains(@id,'dispensing_unit_id')]")
    public WebElement select_itemDispensingUnit;

    @FindBy(xpath = "//input[@id='inventory_search']")
    public WebElement input_searchInventoryInStoreInventory;
    @FindBy(xpath = "//h4[text()='MANAGE INVENTORY ITEM']")
    public WebElement header_itemMasterTitle;

    @FindBy(xpath = "//h4[text()='Add New Item to Inventory']")
    public WebElement header_addItemMasterTemplateTitle;

    @FindBy(xpath = "//select[contains(@class,'form-control p7_10 mb5 item-custom-field-value')]")
    public WebElement select_itemPossibleVariantValue;
    @FindBy(xpath = "//tbody[@id='inventory_table_body']/tr")
    public List<WebElement> list_itemListInStoreInventory;

    @FindBy(xpath = "//tbody[@id='inventory_table_body']/tr")
    public WebElement list_firstItemListInStoreInventory;

    @FindBy(xpath = "//tbody[@id='inventory_table_body']")
    public WebElement list_emptyItemListInStoreInventory;

    @FindBy(xpath = "//input[@id='inventory_item_sub_category_name']")
    public WebElement input_inventoryItemSubCategoryName;
    @FindBy(xpath = "//input[@onclick='addItem_page()']")
    public WebElement button_saveAddItemTemplate;
    @FindBy(xpath = "//span[@id='select2-inventory_item_package_type-container']")
    public WebElement span_inventoryItemPackageType;
    @FindBy(xpath = "//input[contains(@name,'[sub_package_units]')]")
    public WebElement input_inventoryItemSubPackageUnit;
    @FindBy(xpath = "//span[@id='select2-inventory_item_sub_package_type-container']")
    public WebElement input_inventoryItemSubPackageType;
    @FindBy(xpath = "//input[contains(@name,'[item_units]')]")
    public WebElement input_inventoryItemSubPackageItemUnit;
    @FindBy(xpath = "//input[@id='inventory_item_item_type']")
    public WebElement input_inventoryItemSubPackageItemType;
    @FindBy(xpath = "//input[@id='inventory_item_fixed_threshold']")
    public WebElement input_inventoryItemFixedThreshold;
    @FindBy(xpath = "//input[@id='item_medicine_class']")
    public WebElement input_inventoryItemMedicineClass;
    @FindBy(xpath = "//a[@class='btn btn-success btn-sm']")
    public WebElement button_classFilterButton;

    @FindBy(xpath = "//select[@id='inventory_item_tax_group_id']/option")
    public List<WebElement> list_inventoryItemPropertiesTaxList;
    @FindBy(xpath = "//input[@id='inventory_item_expiry_present']")
    public WebElement checkbox_propertiesExpiryPresent;
    @FindBy(xpath = "//input[@id='inventory_item_prescription_mandatory']")
    public WebElement checkbox_propertiesPrescriptionMandatory;
    @FindBy(xpath = "//input[@id='inventory_item_unit_level']")
    public WebElement checkbox_propertiesUnitLevel;
    @FindBy(xpath = "//input[@id='inventory_item_stockable']")
    public WebElement checkbox_propertiesStockable;
    @FindBy(xpath = "//input[@id='inventory_item_generic_names_attributes_0_name']")
    public WebElement input_itemGenericCompositionName;
    @FindBy(xpath = "//a[contains(text(),'Hexapeptide-11')]")
    public WebElement name_itemGenericCompositionHexapeptide;
    @FindBy(xpath = "//a[contains(text(),'Vitamin Aasetat')]")
    public WebElement name_itemGenericCompositionVitaminAasetat;
    @FindBy(xpath = "//input[@id='inventory_item_generic_names_attributes_0_quantity']")
    public WebElement input_itemGenericCompositionValue;
    @FindBy(xpath = "//select[@id='inventory_item_generic_names_attributes_0_unit']")
    public WebElement select_itemGenericCompositionUnit;
    @FindBy(xpath = "//select[@id='item_sub_category']")
    public WebElement select_editItemSubCategory;
    @FindBy(xpath = "//select[contains(@id,'inventory_item_tax_group_id')]")
    public WebElement select_itemPropertiesTaxList;
    @FindBy(xpath = "//input[contains(@class,'form-control p7_10 mb5 item-custom-field-name')]")
    public WebElement input_itemPossibleVariantName;

    @FindBy(xpath = "//input[contains(@class,'form-control p7_10 mb5 item-custom-field-name')]")
    public List<WebElement> list_input_itemPossibleVariantNameList;

    @FindBy(xpath = "//input[@class='select2-search__field']")
    public WebElement input_itemPossibleVariantValue;
    @FindBy(xpath = "//div[@id='manage_inventory_item_master']//input")
    public WebElement input_inventoryItemSearch;
    @FindBy(xpath = "//div[text()='Search']")
    public WebElement div_searchButton;

    @FindBy(xpath = "//tbody[@id='item_master-list']/tr")
    public List<WebElement> list_itemRowListInItemMaster;
    @FindBy(xpath = "//tbody[@id='item_master-list']/tr/td/a[@id='edit_inventory_item']")
    public WebElement button_searchedItemEditButton;
    @FindBy(xpath = "//h4[text()='Edit Inventory Item']")
    public WebElement header_editItemHeader;
    @FindBy(xpath = "//ul[@class='select2-results__options']/li")
    public List<WebElement> list_itemPossibleVariantValuesList;
    @FindBy(xpath = "//div[@class='col-md-7 min-stock-percentages item-label']")
    public WebElement text_miniStock;
    @FindBy(xpath = "//button[contains(@class,'remove-item-custom')]")
    public WebElement button_removePossibleVariant;

    @FindBy(xpath = "//input[@id='inventory_item_tax_inclusive']")
    public WebElement checkBox_taxInclusive;

    @FindBy(xpath = "//select[contains(@name,'[package_type]')]")
    public WebElement select_inventoryItemPackageType;
    @FindBy(xpath = "//select[contains(@name,'[sub_package_type]')]")
    public WebElement select_inventoryItemSubPackageType;
    @FindBy(xpath = "//select[contains(@name,'[item_type]')]")
    public WebElement select_inventoryItemUnitType;
    @FindBy(xpath = "//select[contains(@id,'inventory_item') and contains(@id,'custom')]")
    public List<WebElement> list_selectItemPossibleVariantValue;

   ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FindBy(xpath = "//button[@class='btn btn-primary btn-sm inventory_search_panel_dropdown']/following-sibling::ul[@class='dropdown-menu']/li")
    public List<WebElement> list_searchFilterItemDescriptionDropdown;

    @FindBy(xpath = "//span[@id='inventory_search_criteria']")
    public WebElement select_searchFilterItemDescription;

    @FindBy(xpath = "//input[@id='inventory_search']")
    public WebElement input_searchbarCriteria;

    @FindBy(xpath = "//button[@class='btn btn-primary inventory-search-button']")
    public WebElement button_searchItem;

    @FindBy(xpath = "//tbody[@id='c']/tr")
    public WebElement list_searchItem;

    @FindBy(xpath = "//tbody[@id='inventory_table_body']/tr")
    public List<WebElement> list_searchItem1;


    @FindBy(xpath = "//div/span[text()='Description:']//parent::div/following-sibling::div")
    public WebElement text_searchItemDescriptionName;

    @FindBy(xpath = "//tbody[@id='inventory_table_body']/tr/td/div[3]/span[1]/b")
    public WebElement text_searchBatchNo;

    @FindBy(xpath = "//span[text()='Variant Code:']/parent::div/following-sibling::div[1]/strong")
    public WebElement text_searchVariantCode;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tbody//td[2]")
    public WebElement text_searchTransactionNo;

    @FindBy(xpath = "//div/span[text()='Item Code:']//parent::div/following-sibling::div[1]")
    public WebElement text_itemCode;

    @FindBy(xpath = "//div/span[text()='Barcode:']//parent::div/following-sibling::div")
    public WebElement text_searchBarCode;

    @FindBy(xpath = "//h4[contains(text(),'Item Level')]/following-sibling::div//h4[contains(text(),'Out Of Stock')]/a")
    public WebElement link_outOfStockItemLevel;
    @FindBy(xpath = "//h4[contains(text(),'Item Level')]/following-sibling::div//h4[contains(text(),'Running Low Items')]/a")
    public WebElement link_runningLowItemLevel;

    @FindBy(xpath = "//h4[contains(text(),'Variant Level')]/following-sibling::div//h4[contains(text(),'Out Of Stock')]/a")
    public WebElement link_outOfStockVariantLevel;
    @FindBy(xpath = "//h4[contains(text(),'Variant Level')]/following-sibling::div//h4[contains(text(),'Running Low Items')]/a")
    public WebElement link_runningLowVariantLevel;
    @FindBy(xpath = "//button[@class='btn btn-primary inventory-clear-button']")
    public WebElement button_clearButtonInSearchBx;

    @FindBy(xpath = "//select[@id='inventory_order_indent_store_id']")
    public WebElement select_category;

    @FindBy(xpath = "//select[@id='sub_category_id']")
    public WebElement select_subCategory;

    @FindBy(xpath = "//tbody[@id='item_master-list']//tr//td[4]")
    public List<WebElement> list_linkedSubcategoryLinkedForSelectedCategory;
    /////////////////////////////////////////////////////////
    @FindBy(xpath = "//i[@class='fa fa-filter']")
    public WebElement button_filterButton;
    @FindBy(xpath = "//div[contains(text(),'Filter Master Items')]")
    public WebElement text_filterHeader;
    @FindBy(xpath = "//a[@class='btn btn-success search-item-filter']")
    public WebElement button_filterApplyButton;
    @FindBy(xpath = "//button[normalize-space()='Close']")
    public WebElement button_filterCloseButton;
    @FindBy(xpath = "//button[normalize-space()='Clear to default']")
    public WebElement button_filterClearButton;
    @FindBy(xpath = "//tr[@class='inventory-list-row item-stock']/td[3]/b")
    public List<WebElement> list_searchItemsWithStock;
    @FindBy(xpath = "//tr[@class='inventory-list-row item-running-low']")
    public List<WebElement> list_searchRunningLowStock;
    @FindBy(xpath = "//tr[@class='inventory-list-row item-empty']/td[3]")
    public List<WebElement> list_searchOutOfStock;
    @FindBy(xpath = "//tbody[@id='inventory_table_body']/tr/td[5]")
    public List<WebElement> list_searchItemsWithCategory;
    @FindBy(xpath = "//span[@class='filters-tag']")
    public WebElement button_clearMasterFilterButton;
    @FindBy(xpath = "//span[@class='clear_stock clear_btn']")
    public WebElement button_clearFilterItemByStock;
    @FindBy(xpath = "//span[@class='clear_category clear_btn']")
    public WebElement button_clearSortBy;
    @FindBy(xpath = "//tbody[@id='inventory_table_body']/tr/td[2]/b")
    public List<WebElement> list_searchItemsWithDescription;

}
