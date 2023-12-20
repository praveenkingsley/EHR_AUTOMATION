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
import pages.store.PharmacyStore.Order.Page_WorkOrder;


import java.util.ArrayList;
import java.util.List;


public class InventoryPolicyWorkOrderTest extends TestBase {

    String sPolicyRequired = "INVENTORY";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String sStore = "Pharmacy automation- Pharmacy";
    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_Master oPage_Master;
    Page_WorkOrder oPage_WorkOrder;


    String sViewPolicyComponent = "VIEW (WORK ORDER)";
    String sAddPolicyComponent = "ADD (WORK ORDER)";
    String sApprovePolicyComponent = "APPROVE (WORK ORDER)";
    String sEditPolicyComponent = "EDIT (WORK ORDER)";
    String sCancelPolicyComponent = "CANCEL (WORK ORDER)";
    String sEditTxnDateTimePolicyComponent = "EDIT ORDER DATE & TIME (WORK ORDER)";
    String sClosePolicyComponent = "CLOSE (WORK ORDER)";
    String sNewTransactionPolicyComponent = "NEW TRANSACTION (WORK ORDER)";
    String sViewPolicyDescription = "Inventory Work Order View access";
    String sAddPolicyDescription = "Inventory Work Order Add access";
    String sApprovePolicyDescription = "Inventory Work Order Approval access";
    String sEditPolicyDescription = "Inventory Work Order Edit access";
    String sCancelPolicyDescription = "Inventory Work Order cancel access";
    String sEditTxnDateTimePolicyDescription = "Inventory Work Order Date and Time Edit access";
    String sClosePolicyDescription = "Inventory Work Order Close access";
    String sNewTransactionPolicyDescription = "Inventory Work Order New Transaction access";

    public String expectedLoggedInUser = EHR_Data.user_PRAkashTest;

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

    @Test(enabled = true, description = "Validate View Work Order inventory policy")
    public void validateViewWorkOrder() {
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
            boolean workOrderTab = CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            m_assert.assertFalse(workOrderTab, "Validated -->Work Order view access-->Work Order tab is disabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            workOrderTab = CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            m_assert.assertTrue(workOrderTab, "Validated -->Work Order view access-->Work Order tab is enabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate view Work Order policy " + e);
        }

    }

    @Test(enabled = true, description = "Validate Add Work Order inventory policy")
    public void validateAddWorkOrder() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_WorkOrder = new Page_WorkOrder(driver);

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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.customWait(4);
            boolean bAddButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.button_new);
            m_assert.assertFalse(bAddButtonFound, "Validated -->Work Order creation-->Add functionality is disabled in Work Order");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 6);
            bAddButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.button_new);
            m_assert.assertTrue(bAddButtonFound, "Validated -->Work Order creation-->Add functionality is enabled in Work Order");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate add Work Order policy" + e);
        }
    }

    @Test(enabled = true, description = "Validate Approve Work Order inventory policy")
    public void validateApproveWorkOrder() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_WorkOrder = new Page_WorkOrder(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sApprovePolicyComponent, sApprovePolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 6);
            createWorkOrderWithMandatoryFields();
            refreshAndOpenWorkOrder();
            selectWorkOrder("pending");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_edit,10);
            boolean approveButton = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.button_approve);
            m_assert.assertFalse(approveButton, "Validated -->Work Order Approval --> Approve functionality is disabled in Work Order");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sApprovePolicyComponent, sApprovePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            refreshAndOpenWorkOrder();
            selectWorkOrder("pending");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_edit,10);
            approveButton = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.button_approve);
            m_assert.assertTrue(approveButton, "Validated -->Work Order Approval --> Approve functionality is enabled in Work Order");
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate approve Work Order policy" + e);
        }
    }
    @Test(enabled = true, description = "Validate Edit Work Order inventory policy")
    public void validateEditWorkOrder() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_WorkOrder = new Page_WorkOrder(driver);

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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 6);
            selectWorkOrder("pending");
            Cls_Generic_Methods.customWait();
            boolean bEditButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.button_edit);
            m_assert.assertFalse(bEditButtonFound, "Validated -->Work Order edit-->Edit functionality is disabled in Work Order");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent,sEditPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            refreshAndOpenWorkOrder();
            selectWorkOrder("pending");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_edit,10);
            bEditButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.button_edit);
            m_assert.assertTrue(bEditButtonFound, "Validated -->Work Order edit-->Edit functionality is enabled in Work Order");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Edit Work Order policy" + e);
        }
    }
    @Test(enabled = true, description = "Validate Cancel Work Order inventory policy")
    public void validateCancelWorkOrder() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_WorkOrder = new Page_WorkOrder(driver);

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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 6);
            selectWorkOrder("pending");
            Cls_Generic_Methods.customWait();
            boolean bCancelButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.button_cancel);
            m_assert.assertFalse(bCancelButtonFound, "Validated -->Work Order cancellation-->Cancel functionality is disabled in Work Order");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCancelPolicyComponent,sCancelPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            refreshAndOpenWorkOrder();
            selectWorkOrder("pending");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_cancel,10);
            bCancelButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.button_cancel);
            m_assert.assertTrue(bCancelButtonFound, "Validated -->Work Order cancellation-->Cancel functionality is enabled in WorkOrder");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Cancel Work Order policy" + e);
        }
    }
    @Test(enabled = true, description = "Validate Edit Transaction Date And Time Work Order inventory policy")
    public void validateEditTransactionDateAndTimeWorkOrder() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_WorkOrder = new Page_WorkOrder(driver);

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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 6);
            selectWorkOrder("pending");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_edit,10);

            boolean bTransactionDateEditable = checkTransactionDateTimeIsEditable();
            m_assert.assertFalse(bTransactionDateEditable, "Validated -->Work Order date & time edit access-->Transaction date & time edit functionality is disabled");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTxnDateTimePolicyComponent,sEditTxnDateTimePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            refreshAndOpenWorkOrder();
            selectWorkOrder("pending");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_edit,10);
            bTransactionDateEditable = checkTransactionDateTimeIsEditable();
            m_assert.assertTrue(bTransactionDateEditable, "Validated -->Work Order date & time edit access-->Transaction date & time edit functionality is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Transaction date & time edit Work Order policy" + e);
        }
    }

    @Test(enabled = true, description = "Validate Close Work Order inventory policy")
    public void validateCloseWorkOrder() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_WorkOrder = new Page_WorkOrder(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sClosePolicyComponent,sClosePolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 6);
            selectWorkOrder("pending");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_approve,10);
            Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_approve);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_yes, 6);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_WorkOrder.button_yes);
            Cls_Generic_Methods.customWait();
            boolean bCloseButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.button_close);
            m_assert.assertFalse(bCloseButtonFound, "Validated --> Work Order Close access -->Close functionality is disabled in Work Order");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sClosePolicyComponent,sClosePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            refreshAndOpenWorkOrder();
            selectWorkOrder("approved");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_close,10);
            bCloseButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.button_close);
            m_assert.assertTrue(bCloseButtonFound, "Validated --> Work Order Close access -->Close functionality is enabled in WorkOrder");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Close Work Order policy" + e);
        }
    }
    @Test(enabled = true, description = "Validate New Transaction Work Order inventory policy")
    public void validateNewTransactionWorkOrder() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_WorkOrder = new Page_WorkOrder(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sNewTransactionPolicyComponent,sNewTransactionPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 6);
            selectWorkOrder("approved");
            Cls_Generic_Methods.customWait(3);
            boolean bNewTransactionButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.button_newTransaction);
            m_assert.assertTrue(!bNewTransactionButtonFound, "Validated --> Work Order New Transaction access--> New Transaction is disabled");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired,  sNewTransactionPolicyComponent,sNewTransactionPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            refreshAndOpenWorkOrder();
            selectWorkOrder("approved");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_newTransaction,10);
            bNewTransactionButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.button_newTransaction);
            m_assert.assertTrue(bNewTransactionButtonFound, "Validated --> Work Order New Transaction access--> New Transaction is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate New Transaction Work Order policy" + e);
        }
    }

    public void createWorkOrderWithMandatoryFields() {
        oPage_WorkOrder = new Page_WorkOrder(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        String sVendorName = "Supplier ABC";
        String sInstruction = "AUTO-TEST";
        String orderType = "Urgent";
        String itemRate = "100";
        String quantity = "1";


        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 10);
            Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_new);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.text_headerCreteWorkOrder, 10);
            selectByOptions(oPage_WorkOrder.select_vendor, sVendorName);
            Cls_Generic_Methods.customWait(5);

            for (WebElement row : oPage_WorkOrder.list_row_createWorkOrder) {
                Cls_Generic_Methods.clickElement(row);
                break;
            }
            Cls_Generic_Methods.customWait(5);

            //Order Notes And Order Type
            String orderNotes = "Test Notes";
            Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.input_orderNotesCreateWO, orderNotes);
            Cls_Generic_Methods.selectElementByVisibleText(oPage_WorkOrder.select_orderTypeCreateWO, orderType);
            Cls_Generic_Methods.customWait(3);

            int totalItemSelected = oPage_WorkOrder.list_tableBody_createWorkOrder.size();
            for (int i = 0; i < totalItemSelected; i++) {
                Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.list_inputTableInstructions_createWorkOrder.get(i), sInstruction);
                Cls_Generic_Methods.clearValuesInElement(oPage_WorkOrder.list_inputTableRate_createWorkOrder.get(i));
                Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.list_inputTableRate_createWorkOrder.get(i), itemRate);

                //Validate Quantity
                Cls_Generic_Methods.clearValuesInElement(oPage_WorkOrder.list_inputTableQuantity_createWorkOrder.get(i));
                Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.list_inputTableQuantity_createWorkOrder.get(i), quantity);
                selectByOptions(oPage_WorkOrder.list_selectTableTax_createWorkOrder.get(i), "GST5");
                break;
            }
            Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_removeOtherCharges_createWorkOrder.get(0));
            Cls_Generic_Methods.customWait();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_saveChanges_createWorkOrder), "Clicked <b>Save Changes</b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 15);

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFalse("Unable to validate Create Work Order --> " + e);
        }
    }

    private boolean selectByOptions(WebElement selectElement, String optionToSelect) {
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

    private void refreshAndOpenWorkOrder() {
        try {
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 10);
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to refresh");
        }
    }

    public void selectWorkOrder(String status) {
         oPage_WorkOrder = new Page_WorkOrder(driver);
        try {

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_WorkOrder.list_woHeaderList, 10);
            List<String> workOrderHeaderList = new ArrayList<String>();

            for (WebElement purchaseHeaderList : oPage_WorkOrder.list_woHeaderList) {
                workOrderHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
            }

            int rowNo = 0;
            for (WebElement row : oPage_WorkOrder.list_woCreatedList) {
                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> workOrderRow = row.findElements(By.xpath("./child::*"));
                    String woStatus = Cls_Generic_Methods.getTextInElement(workOrderRow.get(workOrderHeaderList.indexOf("Status")));

                    if (woStatus.equalsIgnoreCase(status)) {
                        Cls_Generic_Methods.clickElement(row);
                        break;
                    }
                }
                rowNo++;
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to find Work Order " + e);
        }

    }
    public boolean checkTransactionDateTimeIsEditable() {
        boolean flag = false;


        try {
            Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_edit);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.input_orderNotesCreateWO, 10);
            try {
                String readOnlyValueTime = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.input_orderTimeCreateWO, "readonly");
                String readOnlyValueDate = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.input_orderDateCreateWO, "readonly");
                if (!readOnlyValueDate.isEmpty() && !readOnlyValueTime.isEmpty()) {
                    flag = false;
                }
            } catch (NullPointerException e) {
                flag = true;
            }
            Cls_Generic_Methods.clickElementByJS(driver, oPage_WorkOrder.button_closeCreateWO);

        } catch (Exception e) {
            m_assert.assertFatal("Unable to check transaction time editable status " + e);
        }
        return flag;
    }
}
