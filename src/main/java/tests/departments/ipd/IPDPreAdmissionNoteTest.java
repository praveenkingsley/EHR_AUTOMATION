package tests.departments.ipd;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import com.mongodb.util.JSONParseException;
import data.EHR_Data;
import data.IPD_Data;
import data.Settings_Data;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.ipd.forms.Page_PreAdmissionNote;
import pages.opd.Page_OPD;
import pages.settings.facilitySettings.clinical.facilityProcedureNote.Page_FacilityProcedureNote;
import pages.settings.organisationSettings.clinical.Page_PreAdmissionInstructionNote;
import pages.settings.organisationSettings.clinical.Page_PreAdmissionInvestigationNote;
import pages.settings.organisationSettings.clinical.Page_PreAdmissionMedicationNote;
import pages.settings.organisationSettings.clinical.Page_PreAdmissionSpecialNote;
import pages.settings.organisationSettings.general.Page_PatientSettings;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_CategoryMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;

import java.util.List;

public class IPDPreAdmissionNoteTest extends TestBase {
    Page_IPD oPage_IPD;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_NewPatientRegisteration oPage_NewPatientRegisteration;

    Page_OPD oPage_OPD;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    static Model_Patient myPatient;
    Page_Navbar oPage_Navbar;
    Page_CategoryMaster oPage_CategoryMaster;
    Page_ItemMaster oPage_ItemMaster;
    EHR_Data oEHR_Data;
    static String sPreAdmissionInstructionNoteName = "Automation Ins Note";
    static String sPreAdmissionInstructionNoteText = "Automation pre admission instruction note test text";
    static String sPreAdmissionMedicationNoteName = "Automation Medi Note";
    static String sPreAdmissionMedicationNoteText = "Automation pre admission medication note test text";
    static String sPreAdmissionInvestigationNoteName = "Auto Invest Note";
    static String sPreAdmissionInvestigationNoteText = "Automation pre admission investigation note test text";
    static String sPreAdmissionSpecialNoteName = "Auto Special Note";
    static String sPreAdmissionSpecialNoteText = "Automation pre admission Special note test text";
    static String updated = sPreAdmissionInstructionNoteText + " edited updated";

    @Test(enabled = true, description = "Validate adding new Instruction note from org setting")
    public void validateAddNewPreAdmissionInstructionNote() {
        // fill the details and save
        Page_PreAdmissionInstructionNote oPage_PreAdmissionInstructionNote = new Page_PreAdmissionInstructionNote(driver);
        oEHR_Data = new EHR_Data();
        boolean bInstructionNoteFound = false;
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            try {
                //get Organisation settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Pre-Admission Instruction Notes");
                //creating new Pre admission Instruction notes
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionInstructionNote.button_addInstructionNote, 16);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_PreAdmissionInstructionNote.button_addInstructionNote),
                        "Add Note Button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionInstructionNote.input_instructionNoteName, 16);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PreAdmissionInstructionNote.input_instructionNoteName, sPreAdmissionInstructionNoteName)
                        , "Pre admission Note Name entered: <b> " + sPreAdmissionInstructionNoteName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionInstructionNote.input_instructionNoteText, 16);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PreAdmissionInstructionNote.input_instructionNoteText, sPreAdmissionInstructionNoteText),
                        "Pre admission instruction Note Content entered: <b> " + sPreAdmissionInstructionNoteText + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionInstructionNote.button_saveInstructionNote, 16);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_PreAdmissionInstructionNote.button_saveInstructionNote),
                        "Save Pre admission instruction Note button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionInstructionNote.button_addInstructionNote, 16);
                //Find the created pre admission instruction note in the table
                for (WebElement eRowPreAdmissionInstructionNoteName : oPage_PreAdmissionInstructionNote.tableList_preAdmissionInstructionNoteName) {
                    if (eRowPreAdmissionInstructionNoteName.isDisplayed()) {
                        if (Cls_Generic_Methods.getTextInElement(eRowPreAdmissionInstructionNoteName).equalsIgnoreCase(sPreAdmissionInstructionNoteName)) {
                            m_assert.assertTrue(true, "Created Pre admission instruction Note found in the tables: <b> " + eRowPreAdmissionInstructionNoteName.getText() + " </b> ");
                            bInstructionNoteFound = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bInstructionNoteFound, "Pre admission instruction Note Found");
            } catch (Exception e) {
                m_assert.assertFatal("Error loading the application " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate adding new medication note from org setting")
    public void validateAddNewPreAdmissionMedicationNote() {
        // fill the details and save
        Page_PreAdmissionMedicationNote oPage_PreAdmissionMedicationNote = new Page_PreAdmissionMedicationNote(driver);
        oEHR_Data = new EHR_Data();
        boolean bMedicationNoteFound = false;
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            try {
                //get Organisation settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Pre-Admission Medication Notes");
                //creating new Pre admission medication notes
                Cls_Generic_Methods.scrollToTop();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PreAdmissionMedicationNote.button_addMedicationNote),
                        "Add Note Button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionMedicationNote.input_medicationNoteName, 16);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PreAdmissionMedicationNote.input_medicationNoteName, sPreAdmissionMedicationNoteName)
                        , "Pre admission medication Note Name entered: <b> " + sPreAdmissionMedicationNoteName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionMedicationNote.input_medicationNoteText, 16);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PreAdmissionMedicationNote.input_medicationNoteText, sPreAdmissionMedicationNoteText),
                        "Pre admission instruction Note Content entered: <b> " + sPreAdmissionMedicationNoteText + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionMedicationNote.button_saveMedNote, 16);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_PreAdmissionMedicationNote.button_saveMedNote),
                        "Save Pre admission medication Note button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionMedicationNote.button_addMedicationNote, 16);
                //Find the created pre admission medication note in the table
                for (WebElement eRowPreAdmissionMedicationNoteName : oPage_PreAdmissionMedicationNote.tableList_preAdmissionMedicationNoteName) {
                    if (eRowPreAdmissionMedicationNoteName.isDisplayed()) {
                        if (Cls_Generic_Methods.getTextInElement(eRowPreAdmissionMedicationNoteName).equalsIgnoreCase(sPreAdmissionMedicationNoteName)) {
                            m_assert.assertTrue(true, "Created Pre admission medication Note found in the tables: <b> " + eRowPreAdmissionMedicationNoteName.getText() + " </b> ");
                            bMedicationNoteFound = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bMedicationNoteFound, "Pre admission Medication Note Found");
            } catch (Exception e) {
                m_assert.assertFatal("Error loading the application " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate adding new investigation note from org setting")
    public void validateAddNewPreAdmissionInvestigationNote() {
        // fill the details and save
        Page_PreAdmissionInvestigationNote oPage_PreAdmissionInvestigationNote = new Page_PreAdmissionInvestigationNote(driver);
        oEHR_Data = new EHR_Data();
        boolean bProcedureNoteFound = false;
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            try {
                //get Organisation settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Pre-Admission Investigation Notes");
                //creating new Pre admission investigation notes
                Cls_Generic_Methods.scrollToTop();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PreAdmissionInvestigationNote.button_addInvestigationNote),
                        "Add Investigation Note Button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionInvestigationNote.input_InvestigationNoteName, 16);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PreAdmissionInvestigationNote.input_InvestigationNoteName, sPreAdmissionInvestigationNoteName)
                        , "Pre admission Investigation Note Name entered: <b> " + sPreAdmissionInvestigationNoteName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionInvestigationNote.input_InvestigationNoteText, 16);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PreAdmissionInvestigationNote.input_InvestigationNoteText, sPreAdmissionInvestigationNoteText),
                        "Pre admission Investigation Note Content entered: <b> " + sPreAdmissionInvestigationNoteText + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionInvestigationNote.button_saveInvestigationNote, 16);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_PreAdmissionInvestigationNote.button_saveInvestigationNote),
                        "Save Pre admission Investigation Note button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionInvestigationNote.button_addInvestigationNote, 16);
                //Find the created pre admission investigation note in the table
                for (WebElement eRowPreAdmissionInvestigationNoteName : oPage_PreAdmissionInvestigationNote.tableList_preAdmissionInvestigationNoteName) {
                    if (eRowPreAdmissionInvestigationNoteName.isDisplayed()) {
                        if (Cls_Generic_Methods.getTextInElement(eRowPreAdmissionInvestigationNoteName).equalsIgnoreCase(sPreAdmissionInvestigationNoteName)) {
                            m_assert.assertTrue(true, "Created Pre admission Investigation Note found in the tables: <b> " + eRowPreAdmissionInvestigationNoteName.getText() + " </b> ");
                            bProcedureNoteFound = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bProcedureNoteFound, "Pre admission Investigation Note Found");
            } catch (Exception e) {
                m_assert.assertFatal("Error loading the application " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate adding new special note from org setting")
    public void validateAddNewPreAdmissionSpecialNote() {
        // fill the details and save
        Page_PreAdmissionSpecialNote oPage_PreAdmissionSpecialNote = new Page_PreAdmissionSpecialNote(driver);
        oEHR_Data = new EHR_Data();
        boolean bSpecialNoteFound = false;
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            try {
                //get Organisation settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Pre-Admission Special Notes");
                //creating new Pre admission Special notes
                if (Cls_Generic_Methods.isElementDisplayed(oPage_PreAdmissionSpecialNote.button_addSpecialNote)) {
                    Cls_Generic_Methods.scrollToTop();

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PreAdmissionSpecialNote.button_addSpecialNote),
                            "Add Special Note Button clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionSpecialNote.input_nameField, 16);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PreAdmissionSpecialNote.input_nameField, sPreAdmissionSpecialNoteName)
                            , "Pre admission Special Note Name entered: <b> " + sPreAdmissionInvestigationNoteName + " </b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionSpecialNote.input_noteField, 16);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PreAdmissionSpecialNote.input_noteField, sPreAdmissionSpecialNoteText),
                            "Pre admission Special Note Content entered: <b> " + sPreAdmissionSpecialNoteText + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionSpecialNote.button_saveNote, 16);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_PreAdmissionSpecialNote.button_saveNote),
                            "Save Pre admission Special Note button clicked");
                    Cls_Generic_Methods.customWait(5);

                    //Find the created pre admission investigation note in the table
                    for (WebElement eRowPreAdmissionSpecialNoteName : oPage_PreAdmissionSpecialNote.tableList_preAdmissionSpecialNoteName) {
                        if (eRowPreAdmissionSpecialNoteName.isDisplayed()) {
                            if (Cls_Generic_Methods.getTextInElement(eRowPreAdmissionSpecialNoteName).equalsIgnoreCase(sPreAdmissionSpecialNoteName)) {
                                m_assert.assertTrue(true, "Created Pre admission Special Note found in the tables: <b> " + eRowPreAdmissionSpecialNoteName.getText() + " </b> ");
                                bSpecialNoteFound = true;
                                break;
                            }
                        }
                    }
                    m_assert.assertTrue(bSpecialNoteFound, "Pre admission Special Note Found");
                } else {
                    m_assert.assertInfo("Special Note already created");
                }
            } catch (Exception e) {
                m_assert.assertFatal("Error loading the application " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Essential Details are filled in Create New Patient")
    public void createAppointmentInOpd() {

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectFacility("TST");

            try {
                CommonActions.selectDepartmentOnApp("OPD");

                // Open the Search/Add patient dialog box
                Cls_Generic_Methods.customWait(3);
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

                // Entering Essential Form Data
                if (!myPatient.getsSALUTATION().isEmpty()) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                    oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()),
                            "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
                }

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                        "First Name is entered as - " + myPatient.getsFIRST_NAME());

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                                myPatient.getsMOBILE_NUMBER()),
                        "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());
                m_assert.assertTrue(
                        Cls_Generic_Methods.clickElementByJS(driver,
                                oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm),
                        "Validate that Create Appointment button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validate Essential Details are filled in Create New Patient")
    public void scheduleAdmissionToIpd() {

        oPage_CategoryMaster = new Page_CategoryMaster(driver);
        oPage_ItemMaster = new Page_ItemMaster(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        oEHR_Data = new EHR_Data();

        String concatPatientFullName = "";
        boolean bPatientNameFound = false;
        String admType = "Same Day";
        try {
            CommonActions.loginFunctionality("PR.Akash test");
            CommonActions.selectDepartmentOnApp("OPD");

            try {
                String MyQueueTab = "My Queue";
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage.get(0), 10);
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                        "Validate " + MyQueueTab + " tab is selected");
                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient" + e);
            }
            if (bPatientNameFound) {
                m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByAction(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission),
                        "Scheduled admission button is displayed");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission),
                        "Clicked on scheduled admission button");

                //Fill Schedule Admission Form
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_ScheduleAdmissionForm, 20),
                        "Scheduled admission form is displayed");

                //Admission Type Same day scheduling admission
                for (WebElement radioAdmissionBtn : oPage_ScheduleAdmission.list_radioAdmissionType) {
                    String admissionTypeBtn = Cls_Generic_Methods.getTextInElement(radioAdmissionBtn);
                    if (admissionTypeBtn.equalsIgnoreCase(admType)) {
                        try {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, radioAdmissionBtn), admissionTypeBtn + " Button in Admission Type is Clickable");
                        } catch (Exception e) {
                            m_assert.assertFatal(admissionTypeBtn + " button is not clickable");
                        }
                        break;
                    }
                }
                //Case Detail Tab
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduleAdmission.button_viewCaseDetails),
                        "View case details button is clicked");

                //Create Admission
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_createAdmission),
                        "Create admission button is clicked");

                Cls_Generic_Methods.customWait(4);

                //Assign Bed
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_assignBed, 15),
                        "Assigned bed Form is displayed");
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait(5);
            } else {
                m_assert.assertTrue(false, "searched patient is not present in patient list");
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        }

    }

    @Test(enabled = true, description = "Create pre admission note by selecting data that we created and validate it on preview")
    public void createPreAdmissionNoteAndValidatePreview() {
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        oEHR_Data = new EHR_Data();
        Page_PreAdmissionNote oPage_PreAdmissionNote = new Page_PreAdmissionNote(driver);

        String concatPatientFullName = "";
        boolean bPatientNameFound = false;
        try {
            CommonActions.loginFunctionality("PR.Akash test");
            CommonActions.selectDepartmentOnApp("OPD");

            try {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
                String MyQueueTab = "My Queue";
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage.get(0), 10);
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                        "Validate " + MyQueueTab + " tab is selected");
                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient" + e);
            }
            if (bPatientNameFound) {
                Cls_Generic_Methods.scrollToElementByJS(oPage_PatientAppointmentDetails.button_preAdmissionNote);
                Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_preAdmissionNote);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionNote.header_preAdNote, 5);

                m_assert.assertTrue(noteSelectStatus(oPage_PreAdmissionNote.list_instructionNote, sPreAdmissionInstructionNoteName),
                        "Investigation note selected");
                m_assert.assertTrue(notesTextValidate(oPage_PreAdmissionNote.list_notesText, sPreAdmissionInstructionNoteText),
                        "Instruction text validated");
                m_assert.assertTrue(noteSelectStatus(oPage_PreAdmissionNote.list_medicationNote, sPreAdmissionMedicationNoteName),
                        "Medication note selected");
                m_assert.assertTrue(notesTextValidate(oPage_PreAdmissionNote.list_notesText, sPreAdmissionMedicationNoteText),
                        "Medication text validated");
                m_assert.assertTrue(noteSelectStatus(oPage_PreAdmissionNote.list_investigationNote, sPreAdmissionInvestigationNoteName),
                        "Investigation note selected");
                m_assert.assertTrue(notesTextValidate(oPage_PreAdmissionNote.list_notesText, sPreAdmissionInvestigationNoteText),
                        "Investigation text validated");

                Cls_Generic_Methods.clickElement(oPage_PreAdmissionNote.button_saveNote);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionNote.text_previewPreAdmNote, 10);

                //Validate on preview
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PreAdmissionNote.text_previewPreAdmNote).equalsIgnoreCase("pre-admission note"),
                        "On preview pre admission note visible");
                m_assert.assertTrue(notesTextValidate(oPage_PreAdmissionNote.list_previewNotesText, sPreAdmissionInstructionNoteText),
                        "Instruction text validated");
                m_assert.assertTrue(notesTextValidate(oPage_PreAdmissionNote.list_previewNotesText, sPreAdmissionMedicationNoteText),
                        "Medication text validated");
                m_assert.assertTrue(notesTextValidate(oPage_PreAdmissionNote.list_previewNotesText, sPreAdmissionInvestigationNoteText),
                        "Investigation text validated");
                m_assert.assertTrue(notesTextValidate(oPage_PreAdmissionNote.list_previewNotesText, sPreAdmissionSpecialNoteText),
                        "Special text validated");
                Cls_Generic_Methods.driverRefresh();
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        }

    }

    @Test(enabled = true, description = "Jump to respective adm and edit,update the pre-op note ")
    public void editAndValidatePreOpAdmissionNoteInIpd() {
        oPage_OPD = new Page_OPD(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        oEHR_Data = new EHR_Data();
        Page_PreAdmissionNote oPage_PreAdmissionNote = new Page_PreAdmissionNote(driver);

        String concatPatientFullName = "";
        boolean bPatientNameFound = false;
        try {
            CommonActions.loginFunctionality("PR.Akash test");
            CommonActions.selectDepartmentOnApp("OPD");

            try {
                String MyQueueTab = "My Queue";
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage.get(0), 10);
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                        "Validate " + MyQueueTab + " tab is selected");
                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient" + e);
            }
            if (bPatientNameFound) {
                oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
                Cls_Generic_Methods.scrollToElementByJS(oPage_PatientAppointmentDetails.button_updatedJumpToAdmission);
                Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_updatedJumpToAdmission);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 16);
                Cls_Generic_Methods.scrollToElementByJS(oPage_PatientAppointmentDetails.button_preAdmissionNote);
                Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_preAdmissionNote);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionNote.text_previewPreAdmNote, 5);
                Cls_Generic_Methods.clickElement(oPage_PreAdmissionNote.button_editPreAdmNote);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionNote.header_preAdNote, 5);

                for (WebElement e : oPage_PreAdmissionNote.list_notesText) {
                    Cls_Generic_Methods.clearValuesInElement(e);
                    Cls_Generic_Methods.sendKeysIntoElement(e, updated);
                    break;

                }
                Cls_Generic_Methods.clickElement(oPage_PreAdmissionNote.button_saveNote);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAdmissionNote.text_previewPreAdmNote, 10);
                //Validate on preview
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PreAdmissionNote.text_previewPreAdmNote).equalsIgnoreCase("pre-admission note"),
                        "On preview pre admission note visible");
                m_assert.assertTrue(notesTextValidate(oPage_PreAdmissionNote.list_previewNotesText, updated),
                        "Instruction text updated validated ");
                Cls_Generic_Methods.clickElement(oPage_PreAdmissionNote.button_closePreAdmNote);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        }
    }


    public static boolean noteSelectStatus(List<WebElement> typeOfNote, String noteName) {
        boolean flag = false;
        try {
            for (WebElement eNotesName : typeOfNote) {
                if (Cls_Generic_Methods.getTextInElement(eNotesName).equalsIgnoreCase(noteName)) {
                    Cls_Generic_Methods.clickElement(eNotesName);
                    Cls_Generic_Methods.customWait(5);
                    flag = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean notesTextValidate(List<WebElement> sNoteText, String noteText) {
        boolean flag = false;
        try {
            for (WebElement eNoteText : sNoteText) {
                if (Cls_Generic_Methods.getTextInElement(eNoteText).equalsIgnoreCase(noteText)) {
                    Cls_Generic_Methods.customWait(5);
                    flag = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}



