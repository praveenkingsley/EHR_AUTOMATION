package tests.authorizationpolicy.inventory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.mongodb.gridfs.CLI;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Order.Page_Indent;




public class InventoryPolicy extends TestBase {

    String sPolicyRequired = "INVENTORY";
    String sPolicyComponent = "ADD (ITEM MASTER)";
    String sPolicyDescription = "Inventory Item master creation";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String sStore ="Pharmacy automation- Pharmacy";


    @Test(enabled = true, description = "Inventory Policy For Master")
    public void inventoryPolicyForMaster() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        Page_InventoryPolicy oPage_InventoryPolicy=new Page_InventoryPolicy(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Master oPage_Master = new Page_Master(driver);

        try {
            //Opening Organization Settings
            String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
            Cls_Generic_Methods.customWait(3);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUsers, 16);

            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_allUsers), "clicked on All users");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser,sPolicyRequired,sPolicyComponent,sPolicyDescription);
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
            if(!Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_addNew))
            {
             bAddButtonNotFound = true;
             m_assert.assertTrue(bAddButtonNotFound,"add button in master disabled");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser,sPolicyRequired,sPolicyComponent,sPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            boolean bAddButtonFound = false;
            if(Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_addNew))
            {
                bAddButtonFound = true;
                m_assert.assertTrue(bAddButtonFound,"add button in master Enabled");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        }catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        }
}
