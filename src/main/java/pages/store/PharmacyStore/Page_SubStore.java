package pages.store.PharmacyStore;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_SubStore extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_SubStore(WebDriver driver) {
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
    @FindBy(xpath = "//a[text()=' Add Sub-Store']")
    public WebElement button_addSubStore;
    @FindBy(xpath = "//a[contains(text(),'Sub-Store')]")
    public WebElement header_subStorePanelOnLeft;
    @FindBy(xpath = "//div[contains(@class,'store-content-list')]//table/tbody/tr")
    public List<WebElement> list_rowCreatedSubStore;
    @FindBy(xpath = "//div[contains(@class,'store-content-list')]//table[@class='table inventory_table_center']/thead/tr/td")
    public List<WebElement> list_subStoreHeaderList;
    @FindBy(xpath = "//a[@id='edit_sub_store']")
    public WebElement button_editSubStore;
    @FindBy(xpath = "//a[@id='delete_sub_store']")
    public WebElement button_disableSubStore;
    @FindBy(xpath = "//a[@id='enable_sub_store']")
    public WebElement button_enableSubStore;

}
