package tests.Sprint88;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.Sprint88.Page_ShowMrnInInvestigationTemplateCreate;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.common_tabs.Page_DiagnosisTab;
import pages.commonElements.common_tabs.Page_HistoryTab;
import pages.commonElements.common_tabs.Page_RefractionTab;
import pages.commonElements.common_tabs.advice.Page_AdviceTab;
import pages.commonElements.common_tabs.investigation.Page_InvestigationTab;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.templates.eye.Page_EyeTemplate;
import pages.opd.Page_OPD;
import pages.optometrist.Pages_Optometrist;
import pages.settings.organisationSettings.general.Page_PatientSettings;

import java.util.ArrayList;
import java.util.List;

import static pages.commonElements.CommonActions.*;

public class ShowMrnInInvestigationTemplateCreateTest extends TestBase {

    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    static Model_Patient myPatient;

    String concatPatientFullName = "";

    String sMRNumber= CommonActions.getRandomUniqueString(6);

    ArrayList<String> checkedMandatoryFields= new ArrayList<>();

    boolean patientSelectedOPD = false;

    @Test(enabled = true, description = "Validate Essential Details are filled in Create New Patient")
    public void createPatientInOpd() {

        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            Page_PatientSettings oPage_PatientSettings = new Page_PatientSettings(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);


            try {
                //To find out selected mandatory field in Organisation setting
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Patient Settings");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientSettings.header_formValidationFields, 3);

                for (WebElement eCheckbox : oPage_PatientSettings.list_checkbox_formFieldNames) {
                    if ((eCheckbox.isSelected())) {
                        String checkedField = oPage_PatientSettings.list_formFieldNames
                                .get(oPage_PatientSettings.list_checkbox_formFieldNames.indexOf(eCheckbox)).getText().trim();
                        checkedMandatoryFields.add(checkedField);
                    }
                }

                m_assert.assertInfo("Selected mandatory field in Patient setting are "+
                        (checkedMandatoryFields.toString().replaceAll("\\[", "").replaceAll("\\]","")));

                CommonActions.selectDepartmentOnApp("OPD");

                // Open the Search/Add patient dialog box
                try {
                    if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                        CommonActions.openPatientRegisterationAndAppointmentForm();
                    } else {
                        CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                                "Patient Details");
                        Thread.sleep(2000);
                    }
                } catch (NoSuchElementException e1) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                }

                // Validate the tabs on Patient Registration Form
                if (oPage_NewPatientRegisteration.tabs_PatientRegForm
                        .size() != oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.size()) {
                    m_assert.assertTrue(false,
                            "No. of Tabs on Patient Reg. Form is "
                                    + oPage_NewPatientRegisteration.tabs_PatientRegForm.size() + ". Expected = "
                                    + oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.size());
                } else {

                    m_assert.assertTrue("No. of Tabs on Patient Reg. & Appointment Form is "
                            + oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.size());

                    if (!Cls_Generic_Methods
                            .getElementAttribute(oPage_NewPatientRegisteration.tabs_PatientRegForm.get(0), "class")
                            .equals("active")) {
                        m_assert.assertTrue(false, "Patient Details Tab is not selected on start by default.");
                    } else {
                        m_assert.assertTrue(true, "Patient Details Tab is selected on start by default.");

                        try {
                            for (int i = 0; i < oPage_NewPatientRegisteration.tabs_PatientRegForm.size(); i++) {

                                if (oPage_NewPatientRegisteration.tabs_PatientRegForm.get(i).getText().trim()
                                        .equals(oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.get(i))) {

                                    m_assert.assertInfo(oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.get(i)
                                            + " Tab is displayed on the form.");
                                } else {
                                    m_assert.assertTrue(false, oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.get(i)
                                            + " Tab is not displayed on the form.");
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("" + e);
                        }
                    }
                }

                m_assert.assertTrue(
                        Cls_Generic_Methods.waitForElementToBecomeVisible(
                                oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage, 5),
                        "Alert for compulsory field is visible by default on the empty form.");

                Cls_Generic_Methods.clickElementByAction(driver,
                        oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm);
                Thread.sleep(1000);

                // Validate the Compulsory Sections Message
                if (Cls_Generic_Methods
                        .getTextInElement(oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage).trim()
                        .equals(oEHR_Data.sCOMPULSORY_FIELDS_MESSAGE)) {
                    m_assert.assertTrue(true,
                            "Validate that the Compulsory sections message is " + oEHR_Data.sCOMPULSORY_FIELDS_MESSAGE);
                } else {
                    m_assert.assertTrue(false,
                            "Validate that the Compulsory sections message is " + oEHR_Data.sCOMPULSORY_FIELDS_MESSAGE);
                }

                // Validate the CSS of Compulsory Alert message
                if (Cls_Generic_Methods
                        .getElementAttribute(oPage_NewPatientRegisteration.subText_requiredFieldsAlertMessage, "style")
                        .equals(oEHR_Data.sSTYLE_OF_RED_ALERT_MSG_FOR_MANODATORY_FIELDS)) {
                    m_assert.assertTrue(true,
                            "Alert for compulsory field is highlighted in <b>Red</b> when trying to Create Appointment with empty form. Message = "
                                    + oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage.getText());
                } else {
                    m_assert.assertTrue(false,
                            "Alert for compulsory field is highlighted in <b>Red</b> when trying to Create Appointment with empty form.<br>"
                                    + "Expected = " + oEHR_Data.sSTYLE_OF_RED_ALERT_MSG_FOR_MANODATORY_FIELDS
                                    + "<br>Actual = " + Cls_Generic_Methods.getElementAttribute(
                                    oPage_NewPatientRegisteration.subText_requiredFieldsAlertMessage, "style"));
                }

                m_assert.assertTrue(oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage.getText().trim()
                        .contains("First Name"), "First Name is visible in the Compulsory Fields alert message.");

                m_assert.assertTrue(oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage.getText().trim()
                        .contains("Mobile Number"), "Mobile Number is visible in the Compulsory Fields alert message.");

                if(checkedMandatoryFields.size()>2) {

                    for (String fieldName :
                            checkedMandatoryFields) {
                        switch (fieldName) {
                            case "Gender" -> m_assert.assertTrue(oPage_NewPatientRegisteration.radio_gender_Male
                                    .getAttribute("class").contains("error"), "Alert for mandatory field is highlighted in " + fieldName + " field");
                            case "Address" ->
                                    m_assert.assertTrue(oPage_NewPatientRegisteration.input_pincodeOnPatientRegForm
                                            .getAttribute("class").contains("error"), "Alert for mandatory field is highlighted in " + fieldName + " field");
                            case "Age" -> m_assert.assertTrue(oPage_NewPatientRegisteration.text_patientAge
                                    .getAttribute("class").contains("error"), "Alert for mandatory field is highlighted in " + fieldName + " field");
                            case "Age Month" -> m_assert.assertTrue(oPage_NewPatientRegisteration.input_patientAgeMonth
                                    .getAttribute("class").contains("error"), "Alert for mandatory field is highlighted in " + fieldName + " field");
                            case "Referral Source" ->
                                    m_assert.assertTrue(oPage_NewPatientRegisteration.text_PatientReferralSourceErrorMsg
                                            .isDisplayed(), "Alert for mandatory field is highlighted in " + fieldName + " field");
                        }
                    }

                }

                // Entering Essential Form Data
                if (!myPatient.getsSALUTATION().isEmpty()) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                    oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()),
                            "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
                }

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                        "First Name is entered as - " + myPatient.getsFIRST_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_middleNameOnPatientRegForm, myPatient.getsMIDDLE_NAME()),
                        "Middle Name is entered as - " + myPatient.getsMIDDLE_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_lastNameOnPatientRegForm, myPatient.getsLAST_NAME()),
                        "Last Name is entered as - " + myPatient.getsLAST_NAME());

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                                myPatient.getsMOBILE_NUMBER()),
                        "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_medicalRecordNumOnPatientRegForm,
                                sMRNumber),
                        sMRNumber + " entered for Medical Report Number");

                // m_assert.assertTrue(
                //      Cls_Generic_Methods.sendKeysIntoElement(sMRNumber),
                //      " entered for Medical Report Number");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }
    @Test(enabled = true, description = "Click on Create Appointment button")
    public void clickOnCreateAppointment() {

        myPatient = map_PatientsInExcel.get(patientKey);

        try {
            oPage_Navbar = new Page_Navbar(driver);
            oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);

            try {
                try {
                    if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                        CommonActions.openPatientRegisterationAndAppointmentForm();
                    }

                } catch (NoSuchElementException e1) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                }

                m_assert.assertTrue(
                        Cls_Generic_Methods.clickElementByJS(driver,
                                oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm),
                        "Validate that Create Appointment button is clicked");

                Thread.sleep(4000);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 20);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Fill Investigation data in Eye Template")
    public void createEyeTemplateAdviceInvestigation () {

        Page_EyeTemplate oPage_EyeTemplate = new Page_EyeTemplate(driver);
        Pages_Optometrist oPages_Optometrist = new Pages_Optometrist(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_HistoryTab oPage_HistoryTab = new Page_HistoryTab(driver);
        Page_RefractionTab oPage_RefractionTab = new Page_RefractionTab(driver);
        Page_AdviceTab oPage_AdviceTab = new Page_AdviceTab(driver);
        Page_OPD  oPage_OPD = new Page_OPD(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_DiagnosisTab oPage_DiagnosisTab = new Page_DiagnosisTab(driver);
        Page_InvestigationTab oPage_InvestigationTab = new Page_InvestigationTab(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        String EyeTemplate = "Eye";
        Page_ShowMrnInInvestigationTemplateCreate oPage_ShowMrnInInvestigationTemplateCreate = new Page_ShowMrnInInvestigationTemplateCreate(driver);
        boolean bPatientNameFound = false;


        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        try {

            CommonActions.selectDepartmentOnApp("OPD");
            Cls_Generic_Methods.customWait(5);
            String MyQueueTab = "My Queue";
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            m_assert.assertTrue(
                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                    "Validate " + MyQueueTab + " tab is selected");
            Cls_Generic_Methods.customWait(10);

            for (WebElement patient : oPage_OPD.rows_patientAppointments) {
                if (patient.isDisplayed()) {
                    List<WebElement> patientDetailsOnRow = patient.findElements(By.xpath("./child::*"));
                    String patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");
                    Thread.sleep(1000);

                    if (concatPatientFullName.equals(patientName.trim())) {
                        Cls_Generic_Methods.clickElement(driver, patient);
                        bPatientNameFound = true;
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
                        break;
                    }
                }
            }

            m_assert.assertTrue(bPatientNameFound, "Validate Patient  " + concatPatientFullName + " is clicked in " + MyQueueTab + " of doctor");
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Exception while getting patient in doctor module " + e);
        }

        try {
            Cls_Generic_Methods.customWait(3);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, EyeTemplate), "Validate " + EyeTemplate + " template  is selected");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.tab_RefractionTab, 20);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_HistoryTab.tab_HistoryTab, 20);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.tab_HistoryTab), "Validate clicked on History tab");

            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_HistoryTab.checkbox_NIL_Chief_Complaints),
                    "Clicked on NIL Checkbox for Chief Complaints");
            Cls_Generic_Methods.customWait(2);
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_HistoryTab.checkbox_NIL_Ophthalmic_History),
                    "Clicked on NIL Checkbox for Ophthalmic History");
            Cls_Generic_Methods.customWait(2);
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_HistoryTab.checkbox_NIL_Systemic_History),
                    "Clicked on NIL Checkbox for Systemic History");
            Cls_Generic_Methods.customWait(2);
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_HistoryTab.checkbox_NIL_All_Allergies),
                    "Clicked on NIL Checkbox for All Allergies");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.tab_RefractionTab, 8);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_RefractionTab.tab_RefractionTab), "Validate clicked on Refraction tab");
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.clickElement(oPage_ShowMrnInInvestigationTemplateCreate.input_right_va);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.clickElement(oPage_ShowMrnInInvestigationTemplateCreate.input_left_va);
            Cls_Generic_Methods.clearValuesInElement(oPage_ShowMrnInInvestigationTemplateCreate.input_rightIopValue);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ShowMrnInInvestigationTemplateCreate.input_rightIopValue, myPatient.getsIOP_RIGHT()), "Right Eye intraocularPressure is " + myPatient.getsIOP_RIGHT());

            Cls_Generic_Methods.clearValuesInElement(oPage_ShowMrnInInvestigationTemplateCreate.input_leftIopValue);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ShowMrnInInvestigationTemplateCreate.input_leftIopValue, myPatient.getsIOP_LEFT()), "Left Eye intraocularPressure is " + myPatient.getsIOP_RIGHT());

            //Validate Investigation Tab

            // Ophthal Investigations
            try {

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_InvestigationTab.tab_investigation), "Investigation Tab Is selected");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_InvestigationTab.tab_ophthalUnderInvestigationTab), "Ophthal in Investigation Tab Is selected");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_InvestigationTab.select_ophthalSetsUnderInvestigations, 0), "Ophthalmology Investigations selected");

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
//
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
//
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Error while validating Laboratory Investigations. \n" + e);
            }

            //Validate Diagnosis Tab
            try {
                String sProvisionalDiagnosisComment = "Demo Provisional Diagnosis Comment";
//
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

    @Test(enabled = true, description = "Validating MRN In Investigation Template create form")
    public void validateMrnInInvestigationTemplateCreateForm() {
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_ShowMrnInInvestigationTemplateCreate oPage_ShowMrnInInvestigationTemplateCreate = new Page_ShowMrnInInvestigationTemplateCreate(driver);
        try  {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait();
            CommonActions.selectDepartmentOnApp("OPD");
            Cls_Generic_Methods.customWait(5);
            concatPatientFullName = CommonActions.getFullPatientName(myPatient).toUpperCase().trim();
            patientSelectedOPD = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
            if (patientSelectedOPD) {
                String sPatient_MRN;
                sPatient_MRN = Cls_Generic_Methods.getTextInElement(oPage_ShowMrnInInvestigationTemplateCreate.text_mrnOpdRhs);
                m_assert.assertTrue(!sPatient_MRN.isEmpty(),"Copied Patient MRN Number");

                //Opthal investigation MRN Validation
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ShowMrnInInvestigationTemplateCreate.button_patientRhsInvestigationTab),"Clicked On Investigation tab on Patient RHS");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ShowMrnInInvestigationTemplateCreate.button_OpthalInvestigationTab, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ShowMrnInInvestigationTemplateCreate.button_OpthalInvestigationTab),"clicked on opthalInvestigation tab");
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ShowMrnInInvestigationTemplateCreate.button_opthalInvestigationPerformedTab),"Clicked on performed investigation tab ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ShowMrnInInvestigationTemplateCreate.button_performedSave,3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ShowMrnInInvestigationTemplateCreate.button_performedSave),"Clicked on save button to perform investigation");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ShowMrnInInvestigationTemplateCreate.button_clickOnTemplate,3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ShowMrnInInvestigationTemplateCreate.button_clickOnTemplate),"Click on template to open the investigation template ");
                Cls_Generic_Methods.customWait();
                String sMRN_In_template = Cls_Generic_Methods.getTextInElement(oPage_ShowMrnInInvestigationTemplateCreate.text_mrnInTemplate);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ShowMrnInInvestigationTemplateCreate.button_createOpthalRecord),"record is created for the opthal investigation");
                Cls_Generic_Methods.clickElement(oPage_ShowMrnInInvestigationTemplateCreate.button_recordCloseTab);
                if (sMRN_In_template.contains(sPatient_MRN)){
                    m_assert.assertTrue(true, "Expected mrn is verified in opthalInvestigationTemplate "+sMRN_In_template);
                }
                else{
                    m_assert.assertFalse("expected mrn is not present in the template");
                }

                //Laboratory investigation MRN Validation
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ShowMrnInInvestigationTemplateCreate.button_labInvestigationTab),"Clicked on lab Investigation tab");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ShowMrnInInvestigationTemplateCreate.button_labInvestigationPerformedTab, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ShowMrnInInvestigationTemplateCreate.button_labInvestigationPerformedTab),"Clicked on performed investigation tab ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ShowMrnInInvestigationTemplateCreate.button_performedSave, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ShowMrnInInvestigationTemplateCreate.button_performedSave),"Clicked on save button to perform investigation");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ShowMrnInInvestigationTemplateCreate.button_labInvestigationClickOnTemplate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ShowMrnInInvestigationTemplateCreate.button_labInvestigationClickOnTemplate),"Click on template to open the investigation template ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ShowMrnInInvestigationTemplateCreate.text_mrnInLabInvestigationTemplate, 3);
                String sMRN_In_LabTemplate = Cls_Generic_Methods.getTextInElement(oPage_ShowMrnInInvestigationTemplateCreate.text_mrnInLabInvestigationTemplate);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ShowMrnInInvestigationTemplateCreate.button_createLabRecord),"record is created for the lab investigation");
                Cls_Generic_Methods.clickElement(oPage_ShowMrnInInvestigationTemplateCreate.button_recordCloseTab);
                if (sMRN_In_LabTemplate.contains(sPatient_MRN)){
                    m_assert.assertTrue(true, "Expected mrn is verified in laboratory Investigation template "+sMRN_In_LabTemplate);
                }
                else{
                    m_assert.assertFalse("expected mrn is not present in the template");
                }

                //Radiology investigation MRN Validation
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ShowMrnInInvestigationTemplateCreate.button_radInvestigationTab),"Clicked on Radiology investigation tab");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ShowMrnInInvestigationTemplateCreate.button_radInvestigationPerformedTab, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ShowMrnInInvestigationTemplateCreate.button_radInvestigationPerformedTab),"Clicked on performed investigation tab ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ShowMrnInInvestigationTemplateCreate.button_performedSave, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ShowMrnInInvestigationTemplateCreate.button_performedSave),"Clicked on save button to perform investigation");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ShowMrnInInvestigationTemplateCreate.button_radInvestigationClickOnTemplate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ShowMrnInInvestigationTemplateCreate.button_radInvestigationClickOnTemplate),"Click on template to open the investigation template ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ShowMrnInInvestigationTemplateCreate.text_mrnInRadInvestigationTemplate, 3);
                String sMRN_In_RadTemplate = Cls_Generic_Methods.getTextInElement(oPage_ShowMrnInInvestigationTemplateCreate.text_mrnInRadInvestigationTemplate);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ShowMrnInInvestigationTemplateCreate.button_createRadiologyRecord),"record is created for the lab investigation");
                Cls_Generic_Methods.clickElement(oPage_ShowMrnInInvestigationTemplateCreate.button_recordCloseTab);
                if (sMRN_In_RadTemplate.contains(sPatient_MRN)){
                    m_assert.assertTrue(true, "Expected mrn is verified in Radiology Investigation template "+sMRN_In_RadTemplate);
                }
                else{
                    m_assert.assertFalse("expected mrn is not present in the template");
                }
            }
            else{
                m_assert.assertTrue("Patient Not Found");
            }

        }catch (Exception e) {
            m_assert.assertTrue(false, "<b>Error while loading patient details </b> " + e);
            e.printStackTrace();
        }

    }
}
