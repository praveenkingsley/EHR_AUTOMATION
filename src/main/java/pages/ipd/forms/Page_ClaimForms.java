package pages.ipd.forms;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_ClaimForms extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_ClaimForms(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }
   @FindBy(xpath = "//button[contains(@class,'print-covering-letter')]")
    public WebElement button_claimForms;
    @FindBy(xpath = "//ul[@aria-labelledby='covering-letter-print-dropdown']/li")
    public List<WebElement> list_typeOfClaimForms;


}