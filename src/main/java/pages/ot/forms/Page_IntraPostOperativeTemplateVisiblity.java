package pages.ot.forms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_IntraPostOperativeTemplateVisiblity extends TestBase {
    private WebDriver driver;

    public Page_IntraPostOperativeTemplateVisiblity(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@id='ot_summary']//div[text()='Intra-Operative']")
    public WebElement text_intraOperativeSectionInOt;

    @FindBy(xpath = "//div[@id='ot_summary']//div[text()='Post-Operative']")
    public WebElement text_postOperativeSectionInOt;

}
