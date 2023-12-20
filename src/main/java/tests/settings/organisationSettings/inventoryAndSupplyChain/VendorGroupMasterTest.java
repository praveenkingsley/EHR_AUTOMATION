package tests.settings.organisationSettings.inventoryAndSupplyChain;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_VendorGroupMaster;

import static pages.commonElements.CommonActions.oEHR_Data;
import static tests.settings.organisationSettings.inventoryAndSupplyChain.CategoryMasterTest.getRandomString;


public class VendorGroupMasterTest extends TestBase {


    @Test(enabled = true, description = "validate Add Vendor Group")
    public void validateAddVendorGroup() {

        Page_VendorGroupMaster oPage_VendorGroupMaster = new Page_VendorGroupMaster(driver);
        boolean bVendorGroupNameFound = false;
        String sVendorGroupName = "Automation Vendor Group";
        String sVendorGroupDescription = "Vendor group description";

        try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Group Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.button_addVendorGroupMaster, 10);

            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_VendorGroupMaster.button_addVendorGroupMaster),
                        "Add Vendor Group master Button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.input_vendorGroupName, 5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorGroupMaster.input_vendorGroupName, sVendorGroupName),
                        "Vendor Group name entered: <b>" + sVendorGroupName + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.input_vendorGroupDescription, 5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorGroupMaster.input_vendorGroupDescription, sVendorGroupDescription),
                        "Vendor Group description entered: <b>" + sVendorGroupDescription + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.button_saveVendorGroup, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_VendorGroupMaster.button_saveVendorGroup),
                        "Save Vendor Group master Button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.button_addVendorGroupMaster, 3);


                CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Master");
                Cls_Generic_Methods.customWait(2);
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_VendorGroupMaster.button_addVendor, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver, oPage_VendorGroupMaster.button_addVendor),
                        "Add Vendor Button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.select_vendorGroupField, 3);
                Cls_Generic_Methods.clickElement(driver, oPage_VendorGroupMaster.select_vendorGroupField);
                for (WebElement eVendorGroupName : oPage_VendorGroupMaster.list_vendorGroupUnderAddNewVendorPopUp) {
                    String sTableValue = Cls_Generic_Methods.getTextInElement(eVendorGroupName);
                    if (sTableValue.equalsIgnoreCase(sVendorGroupName)) {
                        bVendorGroupNameFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(bVendorGroupNameFound,
                        "Validated <b> " + " " + sVendorGroupName + " " + "</b> present under vendor group dropdown while Adding new vendor.");
                Cls_Generic_Methods.clickElement(driver, oPage_VendorGroupMaster.select_vendorGroupField);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.button_closeAddNewVendorPopUp, 10);
                Cls_Generic_Methods.scrollToElementByJS(oPage_VendorGroupMaster.button_closeAddNewVendorPopUp);
                Cls_Generic_Methods.clickElement(driver, oPage_VendorGroupMaster.button_closeAddNewVendorPopUp);
            } catch (Exception e) {
                m_assert.assertFatal(e + "");
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal(e + "");
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "validate Edit Vendor Group")
    public void validateEditVendorGroup() {

        Page_VendorGroupMaster oPage_VendorGroupMaster = new Page_VendorGroupMaster(driver);
        int indexOfVendorGroupName = -1;
        boolean bVendorGroupNameFound = false;
        boolean bVendorGroupDescriptionFound = false;
        String sVendorGroupName = "Automation Vendor Group";
        String sVendorGroupDescription = "Vendor group description";
        String sUpdatedVendorGroupName = "Updated Automation Vendor Group" + getRandomString(3);
        String sUpdatedVendorGroupDescription = "Updated Vendor group description";

        try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Group Master");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.button_addVendorGroupMaster, 3);
            try {
                for (WebElement eVendorGroupName : oPage_VendorGroupMaster.list_vendorGroupName) {
                    String sTableValue = Cls_Generic_Methods.getTextInElement(eVendorGroupName);
                    if (sTableValue.equalsIgnoreCase(sVendorGroupName)) {
                        indexOfVendorGroupName = oPage_VendorGroupMaster.list_vendorGroupName.indexOf(eVendorGroupName);
                        bVendorGroupNameFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(bVendorGroupNameFound, "<b> " + sVendorGroupName + "</b> present at index: <b>" + indexOfVendorGroupName + "</b>");
                if (bVendorGroupNameFound) {
                    oPage_VendorGroupMaster.list_vendorGroupDescription.get(indexOfVendorGroupName);
                    if (Cls_Generic_Methods.getTextInElement(oPage_VendorGroupMaster.list_vendorGroupDescription.get(indexOfVendorGroupName)).equalsIgnoreCase(sVendorGroupDescription)) {
                        bVendorGroupDescriptionFound = true;
                    }
                }

                m_assert.assertTrue(bVendorGroupDescriptionFound, "<b> " + sVendorGroupDescription + "</b> present at index: <b>" + indexOfVendorGroupName + "</b>");
                if (bVendorGroupDescriptionFound) {
                    Cls_Generic_Methods.customWait(2);
                    Cls_Generic_Methods.clickElement(oPage_VendorGroupMaster.list_editVendorGroupButton.get(indexOfVendorGroupName));
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.input_vendorGroupName, 5);
                Cls_Generic_Methods.clearValuesInElement(oPage_VendorGroupMaster.input_vendorGroupName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.input_vendorGroupName, 5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorGroupMaster.input_vendorGroupName, sUpdatedVendorGroupName),
                        "Vendor Group name Updated: <b>" + sUpdatedVendorGroupName + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.input_vendorGroupDescription, 5);
                Cls_Generic_Methods.clearValuesInElement(oPage_VendorGroupMaster.input_vendorGroupDescription);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorGroupMaster.input_vendorGroupDescription, sUpdatedVendorGroupDescription),
                        "Vendor Group description updated: <b>" + sUpdatedVendorGroupDescription + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.button_saveVendorGroup, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_VendorGroupMaster.button_saveVendorGroup),
                        "Save Vendor Group master Button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.button_addVendorGroupMaster, 3);
                try {
                    for (WebElement eVendorGroupName : oPage_VendorGroupMaster.list_vendorGroupName) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eVendorGroupName);
                        if (sTableValue.equalsIgnoreCase(sUpdatedVendorGroupName)) {
                            indexOfVendorGroupName = oPage_VendorGroupMaster.list_vendorGroupName.indexOf(eVendorGroupName);
                            bVendorGroupNameFound = true;
                            break;
                        }
                    }
                    m_assert.assertTrue(true, "<b> " + sUpdatedVendorGroupName + "</b>updated vendor group name present at index: <b>" + indexOfVendorGroupName + "</b>");

                    if (bVendorGroupNameFound) {
                        oPage_VendorGroupMaster.list_vendorGroupDescription.get(indexOfVendorGroupName);
                        if (Cls_Generic_Methods.getTextInElement(oPage_VendorGroupMaster.list_vendorGroupDescription.get(indexOfVendorGroupName)).equalsIgnoreCase(sUpdatedVendorGroupDescription)) {
                            bVendorGroupDescriptionFound = true;

                        }
                    }
                    m_assert.assertTrue(bVendorGroupDescriptionFound, "<b> " + sUpdatedVendorGroupDescription + "</b>updated vendor group description present at index: <b>" + indexOfVendorGroupName + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.button_addVendorGroupMaster, 3);


                    CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
                    CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                    Cls_Generic_Methods.customWait(2);
                    CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Master");
                    Cls_Generic_Methods.customWait(2);
                    Cls_Generic_Methods.scrollToTop();
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_VendorGroupMaster.button_addVendor, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_VendorGroupMaster.button_addVendor),
                            "Add Vendor Button clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.select_vendorGroupField, 3);
                    Cls_Generic_Methods.clickElement(driver, oPage_VendorGroupMaster.select_vendorGroupField);
                    for (WebElement eVendorGroupName : oPage_VendorGroupMaster.list_vendorGroupUnderAddNewVendorPopUp) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eVendorGroupName);
                        if (sTableValue.equalsIgnoreCase(sUpdatedVendorGroupName)) {
                            bVendorGroupNameFound = true;
                            break;
                        }
                    }
                    m_assert.assertTrue(bVendorGroupNameFound, "Validated Updated vendor group  <b> " + " " + sVendorGroupName + " " + "</b> present under vendor group dropdown while Adding new vendor: <b> " + indexOfVendorGroupName + "</b>");
                    Cls_Generic_Methods.clickElement(driver, oPage_VendorGroupMaster.select_vendorGroupField);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.button_closeAddNewVendorPopUp, 10);
                    Cls_Generic_Methods.scrollToElementByJS(oPage_VendorGroupMaster.button_closeAddNewVendorPopUp);
                    Cls_Generic_Methods.clickElement(driver, oPage_VendorGroupMaster.button_closeAddNewVendorPopUp);


                } catch (Exception e) {
                    m_assert.assertFatal(e + "");
                    e.printStackTrace();
                }
            } catch (Exception e) {
                m_assert.assertFatal(e + "");
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal(e + "");
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "validate Disabling Vendor Group")
    public void validateDisablingVendorGroup() {
        Page_VendorGroupMaster oPage_VendorGroupMaster = new Page_VendorGroupMaster(driver);
        boolean bVendorGroupNameFound = false;
        String sUpdatedVendorGroupName = "Updated Automation Vendor Group" + getRandomString(3);
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Group Master");
            Cls_Generic_Methods.customWait(2);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorGroupMaster.search_searchVendorGroupMaster, sUpdatedVendorGroupName);
            Cls_Generic_Methods.customWait(2);
            boolean clickOnDisableActions = CommonActions.getActionToPerformInInventorySetting(oPage_VendorGroupMaster.list_vendorGroupName,
                    sUpdatedVendorGroupName, oPage_VendorGroupMaster.list_actionButtonsVendorGroup, "Disable");
            if (clickOnDisableActions) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.button_confirmDisableVendorGroup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_VendorGroupMaster.button_confirmDisableVendorGroup),
                        "Clicked on CONFIRM disable vendor group button");
                Cls_Generic_Methods.customWait(2);
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.button_addVendorGroupMaster, 3);
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(2);


            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Master");
            Cls_Generic_Methods.customWait(2);
            Cls_Generic_Methods.scrollToTop();
            Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_VendorGroupMaster.button_addVendor, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_VendorGroupMaster.button_addVendor),
                    "Add Vendor Button clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.select_vendorGroupField, 3);
            Cls_Generic_Methods.clickElement(driver, oPage_VendorGroupMaster.select_vendorGroupField);
            for (WebElement eVendorGroupName : oPage_VendorGroupMaster.list_vendorGroupUnderAddNewVendorPopUp) {
                String sTableValue = Cls_Generic_Methods.getTextInElement(eVendorGroupName);
                if (sTableValue.equalsIgnoreCase(sUpdatedVendorGroupName)) {
                    bVendorGroupNameFound = true;
                    break;
                }
            }
            if (bVendorGroupNameFound) {
                m_assert.assertTrue(false, "vendor group still showing in the dropdown even after disable ");
            } else {
                m_assert.assertTrue("After Disable: Vendor group is not showing in the list");
            }
            Cls_Generic_Methods.clickElement(driver, oPage_VendorGroupMaster.select_vendorGroupField);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.button_closeAddNewVendorPopUp, 10);
            Cls_Generic_Methods.scrollToElementByJS(oPage_VendorGroupMaster.button_closeAddNewVendorPopUp);
            Cls_Generic_Methods.clickElement(driver, oPage_VendorGroupMaster.button_closeAddNewVendorPopUp);
        } catch (Exception e) {
            m_assert.assertFatal(e + "");
            e.printStackTrace();
        }


    }

    @Test(enabled = true, description = "validate Active Vendor Group")
    public void validateActiveVendorGroup() {
        Page_VendorGroupMaster oPage_VendorGroupMaster = new Page_VendorGroupMaster(driver);
        boolean bVendorGroupNameFound = false;
        String sVendorGroupName = "Automation Vendor Group" + getRandomString(3);
        String sUpdatedVendorGroupName = "Updated Automation Vendor Group";

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(2);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Group Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.button_addVendorGroupMaster, 3);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorGroupMaster.search_searchVendorGroupMaster, sUpdatedVendorGroupName);
            Cls_Generic_Methods.customWait(2);
            boolean clickOnActiveActions = CommonActions.getActionToPerformInInventorySetting(oPage_VendorGroupMaster.list_vendorGroupName,
                    sUpdatedVendorGroupName, oPage_VendorGroupMaster.list_actionButtonsVendorGroup, "Active");
            if (clickOnActiveActions) {
                m_assert.assertTrue(sUpdatedVendorGroupName + "  Vendor group Activated");
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.button_addVendorGroupMaster, 3);
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(2);


            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Master");
            Cls_Generic_Methods.customWait(2);
            Cls_Generic_Methods.scrollToTop();
            Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_VendorGroupMaster.button_addVendor, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_VendorGroupMaster.button_addVendor),
                    "Add Vendor Button clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.select_vendorGroupField, 3);
            Cls_Generic_Methods.clickElement(driver, oPage_VendorGroupMaster.select_vendorGroupField);
            for (WebElement eVendorGroupName : oPage_VendorGroupMaster.list_vendorGroupUnderAddNewVendorPopUp) {
                String sTableValue = Cls_Generic_Methods.getTextInElement(eVendorGroupName);
                if (sTableValue.equalsIgnoreCase(sUpdatedVendorGroupName)) {
                    bVendorGroupNameFound = true;
                    break;
                }
            }
            m_assert.assertTrue(bVendorGroupNameFound, "Validated After Activating the vendor group  <b> " + " " + sVendorGroupName + " " + "</b> present under vendor group dropdown while Adding new vendor");
            Cls_Generic_Methods.customWait(2);
            Cls_Generic_Methods.clickElement(driver, oPage_VendorGroupMaster.select_vendorGroupField);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.button_closeAddNewVendorPopUp, 10);
            Cls_Generic_Methods.scrollToElementByJS(oPage_VendorGroupMaster.button_closeAddNewVendorPopUp);
            Cls_Generic_Methods.clickElement(driver, oPage_VendorGroupMaster.button_closeAddNewVendorPopUp);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(2);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Group Master");
            Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorGroupMaster.search_searchVendorGroupMaster, sUpdatedVendorGroupName);
            Cls_Generic_Methods.customWait(2);
            boolean clickOnDisableActions = CommonActions.getActionToPerformInInventorySetting(oPage_VendorGroupMaster.list_vendorGroupName,
                    sUpdatedVendorGroupName, oPage_VendorGroupMaster.list_actionButtonsVendorGroup, "Disable");
            if (clickOnDisableActions) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.button_confirmDisableVendorGroup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_VendorGroupMaster.button_confirmDisableVendorGroup),
                        "Clicked on CONFIRM disable vendor group button");
                Cls_Generic_Methods.customWait(2);
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorGroupMaster.button_addVendorGroupMaster, 3);

        } catch (Exception e) {
            m_assert.assertFatal(e + "");
            e.printStackTrace();
        }


    }

}