package pages.ot.forms.postOperative;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_ViewAllWardNotes extends TestBase {
    private WebDriver driver;

    public Page_ViewAllWardNotes(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@id='all-ward-note-btn']")
    public WebElement button_viewAllWardNote;

    @FindBy(xpath = "//a[@class='btn btn-default btn-xs edit-ward-note-btn']")
    public WebElement button_editWardNote;
}
