package pages.store.PharmacyStore.Order;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_WorkOrder extends TestBase {

    public Page_WorkOrder(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[text()=' New']")
    public WebElement button_new;

    @FindBy(xpath = "//a[text()='Approve']")
    public WebElement button_approve;
    @FindBy(xpath = "//a[contains(@href,'edit')]")
    public WebElement button_edit;
    @FindBy(xpath = "//button[contains(@class,'btn btn-xs btn-danger')]")
    public WebElement button_cancel;
    @FindBy(xpath = "//h4[text()='New Work Order']")
    public WebElement text_headerCreteWorkOrder;
    @FindBy(css = "#inventory_order_work_order_vendor_location_id")
    public WebElement select_vendor;
    @FindBy(css = ".select2-search__field")
    public WebElement input_vendorSelect;
    @FindBy(xpath = "//table[@class='table items-variants-table']//tr")
    public List<WebElement> list_row_createWorkOrder;
    @FindBy(xpath = "//table[@class='table items-variants-table']//tr/td[1]")
    public List<WebElement> list_column_itemCode_createWorkOrder;
    @FindBy(xpath = "//table[@class='table items-variants-table']//tr/td[2]/b")
    public List<WebElement> list_column_itemDescription_createWorkOrder;
    @FindBy(xpath = "//table[@class='table items-variants-table']//tr/td[3]")
    public List<WebElement> list_column_itemStock_createWorkOrder;
    @FindBy(xpath = "//table[@class='table items-variants-table']//tr/td[4]")
    public List<WebElement> list_column_itemCategory_createWorkOrder;
    @FindBy(id = "variants_search")
    public WebElement input_searchItemCreateWO;
    @FindBy(xpath = "//input[@id='inventory_order_work_order_note']")
    public WebElement input_orderNotesCreateWO;
    @FindBy(xpath = "//select[contains(@id,'work_order_type')]")
    public WebElement select_orderTypeCreateWO;
    @FindBy(xpath = "//input[contains(@id,'work_order_date')][@type='text']")
    public WebElement input_orderDateCreateWO;

    @FindBy(xpath = "//table[@class='ui-datepicker-calendar']/tbody/tr/td[contains(@class,'ui-datepicker-today')]")
    public WebElement input_todayBillDate;
    @FindBy(xpath = "//input[contains(@id,'work_order_time')][@type='text']")
    public WebElement input_orderTimeCreateWO;
    @FindBy(xpath = "//input[contains(@id,'work_order_vendor_credit_days')][@type='text']")
    public WebElement input_creditDaysCreateWO;
    @FindBy(xpath = "//input[contains(@id,'expiry_date')]")
    public WebElement input_expiryDateCreateWO;
    @FindBy(xpath = "//table[@id='workOrderTable']/thead//td")
    public List<WebElement> list_tableHeader_createWorkOrder;
    @FindBy(xpath = "//table[@id='workOrderTable']/tbody/tr")
    public List<WebElement> list_tableBody_createWorkOrder;
    @FindBy(xpath = "//table[@id='workOrderTable']/tbody/tr//td[2]/span")
    public List<WebElement> list_textTableItemDescription_createWorkOrder;
    @FindBy(xpath = "//table[@id='workOrderTable']/tbody/tr//td/input[contains(@id,'instruction')]")
    public List<WebElement> list_inputTableInstructions_createWorkOrder;
    @FindBy(xpath = "//table[@id='workOrderTable']/tbody/tr//td//input[contains(@id,'unit_cost')]")
    public List<WebElement> list_inputTableRate_createWorkOrder;
    @FindBy(xpath = "//table[@id='workOrderTable']/tbody/tr//td//select[contains(@id,'tax_group_id')]")
    public List<WebElement> list_selectTableTax_createWorkOrder;
    @FindBy(xpath = "//table[@id='workOrderTable']/tbody/tr//td//input[contains(@class,'item_quantity')]")
    public List<WebElement> list_inputTableQuantity_createWorkOrder;
    @FindBy(xpath = "//table[@id='workOrderTable']/tbody/tr//td//input[contains(@id,'item_discount_show')]")
    public List<WebElement> list_inputTableDiscount_createWorkOrder;
    @FindBy(xpath = "//table[@id='workOrderTable']/tbody/tr//td//select[contains(@id,'item_discount_type')]")
    public List<WebElement> list_selectTableDiscountType_createWorkOrder;
    @FindBy(xpath = "//table[@id='workOrderTable']/tbody/tr//td//span[contains(@class,'discount_amount')]")
    public List<WebElement> list_textTableDiscountValue_createWorkOrder;
    @FindBy(xpath = "//table[@id='workOrderTable']/tbody/tr//td//span[@class='net-amount']")
    public List<WebElement> list_textTableNetAmount_createWorkOrder;
    @FindBy(xpath = "//a[@id='show_vendor_purchase_rate']")
    public List<WebElement> list_button_previousRate_createWorkOrder;
    @FindBy(xpath = "//select[contains(@id,'other_charges')]")
    public List<WebElement> select_otherCharges_createWorkOrder;
    @FindBy(xpath = "//input[contains(@id,'work_order_other_charges')][@placeholder='Price']")
    public List<WebElement> input_otherCharges_createWorkOrder;
    @FindBy(xpath = "//input[contains(@class,'other_charge_amount_percent')]")
    public WebElement input_otherChargesPercent_createWorkOrder;
    @FindBy(xpath = "//input[contains(@class,'other_charge_net_amount_show')]")
    public WebElement text_displayOtherCharges_createWorkOrder;
    @FindBy(xpath = "//button[contains(@class,'other-charge-plus')]")
    public WebElement button_plusOtherCharges_createWorkOrder;
    @FindBy(xpath = "//button[contains(@class,'other-charge-minus')]")
    public List<WebElement> button_minusOtherCharges_createWorkOrder;
    @FindBy(xpath = "//button[contains(@class,'btn-remove-item')]")
    public List<WebElement> button_removeOtherCharges_createWorkOrder;
    @FindBy(xpath = "//span[contains(@class,'btn-add-item')]")
    public WebElement button_addOtherCharges_createWorkOrder;
    @FindBy(xpath = "//input[@id='global_discount']")
    public WebElement input_globalDiscount_createWorkOrder;
    @FindBy(xpath = "//select[@id='global_discount_type']")
    public WebElement select_globalDiscountType_createWorkOrder;
    @FindBy(xpath = "//button[text()='Apply']")
    public WebElement button_applyGlobalDiscount_createWorkOrder;
    @FindBy(xpath = "//button[@title='Remove From List']")
    public WebElement button_removeFromList_createWorkOrder;
    @FindBy(xpath = "//*[@value='Save Changes']")
    public WebElement button_saveChanges_createWorkOrder;
    @FindBy(xpath = "//input[@id='inventory_order_work_order_total_cost']")
    public WebElement text_finalGrossAmount_createWorkOrder;
    @FindBy(xpath = "//input[@id='inventory_order_work_order_discount']")
    public WebElement text_finalTotalDiscount_createWorkOrder;
    @FindBy(xpath = "//b[contains(text(),'GST5')]/parent::div/following-sibling::div/input")
    public WebElement text_finalGST5_createWorkOrder;
    @FindBy(xpath = "//b[contains(text(),'GST12')]/parent::div/following-sibling::div/input")
    public WebElement text_finalGST12_createWorkOrder;
    @FindBy(xpath = "//input[@id='inventory_order_work_order_net_amount']")
    public WebElement text_finalNetAmount_createWorkOrder;
    @FindBy(xpath = "//input[contains(@name,'total_other_charges_amount')][@type='text']")
    public WebElement text_finalOtherCharges_createWorkOrder;

    @FindBy(xpath = "//button[@id='get_delivery_terms']")
    public WebElement button_deliveryTerms;
    @FindBy(xpath = "//select[contains(@id,'work_order_payment_terms')]")
    public WebElement select_paymentTerms;
    @FindBy(xpath = "//select[contains(@id,'work_order_delivery_terms')]")
    public WebElement select_deliveryTerms;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/thead/tr/td")
    public List<WebElement> list_woHeaderList;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//tr")
    public List<WebElement> list_woCreatedList;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//tr/child::td/span[2]")
    public List<WebElement> list_workOrderNoList;
    @FindBy(xpath = "//b[text()='Work Order No']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_workOrderNo;
    @FindBy(xpath = "//b[text()='WO Created By']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_workOrderCreatedByUser;
    @FindBy(xpath = "//b[text()='WO Created By']/parent::div/following-sibling::div/span[3]")
    public WebElement text_rhs_workOrderCreatedStore;
    @FindBy(xpath = "//b[text()='WO Created By']/parent::div/following-sibling::div/span[5]")
    public WebElement text_rhs_workOrderCreatedAt;
    @FindBy(xpath = "//b[text()='WO Status']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_workOrderStatus;
    @FindBy(xpath = "//b[text()='WO Type']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_workOrderType;
    @FindBy(xpath = "//b[text()='Vendor']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_workOrderVendor;
    @FindBy(xpath = "//b[text()='Vendor GST']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_workOrderVendorGST;
    @FindBy(xpath = "//b[text()='WO Cancelled By']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_workOrderCancelledByUser;
    @FindBy(xpath = "//b[text()='WO Cancelled By']/parent::div/following-sibling::div/span[3]")
    public WebElement text_rhs_workOrderCancelledAt;
    @FindBy(xpath = "//b[text()='WO Cancelled By']/parent::div/following-sibling::div/span[5]")
    public WebElement text_rhs_workOrderCancelledReason;
    @FindBy(xpath = "//table[contains(@class,'table-striped')]/thead//th")
    public List<WebElement> list_rhsTableHeader;
    @FindBy(xpath = "//table[contains(@class,'table-striped')]/tbody/tr")
    public List<WebElement> list_rhsTableRow;
    @FindBy(xpath = "//div[@id='item_information']//b")
    public List<WebElement> list_rhsItemInfoHeader;
    @FindBy(xpath = "//div[@id='item_information']//b/parent::div/following-sibling::div[2]")
    public List<WebElement> list_rhsItemInfoValue;
    @FindBy(xpath = "//input[contains(@id,'work_order_updated_reason')]")
    public WebElement input_updateReason;
    @FindBy(xpath = "//*[@value='Update Changes']")
    public WebElement button_updateChanges;
    @FindBy(xpath = "//button[text()='Yes']")
    public WebElement button_yes;
    @FindBy(xpath = "//a[contains(@href,'work_orders/print')]")
    public WebElement button_print;

    @FindBy(xpath = "//h4[text()='Approved']")
    public WebElement label_approved;
    @FindBy(xpath = "//a[contains(@href,'new_fulfilment')]")
    public WebElement button_newTransaction;

    @FindBy(xpath = "//button[contains(@class,'btn-xs btn-danger')]")
    public WebElement button_close;
    @FindBy(xpath = "//input[@id='cancelled_reason']")
    public WebElement input_cancellationReason;
    @FindBy(xpath = "//button[contains(@class,'btn-success close-button')]")
    public WebElement button_dontCancel;
    @FindBy(xpath = "//*[@value='Cancel Work Order']")
    public WebElement button_confirmCancel;
    @FindBy(xpath = "//span[normalize-space()='×']")
    public WebElement button_closeCreateWO;

}
