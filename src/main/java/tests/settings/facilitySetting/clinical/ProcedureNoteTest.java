package tests.settings.facilitySetting.clinical;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.*;
import data.settingsData.FacilitySettings_Data;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.ipd.forms.intraOperative.Page_OperativeForm;
import pages.ipd.forms.intraOperative.Page_PreAnaesthesiaTemplate;
import pages.ipd.forms.preOperative.Page_AdmissionInPreOperative;
import pages.opd.Page_OPD;
import pages.settings.facilitySettings.clinical.facilityProcedureNote.Page_FacilityProcedureNote;
import pages.settings.facilitySettings.general.wardSetup.Page_WardSetup;

public class ProcedureNoteTest extends TestBase {

    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();

    /*@Test(enabled = true, description = "Validate Patient for Procedure Note ")
    public void createPatientToValidateProcedureNote() {
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_WardSetup oPage_WardSetUp = new Page_WardSetup(driver);
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        boolean bPatientNameFound = false;
        String NotArrivedTab = OPD_Data.tab_NOT_ARRIVED;
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                // Open the Search/Add patient dialog box
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

                // Entering Essential Form Data
                if (!myPatient.getsSALUTATION().isEmpty()) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()), "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
                }

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()), "First Name is entered as - " + myPatient.getsFIRST_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_middleNameOnPatientRegForm, myPatient.getsMIDDLE_NAME()), "Middle Name is entered as - " + myPatient.getsMIDDLE_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_lastNameOnPatientRegForm, myPatient.getsLAST_NAME()), "Last Name is entered as - " + myPatient.getsLAST_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, myPatient.getsMOBILE_NUMBER()), "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm), "Validate that Create Appointment button is clicked");

                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_WardSetUp.tab_appointmentDetails, 20), "Wait until appointment details is displayed ");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5), "Patient details displayed ");

                //go in opd and schedule admission
                //	CommonActions.selectDepartmentOnApp("OPD");
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, NotArrivedTab), "Validate " + NotArrivedTab + " tab is selected");
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                //select patient
                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

                if (bPatientNameFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
                    Cls_Generic_Methods.customWait(1);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientArrived), "Mark Patient arrived clicked ");
                    Cls_Generic_Methods.customWait(3);

                    m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByJS(oPage_WardSetUp.button_scheduledAdmission), "Scroll to Scheduled Admission button");
                    //click on scheduled admission
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_WardSetUp.button_scheduledAdmission));
                    // Wait until admission form is displayed
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_WardSetUp.form_scheduledAdmission, 10), "Wait until scheduled admission form is displayed ");
                    //fill essential details and create admission
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WardSetUp.radioButton_sameDay));
                    Cls_Generic_Methods.customWait(3);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WardSetUp.tab_caseDetails));
                    Cls_Generic_Methods.customWait(3);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WardSetUp.button_createAdmission));
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WardSetUp.window_assignBed, 10), "Wait until assign bed window is displayed ");

                    Cls_Generic_Methods.driverRefresh();
                    m_assert.assertTrue(Cls_Generic_Methods.waitForPageLoad(driver,
                            20), "Appointment details are displayed ");

                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }


    }*/

    @Test(enabled = true, description = "Validate Patient for Procedure Note")
    public void createPatientToValidateProcedureNote() {

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
                       // m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.input_selectWard),
                          //      "Ward dropdown Clicked");
                        m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_ScheduleAdmission.input_selectWard, 1),
                                "Ward Value Selected");
                        Cls_Generic_Methods.customWait(2);
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

    @Test(enabled = true, description = "Validate adding new procedure note")
    public void validateAddNewProcedureNote() {
        // fill the details and save
        Page_FacilityProcedureNote oPage_FacilityProcedureNote = new Page_FacilityProcedureNote(driver);
        boolean bSpecialitySelected = false;
        boolean bProcedureNoteFound = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();

            try {
                //get facility settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility In-patient Surgical Notes");

                //creating new procedure note
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityProcedureNote.button_addProcedureNote, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_FacilityProcedureNote.button_addProcedureNote),
                        "Add Procedure Note Button clicked");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityProcedureNote.header_procedureNote, 5);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityProcedureNote.input_procedureNoteName, FacilitySettings_Data.sPROCEDURE_NOTE_NAME)
                        , "Procedure Note Name entered: <b> " + FacilitySettings_Data.sPROCEDURE_NOTE_NAME + " </b>");

                //select speciality from the list
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FacilityProcedureNote.dropdown_selectSpeciality), "Select Speciality dropdown Clicked");

                for (WebElement listSpecialityName : oPage_FacilityProcedureNote.list_dropdownSpeciality) {
                    if (listSpecialityName.isDisplayed()) {
                        if(Cls_Generic_Methods.getTextInElement(listSpecialityName).equalsIgnoreCase(FacilitySettings_Data.sPROCEDURE_NOTE_SPECIALITY)){
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, listSpecialityName), "Speciality selected from dropdown <b> " + listSpecialityName.getText() + " </b> ");
                            bSpecialitySelected = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bSpecialitySelected, "Speciality Selected");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityProcedureNote.input_procedureNoteContent, 3);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityProcedureNote.input_procedureNoteContent, FacilitySettings_Data.sPROCEDURE_NOTE),
                        "Procedure Note Content entered: <b> " + FacilitySettings_Data.sPROCEDURE_NOTE + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FacilityProcedureNote.button_saveProcedureNote),
                        "Save Procedure Note button clicked");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityProcedureNote.button_addProcedureNote,10);
                //Find the created procedure note in the table
                for (WebElement eRowProcedureNoteName : oPage_FacilityProcedureNote.tableList_procedureNoteName) {
                    if (eRowProcedureNoteName.isDisplayed()) {
                        if(Cls_Generic_Methods.getTextInElement(eRowProcedureNoteName).equalsIgnoreCase(FacilitySettings_Data.sPROCEDURE_NOTE_NAME)){
                            m_assert.assertTrue(true, "Created procedure Note found in the tables: <b> " + eRowProcedureNoteName.getText() + " </b> ");
                            bProcedureNoteFound = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bProcedureNoteFound, "Procedure Note Found");

            } catch (Exception e) {
                m_assert.assertFatal("Error loading the application " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate procedure note in operative form")
    public void validateProcedureNoteInOperativeForm() {
        // go in ipd, create admission and under operative search the created procedure note
        Page_IPD oPage_IPD = new Page_IPD(driver);
        String tabToSelect = IPD_Data.tab_Scheduled_Today;
        Page_AdmissionInPreOperative oPage_AdmissionInPreOperative = new Page_AdmissionInPreOperative(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_OperativeForm oPage_OperativeForm = new Page_OperativeForm(driver);
        Page_PreAnaesthesiaTemplate oPage_PreAnaesthesiaTemplate = new Page_PreAnaesthesiaTemplate(driver);
        int indexOfProcedureNote = -1;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            boolean bValidatePatientFound = false;
            myPatient = map_PatientsInExcel.get(patientKey);

            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();

            //select IPD from Departments list
            CommonActions.selectDepartmentOnApp("IPD");
            Cls_Generic_Methods.customWait(8);

            m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, tabToSelect), "Validate " + tabToSelect + " tab is selected");
            Cls_Generic_Methods.customWait(1);

            bValidatePatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, concatPatientFullName);
            m_assert.assertTrue(bValidatePatientFound, "Validate patient " + concatPatientFullName + " is found");

            if (bValidatePatientFound) {
                m_assert.assertTrue(true, "Validate the Pre-Operative forms section is displayed");

                Cls_Generic_Methods.scrollToElementByJS(oPage_IPD.text_preOperativeSection);
                try {
                    m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_AdmissionInPreOperative.button_admissionInPreOperative),
                            "<b>Validate Admission button is visible under the Pre-Operative forms</b>");

                    Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.button_admissionInPreOperative);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AdmissionInPreOperative.button_saveOnModalHeader,5);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdmissionInPreOperative.button_saveOnModalHeader),
                            "Validate Save button is clicked on Admission under Pre-Operative");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);

                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_preOperativeSection, 16);

                    try {
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

                        Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.tab_surgicalNote);
                        Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.input_searchProcedureNote);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_OperativeForm.input_searchProcedureNote, FacilitySettings_Data.sPROCEDURE_NOTE_NAME);
                        Cls_Generic_Methods.customWait(5);


                        //Find the created advice set from the list
                        for (WebElement eRowProcedureNoteName : oPage_OperativeForm.list_savedProcedureNotes) {
                            if (eRowProcedureNoteName.isDisplayed()) {
                                if(Cls_Generic_Methods.getTextInElement(eRowProcedureNoteName).contains(FacilitySettings_Data.sPROCEDURE_NOTE_NAME)){
                                    indexOfProcedureNote = oPage_OperativeForm.list_savedProcedureNotes.indexOf(eRowProcedureNoteName);
                                    Cls_Generic_Methods.clickElement(eRowProcedureNoteName);
                                    Cls_Generic_Methods.customWait(3);
                                    m_assert.assertTrue(true, "Procedure note found <b> " + eRowProcedureNoteName.getText() + " </b>");
                                    break;
                                }
                            }
                        }

                        if (indexOfProcedureNote < 0) {
                            m_assert.assertTrue(false, "<b>" + FacilitySettings_Data.sPROCEDURE_NOTE_NAME + " </b> not found in Operative form by Search");
                        } else {
                            m_assert.assertTrue(true, "<b>" + FacilitySettings_Data.sPROCEDURE_NOTE_NAME + " </b> found in Operative Form by Search");
                        }
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
                        Cls_Generic_Methods.customWait(5);
                    } catch (Exception e) {
                        m_assert.assertFatal("Error while searching procedure note " + e);
                    }

                } catch (Exception e) {
                    m_assert.assertFatal("Error in admission form " + e);
                }
            }

        } catch (Exception e) {
            m_assert.assertFatal("Error while loading " + e);
        }

    }

    @Test(enabled = true, description = "Validate search and edit created procedure note")
    public void validateSearchAndEditProcedureNote() {
        //search the created medication list and edit it
        Page_FacilityProcedureNote oPage_FacilityProcedureNote = new Page_FacilityProcedureNote(driver);
        int indexOfEditButton = -1;
        int indexOfProcedureNote = -1;
        String editProcedureNoteName = "Procedure Note Name updated";
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                //get advice set settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility In-patient Surgical Notes");

                //Search advice set
                Cls_Generic_Methods.clickElementByJS(driver, oPage_FacilityProcedureNote.input_searchProcedureNote);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityProcedureNote.input_searchProcedureNote, FacilitySettings_Data.sPROCEDURE_NOTE_NAME);
                Cls_Generic_Methods.customWait(5);

                //Find the created advice set on the table
                for (WebElement eRowProcedureNoteName : oPage_FacilityProcedureNote.tableList_procedureNoteName) {
                    if (eRowProcedureNoteName.isDisplayed()) {
                        if(Cls_Generic_Methods.getTextInElement(eRowProcedureNoteName).equalsIgnoreCase(FacilitySettings_Data.sPROCEDURE_NOTE_NAME)){
                            indexOfProcedureNote = oPage_FacilityProcedureNote.tableList_procedureNoteName.indexOf(eRowProcedureNoteName);
                            m_assert.assertTrue(true, "Procedure Note found to be search and edit <b> " + eRowProcedureNoteName.getText() + " </b>");
                            break;
                        }
                    }
                }

                if (indexOfProcedureNote < 0) {
                    m_assert.assertTrue(false, "<b>" + FacilitySettings_Data.sPROCEDURE_NOTE_NAME + " </b> not found in Procedure Note by Search");
                } else {
                    m_assert.assertTrue(true, "<b>" + FacilitySettings_Data.sPROCEDURE_NOTE_NAME + " </b> found in Procedure Note by Search");

                }

                //Find the respected edit button and edit content
                for (WebElement btn_edit : oPage_FacilityProcedureNote.tableList_procedureNoteEditButton) {
                    if (btn_edit.isDisplayed()) {
                        indexOfEditButton = oPage_FacilityProcedureNote.tableList_procedureNoteEditButton.indexOf(btn_edit);
                    }

                    if (indexOfProcedureNote == indexOfEditButton) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, btn_edit), "<b> Procedure Note edit </b> button clicked ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityProcedureNote.button_updateProcedureNote, 5);

                        Cls_Generic_Methods.customWait();
                        Cls_Generic_Methods.clearValuesInElement(oPage_FacilityProcedureNote.input_procedureNoteContent);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityProcedureNote.input_procedureNoteContent, editProcedureNoteName),
                                "Procedure Note content updated: <b> " + editProcedureNoteName + "</b>");

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FacilityProcedureNote.button_updateProcedureNote), "<b> Procedure Note update </b> clicked ");
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityProcedureNote.button_addProcedureNote, 2);
              //  Cls_Generic_Methods.clearValuesInElement(oPage_FacilityProcedureNote.input_searchProcedureNote);

            } catch (Exception e) {
                m_assert.assertFatal("Procedure Note edit button not found " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    /*@Test(enabled = true, description = "Validate deleting patient made for test procedure note")
    public void removePatientCreatedForValidateProcedureNote() {
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = "";
        String NotArrivedTab = OPD_Data.tab_NOT_ARRIVED;
        String myQueueTab = OPD_Data.tab_MY_QUEUE;
        boolean bPatientFound = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OPD");
            try {
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();

                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, myQueueTab),
                        "Validate " + myQueueTab + " tab is selected");
                Cls_Generic_Methods.customWait(1);
                bPatientFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                if (bPatientFound) {


                    Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientNotArrived);
                    m_assert.assertTrue(
                            CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, NotArrivedTab),
                            "Validate " + NotArrivedTab + " tab is selected");
                    Cls_Generic_Methods.customWait(1);

                    CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                            10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_cancelAppointment),
                            "<b> Cancel Button </b> " + " clicked on page for patient <b> " + concatPatientFullName + " </b>");
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.header_cancelAppointment, 5),
                            "<b> Cancel appointment form displayed </b> ");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_cancelAppointmentForm),
                            "Appointment cancel button clicked for patient <b> " + concatPatientFullName + " </b> ");
                    Cls_Generic_Methods.waitForPageLoad(driver, 5);
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while deleting patient" + e);
            }
        } catch (Exception e) {
            m_assert.assertTrue(false, "Exception while running application" + e);
            e.printStackTrace();
        }
    }*/
    @Test(enabled = true, description = "Remove the patient created for validation")
    public void removePatientCreatedForValidateProcedureNote() {

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


    @Test(enabled = true, description = "Validate deleting procedure note")
    public void deleteCreatedProcedureNote() {
        Page_FacilityProcedureNote oPage_FacilityProcedureNote = new Page_FacilityProcedureNote(driver);
        int indexOfDeleteButton = -1;
        int indexOfProcedureNote = -1;
        boolean bDeleteButtonFound = false;
        boolean bProcedureNoteFound = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                //get advice set settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility In-patient Surgical Notes");

                //Find the created advice set on the table
                for (WebElement eRowProcedureNoteName : oPage_FacilityProcedureNote.tableList_procedureNoteName) {
                    if (eRowProcedureNoteName.isDisplayed()) {
                        if(Cls_Generic_Methods.getTextInElement(eRowProcedureNoteName).equalsIgnoreCase(FacilitySettings_Data.sPROCEDURE_NOTE_NAME)){
                            indexOfProcedureNote = oPage_FacilityProcedureNote.tableList_procedureNoteName.indexOf(eRowProcedureNoteName);
                            m_assert.assertTrue(true, "Procedure Note found to be deleted <b> " + eRowProcedureNoteName.getText() + " </b>");

                            bProcedureNoteFound = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bProcedureNoteFound, "Procedure Note Status <b> " + bProcedureNoteFound + "</b> ");

                //Find the respected delete button and delete
                for (WebElement btn_del : oPage_FacilityProcedureNote.tableList_procedureNoteDeleteButton) {
                    if (btn_del.isDisplayed()) {
                        indexOfDeleteButton = oPage_FacilityProcedureNote.tableList_procedureNoteDeleteButton.indexOf(btn_del);
                    }

                    if (indexOfProcedureNote == indexOfDeleteButton) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, btn_del), "<b> Procedure Note delete </b> button clicked ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityProcedureNote.button_confirmDeleteProcedureNote, 5);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FacilityProcedureNote.button_confirmDeleteProcedureNote), "<b> Procedure Note delete </b> confirmation clicked ");
                        Cls_Generic_Methods.customWait(3);
                        bDeleteButtonFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(bDeleteButtonFound, "Delete button status <b> " + bDeleteButtonFound + " </b> for the Procedure Note");

            } catch (Exception e) {
                m_assert.assertFatal("Procedure Note delete button not found " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

}