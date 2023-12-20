package tests.authorizationpolicy.ipd;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.ipd.forms.wardNote.Page_wardNoteForm;
import pages.opd.Page_OPD;
import pages.ot.Page_OT;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;

public class OtDetailsPolicyTest extends IPD_Policy{

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_IPD oPage_IPD;
    Page_wardNoteForm oPage_wardNoteForm;
    Page_OT oPage_OT;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    String sEditOtDetailsPolicyComponent = "EDIT (OT DETAILS)";
    String sUnlinkOtDetailsPolicyComponent = "UNLINK OT (OT DETAILS)";
    String sDeleteOtDetailsPolicyComponent = "DELETE OT (OT DETAILS)";
    String sViewOtDetailsPolicyComponent = "VIEW (OT DETAILS)";


    @Test(description = "Validate OT Details Edit access")
    public void validatePolicy_editOtDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_wardNoteForm = new Page_wardNoteForm(driver);
        oPage_OT = new Page_OT(driver);


        try {

            setPolicy(sEditOtDetailsPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            selectPatientFromOt();

            boolean editOtBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_editOtDetails, 10);
            m_assert.assertFalse(editOtBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditOtDetailsPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromOt();
            editOtBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_editOtDetails, 10);
            m_assert.assertTrue(editOtBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OT Details Unlink access")
    public void validatePolicy_unlinkOtDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_wardNoteForm = new Page_wardNoteForm(driver);
        oPage_OT = new Page_OT(driver);


        try {

            setPolicy(sUnlinkOtDetailsPolicyComponent, false);
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

            boolean unlinkOtBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.link_unlinkOt, 10);
            m_assert.assertFalse(unlinkOtBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sUnlinkOtDetailsPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd();
            unlinkOtBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.link_unlinkOt, 10);
            m_assert.assertTrue(unlinkOtBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OT Details Delete access")
    public void validatePolicy_deleteOtDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_wardNoteForm = new Page_wardNoteForm(driver);
        oPage_OT = new Page_OT(driver);


        try {

            setPolicy(sDeleteOtDetailsPolicyComponent, false);
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

            boolean deleteOtBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.link_deleteOt, 10);
            m_assert.assertFalse(deleteOtBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sDeleteOtDetailsPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd();
            deleteOtBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.link_deleteOt, 10);
            m_assert.assertTrue(deleteOtBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OT Details view access")
    public void validatePolicy_viewOtDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_wardNoteForm = new Page_wardNoteForm(driver);
        oPage_OT = new Page_OT(driver);


        try {

            setPolicy(sViewOtDetailsPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                ipdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            selectPatientFromOt();

            boolean viewOtDetailsDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.text_operatingDoctor, 10);
            m_assert.assertFalse(viewOtDetailsDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewOtDetailsPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromOt();
            viewOtDetailsDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.text_operatingDoctor, 10);
            m_assert.assertTrue(viewOtDetailsDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }
}
