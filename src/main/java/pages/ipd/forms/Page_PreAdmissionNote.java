package pages.ipd.forms;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_PreAdmissionNote extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_PreAdmissionNote(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }
   @FindBy(xpath = "//div[contains(text(),'Pre Admission')]")
    public WebElement header_preAdNote;
    @FindBy(xpath = "//ul[contains(@class,'saved_instruction_notes')]/li")
    public List<WebElement> list_instructionNote;
    @FindBy(xpath = "//ul[contains(@class,'saved_medication_notes')]/li")
    public List<WebElement> list_medicationNote;
    @FindBy(xpath = "//ul[contains(@class,'saved_investigation_notes')]/li")
    public List<WebElement> list_investigationNote;
    @FindBy(xpath = "//textarea[@id='inpatient_pre_admission_note_instruction_note']")
    public WebElement input_note;
    @FindBy(xpath = "//div[@class='note-editable panel-body']")
    public List<WebElement> list_notesText;
    @FindBy(xpath = "//input[contains(@class,'submit_pre_admission_note')]")
    public WebElement button_saveNote;
    @FindBy(xpath = "//div[@class='pcs-entity-title']/span")
    public WebElement text_previewPreAdmNote;
    @FindBy(xpath = "//div[@class='advice col-md-12 ']/div")
    public List<WebElement> list_previewNotesText;
    @FindBy(xpath = "//a[contains(text(),'Edit')]")
    public WebElement button_editPreAdmNote;
    @FindBy(xpath = "//button[contains(text(),'Close')]")
    public WebElement button_closePreAdmNote;

}