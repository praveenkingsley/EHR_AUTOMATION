package tests.settings.organisationSettings.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.general.Page_IDPrefix;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrganisationSetupTest extends TestBase {
    EHR_Data oEHR_Data = new EHR_Data();
    String uniqueStringForFacility = CommonActions.getRandomUniqueString(6);
    String originalOrganisationName = null, originalTagline = null, originalCountry = null,
            originalSupportedCurrencies = null;

    String originalAddress1 = null, originalAddress2 = null, originalPincode = null, originalState = null;
    String originalCity = null, originalTelephone = null, originalFax = null, originalWebsite = null;
    String originalEmail = null, originalPanNumber = null, originalServiceTaxNumber = null;
    String originalSmsContactNumber = null, originalPreferredNumberFormat = null;
    String updatedOrganisationName = "Test Organisation", updatedTagline = "Testing Healthgraph Organisation";
    String updatedCountry = "Indonesia (ID) ", updatedSupportedCurrencies = "USD - US Dollar", updatedAddress1 = "Updated address1";
    String updatedAddress2 = "Updated address2", updatedPincode = "452005", updatedState = "Madhya Pradesh";
    String updatedCity = "Indore", updatedTelephone = "3333333333", updatedFax = "1", updatedWebsite = "www.demo.com";
    String updatedEmail = "updateddummy@gmail.com", updatedPanNumber = "GH54321", updatedServiceTaxNumber = "02";
    String updatedSmsContactNumber = "9999999999", updatedPreferredNumberFormat = "12345678.99 (Default)";
    @Test(enabled = true, description = "Add new user for the Organisation")
    public void addNewUserForFacility() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        boolean bCountryFound = false;
        boolean bUserFound = true;
        boolean bRadioButtonSelected = false;
        boolean bCheckboxSelected = false;
        String sCountryName = "India (IN) ";
        String sFacilityName = "TESTING_FACILITY";
        String sUserRole = "Admin";
        String sActiveUser = "Inactive";

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            Cls_Generic_Methods.customWait(2);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            DateFormat dateFormat = new SimpleDateFormat("ddMMyy_HHmmss");
            Date date = new Date();
            String sDateAsString = dateFormat.format(date);

            try {

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 8);

                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_addUser), "clicked on Add user");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_salutaionForTheUser, 8);

                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_salutaionForTheUser, Settings_Data.sSALUTATION), "Salutation =  <b> " + Settings_Data.sSALUTATION + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_userFullName, Settings_Data.sUSER_FULL_NAME), "User Full Name =  <b> " + Settings_Data.sUSER_FULL_NAME + "</b>");
                String sEmailID = Settings_Data.sEMAIL_ID + sDateAsString;

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_emailAddressOfTheUser, sEmailID), "Email ID  =  <b> " + sEmailID + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_emailDomain, Settings_Data.sEMAIL_DOMAIN), "Email domain =  <b> " + Settings_Data.sEMAIL_DOMAIN + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_genderOfTheUser, Settings_Data.sUSER_GENDER), "Gender  =  <b> " + Settings_Data.sUSER_GENDER + "</b>");

                Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.input_datePicker);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_month, Settings_Data.sMONTH);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_year, Settings_Data.sYEAR);
                Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.list_birthDateOfTheUser.get(0));
//                oPage_OrganisationSetup.list_birthDateOfTheUser.get(0).click();

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_mobileNumberOfTheUser, Settings_Data.sUSER_MOBILE_NUMBER), "Mobile number  =  <b> " + Settings_Data.sUSER_MOBILE_NUMBER + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_telephoneNumberOfTheUser, Settings_Data.sUSER_TELEPHONE_NUMBER), "User Full Name =  <b> " + Settings_Data.sUSER_TELEPHONE_NUMBER + "</b>");

                Cls_Generic_Methods.customWait(2);
                Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.input_countryNamefield);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_countryNameOfTheUser, sCountryName);

                try {
                    oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
                    for (WebElement eCountryName : oPage_OrganisationSetup.list_country) {
                        if (Cls_Generic_Methods.isElementDisplayed(eCountryName)) {
                            if (Cls_Generic_Methods.getTextInElement(eCountryName).trim().contains(sCountryName.trim())) {
                                bCountryFound = true;
                                Cls_Generic_Methods.clickElement(driver, eCountryName);
                                break;
                            }
                        }
                    }
                    m_assert.assertTrue(bCountryFound, "country selected as  = <b> " + sCountryName + "</b>");
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_address, Settings_Data.sADDRESS), "Address =  <b> " + Settings_Data.sADDRESS + "</b>");
                if (bCountryFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_pincodeOfTheUser, 10);
                    boolean bPinCodeSelected = false;
                    try {
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_pincodeOfTheUser, (Settings_Data.sPINCODE)),
                                (Settings_Data.sPINCODE) + " entered for Pin Code");
                        Cls_Generic_Methods.customWait();

                        for (WebElement pinCodeElement : oPage_OrganisationSetup.inputOptions_pincodeOnAddUserForm) {
                            if (Cls_Generic_Methods.getTextInElement(pinCodeElement).equals(Settings_Data.sPINCODE)) {
                                Cls_Generic_Methods.clickElement(pinCodeElement);
                                bPinCodeSelected = true;
                            }
                        }
                        m_assert.assertTrue(bPinCodeSelected, Settings_Data.sPINCODE + " entered for Pin Code");

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_next, 10);
                        Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.button_next);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_userDesignation, Settings_Data.sDESIGNATION), "Designation =  <b> " + Settings_Data.sDESIGNATION + "</b>");
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_employeeId, Settings_Data.sEMPLOYEE_ID), "Employee ID =  <b> " + Settings_Data.sEMPLOYEE_ID + "</b>");
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_registrationNumberOfTheUser, Settings_Data.sREGISTRATION_NUMBER), "Registration number =  <b> " + Settings_Data.sREGISTRATION_NUMBER + "</b>");
                        try {
                            oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
                            for (WebElement eUserRole : oPage_OrganisationSetup.list_userRole) {
                                if (Cls_Generic_Methods.isElementDisplayed(eUserRole)) {
                                    if (Cls_Generic_Methods.getTextInElement(eUserRole).contains(sUserRole.trim())) {
                                        bRadioButtonSelected = true;
                                        Cls_Generic_Methods.clickElement(driver, eUserRole);
                                        break;
                                    }
                                }
                            }
                            m_assert.assertTrue(bRadioButtonSelected, "Selected user role  = <b> " + sUserRole + "</b>");
                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("" + e);
                        }
                        if (Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.text_singleSpecialityRestrictionMessage)) {
                            try {
                                oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
                                for (WebElement eSpeciality : oPage_OrganisationSetup.list_specialitiesName) {
                                    if (Cls_Generic_Methods.isElementDisplayed(eSpeciality)) {
                                        int indexOfSpeciality = oPage_OrganisationSetup.list_specialitiesName.indexOf(eSpeciality);
                                        boolean bCheckIfSpecialitySelected = oPage_OrganisationSetup.list_checkboxesForSpecialities.get(indexOfSpeciality).isSelected();
                                        bCheckboxSelected = true;
                                        if (!bCheckIfSpecialitySelected) {
                                            Cls_Generic_Methods.clickElement(eSpeciality);
                                        }
                                        break;
                                    }
                                }
                                m_assert.assertTrue(bCheckboxSelected, "List Restricted to single specialty text present");
                            } catch (Exception e) {
                                e.printStackTrace();
                                m_assert.assertFatal("" + e);
                            }
                        } else {
                            oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
                            for (WebElement eSpeciality : oPage_OrganisationSetup.list_specialitiesName) {
                                if (Cls_Generic_Methods.isElementDisplayed(eSpeciality)) {
                                    int indexOfSpeciality = oPage_OrganisationSetup.list_specialitiesName.indexOf(eSpeciality);
                                    boolean bCheckIfSpecialitySelected = oPage_OrganisationSetup.list_checkboxesForSpecialities.get(indexOfSpeciality).isSelected();
                                    bCheckboxSelected = true;
                                    if (!bCheckIfSpecialitySelected) {
                                        Cls_Generic_Methods.clickElement(eSpeciality);
                                    }
                                    break;
                                }
                            }
                            m_assert.assertInfo(bCheckboxSelected, "List Restricted to single specialty is not present");
                        }
                        if (Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.text_subSpecialityList)) {
                            oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
                            for (WebElement eSubSpeciality : oPage_OrganisationSetup.list_subSpecialitiesName) {
                                if (Cls_Generic_Methods.isElementDisplayed(eSubSpeciality)) {
                                    int indexOfSubSpeciality = oPage_OrganisationSetup.list_subSpecialitiesName.indexOf(eSubSpeciality);
                                    boolean bCheckIfSpecialitySelected = oPage_OrganisationSetup.list_checkboxesForSubSpecialities.get(indexOfSubSpeciality).isSelected();
                                    bCheckboxSelected = true;
                                    if (!bCheckIfSpecialitySelected) {
                                        Cls_Generic_Methods.clickElement(eSubSpeciality);
                                    }

                                }
                            }

                            m_assert.assertTrue(bCheckboxSelected, "Sub speciality selection checkboxes present and selected ");
                        } else {
                            oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
                            for (WebElement eSubSpeciality : oPage_OrganisationSetup.list_subSpecialitiesName) {
                                if (Cls_Generic_Methods.isElementDisplayed(eSubSpeciality)) {
                                    int indexOfSubSpeciality = oPage_OrganisationSetup.list_subSpecialitiesName.indexOf(eSubSpeciality);
                                    boolean bCheckIfSpecialitySelected = oPage_OrganisationSetup.list_checkboxesForSubSpecialities.get(indexOfSubSpeciality).isSelected();
                                    bCheckboxSelected = true;
                                    if (!bCheckIfSpecialitySelected) {
                                        Cls_Generic_Methods.clickElement(eSubSpeciality);
                                    }

                                }
                            }
                            m_assert.assertInfo(bCheckboxSelected, "Sub speciality selection checkboxes are not present");
                        }
                        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
                        for (WebElement eDepartment : oPage_OrganisationSetup.list_departmentsNames) {
                            if (Cls_Generic_Methods.isElementDisplayed(eDepartment)) {
                                int indexOfSpeciality = oPage_OrganisationSetup.list_departmentsNames.indexOf(eDepartment);
                                boolean bCheckIfSpecialitySelected = oPage_OrganisationSetup.list_checkboxForDepartments.get(indexOfSpeciality).isSelected();
                                bCheckboxSelected = true;
                                if (!bCheckIfSpecialitySelected) {
                                    Cls_Generic_Methods.clickElement(eDepartment);
                                }
                            }
                        }
                        m_assert.assertTrue(bCheckboxSelected, "Department checkboxes are selected ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_saveUser, 10);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_saveUser), "clicked on save user button");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_facilityNameField, 10);
                        Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.input_facilityNameField);
                        Cls_Generic_Methods.customWait(1);
                        boolean bfacilityNamePresentInList = false;
                        try {
                            oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
                            for (WebElement eFacilityElement : oPage_OrganisationSetup.list_facilityNameFromDropdown) {
                                String nameOfFacilityToSelect = Cls_Generic_Methods.getTextInElement(eFacilityElement);
                                if (sFacilityName.contains(nameOfFacilityToSelect)) {
                                    bfacilityNamePresentInList = true;
                                    Cls_Generic_Methods.clickElement(driver, eFacilityElement);
                                    break;
                                }
                            }
                            m_assert.assertTrue(bfacilityNamePresentInList, "Facility selected as = <b> " + sFacilityName + "</b>");
                            if (bfacilityNamePresentInList) {
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_save, 5);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_save), "clicked on save facility button");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUserInOrgLevel, 10);
                                Cls_Generic_Methods.scrollToElementByJS(oPage_OrganisationSetup.button_allUserInOrgLevel);
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUserInOrgLevel, 10);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_allUserInOrgLevel), "clicked on all user button in Organisation setting");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_facilityUnderAllUser, 10);
                                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_facilityUnderAllUser, sFacilityName), "Facility name selected  =  <b> " + sFacilityName + "</b>");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_roleUnderAllUser, 10);
                                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_roleUnderAllUser, sUserRole), "User Role selected as =  <b> " + sUserRole + "</b>");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_activeStatusOfUser, 10);
                                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_activeStatusOfUser, sActiveUser), "Active status  =  <b> " + sActiveUser + "</b>");
                                Cls_Generic_Methods.customWait(1);
                            } else {
                                m_assert.assertTrue(false, "Required facility is not selected");
                            }
                            try {
                                int iReferralMessageIndex = -1;
                                for (WebElement eUserName : oPage_OrganisationSetup.list_userName) {
                                    oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
                                    String sTableValue = Cls_Generic_Methods.getTextInElement(eUserName);
                                    sTableValue = sTableValue.split("\n")[0].trim();
                                    String sUserNameOnTable = Settings_Data.sSALUTATION + " " + Settings_Data.sUSER_FULL_NAME;
                                    if (sTableValue.contains(sUserNameOnTable)) {
                                        iReferralMessageIndex = oPage_OrganisationSetup.list_userName.indexOf(eUserName);
                                        bUserFound = true;
                                        break;
                                    }
                                }
                                if (bUserFound) {
                                    Cls_Generic_Methods.scrollToElementByJS(oPage_OrganisationSetup.list_activateButtonName.get(iReferralMessageIndex));
                                    Cls_Generic_Methods.customWait();
                                    Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.list_activateButtonName.get(iReferralMessageIndex));
                                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_sendActivationUserLink, 10);
                                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_sendActivationUserLink), "clicked on send activation link button");
                                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 10);
                                } else {
                                    m_assert.assertTrue(false, "Not Able to find the user from the list");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                m_assert.assertFatal("" + e);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("" + e);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("" + e);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Delete the created new user")
    public void deleteCreatedUser() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        boolean bUserFound = true;
        String sFacilityName = "TESTING_FACILITY";
        String sUserRole = "Admin";
        String sActiveUser = "Inactive";
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUserInOrgLevel, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_allUserInOrgLevel), "clicked on all user button in Organisation setting");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_facilityUnderAllUser, 10);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_facilityUnderAllUser, sFacilityName), "Facility name selected  =  <b> " + sFacilityName + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_roleUnderAllUser, 10);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_roleUnderAllUser, sUserRole), "User Role selected as =  <b> " + sUserRole + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_activeStatusOfUser, 10);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_activeStatusOfUser, sActiveUser), "Active status  =  <b> " + sActiveUser + "</b>");
                Cls_Generic_Methods.customWait(1);

                try {
                    int iReferralMessageIndex = -1;
                    for (WebElement eUserName : oPage_OrganisationSetup.list_userName) {
                        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eUserName);
                        sTableValue = sTableValue.split("\n")[0].trim();
                        String sUserNameOnTable = Settings_Data.sSALUTATION + " " + Settings_Data.sUSER_FULL_NAME;
                        if (sTableValue.contains(sUserNameOnTable)) {
                            iReferralMessageIndex = oPage_OrganisationSetup.list_userName.indexOf(eUserName);
                            bUserFound = true;
                            break;
                        }
                    }

                    if (bUserFound) {
                        Cls_Generic_Methods.scrollToElementByJS(oPage_OrganisationSetup.list_deleteButtonName.get(iReferralMessageIndex));
                        Cls_Generic_Methods.customWait(2);
                        Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.list_deleteButtonName.get(iReferralMessageIndex));
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_confirm, 10);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.button_confirm), "Delete action performed successfully");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 10);
                    } else {
                        m_assert.assertTrue(false, "Not Able to find the user from the list");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Add and Validate New Facility")
    public void validateAndAddNewFacility() throws Exception {
        // fill the details and save
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        boolean bSpecialitySelected = false;
        boolean bCountrySelected = false;
        boolean bTimezoneSelected = false;
        boolean bDepartmentSelected = false;
        boolean bFacilityFound = false;
        boolean bRegionSelected = false;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addFacility, 5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_addFacility),
                        "Add Facility Button clicked");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_facilityName, 5);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_facilityName, uniqueStringForFacility)
                        , "Facility Name entered: <b> " + uniqueStringForFacility + " </b>");

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_facilityDisplayName, uniqueStringForFacility)
                        , "Facility Display Name entered: <b> " + uniqueStringForFacility + " </b>");
                Cls_Generic_Methods.customWait(1);

                if (Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.text_facilityDisplayNameError)) {
                    Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_facilityDisplayName);
                    String changeDisplayName = CommonActions.getRandomUniqueString(6);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_facilityDisplayName, changeDisplayName)
                            , "Facility Display Name changed entered: <b> " + changeDisplayName + " </b>");
                }

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_facilityCode, uniqueStringForFacility)
                        , "Facility Code entered: <b> " + uniqueStringForFacility + " </b>");
                Cls_Generic_Methods.customWait(1);

                if (Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.text_facilityCodeError)) {
                    Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_facilityCode);
                    String changeFacilityCode = CommonActions.getRandomUniqueString(6);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_facilityCode, changeFacilityCode)
                            , "Facility Display Name entered: <b> " + changeFacilityCode + " </b>");
                }
                //Select country
                Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.dropdown_facilityCountry);

                for (WebElement eCountryName : oPage_OrganisationSetup.list_countryNames) {
                    if (eCountryName.isDisplayed()) {
                        if (Cls_Generic_Methods.getTextInElement(eCountryName).contains(Settings_Data.sFACILITY_COUNTRY)) {
                            Cls_Generic_Methods.clickElement(eCountryName);
                            bCountrySelected = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bCountrySelected, "Country selected for facility <b> " + Settings_Data.sFACILITY_COUNTRY + "</b>");
                Cls_Generic_Methods.customWait(3);
                //Select Timezone
                oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
                Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.dropdown_timezone);
                for (WebElement eCountryName : oPage_OrganisationSetup.list_timezone) {
                    if (eCountryName.isDisplayed()) {
                        if (Cls_Generic_Methods.getTextInElement(eCountryName).contains(Settings_Data.sFACILITY_TIMEZONE)) {
                            Cls_Generic_Methods.clickElement(eCountryName);
                            bTimezoneSelected = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bTimezoneSelected, "Timezone selected for facility " + Settings_Data.sFACILITY_TIMEZONE);

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_facilityAddress, Settings_Data.sADDRESS)
                        , "Facility Address entered: <b> " + Settings_Data.sADDRESS + " </b>");

                //Select Region
                Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.dropdown_facilityRegion);
                for (WebElement eRegionName : oPage_OrganisationSetup.list_facilityRegion) {
                    if (eRegionName.isDisplayed()) {
                        if (Cls_Generic_Methods.getTextInElement(eRegionName).contains(Settings_Data.sFACILITY_REGION)) {
                            Cls_Generic_Methods.clickElement(eRegionName);
                            bRegionSelected = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bRegionSelected, "Timezone selected for facility " + Settings_Data.sFACILITY_REGION);

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_facilityPincode, Settings_Data.sPINCODE)
                        , "Facility Pincode entered: <b> " + Settings_Data.sPINCODE + " </b>");

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_facilityState, Settings_Data.sFACILITY_STATE)
                        , "Facility State entered: <b> " + Settings_Data.sFACILITY_STATE + " </b>");

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_facilityCity, Settings_Data.sFACILITY_CITY)
                        , "Facility City entered: <b> " + Settings_Data.sFACILITY_CITY + " </b>");

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_facilityEmail, Settings_Data.sEMAIL_ID)
                        , "Facility Email entered: <b> " + Settings_Data.sEMAIL_ID + " </b>");

                Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.dropdown_emailDomain);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.dropdown_emailDomain, Settings_Data.sEMAIL_DOMAIN),
                        "Facility email domain selected: <b> " + Settings_Data.sEMAIL_DOMAIN + " </b>");

                //Select Speciality
                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_next);
                Cls_Generic_Methods.customWait();
                for (WebElement eSpecialityName : oPage_OrganisationSetup.list_specialitiesForFacilityName) {
                    if (eSpecialityName.isDisplayed()) {
                        if (Cls_Generic_Methods.getTextInElement(eSpecialityName).contains(Settings_Data.sFACILITY_SPECIALTIY)) {
                            Cls_Generic_Methods.clickElement(eSpecialityName);
                            bSpecialitySelected = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bSpecialitySelected, "Speciality selected for facility <b> " + Settings_Data.sFACILITY_SPECIALTIY + "</b>");


                //Select Department
                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_next);
                Cls_Generic_Methods.customWait();
                for (WebElement eDepartmentName : oPage_OrganisationSetup.list_departmentName) {
                    if (eDepartmentName.isDisplayed()) {
                        if (Cls_Generic_Methods.getTextInElement(eDepartmentName).contains(Settings_Data.sFACILITY_DEPARTMENT)) {
                            Cls_Generic_Methods.clickElement(eDepartmentName);
                            bDepartmentSelected = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bDepartmentSelected, "Department selected for facility <b> " + Settings_Data.sFACILITY_DEPARTMENT + "</b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.button_saveFacility),
                        "Facility Saved");

                //find facility in table
                driver.navigate().refresh();
                oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addFacility, 5);
                for (WebElement e : oPage_OrganisationSetup.tableList_facilityName) {
                    if (e.isDisplayed()) {
                        Cls_Generic_Methods.getTextInElement(e).equalsIgnoreCase(uniqueStringForFacility);
                        bFacilityFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(bFacilityFound, "Facility found in table <b> " + uniqueStringForFacility + "</b>");


            } catch (Exception e) {
                m_assert.assertFatal("Error loading the application " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Searching and Editing the added facility")
    public void validateSearchAndEditFacility() {
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        int indexOfEditButton = -1;
        int indexOfFacilityName = -1;
        int indexOfDisableButton = -1;
        String editFacilityDisplayName = uniqueStringForFacility + " updated ";
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");

                //Search facility
                Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.input_searchFacility);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_searchFacility, uniqueStringForFacility);
                Cls_Generic_Methods.customWait(5);

                //Find the created facility on the table
                for (WebElement eFacilityName : oPage_OrganisationSetup.tableList_facilityName) {
                    if (Cls_Generic_Methods.isElementDisplayed(eFacilityName)) {
                        String sFacilityName = Cls_Generic_Methods.getTextInElement(eFacilityName);
                        if (sFacilityName.equalsIgnoreCase(uniqueStringForFacility)) {
                            indexOfFacilityName = oPage_OrganisationSetup.tableList_facilityName.indexOf(eFacilityName);
                            m_assert.assertTrue(true, "Facility found to be search and edit <b> " + sFacilityName + " </b>");
                            break;
                        }
                    }
                }

                if (indexOfFacilityName < 0) {
                    m_assert.assertTrue(false, "<b>" + uniqueStringForFacility + " </b> not found in Facility by Search");
                } else {
                    m_assert.assertTrue(true, "<b>" + uniqueStringForFacility + " </b> found in Facility by Search");
                }

                //Find the respected edit button and edit content
                for (WebElement btn_edit : oPage_OrganisationSetup.list_facilityEditButton) {
                    if (Cls_Generic_Methods.isElementDisplayed(btn_edit)) {
                        indexOfEditButton = oPage_OrganisationSetup.list_facilityEditButton.indexOf(btn_edit);
                    }

                    if (indexOfFacilityName == indexOfEditButton) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, btn_edit), "<b> Facility edit </b> button clicked ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_facilityName, 5);

                        Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_facilityDisplayName);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_facilityDisplayName, editFacilityDisplayName),
                                "Facility Display name updated: <b> " + editFacilityDisplayName + "</b>");

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.button_saveFacility), "<b> Facility update </b> clicked ");
                        Cls_Generic_Methods.customWait();
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_closeEditModal),
                                "Edit modal closed");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addFacility, 5);
                        break;
                    }
                }

                //disable the facility
                for (WebElement btn_del : oPage_OrganisationSetup.list_facilityDisableButton) {
                    if (Cls_Generic_Methods.isElementDisplayed(btn_del)) {
                        indexOfDisableButton = oPage_OrganisationSetup.list_facilityDisableButton.indexOf(btn_del);
                    }
                    if (indexOfFacilityName == indexOfDisableButton) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, btn_del), "<b> Facility disable </b> button clicked ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_confirmDisableFacility, 3);

                        Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_confirmDisableFacility);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addFacility, 5);
                        m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.list_userByRoles).equalsIgnoreCase("Activate facility first"),
                                "Facility disabled as text is: <b> " + Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.list_userByRoles) + "</b> ");
                        break;
                    }
                }
                Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_searchFacility);

            } catch (Exception e) {
                m_assert.assertFatal("Facility edit button not found " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validate Add new user for the Organisation")
    public void validateAllUsersFunctionality() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);

        String sFacilityName = "TESTING_FACILITY";
        String sDefaultOptionForDropdown = "All";
        String sUserRole = "Admin";
        String sActiveUser = "Active";
        String sInactiveUser = "Inactive";
        String sEditButton = "Edit";
        String sActivateButton = "Activate";


        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            Cls_Generic_Methods.customWait(2);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUserInOrgLevel, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_allUserInOrgLevel), "clicked on all user button in Organisation setting");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_facilityUnderAllUser, 10);
                if (Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_facilityUnderAllUser, sFacilityName)) {
                    Cls_Generic_Methods.customWait();
                    try {
                        boolean bFacilityFound = false;
                        for (WebElement eFacilityName : oPage_OrganisationSetup.list_facilityName) {
                            bFacilityFound = false;
                            int index = oPage_OrganisationSetup.list_facilityName.indexOf(eFacilityName);
                            WebElement CurrentUserName = oPage_OrganisationSetup.list_userName.get(index);
                            String sCurrentUserName = Cls_Generic_Methods.getTextInElement(CurrentUserName);
                            sCurrentUserName = sCurrentUserName.split("\n")[0].trim();
                            oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
                            String sTableValue = Cls_Generic_Methods.getTextInElement(eFacilityName);
                            if (sTableValue.contains(sFacilityName)) {
                                bFacilityFound = true;
                            }
                            m_assert.assertTrue(bFacilityFound, "User Name = <b> " + " " + sCurrentUserName + "</b>" + "  is linked to <b> " + sFacilityName + "</b> ");
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("" + e);
                    }
                } else {
                    m_assert.assertTrue(false, " " + sFacilityName + "Not selected");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_facilityUnderAllUser, 10);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_facilityUnderAllUser, sDefaultOptionForDropdown), "Default option is selected fro facility dropdown  =  <b> " + sDefaultOptionForDropdown + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_roleUnderAllUser, 10);
                oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
                if (Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_roleUnderAllUser, sUserRole)) {
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.search_user, sUserRole);
                    Cls_Generic_Methods.customWait();
                    try {
                        boolean bUserRole = false;
                        Cls_Generic_Methods.customWait();
                        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
                        for (WebElement eUserNameAndRole : oPage_OrganisationSetup.list_userName) {
                            bUserRole = false;
                            int index = oPage_OrganisationSetup.list_userName.indexOf(eUserNameAndRole);
                            WebElement CurrentUserName = oPage_OrganisationSetup.list_userName.get(index);
                            String sCurrentUserName = Cls_Generic_Methods.getTextInElement(CurrentUserName);
                            sCurrentUserName = sCurrentUserName.split("\n")[0].trim();
                            String sTableValue = Cls_Generic_Methods.getTextInElement(eUserNameAndRole);
                            System.out.println(sTableValue);
                            sTableValue = sTableValue.split("\n")[1].trim();
                            System.out.println(sTableValue);
                            if (sTableValue.contains(sUserRole)) {
                                bUserRole = true;
                            }
                            m_assert.assertTrue(bUserRole, "User Name = <b> " + " " + sCurrentUserName + "</b>" + "  Actual user role =  <b> " + sTableValue + "</b> " + " Expected User Role =  <b>" + sUserRole + "</b>");

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("" + e);
                    }
                } else {
                    m_assert.assertTrue(false, "<b> " + sUserRole + "</b> Not selected");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_roleUnderAllUser, 10);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_roleUnderAllUser, sDefaultOptionForDropdown), "Default option is selected fro facility dropdown  =  <b> " + sDefaultOptionForDropdown + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_roleUnderAllUser, 10);
                oPage_OrganisationSetup = new Page_OrganisationSetup(driver);

                if (Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_activeStatusOfUser, sActiveUser)) {
                    Cls_Generic_Methods.customWait();
                    try {
                        boolean bEditButtonPresent = false;
                        Cls_Generic_Methods.customWait();
                        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
                        for (WebElement eActiveState : oPage_OrganisationSetup.list_editAndActiveButtons) {
                            bEditButtonPresent = false;
                            String sTableValue = Cls_Generic_Methods.getTextInElement(eActiveState);
                            if (sTableValue.contains(sEditButton)) {
                                bEditButtonPresent = true;
                            }
                            m_assert.assertTrue(bEditButtonPresent, "Edit buttons Present as user state = <b> " + sActiveUser + "</b>");

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("" + e);
                    }
                } else {
                    m_assert.assertTrue(false, "<b>" + sActiveUser + "</b> Not selected");
                }

                if (Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_activeStatusOfUser, sInactiveUser)) {
                    Cls_Generic_Methods.customWait();
                    try {
                        boolean bActiveButtonPresent = false;
                        Cls_Generic_Methods.customWait();
                        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
                        for (WebElement eInActiveState : oPage_OrganisationSetup.list_editAndActiveButtons) {
                            bActiveButtonPresent = false;
                            String sTableValue = Cls_Generic_Methods.getTextInElement(eInActiveState);
                            System.out.println(sTableValue);
                            if (sTableValue.contains(sActivateButton)) {
                                bActiveButtonPresent = true;
                            }
                            m_assert.assertTrue(bActiveButtonPresent, "Activate buttons Present as user state = <b> " + sInactiveUser + "</b>");

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("" + e);
                    }
                } else {
                    m_assert.assertTrue(false, "<b> " + sInactiveUser + "</b> Not selected");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_addUser), "clicked on add user button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_close, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_close), "clicked on close button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_back, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_back), "clicked on back button");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Storing Existing Organisation Values")
    public void validateStoringExistingOrganisationValueFunctionality() {

        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_IDPrefix oPage_IDPrefix = new Page_IDPrefix(driver);


        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IDPrefix.input_idPrefixTextBox, 5);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_editOrganisation, 10);
                Cls_Generic_Methods.scrollToElementByAction(driver, oPage_OrganisationSetup.button_editOrganisation);
                // Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_editOrganisation, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_editOrganisation), " Edit Organisation Button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.text_editOrganisationTitle, 12);
                originalOrganisationName = Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_organisationName, "value");
                originalTagline = Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_organisationTagline, "value");
                originalCountry = Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_country, "title");
                originalSupportedCurrencies = Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.text_selectedSupportedCurrenciesInBox, "title");
                originalAddress1 = Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_address1, "value");
                originalAddress2 = Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_address2, "value");
                originalPincode = Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_pincode, "value");
                originalState = Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_state, "value");
                originalCity = Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_city, "value");
                originalTelephone = Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_telephone, "value");
                originalFax = Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_fax, "value");
                originalWebsite = Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_website, "value");
                originalEmail = Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_email, "value");
                originalPanNumber = Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_panNumber, "value");
                originalServiceTaxNumber = Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_serviceTaxNumber, "value");
                originalSmsContactNumber = Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_smsContactNumber, "value");
                originalPreferredNumberFormat = Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_preferredNumberFormat);
                Cls_Generic_Methods.scrollToElementByJS(oPage_CommonElements.button_closeTemplateWithoutSaving);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 2);
                m_assert.assertInfo("Existing Organisation Information's are " + originalOrganisationName + ", " + originalTagline + " ," + originalCountry
                        + " ," + originalSupportedCurrencies + " ," + originalAddress1 + " ," + originalAddress2 + ", " + originalPincode + " ," + originalState + " ," + originalCity + ", " + originalTelephone
                        + " ," + originalFax + ", " + originalWebsite + " " + originalEmail + ", " + originalPanNumber + " ," + originalServiceTaxNumber + " ," + originalSmsContactNumber + " ," + originalPreferredNumberFormat);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_closeTemplateWithoutSaving), "Close Button in Edit Organisation is clicked");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Editing and Validating Organisation Details In Organisation Setting")
    public void validateEditOrganisationDetailsFunctionality() {

        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_IDPrefix oPage_IDPrefix = new Page_IDPrefix(driver);

        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IDPrefix.input_idPrefixTextBox, 5);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_editOrganisation, 10);
                Cls_Generic_Methods.scrollToElementByJS(oPage_OrganisationSetup.button_editOrganisation);
                //  Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_editOrganisation, 2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_editOrganisation), " Edit Organisation Button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.text_editOrganisationTitle, 12);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_organisationName));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_organisationName, updatedOrganisationName), "Organisation Name is entered as - " + updatedOrganisationName);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_organisationTagline));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_organisationTagline, updatedTagline), "Tagline is entered as - " + updatedTagline);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.dropdown_countryArrow), "Country arrow is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.list_countrySelectionList, updatedCountry), "New Country selected as -" + updatedCountry);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_removeSelectedCurrency), "Supported Currencies is cleared");
                m_assert.assertTrue(CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_OrganisationSetup.list_supportedCurrenciesList, updatedSupportedCurrencies), "New Supported Currencies selected as -" + updatedSupportedCurrencies);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_address1));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_address1, updatedAddress1), "Address 1 is entered as - " + updatedAddress1);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_address2));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_address2, updatedAddress2), "Address 2 is entered as - " + updatedAddress2);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_pincode));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_pincode, updatedPincode), "Pincode is entered as - " + updatedPincode);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_state));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_state, updatedState), "State is entered as - " + updatedState);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_city));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_city, updatedCity), "City is entered as - " + updatedCity);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_telephone));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_telephone, updatedTelephone), "Telephone is entered as - " + updatedTelephone);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_fax));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_fax, updatedFax), "Fax is entered as - " + updatedFax);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_website));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_website, updatedWebsite), "Website is entered as - " + updatedWebsite);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_email));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_email, updatedEmail), "Email is entered as - " + updatedEmail);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_panNumber));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_panNumber, updatedPanNumber), "Pan No. is entered as - " + updatedPanNumber);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_serviceTaxNumber));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_serviceTaxNumber, updatedServiceTaxNumber), "Service Tax Number is entered as - " + updatedServiceTaxNumber);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_smsContactNumber));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_smsContactNumber, updatedSmsContactNumber), "Sms Contact Number is entered as - " + updatedSmsContactNumber);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.input_preferredNumberFormat, updatedPreferredNumberFormat), "Preferred Number Format Selected");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_saveChanges), "Save Changes Button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 12);
                //  Cls_Generic_Methods.scrollToElementByJS(oPage_CommonElements.button_closeTemplateWithoutSaving);
                //  Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_closeTemplateWithoutSaving), "Close Button  is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_editOrganisation, 12);

                //Validating Updated Organisation Information
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_editOrganisation), " Edit Organisation Button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.text_editOrganisationTitle, 12);
                m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_organisationName, "value"), updatedOrganisationName, "Updated Organisation Name is Matched");
                m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_organisationTagline, "value"), updatedTagline, "Updated Tagline is Matched");
                m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_country, "title"), updatedCountry, "Updated Country is Matched");
                m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.text_selectedSupportedCurrenciesInBox, "title"), updatedSupportedCurrencies, "Updated Supported Currencies is Matched");
                m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_address1, "value"), updatedAddress1, "Updated Address1 is Matched");
                m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_address2, "value"), originalAddress2, "Updated Address2 is Matched");
                m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_pincode, "value"), updatedPincode, "Updated Pincode is Matched");
                m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_state, "value"), updatedState, "Updated State is Matched");
                m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_city, "value"), updatedCity, "Updated City is Matched");
                m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_telephone, "value"), updatedTelephone, "Updated Telephone is Matched");
                m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_fax, "value"), updatedFax, "Updated Fax is Matched");
                m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_website, "value"), updatedWebsite, "Updated Website is Matched");
                m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_email, "value"), updatedEmail, "Updated Email is Matched");
                m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_panNumber, "value"), updatedPanNumber, "Updated Pan Number is Matched");
                m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_serviceTaxNumber, "value"), updatedServiceTaxNumber, "Updated Service Tax Number is Matched");
                m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.input_smsContactNumber, "value"), updatedSmsContactNumber, "Updated Sms Contact Number is Matched");
                m_assert.assertEquals(Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_preferredNumberFormat), updatedPreferredNumberFormat, "Updated Preferred Number Format is Matched");
                //  Cls_Generic_Methods.scrollToElementByJS(oPage_CommonElements.button_closeTemplateWithoutSaving);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_closeTemplateWithoutSaving), "Close Button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_editOrganisation, 10);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Resting Edit Organisation Details To Initial Value")
    public void validateResetEditOrganisationDetailsFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        Page_IDPrefix oPage_IDPrefix = new Page_IDPrefix(driver);

        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IDPrefix.input_idPrefixTextBox, 5);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_editOrganisation, 10);
                Cls_Generic_Methods.scrollToElementByJS(oPage_OrganisationSetup.button_editOrganisation);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_editOrganisation, 2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_editOrganisation), " Edit Organisation Button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.text_editOrganisationTitle, 12);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_organisationName));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_organisationName, originalOrganisationName), "Organisation Name is entered as - " + originalOrganisationName);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_organisationTagline));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_organisationTagline, originalTagline), "Tagline is entered as - " + originalTagline);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.dropdown_countryArrow), "Country arrow is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.list_countrySelectionList, originalCountry), "New Country selected as -" + originalCountry);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_removeSelectedCurrency), "Supported Currencies is cleared");
                m_assert.assertTrue(CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_OrganisationSetup.list_supportedCurrenciesList, originalSupportedCurrencies), "New Supported Currencies selected as -" + originalSupportedCurrencies);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_address1));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_address1, originalAddress1), "Address 1 is entered as - " + originalAddress1);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_address2));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_address2, originalAddress2), "Address 2 is entered as - " + originalAddress2);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_pincode));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_pincode, originalPincode), "Pincode is entered as - " + originalPincode);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_state));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_state, originalState), "State is entered as - " + originalState);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_city));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_city, originalCity), "City is entered as - " + originalCity);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_telephone));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_telephone, originalTelephone), "Telephone is entered as - " + originalTelephone);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_fax));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_fax, originalFax), "Fax is entered as - " + originalFax);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_website));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_website, originalWebsite), "Website is entered as - " + originalWebsite);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_email));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_email, originalEmail), "Email is entered as - " + originalEmail);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_panNumber));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_panNumber, originalPanNumber), "Pan No. is entered as - " + originalPanNumber);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_serviceTaxNumber));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_serviceTaxNumber, originalServiceTaxNumber), "Service Tax Number is entered as - " + originalServiceTaxNumber);
                m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_smsContactNumber));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_smsContactNumber, originalSmsContactNumber), "Sms Contact Number is entered as - " + originalSmsContactNumber);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.input_preferredNumberFormat, originalPreferredNumberFormat), "Preferred Number Format Selected as " + originalPreferredNumberFormat);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_saveChanges), "Save Changes Button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.module_savedModule, 12);
                Cls_Generic_Methods.scrollToElementByJS(oPage_CommonElements.button_closeTemplateWithoutSaving);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_closeTemplateWithoutSaving), "Close Button  is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_editOrganisation, 12);


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(enabled = true, description = "validate select All Policies Radio Button in organization Settings ")
    public void ValidateSelectAllPoliciesRadioButton() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            Cls_Generic_Methods.customWait(2);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.customWait(6);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_Policy), "click on policy button");
                Cls_Generic_Methods.customWait(3);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_noPolicy),
                        "select inventory tab and select all policies NO option");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_noPolicy,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_yesPolicy),
                        "select inventory tab and select all policies YES option");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_yesPolicy,5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.select_counsellorTab),
                        "select Counsellor tab ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_counsellorTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_noPolicyInCounsellorTab),
                        "select All policies NO option in COUNSELLOR");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_noPolicyInCounsellorTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_yesPolicyInCounsellorTab),
                        "select All policies YES option in COUNSELLOR");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_yesPolicyInCounsellorTab,5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.select_ehrSettingsTab),
                        "select EHR SETTINGS tab ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_ehrSettingsTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_noPolicyEhrSettingsTab),
                        "select All policies NO option in EHR SETTINGS ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_noPolicyEhrSettingsTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_yesPolicyInEhrSettingsTab),
                        "select All policies YES option in EHR SETTINGS");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_yesPolicyInEhrSettingsTab,5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.select_ipdandOtTab),
                        "select IPD and OT tab ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_ipdandOtTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_noPolicyipdandOtTab),
                        "select All policies NO option in IPD and OT");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_noPolicyipdandOtTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_yesPolicyInipdandOtTab),
                        "select All policies YES option in IPD and OT");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_yesPolicyInipdandOtTab,5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.select_misClinicalReportsTab),
                        "select MIS CLINICAL REPORTS tab ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_misClinicalReportsTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_noPolicymisClinicalReportsTab),
                        "select All policies NO option in MIS CLINICAL REPORTS");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_noPolicymisClinicalReportsTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_yesPolicyInmisClinicalReportsTab),
                        "select All policies YES option in MIS CLINICAL REPORTS");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_yesPolicyInmisClinicalReportsTab,5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.select_misFinanceReportsTab),
                        "select MIS FINANCE REPORTS tab ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_misFinanceReportsTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_noPolicymisFinanceReportsTab),
                        "select All policies NO option in MIS FINANCE REPORTS");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_noPolicymisFinanceReportsTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_yesPolicyInmisFinanceReportsTab),
                        "select All policies YES option in MIS FINANCE REPORTS");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_yesPolicyInprintAndEmailTab,5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.select_opdTab),
                        "select OPD tab ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_opdTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_noPolicyopdTab),
                        "select All policies NO option in OPD");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_noPolicyopdTab,3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_yesPolicyInopdTab),
                        "select All policies YES option in OPD");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_yesPolicyInopdTab,3);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.select_printAndEmailTab),
                        "select PRINT and EMAIL tab ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_printAndEmailTab,3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_noPolicyprintAndEmailTab),
                        "select All policies NO option in PRINT and EMAIL");
                Cls_Generic_Methods.waitForElementToBeDisplayed
                        (oPage_OrganisationSetup.input_noPolicyprintAndEmailTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_yesPolicyInprintAndEmailTab),
                        "select All policies YES option in PRINT and EMAIL");
                Cls_Generic_Methods.waitForElementToBeDisplayed
                        (oPage_OrganisationSetup.input_yesPolicyInprintAndEmailTab,5);


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(enabled = true, description = "validate select All Policies Radio Button in Authorization in policy Template ")
    public void ValidateSelectAllPoliciesRadioButtonInAuthorization() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            Cls_Generic_Methods.customWait(2);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Authorization", "Policy Template");
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_addPolicyTemplate),
                        "click on ADD POLICY TEMPLATE");
                Cls_Generic_Methods.customWait(5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_noPolicy),
                        "select inventory tab and select all policies NO option");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_noPolicy,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_yesPolicy),
                        "select inventory tab and select all policies YES option");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_yesPolicy,5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.select_counsellorTab),
                        "select Counsellor tab ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_counsellorTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_noPolicyInCounsellorTab),
                        "select All policies NO option in COUNSELLOR");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_noPolicyInCounsellorTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_yesPolicyInCounsellorTab),
                        "select All policies YES option in COUNSELLOR");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_yesPolicyInCounsellorTab,5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.select_ehrSettingsTab),
                        "select EHR SETTINGS tab ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_ehrSettingsTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_noPolicyEhrSettingsTab),
                        "select All policies NO option in EHR SETTINGS ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_noPolicyEhrSettingsTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_yesPolicyInEhrSettingsTab),
                        "select All policies YES option in EHR SETTINGS");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_yesPolicyInEhrSettingsTab,5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.select_ipdandOtTab),
                        "select IPD and OT tab ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_ipdandOtTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_noPolicyipdandOtTab),
                        "select All policies NO option in IPD and OT");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_noPolicyipdandOtTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_yesPolicyInipdandOtTab),
                        "select All policies YES option in IPD and OT");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_yesPolicyInipdandOtTab,5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.select_misClinicalReportsTab),
                        "select MIS CLINICAL REPORTS tab ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_misClinicalReportsTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_noPolicymisClinicalReportsTab),
                        "select All policies NO option in MIS CLINICAL REPORTS");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_noPolicymisClinicalReportsTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_yesPolicyInmisClinicalReportsTab),
                        "select All policies YES option in MIS CLINICAL REPORTS");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_yesPolicyInmisClinicalReportsTab,5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.select_misFinanceReportsTab),
                        "select MIS FINANCE REPORTS tab ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_misFinanceReportsTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_noPolicymisFinanceReportsTab),
                        "select All policies NO option in MIS FINANCE REPORTS");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_noPolicymisFinanceReportsTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_yesPolicyInmisFinanceReportsTab),
                        "select All policies YES option in MIS FINANCE REPORTS");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_yesPolicyInprintAndEmailTab,5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.select_opdTab),
                        "select OPD tab ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_opdTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_noPolicyopdTab),
                        "select All policies NO option in OPD");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_noPolicyopdTab,3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_yesPolicyInopdTab),
                        "select All policies YES option in OPD");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_yesPolicyInopdTab,3);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.select_printAndEmailTab),
                        "select PRINT and EMAIL tab ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_printAndEmailTab,3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_noPolicyprintAndEmailTab),
                        "select All policies NO option in PRINT and EMAIL");
                Cls_Generic_Methods.waitForElementToBeDisplayed
                        (oPage_OrganisationSetup.input_noPolicyprintAndEmailTab,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.input_yesPolicyInprintAndEmailTab),
                        "select All policies YES option in PRINT and EMAIL");
                Cls_Generic_Methods.waitForElementToBeDisplayed
                        (oPage_OrganisationSetup.input_yesPolicyInprintAndEmailTab,5);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Editing and Validating Organisation Details In Organisation Setting")
    public void validateUserCountFunctionality() {

        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_IDPrefix oPage_IDPrefix = new Page_IDPrefix(driver);
        String sFacilityName = "Ophthalpractice";
        String sUserRole = "Admin";
        String sActiveUser = "Active";


        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IDPrefix.input_idPrefixTextBox, 5);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_editOrganisation, 10);

                String userCountInfoUI = Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_userCountInfo);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.text_userCountInfo),
                        " User Count Info Displayed In Organisation Setup as : "+userCountInfoUI);
                int totalUserCount = getUserOrFacilityCount(oPage_OrganisationSetup.list_allUsersNameList,"All User");
                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_firstInUsers);
                Cls_Generic_Methods.customWait();
                int activeUserCount = getUserOrFacilityCount(oPage_OrganisationSetup.list_allUsersEditActionList,"Active User");
                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_firstInUsers);
                Cls_Generic_Methods.customWait();
                int totalInactiveUserCount = getUserOrFacilityCount(oPage_OrganisationSetup.list_allUsersActivateActionList,"Inactive User");

                String userCountCalculated = "Total Users: "+totalUserCount+" | Active Users: "+activeUserCount+" | Inactive Users: "+totalInactiveUserCount;
                m_assert.assertTrue(userCountInfoUI.equalsIgnoreCase(userCountCalculated),
                        " Total Users ,Active User ,Inactive User Count Displayed correctly as : "+userCountInfoUI);

                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_facilityUnderAllUser, sFacilityName),
                        "Facility name selected  =  <b> " + sFacilityName + "</b>");
                Cls_Generic_Methods.customWait();

                String userCountInfoUIAfterFacility = Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_userCountInfo);

                int totalUserCountAfterFacility = getUserOrFacilityCount(oPage_OrganisationSetup.list_allUsersNameList,"All User");
                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_firstInUsers);
                Cls_Generic_Methods.customWait();
                int activeUserCountAfterFacility = getUserOrFacilityCount(oPage_OrganisationSetup.list_allUsersEditActionList,"Active User");
                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_firstInUsers);
                Cls_Generic_Methods.customWait();
                int totalInactiveUserCountAfterFacility = getUserOrFacilityCount(oPage_OrganisationSetup.list_allUsersActivateActionList,"Inactive User");

                userCountCalculated = "Total Users: "+totalUserCountAfterFacility+" | Active Users: "+activeUserCountAfterFacility+" | Inactive Users: "+totalInactiveUserCountAfterFacility;
                m_assert.assertTrue(userCountInfoUIAfterFacility.equalsIgnoreCase(userCountCalculated),
                        " Total Users ,Active User ,Inactive User Count Displayed correctly  After Single Facility Selection as : "+userCountInfoUIAfterFacility);


                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_roleUnderAllUser, sUserRole),
                        "Role name selected  =  <b> " + sUserRole + "</b>");
                Cls_Generic_Methods.customWait();

                String userCountInfoUIAfterRole = Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_userCountInfo);

                int totalUserCountAfterRole = getUserOrFacilityCount(oPage_OrganisationSetup.list_allUsersNameList,"All User");
                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_firstInUsers);
                Cls_Generic_Methods.customWait();
                int activeUserCountAfterRole = getUserOrFacilityCount(oPage_OrganisationSetup.list_allUsersEditActionList,"Active User");
                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_firstInUsers);
                Cls_Generic_Methods.customWait();
                int totalInactiveUserCountAfterRole = getUserOrFacilityCount(oPage_OrganisationSetup.list_allUsersActivateActionList,"Inactive User");

                userCountCalculated = "Total Users: "+totalUserCountAfterRole+" | Active Users: "+activeUserCountAfterRole+" | Inactive Users: "+totalInactiveUserCountAfterRole;
                m_assert.assertTrue(userCountInfoUIAfterRole.equalsIgnoreCase(userCountCalculated),
                        " Total Users ,Active User ,Inactive User Count Displayed correctly  After Single Role Selection as : "+userCountInfoUIAfterRole);


                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_activeStatusOfUser, sActiveUser),
                        "Status name selected  =  <b> " + sActiveUser + "</b>");
                Cls_Generic_Methods.customWait();

                String userCountInfoUIAfterActive = Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_userCountInfo);

                int totalUserCountAfterActive = getUserOrFacilityCount(oPage_OrganisationSetup.list_allUsersNameList,"All User");
                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_firstInUsers);
                Cls_Generic_Methods.customWait();
                int activeUserCountAfterActive = getUserOrFacilityCount(oPage_OrganisationSetup.list_allUsersEditActionList,"Active User");


                userCountCalculated = "Total Users: "+totalUserCountAfterActive+" | Active Users: "+activeUserCountAfterActive;
                m_assert.assertTrue(userCountInfoUIAfterActive.equalsIgnoreCase(userCountCalculated),
                        " Total Users ,Active User Count Displayed correctly  After Active Status Selection as : "+userCountInfoUIAfterActive);

                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_activeStatusOfUser, "Inactive"),
                        "Status name selected  =  <b> " + "Inactive" + "</b>");
                Cls_Generic_Methods.customWait();

                String userCountInfoUIAfterInactive = Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_userCountInfo);

                int totalUserCountAfterInactive = getUserOrFacilityCount(oPage_OrganisationSetup.list_allUsersNameList,"All User");
                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_firstInUsers);
                Cls_Generic_Methods.customWait();
                int activeUserCountAfterInactive = getUserOrFacilityCount(oPage_OrganisationSetup.list_allUsersActivateActionList,"Inactive User");


                userCountCalculated = "Total Users: "+totalUserCountAfterInactive+" | Inactive Users: "+activeUserCountAfterInactive;
                m_assert.assertTrue(userCountInfoUIAfterInactive.equalsIgnoreCase(userCountCalculated),
                        " Total Users ,Inactive User Count Displayed correctly  After Inactive Status Selection as : "+userCountInfoUIAfterInactive);

                addNewUserForFacility();

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait(5);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_editOrganisation, 10);

                String userCountInfoUIAfterNewUser = Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_userCountInfo);
                String userCountCalculatedNewUser = "Total Users: "+(totalUserCount+1)+" | Active Users: "+activeUserCount+" | Inactive Users: "+(totalInactiveUserCount+1);
                m_assert.assertTrue(userCountInfoUIAfterNewUser.equalsIgnoreCase(userCountCalculatedNewUser),
                        " Total Users ,Active User ,Inactive User Count Displayed correctly After New User creation as : "+userCountInfoUIAfterNewUser);


                Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_searchUser,"Deepak");
                Cls_Generic_Methods.customWait(3);

                Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_showUserEntries,"100");
                Cls_Generic_Methods.customWait();
                for(WebElement userName : oPage_OrganisationSetup.list_allUsersNameList){
                    int i = oPage_OrganisationSetup.list_allUsersNameList.indexOf(userName);
                    String userNameText = Cls_Generic_Methods.getTextInElement(userName);
                    if(userNameText.contains("Deepak")){
                        WebElement actionElement = oPage_OrganisationSetup.list_actionButtonName.get(i);
                        WebElement disableUser  = actionElement.findElement(By.xpath("./span[2]"));
                        Cls_Generic_Methods.clickElement(disableUser);
                        Cls_Generic_Methods.customWait(3);
                        Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_confirm);
                        Cls_Generic_Methods.customWait();
                        break;

                    }
                }

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForPageLoad(driver,5);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.customWait( 7);


                String userCountInfoUIAfterDeactivate = Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_userCountInfo);
                String userCountCalculatedDeactivate = "Total Users: "+(totalUserCount+1)+" | Active Users: "+(activeUserCount-1)+" | Inactive Users: "+(totalInactiveUserCount+2);
                m_assert.assertTrue(userCountInfoUIAfterDeactivate.equalsIgnoreCase(userCountCalculatedDeactivate),
                        " Total Users ,Active User ,Inactive User Count Displayed correctly Disabling User as : "+userCountInfoUIAfterDeactivate);

                Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_searchUser);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_searchUser,"Deepak");
                Cls_Generic_Methods.customWait(3);

                Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_showUserEntries,"100");
                Cls_Generic_Methods.customWait();
                for(WebElement userName : oPage_OrganisationSetup.list_allUsersNameList){
                    int i = oPage_OrganisationSetup.list_allUsersNameList.indexOf(userName);
                    String userNameText = Cls_Generic_Methods.getTextInElement(userName);
                    if(userNameText.contains("Deepak")){
                        WebElement actionElement = oPage_OrganisationSetup.list_actionButtonName.get(i);
                        WebElement enableUser  = actionElement.findElement(By.xpath("./span"));
                        Cls_Generic_Methods.clickElement(enableUser);
                        Cls_Generic_Methods.customWait(3);
                        break;

                    }
                }

                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.customWait( 5);
                String userCountInfoUIAfterActivate = Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_userCountInfo);
                String userCountCalculatedActivate = "Total Users: "+(totalUserCount+1)+" | Active Users: "+(activeUserCount)+" | Inactive Users: "+(totalInactiveUserCount+1);
                m_assert.assertTrue(userCountInfoUIAfterActivate.equalsIgnoreCase(userCountCalculatedActivate),
                        " Total Users ,Active User ,Inactive User Count Displayed correctly Enabling User as : "+userCountInfoUIAfterActivate);


                deleteCreatedUser();

                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.customWait( 7);
                String userCountInfoUIAfterDeleteUser = Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_userCountInfo);
                String userCountCalculatedDeleteUser = "Total Users: "+totalUserCount+" | Active Users: "+activeUserCount+" | Inactive Users: "+totalInactiveUserCount;
                m_assert.assertTrue(userCountInfoUIAfterDeleteUser.equalsIgnoreCase(userCountCalculatedDeleteUser),
                        " Total Users ,Active User ,Inactive User Count Displayed correctly After Delete User creation as : "+userCountInfoUIAfterDeleteUser);

                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_addFacility);
                Cls_Generic_Methods.customWait();

                int totalFacilityCount = getUserOrFacilityCount(oPage_OrganisationSetup.tableList_facilityName,"Facility");
                int totalInactiveFacilityCount = getUserOrFacilityCount(oPage_OrganisationSetup.list_inactiveFacilityList,"Facility");

                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_back);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_editOrganisation);
                Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OrganisationSetup.button_orgSubscriptionDetailsButton,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_orgSubscriptionDetailsButton),
                        "Subscription Details Button Clicked In Edit Organisation");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_organisationName).equalsIgnoreCase("Organisation Name :  HG Organisation"),
                        " Organisation Name Displayed as  Organisation Name :  HG Organisation");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.header_facilityAndUser),
                        " Facility And User Header Displayed Correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_activeFacility).contains(String.valueOf(totalFacilityCount-totalInactiveFacilityCount)),
                        " Facility Count Displayed correctly as : "+Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_activeFacility));
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_inActiveFacility).contains(String.valueOf(totalInactiveFacilityCount)),
                        " In active Facility Count Displayed correctly as "+ Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_inActiveFacility));

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_activeUser).contains(String.valueOf(activeUserCount)),
                        " Active User Count Displayed correctly as : "+Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_activeUser));
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_inActiveUser).contains(String.valueOf(totalInactiveUserCount)),
                        " In active User Count Displayed correctly as "+ Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_inActiveUser));

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.header_subscriptionDetailsSection),
                        " Subscription Details Section Header Displayed as Subscription Details  :");

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_subscriptionQuota).equalsIgnoreCase("Subscription Quota  :  Yearly"),
                        " Subscription Quota  :  Yearly Text Displayed Correctly");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.text_subscriptionUser),
                        " Subscription User Text Displayed as : "+(Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_subscriptionUser)));
                String accountExpireInText = Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_accountExpiresIn);

                int numberOfExpireDays = Integer.parseInt(accountExpireInText.split(" ")[6]);

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.text_accountExpiresIn),
                        " Account Expires In Displayed as : "+accountExpireInText);

                String accountExpireOnText = Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_accountExpiresOn);

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.text_accountExpiresOn),
                        " Account Expires on is Displayed as  "+accountExpireOnText);

                if(numberOfExpireDays<=30) {
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.text_accountExpireWaringMessage),
                            " Account Expires on Warning Message is Displayed ");
                }

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.text_subscriptionPricingType),
                        " Subscription Pricing Type Displayed as :"+Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_subscriptionPricingType));

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.text_activeUserInSubscriptionDetails),
                        " Active User Displayed In Subscription Details "+Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_activeUserInSubscriptionDetails));
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.text_availableUserInSubscriptionDetails),
                        " Available User Displayed In Subscription Details "+Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.text_availableUserInSubscriptionDetails));

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.text_clickHereForMoreUserInSubscriptionDetails),
                        " Click Here to Request More User displayed correctly ");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.text_comingSoonForMoreUser),
                        " Coming Soon for Click Here  displayed correctly ");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.header_billingHistorySection),
                        " Billing History Header Displayed");

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.text_noDataText),
                        " No Data Displayed Correctly for Billing History");
                String billingHistoryTableHeaderList[] = {"Invoice Status","Billing Date","Plan","User","Invoice"};

                for(WebElement header : oPage_OrganisationSetup.list_billingHistoryTableHeaderList){
                    int index = oPage_OrganisationSetup.list_billingHistoryTableHeaderList.indexOf(header);
                    String headerText = Cls_Generic_Methods.getTextInElement(header);
                    if(headerText.equalsIgnoreCase(billingHistoryTableHeaderList[index])){
                        m_assert.assertTrue(headerText+" Header Is Displayed Correctly");
                    }else{
                        m_assert.assertFalse(true ," Header Is Not Displayed");
                    }
                }

                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                Cls_Generic_Methods.customWait();

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    public int getUserOrFacilityCount(List<WebElement> listOfElement , String type){
        int count = 0 ;
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);

        try {
            if(type.equalsIgnoreCase("Facility")){
                Cls_Generic_Methods.selectElementByValue(oPage_OrganisationSetup.select_showFacilityEntries, "100");
                Cls_Generic_Methods.customWait(3);
            }else {
                Cls_Generic_Methods.selectElementByValue(oPage_OrganisationSetup.select_showUserEntries, "100");
                Cls_Generic_Methods.customWait(3);
            }
            for(int i =0 ;i< oPage_OrganisationSetup.list_paginationNumberList.size();i++){

             /*   if(type.equalsIgnoreCase("Facility")){
                    count = count + oPage_OrganisationSetup.tableList_facilityName.size();
                }else if (type.equalsIgnoreCase("Active User")){
                    count = count + oPage_OrganisationSetup.list_allUsersEditActionList.size();
                }else if(type.equalsIgnoreCase("Inactive User")){
                    count = count + oPage_OrganisationSetup.list_allUsersActivateActionList.size();
                } else if (type.equalsIgnoreCase("All User")){
                    count = count + oPage_OrganisationSetup.list_allUsersNameList.size();
                }else{
                    m_assert.assertInfo(" Type is Not Present");
                }*/
                count = count + listOfElement.size();

                if(Cls_Generic_Methods.isElementEnabled(oPage_OrganisationSetup.button_nextInUsers)) {
                    Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_nextInUsers);
                }
                Cls_Generic_Methods.customWait(3);

            }



        }catch (Exception e){
            e.printStackTrace();
        }

        return count;
    }

}



