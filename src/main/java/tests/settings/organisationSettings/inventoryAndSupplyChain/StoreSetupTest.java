package tests.settings.organisationSettings.inventoryAndSupplyChain;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.Settings_Data;
import data.settingsData.OrganisationSettings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static pages.commonElements.CommonActions.oEHR_Data;


public class StoreSetupTest extends TestBase {

    String storeNameInStoreSetup = "OpticalStore";

    @Test(enabled = true, description = "")
    public void validateAddStoreFunctionality() {
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        boolean bFacilitySelected = false;
        boolean bCountrySelected = false;
        boolean bEntitySelected = false;
        boolean bStoreTypeSelected = false;
        List<WebElement> listOfRequiredStores = new ArrayList<>();

        boolean bStoreFound = false;
        int indexOfFacility = -1;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_StoreSetUp.button_addStore),
                        "Add Store Button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.input_storeName, 5);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_storeName, OrganisationSettings_Data.sSTORE_NAME)
                        , "Store Name entered: <b> " + OrganisationSettings_Data.sSTORE_NAME + " </b>");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_storeAbbreviation, OrganisationSettings_Data.sSTORE_ABBREVIATION_NAME)
                        , "Store Abbreviation Name entered: <b> " + OrganisationSettings_Data.sSTORE_ABBREVIATION_NAME + " </b>");
                //Select type of store
                Cls_Generic_Methods.clickElement(oPage_StoreSetUp.dropdown_storeType);
                for (WebElement eStoreType : oPage_StoreSetUp.list_storeType) {
                    if (Cls_Generic_Methods.isElementDisplayed(eStoreType)) {
                        if (Cls_Generic_Methods.getTextInElement(eStoreType).contains(OrganisationSettings_Data.sSTORE_TYPE)) {
                            Cls_Generic_Methods.clickElement(eStoreType);
                            bStoreTypeSelected = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bStoreTypeSelected, "Store Type selected <b> " + OrganisationSettings_Data.sSTORE_TYPE + "</b>");
                //Select Facility
                Cls_Generic_Methods.clickElement(oPage_StoreSetUp.dropdown_selectFacility);
                // Cls_Generic_Methods.customWait();
                for (WebElement eFacilityName : oPage_StoreSetUp.list_storeFacilities) {
                    if (Cls_Generic_Methods.isElementDisplayed(eFacilityName)) {
                        if (Cls_Generic_Methods.getTextInElement(eFacilityName).contains(OrganisationSettings_Data.sSTORE_FACILITY)) {
                            Cls_Generic_Methods.clickElement(eFacilityName);
                            bFacilitySelected = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bFacilitySelected, "Facility selected " + OrganisationSettings_Data.sSTORE_FACILITY + "</b>");
                //Select entity
                Cls_Generic_Methods.clickElement(oPage_StoreSetUp.dropdown_entityGroup);
                for (WebElement eEntity : oPage_StoreSetUp.list_storeEntityGroup) {
                    if (Cls_Generic_Methods.isElementDisplayed(eEntity)) {
                        if (Cls_Generic_Methods.getTextInElement(eEntity).contains(OrganisationSettings_Data.sSTORE_ENTITY)) {
                            Cls_Generic_Methods.clickElement(eEntity);
                            bEntitySelected = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bEntitySelected, "Entity selected <b> " + OrganisationSettings_Data.sSTORE_ENTITY + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_mobileNumber, OrganisationSettings_Data.sSTORE_MOBILE_NUMBER)
                        , "Store Mobile Number entered: <b> " + OrganisationSettings_Data.sSTORE_MOBILE_NUMBER + " </b>");
                //Select country
                Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.dropdown_storeCountry);
                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_search,OrganisationSettings_Data.sSTORE_COUNTRY);
                Cls_Generic_Methods.customWait(1);

                for (WebElement eCountryName : oPage_StoreSetUp.list_countryNames) {
                    if (Cls_Generic_Methods.isElementDisplayed(eCountryName)) {
                        if (Cls_Generic_Methods.getTextInElement(eCountryName).contains(OrganisationSettings_Data.sSTORE_COUNTRY)) {
                            Cls_Generic_Methods.clickElement(eCountryName);
                            Cls_Generic_Methods.customWait(1);

                            bCountrySelected = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bCountrySelected, "Country selected  <b> " + OrganisationSettings_Data.sSTORE_COUNTRY + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_storeAddress, OrganisationSettings_Data.sSTORE_ADDRESS)
                        , "Store Address entered: <b> " + OrganisationSettings_Data.sSTORE_ADDRESS + " </b>");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_storePincode, OrganisationSettings_Data.sPINCODE)
                        , "Store Pincode entered: <b> " + OrganisationSettings_Data.sPINCODE + " </b>");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_storeState, OrganisationSettings_Data.sSTORE_STATE)
                        , "Store State entered: <b> " + OrganisationSettings_Data.sSTORE_STATE + " </b>");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_storeCity, OrganisationSettings_Data.sSTORE_CITY)
                        , "Store City entered: <b> " + OrganisationSettings_Data.sSTORE_CITY + " </b>");

                Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.dropdown_storeCountry);
                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_search,OrganisationSettings_Data.sSTORE_COUNTRY);
                Cls_Generic_Methods.customWait(2);

                for (WebElement eCountryName : oPage_StoreSetUp.list_countryNamesBilling) {
                    if (Cls_Generic_Methods.isElementDisplayed(eCountryName)) {
                        if (Cls_Generic_Methods.getTextInElement(eCountryName).contains(OrganisationSettings_Data.sSTORE_COUNTRY)) {
                            Cls_Generic_Methods.clickElement(eCountryName);
                            Cls_Generic_Methods.customWait(1);

                            bCountrySelected = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bCountrySelected, "Country selected  <b> " + OrganisationSettings_Data.sSTORE_COUNTRY + "</b>");

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_billingAddress, OrganisationSettings_Data.sSTORE_ADDRESS)
                        , "Store Address entered: <b> " + OrganisationSettings_Data.sSTORE_ADDRESS + " </b>");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_storeBillingPincode, OrganisationSettings_Data.sPINCODE)
                        , "Store Pincode entered: <b> " + OrganisationSettings_Data.sPINCODE + " </b>");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_storeBillingState, OrganisationSettings_Data.sSTORE_STATE)
                        , "Store State entered: <b> " + OrganisationSettings_Data.sSTORE_STATE + " </b>");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_storeBillingCity, OrganisationSettings_Data.sSTORE_CITY)
                        , "Store City entered: <b> " + OrganisationSettings_Data.sSTORE_CITY + " </b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.button_saveStore),
                        "Store Saved");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 3);
                // finding the added store in the corresponding facility
                try {
                    for (WebElement facilityName : oPage_StoreSetUp.list_facilityNamesForInventory) {
                        if (OrganisationSettings_Data.sSTORE_FACILITY.contains(Cls_Generic_Methods.getTextInElement(facilityName))) {
                            indexOfFacility = oPage_StoreSetUp.list_facilityNamesForInventory.indexOf(facilityName);
                            Cls_Generic_Methods.scrollToElementByJS(facilityName);
                            break;
                        }
                    }
                    List<WebElement> storesNameList = oPage_StoreSetUp.list_storeColumnLinkedToFacility.get(indexOfFacility).
                            findElements(By.xpath("./child::*"));
                    List<WebElement> listOfStores = storesNameList.get(0).findElements(By.xpath(".//tr"));
                    List<String> store = new ArrayList<>();
                    for (WebElement storeName : listOfStores) {
                        listOfRequiredStores.add(storeName);
                    }
                    for (WebElement storeName : listOfRequiredStores) {
                        if (Cls_Generic_Methods.getTextInElement(storeName).contains(OrganisationSettings_Data.sSTORE_NAME)) {
                            Cls_Generic_Methods.waitForElementToBeDisplayed(storeName, 3);
                            bStoreFound = true;
                            break;
                        }
                    }
                    for (int i = 0; i < listOfRequiredStores.size(); i++) {
                        store.add(listOfRequiredStores.get(i).getText());
                    }
                    m_assert.assertTrue(bStoreFound, "Store created successfully.List of stores for facility :<b> " + OrganisationSettings_Data.sSTORE_FACILITY + "</b> are <b> " + store + "</b>");
                } catch (Exception e) {
                    m_assert.assertFatal("Store not created " + e);
                    e.printStackTrace();
                }
            } catch (Exception e) {
                m_assert.assertFatal("Error loading the application " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "")
    public void validateEditFunctionalityForStore() {
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        boolean bStoreFound = false;
        boolean bUpdatedStoreFound = false;
        String updatedStoreName = "Automation_Store Updated"+CommonActions.getRandomString(4);
        String updatedStoreAbbreviationName = "AutoUpdate";
        String updatedStoreMobile = "9999999999";
        String updatedStoreAddress = "Testing store address Updated";

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 3);

                bStoreFound = CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetUp.list_facilityNamesForInventory, OrganisationSettings_Data.sSTORE_FACILITY,
                        oPage_StoreSetUp.list_storeColumnLinkedToFacility, oPage_StoreSetUp.list_buttonColumnLinkedToStores,
                        "Edit", OrganisationSettings_Data.sSTORE_NAME, "Disable");

                m_assert.assertTrue(bStoreFound, "Validate Store <b>" + OrganisationSettings_Data.sSTORE_NAME + "</b> is found");
                if (bStoreFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.input_storeName, 5);
                    Cls_Generic_Methods.clearValuesInElement(oPage_StoreSetUp.input_storeName);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_storeName, updatedStoreName),
                            "Entered updated store name: " + updatedStoreName);
                    Cls_Generic_Methods.clearValuesInElement(oPage_StoreSetUp.input_storeAbbreviation);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_storeAbbreviation, updatedStoreAbbreviationName),
                            "Entered updated Store Abbreviation name:" + updatedStoreAbbreviationName);
                    Cls_Generic_Methods.clearValuesInElement(oPage_StoreSetUp.input_mobileNumber);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_mobileNumber, updatedStoreMobile),
                            "Entered updated mobile number:" + updatedStoreMobile);
                   // Cls_Generic_Methods.clearValuesInElement(oPage_StoreSetUp.input_storeAddress);
                   // m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_storeAddress, updatedStoreAddress),
                           // "Entered updated Address: " + updatedStoreAddress);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.button_updateStore),
                            "Update store clicked ");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 5);
                }

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 3);


                bUpdatedStoreFound = CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetUp.list_facilityNamesForInventory, OrganisationSettings_Data.sSTORE_FACILITY,
                        oPage_StoreSetUp.list_storeColumnLinkedToFacility, oPage_StoreSetUp.list_buttonColumnLinkedToStores,
                        "Edit", updatedStoreName, "Disable");

                m_assert.assertTrue(bUpdatedStoreFound, "Validate Store <b>" + updatedStoreName + "</b> is found");
                if (bUpdatedStoreFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.input_storeName, 8);

                    m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_StoreSetUp.input_storeName, "value").equalsIgnoreCase(updatedStoreName),
                            "Store name updated: <b> " + updatedStoreName + "</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_StoreSetUp.input_storeAbbreviation, "value").equalsIgnoreCase(updatedStoreAbbreviationName),
                            "Store abbreviation updated:<b> " + updatedStoreAbbreviationName + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_StoreSetUp.input_mobileNumber, "value").equalsIgnoreCase(updatedStoreMobile),
                            "Store mobile number updated: <b> " + updatedStoreMobile + "</b>");
                    //m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_StoreSetUp.input_storeAddress, "value").equalsIgnoreCase(updatedStoreAddress),
                            //"Store address updated:<b> " + updatedStoreAddress + "</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.button_closeEditStore),
                            "Edit Store modal closed");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 5);

                }

            } catch (Exception e) {
                m_assert.assertFatal("Error loading the application " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validating Link Store Functionality In Store Setup")
    public void validateLinkStoreFunctionalityForStore() {

        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        List<String> beforeLinkStoresList = new ArrayList<String>();
        List<String> afterEditSelectStoresList = new ArrayList<String>();
        List<String> linkedStoreListInOrderReceivingStore = new ArrayList<String>();
        List<String> facilityAndStoreTypeStoresList = new ArrayList<String>();
        String selectedFacilityName = null;
        String storeType = "Pharmacy";
        boolean isLinkedStore = false;
        boolean isStoreLinkedInReceivingStore = false;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                // Storing Selected Facility Full Name

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_facilitySelector),
                        "Facility Selector Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.text_selectedFacilityName, 2);
                selectedFacilityName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_selectedFacilityName);

                // Selection Store Setup Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");

                // Validating selected Facility in Store Setup and clicking on link button

                boolean clickLinkOperation = CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetUp.list_facilityNamesForInventory,
                        selectedFacilityName, oPage_StoreSetUp.list_storeColumnLinkedToFacility, oPage_StoreSetUp.list_buttonColumnLinkedToStores,
                        "Link", storeNameInStoreSetup, "Disable");

                if (clickLinkOperation) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.dropdown_actionPerformedOnLinkDropdown, 2);

                    //Clicking on Stores In Link Dropdown
                    m_assert.assertTrue(selectOptionFromListBasedOnText(oPage_StoreSetUp.list_linkActionDropdown, "Requisition Stores"),
                            "<b> Stores </b> Selected from Link Dropdown for store : <b> " + storeNameInStoreSetup + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.header_linkExistingStore, 2);

                    // Validating Select Store Displayed for Pharmacy Store Type

                    String facilityNameFullText[] = selectedFacilityName.trim().split("-");
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_StoreSetUp.dropdown_facilityFilter, facilityNameFullText[0]);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.dropdown_facilityFilter, 1);

                    m_assert.assertTrue(facilityNameFullText[0].contains(Cls_Generic_Methods.getSelectedValue(oPage_StoreSetUp.dropdown_facilityFilter)),
                            "Facility Dropdown In Stores Working Correctly ,Facility select as : <b> " + facilityNameFullText[0] + "</b>");

                    Cls_Generic_Methods.selectElementByVisibleText(oPage_StoreSetUp.dropdown_storeTypeFilter, storeType);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.dropdown_facilityFilter, 1);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore),
                            "Store input Box clicked in Link Existing Store");
                    for (WebElement stores : oPage_StoreSetUp.list_selectStoresListInLinkExistingStore) {
                        facilityAndStoreTypeStoresList.add(Cls_Generic_Methods.getTextInElement(stores));
                    }
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore),
                            "Select Store Input Box Clicked");
                    m_assert.assertInfo("Stores List for facility " + selectedFacilityName + "under " + storeType + " store Type: " + "<b>" + facilityAndStoreTypeStoresList + "</b> ");

                    // validating Linking New Store

                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_StoreSetUp.dropdown_facilityFilter, "All"),
                            " All Selected In Facility Dropdown as Facility");
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_StoreSetUp.dropdown_storeTypeFilter, "All"),
                            "\" All Selected In Store Type Dropdown as Store\"");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.dropdown_storeTypeFilter, 1);
                    m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_StoreSetUp.dropdown_storeTypeFilter).equalsIgnoreCase("All"),
                            "Store Type Dropdown is working in Link Existing Store");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore),
                            "Store input Box clicked in Link Existing Store");
                    for (WebElement stores : oPage_StoreSetUp.list_currentlyLinkedStores) {
                        beforeLinkStoresList.add(Cls_Generic_Methods.getTextInElement(stores));
                    }
                    m_assert.assertInfo("Currently Linked Stores List  Before Linking New : <b> " + beforeLinkStoresList + "</b>");

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore, facilityAndStoreTypeStoresList.get(0));
                    m_assert.assertTrue(CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_StoreSetUp.list_selectStoresListInLinkExistingStore, facilityAndStoreTypeStoresList.get(0)),
                            "Store selected for linking: " + facilityAndStoreTypeStoresList.get(0));
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_saveLinkStore, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.button_saveLinkStore), "Clicked on save for linking store");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.text_selectedFacilityName, 2);

                    if (!Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetUp.button_addStore)) {
                        Cls_Generic_Methods.clickElement(oPage_StoreSetUp.button_StoreInventorySetupPage);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.title_StoreSetup, 1);
                    }

                    // Verifying Addition of Linking Store to Currently Linked Store

                    clickLinkOperation = CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetUp.list_facilityNamesForInventory, selectedFacilityName,
                            oPage_StoreSetUp.list_storeColumnLinkedToFacility, oPage_StoreSetUp.list_buttonColumnLinkedToStores, "Link", storeNameInStoreSetup, "Disable");

                    //m_assert.assertTrue(clickLinkOperation,"Link Button Clicked for "+ selectedFacilityName);

                    if (clickLinkOperation) {

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.dropdown_actionPerformedOnLinkDropdown, 2);
                        m_assert.assertTrue(selectOptionFromListBasedOnText(oPage_StoreSetUp.list_linkActionDropdown, "Requisition Stores"),
                                "Stores Clicked In Link Dropdown for facility name " + selectedFacilityName);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.header_linkExistingStore, 2);
                        Cls_Generic_Methods.selectElementByVisibleText(oPage_StoreSetUp.dropdown_storeTypeFilter, "All");
                        Cls_Generic_Methods.customWait(1);
                        for (WebElement linkedStores : oPage_StoreSetUp.list_currentlyLinkedStores) {
                            if (Cls_Generic_Methods.getTextInElement(linkedStores).equalsIgnoreCase(facilityAndStoreTypeStoresList.get(0))) {
                                isLinkedStore = true;
                                break;
                            }
                        }

                        for (WebElement stores : oPage_StoreSetUp.list_currentlyLinkedStores) {
                            afterEditSelectStoresList.add(Cls_Generic_Methods.getTextInElement(stores));
                        }

                        m_assert.assertInfo("Linked Stores List  After Linking New Store : <b> " + afterEditSelectStoresList + "</b>");
                        m_assert.assertTrue(isLinkedStore, facilityAndStoreTypeStoresList.get(0) + " is linked successfully");
                        Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.title_StoreSetup, 3);


                        // Verify Linked Store In Optical Store Order Requisition

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_storesSelector),
                                "Store Dropdown Button Clicked");
                        Cls_Generic_Methods.customWait(1);
                        for (WebElement store : oPage_Navbar.list_storesSelector) {
                            if (Cls_Generic_Methods.getTextInElement(store).contains(storeNameInStoreSetup)) {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(store),
                                        storeNameInStoreSetup + " selected from Store List from navbar");
                                Cls_Generic_Methods.waitForPageLoad(driver, 5);
                            }
                        }

                        Cls_Generic_Methods.switchToOtherTab();
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.text_opticalStoreTitle, 2);
                        m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetUp.text_opticalStoreTitle),
                                "Optical Store Opened");
                        Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                        Cls_Generic_Methods.customWait(1);
                        CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.button_addNewButtonInOrder),
                                "New Button clicked in Order Requisition");

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.dropdown_receivingStoreInRequisition, 3);

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.dropdown_receivingStoreInRequisition),
                                "Receiving Store Dropdown clicked");
                        Cls_Generic_Methods.customWait(1);

                        for (WebElement receivingStore : oPage_StoreSetUp.list_storesListInReceivingStoresRequisition) {

                            if (Cls_Generic_Methods.getTextInElement(receivingStore).contains(facilityAndStoreTypeStoresList.get(0))) {
                                isStoreLinkedInReceivingStore = true;
                            }

                        }

                        m_assert.assertTrue(isStoreLinkedInReceivingStore,
                                "Stores Linked under requisition for store " + storeNameInStoreSetup + "<b> are: " + linkedStoreListInOrderReceivingStore + "</b>");
                        Cls_Generic_Methods.clickElement(oPage_StoreSetUp.button_closeNewTransactionWithoutSaving);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addNewButtonInOrder, 2);
                        Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                    } else {
                        m_assert.assertTrue(clickLinkOperation, "Link Button is not Clicked in Store Setup List");
                    }
                } else {
                    m_assert.assertTrue(clickLinkOperation, "Link Button is not Clicked in Store Setup List");
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

    @Test(enabled = true, description = "Validating Unlink Store Functionality In Store Setup")
    public void validateUnlinkStoreFunctionalityForStore() {

        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        List<String> facilityAndStoreTypeStoresList = new ArrayList<String>();
        List<String> beforeUnLinkSelectStoresList = new ArrayList<String>();
        List<String> afterUnLinkSelectStoresList = new ArrayList<String>();
        List<String> unlinkedStoreListInOrderReceivingStore = new ArrayList<String>();
        String storeType = "Pharmacy";
        String selectedFacilityName = null;
        boolean isUnlinkedStore = false;
        boolean isUnlinkedStoreInReceivingStore = true;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                // Storing Selected Facility Full Name

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_facilitySelector),
                        "Facility Selector Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.text_selectedFacilityName, 2);
                selectedFacilityName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_selectedFacilityName);

                // Selection Store Setup Inventory And Supply Chain
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");

                // Validating selected Facility in Store Setup and clicking on link button

                boolean clickUnlinkOperation = CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetUp.list_facilityNamesForInventory,
                        selectedFacilityName, oPage_StoreSetUp.list_storeColumnLinkedToFacility, oPage_StoreSetUp.list_buttonColumnLinkedToStores,
                        "Un-Link", storeNameInStoreSetup, "Disable");

                if (clickUnlinkOperation) {

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.dropdown_actionPerformedOnLinkDropdown, 2);

                    //Clicking on Stores In Link Dropdown

                    m_assert.assertTrue(selectOptionFromListBasedOnText(oPage_StoreSetUp.list_linkActionDropdown, "Requisition Stores"),
                            "Stores Clicked In Link Dropdown");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.header_unLinkExistingStore, 2);

                    //Validating Addition of New Store In Existing Story Type

                    String fullText[] = selectedFacilityName.trim().split("-");
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_StoreSetUp.dropdown_facilityFilter, fullText[0]),
                            "Facility Selected as : <b>" + fullText[0] + "</b> in Facility Dropdown");
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertTrue(fullText[0].contains(Cls_Generic_Methods.getSelectedValue(oPage_StoreSetUp.dropdown_facilityFilter)),
                            "Facility Dropdown In Stores Working Correctly,facility selected as : <b> " + fullText[0] + "</b>");

                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_StoreSetUp.dropdown_storeTypeFilter, storeType),
                            "Store Selected as : <b>" + storeType + "</b> in Store Type Dropdown");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.dropdown_facilityFilter, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore),
                            "Store input Box clicked in Unlink Existing Store");
                    for (WebElement stores : oPage_StoreSetUp.list_selectStoresListInLinkExistingStore) {
                        facilityAndStoreTypeStoresList.add(Cls_Generic_Methods.getTextInElement(stores));
                    }

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore),
                            "Input Select Store Box Clicked");
                    m_assert.assertInfo("Stores List for facility " + selectedFacilityName + " under " + storeType + " store Type: " + "<b>" + facilityAndStoreTypeStoresList + "</b> ");

                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_StoreSetUp.dropdown_storeTypeFilter, "All"),
                            "Store Selected as : <b>" + "All" + "</b> in Store Type Dropdown");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.dropdown_storeTypeFilter, 3);


                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore),
                            "Store input Box clicked in Unlink Existing Store");
                    Cls_Generic_Methods.customWait(1);

                    for (WebElement stores : oPage_StoreSetUp.list_currentlyUnlinkedStores) {
                        beforeUnLinkSelectStoresList.add(Cls_Generic_Methods.getTextInElement(stores));
                    }

                    m_assert.assertInfo("Currently Unlinked Stores List Before Unlinking : <b> " + beforeUnLinkSelectStoresList + "</b>");
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore, facilityAndStoreTypeStoresList.get(0));

                    m_assert.assertTrue(CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_StoreSetUp.list_selectStoresListInLinkExistingStore, facilityAndStoreTypeStoresList.get(0)),
                            "Store  selected for Unlinking <b> " + facilityAndStoreTypeStoresList.get(0) + " </b>");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_saveLinkStore, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.button_saveLinkStore),
                            "Save Button clicked in link Store");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.text_selectedFacilityName, 2);


                    if (!Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetUp.button_addStore)) {
                        Cls_Generic_Methods.clickElement(oPage_StoreSetUp.button_StoreInventorySetupPage);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.title_StoreSetup, 1);
                    }


                    // Verifying Addition of Linking Store to Currently Linked Store

                    clickUnlinkOperation = CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetUp.list_facilityNamesForInventory,
                            selectedFacilityName, oPage_StoreSetUp.list_storeColumnLinkedToFacility, oPage_StoreSetUp.list_buttonColumnLinkedToStores,
                            "Un-Link", storeNameInStoreSetup, "Disable");

                    if (clickUnlinkOperation) {

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.dropdown_actionPerformedOnLinkDropdown, 4);
                        m_assert.assertTrue(selectOptionFromListBasedOnText(oPage_StoreSetUp.list_linkActionDropdown, "Requisition Stores"),
                                "Stores Clicked In Un-Link Dropdown for facility name " + selectedFacilityName);

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.header_unLinkExistingStore, 5);
                        m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_StoreSetUp.dropdown_facilityFilter, fullText[0]),
                                "Facility Selected as :<b>" + fullText[0] + "</b> in Facility dropdown");
                        Cls_Generic_Methods.customWait(3);

                        m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_StoreSetUp.dropdown_storeTypeFilter, "All"),
                                "Facility Selected as :<b> All </b> in Facility dropdown");
                        Cls_Generic_Methods.customWait(1);
                        for (WebElement unlinkedStores : oPage_StoreSetUp.list_currentlyUnlinkedStores) {
                            if (Cls_Generic_Methods.getTextInElement(unlinkedStores).equalsIgnoreCase(facilityAndStoreTypeStoresList.get(0))) {
                                isUnlinkedStore = true;
                                break;
                            }
                        }


                        m_assert.assertTrue(isUnlinkedStore, "<b> " + facilityAndStoreTypeStoresList.get(0) + " </b> is Unlinked successfully");

                        for (WebElement stores : oPage_StoreSetUp.list_currentlyUnlinkedStores) {
                            afterUnLinkSelectStoresList.add(Cls_Generic_Methods.getTextInElement(stores));
                        }

                        m_assert.assertInfo("Currently Unlinked Stores List After Unlink: <br><b>" + afterUnLinkSelectStoresList + "</b>");
                        Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.title_StoreSetup, 3);
                    }
                    // Verifying Unliked Store in Store Order

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_storesSelector),
                            "Store Dropdown Clicked");
                    for (WebElement store : oPage_Navbar.list_storesSelector) {
                        if (Cls_Generic_Methods.getTextInElement(store).contains(storeNameInStoreSetup)) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(store),
                                    storeNameInStoreSetup + " clicked from Store List from the home page. ");
                            Cls_Generic_Methods.waitForPageLoad(driver, 5);
                        }
                    }

                    Cls_Generic_Methods.switchToOtherTab();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.text_opticalStoreTitle, 2);
                    m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetUp.text_opticalStoreTitle),
                            "Optical Store Opened");
                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                    Cls_Generic_Methods.customWait(1);
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addNewButtonInOrder, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.button_addNewButtonInOrder),
                            "New Button clicked in Order Requisition");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.dropdown_receivingStoreInRequisition, 3);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.dropdown_receivingStoreInRequisition),
                            "Receiving Store Dropdown clicked");
                    Cls_Generic_Methods.customWait(3);

                    for (WebElement receivingStore : oPage_StoreSetUp.list_storesListInReceivingStoresRequisition) {

                        if (Cls_Generic_Methods.getTextInElement(receivingStore).contains(facilityAndStoreTypeStoresList.get(0))) {
                            isUnlinkedStoreInReceivingStore = false;
                        }

                    }


                    m_assert.assertTrue(isUnlinkedStoreInReceivingStore,
                            "Stores UnLinked under requisition for store " + storeNameInStoreSetup + "<b> are: " + unlinkedStoreListInOrderReceivingStore + "</b>");

                    Cls_Generic_Methods.clickElement(oPage_StoreSetUp.button_closeNewTransactionWithoutSaving);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addNewButtonInOrder, 2);
                    Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

                } else {
                    m_assert.assertTrue(clickUnlinkOperation, "Unlink Button is not Clicked in Sub Category List");
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

    @Test(enabled = true, description = "validate Link And Un-link Category Functionality For Store")
    public void validateLinkAndUnlinkCategoryFunctionalityForStore() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_StoreSetUp oPage_StoreSetup = new Page_StoreSetUp(driver);
        boolean bCategoryFound = false;
        boolean bUserFound = false;
        String sFacilityName = "TESTING_FACILITY";
        String sStoreName = "Pharmacy automation";
        String sCategoryName = "Miscellaneous";
        String sUserName = "PR.Akash test";

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectFacility(sFacilityName);

            try {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");
                Cls_Generic_Methods.customWait(2);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }

            boolean clickLinkOperation = CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName, oPage_StoreSetup.list_storeColumnLinkedToFacility, oPage_StoreSetup.list_buttonColumnLinkedToStores, "Link", sStoreName, "Disable");

            if (clickLinkOperation) {
                m_assert.assertTrue(true, "link Button Clicked for " + sStoreName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_category, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_category), " clicked on category button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);

                for (WebElement eCategoryName : oPage_StoreSetup.list_linkedCategory) {
                    String sTableValue = Cls_Generic_Methods.getTextInElement(eCategoryName);
                    if (sTableValue.equalsIgnoreCase(sCategoryName)) {
                        bCategoryFound = true;
                        break;
                    }
                }

                if (bCategoryFound) {

                    m_assert.assertTrue(true, "<b>" + sCategoryName + "</b>  " + "category" + " " + "already linked to store = <b>" + sStoreName + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_close);

                    CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName, oPage_StoreSetup.list_storeColumnLinkedToFacility,
                            oPage_StoreSetup.list_buttonColumnLinkedToStores, "Un-Link", sStoreName, "Disable");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_category, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_category), " clicked on category button");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.select_optionFromDropdownField, 10);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.select_optionFromDropdownField);
                    m_assert.assertTrue(CommonActions.selectOptionFromDropdown(oPage_StoreSetup.list_categoriesInDropdown, sCategoryName), "unlinked" + "<b>  " + sCategoryName + "</b>  " + "category From the " + "<b> " + sStoreName + " </b> " + "store");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_save, 10);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_save);
                    Cls_Generic_Methods.customWait();

                    CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName, oPage_StoreSetup.list_storeColumnLinkedToFacility,
                            oPage_StoreSetup.list_buttonColumnLinkedToStores, "Un-Link", sStoreName, "Disable");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_users, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_category), " clicked on category button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);

                    for (WebElement eUnlinkedCategoryName : oPage_StoreSetup.list_linkedAndUnlinkedUsersAndCategory) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eUnlinkedCategoryName);
                        if (sTableValue.equalsIgnoreCase(sCategoryName)) {
                            bCategoryFound = true;
                            break;
                        }
                    }

                    if (bCategoryFound) {

                        m_assert.assertTrue(true, "<b>" + sCategoryName + "</b>  " + "category" + " " + " Un-linked from the store = " + "<b> " + sStoreName + "</b>  " + "successfully");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);
                        Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_close);
                        Cls_Generic_Methods.customWait(2);
                        CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName, oPage_StoreSetup.list_storeColumnLinkedToFacility,
                                oPage_StoreSetup.list_buttonColumnLinkedToStores, "Link", sStoreName, "Disable");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_users, 10);
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_users), " clicked on user button");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);
                        for (WebElement eUserName : oPage_StoreSetup.list_linkedAndUnlinkedUsersAndCategory) {
                            String sTableValue = Cls_Generic_Methods.getTextInElement(eUserName);
                            if (sTableValue.equalsIgnoreCase(sUserName)) {
                                bUserFound = true;
                                break;
                            }
                        }

                        if (bUserFound) {
                            Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_close);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_store, 10);
                            Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_store);
                        } else {
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.select_optionFromDropdownField, 10);
                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.select_optionFromDropdownField), " clicked on select user field");
                            m_assert.assertTrue(CommonActions.selectOptionFromDropdown(oPage_StoreSetup.list_usersInDropdown, sUserName),
                                    "linked" + " " + "<b>" + sUserName + " " + "user to the " + " " + sStoreName + "</b>" + " " + "store");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_save, 10);
                            Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_save);
                            driver.navigate().refresh();
                            Cls_Generic_Methods.waitForPageLoad(driver, 40);
                            Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_store);
                        }

                        Cls_Generic_Methods.customWait(2);
                        for (WebElement eStoreName : oPage_Navbar.list_storesSelector) {
                            String sTableValue = Cls_Generic_Methods.getTextInElement(eStoreName);
                            sTableValue = sTableValue.split("-")[0].trim();
                            if (sTableValue.equalsIgnoreCase(sStoreName)) {
                                Cls_Generic_Methods.clickElement(driver, eStoreName);
                                break;
                            }
                        }

                        m_assert.assertTrue(Cls_Generic_Methods.switchToOtherTab(), "Switched to :<b>" + sStoreName + "</b> tab");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.text_pharmacyStoreTitle, 2);
                        Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetup.text_pharmacyStoreTitle);
                        Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_close);
                        Cls_Generic_Methods.customWait(1);
                        CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_addNewMaterItem, 10);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetup.button_addNewMaterItem), "Clicked on New Button");
                        Cls_Generic_Methods.customWait(2);
                        if (Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetup.text_warningToaster)) {
                            m_assert.assertTrue("<b>" + sCategoryName + "</b>  " + "Category" + "unlinked successfully");
                        } else {
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.select_categoryFieldUnderItemMaster, 10);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetup.select_categoryFieldUnderItemMaster), "Clicked on category field");
                            boolean bCategorylinkedToStore = false;
                            for (WebElement elinkedCategoryName : oPage_StoreSetup.list_categoriesInDropdownUnderAddNewItemToInventory) {
                                String sTableValue = Cls_Generic_Methods.getTextInElement(elinkedCategoryName);
                                if (sTableValue.equals(sCategoryName)) {
                                    bCategorylinkedToStore = true;
                                    break;
                                }
                            }

                            if (bCategorylinkedToStore) {
                                m_assert.assertTrue(true, " " + " <b>" + sCategoryName + "</b>  " + "category" + " " + " still linked to store = <b>" + sStoreName + "</b>");
                            } else {
                                m_assert.assertTrue("Validated" + " <b> " + sCategoryName + "</b> " + "category" + " " + " successfully Un-linked from store = <b>" + sStoreName + "</b>");
                            }
                        }
                        m_assert.assertTrue(Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab(), "closed current tab and switched to other tab");
                    } else {
                        m_assert.assertTrue(false, "" + "<b> " + sCategoryName + "</b>  " + "category" + " " + " still linked to store =<b> " + sStoreName + "</b>");
                    }

                    Cls_Generic_Methods.customWait(2);
                    CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");
                    Cls_Generic_Methods.customWait(2);
                    CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName, oPage_StoreSetup.list_storeColumnLinkedToFacility,
                            oPage_StoreSetup.list_buttonColumnLinkedToStores, "Link", sStoreName, "Disable");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_category, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_category), " clicked on category button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.select_optionFromDropdownField, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.select_optionFromDropdownField), " clicked on select category field");
                    m_assert.assertTrue(CommonActions.selectOptionFromDropdown(oPage_StoreSetup.list_categoriesInDropdown, sCategoryName), "linked" + " <b>" + sCategoryName + "</b> " + "category to the " + " <b>" + sStoreName + "</b> " + "store");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_save, 10);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_save);
                    Cls_Generic_Methods.customWait(2);
                    CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName, oPage_StoreSetup.list_storeColumnLinkedToFacility,
                            oPage_StoreSetup.list_buttonColumnLinkedToStores, "Link", sStoreName, "Disable");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_category, 10);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_category);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.select_optionFromDropdownField, 10);
                    for (WebElement eCategoryName : oPage_StoreSetup.list_linkedAndUnlinkedUsersAndCategory) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eCategoryName);
                        if (sTableValue.equalsIgnoreCase(sCategoryName)) {
                            bCategoryFound = true;
                            break;
                        }
                    }
                    if (bCategoryFound) {
                        m_assert.assertTrue(true, "<b>" + sCategoryName + "</b> " + "category" + " " + " linked to the store = <b>" + sStoreName + "</b> " + "successfully");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);
                        Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_close);
                        Cls_Generic_Methods.customWait(2);
                        CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName, oPage_StoreSetup.list_storeColumnLinkedToFacility,
                                oPage_StoreSetup.list_buttonColumnLinkedToStores, "Link", sStoreName, "Disable");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_users, 10);
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_users), " clicked on user button");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);
                        for (WebElement eUserName : oPage_StoreSetup.list_linkedAndUnlinkedUsersAndCategory) {
                            String sTableValue = Cls_Generic_Methods.getTextInElement(eUserName);
                            if (sTableValue.equalsIgnoreCase(sUserName)) {
                                bUserFound = true;
                                break;
                            }
                        }
                        if (bUserFound) {
                            Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_close);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_store, 10);
                            Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_store);
                        } else {
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.select_optionFromDropdownField, 10);
                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.select_optionFromDropdownField), " clicked on select user field");
                            m_assert.assertTrue(CommonActions.selectOptionFromDropdown(oPage_StoreSetup.list_usersInDropdown, sUserName), "linked" + " " + "<b>" + sUserName + "</b>  " + "user to the " + " <b>" + sStoreName + "</b>" + " " + "store");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_save, 10);
                            Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_save);
                            driver.navigate().refresh();
                            Cls_Generic_Methods.waitForPageLoad(driver, 40);
                            Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_store);
                        }
                        Cls_Generic_Methods.customWait(2);
                        for (WebElement eStoreName : oPage_Navbar.list_storesSelector) {
                            String sTableValue = Cls_Generic_Methods.getTextInElement(eStoreName);
                            sTableValue = sTableValue.split("-")[0].trim();
                            if (sTableValue.equalsIgnoreCase(sStoreName)) {
                                Cls_Generic_Methods.clickElement(driver, eStoreName);
                                break;
                            }
                        }
                        m_assert.assertTrue(Cls_Generic_Methods.switchToOtherTab(), "Switched to: <b>" + sStoreName + "</b> tab");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.text_pharmacyStoreTitle, 2);
                        Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetup.text_pharmacyStoreTitle);
                        Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_close);
                        Cls_Generic_Methods.customWait(1);
                        CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_addNewMaterItem, 10);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetup.button_addNewMaterItem), "Clicked on New Button");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.select_categoryFieldUnderItemMaster, 10);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetup.select_categoryFieldUnderItemMaster), "Clicked on category field");
                        for (WebElement eLinkedCategoryName : oPage_StoreSetup.list_categoriesInDropdownUnderAddNewItemToInventory) {
                            String sTableValue = Cls_Generic_Methods.getTextInElement(eLinkedCategoryName);
                            if (sTableValue.equalsIgnoreCase(sCategoryName)) {
                                bCategoryFound = true;
                                break;
                            }
                        }
                        m_assert.assertTrue(true, "Validated" + " <b>" + sCategoryName + "</b> " + "category" + " " + " linked to store = <b>" + sStoreName + "</b>");
                    } else {
                        m_assert.assertTrue(false, "" + " <b>" + sCategoryName + "</b> " + "category" + " " + " not linked to store = <b>" + sStoreName + "</b>");
                    }
                } else {

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.select_optionFromDropdownField, 10);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.select_optionFromDropdownField);
                    m_assert.assertTrue(CommonActions.selectOptionFromDropdown(oPage_StoreSetup.list_categoriesInDropdown, sCategoryName), "linked" + " <b>" + sCategoryName + "</b> category to the " + " <b>" + sStoreName + "</b> " + "store");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_save, 10);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_save);
                    Cls_Generic_Methods.customWait(2);
                    CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName, oPage_StoreSetup.list_storeColumnLinkedToFacility,
                            oPage_StoreSetup.list_buttonColumnLinkedToStores, "Link", sStoreName, "Disable");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_category, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_category), " clicked on category button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);


                    for (WebElement eCategoryName : oPage_StoreSetup.list_linkedCategory) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eCategoryName);
                        if (sTableValue.equalsIgnoreCase(sCategoryName)) {
                            bCategoryFound = true;
                            break;
                        }
                    }


                    if (bCategoryFound) {
                        m_assert.assertTrue(true, "<b>" + sCategoryName + "</b> " + "category" + " " + " linked to the store = <b>" + sStoreName + "</b>  " + "successfully");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);
                        Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_close);
                        Cls_Generic_Methods.customWait(2);
                        CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName, oPage_StoreSetup.list_storeColumnLinkedToFacility,
                                oPage_StoreSetup.list_buttonColumnLinkedToStores, "Link", sStoreName, "Disable");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_users, 10);
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_users), " clicked on user button");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);
                        for (WebElement eUserName : oPage_StoreSetup.list_linkedAndUnlinkedUsersAndCategory) {
                            String sTableValue = Cls_Generic_Methods.getTextInElement(eUserName);
                            if (sTableValue.equalsIgnoreCase(sUserName)) {
                                bUserFound = true;
                                break;
                            }
                        }
                        if (bUserFound) {
                            Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_close);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_store, 10);
                            Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_store);
                        } else {
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.select_optionFromDropdownField, 10);
                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.select_optionFromDropdownField), " clicked on select user field");
                            m_assert.assertTrue(CommonActions.selectOptionFromDropdown(oPage_StoreSetup.list_usersInDropdown, sUserName), "linked" + " " + "<b>" + sUserName + "</b> " + "user to the " + " <b>" + sStoreName + "</b>" + " " + "store");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_save, 10);
                            Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_save);
                            driver.navigate().refresh();
                            Cls_Generic_Methods.waitForPageLoad(driver, 40);
                            Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_store);
                        }
                        Cls_Generic_Methods.customWait(2);
                        for (WebElement eStoreName : oPage_Navbar.list_storesSelector) {
                            String sTableValue = Cls_Generic_Methods.getTextInElement(eStoreName);
                            sTableValue = sTableValue.split("-")[0].trim();
                            if (sTableValue.equalsIgnoreCase(sStoreName)) {
                                Cls_Generic_Methods.clickElement(driver, eStoreName);
                                break;
                            }
                        }
                        m_assert.assertTrue(Cls_Generic_Methods.switchToOtherTab(), "Switched to: <b>" + sStoreName + "</b> tab");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.text_pharmacyStoreTitle, 2);
                        Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetup.text_pharmacyStoreTitle);
                        Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_close);
                        Cls_Generic_Methods.customWait(1);
                        CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_addNewMaterItem, 10);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetup.button_addNewMaterItem), "Clicked on New Button");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.select_categoryFieldUnderItemMaster, 10);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetup.select_categoryFieldUnderItemMaster), "Clicked on category field");
                        for (WebElement eLinkedCategoryName : oPage_StoreSetup.list_categoriesInDropdownUnderAddNewItemToInventory) {
                            String sTableValue = Cls_Generic_Methods.getTextInElement(eLinkedCategoryName);
                            if (sTableValue.equalsIgnoreCase(sCategoryName)) {
                                bCategoryFound = true;
                                break;
                            }
                        }
                        Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                        m_assert.assertTrue(true, "Validated" + " <b>" + sCategoryName + "</b> " + "category" + " " + " linked to store = <b>" + sStoreName + "</b>");
                    } else {
                        m_assert.assertTrue(false, "" + " <b>" + sCategoryName + "</b> " + "category" + " " + " not linked to store = <b>" + sStoreName + "</b>");
                    }


                    Cls_Generic_Methods.customWait(2);
                    CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");
                    Cls_Generic_Methods.customWait(2);
                    CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName, oPage_StoreSetup.list_storeColumnLinkedToFacility,
                            oPage_StoreSetup.list_buttonColumnLinkedToStores, "Un-Link", sStoreName, "Disable");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_category, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_category), " clicked on category button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.select_optionFromDropdownField, 10);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.select_optionFromDropdownField);
                    m_assert.assertTrue(CommonActions.selectOptionFromDropdown(oPage_StoreSetup.list_categoriesInDropdown, sCategoryName), "unlinked" + " <b>" + sCategoryName + "</b> " + "category From the " + " <b>" + sStoreName + "</b> " + "store");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_save, 10);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_save);
                    Cls_Generic_Methods.customWait();
                    CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName, oPage_StoreSetup.list_storeColumnLinkedToFacility,
                            oPage_StoreSetup.list_buttonColumnLinkedToStores, "Un-Link", sStoreName, "Disable");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_users, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_users), " clicked on user button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);
                    for (WebElement eUnlinkedCategoryName : oPage_StoreSetup.list_linkedAndUnlinkedUsersAndCategory) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eUnlinkedCategoryName);
                        if (sTableValue.equalsIgnoreCase(sCategoryName)) {
                            bCategoryFound = true;
                            break;
                        }
                    }

                    if (bCategoryFound) {
                        m_assert.assertTrue(true, "<b>" + sCategoryName + "</b> " + "category" + " " + " Un-linked from the store = <b>" + sStoreName + "</b> " + "successfully");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);
                        Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_close);
                        Cls_Generic_Methods.customWait(2);
                        CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName, oPage_StoreSetup.list_storeColumnLinkedToFacility,
                                oPage_StoreSetup.list_buttonColumnLinkedToStores, "Link", sStoreName, "Disable");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_users, 10);
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_users), " clicked on user button");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);
                        for (WebElement eUserName : oPage_StoreSetup.list_linkedAndUnlinkedUsersAndCategory) {
                            String sTableValue = Cls_Generic_Methods.getTextInElement(eUserName);
                            if (sTableValue.equalsIgnoreCase(sUserName)) {
                                bUserFound = true;
                                break;
                            }
                        }
                        if (bUserFound) {
                            Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_close);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_store, 10);
                            Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_store);
                        } else {
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.select_optionFromDropdownField, 10);
                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.select_optionFromDropdownField), " clicked on select user field");
                            m_assert.assertTrue(CommonActions.selectOptionFromDropdown(oPage_StoreSetup.list_usersInDropdown, sUserName), "linked" + " " + "<b>" + sUserName + "</b> " + "user to the " + " <b>" + sStoreName + "</b>" + " " + "store");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_save, 10);
                            Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_save);
                            driver.navigate().refresh();
                            Cls_Generic_Methods.waitForPageLoad(driver, 40);
                            Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_store);
                        }
                        Cls_Generic_Methods.customWait(2);
                        for (WebElement eStoreName : oPage_Navbar.list_storesSelector) {
                            String sTableValue = Cls_Generic_Methods.getTextInElement(eStoreName);
                            sTableValue = sTableValue.split("-")[0].trim();
                            if (sTableValue.equalsIgnoreCase(sStoreName)) {
                                Cls_Generic_Methods.clickElement(driver, eStoreName);
                                break;
                            }
                        }
                        m_assert.assertTrue(Cls_Generic_Methods.switchToOtherTab(), "Switched to: " + sStoreName + "</b> tab");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.text_pharmacyStoreTitle, 2);
                        Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetup.text_pharmacyStoreTitle);
                        Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_close);
                        Cls_Generic_Methods.customWait(1);
                        CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_addNewMaterItem, 10);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetup.button_addNewMaterItem), "Clicked on New Button");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.text_warningToaster, 10);
                        if (Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetup.text_warningToaster)) {
                            m_assert.assertTrue("There is no category linked to this store.");
                        } else {
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.select_categoryFieldUnderItemMaster, 10);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetup.select_categoryFieldUnderItemMaster), "Clicked on category field");
                            boolean bCategoryLinkedToStore = false;
                            for (WebElement eLinkedCategoryName : oPage_StoreSetup.list_categoriesInDropdownUnderAddNewItemToInventory) {
                                String sTableValue = Cls_Generic_Methods.getTextInElement(eLinkedCategoryName);
                                if (sTableValue.equals(sCategoryName)) {
                                    bCategoryLinkedToStore = true;
                                    break;
                                }
                            }

                            if (bCategoryLinkedToStore) {
                                m_assert.assertTrue(true, "" + " <b>" + sCategoryName + "</b> " + "category" + " " + " still linked to store = <b>" + sStoreName + "</b>");
                            } else {
                                m_assert.assertTrue("Validated" + " <b>" + sCategoryName + "</b> " + "category" + " " + " successfully Un-linked from store = <b>" + sStoreName + "</b>");
                            }
                        }
                        Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                    } else {
                        m_assert.assertTrue(false, "" + " <b>" + sCategoryName + "</b> " + "category" + " " + " still linked to store = <b>" + sStoreName + "</b>");
                    }

                }


            } else {
                m_assert.assertTrue(false, "Not able to clink on link Button for <b>" + sStoreName + "</b> " + "store");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }


    }

    @Test(enabled = true, description = "validate Link And Un-link Users Functionality For Store")
    public void validateLinkAndUnlinkUsersFunctionalityForStore() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_StoreSetUp oPage_StoreSetup = new Page_StoreSetUp(driver);
        boolean bUserFound = false;
        String sFacilityName = "TESTING_FACILITY";
        String sStoreName = "Pharmacy automation";
        String sUserName = "PR.Akash test";
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectFacility(sFacilityName);

            try {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");
                Cls_Generic_Methods.customWait(2);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
            boolean clickLinkOperation = CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName,
                    oPage_StoreSetup.list_storeColumnLinkedToFacility, oPage_StoreSetup.list_buttonColumnLinkedToStores, "Link", sStoreName, "Disable");

            if (clickLinkOperation) {
                m_assert.assertTrue(true, "link Button Clicked for " + sStoreName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_users, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_users), " clicked on user button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);
                for (WebElement eUserName : oPage_StoreSetup.list_linkedAndUnlinkedUsersAndCategory) {
                    String sTableValue = Cls_Generic_Methods.getTextInElement(eUserName);
                    if (sTableValue.equalsIgnoreCase(sUserName)) {
                        bUserFound = true;
                        break;
                    }
                }

                if (bUserFound) {
                    m_assert.assertTrue(true, "<b>" + sUserName + "</b> " + "user" + " " + "already linked to store = <b>" + sStoreName + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_close);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_store, 10);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_store);
                    Cls_Generic_Methods.customWait();

                    boolean bStoreLinkedToUser = false;
                    for (WebElement eStoreName : oPage_StoreSetup.list_storeLinkedToUser) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eStoreName);
                        sTableValue = sTableValue.split("-")[0].trim();
                        if (sTableValue.equalsIgnoreCase(sStoreName)) {
                            bStoreLinkedToUser = true;
                            break;
                        }
                    }

                    m_assert.assertTrue(bStoreLinkedToUser, "<b>" + sStoreName + "</b>  " + "already linked to = " + "<b>" + sUserName + "</b>");
                    Cls_Generic_Methods.customWait();
                    CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName,
                            oPage_StoreSetup.list_storeColumnLinkedToFacility, oPage_StoreSetup.list_buttonColumnLinkedToStores, "Un-Link", sStoreName, "Disable");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_users, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_users), " clicked on user button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.select_optionFromDropdownField, 10);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.select_optionFromDropdownField);
                    m_assert.assertTrue(
                            CommonActions.selectOptionFromDropdown(oPage_StoreSetup.list_usersInDropdown, sUserName),
                            "unlinked" + " " + "<b>" + sUserName + "</b>  " + "user From the " + " <b>" + sStoreName + "</b>" + " " + "store");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_save, 10);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_save);
                    Cls_Generic_Methods.customWait(2);

                    CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName,
                            oPage_StoreSetup.list_storeColumnLinkedToFacility, oPage_StoreSetup.list_buttonColumnLinkedToStores, "Un-Link", sStoreName, "Disable");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_users, 10);

                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_users), " clicked on user button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);

                    for (WebElement eUnlinkedUserName : oPage_StoreSetup.list_linkedAndUnlinkedUsersAndCategory) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eUnlinkedUserName);
                        if (sTableValue.equalsIgnoreCase(sUserName)) {
                            bUserFound = true;
                            break;
                        }
                    }

                    m_assert.assertTrue(bUserFound, "<b>" + sStoreName + "</b> " + " unlinked from = " + " " + " <b>" + sUserName + "</b>");
                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.waitForPageLoad(driver, 40);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_store);
                    Cls_Generic_Methods.customWait(2);

                    boolean bStoreFound = false;
                    for (WebElement eStoreName : oPage_Navbar.list_storesSelector) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eStoreName);
                        sTableValue = sTableValue.split("-")[0].trim();
                        if (sTableValue.equalsIgnoreCase(sStoreName)) {
                            bStoreFound = true;
                            break;

                        }
                    }

                    if (bStoreFound) {
                        m_assert.assertFatal("<b>" + sStoreName + "</b> " + "still linked to user<b>" + sUserName + "</b>");
                    } else {
                        m_assert.assertTrue("<b>" + sStoreName + "</b> " + "Un-linked from user<b>" + sUserName + "</b> " + "successfully");
                    }

                    Cls_Generic_Methods.customWait(2);
                    CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");
                    Cls_Generic_Methods.customWait(2);
                    CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName,
                            oPage_StoreSetup.list_storeColumnLinkedToFacility, oPage_StoreSetup.list_buttonColumnLinkedToStores, "Link", sStoreName, "Disable");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_users, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_users), " clicked on users button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.select_optionFromDropdownField, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.select_optionFromDropdownField), " clicked on select user field");
                    m_assert.assertTrue(
                            CommonActions.selectOptionFromDropdown(oPage_StoreSetup.list_usersInDropdown, sUserName),
                            "linked" + " " + "<b>" + sUserName + "</b> " + "user to the " + " <b>" + sStoreName + "</b>" + " " + "store");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_save, 10);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_save);
                    Cls_Generic_Methods.customWait(3);
                    CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName,
                            oPage_StoreSetup.list_storeColumnLinkedToFacility, oPage_StoreSetup.list_buttonColumnLinkedToStores, "Link", sStoreName, "Disable");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_users, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_users), " clicked on users button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);
                    for (WebElement eLinkedUserName : oPage_StoreSetup.list_linkedAndUnlinkedUsersAndCategory) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eLinkedUserName);
                        if (sTableValue.equalsIgnoreCase(sUserName)) {
                            bUserFound = true;
                            break;
                        }
                    }
                    m_assert.assertTrue(bUserFound, "<b>" + sStoreName + "</b> " + " linked to = " + " <b>" + sUserName + "</b>");
                    driver.navigate().refresh();
                    Cls_Generic_Methods.waitForPageLoad(driver, 40);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_store);
                    Cls_Generic_Methods.customWait(2);
                    for (WebElement elinkedUserName : oPage_Navbar.list_storesSelector) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(elinkedUserName);
                        if (sTableValue.equalsIgnoreCase(sUserName)) {
                            bUserFound = true;
                            break;
                        }
                    }
                    m_assert.assertTrue(bUserFound, "<b>" + sStoreName + "</b> " + " linked to = " + " <b>" + sUserName + "</b>" + " " + "successfully");
                } else {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.select_optionFromDropdownField, 10);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.select_optionFromDropdownField);
                    m_assert.assertTrue(
                            CommonActions.selectOptionFromDropdown(oPage_StoreSetup.list_usersInDropdown, sUserName),
                            "linked" + " " + "<b>" + sUserName + "</b>user to the " + " <b>" + sStoreName + "</b>" + " " + "store");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_save, 10);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_save);
                    Cls_Generic_Methods.customWait(2);
                    CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName,
                            oPage_StoreSetup.list_storeColumnLinkedToFacility, oPage_StoreSetup.list_buttonColumnLinkedToStores, "Link", sStoreName, "Disable");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_users, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_users), " clicked on user button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);
                    for (WebElement eUserName : oPage_StoreSetup.list_linkedAndUnlinkedUsersAndCategory) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eUserName);
                        if (sTableValue.equalsIgnoreCase(sUserName)) {
                            bUserFound = true;
                            break;
                        }
                    }


                    if (bUserFound) {
                        m_assert.assertTrue(true, "<b>" + sUserName + "</b> " + "user has been linked to = <b>" + sStoreName + "</b>");
                        driver.navigate().refresh();
                        Cls_Generic_Methods.waitForPageLoad(driver, 40);
                        Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_store);
                        Cls_Generic_Methods.customWait(2);
                        for (WebElement elinkedUserName : oPage_Navbar.list_storesSelector) {
                            String sTableValue = Cls_Generic_Methods.getTextInElement(elinkedUserName);
                            if (sTableValue.equalsIgnoreCase(sUserName)) {
                                bUserFound = true;
                                break;
                            }
                        }
                        m_assert.assertTrue(bUserFound, "<b>" + sStoreName + "</b> " + " linked to = " + " <b>" + sUserName + "</b>" + " successfully");
                        Cls_Generic_Methods.customWait(2);
                        CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");
                        Cls_Generic_Methods.customWait(2);
                        CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName,
                                oPage_StoreSetup.list_storeColumnLinkedToFacility, oPage_StoreSetup.list_buttonColumnLinkedToStores, "Un-Link", sStoreName, "Disable");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_users, 10);
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_users), " clicked on user button");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.select_optionFromDropdownField, 10);
                        Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.select_optionFromDropdownField);
                        m_assert.assertTrue(
                                CommonActions.selectOptionFromDropdown(oPage_StoreSetup.list_usersInDropdown, sUserName),
                                "unlinked" + " " + "<b>" + sUserName + "</b> " + "user From the " + " <b>" + sStoreName + "</b>" + " " + "store");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_save, 10);
                        Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_save);
                    } else {
                        m_assert.assertTrue(false, "<b>" + sUserName + "</b> not linked to = <b>" + sStoreName + "</b>");
                    }

                    Cls_Generic_Methods.customWait(2);
                    CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetup.list_facilityNamesForInventory, sFacilityName,
                            oPage_StoreSetup.list_storeColumnLinkedToFacility, oPage_StoreSetup.list_buttonColumnLinkedToStores, "Un-Link", sStoreName, "Disable");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_users, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_users), " clicked on user button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetup.button_close, 10);
                    for (WebElement eUnlinkedUserName : oPage_StoreSetup.list_linkedAndUnlinkedUsersAndCategory) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eUnlinkedUserName);
                        if (sTableValue.equalsIgnoreCase(sUserName)) {
                            bUserFound = true;
                            break;
                        }
                    }
                    m_assert.assertTrue(bUserFound, "<b>" + sStoreName + "</b> " + " unlinked from = " + " <b>" + sUserName + "</b>");
                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.waitForPageLoad(driver, 40);
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetup.button_store);
                    Cls_Generic_Methods.customWait(2);

                    boolean bStoreFound = false;
                    for (WebElement eStoreName : oPage_Navbar.list_storesSelector) {
                        String sTableValue = Cls_Generic_Methods.getTextInElement(eStoreName);
                        sTableValue = sTableValue.split("-")[0].trim();
                        if (sTableValue.equalsIgnoreCase(sStoreName)) {
                            bStoreFound = true;
                            break;

                        }
                    }
                    if (bStoreFound) {
                        m_assert.assertFatal("<b>" + sStoreName + "</b> " + "still linked to user<b>" + sUserName + "</b>");
                    } else {
                        m_assert.assertTrue("<b>" + sStoreName + "</b> " + "Un-linked from user<b>" + sUserName + "</b> " + "successfully");
                    }

                }

            } else {
                m_assert.assertTrue(false, "Not able to clink on link Button for " + " " + "<b>" + sStoreName + "</b>" + " " + "store");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }


    }

    @Test(enabled = true, description = "Validating Disable Functionality In Store Setup")
    public void validateDisablingStoreFunctionalityForStore() {
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        List<String> beforeDisableStoreList = new ArrayList<String>();
        List<String> afterDisableStoreList = new ArrayList<String>();
        List<String> afterActivateStoreList = new ArrayList<String>();
        String selectedFacilityName = null;
        String storeNameInStoreSetup = "OpticalStore";
        boolean isDisableStore = false;
        boolean isActivateStore = false;
        String alertConfirmationMessage = "Are you sure, you want disable OpticalStore?";


        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                // Storing Selected Facility Full Name

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_facilitySelector),
                        "Facility Selector Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.text_selectedFacilityName, 2);
                selectedFacilityName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_selectedFacilityName);

                // Validating Store before edit in home page

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_storesSelector),
                        "Store button clicked in Home Page");
                for (WebElement store : oPage_Navbar.list_storesSelector) {
                    beforeDisableStoreList.add(Cls_Generic_Methods.getTextInElement(store));
                }

                m_assert.assertInfo("Stores List In Home Page Before Disabling: <b>  " + beforeDisableStoreList + "</b>");

                // Selection Store Setup Inventory And Supply Chain
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");

                // Validating selected Facility in Store Setup and clicking on link button

                boolean clickDisableOperation = CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetUp.list_facilityNamesForInventory, selectedFacilityName,
                        oPage_StoreSetUp.list_storeColumnLinkedToFacility, oPage_StoreSetUp.list_buttonColumnLinkedToStores, "Disable", storeNameInStoreSetup, "Disable");

                if (clickDisableOperation) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.alert_disableConfirmAlert, 3);

                    //Validating Disable Store Functionality

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetUp.text_disableConfirmAlertTitle),
                            "Disable Confirm Title is Present");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_StoreSetUp.text_disableConfirmationAlertMessage).contains(alertConfirmationMessage),
                            "Confirmation Message Is present");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.button_disableAlertConfirmButton),
                            "Button confirmed for disabling the store: " + storeNameInStoreSetup);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_storesSelector, 4);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_storesSelector),
                            "Store button clicked in Home Page");
                    for (WebElement store : oPage_Navbar.list_storesSelector) {
                        afterDisableStoreList.add(Cls_Generic_Methods.getTextInElement(store));
                    }
                    if (!beforeDisableStoreList.equals(afterDisableStoreList)) {
                        isDisableStore = true;
                    }
                    m_assert.assertTrue(isDisableStore, "Stores list after disabling store : " + storeNameInStoreSetup + "<b>" + afterDisableStoreList + "</b>");
                }
                // Activating previously Disable Store Again

                boolean clickActiveOperation = CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetUp.list_facilityNamesForInventory, selectedFacilityName,
                        oPage_StoreSetUp.list_storeColumnLinkedToFacility, oPage_StoreSetUp.list_buttonColumnLinkedToStores, "Active", storeNameInStoreSetup, "Active");

                if (clickActiveOperation) {
                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_storesSelector, 4);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_storesSelector),
                            "Stores clicked after activate store");
                    Cls_Generic_Methods.customWait(1);
                    for (WebElement store : oPage_Navbar.list_storesSelector) {
                        afterActivateStoreList.add(Cls_Generic_Methods.getTextInElement(store));
                    }
                    if (beforeDisableStoreList.equals(afterActivateStoreList)) {
                        isActivateStore = true;
                    }

                    m_assert.assertTrue(isActivateStore, "Store List In Home Page After Active Store: " + storeNameInStoreSetup + "<b>" + afterActivateStoreList + "</b>");
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

    public static boolean selectOptionFromListBasedOnText(List<WebElement> listOfElements, String sOptionValue) {

        boolean valueSelected = false;

        for (WebElement e : listOfElements) {
            if (e.getText().trim().equalsIgnoreCase(sOptionValue)
                   // || e.getAttribute("value").equalsIgnoreCase(sOptionValue)
            ) {
                System.out.println(e.getText() + " is selected.");
                e.click();
                valueSelected = true;
                break;
            }
        }

        return valueSelected;

    }

}