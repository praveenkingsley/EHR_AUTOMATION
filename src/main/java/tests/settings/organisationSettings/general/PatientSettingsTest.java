package tests.settings.organisationSettings.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.settings.organisationSettings.general.Page_PatientSettings;


public class PatientSettingsTest extends TestBase {
    EHR_Data oEHR_Data = new EHR_Data();
    static String formFieldName = "Address";

    @Test(enabled = true, description = "Validate mandatory fields selection in patient form settings")
    public void validateSelectionOfMandatoryField() {
        Page_PatientSettings oPage_PatientSettings = new Page_PatientSettings(driver);
        boolean fieldFound = false;
        int indexOfFormField = -1;
        int indexOfCheckbox = -1;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Patient Settings");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientSettings.header_formValidationFields, 3);

                for (WebElement eField : oPage_PatientSettings.list_formFieldNames) {
                    if (Cls_Generic_Methods.getTextInElement(eField).equalsIgnoreCase(formFieldName)) {
                        indexOfFormField = oPage_PatientSettings.list_formFieldNames.indexOf(eField);
                        fieldFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(fieldFound, "Field for Patient Form Settings: <b> " + formFieldName + "</b>");
                if (fieldFound) {
                    for (WebElement eCheckbox : oPage_PatientSettings.list_checkbox_formFieldNames) {
                        if ((!eCheckbox.isSelected())) {
                            indexOfCheckbox = oPage_PatientSettings.list_checkbox_formFieldNames.indexOf(eCheckbox);
                        }

                        if (indexOfFormField == indexOfCheckbox) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, eCheckbox),
                                    "Option selected for: <b> " + formFieldName + "</b>");
                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_PatientSettings.button_saveFormFields),
                                    "Mandatory Fields saved");
                            break;
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal(" " + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }
    }

    @Test(enabled = true, description = "Validating mandatory field selection in patient registration form")
    public void validateFormFieldsInOpdRegistrationForm() {
        boolean mandatoryFieldAlert = false;
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                CommonActions.selectDepartmentOnApp("OPD");

                // Open the Search/Add patient dialog box
                try {
                    if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                        CommonActions.openPatientRegisterationAndAppointmentForm();
                    } else {
                        CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm, "Patient Details");
                        Cls_Generic_Methods.customWait();
                    }
                } catch (NoSuchElementException e1) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                }
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm), "Validate that Create Appointment button is clicked");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_pincodeOnPatientRegForm, 2);
                //validating the alert message
                try {
                    String sMandatoryFieldClass = Cls_Generic_Methods.getElementAttribute(oPage_NewPatientRegisteration.input_pincodeOnPatientRegForm, "class");
                    if (sMandatoryFieldClass.contains("error")) {
                        mandatoryFieldAlert = true;
                    }
                    m_assert.assertTrue(mandatoryFieldAlert, "Compulsory Field Message Alert Coming for Address field.");
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.button_closePatientRegForm),
                            "Registration form closed ");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_addAppointment, 3);
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal(" " + e);
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal(" " + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }

    }

    @Test(enabled = true, description = "Validating restore to initial mandatory form fields")
    public void validateRestoringToInitialFormValidationFields() {
        Page_PatientSettings oPage_PatientSettings = new Page_PatientSettings(driver);
        boolean fieldFound = false;
        boolean fieldUnselected = false;
        int indexOfFormField = -1;
        int indexOfCheckbox = -1;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Patient Settings");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientSettings.header_formValidationFields, 3);

                for (WebElement eField : oPage_PatientSettings.list_formFieldNames) {
                    if (Cls_Generic_Methods.getTextInElement(eField).equalsIgnoreCase(formFieldName)) {
                        indexOfFormField = oPage_PatientSettings.list_formFieldNames.indexOf(eField);
                        fieldFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(fieldFound, "Field for Patient Form Settings: <b> " + formFieldName + "</b>");
                if (fieldFound) {
                    for (WebElement eCheckbox : oPage_PatientSettings.list_checkbox_formFieldNames) {
                        if (eCheckbox.isSelected()) {
                            indexOfCheckbox = oPage_PatientSettings.list_checkbox_formFieldNames.indexOf(eCheckbox);
                        }

                        if (indexOfFormField == indexOfCheckbox) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, eCheckbox),
                                    "Option unselected for: <b> " + formFieldName + "</b>");
                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_PatientSettings.button_saveFormFields),
                                    "Mandatory Fields saved");
                            fieldUnselected = true;
                            break;
                        }
                    }
                    m_assert.assertTrue(fieldUnselected, "<b> Field was Unselected successfully </b>");
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal(" " + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }

    }
}
