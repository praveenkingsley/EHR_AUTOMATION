package tests.authorizationpolicy.inventory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_PurchaseReturn;

public class InventoryPolicyPurchaseReturnTest extends TestBase {
    String sPolicyRequired = "INVENTORY";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    static String sStore = "Pharmacy automation- Pharmacy";
    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_Master oPage_Master;
    Page_Purchase oPage_Purchase;
    Page_PurchaseReturn oPage_PurchaseReturn;
    String vendorName = "Supplier ABC";


    String sViewPolicyComponent = "VIEW (PURCHASE RETURN)";
    String sAddPolicyComponent = "ADD (PURCHASE RETURN)";
    String sReturnTxnDatePolicyComponent = "RETURN TXN DATE (PURCHASE RETURN)";

    String sViewPolicyDescription = "Inventory Purchase Return View Access";
    String sAddPolicyDescription = "Inventory Purchase Return Creation Access";
    String sReturnTxnDatePolicyDescription = "Inventory Purchase Return Transaction Date Edit Access";


    @BeforeMethod
    private void init() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
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
        } catch (Exception e) {
            m_assert.assertFatal("Unable to open organisation setting " + e);
        }
    }

    @Test(enabled = true, description = "Validate View purchase return inventory policy")
    public void validateViewPurchaseReturnPolicy() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            boolean purchaseReturnTab = CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Return");
            m_assert.assertFalse(purchaseReturnTab, "Validated --> Inventory Purchase Return View Access--> Purchase Return Tab is disabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            purchaseReturnTab = CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Return");
            m_assert.assertTrue(purchaseReturnTab, "Validated --> Inventory Purchase Return View Access--> Purchase Return Tab is enabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate view Purchase Return policy " + e);
        }
    }

    @Test(enabled = true, description = "Validate Add purchase return inventory policy")
    public void validateAddPurchaseReturnPolicy() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_PurchaseReturn = new Page_PurchaseReturn(driver);
        oPage_Purchase = new Page_Purchase(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Return");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 3);
            boolean newPurchaseReturn = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_purchaseNew);
            m_assert.assertFalse(newPurchaseReturn, "Validated --> Inventory Purchase Return Creation Access--> New Purchase Return button is disabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Return");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 10);
            newPurchaseReturn = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_purchaseNew);
            m_assert.assertTrue(newPurchaseReturn, "Validated --> Inventory Purchase Return Creation Access--> New Purchase Return button is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Add Purchase Return policy " + e);
        }
    }

    @Test(enabled = true, description = "Validate ReturnTxnDate purchase return inventory policy")
    public void validateReturnTxnDatePurchaseReturnPolicy() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_PurchaseReturn = new Page_PurchaseReturn(driver);
        oPage_Purchase = new Page_Purchase(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sReturnTxnDatePolicyComponent, sReturnTxnDatePolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Return");
            boolean returnTxnDateIsEditable = checkTransactionDateTimeIsEditable();
            m_assert.assertFalse(returnTxnDateIsEditable, "Validated --> Inventory Purchase Return Transaction Date Edit Access --> Purchase Return date edit is disabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sReturnTxnDatePolicyComponent, sReturnTxnDatePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Return");
            returnTxnDateIsEditable = checkTransactionDateTimeIsEditable();
            m_assert.assertTrue(returnTxnDateIsEditable, "Validated --> Inventory Purchase Return Transaction Date Edit Access -->  Purchase Return date edit is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Return Txn Date Purchase Return policy " + e);
        }
    }

    public boolean checkTransactionDateTimeIsEditable() {
        boolean flag = false;

        oPage_PurchaseReturn = new Page_PurchaseReturn(driver);
        oPage_Purchase = new Page_Purchase(driver);
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 10);
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_purchaseNew);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_Vendor_search, 5);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_Vendor_search, vendorName);
            Cls_Generic_Methods.customWait();
            for (WebElement eVendor : oPage_Purchase.list_select_vendor) {
                Cls_Generic_Methods.clickElementByJS(driver, eVendor);
                break;
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseReturn.input_purchaseTransactionDate, 10);
            try {
                String readOnlyValueDate = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseReturn.input_purchaseTransactionDate, "readonly");
                if (!readOnlyValueDate.isEmpty()) {
                    flag = false;
                }
            } catch (NullPointerException e) {
                flag = true;
            }

        } catch (Exception e) {
            m_assert.assertFatal("Unable to check transaction time editable status " + e);
        }
        return flag;
    }
}
