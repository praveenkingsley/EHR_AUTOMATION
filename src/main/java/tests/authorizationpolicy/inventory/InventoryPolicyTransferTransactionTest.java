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
import pages.store.PharmacyStore.Page_TaxInvoiceDeliveryChallan;
import pages.store.PharmacyStore.Transaction.Page_Receive;
import pages.store.PharmacyStore.Transaction.Page_Transfer;

import java.util.List;

public class InventoryPolicyTransferTransactionTest extends TestBase {
    String sPolicyRequired = "INVENTORY";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String sStore = "Pharmacy automation- Pharmacy";
    String sReceivingStore = "OpticalStore- Optical";
    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_Master oPage_Master;
    Page_Transfer oPage_Transfer;


    String sViewPolicyComponent = "VIEW TRANSFER (TRANSFER/ISSUE)";
    String sAddPolicyComponent = "ADD TRANSFER (TRANSFER/ISSUE)";
    String sApprovePolicyComponent = "APPROVE TRANSFER (TRANSFER/ISSUE)";
    String sEditPolicyComponent = "EDIT TRANSFER (TRANSFER/ISSUE)";
    String sCancelPolicyComponent = "CANCEL TRANSFER (TRANSFER/ISSUE)";
    String sEditTxnDateTimePolicyComponent = "EDIT TRANSFER TXN DATE (TRANSFER/ISSUE)";
    String sReStockPolicyComponent = "RE STOCK TRANSFER (TRANSFER/ISSUE)";
    String sViewPolicyDescription = "Inventory Transfer Transaction View Access";
    String sAddPolicyDescription = "Inventory Transfer Transaction Creation Access";
    String sApprovePolicyDescription = "Inventory Transfer Transaction Approval Access";
    String sEditPolicyDescription = "Inventory Transfer Transaction Edit Access";
    String sCancelPolicyDescription = "Inventory Transfer Transaction Cancellation Access";
    String sEditTxnDateTimePolicyDescription = "Inventory Transfer Transaction Date Edit Access";
    String sReStockPolicyDescription = "Inventory Transfer Transaction Re Stock Access";


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

    @Test(enabled = true, description = "Validate View Transfer Transaction inventory policy")
    public void validateViewTransferTransaction() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Transfer = new Page_Transfer(driver);

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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.customWait(4);
            boolean bViewTransfer = Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.button_newTransaction);
            m_assert.assertFalse(bViewTransfer, "Validated --> Inventory Transfer Transaction View Access --> Transfer Transaction is disabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            bViewTransfer = Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.button_newTransaction);
            m_assert.assertTrue(bViewTransfer, "Validated --> Inventory Transfer Transaction View Access --> Transfer Transaction is enabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate view Transfer policy " + e);
        }

    }

    @Test(enabled = true, description = "Validate Add Transfer Transaction inventory policy")
    public void validateAddTransferTransaction() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Transfer = new Page_Transfer(driver);

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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.customWait(3);
            boolean bAddButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.button_newTransaction);
            m_assert.assertFalse(bAddButtonFound, "Validated --> Inventory Transfer Transaction Creation Access --> Add functionality is disabled in Transfer");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.customWait(4);
            bAddButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.button_newTransaction);
            m_assert.assertTrue(bAddButtonFound, "Validated --> Inventory Transfer Transaction Creation Access --> Add functionality is enabled in Transfer");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate add Transfer policy" + e);
        }
    }

    @Test(enabled = true, description = "Validate Approve Transfer Transaction inventory policy")
    public void validateApproveTransferTransaction() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Transfer = new Page_Transfer(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sApprovePolicyComponent, sApprovePolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_newTransaction, 6);


            boolean createTransferStatus = createDirectTransfer();
            selectTransfer();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_approveTransfer, 2);
            if (createTransferStatus) {
                m_assert.assertFalse(Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.button_approveTransfer), "Validated --> Inventory Transfer Transaction Approval Access --> Approve functionality is disabled in Transfer");
            }

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sApprovePolicyComponent, sApprovePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Transfer.list_transferTransactionRow, 10);
            selectTransfer();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_approveTransfer, 10);
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.button_approveTransferTransaction), "Validated --> Inventory Transfer Transaction Approval Access --> Approve functionality is enabled in Transfer");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate approve Transfer policy" + e);
        }

    }

    @Test(enabled = true, description = "Validate Edit Transfer inventory policy")
    public void validateEditTransferTransaction() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent, sEditPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Transfer.list_transferTransactionRow, 6);
            selectTransfer();
            Cls_Generic_Methods.customWait();
            boolean bEditButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.button_edit);
            m_assert.assertFalse(bEditButtonFound, "Validated --> Inventory Transfer Transaction Edit Access -->Edit functionality is disabled in Transfer");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent, sEditPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Transfer.list_transferTransactionRow, 6);
            Cls_Generic_Methods.customWait();
            selectTransfer();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_edit, 10);
            bEditButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.button_edit);
            m_assert.assertTrue(bEditButtonFound, "Validated --> Inventory Transfer Transaction Edit Access -->Edit functionality is enabled in Transfer");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Edit Transfer policy" + e);
        }
    }

    @Test(enabled = true, description = "Validate Cancel Transfer inventory policy")
    public void validateCancelTransferTransaction() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Transfer = new Page_Transfer(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCancelPolicyComponent, sCancelPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Transfer.list_transferTransactionRow, 6);
            selectTransfer();
            Cls_Generic_Methods.customWait();
            boolean bCancelButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.button_cancel);
            m_assert.assertFalse(bCancelButtonFound, "Validated -->Inventory Transfer Transaction Cancellation Access-->Cancel functionality is disabled in Transfer");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCancelPolicyComponent, sCancelPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Transfer.list_transferTransactionRow, 6);
            Cls_Generic_Methods.customWait();
            selectTransfer();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_edit, 10);
            bCancelButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.button_cancel);
            m_assert.assertTrue(bCancelButtonFound, "Validated -->Inventory Transfer Transaction Cancellation Access-->Cancel functionality is enabled in Transfer");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Cancel Transfer policy" + e);
        }
    }

    @Test(enabled = true, description = "Validate Edit Transaction Date Transfer inventory policy")
    public void validateEditDateTransferTransaction() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Transfer = new Page_Transfer(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTxnDateTimePolicyComponent, sEditTxnDateTimePolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Transfer.list_transferTransactionRow, 6);
            selectTransfer();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_edit, 10);

            boolean bTransactionDateEditable = checkTransactionDateIsEditable();
            m_assert.assertFalse(bTransactionDateEditable, "Validated -->Inventory Transfer Transaction Date Edit Access-->Transaction date edit functionality is disabled in Transfer");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTxnDateTimePolicyComponent, sEditTxnDateTimePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Transfer.list_transferTransactionRow, 6);
            Cls_Generic_Methods.customWait();
            selectTransfer();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_edit, 10);
            bTransactionDateEditable = checkTransactionDateIsEditable();
            m_assert.assertTrue(bTransactionDateEditable, "Validated -->Inventory Transfer Transaction Date Edit Access-->Transaction date edit functionality is enabled in Transfer");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Transaction date edit Transfer policy" + e);
        }
    }

    @Test(enabled = true, description = "Validate Restock Transfer inventory policy")
    public void validateRestockTransferTransaction() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Transfer = new Page_Transfer(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sReStockPolicyComponent, sReStockPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Transfer.list_transferTransactionRow, 6);
            selectTransfer();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_approveTransfer, 10);
            Cls_Generic_Methods.clickElement(oPage_Transfer.button_approveTransfer);
            Cls_Generic_Methods.customWait();
            createTaxInvoiceDeliveryChallanWithMandatoryField("TAX INVOICE");
            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_storesSelector);
            receiveItem();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_storesSelector);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Transfer.list_transferTransactionRow, 6);
            selectTransfer();

            boolean bRestock=Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_restock,5);

            m_assert.assertFalse(bRestock, "Validated -->Inventory Transfer Transaction Re Stock Access-->Re Stock is disabled in Transfer");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired,sReStockPolicyComponent, sReStockPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Transfer.list_transferTransactionRow, 6);
            Cls_Generic_Methods.customWait();
            selectTransfer();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_edit, 10);
            bRestock = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_restock,10);
            m_assert.assertTrue(bRestock, "Validated -->Inventory Transfer Transaction Re Stock Access-->Re Stock is enabled in Transfer");
           Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Restock Transfer policy" + e);
        }
    }

    public boolean createDirectTransfer() {

        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Transfer = new Page_Transfer(driver);
        boolean status = false;
        String sItemDescription = "Luxturna";
        String sQuantity = "1";

        try {

            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(2);
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_newTransaction, 5);
            Cls_Generic_Methods.clickElement(oPage_Transfer.button_newTransaction);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.dropdown_receivingStore, 5);
            Cls_Generic_Methods.clickElement(oPage_Transfer.dropdown_receivingStore);

            boolean receivingStoreFound = false;
            Cls_Generic_Methods.customWait(3);

            for (WebElement eReceivingStore : oPage_Transfer.list_receivingStore) {

                if (Cls_Generic_Methods.getTextInElement(eReceivingStore).equalsIgnoreCase(sReceivingStore.split("-")[0])) {
                    Cls_Generic_Methods.clickElement(eReceivingStore);
                    receivingStoreFound = true;
                    break;
                }
            }

            m_assert.assertInfo(receivingStoreFound, "Selected Received Store as : <b>" + sReceivingStore.split("-")[0] + "</b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_searchByDescription, 5);
            Cls_Generic_Methods.clickElement(oPage_Transfer.button_searchByDescription);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItemToBeTransferred, 5);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_searchByDescription, 5);
            Cls_Generic_Methods.clickElement(oPage_Transfer.button_searchByDescription);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItemToBeTransferred, 5);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_searchItemToBeTransferred, sItemDescription);

            Cls_Generic_Methods.customWait(1);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_searchItemToBeTransferred, " ");
            Cls_Generic_Methods.customWait(3);

            for (WebElement eItemVariantCode : oPage_Transfer.list_itemDescriptionRow) {
                if (Cls_Generic_Methods.getTextInElement(eItemVariantCode).equalsIgnoreCase(sItemDescription)) {
                    Cls_Generic_Methods.clickElement(eItemVariantCode);
                    break;
                }
            }

            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.list_quantityFieldForItemsToTransfer.get(0), sQuantity);
            Cls_Generic_Methods.customWait();

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_saveChanges), "Transfer Transaction saved");
            Cls_Generic_Methods.customWait(6);

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertInfo("Unable to create Transfer " + e);
        }
        return status;
    }

    private void selectTransfer() {

        try {
            for (WebElement row : oPage_Transfer.list_transferTransactionRow) {
                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    Cls_Generic_Methods.clickElement(row);
                    break;
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to select Transfer Transaction " + e);
        }
    }

    public boolean checkTransactionDateIsEditable() {
        boolean flag = false;
        oPage_Transfer = new Page_Transfer(driver);

        try {
            Cls_Generic_Methods.clickElement(oPage_Transfer.button_edit);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_transactionNote, 10);
            try {
                String readOnlyValueDate = Cls_Generic_Methods.getElementAttribute(oPage_Transfer.input_date, "readonly");
                if (!readOnlyValueDate.isEmpty()) {
                    flag = false;
                }
            } catch (NullPointerException e) {
                flag = true;
            }
            Cls_Generic_Methods.clickElementByAction(driver, oPage_Transfer.button_saveChanges);

        } catch (Exception e) {
            m_assert.assertFatal("Unable to check transaction time editable status " + e);
        }
        return flag;
    }

    public void receiveItem() {
        Page_Receive oPage_Receive = new Page_Receive(driver);
        String subStore="Default";
        try {
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sReceivingStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Receive");

            Cls_Generic_Methods.customWait(4);
            boolean bTransactionStatus = false;

            for (WebElement row : oPage_Receive.list_text_transactionIdRow) {
                bTransactionStatus = true;
                Cls_Generic_Methods.clickElement(row);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.button_receiveStock, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Receive.button_receiveStock), "Clicked <b>Receive</b>");

                Cls_Generic_Methods.customWait(5);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.text_receiveQtyUnderReceiveTransactionPopUp, 10);
                boolean selectSubStore =Cls_Generic_Methods.isElementDisplayed(oPage_Receive.select_subStore);

                if (selectSubStore) {
                    selectByOptions(oPage_Receive.select_subStore,subStore);
                }
                Cls_Generic_Methods.clearValuesInElement(oPage_Receive.text_receiveQtyUnderReceiveTransactionPopUp);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Receive.text_receiveQtyUnderReceiveTransactionPopUp, "0.1");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Receive.button_saveChanges), "Clicked <b>Save Changes</b>");
                Cls_Generic_Methods.customWait(4);
                break;
            }

            if (!bTransactionStatus) {
                m_assert.assertFatal("Unable to find transaction in receiving store");
            }

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            m_assert.assertFatal("Unable to Receive " + e);
            e.printStackTrace();
        }
    }

    public void createTaxInvoiceDeliveryChallanWithMandatoryField(String transactionType) {


        Page_TaxInvoiceDeliveryChallan oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);

        try {
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(2);

            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan), "Selected Option in the Left Panel = Tax Invoice / Delivery Challan");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_new, 5);

            //Create New

            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_new), "Clicked New Button");
            Cls_Generic_Methods.customWait();

            if (transactionType.equalsIgnoreCase("DELIVERY CHALLAN")) {
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_deliveryChallan), "Clicked <b>New Delivery Challan</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_headerCreateDeliveryChallan, 10);

            } else if (transactionType.equalsIgnoreCase("TAX INVOICE")) {
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_taxInvoice), "Clicked <b>New Tax invoice</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_headerCreateTaxInvoice, 10);

            } else {
                m_assert.assertFalse("Enter Valid Transaction Type");
            }

            //Select To store
            m_assert.assertInfo(selectByOptions(oPage_TaxInvoiceDeliveryChallan.select_againstStore, sReceivingStore.split("-")[0]), "Selected <b>To</b> store as : <b>" + sReceivingStore + "</b>");
            Cls_Generic_Methods.customWait(5);

            for (WebElement lhsRow : oPage_TaxInvoiceDeliveryChallan.row_lhsCreateTaxInvoiceDeliveryChallan) {
                Cls_Generic_Methods.clickElement(lhsRow);
                break;
            }

            //Dispatch Details
            Cls_Generic_Methods.customWait();
            m_assert.assertInfo(selectByOptions(oPage_TaxInvoiceDeliveryChallan.select_transportationMode, "Test"), "Selected <b>Test</b> in Transportation Mode");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_TaxInvoiceDeliveryChallan.input_transactionId, "1234"), "Entered <b>1234</b> in Transaction Id");
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_saveChanges), "Clicked <b>Save Changes</b>");

            Cls_Generic_Methods.customWait();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_refresh), "Clicked <b>Refresh</b> button");

            Cls_Generic_Methods.customWait();
            for (WebElement row : oPage_TaxInvoiceDeliveryChallan.list_transactionCreatedList) {
                Cls_Generic_Methods.clickElement(row);
                break;
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_approve, 10);
            Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_approve);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to create Tax Invoice -> " + e);
        }
    }

    public boolean selectByOptions(WebElement selectElement, String optionToSelect) {
        boolean status = false;
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(selectElement, 10);
            Cls_Generic_Methods.clickElementByJS(driver, selectElement);
            List<WebElement> allOptions = selectElement.findElements(By.xpath("./option"));
            for (WebElement option : allOptions) {
                String optionValue = Cls_Generic_Methods.getTextInElement(option);
                if (optionValue.contains((optionToSelect))) {
                    Cls_Generic_Methods.clickElement(option);
                    status = true;
                    break;
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to select " + optionToSelect + " -->" + e);
        }
        return status;
    }

}
