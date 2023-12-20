package pages.settings.organisationSettings.clinical;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_PreAdmissionSpecialNote extends TestBase{

    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_PreAdmissionSpecialNote(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//a[contains(@href,'special_notes')]/parent::div")
    public WebElement button_addSpecialNote;
    @FindBy(xpath = "//input[@id='pre_admission_special_note_name']")
    public WebElement input_nameField;
    @FindBy(xpath = "//div[@class='note-editable panel-body']")
    public WebElement input_noteField;
    @FindBy(xpath = "//input[@value='Save']")
    public WebElement button_saveNote;
    @FindBy(xpath = "//table[@id='pre-admission-special-note-list']/tbody/tr/td[@class='sorting_1']")
    public List<WebElement> tableList_preAdmissionSpecialNoteName;



}
