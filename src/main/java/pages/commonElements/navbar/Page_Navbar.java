package pages.commonElements.navbar;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_Navbar extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_Navbar(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	////////////////////////////////////////////
	/////// Primary Navbar - Top Navbar ////////
	////////////////////////////////////////////

	@FindBy(xpath = "//a[@id='main-logo']")
	public WebElement logo_FF_EHS;
	
	// Departments Without Stores - All depts. one one line
	@FindBy(xpath = "//li[contains(@class,'web_responsive')][contains(@id,'management')]")
	public List<WebElement> list_departmentsInOneLine;
	
	// Departments WITH Stores - All depts. in a dropdown
	@FindBy(xpath = "//span[contains(@class,'departments_span')]")
	public WebElement button_departmentFromDropdownSelector;

	@FindBy(xpath = "//li[contains(@class,'departments_link')]")
	public List<WebElement> list_departmentSelector;

	@FindBy(xpath = "//span[contains(text(),'Stores')]")
	public WebElement button_storesSelector;

	@FindBy(xpath = "//li[contains(@class,'main-store-list')]")
	public List<WebElement> list_storesSelector;
	@FindBy(xpath = "//li[contains(@id,'navbar_store_')]")
	public List<WebElement> updated_list_storesSelector;

	@FindBy(xpath = "//button[contains(@class,'search-criteria')]")
	public WebElement button_searchCriteriaSelector;

	@FindBy(xpath = "//a[@class='search-criteria-list']")
	public List<WebElement> list_searchCriteriaSelector;

	@FindBy(xpath = "//input[@name='master_search']")
	public WebElement input_search;

	@FindBy(xpath = "//button[@class='btn master-search-button']")
	public WebElement button_search;

	@FindBy(xpath = "//div[contains(@class,'patient-search-dialog')]")
	public WebElement modal_patientSearch;

	@FindBy(xpath = "//div[contains(@class,'patient-search-dialog')]//select")
	public WebElement select_modalPatientSearchCriteria;

	@FindBy(xpath = "//div[contains(@class,'patient-search-dialog')]//select/option")
	public WebElement options_modalPatientSearchCriteria;

	@FindBy(xpath = "//div[contains(@class,'patient-search-dialog')]//input[@id='search-patient']")
	public WebElement input_modalPatientSearch;

	@FindBy(xpath = "//div[contains(@class,'patient-search-dialog')]//button[text()='Search']")
	public WebElement button_modalPatientSearch;

	@FindBy(xpath = "//span[contains(text(),'Set State')]")
	public WebElement button_setState;

	@FindBy(xpath = "//div[contains(@class,'search-result')]//button")
	public List<WebElement> list_modalSearchResults;

	@FindBy(xpath = "//h4[text()='Search Patient to View Summary']/preceding-sibling::button[@type='button' or @class='close']")
	public WebElement button_modalSearchResultsClose;

	@FindBy(xpath = "//h4[text()='Change State']/preceding-sibling::button")
	public WebElement button_modalClose;

	@FindBy(xpath = "//div[@class='modal-header']//h4[text()='Change State']")
	public WebElement modal_ChangeState;

	@FindBy(xpath = "//b[text()='MARK ALL']/parent::div/following-sibling::div//input")
	public List<WebElement> listButtons_setStateOptions;

	@FindBy(xpath = "//button[contains(@class,'facility_dropdown')]//i[@class='fa fa-hospital-o']/parent::span")
	public WebElement button_facilitySelector;

	@FindBy(xpath = "//ul[@id='main-facility-list']/li[contains(@class,'facility_name') or contains(@class,'main-facility')]")
	public List<WebElement> list_facilitySelector;

	@FindBy(xpath = "//li[@class='facility_name']")
	public WebElement text_selectedFacilityName;

	@FindBy(xpath = "//button[contains(@class,'feedback-management')]")
	public WebElement button_feedback;

	@FindBy(xpath = "//iframe[@title='Feedback']")
	public WebElement iframe_feedback;

	@FindBy(xpath = "//label[@for='summary']/following-sibling::input")
	public WebElement input_feedbackSummary;

	@FindBy(xpath = "//label[@for='description']/following-sibling::div//div[@id='description-wiki-edit']")
	public WebElement input_feedbackDescription;

	@FindBy(xpath = "//input[@id='fullname']")
	public WebElement input_feedbackFullName;

	@FindBy(xpath = "//input[@id='email']")
	public WebElement input_feedbackEmail;

	@FindBy(xpath = "//input[@name='recordWebInfo']")
	public WebElement checkbox_feedbackRecordWebInfo;

	@FindBy(xpath = "//h2[text()='Feedback']/parent::div//input[@type='submit']")
	public WebElement button_feedbackSubmit;

	@FindBy(xpath = "//h2[text()='Feedback']/parent::div//a[text()='Close']")
	public WebElement button_feedbackClose;

	@FindBy(xpath = "//button[@id='setting_n_logout']")
	public WebElement button_SettingsNdLogout;
	
	@FindBy(xpath = "//li[@class='username text-center']")
	public WebElement text_currentUser;

	@FindBy(xpath = "//a[contains(@class,'logout-settings')]")
	public List<WebElement> listButtons_settings;

	@FindBy(xpath = "//a[@href='/users/logout']")
	public WebElement button_logout;

	@FindBy(xpath = "//button[contains(@class,'referral_list_button')]")
	public WebElement button_referralsTrigger;

	@FindBy(xpath = "//i[@class='fa fa-user-md']/ancestor::li/ul/li")
	public List<WebElement> list_OptionsUnderSettingsAndLogout;

	////////////////////////////////////////////
	///// Secondary Navbar - Second Navbar//////
	////////////////////////////////////////////

	@FindBy(xpath = "//a[@id='show-appointment-calender-view']")
	public WebElement button_showCalendarView;

	@FindBy(xpath = "//div[@class='sidebar-summary']")
	public WebElement section_calendarView_Summary;

	@FindBy(xpath = "//a[@id='show-appointment-list-view']")
	public WebElement button_showListView;

	@FindBy(xpath = "//div[@class='navbar-header']/button[contains(@id,'refresh')]")
	public WebElement button_refreshView;

	@FindBy(xpath = "//input[contains(@placeholder,'Search Patient')]")
	public WebElement input_searchPatientOnView;

	@FindBy(xpath = "//button[contains(text(),'Today')]")
	public WebElement button_today;

	@FindBy(xpath = "//div[contains(@class,'mobile_date_responsive')]/span")
	public WebElement text_dateOnNav;

	@FindBy(xpath = "//div[contains(@class,'mobile_date_responsive')]/button[contains(@id,'backdate')]/i")
	public WebElement button_datePrevious;

	@FindBy(xpath = "//div[contains(@class,'mobile_date_responsive')]/button[contains(@id,'frontdate')]/i")
	public WebElement button_dateNext;

	@FindBy(xpath = "//button[contains(@class,'navbar-btn-calender')]/i")
	public WebElement button_calendarOpen;

	@FindBy(xpath = "//table[@class='ui-datepicker-calendar']//a")
	public List<WebElement> td_datesOnCalendar;

	@FindBy(xpath = "//span[@class='ui-datepicker-month']")
	public WebElement text_monthOnCalendar;

	@FindBy(xpath = "//span[@class='ui-datepicker-year']")
	public WebElement text_yearOnCalendar;

	@FindBy(xpath = "//div[@id='ui-datepicker-div']//a[@title='Prev']")
	public WebElement button_previousMonthOnCalendar;

	@FindBy(xpath = "//div[@id='ui-datepicker-div']//a[@title='Next']")
	public WebElement button_nextMonthOnCalendar;

	@FindBy(xpath = "//a[@id='add-appointment-btn' or @id='add-admission-btn']/i")
	public WebElement button_addAppointment;

	@FindBy(xpath = "//a[contains(@class,'navbar')]/i[contains(@class,'print')]")
	public WebElement button_printAppointment;
	@FindBy(xpath = "//i[@class='fa fa-print']/parent::a")
	public WebElement button_printAppointmentList;

	@FindBy(xpath = "//button[@id='all-reports' or @id='print-reports-dropdown']")
	public WebElement button_reports;

	@FindBy(xpath = "//button[@id='all-reports']/following-sibling::ul/li/a")
	public List<WebElement> options_reportOptions;
	@FindBy(xpath = "//a[text()='Draft Bills']")
	public WebElement button_draftBills;
	@FindBy(xpath = "//a[text()='Final Bills']")
	public WebElement button_finalBills;

	@FindBy(xpath = "(//a[text()='Draft Bills'])[2]")
	public WebElement button_draftBillsInAllBill;
	@FindBy(xpath = "(//a[text()='Final Bills'])[2]")
	public WebElement button_finalBillsInAllBill;

	@FindBy(xpath = "//h4[@class='ui-pnotify-title']")
	public WebElement alert_loggedIn;

	@FindBy(xpath = "//input[@name='search_facility'][@type='search']")
	public WebElement input_searchStore;

	@FindBy(xpath = "//h4[@class='ui-pnotify-title'][text()='Working From']")
	public WebElement text_notifyWorkingFrom;
	@FindBy(xpath = "//button[@id='btn-appointment-backdate']")
	public WebElement button_backDateButton;
}
