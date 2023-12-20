package pages.store.PharmacyStore.Order;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_RequisitionReceived extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_RequisitionReceived(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//a[text()='Today'][contains(@class,'btn btn-primary')]")
    public WebElement button_today;

    @FindBy(xpath = "/html/body/div[2]/div/div[2]/div[1]/div[1]/nav/div/div[3]/div/button")
    public WebElement button_downArrowForToday;

    @FindBy(xpath = "//ul/li[@class='period-list']")
    public List<WebElement> list_filterPeriodType;

    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//td[1]")
    public List<WebElement> list_dateTimeOfRequisitionReceived;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//td[2]")
    public List<WebElement> list_statusOfRequisitionReceived;

    @FindBy(xpath = "//div[@class='inventory-show-panel']//div/a[text()='New Transaction']")
    public WebElement button_newTransactionRequisition;
    @FindBy(xpath = "//div[@class='inventory-show-panel']//div/a[contains(@class,'closeReq')]")
    public WebElement button_closeRequisitionReceived;
    @FindBy(xpath = "//table[contains(@class,'table-bordered table-hover')]/tbody//td[1]")
    public WebElement list_transferItem;

    @FindBy(xpath = "//input[@name='lots[items_attributes][0][stock]']")
    public WebElement input_issueQuantity;

    @FindBy(xpath = "//input[@value='Confirm']")
    public WebElement button_confirmTransfer;

    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveTransfer;

    @FindBy(xpath = "//div[@id='freeInvoiceEdit']//b[contains(text(),'Created AT')]")
    public WebElement text_dateTimeCreatedAt;

    @FindBy(xpath = "//div[@class='panel-heading']/h4/a")
    public List<WebElement> list_transferTransactions;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//td[4]")
    public List<WebElement> list_requisitionNumberOfRequisitionReceived;
    @FindBy(xpath = "//h4[text()='Issue Items']")
    public WebElement text_headerIssueItems;
    @FindBy(xpath = "//tr[contains(@id,'requisition-item')]")
    public List<WebElement> list_requisitionItems;
    @FindBy(xpath = "//input[contains(@class,'lot-stock')]")
    public List<WebElement> list_inputLotStock;
    @FindBy(xpath = "//table[@class='table']//tbody/tr/td[6]")
    public List<WebElement> list_textGrnCreatedAtLots;
    @FindBy(xpath = "//table[@class='table']//tbody/tr/td[5]")
    public List<WebElement> list_textGrnNoLots;
    @FindBy(xpath = "//table[@class='table']//tbody/tr/td[7]")
    public List<WebElement> list_textMrpLots;
    @FindBy(xpath = "//table[@class='table']//tbody/tr/td[8]")
    public List<WebElement> list_textRateLots;
    @FindBy(xpath = "//table[@class='table']//tbody/tr/td[9]")
    public List<WebElement> list_textAvailableQuantityLots;
    @FindBy(xpath = "//table[@class='table']//tbody/tr/td[2]")
    public List<WebElement> list_textDescriptionLots;
    @FindBy(xpath = "//table[contains(@class,'table-bordered table-hover')]/tbody//td[1]")
    public List<WebElement> list_transferItemList;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//td[4]")
    public List<WebElement> list_requisitionNumberOnRequisition;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table//tr//td[2]/strong")
    public List<WebElement> list_itemDescriptionInEditIssue;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table//tr//td[8]")
    public List<WebElement> list_itemMrpInEditIssue;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table//tr//td[9]")
    public List<WebElement> list_itemRateInEditIssue;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table//tr//td[11]/input[1]")
    public List<WebElement> list_itemAmountInEditIssue;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table//tr//td[10]/input[1]")
    public List<WebElement> list_itemQtyInEditIssue;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table//tr//td[3]/button")
    public List<WebElement> list_itemDeleteInEditIssue;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table//tr//td[2]//i[@class='fa fa-edit']")
    public List<WebElement> list_itemEditInEditIssue;
    @FindBy(xpath = "//h4[@class='panel-title']//a")
    public WebElement text_rhsIssueNo;
    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tr/td[2]")
    public List<WebElement> list_textDescriptionRhs;
    @FindBy(xpath = "//*[text()='Receiving Store']/following-sibling::input[1]")
    public WebElement input_receivingStore;
    @FindBy(xpath = "//input[@id='inventory_transaction_transfer_total_cost']")
    public WebElement input_totalAmtIncludingTax;
    @FindBy(xpath = "//input[contains(@id,'transfer_transaction_date')]")
    public WebElement input_transactionDate;
    @FindBy(xpath = "//table[contains(@class,'transaction-lots-table')]/thead//td")
    public List<WebElement> list_headerIssueItems;
    @FindBy(xpath = "//table[contains(@class,'transfer-transaction-log-item')]//tbody/tr[@class='treatmentmedications']")
    public List<WebElement> list_rowIssueItems;
    @FindBy(xpath = "//table[contains(@class,'transfer-transaction-log-item')]//strong[contains(text(),'Total')]")
    public List<WebElement> list_textTotalIssueItems;
    @FindBy(xpath = "//i[@class='fa fa-edit']")
    public List<WebElement> list_editLotIssueItems;
    @FindBy(xpath = "//input[@id='issue_quantity']")
    public WebElement input_issueQuantityLot;
    @FindBy(xpath = "//input[@id='remaining_quantity']")
    public WebElement input_remainingQuantityLot;
    @FindBy(xpath = "//div[@class='modal-header inventory-lot-header']//span[@aria-hidden='true'][normalize-space()='×']")
    public WebElement button_closeWithoutSavingLot;
    @FindBy(xpath = "//input[@id='balance_quantity']")
    public WebElement input_balanceQuantityLot;
    @FindBy(xpath = "//table[@class='table']//tbody/tr/td[3]")
    public List<WebElement> list_textBatchNoLots;
    @FindBy(xpath = "//table[@class='table']//tbody/tr/td[4]")
    public List<WebElement> list_textExpiryLots;
    @FindBy(xpath = "//tr[contains(@id,'requisition-item')]/ancestor::table//th")
    public List<WebElement> list_requisitionItemsHeader;
    @FindBy(xpath = "//div[@class='panel-heading']//a")
    public List<WebElement> list_issueHistory;

}
