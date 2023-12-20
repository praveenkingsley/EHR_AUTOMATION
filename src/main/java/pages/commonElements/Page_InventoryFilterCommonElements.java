package pages.commonElements;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_InventoryFilterCommonElements {
    private WebDriver driver;

    public Page_InventoryFilterCommonElements(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='stock_running']")
    public WebElement radioButton_itemWithStock;
    @FindBy(xpath = "//input[@id='stock_running_low']")
    public WebElement radioButton_runningLowStock;
    @FindBy(xpath = "//input[@id='stock_out_stock']")
    public WebElement radioButton_outOfStock;
    @FindBy(xpath = "//input[@id='items_stock']")
    public WebElement button_stockButton;
    @FindBy(xpath = "//input[@id='items_description']")
    public WebElement button_descriptionButton;
    @FindBy(xpath = "//input[@id='items_created_at']")
    public WebElement button_createdTimeButton;
    @FindBy(xpath = "//select[@id='category_name']")
    public WebElement select_itemsByCategory;

}
