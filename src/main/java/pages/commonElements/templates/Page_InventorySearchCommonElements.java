package pages.commonElements.templates;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_InventorySearchCommonElements extends TestBase {
    private WebDriver driver;

    public Page_InventorySearchCommonElements(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//button[@class='btn btn-primary btn-sm inventory_search_panel_dropdown']")
    public WebElement button_searchTypeDropdown;
    @FindBy(xpath = "//input[@id='inventory_search']")
    public WebElement input_searchBoxInput;
    @FindBy(xpath = "//button[@class='btn btn-primary btn-sm inventory_search_panel_dropdown']/span[1]")
    public WebElement button_searchTypeSelectedText;
    @FindBy(xpath = "//div[@class='input-group-btn open']/ul/li")
    public List<WebElement> list_searchTypeList;
    @FindBy(xpath = "//button[@class='btn btn-primary inventory-search-button']")
    public WebElement button_searchButtonInSearchBox;
    @FindBy(xpath = "//button[@class='btn btn-primary inventory-clear-button']")
    public WebElement button_clearButtonInSearchBx;

    @FindBy(xpath = "(//button[@class='btn btn-primary btn-sm dropdown-toggle '])[2]")
    public WebElement button_filterDropdownButton;
    @FindBy(xpath = "//a[text()='Previous Month']")
    public WebElement text_previousMonthFilter;
    @FindBy(xpath = "//a[text()='This Year']")
    public WebElement text_thisYearFilter;
    @FindBy(xpath = "//strong[text()='Nothing to Display']")
    public WebElement text_nothingToDisplay;
    @FindBy(xpath = "//td[text()='Delivery Challan']/parent::tr/td[4]")
    public WebElement text_deliveryChallanId;
    @FindBy(xpath = "//td[text()='Tax Invoice']/parent::tr/td[4]")
    public WebElement text_taxInvoiceId;







}
