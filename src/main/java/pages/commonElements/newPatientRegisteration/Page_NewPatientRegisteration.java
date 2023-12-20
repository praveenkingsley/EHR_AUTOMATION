package pages.commonElements.newPatientRegisteration;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_NewPatientRegisteration extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_NewPatientRegisteration(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    // Create New Appointment Elements

    @FindBy(xpath = "//div[@id='add_new_patient_modal']")
    public WebElement modal_addPatient;

    @FindBy(xpath = "//select[@id='patient_patient_type_id']")
    public WebElement select_searchPatientType;

    @FindBy(xpath = "//input[@id='patient_patient_type_comment']")
    public WebElement input_patientTypeComment;

    @FindBy(xpath = "//select[@id='patient_search']")
    public WebElement select_searchPatientOrAddNewPatient;

    @FindBy(xpath = "//input[@id='search-patient']")
    public WebElement input_searchPatient;

    @FindBy(xpath = "//button[text()='Search']")
    public WebElement button_searchPatient;

    @FindBy(xpath = "//button[text()='Add New Patient']")
    public WebElement button_addNewPatient;

    // ***********Patient Registration & Appointment Form****************//

    @FindBy(xpath = "//div[@class='modal-content']")
    public WebElement modal_PatientRegForm;

    @FindBy(xpath = "//h4[text()='Patient Registration & Appointment Form']")
    public WebElement modalHeader_PatientRegForm;

    @FindBy(xpath = "//div[@id='patientWizard']//li[@role='presentation']")
    public List<WebElement> tabs_PatientRegForm;

    @FindBy(xpath = "//div[@class='modal-footer']//input[@value='Save']")
    public WebElement button_createAppointmentPatientRegForm;

    @FindBy(xpath = "//button[text()='Close']")
    public WebElement button_closePatientRegForm;

    // *********** Patient Details Tab ****************//

    @FindBy(xpath = "//select[@id='patient_salutation']")
    public WebElement select_salutationForPatient;

    @FindBy(xpath = "//span[@class='required-message']")
    public WebElement text_compulsoryFieldsAlertMessage;

    @FindBy(xpath = "//*[@id='patient_referral_type_referral_type_id-error']")
    public WebElement text_PatientReferralSourceErrorMsg;

    @FindBy(xpath = "//span[@class='required-message']/b")
    public WebElement subText_requiredFieldsAlertMessage;

    @FindBy(xpath = "//input[@placeholder='First Name']")
    public WebElement input_firstNameOnPatientRegForm;

    @FindBy(xpath = "//input[@placeholder='Middle Name']")
    public WebElement input_middleNameOnPatientRegForm;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    public WebElement input_lastNameOnPatientRegForm;

    @FindBy(xpath = "//input[@placeholder='Mobile Number']")
    public WebElement input_mobileNumberOnPatientRegForm;

    @FindBy(xpath = "//input[@placeholder='Secondary Number']")
    public WebElement input_secondaryMobileNumberOnPatientRegForm;

    @FindBy(xpath = "//input[@placeholder='Email']")
    public WebElement input_emailOnPatientRegForm;

    @FindBy(xpath = "//input[@id='patient_same_as_mobilenumber']")
    public WebElement checkbox_sameAsContactNumber;

    @FindBy(xpath = "//input[@id='patient_whatsappnumber']")
    public WebElement input_whatsappNumber;

    @FindBy(xpath = "//span[@title='Primary']")
    public WebElement select_primaryLanguage;

    @FindBy(xpath = "//ul[@id='select2-patient_primary_language-results']/li")
    public List<WebElement> options_primaryLanguage;

    @FindBy(xpath = "//input[@class='patient-gender']")
    public List<WebElement> list_genderRadioButtons;
    @FindBy(xpath = "//input[@id='gender_male']")
    public WebElement radio_gender_Male;

    @FindBy(xpath = "//input[@id='gender_female']")
    public WebElement radio_gender_Female;

    @FindBy(xpath = "//input[@id='gender_transgender']")
    public WebElement radio_gender_Transgender;

    @FindBy(xpath = "//input[@id='patient_age_month']")
    public WebElement input_patientAgeMonth;

    @FindBy(xpath = "//span[@title='Secondary']")
    public WebElement select_secondaryLanguage;

    @FindBy(xpath = "//ul[@id='select2-patient_secondary_language-results']/li")
    public List<WebElement> options_secondaryLanguage;

    @FindBy(xpath = "//input[@placeholder='Pincode']")
    public WebElement input_pincodeOnPatientRegForm;

    @FindBy(xpath = "//input[@placeholder='State']")
    public WebElement input_stateOnPatientRegForm;

    @FindBy(xpath = "//input[@placeholder='City']")
    public WebElement input_cityOnPatientRegForm;

    @FindBy(xpath = "//li[@class='ui-menu-item']/a")
    public List<WebElement> inputOptions_pincodeOnPatientRegForm;

    @FindBy(xpath = "//input[@placeholder='Address 1']")
    public WebElement input_address_1_OnPatientRegForm;

    @FindBy(xpath = "//input[@placeholder='Address 2']")
    public WebElement input_address_2_OnPatientRegForm;

    @FindBy(xpath = "//select[contains(@id,'patient_area')]")
    public WebElement select_areaPatientAddress;

    @FindBy(xpath = "//input[@placeholder='Medical Record No.']")
    public WebElement input_medicalRecordNumOnPatientRegForm;

    @FindBy(xpath = "//input[@id='patient_health_id_number']")
    public WebElement input_healthIdNumOnPatientRegForm;

    @FindBy(xpath = "//input[@placeholder='Aadhar Card No.']")
    public WebElement input_aadharCardNumOnPatientRegForm;

    @FindBy(xpath = "//input[@placeholder='Pan No.']")
    public WebElement input_panNumOnPatientRegForm;

    @FindBy(xpath = "//input[@placeholder='DL No.']")
    public WebElement input_drivingLicenseNumOnPatientRegForm;

    @FindBy(xpath = "//input[@placeholder='GST No.']")
    public WebElement input_gstNumberOnPatientRegForm;

    @FindBy(xpath = "//b[text()='Patient Referral Source']")
    public WebElement text_PatientReferralSource;

    @FindBy(xpath = "//select[contains(@id,'type_referral')]")
    public WebElement select_patientReferralSrcOnPatientRegForm;

    @FindBy(xpath = "//span[@aria-labelledby='select2-patient_referral_type_referral_type_id-container']/span[2]")
    public WebElement span_patientReferralSrcOnPatientRegForm;;
    @FindBy(xpath = "//ul[@id='select2-patient_referral_type_referral_type_id-results']/li")
    public List<WebElement> options_patientReferralSrcOnPatientRegForm;

    @FindBy(xpath = "//select[@id='patient_referral_type_sub_referral_type_id']")
    public WebElement select_patientSubReferralSrcOnPatientRegForm;

    @FindBy(xpath = "//input[@id='patient_dob']")
    public WebElement input_patientDob;
    @FindBy(xpath = "//select[@class='ui-datepicker-year']")
    public WebElement select_dobYear;

    @FindBy(xpath = "//select[@class='ui-datepicker-month']")
    public WebElement select_dobMonth;
    @FindBy(xpath = "//*[@id='patient_dob']")
    public WebElement select_dobDateDay;
    @FindBy(xpath = "//input[@id='patient_age']")
    public WebElement text_patientAge;

    @FindBy(xpath = "//select[@id='patient_relative_relation']")
    public WebElement select_patientRelation;

    @FindBy(xpath = "//*[@id='patient_relative_name']")
    public WebElement input_patientRelation;

    @FindBy(xpath = "//*[@id='patient_occupation']")
    public WebElement input_patientOccupation;

    @FindBy(xpath = "//*[@id='patient_employee_id']")
    public WebElement input_patientEmployeeId;

    public WebElement select_dobDate(String value) {
        return driver.findElement(By.xpath("//*[@class='ui-state-default'][text()='" + value + "']"));
    }

    public WebElement getMonth(String value) {
        return driver.findElement(By.xpath("//*[@class='ui-datepicker-month']/child::option[@value='" + value + "']"));
    }

    // *********** Other Details Tab ****************//

    @FindBy(xpath = "//input[@name='patient[blood_group]']")
    public List<WebElement> radioButtons_bloodGroupsOnPatientRegForm;

    @FindBy(xpath = "//input[@name='patient[blood_group]']/following-sibling::label")
    public List<WebElement> radioButtonsSelector_bloodGroupsOnPatientRegForm;

    @FindBy(xpath = "//input[contains(@id, 'maritalstatus_')]")
    public List<WebElement> radioButtons_maritalStatusOnPatientRegForm;

    @FindBy(xpath = "//input[contains(@id, 'maritalstatus_')]/following-sibling::label")
    public List<WebElement> radioButtonsSelector_maritalStatusOnPatientRegForm;

    @FindBy(xpath = "//input[contains(@id, 'one_eyed_')]")
    public List<WebElement> radioButtons_oneEyedOnPatientRegForm;

    @FindBy(xpath = "//input[contains(@id, 'one_eyed_')]/following-sibling::label")
    public List<WebElement> radioButtonsSelector_oneEyedOnPatientRegForm;

    @FindBy(xpath = "//input[@id='patient_emergencycontactname']")
    public WebElement input_emergencyPatientNameOnPatientRegForm;

    @FindBy(xpath = "//input[@id='patient_emergencymobilenumber']")
    public WebElement input_emergencyPatientNumberOnPatientRegForm;

    // *********** History Tab ****************//

    @FindBy(xpath = "//div[contains(@class, 'complaint_name')]")
    public List<WebElement> row_disordersDetailsAfterButtonClickOnPatientRegForm;

    @FindBy(xpath = "//div[contains(@class, 'complaint_name')]/div")
    public List<WebElement> section_disordersDetails_div_InRowAfterClickOnPatientRegForm;

    @FindBy(xpath = "//button[contains(@id, 'personal_histories_')]")
    public List<WebElement> buttons_systemicHistoryDiseasesOnPatientRegForm;

    @FindBy(xpath = "//button[contains(@id, 'speciality_histories_')]")
    public List<WebElement> buttons_ophthalmicHistoryDiseasesOnPatientRegForm;

    @FindBy(xpath = "//input[@id='patient_history_comment']")
    public WebElement input_systemicHistoryCommentOnPatientRegForm;

    @FindBy(xpath = "//input[@id='patient_opthal_history_comment']")
    public WebElement input_ophthalmicHistoryCommentOnPatientRegForm;

    @FindBy(xpath = "//input[@id='patient_other_history_attributes_medical_history']")
    public WebElement input_medicalHistoryCommentOnPatientRegForm;

    @FindBy(xpath = "//input[@id='patient_other_history_attributes_family_history']")
    public WebElement input_familyHistoryCommentOnPatientRegForm;

    @FindBy(xpath = "//div[@target-input-id='patient_nutritional_assessment']/button[@name='nutritional_assessment']")
    public List<WebElement> buttons_nutritionalAssessmentsOnPatientRegForm;

    @FindBy(xpath = "//textarea[@id='patient_nutritional_assessment_comment']")
    public WebElement input_nutritionalAssessmentCommentOnPatientRegForm;

    @FindBy(xpath = "//div[@target-input-id='patient_immunization_assessment']/button[@name='immunization_assessment']")
    public List<WebElement> buttons_immunizationAssessmentsOnPatientRegForm;

    @FindBy(xpath = "//textarea[@id='patient_immunization_assessment_comment']")
    public WebElement input_immunizationAssessmentCommentOnPatientRegForm;

    // *********** Allergies Tab ****************//

    @FindBy(xpath = "//button[contains(@id, 'drug_allergies_')]")
    public List<WebElement> buttons_drugAllergiesOnPatientRegForm;

    @FindBy(xpath = "//button[contains(@id,'antimicrobial_agents_')]")
    public List<WebElement> buttons_drugAllergies___Antimicrobial;

    @FindBy(xpath = "//button[contains(@id,'antifungal_agents_')]")
    public List<WebElement> buttons_drugAllergies___Antifungal;

    @FindBy(xpath = "//button[contains(@id,'antiviral_agents_')]")
    public List<WebElement> buttons_drugAllergies___Antiviral;

    @FindBy(xpath = "//button[contains(@id,'nsaids_')]")
    public List<WebElement> buttons_drugAllergies___Nsaids;

    @FindBy(xpath = "//button[contains(@id,'eye_drops_')]")
    public List<WebElement> buttons_drugAllergies___EyeDrops;

    @FindBy(xpath = "//input[@id='patient_drug_allergies_comment']")
    public WebElement input_drugAllergiesCommentOnPatientRegForm;

    @FindBy(xpath = "//button[contains(@id, 'contact_allergies_')]")
    public List<WebElement> buttons_contactAllergiesOnPatientRegForm;

    @FindBy(xpath = "//input[@id='patient_contact_allergies_comment']")
    public WebElement input_contactAllergiesCommentOnPatientRegForm;

    @FindBy(xpath = "//button[contains(@id, 'food_allergies_')]")
    public List<WebElement> buttons_foodAllergiesOnPatientRegForm;

    @FindBy(xpath = "//input[@id='patient_food_allergies_comment']")
    public WebElement input_foodAllergiesCommentOnPatientRegForm;

    // *********** Appointment Details Section on Right ****************//

    @FindBy(xpath = "//input[contains(@id,'appointmenttype_')]")
    public List<WebElement> radioButtons_appointmentTypeOnPatientRegForm;

    // Date
    @FindBy(xpath = "//input[@id='appointment_appointmentdate']")
    public WebElement inputButton_appointmentDateForAppointDetails;

    @FindBy(xpath = "//table[@class='ui-datepicker-calendar']//a")
    public List<WebElement> td_datesOnCalendarOnPatientRegForm;

    @FindBy(xpath = "//span[@class='ui-datepicker-month']")
    public WebElement text_monthOnCalendarForAppointDetails;

    @FindBy(xpath = "//div[@id='ui-datepicker-div']//a[@title='Next']")
    public WebElement button_nextMonthForAppointmentOnPatientRegForm;

    @FindBy(xpath = "//span[@class='ui-datepicker-year']")
    public WebElement text_yearOnCalendarForAppointDetails;
    @FindBy(xpath = "//button[contains(@class,'ui-datepicker-trigger btn')]")
    public WebElement button_calendar;

    // Time
    @FindBy(xpath = "//input[@id='appointment_time']")
    public WebElement inputButton_appointmentTimeForAppointDetails;

    @FindBy(xpath = "//input[@class='bootstrap-timepicker-hour']")
    public WebElement input_appointmentHourForAppointDetails;

    @FindBy(xpath = "//input[@class='bootstrap-timepicker-minute']")
    public WebElement input_appointmentMinuteForAppointDetails;

    @FindBy(xpath = "//input[@class='bootstrap-timepicker-meridian']")
    public WebElement input_appointmentMeridianForAppointDetails;

    @FindBy(xpath = "//input[contains(@id,'appointmenttype_')]/following-sibling::label")
    public List<WebElement> radioButtonsSelector_appointmentTypeOnPatientRegForm;

    @FindBy(xpath = "//div[@id='appointment-details']//b[text()='Location']/parent::div/span")
    public WebElement selectButton_locationForAppointmentOnPatientRegForm;

    @FindBy(xpath = "//div[@id='appointment-details']//b[text()='Consultant']/parent::div/span")
    public WebElement selectButton_consultantForAppointmentOnPatientRegForm;

    @FindBy(xpath = "//div[@id='appointment-details']//b[text()='Visit Types']/parent::div/following-sibling::div/span")
    public WebElement selectButton_visitTypesForAppointmentOnPatientRegForm;

    @FindBy(xpath = "//div[@id='appointment-details']//b[text()='Visit Category']/parent::div/span")
    public WebElement selectButton_visitCategoryForAppointmentOnPatientRegForm;

    @FindBy(xpath = "//ul[@class='select2-results__options']/li")
    public List<WebElement> selectButtonOptions_searchResultsForSelectorsOnPatientRegForm;

    @FindBy(xpath = "//*[contains(@id,'specialty_id-container')]")
    public WebElement selectButton_specialitiesAvailableOnPatientRegForm;

    @FindBy(xpath = "//div[@id='user-calendar']")
    public WebElement table_timeSlotPatientRegForm;

    @FindBy(xpath = "//div[contains(text(),'Select Patient')]/following-sibling::div/button")
    public List<WebElement> list_textSearchResults;
    @FindBy(xpath = "//button[@id='btn-add-sub-referral-type']")
    public WebElement button_addSubReferral;
    @FindBy(xpath = "//select[contains(@id,'appointment_specialty_id')]")
    public WebElement select_specialityAvailable;

}
