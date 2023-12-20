package pages.ot.tabs;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_OtTabs extends TestBase {
    private WebDriver driver;

    public Page_OtTabs(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(@class,'row no_margin summary_ot summary_ot_clickable')]//div[@class='overflow patient-name']")
    public List<WebElement> rows_patientNamesOnOt;
}
