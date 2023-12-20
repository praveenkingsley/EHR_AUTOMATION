package tests.authorizationpolicy.inventory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Order.Page_WorkOrder;
import pages.store.PharmacyStore.Transaction.Page_WorkOrderFulfilment;
import tests.inventoryStores.pharmacyStore.Transaction.WorkOrderFulfilmentTest;

import java.util.Random;

public class InventoryPolicyWorkOrderFulfilmentTest extends TestBase {
    String sPolicyRequired = "INVENTORY";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String sStore = "Pharmacy automation- Pharmacy";
    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_Master oPage_Master;
    Page_WorkOrder oPage_WorkOrder;
    Page_WorkOrderFulfilment oPage_WorkOrderFulfilment;

    WorkOrderFulfilmentTest workOrderFulfilment;
    InventoryPolicyWorkOrderTest workOrder;

    String sViewPolicyComponent = "VIEW (WORK ORDER FULFILMENT)";
    String sApprovePolicyComponent = "APPROVE (WORK ORDER FULFILMENT)";
    String sEditPolicyComponent = "EDIT (WORK ORDER FULFILMENT)";
    String sCancelPolicyComponent = "CANCEL (WORK ORDER FULFILMENT)";
    String sEditTxnDateTimePolicyComponent = "EDIT WOF DATE & TIME (WORK ORDER FULFILMENT)";

    String sViewPolicyDescription = "Inventory Work Order Fulfilment View access";
    String sApprovePolicyDescription = "Inventory Work Order Fulfilment Approval access";
    String sEditPolicyDescription = "Inventory Work Order Fulfilment Edit access";
    String sCancelPolicyDescription = "Inventory Work Order Fulfilment Cancel access";
    String sEditTxnDateTimePolicyDescription = "Inventory Work Order Fulfilment Date and Time Edit access";


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

    @Test(enabled = true, description = "Validate View Work Order Fulfilment inventory policy")
    public void validateViewWorkOrderFulfilment() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_WorkOrder = new Page_WorkOrder(driver);
        workOrder = new InventoryPolicyWorkOrderTest();


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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            workOrder.createWorkOrderWithMandatoryFields();
            workOrder.selectWorkOrder("pending");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_approve, 10);
            Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_approve);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_yes, 6);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_WorkOrder.button_yes);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_newTransaction, 2);
            boolean workOrderFulfilmentButton = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.button_newTransaction);
            m_assert.assertFalse(workOrderFulfilmentButton, "Validated -->Work Order Fulfilment view access-->Work Order Fulfilment tab is disabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.customWait();
            workOrder.selectWorkOrder("approved");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_newTransaction, 5);
            workOrderFulfilmentButton = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.button_newTransaction);
            m_assert.assertTrue(workOrderFulfilmentButton, "Validated -->Work Order Fulfilment view access-->Work Order Fulfilment tab is enabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate view Work Order Fulfilment policy " + e);
        }
    }

    @Test(enabled = true, description = "Validate Approve Work Order Fulfilment inventory policy")
    public void validateApproveWorkOrderFulfilment() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_WorkOrder = new Page_WorkOrder(driver);
        workOrder = new InventoryPolicyWorkOrderTest();
        workOrderFulfilment=new WorkOrderFulfilmentTest();
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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 6);
            workOrder.selectWorkOrder("approved");
            createWorkOrderFulfilmentWithMandatoryFields();
            refreshAndOpenWorkOrderFulfilment();
             workOrderFulfilment.selectWorkOrderFulfilment("open");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_edit, 10);
            boolean approveButton = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrderFulfilment.button_approve);
            m_assert.assertFalse(approveButton, "Validated -->Work Order Fulfilment Approval --> Approve functionality is disabled");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sApprovePolicyComponent, sApprovePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            refreshAndOpenWorkOrderFulfilment();
            workOrderFulfilment.selectWorkOrderFulfilment("open");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_edit, 10);
            approveButton = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrderFulfilment.button_approve);
            m_assert.assertTrue(approveButton, "Validated -->Work Order Fulfilment Approval --> Approve functionality is enabled");
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate approve Work Order Fulfilment policy" + e);
        }
    }
    @Test(enabled = true, description = "Validate Edit Work Order Fulfilment inventory policy")
    public void validateEditWorkOrderFulfilment() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_WorkOrderFulfilment = new Page_WorkOrderFulfilment(driver);
        workOrder = new InventoryPolicyWorkOrderTest();

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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Work Order Fulfilment");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_refresh, 6);
            workOrderFulfilment.selectWorkOrderFulfilment("open");
            Cls_Generic_Methods.customWait();
            boolean bEditButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrderFulfilment.button_edit);
            m_assert.assertFalse(bEditButtonFound, "Validated -->Work Order Fulfilment edit-->Edit functionality is disabled");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent,sEditPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            refreshAndOpenWorkOrderFulfilment();
            workOrderFulfilment.selectWorkOrderFulfilment("open");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_edit,10);
            bEditButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrderFulfilment.button_edit);
            m_assert.assertTrue(bEditButtonFound, "Validated -->Work Order Fulfilment edit -->Edit functionality is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Edit Work Order Fulfilment policy" + e);
        }
    }
    @Test(enabled = true, description = "Validate Cancel Work Order Fulfilment inventory policy")
    public void validateCancelWorkOrderFulfilment() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_WorkOrder = new Page_WorkOrder(driver);
        workOrder = new InventoryPolicyWorkOrderTest();

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCancelPolicyComponent,sCancelPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Work Order Fulfilment");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_refresh, 6);
            workOrderFulfilment.selectWorkOrderFulfilment("open");
            Cls_Generic_Methods.customWait();
            boolean bCancelButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrderFulfilment.button_cancel);
            m_assert.assertFalse(bCancelButtonFound, "Validated -->Work Order Fulfilment cancellation-->Cancel functionality is disabled");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCancelPolicyComponent,sCancelPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            refreshAndOpenWorkOrderFulfilment();
            workOrderFulfilment.selectWorkOrderFulfilment("open");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_cancel,10);
            bCancelButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrderFulfilment.button_cancel);
            m_assert.assertTrue(bCancelButtonFound, "Validated -->Work Order Fulfilment cancellation-->Cancel functionality is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Cancel Work Order Fulfilment policy" + e);
        }
    }
    @Test(enabled = true, description = "Validate Edit Transaction Date And Time Work Order Fulfilment inventory policy")
    public void validateEditTransactionDateAndTimeWorkOrderFulfilment() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_WorkOrder = new Page_WorkOrder(driver);
        workOrder = new InventoryPolicyWorkOrderTest();

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTxnDateTimePolicyComponent,sEditTxnDateTimePolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Work Order Fulfilment");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_refresh, 6);
            workOrderFulfilment.selectWorkOrderFulfilment("open");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_edit,10);

            boolean bTransactionDateEditable = checkTransactionDateTimeIsEditable();
            m_assert.assertFalse(bTransactionDateEditable, "Validated -->Work Order Fulfilment date & time edit access-->Transaction date & time edit functionality is disabled");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTxnDateTimePolicyComponent,sEditTxnDateTimePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            refreshAndOpenWorkOrderFulfilment();
            workOrderFulfilment.selectWorkOrderFulfilment("open");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_edit,10);
            bTransactionDateEditable = checkTransactionDateTimeIsEditable();
            m_assert.assertTrue(bTransactionDateEditable, "Validated -->Work Order Fulfilment date & time edit access-->Transaction date & time edit functionality is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Transaction date & time edit Work Order Fulfilment policy" + e);
        }
    }

    private String getRandomNumber() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    public void createWorkOrderFulfilmentWithMandatoryFields() {
        oPage_WorkOrder = new Page_WorkOrder(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_WorkOrderFulfilment = new Page_WorkOrderFulfilment(driver);
        String billNumber = "BILL-NO-" + getRandomNumber();
        String sInstruction = "TEST";

        try {

            //WorkOrder fulfilment
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_newTransaction, 15);
            Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_newTransaction);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.text_headerCreteWorkOrderFulfilment, 15);


            //Transaction Notes
            Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrderFulfilment.input_transactionNotes_creteWorkOrderFulfilment, sInstruction);


            //Bill
            Cls_Generic_Methods.selectElementByVisibleText(oPage_WorkOrderFulfilment.select_billType_creteWorkOrderFulfilment, "Bill");
            Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrderFulfilment.input_billNo_creteWorkOrderFulfilment, billNumber);

            Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.input_billDate_creteWorkOrderFulfilment);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.input_todayBillDate, 3);

            Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.input_todayBillDate);


            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.button_saveChanges), "Clicked <b>Save Changes</b>");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_refresh, 10);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFalse("Unable to validate Create Work Order Fulfilment --> " + e);
        }
    }
    private void refreshAndOpenWorkOrderFulfilment() {
        try {
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Work Order Fulfilment");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_refresh, 10);
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to refresh");
        }
    }
    public boolean checkTransactionDateTimeIsEditable() {
        boolean flag = false;


        try {
            Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.button_edit);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.input_woDate_creteWorkOrderFulfilment, 10);
            try {
                String readOnlyValueTime = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrderFulfilment.input_woTime_creteWorkOrderFulfilment, "readonly");
                String readOnlyValueDate = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrderFulfilment.input_woDate_creteWorkOrderFulfilment, "readonly");
                if (!readOnlyValueDate.isEmpty() && !readOnlyValueTime.isEmpty()) {
                    flag = false;
                }
            } catch (NullPointerException e) {
                flag = true;
            }
            Cls_Generic_Methods.clickElementByJS(driver, oPage_WorkOrderFulfilment.button_closeCreateWO);

        } catch (Exception e) {
            m_assert.assertFatal("Unable to check transaction time editable status " + e);
        }
        return flag;
    }

}
