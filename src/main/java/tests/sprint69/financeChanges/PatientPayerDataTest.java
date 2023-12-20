package tests.sprint69.financeChanges;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.sprint69.financeChanges.Page_FinanceChanges;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientPayerDataTest extends TestBase {
    EHR_Data oEHR_Data = new EHR_Data();
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date date = new Date();
    String date1 = dateFormat.format(date);
    String sPatientPayerDataName = "TestAutoPatientPayerDataName" + date1;
    String sPatientPayerDataDescription = "TestAutoPatientPayerDataDescription" + date1;
    String sUpdatedPatientPayerDataName = "UpdatedTestAutoPatientPayerDataName" + date1;
    String sUpdatedPatientPayerDataDescription = "UpdatedTestAutoPatientPayerDataDescription" + date1;

    //Patient payer data Master
    @Test(enabled = true, description = "Validate Adding New Patient Payer Data")
    public void validateAddingNewPatientPayerDataFromMaster() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        boolean bPatientPayerButtonFound = false;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Patient Payer Data Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPatientPayerData, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addPatientPayerData),
                        "Add Patient Payer Data button clicked ");

                //adding new Patient Payer Data
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.text_addPatientPayerDataLabel, 20);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_FinanceChanges.text_addPatientPayerDataLabel),
                        "Add Patient Payer Data pop opened");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_patientPayerDataName, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_patientPayerDataName, sPatientPayerDataName), "Entered Patient Payer Data name  =  <b> " + sPatientPayerDataName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_dispensaryDescription, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_patientPayerDataDescription, sPatientPayerDataDescription), "Entered Patient Payer Data Description  =  <b> " + sPatientPayerDataDescription + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_saveChanges, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_saveChanges),
                        "Save changes button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPatientPayerData, 20);
                //Verify newly created patient payer data in Payer master form.
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPayerMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addPayerMaster),
                        "Add Payer master button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.label_addPayerForm, 20);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_FinanceChanges.label_addPayerForm),
                        "Add Payer form opened");
                Cls_Generic_Methods.customWait();
                for (WebElement ePatientPayerDataButton : oPage_FinanceChanges.list_patientPayerDataButton) {
                    if (ePatientPayerDataButton.getText().equalsIgnoreCase(sPatientPayerDataName)) {
                        bPatientPayerButtonFound = true;
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(ePatientPayerDataButton), "Clicked on patient payer data button = <b>" + sPatientPayerDataName + "</b>");
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }
                if (bPatientPayerButtonFound) {
                    m_assert.assertTrue(true, "Patient payer data  Found in payer form <b> " + sPatientPayerDataName + " </b> ");
                } else {
                    m_assert.assertTrue(false, "incorrect Patient payer data  Found in payer form  ");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_Close, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_Close);
                Cls_Generic_Methods.customWait();
            } catch (Exception e) {
                m_assert.assertFatal("Patient Payer Data not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Edit and Disable Patient Payer Data Functionality")
    public void validateEditAndDisablePatientPayerDataMaster() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        boolean bPatientPayerDataNameFoundInTable = false;
        boolean bPatientPayerDataDescriptionFoundInTable = false;
        boolean bUpdatedPatientPayerButtonFound = false;
        boolean bDisabledPatientPayerButtonFound = false;
        int indexOfPatientPayerDataName = -1;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Patient Payer Data Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPatientPayerData, 3);

                //validate Patient Payer Data name in table
                for (WebElement ePatientPayerDataName : oPage_FinanceChanges.list_PatientPayerDataName) {
                    if (ePatientPayerDataName.getText().equalsIgnoreCase(sPatientPayerDataName)) {
                        bPatientPayerDataNameFoundInTable = true;
                        indexOfPatientPayerDataName = oPage_FinanceChanges.list_PatientPayerDataName.indexOf(ePatientPayerDataName);
                        break;
                    }
                }
                m_assert.assertTrue(bPatientPayerDataNameFoundInTable, "Patient payer data Name Found in table <b> " + sPatientPayerDataName + " </b> ");
                Cls_Generic_Methods.customWait(2);

                //Validate Patient Payer description
                String sPatientPayerDescriptionOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_PatientPayerDataDescription.get(indexOfPatientPayerDataName));
                if (sPatientPayerDescriptionOnUI.equalsIgnoreCase(sPatientPayerDataDescription)) {
                    bPatientPayerDataDescriptionFoundInTable = true;
                }
                m_assert.assertTrue(bPatientPayerDataDescriptionFoundInTable, "Patient Payer Description Description Found in table <b> " + sPatientPayerDescriptionOnUI + " </b> ");

                //validate edit Patient Payer data functionality
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_editPatientPayerDataButton.get(indexOfPatientPayerDataName));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_patientPayerDataName, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.inputField_patientPayerDataName), "Cleared values in Patient payer data name field");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_patientPayerDataName, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_patientPayerDataName, sUpdatedPatientPayerDataName), "Entered Patient payer data name  =  <b> " + sUpdatedPatientPayerDataName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_patientPayerDataDescription, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.inputField_patientPayerDataDescription), "Cleared values in Patient payer data description field");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_patientPayerDataDescription, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_patientPayerDataDescription, sUpdatedPatientPayerDataDescription), "Entered Patient payer data Description  =  <b> " + sUpdatedPatientPayerDataDescription + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_saveChanges, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_saveChanges),
                        "Save changes button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPatientPayerData, 20);

                //validate Updated Patient payer data Name in table
                for (WebElement ePatientPayerDataName : oPage_FinanceChanges.list_PatientPayerDataName) {
                    if (ePatientPayerDataName.getText().equalsIgnoreCase(sUpdatedPatientPayerDataName)) {
                        bPatientPayerDataNameFoundInTable = true;
                        indexOfPatientPayerDataName = oPage_FinanceChanges.list_PatientPayerDataName.indexOf(ePatientPayerDataName);
                        break;
                    }
                }
                m_assert.assertTrue(bPatientPayerDataNameFoundInTable, "Updated Patient payer data Name Found in table <b> " + sUpdatedPatientPayerDataName + " </b> ");

                //Validate updated Patient payer data description in table
                String sUpdatedPatientPayerDataDescriptionOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_PatientPayerDataDescription.get(indexOfPatientPayerDataName));
                if (sUpdatedPatientPayerDataDescriptionOnUI.equalsIgnoreCase(sUpdatedPatientPayerDataDescription)) {
                    bPatientPayerDataDescriptionFoundInTable = true;
                }
                m_assert.assertTrue(bPatientPayerDataDescriptionFoundInTable, "Updated Patient payer data Description Found in table <b> " + sUpdatedPatientPayerDataDescriptionOnUI + " </b> ");

                //Verify Updated patient payer data in Payer master form.
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPayerMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addPayerMaster),
                        "Add Payer master button clicked ");
                Cls_Generic_Methods.customWait(5);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_FinanceChanges.label_addPayerForm),
                        "Add Payer form opened");
                Cls_Generic_Methods.customWait();
                for (WebElement eUpdatedPatientPayerDataButton : oPage_FinanceChanges.list_patientPayerDataButton) {
                    if (eUpdatedPatientPayerDataButton.getText().equalsIgnoreCase(sUpdatedPatientPayerDataName)) {
                        bUpdatedPatientPayerButtonFound = true;
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(eUpdatedPatientPayerDataButton), "Clicked on updated patient payer data button = <b>" + sUpdatedPatientPayerDataName + "</b>");
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }
                if (bUpdatedPatientPayerButtonFound) {
                    m_assert.assertTrue(true, "updated Patient payer data  Found in payer form = <b> " + sUpdatedPatientPayerDataName + " </b> ");
                } else {
                    m_assert.assertTrue(false, "incorrect Patient payer data  Found in payer form ");

                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_Close, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_Close);

                //Validate Disable Patient payer data description functionality
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Patient Payer Data Master");
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPatientPayerData, 20);
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_disableAndActivePatientPayerDataButton.get(indexOfPatientPayerDataName));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_confirm, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_confirm),
                        "Clicked on confirm button to disable Patient payer data = <b>" + sUpdatedPatientPayerDataName + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPatientPayerData, 20);

                //Verify Disabled patient payer data is not present in Payer master form.
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPayerMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addPayerMaster),
                        "Add Payer master button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.label_addPayerForm, 20);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_FinanceChanges.label_addPayerForm),
                        "Add Payer form opened");
                Cls_Generic_Methods.customWait();
                for (WebElement ePatientPayerDataButton : oPage_FinanceChanges.list_patientPayerDataButton) {
                    String sPatientPayerDataButton = ePatientPayerDataButton.getText();
                    if (sPatientPayerDataButton.equalsIgnoreCase(sUpdatedPatientPayerDataName)) {
                        System.out.println(sPatientPayerDataButton);
                        System.out.println(sUpdatedPatientPayerDataName);
                        bDisabledPatientPayerButtonFound = true;
                    }
                }
                if (bDisabledPatientPayerButtonFound) {
                    m_assert.assertTrue(false, "Disabled Patient payer data Found in payer form <b> " + sUpdatedPatientPayerDataName + " </b> ");
                } else {
                    m_assert.assertTrue(true, "Disabled Patient payer data is not Preset in payer form <b> ");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_Close, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_Close);
                Cls_Generic_Methods.customWait();
            } catch (Exception e) {
                m_assert.assertFatal("Patient payer data is not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Activating Patient Payer Data Functionality")
    public void validateActivePatientPayerDataMaster() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        boolean bPatientPayerDataNameFoundInTable = false;
        boolean bPatientPayerDataDescriptionFoundInTable = false;
        boolean bActivatedPatientPayerButtonFound = false;
        int indexOfPatientPayerDataName = -1;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Patient Payer Data Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPatientPayerData, 3);

                //validate Patient payer data Name in table
                for (WebElement ePatientPayerDataName : oPage_FinanceChanges.list_PatientPayerDataName) {
                    if (ePatientPayerDataName.getText().equalsIgnoreCase(sUpdatedPatientPayerDataName)) {
                        bPatientPayerDataNameFoundInTable = true;
                        indexOfPatientPayerDataName = oPage_FinanceChanges.list_PatientPayerDataName.indexOf(ePatientPayerDataName);
                        break;
                    }
                }
                m_assert.assertTrue(bPatientPayerDataNameFoundInTable, "Patient payer data Name Found in table <b> " + sUpdatedPatientPayerDataName + " </b> ");

                //Validate description in table
                String sUpdatedPatientPayerDataDescriptionOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_PatientPayerDataDescription.get(indexOfPatientPayerDataName));
                if (sUpdatedPatientPayerDataDescriptionOnUI.equalsIgnoreCase(sUpdatedPatientPayerDataDescription)) {
                    bPatientPayerDataDescriptionFoundInTable = true;
                }
                m_assert.assertTrue(bPatientPayerDataDescriptionFoundInTable, "Updated Patient payer data Description Found in table <b> " + sUpdatedPatientPayerDataDescriptionOnUI + " </b> ");

                //Validate Activate Patient payer data functionality
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPatientPayerData, 20);
                String sButtonName = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_disableAndActivePatientPayerDataButton.get(indexOfPatientPayerDataName));
                if (sButtonName.equalsIgnoreCase("Active")) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_disableAndActivePatientPayerDataButton.get(indexOfPatientPayerDataName)), "Clicked on <b> Active </b> button");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPatientPayerData, 20);

                //Verify activated patient payer data present in Payer master form.
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPayerMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addPayerMaster),
                        "Add Payer master button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.label_addPayerForm, 20);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_FinanceChanges.label_addPayerForm),
                        "Add Payer form opened");
                Cls_Generic_Methods.customWait(4);
                for (WebElement eUpdatedPatientPayerDataButton : oPage_FinanceChanges.list_patientPayerDataButton) {
                    if (eUpdatedPatientPayerDataButton.getText().equalsIgnoreCase(sUpdatedPatientPayerDataName)) {
                        bActivatedPatientPayerButtonFound = true;
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(eUpdatedPatientPayerDataButton), "Clicked on updated patient payer data button = <b>" + sUpdatedPatientPayerDataName + "</b>");
                        break;
                    }
                }
                if (bActivatedPatientPayerButtonFound) {
                    m_assert.assertTrue(true, "updated Patient payer data Found in payer form <b> " + sUpdatedPatientPayerDataName + " </b> ");
                } else {
                    m_assert.assertTrue(false, "updated Patient payer data Found in payer form");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_Close, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_Close);
                Cls_Generic_Methods.customWait();
            } catch (Exception e) {
                m_assert.assertFatal("Patient payer data is not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }
}
