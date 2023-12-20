package tests.authorizationpolicy.opd;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.bills.Page_Bills;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;

public class SettleBillsPolicyTest extends OPD_Policy {
    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_Bills oPage_Bills;
    String sViewSettleBillsPolicyComponent = "VIEW (SETTLE BILLS)";
    String sSettlePolicyComponent = "SETTLE (SETTLE BILLS)";

    @Test(description = "Validate OPD Credit Bill dashboard view access")
    public void validatePolicy_viewSettleBills() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills = new Page_Bills(driver);

        try {

            setPolicy(sViewSettleBillsPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_reports, 10);
            Cls_Generic_Methods.clickElement(oPage_Navbar.button_reports);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_settleBills, 10);

            boolean settleBillBtnEnabled = isButtonEnabled(oPage_Bills.button_settleBills);
            m_assert.assertFalse(settleBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewSettleBillsPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_reports, 10);
            Cls_Generic_Methods.clickElement(oPage_Navbar.button_reports);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_settleBills, 10);

            settleBillBtnEnabled = isButtonEnabled(oPage_Bills.button_settleBills);
            m_assert.assertTrue(settleBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Credit Bill Settle access")
    public void validatePolicy_settleBills() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills = new Page_Bills(driver);

        try {

            setPolicy(sSettlePolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_reports, 10);
            Cls_Generic_Methods.clickElement(oPage_Navbar.button_reports);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_settleBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_settleBills);
            boolean settleBillBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.link_settleAmountPending, 10);
            m_assert.assertFalse(settleBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sSettlePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_reports, 10);
            Cls_Generic_Methods.clickElement(oPage_Navbar.button_reports);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_settleBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_settleBills);

            settleBillBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.link_settleAmountPending, 10);
            m_assert.assertTrue(settleBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }
}
