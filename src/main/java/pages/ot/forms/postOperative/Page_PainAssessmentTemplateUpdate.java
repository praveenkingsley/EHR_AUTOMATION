package pages.ot.forms.postOperative;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_PainAssessmentTemplateUpdate extends TestBase {
    private WebDriver driver;

    public Page_PainAssessmentTemplateUpdate(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='nursing_record_treatmentmedication_attributes_1_medicinename']")
    public WebElement input_medicationNameInSecondRowPainAssessmentTemplate;

}
