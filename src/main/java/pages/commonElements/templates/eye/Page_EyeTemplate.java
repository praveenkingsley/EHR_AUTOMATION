package pages.commonElements.templates.eye;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

import java.util.List;

public class Page_EyeTemplate extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_EyeTemplate(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//h4[@class='template-name']")
	public WebElement header_templateHeaderText;

	@FindBy(xpath = "//a[@href='#appointment_my_queue_list']")
	public WebElement my_queue;
	
	@FindBy(xpath = "//div[@class='appointment_type_content tab-pane active']/div")
	public By patient_List;
	 
	@FindBy(xpath = "//a[@id='add-appointment-btn']/i")
	public WebElement button_addAppointment;



	@FindBy(xpath = "//span[text()='Step 2']")
	public WebElement refraction_Tab;

	@FindBy(xpath = "(//strong[contains(text(),'VISUAL ACUITY')])[1]")
	public WebElement left_UCVA;

	@FindBy(xpath = "(//strong[contains(text(),'VISUAL ACUITY')])[2]")
	public WebElement right_UCVA;

	@FindBy(xpath = "(//input[@name='opdrecord[no_right_va_advised]'])[2]")
	public WebElement check_left_UCVA;

	@FindBy(xpath = "(//input[@name='opdrecord[no_left_va_advised]'])[2]")
	public WebElement check_right_UCVA;

	@FindBy(xpath = "(//input[@name='opdrecord[no_right_iop_advised]'])[2]")
	public WebElement check_left_IOP;

	@FindBy(xpath = "(//input[@name='opdrecord[no_left_iop_advised]'])[2]")
	public WebElement check_right_IOP;

	@FindBy(xpath = "//span[text()='Send To :']")
	public WebElement text_sendToDepartmentSection;

	@FindBy(xpath = "//li[@id='followup_is_valid']")
	public WebElement button_followUpButtonInTemplate;
	@FindBy(xpath = "//input[@id='opdfollowupdate-datepicker']")
	public WebElement input_dateFieldInFollowUpTab;

	@FindBy(xpath = "//div[@id='calendar-modal']//button[text()='×']")
	public WebElement button_CloseModalWith_X;
	
	
	   
}
