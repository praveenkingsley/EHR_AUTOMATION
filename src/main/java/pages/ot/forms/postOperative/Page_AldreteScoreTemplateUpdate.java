package pages.ot.forms.postOperative;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_AldreteScoreTemplateUpdate extends TestBase {
    private WebDriver driver;

    public Page_AldreteScoreTemplateUpdate(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='respiration_1']")
    public WebElement radioButton_dyspnoeaInAldreteScoreTemplate;
}
