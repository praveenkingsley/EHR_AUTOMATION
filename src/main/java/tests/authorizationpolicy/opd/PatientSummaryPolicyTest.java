package tests.authorizationpolicy.opd;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;

public class PatientSummaryPolicyTest extends OPD_Policy {

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_OPD oPage_OPD;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    String sViewPatientSummaryPolicyComponent = "VIEW (PATIENT SUMMARY)";
    String sPatientTimelinePolicyComponent = "PATIENT TIMELINE (PATIENT SUMMARY)";
    String sPatientUploadsPolicyComponent = "PATIENT UPLOADS (PATIENT SUMMARY)";
    String sMedicationsPolicyComponent = "MEDICATIONS (PATIENT SUMMARY)";
    String sInvestigationsPolicyComponent = "INVESTIGATIONS (PATIENT SUMMARY)";
    String sOpticalPolicyComponent = "OPTICAL (PATIENT SUMMARY)";
    String sBillsPolicyComponent = "BILLS (PATIENT SUMMARY)";
    String sCertificatePolicyComponent = "CERTIFICATE (PATIENT SUMMARY)";
    String sReferralMessagePolicyComponent = "REFERRAL MESSAGE (PATIENT SUMMARY)";


    @Test(description = "Validate Patient summary view access (Sub module)")
    public void validatePolicy_viewPatientSummary() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            setPolicy(sViewPatientSummaryPolicyComponent, false);
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

            boolean summaryBtnEnabled = Cls_Generic_Methods.isElementEnabled(oPage_OPD.button_Summary);
            m_assert.assertFalse(summaryBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewPatientSummaryPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            summaryBtnEnabled = Cls_Generic_Methods.isElementEnabled(oPage_OPD.button_Summary);
            m_assert.assertTrue(summaryBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Patient timeline view access")
    public void validatePolicy_patientTimelinePatientSummary() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            setPolicy(sPatientTimelinePolicyComponent, false);
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
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_Summary,10);
            Cls_Generic_Methods.clickElement(oPage_OPD.button_Summary);

            boolean timelineSummaryBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_viewTimelineSummary,7);
            m_assert.assertFalse(timelineSummaryBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sPatientTimelinePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            timelineSummaryBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_viewTimelineSummary,7);
            m_assert.assertTrue(timelineSummaryBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Patient upload view access")
    public void validatePolicy_patientUploadPatientSummary() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            setPolicy(sPatientUploadsPolicyComponent, false);
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
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_Summary,10);
            Cls_Generic_Methods.clickElement(oPage_OPD.button_Summary);

            boolean uploadSummaryBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_viewUploadSummary,7);
            m_assert.assertFalse(uploadSummaryBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sPatientUploadsPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            uploadSummaryBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_viewUploadSummary,7);
            m_assert.assertTrue(uploadSummaryBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Patient medications view access")
    public void validatePolicy_medicationsPatientSummary() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            setPolicy(sMedicationsPolicyComponent, false);
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
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_Summary,10);
            Cls_Generic_Methods.clickElement(oPage_OPD.button_Summary);

            boolean medicationsSummaryBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_viewMedicationsSummary,7);
            m_assert.assertFalse(medicationsSummaryBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sMedicationsPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            medicationsSummaryBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_viewMedicationsSummary,7);
            m_assert.assertTrue(medicationsSummaryBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Patient Investigations view access")
    public void validatePolicy_investigationsPatientSummary() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            setPolicy(sInvestigationsPolicyComponent, false);
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
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_Summary,10);
            Cls_Generic_Methods.clickElement(oPage_OPD.button_Summary);

            boolean investigationsSummaryBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_viewInvestigationsSummary,7);
            m_assert.assertFalse(investigationsSummaryBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sInvestigationsPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            investigationsSummaryBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_viewInvestigationsSummary,7);
            m_assert.assertTrue(investigationsSummaryBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Patient Optical view access")
    public void validatePolicy_opticalPatientSummary() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            setPolicy(sOpticalPolicyComponent, false);
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
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_Summary,10);
            Cls_Generic_Methods.clickElement(oPage_OPD.button_Summary);

            boolean opticalSummaryBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_viewOpticalSummary,7);
            m_assert.assertFalse(opticalSummaryBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sOpticalPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            opticalSummaryBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_viewOpticalSummary,7);
            m_assert.assertTrue(opticalSummaryBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Patient Bills view access")
    public void validatePolicy_billsPatientSummary() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            setPolicy(sBillsPolicyComponent, false);
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
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_Summary,10);
            Cls_Generic_Methods.clickElement(oPage_OPD.button_Summary);

            boolean invoiceSummaryBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_viewInvoiceSummary,7);
            m_assert.assertFalse(invoiceSummaryBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sBillsPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            invoiceSummaryBtnEnabled = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_viewInvoiceSummary,7);
            m_assert.assertTrue(invoiceSummaryBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Patients medical certificate generate access")
    public void validatePolicy_certificatePatientSummary() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {
            setPolicy(sCertificatePolicyComponent, false);
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
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_Summary,10);
            Cls_Generic_Methods.clickElement(oPage_OPD.button_Summary);

            boolean certificateBtnDisplayed= Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_certificateSummary,7);

            boolean certificateSummaryBtnEnabled;

            if(certificateBtnDisplayed){
                certificateSummaryBtnEnabled = isButtonEnabled(oPage_OPD.button_certificateSummary);
                m_assert.assertFalse(certificateSummaryBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");
            }else{
                throw new RuntimeException("Login with valid user");
            }

            switchToAdmin();
            setPolicy(sCertificatePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            certificateSummaryBtnEnabled = isButtonEnabled(oPage_OPD.button_certificateSummary);
            m_assert.assertTrue(certificateSummaryBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Patients referral message generate access")
    public void validatePolicy_referralMessagePatientSummary() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {
            setPolicy(sReferralMessagePolicyComponent, false);
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
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_Summary,10);
            Cls_Generic_Methods.clickElement(oPage_OPD.button_Summary);

            boolean certificateBtnDisplayed= Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_referralMessageSummary,7);

            boolean referralMessageSummaryBtnEnabled;

            if(certificateBtnDisplayed){
                referralMessageSummaryBtnEnabled = isButtonEnabled(oPage_OPD.button_referralMessageSummary);
                m_assert.assertFalse(referralMessageSummaryBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");
            }else{
                throw new RuntimeException("Login with valid user");
            }

            switchToAdmin();
            setPolicy(sReferralMessagePolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            referralMessageSummaryBtnEnabled = isButtonEnabled(oPage_OPD.button_referralMessageSummary);
            m_assert.assertTrue(referralMessageSummaryBtnEnabled, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }


}
