package pages.ot.forms.intraOperative;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_OperativeTemplateUpdate extends TestBase {
    private WebDriver driver;

    public Page_OperativeTemplateUpdate(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-right']//li[@style='text-align:center']")
    public WebElement button_viewOperativeTemplate;

    @FindBy(xpath = "//input[@placeholder='Surgeon 1']")
    public WebElement input_surgeon1;

    @FindBy(xpath = "//a[text()=' Edit']")
    public WebElement button_editOperative;


}
