package pages.ot.forms.intraOperative;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_PreAnaesthesiaTemplateUpdate extends TestBase {
    private WebDriver driver;

    public Page_PreAnaesthesiaTemplateUpdate(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[text()=' Edit ']")
    public WebElement button_editTemplate;

    @FindBy(xpath = "//button[@name='diabetes'][1]")
    public WebElement button_noDiabetesUnderMedicalHistoryInPreAnaesthesiaTemplate;

    @FindBy(xpath = " //input[@value='Update']")
    public WebElement button_updateTemplate;

}
