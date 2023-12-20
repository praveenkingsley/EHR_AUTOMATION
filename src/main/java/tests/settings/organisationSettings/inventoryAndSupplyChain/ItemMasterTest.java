package tests.settings.organisationSettings.inventoryAndSupplyChain;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;


import java.util.List;

import static pages.commonElements.CommonActions.getRandomUniqueString;
import static pages.commonElements.CommonActions.oEHR_Data;
import static tests.settings.organisationSettings.inventoryAndSupplyChain.CategoryMasterTest.getRandomString;
import static tests.settings.organisationSettings.inventoryAndSupplyChain.CategoryMasterTest.selectCategoryFromDropdown;


public class ItemMasterTest extends TestBase {


    String categoryName = "Medication";

    // They are not standard sub category , created for automation only
    String subCategoryName = "itemSubCategory_2";
    String updatedSubCategoryName = "itemSubCategory_2";
    String brandCompanyName = "HealthGraph Pvt";
    String hsnCode = "HSN" + getRandomUniqueString(4);
    String updatedHsnCode = hsnCode + "Update";
    String itemDescription = "Description_" + getRandomString(4);
    String updatedItemDescription = "Description_" + getRandomString(4) + "_Updated";
    String itemPropertiesTax = "GST0 - 0.0%";
    String updatedItemPropertiesTax = "GST5 - 5.0%";
    String itemPossibleVariantName = "Variant1";
    String itemPossibleVariantValue = "1";
    String storeNameInStoreSetup = "Optical Store 1- Optical";
    String dispensingUnitName = "Aerosols";
    String itemGenericCompositionName = "Hexapeptide-11";
    String itemGenericCompositionValue = "1.00000";


    @Test(enabled = true, description = "Validating Add Item Functionality in Item Master")
    public void validateAddFunctionalityInItemMaster() {

        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);

        boolean bCategoryFound = false;

        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                // Select Item Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Item Master");

                // Opening Add Item Template ,Verify Add Item Template

                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.header_itemMasterTitle),
                        " Verifying Item Master Header ");
                Cls_Generic_Methods.scrollToTop();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_addItem),
                        " Add Item Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.header_addItemMasterTemplateTitle, 5);

                // Verifying Required Error In Add Item Template

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_saveAddItemTemplate),
                        "Save Button Clicked without filling required field");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemDescription, 5);
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_itemDescription, "class").contains("error"),
                        "<b> Item Description Required Error Displayed </b> ");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.select_itemPropertiesTaxList, "class").contains("error"),
                        "<b> Item Properties Tax Required Error Displayed </b> ");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_itemPossibleVariantName, "class").contains("error"),
                        "<b> Item Possible Variant Name Required Error Displayed </b> ");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.select_itemPossibleVariantValue, "class").contains("error"),
                        "<b> Item Possible Variant Value Required Error Displayed </b> ");
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_ItemMaster.button_closeItemMasterTemplate),
                        "Close button In Item Master Clicked");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 5);

                // Entering Required fields and fill data in Item Details ,Properties and Possible Variant to create Item

                Cls_Generic_Methods.scrollToTop();
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_addItem),
                        " Add Item Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.header_addItemMasterTemplateTitle, 3);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.dropdown_categoryArrow),
                        "Category Dropdown Clicked in add item ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, categoryName);
                Cls_Generic_Methods.customWait(1);

                for (WebElement itemCategory : oPage_ItemMaster.list_inventoryItemCategoryList) {
                    if (Cls_Generic_Methods.getTextInElement(itemCategory).contains(categoryName)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemCategory), "Category selected: <b> " + categoryName + "</b>");
                        bCategoryFound = true;
                        break;
                    }
                }

                if (bCategoryFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_subCategoryDropdownArrow, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_subCategoryDropdownArrow),
                            "Sub Category Dropdown Arrow Clicked In Add Item");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, subCategoryName);
                    Cls_Generic_Methods.customWait(1);

                    for (WebElement subCategory : oPage_ItemMaster.list_inventoryItemSubCategoryList) {
                        if (Cls_Generic_Methods.getTextInElement(subCategory).contains(subCategoryName)) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(subCategory), "Sub Category selected: <b> " + subCategoryName + "</b>");
                            break;

                        }
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemHsnCode, 1);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemHsnCode, hsnCode),
                            " Item HSN code Entered as : <b>" + hsnCode + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemBrandCompany, brandCompanyName),
                            "Item Brand/Company Name Entered as : <b> " + brandCompanyName + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemDescription, itemDescription),
                            "Item Description Entered as : <b>" + itemDescription + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemManufacturerName, "HealthGraph"),
                            "Item Manufacturer Name Entered as :<b>" + brandCompanyName + "</b>");

                    Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_itemPropertiesTaxList);
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_ItemMaster.select_itemPropertiesTaxList, itemPropertiesTax),
                            "Item Properties Tax Entered as : <b>" + itemPropertiesTax + "</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.checkbox_propertiesExpiryPresent),
                            "Item Properties Expiry Present Checkbox Clicked");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.checkbox_propertiesPrescriptionMandatory),
                            "Item Properties Prescription Mandatory Checkbox Clicked");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.checkbox_propertiesUnitLevel),
                            "Item Properties Unit Level Checkbox Clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemPossibleVariantName, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemPossibleVariantName, itemPossibleVariantName),
                            "Item Possible Variant Name Entered as : <b>" + itemPossibleVariantName + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.input_itemPossibleVariantValue),
                            "Item Possible Variant Value Clicked");

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemPossibleVariantValue, itemPossibleVariantValue);
                    m_assert.assertTrue(CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_ItemMaster.list_itemPossibleVariantValuesList, itemPossibleVariantValue),
                            "Item Possible Variant Value Entered as : <b>" + itemPossibleVariantValue + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_saveAddItemTemplate),
                            "Save Button Clicked with filled required field");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 17);

                } else {
                    m_assert.assertTrue(bCategoryFound, "Create Category Name as " + categoryName + " In category master ");
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

    @Test(enabled = true, description = "Validating Search Item Functionality in Item Master")
    public void validateSearchFunctionalityInItemMaster() {

        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        boolean bItemFoundInItemMaster = false;

        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                // Select Item Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Item Master");

                // Search Existing Item In Item Master List

                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.button_addItem),
                        "Add item button Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_inventoryItemSearch, hsnCode),
                        "Item Searched By HSN Code as <b>" + hsnCode + "</b>");

                Cls_Generic_Methods.customWait(2);
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.customWait(1);

                Cls_Generic_Methods.clickElement(oPage_ItemMaster.div_searchButton);
                Cls_Generic_Methods.customWait(2);



                for (WebElement itemRow : oPage_ItemMaster.list_itemRowListInItemMaster) {
                    if (Cls_Generic_Methods.isElementDisplayed(itemRow)) {
                        List<WebElement> itemDetailsInRow = itemRow.findElements(By.xpath("./child::*"));
                        String itemDescription = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));
                        String itemCategory = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((2)));
                        String itemSubCategory = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((3)));
                        String itemHsnCode = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((4)));
                        String itemTax = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((5)));
                        if (itemDescription.equalsIgnoreCase(itemDescription)
                                && itemCategory.equalsIgnoreCase(categoryName)
                                && itemHsnCode.equalsIgnoreCase(hsnCode)
                                && itemSubCategory.equalsIgnoreCase(subCategoryName)
                                && itemPropertiesTax.contains(itemTax)
                        ) {
                            bItemFoundInItemMaster = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bItemFoundInItemMaster, "Item Found in Item Master List, Search Working Successfully");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Edit Item Functionality in Item Master")
    public void validateEditFunctionalityInItemMaster() {

        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);


        boolean bItemFoundInItemMaster = false;

        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                // Select Item Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Item Master");

                // Search Existing Item In Item Master List and Clicked on Edit

                Cls_Generic_Methods.waitForPageLoad(driver, 5);

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_inventoryItemSearch, hsnCode),
                        "Item Searched By HSN Code as <b>" + hsnCode + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_searchedItemEditButton, 3);
                Cls_Generic_Methods.customWait(2);
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.customWait(1);

                Cls_Generic_Methods.clickElement(oPage_ItemMaster.div_searchButton);
                Cls_Generic_Methods.customWait(2);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_searchedItemEditButton),
                        "Edit Button Clicked for Searched Item in Item Master List ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.header_editItemHeader, 2);

                // Edit Item with New Information

                // m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_editItemSubCategory),
                //         "Sub Category Dropdown Arrow Clicked In Add Item");
                //Cls_Generic_Methods.customWait(1);

                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_ItemMaster.select_editItemSubCategory, updatedSubCategoryName),
                        "Sub Category Entered as : <b> " + updatedSubCategoryName + "</b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemHsnCode, 2);
                Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_itemHsnCode);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemHsnCode, updatedHsnCode),
                        "Entered Updated HSN Code as : <b>" + updatedHsnCode + "</b>");
                Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_itemDescription);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemDescription, updatedItemDescription),
                        "Entered Updated Description as : <b>" + updatedItemDescription + "</b>");

                Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_itemPropertiesTaxList);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_ItemMaster.select_itemPropertiesTaxList, updatedItemPropertiesTax),
                        "Item Properties Tax Entered as : <b>" + updatedItemPropertiesTax + "</b>");

                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_ItemMaster.select_itemDispensingUnit, dispensingUnitName),
                        "Dispensing Unit entered as : <b>" + dispensingUnitName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.textbox_dispensingUnitDropdownBox, 2);

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemGenericCompositionName, itemGenericCompositionName),
                        " Item generic Composition Name Entered as : <b> " + itemGenericCompositionName + " </b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.name_itemGenericCompositionHexapeptide, 2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.name_itemGenericCompositionHexapeptide),
                        "Medication Name Clicked on Generic Composition Suggestion Value");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemGenericCompositionValue, itemGenericCompositionValue),
                        " Generic Composition Value Entered as : <b>" + itemGenericCompositionValue + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_ItemMaster.select_itemGenericCompositionUnit, "mg"),
                        " Generic Composition Unit Entered as : <b>" + "mg" + "</b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_saveAddItemTemplate, 2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_saveAddItemTemplate),
                        "Save button Clicked in Edit Item");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 20);

                //  Verifying Updated value in Edit Item Template

                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_inventoryItemSearch, updatedHsnCode),
                        "Item Searched By HSN Code as <b>" + updatedHsnCode + "</b>");

                Cls_Generic_Methods.customWait(2);
               // Cls_Generic_Methods.scrollToTop();
                //Cls_Generic_Methods.customWait(1);

                Cls_Generic_Methods.clickElement(oPage_ItemMaster.div_searchButton);
                Cls_Generic_Methods.customWait(2);


                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_searchedItemEditButton, 5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_searchedItemEditButton),
                        "Edit Button Clicked for Searched Item in Item Master List ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.header_editItemHeader, 2);
                m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_editItemSubCategory).contains(updatedSubCategoryName),
                        "Sub Category Edited Successfully");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_itemHsnCode, "value").contains(updatedHsnCode),
                        "HSN code edited successfully");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_itemDescription, "value").contains(updatedItemDescription),
                        "Item Description edited successfully");
                m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_itemPropertiesTaxList).contains(updatedItemPropertiesTax),
                        "Tax Field Edited Successfully");
                m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_itemDispensingUnit).contains(dispensingUnitName),
                        "Dispensing Unit Edited Successfully");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_itemGenericCompositionName, "value").contains(itemGenericCompositionName),
                        "Generic Composition name edited successfully");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_itemGenericCompositionValue, "value").contains(itemGenericCompositionValue),
                        "Generic Composition value edited successfully");
                m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_itemGenericCompositionUnit).contains("mg"),
                        "Dispensing Unit Edited Successfully");

                // Verifying Non Editable Fields

                m_assert.assertFalse(Cls_Generic_Methods.isElementEnabled(oPage_ItemMaster.select_itemCategory),
                        "Category Dropdown is Disabled");
                m_assert.assertFalse(Cls_Generic_Methods.isElementEnabled(oPage_ItemMaster.checkbox_propertiesUnitLevel),
                        "Properties Unit Level Checkbox is Disabled");
                m_assert.assertFalse(Cls_Generic_Methods.isElementEnabled(oPage_ItemMaster.span_inventoryItemPackageType),
                        "Package Type Dropdown is Disabled");
                m_assert.assertFalse(Cls_Generic_Methods.isElementEnabled(oPage_ItemMaster.input_inventoryItemSubPackageType),
                        "Sub Package Type Dropdown is Disabled");
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_ItemMaster.input_inventoryItemSubPackageUnit),
                        "Sub Package Unit Dropdown is Disabled");
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_ItemMaster.input_inventoryItemSubPackageItemUnit),
                        "Sub Package Item Unit Dropdown is Disabled");
                m_assert.assertFalse(Cls_Generic_Methods.isElementEnabled(oPage_ItemMaster.input_inventoryItemSubPackageItemType),
                        "Sub Package Item Type Dropdown is Disabled");
                m_assert.assertFalse(Cls_Generic_Methods.isElementEnabled(oPage_ItemMaster.input_inventoryItemFixedThreshold),
                        "Fixed Threshold Input Box is Disabled");
                m_assert.assertFalse(Cls_Generic_Methods.isElementEnabled(oPage_ItemMaster.input_inventoryItemMedicineClass),
                        " Class Input Box is Disabled");
                m_assert.assertFalse(Cls_Generic_Methods.isElementEnabled(oPage_ItemMaster.input_itemPossibleVariantName),
                        " Possible Variant Name is Disabled");
                m_assert.assertFalse(Cls_Generic_Methods.isElementEnabled(oPage_ItemMaster.input_itemPossibleVariantValue),
                        " Possible Variant Value is Disabled");

                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ItemMaster.button_closeItemMasterTemplate),
                        "Close button In Item Master Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 3);

                // Verify Updated Item in Item Master List

                for (WebElement itemRow : oPage_ItemMaster.list_itemRowListInItemMaster) {
                    if (Cls_Generic_Methods.isElementDisplayed(itemRow)) {
                        List<WebElement> itemDetailsInRow = itemRow.findElements(By.xpath("./child::*"));
                        String itemDescription = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));
                        String itemCategory = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((2)));
                        String itemSubCategory = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((3)));
                        String itemHsnCode = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((4)));
                        String itemTax = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((5)));
                        if (itemDescription.equalsIgnoreCase(itemDescription)
                                && itemCategory.equalsIgnoreCase(categoryName)
                                && itemHsnCode.equalsIgnoreCase(updatedHsnCode)
                                // && itemSubCategory.equalsIgnoreCase(updatedSubCategoryName)
                                && updatedItemPropertiesTax.contains(itemTax)
                        ) {
                            bItemFoundInItemMaster = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bItemFoundInItemMaster, "Item Found in Item Master List, Edit Working Successfully");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Item For Linked Category In Store Inventory")
    public void validateItemForLinkedCategoryInStoreInventory() {

        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);

        boolean bItemFoundInItemMaster = false;
        boolean bCategoryAlreadyLinkedInStoreInventory = true;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_facilitySelector),
                        "Facility Selector Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.text_selectedFacilityName, 2);
                String facilityName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_selectedFacilityName);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");

                Cls_Generic_Methods.waitForPageLoad(driver, 3);

                boolean clickOnLinkActions = CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetUp.list_facilityNamesForInventory, facilityName,
                        oPage_StoreSetUp.list_storeColumnLinkedToFacility, oPage_StoreSetUp.list_buttonColumnLinkedToStores, "Link", "Optical Store 1", "Disable");
                m_assert.assertTrue(clickOnLinkActions, "Link Button is Clicked in Store Setup List");

                if (clickOnLinkActions) {

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.dropdown_actionPerformedOnLinkDropdown, 2);
                    m_assert.assertTrue(selectCategoryFromDropdown(oPage_StoreSetUp.list_linkActionDropdown, "Category"),
                            "Category Clicked In Link Dropdown");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.input_selectStoreInLinkExistingStore, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore),
                            "Store input Box clicked in Link Existing Store");
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore, categoryName);
                    Cls_Generic_Methods.customWait(2);
                    for (WebElement category : oPage_StoreSetUp.list_categoriesInDropdown) {
                        if (Cls_Generic_Methods.getTextInElement(category).equalsIgnoreCase(categoryName)) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(category),
                                    " Clicked on category name :<b>" + categoryName + "</b>");
                            bCategoryAlreadyLinkedInStoreInventory = false;
                            break;
                        }
                    }
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.button_saveLinkCategory),
                            " Save Button clicked in Link Category");

                    if (bCategoryAlreadyLinkedInStoreInventory) {

                        m_assert.assertInfo(" Category is already linked ");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                                "Closing Link Existing Dispensing Unit ");
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 5);

                    boolean storeSelectedOnApp = CommonActions.selectStoreOnApp(storeNameInStoreSetup);
                    m_assert.assertTrue(storeSelectedOnApp, "Store found " + storeNameInStoreSetup);

                    if (storeSelectedOnApp) {
                        Cls_Generic_Methods.switchToOtherTab();
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.text_opticalStoreTitle, 2);
                       // m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetUp.text_opticalStoreTitle),
                             //   "Optical Store Title Displayed");
                        Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                        Cls_Generic_Methods.customWait(1);
                        CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_searchInventoryInStoreInventory, 2);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, updatedItemDescription);
                        Cls_Generic_Methods.customWait(5);
                        oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
                        Cls_Generic_Methods.customWait(5);
                        for (WebElement items : oPage_ItemMaster.list_itemListInStoreInventory) {
                            List<WebElement> itemNameRow = items.findElements(By.xpath("./child::*"));
                            String itemDescription = Cls_Generic_Methods.getTextInElement(itemNameRow.get((1)));

                            if (itemDescription.contains(updatedItemDescription)) {
                                bItemFoundInItemMaster = true;
                                break;
                            }

                        }
                        m_assert.assertTrue(bItemFoundInItemMaster, " Item created for category is linked to store inventory");
                        Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                        Cls_Generic_Methods.customWait();
                    }
                } else {
                    m_assert.assertTrue(clickOnLinkActions, "Link Button is not Clicked in Store Setup List");
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

    @Test(enabled = true, description = "Validating Item For Unlinked Category In Store Inventory")
    public void validateItemForUnlinkedCategoryInStoreInventory() {

        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);

        boolean bItemNotFoundInItemMaster = false;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_facilitySelector),
                        "Facility Selector Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.text_selectedFacilityName, 2);
                String facilityName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_selectedFacilityName);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");

                Cls_Generic_Methods.waitForPageLoad(driver, 3);

                boolean clickOnUnlinkActions = CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetUp.list_facilityNamesForInventory, facilityName,
                        oPage_StoreSetUp.list_storeColumnLinkedToFacility, oPage_StoreSetUp.list_buttonColumnLinkedToStores, "Un-Link", "Optical Store 1", "Disable");
                m_assert.assertTrue(clickOnUnlinkActions, "Link Button is Clicked in Store Setup List");

                if (clickOnUnlinkActions) {

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.dropdown_actionPerformedOnLinkDropdown, 2);
                    m_assert.assertTrue(selectCategoryFromDropdown(oPage_StoreSetUp.list_linkActionDropdown, "Category"),
                            "Category Clicked In Link Dropdown");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.input_selectStoreInLinkExistingStore, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore),
                            "Store input Box clicked in Link Existing Store");
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore, categoryName);
                    Cls_Generic_Methods.customWait(2);
                    for (WebElement category : oPage_StoreSetUp.list_categoriesInDropdown) {
                        if (Cls_Generic_Methods.getTextInElement(category).equalsIgnoreCase(categoryName)) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(category),
                                    " Category Selected in Store setup as : <b> " + categoryName + "</b>");
                            break;
                        }
                    }
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.button_saveLinkCategory),
                            "Save Button Clicked in Link Category");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 5);

                    boolean storeSelectedOnApp = CommonActions.selectStoreOnApp(storeNameInStoreSetup);
                    m_assert.assertTrue(storeSelectedOnApp, "Store found " + storeNameInStoreSetup);

                    if (storeSelectedOnApp) {
                        Cls_Generic_Methods.switchToOtherTab();
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.text_opticalStoreTitle, 2);
                        //m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetUp.text_opticalStoreTitle),
                               // "Optical Store Title Displayed");
                        Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                        Cls_Generic_Methods.customWait(1);
                        CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, updatedItemDescription);
                        Cls_Generic_Methods.customWait(5);
                        List<WebElement> itemNameList = oPage_ItemMaster.list_emptyItemListInStoreInventory.findElements(By.xpath("./tr"));
                        if (itemNameList.size() == 0) {
                            bItemNotFoundInItemMaster = true;
                        }
                        m_assert.assertTrue(bItemNotFoundInItemMaster, " Item created for category is linked to store inventory");
                        Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                    }
                } else {
                    m_assert.assertTrue(clickOnUnlinkActions, "Link Button is not Clicked in Store Setup List");
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

    @Test(enabled = true, description = "Validate Category And SubCategory Filter Functionality In ItemMaster")
    public void validateCategoryAndSubCategoryFilterFunctionalityInItemMaster() {

        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        String sCategoryWithNoLinkedSubCategory = "Category with No subcategory";
        String sCategoryWithLinkedSubCategory = "Category with multiple subcategory";
        String sSubCategory = "SubcategoryAutomationTest";
        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                // Select Item Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Item Master");

                // Validate category filter
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.select_category,30);
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.customWait(3);
                String sSelectedOptionUnderCategoryField = Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_category);
                String sSelectedOptionUnderSubCategoryField = Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_subCategory);
                m_assert.assertTrue(sSelectedOptionUnderCategoryField.equals("Any"),"By default Any option should be selected under category dropdown  =  <b>"+sSelectedOptionUnderCategoryField+ " </b>");
                m_assert.assertTrue(sSelectedOptionUnderSubCategoryField.equals("Any"),"By default Any option should be selected under Sub category dropdown  =  <b>"+sSelectedOptionUnderSubCategoryField+ " </b>");
                // Validate subcategory filter should be in disable state if there is no subcategory linked with category.
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_ItemMaster.select_category,sCategoryWithNoLinkedSubCategory),
                        "Selected category = <b> "+sCategoryWithNoLinkedSubCategory+"</b>");
               if(Cls_Generic_Methods.isElementEnabled(oPage_ItemMaster.select_subCategory))
                {
                   m_assert.assertWarn("Ideally subcategory selection dropdown should be disabled as there is no subcategory linked to the selected category");
                }
                else{
                   m_assert.assertTrue("As expected subcategory selection dropdown is in disabled state");
                   Cls_Generic_Methods.customWait(7);
                   for (WebElement eSubCategory: oPage_ItemMaster.list_linkedSubcategoryLinkedForSelectedCategory) {
                       String sSubCategoryName = Cls_Generic_Methods.getTextInElement(eSubCategory);
                       if(sSubCategoryName.isEmpty()){
                          m_assert.assertTrue("subcategory field is blank as expected");
                       }
                       else{
                           m_assert.assertWarn("Items are not loading based on the filtered category");
                       }
                   }
                }

                // Validate subcategory filter should be in enabled state if a category linked with least one subcategory.
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_ItemMaster.select_category,sCategoryWithLinkedSubCategory),
                        "Selected category = <b> "+sCategoryWithLinkedSubCategory+"</b>");
                Cls_Generic_Methods.customWait(5);
                if(Cls_Generic_Methods.isElementEnabled(oPage_ItemMaster.select_subCategory))
                {
                    Cls_Generic_Methods.customWait(3);
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_ItemMaster.select_subCategory,sSubCategory),
                            "Selected subcategory = <b> "+sSubCategory+"</b>");
                    Cls_Generic_Methods.customWait(3);
                    m_assert.assertTrue("Subcategory field is in Enabled state");
                    Cls_Generic_Methods.customWait(7);
                    for (WebElement eSubCategory: oPage_ItemMaster.list_linkedSubcategoryLinkedForSelectedCategory) {
                        String sSubCategoryName = Cls_Generic_Methods.getTextInElement(eSubCategory);
                        if(sSubCategoryName.equals(sSubCategory)){
                            m_assert.assertTrue("Expected items are loaded based on the selected subcategory=  <b>"+sSubCategoryName+"</b>");
                        }
                        else{
                            m_assert.assertWarn("Items are not loading based on the filtered category");
                        }
                    }

                }
                else{
                    m_assert.assertWarn("Ideally subcategory selection dropdown should be in enabled state but currently it is in disabled state");

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