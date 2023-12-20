package tests.settings.facilitySetting.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.*;
import data.settingsData.FacilitySettings_Data;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.ipd.Page_IPD;
import pages.opd.Page_OPD;
import pages.settings.facilitySettings.general.wardSetup.Page_WardSetup;

public class WardSetupTest extends TestBase {


    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();

    @Test(enabled = true, description = "Validate creating patient for ward setup")
    public void createPatientToValidateWardSetup() {
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_WardSetup oPage_WardSetUp = new Page_WardSetup(driver);
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);

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

                Cls_Generic_Methods.customWait(10);
              //  m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_WardSetUp.tab_appointmentDetails, 20), "Wait until appointment details is displayed ");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5), "Patient details displayed ");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validate adding new Ward and room in ward setup")
    public void validateAddNewWard() {

        Page_WardSetup oPage_WardSetUp = new Page_WardSetup(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        String NotArrivedTab = OPD_Data.tab_ALL;
        boolean bPatientNameFound = false;
        boolean bRoomSelected = false;
        boolean bBedSelected = false;
        boolean bWardSelected = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();

            try {
                //get ward settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Ward Setup");


                //click on Add Ward
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_WardSetUp.button_addWard),"Add Ward Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WardSetUp.form_addWard, 15);

                //fill data on add ward form
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_WardSetUp.input_wardName, FacilitySettings_Data.sWARD_NAME)," Name Entered as :"+FacilitySettings_Data.sWARD_NAME);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_WardSetUp.input_wardCode, FacilitySettings_Data.sWARD_CODE)," Code Entered as :"+FacilitySettings_Data.sWARD_CODE);

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_WardSetUp.input_roomName, FacilitySettings_Data.sROOM_NAME),
                        "Room Name Entered as "+FacilitySettings_Data.sROOM_NAME);
                Cls_Generic_Methods.clickElement(oPage_WardSetUp.select_roomType);
                Cls_Generic_Methods.customWait(1);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WardSetUp.option_generalWard),"Clicked On general Ward");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_WardSetUp.input_roomCode, FacilitySettings_Data.sROOM_CODE),
                        " Room Code Entered as :"+FacilitySettings_Data.sROOM_CODE);

                Cls_Generic_Methods.sendKeysIntoElement(oPage_WardSetUp.input_bedName, FacilitySettings_Data.sBED_NAME);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_WardSetUp.input_bedCode, FacilitySettings_Data.sBED_CODE);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_WardSetUp.input_totalBed, FacilitySettings_Data.sTOTAL_BEDS);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_WardSetUp.input_bedPrice, FacilitySettings_Data.sBED_PRICE);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WardSetUp.button_saveWard),"Save Ward Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WardSetUp.button_addWard, 15);
                Cls_Generic_Methods.customWait(5);


                //Validate created ward should display on the ward list page.
                for (WebElement wardName : oPage_WardSetUp.list_wardNames) {
                    if (wardName.isDisplayed()) {
                        if (wardName.getText().contains(FacilitySettings_Data.sWARD_NAME)) {
                            m_assert.assertTrue(true, "New ward successfully created.<b> " + wardName.getText() + " </b>");
                            Cls_Generic_Methods.customWait(2);
                            break;
                        }
                    }
                }

                //go in opd
                CommonActions.selectDepartmentOnApp("OPD");
                Cls_Generic_Methods.customWait(2);
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, NotArrivedTab), "Validate " + NotArrivedTab + " tab is selected");

                //select patient
                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

                if (bPatientNameFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 15);
                    Cls_Generic_Methods.customWait(1);

                  //  m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientArrived), "Mark Patient arrived clicked ");
                   // Cls_Generic_Methods.customWait(3);

                    m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByJS(oPage_WardSetUp.button_scheduledAdmission), "Scroll to Scheduled Admission button");
                    //click on scheduled admission
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_WardSetUp.button_scheduledAdmission),
                            " Schedule Admission Button Clicked");
                    // Wait until admission form is displayed
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_WardSetUp.form_scheduledAdmission, 10), "Wait until scheduled admission form is displayed ");
                    //fill essential details and create admission
                    Cls_Generic_Methods.clickElement(oPage_WardSetUp.radioButton_sameDay);
                    Cls_Generic_Methods.customWait(3);
                    Cls_Generic_Methods.clickElement(oPage_WardSetUp.tab_caseDetails);
                    Cls_Generic_Methods.customWait(3);
                    Cls_Generic_Methods.clickElement(oPage_WardSetUp.button_createAdmission);
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WardSetUp.window_assignBed, 10), "Wait until assign bed window is displayed ");

                    //select created ward from the list

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_WardSetUp.dropdown_ward), "Ward dropdown Clicked");

                    for (WebElement listWardName : oPage_WardSetUp.list_dropdownWardNames) {
                        if (listWardName.isDisplayed()) {
                            if (listWardName.getText().equalsIgnoreCase(FacilitySettings_Data.sWARD_NAME)) {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, listWardName), "Ward selected from dropdown <b> " + listWardName.getText() + " </b> ");
                                bWardSelected = true;
                                break;
                            }
                        }
                    }
                    m_assert.assertTrue(bWardSelected, "Created Ward found");

                    //select created room from the list
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_WardSetUp.dropdown_room), "SelectRoom dropdown Clicked");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WardSetUp.button_saveBed, 3);
                    for (WebElement listRoomName : oPage_WardSetUp.list_dropdownRoomNames) {
                        if (listRoomName.isDisplayed()) {
                            if (listRoomName.getText().equalsIgnoreCase(FacilitySettings_Data.sROOM_NAME)) {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, listRoomName), "Room selected from dropdown <b> " + listRoomName.getText() + " </b> ");
                                bRoomSelected = true;
                                Cls_Generic_Methods.customWait(3);
                                break;
                            }
                        }
                    }
                    m_assert.assertTrue(bRoomSelected, "Created room found");

                    //Select bed from the radio buttons
                    for (WebElement listBedName : oPage_WardSetUp.list_radioBeds) {
                        if (oPage_WardSetUp.list_radioBeds.size() == Integer.parseInt(FacilitySettings_Data.sTOTAL_BEDS)) {
                            m_assert.assertTrue(true, "Total number of beds displayed : <b> " + oPage_WardSetUp.list_radioBeds.size() + " </b> ");

                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WardSetUp.list_radioBeds.get(0), 3);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_WardSetUp.text_bedName), "Bed selected <b> " + oPage_WardSetUp.text_bedName.getText() + " </b> ");
                            bBedSelected = true;
                            break;
                        }
                    }
                    m_assert.assertTrue(bBedSelected, "Bed is Selected");

                    //click on save bed
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_WardSetUp.button_saveBed),
                            "Save bed Button clicked");
                    Cls_Generic_Methods.customWait(5);

                    //wait until appointment details is visible
                   // m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_WardSetUp.tab_appointmentDetails,
                          //  10), "Wait until appointment details is displayed ");
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

    @Test(enabled = true, description = "Validate assigned ward and bed in IPD")
    public void validateAssignedBedInIPD() throws Exception {
        Page_WardSetup oPage_WardSetUp = new Page_WardSetup(driver);
        Page_IPD oPage_IPD = new Page_IPD(driver);
        String tabToSelect = IPD_Data.tab_Scheduled_Today;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            boolean bValidatePatientFound = false;
            myPatient = map_PatientsInExcel.get(patientKey);

            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();

            //select IPD from Departments list
            CommonActions.selectDepartmentOnApp("IPD");

            m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, tabToSelect), "Validate " + tabToSelect + " tab is selected");
            Cls_Generic_Methods.customWait(1);

            bValidatePatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, concatPatientFullName);
            m_assert.assertTrue(bValidatePatientFound, "Validate patient " + concatPatientFullName + " is found");

            if (bValidatePatientFound) {
                //scroll to Assigned bed section
                m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByJS(oPage_WardSetUp.text_assignedBed), "Scroll to assigned bed field");

                if (!(oPage_WardSetUp.text_assignedBed.getText().isEmpty())) {
                    String roomDetails = oPage_WardSetUp.text_ofAssignedBed.getText();
                    String str[] = roomDetails.split("/");
                    String wardName = str[0];
                    String roomName = str[1];
                    if ((wardName.contains(FacilitySettings_Data.sWARD_NAME)) && (roomName.contains(FacilitySettings_Data.sROOM_NAME))) {
                        m_assert.assertTrue(true, "Ward details are displayed <b> " + wardName + " , " + roomName + " </b> ");
                    }
                } else {
                    m_assert.assertTrue(false, "Ward details not showing correctly ");
                }
            } else {
                m_assert.assertTrue(false, "Ward and Bed is not assigned for patient ");
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error while getting application" + e);
            e.printStackTrace();
        }

    }

    @Test(enabled = true, description = "Validate removing assigned bed and deleting patient in IPD")
    public void validateRemoveBedAndDeleteAdmissionFromIPD() throws Exception {

        Page_WardSetup oPage_WardSetUp = new Page_WardSetup(driver);
        Page_IPD oPage_IPD = new Page_IPD(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            boolean bValidatePatientFound = false;
            myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            CommonActions.loginFunctionality(expectedLoggedInUser);

            //select IPD from Departments list
            CommonActions.selectDepartmentOnApp("IPD");

            m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today),
                    "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.tabs_TabsOnIPD.get(0), 8);

            bValidatePatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, concatPatientFullName);
            m_assert.assertTrue(bValidatePatientFound, "Validate patient " + concatPatientFullName + " is found");

            if (bValidatePatientFound) {
                //click on remove bed
                Cls_Generic_Methods.scrollToElementByJS(oPage_WardSetUp.text_assignedBed);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WardSetUp.icon_removeBed), "<b> Remove bed icon </b> clicked ");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_WardSetUp.button_removeBed, 10), "Remove bed button is displayed ");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WardSetUp.button_removeBed), "<b> Remove bed button <b> is clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);

                m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByJS(oPage_WardSetUp.text_assignedBed), "Scroll to assigned bed field");

                //Validate after removed bed daycare text should be displayed for assigned bed field
                try {
                    if (oPage_WardSetUp.text_dayCare.isDisplayed()) {
                        m_assert.assertTrue("After removing bed text displayed <b> " + oPage_WardSetUp.text_dayCare.getText() + " </b> ");
                        Cls_Generic_Methods.customWait(4);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Daycare text is not displayed after removing bed " + e);
                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WardSetUp.link_deleteAdmission), "Delete admission link clicked ");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_WardSetUp.button_deleteAdmission, 10), "Remove bed button is displayed ");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WardSetUp.button_deleteAdmission), "<b> Delete Admission button <b> is clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);


            }
        } catch (Exception e) {
            m_assert.assertFatal("Exception while getting patient" + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate deleting admission of created patient from OPD")
    public void validateDeleteAdmissionFromOPD() throws Exception {
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        String notArrivedTab = OPD_Data.tab_NOT_ARRIVED;
        String myQueueTab = OPD_Data.tab_MY_QUEUE;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            boolean bValidatePatientFound = false;
            myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            CommonActions.loginFunctionality(expectedLoggedInUser);

            //select IPD from Departments list
            CommonActions.selectDepartmentOnApp("OPD");

            m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, myQueueTab), "Validate " + myQueueTab + " tab is selected");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage.get(0), 8);

            bValidatePatientFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
            m_assert.assertTrue(bValidatePatientFound, "Validate patient " + myQueueTab + concatPatientFullName + " is found");

            if (bValidatePatientFound) {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientNotArrived), "<b> Button Not Arrived </b> Clicked for patient <b> " + concatPatientFullName + " </b> ");
                Cls_Generic_Methods.waitForPageLoad(driver, 5);
            }

            m_assert.assertInfo(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, notArrivedTab), "Validate " + notArrivedTab + " tab is selected");
            bValidatePatientFound = false;
            bValidatePatientFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
            m_assert.assertTrue(bValidatePatientFound, "Validate patient for " + notArrivedTab + concatPatientFullName + " is found");

            if (bValidatePatientFound) {
                Cls_Generic_Methods.customWait(1);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_cancelAppointment), "<b> Cancel </b> " + " Button clicked on page ");
                m_assert.assertInfo(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.header_cancelAppointment, 5), "<b> Cancel appointment form displayed </b> ");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_cancelAppointmentForm), "<b> Appointment cancel </b> button clicked ");
                Cls_Generic_Methods.waitForPageLoad(driver, 5);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 4);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validate deleting previously created ward in ward setup")
    public void validateRemoveAddedWard() {

        Page_WardSetup oPage_WardSetUp = new Page_WardSetup(driver);

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                //get ward settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Ward Setup");

                //Find the created ward on the ward list page.
                for (WebElement wardName : oPage_WardSetUp.list_wardNames) {
                    if (wardName.isDisplayed()) {
                        if (wardName.getText().contains(FacilitySettings_Data.sWARD_NAME)) {
                            Cls_Generic_Methods.scrollToElementByJS(wardName);
                            m_assert.assertTrue(true, "New ward found <b> " + wardName.getText() + " </b>");

                            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_WardSetUp.button_deleteWard), "<b> Ward delete </b> button clicked ");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WardSetUp.header_deleteWard, 5);
                            if (oPage_WardSetUp.header_deleteWard.isDisplayed()) {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_WardSetUp.button_confirmDeleteWard), "<b> Ward delete </b> confirmation clicked ");
                                Cls_Generic_Methods.customWait(4);
                            }

                            break;
                        }
                    }
                }

            } catch (Exception e) {
                m_assert.assertFatal("Ward not found " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

}
