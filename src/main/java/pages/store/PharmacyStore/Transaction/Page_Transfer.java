package pages.store.PharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Transfer extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_Transfer(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//a[text()=' New']")
    public WebElement button_newTransaction;

    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//td[3]")
    public List<WebElement> list_dateTimeOfRequisitionReceived;

    @FindBy(xpath = "//a[text()='Approve']")
    public WebElement button_approveTransfer;

    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//td[1]")
    public List<WebElement> list_dateTimeOfTransfer;
    @FindBy(xpath = "//b[text()=' Issue ID']/parent::div/following-sibling::div/span[1]")
    public WebElement text_issueNo;


    @FindBy(xpath = "//input[@id='inventory_search']")
    public WebElement input_searchItem;

    @FindBy(xpath = "//tbody[@class='inventory-table-body']//tr/td/div[@class='row'][1]/span[1]")
    public List<WebElement> list_itemsUnderLot;

    @FindBy(xpath = "//tbody[@class='inventory-table-body']//tr/td/div[@class='row'][1]/span[2]")
    public List<WebElement> list_variantCodeUnderLot;

    @FindBy(xpath = "//tbody[@class='inventory-table-body']//tr/td[1]")
    public List<WebElement> list_variantCode;

    @FindBy(xpath = "//div[@id='item_information']/div/div[1]/div[4]")
    public WebElement text_itemDescriptionUnderVariantFolder;

    @FindBy(xpath = "//div[@id='item_information']/div[1]/div[2]/div[2]/strong")
    public WebElement text_itemVarientCode;

    @FindBy(xpath = "//div[@id='item_information']/div[1]/div[3]/div[2]/strong")
    public WebElement text_stock;

    @FindBy(xpath = "//div[@id='item_information']/div[1]/div[4]/div[2]/strong")
    public WebElement text_blockedStock;

    @FindBy(xpath = "//div[@id='item_information']/div[1]/div[4]/div[4]/strong")
    public WebElement text_costPriceOfItem;


    @FindBy(xpath = "//div[@id='item_information']/div[1]/div[3]/div[4]/strong")
    public WebElement text_availableStock;

    @FindBy(xpath = "//input[@id='inventory_transaction_transfer_note']")
    public WebElement input_transactionNote;

    @FindBy(xpath = "//span[@class='select2-selection select2-selection--single']")
    public WebElement dropdown_receivingStore;

    @FindBy(xpath = "//ul[@class='select2-results__options']//li")
    public List<WebElement> list_receivingStore;

    @FindBy(xpath = "//input[@id='inventory_transaction_transfer_transaction_date']")
    public WebElement input_transactionDate;

    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveChanges;

    @FindBy(xpath = "//a[text()='Approve']")
    public WebElement button_approveTransferTransaction;
    @FindBy(xpath = "//tbody[@class='items-lots-body']//tr//td//div/span[2]/label")
    public List<WebElement> list_itemsCanBeTransferred;
    @FindBy(xpath = "//tbody[@class='items-lots-body']//tr//td//div[1]/span[1]/b")
    public List<WebElement> list_itemDescriptionRow;
    @FindBy(xpath = "//input[@id='lots_search']")
    public WebElement input_searchItemToBeTransferred;

    @FindBy(xpath = "//button[text()='Description']")
    public WebElement button_searchByDescription;

    @FindBy(xpath = "//form[@id='inventory_transfer_transactions_form']/div[5]/table/tbody/tr/td[1]")
    public List<WebElement> list_selectedItemsToTransfer;

    @FindBy(xpath = "//form[@id='inventory_transfer_transactions_form']/div[5]/table/tbody/tr/td[10]//input[@placeholder='Qty']")
    public List<WebElement> list_quantityFieldForItemsToTransfer;

    @FindBy(xpath = "//form[@id='inventory_transfer_transactions_form']/div[5]/table/tbody/tr/td[12]")
    public List<WebElement> list_deleteItemButtons;
    @FindBy(xpath = "//thead[@class='inventory-table-head']/tr/td")
    public List<WebElement> list_tableHeaderTransfer;
    @FindBy(xpath = "//tbody[@class='inventory-table-body']//tr//td[3]")
    public List<WebElement> list_transactionIdToGetTransferTransaction;
    @FindBy(xpath = "//tbody[@class='inventory-table-body']//tr//td[3]")
    public List<WebElement> list_transferTransactionRow;
    @FindBy(xpath = " //tbody[@class='inventory-table-body']//tr//td[4]")
    public List<WebElement> list_transactionNoteToGetItemsToBeReceived;

    @FindBy(xpath = "//div[@class='inventory-show-panel']/div/h3")
    public WebElement tnote_rhs;

    @FindBy(xpath = "//label[text()='Cannot Add more than stock']")
    public WebElement text_ValidationForReceiveQuantity;

    @FindBy(xpath = "//label[text()='Cannot Checkout more than stock']")
    public WebElement text_ValidationForTransferQuantity;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']/tbody/tr/td[5]")
    public WebElement text_selectedQuantityToTransfer;

    @FindBy(xpath = "//div[contains(text(),'Transaction ID:')]")
    public WebElement text_transactionID;

    @FindBy(xpath = "//div[@id='item_information']/div/div[3]/div[2]")
    public WebElement text_stockCountUnderVariantFolder;

    @FindBy(xpath = "//b[contains(text(),'Transfer No')]/parent::div/following-sibling::div/span[1]")
    public WebElement text_transferTransactionID;

    @FindBy(xpath = "//input[@id='inventory_transaction_transfer_total_cost']")
    public WebElement input_totalAmountIncludingTax;
    @FindBy(xpath = "//a[contains(@class,'btn btn-primary btn-xs')]")
    public WebElement button_edit;
    @FindBy(xpath = "//button[@class='btn btn-xs btn-danger cancel-transfer']")
    public WebElement button_cancel;
    @FindBy(xpath = "//input[@id='inventory_transaction_transfer_transaction_date']")
    public WebElement input_date;
    @FindBy(xpath = "//a[@id='re-stock']")
    public WebElement button_restock;
    @FindBy(xpath = "//td[text()='Stock Issue Against Ch To Fs']/following-sibling::td[3]")
    public WebElement issueIdText;
    @FindBy(xpath = "//td[text()='Receive Transaction']/parent::tr/following-sibling::tr[1]/td[4]")
    public WebElement receiveTransactionIdStoreText;
    @FindBy(xpath = "//b[contains(text(),'Issue Approved By')]/parent::div/following-sibling::div")
    public WebElement text_issueApprovedBy;
    @FindBy(xpath = "//h4[contains(@class,'panel-title')]/a")
    public WebElement header_requisitionViewDetailsDropdown;
    @FindBy(xpath = "//h4[contains(@class,'panel-title')]//span[1]")
    public WebElement text_requisitionNumber;
    @FindBy(xpath = "//h4[contains(@class,'panel-title')]//span[2]")
    public WebElement text_requisitionDateAndTime;
    @FindBy(xpath = "//h4[contains(@class,'panel-title')]//span[3]")
    public WebElement text_transferDateAndTime;
    @FindBy(xpath = "//b[contains(text(),'Created By')]/parent::div/following-sibling::div")
    public WebElement text_requisitionCreatedBy;
    @FindBy(xpath = "//b[contains(text(),'Approved By')]/parent::div/following-sibling::div")
    public WebElement text_requisitionApprovedBy;
    @FindBy(xpath = "//b[contains(text(),'Requisition To')]/parent::div/following-sibling::div")
    public WebElement text_requisitionTo;
    @FindBy(xpath = "//b[contains(text(),'Requisition Store')]/parent::div/following-sibling::div")
    public WebElement text_requisitionStore;
    @FindBy(xpath = "//b[contains(text(),'Requisition Status')]/parent::div/following-sibling::div")
    public WebElement text_requisitionStatus;

    @FindBy(xpath = "//b[contains(text(),'Transfer Store')]/parent::div/following-sibling::div")
    public WebElement text_transferStore;
    @FindBy(xpath = "//b[contains(text(),'Transfer Status')]/parent::div/following-sibling::div")
    public WebElement text_transferStatus;

    @FindBy(xpath = "//b[contains(text(),'Issue ID')]/parent::div/following-sibling::div")
    public WebElement text_issueIdInTransfer;
    @FindBy(xpath = "//b[contains(text(),'Issue Created By')]/parent::div/following-sibling::div")
    public WebElement text_issueCreatedBy;
    @FindBy(xpath = "//b[contains(text(),'Issue Status')]/parent::div/following-sibling::div")
    public WebElement text_issueStatus;
    @FindBy(xpath = "//b[contains(text(),'Cancelled By')]/parent::div/following-sibling::div")
    public WebElement text_issueCancelledBy;
    @FindBy(xpath = "//b[contains(text(),'Cancelled Reason')]/parent::div/following-sibling::div")
    public WebElement text_issueCancelledReason;
    @FindBy(xpath = "//input[@id='cancelled_reason']")
    public WebElement input_cancelReason;
    @FindBy(xpath = "//input[@value='Cancel']")
    public WebElement input_cancelButtonInCancel;
    @FindBy(xpath = "//tbody[@class='inventory-table-body']//tr")
    public List<WebElement> list_tableDataTransfer;
    @FindBy(xpath = "//b[contains(text(),'Receive Status')]/parent::a")
    public WebElement text_receiveStatus;
    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//td[2]")
    public List<WebElement> list_transactionDetailsItemDataList;
    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//td[7]")
    public List<WebElement> list_transactionDetailsTotalPriceDataList;
    @FindBy(xpath = " //table[@class='table table-striped table-bordered']//th")
    public List<WebElement> list_transactionDetailsHeaderList;
    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//td")
    public List<WebElement> list_transactionDetailsDataList;
    @FindBy(xpath = "//h4[text()='Transfer Items']")
    public WebElement header_addTransferHeader;
    @FindBy(xpath = "//b[contains(text(),'Transfer')]/parent::div/following-sibling::div")
    public WebElement text_transferText;
    @FindBy(xpath = "//b[contains(text(),'Transfer Created By')]/parent::div/following-sibling::div")
    public WebElement text_transferCreatedBy;
    @FindBy(xpath = " //thead[@class='transaction-table-head']//td")
    public List<WebElement> list_transactionHeaderList;
    @FindBy(xpath = "//div[@class='transaction-table-body']//tr//td")
    public List<WebElement> list_transactionDataList;
    @FindBy(xpath = "//button[@class='btn btn-link btn-xs btn-inventory-refresh']/i")
    public WebElement button_refreshButton;
    @FindBy(xpath = "//b[contains(text(),'Transaction')]/parent::div/following-sibling::div/span")
    public WebElement text_transactionStatus;
    @FindBy(xpath = "(//table[@class='table table-striped table-bordered'])[2]//th")
    public List<WebElement> list_receiveDetailsHeaderList;
    @FindBy(xpath = "(//table[@class='table table-striped table-bordered'])[2]//td")
    public List<WebElement> list_receiveDetailsDataList;
    @FindBy(xpath = " (//table[@class='table table-striped table-bordered'])[1]//th")
    public List<WebElement> list_transferDetailsHeaderList;
    @FindBy(xpath = "(//table[@class='table table-striped table-bordered'])[1]//td")
    public List<WebElement> list_transferDetailsDataList;
    @FindBy(xpath = "//td[text()='Receive Transaction']/following-sibling::td[3]")
    public WebElement receiveTransactionIdText;
    @FindBy(xpath = "//td[text()='Delivery Challan']/following-sibling::td[3]")
    public WebElement deliveryChallanIdText;
    @FindBy(xpath = "//td[text()='Delivery Challan']/parent::tr/following-sibling::tr[3]/td[4]")
    public WebElement deliveryChallanIdPharmacyText;

    @FindBy(xpath = "//td[text()='Stock Direct Transfer Fs To Fs']/following-sibling::td[3]")
    public WebElement transferIdText;
    @FindBy(xpath = "//b[contains(text(),'Transfer Approved By')]/parent::div/following-sibling::div")
    public WebElement text_transferApprovedBy;
    @FindBy(xpath = "//label[text()='Reason for cancellation']")
    public WebElement label_cancelReason;
    @FindBy(xpath = "//b[contains(text(),'Transfer Cancelled By')]/parent::div/following-sibling::div")
    public WebElement text_transferCancelledBy;
    @FindBy(xpath = "//div[@class='transaction-table-body']//tr//td[11]")
    public List<WebElement> list_transactionDataAmountList;
    @FindBy(xpath = "//div[@class='panel-body']/div[1]/div[2]/span[1]")
    public WebElement text_receiveIdInTransfer;

    @FindBy(xpath = "//button[normalize-space()='Confirm']")
    public WebElement button_confirmRestock;

}
