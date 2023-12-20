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
import pages.settings.organisationSettings.general.Page_CorporateMaster;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.settings.organisationSettings.general.Page_PayerMaster;

public class CorporateMasterPolicyTest extends EHR_Setting_Policy{

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_IPD oPage_IPD;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PayerMaster oPage_PayerMaster;
    Page_CorporateMaster oPage_CorporateMaster;
    String sViewCorporateMasterPolicyComponent = "VIEW (CORPORATE MASTER)";
    String sAddCorporateMasterPolicyComponent = "ADD CORPORATE (CORPORATE MASTER)";
    String sEditCorporateMasterPolicyComponent = "EDIT (CORPORATE MASTER)";
    String sDisableCorporateMasterPolicyComponent = "DISABLE (CORPORATE MASTER)";
    String sActivateCorporateMasterPolicyComponent = "ACTIVATE (CORPORATE MASTER)";


    @Test(description = "Validate Corporate Master View Access")
    public void validatePolicy_viewCorporateMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);

        try {

            setPolicy(sViewCorporateMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            boolean viewCorporateMasterSetting = CommonActions.selectOptionFromLeftInSettingsPanel("General", "Corporate Master");
            m_assert.assertFalse(viewCorporateMasterSetting, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewCorporateMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            viewCorporateMasterSetting = CommonActions.selectOptionFromLeftInSettingsPanel("General", "Corporate Master");
            m_assert.assertTrue(viewCorporateMasterSetting, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Add Corporate Master Access")
    public void validatePolicy_addCorporateMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_CorporateMaster=new Page_CorporateMaster(driver);

        try {

            setPolicy(sAddCorporateMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Corporate Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CorporateMaster.button_addCorporate,10);
            boolean addCorporateMasterEnabled = isButtonEnabled(oPage_CorporateMaster.button_addCorporate);

            m_assert.assertFalse(addCorporateMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sAddCorporateMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Corporate Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CorporateMaster.button_addCorporate,10);
            addCorporateMasterEnabled = isButtonEnabled(oPage_CorporateMaster.button_addCorporate);

            m_assert.assertTrue(addCorporateMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Edit Corporate Master Access")
    public void validatePolicy_editCorporateMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_CorporateMaster=new Page_CorporateMaster(driver);
        try {

            setPolicy(sEditCorporateMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Corporate Master");
            boolean editCorporateMasterEnabled = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_CorporateMaster.list_editButton,10);

            m_assert.assertFalse(editCorporateMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditCorporateMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Corporate Master");
            editCorporateMasterEnabled = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_CorporateMaster.list_editButton,10);

            m_assert.assertTrue(editCorporateMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Disable Corporate Master Access")
    public void validatePolicy_disableCorporateMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_CorporateMaster=new Page_CorporateMaster(driver);

        try {

            setPolicy(sDisableCorporateMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Corporate Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_CorporateMaster.list_disableButton,10);
            boolean disableCorporateMasterEnabled =isButtonEnabled(oPage_CorporateMaster.list_disableButton.get(0));
            m_assert.assertFalse(disableCorporateMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sDisableCorporateMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Corporate Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_CorporateMaster.list_disableButton,10);
            disableCorporateMasterEnabled =isButtonEnabled(oPage_CorporateMaster.list_disableButton.get(0));

            m_assert.assertTrue(disableCorporateMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate activate Corporate Master Access")
    public void validatePolicy_activateCorporateMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_CorporateMaster=new Page_CorporateMaster(driver);

        try {

            setPolicy(sActivateCorporateMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Corporate Master");
            boolean activateCorporateMasterEnabled = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_CorporateMaster.list_activeButton,10);

            m_assert.assertFalse(activateCorporateMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sActivateCorporateMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Corporate Master");
            activateCorporateMasterEnabled = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_CorporateMaster.list_activeButton,10);
            m_assert.assertTrue(activateCorporateMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

}
