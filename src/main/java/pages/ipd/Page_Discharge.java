package pages.ipd;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Discharge extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_Discharge(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	@FindBy(xpath = "//*[contains(text(),'Discharge Patient')]")
	public WebElement button_dischargePatient;

	@FindBy(xpath = "//form[@id='ipd_patients_discharge']")
	public WebElement form_discharge;

	@FindBy(xpath = "//div[contains(@class,'ui-pnotify-text')]")
	public WebElement handle_alert;
	@FindBy(xpath = "//input[@id='admission_dischargedate']")
	public WebElement click_dischargeDate;

	@FindBy(xpath = "//table[@class='ui-datepicker-calendar']//td")
	public List<WebElement> input_dischargeDate;

	@FindBy(xpath = "//input[@id='dischargetime']")
	public WebElement click_dischargeTime;

	@FindBy(xpath = "//input[@value='Discharge']")
	public WebElement button_discharge;

}
