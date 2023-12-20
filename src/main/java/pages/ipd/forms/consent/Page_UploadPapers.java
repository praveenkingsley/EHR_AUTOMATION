package pages.ipd.forms.consent;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_UploadPapers extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_UploadPapers(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }


    @FindBy(xpath = "//b[text()='Consents']/following::div//a[@id='patient-asset-btn']")
    public WebElement button_uploadPapersInConsent;

    @FindBy(xpath = "//input[@value='Upload Files']")
    public WebElement button_uploadFilesInUploadPapers_InConsent;

    @FindBy(xpath = "//span[text()=' Drag & Drop Files Here']")
    public WebElement section_dragAndDropInUploadPapers_InConsent;

    @FindBy(xpath = "//span[text()='Add files']")
    public WebElement button_AddFilesInUploadPapers_InConsent;


}