package tests.authorizationpolicy.inventory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.python.jline.console.completer.CandidateListCompletionHandler;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.store.PharmacyStore.Items.Page_Lot;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Items.Page_Variant;

import java.util.List;


public class MasterPolicyTest extends TestBase {

    String sPolicyRequired = "INVENTORY";

    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String sStore = "Pharmacy automation- Pharmacy";

    String sItemDescription = "MasterPolicyItem" + CommonActions.getRandomString(3);

    String sUpdatedItemDescription = "";


    @Test(enabled = true, description = "Add Item Policy For Master")
    public void addItemPolicyForMaster() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        String sPolicyComponent = "ADD (ITEM MASTER)";
        String sPolicyDescription = "Inventory Item Master Create Access";

        try {
            //Disabling the add item policy for master
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            boolean bAddButtonNotFound = false;
            if (!Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_addNew)) {
                bAddButtonNotFound = true;
                m_assert.assertTrue(bAddButtonNotFound, "<b> Add button in master Disabled </b>");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            boolean bAddButtonFound = false;
            if (Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_addNew)) {
                bAddButtonFound = true;
                m_assert.assertTrue(bAddButtonFound, "<b> Add button in master Enabled </b>");
                CommonActions.addItemInInventory(sItemDescription);
                m_assert.assertInfo("Added <b>" + sItemDescription + " </b> item in master");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            Cls_Generic_Methods.customWait();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Edit Item Policy For Master")
    public void editItemPolicyForMaster() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        String sPolicyComponent = "EDIT (ITEM MASTER)";
        String sPolicyDescription = "Inventory Item Master Edit Access";
        sUpdatedItemDescription = "Updated" + sItemDescription;

        try {

            // Disabling the Edit policy for master
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, sItemDescription);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.pressEnter();
            Cls_Generic_Methods.customWait(3);
            boolean bItemFound = false;
            for (WebElement items : oPage_Master.list_itemDescriptionNameOnItemMaster) {
                String itemDescriptionText = Cls_Generic_Methods.getTextInElement(items);

                if (itemDescriptionText.contains(sItemDescription)) {
                    Cls_Generic_Methods.clickElement(items);
                    Cls_Generic_Methods.customWait(3);
                    bItemFound = true;

                    break;
                }

            }
            if (bItemFound) {
                boolean bEditNotButtonFound = false;
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_editItem)) {
                    bEditNotButtonFound = true;
                    m_assert.assertTrue(bEditNotButtonFound, "<b> Edit button in master Disabled </b>");
                } else {
                    m_assert.assertTrue(bEditNotButtonFound, "Edit button in master is displaying after disabled the policy");
                }
            } else {
                m_assert.assertTrue(bItemFound, "Item not found to edit in master");
            }
            Cls_Generic_Methods.switchToOtherTab();
            // Enabling the Edit policy for master
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, sItemDescription);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.pressEnter();
            Cls_Generic_Methods.customWait(3);
            bItemFound = false;
            for (WebElement items : oPage_Master.list_itemDescriptionNameOnItemMaster) {
                String itemDescriptionText = Cls_Generic_Methods.getTextInElement(items);

                if (itemDescriptionText.contains(sItemDescription)) {
                    bItemFound = true;
                    Cls_Generic_Methods.clickElement(items);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.button_editItem,10);
                    break;
                }

            }


            if (bItemFound) {
                if(Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_editItem)) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.button_editItem, 8);
                    Cls_Generic_Methods.clickElement(oPage_Master.button_editItem);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemDescription, 8);
                    Cls_Generic_Methods.clearValuesInElement(oPage_Master.input_itemDescription);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemDescription, sUpdatedItemDescription), "Updated item description as <b>" + sUpdatedItemDescription);
                    Cls_Generic_Methods.clickElement(oPage_Master.button_save);
                    Cls_Generic_Methods.customWait(5);
                }else {
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_editItem),"Edit button is not displayed");
                }

            } else {
                m_assert.assertTrue(bItemFound, "Item not found to edit in master");
            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Delete Policy For Master")
    public void deleteItemPolicyForMaster() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        String sPolicyComponent = "DELETE (ITEM MASTER)";
        String sPolicyDescription = "Inventory Item Master Delete Access";

        try {


            // Disabling the delete policy for master
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, sUpdatedItemDescription);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.pressEnter();
            Cls_Generic_Methods.customWait(3);
            boolean bItemFound = false;
            for (WebElement items : oPage_Master.list_itemDescriptionNameOnItemMaster) {
                String itemDescriptionText = Cls_Generic_Methods.getTextInElement(items);

                if (itemDescriptionText.contains(sUpdatedItemDescription)) {
                    Cls_Generic_Methods.clickElement(items);
                    Cls_Generic_Methods.customWait(3);
                    bItemFound = true;

                    break;
                }

            }
            if (bItemFound) {
                boolean bDeleteNotButtonFound = false;
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_deleteItem)) {
                    bDeleteNotButtonFound = true;
                    m_assert.assertTrue(bDeleteNotButtonFound, "<b> Delete button in master Disabled </b> ");
                } else {
                    m_assert.assertTrue(bDeleteNotButtonFound, "Delete button in master is displaying after disabled the policy");
                }
            } else {
                m_assert.assertTrue(bItemFound, "Item not found to delete in master");
            }
            Cls_Generic_Methods.switchToOtherTab();
            //Enabling delete policy in master
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, sUpdatedItemDescription);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.pressEnter();
            Cls_Generic_Methods.customWait(3);
            bItemFound = false;
            for (WebElement items : oPage_Master.list_itemDescriptionNameOnItemMaster) {
                String itemDescriptionText = Cls_Generic_Methods.getTextInElement(items);

                if (itemDescriptionText.contains(sUpdatedItemDescription)) {
                    bItemFound = true;
                    Cls_Generic_Methods.clickElement(items);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.button_deleteItem,10);
                    break;
                }

            }


            if (bItemFound) {
                if(Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_deleteItem)) {
                    m_assert.assertInfo("Delete Button is displayed");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.button_deleteItem, 8);
                    Cls_Generic_Methods.clickElement(oPage_Master.button_deleteItem);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemCodeForDelete, 8);
                    String sItemCode = Cls_Generic_Methods.getTextInElement(oPage_Master.text_itemCode);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemCodeForDelete, sItemCode), "entering item code <b>" + sItemCode + "</b> to delete the item in master");
                    Cls_Generic_Methods.customWait();
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Master.button_confirmDeleteItem), "Deleted the item <b>"+sUpdatedItemDescription+"</b> in master");
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Master.list_itemDescriptionNameOnItemMaster, 8);
                    Cls_Generic_Methods.clearValuesInElement(oPage_Master.input_itemNameSearchInItemMaster);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, sUpdatedItemDescription);
                    Cls_Generic_Methods.customWait(3);
                    Cls_Generic_Methods.pressEnter();
                    Cls_Generic_Methods.customWait(3);
                    m_assert.assertTrue(oPage_Master.list_itemDescriptionNameOnItemMaster.isEmpty(),"Item deleted successfully");

                }else {
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_deleteItem),"Delete button is not displayed");
                }

            } else {
                m_assert.assertTrue(bItemFound, "Item not found to edit in master");
            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();



        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }
    @Test(enabled = true, description = "View Policy For Master")
    public void viewPolicyForMaster() {


        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        String sPolicyComponent = "VIEW (ITEM MASTER)";
        String sPolicyDescription = "Inventory Item Master View Access";

        try {


            // Disabling the View policy for master

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            boolean bMatserViewButtonFound = false;
            bMatserViewButtonFound = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items","Master");
            m_assert.assertFalse(bMatserViewButtonFound, "<b> Master Option Can't be viewed from order. It's Disabled </b>");
            Cls_Generic_Methods.customWait(3);
            //enabling for Master View policy
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait(3);
            bMatserViewButtonFound = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            m_assert.assertTrue(bMatserViewButtonFound, "<b> Master Can be viewed from order. It's enabled </b>");
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "View Stock Availability Policy For Master")
    public void viewStockAvailabilityPolicyForMaster() {


        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        String sPolicyComponent = "STOCK AVAILABILITY (ITEM MASTER)";
        String sPolicyDescription = "Inventory Item Master Stock Availability View Access";
        String sItem = "Transfer item";

        try {


            // Disabling the View stock availability policy for master
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            boolean bMasterStockAvailabilityButtonFound = false;
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items","Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster,10);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster,sItem);
            Cls_Generic_Methods.customWait(3);
            oPage_Master.input_itemNameSearchInItemMaster.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);
            boolean bItemFound = false;
            for(WebElement element: oPage_Master.list_itemDescriptionNameOnItemMaster){
                String sItemNameOnUI = Cls_Generic_Methods.getTextInElement(element);
                if(sItemNameOnUI.equalsIgnoreCase(sItem)){
                    Cls_Generic_Methods.clickElement(element);
                    bItemFound = true;
                    break;
                }
            }
            if(bItemFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.text_itemCode,10);
                bMasterStockAvailabilityButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_stockAvailability);
                m_assert.assertFalse(bMasterStockAvailabilityButtonFound, "<b>  Stock Availability Can't be viewed from Master. It's Disabled </b>");
                Cls_Generic_Methods.customWait(3);
            }else {
                m_assert.assertTrue(bItemFound,"Item not found in master");
            }

            //enabling the View stock availability policy for master

            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster,10);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster,sItem);
            Cls_Generic_Methods.customWait(3);
            oPage_Master.input_itemNameSearchInItemMaster.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);
            bItemFound = false;
            for(WebElement element: oPage_Master.list_itemDescriptionNameOnItemMaster){
                String sItemNameOnUI = Cls_Generic_Methods.getTextInElement(element);
                if(sItemNameOnUI.equalsIgnoreCase(sItem)){
                    Cls_Generic_Methods.clickElement(element);
                    bItemFound = true;
                    break;
                }
            }
            if(bItemFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.text_itemCode,10);
                bMasterStockAvailabilityButtonFound =Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_stockAvailability);
                m_assert.assertTrue(bMasterStockAvailabilityButtonFound, "<b>  Stock Availability Can be viewed from Master. It's Enabled </b>");
                Cls_Generic_Methods.customWait(3);
            }else {
                m_assert.assertTrue(bItemFound,"Item not found in master");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }
    @BeforeMethod
    public void openingOrgSettings() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUsers, 16);
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_allUsers), "clicked on All users");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

}
