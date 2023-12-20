package pages.settings.organisationSettings.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_OrganisationSetup extends TestBase {
    private WebDriver driver;

    public Page_OrganisationSetup(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(text(),' Add User')]")
    public WebElement button_addUser;

    @FindBy(xpath = "//button[@id='all-users']")
    public WebElement button_allUsers;

    @FindBy(xpath = "//select[@id='user_salutation']")
    public WebElement select_salutaionForTheUser;

    @FindBy(xpath = "//input[@id='user_fullname']")
    public WebElement input_userFullName;

    @FindBy(xpath = "//input[@id='email_local']")
    public WebElement input_emailAddressOfTheUser;

    @FindBy(xpath = "//select[@id='email_domain']")
    public WebElement select_emailDomain;

    @FindBy(xpath = "//select[@id='user_gender']")
    public WebElement select_genderOfTheUser;

    @FindBy(xpath = "//input[@id='user_dob']")
    public WebElement input_datePicker;

    @FindBy(xpath = "//input[@id='user_mobilenumber']")
    public WebElement input_mobileNumberOfTheUser;

    @FindBy(xpath = "//input[@id='user_telephone']")
    public WebElement input_telephoneNumberOfTheUser;

    @FindBy(xpath = "//span[contains(@class,'single')]")
    public WebElement input_countryNamefield;

    @FindBy(xpath = "//input[@id='user_address']")
    public WebElement input_address;

    @FindBy(xpath = "//input[@class='select2-search__field']")
    public WebElement input_countryNameOfTheUser;

    @FindBy(xpath = "//ul[@id='select2-user_country_id-results']//li")
    public List<WebElement> list_country;

    @FindBy(xpath = "//input[@id='search_pincode_other']")
    public WebElement input_pincodeOfTheUser;

    @FindBy(xpath = "//input[@id='user_employee_id']")
    public WebElement input_employeeId;

    @FindBy(xpath = "//input[@id='user_designation']")
    public WebElement input_userDesignation;

    @FindBy(xpath = "//input[@id='user_registration_number']")
    public WebElement input_registrationNumberOfTheUser;

    @FindBy(xpath = "//label[contains(@for,'role_ids')]")
    public List<WebElement> list_userRole;

    @FindBy(xpath = "//button[@id='next']")
    public WebElement button_next;

    @FindBy(xpath = "//li[@class='ui-menu-item']/a")
    public List<WebElement> inputOptions_pincodeOnAddUserForm;

    @FindBy(xpath = "//select[@class='ui-datepicker-year']")
    public WebElement select_year;

    @FindBy(xpath = "//select[@class='ui-datepicker-month']")
    public WebElement select_month;

    @FindBy(xpath = "//table[@class='ui-datepicker-calendar']//td//a")
    public List<WebElement> list_birthDateOfTheUser;

    @FindBy(xpath = "//span[text()='List Restricted to single specialty']")
    public WebElement text_singleSpecialityRestrictionMessage;

    @FindBy(xpath = "//span[text()='Specialty selection is not possible for this User Role.']")
    public WebElement text_specialityRestrictionMessageDisabled;

    @FindBy(xpath = "//strong[text()='Sub Specialties List']")
    public WebElement text_subSpecialityList;

    @FindBy(xpath = "//input[@class='specialty_list']/..//label")
    public List<WebElement> list_specialitiesName;

    @FindBy(xpath = "//input[@class='sub_specialty_list']/..//label")
    public List<WebElement> list_subSpecialitiesName;

    @FindBy(xpath = "//input[@class='specialty_list']")
    public List<WebElement> list_checkboxesForSpecialities;

    @FindBy(xpath = "//input[@class='sub_specialty_list']")
    public List<WebElement> list_checkboxesForSubSpecialities;

    @FindBy(xpath = "//input[@class='department_list']/..//label")
    public List<WebElement> list_departmentsNames;

    @FindBy(xpath = "//input[@class='department_list']")
    public List<WebElement> list_checkboxForDepartments;

    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveUser;

    @FindBy(xpath = "//ul[@class='select2-results__options']//li")
    public List<WebElement> list_facilityNameFromDropdown;

    @FindBy(xpath = " //input[@value='Save']")
    public WebElement button_save;
    @FindBy(xpath = " //ul[@class='select2-selection__rendered']")
    public WebElement input_facilityNameField;

    @FindBy(xpath = "//button[@id='all-users']")
    public WebElement button_allUserInOrgLevel;
    @FindBy(xpath = "//select[@id='search_by_facility_name']")
    public WebElement select_facilityUnderAllUser;

    @FindBy(xpath = " //select[@id='search_by_role']")
    public WebElement select_roleUnderAllUser;

    @FindBy(xpath = "//select[@id='search_by_is_active']")
    public WebElement select_activeStatusOfUser;

    @FindBy(xpath = "//table[@id='manage_users']/tbody/tr/td[2]")
    public List<WebElement> list_userName;
    @FindBy(xpath = "//table[@id='manage_users']/tbody/tr/td[6]")
    public List<WebElement> list_emailUserName;
    @FindBy(xpath = "//table[@id='manage_users']/tbody/tr/td[8]/span[1]/a")
    public List<WebElement> list_editAndActiveButtons;

    @FindBy(xpath = "//table[@id='manage_users']/tbody/tr/td[3]")
    public List<WebElement> list_facilityName;

    @FindBy(xpath = " //table[@id='manage_users']/tbody/tr/td[8]/span[1]")
    public List<WebElement> list_activateButtonName;

    @FindBy(xpath = " //table[@id='manage_users']/tbody/tr/td[8]/span[2]/a")
    public List<WebElement> list_deleteButtonName;

    @FindBy(xpath = "//input[@value='Send Activation Link']")
    public WebElement button_sendActivationUserLink;

    @FindBy(xpath = "//div[@aria-hidden='false']/div[@class='modal-dialog']//button[text()='Confirm']")
    public WebElement button_confirm;

    @FindBy(xpath = "//input[@type='search']")
    public WebElement search_user;
    @FindBy(xpath = "//div[@id='manage_users_filter']/label/input")
    public WebElement input_searchUser;

    @FindBy(xpath = "//button[contains(text(),'Close')]")
    public WebElement button_close;

    @FindBy(xpath = "//button[contains(text(),' Back')]")
    public WebElement button_back;

    @FindBy(xpath = "//a[@id='edit-facility']")
    public WebElement button_addFacility;

    @FindBy(xpath = "//input[@id='facility_name']")
    public WebElement input_facilityName;

    @FindBy(xpath = "//input[@id='facility_display_name']")
    public WebElement input_facilityDisplayName;

    @FindBy(xpath = "//input[@id='facility_abbreviation']")
    public WebElement input_facilityCode;

    @FindBy(xpath = "//label[@id='facility_display_name-error']")
    public WebElement text_facilityDisplayNameError;

    @FindBy(xpath = "//label[@id='facility_abbreviation-error']")
    public WebElement text_facilityCodeError;

    @FindBy(xpath = "//span[@title='Select Country']")
    public WebElement dropdown_facilityCountry;

    @FindBy(xpath = "//ul[contains(@id,'facility_country_id-results')]/li")
    public List<WebElement> list_countryNames;

    @FindBy(xpath = "//span[@aria-labelledby='select2-facility_time_zone-container']")
    public WebElement dropdown_timezone;
    @FindBy(xpath = "//ul[@id='select2-facility_time_zone-results']")
    public List<WebElement> list_timezone;

    @FindBy(xpath = "//span[contains(@aria-labelledby,'facility_region_master_id-container')]")
    public WebElement dropdown_facilityRegion;

    @FindBy(xpath = "//span[contains(@id,'select2-facility_region_master_id-container')]")
    public WebElement dropdown_facilityRegionSelected;
    @FindBy(xpath = "//ul[contains(@id,'facility_region_master_id-results')]/li")
    public List<WebElement> list_facilityRegion;
    @FindBy(xpath = "//input[@id='facility_address']")
    public WebElement input_facilityAddress;

    @FindBy(xpath = "//input[@id='search_pincode_other']")
    public WebElement input_facilityPincode;

    @FindBy(xpath = "//input[@id='search_state_other']")
    public WebElement input_facilityState;

    @FindBy(xpath = "//input[@id='search_city_other']")
    public WebElement input_facilityCity;

    @FindBy(xpath = "//input[@id='email_local']")
    public WebElement input_facilityEmail;

    @FindBy(xpath = "//select[@id='email_domain']")
    public WebElement dropdown_emailDomain;

    @FindBy(xpath = "//select[@id='email_domain']/option")
    public List<WebElement> list_emailDomain;

    @FindBy(xpath = "//input[@name='facility[specialty_ids][]']/..//label")
    public List<WebElement> list_specialitiesForFacilityName;

    @FindBy(xpath = "//input[@name='facility[department_ids][]']/..//label")
    public List<WebElement> list_departmentName;

    @FindBy(xpath = "//input[@value='Save']")
    public WebElement button_saveFacility;

    @FindBy(xpath = "//table[@id='manage_facilities']//td[2]")
    public List<WebElement> tableList_facilityName;

    @FindBy(xpath = "//div[@id='manage_facilities_filter']//input")
    public WebElement input_searchFacility;

    @FindBy(xpath = "//table[@id='manage_facilities']//td[6]//i[@class='fa fa-solid fa-pen']")
    public List<WebElement> list_facilityEditButton;

    @FindBy(xpath = "//div[@class='modal-header']//button[@class='close']")
    public WebElement button_closeEditModal;

    @FindBy(xpath = "//table[@id='manage_facilities']//td[6]//i[@class='fa fa-trash-alt']")
    public List<WebElement> list_facilityDisableButton;

    @FindBy(xpath = "//button[text()='Confirm']")
    public WebElement button_confirmDisableFacility;

    @FindBy(xpath = "//table[@id='manage_facilities']//td[5]")
    public WebElement list_userByRoles;
    @FindBy(xpath = "//a[@data-target='#organisation-modal' and @id='edit-organisation']")
    public WebElement button_editOrganisation;

    @FindBy(xpath = "//h4[text()='Manage Organisation']")
    public WebElement text_editOrganisationTitle;

    @FindBy(xpath = "//input[@id='organisation_name']")
    public WebElement input_organisationName;

    @FindBy(xpath = "//input[@id='organisation_tagline']")
    public WebElement input_organisationTagline;

    @FindBy(xpath = "//span[@id='select2-organisation_country_id-container']")
    public WebElement input_country;

    @FindBy(xpath = "//span[@aria-labelledby='select2-organisation_country_id-container']//span[@class='select2-selection__arrow']")
    public WebElement dropdown_countryArrow;

    @FindBy(xpath = "//select[@id='organisation_country_id']")
    public WebElement list_countrySelectionList;

    @FindBy(xpath = "//ul[@class='select2-selection__rendered']//li[1]")
    public WebElement text_selectedSupportedCurrenciesInBox;

    @FindBy(xpath = "//span[contains(@class,'choice__remove')]")
    public WebElement button_removeSelectedCurrency;

    @FindBy(xpath = "//ul[@id='select2-organisation_currency_ids-results']//li")
    public List<WebElement> list_supportedCurrenciesList;

    @FindBy(xpath = "//input[@id='organisation_address1']")
    public WebElement input_address1;

    @FindBy(xpath = "//input[@id='organisation_address2']")
    public WebElement input_address2;

    @FindBy(xpath = "//input[@id='search_pincode_other']")
    public WebElement input_pincode;

    @FindBy(xpath = "//input[@id='search_state_other']")
    public WebElement input_state;

    @FindBy(xpath = "//input[@id='search_city_other']")
    public WebElement input_city;

    @FindBy(xpath = "//input[@id='organisation_telephone']")
    public WebElement input_telephone;

    @FindBy(xpath = "//input[@id='organisation_fax']")
    public WebElement input_fax;

    @FindBy(xpath = "//input[@id='organisation_website']")
    public WebElement input_website;

    @FindBy(xpath = "//input[@id='organisation_email']")
    public WebElement input_email;

    @FindBy(xpath = "//input[@id='organisation_pan_no']")
    public WebElement input_panNumber;

    @FindBy(xpath = "//input[@id='organisation_st_no']")
    public WebElement input_serviceTaxNumber;

    @FindBy(xpath = "//input[@id='organisation_sms_contact_number']")
    public WebElement input_smsContactNumber;

    @FindBy(xpath = "//p[text()=' Note: The above number will be sent on the sms to patients.']")
    public WebElement text_smsNotes;

    @FindBy(xpath = "//select[@id='number_format']")
    public WebElement input_preferredNumberFormat;

    @FindBy(xpath = "//select[@id='number_format']/option[@selected='selected']")
    public WebElement text_preferredNumberFormat;

    @FindBy(xpath = "//input[@id='save-organisation']")
    public WebElement button_saveChanges;

    @FindBy(xpath = "//div[@class='modal-body']")
    public WebElement module_savedModule;
    @FindBy(xpath = "//a[@data-target='#organisation-modal' and @id='edit-organisation-settings']")
    public WebElement button_orgSettings;
    @FindBy(xpath = "//h4[contains(text(),'Edit Organisation Settings')]")
    public WebElement header_orgSettings;
    @FindBy(xpath = "//button[contains(@id,'settings_button')]")
    public List<WebElement> list_typeOfSetting;
    @FindBy(xpath = "//label[contains(@for,'organisations_setting_bill_clearance_needed')]")
    public WebElement label_billClearanceSetting;
    @FindBy(xpath = "//input[contains(@id,'bill_clearance_needed_yes')]")
    public WebElement radBtn_billClearanceYes;
    @FindBy(xpath = "//input[contains(@id,'bill_clearance_needed_no')]")
    public WebElement radBtn_billClearanceNo;
    @FindBy(xpath = "//input[@name='organisations_setting[bill_clearance_needed]']")
    public List<WebElement> radioBtn_billClearanceStatus;
    @FindBy(xpath = "//input[contains(@value,'Save Changes')]")
    public WebElement button_saveOrgSetting;
    @FindBy(xpath = "//input[@id='user_password']")
    public WebElement input_newPasswordResetPassword;
    @FindBy(xpath = "//input[@id='user_password_confirmation']")
    public WebElement input_confirmPasswordResetPassword;
    @FindBy(xpath = "//input[@id='signinbutton']")
    public WebElement button_resetPassword;
    @FindBy(xpath = "//select[@id='user_country_id']")
    public WebElement select_countryId;
    @FindBy(xpath = "//b[text()='Doctor']/parent::label")
    public WebElement button_doctorRole;
    @FindBy(xpath = "//b[text()='Outpatient Department']/parent::label")
    public WebElement checkbox_outPatientDepartmant;
    @FindBy(xpath = "//a[@id='manage_users_last']")
    public WebElement button_lastUserPagination;
    @FindBy(id = "show_auth_policy_modal_checkbox")
    public WebElement checkbox_authPolicy;

    @FindBy(xpath = "(//a[@class='btn  btn-xs'])[2]")
    public WebElement button_Policy;
    @FindBy(xpath = " (//input[@class='select-all-radio'])[5]")
    public WebElement input_yesPolicy;
    @FindBy(xpath = " (//input[@class='select-all-radio'])[6]")
    public WebElement input_noPolicy;
    @FindBy(xpath = "//a[text()='COUNSELLOR']")
    public WebElement select_counsellorTab;
    @FindBy(xpath = " (//input[@class='select-all-radio'])[1]")
    public WebElement input_yesPolicyInCounsellorTab;
    @FindBy(xpath = " (//input[@class='select-all-radio'])[2]")
    public WebElement input_noPolicyInCounsellorTab;
    @FindBy(xpath = "//a[text()='EHR SETTINGS']")
    public WebElement select_ehrSettingsTab;
    @FindBy(xpath = " (//input[@class='select-all-radio'])[4]")
    public WebElement input_noPolicyEhrSettingsTab;
    @FindBy(xpath = " (//input[@class='select-all-radio'])[3]")
    public WebElement input_yesPolicyInEhrSettingsTab;
    @FindBy(xpath = "//a[text()='IPD AND OT']")
    public WebElement select_ipdandOtTab;
    @FindBy(xpath = " (//input[@class='select-all-radio'])[8]")
    public WebElement input_noPolicyipdandOtTab;
    @FindBy(xpath = " (//input[@class='select-all-radio'])[7]")
    public WebElement input_yesPolicyInipdandOtTab;
    @FindBy(xpath = "//a[text()='MIS CLINICAL REPORTS']")
    public WebElement select_misClinicalReportsTab;
    @FindBy(xpath = " (//input[@class='select-all-radio'])[10]")
    public WebElement input_noPolicymisClinicalReportsTab;
    @FindBy(xpath = " (//input[@class='select-all-radio'])[9]")
    public WebElement input_yesPolicyInmisClinicalReportsTab;
    @FindBy(xpath = "//a[text()='MIS FINANCE REPORTS']")
    public WebElement select_misFinanceReportsTab;
    @FindBy(xpath = " (//input[@class='select-all-radio'])[12]")
    public WebElement input_noPolicymisFinanceReportsTab;
    @FindBy(xpath = " (//input[@class='select-all-radio'])[11]")
    public WebElement input_yesPolicyInmisFinanceReportsTab;
    @FindBy(xpath = "//a[text()='OPD']")
    public WebElement select_opdTab;
    @FindBy(xpath = " (//input[@class='select-all-radio'])[14]")
    public WebElement input_noPolicyopdTab;
    @FindBy(xpath = " (//input[@class='select-all-radio'])[13]")
    public WebElement input_yesPolicyInopdTab;
    @FindBy(xpath = "//a[text()='PRINT AND EMAIL']")
    public WebElement select_printAndEmailTab;
    @FindBy(xpath = " (//input[@class='select-all-radio'])[16]")
    public WebElement input_noPolicyprintAndEmailTab;
    @FindBy(xpath = " (//input[@class='select-all-radio'])[15]")
    public WebElement input_yesPolicyInprintAndEmailTab;
    @FindBy(xpath = "//a[text()=' Add Policy Template']")
    public WebElement button_addPolicyTemplate;

    @FindBy(xpath = "//input[@id='time_slots_enabled_yes']")
    public WebElement checkbox_yesEnableTimeSlot;
    @FindBy(xpath = "//input[@id='time_slots_enabled_no']")
    public WebElement checkbox_noEnableTimeSlot;
    @FindBy(xpath = "//strong[@id='users_count_info']")
    public WebElement text_userCountInfo;
    @FindBy(xpath = "//table[@id='manage_users']//tbody//td[2]")
    public List<WebElement> list_allUsersNameList;
    @FindBy(xpath = "//table[@id='manage_users']//tbody//td[8]//a[text()='Activate']")
    public List<WebElement> list_allUsersActivateActionList;
    @FindBy(xpath = "//table[@id='manage_users']//tbody//td[8]//a[@title='Edit User']")
    public List<WebElement> list_allUsersEditActionList;
    @FindBy(xpath = "//select[@name='manage_users_length']")
    public WebElement select_showUserEntries;
    @FindBy(xpath = "//select[@name='manage_facilities_length']")
    public WebElement select_showFacilityEntries;
    @FindBy(xpath = "//div[@class='dataTables_paginate paging_full_numbers']//span/a")
    public List<WebElement> list_paginationNumberList;
    @FindBy(xpath = "//button[@id='org-subscription-details']")
    public WebElement button_orgSubscriptionDetailsButton;
    @FindBy(xpath = "//div[@class='org-subscription-details-section']//div[1]/strong[1]")
    public WebElement text_organisationName;
    @FindBy(xpath = "//h4[text()='Facility and User']")
    public WebElement header_facilityAndUser;
    @FindBy(xpath = "//p[contains(text(),'Active Facility ')]")
    public WebElement text_activeFacility;
    @FindBy(xpath = "//p[contains(text(),'In-active Facility ')]")
    public WebElement text_inActiveFacility;
    @FindBy(xpath = "//p[contains(text(),'Active User')]")
    public WebElement text_activeUser;
    @FindBy(xpath = "//p[contains(text(),'In-active User')]")
    public WebElement text_inActiveUser;
    @FindBy(xpath = "//h4[contains(text(),'Subscription Detail')]")
    public WebElement header_subscriptionDetailsSection;
    @FindBy(xpath = "//p[contains(text(),'Subscription Quota')]")
    public WebElement text_subscriptionQuota;
    @FindBy(xpath = "//p[contains(text(),'Subscription User')]")
    public WebElement text_subscriptionUser;
    @FindBy(xpath = "//p[contains(text(),'Account Expries in ')]")
    public WebElement text_accountExpiresIn;
    @FindBy(xpath = "//p[contains(text(),'Account Expries on')]")
    public WebElement text_accountExpiresOn;
    @FindBy(xpath = "//p[contains(text(),'Subscription Pricing Type')]")
    public WebElement text_subscriptionPricingType;
    @FindBy(xpath = "//b[contains(text(),'Active ')]")
    public WebElement text_activeUserInSubscriptionDetails;
    @FindBy(xpath = "//b[contains(text(),'Available')]")
    public WebElement text_availableUserInSubscriptionDetails;
    @FindBy(xpath = "//a[contains(text(),'Click Here to Request More User')]")
    public WebElement text_clickHereForMoreUserInSubscriptionDetails;
    @FindBy(xpath = "//i[contains(text(),'Coming soon')]")
    public WebElement text_comingSoonForMoreUser;
    @FindBy(xpath = "//h4[contains(text(),'Billing History')]")
    public WebElement header_billingHistorySection;
    @FindBy(xpath = "//td[text()=' No Data']")
    public WebElement text_noDataText;
    @FindBy(xpath = "//div[@class='org-subscription-details-section']//table//th")
    public List<WebElement> list_billingHistoryTableHeaderList;
    @FindBy(xpath = "//a[text()='Next']")
    public WebElement button_nextInUsers;
    @FindBy(xpath = "//a[text()='First']")
    public WebElement button_firstInUsers;

    @FindBy(xpath = " //table[@id='manage_users']/tbody/tr/td[8]")
    public List<WebElement> list_actionButtonName;
    @FindBy(xpath = " //table[@id='manage_users']//tbody//td[8]//a[@data-method='delete']")
    public List<WebElement> list_actionDeleteButtonName;

    @FindBy(xpath = "//table[@id='manage_facilities']//td[6]//a[text()='Activate']")
    public List<WebElement> list_inactiveFacilityList;

    @FindBy(xpath = "//span[contains(text(),'Note: Your account is about to expiry please review renew your account before')]")
    public WebElement text_accountExpireWaringMessage;

    @FindBy(xpath = "(//a[@title='Edit User'])[1]")
    public WebElement button_editInUsers;

    @FindBy(xpath = "(//a[@title='Reset Password'])[1]")
    public WebElement button_resetPasswordIcon;

    @FindBy(xpath = "(//a[@title='Disable User'])[1]")
    public WebElement button_disableUsersIcon;

    @FindBy(xpath = "(//a[@class='btn btn-success btn-xs'])[1]")
    public WebElement button_linkFacilityIcon;

    @FindBy(xpath = "(//a[@class='btn btn-danger btn-xs']//i[@class='fa fa-unlink'])[1]")
    public WebElement button_unlinkFacilityIcon;

    @FindBy(xpath = "(//a[text()='Audit Trail'])[1]")
    public WebElement button_auditTrailIcon;

    @FindBy(xpath = "//a[@id='edit-facility']")
    public WebElement button_facilityLocation;

    @FindBy(xpath = "//a[@class='btn btn-primary edit-facility mb10']")
    public WebElement button_addFacilityLocation;

    @FindBy(xpath = "//a[text()='Activate']")
    public WebElement button_activate;

    @FindBy(xpath = "(//a[@title='Edit Facility'])[1]")
    public WebElement button_editFacilityIcon;

    @FindBy(xpath = "//a[contains(text(),'Link users')]")
    public WebElement button_linkUserFacilityIcon;

    @FindBy(xpath = "//a[contains(text(),'Un-link users')]")
    public WebElement button_unlinkUserFacilityIcon;

    @FindBy(xpath = "(//a[@title='Disable Facility'])[1]")
    public WebElement button_disableUserFacilityIcon;

    @FindBy(xpath = "//a[text()='Activate']")
    public WebElement button_activateUserFacilityIcon;

    @FindBy(xpath = "//button[@class='btn btn-primary dropdown-toggle']")
    public WebElement button_downloadFacility;

    @FindBy(xpath = "//a[contains(text(),' Facility Wise User Report')]")
    public WebElement button_downloadFacilityReport;

//    @FindBy(xpath = "//a[contains(text(),' User Wise Report')]")
//    public WebElement button_downloadUserReport;

    @FindBy(xpath = "//div[@id='facility_locations']/div[1]/div[2]/div/div/a[2]/text()")
    public WebElement button_downloadUserReport;

    @FindBy(id = "manage_users_next")
    public WebElement button_nextManageUser;

    @FindBy(xpath = "//a[@id='edit-organisation-settings']")
    public WebElement button_orgSettingsButton;
    @FindBy(xpath = "//h4[text()='Edit Organisation Settings']")
    public WebElement header_editOrganisationSettingHeader;
    @FindBy(xpath = "//label[text()='Enable Time Slots']")
    public WebElement label_enableTimeSlotsLabel;
    @FindBy(xpath = "//input[@id='time_slots_enabled_yes']")
    public WebElement input_timeSlotYesRadioButton;
    @FindBy(xpath = "//input[@id='time_slots_enabled_no']")
    public WebElement input_timeSlotNoRadioButton;

}
