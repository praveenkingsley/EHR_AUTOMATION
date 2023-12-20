package tests.settings.organisationSettings.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.templates.optometrist.Page_OptometristTemplate;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_IDPrefix;
import pages.settings.organisationSettings.general.Page_NabhAccreditation;

public class NabhAccreditationTest extends TestBase {

    static String myPatient = "FINAL TEST";
    EHR_Data oEHR_Data = new EHR_Data();


    @Test(enabled = true, description = "Verify Nabh Accreditation Functionality In Organisation Setting")
    public void validateNabhAccreditationFunctionality() {

        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_NabhAccreditation oPage_Nabhccreditation = new Page_NabhAccreditation(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_IDPrefix oPage_IDPrefix = new Page_IDPrefix(driver);
        boolean boldPatientFoundViaDate = false;

        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectFacility("OPTHA1");

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IDPrefix.input_idPrefixTextBox, 5);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "NABH/Accreditation");

                // Setting up Nabh Setting

                boolean yesButtonStatus = Cls_Generic_Methods.radioButtonIsSelected(oPage_Nabhccreditation.radio_buttonYes);
                if (!yesButtonStatus) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_Nabhccreditation.radio_buttonNo), " No button is clicked");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Nabhccreditation.list_selectOphthalpracticeLocation), "Ophthalpractice is clicked in location");
                }
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_Nabhccreditation.radio_buttonNo), " No button is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate),"Save Button CLicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_departmentFromDropdownSelector, 5);

                //Navigating to OPD Module

                CommonActions.selectDepartmentOnApp("OPD");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_addAppointment, 2);

                // Function to Verify and Select Previously Created  Patient

                boldPatientFoundViaDate = verifyAndSelectExistingPatientInOpd();
                m_assert.assertTrue(boldPatientFoundViaDate, "Patient is found and Selected");

                // Verify Edit Button Functionality for  Nabh setting as NO

                if (boldPatientFoundViaDate) {

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_openTemplate), " Optometrist Template is clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_enabledEditInOpdSummary, 5);
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_OPD.button_enabledEditInOpdSummary), "Verify Edit Template Button Enabled");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_closeTemplateWithoutSaving), "Optometrist Template closed");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 12);
                }
                //Changing Nabh Setting to Yes

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IDPrefix.input_idPrefixTextBox, 5);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "NABH/Accreditation");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_Nabhccreditation.radio_buttonYes), " Yes option is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Nabhccreditation.list_selectOphthalpracticeLocation), "Ophthalpractice is selected in location");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate),"Save Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_departmentFromDropdownSelector, 5);

                // Selecting OPD from Department dropdown

                CommonActions.selectDepartmentOnApp("OPD");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_addAppointment, 2);

                // Function to Verify and Select Previously Created  Patient
                boldPatientFoundViaDate = verifyAndSelectExistingPatientInOpd();
                m_assert.assertTrue(boldPatientFoundViaDate, "Patient is found and Selected");

                //Verifying Edit Button Functionality for Nabh Seeting as Yes

                if (boldPatientFoundViaDate) {

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_openTemplate), " Optometrist Template is clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_enabledEditInOpdSummary, 5);
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_OPD.button_disabledEditInOpdSummary), "Verify Edit Template Button Disabled");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_closeTemplateWithoutSaving), "Optometrist Template closed");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 12);
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


    // Function to Verify and Select Previously Created  Patient

    public boolean verifyAndSelectExistingPatientInOpd() throws Exception {

        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);

        boolean bPatientNameFound = false;
        // Selecting previous created patient

        try {
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_appointmentDatePicker), "Appointment Date picker is clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.text_monthInCalendar, 10);
            String month = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.text_monthInCalendar);
            String year = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.text_yearInCalendar);
            while (!(month.contains("January") && year.contains("2022"))) {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_previousDateInCalender),"Previous Date Clicked");
                month = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.text_monthInCalendar);
                year = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.text_yearInCalendar);
            }
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_dateInCalendar),"Date In Calendar CLicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 12);

            // verifying and selecting existing patient

            try {
                String MyQueueTab = "INCOMPLETED";
                String concatPatientFullName = myPatient;
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab), "Validate " + MyQueueTab + " tab is selected");
                Thread.sleep(1000);
                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient in Nabh module " + e);
            }
        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }

        return bPatientNameFound;
    }


}