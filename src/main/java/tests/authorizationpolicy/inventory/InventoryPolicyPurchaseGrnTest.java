package tests.authorizationpolicy.inventory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
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
import tests.inventoryStores.pharmacyStore.Transaction.PurchaseGRNTest;

import java.util.ArrayList;
import java.util.List;

import static pages.commonElements.CommonActions.getRandomString;

public class InventoryPolicyPurchaseGrnTest extends TestBase {
    String sPolicyRequired = "INVENTORY";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    static String sStore = "Pharmacy automation- Pharmacy";
    static Page_Navbar oPage_Navbar;
    static Page_OrganisationSetup oPage_OrganisationSetup;
    static Page_InventoryPolicy oPage_InventoryPolicy;
    static Page_CommonElements oPage_CommonElements;
    static Page_Master oPage_Master;
    static Page_Purchase oPage_Purchase;
    static PurchaseGRNTest oPurchaseTest;
    static String purchaseDateAndTime;

    String sViewPolicyComponent = "VIEW (PURCHASE/GRN)";
    String sAddPolicyComponent = "ADD (PURCHASE/GRN)";
    String sApprovePolicyComponent = "APPROVE (PURCHASE/GRN)";
    String sEditPolicyComponent = "EDIT (PURCHASE/GRN)";
    String sCancelPolicyComponent = "CANCEL (PURCHASE/GRN)";
    String sEditTxnDateTimePolicyComponent = "EDIT TXN DATE & TIME (PURCHASE/GRN)";
    String sAssignBarcodePolicyComponent = "ASSIGN BARCODE (PURCHASE/GRN)";
    String sViewPolicyDescription = "Inventory Purchase/GRN View Access";
    String sAddPolicyDescription = "Inventory Purchase/GRN Create Access";
    String sApprovePolicyDescription = "Inventory Purchase/GRN Approval Access";
    String sEditPolicyDescription = "Inventory Purchase/GRN Edit Access";
    String sCancelPolicyDescription = "Inventory Purchase/GRN Cancellation Access";
    String sEditTxnDateTimePolicyDescription = "Inventory Purchase/GRN Transaction Date and Time Edit Access";
    String sAssignBarcodePolicyDescription ="Inventory Purchase/GRN Assign Barcode Access";



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

    @Test(enabled = true, description = "Validate View purchase/grn inventory policy")
    public void validatePolicy_ViewPurchaseGrn() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent,sViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            boolean purchaseGrnTab = CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            m_assert.assertTrue(!purchaseGrnTab, "Validated -->Inventory purchase transaction view access-->Purchase/Grn Tab is disabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent,sViewPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            purchaseGrnTab = CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            m_assert.assertTrue(purchaseGrnTab, "Validated -->Inventory purchase transaction view access-->Purchase/Grn Tab is enabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate view Purchase/Grn policy " + e);
        }

    }

    @Test(enabled = true, description = "Validate Add purchase/grn inventory policy")
    public void validatePolicy_AddPurchaseGrn() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Purchase = new Page_Purchase(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent,sAddPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            boolean bAddButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_purchaseNew);
            m_assert.assertTrue(!bAddButtonFound, "Validated -->Inventory purchase transaction creation-->Add functionality is disabled in Purchase/Grn");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent,sAddPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            bAddButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_purchaseNew);
            m_assert.assertTrue(bAddButtonFound, "Validated -->Inventory purchase transaction creation-->Add functionality is enabled in Purchase/Grn");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate add Purchase/Grn policy" + e);
        }
    }

    @Test(enabled = true, description = "Validate Approve purchase/grn inventory policy")
    public void validatePolicy_ApprovePurchaseGrn() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Purchase = new Page_Purchase(driver);
        oPurchaseTest = new PurchaseGRNTest();

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sApprovePolicyComponent,sApprovePolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean createPurchaseGrnStatus = createPurchaseTransaction();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_editPurchaseTransaction, 5);
            if (createPurchaseGrnStatus) {
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_approvePurchaseTransaction), "Validated -->Inventory Purchase Transaction Approval --> Approve functionality is disabled in Purchase/Grn");
            }

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sApprovePolicyComponent,sApprovePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_purchaseTransactionHeaderList, 10);
            selectPurchaseGrn();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction,10);
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_approvePurchaseTransaction), "Validated -->Inventory Purchase Transaction Approval --> Approve functionality is enabled in Purchase/Grn");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate approve Purchase/Grn policy" + e);
        }

    }

    @Test(enabled = true, description = "Validate Edit purchase/grn inventory policy")
    public void validatePolicy_EditPurchaseGrn() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Purchase = new Page_Purchase(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent,sEditPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            selectPurchaseGrn();
            Cls_Generic_Methods.customWait();
            boolean bEditButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_editPurchaseTransaction);
            m_assert.assertTrue(!bEditButtonFound, "Validated -->Inventory Purchase Transaction edit-->Edit functionality is disabled in Purchase/Grn");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent,sEditPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            Cls_Generic_Methods.customWait();
            selectPurchaseGrn();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_editPurchaseTransaction,10);
            bEditButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_editPurchaseTransaction);
            m_assert.assertTrue(bEditButtonFound, "Validated -->Inventory purchase transaction edit-->Edit functionality is enabled in Purchase/Grn");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Edit Purchase/Grn policy" + e);
        }
    }

    @Test(enabled = true, description = "Validate Cancel purchase/grn inventory policy")
    public void validatePolicy_CancelPurchaseGrn() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Purchase = new Page_Purchase(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCancelPolicyComponent,sCancelPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            selectPurchaseGrn();
            Cls_Generic_Methods.customWait();
            boolean bCancelButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_cancelPurchaseTransaction);
            m_assert.assertTrue(!bCancelButtonFound, "Validated -->Inventory Purchase Transaction cancellation-->Cancel functionality is disabled in Purchase/Grn");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCancelPolicyComponent,sCancelPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            Cls_Generic_Methods.customWait();
            selectPurchaseGrn();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_cancelPurchaseTransaction,10);
            bCancelButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_cancelPurchaseTransaction);
            m_assert.assertTrue(bCancelButtonFound, "Validated -->Inventory Purchase Transaction cancellation-->Cancel functionality is enabled in Purchase/Grn");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Cancel Purchase/Grn policy" + e);
        }
    }

    @Test(enabled = true, description = "Validate Edit Transaction Date And Time purchase/grn inventory policy")
    public void validatePolicy_EditTransactionDateAndTimePurchaseGrn() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Purchase = new Page_Purchase(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTxnDateTimePolicyComponent,sEditTxnDateTimePolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            selectPurchaseGrn();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_editPurchaseTransaction,10);

            boolean bTransactionDateEditable = checkTransactionDateTimeIsEditable();
            m_assert.assertTrue(!bTransactionDateEditable, "Validated -->Inventory Purchase Transaction date & time edit access-->Transaction date & time edit functionality is disabled in Purchase/Grn");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTxnDateTimePolicyComponent,sEditTxnDateTimePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            Cls_Generic_Methods.customWait();
            selectPurchaseGrn();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_editPurchaseTransaction,10);
            bTransactionDateEditable = checkTransactionDateTimeIsEditable();
            m_assert.assertTrue(bTransactionDateEditable, "Validated -->Inventory Purchase Transaction date & time edit access-->Transaction date & time edit functionality is enabled in Purchase/Grn");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Transaction date & time edit Purchase/Grn policy" + e);
        }
    }

    @Test(enabled = true, description = "Validate Assign Barcode purchase/grn inventory policy")
    public void validatePolicy_AssignBarcodePurchaseGrn() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Purchase = new Page_Purchase(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAssignBarcodePolicyComponent,sAssignBarcodePolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            selectPurchaseGrn("approved");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 6);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.button_approvePurchaseTransaction);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_Yes, 6);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.button_Yes);
            Cls_Generic_Methods.customWait();
            boolean bAssignBarcodeButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_assignBarcodePurchaseTransaction);
            m_assert.assertTrue(!bAssignBarcodeButtonFound, "Validated -->" + sAssignBarcodePolicyDescription + "-->Assign Barcode functionality is disabled in Purchase/Grn");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAssignBarcodePolicyComponent, sAssignBarcodePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_purchaseTransactionHeaderList, 10);
            selectPurchaseGrn("approved");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_assignBarcodePurchaseTransaction,10);
            bAssignBarcodeButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_assignBarcodePurchaseTransaction);
            m_assert.assertTrue(bAssignBarcodeButtonFound, "Validated -->" + sAssignBarcodePolicyDescription + "-->ASSIGN BARCODE functionality is enabled in Purchase/Grn");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate ASSIGN BARCODE Purchase/Grn policy" + e);
        }
    }


    private static boolean createPurchaseTransaction() {
        boolean flag = false;
        String vendorName = "Supplier ABC";
        String billType = "Bill";
        String billNumber = "BILL" + getRandomString(4);
        String subStore = "Default";
        String purchaseTransactionTime = "";
        String purchaseTransactionDate = "";
        String unitCostWOTax = "100";
        String packageQuantity = "2";
        final String sellingPrice = "120";
        String transactionNotes = "Transaction_notes" + getRandomString(4);
        String totalNetAmountAfterOtherChargesAddition = "";
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);

        boolean vendorFound = false;
        boolean bPurchaseTransactionFound = false;

        try {
            // Creating Purchase Transaction for Created Item
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_purchaseNew),
                    "New Button clicked in Purchase Transaction");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_Vendor_search, 4);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_Vendor_search, vendorName);
            Cls_Generic_Methods.customWait();
            for (WebElement eVendor : oPage_Purchase.list_select_vendor) {
                Cls_Generic_Methods.clickElementByJS(driver, eVendor);
                vendorFound = true;
                break;
            }
            m_assert.assertTrue(vendorFound, "Vendor present in dropdown: <b> " + vendorName + "</b>");

            boolean itemClicked = false;

            try {

                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_itemNameInPurchaseStore, 10);
                for (WebElement eItemName : oPage_Purchase.list_itemNameInPurchaseStore) {
                    Cls_Generic_Methods.clickElementByJS(driver, eItemName);
                    itemClicked = true;
                    break;
                }

                if (itemClicked) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewLot, 15);

                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.select_subStore)) {
                        m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_subStore, subStore),
                                "Sub Store: <b> " + subStore + " </b>");
                    }

                    if(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.input_expiryDate)) {

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_expiryDate), "Expiry Date Input box clicked");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.select_expiryDateYear, 1);
                        String currentYear = Cls_Generic_Methods.getSelectedValue(oPage_Purchase.select_expiryDateYear);
                        int year = Integer.parseInt(currentYear);
                        int newYear = year + 3;

                        Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_expiryDateYear, Integer.toString(newYear));
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.select_expiryDateDay);
                    }

                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_unitCostWOTax);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_unitCostWOTax, unitCostWOTax),
                            "Unit cost without tax entered as : <b> " + unitCostWOTax + "</b>");

                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_packageQuantity);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_packageQuantity, packageQuantity),
                            "package entry entered as : <b> " + packageQuantity + "</b>");

                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_sellingPrice);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_sellingPrice, sellingPrice),
                            "Selling Price entered as : <b> " + sellingPrice + "</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveLot),
                            " Save Changes Button CLicked In Inventory Template");

                } else {
                    m_assert.assertFatal("Item not selected");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Purchase.button_SaveChanges),
                        " Save Changes Button CLicked In Inventory Template");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }

            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes),
                    " Transaction Notes Entered as :<b> " + transactionNotes + "</b>");
            Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType),
                    "Bill Type Selected:<b> " + billType + " </b>");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber),
                    "Bill Number: <b> " + billNumber + " </b>");
            Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                    "Date of bill selected:<b> " + oPage_Purchase.input_todayBillDate.getText() + " </b>");

            purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
            purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
            purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
            purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");

            purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
            purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_deleteOtherCharges);
            totalNetAmountAfterOtherChargesAddition = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalNetAmount, "value");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                    "Save Changes Button Clicked In A Lot Inventory Template");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 15);

            //Verifying Created Purchase Transaction In List
            System.out.println(purchaseTransactionDate + "|" + purchaseTransactionTime + "|" +
                    totalNetAmountAfterOtherChargesAddition + "|" + vendorName + "|" + transactionNotes + "|Open");
            Cls_Generic_Methods.customWait(7);

            //............
            List<String> purchaseTransactionHeaderList = new ArrayList<String>();

            for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
                purchaseTransactionHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
            }
            for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("GRN Info.")));
                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Status")));

                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    purchaseDateAndTime = date + "|" + time;
                    System.out.println(purchaseDateAndTime);

                    if (purchaseDateAndTime.equals(purchaseTransactionDate + "|" + purchaseTransactionTime) &&
                            purchaseStatus.equalsIgnoreCase("open")
                    ) {
                        bPurchaseTransactionFound = true;
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(row),
                                "Purchase Transaction Clicked  In Transaction List");
                        Cls_Generic_Methods.customWait(2);
                        flag = true;
                        break;
                    }
                }
            }
            m_assert.assertTrue(bPurchaseTransactionFound, "Purchase Transaction Found, Created Successfully for date " + purchaseTransactionDate + " and " + purchaseTransactionTime);

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
        return flag;
    }

    private static boolean selectPurchaseGrn(String... status) {
        boolean flag = false;
        String selectStatus = "open";
        try {
            if (status.length > 0) {
                selectStatus = status[0];
            }

            List<String> purchaseTransactionHeaderList = new ArrayList<String>();

            for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
                purchaseTransactionHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
            }
            for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("GRN Info.")));
                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Status")));

                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    String purchaseDateAndTimeInRow = date + "|" + time;
                    System.out.println(purchaseDateAndTime);


                    if (purchaseDateAndTimeInRow.equals(purchaseDateAndTime) ||
                            purchaseStatus.equalsIgnoreCase(selectStatus)
                    ) {
                        Cls_Generic_Methods.clickElement(row);
                        flag = true;
                        break;
                    }
                }
            }

        } catch (Exception e) {
            m_assert.assertFatal("Unable to find newly created purchase grn " + e);
        }
        return flag;
    }

    public static boolean checkTransactionDateTimeIsEditable() {
        boolean flag = false;

        try {
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_editPurchaseTransaction);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_transactionNotes, 10);
            try {
                String readOnlyValueTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "readonly");
                String readOnlyValueDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "readonly");
                if (!readOnlyValueDate.isEmpty() && !readOnlyValueTime.isEmpty()) {
                    flag = false;
                }
            } catch (NullPointerException e) {
                flag = true;
            }
            Cls_Generic_Methods.clickElementByAction(driver, oPage_Purchase.button_closeGrnWithoutSaving);

        } catch (Exception e) {
            m_assert.assertFatal("Unable to check transaction time editable status " + e);
        }
        return flag;
    }

}

