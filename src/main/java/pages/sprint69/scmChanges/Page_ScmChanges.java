package pages.sprint69.scmChanges;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Page_ScmChanges extends TestBase {

    private WebDriver driver;

    public Page_ScmChanges(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

}
