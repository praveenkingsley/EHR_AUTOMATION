package pages.ot.forms.postOperative;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_DischargeNoteUpdate extends TestBase {
    private WebDriver driver;

    public Page_DischargeNoteUpdate(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='inpatient_ipd_record_discharge_note_attributes_procedure_code']")
    public WebElement input_procedureCodeInDischargeTemplate;

}
