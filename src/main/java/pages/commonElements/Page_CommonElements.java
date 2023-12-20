package pages.commonElements;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_CommonElements extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_CommonElements(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//button[contains(@class,'saveopdrecord')]")
    public WebElement button_SaveTemplate;

    @FindBy(xpath = "//button[text()='Close']")
    public WebElement button_closeTemplateWithoutSaving;

    @FindBy(xpath = "//div[@class='modal-header']//button[text()='Close']")
    public WebElement button_CloseTemplate;

    @FindBy(xpath = "//button[@aria-label='Close']/span[text()='×']")
    public WebElement button_CloseModalWith_X;

    @FindBy(xpath = "//button[contains(text(),'Update')]")
    public WebElement button_updateTemplate;

    @FindBy(xpath = "(//div[@class='modal-header'])[1]")
    public WebElement header_modal;

    @FindBy(xpath = "(//h4[@class='template-name'])")
    public WebElement header_templateName;

    @FindBy(xpath = "//div[@class='modal-body']//a[contains(text(),'Edit')]")
    public WebElement button_editOnModal;

    @FindBy(xpath = "//div[@class='modal-body']//*[contains(text(),'Summary')]")
    public WebElement button_summaryOnModal;

    @FindBy(xpath = "//div[@class='modal-body']//*[contains(text(),'Note')]")
    public WebElement button_noteOnModal;

    @FindBy(xpath = "//div[@class='modal-body']//*[contains(text(),'Email')]")
    public WebElement button_emailOnModal;

    // Elements of inventory settings
    @FindBy(xpath = "//h4[text()='Item Level']")
    public WebElement header_storePopup;

    @FindBy(className = "ui-datepicker-title")
    public WebElement text_monthYearInDatePicker;

    @FindBy(xpath = "//a[@title='Next']")
    public WebElement button_nextDatePicker;
    @FindBy(xpath = "//h4[normalize-space()='Compose Email']")
    public WebElement header_composeEmail;
    @FindBy(xpath = "//span[@class='cancel-email']")
    public WebElement button_clearSentTo;
    @FindBy(xpath = "//input[contains(@name,'email-receiver-input')]")
    public WebElement input_sentToEmail;
    @FindBy(xpath = "//input[@id='mail_record_tracker_subject']")
    public WebElement input_subjectEmail;
    @FindBy(xpath = "//input[@value='Preview']")
    public WebElement button_previewEmail;
    @FindBy(xpath = "//a[normalize-space()='Send Email']")
    public WebElement button_sendEmail;

    @FindBy(xpath = "(//div[@class='replace_medication']/div/table/tbody/tr/td[2])[2]")
    public WebElement medicationListNameOnTemplatePreview;

    @FindBy(xpath = "(//div[contains(@class,'replace_ipd_medicine')]/div/table/tbody/tr/td[2])[2]")
    public WebElement medicationListNameOnDischargeTemplatePreview;

    @FindBy(xpath = "(//div[@class='replace_medication mb5']/div/table/tbody/tr/td[2])[2]")
    public WebElement medicationListNameOnCarePlanTemplatePreview;
    @FindBy(xpath = "//b[text()='Advance ID: ']/parent::div/following-sibling::div")
    public WebElement text_advanceId;
    @FindBy(xpath = "//b[contains(text(),'Bill Number')]/parent::div/following-sibling::div")
    public WebElement text_billNumberInStore;




    

}
