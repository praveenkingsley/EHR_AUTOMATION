package pages.commonElements.dashboard;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page_Dashboard {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_Dashboard(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//div[@class='dashboard-panel']//div[@class='panel panel-success']")
	public List<WebElement> panels_DashboardPanels;

}
