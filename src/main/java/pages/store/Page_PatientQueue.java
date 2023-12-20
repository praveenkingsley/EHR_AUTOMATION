package pages.store;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_PatientQueue extends TestBase {

    public Page_PatientQueue(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//span[@class='title-pres']")
    public WebElement title_glassPrescriptionTitle;

    @FindAll
            ({
                    @FindBy(xpath = "//div[@class='prescription-information']//div[text()='R/OD']"),
                    @FindBy(xpath = "//div[text()='R/OD']"),
            })
    public WebElement text_glassAdvisedROD;
    @FindAll
            ({
                    @FindBy(xpath = "//div[@class='prescription-information']//div[text()='L/OS']"),
                    @FindBy(xpath = "//div[text()='L/OS']"),
            })
    public WebElement text_glassAdvisedLOS;

    @FindBy(xpath = "//table[@class='mb3 table-bordered']/thead//th")
    public List<WebElement> list_advisedGlassTableHeader;
    @FindBy(xpath = "//table[@class='mb3 table-bordered']/tbody//td")
    public List<WebElement> list_advisedGlassTableValue;
    @FindBy(xpath = "//div[@class='prescription-information']//table[@class='mb3 table-bordered']/thead//th")
    public List<WebElement> list_advisedGlassTableHeaderInSalesOrder;
    @FindBy(xpath = "//div[@class='prescription-information']//table[@class='mb3 table-bordered']/tbody//td")
    public List<WebElement> list_advisedGlassTableValueInSalesOrder;
    @FindBy(xpath = "//b[text()='Frame Material ']/parent::span")
    public List<WebElement> glassPrescription_FrameMaterialDetails;
    @FindBy(xpath = "//b[text()='IPD ']/parent::span")
    public List<WebElement> glassPrescription_IpdDetails;
    @FindBy(xpath = " //b[text()='Lens Material ']/parent::span")
    public List<WebElement> glassPrescription_LensMaterialDetails;
    @FindBy(xpath = "//b[text()='Lens Tint ']/parent::span")
    public List<WebElement> glassPrescription_LensTintDetails;
    @FindBy(xpath = "//b[text()='Type of Lens ']/parent::span")
    public List<WebElement> glassPrescription_TypeOfLensDetails;
    @FindBy(xpath = "//b[contains(text(),'Dia')]/parent::span")
    public List<WebElement> glassPrescription_DiaDetails;
    @FindBy(xpath = "//b[text()='Size']/parent::span")
    public List<WebElement> glassPrescription_SizeDetails;
    @FindBy(xpath = "//b[text()='Fitting Height']/parent::span")
    public List<WebElement> glassPrescription_FittingHeightDetails;
    @FindBy(xpath = "//b[text()='Prism Base']/parent::span")
    public List<WebElement> glassPrescription_PrismBaseDetails;
    @FindBy(xpath = "//li[text()='Glass Prescription Advised']")
    public List<WebElement> list_advisedGlassPrescriptionComments;

    @FindBy(xpath = "//div[@class='prescription-information']//b")
    public List<WebElement> list_advisedGlassPrescriptionHeadersInSalesOrder;

    @FindBy(xpath = "//div[contains(@class,'row no_margin summary_optical summary_optical_clickable')]/div[1]/span[2]/b")
    public List<WebElement> rows_patientAppointmentsInPatientQueue;
    @FindBy(xpath = "//div[contains(@class,'row no_margin summary_pharmacy summary_pharmacy_clickable')]/div[1]/span[2]/b")
    public List<WebElement> rows_patientAppointmentsInPharmacyPatientQueue;
    @FindBy(xpath = "//div[contains(@class,'row no_margin summary_optical summary_optical_clickable')]/div[4]/span")
    public WebElement text_patientStatusInStore;

    @FindBy(xpath = "//div[contains(@class,'row no_margin summary_pharmacy summary_pharmacy_clickable active-pharmacy')]/div[4]/span")
    public WebElement text_patientStatusInPharmacyStore;

    @FindBy(xpath = "//a[@class='btn btn-xs btn-primary mail_with_advice']")
    public WebElement button_emailButton;

    @FindAll
            ({
                    @FindBy(xpath = "//button[@class='btn btn-primary btn-xs full_print dropdown-toggle']"),
                    @FindBy(xpath = "//button[@class='btn btn-primary btn-xs dropdown-toggle']"),
            })
    public WebElement button_printButton;

    /*@FindBy(xpath = "//button[@class='btn btn-primary btn-xs full_print dropdown-toggle']")
    public WebElement button_printButton;*/
    @FindBy(xpath = "//label[@class='print_follow_up']")
    public WebElement checkbox_printFollowupButton;
    @FindBy(xpath = "//span[contains(text(), 'Patient State ')]")
    public WebElement label_patientState;
    @FindBy(xpath = "//b[contains(text(), 'CONVERTED')]")
    public WebElement text_patientStatus;
    @FindBy(xpath = "//button[contains(text(), 'Validate prescription')]")
    public WebElement button_validatePrescription;
    @FindBy(xpath = "//select[@id='invoice_inventory_order_r_glassesprescriptions_typeoflens']")
    public WebElement input_rightSideGlassPrescriptionTypeOfLens;
    @FindBy(xpath = "//select[@id='invoice_inventory_order_r_glassesprescriptions_typeoflens']")
    public WebElement input_leftSideGlassPrescriptionTypeOfLens;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_r_glassesprescriptions_ipd']")
    public WebElement input_rightSideGlassPrescriptionIpd;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_l_glassesprescriptions_ipd']")
    public WebElement input_leftSideGlassPrescriptionIpd;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_r_glassesprescriptions_size']")
    public WebElement input_rightSideGlassPrescriptionSize;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_l_glassesprescriptions_size']")
    public WebElement input_leftSideGlassPrescriptionSize;
    @FindBy(xpath = "//select[@id='invoice_inventory_order_r_glassesprescriptions_lensmaterial']")
    public WebElement input_rightSideGlassPrescriptionLensMaterial;
    @FindBy(xpath = "//select[@id='invoice_inventory_order_l_glassesprescriptions_lensmaterial']")
    public WebElement input_leftSideGlassPrescriptionLensMaterial;
    @FindBy(xpath = "//select[@id='invoice_inventory_order_r_glassesprescriptions_lenstint']")
    public WebElement input_rightSideGlassPrescriptionLensTint;
    @FindBy(xpath = "//select[@id='invoice_inventory_order_r_glassesprescriptions_lenstint']")
    public WebElement input_leftSideGlassPrescriptionLensTint;
    @FindBy(xpath = "//select[@id='invoice_inventory_order_r_glassesprescriptions_framematerial']")
    public WebElement input_rightSideGlassPrescriptionFrameMaterial;
    @FindBy(xpath = "//select[@id='invoice_inventory_order_l_glassesprescriptions_framematerial']")
    public WebElement input_leftSideGlassPrescriptionFrameMaterial;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_r_glassesprescriptions_dia']")
    public WebElement input_rightSideGlassPrescriptionDia;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_l_glassesprescriptions_dia']")
    public WebElement input_leftSideGlassPrescriptionDia;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_r_glassesprescriptions_fittingheight']")
    public WebElement input_rightSideGlassPrescriptionFittingHeight;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_l_glassesprescriptions_fittingheight']")
    public WebElement input_leftSideGlassPrescriptionFittingHeight;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_r_glassesprescriptions_prismbase']")
    public WebElement input_rightSideGlassPrescriptionPrismBase;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_l_glassesprescriptions_prismbase']")
    public WebElement input_leftSideGlassPrescriptionPrismBase;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_r_glassesprescriptions_comments']")
    public WebElement input_rightSideGlassPrescriptionComment;
    @FindBy(xpath = "//input[@id='invoice_inventory_order_l_glassesprescriptions_comments']")
    public WebElement input_leftSideGlassPrescriptionComment;

    @FindBy(xpath = "//button[@class='btn btn-xs btn-success dropdown-toggle opd-invoice']")
    public WebElement button_billButton;
    @FindBy(xpath = "//a[text()=' Advance Receipt']")
    public WebElement button_advanceReceiptButton;
    @FindBy(xpath = "//h4[text()='Advance Receipt']")
    public WebElement button_advanceReceiptTemplate;
    @FindBy(xpath = "//div[@class='row no_margin no_padding']//div[2]/div/div/p")
    public WebElement button_medicationPrescriptionHeader;
    @FindBy(xpath = "//b[text()='Language:']")
    public WebElement label_langaugeLabelDisplayed;
    @FindBy(xpath = "//select[@id='advice_sets_option']")
    public WebElement list_LangaugeSelectDropdown;
    @FindBy(xpath = "//strong[text()='New']")
    public WebElement text_newLabelText;
    @FindBy(xpath = "//table[@class='table table-bordered']//thead//th")
    public List<WebElement> rows_medicationTableHeaderListInPatientQueue;
    @FindBy(xpath = "//table[@class='table table-bordered']//tbody//td")
    public List<WebElement> rows_medicationTabledataListInPatientQueue;

    @FindBy(xpath = "//div[@class='prescription-information']//div//span")
    public WebElement button_medicationPrescriptionHeaderInSalesOrder;

    @FindBy(xpath = "//div[@class='prescription-information']//table[@class='table table-bordered']//thead//th")
    public List<WebElement> rows_medicationTableHeaderListInSalesOrder;
    @FindBy(xpath = "//div[@class='prescription-information']//table[@class='table table-bordered']//tbody//td")
    public List<WebElement> rows_medicationTabledataListInSalesOrder;
    @FindBy(xpath = "//select[@name='invoice_inventory_order[payment_received_breakups_attributes][0][mode_of_payment]']")
    public WebElement select_modeOfPaymentInSalesOrder;
    @FindBy(xpath = "//input[@class='modalRequest_input_style item_total_price']")
    public WebElement input_totalAmount;
    @FindBy(xpath = "//input[@class='form-control payment_received_breakups_amount p3_10']")
    public WebElement input_totalAmountInPayment;
    @FindBy(xpath = "//a[@class='btn btn-xs btn-success']")
    public WebElement button_invoiceButton;

    @FindBy(xpath = "//h4[text()='Mark Not Converted Details']")
    public WebElement header_markNotConvertedTemplateHeader;
    @FindBy(xpath = "//div[contains(text(),'Attended by')]")
    public WebElement text_attendedBy;
    @FindBy(xpath = "//select[@id='patient_optical_prescription_mark_converted_by']")
    public WebElement select_selectedOpticianName;
    @FindBy(xpath = " //label[@for='reason_two']")
    public WebElement label_dilatedReason;
    @FindBy(xpath = " //form[@id='mark-converted-form']//input[@value='Save']")
    public WebElement button_saveNotConverted;

    @FindBy(xpath = "//label[@for='reason_four']")
    public WebElement label_knownOpticalShopReason;
    @FindBy(xpath = "//span[text()='Status']")
    public WebElement label_status;
    @FindBy(xpath = "//div[@class='col-sm-12 patient-converted-section']//span[text()='Not Converted']")
    public WebElement label_notConvertedStatus;
    @FindBy(xpath = "//span[contains(text(),'REASONS')]")
    public WebElement label_reason;
    @FindBy(xpath = "//li[contains(text(),' Dilated')]")
    public WebElement text_dilatedReason;
    @FindBy(xpath = "//a[contains(text(),'Edit')]")
    public WebElement button_editReason;
    @FindBy(xpath = "//li[contains(text(),' Known optical shop')]")
    public WebElement text_knowOpticalShopReason;
    @FindBy(xpath = "//span[text()='Previous Bills']/parent::li/following-sibling::li")
    public WebElement text_previousBills;
    @FindBy(css = "#advance_payment_reason")
    public WebElement input_reasonAdvance;
    @FindBy(xpath = "//select[contains(@class,'mode_of_payment')]")
    public WebElement select_mop;
    @FindBy(xpath = "//input[contains(@class,'mop_breakups_amount')]")
    public WebElement input_amountAdvance;

    @FindBy(xpath = "(//select[contains(@class,'mode_of_payment')])[2]")
    public WebElement select_mopSecond;
    @FindBy(xpath = "(//input[contains(@class,'mop_breakups_amount')])[2]")
    public WebElement input_amountAdvanceSecond;

    @FindBy(xpath = "//input[@class='btn btn-success']")
    public WebElement button_saveAdvance;
    @FindBy(xpath = "//input[@class='btn btn-success btn-refund-save']")
    public WebElement button_saveRefund;
    @FindBy(xpath = "//button[normalize-space()='Close']")
    public WebElement button_closeAdvance;
    @FindBy(xpath = "//a[@id='cancel_ins_advance_path']")
    public WebElement button_cancellationAdvance;
    @FindBy(xpath = "//a[@id='refund_ins_advance_path']")
    public WebElement button_refundAdvance;
    @FindBy(xpath = "//span[contains(text(),'Advance Payment')]/parent::div/following-sibling::div//a[contains(@href,'invoice/advance_payments')]  ")
    public WebElement button_createdAdvance;
    @FindBy(xpath = "//input[contains(@class,'advance_payment_breakups_amount')]")
    public WebElement input_settleAmountAdvanceInPayment;
    @FindBy(xpath = " //input[@name = 'invoice_inventory_order[advance_payment_breakups_attributes][0][advance_display_id]']")
    public WebElement input_advanceIdInPayment;
    @FindBy(xpath = "//b[contains(text(),'Advance ID')]/parent::div/following-sibling::div[1]")
    public WebElement text_advanceId;


}
