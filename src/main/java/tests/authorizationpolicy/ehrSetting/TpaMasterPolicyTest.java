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
import pages.settings.organisationSettings.general.Page_PayerMaster;
import pages.settings.organisationSettings.general.Page_TpaMaster;

public class TpaMasterPolicyTest extends EHR_Setting_Policy{

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_IPD oPage_IPD;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PayerMaster oPage_PayerMaster;
    Page_TpaMaster oPage_TpaMaster;

    String sViewTpaMasterPolicyComponent = "VIEW (TPA MASTER)";
    String sAddTpaMasterPolicyComponent = "ADD TPA (TPA MASTER)";
    String sEditTpaMasterPolicyComponent = "EDIT (TPA MASTER)";
    String sDisableTpaMasterPolicyComponent = "DISABLE (TPA MASTER)";
    String sActivateTpaMasterPolicyComponent = "ACTIVATE (TPA MASTER)";


    @Test(description = "Validate Tpa Master View Access")
    public void validatePolicy_viewTpaMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);

        try {

            setPolicy(sViewTpaMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            boolean viewInsuranceMasterSetting = CommonActions.selectOptionFromLeftInSettingsPanel("General", "TPA Master");
            m_assert.assertFalse(viewInsuranceMasterSetting, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewTpaMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            viewInsuranceMasterSetting = CommonActions.selectOptionFromLeftInSettingsPanel("General", "TPA Master");
            m_assert.assertTrue(viewInsuranceMasterSetting, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Add Tpa Master Access")
    public void validatePolicy_addTpaMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_TpaMaster=new Page_TpaMaster(driver);

        try {

            setPolicy(sAddTpaMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "TPA Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TpaMaster.button_addTPA,10);
            boolean addTpaMasterEnabled = isButtonEnabled(oPage_TpaMaster.button_addTPA);

            m_assert.assertFalse(addTpaMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sAddTpaMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "TPA Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TpaMaster.button_addTPA,10);
            addTpaMasterEnabled = isButtonEnabled(oPage_TpaMaster.button_addTPA);

            m_assert.assertTrue(addTpaMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Edit Tpa Master Access")
    public void validatePolicy_editTpaMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_TpaMaster=new Page_TpaMaster(driver);

        try {

            setPolicy(sEditTpaMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "TPA Master");
            boolean editTpaMasterEnabled =Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_TpaMaster.list_editTpaButton,6);

            m_assert.assertFalse(editTpaMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditTpaMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "TPA Master");
            editTpaMasterEnabled =Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_TpaMaster.list_editTpaButton,10);


            m_assert.assertTrue(editTpaMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }

    @Test(description = "Validate Disable Tpa Master Access")
    public void validatePolicy_disableTpaMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_TpaMaster=new Page_TpaMaster(driver);

        try {

            setPolicy(sDisableTpaMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "TPA Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_TpaMaster.list_disableTpaButton,10);
            boolean disableTpaMasterEnabled = isButtonEnabled(oPage_TpaMaster.list_disableTpaButton.get(0));

            m_assert.assertFalse(disableTpaMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sDisableTpaMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "TPA Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_TpaMaster.list_disableTpaButton,10);
            disableTpaMasterEnabled = isButtonEnabled(oPage_TpaMaster.list_disableTpaButton.get(0));

            m_assert.assertTrue(disableTpaMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }

    @Test(description = "Validate Activate Tpa Master Access")
    public void validatePolicy_activateTpaMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_TpaMaster=new Page_TpaMaster(driver);

        try {

            setPolicy(sActivateTpaMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "TPA Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_TpaMaster.list_activeTpaButton,10);
            boolean activeTpaMasterEnabled = isButtonEnabled(oPage_TpaMaster.list_activeTpaButton.get(0));

            m_assert.assertFalse(activeTpaMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sActivateTpaMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "TPA Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_TpaMaster.list_activeTpaButton,10);
            activeTpaMasterEnabled = isButtonEnabled(oPage_TpaMaster.list_activeTpaButton.get(0));

            m_assert.assertTrue(activeTpaMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }
}
