package tests.departments.opd.template;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.Settings_Data;
import data.Template_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.TemplateCommonActions;
import pages.commonElements.common_tabs.Page_ExaminationTab;
import pages.commonElements.common_tabs.Page_HistoryTab;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_PatientSettings;
import java.util.ArrayList;
import java.util.List;
public class TemplateTest extends TestBase {
    Page_OPD oPage_OPD;
    Page_HistoryTab oPage_HistoryTab;
    Template_Data oTemplate_Data;
    Page_CommonElements oPage_CommonElements;
    static Page_ExaminationTab oPage_ExaminationTab;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_NewPatientRegisteration oPage_NewPatientRegisteration;
    Page_PatientSettings oPage_PatientSettings;
    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    String defaultPatientName = "TEMPLATE TEST";
    boolean sFILLED_OPTOMETRIST_TEMPLATE=false;
    @BeforeMethod
    private void selectPatientFromList() {
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        oPage_PatientSettings = new Page_PatientSettings(driver);
        ArrayList<String> checkedMandatoryFields = new ArrayList<>();
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String patientName = null;
        boolean bPatientNameFound = false;
        String expectedLoggedInUser = "PR.Akash test";
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
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
                        } else if (defaultPatientName.equals(patientName.trim())) {
                            bPatientNameFound = true;
                            Cls_Generic_Methods.clickElement(driver, patient);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
                            break;
                        }
                    }
                }
                if (!bPatientNameFound) {
                    try {
                        //To find out selected mandatory field in Organisation setting
                        m_assert.assertInfo("Patient name -"+concatPatientFullName+" is not present in My Queue");
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
                            e1.printStackTrace();
                        }
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.select_salutationForPatient, 10);
                        for (String fieldName :
                                checkedMandatoryFields) {
                            switch (fieldName) {
                                case "First Name" ->
                                        Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm,
                                                "TEMPLATE TEST");
                                case "Mobile Number" -> Cls_Generic_Methods.sendKeysIntoElement(
                                        oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                                        "9876543210");
                                case "Gender" -> Cls_Generic_Methods.clickElementByJS(driver,
                                        oPage_NewPatientRegisteration.radio_gender_Male);
                                case "Address" ->
                                        Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_pincodeOnPatientRegForm, "600000" + Keys.DOWN + Keys.ENTER);
                                case "Age", "Age Month" ->
                                        Cls_Generic_Methods.sendKeysByJS(driver, oPage_NewPatientRegisteration.input_patientDob, "01/01/2000");
                                case "Referral Source" ->
                                        Cls_Generic_Methods.selectElementByIndex(oPage_NewPatientRegisteration.select_patientReferralSrcOnPatientRegForm, 1);
                            }
                        }
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
                        m_assert.assertInfo("Default Patient -TEMPLATE TEST is selected");
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("Unable to create new patient");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Unable to select patient " + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }
    @Test(enabled = true, description = "Validate and fill Eye Template")
    public void validateAndFillEyeTemplate() {
        oPage_OPD = new Page_OPD(driver);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oTemplate_Data = new Template_Data();
        oPage_CommonElements = new Page_CommonElements(driver);
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 20);
            Template_Data.sTODAY_FILLED_EYE_TEMPLATE = TemplateCommonActions.validateFilledTodayTemplate("EYE");
            sFILLED_OPTOMETRIST_TEMPLATE = TemplateCommonActions.validateFilledTodayTemplate("OPTOMETRIST");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, "Eye"), "<b>Eye</b> template  is selected");
            if(!sFILLED_OPTOMETRIST_TEMPLATE) {
                TemplateCommonActions.validateAndFillHistoryTab();
                TemplateCommonActions.validateAndFillRefractionTab();
                TemplateCommonActions.validateAndFillExaminationTab();
            }
            TemplateCommonActions.validateAndFillInvestigation();
            TemplateCommonActions.validateAndFillDiagnosis();
            TemplateCommonActions.validateAndFillAdvise();
            //To save
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Save button is clicked");
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OPD.text_headerOPDSummary, 15), "Upon clicking Save template, opd summary is displayed");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            m_assert.assertTrue(TemplateCommonActions.validateFilledTodayTemplate("EYE"), "Validated --> Eye template saved in today's template");
        } catch (Exception e) {
            m_assert.assertFatal("Unable To validate Eye Template" + e);
        }
    }
    @Test(enabled = true, description = "Validate and fill Optometrist Template")
    public void validateAndFillOptometristTemplate() {
        oPage_OPD = new Page_OPD(driver);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oTemplate_Data = new Template_Data();
        oPage_CommonElements = new Page_CommonElements(driver);
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            Template_Data.sTODAY_FILLED_EYE_TEMPLATE = TemplateCommonActions.validateFilledTodayTemplate("EYE");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, "Optometrist"), "<b>Optometrist</b> template  is selected");
            TemplateCommonActions.validateAndFillHistoryTab();
            TemplateCommonActions.validateAndFillRefractionTab();
            TemplateCommonActions.validateAndFillExaminationTab();
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Save button is clicked");
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OPD.text_headerOPDSummary, 15), "Upon clicking Save template, opd summary is displayed");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            m_assert.assertTrue(TemplateCommonActions.validateFilledTodayTemplate("OPTOMETRIST"), "Validated --> LENS template saved in today's template");
        } catch (Exception e) {
            m_assert.assertFatal("Unable To validate Optometrist Template" + e);
        }
    }
    @Test(enabled = true, description = "Validate and fill Lens Template")
    public void validateAndFillLensTemplate() {
        oPage_OPD = new Page_OPD(driver);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oTemplate_Data = new Template_Data();
        oPage_CommonElements = new Page_CommonElements(driver);
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            Template_Data.sTODAY_FILLED_EYE_TEMPLATE = TemplateCommonActions.validateFilledTodayTemplate();
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, "Lens"), "<b>Lens</b> template  is selected");
            TemplateCommonActions.validateAndFillHistoryTab();
            TemplateCommonActions.validateAndFillRefractionTab();
            TemplateCommonActions.validateAndFillExaminationTab();
            TemplateCommonActions.validateAndFillInvestigation();
            TemplateCommonActions.validateAndFillDiagnosis();
            TemplateCommonActions.validateAndFillAdvise();
            //To save
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Save button is clicked");
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OPD.text_headerOPDSummary, 15), "Upon clicking Save template, opd summary is displayed");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            m_assert.assertTrue(TemplateCommonActions.validateFilledTodayTemplate("LENS"), "Validated --> LENS template saved in today's template");
        } catch (Exception e) {
            m_assert.assertFatal("Unable To validate Lens Template" + e);
        }
    }
    @Test(enabled = true, description = "Validate and fill PMT Template")
    public void validateAndFillPMTTemplate() {
        oPage_OPD = new Page_OPD(driver);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oTemplate_Data = new Template_Data();
        oPage_CommonElements = new Page_CommonElements(driver);
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            Template_Data.sTODAY_FILLED_EYE_TEMPLATE = TemplateCommonActions.validateFilledTodayTemplate("EYE");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, "PMT"), "<b>PMT</b> template  is selected");
            TemplateCommonActions.validateAndFillRefractionTab();
            TemplateCommonActions.validateAndFillExaminationTab();
            TemplateCommonActions.validateAndFillDiagnosis();
            TemplateCommonActions.validateAndFillAdvise();
            //To save
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Save button is clicked");
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OPD.text_headerOPDSummary, 15), "Upon clicking Save template, opd summary is displayed");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            m_assert.assertTrue(TemplateCommonActions.validateFilledTodayTemplate("PMT"), "Validated --> PMT template saved in today's template");
        } catch (Exception e) {
            m_assert.assertFatal("Unable To validate PMT Template" + e);
        }
    }
    @Test(enabled = true, description = "Validate and fill Post Op Template")
    public void validateAndFillPostOpTemplate() {
        oPage_OPD = new Page_OPD(driver);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oTemplate_Data = new Template_Data();
        oPage_CommonElements = new Page_CommonElements(driver);
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            Template_Data.sTODAY_FILLED_EYE_TEMPLATE = TemplateCommonActions.validateFilledTodayTemplate("EYE");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, "Post OP"), "<b>Post OP</b> template  is selected");
            if(!Template_Data.sTODAY_FILLED_EYE_TEMPLATE) {
                TemplateCommonActions.validateAndFillRefractionTab();
                TemplateCommonActions.validateAndFillExaminationTab();
                TemplateCommonActions.validateAndFillInvestigation();
                TemplateCommonActions.validateAndFillDiagnosis();
                TemplateCommonActions.validateAndFillAdvise();
            }
            //To save
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Save button is clicked");
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OPD.text_headerOPDSummary, 15), "Upon clicking Save template, opd summary is displayed");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            m_assert.assertTrue(TemplateCommonActions.validateFilledTodayTemplate("Postop"), "Validated --> POST OP template saved in today's template");
        } catch (Exception e) {
            m_assert.assertFatal("Unable To validate POST OP Template" + e);
        }
    }
    @Test(enabled = true, description = "Validate and fill Quick Eye Template")
    public void validateAndFillQuickEyeTemplate() {
        oPage_OPD = new Page_OPD(driver);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oTemplate_Data = new Template_Data();
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_ExaminationTab = new Page_ExaminationTab(driver);
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            Template_Data.sTODAY_FILLED_EYE_TEMPLATE = TemplateCommonActions.validateFilledTodayTemplate("EYE");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, "Quick Eye"), "<b>Quick Eye</b> template  is selected");
            if(!Template_Data.sTODAY_FILLED_EYE_TEMPLATE) {
                TemplateCommonActions.validateAndFillHistoryTab();
                TemplateCommonActions.validateAndFillRefractionTab();
            }
            //Examination-QUICK EYE
            try {
                try {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ExaminationTab.tab_ExaminationTab),
                            "Examination Tab is clicked");
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to click Examination Tab");
                }
                for (WebElement inputExamination : oPage_ExaminationTab.list_inputQuickEyeExamination) {
                    String inputValue = Cls_Generic_Methods.getTextInElement(inputExamination);
                    if (!inputValue.isEmpty()) {
                        m_assert.assertFatal(TemplateCommonActions.findLeftOrRightFieldName(inputExamination) + " is not empty by default -->Value=" + inputValue);
                    }
                }
                for (WebElement btnNormal : oPage_ExaminationTab.list_buttonQuickEyeShortcut_Normal) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(btnNormal), "Clicked Normal Button in shortcut");
                }
                Cls_Generic_Methods.customWait();
                for (WebElement inputExamination : oPage_ExaminationTab.list_inputQuickEyeExamination) {
                    String inputValue = Cls_Generic_Methods.getTextInElement(inputExamination);
                    m_assert.assertEquals(inputValue, "Normal", "Validated normal -->Entered Normal in " + TemplateCommonActions.findLeftOrRightFieldName(inputExamination));
                }
                for (WebElement btnResetAll : oPage_ExaminationTab.list_resetAllButton) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(btnResetAll), "Clicked Reset All Button in Examination Tab");
                    driver.switchTo().alert().accept();
                }
                for (WebElement inputExamination : oPage_ExaminationTab.list_inputQuickEyeExamination) {
                    String inputValue = Cls_Generic_Methods.getTextInElement(inputExamination);
                    if (!inputValue.isEmpty()) {
                        m_assert.assertFatal(TemplateCommonActions.findLeftOrRightFieldName(inputExamination) + " is not empty --> Unable to validate ResetAll ");
                    }
                }
                for (WebElement diagramBtn : oPage_ExaminationTab.list_BtnAppendagesDiagram) {
                    TemplateCommonActions.clickAndValidateDiagram(diagramBtn);
                }
                for (WebElement diagramBtn : oPage_ExaminationTab.list_BtnCorneaDiagram) {
                    TemplateCommonActions.clickAndValidateDiagram(diagramBtn);
                }
                for (WebElement diagramBtn : oPage_ExaminationTab.list_BtnCorneaSlitDiagram) {
                    TemplateCommonActions.clickAndValidateDiagram(diagramBtn);
                }
                for (WebElement diagramBtn : oPage_ExaminationTab.list_BtnFundusDiagram) {
                    TemplateCommonActions.clickAndValidateDiagram(diagramBtn);
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Unable to validate Examination tab" + e);
            }
            TemplateCommonActions.validateAndFillInvestigation();
            TemplateCommonActions.validateAndFillDiagnosis();
            TemplateCommonActions.validateAndFillAdvise();
            //To save
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Save button is clicked");
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OPD.text_headerOPDSummary, 15), "Upon clicking Save template, opd summary is displayed");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            m_assert.assertTrue(TemplateCommonActions.validateFilledTodayTemplate("QUICKEYE"), "Validated --> Quick Eye template saved in today's template");
        } catch (Exception e) {
            m_assert.assertFatal("Unable To validate Quick Eye Template" + e);
        }
    }
    @Test(enabled = true, description = "Validate and fill Paediatrics Template")
    public void validateAndFillPaediatricsTemplate() {
        oPage_OPD = new Page_OPD(driver);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oTemplate_Data = new Template_Data();
        oPage_CommonElements = new Page_CommonElements(driver);
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            Template_Data.sTODAY_FILLED_EYE_TEMPLATE = TemplateCommonActions.validateFilledTodayTemplate("EYE");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, "Paediatrics"), "<b>Paediatrics</b> template  is selected");
            if(!Template_Data.sTODAY_FILLED_EYE_TEMPLATE) {
                TemplateCommonActions.validateAndFillHistoryTab();
                TemplateCommonActions.validateAndFillRefractionTab();
                TemplateCommonActions.validateAndFillExaminationTab();
            }
            TemplateCommonActions.validateAndFillInvestigation();
            TemplateCommonActions.validateAndFillDiagnosis();
            TemplateCommonActions.validateAndFillAdvise();
            //To save
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Save button is clicked");
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OPD.text_headerOPDSummary, 15), "Upon clicking Save template, opd summary is displayed");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            m_assert.assertTrue(TemplateCommonActions.validateFilledTodayTemplate("Paediatrics"), "Validated --> Paediatrics template saved in today's template");
        } catch (Exception e) {
            m_assert.assertFatal("Unable To validate Paediatrics Template" + e);
        }
    }
    @Test(enabled = true, description = "Validate and fill Orthoptics Template")
    public void validateAndFillOrthopticsTemplate() {
        oPage_OPD = new Page_OPD(driver);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oTemplate_Data = new Template_Data();
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_ExaminationTab = new Page_ExaminationTab(driver);
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            Template_Data.sTODAY_FILLED_EYE_TEMPLATE = TemplateCommonActions.validateFilledTodayTemplate("EYE");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, "Orthoptics"), "<b>Orthoptics</b> template  is selected");
            TemplateCommonActions.validateAndFillHistoryTab();
            TemplateCommonActions.validateAndFillRefractionTab();
            //EXAMINATION
            try {
                try {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ExaminationTab.tab_ExaminationTab),
                            "Examination Tab is clicked");
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to click Examination Tab");
                }
                validateListOfButtons(oPage_ExaminationTab.list_btnHeadPosture_orthoptics);
                validateListOfButtons(oPage_ExaminationTab.list_btnHeadTilt_orthoptics);
                validateListOfButtons(oPage_ExaminationTab.list_btnHirschbergTest_orthoptics);
                validateAndFill_ListOfInput(oPage_ExaminationTab.list_inputCoverTest_orthoptics, "1");
                validateListOfButtons(oPage_ExaminationTab.list_btnPatterns_orthoptics);
                validateListOfButtons(oPage_ExaminationTab.list_btnPrismBarCoverTest_orthoptics);
                validateListOfButtons(oPage_ExaminationTab.list_btnPrismBarReflexTest_orthoptics);
                validateListOfSelectDropdown(oPage_ExaminationTab.select_prismBarTest_orthoptics);
                validateListOfSelectDropdown(oPage_ExaminationTab.list_selectOcularMovement_orthoptics);
                validateAndFill_ListOfInput(oPage_ExaminationTab.list_inputNystagmus_orthoptics, "2");
                validateAndFill_ListOfInput(oPage_ExaminationTab.list_inputMaddoxTest_orthoptics, "8");
                validateAndFill_ListOfInput(oPage_ExaminationTab.list_inputWorthFourDotTest_orthoptics, "2");
                validateAndFill_ListOfInput(oPage_ExaminationTab.list_inputStereopsis_orthoptics, "1");
                validateAndFill_ListOfInput(oPage_ExaminationTab.list_inputHessScreen_orthoptics, "9");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Unable to validate examination" + e);
            }
            TemplateCommonActions.validateAndFillInvestigation();
            TemplateCommonActions.validateAndFillDiagnosis();
            TemplateCommonActions.validateAndFillAdvise();
            //To save
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Save button is clicked");
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OPD.text_headerOPDSummary, 15), "Upon clicking Save template, opd summary is displayed");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            m_assert.assertTrue(TemplateCommonActions.validateFilledTodayTemplate("Orthoptics"), "Validated --> Orthoptics template saved in today's template");
        } catch (Exception e) {
            m_assert.assertFatal("Unable To validate Orthoptics Template" + e);
        }
    }
    @Test(enabled = true, description = "Validate and fill Express Template")
    public void validateAndFillExpressTemplate() {
        oPage_OPD = new Page_OPD(driver);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oTemplate_Data = new Template_Data();
        oPage_CommonElements = new Page_CommonElements(driver);
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            Template_Data.sTODAY_FILLED_EYE_TEMPLATE = TemplateCommonActions.validateFilledTodayTemplate("EYE");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, "Express"), "<b>Express</b> template  is selected");
            TemplateCommonActions.validateAndFillClinicalDetailsAssessment();
            TemplateCommonActions.validateAndFillAdvise();
            //To save
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Save button is clicked");
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OPD.text_headerOPDSummary, 15), "Upon clicking Save template, opd summary is displayed");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            m_assert.assertTrue(TemplateCommonActions.validateFilledTodayTemplate("Express"), "Validated --> Express template saved in today's template");
        } catch (Exception e) {
            m_assert.assertFatal("Unable To validate Express Template" + e);
        }
    }
    @Test(enabled = true, description = "Validate and fill Trauma Template")
    public void validateAndFillTraumaTemplate() {
        oPage_OPD = new Page_OPD(driver);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oTemplate_Data = new Template_Data();
        oPage_CommonElements = new Page_CommonElements(driver);
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            Template_Data.sTODAY_FILLED_EYE_TEMPLATE = TemplateCommonActions.validateFilledTodayTemplate("EYE");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, "Trauma"), "<b>Trauma</b> template  is selected");
            TemplateCommonActions.validateAndFillHistoryTab();
            TemplateCommonActions.validateAndFillRefractionTab();
            TemplateCommonActions.validateAndFillExaminationTab();
            TemplateCommonActions.validateAndFillInvestigation();
            TemplateCommonActions.validateAndFillDiagnosis();
            TemplateCommonActions.validateAndFillAdvise();
            //To save
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Save button is clicked");
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OPD.text_headerOPDSummary, 15), "Upon clicking Save template, opd summary is displayed");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            m_assert.assertTrue(TemplateCommonActions.validateFilledTodayTemplate("Trauma"), "Validated --> Trauma template saved in today's template");
        } catch (Exception e) {
            m_assert.assertFatal("Unable To validate Trauma Template" + e);
        }
    }
    @Test(enabled = true, description = "Validate and fill Free Form Template")
    public void validateAndFillFreeFormTemplate() {
        oPage_OPD = new Page_OPD(driver);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oTemplate_Data = new Template_Data();
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_ExaminationTab = new Page_ExaminationTab(driver);
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            Template_Data.sTODAY_FILLED_EYE_TEMPLATE = TemplateCommonActions.validateFilledTodayTemplate("EYE");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, "Free Form"), "<b>Free Form</b> template  is selected");
            //History
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.list_inputHistoryFreeForm, oTemplate_Data.sCOMMENT_EXAMINATION), "Entered " + oTemplate_Data.sCOMMENT_EXAMINATION + " in Free form History Evaluation");
            if(!Template_Data.sTODAY_FILLED_EYE_TEMPLATE) {
                TemplateCommonActions.validateAndFillRefractionTab();
            }
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ExaminationTab.tab_ExaminationTab),
                    "Examination Tab is clicked");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ExaminationTab.input_examinationComments_FreeForm, oTemplate_Data.sCOMMENT_EXAMINATION), "Entered " + oTemplate_Data.sCOMMENT_EXAMINATION + " in Free form Examination Evaluation");
            TemplateCommonActions.validateAndFillInvestigation();
            TemplateCommonActions.validateAndFillDiagnosis();
            TemplateCommonActions.validateAndFillAdvise();
            //To save
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Save button is clicked");
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OPD.text_headerOPDSummary, 15), "Upon clicking Save template, opd summary is displayed");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            m_assert.assertTrue(TemplateCommonActions.validateFilledTodayTemplate("Freeform"), "Validated --> Free Form template saved in today's template");
        } catch (Exception e) {
            m_assert.assertFatal("Unable To validate Free Form Template" + e);
        }
    }
    @Test(enabled = true, description = "Validate and fill Nursing Template")
    public void validateAndFillNursingTemplate() {
        oPage_OPD = new Page_OPD(driver);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oTemplate_Data = new Template_Data();
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_ExaminationTab = new Page_ExaminationTab(driver);
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            Template_Data.sTODAY_FILLED_EYE_TEMPLATE = TemplateCommonActions.validateFilledTodayTemplate("EYE");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, "Nursing"), "<b>Nursing</b> template  is selected");
            //History
            if (!Template_Data.sTODAY_FILLED_EYE_TEMPLATE) {
                TemplateCommonActions.validateAndFillHistoryTab();
            } else {
                for (WebElement selectSyringing : oPage_HistoryTab.list_selectSyringing_nursing) {
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(selectSyringing, 1), Cls_Generic_Methods.getSelectedValue(selectSyringing) + " is selected under " + TemplateCommonActions.findLeftOrRightFieldName(selectSyringing));
                }
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_randomBloodSugar, "120"), "Entered <b>120</b> in blood sugar result");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_randomBloodSugarComments, "TEST"), "Entered <b>TEST</b> in blood sugar comment");
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(oPage_HistoryTab.select_user_template, 1), Cls_Generic_Methods.getSelectedValue(oPage_HistoryTab.select_user_template) + " is selected as user");
            }
            //To save
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Save button is clicked");
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OPD.text_headerOPDSummary, 15), "Upon clicking Save template, opd summary is displayed");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            m_assert.assertTrue(TemplateCommonActions.validateFilledTodayTemplate("Nursing"), "Validated --> Nursing template saved in today's template");
        } catch (Exception e) {
            m_assert.assertFatal("Unable To validate Nursing Template" + e);
        }
    }
    @Test(enabled = true, description = "Validate and fill Vital Sign Template")
    public void validateAndFillVitalSignTemplate() {
        oPage_OPD = new Page_OPD(driver);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oTemplate_Data = new Template_Data();
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_ExaminationTab = new Page_ExaminationTab(driver);
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            Template_Data.sTODAY_FILLED_EYE_TEMPLATE = TemplateCommonActions.validateFilledTodayTemplate("EYE");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, "Vital Sign"), "<b>Vital Sign</b> template  is selected");
            TemplateCommonActions.validateVitalSign();
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(oPage_HistoryTab.select_user_template, 1), Cls_Generic_Methods.getSelectedValue(oPage_HistoryTab.select_user_template) + " is selected as user");
            //To save
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Save button is clicked");
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OPD.text_headerOPDSummary, 15), "Upon clicking Save template, opd summary is displayed");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            m_assert.assertTrue(TemplateCommonActions.validateFilledTodayTemplate("Vitalsign"), "Validated --> Vital Sign template saved in today's template");
        } catch (Exception e) {
            m_assert.assertFatal("Unable To validate Vital Sign Template" + e);
        }
    }
    @Test(enabled = true, description = "Validate and fill Biometry Template")
    public void validateAndFillBiometryTemplate() {
        oPage_OPD = new Page_OPD(driver);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oTemplate_Data = new Template_Data();
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_ExaminationTab = new Page_ExaminationTab(driver);
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            Template_Data.sTODAY_FILLED_EYE_TEMPLATE = TemplateCommonActions.validateFilledTodayTemplate("EYE");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, "Biometry"), "<b>Biometry</b> template  is selected");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_HistoryTab.list_inputBiometryTemplate, 10);
            validateAndFill_ListOfInput(oPage_HistoryTab.list_inputBiometryTemplate, "8");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(oPage_HistoryTab.select_user_template, 1), Cls_Generic_Methods.getSelectedValue(oPage_HistoryTab.select_user_template) + " is selected as user");
            //To save
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Save button is clicked");
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OPD.text_headerOPDSummary, 15), "Upon clicking Save template, opd summary is displayed");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            m_assert.assertTrue(TemplateCommonActions.validateFilledTodayTemplate("Biometry"), "Validated --> Biometry template saved in today's template");
        } catch (Exception e) {
            m_assert.assertFatal("Unable To validate Biometry Template" + e);
        }
    }
    
    private static void validateListOfButtons(List<WebElement> listOfBtn) {
        for (WebElement btn : listOfBtn) {
            try {
                String btnValue = Cls_Generic_Methods.getTextInElement(btn);
                if (!TemplateCommonActions.isButtonSelected(btn)) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(btn), "<b>" + btnValue + "</b> button in " + TemplateCommonActions.findLeftOrRightFieldName(btn) + " is clickable");
                } else {
                    m_assert.assertFatal(btnValue + " should not be selected as default value in " + TemplateCommonActions.findLeftOrRightFieldName(btn));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static void validateAndFill_ListOfInput(List<WebElement> listOfInputTextBox, String valueToBeEntered) {
        try {
            for (WebElement input : listOfInputTextBox) {
                String value = Cls_Generic_Methods.getElementAttribute(input, "value");
                if (value.isEmpty()) {
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(input, valueToBeEntered), "Entered <b>" + valueToBeEntered + "</b> in " + TemplateCommonActions.findLeftOrRightFieldName(input));
                    Cls_Generic_Methods.customWait(1 / 4);
                } else {
                    m_assert.assertFatal(value + " should not be entered as default value in " + TemplateCommonActions.findLeftOrRightFieldName(input));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void validateListOfSelectDropdown(List<WebElement> listOfSelectElement) {
        try {
            for (WebElement select : listOfSelectElement) {
                String actualValue = Cls_Generic_Methods.getSelectedValue(select);
                Cls_Generic_Methods.selectElementByIndex(select, 0);
                String expValue = Cls_Generic_Methods.getSelectedValue(select);
                if (actualValue.equals(expValue)) {
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(select, 1), "Selected <b>" + Cls_Generic_Methods.getSelectedValue(select) + "</b> in " + TemplateCommonActions.findLeftOrRightFieldName(select));
                } else {
                    m_assert.assertFatal(actualValue + " should not be selected as default value in " + TemplateCommonActions.findLeftOrRightFieldName(select));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}