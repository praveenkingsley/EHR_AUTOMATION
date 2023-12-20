package pages.settings.facilitySettings.clinical.facilityMedicationSets;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_FacilityMedicationSets extends TestBase {
    private WebDriver driver;

    public Page_FacilityMedicationSets(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@id='medicationsets']//a[contains(text(),'Medication Set')]")
    public WebElement button_createMedicationSets;

    @FindBy(xpath = "//div[@id='medication-set-table_filter']//input")
    public WebElement input_medicationSetSearchFilter;

    @FindBy(xpath = "//input[@id='medication_kit_name']")
    public WebElement input_medicationSetName;

    @FindBy(xpath = "//div[@id='medication-set-table_wrapper']//tbody/tr/td[1]")
    public List<WebElement> list_rowsMedicationSetsNameOnTable;

    @FindBy(xpath = "//div[@id='medication-set-table_wrapper']//tbody/tr/td[2]//i[@class='fa fa-pencil-square-o']")
    public List<WebElement> list_MedicationSetEditButtonOnTable;

    @FindBy(xpath = "//div[@id='medication-set-table_wrapper']//tbody/tr/td[2]//i[@class='fa fa-trash-alt']")
    public List<WebElement> list_MedicationSetDeleteButtonOnTable;

    @FindBy(xpath = "//div[@target-input-id='medication_kit_set_type']/button")
    public List<WebElement> list_buttonsSetType;

    @FindBy(xpath = "//select[@id='opdrecord_specalityid']")
    public WebElement select_specialities;

    // Rows
    @FindBy(xpath = "//tr[@class='treatmentmedications']")
    public List<WebElement> list_rowsOnMedicationSet;

    // List Medicine Name
    @FindBy(xpath = "//tr[@class='treatmentmedications']/td//input[contains(@id,'medicinename')]")
    public List<WebElement> list_input_MedicineName;

    // List Medicine Name
    @FindBy(xpath = "//tr[@class='treatmentmedications']/td//input[contains(@name,'[medicinetype]')]")
    public List<WebElement> list_input_MedicineType;

    // List Medicine Quantity
    @FindBy(xpath = "//tr[@class='treatmentmedications']/td//select[contains(@id,'medicinequantity')]")
    public List<WebElement> list_select_MedicineQuantity;

    // List Medicine Frequency
    @FindBy(xpath = "//tr[@class='treatmentmedications']/td//select[contains(@id,'medicinefrequency')]")
    public List<WebElement> list_select_MedicineFrequency;

    // List Medicine Duration
    @FindBy(xpath = "//tr[@class='treatmentmedications']/td//select[contains(@name,'[medicineduration]')]")
    public List<WebElement> list_select_MedicineDuration;

    // List Medicine Duration Option
    @FindBy(xpath = "//tr[@class='treatmentmedications']/td//select[contains(@id,'medicinedurationoption')]")
    public List<WebElement> list_select_MedicineDurationOption;

    // List Tapering Button
    @FindBy(xpath = "//tr[@class='treatmentmedications']/td//span[contains(@class,'tapering_btn')]")
    public List<WebElement> list_button_MedicineTapering;

    // List Eye Side
    @FindBy(xpath = "//tr[@class='treatmentmedications']/td//select[contains(@id,'eyeside')]")
    public List<WebElement> list_select_MedicineEyeSide;

    // List Instruction
    @FindBy(xpath = "//tr[@class='treatmentmedications']/td//select[contains(@id,'instruction')]")
    public List<WebElement> list_select_MedicineInstruction;

    // List Remove Button
    @FindBy(xpath = "//tr[@class='treatmentmedications']/td//a[contains(@id,'removemedicationbutton1')]")
    public List<WebElement> list_button_MedicineRowRemove;

    // List Autocomplete suggestion
    @FindBy(xpath = "//ul[contains(@class, 'ui-autocomplete ui-front')]/li/a")
    public List<WebElement> list_rowsMedicineNameAutoCompleteSuggestions;

    //    Add Medication Set
    @FindBy(xpath = "//input[@value='Add Medication Set']")
    public WebElement button_addMedicationSet;

    @FindBy(xpath = "//input[@value='Update Medication Set']")
    public WebElement button_updateMedicationSet;

    @FindBy(xpath = "//div[@class='bottom']//a[@class='paginate_button last']")
    public WebElement button_paginationBottomLast;

    // Delete Medication confirm Dialog Box
    @FindBy(xpath = "//h5[text()='Are you sure?']")
    public WebElement header_confirmDeleteMedicationSet;

    @FindBy(xpath = "//div[@class='modal-footer']//button[text()='Confirm']")
    public WebElement button_confirmDeleteMedicationSet;


}
