package tests.settings.organisationSettings.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.bills.Page_Bills;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.commonElements.templates.optometrist.Page_OptometristTemplate;
import pages.commonElements.templates.optometrist.optometristTemplateSummary.Page_OptometristTemplateSummary;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_IDPrefix;


public class IDPrefixTest extends TestBase {

    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();
    String savePrefixMessage = "Your ID Prefix is successfully updated";
    String newIdPrefix = "RHS";
    String originalIdPrefix = null;
    String saveCinMessage = "Your Organisation CIN is successfully updated";
    String newCin = "PH123421313R";
    String originalCin = null;
    String patientName = null;
    boolean bPatientNameFound = false;


    @Test(enabled = true, description = "Verify ID Prefix Functionality In OPD")
    public void validateIDPrefixFunctionality() {

        Page_IDPrefix oPage_IDPrefix = new Page_IDPrefix(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_ScheduleAdmission oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_OptometristTemplate oPage_OptometristTemplate = new Page_OptometristTemplate(driver);
        Page_OptometristTemplateSummary oPage_OptometristTemplateSummary = new Page_OptometristTemplateSummary(driver);
        Page_Bills oPage_Bills = new Page_Bills(driver);


        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IDPrefix.input_idPrefixTextBox, 5);

                // Setting New ID Prefix Value

                originalIdPrefix = Cls_Generic_Methods.getElementAttribute(oPage_IDPrefix.input_idPrefixTextBox, "value");
                Cls_Generic_Methods.clearValuesInElement(oPage_IDPrefix.input_idPrefixTextBox);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_IDPrefix.input_idPrefixTextBox, newIdPrefix), "ID Prefix is entered as - " + newIdPrefix);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_IDPrefix.button_saveIdPrefix), "Save ID Prefix button is clicked");
                Cls_Generic_Methods.customWait(15);
                m_assert.assertEquals(Cls_Generic_Methods.getTextInElement(oPage_IDPrefix.alert_saveSuccessfullyMessage), savePrefixMessage, "Validating saved successfully message");

                //Navigating to OPD Module

                // m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_departmentFromDropdownSelector), " Department Dropdown is clicked");
                // m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Navbar.button_departmentFromDropdownSelector), "Validating Department Dropdown");
                m_assert.assertTrue(CommonActions.selectDepartmentOnApp("OPD"), "Selecting OPD From Department Dropdown");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_addAppointment, 2);

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
                if (!myPatient.getsSALUTATION().isEmpty()) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()), "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
                }

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()), "First Name is entered as - " + myPatient.getsFIRST_NAME());
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_middleNameOnPatientRegForm, myPatient.getsMIDDLE_NAME()), "Middle Name is entered as - " + myPatient.getsMIDDLE_NAME());
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_lastNameOnPatientRegForm, myPatient.getsLAST_NAME()), "Last Name is entered as - " + myPatient.getsLAST_NAME());
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, myPatient.getsMOBILE_NUMBER()), "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm), "Validate that Create Appointment button is clicked");
                Cls_Generic_Methods.customWait( 15);

                // marking patient as arrived
                //  m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_PatientAppointmentDetails.button_markPatientArrived), "Marked Patient arrived button is clicked");
                // Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_markPatientAsCompleted, 5);
               /* if (Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.text_selectToken)) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_PatientAppointmentDetails.button_selectToken), "Select Token  button is clicked");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_PatientAppointmentDetails.button_saveToken), "Save Token button is clicked");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_markPatientAsCompleted, 10);
*/
                // Selecting Newly created patient

                try {
                    String MyQueueTab = "My Queue";
                    String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                    concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                    m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab), "Validate " + MyQueueTab + " tab is selected");
                    Thread.sleep(1000);
                    bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                    m_assert.assertTrue(bPatientNameFound, "Validate Patient  " + concatPatientFullName + " is clicked in " + MyQueueTab + " of Optometrist");
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Exception while getting patient in OPD module " + e);
                }

                //Verify ID prefix in Schedule Admission section

                // m_assert.assertTrue((Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.text_patientIdValue).contains(newIdPrefix)), "Verifying PatientID Starting With New PrefixID In Appointment Details");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission), "Schedule Admission button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.button_caseDetails, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_caseDetails), "Case Details button is clicked In Schedule Admission");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ScheduleAdmission.input_caseDetailsId, "value").contains(newIdPrefix), "Verifying PatientID Starting With New PrefixID In Case Details Schedule Admission");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_closeTemplateWithoutSaving), "Closing Without Save");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_addCaseName, 2);
                m_assert.assertTrue((Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.button_addCaseName).contains(newIdPrefix)), "Verifying PatientID Starting With New PrefixID In  Case Details Section");

                // Verify ID prefix in Add New Case section

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_addCaseName), "Add New Case button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.input_caseDetailsId, 5);
                m_assert.assertTrue((Cls_Generic_Methods.getElementAttribute(oPage_ScheduleAdmission.input_caseDetailsId, "value").contains(newIdPrefix)), "Verifying PatientID Starting With New PrefixID In  Case Details Schedule Admission");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_closeTemplateWithoutSaving), "Closing Without Save");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_allCases, 2);

                //Verify ID prefix in All Cases section

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_allCases), "All Cases button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.text_caseDetailsOutsideBody, 10);
                m_assert.assertTrue((Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.text_caseDetailsOutsideBody).contains(newIdPrefix)), "Verifying PatientID Starting With New PrefixID In All Case Details outside body ");
                m_assert.assertTrue((Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.text_caseDetailsInsideBody).contains(newIdPrefix)), "Verifying PatientID Starting With New PrefixID In All Cases In Body");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_closeTemplateWithoutSaving), "Close button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_AddNewTemplate, 2);

                // Verify ID prefix in Template Section , First creating optometrist template then verify

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_AddNewTemplate), "New Template button is clicked");
                m_assert.assertTrue(CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_PatientAppointmentDetails.list_templateDetailsList, "Optometrist"), "Selecting Optometrist From Template List Dropdown");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OptometristTemplate.text_belowTemplateTitle, 10);
                m_assert.assertTrue((Cls_Generic_Methods.getTextInElement(oPage_OptometristTemplate.text_belowTemplateTitle).contains(newIdPrefix)), "Verifying PatientID Starting With New PrefixID In Optometrist Template ");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OptometristTemplate.checkbox_chiefComplaints), "Chief Complaints Checkbox is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OptometristTemplate.checkbox_opthalmicHistory), "Opthalmic History Checkbox is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OptometristTemplate.checkbox_systemicHistory), "Systemic History Checkbox  is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OptometristTemplate.checkbox_allAllergy), "All Allergy Checkbox is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OptometristTemplate.tab_Refraction), "Refraction Tab Button is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OptometristTemplate.checkbox_rightVaExamined), "Right VA Examined  Checkbox is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OptometristTemplate.checkbox_leftVaExamined), "Left VA Examined Checkbox is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OptometristTemplate.checkbox_rightIopNotExamined), "Right Iop Not Examined Checkbox is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OptometristTemplate.checkbox_leftIopNotExamined), "Left Iop Not Examined Checkbox is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OptometristTemplate.tab_Examination), "Examination Tab is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OptometristTemplate.button_RodNormal), "Rod Normal button is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OptometristTemplate.tab_LosNormal), "Los Normal button is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OptometristTemplate.dropdown_optometristDropdownBox), "Optometrist dropdown  is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OptometristTemplate.list_optometristListValueSelection), "Value Selected in Optometrist Dropdown");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_CommonElements.button_SaveTemplate), "Save Button in Optometrist is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OptometristTemplateSummary.text_opdSummaryAppointmentId, 10);
                m_assert.assertTrue((Cls_Generic_Methods.getTextInElement(oPage_OptometristTemplateSummary.text_opdSummaryAppointmentId).contains(newIdPrefix)), "Verifying PatientID Starting With New PrefixID In Optometrist Template ");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_CommonElements.button_CloseTemplate), "Close Button in Optometrist is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_consolidateReports, 2);

                // Verify ID prefix in Consolidate Reports section

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_consolidateReports), "Consolidate Reports Button is clicked");
                m_assert.assertTrue(CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_PatientAppointmentDetails.list_consolidateReportsList, "Ophthalmology"), "Ophthalmology reports selected from Reports");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OptometristTemplateSummary.text_opdSummaryAppointmentId, 10);
                m_assert.assertTrue((Cls_Generic_Methods.getTextInElement(oPage_OptometristTemplateSummary.text_opdSummaryAppointmentId).contains(newIdPrefix)), "Verifying PatientID Starting With New PrefixID In Ophthalmology Reports ");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close Button in Consolidate Reports is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_scheduleOT, 2);

                // Verify ID prefix in Schedule OT section

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_scheduleOT), "Schedule Ot Button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.button_caseDetails, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_caseDetails), "Case Details button is clicked In Schedule Ot");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ScheduleAdmission.input_caseDetailsId, "value").contains(newIdPrefix), "Verifying PatientID Starting With New PrefixID In Case Details Schedule Ot");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_closeTemplateWithoutSaving), "Closing Without Save");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_scheduleOT, 3);

                //Marking Patient as Complete

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientAsCompleted), "Marked Patient Completed button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 12);

                // Reset Id Prefix to Original Value
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IDPrefix.input_idPrefixTextBox, 5);

                // Setting New ID Prefix Value

                Cls_Generic_Methods.clearValuesInElement(oPage_IDPrefix.input_idPrefixTextBox);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_IDPrefix.input_idPrefixTextBox, originalIdPrefix), "ID Prefix is entered as - " + originalIdPrefix);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_IDPrefix.button_saveIdPrefix), "Save ID Prefix button is clicked");
                Cls_Generic_Methods.customWait(10);
                m_assert.assertEquals(Cls_Generic_Methods.getTextInElement(oPage_IDPrefix.alert_saveSuccessfullyMessage), savePrefixMessage, "Validating saved successfully message");


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }


    @Test(enabled = true, description = "Verify CIN Functionality In Organisation Setting")
    public void validateCinFunctionality() {

        Page_IDPrefix oPage_IDPrefix = new Page_IDPrefix(driver);


        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IDPrefix.input_idPrefixTextBox, 5);

                //Verifying Editing Cin Number Functionality

                originalCin = Cls_Generic_Methods.getElementAttribute(oPage_IDPrefix.input_cinTextBox, "value");
                Cls_Generic_Methods.clearValuesInElement(oPage_IDPrefix.input_cinTextBox);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_IDPrefix.input_cinTextBox, newCin), "CIN is entered as - " + newCin);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IDPrefix.button_saveCin), "Save CIN button is clicked");
                Cls_Generic_Methods.customWait();
                m_assert.assertEquals(Cls_Generic_Methods.getTextInElement(oPage_IDPrefix.alert_saveCinMessage), saveCinMessage, "Validating saved successfully message");
                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait(1);
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_IDPrefix.input_cinTextBox, "value").contains(newCin), "Verifying Updated Cin is displayed");

                // Reset Cin Number TO Initial Value
                Cls_Generic_Methods.clearValuesInElement(oPage_IDPrefix.input_cinTextBox);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_IDPrefix.input_cinTextBox, originalCin), "CIN is entered as - " + originalCin);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IDPrefix.button_saveCin), "Save CIN button is clicked");
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait(1);

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