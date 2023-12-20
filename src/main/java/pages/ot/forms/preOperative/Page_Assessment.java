package pages.ot.forms.preOperative;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page_Assessment extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_Assessment(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    // Pre Operative Section
    @FindBy(xpath = "//div[@class='modal-header']//input[@name='commit'][@value='Save']")
    public WebElement button_saveOnModalHeader;

    @FindBy(xpath = "//div[text()='Pre-Operative']/following-sibling::div//a[contains(text(),'Assessment')]")
    public WebElement button_assessmentInPreOperative;

    @FindBy(xpath = "//div[text()='Pre-Operative']/following-sibling::div//a[contains(text(),'Assessment')]/i[@class='fa fa-file-alt']")
    public WebElement button_docIconInAssessmentInPreOperative;

    @FindBy(xpath = "//div[@id='tpaFormRecordWizard']//a[text()='PRIMARY ASSESSMENT']")
    public WebElement tab_primaryAssessmentOnAssessmentFormInPreOperativeForm;

    @FindBy(xpath = "//textarea[@id='patient_assessment_primary_comment']")
    public WebElement input_commentOnPrimaryAssessment;

    @FindBy(xpath = "//div[@id='tpaFormRecordWizard']//a[text()='SECONDARY ASSESSMENT']")
    public WebElement tab_secondaryAssessmentOnAssessmentFormInPreOperativeForm;

    @FindBy(xpath = "//textarea[@id='patient_assessment_secondary_comment']")
    public WebElement input_commentOnSecondaryAssessment;

    @FindBy(xpath = "//div[@id='tpaFormRecordWizard']//a[text()='VITAL SIGNS']")
    public WebElement tab_vitalSignsOnAssessmentFormInPreOperativeForm;

    @FindBy(xpath = "//textarea[@id='patient_assessment_vital_comment']")
    public WebElement input_commentOnVitalSigns;

    @FindBy(xpath = "//div[@class='modal-header']//input[@name='commit'][@value='Update']")
    public WebElement button_updateOnModalHeader;

}