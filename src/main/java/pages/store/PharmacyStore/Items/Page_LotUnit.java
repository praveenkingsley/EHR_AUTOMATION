package pages.store.PharmacyStore.Items;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_LotUnit extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_LotUnit(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = " //div[@class='mainpanel']//h4")
    public WebElement header_panelOnLeft;

    @FindBy(xpath = "//ul[@class='nav nav-pills nav-stacked nav-bracket']/li/a")
    public List<WebElement> list_ParentOptionsOnLeft;

    @FindBy(xpath = "//ul[@class='nav nav-pills nav-stacked nav-bracket']/li/a/span")
    public List<WebElement> list_namesParentOptionsOnLeft;

    @FindBy(xpath = "//ul[@class='nav nav-pills nav-stacked nav-bracket']/li//li")
    public List<WebElement> list_ChildOptionsOnLeft;


}
