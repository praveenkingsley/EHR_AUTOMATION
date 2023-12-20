package tests.authorizationpolicy.ipd;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.opd.Page_OPD;
import pages.ot.Page_OT;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;

public class PatientFlowPolicyTest extends IPD_Policy {

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_IPD oPage_IPD;
    Page_OT oPage_OT;
    String sSendAdmissionPolicyComponent = "SEND (PATIENT FLOW)";
    String sReadyForAdmissionPolicyComponent = "READY FOR ADMISSION (PATIENT FLOW)";
    String sAdmitPatientPolicyComponent = "ADMIT PATIENT (PATIENT FLOW)";
    String sDischargePatientPolicyComponent = "DISCHARGE PATIENT (PATIENT FLOW)";
    String sReAdmitPolicyComponent = "RE-ADMIT (PATIENT FLOW)";
    String sReadyForOtPolicyComponent = "READY FOR OT (PATIENT FLOW)";
    String sEngageOtPolicyComponent = "ENGAGE OT (PATIENT FLOW)";
    String sMarkAsDonePolicyComponent = "MARK AS DONE (PATIENT FLOW)";
    String sSendToWardPolicyComponent = "SEND TO WARD (PATIENT FLOW)";
    String sUndoPolicyComponent = "UNDO (PATIENT FLOW)";
    String sEditAdmissionDataTimePolicyComponent = "EDIT ADMISSION DATE & TIME (PATIENT FLOW)";
    String sNaPolicyComponent = "NA (PATIENT FLOW)";
    String sBillingClearancePolicyComponent = "BILLING CLEARANCE (PATIENT FLOW)";
    String sUndoBillingClearancePolicyComponent = "UNDO BILLING CLEARANCE (PATIENT FLOW)";


    @Test(description = "Validate Send patient to other users access")
    public void validatePolicy_sendPatientFlow() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sSendAdmissionPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            scheduleAdmission();

            boolean clickReadyForAdmission = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_readyForAdmission, 6);

            if (clickReadyForAdmission) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_readyForAdmission);
            }

            boolean sendToBtnDisplayed = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PatientAppointmentDetails.list_btnSendToUsers, 10);

            m_assert.assertFalse(sendToBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sSendAdmissionPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd();

            sendToBtnDisplayed = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PatientAppointmentDetails.list_btnSendToUsers, 10);
            m_assert.assertTrue(sendToBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Mark Ready for Admission access")
    public void validatePolicy_readyForAdmission() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sReadyForAdmissionPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            scheduleAdmission();

            boolean undoAdmission = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_undoAdmission, 6);

            if (undoAdmission) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_undoAdmission);
            }

            boolean readyForAdmissionBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_readyForAdmission, 10);

            m_assert.assertFalse(readyForAdmissionBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sReadyForAdmissionPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd();

            readyForAdmissionBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_readyForAdmission, 10);
            m_assert.assertTrue(readyForAdmissionBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Admit patient access")
    public void validatePolicy_admitPatient() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sAdmitPatientPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            scheduleAdmission();

            boolean clickReadyForAdmission = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_readyForAdmission, 6);

            if (clickReadyForAdmission) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_readyForAdmission);
            }

            boolean admitPatientBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 10);

            m_assert.assertFalse(admitPatientBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sAdmitPatientPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd();

            admitPatientBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 10);
            m_assert.assertTrue(admitPatientBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Discharge patient access")
    public void validatePolicy_dischargePatient() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sDischargePatientPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            scheduleAdmission();

            boolean clickReadyForAdmission = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_readyForAdmission, 6);
            if (clickReadyForAdmission) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_readyForAdmission);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 10);
            }

            boolean admitBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 6);
            if (admitBtn) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_admitPatient);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.header_admissionForm, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_saveAdmissionForm);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
            }

            boolean dischargePatientBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_dischargePatient, 10);

            m_assert.assertFalse(dischargePatientBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sDischargePatientPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabMyQueue);

            dischargePatientBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_dischargePatient, 10);
            m_assert.assertTrue(dischargePatientBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Re-Admit patient access")
    public void validatePolicy_readmitPatient() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sReAdmitPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            scheduleAdmission();

            boolean admissionScheduled = selectPatientFromIpd(sTabScheduled);

            if (admissionScheduled) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_readyForAdmission, 6);
                Cls_Generic_Methods.clickElement(oPage_IPD.button_readyForAdmission);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 10);
            } else {
                selectPatientFromIpd(sTabMyQueue);
            }

            boolean admitBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 6);
            if (admitBtn) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_admitPatient);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.header_admissionForm, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_saveAdmissionForm);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
            }

            boolean dischargeBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_dischargePatient, 10);

            if (dischargeBtn) {
                Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_dischargePatient);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_saveDischargePatient, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_saveDischargePatient);
                Cls_Generic_Methods.customWait(4);
            }

            selectPatientFromIpd(sTabDischargedToday);
            boolean clickToReadmitBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_clickToReadmit, 10);

            m_assert.assertFalse(clickToReadmitBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sReAdmitPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabDischargedToday);
            clickToReadmitBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_clickToReadmit, 10);

            m_assert.assertTrue(clickToReadmitBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Ready for OT access")
    public void validatePolicy_readyForOt() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_OT = new Page_OT(driver);

        try {

            setPolicy(sReadyForOtPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            selectPatientFromOt();

            boolean sendToBtnDisplayed = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PatientAppointmentDetails.list_btnSendToUsers, 10);

            if (!sendToBtnDisplayed) {
                Cls_Generic_Methods.clickElement(oPage_OT.button_deleteOt);
                selectPatientFromOt();
            }

            boolean readyForOtBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_readyForOT, 10);
            m_assert.assertFalse(readyForOtBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sReadyForOtPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromOt();
            readyForOtBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_readyForOT, 10);
            m_assert.assertTrue(readyForOtBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Engage OT access")
    public void validatePolicy_engageOt() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_OT = new Page_OT(driver);

        try {

            setPolicy(sEngageOtPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            selectPatientFromOt();

            boolean sendToBtnDisplayed = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PatientAppointmentDetails.list_btnSendToUsers, 10);

            if (!sendToBtnDisplayed) {
                Cls_Generic_Methods.clickElement(oPage_OT.button_deleteOt);
                selectPatientFromOt();
            }

            boolean readyForOtBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_readyForOT, 10);

            if (readyForOtBtnDisplayed) {
                Cls_Generic_Methods.clickElement(oPage_OT.button_readyForOT);
            }

            boolean engageOtBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_engageOt, 10);
            m_assert.assertFalse(engageOtBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEngageOtPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromOt();
            engageOtBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_engageOt, 10);
            m_assert.assertTrue(engageOtBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Mark as OT done access")
    public void validatePolicy_markAsDone() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_OT = new Page_OT(driver);

        try {

            setPolicy(sMarkAsDonePolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            selectPatientFromOt();

            boolean sendToBtnDisplayed = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PatientAppointmentDetails.list_btnSendToUsers, 10);

            if (!sendToBtnDisplayed) {
                Cls_Generic_Methods.clickElement(oPage_OT.button_deleteOt);
                selectPatientFromOt();
            }

            boolean readyForOtBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_readyForOT, 10);

            if (readyForOtBtnDisplayed) {
                Cls_Generic_Methods.clickElement(oPage_OT.button_readyForOT);
            }

            boolean engageOtBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_engageOt, 10);
            if (engageOtBtnDisplayed) {
                Cls_Generic_Methods.clickElement(oPage_OT.button_engageOt);
            }

            boolean markAsDoneBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_markAsDone, 10);
            m_assert.assertFalse(markAsDoneBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sMarkAsDonePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromOt();
            markAsDoneBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_markAsDone, 10);
            m_assert.assertTrue(markAsDoneBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Send Patients to Ward department access")
    public void validatePolicy_sendToWard() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_OT = new Page_OT(driver);

        try {

            setPolicy(sSendToWardPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            selectPatientFromOt();

            boolean sendToBtnDisplayed = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PatientAppointmentDetails.list_btnSendToUsers, 5);

            if (!sendToBtnDisplayed) {
                Cls_Generic_Methods.clickElement(oPage_OT.button_deleteOt);
                selectPatientFromOt();
            }

            boolean readyForOtBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_readyForOT, 5);

            if (readyForOtBtnDisplayed) {
                Cls_Generic_Methods.clickElement(oPage_OT.button_readyForOT);
            }

            boolean engageOtBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_engageOt, 5);
            if (engageOtBtnDisplayed) {
                Cls_Generic_Methods.clickElement(oPage_OT.button_engageOt);
            }

            boolean markAsDoneBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_markAsDone, 5);
            if (markAsDoneBtnDisplayed) {
                Cls_Generic_Methods.clickElement(oPage_OT.button_markAsDone);
            }

            boolean sendToWardBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_sendToWard, 10);
            m_assert.assertFalse(sendToWardBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sSendToWardPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromOt();
            sendToWardBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_sendToWard, 10);
            m_assert.assertTrue(sendToWardBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Ready for Admission Undo access")
    public void validatePolicy_undoReadyForAdmission() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sUndoPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            scheduleAdmission();

            boolean clickReadyForAdmission = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_readyForAdmission, 6);
            if (clickReadyForAdmission) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_readyForAdmission);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 10);
            }

            boolean undoAdmissionBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_undoAdmission, 10);
            m_assert.assertFalse(undoAdmissionBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sUndoPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabMyQueue);

            undoAdmissionBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_undoAdmission, 10);
            m_assert.assertTrue(undoAdmissionBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Admission Date and Time Edit access")
    public void validatePolicy_editAdmissionDateAndTime() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sEditAdmissionDataTimePolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();

            if (!selectPatientFromIpd(sTabScheduled) || selectPatientFromIpd(sTabMyQueue)) {
                scheduleAdmission(true);
            }

            selectPatientFromIpd(sTabScheduled);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_readyForAdmission, 10);
            Cls_Generic_Methods.clickElement(oPage_IPD.button_readyForAdmission);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 10);
            Cls_Generic_Methods.clickElement(oPage_IPD.button_admitPatient);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.header_admissionForm, 10);

            boolean admissionDateAndTimeEnabled = Cls_Generic_Methods.isElementDisplayed(oPage_IPD.select_admissionDateAdmissionForm)
                    && Cls_Generic_Methods.isElementDisplayed(oPage_IPD.select_admissionTimeAdmissionForm);
            m_assert.assertFalse(admissionDateAndTimeEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditAdmissionDataTimePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabScheduled);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 10);
            Cls_Generic_Methods.clickElement(oPage_IPD.button_admitPatient);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.header_admissionForm, 10);

            admissionDateAndTimeEnabled = Cls_Generic_Methods.isElementDisplayed(oPage_IPD.select_admissionDateAdmissionForm)
                    && Cls_Generic_Methods.isElementDisplayed(oPage_IPD.select_admissionTimeAdmissionForm);
            m_assert.assertTrue(admissionDateAndTimeEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Ready for Admission Undo access")
    public void validatePolicy_naPatient() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sNaPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            scheduleAdmission();

            boolean runningAdmission = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_runningAdmission, 3);
            if (runningAdmission) {
                selectPatientFromIpd(sTabMyQueue);
            }

            boolean clickReadyForAdmission = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_readyForAdmission, 6);
            if (clickReadyForAdmission) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_readyForAdmission);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 10);
            }

            boolean admitBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 6);
            if (admitBtn) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_admitPatient);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.header_admissionForm, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_saveAdmissionForm);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

            }

            boolean notArrivedBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_notArrived, 10);

            m_assert.assertFalse(notArrivedBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sNaPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabMyQueue);

            notArrivedBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_notArrived, 10);
            m_assert.assertTrue(notArrivedBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Give Billing Clearance access")
    public void validatePolicy_billingClearance() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sBillingClearancePolicyComponent, false);
            enableOrDisableTimeSlot(false);
            enableOrDisableBillingClearanceInIpd(true);

            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            scheduleAdmission();

            boolean runningAdmission = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_runningAdmission, 3);
            if (runningAdmission) {
                selectPatientFromIpd(sTabMyQueue);
            }

            boolean clickReadyForAdmission = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_readyForAdmission, 6);
            if (clickReadyForAdmission) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_readyForAdmission);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 10);
            }

            boolean admitBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 6);
            if (admitBtn) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_admitPatient);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.header_admissionForm, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_saveAdmissionForm);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

            }

            boolean billingClearanceBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_billClearance, 10);
            m_assert.assertFalse(billingClearanceBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sBillingClearancePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabMyQueue);

            billingClearanceBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_billClearance, 10);
            m_assert.assertTrue(billingClearanceBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Undo Billing Clearance access")
    public void validatePolicy_undoBillingClearance() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sUndoBillingClearancePolicyComponent, false);
            enableOrDisableTimeSlot(false);
            enableOrDisableBillingClearanceInIpd(true);

            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            scheduleAdmission();

            boolean runningAdmission = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_runningAdmission, 3);
            if (runningAdmission) {
                selectPatientFromIpd(sTabMyQueue);
            }

            boolean clickReadyForAdmission = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_readyForAdmission, 6);
            if (clickReadyForAdmission) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_readyForAdmission);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 10);
            }

            boolean admitBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 6);
            if (admitBtn) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_admitPatient);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.header_admissionForm, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_saveAdmissionForm);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

            }

            boolean billingClearanceBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_billClearance, 10);
            if (billingClearanceBtnDisplayed) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_billClearance);
            }

            boolean undoBillingClearanceBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_undoBill, 10);
            m_assert.assertFalse(undoBillingClearanceBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sUndoBillingClearancePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabMyQueue);

            undoBillingClearanceBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_undoBill, 10);
            m_assert.assertTrue(undoBillingClearanceBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

}
