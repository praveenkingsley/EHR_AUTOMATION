package tests.departments.ot;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.IPD_Data;
import data.OT_Data;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.ipd.Page_IPD;
import pages.ipd.forms.intraOperative.*;
import pages.ipd.forms.postOperative.Page_AldreteScoreTemplate;
import pages.ipd.forms.postOperative.Page_DischargeForm;
import pages.ipd.forms.postOperative.Page_PainAssessment;
import pages.ipd.forms.wardNote.Page_wardNoteForm;
import pages.ot.Page_OT;
import pages.ot.forms.Page_IntraPostOperativeTemplateVisiblity;
import pages.ot.forms.consent.Page_AdmissionInConsent;
import pages.ot.forms.consent.Page_CustomConsents;
import pages.ot.forms.consent.Page_Operative;
import pages.ot.forms.consent.Page_UploadPapers;
import pages.ot.forms.intraOperative.*;
import pages.ot.forms.postOperative.Page_AldreteScoreTemplateUpdate;
import pages.ot.forms.postOperative.Page_DischargeNoteUpdate;
import pages.ot.forms.postOperative.Page_PainAssessmentTemplateUpdate;
import pages.ot.forms.postOperative.Page_ViewAllWardNotes;
import pages.ot.forms.preOperative.Page_AdmissionInPreOperative;
import pages.ot.forms.preOperative.Page_Assessment;
import pages.ot.forms.preOperative.Page_CarePlan;
import pages.ot.forms.preOperative.Page_WardChecklist;
import pages.ot.tabs.Page_OtTabs;

public class OTFormsTest extends TestBase {

    //    Page_Navbar oPage_Navbar;
//    Page_Dashboard oPage_Dashboard;
//    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
//    Page_OT oPage_OT;
//    Page_CommonElements oPage_CommonElements;
//    Page_AdmissionInConsent oPage_AdmissionInConsent;
//    Page_Operative oPage_Operative;
//    Page_UploadPapers oPage_UploadPapers;
//    Page_CustomConsents oPage_CustomConsents;
//    Page_AdmissionInPreOperative oPage_AdmissionInPreOperative;
//    Page_Assessment oPage_Assessment;
//    Page_CarePlan oPage_CarePlan;
//    Page_WardChecklist oPage_WardChecklist;
    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();
    IPD_Data oIPD_Data = new IPD_Data();

    @Test(enabled = true, description = "Validate Consent Forms for Patient in OT")
    public void fillConsentForms() {

        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_OT oPage_OT = new Page_OT(driver);
        Page_AdmissionInConsent oPage_AdmissionInConsent = new Page_AdmissionInConsent(driver);
        Page_Operative oPage_Operative = new Page_Operative(driver);
        Page_UploadPapers oPage_UploadPapers = new Page_UploadPapers(driver);
        Page_CustomConsents oPage_CustomConsents = new Page_CustomConsents(driver);

        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        boolean bValidatePatientFound = false;
        String expectedLoggedInUser = oEHR_Data.user_HGNurse2;
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();

        try {

            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OT");

            oPage_OT = new Page_OT(driver);
            String tabToSelect = OT_Data.tab_ALL;
            CommonActions.selectTabOnDepartmentPage(oPage_OT.tabs_TabsOnIPD, tabToSelect);

            bValidatePatientFound = CommonActions.selectPatientNameInOT(oPage_OT.rows_patientNamesOnOT, concatPatientFullName);

            m_assert.assertTrue(bValidatePatientFound, "Validate patient " + concatPatientFullName + " is found");

            // Validate that consent forms section is visible
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.text_consentsSection, 16);

            if (bValidatePatientFound) {
                m_assert.assertTrue(true, "Validate the Consent forms section is displayed");

                Cls_Generic_Methods.scrollToElementByJS(oPage_OT.text_consentsSection);

                // Admission
                try {
                    m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_AdmissionInConsent.button_admissionInConsent),
                            "<b>Validate Admission button is visible under the Consents forms</b>");

                    int preWindowsCount = driver.getWindowHandles().size();
                    String initialWindowHandle = driver.getWindowHandle();

                    Cls_Generic_Methods.clickElement(oPage_AdmissionInConsent.button_admissionInConsent);
                    Cls_Generic_Methods.customWait(8);
                    int postWindowsCount = driver.getWindowHandles().size();

                    m_assert.assertTrue(postWindowsCount > preWindowsCount, "Validate New tab is opened when clicking on Admission button");
                    // Closing the Second Window
                    for (String currentWindowHandle : driver.getWindowHandles()) {
                        if (!currentWindowHandle.equals(driver.getWindowHandle())) {
                            driver.switchTo().window(currentWindowHandle);
                        }
                    }
                    driver.close();
                    driver.switchTo().window(initialWindowHandle);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.text_consentsSection, 16);

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }

                // Operative
                try {
                    m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_Operative.button_operativeInConsent),
                            "<b>Validate Operative button is visible under the Consents forms</b>");

                    Cls_Generic_Methods.clickElement(oPage_Operative.button_operativeInConsent);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Operative.header_ConsentWindowName, 20);

                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Operative.header_ConsentWindowName)) {
                        m_assert.assertInfo(true,
                                "Validate Operative consent window is visible");

                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Operative.selectButton_templateForConsent),
                                "Validate Select option for Operative Template is visible");

//						Cls_Generic_Methods.clickElement(oPage_OT.selectButton_templateForConsent);
                        int optionsCount = oPage_Operative.selectOptions_templateForConsent.size();

                        m_assert.assertTrue(optionsCount > 0,
                                "Validate " + optionsCount + " options are present under Operative Template");


                        int preWindowsCount = driver.getWindowHandles().size();
                        String initialWindowHandle = driver.getWindowHandle();

                        Cls_Generic_Methods.clickElement(oPage_Operative.button_printTemplate);
                        Cls_Generic_Methods.customWait(8);
                        int postWindowsCount = driver.getWindowHandles().size();

                        m_assert.assertTrue(postWindowsCount > preWindowsCount, "Validate New tab is opened with Consent form when clicking on Print button");
                        // Closing the Second Window
                        for (String currentWindowHandle : driver.getWindowHandles()) {
                            if (!currentWindowHandle.equals(driver.getWindowHandle())) {
                                driver.switchTo().window(currentWindowHandle);
                            }
                        }
                        driver.close();
                        driver.switchTo().window(initialWindowHandle);
                        Cls_Generic_Methods.clickElement(oPage_Operative.button_closeTemplate);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.text_consentsSection, 16);

                    } else {
                        m_assert.assertTrue(false,
                                "Validate Operative consent window is visible");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }

                // Upload Papers
                try {
                    m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_UploadPapers.button_uploadPapersInConsent),
                            "<b>Validate Upload Papers button is visible under the Consents forms</b>");

                    int preWindowsCount = driver.getWindowHandles().size();
                    String initialWindowHandle = driver.getWindowHandle();

                    Cls_Generic_Methods.clickElement(oPage_UploadPapers.button_uploadPapersInConsent);
                    Cls_Generic_Methods.customWait(4);

                    for (String currentWindowHandle : driver.getWindowHandles()) {
                        if (!currentWindowHandle.equals(driver.getWindowHandle())) {
                            driver.switchTo().window(currentWindowHandle);
                        }
                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_UploadPapers.button_uploadFilesInUploadPapers_InConsent, 16);

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_UploadPapers.button_AddFilesInUploadPapers_InConsent),
                            "Validate Add Files button is displayed on New Tab");

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_UploadPapers.button_uploadFilesInUploadPapers_InConsent),
                            "Validate Upload Files button is displayed on New Tab");

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_UploadPapers.section_dragAndDropInUploadPapers_InConsent),
                            "Validate Drag & Drop section is displayed on New Tab");

                    int postWindowsCount = driver.getWindowHandles().size();

                    m_assert.assertTrue(postWindowsCount > preWindowsCount,
                            "Validate Upload document tab is opened when clicking on Upload Documents button");

                    // Closing the Second Window

                    driver.close();
                    driver.switchTo().window(initialWindowHandle);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.text_consentsSection, 16);

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validate Pre Operative Forms for Patient in OT")
    public void fillPreOperativeForms() {


        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_OT oPage_OT = new Page_OT(driver);
        Page_AdmissionInPreOperative oPage_AdmissionInPreOperative = new Page_AdmissionInPreOperative(driver);
        Page_Assessment oPage_Assessment = new Page_Assessment(driver);
        Page_CarePlan oPage_CarePlan = new Page_CarePlan(driver);
        Page_WardChecklist oPage_WardChecklist = new Page_WardChecklist(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        boolean bValidatePatientFound = false;

        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();

        try {

            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OT");

            oPage_OT = new Page_OT(driver);
            String tabToSelect = OT_Data.tab_ALL;
            CommonActions.selectTabOnDepartmentPage(oPage_OT.tabs_TabsOnIPD, tabToSelect);
            Cls_Generic_Methods.customWait();

            bValidatePatientFound = CommonActions.selectPatientNameInIpd(oPage_OT.rows_patientNamesOnOT, concatPatientFullName);

            m_assert.assertTrue(bValidatePatientFound, "Validate patient " + concatPatientFullName + " is found");

            // Validate that Pre Operative forms section is visible
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.text_preOperativeSection, 16);

            if (bValidatePatientFound) {
                m_assert.assertTrue(true, "Validate the Pre-Operative forms section is displayed");

                Cls_Generic_Methods.scrollToElementByJS(oPage_OT.text_preOperativeSection);

                // Admission
                try {
                    String inputText = "Demo Reason For Admission Comment";
                    m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_AdmissionInPreOperative.button_admissionInPreOperative),
                            "<b>Validate Admission button is visible under the Pre-Operative forms</b>");

                    Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.button_admissionInPreOperative);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AdmissionInPreOperative.tab_AdminTabOnAdmissionUnderPreOperative, 16);


                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.tab_AdminTabOnAdmissionUnderPreOperative),
                            "Validate Admin Tab is clicked");
                    Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.input_reasonForAdmissionUnderAdminTab);
                    Cls_Generic_Methods.clearValuesInElement(oPage_AdmissionInPreOperative.input_reasonForAdmissionUnderAdminTab);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_AdmissionInPreOperative.input_reasonForAdmissionUnderAdminTab, inputText);

                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.tab_CaseClinicalTabOnAdmissionUnderPreOperative),
                            "Validate Case Clinical Tab is clicked");

                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.tab_HistoryTabOnAdmissionUnderPreOperative),
                            "Validate History Tab is clicked");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.button_saveOnModalHeader),
                            "Validate Save button is clicked on Admission under Pre-Operative");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);

                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.text_preOperativeSection, 16);

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }

                // Assessment
                try {

                    m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_Assessment.button_assessmentInPreOperative),
                            "<b>Validate Assessment button is visible under the Pre-Operative forms</b>");

                    Cls_Generic_Methods.clickElement(oPage_Assessment.button_assessmentInPreOperative);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);

                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Assessment.tab_primaryAssessmentOnAssessmentFormInPreOperativeForm),
                            "Validate Primary Assessment Tab is clicked");
                    Cls_Generic_Methods.customWait(1);

                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Assessment.tab_secondaryAssessmentOnAssessmentFormInPreOperativeForm),
                            "Validate Secondary Assessment Tab is clicked");
                    Cls_Generic_Methods.customWait(1);

                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Assessment.tab_vitalSignsOnAssessmentFormInPreOperativeForm),
                            "Validate Vital Signs Tab is clicked");
                    Cls_Generic_Methods.customWait(1);


                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Assessment.button_saveOnModalHeader),
                            "Validate Save button is clicked on Assessment under Pre-Operative");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);

                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.text_preOperativeSection, 16);

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }

                // Care Plan
                try {
                    m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_CarePlan.button_carePlanInPreOperative),
                            "<b>Validate Care Plan button is visible under the Pre-Operative forms</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CarePlan.button_carePlanInPreOperative),
                            "Validate Care Plan button is clicked");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CarePlan.button_saveOnModalHeader, 16);

                    String medicationSetName = "testenv";
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_CarePlan.select_medicationSetInCarePlan, medicationSetName),
                            "Validate " + medicationSetName + " option is selected under Medical sets for Nurse Care Plan");
                    Cls_Generic_Methods.customWait();

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CarePlan.button_saveOnModalHeader),
                            "Validate Save button is clicked on Assessment under Pre-Operative");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);

                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.text_preOperativeSection, 16);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                // Ward Checklist
                try {
                    m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_WardChecklist.button_wardChecklistInPreOperative),
                            "<b>Validate Assessment button is visible under the Pre-Operative forms</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WardChecklist.button_wardChecklistInPreOperative),
                            "Validate Assessment button is clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_saveOnModalHeader, 16);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OT.button_saveOnModalHeader),
                            "Validate Save button is clicked on Assessment under Pre-Operative");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);
                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.text_preOperativeSection, 16);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        } finally {

        }
    }

    @Test(enabled = true, description = "Validate Update in Pre Operative Forms for Patient in OT")
    public void updatePreOperativeForms() {

        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_OT oPage_OT = new Page_OT(driver);
        Page_AdmissionInPreOperative oPage_AdmissionInPreOperative = new Page_AdmissionInPreOperative(driver);
        Page_Assessment oPage_Assessment = new Page_Assessment(driver);
        Page_CarePlan oPage_CarePlan = new Page_CarePlan(driver);
        Page_WardChecklist oPage_WardChecklist = new Page_WardChecklist(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        boolean bValidatePatientFound = false;

        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();

        try {

            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OT");

            oPage_OT = new Page_OT(driver);
            String tabToSelect = OT_Data.tab_ALL;
            CommonActions.selectTabOnDepartmentPage(oPage_OT.tabs_TabsOnIPD, tabToSelect);

            bValidatePatientFound = CommonActions.selectPatientNameInIpd(oPage_OT.rows_patientNamesOnOT, concatPatientFullName);

            m_assert.assertTrue(bValidatePatientFound, "Validate patient " + concatPatientFullName + " is found");

            // Validate that Pre Operative forms section is visible
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.text_preOperativeSection, 16);

            if (bValidatePatientFound) {
                m_assert.assertTrue(true, "Validate the Pre-Operative forms section is displayed");

                Cls_Generic_Methods.scrollToElementByJS(oPage_OT.text_preOperativeSection);

                // Admission
                try {
                    String inputText = "Updated Demo Reason For Admission Comment";

                    if (Cls_Generic_Methods.isElementDisplayed(oPage_AdmissionInPreOperative.button_docIconInAdmissionInPreOperative)) {
                        m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_AdmissionInPreOperative.button_docIconInAdmissionInPreOperative),
                                "<b>Validate Admission button is visible under the Pre-Operative forms</b>");

                        Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.button_admissionInPreOperative);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_editOnModal, 16);

                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_editOnModal),
                                "Validate Edit button is clicked");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AdmissionInPreOperative.tab_AdminTabOnAdmissionUnderPreOperative, 16);

                        Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.input_reasonForAdmissionUnderAdminTab);
                        Cls_Generic_Methods.clearValuesInElement(oPage_AdmissionInPreOperative.input_reasonForAdmissionUnderAdminTab);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_AdmissionInPreOperative.input_reasonForAdmissionUnderAdminTab, inputText);

                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.tab_CaseClinicalTabOnAdmissionUnderPreOperative),
                                "Validate Case Clinical Tab is clicked");

                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.tab_HistoryTabOnAdmissionUnderPreOperative),
                                "Validate History Tab is clicked");

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.button_updateOnModalHeader),
                                "Validate Update button is clicked on Admission under Pre-Operative");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);

                        Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.text_preOperativeSection, 16);
                    } else {
                        m_assert.assertTrue(false, "The Admission form is not already filled. Please fill the form and then run Update Checks");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }

                // Assessment
                try {

                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Assessment.button_docIconInAssessmentInPreOperative)) {

                        String inputPrimaryComment = "updated Primary Comment";
                        String inputSecondaryComment = "updated Secondary Comment";
                        String inputVitalSignsComment = "updated Vital Signs Comment";

                        m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_Assessment.button_assessmentInPreOperative),
                                "<b>Validate Assessment button is visible under the Pre-Operative forms</b>");

                        Cls_Generic_Methods.clickElement(oPage_Assessment.button_assessmentInPreOperative);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);

                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_editOnModal),
                                "Validate Edit button is clicked");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);


                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Assessment.tab_primaryAssessmentOnAssessmentFormInPreOperativeForm),
                                "Validate Primary Assessment Tab is clicked");
                        Cls_Generic_Methods.customWait(1);
                        Cls_Generic_Methods.clickElement(oPage_Assessment.input_commentOnPrimaryAssessment);
                        Cls_Generic_Methods.clearValuesInElement(oPage_Assessment.input_commentOnPrimaryAssessment);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Assessment.input_commentOnPrimaryAssessment, inputPrimaryComment);


                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Assessment.tab_secondaryAssessmentOnAssessmentFormInPreOperativeForm),
                                "Validate Secondary Assessment Tab is clicked");
                        Cls_Generic_Methods.customWait(1);
                        Cls_Generic_Methods.clickElement(oPage_Assessment.input_commentOnSecondaryAssessment);
                        Cls_Generic_Methods.clearValuesInElement(oPage_Assessment.input_commentOnSecondaryAssessment);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Assessment.input_commentOnSecondaryAssessment, inputSecondaryComment);

                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Assessment.tab_vitalSignsOnAssessmentFormInPreOperativeForm),
                                "Validate Vital Signs Tab is clicked");
                        Cls_Generic_Methods.customWait(1);
                        Cls_Generic_Methods.clickElement(oPage_Assessment.input_commentOnVitalSigns);
                        Cls_Generic_Methods.clearValuesInElement(oPage_Assessment.input_commentOnVitalSigns);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Assessment.input_commentOnVitalSigns, inputVitalSignsComment);

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Assessment.button_updateOnModalHeader),
                                "Validate Save button is clicked on Assessment under Pre-Operative");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);

                        Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.text_preOperativeSection, 16);
                    } else {
                        m_assert.assertTrue(false, "The Assessment form is not already filled. Please fill the form and then run Update Checks");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }

                // Care Plan
                try {
                    m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_CarePlan.button_carePlanInPreOperative),
                            "<b>Validate Care Plan button is visible under the Pre-Operative forms</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CarePlan.button_carePlanInPreOperative),
                            "Validate Care Plan button is clicked");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CarePlan.button_saveOnModalHeader, 16);

                    String medicationSetName = "testenv";
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_CarePlan.select_medicationSetInCarePlan, medicationSetName),
                            "Validate " + medicationSetName + " option is selected under Medical sets for Nurse Care Plan");
                    Cls_Generic_Methods.customWait();

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CarePlan.button_saveOnModalHeader),
                            "Validate Save button is clicked on Assessment under Pre-Operative");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);

                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.text_preOperativeSection, 16);

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }

                // Ward Checklist
                try {
                    m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_WardChecklist.button_wardChecklistInPreOperative),
                            "<b>Validate Assessment button is visible under the Pre-Operative forms</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WardChecklist.button_wardChecklistInPreOperative),
                            "Validate Assessment button is clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_saveOnModalHeader, 16);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OT.button_saveOnModalHeader),
                            "Validate Save button is clicked on Assessment under Pre-Operative");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);
                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.text_preOperativeSection, 16);

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Fill OT templates ")
    public void fillIntraOperativeTemplateFormInOt() {

        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        Page_PreAnaesthesiaTemplate oPage_PreAnaesthesiaTemplate = new Page_PreAnaesthesiaTemplate(driver);
        Page_OtChecklist oPage_OtChecklist = new Page_OtChecklist(driver);
        Page_BillOfMaterial oPage_BillOfMaterial = new Page_BillOfMaterial(driver);
        Page_OperativeForm oPage_OperativeForm = new Page_OperativeForm(driver);
        Page_SedationChart oPage_SedationChart = new Page_SedationChart(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_IPD oPage_IPD = new Page_IPD(driver);
        Page_OtTabs oPage_OtTabs = new Page_OtTabs(driver);
        Page_IntraPostOperativeTemplateVisiblity oPage_IntaOperative = new Page_IntraPostOperativeTemplateVisiblity(driver);
        IPD_Data oIPD_Data = new IPD_Data();
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        boolean bValidatePatientFound = false;
        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OT");

            oPage_IPD = new Page_IPD(driver);
            oPage_OtTabs = new Page_OtTabs(driver);
            String tabToSelect = OT_Data.tab_ALL;

            CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, tabToSelect);
            bValidatePatientFound = CommonActions.selectPatientNameInOT(oPage_OtTabs.rows_patientNamesOnOt, concatPatientFullName);
            m_assert.assertInfo(bValidatePatientFound, "Validate patient found");

            if (bValidatePatientFound) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_IntaOperative.text_intraOperativeSectionInOt)) {
                    try {

                        // fill Pre Anesthesia checklist
                        try {
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_PreAnaesthesiaTemplate.button_PreAnaesthesiaChecklistTemplateInIPD, 20);
                            m_assert.assertInfo(
                                    Cls_Generic_Methods.clickElementByAction(driver,
                                            oPage_PreAnaesthesiaTemplate.button_PreAnaesthesiaChecklistTemplateInIPD),
                                    "clicked on Pre Anesthesia button");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAnaesthesiaTemplate.input_surgeryName,
                                    20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(oPage_PreAnaesthesiaTemplate.input_surgeryName,
                                            oIPD_Data.sSURGERY_NAME),
                                    "Surgery name entered as " + oIPD_Data.sSURGERY_NAME);

                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(oPage_PreAnaesthesiaTemplate.input_surgeonName,
                                            oIPD_Data.sSURGEON_NAME),
                                    "Surgeon name entered as " + oIPD_Data.sSURGEON_NAME);

                            try {
                                for (WebElement buttonElement : oPage_PreAnaesthesiaTemplate.listButtons_selectAnesthesiaPlanned) {

                                    int index = oPage_PreAnaesthesiaTemplate.listButtons_selectAnesthesiaPlanned
                                            .indexOf(buttonElement);
                                    boolean validateButtonFunctionality = false;
                                    validateButtonFunctionality = CommonActions
                                            .validateIf_EHR_ButtonIsClickable(buttonElement);
                                    m_assert.assertInfo(validateButtonFunctionality,
                                            "Validate Planned Anesthesia"
                                                    + oIPD_Data.List_ANESTHESIA_PLANNED.get(index)
                                                    + " button is Clickable");

                                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, buttonElement),
                                            "Validate Planned Anesthesia"
                                                    + oIPD_Data.List_ANESTHESIA_PLANNED.get(index)
                                                    + "  button is Clicked");
                                    break;

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                m_assert.assertFatal("Exception while validating Anesthesia planned buttons" + e);
                            }

                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_PreAnaesthesiaTemplate.input_extraInformationToDoctor,
                                            oIPD_Data.sEXTRA_INFORMATION_TO_DOCTOR),
                                    "Extra Information to doctor entered as "
                                            + oIPD_Data.sEXTRA_INFORMATION_TO_DOCTOR);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_PreAnaesthesiaTemplate.input_medicationComment,
                                            oIPD_Data.sMEDICATION),
                                    " Medication field in Pre Anesthesia template filled as "
                                            + oIPD_Data.sMEDICATION);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_PreAnaesthesiaTemplate.button_savePreAnaesthesiaTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_PreAnaesthesiaTemplate.button_savePreAnaesthesiaTemplate),
                                    "Clicked on save Button to save Pre Anesthesia Template ");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate),
                                    "Clicked on close Button to close Pre Anesthesia Template ");
                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("Exception while validating Anesthesia " + e);
                        }

                        // fill OT checklist
                        try {
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_OtChecklist.button_oTChecklistTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_OtChecklist.button_oTChecklistTemplate),
                                    "Clicked on OT checklist Button");
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_OtChecklist.button_newCheckListTemplate),
                                    "Clicked on New checklist to view OT Checklist template ");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_OtChecklist.checkbox_correctPatientInOtCheckListTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_OtChecklist.checkbox_correctPatientInOtCheckListTemplate),
                                    "Clicked on correct patient checkbox ");
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_OtChecklist.checkbox_identityInOtCheckListTemplate),
                                    "Clicked on identify checkbox under correct patient");

                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_OtChecklist.input_commentsInOtChecklistTemplate,
                                            oIPD_Data.sCOMMENT_BOX_IN_OT_CHECKLIST),
                                    "Comment box in OT Checklist Template filled as "
                                            + oIPD_Data.sCOMMENT_BOX_IN_OT_CHECKLIST);
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_OtChecklist.button_saveOtChecklistTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_OtChecklist.button_saveOtChecklistTemplate),
                                    "OT checklist template saved ");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate),
                                    "OT checklist template closed");
                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("Exception while filling OT Checklist Template " + e);
                        }

                        // fill sedation template
                        try {
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SedationChart.button_sedationChart,
                                    20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver, oPage_SedationChart.button_sedationChart),
                                    "Clicked on sedation chart template ");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_SedationChart.input_respirationRateUnderSedationChart, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationChart.input_respirationRateUnderSedationChart,
                                            oIPD_Data.sRESPIRATION_RATE),
                                    "Respiration rate captured as "
                                            + oIPD_Data.sRESPIRATION_RATE);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationChart.input_bloodPressureUnderSedationChart,
                                            oIPD_Data.sBP),
                                    "Blood pressure captured as " + oIPD_Data.sBP);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationChart.input_pulseUnderSedationChart,
                                            oIPD_Data.sPULSE),
                                    "Pulse rate captured as " + oIPD_Data.sPULSE);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationChart.input_sedationScoreUnderSedationChart,
                                            oIPD_Data.sSEDATION_SCORE),
                                    "sedation score captured as "
                                            + oIPD_Data.sSEDATION_SCORE);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationChart.input_medicationUnderSedationChart,
                                            oIPD_Data.sMEDICATION),
                                    "sedation score captured as " + oIPD_Data.sMEDICATION);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationChart.input_bolusmlUnderSedationChart,
                                            oIPD_Data.sBOLUS_ML),
                                    "Bolus ml value captured as " + oIPD_Data.sBOLUS_ML);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationChart.input_bolusmgUnderSedationChart,
                                            oIPD_Data.sBOLUS_MG),
                                    "Bolus Mg value captured as " + oIPD_Data.sBOLUS_MG);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationChart.input_o2SaturationUnderSedationChart,
                                            oIPD_Data.sO2_SATURATION),
                                    "O2 saturation field in sedation chart filled as "
                                            + oIPD_Data.sO2_SATURATION);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationChart.input_commentsUnderSedationChart,
                                            oIPD_Data.sCOMMENTS_SEDATION_CHART),
                                    "Comments field in sedation chart filled as "
                                            + oIPD_Data.sCOMMENTS_SEDATION_CHART);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationChart.input_nameUnderSedationChart,
                                            oIPD_Data.sNAME_SEDATION_CHART),
                                    "Name field in sedation chart filled as "
                                            + oIPD_Data.sNAME_SEDATION_CHART);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_SedationChart.button_saveSedationChartTemplate),
                                    "sedation chart template saved ");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate),
                                    "sedation chart template closed ");

                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("Exception while filling sedation chart Template " + e);
                        }

                        // bill of material
                        try {
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_BillOfMaterial.button_billOfMaterialTemplate, 4);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_BillOfMaterial.button_billOfMaterialTemplate),
                                    "Clicked on bill of material template ");
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_BillOfMaterial.button_addBillOfMaterial, 4);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_BillOfMaterial.button_addBillOfMaterial),
                                    "Clicked on + bill of material to fill the template ");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_BillOfMaterial.button_saveBom, 8);
                            try {
                                for (WebElement buttonElement : oPage_BillOfMaterial.tr_itemInBom) {

                                    //  int index = oPage_BillOfMaterial.tr_itemInBom.indexOf(buttonElement);
                                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, buttonElement),
                                            "selected a item from the list for BOM");
                                    break;

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                m_assert.assertFatal("Exception while selecting item in Bill of material" + e);
                            }

                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_BillOfMaterial.input_billableUnitPrice, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_BillOfMaterial.input_billableUnitPrice,
                                            oIPD_Data.sBILLABLE_UNIT_PRICE),
                                    "Billable unit price entered as "
                                            + oIPD_Data.sBILLABLE_UNIT_PRICE);
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_BillOfMaterial.input_consumedQuantityInBom, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_BillOfMaterial.input_consumedQuantityInBom,
                                            oIPD_Data.sCONSUMED_QUANTITY),
                                    "Consumed quantity entered as "
                                            + oIPD_Data.sCONSUMED_QUANTITY);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver, oPage_BillOfMaterial.button_saveBom),
                                    " BOM saved");

                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("Exception while filling bill of material chart Template " + e);
                        }

                        // fill operative form
                        try {
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OperativeForm.button_operativeNote,
                                    20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_operativeNote),
                                    " clicked on operative template button");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OperativeForm.button_newOperativeNote,
                                    20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_OperativeForm.button_newOperativeNote),
                                    " clicked on new note to view operative template ");
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_OperativeForm.input_personalCommentBox, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_OperativeForm.input_personalCommentBox,
                                            oIPD_Data.sOPERATIVE_NOTE_PERSONNEL_COMMENT),
                                    "Personnel comment inside operative note entered as "
                                            + oIPD_Data.sOPERATIVE_NOTE_PERSONNEL_COMMENT);

                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElementByJS(driver,
                                            oPage_OperativeForm.radioButtons_surgeon1InOperativeForm),
                                    " clicked on surgeon 1 radio button ");

                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_next),
                                    " clicked on next button");
                            if (Cls_Generic_Methods
                                    .isElementDisplayed(oPage_OperativeForm.button_bomItemsInOperativeForm)) {
                                m_assert.assertTrue(
                                        Cls_Generic_Methods.clickElement(driver,
                                                oPage_OperativeForm.button_selectBillOfMaterialInOperativeForm),
                                        " clicked on select bill of material button");
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver,
                                                oPage_OperativeForm.button_selectBillOfMaterialFromDropdownInOperativeForm),
                                        " bill of material is selected from dropdown");
                                m_assert.assertTrue(
                                        Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_next),
                                        " clicked on next button");
                            } else {
                                m_assert.assertTrue(
                                        Cls_Generic_Methods.sendKeysIntoElement(
                                                oPage_OperativeForm.input_irrigationSolutionInOperativeForm,
                                                oIPD_Data.sIRRIGATION_SOLUTION),
                                        "Irrigation solution field value filled as "
                                                + oIPD_Data.sIRRIGATION_SOLUTION);
                                m_assert.assertTrue(
                                        Cls_Generic_Methods.sendKeysIntoElement(
                                                oPage_OperativeForm.input_irrigationSolutionBatchNoInOperativeForm,
                                                oIPD_Data.sBATCH_NO),
                                        "Irrigation solution Batch no value filled as "
                                                + oIPD_Data.sBATCH_NO);
                                m_assert.assertTrue(
                                        Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_next),
                                        " clicked on next button");
                            }
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_OperativeForm.button_typeOfSurgeryInOperativeForm),
                                    " Type of surgery need to be performed is selected");
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_next),
                                    " clicked on next button");
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_OperativeForm.button_savedSurgeryNotesInOperativeForm),
                                    " clicked on one of the saved surgery note");
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_next),
                                    " clicked on next button");
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_OperativeForm.input_complicationCommentInOperativeForm,
                                            oIPD_Data.sCOMPLICATION),
                                    "Intraoperative Complications filled as "
                                            + oIPD_Data.sCOMPLICATION);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_next),
                                    " clicked on next button");
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_OperativeForm.input_PostOpPlanInOperativeForm,
                                            oIPD_Data.sPOST_OP_PLAN),
                                    "Post OP Plan filled as " + oIPD_Data.sPOST_OP_PLAN);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_next),
                                    " clicked on next button");
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_OperativeForm.checkbox_ctrDeviceInOperativeForm),
                                    "devices used during procedure are selected ");
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_OperativeForm.button_saveOperativeForm, 20);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver,
                                    oPage_OperativeForm.button_saveOperativeForm), "Operative form saved ");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate),
                                    "operative form closed ");

                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("Exception while filling Operative Template " + e);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("Exception while filling Intra Operative Templates " + e);
                    }

                } else {
                    m_assert.assertInfo(false,
                            "Admission form in Intra operative section is not filled, please fill admission form");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Fill OT templates")
    public void fillPostOperativeTemplateFormInOt() {

        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        Page_AldreteScoreTemplate oPage_AldreteScoreTemplate = new Page_AldreteScoreTemplate(driver);
        Page_DischargeForm oPage_DischargeForm = new Page_DischargeForm(driver);
        Page_PainAssessment oPage_PainAssessment = new Page_PainAssessment(driver);
        Page_PreAnaesthesiaTemplate oPage_PreAnaesthesiaTemplate = new Page_PreAnaesthesiaTemplate(driver);
        Page_OperativeForm oPage_OperativeForm = new Page_OperativeForm(driver);
        Page_IntraPostOperativeTemplateVisiblity oPage_IntaOperative = new Page_IntraPostOperativeTemplateVisiblity(driver);
        Page_OtTabs oPage_OtTabs = new Page_OtTabs(driver);
        IPD_Data oIPD_Data = new IPD_Data();

        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        boolean bValidatePatientFound = false;
        Page_IPD oPage_IPD = new Page_IPD(driver);
        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OT");


            String tabToSelect = OT_Data.tab_ALL;
            CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, tabToSelect);
            bValidatePatientFound = CommonActions.selectPatientNameInOT(oPage_OtTabs.rows_patientNamesOnOt, concatPatientFullName);
            m_assert.assertInfo(bValidatePatientFound, "Validate patient found");

            if (bValidatePatientFound) {
                // Cls_Generic_Methods.customWait(4);
                if (Cls_Generic_Methods.isElementDisplayed(oPage_IntaOperative.text_postOperativeSectionInOt)) {

                    // Aldrete Score
                    try {

                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_AldreteScoreTemplate.button_alderteScoreTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_AldreteScoreTemplate.button_alderteScoreTemplate),
                                "clicked on Alderte score template ");
                        Cls_Generic_Methods.customWait(4);

                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElementByJS(driver,
                                        oPage_AldreteScoreTemplate.radioButton_2extremitiesInAldreteScoreTemplate),
                                "selected 2 extremities(1) radio button as Activity ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_AldreteScoreTemplate.button_saveAldereteScoreTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_AldreteScoreTemplate.button_saveAldereteScoreTemplate),
                                "Aldrete score template saved ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate),
                                "Aldrete score template closed ");
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("Exception while filling Aldrete score Template " + e);
                    }

                    // Discharge
                    try {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DischargeForm.button_dischargeTemplate,
                                20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver, oPage_DischargeForm.button_dischargeTemplate),
                                "clicked on Discharge template ");
                        Cls_Generic_Methods.customWait(4);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElementByJS(driver,
                                        oPage_DischargeForm.checkbox_checkAllInDischargeTemplate),
                                "selected all discharge checklist ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OperativeForm.button_next, 20);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_next),
                                " clicked on next button");
                        m_assert.assertTrue(
                                Cls_Generic_Methods.sendKeysIntoElement(
                                        oPage_DischargeForm.input_treatmentNotesInDischargeTemplate,
                                        oIPD_Data.sTREATMENT_NOTES),
                                "Treatment notes entered as " + oIPD_Data.sTREATMENT_NOTES);

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_next),
                                " clicked on next button");
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_DischargeForm.button_selectMedicationSetInDischargeTemplate),
                                " medication set selected in discharge template");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_next),
                                " clicked on next button");
                        m_assert.assertTrue(
                                Cls_Generic_Methods.sendKeysIntoElement(
                                        oPage_DischargeForm.input_precautionsInDischargeTemplate,
                                        oIPD_Data.sPRECAUTIONS),
                                "Precaution in discharge template filled as "
                                        + oIPD_Data.sPRECAUTIONS);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OperativeForm.button_saveOperativeForm,
                                20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_saveOperativeForm),
                                " Discharge template successfully saved");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate),
                                "Discharge form closed ");

                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("Exception while filling discharge Template " + e);
                    }

                    // Pain Assessment
                    try {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_PainAssessment.input_painScaleInPainAssessmentTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_PainAssessment.input_painScaleInPainAssessmentTemplate),
                                "Clicked on pain assessment template");
                        //  Cls_Generic_Methods.customWait(4);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_AldreteScoreTemplate.button_saveAldereteScoreTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElementByJS(driver,
                                        oPage_PainAssessment.radioButton_hurtsLittleBitButtonInPainAssessmentTemplate),
                                "Hurt little Bit(2) radio button is selected from Wong-Baker Faces Pain Rating Scale");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_PainAssessment.tab_medicationTabInPainAssessmentTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_PainAssessment.tab_medicationTabInPainAssessmentTemplate),
                                "clicked on medication tab in pain assessment template");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_PainAssessment.input_medicationNameInPainAssessmentTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.sendKeysIntoElement(
                                        oPage_PainAssessment.input_medicationNameInPainAssessmentTemplate,
                                        oIPD_Data.sMEDICATION),
                                "Medicine name entered in pain assessment template as "
                                        + oIPD_Data.sMEDICATION);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_PainAssessment.tab_othersTabInPainAssessmentTemplate),
                                "clicked on others tab in pain assessment template");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_PainAssessment.input_recievedByFieldInPainAssessmentTemplate, 20);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                        oPage_PainAssessment.input_recievedByFieldInPainAssessmentTemplate,
                                        oIPD_Data.sRECEIVED_BY_FIELD_UNDER_OTHERS_TAB_IN_PAIN_ASSESSMENT_TEMPLATE),
                                "Received by field under others tab in pain assessment template filled as "
                                        + oIPD_Data.sRECEIVED_BY_FIELD_UNDER_OTHERS_TAB_IN_PAIN_ASSESSMENT_TEMPLATE);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_AldreteScoreTemplate.button_saveAldereteScoreTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_AldreteScoreTemplate.button_saveAldereteScoreTemplate),
                                "Pain assessment template saved ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate),
                                "Pain assessment template closed ");

                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("Exception while filling pain assessment Template " + e);
                    }
                } else {
                    m_assert.assertInfo(false,
                            "Admission form in Post Operative section is not filled, please fill admission form");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Exception while filling post Operative Templates " + e);
        }

    }

    @Test(enabled = true, description = "Fill OT templates")
    public void fillWardTemplateFormInOt() {
        Page_OtTabs oPage_OtTabs = new Page_OtTabs(driver);
        Page_IPD oPage_IPD = new Page_IPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        Page_wardNoteForm oPage_wardNoteForm = new Page_wardNoteForm(driver);
        Page_OperativeForm oPage_OperativeForm = new Page_OperativeForm(driver);
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        boolean bValidatePatientFound = false;
        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        String tabToSelect = OT_Data.tab_ALL;
        IPD_Data oIPD_Data = new IPD_Data();

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OT");


            CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, tabToSelect);
            bValidatePatientFound = CommonActions.selectPatientNameInOT(oPage_OtTabs.rows_patientNamesOnOt, concatPatientFullName);
            m_assert.assertInfo(bValidatePatientFound, "Validate patient found");

            if (bValidatePatientFound) {

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_wardNoteForm.button_wardNote),
                        " Clicked on new to view ward note");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.input_wardNoteText, 20);
                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_wardNoteForm.input_wardNoteText,
                                oIPD_Data.sWARD_NOTE),
                        "Ward note field filled as " + oIPD_Data.sWARD_NOTE);
                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_wardNoteForm.input_weightInWardNote,
                                oIPD_Data.sWEIGHT),
                        "Weight is captured as " + oIPD_Data.sWEIGHT);
                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_wardNoteForm.input_heightInWarNote,
                                oIPD_Data.sHEIGHT),
                        "Height is captured as " + oIPD_Data.sHEIGHT);
                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_wardNoteForm.input_TemperatureInWardNote,
                                oIPD_Data.sTEMPERATURE),
                        "Temperature captured as " + oIPD_Data.sTEMPERATURE);
                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_wardNoteForm.input_pulseInWardNote,
                                oIPD_Data.sPULSE),
                        "Pulse rate captured entered as " + oIPD_Data.sPULSE);
                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_wardNoteForm.input_bloodPressureInWardNote,
                                oIPD_Data.sBP),
                        "BP value captured as " + oIPD_Data.sBP);
                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_wardNoteForm.input_rrInWardNote,
                                oIPD_Data.sRESPIRATION_RATE),
                        "Respiration rate entered as " + oIPD_Data.sRESPIRATION_RATE);
                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_wardNoteForm.input_spo2InWardNote,
                                oIPD_Data.sSPo2),
                        "Spo2 entered as " + oIPD_Data.sSPo2);
                m_assert.assertTrue(
                        Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_saveOperativeForm),
                        " ward note successfully saved");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.button_closeWardNote, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_wardNoteForm.button_closeWardNote),
                        " ward note closed ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.button_wardNote, 16);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Exception while filling ward Template " + e);
        }

    }

    @Test(enabled = true, description = "Fill OT templates ")
    public void updateIntraOperativeTemplateFormInOt() {

        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        Page_PreAnaesthesiaTemplate oPage_PreAnaesthesiaTemplate = new Page_PreAnaesthesiaTemplate(driver);
        Page_OtChecklist oPage_OtChecklist = new Page_OtChecklist(driver);
        Page_BillOfMaterial oPage_BillOfMaterial = new Page_BillOfMaterial(driver);
        Page_OperativeForm oPage_OperativeForm = new Page_OperativeForm(driver);
        Page_SedationChart oPage_SedationChart = new Page_SedationChart(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_IPD oPage_IPD = new Page_IPD(driver);
        Page_OtTabs oPage_OtTabs = new Page_OtTabs(driver);
        Page_IntraPostOperativeTemplateVisiblity oPage_IntraPostOperativeTemplateVisiblity = new Page_IntraPostOperativeTemplateVisiblity(driver);
        Page_PreAnaesthesiaTemplateUpdate oPage_preAnaesthesiaTemplateUpdate = new Page_PreAnaesthesiaTemplateUpdate(driver);
        Page_OtChecklistTemplateUpdate oPage_OtChecklistTemplateUpdate = new Page_OtChecklistTemplateUpdate(driver);
        Page_SedationTemplateUpdate oPage_SedationTemplateUpdate = new Page_SedationTemplateUpdate(driver);
        Page_BillOfMaterialUpdate oPage_BillOfMaterialUpdate = new Page_BillOfMaterialUpdate(driver);
        Page_OperativeTemplateUpdate oPage_OperativeTemplateUpdate = new Page_OperativeTemplateUpdate(driver);

        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        boolean bValidatePatientFound = false;
        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OT");

            oPage_IPD = new Page_IPD(driver);
            oPage_OtTabs = new Page_OtTabs(driver);
            String tabToSelect = OT_Data.tab_ALL;

            CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, tabToSelect);
            bValidatePatientFound = CommonActions.selectPatientNameInOT(oPage_OtTabs.rows_patientNamesOnOt, concatPatientFullName);
            m_assert.assertInfo(bValidatePatientFound, "Validate patient found");

            if (bValidatePatientFound) {

                if (Cls_Generic_Methods.isElementDisplayed(oPage_IntraPostOperativeTemplateVisiblity.text_intraOperativeSectionInOt)) {
                    try {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_PreAnaesthesiaTemplate.button_PreAnaesthesiaChecklistTemplateInIPD, 20);
                        m_assert.assertInfo(
                                Cls_Generic_Methods.clickElementByAction(driver,
                                        oPage_PreAnaesthesiaTemplate.button_PreAnaesthesiaChecklistTemplateInIPD),
                                "clicked on Pre Anesthesia button");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_preAnaesthesiaTemplateUpdate.button_editTemplate, 20);
                        m_assert.assertInfo(
                                Cls_Generic_Methods.clickElementByAction(driver,
                                        oPage_preAnaesthesiaTemplateUpdate.button_editTemplate),
                                "clicked edit button to edit Pre Anesthesia button");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_preAnaesthesiaTemplateUpdate.button_noDiabetesUnderMedicalHistoryInPreAnaesthesiaTemplate,
                                20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(oPage_preAnaesthesiaTemplateUpdate.button_noDiabetesUnderMedicalHistoryInPreAnaesthesiaTemplate),
                                "Under medical history, Diabetes selected as NO ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_preAnaesthesiaTemplateUpdate.button_updateTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_preAnaesthesiaTemplateUpdate.button_updateTemplate),
                                " Pre Anesthesia Template updated");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate),
                                "Clicked on close Button to close Pre Anesthesia Template ");

                        // fill OT checklist
                        try {
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_OtChecklist.button_oTChecklistTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_OtChecklist.button_oTChecklistTemplate),
                                    "Clicked on OT checklist Button");
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_OtChecklistTemplateUpdate.button_viewOtCheckListTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_OtChecklistTemplateUpdate.button_viewOtCheckListTemplate),
                                    "Clicked on already created OT checklist Template to view and update ");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_preAnaesthesiaTemplateUpdate.button_editTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_preAnaesthesiaTemplateUpdate.button_editTemplate),
                                    "Clicked on Edit button to view and update OT checklist template ");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_OtChecklistTemplateUpdate.input_doseGivenByFieldInChecklistTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_OtChecklistTemplateUpdate.input_doseGivenByFieldInChecklistTemplate,
                                            oIPD_Data.sDOSE_GIVEN_BY),
                                    "In OT checklist template dose given by field filled as "
                                            + oIPD_Data.sDOSE_GIVEN_BY);
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_preAnaesthesiaTemplateUpdate.button_updateTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_preAnaesthesiaTemplateUpdate.button_updateTemplate),
                                    " OT checklist template updated ");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate),
                                    "OT checklist template closed");
                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("Exception while updating OT Checklist Template " + e);
                        }

                        // fill sedation template
                        try {
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SedationTemplateUpdate.button_sedation,
                                    20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver, oPage_SedationTemplateUpdate.button_sedation),
                                    "Clicked on sedation template to view and update ");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_preAnaesthesiaTemplateUpdate.button_editTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver, oPage_preAnaesthesiaTemplateUpdate.button_editTemplate),
                                    "Clicked on Edit button  ");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_SedationTemplateUpdate.input_respirationRateUnderSedationChartInOt, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationTemplateUpdate.input_respirationRateUnderSedationChartInOt,
                                            oIPD_Data.sRESPIRATION_RATE),
                                    "Respiration rate captured as "
                                            + oIPD_Data.sRESPIRATION_RATE);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationTemplateUpdate.input_bloodPressureUnderSedationChartInOt,
                                            oIPD_Data.sBP),
                                    "Blood pressure captured as " + oIPD_Data.sBP);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationTemplateUpdate.input_pulseUnderSedationChartInOt,
                                            oIPD_Data.sPULSE),
                                    "Pulse rate captured as " + oIPD_Data.sPULSE);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationTemplateUpdate.input_sedationScoreUnderSedationChartInOt,
                                            oIPD_Data.sSEDATION_SCORE),
                                    "sedation score captured as "
                                            + oIPD_Data.sSEDATION_SCORE);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationTemplateUpdate.input_medicationUnderSedationChartInOT,
                                            oIPD_Data.sMEDICATION),
                                    "Medication name captured as " + oIPD_Data.sMEDICATION);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationTemplateUpdate.input_bolusmlUnderSedationChartInOT,
                                            oIPD_Data.sBOLUS_ML),
                                    "Bolus ml value captured as " + oIPD_Data.sBOLUS_ML);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationTemplateUpdate.input_bolusmgUnderSedationChartInOt,
                                            oIPD_Data.sBOLUS_MG),
                                    "Bolus Mg value captured as " + oIPD_Data.sBOLUS_MG);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationTemplateUpdate.input_o2SaturationUnderSedationChartInOt,
                                            oIPD_Data.sO2_SATURATION),
                                    "O2 saturation field in sedation chart filled as "
                                            + oIPD_Data.sO2_SATURATION);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationTemplateUpdate.input_commentsUnderSedationChartInOT,
                                            oIPD_Data.sCOMMENTS_SEDATION_CHART),
                                    "Comments field in sedation chart filled as "
                                            + oIPD_Data.sCOMMENTS_SEDATION_CHART);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_SedationTemplateUpdate.input_nameUnderSedationChartInOT,
                                            oIPD_Data.sNAME_SEDATION_CHART),
                                    "Name field in sedation chart filled as "
                                            + oIPD_Data.sNAME_SEDATION_CHART);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_preAnaesthesiaTemplateUpdate.button_updateTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_preAnaesthesiaTemplateUpdate.button_updateTemplate),
                                    "sedation chart template updated ");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate),
                                    "sedation chart template closed ");

                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("Exception while updating sedation chart Template " + e);
                        }
                        // bill of material
                        try {
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_BillOfMaterial.button_billOfMaterialTemplate, 4);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_BillOfMaterial.button_billOfMaterialTemplate),
                                    "Clicked on bill of material template ");
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_BillOfMaterial.button_addBillOfMaterial, 4);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_BillOfMaterialUpdate.button_viewBillOfMaterial),
                                    "Clicked on bill of material to view BOM ");
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_BillOfMaterialUpdate.button_closeTemplate, 20);
                            if (Cls_Generic_Methods.isElementDisplayed(oPage_BillOfMaterialUpdate.button_editBillOfMaterial)) {
                                m_assert.assertTrue(
                                        Cls_Generic_Methods.clickElement(driver,
                                                oPage_BillOfMaterialUpdate.button_editBillOfMaterial),
                                        "Clicked on edit bill of material button");
                                Cls_Generic_Methods
                                        .waitForElementToBeDisplayed(oPage_BillOfMaterialUpdate.button_updateChangesInBillOfMaterial, 20);
                                m_assert.assertTrue(
                                        Cls_Generic_Methods.clickElement(driver,
                                                oPage_BillOfMaterialUpdate.button_updateChangesInBillOfMaterial),
                                        "Clicked on update changes button in bill of material template");
                            } else {
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_BillOfMaterialUpdate.button_closeTemplate, 8);
                                m_assert.assertTrue(
                                        Cls_Generic_Methods.clickElement(driver, oPage_BillOfMaterialUpdate.button_closeTemplate),
                                        " Clicked on close button");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("Exception while updating bill of material Template " + e);
                        }
                        // fill operative form
                        try {
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OperativeForm.button_operativeNote,
                                    20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_operativeNote),
                                    " clicked on operative template button");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OperativeTemplateUpdate.button_viewOperativeTemplate,
                                    20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_OperativeTemplateUpdate.button_viewOperativeTemplate),
                                    " clicked on note to view operative template ");
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_OperativeTemplateUpdate.button_editOperative, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_OperativeTemplateUpdate.button_editOperative),
                                    " clicked on edit operative note button ");
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_OperativeTemplateUpdate.input_surgeon1, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.sendKeysIntoElement(
                                            oPage_OperativeTemplateUpdate.input_surgeon1,
                                            oIPD_Data.sSURGEON_NAME),
                                    "Surgeon 1 name entered as "
                                            + oIPD_Data.sSURGEON_NAME);
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_preAnaesthesiaTemplateUpdate.button_updateTemplate, 20);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver,
                                    oPage_preAnaesthesiaTemplateUpdate.button_updateTemplate), "Operative form updated ");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate),
                                    "operative form closed ");
                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("Exception while updating  Operative Template " + e);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("Exception while updated Intra Operative Templates " + e);
                    }

                } else {
                    m_assert.assertInfo(false,
                            "Admission form in Intra operative section is not filled, please fill admission form");

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Fill OT templates")
    public void updatePostOperativeTemplateFormInOt() {

        Page_PreAnaesthesiaTemplate oPage_PreAnaesthesiaTemplate = new Page_PreAnaesthesiaTemplate(driver);
        Page_OperativeForm oPage_OperativeForm = new Page_OperativeForm(driver);
        Page_IntraPostOperativeTemplateVisiblity oPage_IntraPostOperativeTemplateVisiblity = new Page_IntraPostOperativeTemplateVisiblity(driver);
        Page_PreAnaesthesiaTemplateUpdate oPage_preAnaesthesiaTemplateUpdate = new Page_PreAnaesthesiaTemplateUpdate(driver);
        Page_OperativeTemplateUpdate oPage_OperativeTemplateUpdate = new Page_OperativeTemplateUpdate(driver);
        Page_IPD oPage_IPD = new Page_IPD(driver);
        Page_OtTabs oPage_OtTabs = new Page_OtTabs(driver);

        Page_AldreteScoreTemplate oPage_AldreteScoreTemplate = new Page_AldreteScoreTemplate(driver);
        Page_DischargeForm oPage_DischargeForm = new Page_DischargeForm(driver);
        Page_PainAssessment oPage_PainAssessment = new Page_PainAssessment(driver);
        Page_AldreteScoreTemplateUpdate oPage_AldreteScoreTemplateUpdate = new Page_AldreteScoreTemplateUpdate(driver);
        Page_DischargeNoteUpdate oPage_DischargeNoteUpdate = new Page_DischargeNoteUpdate(driver);
        Page_PainAssessmentTemplateUpdate oPage_PainAssessmentTemplateUpdate = new Page_PainAssessmentTemplateUpdate(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        boolean bValidatePatientFound = false;
        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OT");


            String tabToSelect = OT_Data.tab_ALL;
            CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, tabToSelect);
            bValidatePatientFound = CommonActions.selectPatientNameInOT(oPage_OtTabs.rows_patientNamesOnOt, concatPatientFullName);
            m_assert.assertInfo(bValidatePatientFound, "Validate patient found");

            if (bValidatePatientFound) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_IntraPostOperativeTemplateVisiblity.text_postOperativeSectionInOt)) {

                    // Aldrete Score
                    try {

                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_AldreteScoreTemplate.button_alderteScoreTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_AldreteScoreTemplate.button_alderteScoreTemplate),
                                "clicked on Alderte score template ");
                        Cls_Generic_Methods
                                .waitForElementToBeDisplayed(oPage_preAnaesthesiaTemplateUpdate.button_editTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_preAnaesthesiaTemplateUpdate.button_editTemplate),
                                " clicked on edit aldrete score template button ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_preAnaesthesiaTemplateUpdate.button_updateTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElementByJS(driver,
                                        oPage_AldreteScoreTemplateUpdate.radioButton_dyspnoeaInAldreteScoreTemplate),
                                "selected dyspnoea radio button in aldrete score template ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_preAnaesthesiaTemplateUpdate.button_updateTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_preAnaesthesiaTemplateUpdate.button_updateTemplate),
                                "Aldrete score template updated ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate),
                                "Aldrete score template closed ");
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("Exception while updating Aldrete score Template " + e);
                    }

                    // discharge template
                    try {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DischargeForm.button_dischargeTemplate,
                                20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver, oPage_DischargeForm.button_dischargeTemplate),
                                "clicked on Discharge template ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_OperativeTemplateUpdate.button_editOperative, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver, oPage_OperativeTemplateUpdate.button_editOperative),
                                "Clicked on edit discharge button  ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_OperativeForm.button_next, 20);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_next),
                                " clicked on next button");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_DischargeNoteUpdate.input_procedureCodeInDischargeTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.sendKeysIntoElement(
                                        oPage_DischargeNoteUpdate.input_procedureCodeInDischargeTemplate,
                                        oIPD_Data.sPROCEDURE_CODE),
                                "Procedure code entered as " + oIPD_Data.sPROCEDURE_CODE);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_preAnaesthesiaTemplateUpdate.button_updateTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_preAnaesthesiaTemplateUpdate.button_updateTemplate),
                                "Discharge Note template updated ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate),
                                "Discharge form closed ");

                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("Exception while updating discharge Template " + e);
                    }

                    // pain assessment
                    try {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_PainAssessment.input_painScaleInPainAssessmentTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_PainAssessment.input_painScaleInPainAssessmentTemplate),
                                "Clicked on pain assessment template");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_preAnaesthesiaTemplateUpdate.button_editTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver, oPage_preAnaesthesiaTemplateUpdate.button_editTemplate),
                                "Clicked on edit pain assessment template button  ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_PainAssessment.tab_medicationTabInPainAssessmentTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_PainAssessment.tab_medicationTabInPainAssessmentTemplate),
                                "clicked on medication tab in pain assessment template");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_PainAssessmentTemplateUpdate.input_medicationNameInSecondRowPainAssessmentTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.sendKeysIntoElement(
                                        oPage_PainAssessmentTemplateUpdate.input_medicationNameInSecondRowPainAssessmentTemplate,
                                        oIPD_Data.sMEDICATION),
                                "Medicine name entered in pain assessment template as "
                                        + oIPD_Data.sMEDICATION);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_preAnaesthesiaTemplateUpdate.button_updateTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_preAnaesthesiaTemplateUpdate.button_updateTemplate),
                                "Pain Assessment template updated ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate),
                                "Pain assessment template closed ");

                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("Exception while updating pain assessment Template " + e);
                    }

                } else {
                    m_assert.assertInfo(false,
                            "Admission form in Post Operative section is not filled, please fill admission form");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Exception while updating post Operative Templates " + e);
        }

    }

    @Test(enabled = true, description = "Fill OT templates")
    public void updateWardTemplateFormInOt() {

        Page_OtTabs oPage_OtTabs = new Page_OtTabs(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        Page_wardNoteForm oPage_wardNoteForm = new Page_wardNoteForm(driver);
        Page_ViewAllWardNotes oPage_ViewAllWardNotes = new Page_ViewAllWardNotes(driver);
        Page_PreAnaesthesiaTemplateUpdate oPage_preAnaesthesiaTemplateUpdate = new Page_PreAnaesthesiaTemplateUpdate(driver);
        Page_IPD oPage_IPD = new Page_IPD(driver);

        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        boolean bValidatePatientFound = false;
        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OT");

            String tabToSelect = OT_Data.tab_ALL;
            CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, tabToSelect);
            bValidatePatientFound = CommonActions.selectPatientNameInOT(oPage_OtTabs.rows_patientNamesOnOt, concatPatientFullName);
            m_assert.assertInfo(bValidatePatientFound, "Validate patient found");
            if (bValidatePatientFound) {

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ViewAllWardNotes.button_viewAllWardNote, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ViewAllWardNotes.button_viewAllWardNote),
                        " Clicked on view all ward note");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ViewAllWardNotes.button_editWardNote, 20);
                m_assert.assertTrue(
                        Cls_Generic_Methods.clickElement(driver, oPage_ViewAllWardNotes.button_editWardNote),
                        " clicked on edit button to edit ward note");
                Cls_Generic_Methods.waitForElementToBeDisplayed(
                        oPage_preAnaesthesiaTemplateUpdate.button_updateTemplate, 20);
                m_assert.assertTrue(
                        Cls_Generic_Methods.clickElement(driver,
                                oPage_preAnaesthesiaTemplateUpdate.button_updateTemplate),
                        "ward note updated ");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_wardNoteForm.button_closeWardNote),
                        " ward note closed ");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Exception while updating ward Template " + e);
        }

    }

}
