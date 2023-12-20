package pages.store.PharmacyStore.Items;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Lot extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_Lot(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//div[text()='Available Stock:']/..//div[4]/strong")
    public WebElement value_AvailableStock;

    @FindBy(xpath = "//tbody[@id='inventory_table_body']/tr/td")
    public List<WebElement> list_itemsOnItemLot;

    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//tr/td/div[1]/span/b")
    public List<WebElement> list_LotDetailsOnVariants;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tr[1]//td[5]")
    public WebElement text_BeforeStock;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tr[1]//td[6]")
    public WebElement text_AfterStock;

    @FindBy(xpath = "//input[@id='inventory_search']")
    public WebElement input_InventorySearch;

    @FindBy(xpath = "//b[contains(text(),'Transactions Details:')]")
    public WebElement text_transactionDetailsSectionText;

    @FindBy(xpath = "//a[@id='edit_inventory_lot']")
    public WebElement button_editLot;

    @FindBy(xpath = "//span[text()='List Price:']/parent::div/following-sibling::div[1]/strong")
    public WebElement text_mrpPrice;

    @FindBy(xpath = "//input[@id='inventory_lot_list_price']")
    public WebElement input_newMRP;

    @FindBy(xpath = "//input[@value='Update Lot']")
    public WebElement button_updateLot;

    @FindBy(xpath = "//a[text()=' Block Lot']")
    public WebElement button_blockLot;

    @FindBy(xpath = "//input[@placeholder='Block Lot Comment']")
    public WebElement input_blockLotComment;

    @FindBy(xpath = "//input[@value='Block Lot']")
    public WebElement button_blockLotConfirmation;

    @FindBy(xpath = "//a[text()=' Unblock Lot']")
    public WebElement button_unblockLot;

    @FindBy(xpath = "//input[@placeholder='Unblock Lot Comment']")
    public WebElement input_unblockLotComment;

    @FindBy(xpath = "//input[@value='Unblock Lot']")
    public WebElement button_unblockLotConfirmation;

    @FindBy(xpath = "(//div[text()='Blocked Stock:']/following-sibling::div[1]/strong[1])[1]")
    public WebElement text_blockedLotCount;
    @FindBy(xpath = "//span[text()='Stock:']/parent::div/following-sibling::div[1]/strong")
    public WebElement text_stockCount;
    @FindBy(xpath = "//button[@class='btn btn-primary btn-sm dropdown-toggle ']")
    public WebElement button_filterByButton;
    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//thead//th")
    public List<WebElement> list_transactionDetailsTableHeaderList;
    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tbody//td")
    public List<WebElement> list_transactionDetailsTableValueList;
    @FindBy(xpath = "//h4[text()='Lot Details']")
    public WebElement header_lotDetailsHeaderInEditLot;
    @FindBy(xpath = "//div[@class='modal-body window-body']/div/div")
    public List<WebElement> list_lotDetailsTableKeysAndValuesInEditLot;
    @FindBy(xpath = "//strong[contains(text(),'600')]")
    public WebElement strong_updatedSellingPrice;
    @FindBy(xpath = "//h5[text()='Block Lot Confirmation']")
    public WebElement header_blockLotHeader;
    @FindBy(xpath = "//label[text()='You are about to block the lot. are you sure?']")
    public WebElement label_areYouSureMessage;
    @FindBy(xpath = "//label[text()='Please provide a reason for closure below']")
    public WebElement label_closerReasonMessage;
    @FindBy(xpath = "//button[@class='btn commit btn-danger dontBlock']")
    public WebElement button_dontBlockButton;
    @FindBy(xpath = "//b[text()='Blocked Lot by User Details']")
    public WebElement text_blockedLotUSerDetails;

    @FindBy(xpath = "//b[text()='Blocked Lot by User Details']/parent::div/parent::div/following-sibling::div/div")
    public List<WebElement> list_blockedLotUserDetailsTableKeysAndValuesList;

    @FindBy(xpath = "//h5[text()='Unblock Lot Confirmation']")
    public WebElement header_unblockLotHeader;
    @FindBy(xpath = "//label[text()='You are about to unblock the lot. are you sure?']")
    public WebElement label_areYouSureMessageUnblock;
    @FindBy(xpath = "(//label[text()='Please provide a reason for closure below'])[2]")
    public WebElement label_closerReasonMessageUnblock;
    @FindBy(xpath = "(//button[@class='btn commit btn-danger dontBlock'])[2]")
    public WebElement button_dontUnblockButton;

    @FindBy(xpath = "//span[text()='Cost Price:']/parent::div/following-sibling::div[1]/strong")
    public WebElement text_costPrice;

    @FindBy(xpath = "//button[contains(@class,'btn btn-primary inventory-search-button')]")
    public WebElement button_search;

    @FindBy(xpath = "//div[@id='item_information']/div/div[3]/div[1]/span/parent::div/following-sibling::div[1]")
    public WebElement text_stock;

    @FindBy(xpath = "//div[@id='item_information']/div/div[4]/div[1]/following-sibling::div[1]")
    public WebElement text_blockedLot;




}
