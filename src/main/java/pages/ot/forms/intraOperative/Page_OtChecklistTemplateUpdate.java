package pages.ot.forms.intraOperative;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_OtChecklistTemplateUpdate extends TestBase {
    private WebDriver driver;

    public Page_OtChecklistTemplateUpdate(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='ot_checklist_dose_given_by']")
    public WebElement input_doseGivenByFieldInChecklistTemplate;

    @FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu']//li[2]")
    public WebElement button_viewOtCheckListTemplate;
}
