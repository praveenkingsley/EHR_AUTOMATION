package pages.store.PharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Sale extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_Sale(WebDriver driver) {
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

    @FindBy(xpath = "//a[contains(text(),'Place')]")
    public WebElement button_PlaceOrder;

    @FindBy(xpath = "(//div[@class='col-lg-2 font_size'])[6]")
    public WebElement text_Patient;
    @FindBy(xpath = "(//div[contains(@class,'col-sm-5 font_size')])[1]")
    public  WebElement text_TaxableAmount;

    @FindBy(xpath = "(//div[contains(@class,'col-sm-5 font_size')])[2]")
    public  WebElement text_TaxAmount;

    @FindBy(xpath = "(//div[contains(@class,'col-sm-5 font_size')])[3]")
    public  WebElement text_TotalAmount;

    @FindBy(xpath = "(//div[contains(@class,'col-sm-5 font_size')])[4]")
    public  WebElement text_PaymentReceived;
    @FindBy(xpath = "//table[@class='table table table-striped table-bordered']//td[2]")
    public WebElement text_Quantity;
    @FindBy(xpath = "//table[@class='table table table-striped table-bordered']//td[5]")
    public WebElement text_SellingPrice;
    @FindBy(xpath = "//button[@class='btn btn-info btn-sm dropdown-toggle ']")
    public WebElement button_filterDropdownButton;
    @FindBy(xpath = "//a[text()='This Month']")
    public WebElement text_thisMonthFilter;
    @FindBy(xpath = "//a[text()='Today']")
    public WebElement text_todayFilter;
    @FindBy(xpath = "//tbody[@class='inventory-table-body']//tr//td[3]")
    public List<WebElement> list_deliveryDateColumnList;
    @FindBy(xpath = "//div[@class='date_range_field']")
    public WebElement dropdown_dateRangeFieldDropdown;
    @FindBy(xpath = "//a[contains(text(),'Cancel Bill')]")
    public WebElement button_cancelBillInSale;
    @FindBy(xpath = "(//a[contains(text(),'Cancel Bill')])[2]")
    public WebElement button_cancelBillInPharmacyBill;
    @FindBy(xpath = "//h4[text()='Pharmacy Bill']")
    public WebElement header_pharmacyBillHeader;
    @FindBy(xpath = "//h4[text()='Optical Bill']")
    public WebElement header_opticalBillHeader;
    @FindBy(xpath = "(//a[@class='btn btn-xs btn-info'])")
    public WebElement button_emailSaleTransactionButton;
    @FindBy(xpath = "//input[@name ='email_name_email-receiver-input']")
    public WebElement input_emailAddressInComposeEmail;

    @FindBy(xpath = "//span[@class='email-ids']")
    public WebElement span_emailIdExistInComposeEmail;

    @FindBy(xpath = "//input[@id='mail_record_tracker_subject']")
    public WebElement input_emailSubjectInComposeEmail;
    @FindBy(xpath = "//input[@class='btn btn-primary submit_btn']")
    public WebElement input_previewButtonInComposeEmail;
    @FindBy(xpath = "//a[text()=' Send Email ']")
    public WebElement input_sendEmailButtonInComposeEmail;
    @FindBy(xpath = "//div[@id='freeInvoiceEdit']/div[1]/div")
    public List<WebElement> list_keysAndValuesInViewOrderList;
    @FindBy(xpath = "//h4[text()='Item Details:']")
    public WebElement header_itemDetailsSection;
    @FindBy(xpath = "//table[@class='table table table-striped table-bordered']//thead//th")
    public List<WebElement> list_itemDetailsTableHeaderList;
    @FindBy(xpath = "//table[@class='table table table-striped table-bordered']//tbody//td")
    public List<WebElement> list_itemDetailsTableDataList;

    @FindBy(xpath = "//b[text()='Taxable Amount :']/parent::div/following-sibling::div")
    public WebElement text_taxableAmountValue;
    @FindBy(xpath = "//b[text()='SGST2.5 :']/parent::div/following-sibling::div[1]")
    public WebElement text_sgstAmountValue;
    @FindBy(xpath = "//b[text()='CGST2.5 :']/parent::div/following-sibling::div[1]")
    public WebElement text_cgstAmountValue;
    @FindBy(xpath = "//b[text()='Total Amt Incl. Tax :']/parent::div/following-sibling::div[1]")
    public WebElement text_totalAmtIncAmountValue;
    @FindBy(xpath = "//b[text()='Discount :']/parent::div/following-sibling::div[1]")
    public WebElement text_discountValue;
    @FindBy(xpath = "//b[text()='Offer :']/parent::div/following-sibling::div[1]")
    public WebElement text_offerValue;
    @FindBy(xpath = "//b[text()='Total of all discounts :']/parent::div/following-sibling::div[1]")
    public WebElement text_totalOfDiscountValue;
    @FindBy(xpath = "//b[contains(text(),'Net Amount')]/parent::div/following-sibling::div[1]")
    public WebElement text_netAmountValue;
    @FindBy(xpath = " //div[contains(text(),'Received from Advance :')]/following-sibling::div[1]")
    public WebElement text_receiveFromAdvanceValue;
    @FindBy(xpath = "//div[contains(text(),'Payment Received :')]/following-sibling::div[1]")
    public WebElement text_paymentReceiveValue;

    @FindBy(xpath = " //div[contains(text(),'Received from Advance :')]/following-sibling::div[1]//a")
    public WebElement text_receiveFromAdvanceDetailsButtonInViewSale;
    @FindBy(xpath = "//div[contains(text(),'Payment Received :')]/following-sibling::div[1]//a")
    public WebElement text_paymentReceiveValueDetailsButtonInViewSale;

    @FindBy(xpath = "//b[text()='PATIENT ALL TRANSACTIONS']")
    public WebElement text_patientAllTransactionHeader;
    @FindBy(xpath = "//span[contains(text(),'Advance Payment (Remaining Adv. Amt.)')]")
    public WebElement text_advancePaymentSectionHeader;
    @FindBy(xpath = "//span[contains(text(),'Advance Payment (Remaining Adv. Amt.)')]/label")
    public WebElement text_advancePaymentRemainingAmount;
    @FindBy(xpath = "//h4[text()='Pharmacy Bill']")
    public WebElement text_previewBillPharmacy;
    @FindBy(xpath = "//h4[text()='Optical Bill']")
    public WebElement text_previewBillOptical;

    @FindBy(xpath = "//div[@class='row mt10 mb5 f-14 summary']/div")
    public List<WebElement> list_keysAndValuesInPharmacyBillList;

    @FindBy(xpath = "(//h4[text()='Item Details:'])[2]")
    public WebElement header_itemDetailsSectionInPharmacyBill;

    @FindBy(xpath = "//div[@class='row mt10 mb5 f-14']/table/thead/tr/th")
    public List<WebElement> list_keysOfItemDetailsInPharmacyBillList;
    @FindBy(xpath = "//div[@class='row mt10 mb5 f-14']/table/tbody/tr/td")
    public List<WebElement> list_valueOfItemDetailsInPharmacyBillList;
    @FindBy(xpath = "//table[@class='row mb5 f-14 text-left']//tr/td[1]/b")
    public List<WebElement> list_labelOfItemDetailsInPharmacyBillList;
    @FindBy(xpath = " //table[@class='row mb5 f-14 text-left']//tr/td[2]")
    public List<WebElement> list_valueOfLabelOfItemDetailsInPharmacyBillList;
    @FindBy(xpath = " //b[text()='Payment Received :']/parent::td/following-sibling::td[2]//a")
    public WebElement button_paymentReceivedDetailsButton;
    @FindBy(xpath = "//b[text()='Received from Advance :']/parent::td/following-sibling::td[2]//a")
    public WebElement button_receiveFromAdvanceDetailsButton;

    @FindBy(xpath = "//h4[text()='RECEIVED FROM ADVANCE']")
    public WebElement header_receiveAdvanceTemplateHeader;
    @FindBy(xpath = "//div[@class='modal-header p7_10']/following-sibling::div//table//thead//th")
    public List<WebElement> list_keysInReceivedAdvanceViewTemplateList;
    @FindBy(xpath = "//div[@class='modal-header p7_10']/following-sibling::div//table//tbody//td")
    public List<WebElement> list_valueInReceivedAdvanceViewTemplateList;
    @FindBy(xpath = "//div[@id='inventory-invoice-cancellation-modal']//button[text()='×']")
    public WebElement button_closeByXButton;

    @FindBy(xpath = "//h4[text()='RECEIVED FROM ADVANCE']/parent::div/button")
    public WebElement button_closeByXButtonInReceiveAdvance;
    @FindBy(xpath = "//h4[text()='AMOUNT SETTLED']/parent::div/button")
    public WebElement button_closeByXButtonInAmountSettled;
    @FindBy(xpath = "//ul[@aria-labelledby='summary-print-dropdown']/li")
    public WebElement list_printDropdownFirstOption;
    @FindBy(xpath = "//div[@class='btn-group print-receipt-buttons open']//ul[@aria-labelledby='summary-print-dropdown']/li")
    public WebElement list_printDropdownFirstOptionInAdvanceReceipt;

    @FindBy(xpath = "//h4[text()='Advance MOP Receipts']/parent::div/button")
    public WebElement button_closeByXButtonInAdvanceMopReceipts;
    @FindBy(xpath = "//h4[text()='Refund MOP Receipts']/parent::div/button")
    public WebElement button_closeByXButtonInRefundMopReceipts;
    @FindBy(xpath = "//h4[text()='AMOUNT SETTLED']")
    public WebElement header_paymentReceivedViewTemplateHeader;
    @FindBy(xpath = "//h4[text()='Pharmacy Bill History']")
    public WebElement header_pharmacyBillHistoryHeader;
    @FindBy(xpath = "//h4[text()='Optical Bill History']")
    public WebElement header_opticalBillHistoryHeader;

    @FindBy(xpath = "//div[@class='row text-center']/div/div[1]")
    public WebElement text_createdLabelInHistory;
    @FindBy(xpath = "//div[@class='row text-center']/div[2]")
    public WebElement text_createdByAndOnLabelInHistory;
    @FindBy(xpath = "//h4[text()='Advance Payment']")
    public WebElement header_advancePaymentSectionHeaderInHistory;
    @FindBy(xpath = "//h4[text()='Payment Received']")
    public WebElement header_paymentReceivedSectionHeaderInHistory;

    @FindBy(xpath = "(//div[@class='show-payment-received text-center'])[1]//table//thead//tr/th[1]")
    public WebElement list_paidByHeaderAdvancePaymentSectionInHistoryList;
    @FindBy(xpath = "(//div[@class='show-payment-received text-center'])[1]//table//tbody//tr/td[1]")
    public WebElement list_paidByValueAdvancePaymentSectionInHistoryList;
    @FindBy(xpath = "(//div[@class='show-payment-received text-center'])[1]//table//thead//tr/th[2]")
    public WebElement list_amountHeaderAdvancePaymentSectionInHistoryList;
    @FindBy(xpath = "(//div[@class='show-payment-received text-center'])[1]//table//tbody//tr/td[2]")
    public WebElement list_amountValueAdvancePaymentSectionInHistoryList;

    @FindBy(xpath = "//h4[text()='Payment Received']/parent::div/parent::div/following-sibling::div//thead//th[1]")
    public WebElement list_paidByPaymentReceivedSectionInHistoryList;
    @FindBy(xpath = "//h4[text()='Payment Received']/parent::div/parent::div/following-sibling::div//tbody//td[1]")
    public WebElement list_paidByValuePaymentReceivedSectionInHistoryList;
    @FindBy(xpath = "//h4[text()='Payment Received']/parent::div/parent::div/following-sibling::div//thead//th[1]")
    public WebElement list_amountHeaderPaymentReceivedSectionInHistoryList;
    @FindBy(xpath = "//h4[text()='Payment Received']/parent::div/parent::div/following-sibling::div//tbody//td[2]")
    public WebElement list_amountHeaderValuePaymentReceivedSectionInHistoryList;

    @FindBy(xpath = "//a[@class='btn btn-xs btn-default']")
    public List<WebElement> list_advancePaymentsList;
    @FindBy(xpath = "(//a[@class='btn btn-xs btn-default'])[1]")
    public WebElement text_advancePaymentFirst;
    @FindBy(xpath = "(//a[@class='btn btn-xs btn-default'])[2]")
    public WebElement text_advancePaymentSecond;

    @FindBy(xpath = "//h4[text()='Advance Receipt']")
    public WebElement header_AdvanceReceiptTemplateHeader;
    @FindBy(xpath = " //div[@class='row advance-info mb20']/div/div")
    public List<WebElement> list_advanceReceiptKeysAndValueList;
    @FindBy(xpath = " //div[@class='row advance-info mb20']/div/div[2]//a")
    public WebElement button_advanceReceivedDetailsButtonInViewCreatedAdvance;
    @FindBy(xpath = "//div[@class='row advance-breakup']/div[1]")
    public WebElement text_advanceHistoryLabel;
    @FindBy(xpath = "//div[@class='row advance-breakup']/div[2]/b")
    public WebElement text_advanceRemainingLabelInAdvanceReceipt;
    @FindBy(xpath = "//table[@class='table table-bordered']/thead/tr/th")
    public List<WebElement> list_keysAdvanceReceiptHeaderList;
    @FindBy(xpath = "//table[@class='table table-bordered']/tbody/tr/td")
    public List<WebElement> list_valueAdvanceReceiptHeaderList;
    @FindBy(xpath = "//div[contains(text(),'Order Number:')]/following-sibling::div[1]")
    public WebElement text_orderNumber;

    @FindBy(xpath = "//h4[text()='Advance MOP Receipts']/parent::div/following-sibling::div//thead//th")
    public List<WebElement> list_keysAdvanceMOPReceiptHeaderList;
    @FindBy(xpath = "//h4[text()='Advance MOP Receipts']/parent::div/following-sibling::div//tbody//td[1]")
    public List<WebElement> list_noValueAdvanceMOPReceiptList;
    @FindBy(xpath = "//h4[text()='Advance MOP Receipts']/parent::div/following-sibling::div//tbody//td[2]")
    public List<WebElement> list_modeOfPaymentValueAdvanceReceiptList;
    @FindBy(xpath = "//h4[text()='Advance MOP Receipts']/parent::div/following-sibling::div//tbody//td[3]")
    public List<WebElement> list_advanceMopIDValueAdvanceReceiptHeaderList;
    @FindBy(xpath = "//h4[text()='Advance MOP Receipts']/parent::div/following-sibling::div//tbody//td[4]")
    public List<WebElement> list_amountValueAdvanceReceiptHeaderList;

    @FindBy(xpath = "//div[@class='btn-group print-receipt-buttons']/button")
    public WebElement button_printAdvanceReceiptTemplateButton;
    @FindBy(xpath = "//div[@class='btn-group print-receipt-buttons open']//a[@id='record-mail-html']")
    public WebElement button_emailAdvanceReceiptTemplateButton;
    @FindBy(xpath = "//a[@id='refund_ins_advance_path']")
    public WebElement button_refundAdvanceReceiptTemplateButton;
    @FindBy(xpath = "//strong[text()='Advance Payment Cancelled']")
    public WebElement text_advanceCancelMessage;
    @FindBy(xpath = "//strong[text()='Bill Refunded']")
    public WebElement text_billRefundedLabel;
    @FindBy(xpath = "//strong[text()='Payment Refunded ']")
    public WebElement text_paymentRefundedLabel;
    @FindBy(xpath = "//strong[text()='Refund Remarks: ']")
    public WebElement text_refundRemarkLabel;

    @FindBy(xpath = "//span[contains(text(),'Refund Payment :')]")
    public WebElement text_refundPaymentSectionHeader;
    @FindBy(xpath = "//span[contains(text(),'Refund Payment :')]/parent::div/following-sibling::div/a")
    public WebElement text_firstRefundPayment;
    @FindBy(xpath = "(//span[contains(text(),'Refund Payment :')]/parent::div/following-sibling::div/a)[2]")
    public WebElement text_secondRefundPayment;

    @FindBy(xpath = "//h4[text()='Refund Receipt']")
    public WebElement header_refundReceiptViewTemplate;
    @FindBy(xpath = "//div[@class='row refund-info mb20']//p[1]")
    public WebElement text_refundReceiptTemplateMessage;
    @FindBy(xpath = "//div[@class='row refund-info mb20']//p[2]")
    public WebElement text_refundReceiptTemplateRemark;
    @FindBy(xpath = "//div[@class='row refund-info mb20']/parent::div/following-sibling::div//button[@class='btn btn-info btn-sm full_print dropdown-toggle']")
    public WebElement button_printButtonInRefundTemplate;
    @FindBy(xpath = "//div[@class='row refund-info mb20']/parent::div/following-sibling::div/div/a[@id='record-mail-html']")
    public WebElement button_emailButtonInRefundTemplate;

    @FindBy(xpath = "//button[text()='Add Payment']")
    public WebElement button_addPaymentButton;
    @FindBy(xpath = "//input[@id='dummy_payment_date']")
    public WebElement input_advanceDate;
    @FindBy(xpath = "//input[@id='dummy_payment_time']")
    public WebElement input_advanceTime;
    @FindBy(xpath = "//input[@id='refund_payment_dummy_payment_time']")
    public WebElement input_refundDate;
    @FindBy(xpath = "//input[@id='refund_payment_dummy_payment_time']")
    public WebElement input_refundTime;
    @FindBy(xpath = "//input[@id='refund_payment_reason']")
    public WebElement input_refundReason;
    @FindBy(xpath = "//h4[text()='Advance MOP Receipts']")
    public WebElement header_advanceMopReceiptsViewTemplate;
    @FindBy(xpath = "//select[contains(@name,'refund_payment[mop_breakups_attributes][0][mode_of_payment]')]")
    public WebElement select_modeOfPaymentInRefund;
    @FindBy(xpath = "//input[contains(@name,'refund_payment[mop_breakups_attributes][0][amount]')]")
    public WebElement input_refundAmount;
    @FindBy(xpath = "//b[text()='Total Amount Received: ']/following-sibling::label")
    public WebElement label_totalAmountReceivedLabelInAdvanceTemplate;

    @FindBy(xpath = "(//a[@class='btn btn-success'])[1]")
    public WebElement button_printA4SizeButton;
    @FindBy(xpath = "(//a[@class='btn btn-success'])[2]")
    public WebElement button_printA5SizeButton;
    @FindBy(xpath = "//form[@id='new_refund_payment']//b[text()='Adv. Receipt No.: ']/parent::div")
    public WebElement label_advanceIdLabelInRefund;
    @FindBy(xpath = "//form[@id='new_refund_payment']//b[text()='Remaining Amount: ']/parent::div")
    public WebElement label_remainingAmountLabelInRefund;

    @FindBy(xpath = "//input[@id='inventory_transaction_return_note']")
    public WebElement input_returnNoteTextBox;
    @FindBy(xpath = "//select[@name='inventory_transaction_return[mop_breakups_attributes][0][mode_of_payment]']")
    public WebElement input_modeOfPaymentInReturnTemplate;
    @FindBy(xpath = "//input[@id='inventory_transaction_return_total_cost']")
    public WebElement input_grossReturnAmountInReturnTemplate;
    @FindBy(xpath = "//input[@id='inventory_transaction_return_return_date']")
    public WebElement input_returnDate;
    @FindBy(xpath = "//input[@id='inventory_transaction_return_return_time']")
    public WebElement input_returnTime;
    @FindBy(xpath = "//strong[text()='Note']")
    public WebElement strong_noteLabel;
    @FindBy(xpath = "//strong[text()='Return Date']")
    public WebElement strong_returnDateLabel;
    @FindBy(xpath = "//strong[text()='Return Time']")
    public WebElement strong_returnTimeLabel;
    @FindBy(xpath = "//b[text()='Mode of Payment (for refunds)']")
    public WebElement label_modeOfPaymentLabel;

    @FindBy(xpath = " //input[@name= 'inventory_transaction_return[mop_breakups_attributes][0][return_amount]']")
    public WebElement input_refundAmountBox;
    @FindBy(xpath = "//b[text()='Gross Return Amt.']")
    public WebElement label_grossAmountLabel;
    @FindBy(xpath = "//b[text()='Return charges']")
    public WebElement label_returnChargesLabel;
    @FindBy(xpath = "//b[text()='Net Return Value']")
    public WebElement label_netReturnValueLabel;
    @FindBy(xpath = "(//h4[text()='Item Details:'])[2]")
    public WebElement label_itemDetailsInReturn;
    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item text-center']//th")
    public List<WebElement> list_itemDetailsTableHeaderListInReturn;
    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item text-center']//td[1]")
    public WebElement list_slNoInReturn;
    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item text-center']//td[2]")
    public WebElement list_descriptionInReturn;
    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item text-center']//td[3]/input")
    public WebElement input_billedQty;
    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item text-center']//td[4]/input")
    public WebElement input_returnableQty;
    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item text-center']//td[8]/div[1]")
    public WebElement text_uPriceWithTaxInReturn;
    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item text-center']//td[9]")
    public WebElement text_taxInReturn;
    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item text-center']//td[10]")
    public WebElement text_discountInReturn;
    @FindBy(xpath = "//table[@class='table table-bordered table-striped transfer-transaction-log-item text-center']//td[11]")
    public WebElement text_returnAmtInReturn;
    @FindBy(xpath = "//input[@id='inventory_transaction_return_return_charges']")
    public WebElement input_returnCharges;
    @FindBy(xpath = "//input[@id='inventory_transaction_return_return_amount']")
    public WebElement input_returnNetReturnValue;
    @FindBy(xpath = "//input[@id='inventory_transaction_return_items_attributes_0_stock']")
    public WebElement input_returnQty;
    @FindBy(xpath = "//label[@id='inventory_transaction_return_items_attributes_0_stock-error']")
    public WebElement label_returnQtyError;
    @FindBy(xpath = "//input[@class='item_remark']")
    public WebElement input_returnItemRemark;
    @FindBy(xpath = "//h4[text()='Pharmacy Return Bill']")
    public WebElement header_pharmacyReturnBill;
    @FindBy(xpath = "//h4[text()='Optical Return Bill']")
    public WebElement header_opticalReturnBill;

    @FindBy(xpath = "(//button[@title='Receive Items'])[1]")
    public WebElement button_returnDisabledButton;
    @FindBy(xpath = "//span[text()='Previous Bills']/parent::li/following-sibling::li[2]")
    public WebElement text_returnBills;
    @FindBy(xpath = "//div[@class='modal-body pharmacy-request-log']/following-sibling::div//a[1]")
    public WebElement button_printA4ButtonInReturn;
    @FindBy(xpath = "//div[@class='modal-body pharmacy-request-log']/following-sibling::div//a[2]")
    public WebElement button_printA5ButtonInReturn;
    @FindBy(xpath = "//h4[text()='Pharmacy Bill Details']")
    public WebElement header_pharmacyBillDetailsHeader;
    @FindBy(xpath = "//h4[text()='Optical Bill Details']")
    public WebElement header_opticalBillDetailsHeader;
    @FindBy(xpath = "//label[@id='accept-label']/span")
    public WebElement checkbox_acceptCheckBox;
    @FindBy(xpath = "//select[@name='refund_mop_breakups[0][mop]']")
    public WebElement select_modeOfPaymentInCancel;
    @FindBy(xpath = "//input[@name='refund_mop_breakups[0][amount]']")
    public WebElement input_amountReceivedInCancel;
    @FindBy(xpath = "//input[@id='inventory_cancel_reason']")
    public WebElement input_cancelReason;
    @FindBy(xpath = "//button[text()='Cancel Bill']")
    public WebElement button_cancelBillInCancel;
    @FindBy(xpath = "//button[text()=' Yes ']")
    public WebElement button_cancelBillYesInCancel;

    @FindBy(xpath = "//b[text()='Advance No: ']/following-sibling::span[1]")
    public WebElement text_advanceIdInSalesOrderOptical;
    @FindBy(xpath = "//div[contains(text(),'Bill Number:')]/following-sibling::div[1]")
    public WebElement text_rhsBillNumber;

    @FindBy(xpath="//input[@id='invoice_type_credit']")
    public WebElement radio_creditBill;
    @FindBy(xpath="//span[@id='select2-invoice_inventory_order_contact_group_id-container']")
    public WebElement input_contactGroupFieldUnderSaleOrder;
    @FindBy(xpath="//span[@id='select2-invoice_inventory_order_payer_master_id-container']")
    public WebElement input_payerNameFieldUnderSaleOrder;
    @FindBy(xpath="//span[@id='select2-invoice_inventory_order_corporate_id-container']")
    public WebElement input_corporateFieldUnderSalesOrder;
    @FindBy(xpath="//span[@id='select2-invoice_inventory_order_insurer_id-container']")
    public WebElement input_insurerFieldUnderSalesOrder;

    @FindBy(xpath="//input[@id='invoice_inventory_order_tpa_name']")
    public WebElement input_tpaNameFieldUnderSalesOrder;
    @FindBy(xpath="//span[@id='select2-invoice_inventory_order_dispensary_id-container']")
    public WebElement input_dispensaryFieldUnderSalesOrder;

    @FindBy(xpath="//input[@id='invoice_inventory_order_insurance_name']")
    public WebElement input_insuranceFieldUnderSalesOrder;

    @FindBy(xpath="//button[@class='btn btn-link btn-xs remove_payment_received']")
    public WebElement button_closeReceivedDetailsSection;

    @FindBy(xpath="//input[@id='invoice_total_payment_remaining']")
    public WebElement input_balancePendingAmountFieldUnderSalesOrder;

    @FindBy(xpath="//input[@class='form-control payment_pending_breakups_amount p3_10']")
    public WebElement input_paymentPendingAmountFieldUnderSalesOrder;

    @FindBy(xpath="//span[@id='select2-invoice_inventory_order_insurance_id-container']")
    public WebElement input_insuranceFieldUnderSalesOrderForTpaContact;
    @FindBy(xpath="//div[contains(text(),'Return Receipt Id')]/following-sibling::div")
    public WebElement text_refundId;





}
