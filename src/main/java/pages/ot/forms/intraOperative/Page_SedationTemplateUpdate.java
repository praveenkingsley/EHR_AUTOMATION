package pages.ot.forms.intraOperative;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_SedationTemplateUpdate extends TestBase {
    private WebDriver driver;

    public Page_SedationTemplateUpdate(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(text(),'Sedation')]")
    public WebElement button_sedation;

    @FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_1_respiration_rate']")
    public WebElement input_respirationRateUnderSedationChartInOt;

    @FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_1_blood_pressure']")
    public WebElement input_bloodPressureUnderSedationChartInOt;

    @FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_1_pulse']")
    public WebElement input_pulseUnderSedationChartInOt;

    @FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_1_sedation_score']")
    public WebElement input_sedationScoreUnderSedationChartInOt;

    @FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_1_medication']")
    public WebElement input_medicationUnderSedationChartInOT;

    @FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_1_bolus_ml']")
    public WebElement input_bolusmlUnderSedationChartInOT;

    @FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_1_bolus_mg']")
    public WebElement input_bolusmgUnderSedationChartInOt;

    @FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_1_o2_saturation']")
    public WebElement input_o2SaturationUnderSedationChartInOt;

    @FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_1_comments']")
    public WebElement input_commentsUnderSedationChartInOT;

    @FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_1_name']")
    public WebElement input_nameUnderSedationChartInOT;

}
