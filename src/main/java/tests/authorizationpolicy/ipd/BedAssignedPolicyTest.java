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
import pages.settings.organisationSettings.general.Page_OrganisationSetup;

public class BedAssignedPolicyTest extends IPD_Policy{

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_IPD oPage_IPD;
    Page_wardNoteForm oPage_wardNoteForm;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    String sAssignBedPolicyComponent = "ASSIGN BED (BED ASSIGNED)";
    String sRemoveBedPolicyComponent = "REMOVE BED (BED ASSIGNED)";


    @Test(description = "Validate Assign IPD Bed access")
    public void validatePolicy_assignBed() {
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

            setPolicy(sAssignBedPolicyComponent, false);
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

            boolean assignBedBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_assignBed, 10);
            m_assert.assertFalse(assignBedBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sAssignBedPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabMyQueue);
            assignBedBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_assignBed, 10);
            m_assert.assertTrue(assignBedBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Remove IPD Bed access")
    public void validatePolicy_removeAssignedBed() {
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

            setPolicy(sRemoveBedPolicyComponent, false);
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

            boolean removeAssignedBedBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_removeAssignBed, 10);

            if(!removeAssignedBedBtnDisplayed){
               Cls_Generic_Methods.clickElement(oPage_IPD.button_assignBed);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.header_assignBed, 15);
                Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.input_selectWard);
                Cls_Generic_Methods.selectElementByIndex(oPage_ScheduleAdmission.input_selectWard, 2);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.input_selectRoom);
                Cls_Generic_Methods.selectElementByIndex(oPage_ScheduleAdmission.input_selectRoom, 1);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_ScheduleAdmission.list_bedAssign,10);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduleAdmission.list_bedAssign.get(1));
                Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_saveBed);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
            }

            m_assert.assertFalse(removeAssignedBedBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sRemoveBedPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabMyQueue);
            removeAssignedBedBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_removeAssignBed, 10);
            m_assert.assertTrue(removeAssignedBedBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }
}
