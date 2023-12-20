package tests.departments.ipd;

import data.EHR_Data;
import data.IPD_Data;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;

import pages.commonElements.CommonActions;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_CategoryMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class IPDWFTest extends TestBase {
    Page_IPD oPage_IPD;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_OPD oPage_OPD;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    static Model_Patient myPatient;
    Page_Navbar oPage_Navbar;
    Page_CategoryMaster oPage_CategoryMaster;
    Page_ItemMaster oPage_ItemMaster;
    EHR_Data oEHR_Data;

    String[] admissionTypes = {"Planned", "Same Day"};
    static String itemDescription = "test iol type cat 1";
    String currentDate = Cls_Generic_Methods.getTodayDate("dd/MM/yyyy");
    String admissionDateValue = "";
    String admissionTimeValue;
    String anesthesiaType = "Topical";
    String reasonForAdmission = "NA";
    String managementPlans = "NA";
    String procedure = "Phaco with foldable IOL implant";

    // Note : Prerequisite : Need to create one Iol Category and a item using same IOl category . Add IOL type in itemDescription
    @Test(enabled = true, description = "Validate schedule admission for patient")
    public void scheduleAdmissionFromOPD() throws Exception {

        oPage_CategoryMaster = new Page_CategoryMaster(driver);
        oPage_ItemMaster = new Page_ItemMaster(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        oEHR_Data = new EHR_Data();

        String concatPatientFullName = "";
        boolean bPatientNameFound = false;
        String expectedLoggedInUser = "PR.Akash test";

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                String MyQueueTab = "My Queue";
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage.get(0), 10);
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                        "Validate " + MyQueueTab + " tab is selected");
                Thread.sleep(1000);
                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient" + e);
            }
            if (bPatientNameFound) {
                int admissionCount = 0;

                for (String admissionTypeTxt : admissionTypes) {
                    CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

                    m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByAction(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission),
                            "Scheduled admission button is displayed");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission),
                            "Clicked on scheduled admission button");

                    //Fill Schedule Admission Form
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_ScheduleAdmissionForm, 20),
                            "Scheduled admission form is displayed");

                    m_assert.assertEquals(Cls_Generic_Methods.getTextInElement(oPage_ScheduleAdmission.tab_activeScheduleAdmissionForm), "Admission Details", "Admission Details Tab is selected on start by default.");

                    switch (admissionTypeTxt.toUpperCase()) {
                        case "PLANNED" -> admissionCount = 0;
                        case "EMERGENCY" -> admissionCount = 1;
                        case "SAME DAY" -> admissionCount = 2;
                    }
                    WebElement radioAdmissionType = oPage_ScheduleAdmission.list_radioAdmissionType.get(admissionCount);
                    String admissionType = Cls_Generic_Methods.getTextInElement(radioAdmissionType);

                    if (admissionType.equals("Planned")) {
                        for (int j = oPage_ScheduleAdmission.list_radioBtnCashlessHospitalisation.size() - 1; j >= 0; j--) {

                            WebElement radioBtnCashlessHospitalisation = oPage_ScheduleAdmission.list_radioBtnCashlessHospitalisation.get(j);
                            String btnValue = Cls_Generic_Methods.getElementAttribute(radioBtnCashlessHospitalisation, "value");

                            try {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, radioBtnCashlessHospitalisation), btnValue + " Button in Cashless Hospitalisation is Clickable");
                            } catch (Exception e) {
                                m_assert.assertFatal(btnValue + " button is not clickable");
                            }
                        }

                        //Admission Type Button Clickable validation
                        for (WebElement radioAdmissionBtn :
                                oPage_ScheduleAdmission.list_radioAdmissionType) {
                            String admissionTypeBtn = Cls_Generic_Methods.getTextInElement(radioAdmissionBtn);

                            try {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, radioAdmissionBtn), admissionTypeBtn + " Button in Admission Type is Clickable");
                            } catch (Exception e) {
                                m_assert.assertFatal(admissionTypeBtn + " button is not clickable");
                            }
                            if (admissionTypeBtn.equals("Planned")) {
                                m_assert.assertEquals(Cls_Generic_Methods.getTextInElement(oPage_ScheduleAdmission.text_plannedAdmissionNotifyMsg), EHR_Data.sPlannedAdmissionNotifyMsg, "Planned Admission Notification message validated");
                            }
                        }

                    }

                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, radioAdmissionType),
                            "Select " + admissionType + " radio button");

                    //Location
                    Cls_Generic_Methods.customWait(2);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.select_locationAdmissionForm, 20);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.select_locationAdmissionForm),
                            "Clicked Location in Admission Form");
                    String location = oEHR_Data.sLOCATION_ADMISSION;
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ScheduleAdmission.input_textBox,
                            location + Keys.ENTER), location + " is selected in Location");


                    //Doctor
                    Cls_Generic_Methods.customWait(2);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.select_doctorAdmissionForm),
                            "Clicked Doctor in Admission Form");
                    String doctorName = oEHR_Data.sDOCTOR_NAME_ADMISSION;
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ScheduleAdmission.input_textBox, doctorName + Keys.ENTER),
                            doctorName + " is selected in Doctor Options");


                    //Additional Doctors
                    Cls_Generic_Methods.customWait(2);

                    if (oEHR_Data.sADDITIONAL_DOCTORS_ADMISSION.length > 0) {
                        int additionalDoctorCount = 0;
                        for (String additionalDoctors : oEHR_Data.sADDITIONAL_DOCTORS_ADMISSION) {
                            WebElement additionDoctorEle = oPage_ScheduleAdmission.list_selectAdditionalDoctorsAdmissionForm.get(additionalDoctorCount);
                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(additionDoctorEle),
                                    "Clicked Additional Doctors field");
                            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ScheduleAdmission.input_textBox, additionalDoctors + Keys.ENTER),
                                    additionalDoctors + " is selected in Additional Doctors Options");
                            additionalDoctorCount++;
                        }
                    }

                    //Admission Date and Time
                    admissionDateValue = String.valueOf(getDateFormatted());
                    if (admissionType.equals("Planned")) {
                        admissionTimeValue = oEHR_Data.sADMISSION_TIME;
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysByJS(driver, oPage_ScheduleAdmission.input_admissionDate,
                                admissionDateValue), "<b>" + admissionDateValue + "</b> is selected as Admission Date");
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysByJS(driver, oPage_ScheduleAdmission.input_admissionTime,
                                admissionTimeValue), admissionTimeValue + " is selected as Admission Time");
                    }

                    //Anesthesia Type
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_ScheduleAdmission.select_anesthesia,
                            anesthesiaType), "Selected<b> " + anesthesiaType + " </b>in Anesthesia Type");

                    //Reason For Admission and Management Plans
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ScheduleAdmission.input_reasonForAdmission, reasonForAdmission),
                            "Entered " + reasonForAdmission + " in Reason for Admission Field");
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ScheduleAdmission.input_managementPlansAdmissionForm, managementPlans),
                            "Entered " + managementPlans + " in Management Plans Field");

                    //Case Detail Tab
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduleAdmission.button_viewCaseDetails),
                            "View case details button is clicked");

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ScheduleAdmission.input_procedureCaseDetails, procedure), "Entered " + procedure + " in Procedures field");
                    Cls_Generic_Methods.customWait(5);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_ScheduleAdmission.input_procedureCaseDetails, "" + Keys.DOWN + Keys.ENTER);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.button_saveProcedure, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_saveProcedure),
                            "Clicked Save button in procedure");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.input_checkBoxPresentProcedure, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduleAdmission.input_checkBoxPresentProcedure), "Clicked Procedure case sheet CheckBox");
                    int count = 0;

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.select_listProcedureCaseSheetItems.get(0), 20);
                    for (WebElement caseSheetItem : oPage_ScheduleAdmission.select_listProcedureCaseSheetItems) {
                        count++;
                        m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(caseSheetItem, itemDescription), "Selected <b>" + itemDescription + "</b> in Procedure CaseSheet Items" + count);
                    }

                    //Create Admission
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_createAdmission),
                            "Create admission button is clicked");

                    Cls_Generic_Methods.customWait(4);

                    //Assign Bed
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_assignBed, 15),
                            "Assigned bed Form is displayed");

                    try {
                        if (Cls_Generic_Methods.isElementDisplayed(oPage_ScheduleAdmission.header_assignBed)) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.input_selectWard),
                                    "Ward dropdown Clicked");
                            m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_ScheduleAdmission.input_selectWard, 1),
                                    "Ward Value Selected");
                            Cls_Generic_Methods.customWait(1);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.input_selectRoom),
                                    "SelectRoom dropdown Clicked");
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

                    Cls_Generic_Methods.customWait(3);
                    CommonActions.selectDepartmentOnApp("OPD");
                }
                Cls_Generic_Methods.customWait(5);
                CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_jumpToAdmission,
                        10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_jumpToAdmission),
                        "Clicked Jump to Admission Button");
                Cls_Generic_Methods.customWait(2);

                for (WebElement admissionDate :
                        oPage_PatientAppointmentDetails.list_jumpToAdmission) {
                    String admissionText = admissionDate.getText();

                    if (admissionText.contains(admissionDateValue)) {
                        m_assert.assertTrue("Validated planned admission on " + admissionDateValue);
                    } else if (admissionText.contains(currentDate)) {
                        m_assert.assertTrue("Validated emergency admission on " + currentDate);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(admissionDate), "Clicked on Jump to admission");
                    }
                }

            } else {
                m_assert.assertTrue(false, "searched patient is not present in patient list");
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        }

    }

    @Test(enabled = true, description = "Validate Patient's admission is scheduled and send to My Queue Tab")
    public void validateReadyForAdmissionInIpd() {

        oPage_IPD = new Page_IPD(driver);
        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String patientName = null;
        boolean bPatientNameFound = false;
        String expectedLoggedInUser = "PR.Akash test";
        String ScheduleTodayTab = "Scheduled Today";
        String myQueueTab = "My Queue";
        int initialMyQueueCount = -1;
        int updatedMyQueueCount = -1;

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("IPD");

            try {
                Cls_Generic_Methods.customWait(10);
                initialMyQueueCount = CommonActions.getCountOfPatientsInTab(oPage_IPD.tabs_TabsOnIPD, myQueueTab);
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today),
                        "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");
                Cls_Generic_Methods.customWait(1);

                bPatientNameFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnScheduledTodayTab, concatPatientFullName);

                if (bPatientNameFound) {
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_IPD.button_readyForAdmission, 10),
                            "Ready for admission Button visible ");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_readyForAdmission),
                            "Ready for admission Button clicked ");

                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_IPD.button_admitPatient, 10),
                            "Admit Patient Button visible");

                    m_assert.assertTrue(
                            CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, myQueueTab),
                            "Validate " + myQueueTab + " tab is selected");
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

                    updatedMyQueueCount = CommonActions.getCountOfPatientsInTab(oPage_IPD.tabs_TabsOnIPD, myQueueTab);

                    m_assert.assertTrue((updatedMyQueueCount > initialMyQueueCount),
                            "Validate Patient Count has been increased from <b>" + initialMyQueueCount + "</b> to <b>"
                                    + updatedMyQueueCount + "</b> in " + myQueueTab);
                } else {
                    m_assert.assertTrue(false, "Admission is not Scheduled for patient: " + patientName);
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while scheduling admission of patient to IPD" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validate Patient's admitted in IPD and send to Admitted tab")
    public void validatePatientAdmitToIpd() {
        oPage_IPD = new Page_IPD(driver);
        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String admittedPatientTab = "Admitted Patients";
        String patientName = null;
        boolean bPatientNameFound = false;
        String expectedLoggedInUser = "PR.Akash test";
        String myQueueTab = "My Queue";
        int initialAdmittedPatientCount = -1;
        int updatedAdmittedPatientCount = -1;

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("IPD");

            try {
                Cls_Generic_Methods.customWait(10);
                initialAdmittedPatientCount = CommonActions.getCountOfPatientsInTab(oPage_IPD.tabs_TabsOnIPD, admittedPatientTab);

                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, myQueueTab),
                        "Validate " + myQueueTab + " tab is selected");
                Thread.sleep(1000);

                bPatientNameFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnMyQueueTab, concatPatientFullName);
                if (bPatientNameFound) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_admitPatient),
                            "Admit Patient Button clicked ");

                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_IPD.header_admissionForm, 10),
                            "Admission form is displayed");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_saveAdmissionForm),
                            "Admission Form Saved. ");
                    Cls_Generic_Methods.customWait(8);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

                    updatedAdmittedPatientCount = CommonActions.getCountOfPatientsInTab(oPage_IPD.tabs_TabsOnIPD, admittedPatientTab);

                    m_assert.assertTrue((updatedAdmittedPatientCount > initialAdmittedPatientCount),
                            "Validate Patient Count has been increased from <b>" + initialAdmittedPatientCount + "</b> to <b>"
                                    + updatedAdmittedPatientCount + "</b> in " + admittedPatientTab);
                } else {
                    m_assert.assertTrue(false, "Patient not found to admit " + patientName);
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while admitting patient to IPD" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }
    public LocalDate getDateFormatted() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        int nrOfDays = 2;
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(nrOfDays);
        return futureDate;
    }
}

