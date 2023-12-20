package pages.store.PharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_PaymentRequisitionForm extends TestBase {
    private WebDriver driver;

    public Page_PaymentRequisitionForm(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//a[text()=' New']")
    public WebElement button_newPaymentRequisitionForm;
    @FindBy(xpath = "//i[contains(@class,'edit')]")
    public WebElement button_edit;
    @FindBy(xpath = "//button[contains(@class,'cancel-purchase')]")
    public WebElement button_cancelPaymentRequisition;
    @FindBy(xpath = "//select[contains(@id,'vendor_location')]")
    public WebElement select_vendor;
    @FindBy(xpath = "//tbody[contains(@class,'transaction-purchases-body')]/tr")
    public List<WebElement> row_createPaymentRequisitionForm;
    @FindBy(xpath = "//*[@value='Save Changes']")
    public WebElement button_saveChanges;
    @FindBy(xpath = "//div[@class='btn-group']/a[text()='Approve']")
    public WebElement button_approvePaymentRequisitionForm;

    @FindBy(xpath = "//input[contains(@id,'requisition_form_date')]")
    public WebElement input_paymentRequisitionDate;
    @FindBy(xpath = "//input[contains(@id,'requisition_form_time_picker')]")
    public WebElement input_paymentRequisitionTime;
    @FindBy(xpath = "//button[@class='close']/span")
    public WebElement button_closeWithoutSaving;

    @FindBy(xpath = "//input[@role='textbox']")
    public WebElement input_Vendor_search;
    @FindBy(xpath = "//li[contains(@id,'payment_requisition_form')]")
    public List<WebElement> list_select_vendor;
    @FindBy(xpath = "//tbody[contains(@class,'transaction-purchases-body')]/tr/td[2]")
    public List<WebElement> list_purchaseBillNoInRowCreatePrf;
    @FindBy(xpath = "//tbody[contains(@class,'transaction-purchases-body')]/tr/td[1]/span[1]")
    public List<WebElement> list_pbDateAndTimeInRowCreatePrf;
    @FindBy(xpath = "//tbody[contains(@class,'transaction-purchases-body')]/tr/td[1]/span[2]")
    public List<WebElement> list_pbCreatedUserInRowCreatePrf;
    @FindBy(xpath = "//tbody[contains(@class,'transaction-purchases-body')]/tr/td[3]")
    public List<WebElement> list_pbNetAmountInRowCreatePrf;
    @FindBy(xpath = "//tbody[contains(@class,'transaction-purchases-body')]/tr/td[4]/b")
    public List<WebElement> list_pbVendorInRowCreatePrf;
    @FindBy(xpath = "//button[@title='Remove From List']")
    public WebElement button_removeFromList;
    @FindBy(xpath = "//input[@id='purchases_search']")
    public WebElement input_searchPurchase;
    @FindBy(xpath = "//input[@id='purchases_search']/parent::div//i")
    public WebElement button_searchClearPurchase;
    @FindBy(xpath = "//a[@id='time_period_payment_requisition_form']/following-sibling::button")
    public WebElement button_timePeriod;
    @FindBy(xpath = "//input[contains(@id,'payment_requisition_form_vendor_gst_number')][@type='text']")
    public WebElement input_vendorGstNo;

    @FindBy(xpath = "//table[@class='ui-datepicker-calendar']/tbody/tr/td[contains(@class,'ui-datepicker-today')]")
    public WebElement input_todayBillDate;
    @FindBy(xpath = "//input[@id='inventory_transaction_payment_requisition_form_note']")
    public WebElement input_transactionNote;

    @FindBy(xpath = "//div[@class='payment-requisition-form-table']//thead//td")
    public List<WebElement> list_tableHeaderCreatePrf;
    @FindBy(xpath = "//div[@class='payment-requisition-form-table']//tbody//td/span")
    public List<WebElement> list_tableBodyCreatePrf;
    @FindBy(xpath = "//input[contains(@id,'remarks')]")
    public WebElement input_remark;
    @FindBy(xpath = "//input[contains(@id,'net_amount_total')]")
    public WebElement input_totalNetAmount;
    @FindBy(xpath = "//b[text()='PRF No.']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_prfNo;
    @FindBy(xpath = "//b[text()='Vendor']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_vendor;
    @FindBy(xpath = "//b[text()='Vendor GSTIN No.']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_vendorGstInNo;
    @FindBy(xpath = "//b[text()='Vendor GSTIN No.']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_vendorAddress;
    @FindBy(xpath = "//b[text()='Status']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_status;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/thead/tr/td")
    public List<WebElement> list_prfHeaderList;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//tr")
    public List<WebElement> list_prfCreatedList;
    @FindBy(xpath = "//h4[text()='Update Payment Requisition Form']")
    public WebElement text_updatePaymentRequisition;
    @FindBys({
            @FindBy(xpath = "//h4[text()='Approved']"),
            @FindBy(xpath = "//div[text()='This PRF is approved']")
    })
    public WebElement text_approvedNotifyMsg;
    @FindBys({
            @FindBy(xpath = "//h4[text()='Cancelled']"),
            @FindBy(xpath = "//div[text()='This PRF is cancelled']")
    })
    public WebElement text_cancelledNotifyMsg;
    @FindBy(xpath = "//b[text()='Approved By']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_approvedByUser;
    @FindBy(xpath = "//b[text()='Cancelled By']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_cancelledByUser;
    @FindBy(xpath = "//b[text()='Created By']/parent::div/following-sibling::div/span[3]")
    public WebElement text_rhs_createdAt;
    @FindBy(xpath = "//b[text()='Created By']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_createdBy;
    @FindBy(xpath = "//b[text()='Vendor Location']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_vendorLocation;
    @FindBy(xpath = "//b[text()='Cancelled Reason']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_cancelledReason;
    @FindBy(xpath = "//input[@id='cancelled_reason']")
    public WebElement input_cancellationReason;
    @FindBy(xpath = "//button[contains(@class,'btn-success close-button')]")
    public WebElement button_dontCancel;
    @FindBy(xpath = "//*[@value='Cancel Payment Requisition Form']")
    public WebElement button_confirmCancel;
    @FindBy(xpath = "//a[contains(@href,'payment_requisition_forms/print')]")
    public WebElement button_print;
    @FindBy(xpath = "//button[@class='btn btn-info cancel-filter']")
    public WebElement button_noDontChangeVendor;
    @FindBy(xpath = "//button[@class='btn btn-danger']")
    public WebElement button_yesChangeVendor;
    @FindBy(xpath = "//p[text()='You are about to change vendor']")
    public WebElement text_vendorChangeConfirmationMessage;

    @FindBy(xpath = "//button[@class='btn btn-primary btn-sm inventory_search_panel_dropdown']/following-sibling::ul[@class='dropdown-menu']/li")
    public List<WebElement> list_searchFilterPrfNoDropdown;
    @FindBy(xpath = "//span[@id='inventory_search_criteria']")
    public WebElement select_searchFilterPrfNo;
    @FindBy(xpath = "//input[@id='inventory_search']")
    public WebElement input_searchbarCriteria;

    @FindBy(xpath = "//button[@class='btn btn-primary inventory-search-button']")
    public WebElement button_searchItem;
    @FindBy(xpath = "//tbody[@class='inventory-table-body']/tr/td")
    public List<WebElement> list_searchPrfDropDown;

    @FindBy(xpath = "//tbody[@class='inventory-table-body']/tr/td[2]")
    public WebElement text_searchPrfNo;

    @FindBy(xpath = "//tbody[@class='inventory-table-body']/tr")
    public List<WebElement> list_searchItem;

    @FindBy(xpath = "//button[@class='btn btn-primary inventory-clear-button']")
    public WebElement button_clearButtonInSearchBx;
}
