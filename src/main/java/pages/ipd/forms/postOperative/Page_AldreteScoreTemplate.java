package pages.ipd.forms.postOperative;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_AldreteScoreTemplate extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_AldreteScoreTemplate(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[contains(text(),' Aldrete Score ')]")
	public WebElement button_alderteScoreTemplate;

	@FindBy(xpath = "//input[@id='activity_1']")
	public WebElement radioButton_2extremitiesInAldreteScoreTemplate;

	@FindBy(xpath = "//div[@class='modal-header']//input[@class='btn btn-success btn-sm submit_nursing']")
	public WebElement button_saveAldereteScoreTemplate;

	// Discharge template
	@FindBy(xpath = "//a[@class='btn btn-primary btn-primary-transparent btn-xs discharge_note']")
	public WebElement button_dischargeTemplate;

	@FindBy(xpath = "//input[@class='check_all_box']")
	public WebElement checkbox_checkAllInDischargeTemplate;

	@FindBy(xpath = "//textarea[@id='inpatient_ipd_record_discharge_note_attributes_treatment_notes']")
	public WebElement input_treatmentNotesInDischargeTemplate;

	@FindBy(xpath = "//select[@id='inpatient_ipd_record_discharge_note_attributes_medicationsets']/option[1]")
	public WebElement button_selectMedicationSetInDischargeTemplate;

	@FindBy(xpath = "//textarea[@id='inpatient_ipd_record_discharge_note_attributes_precautions']")
	public WebElement input_precautionsInDischargeTemplate;

}
