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
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ROLRules;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Order.Page_PurchaseOrder;
import pages.store.PharmacyStore.Order.Page_Requisition;
import pages.store.PharmacyStore.Order.Page_RequisitionReceived;
import pages.store.PharmacyStore.Page_Vendors;


public class InventoryRequisitionPolicyTest extends TestBase {

    String sPolicyRequired = "INVENTORY";
    String sAddPolicyComponent = "ADD (REQUISITION)";
    String sViewPolicyComponent = "VIEW (REQUISITION)";
    String sApprovePolicyComponent = "APPROVE (REQUISITION)";
    String sEditPolicyComponent = "EDIT (REQUISITION)";
    String sCancelPolicyComponent = "CANCEL (REQUISITION)";
    String sEditDateTimePolicyComponent = "EDIT ORDER DATE & TIME (REQUISITION)";

    String sAddPolicyDescription = "Inventory Requisition Creation Access";
   String sViewPolicyDescription = "Inventory Requisition View Access";
    String sApprovePolicyDescription = "Inventory Requisition Approve Access";
    String sEditPolicyDescription = "Inventory Requisition Edit Access";
    String sCancelPolicyDescription = "Inventory Requisition Cancellation Access";
    String sEditDateTimePolicyDescription = "Inventory Requisition Date and Time Edit Access";

    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String sStore = "Pharmacy automation- Pharmacy";
    String sStore1 = "pharmacy viet- Pharmacy";
    String filterByText = "This Month";

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
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

        } catch (Exception e) {
            m_assert.assertFatal("Unable to open organisation setting " + e);
        }
    }
    @Test(enabled = true, description = "Policy For Requisition View")
    public void inventoryPolicyForRequisitionView() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        boolean bRequisitionViewInOrder = true;

        try {

            //disable req policy for view
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            bRequisitionViewInOrder = CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
            m_assert.assertFalse(bRequisitionViewInOrder, "<b> Requisition Can't be viewed from order. It's Disabled </b>");
            Cls_Generic_Methods.customWait(6);

            //enabling for PO View
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            bRequisitionViewInOrder = CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
            m_assert.assertTrue(bRequisitionViewInOrder, "<b> Requisition Can be viewed from order. It's enabled </b>");

            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }
    @Test(enabled = true, description = "Policy For Requisition Add")
    public void inventoryPolicyForRequisitionAdd() {
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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
            Cls_Generic_Methods.customWait(6);
            boolean bAddButtonNotFound = false;
            if (!Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_addNew)) {
                bAddButtonNotFound = true;
            }
            m_assert.assertTrue(bAddButtonNotFound, "<b> Add button in Requisition is disabled </b>");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
            Cls_Generic_Methods.customWait(6);
            boolean bAddButtonFound = false;
            if (Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_addNew)) {
                bAddButtonFound = true;
            }
            m_assert.assertTrue(bAddButtonFound, "<b> Add button in Requisition is  Enabled </b> ");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }
    @Test(enabled = true, description = "Policy For Requisition Approve")
    public void inventoryPolicyForRequisitionApproval() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        Page_Requisition oPage_Requisition = new Page_Requisition(driver);
        boolean bRequisitionFound = false;
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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
             Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
             Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_downArrowForToday);
             CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterByText);
            Cls_Generic_Methods.customWait();

            bRequisitionFound = getStatusOfRequisition();
            if (!bRequisitionFound) {
                 createRequisition();
                bRequisitionFound = getStatusOfRequisition();
            }
                if (bRequisitionFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 5);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_viewOrderRequisition),
                            "View Order clicked for requisition");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_approveRequisition, 15);
                   if(Cls_Generic_Methods.getElementAttribute(oPage_Requisition.button_approveRequisition,"data-confirm") == null){
                       bApproveButtonNotFound = true;
                   }
                    m_assert.assertTrue(bApproveButtonNotFound, "<b> Approve button in Requisition is disabled </b> ");
                } else {
                    m_assert.assertTrue(bRequisitionFound, "Requisition not present, create one");
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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterByText);
            Cls_Generic_Methods.customWait();

            bRequisitionFound = getStatusOfRequisition();
            if (bRequisitionFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_viewOrderRequisition),
                        "View Order clicked for requisition");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_approveRequisition, 15);
                if(Cls_Generic_Methods.isElementDisplayed(oPage_Requisition.button_approveRequisition)){
                    bApproveButtonFound = true;
                }
                m_assert.assertTrue(bApproveButtonFound, "<b> Approve button in Requisition is enabled </b> ");
            } else {
                m_assert.assertTrue(bRequisitionFound, "Requisition not present, create one");
            }

            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Policy For Requisition Edit")
    public void inventoryPolicyForRequisitionEdit() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        Page_Requisition oPage_Requisition = new Page_Requisition(driver);
        boolean bRequisitionFound = false;
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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterByText);
            Cls_Generic_Methods.customWait();

           bRequisitionFound = getStatusOfRequisition();
            if (bRequisitionFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_viewOrderRequisition),
                        "View Order clicked for requisition");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_approveRequisition, 15);
                if(!Cls_Generic_Methods.isElementDisplayed(oPage_Requisition.button_editRequisition)){
                    bEditButtonNotFound = true;
                }
                m_assert.assertTrue(bEditButtonNotFound, "<b> Edit button in Requisition is disabled </b> ");
            }

            //enabling for PO approve
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent, sEditPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterByText);
            Cls_Generic_Methods.customWait();

            bRequisitionFound = getStatusOfRequisition();
            if (bRequisitionFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_viewOrderRequisition),
                        "View Order clicked for requisition");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_approveRequisition, 15);
                if(Cls_Generic_Methods.isElementDisplayed(oPage_Requisition.button_editRequisition)){
                    bEditButtonFound = true;
                }
                m_assert.assertTrue(bEditButtonFound, "<b> Edit button in Requisition is enabled </b> ");
            }
            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }
    @Test(enabled = true, description = "Policy For Requisition Cancel")
    public void inventoryPolicyForRequisitionCancel() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        Page_Requisition oPage_Requisition = new Page_Requisition(driver);
        boolean bRequisitionFound = false;
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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterByText);
            Cls_Generic_Methods.customWait();

           bRequisitionFound = getStatusOfRequisition();
            if (bRequisitionFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_viewOrderRequisition),
                        "View Order clicked for requisition");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_approveRequisition, 15);
                if(!Cls_Generic_Methods.getElementAttribute(oPage_Requisition.button_cancelRequisition,"class").contains("danger")){
                    bCancelButtonNotFound = true;
                }
                m_assert.assertTrue(bCancelButtonNotFound, "<b> Cancel button in Requisition is disabled </b> ");
            }

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCancelPolicyComponent, sCancelPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterByText);
            Cls_Generic_Methods.customWait();

           bRequisitionFound = getStatusOfRequisition();
            if (bRequisitionFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_viewOrderRequisition),
                        "View Order clicked for requisition");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_approveRequisition, 15);
                if(Cls_Generic_Methods.isElementDisplayed(oPage_Requisition.button_cancelRequisition)){
                    bCancelButtonFound = true;
                }
                m_assert.assertTrue(bCancelButtonFound, "<b> Cancel button in Requisition is enabled </b> ");
            }
            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }
    @Test(enabled = true, description = "Policy For Requisition Edit date and time")
    public void inventoryPolicyForRequisitionEditDateTime() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        Page_Requisition oPage_Requisition = new Page_Requisition(driver);
        boolean bRequisitionFound = false;
        boolean bEditDateTimeDisabled1 = false;
        boolean bEditDateTimeDisabled2 = false;
        boolean bEditDateTimeEnabled1 = false;
        boolean bEditDateTimeEnabled2 = false;
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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);

            //verifying in new
            Cls_Generic_Methods.clickElement(oPage_Requisition.button_newRequisition);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.select_receivingStore,6);
            if(Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderDate,"readonly") != null &&
                    Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderTime, "readonly") != null) {
                bEditDateTimeDisabled1 = true;
            }
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
            Cls_Generic_Methods.customWait(6);

            //verifying from requisition created
            Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterByText);
            Cls_Generic_Methods.customWait();

            bRequisitionFound = getStatusOfRequisition();
            if (bRequisitionFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_viewOrderRequisition),
                        "View Order clicked for requisition");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_editRequisition, 15);
                Cls_Generic_Methods.clickElement(oPage_Requisition.button_editRequisition);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.input_requisitionOrderDate,10);
                if(Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderDate,"readonly") != null &&
                        Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderTime, "readonly") != null) {
                    bEditDateTimeDisabled2 = true;
                }
                if(bEditDateTimeDisabled1 && bEditDateTimeDisabled2)
                m_assert.assertTrue(true, "<b> Edit Requisition Order Date and time button is disabled </b> ");
            }else{
                m_assert.assertTrue(false, "<b> Edit Requisition Order Date and time policy is not working </b> ");

            }

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditDateTimePolicyComponent, sEditDateTimePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving,5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);

            //verifying in new
            Cls_Generic_Methods.clickElement(oPage_Requisition.button_newRequisition);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.select_receivingStore,6);
            if(Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderDate,"readonly") == null &&
                    Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderTime, "readonly") == null) {
                bEditDateTimeEnabled1 = true;
            }
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterByText);
            Cls_Generic_Methods.customWait();

            bRequisitionFound = getStatusOfRequisition();
            if (bRequisitionFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_viewOrderRequisition),
                        "View Order clicked for requisition");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_editRequisition, 15);
                Cls_Generic_Methods.clickElement(oPage_Requisition.button_editRequisition);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.input_requisitionOrderDate,10);
                if(Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderDate,"readonly") == null &&
                        Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderTime, "readonly") == null) {
                    bEditDateTimeEnabled2 = true;
                }
                if(bEditDateTimeEnabled1 && bEditDateTimeEnabled2)
                    m_assert.assertTrue(true, "<b> Edit Requisition Order Date and time button is enabled </b> ");
            }else{
                m_assert.assertTrue(false, "<b> Edit Requisition Order Date and time policy enable is not working </b> ");

            }

            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    public static boolean getStatusOfRequisition(String... status) {
        boolean bRequisitionFound = false;
        Page_Requisition oPage_Requisition = new Page_Requisition(driver);
        String sStatus = "Open";
        try {
            if (status.length > 0) {
                sStatus = status[0];
            }
            for (WebElement eStatus : oPage_Requisition.list_statusOfRequisition) {
                if (Cls_Generic_Methods.getTextInElement(eStatus).contains(sStatus)) {
                    bRequisitionFound = true;
                    Cls_Generic_Methods.clickElement(eStatus);
                    Cls_Generic_Methods.customWait(5);
                    break;
                }
            }
            if (bRequisitionFound) {
                m_assert.assertTrue(bRequisitionFound, "Requisition Found with status: " + sStatus);
            } else {
                m_assert.assertFalse(bRequisitionFound, "Requisition not Found with status: " + sStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to find requisition " + e);
        }
        return bRequisitionFound;
    }
    public static boolean createRequisition() {

        Page_Requisition oPage_Requisition = new Page_Requisition(driver);
        Page_ROLRules oPage_ROLRules = new Page_ROLRules(driver);
        String sCENTRAL_HUB = "CENTRAL HUB 01";
        String sRequisitionType = "Normal";
        String sROL_ITEM = "testDemoMed";
        String sReqQuantity = "10";
        String reqDateAndTime = "";
        String requisitionOrderTime = "";
        String requisitionOrderDate = "";
        boolean bRequisitionOrderFound = false;

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_newRequisition, 3);

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_newRequisition),
                    "New Button clicked in Order Requisition");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.dropdown_receivingStoreInRequisition, 3);
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Requisition.select_receivingStore, sCENTRAL_HUB),
                    "Receiving store selected: <b>" + sCENTRAL_HUB + "</b>");

            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Requisition.select_reqType, sRequisitionType),
                    "Requisition Type selected: <b>" + sRequisitionType + "</b>");


           // find rol item from list table
            Cls_Generic_Methods.clickElement(oPage_Requisition.input_searchMedicineName);
            Cls_Generic_Methods.sendKeysByAction(oPage_Requisition.input_searchMedicineName, sROL_ITEM);
            Cls_Generic_Methods.pressEnter();
            Cls_Generic_Methods.customWait();
            for (WebElement eItemName : oPage_Requisition.list_itemNameInPurchaseStore) {
                if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(sROL_ITEM)) {
                    Cls_Generic_Methods.clickElement(eItemName);
                    break;
                }
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ROLRules.input_quantityRequisition, 10);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_ROLRules.input_quantityRequisition,sReqQuantity);
            //getting rol_date and rol_time value
            requisitionOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderTime, "value");
            requisitionOrderTime = requisitionOrderTime.replaceAll("\\s+", "");
            requisitionOrderTime = CommonActions.getRequiredFormattedDateTime("K:mma","hh:mma",requisitionOrderTime);
            requisitionOrderTime = requisitionOrderTime.replace("am", "AM").replace("pm","PM");

            requisitionOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderDate, "value");
            requisitionOrderDate = requisitionOrderDate.replaceAll("/", "-");

            Cls_Generic_Methods.clickElement(oPage_Requisition.button_saveRequisition);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_newRequisition,10);

            for (WebElement eDate : oPage_Requisition.list_dateTimeOfRequisition) {
                reqDateAndTime = Cls_Generic_Methods.getTextInElement(eDate).split("\\r?\\n")[0].replaceAll(" ","");
                if (reqDateAndTime.equals(requisitionOrderDate + "|" + requisitionOrderTime)) {
                    bRequisitionOrderFound = true;
                    Cls_Generic_Methods.clickElement(eDate);
                    Cls_Generic_Methods.customWait();
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bRequisitionOrderFound;

    }
}
