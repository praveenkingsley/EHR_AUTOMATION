package pages.store.PharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_SRN extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_SRN(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//a[@class='btn btn-primary btn-sm']/i")
    public WebElement button_addNew;

    @FindBy(xpath = "//h4[text()='Stock Receive Note']")
    public WebElement header_stockReceiveNoteHeader;

    @FindBy(xpath = "//input[@id='sale_order_search']")
    public WebElement input_saleOrderSearchSRN;

    @FindBy(xpath = "//tbody[@class='items-variants-body']//tr")
    public List<WebElement> list_salesOrderListInSRNTemplate;

    @FindBy(xpath = "//span[@class='select2-selection select2-selection--single']//span[@class='select2-selection__arrow']")
    public WebElement dropdown_vendorDropdownArrow;

    @FindBy(xpath = "//input[@class='select2-search__field']")
    public WebElement input_searchVendorBox;

    @FindBy(xpath = "//ul[@id='select2-inventory_transaction_stock_receive_note_vendor_id-results']/li")
    public List<WebElement> list_srnVendorList;

    @FindBy(xpath = "//select[@id='inventory_transaction_stock_receive_note_bill_type']")
    public WebElement dropdown_selectBillType;

    @FindBy(xpath = "//input[@id='inventory_transaction_stock_receive_note_bill_number']")
    public WebElement input_billNumber;

    @FindBy(xpath = "//input[@id='inventory_transaction_stock_receive_note_bill_date']")
    public WebElement input_billDate;

    @FindBy(xpath = "//table[@class='ui-datepicker-calendar']/tbody/tr/td[contains(@class,'ui-datepicker-today')]")
    public WebElement input_todayBillDate;

    @FindBy(xpath = "//tbody[@id='tbody_medication']//tr/td[1]/strong")
    public List<WebElement> list_itemDescriptionListInSrn;
    @FindBy(xpath = "//tbody[@id='tbody_medication']//tr/td[2]/input")
    public List<WebElement> list_mrpListInSrn;
    @FindBy(xpath = "//tbody[@id='tbody_medication']//tr/td[3]/input[1]")
    public List<WebElement> list_unitCostListInSrn;
    @FindBy(xpath = "//tbody[@id='tbody_medication']//tr/td[4]/input")
    public List<WebElement> list_quantityListInSrn;
    @FindBy(xpath = "//tbody[@id='tbody_medication']//tr/td[5]")
    public List<WebElement> list_taxRateListInSrn;
    @FindBy(xpath = "//tbody[@id='tbody_medication']//tr/td[6]")
    public List<WebElement> list_taxInclusiveListInSrn;
    @FindBy(xpath = "//tbody[@id='tbody_medication']//tr/td[7]/input[1]")
    public List<WebElement> list_taxableAmountListInSrn;

    @FindBy(xpath = "//tbody[@id='tbody_medication']//tr/td[8]/input")
    public List<WebElement> list_taxAmountListInSrn;

    @FindBy(xpath = "//tbody[@id='tbody_medication']//tr/td[9]/input")
    public List<WebElement> list_totalCostListInSrn;

    @FindBy(xpath = "//input[@name='inventory_transaction_stock_receive_note[taxable_amount]']")
    public WebElement input_srnTaxableAmountInTaxCage;

    @FindBy(xpath = "(//div[@id='tax-cage']//input[@name='inventory_transaction_stock_receive_note[tax_breakup][][amount]'])[1]")
    public WebElement input_srnSgstTaxValue;

    @FindBy(xpath = "(//div[@id='tax-cage']//input[@name='inventory_transaction_stock_receive_note[tax_breakup][][amount]'])")
    public List<WebElement> list_gstValuesList;

    @FindBy(xpath = "(//div[@id='tax-cage']//input[@name='inventory_transaction_stock_receive_note[tax_breakup][][amount]'])[2]")
    public WebElement input_srnCgstTaxValue;

    @FindBy(xpath = "//input[@name='inventory_transaction_stock_receive_note[total_cost]']")
    public WebElement input_srnTotalAmountIncTax;

    @FindBy(xpath = "//input[@id='total_other_charges_amount']")
    public WebElement input_srnOtherChargesValue;
    @FindBy(xpath = "//input[@id='inventory_transaction_stock_receive_note_net_amount']")
    public WebElement input_srnNetAmountValue;

    @FindBy(xpath = "//input[@id='inventory_transaction_stock_receive_note_transaction_date']")
    public WebElement input_srnTransactionDateValue;

    @FindBy(xpath = "//input[@id='inventory_transaction_stock_receive_note_transaction_time_picker']")
    public WebElement input_srnTransactionTimeValue;

    @FindBy(xpath = "//select[contains(@id,'inventory_transaction_stock_receive_note_other_charges_attributes_0_other_charge_id')]")
    public WebElement select_otherCharges;

    @FindBy(xpath = "//input[@id='inventory_transaction_stock_receive_note_other_charges_attributes_0_amount_show']")
    public WebElement input_otherChargesAmount;

    @FindBy(xpath = "//div[text()='Blocked Stock:']/following-sibling::div[1]/strong")
    public WebElement text_lotBlockedStockAmount;

    @FindBy(xpath = "//div[text()='Available Stock:']/following-sibling::div[1]/strong")
    public WebElement text_lotAvailableStockAmount;
    @FindBy(xpath = "//input[@id='inventory_transaction_stock_receive_note_note']")
    public WebElement input_srnNotes;
    @FindBy(xpath = "//input[@id='inventory_transaction_stock_receive_note_discount']")
    public WebElement input_srnDiscount;
    @FindBy(xpath = "//b[contains(text() ,'Status:')]")
    public WebElement text_statusRhsSide;
    @FindBy(xpath = "//div[contains(text() ,'Transaction')]")
    public WebElement text_transactionIdRhs;
    @FindBy(xpath = "//h4[contains(text() ,'Transaction Details:')]")
    public WebElement header_transactionDetailsSection;
    @FindBy(xpath = "//b[text()='Total Amt Including Tax']/parent::div/following-sibling::div[2]")
    public WebElement text_totalAmtIncTax;
    @FindBy(xpath = "//b[text()='Net Amount']/parent::div/following-sibling::div[2]")
    public WebElement text_netAmount;
    @FindBy(xpath = "//b[text()='Taxable Amount']/parent::div/following-sibling::div[2]")
    public WebElement text_taxableAmt;
    @FindBy(xpath = "//b[contains(text(),'SGST')]/parent::div/following-sibling::div[2]")
    public WebElement text_sgst;
    @FindBy(xpath = "//b[contains(text(),'CGST')]/parent::div/following-sibling::div[2]")
    public WebElement text_cgst;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//thead//th")
    public List<WebElement> list_transactionDetailsHeaderList;
    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tbody//td")
    public List<WebElement> list_transactionDetailsValueList;


}
