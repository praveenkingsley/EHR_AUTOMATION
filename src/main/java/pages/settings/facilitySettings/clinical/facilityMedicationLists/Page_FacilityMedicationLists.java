package pages.settings.facilitySettings.clinical.facilityMedicationLists;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_FacilityMedicationLists extends TestBase {
    private WebDriver driver;

    public Page_FacilityMedicationLists(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(text(),'Add  Medication List')]")
    public WebElement button_CreateMedicationLists;

    @FindBy(xpath = "//div[@id='medication-list-table_filter']//input")
    public WebElement input_MedicationListSearchFilter;

    @FindBy(xpath = "//strong[text()='Specialty:']/parent::div/following-sibling::div/select")
    public WebElement select_SpecialityForMedicationList;

    @FindBy(xpath = "//input[@placeholder='Medicine Name']")
    public WebElement input_MedicationName;

    @FindBy(xpath = "//strong[text()='Dispensing Unit :']/following-sibling::select")
    public WebElement select_DispensingUnit;

    @FindBy(xpath = "//input[@id='item_medicine_class']")
    public WebElement input_MedicationClass;
    @FindBy(xpath = "    //ul[@class='ui-menu ui-widget ui-widget-content ui-autocomplete ui-front']//li/a\n")
    public List<WebElement> list_medicineClassNameList;

    @FindBy(xpath = "//div[@id='item_class_section']/div/p")
    public List<WebElement> list_textMedicineClassName;

    @FindBy(xpath = "//div[@id='item_class_section']/div/span[@class='btn-remove-class']")
    public List<WebElement> list_ButtonForDeleteMedicineClass;

    @FindBy(xpath = "//ul[@class='ui-menu ui-widget ui-widget-content ui-autocomplete ui-front']/li")
    public List<WebElement> list_AutocompleteOptionsForMedicationClass;

    @FindBy(xpath = "//div[contains(@class,'generic-composition-row')]")
    public static List<WebElement> list_ItemsForGenericNames;
    @FindBy(xpath = "//div[contains(@class,'generic-composition-row')]//input[contains(@class,'item_name')]")
    public List<WebElement> list_InputForItemNameOnGenericComposition;

    @FindBy(xpath = "//div[contains(@class,'generic-composition-row')]//input[contains(@class,'item_quantity')]")
    public List<WebElement> list_InputForItemQuantityOnGenericComposition;

    @FindBy(xpath = "//div[contains(@class,'generic-composition-row')]//select[contains(@class,'item_unit')]")
    public List<WebElement> list_SelectForItemUnitOnGenericComposition;

    @FindBy(xpath = "//button[contains(@class,'btn-remove-item')]")
    public List<WebElement> list_ButtonForDeleteGenericComposition;

    @FindBy(xpath = "//span[@onclick='add_generic_composition()']")
    public WebElement button_AddGenericComposition;

    @FindBy(xpath = "//input[@id='generic_name_container']/preceding-sibling::div")
    public WebElement text_SelectedGenericName;

    @FindBy(xpath = "//div[@class='modal-footer']/button[text()='Close']")
    public WebElement button_CloseModalForAddMedicationList;

    @FindBy(xpath = "//div[@class='modal-footer']/input[@value='Save & Add Another']")
    public WebElement button_SaveAndAddAnotherForAddMedicationList;

    @FindBy(xpath = "//div[@class='modal-footer']/input[@value='Update']")
    public WebElement button_UpdateForAddMedicationList;

    @FindBy(xpath = "//tbody[@class='medication-list']/tr/td[1]")
    public List<WebElement> list_rowsMedicationNameOnMedicineLists;

    @FindBy(xpath = "//tbody[@class='medication-list']/tr/td[2]")
    public List<WebElement> list_rowsMedicationGenericNameOnMedicineLists;

    @FindBy(xpath = "//tbody[@class='medication-list']/tr/td[3]")
    public List<WebElement> list_rowsMedicationClassNameOnMedicineLists;

    @FindBy(xpath = "//tbody[@class='medication-list']/tr/td[4]")
    public List<WebElement> list_rowsMedicationDispensingTypeOnMedicineLists;

    @FindBy(xpath = "//tbody[@class='medication-list']/tr//a[@data-target='edit_medication']")
    public List<WebElement> list_ButtonForEditMedicineLists;

    @FindBy(xpath = "//tbody[@class='medication-list']/tr//a[@data-method='delete']")
    public List<WebElement> list_ButtonForDeleteMedicineLists;

    @FindBy(xpath = "//button[text()='Confirm']")
    public WebElement button_ConfirmDeleteMedication;

    @FindBy(xpath = "//tr[@class='treatmentmedications']/td//input[contains(@id,'medicinename')]")
    public List<WebElement> list_input_MedicineName;
    @FindBy(xpath = "//div[@class='brighttheme ui-pnotify-container brighttheme-error ui-pnotify-shadow']")
    public WebElement alert_deleteListWarning;

}
