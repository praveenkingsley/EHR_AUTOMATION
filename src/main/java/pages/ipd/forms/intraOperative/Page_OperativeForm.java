package pages.ipd.forms.intraOperative;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

import java.util.List;

public class Page_OperativeForm extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_OperativeForm(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // operative form
    @FindBy(xpath = "//button[contains(text(),' Operative ')]")
    public WebElement button_operativeNote;

    @FindBy(xpath = "//textarea[@id='inpatient_ipd_record_operative_notes_attributes_0_personnel_comments']")
    public WebElement input_personalCommentBox;

    @FindBy(xpath = "//a[@id='operative-note-btn']")
    public WebElement button_newOperativeNote;

    @FindBy(xpath = "//button[@class='btn btn-default btn-next']")
    public WebElement button_next;

    @FindBy(xpath = "//input[@id='surgeon_1']")
    public WebElement radioButtons_surgeon1InOperativeForm;

    @FindBy(xpath = "//div[@class='show-bom-items']")
    public WebElement button_bomItemsInOperativeForm;

    @FindBy(xpath = "//button[contains(text(),'Select Bill Of Material ')]")
    public WebElement button_selectBillOfMaterialInOperativeForm;

    @FindBy(xpath = "//a[@class=' bill-of-material-note'][1]")
    public WebElement button_selectBillOfMaterialFromDropdownInOperativeForm;

    @FindBy(xpath = "//input[@id='inpatient_ipd_record_operative_notes_attributes_0_irrigation_solution']")
    public WebElement input_irrigationSolutionInOperativeForm;

    @FindBy(xpath = "//input[@id='inpatient_ipd_record_operative_notes_attributes_0_irrigation_solution_batch_no']")
    public WebElement input_irrigationSolutionBatchNoInOperativeForm;

    @FindBy(xpath = "//button[@class='btn btn-xs btn-white surgerytype custom-radio-button '][1]")
    public WebElement button_typeOfSurgeryInOperativeForm;

    @FindBy(xpath = "//li[@class='list-group-item saved_procedure_note'][1]")
    public WebElement button_savedSurgeryNotesInOperativeForm;

    @FindBy(xpath = "//textarea[@class='form-control compliation_comment']")
    public WebElement input_complicationCommentInOperativeForm;

    @FindBy(xpath = "//textarea[@id='inpatient_ipd_record_operative_notes_attributes_0_post_op_plan']")
    public WebElement input_PostOpPlanInOperativeForm;

    @FindBy(xpath = "//label[@for='device_ctr']")
    public WebElement checkbox_ctrDeviceInOperativeForm;

    @FindBy(xpath = "//div[@class='pull-right']//input[contains(@class,'btn btn-success btn-sm')]")
    public WebElement button_saveOperativeForm;

    @FindBy(xpath = "//ul[@id='operative-note-steps']/li[text()=' Surgical Note']")
    public WebElement tab_surgicalNote;

    @FindBy(xpath = "//input[contains(@class,'search_procedure_notes')]")
    public WebElement input_searchProcedureNote;

    @FindBy(xpath = "//ul[contains(@class,'saved_procedure_notes')]/li")
    public List<WebElement> list_savedProcedureNotes;
    @FindBy(xpath = "//input[contains(@id,'is_performed')][@type='checkbox']")
    public List<WebElement> list_checkboxProcedure;
    @FindBy(xpath = "//select[contains(@id,'ipd_record_procedure')]")
    public List<WebElement> list_selectProcedurePerformed;

    @FindBy(xpath = "//input[@id='print_history']")
    public WebElement checkbox_history;

    @FindBy(xpath = "//strong[text()='History :']")
    public List<WebElement> list_historySection;

    @FindBy(xpath = "//div[@class='col-md-12']/li")
    public List<WebElement> list_historiesUnderDischargeSummaryAndNote;
}
