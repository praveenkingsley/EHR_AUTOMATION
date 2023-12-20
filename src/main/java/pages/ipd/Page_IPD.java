package pages.ipd;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import java.util.List;

public class Page_IPD extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_IPD(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//ul[@role='tablist']/li")
    public List<WebElement> tabs_TabsOnIPD;

    @FindBy(xpath = "//input[@id='save_button']")
    public WebElement button_saveAdmissionForm;

    @FindBy(xpath = "//a[text()=' Ready for Admission ']")
    public WebElement button_readyForAdmission;

    @FindBy(xpath = "//div[@id='admission_summary']//a[contains(text(),'Undo')]")
    public WebElement button_undoAdmission;

    @FindBy(xpath = "//*[text()=' Admit Patient ']")
    public WebElement button_admitPatient;

    @FindBy(xpath = "//h4[text()='Admit Form']")
    public WebElement header_admissionForm;

    @FindBy(xpath = "//div[@id='admission_summary']//a[contains(text(),'Delete Admission')]")
    public WebElement button_deleteAdmission;

    @FindBy(xpath = "//div[@class='modal-content']//button[text()='Delete Admission']")
    public WebElement button_deleteAdmissionOnModal;

    @FindBy(xpath = "//div[contains(@class,'row no_margin summary_admission')]//div[@class='overflow patient-name']")
    public List<WebElement> rows_patientNamesOnIPD;

    @FindBy(xpath = "//div[@id='my_queue_list']//div[contains(@class,'row no_margin summary_admission')]")
    public List<WebElement> rows_patientsOnMyQueueTab;

    @FindBy(xpath = "//div[@id='admitted_patient_list']//div[contains(@class,'row no_margin summary_admission')]")
    public List<WebElement> rows_patientsOnAdmittedPatientsTab;

    @FindBy(xpath = "//div[@id='scheduled_admission_list']//div[contains(@class,'row no_margin summary_admission')]")
    public List<WebElement> rows_patientsOnScheduledTodayTab;

    @FindBy(xpath = "//div[@id='current_admitted_patient_list']//div[contains(@class,'row no_margin summary_admission')]")
    public List<WebElement> rows_patientsOnAdmittedTodayTab;

    @FindBy(xpath = "//div[@id='discharged_patient_list']//div[contains(@class,'row no_margin summary_admission')]")
    public List<WebElement> rows_patientsOnDischargedTodayTab;

    // Consent Section
    @FindBy(xpath = "//div[@id='admission_summary']//b[text()='Consents']")
    public WebElement text_consentsSection;

    // Pre Operative Section
    @FindBy(xpath = "//div[@id='admission_summary']//b[text()='Pre-Operative']")
    public WebElement text_preOperativeSection;

    @FindBy(xpath = "//div[@class='modal-header']//input[@name='commit'][@value='Save']")
    public WebElement button_saveOnModalHeader;

    // Intra Operative Section
    @FindBy(xpath = "//div[@id='admission_summary']//b[text()='Intra-Operative']")
    public WebElement text_intraOperativeSection;

    // Post Operative Section
    @FindBy(xpath = "//div[@id='admission_summary']//b[text()='Post-Operative']")
    public WebElement text_postOperativeSection;

    @FindBy(xpath = "//div[@id='sidebar_summary']//li")
    public List<WebElement> tabs_appointmentTabsOnIPDpage;

    @FindBy(xpath = "//div[contains(@class,'row no_margin summary_admission')]")
    public List<WebElement> rows_patientAppointmentsInIPD;
    //Schedule details
    @FindBy(xpath = "//div[contains(text(),'Booking Time')]//following-sibling::div[2]")
    public WebElement text_bookingTime;
    @FindBy(xpath = "//div[contains(text(),'Reporting Time')]//following-sibling::div[2]")
    public WebElement text_reportingTime;
    @FindBy(xpath = "//div[contains(text(),'Arrival Time')]//following-sibling::div[2]")
    public WebElement text_arrivalTime;
    @FindBy(xpath = "//div[contains(text(),'Scheduled Admission Time')]//following-sibling::div[2]")
    public WebElement text_scheduleAdmissionTime;
    @FindBy(xpath = "//div[text()='Admission Time']//following-sibling::div[2]")
    public WebElement text_admissionTime;
    @FindBy(xpath = "//div[text()='Discharge Time']//following-sibling::div[2]")
    public WebElement text_dischargeTime;
    @FindBy(xpath = "//div[text()='Expected Discharge Time']//following-sibling::div[2]")
    public WebElement text_expectedDischargeTime;
    @FindBy(xpath = "//a[@id='edit-admission-btn']")
    public WebElement button_editAdmissionIpd;

    //IPD Cashless hospitalisation changes
    @FindBy(xpath = "//a[@id='insurance-btn']/i")
    public WebElement button_editInsuranceDetailsButton;

    @FindBy(xpath = "//label[contains(@for,'is_insured')]")
    public List<WebElement> list_radioButtonsOfCashlessHospitalisationUnderInsuranceDetailsForm;

    @FindBy(xpath = "//label[contains(@for,'is_insured_admission')]")
    public List<WebElement> list_radioButtonsOfCashlessHospitalisationUnderScheduleAdmissionForm;

    @FindBy(xpath = "//input[@id='patient_contact_person']")
    public WebElement input_patientContactPersonFieldUnderInsuranceDetailsForm;

    @FindBy(xpath = "//select[@id='admission_contact_group_id']")
    public WebElement select_payerFieldUnderInsuranceDetailsForm;

    @FindBy(xpath = "//select[@id='admission_payer_master_id']")
    public WebElement select_contactFieldUnderInsuranceDetailsForm;

    @FindBy(xpath = "//input[@id='sum_insured']")
    public WebElement input_sumInsuredFieldUnderInsuranceDetailsForm;

    @FindBy(xpath = "//input[@id='insurance_comments']")
    public WebElement input_commentsFieldUnderInsuranceDetailsForm;

    @FindBy(xpath = "//input[@id='admission_insurance_name']")
    public WebElement input_insuranceFieldUnderInsuranceDetailsForm;

    @FindBy(xpath = "//input[@id='admission_tpa_name']")
    public WebElement input_tpaFieldUnderInsuranceDetailsForm;

    @FindBy(xpath = "//select[@id='admission_corporate_id']")
    public WebElement select_corporateUnderInsuranceDetailsForm;

    @FindBy(xpath = "//select[@id='admission_insurer_id']")
    public WebElement select_insurerFieldUnderInsuranceDetailsForm;
    @FindBy(xpath = "//select[@id='admission_insurance_id']")
    public WebElement select_insuranceFieldUnderInsuranceDetailsForm;
    @FindBy(xpath = "//input[@id='tpa_policy_no']")
    public WebElement input_policyNumberUnderInsuranceDetailsForm;

    @FindBy(xpath = "//input[@id='admission_insurance_card_number']")
    public WebElement input_cardNumberUnderInsuranceDetailsForm;

    @FindBy(xpath = "//input[@id='admission_insurance_ccn_number']")
    public WebElement input_ccnNumberUnderInsuranceDetailsForm;

    @FindBy(xpath = "//input[@id='policy_expiry_date']")
    public WebElement input_policyExpiryDateFieldUnderInsuranceDetailsForm;

    @FindBy(xpath = "//input[@id='admission_insurance_employee_name']")
    public WebElement input_employeeNameUnderInsuranceDetailsForm;

    @FindBy(xpath = "//input[@id='admission_insurance_relation_with_beneficiary']")
    public WebElement input_relationWithBeneficiaryUnderInsuranceDetailsForm;


    @FindBy(xpath = "//label[@for='pharmacy_credit_included']")
    public WebElement checkbox_pharmacyCreditIncludedCheckboxUnderInsuranceDetailsForm;

    @FindBy(xpath = " //input[@id='doc_document_tpa_form']")
    public WebElement checkbox_preAuthFormCheckboxUnderInsuranceDetailsForm;
    @FindBy(xpath = " //input[@id='doc_document_passport']")
    public WebElement checkbox_passportCheckboxUnderInsuranceDetailsForm;
    @FindBy(xpath = " //input[@id='doc_document_electioncard']")
    public WebElement checkbox_electionCardCheckboxUnderInsuranceDetailsForm;
    @FindBy(xpath = " //input[@id='doc_document_others']")
    public WebElement checkbox_othersCheckboxUnderInsuranceDetailsForm;
    @FindBy(xpath = " //input[@id='doc_patient_cancelled_cheque']")
    public WebElement checkbox_cancelledChequeCheckboxUnderInsuranceDetailsForm;
    @FindBy(xpath = " //input[@id='doc_document_insurancecard']")
    public WebElement checkbox_insuranceCardCheckboxUnderInsuranceDetailsForm;
    @FindBy(xpath = " //input[@id='doc_document_aadharcard']")
    public WebElement checkbox_aadharCardCheckboxUnderInsuranceDetailsForm;

    @FindBy(xpath = " //input[@id='doc_document_employeecard']")
    public WebElement checkbox_employeeCardCheckboxUnderInsuranceDetailsForm;

    @FindBy(xpath = "//input[@value='Update']")
    public WebElement button_updateInsuranceDetailsUnderInsuranceDetailsForm;

    @FindBy(xpath = "//button[contains(text(),'₹ Bills')]")
    public WebElement button_billsUnderIPD;


    @FindBy(xpath = "//span[@id='select2-admission_contact_group_id-container']")
    public WebElement input_payerFieldUnderInsuranceDetails;

    @FindBy(xpath = "//span[@id='select2-admission_payer_master_id-container']")
    public WebElement input_contactFieldUnderInsuranceDetails;

    @FindBy(xpath = "//b[text()='Patient Payer Information :']")
    public WebElement text_patientPayerInfoUnderInsuranceDetails;

    @FindBy(xpath = "//input[@id='admission_patient_payer_data_attributes_0_text_value']")
    public WebElement input_patientPayerDataInfoFieldUnderInsuranceDetails;

    @FindBy(xpath = "//div[@class='col-md-3']/strong")
    public WebElement text_patientPayerDataFieldName;

    @FindBy(xpath = "//table[@class='ui-datepicker-calendar']/tbody/tr/td[contains(@class,'ui-datepicker-today')]")
    public WebElement input_policyExpiryDate;

    @FindBy(xpath = "//input[@class='btn btn-success']")
    public WebElement button_savePatientInsuranceForm;
    @FindBy(xpath = "//button[@class='btn btn-default']")
    public WebElement button_closeAssignBedForm;

    @FindBy(xpath = "//label[@for='admission_emergency_hospitalization']")
    public WebElement button_appointmentType;

    @FindBy(xpath = "//div[text()='Payer']/following-sibling::div[2]")
    public WebElement text_payerGroupUnderInsuranceDetailsInIpdRHS;

    @FindBy(xpath = "//div[text()='Payer']/following-sibling::div[3]")
    public WebElement text_insuranceDisplayNameUnderInsuranceDetailsInIpdRHS;

    @FindBy(xpath = "//div[text()='Insurance Name']/following-sibling::div[2]")
    public WebElement text_insuranceNameUnderInsuranceDetailsInIpdRHS;

    @FindBy(xpath = "//div[text()='Corporate Name']/following-sibling::div[2]")
    public WebElement text_corporateNameUnderInsuranceDetailsInIpdRHS;

    @FindBy(xpath = "//div[text()='Dispensary Name']/following-sibling::div[2]")
    public WebElement text_dispensaryNameUnderInsuranceDetailsInIpdRHS;


    @FindBy(xpath = "//div[text()='Policy No.']/following-sibling::div[2]")
    public WebElement text_policyNumberUnderInsuranceDetailsInIpdRHS;

    @FindBy(xpath = "//div[text()='Employee Name']/following-sibling::div[2]")
    public WebElement text_employeeNameUnderInsuranceDetailsInIpdRHS;
    @FindBy(xpath = "//div[text()='Card No.']/following-sibling::div[2]")
    public WebElement text_cardNumberUnderInsuranceDetailsInIpdRHS;
    @FindBy(xpath = "//div[text()='CCN No.']/following-sibling::div[2]")
    public WebElement text_ccnNumberUnderInsuranceDetailsInIpdRHS;
    @FindBy(xpath = "//div[text()='Relation with Beneficiary']/following-sibling::div[2]")
    public WebElement text_relationWithBeneficiaryUnderInsuranceDetailsInIpdRHS;
    @FindBy(xpath = "//div[text()='TPA Name']/following-sibling::div[2]")
    public WebElement text_tpaNameUnderInsuranceDetailsInIpdRHS;

    @FindBy(xpath = "//div[text()='Pharmacy Credit Included']/following-sibling::div[2]")
    public WebElement text_pharmacyCreditInfoUnderInsuranceDetailsInIpdRHS;
    @FindBy(xpath = "//div[@id='admission_summary']/div/div[9]/div[2]/div/div[9]/div[3]")
    public WebElement text_patientPayerInputInfoUnderInsuranceDetailsInIpdRHS;

    @FindBy(xpath = "//*[@id='admission_summary']/div/div[9]/div[2]/div/div[7]/div[3]")
    public WebElement text_patientPayerForPanelInputInfoUnderInsuranceDetailsInIpdRHS;

    @FindBy(xpath = "//*[@id=\"admission_summary\"]/div/div[9]/div[2]/div/div[10]/div[1]")
    public WebElement text_patientPayerForTpaFieldNameUnderInsuranceDetailsInIpdRHS;
    @FindBy(xpath = "//*[@id=\"admission_summary\"]/div/div[9]/div[2]/div/div[10]/div[3]")
    public WebElement text_patientPayerForTpaInputInfoUnderInsuranceDetailsInIpdRHS;

    @FindBy(xpath = "//div[text()='Sum Insured']/following-sibling::div[2]")
    public WebElement text_sumInsuredUnderInsuranceDetailsInIpdRHS;
    @FindBy(xpath = "//div[text()='Comments']/following-sibling::div[2]")
    public WebElement text_commentsUnderInsuranceDetailsInIpdRHS;
    @FindBy(xpath = "//div[@id='admission_summary']/div/div[9]/div[2]/div/div[9]/div[1]")
    public WebElement text_patientPayerDataFieldNameInsuranceDetailsInIpdRHS;

    @FindBy(xpath = "//*[@id='admission_summary']/div/div[9]/div[2]/div/div[10]/div[1]")
    public WebElement text_patientPayerDataFieldNameInsuranceDetailsInIpdRHSForTPAPlusInsurancePayer;
    @FindBy(xpath = "//*[@id='admission_summary']/div/div[9]/div[2]/div/div[7]/div[1]")
    public WebElement text_patientPayerDataFieldNameForPanelContactUnderInsuranceDetailsInIpdRHS;

    @FindBy(xpath = "//*[@id=\"admission_summary\"]/div/div[9]/div[2]/div/div[10]/div[3]")
    public WebElement text_patientPayerDataInputFieldForTpaPlusInsuranceContactUnderInsuranceDetailsInIpdRHS;

    @FindBy(xpath = "//input[contains(@id,'is_insured_admission_false')]")
    public WebElement radio_noCashlessHospitalisationUnderScheduleAdmissionForm;

    @FindBy(xpath = "//input[contains(@id,'is_insured_admission_true')]")
    public WebElement radio_yesCashlessHospitalisationUnderScheduleAdmissionForm;

    @FindBy(xpath = "//a[text()='New Credit Bill']")
    public WebElement button_newCreditBill;

    @FindBy(xpath = "(//a[text()='New Draft Bill'])[1]")
    public WebElement button_newDraftBill;


    @FindBy(xpath = " //span[@id='select2-invoice_ipd_contact_group_id-container']")
    public WebElement button_payerContactGroupUnderBills;

    @FindBy(xpath = "//span[@id='select2-invoice_ipd_payer_master_id-container']")
    public WebElement button_payerNameUnderBills;

    @FindBy(xpath = "//input[@id='invoice_ipd_insurance_name']")
    public WebElement button_insuranceNameUnderBills;

    @FindBy(xpath = "//input[@id='invoice_ipd_tpa_name']")
    public WebElement button_tpaNameUnderBills;

    @FindBy(xpath = "//span[@id='select2-invoice_ipd_insurance_id-container']")
    public WebElement button_insuranceFieldUnderBills;

    @FindBy(xpath = "//select[@id='invoice_ipd_corporate_id']")
    public WebElement select_corporateNameUnderBills;
    @FindBy(xpath = "//select[@id='invoice_ipd_dispensary_id']")
    public WebElement select_dispensaryNameUnderBills;

    @FindBy(xpath = "//input[@id='tpa_policy_no insurance_policy_no']")
    public WebElement input_policyNumberUnderBills;

    @FindBy(xpath = "//input[@id='invoice_ipd_insurance_employee_name']")
    public WebElement input_employeeNameUnderBills;

    @FindBy(xpath = "//input[@id='invoice_ipd_insurance_relation_with_beneficiary']")
    public WebElement input_relationWithBeneficiaryNameUnderBills;

    @FindBy(xpath = "//button[@class='btn btn-link btn-xs remove_payment_received']")
    public WebElement button_removePaymentReceivedDetailsUnderBills;


    @FindBy(xpath = "//input[@id='policy_expiry_date']")
    public WebElement input_expiryDateUnderBills;

    @FindBy(xpath = "//input[@id='invoice_ipd_insurance_card_number']")
    public WebElement input_cardNumberUnderBills;

    @FindBy(xpath = "//input[@id='invoice_ipd_insurance_ccn_number']")
    public WebElement input_cnnNumberUnderBills;

    @FindBy(xpath = "//div[@id='service_item_v21']/div[2]/div[2]/div[1]/strong")
    public WebElement text_patientPayerInfoFieldNameUnderBills;

    @FindBy(xpath = "//input[@id='invoice_ipd_patient_payer_data_attributes_0_text_value']")
    public WebElement text_patientPayerDataInputFieldNameUnderBills;

    @FindBy(xpath = "//select[@id='invoice_ipd_services_0_description']")
    public WebElement select_itemUnderBills;

    @FindBy(xpath = "//input[@id='invoice_total_payment_remaining']")
    public WebElement input_amountRemainingField;

    @FindBy(xpath = "//input[@name='invoice_ipd[payment_pending_breakups_attributes][0][amount]']")
    public WebElement input_amountPendingField;

    @FindBy(xpath = "//input[@value='Save as Draft']")
    public WebElement button_saveDraftBillButton;

    @FindBy(xpath = "//input[@value='Save Final Bill']")
    public WebElement button_saveFinalBillButton;

    @FindBy(xpath = "//button[text()='Close']")
    public WebElement button_closeForm;

    @FindBy(xpath = "//button[contains(@class,'dropdown-toggle print-covering-letter')]")
    public WebElement button_coverLetter;

    @FindBy(xpath = "//span[@id='select2-invoice_ipd_corporate_id-container']")
    public WebElement input_corporateFieldInBill;

    @FindBy(xpath = "//span[@id='select2-invoice_ipd_insurer_id-container']")
    public WebElement input_insurerFieldInBill;

    @FindBy(xpath = "//span[@id='select2-invoice_ipd_dispensary_id-container']")
    public WebElement input_dispensaryFieldInBill;

    @FindBy(xpath = "//div[@class='col-md-12']")
    public List<WebElement> bill_rowsOfServices;

    @FindBy(xpath = "//input[@class='select2-search__field'][@type=\"search\"]")
    public WebElement input_billService;

    @FindBy(xpath = "//button[text()=' Print Claim Bill ']")
    public WebElement button_printClaimBill;

    @FindBy(xpath = "//ul[@class='dropdown-menu dropdown-left-manual']/li")
    public List<WebElement> list_printOptionsUnderClaimBill;

    @FindBy(xpath = "//select[@id='admission_dispensary_id']")
    public WebElement select_dispensaryUnderInsuranceDetailsForm;

    @FindBy(xpath = "//a[text()='Insurance Details']")
    public WebElement tab_insuranceDetailsUnderScheduleAdmissionButton;
    @FindBy(xpath = "//a[contains(text(),'Bill Clearance')]")
    public WebElement button_billClearance;
    @FindBy(xpath = "//a[contains(text(),'Discharge Patient')]")
    public WebElement button_dischargePatient;
    @FindBy(xpath = "//a[contains(text(),'Undo')]")
    public WebElement button_undoBill;

    //////////////////////////////////////////////////////////////////////////////
    @FindBy(xpath = "//b[contains(text(),'Ophthalmic & Systemic')]/parent::div//following-sibling::div[2]")
    public WebElement text_historyAndAllerigiesRHS;

    @FindBy(xpath = "//b[contains(text(),'Drug (Allergies)')]/parent::div//following-sibling::div[2]")
    public WebElement text_DrugAllergiesInRHSIPD;
    @FindBy(xpath = "//b[contains(text(),'Contact (Allergies)')]/parent::div//following-sibling::div[2]")
    public WebElement text_ContactAllergiesInRHSIPD;

    @FindBy(xpath = " //b[contains(text(),'Food (Allergies)')]/parent::div//following-sibling::div[2]")
    public WebElement text_FoodAllergiesInRHSIPD;

    @FindBy(xpath = "//b[contains(text(),'Ophthalmic & Systemic')]/parent::div/following-sibling::div")
    public WebElement text_historyInSummaryIPD;

    @FindBy(xpath = "//b[contains(text(),'Drug (Allergies)')]/parent::div/following-sibling::div/span")
    public WebElement text_DrugAllergiesInSummaryIPD;
    @FindBy(xpath = "//b[contains(text(),'Contact (Allergies)')]/parent::div/following-sibling::div/span")
    public WebElement text_ContactAllergiesInSummaryIPD;

    @FindBy(xpath = "//b[contains(text(),'Food (Allergies)')]/parent::div/following-sibling::div/span")
    public WebElement text_FoodAllerigiesInSummaryIPD;

    @FindBy(xpath = "//a[@id='add-admission-btn']")
    public WebElement button_AddInIPD;

    @FindBy(xpath = "//button[text()='Edit Patient']")
    public WebElement button_EditPatient;

    @FindBy(xpath = "//a[text()='History']")
    public WebElement button_History;


    @FindBy(xpath = "//button[@id='speciality_histories_eye_disease']")
    public WebElement rows_textEyeDisease;

    @FindBy(xpath = "//button[@id='personal_histories_alcoholism']")
    public WebElement rows_SystemicAlchoism;

    @FindBy(xpath = "//button[@id='btn-cancel_discharge']")
    public WebElement button_clickToReadmit;
    @FindBy(xpath = "//input[@name='commit'][@value='Discharge']")
    public WebElement button_saveDischargePatient;
    @FindBy(xpath = "//input[contains(@id,'admissiondate')][@type='text']")
    public WebElement select_admissionDateAdmissionForm;
    @FindBy(xpath = "//input[contains(@id,'admissiontime')][@type='text']")
    public WebElement select_admissionTimeAdmissionForm;
    @FindBy(xpath = "//button[text()='NA']")
    public WebElement button_notArrived;
    @FindBy(xpath = "//span[text()='Patient has a running admission.']")
    public WebElement text_runningAdmission;
    @FindBy(xpath = "//h4[text()='Schedule Details']")
    public WebElement text_scheduleAdmissionDetails;
    @FindBy(xpath = "//button[@id='print-admission-dropdown']")
    public WebElement button_printAdmission;
    @FindBy(xpath = "//span[contains(text(),'Bills Amount')]")
    public WebElement text_billsAmount;
    @FindBy(xpath = "//button[contains(text(),'₹Template Bills')]")
    public WebElement button_templateBills;
    @FindBy(xpath = "//button[contains(@class,'print-covering-letter')]")
    public WebElement button_printClaimForms;
    @FindBy(xpath = "//a[contains(@class,'btn-case-details')]")
    public WebElement button_addCaseDetails;
    @FindBy(xpath = "//a[@id='pre_admission-note-btn']")
    public WebElement button_preAdmissionNotes;
    @FindBy(xpath = "//div[text()='Pre Admission Note']")
    public WebElement text_headerPreAdmissionNotes;
    @FindBy(xpath = "//a[contains(text(),'Edit')]")
    public WebElement edit_template;
    @FindBy(xpath = "//div[@id='admission_summary']//div[text()='Pre-Operative']/parent::div//a")
    public List<WebElement> list_buttonPreOperativeTemplates;
    @FindBy(xpath = "//div[@id='admission_summary']//div[text()='Intra-Operative']/parent::div//a")
    public List<WebElement> list_buttonIntraOperativeTemplates;
    @FindBy(xpath = "//div[@id='admission_summary']//div[text()='Post-Operative']/parent::div//a")
    public List<WebElement> list_buttonPostOperativeTemplates;
    @FindBy(xpath = "//div[@id='admission_summary']//div[text()='All Notes']/parent::div//a")
    public WebElement list_buttonAllNotesTemplates;
    @FindBy(xpath = "//a[@title='Assign Bed']")
    public WebElement button_assignBed;
    @FindBy(xpath = "//a[contains(@class,'remove-existing-bed')]")
    public WebElement button_removeAssignBed;
    @FindBy(xpath = "//a[contains(@class,'engage_ot')]")
    public WebElement link_unlinkOt;
    @FindBy(xpath = "//a[contains(@class,'delete_ot')]")
    public WebElement link_deleteOt;
    @FindBy(xpath = "//b[text()='ID']/parent::div/following-sibling::div[2]")
    public WebElement text_admissionId;
    @FindBy(xpath = "//b[text()='Adm. ID']/parent::div/following-sibling::div")
    public WebElement text_admissionIdInCreatedAdmission;
}