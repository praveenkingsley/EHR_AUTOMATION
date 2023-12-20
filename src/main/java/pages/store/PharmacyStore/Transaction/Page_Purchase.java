package pages.store.PharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Purchase extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_Purchase(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//a[text()=' New']")
    public WebElement button_purchaseNew;
    @FindBy(xpath = "//span[@aria-labelledby='select2-vendor_id-container']")
    public WebElement dropdown_selectVendorInStore;

    @FindBy(xpath = "//ul[@id='select2-vendor_id-results']/li")
    public List<WebElement> list_selectVendorInStore;
    @FindBy(xpath = "//h4[text()='Add New Stock to Inventory']")
    public WebElement header_addNewStock;
    @FindBy(xpath = "//table[@class='table items-variants-table']//tr/td[2]/b")
    public List<WebElement> list_itemNameInPurchaseStore;
    @FindBy(xpath = "//h4[contains(text(),'Add Lot')]")
    public WebElement header_addNewLot;
    @FindBy(xpath = "//input[@id='variants_search']")
    public WebElement input_searchItem;
    @FindBy(xpath = "//select[contains(@id,'sub_store_id')]")
    public WebElement select_subStore;

    @FindBy(xpath = "//input[@id='plus_other_charges_amount']")
    public WebElement input_vendorOtherChargesToBePaid;
    @FindBy(xpath = "//input[@id='unit_cost_without_tax']")
    public WebElement input_unitCostWOTax;

    @FindBy(xpath = "//input[@id='list_price_pack']")
    public WebElement input_sellingPrice;
    @FindBy(xpath = "//input[@id='list_price']")
    public WebElement input_sellingPriceByUnit;

    @FindBy(xpath = "//input[contains(@class,'unit_item_list_price modalRequest_input_style')]")
    public List<WebElement> list_MRPPrice;
    @FindBy(xpath = "//td[contains(@class,'order-rate')]/span")
    public List<WebElement> list_rate;
    @FindBy(xpath = "//input[@id='stock_unit']")
    public WebElement input_packageQuantity;

    @FindBy(xpath = "//input[@value='Save']")
    public WebElement button_saveLot;
    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveAddNewLot;
    @FindBy(xpath = "//select[@id='inventory_transaction_purchase_bill_type']")
    public WebElement dropdown_selectBillType;
    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_bill_number']")
    public WebElement input_billNumber;
    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_challan_number']")
    public WebElement input_challanNumber;
    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_bill_date']")
    public WebElement input_billDate;
    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_challan_date']")
    public WebElement input_challanDate;
    @FindBy(xpath = "//table[@class='ui-datepicker-calendar']/tbody/tr/td[contains(@class,'ui-datepicker-today')]")
    public WebElement input_todayBillDate;

    @FindBy(xpath = "//input[contains(@id,'transaction_date')]")
    public WebElement input_purchaseTransactionDate;
    @FindBy(xpath = "//input[contains(@id,'transaction_time_picker')]")
    public WebElement input_purchaseTransactionTime;

    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//td[1]")
    public List<WebElement> list_dateTimeOfPurchase;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//tr")
    public List<WebElement> list_transactionPurchaseList;
    @FindBy(xpath = "//div[@class='btn-group']/a[text()='Approve']")
    public WebElement button_approveTransaction;

    @FindBy(xpath ="//input[@id='expiry']")
    public WebElement input_expiryDate;

    @FindBy(xpath ="//input[contains(@id,'expiry')][@type='text']")
    public WebElement input_expiryDateGRN;
    @FindBy(xpath ="//select[@class='ui-datepicker-year']")
    public WebElement select_expiryDateYear;

    @FindBy(xpath ="//td[@class=' ui-datepicker-days-cell-over  ui-datepicker-today']/a")
    public WebElement activeDateInExpiryDate;

    @FindBy(xpath ="//a[text()='1']")
    public WebElement select_expiryDateDay;

    @FindBy(xpath ="//a[text()='Previous Rate']")
    public WebElement button_previousRate;

    @FindBy(xpath ="//a[text()='Stock Availability']")
    public WebElement button_stockAvailability;

    @FindBy(xpath = "//button[text()='Cancel']")
    public WebElement button_closeTemplateWithoutSaving;
    @FindBy(xpath = "//button[@class='close']/span")
    public WebElement button_closeGrnWithoutSaving;
    @FindBy(xpath = "//input[@id='total_cost']")
    public WebElement input_totalCost;

    @FindBy(xpath = "//input[@id='unit_cost']")
    public WebElement input_unitCostWithTax;

    @FindBy(xpath = "//input[@id='discount_amount']")
    public WebElement input_discountAmount;
    @FindBy(xpath = "//input[@id='stock_free_unit']")
    public WebElement input_addFreeQuantity;
    @FindBy(xpath = "//input[@id='item_batch_info_Indent']")
    public WebElement input_batchNumber;
    @FindBy(xpath = "//label[contains(text(),'%') and contains(text(),'tax')]")
    public WebElement label_taxPercentage;
    @FindBy(xpath = "//label[contains(text(),'Dispensing Unit')]/parent::div/following-sibling::div/strong")
    public WebElement label_dispensingUnit;
    @FindBy(xpath = "//input[@id='unit_non_taxable_amount_show']")
    public WebElement input_sellingPriceWOTax;

    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_note']")
    public WebElement input_transactionNotes;

    @FindBy(xpath = "//select[contains(@id,'inventory_transaction_purchase_other_charges')]")
    public WebElement select_otherCharges;

    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_other_charges_attributes_0_amount_show']")
    public WebElement input_otherChargesAmount;

    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_total_cost']")
    public WebElement input_totalGrossAmountInLotInventory;

    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_discount']")
    public WebElement input_totalDiscount;

    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_tax_amount']")
    public WebElement input_totalGST;

    @FindBy(xpath = "//input[@id='total_other_charges_amount']")
    public WebElement input_totalOtherCharges;

    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_net_amount']")
    public WebElement input_totalNetAmount;

    @FindBy(xpath = "//a[@class='btn btn-primary btn-xs ']")
    public WebElement button_editPurchaseTransaction;
    @FindBy(xpath = "//a[@class='btn btn-primary btn-xs']")
    public WebElement button_editPOTransaction;
    @FindBy(xpath = "//button[contains(@class,'cancel-purchase')]")
    public WebElement button_cancelPurchaseTransaction;

    @FindBy(xpath = "//a[contains(@href,'barcode')]")
    public WebElement button_assignBarcodePurchaseTransaction;
    @FindBy(xpath = "//table[@class='purchase-transaction-log-item']//tr/td[1]/strong")
    public List<WebElement> list_itemStockNameListInStockInventoryTemplate;

    @FindBy(xpath = "//table[@class='purchase-transaction-log-item']//td[2]/input")
    public List<WebElement> list_unitCostWOTaxListInStockInventoryTemplate;

    @FindBy(xpath = "//table[@class='purchase-transaction-log-item']//td[3]/input[2]")
    public List<WebElement> list_taxRateListInStockInventoryTemplate;

    @FindBy(xpath = "//table[@class='purchase-transaction-log-item']//td[4]/input[1]")
    public List<WebElement> list_paidQuantityListInStockInventoryTemplate;

    @FindBy(xpath = "//table[@class='purchase-transaction-log-item']//td[6]//input[2]")
    public List<WebElement> list_discountListInStockInventoryTemplate;

    @FindBy(xpath = "//table[@class='purchase-transaction-log-item']//td[7]//input")
    public List<WebElement> list_netAmountListInStockInventoryTemplate;

    @FindBy(xpath = "//b[text()='GST5']/parent::div/following-sibling::div/input")
    public List<WebElement> list_perItemGstListInStockInventoryTemplate;

    @FindBy(xpath = "//a[text()='Approve']")
    public WebElement button_approvePurchaseTransaction;

    @FindBy(xpath = "//h4[text()='Approved']")
    public WebElement header_approveNotificationTemplate;

    @FindBy(xpath = "//div[@class='ui-pnotify-text']")
    public WebElement text_approveNotificationTemplateMessage;

    @FindBy(xpath = "//a[text()=' Complete Payment']")
    public WebElement button_completePaymentPurchaseTransaction;

    @FindBy(xpath = "//button[@class='btn btn-link btn-xs btn-inventory-refresh']/i")
    public WebElement button_refreshPurchaseTransaction;

    @FindBy(xpath = "//h5[text()='Review Order']")
    public WebElement header_completePaymentTemplate;

    @FindBy(xpath = "//input[@value='Complete Payment']")
    public WebElement button_completePaymentInCompletePaymentTemplate;

    @FindBy(xpath = "//div[@class='inventory-show-panel']//a[contains(text(),'Return')]")
    public WebElement button_returnPurchaseTransaction;

    @FindBy(xpath = "//h4[text()='Purchase Return']")
    public WebElement header_returnPurchaseTransactionTemplate;

    @FindBy(xpath = "//input[@id='inventory_transaction_vendor_return_note']")
    public WebElement input_returnNotesInReturnPurchaseTransactionTemplate;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//td[10]")
    public List<WebElement> list_discountListInTransactionDetailsTable;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item']/tbody/tr")
    public List<WebElement> list_itemStockRowListInReturnPurchaseTemplate;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item']/tbody/tr/td[5]")
    public List<WebElement> list_itemStockMRPListInReturnPurchaseTemplate;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item']/tbody/tr/td[7]")
    public List<WebElement> list_unitCostWOTaxListInReturnPurchaseTemplate;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item']/tbody/tr/td[8]/input")
    public List<WebElement> list_netUnitCostWOTaxListInReturnPurchaseTemplate;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item']/tbody/tr/td[9]")
    public List<WebElement> list_marginListInReturnPurchaseTemplate;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item']/tbody/tr/td[10]")
    public List<WebElement> list_paidQuantityListInReturnPurchaseTemplate;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item']/tbody/tr/td[11]")
    public List<WebElement> list_freeQuantityListInReturnPurchaseTemplate;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item']/tbody/tr/td[12]")
    public List<WebElement> list_returnableQuantityListInReturnPurchaseTemplate;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item']/tbody/tr/td[13]")
    public List<WebElement> list_availableQuantityListInReturnPurchaseTemplate;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item']/tbody/tr/td[14]/input")
    public List<WebElement> list_returnQuantityListInReturnPurchaseTemplate;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item']/tbody/tr/td[15]")
    public List<WebElement> list_taxRateListInReturnPurchaseTemplate;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item']/tbody/tr/td[16]/input[1]")
    public List<WebElement> list_netAmountListInReturnPurchaseTemplate;

    @FindBy(xpath = "//input[@id='inventory_transaction_vendor_return_total_cost']")
    public WebElement input_amountBeforeTax;

    @FindBy(xpath = "//b[text()='GST5']/parent::div/following-sibling::div/input")
    public List<WebElement> list_perItemVATListInReturnPurchaseTemplate;

    @FindBy(xpath = "//input[@id = 'inventory_transaction_vendor_return_return_amount']")
    public WebElement input_netReturnWithTaxAmount;

    @FindBy(xpath = "//h4[contains(text(),'Return Details')]")
    public WebElement header_ReturnDetailsSection;

    @FindBy(xpath = "//span[text()='Stock:']/parent::div/following-sibling::div/strong")
    public WebElement text_stockAvailableInItemDetailsInViewTemplate;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tr[1]//td[4]")
    public WebElement table_transactionDetailsFlowInViewTemplate;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tr[1]//td[5]")
    public WebElement table_transactionDetailsStockBeforeInViewTemplate;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tr[1]//td[6]")
    public WebElement table_transactionDetailsStockAfterInViewTemplate;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tr[1]//td[3]")
    public WebElement table_transactionDetailsDateAndTimeInViewTemplate;

    @FindBy(xpath = "//table[@class='table inventory_table_center']/thead/tr/td")
    public List<WebElement> list_purchaseTransactionHeaderList;

    @FindBy(xpath = "//h4[text()='Goods Received Note']")
    public WebElement header_GRN;

    @FindBy(xpath = "//table[@class='table transaction-lots-table']/tbody/tr[contains(@id,'inventory_transaction_purchase_items_attributes')]")
    public List<WebElement> table_itemsRowWise;

    @FindBy(xpath = "//label[@id='inventory_transaction_purchase_bill_type-error']")
    public WebElement label_billTypeRequired;

    @FindBy(xpath = "//label[@id='inventory_transaction_purchase_items_attributes_0_list_price-error']")
    public WebElement label_MRPRequired;

    @FindBy(xpath = "//label[@id='inventory_transaction_purchase_items_attributes_0_batch_no-error']")
    public WebElement label_batchNumberRequired;

    @FindBy(xpath = "//label[@id='plus_other_charges_amount-error']")
    public WebElement label_otherChargesRequired;

    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_SaveChanges;

    @FindBy(xpath = "//button/i[@class='fa fa-plus']")
    public List<WebElement> button_addChildRow;

    @FindBy(xpath = "//button[@id='inventory_transaction_purchase_items_attributes_1']/i[@class='fa fa-trash-alt']")
    public WebElement button_deleteChildRow;

    @FindBy(xpath = "//input[contains(@class,'paid_stock')]")
    public List<WebElement> input_paidQty;

    @FindBy(xpath = "//input[@placeholder='Batch']")
    public List<WebElement> list_batchNumber;

    @FindBy(xpath = "//input[@placeholder='Expiry']")
    public List<WebElement> list_expiryDate;

    @FindBy(xpath = "//label[@id='inventory_transaction_purchase_items_attributes_1_batch_no-error']")
    public WebElement label_duplicateBatch;

    @FindBy(xpath = "//input[@id='plus_other_charges_amount']")
    public WebElement input_OtherCharges;

    @FindBy(xpath = "//input[@placeholder='Transaction Note']")
    public WebElement input_TransactionNote;

    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody/tr")
    public List<WebElement> list_GRNTransaction;

    @FindBy(xpath = "//div[@class='row check_font']/table/tbody/tr/td[3]")
    public WebElement table_itemDescription;

    @FindBy(xpath = "//div[@class='row check_font']/table/tbody/tr/td[7]")
    public List<WebElement> list_PaidQty;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']/tr/td[10]//div/input[contains(@id,'inventory_transaction_purchase_items_attributes_')][1]")
    public List<WebElement> list_inputPaidQty;

    @FindBy(xpath = "//div[@class='row check_font']/table/tbody/tr/td[10]")
    public List<WebElement> list_Discount;

    @FindBy(xpath = "//div[@class='row check_font']/table/tbody/tr/td[12]")
    public List<WebElement> list_NetAmount;

    @FindBy(xpath = "//div[@class='row check_font']/table/tbody/tr/td[8]")
    public List<WebElement> list_FreeQty;

    @FindBy(xpath = "//div/b[contains(text(),'Gross Amount')]/parent::div/following-sibling::div[2]")
    public WebElement text_totalGrossAmt;

    @FindBy(xpath = "//div/b[contains(text(),'Total Discount')]/parent::div/following-sibling::div[2]")
    public WebElement text_TotalDiscount;

    @FindBy(xpath = "//div/b[contains(text(),'GST')]/parent::div/following-sibling::div[2]")
    public WebElement text_TotalTaxAmt;

    @FindBy(xpath = "//div/b[contains(text(),'Other Charges')]/parent::div/following-sibling::div[2]")
    public WebElement text_TotalOtherCharges;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']/tbody/tr")
    public List<WebElement> list_RowsOnUITable;

    @FindBy(xpath = "//div/b[contains(text(),'Net Amount')]/parent::div/following-sibling::div[2]")
    public WebElement text_TotalNetAmount;

    @FindBy(xpath = "//button[text()='Yes']")
    public WebElement button_Yes;
    @FindBy(xpath = "//b[contains(text(),'Transaction ID')]/parent::div/following-sibling::div/span")
    public WebElement text_transactionID;
    @FindBy(xpath = "//b[text()='GRN Approved By']/parent::div/following-sibling::div/span[1]")
    public WebElement text_grnApprovedByUser;
    @FindBy(xpath = "//b[text()='GRN Approved By']/parent::div/following-sibling::div/span[3]")
    public WebElement text_grnApprovedAt;
    @FindBy(xpath = "//b[contains(text(),'GRN Created By')]/parent::div/following-sibling::div/span[3]")
    public WebElement text_grnCreatedAt;
    @FindBy(xpath = "//b[text()='Cancelled By']/parent::div/following-sibling::div")
    public WebElement text_grnCancelledAt;

    @FindBy(xpath = "//input[@id='search_vendor_address']")
    public WebElement input_Vendor_search;
    @FindBy(xpath = "//li[@class='ui-menu-item']/a")
    public List<WebElement> list_select_vendor;
    @FindBy(xpath = "//button[@id='first_delete_button']")
    public WebElement button_deleteOtherCharges;

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
    @FindBy(xpath="//div[contains(@class,'filemanager')]//button[contains(@class,'dropdown-toggle')]")
    public WebElement button_dropdownDeleteVendorInvoicePurchaseBill;
    @FindBy(xpath = "//a[@data-action='destroy']")
    public WebElement button_deleteFilesVendorInvoicePurchaseBill;
    @FindBy(xpath = "//small[contains(text(),'Added')]")
    public WebElement button_uploadedImageVendorInvoicePurchaseBill;
    @FindBy(xpath = "//a[@id='time_period']")
    public WebElement button_todayFilterButton;
    @FindBy(xpath = "//a[@id='inventory_vendor_name']/parent::div/button")
    public WebElement button_vendorDropdownButtonList;
    @FindBy(xpath = "//input[@id='inventory_search']")
    public WebElement input_vendorSearchBox;
    @FindBy(xpath = "//button[@class='btn btn-info navbar-btn navbar-btn-hover']")
    public WebElement button_reportButtonInPurchaseGRN;

    @FindBy(xpath = "//div[@class='inventory-show-panel']//h3")
    public WebElement header_viewGrnTransactionNotesHeader;

    @FindBy(xpath = "//b[text()='Vendor']/parent::div/following-sibling::div/span")
    public WebElement text_viewGrnVendorName;

    @FindBy(xpath = "//b[text()='Bill Number']/parent::div/following-sibling::div/span")
    public WebElement text_viewGrnBillNumber;
    @FindBy(xpath = "//b[text()='Status']/parent::div/following-sibling::div/span")
    public WebElement text_viewGrnStatus;
    @FindBy(xpath = "//b[text()='GST']/parent::div/following-sibling::div/span")
    public WebElement text_viewGrnGSTNumber;
    @FindBy(xpath = "//b[text()='GRN Created By']/parent::div/following-sibling::div")
    public WebElement text_viewGrnCreatedBy;
    @FindBy(xpath = "(//a[@class='btn btn-success btn-xs'])[1]")
    public WebElement button_printA4SizeButton;
    @FindBy(xpath = "(//a[@class='btn btn-success btn-xs'])[2]")
    public WebElement button_printA5SizeButton;

    @FindBy(xpath = "//h4[@class='purchase-transactions-title']")
    public WebElement header_transactionDetailsTitle;
    @FindBy(xpath = "//table[@class='table table-striped table-bordered']/thead//th")
    public List<WebElement> list_keysInTransactionDetailsTable;
    @FindBy(xpath = "//table[@class='table table-striped table-bordered']/tbody/tr/td")
    public List<WebElement> list_valuesInTransactionDetailsTable;

    @FindBy(xpath = "//label[text()='HSN No:']/parent::div/following-sibling::div/strong")
    public WebElement text_hsnCodeValue;

    @FindBy(xpath = "//button[text()='Yes']")
    public List<WebElement> button_yesButtonList;
    @FindBy(xpath = "//h5[text()='Are you sure?']")
    public List<WebElement> header_areYouSureHeaderList;
    @FindBy(xpath = "//p[text()='Are you sure?']")
    public List<WebElement> text_areYouSureTextList;
    @FindBy(xpath = "//select[@name = 'inventory_transaction_vendor_return[vendor_location_id]']")
    public WebElement select_vendorList;

    @FindBy(xpath = "//button[text()='Confirm']")
    public WebElement button_confirmButton;
    @FindBy(xpath = "//input[@id='cancelled_reason']")
    public WebElement input_cancelReasonInput;
    @FindBy(xpath = "//input[@value='Cancel GRN']")
    public WebElement input_cancelGRNButton;
    @FindBy(xpath = "//button[text()='Done']")
    public WebElement button_doneButtonInExpiry;

    @FindBy(xpath = "//table[@class='table transaction-lots-table']/tbody/tr[1]/td[10]//input")
    public WebElement input_poPaidQty;
    @FindBy(xpath = "//b[contains(text(),'Pending to be paid to Vendor:')]")
    public WebElement label_pendingToBePaid;

    @FindBy(xpath = "//a[@id='show_vendor_purchase_rate'][contains(@href,'show')]")
    public WebElement link_showPODetails;
    @FindBy(xpath = "//th[text()='Description']/ancestor::div[contains(@class,'purchase-order')]//th")
    public List<WebElement> list_poLinkTableHeaderPurchaseOrder;
    @FindBy(xpath = "//th[text()='Description']/ancestor::div[contains(@class,'purchase-order')]//tbody/tr")
    public List<WebElement> list_poLinkTableRowPurchaseOrder;
    @FindBy(xpath = "//div[@class='purchase-order row']//b[contains(text(),'Gross Amount')]/parent::div/following-sibling::div[2]")
    public WebElement text_poLinkTotalGrossAmt;

    @FindBy(xpath = "//div[@class='purchase-order row']//b[contains(text(),'Total Discount')]/parent::div/following-sibling::div[2]")
    public WebElement text_poLinkTotalDiscount;

    @FindBy(xpath = "//div[@class='purchase-order row']//b[contains(text(),'Other Charges')]/parent::div/following-sibling::div[2]")
    public WebElement text_poLinkTotalOtherCharges;

    @FindBy(xpath = "//div[@class='purchase-order row']//b[contains(text(),'Net Amount')]/parent::div/following-sibling::div[2]")
    public WebElement text_poLinkTotalNetAmount;
    @FindBy(xpath = "//div[@class='purchase-order row']//b[contains(text(),'GST5')]/parent::div/following-sibling::div[2]")
    public WebElement text_poLinkTotalGST5TaxAmt;
    @FindBy(xpath = "//div[@class='purchase-order row']//b[contains(text(),'GST28')]/parent::div/following-sibling::div[2]")
    public WebElement text_poLinkTotalGST28TaxAmt;
    @FindBy(xpath = "//button[text()='Close']")
    public WebElement button_closePOLink;
    @FindBy(xpath = "//input[contains(@name,'vendor_location_address')]")
    public WebElement input_vendorPOGrn;
    @FindBy(xpath = "//th[text()='Description']/ancestor::div[contains(@class,'transaction-lots-list')]//th")
    public List<WebElement> list_createGRNTableHeaderPurchaseOrder;
    @FindBy(xpath = "//tr[contains(@id,'inventory_transaction_purchase_items_attributes')]")
    public List<WebElement> list_createGRNTableRowPurchaseOrder;
    @FindBy(xpath = "//div[contains(@class,'paid_error')]")
    public WebElement label_paidQtyError;
    @FindBy(xpath = "//div[contains(@class,'error-msg free_error')]")
    public WebElement label_freeQtyError;
    @FindBy(xpath = "//div[contains(@class,'purchase-invoice-information')]//b[contains(text(),'Gross Amount')]/parent::div/following-sibling::div[2]//input")
    public WebElement text_totalGrossGRNPo;
    @FindBy(xpath = "//div[contains(@class,'purchase-invoice-information')]//b[contains(text(),'Total Discount')]/parent::div/following-sibling::div[2]//input")
    public WebElement text_totalDiscountGRNPo;
    @FindBy(xpath = "//div[contains(@class,'purchase-invoice-information')]//b[contains(text(),'GST5')]/parent::div/following-sibling::div[2]//input")
    public WebElement text_totalGST5GRNPo;
    @FindBy(xpath = "//div[contains(@class,'purchase-invoice-information')]//b[contains(text(),'GST28')]/parent::div/following-sibling::div[2]//input")
    public WebElement text_totalGST28GRNPo;
    @FindBy(xpath = "//div[contains(@class,'purchase-invoice-information')]//b[contains(text(),'Other Charges')]/parent::div/following-sibling::div[2]//input")
    public WebElement text_totalOtherChargesGRNPo;
    @FindBy(xpath = "//div[contains(@class,'purchase-invoice-information')]//b[contains(text(),'Net Amount')]/parent::div/following-sibling::div[2]//input")
    public WebElement text_totalNetAmountGRNPo;
    @FindBy(xpath = "//span[@class='pending_other_charges']")
    public WebElement text_pendingOtherCharges;
    @FindBy(xpath = "//div/b[contains(text(),'Amount Paid')]/parent::div/following-sibling::div[2]")
    public WebElement text_grnRhsAmountPaid;
    @FindBy(xpath = "//div/b[contains(text(),'Amount Remaining')]/parent::div/following-sibling::div[2]")
    public WebElement text_grnRhsAmountRemaining;

    @FindBy(xpath = "//tbody[@class='inventory-table-body purchase-transaction-list']/tr/td")
    public List<WebElement> list_purchaseTransactionDateandTimeList;

    @FindBy(xpath = "//div[@class='modal false fade in']/div/div/div[3]/button[text()='Yes']")
    public WebElement button_approveGRN;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FindBy(xpath = "//a[@id='time_period']")
    public WebElement select_dateFilter;

    @FindBy(xpath = "(//button[@class='btn btn-primary btn-sm dropdown-toggle '])[2]")
    public WebElement button_dateFilterExpandOption;

    @FindBy(xpath = "(//a[@class='mis-date-filter'])[4]")
    public List<WebElement> list_purchaseGRNDateFilter;

    @FindBy(xpath = "(//button[@class='btn btn-primary btn-sm inventory_search_panel_dropdown'])")
    public WebElement button_searchFilterOptionGRNno;

    @FindBy(xpath = "(//input[@class='form-control master-search-box'])[2]")
    public WebElement input_searchCriteriaforPurchaseGRN;

    @FindBy(xpath = "//tbody[@class='inventory-table-body purchase-transaction-list']/tr[1]")
    public List<WebElement> list_purchaseGRNDateFilterDataDetails;

    @FindBy(xpath = "//span[text()='RSO-GRN-201023-101599']")
    public WebElement text_searchPurchaseGRN;

    @FindBy(xpath = "//button[@class='btn btn-warning btn-sm dropdown-toggle ']")
    public WebElement button_vendorExpandbutton;

    @FindBy(xpath = "//a[text()='New vendor kk']")
    public List<WebElement> list_purchaseGRNvendorList;

    @FindBy(xpath = "//button[@class='btn btn-primary btn-sm clear-date-filter']")
    public WebElement button_dateFilterCrossoption;

    @FindBy(xpath = " //button[@class='btn btn-warning btn-sm clear-vendor-filter']")
    public WebElement button_vendorCrossoption;
    @FindBy(xpath = "//select[@id='list_price_pack_denomination']")
    public WebElement select_unitPriceType;








}
