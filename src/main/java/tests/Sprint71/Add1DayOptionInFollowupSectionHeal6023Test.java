package tests.Sprint71;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.Optometrist_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.Sprint71.Page_HEAL6023;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.common_tabs.Page_DiagnosisTab;
import pages.commonElements.common_tabs.Page_HistoryTab;
import pages.commonElements.common_tabs.Page_RefractionTab;
import pages.commonElements.common_tabs.advice.Page_AdviceTab;
import pages.commonElements.common_tabs.investigation.Page_InvestigationTab;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.templates.eye.Page_EyeTemplate;
import pages.opd.Page_OPD;
import pages.optometrist.Pages_Optometrist;

import java.util.List;

public class Add1DayOptionInFollowupSectionHeal6023Test extends TestBase {
    Page_EyeTemplate oPage_EyeTemplate;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Optometrist_Data oOptometrist_Data = new Optometrist_Data();
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
    Page_HEAL6023 oPage_HEAL6023;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    static Model_Patient myPatient;
    boolean runScriptInDebugMode = false;
    boolean bScriptDebugMode = false;


    @Test(enabled = true, description = "Validate Doctor Module")
    public void validatePatientArrivedInDoctor() {
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String patientName = null;
        boolean bPatientNameFound = false;

        String expectedLoggedInUser = "PR.Akash test";
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);

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
                m_assert.assertFatal(" " + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Fill Eye Template Data")
    public void createEyeTemplateToValidateOneDayOptionUnderFollowupDetails() {

        oPage_EyeTemplate = new Page_EyeTemplate(driver);
        oPages_Optometrist = new Pages_Optometrist(driver);
        oPage_Navbar = new Page_Navbar(driver);
        oPage_HEAL6023 = new Page_HEAL6023(driver);
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
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 6);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, EyeTemplate), "Validate " + EyeTemplate + " template  is selected");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.tab_RefractionTab, 20);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_HistoryTab.tab_HistoryTab, 20);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.tab_HistoryTab), "Validate clicked on History tab");
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_HistoryTab.checkbox_NIL_Chief_Complaints),
                    "Clicked on NIL Checkbox for Chief Complaints");
            Cls_Generic_Methods.customWait(1);
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_HistoryTab.checkbox_NIL_Ophthalmic_History),
                    "Clicked on NIL Checkbox for Ophthalmic History");
            Cls_Generic_Methods.customWait(1);
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_HistoryTab.checkbox_NIL_Systemic_History),
                    "Clicked on NIL Checkbox for Systemic History");
            Cls_Generic_Methods.customWait(1);
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_HistoryTab.checkbox_NIL_All_Allergies),
                    "Clicked on NIL Checkbox for All Allergies");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.tab_RefractionTab, 8);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_RefractionTab.tab_RefractionTab), "Validate clicked on Refraction tab");

            if (Cls_Generic_Methods.isElementDisplayed(oPage_RefractionTab.tab_RedColourInVisionTab)) {
                // Validating the R/OD Visual Acuity

                try {
                    for (WebElement buttonElement : oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityRight) {

                        int index = oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityRight.indexOf(buttonElement);
                        boolean validateButtonFunctionality = false;
                        validateButtonFunctionality = CommonActions.validateIf_EHR_ButtonIsClickable(buttonElement);
                        m_assert.assertInfo(validateButtonFunctionality, "Validate "
                                + oOptometrist_Data.list_UCVA_ABSENT.get(index) + " R/OD Button is Clickable");

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, buttonElement), "Validate "
                                + oOptometrist_Data.list_UCVA_ABSENT.get(index) + " R/OD Button is Clicked");
                        break;
                    }
                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_RefractionTab.input_ucvaCommentUnderVisualAcuityRightSide,
                                    oOptometrist_Data.sUCVA_COMMENT),
                            "Validate Visual Acuity for right eye Comment is entered as "
                                    + oOptometrist_Data.sUCVA_COMMENT);

                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_RefractionTab.input_CommentUnderVisualAcuityRightSide,
                                    oOptometrist_Data.sUCVA_COMMENT),
                            "Validate R/OD Visual Acuity Comment is entered as "
                                    + oOptometrist_Data.sVISUAL_ACUITY_COMMENT);

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to validate R/OD Section under visual acuity - \n" + e);
                }
                // Validating the L/OS Visual Acuity
                try {
                    for (WebElement buttonElement : oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityLeft) {

                        int index = oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityLeft.indexOf(buttonElement);

                        boolean validateButtonFunctionality = CommonActions.validateIf_EHR_ButtonIsClickable(buttonElement);

                        m_assert.assertTrue(validateButtonFunctionality, "Validate "
                                + oOptometrist_Data.list_UCVA_ABSENT.get(index) + " L/OS Button is Clickable");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, buttonElement), "Validate "
                                + oOptometrist_Data.list_UCVA_ABSENT.get(index) + " L/OS Button is Clicked");
                        break;
                    }
                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_RefractionTab.input_ucvaCommentUnderVisualAcuityLeftSide,
                                    oOptometrist_Data.sUCVA_COMMENT),
                            "Validate Visual Acuity for left eye Comment is entered as "
                                    + oOptometrist_Data.sUCVA_COMMENT);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_RefractionTab.input_CommentUnderVisualAcuityLefttSide,
                                    oOptometrist_Data.sUCVA_COMMENT),
                            "Validate L/OD Visual Acuity Comment is entered as "
                                    + oOptometrist_Data.sVISUAL_ACUITY_COMMENT);
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to validate L/OS side absent Section under visual acuity - \n" + e);
                }
            } else {
                m_assert.assertTrue(
                        Cls_Generic_Methods.isElementDisplayed(oPage_RefractionTab.tab_GreenColourInVisioTab),
                        "vision Tab is in Green colour");
            }

            try {

                Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_rightIop);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_rightIop,
                        myPatient.getsIOP_RIGHT()), "IOP Value filled for R/OD");

                Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_leftIop);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_leftIop,
                        myPatient.getsIOP_LEFT()), "IOP Value filled for L/OS");
                if (CommonActions.templateBadgeIsGreen(oPage_RefractionTab.status_iopBadge)) {
                    m_assert.assertTrue(true, "IOP fields are filled successfully, button is GREEN");
                } else if (CommonActions.templateBadgeIsRed(oPage_RefractionTab.status_iopBadge)) {
                    m_assert.assertTrue(false, "IOP fields are not filled, button is RED");
                }

            } catch (Exception e) {
                m_assert.assertTrue(false, "Unable to validate IOP Section Under Refraction \n" + e);
                e.printStackTrace();
            }

            if (bScriptDebugMode) {
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.tab_HistoryTab),
                            "clicked on History tab");
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_HistoryTab.tab_RedColourInHistoryTab)) {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_HistoryTab.button_OneChiefComplaints,
                                10);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.button_OneChiefComplaints),
                                " selected one of the chief complaints");
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_HistoryTab.button_OneOphthalmicHistory),
                                " selected one of the ophthalmic history");
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.button_SystemmicHistory),
                                " selected one of the systemic history");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.button_Allergy),
                                " selected one of the allergy");
                    } else {
                        m_assert.assertTrue(
                                Cls_Generic_Methods.isElementDisplayed(oPage_HistoryTab.tab_GreenColourInHistoryTab),
                                "vision Tab is in Green colour");
                    }
                } catch (Exception e) {
                    m_assert.assertTrue(false, "Unable to validate History Section Under Refraction " + e);
                    e.printStackTrace();
                }
            }
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
                m_assert.assertInfo(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_EyeTemplate.left_UCVA, 15), "Upon clicking on refraction tab left UCVA is displayed");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_EyeTemplate.check_left_UCVA), "Left UCVA Is selected as Nil");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_EyeTemplate.check_right_UCVA), "Right UCVA Is selected as Nil");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_EyeTemplate.check_left_IOP), "Left IOP Is selected as Nil");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_EyeTemplate.check_right_IOP), "Right IOP Is selected as Nil");
            }

            //Validate Investigation Tab

            // Ophthal Investigations
            try {
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_InvestigationTab.tab_investigation), "Investigation Tab Is selected");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_InvestigationTab.tab_ophthalUnderInvestigationTab), "Ophthal in Investigation Tab Is selected");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_InvestigationTab.select_ophthalSetsUnderInvestigations, 0),
                        Cls_Generic_Methods.getSelectedValue(oPage_InvestigationTab.select_ophthalSetsUnderInvestigations) + " option is selected under Ophthal Sets");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InvestigationTab.rows_selectedOphthalInvestigations.get(0), 16);
                int selectedOphthalInvestigations = oPage_InvestigationTab.rows_selectedOphthalInvestigations.size();
                m_assert.assertTrue(selectedOphthalInvestigations > 0, "Validate at least one Investigation is selected. Current count of Ophthal investigations = " + selectedOphthalInvestigations);
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
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InvestigationTab.rows_selectedLaboratoryInvestigations.get(0), 16);
                int selectedLaboratoryInvestigations = oPage_InvestigationTab.rows_selectedLaboratoryInvestigations.size();
                m_assert.assertTrue(selectedLaboratoryInvestigations > 0, "Validate at least one Investigation is selected. Current count of Laboratory investigations = " + selectedLaboratoryInvestigations);
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
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InvestigationTab.rows_selectedRadiologyInvestigations.get(0), 16);
                int selectedLaboratoryInvestigations = oPage_InvestigationTab.rows_selectedRadiologyInvestigations.size();
                m_assert.assertTrue(selectedLaboratoryInvestigations > 0, "Validate at least one Investigation is selected. Current count of Radiology investigations = " + selectedLaboratoryInvestigations);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Error while validating Laboratory Investigations. \n" + e);
            }

            //Validate Diagnosis Tab
            try {
                String sProvisionalDiagnosisComment = "Demo Provisional Diagnosis Comment";
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_DiagnosisTab.tab_diagnosis), "Diagnosis Tab Is selected");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_DiagnosisTab.checkbox_provisionalDiagnosis), "Provisional diagnosis checkbox is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_DiagnosisTab.input_provisionalDiagnosisComments, sProvisionalDiagnosisComment), "Validate provisional diagnosis Comment is entered as Smoke Test");

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

            // Validate 1Day option under followup section
            try {
                Cls_Generic_Methods.customWait(4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_HEAL6023.button_1DayOption), "clicked on 1 day option");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Error while validating followup section. \n" + e);
            }
            //Click On Save Button
            try {
                if (Cls_Generic_Methods.clickElement(oPage_CommonElements.button_SaveTemplate)) {
                    m_assert.assertTrue("Save button is clicked");
                } else {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_HEAL6023.button_RetrySaveTemplate), "Retry button is clicked to save template");
                }
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_HEAL6023.text_1DayTextDisplayingUnderFollowupDetailsInTempPreview, 6);
            String OneDayOption = Cls_Generic_Methods.getTextInElement(oPage_HEAL6023.text_1DayTextDisplayingUnderFollowupDetailsInTempPreview);
            if (OneDayOption.contains("1 Day")) {
                m_assert.assertTrue(true, "1 day option displaying in template preview");
            } else {
                m_assert.assertTrue(false, "1 day option is not displaying in template preview");
            }
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate,10);


        } catch (Exception e) {
            m_assert.assertTrue(false, "<b>Eye Template is not selected. </b> " + e);
            e.printStackTrace();
        }

    }

    @Test(enabled = true, description = "Fill Lens Template Data")
    public void createLensTemplateToValidateOneDayOptionUnderFollowupDetails() {

        oPage_EyeTemplate = new Page_EyeTemplate(driver);
        oPages_Optometrist = new Pages_Optometrist(driver);
        oPage_Navbar = new Page_Navbar(driver);
        oPage_HEAL6023 = new Page_HEAL6023(driver);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oPage_RefractionTab = new Page_RefractionTab(driver);
        oPage_AdviceTab = new Page_AdviceTab(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_DiagnosisTab = new Page_DiagnosisTab(driver);
        oPage_InvestigationTab = new Page_InvestigationTab(driver);
        String EyeTemplate = "Lens";
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);

        try {
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, EyeTemplate), "Validate " + EyeTemplate + " template  is selected");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.tab_RefractionTab, 20);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_HistoryTab.tab_HistoryTab, 20);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.tab_HistoryTab), "Validate clicked on History tab");

            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_HistoryTab.checkbox_NIL_Chief_Complaints),
                    "Clicked on NIL Checkbox for Chief Complaints");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.tab_RefractionTab, 8);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_RefractionTab.tab_RefractionTab), "Validate clicked on Refraction tab");

            if (Cls_Generic_Methods.isElementDisplayed(oPage_RefractionTab.tab_RedColourInVisionTab)) {
                // Validating the R/OD Visual Acuity

                try {
                    for (WebElement buttonElement : oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityRight) {

                        int index = oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityRight.indexOf(buttonElement);
                        boolean validateButtonFunctionality = false;
                        validateButtonFunctionality = CommonActions.validateIf_EHR_ButtonIsClickable(buttonElement);
                        m_assert.assertInfo(validateButtonFunctionality, "Validate "
                                + oOptometrist_Data.list_UCVA_ABSENT.get(index) + " R/OD Button is Clickable");

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, buttonElement), "Validate "
                                + oOptometrist_Data.list_UCVA_ABSENT.get(index) + " R/OD Button is Clicked");
                        break;
                    }
                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_RefractionTab.input_ucvaCommentUnderVisualAcuityRightSide,
                                    oOptometrist_Data.sUCVA_COMMENT),
                            "Validate Visual Acuity for right eye Comment is entered as "
                                    + oOptometrist_Data.sUCVA_COMMENT);

                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_RefractionTab.input_CommentUnderVisualAcuityRightSide,
                                    oOptometrist_Data.sUCVA_COMMENT),
                            "Validate R/OD Visual Acuity Comment is entered as "
                                    + oOptometrist_Data.sVISUAL_ACUITY_COMMENT);

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to validate R/OD Section under visual acuity - \n" + e);
                }
                // Validating the L/OS Visual Acuity
                try {
                    for (WebElement buttonElement : oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityLeft) {

                        int index = oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityLeft.indexOf(buttonElement);

                        boolean validateButtonFunctionality = CommonActions.validateIf_EHR_ButtonIsClickable(buttonElement);

                        m_assert.assertTrue(validateButtonFunctionality, "Validate "
                                + oOptometrist_Data.list_UCVA_ABSENT.get(index) + " L/OS Button is Clickable");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, buttonElement), "Validate "
                                + oOptometrist_Data.list_UCVA_ABSENT.get(index) + " L/OS Button is Clicked");
                        break;
                    }
                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_RefractionTab.input_ucvaCommentUnderVisualAcuityLeftSide,
                                    oOptometrist_Data.sUCVA_COMMENT),
                            "Validate Visual Acuity for left eye Comment is entered as "
                                    + oOptometrist_Data.sUCVA_COMMENT);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_RefractionTab.input_CommentUnderVisualAcuityLefttSide,
                                    oOptometrist_Data.sUCVA_COMMENT),
                            "Validate L/OD Visual Acuity Comment is entered as "
                                    + oOptometrist_Data.sVISUAL_ACUITY_COMMENT);
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to validate L/OS side absent Section under visual acuity - \n" + e);
                }
            } else {
                m_assert.assertTrue(
                        Cls_Generic_Methods.isElementDisplayed(oPage_RefractionTab.tab_GreenColourInVisioTab),
                        "vision Tab is in Green colour");
            }

            try {

                Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_rightIop);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_rightIop,
                        myPatient.getsIOP_RIGHT()), "IOP Value filled for R/OD");

                Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_leftIop);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_leftIop,
                        myPatient.getsIOP_LEFT()), "IOP Value filled for L/OS");
                if (CommonActions.templateBadgeIsGreen(oPage_RefractionTab.status_iopBadge)) {
                    m_assert.assertTrue(true, "IOP fields are filled successfully, button is GREEN");
                } else if (CommonActions.templateBadgeIsRed(oPage_RefractionTab.status_iopBadge)) {
                    m_assert.assertTrue(false, "IOP fields are not filled, button is RED");
                }

            } catch (Exception e) {
                m_assert.assertTrue(false, "Unable to validate IOP Section Under Refraction \n" + e);
                e.printStackTrace();
            }

            if (bScriptDebugMode) {
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.tab_HistoryTab),
                            "clicked on History tab");
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_HistoryTab.tab_RedColourInHistoryTab)) {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_HistoryTab.button_OneChiefComplaints,
                                10);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.button_OneChiefComplaints),
                                " selected one of the chief complaints");
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_HistoryTab.button_OneOphthalmicHistory),
                                " selected one of the ophthalmic history");
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.button_SystemmicHistory),
                                " selected one of the systemic history");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.button_Allergy),
                                " selected one of the allergy");
                    } else {
                        m_assert.assertTrue(
                                Cls_Generic_Methods.isElementDisplayed(oPage_HistoryTab.tab_GreenColourInHistoryTab),
                                "vision Tab is in Green colour");
                    }
                } catch (Exception e) {
                    m_assert.assertTrue(false, "Unable to validate History Section Under Refraction " + e);
                    e.printStackTrace();
                }
            }
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
                m_assert.assertInfo(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_EyeTemplate.left_UCVA, 15), "Upon clicking on refraction tab left UCVA is displayed");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_EyeTemplate.check_left_UCVA), "Left UCVA Is selected as Nil");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_EyeTemplate.check_right_UCVA), "Right UCVA Is selected as Nil");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_EyeTemplate.check_left_IOP), "Left IOP Is selected as Nil");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_EyeTemplate.check_right_IOP), "Right IOP Is selected as Nil");
            }

            //Validate Investigation Tab

            // Ophthal Investigations
            try {
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_InvestigationTab.tab_investigation), "Investigation Tab Is selected");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_InvestigationTab.tab_ophthalUnderInvestigationTab), "Ophthal in Investigation Tab Is selected");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_InvestigationTab.select_ophthalSetsUnderInvestigations, 0),
                        Cls_Generic_Methods.getSelectedValue(oPage_InvestigationTab.select_ophthalSetsUnderInvestigations) + " option is selected under Ophthal Sets");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InvestigationTab.rows_selectedOphthalInvestigations.get(0), 16);
                int selectedOphthalInvestigations = oPage_InvestigationTab.rows_selectedOphthalInvestigations.size();
                m_assert.assertTrue(selectedOphthalInvestigations > 0, "Validate at least one Investigation is selected. Current count of Ophthal investigations = " + selectedOphthalInvestigations);
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
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InvestigationTab.rows_selectedLaboratoryInvestigations.get(0), 16);
                int selectedLaboratoryInvestigations = oPage_InvestigationTab.rows_selectedLaboratoryInvestigations.size();
                m_assert.assertTrue(selectedLaboratoryInvestigations > 0, "Validate at least one Investigation is selected. Current count of Laboratory investigations = " + selectedLaboratoryInvestigations);
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
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InvestigationTab.rows_selectedRadiologyInvestigations.get(0), 16);
                int selectedLaboratoryInvestigations = oPage_InvestigationTab.rows_selectedRadiologyInvestigations.size();
                m_assert.assertTrue(selectedLaboratoryInvestigations > 0, "Validate at least one Investigation is selected. Current count of Radiology investigations = " + selectedLaboratoryInvestigations);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Error while validating Laboratory Investigations. \n" + e);
            }

            //Validate Diagnosis Tab
            try {
                String sProvisionalDiagnosisComment = "Demo Provisional Diagnosis Comment";
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_DiagnosisTab.tab_diagnosis), "Diagnosis Tab Is selected");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_DiagnosisTab.checkbox_provisionalDiagnosis), "Provisional diagnosis checkbox is clicked");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_DiagnosisTab.input_provisionalDiagnosisComments, sProvisionalDiagnosisComment), "Validate provisional diagnosis Comment is entered as Smoke Test");

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

            // Validate 1Day option under followup section
            try {
                Cls_Generic_Methods.customWait(4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_HEAL6023.button_1DayOption), "clicked on 1 day option");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Error while validating followup section. \n" + e);
            }
            Cls_Generic_Methods.customWait(3);
            //Click On Save Button
            try {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_CommonElements.button_SaveTemplate)) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_SaveTemplate), "Save button is clicked");
                } else {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_HEAL6023.button_RetrySaveTemplate), "Retry button is clicked to save template");
                }
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_HEAL6023.text_1DayTextDisplayingUnderFollowupDetailsInTempPreview, 6);
            String OneDayOption = Cls_Generic_Methods.getTextInElement(oPage_HEAL6023.text_1DayTextDisplayingUnderFollowupDetailsInTempPreview);
            if (OneDayOption.contains("1 Day")) {
                m_assert.assertTrue(true, "1 day option displaying in template preview");
            } else {
                m_assert.assertTrue(false, "1 day option is not displaying in template preview");
            }
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate,10);

        } catch (Exception e) {
            m_assert.assertTrue(false, "<b>Eye Template is not selected. </b> " + e);
            e.printStackTrace();
        }

    }


}
