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
import pages.settings.facilitySettings.general.referralSources.Page_ReferralSources;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.settings.organisationSettings.general.Page_PayerMaster;

public class ReferralSourcePolicyTest extends EHR_Setting_Policy{
    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_IPD oPage_IPD;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PayerMaster oPage_PayerMaster;
    Page_ReferralSources oPage_ReferralSources;
    String sViewReferralSourcesPolicyComponent = "VIEW (REFERRAL SOURCES)";
    String sAddSubReferralSourcesPolicyComponent = "ADD SUB REFERRAL (REFERRAL SOURCES)";
    String sEditReferralSourcesPolicyComponent = "EDIT (REFERRAL SOURCES)";
    String sDisableReferralSourcesPolicyComponent = "DELETE (REFERRAL SOURCES)";


    @Test(description = "Validate Referral Sources View Access")
    public void validatePolicy_viewReferralSources() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);

        try {

            setPolicy(sViewReferralSourcesPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            boolean viewDispensaryMasterSetting = CommonActions.selectOptionFromLeftInSettingsPanel("General", "Referral Sources");
            m_assert.assertFalse(viewDispensaryMasterSetting, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewReferralSourcesPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            viewDispensaryMasterSetting = CommonActions.selectOptionFromLeftInSettingsPanel("General", "Referral Sources");
            m_assert.assertTrue(viewDispensaryMasterSetting, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Add Sub Referral Sources Access")
    public void validatePolicy_addSubReferralSources() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_ReferralSources=new Page_ReferralSources(driver);

        try {

            setPolicy(sAddSubReferralSourcesPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Referral Sources");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ReferralSources.button_AddSubReferral,10);
            boolean addSubReferralBtnEnabled = isButtonEnabled(oPage_ReferralSources.button_AddSubReferral);

            m_assert.assertFalse(addSubReferralBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sAddSubReferralSourcesPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Referral Sources");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ReferralSources.button_AddSubReferral,10);
            addSubReferralBtnEnabled = isButtonEnabled(oPage_ReferralSources.button_AddSubReferral);

            m_assert.assertTrue(addSubReferralBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Edit Referral Sources Access")
    public void validatePolicy_editReferralSources() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_ReferralSources=new Page_ReferralSources(driver);

        try {

            setPolicy(sEditReferralSourcesPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Referral Sources");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ReferralSources.button_AddSubReferral,6);
            boolean editSubReferralBtnEnabled =Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_ReferralSources.list_EditButtonsOnTable,10);

            m_assert.assertFalse(editSubReferralBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditReferralSourcesPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Referral Sources");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ReferralSources.button_AddSubReferral,6);
            editSubReferralBtnEnabled =Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_ReferralSources.list_EditButtonsOnTable,10);

            m_assert.assertTrue(editSubReferralBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Disable Referral Sources Access")
    public void validatePolicy_disableReferralSources() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_ReferralSources=new Page_ReferralSources(driver);

        try {

            setPolicy(sDisableReferralSourcesPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Referral Sources");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ReferralSources.button_AddSubReferral,6);
            boolean deleteSubReferralBtnEnabled =Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_ReferralSources.list_DeleteButtonsOnTable,10);

            m_assert.assertFalse(deleteSubReferralBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sDisableReferralSourcesPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Referral Sources");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ReferralSources.button_AddSubReferral,6);
            deleteSubReferralBtnEnabled =Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_ReferralSources.list_DeleteButtonsOnTable,10);

            m_assert.assertTrue(deleteSubReferralBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

}
