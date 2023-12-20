package tests.settings.organisationSettings.inventoryAndSupplyChain;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import data.settingsData.OrganisationSettings_Data;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_OtherCharges;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;


import static tests.settings.organisationSettings.inventoryAndSupplyChain.CategoryMasterTest.getRandomString;


public class OtherChargesTest extends TestBase {


    EHR_Data oEHR_Data = new EHR_Data();

    String otherChargesName = OrganisationSettings_Data.sOtherCharges_NAME + getRandomString(3).toUpperCase();

    String editOtherChargesName = otherChargesName + "_EDIT";


    @Test(enabled = true, description = "Add and validate other charges")
    public void validateAddOtherCharges() {
        Page_OtherCharges oPage_OtherCharges = new Page_OtherCharges(driver);
        boolean chargesNameFound = false;
        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Other Charges");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtherCharges.button_addOtherCharges, 4);
            try {
                Cls_Generic_Methods.clickElementByJS(driver, oPage_OtherCharges.button_addOtherCharges);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtherCharges.input_otherChargesName, 3);

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OtherCharges.input_otherChargesName, otherChargesName),
                        "Other charges name: <b> " + otherChargesName + "</b> ");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OtherCharges.input_otherChargesDescription, OrganisationSettings_Data.sOtherCharges_DESCRIPTION),
                        "Other charges description: <b> " + OrganisationSettings_Data.sOtherCharges_DESCRIPTION + "</b> ");

                Cls_Generic_Methods.clickElement(oPage_OtherCharges.button_saveChanges);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtherCharges.button_addOtherCharges, 4);

                for (WebElement charges : oPage_OtherCharges.list_chargesNameInTable) {
                    if (Cls_Generic_Methods.isElementDisplayed(charges)) {
                        if (Cls_Generic_Methods.getTextInElement(charges).equalsIgnoreCase(otherChargesName)) {
                            chargesNameFound = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(chargesNameFound, "Other charge created successfully");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal(" " + e);
            }


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }
    }

    @Test(enabled = true, description = "Validate the added other charges in optical store")
    public void validateVendorRateInStore() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_OtherCharges oPage_OtherCharges = new Page_OtherCharges(driver);
        boolean otherChargesFoundInSRN = false;

        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Other Charges");

            try {

                boolean storeSelectedOnApp = CommonActions.selectStoreOnApp(OrganisationSettings_Data.sOtherCharges_STORE);
                if (storeSelectedOnApp) {
                    m_assert.assertTrue(Cls_Generic_Methods.switchToOtherTab(), "Switching to other Tab");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 3);
                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                    Cls_Generic_Methods.customWait(1);

                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SRN");
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.clickElement(oPage_OtherCharges.button_salesOrderNew);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtherCharges.dropdown_otherCharges, 4);
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_OtherCharges.dropdown_otherCharges,otherChargesName);
                    Cls_Generic_Methods.customWait();
                    if (Cls_Generic_Methods.getSelectedValue(oPage_OtherCharges.dropdown_otherCharges).equalsIgnoreCase(otherChargesName)) {
                        otherChargesFoundInSRN = true;

                    }

                 /*   for (WebElement otherCharges : oPage_OtherCharges.list_otherCharges) {
                        if (Cls_Generic_Methods.isElementDisplayed(otherCharges)) {
                            if (Cls_Generic_Methods.getTextInElement(otherCharges).equalsIgnoreCase(otherChargesName)) {
                                otherChargesFoundInSRN = true;
                                break;
                            }
                        }
                    }*/

                    m_assert.assertTrue(otherChargesFoundInSRN, "Other charge found in srn store");
                    Cls_Generic_Methods.clickElement(driver, oPage_OtherCharges.button_closeSRNTemplate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtherCharges.button_addOtherCharges, 4);
                    Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

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

    @Test(enabled = true, description = "Edit and validate other charges")
    public void validateEditOtherCharges() {
        Page_OtherCharges oPage_OtherCharges = new Page_OtherCharges(driver);
        boolean chargesNameFound = false;

        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Other Charges");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtherCharges.button_addOtherCharges, 4);

            try {

                boolean clickOnEditActions = CommonActions.getActionToPerformInInventorySetting(oPage_OtherCharges.list_chargesNameInTable,
                        otherChargesName, oPage_OtherCharges.list_chargesActionsInTable, "Edit");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtherCharges.input_otherChargesName, 2);

                if (clickOnEditActions) {

                    Cls_Generic_Methods.clearValuesInElement(oPage_OtherCharges.input_otherChargesName);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OtherCharges.input_otherChargesName, editOtherChargesName),
                            "Other charges edited name: <b> " + editOtherChargesName + "</b> ");

                    Cls_Generic_Methods.clearValuesInElement(oPage_OtherCharges.input_otherChargesDescription);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OtherCharges.input_otherChargesDescription, OrganisationSettings_Data.sOtherCharges_EDIT_DESCRIPTION),
                            "Other charges edit description: <b> " + OrganisationSettings_Data.sOtherCharges_EDIT_DESCRIPTION + "</b> ");

                    Cls_Generic_Methods.clickElement(oPage_OtherCharges.button_saveChanges);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtherCharges.button_addOtherCharges, 4);

                    for (WebElement charges : oPage_OtherCharges.list_chargesNameInTable) {
                        if (Cls_Generic_Methods.isElementDisplayed(charges)) {
                            if (Cls_Generic_Methods.getTextInElement(charges).equalsIgnoreCase(editOtherChargesName)) {
                                chargesNameFound = true;
                                break;
                            }
                        }
                    }
                    m_assert.assertTrue(chargesNameFound, "Other charge Edited successfully");
                } else {
                    m_assert.assertTrue(clickOnEditActions, "Edit Button not clicked in Other Charges List");
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

    @Test(enabled = true, description = "Validating Disable Other Charges Functionality")
    public void validateDisableOtherCharges() {

        Page_OtherCharges oPage_OtherCharges = new Page_OtherCharges(driver);
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);


        boolean chargesNameDisableFound = true;
        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Other Charges");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtherCharges.button_addOtherCharges, 4);

            try {

                boolean clickOnDisableActions = CommonActions.getActionToPerformInInventorySetting(oPage_OtherCharges.list_chargesNameInTable,
                        editOtherChargesName, oPage_OtherCharges.list_chargesActionsInTable, "Disable");

                if (clickOnDisableActions) {

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.alert_disableConfirmAlert, 2);

                    //Validating Disable Store Functionality

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetUp.text_disableConfirmAlertTitle),
                            "Disable Confirm Title is Present");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_StoreSetUp.text_disableConfirmationAlertMessage).contains("Are you sure, you want disable " + editOtherChargesName + "?"),
                            "Confirmation Message Is present");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.button_disableAlertConfirmButton),
                            "Confirm Button Is Clicked in Confirm Template");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtherCharges.button_addOtherCharges, 4);

                    boolean storeSelectedOnApp = CommonActions.selectStoreOnApp(OrganisationSettings_Data.sOtherCharges_STORE);

                    if (storeSelectedOnApp) {
                        m_assert.assertTrue(Cls_Generic_Methods.switchToOtherTab(), "Switching to other opened Tab");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 3);
                        Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                        Cls_Generic_Methods.customWait(1);

                        CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SRN");
                        Cls_Generic_Methods.clickElement(oPage_OtherCharges.button_salesOrderNew);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtherCharges.dropdown_otherCharges, 4);
                        Cls_Generic_Methods.selectElementByVisibleText(oPage_OtherCharges.dropdown_otherCharges,editOtherChargesName);
                        Cls_Generic_Methods.customWait();
                        if (Cls_Generic_Methods.getSelectedValue(oPage_OtherCharges.dropdown_otherCharges).equalsIgnoreCase(otherChargesName)) {
                            chargesNameDisableFound = true;

                        }


                        m_assert.assertTrue(chargesNameDisableFound, "Other charge found in Disable Successfully");
                        Cls_Generic_Methods.clickElement(driver, oPage_OtherCharges.button_closeSRNTemplate);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtherCharges.button_addOtherCharges, 4);
                        Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                    }
                    else {
                        m_assert.assertTrue(storeSelectedOnApp, "Either Store is not available in list or store not clicked");
                    }


                } else {
                    m_assert.assertTrue(clickOnDisableActions, "Disable Button not clicked in Other Charges List");
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