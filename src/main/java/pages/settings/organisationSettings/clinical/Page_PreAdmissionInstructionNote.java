package pages.settings.organisationSettings.clinical;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_PreAdmissionInstructionNote extends TestBase{

    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_PreAdmissionInstructionNote(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//a[contains(text(),'Add Note')]")
    public WebElement button_addInstructionNote;
    @FindBy(xpath = "//input[@id='pre_admission_instruction_note_name']")
    public WebElement input_instructionNoteName;
    @FindBy(xpath = "//div[@class='note-editable panel-body']")
    public WebElement input_instructionNoteText;
    @FindBy(xpath = "//input[@value='Save']")
    public WebElement button_saveInstructionNote;
    @FindBy(xpath = "//table[@id='pre-admission-instruction-note-list']/tbody/tr/td[@class='sorting_1']")
    public List<WebElement> tableList_preAdmissionInstructionNoteName;



}
