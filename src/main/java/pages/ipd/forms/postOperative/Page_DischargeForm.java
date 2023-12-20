package pages.ipd.forms.postOperative;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

import java.util.List;

public class Page_DischargeForm extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_DischargeForm(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@class='btn btn-primary btn-primary-transparent btn-xs discharge_note']")
	public WebElement button_dischargeTemplate;

	@FindBy(xpath = "//input[@class='check_all_box']")
	public WebElement checkbox_checkAllInDischargeTemplate;

	@FindBy(xpath = "//textarea[@id='inpatient_ipd_record_discharge_note_attributes_treatment_notes']")
	public WebElement input_treatmentNotesInDischargeTemplate;

	@FindBy(xpath = "//select[@id='inpatient_ipd_record_discharge_note_attributes_medicationsets']")
	public WebElement button_selectMedicationSetInDischargeTemplate;

	@FindBy(xpath = "//textarea[@id='inpatient_ipd_record_discharge_note_attributes_precautions']")
	public WebElement input_precautionsInDischargeTemplate;

	@FindBy(xpath = "//ul[@id='discharge-note-steps']/li")
	public List<WebElement> list_tabsOnDischargeForm;

	@FindBy(xpath = "//button[@class='btn btn-default btn-next']")
	public WebElement button_next;
	@FindBy(xpath = "//input[@id='discharge_date']")
	public WebElement input_dischargeDate;
	@FindBy(xpath = "//input[@id='discharge_time']")
	public WebElement input_dischargeTime;

}
