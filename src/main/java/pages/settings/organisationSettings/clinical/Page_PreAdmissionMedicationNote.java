package pages.settings.organisationSettings.clinical;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_PreAdmissionMedicationNote extends TestBase{

    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_PreAdmissionMedicationNote(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//a[contains(@href,'medication_notes/new')]")
    public WebElement button_addMedicationNote;
    @FindBy(xpath = "//input[@id='pre_admission_medication_note_name']")
    public WebElement input_medicationNoteName;
    @FindBy(xpath = "//div[@class='note-editable panel-body']")
    public WebElement input_medicationNoteText;
    @FindBy(xpath = "//input[@value='Save']")
    public WebElement button_saveMedNote;
    @FindBy(xpath = "//table[@id='pre-admission-medication-note-list']/tbody/tr/td[@class='sorting_1']")
    public List<WebElement> tableList_preAdmissionMedicationNoteName;


}
