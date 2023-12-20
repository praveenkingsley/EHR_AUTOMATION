package tests.settings.organisationSettings.inventoryAndSupplyChain;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_CategoryMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static pages.commonElements.CommonActions.oEHR_Data;


public class CategoryMasterTest extends TestBase {


    String newCategoryName = getRandomString(5);
    String newCategoryDescription = getRandomString(10);
    String newCategoryPrefix = getRandomString(3);

    String updatedCategoryName = newCategoryName + "update";

    String updateCategoryType = "Asset";

    String updatedCategoryDescription = newCategoryDescription + "update";

    @Test(enabled = true, description = "Validating Add Category Functionality In Category Master")
    public void validateAddCategoryFunctionality() {

        Page_CategoryMaster oPage_CategoryMaster = new Page_CategoryMaster(driver);
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);


        boolean bCategoryFoundInCategoryMaster = false;
        boolean bCategoryFoundInStoreSetupCategory = false;
        boolean bCategoryFoundInItemMaster = false;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                // Selection Category Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Category Master");

                // Adding New Category to Category Master

                Cls_Generic_Methods.scrollToTop();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CategoryMaster.button_addCategory),
                        " Add Category Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.header_titleAddCategory, 3);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CategoryMaster.text_labelCategoryInformation),
                        "Category Information Label is present");

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_categoryName, newCategoryName),
                        "Category Name Entered as : <b>" + newCategoryName + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_categoryDescription, newCategoryDescription),
                        "Category Description Entered as : <b>" + newCategoryDescription + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_categoryPrefix, newCategoryPrefix),
                        "Category Prefix Entered as : <b>" + newCategoryPrefix + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver, oPage_CategoryMaster.radio_yesMultipleVariantsButton),
                        " <b> Yes </b> radio button clicked for <b> Multiple Variant </b>");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver, oPage_CategoryMaster.radio_yesStockableButton),
                        "<b> Yes </b> radio button clicked for <b> Stockable </b>");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CategoryMaster.button_saveChanges),
                        " Save Changes button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_addCategory, 3);


                // Validation Newly Created Category Using Search In Category Master

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_searchByCategoryName, newCategoryName),
                        "Category entered in Search Box as :<b>" + newCategoryName + "</b>");

                Cls_Generic_Methods.customWait(2);
                for (WebElement name : oPage_CategoryMaster.list_categoryRowForCategoryMaster) {

                    if (Cls_Generic_Methods.isElementDisplayed(name)) {
                        List<WebElement> categoryNameRow = name.findElements(By.xpath("./child::*"));
                        String categoryName = Cls_Generic_Methods.getTextInElement(categoryNameRow.get((0)));
                        String categoryDescription = Cls_Generic_Methods.getTextInElement(categoryNameRow.get((1)));
                        if (categoryName.equalsIgnoreCase(newCategoryName) && categoryDescription.equalsIgnoreCase(newCategoryDescription)) {
                            bCategoryFoundInCategoryMaster = true;
                            break;
                        }
                    }
                }

                m_assert.assertTrue(bCategoryFoundInCategoryMaster, " New Category Added Successfully In Category Master List as : <b>" + newCategoryName + "</b>");
                if (bCategoryFoundInCategoryMaster) {

                    // Validating Newly Created Category in Store Setup Category List

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_facilitySelector),
                            "Facility Selector Button Clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.text_selectedFacilityName, 2);
                    String facilityName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_selectedFacilityName);
                    CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                    CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");

                    boolean clickOnLinkActions = CommonActions.getActionOfFacilityStores(oPage_StoreSetUp.list_facilityNamesForInventory, facilityName,
                            oPage_StoreSetUp.list_storeColumnLinkedToFacility, oPage_StoreSetUp.list_buttonColumnLinkedToStores, "Link");

                    if (clickOnLinkActions) {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.dropdown_actionPerformedOnLinkDropdown, 2);
                        m_assert.assertTrue(selectCategoryFromDropdown(oPage_StoreSetUp.list_linkActionDropdown, "Category"),
                                "Category Clicked In Link Dropdown");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.input_selectStoreInLinkExistingStore, 2);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore),
                                "Store input Box clicked in Link Existing Store");
                        for (WebElement category : oPage_StoreSetUp.list_categoriesInDropdown) {
                            if (Cls_Generic_Methods.getTextInElement(category).equalsIgnoreCase(newCategoryName)) {
                                bCategoryFoundInStoreSetupCategory = true;
                                break;
                            }
                        }

                        m_assert.assertTrue(bCategoryFoundInStoreSetupCategory,
                                " New Category Added Successfully In Store Setup Category List as : <b>" + newCategoryName + "</b>");

                        Cls_Generic_Methods.clickElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore);
                        Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.title_StoreSetup, 3);
                    }

                    // Validating Newly Created Category in Item Master Category List

                    CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                    CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Item Master");
                    Cls_Generic_Methods.customWait(5);

                    Cls_Generic_Methods.scrollToTop();
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_addItem),
                            "Add Item Button Clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemHsnCode, 3);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.dropdown_categoryArrow),
                            "Category dropdown clicked ");
                    Cls_Generic_Methods.customWait(2);
                    for (WebElement category : oPage_ItemMaster.list_inventoryItemCategoryList) {
                        if (Cls_Generic_Methods.getTextInElement(category).equalsIgnoreCase(newCategoryName)) {
                            bCategoryFoundInItemMaster = true;
                            break;
                        }
                    }

                    m_assert.assertTrue(bCategoryFoundInItemMaster,
                            " New Category Added Successfully In Add Item Master Category List as : <b>" + newCategoryName + "</b>");
                    Cls_Generic_Methods.clickElement(oPage_ItemMaster.dropdown_categoryArrow);
                    Cls_Generic_Methods.customWait(2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ItemMaster.button_closeItemMasterTemplate),
                            "Closing Add Item Master Template ");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 3);

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

    @Test(enabled = true, description = "Validating Edit Category Functionality In Category Master")
    public void validateEditCategoryFunctionality() {

        Page_CategoryMaster oPage_CategoryMaster = new Page_CategoryMaster(driver);
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        boolean bCategoryFoundInStoreSetupCategory = false;
        boolean bCategoryFoundInCategoryMaster = false;


        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                // Selection Category Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Category Master");

                // Validation Newly Created Category Using Search In Category Master List

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_searchByCategoryName, newCategoryName),
                        "Searching category in search box :<b> " + newCategoryName + "</b>");

                Cls_Generic_Methods.customWait();
                for (WebElement name : oPage_CategoryMaster.list_categoryRowForCategoryMaster) {

                    if (Cls_Generic_Methods.isElementDisplayed(name)) {
                        List<WebElement> categoryNameRow = name.findElements(By.xpath("./child::*"));
                        String categoryName = Cls_Generic_Methods.getTextInElement(categoryNameRow.get((0)));
                        String categoryDescription = Cls_Generic_Methods.getTextInElement(categoryNameRow.get((1)));
                        if (categoryName.equalsIgnoreCase(newCategoryName) && categoryDescription.equalsIgnoreCase(newCategoryDescription)) {
                            bCategoryFoundInCategoryMaster = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bCategoryFoundInCategoryMaster, " Category Found In Category Master List via search : <b>" + newCategoryName + "</b>");
                // Edit Category In Category Master

                if (bCategoryFoundInCategoryMaster) {

                    boolean clickOnEditActions = CommonActions.getActionToPerformInInventorySetting(oPage_CategoryMaster.list_categoryNameForCategoryMaster,
                            newCategoryName, oPage_CategoryMaster.list_categoryActionsForCategoryName, "Edit");
                    Cls_Generic_Methods.customWait();

                    if (clickOnEditActions) {

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.text_labelCategoryInformation, 2);
                        Cls_Generic_Methods.clearValuesInElement(oPage_CategoryMaster.input_categoryName);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_categoryName, updatedCategoryName),
                                "Updated Category Name Entered as : <b>" + updatedCategoryName + "</b>");
                        Cls_Generic_Methods.clearValuesInElement(oPage_CategoryMaster.input_categoryDescription);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_categoryDescription, updatedCategoryDescription),
                                "Updated Category Description Entered as : <b>" + updatedCategoryDescription + "</b>");
                        //m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_CategoryMaster.select_categoryType, "Asset"),
                            //    "Updated Selecting Category Type as : <b> " + updateCategoryType + "</b>");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver, oPage_CategoryMaster.radio_noMultipleVariantsButton),
                                " Updated <b> No </b> radio button clicked for <b> Multiple Variant </b>");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver, oPage_CategoryMaster.radio_noStockableButton),
                                "Updated <b> No </b> radio button clicked for <b> Stockable </b>");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CategoryMaster.button_saveChanges),
                                " Save Changes button clicked ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_addCategory, 3);
                    }
                    // Validating Updated Values In Category

                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_searchByCategoryName, updatedCategoryName),
                            "Category name Entered in search box as  : <b>" + updatedCategoryName + "</b>");
                    Cls_Generic_Methods.customWait(3);

                    clickOnEditActions = CommonActions.getActionToPerformInInventorySetting(oPage_CategoryMaster.list_categoryNameForCategoryMaster,
                            updatedCategoryName, oPage_CategoryMaster.list_categoryActionsForCategoryName, "Edit");

                    if (clickOnEditActions) {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.text_labelCategoryInformation, 2);
                        m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_CategoryMaster.input_categoryName, "value"),
                                updatedCategoryName, "Category Name updated");
                        m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_CategoryMaster.input_categoryDescription, "value"),
                                updatedCategoryDescription, "Category Description updated");
                       // m_assert.assertEquals(Cls_Generic_Methods.getTextInElement(oPage_CategoryMaster.text_categoryTypeBox), "Asset",
                               // "Category Type Updated");
                        m_assert.assertTrue(!Cls_Generic_Methods.radioButtonIsSelected(oPage_CategoryMaster.radio_noStockableButton),
                                "Stockable button updated");
                        m_assert.assertTrue(Cls_Generic_Methods.radioButtonIsSelected(oPage_CategoryMaster.radio_noMultipleVariantsButton),
                                "Multiple Variant button updated");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                                "Update Category closed , Category updated successfully as : <b>" + updatedCategoryName + "</b>");


                        // Validating Updated Category in Store Setup

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_facilitySelector),
                                "Facility Selector Button Clicked");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.text_selectedFacilityName, 2);
                        String facilityName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_selectedFacilityName);
                        Cls_Generic_Methods.customWait();
                        CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                        CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");

                        Cls_Generic_Methods.waitForPageLoad(driver, 3);
                        boolean clickOnLinkActions = CommonActions.getActionOfFacilityStores(oPage_StoreSetUp.list_facilityNamesForInventory, facilityName,
                                oPage_StoreSetUp.list_storeColumnLinkedToFacility, oPage_StoreSetUp.list_buttonColumnLinkedToStores, "Link");


                        if (clickOnLinkActions) {

                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.dropdown_actionPerformedOnLinkDropdown, 2);
                            m_assert.assertTrue(selectCategoryFromDropdown(oPage_StoreSetUp.list_linkActionDropdown, "Category"),
                                    "Category Clicked In Link Dropdown");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.input_selectStoreInLinkExistingStore, 2);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore),
                                    "Store input Box clicked in Link Existing Store");
                            for (WebElement category : oPage_StoreSetUp.list_categoriesInDropdown) {
                                if (Cls_Generic_Methods.getTextInElement(category).equalsIgnoreCase(updatedCategoryName)) {
                                    bCategoryFoundInStoreSetupCategory = true;
                                    break;
                                }
                            }

                            m_assert.assertTrue(bCategoryFoundInStoreSetupCategory,
                                    "  Category Updated Successfully In Store Setup Category List as: <b> " + updatedCategoryName + "</b>");

                            Cls_Generic_Methods.clickElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore);
                            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.title_StoreSetup, 3);

                        }

                    } else {
                        m_assert.assertTrue(clickOnEditActions, "Edit Button is not Clicked in  Category List");
                    }

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

    @Test(enabled = true, description = "Validating Link Dispensing Unit To Category Functionality In Category Master")
    public void validateLinkDispensingUnitCategoryFunctionality() {

        Page_CategoryMaster oPage_CategoryMaster = new Page_CategoryMaster(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);

        boolean bCurrentlyLinkedDispensingUnit = false;
        List<String> beforeLinkSelectDispensingUnitList = new ArrayList<String>();
        List<String> beforeLinkCurrentlyLinkedDispensingUnitList = new ArrayList<String>();
        List<String> afterLinkCurrentlyLinkedDispensingUnitList = new ArrayList<String>();

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                // Selection Category Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Category Master");

                // Clicking on Link Dispensing Unit for category

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_searchByCategoryName, updatedCategoryName),
                        "Searching category in search box :<b> " + updatedCategoryName + "</b>");
                Cls_Generic_Methods.customWait(1);

                boolean clickOnLinkDispensingUnitActions = CommonActions.getActionToPerformInInventorySetting(oPage_CategoryMaster.list_categoryNameForCategoryMaster,
                        updatedCategoryName, oPage_CategoryMaster.list_categoryActionsForCategoryName, "Link Dispensing Units");


                if (clickOnLinkDispensingUnitActions) {

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_saveLinkDispensingUnits, 3);

                    // Linking Dispensing Unit to Category

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CategoryMaster.button_saveLinkDispensingUnits),
                            "Link Existing Dispensing Unit Opened");
                    for (WebElement units : oPage_CategoryMaster.list_selectDispensingUnitsList) {
                        beforeLinkSelectDispensingUnitList.add(Cls_Generic_Methods.getTextInElement(units));
                    }

                    for (WebElement currentlyLinked : oPage_CategoryMaster.list_currentlyLinkedDispensingUnitsList) {
                        beforeLinkCurrentlyLinkedDispensingUnitList.add(Cls_Generic_Methods.getTextInElement(currentlyLinked));
                    }

                    m_assert.assertInfo("Currently Linked Dispensing Unit Before Link :<b>" + beforeLinkCurrentlyLinkedDispensingUnitList + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_CategoryMaster.select_selectDispensingUnits, beforeLinkSelectDispensingUnitList.get(0)),
                            "Dispensing Unit entered as " + beforeLinkSelectDispensingUnitList.get(0));

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_saveLinkDispensingUnits, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CategoryMaster.button_saveLinkDispensingUnits),
                            " Save button In Link Dispensing Unit clicked ");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_addCategory, 5);

                    // Verifying Linked Dispensing Unit In Category Master

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_searchByCategoryName, updatedCategoryName);
                    Cls_Generic_Methods.customWait(1);

                    clickOnLinkDispensingUnitActions = CommonActions.getActionToPerformInInventorySetting(oPage_CategoryMaster.list_categoryNameForCategoryMaster,
                            updatedCategoryName, oPage_CategoryMaster.list_categoryActionsForCategoryName, "Link Dispensing Units");

                    if (clickOnLinkDispensingUnitActions) {

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_saveLinkDispensingUnits, 3);
                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CategoryMaster.button_saveLinkDispensingUnits),
                                "Link Existing Dispensing Unit Opened");

                        for (WebElement currentlyLinked : oPage_CategoryMaster.list_currentlyLinkedDispensingUnitsList) {
                            if (Cls_Generic_Methods.getTextInElement(currentlyLinked).equalsIgnoreCase(beforeLinkSelectDispensingUnitList.get(0))) {
                                bCurrentlyLinkedDispensingUnit = true;
                                break;
                            }
                        }
                        m_assert.assertTrue(bCurrentlyLinkedDispensingUnit, "Dispensing Unit linked successfully");

                        for (WebElement currentlyLinked : oPage_CategoryMaster.list_currentlyLinkedDispensingUnitsList) {
                            afterLinkCurrentlyLinkedDispensingUnitList.add(Cls_Generic_Methods.getTextInElement(currentlyLinked));
                        }

                        m_assert.assertInfo("Currently Linked Dispensing Unit After Linking :<b>" + afterLinkCurrentlyLinkedDispensingUnitList + "</b>");

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                                "Closing Link Existing Dispensing Unit ");

                        // Validation Linked Dispensing Unit In Item Master

                        CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                        CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Item Master");
                        Cls_Generic_Methods.scrollToTop();
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_addItem),
                                "Add Item Button Clicked");

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.dropdown_categoryArrow, 5);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.dropdown_categoryArrow),
                                "Category Dropdown Clicked in add item ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, updatedCategoryName);
                        Cls_Generic_Methods.customWait(1);
                        for (WebElement itemCategory : oPage_ItemMaster.list_inventoryItemCategoryList) {
                            if (Cls_Generic_Methods.getTextInElement(itemCategory).contains(updatedCategoryName)) {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemCategory), "Category selected: <b> " + newCategoryName + "</b>");
                                break;
                            }
                        }

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.dropdown_dispensingUnitArrow, 3);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.dropdown_dispensingUnitArrow),
                                "Dispensing Unit Dropdown Clicked in add item ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, beforeLinkSelectDispensingUnitList.get(0));
                        Cls_Generic_Methods.customWait(1);
                        for (WebElement itemDispensingUnit : oPage_ItemMaster.list_inventoryItemDispensingUnitList) {
                            if (Cls_Generic_Methods.getTextInElement(itemDispensingUnit).contains(beforeLinkSelectDispensingUnitList.get(0))) {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemDispensingUnit), "Dispensing unit linked successfully: <b> " + beforeLinkSelectDispensingUnitList.get(0) + "</b>");
                                break;
                            }
                        }

                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ItemMaster.button_closeItemMasterTemplate),
                                "Close button In Item Master Clicked");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 3);

                    }
                } else {
                    m_assert.assertTrue(clickOnLinkDispensingUnitActions, "Link Dispensing Unit Button is not Clicked in  Category List");
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

    @Test(enabled = true, description = "Validating Unlink Dispensing Unit To Category Functionality In Category Master")
    public void validateUnlinkDispensingUnitCategoryFunctionality() {

        Page_CategoryMaster oPage_CategoryMaster = new Page_CategoryMaster(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);

        boolean bCurrentlyUnlinkedDispensingUnit = false;
        boolean bUnlinkedDispensingUnitInItemMaster = false;
        List<String> beforeUnlinkSelectDispensingUnitList = new ArrayList<String>();
        List<String> afterUnlinkSelectDispensingUnitList = new ArrayList<String>();
        List<String> dispensingUnitListInItemMaster = new ArrayList<String>();


        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                // Selection Category Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Category Master");

                // Clicking on Unlink Dispensing Unit for category

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_searchByCategoryName, updatedCategoryName),
                        "Category Entered in search box as :<b>" + updatedCategoryName + "</b>");
                Cls_Generic_Methods.customWait(1);

                boolean clickOnUnlinkDispensingUnitActions = CommonActions.getActionToPerformInInventorySetting(oPage_CategoryMaster.list_categoryNameForCategoryMaster,
                        updatedCategoryName, oPage_CategoryMaster.list_categoryActionsForCategoryName, "Unlink Dispensing Units");

                if (clickOnUnlinkDispensingUnitActions) {

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_saveLinkDispensingUnits, 2);

                    // Unlinking Dispensing Unit to Category

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CategoryMaster.button_saveLinkDispensingUnits),
                            "Unlink Existing Dispensing Unit Opened");
                    for (WebElement units : oPage_CategoryMaster.list_selectDispensingUnitsList) {
                        beforeUnlinkSelectDispensingUnitList.add(Cls_Generic_Methods.getTextInElement(units));
                    }

                    m_assert.assertInfo("List of Dispensing Unit Before Unlink In Category Master <b>" + beforeUnlinkSelectDispensingUnitList + "</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_CategoryMaster.select_selectDispensingUnits,
                            beforeUnlinkSelectDispensingUnitList.get(0)), "Dispensing Unit for unlinking entered :<b>" + beforeUnlinkSelectDispensingUnitList.get(0) + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_saveLinkDispensingUnits, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CategoryMaster.button_saveLinkDispensingUnits),
                            " Save button In Unlink Dispensing Unit clicked ");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_addCategory, 10);

                    // Verifying Unlinked Dispensing Unit in  to selected Category

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_searchByCategoryName, updatedCategoryName);
                    Cls_Generic_Methods.customWait(1);
                    clickOnUnlinkDispensingUnitActions = CommonActions.getActionToPerformInInventorySetting(oPage_CategoryMaster.list_categoryNameForCategoryMaster,
                            updatedCategoryName, oPage_CategoryMaster.list_categoryActionsForCategoryName, "Unlink Dispensing Units");

                    if (clickOnUnlinkDispensingUnitActions) {

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.header_titleUnlinkExistingDispensingUnit, 2);
                        for (WebElement currentlyUnlinked : oPage_CategoryMaster.list_currentlyUnlinkedDispensingUnitsList) {
                            if (Cls_Generic_Methods.getTextInElement(currentlyUnlinked).equalsIgnoreCase(beforeUnlinkSelectDispensingUnitList.get(0))) {

                                bCurrentlyUnlinkedDispensingUnit = true;
                                break;
                            }
                        }
                        m_assert.assertTrue(bCurrentlyUnlinkedDispensingUnit, "Dispensing Unit Unlinked successfully");

                        for (WebElement units : oPage_CategoryMaster.list_currentlyUnlinkedDispensingUnitsList) {
                            afterUnlinkSelectDispensingUnitList.add(Cls_Generic_Methods.getTextInElement(units));
                        }

                        m_assert.assertInfo("List of Dispensing Unit After Unlink In Category Master <b>" + afterUnlinkSelectDispensingUnitList + "</b>");

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                                "Closing Unlink Existing Dispensing Unit ");

                        // Validation Unlinked Dispensing Unit In Item Master

                        CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                        CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Item Master");
                        Cls_Generic_Methods.scrollToTop();
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_addItem),
                                "Add Item Button Clicked");

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.dropdown_categoryArrow, 3);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.dropdown_categoryArrow),
                                "Category Dropdown Clicked in add item");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, updatedCategoryName);
                        Cls_Generic_Methods.customWait(1);
                        for (WebElement itemCategory : oPage_ItemMaster.list_inventoryItemCategoryList) {
                            if (Cls_Generic_Methods.getTextInElement(itemCategory).contains(updatedCategoryName)) {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemCategory), "Category selected: <b> " + updatedCategoryName + "</b>");
                                break;
                            }
                        }

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.dropdown_dispensingUnitArrow, 2);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.dropdown_dispensingUnitArrow),
                                "Dispensing Unit Text Box Clicked");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
                        for (WebElement dispensingUnit : oPage_ItemMaster.list_inventoryItemDispensingUnitList) {
                            dispensingUnitListInItemMaster.add(Cls_Generic_Methods.getTextInElement(dispensingUnit));
                        }

                        if (!dispensingUnitListInItemMaster.equals(beforeUnlinkSelectDispensingUnitList)) {
                            bUnlinkedDispensingUnitInItemMaster = true;
                        }

                        Cls_Generic_Methods.clickElement(oPage_ItemMaster.dropdown_dispensingUnitArrow);
                        m_assert.assertTrue(bUnlinkedDispensingUnitInItemMaster, "Dispensing Unit Unlinked successfully in Item master");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ItemMaster.button_closeItemMasterTemplate),
                                "Closing Add Item Master Template without saving ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 3);

                    }
                } else {
                    m_assert.assertTrue(clickOnUnlinkDispensingUnitActions, "Unlink dispensing Button is not Clicked in Sub Category List");
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

    @Test(enabled = true, description = "Validating Disable and Active Category Functionality In Category Master")
    public void validateDisableAndActiveCategoryFunctionality() {

        Page_CategoryMaster oPage_CategoryMaster = new Page_CategoryMaster(driver);
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);

        boolean bActiveCategory = false;
        boolean bDisableCategory = true;


        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {


                // Selection Category Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Category Master");

                // Clicking on Disable button for category

                Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_searchByCategoryName, updatedCategoryName);
                Cls_Generic_Methods.customWait(1);

                boolean clickOnDisableActions = CommonActions.getActionToPerformInInventorySetting(oPage_CategoryMaster.list_categoryNameForCategoryMaster,
                        updatedCategoryName, oPage_CategoryMaster.list_categoryActionsForCategoryName, "Disable");

                if (clickOnDisableActions) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.alert_disableConfirmAlert, 2);

                    //Validating Disable Store Functionality

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetUp.text_disableConfirmAlertTitle),
                            "Disable Confirm Title is Present");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_StoreSetUp.text_disableConfirmationAlertMessage).contains("Are you sure, you want disable " + updatedCategoryName + "?"),
                            "Confirmation Message Is present");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.button_disableAlertConfirmButton),
                            "Confirm Button Clicked");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_addCategory, 2);


                    // Validating Disable Category In Item Master

                    CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                    CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Item Master");
                    Cls_Generic_Methods.scrollToTop();
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_addItem),
                            "Add Item Button Clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.select_itemCategory, 3);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.dropdown_categoryArrow),
                            "Category Dropdown Clicked in Item Master");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 3);
                    for (WebElement category : oPage_ItemMaster.list_inventoryItemCategoryList) {
                        if (Cls_Generic_Methods.getTextInElement(category).contains(updatedCategoryName)) {
                            bDisableCategory = false;
                            break;
                        }
                    }


                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ItemMaster.dropdown_categoryArrow),
                            "Category Dropdown Arrow Clicked in Item Master");
                    m_assert.assertTrue(bDisableCategory, " Disabled <b> " + updatedCategoryName + " </b> Category successfully in Item master ");
                    Cls_Generic_Methods.scrollToTop();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_closeItemMasterTemplate, 5);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ItemMaster.button_closeItemMasterTemplate),
                            "Closing Add Item Master Template");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 3);

                    //  Enable Category Using Active Button In Category Master

                    CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                    CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Category Master");

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_searchByCategoryName, updatedCategoryName);
                    Cls_Generic_Methods.customWait(1);

                    clickOnDisableActions = CommonActions.getActionToPerformInInventorySetting(oPage_CategoryMaster.list_categoryNameForCategoryMaster,
                            updatedCategoryName, oPage_CategoryMaster.list_categoryActionsForCategoryName, "Active");

                    if (clickOnDisableActions) {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_addCategory, 2);

                        // Verifying enabled category in item master

                        CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                        CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Item Master");
                        Cls_Generic_Methods.scrollToTop();
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_addItem),
                                "Add Item Button Clicked");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.select_itemCategory, 3);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.dropdown_categoryArrow),
                                "Category Dropdown Clicked in Item Master");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
                        for (WebElement category : oPage_ItemMaster.list_inventoryItemCategoryList) {
                            if (Cls_Generic_Methods.getTextInElement(category).contains(updatedCategoryName)) {
                                bActiveCategory = true;
                                break;
                            }
                        }
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ItemMaster.dropdown_categoryArrow),
                                "Category Dropdown Arrow Clicked in Item Master");
                        m_assert.assertTrue(bActiveCategory, " Active <b> " + updatedCategoryName + " </b> Category successfully in Item master ");
                        Cls_Generic_Methods.scrollToTop();
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_closeItemMasterTemplate, 5);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ItemMaster.button_closeItemMasterTemplate),
                                "Closing Add Item Master Template");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 3);

                        CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                        CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Category Master");

                        // Clicking on Disable button for category

                        Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_searchByCategoryName, updatedCategoryName);
                        Cls_Generic_Methods.customWait(1);

                        clickOnDisableActions = CommonActions.getActionToPerformInInventorySetting(oPage_CategoryMaster.list_categoryNameForCategoryMaster,
                                updatedCategoryName, oPage_CategoryMaster.list_categoryActionsForCategoryName, "Disable");

                        if (clickOnDisableActions) {

                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.alert_disableConfirmAlert, 2);

                            //Validating Disable Store Functionality

                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.button_disableAlertConfirmButton),
                                    "Confirm Button Clicked");

                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_addCategory, 2);
                        }

                    }
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


    public static String getRandomString(int... length) {

        String randomString = "";
        int randomStringLength = 3;

        try {
            if (length.length > 0) {
                randomStringLength = length[0];
            }

            final char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            final Random random = new Random();
            for (int i = 1; i <= randomStringLength; i++) {
                randomString = randomString + chars[random.nextInt(chars.length)];
            }
        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

        return randomString;
    }


    public static boolean selectCategoryFromDropdown(List<WebElement> listOfCategoryElements, String nameOfCategoryToSelect) {
        boolean categorySelected = false;
        Page_StoreSetUp oPage_storeSetUp = new Page_StoreSetUp(driver);
        try {
            oPage_storeSetUp = new Page_StoreSetUp(driver);
            for (WebElement eCategoryElement : listOfCategoryElements) {
                String sCategoryName = Cls_Generic_Methods.getTextInElement(eCategoryElement);
                if (sCategoryName.contains(nameOfCategoryToSelect)) {
                    categorySelected = true;
                    Cls_Generic_Methods.waitForElementToBeDisplayed(eCategoryElement, 2);
                    Cls_Generic_Methods.clickElementByJS(driver, eCategoryElement);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertTrue(false, "Error while selecting the category - " + nameOfCategoryToSelect + ". \n" + e);
        }
        return categorySelected;
    }
}