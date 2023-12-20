package pages.Sprint71;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_MopForCanceBillAndOrder6062 extends TestBase {
    private WebDriver driver;

    public Page_MopForCanceBillAndOrder6062(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//tr[@class='inventory-list-row']/td/div[2]/span[1]")
    public List<WebElement> list_expiredItems;


    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveChanges;

    @FindBy(xpath = "//input[@value='Save']")
    public WebElement button_savePatient;

    @FindBy(xpath = "//tbody[@class='inventory-table-body']/tr/td[5]")
    public List<WebElement> list_salesTransactionInInventory;

    @FindBy(xpath = "//a[contains(text(),' Cancel Bill')]")
    public WebElement button_cancelBill;

    @FindBy(xpath = "//span[text()='All the items in the bill have been returned/taken back.']")
    public WebElement text_allItemsInTheBillTakenBackCheckbox;

    @FindBy(xpath = "//select[@id='mode_of_payment']")
    public WebElement select_modeOfPaymentToCancelBill;

    @FindBy(xpath = "//input[@id='payment_received']")
    public WebElement input_amountToBeRefund;

    @FindBy(xpath = "//tr/td/b[contains(text(),'Net Amount')]/parent::td/following-sibling::td/b")
    public WebElement text_netAmount;

    @FindBy(xpath = "//tr/td/b[contains(text(),'Received from Advance')]/parent::td/following-sibling::td/b")
    public WebElement text_amountReceivedFromAdvance;

    @FindBy(xpath = "//input[@id='inventory_cancel_reason']")
    public WebElement input_cancelReason;

    @FindBy(xpath = "//label[@class='error']")
    public WebElement validation_allItemsTakenBack;

    @FindBy(xpath = "//select[contains(@class, 'error')]")
    public WebElement validation_selectModeOfPayment;

    @FindBy(xpath = "//strong[@class='error']")
    public WebElement validation_reasonField;

    @FindBy(xpath = "//button[contains(text(),'Cancel Bill')]")
    public WebElement button_cancelBillInBillDetailsModal;

    @FindBy(xpath = "//button[@id='cancel_invoice']")
    public WebElement button_cancelOderInOrderDetailsModal;

    @FindBy(xpath = "//button[text()='Add New Patient']")
    public WebElement button_addNewPatientButton;

    @FindBy(xpath = "//a[contains(text(),'More Action')]")
    public WebElement button_moreAction;

    @FindBy(xpath = "//input[@id='other_transaction_id']")
    public WebElement input_transactionId;

    @FindBy(xpath = "//input[@id='other_note']")
    public WebElement input_transactionNote;

    @FindBy(xpath = "//label[@for='delivery_completed']")
    public WebElement radioButton_completedDeliveryUnderOpticalSalesOrderForm;

    @FindBy(xpath = "//input[@name='invoice_inventory_order[pending_advance_payments_attributes][reason]']")
    public WebElement input_reasonForAdvance;

    @FindBy(xpath = "//a[contains(text(),' Cancel Order')]")
    public WebElement button_cancelOrder;

    @FindBy(xpath = "//input[@id='pending_invoice_total_payment_remaining']")
    public WebElement input_balanceOrPending;

    @FindBy(xpath = "//span[text()='All the items in the order have been returned/taken back.']")
    public WebElement text_allItemsInTheOrderTakenBackCheckbox;

    @FindBy(xpath = "//div[@class='inventory-show-panel']//div[contains(text(),'Current Status')]/following-sibling::div/b[contains(text(),'Canceled')]")
    public WebElement text_currentStatusOfOrderAndBill;

    @FindBy(xpath = "//input[@name='invoice_inventory_order[pending_advance_payments_attributes][mop_breakups_attributes][0][amount]']")
   public WebElement input_advanceAmount;

    @FindBy(xpath = "//button[contains(text(),'Cancel Order')]")
   public WebElement button_cancelOrderInCancel;





}

