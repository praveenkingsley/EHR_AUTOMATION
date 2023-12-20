package pages.store.PharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_PurchaseBill extends TestBase {
    private WebDriver driver;

    public Page_PurchaseBill(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[text()=' New']")
    public WebElement button_purchaseNew;
    @FindBy(xpath = "//a[@class='btn btn-primary btn-xs']/i")
    public WebElement button_edit;
    @FindBy(xpath = "//button[contains(@class,'cancel-purchase')]")
    public WebElement button_cancel;
    @FindBy(xpath = "//a[contains(@href,'purchase_bills/print')]")
    public WebElement button_print;
    @FindBy(xpath = "//a[text()='Approve']")
    public WebElement button_approve;
    @FindBy(xpath = "//h4[text()='Approved']")
    public WebElement notifyMsg_Approved;
    @FindBy(xpath = "//input[@id='cancelled_reason']")
    public WebElement input_cancellationReason;
    @FindBy(xpath = "//button[contains(@class,'btn-success close-button')]")
    public WebElement button_dontCancel;
    @FindBy(xpath = "//*[@value='Cancel Purchase Bill']")
    public WebElement button_confirmCancel;
    @FindBy(xpath = "//select[contains(@id,'vendor')]")
    public WebElement select_vendorOptionsCreatePurchaseBill;
    @FindBy(xpath = "//select[contains(@id,'create_against')]")
    public WebElement select_createAgainstPurchaseBill;
    @FindBy(xpath = "//table[contains(@class,'purchase-transactions-table')]/tbody/tr")
    public List<WebElement> list_RowsOnCreatePurchaseBillTable;
    @FindBy(xpath = "//b[text()='Vendor Invoice']/parent::div/following-sibling::div/a[text()='View']")
    public WebElement button_viewVendorInvoicePurchaseBill;
    @FindBy(xpath = "//b[text()='Vendor Invoice']/parent::div/following-sibling::div/a[text()='Upload']")
    public WebElement button_uploadVendorInvoicePurchaseBill;
    @FindBy(xpath = "//b[text()='Vendor Docs']/parent::div/following-sibling::div/a[text()='View']")
    public WebElement button_viewVendorDocsPurchaseBill;
    @FindBy(xpath = "//span[text()='Add files']")
    public WebElement button_addFilesVendorInvoicePurchaseBill;
    @FindBy(xpath = "//input[@value='Upload Files']")
    public WebElement button_uploadFilesVendorInvoicePurchaseBill;
    @FindBy(xpath = "//div[contains(@class,'filemanager')]//button[contains(@class,'dropdown-toggle')]")
    public WebElement button_dropdownDeleteVendorInvoicePurchaseBill;
    @FindBy(xpath = "//a[@data-action='destroy']")
    public WebElement button_deleteFilesVendorInvoicePurchaseBill;
    @FindBy(xpath = "//small[contains(text(),'Added')]")
    public WebElement button_uploadedImageVendorInvoicePurchaseBill;
    @FindBy(xpath = "//tr[contains(@class,'transaction-list-row')]/td[1]/span[2]")
    public List<WebElement> list_textGRNnoPurchaseBillTable;
    @FindBy(xpath = "//tr[contains(@class,'transaction-list-row')]/td[3]/span[2]")
    public List<WebElement> list_textGrnBillNoPurchaseBillTable;
    @FindBy(xpath = "//tr[contains(@class,'transaction-list-row')]/td[2]/span[2]")
    public List<WebElement> list_textGrnPONoPurchaseBillTable;
    @FindBy(xpath = "//tr[contains(@class,'transaction-list-row')]/td[3]/span[2]")
    public List<WebElement> list_textGrnChallanNoPurchaseBillTable;
    @FindBy(xpath = "//tr[contains(@class,'transaction-list-row')]/td[1]/span[1]")
    public List<WebElement> list_textGRNApprovedAtPurchaseBillTable;
    @FindBy(xpath = "//tr[contains(@class,'transaction-list-row')]/td[1]/span[3]")
    public List<WebElement> list_textGRNApprovedByUserPurchaseBillTable;
    @FindBy(xpath = "//input[@id='purchase_bill_search']")
    public WebElement input_searchPurchaseBill;
    @FindBy(xpath = "//button[@id='purchase_bill_search_button']")
    public WebElement btn_submitSearchPurchaseBill;

    @FindBy(xpath = "//button[@class='btn  dropdown-toggle purchase-bill-search-criteria']")
    public WebElement btn_searchTypePurchaseBill;
    @FindBy(xpath = "//a[text()='GRN No' and @class ='search-criteria-list']")
    public WebElement btn_searchTypeGRNnoPurchaseBill;
    @FindBy(xpath = "//a[text()='PO No' and @class ='search-criteria-list']")
    public WebElement btn_searchTypePOnoPurchaseBill;
    @FindBy(xpath = "//a[text()='Bill No' and @class ='search-criteria-list']")
    public WebElement btn_searchTypeBillNoPurchaseBill;
    @FindBy(xpath = "//a[text()='Challan No' and @class ='search-criteria-list']")
    public WebElement btn_searchTypeChallanNoPurchaseBill;
    @FindBy(xpath = "//input[contains(@id,'purchase_bill_vendor_gst_number')][@type='text']")
    public WebElement text_vendorGstNoPurchaseBill;
    @FindBy(xpath = "//input[contains(@id,'invoice_number')][@type='text']")
    public WebElement input_invoiceNoPurchaseBill;
    @FindBy(xpath = "//input[contains(@id,'invoice_date')][@type='text']")
    public WebElement input_invoiceDatePurchaseBill;
    @FindBy(xpath = "//input[contains(@id,'purchase_bill_note')][@type='text']")
    public WebElement input_transactionNotePurchaseBill;
    @FindBy(xpath = "//table[@id='purchase-bill-table']/thead//td")
    public List<WebElement> list_headerPurchaseBillTable;
    @FindBy(xpath = "//table[@id='purchase-bill-table']/tbody/tr[2]/td")
    public List<WebElement> list_bodyPurchaseBillTable;
    @FindBy(xpath = "//div[contains(@class,'cgst-tax-name')]/input")
    public WebElement input_cgstRate;
    @FindBy(xpath = "//div[contains(@class,'cgst-tax-amount')]/input[@type='text']")
    public WebElement input_cgstAmount;
    @FindBy(xpath = "//div[contains(@class,'sgst-tax-name')]/input")
    public WebElement input_sgstRate;
    @FindBy(xpath = "//div[contains(@class,'sgst-tax-amount')]/input[@type='text']")
    public WebElement input_sgstAmount;
    @FindBy(xpath = "//div[contains(@class,'igst-tax-name')]/input")
    public WebElement input_igstRate;
    @FindBy(xpath = "//div[contains(@class,'igst-tax-amount')]/input[@type='text']")
    public WebElement input_igstAmount;
    @FindBy(xpath = "//button[@title='Remove From List']")
    public WebElement button_removeFromList;
    @FindBy(xpath = "//a[@id='time_period_purchase_bill']/following-sibling::button")
    public WebElement button_timePeriod;
    @FindBy(xpath = "//div[contains(@class,'purchase-invoice-information')]/div[2]//b")
    public List<WebElement> list_textHeadingInvoiceInfoPurchaseBill;
    @FindBy(xpath = "//div[contains(@class,'purchase-invoice-information')]/div[2]//input")
    public List<WebElement> list_inputValueInvoiceInfoPurchaseBill;

    @FindBy(xpath = "//table[@class='table inventory_table_center']/thead/tr/td")
    public List<WebElement> list_purchaseBillHeaderList;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//tr")
    public List<WebElement> list_purchaseBillRowList;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//tr/td[2]/span[2]")
    public List<WebElement> list_purchaseBillRowPBnoList;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//tr/td[3]")
    public List<WebElement> list_purchaseBillRowInvoicenoList;
    @FindBy(xpath = "//input[contains(@id,'transaction_date')]")
    public WebElement input_purchaseBillDate;
    @FindBy(xpath = "//input[contains(@id,'transaction_time_picker')]")
    public WebElement input_purchaseBillTime;

    @FindBy(xpath = "//b[text()='Purchase Bill No.']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_purchaseBillNo;
    @FindBy(xpath = "//b[text()='Invoice No.']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_InvoiceNo;
    @FindBy(xpath = "//b[text()='Created By']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_purchaseBillCreatedByUser;
    @FindBy(xpath = "//b[text()='Created By']/parent::div/following-sibling::div/span[3]")
    public WebElement text_rhs_purchaseBillCreatedAt;
    @FindBy(xpath = "//b[text()='Cancelled By']/parent::div/following-sibling::div/span[5]")
    public WebElement text_rhs_purchaseBillCancelledReason;
    @FindBy(xpath = "//b[text()='Vendor']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_purchaseBillVendor;
    @FindBy(xpath = "//b[text()='Vendor GSTIN']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_purchaseBillVendorGSTno;
    @FindBy(xpath = "//input[@id='inventory_search']")
    public WebElement text_rhs_searchInvoiceNo;
    @FindBy(xpath = "//b[text()='Status']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_purchaseBillStatus;

    @FindBy(xpath = "//*[@value='Save Changes']")
    public WebElement button_saveChanges;

    @FindBy(xpath = "//button[@class='btn btn-info cancel-filter']")
    public WebElement button_noDontChangeVendor;
    @FindBy(xpath = "//button[@class='btn btn-danger']")
    public WebElement button_yesChangeVendor;
    @FindBy(xpath = "//p[text()='You are about to change vendor']")
    public WebElement text_vendorChangeConfirmationMessage;

    @FindBy(xpath = "//table[@class='ui-datepicker-calendar']/tbody/tr/td[contains(@class,'ui-datepicker-today')]")
    public WebElement input_todayBillDate;
    @FindBy(xpath = "//b[text()='Net Amount']/parent::div/following-sibling::div[2]/input")
    public WebElement text_netAmountValue;
    @FindBy(xpath = "//b[text()='Invoice No.']/parent::div/following-sibling::div/span[3]")
    public WebElement text_rhs_InvoiceDate;
}
