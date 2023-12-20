package tests.authorizationpolicy.ehrSetting;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import org.openqa.selenium.WebElement;
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

public class PayerMasterPolicyTest extends EHR_Setting_Policy {

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_IPD oPage_IPD;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PayerMaster oPage_PayerMaster;
    String sViewPayerMasterPolicyComponent = "VIEW (PAYER MASTER)";
    String sAddPayerMasterPolicyComponent = "ADD PAYER MASTER (PAYER MASTER)";
    String sAllPayerClonePayerMasterPolicyComponent = "ALL PAYER CLONE (PAYER MASTER)";
    String sDisablePayerMasterPolicyComponent = "DISABLE (PAYER MASTER)";
    String sEnablePayerMasterPolicyComponent = "ENABLE (PAYER MASTER)";
    String sClonePayerMasterPolicyComponent = "CLONE (PAYER MASTER)";
    String sEditPayerMasterPolicyComponent = "EDIT (PAYER MASTER)";

    @Test(description = "Validate Payer Master View Access")
    public void validatePolicy_viewPayerMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sViewPayerMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            boolean viewPayerMasterSetting = CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
            m_assert.assertFalse(viewPayerMasterSetting, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewPayerMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            viewPayerMasterSetting = CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
            m_assert.assertTrue(viewPayerMasterSetting, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Add Payer Master Access")
    public void validatePolicy_addPayerMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_PayerMaster = new Page_PayerMaster(driver);

        try {

            setPolicy(sAddPayerMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_addPayerMaster, 10);
            boolean addPayerMasterEnabled = isButtonEnabled(oPage_PayerMaster.button_addPayerMaster);

            m_assert.assertFalse(addPayerMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sAddPayerMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_addPayerMaster, 10);
            addPayerMasterEnabled = isButtonEnabled(oPage_PayerMaster.button_addPayerMaster);

            m_assert.assertTrue(addPayerMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Add Payer Clone Access")
    public void validatePolicy_allPayerClone() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_PayerMaster = new Page_PayerMaster(driver);
        try {

            setPolicy(sAllPayerClonePayerMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
            addPayerMaster();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_addPayerMaster, 10);
            boolean clonePayerMasterEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_cloneAllAddPayer, 2);

            m_assert.assertFalse(clonePayerMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sAllPayerClonePayerMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_addPayerMaster, 10);
            clonePayerMasterEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_cloneAllAddPayer, 2);

            m_assert.assertTrue(clonePayerMasterEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Payer master disable access")
    public void validatePolicy_disablePayerMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_PayerMaster = new Page_PayerMaster(driver);
        try {

            setPolicy(sDisablePayerMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
            addPayerMaster();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_addPayerMaster, 10);
            Cls_Generic_Methods.clickElement(oPage_PayerMaster.list_linkPayerLink.get(0));

            boolean disablePayerBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_disablePayer, 10);

            m_assert.assertFalse(disablePayerBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sDisablePayerMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_addPayerMaster, 10);
            Cls_Generic_Methods.clickElement(oPage_PayerMaster.list_linkPayerLink.get(0));

            disablePayerBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_disablePayer, 10);
            m_assert.assertTrue(disablePayerBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Payer master edit access")
    public void validatePolicy_editPayerMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_PayerMaster = new Page_PayerMaster(driver);
        try {

            setPolicy(sEditPayerMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
            addPayerMaster();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_addPayerMaster, 10);
            Cls_Generic_Methods.clickElement(oPage_PayerMaster.list_linkPayerLink.get(0));

            boolean editPayerBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_editPayer, 10);

            m_assert.assertFalse(editPayerBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditPayerMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_addPayerMaster, 10);
            Cls_Generic_Methods.clickElement(oPage_PayerMaster.list_linkPayerLink.get(0));

            editPayerBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_editPayer, 10);
            m_assert.assertTrue(editPayerBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Payer master enable access")
    public void validatePolicy_enablePayerMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_PayerMaster = new Page_PayerMaster(driver);
        try {

            setPolicy(sEnablePayerMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
            addPayerMaster();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_addPayerMaster, 10);
            Cls_Generic_Methods.clickElement(oPage_PayerMaster.list_linkPayerLink.get(getFacilityIndex()));
            boolean enablePayerBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_enablePayer, 10);

            m_assert.assertFalse(enablePayerBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEnablePayerMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_addPayerMaster, 10);

            Cls_Generic_Methods.clickElement(oPage_PayerMaster.list_linkPayerLink.get(getFacilityIndex()));

            enablePayerBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_enablePayer, 10);
            m_assert.assertTrue(enablePayerBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Payer master clone access")
    public void validatePolicy_clonePayerMaster() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_PayerMaster = new Page_PayerMaster(driver);
        try {

            setPolicy(sClonePayerMasterPolicyComponent, false);
            adminTab = driver.getWindowHandle();

            //If Policy User and Admin User is same , then below method ll be executed
            openNewTab();

            switchToPolicyUser();
            openOrganisationSetting();

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
            addPayerMaster();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_addPayerMaster, 10);
            Cls_Generic_Methods.clickElement(oPage_PayerMaster.list_linkPayerLink.get(0));
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_editPayer,10);
            boolean clonePayerBtnEnabled =Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_clonePayer, 10);

            m_assert.assertFalse(clonePayerBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sClonePayerMasterPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_addPayerMaster, 10);
            Cls_Generic_Methods.clickElement(oPage_PayerMaster.list_linkPayerLink.get(0));

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_editPayer,10);
            clonePayerBtnEnabled =Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_clonePayer, 10);
            m_assert.assertTrue(clonePayerBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    public void addPayerMaster() {
        String sPayerName = "AutoTestPayer";

        try {

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_addPayerMaster, 20);
            boolean payerNotAdded = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.text_noPayerAdded, 2);

            if (payerNotAdded) {
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_PayerMaster.button_addPayerMaster), "Add Payer master button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.label_addPayerForm, 20);
                Cls_Generic_Methods.customWait();

                Cls_Generic_Methods.selectElementByIndex(oPage_PayerMaster.select_facilityAddPayerForm, 1);
                Cls_Generic_Methods.selectElementByIndex(oPage_PayerMaster.select_insuranceAddPayerForm, 1);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.selectElementByIndex(oPage_PayerMaster.select_payerTypeMasterAddPayerForm, 1);

                Cls_Generic_Methods.sendKeysIntoElement(oPage_PayerMaster.input_displayNameAddPayerForm, sPayerName);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_PayerMaster.button_savePayerAddPayerForm);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PayerMaster.button_addPayerMaster, 20);
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to add new payer master " + e);
            e.printStackTrace();
        }

    }

    private int getFacilityIndex() {
        int index = 0;

        try {
            int no = 0;
            for (WebElement ele : oPage_PayerMaster.list_textFacilityName) {
                String facilityName = Cls_Generic_Methods.getTextInElement(ele);
                if (facilityName.equals("TESTING_FACILITY")) {
                    index = no;
                    break;
                }
                no++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return index;
    }
}
