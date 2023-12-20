package tests.authorizationpolicy.ipd;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.ipd.forms.intraOperative.Page_PreAnaesthesiaTemplate;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;

public class IntraOperativePolicyTest extends IPD_Policy{

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_IPD oPage_IPD;
    Page_PreAnaesthesiaTemplate oPage_PreAnaesthesiaTemplate;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    String sCreateIntraOperativePolicyComponent = "CREATE (INTRA-OPERATIVE)";
    String sEditIntraOperativePolicyComponent = "EDIT (INTRA-OPERATIVE)";
    String sViewIntraOperativePolicyComponent = "VIEW (INTRA-OPERATIVE)";


    @Test(description = "Validate Intra Operative template Create access")
    public void validatePolicy_createIntraOperativeTemplate() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sCreateIntraOperativePolicyComponent, false);
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

            boolean intraOperativeTemplateDisplayed = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_IPD.list_buttonIntraOperativeTemplates, 10);
            m_assert.assertFalse(intraOperativeTemplateDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sCreateIntraOperativePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabMyQueue);
            intraOperativeTemplateDisplayed = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_IPD.list_buttonIntraOperativeTemplates, 10);
            m_assert.assertTrue(intraOperativeTemplateDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Intra Operative edit access")
    public void validatePolicy_editIntraOperativeTemplate() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_PreAnaesthesiaTemplate = new Page_PreAnaesthesiaTemplate(driver);

        try {

            setPolicy(sEditIntraOperativePolicyComponent, false);
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

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_IPD.list_buttonIntraOperativeTemplates, 10);
            Cls_Generic_Methods.clickElement(oPage_PreAnaesthesiaTemplate.button_PreAnaesthesiaChecklistTemplateInIPD);

            boolean intraOperativeCreated =Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAnaesthesiaTemplate.input_extraInformationToDoctor, 16);

            if(intraOperativeCreated){
                Cls_Generic_Methods.clickElement(oPage_IPD.button_saveOnModalHeader);
            }

            boolean editIntraOperative = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.edit_template, 6);
            m_assert.assertFalse(editIntraOperative, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditIntraOperativePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabMyQueue);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_IPD.list_buttonIntraOperativeTemplates, 10);
            Cls_Generic_Methods.clickElement(oPage_PreAnaesthesiaTemplate.button_PreAnaesthesiaChecklistTemplateInIPD);

            editIntraOperative = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.edit_template, 6);
            m_assert.assertTrue(editIntraOperative, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Intra Operative view access")
    public void validatePolicy_viewIntraOperativeTemplate() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_PreAnaesthesiaTemplate=new Page_PreAnaesthesiaTemplate(driver);

        try {

            setPolicy(sViewIntraOperativePolicyComponent, false);
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

            boolean viewIntraOperativeTemplateDisplayed =Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAnaesthesiaTemplate.button_PreAnaesthesiaChecklistTemplateInIPD, 10);

            if(viewIntraOperativeTemplateDisplayed){
                Cls_Generic_Methods.clickElement(oPage_PreAnaesthesiaTemplate.button_PreAnaesthesiaChecklistTemplateInIPD);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAnaesthesiaTemplate.input_extraInformationToDoctor, 6);
                Cls_Generic_Methods.clickElement(oPage_IPD.button_saveOnModalHeader);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_closeForm,7);
                Cls_Generic_Methods.clickElement(oPage_IPD.button_closeForm);
            }

            viewIntraOperativeTemplateDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAnaesthesiaTemplate.button_PreAnaesthesiaChecklistTemplateInIPD, 10);
            m_assert.assertFalse(viewIntraOperativeTemplateDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewIntraOperativePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabMyQueue);
            viewIntraOperativeTemplateDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PreAnaesthesiaTemplate.button_PreAnaesthesiaChecklistTemplateInIPD, 10);
            m_assert.assertTrue(viewIntraOperativeTemplateDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }


}
