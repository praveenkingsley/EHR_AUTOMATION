package pages.commonElements.templates.optometrist;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

import java.util.List;

public class Page_OptometristTemplate extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_OptometristTemplate(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//a[@id='add-appointment-btn']/i")
	public WebElement button_addAppointment;

	@FindBy(xpath = "//div[@id='templates-modal']//div//h4/label")
	public WebElement text_belowTemplateTitle;

	@FindBy(xpath = "//input[@id='opdrecord_patient_id']/parent::div//div[3]")
	public WebElement text_patientIdValue;

	//  History Section Checkboxes
	@FindBy(xpath = "//input[@id='opdrecord_no_chief_complaints_advised']")
	public WebElement checkbox_chiefComplaints;

	@FindBy(xpath = "//input[@id='opdrecord_no_opthalmic_history_advised']")
	public WebElement checkbox_opthalmicHistory;

	@FindBy(xpath = "//input[@id='opdrecord_no_systemic_history_advised']")
	public WebElement checkbox_systemicHistory;

	@FindBy(xpath = "//input[@id='opdrecord_no_allergy_advised']")
	public WebElement checkbox_allAllergy;

	// Refraction Section Checkboxes

	@FindBy(xpath = "//li[@id='refraction_step']")
	public WebElement tab_Refraction;

	@FindBy(xpath = "//input[@id='opdrecord_no_right_va_advised']")
	public WebElement checkbox_rightVaExamined;

	@FindBy(xpath = "//input[@id='opdrecord_no_left_va_advised']")
	public WebElement checkbox_leftVaExamined;

	@FindBy(xpath = "//input[@id='opdrecord_no_right_iop_advised']")
	public WebElement checkbox_rightIopNotExamined;

	@FindBy(xpath = "//input[@id='opdrecord_no_left_iop_advised']")
	public WebElement checkbox_leftIopNotExamined;

	//Examination Section

	@FindBy(xpath = "//li[@id='examination_step']")
	public WebElement tab_Examination;

	@FindBy(xpath = "//button[@id='r_local_examination_normal_normal']")
	public WebElement button_RodNormal;

	@FindBy(xpath = "//button[@id='l_local_examination_normal_normal']")
	public WebElement tab_LosNormal;

	@FindBy(xpath = "//span[@aria-labelledby='select2-opdrecord_view_owner_id-container']//span[@class='select2-selection__arrow']")
	public WebElement dropdown_optometristDropdownBox;

	@FindBy(xpath = "//ul[@id='select2-opdrecord_view_owner_id-results']//li[2]")
	public WebElement list_optometristListValueSelection;









}
