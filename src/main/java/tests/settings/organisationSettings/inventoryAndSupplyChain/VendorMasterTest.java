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
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_CategoryMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_VendorMaster;
import pages.store.Page_Store;


import java.util.List;

import static pages.commonElements.CommonActions.oEHR_Data;
import static tests.settings.organisationSettings.inventoryAndSupplyChain.CategoryMasterTest.getRandomString;
import static tests.settings.organisationSettings.inventoryAndSupplyChain.CategoryMasterTest.selectCategoryFromDropdown;


public class VendorMasterTest extends TestBase {


    String newVendorName = "Test_" + getRandomString(3);
    String updateVendorName = newVendorName + "_Update";
    String newVendorGroup = "Deepak_Automation_Group_1";
    String updateVendorGroup = "Deepak_Automation_Group_2";

    String newVendorCompanyName = "Vendor Group";
    String newVendorContactPerson = "Deepak";
    String newVendorMobileNumber = "7860000000";
    String updateVendorMobileNumber = "9999999999";
    String newVendorEmail = "dummy@gmail.com";
    String newVendorSecondaryMobileNumber = "8888888888";
    String newVendorPanNumber = "CTCD8888B";
    String newVendorCinNumber = "HG12345P";
    String newVendorMsmeNumber = "MSME1234";
    String newVendorCreditDays = "2";
    String newVendorCreditLimit = "200000";
    String newVendorDLNumber = "DL54321";
    String newVendorGstNumber = "GST";
    String newVendorCountry = "India (IN)";
    String newVendorAddress = "Address1";
    String selectedCategoryName = "Medication";
    String selectedStoreSetupName = "OpticalStore- Optical";

    @Test(enabled = true, description = "Validating Add Vendor Functionality in Vendor Master")
    public void validateAddVendorFunctionality() {

        Page_VendorMaster oPage_VendorMaster = new Page_VendorMaster(driver);
        Page_CategoryMaster oPage_CategoryMaster = new Page_CategoryMaster(driver);
        boolean bVendorFound = false;

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            try {

                // Select Vendor Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Master");

                // Opening Add Item Template ,Verify Add Item Template

                Cls_Generic_Methods.waitForPageLoad(driver, 4);

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_VendorMaster.header_vendorMasterTitle),
                        " Verifying Vendor Master Header ");
                Cls_Generic_Methods.scrollToTop();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_addVendorButton),
                        " Add Vendor Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.header_addVendorMasterTemplateTitle, 5);

                // Verifying Required Error In Add Vendor Template

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CategoryMaster.button_saveChanges),
                        " Save Changes button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.label_inventoryVendorNameRequiredError, 2);
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_VendorMaster.input_inventoryVendorName, "class").contains("error"),
                        "<b> Required Error Displayed for Name </b> ");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_VendorMaster.input_inventoryVendorMobileNumber, "class").contains("error"),
                        "<b> Required Error Displayed for Mobile Number </b> ");
                // m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_VendorMaster.label_inventoryVendorNameRequiredError),
                //     " Vendor Name Required Error is Displayed");
                // m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_VendorMaster.label_inventoryVendorMobileNumberRequiredError),
                //   " Vendor Mobile Number Required Error is Displayed");

                //Entering All Fields Values and Creating New Vendor

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorName, newVendorName),
                        "Vendor Name Entered as : <b>" + newVendorName + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_inventoryVendorGroupArrow),
                        "Vendor Group Dropdown Arrow Clicked");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.input_inventoryDropdownCommonInputBox, 1);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryDropdownCommonInputBox, newVendorGroup);
                Cls_Generic_Methods.customWait(1);
                m_assert.assertTrue(CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_VendorMaster.list_inventoryVendorDropdownResultOptions,
                        newVendorGroup), "Vendor Group Entered as : <b>" + newVendorGroup + "</b>");


                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorCompanyName, newVendorCompanyName),
                        "Vendor Company Name Entered as : <b>" + newVendorCompanyName + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorContactPerson, newVendorContactPerson),
                        "Vendor Contact Person Entered as : <b>" + newVendorContactPerson + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorMobileNumber, newVendorMobileNumber),
                        "Vendor Mobile Number Entered as : <b>" + newVendorMobileNumber + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorEmail, newVendorEmail),
                        "Vendor Vendor Email Entered as : <b>" + newVendorEmail + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorSecondaryMobileNumber, newVendorSecondaryMobileNumber),
                        "Vendor Secondary Mobile Number Entered as : <b>" + newVendorSecondaryMobileNumber + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorPanNumber, newVendorPanNumber),
                        "Vendor Pan Number Entered as : <b>" + newVendorPanNumber + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorCinNumber, newVendorCinNumber),
                        "Vendor Cin Number Entered as : <b>" + newVendorCinNumber + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorMsmeNumber, newVendorMsmeNumber),
                        "Vendor MSME Number Entered as : <b>" + newVendorMsmeNumber + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorCreditDays, newVendorCreditDays),
                        "Vendor Credit Days Entered as : <b>" + newVendorCreditDays + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorCreditLimit, newVendorCreditLimit),
                        "Vendor Credit Limit Entered as : <b>" + newVendorCreditLimit + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorDlNumber, newVendorDLNumber),
                        "Vendor DL Number Entered as : <b>" + newVendorDLNumber + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorGstNumber, newVendorGstNumber),
                        "Vendor GST Entered as : <b>" + newVendorGstNumber + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_inventoryVendorCountryArrow),
                        "Vendor Group Dropdown Arrow Clicked");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.input_inventoryDropdownCommonInputBox, 1);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryDropdownCommonInputBox, newVendorCountry);
                Cls_Generic_Methods.customWait(1);

                m_assert.assertTrue(CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_VendorMaster.list_inventoryVendorDropdownResultOptions,
                        newVendorCountry), "Vendor Country Entered as : <b>" + newVendorCountry + "</b>");

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorAddress, newVendorAddress),
                        "Vendor GST Entered as : <b>" + newVendorAddress + "</b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CategoryMaster.button_saveChanges),
                        " Save Changes button clicked ");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.button_addVendorButton, 5);

                // Validation Newly Created Vendor Using Search In Vendor Master

                Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_searchVendorNameBox, newVendorName);
                Cls_Generic_Methods.customWait();
                for (WebElement vendor : oPage_VendorMaster.list_vendorRowForVendorMaster) {
                    if (Cls_Generic_Methods.isElementDisplayed(vendor)) {
                        List<WebElement> vendorNameRow = vendor.findElements(By.xpath("./child::*"));
                        String vendorName = Cls_Generic_Methods.getTextInElement(vendorNameRow.get((0)));
                        String vendorGroup = Cls_Generic_Methods.getTextInElement(vendorNameRow.get((1)));
                        if (vendorName.equalsIgnoreCase(newVendorName) && vendorGroup.equalsIgnoreCase(newVendorGroup)) {
                            bVendorFound = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bVendorFound, "New Vendor Found Successfully in Vendor Master List as :<b> " + newVendorName + "</b>");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Search And Edit Vendor Functionality in Vendor Master")
    public void validateSearchAndEditVendorFunctionality() {

        Page_VendorMaster oPage_VendorMaster = new Page_VendorMaster(driver);

        boolean bEditVendorFoundInVendorList = false;
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            try {

                // Select Vendor Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Master");

                //Search Vendor and Edit With New Information

                Cls_Generic_Methods.waitForPageLoad(driver, 4);

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_searchVendorNameBox, newVendorName),
                        " Vendor Enter In search Box as <b> " + newVendorName + "</b>");
                Cls_Generic_Methods.customWait(2);

                boolean clickOnEditActions = CommonActions.getActionToPerformInInventorySetting(oPage_VendorMaster.list_vendorNameForVendorMaster,
                        newVendorName, oPage_VendorMaster.list_vendorActionsForVendorName, "Edit");
                m_assert.assertTrue(clickOnEditActions, "Edit Button is Clicked in Vendor Master List");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.header_addVendorMasterTemplateTitle, 2);

                if (clickOnEditActions) {

                    Cls_Generic_Methods.clearValuesInElement(oPage_VendorMaster.input_inventoryVendorName);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorName, updateVendorName),
                            "Vendor Name Entered as : <b>" + updateVendorName + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_inventoryVendorGroupArrow),
                            "Vendor Group Dropdown Arrow Clicked");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.input_inventoryDropdownCommonInputBox, 1);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryDropdownCommonInputBox, updateVendorGroup);
                    Cls_Generic_Methods.customWait();

                    m_assert.assertTrue(CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_VendorMaster.list_inventoryVendorDropdownResultOptions,
                            updateVendorGroup), "Vendor Group Entered as : <b>" + updateVendorGroup + "</b>");

                    Cls_Generic_Methods.clearValuesInElement(oPage_VendorMaster.input_inventoryVendorMobileNumber);

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorMobileNumber, updateVendorMobileNumber),
                            "Vendor Mobile Number Entered as : <b>" + updateVendorMobileNumber + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_saveInVendorMaster),
                            " Save Changes button clicked ");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.button_saveInVendorMaster, 5);

                    // Validation Edited Vendor Using Search In Vendor Master

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_searchVendorNameBox, updateVendorName);
                    Cls_Generic_Methods.customWait();
                    for (WebElement vendor : oPage_VendorMaster.list_vendorRowForVendorMaster) {
                        if (Cls_Generic_Methods.isElementDisplayed(vendor)) {
                            List<WebElement> vendorNameRow = vendor.findElements(By.xpath("./child::*"));
                            String vendorName = Cls_Generic_Methods.getTextInElement(vendorNameRow.get((0)));
                            String vendorGroup = Cls_Generic_Methods.getTextInElement(vendorNameRow.get((1)));
                            if (vendorName.equalsIgnoreCase(updateVendorName) && vendorGroup.equalsIgnoreCase(updateVendorGroup)) {
                                bEditVendorFoundInVendorList = true;
                                break;
                            }
                        }
                    }
                    m_assert.assertTrue(bEditVendorFoundInVendorList, "Edit Vendor Found Successfully in Vendor List as : <b>" + updateVendorName + "</b>");
                } else {
                    m_assert.assertTrue(clickOnEditActions, "Edit Button is not Clicked in Vendor Master List");
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

    @Test(enabled = true, description = "Validating Upload Document Vendor Functionality in Vendor Master")
    public void validateUploadDocumentVendorFunctionality() {

        Page_VendorMaster oPage_VendorMaster = new Page_VendorMaster(driver);

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            try {

                // Select Item Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Master");

                //Search Vendor and Click on upload Document

                Cls_Generic_Methods.waitForPageLoad(driver, 4);

                Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_searchVendorNameBox, updateVendorName);
                Cls_Generic_Methods.customWait(1);

                boolean clickOnUploadDocumentActions = CommonActions.getActionToPerformInInventorySetting(oPage_VendorMaster.list_vendorNameForVendorMaster,
                        updateVendorName, oPage_VendorMaster.list_vendorActionsForVendorName, "Upload Documents");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.text_alertMessageInUploadDocument, 2);
                m_assert.assertTrue(clickOnUploadDocumentActions, "Upload Document Button is not Clicked in Sub Category List");

                if (clickOnUploadDocumentActions) {

                    Cls_Generic_Methods.switchToOtherTab();
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_VendorMaster.button_addFileInUploadDocument),
                            "Add File Button is displayed in Upload Document");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_VendorMaster.button_uploadFileInUploadDocument),
                            "Upload File Button is displayed in Upload Document");
                    Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                } else {
                    m_assert.assertTrue(clickOnUploadDocumentActions, "Upload Document Button is not Clicked in Sub Category List");
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

    @Test(enabled = true, description = "Validating View Document Vendor Functionality in Vendor Master")
    public void validateViewDocumentVendorFunctionality() {

        Page_VendorMaster oPage_VendorMaster = new Page_VendorMaster(driver);
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            try {

                // Select Item Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Master");

                //Search Vendor and Link Category to Vendor

                Cls_Generic_Methods.waitForPageLoad(driver, 4);

                Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_searchVendorNameBox, updateVendorName);
                Cls_Generic_Methods.customWait(1);

                boolean clickOnViewDocumentActions = CommonActions.getActionToPerformInInventorySetting(oPage_VendorMaster.list_vendorNameForVendorMaster,
                        updateVendorName, oPage_VendorMaster.list_vendorActionsForVendorName, "View Documents");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.header_viewDocumentTemplateHeader, 3);
                m_assert.assertTrue(clickOnViewDocumentActions, "View Document Button is not Clicked in Sub Category List");

                if (clickOnViewDocumentActions) {
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_VendorMaster.header_viewDocumentTemplateHeader),
                            "Vendor Document Header Displayed In View Document");
                    m_assert.assertInfo("View Document Template Open Successfully");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_VendorMaster.button_closeViewDocumentTemplate),
                            "View Document Closed Successfully");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.button_addVendorButton, 5);
                } else {
                    m_assert.assertTrue(clickOnViewDocumentActions, "View Document Button is not Clicked in Sub Category List");
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

    @Test(enabled = true, description = "Validating Link Category To Vendor Functionality in Vendor Master")
    public void validateLinkCategoryInVendorFunctionality() {

        Page_VendorMaster oPage_VendorMaster = new Page_VendorMaster(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Store oPage_Store = new Page_Store(driver);
        boolean bLinkedVendorFoundInStoreInventory = true;
        boolean bCategoryLinkedInStoreInventory = true;

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            try {
                // Select Vendor Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Master");

                //Search Vendor and Link Category to Vendor

                Cls_Generic_Methods.waitForPageLoad(driver, 4);

                Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_searchVendorNameBox, updateVendorName);
                Cls_Generic_Methods.customWait(1);

                boolean clickOnLinkCategoryActions = CommonActions.getActionToPerformInInventorySetting(oPage_VendorMaster.list_vendorNameForVendorMaster,
                        updateVendorName, oPage_VendorMaster.list_vendorActionsForVendorName, "Link Category");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.header_linkCategoryTemplateTitle, 2);
                m_assert.assertTrue(clickOnLinkCategoryActions, "Link Category Button is Clicked in Vendor Master List");

                if (clickOnLinkCategoryActions) {

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_VendorMaster.input_selectCategorySearchBox),
                            " Link Existing Category Template opened in Vendor Master");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorMaster.input_selectCategorySearchBox),
                            "Select Category Input Box Clicked");
                    Cls_Generic_Methods.customWait(1);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_selectCategorySearchBox, selectedCategoryName);
                    for (WebElement categoryName : oPage_VendorMaster.list_selectCategoryValuesList) {
                        if (Cls_Generic_Methods.getTextInElement(categoryName).contains(selectedCategoryName)) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(categoryName),
                                    "Category clicked as : <b>" + selectedCategoryName + "</b>");
                            break;
                        }
                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.button_saveLinkCategoryInVendorMaster, 1);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_saveLinkCategoryInVendorMaster),
                            " Save button In Link Existing Category Clicked ");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.button_addVendorButton, 5);

                    // Verify Vendor to linked Category In Linked Category Story Inventory

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_facilitySelector),
                            "Facility Selector Button Clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.text_selectedFacilityName, 2);
                    String facilityName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_selectedFacilityName);

                    CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                    CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");

                    Cls_Generic_Methods.waitForPageLoad(driver, 4);

                    boolean clickOnLinkActions = CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetUp.list_facilityNamesForInventory, facilityName,
                            oPage_StoreSetUp.list_storeColumnLinkedToFacility, oPage_StoreSetUp.list_buttonColumnLinkedToStores, "Link", "OpticalStore", "Disable");

                    Cls_Generic_Methods.customWait();
                    m_assert.assertTrue(clickOnLinkActions, "Link Category Button is Clicked in Store Setup List");

                    if (clickOnLinkActions) {

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.dropdown_actionPerformedOnLinkDropdown, 2);
                        m_assert.assertTrue(selectCategoryFromDropdown(oPage_StoreSetUp.list_linkActionDropdown, "Category"),
                                "Category Clicked In Link Dropdown");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.input_selectStoreInLinkExistingStore, 2);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore),
                                "Store input Box clicked in Link Existing Store");
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore, selectedCategoryName);
                        Cls_Generic_Methods.customWait(2);

                        for (WebElement category : oPage_StoreSetUp.list_categoriesInDropdown) {
                            if (Cls_Generic_Methods.getTextInElement(category).equalsIgnoreCase(selectedCategoryName)) {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(category),
                                        "Category Entered as : <b> " + selectedCategoryName + "</b>");
                                bCategoryLinkedInStoreInventory = true;
                                break;
                            }
                        }
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.button_saveLinkCategory),
                                " Save Category Button Clicked");

                        if (bCategoryLinkedInStoreInventory) {

                            m_assert.assertInfo(" Category is already linked ");
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                                    "Closing Link Existing Dispensing Unit ");
                        }

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 3);

                        boolean storeSelectedOnApp = CommonActions.selectStoreOnApp(selectedStoreSetupName);
                        m_assert.assertTrue(storeSelectedOnApp, "Store found " + selectedStoreSetupName);

                        if (storeSelectedOnApp) {
                            Cls_Generic_Methods.switchToOtherTab();
                            Cls_Generic_Methods.waitForPageLoad(driver, 4);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.text_opticalStoreTitle, 2);
                            Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetUp.text_opticalStoreTitle);
                            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                            Cls_Generic_Methods.customWait(1);

                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Store.button_storeInventoryVendorsButton),
                                    "Vendors Button Clicked");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_searchInventoryInStoreInventory, 2);

                            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, updateVendorName),
                                    "Search By Vendor In Vendor");
                            Cls_Generic_Methods.customWait(5);

                            for (WebElement vendors : oPage_Store.list_tableRowListInStoreInventory) {
                                List<WebElement> vendorNameRow = vendors.findElements(By.xpath("./child::*"));
                                String vendorName = Cls_Generic_Methods.getTextInElement(vendorNameRow.get((0)));
                              /*  String vendorNameSplitBySpace[] = vendorName.split(" ");
                                String updateVendorNameSplit[] = updateVendorName.split("_");

                                for (int i = 0; i < vendorNameSplitBySpace.length; i++) {
                                    if (!vendorNameSplitBySpace[i].equalsIgnoreCase(updateVendorNameSplit[i])) {
                                        bLinkedVendorFoundInStoreInventory = false;
                                        break;
                                    }
                                }
                                break;
                            }*/
                                vendorName = vendorName.replaceAll(" ", "");
                                String updateVendorNameSplit = updateVendorName.replaceAll("_", "");
                                if (vendorName.contains(updateVendorNameSplit)) {
                                    bLinkedVendorFoundInStoreInventory = false;
                                    break;
                                }
                            }
                            m_assert.assertTrue(bLinkedVendorFoundInStoreInventory, " Vendor Name <b> " + updateVendorName + "</b> linked to category Name <b>" + selectedCategoryName + " </b> is displayed in linked Store Inventory Name :<b>" + selectedStoreSetupName + "</b>");
                            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                        }
                    } else {
                        m_assert.assertTrue(clickOnLinkActions, "Link Category Button is not Clicked in Store Setup List");
                    }
                } else {
                    m_assert.assertTrue(clickOnLinkCategoryActions, "Link Category Button is not Clicked in Vendor Master List");
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

    @Test(enabled = true, description = "Validating Unlink Category To Vendor Functionality in Vendor Master")
    public void validateUnlinkCategoryInVendorFunctionality() {

        Page_VendorMaster oPage_VendorMaster = new Page_VendorMaster(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Store oPage_Store = new Page_Store(driver);

        boolean bUnlinkedVendorInStoreInventory = false;

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            try {
                // Select Item Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Master");

                //Search Vendor and Link Category to Vendor

                Cls_Generic_Methods.waitForPageLoad(driver, 4);

                Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_searchVendorNameBox, updateVendorName);
                Cls_Generic_Methods.customWait(1);

                boolean clickOnUnlinkCategoryActions = CommonActions.getActionToPerformInInventorySetting(oPage_VendorMaster.list_vendorNameForVendorMaster,
                        updateVendorName, oPage_VendorMaster.list_vendorActionsForVendorName, "Unlink Category");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.header_unlinkCategoryTemplateTitle, 2);
                m_assert.assertTrue(clickOnUnlinkCategoryActions, "Unlink Category Button is Clicked in Vendor Master List");

                if (clickOnUnlinkCategoryActions) {

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_VendorMaster.input_selectCategorySearchBox),
                            " Unlink Existing Category Template opened in Vendor Master");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorMaster.input_selectCategorySearchBox),
                            "Select Category Input Box Clicked");
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_selectCategorySearchBox, selectedCategoryName),
                            " Search By vendor In Vendor Master");
                    for (WebElement categoryName : oPage_VendorMaster.list_selectCategoryValuesList) {
                        if (Cls_Generic_Methods.getTextInElement(categoryName).contains(selectedCategoryName)) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(categoryName),
                                    "Category entered as : <b>" + selectedCategoryName + "</b>");
                            break;
                        }
                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.button_saveLinkCategoryInVendorMaster, 1);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_saveLinkCategoryInVendorMaster),
                            " Save button In Link Dispensing Unit clicked ");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.button_addVendorButton, 5);

                    // Verify Vendor to linked Category In Linked Category Story Inventory

                    boolean storeSelectedOnApp = CommonActions.selectStoreOnApp(selectedStoreSetupName);
                    m_assert.assertTrue(storeSelectedOnApp, "Store found " + selectedStoreSetupName);

                    if (storeSelectedOnApp) {
                        Cls_Generic_Methods.switchToOtherTab();
                        Cls_Generic_Methods.waitForPageLoad(driver, 4);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.text_opticalStoreTitle, 2);
                        Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetUp.text_opticalStoreTitle);
                        Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                        Cls_Generic_Methods.customWait(1);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Store.button_storeInventoryVendorsButton),
                                "Vendors Button Clicked");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_searchInventoryInStoreInventory, 2);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, updateVendorName),
                                "Search By Vendor In Vendor List");
                        Cls_Generic_Methods.customWait(5);
                        oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
                        Cls_Generic_Methods.customWait(5);
                        oPage_Store = new Page_Store(driver);

                        if (Cls_Generic_Methods.isElementDisplayed(oPage_Store.text_nothingToDisplayMessage)) {
                            bUnlinkedVendorInStoreInventory = true;

                        }
                        m_assert.assertTrue(bUnlinkedVendorInStoreInventory, " Vendor unlinked to category is unlinked to store inventory");
                        Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                    }
                } else {
                    m_assert.assertTrue(clickOnUnlinkCategoryActions, "Unlink Category Button is not Clicked in Vendor Master List");
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

    @Test(enabled = true, description = "Validating Disable Vendor Functionality in Vendor Master")
    public void validateDisableVendorFunctionality() {

        Page_VendorMaster oPage_VendorMaster = new Page_VendorMaster(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Store oPage_Store = new Page_Store(driver);

        boolean bDisableVendorInStoreInventory = false;
        boolean bLinkedVendorFoundInStoreInventory = true;
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            try {
                // Select Item Master In Inventory And Supply Chain

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Master");

                //Search Vendor and Link Category to Vendor

                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_searchVendorNameBox, updateVendorName),
                        "Search vendor By Name in List");
                Cls_Generic_Methods.customWait(1);

                boolean clickOnLinkCategoryActions = CommonActions.getActionToPerformInInventorySetting(oPage_VendorMaster.list_vendorNameForVendorMaster,
                        updateVendorName, oPage_VendorMaster.list_vendorActionsForVendorName, "Link Category");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.header_linkCategoryTemplateTitle, 2);
                m_assert.assertTrue(clickOnLinkCategoryActions, "Link Category Button is Clicked in Vendor Master List");

                if (clickOnLinkCategoryActions) {

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_VendorMaster.input_selectCategorySearchBox),
                            " Link Existing Category Template opened in Vendor Master");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorMaster.input_selectCategorySearchBox),
                            "Select Category Input Box Clicked");
                    Cls_Generic_Methods.customWait(1);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_selectCategorySearchBox, selectedCategoryName);
                    for (WebElement categoryName : oPage_VendorMaster.list_selectCategoryValuesList) {
                        if (Cls_Generic_Methods.getTextInElement(categoryName).contains(selectedCategoryName)) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(categoryName), "");
                            break;
                        }
                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.button_saveLinkCategoryInVendorMaster, 1);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_saveLinkCategoryInVendorMaster),
                            " Save button In Link Existing Category clicked ");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.button_addVendorButton, 5);

                    // Disable Vendor In Vendor Master

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_searchVendorNameBox, updateVendorName),
                            "Search vendor By Name in List");
                    Cls_Generic_Methods.customWait(1);

                    boolean clickOnDisableActions = CommonActions.getActionToPerformInInventorySetting(oPage_VendorMaster.list_vendorNameForVendorMaster,
                            updateVendorName, oPage_VendorMaster.list_vendorActionsForVendorName, "Disable");
                    m_assert.assertTrue(clickOnDisableActions, "Disable Button is Clicked in Vendor Master List");

                    Cls_Generic_Methods.customWait(1);
                    if (clickOnDisableActions) {

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.alert_disableConfirmAlert, 2);

                        //Validating Disable Store Functionality

                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetUp.text_disableConfirmAlertTitle),
                                "Disable Confirm Title is Present");
                        m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_StoreSetUp.text_disableConfirmationAlertMessage).contains("Are you sure, you want disable " + updateVendorName + "?"),
                                "Confirmation Message is present");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.button_disableAlertConfirmButton),
                                "");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.button_addVendorButton, 5);

                        // Verify Vendor to linked Category In Linked Category Story Inventory

                        boolean storeSelectedOnApp = CommonActions.selectStoreOnApp(selectedStoreSetupName);
                        m_assert.assertTrue(storeSelectedOnApp, "Store found " + selectedStoreSetupName);

                        if (storeSelectedOnApp) {
                            Cls_Generic_Methods.switchToOtherTab();
                            Cls_Generic_Methods.waitForPageLoad(driver, 4);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.text_opticalStoreTitle, 2);
                            m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_StoreSetUp.text_opticalStoreTitle),
                                    "Optical Store Opened");
                            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                            Cls_Generic_Methods.customWait(1);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Store.button_storeInventoryVendorsButton),
                                    "Vendors Button Clicked");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_searchInventoryInStoreInventory, 2);
                            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, updateVendorName),
                                    "Search Vendor By Name In Vendor List");

                            Cls_Generic_Methods.customWait(5);
                            oPage_Store = new Page_Store(driver);

                            for (WebElement vendors : oPage_Store.list_tableRowListInStoreInventory) {
                                List<WebElement> vendorNameRow = vendors.findElements(By.xpath("./child::*"));
                                String vendorName = Cls_Generic_Methods.getTextInElement(vendorNameRow.get((0)));
                                String vendorStatus = Cls_Generic_Methods.getTextInElement(vendorNameRow.get((4)));
                               /* String vendorNameSplitBySpace[] = vendorName.split(" ");
                                String updateVendorNameSplit[] = updateVendorName.split("_");


                                for (int i = 0; i < vendorNameSplitBySpace.length; i++) {
                                    if (!vendorNameSplitBySpace[i].equalsIgnoreCase(updateVendorNameSplit[i])) {
                                        bLinkedVendorFoundInStoreInventory = false;
                                        break;
                                    }
                                }*/
                                vendorName = vendorName.replaceAll(" ", "");
                                String updateVendorNameSplit = updateVendorName.replaceAll("_", "");
                                if (vendorName.contains(updateVendorNameSplit)) {
                                    bLinkedVendorFoundInStoreInventory = false;
                                }
                                if (bLinkedVendorFoundInStoreInventory && vendorStatus.contains("Inactive")) {
                                    bDisableVendorInStoreInventory = true;
                                    break;
                                }
                            }
                            m_assert.assertTrue(bDisableVendorInStoreInventory, " Vendor Disable in  store inventory");
                            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                        }
                    } else {
                        m_assert.assertTrue(clickOnDisableActions, "Disable Button is not Clicked in Vendor Master List");
                    }
                } else {
                    m_assert.assertTrue(clickOnLinkCategoryActions, "Link Category Button is not Clicked in Vendor Master List");
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