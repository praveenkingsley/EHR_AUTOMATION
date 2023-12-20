package tests.settings.organisationSettings.inventoryAndSupplyChain;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_CategoryMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_SubCategoryMaster;


import java.util.List;

import static tests.settings.organisationSettings.inventoryAndSupplyChain.CategoryMasterTest.getRandomString;


public class SubCategoryMasterTest extends TestBase {


    String newSubCategoryName = "Test_" + getRandomString(3);
    String newSubCategoryDescription = "Description_" + getRandomString(4);

    // Do Not Use Camel Casing. Application Issue
    String categoryName_1 = "Medication";
    String categoryName_2 = "Miscellaneous";

    String updatedSubCategoryName = newSubCategoryName + "_Update";


    @Test(enabled = true, description = "Validating Add Sub Category Functionality")
    public void validateAddSubCategoryFunctionality() {

        Page_SubCategoryMaster oPage_SubCategoryMaster = new Page_SubCategoryMaster(driver);
        Page_CategoryMaster oPage_CategoryMaster = new Page_CategoryMaster(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);


        boolean bSubCategoryFoundInCategoryMaster = false;

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {

                // Select Sub Category Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Sub Category Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubCategoryMaster.header_subCategoryMasterTitle, 8);
                // Opening New Sub Category ,Verify Sub Category Template

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SubCategoryMaster.header_subCategoryMasterTitle),
                        "Verifying Sub Category Master Header");
                Cls_Generic_Methods.scrollToTop();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_SubCategoryMaster.button_addSubCategory),
                        "Add Sub Category Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubCategoryMaster.header_addSubCategoryTemplateHeader, 3);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SubCategoryMaster.label_subCategoryInformationLabel),
                        "Sub Category Information Label is present");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SubCategoryMaster.label_categoryLabelInTemplate),
                        "Category Label is present");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SubCategoryMaster.label_nameLabelInTemplate),
                        "Name Label is present");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SubCategoryMaster.label_descriptionLabelInTemplate),
                        "Description Label is present");

                // Verifying Required Error In Sub Category

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CategoryMaster.button_saveChanges),
                        "Save Changes Button Clicked without filling required field");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubCategoryMaster.label_requiredErrorForCategoryField, 2);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SubCategoryMaster.label_requiredErrorForNameField),
                        "Required Error Message Displayed For Name Field");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SubCategoryMaster.label_requiredErrorForCategoryField),
                        "Required Error Message Displayed For Category Field ");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Close Button In Edit Sub Category Template Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubCategoryMaster.button_addSubCategory, 3);

                // Entering all required Field and Creating New Sub Category

                Cls_Generic_Methods.scrollToTop();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_SubCategoryMaster.button_addSubCategory),
                        "Add Sub Category Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubCategoryMaster.header_addSubCategoryTemplateHeader, 3);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SubCategoryMaster.select_categoryDropdownInTemplate),
                        "Category Dropdown Arrow Clicked");
                Cls_Generic_Methods.customWait(2);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_SubCategoryMaster.select_categoryDropdownInTemplate, categoryName_1),
                        "Category Selected as : <b>" + categoryName_1 + "</b>");

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SubCategoryMaster.input_nameInAddSubCategory, newSubCategoryName),
                        "Sub Category Name Entered as : <b>" + newSubCategoryName + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SubCategoryMaster.textarea_descriptionInAddSubCategory, newSubCategoryDescription),
                        " Sub Category Description Entered as : <b>" + newSubCategoryDescription + "</b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CategoryMaster.button_saveChanges),
                        "Save Changes button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubCategoryMaster.button_addSubCategory, 5);


                // Validation Newly Created Sub Category Using Search In Sub Category Page List

                Cls_Generic_Methods.sendKeysIntoElement(oPage_SubCategoryMaster.input_searchSubCategory, newSubCategoryName);
                Cls_Generic_Methods.customWait(1);
                for (WebElement subCategoryRow : oPage_SubCategoryMaster.list_subCategoryRowsInSubCategoryList) {

                    if (Cls_Generic_Methods.isElementDisplayed(subCategoryRow)) {
                        List<WebElement> subCategoryNameRow = subCategoryRow.findElements(By.xpath("./child::*"));
                        String subCategoryName = Cls_Generic_Methods.getTextInElement(subCategoryNameRow.get((0)));
                        String subCategoryDescription = Cls_Generic_Methods.getTextInElement(subCategoryNameRow.get((1)));
                        String subCategoryCategory = Cls_Generic_Methods.getTextInElement(subCategoryNameRow.get((2)));

                        if (subCategoryName.equalsIgnoreCase(newSubCategoryName) &&
                                subCategoryDescription.equalsIgnoreCase(newSubCategoryDescription) &&
                                subCategoryCategory.equalsIgnoreCase(categoryName_1)) {
                            bSubCategoryFoundInCategoryMaster = true;
                            break;
                        }
                    }
                }

                m_assert.assertTrue(bSubCategoryFoundInCategoryMaster,
                        "New Sub Category Added Successfully In Sub Category Master List ");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Edit Sub Category Functionality")
    public void validateEditFunctionalityForSubCategory() {
        Page_SubCategoryMaster oPage_SubCategoryMaster = new Page_SubCategoryMaster(driver);
        Page_CategoryMaster oPage_CategoryMaster = new Page_CategoryMaster(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

        boolean bUpdatedSubCategoryFoundInCategoryMaster = false;

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {

                // Select Sub Category Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Sub Category Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubCategoryMaster.header_subCategoryMasterTitle, 8);
                // Opening Previously Created Sub Category To Edit

                Cls_Generic_Methods.sendKeysIntoElement(oPage_SubCategoryMaster.input_searchSubCategory, newSubCategoryName);
                Cls_Generic_Methods.customWait(1);

                boolean clickOnEditActions = CommonActions.getActionToPerformInInventorySetting(oPage_SubCategoryMaster.list_subCategoryNameInSubCategoryMaster,
                        newSubCategoryName, oPage_SubCategoryMaster.list_subCategoryActionsForSubCategoryName, "Edit");

                if (clickOnEditActions) {

                    // Entering all required Field and Updating Existing Sub Category

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubCategoryMaster.select_categoryDropdownInTemplate, 2);
                    Cls_Generic_Methods.clickElement(oPage_SubCategoryMaster.select_categoryDropdownInTemplate);
                    Cls_Generic_Methods.customWait(1);

                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_SubCategoryMaster.select_categoryDropdownInTemplate, categoryName_2),
                            "Category Selected as : <b> " + categoryName_2 + "</b>");
                    m_assert.assertInfo(Cls_Generic_Methods.clearValuesInElement(oPage_SubCategoryMaster.input_nameInAddSubCategory),
                            "Clear Name In Edit Sub Category");
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SubCategoryMaster.input_nameInAddSubCategory, updatedSubCategoryName),
                            "Sub Category Name Entered as : <b>" + updatedSubCategoryName + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CategoryMaster.button_saveChanges),
                            "Save Changes button clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubCategoryMaster.button_addSubCategory, 5);

                    //Verifying Update Sub category Value In Edit Template

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_SubCategoryMaster.input_searchSubCategory, updatedSubCategoryName);
                    Cls_Generic_Methods.customWait(4);

                    clickOnEditActions = CommonActions.getActionToPerformInInventorySetting(oPage_SubCategoryMaster.list_subCategoryNameInSubCategoryMaster,
                            updatedSubCategoryName, oPage_SubCategoryMaster.list_subCategoryActionsForSubCategoryName, "Edit");

                    if (clickOnEditActions) {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubCategoryMaster.select_categoryDropdownInTemplate, 2);
                        m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_SubCategoryMaster.select_categoryDropdownInTemplate).contains(categoryName_2),
                                "Category Updated successfully as : <b>" + categoryName_2 + "</b>");
                        m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_SubCategoryMaster.input_nameInAddSubCategory, "value").contains(updatedSubCategoryName),
                                "Name Updated Successfully as : <b>" + updatedSubCategoryName + "</b>");

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                                "Close Button In Edit Sub Category Template Clicked");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubCategoryMaster.button_addSubCategory, 3);

                        // Validation Updated Sub Category Using Search In Sub Category Page List


                        for (WebElement subCategoryRow : oPage_SubCategoryMaster.list_subCategoryRowsInSubCategoryList) {

                            if (Cls_Generic_Methods.isElementDisplayed(subCategoryRow)) {
                                List<WebElement> subCategoryNameRow = subCategoryRow.findElements(By.xpath("./child::*"));
                                String subCategoryName = Cls_Generic_Methods.getTextInElement(subCategoryNameRow.get((0)));
                                String subCategoryCategory = Cls_Generic_Methods.getTextInElement(subCategoryNameRow.get((2)));

                                if (subCategoryName.equalsIgnoreCase(updatedSubCategoryName) &&
                                        subCategoryCategory.equalsIgnoreCase(categoryName_2)) {
                                    bUpdatedSubCategoryFoundInCategoryMaster = true;
                                    break;
                                }
                            }
                        }
                        m_assert.assertTrue(bUpdatedSubCategoryFoundInCategoryMaster,
                                "Sub Category Updated Successfully In Sub Category Master List as : " + updatedSubCategoryName);
                    }
                } else {
                    m_assert.assertTrue(clickOnEditActions, "Edit Button is not Clicked in Sub Category List");
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

    @Test(enabled = true, description = "Validating Add Sub Category In Item Master")
    public void validateSubCategoryInItemMaster() {

        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);

        boolean bSubCategoryFoundInAddItemMaster = false;

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {

                // Select Sub Category Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Item Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 8);
                Cls_Generic_Methods.scrollToTop();

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_addItem),
                        "Add Item Button Clicked");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.dropdown_categoryArrow, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.dropdown_categoryArrow),
                        "Category Dropdown Clicked in add item ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, categoryName_2);
                Cls_Generic_Methods.customWait(1);

                for (WebElement itemCategory : oPage_ItemMaster.list_inventoryItemCategoryList) {
                    if (Cls_Generic_Methods.getTextInElement(itemCategory).contains(categoryName_2)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemCategory), "Category selected: <b> " + categoryName_2 + "</b>");
                        break;
                    }
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_subCategoryDropdownArrow, 2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_subCategoryDropdownArrow),
                        "Sub Category Dropdown Arrow Clicked In Add Item");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, updatedSubCategoryName);
                Cls_Generic_Methods.customWait(1);

                for (WebElement subCategory : oPage_ItemMaster.list_inventoryItemSubCategoryList) {
                    if (Cls_Generic_Methods.getTextInElement(subCategory).contains(updatedSubCategoryName)) {
                        bSubCategoryFoundInAddItemMaster = true;
                        break;

                    }
                }
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_subCategoryDropdownArrow),
                        "Sub Category Dropdown Arrow Clicked In Add Item");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_closeItemMasterTemplate, 2);
                m_assert.assertTrue(bSubCategoryFoundInAddItemMaster, "Sub Category Created Successfully for " + categoryName_2 + "category");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ItemMaster.button_closeItemMasterTemplate),
                        "Close button In Item Master Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 5);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Disable Sub Category In Item Master")
    public void validateDisablingSubCategoryFunctionality() {
        Page_SubCategoryMaster oPage_SubCategoryMaster = new Page_SubCategoryMaster(driver);
        Page_CategoryMaster oPage_CategoryMaster = new Page_CategoryMaster(driver);
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);


        boolean bDisableSubCategoryFoundInItemMaster = true;
        boolean bActiveSubCategoryFoundInItemMaster = false;


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {


                // Selection Sub Category Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Sub Category Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubCategoryMaster.header_subCategoryMasterTitle, 8);

                // Clicking on Disable Sub Category From Sub Category master

                Cls_Generic_Methods.sendKeysIntoElement(oPage_SubCategoryMaster.input_searchSubCategory, updatedSubCategoryName);
                Cls_Generic_Methods.customWait(1);

                boolean clickOnDisableActions = CommonActions.getActionToPerformInInventorySetting(oPage_SubCategoryMaster.list_subCategoryNameInSubCategoryMaster,
                        updatedSubCategoryName, oPage_SubCategoryMaster.list_subCategoryActionsForSubCategoryName, "Disable");

                if (clickOnDisableActions) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.alert_disableConfirmAlert, 2);

                    //Validating Disable Store Functionality

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetUp.text_disableConfirmAlertTitle),
                            "Disable Confirm Title is Present");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_StoreSetUp.text_disableConfirmationAlertMessage).contains("Are you sure, you want disable " + updatedSubCategoryName + "?"),
                            "Disable Confirmation Message Is present");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.button_disableAlertConfirmButton), "" +
                            "Confirm Button clicked in Confirm Template");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_addCategory, 2);

                    // Verifying Disabled Sub Category Action Status As Active


                    boolean subCategoryActionsStatus = CommonActions.getActionButtonStatusInInventorySetting(oPage_SubCategoryMaster.list_subCategoryNameInSubCategoryMaster,
                            updatedSubCategoryName, oPage_SubCategoryMaster.list_subCategoryActionsForSubCategoryName, "Active");

                    m_assert.assertTrue(subCategoryActionsStatus,
                            " Sub Category Name <b>" + updatedSubCategoryName + " is Disabled successfully");

                    // Validating Disable Sub Category In Item Master

                    CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                    CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Item Master");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 8);
                    Cls_Generic_Methods.scrollToTop();
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_addItem),
                            "Add Item Button Clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.dropdown_categoryArrow, 5);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.dropdown_categoryArrow),
                            "Category Dropdown Clicked in add item ");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, categoryName_2);
                    Cls_Generic_Methods.customWait(1);
                    for (WebElement itemCategory : oPage_ItemMaster.list_inventoryItemCategoryList) {
                        if (Cls_Generic_Methods.getTextInElement(itemCategory).contains(categoryName_2)) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemCategory), "Category selected: <b> " + categoryName_2 + "</b>");
                            break;
                        }
                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_subCategoryDropdownArrow, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_subCategoryDropdownArrow),
                            "Sub Category Dropdown Arrow Clicked In Add Item");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, updatedSubCategoryName);
                    Cls_Generic_Methods.customWait(1);
                    for (WebElement subCategory : oPage_ItemMaster.list_inventoryItemSubCategoryList) {
                        if (Cls_Generic_Methods.getTextInElement(subCategory).contains(updatedSubCategoryName)) {
                            bDisableSubCategoryFoundInItemMaster = false;
                            break;
                        }

                    }


                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_subCategoryDropdownArrow),
                            "Sub Category Dropdown Arrow Clicked In Add Item");
                    m_assert.assertTrue(bDisableSubCategoryFoundInItemMaster, "Disabled Sub Category successfully in Item master ");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_closeItemMasterTemplate, 5);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ItemMaster.button_closeItemMasterTemplate),
                            "Closing Add Item Master Template without saving ");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 3);

                    //  Enable Category Using Active Button In Sub Category Master

                    CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                    CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Sub Category Master");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubCategoryMaster.header_subCategoryMasterTitle, 8);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_SubCategoryMaster.input_searchSubCategory, updatedSubCategoryName);
                    Cls_Generic_Methods.customWait(1);

                    boolean clickOnActiveActions = CommonActions.getActionToPerformInInventorySetting(oPage_SubCategoryMaster.list_subCategoryNameInSubCategoryMaster,
                            updatedSubCategoryName, oPage_SubCategoryMaster.list_subCategoryActionsForSubCategoryName, "Active");

                    if (clickOnActiveActions) {

                        // Verifying enabled sub category in item master

                        CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                        CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Item Master");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 8);

                        Cls_Generic_Methods.scrollToTop();
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_addItem),
                                "Add Item Button Clicked");

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.dropdown_categoryArrow, 5);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.dropdown_categoryArrow),
                                "Category Dropdown Clicked in add item ");

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, categoryName_2);
                        Cls_Generic_Methods.customWait(1);

                        for (WebElement itemCategory : oPage_ItemMaster.list_inventoryItemCategoryList) {
                            if (Cls_Generic_Methods.getTextInElement(itemCategory).contains(categoryName_2)) {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemCategory), "Category selected: <b> " + categoryName_2 + "</b>");
                                break;
                            }
                        }

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_subCategoryDropdownArrow, 2);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_subCategoryDropdownArrow),
                                "Sub Category Dropdown Arrow Clicked In Add Item");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, updatedSubCategoryName);
                        Cls_Generic_Methods.customWait(1);
                        for (WebElement subCategory : oPage_ItemMaster.list_inventoryItemSubCategoryList) {
                            if (Cls_Generic_Methods.getTextInElement(subCategory).contains(updatedSubCategoryName)) {
                                bActiveSubCategoryFoundInItemMaster = true;
                                break;
                            }
                        }

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_subCategoryDropdownArrow),
                                "Sub Category Dropdown Arrow Clicked In Add Item");
                        m_assert.assertTrue(bActiveSubCategoryFoundInItemMaster, "Active Category successfully in Item master ");
                        Cls_Generic_Methods.scrollToTop();
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_closeItemMasterTemplate, 5);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ItemMaster.button_closeItemMasterTemplate),
                                "Closing Add Item Master Template without saving ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 3);

                        //  Enable Category Using Active Button In Category Master

                        CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                        CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Sub Category Master");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubCategoryMaster.header_subCategoryMasterTitle, 8);

                        Cls_Generic_Methods.sendKeysIntoElement(oPage_SubCategoryMaster.input_searchSubCategory, updatedSubCategoryName);
                        Cls_Generic_Methods.customWait(1);

                        clickOnDisableActions = CommonActions.getActionToPerformInInventorySetting(oPage_SubCategoryMaster.list_subCategoryNameInSubCategoryMaster,
                                updatedSubCategoryName, oPage_SubCategoryMaster.list_subCategoryActionsForSubCategoryName, "Disable");

                        m_assert.assertTrue(clickOnDisableActions, " Sub Category Disabled Successfully");
                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetUp.text_disableConfirmAlertTitle),
                                "Disable Confirm Title is Present");
                        m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_StoreSetUp.text_disableConfirmationAlertMessage).contains("Are you sure, you want disable " + updatedSubCategoryName + "?"),
                                "Disable Confirmation Message Is present");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.button_disableAlertConfirmButton), "" +
                                "Confirm Button clicked in Confirm Template");

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_addCategory, 2);


                    } else {
                        m_assert.assertTrue(clickOnActiveActions, "Active Button is not Clicked in Sub Category List");

                    }

                } else {
                    m_assert.assertTrue(clickOnDisableActions, "Disable Button is not Clicked in Sub Category List");
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