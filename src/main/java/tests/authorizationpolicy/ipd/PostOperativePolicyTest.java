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
import pages.ipd.forms.postOperative.Page_AldreteScoreTemplate;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;

public class PostOperativePolicyTest extends IPD_Policy{

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_IPD oPage_IPD;
    Page_PreAnaesthesiaTemplate oPage_PreAnaesthesiaTemplate;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_AldreteScoreTemplate oPage_AldreteScoreTemplate;
    String sCreatePostOperativePolicyComponent = "CREATE (POST-OPERATIVE)";
    String sEditPostOperativePolicyComponent = "EDIT (POST-OPERATIVE)";
    String sViewPostOperativePolicyComponent = "VIEW (POST-OPERATIVE)";


    @Test(description = "Validate Post Operative template Create access")
    public void validatePolicy_createPostOperativeTemplate() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        try {

            setPolicy(sCreatePostOperativePolicyComponent, false);
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

            boolean postOperativeTemplateDisplayed = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_IPD.list_buttonPostOperativeTemplates, 10);
            m_assert.assertFalse(postOperativeTemplateDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sCreatePostOperativePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabMyQueue);
            postOperativeTemplateDisplayed = Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_IPD.list_buttonPostOperativeTemplates, 10);
            m_assert.assertTrue(postOperativeTemplateDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Post Operative edit access")
    public void validatePolicy_editPostOperativeTemplate() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_PreAnaesthesiaTemplate = new Page_PreAnaesthesiaTemplate(driver);
        oPage_AldreteScoreTemplate = new Page_AldreteScoreTemplate(driver);


        try {

            setPolicy(sEditPostOperativePolicyComponent, false);
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

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_IPD.list_buttonPostOperativeTemplates, 10);
            Cls_Generic_Methods.clickElement(oPage_AldreteScoreTemplate.button_alderteScoreTemplate);

            boolean postOperativeCreated =Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AldreteScoreTemplate.radioButton_2extremitiesInAldreteScoreTemplate, 16);

            if(postOperativeCreated){
                Cls_Generic_Methods.clickElement(oPage_IPD.button_saveOnModalHeader);
            }

            boolean editPostOperative = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.edit_template, 6);
            m_assert.assertFalse(editPostOperative, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditPostOperativePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabMyQueue);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_IPD.list_buttonPostOperativeTemplates, 10);
            Cls_Generic_Methods.clickElement(oPage_AldreteScoreTemplate.button_alderteScoreTemplate);

            editPostOperative = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.edit_template, 6);
            m_assert.assertTrue(editPostOperative, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Post Operative view access")
    public void validatePolicy_viewPostOperativeTemplate() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_PreAnaesthesiaTemplate=new Page_PreAnaesthesiaTemplate(driver);
        oPage_AldreteScoreTemplate = new Page_AldreteScoreTemplate(driver);


        try {

            setPolicy(sViewPostOperativePolicyComponent, false);
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

            boolean viewPostOperativeTemplateDisplayed =Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AldreteScoreTemplate.button_alderteScoreTemplate, 10);

            if(viewPostOperativeTemplateDisplayed){
                Cls_Generic_Methods.clickElement(oPage_AldreteScoreTemplate.button_alderteScoreTemplate);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AldreteScoreTemplate.radioButton_2extremitiesInAldreteScoreTemplate, 6);
                Cls_Generic_Methods.clickElement(oPage_IPD.button_saveOnModalHeader);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_closeForm,7);
                Cls_Generic_Methods.clickElement(oPage_IPD.button_closeForm);
            }

            viewPostOperativeTemplateDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AldreteScoreTemplate.button_alderteScoreTemplate, 10);
            m_assert.assertFalse(viewPostOperativeTemplateDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewPostOperativePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            selectPatientFromIpd(sTabMyQueue);
            viewPostOperativeTemplateDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AldreteScoreTemplate.button_alderteScoreTemplate, 10);
            m_assert.assertTrue(viewPostOperativeTemplateDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

}
