package pages.settings.facilitySettings.clinical.facilityProcedureNote;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_FacilityProcedureNote extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_FacilityProcedureNote(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//div[contains(@id,'surg_notes_organisation')]//a[contains(text(),'Procedure Note')]")
    public WebElement button_addProcedureNote;

    //search
    @FindBy(xpath = "//div[@id='procedure-note-list_filter']//input")
    public WebElement input_searchProcedureNote;

    //Find the created advice set in the table
    @FindBy(xpath = "//table[@id='procedure-note-list']/tbody/tr/td[@class='sorting_1']")
    public List<WebElement> tableList_procedureNoteName;

    @FindBy(xpath = "//table[@id='procedure-note-list']/tbody/tr/td[2]//i[@class='fa fa-trash-alt']")
    public List<WebElement> tableList_procedureNoteDeleteButton;

    @FindBy(xpath = "//button[text()='Confirm']")
    public WebElement button_confirmDeleteProcedureNote;

    @FindBy(xpath = "//table[@id='procedure-note-list']/tbody/tr/td[2]//i[@class='fa fa-pencil-square-o']")
    public List<WebElement> tableList_procedureNoteEditButton;

    @FindBy(xpath = "//input[@value='Update']")
    public WebElement button_updateProcedureNote;

    //fill the details for new procedure note
    @FindBy(xpath = "//input[@id='procedure_note_name']")
    public WebElement input_procedureNoteName;

    @FindBy(xpath = "//h4[text()='New Procedure Note']")
    public WebElement header_procedureNote;

    @FindBy(xpath = "//select[@id='procedure_note_specialty_id']")
    public WebElement dropdown_selectSpeciality;

    @FindBy(xpath = "//select[@id='procedure_note_specialty_id']/option")
    public List<WebElement> list_dropdownSpeciality;

    @FindBy(xpath = "//div[@class='note-editing-area']//div[contains(@class,'note-editable')]")
    public WebElement input_procedureNoteContent;

    @FindBy(xpath = "//input[@onclick='procedure_note_form_validate()']")
    public WebElement button_saveProcedureNote;


}