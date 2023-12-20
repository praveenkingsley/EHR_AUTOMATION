package tests.authorizationpolicy.opd;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;


public class PatientFormPolicyTest extends OPD_Policy {
    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_NewPatientRegisteration oPage_NewPatientRegisteration;
    Page_OPD oPage_OPD;

    String sEditPolicyComponent = "EDIT (PATIENT FORM)";
    String sAddSubReferralPolicyComponent = "ADD SUB REFERRAL MASTER (PATIENT FORM)";
    String sHistoryPolicyComponent = "HISTORY (PATIENT FORM)";
    String sAllergiesPolicyComponent = "ALLERGIES (PATIENT FORM)";


    @Test(description = "Validate Edit (PATIENT FORM) policy")
    public void validatePolicy_editPatientForm() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sEditPolicyComponent, false);
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

            boolean editPatientDetailsButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_Edit, 7);
            m_assert.assertFalse(editPatientDetailsButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditPolicyComponent, true);

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            CommonActions.selectDepartmentOnApp("OPD");
            boolean patientSelected = selectPatientFromAllTab();

            if (patientSelected) {
                editPatientDetailsButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_Edit, 7);
                m_assert.assertTrue(editPatientDetailsButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");
            }


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate edit patient details policy ->" + e);
        }

    }

    @Test(description = "Validate Add Referral (PATIENT FORM) policy")
    public void validatePolicy_AddSubReferralPatientForm() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        oPage_OPD = new Page_OPD(driver);

        String referral = "Referring Doctor";

        try {

            setPolicy(sAddSubReferralPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            CommonActions.loginFunctionality(sPolicyUser);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_AddAppointment, 10);
            CommonActions.openPatientRegisterationAndAppointmentForm();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.modalHeader_PatientRegForm, 20);

            Cls_Generic_Methods.scrollToElementByAction(driver, oPage_NewPatientRegisteration.input_medicalRecordNumOnPatientRegForm);
            Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_patientReferralSrcOnPatientRegForm, referral);

            boolean addSubReferralButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.button_addSubReferral, 7);
            m_assert.assertFalse(addSubReferralButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sAddSubReferralPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 10);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_AddAppointment, 10);
            CommonActions.openPatientRegisterationAndAppointmentForm();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.modalHeader_PatientRegForm, 20);

            Cls_Generic_Methods.scrollToElementByAction(driver, oPage_NewPatientRegisteration.input_medicalRecordNumOnPatientRegForm);
            Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_patientReferralSrcOnPatientRegForm, referral);

            addSubReferralButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.button_addSubReferral, 10);
            m_assert.assertTrue(addSubReferralButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(description = "Validate History (PATIENT FORM) policy")
    public void validatePolicy_historyPatientForm() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        oPage_OPD = new Page_OPD(driver);


        try {

            setPolicy(sHistoryPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            CommonActions.loginFunctionality(sPolicyUser);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_AddAppointment, 10);
            CommonActions.openPatientRegisterationAndAppointmentForm();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.modalHeader_PatientRegForm, 20);

            boolean historyTabDisplayed = CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm, "History");
            m_assert.assertFalse(historyTabDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sHistoryPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 10);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_AddAppointment, 10);
            CommonActions.openPatientRegisterationAndAppointmentForm();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.modalHeader_PatientRegForm, 20);

            historyTabDisplayed = CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm, "History");
            m_assert.assertTrue(historyTabDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(description = "Validate Allergies (PATIENT FORM) policy")
    public void validatePolicy_allergiesPatientForm() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        oPage_OPD = new Page_OPD(driver);


        try {

            setPolicy(sAllergiesPolicyComponent, false);
            enableOrDisableTimeSlot(false);
            adminTab = driver.getWindowHandle();

            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                opdTab = driver.getWindowHandle();
            }

            switchToPolicyUser();
            CommonActions.loginFunctionality(sPolicyUser);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_AddAppointment, 10);
            CommonActions.openPatientRegisterationAndAppointmentForm();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.modalHeader_PatientRegForm, 20);

            boolean allergiesTabDisplayed = CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm, "Allergies");
            m_assert.assertFalse(allergiesTabDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sAllergiesPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 10);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_AddAppointment, 10);
            CommonActions.openPatientRegisterationAndAppointmentForm();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.modalHeader_PatientRegForm, 20);

            allergiesTabDisplayed = CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm, "Allergies");
            m_assert.assertTrue(allergiesTabDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription);
        }
    }




}
