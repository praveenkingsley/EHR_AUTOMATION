package pages.settings.organisationSettings.inventoryAndSupplyChain;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_ROLRules extends TestBase{
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_ROLRules(WebDriver driver) {
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

    // --------------------------------------------------------------------------------------

    @FindBy(xpath = "//a[contains(text(),' Add ROL Rule')]")
    public WebElement button_AddROLRule;

    @FindBy(xpath = "//h4[text()='Add Rol Rules']")
    public WebElement header_AddRolRules;

    @FindBy(xpath = "//select[@name='store_id']")
    public WebElement select_RolStore;

    @FindBy(xpath = "//input[@id='search-item-variants']")
    public WebElement input_searchItemName;

    @FindBy(xpath = "//div[@class='main-rol-rule-list']/div//b[@class='sr_no']")
    public List<WebElement> text_ItemSrNo;

    @FindBy(xpath = "//div[@class='main-rol-rule-list']/div//b[@class='variant_name']")
    public List<WebElement> text_ItemVariantName;

    @FindBy(xpath = "//div[@class='main-rol-rule-list']/div//b[@class='category_name']")
    public List<WebElement> text_ItemCategoryName;

    @FindBy(xpath = "//div[@class='item-variants-rates']//div[contains(@class,'row inventory-rol-rule-details')]/div[1]/input")
    public List<WebElement> input_RolItemValue;

    @FindBy(xpath = "//div[@class='item-variants-rates']//div[contains(@class,'row inventory-rol-rule-details')]/div[2]/input")
    public List<WebElement> input_RolItemMaxValue;

    @FindBy(xpath = "//div[@class='item-variants-rates']//div[contains(@class,'row inventory-rol-rule-details')]/div[3]/input")
    public List<WebElement> input_RolItemMinValue;

    @FindBy(xpath = "//input[@value='Save']")
    public WebElement button_SaveRol;

    @FindBy(xpath = "//table[@id='manage_inventory_items_table']//tr/td[1]")
    public List<WebElement> list_itemNameTable;

    @FindBy(xpath = "//table[@id='manage_inventory_items_table']//tr/td[3]")
    public List<WebElement> list_rolValueTable;

    @FindBy(xpath = "//table[@id='manage_inventory_items_table']//tr/td[6]")
    public List<WebElement> list_maxValueTable;

    @FindBy(xpath = "//table[@id='manage_inventory_items_table']//tr/td[7]")
    public List<WebElement> list_minValueTable;

    //elements for store
    @FindBy(xpath = "//input[@placeholder='Qty']")
    public WebElement input_quantityRequisition;

    @FindBy(xpath = "//span[contains(@aria-labelledby,'_requisition_type')]")
    public WebElement dropdown_requisitionType;

    @FindBy(xpath = "//ul[contains(@id,'_requisition_type')]/li")
    public List<WebElement> list_requisitionType;





}
