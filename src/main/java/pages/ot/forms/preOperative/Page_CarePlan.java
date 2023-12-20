package pages.ot.forms.preOperative;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page_CarePlan extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_CarePlan(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//div[text()='Pre-Operative']/following-sibling::div//a[contains(text(),'Care Plan')]")
    public WebElement button_carePlanInPreOperative;

    @FindBy(xpath = "//div[@class='modal-header']//input[@name='commit'][@value='Save']")
    public WebElement button_saveOnModalHeader;

    @FindBy(xpath = "//select[@id='nursing_record_medicationsets']")
    public WebElement select_medicationSetInCarePlan;

}