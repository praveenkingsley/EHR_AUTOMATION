package pages.commonElements.templates.optometrist.optometristTemplateEmail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_OptometristTemplateEmail extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_OptometristTemplateEmail(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//a[@id='add-appointment-btn']/i")
	public WebElement button_addAppointment;

}
