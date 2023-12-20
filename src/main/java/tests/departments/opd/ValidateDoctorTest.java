package tests.departments.opd;

import java.util.List;

import data.EHR_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;

import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.common_tabs.*;
import pages.commonElements.common_tabs.advice.Page_AdviceTab;
import pages.commonElements.common_tabs.investigation.Page_InvestigationTab;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.templates.eye.Page_EyeTemplate;
import pages.opd.Page_OPD;
import pages.optometrist.Pages_Optometrist;

public class ValidateDoctorTest extends TestBase {

    Page_EyeTemplate oPage_EyeTemplate;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_OPD oPage_OPD;
    Pages_Optometrist oPages_Optometrist;
    Page_Navbar oPage_Navbar;
    EHR_Data oEHR_Data = new EHR_Data();
    Page_HistoryTab oPage_HistoryTab;
    Page_RefractionTab oPage_RefractionTab;
    Page_InvestigationTab oPage_InvestigationTab;
    Page_DiagnosisTab oPage_DiagnosisTab;
    Page_AdviceTab oPage_AdviceTab;
    Page_CommonElements oPage_CommonElements;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    static Model_Patient myPatient;
    boolean runScriptInDebugMode = false;


    @Test(enabled = true, description = "Validate Doctor Module")
    public void validatePatientArrivedInDoctor() {
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String patientName = null;
//        String status = null;
        boolean bPatientNameFound = false;

        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectFacility("TST");

            Cls_Generic_Methods.customWait(5);
            // Assuming that the opened page is OPD
            try {
                String MyQueueTab = "My Queue";
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab), "Validate " + MyQueueTab + " tab is selected");
                Thread.sleep(1000);

                for (WebElement patient : oPage_OPD.rows_patientAppointments) {
                    if (patient.isDisplayed()) {
                        List<WebElement> patientDetailsOnRow = patient.findElements(By.xpath("./child::*"));
                        patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");
                        //                    status = Cls_Generic_Methods.getTextInElement(patientDetailsOnRow.get(4));
                        if (concatPatientFullName.equals(patientName.trim())) {

                            m_assert.assertTrue(true, "Patient Name Matched in Appointment Details Section");
                            bPatientNameFound = true;
                            Cls_Generic_Methods.clickElement(driver, patient);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
                            break;
                        }
                    }
                }

                m_assert.assertTrue(bPatientNameFound, "Validate Patient  " + concatPatientFullName + " is clicked in " + MyQueueTab + " of Doctor");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient in Optometrist module " + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Fill Eye Template Data")
    public void createEyeTemplate() {

        oPage_EyeTemplate = new Page_EyeTemplate(driver);
        oPages_Optometrist = new Pages_Optometrist(driver);
        oPage_Navbar = new Page_Navbar(driver);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oPage_RefractionTab = new Page_RefractionTab(driver);
        oPage_AdviceTab = new Page_AdviceTab(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_DiagnosisTab = new Page_DiagnosisTab(driver);
        oPage_InvestigationTab = new Page_InvestigationTab(driver);
        String EyeTemplate = "Eye";
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);

        try {
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, EyeTemplate), "Validate " + EyeTemplate + " template  is selected");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.tab_RefractionTab, 20);

            if (runScriptInDebugMode) {
                //Validate History Tab
                m_assert.assertInfo(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_HistoryTab.tab_HistoryTab, 15), "Upon clicking on eye template history tab is displayed");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.tab_HistoryTab), "clicked on history tab");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.button_OneChiefComplaints), "Chief Complaint selected as Nil");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.button_OneOphthalmicHistory), "Ophthalmic History is selected as Nil");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.button_SystemmicHistory), "Systemic History is selected as Nil");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.button_Allergy), "Allergy Advice is selected as Nil");

                //Validate Refraction Tab
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_EyeTemplate.refraction_Tab), "Refraction Tab Is selected");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.button_editRefractionButton, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_RefractionTab.button_editRefractionButton), "Validate clicked on Refraction tab");
                Cls_Generic_Methods.customWait(5);
                m_assert.assertInfo(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_EyeTemplate.left_UCVA, 15), "Upon clicking on refraction tab left UCVA is displayed");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_EyeTemplate.check_left_UCVA), "Left UCVA Is selected as Nil");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_EyeTemplate.check_right_UCVA), "Right UCVA Is selected as Nil");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_EyeTemplate.check_left_IOP), "Left IOP Is selected as Nil");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_EyeTemplate.check_right_IOP), "Right IOP Is selected as Nil");
            }

            //Validate Investigation Tab

            // Ophthal Investigations
            try {
//                String sOphthalSetToSelect = "glaucoma";
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_InvestigationTab.tab_investigation), "Investigation Tab Is selected");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_InvestigationTab.tab_ophthalUnderInvestigationTab), "Ophthal in Investigation Tab Is selected");
//                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_InvestigationTab.select_ophthalSetsUnderInvestigations, sOphthalSetToSelect), sOphthalSetToSelect + " option is selected under Ophthal Sets");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_InvestigationTab.select_ophthalSetsUnderInvestigations, 0), "Ophthalmology Investigations selected");
//               Commented due to prod issue once prod issue resolved we can uncomment
//                m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_InvestigationTab.select_ophthalSetsUnderInvestigations, 0),
//                        Cls_Generic_Methods.getSelectedValue(oPage_InvestigationTab.select_ophthalSetsUnderInvestigations) + " option is selected under Ophthal Sets");
//                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InvestigationTab.rows_selectedOphthalInvestigations.get(0),16);
//                int selectedOphthalInvestigations = oPage_InvestigationTab.rows_selectedOphthalInvestigations.size();
//                m_assert.assertTrue(selectedOphthalInvestigations > 0, "Validate at least one Investigation is selected. Current count of Ophthal investigations = " + selectedOphthalInvestigations);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Error while validating Ophthal Investigations. \n" + e);
            }

            // Laboratory Investigations
            try {
                String sLaboratorySetToSelect = "cornea";
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_InvestigationTab.tab_laboratoryUnderInvestigationTab), "Laboratory in Investigation Tab Is selected");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_InvestigationTab.select_laboratorySetsUnderInvestigations, sLaboratorySetToSelect), sLaboratorySetToSelect + " option is selected under Laboratory Sets");
                Cls_Generic_Methods.customWait();
                //Commented due to prod issue once prod issue resolved we can uncomment
                //Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InvestigationTab.rows_selectedLaboratoryInvestigations.get(0),16);
                // int selectedLaboratoryInvestigations = oPage_InvestigationTab.rows_selectedLaboratoryInvestigations.size();
                // m_assert.assertTrue(selectedLaboratoryInvestigations > 0, "Validate at least one Investigation is selected. Current count of Laboratory investigations = " + selectedLaboratoryInvestigations);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Error while validating Laboratory Investigations. \n" + e);
            }

            // Radiology Investigations
            try {
                String sRadiologySetToSelect = "testenv";
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_InvestigationTab.tab_radiologyUnderInvestigationTab), "Radiology in Investigation Tab Is selected");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_InvestigationTab.select_radiologySetsUnderInvestigations, sRadiologySetToSelect), sRadiologySetToSelect + " option is selected under Radiology Sets");
                Cls_Generic_Methods.customWait();
                //Commented due to prod issue once prod issue resolved we can uncomment
                // Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InvestigationTab.rows_selectedRadiologyInvestigations.get(0),16);
                // int selectedLaboratoryInvestigations = oPage_InvestigationTab.rows_selectedRadiologyInvestigations.size();
                // m_assert.assertTrue(selectedLaboratoryInvestigations > 0, "Validate at least one Investigation is selected. Current count of Radiology investigations = " + selectedLaboratoryInvestigations);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Error while validating Laboratory Investigations. \n" + e);
            }

            //Validate Diagnosis Tab
            try {
                String sProvisionalDiagnosisComment = "Demo Provisional Diagnosis Comment";
//                String sCommonlyUsedDiagnosisToSelect = "Glaucoma";
//                String sListToSelect = "Steroid responder";
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_DiagnosisTab.tab_diagnosis), "Diagnosis Tab Is selected");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_DiagnosisTab.checkbox_provisionalDiagnosis), "Provisional diagnosis checkbox is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_DiagnosisTab.input_provisionalDiagnosisComments, sProvisionalDiagnosisComment), "Validate provisional diagnosis Comment is entered as Smoke Test");
//                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_EyeTemplate.select_commonlyUsedDiagnosisUnderDiagnosis, sCommonlyUsedDiagnosisToSelect), sCommonlyUsedDiagnosisToSelect + " option is selected under Commonly Used Diagnosis Sets");
//                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_EyeTemplate.select_listUnderDiagnosis, sListToSelect), sListToSelect + " option is selected under Commonly Used Diagnosis Sets");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Error while validating Diagnosis. \n" + e);
            }

            // Validate Advice Tab
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.tab_advice), "Advice Tab Is selected");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_AdviceTab.checkbox_noMedicationAdvised), "No Medication Advice check box is clicked");
                Cls_Generic_Methods.customWait();
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Error while validating Advice. \n" + e);
            }

            // Validate Procedures Tab
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.tab_proceduresUnderAdviceTab), "Procedures Tab Is selected");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_AdviceTab.checkbox_noProceduresAdvised), "No Procedures check box is checked");
                Cls_Generic_Methods.customWait();
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Error while validating Procedures. \n" + e);
            }

            // Validate Referral Tab
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.tab_referralUnderAdviceTab), "Referral Tab Is selected");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Error while validating Referral. \n" + e);
            }

            // Validate Advice Tab
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.tab_adviceUnderAdviceTab), "Advice Tab Is selected");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Error while validating Advice. \n" + e);
            }

            //Click On Save Button
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Save button is clicked");
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }

            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OPD.text_headerOPDSummary, 15), "Upon clicking Save template, opd summary is displayed");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");

            //After Close wait till user drop down display
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_EyeTemplate.text_sendToDepartmentSection, 15), "Upon clicking close template send to user dropdown is displayed");

        } catch (Exception e) {
            m_assert.assertTrue(false, "<b>Eye Template is not selected. </b> " + e);
            e.printStackTrace();
        }

    }

    @Test(enabled = true, description = "Send Patient to Counsellor")
    public void validateSendPatientFromDoctorToCounsellor() {
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);

        String tabToSelect = "My Queue";
        myPatient = map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();

        String destinationCounsellorName = "HG Counsellor";
        boolean requiredCounsellorFound = false;
        int myQueueInitialCount = -1, updatedMyQueueCount = -1, requiredCounsellorIndex = -1;

        try {

            if (!driver.getTitle().equals(oEHR_Data.sEXPECTED_TITLE)) {
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
            }

            m_assert.assertInfo(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
                    "Validate " + tabToSelect + " tab is selected");

            String myQueueTab = "My Queue";

            myQueueInitialCount = CommonActions.getCountOfPatientsInTab(oPage_OPD.tabs_appointmentTabsOnHomepage,
                    myQueueTab);

            for (WebElement patient : oPage_OPD.rows_patientAppointments) {

                if (patient.isDisplayed()) {

                    List<WebElement> patientDetailsOnRow = patient.findElements(By.xpath("./child::*"));
                    String patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");
                    if (concatPatientFullName.equals(patientName.trim())) {
                        Cls_Generic_Methods.clickElement(driver, patient);
                        m_assert.assertInfo(true, "Validate " + concatPatientFullName + " patient is selected");
                        break;
                    }
                }
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 16);

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_sendToCounsellor), "Send to Counsellor clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.listButtons_sendToCounsellor.get(0), 8);

            for (WebElement counsellorRow : oPage_PatientAppointmentDetails.listButtons_sendToCounsellor) {
                if (counsellorRow.isDisplayed()) {

                    List<WebElement> counsellorDetails = counsellorRow.findElements(By.xpath("./child::*"));
                    WebElement eCounsellorName = counsellorDetails.get(1);

                    if (Cls_Generic_Methods.getTextInElement(eCounsellorName).equals(destinationCounsellorName)) {
                        requiredCounsellorIndex = oPage_PatientAppointmentDetails.listButtons_sendToCounsellor.indexOf(counsellorRow);
                        requiredCounsellorFound = true;
                        break;
                    }
                }
            }

            m_assert.assertInfo(requiredCounsellorFound, "Validate " + destinationCounsellorName + " User is found.");
            Cls_Generic_Methods.customWait(4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.listButtons_sendToCounsellor.get(requiredCounsellorIndex)),
                    "<b>" + destinationCounsellorName + "</b> is clicked");
            Cls_Generic_Methods.customWait(5);

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_OPD.rows_patientAppointments, 12);

            updatedMyQueueCount = CommonActions.getCountOfPatientsInTab(oPage_OPD.tabs_appointmentTabsOnHomepage, myQueueTab);

            if (updatedMyQueueCount == (myQueueInitialCount - 1)) {
                m_assert.assertTrue("Validate Patient Count has been decreased from <b>" + myQueueInitialCount
                        + "</b> to <b>" + updatedMyQueueCount + "</b> in MY QUEUE tab for current user.");
            } else if (updatedMyQueueCount < myQueueInitialCount) {
                m_assert.assertWarn("Validate Patient Count has been decreased from <b>" + myQueueInitialCount
                        + "</b> to <b>" + updatedMyQueueCount + "</b> in MY QUEUE tab for current user.");
            } else {
                m_assert.assertTrue(false, "Validate Patient Count has been decreased from <b>" + myQueueInitialCount
                        + "</b> to <b>" + (myQueueInitialCount - 1) + "</b> in MY QUEUE tab for current user. Actual = " + updatedMyQueueCount);
            }

        } catch (Exception e) {
            m_assert.assertFatal("<b>Not</b> able to send the patient to Counsellor. \n" + e);
            e.printStackTrace();
        }

    }

}
