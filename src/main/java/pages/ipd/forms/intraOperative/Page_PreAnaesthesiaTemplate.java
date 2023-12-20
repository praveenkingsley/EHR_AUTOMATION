package pages.ipd.forms.intraOperative;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_PreAnaesthesiaTemplate extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_PreAnaesthesiaTemplate(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Pre anaesthesia template
	@FindBy(xpath = "//a[@id='pre_anaesthesia-note-btn']")
	public WebElement button_PreAnaesthesiaChecklistTemplateInIPD;

	@FindBy(xpath = "//input[@id='inpatient_pre_anaesthesia_note_surgery_name']")
	public WebElement input_surgeryName;

	@FindBy(xpath = "//input[@id='inpatient_pre_anaesthesia_note_surgeon_name']")
	public WebElement input_surgeonName;

	@FindBy(xpath = "//button[@name='anesthesia_planned']")
	public List<WebElement> listButtons_selectAnesthesiaPlanned;

	@FindBy(xpath = "//button[@name='hearing_ability']")
	public List<WebElement> listButtons_selectHearingAbility;

	@FindBy(xpath = "//textarea[@name='inpatient_pre_anaesthesia_note[extra_information_to_doctor_comment]']")
	public WebElement input_extraInformationToDoctor;

	@FindBy(xpath = "//textarea[@name='inpatient_pre_anaesthesia_note[medication_comments]']")
	public WebElement input_medicationComment;

	@FindBy(xpath = "//input[@class='btn btn-success btn-sm submit_pre_anaesthesia_note']")
	public WebElement button_savePreAnaesthesiaTemplate;

	@FindBy(xpath = "//button[normalize-space()='Close']")
	public WebElement button_closePreAnsthesiaTemplate;
	
	@FindBy(xpath = "//a[contains(text(),' Admission')]//i")
	public WebElement button_filledAdmissionTemplateInIPD;

	@FindBy(xpath = "//a[@id='clinical-note-btn']")
	public WebElement button_admissionTemplateInIPD;

}
