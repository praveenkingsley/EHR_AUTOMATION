package tests.authorizationpolicy.opd;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;

public class AppointmentDetailsPolicyTest extends OPD_Policy{

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    String sPrintPolicyComponent = "PRINT (APPOINTMENT DETAILS)";
    String sEditPolicyComponent = "EDIT (APPOINTMENT DETAILS)";
    String sCancelPolicyComponent = "CANCEL (APPOINTMENT DETAILS)";
    String sReschedulePolicyComponent = "RESCHEDULE (APPOINTMENT DETAILS)";
    String sOverviewPolicyComponent = "OVERVIEW (APPOINTMENT DETAILS)";
    String sBillsPolicyComponent = "BILLS (APPOINTMENT DETAILS)";
    String sDiagnosisPolicyComponent = "DIAGNOSES (APPOINTMENT DETAILS)";
    String sInvestigationPolicyComponent = "INVESTIGATIONS (APPOINTMENT DETAILS)";
    String sProcedurePolicyComponent = "PROCEDURES (APPOINTMENT DETAILS)";
    String sPrescriptionPolicyComponent = "PRESCRIPTIONS (APPOINTMENT DETAILS)";
    String sGlassesPolicyComponent = "GLASSES (APPOINTMENT DETAILS)";
    String sConfirmAppointmentPolicyComponent = "CONFIRM APPOINTMENT (APPOINTMENT DETAILS)";


    @Test(description = "Validate Scheduled OPD appointment print access")
    public void validatePolicy_printAppointmentDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sPrintPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            CommonActions.loginFunctionality(sPolicyUser);

            boolean printScheduledAppointmentButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_printScheduledAppointments, 7);
            m_assert.assertFalse(printScheduledAppointmentButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sPrintPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            printScheduledAppointmentButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_printScheduledAppointments, 10);
            m_assert.assertTrue(printScheduledAppointmentButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Appointment details edit access")
    public void validatePolicy_editAppointmentDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sEditPolicyComponent, false);
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

            boolean editScheduledAppointmentButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_editScheduledAppointment, 7);
            m_assert.assertFalse(editScheduledAppointmentButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            editScheduledAppointmentButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_editScheduledAppointment, 10);
            m_assert.assertTrue(editScheduledAppointmentButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Appointment details cancel access")
    public void validatePolicy_cancelAppointmentDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sCancelPolicyComponent, false);
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

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_cancelScheduledAppointment, 7);

            boolean cancelScheduledAppointmentButtonEnabled = Cls_Generic_Methods.isElementEnabled(oPage_OPD.button_cancelScheduledAppointment);
            m_assert.assertFalse(cancelScheduledAppointmentButtonEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sCancelPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_cancelScheduledAppointment, 10);
            cancelScheduledAppointmentButtonEnabled = Cls_Generic_Methods.isElementEnabled(oPage_OPD.button_cancelScheduledAppointment);

            m_assert.assertTrue(cancelScheduledAppointmentButtonEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Appointment details Reschedule access")
    public void validatePolicy_rescheduleAppointmentDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sReschedulePolicyComponent, false);
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

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_ReScheduledAppointment, 7);
            boolean rescheduleAppointmentButtonEnabled = Cls_Generic_Methods.isElementEnabled(oPage_OPD.button_ReScheduledAppointment);

            m_assert.assertFalse(rescheduleAppointmentButtonEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sReschedulePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_ReScheduledAppointment, 7);
            rescheduleAppointmentButtonEnabled = Cls_Generic_Methods.isElementEnabled(oPage_OPD.button_ReScheduledAppointment);

            m_assert.assertTrue(rescheduleAppointmentButtonEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate RHS Overview details tab view access")
    public void validatePolicy_overviewAppointmentDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sOverviewPolicyComponent, false);
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

            boolean overviewTabDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tab_overviewOpdRhs, 7);
            m_assert.assertFalse(overviewTabDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sOverviewPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            overviewTabDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tab_overviewOpdRhs, 10);
            m_assert.assertTrue(overviewTabDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate RHS Bill section view access")
    public void validatePolicy_billAppointmentDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sBillsPolicyComponent, false);
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

            boolean billTabDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tab_billsOpdRhs, 7);
            m_assert.assertFalse(billTabDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sBillsPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            billTabDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tab_billsOpdRhs, 10);
            m_assert.assertTrue(billTabDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate RHS Section Diagnoses view access")
    public void validatePolicy_diagnosisAppointmentDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sDiagnosisPolicyComponent, false);
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

            boolean diagnosisTabDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tab_diagnosisOpdRhs, 7);
            m_assert.assertFalse(diagnosisTabDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sDiagnosisPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            diagnosisTabDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tab_diagnosisOpdRhs, 10);
            m_assert.assertTrue(diagnosisTabDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate RHS Section Investigations view access")
    public void validatePolicy_investigationsAppointmentDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sInvestigationPolicyComponent, false);
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

            boolean investigationsTabDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tab_investigationOpdRhs, 7);
            m_assert.assertFalse(investigationsTabDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sInvestigationPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            investigationsTabDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tab_investigationOpdRhs, 10);
            m_assert.assertTrue(investigationsTabDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate RHS Section Procedures view access")
    public void validatePolicy_procedureAppointmentDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sProcedurePolicyComponent, false);
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

            boolean procedureTabDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tab_proceduresOpdRhs, 7);
            m_assert.assertFalse(procedureTabDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sProcedurePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            procedureTabDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tab_proceduresOpdRhs, 10);
            m_assert.assertTrue(procedureTabDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate RHS Section Prescriptions view access")
    public void validatePolicy_prescriptionAppointmentDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sPrescriptionPolicyComponent, false);
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

            boolean prescriptionTabDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tab_prescriptionsOpdRhs, 7);
            m_assert.assertFalse(prescriptionTabDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sPrescriptionPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            prescriptionTabDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tab_prescriptionsOpdRhs, 10);
            m_assert.assertTrue(prescriptionTabDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate RHS Section Glasses prescription view access")
    public void validatePolicy_glassesAppointmentDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sGlassesPolicyComponent, false);
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

            boolean glassesTabDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tab_glassesOpdRhs, 7);
            m_assert.assertFalse(glassesTabDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sGlassesPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            glassesTabDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tab_glassesOpdRhs, 10);
            m_assert.assertTrue(glassesTabDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Appointments Confirmation access")
    public void validatePolicy_confirmAppointmentDetails() {

        //Policy user should be receptionist

        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sConfirmAppointmentPolicyComponent, false);
            enableOrDisableTimeSlot(true);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            CommonActions.loginFunctionality(sPolicyUser);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_calendarOpen,10);

            CommonActions.selectDateFromDatePicker(oPage_Navbar.button_calendarOpen,"01/01/2025");
            Cls_Generic_Methods.customWait(10);
            selectPatientFromAllTab("UNASSIGNED","POLICY USER");


            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_confirmAppointment, 10);
            boolean confirmAppointmentBtnDisplayed = Cls_Generic_Methods.isElementEnabled(oPage_OPD.button_confirmAppointment);

            m_assert.assertFalse(confirmAppointmentBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sConfirmAppointmentPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectDateFromDatePicker(oPage_Navbar.button_calendarOpen,"01/01/2025");
            Cls_Generic_Methods.customWait(10);
            selectPatientFromAllTab("UNASSIGNED","POLICY USER");


            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_confirmAppointment, 10);
            confirmAppointmentBtnDisplayed = Cls_Generic_Methods.isElementEnabled(oPage_OPD.button_confirmAppointment);

            m_assert.assertTrue(confirmAppointmentBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }


}
