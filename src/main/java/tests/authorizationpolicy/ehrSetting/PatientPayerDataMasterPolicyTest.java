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
import pages.settings.organisationSettings.general.Page_PatientPayerDataMaster;
import pages.settings.organisationSettings.general.Page_PayerMaster;

public class PatientPayerDataMasterPolicyTest extends EHR_Setting_Policy{

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_IPD oPage_IPD;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PayerMaster oPage_PayerMaster;
    Page_PatientPayerDataMaster oPage_PatientPayerDataMaster;
    String sViewPatientPayerDataMasterPolicyComponent = "VIEW (PATIENT PAYER DATA MASTER)";
    String sAddPatientPayerDataMasterPolicyComponent = "ADD PATIENT PAYER DATA (PATIENT PAYER DATA MASTER)";
    String sEditPatientPayerDataMasterPolicyComponent = "EDIT (PATIENT PAYER DATA MASTER)";
    String sDisablePatientPayerDataMasterPolicyComponent = "DISABLE (PATIENT PAYER DATA MASTER)";
    String sActivatePatientPayerDataMasterPolicyComponent = "ACTIVATE (PATIENT PAYER DATA MASTER)";


    @Test(description = "Validate Patient Payer Data Master View Access")
    public void validatePolicy_viewPatientPayerDataMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);

        try {

            setPolicy(sViewPatientPayerDataMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            boolean viewPatientPayerDataMasterSetting = CommonActions.selectOptionFromLeftInSettingsPanel("General", "Patient Payer Data Master");
            m_assert.assertFalse(viewPatientPayerDataMasterSetting, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewPatientPayerDataMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            viewPatientPayerDataMasterSetting = CommonActions.selectOptionFromLeftInSettingsPanel("General", "Patient Payer Data Master");
            m_assert.assertTrue(viewPatientPayerDataMasterSetting, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Add Patient Payer Data Master Access")
    public void validatePolicy_addPatientPayerDataMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_PatientPayerDataMaster=new Page_PatientPayerDataMaster(driver);

        try {

            setPolicy(sAddPatientPayerDataMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Patient Payer Data Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientPayerDataMaster.button_addPatientPayerData,10);
            boolean addDispensaryMasterEnabled = isButtonEnabled(oPage_PatientPayerDataMaster.button_addPatientPayerData);

            m_assert.assertFalse(addDispensaryMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sAddPatientPayerDataMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Patient Payer Data Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientPayerDataMaster.button_addPatientPayerData,10);
            addDispensaryMasterEnabled = isButtonEnabled(oPage_PatientPayerDataMaster.button_addPatientPayerData);

            m_assert.assertTrue(addDispensaryMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Edit Patient Payer Data Master Access")
    public void validatePolicy_editPatientPayerDataMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_PatientPayerDataMaster=new Page_PatientPayerDataMaster(driver);

        try {

            setPolicy(sEditPatientPayerDataMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Patient Payer Data Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientPayerDataMaster.button_addPatientPayerData,10);
            boolean editDispensaryMasterEnabled = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PatientPayerDataMaster.list_editButton,10);

            m_assert.assertFalse(editDispensaryMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditPatientPayerDataMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Patient Payer Data Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientPayerDataMaster.button_addPatientPayerData,10);
            editDispensaryMasterEnabled = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PatientPayerDataMaster.list_editButton,10);

            m_assert.assertTrue(editDispensaryMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Disable Patient Payer Data Master Access")
    public void validatePolicy_disablePatientPayerDataMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_PatientPayerDataMaster=new Page_PatientPayerDataMaster(driver);

        try {

            setPolicy(sDisablePatientPayerDataMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Patient Payer Data Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PatientPayerDataMaster.list_disableButton,10);
            boolean disableDispensaryMasterEnabled =isButtonEnabled(oPage_PatientPayerDataMaster.list_disableButton.get(0));

            m_assert.assertFalse(disableDispensaryMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sDisablePatientPayerDataMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Patient Payer Data Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PatientPayerDataMaster.list_disableButton,10);
            disableDispensaryMasterEnabled =isButtonEnabled(oPage_PatientPayerDataMaster.list_disableButton.get(0));

            m_assert.assertTrue(disableDispensaryMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Activate Patient Payer Data Master Access")
    public void validatePolicy_activatePatientPayerDataMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD=new Page_IPD(driver);
        oPage_PayerMaster=new Page_PayerMaster(driver);
        oPage_PatientPayerDataMaster=new Page_PatientPayerDataMaster(driver);

        try {

            setPolicy(sActivatePatientPayerDataMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Patient Payer Data Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PatientPayerDataMaster.list_activeButton,10);
            boolean activateDispensaryMasterEnabled =isButtonEnabled(oPage_PatientPayerDataMaster.list_activeButton.get(0));

            m_assert.assertFalse(activateDispensaryMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sActivatePatientPayerDataMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Patient Payer Data Master");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PatientPayerDataMaster.list_activeButton,10);
            activateDispensaryMasterEnabled =isButtonEnabled(oPage_PatientPayerDataMaster.list_activeButton.get(0));

            m_assert.assertTrue(activateDispensaryMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

}
