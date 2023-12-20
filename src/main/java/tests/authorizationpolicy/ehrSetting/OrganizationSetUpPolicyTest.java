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

public class OrganizationSetUpPolicyTest extends EHR_Setting_Policy {
    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_IPD oPage_IPD;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_ScheduleAdmission oPage_ScheduleAdmission;

    String sViewOrganizationSetUpPolicyComponent = "VIEW (ORGANIZATION SET UP)";
    String sEditOrganizationSettingPolicyComponent = "EDIT ORGANIZATION (ORGANIZATION SET UP)";
    String sOrgSettingPolicyComponent = "ORG. SETTINGS (ORGANIZATION SET UP)";
    String sViewAllUsersPolicyComponent = "ALL USER VIEW (ORGANIZATION SET UP)";
    String sAddUserPolicyComponent = "ADD USER (ORGANIZATION SET UP)";
    String sEditUserPolicyComponent = "EDIT USER (ORGANIZATION SET UP)";
    String sDisableUserPolicyComponent = "DISABLE USER (ORGANIZATION SET UP)";
    String sActivateUserPolicyComponent = "ACTIVATE (ORGANIZATION SET UP)";
    String sResetPasswordPolicyComponent = "RESET PASSWORD (ORGANIZATION SET UP)";
    String sLinkFacilityPolicyComponent = "LINK FACILITY (ORGANIZATION SET UP)";
    String sUnLinkFacilityPolicyComponent = "UNLINK FACILITY (ORGANIZATION SET UP)";
    String sViewAuditTrailPolicyComponent = "AUDIT TRAIL (ORGANIZATION SET UP)";
    String sViewFacilityLocationPolicyComponent = "FACILITY/LOCATION VIEW (ORGANIZATION SET UP)";
    String sAddFacilityLocationPolicyComponent = "ADD FACILITY/LOCATION (ORGANIZATION SET UP)";
    String sEditFacilityLocationPolicyComponent = "EDIT FACILITY (ORGANIZATION SET UP)";
    String sLinkFacilityLocationPolicyComponent = "LINK USER (ORGANIZATION SET UP)";
    String sUnLinkFacilityLocationPolicyComponent = "UNLINK USER (ORGANIZATION SET UP)";
    String sDisableFacilityLocationPolicyComponent = "DISABLE FACILITY (ORGANIZATION SET UP)";
    String sEnableFacilityLocationPolicyComponent = "ENABLE FACILITY (ORGANIZATION SET UP)";
    String sDownloadFacilityReportPolicyComponent = "DOWNLOAD FACILITY WISE REPORT (ORGANIZATION SET UP)";
    String sDownloadUserReportPolicyComponent = "DOWNLOAD USER WISE REPORT (ORGANIZATION SET UP)";


    @Test(description = "Validate General Setting View Access")
    public void validatePolicy_viewOrganizationSetUp() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sViewOrganizationSetUpPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            boolean viewOrganizationSetUp = CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            m_assert.assertFalse(viewOrganizationSetUp, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewOrganizationSetUpPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            viewOrganizationSetUp = CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            m_assert.assertTrue(viewOrganizationSetUp, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }


    @Test(description = "Validate Organization SetUp Edit Access")
    public void validatePolicy_editOrganizationSetUp() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sEditOrganizationSettingPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_editOrganisation, 10);
            boolean editOrganizationSetUp = isButtonEnabled(oPage_OrganisationSetup.button_editOrganisation);
            m_assert.assertFalse(editOrganizationSetUp, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditOrganizationSettingPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_editOrganisation, 10);
            editOrganizationSetUp = isButtonEnabled(oPage_OrganisationSetup.button_editOrganisation);
            m_assert.assertTrue(editOrganizationSetUp, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }


    @Test(description = "Validate Organization SetUp Org Settings Access")
    public void validatePolicy_OrgSettings() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sOrgSettingPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_orgSettings, 10);
            boolean orgSetting = isButtonEnabled(oPage_OrganisationSetup.button_orgSettings);
            m_assert.assertFalse(orgSetting, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sOrgSettingPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_orgSettings, 10);
            orgSetting = isButtonEnabled(oPage_OrganisationSetup.button_orgSettings);
            m_assert.assertTrue(orgSetting, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }


    @Test(description = "Validate Organization SetUp View All User Access")
    public void validatePolicy_viewAllUser() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sViewAllUsersPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUserInOrgLevel, 10);
            boolean viewAllUser = isButtonEnabled(oPage_OrganisationSetup.button_allUserInOrgLevel);
            m_assert.assertFalse(viewAllUser, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewAllUsersPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUserInOrgLevel, 10);
            viewAllUser = isButtonEnabled(oPage_OrganisationSetup.button_allUserInOrgLevel);
            m_assert.assertTrue(viewAllUser, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Organization SetUp View Add User Access")
    public void validatePolicy_viewAddUser() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sAddUserPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 10);
            boolean viewAddUser = isButtonEnabled(oPage_OrganisationSetup.button_addUser);
            m_assert.assertFalse(viewAddUser, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sAddUserPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 10);
            viewAddUser = isButtonEnabled(oPage_OrganisationSetup.button_addUser);
            m_assert.assertTrue(viewAddUser, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }


    @Test(description = "Validate Organization SetUp View Edit User Access")
    public void validatePolicy_viewEditUser() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sEditUserPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_editInUsers, 10);
            boolean viewEditUser = isButtonEnabled(oPage_OrganisationSetup.button_editInUsers);
            m_assert.assertFalse(viewEditUser, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditUserPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_editInUsers, 10);
            viewEditUser = isButtonEnabled(oPage_OrganisationSetup.button_editInUsers);
            m_assert.assertTrue(viewEditUser, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }


    @Test(description = "Validate Organization SetUp View Disable User Access")
    public void validatePolicy_viewDisableUser() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sDisableUserPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");

            boolean viewDisableUser = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_disableUsersIcon, 10);
            m_assert.assertFalse(viewDisableUser, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sDisableUserPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            viewDisableUser = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_disableUsersIcon, 10);
            m_assert.assertTrue(viewDisableUser, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }


    @Test(description = "Validate Organization SetUp View Activate User Access")
    public void validatePolicy_viewActivateUser() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sActivateUserPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_activate, 10);
            boolean viewActivateUser = isButtonEnabled(oPage_OrganisationSetup.button_activate);
            m_assert.assertFalse(viewActivateUser, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sActivateUserPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_activate, 10);
            viewActivateUser = isButtonEnabled(oPage_OrganisationSetup.button_activate);
            m_assert.assertTrue(viewActivateUser, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }

    @Test(description = "Validate Organization SetUp View Reset Password User Access")
    public void validatePolicy_viewResetPasswordUser() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sResetPasswordPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_resetPasswordIcon, 10);
            boolean viewResetPasswordUser = isButtonEnabled(oPage_OrganisationSetup.button_resetPasswordIcon);
            m_assert.assertFalse(viewResetPasswordUser, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sResetPasswordPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_resetPasswordIcon, 10);
            viewResetPasswordUser = isButtonEnabled(oPage_OrganisationSetup.button_resetPasswordIcon);
            m_assert.assertTrue(viewResetPasswordUser, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }


    @Test(description = "Validate Organization SetUp View Link Facility User Access")
    public void validatePolicy_viewLinkFacilityUser() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sLinkFacilityPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_linkFacilityIcon, 10);
            boolean viewLinkFacilityUser = isButtonEnabled(oPage_OrganisationSetup.button_linkFacilityIcon);
            m_assert.assertFalse(viewLinkFacilityUser, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sLinkFacilityPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_linkFacilityIcon, 10);
            viewLinkFacilityUser = isButtonEnabled(oPage_OrganisationSetup.button_linkFacilityIcon);
            m_assert.assertTrue(viewLinkFacilityUser, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }


    @Test(description = "Validate Organization SetUp View UnLink Facility User Access")
    public void validatePolicy_viewUnLinkFacilityUser() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sUnLinkFacilityPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_unlinkFacilityIcon, 10);
            boolean viewUnLinkFacilityUser = isButtonEnabled(oPage_OrganisationSetup.button_unlinkFacilityIcon);
            m_assert.assertFalse(viewUnLinkFacilityUser, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sUnLinkFacilityPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_unlinkFacilityIcon, 10);
            viewUnLinkFacilityUser = isButtonEnabled(oPage_OrganisationSetup.button_unlinkFacilityIcon);
            m_assert.assertTrue(viewUnLinkFacilityUser, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }

    @Test(description = "Validate Organization SetUp View Audit Trail User Access")
    public void validatePolicy_viewAuditTrailUser() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sViewAuditTrailPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_auditTrailIcon, 10);
            boolean viewAuditTrailUser = isButtonEnabled(oPage_OrganisationSetup.button_auditTrailIcon);
            m_assert.assertFalse(viewAuditTrailUser, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewAuditTrailPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_auditTrailIcon, 10);
            viewAuditTrailUser = isButtonEnabled(oPage_OrganisationSetup.button_auditTrailIcon);
            m_assert.assertTrue(viewAuditTrailUser, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }


    @Test(description = "Validate Organization SetUp View Facility Location User Access")
    public void validatePolicy_viewFacilityLocationUser() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sViewFacilityLocationPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_facilityLocation, 10);
            boolean viewFacilityLocationUser = isButtonEnabled(oPage_OrganisationSetup.button_facilityLocation);
            m_assert.assertFalse(viewFacilityLocationUser, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewFacilityLocationPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_facilityLocation, 10);
            viewFacilityLocationUser = isButtonEnabled(oPage_OrganisationSetup.button_facilityLocation);
            m_assert.assertTrue(viewFacilityLocationUser, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }

    @Test(description = "Validate Organization SetUp View Add Facility Location User Access")
    public void validatePolicy_viewAddFacilityLocationUser() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sAddFacilityLocationPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_facilityLocation, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_facilityLocation);

            boolean viewAddFacilityLocationUser = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addFacilityLocation, 10);
            m_assert.assertFalse(viewAddFacilityLocationUser, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sAddFacilityLocationPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_facilityLocation, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_facilityLocation);
            viewAddFacilityLocationUser = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addFacilityLocation, 10);
            m_assert.assertTrue(viewAddFacilityLocationUser, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }


    @Test(description = "Validate Organization SetUp View Edit Facility Access")
    public void validatePolicy_viewEditFacilityLocationUser() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sEditFacilityLocationPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_facilityLocation, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_facilityLocation);

            boolean viewEditFacilityLocationUser = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_editFacilityIcon, 10);
            m_assert.assertFalse(viewEditFacilityLocationUser, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditFacilityLocationPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_facilityLocation, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_facilityLocation);
            viewEditFacilityLocationUser = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_editFacilityIcon, 10);
            m_assert.assertTrue(viewEditFacilityLocationUser, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }


    @Test(description = "Validate Organization SetUp View Link Facility Access")
    public void validatePolicy_viewLinkFacilityLocationUser() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sLinkFacilityLocationPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_facilityLocation, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_facilityLocation);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_linkUserFacilityIcon, 10);
            boolean viewLinkFacilityLocationUser =isButtonEnabled(oPage_OrganisationSetup.button_linkUserFacilityIcon);
            m_assert.assertFalse(viewLinkFacilityLocationUser, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sLinkFacilityLocationPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_facilityLocation, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_facilityLocation);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_linkUserFacilityIcon, 10);
            viewLinkFacilityLocationUser =isButtonEnabled(oPage_OrganisationSetup.button_linkUserFacilityIcon);
            m_assert.assertTrue(viewLinkFacilityLocationUser, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }


    @Test(description = "Validate Organization SetUp View UnLink Facility Access")
    public void validatePolicy_viewUnLinkFacilityLocationUser() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sUnLinkFacilityLocationPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_facilityLocation, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_facilityLocation);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_unlinkUserFacilityIcon, 10);
            boolean viewUnLinkFacilityLocationUser =isButtonEnabled(oPage_OrganisationSetup.button_unlinkUserFacilityIcon);
            m_assert.assertFalse(viewUnLinkFacilityLocationUser, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sUnLinkFacilityLocationPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_facilityLocation, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_facilityLocation);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_unlinkUserFacilityIcon, 10);
            viewUnLinkFacilityLocationUser =isButtonEnabled(oPage_OrganisationSetup.button_unlinkUserFacilityIcon);
            m_assert.assertTrue(viewUnLinkFacilityLocationUser, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }


    @Test(description = "Validate Organization SetUp View Disable Facility Access")
    public void validatePolicy_viewDisableFacilityLocationUser() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sDisableFacilityLocationPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_facilityLocation, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_facilityLocation);

            boolean viewDisableFacilityLocationUser = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_disableUserFacilityIcon, 10);
            m_assert.assertFalse(viewDisableFacilityLocationUser, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sDisableFacilityLocationPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_facilityLocation, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_facilityLocation);
            viewDisableFacilityLocationUser = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_disableUserFacilityIcon, 10);
            m_assert.assertTrue(viewDisableFacilityLocationUser, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }


    @Test(description = "Validate Organization SetUp View Enable Facility Access")
    public void validatePolicy_viewEnableFacilityLocationUser() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sEnableFacilityLocationPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_facilityLocation, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_facilityLocation);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_activateUserFacilityIcon, 10);
            boolean viewEnableFacilityLocationUser = isButtonEnabled(oPage_OrganisationSetup.button_activateUserFacilityIcon);
            m_assert.assertFalse(viewEnableFacilityLocationUser, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEnableFacilityLocationPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_facilityLocation, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_facilityLocation);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_activateUserFacilityIcon, 10);
            viewEnableFacilityLocationUser = isButtonEnabled(oPage_OrganisationSetup.button_activateUserFacilityIcon);
            m_assert.assertTrue(viewEnableFacilityLocationUser, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }


    @Test(description = "Validate Organization SetUp View Download Facility Report Access")
    public void validatePolicy_viewDownloadFacilityReport() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sDownloadFacilityReportPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_facilityLocation, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_facilityLocation);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_downloadFacility, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_downloadFacility);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_downloadFacilityReport, 10);
            boolean viewDownloadFacilityReportUser = isButtonEnabled(oPage_OrganisationSetup.button_downloadFacilityReport);
            m_assert.assertFalse(viewDownloadFacilityReportUser, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sDownloadFacilityReportPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_facilityLocation, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_facilityLocation);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_downloadFacility, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_downloadFacility);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_downloadFacilityReport, 10);
            viewDownloadFacilityReportUser = isButtonEnabled(oPage_OrganisationSetup.button_downloadFacilityReport);
            m_assert.assertTrue(viewDownloadFacilityReportUser, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }


    @Test(description = "Validate Organization SetUp View Download User Report Access")
    public void validatePolicy_viewDownloadUserReport() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sDownloadUserReportPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_facilityLocation, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_facilityLocation);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_downloadFacility, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_downloadFacility);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_downloadUserReport, 10);
            boolean viewDownloadUserReport = isButtonEnabled(oPage_OrganisationSetup.button_downloadUserReport);
            m_assert.assertFalse(viewDownloadUserReport, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sDownloadUserReportPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_facilityLocation, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_facilityLocation);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_downloadFacility, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_downloadFacility);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_downloadUserReport, 10);
            viewDownloadUserReport = isButtonEnabled(oPage_OrganisationSetup.button_downloadUserReport);
            m_assert.assertTrue(viewDownloadUserReport, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }
    }
}


