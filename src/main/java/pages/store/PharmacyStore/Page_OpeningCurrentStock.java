package pages.store.PharmacyStore;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page_OpeningCurrentStock extends TestBase {
    public Page_OpeningCurrentStock(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(@href,'opening_current_stock')]")
    public WebElement header_subStorePanelOnLeft;
}
