package pages.ot;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

import java.util.List;

public class Page_OT extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_OT(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	@FindBy(xpath = "//a[text()=' Schedule OT']")
	public WebElement button_scheduleOt;
	
	@FindBy(xpath = "//h4[text()='New OT Form']")
	public WebElement header_OTForm;
	
	@FindBy(xpath = "//input[@id='confirm_ot']")
	public WebElement checkbox_timeSlotOverlap;

	@FindBy(xpath = "//button[text()='View Case Details']")
	public WebElement button_viewCaseDetails;
	
	@FindBy(xpath = "//button[text()='Schedule OT']")
	public WebElement button_saveOtForm;
	
	@FindBy(xpath = "//a[@class='link-to-ot']")
	public WebElement link_Ot;
	
    // OT Page
	@FindBy(xpath = "//ul[@role='tablist']/li")
	public List<WebElement> tabs_TabsOnOT;

	@FindBy(xpath = "//div[contains(@class,'row no_margin summary_ot')]//div[@class='overflow patient-name']")
	public List<WebElement> rows_patientNamesOnOT;

	@FindBy(xpath = "//a[text()=' Ready for OT ']")
	public WebElement button_readyForOT;

	@FindBy(xpath = "//h4[text()='Send Patient']")
	public WebElement header_sendPatientToForm;

	@FindBy(xpath = "//h4[text()='Message']")
	public WebElement header_messageFormOt;

	@FindBy(xpath = "//div[@class='modal-body']//button[contains(text(),'Nurse')]")
	public WebElement button_sendToNurseModalForm;

	@FindBy(xpath = "//div[@class='modal-body']//button[contains(text(),'Nurse')]/following-sibling::ul//li/a")
	public List<WebElement> listButtons_sendToNurseModalForm;
	@FindBy(xpath = "//textarea[@id='message']")
	public WebElement input_reasonForSendingPatient;

	@FindBy(xpath = "//div[@class='modal-footer']//input[@name='commit']")
	public WebElement button_sendToUser;

	@FindBy(xpath = "//ul[@role='tablist']/li")
	public List<WebElement> tabs_TabsOnIPD;

	@FindBy(xpath = "//div[@id='ot_summary']//div[text()='Consents']")
	public WebElement text_consentsSection;

	// Pre Operative Section
	@FindBy(xpath = "//div[@id='ot_summary']//div[text()='Pre-Operative']")
	public WebElement text_preOperativeSection;

	@FindBy(xpath = "//div[@class='modal-header']//input[@name='commit'][@value='Save']")
	public WebElement button_saveOnModalHeader;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@FindBy(xpath = "//div[@class='col-md-8 no_padding']")
	public WebElement text_HistoryInRHSOT;

	@FindBy(xpath = "//a[text()='Delete OT']")
	public WebElement button_deleteOt;

	@FindBy(xpath = "//button[text()='Engage OT']")
	public WebElement button_engageOt;
	@FindBy(xpath = "//button[text()='Mark As Done']")
	public WebElement button_markAsDone;
	@FindBy(xpath = "//a[contains(text(),'Send to Ward')]")
	public WebElement button_sendToWard;
	@FindBy(id = "edit-ot-btn")
	public WebElement button_editOtDetails;
	@FindBy(xpath = "//div[text()='Operating Doctor']/following-sibling::div[2]")
	public WebElement text_operatingDoctor;
	@FindBy(xpath = "//a[text()='Unlink OT']")
	public WebElement button_unlinkOt;
	@FindBy(xpath = "//button[contains(text(),'Link To Admission')]")
	public WebElement button_linkToAdmission;
	@FindBy(id = "print-ot-dropdown")
	public WebElement button_printOtDetails;
}
