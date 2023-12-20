package pages.commonElements.scheduleAdmission;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_ScheduleAdmission extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_ScheduleAdmission(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//h4[text()='Schedule Admission']")
	public WebElement header_ScheduleAdmissionForm;

	@FindBy(xpath = "//label[text()='Emergency']/parent::div/input[@type='radio']")
	public WebElement radioBtn_emergency;

	@FindBy(xpath = "//label[contains(@for,'is_insured_admission')]/preceding-sibling::input")
	public List<WebElement> list_radioBtnCashlessHospitalisation;

	@FindBy(xpath="//label[contains(@for,'admission')and contains(@for,'hospitalization')]")
	public List<WebElement>  list_radioAdmissionType;

	@FindBy(xpath = "//*[@class='expected_planned_admission_notify']/b")
	public WebElement text_plannedAdmissionNotifyMsg;

	@FindBy(xpath = "//span[contains(@id,'admission_facility')]")
	public WebElement select_locationAdmissionForm;

	@FindBy(xpath = "//input[@role='textbox']")
	public WebElement input_textBox;

	@FindBy(xpath = "//span[contains(@id,'admission_doctor-')]")
	public WebElement select_doctorAdmissionForm;

	@FindBy(xpath = "//*[contains(@id,'select2-admission_doctor_')]")
	public List<WebElement> list_selectAdditionalDoctorsAdmissionForm;
	@FindBy(xpath = "//*[@id='admission_anesthesia']")
	public WebElement select_anesthesia;
	@FindBy(xpath = "//ul[@class='select2-results__options']/li")
	public List<WebElement> list_searchAllDropdownItems;
	@FindBy(xpath = "//button[text()='Select Case']")
	public WebElement button_selectCase;

	@FindBy(xpath = "//input[contains(@id,'admission_scheduled_date')]")
	public WebElement input_admissionDate;
	@FindBy(xpath = "//input[contains(@id,'admission_reporting_date')]")
	public WebElement input_reportingDate;
	@FindBy(xpath = "//input[@id='reporting_time']")
	public WebElement input_reportingTime;
	@FindBy(xpath = "//input[@id='admission_time']")
	public WebElement input_admissionTime;

	@FindBy(xpath = "//input[contains(@id,'dischargedate')]")
	public WebElement input_dischargeDate;

	@FindBy(xpath = "//input[@id='expecteddischargetime']")
	public WebElement input_dischargeTime;

	@FindBy(xpath = "//input[contains(@id,'admissionreason')]")
	public WebElement input_reasonForAdmission;

	@FindBy(xpath="//*[contains(@id,'managementplan')]")
	public WebElement input_managementPlansAdmissionForm;

	@FindBy(xpath = "//input[contains(@id,'search_procedure')]")
	public WebElement input_procedureCaseDetails;

	@FindBy(xpath = "//button[text()='Save']")
	public WebElement button_saveProcedure;

	@FindBy(xpath = "//input[contains(@name,'procedures') and @type='checkbox']")
	public WebElement input_checkBoxPresentProcedure;

	@FindBy(xpath = "//select[contains(@id,'inventory_item_id')]")
	public List<WebElement> select_listProcedureCaseSheetItems;

	@FindBy(xpath="//button[@data-dismiss='modal'][normalize-space()='×']")
	public WebElement button_closeAdmissionForm;

	@FindBy(xpath = "//select[@id='admission_case_sheet_id']")
	public WebElement select_allCasesDropDown;


	@FindBy(xpath = "//select[@id='admission_case_sheet_id']/following-sibling::span//span[@role='combobox']")
	public WebElement button_allCasesDropDown;
	@FindBy(xpath = "//button[text()='View Case Details']")
	public WebElement button_viewCaseDetails;

	@FindBy(xpath = "//input[@value='Schedule Admission']")
	public WebElement button_createAdmission;
	@FindBy(xpath = "//input[@class='btn btn-success btn-create-admission']")
	public WebElement button_saveAdmissionEdit;

	@FindBy(xpath = "//h4[text()='Assign Bed']")
	public WebElement header_assignBed;

	@FindBy(xpath = "//select[@name='admission[ward_id]']")
	public WebElement input_selectWard;

	@FindBy(xpath = "//select[@name='admission[room_id]']")
	public WebElement input_selectRoom;

	@FindBy(xpath = "//input[@value='Save Bed']")
	public WebElement button_saveBed;

	@FindBy(xpath = "//strong[text()='PATIENT ID']/parent::div/following-sibling::div")
	public WebElement text_PatientIdValue;

	@FindBy(xpath = "//a[@class='case-details']")
	public WebElement button_caseDetails;

	@FindBy(xpath = "//input[@id='case_sheet_case_id']")
	public WebElement input_caseDetailsId;

	@FindBy(xpath = "//div[@id='admissionWizard']/descendant::li[@class='active']/a")
	public WebElement tab_activeScheduleAdmissionForm;

	@FindBy(xpath = "//input[@id='inpatient_ipd_record_admission_attributes_admissiondate']")
	public WebElement input_admitAdmissionDate;

	@FindBy(xpath = "//input[@id='inpatient_ipd_record_admission_attributes_display_admissiontime']")
	public WebElement input_admitAdmissionTime;

	@FindBy(xpath = "//input[@id='is_insured_admission_true']")
	public WebElement radioBtn_cashlessYes;
	@FindBy(xpath = "//input[@name='admission[bed_id]']")
	public List<WebElement> list_bedAssign;

}
