package tests.settings.facilitySetting.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.OPD_Data;
import data.Settings_Data;
import org.openqa.selenium.*;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.bills.Page_Bills;
import pages.commonElements.common_tabs.Page_RefractionTab;
import pages.commonElements.common_tabs.advice.Page_AdviceTab;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;
import pages.settings.facilitySettings.general.facilitySetup.Page_FacilitySetup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FacilitySetupTest extends TestBase {

    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();

    @Test(enabled = true, description = "Validate Patient for facility setup")
    public void createPatientToValidateFacilitySetup() {

        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                // Open the Search/Add patient dialog box
                try {
                    if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                        CommonActions.openPatientRegisterationAndAppointmentForm();
                    } else {
                        CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                                "Patient Details");
                        Thread.sleep(2000);
                    }
                } catch (NoSuchElementException e1) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                }

                Cls_Generic_Methods.customWait(2);

                // Validate the tabs on Patient Registration Form
                if (oPage_NewPatientRegisteration.tabs_PatientRegForm
                        .size() != oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.size()) {
                    m_assert.assertTrue(false,
                            "No. of Tabs on Patient Reg. Form is "
                                    + oPage_NewPatientRegisteration.tabs_PatientRegForm.size() + ". Expected = "
                                    + oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.size());
                } else {

                    m_assert.assertTrue("No. of Tabs on Patient Reg. & Appointment Form is "
                            + oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.size());

                    if (!Cls_Generic_Methods
                            .getElementAttribute(oPage_NewPatientRegisteration.tabs_PatientRegForm.get(0), "class")
                            .equals("active")) {
                        m_assert.assertTrue(false, "Patient Details Tab is not selected on start by default.");
                    } else {
                        m_assert.assertTrue(true, "Patient Details Tab is selected on start by default.");

                        try {
                            for (int i = 0; i < oPage_NewPatientRegisteration.tabs_PatientRegForm.size(); i++) {

                                if (oPage_NewPatientRegisteration.tabs_PatientRegForm.get(i).getText().trim()
                                        .equals(oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.get(i))) {

                                    m_assert.assertInfo(oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.get(i)
                                            + " Tab is displayed on the form.");
                                } else {
                                    m_assert.assertTrue(false, oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.get(i)
                                            + " Tab is not displayed on the form.");
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("" + e);
                        }
                    }
                }

                m_assert.assertTrue(
                        Cls_Generic_Methods.waitForElementToBecomeVisible(
                                oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage, 5),
                        "Alert for compulsory field is visible by default on the empty form.");

                Cls_Generic_Methods.clickElement(driver,
                        oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm);
                Thread.sleep(1000);

                // Validate the Compulsory Sections Message
                if (Cls_Generic_Methods
                        .getTextInElement(oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage).trim()
                        .equals(oEHR_Data.sCOMPULSORY_FIELDS_MESSAGE)) {
                    m_assert.assertTrue(true,
                            "Validate that the Compulsory sections message is " + oEHR_Data.sCOMPULSORY_FIELDS_MESSAGE);
                } else {
                    m_assert.assertTrue(false,
                            "Validate that the Compulsory sections message is " + oEHR_Data.sCOMPULSORY_FIELDS_MESSAGE);
                }

                // Validate the CSS of Compulsory Alert message
                if (Cls_Generic_Methods
                        .getElementAttribute(oPage_NewPatientRegisteration.subText_requiredFieldsAlertMessage, "style")
                        .equals(oEHR_Data.sSTYLE_OF_RED_ALERT_MSG_FOR_MANODATORY_FIELDS)) {
                    m_assert.assertTrue(true,
                            "Alert for compulsory field is highlighted in <b>Red</b> when trying to Create Appointment with empty form. Message = "
                                    + oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage.getText());
                } else {
                    m_assert.assertTrue(false,
                            "Alert for compulsory field is highlighted in <b>Red</b> when trying to Create Appointment with empty form.<br>"
                                    + "Expected = " + oEHR_Data.sSTYLE_OF_RED_ALERT_MSG_FOR_MANODATORY_FIELDS
                                    + "<br>Actual = " + Cls_Generic_Methods.getElementAttribute(
                                    oPage_NewPatientRegisteration.subText_requiredFieldsAlertMessage, "style"));
                }

                m_assert.assertTrue(oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage.getText().trim()
                        .contains("First Name"), "First Name is visible in the Compulsory Fields alert message.");

                m_assert.assertTrue(oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage.getText().trim()
                        .contains("Mobile Number"), "Mobile Number is visible in the Compulsory Fields alert message.");

                // Entering Essential Form Data
                if (!myPatient.getsSALUTATION().isEmpty()) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                    oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()),
                            "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
                }

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                        "First Name is entered as - " + myPatient.getsFIRST_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_middleNameOnPatientRegForm, myPatient.getsMIDDLE_NAME()),
                        "Middle Name is entered as - " + myPatient.getsMIDDLE_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_lastNameOnPatientRegForm, myPatient.getsLAST_NAME()),
                        "Last Name is entered as - " + myPatient.getsLAST_NAME());

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                                myPatient.getsMOBILE_NUMBER()),
                        "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm),
                        "Create Appointment button clicked");

                Cls_Generic_Methods.customWait(10);


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validate Finance and Receipts module in facility settings")
    public void validateDisplayAndReceipt() {


        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_FacilitySetup oPage_FacilitySetup = new Page_FacilitySetup(driver);
        Page_Bills oPage_Bills = new Page_Bills(driver);
        String expenseReport = "Expenses";
        String financeReport = "Finance Reports";
        String moduleName = "Display Finances & Receipts";
        boolean bModuleFound = false;
        boolean bExpenseReport = false;
        boolean bFinanceReport = false;
        String radioBtnValue = "";
        boolean bFinanceEnable = false;
        boolean bFinanceNotEnable = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.customWait(4);
            try {
                //get the facility settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Facility Setup");

                String settingWindowHandle = driver.getWindowHandle();
                bModuleFound = getFacilitySetupModulesOption(oPage_FacilitySetup.list_facilityOptions, moduleName);


                if (bModuleFound) {
                    //get the radio button status
                    for (WebElement radioButtonStatus : oPage_FacilitySetup.radioBtn_FinanceStatus) {
                        radioBtnValue = Cls_Generic_Methods.getElementAttribute(radioButtonStatus, "checked");
                        if (radioBtnValue != null) {
                            if (oPage_FacilitySetup.radioBtn_financeYes.isSelected()) {
                                bFinanceEnable = true;
                            } else {
                                Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.radioBtn_financeYes);
                                Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_financeSaveModule);
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                                Cls_Generic_Methods.customWait(15);
                                bFinanceEnable = true;
                            }
                        } else if (radioBtnValue == null) {
                            if (oPage_FacilitySetup.radioBtn_financeNo.isSelected()) {
                                bFinanceNotEnable = true;
                            } else {
                                Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.radioBtn_financeNo);
                                Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_financeSaveModule);
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                                Cls_Generic_Methods.customWait(15);
                                bFinanceNotEnable = true;
                            }
                        }


                        if (bFinanceEnable) {
                            //validate the receipt and bills in option "yes" case
                            m_assert.assertTrue(true, "Radio button Option = <b>" + "Yes" + "</b>");

                            WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);

                            if (!newTab.getTitle().equals(oEHR_Data.sEXPECTED_TITLE)) {
                                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                            }
                          //  getPatientInMyQueue();
                            /*if (Cls_Generic_Methods.isElementDisplayed(oPage_FacilitySetup.header_selectToken)) {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_skipWithoutToken),
                                        "Skip Without Token clicked ");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);
                            }*/
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);

                            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10),
                                    "<b> Bills option is visible </b> ");

                            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                            for (WebElement eOption : oPage_Navbar.list_OptionsUnderSettingsAndLogout) {
                                String sOptionText = Cls_Generic_Methods.getTextInElement(eOption);

                                if (sOptionText.equalsIgnoreCase(expenseReport)) {
                                    bExpenseReport = true;
                                }
                                if (sOptionText.equalsIgnoreCase(financeReport)) {
                                    bFinanceReport = true;
                                }

                            }
                            if (bExpenseReport && bFinanceReport) {
                                m_assert.assertTrue(true, "<b>" + expenseReport + " and " + financeReport + "  are visible </b> ");
                            }
                            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);

                            Cls_Generic_Methods.customWait(5);
                          //  m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientNotArrived),
                            //        "Button Not Arrived Clicked");
                            bFinanceEnable = false;
                            newTab.close();
                            Cls_Generic_Methods.customWait(1);
                            driver.switchTo().window(settingWindowHandle);

                        }
                        if (bFinanceNotEnable) {
                            //validate the receipt and bills in option "no" case
                            m_assert.assertTrue(true, "Radio button Option = <b>" + "No" + "</b>");

                            WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);

                            if (!newTab.getTitle().equals(oEHR_Data.sEXPECTED_TITLE)) {
                                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                            }
                            //getPatientInMyQueue();
                           /* if (Cls_Generic_Methods.isElementDisplayed(oPage_FacilitySetup.header_selectToken)) {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_skipWithoutToken),
                                        "Skip Without Token clicked ");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);
                            }*/
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);

                            m_assert.assertTrue(!(Cls_Generic_Methods.isElementDisplayed(oPage_Bills.button_clickBills)),
                                    "<b> Bills option is not visible </b> ");
                            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                            for (WebElement eOption : oPage_Navbar.list_OptionsUnderSettingsAndLogout) {
                                String sOptionText = Cls_Generic_Methods.getTextInElement(eOption);

                                if (!(sOptionText.equalsIgnoreCase(expenseReport))) {
                                    bExpenseReport = true;
                                }
                                if (!(sOptionText.equalsIgnoreCase(financeReport))) {
                                    bFinanceReport = true;
                                }

                            }
                            if (bExpenseReport && bFinanceReport) {
                                m_assert.assertTrue(true, "<b>" + expenseReport + " and " + financeReport + "  are not visible </b> ");

                            }
                            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                            Cls_Generic_Methods.customWait(5);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientNotArrived),
                                    "Button Not Arrived Clicked");
                            bFinanceNotEnable = false;
                            newTab.close();
                            Cls_Generic_Methods.customWait(1);
                            driver.switchTo().window(settingWindowHandle);
                            Cls_Generic_Methods.customWait(5);

                        }
                    }
                    //For making consultancy module at last as disabled
                    CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                    CommonActions.selectOptionFromLeftInSettingsPanel("General", "Facility Setup");

                    for (WebElement radioButtonStatus : oPage_FacilitySetup.radioBtn_FinanceStatus) {
                        radioBtnValue = Cls_Generic_Methods.getElementAttribute(radioButtonStatus, "checked");

                        if (radioBtnValue != null) {
                            if (oPage_FacilitySetup.radioBtn_financeYes.isSelected()) {
                                m_assert.assertInfo(true, moduleName + " is enabled");
                                break;
                            }
                        } else if (radioBtnValue == null) {
                            if (oPage_FacilitySetup.radioBtn_financeNo.isSelected()) {

                                Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.radioBtn_financeYes);
                                Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_financeSaveModule);
                                m_assert.assertInfo(true, moduleName + " is enabled");
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                                Cls_Generic_Methods.customWait(15);
                                break;
                            }
                        }
                    }
                } else {
                    m_assert.assertTrue(false, "Module not found");

                }
            } catch (Exception e) {
                m_assert.assertFatal(" Exception while getting finance module in facility setup " + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal("exception while getting application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Enable Invoice ")
    public void validateEnableInvoiceCompulsion() {
        Page_FacilitySetup oPage_FacilitySetup = new Page_FacilitySetup(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        String moduleName = "Enable Invoice Compulsion";
        boolean bModuleFound = false;
        String radioBtnValue = "";
        boolean bInvoiceEnable = false;
        boolean bInvoiceNotEnable = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.customWait(4);

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Facility Setup");

                String settingWindowHandle = driver.getWindowHandle();
                bModuleFound = getFacilitySetupModulesOption(oPage_FacilitySetup.list_facilityOptions, moduleName);

                if (bModuleFound) {
                    for (WebElement radioButtonStatus : oPage_FacilitySetup.radioBtn_InvoiceStatus) {
                        radioBtnValue = Cls_Generic_Methods.getElementAttribute(radioButtonStatus, "checked");
                        if (radioBtnValue != null) {
                            if (oPage_FacilitySetup.radioBtn_InvoiceYes.isSelected()) {
                                bInvoiceEnable = true;
                            } else {
                                Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.radioBtn_InvoiceYes);
                                Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_invoiceSaveModule);
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                                Cls_Generic_Methods.customWait(15);
                                bInvoiceEnable = true;
                            }
                        } else if (radioBtnValue == null) {
                            if (oPage_FacilitySetup.radioBtn_InvoiceNo.isSelected()) {
                                bInvoiceNotEnable = true;
                            } else {
                                Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.radioBtn_InvoiceNo);
                                Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_invoiceSaveModule);
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                                Cls_Generic_Methods.customWait(15);
                                bInvoiceNotEnable = true;
                            }
                        }

                        if (bInvoiceEnable) {
                            m_assert.assertTrue(true, "Radio button Option = <b>" + "Yes" + "</b>");

                            WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);

                            if (!newTab.getTitle().equals(oEHR_Data.sEXPECTED_TITLE)) {
                                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                            }
                            getPatientInMyQueue();

                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);

                            String invoiceAlert = oPage_FacilitySetup.text_InvoiceCompulsion.getText();
                            m_assert.assertTrue(true, "Invoice Compulsion alert message = <b>" + invoiceAlert + "</b>");
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientNotArrived),
                                    "Button Not Arrived Clicked");
                            Cls_Generic_Methods.customWait(5);

                            bInvoiceEnable = false;
                            newTab.close();
                            Cls_Generic_Methods.customWait(1);
                            driver.switchTo().window(settingWindowHandle);
                            Cls_Generic_Methods.customWait(1);
                        }
                        if (bInvoiceNotEnable) {
                            m_assert.assertTrue(true, "Radio button Option = <b>" + "NO" + "</b>");

                            WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);

                            if (!newTab.getTitle().equals(oEHR_Data.sEXPECTED_TITLE)) {
                                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                            }
                            getPatientInMyQueue();

                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);


                            m_assert.assertTrue(!(Cls_Generic_Methods.isElementDisplayed(oPage_FacilitySetup.text_InvoiceCompulsion)),
                                    "Invoice Compulsion Message is <b> not visible </b>");
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientNotArrived),
                                    "Button Not Arrived Clicked");
                            Cls_Generic_Methods.customWait(5);

                            bInvoiceNotEnable = false;
                            newTab.close();
                            Cls_Generic_Methods.customWait(1);
                            driver.switchTo().window(settingWindowHandle);
                            Cls_Generic_Methods.customWait(1);
                        }
                    }
                } else {
                    m_assert.assertTrue(false, "Please enable <b> Display Finances & Receipts </b> Module");
                }
            } catch (Exception e) {
                m_assert.assertFatal(" Exception while getting invoice module in facility setup " + e);
                e.printStackTrace();
            }

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Facility Setup");

                String settingWindowHandle = driver.getWindowHandle();
                bModuleFound = getFacilitySetupModulesOption(oPage_FacilitySetup.list_facilityOptions, moduleName);

                if (bModuleFound) {
                    for (WebElement radioButtonStatus : oPage_FacilitySetup.radioBtn_InvoiceStatus) {
                        radioBtnValue = Cls_Generic_Methods.getElementAttribute(radioButtonStatus, "checked");
                        if (radioBtnValue != null) {
                            if (oPage_FacilitySetup.radioBtn_InvoiceYes.isSelected()) {
                                bInvoiceEnable = true;
                            } else {
                                bInvoiceNotEnable = true;
                            }
                        } else if (radioBtnValue == null) {
                            if (oPage_FacilitySetup.radioBtn_InvoiceYes.isSelected()) {
                                bInvoiceEnable = true;
                            } else {
                                bInvoiceNotEnable = true;
                            }
                        }

                        if (bInvoiceEnable) {
                            m_assert.assertInfo(true, "Radio button Option = <b>" + "Yes" + "</b>");
                            Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.radioBtn_InvoiceNo);
                            Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_invoiceSaveModule);
                            Cls_Generic_Methods.waitForPageLoad(driver, 20);
                            Cls_Generic_Methods.customWait(15);
                            m_assert.assertTrue(true, "Set Radio button Option = <b>" + "No" + "</b>");
                              break;
                        }
                    }
                } else {
                    m_assert.assertTrue(false, "Please enable <b> Display Finances & Receipts </b> Module");
                }
            } catch (Exception e) {
                m_assert.assertFatal(" Exception while getting invoice module in facility setup " + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal(" Exception while getting application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate token enablement for patient appointment")
    public void validateEnableTokenForAppointment() {
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_FacilitySetup oPage_FacilitySetup = new Page_FacilitySetup(driver);
        String moduleName = "Enable Token for Appointment";
        boolean bModuleFound = false;
        String radioBtnValue = "";
        boolean bTokenEnable = false;
        boolean bTokenNotEnable = false;

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.customWait(4);
            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(2);

                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Facility Setup");

                String settingWindowHandle = driver.getWindowHandle();
                bModuleFound = getFacilitySetupModulesOption(oPage_FacilitySetup.list_facilityOptions, moduleName);
                if (bModuleFound) {
                    for (WebElement radioButtonStatus : oPage_FacilitySetup.radioBtn_TokenStatus) {
                        radioBtnValue = Cls_Generic_Methods.getElementAttribute(radioButtonStatus, "checked");
                        if (radioBtnValue != null) {
                            if (oPage_FacilitySetup.radioBtn_TokenYes.isSelected()) {
                                bTokenEnable = true;
                            } else {
                                Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.radioBtn_TokenYes);
                                Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_tokenSaveModule);
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                                Cls_Generic_Methods.customWait(14);
                                bTokenEnable = true;
                            }
                        } else if (radioBtnValue == null) {
                            if (oPage_FacilitySetup.radioBtn_TokenNo.isSelected()) {
                                bTokenNotEnable = true;
                            } else {
                                Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.radioBtn_TokenNo);
                                Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_tokenSaveModule);
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                                Cls_Generic_Methods.customWait(14);
                                bTokenNotEnable = true;
                            }
                        }
                        if (bTokenEnable) {
                            m_assert.assertTrue(true, "Radio button Option = <b>" + "Yes" + "</b>");

                            WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);

                            if (!newTab.getTitle().equals(oEHR_Data.sEXPECTED_TITLE)) {
                                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                            }
                            getPatientInMyQueue();
                           /* m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_FacilitySetup.header_selectToken, 10),
                                    "<b> Select token </b> form is visible");

                            try {
                                for (WebElement tokenElement : oPage_FacilitySetup.list_tokenNumber) {
                                    //int index = oPage_FacilitySetup.list_tokenNumber.indexOf(tokenElement);
                                    String tokenNumber = tokenElement.getText();
                                    if (!(tokenElement.getAttribute("class").contains("activate-token"))) {
                                        tokenElement.click();
                                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FacilitySetup.button_saveToken),
                                                "Token Number Saved <b> " + tokenNumber + " </b> ");
                                        break;
                                    }else if(tokenElement.getAttribute("class").contains("activate-token")){

                                        tokenElement.click();
                                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FacilitySetup.button_saveToken),
                                                "Token Saved ");
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                m_assert.assertWarn("Unable to select token - \n" + e);

                            }*/

                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientNotArrived),
                                    "Button Not Arrived Clicked");
                            Cls_Generic_Methods.customWait(4);
                            bTokenEnable = false;
                            newTab.close();
                            Cls_Generic_Methods.customWait(1);
                            driver.switchTo().window(settingWindowHandle);
                            Cls_Generic_Methods.customWait(1);
                        }
                        if (bTokenNotEnable) {
                            m_assert.assertTrue(true, "Radio button Option = <b>" + "NO" + "</b>");

                            WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);

                            if (!newTab.getTitle().equals(oEHR_Data.sEXPECTED_TITLE)) {
                                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                            }
                            getPatientInMyQueue();

                            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5),
                                    "");
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientNotArrived),
                                    "Button Not Arrived Clicked");
                            Cls_Generic_Methods.customWait(4);

                            bTokenNotEnable = false;
                            newTab.close();
                            Cls_Generic_Methods.customWait(1);
                            driver.switchTo().window(settingWindowHandle);
                            Cls_Generic_Methods.customWait(1);
                        }
                    }
                } else {
                    m_assert.assertTrue(false, "Module not found");

                }
            } catch (Exception e) {
                m_assert.assertFatal(" Exception while getting enable token module in facility setup " + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal(" Exception while getting application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate sorting of tokens for appointment")
    public void validateSortAppointmentListByToken() {
        Page_FacilitySetup oPage_FacilitySetup = new Page_FacilitySetup(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        String moduleName = "Sort Appointment List by Token";
        Page_OPD oPage_OPD = new Page_OPD(driver);
        List<Integer> beforeMarkingTokenSort = new ArrayList<>();
        List<Integer> initialListOfTokens = new ArrayList<>();
        List<Integer> afterMarkingTokenSort = new ArrayList<>();
        String myQueueTab = OPD_Data.tab_MY_QUEUE;
        boolean bModuleFound = false;
        String radioBtnValue = "";
        boolean bSortByToken = false;
        String patientName = null;
        boolean bNotSortByToken = false;
        String concatPatientFullName = "";
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.customWait(5);
            concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Facility Setup");

                String settingWindowHandle = driver.getWindowHandle();
                bModuleFound = getFacilitySetupModulesOption(oPage_FacilitySetup.list_facilityOptions, moduleName);

                if (bModuleFound) {
                    CommonActions.selectDepartmentOnApp("OPD");
                    for (int i = 0; i < 3; i++) {
                        FacilitySetupTest oFacilitySetupTest = new FacilitySetupTest();
                        oFacilitySetupTest.createPatientToValidateFacilitySetup();
                        //getPatientInMyQueue();
                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);

                    for (WebElement token : oPage_FacilitySetup.list_token) {
                        if (token.isDisplayed()) {
                            initialListOfTokens.add(Integer.parseInt(token.getText()));
                            Collections.sort(initialListOfTokens);
                        }
                    }
                    CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                    CommonActions.selectOptionFromLeftInSettingsPanel("General", "Facility Setup");
                    for (WebElement radioButtonStatus : oPage_FacilitySetup.radioBtn_TokenSortStatus) {
                        radioBtnValue = Cls_Generic_Methods.getElementAttribute(radioButtonStatus, "checked");
                        if (radioBtnValue != null) {
                            if (oPage_FacilitySetup.radioBtn_TokenSortYes.isSelected()) {
                                bSortByToken = true;
                            } else {
                                Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.radioBtn_TokenSortYes);
                                Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_tokenSaveModule);
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                                bSortByToken = true;
                            }
                        } else if (radioBtnValue == null) {
                            if (oPage_FacilitySetup.radioBtn_TokenSortNo.isSelected()) {
                                bNotSortByToken = true;
                            } else {
                                Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.radioBtn_TokenSortNo);
                                Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_tokenSaveModule);
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                                Cls_Generic_Methods.customWait(14);
                                bNotSortByToken = true;
                            }
                        }

                        if (bSortByToken) {
                            m_assert.assertTrue(true, "Radio button Option = <b>" + "YES" + "</b>");

                            WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);

                            if (!newTab.getTitle().equals(oEHR_Data.sEXPECTED_TITLE)) {
                                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                            }

                            if (oPage_FacilitySetup.list_token.isEmpty()) {
                                m_assert.assertTrue(true, "<b> No Token assigned </b> to any patient. ");
                            } else {
                                for (WebElement token : oPage_FacilitySetup.list_token) {
                                    if (token.isDisplayed()) {
                                        afterMarkingTokenSort.add(Integer.parseInt(token.getText()));
                                    }
                                }

//                                m_assert.assertTrue(initialListOfTokens.equals(afterMarkingTokenSort), "Token List is sorted <b> " + afterMarkingTokenSort +initialListOfTokens + " </b> ");
                                m_assert.assertTrue(initialListOfTokens.equals(afterMarkingTokenSort), "Token List is sorted <b> Initial = " + initialListOfTokens + ", After sorting = " + afterMarkingTokenSort + " </b> ");
                            }
                            bSortByToken = false;
                            newTab.close();
                            Cls_Generic_Methods.customWait(1);
                            driver.switchTo().window(settingWindowHandle);
                        }
                        if (bNotSortByToken) {
                            m_assert.assertTrue(true, "Radio button Option = <b>" + "NO" + "</b>");

                            WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);

                            if (!newTab.getTitle().equals(oEHR_Data.sEXPECTED_TITLE)) {
                                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                            }
                            if (oPage_FacilitySetup.list_token.isEmpty()) {
                                m_assert.assertTrue(true, "<b> No Token assigned </b> to any patient. ");
                            } else {
                                for (WebElement token : oPage_FacilitySetup.list_token) {
                                    if (token.isDisplayed()) {
                                        beforeMarkingTokenSort.add(Integer.parseInt(token.getText()));
                                    }
                                }
//                                m_assert.assertTrue(!(initialListOfTokens.equals(beforeMarkingTokenSort)), "Token List is not sorted <b> " + beforeMarkingTokenSort + initialListOfTokens + " </b> ");
                                m_assert.assertTrue(!(initialListOfTokens.equals(beforeMarkingTokenSort)),
                                        "Token List is not sorted <b> Initial = " + beforeMarkingTokenSort + " </b> ");
                            }
                            bNotSortByToken = false;
                            newTab.close();
                            Cls_Generic_Methods.customWait(1);
                            driver.switchTo().window(settingWindowHandle);
                        }
                    }
                    CommonActions.selectDepartmentOnApp("OPD");
                    m_assert.assertTrue(
                            CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, myQueueTab),
                            "Validate " + myQueueTab + " tab is selected");
                    Cls_Generic_Methods.customWait(1);

                    for (int j = oPage_OPD.rows_patientAppointments.size(); j > 0; j--) {
                        WebElement eTabElement = oPage_OPD.rows_patientAppointments.get(j - 1);

                        if (eTabElement.isDisplayed()) {
                            List<WebElement> patientDetailsOnRow = eTabElement.findElements(By.xpath("./child::*"));
                            patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");

                            if (concatPatientFullName.equals(patientName.trim())) {
                                Cls_Generic_Methods.clickElement(driver, eTabElement);
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                                        16);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientNotArrived),
                                        "Button Not Arrived Clicked for patient <b> " + patientName + " </b> ");
                                Cls_Generic_Methods.waitForPageLoad(driver, 5);
                                Cls_Generic_Methods.customWait(2);
                                oPage_OPD = new Page_OPD(driver);
                            }
                        }

                    }
                    //disable token module
                    disableTokenModuleAfterTest();
                } else {
                    m_assert.assertTrue(false, "Please enable <b> Enable Token for Appointment </b> Module");
                }
            } catch (Exception e) {
                m_assert.assertTrue(false, "Exception while sorting token in facility setup " + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertTrue(false, "Exception while running application" + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Consultancy Type Popup ")
    public void validateConsultancyTypePopUp() {
        Page_FacilitySetup oPage_FacilitySetup = new Page_FacilitySetup(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = "";
        boolean bPatientNameFound = false;
        String NotArrivedTab = OPD_Data.tab_NOT_ARRIVED;
        String moduleName = "Show Consultancy Type Popup";
        boolean bModuleFound = false;
        String radioBtnValue = "";
        boolean bConsultancyPopUpEnable = false;
        boolean bConsultancyPopUpNotEnable = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Facility Setup");

                String settingWindowHandle = driver.getWindowHandle();
                bModuleFound = getFacilitySetupModulesOption(oPage_FacilitySetup.list_facilityOptions, moduleName);


                if (bModuleFound) {
                    for (WebElement radioButtonStatus : oPage_FacilitySetup.radioBtn_ConsultancyStatus) {
                        radioBtnValue = Cls_Generic_Methods.getElementAttribute(radioButtonStatus, "checked");
                        if (radioBtnValue != null) {
                            if (oPage_FacilitySetup.radioBtn_ConsultancyYes.isSelected()) {
                                bConsultancyPopUpEnable = true;
                            } else {
                                Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.radioBtn_ConsultancyYes);
                                Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_consultancySaveModule);
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                                Cls_Generic_Methods.customWait(15);
                                bConsultancyPopUpEnable = true;
                            }
                        } else if (radioBtnValue == null) {
                            if (oPage_FacilitySetup.radioBtn_ConsultancyNo.isSelected()) {
                                bConsultancyPopUpNotEnable = true;
                            } else {
                                Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.radioBtn_ConsultancyNo);
                                Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_consultancySaveModule);
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                                Cls_Generic_Methods.customWait(15);
                                bConsultancyPopUpNotEnable = true;
                            }
                        }

                        if (bConsultancyPopUpEnable) {
                            m_assert.assertTrue(true, "Radio button Option = <b>" + "Yes" + "</b>");

                            WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);

                            if (!newTab.getTitle().equals(oEHR_Data.sEXPECTED_TITLE)) {
                                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                            }

                            m_assert.assertTrue(
                                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, NotArrivedTab),
                                    "Validate " + NotArrivedTab + " tab is selected");
                            Cls_Generic_Methods.customWait(1);
                            bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

                            if (bPatientNameFound) {
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientArrived),
                                        "Mark Patient arrived clicked ");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilitySetup.header_consultancyPopUp, 8);
                                if (Cls_Generic_Methods.isElementDisplayed(oPage_FacilitySetup.header_consultancyPopUp)) {
                                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_FacilitySetup.header_consultancyPopUp, 10),
                                            "<b> Consultancy PopUp </b> form is visible");
                                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_closeConsultancyPopUp),
                                            "<b> Consultancy PopUp Closed </b> ");

                                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);
                                } else {
                                    m_assert.assertTrue(false, "<b> Consultancy PopUp </b> form is not visible even it's enabled.");
                                }
                                bConsultancyPopUpEnable = false;
                                newTab.close();
                                Cls_Generic_Methods.customWait(1);
                                driver.switchTo().window(settingWindowHandle);
                                Cls_Generic_Methods.customWait(1);
                            }
                        }
                        if (bConsultancyPopUpNotEnable) {
                            m_assert.assertTrue(true, "Radio button Option = <b>" + "NO" + "</b>");

                            WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);

                            if (!newTab.getTitle().equals(oEHR_Data.sEXPECTED_TITLE)) {
                                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                            }


                            m_assert.assertTrue(
                                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, NotArrivedTab),
                                    "Validate " + NotArrivedTab + " tab is selected");
                            Cls_Generic_Methods.customWait(1);
                            bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

                            if (bPatientNameFound) {
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientArrived),
                                        "Mark Patient arrived clicked ");
                                Cls_Generic_Methods.customWait(3);

                                //if token form is visible click skip without token
                                if (Cls_Generic_Methods.isElementDisplayed(oPage_FacilitySetup.header_selectToken)) {
                                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_FacilitySetup.header_selectToken, 10),
                                            "<b> Select token </b> form is visible");
                                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_skipWithoutToken),
                                            "Skip Without Token clicked ");
                                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage.get(0), 16);

                                }

                                if (!(Cls_Generic_Methods.isElementDisplayed(oPage_FacilitySetup.header_consultancyPopUp))) {
                                    m_assert.assertTrue(true, "<b> Consultancy PopUp form is not </b> visible");
                                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage.get(0), 16);

                                } else {
                                    m_assert.assertTrue(false, "<b> Consultancy PopUp </b> form is visible even when disabled.");
                                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_closeConsultancyPopUp),
                                            "<b> Consultancy PopUp Closed </b> ");

                                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);
                                }

                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientNotArrived),
                                        "Button Not Arrived Clicked");
                                Cls_Generic_Methods.customWait(5);
                                bConsultancyPopUpNotEnable = false;
                                newTab.close();
                                Cls_Generic_Methods.customWait(1);
                                driver.switchTo().window(settingWindowHandle);
                                Cls_Generic_Methods.customWait(1);
                            }
                        }
                    }

                    //For making consultancy module at last as disabled
                    CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                    CommonActions.selectOptionFromLeftInSettingsPanel("General", "Facility Setup");

                    for (WebElement radioButtonStatus : oPage_FacilitySetup.radioBtn_ConsultancyStatus) {
                        radioBtnValue = Cls_Generic_Methods.getElementAttribute(radioButtonStatus, "checked");

                        if (radioBtnValue != null) {
                            if (oPage_FacilitySetup.radioBtn_ConsultancyYes.isSelected()) {
                                Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.radioBtn_ConsultancyNo);
                                Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_consultancySaveModule);
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                                Cls_Generic_Methods.customWait(15);
                                break;
                            }
                        } else if (radioBtnValue == null) {
                            if (oPage_FacilitySetup.radioBtn_ConsultancyNo.isSelected()) {
                                break;
                            }
                        }
                    }
                } else {
                    m_assert.assertTrue(false, "Module not found");

                }
            } catch (Exception e) {
                m_assert.assertFatal(" Exception while getting consultancy popup module in facility setup " + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal(" Exception while getting application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Language Support Feature ")
    public void validateLanguageSupportFeature() {
        Page_FacilitySetup oPage_FacilitySetup = new Page_FacilitySetup(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = "";
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        boolean bPatientNameFound = false;
        String myAppointmentTab = OPD_Data.tab_ALL;
        String moduleName = "Use Language Support Feature";
        boolean bModuleFound = false;
        String radioBtnValue = "";
        boolean bLanguageSupportEnable = false;
        boolean bLanguageSupportNotEnable = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Facility Setup");

                String settingWindowHandle = driver.getWindowHandle();
                bModuleFound = getFacilitySetupModulesOption(oPage_FacilitySetup.list_facilityOptions, moduleName);

                if (bModuleFound) {

                    //go in opd and create template
                    CommonActions.selectDepartmentOnApp("OPD");
                    Cls_Generic_Methods.customWait();
                    getPatientInMyQueue();
                    createPostOpTemplate();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);
                    CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                    CommonActions.selectOptionFromLeftInSettingsPanel("General", "Facility Setup");

                    for (WebElement radioButtonStatus : oPage_FacilitySetup.radioBtn_LanguageStatus) {
                        radioBtnValue = Cls_Generic_Methods.getElementAttribute(radioButtonStatus, "checked");
                        if (radioBtnValue != null) {
                            if (oPage_FacilitySetup.radioBtn_LanguageYes.isSelected()) {
                                bLanguageSupportEnable = true;
                            } else {
                                Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.radioBtn_LanguageYes);
                                Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_LanguageSaveModule);
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                                Cls_Generic_Methods.customWait(15);
                                bLanguageSupportEnable = true;
                            }
                        } else if (radioBtnValue == null) {
                            if (oPage_FacilitySetup.radioBtn_LanguageNo.isSelected()) {
                                bLanguageSupportNotEnable = true;
                            } else {
                                Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.radioBtn_LanguageNo);
                                Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_LanguageSaveModule);
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                                Cls_Generic_Methods.customWait(15);
                                bLanguageSupportNotEnable = true;
                            }
                        }

                        if (bLanguageSupportEnable) {
                            m_assert.assertTrue(true, "Radio button Option = <b>" + "Yes" + "</b>");

                            WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);

                            if (!newTab.getTitle().equals(oEHR_Data.sEXPECTED_TITLE)) {
                                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                                Cls_Generic_Methods.customWait(5);
                            }

                            m_assert.assertTrue(
                                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, myAppointmentTab),
                                    "Validate " + myAppointmentTab + " tab is selected");
                            Cls_Generic_Methods.customWait(2);
                           // Cls_Generic_Methods.clickElement(driver, oPage_OPD.section_completed);
                            for (WebElement eTabElement : oPage_OPD.rows_patientAppointments) {

                                if (eTabElement.isDisplayed()) {
                                    List<WebElement> patientDetailsOnRow = eTabElement.findElements(By.xpath("./child::*"));
                                   String patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");
                                   String status = Cls_Generic_Methods.getTextInElement(patientDetailsOnRow.get(4));

                                    if (concatPatientFullName.equals(patientName.trim())
                                    && status.contains("Completed")) {
                                        Cls_Generic_Methods.clickElement(driver, eTabElement);
                                        bPatientNameFound = true;
                                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                                                16);
                                        break;
                                    }
                                }
                            }
                            m_assert.assertTrue(bPatientNameFound, "Patient name found in OPD: <b> " + concatPatientFullName + "</b>");

                           // bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);


                            if (bPatientNameFound) {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_openTemplate),
                                        "Clicked on Today's template");

                                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.text_headerOPDSummary, 5),
                                        "Templated Opened, Text Visible: <b> " + oPage_OPD.text_headerOPDSummary.getText() + "</b>");

                                if (Cls_Generic_Methods.isElementDisplayed(oPage_FacilitySetup.text_language)) {
                                    Cls_Generic_Methods.customWait();
                                    m_assert.assertTrue(true, "<b> Language support is visible </b> ");
                                }
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
                                Cls_Generic_Methods.customWait(1);

                            }
                            bLanguageSupportEnable = false;
                            newTab.close();
                            Cls_Generic_Methods.customWait(1);
                            driver.switchTo().window(settingWindowHandle);
                            Cls_Generic_Methods.customWait(1);
                        }

                        if (bLanguageSupportNotEnable) {
                            m_assert.assertTrue(true, "Radio button Option = <b>" + "NO" + "</b>");

                            WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);

                            if (!newTab.getTitle().equals(oEHR_Data.sEXPECTED_TITLE)) {
                                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                            }

                            m_assert.assertTrue(
                                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, myAppointmentTab),
                                    "Validate " + myAppointmentTab + " tab is selected");
                            Cls_Generic_Methods.customWait(1);
                            bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

                            if (bPatientNameFound) {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_openTemplate),
                                        "Clicked on Today's template");
                                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.text_headerOPDSummary, 5),
                                        "Templated Opened, Text Visible: <b> " + oPage_OPD.text_headerOPDSummary.getText() + "</b>");

                                if (!(Cls_Generic_Methods.isElementDisplayed(oPage_FacilitySetup.text_language))) {
                                    Cls_Generic_Methods.customWait();
                                    m_assert.assertTrue(true, "<b> Language support is not visible </b>");
                                } else {
                                    m_assert.assertTrue(false, "<b> Language Support </b> is visible even when disabled.");
                                }
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
                                Cls_Generic_Methods.customWait(1);

                            }
                            bLanguageSupportNotEnable = false;
                            newTab.close();
                            Cls_Generic_Methods.customWait(1);
                            driver.switchTo().window(settingWindowHandle);
                            Cls_Generic_Methods.customWait(1);
                        }
                    }
                } else {
                    m_assert.assertTrue(false, "Module not found");

                }
            } catch (Exception e) {
                m_assert.assertFatal(" Exception while getting Language support module in facility setup " + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal(" Exception while getting application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate deleting patient")
    public void removePatientForFacilitySetup() {
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = "";
        String NotArrivedTab = OPD_Data.tab_NOT_ARRIVED;
        String patientName = null;

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OPD");
            try {
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();


                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, NotArrivedTab),
                        "Validate " + NotArrivedTab + " tab is selected");
                Cls_Generic_Methods.customWait(1);

                for (int j = oPage_OPD.rows_patientAppointments.size(); j > 0; j--) {
                    WebElement eTabElement = oPage_OPD.rows_patientAppointments.get(j - 1);

                    if (eTabElement.isDisplayed()) {
                        List<WebElement> patientDetailsOnRow = eTabElement.findElements(By.xpath("./child::*"));
                        patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");

                        if (concatPatientFullName.equals(patientName.trim())) {
                            Cls_Generic_Methods.clickElement(driver, eTabElement);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                                    16);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_cancelAppointment),
                                    "<b> Cancel Button </b> " + " clicked on page for patient <b> " + patientName + " </b>");
                            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.header_cancelAppointment, 5),
                                    "<b> Cancel appointment form displayed </b> ");
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_cancelAppointmentForm),
                                    "Appointment cancel button clicked for patient <b> " + patientName + " </b> ");
                            Cls_Generic_Methods.waitForPageLoad(driver, 5);

                            oPage_OPD = new Page_OPD(driver);
                        }
                    }

                }


               /* CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_cancelAppointment),
                        "Cancel Button clicked on page ");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.header_cancelAppointment, 5),
                        "Cancel appointment form displayed ");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_cancelAppointmentForm),
                        "Appointment cancel button clicked ");*/

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while deleting patient" + e);
            }
        } catch (Exception e) {
            m_assert.assertTrue(false, "Exception while running application" + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate online and offline feature uses")
    public void validateOnlineOfflineFeatureUses() {

        Page_FacilitySetup oPage_FacilitySetup = new Page_FacilitySetup(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = "";
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        boolean bPatientNameFound = false;
        String myAppointmentTab = OPD_Data.tab_ALL;
        String moduleName = "Use Online/Offline Feature";
        boolean bModuleFound = false;
        String radioBtnValue = "";
        boolean bOnlineEnable = false;
        boolean bOnlineNotEnable = false;


        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Facility Setup");

                String settingWindowHandle = driver.getWindowHandle();
                bModuleFound = getFacilitySetupModulesOption(oPage_FacilitySetup.list_facilityOptions, moduleName);

                if (bModuleFound) {
                    for (WebElement radioButtonStatus : oPage_FacilitySetup.radioBtn_showUserState) {
                        radioBtnValue = Cls_Generic_Methods.getElementAttribute(radioButtonStatus, "checked");
                        if (radioBtnValue != null) {
                            if (oPage_FacilitySetup.radioBtn_onlineYes.isSelected()) {
                                bOnlineEnable = true;
                            } else {
                                Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.radioBtn_onlineYes);
                                Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_onlineStateSave);
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                                Cls_Generic_Methods.customWait(15);
                                bOnlineEnable = true;
                            }
                        } else if (radioBtnValue == null) {
                            if (oPage_FacilitySetup.radioBtn_onlineNo.isSelected()) {
                                bOnlineNotEnable = true;
                            } else {
                                Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.radioBtn_onlineNo);
                                Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_onlineStateSave);
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                                Cls_Generic_Methods.customWait(15);
                                bOnlineNotEnable = true;
                            }
                        }

                        if (bOnlineEnable) {
                            m_assert.assertTrue(true, "Radio button Option = <b>" + "Yes" + "</b>");
                            WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);
                            if (!newTab.getTitle().equals(oEHR_Data.sEXPECTED_TITLE)) {
                                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                            }

                            if (Cls_Generic_Methods.isElementDisplayed(oPage_FacilitySetup.link_userState)) {
                                Cls_Generic_Methods.clickElement(oPage_FacilitySetup.link_userState);
                                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilitySetup.text_changeState, 10),
                                        "SetState link is displaying when online feature is enabled");
                            } else {
                                m_assert.assertTrue(false, "SetState link is not displaying when online feature is enabled");
                            }
                            //Validate message when OT is selected on setState
                            Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.input_otAll);
                            Cls_Generic_Methods.customWait(2);
                            Cls_Generic_Methods.clickElement(oPage_FacilitySetup.button_saveChangeSet);
                            Cls_Generic_Methods.waitForPageLoad(driver, 8);
                            m_assert.assertTrue(
                                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, myAppointmentTab),
                                    "Validate " + myAppointmentTab + " tab is selected");
                            Cls_Generic_Methods.customWait(3);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);

                            bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

                            if (bPatientNameFound) {
                                if (Cls_Generic_Methods.isElementDisplayed(oPage_FacilitySetup.text_otMode)) {
                                    m_assert.assertTrue(true, "Validation message is displaying when selecting OT on change set and do save");
                                } else {
                                    m_assert.assertTrue(false, "Validation message is not displaying when selecting OT on change set and do save");
                                }
                            }

                            Cls_Generic_Methods.clickElement(oPage_FacilitySetup.link_userState);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilitySetup.text_changeState, 10);
                            Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.input_offlineAll);
                            Cls_Generic_Methods.customWait(2);
                            Cls_Generic_Methods.clickElement(oPage_FacilitySetup.button_saveChangeSet);
                            Cls_Generic_Methods.waitForPageLoad(driver, 8);

                            m_assert.assertTrue(
                                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, myAppointmentTab),
                                    "Validate " + myAppointmentTab + " tab is selected");
                            Cls_Generic_Methods.customWait(1);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);

                            bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

                            if (bPatientNameFound) {
                                if (Cls_Generic_Methods.isElementDisplayed(oPage_FacilitySetup.text_offlineMode)) {
                                    m_assert.assertTrue(true, "Validation message is displaying when selecting Offline on change set and do save");
                                } else {
                                    m_assert.assertTrue(false, "Validation message is not displaying when selecting Offline on change set and do save");
                                }
                            }

                            //Reset to OPD mode
                           Cls_Generic_Methods.clickElement(oPage_FacilitySetup.link_userState);
                          Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilitySetup.text_changeState, 10);
                            Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.input_opdAll);
                            Cls_Generic_Methods.customWait(2);
                            Cls_Generic_Methods.clickElement(oPage_FacilitySetup.button_saveChangeSet);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);

                            bOnlineEnable = false;
                            newTab.close();
                            Cls_Generic_Methods.customWait(1);
                            driver.switchTo().window(settingWindowHandle);
                            Cls_Generic_Methods.customWait(1);
                        }
                        if (bOnlineNotEnable) {
                            m_assert.assertTrue(true, "Radio button Option = <b>" + "NO" + "</b>");

                            WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);

                            if (!newTab.getTitle().equals(oEHR_Data.sEXPECTED_TITLE)) {
                                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                                Cls_Generic_Methods.waitForPageLoad(newTab, 20);
                            }
                            Cls_Generic_Methods.customWait(5);
                            newTab.navigate().refresh();
                            Cls_Generic_Methods.waitForPageLoad(newTab, 20);

                            try {
                                if (Cls_Generic_Methods.isElementDisplayed(oPage_FacilitySetup.link_userState)) {
                                    m_assert.assertTrue(false, "User state is displaying in online disabled mode also ");
                                } else {
                                    m_assert.assertTrue(true, "User state is not displaying in online disabled mode also ");
                                }
                            } catch (NoSuchElementException e) {
                                m_assert.assertTrue(true, "User state is not displaying in online disabled mode also ");
                            }

                            bOnlineNotEnable = false;
                            newTab.close();
                            Cls_Generic_Methods.customWait(1);
                            driver.switchTo().window(settingWindowHandle);
                            Cls_Generic_Methods.customWait(1);
                        }
                    }
                } else {
                    m_assert.assertTrue(false, "Please enable <b> online/offline  </b> Module");
                }
            } catch (Exception e) {
                m_assert.assertFatal(" Exception while getting online/offline module in facility setup " + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal(" Exception while getting application " + e);
            e.printStackTrace();
        }

    }

    private void getPatientInMyQueue() {
        //get patient in my queue and assign token as desired
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_FacilitySetup oPage_FacilitySetup = new Page_FacilitySetup(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = "";
        boolean bPatientNameFound = false;
        String NotArrivedTab = OPD_Data.tab_NOT_ARRIVED;
        try {

            concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            m_assert.assertTrue(
                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, NotArrivedTab),
                    "Validate " + NotArrivedTab + " tab is selected");
            Cls_Generic_Methods.customWait(1);
            bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

            if (bPatientNameFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientArrived),
                        "Mark Patient arrived clicked ");
                Cls_Generic_Methods.customWait(3);
                if (Cls_Generic_Methods.isElementDisplayed(oPage_FacilitySetup.header_selectToken)) {
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_FacilitySetup.header_selectToken, 10),
                            "<b> Select token </b> form is visible");

                    for (WebElement tokenElement : oPage_FacilitySetup.list_tokenNumber) {
                        String tokenNumber = tokenElement.getText();
                        if (!(tokenElement.getAttribute("class").contains("activate-token"))) {
                            Cls_Generic_Methods.clickElement(tokenElement);
                            Cls_Generic_Methods.customWait();
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FacilitySetup.button_saveToken),
                                    "Token Number Saved <b>  " + tokenNumber + " </b> ");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);
                            break;
                        }
                    }

                } else {
                    m_assert.assertTrue(!(Cls_Generic_Methods.isElementDisplayed(oPage_FacilitySetup.header_selectToken)),
                            "<b> Select token </b> form is not visible.");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage.get(0), 16);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Exception while getting patient" + e);
        }
    }

    private boolean getFacilitySetupModulesOption(List<WebElement> listModuleOptions, String expectedModuleName) {

        //for getting module name in facility setup
        boolean moduleSelected = false;
        try {
            for (WebElement facilityOption : listModuleOptions) {
                if (facilityOption.isDisplayed()) {
                    String facilityOptionName = facilityOption.getText();
                    if (expectedModuleName.equals(facilityOptionName.trim())) {
                        m_assert.assertTrue(true, "Facility Module Option = <b>" + facilityOptionName + "</b>");
                        moduleSelected = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moduleSelected;
    }

    private void createPostOpTemplate() {
        Page_OPD oPage_OPD = new Page_OPD(driver);
        String PostOpTemplate = "Post OP";
        Page_FacilitySetup oPage_FacilitySetup = new Page_FacilitySetup(driver);
        Page_RefractionTab oPage_RefractionTab = new Page_RefractionTab(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_AdviceTab oPage_AdviceTab = new Page_AdviceTab(driver);
        try {
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, PostOpTemplate), "Validate " + PostOpTemplate + " template  is selected");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.tab_RefractionTab, 20);

            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FacilitySetup.checkbox_rightVision),
                    "VA not examined for right clicked ");
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FacilitySetup.checkbox_leftVision),
                    "VA not examined for left clicked ");

            Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_rightIop);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_rightIop, myPatient.getsIOP_RIGHT()), "Right Eye intraocularPressure is " + myPatient.getsIOP_RIGHT());

            Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_leftIop);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_leftIop, myPatient.getsIOP_LEFT()), "Left Eye intraocularPressure is " + myPatient.getsIOP_RIGHT());

            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.tab_advice), "Advice Tab Is selected");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.tab_adviceUnderAdviceTab), "Advice Tab under advice Is selected");

                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_AdviceTab.dropdown_advice),
                        "Advice dropdown clicked ");
                Cls_Generic_Methods.selectElementByIndex(oPage_AdviceTab.dropdown_advice, 1);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate),
                        PostOpTemplate + " Template Saved. ");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.text_headerOPDSummary, 5),
                        "Templated Saved, Text Visible: <b> " + oPage_OPD.text_headerOPDSummary.getText() + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);
                //make patient as mark as complete
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_markPatientAsCompleted),
                        "Mark As Completed Clicked ");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

    }

    private void disableTokenModuleAfterTest() {
        Page_FacilitySetup oPage_FacilitySetup = new Page_FacilitySetup(driver);
        String radioBtnValue = "";

        String moduleName = "Enable Token for Appointment";
        boolean bModulePresent = false;
        try {

            CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Facility Setup");

            bModulePresent = getFacilitySetupModulesOption(oPage_FacilitySetup.list_facilityOptions, moduleName);
            if (bModulePresent) {
                for (WebElement radioButtonStatus : oPage_FacilitySetup.radioBtn_TokenStatus) {
                    radioBtnValue = Cls_Generic_Methods.getElementAttribute(radioButtonStatus, "checked");

                    if (radioBtnValue != null) {
                        if (oPage_FacilitySetup.radioBtn_TokenYes.isSelected()) {
                            Cls_Generic_Methods.clickElementByAction(driver, oPage_FacilitySetup.radioBtn_TokenNo);
                            Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_tokenSaveModule);
                            Cls_Generic_Methods.waitForPageLoad(driver, 20);
                            Cls_Generic_Methods.customWait(15);
                            break;
                        }
                    } else if (radioBtnValue == null) {
                        if (oPage_FacilitySetup.radioBtn_TokenNo.isSelected()) {
                            break;
                        }
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
