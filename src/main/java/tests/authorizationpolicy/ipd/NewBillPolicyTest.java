package tests.authorizationpolicy.ipd;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.bills.Page_Bills;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;

public class NewBillPolicyTest extends IPD_Policy{

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_IPD oPage_IPD;
    Page_Bills oPage_Bills;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    String sCreateNewBillPolicyComponent = "CREATE (NEW BILL)";
    String sEditNewBillPolicyComponent = "EDIT (NEW BILL)";
    String sRefundBillPolicyComponent = "REFUND (NEW BILL)";
    String sCancelBillPolicyComponent = "CANCEL (NEW BILL)";
    String sViewNewBillPolicyComponent = "VIEW (NEW BILL)";


    @Test(description = "Validate IPD/OT New Bill Create access")
    public void validatePolicy_createNewBill() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_Bills = new Page_Bills(driver);

        try {

            setPolicy(sCreateNewBillPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            scheduleAdmission();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);

            boolean createNewBillsDisplayed =Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_cashBill, 10);
            m_assert.assertFalse(createNewBillsDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sCreateNewBillPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);

            createNewBillsDisplayed =Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_cashBill, 10);
            m_assert.assertTrue(createNewBillsDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate IPD/OT New Bill edit access")
    public void validatePolicy_editNewBill() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_Bills = new Page_Bills(driver);

        try {

            setPolicy(sEditNewBillPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            scheduleAdmission();

            createNewBill();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);

            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_editBill, 10);

            boolean editNewBillBtnEnabled =isButtonEnabled(oPage_Bills.button_editBill);
            m_assert.assertFalse(editNewBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditNewBillPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);

            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_editBill, 10);

            editNewBillBtnEnabled =isButtonEnabled(oPage_Bills.button_editBill);
            m_assert.assertTrue(editNewBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate IPD/OT New Bill refund access")
    public void validatePolicy_refundNewBill() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_Bills = new Page_Bills(driver);

        try {

            setPolicy(sRefundBillPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            scheduleAdmission();

            createNewBill();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);

            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));

            boolean refundNewBillBtnEnabled =Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_refundBill, 10);

            m_assert.assertFalse(refundNewBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sRefundBillPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);

            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));

            refundNewBillBtnEnabled =Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_refundBill, 10);
            m_assert.assertTrue(refundNewBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate IPD/OT New Bill cancel access")
    public void validatePolicy_cancelNewBill() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_Bills = new Page_Bills(driver);

        try {

            setPolicy(sCancelBillPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            scheduleAdmission();

            createNewBill();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);

            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));

            boolean cancelNewBillBtnEnabled =Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_cancelBill, 10);

            m_assert.assertFalse(cancelNewBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sCancelBillPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);

            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));
            cancelNewBillBtnEnabled =Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_cancelBill, 10);
            m_assert.assertTrue(cancelNewBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate IPD/OT View New Bill access")
    public void validatePolicy_viewNewBill() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_Bills = new Page_Bills(driver);

        try {

            setPolicy(sViewNewBillPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            scheduleAdmission();

            createNewBill();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);

            boolean viewBillDisplayed =Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);
            m_assert.assertFalse(viewBillDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewNewBillPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);

            viewBillDisplayed =Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);
            m_assert.assertTrue(viewBillDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

}
