package pages.settings.facilityUserNote;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_FacilityUserNote extends TestBase {
    private WebDriver driver;

    public Page_FacilityUserNote(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//select[@id='user_note_type']")
    public WebElement select_userNoteType;
    @FindBy(xpath = "//a[contains(text(),' New Template')]//i")
    public WebElement button_newTemplateForAddingUserNote;
    @FindBy(xpath = "//input[@id='user_notes_template_name']")
    public WebElement input_nameFieldInUserNote;
    @FindBy(xpath = "//select[@id='opdrecord_specalityid']")
    public WebElement select_specialityForCreatingUserNote;
    @FindBy(xpath = "//input[@id='user_notes_template_template_details_category']")
    public WebElement input_headingFieldInUserNote;
    @FindBy(xpath = "//input[@id='user_notes_template_template_details_medical_subject']")
    public WebElement input_subjectFieldInUserNote;
    @FindBy(xpath = "//div[@class='note-editable panel-body']")
    public WebElement input_contentFieldInUserNote;
    @FindBy(xpath = "//input[@type='submit']")
    public WebElement button_submitUserNote;
    @FindBy(xpath = "//select[@id='user_notes_template_type']")
    public WebElement select_templateTypeForUserNote;
    @FindBy(xpath = "//input[@aria-controls='certificate-template-list']")
    public WebElement input_medicalCertificate;
    @FindBy(xpath = "//table[@id='certificate-template-list']//tr/td[1]")
    public List<WebElement> list_Certificates;
    @FindBy(xpath = "//table[@id='certificate-template-list']//tbody//tr//td//span[1]/a[1]")
    public List<WebElement> list_buttonForEditUserNote;
    @FindBy(xpath = "//button[@id='patient-summary-timeline-link']")
    public WebElement button_summary;
    @FindBy(xpath = "//span[contains(text(),'Certificates')]")
    public WebElement button_certificateInSummary;
    @FindBy(xpath = "//span[contains(text(),'Referral Message')]")
    public WebElement button_referralMessageInSummary;
    @FindBy(xpath = "//ul[@class='dropdown-menu children nopadding_dropdown']//li")
    public List<WebElement> list_medicalCertificatesInOpdSummary;
    @FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-right children nopadding_dropdown']//li")
    public List<WebElement> list_referralMessageInOpdSummary;
    @FindBy(xpath = "//table[@id='certificate-template-list']//tbody//tr//td//span[2]/a[1]")
    public List<WebElement> list_buttonForDeleteUserNote;
    @FindBy(xpath = "//button[@class='btn commit btn-danger']")
    public WebElement button_confirmDeleteMedicalCertificate;
    @FindBy(xpath = " //div[@class='modal false fade in']//button[@class='btn commit btn-danger'][normalize-space()='Confirm']")
    public WebElement button_confirmDeleteReferralMessage;
    @FindBy(xpath = "//input[@value='Save Medical Certificate']")
    public WebElement button_saveMedicalCertificateInSummary;
    @FindBy(xpath = "//input[@value='Save Referral Message']")
    public WebElement button_saveReferralMessageInSummary;
    @FindBy(xpath = "//button[@class='btn btn-info']")
    public WebElement button_closeUserNotePreview;
    @FindBy(xpath = "//input[@aria-controls='certificate-template-list']")
    public WebElement input_searchUserNote;
    @FindBy(xpath = "//a[contains(text(),'Mark Patient Arrived')]")
    public WebElement button_markPatientArrivedOPD;
    @FindBy(xpath = "//a[contains(text(),'Mark as Completed')]")
    public WebElement button_markPatientCompleted;
}
