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

public class TemplatePolicyTest extends OPD_Policy {

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    String sCreateTemplatePolicyComponent = "CREATE (TEMPLATE)";
    String sEditTemplatePolicyComponent = "EDIT (TEMPLATE)";
    String sCloneTemplatePolicyComponent = "CLONE (TEMPLATE)";


    @Test(description = "Validate OPD Clinical template create access")
    public void validatePolicy_createTemplate() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            setPolicy(sCreateTemplatePolicyComponent, false);
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

            boolean addNewTemplateBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_AddNewTemplate, 10);

            m_assert.assertFalse(addNewTemplateBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sCreateTemplatePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            addNewTemplateBtnDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_AddNewTemplate, 10);
            m_assert.assertTrue(addNewTemplateBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Clinical template Edit access")
    public void validatePolicy_editTemplate() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            setPolicy(sEditTemplatePolicyComponent, false);
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
            Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate);
            CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, "Vital Sign");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_SaveTemplate, 10);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_SaveTemplate);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_editOnModal, 10);
            boolean editTemplateBtnDisplayed = isButtonEnabled(oPage_CommonElements.button_editOnModal);

            m_assert.assertFalse(editTemplateBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditTemplatePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_createdVitalSignTemplate, 10);
            Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_createdVitalSignTemplate);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_editOnModal, 10);
            editTemplateBtnDisplayed = isButtonEnabled(oPage_CommonElements.button_editOnModal);
            m_assert.assertTrue(editTemplateBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate OPD Clinical template clone access")
    public void validatePolicy_cloneTemplate() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        String patientName="POLICY TEST (DONT USE)";

        try {

            setPolicy(sCloneTemplatePolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            searchOldPatientAndCreateAppointment(patientName);
            selectPatientFromAllTab("ALL",patientName);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            Cls_Generic_Methods.clickElement(oPage_OPD.list_buttonOldTemplates.get(0));
            Cls_Generic_Methods.customWait();
            boolean cloneTemplateBtnDisplayed = isButtonEnabled(oPage_OPD.list_buttonOldTemplates.get(0));

            m_assert.assertFalse(cloneTemplateBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sCloneTemplatePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab("ALL",patientName);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            Cls_Generic_Methods.clickElement(oPage_OPD.list_buttonOldTemplates.get(0));
            Cls_Generic_Methods.customWait();
            cloneTemplateBtnDisplayed = isButtonEnabled(oPage_OPD.list_buttonOldTemplates.get(0));
            m_assert.assertTrue(cloneTemplateBtnDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }


}
