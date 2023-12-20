package pages.ipd.forms.intraOperative;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

import java.util.List;

public class Page_OtChecklist extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_OtChecklist(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// OT check list
	@FindBy(xpath = "//button[contains(text(),'OT Checklist')]")
	public WebElement button_oTChecklistTemplate;

	@FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu']")
	public WebElement button_newCheckListTemplate;

	@FindBy(xpath = "//label[contains(text(),' Correct Patient')]")
	public WebElement checkbox_correctPatientInOtCheckListTemplate;

	@FindBy(xpath = "//label[contains(text(),'Identity')]")
	public WebElement checkbox_identityInOtCheckListTemplate;

	@FindBy(xpath = "//textarea[@id='ot_checklist_otchecklist_comments']")
	public WebElement input_commentsInOtChecklistTemplate;

	@FindBy(xpath = "//input[@class='btn btn-success btn-sm submit_checklist']")
	public WebElement button_saveOtChecklistTemplate;
	@FindBy(xpath="//input[contains(@name,'ipd_record')][@type='checkbox']")
	public List<WebElement> list_checkboxProcedure;

	
}
