package tests.Sprint88;

import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;

import data.EHR_Data;
import pages.Sprint88.Page_ShowMrnInInvestigationTemplateCreate;
import pages.commonElements.CommonActions;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.login.Page_Login;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_PatientSettings;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import static org.python.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static pages.commonElements.CommonActions.*;

public class CopyMrnAndPatientIdHeal7544 extends TestBase {
    static Model_Patient myPatient;
    String  patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    ArrayList<String> checkedMandatoryFields=new ArrayList<>();
    String sMRNumber = CommonActions.getRandomUniqueString(6);

    @Test(enabled = true, description = "Validate Essential Details are filled in Create New Patient")
    public void createPatientInOpd() {

        String expectedLoggedInUser =EHR_Data.user_PRAkashTest;
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
    @Test(enabled = true, description = "Validate copy option should visible on hover of MRN and patient id")
    public void validateCopyButtonOnHoverForMrnAndPatientId () throws Exception {
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_ShowMrnInInvestigationTemplateCreate oPage_ShowMrnInInvestigationTemplateCreate = new Page_ShowMrnInInvestigationTemplateCreate(driver);
        boolean bPatientNameFound = false;
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);

        try {
            String MyQueueTab = "My Queue";
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            Cls_Generic_Methods.customWait(7);
            m_assert.assertTrue(
                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                    "Validate " + MyQueueTab + " tab is selected");
           Cls_Generic_Methods.customWait(5);

            for (WebElement patient : oPage_OPD.rows_patientAppointments) {
                if (patient.isDisplayed()) {
                    List<WebElement> patientDetailsOnRow = patient.findElements(By.xpath("./child::*"));
                    String patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");


                    if (concatPatientFullName.equals(patientName.trim())) {
                        Cls_Generic_Methods.clickElement(driver, patient);
                        bPatientNameFound = true;
                        Cls_Generic_Methods.customWait(2);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
                        break;
                    }
                }
            }
            Thread.sleep(1000);
            m_assert.assertTrue(bPatientNameFound, "Validate Patient  " + concatPatientFullName + " is clicked in " + MyQueueTab + " of doctor");
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Exception while getting patient in doctor module " + e);
        }

        try{           // validating the copy option for patient id
                 Cls_Generic_Methods.customWait(4);
                 m_assert.assertTrue(CommonActions.MouseHoverAction(driver, oPage_ShowMrnInInvestigationTemplateCreate.text_patientIdOpdRhs
                    ), "Mouse hover action is performed on patient id ");
                 Cls_Generic_Methods.customWait(3);

                if (Cls_Generic_Methods.isElementDisplayed(oPage_ShowMrnInInvestigationTemplateCreate.button_copyPatientId)){
                    Cls_Generic_Methods.scrollToTop();
                    CommonActions.MouseHoverAction(driver, oPage_ShowMrnInInvestigationTemplateCreate.text_patientIdOpdRhs);
                    Cls_Generic_Methods.customWait(1);
                    Cls_Generic_Methods.clickElement(driver, oPage_ShowMrnInInvestigationTemplateCreate.button_copyPatientId);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    Transferable contents = clipboard.getContents(null);
                    String result = (String) contents.getTransferData(DataFlavor.stringFlavor);
                    m_assert.assertTrue("PatientId is copied of the patient"+concatPatientFullName+" in OPD RHS as :"+result);
                    Cls_Generic_Methods.customWait(3);
        }
                else{
                    m_assert.assertWarn("Expected PatientId Copy option is not clickable ");
                    Cls_Generic_Methods.customWait(2);
                }
            // validating the copy option for MRN of the patient in OPD RHS
            m_assert.assertTrue(CommonActions.MouseHoverAction(driver, oPage_ShowMrnInInvestigationTemplateCreate.text_mrnOpdRhs
            ), "Mouse hover action is performed on MRN of the patient ");
            Cls_Generic_Methods.customWait(2);

            if(Cls_Generic_Methods.isElementDisplayed(oPage_ShowMrnInInvestigationTemplateCreate.button_copyPatientId)){
                Cls_Generic_Methods.scrollToTop();
                CommonActions.MouseHoverAction(driver, oPage_ShowMrnInInvestigationTemplateCreate.text_mrnOpdRhs);
                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElementByJS(driver,oPage_ShowMrnInInvestigationTemplateCreate.button_copyPatientId);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable contents = clipboard.getContents(null);
                String result2 = (String) contents.getTransferData(DataFlavor.stringFlavor);
                m_assert.assertTrue("Mrn of Patient is copied in OPD RHS as "+result2);

            }
            else{
                m_assert.assertWarn("Expected MRN Copy option is not available ");
            }
            //Validating Patient ID and Mrn copy option in Summary options
            // validating the copy option for MRN of the patient in OPD Summary
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.scrollToTop();
            Cls_Generic_Methods.clickElement(driver, oPage_ShowMrnInInvestigationTemplateCreate.button_summaryOption);
            Cls_Generic_Methods.customWait(3);
            m_assert.assertTrue(CommonActions.MouseHoverAction(driver, oPage_ShowMrnInInvestigationTemplateCreate.text_mrnOpdSummary
            ), "Mouse hover action is performed on MRN of the patient in Summary");
            Cls_Generic_Methods.customWait(2);

            if(Cls_Generic_Methods.isElementDisplayed(oPage_ShowMrnInInvestigationTemplateCreate.button_copyMrnSummary)){
                Cls_Generic_Methods.scrollToTop();
                CommonActions.MouseHoverAction(driver, oPage_ShowMrnInInvestigationTemplateCreate.text_mrnOpdSummary);
                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElementByJS(driver,oPage_ShowMrnInInvestigationTemplateCreate.button_copyMrnSummary);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable contents = clipboard.getContents(null);
                String result3 = (String) contents.getTransferData(DataFlavor.stringFlavor);
                m_assert.assertTrue("Mrn of Patient is copied from OPD summary as "+result3);

            }
            else{
                m_assert.assertWarn("Expected MRN Copy option is not available in summary");
            }

            // validating the copy option for PatientId of the patient in OPD Summary
            Cls_Generic_Methods.customWait(4);
            m_assert.assertTrue(CommonActions.MouseHoverAction(driver, oPage_ShowMrnInInvestigationTemplateCreate.text_patientIdOpdSummary
            ), "Mouse hover action is performed on patient id ");
            Cls_Generic_Methods.customWait(3);

            if (Cls_Generic_Methods.isElementDisplayed(oPage_ShowMrnInInvestigationTemplateCreate.button_copyPatientIdSummary)){
                Cls_Generic_Methods.scrollToTop();
                CommonActions.MouseHoverAction(driver, oPage_ShowMrnInInvestigationTemplateCreate.text_patientIdOpdSummary);
                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElement(driver, oPage_ShowMrnInInvestigationTemplateCreate.button_copyPatientIdSummary);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable contents = clipboard.getContents(null);
                String result4 = (String) contents.getTransferData(DataFlavor.stringFlavor);
                m_assert.assertTrue("PatientId is copied of the patient"+concatPatientFullName+" in OPD RHS as :"+result4);
                Cls_Generic_Methods.customWait(3);
            }
            else{
                m_assert.assertWarn("Expected PatientId Copy option is not clickable in summary");
                Cls_Generic_Methods.customWait(2);
            }

        }catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Exception while validating copy option for mrn and patient id  " + e);
    }

    }
}

