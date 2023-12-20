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


public class VariantPolicyTest extends TestBase {

    String sPolicyRequired = "INVENTORY";

    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String sStore = "Pharmacy automation- Pharmacy";

    String sItemDescription = "VariantPolicyItem" + CommonActions.getRandomString(3);



    @Test(enabled = true, description = "Delete Variant policy ")
    public void deleteVariantPolicyForVariant() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        Page_Variant oPage_Variant = new Page_Variant(driver);
        String sPolicyComponent = "DELETE (ITEM VARIANT)";
        String sPolicyDescription = "Inventory item Master Varient Delete Access";

        try {
            //Disabling the delete variant policy
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
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.button_addNew, 10);
            CommonActions.addItemInInventory(sItemDescription);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 8);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Variant");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Variant.input_itemNameSearchInItemVariant, 6);
            Cls_Generic_Methods.clearValuesInElement(oPage_Variant.input_itemNameSearchInItemVariant);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Variant.input_itemNameSearchInItemVariant, sItemDescription);
            Cls_Generic_Methods.customWait(3);
            oPage_Variant.input_itemNameSearchInItemVariant.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);
            boolean bItemFound = false;
            for (WebElement items : oPage_Variant.list_itemDescriptionNameOnItemVariant) {
                String itemDescriptionText = Cls_Generic_Methods.getTextInElement(items);

                if (itemDescriptionText.contains(sItemDescription)) {
                    bItemFound = true;
                    Cls_Generic_Methods.clickElement(items);
                    Cls_Generic_Methods.customWait(3);
                    break;
                }

            }
            if (bItemFound) {
                boolean bDeleteNotButtonFound = false;
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_Variant.button_deleteVariant)) {
                    bDeleteNotButtonFound = true;
                    m_assert.assertTrue(bDeleteNotButtonFound, "Delete button in Variant Disabled");
                } else {
                    m_assert.assertTrue(bDeleteNotButtonFound, "Delete button in variant is displaying after disabled the policy");
                }
            } else {
                m_assert.assertTrue(bItemFound, "Item not found to delete in variant");
            }
            Cls_Generic_Methods.switchToOtherTab();
            //Enabling delete policy in Variant
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Variant");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Variant.input_itemNameSearchInItemVariant, 6);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Variant.input_itemNameSearchInItemVariant, sItemDescription);
            Cls_Generic_Methods.customWait(3);
            oPage_Master.input_itemNameSearchInItemMaster.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);
            bItemFound = false;
            for (WebElement items : oPage_Variant.list_itemDescriptionNameOnItemVariant) {
                String itemDescriptionText = Cls_Generic_Methods.getTextInElement(items);

                if (itemDescriptionText.contains(sItemDescription)) {
                    bItemFound = true;
                    Cls_Generic_Methods.clickElement(items);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Variant.button_deleteVariant, 10);
                    break;
                }

            }


            if (bItemFound) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Variant.button_deleteVariant)) {
                    m_assert.assertInfo("Delete Button is displayed");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Variant.button_deleteVariant, 8);
                    Cls_Generic_Methods.clickElement(oPage_Variant.button_deleteVariant);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Variant.input_variantCodeForDelete, 8);
                    String sVariantCode = Cls_Generic_Methods.getTextInElement(oPage_Variant.text_variantCode);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Variant.input_variantCodeForDelete, sVariantCode), "entering Variant code <b>" + sVariantCode + "</b> to delete the variant");
                    Cls_Generic_Methods.customWait();
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_confirmDeleteItem), "Deleted the variant <b>" + sItemDescription + "</b> in variant Page");
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Variant.list_itemDescriptionNameOnItemVariant, 8);
                    Cls_Generic_Methods.clearValuesInElement(oPage_Variant.input_itemNameSearchInItemVariant);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Variant.input_itemNameSearchInItemVariant, sItemDescription);
                    Cls_Generic_Methods.customWait(3);
                    oPage_Master.input_itemNameSearchInItemMaster.sendKeys(Keys.ENTER);
                    Cls_Generic_Methods.customWait(3);
                    m_assert.assertTrue(oPage_Variant.list_itemDescriptionNameOnItemVariant.isEmpty(), "Variant deleted successfully");

                } else {
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Variant.button_deleteVariant), "Delete button is not displayed");
                }

            } else {
                m_assert.assertTrue(bItemFound, "Item not found to delete in variant");
            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }
    @Test(enabled = true, description = "View Policy For Variant")
    public void viewPolicyForVariant() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        String sPolicyComponent = "VIEW (ITEM VARIANT)";
        String sPolicyDescription = "Inventory Item Master Variant View Access";

        try {


            // Disabling the View policy for master
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sPolicyComponent, sPolicyDescription);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            boolean bVariantViewButtonFound = false;
            bVariantViewButtonFound = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items","Variant");
            m_assert.assertFalse(bVariantViewButtonFound, "<b> Variant Option Can't be viewed from Items. It's Disabled </b>");
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
            bVariantViewButtonFound = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Variant");
            m_assert.assertTrue(bVariantViewButtonFound, "<b> Variant Can be viewed from Items. It's enabled </b>");
            Cls_Generic_Methods.customWait(3);
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