package tests.settings.facilitySetting.clinical;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_MedicationSet;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.IPD_Data;
import data.OPD_Data;
import data.Settings_Data;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.common_tabs.advice.Page_AdviceTab;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.ipd.forms.postOperative.Page_DischargeForm;
import pages.ipd.forms.preOperative.Page_AdmissionInPreOperative;
import pages.opd.Page_OPD;
import pages.settings.facilitySettings.general.facilityContactList.Page_FacilityContactList;
import pages.settings.facilitySettings.clinical.facilityMedicationSets.Page_FacilityMedicationSets;
import pages.settings.facilitySettings.general.otSetup.Page_OtSetup;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MedicationSetTest extends TestBase {

    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();
    String medicationSetName = "Test Medication Set";
    String updatedMedicationSetName = "UPDATED " + medicationSetName;
    public static ArrayList<Model_MedicationSet> listMedicationSets = new ArrayList<Model_MedicationSet>();
    public static ArrayList<Model_MedicationSet> updatedListMedicationSets = new ArrayList<Model_MedicationSet>();



    @Test(enabled = true, description = "Validate Patient for Financial setup")
    public void createPatientInOpdToValidateMedicationSet() {

        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

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

                // Validate the tabs on Patient Registration Form
                if (oPage_NewPatientRegisteration.tabs_PatientRegForm.size() != oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.size()) {
                    m_assert.assertTrue(false, "No. of Tabs on Patient Reg. Form is " + oPage_NewPatientRegisteration.tabs_PatientRegForm.size() + ". Expected = " + oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.size());
                } else {

                    m_assert.assertTrue("No. of Tabs on Patient Reg. & Appointment Form is " + oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.size());

                    if (!Cls_Generic_Methods.getElementAttribute(oPage_NewPatientRegisteration.tabs_PatientRegForm.get(0), "class").equals("active")) {
                        m_assert.assertTrue(false, "Patient Details Tab is not selected on start by default.");
                    } else {
                        m_assert.assertTrue(true, "Patient Details Tab is selected on start by default.");

                        try {
                            for (int i = 0; i < oPage_NewPatientRegisteration.tabs_PatientRegForm.size(); i++) {

                                if (oPage_NewPatientRegisteration.tabs_PatientRegForm.get(i).getText().trim().equals(oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.get(i))) {

                                    m_assert.assertInfo(oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.get(i) + " Tab is displayed on the form.");
                                } else {
                                    m_assert.assertTrue(false, oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.get(i) + " Tab is not displayed on the form.");
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("" + e);
                        }
                    }
                }

                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage, 5), "Alert for compulsory field is visible by default on the empty form.");

                Cls_Generic_Methods.clickElement(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm);
                Cls_Generic_Methods.customWait(1);

                // Validate the Compulsory Sections Message
                if (Cls_Generic_Methods.getTextInElement(oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage).trim().equals(oEHR_Data.sCOMPULSORY_FIELDS_MESSAGE)) {
                    m_assert.assertTrue(true, "Validate that the Compulsory sections message is " + oEHR_Data.sCOMPULSORY_FIELDS_MESSAGE);
                } else {
                    m_assert.assertTrue(false, "Validate that the Compulsory sections message is " + oEHR_Data.sCOMPULSORY_FIELDS_MESSAGE);
                }

                // Validate the CSS of Compulsory Alert message
                if (Cls_Generic_Methods.getElementAttribute(oPage_NewPatientRegisteration.subText_requiredFieldsAlertMessage, "style").equals(oEHR_Data.sSTYLE_OF_RED_ALERT_MSG_FOR_MANODATORY_FIELDS)) {
                    m_assert.assertTrue(true, "Alert for compulsory field is highlighted in <b>Red</b> when trying to Create Appointment with empty form. Message = " + oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage.getText());
                } else {
                    m_assert.assertTrue(false, "Alert for compulsory field is highlighted in <b>Red</b> when trying to Create Appointment with empty form.<br>" + "Expected = " + oEHR_Data.sSTYLE_OF_RED_ALERT_MSG_FOR_MANODATORY_FIELDS + "<br>Actual = " + Cls_Generic_Methods.getElementAttribute(oPage_NewPatientRegisteration.subText_requiredFieldsAlertMessage, "style"));
                }

                m_assert.assertTrue(oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage.getText().trim().contains("First Name"), "First Name is visible in the Compulsory Fields alert message.");

                m_assert.assertTrue(oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage.getText().trim().contains("Mobile Number"), "Mobile Number is visible in the Compulsory Fields alert message.");

                // Entering Essential Form Data
                if (!myPatient.getsSALUTATION().isEmpty()) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()), "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
                }

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()), "First Name is entered as - " + myPatient.getsFIRST_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_middleNameOnPatientRegForm, myPatient.getsMIDDLE_NAME()), "Middle Name is entered as - " + myPatient.getsMIDDLE_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_lastNameOnPatientRegForm, myPatient.getsLAST_NAME()), "Last Name is entered as - " + myPatient.getsLAST_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, myPatient.getsMOBILE_NUMBER()), "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm), "Create Appointment button clicked");
                Cls_Generic_Methods.customWait(8);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 12);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validate Patient for Financial setup")
    public void createPatientInIpdToValidateMedicationSet() {

        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_ScheduleAdmission oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        Page_IPD oPage_IPD = new Page_IPD(driver);

        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        String sAddNewCase = "Add New Case";

        boolean bPatientFound = false;

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();

            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("IPD");
            Cls_Generic_Methods.customWait(8);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.tabs_TabsOnIPD.get(0), 8);


            try {

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

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, 12);

                // Entering Essential Form Data
                if (!myPatient.getsSALUTATION().isEmpty()) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()), "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
                }

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                        "First Name is entered as - " + myPatient.getsFIRST_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_middleNameOnPatientRegForm, myPatient.getsMIDDLE_NAME()),
                        "Middle Name is entered as - " + myPatient.getsMIDDLE_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_lastNameOnPatientRegForm, myPatient.getsLAST_NAME()),
                        "Last Name is entered as - " + myPatient.getsLAST_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, myPatient.getsMOBILE_NUMBER()),
                        "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

                Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduleAdmission.radioBtn_emergency);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.button_selectCase, 8);
                Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_selectCase);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.button_allCasesDropDown, 8);

                Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_allCasesDropDown);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_ScheduleAdmission.select_allCasesDropDown, sAddNewCase);
                Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_allCasesDropDown);

                Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_createAdmission);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 12);
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_assignBed, 15),
                        "Assigned bed Form is displayed");

                try {
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_ScheduleAdmission.header_assignBed)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.input_selectWard),
                                "Ward dropdown Clicked");
                        m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_ScheduleAdmission.input_selectWard, 1),
                                "Ward Value Selected");
                        Cls_Generic_Methods.customWait(1);
                        // m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.input_selectRoom),
                       //         "SelectRoom dropdown Clicked");
                        m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_ScheduleAdmission.input_selectRoom, 1),
                                "SelectRoom value Selected");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_saveBed),
                                "Clicked on Save bed");
                        Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);

                    } else {
                        m_assert.assertTrue(false, "Assign Bed Form Not displayed. ");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to Select Room and assign Bed" + e);
                }

                CommonActions.selectDepartmentOnApp("IPD");
                Cls_Generic_Methods.customWait(7);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.tabs_TabsOnIPD.get(0), 8);
                CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today);
                Cls_Generic_Methods.customWait(5);
                bPatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, concatPatientFullName);
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(bPatientFound, "Validate that the patient " + concatPatientFullName + " is created in IPD");


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validate Add New Medication Set")
    public void validateAddNewMedicationSet() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        String sSetType = "All", sFacilityName = "Ophthalmology";
        Page_FacilityMedicationSets oPage_FacilityMedicationSets = new Page_FacilityMedicationSets(driver);
        listMedicationSets.add(TestBase.map_MedicationSetsInExcel.get("001"));
        listMedicationSets.add(TestBase.map_MedicationSetsInExcel.get("002"));
        listMedicationSets.add(TestBase.map_MedicationSetsInExcel.get("003"));

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(1);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility Medication Sets");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationSets.button_createMedicationSets, 8);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_FacilityMedicationSets.button_createMedicationSets);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_modal, 8);

                Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityMedicationSets.input_medicationSetName, medicationSetName);
                for (WebElement eSetType : oPage_FacilityMedicationSets.list_buttonsSetType) {
                    if (Cls_Generic_Methods.getTextInElement(eSetType).equalsIgnoreCase(sSetType)) {
                        Cls_Generic_Methods.clickElement(eSetType);
                    }
                }
                Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityMedicationSets.select_specialities, sFacilityName);


//                --------------------------------------------------------------------------------------------------------------------------------------

                for(Model_MedicationSet oModel_MedicationSet: listMedicationSets){
                    int i = listMedicationSets.indexOf(oModel_MedicationSet);
                    String currentMedicationName = oModel_MedicationSet.getsMEDICATION_NAME();

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityMedicationSets.list_input_MedicineName.get(i), currentMedicationName);
                    Cls_Generic_Methods.customWait(4);

                    try {
                        if (oPage_FacilityMedicationSets.list_rowsMedicineNameAutoCompleteSuggestions.get(0).isDisplayed()) {
                            oPage_FacilityMedicationSets.list_input_MedicineName.get(i).sendKeys(Keys.DOWN);
                            oPage_FacilityMedicationSets.list_input_MedicineName.get(i).sendKeys(Keys.ENTER);
                            Cls_Generic_Methods.customWait(1);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    String copiedContent = copyTextAfterTab(oPage_FacilityMedicationSets.list_input_MedicineName.get(i));
                    m_assert.assertTrue("<b>" + copiedContent + " </b> added into Medication Set " + medicationSetName);

                    Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityMedicationSets.list_select_MedicineQuantity.get(i), oModel_MedicationSet.getsQUANTITY());
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertInfo("<b>" + oModel_MedicationSet.getsQUANTITY() + "</b> for Medicine Quantity added for Medication " + currentMedicationName);

                    Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityMedicationSets.list_select_MedicineFrequency.get(i), oModel_MedicationSet.getsFREQUENCY());
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertInfo("<b>" + oModel_MedicationSet.getsFREQUENCY() + "</b> for Frequency added for Medication " + currentMedicationName);

                    Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityMedicationSets.list_select_MedicineDuration.get(i), oModel_MedicationSet.getsDURATION_COUNT());
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertInfo("<b>" + oModel_MedicationSet.getsDURATION_COUNT() + "</b> for Medicine Duration added for Medication " + currentMedicationName);

                    Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityMedicationSets.list_select_MedicineDurationOption.get(i), oModel_MedicationSet.getsDURATION_DAYS());
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertInfo("<b>" + oModel_MedicationSet.getsDURATION_DAYS() + "</b> for Medicine Duration Option added into Medication " + currentMedicationName);

                    Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityMedicationSets.list_select_MedicineEyeSide.get(i), oModel_MedicationSet.getsSIDE());
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertInfo("<b>" + oModel_MedicationSet.getsSIDE() + "</b> for Eye Side added into Medication " + currentMedicationName);

                    Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityMedicationSets.list_select_MedicineInstruction.get(i), oModel_MedicationSet.getsINSTRUCTION());
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertInfo("<b>" + oModel_MedicationSet.getsDURATION_DAYS() + "</b> for Instruction added into Medication " + currentMedicationName);

                }

                //                --------------------------------------------------------------------------------------------------------------------------------------

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FacilityMedicationSets.button_addMedicationSet),
                        "Add Medication Set button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationSets.button_createMedicationSets, 12);

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForPageLoad(driver, 20);

                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility Medication Sets");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationSets.button_createMedicationSets, 8);

                Cls_Generic_Methods.clickElement(oPage_FacilityMedicationSets.input_medicationSetSearchFilter);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityMedicationSets.input_medicationSetSearchFilter, medicationSetName);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationSets.list_rowsMedicationSetsNameOnTable.get(0), 4);
                boolean bValidateMedicationSetCreated = false;

                for (WebElement eMedicationSetName : oPage_FacilityMedicationSets.list_rowsMedicationSetsNameOnTable) {
                    if (Cls_Generic_Methods.getTextInElement(eMedicationSetName).contains(medicationSetName)) {
                        bValidateMedicationSetCreated = true;
                        break;
                    }
                }

                m_assert.assertTrue(bValidateMedicationSetCreated,
                        "Validate the Medication set <b>" + medicationSetName + "</b> has been created and displayed");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validate the Edit functionality of Medication Set")
    public void validateSearchAndEditMedicationSet() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_FacilityMedicationSets oPage_FacilityMedicationSets = new Page_FacilityMedicationSets(driver);
        ArrayList<String> medNamesList = new ArrayList<String>();
        ArrayList<String> updatedMedNamesList = new ArrayList<String>();

        updatedListMedicationSets.add(TestBase.map_MedicationSetsInExcel.get("004"));
        updatedListMedicationSets.add(TestBase.map_MedicationSetsInExcel.get("005"));
        updatedListMedicationSets.add(TestBase.map_MedicationSetsInExcel.get("006"));

        boolean bValidateMedicationSetUpdated = false;

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(1);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility Medication Sets");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationSets.button_createMedicationSets, 8);


                Cls_Generic_Methods.clickElement(oPage_FacilityMedicationSets.input_medicationSetSearchFilter);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityMedicationSets.input_medicationSetSearchFilter, medicationSetName);
                Cls_Generic_Methods.customWait(4);
//				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationSets.list_rowsMedicationSetsNameOnTable.get(0), 4);

                int requiredIndex = -1;
                for (WebElement eMedicationSetName : oPage_FacilityMedicationSets.list_rowsMedicationSetsNameOnTable) {
                    if (Cls_Generic_Methods.getTextInElement(eMedicationSetName).contains(medicationSetName)) {
                        requiredIndex = oPage_FacilityMedicationSets.list_rowsMedicationSetsNameOnTable.indexOf(eMedicationSetName);
                        break;
                    }
                }

                if (requiredIndex == -1) {
                    m_assert.assertTrue(false, "<b>" + medicationSetName + " </b> not found in Medication Sets via Search");
                } else {
                    m_assert.assertTrue(true, "<b>" + medicationSetName + " </b> found in Medication Sets");

                    Cls_Generic_Methods.clickElement(driver, oPage_FacilityMedicationSets.list_MedicationSetEditButtonOnTable.get(requiredIndex));
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_modal, 8);

                    Cls_Generic_Methods.clearValuesInElement(oPage_FacilityMedicationSets.input_medicationSetName);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityMedicationSets.input_medicationSetName, updatedMedicationSetName);


                    for (Model_MedicationSet oModel_MedicationSet : updatedListMedicationSets) {

                        int i = updatedListMedicationSets.indexOf(oModel_MedicationSet);
                        String currentMedicationName = oModel_MedicationSet.getsMEDICATION_NAME();

                        Cls_Generic_Methods.clickElement(oPage_FacilityMedicationSets.list_input_MedicineName.get(i));
                        String previousCopiedContent = copyTextAfterTab(oPage_FacilityMedicationSets.list_input_MedicineName.get(i));
                        medNamesList.add(previousCopiedContent);

                        Cls_Generic_Methods.clearValuesInElement(oPage_FacilityMedicationSets.list_input_MedicineName.get(i));
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityMedicationSets.list_input_MedicineName.get(i), currentMedicationName);
                        Cls_Generic_Methods.customWait(4);
                       try {
                           for(WebElement medication : oPage_FacilityMedicationSets.list_rowsMedicineNameAutoCompleteSuggestions) {
                                   String suggestionText = Cls_Generic_Methods.getTextInElement(medication);
                                   if (suggestionText.contains(currentMedicationName)) {
                                       Cls_Generic_Methods.clickElement(medication);
                                       Cls_Generic_Methods.customWait(1);
                                   }
                           }
                       }catch (Exception e){
                           e.printStackTrace();
                       }

                        String copiedContent = copyTextAfterTab(oPage_FacilityMedicationSets.list_input_MedicineName.get(i));
                        updatedMedNamesList.add(copiedContent);

                        m_assert.assertTrue(!previousCopiedContent.equals(copiedContent),
                                "Medicine name changed to = <b>" + copiedContent + " </b> from = <b>" + previousCopiedContent + " </b>");

                        Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityMedicationSets.list_select_MedicineQuantity.get(i), oModel_MedicationSet.getsQUANTITY());
                        Cls_Generic_Methods.customWait(1);
                        m_assert.assertInfo("<b>" + oModel_MedicationSet.getsQUANTITY() + "</b> for Medicine Quantity updated for Medication " + currentMedicationName);

                        Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityMedicationSets.list_select_MedicineFrequency.get(i), oModel_MedicationSet.getsFREQUENCY());
                        Cls_Generic_Methods.customWait(1);
                        m_assert.assertInfo("<b>" + oModel_MedicationSet.getsFREQUENCY() + "</b> for Frequency updated for Medication " + currentMedicationName);

                        Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityMedicationSets.list_select_MedicineDuration.get(i), oModel_MedicationSet.getsDURATION_COUNT());
                        Cls_Generic_Methods.customWait(1);
                        m_assert.assertInfo("<b>" + oModel_MedicationSet.getsDURATION_COUNT() + "</b> for Medicine Duration updated for Medication " + currentMedicationName);

                        Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityMedicationSets.list_select_MedicineDurationOption.get(i), oModel_MedicationSet.getsDURATION_DAYS());
                        Cls_Generic_Methods.customWait(1);
                        m_assert.assertInfo("<b>" + oModel_MedicationSet.getsDURATION_DAYS() + "</b> for Medicine Duration Option updated into Medication " + currentMedicationName);

                        Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityMedicationSets.list_select_MedicineEyeSide.get(i), oModel_MedicationSet.getsSIDE());
                        Cls_Generic_Methods.customWait(1);
                        m_assert.assertInfo("<b>" + oModel_MedicationSet.getsSIDE() + "</b> for Eye Side updated into Medication " + currentMedicationName);

                        Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityMedicationSets.list_select_MedicineInstruction.get(i), oModel_MedicationSet.getsINSTRUCTION());
                        Cls_Generic_Methods.customWait(1);
                        m_assert.assertInfo("<b>" + oModel_MedicationSet.getsDURATION_DAYS() + "</b> for Instruction updated into Medication " + currentMedicationName);

                    }

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FacilityMedicationSets.button_updateMedicationSet),
                            "Update Medication Set button clicked");

                    m_assert.assertInfo(!updatedMedNamesList.toString().equals(medNamesList.toString()),
                            "Updated the medicines in set from " + medNamesList.toString() + " to " + updatedMedNamesList.toString());

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationSets.button_createMedicationSets, 12);

//                    driver.navigate().refresh();
                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.waitForPageLoad(driver, 20);

                    CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility Medication Sets");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationSets.button_createMedicationSets, 8);

                    Cls_Generic_Methods.clickElement(oPage_FacilityMedicationSets.input_medicationSetSearchFilter);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityMedicationSets.input_medicationSetSearchFilter, updatedMedicationSetName);
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationSets.list_rowsMedicationSetsNameOnTable.get(0), 4);

                    for (WebElement eMedicationSetName : oPage_FacilityMedicationSets.list_rowsMedicationSetsNameOnTable) {
                        if (Cls_Generic_Methods.getTextInElement(eMedicationSetName).contains(updatedMedicationSetName)) {
                            bValidateMedicationSetUpdated = true;
                            break;
                        }
                    }
                    m_assert.assertTrue(bValidateMedicationSetUpdated,
                            "Validate the Medication set <b>" + updatedMedicationSetName + "</b> has been updated from <b>" + medicationSetName + "</b> and displayed");
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

    @Test(enabled = true, description = "Validate the generated medication set under Advise Template")
    public void validateMedicationSetInOPDTemplate() {

        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_AdviceTab oPage_AdviceTab = new Page_AdviceTab(driver);
        Page_FacilityMedicationSets oPage_FacilityMedicationSets = new Page_FacilityMedicationSets(driver);

        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        String sTemplateOption = "Post OP";
        String sMedicationSetLevel = "All";
        boolean bPatientNameFound = false;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                //go in opd and create template
                CommonActions.selectDepartmentOnApp("OPD");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);

                m_assert.assertInfo(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, OPD_Data.tab_ALL),
                        "Validate " + OPD_Data.tab_ALL + " tab is selected");
                Cls_Generic_Methods.customWait(1);

                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

                if (bPatientNameFound) {
                    //Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_markPatientArrived);
                    Cls_Generic_Methods.customWait();
                   // Cls_Generic_Methods.waitForPageLoad(driver, 5);

                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate),
                            "Clicked on + New template button");

                    m_assert.assertInfo(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate,
                            sTemplateOption), "Validate " + sTemplateOption + " template  is selected");

                    m_assert.assertInfo(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_templateName, 8),
                            "Templated Opened, Text Visible: <b> " + oPage_CommonElements.header_templateName.getText() + "</b>");

                    Cls_Generic_Methods.clickElement(oPage_AdviceTab.tab_advice);
                    Cls_Generic_Methods.clickElement(oPage_AdviceTab.tab_medicationUnderAdviceTab);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AdviceTab.select_medicationSetLevel, 8);

                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_AdviceTab.select_medicationSetLevel, sMedicationSetLevel),
                            "Medication Set level is set to " + sMedicationSetLevel);
                    Cls_Generic_Methods.customWait();

                    try {
                        m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_AdviceTab.select_medicationSets, updatedMedicationSetName),
                                "Selected Medication Set as - " + updatedMedicationSetName);

                        String selectedMedicationSet = Cls_Generic_Methods.getSelectedValue(oPage_AdviceTab.select_medicationSets);
                        m_assert.assertTrue(selectedMedicationSet.equals(updatedMedicationSetName),
                                "Validate selected Medication Set as - " + selectedMedicationSet);

                        if(listMedicationSets.size()==0){
                            listMedicationSets = new ArrayList<Model_MedicationSet>();
                            listMedicationSets.add(TestBase.map_MedicationSetsInExcel.get("001"));
                            listMedicationSets.add(TestBase.map_MedicationSetsInExcel.get("002"));
                            listMedicationSets.add(TestBase.map_MedicationSetsInExcel.get("003"));
                        }
                        if(updatedListMedicationSets.size()==0){
                            updatedListMedicationSets = new ArrayList<Model_MedicationSet>();
                            updatedListMedicationSets.add(TestBase.map_MedicationSetsInExcel.get("004"));
                            updatedListMedicationSets.add(TestBase.map_MedicationSetsInExcel.get("005"));
                            updatedListMedicationSets.add(TestBase.map_MedicationSetsInExcel.get("006"));
                        }

                        for (Model_MedicationSet oModel_MedicationSet : updatedListMedicationSets) {
                            int i = updatedListMedicationSets.indexOf(oModel_MedicationSet);

                            String sMedicineName, sType, sQuantity, sFrequency, sDurationCount, sDurationDays, sSide, sInstruction;

                            sMedicineName = Cls_Generic_Methods.getElementAttribute(oPage_FacilityMedicationSets.list_input_MedicineName.get(i), "value").trim();
                            sType = Cls_Generic_Methods.getElementAttribute(oPage_FacilityMedicationSets.list_input_MedicineType.get(i), "value").trim();
                            sQuantity = Cls_Generic_Methods.getSelectedValue(oPage_FacilityMedicationSets.list_select_MedicineQuantity.get(i));
                            sFrequency = Cls_Generic_Methods.getSelectedValue(oPage_FacilityMedicationSets.list_select_MedicineFrequency.get(i));
                            sDurationCount = Cls_Generic_Methods.getSelectedValue(oPage_FacilityMedicationSets.list_select_MedicineDuration.get(i));
                            sDurationDays = Cls_Generic_Methods.getSelectedValue(oPage_FacilityMedicationSets.list_select_MedicineDurationOption.get(i));
                            sSide = Cls_Generic_Methods.getSelectedValue(oPage_FacilityMedicationSets.list_select_MedicineEyeSide.get(i));
                            sInstruction = Cls_Generic_Methods.getSelectedValue(oPage_FacilityMedicationSets.list_select_MedicineInstruction.get(i));

                            m_assert.assertTrue(sMedicineName.contains(oModel_MedicationSet.getsMEDICATION_NAME()),
                                    "Medicine Name <b>" + sMedicineName + "</b> in Medication Set matched with " + oModel_MedicationSet.getsMEDICATION_NAME());

                            m_assert.assertInfo(sType.equalsIgnoreCase(oModel_MedicationSet.getsTYPE()),
                                    "Medicine Type <b>" + sType + "</b> in Medication Set matched with " + oModel_MedicationSet.getsTYPE());

                            m_assert.assertInfo(sQuantity.equalsIgnoreCase(oModel_MedicationSet.getsQUANTITY()),
                                    "Medicine Quantity <b>" + sQuantity + "</b> in Medication Set matched with " + oModel_MedicationSet.getsQUANTITY());

                            m_assert.assertInfo(sFrequency.equalsIgnoreCase(oModel_MedicationSet.getsFREQUENCY()),
                                    "Medicine Frequency <b>" + sFrequency + "</b> in Medication Set matched with " + oModel_MedicationSet.getsFREQUENCY());

                            m_assert.assertInfo(sDurationCount.equalsIgnoreCase(oModel_MedicationSet.getsDURATION_COUNT()),
                                    "Medicine Duration Count <b>" + sDurationCount + "</b> in Medication Set matched with " + oModel_MedicationSet.getsDURATION_COUNT());

                            m_assert.assertInfo(sDurationDays.equalsIgnoreCase(oModel_MedicationSet.getsDURATION_DAYS()),
                                    "Medicine Duration Days <b>" + sDurationDays + "</b> in Medication Set matched with " + oModel_MedicationSet.getsDURATION_DAYS());

                            m_assert.assertInfo(sSide.equalsIgnoreCase(oModel_MedicationSet.getsSIDE()),
                                    "Medicine Side <b>" + sSide + "</b> in Medication Set matched with " + oModel_MedicationSet.getsSIDE());

                            m_assert.assertInfo(sInstruction.equalsIgnoreCase(oModel_MedicationSet.getsINSTRUCTION()),
                                    "Medicine Instruction <b>" + sInstruction + "</b> in Medication Set matched with " + oModel_MedicationSet.getsINSTRUCTION());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertTrue(false, "Error while selecting the medication set - " + updatedMedicationSetName + "\n" + e);
                    }

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.button_close), "Close template button is clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 4);

                    if (Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.button_markPatientNotArrived)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_markPatientNotArrived),
                                "Click on the Not Arrived button");
                        Cls_Generic_Methods.customWait();
                        Cls_Generic_Methods.waitForPageLoad(driver, 4);
                    } else {
                        m_assert.assertTrue(false, "Not Arrived button for patient is not displayed");
                    }

                } else {
                    m_assert.assertTrue(false, "Patient" + concatPatientFullName + " was not found on the page");
                }

            } catch (Exception e) {
                m_assert.assertFatal(" Exception while validating the medication Set in Advice Tab on OPD. " + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate the generated medication set under Discharge Template")
    public void validateMedicationSetInIPDTemplate() {

        Page_IPD oPage_IPD = new Page_IPD(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_AdviceTab oPage_AdviceTab = new Page_AdviceTab(driver);
        Page_FacilityMedicationSets oPage_FacilityMedicationSets = new Page_FacilityMedicationSets(driver);
        Page_AdmissionInPreOperative oPage_AdmissionInPreOperative = new Page_AdmissionInPreOperative(driver);
        Page_DischargeForm oPage_DischargeForm = new Page_DischargeForm(driver);

        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        String sMedicationSetLevel = "All";
        boolean bPatientNameFound = false;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                //go in opd and create template
                CommonActions.selectDepartmentOnApp("IPD");
                Cls_Generic_Methods.customWait(7);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.tabs_TabsOnIPD.get(0), 8);

                m_assert.assertInfo(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today),
                        "Validate Scheduled Today tab is selected");

                bPatientNameFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, concatPatientFullName);
                Cls_Generic_Methods.customWait(5);

                if (bPatientNameFound) {

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_readyForAdmission, 8);
                    if(Cls_Generic_Methods.isElementDisplayed(oPage_IPD.button_readyForAdmission)){
                        Cls_Generic_Methods.clickElement(oPage_IPD.button_readyForAdmission);
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 8);
                    Cls_Generic_Methods.scrollToElementByJS(oPage_IPD.text_preOperativeSection);

                    if(!Cls_Generic_Methods.isElementDisplayed(oPage_AdmissionInPreOperative.button_filledIconInAdmissionInPreOperative)){
                        Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.button_admissionInPreOperative);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AdmissionInPreOperative.tab_AdminTabOnAdmissionUnderPreOperative, 16);
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.tab_AdminTabOnAdmissionUnderPreOperative),
                                "Validate Admin Tab is clicked");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.button_saveOnModalHeader),
                                "Validate Save button is clicked on Admission under Pre-Operative");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);

                        Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_preOperativeSection, 16);
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DischargeForm.button_dischargeTemplate, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_DischargeForm.button_dischargeTemplate), "clicked on Discharge template ");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DischargeForm.button_next, 8);
                    CommonActions.selectListOption(oPage_DischargeForm.list_tabsOnDischargeForm, "Advice On Discharge");

                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_AdviceTab.select_medicationSetLevel, sMedicationSetLevel),
                            "Medication Set level is set to " + sMedicationSetLevel);
                    Cls_Generic_Methods.customWait();

                    try {
                        m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_AdviceTab.select_medicationSets, updatedMedicationSetName),
                                "Selected Medication Set as - " + updatedMedicationSetName);

                        String selectedMedicationSet = Cls_Generic_Methods.getSelectedValue(oPage_AdviceTab.select_medicationSets).trim();
                        m_assert.assertTrue(selectedMedicationSet.equals(updatedMedicationSetName),
                                "Validate selected Medication Set as - " + selectedMedicationSet);

                        if(listMedicationSets.size()==0){
                            listMedicationSets = new ArrayList<Model_MedicationSet>();
                            listMedicationSets.add(TestBase.map_MedicationSetsInExcel.get("001"));
                            listMedicationSets.add(TestBase.map_MedicationSetsInExcel.get("002"));
                            listMedicationSets.add(TestBase.map_MedicationSetsInExcel.get("003"));
                        }
                        if(updatedListMedicationSets.size()==0){
                            updatedListMedicationSets = new ArrayList<Model_MedicationSet>();
                            updatedListMedicationSets.add(TestBase.map_MedicationSetsInExcel.get("004"));
                            updatedListMedicationSets.add(TestBase.map_MedicationSetsInExcel.get("005"));
                            updatedListMedicationSets.add(TestBase.map_MedicationSetsInExcel.get("006"));
                        }

                        for (Model_MedicationSet oModel_MedicationSet : updatedListMedicationSets) {
                            int i = updatedListMedicationSets.indexOf(oModel_MedicationSet);

                            String sMedicineName, sType, sQuantity, sFrequency, sDurationCount, sDurationDays, sSide, sInstruction;

                            sMedicineName = Cls_Generic_Methods.getElementAttribute(oPage_FacilityMedicationSets.list_input_MedicineName.get(i), "value").trim();
                            sType = Cls_Generic_Methods.getElementAttribute(oPage_FacilityMedicationSets.list_input_MedicineType.get(i), "value").trim();
                            sQuantity = Cls_Generic_Methods.getSelectedValue(oPage_FacilityMedicationSets.list_select_MedicineQuantity.get(i));
                            sFrequency = Cls_Generic_Methods.getSelectedValue(oPage_FacilityMedicationSets.list_select_MedicineFrequency.get(i));
                            sDurationCount = Cls_Generic_Methods.getSelectedValue(oPage_FacilityMedicationSets.list_select_MedicineDuration.get(i));
                            sDurationDays = Cls_Generic_Methods.getSelectedValue(oPage_FacilityMedicationSets.list_select_MedicineDurationOption.get(i));
                            sSide = Cls_Generic_Methods.getSelectedValue(oPage_FacilityMedicationSets.list_select_MedicineEyeSide.get(i));
                            sInstruction = Cls_Generic_Methods.getSelectedValue(oPage_FacilityMedicationSets.list_select_MedicineInstruction.get(i));

                            m_assert.assertTrue(sMedicineName.contains(oModel_MedicationSet.getsMEDICATION_NAME()),
                                    "Medicine Name <b>" + sMedicineName + "</b> in Medication Set matched with " + oModel_MedicationSet.getsMEDICATION_NAME());

                            m_assert.assertInfo(sType.equalsIgnoreCase(oModel_MedicationSet.getsTYPE()),
                                    "Medicine Type <b>" + sType + "</b> in Medication Set matched with " + oModel_MedicationSet.getsTYPE());

                            m_assert.assertInfo(sQuantity.equalsIgnoreCase(oModel_MedicationSet.getsQUANTITY()),
                                    "Medicine Quantity <b>" + sQuantity + "</b> in Medication Set matched with " + oModel_MedicationSet.getsQUANTITY());

                            m_assert.assertInfo(sFrequency.equalsIgnoreCase(oModel_MedicationSet.getsFREQUENCY()),
                                    "Medicine Frequency <b>" + sFrequency + "</b> in Medication Set matched with " + oModel_MedicationSet.getsFREQUENCY());

                            m_assert.assertInfo(sDurationCount.equalsIgnoreCase(oModel_MedicationSet.getsDURATION_COUNT()),
                                    "Medicine Duration Count <b>" + sDurationCount + "</b> in Medication Set matched with " + oModel_MedicationSet.getsDURATION_COUNT());

                            m_assert.assertInfo(sDurationDays.equalsIgnoreCase(oModel_MedicationSet.getsDURATION_DAYS()),
                                    "Medicine Duration Days <b>" + sDurationDays + "</b> in Medication Set matched with " + oModel_MedicationSet.getsDURATION_DAYS());

                            m_assert.assertInfo(sSide.equalsIgnoreCase(oModel_MedicationSet.getsSIDE()),
                                    "Medicine Side <b>" + sSide + "</b> in Medication Set matched with " + oModel_MedicationSet.getsSIDE());

                            m_assert.assertInfo(sInstruction.equalsIgnoreCase(oModel_MedicationSet.getsINSTRUCTION()),
                                    "Medicine Instruction <b>" + sInstruction + "</b> in Medication Set matched with " + oModel_MedicationSet.getsINSTRUCTION());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertTrue(false, "Error while selecting the medication set - " + updatedMedicationSetName + "\n" + e);
                    }

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.button_close), "Close template button is clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 4);

                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.button_undoAdmission)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.button_undoAdmission),
                                "Click on the Undo Admission button");
                        Cls_Generic_Methods.customWait();
                        Cls_Generic_Methods.waitForPageLoad(driver, 4);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 8);
                    } else {
                        m_assert.assertTrue(false, "Undo Admission button for patient is not displayed");
                    }

                }

            } catch (Exception e) {
                m_assert.assertFatal(" Exception while validating the medication Set in Advice Tab on IPD. " + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Remove the patient created for validation")
    public void removePatientCreatedForValidateMedicationSetInOPD() {

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
            Cls_Generic_Methods.customWait(7);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage.get(0), 8);

            try {
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, OPD_Data.tab_ALL),
                        "Validate " + OPD_Data.tab_ALL + " tab is selected");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage.get(0), 8);

                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                m_assert.assertTrue(bPatientNameFound, "Validate Patient  " + concatPatientFullName + " is clicked in " + OPD_Data.tab_ALL);

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
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.button_cancelAppointmentButtonInForm, 8);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FacilityContactList.button_cancelAppointmentButtonInForm), "Appointment cancelled");
                    Cls_Generic_Methods.customWait(8);
                } else {
                    m_assert.assertTrue(false, "Patient " + concatPatientFullName + " not found");
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

    @Test(enabled = true, description = "Remove the patient created for validation")
    public void removePatientCreatedForValidateMedicationSetInIPD() {

        Page_IPD oPage_IPD = new Page_IPD(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;

        boolean bPatientNameFound = false;

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("IPD");
            Cls_Generic_Methods.customWait(7);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.tabs_TabsOnIPD.get(0), 8);

            try {
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertInfo(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today),
                        "Validate Scheduled Today tab is selected");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.tabs_TabsOnIPD.get(0), 8);

                bPatientNameFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, concatPatientFullName);

                if (bPatientNameFound) {

                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.button_undoAdmission)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.button_undoAdmission),
                                "Click on the Undo Admission button");
                        Cls_Generic_Methods.customWait();
                        Cls_Generic_Methods.waitForPageLoad(driver, 4);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 8);
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_deleteAdmission, 8);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_deleteAdmission), "Clicked on <b>Delete Admission</b> button");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_deleteAdmissionOnModal, 8);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_deleteAdmissionOnModal),
                            "IPD Admission Appointment cancelled");
                    Cls_Generic_Methods.waitForPageLoad(driver, 8);

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

    @Test(enabled = true, description = "Remove the created medication set")
    public void deleteCreatedMedicationSet() {
        Page_FacilityMedicationSets oPage_FacilityMedicationSets = new Page_FacilityMedicationSets(driver);
        boolean bValidateMedicationSetUpdated = false;

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility Medication Sets");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationSets.button_createMedicationSets, 8);


                Cls_Generic_Methods.clickElement(oPage_FacilityMedicationSets.input_medicationSetSearchFilter);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityMedicationSets.input_medicationSetSearchFilter, medicationSetName);
                Cls_Generic_Methods.customWait(4);

                int requiredIndex = -1;
                for (WebElement eMedicationSetName : oPage_FacilityMedicationSets.list_rowsMedicationSetsNameOnTable) {
                    if (Cls_Generic_Methods.getTextInElement(eMedicationSetName).contains(medicationSetName)) {
                        requiredIndex = oPage_FacilityMedicationSets.list_rowsMedicationSetsNameOnTable.indexOf(eMedicationSetName);
                        break;
                    }
                }

                if (requiredIndex == -1) {
                    m_assert.assertTrue(false, "<b>" + medicationSetName + " </b> not found in Medication Sets via Search");
                } else {
                    m_assert.assertTrue(true, "<b>" + medicationSetName + " </b> found in Medication Sets");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FacilityMedicationSets.list_MedicationSetDeleteButtonOnTable.get(requiredIndex)),
                            "<b>Delete</b> on Medication Set - <b>" + Cls_Generic_Methods.getTextInElement(oPage_FacilityMedicationSets.list_rowsMedicationSetsNameOnTable.get(requiredIndex))
                                    + "</b> button clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationSets.header_confirmDeleteMedicationSet, 8);

                    bValidateMedicationSetUpdated = Cls_Generic_Methods.clickElement(driver, oPage_FacilityMedicationSets.button_confirmDeleteMedicationSet);
                    m_assert.assertTrue(bValidateMedicationSetUpdated, "<b>Confirm</b> on Delete Medication Set Dialog box is clicked");
                }

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForPageLoad(driver, 20);

                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility Medication Sets");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationSets.button_createMedicationSets, 8);

                Cls_Generic_Methods.clickElement(oPage_FacilityMedicationSets.input_medicationSetSearchFilter);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityMedicationSets.input_medicationSetSearchFilter, medicationSetName);
                Cls_Generic_Methods.customWait();
                boolean bValidateMedicationSetDeleted = true;

                for (WebElement eMedicationSetName : oPage_FacilityMedicationSets.list_rowsMedicationSetsNameOnTable) {
                    if (Cls_Generic_Methods.getTextInElement(eMedicationSetName).contains(medicationSetName)) {
                        bValidateMedicationSetDeleted = false;
                        break;
                    }
                }

                m_assert.assertTrue(bValidateMedicationSetDeleted,
                        "Validate the Medication set <b>" + medicationSetName + "</b> has been deleted");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    public static boolean selectMedicineFromDropdown(List<WebElement> list_element, String optionToSelect) {

        boolean optionSelected = false;

        try {
            for (WebElement eElementInDropdown : list_element) {

                if (eElementInDropdown.isDisplayed()) {
                    String medicineText = Cls_Generic_Methods.getTextInElement(eElementInDropdown);
                    String medicineName = medicineText.split("\n")[0].trim();
                    String medicineCategory = medicineText.split("\n")[1].trim();

                    System.out.println(medicineName);
                    System.out.println(optionToSelect);

                    System.out.println(medicineName + "\t" + medicineCategory);
                    if (medicineText.contains(optionToSelect)) {
                        Cls_Generic_Methods.clickElement(eElementInDropdown);
                        optionSelected = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        m_assert.assertInfo(optionSelected, "Validate <b>" + optionToSelect + "</b> option is selected");
        return optionSelected;
    }

    public static String copyTextAfterTab(WebElement targetElement) throws IOException, UnsupportedFlavorException {

        String returnMsg = null;
        try {
            Platform platformName = ((HasCapabilities) driver).getCapabilities().getPlatformName();
            Keys cmdCtrl = platformName.is(Platform.MAC) ? Keys.COMMAND : Keys.CONTROL;

//			new Actions(driver)
//					.sendKeys(Keys.TAB)
//					.keyDown(Keys.SHIFT)
//					.sendKeys(Keys.TAB)
//					.keyUp(Keys.SHIFT)
//					.keyDown(cmdCtrl)
//					.sendKeys("c")
//					.keyUp(cmdCtrl)
//					.perform();

            new Actions(driver).sendKeys(Keys.TAB).perform();
            Cls_Generic_Methods.customWait(1);

            new Actions(driver).keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).build().perform();
            Cls_Generic_Methods.customWait(1);

            new Actions(driver).keyDown(cmdCtrl).sendKeys("c").keyUp(cmdCtrl).build().perform();
            Cls_Generic_Methods.customWait(1);

            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            returnMsg = (String) c.getData(DataFlavor.stringFlavor);

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        return returnMsg;
    }

}