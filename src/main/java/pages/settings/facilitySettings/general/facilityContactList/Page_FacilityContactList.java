package pages.settings.facilitySettings.general.facilityContactList;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_FacilityContactList extends TestBase {
    private WebDriver driver;

    public Page_FacilityContactList(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@class='btn btn-primary navbar-btn']")
    public WebElement button_addContactInFacilityContactList;

    @FindBy(xpath = "//select[@id='facility_contact_contact_group_id']")
    public WebElement select_contactGroup;

    @FindBy(xpath = "//select[@id='facility_contact_salutation']")
    public WebElement select_selectSalutationOfContact;

    @FindBy(xpath = "//input[@id='facility_contact_first_name']")
    public WebElement input_firstNameOfContact;

    @FindBy(xpath = "//input[@id='facility_contact_middle_name']")
    public WebElement input_middleNameOfContact;

    @FindBy(xpath = "//input[@id='facility_contact_last_name']")
    public WebElement input_lastNameOfContact;

    @FindBy(xpath = "//input[@id='facility_contact_company_name']")
    public WebElement input_companyNameInNewContactForm;

    @FindBy(xpath = "//input[@id='facility_contact_abbreviation']")
    public WebElement input_abbreviationInNewContactForm;

    @FindBy(xpath = "//input[@id='facility_contact_display_name']")
    public WebElement input_displayNameInNewContactForm;

    @FindBy(xpath = "//input[@id='facility_contact_email']")
    public WebElement input_emailOfContactInNewContactForm;

    @FindBy(xpath = "//input[@id='facility_contact_mobile_number']")
    public WebElement input_contactNumberInNewContactForm;

    @FindBy(xpath = "//input[@id='facility_contact_work_number']")
    public WebElement input_workContactNumberInNewContactForm;

    @FindBy(xpath = "//input[@id='search_pincode']")
    public WebElement input_pincodeInNewContactForm;

    @FindBy(xpath = "//li[@class='ui-menu-item']/a")
    public List<WebElement> inputOptions_pincodeOnNewContactForm;

    @FindBy(xpath = "//input[@id='facility_contact_address_line_1']")
    public WebElement input_address1InNewContactForm;

    @FindBy(xpath = "//input[@id='facility_contact_address_line_2']")
    public WebElement input_address2InNewContactForm;

    @FindBy(xpath = "//input[@name='commit']")
    public WebElement button_createContact;

    @FindBy(xpath = "//div[@role='tabpanel']//select[@class='filter-facility-contact-group form-control']")
    public WebElement select_groupOfContact;

    @FindBy(xpath = "//tr[@class='each-facility-contact-table-row']/td[1]/b")
    public List<WebElement> list_displayNameOfContactOnTable;

    @FindBy(xpath = "//input[@class='search-facility-contact form-control']")
    public WebElement input_searchFacilityContact;

    @FindBy(xpath = "//div[@id='show-facility-contacts-details']//tbody/tr/td[5]/a")
    public List<WebElement> list_editButtonsOnContactListTable;

    @FindBy(xpath = "//input[@id='cancel_appointment']")
    public WebElement button_cancelAppointmentButtonInForm;
    @FindBy(xpath = "//a[contains(text(),' Schedule Admission')]")
    public WebElement button_scheduleAdmission;
    @FindBy(xpath = "//a[contains(text(),'Insurance Details')]")
    public WebElement tab_insuranceDetailsTabInScheduleAdmission;
    @FindBy(xpath = "//input[@id='is_insured_true']")
    public WebElement radioButton_cashlessHospitalisation;

    @FindBy(xpath = "//span[@id='select2-admission_tpa_contact_id-container']")
    public WebElement select_tpaContact;
    @FindBy(xpath = "//ul[@class='select2-results__options']//li")
    public List<WebElement> list_tpaContactFromDropdown;

    @FindBy(xpath = "//input[@id='facility_contact_for_expense_box']")
    public WebElement checkbox_showInExpense;

    @FindBy(xpath = "//label[@for='facility_contact_for_expense_box']")
    public WebElement text_showInExpense;
    @FindBy(xpath = "//button[contains(text(),'Close')]")
    public WebElement button_closeScheduleAdmissionModel;

}
