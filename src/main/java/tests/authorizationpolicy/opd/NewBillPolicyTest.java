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
import tests.departments.opd.CreateBillsTest;

public class NewBillPolicyTest extends OPD_Policy{

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_Bills oPage_Bills;
    String sCreateNewBillPolicyComponent = "CREATE (NEW BILL)";
    String sEditNewBillsPolicyComponent = "EDIT (NEW BILL)";
    String sRefundNewBillPolicyComponent = "REFUND (NEW BILL)";
    String sCancelNewBillPolicyComponent = "CANCEL (NEW BILL)";



    @Test(description = "Validate OPD cash/credit Bill create access")
    public void validatePolicy_createNewBill() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills=new Page_Bills(driver);

        try {

            setPolicy(sCreateNewBillPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            createPatient();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_newBill,10);

            boolean createNewBillBtnEnabled =isButtonEnabled(oPage_Bills.button_newBill);
            m_assert.assertFalse(createNewBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sCreateNewBillPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_newBill,10);

            createNewBillBtnEnabled = isButtonEnabled(oPage_Bills.button_newBill);
            m_assert.assertTrue(createNewBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Bill Edit access")
    public void validatePolicy_editNewBill() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills=new Page_Bills(driver);

        try {

            setPolicy(sEditNewBillsPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            createPatient();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_newBill,10);

            if(oPage_Bills.list_previousBills.size()==0){
                Cls_Generic_Methods.clickElement(oPage_Bills.button_newBill);
                new CreateBillsTest().fillBillForm();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);
            }

            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_editBill, 10);

            boolean editNewBillBtnEnabled =isButtonEnabled(oPage_Bills.button_editBill);
            m_assert.assertFalse(editNewBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditNewBillsPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

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

    @Test(description = "Validate OPD Bill Refund access")
    public void validatePolicy_refundNewBill() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills=new Page_Bills(driver);

        try {

            setPolicy(sRefundNewBillPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            createPatient();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_newBill,10);

            if(oPage_Bills.list_previousBills.size()==0){
                Cls_Generic_Methods.clickElement(oPage_Bills.button_newBill);
                new CreateBillsTest().fillBillForm();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);
            }

            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));

            boolean refundBillBtnEnabled =Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_refundBill, 10);

            m_assert.assertFalse(refundBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sRefundNewBillPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);
            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));

            refundBillBtnEnabled =Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_refundBill, 10);
            m_assert.assertTrue(refundBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Bill Cancellation access")
    public void validatePolicy_cancelNewBill() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills=new Page_Bills(driver);

        try {

            setPolicy(sCancelNewBillPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            createPatient();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_newBill,10);

            if(oPage_Bills.list_previousBills.size()==0){
                Cls_Generic_Methods.clickElement(oPage_Bills.button_newBill);
                new CreateBillsTest().fillBillForm();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);
            }

            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));

            boolean cancelBillBtnEnabled =Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_cancelBill, 10);

            m_assert.assertFalse(cancelBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sCancelNewBillPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);
            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));

            cancelBillBtnEnabled =Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_cancelBill, 10);
            m_assert.assertTrue(cancelBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }


}
