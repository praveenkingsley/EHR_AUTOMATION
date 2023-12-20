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
import pages.settings.organisationSettings.general.Page_InsuranceMaster;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.settings.organisationSettings.general.Page_PayerMaster;

public class InsuranceMasterPolicyTest extends EHR_Setting_Policy{
    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_IPD oPage_IPD;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PayerMaster oPage_PayerMaster;
    Page_InsuranceMaster oPage_InsuranceMaster;

    String sViewInsuranceMasterPolicyComponent = "VIEW (INSURANCE MASTER)";
    String sAddInsuranceMasterPolicyComponent = "ADD INSURANCE (INSURANCE MASTER)";
    String sEditInsuranceMasterPolicyComponent = "EDIT (INSURANCE MASTER)";
    String sDisableInsuranceMasterPolicyComponent = "DISABLE (INSURANCE MASTER)";
    String sActivateInsuranceMasterPolicyComponent = "ACTIVATE (INSURANCE MASTER)";


    @Test(description = "Validate Insurance Master View Access")
    public void validatePolicy_viewInsuranceMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);

        try {

            setPolicy(sViewInsuranceMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            boolean viewInsuranceMasterSetting = CommonActions.selectOptionFromLeftInSettingsPanel("General", "Insurance Master");
            m_assert.assertFalse(viewInsuranceMasterSetting, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewInsuranceMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            viewInsuranceMasterSetting = CommonActions.selectOptionFromLeftInSettingsPanel("General", "Insurance Master");
            m_assert.assertTrue(viewInsuranceMasterSetting, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Add Insurance Master Access")
    public void validatePolicy_addInsuranceMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_InsuranceMaster=new Page_InsuranceMaster(driver);

        try {

            setPolicy(sAddInsuranceMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Insurance Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InsuranceMaster.button_addInsurance,10);
            boolean addInsuranceMasterEnabled = isButtonEnabled(oPage_InsuranceMaster.button_addInsurance);

            m_assert.assertFalse(addInsuranceMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sAddInsuranceMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Insurance Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InsuranceMaster.button_addInsurance,10);
            addInsuranceMasterEnabled = isButtonEnabled(oPage_InsuranceMaster.button_addInsurance);

            m_assert.assertTrue(addInsuranceMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Edit Insurance Master Access")
    public void validatePolicy_editInsuranceMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_InsuranceMaster=new Page_InsuranceMaster(driver);

        try {

            setPolicy(sEditInsuranceMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Insurance Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_InsuranceMaster.list_editButton,10);
            boolean editInsuranceMasterEnabled = isButtonEnabled(oPage_InsuranceMaster.list_editButton.get(0));

            m_assert.assertFalse(editInsuranceMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditInsuranceMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Insurance Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_InsuranceMaster.list_editButton,10);
            editInsuranceMasterEnabled = isButtonEnabled(oPage_InsuranceMaster.list_editButton.get(0));

            m_assert.assertTrue(editInsuranceMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Disable Insurance Master Access")
    public void validatePolicy_disableInsuranceMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_InsuranceMaster=new Page_InsuranceMaster(driver);

        try {

            setPolicy(sDisableInsuranceMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Insurance Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_InsuranceMaster.list_diableButton,10);
            boolean disableInsuranceMasterBtnEnabled = isButtonEnabled(oPage_InsuranceMaster.list_diableButton.get(0));

            m_assert.assertFalse(disableInsuranceMasterBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sDisableInsuranceMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Insurance Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_InsuranceMaster.list_diableButton,10);
            disableInsuranceMasterBtnEnabled = isButtonEnabled(oPage_InsuranceMaster.list_diableButton.get(0));

            m_assert.assertTrue(disableInsuranceMasterBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate activate Insurance Master Access")
    public void validatePolicy_activateInsuranceMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_InsuranceMaster=new Page_InsuranceMaster(driver);

        try {

            setPolicy(sActivateInsuranceMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Insurance Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_InsuranceMaster.list_activeButton,10);
            boolean activateInsuranceMasterBtnEnabled = isButtonEnabled(oPage_InsuranceMaster.list_activeButton.get(0));

            m_assert.assertFalse(activateInsuranceMasterBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sActivateInsuranceMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Insurance Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_InsuranceMaster.list_activeButton,10);
            activateInsuranceMasterBtnEnabled = isButtonEnabled(oPage_InsuranceMaster.list_activeButton.get(0));

            m_assert.assertTrue(activateInsuranceMasterBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

}
