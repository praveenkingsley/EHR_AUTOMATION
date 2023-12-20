package pages.sprint69.phrWebApp;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Page_PHRWeb extends TestBase {

    private WebDriver driver;

    public Page_PHRWeb(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }
}
