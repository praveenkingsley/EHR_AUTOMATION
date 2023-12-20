package tests.settings.organisationSettings.inventoryAndSupplyChain;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_CategoryMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_DispensingUnitMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;


import static pages.commonElements.CommonActions.oEHR_Data;
import static tests.settings.organisationSettings.inventoryAndSupplyChain.CategoryMasterTest.getRandomString;


public class DispensingUnitMasterTest extends TestBase {


    String newDispensingUnitName = "Test_" + getRandomString(3).toUpperCase();

    String updateDispensingUnitName = newDispensingUnitName + "_Update";

    String categoryName = "Medication";


    @Test(enabled = true, description = "Validating Add Dispensing Unit Functionality")
    public void validateAddDispensingUnitFunctionality() {

        Page_DispensingUnitMaster oPage_DispensingUnitMaster = new Page_DispensingUnitMaster(driver);
        Page_CategoryMaster oPage_CategoryMaster = new Page_CategoryMaster(driver);

        boolean bDispensingUnitFoundInCategoryMaster = false;

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {

                // Select Sub Category Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Dispensing Unit Master");

                // Opening Add Dispensing Unit Template ,Verifying Template

                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_DispensingUnitMaster.header_manageInventoryDispensingUnit),
                        "Verifying Dispensing Unit Master Header");
                Cls_Generic_Methods.scrollToTop();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_DispensingUnitMaster.button_addDispensingUnit),
                        " Add Dispensing Unit Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DispensingUnitMaster.header_addDispensingUnitTemplateHeader, 3);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_DispensingUnitMaster.label_dispensingUnitInformationLabel),
                        "Dispensing Unit Information Label is present");
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_DispensingUnitMaster.input_dispensingUnitName),
                        "Name Field is present");


                // Verifying Required Error In Add Dispensing Unit

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CategoryMaster.button_saveChanges),
                        "Save Changes Button Clicked without filling required field");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DispensingUnitMaster.label_dispensingUnitNameRequiredError, 2);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_DispensingUnitMaster.label_dispensingUnitNameRequiredError),
                        "Required Error Message Displayed For Name Field");

                // Entering all required Field and Creating New Dispensing Unit


                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_DispensingUnitMaster.input_dispensingUnitName, newDispensingUnitName),
                        " Dispensing Unit Name Entered as :<b>" + newDispensingUnitName + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CategoryMaster.button_saveChanges),
                        " Save Changes button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DispensingUnitMaster.button_addDispensingUnit, 3);


                // Validation Newly Created Dispensing Unit in Dispensing Unit List

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_DispensingUnitMaster.input_searchDispensingUnitName, newDispensingUnitName),
                        " Newly Created dispensing Unit entered in search box as : <b> " + newDispensingUnitName + "</b>");
                Cls_Generic_Methods.customWait();

                for (WebElement dispensingUnitName : oPage_DispensingUnitMaster.list_dispensingUnitNameInDispensingUnitMaster) {

                    if (Cls_Generic_Methods.isElementDisplayed(dispensingUnitName)) {

                        if (Cls_Generic_Methods.getTextInElement(dispensingUnitName).equalsIgnoreCase(newDispensingUnitName)) {
                            bDispensingUnitFoundInCategoryMaster = true;
                            break;
                        }
                    }
                }

                m_assert.assertTrue(bDispensingUnitFoundInCategoryMaster,
                        " New Dispensing Unit Added Successfully In Dispensing Unit Master List");


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validating Search and Edit Dispensing Unit Functionality")
    public void validateSearchAndEditDispensingUnitFunctionality() {

        Page_DispensingUnitMaster oPage_DispensingUnitMaster = new Page_DispensingUnitMaster(driver);
        Page_CategoryMaster oPage_CategoryMaster = new Page_CategoryMaster(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

        boolean bDispensingUnitFoundInCategoryMaster = false;


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {

                // Select Sub Category Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Dispensing Unit Master");

                // Validation Newly Created Sub Category Using Search In Sub Category Page List

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_DispensingUnitMaster.input_searchDispensingUnitName, newDispensingUnitName),
                        " Dispensing Unit Name entered in search box as : <b> " + newDispensingUnitName + "</b>");

                Cls_Generic_Methods.customWait();
                boolean clickOnEditActions = CommonActions.getActionToPerformInInventorySetting(oPage_DispensingUnitMaster.list_dispensingUnitNameInDispensingUnitMaster,
                        newDispensingUnitName, oPage_DispensingUnitMaster.list_dispensingUnitActionsForDispensingUnitName, "Edit");

                if (clickOnEditActions) {

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DispensingUnitMaster.header_editDispensingUnitTemplateHeader, 3);

                    // Verifying Edit Dispensing Unit Template

                    m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_DispensingUnitMaster.header_editDispensingUnitTemplateHeader),
                            "Update Dispensing Unit Header Displayed");
                    m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_DispensingUnitMaster.label_dispensingUnitInformationLabel),
                            "Dispensing Unit Information Label Displayed");
                    m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_DispensingUnitMaster.input_dispensingUnitName)
                            , " Clear Name Field Before Enter New Name");
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_DispensingUnitMaster.input_dispensingUnitName, updateDispensingUnitName),
                            "Updated Dispensing Name Entered as : <b>" + updateDispensingUnitName + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CategoryMaster.button_saveChanges),
                            " Save Changes button clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DispensingUnitMaster.button_addDispensingUnit, 3);

                    clickOnEditActions = CommonActions.getActionToPerformInInventorySetting(oPage_DispensingUnitMaster.list_dispensingUnitNameInDispensingUnitMaster,
                            updateDispensingUnitName, oPage_DispensingUnitMaster.list_dispensingUnitActionsForDispensingUnitName, "Edit");

                    if (clickOnEditActions) {

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DispensingUnitMaster.header_editDispensingUnitTemplateHeader, 3);
                        m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_DispensingUnitMaster.input_dispensingUnitName, "value").contains(updateDispensingUnitName),
                                "Verifying Updated Dispensing Unit Name In Edit Templated ");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                                "Edit Dispensing Unit Template Closed , Dispensing unit updated successfully");

                    }

                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_DispensingUnitMaster.input_searchDispensingUnitName, updateDispensingUnitName),
                            " Updated dispensing Unit entered in search box as : <b> " + updateDispensingUnitName + "</b>");

                    Cls_Generic_Methods.customWait();

                    for (WebElement dispensingUnitName : oPage_DispensingUnitMaster.list_dispensingUnitNameInDispensingUnitMaster) {

                        if (Cls_Generic_Methods.isElementDisplayed(dispensingUnitName)) {

                            if (Cls_Generic_Methods.getTextInElement(dispensingUnitName).equalsIgnoreCase(updateDispensingUnitName)) {
                                bDispensingUnitFoundInCategoryMaster = true;
                                break;
                            }
                        }
                    }

                    m_assert.assertTrue(bDispensingUnitFoundInCategoryMaster,
                            " Dispensing Unit Updated Successfully Verifying In Dispensing Unit List ");
                } else {
                    m_assert.assertTrue(clickOnEditActions, "Edit Button is not clicked in Dispensing Unit Master");
                }


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = " Validating Dispensing Unit In Category Master")
    public void validateDispensingUnitInCategoryMaster() {

        Page_CategoryMaster oPage_CategoryMaster = new Page_CategoryMaster(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

        boolean bDispensingUnitFoundInCategoryMaster = false;

        try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {
                // Selection Category Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Category Master");

                // Clicking on Link Dispensing Unit for category

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_searchByCategoryName, categoryName),
                        "Category name enter in search box as :<b>" + categoryName + "</b>");
                Cls_Generic_Methods.customWait(1);

                boolean clickOnLinkDispensingUnitActions = CommonActions.getActionToPerformInInventorySetting(oPage_CategoryMaster.list_categoryNameForCategoryMaster,
                        categoryName, oPage_CategoryMaster.list_categoryActionsForCategoryName, "Link Dispensing Units");

                if (clickOnLinkDispensingUnitActions) {

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.header_titleLinkExistingDispensingUnit, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CategoryMaster.input_selectStoreInLinkExistingStore),
                            "Input select dispensing unit clicked");
                    Cls_Generic_Methods.customWait(2);
                    for (WebElement dispensingUnit : oPage_CategoryMaster.list_selectDispensingUnitsList) {
                        if (Cls_Generic_Methods.getTextInElement(dispensingUnit).contains(updateDispensingUnitName)) {
                            bDispensingUnitFoundInCategoryMaster = true;
                            break;
                        }
                    }

                    m_assert.assertTrue(bDispensingUnitFoundInCategoryMaster, "Dispensing Unit found in Category master as :<b> " + updateDispensingUnitName + " </b> for category " + categoryName);
                    Cls_Generic_Methods.clickElement(oPage_CategoryMaster.input_selectStoreInLinkExistingStore);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_CommonElements.button_closeTemplateWithoutSaving),
                            "Close button clicked for link dispensing unit template");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_addCategory, 3);

                } else {
                    m_assert.assertTrue(clickOnLinkDispensingUnitActions, " Link Dispensing unit button is  clicked ");
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = " Validating Disable Functionality ")
    public void validateDisableDispensingUnit() {

        Page_CategoryMaster oPage_CategoryMaster = new Page_CategoryMaster(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_DispensingUnitMaster oPage_DispensingUnitMaster = new Page_DispensingUnitMaster(driver);
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);


        boolean bDispensingUnitDisableInCategoryMaster = true;

        try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {
                // Select Sub Category Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Dispensing Unit Master");

                // Validation Newly Created Sub Category Using Search In Sub Category Page List

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_DispensingUnitMaster.input_searchDispensingUnitName, updateDispensingUnitName),
                        " Dispensing Unit Name entered in search box as : <b> " + updateDispensingUnitName + "</b>");

                Cls_Generic_Methods.customWait();

                boolean clickOnDisableActions = CommonActions.getActionToPerformInInventorySetting(oPage_DispensingUnitMaster.list_dispensingUnitNameInDispensingUnitMaster,
                        updateDispensingUnitName, oPage_DispensingUnitMaster.list_dispensingUnitActionsForDispensingUnitName, "Disable");

                if (clickOnDisableActions) {

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetUp.text_disableConfirmAlertTitle),
                            "Disable Confirm Title is Present");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_StoreSetUp.text_disableConfirmationAlertMessage).contains("Are you sure, you want disable " + updateDispensingUnitName + "?"),
                            "Confirmation Message Is present");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.button_disableAlertConfirmButton),
                            "Confirm button clicked in dispensing unit");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_addCategory, 2);


                    CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                    CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Category Master");

                    Cls_Generic_Methods.waitForPageLoad(driver , 3);

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_searchByCategoryName, categoryName),
                            "Category name enter in search box as :<b>" + categoryName + "</b>");
                    Cls_Generic_Methods.customWait(1);

                    boolean clickOnLinkDispensingUnitActions = CommonActions.getActionToPerformInInventorySetting(oPage_CategoryMaster.list_categoryNameForCategoryMaster,
                            categoryName, oPage_CategoryMaster.list_categoryActionsForCategoryName, "Link Dispensing Units");

                    if (clickOnLinkDispensingUnitActions) {

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.header_titleLinkExistingDispensingUnit, 2);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CategoryMaster.input_selectStoreInLinkExistingStore),
                                "Select Dispensing Unit input box clicked in category master");
                        Cls_Generic_Methods.customWait(2);

                        for (WebElement dispensingUnit : oPage_CategoryMaster.list_selectDispensingUnitsList) {
                            if (Cls_Generic_Methods.getTextInElement(dispensingUnit).equalsIgnoreCase(updateDispensingUnitName)) {
                                bDispensingUnitDisableInCategoryMaster = false;
                                break;
                            }
                        }

                        m_assert.assertTrue(bDispensingUnitDisableInCategoryMaster, "Dispensing Unit Disabled in Category master as : <b> " + updateDispensingUnitName + " </b> for category " + categoryName);
                        Cls_Generic_Methods.clickElement(oPage_CategoryMaster.input_selectStoreInLinkExistingStore);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                                "Close button clicked for link dispensing unit template");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_addCategory, 3);

                    } else {
                        m_assert.assertTrue(clickOnLinkDispensingUnitActions, " Link dispensing unit button is not clicked in category master");

                    }

                } else {
                    m_assert.assertTrue(clickOnDisableActions, " Disable button is not clicked in category master");
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = " Validating Active Functionality In Dispensing Unit Master")
    public void validateActiveDispensingUnit() {

        Page_CategoryMaster oPage_CategoryMaster = new Page_CategoryMaster(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_DispensingUnitMaster oPage_DispensingUnitMaster = new Page_DispensingUnitMaster(driver);
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);


        boolean bDispensingUnitActiveInCategoryMaster = false;

        try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {
                // Select Sub Category Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Dispensing Unit Master");

                // Validation Newly Created Sub Category Using Search In Sub Category Page List

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_DispensingUnitMaster.input_searchDispensingUnitName, updateDispensingUnitName),
                        "Dispensing Unit Name entered in search box as : <b> " + updateDispensingUnitName + "</b>");
                Cls_Generic_Methods.customWait(1);

                boolean clickOnActiveActions = CommonActions.getActionToPerformInInventorySetting(oPage_DispensingUnitMaster.list_dispensingUnitNameInDispensingUnitMaster,
                        updateDispensingUnitName, oPage_DispensingUnitMaster.list_dispensingUnitActionsForDispensingUnitName, "Active");

                if (clickOnActiveActions) {

                    CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                    CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Category Master");

                    Cls_Generic_Methods.waitForPageLoad(driver, 2);

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_searchByCategoryName, categoryName),
                            "Category name enter in search box as :<b>" + categoryName + "</b>");
                    Cls_Generic_Methods.customWait(1);

                    boolean clickOnLinkDispensingUnitActions = CommonActions.getActionToPerformInInventorySetting(oPage_CategoryMaster.list_categoryNameForCategoryMaster,
                            categoryName, oPage_CategoryMaster.list_categoryActionsForCategoryName, "Link Dispensing Units");

                    if (clickOnLinkDispensingUnitActions) {

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.header_titleLinkExistingDispensingUnit, 2);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CategoryMaster.input_selectStoreInLinkExistingStore),
                                " Input dispensing unit box clicked in category master");
                        Cls_Generic_Methods.customWait(2);

                        for (WebElement dispensingUnit : oPage_CategoryMaster.list_selectDispensingUnitsList) {
                            if (Cls_Generic_Methods.getTextInElement(dispensingUnit).equalsIgnoreCase(updateDispensingUnitName)) {
                                bDispensingUnitActiveInCategoryMaster = true;
                                break;
                            }
                        }


                        m_assert.assertTrue(bDispensingUnitActiveInCategoryMaster, "Dispensing Unit found in Category master as : " + updateDispensingUnitName + "for category " + categoryName);
                        Cls_Generic_Methods.clickElement(oPage_CategoryMaster.input_selectStoreInLinkExistingStore);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                                "Close button clicked for link dispensing unit template");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_addCategory, 3);

                        CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                        CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Dispensing Unit Master");

                        Cls_Generic_Methods.waitForPageLoad(driver , 2);

                        // Validation Newly Created Sub Category Using Search In Sub Category Page List

                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_DispensingUnitMaster.input_searchDispensingUnitName, updateDispensingUnitName),
                                " Dispensing Unit Name entered in search box as : <b> " + updateDispensingUnitName + "</b>");

                        boolean clickOnDisableActions = CommonActions.getActionToPerformInInventorySetting(oPage_DispensingUnitMaster.list_dispensingUnitNameInDispensingUnitMaster,
                                updateDispensingUnitName, oPage_DispensingUnitMaster.list_dispensingUnitActionsForDispensingUnitName, "Disable");

                        if (clickOnDisableActions) {


                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.button_disableAlertConfirmButton),
                                    "Confirm button clicked in dispensing unit");

                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_addCategory, 2);

                        }

                    }
                    else {
                        m_assert.assertTrue(clickOnLinkDispensingUnitActions, " Link Dispensing Unit button is not clicked in category master");
                    }

                } else {
                    m_assert.assertTrue(clickOnActiveActions, " Active button is not clicked in category master");
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

}