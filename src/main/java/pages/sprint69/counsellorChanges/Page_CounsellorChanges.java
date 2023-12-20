package pages.sprint69.counsellorChanges;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_CounsellorChanges extends TestBase {

    private WebDriver driver;

    public Page_CounsellorChanges(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//button[contains(text(),'Counselling')]")
    public WebElement button_counsellingDropdownButton;

    @FindBy(xpath = "//button[contains(text(),'Followup')]")
    public WebElement button_followupDropdownButton;

    @FindBy(xpath = "//a[contains(text(),'New Counselling Record')]")
    public WebElement button_newCounsellingRecordButton;

    @FindBy(xpath = "//a[contains(text(),'New Counselling Followup')]")
    public WebElement button_newCounsellingFollowupButton;

    @FindBy(xpath = "//h4[text()='Counselling Record']")
    public WebElement header_counsellingRecordTemplateHeader;

    @FindBy(xpath = "//h4[text()='Counselling Followup']")
    public WebElement header_counsellingFollowupTemplateHeader;

    @FindBy(xpath = "//select[@id='order_counselling_record_counselled_by_id']")
    public WebElement select_orderCounselledBy;

    @FindBy(xpath = "//div[contains(text(),'Counselled by:')]")
    public WebElement text_counselledByInFollowUp;

    @FindBy(xpath = "//select[@id='order_followup_counselled_by_id']")
    public WebElement select_followupCounselledBy;

    @FindBy(xpath = "//a[text()='Procedures']/parent::li")
    public WebElement tab_proceduresTabInTemplate;

    @FindBy(xpath = "//a[contains(text(),'Investigations')]/parent::li")
    public WebElement tab_investigationsTabInTemplate;

    @FindBy(xpath = "//li[@class='active']//a[contains(text(),'Ophthal')]")
    public WebElement tab_ophthalmologyInvestigationTab;

    @FindBy(xpath = "//div[contains(@id,'investigations')]//a[contains(text(),'Laboratory')]")
    public WebElement tab_laboratoryInvestigationTab;

    @FindBy(xpath = "//div[contains(@id,'investigations')]//a[contains(text(),'Radiology')]")
    public WebElement tab_radiologyInvestigationTab;

    @FindBy(xpath = "//input[@id='search_procedure_keyword']")
    public WebElement input_searchProcedureInputBox;

    @FindBy(xpath = "//input[@id='search_ophthal_investigations']")
    public WebElement input_searchOphthalmologyInvestigationInputBox;

    @FindBy(xpath = "//input[@id='search_laboratory_investigations']")
    public WebElement input_searchLaboratoryInvestigationInputBox;

    @FindBy(xpath = "//input[@id='search_radiology_investigations']")
    public WebElement input_searchRadiologyInvestigationInputBox;

    @FindBy(xpath = "//div[@id='procedures']/div/div/strong")
    public List<WebElement> list_proceduresTableHeaderList;

    @FindBy(xpath = "//div[@id='procedures']//div[@class='row orders-data']/div[1]")
    public List<WebElement> list_advisedProceduresList;

    @FindBy(xpath = "//div[@id='procedures']//div[@class='row orders-data']/div[2]/select")
    public List<WebElement> list_counsellingOutcomeBoxList;

    @FindBy(xpath = "//div[@id='procedures']//div[@class='row orders-data']/div[3]")
    public List<WebElement> list_currentStatusList;

    @FindBy(xpath = "//div[@id='procedures']//div[@class='row orders-data']/div[5]/input")
    public List<WebElement> list_patientCommentBoxList;

    @FindBy(xpath = "//div[@id='procedures']//div[@class='row orders-data']/div[6]/input")
    public List<WebElement> list_counsellorCommentBoxList;

    @FindBy(xpath = "//div[@id='procedures']//div[@class='row orders-data']/div[7]")
    public List<WebElement> list_procedureActionList;

    @FindBy(xpath = "//div[@id='ophthalmology']//div[@class='row orders-data']/div[1]")
    public List<WebElement> list_advisedOphthalmologyInvestigationList;

    @FindBy(xpath = "//div[@id='ophthalmology']//div[@class='row orders-data']/div[2]/select")
    public List<WebElement> list_ophthalmologyCounsellingOutcomeBoxList;

    @FindBy(xpath = "//div[@id='ophthalmology']//div[@class='row orders-data']/div[3]")
    public List<WebElement> list_ophthalmologyCurrentStatusList;

    @FindBy(xpath = "//div[@id='ophthalmology']//div[@class='row orders-data']/div[5]/input")
    public List<WebElement> list_ophthalmologyPatientCommentBoxList;

    @FindBy(xpath = "//div[@id='ophthalmology']//div[@class='row orders-data']/div[6]/input")
    public List<WebElement> list_ophthalmologyCounsellorCommentBoxList;

    @FindBy(xpath = "//div[@id='ophthalmology']//div[@class='row orders-data']/div[7]")
    public List<WebElement> list_ophthalmologyActionList;

    @FindBy(xpath = "//div[@id='laboratory']//div[@class='row orders-data']/div[1]")
    public List<WebElement> list_advisedLaboratoryInvestigationList;

    @FindBy(xpath = "//div[@id='laboratory']//div[@class='row orders-data']/div[2]/select")
    public List<WebElement> list_laboratoryCounsellingOutcomeBoxList;

    @FindBy(xpath = "//div[@id='laboratory']//div[@class='row orders-data']/div[3]")
    public List<WebElement> list_laboratoryCurrentStatusList;

    @FindBy(xpath = "//div[@id='laboratory']//div[@class='row orders-data']/div[5]/input")
    public List<WebElement> list_laboratoryPatientCommentBoxList;

    @FindBy(xpath = "//div[@id='laboratory']//div[@class='row orders-data']/div[6]/input")
    public List<WebElement> list_laboratoryCounsellorCommentBoxList;

    @FindBy(xpath = "//div[@id='laboratory']//div[@class='row orders-data']/div[7]")
    public List<WebElement> list_laboratoryActionList;

    @FindBy(xpath = "//div[@id='radiology']//div[@class='row orders-data']/div[1]")
    public List<WebElement> list_advisedRadiologyInvestigationList;

    @FindBy(xpath = "//div[@id='radiology']//div[@class='row orders-data']/div[2]/select")
    public List<WebElement> list_radiologyCounsellingOutcomeBoxList;

    @FindBy(xpath = "//div[@id='radiology']//div[@class='row orders-data']/div[3]")
    public List<WebElement> list_radiologyCurrentStatusList;

    @FindBy(xpath = "//div[@id='radiology']//div[@class='row orders-data']/div[5]/input")
    public List<WebElement> list_radiologyPatientCommentBoxList;

    @FindBy(xpath = "//div[@id='radiology']//div[@class='row orders-data']/div[6]/input")
    public List<WebElement> list_radiologyCounsellorCommentBoxList;

    @FindBy(xpath = "//div[@id='radiology']//div[@class='row orders-data']/div[7]")
    public List<WebElement> list_radiologyActionList;


    @FindBy(xpath = "//input[@class='form-control btn btn-sm btn-success']")
    public WebElement input_saveCounsellingRecord;

    @FindBy(xpath = "//a[contains(text(),'procedure1')]")
    public WebElement text_procedureTextFromProceduresList;

    @FindBy(xpath = "//select[@class='form-control procedure-side']")
    public WebElement select_procedureSideFormControl;

    @FindBy(xpath = "//ul[@aria-labelledby='counselling-record-dropdown']/li/a")
    public WebElement list_counsellingDropdownTodayRecords;

    @FindBy(xpath = "//button[text()='Cancel']")
    public WebElement button_closeCounsellingRecordTemplate;

    @FindBy(xpath = "//div[@id='procedures']/div[1]/div/strong")
    public List<WebElement> list_followupProcedureTableHeaderList;

    @FindBy(xpath = "//div[@id='procedures']/div")
    public List<WebElement> list_followupProceduresList;

    @FindBy(xpath = "//div[@class='brighttheme ui-pnotify-container brighttheme-notice ui-pnotify-shadow']")
    public WebElement text_followupErrorMessage;

    @FindBy(xpath = "//div[@class='row orders-count']")
    public WebElement text_totalOrderCountText;

    @FindBy(xpath = "//input[@id='order_followup_followup_date']")
    public WebElement input_followupOrderDate;

    @FindBy(xpath = "//td[contains(@class,' ui-datepicker-days-cell-over')]/following-sibling::td[1]")
    public WebElement date_nextDateToCurrentDate;

    @FindBy(xpath = "//input[@id='order_followup_followup_time']")
    public WebElement input_followupOrderTime;

    @FindBy(xpath = "//a[@data-action='incrementHour']")
    public WebElement input_followupTimeHourIncrement;

    @FindBy(xpath = "//label[@for='order_followup_followup_type_in_person']")
    public WebElement input_followupTypeInPerson;

    @FindBy(xpath = "//label[@for='order_followup_followup_type_over_phone']")
    public WebElement input_followupTypeOverPhone;

    @FindBy(xpath = "//select[@id='order_followup_followup_for_id']")
    public WebElement select_followupCounsellor;

    @FindBy(xpath = "//input[@value='Save - Counsel Followup']")
    public WebElement input_saveFollowupButton;

    @FindBy(xpath = "//ul[@aria-labelledby='followup-dropdown']/li[2]/a")
    public WebElement list_followupDropdownTodayFirstRecords;

    @FindBy(xpath = "//b[text()='Followup Details:']/parent::div/following-sibling::div/a")
    public WebElement text_followupDetailsTextInPatientRHS;

    @FindBy(xpath = "//button[@id='btn-appointment-frontdate']/i")
    public WebElement button_appointmentForwardDateButton;

    @FindBy(xpath = "//button[@id='btn-appointment-today']")
    public WebElement button_appointmentTodayButton;

    @FindBy(xpath = "//li[@class='full-width councellor_followup ']/a/span")
    public WebElement button_followUpButtonInAppointmentPatientList;

    @FindBy(xpath = "//input[@value='Update - Counsel Followup']")
    public WebElement input_updateFollowupButton;

    @FindBy(xpath = "(//input[@class='orders-procedures'])[1]")
    public WebElement input_firstProcedureCheckBox;

    @FindBy(xpath = "//a[@class='btn btn-sm btn-primary btn-primary-transparent dropdown-toggle']")
    public WebElement button_plusAppointmentButtonInPatientRHS;

    @FindBy(xpath = "//span[@id='select2-appointment_consultant_id-container']")
    public WebElement select_consultantNameInAddPatient;

    @FindBy(xpath = "//button[text()='All Orders']")
    public WebElement button_allOrderButton;

    @FindBy(xpath = "//h4[text()='Orders']")
    public WebElement header_allOrderTemplateHeader;

    @FindBy(xpath = "//table[@id='order-procedures-table']/tbody/tr/td[1]")
    public List<WebElement> list_allOrderProceduresOrderList;

    @FindBy(xpath = "//table[@id='order-procedures-table']/tbody/tr/td[2]")
    public List<WebElement> list_allOrderProceduresStatusList;

    @FindBy(xpath = "//table[@id='order-ophthal-table']/tbody/tr/td[1]")
    public List<WebElement> list_allOrderOphthalmologyOrderList;

    @FindBy(xpath = "//table[@id='order-ophthal-table']/tbody/tr/td[2]")
    public List<WebElement> list_allOrderOphthalmologyStatusList;

    @FindBy(xpath = "//table[@id='order-laboratory-table']/tbody/tr/td[1]")
    public List<WebElement> list_allOrderLaboratoryOrderList;

    @FindBy(xpath = "//table[@id='order-laboratory-table']/tbody/tr/td[2]")
    public List<WebElement> list_allOrderLaboratoryStatusList;

    @FindBy(xpath = "//table[@id='order-radiology-table']/tbody/tr/td[1]")
    public List<WebElement> list_allOrderRadiologyOrderList;

    @FindBy(xpath = "//table[@id='order-radiology-table']/tbody/tr/td[2]")
    public List<WebElement> list_allOrderRadiologyStatusList;




}
