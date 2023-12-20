package pages.ipd.forms.preOperative;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

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

    @FindBy(xpath = "//b[text()='Pre-Operative']/following::div//a[@id='assessment-note-btn']")
    public WebElement button_assessmentInPreOperative;

    @FindBy(xpath = "//div[@id='tpaFormRecordWizard']//a[text()='PRIMARY ASSESSMENT']")
    public WebElement tab_primaryAssessmentOnAssessmentFormInPreOperativeForm;

    @FindBy(xpath = "//div[@id='tpaFormRecordWizard']//a[text()='SECONDARY ASSESSMENT']")
    public WebElement tab_secondaryAssessmentOnAssessmentFormInPreOperativeForm;

    @FindBy(xpath = "//div[@id='tpaFormRecordWizard']//a[text()='VITAL SIGNS']")
    public WebElement tab_vitalSignsOnAssessmentFormInPreOperativeForm;

}