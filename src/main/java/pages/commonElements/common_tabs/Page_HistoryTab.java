package pages.commonElements.common_tabs;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_HistoryTab extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_HistoryTab(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	@FindBy(xpath = "//li[@id='history_step']")
	public WebElement tab_HistoryTab;

	@FindBy(xpath = "//button[@id='complaints_blurring_diminution_of_vision']")
	public WebElement button_OneChiefComplaints;

	@FindBy(xpath = "//button[@id='speciality_histories_glaucoma']")
	public WebElement button_OneOphthalmicHistory;

	@FindBy(xpath = "//button[@id='personal_histories_diabetes']")
	public WebElement button_SystemmicHistory;

	@FindBy(xpath = "//button[@id='food_allergies_all_seafood']")
	public WebElement button_Allergy;

	@FindBy(xpath = "//li[@class='history_step btn-pointer my-danger my-error my-error-dark']")
	public WebElement tab_RedColourInHistoryTab;

	@FindBy(xpath = "//li[@class='history_step btn-pointer my-success my-success-dark']")
	public WebElement tab_GreenColourInHistoryTab;

	@FindBy(xpath = "//label[contains(@for,'opthalmic_history_advised')]/preceding-sibling::input[@type='checkbox']")
	public WebElement checkbox_NIL_Ophthalmic_History;

	@FindBy(xpath = "//label[contains(@for,'systemic_history_advised')]/preceding-sibling::input[@type='checkbox']")
	public WebElement checkbox_NIL_Systemic_History;

	@FindBy(xpath = "//label[contains(@for,'chief_complaints_advised')]/preceding-sibling::input[@type='checkbox']")
	public WebElement checkbox_NIL_Chief_Complaints;

	@FindBy(xpath = "//label[contains(@for,'no_allergy_advised')]/preceding-sibling::input[@type='checkbox']")
	public WebElement checkbox_NIL_All_Allergies;

	@FindBy(xpath="//button[@name='speciality_histories']")
	public List<WebElement> list_buttonOphthalmicHistory;
	@FindBy(xpath = "//textarea[@id='opdrecord_opthal_history_comment']")
	public WebElement textarea_ophthalmicHistoryCommentBox;
	@FindBy(xpath = "//select[@class='form-control personal_field_add_field speciality_r_duration r_duration_field']")
	public WebElement select_rightDurationOphthalmicList;
	@FindBy(xpath = "//select[@class='form-control personal_field_add_field speciality_r_duration_unit r_duration_unit']")
	public WebElement select_rightDurationUnitOphthalmicList;
	@FindBy(xpath="//button[@name='personal_histories']")
	public List<WebElement> list_buttonSystemicHistory;
	@FindBy(xpath = "//select[contains(@id,'personal_history_records_attributes_0_duration')]")
	public WebElement select_durationSystemicList;
	@FindBy(xpath = "//textarea[@id='opdrecord_history_comment']")
	public WebElement textarea_systemicHistoryCommentBox;
	@FindBy(xpath = "//select[contains(@id,'personal_history_records_attributes_0_duration_unit')]")
	public WebElement select_durationUnitSystemicList;

	@FindBy(xpath = "//i[@class='fa fa-arrow-right ']")
	public WebElement button_copyButtonInGlaucoma;

	@FindBy(xpath="//input[@id='family_history']")
	public WebElement input_familyHistory;

	@FindBy(xpath="//input[@id='medical_history']")
	public WebElement input_medicalHistory;

	@FindBy(xpath = "//button[@name='drug_allergies']")
	public List<WebElement> list_buttonDrugAllergies;

	@FindBy(xpath = "//button[@name='contact_allergies']")
	public List<WebElement> list_buttonContactAllergies;

	@FindBy(xpath="//button[@id='antimicrobial_agents_ampicillin']")
	public WebElement button_ampicillinAntimicrobialAgents;

	@FindBy(xpath = "//div[@id='antimicrobial_agents_button_main_hide']//select[@class='form-control allergy_fields_add_field allergy_duration' or @class='form-control allergy_field_add_field allergy_duration']")
	public WebElement select_durationAntimicrobialAgentsList;

	@FindBy(xpath = "//div[@id='antimicrobial_agents_button_main_hide']//select[@class='form-control allergy_fields_add_field allergy_duration_unit' or @class = 'form-control allergy_field_add_field allergy_duration_unit']")
	public WebElement select_durationUnitAntimicrobialAgentsList;

	@FindBy(xpath = "//button[@name='food_allergies']")
	public List<WebElement> list_buttonFoodAllergies;

	@FindBy(xpath = "//div[@id='contact_allergies_main_hide']//select[@class='form-control allergy_fields_add_field allergy_duration' or @class ='form-control allergy_field_add_field allergy_duration']")
	public WebElement select_durationContactAllergiesList;

	@FindBy(xpath = "//div[@id='contact_allergies_main_hide']//select[@class='form-control allergy_fields_add_field allergy_duration_unit' or @class ='form-control allergy_field_add_field allergy_duration_unit']")
	public WebElement select_durationUnitContactAllergiesList;

	@FindBy(xpath = "//div[@id='food_allergies_main_hide']//select[@class='form-control allergy_fields_add_field allergy_duration' or @class='form-control allergy_field_add_field allergy_duration']")
	public WebElement select_durationFoodAllergiesList;

	@FindBy(xpath = "//div[@id='food_allergies_main_hide']//select[@class='form-control allergy_fields_add_field allergy_duration_unit' or @class ='form-control allergy_field_add_field allergy_duration_unit']")
	public WebElement select_durationUnitFoodAllergiesList;

	@FindBy(xpath = "//input[@id='opdrecord_others_allergies']")
	public WebElement input_otherAllergies;

	@FindBy(xpath = "//textarea[@id='opdrecord_drug_allergies_comment']")
	public WebElement textarea_drugAllergiesCommentBox;

	@FindBy(xpath = "//textarea[@id='opdrecord_contact_allergies_comment']")
	public WebElement textarea_contactAllergiesCommentBox;

	@FindBy(xpath = "//textarea[@id='opdrecord_food_allergies_comment']")
	public WebElement textarea_foodAllergiesCommentBox;

	@FindBy(xpath = "//button[@name='checkuptype']")
	public List<WebElement> list_buttonVisitType;
	@FindBy(xpath = "//*[@id='clear_checkuptype']")
	public WebElement button_clearVisitType;
	@FindBy(xpath = "//input[@id='opdrecord_checkuptypecomments']")
	public WebElement input_visitTypeComment;
	@FindBy(xpath="//button[@name='complaints']")
	public List<WebElement> list_buttonChiefComplaints;
	@FindBy(xpath = "//textarea[@id='opdrecord_chiefcomplaint_comments']")
	public WebElement input_commentChiefComplaints;
	//Ophthalmic History

	@FindBy(xpath = "//textarea[@id='opdrecord_opthal_history_comment']")
	public WebElement input_commentOphthalmicHistory;
	//Systemic History

	@FindBy(xpath = "//textarea[@id='opdrecord_history_comment']")
	public WebElement input_commentSystemicHistory;


	//Allergies

	@FindBy(xpath = "//textarea[@id='opdrecord_drug_allergies_comment']")
	public WebElement input_commentDrugAllergies;
	@FindBy(xpath = "//textarea[@id='opdrecord_contact_allergies_comment']")
	public WebElement input_commentContactAllergies;
	@FindBy(xpath = "//textarea[@id='opdrecord_food_allergies_comment']")
	public WebElement input_commentFoodAllergies;
	@FindBy(xpath = "//input[@id='opdrecord_others_allergies']")
	public WebElement input_otherAllergiesComment;
	//Vital Signs
	@FindBy(xpath="//input[@id='opdrecord_vital_signs_temperature']")
	public WebElement input_temperatureVitalSigns;
	@FindBy(xpath="//input[@id='opdrecord_vital_signs_pulse']")
	public WebElement input_pulseVitalSigns;
	@FindBy(xpath="//input[@id='opdrecord_vital_signs_bp']")
	public WebElement input_bloodPressureVitalSigns;
	@FindBy(xpath="//input[@id='opdrecord_vital_signs_rr']")
	public WebElement input_rrVitalSigns;
	@FindBy(xpath="//input[@id='opdrecord_vital_signs_spo2']")
	public WebElement input_spO2VitalSigns;
	@FindBy(xpath="//input[@id='opdrecord_anthropometry_height']")
	public WebElement input_heightVitalSigns;
	@FindBy(xpath="//input[@id='opdrecord_anthropometry_weight']")
	public WebElement input_weightVitalSigns;
	@FindBy(xpath="//input[@id='opdrecord_anthropometry_bmi']")
	public WebElement input_bmiVitalSigns;
	@FindBy(xpath="//textarea[@id='opdrecord_vital_signs_comments']")
	public WebElement input_commentVitalSigns;
	//nill Msg
	@FindBy(xpath = "//span[@class='chief_complaints_msg']")
	public WebElement txt_noChiefComplaintSelectMsg;
	@FindBy(xpath = "//span[@class='opthalmic_history_msg']")
	public WebElement txt_noOphthalmicHistorySelectMsg;
	@FindBy(xpath = "//span[@class='systemic_history_msg']")
	public WebElement txt_noSystemicHistorySelectMsg;
	@FindBy(xpath = "//span[@class='all_allergies_msg']")
	public WebElement txt_noAllergiesSelectMsg;
	@FindBy(xpath = "//li[@id='history_step']")
	public WebElement tab_History;
	@FindBy(xpath = "//button[contains(@class,'checkuptype') and contains(@class,' btn-darkblue')]")
	public List<WebElement> list_btnClickedInHistoryTab;
	@FindBy(xpath="//select[@id='opdrecord_view_owner_id']")
	public WebElement select_user_template;
	@FindBy(xpath="//h4[@class='template-name']")
	public WebElement text_templateName;
	@FindBy(xpath = "//textarea[contains(@id,'clincalevaluation')]/parent::div/descendant::div[contains(@class,'note-editable')]")
	public WebElement list_inputHistoryFreeForm;
	@FindBy(xpath = "//select[contains(@id,'punctum')]")
	public List<WebElement> list_selectSyringing_nursing;
	@FindBy(xpath="//input[contains(@id,'random_blood_sugar_result')]")
	public WebElement input_randomBloodSugar;
	@FindBy(xpath="//input[contains(@id,'random_blood_sugar_comment')]")
	public WebElement input_randomBloodSugarComments;
	@FindBy(xpath = "//div[contains(@class,'biometry')]//input")
	public List<WebElement> list_inputBiometryTemplate;
	//...........................................................................................................
	// xpath with parameter
	//History
	public WebElement select_sideChiefComplaints(String chiefComplaintValue){
		return driver.findElement(By.xpath("//*[contains(text(),'"+chiefComplaintValue+"')]/parent::div/following-sibling::div/descendant::select[contains(@id,'side')]"));
	}
	public WebElement select_durationChiefComplaints(String chiefComplaintValue){
		return driver.findElement(By.xpath("//*[contains(text(),'"+chiefComplaintValue+"')]/parent::div/following-sibling::div/descendant::select[contains(@id,'duration')]"));
	}
	public WebElement select_durationUnitChiefComplaints(String chiefComplaintValue){
		return driver.findElement(By.xpath("//*[contains(text(),'"+chiefComplaintValue+"')]/parent::div/following-sibling::div/descendant::select[contains(@id,'duration_unit')]"));
	}
	public WebElement input_commentChiefComplaint(String chiefCompliantValue){
		return driver.findElement(By.xpath("//*[contains(text(),'"+chiefCompliantValue+"')]/parent::div/following-sibling::div/descendant::input[contains(@id,'comment')]"));
	}
	//..Ophthalmic History..
	public WebElement select_rightDurationOphthalmicHistory(String ophthalmicHistoryValue){
		return driver.findElement(By.xpath("//*[text()='"+ophthalmicHistoryValue+"']/parent::div/following-sibling::div/descendant::select[contains(@class,'r_duration_field')]"));
	}
	public WebElement select_rightDurationUnitOphthalmicHistory(String ophthalmicHistoryValue){
		return driver.findElement(By.xpath("//*[text()='"+ophthalmicHistoryValue+"']/parent::div/following-sibling::div/descendant::select[contains(@class,'r_duration_unit')]"));
	}
	public WebElement select_leftDurationOphthalmicHistory(String ophthalmicHistoryValue){
		return driver.findElement(By.xpath("//*[text()='"+ophthalmicHistoryValue+"']/parent::div/following-sibling::div/descendant::select[contains(@class,'l_duration_field')]"));
	}
	public WebElement select_leftDurationUnitOphthalmicHistory(String ophthalmicHistoryValue){
		return driver.findElement(By.xpath("//*[text()='"+ophthalmicHistoryValue+"']/parent::div/following-sibling::div/descendant::select[contains(@class,'l_duration_unit')]"));
	}
	public WebElement input_commentOphthalmicHistory(String ophthalmicHistoryValue){
		return driver.findElement(By.xpath("//*[text()='"+ophthalmicHistoryValue+"']/parent::div/following-sibling::div/descendant::input[@placeholder='Comment...']"));
	}
	public WebElement button_copyRightToLeftOphthalmicHistory(String ophthalmicHistoryValue){
		ophthalmicHistoryValue=ophthalmicHistoryValue.replaceAll(" ","_").toLowerCase();
		return driver.findElement(By.xpath("//div[contains(@class,'col-md-12 col-sm-12 col-xs-12 complaint_name specility_field_row "+ophthalmicHistoryValue+"')]//i[contains(@class,'fa fa-arrow-right')]"));
	}
	//Systemic History
	public WebElement select_durationSystemicHistory(String systemicHistoryValue){
		return driver.findElement(By.xpath("//div[contains(@class,'"+systemicHistoryValue+"')]/descendant::select[contains(@class,'duration_field')]"));
	}
	public WebElement select_durationUnitSystemicHistory(String systemicHistoryValue){
		return driver.findElement(By.xpath("//div[contains(@class,'"+systemicHistoryValue+"')]/descendant::select[contains(@class,'duration_unit')]"));
	}
	public WebElement input_commentSystemicHistory(String systemicHistoryValue){
		return driver.findElement(By.xpath("//div[contains(@class,'"+systemicHistoryValue+"')]/descendant::input[contains(@id,'comment')]"));
	}
	//Drug Allergies
	public List<WebElement> list_buttonAllDrugAgentAllergies(String agentType){
		agentType=agentType.toLowerCase().replaceAll(" ","_");
		return driver.findElements(By.xpath("//button[@name='"+agentType+"']"));
	}
	public  WebElement select_durationAllergies(String allergyAgentValue){
		return driver.findElement(By.xpath("(//*[contains(text(),'"+allergyAgentValue+"')]/parent::div/following-sibling::div/descendant::select)[1]"));
	}
	public WebElement select_durationUnitAllergies(String allergyAgentValue){
		return driver.findElement(By.xpath("(//*[contains(text(),'"+allergyAgentValue+"')]/parent::div/following-sibling::div/descendant::select)[2]"));
	}
	public WebElement input_commentSubAllergies(String allergyAgentValue){
		return driver.findElement(By.xpath("//*[contains(text(),'"+allergyAgentValue+"')]/parent::div/following-sibling::div/descendant::input[@placeholder='Comment...']"));
	}
	@FindBy(xpath = "//button[@id='antimicrobial_agents_ampicillin']")
	public WebElement button_AllergyName;



}
