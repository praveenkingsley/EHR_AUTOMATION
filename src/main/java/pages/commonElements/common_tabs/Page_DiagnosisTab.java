package pages.commonElements.common_tabs;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_DiagnosisTab extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_DiagnosisTab(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	// Diagnosis
	@FindAll
			({
					@FindBy(xpath = "//div[@id='MyWizard']//li[contains(text(),'Diagnosis')]"),
					@FindBy(xpath = "//a[@href='#diagnosis']"),
			})
	public WebElement tab_diagnosis;

	@FindBy(xpath = "//label[text()='Provisional Diagnosis']/preceding-sibling::input[@type='checkbox']")
	public WebElement checkbox_provisionalDiagnosis;

	@FindBy(xpath = "//input[@name='opdrecord[provisional_diagnosis]']")
	public WebElement input_provisionalDiagnosisComments;

	@FindBy(xpath = "//label[text()='Commonly Used Diagnosis']/parent::div//select[@id='opdrecord_topicd_name']")
	public WebElement select_commonlyUsedDiagnosisUnderDiagnosis;

	@FindBy(xpath = "//div[@id='opdrecord_topicd_list_select']//select[@id='opdrecord_topicd_list']")
	public WebElement select_listUnderDiagnosis;

	@FindBy(xpath = "//input[@name='diagnosis_icd_type']")
	public List<WebElement> list_radioBtn_Diagnosis;

	@FindBy(xpath = "//input[@id='RecentDiagnosisIcdRadio1']")
	public WebElement input_radioBtn_commonlyUsedDiagnosis;

	@FindBy(xpath = "//select[@id='diagnosis_children']")
	public WebElement select_diagnosisType;

	@FindBy(xpath = "//select[@id='diagnosis_children']")
	public WebElement select_subDiagnosisType;

	@FindBy(xpath = "//button[normalize-space()='Save' and contains(@class,'pull-right')]")
	public WebElement btn_save_diagnosis;

	@FindBy(xpath = "//tbody[@id='opdrecord_diagnosislist']/tr")
	public List<WebElement> rows_selectedDiagnosis;

	@FindBy(xpath = "//a[contains(@id,'editdiagnosisbutton')]")
	public WebElement btn_editDiagnosis;

	@FindBy(xpath = "//a[contains(@id,'removediagnosisbutton')]")
	public WebElement btn_deleteDiagnosis;

	@FindBy(xpath = "//button[contains(@id,'update')]")
	public WebElement btn_updateDiagnosis;


}
