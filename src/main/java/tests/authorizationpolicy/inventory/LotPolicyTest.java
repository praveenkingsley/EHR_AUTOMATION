package tests.authorizationpolicy.inventory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
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


public class LotPolicyTest extends TestBase {

    String sPolicyRequired = "INVENTORY";

    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String sStore = "Pharmacy automation- Pharmacy";

    String sItemDescription = "LotPolicy";
    String sNewMRP = "300.0";



    @Test(enabled = true, description = "Edit Lot Price policy ")
    public void editLotPricePolicy() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Lot oPage_Lot = new Page_Lot(driver);
        String sPolicyComponent = "EDIT (ITEM LOT)";
        String sPolicyDescription = "Inventory Item Lot Edit Access";

        try {

            //disabling the edit lot policy
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.input_InventorySearch, 10);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.input_InventorySearch, 6);
            Cls_Generic_Methods.clearValuesInElement(oPage_Lot.input_InventorySearch);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_InventorySearch, sItemDescription);
            Cls_Generic_Methods.customWait(3);
            oPage_Lot.input_InventorySearch.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);
            boolean bItemFound = false;
            for (WebElement items : oPage_Lot.list_LotDetailsOnVariants) {
                String itemDescriptionText = Cls_Generic_Methods.getTextInElement(items);

                if (itemDescriptionText.contains(sItemDescription)) {
                    bItemFound = true;
                    Cls_Generic_Methods.clickElement(items);
                    Cls_Generic_Methods.customWait(3);
                    break;
                }

            }
            if (bItemFound) {
                boolean bEditNotButtonFound = false;
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_Lot.button_editLot)) {
                    bEditNotButtonFound = true;
                    m_assert.assertTrue(bEditNotButtonFound, "<b> Edit button in Lot Disabled </b>");
                } else {
                    m_assert.assertTrue(bEditNotButtonFound, "Edit button in Lot is displaying after disabled the policy");
                }
            } else {
                m_assert.assertTrue(bItemFound, "Item not found to Edit Lot");
            }
            Cls_Generic_Methods.switchToOtherTab();
            //Enabling Edit policy in Lot
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.input_InventorySearch, 6);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_InventorySearch, sItemDescription);
            Cls_Generic_Methods.customWait(3);
            oPage_Lot.input_InventorySearch.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);
            bItemFound = false;
            for (WebElement items : oPage_Lot.list_LotDetailsOnVariants) {
                String itemDescriptionText = Cls_Generic_Methods.getTextInElement(items);

                if (itemDescriptionText.contains(sItemDescription)) {
                    bItemFound = true;
                    Cls_Generic_Methods.clickElement(items);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.button_editLot, 10);
                    break;
                }

            }


            if (bItemFound) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Lot.button_editLot)) {
                   // String sMRP = Cls_Generic_Methods.getTextInElement(oPage_Lot.text_mrpPrice);
                    m_assert.assertInfo("Edit Lot Button is displayed");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.button_editLot, 8);
                    Cls_Generic_Methods.clickElement(oPage_Lot.button_editLot);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.input_newMRP, 8);
                    Cls_Generic_Methods.clearValuesInElement(oPage_Lot.input_newMRP);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_newMRP,sNewMRP),"Editing new selling price <b>"+sNewMRP+" </b>for the Lot");
                    Cls_Generic_Methods.clickElement(oPage_Lot.button_updateLot);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.input_InventorySearch, 6);
                    Cls_Generic_Methods.clearValuesInElement(oPage_Lot.input_InventorySearch);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_InventorySearch, sItemDescription);
                    Cls_Generic_Methods.customWait(3);
                    oPage_Lot.input_InventorySearch.sendKeys(Keys.ENTER);
                    Cls_Generic_Methods.customWait(3);
                     bItemFound = false;
                    for (WebElement items : oPage_Lot.list_LotDetailsOnVariants) {
                        String itemDescriptionText = Cls_Generic_Methods.getTextInElement(items);

                        if (itemDescriptionText.contains(sItemDescription)) {
                            bItemFound = true;
                            Cls_Generic_Methods.clickElement(items);
                            Cls_Generic_Methods.customWait(3);
                            break;
                        }

                    }
                    if(bItemFound)
                    {
                        String sNewPrice = Cls_Generic_Methods.getTextInElement(oPage_Lot.text_mrpPrice).replace("₹","");
                        m_assert.assertTrue(Double.parseDouble(sNewMRP)==Double.parseDouble(sNewPrice),"Lot Price Edited successfully");

                    }else {
                        m_assert.assertTrue(bItemFound, "Item not found to Edit Lot");
                    }



                } else {
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Lot.button_editLot), "Edit button in Lot is not displayed");
                }

            } else {
                m_assert.assertTrue(bItemFound, "Item not found to Edit Lot Price");
            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "View Policy For Lot")
    public void viewPolicyForLot() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        String sPolicyComponent = "VIEW (ITEM LOT)";
        String sPolicyDescription = "Inventory Item Lot View Access";

        try {


            // Disabling the View policy for Lot
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            boolean bLotViewButtonFound = false;
            bLotViewButtonFound = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items","Lot");
            m_assert.assertFalse(bLotViewButtonFound, "<b> Lot Option Can't be viewed from Items. It's Disabled </b>");
            Cls_Generic_Methods.customWait(3);
            //enabling for Lot View policy
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
            bLotViewButtonFound = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
            m_assert.assertTrue(bLotViewButtonFound, "<b> Lot Can be viewed from Items. It's enabled </b>");
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "BlockLot policy for Lot ")
    public void blockLotPolicyForLot() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Lot oPage_Lot = new Page_Lot(driver);
        String sPolicyComponent = "BLOCK LOT (ITEM LOT)";
        String sPolicyDescription = "Inventory Item Lot Block Access";

        try {

            //disabling the BlockLot Policy
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.input_InventorySearch, 10);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.input_InventorySearch, 6);
            Cls_Generic_Methods.clearValuesInElement(oPage_Lot.input_InventorySearch);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_InventorySearch, sItemDescription);
            Cls_Generic_Methods.customWait(3);
            oPage_Lot.input_InventorySearch.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);
            boolean bItemFound = false;
            for (WebElement items : oPage_Lot.list_LotDetailsOnVariants) {
                String itemDescriptionText = Cls_Generic_Methods.getTextInElement(items);

                if (itemDescriptionText.contains(sItemDescription)) {
                    bItemFound = true;
                    Cls_Generic_Methods.clickElement(items);
                    Cls_Generic_Methods.customWait(3);
                    break;
                }

            }
            if (bItemFound) {
                boolean bBlockLotButtonNotFound = false;
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_Lot.button_blockLot)) {
                    bBlockLotButtonNotFound = true;
                    m_assert.assertTrue(bBlockLotButtonNotFound, "<b> Block Lot button in Lot Disabled </b> ");
                } else {
                    m_assert.assertTrue(bBlockLotButtonNotFound, "Block Lot button in Lot is displaying after disabled the policy");
                }
            } else {
                m_assert.assertTrue(bItemFound, "Item not found to Block Lot");
            }
            Cls_Generic_Methods.switchToOtherTab();
            //Enabling Block Lot policy in Lot
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.input_InventorySearch, 6);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_InventorySearch, sItemDescription);
            Cls_Generic_Methods.customWait(3);
            oPage_Lot.input_InventorySearch.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);
            bItemFound = false;
            for (WebElement items : oPage_Lot.list_LotDetailsOnVariants) {
                String itemDescriptionText = Cls_Generic_Methods.getTextInElement(items);

                if (itemDescriptionText.contains(sItemDescription)) {
                    bItemFound = true;
                    Cls_Generic_Methods.clickElement(items);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.button_blockLot, 10);
                    break;
                }

            }


            if (bItemFound) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Lot.button_blockLot)) {
                    m_assert.assertInfo("<b>BlockLot Button is displayed");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Lot.button_blockLot),"Clicked Block Lot Button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.input_blockLotComment,10);
                    Cls_Generic_Methods.customWait(5);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_blockLotComment,"BlockLot");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Lot.button_blockLotConfirmation),"Blocked the Lot for the Item <b>"+sItemDescription);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Lot.button_unblockLot,10);
                    Cls_Generic_Methods.customWait(5);
                    String sBlockedStock = Cls_Generic_Methods.getTextInElement(oPage_Lot.text_blockedLotCount);
                    String sStockOfTheItem = Cls_Generic_Methods.getTextInElement(oPage_Lot.text_stockCount);
                    m_assert.assertTrue(Double.parseDouble(sBlockedStock)==Double.parseDouble(sStockOfTheItem),"Validated the blocked stock count, and the count is <b>"+sBlockedStock);

                } else {
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Lot.button_editLot), "Block Lot in Lot is not displayed");
                }

            } else {
                m_assert.assertTrue(bItemFound, "Item not found to Block Lot ");
            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "UnblockLot policy for Lot ")
    public void unblockLotPolicyForLot() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Lot oPage_Lot = new Page_Lot(driver);
        String sPolicyComponent = "UNBLOCK LOT (ITEM LOT)";
        String sPolicyDescription = "Inventory Item Lot Un Block Access";

        try {


            //disabling the BlockLot Policy
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.input_InventorySearch, 10);
            Cls_Generic_Methods.customWait(3);
           // boolean bEditButtonNotFound = false;
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.input_InventorySearch, 6);
            Cls_Generic_Methods.clearValuesInElement(oPage_Lot.input_InventorySearch);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_InventorySearch, sItemDescription);
            Cls_Generic_Methods.customWait(3);
            oPage_Lot.input_InventorySearch.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);
            boolean bItemFound = false;
            for (WebElement items : oPage_Lot.list_LotDetailsOnVariants) {
                String itemDescriptionText = Cls_Generic_Methods.getTextInElement(items);

                if (itemDescriptionText.contains(sItemDescription)) {
                    bItemFound = true;
                    Cls_Generic_Methods.clickElement(items);
                    Cls_Generic_Methods.customWait(3);
                    break;
                }

            }
            if (bItemFound) {
                boolean bBlockLotButtonNotFound = false;
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_Lot.button_unblockLot)) {
                    bBlockLotButtonNotFound = true;
                    m_assert.assertTrue(bBlockLotButtonNotFound, " <b> UnBlock Lot button in Lot Disabled </b> ");
                } else {
                    m_assert.assertTrue(bBlockLotButtonNotFound, "UnBlock Lot button in Lot is displaying after disabled the policy");
                }
            } else {
                m_assert.assertTrue(bItemFound, "Item not found to UnBlock Lot");
            }
            Cls_Generic_Methods.switchToOtherTab();
            //Enabling Block Lot policy in Lot
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.input_InventorySearch, 6);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_InventorySearch, sItemDescription);
            Cls_Generic_Methods.customWait(3);
            oPage_Lot.input_InventorySearch.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);
            bItemFound = false;
            for (WebElement items : oPage_Lot.list_LotDetailsOnVariants) {
                String itemDescriptionText = Cls_Generic_Methods.getTextInElement(items);

                if (itemDescriptionText.contains(sItemDescription)) {
                    bItemFound = true;
                    Cls_Generic_Methods.clickElement(items);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.button_unblockLot, 10);
                    break;
                }

            }


            if (bItemFound) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Lot.button_unblockLot)) {
                    m_assert.assertInfo("<b>UnBlockLot Button is displayed");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Lot.button_unblockLot),"Clicked UnBlock Lot Button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.input_unblockLotComment,10);
                    Cls_Generic_Methods.customWait(5);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_unblockLotComment,"UnBlockLot");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Lot.button_unblockLotConfirmation),"UnBlocked the Lot for the Item <b>"+sItemDescription);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Lot.button_blockLot,10);
                    Cls_Generic_Methods.customWait(5);
                    String sBlockedStock = Cls_Generic_Methods.getTextInElement(oPage_Lot.text_blockedLotCount);
                    String sStockOfTheItem = Cls_Generic_Methods.getTextInElement(oPage_Lot.text_stockCount);
                    m_assert.assertTrue(Double.parseDouble(sBlockedStock)!=Double.parseDouble(sStockOfTheItem),"Validated the blocked stock count after unblocking should be 0, and the count on UI is <b>"+sBlockedStock);

                } else {
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Lot.button_unblockLot), "UnBlock Lot in Lot is not displayed");
                }

            } else {
                m_assert.assertTrue(bItemFound, "Item not found to UnBlock Lot ");
            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();


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