package pages.store.PharmacyStore;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page_StoreInformation extends TestBase {
    public Page_StoreInformation(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[contains(text(),'Store Information')]")
    public WebElement header_subStorePanelOnLeft;
    @FindBy(xpath = "//h4[text()='Store Information']")
    public WebElement header_storeInformation;
    @FindBy(xpath = "//input[@id='shop_name']")
    public WebElement input_storeName;

}
