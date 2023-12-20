package pages.settings.facilitySettings.clinical.scheduledAdmissionList;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page_ScheduledAdmissionList extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_ScheduledAdmissionList(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//div[@class='manage_scheduled_admission_list']//div[contains(text(),'Select Location')]")
    public WebElement text_SelectLocation;

    @FindBy(xpath = "//div[@class='manage_scheduled_admission_list']//select[@class='form-control facility-dropdown-for-admission']")
    public WebElement select_SelectLocation;

    @FindBy(xpath = "//div[@id='show-scheduled-list-option-box']//select")
    public WebElement select_ScheduledListToShowForLastDays;

    @FindBy(xpath = "//div[@class='manage_scheduled_admission_list']//button[text()='Save']")
    public WebElement button_Save;

}
