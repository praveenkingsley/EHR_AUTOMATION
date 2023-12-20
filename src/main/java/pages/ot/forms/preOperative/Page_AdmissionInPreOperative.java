package pages.ot.forms.preOperative;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_AdmissionInPreOperative extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_AdmissionInPreOperative(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    // Pre Operative Section
    @FindBy(xpath = "//div[@id='admission_summary']//div[text()='Pre-Operative']")
    public WebElement text_preOperativeSection;

    @FindBy(xpath = "//div[text()='Pre-Operative']/following-sibling::div//a[contains(text(),'Admission')]")
    public WebElement button_admissionInPreOperative;

    @FindBy(xpath = "//div[text()='Pre-Operative']/following-sibling::div//a[contains(text(),'Admission')]/i[@class='fa fa-file-alt']")
    public WebElement button_docIconInAdmissionInPreOperative;

    @FindBy(xpath = "//ul[@id='clinical-note-steps']/li")
    public List<WebElement> tabs_admissionInPreOperative;

    @FindBy(xpath = "//ul[@id='clinical-note-steps']/li[contains(text(),'Admin')]")
    public WebElement tab_AdminTabOnAdmissionUnderPreOperative;

    @FindBy(xpath = "//div[contains(text(),'Reason for Admission')]/following-sibling::div/input")
    public WebElement input_reasonForAdmissionUnderAdminTab;

    @FindBy(xpath = "//ul[@id='clinical-note-steps']/li[contains(text(),'Case Clinical')]")
    public WebElement tab_CaseClinicalTabOnAdmissionUnderPreOperative;

    @FindBy(xpath = "//ul[@id='clinical-note-steps']/li[contains(text(),'History')]")
    public WebElement tab_HistoryTabOnAdmissionUnderPreOperative;

    @FindBy(xpath = "//div[@class='modal-header']//input[@name='commit'][@value='Save']")
    public WebElement button_saveOnModalHeader;

    @FindBy(xpath = "//div[@class='modal-header']//input[@name='commit'][@value='Update']")
    public WebElement button_updateOnModalHeader;



}