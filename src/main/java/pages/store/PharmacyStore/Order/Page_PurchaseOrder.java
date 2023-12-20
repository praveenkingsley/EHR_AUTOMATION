package pages.store.PharmacyStore.Order;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_PurchaseOrder extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_PurchaseOrder(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//a[text()=' New']")
    public WebElement button_newOrder;
    @FindBy(xpath = "//input[@id='inventory_order_purchase_order_date']")
    public WebElement input_purchaseOrderDate;
    @FindBy(xpath = "//input[@id='inventory_order_purchase_order_time_picker']")
    public WebElement input_purchaseOrderTime;
    @FindBy(xpath = "//label[@id='inventory_order_purchase_vendor_id-error']")
    public WebElement label_vendorError;
    @FindBy(xpath = "//label[@id='inventory_order_purchase_bill_to_store-error']")
    public WebElement label_billToError;
    @FindBy(xpath = "//label[@id='inventory_order_purchase_ship_to_store-error']")
    public WebElement label_shipToError;
    // @FindBy(xpath = "//span[contains(@id,'inventory_order_purchase_vendor_location_id')]")
    @FindBy(xpath = "//select[@id='inventory_order_purchase_vendor_location_id']")
    public WebElement dropdown_vendor;
    @FindBy(xpath = "//input[@id='inventory_order_purchase_vendor_address']")
    public WebElement input_Vendor_search;
    @FindBy(xpath = "//ul[contains(@id,'inventory_order_purchase_vendor_location_id')]/li")
    public List<WebElement> list_vendorNames;
    @FindBy(xpath = "//select[@id='inventory_order_purchase_po_type']")
    public WebElement dropdown_poType;
    @FindBy(xpath = "//ul[contains(@id,'inventory_order_purchase_po_type')]/li")
    public List<WebElement> list_poType;

    @FindBy(xpath = "//span[@id='select2-inventory_order_purchase_ship_to_store-container']")
    public WebElement dropdown_shipTo;
    @FindBy(xpath = "//ul[@id='select2-inventory_order_purchase_ship_to_store-results']/li")
    public List<WebElement> list_shipToAddress;
    @FindBy(xpath = "//span[@id='select2-inventory_order_purchase_bill_to_store-container']")
    public WebElement dropdown_billTo;
    @FindBy(xpath = "//ul[@id='select2-inventory_order_purchase_bill_to_store-results']/li")
    public List<WebElement> list_billToAddress;
    @FindBy(xpath = "//select[@id='inventory_order_purchase_store_id']")
    public WebElement dropdown_store;
    @FindBy(xpath = "//ul[@id='select2-inventory_order_purchase_store_id-results']/li")
    public List<WebElement> list_store;
    @FindBy(xpath = "//input[@id='inventory_order_purchase_vendor_address']")
    public WebElement input_vendorSearchPo;
    @FindBy(xpath = "//li[@class='ui-menu-item']/a")
    public List<WebElement> list_select_vendor;
    @FindBy(xpath = "//select[contains(@id,'other_charge_id')]")
    public WebElement dropdown_otherCharges;
    @FindBy(xpath = "//input[@id='variants_search']")
    public WebElement input_searchMedicineNamePO;
    @FindBy(xpath = "//tbody[@class='items-variants-body']//td[2]/b")
    public List<WebElement> list_namesOfMedicinesOnLeftInSearchResultPO;
    @FindBy(xpath = "//h4[contains(text(),'Add Lot')]")
    public WebElement header_addNewLot;
    @FindBy(xpath = "//input[@id='unit_cost']")
    public WebElement input_unitCost;
    @FindBy(xpath = "//input[@id='unit_cost_without_tax']")
    public WebElement input_unitCostWOTax;
    @FindBy(xpath = "//input[@id='stock_unit']")
    public WebElement input_packageQuantity;
    @FindBy(xpath = "//input[@id='discount_amount']")
    public WebElement input_discountAmount;
    @FindBy(xpath = "//input[@id='stock_free_unit']")
    public WebElement input_freeUnitQuantity;
    @FindBy(xpath = "//div[contains(@class,'text-left')]/label[@class='label-info']")
    public WebElement input_taxPercentage;
    @FindBy(xpath = "//input[@id='total_cost']")
    public WebElement text_totalCost;
    @FindBy(xpath = "//input[@value='Save']")
    public WebElement button_saveLot;
    @FindBy(xpath = "//select[contains(@class,'other_charge_id')]")
    public WebElement dropdown_otherCharge;
    @FindBy(xpath = "//button[contains(@class,'other-charge-plus')]")
    public WebElement button_otherChargePlus;
    @FindBy(xpath = "//button[contains(@class,'other-charge-minus')]")
    public WebElement button_otherChargeMinus;
    @FindBy(xpath = "//input[contains(@class,'other_charge_amount_show')]")
    public WebElement input_otherChargeAmount;
    @FindBy(xpath = "//div[contains(@class,'purchase-invoice-information')]/div//div[contains(@class,'col-sm-6')]")
    public List<WebElement> list_invoiceInfo;
    @FindBy(xpath = "//input[@id='inventory_order_purchase_total_cost']")
    public WebElement text_grossAmountOnUIPO;
    @FindBy(xpath = "//input[@id='inventory_order_purchase_discount']")
    public WebElement text_discountOnUI;
    @FindBy(xpath = "//input[@id='inventory_order_purchase_net_amount']")
    public WebElement text_netAmountOnUI;
    @FindBy(xpath = "//input[@id='total_other_charges_amount']")
    public WebElement text_otherChargesOnUI;
    @FindBy(xpath = "//b[text()='GST5']/parent::div/following-sibling::div/input")
    public WebElement text_gst5AmountOnUI;
    @FindBy(xpath = "//b[text()='GST28']/parent::div/following-sibling::div/input")
    public WebElement text_gst28AmountOnUI;
    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveOrder;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//td[1]")
    public List<WebElement> list_dateTimeOfPO;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//td[6]")
    public List<WebElement> list_statusOfPO;
    @FindBy(xpath = "/html/body/div[2]/div/div[2]/div[1]/div[1]/nav/div/div[3]/div/button")
    public WebElement button_downArrowForToday;

    @FindBy(xpath = "//div[@class='inventory-show-panel']/div//a[contains(@class,'info')]")
    public WebElement button_approveOrder;
    @FindBy(xpath = "//a[@class='btn btn-danger btn-xs closeReq']")
    public WebElement button_cancelOrder;
    @FindBy(xpath = "//div[@class='btn-group']/a[contains(@href,'edit')]")
    public WebElement button_editOrder;
    @FindBy(xpath = "//div[@class='inventory-show-panel']/div/div[contains(@class,'row')]")
    public List<WebElement> list_PODetails;
    @FindBy(xpath = "//a[contains(@class,'close-purchase-order')]")
    public WebElement button_closeOrder;
    @FindBy(xpath = "//input[@id='cancelled_reason']")
    public WebElement input_cancelReason;
    @FindBy(xpath = "//input[@value='Cancel Purchase Order']")
    public WebElement button_confirmCancelOrder;
    @FindBy(xpath = "//input[@name='closed_reason']")
    public WebElement input_closeReasonPo;
    @FindBy(xpath = "//input[@value='Close Purchase Order']")
    public WebElement button_confirmCloseOrder;
    @FindBy(xpath = "//button[@class='btn commit btn-danger']")
    public WebElement button_confirmCancelApproveOrder;
    @FindBy(xpath = "//div[@class='inventory-show-panel']/div//a[contains(@class,'primary')]")
    public WebElement button_newTransactionPO;
    @FindBy(xpath = "//select[@id='inventory_transaction_purchase_items_attributes_0_sub_store_id']")
    public WebElement dropdown_subStore;
    @FindBy(xpath = "//select[@id='inventory_transaction_purchase_items_attributes_1_sub_store_id']")
    public WebElement dropdown_subStore2;
    @FindBy(xpath = "//div[contains(@class,'items-variants')]")
    public WebElement table_orderDetails;
    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_transaction_date']")
    public WebElement input_transactionOrderDate;
    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_transaction_time_picker']")
    public WebElement input_transactionOrderTime;
    @FindBy(xpath = "//select[@id='inventory_transaction_purchase_bill_type']")
    public WebElement dropdown_selectBillType;
    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_bill_number']")
    public WebElement input_billNumber;
    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_bill_date']")
    public WebElement input_billDate;
    @FindBy(xpath = "//table[@class='ui-datepicker-calendar']/tbody/tr/td[contains(@class,'ui-datepicker-today')]")
    public WebElement input_todayBillDate;
    @FindBy(xpath = "//table[@class='table transaction-lots-table']//tr/th")
    public List<WebElement> list_addNewStockHeaders;

    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_items_attributes_0_list_price']")
    public WebElement input_sellingPrice;
    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_items_attributes_0_batch_no']")
    public WebElement input_batchNo;
    @FindBy(xpath = "//input[contains(@class,'exp_datepicker')]")
    public WebElement input_expiryDate;
    @FindBy(xpath = "//button[text()='Done']")
    public WebElement button_expiryDateDone;
    @FindBy(xpath = "//span[contains(@class,'purchase_items_attributes_0_pending_stock')]")
    public WebElement text_pendingQuantity;
    @FindBy(xpath = "//input[contains(@class,'paid-stock check_order_quantity')]")
    public WebElement text_paidQuantity;
    @FindBy(xpath = "//input[contains(@class,'stock-free-unit')]")
    public WebElement text_freeQuantity;
    @FindBy(xpath = "//label[contains(@id,'stock_free_unit-error')]")
    public WebElement label_freeQtyError;
    @FindBy(xpath = "//label[contains(@id,'paid_stock-error')]")
    public WebElement label_paidQtyError;
    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_total_cost']")
    public WebElement text_grossAmountOnUiTP;
    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_net_amount']")
    public WebElement text_netAmountOnUiTP;
    @FindBy(xpath = "//div[@class='row check_font']/table/tbody/tr/td[3]")
    public WebElement table_itemDescription;
    @FindBy(xpath = "//div[contains(@class,'other-charges')]//button[contains(@class,'remove-item')]")
    public WebElement button_cancelOtherCharges;

    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//tr")
    public List<WebElement> list_purchaseOrdertransactions;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']/tbody/tr[1]/td[3]")
    public WebElement text_itemOnUI;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']/tbody/tr[1]/td[4]")
    public WebElement text_PaidQty;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']/tbody/tr[1]/td[5]")
    public WebElement text_FreeQty;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']/tbody/tr[1]/td[5]")
    public WebElement text_Discount;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']/tbody/tr[1]/td[7]")
    public WebElement text_itemRate;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']/tbody/tr[1]/td[9]")
    public WebElement text_NetAmountOnTable;

    @FindBy(xpath = "//div/b[contains(text(),'Gross Amount')]/parent::div/following-sibling::div[2]")
    public WebElement text_totalGrossAmt;

    @FindBy(xpath = "//div/b[contains(text(),'Total Discount')]/parent::div/following-sibling::div[2]")
    public WebElement text_TotalDiscount;

    @FindBy(xpath = "//div/b[contains(text(),'GST')]/parent::div/following-sibling::div[2]")
    public WebElement text_TotalTaxAmt;

    @FindBy(xpath = "//div/b[contains(text(),'Other Charges')]/parent::div/following-sibling::div[2]")
    public WebElement text_TotalOtherCharges;

    @FindBy(xpath = "//div/b[contains(text(),'Net Amount')]/parent::div/following-sibling::div[2]")
    public WebElement text_TotalNetAmount;

    @FindBy(xpath = "//a[text()='Approve']")
    public WebElement button_ApproveOrder;

    @FindBy(xpath = "//button[text()='Yes']")
    public WebElement button_Yes;

    @FindBy(xpath = "//a[text()=' New Transaction']")
    public WebElement button_NewTransaction;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/thead/tr/td")
    public List<WebElement> list_purchaseOrderHeaderList;
    @FindBy(xpath = "//b[text()='Purchase Order']/parent::div/following-sibling::div/span")
    public WebElement text_purchaseOrderId;
    @FindBy(xpath = "//b[text()='PO Created By']/parent::div/following-sibling::div/span[1]")
    public WebElement text_poCreatedByUser;
    @FindBy(xpath = "//b[text()='PO Created By']/parent::div/following-sibling::div/span[5]")
    public WebElement text_poCreatedAt;

    @FindBy(xpath = "//th[text()='Description']/ancestor::table//th")
    public List<WebElement> list_rhsTableHeaderPurchaseOrder;
    @FindBy(xpath = "//th[text()='Description']/ancestor::table//tbody/tr")
    public List<WebElement> list_rhsTableRowPurchaseOrder;
    @FindAll({@FindBy(xpath = "//h4[@class='purchase-transactions-title']/parent::div//th[text()='Description']/ancestor::table//th"),
            @FindBy(xpath = "//div[@class='purchase-transactions']//th[text()='Description']/ancestor::table//th")})
    public List<WebElement> list_rhsTableHeaderPurchaseGrn;
    @FindAll({@FindBy(xpath = "//th[text()='Description']/ancestor::table//tbody/tr"),
            @FindBy(xpath = "//div[@class='purchase-transactions']//th[text()='Description']/ancestor::table//tbody/tr")
    })
    public List<WebElement> list_rhsTableRowPurchaseGrn;

    @FindBy(xpath = "//span[@class='store_info']/parent::h4")
    public WebElement text_headerCreatePurchaseOrder;
    @FindBy(xpath = "//label[contains(text(),'Description')]/parent::div/following-sibling::div[1]")
    public WebElement text_descriptionHeaderAddLot;
    @FindBy(xpath = "//label[contains(text(),'HSN No')]/parent::div/following-sibling::div[1]")
    public WebElement text_hsnNoHeaderAddLot;
    @FindBy(xpath = "//label[contains(text(),'Item Code')]/parent::div/following-sibling::div[1]")
    public WebElement text_itemCodeHeaderAddLot;
    @FindBy(xpath = "//label[contains(text(),'Category')]/parent::div/following-sibling::div[1]")
    public WebElement text_categoryHeaderAddLot;
    @FindBy(xpath = "//label[contains(text(),'Dispensing Unit')]/parent::div/following-sibling::div[1]")
    public WebElement text_dispensingUnitHeaderAddLot;
    @FindBy(xpath = "//label[contains(text(),'Package Type')]/parent::div/following-sibling::div[1]")
    public WebElement text_packageTypeHeaderAddLot;
    @FindBy(xpath = "//label[contains(text(),'Sub package/Package')]/parent::div/following-sibling::div[1]")
    public WebElement text_subPackageHeaderAddLot;
    @FindBy(xpath = "//label[contains(text(),'Unit/Sub package')]/parent::div/following-sibling::div[1]")
    public WebElement text_unitHeaderAddLot;

    @FindBy(xpath = "//tbody[@class='items-variants-body']//td[1]")
    public List<WebElement> list_itemCodeOnLeftInSearchResultPO;

    @FindBy(xpath = "//input[@id='expiry']")
    public WebElement input_expiryDateCreatePO;
    @FindBy(xpath = "//select[@class='ui-datepicker-year']")
    public WebElement select_expiryDateYear;
    @FindBy(xpath = "//a[text()='1']")
    public WebElement select_expiryDateDay;
    @FindBy(xpath = "//label[@id='paid_stock-error']")
    public WebElement label_totalPaidError;
    @FindBy(xpath = "//label[@id='unit_cost-error']")
    public WebElement label_unitCostError;
    @FindBy(xpath = "//lable[@class='item_units_error_message']")
    public WebElement label_unitError;
    @FindBy(xpath = "//input[@id='stock_subpackage']")
    public WebElement input_subPackage;
    @FindBy(xpath = "//input[@id='stock_package']")
    public WebElement input_package;
    @FindBy(xpath = "//input[@id='paid_stock']")
    public WebElement input_paidStock;
    @FindBy(xpath = "//select[@id='item_discount_type']")
    public WebElement select_discountType;
    @FindBy(xpath = "//div[@class='discount_amount_value']")
    public WebElement label_discountValue;
    @FindBy(xpath = "//input[@id='inventory_order_purchase_note']")
    public WebElement input_orderNote;
    @FindBy(xpath = "//input[@id='inventory_order_purchase_store_gst']")
    public WebElement input_storeGstNo;
    @FindBy(xpath = "//input[@id='inventory_order_purchase_po_expiry_date']")
    public WebElement input_expiryDatePO;
    @FindBy(xpath = "//input[@id='inventory_order_purchase_vendor_credit_days']")
    public WebElement input_creditDays;
    @FindBy(xpath = "//select[@id='inventory_order_purchase_ship_to_store']")
    public WebElement select_shipToStore;
    @FindBy(xpath = "//select[@id='inventory_order_purchase_bill_to_store']")
    public WebElement select_billToStore;
    @FindBy(xpath = "//input[contains(@id,'inventory_order_purchase_items') and contains(@id,'description')]")
    public List<WebElement> list_itemDescriptionCreatePO;
    @FindBy(xpath = "//input[contains(@id,'inventory_order_purchase_items') and contains(@id,'unit_cost_without_tax')][@type='text']")
    public List<WebElement> list_itemRateCreatePO;
    @FindBy(xpath = "//input[contains(@id,'inventory_order_purchase_items') and contains(@id,'tax_rate')][@type='text']")
    public List<WebElement> list_itemTaxRateCreatePO;
    @FindBy(xpath = "//input[contains(@id,'inventory_order_purchase_items') and contains(@id,'paid_stock')][@type='text']")
    public List<WebElement> list_itemPaidQtyCreatePO;
    @FindBy(xpath = "//input[contains(@id,'inventory_order_purchase_items') and contains(@id,'free_unit')][@type='text']")
    public List<WebElement> list_itemFreeQtyCreatePO;
    @FindBy(xpath = "//input[contains(@id,'inventory_order_purchase_items') and contains(@id,'discount')][@type='text']")
    public List<WebElement> list_itemDiscountCreatePO;
    @FindBy(xpath = "//input[contains(@id,'inventory_order_purchase_items') and contains(@id,'amount_after_tax')][@type='text']")
    public List<WebElement> list_itemNetAmountCreatePO;
    @FindBy(xpath = "//input[contains(@id,'inventory_order_purchase_items') and contains(@id,'sub_store')][@type='text']")
    public List<WebElement> list_itemSubStoreCreatePO;
    @FindBy(xpath = "//input[contains(@id,'inventory_order_purchase_items') and contains(@id,'remark')][@type='text']")
    public List<WebElement> list_itemRemarkCreatePO;
    @FindBy(xpath = "//select[contains(@id,'other_charges')]")
    public List<WebElement> select_otherCharges_createPo;
    @FindBy(xpath = "//input[contains(@id,'other_charges')][@placeholder='Price']")
    public List<WebElement> input_otherCharges_createPo;
    @FindBy(xpath = "//input[contains(@class,'other_charge_amount_percent')]")
    public WebElement input_otherChargesPercent_createPo;
    @FindBy(xpath = "//input[contains(@class,'other_charge_net_amount_show')]")
    public WebElement text_displayOtherCharges_createPo;
    @FindBy(xpath = "//span[contains(@class,'btn-add-item')]")
    public WebElement button_addOtherCharges_createPo;
    @FindBy(xpath = "//button[contains(@class,'other-charge-minus')]")
    public List<WebElement> button_minusOtherCharges_createPo;
    @FindBy(xpath = "//button[contains(@class,'btn-remove-item')]")
    public List<WebElement> button_removeOtherCharges_createPo;
    @FindBy(xpath = "//input[contains(@name,'total_other_charges_amount')][@type='text']")
    public WebElement text_finalOtherCharges_createPo;
    @FindBy(xpath = "//button[@id='get_delivery_terms']")
    public WebElement button_deliveryTerms;
    @FindBy(xpath = "//select[contains(@name,'payment_terms')]")
    public WebElement select_paymentTerms;
    @FindBy(xpath = "//select[contains(@name,'delivery_terms')]")
    public WebElement select_deliveryTerms;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//tr/td[1]")
    public List<WebElement> list_orderInfoTransactionList;
    @FindBy(xpath = "//b[text()='PO Status']/parent::div/following-sibling::div/span")
    public WebElement text_rhsPoStatus;
    @FindBy(xpath = "//b[text()='PO Type']/parent::div/following-sibling::div/span")
    public WebElement text_rhsPoType;
    @FindBy(xpath = "//b[text()='PO Expiry']/parent::div/following-sibling::div/span")
    public WebElement text_rhsPoExpiry;
    @FindBy(xpath = "//b[text()='Vendor']/parent::div/following-sibling::div/span")
    public WebElement text_rhsPoVendor;
    @FindBy(xpath = "//b[text()='Vendor GST']/parent::div/following-sibling::div/span")
    public WebElement text_rhsPoVendorGSTNo;
    @FindBy(xpath = "//b[text()='Vendor Credit Days']/parent::div/following-sibling::div/span")
    public WebElement text_rhsPoVendorCreditDays;
    @FindBy(xpath = "//b[text()='Bill To']/parent::div/following-sibling::div/span")
    public WebElement text_rhsPoStoreBillTo;
    @FindBy(xpath = "//b[text()='Ship To']/parent::div/following-sibling::div/span")
    public WebElement text_rhsPoStoreShipTo;
    @FindBy(xpath = "//b[text()='Store GSTIN']/parent::div/following-sibling::div/span")
    public WebElement text_rhsPoStoreGSTNo;
    @FindBy(xpath = "//b[text()='Entity Group']/parent::div/following-sibling::div/span")
    public WebElement text_rhsPoEntityGroup;
    @FindBy(xpath = "//div/b[contains(text(),'GST5')]/parent::div/following-sibling::div[2]")
    public WebElement text_totalGST5TaxAmt;
    @FindBy(xpath = "//div/b[contains(text(),'GST28')]/parent::div/following-sibling::div[2]")
    public WebElement text_totalGST28TaxAmt;
    @FindBy(xpath = "//a[contains(@href,'edit')]")
    public WebElement button_editPO;
    @FindBy(xpath = "//h4[text()='Approved']")
    public WebElement label_approved;
    @FindBy(xpath = "//h4[text()='Closed']")
    public WebElement label_closed;
    @FindBy(xpath = "//a[contains(@href,'cancel')]")
    public WebElement button_cancelPO;
    @FindBy(xpath = "//h4[text()='Cancelled']")
    public WebElement label_cancelled;
    @FindBy(xpath = "//div[@class='purchase-transactions']//a")
    public List<WebElement> list_transactionAgainstOrder;

    @FindBy(xpath = "//b[text()='Cancellation Reason']/parent::div/following-sibling::div/span")
    public WebElement text_rhsCancellationReason;

    @FindBy(xpath = "//b[contains(text(),'Indent')]/parent::div/following-sibling::div//span[1]")
    public WebElement text_rhsIndentNumber;
    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tr/td[3]")
    public List<WebElement> list_itemNameOnRequisition;
    @FindBy(xpath = "//input[@id='inventory_order_purchase_store_name']")
    public WebElement input_purchaseStoreName;

}
