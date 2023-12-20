package pages.store.PharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Receive extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_Receive(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }


    @FindBy(xpath = "//div[@class='btn-group']/a[text()='Today']")
    public WebElement button_todayButtonInTransaction;

    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//tr/td[4]")
    public List<WebElement> list_transferredOnValueInRowOnTransactionReceive;

    @FindBy(xpath = "//div[@class='inventory-show-panel']//a[text()='Receive']")
    public WebElement button_receiveButtonInTransaction;

    @FindBy(xpath = "//h4[text()='Receive Transaction']")
    public WebElement header_receiveTransactionOnModal;

    @FindBy(xpath = "//div[@class='modal-body transaction-body']//input[@value='Save Changes']")
    public WebElement button_receiveTransactionOnModal;

    @FindBy(xpath = "//b[contains(text(),'Receive Details')]")
    public WebElement text_receiveTransactionInTableOnTransactionReceive;

    @FindBy(xpath = "//div[@class='inventory-show-panel']//tbody//td[5]")
    public WebElement text_itemQuantity;


    @FindBy(xpath = "//form[@id='inventory_receive_form']/div[1]/table/tbody/tr/td[2]")
    public WebElement text_itemDescriptionUnderReceiveTransactionPopUp;
    @FindBy(xpath = "//form[@id='inventory_receive_form']/div[1]/table/tbody/tr/td[3]")
    public WebElement text_itemBatchNoUnderReceiveTransactionPopUp;
    @FindBy(xpath = "//form[@id='inventory_receive_form']/div[1]/table/tbody/tr/td[4]")
    public WebElement text_totalCostUnderReceiveTransactionPopUp;

    @FindBy(xpath = "//form[@id='inventory_receive_form']/div[1]/table/tbody/tr/td[5]")
    public WebElement text_transferQtyUnderReceiveTransactionPopUp;

    @FindBy(xpath = "//input[contains(@class,'item_quantity')]")
    public WebElement text_receiveQtyUnderReceiveTransactionPopUp;

    @FindBy(xpath = "//form[@id='inventory_receive_form']/div[1]/table/tbody/tr/td[8]")
    public WebElement text_rejectedQtyUnderReceiveTransactionPopUp;

    @FindBy(xpath = "//form[@id='inventory_receive_form']/div[1]/table/tbody/tr/td[9]/input")
    public WebElement text_receiveTotalCostUnderReceiveTransactionPopUp;

    @FindBy(xpath = "//input[@id='inventory_transaction_receive_total_cost']")
    public WebElement text_totalAmountIncludingTaxUnderReceiveTransactionPopUp;

    @FindBy(xpath = "//div[@class='inventory-show-panel']//a[text()='Receive']")
    public WebElement button_receiveStock;

    @FindBy(xpath = "//select[@id='sub_store_id']")
    public WebElement select_subStore;

    @FindBy(xpath = "//input[@id='inventory_search']")
    public WebElement input_searchItem;

    @FindBy(xpath = "//tbody[@class='inventory-table-body']//tr/td[1]")
    public List<WebElement> list_variantCode;

    @FindBy(xpath = "//div[@id='item_information']/div[1]/div[1]/div[2]/strong")
    public WebElement text_itemDescriptionUnderVariantFolder;

    @FindBy(xpath = "//div[@id='item_information']/div/div[3]/div[2]")
    public WebElement text_stockCountUnderVariantFolder;

    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//tr/td[4]/div[2]")
    public List<WebElement> list_text_transactionIdRow;

    @FindBy(xpath = "//input[@name='commit']")
    public WebElement button_saveChanges;
    @FindBy(xpath = "//div[contains(text(),'Status')]")
    public WebElement text_rhsStatus;
    @FindBy(xpath = "//select[@id='sub_store_id']")
    public List<WebElement> list_selectSubStore;
    @FindBy(xpath = "//div[@class='inventory-show-panel']//h3")
    public WebElement text_transferNote;
    @FindBy(xpath = "//b[contains(text(),'Receive ID')]/parent::div/following-sibling::div/span[1]")
    public WebElement text_receiveTransactionId;
    @FindBy(xpath = "//h4[@class='panel-title']//span[1]")
    public WebElement text_transferTransactionId;
    @FindBy(xpath = "//b[contains(text(),'Issue')]/parent::div/following-sibling::div//a")
    public WebElement text_issueTransactionId;
    @FindBy(xpath = "//div[contains(text(),'Status: Pending')]")
    public WebElement text_receiveStatusInReceive;
    @FindBy(xpath = "//b[contains(text(),'Receive Status')]/parent::div/following-sibling::div")
    public WebElement text_receiveFulfilmentStatus;
    @FindBy(xpath = "//div[contains(text(),'Receive Note: NA')]")
    public WebElement text_receiveNote;
    @FindBy(xpath = "//div[contains(text(),'Transfer Store Name:')]")
    public WebElement text_transferStoreName;
    @FindBy(xpath = " //b[contains(text(),'Delivery Challan No')]//parent::div/following-sibling::div/span")
    public WebElement text_deliveryChallanNumber;
    @FindBy(xpath = "//b[contains(text(),'Transfer Details')]")
    public WebElement text_transferDetails;
    @FindBy(xpath = "//label[contains(text(),'approved')]")
    public WebElement text_transferStatus;
    @FindBy(xpath = "//b[contains(text(),'Receive Status')]/parent::div/following-sibling::div")
    public WebElement text_receivedStatusInReceive;
    @FindBy(xpath = "//label[contains(text(),'closed')]")
    public WebElement text_transferClosedStatus;
    @FindBy(xpath = "//b[contains(text(),'Receive Details')]")
    public WebElement text_receivedDetails;
    @FindBy(xpath = "//label[contains(text(),'Received')]")
    public WebElement text_receiveDetailsStatus;

    @FindBy(xpath = "//b[contains(text(),'Received By')]/parent::div/following-sibling::div")
    public WebElement text_receivedBy;
    @FindBy(xpath = "//h4[@class='panel-title']")
    public WebElement header_transferViewDetailsDropdown;
    @FindBy(xpath = "//b[text()='Created By']/parent::div/following-sibling::div")
    public WebElement text_issueCreatedBy;
    @FindBy(xpath = "//b[text()='Approved By']/parent::div/following-sibling::div")
    public WebElement text_issueApprovedBy;
    @FindBy(xpath = "//b[contains(text(),'Issue Store')]/parent::div/following-sibling::div")
    public WebElement text_issueStore;
    @FindBy(xpath = "//b[contains(text(),'Issue Status')]/parent::div/following-sibling::div")
    public WebElement text_issueStatus;

    @FindBy(xpath = "//b[contains(text(),'Receive Status')]")
    public WebElement text_receiveStatusText;
    @FindBy(xpath = "//font[contains(text(),'Completed')]")
    public WebElement text_receiveStatus;
    @FindBy(xpath = "//b[contains(text(),'Receive By')]/parent::div/following-sibling::div")
    public WebElement text_receiveByText;
    @FindBy(xpath = "//font[contains(text(),'Receive By')]/parent::font/parent::b/parent::div/following-sibling::div")
    public WebElement text_receiveByValue;
    @FindBy(xpath = "//b[contains(text(),'Req. Status')]/parent::div/following-sibling::div")
    public WebElement text_requisitionStatus;




}
