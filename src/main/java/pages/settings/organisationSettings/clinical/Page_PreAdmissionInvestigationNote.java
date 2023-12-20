package pages.settings.organisationSettings.clinical;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_PreAdmissionInvestigationNote extends TestBase{

    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_PreAdmissionInvestigationNote(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//a[contains(@href,'investigation_notes')]/parent::div")
    public WebElement button_addInvestigationNote;
    @FindBy(xpath = "//input[@id='pre_admission_investigation_note_name']")
    public WebElement input_InvestigationNoteName;
    @FindBy(xpath = "//div[@class='note-editable panel-body']")
    public WebElement input_InvestigationNoteText;
    @FindBy(xpath = "//input[@value='Save']")
    public WebElement button_saveInvestigationNote;
    @FindBy(xpath = "//table[@id='pre-admission-investigation-note-list']/tbody/tr/td[@class='sorting_1']")
    public List<WebElement> tableList_preAdmissionInvestigationNoteName;

}
