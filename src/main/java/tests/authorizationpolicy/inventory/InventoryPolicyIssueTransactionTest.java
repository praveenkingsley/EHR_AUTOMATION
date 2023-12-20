package tests.authorizationpolicy.inventory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Order.Page_Requisition;
import pages.store.PharmacyStore.Order.Page_RequisitionReceived;
import pages.store.PharmacyStore.Page_TaxInvoiceDeliveryChallan;
import pages.store.PharmacyStore.Transaction.Page_Receive;
import pages.store.PharmacyStore.Transaction.Page_Transfer;

import java.util.List;

public class InventoryPolicyIssueTransactionTest extends TestBase {
    String sPolicyRequired = "INVENTORY";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String sStore = "Pharmacy automation- Pharmacy";
    String sReceivingStore = "CENTRAL HUB 01- Central Hub";
    String sItemDescription;
    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_Master oPage_Master;
    Page_Transfer oPage_Transfer;
    String requisitionQuantity = "1";


    String sViewPolicyComponent = "VIEW ISSUE (TRANSFER/ISSUE)";
    String sApprovePolicyComponent = "APPROVE ISSUE (TRANSFER/ISSUE)";
    String sEditPolicyComponent = "EDIT ISSUE (TRANSFER/ISSUE)";
    String sCancelPolicyComponent = "CANCEL ISSUE (TRANSFER/ISSUE)";
    String sEditTxnDateTimePolicyComponent = "EDIT ISSUE TXN DATE (TRANSFER/ISSUE)";
    String sReStockPolicyComponent = "RE STOCK ISSUE (TRANSFER/ISSUE)";
    String sViewPolicyDescription = "Inventory Issue Transaction View Access";
    String sApprovePolicyDescription = "Inventory Issue Transaction Approval Access";
    String sEditPolicyDescription = "Inventory Issue Transaction Edit Access";
    String sCancelPolicyDescription = "Inventory Issue Transaction Cancel Access";
    String sEditTxnDateTimePolicyDescription = "Inventory Issue Transaction Date Edit Access";
    String sReStockPolicyDescription = "Inventory Issue Transaction Re Stock Access";

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

    @Test(enabled = true, description = "Validate View Issue Transaction inventory policy")
    public void validateViewIssueTransaction() {
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
            sItemDescription = getAvailableStockItemName();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

            //Creating Requisition
            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_storesSelector);
            CommonActions.selectStoreOnApp(sReceivingStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            createNewRequisition(sItemDescription);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

            //Receive Requisition
            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_storesSelector);
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition Received");

            boolean bViewIssue = isNewTransactionEnabled();
            m_assert.assertFalse(bViewIssue, "Validated --> Inventory Issue Transaction View Access --> Issue Transaction is disabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition Received");

            bViewIssue = isNewTransactionEnabled();
            m_assert.assertTrue(bViewIssue, "Validated --> Inventory Issue Transaction View Access --> Issue Transaction is enabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate view Issue policy " + e);
        }

    }

    @Test(enabled = true, description = "Validate Approve Issue Transaction inventory policy")
    public void validateApproveIssueTransaction() {
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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition Received");
            Cls_Generic_Methods.customWait();
            receiveRequisition();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            selectIssue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_approveTransfer, 2);
            m_assert.assertFalse(Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.button_approveTransfer), "Validated --> Inventory Issue Transaction Approval Access --> Approve functionality is disabled in Issue");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sApprovePolicyComponent, sApprovePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Transfer.list_transferTransactionRow, 10);
            selectIssue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_approveTransfer, 10);
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.button_approveTransferTransaction), "Validated --> Inventory Issue Transaction Approval Access --> Approve functionality is enabled in Issue");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate approve Issue policy" + e);
        }

    }
    @Test(enabled = true, description = "Validate Edit Issue inventory policy")
    public void validateEditIssueTransaction() {
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
            selectIssue();
            Cls_Generic_Methods.customWait();
            boolean bEditButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.button_edit);
            m_assert.assertFalse(bEditButtonFound, "Validated --> Inventory Issue Transaction Edit Access -->Edit functionality is disabled in Issue");

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
            selectIssue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_edit, 10);
            bEditButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.button_edit);
            m_assert.assertTrue(bEditButtonFound, "Validated --> Inventory Issue Transaction Edit Access -->Edit functionality is enabled in Issue");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Edit Issue policy" + e);
        }
    }
    @Test(enabled = true, description = "Validate Cancel Issue inventory policy")
    public void validateCancelIssueTransaction() {
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
            selectIssue();
            Cls_Generic_Methods.customWait();
            boolean bCancelButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.button_cancel);
            m_assert.assertFalse(bCancelButtonFound, "Validated -->Inventory Issue Transaction Cancellation Access-->Cancel functionality is disabled in Issue");

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
            selectIssue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_edit, 10);
            bCancelButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.button_cancel);
            m_assert.assertTrue(bCancelButtonFound, "Validated -->Inventory Issue Transaction Cancellation Access-->Cancel functionality is enabled in Issue");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Cancel Issue policy" + e);
        }
    }
    @Test(enabled = true, description = "Validate Edit Transaction Date Issue inventory policy")
    public void validateEditDateIssueTransaction() {
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
            selectIssue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_edit, 10);

            boolean bTransactionDateEditable = checkTransactionDateIsEditable();
            m_assert.assertFalse(bTransactionDateEditable, "Validated -->Inventory Issue Transaction Date Edit Access-->Transaction date edit functionality is disabled in Issue");

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
            selectIssue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_edit, 10);
            bTransactionDateEditable = checkTransactionDateIsEditable();
            m_assert.assertTrue(bTransactionDateEditable, "Validated -->Inventory Issue Transaction Date Edit Access-->Transaction date edit functionality is enabled in Issue");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Transaction date edit Issue policy" + e);
        }
    }
    @Test(enabled = true, description = "Validate Restock Issue inventory policy")
    public void validateRestockIssueTransaction() {
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
            selectIssue();
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
            selectIssue();

            boolean bRestock=Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_restock,5);
            m_assert.assertFalse(bRestock, "Validated -->Inventory Issue Transaction Re Stock Access-->Re Stock is disabled in Issue");
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
            selectIssue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_edit, 10);
            bRestock = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_restock,10);
            m_assert.assertTrue(bRestock, "Validated -->Inventory Issue Transaction Re Stock Access-->Re Stock is enabled in Issue");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Restock Issue policy" + e);
        }
    }

    //Checking available stock to create requisition
    public String getAvailableStockItemName() {

        String itemAvailableStockName = "";
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);


        try {
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");

            for (WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory) {
                String stock = Cls_Generic_Methods.getElementAttribute(itemData, "title");

                if (!stock.equals("Item Empty")) {

                    List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./child::*"));
                    String itemDescriptionName = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));

                    Cls_Generic_Methods.clickElement(itemData);
                    Cls_Generic_Methods.customWait(5);
                    itemAvailableStockName = itemDescriptionName;
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemAvailableStockName;
    }


    public void createNewRequisition(String itemName) {

        String requisitionType = "Urgent";

        Page_Requisition oPage_Requisition = new Page_Requisition(driver);
        try {
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.clickElement(oPage_Requisition.button_newRequisition);
            selectByOptions(oPage_Requisition.select_receivingStore, sStore.split("-")[0]);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_itemSearchBox, itemName);
            oPage_Requisition.input_itemSearchBox.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait();
            for (WebElement row : oPage_Requisition.list_namesOfMedicinesOnLeftInSearchResultPO) {
                String itemValue = Cls_Generic_Methods.getTextInElement(row);
                if (itemValue.equalsIgnoreCase(itemName)) {
                    Cls_Generic_Methods.clickElement(row);
                    break;
                }
            }
            Cls_Generic_Methods.customWait();
            selectByOptions(oPage_Requisition.select_reqType, requisitionType);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_quantityForRequisition, requisitionQuantity);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_Requisition.button_saveRequisition);
            Cls_Generic_Methods.customWait(5);

            boolean bRequisitionOrderFound = false;
            for (WebElement createdReqList : oPage_Requisition.list_dateTimeOfRequisition) {
                Cls_Generic_Methods.clickElement(createdReqList);
                bRequisitionOrderFound = true;
                break;
            }

            if (bRequisitionOrderFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 5);
                Cls_Generic_Methods.clickElement(oPage_Requisition.button_viewOrderRequisition);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_approveRequisition, 15);
                Cls_Generic_Methods.clickElement(oPage_Requisition.button_approveRequisition);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_confirmRequisition, 15);
                Cls_Generic_Methods.clickElement(oPage_Requisition.button_confirmRequisition);
                Cls_Generic_Methods.customWait(5);
            }

        } catch (Exception e) {
            m_assert.assertFatal("Unable to create Requisition "+e);
            e.printStackTrace();
        }

    }
    public boolean isNewTransactionEnabled() {

        boolean flag=true;
        Page_RequisitionReceived oPage_RequisitionReceived=new Page_RequisitionReceived(driver);
        try {
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_RequisitionReceived.list_statusOfRequisitionReceived, 8);
            for (WebElement e : oPage_RequisitionReceived.list_statusOfRequisitionReceived) {

                    Cls_Generic_Methods.clickElement(e);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 10);
                    String value=Cls_Generic_Methods.getElementAttribute(oPage_RequisitionReceived.button_newTransactionRequisition,"title");
                    if(value.contains("denied")){
                        flag=false;
                    }
                    break;
            }

        } catch (Exception e) {
            m_assert.assertFatal("Unable to get new transaction status - Requisition Received "+e);
            e.printStackTrace();
        }

        return flag;
    }
    public void receiveRequisition() {

        boolean flag=false;
        Page_RequisitionReceived oPage_RequisitionReceived=new Page_RequisitionReceived(driver);
        try {
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_RequisitionReceived.list_statusOfRequisitionReceived,10);
            for (WebElement e : oPage_RequisitionReceived.list_statusOfRequisitionReceived) {
                Cls_Generic_Methods.clickElement(e);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 4);
                Cls_Generic_Methods.clickElement(driver,oPage_RequisitionReceived.button_newTransactionRequisition);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.text_headerIssueItems,10);
                Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.list_requisitionItems.get(0));
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_RequisitionReceived.list_inputLotStock,10);
                Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.list_inputLotStock.get(0));
                Cls_Generic_Methods.sendKeysIntoElement(oPage_RequisitionReceived.list_inputLotStock.get(0),requisitionQuantity);
                Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_confirmTransfer);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.text_headerIssueItems,10);
                Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_saveTransfer);
                break;
            }

            Cls_Generic_Methods.customWait();
        } catch (Exception e) {
            m_assert.assertFatal("Unable to receive- Requisition Received "+e);
            e.printStackTrace();
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
    private void selectIssue() {

        try {
            for (WebElement row : oPage_Transfer.list_transferTransactionRow) {
                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    Cls_Generic_Methods.clickElement(row);
                    break;
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to select Issue Transaction " + e);
        }
    }
    public boolean checkTransactionDateIsEditable() {
        boolean flag = false;
        oPage_Transfer = new Page_Transfer(driver);

        try {
            Cls_Generic_Methods.clickElement(oPage_Transfer.button_edit);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_transactionNote, 10);
            try {
                String readOnlyValueDate = Cls_Generic_Methods.getElementAttribute(oPage_Transfer.input_date, "class");
                if (readOnlyValueDate.contains("form-control p7_10 fullname transaction-datepicker hasDatepicker")) {
                    flag = true;
                }else{
                    flag = false;
                }
            } catch (NullPointerException e) {

            }
            Cls_Generic_Methods.clickElementByAction(driver, oPage_Transfer.button_saveChanges);

        } catch (Exception e) {
            m_assert.assertFatal("Unable to check transaction time editable status " + e);
        }
        return flag;
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
}
