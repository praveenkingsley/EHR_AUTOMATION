package tests.settings.organisationSettings.inventoryAndSupplyChain;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.Settings_Data;
import data.settingsData.OrganisationSettings_Data;
import org.jsoup.select.CombiningEvaluator;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_VendorRateList;

import java.awt.image.BandCombineOp;

import static pages.commonElements.CommonActions.getActionsOfSelectedStoreOfFacility;
import static pages.commonElements.CommonActions.oEHR_Data;


public class VendorRateListTest extends TestBase {


    @Test(enabled = true, description = "Add vendor rate and validate the same")
    public void validateAddVendorRate() {
        Page_VendorRateList oPage_VendorRateList = new Page_VendorRateList(driver);
        int indexOfItemName = -1;
        int indexOfItemRate = -1;
        boolean itemFoundForAddingRate = false;
        boolean itemFoundInTable = false;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Rate List");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorRateList.button_addVendorRate, 3);
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_VendorRateList.button_addVendorRate),
                        "Add Vendor Rate Button clicked");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorRateList.dropdown_vendorSelect, 5);

                Cls_Generic_Methods.clickElement(oPage_VendorRateList.dropdown_vendorSelect);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_VendorRateList.dropdown_vendorSelect, OrganisationSettings_Data.sVendor_Name),
                        "Vendor selected: <b> " + OrganisationSettings_Data.sVendor_Name + " </b>");

                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorRateList.input_searchItemVariant, OrganisationSettings_Data.sItem_Name_VendorRateList);
                Cls_Generic_Methods.customWait();
                for (WebElement eItemName : oPage_VendorRateList.list_itemNameForVendorRate) {
                    if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(OrganisationSettings_Data.sItem_Name_VendorRateList)) {
                        indexOfItemName = oPage_VendorRateList.list_itemNameForVendorRate.indexOf(eItemName);
                        itemFoundForAddingRate = true;
                        break;
                    }
                }
                m_assert.assertTrue(itemFoundForAddingRate, "<b> " + OrganisationSettings_Data.sItem_Name_VendorRateList + " present at index: " + indexOfItemName + "</b>");

                if (itemFoundForAddingRate) {

                    Cls_Generic_Methods.clearValuesInElement(oPage_VendorRateList.list_input_itemRate.get(indexOfItemName));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorRateList.list_input_itemRate.get(indexOfItemName), OrganisationSettings_Data.sRate),
                            "Rate entered: " + OrganisationSettings_Data.sRate);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorRateList.button_saveVendorRateList),
                            "Save clicked for vendor rate.");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorRateList.button_addVendorRate, 10);
                }

                // validate in table
                for (WebElement e : oPage_VendorRateList.list_itemNameInTable) {
                    if (Cls_Generic_Methods.getTextInElement(e).equalsIgnoreCase(OrganisationSettings_Data.sItem_Name_VendorRateList)) {
                        itemFoundInTable = true;
                        break;
                    }
                }

                m_assert.assertTrue(itemFoundInTable, "Item rate added successfully : <b> " + OrganisationSettings_Data.sItem_Name_VendorRateList + " </b>");

            } catch (Exception e) {
                m_assert.assertFatal(e + "");
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal(e + "");
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate the added vendor rate for item in store")
    public void validateVendorRateInStore() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_VendorRateList oPage_VendorRateList = new Page_VendorRateList(driver);
        boolean vendorRateDefined = false;
        boolean vendorFound = false;
        boolean itemFoundInPurchase = false;
        boolean updatedRate = false;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            boolean storeSelectedOnApp = CommonActions.selectStoreOnApp(OrganisationSettings_Data.sStore_To_Select);

            if (storeSelectedOnApp) {
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                Cls_Generic_Methods.customWait();

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorRateList.button_transactionPurchaseNew, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorRateList.button_transactionPurchaseNew),
                        "New button clicked for purchase transaction");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorRateList.dropdown_selectVendorInStore, 5);
                Cls_Generic_Methods.clickElement(oPage_VendorRateList.dropdown_selectVendorInStore);

                for (WebElement eVendor : oPage_VendorRateList.list_selectVendorInStore) {
                    if (eVendor.getText().equalsIgnoreCase(OrganisationSettings_Data.sVendor_Name)) {
                        Cls_Generic_Methods.clickElement(eVendor);
                        vendorFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(vendorFound, "Vendor present in dropdown: " + OrganisationSettings_Data.sVendor_Name);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorRateList.header_addNewStock, 15);

                for (WebElement eItemName : oPage_VendorRateList.list_itemNameInPurchaseStore) {
                    if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(OrganisationSettings_Data.sItem_Name_VendorRateList)) {
                        Cls_Generic_Methods.clickElement(eItemName);
                        itemFoundInPurchase = true;
                        break;
                    }
                }

                m_assert.assertTrue(itemFoundInPurchase, "Item found in purchase: " + OrganisationSettings_Data.sItem_Name_VendorRateList);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorRateList.header_addNewLot, 15);

                String vendorRateOfItem = Cls_Generic_Methods.getElementAttribute(oPage_VendorRateList.input_unitCostWOTax, "value");
                Cls_Generic_Methods.customWait(5);
                if (vendorRateOfItem.equalsIgnoreCase(OrganisationSettings_Data.sRate) || vendorRateOfItem.equalsIgnoreCase(OrganisationSettings_Data.sUpdated_Rate)) {
                    vendorRateDefined = true;
                }
                m_assert.assertTrue(vendorRateDefined, "Vendor rate defined is visible in stock purchase.");

                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

            }
        } catch (Exception e) {
            m_assert.assertFatal(e + "");
            e.printStackTrace();
        }

    }

    @Test(enabled = true, description = "Validate editing ")
    public void validateEditVendorRate() {
        Page_VendorRateList oPage_VendorRateList = new Page_VendorRateList(driver);
        int indexOfItemName = -1;
        int indexOfEditButton = -1;
        boolean itemFoundInTable = false;
        boolean editButtonFound = false;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Rate List");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorRateList.button_addVendorRate, 3);
            for (WebElement eItem : oPage_VendorRateList.list_itemNameInTable) {
                if (eItem.getText().equalsIgnoreCase(OrganisationSettings_Data.sItem_Name_VendorRateList)) {
                    indexOfItemName = oPage_VendorRateList.list_itemNameInTable.indexOf(eItem);
                    itemFoundInTable = true;
                    break;
                }
            }
            m_assert.assertTrue(itemFoundInTable, "Item found successfully : <b> " + OrganisationSettings_Data.sItem_Name_VendorRateList + " </b>");

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorRateList.list_editItemRate.get(indexOfItemName)),
                    "Edit button clicked for:" + OrganisationSettings_Data.sItem_Name_VendorRateList);

            //m_assert.assertTrue(editButtonFound, "Edit button clicked for: " + OrganisationSettings_Data.sItem_Name_VendorRateList);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorRateList.dropdown_vendorGroupName, 10);
            Cls_Generic_Methods.clearValuesInElement(oPage_VendorRateList.input_editRate);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorRateList.input_editRate, OrganisationSettings_Data.sUpdated_Rate),
                    "Updated Vendor Rate: <b> " + OrganisationSettings_Data.sUpdated_Rate + "</b>");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_VendorRateList.dropdown_vendorGroupName);
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_VendorRateList.dropdown_vendorGroupName, OrganisationSettings_Data.sVendor_Group_Name),
                    "Vendor group name updated: " + OrganisationSettings_Data.sVendor_Group_Name);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorRateList.button_saveChanges),
                    "Button clicked for saving updates");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorRateList.button_addVendorRate, 5);

            validateVendorRateInStore();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validate filter of vendor name")
    public void validateVendorFilterListPage() {
        Page_VendorRateList oPage_VendorRateList = new Page_VendorRateList(driver);
        int countOfVendorName = 0;
        boolean vendorFound = false;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Rate List");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorRateList.button_addVendorRate, 3);
            Cls_Generic_Methods.scrollToTop();
            Cls_Generic_Methods.clickElementByJS(driver, oPage_VendorRateList.dropdown_vendorFilter);
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_VendorRateList.dropdown_vendorFilter, OrganisationSettings_Data.sVendor_Name),
                    "Vendor selected for filter:" + OrganisationSettings_Data.sVendor_Name);

            Cls_Generic_Methods.customWait();
            for (WebElement eVendor : oPage_VendorRateList.list_vendorNameInTable) {
                if (Cls_Generic_Methods.getTextInElement(eVendor).equalsIgnoreCase(OrganisationSettings_Data.sVendor_Name)) {
                    countOfVendorName++;
                    vendorFound = true;
                }
            }

            m_assert.assertTrue(vendorFound, "Vendor filter working.<b> " + OrganisationSettings_Data.sVendor_Name + " </b> is assigned <b> " + countOfVendorName + " </b> times");

            m_assert.assertTrue(countOfVendorName > 0, "Validate that the number of items for Vendor " + OrganisationSettings_Data.sVendor_Name + " is greater than 0");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validate search of vendor group name")
    public void validateSearchByVendorGroupName() {
        Page_VendorRateList oPage_VendorRateList = new Page_VendorRateList(driver);
        int countOfVendorGroupName = 0;
        boolean vendorGroupFound = false;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Rate List");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorRateList.button_addVendorRate, 3);
            Cls_Generic_Methods.scrollToTop();
            Cls_Generic_Methods.clickElement(driver, oPage_VendorRateList.input_searchVendorGroup);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorRateList.input_searchVendorGroup, OrganisationSettings_Data.sVendor_Group_Name);

            Cls_Generic_Methods.customWait();
            for (WebElement eVendor : oPage_VendorRateList.list_vendorGroupNameInTable) {
                if (Cls_Generic_Methods.getTextInElement(eVendor).equalsIgnoreCase(OrganisationSettings_Data.sVendor_Group_Name)) {
                    countOfVendorGroupName++;
                    vendorGroupFound = true;
                }
            }

            m_assert.assertTrue(vendorGroupFound, "Search Vendor group working.<b> " + OrganisationSettings_Data.sVendor_Group_Name + " </b> is assigned <b> " + countOfVendorGroupName + " </b> times");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

}