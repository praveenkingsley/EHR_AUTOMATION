package tests.departments.ipd;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.IPD_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.common_tabs.Page_HistoryTab;
import pages.commonElements.dashboard.Page_Dashboard;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.ipd.Page_IPD;
import pages.ipd.forms.consent.Page_AdmissionInConsent;
import pages.ipd.forms.consent.Page_CustomConsents;
import pages.ipd.forms.consent.Page_Operative;
import pages.ipd.forms.consent.Page_UploadPapers;
import pages.ipd.forms.intraOperative.*;
import pages.ipd.forms.postOperative.Page_AldreteScoreTemplate;
import pages.ipd.forms.postOperative.Page_DischargeForm;
import pages.ipd.forms.postOperative.Page_PainAssessment;
import pages.ipd.forms.preOperative.Page_AdmissionInPreOperative;
import pages.ipd.forms.preOperative.Page_Assessment;
import pages.ipd.forms.preOperative.Page_CarePlan;
import pages.ipd.forms.preOperative.Page_WardChecklist;
import pages.ipd.forms.wardNote.Page_wardNoteForm;

import java.text.SimpleDateFormat;
import java.util.Date;


public class IPDFormsTest extends TestBase {

    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();
    Page_IPD oPage_IPD;
    String sSelectedOphthalmicHistory = "";
    String sSelectedSystemicHistory = "";

    String sSelectedAllergy = "";

    @Test(enabled = true, description = "Validate Consent Forms for Patient in IPD")
    public void fillConsentForms() {
        Page_AdmissionInConsent oPage_AdmissionInConsent = new Page_AdmissionInConsent(driver);
        Page_Operative oPage_Operative = new Page_Operative(driver);
        Page_UploadPapers oPage_UploadPapers = new Page_UploadPapers(driver);
        Page_CustomConsents oPage_CustomConsents = new Page_CustomConsents(driver);
        String myQueueTab = "My Queue";
        oPage_IPD = new Page_IPD(driver);

        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        boolean bValidatePatientFound = false;
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();

        try {

            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("IPD");

            Cls_Generic_Methods.customWait(10);
            m_assert.assertTrue(
                    CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, myQueueTab),
                    "Validate " + myQueueTab + " tab is selected");
            Cls_Generic_Methods.customWait(3);

            bValidatePatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, concatPatientFullName);

            m_assert.assertTrue(bValidatePatientFound, "Validate patient " + concatPatientFullName + " is found");

            // Validate that consent forms section is visible
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_consentsSection, 16);

            if (bValidatePatientFound) {
                m_assert.assertTrue(true, "Validate the Consent forms section is displayed");

                Cls_Generic_Methods.scrollToElementByJS(oPage_IPD
                        .text_consentsSection);

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
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_consentsSection, 16);

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

//						Cls_Generic_Methods.clickElement(oPage_IPD.selectButton_templateForConsent);
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
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_consentsSection, 16);

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
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_consentsSection, 16);

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }

                // Custom Consents
                try {
                    m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_CustomConsents.button_customConsentInConsent),
                            "<b>Validate Custom Consent button is visible under the Consents forms</b>");

                    Cls_Generic_Methods.clickElement(oPage_CustomConsents.button_customConsentInConsent);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CustomConsents.header_customConsent, 16);

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CustomConsents.selectButton_specialityInCustomConsent),
                            "Validate the Speciality dropdown is visible");
                    m_assert.assertInfo(true,
                            "Validate " + oPage_CustomConsents.selectOptions_specialityInCustomConsent.size() + " options are present under Speciality dropdown");

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CustomConsents.selectButton_consentNameInCustomConsent),
                            "Validate the Consent Name dropdown is visible");
                    m_assert.assertInfo(true,
                            "Validate " + oPage_CustomConsents.selectOptions_consentNameInCustomConsent.size() + " options are present under Consent Name dropdown");

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CustomConsents.selectButton_languageInCustomConsent),
                            "Validate the Language dropdown is visible");
                    m_assert.assertInfo(true,
                            "Validate " + oPage_CustomConsents.selectOptions_languageInCustomConsent.size() + " options are present under Language dropdown");

                    Cls_Generic_Methods.clickElement(oPage_CustomConsents.button_closeTemplate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_consentsSection, 16);

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

    @Test(enabled = true, description = "Validate Pre Operative Forms for Patient in IPD")
    public void fillPreOperativeForms() {
        Page_AdmissionInPreOperative oPage_AdmissionInPreOperative = new Page_AdmissionInPreOperative(driver);
        Page_Assessment oPage_Assessment = new Page_Assessment(driver);
        Page_CarePlan oPage_CarePlan = new Page_CarePlan(driver);
        Page_WardChecklist oPage_WardChecklist = new Page_WardChecklist(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String myQueueTab = "My Queue";
        boolean bValidatePatientFound = false;
        oPage_IPD = new Page_IPD(driver);
        Page_HistoryTab oPage_HistoryTab = new Page_HistoryTab(driver);
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();

        try {

            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("IPD");
            Cls_Generic_Methods.customWait(10);

            m_assert.assertTrue(
                    CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, myQueueTab),
                    "Validate " + myQueueTab + " tab is selected");
            Cls_Generic_Methods.customWait(3);

            bValidatePatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, concatPatientFullName);

            // Validate that Pre Operative forms section is visible
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_preOperativeSection, 16);

            if (bValidatePatientFound) {
                m_assert.assertTrue(true, "Validate the Pre-Operative forms section is displayed");

                Cls_Generic_Methods.scrollToElementByJS(oPage_IPD.text_preOperativeSection);

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
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver,
                                    oPage_HistoryTab.button_OneOphthalmicHistory),
                            " selected one of the ophthalmic history");
                     sSelectedOphthalmicHistory = Cls_Generic_Methods.getTextInElement(oPage_HistoryTab.button_OneOphthalmicHistory);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.button_SystemmicHistory),
                            " selected one of the systemic history");
                     sSelectedSystemicHistory = Cls_Generic_Methods.getTextInElement(oPage_HistoryTab.button_SystemmicHistory);
                    Cls_Generic_Methods.customWait(3);
                    Cls_Generic_Methods.scrollToElementByJS(oPage_HistoryTab.button_Allergy);
                    Cls_Generic_Methods.customWait();
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.button_Allergy),
                            " selected one of the allergy");
                    Cls_Generic_Methods.customWait(3);
                     sSelectedAllergy = Cls_Generic_Methods.getTextInElement(oPage_HistoryTab.button_Allergy);
                    Cls_Generic_Methods.customWait(3);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.button_saveOnModalHeader),
                            "Validate Save button is clicked on Admission under Pre-Operative");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);
                    Cls_Generic_Methods.customWait(5);
                    Cls_Generic_Methods.clickElementByAction(driver,oPage_CommonElements.button_CloseTemplate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_preOperativeSection, 16);

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
                    Cls_Generic_Methods.customWait(4);
                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_preOperativeSection, 16);

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

//                    String medicationSetName = "testenv";
//                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_CarePlan.select_medicationSetInCarePlan, medicationSetName),
//                            "Validate " + medicationSetName + " option is selected under Medical sets for Nurse Care Plan");
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_CarePlan.select_medicationSetInCarePlan, 0),
                            "Validate " + Cls_Generic_Methods.getSelectedValue(oPage_CarePlan.select_medicationSetInCarePlan) + " option is selected under Medical sets for Nurse Care Plan");

                    Cls_Generic_Methods.customWait();

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CarePlan.button_saveOnModalHeader),
                            "Validate Save button is clicked on Assessment under Pre-Operative");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);
                    Cls_Generic_Methods.customWait(5);
                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_preOperativeSection, 16);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                // Ward Checklist
                try {
                    m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_WardChecklist.button_wardChecklistInPreOperative),
                            "<b>Validate Assessment button is visible under the Pre-Operative forms</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WardChecklist.button_wardChecklistInPreOperative),
                            "Validate Assessment button is clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_saveOnModalHeader, 16);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.button_saveOnModalHeader),
                            "Validate Save button is clicked on Assessment under Pre-Operative");
                    Cls_Generic_Methods.customWait(5);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);
                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_preOperativeSection, 16);

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

    @Test(enabled = true, description = "Fill IPD templates ")
    public void fillIntraOperativeTemplateForm() {

        Page_PreAnaesthesiaTemplate oPage_PreAnaesthesiaTemplate = new Page_PreAnaesthesiaTemplate(driver);
        Page_OtChecklist oPage_OtChecklist = new Page_OtChecklist(driver);
        Page_BillOfMaterial oPage_BillOfMaterial = new Page_BillOfMaterial(driver);
        Page_OperativeForm oPage_OperativeForm = new Page_OperativeForm(driver);
        Page_SedationChart oPage_SedationChart = new Page_SedationChart(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String myQueueTab = "My Queue";
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        boolean bValidatePatientFound = false;
        IPD_Data oIPD_Data = new IPD_Data();
        oPage_IPD = new Page_IPD(driver);
        String expectedLoggedInUser = "PR.Akash test";

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("IPD");
            Cls_Generic_Methods.customWait(10);

            m_assert.assertTrue(
                    CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, myQueueTab),
                    "Validate " + myQueueTab + " tab is selected");
            Cls_Generic_Methods.customWait(3);
            bValidatePatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnMyQueueTab, concatPatientFullName);

            if (bValidatePatientFound) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_intraOperativeSection)) {
                    try {

                        // fill Pre Anesthesia Checklist
                        try {
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_PreAnaesthesiaTemplate.button_PreAnaesthesiaChecklistTemplateInIPD, 20);
                            m_assert.assertInfo(
                                    Cls_Generic_Methods.clickElementByAction(driver,
                                            oPage_PreAnaesthesiaTemplate.button_PreAnaesthesiaChecklistTemplateInIPD),
                                    "clicked on Pre Anesthesia button");
                            Cls_Generic_Methods.customWait(3);

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
                                m_assert.assertFatal("Exception while validationg Anesthesia planned buttons" + e);
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
                            Cls_Generic_Methods.customWait(4);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate),
                                    "Clicked on close Button to close Pre Anesthesia Template ");
                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("Exception while filling Pre Anesthesia Checklist Template " + e);
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

                            if (oPage_OtChecklist.list_checkboxProcedure.size() > 0) {
                                for (WebElement inputProcedure : oPage_OtChecklist.list_checkboxProcedure) {
                                    String procedureValue = Cls_Generic_Methods.getTextInElement(inputProcedure.findElement(By.xpath("./following-sibling::label")));
                                    m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, inputProcedure), "Clicked procedure " + procedureValue + " in OT Checklist");
                                }
                            }
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_OtChecklist.button_saveOtChecklistTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_OtChecklist.button_saveOtChecklistTemplate),
                                    "OT checklist template saved ");
                            Cls_Generic_Methods.customWait(4);
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
                            Cls_Generic_Methods.customWait(4);
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

                                    int index = oPage_BillOfMaterial.tr_itemInBom.indexOf(buttonElement);
                                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, buttonElement),
                                            "selected a item from the list for BOM");
                                    break;

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                m_assert.assertFatal("Exception while validationg Anesthesia planned buttons" + e);
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
                                    Cls_Generic_Methods.clickElementByJS(driver, oPage_OperativeForm.button_operativeNote),
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
                            if (oPage_OperativeForm.list_checkboxProcedure.size() > 0) {
                                for (WebElement inputProcedure : oPage_OperativeForm.list_checkboxProcedure) {
                                    String procedureValue = Cls_Generic_Methods.getTextInElement(inputProcedure.findElement(By.xpath("./following-sibling::label")));
                                    m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, inputProcedure), "Clicked procedure " + procedureValue + " performed in Operative note");
                                }
                                for (WebElement selectProcedure : oPage_OperativeForm.list_selectProcedurePerformed) {
                                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(selectProcedure, 2), "Selected " + Cls_Generic_Methods.getSelectedValue(selectProcedure) + " under Procedure performed By");
                                }

                            }
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
                            Cls_Generic_Methods.customWait(3);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate, 20);
                            m_assert.assertTrue(
                                    Cls_Generic_Methods.clickElement(driver,
                                            oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate),
                                    "operative form closed ");
                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("Exception while filling  Operative Template " + e);
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

    @Test(enabled = true, description = "Fill IPD templates")
    public void fillPostOperativeTemplateForm() {

        Page_AldreteScoreTemplate oPage_AldreteScoreTemplate = new Page_AldreteScoreTemplate(driver);
        Page_DischargeForm oPage_DischargeForm = new Page_DischargeForm(driver);
        Page_PainAssessment oPage_PainAssessment = new Page_PainAssessment(driver);
        Page_PreAnaesthesiaTemplate oPage_PreAnaesthesiaTemplate = new Page_PreAnaesthesiaTemplate(driver);
        Page_OperativeForm oPage_OperativeForm = new Page_OperativeForm(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        String myQueueTab = "My Queue";
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        boolean bValidatePatientFound = false;
        IPD_Data oIPD_Data = new IPD_Data();
        oPage_IPD = new Page_IPD(driver);
        String expectedLoggedInUser = "PR.Akash test";

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("IPD");
            Cls_Generic_Methods.customWait(10);
            m_assert.assertTrue(
                    CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, myQueueTab),
                    "Validate " + myQueueTab + " tab is selected");
            Cls_Generic_Methods.customWait(3);
            bValidatePatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnMyQueueTab, concatPatientFullName);

            if (bValidatePatientFound) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_postOperativeSection)) {

                    // Alderete Score
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
                        Cls_Generic_Methods.customWait(4);
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
                                Cls_Generic_Methods.selectElementByIndex(oPage_DischargeForm.button_selectMedicationSetInDischargeTemplate, 0),
                                Cls_Generic_Methods.getSelectedValue(oPage_DischargeForm.button_selectMedicationSetInDischargeTemplate)
                                        + " medication set selected in discharge template");

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
                        Cls_Generic_Methods.customWait(4);

                                if(Cls_Generic_Methods.radioButtonIsSelected(oPage_OperativeForm.checkbox_history)){
                                m_assert.assertWarn("By default History checkbox should be selected");

                        }else{
                                    m_assert.assertTrue("By default History checkbox should be in unchecked state");
                                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver,oPage_OperativeForm.checkbox_history),"History checkbox is clickable");
                                }
                        if(Cls_Generic_Methods.isElementDisplayed(oPage_OperativeForm.list_historySection.get(0))){
                           String sOphthalmicHistory = Cls_Generic_Methods.getTextInElement(oPage_OperativeForm.list_historiesUnderDischargeSummaryAndNote.get(0));
                            String sSystemicHistory = Cls_Generic_Methods.getTextInElement(oPage_OperativeForm.list_historiesUnderDischargeSummaryAndNote.get(1));
                            String sAllergiesUnderHistory = Cls_Generic_Methods.getTextInElement(oPage_OperativeForm.list_historiesUnderDischargeSummaryAndNote.get(2));
                            if(sOphthalmicHistory.equalsIgnoreCase(sSelectedOphthalmicHistory)){
                                m_assert.assertTrue("Ophthalmic history present for a patient = <b>" +sOphthalmicHistory+ "</b> ");
                            }
                            else{
                                m_assert.assertTrue("Patient histories are not visible under history section of discharge note" );
                            }
                            if(sSystemicHistory.equalsIgnoreCase(sSelectedSystemicHistory)){
                                m_assert.assertTrue("Systemic history present for a patient: <b>" +sSystemicHistory+ "</b>");
                            }
                            if(sAllergiesUnderHistory.contains(sSelectedAllergy)){
                                m_assert.assertTrue("Patient's allergy: <b> "+sAllergiesUnderHistory+ "</b>");
                            }
                        }
                        else{
                            m_assert.assertWarn("History section is not visible under discharge note");
                        }
                        if(Cls_Generic_Methods.isElementDisplayed(oPage_OperativeForm.list_historySection.get(1))){
                            String sOphthalmicHistoryUnderDischargeSummary = Cls_Generic_Methods.getTextInElement(oPage_OperativeForm.list_historiesUnderDischargeSummaryAndNote.get(3));
                            String sSystemicHistoryUnderDischargeSummary = Cls_Generic_Methods.getTextInElement(oPage_OperativeForm.list_historiesUnderDischargeSummaryAndNote.get(4));
                            String sAllergiesUnderDischargeSummary = Cls_Generic_Methods.getTextInElement(oPage_OperativeForm.list_historiesUnderDischargeSummaryAndNote.get(5));
                            if(sOphthalmicHistoryUnderDischargeSummary.equalsIgnoreCase(sSelectedOphthalmicHistory)){
                                m_assert.assertTrue("Ophthalmic history present for a patient = <b>" +sOphthalmicHistoryUnderDischargeSummary+ "</b> ");
                            }
                            else{
                                m_assert.assertTrue("Patient histories are not visible under history section of discharge note" );
                            }
                            if(sSystemicHistoryUnderDischargeSummary.equalsIgnoreCase(sSelectedSystemicHistory)){
                                m_assert.assertTrue("Systemic history present for a patient: <b>" +sSystemicHistoryUnderDischargeSummary+ "</b>");
                            }
                            if(sAllergiesUnderDischargeSummary.contains(sSelectedAllergy)){
                                m_assert.assertTrue("Patient's allergy: <b> "+sAllergiesUnderDischargeSummary+ "</b>");
                            }
                        }
                        else{
                            m_assert.assertWarn("History section is not visible under discharge summary");
                        }
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

                    // pain assessment
                    try {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_PainAssessment.input_painScaleInPainAssessmentTemplate, 20);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_PainAssessment.input_painScaleInPainAssessmentTemplate),
                                "Clicked on pain assessment template");
                        Cls_Generic_Methods.customWait(4);
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
                        Cls_Generic_Methods.customWait(4);
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

    @Test(enabled = true, description = "Fill IPD templates")
    public void wardTemplateForm() {

        Page_wardNoteForm oPage_wardNoteForm = new Page_wardNoteForm(driver);
        Page_OperativeForm oPage_OperativeForm = new Page_OperativeForm(driver);
        Page_IPD oPage_IPD = new Page_IPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String myQueueTab = "My Queue";
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        boolean bValidatePatientFound = false;
        IPD_Data oIPD_Data = new IPD_Data();

        try {
            String expectedLoggedInUser = "PR.Akash test";
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("IPD");
            Cls_Generic_Methods.customWait(10);

            m_assert.assertTrue(
                    CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, myQueueTab),
                    "Validate " + myQueueTab + " tab is selected");
            Cls_Generic_Methods.customWait(3);
            bValidatePatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnMyQueueTab, concatPatientFullName);

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
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_wardNoteForm.button_closeWardNote),
                        " ward note closed ");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Exception while filling ward Template " + e);
        }

    }


}
