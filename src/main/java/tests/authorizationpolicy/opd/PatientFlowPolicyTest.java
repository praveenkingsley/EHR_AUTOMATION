package tests.authorizationpolicy.opd;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;

public class PatientFlowPolicyTest extends OPD_Policy {

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    String sMarkAsArrivedPolicyComponent = "MARK AS ARRIVED (PATIENT FLOW)";
    String sNotArrivedPolicyComponent = "NOT ARRIVED (PATIENT FLOW)";
    String sMarkAsAwayPolicyComponent = "MARK AS AWAY (PATIENT FLOW)";
    String sGetPatientPolicyComponent = "GET PATIENT (PATIENT FLOW)";
    String sSendPatientPolicyComponent = "SEND (PATIENT FLOW)";
    String sMarkAsCompletedPolicyComponent = "MARK AS COMPLETED (PATIENT FLOW)";
    String sDilatePolicyComponent = "DILATE (PATIENT FLOW)";
    String sStopDilationPolicyComponent = "STOP DILATATION (PATIENT FLOW)";
    String sResetDilationPolicyComponent = "RESET DILATATION (PATIENT FLOW)";
    String sMarkAsArrivedMyQueuePolicyComponent = "MARK AS ARRIVE-MYQUEUE (PATIENT FLOW)";
    String sMarkAsArrivedMyStationPolicyComponent = "MARK AS ARRIVE-MYSTATION (PATIENT FLOW)";

    @Test(description = "Validate OPD patient Mark as arrive access")
    public void validatePolicy_markAsArrivedPatientFlow() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sMarkAsArrivedPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            createPatient();

            boolean notArrivedBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_notArrived, 10);

            if (notArrivedBtn) {
                Cls_Generic_Methods.clickElement(oPage_OPD.button_notArrived);
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_markPatientArrived, 10);
            Cls_Generic_Methods.clickElement(oPage_OPD.button_markPatientArrived);
            boolean markPatientArrivedButtonEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_notArrived, 5);
            m_assert.assertFalse(markPatientArrivedButtonEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sMarkAsArrivedPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_markPatientArrived, 10);
            Cls_Generic_Methods.clickElement(oPage_OPD.button_markPatientArrived);

            markPatientArrivedButtonEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_notArrived, 5);

            m_assert.assertTrue(markPatientArrivedButtonEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Undo OPD patients to Not Arrived access")
    public void validatePolicy_notArrivedPatientFlow() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sNotArrivedPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            createPatient();

            boolean markArrivedBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_markPatientArrived, 10);

            if (markArrivedBtn) {
                Cls_Generic_Methods.clickElement(oPage_OPD.button_markPatientArrived);
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_notArrived, 10);
            boolean notArrivedButtonEnabled = Cls_Generic_Methods.isElementEnabled(oPage_OPD.button_notArrived);
            m_assert.assertFalse(notArrivedButtonEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sNotArrivedPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            notArrivedButtonEnabled = Cls_Generic_Methods.isElementEnabled(oPage_OPD.button_notArrived);

            m_assert.assertTrue(notArrivedButtonEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Mark OPD patients as Away access")
    public void validatePolicy_markAsAwayPatientFlow() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sMarkAsAwayPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            createPatient();
            boolean patientInMyQueue = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_markPatientArrived, 10);

            if (patientInMyQueue) {
                Cls_Generic_Methods.clickElement(oPage_OPD.button_markPatientArrived);
                Cls_Generic_Methods.customWait(4);
            }

            sendPatientToOtherUser("OPTOMETRIST", EHR_Data.user_HGOptom1);
            Cls_Generic_Methods.customWait();
            selectPatientFromAllTab();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_getPatient, 10);
            Cls_Generic_Methods.clickElement(oPage_OPD.button_getPatient);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_markAsAway, 10);

            boolean markAsAwayBtnEnabled = isButtonEnabled(oPage_OPD.button_markAsAway);

            m_assert.assertFalse(markAsAwayBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sMarkAsAwayPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();
            markAsAwayBtnEnabled = isButtonEnabled(oPage_OPD.button_markAsAway);

            m_assert.assertTrue(markAsAwayBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Get OPD Patients from others users queue to my queue")
    public void validatePolicy_getPatientFlow() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sGetPatientPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            createPatient();

            boolean getPatientInMyQueue = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_markPatientArrived, 10);

            if (getPatientInMyQueue) {
                Cls_Generic_Methods.clickElement(oPage_OPD.button_markPatientArrived);
                Cls_Generic_Methods.customWait(4);
            }

            boolean getPatient = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_getPatient, 10);

            if (!getPatient) {
                sendPatientToOtherUser("OPTOMETRIST", EHR_Data.user_HGOptom1);
                Cls_Generic_Methods.customWait();
            }

            selectPatientFromAllTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_getPatient, 10);

            boolean getPatientBtnEnabled = isButtonEnabled(oPage_OPD.button_getPatient);

            m_assert.assertFalse(getPatientBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sGetPatientPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();
            getPatientBtnEnabled = isButtonEnabled(oPage_OPD.button_getPatient);

            m_assert.assertTrue(getPatientBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Patient send access")
    public void validatePolicy_sendPatientFlow() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            setPolicy(sSendPatientPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            createPatient();

            boolean getPatientInMyQueue = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_getPatient, 10);

            if (getPatientInMyQueue) {
                Cls_Generic_Methods.clickElement(oPage_OPD.button_getPatient);
                Cls_Generic_Methods.customWait(4);
            }

            boolean sendToBtnDisplayed = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PatientAppointmentDetails.list_btnSendToUsers, 10);
            m_assert.assertFalse(sendToBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sSendPatientPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            sendToBtnDisplayed = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PatientAppointmentDetails.list_btnSendToUsers, 10);

            m_assert.assertTrue(sendToBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Patient Mark As Completed access")
    public void validatePolicy_markAsCompletedPatientFlow() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            setPolicy(sMarkAsCompletedPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            createPatient();

            boolean getPatientInMyQueue = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_getPatient, 10);

            if (getPatientInMyQueue) {
                Cls_Generic_Methods.clickElement(oPage_OPD.button_getPatient);
                Cls_Generic_Methods.customWait(4);
            }

            boolean markAsCompletedBtnDisplayed = isButtonEnabled(oPage_PatientAppointmentDetails.button_markPatientAsCompleted);
            m_assert.assertFalse(markAsCompletedBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sMarkAsCompletedPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            markAsCompletedBtnDisplayed = isButtonEnabled(oPage_PatientAppointmentDetails.button_markPatientAsCompleted);

            m_assert.assertTrue(markAsCompletedBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Patient Dilation access")
    public void validatePolicy_dilatePatientFlow() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            setPolicy(sDilatePolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            createPatient();

            boolean getPatientInMyQueue = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_getPatient, 10);

            if (getPatientInMyQueue) {
                Cls_Generic_Methods.clickElement(oPage_OPD.button_getPatient);
                Cls_Generic_Methods.customWait(4);
            }

            boolean dilateBtnDisplayed = isButtonEnabled(oPage_PatientAppointmentDetails.button_dilate_OR_dilateAgain);
            m_assert.assertFalse(dilateBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sDilatePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            dilateBtnDisplayed = isButtonEnabled(oPage_PatientAppointmentDetails.button_dilate_OR_dilateAgain);

            m_assert.assertTrue(dilateBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Patient Dilation Stop access")
    public void validatePolicy_stopDilatePatientFlow() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        String dilationDilatingUser="Test";
        String dilationDilationComment="Test";


        try {

            setPolicy(sStopDilationPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            createPatient();

            boolean getPatientInMyQueue = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_getPatient, 10);

            if (getPatientInMyQueue) {
                Cls_Generic_Methods.clickElement(oPage_OPD.button_getPatient);
                Cls_Generic_Methods.customWait(4);
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_dilate_OR_dilateAgain, 10);
            Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_dilate_OR_dilateAgain);
            boolean bDilationStarted = CommonActions.fillDilationDetailsByIndex(1, 1, 1, dilationDilatingUser, dilationDilationComment);

            boolean stopDilationBtnEnabled=false;

            if(bDilationStarted){
                stopDilationBtnEnabled=Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_stopDilation,10);
            }

            m_assert.assertFalse(stopDilationBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sStopDilationPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            stopDilationBtnEnabled=Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_stopDilation,10);

            if(stopDilationBtnEnabled){
                Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_stopDilation);
                m_assert.assertTrue("<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Patient Dilation Reset access")
    public void validatePolicy_resetDilatePatientFlow() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            setPolicy(sResetDilationPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            createPatient();

            boolean getPatientInMyQueue = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_getPatient, 10);

            if (getPatientInMyQueue) {
                Cls_Generic_Methods.clickElement(oPage_OPD.button_getPatient);
                Cls_Generic_Methods.customWait(4);
            }

            boolean resetDilationBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_resetDilation, 10);

            if (!resetDilationBtnDisplayed) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_dilate_OR_dilateAgain, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_dilate_OR_dilateAgain);
                CommonActions.fillDilationDetailsByIndex(1, 1, 1, "TEST", "TEST");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_stopDilation,10);
                Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_stopDilation);
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_resetDilation, 10);
            boolean resetDilateEnabled = isButtonEnabled(oPage_PatientAppointmentDetails.button_resetDilation);

            m_assert.assertFalse(resetDilateEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sResetDilationPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_resetDilation, 10);
            resetDilateEnabled = isButtonEnabled(oPage_PatientAppointmentDetails.button_resetDilation);
            m_assert.assertTrue(resetDilateEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Patients Mark as Arrived into My Queue.")
    public void validatePolicy_markAsArrivedMyQueuePatientFlow() {

        setDefaultUser(sMarkAsArrivedMyQueuePolicyComponent);

        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails=new Page_PatientAppointmentDetails(driver);

        try {
            setPolicy(sMarkAsArrivedMyQueuePolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            createPatient();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_markAsArrivedMyQueue, 10);
            boolean markAsArrivedMyQueueButtonEnabled = isButtonEnabled(oPage_PatientAppointmentDetails.button_markAsArrivedMyQueue);

            m_assert.assertFalse(markAsArrivedMyQueueButtonEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sMarkAsArrivedMyQueuePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_markAsArrivedMyQueue, 10);
            markAsArrivedMyQueueButtonEnabled = isButtonEnabled(oPage_PatientAppointmentDetails.button_markAsArrivedMyQueue);

            m_assert.assertTrue(markAsArrivedMyQueueButtonEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Patients Mark as Arrived into My Station.")
    public void validatePolicy_markAsArrivedMyStationPatientFlow() {

        setDefaultUser(sMarkAsArrivedMyStationPolicyComponent);

        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails=new Page_PatientAppointmentDetails(driver);

        try {
            setPolicy(sMarkAsArrivedMyStationPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            createPatient();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_markAsArrivedMyStation, 10);
            boolean markAsArrivedMyQueueButtonEnabled = isButtonEnabled(oPage_PatientAppointmentDetails.button_markAsArrivedMyStation);

            m_assert.assertFalse(markAsArrivedMyQueueButtonEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sMarkAsArrivedMyStationPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_markAsArrivedMyStation, 10);
            markAsArrivedMyQueueButtonEnabled = isButtonEnabled(oPage_PatientAppointmentDetails.button_markAsArrivedMyStation);

            m_assert.assertTrue(markAsArrivedMyQueueButtonEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }
}
