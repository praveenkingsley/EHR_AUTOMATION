package tests.authorizationpolicy.ipd;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.ipd.forms.wardNote.Page_wardNoteForm;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;

public class WardNotesPolicyTest extends IPD_Policy {

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_IPD oPage_IPD;
    Page_wardNoteForm oPage_wardNoteForm;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    String sCreateWardNotesPolicyComponent = "CREATE (WARD NOTES)";
    String sViewAllWardNotesPolicyComponent = "VIEW ALL (WARD NOTES)";
    String sEditWardNotesPolicyComponent = "EDIT (WARD NOTES)";
    String sCloneWardNotesPolicyComponent = "CLONE (WARD NOTES)";


    @Test(description = "Validate Ward Notes Create access")
    public void validatePolicy_createWardNotesTemplate() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_wardNoteForm = new Page_wardNoteForm(driver);

        try {

            setPolicy(sCreateWardNotesPolicyComponent, false);
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

            boolean clickReadyForAdmission = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_readyForAdmission, 6);
            if (clickReadyForAdmission) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_readyForAdmission);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 10);
            }

            boolean admitBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 6);
            if (admitBtn) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_admitPatient);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.header_admissionForm, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_saveAdmissionForm);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
            }

            boolean wardNotesTemplateDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.button_wardNote, 10);
            m_assert.assertFalse(wardNotesTemplateDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sCreateWardNotesPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabMyQueue);
            wardNotesTemplateDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.button_wardNote, 10);
            m_assert.assertTrue(wardNotesTemplateDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Ward Notes view all access")
    public void validatePolicy_viewAllWardNotesTemplate() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_wardNoteForm = new Page_wardNoteForm(driver);

        try {

            setPolicy(sViewAllWardNotesPolicyComponent, false);
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

            boolean clickReadyForAdmission = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_readyForAdmission, 6);
            if (clickReadyForAdmission) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_readyForAdmission);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 10);
            }

            boolean admitBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 6);
            if (admitBtn) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_admitPatient);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.header_admissionForm, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_saveAdmissionForm);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.button_wardNote, 10);
            Cls_Generic_Methods.clickElement(oPage_wardNoteForm.button_wardNote);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.input_rrInWardNote, 10);
            Cls_Generic_Methods.clickElement(oPage_IPD.button_saveOnModalHeader);

            boolean viewAllWardNotesBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.button_viewAllWardNote, 6);
            m_assert.assertFalse(viewAllWardNotesBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewAllWardNotesPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabMyQueue);
            viewAllWardNotesBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.button_viewAllWardNote, 6);
            m_assert.assertTrue(viewAllWardNotesBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Ward Notes edit access")
    public void validatePolicy_editWardNotesTemplate() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_wardNoteForm = new Page_wardNoteForm(driver);

        try {

            setPolicy(sEditWardNotesPolicyComponent, false);
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

            boolean clickReadyForAdmission = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_readyForAdmission, 6);
            if (clickReadyForAdmission) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_readyForAdmission);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 10);
            }

            boolean admitBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 6);
            if (admitBtn) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_admitPatient);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.header_admissionForm, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_saveAdmissionForm);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.button_wardNote, 10);
            Cls_Generic_Methods.clickElement(oPage_wardNoteForm.button_wardNote);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.input_rrInWardNote, 10);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_wardNoteForm.input_rrInWardNote, "10");
            Cls_Generic_Methods.clickElement(oPage_IPD.button_saveOnModalHeader);

            boolean editWardNotesBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.button_editWardNote, 6);

            if(editWardNotesBtnDisplayed){
                 editWardNotesBtnDisplayed=Cls_Generic_Methods.getTextInElement(oPage_wardNoteForm.button_editWardNote.findElement(By.xpath("./parent::div/preceding-sibling::div//b"))).contains("Empty");
            }

            m_assert.assertFalse(editWardNotesBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditWardNotesPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabMyQueue);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.button_viewAllWardNote, 6);
            Cls_Generic_Methods.clickElement(oPage_wardNoteForm.button_viewAllWardNote);

            editWardNotesBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.button_editWardNote, 6);
            m_assert.assertTrue(editWardNotesBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Ward Notes clone access")
    public void validatePolicy_cloneWardNotesTemplate() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_wardNoteForm = new Page_wardNoteForm(driver);

        try {

            setPolicy(sCloneWardNotesPolicyComponent, false);
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

            boolean clickReadyForAdmission = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_readyForAdmission, 6);
            if (clickReadyForAdmission) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_readyForAdmission);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 10);
            }

            boolean admitBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 6);
            if (admitBtn) {
                Cls_Generic_Methods.clickElement(oPage_IPD.button_admitPatient);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.header_admissionForm, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_saveAdmissionForm);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.button_wardNote, 10);
            Cls_Generic_Methods.clickElement(oPage_wardNoteForm.button_wardNote);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.input_rrInWardNote, 10);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_wardNoteForm.input_rrInWardNote, "10");
            Cls_Generic_Methods.clickElement(oPage_IPD.button_saveOnModalHeader);

            boolean cloneWardNotesBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.button_cloneWardNote, 6);
            m_assert.assertFalse(cloneWardNotesBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sCloneWardNotesPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabMyQueue);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.button_viewAllWardNote, 6);
            Cls_Generic_Methods.clickElement(oPage_wardNoteForm.button_viewAllWardNote);

            cloneWardNotesBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_wardNoteForm.button_cloneWardNote, 6);
            m_assert.assertTrue(cloneWardNotesBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }
}
