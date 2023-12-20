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
import pages.settings.organisationSettings.general.Page_NabhAccreditation;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;

public class NabhSettingPolicyTest extends EHR_Setting_Policy{

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_NabhAccreditation oPage_NabhAccreditation=new Page_NabhAccreditation(driver);
    Page_OPD oPage_OPD;
    Page_IPD oPage_IPD;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    String sChangeNabhSettingPolicyComponent = "CHANGE NABH SETTING (NABH SETTING)";


    @Test(description = "Validate View Policy and change Setting Policy")
    public void validatePolicy_changeNabhSetting() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_NabhAccreditation=new Page_NabhAccreditation(driver);

        try {

            setPolicy(sChangeNabhSettingPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "NABH/Accreditation");

            boolean viewNabhSetting =Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_NabhAccreditation.list_radioButtonNabhSetting,10);
            m_assert.assertFalse(viewNabhSetting, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sChangeNabhSettingPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "NABH/Accreditation");
            viewNabhSetting =Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_NabhAccreditation.list_radioButtonNabhSetting,10);
            m_assert.assertTrue(viewNabhSetting, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }


}
