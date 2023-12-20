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
import pages.settings.organisationSettings.general.Page_DispensaryMaster;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.settings.organisationSettings.general.Page_PayerMaster;

public class DispensaryMasterPolicyTest extends EHR_Setting_Policy{

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_IPD oPage_IPD;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PayerMaster oPage_PayerMaster;
    Page_DispensaryMaster oPage_DispensaryMaster;
    String sViewDispensaryMasterPolicyComponent = "VIEW (DISPENSARY MASTER)";
    String sAddDispensaryMasterPolicyComponent = "ADD DISPENSARY (DISPENSARY MASTER)";
    String sEditDispensaryMasterPolicyComponent = "EDIT (DISPENSARY MASTER)";
    String sDisableDispensaryMasterPolicyComponent = "DISABLE (DISPENSARY MASTER)";
    String sActivateDispensaryMasterPolicyComponent = "ACTIVATE (DISPENSARY MASTER)";


    @Test(description = "Validate Dispensary Master View Access")
    public void validatePolicy_viewDispensaryMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);

        try {

            setPolicy(sViewDispensaryMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            boolean viewDispensaryMasterSetting = CommonActions.selectOptionFromLeftInSettingsPanel("General", "Dispensary Master");
            m_assert.assertFalse(viewDispensaryMasterSetting, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewDispensaryMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            viewDispensaryMasterSetting = CommonActions.selectOptionFromLeftInSettingsPanel("General", "Dispensary Master");
            m_assert.assertTrue(viewDispensaryMasterSetting, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Add Dispensary Master Access")
    public void validatePolicy_addDispensaryMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_DispensaryMaster=new Page_DispensaryMaster(driver);

        try {

            setPolicy(sAddDispensaryMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Dispensary Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DispensaryMaster.button_addDispensary,10);
            boolean addDispensaryMasterEnabled = isButtonEnabled(oPage_DispensaryMaster.button_addDispensary);

            m_assert.assertFalse(addDispensaryMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sAddDispensaryMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Dispensary Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DispensaryMaster.button_addDispensary,10);
            addDispensaryMasterEnabled = isButtonEnabled(oPage_DispensaryMaster.button_addDispensary);

            m_assert.assertTrue(addDispensaryMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Edit Dispensary Master Access")
    public void validatePolicy_editDispensaryMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_DispensaryMaster=new Page_DispensaryMaster(driver);

        try {

            setPolicy(sEditDispensaryMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();
            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Dispensary Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DispensaryMaster.button_addDispensary,10);
            boolean editDispensaryMasterBtnEnabled =Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_DispensaryMaster.list_editButton,10);

            m_assert.assertFalse(editDispensaryMasterBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditDispensaryMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Dispensary Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DispensaryMaster.button_addDispensary,10);
            editDispensaryMasterBtnEnabled =Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_DispensaryMaster.list_editButton,10);

            m_assert.assertTrue(editDispensaryMasterBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Disable Dispensary Master Access")
    public void validatePolicy_disableDispensaryMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_DispensaryMaster=new Page_DispensaryMaster(driver);

        try {

            setPolicy(sDisableDispensaryMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();
            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Dispensary Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_DispensaryMaster.list_disableButton,10);
            boolean disableDispensaryMasterBtnEnabled =isButtonEnabled(oPage_DispensaryMaster.list_disableButton.get(0));

            m_assert.assertFalse(disableDispensaryMasterBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sDisableDispensaryMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Dispensary Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_DispensaryMaster.list_disableButton,10);
            disableDispensaryMasterBtnEnabled =isButtonEnabled(oPage_DispensaryMaster.list_disableButton.get(0));

            m_assert.assertTrue(disableDispensaryMasterBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }

    @Test(description = "Validate Activate Dispensary Master Access")
    public void validatePolicy_activateDispensaryMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_DispensaryMaster=new Page_DispensaryMaster(driver);

        try {

            setPolicy(sActivateDispensaryMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();
            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Dispensary Master");
            boolean activeDispensaryMasterBtnEnabled =Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_DispensaryMaster.list_activeButton,10);

            m_assert.assertFalse(activeDispensaryMasterBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sActivateDispensaryMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Dispensary Master");
            activeDispensaryMasterBtnEnabled =Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_DispensaryMaster.list_activeButton,10);

            m_assert.assertTrue(activeDispensaryMasterBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }
}
