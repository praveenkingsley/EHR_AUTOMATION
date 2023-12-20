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

public class PanelMasterTest extends TestBase {
    EHR_Data oEHR_Data = new EHR_Data();
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date date = new Date();
    String date1 = dateFormat.format(date);
    String sPanelName = "TestAutoPanelName" + date1;
    String sPanelDescription = "TestAutoPanelDescription" + date1;
    String sUpdatedPanelName = "UpdatedTestAutoPanelName" + date1;
    String sUpdatedPanelDescription = "UpdatedTestAutoPanelDescription" + date1;

    @Test(enabled = true, description = "Validate Adding New Panel")
    public void validateAddingNewPanelFromMaster() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectFacility("TST");
            String sContactGroupName = "Panel";
            boolean bPayerTypeMasterFound = false;
            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Panel Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPanel, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addPanel),
                        "Add Panel button clicked ");

                //adding new Panel
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.text_addPanelLabel, 20);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_FinanceChanges.text_addPanelLabel),
                        "Add Panel pop opened");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_panelName, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_panelName, sPanelName), "Entered Panel name  =  <b> " + sPanelName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_panelDescription, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_panelDescription, sPanelDescription), "Entered Panel Description  =  <b> " + sPanelDescription + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_saveChanges, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_saveChanges),
                        "Save changes button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addTPA, 20);
                //Verify newly created panel in Payer master form.
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
                Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactGroup, sContactGroupName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.field_payerTypeMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.field_payerTypeMaster),
                        "clicked on payer type master field");
                Cls_Generic_Methods.customWait();
                for (WebElement ePayerTypeMasterList : oPage_FinanceChanges.list_payerTypeMaster) {
                    if (ePayerTypeMasterList.getText().equalsIgnoreCase(sPanelName)) {
                        bPayerTypeMasterFound = true;
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(ePayerTypeMasterList), "Clicked on payer type master = <b>" + sPanelName + "</b>");
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }
                if (bPayerTypeMasterFound) {
                    m_assert.assertTrue(true, "payer type master Found in payer form <b> " + sPanelName + " </b> ");
                } else {
                    m_assert.assertTrue(false, "incorrect payer type master Found in payer form");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_Close, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_Close);
                Cls_Generic_Methods.customWait();
            } catch (Exception e) {
                m_assert.assertFatal("Panel not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Edit and Disable Panel Functionality")
    public void validateEditAndDisablePanelMaster() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        boolean bPanelNameFoundInTable = false;
        boolean bPanleDescriptionFoundInTable = false;
        boolean bPayerTypeMasterFound = false;
        String sContactGroupName = "Panel";
        int indexOfPanelName = -1;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Panel Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addTPA, 20);

                //validate Panel name in table
                for (WebElement ePanelName : oPage_FinanceChanges.list_panelName) {
                    if (ePanelName.getText().equalsIgnoreCase(sPanelName)) {
                        bPanelNameFoundInTable = true;
                        indexOfPanelName = oPage_FinanceChanges.list_panelName.indexOf(ePanelName);
                        break;
                    }
                }
                m_assert.assertTrue(bPanelNameFoundInTable, "Panel Name Found in table <b> " + sPanelName + " </b> ");
                Cls_Generic_Methods.customWait();
                //Validate Panel description
                String sPanelDescriptionOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_panelDescription.get(indexOfPanelName));
                if (sPanelDescriptionOnUI.equalsIgnoreCase(sPanelDescription)) {
                    bPanleDescriptionFoundInTable = true;
                }
                m_assert.assertTrue(bPanleDescriptionFoundInTable, "Panel Description Found in table <b> " + sPanelDescriptionOnUI + " </b> ");

                //validate edit Panel functionality
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_editPanelButton.get(indexOfPanelName));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_panelName, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.inputField_panelName), "Cleared values in Panel name field");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_panelName, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_panelName, sUpdatedPanelName), "Entered Panel name  =  <b> " + sUpdatedPanelName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_panelDescription, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.inputField_panelDescription), "Cleared values in panel description field");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_panelDescription, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_panelDescription, sUpdatedPanelDescription), "Entered panel Description  =  <b> " + sUpdatedPanelDescription + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_saveChanges, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_saveChanges),
                        "Save changes button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addCorporate, 20);

                //validate Updated Panel Name in table
                for (WebElement ePanelName : oPage_FinanceChanges.list_panelName) {
                    if (ePanelName.getText().equalsIgnoreCase(sUpdatedPanelName)) {
                        bPanelNameFoundInTable = true;
                        indexOfPanelName = oPage_FinanceChanges.list_panelName.indexOf(ePanelName);
                        break;
                    }
                }
                m_assert.assertTrue(bPanelNameFoundInTable, "Panel Name Found in table <b> " + sUpdatedPanelName + " </b> ");

                //Validate updated Panel description in table
                String sUpdatedPanelDescriptionOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_panelDescription.get(indexOfPanelName));
                if (sUpdatedPanelDescriptionOnUI.equalsIgnoreCase(sUpdatedPanelDescription)) {
                    bPanleDescriptionFoundInTable = true;
                }
                m_assert.assertTrue(bPanleDescriptionFoundInTable, "Updated Panel Description Found in table <b> " + sUpdatedPanelDescriptionOnUI + " </b> ");

                //Verify updated  panel in Payer master form.
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
                Cls_Generic_Methods.customWait(5);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactGroup, sContactGroupName);
                Cls_Generic_Methods.customWait(3);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.field_payerTypeMaster),
                        "clicked on payer type master field");
                Cls_Generic_Methods.customWait(3);
                for (WebElement ePayerTypeMasterList : oPage_FinanceChanges.list_payerTypeMaster) {
                    if (ePayerTypeMasterList.getText().equalsIgnoreCase(sUpdatedPanelName)) {
                        bPayerTypeMasterFound = true;
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(ePayerTypeMasterList), "Clicked on updated payer type master = <b>" + sUpdatedPanelName + "</b>");
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }
                if (bPayerTypeMasterFound) {
                    m_assert.assertTrue(true, "Updated payer type master Found in payer form = <b> " + sUpdatedPanelName + " </b> ");
                } else {
                    m_assert.assertTrue(false, "incorrect payer type master Found in payer form ");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_Close, 3);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_Close);

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Panel Master");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.scrollToTop();
                //Validate Disable Panel functionality
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPanel, 20);
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_disableAndActivePanelButton.get(indexOfPanelName));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_confirm, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_confirm),
                        "Confirm button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPanel, 20);

                //Verify Disabled panel is not present in Payer master form.
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
                Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactGroup, sContactGroupName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.field_payerTypeMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.field_payerTypeMaster),
                        "clicked on payer type master field");
                Cls_Generic_Methods.customWait(4);
                for (WebElement ePayerTypeMasterList : oPage_FinanceChanges.list_payerTypeMaster) {
                    String sDisabledPanelName = ePayerTypeMasterList.getText();
                    if (sDisabledPanelName.equals(sUpdatedPanelName)) {
                        m_assert.assertTrue(false, "Disabled panel Found in payer form <b> " + sUpdatedPanelName + " </b> ");
                    } else {
                        m_assert.assertTrue(true, "Disabled panel = <b> " + sUpdatedPanelName + " </b> is not Preset in the payer type master dropdown " + "current Panel name inside the payer type master dropdown =<b>  " + sDisabledPanelName + " </b>");
                    }
                }
                Cls_Generic_Methods.driverRefresh();
            } catch (Exception e) {
                m_assert.assertFatal("Panel is not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Activating Panel Functionality")
    public void validateActivePanelMaster() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        boolean bPanelNameFoundInTable = false;
        boolean bPanelDescriptionFoundInTable = false;
        boolean bPayerTypeMasterFound = false;
        String sContactGroupName = "Panel";
        int indexOfPanelName = -1;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Panel Master");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.scrollToTop();
                //validate Panel Name in table
                for (WebElement ePanelName : oPage_FinanceChanges.list_panelName) {
                    if (ePanelName.getText().equalsIgnoreCase(sUpdatedPanelName)) {
                        bPanelNameFoundInTable = true;
                        indexOfPanelName = oPage_FinanceChanges.list_panelName.indexOf(ePanelName);
                        break;
                    }
                }
                m_assert.assertTrue(bPanelNameFoundInTable, "Panel Name Found in table <b> " + sUpdatedPanelName + " </b> ");
                Cls_Generic_Methods.customWait(3);
                //Validate description in table
                String sUpdatedPanelDescriptionOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_panelDescription.get(indexOfPanelName));
                if (sUpdatedPanelDescriptionOnUI.equalsIgnoreCase(sUpdatedPanelDescription)) {
                    bPanelDescriptionFoundInTable = true;
                }
                m_assert.assertTrue(bPanelDescriptionFoundInTable, "Updated Panel Description Found in table <b> " + sUpdatedPanelDescriptionOnUI + " </b> ");
                Cls_Generic_Methods.customWait(3);
                //Validate Activate Panel functionality
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPanel, 20);
                String sButtonName = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_disableAndActivePanelButton.get(indexOfPanelName));
                if (sButtonName.equalsIgnoreCase("Active")) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_disableAndActivePanelButton.get(indexOfPanelName)), "Clicked on <b> Active </b> button");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPanel, 20);
                //Verify Activated panel present in Payer master form.
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
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.select_contactGroup, 20);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactGroup, sContactGroupName);
                Cls_Generic_Methods.customWait(3);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.field_payerTypeMaster),
                        "clicked on payer type master field");
                Cls_Generic_Methods.customWait(4);
                for (WebElement ePayerTypeMasterList : oPage_FinanceChanges.list_payerTypeMaster) {
                    if (ePayerTypeMasterList.getText().equalsIgnoreCase(sUpdatedPanelName)) {
                        bPayerTypeMasterFound = true;
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(ePayerTypeMasterList), "Clicked on payer type master = <b>" + sUpdatedPanelName + "</b>");
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }
                if (bPayerTypeMasterFound) {
                    m_assert.assertTrue(true, "payer type master Found in payer form = <b> " + sUpdatedPanelName + " </b> ");
                } else {
                    m_assert.assertTrue(false, "incorrect payer type master Found in payer form ");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_Close, 3);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_Close);

            } catch (Exception e) {
                m_assert.assertFatal("Panel is not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }
}