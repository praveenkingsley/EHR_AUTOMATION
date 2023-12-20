package pages.optometrist;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Pages_Optometrist extends TestBase {

	@SuppressWarnings("unused")
	private WebDriver driver;

	public Pages_Optometrist(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}


	@FindBy(xpath = "//a[@data_templatetype='optometrist']")
	public WebElement button_OptometristTemplate;
	// vision,iop and history tab colour
	@FindBy(xpath = "//li[@class='refraction_step_vision btn-pointer my-danger my-error my-error-dark']")
	public WebElement tab_RedColourInVisionTab;

	@FindBy(xpath = "//li[@class='refraction_step_vision btn-pointer my-success my-success-dark']")
	public WebElement tab_GreenColourInVisioTab;

	@FindBy(xpath = "//li[@class='refraction_step_iop btn-pointer my-danger my-error my-error-dark']")
	public WebElement tab_RedColourInIopTab;

	@FindBy(xpath = "//li[@class='refraction_step_iop btn-pointer my-success my-success-dark']")
	public WebElement tab_GreenColourInIopTab;

	@FindBy(xpath = "//li[@class='history_step btn-pointer my-danger my-error my-error-dark']")
	public WebElement tab_RedColourInHistoryTab;

	@FindBy(xpath = "//li[@class='history_step btn-pointer my-success my-success-dark']")
	public WebElement tab_GreenColourInHistoryTab;

	// History and Refraction tab
	@FindBy(xpath = "//li[@id='refraction_step']")
	public WebElement tab_RefractionTab;

	@FindBy(xpath = "//li[@id='history_step']")
	public WebElement tab_HistoryTab;

	@FindBy(xpath = "//h4[contains(text(),'Optometrist Template')]")
	public WebElement text_NameOfTemplate;

	// UCVA Absent/unaided under visual acuity R/OD
	@FindBy(xpath = "//div[@target-input-id='opdrecord_r_visualacuity_unaided']/button[@name='r_visualacuity_unaided']")
	public List<WebElement> buttons_ucvaAbsentUnderVisualAcuityRight;

	// UCVA Absent/unaided under visual acuity L/OD
	@FindBy(xpath = "//div[@target-input-id='opdrecord_l_visualacuity_unaided']/button[@name='l_visualacuity_unaided']")
	public List<WebElement> buttons_ucvaAbsentUnderVisualAcuityLeft;

	// comments under ucva R/OD
	@FindBy(xpath = "//input[@id='opdrecord_r_visualacuity_unaided_comments']")
	public WebElement input_ucvaCommentUnderVisualAcuityRightSide;

	// comments under ucva R/OD
	@FindBy(xpath = "//input[@id='opdrecord_l_visualacuity_unaided_comments']")
	public WebElement input_ucvaCommentUnderVisualAcuityLeftSide;

	// comments under visual acuity R/OD
	@FindBy(xpath = "//textarea[@id='opdrecord_r_visualacuity_comments']")
	public WebElement input_CommentUnderVisualAcuityRightSide;

	// comments under visual acuity L/OD
	@FindBy(xpath = "//textarea[@id='opdrecord_l_visualacuity_comments']")
	public WebElement input_CommentUnderVisualAcuityLefttSide;

	// fill interocular pressure in template
	@FindBy(xpath = "//input[@id='opdrecord_r_intraocularpressure']")
	public WebElement input_rightIop;

	@FindBy(xpath = "//input[@id='opdrecord_l_intraocularpressure']")
	public WebElement input_leftIop;

	// check box selection under history----Not working
	// @FindBy(xpath = "//input[@id='opdrecord_no_chief_complaints_advised']")

	@FindBy(xpath = "//input[@id='opdrecord_no_chief_complaints_advised']")
	public WebElement button_ChiefComplaintsCheckBox;

	@FindBy(xpath = "//input[@id='opdrecord_no_opthalmic_history_advised']")
	public WebElement button_OphthalmicHistoryCheckBox;

	@FindBy(xpath = "//input[@id='opdrecord_no_systemic_history_advised']")
	public WebElement button_SystemmicHistoryCheckBox;

	@FindBy(xpath = "//input[@id='opdrecord_no_allergy_advised']")
	public WebElement button_AllergyCheckBox;





	// Glass prescription

	@FindBy(xpath = "//select[@id='opdrecord_r_acceptance_typeoflens']/option")
	public List<WebElement> listbutton_ToSelectOfTypeOfLens;

	@FindBy(xpath = "//select[@id='opdrecord_r_acceptance_lensmaterial']/option")
	public List<WebElement> listbutton_ToSelectOfLensMaterial;

	@FindBy(xpath = "//select[@id='opdrecord_r_acceptance_lenstint']/option")
	public List<WebElement> listbutton_ToSelectOfLensTint;

	@FindBy(xpath = "//input[@id='opdrecord_r_acceptance_ipd']")
	public WebElement input_ToFillIpdValues;

	@FindBy(xpath = "//select[@id='opdrecord_r_acceptance_framematerial']/option")
	public List<WebElement> listbutton_ToSelectOfFrameMaterial;

}
