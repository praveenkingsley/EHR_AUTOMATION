package pages.commonElements.common_tabs.advice;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_MedicationUnderAdviceTab extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_MedicationUnderAdviceTab(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	// Advice
	@FindBy(xpath = "//div[@id='MyWizard']//li[contains(text(),'Advice')]")
	public WebElement tab_advice;

	// Medication - Advice
	@FindBy(xpath = "//strong[text()='Medication']")
	public WebElement tab_medicationUnderAdviceTab;

	@FindBy(xpath = "//select[contains(@class,'medication_sets_dropdown')]")
	public WebElement select_medicationSetLevel;

	@FindBy(xpath = "//select[@id='opdrecord_medicationsets']")
	public WebElement select_medicationSets;

	@FindBy(xpath = "//label[text()='No Medication Advised']/preceding-sibling::input[@type='checkbox']")
	public WebElement checkbox_noMedicationAdvised;

	@FindBy(xpath = "//tr[@class='treatmentmedications']/td//input[contains(@id,'medicinename')]")
	public List<WebElement> list_input_MedicineName;

	@FindBy(xpath = "//tr[@class='treatmentmedications']/td//input[contains(@id,'medicinetype')]")
	public List<WebElement> list_input_MedicineType;

	@FindBy(xpath = "//tr[@class='treatmentmedications']/td//select[contains(@id,'medicinequantity')]")
	public List<WebElement> list_select_MedicineQuantity;

	@FindBy(xpath = "//tr[@class='treatmentmedications']/td//select[contains(@id,'medicinefrequency')]")
	public List<WebElement> list_select_MedicineFrequency;

	@FindBy(xpath = "//tr[@class='treatmentmedications']/td//select[contains(@id,'medicineduration')]")
	public List<WebElement> list_select_MedicineDuration;

	@FindBy(xpath = "//tr[@class='treatmentmedications']/td//select[contains(@id,'medicinedurationoption')]")
	public List<WebElement> list_select_MedicineDurationOption;

	@FindBy(xpath = "//tr[@class='treatmentmedications']/td//span[contains(@class,'tapering_btn')]")
	public List<WebElement> list_button_TaperingButton;

	@FindBy(xpath = "//tr[@class='treatmentmedications']/td//select[contains(@id,'eyeside')]")
	public List<WebElement> list_select_EyeSide;

	@FindBy(xpath = "//tr[@class='treatmentmedications']/td//select[contains(@id,'instruction')]")
	public List<WebElement> list_select_Instruction;

	@FindBy(xpath = "//tr[@class='treatmentmedications']/td//a[@name='removemedicationbutton']")
	public List<WebElement> list_button_RemoveMedication;

	@FindBy(xpath = "//tr[@class='treatmentmedications']/td//a[@id='addnewmedicationbutton1']")
	public List<WebElement> list_button_AddMedication;

}
