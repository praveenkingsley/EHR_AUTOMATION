package pages.store.PharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_PurchaseReturn extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_PurchaseReturn(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = " //button[text()='Yes']")
    public WebElement button_confirmYesTemplate;

    @FindBy(xpath = "//input[@id='lots_search']")
    public WebElement input_lotSearchBoxInPurchaseReturn;

    @FindBy(xpath = "//tbody[@class='items-lots-body']/tr[@class='inventory-list-row']/td[1]")
    public List<WebElement> list_purchaseItemDescriptionList;

    @FindBy(xpath = "//tbody[@class='items-lots-body']/tr[@class='inventory-list-row']/td[3]")
    public List<WebElement> list_purchaseItemStockList;
    @FindBy(xpath = "//tbody[@class='items-lots-body']/tr[@class='inventory-list-row']/td[4]")
    public List<WebElement> list_purchaseItemPriceList;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']//td[3]/strong")
    public WebElement  text_purchaseUnitCostWOTaxList;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']//td[4]/input")
    public WebElement text_purchaseNetUnitCostWOTaxList;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']//td[5]")
    public WebElement text_purchaseMarginList;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']//td[6]")
    public WebElement text_purchaseGrnQtyList;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']//td[7]")
    public WebElement text_purchaseGrnFreeQtyList;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']//td[8]")
    public WebElement text_purchaseReturnableQtyList;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']//td[9]")
    public WebElement text_purchaseAvailableQtyList;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']//td[10]/input")
    public WebElement text_purchaseReturnQtyList;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']//td[11]/strong")
    public WebElement text_purchaseTaxRateList;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']//td[12]/input[1]")
    public WebElement text_purchaseNetAmountList;

    @FindBy(xpath = "//input[@id='inventory_transaction_vendor_return_note']")
    public WebElement input_returnNotes;

    @FindBy(xpath = "(//input[@id='inventory_transaction_vendor_return_transaction_date'])[1]")
    public WebElement input_purchaseTransactionDate;

    @FindBy(xpath = "//input[@placeholder='Time of transition']")
    public WebElement input_purchaseTransactionTime;
    @FindBy(xpath = "//h4[text()='Pharmacy  Return Invoice']")
    public WebElement header_returnInvoiceHeader;
    @FindBy(xpath = "//b[contains(text(),'Vendor:')]")
    public WebElement text_vendorName;
    @FindBy(xpath = "//b[contains(text(),'Mobile:')]")
    public WebElement text_mobileNumber;
    @FindBy(xpath = "//b[contains(text(),'Total Cost:')]")
    public WebElement text_totalCostInView;
    @FindBy(xpath = "//b[contains(text(),'Transaction ID:')]")
    public WebElement text_transactionId;
    @FindBy(xpath = "//b[contains(text(),'Created')]")
    public WebElement text_createdBy;
    @FindBy(xpath = "//b[contains(text(),'Date')]")
    public WebElement text_DateAndTime;
    @FindBy(xpath = "//b[contains(text(),'GST')]")
    public WebElement text_gstNumber;
    @FindBy(xpath = "//b[contains(text(),'Note')]")
    public WebElement text_returnNoteInView;

    @FindBy(xpath = "//table[@class='table table table-striped table-bordered']//th")
    public List<WebElement> list_itemDetailsHeaderList;
    @FindBy(xpath = "//table[@class='table table table-striped table-bordered']//td")
    public List<WebElement> list_itemDetailsValueList;
    @FindBy(xpath = "//b[contains(text(),'Amount Before Tax')]/parent::div/following-sibling::div[2]")
    public WebElement text_amountBeforeTax;
    @FindBy(xpath = "//b[contains(text(),'GST5')]/parent::div/following-sibling::div[2]")
    public WebElement text_gstValueInItemDetails;
    @FindBy(xpath = "//b[contains(text(),'Net Return With Tax')]/parent::div/following-sibling::div[2]")
    public WebElement text_netReturnWithTaxInView;
    @FindBy(xpath = "//td[text()='Purchase Return Transaction']/parent::tr/following-sibling::tr[3]/td[4]")
    public WebElement text_transactionIdInSequence;

    @FindBy(xpath = "//b[contains(text(),'GRN No:')]")
    public WebElement text_grnId;


}
