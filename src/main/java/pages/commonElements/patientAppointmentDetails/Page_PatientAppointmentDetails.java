package pages.commonElements.patientAppointmentDetails;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_PatientAppointmentDetails extends TestBase {
	@SuppressWarnings("unused")
	private final WebDriver driver;

	public Page_PatientAppointmentDetails(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	// Patient Appointment Details Section

	@FindBy(xpath = "//div[@id='appointment_summary' or @id='ot_summary' or @id='admission_summary']//img[@class='patient_profile_pic']")
	public WebElement img_patientProfilePicOnPatientDetailsSection;

	@FindBy(xpath = "//div[@id='appointment_summary' or @id='ot_summary' or @id='admission_summary']//h4/span[text()='Patient Details']//ancestor::div[2]//div[contains(@class,'row')]")
	public List<WebElement> rows_PatientDetails;

	@FindBy(xpath = "//div[@id='appointment_summary']//div[@id='modalCalendarAppointmentDetailsId']//h4/b")
	public WebElement text_patientNameInOPD;

	@FindBy(xpath = "//div[@id='patient-details']//div[contains(text(),'Patient ID')]/following-sibling::div[2]")
	public WebElement text_patientID;
	@FindBy(xpath = "//div[@id='patient-details']//div[contains(text(),'Primary Contact')]/following-sibling::div[2]")
	public WebElement text_patientNumber;
	@FindBy(xpath = "//div[@id='appointment_summary']//span/b")
	public WebElement text_patientAgeInOPD;
	@FindBy(xpath = "//div[@id='appointment_summary']//a[contains(@class,'edit_patient')]")
	public WebElement button_editPatientInfo;

	@FindBy(xpath = "//div[@id='appointment_summary' or @id='ot_summary' or @id='admission_summary']//div[text()=':']/preceding-sibling::div")
	public List<WebElement> keysOnRows_PatientDetails;

	@FindBy(xpath = "//div[@id='appointment_summary' or @id='ot_summary' or @id='admission_summary']//div[text()=':']/following-sibling::div")
	public List<WebElement> valuesOnRows_PatientDetails;

	@FindBy(xpath = "//div[@id='patient_history_allergy']")
	public WebElement section_patientHistoryNdAllergies;

	@FindBy(xpath = "//div[@id='patient_history_allergy']//h5")
	public List<WebElement> text_keysOnPatientHistoryNdAllergies;

	@FindBy(xpath = "//div[@id='patient_history_allergy']//h5/following-sibling::label")
	public List<WebElement> labels_valuesOnPatientHistoryNdAllergies;

	// Ophthalmic & Systemic
	@FindBy(xpath = "//div[@id='patient_history_allergy']//b[text()='Ophthalmic & Systemic :']")
	public WebElement key_OphthalmicNdSystemic;

	@FindBy(xpath = "//b[text()='Ophthalmic & Systemic :']/parent::h5/following-sibling::label")
	public List<WebElement> labels_OphthalmicNdSystemic;

	// Medical
	@FindBy(xpath = "//div[@id='patient_history_allergy']//b[text()='Medical :']")
	public WebElement key_Medical;

	@FindBy(xpath = "//b[text()='Medical :']/parent::h5/following-sibling::label")
	public List<WebElement> labels_Medical;

	// Family
	@FindBy(xpath = "//div[@id='patient_history_allergy']//b[text()='Family :']")
	public WebElement key_Family;

	@FindBy(xpath = "//b[text()='Family :']/parent::h5/following-sibling::label")
	public List<WebElement> labels_Family;

	// Drug (Allergies)
	@FindBy(xpath = "//div[@id='patient_history_allergy']//b[text()='Drug (Allergies) :']")
	public WebElement key_DrugAllergies;

	@FindBy(xpath = "//b[text()='Drug (Allergies) :']/parent::h5/following-sibling::label")
	public List<WebElement> labels_DrugAllergies;

	// Contact (Allergies)
	@FindBy(xpath = "//div[@id='patient_history_allergy']//b[text()='Contact (Allergies) :']")
	public WebElement key_ContactAllergies;

	@FindBy(xpath = "//b[text()='Contact (Allergies) :']/parent::h5/following-sibling::label")
	public List<WebElement> labels_ContactAllergies;

	// Food (Allergies)
	@FindBy(xpath = "//div[@id='patient_history_allergy']//b[text()='Food (Allergies) :']")
	public WebElement key_FoodAllergies;

	@FindBy(xpath = "//b[text()='Food (Allergies) :']/parent::h5/following-sibling::label")
	public List<WebElement> labels_FoodAllergies;

	//Lasik eligible
	@FindBy(xpath = "//h5[contains(text(),'LASIK')]/parent::div/following-sibling::div[2]")
	public WebElement text_lasikStatus;

	// Adverse Events
	@FindBy(xpath = "//button[@id='adverse_event_form']")
	public WebElement button_adverseEvent;

	@FindBy(xpath = "//button[@id='adverse_event_form']/following-sibling::ul//a[contains(text(),'Report Adverse Event')]")
	public WebElement button_reportAdverseEvent;

	@FindBy(xpath = "//button[@id='adverse_event_form']/following-sibling::ul/li")
	public List<WebElement> list_optionsAdverseEvent;

	// Adverse Events Modal
	@FindBy(xpath = "//h4[@class='modal-title'][text()='Adverse Events']")
	public WebElement modal_adverseEvent;

	@FindBy(xpath = "//h4[text()='Adverse Events']/preceding-sibling::button[@class='close']")
	public WebElement button_modalAdverseEventClose;

	@FindBy(xpath = "//div[@class='adverse-event']//b[contains(text(),'Patient')]/parent::div/following-sibling::div/b")
	public WebElement text_adverseEventPatientName;

	// Critical, Major, Minor | based on value
	@FindBy(xpath = "//input[@name='adverse_event[type]']")
	public List<WebElement> listRadioButtons_eventsTypes;

	@FindBy(xpath = "//input[@name='adverse_event[type]'][@value='critical']")
	public WebElement radioButton_criticalEventsSelector;

	@FindBy(xpath = "//input[@name='adverse_event[type]'][@value='major']")
	public WebElement radioButton_majorEventsSelector;

	@FindBy(xpath = "//input[@name='adverse_event[type]'][@value='minor']")
	public WebElement radioButton_minorEventsSelector;

	@FindBy(xpath = "//div[@class='critical-adverse-event']//input[contains(@name,'adverse_event')][@class='adverse-check']")
	public List<WebElement> listRadioButtons_criticalEvents;

	@FindBy(xpath = "//div[@class='major-adverse-event']//input[contains(@name,'adverse_event')][@class='adverse-check']")
	public List<WebElement> listRadioButtons_majorEvents;

	@FindBy(xpath = "//div[@class='minor-adverse-event']//input[contains(@name,'adverse_event')][@class='adverse-check']")
	public List<WebElement> listRadioButtons_minorEvents;

	@FindBy(xpath = "//a[text()='Mark Patient Arrived']")
	public WebElement button_markPatientArrived;

	@FindBy(xpath = "//h4[text()='Select Token']")
	public WebElement text_selectToken;

	@FindBy(xpath = "//button[text()='Save Token']")
	public WebElement button_saveToken;

	@FindBy(xpath = "//button[@class='btn btn-primary btn-primary-transparent btn-xs btn-save-token-direct']")
	public WebElement button_selectToken;

	@FindBy(xpath = "//a[text()='NA']")
	public WebElement button_markPatientNotArrived;
	@FindBy(xpath = "//a[text()='Mark as Completed']")
	public WebElement button_markPatientAsCompleted;

	// Send To Departments Button
	@FindBy(xpath = "//span[text()='Send To :']/parent::div/following-sibling::div/div[contains(@class,'dropdown')]/button")
	public List<WebElement> listButtons_sendToDepartmentsParentButtons;

	// Receptionist
	@FindBy(xpath = "//button[contains(text(),'Receptionist')]")
	public WebElement button_sendToReceptionist;

	@FindBy(xpath = "//button[contains(text(),'Receptionist')]/following-sibling::ul//li/a")
	public List<WebElement> listButtons_sendToReceptionist;

	// Optometrist
	@FindBy(xpath = "//button[contains(text(),'Optometrist')]")
	public WebElement button_sendToOptometrist;

	@FindBy(xpath = "//button[contains(text(),'Optometrist')]/following-sibling::ul//li/a")
	public List<WebElement> listButtons_sendToOptometrist;

	// Doctor
	@FindBy(xpath = "//button[contains(text(),'Doctor')]")
	public WebElement button_sendToDoctor;

	@FindBy(xpath = "//button[contains(text(),'Doctor')]/following-sibling::ul//li/a")
	public List<WebElement> listButtons_sendToDoctor;

	// Nurse
	@FindBy(xpath = "//button[contains(text(),'Nurse')]")
	public WebElement button_sendToNurse;

	@FindBy(xpath = "//button[contains(text(),'Nurse')]/following-sibling::ul//li/a")
	public List<WebElement> listButtons_sendToNurse;

	// Counsellor
	@FindBy(xpath = "//button[contains(text(),'Counsellor')]")
	public WebElement button_sendToCounsellor;

	@FindBy(xpath = "//button[contains(text(),'Counsellor')]/following-sibling::ul//li/a")
	public List<WebElement> listButtons_sendToCounsellor;

	// Floormanager
	@FindBy(xpath = "//button[contains(text(),'Floormanager')]")
	public WebElement button_sendToFloormanager;

	@FindBy(xpath = "//button[contains(text(),'Floormanager')]/following-sibling::ul//li/a")
	public List<WebElement> listButtons_sendToFloormanager;

	// Ar Nct
	@FindBy(xpath = "//button[contains(text(),'Ar Nct')]")
	public WebElement button_sendToArNct;

	@FindBy(xpath = "//button[contains(text(),'Ar Nct')]/following-sibling::ul//li/a")
	public List<WebElement> listButtons_sendToArNct;

	// Cashier
	@FindBy(xpath = "//button[contains(text(),'Cashier')]")
	public WebElement button_sendToCashier;

	@FindBy(xpath = "//button[contains(text(),'Cashier')]/following-sibling::ul//li/a")
	public List<WebElement> listButtons_sendToCashier;

	// Departments
	@FindBy(xpath = "//button[contains(text(),'Departments')]")
	public WebElement button_sendToDepartments;

	@FindBy(xpath = "//button[contains(text(),'Departments')]/following-sibling::ul//li/a")
	public List<WebElement> listButtons_sendToDepartments;

	// Tabs on Appointment Details
	@FindBy(xpath = "//div[@id='ApptWizard']//span[@class='tab-text']")
	public List<WebElement> listTabs_tabsOnPatientDetails;

	// Appointment Details on Patient Appointment Details
	@FindBy(xpath = "//div[@id='ApptWizard']")
	public WebElement section_appointmentDetails;

	@FindBy(xpath = "//a[contains(text(),'Edit Appointment')]")
	public WebElement button_editAppointment;

	@FindBy(xpath = "//button[text()='Surgery Advised']")
	public WebElement button_surgeryAdvised;

	@FindBy(xpath = "//a[contains(text(),'Schedule Admission')]")
	public WebElement button_scheduleAdmission;

	@FindBy(xpath = "//button[contains(text(),'Jump to Admission')]")
	public WebElement button_jumpToAdmission;
	@FindBy(xpath = "//a[contains(text(),'Jump to Admission')]")
	public WebElement button_updatedJumpToAdmission;

	@FindBy(xpath = "//a[contains(text(),'Jump to Admission')]")
	public WebElement button_jumpToAdmissionForSingleAdmission;

	@FindBy(xpath="//button[contains(text(),'Jump to Admission')]/following-sibling::ul/li/a")
	public List<WebElement> list_jumpToAdmission;
	@FindBy(xpath = "//div[@id='appointment-details']/div//div[contains(@class,'row')]")
	public WebElement rows_appointmentDetails;

	@FindBy(xpath = "//div[text()='Consultant']/following-sibling::div[1]/following-sibling::div[1]")
	public WebElement text_consultantValue;

	@FindBy(xpath = "//div[text()='ID']/following-sibling::div[1]/following-sibling::div[1]")
	public WebElement text_patientIdValue;

	@FindBy(xpath = "//div[text()='Scheduled By']/following-sibling::div[1]/following-sibling::div[1]")
	public WebElement text_scheduledByValue;

	@FindBy(xpath = "//div[text()='Appointment Type']/following-sibling::div[1]/following-sibling::div[1]/span")
	public WebElement text_appointmentTypeValue;

	// Button text is "Dilate" or "Dilate Again"
	@FindBy(xpath = "//div[@id='patient-dilation-section']//span[text()='Dilatation']/parent::div/following-sibling::div[1]/following-sibling::div[1]/a")
	public WebElement button_dilate_OR_dilateAgain;

	//	In case need common xpath for "Dilate" , "Dilate Again" or "Stop"
	//	(//div[@id='patient-dilation-section']//span[text()='Dilatation']/parent::div/following-sibling::div[1]/following-sibling::div[1]/*)[1]
	@FindBy(xpath = "//div[@id='patient-dilation-section']//span[text()='Dilatation']/parent::div/following-sibling::div[1]/following-sibling::div[1]/button")
	public WebElement button_stopDilation;

	@FindBy(xpath = "//div[@id='patient-dilation-section']//span[text()='Dilatation']/parent::div/following-sibling::div[1]/following-sibling::div[1]/span")
	public WebElement button_textBesideDilationButton;

	@FindBy(xpath = "//div[@id='patient-dilation-section']//div[@class='row'][2]/div[1]//span[contains(@class,'dilation_')]")
	public List<WebElement> list_afterDilationMessagesKeys;

	@FindBy(xpath = "//div[@id='patient-dilation-section']//div[@class='row'][2]/div[2]//span[contains(@class,'dilation_')]")
	public List<WebElement> list_afterDilationMessagesValues;

	// Dilation Modal
	@FindBy(xpath = "//h4[@class='modal-title'][text()='Dilation Options']")
	public WebElement modal_dilationOptions;

	@FindBy(xpath = "//h4[text()='Dilation Options']/preceding-sibling::button[@class='close']")
	public WebElement button_modalDilationOptionsClose;

	@FindBy(xpath = "//select[@id='patient_dilation_drops']")
	public WebElement select_dilationDrops;

	@FindBy(xpath = "//select[@id='patient_dilation_drops']/option")
	public List<WebElement> listOptions_dilationDrops;

	@FindBy(xpath = "//select[@id='patient_dilation_eye_side']")
	public WebElement select_dilationEye;

	@FindBy(xpath = "//select[@id='patient_dilation_eye_side']/option")
	public List<WebElement> listOptions_dilationEye;

	@FindBy(xpath = "//select[@id='patient_dilation_advised_by']")
	public WebElement select_dilationAdvisingDoctor;

	@FindBy(xpath = "//select[@id='patient_dilation_advised_by']/option")
	public List<WebElement> listOptions_dilationAdvisingDoctor;

	@FindBy(xpath = "//input[@id='patient_dilation_dilated_by']")
	public WebElement input_dilatingUser;

	@FindBy(xpath = "//input[@id='patient_dilation_comment']")
	public WebElement input_dilatingComment;

	@FindBy(xpath = "//input[@value='Start Dilation']")
	public WebElement button_startDilation;

	@FindBy(xpath = "//button[text()='Reschedule']")
	public WebElement button_rescheduleAppointment;

	@FindBy(xpath = "//a[contains(@id,'cancel')]")
	public WebElement button_cancelAppointment;

	@FindBy(xpath = "//a[text()='Upload papers']")
	public WebElement button_uploadPapers;

	@FindBy(xpath = "//div[@id='appointment-ot-list']//h4")
	public WebElement text_OT_ScheduleHeader;

	@FindBy(xpath = "//div[@id='appointment-ot-list']//a[contains(text(),'Schedule OT')]")
	public WebElement button_scheduleOT;

	@FindBy(xpath = "//textarea[@id='patient_note_textarea']")
	public WebElement input_patientNote;

	@FindBy(xpath = "//input[@id='submit_patient_note'][@value='Save']")
	public WebElement button_savePatientNote;

	@FindBy(xpath = "//div[@id='panel_notes_appointment']")
	public WebElement section_patientNotes;

	@FindBy(xpath = "//div[@id='panel_notes_appointment']/div")
	public List<WebElement> rows_patientNotes;

	@FindBy(xpath = "//div[@id='panel_notes_appointment']/div/span[contains(@id,'task_comment')]")
	public List<WebElement> listText_patientNotesComment;

	@FindBy(xpath = "//div[@id='panel_notes_appointment']/div/a[contains(@id,'delete')]")
	public List<WebElement> listText_patientNotesDelete;

	// Investigation Details on Patient Appointment Details
	@FindBy(xpath = "//button[@id='btn-investigation-details-refresh']")
	public WebElement button_refreshInvestigationDetails;

	@FindBy(xpath = "//span[text()='All Investigations ']")
	public WebElement button_allInvestigations;

	@FindBy(xpath = "//div[@id='investigations-tab']//span[text()='All Investigations ']/parent::a/following-sibling::ul/li")
	public List<WebElement> list_optionsAllInvestigations;

	@FindBy(xpath = "//span[contains(text(),'All Reports')]")
	public WebElement button_allReports;

	@FindBy(xpath = "//span[contains(text(),'All Reports')]/parent::button/following-sibling::ul/li")
	public List<WebElement> list_optionsAllReports;

	@FindBy(xpath = "//button[contains(text(),'View Reports')]")
	public WebElement button_viewReports;

	@FindBy(xpath = "//button[contains(text(),'Inform Technician')]")
	public WebElement button_informTechnician;

	@FindBy(xpath = "//span[contains(text(),'Add Investigation')]/parent::button")
	public WebElement button_addInvestigations;

	@FindBy(xpath = "//span[contains(text(),'Add Investigation')]/parent::button/following-sibling::ul/li")
	public List<WebElement> list_optionsAddInvestigation;

	@FindBy(xpath = "//div[@id='investigationWizard']//li[@data-tab-id='ophthal']")
	public WebElement tab_headerOphthal;

	@FindBy(xpath = "//div[@id='investigationWizard']//li[@data-tab-id='laboratory']")
	public WebElement tab_headerLaboratory;

	@FindBy(xpath = "//div[@id='investigationWizard']//li[@data-tab-id='radiology']")
	public WebElement tab_headerRadiology;

	@FindBy(xpath = "//div[@class='tab-content investigation-form-body']/div[@id='investigation-ophthal-details']/div")
	public List<WebElement> list_rowsOnOphthalSection;

	@FindBy(xpath = "//div[@class='tab-content investigation-form-body']/div[@id='investigation-laboratory-details']/div")
	public List<WebElement> list_rowsOnLaboratorySection;

	@FindBy(xpath = "//div[@class='tab-content investigation-form-body']/div[@id='investigation-radiology-details']/div")
	public List<WebElement> list_rowsOnRadiologySection;

	//div[@class='tab-content investigation-form-body']/div[@id='investigation-ophthal-details']//div[contains(@class,'row')]
	//div[@class='tab-content investigation-form-body']/div[@id='investigation-laboratory-details']//div[contains(@class,'row')]
	//div[@class='tab-content investigation-form-body']/div[@id='investigation-radiology-details']//div[contains(@class,'row')]

	//cancel appointment form
	@FindBy(xpath = "//input[contains(@id,'cancel')]")
	public WebElement button_cancelAppointmentForm;

	@FindBy(xpath = "//h4[text()='Cancel Appointment Form']")
	public WebElement header_cancelAppointment;

	//Case Details Section In Appointment Details

	@FindBy(xpath = "//a[@class='btn btn-primary btn-xs btn-case-details']")
	public WebElement  button_addCaseName;

	@FindBy(xpath = "//a[text()='All Cases']")
	public WebElement  button_allCases;

	@FindBy(xpath = "//div[@id='case-modal']//ul//a")
	public WebElement  text_caseDetailsOutsideBody;

	@FindBy(xpath = "//div[@id='case-modal']//h4/b")
	public WebElement  text_caseDetailsInsideBody;

	//Template Section in Appointment Details

	@FindBy(xpath = "//button[contains(text(),'New')]")
	public WebElement  button_AddNewTemplate;

	@FindBy(xpath = "//ul[@aria-labelledby='opd-templates-dropdown']/li")
	public List<WebElement> list_templateDetailsList;

	@FindBy(xpath = "//div[@class='consolidate-reports-section']//button[@class='btn btn-primary btn-primary-transparent btn-xs']")
	public WebElement  button_consolidateReports;

	@FindBy(xpath = "//ul[@aria-labelledby='consolidate-reports-dropdown']/li")
	public List<WebElement> list_consolidateReportsList;

	@FindBy(xpath = "//button[@class='ui-datepicker-trigger btn btn-danger calender-btn navbar-btn navbar-btn-calender navbar-btn-hover']/i")
	public WebElement button_appointmentDatePicker;

	@FindBy(xpath = "//span[text()='Prev']")
	public WebElement  button_previousDateInCalender;

	@FindBy(xpath = "//span[@class='ui-datepicker-month']")
	public WebElement  text_monthInCalendar;

	@FindBy(xpath = "//span[@class='ui-datepicker-year']")
	public WebElement  text_yearInCalendar;

	@FindBy(xpath = "//a[text()='11']")
	public WebElement  button_dateInCalendar;

	@FindBy(xpath = "//a[contains(text(),'Pre Admission')]")
	public WebElement  button_preAdmissionNote;

	@FindBy(xpath = "//span[contains(text(),'Send To')]/parent::div/following-sibling::div//button")
	public List<WebElement> list_btnSendToUsers;
	@FindBy(xpath = "//a[text()='Reset']")
	public WebElement  button_resetDilation;
	@FindBy(xpath = "//a[text()='Mark as Arrive-MyQueue']")
	public WebElement  button_markAsArrivedMyQueue;
	@FindBy(xpath = "//a[text()='Mark as Arrive-MyStation']")
	public WebElement  button_markAsArrivedMyStation;
	@FindBy(xpath = "//a[contains(text(),'Vitalsign')]")
	public WebElement  button_createdVitalSignTemplate;
	@FindBy(xpath = "//a[text()='Change Case']")
	public WebElement  button_changeCaseSheet;

	@FindBy(xpath = "//div[@id='appointment_summary']//div[@id='modalCalendarAppointmentDetailsId']/div[1]/div[1]")
	public WebElement  text_patientNameAndDOBInOPD;

	@FindBy(xpath = "//div[@id='appointment_summary']//div[@id='modalCalendarAppointmentDetailsId']/div[@id='patient-details']/div")
	public List<WebElement>  list_patientDetails;

	@FindBy(xpath = "//div[@id='patient_history_allergy']/div/div/div/div[3]")
	public List<WebElement>  list_patientHistoryDetails;
	@FindBy(xpath = "//a[@class='btn btn-link btn-xs print_patient_card']")
	public WebElement  button_printVisitSummary;
	@FindBy(xpath = "//button[@id='patient-summary-timeline-link']")
	public WebElement  button_visitSummary;

	@FindBy(xpath = "//div[text()='Appt/OP ID']/following-sibling::div[2] ")
	public WebElement  text_opdRhsAppointmentId;
	@FindBy(xpath = "//div[text()='Patient ID']//following-sibling::div/b ")
	public WebElement  text_opdRhsPatientId;

	@FindBy(xpath = "//i[@class='fa fa-envelope'] ")
	public WebElement  icon_emailBoxIcon;
	@FindBy(xpath = "//div[@id='patient-referral-type']//li/a")
	public List<WebElement>  list_emailReceiptsReferralList;
	@FindBy(xpath = "//input[@value='Save Referral Message']")
	public WebElement  input_saveReferralMessageButton;
	@FindBy(xpath = "//a[text()=' Print (A4)']")
	public WebElement  button_printA4Button;
	@FindBy(xpath = "//a[text()=' (A5)']")
	public WebElement  button_printA5Button;
	@FindBy(xpath = "//input[@id='case_sheet_case_id']")
	public WebElement  text_caseId;
	@FindBy(xpath = "//div[text()='Appt/OP ID']/following-sibling::div[2]")
	public WebElement  text_appointmentId;


}
