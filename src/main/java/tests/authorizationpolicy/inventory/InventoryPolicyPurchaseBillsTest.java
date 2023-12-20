package tests.authorizationpolicy.inventory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Transaction.Page_Purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static pages.commonElements.CommonActions.getRandomString;

public class InventoryPolicyPurchaseBillsTest extends TestBase {
    String sPolicyRequired = "INVENTORY";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    static String sStore = "Pharmacy automation- Pharmacy";
    static Page_Navbar oPage_Navbar;
    static Page_OrganisationSetup oPage_OrganisationSetup;
    static Page_InventoryPolicy oPage_InventoryPolicy;
    static Page_CommonElements oPage_CommonElements;
    static Page_Master oPage_Master;
    static Page_Purchase oPage_Purchase;
    static String purchaseDateAndTime;

    //POLICY
    String sViewPolicyComponent = "VIEW (PURCHASE BILL)";
    String sAddPolicyComponent = "ADD (PURCHASE BILL)";
    String sApprovePolicyComponent = "APPROVE (PURCHASE BILL)";
    String sEditPolicyComponent = "EDIT (PURCHASE BILL)";
    String sCancelPolicyComponent = "CANCEL (PURCHASE BILL)";
    String sEditTxnDateTimePolicyComponent = "EDIT TXN DATE & TIME (PURCHASE BILL)";
    String sVendorInvoiceViewPolicyComponent ="VENDOR INVOICE VIEW (PURCHASE BILL)";
    String sVendorInvoiceUploadPolicyComponent ="VENDOR INVOICE UPLOAD (PURCHASE BILL)";
    String sVendorInvoiceDeletePolicyComponent ="VENDOR INVOICE DELETE (PURCHASE BILL)";
    String sVendorDocViewPolicyComponent ="VENDOR DOCS (PURCHASE BILL)";

    String sViewPolicyDescription = "Inventory purchase bill view access";
    String sAddPolicyDescription = "Inventory purchase bill creation access";
    String sApprovePolicyDescription = "Inventory purchase bill Approval acsess";
    String sEditPolicyDescription = "Inventory purchase bill Edit access";
    String sCancelPolicyDescription = "Inventory purchase bill Cancel acsess";
    String sEditTxnDateTimePolicyDescription = "Inventory purchase bill Edit Txn date and time acsess";
    String sVendorInvoiceViewPolicyDescription ="Inventory purchase bill vendor invoices view access";
    String sVendorInvoiceUploadPolicyDescription ="Inventory purchase bill vendor invoices upload access";
    String sVendorInvoiceDeletePolicyDescription ="Inventory purchase bill vendor invoices delete access";
    String sVendorDocViewPolicyDescription ="Inventory purchase bill vendor docs View access";

    @BeforeMethod
    private void init() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
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

    @Test(enabled = true, description = "Validate View purchase bills inventory policy")
    public void validatePolicy_ViewPurchaseBills() {
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
            boolean purchaseBillTab = CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            m_assert.assertTrue(!purchaseBillTab, "Validated -->" + sViewPolicyDescription + "-->Purchase Bills Tab is disabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent,sViewPolicyDescription);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            purchaseBillTab = CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            m_assert.assertTrue(purchaseBillTab, "Validated -->" + sViewPolicyDescription + "-->Purchase Bills Tab is enabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate view Purchase Bills policy " + e);
        }

    }

    @Test(enabled = true, description = "Validate Add purchase bills inventory policy")
    public void validatePolicy_AddPurchaseBills() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Purchase = new Page_Purchase(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired,sAddPolicyComponent,sAddPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            bAddButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_purchaseNew);
            m_assert.assertTrue(bAddButtonFound, "Validated -->Inventory purchase transaction creation-->Add functionality is enabled in Purchase/Grn");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate add Purchase Bills policy" + e);
        }
    }

    @Test(enabled = true, description = "Validate Approve purchase bills inventory policy")
    public void validatePolicy_ApprovePurchaseBills() {
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        oPage_Purchase = new Page_Purchase(driver);

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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean selectPurchaseBillStatus = selectPurchaseBills();
            if (!selectPurchaseBillStatus) {
                createNewPurchaseBill();
                selectPurchaseBills();
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_editPurchaseTransaction, 5);
            m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_approvePurchaseTransaction), "Validated -->" + sApprovePolicyDescription + "--> Approve functionality is disabled in Purchase  Bills");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sApprovePolicyComponent,sApprovePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.customWait();
            selectPurchaseBills();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction,10);
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_approvePurchaseTransaction), "Validated -->" + sApprovePolicyDescription + "--> Approve functionality is enabled in Purchase Bills");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate approve Purchase Bills policy" + e);
        }

    }

    @Test(enabled = true, description = "Validate Edit purchase bills inventory policy")
    public void validatePolicy_EditPurchaseBills() {
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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            selectPurchaseBills();
            Cls_Generic_Methods.customWait();
            boolean bAddButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_editPurchaseTransaction);
            m_assert.assertTrue(!bAddButtonFound, "Validated -->" + sEditPolicyDescription + "-->Edit functionality is disabled in Purchase Bills");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent,sEditPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            Cls_Generic_Methods.customWait();
            selectPurchaseBills();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_editPurchaseTransaction,10);
            bAddButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_editPurchaseTransaction);
            m_assert.assertTrue(bAddButtonFound, "Validated -->" +sEditPolicyDescription + "-->Edit functionality is enabled in Purchase Bills");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Edit Purchase Bills policy" + e);
        }
    }

    @Test(enabled = true, description = "Validate Cancel purchase Bills inventory policy")
    public void validatePolicy_CancelPurchaseBills() {
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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            selectPurchaseBills();
            Cls_Generic_Methods.customWait();
            boolean bAddButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_cancelPurchaseTransaction);
            m_assert.assertTrue(!bAddButtonFound, "Validated -->" + sCancelPolicyDescription + "-->Cancel functionality is disabled in Purchase Bills");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCancelPolicyComponent,sCancelPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            Cls_Generic_Methods.customWait();
            selectPurchaseBills();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_cancelPurchaseTransaction,10);
            bAddButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_cancelPurchaseTransaction);
            m_assert.assertTrue(bAddButtonFound, "Validated -->" + sCancelPolicyDescription + "-->Cancel functionality is enabled in Purchase Bills");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Cancel Purchase Bills policy" + e);
        }
    }

    @Test(enabled = true, description = "Validate Edit Transaction Date And Time purchase bills inventory policy")
    public void validatePolicy_EditTransactionDateAndTimePurchaseBills() {
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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            selectPurchaseBills();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_editPurchaseTransaction,10);

            boolean bTransactionDateEditable = checkTransactionDateTimeIsEditable();
            m_assert.assertTrue(!bTransactionDateEditable, "Validated -->" + sEditTxnDateTimePolicyDescription + "-->Transaction date & time edit functionality is disabled in Purchase Bills");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTxnDateTimePolicyComponent,sEditTxnDateTimePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            Cls_Generic_Methods.customWait();
            selectPurchaseBills();
            Cls_Generic_Methods.customWait();
            bTransactionDateEditable = checkTransactionDateTimeIsEditable();
            m_assert.assertTrue(bTransactionDateEditable, "Validated -->" + sEditPolicyDescription + "-->Transaction date & time edit functionality is enabled in Purchase Bills");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Transaction date & time edit Purchase Bills policy" + e);
        }
    }

    @Test(enabled = true, description = "Validate vendor Invoice View PurchaseBill inventory policy")
    public void validatePolicy_VendorInvoiceViewPurchaseBills() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Purchase = new Page_Purchase(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sVendorInvoiceViewPolicyComponent, sVendorInvoiceViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            selectPurchaseBills("approved");
            Cls_Generic_Methods.customWait();
            boolean bVendorInvoiceViewButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_viewVendorInvoicePurchaseBill);
            m_assert.assertTrue(!bVendorInvoiceViewButtonFound, "Validated -->" + sVendorInvoiceViewPolicyDescription + "-->View Vendor Invoice functionality is disabled in Purchase Bills");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sVendorInvoiceViewPolicyComponent, sVendorInvoiceViewPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            Cls_Generic_Methods.customWait();
            selectPurchaseBills("approved");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_viewVendorInvoicePurchaseBill,10);
            bVendorInvoiceViewButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_viewVendorInvoicePurchaseBill);
            m_assert.assertTrue(bVendorInvoiceViewButtonFound, "Validated -->" +sVendorInvoiceViewPolicyDescription + "-->View Vendor Invoice functionality is enabled in Purchase Bills");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Vendor Invoice View Purchase Bill" + e);
        }
    }

    @Test(enabled = true, description = "Validate vendor Invoice Upload PurchaseBill inventory policy")
    public void validatePolicy_VendorInvoiceUploadPurchaseBills() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Purchase = new Page_Purchase(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sVendorInvoiceUploadPolicyComponent,sVendorInvoiceUploadPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            selectPurchaseBills("approved");
            Cls_Generic_Methods.customWait();
            boolean bVendorInvoiceUploadButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_uploadVendorInvoicePurchaseBill);
            m_assert.assertTrue(!bVendorInvoiceUploadButtonFound, "Validated -->" + sVendorInvoiceUploadPolicyDescription + "-->Upload Vendor Invoice functionality is disabled in Purchase Bills");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sVendorInvoiceUploadPolicyComponent,sVendorInvoiceUploadPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            Cls_Generic_Methods.customWait();
            selectPurchaseBills("approved");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_uploadVendorInvoicePurchaseBill,10);
            bVendorInvoiceUploadButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_uploadVendorInvoicePurchaseBill);
            m_assert.assertTrue(bVendorInvoiceUploadButtonFound, "Validated -->" + sVendorInvoiceUploadPolicyDescription + "-->Upload Vendor Invoice functionality is enabled in Purchase Bills");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Vendor Invoice Upload Purchase Bill" + e);
        }
    }

    @Test(enabled = true, description = "Validate vendor Invoice Delete PurchaseBill inventory policy")
    public void validatePolicy_VendorInvoiceDeletePurchaseBills() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Purchase = new Page_Purchase(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired,sVendorInvoiceDeletePolicyComponent,sVendorInvoiceDeletePolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            selectPurchaseBills("approved");
            Cls_Generic_Methods.customWait();
            m_assert.assertInfo(uploadVendorInvoice(), "New File uploaded successfully");
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_viewVendorInvoicePurchaseBill, 10);
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_viewVendorInvoicePurchaseBill);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_uploadedImageVendorInvoicePurchaseBill, 10);
            Actions action = new Actions(driver);
            action.moveToElement(oPage_Purchase.button_uploadedImageVendorInvoicePurchaseBill).build().perform();
            Cls_Generic_Methods.customWait();
            boolean bVendorInvoiceDeleteButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_dropdownDeleteVendorInvoicePurchaseBill);
            m_assert.assertTrue(!bVendorInvoiceDeleteButtonFound, "Validated -->" + sVendorInvoiceDeletePolicyDescription + "-->Delete Vendor Invoice functionality is disabled in Purchase Bills");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sVendorInvoiceDeletePolicyComponent,sVendorInvoiceDeletePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            Cls_Generic_Methods.customWait();
            selectPurchaseBills("approved");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_viewVendorInvoicePurchaseBill,10);
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_viewVendorInvoicePurchaseBill);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_uploadedImageVendorInvoicePurchaseBill, 10);
            action.moveToElement(oPage_Purchase.button_uploadedImageVendorInvoicePurchaseBill).build().perform();
            Cls_Generic_Methods.customWait();
            if (Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_dropdownDeleteVendorInvoicePurchaseBill)) {
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_dropdownDeleteVendorInvoicePurchaseBill);
                bVendorInvoiceDeleteButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_deleteFilesVendorInvoicePurchaseBill);
            }
            m_assert.assertTrue(bVendorInvoiceDeleteButtonFound, "Validated -->" +sVendorInvoiceDeletePolicyDescription + "-->Delete Vendor Invoice functionality is enabled in Purchase Bills");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Vendor Invoice Delete Purchase Bill" + e);
        }
    }

    @Test(enabled = true, description = "Validate vendor Docs View PurchaseBill inventory policy")
    public void validatePolicy_VendorDocsViewPurchaseBills() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Purchase = new Page_Purchase(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sVendorDocViewPolicyComponent,sVendorDocViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            selectPurchaseBills("approved");
            Cls_Generic_Methods.customWait();
            boolean bVendorDocsViewButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_viewVendorDocsPurchaseBill);
            m_assert.assertTrue(!bVendorDocsViewButtonFound, "Validated -->" + sVendorDocViewPolicyDescription + "-->View Vendor Docs functionality is disabled in Purchase Bills");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sVendorDocViewPolicyComponent,sVendorDocViewPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            Cls_Generic_Methods.customWait();
            selectPurchaseBills("approved");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_viewVendorDocsPurchaseBill,10);
            bVendorDocsViewButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_viewVendorDocsPurchaseBill);
            m_assert.assertTrue(bVendorDocsViewButtonFound, "Validated -->" + sVendorDocViewPolicyDescription + "-->View Vendor Docs functionality is enabled in Purchase Bills");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Vendor Docs View Purchase Bill" + e);
        }
    }

    public static boolean createNewPurchaseBill(boolean... approveStatus) {
        boolean flag = false;
        boolean approve = false;
        String vendorName = "Supplier ABC";
        String billType = "Bill";
        String billNumber = "BILL" + getRandomString(4);
        String subStore = "Default";
        String unitCostWOTax = "100";
        String packageQuantity = "2";
        final String sellingPrice = "120";
        String transactionNotes = "Transaction_notes" + getRandomString(4);
        oPage_Purchase = new Page_Purchase(driver);
        oPage_CommonElements = new Page_CommonElements(driver);


        boolean bPurchaseTransactionFound = false;

        try {
            // Creating Purchase Transaction for Created Item
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew,10);
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_purchaseNew);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_Vendor_search, 4);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_Vendor_search, vendorName);
            Cls_Generic_Methods.customWait();
            for (WebElement eVendor : oPage_Purchase.list_select_vendor) {
                Cls_Generic_Methods.clickElementByJS(driver, eVendor);
                break;
            }
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
                        Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_subStore, subStore);
                    }

                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.input_expiryDate)) {
                        Cls_Generic_Methods.clickElement(oPage_Purchase.input_expiryDate);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.select_expiryDateYear, 1);
                        String currentYear = Cls_Generic_Methods.getSelectedValue(oPage_Purchase.select_expiryDateYear);
                        int year = Integer.parseInt(currentYear);
                        int newYear = year + 3;

                        Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_expiryDateYear, Integer.toString(newYear));
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.select_expiryDateDay);
                    }

                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_unitCostWOTax);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_unitCostWOTax, unitCostWOTax);

                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_packageQuantity);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_packageQuantity, packageQuantity);

                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_sellingPrice);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_sellingPrice, sellingPrice);

                    Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveLot);

                } else {
                    m_assert.assertFatal("Item not selected");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_SaveChanges);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }

            Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes);
            Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
            Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber);
            Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
            Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate);
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_deleteOtherCharges);
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 15);
            List<String> purchaseTransactionHeaderList = new ArrayList<String>();

            for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
                purchaseTransactionHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
            }
            for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));
                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Status")));
                    if (purchaseStatus.equalsIgnoreCase("open")) {
                        Cls_Generic_Methods.clickElement(row);
                        Cls_Generic_Methods.customWait(2);
                        Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_Yes, 6);
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.button_Yes);
                        bPurchaseTransactionFound = true;
                        m_assert.assertInfo("Purchase Grn created and approved");
                        break;
                    }
                }
            }
            if (bPurchaseTransactionFound) {
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
                Cls_Generic_Methods.customWait();

                Cls_Generic_Methods.clickElement(oPage_Purchase.button_purchaseNew);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.select_vendorOptionsCreatePurchaseBill, 6);
                Cls_Generic_Methods.selectElementByIndex(oPage_Purchase.select_vendorOptionsCreatePurchaseBill, 1);
                Cls_Generic_Methods.selectElementByIndex(oPage_Purchase.select_createAgainstPurchaseBill, 1);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_Purchase.list_RowsOnCreatePurchaseBillTable.get(0));
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_SaveChanges);
                flag = true;
            } else {
                m_assert.assertFatal("Unable to create Purchase Bill");
            }

            if (approveStatus.length > 0) {
                approve = approveStatus[0];
            }
            if (approve) {
                Cls_Generic_Methods.customWait();
                List<String> purchaseBillHeaderList = new ArrayList<String>();

                for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
                    purchaseBillHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
                }
                for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));
                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseBillHeaderList.indexOf("Status")));

                    if (purchaseStatus.equalsIgnoreCase("open")) {
                        Cls_Generic_Methods.clickElementByJS(driver, row);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_editPurchaseTransaction, 10);
                        Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                        flag = true;
                        break;
                    } else {
                        flag = false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
        return flag;
    }

    public static boolean selectPurchaseBills(String... status) {
        oPage_Purchase = new Page_Purchase(driver);
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


                List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

                String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Status")));

                if (purchaseStatus.equalsIgnoreCase(selectStatus)) {
                    try {
                        Cls_Generic_Methods.clickElementByJS(driver, row);
                    } catch (StaleElementReferenceException ignored) {
                    }
                    flag = true;
                    break;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to find newly created purchase bill " + e);
        }
        return flag;
    }

    public static boolean checkTransactionDateTimeIsEditable() {
        boolean flag = false;

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_editPurchaseTransaction, 10);
            Cls_Generic_Methods.clickElement(driver, oPage_Purchase.button_editPurchaseTransaction);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_purchaseTransactionDate, 10);
            try {
                String readOnlyValueTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "readonly");
                String readOnlyValueDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "readonly");
                if (!readOnlyValueDate.isEmpty() && !readOnlyValueTime.isEmpty()) {
                    flag = false;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                flag = true;
            }
            Cls_Generic_Methods.clickElementByAction(driver, oPage_Purchase.button_closeGrnWithoutSaving);

        } catch (Exception e) {
            m_assert.assertFatal("Unable to check transaction time editable status " + e);
        }
        return flag;
    }

    public static boolean uploadVendorInvoice() {
        boolean flag = false;

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_uploadVendorInvoicePurchaseBill, 10);
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_uploadVendorInvoicePurchaseBill);
            Cls_Generic_Methods.customWait();

            for (String id : driver.getWindowHandles()) {
                Cls_Generic_Methods.switchToWindowHandle(id);
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_addFilesVendorInvoicePurchaseBill)) {
                    break;
                }
            }

            Cls_Generic_Methods.clickElement(driver, oPage_Purchase.button_addFilesVendorInvoicePurchaseBill);
            Cls_Generic_Methods.customWait();
            boolean uploadStatus = CommonActions.uploadFileUsingRobotClass(System.getProperty("user.dir") + "\\test-output\\passed.png");
            Cls_Generic_Methods.customWait();
            if (uploadStatus) {
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.button_uploadFilesVendorInvoicePurchaseBill);
                Cls_Generic_Methods.customWait();
                driver.switchTo().alert().dismiss();
                flag = true;
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to upload vendor invoice" + e);
        }
        return flag;
    }
}
