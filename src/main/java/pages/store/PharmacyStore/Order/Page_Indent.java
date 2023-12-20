package pages.store.PharmacyStore.Order;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Indent extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_Indent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//a[text()=' New']")
    public WebElement button_addNewIndent;

    @FindBy(xpath = "//button[@id='get_variant']")
    public WebElement button_variantOrRequisitionSelected;

    @FindBy(xpath = "//table[@class='table items-variants-table']/tbody/tr/td[2]")
    public List<WebElement> list_ItemDescriptionsUnderIndentPurchase;

    @FindBy(xpath = "//table[@id='medicationTable']/tbody/tr")
    public List<WebElement> input_itemForIndent;

    @FindBy(xpath = "//span[@class='select2-selection__arrow']//b")
    public WebElement select_vendorField;

    @FindBy(xpath = "//span[@id='select2-inventory_order_purchase_vendor_id-container']")
    public WebElement select_vendorFieldForOrder;
    @FindBy(xpath = "//input[@id='inventory_order_purchase_vendor_address']")
    public WebElement input_searchVendorAddress;

    @FindBy(xpath = "//ul[contains(@id,'ui-id')]//li[1]/a")
    public WebElement vendorAddressName;

    @FindBy(xpath = "//ul[@id='select2-inventory_order_indent_vendor_id-results']//li")
    public List<WebElement> list_vendors;
    @FindBy(xpath = "//ul[@id='select2-inventory_order_indent_store_id-results']//li")
    public List<WebElement> list_stores;

    @FindBy(xpath = "//input[@id='inventory_order_indent_remarks']")
    public WebElement input_noteUnderIndentForPurchase;

    @FindBy(xpath = "//input[@id='inventory_order_indent_indent_date']")
    public WebElement input_indentOrderDate;

    @FindBy(xpath = "//input[@id='inventory_order_indent_indent_time_picker']")
    public WebElement input_indentOrderTime;

    @FindBy(xpath = "//input[@id='inventory_order_purchase_order_date']")
    public WebElement input_OrderDate;

    @FindBy(xpath = "//input[@id='inventory_order_purchase_order_time_picker']")
    public WebElement input_OrderTime;
    @FindBy(xpath = "//select[@id='inventory_order_purchase_vendor_location_id']")
    public WebElement select_VendorForOrder;

    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveIndentPurchaseOrder;

    @FindBy(xpath = "//input[@value='Update Changes']")
    public WebElement button_updateChanges;

    @FindBy(xpath = "//button[contains(@class,'cancel-indent')]")
    public WebElement button_cancelIndent;

    @FindBy(xpath = "//div[contains(@class,'modal false fade in')]/div/div/form/div/input")
    public WebElement input_indentCancelReason;

    @FindBy(xpath = "//div[contains(@class,'modal false fade in')]/div/div/form/div[2]/input")
    public WebElement button_cancelIndentConfirmation;

    @FindBy(xpath = "//div/b[text()='Cancellation Reason']/parent::div/following-sibling::div[1]/span")
    public WebElement text_cancelReasonOnUI;

    @FindBy(xpath = " //button[@title='Remove From List']")
    public WebElement button_deleteItem;

    @FindBy(xpath = "//button[@title='Remove From List']")
    public List<WebElement> list_deleteItemButton;

    @FindBy(xpath = "//input[@id='variants_search']")
    public WebElement input_variantSearch;

    @FindBy(xpath = "//label[text()='This field is required.']")
    public WebElement text_validationForQuantity;

    @FindBy(xpath = "//a[text()=' New Order']")
    public WebElement button_newOrder;

    @FindBy(xpath = "//input[@id='myTextBox']")
    public WebElement input_quantityField;

    @FindBy(xpath = "//tbody[@class='medication-table-body-added medication_set_body']/tr[@class='treatmentmedications']")
    public List<WebElement> list_itemNameSelectedToCreateIndentPurchase;
    @FindBy(xpath = "//tbody[@class='medication-table-body-added medication_set_body']/tr[@class='treatmentmedications']//td[4]//input[1]")
    public  List<WebElement> list_quantitySelectedToCreateIndentPurchase;

    @FindBy(xpath = "//tbody[@class='medication-table-body-added medication_set_body']/tr[contains(@class,'treatmentmedications')]/td[4]/input[1]")
    public List<WebElement>list_quantityPresentForItemInEditPage;
    @FindBy(xpath = "//tbody[@class='medication-table-body-added medication_set_body']/tr[@class='treatmentmedications']//td[8]//select[1]")
    public List<WebElement> list_subStoreSelectedToCreateIndentPurchase;

    @FindBy(xpath = "//select[contains(@id,'indent_type')]")
    public WebElement select_indentType;
    @FindBy(xpath = "//lable[contains(text(),'Total Quantity :')]")
    public WebElement text_totalQuantity;
    @FindBy(xpath = "//lable[contains(text(),'Item can be in multiple of')]")
    public WebElement text_multipleOfItemText;


    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//tr")
    public List<WebElement> list_dateTimeOfIndentOrder;


    @FindBy(xpath = "//input[@id='inventory_order_purchase_note']")
    public WebElement input_OrderNote;

    @FindBy(xpath = "//span[contains(@aria-labelledby,'select2-inventory_order_purchase_po_type-container')]")
    public WebElement dropdown_IndentOrderType;
    @FindBy(xpath = "//span[contains(@aria-labelledby,'select2-inventory_order_indent_indent_type-container')]")
    public WebElement dropdown_IndentType;
    @FindBy(xpath = "//ul[contains(@id,'select2-inventory_order_purchase_po_type-results')]/li[@class='select2-results__option']")
    public List<WebElement> list_IndentType;

    @FindBy(xpath = "//ul[contains(@id,'select2-inventory_order_indent_indent_type-results')]/li[@class='select2-results__option']")
    public List<WebElement> list_IndentTypeList;

    @FindBy(xpath = "//span[@id='select2-inventory_order_purchase_ship_to_store-container']")
    public WebElement dropdown_ShipToStore;
    @FindBy(xpath = "//select[@id='inventory_order_purchase_ship_to_store']")
    public WebElement select_shipTo;

    @FindBy(xpath = "//select[@id='inventory_order_purchase_bill_to_store']")
    public WebElement select_BillTo;

    @FindBy(xpath = "//ul[@id='select2-inventory_order_purchase_ship_to_store-results']//li")
    public List<WebElement> list_ShipToStoreList;

    @FindBy(xpath = "//span[@id='select2-inventory_order_purchase_bill_to_store-container']")
    public WebElement dropdown_BillToStore;

    @FindBy(xpath = "//ul[@id='select2-inventory_order_purchase_bill_to_store-results']//li")
    public List<WebElement> list_BillToStoreList;

    @FindBy(xpath = "//input[@id='inventory_order_purchase_items_attributes_0_unit_cost_without_tax']")
    public WebElement input_RatePerUnit;

    @FindBy(xpath = "//tbody[@id='tbody_transaction']//tr/td[5]//span")
    public WebElement value_PendingQty;

    @FindBy(xpath = "//input[@id='inventory_order_purchase_items_attributes_0_paid_stock']")
    public WebElement input_PaidQty;
    @FindBy(xpath = "//select[@id='inventory_order_purchase_other_charges_attributes_0_other_charge_id']")
    public WebElement select_OtherChargesType;

    @FindBy(xpath = "//input[@id='inventory_order_purchase_other_charges_attributes_0_amount_show']")
    public WebElement input_OtherCharges;

    @FindBy(xpath = "//input[@id='global_discount']")
    public WebElement input_globalDiscount;
    @FindBy(xpath = "//select[@id='global_discount_type']")
    public WebElement input_globalDiscountType;

    @FindBy(xpath = "//button[text()='Apply']")
    public WebElement button_applyGlobalDiscount;

    @FindBy(xpath = "//input[@id='inventory_order_purchase_items_attributes_0_stock_free_unit']")
    public WebElement input_FreeQty;

    @FindBy(xpath = "//input[@id='inventory_order_purchase_items_attributes_0_item_discount_show']")
    public WebElement input_Discount;

    @FindBy(xpath = "//input[@id='inventory_order_purchase_items_attributes_0_remarks']")
    public WebElement input_Remarks;

    @FindBy(xpath = "//input[@id='inventory_order_purchase_items_attributes_0_total_cost']")
    public WebElement value_NetAmountTable;

    @FindBy(xpath = "//td[@class='col-md-1 font_size']//input[@id='inventory_order_purchase_items_attributes_0_tax_rate']")
    public WebElement value_TaxRate;

    @FindBy(xpath = "//label[text()='Cannot checkout more than Pending Qty']")
    public WebElement validation_quantity;

    @FindBy(xpath = "//input[@id='inventory_order_purchase_net_amount']")
    public WebElement value_FinalNetAmountList;

    @FindBy(xpath = "//table[@id='indent-table']//tr[1]//td[9]/div[2]")
    public WebElement text_DiscountValueFromUI;

    @FindBy(xpath = "//select[@id='inventory_order_purchase_items_attributes_0_item_discount_type']//option[@value='₹']")
    public WebElement select_DiscountTypeRupee;

    @FindBy(xpath = "//select[@id='inventory_order_purchase_items_attributes_0_item_discount_type']//option[@value='%']")
    public WebElement select_DiscountTypePercentage;


    @FindBy(xpath = "//select[@id='inventory_order_purchase_items_attributes_0_item_discount_type']")
    public WebElement select_DiscountType;

    @FindBy(xpath = "//textarea[@id='inventory_order_purchase_comments']")
    public WebElement text_Comments;

    @FindBy(xpath = "//select[@id='inventory_order_purchase_items_attributes_0_sub_store_id']")
    public WebElement select_SubStore;

    @FindBy(xpath = "//select[@name='inventory_order_purchase[other_charges_attributes][1][other_charge_id]']")
    public WebElement dropdown_otherCharges;

    @FindBy(xpath = "//input[@name='inventory_order_purchase[other_charges_attributes][1][amount_show]']")
    public WebElement input_otherChargesValue;

    @FindBy(xpath = "//button[@class='btn btn-default btn-xs other-charge-minus']")
    public WebElement button_otherChargesMinus;

    @FindBy(xpath = "//button[@class='btn btn-default btn-xs other-charge-plus']")
    public WebElement button_otherChargesPlus;

    @FindBy(xpath = "//button[@class='btn btn-danger btn-xs btn-remove-item']")
    public WebElement button_cancelOtherCharges;

    @FindBy(xpath = "//span[@onclick='add_other_charges()']")
    public WebElement button_addOtherCharges;

    @FindBy(xpath = "//input[@id='inventory_order_purchase_total_cost']")
    public WebElement text_grossAmountOnUI;

    @FindBy(xpath = "//input[@id='inventory_order_purchase_discount']")
    public WebElement text_discountOnUI;
    @FindBy(xpath = "//input[@id='inventory_order_purchase_discount_show']")
    public WebElement text_discountOnUIText;




    @FindBy(xpath = "//b[text()='GST5']/parent::div/following-sibling::div[2]/input")
    public WebElement text_GSTOnUI;

    @FindBy(xpath = "//div[@class='col-sm-4 font_size']/input[@id='total_other_charges_amount']")
    public WebElement text_otherChargesOnUI;

    @FindBy(xpath = "//input[@id='inventory_order_purchase_net_amount']")
    public WebElement text_netAmountOnUI;

    @FindBy(xpath = "(//table[@id='medicationTable']//select[contains(@name,'sub_store_id')])[2]")
    public WebElement select_substoreFromIndentPurchasePage;

//(//select[contains(@id,'order_indent_items_att')])[2]

    @FindBy(xpath = "//b[text()='Indent']/parent::div/following-sibling::div//span[1]")
    public WebElement text_IndentNumber;

    @FindBy(xpath = "//div[@class='modal-content transaction-content']//div[@class='container-fluid']/div[1]/div[1]/strong")
    public WebElement text_IndentNumberInPurchaseOrderPopUp;
    @FindBy(xpath = "//h4//a[@id='show_vendor_purchase_rate']")
    public WebElement link_indentNumberLinkInNewOrder;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']/tr[1]/td[2]/b")
    public WebElement text_itemNameInPurchaseOrderPopUp;
    @FindBy(xpath = "//h4[text()='Create New Purchase Order']")
    public WebElement text_POHeader;

    @FindBy(xpath = "//tbody[@id='tbody_transaction']//tr/td[7]/input[1]")
    public WebElement text_quantityInPurchaseOrderPopUp;

    @FindBy(xpath = "//label[@id='inventory_order_purchase_ship_to_store-error']")
    public WebElement text_validationMessageForShipToField;

    @FindBy(xpath = "//label[@id='inventory_order_purchase_bill_to_store-error']")
    public WebElement text_validationMessageForBillToField;

    @FindBy(xpath = "//label[@id='inventory_order_purchase_items_attributes_0_unit_cost_without_tax-error']")
    public WebElement text_validationMessageForRatePerUnitField;

    @FindBy(xpath = "//label[@id='inventory_order_purchase_other_charges_attributes_0_other_charge_id-error']")
    public WebElement text_validationMessageForOtherChargesNameField;

    @FindBy(xpath = "//label[@id='inventory_order_purchase_other_charges_attributes_0_amount_show-error']")
    public WebElement text_validationMessageForOtherChargesAmountField;

    @FindBy(xpath = "//label[@id='inventory_order_purchase_other_charges_attributes_0_amount_show-error']")
    public WebElement text_validationMessageForOtherChargesNetField;

    @FindBy(xpath = "//b[contains(text(),'Status')]/parent::div/following-sibling::div/span")
    public WebElement text_statusOfOrder;

    @FindBy(xpath = " //ul[@class='nav nav-pills nav-stacked nav-bracket']/li[4]//ul/li[1]/a ")
    public WebElement option_purchaseOrder;

    @FindBy(xpath = "//a[@class='btn btn-xs btn-info']")
    public WebElement button_approve;

    @FindBy(xpath = "//a[text()=' New Transaction']")
    public WebElement button_newTransaction;

    @FindBy(xpath = " //tbody[@class='transaction-table-body']/tr/td[10]//input[1]")
    public WebElement text_quantityInPoTable;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']/tr/td[2]//span")
    public WebElement text_itemNameInPoTable;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']/tr/td[11]/input")
    public WebElement text_freeQuantityInPoTable;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']/tr/td[7]/span")
    public WebElement text_taxRateInPoTable;

    @FindBy(xpath = " //tbody[@class='transaction-table-body']/tr/td[6]/input")
    public WebElement input_mrpInNewTransaction;
    @FindBy(xpath = " //tbody[@class='transaction-table-body']/tr/td[8]/input")
    public WebElement input_batchNOInNewTransaction;
    @FindBy(xpath = " //tbody[@class='transaction-table-body']/tr/td[12]/div[2] ")
    public WebElement text_DiscountInPoTable;

    @FindBy(xpath = " //tbody[@class='transaction-table-body']/tr/td[13]/input[1] ")
    public WebElement text_TotalAmountInPoTable;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']/tr/td[15]/input[1] ")
    public WebElement text_remarksInPoTable;

    @FindBy(xpath = " //input[@id='inventory_transaction_purchase_total_cost']")
    public WebElement text_GrossAmountForPO;

    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_discount']")
    public WebElement text_discountForPo;

    @FindBy(xpath = " //b[text()='GST5']/parent::div/following-sibling::div[2]/input")
    public WebElement text_GSTForPo;
    @FindBy(xpath = " //input[@id='plus_other_charges_amount']")
    public WebElement input_otherCharges;

    @FindBy(xpath = " //input[@id='total_other_charges_amount']")
    public WebElement text_otherChargesForPo;

    @FindBy(xpath = "//input[@id='inventory_transaction_purchase_net_amount']")
    public WebElement text_netAmountForPo;

    @FindBy(xpath = " //span[@aria-hidden='true']")
    public WebElement button_closePoPopUp;

    @FindBy(xpath = "//button[@id='get_requisition_list']")
    public WebElement button_requisitionToSelect;

    @FindBy(xpath = "//div/table/tbody[@class='requisition-body']/tr/td[1]")
    public List<WebElement> list_ReqNumberUnderIndentPurchase;

    @FindBy(xpath = "//select[@id='inventory_order_indent_store_id']")
    public WebElement select_StoreInIndent;

    @FindBy(xpath="//h4[text()='Item Details:']/parent::div/following-sibling::div//td[2] ")
    public WebElement text_itemDescriptionInIndentRHSTable;
    @FindBy(xpath="//h4[text()='Item Details:']/parent::div/following-sibling::div//td[4]  ")
    public WebElement text_itemQuantityInIndentRHSTable;

    @FindBy(xpath="//h4[@class='panel-title']/a  ")
    public WebElement text_poNumberOrderAgainstIndentRHS;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//tr//td[5]")
    public List<WebElement> list_indentNumberOfIndentOrder;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']//tr//td[3]//div//input[1]")
    public List<WebElement> list_rateListOfIndentOrder;
    @FindBy(xpath = "//button[@title='Add Line Item']")
    public List<WebElement> list_addActionListOfIndentOrder;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']//tr//td[7]/input[1]")
    public List<WebElement> list_paidQuantityListOfIndentOrder;
    @FindBy(xpath = "//tbody[@class='transaction-table-body']//tr//td[9]//input[1]")
    public List<WebElement> list_discountListOfIndentOrder;
    @FindBy(xpath = "//tbody[@class='transaction-table-body']//tr//td[9]//div[@class='discount-data']")
    public List<WebElement> list_discountValueListOfIndentOrder;
    @FindBy(xpath = "//tbody[@class='transaction-table-body']//tr//td[6]//input[2]")
    public List<WebElement> list_taxRateListOfIndentOrder;
    @FindBy(xpath = "//b[text()='GST0']/parent::div/following-sibling::div[2]/input")
    public WebElement text_GST0OnUI;
    @FindBy(xpath = "//b[text()='GST5']/parent::div/following-sibling::div[2]/input")
    public WebElement text_GST5OnUI;
    @FindBy(xpath = "//b[text()='GST12']/parent::div/following-sibling::div[2]/input")
    public WebElement text_GST12OnUI;
    @FindBy(xpath = "//b[text()='GST18']/parent::div/following-sibling::div[2]/input")
    public WebElement text_GST18OnUI;
    @FindBy(xpath = "//b[text()='GST28']/parent::div/following-sibling::div[2]/input")
    public WebElement text_GST28OnUI;
    @FindBy(xpath = "//div[@id='tax_details']//input")
    public WebElement text_GSTOnUIText;

    @FindBy(xpath = "//tbody[@class='transaction-table-body']/tr/td[2]/b")
    public List<WebElement> text_itemNameInPurchaseOrderTemplate;


    @FindBy(xpath = "//div/table/tbody[@class='requisition-body']/tr/td[1]")
    public List<WebElement> list_reqInfoUnderIndentPurchase;

    @FindBy(xpath="//a[text()='Approve']")
    public WebElement button_approveIndent;

    @FindBy(xpath = "//div[@class='modal false fade in']/div/div/div[3]/button[text()='Yes']")
    public WebElement button_approveConfirmation;

    @FindBy(xpath = "//a[text()=' Edit']")
    public WebElement button_editIndent;

    @FindBy(xpath = "//button[@id='get_requisition_list']")
    public WebElement button_requisitionIndent;

    @FindBy(xpath = "//tr[@id='6491a9fc3e00ca26443af40b']")
    public WebElement select_requisitionItemForIndent;

    @FindBy(xpath = "//td[@class='col-md-2 font_size item_remark modalRequest_input_style']")
    public WebElement input_remarksforRequisitionItemForIndent;

    @FindBy(xpath = "//tbody[@class='inventory-table-body']/tr[1]")
    public WebElement select_indentItem;

    @FindBy(xpath = "//a[@class='btn btn-primary btn-xs']")
    public WebElement button_editForIndentItem;

    @FindBy(xpath = "//input[@class='btn btn-success validate-indent']")
    public WebElement button_updateChangesInIndent;



}
