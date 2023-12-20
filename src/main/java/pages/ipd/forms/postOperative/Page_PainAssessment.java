package pages.ipd.forms.postOperative;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_PainAssessment extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_PainAssessment(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[contains(text(),' Pain Assessment ')]")
	public WebElement input_painScaleInPainAssessmentTemplate;

	@FindBy(xpath = "//input[@class='pain_rating radio_2']")
	public WebElement radioButton_hurtsLittleBitButtonInPainAssessmentTemplate;

	@FindBy(xpath = "//a[contains(text(),'MEDICATIONS')]")
	public WebElement tab_medicationTabInPainAssessmentTemplate;

	@FindBy(xpath = "//input[@id='nursing_record_treatmentmedication_attributes_0_medicinename']")
	public WebElement input_medicationNameInPainAssessmentTemplate;

	@FindBy(xpath = "//a[contains(text(),'OTHERS')]")
	public WebElement tab_othersTabInPainAssessmentTemplate;

	@FindBy(xpath = "//input[@id='nursing_record_received_by']")
	public WebElement input_recievedByFieldInPainAssessmentTemplate;

	@FindBy(xpath = "//a[@id='show-all-notes-btn']")
	public WebElement button_allNoteTemplate;
	@FindBy(xpath = "//select[@class='form-control medication_sets_dropdown']")
	public WebElement select_medicationSetLevel;
	@FindBy(xpath = "//select[@class='form-control nursing_record_medicationsets']")
	public WebElement select_medicationSetInCarePlan;



}
