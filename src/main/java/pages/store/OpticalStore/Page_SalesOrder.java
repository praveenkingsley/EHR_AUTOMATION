package pages.store.OpticalStore;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_SalesOrder extends TestBase {
    private WebDriver driver;

    public Page_SalesOrder(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }


    @FindBy(xpath = "//a[text()=' New']")
    public WebElement button_addNewButtonInOrder;

    @FindBy(xpath = "//button[text()='Description']")
    public WebElement button_description;

    @FindBy(xpath = "//input[@id='lots_search']")
    public WebElement input_searchMedicineNameInDescription;

    @FindAll({
            @FindBy(xpath = "//tbody[@class='items-lots-body']/tr//div[1]/span[1]/b"),
            @FindBy(xpath = "//tbody[@class='items-body']/tr/td[2]/b")
    })
    public List<WebElement> list_namesOfMedicinesOnLeftInSearchResult;

    @FindBy(xpath = "//tbody[@class='items-lots-body']/tr")
    public List<WebElement> list_namesOfMedicinesRawOnLeftInSearchResult;

    @FindBy(xpath = "//input[@placeholder='Qty']")
    public WebElement input_quantityOfMedicine;

    @FindBy(xpath = "//input[@id='pending_invoice_total_payment_remaining']")
    public WebElement text_balancePendingAmountAdvance;
    @FindBy(xpath = "//input[@id='invoice_total_payment_remaining']")
    public WebElement text_balancePendingAmountPaymentReceived;
    @FindBy(xpath = "//select[contains(@name,'mode_of_payment')and contains(@name,'pending')]")
    public List<WebElement> select_modeOfPaymentAdvancePayment;
    @FindBy(xpath = "//select[contains(@name,'mode_of_payment')and contains(@name,'received')]")
    public List<WebElement> select_modeOfPaymentReceived;

    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveChangesOnSalesOrder;

    @FindBy(xpath = "//button[text()='Close']")
    public WebElement button_closeModalOfSalesOrder;

    @FindBy(xpath = "//select[@id='patient_search']")
    public WebElement list_PatientSearch;

    @FindBy(xpath = "//input[@id='search-patient']")
    public WebElement input_patientdetails;
    @FindBy(xpath = "(//button[contains(@class,'btn btn-success search-patient-button')])")
    public WebElement button_Search;

    @FindBy(xpath = "//b[contains(text(),'Deepak')]")
    public WebElement selectPatient;

    @FindBy(xpath = "//table[contains(@class,'inventory-')]//td[6]")
    public WebElement priceOfMedication;

    @FindBy(xpath = "//input[contains(@name,'[item_new_non_taxable_amount]')]")
    public WebElement taxableAmount;

    @FindBy(xpath = "(//input[contains(@placeholder,'Tax')])[5]")
    public WebElement taxAmount;
    @FindBy(xpath = "//input[contains(@name,'total_list_price')]")
    public WebElement totalAmount;

    @FindBy(xpath = "//table[contains(@class,'inventory')]//tbody/tr")
    public List<WebElement> list_namesofSalesOrder;

    @FindBy(xpath = "//table[contains(@class,'inventory')]//thead//td")
    public List<WebElement> list_tableHeaderCreatedSalesOrder;
    @FindBy(xpath = "//table[contains(@class,'inventory')]//tbody//tr")
    public List<WebElement> list_tableRowCreatedSalesOrder;
    @FindBy(xpath = "//div[contains(text(),'Patient:')]/following-sibling::div[1]")
    public WebElement text_Patient;
    @FindBy(xpath = "//div[contains(text(),'Doctor:')]/following-sibling::div[1]")
    public WebElement text_rhsDoctorName;
    @FindBy(xpath = "//div[contains(text(),'Legal Name:')]/following-sibling::div[1]")
    public WebElement text_rhsLegalName;
    @FindBy(xpath = "//div[contains(text(),'Order Number:')]/following-sibling::div[1]")
    public WebElement text_rhsOrderNumber;
    @FindBy(xpath = "//div[contains(text(),'Est. Delivery:')]/following-sibling::div[1]")
    public WebElement text_rhsEstimatedDelivery;
    @FindBy(xpath = "//div[contains(text(),'GSTIN:')]/following-sibling::div[1]")
    public WebElement text_rhsGSTIN;
    @FindBy(xpath = "//div[contains(text(),'Current Status:')]/following-sibling::div[1]")
    public WebElement text_rhsCurrentStatus;
    @FindBy(xpath = "//div[contains(text(),'Home Delivery')]/following-sibling::div[1]")
    public WebElement text_rhsHomeDelivery;
    @FindBy(xpath = "//div[contains(text(),'Patient ID:')]/following-sibling::div[1]")
    public WebElement text_rhsPatientId;

    @FindBy(xpath = "(//div[contains(text(),'Received from Advance')]/following-sibling::div)[1]")
    public WebElement text_AdvanceReceived;

    @FindBy(xpath = "(//div[contains(text(),'Payment Received :')]/following-sibling::div)[1]")
    public WebElement text_PaymentReceived;

    @FindBy(xpath = "//div[contains(text(),'Payment Pending :')]/following-sibling::div")
    public WebElement text_PaymentPending;

    @FindBy(xpath = "//table[@class='table table table-striped table-bordered']//td[2]")
    public WebElement text_Quantity;
    @FindBy(xpath = "//a[contains(text(),'More')]")
    public WebElement button_MoreAction;

    @FindBy(xpath = "//button[text()='Stockable']")
    public WebElement button_Stockable;

    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_SaveChanges;
    @FindBy(xpath = "//input[@class='btn btn-success']")
    public WebElement button_saveChangesLot;

    @FindBy(xpath = "//label[text()='Required'][@class='error']")
    public WebElement label_QtyRequired;

    @FindBy(xpath = "//input[@id='invoice_inventory_order_fitting_required']")
    public WebElement checkbox_fittingRequired;

    @FindBy(xpath = "//input[@id='invoice_inventory_order_home_delivery']")
    public WebElement checkbox_homeDelivery;

    @FindBy(xpath = "//select[@id='invoice_inventory_order_fitter_id']")
    public WebElement select_Fitter;

    @FindBy(xpath = "//input[@id='invoice_inventory_order_estimated_ready_date']")
    public WebElement input_ReadyDate;

    @FindBy(xpath = "//input[@id='invoice_inventory_order_estimated_delivery_date']")
    public WebElement input_deliveryDate;

    @FindBy(xpath = "(//input[@placeholder='Date of transition'])[1]")
    public WebElement text_TxnDate;

    @FindBy(xpath = "//input[@id='invoice_inventory_order_order_time']")
    public WebElement text_orderTime;

    @FindBy(xpath = "//input[contains(@name,'[pending_advance_payments_attributes][reason]')]")
    public WebElement input_advanceReason;

    @FindBy(xpath = "//input[contains(@name,'[amount]')and contains(@name,'pending') and contains(@class,'breakups_amount')] ")
    public List<WebElement> input_advanceAmount;

    @FindBy(xpath = "//input[contains(@name,'[amount_received]')] ")
    public List<WebElement> input_amountPaymentReceived;
    @FindBy(xpath = "//input[@name='invoice_inventory_order[pending_advance_payments_attributes][amount]']")
    public WebElement input_displayedAdvanceAmount;
    @FindBy(xpath = "//input[@id='invoice_payment_received_total']")
    public WebElement input_displayedTotalAmount;
    @FindBy(xpath = "//div[@id='freeInvoice']//b[contains(text(),'Advance')]/parent::td/following-sibling::td")
    public WebElement text_AdvancePaid;

    @FindBy(xpath = "//div[@id='freeInvoice']//b[contains(text(),'Remaining')]/parent::td/following-sibling::td")
    public WebElement text_RemainingAmount;

    @FindBy(xpath = "//a[text()='Advance Against Order']")
    public WebElement button_AdvanceAgainstOrder;

    @FindBy(xpath = "//input[@name='advance_payment[reason]']")
    public WebElement input_AdvanceReasonInReceipt;

    @FindBy(xpath = "//div[@id='mop_breakups_details']//select[contains(@name,'mode_of_payment')]")
    public WebElement select_ModeOfPaymentInReceipt;

    @FindBy(xpath = "//input[contains(@name,'[amount]') and contains(@name,'mop_breakup')]")
    public WebElement input_AdvanceAmountAgainstOrder;

    @FindBy(xpath = "//a[@class='btn btn-warning']")
    public WebElement button_CreateBill;

    @FindBy(xpath = "//a[@id='ready']")
    public WebElement button_Ready;

    @FindBy(xpath = "//tbody[@class='inventory-table-body']//tr[1]//td[3]/span[1]")
    public WebElement text_DeliveryDate;

    @FindBy(xpath = "//a[@id='delivered']")
    public WebElement button_Delivered;

    @FindBy(xpath = "//select[@class='form-control payment_received_breakups_mode_of_payment']")
    public WebElement select_ModeOfPaymentInCreateBill;

    @FindBy(xpath = "//input[contains(@name,'amount_received')]")
    public WebElement input_FinalAmount;

    @FindBy(xpath = "//button[contains(@class,'refresh')]")
    public WebElement button_Refresh;

    @FindBy(xpath = "(//div[contains(text(),'Current Status:')]/following-sibling::div)[1]")
    public WebElement text_CurrentStatus;

    @FindBy(xpath = "//input[@name='commit']")
    public WebElement button_CreateBillAfterPayment;

    @FindBy(xpath = "//tbody[@class='inventory-table-body']//tr")
    public List<WebElement> list_salesOrderTableItemDataList;

    @FindBy(xpath = "//a[contains(text(),'Cancel Order')]")
    public WebElement button_cancelOrderInSalesOrder;

    @FindBy(xpath = "//a[contains(text(),'Cancel Bill')]")
    public WebElement button_cancelBillInSale;

    @FindBy(xpath = "//input[@name='invoice_inventory_invoice[payment_received_breakups_attributes][0][date]']")
    public WebElement input_paymentDateInCreateBill;

    @FindBy(xpath = "//input[@name='invoice_inventory_invoice[payment_received_breakups_attributes][0][time]']")
    public WebElement input_paymentTimeInCreateBill;

    @FindBy(xpath = "//input[@id='invoice_total_payment_remaining']")
    public WebElement input_balancePendingInCreateBill;

    @FindBy(xpath = "//button[text()='Non-Stockable']")
    public WebElement button_nonStockable;

    @FindBy(xpath = "//span[@id='select2-invoice_inventory_order_vendor_id-container']")
    public WebElement textbox_vendorNameDropdown;

    @FindBy(xpath = "//ul[@id='select2-invoice_inventory_order_vendor_id-results']/li[2]")
    public WebElement text_FirstVendorNameInVendorList;

    @FindBy(xpath = "//tbody[@class='items-body']/tr")
    public List<WebElement> list_rawOfNonStockableItemOnLeftInSearchResult;

    @FindBy(xpath = "//input[@class='unit_item_list_price modalRequest_input_style']")
    public WebElement textbox_mrpValue;
    @FindBy(xpath = "//a[text()=' Create Requisition']")
    public WebElement button_createRequisition;

    @FindBy(xpath = "//div[@class='prescription-information']/div/span[1]")
    public WebElement text_glassPrescriptionAdvisedOn;
    @FindBy(xpath = "//label[@for='delivery_completed']")
    public WebElement label_completedDeliveryRadioButtonLabel;
    @FindBy(xpath = "//input[contains(@id,'unit_checked')]")
    public WebElement checkout_lot;
    @FindBy(xpath = "//button[text()='Description']")
    public WebElement button_barcodeSearchBy;
    @FindBy(xpath = "//select[@id='invoice_inventory_order_vendor_id']")
    public WebElement select_vendor;
    @FindBy(xpath = "//i[@class='fa fa-trash-alt']")
    public WebElement button_removeFromList;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_consultant_name']")
    public WebElement input_doctorName;

    @FindBy(xpath = "//table[@class='ui-datepicker-calendar']/tbody/tr/td[contains(@class,'ui-datepicker-today')]")
    public WebElement input_todayBillDate;

    @FindBy(xpath = "//label[@for='invoice_inventory_order_is_create_gst_bill']")
    public WebElement checkbox_createGstBill;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_gstin']")
    public WebElement input_gstin;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_legal_name']")
    public WebElement input_legal;
    @FindBy(xpath = "//i[@class='fa fa-arrow-circle-right filldatabutton ']")
    public WebElement button_fillGlassPrescription;
    @FindBy(xpath = "//select[@id='invoice_inventory_order_r_glassesprescriptions_typeoflens']")
    public WebElement select_rightSideOfLensGlassPrescription;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_r_glassesprescriptions_ipd']")
    public WebElement select_rightSideOfIPDGlassPrescription;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_r_glassesprescriptions_size']")
    public WebElement select_rightSideOfSizeGlassPrescription;
    @FindBy(xpath = "//select[@id='invoice_inventory_order_r_glassesprescriptions_lensmaterial']")
    public WebElement select_rightSideLensOfMaterialGlassPrescription;
    @FindBy(xpath = "//select[@id='invoice_inventory_order_r_glassesprescriptions_lenstint']")
    public WebElement select_rightSideLensTintGlassPrescription;
    @FindBy(xpath = "//select[@id='invoice_inventory_order_r_glassesprescriptions_framematerial']")
    public WebElement select_rightSideFrameMaterialGlassPrescription;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_r_glassesprescriptions_dia']")
    public WebElement input_rightSideDiaGlassPrescription;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_r_glassesprescriptions_prismbase']")
    public WebElement input_rightSidePrismBaseGlassPrescription;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_r_glassesprescriptions_fittingheight']")
    public WebElement input_rightSideFittingHeightGlassPrescription;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_r_glassesprescriptions_comments']")
    public WebElement textarea_rightSideAdviceCommentGlassPrescription;
    @FindBy(xpath = "//div[@id='r-questionsforacceptance']//i[@title='Copy Left to Right']")
    public WebElement btn_leftToRightCopyGlassPrescription;
    @FindBy(xpath = "//input[@placeholder='Qty'][@type='number']")
    public List<WebElement> list_inputQty;

    @FindBy(xpath = "//strong[contains(text(),'Sales Order')]/parent::h5")
    public WebElement text_headerSalesOrder;

    @FindBy(xpath = "//div[@class='invoice-table-body']//tbody//td[2]")
    public List<WebElement> list_itemNameInTableCreateSalesOrder;

    @FindBy(xpath = "//thead[@class='invoice-table-head']//td")
    public List<WebElement> list_createSalesOrderTableHeader;
    @FindBy(xpath = "//tr[contains(@class,'tbody_medication')]")
    public List<WebElement> list_createSalesOrderTableRow;
    @FindBy(xpath = "//tr[contains(@class,'tbody_medication')]/td[10]/div[2]")
    public List<WebElement> list_discountSalesOrderTableRow;

    @FindBy(xpath = "//a[@title='Add Offer']//i[@class='fa fa-plus']")
    public WebElement button_addOffer;
    @FindBy(xpath = "//h4[normalize-space()='Show Offers']")
    public WebElement header_showOffer;
    @FindBy(xpath = "//input[@name='selected_offer']")
    public WebElement radio_selectOffer;
    @FindBy(xpath = "//form[@class='apply-offer-form']//td[3]")
    public WebElement text_offerCode;
    @FindBy(xpath = "//form[@class='apply-offer-form']//td[4]")
    public WebElement text_offerDiscountPercentage;
    @FindBy(xpath = "//form[@class='apply-offer-form']//td[5]")
    public WebElement text_offerValidTill;

    @FindBy(xpath = "//input[@value='Select Offer']")
    public WebElement button_selectOffer;
    @FindBy(xpath = "//label[@class='lbl_offer_detail']")
    public WebElement label_selectedOffer;
    @FindBy(xpath = "//b[text()='Taxable Amount']/parent::div/following-sibling::div/input")
    public WebElement text_totalTaxableAmount;
    @FindBy(xpath = "//b[text()='GST5']/parent::div/following-sibling::div/input[@type='text']")
    public WebElement text_totalGST5Amount;
    @FindBy(xpath = "//b[text()='GST18']/parent::div/following-sibling::div/input[@type='text']")
    public WebElement text_totalGST18Amount;
    @FindBy(xpath = "//b[text()='Gross Amount']/parent::div/following-sibling::div/input[@type='text']")
    public WebElement text_totalGrossAmount;
    @FindBy(xpath = "//b[text()='Discount']/parent::div/following-sibling::div/input[@type='text']")
    public WebElement text_totalDiscountAmount;
    @FindBy(xpath = "//b[text()='Offer']/parent::div/following-sibling::div/input[@type='text']")
    public WebElement text_totalOfferAmount;
    @FindBy(xpath = "//b[text()='Net Amount']/parent::div/following-sibling::div/input[@type='text']")
    public WebElement text_totalNetAmount;

    @FindBy(xpath = "//input[@id='delivery_pending']")
    public WebElement radioBtn_deliveryPending;
    @FindBy(xpath = "//input[@id='delivery_completed']")
    public WebElement radioBtn_deliveryCompleted;

    @FindBy(xpath="//button[contains(@class,'add_multiple_mop')]")
    public WebElement button_addPaymentAdvancePayment;

    @FindBy(xpath="//button[contains(@id,'add_payment_received')]")
    public WebElement button_addPaymentReceived;
    @FindBy(xpath="//select[contains(@id,'card_machine_master_id') or contains(@name,'card_machine_master_id')]")
    public WebElement select_cardMachineMop;
    @FindBy(xpath="//input[@placeholder='Transaction ID No.']")
    public WebElement input_transactionIdNo;
    @FindBy(xpath="//input[@placeholder='Batch No.']")
    public WebElement input_batchNoMop;
    @FindBy(xpath="//div[contains(@id,'mode_of_payment')][@class='error']")
    public WebElement label_errorModeOfPaymentAdvanceReceipt;
    @FindBy(xpath="//div[contains(@id,'amount')][@class='error']")
    public WebElement label_errorAmountAdvanceReceipt;

    @FindBy(xpath="//div[contains(@class,'invoice-workflow-timeline')]//label")
    public List<WebElement> list_labelOrderWorkFlowTimeline;
    @FindBy(xpath="//a[contains(@class,'undo-invoice-state')]")
    public WebElement button_undo;
    @FindBy(xpath="//a[@id='fitting']")
    public WebElement button_fittingFlow;
    @FindBy(xpath="//input[@value='Save']")
    public WebElement button_saveAdvance;

    @FindBy(xpath="//h4[text()='Item Details:']/parent::div/following-sibling::div//table//th")
    public List<WebElement> list_textHeaderRhsItemDetails;
    @FindBy(xpath="//h4[text()='Item Details:']/parent::div/following-sibling::div//table//tbody//tr")
    public List<WebElement> list_textRowRhsItemDetails;

    @FindBy(xpath="//b[contains(text(),'Taxable Amount')]/parent::div/following-sibling::div[1]")
    public WebElement text_taxableAmountItemDetails;
    @FindBy(xpath="//b[contains(text(),'GST18')]/parent::div/following-sibling::div[1]")
    public WebElement text_gst18ItemDetails;
    @FindBy(xpath="//b[contains(text(),'GST5')]/parent::div/following-sibling::div[1]")
    public WebElement text_gst5ItemDetails;
    @FindBy(xpath="//b[contains(text(),'Total Amt Incl. Tax ')]/parent::div/following-sibling::div[1]")
    public WebElement text_totalAmountIncTaxItemDetails;
    @FindBy(xpath="//b[contains(text(),'Discount')]/parent::div/following-sibling::div[1]")
    public WebElement text_discountItemDetails;
    @FindBy(xpath="//b[contains(text(),'Offer')]/parent::div/following-sibling::div[1]")
    public WebElement text_offerItemDetails;
    @FindBy(xpath="//b[contains(text(),'Net Amount')]/parent::div/following-sibling::div[1]")
    public WebElement text_netAmountItemDetails;
    @FindBy(xpath="//b[contains(text(),'Advance Paid')]/parent::div/following-sibling::div[1]")
    public WebElement text_advancePaidItemDetails;
    @FindBy(xpath="//b[contains(text(),'Remaining')]/parent::div/following-sibling::div[1]")
    public WebElement text_remainingItemDetails;
    @FindBy(xpath="//h5[text()='Review Order']")
    public WebElement header_reviewOrder;
    @FindBy(xpath="//label[@id='invoice_total_payment-error']")
    public WebElement label_receivePaymentError;
    @FindBy(xpath="//a[contains(@id,'invoice_preview')]")
    public WebElement button_order;
    @FindBy(xpath="//h4[text()='Optical Order History']")
    public WebElement text_opticalOrderHistory;
    @FindBy(xpath="//div[@id='freeInvoice']//div[contains(text(),'Legal Name')]/following-sibling::div[1]")
    public WebElement text_legalNameOpticalOrder;
    @FindBy(xpath="//div[@id='freeInvoice']//div[contains(text(),'Doctor')]/following-sibling::div[1]")
    public WebElement text_doctorNameOpticalOrder;
    @FindBy(xpath="//div[contains(@id,'freeInvoice')]//div[contains(text(),'Patient ID')]/following-sibling::div[1]")
    public WebElement text_patientIdOpticalOrder;
    @FindBy(xpath="//div[@id='freeInvoice']//div[contains(text(),'MR No')]/following-sibling::div[1]")
    public WebElement text_mrnNoOpticalOrder;
    @FindBy(xpath="//div[@id='freeInvoice']//div[contains(text(),'Patient Mobile')]/following-sibling::div[1]")
    public WebElement text_patientMobileNoOpticalOrder;
    @FindBy(xpath="//div[@id='freeInvoice']//div[contains(text(),'Ordered On')]/following-sibling::div[1]")
    public WebElement text_orderedOnOpticalOrder;
    @FindBy(xpath="//div[contains(text(),'Order Number')]/following-sibling::div[1]")
    public WebElement text_orderNoOpticalOrder;
    @FindBy(xpath="//div[@id='freeInvoice']//div[contains(text(),'GSTIN')]/following-sibling::div[1]")
    public WebElement text_gstNoOpticalOrder;
    @FindBy(xpath="//div[@id='freeInvoice']//div[contains(text(),'Est. Delivery')]/following-sibling::div[1]")
    public WebElement text_estimatedDeliveryOpticalOrder;
    @FindBy(xpath="//div[@id='freeInvoice']//div[contains(text(),'Created By')]/following-sibling::div[1]")
    public WebElement text_createdByOpticalOrder;
    @FindBy(xpath="//div[@id='freeInvoice']//div[contains(text(),'Current Status')]/following-sibling::div[1]")
    public WebElement text_currentStatusOpticalOrder;
    @FindBy(xpath="//b[text()='Description']/ancestor::table//th")
    public List<WebElement> list_textHeaderOpticalOrderItemDetails;
    @FindBy(xpath="//b[text()='Description']/ancestor::table//tbody//tr")
    public List<WebElement> list_textRowOpticalOrderItemDetails;
    @FindBy(xpath="//div[@id='freeInvoice']/following-sibling::div//a[contains(@href,'invoice/inventory_orders/print')]")
    public List<WebElement> list_buttonPrintOpticalOrderItemDetails;
    @FindBy(xpath="//button[normalize-space()='Close']")
    public WebElement button_closeOpticalOrder;
    @FindBy(xpath="//input[@id='payment_received']")
    public WebElement input_amountRefundedOpticalOrderDetails;
    @FindBy(id="inventory_cancel_reason")
    public WebElement input_cancellationReasonOpticalOrderDetails;
    @FindBy(id="cancel_reason_label")
    public WebElement input_cancelReasonLabelOpticalOrderDetails;
    @FindBy(xpath="//label[@id='accept-label']")
    public WebElement checkbox_allItemTakenBackOpticalOrderDetails;
    @FindBy(xpath="//button[@id='cancel_invoice']")
    public WebElement button_cancelOrderOpticalOrderDetails;
    @FindBy(xpath="//div[@class='ui-pnotify-text']")
    public WebElement text_notifyMsgCancelOrder;
    @FindBy(xpath="//a[contains(@href,'invoice/refund_payments')]")
    public WebElement button_refundPayment;
    @FindBy(xpath="//h4[normalize-space()='Refund Receipt']")
    public WebElement header_refundReceipt;
    @FindBy(xpath="//p[contains(text(),'Refunded a sum of')]")
    public WebElement text_msgInRefundReceipt;

    @FindBy(xpath="//p[contains(text(),'Refund Remark')]/b")
    public WebElement text_refundRemarkRefundReceipt;
    @FindBy(xpath="//button[normalize-space()='Print']")
    public WebElement button_printDropDownRefundReceipt;
    @FindBy(xpath="//a[contains(@href,'refund_payments/print')]")
    public WebElement button_printRefundReceipt;
    @FindBy(xpath="//a[@id='record-mail-html']")
    public WebElement button_emailRefundReceipt;

    @FindBy(xpath="//h4[text()='Warning']")
    public WebElement text_notifyMsgWarningVendorRate;
    @FindBy(xpath="//a[contains(@href,'print?order')]")
    public WebElement button_printOrder;
    @FindBy(xpath="//a[contains(@href,'mail') and contains(@href,'order')]")
    public WebElement button_emailOrder;
    @FindBy(xpath="//a[contains(@href,'print?invoice')]")
    public WebElement button_printInvoice;
    @FindBy(xpath="//a[contains(@href,'mail') and contains(@href,'invoice')]")
    public WebElement button_emailInvoice;
    @FindBy(xpath = " //input[@name='invoice_inventory_order[advance_payment_breakups_attributes][0][amount]']")
    public WebElement input_settleAmountInputBox;
    @FindBy(xpath = "//input[@class='unit_item_list_price modalRequest_input_style']")
    public List<WebElement> input_itemMRP;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_estimated_delivery_date']")
    public WebElement input_estimatedOrderDeliveryDate;
    @FindBy(xpath = "//a[contains(@class,'ui-state-default ui-state-highlight')]")
    public WebElement input_currentEstimatedOrderDeliveryDate;
    @FindBy(xpath = "//input[@id='grand_total_inc_discount']")
    public WebElement input_netAmount;
    @FindBy(xpath = "//thead[@class='inventory-table-head']//td")
    public List<WebElement> list_salesOrderTableHeaderList;
    @FindBy(xpath = "//ul[@id='select2-inventory_transaction_stock_receive_note_vendor_location_id-results']/li")
    public List<WebElement> list_selectVendorInStore;
    @FindBy(xpath = "//input[@class='select2-search__field']")
    public WebElement input_searchInputForSelectField;
    @FindBy(xpath = "//input[@id='lot_0_unit_checked']")
    public WebElement input_selectUnitItem;
    @FindBy(xpath = "//input[@onclick='validate_inventory_order_add_lot_unit_form()']")
    public WebElement button_saveChangesUnitLevel;

    @FindBy(xpath = "//input[@id='invoice_inventory_order_additional_discount_show']")
    public WebElement input_globalDiscountField;
    @FindBy(xpath = "//input[@value='Apply']")
    public WebElement input_globalDiscountApplyButton;
    @FindBy(xpath = "//input[@id='invoice_type_credit']")
    public WebElement radioButton_CreditOption;
    @FindBy(xpath = "//span[@title='Select Payer Type']")
    public WebElement option_PayerTypeOption;
    @FindBy(xpath = "//ul[@class='select2-results__options']/li")
    public List<WebElement> list_payerDropdown;
    @FindBy(xpath = "//span[@title='Select Contact']")
    public WebElement option_ContactSelectDropdown;
    @FindBy(xpath = "//span[@title='Select Insurance']")
    public WebElement option_insurancePayerTypeOption;
    @FindBy(xpath = "//span[@title='Select Corporate']")
    public WebElement option_CorporatePayerTypeOption;
    @FindBy(xpath = "//span[@title='Select Dispensary']")
    public WebElement option_dispensaryPayerTypeOption;
    @FindBy(xpath = "//input[@name='invoice_inventory_order[payment_pending_breakups_attributes][0][amount]']")
    public WebElement input_paymentPendingAmount;
    @FindBy(xpath = "//span[normalize-space()='Reports']")
    public WebElement button_reports;
    @FindBy(xpath = "//a[@class='settle_store_bills']")
    public WebElement button_settleBillReports;
    @FindBy(xpath = "//div[contains(text(),'Claim Processor:')]/following-sibling::div[1]")
    public WebElement text_claimProcessorName;
    @FindBy(xpath = "//div[contains(text(),'Order Type:')]/following-sibling::div[1]")
    public WebElement text_orderType;
    @FindBy(xpath = "//button[@class='btn btn-link btn-xs remove_payment_received']")
    public WebElement button_removePaymentReceived;
    @FindBy(xpath="//div[@id='freeInvoice']//div[6]")
    public WebElement text_patientIdInOpticalOrder;
    @FindBy(xpath = "(//div[contains(text(),'Claim Processor:')]/following-sibling::div[1])[2]")
    public WebElement text_claimProcessorNameInOpticalOrder;
    @FindBy(xpath="//table[@id='settle_invoices_table']/tbody/tr/td[4]")
    public List<WebElement> list_billNumberInSettle;
    @FindBy(xpath = "//table[@id='settle_invoices_table']/tbody/tr/td[4]/a")
    public List<WebElement> list_viewBill;
    @FindBy(xpath = "//a[@data-target='#invoice-detail-modal'][normalize-space()='Settle']")
    public WebElement button_settleOButton;
    @FindBy(xpath="//select[@class='form-control payment_received_breakups_mode_of_payment']")
    public WebElement select_modeOfPaymentInSettle;
    @FindBy(xpath = "//input[@name='settle_invoice[payment_received_breakups][0][amount_received]']")
    public WebElement input_amountReceivedInSettle;
    @FindBy(xpath = "//input[@value='Save']")
    public WebElement button_saveButtonInSettle;
    @FindBy(xpath = "//div[@id='free-invoice-inventory-modal']//button[@type='button'][normalize-space()='Close']")
    public WebElement button_closeButtonInOpticalOrder;
    @FindBy(xpath = "//div[@class='modal-header p7_10']//button[@type='button'][normalize-space()='×']")
    public WebElement button_closeButtonInInventoryPendingBills;
    @FindBy(xpath = "//a[@class='btn btn-primary btn-sm']")
    public WebElement button_placeOrderInSale;
    @FindBy(xpath="//div[contains(text(),'Payment Received :')]/following-sibling::div")
    public WebElement text_paymentReceivedDetailsInSale;
    @FindBy(xpath = "//input[@id='invoice_total_payment_remaining']")
    public WebElement text_balancePendingAmount;
    @FindBy(xpath = "//input[@name='invoice_inventory_order[payment_received_breakups_attributes][0][amount_received]']")
    public WebElement input_partialPaymentReceived;
    @FindBy(xpath = "//div[contains(text(),'Delivery date:')]/following-sibling::div[1]")
    public WebElement text_rhsDeliveryDate;






}
