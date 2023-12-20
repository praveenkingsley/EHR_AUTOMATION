package pages.store.PharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_SON extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_SON(WebDriver driver) {
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

    @FindBy(xpath = "(//div[@class='btn-group']//a)/i")
    public WebElement button_addNewButton;

    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveChanges;

    @FindBy(xpath = "//label[@id='inventory_transaction_stock_opening_document_number-error']")
    public WebElement label_documentNumberRequired;

    @FindBy(xpath = "//label[@id='inventory_transaction_stock_opening_total_cost-error']")
    public WebElement label_totalCostRequired;

    @FindBy(xpath = "//label[@id='inventory_transaction_stock_opening_net_amount-error']")
    public WebElement label_netAmountRequired;

    @FindBy(xpath = "//input[@id='inventory_transaction_stock_opening_document_number']")
    public WebElement input_documentNumber;

    @FindBy(xpath = "//input[@id='variants_search']")
    public WebElement input_variantDescription;

    @FindBy(xpath = "//tbody[@class='items-variants-body']//tr//td[2]")
    public List<WebElement> list_medicationNameOnLeft;

    @FindBy(xpath = "//h4[contains(text(),'Add Lot')]")
    public WebElement header_addNewLot;

    @FindBy(xpath = "//select[@id='sub_store_id']")
    public WebElement select_subStore;

    @FindBy(xpath = "//input[@id='unit_cost_without_tax']")
    public WebElement input_unitCostWOTax;

    @FindBy(xpath = "//input[@id='list_price_pack']")
    public WebElement input_sellingPrice;
    @FindBy(xpath = "//input[@id='stock_unit']")
    public WebElement input_packageQuantity;

    @FindBy(xpath = "//button[text()='Cancel']")
    public WebElement button_closeTemplateWithoutSaving;

    @FindBy(xpath = "//input[@id='unit_cost']")
    public WebElement text_unitCostWithTax;

    @FindBy(xpath = "//input[@id='expiry']")
    public WebElement input_expiryDate;

    @FindBy(xpath = "//input[@id='total_cost']")
    public WebElement input_totalCost;

    @FindBy(xpath = "//input[@value='Save']")
    public WebElement button_saveLot;

    @FindBy(xpath = "//input[contains(@id,'stock_opening_transaction_date')]")
    public WebElement input_TransitionDate;

    @FindBy(xpath = "(//input[contains(@id,'stock_opening_transaction_time')])[1]")
    public WebElement input_OrderTime;

    @FindBy(xpath = "//div[@class='inventory-items']//tbody/tr")
    public List<WebElement> list_SONTransactions;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tbody/tr/td[1]")
    public WebElement text_variantDescription;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tbody/tr/td[5]")
    public WebElement text_Quantity;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tbody/tr/td[6]")
    public WebElement text_totalPrice;

    @FindBy(xpath = "//div/b[contains(text(),'Taxable Amount')]/parent::div//following-sibling::div[2]")
    public WebElement text_taxableAmount;
    @FindBy(xpath = "//a[text()='Approve']")
    public WebElement button_Approve;
    @FindBy(xpath = "//a[@class='btn btn-primary btn-xs']/i")
    public WebElement button_editSONButton;
    @FindBy(xpath = "//h4[text()='Edit Stock Opening Note']")
    public WebElement header_editStockOpeningNoteHeader;
    @FindBy(xpath = "//button[@class='btn btn-xs btn-danger cancel-stock-opening']/i")
    public WebElement button_cancelSONButton;
    @FindBy(xpath = "//input[@id='inventory_transaction_stock_opening_transaction_date']")
    public WebElement input_sonTransactionDate;
    @FindBy(xpath = "//input[@id='inventory_transaction_stock_opening_transaction_time_picker']")
    public WebElement input_sonTransactionTime;
    @FindBy(xpath = "//h4[contains(text(),'Add Opening Stock to Inventory')]")
    public WebElement header_addStockOpeningNoteHeader;

    @FindBy(xpath = "//div[@class='well item-info']/div/div")
    public List<WebElement> list_keysAndValuesInAddLot;

    @FindBy(xpath = "//form[@id='stock_openings_add_lot_form']//label")
    public List<WebElement> list_labelsInStockAddLot;
    @FindBy(xpath = "//select[@class='form-control-custom form-control variant-skew']")
    public WebElement select_selectVariantInAddLot;
    @FindBy(xpath = "//input[@id='batch_no']")
    public WebElement input_batchNumberInputField;
    @FindBy(xpath = "//input[@id='stock_package']")
    public WebElement input_packageStockField;
    @FindBy(xpath = "//input[@id='stock_subpackage']")
    public WebElement input_subPackageStockField;
    @FindBy(xpath = "//input[@id='stock_free_unit']")
    public WebElement input_freeUnitStockField;
    @FindBy(xpath = "//input[@id='stock']")
    public WebElement input_totalStockField;
    @FindBy(xpath = "//select[@id='list_price_pack_denomination']")
    public WebElement select_selectSellingPriceUnitTypeInAddLot;

    @FindBy(xpath = "//table[@class='stock-opening-transaction-log-item']//tr/td[1]/strong")
    public List<WebElement> list_itemStockNameListInStockInventoryTemplate;
    @FindBy(xpath = "//table[@class='stock-opening-transaction-log-item']//tr/td[2]/input[1]")
    public List<WebElement> list_totalQuantityListInStockInventoryTemplate;
    @FindBy(xpath = "//table[@class='stock-opening-transaction-log-item']//tr/td[3]/input[1]")
    public List<WebElement> list_batchNoListInStockInventoryTemplate;
    @FindBy(xpath = "//table[@class='stock-opening-transaction-log-item']//tr/td[4]/input[1]")
    public List<WebElement> list_expiryDateListInStockInventoryTemplate;
    @FindBy(xpath = "//table[@class='stock-opening-transaction-log-item']//tr/td[5]/input")
    public List<WebElement> list_taxableAmountListInStockInventoryTemplate;
    @FindBy(xpath = "//table[@class='stock-opening-transaction-log-item']//tr/td[6]/input[2]")
    public List<WebElement> list_taxRateListInStockInventoryTemplate;
    @FindBy(xpath = "//table[@class='stock-opening-transaction-log-item']//tr/td[7]")
    public List<WebElement> list_taxIncListInStockInventoryTemplate;
    @FindBy(xpath = "//table[@class='stock-opening-transaction-log-item']//tr/td[8]/input")
    public List<WebElement> list_taxAmountListInStockInventoryTemplate;
    @FindBy(xpath = "//table[@class='stock-opening-transaction-log-item']//tr/td[9]/input")
    public List<WebElement> list_unitCostListInStockInventoryTemplate;
    @FindBy(xpath = "//table[@class='stock-opening-transaction-log-item']//tr/td[10]/input")
    public List<WebElement> list_totalCostListInStockInventoryTemplate;
    @FindBy(xpath = "//table[@class='stock-opening-transaction-log-item']//tr/td[11]/button")
    public List<WebElement> list_deleteListInStockInventoryTemplate;

    @FindBy(xpath = "//input[@id='inventory_transaction_stock_opening_note']")
    public WebElement input_transactionNotes;
    @FindBy(xpath = "//b[text()='Total Amt Including Tax']")
    public WebElement text_totalAmtIncludingTaxLabel;
    @FindBy(xpath = "//b[text()='Discount']")
    public WebElement text_discountLabel;
    @FindBy(xpath = "//b[text()='Net Amount']")
    public WebElement text_netAmountLabel;
    @FindBy(xpath = "//b[text()='Amount Paid']")
    public WebElement text_amountPaidLabel;
    @FindBy(xpath = "//b[text()='Amount Remaining']")
    public WebElement text_amountRemainingLabel;
    @FindBy(xpath = "//input[@id='inventory_transaction_stock_opening_total_cost']")
    public WebElement input_totalAmountIncludingTax;
    @FindBy(xpath = "//input[@id='inventory_transaction_stock_opening_discount']")
    public WebElement input_discountBox;
    @FindBy(xpath = "//input[@id='inventory_transaction_stock_opening_net_amount']")
    public WebElement input_netAmount;
    @FindBy(xpath = "(//a[@class='btn btn-xs btn-success'])[1]")
    public WebElement button_printA4SizeButton;
    @FindBy(xpath = "(//a[@class='btn btn-xs btn-success'])[2]")
    public WebElement button_printA5SizeButton;
    @FindBy(xpath = "//div[@class='inventory-show-panel']//h3[2]")
    public WebElement header_viewSONNoteHeader;
    @FindBy(xpath = "//div[@class='inventory-show-panel']//div[@class='container-fluid']/div/div[1]")
    public WebElement text_storeNameKeyAndValue;
    @FindBy(xpath = "//div[@class='inventory-show-panel']//div[@class='container-fluid']/div/div[2]")
    public WebElement text_documentNumberKeyAndValue;
    @FindBy(xpath = "//div[@class='inventory-show-panel']//div[@class='container-fluid']/div/div[3]")
    public WebElement text_statusKeyAndValue;
    @FindBy(xpath = "//div[@class='inventory-show-panel']//div[@class='container-fluid']/div/div[4]")
    public WebElement text_createdByKeyAndValue;
    @FindBy(xpath = "//div[@class='inventory-show-panel']//div[@class='container-fluid']/div/div[5]")
    public WebElement text_createdAtKeyAndValue;
    @FindBy(xpath = "//div[@class='inventory-show-panel']//div[@class='container-fluid']/div/div[6]")
    public WebElement text_transactionIDKeyAndValue;
    @FindBy(xpath = "//div[@class='inventory-show-panel']//div[@class='container-fluid']/div/div[6]")
    public WebElement text_approvedByKeyAndValue;
    @FindBy(xpath = "//div[@class='inventory-show-panel']//div[@class='container-fluid']/div/div[7]")
    public WebElement text_transactionIDKeyAndValueAfterApprove;
    @FindBy(xpath = "//div[@class='inventory-show-panel']//div[@class='container-fluid']/div/div[8]")
    public WebElement text_cancelledByKeyAndValue;
    @FindBy(xpath = "//div[@class='inventory-show-panel']//div[@class='container-fluid']/div/div[9]")
    public WebElement text_transactionIDKeyAndValueAfterCancel;

    @FindBy(xpath = "//h4[contains(text(),'Transaction Details:')]")
    public WebElement text_transactionDetailsSectionText;
    @FindBy(xpath = "//b[text()='Total Amt Including Tax']/parent::div/following-sibling::div[2]")
    public WebElement text_totalAmtIncTaxLabelValue;
    @FindBy(xpath = "//b[text()='Discount']/parent::div/following-sibling::div[2]")
    public WebElement text_discountLabelValue;
    @FindBy(xpath = "//b[text()='Net Amount']/parent::div/following-sibling::div[2]")
    public WebElement text_netAmountLabelValue;
    @FindBy(xpath = "//b[text()='Amount Paid']/parent::div/following-sibling::div[2]")
    public WebElement text_amountPaidLabelValue;
    @FindBy(xpath = "//b[text()='Amount Remaining']/parent::div/following-sibling::div[2]")
    public WebElement text_amountRemainingLabelValue;
    @FindBy(xpath = "//label[text()='Reason for cancellation']")
    public WebElement label_reasonForCancellation;
    @FindBy(xpath = "//input[@value = 'Cancel']")
    public WebElement input_cancelButtonInCancelTemplate;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tbody/tr/td[2]")
    public WebElement text_batchNoText;





}
