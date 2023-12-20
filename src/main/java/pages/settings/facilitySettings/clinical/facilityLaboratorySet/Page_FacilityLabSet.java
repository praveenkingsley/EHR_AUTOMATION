package pages.settings.facilitySettings.clinical.facilityLaboratorySet;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import data.settingsData.FacilitySettings_Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_FacilityLabSet extends TestBase {
    private WebDriver driver;

    public Page_FacilityLabSet(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@href='/facility_laboratory_lists/new']")
    public WebElement link_laboratorySet;

    @FindBy(xpath = "//b[text()='My Practice Laboratory List']")
    public WebElement text_myPracticeLaboratoryList;


    @FindBy(xpath = "//h4[text()='My Practice Laboratory List']")
    public WebElement text_EditmyPracticeLaboratoryList;

    @FindBy(xpath = "//input[@id='facility_laboratory_list_name']")
    public WebElement input_setName;

    @FindBy(xpath = "//button[text()='OPD']")
    public WebElement button_setTypeOpd;

    @FindBy(xpath = "//input[@id='OphthalRadio1']")
    public WebElement input_standardInvestigation;

    @FindBy(xpath = "//select[@id='facility_laboratory_list_toplaboratoryinvestigation']")
    public WebElement select_standardInvestigation;

    @FindBy(xpath = "//button[text()='Save Laboratory List']")
    public WebElement button_saveLaboratoryList;

    @FindBy(xpath = "//tbody[@class='laboratory-set-list']/tr/td[1]")
    public List<WebElement> inputOptions_laboratorySet;

    @FindBy(xpath = "//button[@id='opd-templates']")
    public WebElement button_templateDetails;

    @FindBy(xpath = "//a[@data_templatetype='eye']")
    public WebElement link_eyeTemplate;

    @FindBy(xpath = "//select[@id='facility_laboratory_list_toplaboratoryinvestigation']")
    public WebElement select_investigation;

    @FindBy(xpath = "//button[text()='Save']")
    public WebElement button_saveTemplate;

    @FindBy(xpath = "(//*[text()='Investigation'])[1]")
    public WebElement tab_investigation;

    @FindBy(xpath = "//strong[text()='Laboratory']")
    public WebElement strong_laboratoryTab;

    @FindBy(xpath = "//select[@id='opdrecord_toplaboratoryset']")
    public WebElement select_laboratorySet;

    @FindBy(xpath = "//td[text()='"+ FacilitySettings_Data.sSet_Name+"']/..//td[4]/a[1]")
    public WebElement link_editLabSet;

    @FindBy(xpath = "//a[text()='Mark Patient Arrived']")
    public WebElement link_markPatientArrived;

    @FindBy(xpath = "//td[text()='"+ FacilitySettings_Data.sSet_EditName+"']/..//td[4]/a[2]")
    public WebElement link_deleteLabSet;

    @FindBy(xpath = "//button[text()='Confirm']")
    public WebElement button_confirmDelete;

    @FindBy(xpath = "//a[text()='NA']")
    public WebElement link_notArrived;

    @FindBy(xpath = "//button[text()='Close']")
    public WebElement button_closeTemplate;

    @FindBy(xpath = "//div[@id='show-facility-contacts-details']//tbody/tr/td[5]/a")
    public List<WebElement> list_editButtonsOnContactListTable;

    @FindBy(xpath = "//input[@id='cancel_appointment']")
    public WebElement button_cancelAppointmentButtonInForm;
    @FindBy(xpath = "//a[contains(text(),' Schedule Admission')]")
    public WebElement button_scheduleAdmission;
    @FindBy(xpath = "//a[contains(text(),'Insurance Details')]")
    public WebElement tab_insuranceDetailsTabInScheduleAdmission;
    @FindBy(xpath = "//input[@id='is_insured_true']")
    public WebElement radioButton_cashlessHospitalisation;

    @FindBy(xpath = "//span[@id='select2-admission_tpa_contact_id-container']")
    public WebElement select_tpaContact;
    @FindBy(xpath = "//ul[@class='select2-results__options']//li")
    public List<WebElement> list_tpaContactFromDropdown;

    @FindBy(xpath = "//input[@id='facility_contact_for_expense_box']")
    public WebElement checkbox_showInExpense;

    @FindBy(xpath = "//label[@for='facility_contact_for_expense_box']")
    public WebElement text_showInExpense;
    @FindBy(xpath = "//button[contains(text(),'Close')]")
    public WebElement button_closeScheduleAdmissionModel;

}
