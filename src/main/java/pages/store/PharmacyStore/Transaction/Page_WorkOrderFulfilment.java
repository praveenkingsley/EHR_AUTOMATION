package pages.store.PharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_WorkOrderFulfilment extends TestBase {
    public Page_WorkOrderFulfilment(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }



    @FindBy(xpath = "//button[@class='btn btn-link btn-xs btn-inventory-refresh']")
    public WebElement button_refresh;
    @FindBy(xpath = "//a[text()='Approve']")
    public WebElement button_approve;
    @FindBy(xpath = "//a[contains(@href,'edit')]")
    public WebElement button_edit;
    @FindBy(xpath = "//button[contains(@class,'btn btn-xs btn-danger')]")
    public WebElement button_cancel;
    @FindBy(xpath = "//h4[text()='Work Order Fulfilment']")
    public WebElement text_headerCreteWorkOrderFulfilment;
    @FindBy(xpath = "//div[@class='purchase-order row']//tbody/tr")
    public List<WebElement> list_lhsTableRow;
    @FindBy(xpath = "//div[@class='purchase-order row']//thead//th")
    public List<WebElement> list_lhsTableHeader;
    @FindBy(xpath = "//div[@class='purchase-order row']//div[@id='item_information']//b")
    public List<WebElement> list_lhsItemInfoHeader;
    @FindBy(xpath = "//div[@class='purchase-order row']//div[@id='item_information']//b/parent::div/following-sibling::div[2]")
    public List<WebElement> list_lhsItemInfoValue;

    @FindBy(xpath = "//div[contains(@class,'purchase-invoice-information')]//b")
    public List<WebElement> list_totalItemInfoHeader;
    @FindBy(xpath = "//div[contains(@class,'purchase-invoice-information')]//b/parent::div/following-sibling::div[2]/input")
    public List<WebElement> list_totalItemInfoValue;
    @FindBy(xpath = "//input[@id='inventory_transaction_work_order_fulfilment_note']")
    public WebElement input_transactionNotes_creteWorkOrderFulfilment;
    @FindBy(xpath = "//input[@id='inventory_transaction_work_order_fulfilment_vendor_location_name'][@type='text']")
    public WebElement input_vendor_creteWorkOrderFulfilment;
    @FindBy(xpath = "//input[@id='inventory_transaction_work_order_fulfilment_work_order_fulfilment_date']")
    public WebElement input_woDate_creteWorkOrderFulfilment;
    @FindBy(xpath = "//input[contains(@id,'work_order_fulfilment_time_picker')]")
    public WebElement input_woTime_creteWorkOrderFulfilment;
    @FindBy(xpath = "//select[contains(@id,'work_order_fulfilment_bill_type')]")
    public WebElement select_billType_creteWorkOrderFulfilment;
    @FindBy(xpath = "//input[contains(@id,'work_order_fulfilment_bill_number')]")
    public WebElement input_billNo_creteWorkOrderFulfilment;
    @FindBy(xpath = "//input[contains(@id,'work_order_fulfilment_bill_date')] ")
    public WebElement input_billDate_creteWorkOrderFulfilment;

    @FindBy(xpath = "//input[contains(@id,'work_order_fulfilment_challan_number')]")
    public WebElement input_challanNo_creteWorkOrderFulfilment;
    @FindBy(xpath = "//input[contains(@id,'work_order_fulfilment_challan_date')] ")
    public WebElement input_challanDate_creteWorkOrderFulfilment;

    @FindBy(xpath = "//input[@id='plus_other_charges_amount'] ")
    public WebElement input_otherCharges_creteWorkOrderFulfilment;

    @FindBy(xpath = "//table[@class='ui-datepicker-calendar']/tbody/tr/td[contains(@class,'ui-datepicker-today')]")
    public WebElement input_todayBillDate;
    @FindBy(xpath = "//table[@id='transaction_table']/tbody/tr/td[2]/span")
    public List<WebElement> list_textItemDescription_createWOF;
    @FindBy(xpath = "//table[@id='transaction_table']/tbody/tr/td[3]/span")
    public List<WebElement> list_textInstructions_createWOF;
    @FindBy(xpath = "//table[@id='transaction_table']/tbody/tr/td[4]/span")
    public List<WebElement> list_textRate_createWOF;
    @FindBy(xpath = "//table[@id='transaction_table']/tbody/tr/td[5]/span")
    public List<WebElement> list_textWoQuantity_createWOF;
    @FindBy(xpath = "//table[@id='transaction_table']/tbody/tr/td[6]/span")
    public List<WebElement> list_textPendingQty_createWOF;
    @FindBy(xpath = "//table[@id='transaction_table']/tbody/tr/td[7]/input")
    public List<WebElement> list_inputQuantity_createWOF;

    @FindBy(xpath = "//table[@id='transaction_table']/tbody/tr/td[8]/div")
    public List<WebElement> list_textDiscount_createWOF;
    @FindBy(xpath = "//table[@id='transaction_table']/tbody/tr/td[9]/span")
    public List<WebElement> list_textNetAmount_createWOF;
    @FindBy(xpath = "//table[@id='transaction_table']/tbody/tr/td[11]/input")
    public List<WebElement> list_inputRemark_createWOF;
    @FindBy(xpath = "//label[contains(@id,'stock-error')] ")
    public List<WebElement> label_quantityErrorMsg;
    @FindBy(xpath = "//label[@id='inventory_transaction_work_order_fulfilment_bill_type-error'] ")
    public WebElement label_billTypeErrorMsg;
    @FindBy(xpath = "//label[@id='plus_other_charges_amount-error'] ")
    public WebElement label_otherChargesErrorMsg;

    @FindBy(xpath = "//input[@value='Save Changes'] ")
    public WebElement button_saveChanges;
    @FindBy(xpath = "//span[@class='pending_other_charges'] ")
    public WebElement text_displayedPendingOtherCharges;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/thead/tr/td")
    public List<WebElement> list_wofHeaderList;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//tr")
    public List<WebElement> list_wofCreatedList;

    @FindBy(xpath = "//b[text()='WOF No.']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_wofNo;
    @FindBy(xpath = "//b[text()='Created By']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_wofCreatedByUser;
    @FindBy(xpath = "//b[text()='Created By']/parent::div/following-sibling::div/span[3]")
    public WebElement text_rhs_wofCreatedAt;
    @FindBy(xpath = "//b[text()='Status']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_wofStatus;
    @FindBy(xpath = "//b[text()='Vendor']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_wofVendor;
    @FindBy(xpath = "//b[text()='Vendor Location']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_wofVendorLocation;
    @FindBy(xpath = "//b[text()='GSTIN']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_wofVendorGST;
    @FindBy(xpath = "//b[text()='Cancelled By']/parent::div/following-sibling::div/b[1]")
    public WebElement text_rhs_wofCancelledByUser;
    @FindBy(xpath = "//b[text()='Cancelled By']/parent::div/following-sibling::div/b[3]")
    public WebElement text_rhs_wofCancelledAt;
    @FindBy(xpath = "//b[text()='Cancelled By']/parent::div/following-sibling::div/b[5]")
    public WebElement text_rhs_wofCancelledReason;
    @FindBy(xpath = "//table[contains(@class,'table-striped')]/thead//th")
    public List<WebElement> list_rhsTableHeader;
    @FindBy(xpath = "//table[contains(@class,'table-striped')]/tbody/tr")
    public List<WebElement> list_rhsTableRow;
    @FindBy(xpath = "//div[@id='item_information']//b")
    public List<WebElement> list_rhsItemInfoHeader;
    @FindBy(xpath = "//div[@id='item_information']//b/parent::div/following-sibling::div[2]")
    public List<WebElement> list_rhsItemInfoValue;
    @FindBy(xpath = "//input[contains(@id,'work_order_fulfilment_updated_reason')]")
    public WebElement input_updateReason;
    @FindBy(xpath = "//button[text()='Yes']")
    public WebElement button_yes;
    @FindBy(xpath = "//a[contains(@href,'work_order_fulfilments/print')]")
    public WebElement button_print;
    @FindBy(xpath = "//h4[text()='Approved']")
    public WebElement label_approved;
    @FindBy(xpath = "//h4[text()='Cancelled']")
    public WebElement label_cancelled;
    @FindBy(xpath = "//input[@id='cancelled_reason']")
    public WebElement input_cancellationReason;
    @FindBy(xpath = "//button[contains(@class,'btn-success close-button')]")
    public WebElement button_dontCancel;
    @FindBy(xpath = "//*[@value='Cancel Work Order Fulfilment']")
    public WebElement button_confirmCancel;
    @FindBy(xpath = "//span[normalize-space()='×']")
    public WebElement button_closeCreateWO;




}
