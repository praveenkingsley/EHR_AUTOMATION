package tests.authorizationpolicy.inventory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Order.Page_PurchaseOrder;
import pages.store.PharmacyStore.Order.Page_RequisitionReceived;


public class InventoryPurchaseOrderPolicyTest extends TestBase {

    String sPolicyRequired = "INVENTORY";
    String sAddPolicyComponent = "ADD (PURCHASE ORDER)";
    String sViewPolicyComponent = "VIEW (PURCHASE ORDER)";
    String sApprovePolicyComponent = "APPROVE (PURCHASE ORDER)";
    String sEditPolicyComponent = "EDIT (PURCHASE ORDER)";
    String sCancelPolicyComponent = "CANCEL (PURCHASE ORDER)";
    String sClosePolicyComponent = "CLOSE (PURCHASE ORDER)";
    String sEditDateTimePolicyComponent = "EDIT ORDER DATE AND TIME (PURCHASE ORDER)";
    String sNewTransactionPolicyComponent = "NEW TRANSACTION (PURCHASE ORDER)";
    String sNewTransactionPolicyDescription = "Inventory Purchase Order New Transaction/GRN Creation Access";

    String sAddPolicyDescription = "Inventory Purchase Order Creation Access";
    String sViewPolicyDescription = "Inventory Purchase Order View Access";
    String sApprovePolicyDescription = "Inventory Purchase Order Approval Access";
    String sEditPolicyDescription = "Inventory Purchase Order Edit Access";
    String sCancelPolicyDescription = "Inventory Purchase Order Cancellation Access";
    String sClosePolicyDescription = "Inventory Purchase Order Close Access";
    String sEditDateTimePolicyDescription = "Inventory Purchase order Date and Time Edit Access";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String sStore = "Pharmacy automation- Pharmacy";
    String filterPeriodType = "This Month";

    @BeforeMethod
    private void init() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);

        try {
            //Opening Organization Settings
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
            m_assert.assertFatal("Unable to open organisation setting " + e);
        }
    }

    @Test(enabled = true, description = "Policy For Purchase Order View")
    public void inventoryPolicyForPurchaseOrderView() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        boolean bPurchaseViewInOrder = true;

        try {

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            bPurchaseViewInOrder = CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            m_assert.assertFalse(bPurchaseViewInOrder, "<b> Purchase Can't be viewed from order. It's Disabled </b>");
            Cls_Generic_Methods.customWait(6);

            //enabling for PO View
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            bPurchaseViewInOrder = CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            m_assert.assertTrue(bPurchaseViewInOrder, "<b> Purchase Can be viewed from order. It's enabled </b>");

            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Policy For Purchase Order Add")
    public void inventoryPolicyForPurchaseOrderAdd() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Master oPage_Master = new Page_Master(driver);

        try {

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.customWait(6);
            boolean bAddButtonNotFound = false;
            if (!Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_addNew)) {
                bAddButtonNotFound = true;
            }
            m_assert.assertTrue(bAddButtonNotFound, "<b> Add button in Purchase Order is disabled </b>");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.customWait(6);
            boolean bAddButtonFound = false;
            if (Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_addNew)) {
                bAddButtonFound = true;
            }
            m_assert.assertTrue(bAddButtonFound, "<b> Add button in purchase order is  Enabled </b> ");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Policy For Purchase Order Approve")
    public void inventoryPolicyForPurchaseOrderApproval() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        Page_PurchaseOrder oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        boolean bPurchaseOrderFound = false;
        boolean bApproveButtonNotFound = false;
        boolean bApproveButtonFound = false;

        try {

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sApprovePolicyComponent, sApprovePolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterPeriodType);
            Cls_Generic_Methods.customWait();

            // Selecting Order From List in Purchase Order

            bPurchaseOrderFound = selectStatusOfPo();

            if (!bPurchaseOrderFound) {
                createPurchaseOrder();
                bPurchaseOrderFound = selectStatusOfPo();
            }
            if (bPurchaseOrderFound) {
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseOrder.button_approveOrder)) {
                    bApproveButtonNotFound = true;
                }
                m_assert.assertTrue(bApproveButtonNotFound, "<b> approve button in Purchase Order is disabled </b> ");
            }

            //enabling for PO approve
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sApprovePolicyComponent, sApprovePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterPeriodType);
            Cls_Generic_Methods.customWait();

            bPurchaseOrderFound = selectStatusOfPo();

            if (bPurchaseOrderFound) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseOrder.button_approveOrder)) {
                    bApproveButtonFound = true;
                }
                m_assert.assertTrue(bApproveButtonFound, "<b> Approve button in Purchase Order is enabled </b>");
            }

            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Policy For Purchase Order Cancellation")
    public void inventoryPolicyForPurchaseOrderCancel() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        Page_PurchaseOrder oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        boolean bPurchaseOrderFound = false;
        boolean bCancelButtonNotFound = false;
        boolean bCancelButtonFound = false;

        try {

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCancelPolicyComponent, sCancelPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterPeriodType);
            Cls_Generic_Methods.customWait();

            bPurchaseOrderFound = selectStatusOfPo();
            if (bPurchaseOrderFound) {
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseOrder.button_cancelOrder)) {
                    bCancelButtonNotFound = true;
                }
                m_assert.assertTrue(bCancelButtonNotFound, "<b> Cancel button in Purchase Order is disabled </b>");
            }

            //enabling for PO CANCEL
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCancelPolicyComponent, sCancelPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterPeriodType);
            Cls_Generic_Methods.customWait();

            bPurchaseOrderFound = selectStatusOfPo();
            if (bPurchaseOrderFound) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseOrder.button_cancelOrder)) {
                    bCancelButtonFound = true;
                }
                m_assert.assertTrue(bCancelButtonFound, "<b> Cancel button in Purchase Order is enabled <b> ");
            }

            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Policy For Purchase Order Edit")
    public void inventoryPolicyForPurchaseOrderEdit() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        Page_PurchaseOrder oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        boolean bPurchaseOrderFound = false;
        boolean bEditButtonNotFound = false;
        boolean bEditButtonFound = false;

        try {

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent, sEditPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterPeriodType);
            Cls_Generic_Methods.customWait();

            bPurchaseOrderFound = selectStatusOfPo();
            if (bPurchaseOrderFound) {
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseOrder.button_editOrder)) {
                    bEditButtonNotFound = true;
                }
                m_assert.assertTrue(bEditButtonNotFound, "<b> Edit button in Purchase Order is disabled </b>");
            }

            //enabling for PO CANCEL
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent, sEditPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterPeriodType);
            Cls_Generic_Methods.customWait();

            bPurchaseOrderFound = selectStatusOfPo();
            if (bPurchaseOrderFound) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseOrder.button_editOrder)) {
                    bEditButtonFound = true;
                }
                m_assert.assertTrue(bEditButtonFound, "<b> Edit button in Purchase Order is enabled <b> ");
            }
            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Policy For Purchase Order Edit Order Date & Time")
    public void inventoryPolicyForPurchaseOrderEditOrderDateAndTime() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        Page_PurchaseOrder oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        boolean bPurchaseOrderFound = false;
        boolean bEditDateTimeDisabled = false;
        boolean bEditDateTimeEnabled = false;

        try {

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditDateTimePolicyComponent, sEditDateTimePolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterPeriodType);
            Cls_Generic_Methods.customWait();

            bPurchaseOrderFound = selectStatusOfPo();
            if (bPurchaseOrderFound) {
                Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_editOrder);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_purchaseOrderDate, 5);

                if (Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_purchaseOrderDate, "readonly") != null &&
                        Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_purchaseOrderTime, "readonly") != null) {
                    bEditDateTimeDisabled = true;
                }
                m_assert.assertTrue(bEditDateTimeDisabled, "<b> Edit date and time button in Purchase Order is disabled </b>");
            }

            //enabling for PO CANCEL
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditDateTimePolicyComponent, sEditDateTimePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterPeriodType);
            Cls_Generic_Methods.customWait();

            bPurchaseOrderFound = selectStatusOfPo();
            if (bPurchaseOrderFound) {
                Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_editOrder);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_purchaseOrderDate, 5);

                if (Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_purchaseOrderDate, "readonly") == null &&
                        Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_purchaseOrderTime, "readonly") == null) {
                    bEditDateTimeEnabled = true;
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_approveOrder, 9);
                    Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_approveOrder);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_confirmCancelApproveOrder, 7);
                    Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_confirmCancelApproveOrder);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_closeOrder, 7);
                }
                m_assert.assertTrue(bEditDateTimeEnabled, "<b> Edit date and time button in Purchase Order is enabled </b>");
                Cls_Generic_Methods.customWait(6);

            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Policy For Purchase Order Close")
    public void inventoryPolicyForPurchaseOrderClose() {
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        Page_PurchaseOrder oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        boolean bPurchaseOrderFound = false;
        boolean bCloseButtonNotFound = false;
        boolean bCloseButtonFound = false;

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sClosePolicyComponent, sClosePolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterPeriodType);
            Cls_Generic_Methods.customWait();

            bPurchaseOrderFound = selectStatusOfPo("approved");

            if (bPurchaseOrderFound) {
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseOrder.button_closeOrder)) {
                    bCloseButtonNotFound = true;
                }
                m_assert.assertTrue(bCloseButtonNotFound, "<b> Close button in Purchase Order is disabled </b>");
            }

            //enabling for PO CANCEL
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sClosePolicyComponent, sClosePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.customWait(2);
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterPeriodType);
            Cls_Generic_Methods.customWait();

            bPurchaseOrderFound = selectStatusOfPo("approved");
            if (bPurchaseOrderFound) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseOrder.button_closeOrder)) {
                    bCloseButtonFound = true;
                }
                m_assert.assertTrue(bCloseButtonFound, "<b> Close button in Purchase Order is enabled <b> ");
            }

            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Policy For Inventory Purchase Order New Transaction/GRN Creation Access")
    public void inventoryPolicyForPurchaseOrderNewTransaction() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        Page_PurchaseOrder oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        boolean bPurchaseOrderFound = false;
        boolean bNewTransactionButtonNotFound = false;
        boolean bNewTransactionButtonFound = false;
        String sPoStore = "OpticalStore- Optical";

        try {

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sNewTransactionPolicyComponent, sNewTransactionPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sPoStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterPeriodType);
            Cls_Generic_Methods.customWait();

            bPurchaseOrderFound = selectStatusOfPo("approved");

            if (bPurchaseOrderFound) {
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseOrder.button_newTransactionPO)) {
                    bNewTransactionButtonNotFound = true;
                }
                m_assert.assertTrue(bNewTransactionButtonNotFound, "<b> NewTransaction button in Purchase Order is disabled </b>");
            }

            //enabling for PO CANCEL
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sNewTransactionPolicyComponent, sNewTransactionPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, sPoStore);
            Cls_Generic_Methods.customWait();

            bPurchaseOrderFound = selectStatusOfPo("approved");
            if (bPurchaseOrderFound) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseOrder.button_newTransactionPO)) {
                    bNewTransactionButtonFound = true;
                }
                m_assert.assertTrue(bNewTransactionButtonFound, "<b> NewTransaction button in Purchase Order is enabled <b> ");
            }

            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    //creating vendor to test vendor policies if any vendor is not created
    public static boolean createPurchaseOrder() {
        Page_PurchaseOrder oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        int packageQuantity = 100;
        String orderTime = "";
        String orderDate = "";
        String sPoType = "Normal";
        String sStorePO = "OpticalStore";
        String vendorName = "Supplier ABC";
        String sPurchaseOrder_Item = "automation_PO";
        double costPrice = 50;
        String purchaseDateAndTime;
        boolean bPurchaseTransactionFound = false;

        try {

            // CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 3);

            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_newOrder),
                    "New Button clicked in Order Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_purchaseOrderTime, 7);

            orderTime = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_purchaseOrderTime, "value");
            orderTime = orderTime.replaceAll("\\s+", "");
           // orderTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", orderTime);
            orderTime = orderTime.replace("am", "AM").replace("pm", "PM");
            if(orderTime.length() == 6){
                orderTime = "0"+orderTime;
            }
            m_assert.assertTrue("Purchase Order time:<b> " + orderTime + "</b>");

            orderDate = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_purchaseOrderDate, "value");
            orderDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd-MM-yyyy", orderDate);
            m_assert.assertTrue("Purchase Order date:<b> " + orderDate + "</b>");

            //select vendor, store, type
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_PurchaseOrder.dropdown_poType, sPoType),
                    "PO Type selected: <b>" + sPoType + " </b>");
            Cls_Generic_Methods.customWait(3);
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_PurchaseOrder.dropdown_store, sStorePO),
                    "Store selected: <b>" + sStorePO + " </b>");
            Cls_Generic_Methods.customWait(5);

            Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_vendorSearchPo, vendorName);
            Cls_Generic_Methods.customWait();
            for (WebElement eVendor : oPage_PurchaseOrder.list_select_vendor) {
                Cls_Generic_Methods.clickElementByJS(driver, eVendor);
                break;
            }
            Cls_Generic_Methods.customWait(5);
            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.input_searchMedicineNamePO);
            Cls_Generic_Methods.sendKeysByAction(oPage_PurchaseOrder.input_searchMedicineNamePO, sPurchaseOrder_Item);

            Cls_Generic_Methods.customWait();
            boolean bPO_Item_Found = false;

            for (WebElement eMedicineName : oPage_PurchaseOrder.list_namesOfMedicinesOnLeftInSearchResultPO) {
                String sMedicineName = Cls_Generic_Methods.getTextInElement(eMedicineName);

                if (sMedicineName.equals(sPurchaseOrder_Item)) {
                    bPO_Item_Found = true;
                    Cls_Generic_Methods.clickElement(eMedicineName);
                    break;
                }
            }

            m_assert.assertTrue(bPO_Item_Found, "Validate the PO item:<b> " + sPurchaseOrder_Item + " </b> is found & selected");

            try {
                if (bPO_Item_Found) {

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.header_addNewLot, 15);

                    Cls_Generic_Methods.clearValuesInElement(oPage_PurchaseOrder.input_unitCostWOTax);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_unitCostWOTax, String.valueOf(costPrice));

                    Cls_Generic_Methods.clearValuesInElement(oPage_PurchaseOrder.input_packageQuantity);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_packageQuantity, String.valueOf(packageQuantity));

                    Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_saveLot);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.dropdown_otherCharge, 7);

                    Cls_Generic_Methods.clickElement(driver, oPage_PurchaseOrder.button_cancelOtherCharges);
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_saveOrder);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 7);

                    for (WebElement eDate : oPage_PurchaseOrder.list_dateTimeOfPO) {
                        purchaseDateAndTime = Cls_Generic_Methods.getTextInElement(eDate).replaceAll(" ", "");

                        if (purchaseDateAndTime.contains(orderDate) &&
                                purchaseDateAndTime.contains(orderTime)) {
                            bPurchaseTransactionFound = true;
                            Cls_Generic_Methods.clickElement(eDate);
                            Cls_Generic_Methods.customWait();
                            break;
                        }
                    }
                    m_assert.assertTrue(bPurchaseTransactionFound, "Purchase Order found in purchase Transaction page");
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }
        return bPurchaseTransactionFound;
    }

    public static boolean selectStatusOfPo(String... status) {
        boolean bPoFound = false;
        Page_PurchaseOrder oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        String sStatus = "pending";
        try {
            if (status.length > 0) {
                sStatus = status[0];
            }
            for (WebElement eStatus : oPage_PurchaseOrder.list_statusOfPO) {
                System.out.println(eStatus.getText().trim());
                if (eStatus.getText().trim().equals(sStatus)) {
                    bPoFound = true;
                    Cls_Generic_Methods.clickElement(eStatus);
                    Cls_Generic_Methods.customWait(5);
                    break;
                }
            }
            if (bPoFound) {
                m_assert.assertTrue(bPoFound, "Purchase Order found with status: " + sStatus);
            } else {
                m_assert.assertFalse(bPoFound, "Purchase Order not Found with status: " + sStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to find Purchase order " + e);
        }
        return bPoFound;
    }

}
