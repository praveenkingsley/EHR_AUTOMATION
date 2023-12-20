package pages.store.PharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_SaleReturn extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_SaleReturn(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = " //div[@class='mainpanel']//h4")
    public WebElement header_panelOnLeft;

    @FindBy(xpath = "//ul[@class='nav nav-pills nav-stacked nav-bracket']/li/a")
    public List<WebElement> list_ParentOptionsOnLeft;

    @FindBy(xpath = "//ul[@class='nav nav-pills nav-stacked nav-bracket']/li/a/span")
    public List<WebElement> list_namesParentOptionsOnLeft;

    @FindBy(xpath = "//ul[@class='nav nav-pills nav-stacked nav-bracket']/li//li")
    public List<WebElement> list_ChildOptionsOnLeft;

    @FindBy(xpath = "//a[normalize-space()='New']")
    public WebElement button_new;
    @FindBy(xpath = "//input[@id='search-patient']")
    public WebElement input_searchPatient;
    @FindBy(xpath = "//select[@id='patient_search']")
    public WebElement input_searchTypeFilter;
    @FindBy(xpath = "//button[contains(@class,'search-patient-button')]")
    public WebElement button_searchPatient;
    @FindBy(xpath = "//button[contains(@class,'patient-list')]")
    public List<WebElement> list_searchedPatientList;
    @FindBy(xpath = "//button[contains(@class,'patient-list')]/div[2]/b")
    public List<WebElement> list_searchedPatientIdList;

    @FindBy(xpath = "//table[@class='table items-lots-table']//tr[contains(@class,'inventory-list-row')] ")
    public List<WebElement> list_itemRowRequest;
    @FindBy(xpath = "//input[contains(@id,'unit_checked')] ")
    public WebElement checkbox_lot;
    @FindBy(xpath = "//input[@class='btn btn-success'] ")
    public WebElement button_saveChangesLot;
    @FindBy(xpath = "//input[contains(@class,'item_quantity')] ")
    public WebElement input_itemQuantity;
    @FindBy(xpath = "//input[contains(@class,'inventory-transaction-return-checkbox')] ")
    public WebElement checkbox_verified;
    @FindBy(xpath = "//input[contains(@class,'inventory-transaction-return-checkbox')] ")
    public List<WebElement> list_checkbox_verified;
    @FindBy(xpath = "//select[contains(@name,'inventory_transaction_return[mop_breakups_attributes][0][mode_of_payment]')]")
    public WebElement select_returnMop;

    @FindBy(xpath = "//input[@name='commit'] ")
    public WebElement button_saveChanges;

    @FindBy(xpath = "//input[@id='inventory_transaction_return_note'] ")
    public WebElement input_returnNote;

    @FindBy(xpath = "//button[normalize-space()='Close'] ")
    public WebElement button_close;
    @FindBy(xpath = "//button[contains(@class,'receive-items')] ")
    public WebElement button_receive;
    @FindBy(xpath = "//input[@id='inventory_transaction_return_return_date'] ")
    public WebElement input_dateSaleReturn;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table/tbody/tr/td[4]/label")
    public List<WebElement> list_saleReturnBatchNoList;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table/tbody/tr/td[3]/label")
    public List<WebElement> list_saleReturnBarcodeList;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table/tbody/tr/td[6]/span")
    public List<WebElement> list_saleReturnUPriceWithTaxList;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table/tbody/tr/td[12]/button")
    public List<WebElement> list_saleReturnDeleteActionList;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table/tbody/tr/td[9]/div")
    public List<WebElement> list_saleReturnReturnAmountList;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table/tbody/tr/td[7]")
    public List<WebElement> list_saleReturnTaxRateList;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table/tbody/tr/td[11]/input")
    public List<WebElement> list_saleReturnRemarkList;
    @FindBy(xpath = "//input[@id='inventory_transaction_return_total_cost']")
    public WebElement input_grossAmountInReturn;
    @FindBy(xpath = "//b[text()='Taxable Amount']/parent::div/following-sibling::div[2]/input[@id='total_taxable_amount']")
    public WebElement input_taxableAmountInTaxCage;
    @FindBy(xpath = "//b[text()='CGST2.5']/parent::div/following-sibling::div[2]/input[3]")
    public WebElement input_cgstInTaxCage;
    @FindBy(xpath = "//b[text()='SGST2.5']/parent::div/following-sibling::div[2]/input[3]")
    public WebElement input_sgstInTaxCage;
    @FindBy(xpath = "//input[@id='inventory_transaction_return_return_amount']")
    public WebElement input_netReturnAmountInReturn;
    @FindBy(xpath = "//input[@id='inventory_transaction_return_return_charges']")
    public WebElement input_returnCharges;
    @FindBy(xpath = "//input[@name='inventory_transaction_return[mop_breakups_attributes][0][return_amount]']")
    public WebElement input_returnRefundAmount;
    @FindBy(xpath = "//div[contains(text(),'Return Receipt Id')]/following-sibling::div[1]")
    public WebElement text_returnRefundIdAfterSave;
    @FindBy(xpath = "//input[@id='inventory_transaction_return_return_time'] ")
    public WebElement input_timeSaleReturn;
    @FindBy(xpath = "(//a[contains(@class,'btn btn-success')])[1]")
    public WebElement button_printA4SizeButton;
    @FindBy(xpath = "(//a[contains(@class,'btn btn-success')])[2]")
    public WebElement button_printA5SizeButton;
    @FindBy(xpath = "//b[text()='Taxable Amount']/parent::div/following-sibling::div[2]")
    public WebElement text_taxableAmountKeyAndValue;
    @FindBy(xpath = "//b[text()='CGST2.5']/parent::div/following-sibling::div[2]")
    public WebElement text_cgstAmountKeyAndValue;
    @FindBy(xpath = "//b[text()='SGST2.5']/parent::div/following-sibling::div[2]")
    public WebElement text_sgstAmountKeyAndValue;
    @FindBy(xpath = "//b[text()='Gross Return Amt.']/parent::div/following-sibling::div[2]")
    public WebElement text_grossAmountKeyAndValue;
    @FindBy(xpath = "//b[text()='Return charges']/parent::div/following-sibling::div[2]")
    public WebElement text_returnChargesAmountKeyAndValue;
    @FindBy(xpath = "//b[text()='Net Return Value']/parent::div/following-sibling::div[2]")
    public WebElement text_netReturnValueAmountKeyAndValue;
    @FindBy(xpath = "//div[@class='row refund-info mb20']//p[2]/a")
    public WebElement text_refundReceiptDetailsButton;
    @FindBy(xpath = "//h4[text()='Refund MOP Receipts']")
    public WebElement header_refundMopReceiptsViewTemplate;
    @FindBy(xpath = "//h4[text()='Refund MOP Receipts']/parent::div/following-sibling::div//thead//th")
    public List<WebElement> list_keysRefundMOPReceiptHeaderList;
    @FindBy(xpath = "//h4[text()='Refund MOP Receipts']/parent::div/following-sibling::div//tbody//td[1]")
    public List<WebElement> list_noValueRefundMOPReceiptList;
    @FindBy(xpath = "//h4[text()='Refund MOP Receipts']/parent::div/following-sibling::div//tbody//td[2]")
    public List<WebElement> list_modeOfPaymentValueRefundReceiptList;
    @FindBy(xpath = "//h4[text()='Refund MOP Receipts']/parent::div/following-sibling::div//tbody//td[3]")
    public List<WebElement> list_refundMopIDValueRefundReceiptHeaderList;
    @FindBy(xpath = "//h4[text()='Refund MOP Receipts']/parent::div/following-sibling::div//tbody//td[4]")
    public List<WebElement> list_amountValueRefundReceiptHeaderList;





}
