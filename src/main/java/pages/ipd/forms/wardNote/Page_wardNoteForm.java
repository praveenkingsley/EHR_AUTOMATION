package pages.ipd.forms.wardNote;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_wardNoteForm extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_wardNoteForm(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// ward note
	@FindBy(xpath = "//a[@id='ward-note-btn']")
	public WebElement button_wardNote;

	@FindBy(xpath = "//textarea[@class='form-control']")
	public WebElement input_wardNoteText;

	@FindBy(xpath = "//input[@class='form-control anthropometryheight']")
	public WebElement input_heightInWarNote;

	@FindBy(xpath = "//input[@class='form-control anthropometryweight']")
	public WebElement input_weightInWardNote;

	@FindBy(xpath = "//input[@class='form-control anthropometrybmi']")
	public WebElement input_BMIInWardNote;

	@FindBy(xpath = "//input[@class='form-control vitalsignstemperature']")
	public WebElement input_TemperatureInWardNote;

	@FindBy(xpath = "//input[@class='form-control vitalsignspulse']")
	public WebElement input_pulseInWardNote;

	@FindBy(xpath = "//input[@class='form-control vitalsignsbp']")
	public WebElement input_bloodPressureInWardNote;

	@FindBy(xpath = "//input[@class='form-control vitalsignsrr']")
	public WebElement input_rrInWardNote;

	@FindBy(xpath = "//input[@class='form-control vitalsignsspo2']")
	public WebElement input_spo2InWardNote;

	@FindBy(xpath = "//button[@data-dismiss='modal']")
	public WebElement button_closeWardNote;

	@FindBy(id = "all-ward-note-btn")
	public WebElement button_viewAllWardNote;

	@FindBy(xpath = "//a[contains(@class,'edit-ward-note-btn')]")
	public WebElement button_editWardNote;
	@FindBy(xpath = "//a[contains(@class,'clone-ward-note-btn')]")
	public WebElement button_cloneWardNote;
}
