package pages.commonElements.bills;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_Bills extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_Bills(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//button[contains(text(),'₹ Bills')]")
	public WebElement button_clickBills;

	@FindBy(xpath = "//ul[contains(@class,'dropdown-menu-right')][@aria-labelledby='invoice-print-dropdown']//li")
	public List<WebElement> list_billTypeSelection;

	// ***********Bill Creation Page****************//

	@FindBy(xpath = "//h4[contains(text(),'Bill')]")
	public WebElement text_billHeading;

    @FindBy(xpath = "//input[@name='invoice_opd[payment_pending_breakups_attributes][0][amount]']")
    public WebElement input_OPDamountPendingField;

	@FindBy(xpath = "//span[@title='Select Service']")
	public WebElement bill_clickService;

	@FindBy(xpath = "//ul[contains(@class,'select2-results__options--nested')]/li")
	public List<WebElement> bill_selectOption;

	// @FindBy(xpath = "//div[contains(@class,'invoice_service_item_row row mt5')]")
	@FindBy(xpath = "//div[@class='col-md-12']")
	public List<WebElement> bill_rowsOfServices;

	@FindBy(xpath = "//input[@class='select2-search__field'][@type=\"search\"]")
	public WebElement input_billService;

	// *********** select package****************//

	// click plus sign
	@FindBy(xpath = "//*[@id='opd-0-add-services']/i")
	public WebElement button_clickPlusButton;

	// click on new package
	@FindBy(xpath = "//a[normalize-space()='New Package']")
	public WebElement link_newPackage;

	@FindBy(xpath = "//span[@title='Select Package']")
	public WebElement bill_clickPackage;

	// ***********Remarks, Discount and comment****************//

	@FindBy(xpath = "//textarea[@id='invoice_opd_patient_comment']")
	public WebElement input_billRemarksComment;

	@FindBy(xpath = "//textarea[@id='invoice_opd_comments']")
	public WebElement input_billInternalComment;

	@FindBy(xpath = "//input[@id='discount_amount']")
	public WebElement input_additionalDiscount;

	@FindBy(xpath = "//textarea[@id='invoice_opd_additional_discount_comment']")
	public WebElement input_billDiscountComment;

	// ***********Mode of payment ****************//

	@FindBy(xpath = "//select[@class='form-control payment_received_breakups_mode_of_payment']")
	public WebElement select_modeOfPayment;

	@FindBy(xpath = "//select[contains(@class, 'payment_received_breakups_mode_of_payment')]/option")
	public List<WebElement> options_selectPayment;

	@FindBy(xpath = "//input[@value='Save Final Bill']")
	public WebElement button_clickSaveFinalBills;

    @FindBy(xpath = "//input[@value='Save as Draft']")
    public WebElement button_clickSaveDraftBills;

	@FindBy(xpath = "//button[text()='Close']")
	public WebElement button_closeBill;

	// ***********IPD Bills ****************//
	@FindBy(xpath = "//a[text()='New Cash Bill']")
	public WebElement button_cashBill;
	@FindBy(xpath = "//a[text()='New Draft Bill']")
	public WebElement button_draftBill;
	@FindBy(xpath = "//span[@title='Select Service']")
	public WebElement select_services;
	@FindBy(xpath = "//input[@value='Save as Draft']")
	public WebElement button_saveDraftBillButton;
	@FindBy(xpath = "//input[@id='invoice_total_payment_remaining']")
	public WebElement text_balanceRemainingAmount;
	@FindBy(xpath = "//input[@name='invoice_ipd[payment_pending_breakups_attributes][0][amount]']")
	public WebElement input_amountPending;

	// ***********OPD Bills ****************//
	@FindBy(xpath = "//input[@name='invoice_opd[payment_pending_breakups_attributes][0][amount]']")
	public WebElement input_amountPendingOpd;
   //*****************Sub package item level discount**************

    @FindBy(xpath = "//input[@class='select2-search__field']")
    public WebElement input_searchPackageOrServiceName;
    @FindBy(xpath = "//button[@class='btn btn-primary btn-xs add-new-service-package']")
    public WebElement button_clickIPDPlusActionButton;
    @FindBy(xpath = "//ul[contains(@id,'description-results')]/li[2]//following-sibling::ul/li")
    public List<WebElement> list_packageOrServiceNameUnderDescriptionField;
    @FindBy(xpath = "//button[@title='Add Service/Package/BOM']")
    public WebElement button_actionToAddNewServiceOrPackage;
    @FindBy(xpath = "//a[text()='New Package']")
    public WebElement button_newPackage;
    @FindBy(xpath = "//button[@class='btn btn-danger btn-xs remove_service']")
    public List<WebElement> button_deleteItemPackage;

    @FindBy(xpath = "//button[contains(@class,'remove_sub_package_discount')]")
    public List<WebElement> button_deleteSubPackageItemsDiscount;

    @FindBy(xpath = "//button[contains(@class,'remove_discount')]")
    public List<WebElement> button_deleteItemLevelDiscount;

    @FindBy(xpath = "//button[contains(@class,'add_sub_package_discount')]")
    public List<WebElement> button_addSubPackageItemsDiscount;
    @FindBy(xpath = "//input[contains(@id,'service_global_discount')]")
    public List<WebElement> input_globalDiscountAmount;
    @FindBy(xpath = "//select[contains(@id,'service_global_discount')]")
    public WebElement select_globalDiscountType;
    @FindBy(xpath = "//button[text()='Apply Discount']")
    public WebElement button_applyGlobalDiscount;
    @FindBy(xpath = "//button[@id='ipd1-apply-global-discount']")
    public WebElement button_applyGlobalDiscountForSecondPackage;

    @FindBy(xpath = "//span[contains(@id,'description-container')]")
    public List<WebElement> select_package;
    @FindBy(xpath = "//input[contains(@class,'i_quantity')]")
    public List<WebElement> input_Quantity;
    @FindBy(xpath = "//input[contains(@class,'i_unit_price')]")
    public List<WebElement> input_unitPrice;
    @FindBy(xpath = "//input[contains(@class,'i_price')]")
    public List<WebElement> input_grossPrice;
    @FindBy(xpath = "//table[@class='table table-bordered']/thead/tr/th")
    public List<WebElement> list_subPackageItemDiscountTableHeader;

    @FindBy(xpath = "//button[contains(@class,'add_item_discount')]")
    public List<WebElement> input_itemWiseDiscountButton;
    @FindBy(xpath = "//input[contains(@placeholder,'Net Amount')]")
    public List<WebElement> input_netAmount;
    @FindBy(xpath = "//div/b[text()='Gross Bill Total :']/parent::div/following-sibling::div/input")
    public WebElement text_grossBillTotal;
    @FindBy(xpath = "//div/b[text()='Total Discount on Items :']/parent::div/following-sibling::div/input")
    public WebElement text_totalDiscountOnItem;
    @FindBy(xpath = "//div/b[text()='Additional Discount on Bill :']/parent::div/following-sibling::div/input")
    public WebElement text_additionalDiscountOnBill;
    @FindBy(xpath = "//div/b[text()='Total Discount :']/parent::div/following-sibling::div/input")
    public WebElement text_totalDiscountOnBill;
    @FindBy(xpath = "//div/b[text()='Total of all discount :']/parent::div/following-sibling::div/input")
    public WebElement text_totalOfAllDiscounts;
    @FindBy(xpath = "//div/b[text()='Net Bill Total :']/parent::div/following-sibling::div/input")
    public WebElement text_netBillTotal;
    @FindBy(xpath = "//table[@class='table table-bordered']/thead/following-sibling::tbody/tr[@class='sub-package-tr']/td[3]")
    public List<WebElement> text_subpackageItemDescriptionNames;
    @FindBy(xpath = "//table[@class='table table-bordered']/thead/following-sibling::tbody/tr[@class='sub-package-tr']/td[4]")
    public List<WebElement> text_subPackageItemUnitCost;
    @FindBy(xpath = "//table[@class='table table-bordered']/thead/following-sibling::tbody/tr[@class='sub-package-tr']/td[5]")
    public List<WebElement> text_subPackageItemGrossPrice;
    @FindBy(xpath = "//table[@class='table table-bordered']/thead/following-sibling::tbody/tr[@class='sub-package-tr']/td[6]")
    public List<WebElement> text_subPackageItemNetPrice;
    @FindBy(xpath = "//input[contains(@class,'service_global_discount')]")
    public List<WebElement> list_subpackageLevelGlobalDiscountInputField;
    @FindBy(xpath = "//button[contains(@class,'apply-global-discount')]")
    public List<WebElement> list_subpackageLevelGlobalDiscountApplyButton;
    @FindBy(xpath = "//input[contains(@id,'sub_package_service_discount_show')]")
    public List<WebElement> list_subpackageItemLevelDiscountField;

    @FindBy(xpath = "//input[contains(@class,'form-control form-control-custom-input service_discount_show')]")
    public List<WebElement> list_mainDiscountFieldOnPackage;

    @FindBy(xpath = "//select[contains(@id,'sub_package_service_discount_type')]")
    public List<WebElement> list_subpackageItemLevelDiscountTypes;
    @FindBy(xpath = "//label[contains(@class,'lbl_sub_package_discount_amount lbl_sub_package_discount_amount')]")
    public List<WebElement> list_subpackageItemLevelDiscountAmount;

    @FindBy(xpath = "//label[@class='lbl_discount_amount']")
    public WebElement text_overallDiscountAmountOnPackage;

    @FindBy(xpath = "//input[contains(@id,'discount_reason')]")
    public List<WebElement> input_discountReason;

    @FindBy(xpath = "//input[contains(@class,'payment_received_breakups_amount ')]")
    public List<WebElement> input_amountReceivedField;

    @FindBy(xpath = "//tbody/tr/td[5]")
    public List<WebElement> text_subPackageItemDiscountOnBillPreview;

    @FindBy(xpath = "//b[text()='Discount: ']/parent::div/following-sibling::div[1]/span")
    public List<WebElement> List_overAllPackageItemDiscountOnBillPreview;

    @FindBy(xpath = "//b[text()='Gross Bill Total :']/parent::div/following-sibling::div")
    public WebElement text_grossBillTotalOnBillPreview;

    @FindBy(xpath = "//b[text()='Total Discount on Items :']/parent::div/following-sibling::div")
    public WebElement text_totalDiscountOnItemsOnBillPreview;
    @FindBy(xpath = "//b[text()='Total of all Discounts :']/parent::div/following-sibling::div")
    public WebElement text_totalOfAllDiscountOnBillPreview;
    @FindBy(xpath = "//b[text()='Payment Received :']/parent::div/following-sibling::div")
    public WebElement text_paymentReceivedOnBillPreview;

    @FindBy(xpath = "//b[text()='Payment Pending :']/parent::div/following-sibling::div[1]")
    public WebElement text_paymentPendingOnBillPreview;

    @FindBy(xpath = "//a[text()='Edit']")
    public WebElement button_editBill;

    @FindBy(xpath = "//a[text()='Edit Draft Bill']")
    public WebElement button_editDraftBill;


    @FindBy(xpath = "//button[@id='add_payment_received']")
    public WebElement link_addPayment;

    @FindBy(xpath = "//select[@class='form-control payment_received_breakups_mode_of_payment']")
    public List<WebElement> list_modeOfPayment;

    @FindBy(xpath = "//textarea[@id='invoice_ipd_comments']")
    public WebElement input_internalComments;

    @FindBy(xpath = "//a[text()=' Cancellation']")
    public WebElement button_cancelBill;

    @FindBy(xpath = "//a[text()='Remove']")
    public WebElement button_removeDraftBill;

    @FindBy(xpath = "//input[@id='refund_payment_reason']")
    public WebElement input_refundReason;

    @FindBy(xpath = "//select[@class='form-control mop_breakups_mode_of_payment']")
    public WebElement select_mopInRefundCancellationForm;

    @FindBy(xpath = "//input[@class='form-control mop_breakups_amount p3_10']")
    public WebElement input_cancellationAmount;

    @FindBy(xpath = "//input[@class='btn btn-success btn-refund-save']")
    public WebElement button_saveRefundReciept;

    @FindBy(xpath = "//button[text()='Add Credit Payment']")
    public WebElement button_addCreditPayment;

    @FindBy(xpath = "//input[contains(@class,'form-control payment_pending_breakups_amount')]")
    public List<WebElement> list_amountPendingField;

    @FindBy(xpath = "//label[@class='lbl_discount_amount']")
    public List<WebElement> list_overallDiscountOnPackage;

    @FindBy(xpath = "//select[@id='template_set_details_option']")
    public WebElement option_invoiceSets;

    @FindBy(xpath = "//button[text()='Confirm']")
    public WebElement button_confirmRemoveDraftBill;

    @FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-right']/li")
    public List<WebElement> list_templateBills;

    @FindBy(xpath = "//button[@id='apply-global-discount-1']")
    public WebElement button_receiptTemplateGlobalDiscount;

    @FindBy(xpath = "//input[@class='btn btn-success save-invoice']")
    public WebElement button_saveRecieptTemplate;

    @FindBy(xpath = "//button[text()='Confirm']")
    public WebElement button_confirmCancelCreditBill;

    @FindBy(xpath = "//input[@id='invoice_total_payment_remaining']")
    public WebElement input_pendingPayment;

    @FindBy(xpath = "//input[@name='invoice_opd[payment_pending_breakups_attributes][0][amount]']")
    public WebElement input_Payment;
    @FindBy(xpath = "//input[@id='global_discount']")
    public WebElement input_globalDiscountAmt;
    @FindBy(xpath = "//select[@id='global_discount_type']")
    public WebElement selectOption_globalDiscountType;
    @FindBy(xpath = "//button[contains(@class,'apply_global_discount')]")
    public WebElement applyGlobalDiscount_button;
    @FindBy(xpath = "//a[@class='add_bill_of_material']")
    public WebElement button_addBom;

    @FindBy(xpath = "//a[text()='New Consultation']")
    public WebElement button_newConsultation;

    @FindBy(xpath = "//label[@class='lbl_discount_amount']")
    public List<WebElement> list_text_overallDiscountAmountOnPackage;
    @FindBy(xpath = "//div/b[text()='Round (+/-) :']/parent::div/following-sibling::div/input")
    public WebElement text_roundDiscountOnItem;
    @FindBy(xpath = "//input[contains(@class,'payment_received_breakups_amount ')]")
    public WebElement input_enteredAmountReceivedField;

    @FindBy(xpath = "//*[@id='opd-0-add-services']/i")
    public WebElement button_clickOPDPlusActionButton;

    @FindBy(xpath = "//textarea[@id='invoice_opd_comments']")
    public WebElement input_internalCommentsInOPD;

    @FindBy(xpath = "//label[@for='invoice_type_credit']")
    public WebElement radioButton_creditBillType;

    @FindBy(xpath = "//select[@id='invoice_opd_services_0_description']")
    public WebElement select_packageName;

    @FindBy(xpath = "//select[@id='invoice_opd_services_1_description']")
    public WebElement select_secondPackageName;

    @FindBy(xpath = "//input[@name='invoice_opd[payment_pending_breakups_attributes][0][amount]']")
    public WebElement input_amountPendingFieldInOPD;

    @FindBy(xpath = "//button[@id='opd1-apply-global-discount']")
    public WebElement button_applyGlobalDiscountForSecondPackageInOPD;

    @FindBy(xpath = "//select[@id='invoice_opd_contact_group_id']")
    public WebElement select_contactGroup;


    @FindBy(xpath = "//select[@id='invoice_opd_payer_master_id']")
    public WebElement select_payer;

    @FindBy(xpath = "//span[@id='select2-invoice_opd_contact_group_id-container']")
    public WebElement select_contactFiled;


    @FindBy(xpath = "//span[@id='select2-invoice_opd_payer_master_id-container']")
    public WebElement select_payerField;
    @FindBy(xpath = "//ul[@id='select2-invoice_opd_contact_group_id-results']/li")
    public List<WebElement> list_contactGroup;

    @FindBy(xpath = "//ul[@id='select2-invoice_opd_payer_master_id-results']/li")
    public List<WebElement> list_payerNames;
    @FindBy(xpath = "//ul[@aria-labelledby='invoice-print-dropdown']//a[text()='New Bill']")
    public WebElement button_newBill;

    @FindBy(xpath = "//ul[@aria-labelledby='invoice-print-dropdown']//a[text()='New Draft Bill']")
    public WebElement button_newDraftBill;
    @FindBy(xpath = "//ul[@aria-labelledby='invoice-print-dropdown']//a[contains(text(),'Advance Receipt')]")
    public WebElement button_advanceReceiptBill;
    @FindBy(xpath = "//span[text()='Previous Bills']/parent::li/following-sibling::li//a[contains(text(),'at')]")
    public List<WebElement> list_previousBills;
    @FindBy(xpath = "//a[@id='refund_ins_invoice_path']")
    public WebElement button_refundBill;
    @FindBy(xpath = "//a[@id='draft_to_final_invoice']")
    public WebElement button_convertToFinalBill;
    @FindBy(xpath = "//table[@id='advancePayments-appointment-overview-tab']//a[contains(@href,'invoice/advance')]")
    public List<WebElement> list_previousAdvanceReceipts;
    @FindBy(xpath = "//a[contains(text(),'Settle Bills')]")
    public WebElement button_settleBills;
    @FindBy(xpath = "//td[contains(@class,'amount-pending')]//a[contains(@href,'invoices/settle_invoice')]")
    public WebElement link_settleAmountPending;
    @FindBy(xpath = "//a[contains(@href,'invoice/advance_payments') and contains(@class,'btn')]")
    public List<WebElement> list_previousAdvanceReceiptsIpd;
    @FindBy(xpath = "//b[contains(text(),'Bill Number')]/parent::div")
    public WebElement text_billNumber;


    @FindBy(xpath = "//a[text()=' Refund']")
    public WebElement button_refundButton;

    @FindBy(xpath = "//table[@class='table table-bordered']//td[1]")
    public WebElement text_refundId;






}
