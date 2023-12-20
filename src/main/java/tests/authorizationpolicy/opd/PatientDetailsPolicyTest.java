package tests.authorizationpolicy.opd;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;

public class PatientDetailsPolicyTest extends OPD_Policy {

    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_NewPatientRegisteration oPage_NewPatientRegisteration;
    Page_OPD oPage_OPD;

    String sViewContactNumberPolicyComponent = "VIEW CONTACT NUMBER (PATIENT DETAILS)";
    String sViewDobPolicyComponent = "VIEW DOB (PATIENT DETAILS)";
    String sPrintPolicyComponent = "PRINT (PATIENT DETAILS)";
    String sAddPatientReferralPolicyComponent = "ADD PATIENT REFERRAL (PATIENT DETAILS)";
    String sEditPatientReferralReferralPolicyComponent = "EDIT PATIENT REFERRAL (PATIENT DETAILS)";
    String sAddAppointmentReferralPolicyComponent = "ADD APPOINTMENT REFERRAL (PATIENT DETAILS)";
    String sEditAppointmentReferralPolicyComponent = "EDIT APPOINTMENT REFERRAL (PATIENT DETAILS)";


    @Test(description = "Validate Patient contact number view access")
    public void validatePolicy_viewContactNumberPatientDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oSoftAssert=new SoftAssert(driver);

        try {

            setPolicy(sViewContactNumberPolicyComponent, false);
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

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.text_contactNumberOpdRhs, 10);
            String displayedContactNumber = Cls_Generic_Methods.getTextInElement(oPage_OPD.text_contactNumberOpdRhs);

            boolean viewContactStatus = !displayedContactNumber.contains("X");

            m_assert.assertFalse(viewContactStatus, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewContactNumberPolicyComponent, true);

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 10);

            selectPatientFromAllTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.text_contactNumberOpdRhs, 10);
            displayedContactNumber = Cls_Generic_Methods.getTextInElement(oPage_OPD.text_contactNumberOpdRhs);
            viewContactStatus = !displayedContactNumber.contains("X");

            m_assert.assertTrue(viewContactStatus, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Patients DOB View access")
    public void validatePolicy_viewDobPatientDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oSoftAssert=new SoftAssert(driver);
        oPage_NewPatientRegisteration=new Page_NewPatientRegisteration(driver);

        try {

            setPolicy(sViewDobPolicyComponent, false);
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

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_Edit,10);
            Cls_Generic_Methods.clickElement(oPage_OPD.button_Edit);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_patientDob,10);
            Cls_Generic_Methods.sendKeysByJS(driver,oPage_NewPatientRegisteration.input_patientDob,"31/01/1997");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_OPD.button_Save);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.text_dobOpdRhs, 10);

            String displayedPatientDetails = Cls_Generic_Methods.getTextInElement(oPage_OPD.text_contactNumberOpdRhs);

            boolean patientDobDisplayed = displayedPatientDetails.contains("31-01-1997");

            m_assert.assertFalse(patientDobDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sViewDobPolicyComponent, true);

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 10);

            selectPatientFromAllTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.text_contactNumberOpdRhs, 10);
            displayedPatientDetails = Cls_Generic_Methods.getTextInElement(oPage_OPD.text_contactNumberOpdRhs);
            patientDobDisplayed = !displayedPatientDetails.contains("X");

            m_assert.assertTrue(patientDobDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Patient card/details print access")
    public void validatePolicy_printPatientDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sPrintPolicyComponent, false);
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

            boolean printPatientDetailsButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_printPatientDetails, 7);
            m_assert.assertFalse(printPatientDetailsButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sPrintPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            printPatientDetailsButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_printPatientDetails, 10);
            m_assert.assertTrue(printPatientDetailsButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Assign Patient Referral access")
    public void validatePolicy_addPatientReferral() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            setPolicy(sAddPatientReferralPolicyComponent, false);
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

            boolean addPatientReferralButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_addPatientReferralOpdRhs, 7);
            m_assert.assertFalse(addPatientReferralButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sAddPatientReferralPolicyComponent, true);
            Cls_Generic_Methods.customWait();

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            selectPatientFromAllTab();

            addPatientReferralButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_addPatientReferralOpdRhs, 10);
            m_assert.assertTrue(addPatientReferralButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Patient Referral Edit access")
    public void validatePolicy_editPatientReferral() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_NewPatientRegisteration =new Page_NewPatientRegisteration(driver);

        try {

            setPolicy(sEditPatientReferralReferralPolicyComponent, false);
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

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_addPatientReferralOpdRhs, 7);
            Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_addPatientReferralOpdRhs);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.select_patientReferralSrcOnPatientRegForm, 7);
            Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_patientReferralSrcOnPatientRegForm, "Self");
            Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_Save);

            boolean editPatientReferralButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_editPatientReferralOpdRhs, 7);
            m_assert.assertFalse(editPatientReferralButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditPatientReferralReferralPolicyComponent, true);

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            CommonActions.selectDepartmentOnApp("OPD");
            selectPatientFromAllTab();

            editPatientReferralButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_editPatientReferralOpdRhs, 7);

            m_assert.assertTrue(editPatientReferralButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Add Appointment Referral Edit access")
    public void validatePolicy_addAppointmentReferral() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_NewPatientRegisteration =new Page_NewPatientRegisteration(driver);

        try {

            setPolicy(sAddAppointmentReferralPolicyComponent, false);
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

            boolean referralNotAdded=Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_addPatientReferralOpdRhs, 7);
            if(referralNotAdded) {
                Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_addPatientReferralOpdRhs);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.select_patientReferralSrcOnPatientRegForm, 7);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_patientReferralSrcOnPatientRegForm, "Self");
                Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_Save);
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_Summary,10);
            Cls_Generic_Methods.clickElement(driver,oPage_OPD.button_Summary);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_addAppointmentOpdSummary,10);
            Cls_Generic_Methods.clickElement(driver,oPage_OPD.button_addAppointmentOpdSummary);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_Save,10);
            Cls_Generic_Methods.clickElement(driver,oPage_OPD.button_Save);

            Cls_Generic_Methods.customWait(7);
            CommonActions.selectDepartmentOnApp("OPD");
            Cls_Generic_Methods.customWait(4);
            selectPatientFromAllTab("NOT ARRIVED");

            boolean addAppointmentReferralButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_addAppointmentReferralOpdRhs, 7);
            m_assert.assertFalse(addAppointmentReferralButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sAddAppointmentReferralPolicyComponent, true);

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            CommonActions.selectDepartmentOnApp("OPD");
            selectPatientFromAllTab("NOT ARRIVED");

            addAppointmentReferralButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_addAppointmentReferralOpdRhs, 7);

            m_assert.assertTrue(addAppointmentReferralButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

    @Test(description = "Validate Edit Appointment Referral Edit access")
    public void validatePolicy_editAppointmentReferral() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_NewPatientRegisteration =new Page_NewPatientRegisteration(driver);

        try {

            setPolicy(sEditAppointmentReferralPolicyComponent, false);
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
            Cls_Generic_Methods.waitForPageLoad(driver,20);
            boolean appointmentPatientAdded =selectPatientFromAllTab("NOT ARRIVED");

            if(!appointmentPatientAdded) {
                createPatient();
                selectPatientFromAllTab();

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_addPatientReferralOpdRhs, 7);
                Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_addPatientReferralOpdRhs);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.select_patientReferralSrcOnPatientRegForm, 7);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_patientReferralSrcOnPatientRegForm, "Self");
                Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_Save);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_Summary,10);
                Cls_Generic_Methods.clickElement(driver,oPage_OPD.button_Summary);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_addAppointmentOpdSummary,10);
                Cls_Generic_Methods.clickElement(driver,oPage_OPD.button_addAppointmentOpdSummary);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_Save,10);
                Cls_Generic_Methods.clickElement(driver,oPage_OPD.button_Save);

                Cls_Generic_Methods.customWait(7);
                CommonActions.selectDepartmentOnApp("OPD");
                Cls_Generic_Methods.customWait(5);
                selectPatientFromAllTab("NOT ARRIVED");
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_addAppointmentReferralOpdRhs, 7);
            Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_addAppointmentReferralOpdRhs);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.select_patientReferralSrcOnPatientRegForm, 7);
            Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_patientReferralSrcOnPatientRegForm, "Self");
            Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_Save);

            boolean editAppointmentReferralButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_editAppointmentReferralOpdRhs, 7);
            m_assert.assertFalse(editAppointmentReferralButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is disabled for the user</font>");

            switchToAdmin();
            setPolicy(sEditAppointmentReferralPolicyComponent, true);

            switchToPolicyUser();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);
            CommonActions.selectDepartmentOnApp("OPD");
            selectPatientFromAllTab("NOT ARRIVED");

            editAppointmentReferralButtonDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_editAppointmentReferralOpdRhs, 7);
            m_assert.assertTrue(editAppointmentReferralButtonDisplayed, "<font color='blue'>Validated " + sPolicyDescription + " is enabled for the user</font>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate " + sPolicyDescription + " ->" + e);
        }

    }

}
