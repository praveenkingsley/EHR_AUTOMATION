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

public class NewDraftBillPolicyTest extends OPD_Policy{

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_Bills oPage_Bills;
    String sCreateNewDraftBillPolicyComponent = "CREATE (NEW DRAFT BILL)";
    String sEditDraftBillsPolicyComponent = "EDIT DRAFT BILL (NEW DRAFT BILL)";
    String sConvertToFinalPolicyComponent = "CONVERT TO FINAL (NEW DRAFT BILL)";
    String sRemoveDraftBillPolicyComponent = "REMOVE (NEW DRAFT BILL)";


    @Test(description = "Validate OPD Draft Bill create access")
    public void validatePolicy_createNewDraftBill() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills=new Page_Bills(driver);

        try {

            setPolicy(sCreateNewDraftBillPolicyComponent, false);
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
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_newDraftBill,10);

            boolean createNewBillBtnEnabled =isButtonEnabled(oPage_Bills.button_newDraftBill);
            m_assert.assertFalse(createNewBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sCreateNewDraftBillPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_newDraftBill,10);

            createNewBillBtnEnabled = isButtonEnabled(oPage_Bills.button_newDraftBill);
            m_assert.assertTrue(createNewBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Draft Bill Edit access")
    public void validatePolicy_editNewDraftBill() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills=new Page_Bills(driver);

        try {

            setPolicy(sEditDraftBillsPolicyComponent, false);
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

            if(isButtonEnabled(oPage_Bills.button_newDraftBill)){
                Cls_Generic_Methods.clickElement(oPage_Bills.button_newDraftBill);

                CreateBillsTest createBill=new CreateBillsTest();
                createBill.createDraftBill=true;
                createBill.fillBillForm();

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);
            }

            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_editDraftBill, 10);

            boolean editNewBillBtnEnabled =isButtonEnabled(oPage_Bills.button_editDraftBill);
            m_assert.assertFalse(editNewBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditDraftBillsPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);
            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_editDraftBill, 10);

            editNewBillBtnEnabled =isButtonEnabled(oPage_Bills.button_editDraftBill);
            m_assert.assertTrue(editNewBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Draft Bill Convert to final bill access")
    public void validatePolicy_convertToFinalBill() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills=new Page_Bills(driver);

        try {

            setPolicy(sConvertToFinalPolicyComponent, false);
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

            if(isButtonEnabled(oPage_Bills.button_newDraftBill)){
                Cls_Generic_Methods.clickElement(oPage_Bills.button_newDraftBill);

                CreateBillsTest createBill=new CreateBillsTest();
                createBill.createDraftBill=true;
                createBill.fillBillForm();

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);
            }

            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));

            boolean convertToFinalBillBtnEnabled =Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_convertToFinalBill, 10);

            m_assert.assertFalse(convertToFinalBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sConvertToFinalPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);
            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));

            convertToFinalBillBtnEnabled =Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_convertToFinalBill, 10);

            m_assert.assertTrue(convertToFinalBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Draft Bill Remove Draft bill access")
    public void validatePolicy_removeDraftBill() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills=new Page_Bills(driver);

        try {

            setPolicy(sRemoveDraftBillPolicyComponent, false);
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

            if(isButtonEnabled(oPage_Bills.button_newDraftBill)){
                Cls_Generic_Methods.clickElement(oPage_Bills.button_newDraftBill);

                CreateBillsTest createBill=new CreateBillsTest();
                createBill.createDraftBill=true;
                createBill.fillBillForm();

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);
            }

            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_removeDraftBill, 10);
            boolean removeBtnEnabled =isButtonEnabled(oPage_Bills.button_removeDraftBill);

            m_assert.assertFalse(removeBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sRemoveDraftBillPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills,10);
            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_removeDraftBill, 10);
            removeBtnEnabled =isButtonEnabled(oPage_Bills.button_removeDraftBill);

            m_assert.assertTrue(removeBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }
}
