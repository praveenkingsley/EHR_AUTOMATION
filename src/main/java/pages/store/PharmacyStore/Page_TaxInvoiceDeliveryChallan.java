package pages.store.PharmacyStore;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_TaxInvoiceDeliveryChallan extends TestBase {

    public Page_TaxInvoiceDeliveryChallan(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[normalize-space()='New']")
    public WebElement button_new;
    @FindBy(css = ".fa.fa-refresh")
    public WebElement button_refresh;
    @FindBy(xpath = "//a[@class='btn btn-primary btn-xs']")
    public WebElement button_edit;
    @FindBy(xpath = "//a[@class='btn btn-danger btn-xs cancel-tax-invoice']")
    public WebElement button_cancel;
    @FindBy(xpath = "//a[contains(@href,'approve')]")
    public WebElement button_approve;
    @FindBy(xpath = "//a[contains(@href,'print_tax_invoice')]")
    public WebElement button_print;
    @FindBy(xpath = "//a[normalize-space()='New Tax Invoice']")
    public WebElement button_taxInvoice;
    @FindBy(xpath = "//a[normalize-space()='New Delivery Challan']")
    public WebElement button_deliveryChallan;
    @FindBy(xpath = "//a[@id='inventory_tax_invoice_name']/following-sibling::button")
    public WebElement button_sortTaxInvoiceAndDeliveryChallan;
    @FindBy(xpath = "//a[normalize-space()='Tax Invoice']")
    public WebElement button_sortByTaxInvoice;
    @FindBy(xpath = "//a[@class='tax_invoice-filter'][normalize-space()='Delivery Challan']")
    public WebElement button_sortByDeliveryChallan;
    @FindBy(xpath = "//h4[text()='Create Tax Invoice']")
    public WebElement text_headerCreateTaxInvoice;
    @FindBy(xpath = "//h4[text()='Create Delivery Challan']")
    public WebElement text_headerCreateDeliveryChallan;
    @FindBy(xpath = "//select[@id='inventory_tax_invoice_created_against_store_id']")
    public WebElement select_againstStore;
    @FindBy(xpath = "//a[contains(text(),'Tax Invoice / Delivery Challan')]")
    public WebElement tab_taxInvoiceDeliveryChallan;
    @FindBy(xpath = "//table[@class='table items-variants-table']/tbody/tr")
    public List<WebElement> row_lhsCreateTaxInvoiceDeliveryChallan;

    @FindBy(xpath = "//table[@class='table items-variants-table']/tbody/tr/td[3]/div[1]")
    public List<WebElement> row_transferIdLhsCreateTaxInvoiceDeliveryChallan;
    @FindBy(xpath = "//button[contains(@class,'delete_empty_item')]")
    public WebElement button_deleteItem;
    @FindBy(xpath = "//table[@id='medicationTable']/thead//td")
    public List<WebElement> list_tableHeaderCreateTiDc;
    @FindBy(xpath = "//table[@id='medicationTable']/tbody/tr[contains(@class,'tax-invoice-row')][1]/td")
    public List<WebElement> list_textBodyValueCreateTiDc;
    @FindBy(xpath = "//table[@id='medicationTable']/tbody/tr[contains(@class,'tax-invoice-row')]")
    public List<WebElement> list_rowCreateTiDc;

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
    @FindBy(xpath = "//b[text()='Taxable Amount']/parent::div/following-sibling::div/input")
    public WebElement input_finalTaxableAmount;
    @FindBy(xpath = "//b[text()='Tax Amount']/parent::div/following-sibling::div/input")
    public WebElement input_finalTaxAmount;
    @FindBy(xpath = "//b[text()='Total Amount']/parent::div/following-sibling::div/input")
    public WebElement input_finalTotalAmount;
    @FindBy(css = "#inventory_tax_invoice_transportation_mode")
    public WebElement select_transportationMode;
    @FindBy(css = "#inventory_tax_invoice_transportation_transaction_id")
    public WebElement input_transactionId;
    @FindBy(css = "#inventory_tax_invoice_delivery_date")
    public WebElement input_deliverydate;
    @FindBy(css = "#inventory_tax_invoice_dispatch_remarks")
    public WebElement input_dispatchRemark;
    @FindBy(xpath = "//input[@id='inventory_tax_invoice_transaction_date']")
    public WebElement input_date;
    @FindBy(xpath = "//input[@id='inventory_tax_invoice_transaction_time_picker']")
    public WebElement input_time;
    @FindBy(xpath = "//div[contains(@class,'items-variants')]//button")
    public WebElement button_timePeriod;
    @FindBy(xpath = "//table[@class='ui-datepicker-calendar']/tbody/tr/td[contains(@class,'ui-datepicker-today')]")
    public WebElement input_todayBillDate;
    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveChanges;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/thead/tr/td")
    public List<WebElement> list_transactionHeaderList;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//tr")
    public List<WebElement> list_transactionCreatedList;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//tr/td[3]")
    public List<WebElement> list_transactionDetailsCreatedList;
    @FindBy(xpath = "//b[text()='Transaction ID']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_transactionId;
    @FindBy(xpath = "//b[text()='Transaction Type']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_transactionType;
    @FindBy(xpath = "//b[text()='To']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_toStoreName;
    @FindBy(xpath = "//b[text()='Created By']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_createdByUser;
    @FindBy(xpath = "//b[text()='Created By']/parent::div/following-sibling::div/span[3]")
    public WebElement text_rhs_createdAt;
    @FindBy(xpath = "//b[text()='Approved By']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_approvedByUser;
    @FindBy(xpath = "//b[text()='Approved By']/parent::div/following-sibling::div/span[3]")
    public WebElement text_rhs_ApprovedAt;
    @FindBy(xpath = "//b[text()='Cancelled By']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_cancelledByUser;
    @FindBy(xpath = "//b[text()='Cancelled By']/parent::div/following-sibling::div/span[3]")
    public WebElement text_rhs_cancelledAt;
    @FindBy(xpath = "//b[text()='Cancel Reason']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_cancelReason;
    @FindBy(xpath = "//b[text()='Status']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_status;
    @FindBy(xpath = "//table[@id='medicationTable']/tbody/tr[contains(@class,'tax-invoice-row')][1]/td[2]")
    public WebElement text_tableDescription;
    @FindBy(xpath = "//input[@id='inventory_search']")
    public WebElement input_globalSearch;
    @FindBys({
            @FindBy(xpath = "//h4[text()='Approved']"),
            @FindBy(xpath = "//div[text()='This Transaction is approved']")
    })
    public WebElement text_approvedNotifyMsg;
    @FindBy(xpath = "//input[@id='cancelled_reason']")
    public WebElement input_cancellationReason;
    @FindBy(xpath = "//button[contains(@class,'btn-danger close-button')]")
    public WebElement button_dontCancel;

    @FindAll({
            @FindBy(xpath = "//*[@value='Cancel Tax invoice']"),
            @FindBy(xpath = "//*[@value='Cancel Delivery challan']")
    })
    public WebElement button_confirmCancel;

    @FindBy(xpath = "//h4[text()='Cancelled']")
    public WebElement text_cancelledNotifyMsg;

    @FindBy(xpath = "//i[@class='fa fa-times-circle']")
    public WebElement button_clearGlobalSearch;

    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//tr//td[4]")
    public List<WebElement> list_transactionCreatedIdList;


}
