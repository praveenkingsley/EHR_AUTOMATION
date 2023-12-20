package pages.settings;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Settings extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_Settings(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = " //div[@class='mainpanel']//h4")
    public WebElement header_panelOnLeft;

    @FindBy(xpath = "//li[@class='setting-heading']/parent::ul")
    public WebElement panel_SettingsPanelOnLeft;

    @FindBy(xpath = "//ul[@class='list-unstyled components settings_update']/li/a")
    public List<WebElement> list_ParentOptionsOnLeft;

    @FindBy(xpath = "//ul[@class='list-unstyled components settings_update']/li//span[@class='caret']")
    public List<WebElement> list_ArrowBesideParentOptionsOnLeft;

    @FindBy(xpath = "//ul[@class='list-unstyled components settings_update']/li//ul/li")
    public List<WebElement> list_ChildOptionsOnLeft;


}
