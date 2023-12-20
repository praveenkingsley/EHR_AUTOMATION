package tests.settings.facilitySetting.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.OPD_Data;
import data.Settings_Data;
import data.settingsData.FacilitySettings_Data;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;
import pages.settings.facilitySettings.general.facilityContactList.Page_FacilityContactList;
import pages.settings.facilitySettings.general.otSetup.Page_OtSetup;

import static data.settingsData.FacilitySettings_Data.*;

public class FacilityContactListTest extends TestBase {

    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();

    @Test(enabled = true, description = "Create Patient For Validating Facility Contact List")
    public void createPatientToValidateFacilityContactList() {
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Page_Navbar oPage_Navbar = new Page_Navbar(driver);

            try {
                try {
                    if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                        CommonActions.openPatientRegisterationAndAppointmentForm();
                    } else {
                        CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm, "Patient Details");
                        Cls_Generic_Methods.customWait(1);
                    }
                } catch (NoSuchElementException e1) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                }
                // Entering Essential Form Data
                if (!myPatient.getsSALUTATION().isEmpty()) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()), "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
                }

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()), "First Name is entered as - " + myPatient.getsFIRST_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_middleNameOnPatientRegForm, myPatient.getsMIDDLE_NAME()), "Middle Name is entered as - " + myPatient.getsMIDDLE_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_lastNameOnPatientRegForm, myPatient.getsLAST_NAME()), "Last Name is entered as - " + myPatient.getsLAST_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, myPatient.getsMOBILE_NUMBER()), "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm), "Validate that Create Appointment button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 20);
                Cls_Generic_Methods.customWait(8);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Exception while creating patient" + e);
        }
    }

    @Test(enabled = true, description = "Validate Add New Contact In Facility Contact List Setting")
    public void validateAddContactInFacilitySetting() {
        Page_FacilityContactList oPage_FacilityContactList = new Page_FacilityContactList(driver);
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                Cls_Generic_Methods.customWait(5);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(4);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Facility Contact List");
                try {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.button_addContactInFacilityContactList, 20);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElementByJS(driver,
                                    oPage_FacilityContactList.button_addContactInFacilityContactList),
                            "Clicked on Add contact button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.input_firstNameOfContact, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                    oPage_FacilityContactList.select_contactGroup, FacilitySettings_Data.sCONTACT_GROUP),
                            "Contact group selected is selected as - " + FacilitySettings_Data.sCONTACT_GROUP);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.input_firstNameOfContact, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                    oPage_FacilityContactList.select_selectSalutationOfContact, FacilitySettings_Data.sSALUTATION_PRIMARY_CONTACT),
                            "Salutation of primary contact selected as - " + FacilitySettings_Data.sSALUTATION_PRIMARY_CONTACT);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.input_firstNameOfContact, 10);
                    m_assert.assertInfo(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_FacilityContactList.input_firstNameOfContact,
                                    (FacilitySettings_Data.sFIRST_NAME_PRIMARY_CONTACT)),
                            (FacilitySettings_Data.sFIRST_NAME_PRIMARY_CONTACT) + " entered for First Name");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.input_middleNameOfContact, 10);
                    m_assert.assertInfo(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_FacilityContactList.input_middleNameOfContact,
                                    (FacilitySettings_Data.sMIDDLE_NAME_PRIMARY_CONTACT)),
                            (FacilitySettings_Data.sMIDDLE_NAME_PRIMARY_CONTACT) + " entered for Middle Name");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.input_lastNameOfContact, 10);
                    m_assert.assertInfo(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_FacilityContactList.input_lastNameOfContact,
                                    (FacilitySettings_Data.sLAST_NAME_PRIMARY_CONTACT)),
                            (FacilitySettings_Data.sLAST_NAME_PRIMARY_CONTACT) + " entered for Last Name");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.input_companyNameInNewContactForm, 20);
                    m_assert.assertInfo(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_FacilityContactList.input_companyNameInNewContactForm,
                                    (FacilitySettings_Data.sCOMPANY_NAME)),
                            (FacilitySettings_Data.sCOMPANY_NAME) + " entered for Company Name");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.input_abbreviationInNewContactForm, 10);
                    m_assert.assertInfo(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_FacilityContactList.input_abbreviationInNewContactForm,
                                    (FacilitySettings_Data.sABBREVIATION)),
                            (FacilitySettings_Data.sABBREVIATION) + " entered for Abbreviation");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.input_displayNameInNewContactForm, 10);
                    m_assert.assertInfo(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_FacilityContactList.input_displayNameInNewContactForm,
                                    (FacilitySettings_Data.sDISPLAY_NAME)),
                            (FacilitySettings_Data.sDISPLAY_NAME) + " entered for Display name");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.input_emailOfContactInNewContactForm, 10);
                    m_assert.assertInfo(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_FacilityContactList.input_emailOfContactInNewContactForm,
                                    (FacilitySettings_Data.sEMAIL_CONTACT)),
                            (FacilitySettings_Data.sEMAIL_CONTACT) + " entered for Email ID");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.input_contactNumberInNewContactForm, 10);
                    m_assert.assertInfo(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_FacilityContactList.input_contactNumberInNewContactForm,
                                    (FacilitySettings_Data.sMOBILE_NUMBER_IN_CONTACT_FORM)),
                            (FacilitySettings_Data.sMOBILE_NUMBER_IN_CONTACT_FORM) + " entered for Contact number ");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.input_workContactNumberInNewContactForm, 10);
                    m_assert.assertInfo(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_FacilityContactList.input_workContactNumberInNewContactForm,
                                    (FacilitySettings_Data.sWORK_CONTACT_NUMBER)),
                            (FacilitySettings_Data.sWORK_CONTACT_NUMBER) + " entered for Contact number ");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.input_pincodeInNewContactForm, 10);
                    boolean bPinCodeSelected = false;
                    try {
                        m_assert.assertInfo(
                                Cls_Generic_Methods.sendKeysIntoElement(
                                        oPage_FacilityContactList.input_pincodeInNewContactForm,
                                        (FacilitySettings_Data.sPINCODE_IN_CONTACT_FORM)),
                                (FacilitySettings_Data.sPINCODE_IN_CONTACT_FORM) + " entered for Pin Code");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.input_pincodeInNewContactForm, 20);

                        for (WebElement pinCodeElement : oPage_FacilityContactList.inputOptions_pincodeOnNewContactForm) {
                            if (Cls_Generic_Methods.getTextInElement(pinCodeElement).equals(FacilitySettings_Data.sPINCODE_IN_CONTACT_FORM)) {
                                Cls_Generic_Methods.clickElement(pinCodeElement);
                                bPinCodeSelected = true;
                            }
                        }
                        m_assert.assertTrue(bPinCodeSelected,
                                String.valueOf(FacilitySettings_Data.sPINCODE_IN_CONTACT_FORM) + " entered for Pin Code");

                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("" + e);
                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.input_address1InNewContactForm, 10);
                    m_assert.assertInfo(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_FacilityContactList.input_address1InNewContactForm,
                                    (FacilitySettings_Data.sADDRESS_1_IN_CONTACT_FORM)),
                            (FacilitySettings_Data.sADDRESS_1_IN_CONTACT_FORM) + " entered for Address 1 ");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.input_address2InNewContactForm, 10);
                    m_assert.assertInfo(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_FacilityContactList.input_address2InNewContactForm,
                                    (FacilitySettings_Data.sADDRESS_2_IN_CONTACT_FORM)),
                            (FacilitySettings_Data.sADDRESS_2_IN_CONTACT_FORM) + " entered for Address 2 ");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.button_createContact, 10);
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_FacilityContactList.text_showInExpense)) {
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_FacilityContactList.checkbox_showInExpense);
                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.button_createContact, 10);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver,
                                    oPage_FacilityContactList.button_createContact),
                            "Clicked on create contact button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.select_groupOfContact, 10);
                    Select select = new Select(oPage_FacilityContactList.select_groupOfContact);
                    WebElement selectedOption = select.getFirstSelectedOption();
                    oPage_FacilityContactList = new Page_FacilityContactList(driver);
                    String selectedContactGroupText = Cls_Generic_Methods.getTextInElement(selectedOption);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.select_groupOfContact, 10);
                    if (!selectedContactGroupText.equalsIgnoreCase(FacilitySettings_Data.sCONTACT_GROUP)) {
                        m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                        oPage_FacilityContactList.select_groupOfContact, FacilitySettings_Data.sCONTACT_GROUP),
                                "Contact group is selected as - " + FacilitySettings_Data.sCONTACT_GROUP);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.input_searchFacilityContact, 10);
                    }
                    m_assert.assertInfo(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_FacilityContactList.input_searchFacilityContact, (FacilitySettings_Data.sDISPLAY_NAME)),
                            (FacilitySettings_Data.sDISPLAY_NAME) + " entered for search field ");
                    System.out.println(oPage_FacilityContactList.list_displayNameOfContactOnTable.size());
                    oPage_FacilityContactList = new Page_FacilityContactList(driver);
                    System.out.println(oPage_FacilityContactList.list_displayNameOfContactOnTable.size());
                    boolean displayNameMatched = false;
                    for (WebElement eValueOfDisplayNameOnTable : oPage_FacilityContactList.list_displayNameOfContactOnTable) {
                        if (eValueOfDisplayNameOnTable.isDisplayed()) {
                            String sTableValue = Cls_Generic_Methods.getTextInElement(eValueOfDisplayNameOnTable);
                            if (sTableValue.equals((FacilitySettings_Data.sDISPLAY_NAME))) {
                                System.out.println(sTableValue);
                                System.out.println(FacilitySettings_Data.sDISPLAY_NAME);
                                displayNameMatched = true;
                                break;
                            }
                            if (displayNameMatched) {
                                break;
                            }
                        }

                    }
                    m_assert.assertTrue(displayNameMatched, "Validate Contact name = <b>" + FacilitySettings_Data.sDISPLAY_NAME + "</b> added");
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

    @Test(enabled = true, description = "Validate Edit Contact Functionality")
    public void validateEditContactFunctionality() {
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_FacilityContactList oPage_FacilityContactList = new Page_FacilityContactList(driver);
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(1);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Facility Contact List");
                Cls_Generic_Methods.customWait(2);
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                    oPage_FacilityContactList.select_groupOfContact, sCONTACT_GROUP),
                            "Contact group is selected as - " + sCONTACT_GROUP);
                    try {
                        boolean bEditActionPerformed = false;
                        int requiredIndexOfRow = -1;
                        for (WebElement eValueOfDisplayNameOnTable : oPage_FacilityContactList.list_displayNameOfContactOnTable) {
                            oPage_FacilityContactList = new Page_FacilityContactList(driver);
                            if (eValueOfDisplayNameOnTable.isDisplayed()) {
                                String sTableValue = Cls_Generic_Methods.getTextInElement(eValueOfDisplayNameOnTable);
                                //   oPage_FacilityContactList = new Page_FacilityContactList(driver);
                                if (sTableValue.equals((FacilitySettings_Data.sDISPLAY_NAME))) {
                                    System.out.println(sTableValue);
                                    System.out.println(FacilitySettings_Data.sDISPLAY_NAME);
                                    requiredIndexOfRow = oPage_FacilityContactList.list_displayNameOfContactOnTable.indexOf(eValueOfDisplayNameOnTable);
                                    break;
                                }
                            }
                        }
                        Cls_Generic_Methods.scrollToElementByJS(oPage_FacilityContactList.list_editButtonsOnContactListTable.get(requiredIndexOfRow));
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_FacilityContactList.list_editButtonsOnContactListTable.get(requiredIndexOfRow));
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.input_displayNameInNewContactForm, 10);
                        Cls_Generic_Methods.clearValuesInElement(oPage_FacilityContactList.input_displayNameInNewContactForm);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.input_displayNameInNewContactForm, 10);
                        m_assert.assertInfo(
                                Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityContactList.input_displayNameInNewContactForm,
                                        "UPDATED " + FacilitySettings_Data.sDISPLAY_NAME), ("UPDATED " + FacilitySettings_Data.sDISPLAY_NAME) + " Display name updated ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.button_createContact, 10);
//                        Cls_Generic_Methods.clickElement(oPage_FacilityContactList.button_createContact);
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FacilityContactList.button_createContact),"Contact form edited");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.button_createContact, 10);

                        oPage_FacilityContactList = new Page_FacilityContactList(driver);
                        for (WebElement eValueOfDisplayNameOnTable : oPage_FacilityContactList.list_displayNameOfContactOnTable) {
                            if (eValueOfDisplayNameOnTable.isDisplayed()) {
                                String sTableValue = Cls_Generic_Methods.getTextInElement(eValueOfDisplayNameOnTable);
                                if (sTableValue.equals(("UPDATED " + FacilitySettings_Data.sDISPLAY_NAME))) {
                                    bEditActionPerformed = true;
                                    break;
                                }
                                if (bEditActionPerformed) {
                                    break;
                                }
                            }

                        }
                        m_assert.assertTrue(bEditActionPerformed, "Validate Edited contact form = <b>" +
                                "UPDATED " + FacilitySettings_Data.sDISPLAY_NAME + "</b>");
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertTrue(false, "Exception while editing contact form  " + e);
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

    @Test(enabled = true, description = "Validate Created Contact Display in Schedule Admission Form")
    public void validateCreatedContactInScheduleAdmission() {
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        boolean bPatientNameFound = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
//            Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            Page_FacilityContactList oPage_FacilityContactList = new Page_FacilityContactList(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OPD");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_addAppointment, 8);
            try {
                String tabToSelect = "All";
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect), "Validate " + tabToSelect + " tab is selected");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_addAppointment, 8);
                String patientName = null;
                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);


                m_assert.assertTrue(bPatientNameFound, "Validate Patient  " + concatPatientFullName + " is clicked in " + tabToSelect + "");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.button_scheduleAdmission, 10);
            Cls_Generic_Methods.clickElement(driver, oPage_FacilityContactList.button_scheduleAdmission);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.tab_insuranceDetailsTabInScheduleAdmission, 10);
            Cls_Generic_Methods.clickElement(driver, oPage_FacilityContactList.tab_insuranceDetailsTabInScheduleAdmission);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.radioButton_cashlessHospitalisation, 10);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_FacilityContactList.radioButton_cashlessHospitalisation);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.select_tpaContact, 10);
            try {

                boolean bContactPersonFound = false;
                int ContactPersonIndex = -1;
                Cls_Generic_Methods.clickElement(driver,
                        oPage_FacilityContactList.select_tpaContact);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.tab_insuranceDetailsTabInScheduleAdmission, 10);
                for (WebElement e : oPage_FacilityContactList.list_tpaContactFromDropdown) {
                    if (Cls_Generic_Methods.getTextInElement(e).equals(FacilitySettings_Data.sDISPLAY_NAME)) {
                        bContactPersonFound = true;
                        ContactPersonIndex = oPage_FacilityContactList.list_tpaContactFromDropdown.indexOf(e);
                        break;
                    }
                }

                if (bContactPersonFound) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver,
                                    oPage_FacilityContactList.list_tpaContactFromDropdown
                                            .get(ContactPersonIndex)),
                            FacilitySettings_Data.sDISPLAY_NAME1 + " is selected for contact");
                } else {
                    m_assert.assertWarn(false, FacilitySettings_Data.sDISPLAY_NAME1 + " is not found");
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertTrue(false, "Contact Selection failed." + e);
            }
            m_assert.assertTrue(
                    Cls_Generic_Methods.clickElement(driver, oPage_FacilityContactList.button_closeScheduleAdmissionModel),
                    "Schedule Admission Modal closed");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }


    }

   /* @Test(enabled = true, description = "Remove Patient Created For Validating Facility Contact List")
    public void removePatientForFacilityContactList() {
        Page_OPD oPage_OPD = new Page_OPD(driver);
//        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        boolean bPatientNameFound = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
//            Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Page_FacilityContactList oPage_FacilityContactList = new Page_FacilityContactList(driver);
            Page_OtSetup oPage_OtSetup = new Page_OtSetup(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OPD");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_modalPatientSearch, 8);
            try {
                String tabToSelect = "Not Arrived";
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect), "Validate " + tabToSelect + " tab is selected");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_modalPatientSearch, 8);
                String patientName = null;
                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                m_assert.assertTrue(bPatientNameFound, "Validate Patient  " + concatPatientFullName + " is clicked in " + tabToSelect + " ");

                if (bPatientNameFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_modalPatientSearch, 8);
                    Cls_Generic_Methods.scrollToElementByAction(driver, oPage_OtSetup.button_cancelAppointment);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtSetup.button_cancelAppointment, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver, oPage_OtSetup.button_cancelAppointment),
                            "Clicked on cancel button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.button_cancelAppointmentButtonInForm, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver, oPage_FacilityContactList.button_cancelAppointmentButtonInForm),
                            "Appointment cancelled");
                    Cls_Generic_Methods.customWait(1);
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }


    }*/

    @Test(enabled = true, description = "Remove Patient Created For Validating Facility Contact List")
    public void removePatientForFacilityContactList() {

        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_FacilityContactList oPage_FacilityContactList = new Page_FacilityContactList(driver);
        Page_OtSetup oPage_OtSetup = new Page_OtSetup(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;

        boolean bPatientNameFound = false;

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OPD");
            Cls_Generic_Methods.customWait(7);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage.get(0), 8);

            try {
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, OPD_Data.tab_ALL),
                        "Validate " + OPD_Data.tab_ALL + " tab is selected");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage.get(0), 8);

                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                m_assert.assertTrue(bPatientNameFound, "Validate Patient  " + concatPatientFullName + " is clicked in " + OPD_Data.tab_ALL);

                if (bPatientNameFound) {

                    if (Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.button_markPatientNotArrived)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_markPatientNotArrived),
                                "Clicked on Not Arrived for patient " + concatPatientFullName);
                        Cls_Generic_Methods.waitForPageLoad(driver, 8);
                        Cls_Generic_Methods.customWait();
                    }

                    Cls_Generic_Methods.scrollToElementByAction(driver, oPage_OtSetup.button_cancelAppointment);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtSetup.button_cancelAppointment, 8);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OtSetup.button_cancelAppointment), "Clicked on cancel button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.button_cancelAppointmentButtonInForm, 8);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FacilityContactList.button_cancelAppointmentButtonInForm), "Appointment cancelled");
                    Cls_Generic_Methods.customWait(8);
                } else {
                    m_assert.assertTrue(false, "Patient " + concatPatientFullName + " not found");
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while validating patient in Not arrived tab" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Exception while cancelling appointment" + e);
        }
    }

}
