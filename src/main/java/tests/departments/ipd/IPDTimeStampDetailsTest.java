package tests.departments.ipd;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.IPD_Data;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.ipd.forms.intraOperative.Page_OperativeForm;
import pages.ipd.forms.intraOperative.Page_PreAnaesthesiaTemplate;
import pages.ipd.forms.postOperative.Page_DischargeForm;
import pages.ipd.forms.preOperative.Page_AdmissionInPreOperative;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_CategoryMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

public class IPDTimeStampDetailsTest extends TestBase {
    Page_IPD oPage_IPD;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_OPD oPage_OPD;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    static Model_Patient myPatient;
    Page_CategoryMaster oPage_CategoryMaster;
    Page_ItemMaster oPage_ItemMaster;
    EHR_Data oEHR_Data;

    String[] admissionTypes = {"Planned", "Same Day"};
    String currentDate = Cls_Generic_Methods.getTodayDate("dd/MM/yyyy");
    String admissionBookedAt;
    String sScheduleDate;
    String sReportingDate;
    String sDischargeDate;
    String sArrivalTime;
    String sAdmitDate;

    @Test(enabled = true, description = "Schedule planned admission for a patient in ipd and validate applicable fields")
    public void scheduleAdmissionIpd() throws Exception {

        oPage_CategoryMaster = new Page_CategoryMaster(driver);
        oPage_ItemMaster = new Page_ItemMaster(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        myPatient = map_PatientsInExcel.get(patientKey);
        oEHR_Data = new EHR_Data();
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        String concatPatientFullName = "";
        boolean bPatientNameFound = false;
        String expectedLoggedInUser = "PR.Akash test";
        String sAddNewCase = "Add New Case";
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("IPD");

            try {
                // Open the Search/Add patient dialog box
                Cls_Generic_Methods.customWait(5);
                CommonActions.openPatientRegisterationAndAppointmentForm();
                CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                        "Patient Details");
                if (!myPatient.getsSALUTATION().isEmpty()) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                    oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()),
                            "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
                }
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                        myPatient.getsFIRST_NAME() + " entered for First Name");
                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                                myPatient.getsMOBILE_NUMBER()),
                        myPatient.getsMOBILE_NUMBER() + " entered for Mobile Number");


                //Admission Type Planned scheduling admission
                for (WebElement radioAdmissionBtn :
                        oPage_ScheduleAdmission.list_radioAdmissionType) {
                    String admissionTypeBtn = Cls_Generic_Methods.getTextInElement(radioAdmissionBtn);
                    if (admissionTypeBtn.equalsIgnoreCase(admissionTypes[0])) {
                        try {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, radioAdmissionBtn), admissionTypeBtn + " Button in Admission Type is Clickable");
                            m_assert.assertEquals(Cls_Generic_Methods.getTextInElement(oPage_ScheduleAdmission.text_plannedAdmissionNotifyMsg), EHR_Data.sPlannedAdmissionNotifyMsg, "Planned Admission Notification message validated");

                        } catch (Exception e) {
                            m_assert.assertFatal(admissionTypeBtn + " button is not clickable");
                        }

                        //Schedule admission date and time
                        sScheduleDate = CommonActions.getDateByAddSub(currentDate, "Add", 4, "dd/MM/yyyy");
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysByJS(driver, oPage_ScheduleAdmission.input_admissionDate,
                                sScheduleDate), "<b>" + sScheduleDate + "</b> is selected as Planned Admission Date");
                        m_assert.assertInfo(setTime(oPage_ScheduleAdmission.input_admissionTime, oEHR_Data.sADMISSION_TIME), "Scheduled admission Time as :" + oEHR_Data.sADMISSION_TIME);
                        Cls_Generic_Methods.customWait(5);

                        //Reporting date and time
                        Cls_Generic_Methods.scrollToElementByJS(oPage_ScheduleAdmission.input_reportingTime);
                        sReportingDate = CommonActions.getDateByAddSub(sScheduleDate, "Sub", 2, "dd/MM/yyyy");
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysByJS(driver, oPage_ScheduleAdmission.input_reportingDate,
                                sReportingDate), "<b>" + sReportingDate + "</b> is selected as Reporting Date");
                        Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.input_reportingTime);
                        m_assert.assertInfo(setTime(oPage_ScheduleAdmission.input_reportingTime, oEHR_Data.sREPORTING_TIME), "Reporting Time as :" + oEHR_Data.sREPORTING_TIME);

                        //Expected Discharge date and time
                        sDischargeDate = CommonActions.getDateByAddSub(sScheduleDate, "Add", 1, "dd/MM/yyyy");
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysByJS(driver, oPage_ScheduleAdmission.input_dischargeDate,
                                sDischargeDate), sDischargeDate + " is selected as Discharge Date");
                        m_assert.assertInfo(setTime(oPage_ScheduleAdmission.input_dischargeTime, oEHR_Data.sDISCHARGE_TIME), "Expected Discharge Time as :" + oEHR_Data.sDISCHARGE_TIME);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduleAdmission.button_selectCase),
                                "View select case button is clicked");
                        Cls_Generic_Methods.selectElementByVisibleText(oPage_ScheduleAdmission.select_allCasesDropDown, sAddNewCase);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_createAdmission),
                                "Create admission button is clicked");
                        admissionBookedAt = getCurrentDateTime();
                        m_assert.assertInfo("Admission booking time: " + admissionBookedAt);
                        break;
                    }

                }
                Cls_Generic_Methods.customWait(4);

                //find created patient in advance date
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_assignBed, 15),
                        "Assigned bed Form is displayed");
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait(10);
                CommonActions.selectDateFromDatePicker(oPage_NewPatientRegisteration.button_calendar, sScheduleDate);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 15);

                oPage_IPD = new Page_IPD(driver);
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today),
                        "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");
                bPatientNameFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, concatPatientFullName);

                if (bPatientNameFound) {
                    //Validating details
                    try {
                        String uiBookingTime = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_bookingTime);
                        String uiReportingTime = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_reportingTime);
                        String uiScheduleAdmissionTime = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_scheduleAdmissionTime);
                        String uiExpectedDischargeTime = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_expectedDischargeTime);

                        String splitReport[] = uiReportingTime.split(",");
                        String splitSchedule[] = uiScheduleAdmissionTime.split(",");
                        String splitDischarge[] = uiExpectedDischargeTime.split(",");

                        String updateScheduleDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd MMM yy", sScheduleDate);
                        sReportingDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd MMM yy", sReportingDate);
                        sDischargeDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd MMM yy", sDischargeDate);

                        String sScheduleTime = formatTime(oEHR_Data.sADMISSION_TIME, 6);
                        String sReportingTime = formatTime(oEHR_Data.sREPORTING_TIME, 6);
                        String sExpectedDischargeTime = formatTime(oEHR_Data.sDISCHARGE_TIME, 6);

                        m_assert.assertTrue(uiBookingTime.equalsIgnoreCase(admissionBookedAt), "Booking time equals");
                        m_assert.assertTrue(splitSchedule[0].equals(sScheduleTime) && splitSchedule[1].contains(updateScheduleDate), "Schedule admission date and time equals");
                        m_assert.assertTrue(splitReport[0].equals(sReportingTime) && splitReport[1].contains(sReportingDate), "Reporting date and time equals");
                        m_assert.assertTrue(splitDischarge[0].equals(sExpectedDischargeTime) && splitDischarge[1].contains(sDischargeDate), "Discharge date and time equals");

                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("Schedule details not matched" +e);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Patient not found" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Edit admission for a patient in ipd from planned to same day and validate the timestamp fields")
    public void editScheduleAdmissionIpdAndValidateFields() throws Exception {
        String expectedLoggedInUser = "PR.Akash test";
        oPage_IPD = new Page_IPD(driver);
        oEHR_Data = new EHR_Data();
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        Page_AdmissionInPreOperative oPage_AdmissionInPreOperative = new Page_AdmissionInPreOperative(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_OperativeForm oPage_OperativeForm = new Page_OperativeForm(driver);
        Page_DischargeForm oPage_DischargeForm = new Page_DischargeForm(driver);
        Page_PreAnaesthesiaTemplate oPage_PreAnaesthesiaTemplate = new Page_PreAnaesthesiaTemplate(driver);

        String concatPatientFullName = "";
        boolean bPatientNameFound = false;
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("IPD");

            try {
                //navigate to date on which planned admission was scheduled
                Cls_Generic_Methods.customWait(10);
                sScheduleDate = CommonActions.getDateByAddSub(currentDate, "Add", 4, "dd/MM/yyyy");

                CommonActions.selectDateFromDatePicker(oPage_NewPatientRegisteration.button_calendar, sScheduleDate);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 15);

                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today),
                        "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");
                bPatientNameFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, concatPatientFullName);

                if (bPatientNameFound) {
                    Cls_Generic_Methods.clickElement(oPage_IPD.button_editAdmissionIpd);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.input_reportingTime, 10);

                    //Admission Type Same day scheduling admission
                    for (WebElement radioAdmissionBtn :
                            oPage_ScheduleAdmission.list_radioAdmissionType) {
                        String admissionTypeBtn = Cls_Generic_Methods.getTextInElement(radioAdmissionBtn);
                        if (admissionTypeBtn.equalsIgnoreCase(admissionTypes[1])) {
                            try {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, radioAdmissionBtn), admissionTypeBtn + " Button in Admission Type is Clickable");
                            } catch (Exception e) {
                                m_assert.assertFatal(admissionTypeBtn + " button is not clickable");
                            }
                            break;
                        }
                    }

                    sAdmitDate = Cls_Generic_Methods.getTodayDate();
                    sReportingDate = Cls_Generic_Methods.getTodayDate();
                    Cls_Generic_Methods.customWait(5);
                    Cls_Generic_Methods.scrollToElementByJS(oPage_ScheduleAdmission.input_reportingDate);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysByJS(driver, oPage_ScheduleAdmission.input_reportingDate,
                            sReportingDate), "<b>" + sReportingDate + "</b> is selected as Reporting Date");
                    Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.input_reportingTime);
                    m_assert.assertInfo(setTime(oPage_ScheduleAdmission.input_reportingTime, oEHR_Data.sREPORTING_TIME), "Reporting Time for same day as :" + oEHR_Data.sREPORTING_TIME);
                    Cls_Generic_Methods.customWait(5);
                    Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_saveAdmissionEdit);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_readyForAdmission, 15);
                    Cls_Generic_Methods.clickElement(oPage_IPD.button_readyForAdmission);
                    sArrivalTime = getCurrentDateTime();
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_IPD.button_admitPatient, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_admitPatient),
                            "Admit Patient Button clicked ");

                    //input admission date and time
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_IPD.header_admissionForm, 10),
                            "Admission form is displayed");
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysByJS(driver, oPage_ScheduleAdmission.input_admitAdmissionDate,
                            sAdmitDate), "<b>" + sAdmitDate + "</b> is selected as Admission Date");
                    m_assert.assertInfo(setTime(oPage_ScheduleAdmission.input_admitAdmissionTime, oEHR_Data.sAdmitAdmission_Time), "Admit admission Time as :" + oEHR_Data.sAdmitAdmission_Time);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_saveAdmissionForm),
                            "Admission Form Saved. ");
                    Cls_Generic_Methods.customWait(8);
                    String sFormAdmitDate_format = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd MMM yyyy", sAdmitDate);

                    //validate in forms
                    Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.button_admissionInPreOperative);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AdmissionInPreOperative.tab_AdminTabOnAdmissionUnderPreOperative, 16);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.tab_AdminTabOnAdmissionUnderPreOperative),
                            "Validate Admin Tab is clicked");
                    String t = Cls_Generic_Methods.getTextInElement(oPage_AdmissionInPreOperative.text_admDateOnForm);
                    String f[] = t.split(":"); //31 aug 2023
                    m_assert.assertTrue(f[1].trim().equalsIgnoreCase(sFormAdmitDate_format), "on admission form matched");
                    Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.button_saveOnModalHeader);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);
                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_preOperativeSection, 16);

                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElementByJS(driver, oPage_OperativeForm.button_operativeNote),
                            " clicked on operative template button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OperativeForm.button_newOperativeNote,
                            20);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver,
                                    oPage_OperativeForm.button_newOperativeNote),
                            " clicked on new note to view operative template ");
                    Cls_Generic_Methods
                            .waitForElementToBeDisplayed(oPage_OperativeForm.input_personalCommentBox, 20);
                    m_assert.assertTrue(f[1].trim().equalsIgnoreCase(sFormAdmitDate_format), "on operative form matched");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver,
                            oPage_OperativeForm.button_saveOperativeForm), "Operative form saved ");
                    Cls_Generic_Methods.customWait(3);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(
                            oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate, 20);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver,
                                    oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate),
                            "operative form closed ");

                    //fill discharge form
                    sDischargeDate = Cls_Generic_Methods.getTodayDate();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DischargeForm.button_dischargeTemplate,
                            20);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver, oPage_DischargeForm.button_dischargeTemplate),
                            "clicked on Discharge template ");
                    Cls_Generic_Methods.customWait(4);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_next), " clicked on next button");
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysByJS(driver, oPage_DischargeForm.input_dischargeDate,
                            sDischargeDate), "<b>" + sDischargeDate + "</b> is selected as Discharge Date");
                    m_assert.assertInfo(setTime(oPage_DischargeForm.input_dischargeTime, oEHR_Data.sDISCHARGE_TIME), "Discharge Time as :" + oEHR_Data.sDISCHARGE_TIME);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_saveOperativeForm), " Discharge template successfully saved");
                    Cls_Generic_Methods.customWait(4);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate), "Discharge form closed ");
                    Cls_Generic_Methods.customWait(10);

                    //validate details
                    try {
                        String uiArrivalTime = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_arrivalTime);
                        String uiAdmissionTime = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_admissionTime);
                        String uiDischargeTime = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_dischargeTime);

                        String splitAdmissionTime[] = uiAdmissionTime.split(",");
                        String splitDischargeTime[] = uiDischargeTime.split(",");

                        sAdmitDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd MMM yy", sAdmitDate);
                        sDischargeDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd MMM yy", sDischargeDate);

                        String sAdmitTime = formatTime(oEHR_Data.sAdmitAdmission_Time, 6);
                        String sDischargeTime = formatTime(oEHR_Data.sDISCHARGE_TIME, 6);

                        m_assert.assertTrue(uiArrivalTime.equalsIgnoreCase(sArrivalTime), "Arrival time equals");
                        m_assert.assertTrue(splitAdmissionTime[0].equals(sAdmitTime) && splitAdmissionTime[1].contains(sAdmitDate), "Admission date and time equals");
                        m_assert.assertTrue(splitDischargeTime[0].equals(sDischargeTime) && splitDischargeTime[1].contains(sDischargeDate), "Discharge date and time equals");

                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("Schedule details not matched" + e);
                    }
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


    public String getCurrentDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("hh:mm a, dd MMM yy");
        Date date = new Date();
        //31 Aug 23
        String date1 = dateFormat.format(date);
        return date1;
    }

    public static boolean setTime(WebElement timeElement, String time) {

        boolean flag = false;
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        try {
            Cls_Generic_Methods.clickElement(driver, timeElement);
            String[] seperatedTimeValue = time.split(":");
            Cls_Generic_Methods.clearValuesInElement(oPage_NewPatientRegisteration.input_appointmentHourForAppointDetails);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_appointmentHourForAppointDetails, seperatedTimeValue[0]);
            Cls_Generic_Methods.clearValuesInElement(oPage_NewPatientRegisteration.input_appointmentMinuteForAppointDetails);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_appointmentMinuteForAppointDetails, seperatedTimeValue[1]);

            Cls_Generic_Methods.clearValuesInElement(oPage_NewPatientRegisteration.input_appointmentMeridianForAppointDetails);

            Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_appointmentMeridianForAppointDetails, seperatedTimeValue[2]);
            flag = true;

        } catch (Exception e) {
            m_assert.assertFatal("Unable to set Time");
            e.printStackTrace();
        }
        return flag;

    }

    public static String formatTime(String str, int n) {
        return str.substring(0, n - 1) + " " + str.substring(n);
    }
}

