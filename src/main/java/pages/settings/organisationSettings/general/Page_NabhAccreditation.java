package pages.settings.organisationSettings.general;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_NabhAccreditation extends TestBase{

    public Page_NabhAccreditation(WebDriver driver) {
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//input[@id='nabh_setting_no']")
    public WebElement radio_buttonNo;

    @FindBy(xpath = "//input[@id='nabh_setting_yes']")
    public WebElement radio_buttonYes;

    @FindBy(xpath = "//select[@id='nabh_setting_facility_id']/option[text()='Ophthalpractice']")
    public WebElement list_selectOphthalpracticeLocation;

    @FindBy(xpath = "//div[@id='manage_nabh_setting']//input[@name='nabh_setting']")
    public List<WebElement> list_radioButtonNabhSetting;
}
