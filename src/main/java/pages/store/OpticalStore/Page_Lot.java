package pages.store.OpticalStore;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Lot extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_Lot(WebDriver driver) {
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
    @FindBy (xpath = "//table[@class='table inventory_table_center']//tr")
    public List<WebElement> list_LotDetailsOnVariants;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tr[1]//td[5]")
    public WebElement text_BeforeStock;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tr[1]//td[6]")
    public WebElement text_AfterStock;

    @FindBy(xpath = "//input[@id='inventory_search']")
    public WebElement input_InventorySearch;

    @FindBy(xpath = "//ul[@class='dropdown-menu']/li/a")
    public WebElement lot_searchFilter;

    @FindBy(xpath = "//span[@id='inventory_search_criteria']")
    public WebElement lot_searchFilterItemDescription;

    @FindBy(xpath = "//input[@id='inventory_search']")
    public WebElement lot_searchbar;
}
