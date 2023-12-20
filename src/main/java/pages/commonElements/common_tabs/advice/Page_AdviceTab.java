package pages.commonElements.common_tabs.advice;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_AdviceTab extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_AdviceTab(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	// Advice
	@FindBy(xpath = "//div[@id='MyWizard']//li[contains(text(),'Advice')]")
	public WebElement tab_advice;
	//li[@id='free_advice']/a
	@FindBy(xpath = "//li[@id='free_advice']/a")
	public WebElement tab_adviceFreeForm;

	// Medication - Advice
	@FindBy(xpath = "//strong[text()='Medication']")
	public WebElement tab_medicationUnderAdviceTab;

	@FindBy(xpath = "//select[contains(@class,'medication_sets_dropdown')]")
	public WebElement select_medicationSetLevel;

	@FindBy(xpath = "//select[@id='opdrecord_medicationsets' or @id='inpatient_ipd_record_discharge_note_attributes_medicationsets']")
	public WebElement select_medicationSets;

	@FindBy(xpath = "//label[text()='No Medication Advised']/preceding-sibling::input[@type='checkbox']")
	public WebElement checkbox_noMedicationAdvised;

	// Procedures - Advice
	@FindBy(xpath = "//strong[text()='Procedures']")
	public WebElement tab_proceduresUnderAdviceTab;

	@FindBy(xpath = "//label[text()='No Procedure Advised']/preceding-sibling::input[@type='checkbox']")
	public WebElement checkbox_noProceduresAdvised;

	// Referral - Advice
	@FindBy(xpath = "//strong[text()='Referral']")
	public WebElement tab_referralUnderAdviceTab;

	// Advice - Advice
	@FindBy(xpath = "//strong[text()='Advice']")
	public WebElement tab_adviceUnderAdviceTab;

	@FindBy(xpath = "//span[@class='refresh-advice-set-options']//select[@id='advice_sets_option']")
    public WebElement dropdown_advice;

	@FindBy(xpath = "//button[text()='Close']")
	public WebElement button_close;

	@FindBy(xpath = "//select[@id='advice_sets_option']//option")
	public List<WebElement> list_adviceSetDropdown;
	@FindBy(xpath = "//tbody[@class='medication-table-body-added medication_set_body']//td[1]//input[3]")
	public List<WebElement> list_medicationNameInputBox;

	@FindBy(xpath = "//li[@class='ui-menu-item']/a")
	public WebElement text_medicationInSearchedList;

	//strong[text()="Note:Pharmacy medicines with zero stock will not be accessible for prescription through searches or sets"]
	@FindBy(xpath = "//strong[text()='Note:Pharmacy medicines with zero stock will not be accessible for prescription through searches or sets']")
	public WebElement text_zeroStockWarningMessage;

	@FindBy(xpath = "//tbody[@class='medication-table-body-added medication_set_body']//td[1]//input[6]")
	public List<WebElement> list_medicationNameFilledInputBox;

	@FindBy(xpath = "//input[contains(@id,'medicinename')]")
	public WebElement input_medicineName;
	@FindBy(xpath = "//input[contains(@id,'medicinetype')]")
	public WebElement input_medicineType;
	@FindBy(xpath = "//select[contains(@id,'medicinequantity')]")
	public WebElement select_medicineQuantity;
	@FindBy(xpath = "//select[contains(@id,'medicinefrequency')]")
	public WebElement select_medicineFrequency;
	@FindBy(xpath = "//select[@class='form-control medicineduration']")
	public WebElement select_medicineDurationCount;
	@FindBy(xpath = "//select[@class='form-control medicinedurationoption']")
	public WebElement select_medicineDurationDays;
	@FindBy(xpath = "//select[contains(@id,'eyeside')]")
	public WebElement select_medicineEyeSide;
	@FindBy(xpath = "//select[contains(@id,'instruction')]")
	public WebElement select_medicineInstruction;
	@FindBy(xpath = "//a[contains(@id,'removemedication')]")
	public WebElement select_medicineDeleteAction;
	@FindBy(xpath = "//input[@name='custom_procedure_type' and @value='commonly_used']")
	public WebElement radioBtn_commonlyUsedProcedure;
	@FindBy(xpath = "//input[@name='custom_procedure_type']")
	public List<WebElement> list_radioBtnProcedures;
	@FindBy(xpath = "//select[@id='eye_region']")
	public WebElement select_eyeRegionProcedure;
	@FindBy(xpath = "//select[contains(@class,'eyeregion-options')]")
	public WebElement select_procedure;
	@FindBy(xpath = "//input[@id='stage_advised']")
	public WebElement radioBtn_advisedProcedure;
	@FindBy(xpath = "//input[@id='stage_performed']")
	public WebElement radioBtn_performedProcedure;
	@FindBy(xpath = "//input[@id='procedure-name']")
	public WebElement input_procedureName;
	@FindBy(xpath = "//select[@name='procedure[side]']")
	public WebElement select_procedureSide;
	@FindBy(xpath = "//button[@id='save-procedure-details']")
	public WebElement button_saveProcedure;
	@FindBy(xpath="//tbody[@id='procedure-added']/tr")
	public List<WebElement> list_rowSelectedProcedures;
	@FindBy(xpath = "//select[@id='opdrecord_view_pharmacy_store_id']")
	public WebElement select_storeMedication;
	@FindBy(xpath = "//select[@id='nursing_record_medicationsets']")
	public WebElement select_painAssessmentMedicationSet;
}
