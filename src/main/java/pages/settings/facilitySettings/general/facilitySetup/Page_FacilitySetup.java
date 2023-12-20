package pages.settings.facilitySettings.general.facilitySetup;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class Page_FacilitySetup extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;
	public static List<String> list_facilitySetupOptions = new ArrayList<String>();
	public Page_FacilitySetup(WebDriver driver) {
				this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath="//div[@class='manage_finance_views']//div[contains(@class,'text-right')]")
	public List<WebElement> list_facilityOptions;

	//finance module
	@FindBy(xpath = "//input[@name='show_finances']")
	public List<WebElement> radioBtn_FinanceStatus;
	@FindBy(xpath = "//input[@id='show_finances_yes']")
	public WebElement radioBtn_financeYes;

	@FindBy(xpath = "//input[@id='show_finances_no']")
	public WebElement radioBtn_financeNo;

	@FindBy(xpath = "//button[contains(@class,'finance')]")
	public WebElement button_financeSaveModule;

	//Invoice Module
	@FindBy(xpath = "//input[@name='invoice_compulsory']")
	public List<WebElement> radioBtn_InvoiceStatus;

	@FindBy(xpath = "//input[@id='invoice_compulsory_yes']")
	public WebElement radioBtn_InvoiceYes;

	@FindBy(xpath = "//input[@id='invoice_compulsory_no']")
	public WebElement radioBtn_InvoiceNo;

	@FindBy(xpath = "//button[contains(@class,'invoice')]")
	public WebElement button_invoiceSaveModule;

	@FindBy(xpath = "//*[@id=\"appointment_summary\"]//i[contains(text(),'Invoice')]")
	public WebElement text_InvoiceCompulsion;

	//Enable Token Appointment
	@FindBy(xpath = "//input[@name='token_enabled']")
	public List<WebElement> radioBtn_TokenStatus;

	@FindBy(xpath = "//input[@id='token_enabled_yes']")
	public WebElement radioBtn_TokenYes;

	@FindBy(xpath = "//input[@id='token_enabled_no']")
	public WebElement radioBtn_TokenNo;

	@FindBy(xpath = "//button[contains(@class,'token')]")
	public WebElement button_tokenSaveModule;

	@FindBy(xpath = "//h4[text()='Select Token']")
	public WebElement header_selectToken;

	@FindBy(xpath = "//button[text()='Skip Without Token']")
	public WebElement button_skipWithoutToken;

	@FindBy(xpath = "//div[contains(@class,'token')]//button[contains(@class,'save-token-direct')]")
	public List<WebElement> list_tokenNumber;

	@FindBy(xpath = "//button[text()='Save Token']")
	public WebElement button_saveToken;

	//Sort Token
	@FindBy(xpath = "//input[@name='sort_list_by_token']")
	public List<WebElement> radioBtn_TokenSortStatus;

	@FindBy(xpath = "//input[@id='sort_list_by_token_yes']")
	public WebElement radioBtn_TokenSortYes;

	@FindBy(xpath = "//input[@id='sort_list_by_token_no']")
	public WebElement radioBtn_TokenSortNo;

	@FindBy(xpath = "//label[contains(@class,'show-token-label')]")
	public List<WebElement> list_token;

	//Invoice Module
	@FindBy(xpath = "//input[@name='consultancy_type_enabled']")
	public List<WebElement> radioBtn_ConsultancyStatus;

	@FindBy(xpath = "//input[@id='consultancy_type_enabled_yes']")
	public WebElement radioBtn_ConsultancyYes;

	@FindBy(xpath = "//input[@id='consultancy_type_enabled_no']")
	public WebElement radioBtn_ConsultancyNo;

	@FindBy(xpath = "//div[@id='payment-type-settings']//button[contains(@class,'counsellor')]")
	public WebElement button_consultancySaveModule;

	@FindBy(xpath = "//h4[text()='Add Consultancy Type']")
	public WebElement header_consultancyPopUp;

	@FindBy(xpath = "//button[text()='Close']")
	public WebElement button_closeConsultancyPopUp;

	//Language Support Module
	@FindBy(xpath = "//input[@name='show_language_support']")
	public List<WebElement> radioBtn_LanguageStatus;

	@FindBy(xpath = "//input[@id='show_language_support_yes']")
	public WebElement radioBtn_LanguageYes;

	@FindBy(xpath = "//input[@id='show_language_support_no']")
	public WebElement radioBtn_LanguageNo;

	@FindBy(xpath = "//button[contains(@class,'language')]")
	public WebElement button_LanguageSaveModule;

	@FindBy(xpath = "//div[contains(@class,'tabview_lng')]")
	public  WebElement text_language;

	//template
    @FindBy(xpath = "//input[@class='right_va_advised']")
	public WebElement checkbox_rightVision;

	@FindBy(xpath = "//input[@class='left_va_advised']")
	public WebElement checkbox_leftVision;

	@FindBy(xpath = "//input[@id='show_user_state_yes']")
	public WebElement radioBtn_onlineYes;

	@FindBy(xpath = "//input[@id='show_user_state_no']")
	public WebElement radioBtn_onlineNo;

	@FindBy(xpath = "//input[@name='show_user_state']")
	public List<WebElement> radioBtn_showUserState;

	@FindBy(xpath = "//button[contains(@class,'userstate')]")
	public WebElement button_onlineStateSave;

	@FindBy(xpath = "//a[@id='user_state']")
	public WebElement link_userState;

	@FindBy(xpath = "//h4[text()='Change State']")
	public WebElement text_changeState;

	@FindBy(xpath = "//input[@id='user-state-ot-all']")
	public WebElement input_otAll;

	@FindBy(xpath = "//button[@class='submit-user-state btn btn-success']")
	public WebElement button_saveChangeSet;

	@FindBy(xpath = "//b[text()='You are working in OT Mode.']")
	public WebElement text_otMode;

	@FindBy(xpath = "//input[@id='user-state-offline-all']")
	public WebElement input_offlineAll;

	@FindBy(xpath = "//b[text()='You are working in Offline Mode.']")
	public WebElement text_offlineMode;

	@FindBy(xpath = "//input[@id='user-state-opd-all']")
	public WebElement input_opdAll;


	}