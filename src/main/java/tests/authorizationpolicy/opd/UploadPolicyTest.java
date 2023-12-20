package tests.authorizationpolicy.opd;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.store.PharmacyStore.Transaction.Page_Purchase;

public class UploadPolicyTest extends OPD_Policy {

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_Purchase oPage_Purchase;
    String sUploadPolicyComponent = "UPLOAD (UPLOAD)";
    String sEditUploadPolicyComponent = "EDIT (UPLOAD)";
    String sDeleteUploadPolicyComponent = "DELETE (UPLOAD)";

    @Test(description = "Validate OPD Patient records upload access")
    public void validatePolicy_upload() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            setPolicy(sUploadPolicyComponent, false);
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

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_uploadPapers, 10);
            boolean uploadPapersBtnEnabled = isButtonEnabled(oPage_PatientAppointmentDetails.button_uploadPapers);
            m_assert.assertFalse(uploadPapersBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sUploadPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_uploadPapers, 10);
            uploadPapersBtnEnabled = isButtonEnabled(oPage_PatientAppointmentDetails.button_uploadPapers);

            m_assert.assertTrue(uploadPapersBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Patient records upload edit access")
    public void validatePolicy_editUpload() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Purchase = new Page_Purchase(driver);

        try {

            setPolicy(sEditUploadPolicyComponent, false);
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

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_uploadPapers, 10);
            Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_uploadPapers);

            for (String id : driver.getWindowHandles()) {
                Cls_Generic_Methods.switchToWindowHandle(id);
                if (Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_addFilesVendorInvoicePurchaseBill, 3)) {
                    break;
                }
            }

            Cls_Generic_Methods.clickElement(driver, oPage_Purchase.button_addFilesVendorInvoicePurchaseBill);
            Cls_Generic_Methods.customWait();
            boolean uploadStatus = CommonActions.uploadFileUsingRobotClass(System.getProperty("user.dir") + "\\test-output\\passed.png");
            Cls_Generic_Methods.customWait();

            if (uploadStatus) {
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.button_uploadFilesVendorInvoicePurchaseBill);
                Cls_Generic_Methods.customWait();
                driver.switchTo().alert().dismiss();
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            }

            switchToPolicyUser();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_Summary, 10);
            Cls_Generic_Methods.clickElement(oPage_OPD.button_Summary);
            boolean editUploadBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_editUploadSummary, 10);

            m_assert.assertFalse(editUploadBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditUploadPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            editUploadBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_editUploadSummary, 10);

            m_assert.assertTrue(editUploadBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Patient records upload delete access")
    public void validatePolicy_deleteUpload() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Purchase = new Page_Purchase(driver);

        try {

            setPolicy(sDeleteUploadPolicyComponent, false);
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

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_uploadPapers, 10);
            Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_uploadPapers);

            for (String id : driver.getWindowHandles()) {
                Cls_Generic_Methods.switchToWindowHandle(id);
                if (Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_addFilesVendorInvoicePurchaseBill, 3)) {
                    break;
                }
            }

            Cls_Generic_Methods.clickElement(driver, oPage_Purchase.button_addFilesVendorInvoicePurchaseBill);
            Cls_Generic_Methods.customWait();
            boolean uploadStatus = CommonActions.uploadFileUsingRobotClass(System.getProperty("user.dir") + "\\test-output\\passed.png");
            Cls_Generic_Methods.customWait();

            if (uploadStatus) {
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.button_uploadFilesVendorInvoicePurchaseBill);
                Cls_Generic_Methods.customWait();
                driver.switchTo().alert().dismiss();
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            }

            switchToPolicyUser();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_Summary, 10);
            Cls_Generic_Methods.clickElement(oPage_OPD.button_Summary);
            boolean deleteUploadBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_deleteUploadSummary, 10);

            m_assert.assertFalse(deleteUploadBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sDeleteUploadPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            deleteUploadBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_deleteUploadSummary, 10);

            m_assert.assertTrue(deleteUploadBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }
}
