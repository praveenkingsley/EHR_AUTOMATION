package pages.settings.organisationSettings.inventoryAndSupplyChain;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_CategoryMaster extends TestBase{
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_CategoryMaster(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }
    @FindBy(xpath = "//a[text()=' Add Category']")
    public WebElement button_addCategory;

    @FindBy(xpath = "//h4[text()='Add Category']")
    public WebElement header_titleAddCategory;

    @FindBy(xpath = "//strong[text()='Category Information']")
    public WebElement text_labelCategoryInformation;

    @FindBy(xpath = "//input[@id='inventory_category_name']")
    public WebElement input_categoryName;

    @FindBy(xpath = "//input[@id='inventory_category_description']")
    public  WebElement input_categoryDescription;

    @FindBy(xpath = "//input[@id='inventory_category_prefix']")
    public  WebElement input_categoryPrefix;

    @FindBy(xpath = "//input[@id='inventory_category_multiple_variants_true']")
    public  WebElement radio_yesMultipleVariantsButton;

    @FindBy(xpath = "//input[@id='inventory_category_stockable_true']")
    public  WebElement radio_yesStockableButton;

    @FindBy(xpath = "//input[@id='inventory_category_multiple_variants_false']")
    public  WebElement radio_noMultipleVariantsButton;

    @FindBy(xpath = "//input[@id='inventory_category_stockable_false']")
    public  WebElement radio_noStockableButton;

    @FindBy(xpath = "//input[@value='Save Changes']")
    public  WebElement button_saveChanges;

    @FindBy(xpath = "//input[@placeholder='Search By Category Name']")
    public  WebElement input_searchByCategoryName;

    @FindBy(xpath = "//tbody[@id='category_master-list']/tr")
    public List<WebElement> list_categoryRowForCategoryMaster;

    @FindBy(xpath = "//tbody[@id='category_master-list']/tr/td[1]")
    public List<WebElement> list_categoryNameForCategoryMaster;

    @FindBy(xpath = "//tbody[@id='category_master-list']/tr/td[3]")
    public List<WebElement> list_categoryActionsForCategoryName;

    @FindBy(xpath = "//select[@id='inventory_category_type']")
    public  WebElement select_categoryType;

    @FindBy(xpath = "//span[@id='select2-inventory_category_type-container']")
    public  WebElement text_categoryTypeBox;

    @FindBy(xpath = "//h4[text()='Link Existing Dispensing Unit']")
    public WebElement header_titleLinkExistingDispensingUnit;

    @FindBy(xpath = "//h4[text()='UnLink Existing Dispensing Unit']")
    public WebElement header_titleUnlinkExistingDispensingUnit;

    @FindBy(xpath = "//select[@id='dispensing_unit_ids']")
    public  WebElement select_selectDispensingUnits;

    @FindBy(xpath = "//ul[@class='select2-selection__rendered']//li/input")
    public WebElement input_selectStoreInLinkExistingStore;

    @FindBy(xpath = "//select[@id='dispensing_unit_ids']/option")
    public  List<WebElement> list_selectDispensingUnitsList;

    @FindBy(xpath = "//strong[text()='Currently linked Dispensing Unit']/parent::div/following-sibling::div//div")
    public  List<WebElement> list_currentlyLinkedDispensingUnitsList;

    @FindBy(xpath = "//strong[text()='Currently unlinked Dispensing Unit']/parent::div/following-sibling::div//div")
    public  List<WebElement> list_currentlyUnlinkedDispensingUnitsList;

    @FindBy(xpath = "//input[@onclick='link_unlink_dispensing_unit()']")
    public  WebElement button_saveLinkDispensingUnits;

    @FindBy(xpath = "//*[contains(text(),'Inventory') and contains(text(),'Supply')]")
    public WebElement button_inventoryAndSupply;
    @FindBy(xpath = "//a[text()='Edit']")
    public  List<WebElement> list_editCategory;
    @FindBy(xpath = "//input[@id='inventory_category_type']")
    public WebElement input_categoryType;
}
