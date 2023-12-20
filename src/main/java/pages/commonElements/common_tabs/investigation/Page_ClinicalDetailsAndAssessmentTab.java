package pages.commonElements.common_tabs.investigation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
public class Page_ClinicalDetailsAndAssessmentTab {
    private WebDriver driver;
    public Page_ClinicalDetailsAndAssessmentTab(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//div[@id='MyWizard']//li[contains(text(),'Clinical Details & Assessment')]")
    public WebElement tab_ClinicalDetailsAndAssessment;
    @FindBy(xpath = "//strong[text()='Clinical Details']")
    public WebElement tab_clinicalDetailsUnderAssessmentTab;
    @FindBy(xpath = "//li/input[@type='search']")
    public WebElement input_clinicalDetails;
    @FindBy(xpath = "//strong[text()='DIAGNOSIS']")
    public WebElement tab_diagnosisUnderAssessmentTab;
}