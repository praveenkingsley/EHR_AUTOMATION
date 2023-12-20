package pages.commonElements.common_tabs.investigation;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_InvestigationTab extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_InvestigationTab(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindAll
			({
					@FindBy(xpath = "//div[@id='MyWizard']//li[contains(text(),'Investigation')]"),
					@FindBy(xpath = "//a[text()='Investigation']"),
			})
	public WebElement tab_investigation;

	// Ophthal - Investigations

	@FindBy(xpath = "//strong[text()='Ophthal']")
	public WebElement tab_ophthalUnderInvestigationTab;

	@FindBy(xpath = "//label[text()='Ophthal Set']/parent::div/select[@id='ophthal_set']")
	public WebElement select_ophthalSetsUnderInvestigations;

	@FindBy(xpath = "//tbody[@id='opthalinvestigations-added']/tr")
	public List<WebElement> rows_selectedOphthalInvestigations;

	// Laboratory - Investigations
	@FindBy(xpath = "//strong[text()='Laboratory']")
	public WebElement tab_laboratoryUnderInvestigationTab;

	@FindBy(xpath = "//p[text()='Laboratory Sets']/parent::div//select[@id='opdrecord_toplaboratoryset']")
	public WebElement select_laboratorySetsUnderInvestigations;

	@FindBy(xpath = "//tbody[@id='laboratoryinvestigations-added']/tr")
	public List<WebElement> rows_selectedLaboratoryInvestigations;


	// Radiology - Investigations
	@FindBy(xpath = "//strong[text()='Radiology']")
	public WebElement tab_radiologyUnderInvestigationTab;

	@FindBy(xpath = "//p[text()='Radiology Sets']/parent::div//select[@id='radiology_set']")
	public WebElement select_radiologySetsUnderInvestigations;

	@FindBy(xpath = "//tbody[@id='investigations-added']/tr")
	public List<WebElement> rows_selectedRadiologyInvestigations;

	@FindBy(xpath="//input[@id='opdrecord_no_investigation_advised']")
	public WebElement checkBox_NoInvestigation;

	@FindBy(xpath = "//tbody[@id='opthalinvestigations-added']/tr//a[contains(@id,'delete')]")
	public List<WebElement> list_deleteSelectedOphthalInvestigations;

	@FindBy(xpath="//input[@value='custom_opthal_investigation']")
	public WebElement radioBtn_customOpthalInvestigation;

	@FindBy(xpath="//input[@value='standard_opthal_investigation']")
	public WebElement radioBtn_standardOpthalInvestigation;

	@FindBy(xpath="//input[@value='custom_laboratory_investigation']")
	public WebElement radioBtn_customLaboratoryInvestigation;

	@FindBy(xpath="//input[@value='standard_radiology_investigation']")
	public WebElement radioBtn_standardRadiologyInvestigation;

	@FindBy(xpath="//input[@value='custom_radiology_investigation']")
	public WebElement radioBtn_customRadiologyInvestigation;

	@FindBy(xpath="//input[@value='standard_laboratory_investigation']")
	public WebElement radioBtn_standardLaboratoryInvestigation;

	@FindBy(xpath = "//tbody[@id='opthalinvestigations-added']/tr//input[contains(@id,'investigation_performed')][@type='text']")
	public WebElement input_findingsPerformed;

	@FindBy(xpath = "//tbody[@id='opthalinvestigations-added']/tr//input[@type='text']")
	public List<WebElement> list_inputSelectedOphthalInvestigations;

	@FindBy(xpath = "//input[@id='search_ophthal_investigations']")
	public WebElement input_searchOphthalInvestigation;

	public List<WebElement> list_radioAdvisedPerformedOphthalInvestigations(int row){
		return driver.findElements(By.xpath("//tbody[@id='opthalinvestigations-added']/tr["+row+"]//input[@type='radio']"));
	}

}
