package tests.authorizationpolicy.ehrSetting;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.settings.organisationSettings.general.Page_PanelMaster;
import pages.settings.organisationSettings.general.Page_PayerMaster;

public class PanelMasterPolicyTest extends EHR_Setting_Policy {

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_IPD oPage_IPD;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PayerMaster oPage_PayerMaster;
    Page_PanelMaster oPage_PanelMaster;

    String sViewPanelMasterPolicyComponent = "VIEW (PANEL MASTER)";
    String sAddPanelMasterPolicyComponent = "ADD PANEL (PANEL MASTER)";
    String sEditPanelMasterPolicyComponent = "EDIT (PANEL MASTER)";
    String sDisablePanelMasterPolicyComponent = "DISABLE (PANEL MASTER)";
    String sActivatePanelMasterPolicyComponent = "ACTIVATE (PANEL MASTER)";


    @Test(description = "Validate Panel Master View Access")
    public void validatePolicy_viewPanelMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sViewPanelMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            boolean viewPanelMasterSetting = CommonActions.selectOptionFromLeftInSettingsPanel("General", "Panel Master");
            m_assert.assertFalse(viewPanelMasterSetting, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewPanelMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            viewPanelMasterSetting = CommonActions.selectOptionFromLeftInSettingsPanel("General", "Panel Master");
            m_assert.assertTrue(viewPanelMasterSetting, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Add Panel Master Access")
    public void validatePolicy_addPanelMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_PayerMaster = new Page_PayerMaster(driver);
        oPage_PanelMaster = new Page_PanelMaster(driver);

        try {

            setPolicy(sAddPanelMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Panel Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PanelMaster.button_addPanel, 10);
            boolean addPanelMasterEnabled = isButtonEnabled(oPage_PanelMaster.button_addPanel);

            m_assert.assertFalse(addPanelMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sAddPanelMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Panel Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PanelMaster.button_addPanel, 10);
            addPanelMasterEnabled = isButtonEnabled(oPage_PanelMaster.button_addPanel);

            m_assert.assertTrue(addPanelMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Edit Panel Master Access")
    public void validatePolicy_editPanelMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_PayerMaster = new Page_PayerMaster(driver);
        oPage_PanelMaster = new Page_PanelMaster(driver);

        try {

            setPolicy(sEditPanelMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Panel Master");
            boolean editPanelMasterEnabled =Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PanelMaster.list_editPanelButton, 6);

            m_assert.assertFalse(editPanelMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditPanelMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Panel Master");
            editPanelMasterEnabled =Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PanelMaster.list_editPanelButton, 10);

            m_assert.assertTrue(editPanelMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }


    }

    @Test(description = "Validate Disable Panel Master Access")
    public void validatePolicy_disablePanelMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_PayerMaster = new Page_PayerMaster(driver);
        oPage_PanelMaster = new Page_PanelMaster(driver);

        try {

            setPolicy(sDisablePanelMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Panel Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PanelMaster.list_disablePanelButton, 10);
            boolean disablePanelMasterBtnEnabled = isButtonEnabled(oPage_PanelMaster.list_disablePanelButton.get(0));

            m_assert.assertFalse(disablePanelMasterBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sDisablePanelMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Panel Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PanelMaster.list_disablePanelButton, 10);
            disablePanelMasterBtnEnabled = isButtonEnabled(oPage_PanelMaster.list_disablePanelButton.get(0));

            m_assert.assertTrue(disablePanelMasterBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate activate Panel Master Access")
    public void validatePolicy_activatePanelMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_PayerMaster = new Page_PayerMaster(driver);
        oPage_PanelMaster = new Page_PanelMaster(driver);

        try {

            setPolicy(sActivatePanelMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Panel Master");
            boolean activatePanelMasterBtnEnabled = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PanelMaster.list_activePanelButton, 10);

            m_assert.assertFalse(activatePanelMasterBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sActivatePanelMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Panel Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PanelMaster.button_addPanel, 10);
            activatePanelMasterBtnEnabled = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PanelMaster.list_activePanelButton, 10);

            m_assert.assertTrue(activatePanelMasterBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

}
