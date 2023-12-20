package pages.Sprint88;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page_ShowMrnInInvestigationTemplateCreate extends TestBase {
    private WebDriver driver;

    public Page_ShowMrnInInvestigationTemplateCreate(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='row left_iop_row ']//input[@id='opdrecord_l_intraocularpressure']")
    public WebElement input_leftIopValue;

    @FindBy(xpath = "//div[@class='row right_iop_row']//input[@id='opdrecord_r_intraocularpressure']")
    public WebElement input_rightIopValue;

    @FindBy(xpath = "//label[@for='opdrecord_no_right_va_advised']")
    public WebElement input_right_va;

    @FindBy(xpath = "//label[@for='opdrecord_no_left_va_advised']")
    public WebElement input_left_va;

    @FindBy(xpath = "//a[@class='tab-link investigations-tab']//label")
    public WebElement button_patientRhsInvestigationTab;

    @FindBy(xpath = "//div[@id='investigationWizard']/ul/li[1]")
    public WebElement button_OpthalInvestigationTab;

    @FindBy(xpath = "(//a[contains(text(),'Performed')])[1]")
    public WebElement button_opthalInvestigationPerformedTab;

    @FindBy(xpath = "//input[@class='btn btn-success btn-xs']")
    public WebElement button_performedSave;

    @FindBy(xpath = "//a[@Class='btn btn-xs btn-success btn-success-transparent create-template']")
    public WebElement button_clickOnTemplate;

    @FindBy(xpath = "//div[@class='row text-left']//div[3]")
    public WebElement text_mrnInTemplate;

    @FindBy(xpath = "//input[@value='Create Ophthalmology record']")
    public WebElement button_createOpthalRecord;

    @FindBy(xpath = "//button[contains(text(),'Close')]")
    public WebElement button_recordCloseTab;

    @FindBy(xpath = "//div[text()='Patient ID']/following-sibling::div[2]")
    public WebElement text_patientIdOpdRhs;

    @FindBy(xpath = "//div[text()='MR No.']/following-sibling::div[2]")
    public WebElement text_mrnOpdRhs;

    @FindBy(xpath = "(//p[contains(text(),'Copy')])[1]")
    public WebElement button_copyPatientId;

    @FindBy(xpath = "//button[@id='patient-summary-timeline-link']")
    public WebElement button_summaryOption;

    @FindBy(xpath = "(//div[@class='hover-clip col-sm-7 no_padding'])[1]")
    public WebElement text_mrnOpdSummary;

    @FindBy(xpath = "(//div[@class='hover-clip col-sm-7 no_padding'])[2]")
    public WebElement text_patientIdOpdSummary;

    @FindBy(xpath = "(//p[contains(text(),'Copy')])[1]")
    public WebElement button_copyPatientIdSummary;

    @FindBy(xpath = "(//p[contains(text(),'Copy')])[1]")
    public WebElement button_copyMrnSummary;

    @FindBy(xpath = "//div[@id='investigationWizard']/ul/li[2]")
    public WebElement button_labInvestigationTab;

    @FindBy(xpath = "(//a[contains(text(),'Performed')])[3]")
    public WebElement button_labInvestigationPerformedTab;

    @FindBy(xpath = "//a[@Class='btn btn-xs btn-success btn-success-transparent create-template']")
    public WebElement button_labInvestigationClickOnTemplate;

    @FindBy(xpath = "//div[@class='row text-left']//div[3]")
    public WebElement text_mrnInLabInvestigationTemplate;

    @FindBy(xpath = "//input[@value='Create Laboratory record']")
    public WebElement button_createLabRecord;

    @FindBy(xpath = "//div[@id='investigationWizard']/ul/li[3]")
    public WebElement button_radInvestigationTab;

    @FindBy(xpath = "(//a[contains(text(),'Performed')])[7]")
    public WebElement button_radInvestigationPerformedTab;

    @FindBy(xpath = "//a[@Class='btn btn-xs btn-success btn-success-transparent create-template']")
    public WebElement button_radInvestigationClickOnTemplate;

    @FindBy(xpath = "//div[@class='row text-left']//div[3]")
    public WebElement text_mrnInRadInvestigationTemplate;

    @FindBy(xpath = "//input[@value='Create Radiology record']")
    public WebElement button_createRadiologyRecord;

    }
