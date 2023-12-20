package pages.commonElements.templates.optometrist.optometristTemplateSummary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_OptometristTemplateSummary extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_OptometristTemplateSummary(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//a[@id='add-appointment-btn']/i")
	public WebElement button_addAppointment;

	@FindBy(xpath = "//div[@id='templates-modal']//label[@class='label label-primary']")
	public WebElement text_opdSummaryLabel;

	@FindBy(xpath = "//b[contains(text(),'Pat ID')]/parent::div/following-sibling::div[1]")
	public WebElement text_opdSummaryPatientId;

	@FindBy(xpath = "//b[contains(text(),'Appt. ID')]/parent::div/following-sibling::div[1]")
	public WebElement text_opdSummaryAppointmentId;



}
