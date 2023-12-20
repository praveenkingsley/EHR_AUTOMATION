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

public class NewDraftBillPolicyTest extends IPD_Policy {

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_IPD oPage_IPD;
    Page_Bills oPage_Bills;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    String sCreateNewDraftBillPolicyComponent = "CREATE (NEW DRAFT BILL)";
    String sEditDraftBillPolicyComponent = "EDIT DRAFT BILL (NEW DRAFT BILL)";
    String sConvertToFinalBillPolicyComponent = "CONVERT TO FINAL BILL (NEW DRAFT BILL)";
    String sRemoveDraftBillPolicyComponent = "REMOVE (NEW DRAFT BILL)";
    String sViewNewDraftBillPolicyComponent = "VIEW (NEW DRAFT BILL)";

    String sContactGroup = "Insurance";
    String sPayer = "HG Insurance Ltd";

    @Test(description = "Validate IPD/OT New Draft Bill Create access")
    public void validatePolicy_createNewDraftBill() {
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

            setPolicy(sCreateNewDraftBillPolicyComponent, false);
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

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_editInsuranceDetailsButton, 10);

            Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_editInsuranceDetailsButton);
            Cls_Generic_Methods.customWait(4);
            Cls_Generic_Methods.selectElementByVisibleText(oPage_IPD.select_payerFieldUnderInsuranceDetailsForm, sContactGroup);
            Cls_Generic_Methods.customWait(4);
            Cls_Generic_Methods.selectElementByVisibleText(oPage_IPD.select_contactFieldUnderInsuranceDetailsForm, sPayer);
            Cls_Generic_Methods.customWait(4);
            Cls_Generic_Methods.clickElement(oPage_IPD.button_savePatientInsuranceForm);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);


            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);

            boolean createNewDraftBillsDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_newDraftBill, 10);
            m_assert.assertFalse(createNewDraftBillsDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sCreateNewDraftBillPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);

            createNewDraftBillsDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_newDraftBill, 10);
            m_assert.assertTrue(createNewDraftBillsDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate IPD/OT Draft Bill Edit access")
    public void validatePolicy_editDraftBill() {
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

            setPolicy(sEditDraftBillPolicyComponent, false);
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

            boolean cashlessHospitalizationAdded = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_draftBill, 5);
            boolean claimFormDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_printClaimForms, 5);

            if (!cashlessHospitalizationAdded && !claimFormDisplayed) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_editInsuranceDetailsButton, 10);

                Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_editInsuranceDetailsButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_IPD.select_payerFieldUnderInsuranceDetailsForm, sContactGroup);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_IPD.select_contactFieldUnderInsuranceDetailsForm, sPayer);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(oPage_IPD.button_savePatientInsuranceForm);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);

            } else {
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            }

            if (!claimFormDisplayed) {
                createNewBill(true);
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills, 10);

            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));

            boolean editNewDraftBillBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_editDraftBill, 10);
            m_assert.assertFalse(editNewDraftBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditDraftBillPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills, 10);

            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));

            editNewDraftBillBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_editDraftBill, 10);
            m_assert.assertTrue(editNewDraftBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate IPD/OT Draft Bill Convert to Final Bill access")
    public void validatePolicy_convertToFinalBill() {
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

            setPolicy(sConvertToFinalBillPolicyComponent, false);
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

            boolean cashlessHospitalizationAdded = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_draftBill, 5);
            boolean claimFormDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_printClaimForms, 5);

            if (!cashlessHospitalizationAdded && !claimFormDisplayed) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_editInsuranceDetailsButton, 10);

                Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_editInsuranceDetailsButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_IPD.select_payerFieldUnderInsuranceDetailsForm, sContactGroup);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_IPD.select_contactFieldUnderInsuranceDetailsForm, sPayer);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(oPage_IPD.button_savePatientInsuranceForm);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);

            } else {
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            }

            if (!claimFormDisplayed) {
                createNewBill(true);
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills, 10);

            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));

            boolean convertToFinalBillBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_convertToFinalBill, 10);
            m_assert.assertFalse(convertToFinalBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sConvertToFinalBillPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills, 10);

            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));

            convertToFinalBillBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_convertToFinalBill, 10);
            m_assert.assertTrue(convertToFinalBillBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate IPD/OT Draft Bill Remove access")
    public void validatePolicy_removeDraftBill() {
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

            setPolicy(sRemoveDraftBillPolicyComponent, false);
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

            boolean cashlessHospitalizationAdded = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_draftBill, 5);
            boolean claimFormDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_printClaimForms, 5);

            if (!cashlessHospitalizationAdded && !claimFormDisplayed) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_editInsuranceDetailsButton, 10);

                Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_editInsuranceDetailsButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_IPD.select_payerFieldUnderInsuranceDetailsForm, sContactGroup);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_IPD.select_contactFieldUnderInsuranceDetailsForm, sPayer);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(oPage_IPD.button_savePatientInsuranceForm);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);

            } else {
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            }

            if (!claimFormDisplayed) {
                createNewBill(true);
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills, 10);

            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));

            boolean removeBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_removeDraftBill, 10);
            m_assert.assertFalse(removeBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sRemoveDraftBillPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills, 10);

            Cls_Generic_Methods.clickElement(oPage_Bills.list_previousBills.get(0));

            removeBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_removeDraftBill, 10);
            m_assert.assertTrue(removeBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate IPD/OT View Draft Bill access")
    public void validatePolicy_viewDraftBill() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_Bills = new Page_Bills(driver);
        String billType="";


        try {

            setPolicy(sViewNewDraftBillPolicyComponent, false);
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

            boolean cashlessHospitalizationAdded = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_draftBill, 5);
            boolean claimFormDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_printClaimForms, 5);

            if (!cashlessHospitalizationAdded && !claimFormDisplayed) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_editInsuranceDetailsButton, 10);

                Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_editInsuranceDetailsButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_IPD.select_payerFieldUnderInsuranceDetailsForm, sContactGroup);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_IPD.select_contactFieldUnderInsuranceDetailsForm, sPayer);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(oPage_IPD.button_savePatientInsuranceForm);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);

            } else {
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            }

            if (!claimFormDisplayed) {
                createNewBill(true);
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills, 10);

            billType=Cls_Generic_Methods.getElementAttribute(oPage_Bills.list_previousBills.get(0),"class");

            boolean draftBillDisplayed = billType.contains("draft");
            m_assert.assertFalse(draftBillDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewNewDraftBillPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Bills.list_previousBills, 10);

            billType=Cls_Generic_Methods.getElementAttribute(oPage_Bills.list_previousBills.get(0),"class");

            draftBillDisplayed = billType.contains("draft");
            m_assert.assertTrue(draftBillDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }
}
