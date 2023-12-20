package pages.ipd.forms.intraOperative;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_SedationChart extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_SedationChart(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[contains(text(),' Sedation Chart ')]")
	public WebElement button_sedationChart;

	@FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_0_respiration_rate']")
	public WebElement input_respirationRateUnderSedationChart;

	@FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_0_blood_pressure']")
	public WebElement input_bloodPressureUnderSedationChart;

	@FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_0_pulse']")
	public WebElement input_pulseUnderSedationChart;

	@FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_0_sedation_score']")
	public WebElement input_sedationScoreUnderSedationChart;

	@FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_0_medication']")
	public WebElement input_medicationUnderSedationChart;

	@FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_0_bolus_ml']")
	public WebElement input_bolusmlUnderSedationChart;

	@FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_0_bolus_mg']")
	public WebElement input_bolusmgUnderSedationChart;

	@FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_0_o2_saturation']")
	public WebElement input_o2SaturationUnderSedationChart;

	@FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_0_comments']")
	public WebElement input_commentsUnderSedationChart;

	@FindBy(xpath = "//input[@id='nursing_record_sedation_attributes_0_name']")
	public WebElement input_nameUnderSedationChart;

	@FindBy(xpath = "//input[@class='btn btn-success btn-sm submit_nursing']")
	public WebElement button_saveSedationChartTemplate;
}
