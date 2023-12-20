package tests.settings.facilitySetting.clinical;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.OPD_Data;
import data.Settings_Data;
import data.settingsData.FacilitySettings_Data;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;
import pages.settings.facilitySettings.general.facilityContactList.Page_FacilityContactList;
import pages.settings.facilityUserNote.Page_FacilityUserNote;

public class FacilityUserNoteTest extends TestBase {

    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();

    @Test(enabled = true, description = "Create a Patient To Validate User Note")
    public void createPatientToValidateUserNote() {
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                try {
                    if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                        CommonActions.openPatientRegisterationAndAppointmentForm();
                    } else {
                        CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm, "Patient Details");
                        Cls_Generic_Methods.customWait(1);
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
                m_assert.assertTrue(
                        Cls_Generic_Methods.clickElementByJS(driver,
                                oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm),
                        "Validate that Create Appointment button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                        16 );

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Exception while creating patient" + e);
        }

    }

    @Test(enabled = true, description = "validate Add New Medical Certificate")
    public void validateAddNewMedicalCertificate() {
        Page_FacilityUserNote oPage_facilityUserNote = new Page_FacilityUserNote(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        String sSpecialityName = "Ophthalmology";
        String sUserNoteType = "Medical Certificate Template";
        boolean bMedicalCertificateFound = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            Cls_Generic_Methods.customWait(2);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility User Note");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);

                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_facilityUserNote.button_newTemplateForAddingUserNote), "clicked on New template");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.select_templateTypeForUserNote, 8);

                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_facilityUserNote.select_templateTypeForUserNote, sUserNoteType), "User Note Type selected as <b> " + sUserNoteType + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.input_nameFieldInUserNote, 8);

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_facilityUserNote.input_nameFieldInUserNote, FacilitySettings_Data.sMEDICAL_CERTIFICATE_NAME), "Medical certificate name is entered as <b> " + FacilitySettings_Data.sMEDICAL_CERTIFICATE_NAME + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.input_nameFieldInUserNote, 8);

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_facilityUserNote.input_headingFieldInUserNote, FacilitySettings_Data.sMEDICAL_CERTIFICATE_HEADING), "Medical certificate heading is entered as <b> " + FacilitySettings_Data.sMEDICAL_CERTIFICATE_HEADING + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.input_nameFieldInUserNote, 8);

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_facilityUserNote.input_subjectFieldInUserNote, FacilitySettings_Data.sMEDICAL_CERTIFICATE_SUBJECT), "Medical certificate subject is entered as <b>" + FacilitySettings_Data.sMEDICAL_CERTIFICATE_SUBJECT + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.input_nameFieldInUserNote, 8);

                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_facilityUserNote.select_specialityForCreatingUserNote, sSpecialityName), "speciality selected as <b> " + sSpecialityName + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.input_nameFieldInUserNote, 8);

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_facilityUserNote.input_contentFieldInUserNote, FacilitySettings_Data.sMEDICAL_CERTIFICATE_CONTENT), "Medical certificate content is entered as <b>" + FacilitySettings_Data.sMEDICAL_CERTIFICATE_CONTENT + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.input_nameFieldInUserNote, 8);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_facilityUserNote.button_submitUserNote), "Medical Certificate saved");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);

                    try {
                        for (WebElement eValueOfOtNameOnTable : oPage_facilityUserNote.list_Certificates) {
                            oPage_facilityUserNote = new Page_FacilityUserNote(driver);
                            if (Cls_Generic_Methods.getTextInElement(eValueOfOtNameOnTable).equals((FacilitySettings_Data.sMEDICAL_CERTIFICATE_NAME))) {
                                bMedicalCertificateFound = true;
                                break;
                            }
                            if (bMedicalCertificateFound) {
                                break;
                            }
                            System.out.println(eValueOfOtNameOnTable);
                            System.out.println(FacilitySettings_Data.sMEDICAL_CERTIFICATE_CONTENT);
                        }

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);
                        m_assert.assertTrue(bMedicalCertificateFound, "Medical certificate created as = <b>" + FacilitySettings_Data.sMEDICAL_CERTIFICATE_NAME + "</b>");

                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("" + e);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
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

    @Test(enabled = true, description = "validate Search And Edit Medical Certificate")
    public void validateSearchAndEditMedicalCertificate() {
        Page_FacilityUserNote oPage_facilityUserNote = new Page_FacilityUserNote(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility User Note");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);

                try {
                    boolean bMedicalCertificateFound = false;
                    int iMedicalCertificateIndex = -1;
                    for (WebElement eMedicalCertificateName : oPage_facilityUserNote.list_Certificates) {
                        oPage_facilityUserNote = new Page_FacilityUserNote(driver);
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eMedicalCertificateName);
                        if (sTableValue.equals(FacilitySettings_Data.sMEDICAL_CERTIFICATE_NAME)) {
                            System.out.println(eMedicalCertificateName);
                            iMedicalCertificateIndex = oPage_facilityUserNote.list_Certificates.indexOf(eMedicalCertificateName);
                            System.out.println(iMedicalCertificateIndex);
                            break;
                        }
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);
                    Cls_Generic_Methods.scrollToElementByJS(oPage_facilityUserNote.list_buttonForEditUserNote.get(iMedicalCertificateIndex));
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);

                    Cls_Generic_Methods.clickElementByJS(driver, oPage_facilityUserNote.list_buttonForEditUserNote.get(iMedicalCertificateIndex));
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.input_nameFieldInUserNote, 8);

                    Cls_Generic_Methods.clearValuesInElement(oPage_facilityUserNote.input_nameFieldInUserNote);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.input_nameFieldInUserNote, 8);

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_facilityUserNote.input_nameFieldInUserNote, FacilitySettings_Data.sUPDATED_MEDICAL_CERTIFICATE_NAME),
                            "Medical certificate updated as <b>" + FacilitySettings_Data.sUPDATED_MEDICAL_CERTIFICATE_NAME + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.input_nameFieldInUserNote, 8);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_facilityUserNote.button_submitUserNote), "medical certificate saved");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_facilityUserNote.input_searchUserNote, FacilitySettings_Data.sUPDATED_MEDICAL_CERTIFICATE_NAME);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);

                    String sTableValue = null;
                    for (WebElement eValueOfOtNameOnTable : oPage_facilityUserNote.list_Certificates) {
                        sTableValue = Cls_Generic_Methods.getTextInElement(eValueOfOtNameOnTable);
                        if (sTableValue.contains((FacilitySettings_Data.sUPDATED_MEDICAL_CERTIFICATE_NAME))) {
                            bMedicalCertificateFound = true;
                            break;
                        }
                        if (bMedicalCertificateFound) {
                            break;
                        }
                    }
                    m_assert.assertTrue(bMedicalCertificateFound, " Medical Certificate present in the list = <b>" + sTableValue + "</b>");

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
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

    @Test(enabled = true, description = "validate Medical Certificate In Patient Summary")
    public void validateMedicalCertificateInPatientSummary() {
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        Page_FacilityUserNote oPage_facilityUserNote = new Page_FacilityUserNote(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        boolean bPatientNameFound = false;
        boolean bMedicalCertificateFoundInSummary = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OPD");
            Cls_Generic_Methods.customWait(1);

            try {
                String tabToSelect = OPD_Data.tab_ALL;
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect), "Validate " + tabToSelect + " tab is selected");
                Cls_Generic_Methods.customWait(1);
                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                m_assert.assertTrue(bPatientNameFound, "Validate Patient  " + concatPatientFullName + " is clicked in " + tabToSelect + "");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
            if (bPatientNameFound)
               // Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_markPatientArrivedOPD, 8);
         //   Cls_Generic_Methods.clickElement(driver, oPage_facilityUserNote.button_markPatientArrivedOPD);
            try {
                String tabToSelect = OPD_Data.tab_MY_QUEUE;
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
                        "Validate " + tabToSelect + " tab is selected");
                Cls_Generic_Methods.customWait(1);
                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                m_assert.assertTrue(bPatientNameFound, "Validate Patient  " + concatPatientFullName + " is clicked in " + tabToSelect + "");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }

            if (bPatientNameFound) {

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_summary, 8);
                Cls_Generic_Methods.clickElement(driver, oPage_facilityUserNote.button_summary);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_certificateInSummary, 8);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_facilityUserNote.button_certificateInSummary);
                try {
                    for (WebElement eValueOfOtNameOnTable : oPage_facilityUserNote.list_medicalCertificatesInOpdSummary) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eValueOfOtNameOnTable);
//                    System.out.println(sTableValue);
//                    System.out.println(FacilitySettings_Data.sUPDATED_MEDICAL_CERTIFICATE_NAME);
                        if (sTableValue.equals((FacilitySettings_Data.sUPDATED_MEDICAL_CERTIFICATE_NAME))) {
                            bMedicalCertificateFoundInSummary = true;
                            //	Cls_Generic_Methods.clickElementByJS(driver,eValueOfOtNameOnTable);
                            Cls_Generic_Methods.clickElementByAction(driver, eValueOfOtNameOnTable);
                            break;
                        }
                    }
                    if (bMedicalCertificateFoundInSummary) {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_saveMedicalCertificateInSummary, 8);
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_facilityUserNote.button_saveMedicalCertificateInSummary);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_closeUserNotePreview, 8);
                        Cls_Generic_Methods.clickElement(driver, oPage_facilityUserNote.button_closeUserNotePreview);
                        m_assert.assertTrue(true, "Medical certificate name = <b>" + FacilitySettings_Data.sUPDATED_MEDICAL_CERTIFICATE_NAME + "</b> present in list");
                    } else {
                        m_assert.assertTrue(false, "Medical certificate name = <b>" + FacilitySettings_Data.sUPDATED_MEDICAL_CERTIFICATE_NAME + "</b> present in not present in list");
                    }
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

    @Test(enabled = true, description = "delete Created Medical Certificate")
    public void deleteCreatedMedicalCertificate() {
        Page_FacilityUserNote oPage_facilityUserNote = new Page_FacilityUserNote(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility User Note");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);
                try {
                    int iMedicalCertificateIndex = -1;
                    for (WebElement eMedicalCertificateName : oPage_facilityUserNote.list_Certificates) {
                        oPage_facilityUserNote = new Page_FacilityUserNote(driver);
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eMedicalCertificateName);
                        if (sTableValue.equals(FacilitySettings_Data.sUPDATED_MEDICAL_CERTIFICATE_NAME)) {
                            iMedicalCertificateIndex = oPage_facilityUserNote.list_Certificates.indexOf(eMedicalCertificateName);
                            break;
                        }
                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);
                    Cls_Generic_Methods.scrollToElementByJS(oPage_facilityUserNote.list_buttonForDeleteUserNote.get(iMedicalCertificateIndex));
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_facilityUserNote.list_buttonForDeleteUserNote.get(iMedicalCertificateIndex)),
                            "clicked on delete button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_confirmDeleteMedicalCertificate, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver, oPage_facilityUserNote.button_confirmDeleteMedicalCertificate),
                            "medical certificate deleted");
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
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

    @Test(enabled = true, description = "validate Add New Referral Message")
    public void validateAddNewReferralMessage() {
        Page_FacilityUserNote oPage_facilityUserNote = new Page_FacilityUserNote(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        String sSpecialityName = "Ophthalmology";
        String sUserNoteType = "Referral Message Template";
        boolean bReferralMessageFound = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            Cls_Generic_Methods.customWait(2);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility User Note");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);
                try {
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElementByJS(driver, oPage_facilityUserNote.button_newTemplateForAddingUserNote),
                            "clicked on New template");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.select_templateTypeForUserNote, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.selectElementByVisibleText(oPage_facilityUserNote.select_templateTypeForUserNote, sUserNoteType),
                            "User Note Type selected as <b> " + sUserNoteType + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.input_nameFieldInUserNote, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(oPage_facilityUserNote.input_nameFieldInUserNote, FacilitySettings_Data.sREFERRAL_MESSAGE_NAME),
                            "Referral message name is entered as <b> " + FacilitySettings_Data.sREFERRAL_MESSAGE_NAME + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.input_nameFieldInUserNote, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(oPage_facilityUserNote.input_headingFieldInUserNote, FacilitySettings_Data.sREFERRAL_MESSAGE_HEADING),
                            "Referral message heading is entered as <b> " + FacilitySettings_Data.sREFERRAL_MESSAGE_HEADING + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.input_nameFieldInUserNote, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(oPage_facilityUserNote.input_subjectFieldInUserNote, FacilitySettings_Data.sREFERRAL_MESSAGE_SUBJECT),
                            "Referral message subject is entered as <b> " + FacilitySettings_Data.sREFERRAL_MESSAGE_SUBJECT + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.input_nameFieldInUserNote, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.selectElementByVisibleText(oPage_facilityUserNote.select_specialityForCreatingUserNote, sSpecialityName),
                            "speciality selected as <b>" + sSpecialityName + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.input_nameFieldInUserNote, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(oPage_facilityUserNote.input_contentFieldInUserNote, FacilitySettings_Data.sREFERRAL_MESSAGE_CONTENT),
                            "Referral message content is entered as <b> " + FacilitySettings_Data.sREFERRAL_MESSAGE_CONTENT + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.input_nameFieldInUserNote, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver, oPage_facilityUserNote.button_submitUserNote),
                            "Referral message template saved");
                    Cls_Generic_Methods.customWait(2);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.selectElementByVisibleText(oPage_facilityUserNote.select_userNoteType, sUserNoteType),
                            "User Note Type selected as <b>" + sUserNoteType + "</b>");
                    Cls_Generic_Methods.customWait(2);
                    try {
                        for (WebElement eValueOfOtNameOnTable : oPage_facilityUserNote.list_Certificates) {
                            oPage_facilityUserNote = new Page_FacilityUserNote(driver);
                            if (Cls_Generic_Methods.getTextInElement(eValueOfOtNameOnTable).equals((FacilitySettings_Data.sREFERRAL_MESSAGE_NAME))) {
                                bReferralMessageFound = true;
                                break;
                            }
                            if (bReferralMessageFound) {
                                break;
                            }
                        }

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);
                        m_assert.assertTrue(bReferralMessageFound, "Referral message template created as  = <b>" + FacilitySettings_Data.sREFERRAL_MESSAGE_NAME + "</b> added");
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("" + e);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
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

    @Test(enabled = true, description = "validate Search And Edit Referral Message")
    public void validateSearchAndEditReferralMessage() {
        Page_FacilityUserNote oPage_facilityUserNote = new Page_FacilityUserNote(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        String sUserNoteType = "Referral Message Template";
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility User Note");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);
                m_assert.assertTrue(
                        Cls_Generic_Methods.selectElementByVisibleText(oPage_facilityUserNote.select_userNoteType, sUserNoteType),
                        "User Note Type selected as <b> " + sUserNoteType + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);
                try {
                    boolean bEditActionPerformed = false;
                    int iReferralMessageIndex = -1;
                    for (WebElement eReferralMessage : oPage_facilityUserNote.list_Certificates) {
                        oPage_facilityUserNote = new Page_FacilityUserNote(driver);
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eReferralMessage);
                        if (sTableValue.equals(FacilitySettings_Data.sREFERRAL_MESSAGE_NAME)) {
                            System.out.println(eReferralMessage);
                            iReferralMessageIndex = oPage_facilityUserNote.list_Certificates.indexOf(eReferralMessage);
                            System.out.println(iReferralMessageIndex);
                            break;
                        }
                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);
                    Cls_Generic_Methods.scrollToElementByJS(oPage_facilityUserNote.list_buttonForEditUserNote.get(iReferralMessageIndex));
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_facilityUserNote.list_buttonForEditUserNote.get(iReferralMessageIndex));
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.input_nameFieldInUserNote, 8);
                    Cls_Generic_Methods.clearValuesInElement(oPage_facilityUserNote.input_nameFieldInUserNote);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.input_nameFieldInUserNote, 8);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_facilityUserNote.input_nameFieldInUserNote, FacilitySettings_Data.sUPDATED_REFERRAL_MESSAGE_NAME),
                            "Updated the Referral Message as <b>" + FacilitySettings_Data.sUPDATED_REFERRAL_MESSAGE_NAME + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.input_nameFieldInUserNote, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver, oPage_facilityUserNote.button_submitUserNote),
                            "Referral message template saved");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.selectElementByVisibleText(oPage_facilityUserNote.select_userNoteType, sUserNoteType),
                            "User Note Type selected as <b> " + sUserNoteType + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_facilityUserNote.input_searchUserNote, FacilitySettings_Data.sUPDATED_REFERRAL_MESSAGE_NAME);
                    Cls_Generic_Methods.customWait(2);
                    for (WebElement eValueOfOtNameOnTable : oPage_facilityUserNote.list_Certificates) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eValueOfOtNameOnTable);
                        if (sTableValue.equals((FacilitySettings_Data.sUPDATED_REFERRAL_MESSAGE_NAME))) {
                            bEditActionPerformed = true;
                            break;
                        }
                        if (bEditActionPerformed) {
                            break;
                        }
                    }
                    m_assert.assertTrue(bEditActionPerformed, " Referral message template edited as = <b>" +
                            FacilitySettings_Data.sUPDATED_REFERRAL_MESSAGE_NAME + "</b>");
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
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

    @Test(enabled = true, description = "validate Referral Message In Patient Summary")
    public void validateReferralMessageInPatientSummary() {
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        Page_FacilityUserNote oPage_facilityUserNote = new Page_FacilityUserNote(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        boolean bPatientNameFound = false;
        boolean bReferralTemplateInSummary = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OPD");
            Cls_Generic_Methods.customWait(1);
            try {
                String tabToSelect = OPD_Data.tab_MY_QUEUE;
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect), "Validate " + tabToSelect + " tab is selected");
                Cls_Generic_Methods.customWait(1);
                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                m_assert.assertTrue(bPatientNameFound, "Validate Patient  " + concatPatientFullName + " is clicked in " + tabToSelect + "tab");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
            if (bPatientNameFound){
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_summary, 8);
                Cls_Generic_Methods.clickElement(driver, oPage_facilityUserNote.button_summary);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_certificateInSummary, 8);
                Cls_Generic_Methods.clickElement(driver, oPage_facilityUserNote.button_referralMessageInSummary);
                try {

                    for (WebElement eValueOfOtNameOnTable : oPage_facilityUserNote.list_referralMessageInOpdSummary) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eValueOfOtNameOnTable);
                        if (sTableValue.equals((FacilitySettings_Data.sUPDATED_REFERRAL_MESSAGE_NAME))) {
                            bReferralTemplateInSummary = true;
                            Cls_Generic_Methods.clickElementByAction(driver, eValueOfOtNameOnTable);
                            break;
                        }
                    }
                    if (bReferralTemplateInSummary) {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_saveReferralMessageInSummary, 8);
                        Cls_Generic_Methods.clickElement(driver, oPage_facilityUserNote.button_saveReferralMessageInSummary);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_closeUserNotePreview, 8);
                        Cls_Generic_Methods.clickElement(driver, oPage_facilityUserNote.button_closeUserNotePreview);
                        m_assert.assertTrue(true, "Referral Template  = <b>" + FacilitySettings_Data.sUPDATED_REFERRAL_MESSAGE_NAME + "</b> present in list");
                    } else {
                        m_assert.assertTrue(false, "Referral Template  = <b>" + FacilitySettings_Data.sUPDATED_REFERRAL_MESSAGE_NAME + "</b> is not present in list");
                    }
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

    @Test(enabled = true, description = "delete Created Referral Message")
    public void deleteCreatedReferralMessage() {
        Page_FacilityUserNote oPage_facilityUserNote = new Page_FacilityUserNote(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        String sUserNoteType = "Referral Message Template";
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility User Note");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);
                m_assert.assertTrue(
                        Cls_Generic_Methods.selectElementByVisibleText(oPage_facilityUserNote.select_userNoteType, sUserNoteType),
                        "User Note Type selected as" + sUserNoteType);
                Cls_Generic_Methods.customWait(1);
                try {
                    int iReferralMessageIndex = -1;
                    for (WebElement eMedicalCertificateName : oPage_facilityUserNote.list_Certificates) {
                        oPage_facilityUserNote = new Page_FacilityUserNote(driver);
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eMedicalCertificateName);
                        if (sTableValue.equals(FacilitySettings_Data.sUPDATED_REFERRAL_MESSAGE_NAME)) {
                            iReferralMessageIndex = oPage_facilityUserNote.list_Certificates.indexOf(eMedicalCertificateName);
                            break;
                        }
                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);
                    Cls_Generic_Methods.scrollToElementByJS(oPage_facilityUserNote.list_buttonForDeleteUserNote.get(iReferralMessageIndex));
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_newTemplateForAddingUserNote, 8);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_facilityUserNote.list_buttonForDeleteUserNote.get(iReferralMessageIndex)),
                            "clicked on delete button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_facilityUserNote.button_confirmDeleteReferralMessage, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver, oPage_facilityUserNote.button_confirmDeleteReferralMessage),
                            "Referral Message template deleted");
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
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

    @Test(enabled = true, description = "remove Patient Created For User Note")
    public void removePatientCreatedForUserNote() {
        Page_OPD oPage_OPD = new Page_OPD(driver);
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        boolean bPatientNameFound = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Page_FacilityContactList oPage_FacilityContactList = new Page_FacilityContactList(driver);
            Page_FacilityUserNote oPage_FacilityUserNote = new Page_FacilityUserNote(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OPD");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_modalPatientSearch, 8);
            try {
                String tabToSelect = OPD_Data.tab_MY_QUEUE;
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect), "Validate " + tabToSelect + " tab is selected");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_modalPatientSearch, 8);
                String patientName = null;
                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                m_assert.assertTrue(bPatientNameFound, "Validate Patient  " + concatPatientFullName + " is clicked in " + tabToSelect + " ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_modalPatientSearch, 8);
                Cls_Generic_Methods.scrollToElementByAction(driver, oPage_FacilityUserNote.button_markPatientCompleted);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityUserNote.button_markPatientCompleted, 8);
                m_assert.assertTrue(
                        Cls_Generic_Methods.clickElement(driver, oPage_FacilityUserNote.button_markPatientCompleted),
                        "Clicked on mark patient completed button");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while validating patient in Not arrived tab" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Exception while cancelling appointment" + e);
        }
    }

}