package pages.settings.organisationSettings.inventoryAndSupplyChain;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_StoreSetUp extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_StoreSetUp(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    //add new store elements
    @FindBy(xpath = "//div[@id='manage_inventory_store']//a[contains(@class,'add-normal-store')]")
    public WebElement button_addStore;

    @FindBy(xpath = "//input[@id='inventory_store_name']")
    public WebElement input_storeName;

    @FindBy(xpath = " //div[@class='dropdown open']//a[text()=' Users']")
    public WebElement button_users;

    @FindBy(xpath = "//div[@class='col-sm-12']//div")
    public List<WebElement> list_linkedAndUnlinkedUsersAndCategory;

    @FindBy(xpath = "//div[@class='dropdown open']//a[contains(@href,'category')]")
    public WebElement button_category;

    @FindBy(xpath = "//div[@class='col-sm-12']//div")
    public List<WebElement> list_linkedCategory;

    @FindBy(xpath = "//ul[@id='select2-category_ids-results']/li")
    public List<WebElement> list_categoriesInDropdown;

    @FindBy(xpath = "//ul[@id='select2-item_category-results']/li")
    public List<WebElement> list_categoriesInDropdownUnderAddNewItemToInventory;

    @FindBy(xpath = "//a[text()=' Add Item']")
    public WebElement button_addItem;

    @FindBy(xpath = "//span[@id='select2-item_category-container']")
    public WebElement select_categoryFieldUnderItemMaster;

    @FindBy(xpath = " //li[@class='select2-results__option']")
    public List<WebElement> list_categoryUnderItemMaster;

    @FindBy(xpath = "//a[text()=' New']")
    public WebElement button_addNewMaterItem;

    @FindBy(xpath = "//span[@class='selection']")
    public WebElement select_optionFromDropdownField;

    @FindBy(xpath = "//ul[@id='select2-user_ids-results']/li")
    public List<WebElement> list_usersInDropdown;

    @FindBy(xpath = "//span[text()=' Stores']")
    public WebElement button_store;

    @FindBy(xpath = "//a[@target='_blank']/../../li")
    public List<WebElement> list_storeLinkedToUser;

    @FindBy(xpath = "//input[@id='inventory_store_abbreviation_name']")
    public WebElement input_storeAbbreviation;

    @FindBy(xpath = "//span[contains(@aria-labelledby,'inventory_store_department_id')]")
    public WebElement dropdown_storeType;

    @FindBy(xpath = "//ul[@id='select2-inventory_store_department_id-results']/li")
    public List<WebElement> list_storeType;

    @FindBy(xpath = "//span[@title='Select Entity Group']")
    public WebElement dropdown_entityGroup;

    @FindBy(xpath = "//ul[contains(@id,'inventory_store_entity_group')]/li")
    public List<WebElement> list_storeEntityGroup;

    @FindBy(xpath = "//span[contains(@id,'select2-inventory_store_facility_id')]")
    public WebElement dropdown_selectFacility;

    @FindBy(xpath = "//ul[contains(@id,'select2-inventory_store_facility_id')]/li")
    public List<WebElement> list_storeFacilities;

    @FindBy(xpath = "//input[@id='inventory_store_mobilenumber']")
    public WebElement input_mobileNumber;

    @FindBy(xpath = "//span[@title='Select Country']")
    public WebElement dropdown_storeCountry;

    @FindBy(xpath = "//ul[contains(@id,'select2-inventory_store_shipping_addresses_attributes_0_country_id-results')]/li")
    public List<WebElement> list_countryNames;
    @FindBy(xpath = "//ul[contains(@id,'select2-inventory_store_billing_addresses_attributes_0_billing_country_id-results')]/li")
    public List<WebElement> list_countryNamesBilling;

    @FindBy(xpath = "//input[@id='inventory_store_shipping_addresses_attributes_0_address']")
    public WebElement input_storeAddress;


    @FindBy(xpath = "//input[@id='search_pincode_other']")
    public WebElement input_storePincode;
    @FindBy(xpath = "//input[@id='search_billing_pincode_other']")
    public WebElement input_storeBillingPincode;

    @FindBy(xpath = "//input[@id='search_state_other']")
    public WebElement input_storeState;

    @FindBy(xpath = "//input[@id='search_city_other']")
    public WebElement input_storeCity;

    @FindBy(xpath = "//input[@id='search_billing_state_other']")
    public WebElement input_storeBillingState;

    @FindBy(xpath = "//input[@id='search_billing_city_other']")
    public WebElement input_storeBillingCity;

    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveStore;

    @FindBy(xpath = "//input[@value='Update Changes']")
    public WebElement button_updateStore;

    @FindBy(xpath = "//button[text()='Close']")
    public WebElement button_closeEditStore;

    @FindBy(xpath = "//input[@value='Save']")
    public WebElement button_save;

    @FindBy(xpath = "//button[text()='Close']")
    public WebElement button_close;

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

    // Link Store
    @FindBy(xpath = "//button[@id='get_inventory_stores']")
    public WebElement button_StoreInventorySetupPage;

    @FindBy(xpath = "//h4[text()='MANAGE INVENTORY STORES']")
    public WebElement title_StoreSetup;

    @FindBy(xpath = "//div[@class='dropdown open']")
    public WebElement dropdown_actionPerformedOnLinkDropdown;

    @FindBy(xpath = "//div[@class='dropdown open']//ul//li/a")
    public List<WebElement> list_linkActionDropdown;

    @FindBy(xpath = "//h4[text()='Link Existing Store']")
    public WebElement header_linkExistingStore;

    @FindBy(xpath = "UnLink Existing Store")
    public WebElement header_unLinkExistingStore;

    @FindBy(xpath = "//select[@class='form-control facility-dropdown-filter p7_10']")
    public WebElement dropdown_facilityFilter;

    @FindBy(xpath = "//select[@class='form-control store-type-dropdown-filter p7_10']")
    public WebElement dropdown_storeTypeFilter;

    @FindBy(xpath = "//ul[@class='select2-selection__rendered']//li/input")
    public WebElement input_selectStoreInLinkExistingStore;

    @FindBy(xpath = "//ul[@id='select2-store_ids-results']/li")
    public List<WebElement> list_selectStoresListInLinkExistingStore;

    @FindBy(xpath = "//strong[text()='Currently linked stores']/parent::div/following-sibling::div/div")
    public List<WebElement> list_currentlyLinkedStores;

    @FindBy(xpath = "//strong[text()='Currently unlinked stores']/parent::div/following-sibling::div/div")
    public List<WebElement> list_currentlyUnlinkedStores;

    @FindBy(xpath = "//input[@onclick='link_unlink_store()']")
    public WebElement button_saveLinkStore;

//    @FindBy(xpath = "//a[@class='btn btn-primary add-normal-store']/i")
//    public WebElement button_addStore;


    //Disable Store

    @FindBy(xpath = "//div[@id='confirmation-modal']//div[@class='modal-content']")
    public WebElement alert_disableConfirmAlert;

    @FindBy(xpath = "//h4[text()='Are You Sure?']")
    public WebElement text_disableConfirmAlertTitle;

    @FindBy(xpath = "//h4[text()='OpticalStore']")
    public WebElement text_opticalStoreTitle;

    @FindBy(xpath = "//h4[text()='Warning']")
    public WebElement text_warningToaster;

    @FindBy(xpath = "//h4[text()='Pharmacy automation']")
    public WebElement text_pharmacyStoreTitle;


    @FindBy(xpath = "//div[@id='confirmation-modal']//div[@class='modal-body']/p")
    public WebElement text_disableConfirmationAlertMessage;

    @FindBy(xpath = "//div[@id='confirmation-modal']//a[text()='Confirm']")
    public WebElement button_disableAlertConfirmButton;

    @FindBy(xpath = "//a[text()=' New']")
    public WebElement button_addNewButtonInOrder;

    @FindBy(xpath = "//span[contains(@aria-labelledby,'inventory_order_requisition_receive_store')]")
    public WebElement dropdown_receivingStoreInRequisition;

    @FindBy(xpath = "//ul[@id='select2-inventory_order_requisition_receive_store_id-results']/li[@class='select2-results__option']")
    public List<WebElement> list_storesListInReceivingStoresRequisition;

    @FindBy(xpath = "//div[@class='modal-header order-header']//span")
    public WebElement button_closeNewTransactionWithoutSaving;

    @FindBy(xpath = "//input[@onclick='link_unlink_category()']")
    public WebElement button_saveLinkCategory;

//    @FindBy(xpath = "//ul[@id='select2-category_ids-results']/li")
//    public List<WebElement> list_categoriesInDropdown;

    @FindBy(xpath = "//table[@id='manage_inventory_stores_table']//b[contains(@class,'store_name')]")
    public List<WebElement> list_text_storeName;
    @FindBy(xpath = "//tr[contains(@class,'store-actions')]//a[contains(text(),'Edit')]")
    public List<WebElement> list_btn_editStore;
    @FindBy(xpath = "//div[contains(@class,'billing-addresses-details')]//a[contains(text(),'Edit')]")
    public List<WebElement> list_btn_editStoreBillingAddress;
    @FindBy(xpath = "//input[contains(@class,'billing_gst')][@type='text']")
    public WebElement input_gstNo;

    @FindBy(xpath = "//ul/li/a[contains(@href,'type=link_category')]")
    public List<WebElement> list_btn_linkCategory;
    @FindBy(xpath = "//input[contains(@id,'inventory_store_billing_address')][@placeholder='Address']")
    public WebElement input_billingAddress;
    @FindBy(xpath = "//input[contains(@id,'inventory_store_shipping_address')][@placeholder='Address']")
    public WebElement input_shippingAddress;

    @FindBy(xpath = "//div[contains(@class,'shipping-addresses-details')]//a[contains(text(),'Edit')]")
    public List<WebElement> list_btn_editStoreShippingAddress;

    @FindBy(xpath = "//select[contains(@name,'entity_group_id')]")
    public WebElement select_entityGroup;
    @FindBy(xpath = "//input[@id='inventory_store_is_manual_receive_true']")
    public WebElement input_yesManualReceiveOnSaleReturnButton;
    @FindBy(xpath = "//input[@id='inventory_store_is_manual_receive_false']")
    public WebElement input_noManualReceiveOnSaleReturnButton;

    @FindBy(xpath = "//input[@class='select2-search__field']")
    public WebElement input_search;
    @FindBy(xpath = "//select[@id='store_ids']")
    public WebElement select_storeLink;
    @FindBy(xpath = "//ul/li/a[contains(@href,'type=link_store')]")
    public List<WebElement> list_btn_linkRequisitionStores;
    @FindBy(xpath = "//table[@id='manage_inventory_stores_table']//tr[@class='each-contact-table-row']//td[3]/table/tbody/tr/td[3]")
    public List<WebElement> list_unlinkButton;
    @FindBy(xpath = "//ul/li/a[contains(@href,'type=unlink_store')]")
    public List<WebElement> list_btn_unlinkRequisitionStores;
    @FindBy(xpath = "//input[@id='inventory_store_enable_tax_invoice_true']")
    public WebElement radio_enableTaxInvoiceDeliveryChallan;
    @FindBy(xpath = "//input[@id='inventory_store_enable_tax_invoice_false']")
    public WebElement radio_disableTaxInvoiceDeliveryChallan;
    @FindBy(xpath = "//input[@id='inventory_store_enable_transfer_true']")
    public WebElement radio_enableTransferOrIssue;

}
