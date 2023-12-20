package tests.authorizationpolicy.opd;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;

public class VisitRegistrationPolicyTest extends OPD_Policy {
    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;

    String sAddOpPolicyComponent = "ADD OP (VISIT REGISTRATION)";
    String sAddAppointmentPolicyComponent = "ADD APPOINTMENT (VISIT REGISTRATION)";
    String sRegisterPatientPolicyComponent ="REGISTER PATIENT (PATIENT REGISTRATION)";



    @Test( description = "Validate Add OP (VISIT REGISTRATION) policy")
    public void validatePolicy_addOp() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sAddOpPolicyComponent,false);
            boolean timeSlotEnabled = enableOrDisableTimeSlot(true);
            adminTab=driver.getWindowHandle();

            if(iDriver==null){
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab= driver.getWindowHandle();
            }

            switchToPolicyUser();
            CommonActions.loginFunctionality(sPolicyUser);

            if (timeSlotEnabled) {
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForPageLoad(driver,20);
                CommonActions.selectDepartmentOnApp("OPD");

                boolean addOpButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_addOP, 7);
                m_assert.assertFalse(addOpButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

                Cls_Generic_Methods.customWait();

                switchToAdmin();
                setPolicy(sAddOpPolicyComponent,true);

                switchToPolicyUser();
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForPageLoad(driver, 10);

                addOpButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_addOP, 10);
                m_assert.assertTrue(addOpButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

            } else {
                m_assert.assertFalse("Unable to Enable/Disable Time Slot");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate add op policy ->" + e);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        }

        Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
    }

    @Test( description = "Validate Add Appointment (VISIT REGISTRATION) policy")
    public void validatePolicy_addAppointment() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {
            setPolicy(sAddAppointmentPolicyComponent,false);
            boolean timeSlotEnabled = enableOrDisableTimeSlot(true);

            adminTab=driver.getWindowHandle();

            if(iDriver==null){
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab= driver.getWindowHandle();
            }

            switchToPolicyUser();
            CommonActions.loginFunctionality(sPolicyUser);

            if (timeSlotEnabled) {
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForPageLoad(driver,20);
                CommonActions.selectDepartmentOnApp("OPD");

                boolean addAppointmentButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_AddAppointment, 7);
                m_assert.assertFalse(addAppointmentButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

                Cls_Generic_Methods.customWait();

                switchToAdmin();
                setPolicy(sAddAppointmentPolicyComponent,true);

                switchToPolicyUser();
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForPageLoad(driver, 10);

                addAppointmentButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_AddAppointment, 10);
                m_assert.assertTrue(addAppointmentButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

            } else {
                m_assert.assertFalse("Unable to Enable/Disable Time Slot");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate add appointment policy ->" + e);
        }

        Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

    }

    @Test( description = "Validate Register Patient (PATIENT REGISTRATION) policy")
    public void validatePolicy_registerPatient() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sRegisterPatientPolicyComponent,false);
            boolean timeSlotEnabled = enableOrDisableTimeSlot(true);

            adminTab=driver.getWindowHandle();

            if(iDriver==null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            CommonActions.loginFunctionality(sPolicyUser);

            if (timeSlotEnabled) {
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForPageLoad(driver,20);
                CommonActions.selectDepartmentOnApp("OPD");

                boolean registerPatientButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_registerPatient, 7);
                m_assert.assertFalse(registerPatientButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

                Cls_Generic_Methods.customWait();

                switchToAdmin();
                setPolicy(sRegisterPatientPolicyComponent,true);

                switchToPolicyUser();
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForPageLoad(driver, 10);

                registerPatientButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_registerPatient, 10);
                m_assert.assertTrue(registerPatientButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

            } else {
                m_assert.assertFalse("Unable to Enable/Disable Time Slot");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate register patient policy ->" + e);
        }
        Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
    }




}
