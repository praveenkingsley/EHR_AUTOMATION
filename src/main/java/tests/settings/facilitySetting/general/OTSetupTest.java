package tests.settings.facilitySetting.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.Settings_Data;
import data.settingsData.FacilitySettings_Data;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;
import pages.settings.facilitySettings.general.facilityContactList.Page_FacilityContactList;
import pages.settings.facilitySettings.general.otSetup.Page_OtSetup;

import java.util.List;

public class OTSetupTest extends TestBase {

    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();

    @Test(enabled = true, description = "Create Patient To Validate OT Setup")
    public void createPatientToValidateOTSetup() {
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
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
                Cls_Generic_Methods.customWait(10);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Exception while creating patient" + e);
        }
    }

    @Test(enabled = true, description = "Validate Add New OT Room Functionality")
    public void validateAddNewOtRoom() {
        Page_OtSetup oPage_OtSetup = new Page_OtSetup(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        boolean OtRoomNamefound = false;
        String sFacilityName = "TESTING_FACILITY";
        String sSpecialityName = "Ophthalmology";
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "OT Setup");
                Cls_Generic_Methods.customWait(2);

                try {
                    Cls_Generic_Methods.scrollToTop();
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver, oPage_OtSetup.select_facilityForOtSetup),
                            "clicked on facility field in OT setup page");
                    m_assert.assertTrue(
                            selectFacility(oPage_OtSetup.list_facilityNameFromDropdownForOtSetup, sFacilityName),
                            "Facility name selected as "
                                    + sFacilityName);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OtSetup.button_addOt, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver,
                                    oPage_OtSetup.button_addOt),
                            "clicked on +Add Ot");
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OtSetup.select_facilityForAddOtRoom, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver, oPage_OtSetup.select_facilityForAddOtRoom),
                            "clicked on facility field in Add Ot room pop up");
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OtSetup.input_capacity1, 8);
                    m_assert.assertTrue(
                            selectFacility(oPage_OtSetup.list_facilityNameFromDropdownForOtSetup, sFacilityName),
                            "Facility name selected as "
                                    + sFacilityName);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OtSetup.input_capacity1, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_OtSetup.input_oTName1,
                                    FacilitySettings_Data.sOT_NAME1),
                            "OT name entered as "
                                    + FacilitySettings_Data.sOT_NAME1);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OtSetup.input_capacity1, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver, oPage_OtSetup.select_specialityForOtSetup),
                            "clicked on speciality field in OT setup page");
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OtSetup.input_capacity1, 8);
                    m_assert.assertTrue(
                            selectSpeciality(oPage_OtSetup.list_facilityNameFromDropdownForOtSetup, sSpecialityName),
                            "Speciality name selected as "
                                    + sSpecialityName);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OtSetup.input_capacity1, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_OtSetup.input_capacity1,
                                    FacilitySettings_Data.sOT_CAPACITY1),
                            "OT capacity is "
                                    + FacilitySettings_Data.sOT_CAPACITY1);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OtSetup.button_addRoom, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver, oPage_OtSetup.button_addRoom),
                            "clicked on add room");
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OtSetup.input_oTName2, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_OtSetup.input_oTName2,
                                    FacilitySettings_Data.sOT_NAME2),
                            "OT name entered as "
                                    + FacilitySettings_Data.sOT_NAME2);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OtSetup.input_capacity2, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_OtSetup.input_capacity2,
                                    FacilitySettings_Data.sOT_CAPACITY2),
                            "OT capacity is "
                                    + FacilitySettings_Data.sOT_CAPACITY2);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OtSetup.button_deleteOtRoom, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver, oPage_OtSetup.button_deleteOtRoom),
                            "clicked on delete ot room");
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OtSetup.button_saveOtRoom, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver, oPage_OtSetup.button_saveOtRoom),
                            "clicked on save ot room");

                    Cls_Generic_Methods.customWait(5);
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertTrue(false, "Add New OT operation failed." + e);
                }

                try {

                    for (WebElement eValueOfOtNameOnTable : oPage_OtSetup.list_OtName) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eValueOfOtNameOnTable);
                        System.out.println(FacilitySettings_Data.sOT_NAME1);
                        System.out.println(sTableValue);
                        if (sTableValue.equals((FacilitySettings_Data.sOT_NAME1))) {
                            OtRoomNamefound = true;
                            break;
                        }
                        if (OtRoomNamefound) {
                            break;
                        }

                    }
                    m_assert.assertTrue(OtRoomNamefound, "Validate Ot Room name = <b>" + FacilitySettings_Data.sOT_NAME1 + "</b> added");
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

    @Test(enabled = true, description = "Validate Edit OT Room Functionality")
    public void validateEditOtRoomFunctionality() {
        Page_OtSetup oPage_OtSetup = new Page_OtSetup(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(1);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "OT Setup");
                try {
                    boolean bEditActionPerformed = false;
                    int requiredIndexOfRow = -1;
                    for (WebElement eValueOfOtNameOnTable : oPage_OtSetup.list_OtName) {
                        oPage_OtSetup = new Page_OtSetup(driver);
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eValueOfOtNameOnTable);
                        if (sTableValue.equals((FacilitySettings_Data.sOT_NAME1))) {
                            requiredIndexOfRow = oPage_OtSetup.list_OtName.indexOf(eValueOfOtNameOnTable);
                            break;
                        }
                    }
                    Cls_Generic_Methods.scrollToElementByJS(oPage_OtSetup.list_editButtonInOtSetUpPage.get(requiredIndexOfRow));
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_OtSetup.list_editButtonInOtSetUpPage.get(requiredIndexOfRow));
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OtSetup.input_oTNameUpdate, 8);
                    Cls_Generic_Methods.clearValuesInElement(oPage_OtSetup.input_oTNameUpdate);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OtSetup.input_oTNameUpdate, 8);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_OtSetup.input_oTNameUpdate,
                            "UPDATED " + FacilitySettings_Data.sOT_NAME2);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OtSetup.button_saveOtRoom, 8);
                    Cls_Generic_Methods.clickElement(oPage_OtSetup.button_saveOtRoom);
                    Cls_Generic_Methods.customWait(2);
                    m_assert.assertInfo("OT room name updated ");
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OtSetup.button_addOt, 8);
                    for (WebElement eValueOfOtNameOnTable : oPage_OtSetup.list_OtName) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eValueOfOtNameOnTable);
                        System.out.println(("UPDATED " + FacilitySettings_Data.sOT_NAME2));
                        if (sTableValue.equals(("UPDATED " + FacilitySettings_Data.sOT_NAME2))) {
                            bEditActionPerformed = true;
                            break;
                        }
                        if (bEditActionPerformed) {
                            break;
                        }
                    }
                    m_assert.assertTrue(bEditActionPerformed, "Validate Edited OT room name = <b>" +
                            "UPDATED " + FacilitySettings_Data.sOT_NAME2 + "</b>");


                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertTrue(false, "Exception while editing OT room  " + e);
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

    @Test(enabled = true, description = "Validate OT Room Name In New OT Form")
    public void validateOTRoomInNewOTForm() {
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        boolean bPatientNameFound = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            Page_OtSetup oPage_OtSetup = new Page_OtSetup(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OPD");
            Thread.sleep(1000);
            try {
                String tabToSelect = "All";
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
            Cls_Generic_Methods.scrollToElementByJS(oPage_OtSetup.button_scheduleOt);
            Cls_Generic_Methods.clickElement(driver, oPage_OtSetup.button_scheduleOt);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtSetup.button_cancelOtName, 10);
            try {

                boolean bOperationTheaterFound = false;
                int OperationTheaterIndex = -1;
                Cls_Generic_Methods.clickElement(driver,
                        oPage_OtSetup.select_operationTheaterInNewOtForm);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtSetup.button_closeNewOtForm, 10);
                for (WebElement e : oPage_OtSetup.list_facilityNameFromDropdownForOtSetup) {
                    if (e.getText().trim().equals("UPDATED " + FacilitySettings_Data.sOT_NAME2)) {
                        bOperationTheaterFound = true;
                        OperationTheaterIndex = oPage_OtSetup.list_facilityNameFromDropdownForOtSetup
                                .indexOf(e);
                        break;
                    }
                }

                if (bOperationTheaterFound) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver,
                                    oPage_OtSetup.list_facilityNameFromDropdownForOtSetup
                                            .get(OperationTheaterIndex)),
                            "UPDATED " + FacilitySettings_Data.sOT_NAME2 + " is present in Operation theater list");
                } else {
                    m_assert.assertTrue(false, "UPDATED " + FacilitySettings_Data.sOT_NAME2 + " is not found");
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertTrue(false, "Operation theater Selection failed." + e);
            }
            m_assert.assertTrue(
                    Cls_Generic_Methods.clickElement(driver, oPage_OtSetup.button_closeNewOtForm),
                    "New Ot form closed");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientNotArrived);
            Cls_Generic_Methods.customWait();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Delete OT Room")
    public void removeCreatedDataForOTSetup() {
        Page_OtSetup oPage_OtSetup = new Page_OtSetup(driver);
        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(1);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "OT Setup");
                //  boolean bOtNameFound = true;
                int requiredIndexOfRow = -1;

                try {
                    for (WebElement eValueOfOtNameOnTable : oPage_OtSetup.list_OtName) {
                        oPage_OtSetup = new Page_OtSetup(driver);
                        if (eValueOfOtNameOnTable.getText().trim().equals("UPDATED " + FacilitySettings_Data.sOT_NAME2)) {
                            requiredIndexOfRow = oPage_OtSetup.list_OtName.indexOf(eValueOfOtNameOnTable);
                            break;
                        }
                    }
                    Cls_Generic_Methods.clickElement(oPage_OtSetup.list_deleteButtonInOtSetUpPage.get(requiredIndexOfRow));
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OtSetup.button_cancelOtName, 8);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver, oPage_OtSetup.button_cancelOtName),
                            "Ot Room cancelled");
                    Cls_Generic_Methods.customWait(1);
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
            m_assert.assertFatal("Exception while Deleting the OT room" + e);
        }
    }

    @Test(enabled = true, description = "Remove Patient Created For Validating OT Setup")
    public void removePatientCreatedForOtSetUp() {
        Page_OPD oPage_OPD = new Page_OPD(driver);
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        boolean bPatientNameFound = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Page_FacilityContactList oPage_FacilityContactList = new Page_FacilityContactList(driver);
            Page_OtSetup oPage_OtSetup = new Page_OtSetup(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OPD");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_modalPatientSearch, 8);
            try {
                String tabToSelect = "Not Arrived";
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect), "Validate " + tabToSelect + " tab is selected");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_modalPatientSearch, 8);
                String patientName = null;
                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                m_assert.assertTrue(bPatientNameFound, "Validate Patient  " + concatPatientFullName + " is clicked in " + tabToSelect + " ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_modalPatientSearch, 8);
                Cls_Generic_Methods.scrollToElementByAction(driver, oPage_OtSetup.button_cancelAppointment);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtSetup.button_cancelAppointment, 8);
                m_assert.assertTrue(
                        Cls_Generic_Methods.clickElement(driver, oPage_OtSetup.button_cancelAppointment),
                        "Clicked on cancel button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.button_cancelAppointmentButtonInForm, 8);
                m_assert.assertTrue(
                        Cls_Generic_Methods.clickElement(driver, oPage_FacilityContactList.button_cancelAppointmentButtonInForm),
                        "Appointment cancelled");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while validating patient in Not arrived tab" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Exception while cancelling appointment" + e);
        }


    }

    public static boolean selectFacility(List<WebElement> listOfFacilityElements, String nameOfFacilityToSelect) {
        boolean facilityIsSelected = false;
        Page_OtSetup oPage_OtSetup = new Page_OtSetup(driver);

        try {
            for (WebElement eFacilityElement : listOfFacilityElements) {
                String sFacilityName = Cls_Generic_Methods.getTextInElement(eFacilityElement);
                System.out.println(nameOfFacilityToSelect + " vs " + sFacilityName);
                if (sFacilityName.contains(nameOfFacilityToSelect)) {
                    Cls_Generic_Methods.clickElement(driver, eFacilityElement);
                    Cls_Generic_Methods.customWait();
                    break;
                }
            }


            String facilityNameOnUI = Cls_Generic_Methods.getTextInElement(oPage_OtSetup.select_facilityForOtSetup);

            if (facilityNameOnUI.contains(nameOfFacilityToSelect)) {
                facilityIsSelected = true;
                m_assert.assertTrue(true, "Validate <b>" + nameOfFacilityToSelect + "</b> facility is selected " + facilityNameOnUI);
            } else {
                m_assert.assertTrue(false, "Validate <b>" + nameOfFacilityToSelect + "</b> facility is selected ");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertTrue(false, "Error while selecting the facility - " + nameOfFacilityToSelect + ". \n" + e);
        }

        return facilityIsSelected;

    }

    public static boolean selectSpeciality(List<WebElement> listOfSpecialityElements, String nameOfSpecialityToSelect) {
        boolean specialityIsSelected = false;
        Page_OtSetup oPage_OtSetup = new Page_OtSetup(driver);

        try {
            for (WebElement eSpecialityElement : listOfSpecialityElements) {
                String sSpecialityName = Cls_Generic_Methods.getTextInElement(eSpecialityElement);
                System.out.println(nameOfSpecialityToSelect + " vs " + sSpecialityName);
                if (sSpecialityName.contains(nameOfSpecialityToSelect)) {
                    Cls_Generic_Methods.clickElement(driver, eSpecialityElement);
                    Cls_Generic_Methods.customWait();
                    break;
                }
            }


            String specialityNameOnUI = Cls_Generic_Methods.getTextInElement(oPage_OtSetup.select_specialityForOtSetup);

            if (specialityNameOnUI.contains(nameOfSpecialityToSelect)) {
                specialityIsSelected = true;
                m_assert.assertTrue(true, "Validate <b>" + nameOfSpecialityToSelect + "</b> speciality is selected " + specialityNameOnUI);
            } else {
                m_assert.assertTrue(false, "Validate <b>" + nameOfSpecialityToSelect + "</b> speciality is selected ");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertTrue(false, "Error while selecting the speciality - " + nameOfSpecialityToSelect + ". \n" + e);
        }

        return specialityIsSelected;

    }

}