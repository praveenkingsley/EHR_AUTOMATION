package pages.opd;

import java.util.List;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;

public class Page_OPD extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;
	Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;

	public Page_OPD(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	// OPD Dash board Appointment Details Section

	@FindBy(xpath = "//ul[@role='tablist']/li")
	public List<WebElement> tabs_TabsOnIPD;

	@FindBy(xpath = "//div[@id='appointment_seen_today_list']")
	public WebElement section_completed;

	@FindBy(xpath = "//div[contains(@class,'row no_margin summary_admission')]//div[@class='overflow patient-name']")
	public List<WebElement> rows_patientNamesOnIPD;

	@FindBy(xpath = "//a[text()=' Ready for Admission ']")
	public WebElement button_readyForAdmission;

	@FindBy(xpath = "//*[text()=' Admit Patient ']")
	public WebElement button_admitPatient;

	@FindBy(xpath = "//h4[text()='Admission Form']")
	public WebElement header_admissionForm;

	@FindBy(xpath = "//input[@id='save_button']")
	public WebElement button_saveAdmissionForm;

	@FindBy(xpath = "//div[contains(@class,'appointment_tabs')]//li")
	public List<WebElement> tabs_appointmentTabsOnHomepage;

	@FindBy(xpath = "//span[contains(@class,'appointment_my_queue_list_count')]")
	public WebElement tabs_patientCount;
	@FindBy(xpath = "//h4[text()='OPD Summary']")
	public WebElement text_headerOPDSummary;
	@FindBy(xpath = "//div[contains(@class,'row no_margin summary_appointment')]")
	public List<WebElement> rows_patientAppointments;

	@FindBy(xpath = "//div[@id='modalCalendarAppointmentDetailsId']//span/b")
	public WebElement text_patientAgeInAppointmentDetails;

	@FindBy(xpath = "//button[contains(text(),'New')]")
	public WebElement button_clickNewTemplate;

	@FindBy(xpath = "//div[@id='appointment-opd-templates']//a[contains(@id,'timeline-viewsummary')]")
	public WebElement button_openTemplate;

	@FindBy(xpath = "//button[contains(text(),'New')]/following-sibling::ul//li/a")
	public List<WebElement> listButtons_selectOptionsOnTemplate;

	@FindBy(xpath = "//a[@class='btn btn-primary edit-record']")
	public WebElement button_enabledEditInOpdSummary;

	@FindBy(xpath = "//a[@class='btn btn-primary edit-record disabled']")
	public WebElement button_disabledEditInOpdSummary;

	@FindBy(xpath = "//h4[@class='ui-pnotify-title']")
	public WebElement alert_loggedIn;

	@FindBy(xpath = "//div[@id='glassesprescriptions']/div//strong")
	public WebElement header_glassPrescription;
	@FindBy(xpath = "//div[@id='glassesprescriptions']/div/div/table/tbody[2]/tr[1]/td[2]")
	public List<WebElement> list_glassSph;
	@FindBy(xpath = "//div[@id='glassesprescriptions']/div/div/table/tbody[2]/tr[1]/td[3]")
	public List<WebElement> list_glassCyl;
	@FindBy(xpath = "//div[@id='refraction']/div/div/strong[1]")
	public WebElement header_pgp;
	@FindBy(xpath = "//div[@id='refraction']/div/div/table/tbody[2]/tr[1]/td[2]")
	public List<WebElement> list_PgpSph;
	@FindBy(xpath = "//div[@id='refraction']/div/div/table/tbody[2]/tr[1]/td[3]")
	public List<WebElement> list_PgpCyl;
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@FindBy(xpath = "//a[@id='add-appointment-btn'][contains(@href,'type=appointment')]")
	public WebElement button_AddAppointment;

	@FindBy(xpath = "//div/center/button[@class='btn btn-info form-control text-center add-new-patient new_patinet_add']")
	public WebElement button_AddNewPatient;

	@FindBy(xpath = "//input[@id='patient_firstname']")
	public WebElement input_FirstName;

	@FindBy(xpath = "//input[@id='patient_mobilenumber']")
	public WebElement input_MobileNumber;

	@FindBy(xpath = "//ul[@class='nav nav-pills nav-justified']/li[4]")
	public WebElement button_Allergies;

	@FindBy(xpath = "//button[@id='drug_allergies_antiviral_agents']")
	public WebElement button_AntiviralAgents;

	@FindBy(xpath = "//button[@id='antiviral_agents_enfuvirtide']")
	public WebElement button_Enfuvirtide;

	@FindBy(xpath = "//input[@id='patient_drug_allergies_comment']")
	public WebElement input_DrugAllergiesComment;

	@FindBy(xpath = "//button[@id='contact_allergies_adhesive_tape']")
	public WebElement button_ContactAllergyAdhesiveTape;

	@FindBy(xpath = "//select[@class='form-control allergy_field_add_field allergy_duration valid']/option[@value='2']")
	public WebElement select_Number;

	@FindBy(xpath = "//input[@id='patient_contact_allergies_comment']")
	public WebElement input_ContactAllergiesComment;

	@FindBy(xpath = "//button[@id='food_allergies_all_seafood']")
	public WebElement button_FoodAllergyAllSeaFood;

	@FindBy(xpath = "//input[@class='btn btn-success']")
	public WebElement button_Save;

	@FindBy(xpath = "//div[@class='row no_margin summary_appointment summary_appointment_clickable active-appointment']/div[1]")
	public WebElement select_PatientDetails;

	@FindBy(xpath = "//b[text()='Drug (Allergies)']/parent::div//following-sibling::div[2]")
	public WebElement text_DrugAllergiesOPDRHS;

	@FindBy(xpath = "//b[text()='Contact (Allergies)']/parent::div//following-sibling::div[2]")
	public WebElement text_contactAllergiesOPDRHS;

	@FindBy(xpath = "//b[text()='Food (Allergies)']/parent::div//following-sibling::div[2]")
	public WebElement text_FoodAllergiesOPDRHS;

	@FindBy(xpath = "//b[contains(text(),'Drug (Allergies)')]/parent::div/following-sibling::div/span")
	public WebElement text_DrugAllergiesSummary;

	@FindBy(xpath = "//b[contains(text(),'Contact (Allergies)')]/parent::div/following-sibling::div/span")
	public WebElement text_ContactAllergiesSummary;

	@FindBy(xpath = "//b[contains(text(),'Food (Allergies)')]/parent::div/following-sibling::div/span")
	public WebElement text_FoodAllergiesSummary;


	@FindBy(xpath = "//button[@id='patient-summary-timeline-link']")
	public WebElement button_Summary;

	@FindBy(xpath = "//div[@id='appointment-details']")
	public WebElement button_Add_Appointment;

	@FindBy(xpath = "//a[contains(@class,'edit_patient_info')]")
	public WebElement button_Edit;

	@FindBy(xpath = "//a[text()=' Schedule Admission']")
	public WebElement button_ScheduleAdmission;

	@FindBy(xpath = "//input[@id='admission_planned_hospitalization']")
	public WebElement radiobutton_Planned;

	@FindBy(xpath = "//button[text()='Select Case']")
	public WebElement button_SelectCase;

	@FindBy(xpath = "//span[@id='select2-admission_case_sheet_id-container']")
	public WebElement select_Searchcriteria;

	@FindBy(xpath = "//input[@class='btn btn-success btn-create-admission']")
	public WebElement button_ScheduleAdmission1;

	@FindBy(xpath = "//input[@id='patient_other_identifier_mr_no']")
	public WebElement input_MrnoinOPD;

	@FindBy(xpath = "//button[@class='btn btn-default sub-search-filter sub-dropdown-toggle']/following-sibling::ul[@class='dropdown-menu']/li")
	public List<WebElement> list_searchFilterNameInOPD;

	@FindBy(xpath = "//button[@class='btn btn-default sub-search-filter sub-dropdown-toggle']")
	public WebElement select_SearchFilterName;

	@FindBy(xpath = "//input[@id='search_appointment']")
	public WebElement input_SerachCriteria;

	@FindBy(xpath = "//input[@id='search_admission']")
	public WebElement input_SerachCriteriaInIPD;

	@FindBy(xpath = "//ul[@id='sub-search']/li[2]")
	public WebElement select_searchFeatureMrNo;

	@FindBy(xpath = "//a[@class='tab-link investigations-tab']")
	public WebElement tabs_selectInvestigations;

	@FindBy(xpath = "//span[text()='Add Investigation ']")
	public WebElement tabs_selectAddInvestigations;

	@FindBy(xpath = "(//a[text()='Laboratory'])[2]")
	public WebElement dropdown_selectLaboratory;

	@FindBy(xpath = "//select[@id='toplaboratoryset']/option[2]")
	public WebElement dropdown_selectCornea;

	@FindBy(xpath = "//input[@class='btn btn-success']")
	public WebElement button_saveLabInv;

	@FindBy(xpath = "//i[@class='fa fa-user-md']")
	public WebElement icon_selectProfileIcon;

	@FindBy(xpath = "//i[@class='glyphicon glyphicon-log-out']")
	public WebElement icon_selectLogoutIcon;

	@FindBy(xpath = "//input[@id='session_username']")
	public WebElement input_selectUsername;

	@FindBy(xpath = "//input[@id='signinbutton']")
	public WebElement button_login;

	@FindBy(xpath = "//button[@class='btn btn-default sub-search-filter sub-dropdown-toggle']")
	public WebElement select_searchFilter;

	@FindBy(xpath = "//ul[@id='sub-search']/li[6]")
	public List<WebElement> list_searchFilterNameInLabUser;

	@FindBy(xpath = "//input[@id='search_investigation']")
	public WebElement input_selectSearchCriteriaInInvestigation;

	@FindBy(xpath = "//button[@class='search_by_barcodeid']")
	public WebElement button_searchButton;

	@FindBy(xpath = "(//i[@class='fas fa-barcode'])[1]")
	public WebElement button_selectBarcodeScan;

	@FindBy(xpath = "//input[@class='submit-print-setting']")
	public WebElement button_selectStickerPrintPreviewer;

	@FindBy(xpath = "//a[contains(@href,'type=walkin')][@id='add-appointment-btn']")
	public WebElement button_addOP;
	@FindBy(xpath = "//a[@id='add-patient-btn']")
	public WebElement button_registerPatient;
	@FindBy(xpath = "//div[text()='Primary Contact']//following-sibling::div[2]")
	public WebElement text_contactNumberOpdRhs;
	@FindBy(xpath = "//a[contains(@class,'print_patient_card')]")
	public WebElement button_printPatientDetails;
	@FindBy(xpath = "//a[contains(@href,'opd_appointments/print')]")
	public WebElement button_printScheduledAppointments;
	@FindBy(xpath = "//div[contains(text(),'Patient Referral')]//parent::div//a[text()='Add']")
	public WebElement button_addPatientReferralOpdRhs;
	@FindBy(xpath = "//div[contains(text(),'Patient Referral')]//parent::div//a[contains(@href,'edit')]")
	public WebElement button_editPatientReferralOpdRhs;
	@FindBy(xpath = "//div[contains(text(),'Appt Referral')]//parent::div//a[contains(@href,'edit')]")
	public WebElement button_editAppointmentReferralOpdRhs;
	@FindBy(xpath = "//a[text()='Add Appointment']")
	public WebElement button_addAppointmentOpdSummary;
	@FindBy(xpath = "//div[contains(text(),'Appt Referral')]//parent::div//a[text()='Add']")
	public WebElement button_addAppointmentReferralOpdRhs;
	@FindBy(xpath = "//div[@id='modalCalendarAppointmentDetailsId']//span[contains(text(),'(')]")
	public WebElement text_dobOpdRhs;
	@FindBy(xpath = "//a[contains(@href,'appointment') and contains (@href,'edit')]")
	public WebElement button_editScheduledAppointment;
	@FindBy(xpath = "//button[contains(@class,'btn-cancel')]")
	public WebElement button_cancelScheduledAppointment;
	@FindBy(xpath = "//button[contains(@class,'btn-reschedule')]")
	public WebElement button_ReScheduledAppointment;
	@FindBy(xpath = "//a[contains(@class,'appointment-overview-tab')]")
	public WebElement tab_overviewOpdRhs;
	@FindBy(xpath = "//a[contains(@class,'bills-tab')]")
	public WebElement tab_billsOpdRhs;
	@FindBy(xpath = "//a[contains(@class,'diagnoses-tab')]")
	public WebElement tab_diagnosisOpdRhs;
	@FindBy(xpath = "//a[contains(@class,'investigations-tab')]")
	public WebElement tab_investigationOpdRhs;
	@FindBy(xpath = "//a[contains(@class,'procedures-tab')]")
	public WebElement tab_proceduresOpdRhs;
	@FindBy(xpath = "//a[contains(@class,'prescriptions-tab')]")
	public WebElement tab_prescriptionsOpdRhs;
	@FindBy(xpath = "//a[contains(@class,'glasses-tab')]")
	public WebElement tab_glassesOpdRhs;
	@FindBy(xpath = "//button[contains(@class,'btn-save-appointment-type')]")
	public WebElement button_confirmAppointment;
	@FindBy(xpath = "//a[@title='Not Arrived']")
	public WebElement button_notArrived;
	@FindBy(xpath = "//a[text()='Get Patient']")
	public WebElement button_getPatient;

	@FindBy(xpath = "//a[text()='Mark Patient Arrived']")
	public WebElement button_markPatientArrived;
	@FindBy(xpath = "//a[text()='Mark as Away']")
	public WebElement button_markAsAway;
	@FindBy(xpath = "//a[text()='Mark as Completed']")
	public WebElement button_markAsCompleted;
	@FindBy(xpath = "//div[contains(text(),'Old Templates')]/parent::div/following-sibling::div//button[@id='opd-templates']")
	public List<WebElement> list_buttonOldTemplates;
	@FindBy(xpath = "//a[text()='Clone']")
	public List<WebElement> list_buttonCloneOldTemplates;
	@FindBy(xpath = "//b[contains(text(),'UPLOADED')]//ancestor::div//a[contains(@href,'paperrecords/edit')]")
	public WebElement button_editUploadSummary;
	@FindBy(xpath = "//b[contains(text(),'UPLOADED')]//ancestor::div//a[@title='Delete File']")
	public WebElement button_deleteUploadSummary;
	@FindBy(xpath = "//button[@id='btn-timeline-view']")
	public WebElement button_viewTimelineSummary;
	@FindBy(xpath = "//button[@id='btn-uploads-view']")
	public WebElement button_viewUploadSummary;
	@FindBy(xpath = "//button[@id='btn-medications-view']")
	public WebElement button_viewMedicationsSummary;
	@FindBy(xpath = "//button[@id='btn-investigations-view']")
	public WebElement button_viewInvestigationsSummary;
	@FindBy(xpath = "//button[@id='btn-optical-view']")
	public WebElement button_viewOpticalSummary;
	@FindBy(xpath = "//button[@id='btn-invoices-view']")
	public WebElement button_viewInvoiceSummary;
	@FindBy(xpath = "//a[text()='Certificates']")
	public WebElement button_certificateSummary;
	@FindBy(xpath = "//a[text()='Referral Message']")
	public WebElement button_referralMessageSummary;
	@FindBy(xpath = "//button[@id='adverse_event_form']")
	public WebElement button_adverseEvent;
	@FindBy(xpath = "//div[@id='appointment-overview-tab']//button[contains(text(),'₹Template Bills')]")
	public WebElement button_templateBills;
	@FindBy(xpath="//div[@id='appointment-opd-templates']/descendant::a")
	public List<WebElement> list_todayFilledTemplates;



	public boolean selectTabOnAppointmentDetailsInOPD(List<WebElement> listOfTabElements, String tabToSwitch){
		boolean returnFlag = false;

		oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
		try {
			for (WebElement eTab : listOfTabElements) {
				String sTabName = Cls_Generic_Methods.getTextInElement(eTab);
				System.out.println(sTabName);
				if (sTabName.contains(tabToSwitch)) {
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, eTab),
							"Validate " + sTabName + " tab is selected");
					Cls_Generic_Methods.customWait(1);
					returnFlag = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnFlag;
	}

}
