package tests.settings.facilitySetting.clinical;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.OPD_Data;
import data.Settings_Data;
import data.settingsData.FacilitySettings_Data;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;
import pages.settings.facilitySettings.clinical.facilityLaboratorySet.Page_FacilityLabSet;
import pages.settings.facilitySettings.clinical.facilityMedicationSets.Page_FacilityMedicationSets;
import pages.settings.facilitySettings.general.facilityContactList.Page_FacilityContactList;
import pages.settings.facilitySettings.general.otSetup.Page_OtSetup;
import pages.settings.facilitySettings.general.wardSetup.Page_WardSetup;

import java.util.Arrays;
import java.util.List;

public class LaboratorySetTest extends TestBase {

    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();

    @Test(enabled = true, description = "Validate Patient for Financial setup")
    public void createPatientToValidateLaboratorySet() {
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_WardSetup oPage_WardSetUp = new Page_WardSetup(driver);
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);

        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                // Open the Search/Add patient dialog box
                try {
                    if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                        CommonActions.openPatientRegisterationAndAppointmentForm();
                    } else {
                        CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm, "Patient Details");
                        Cls_Generic_Methods.customWait();
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

               // m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_WardSetUp.tab_appointmentDetails, 20), "Wait until appointment details is displayed ");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 25), "Patient details displayed ");
                Cls_Generic_Methods.customWait(10);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validate add new lab set")
    public void validateAddNewLaboratorySet() {

        Page_FacilityLabSet oPage_FacilityLabSet = new Page_FacilityLabSet(driver);

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait(3);


            try {
                //get clinical laboratory settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Laboratory Sets");

                //click on LaboratorySet
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_FacilityLabSet.link_laboratorySet),
                        " Laboratory Set Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityLabSet.text_myPracticeLaboratoryList, 15);

                //fill data to laboratory list page
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityLabSet.input_setName, FacilitySettings_Data.sSet_Name),
                        " Set Name Entered as "+FacilitySettings_Data.sSet_Name);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FacilityLabSet.button_setTypeOpd),
                        " Set Type CLicked");

                try {
                    String str = oPage_FacilityLabSet.input_standardInvestigation.getAttribute("checked");
                    if (str.equalsIgnoreCase("true")) {

                        m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_FacilityLabSet.select_investigation, 1),
                                "Standard Investigation radio button is selected");
                    }
                    else {
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_FacilityLabSet.input_standardInvestigation);
                        m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_FacilityLabSet.select_investigation, 1),
                                "Standard Investigation radio button is selected");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }

                //Do save Laboratory set
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FacilityLabSet.button_saveLaboratoryList),
                        " Save Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityLabSet.link_laboratorySet, 15);


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validate search lab set and edit functionality")
    public void validateSearchAndEditLaboratorySet() {

        Page_FacilityLabSet oPage_FacilityLabSet = new Page_FacilityLabSet(driver);
        boolean bLabSetFound = false;

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {
                //get laboratory settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Laboratory Sets");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityLabSet.link_editLabSet, 10);
                //Validate the created laboratory set should display on the list

                for (WebElement setName : oPage_FacilityLabSet.inputOptions_laboratorySet) {
                    bLabSetFound=Cls_Generic_Methods.getTextInElement(setName).contains(FacilitySettings_Data.sSet_Name);
                    if (Cls_Generic_Methods.isElementDisplayed(setName)) {
                        if (bLabSetFound) {
                            Cls_Generic_Methods.scrollToElementByAction(driver, oPage_FacilityLabSet.link_editLabSet);
                            Cls_Generic_Methods.clickElement(driver, oPage_FacilityLabSet.link_editLabSet);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityLabSet.text_EditmyPracticeLaboratoryList, 10);
                            m_assert.assertTrue("Upon clicking on edit icon the edit laboratory set page is displayed");

                            //delete the existing value and update with new value .
                            Cls_Generic_Methods.clearValuesInElement(oPage_FacilityLabSet.input_setName);
                            Cls_Generic_Methods.customWait(1);

                            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityLabSet.input_setName, FacilitySettings_Data.sSet_EditName),
                                    " Set Name Entered as : "+FacilitySettings_Data.sSet_EditName);
                            /*Select investigation = new Select(oPage_FacilityLabSet.select_investigation);
                            investigation.selectByIndex(2);*/

                            Cls_Generic_Methods.selectElementByIndex(oPage_FacilityLabSet.select_investigation,2);

                            //Do save Laboratory set
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FacilityLabSet.button_saveLaboratoryList),
                                    " Save Button Clicked");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityLabSet.link_laboratorySet, 15);

                            break;
                        }

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

    @Test(enabled = true, description = "Validate lab set in template")
    public void validateLaboratorySetInTemplate() {
        // go under investigation tab, under laboratory set, created set should be visible

        Page_FacilityLabSet oPage_FacilityLabSet = new Page_FacilityLabSet(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        String AllTab = OPD_Data.tab_ALL;
        boolean bPatientNameFound = false;


        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();

            //go in opd
            CommonActions.selectDepartmentOnApp("OPD");
            m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, AllTab),
                    "Validate " + AllTab + " tab is selected");

            //select patient
            bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

            if (bPatientNameFound) {
                //Click on mark patient arrived button.
               // m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_markPatientArrived));
              //  m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityLabSet.button_templateDetails, 5));
                //open any template
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FacilityLabSet.button_templateDetails),
                        " Template Details Button CLicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityLabSet.link_eyeTemplate, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FacilityLabSet.link_eyeTemplate),
                        "Eye Link Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityLabSet.button_saveTemplate, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FacilityLabSet.tab_investigation),
                        "Investigation Tab Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityLabSet.strong_laboratoryTab, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FacilityLabSet.strong_laboratoryTab),
                        " Laboratory Tab Clicked");

                Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityLabSet.select_laboratorySet, FacilitySettings_Data.sSet_EditName);
                Cls_Generic_Methods.clickElement(oPage_FacilityLabSet.button_closeTemplate);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityLabSet.link_notArrived, 10);

                //click on NA button to make the patient as not arrived
                Cls_Generic_Methods.clickElement(oPage_FacilityLabSet.link_notArrived);
                Cls_Generic_Methods.customWait(3);

            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Remove created Patient to validate lab set")
    public void removePatientCreatedForValidateLaboratorySet() {

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
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage.get(0), 8);

            try {
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, OPD_Data.tab_ALL),
                        "Validate " + OPD_Data.tab_ALL + " tab is selected");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage.get(0), 8);

                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

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
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_cancelAppointment, 8);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FacilityContactList.button_cancelAppointmentButtonInForm), "Appointment cancelled");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
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

    @Test(enabled = true, description = "Deleted created Lab Set")
    public void deleteCreatedLaboratorySet() {

        Page_FacilityLabSet oPage_FacilityLabSet = new Page_FacilityLabSet(driver);
        boolean bLabSetFound = false;

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);


            try {
                //get laboratory settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(4);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Laboratory Sets");

                oPage_FacilityLabSet = new Page_FacilityLabSet(driver);

                //Validate the created laboratory set should display on the list
                for (WebElement setName : oPage_FacilityLabSet.inputOptions_laboratorySet) {
                    bLabSetFound=Cls_Generic_Methods.getTextInElement(setName).contains(FacilitySettings_Data.sSet_Name);
                    if (Cls_Generic_Methods.isElementDisplayed(setName)) {
                        if (bLabSetFound) {

                            Cls_Generic_Methods.scrollToElementByAction(driver, oPage_FacilityLabSet.link_deleteLabSet);
                            Cls_Generic_Methods.clickElement(driver, oPage_FacilityLabSet.link_deleteLabSet);
                            m_assert.assertTrue("Upon clicking on delete icon the confirm pop up is displayed");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityLabSet.button_confirmDelete, 10);
                            Cls_Generic_Methods.clickElement(driver, oPage_FacilityLabSet.button_confirmDelete);
                            Cls_Generic_Methods.customWait(3);

                            break;
                        }

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

}