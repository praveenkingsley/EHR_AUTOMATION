package tests.authorizationpolicy.inventory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.store.OpticalStore.Page_SalesOrder;
import pages.store.PharmacyStore.Items.Page_Master;

import java.util.List;

public class InventorySaleOrderPolicyTest extends TestBase {

    String sPolicyRequired = "INVENTORY";
    String sViewPolicyComponent = "VIEW (SALES ORDER)";
    String sAddPolicyComponent = "ADD (SALES ORDER)";
    String sCancelOrderPolicyComponent = "CANCEL ORDER (SALES ORDER)";
    String sCreateBillPolicyComponent = "CREATE BILL (SALES ORDER)";
    String sEditPaymentDateTimePolicyComponent = "EDIT PAYMENT DATE & TIME (SALES ORDER)";
    String sEditOrderDateTimePolicyComponent = "EDIT ORDER DATE & TIME (SALES ORDER)";
    String sCreateRequisitionPolicyComponent = "CREATE REQUISITION (SALES ORDER)";
    String sViewPolicyDescription = "Inventory Sales Order View Access";
    String sAddPolicyDescription = "Inventory Sales Order Creation Access";
    String sCancelOrderPolicyDescription = "Inventory Sales Order Cancellation Access";
    String sCreateBillPolicyDescription = "Inventory Sales Order Bill Creation Access";
    String sEditPaymentDateTimePolicyDescription = "Inventory Sales Order Payment Date and Time Edit Access";
    String sEditOrderDateTimePolicyDescription = "Inventory Sales Order Date and Time Edit Access";
    String sCreateRequisitionPolicyDescription = "Inventory Requisition Against Sales Order Creation Access";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String sStore = "Optical Store 1- Optical";
    public static String myPatientName = "Deepak";
    public static String myMedicationName = "SalesOrderTest1";
    String sSalesOrderStatusOnUI = "Placed";

    public static String myPatientNumber = "9879879870";
    public static String sPaymentMethod = "Cash";


    @BeforeMethod
    private void init() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        try {
            //Opening Organization Settings
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectFacility("OPTHA1");
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

    @Test(enabled = true, description = "Validating Inventory policy for View Sales Order Functionality")
    public void validatingViewSalesOrderInventoryPolicyFunctionality() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        try {
            // Disabling  Sales Order Policy For User
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy
            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            boolean bSonPresentInTransaction = CommonActions.selectOptionFromLeftInInventoryStorePanel(
                    "Order", "Sales Order");

            m_assert.assertFalse(bSonPresentInTransaction,
                    " <b> Sales Order </b> Feature is not present in Order as policy is <b> Disabled </b> for user");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();

            // Enabling Sales Order Policy For User
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            bSonPresentInTransaction = CommonActions.selectOptionFromLeftInInventoryStorePanel(
                    "Order", "Sales Order");

            m_assert.assertTrue(bSonPresentInTransaction, " <b> Sales Order </b> Feature is present in Transaction as policy is <b> Enabled </b> for user");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validating Inventory policy for Add Sales Order Functionality")
    public void validatingAddSalesOrderInventoryPolicyFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        Page_Master oPage_Master = new Page_Master(driver);

        try {

            // Disabling Sales Order Policy For User

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bAddButtonNotFound = false;
            if (!Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.button_addNewButtonInOrder)) {
                bAddButtonNotFound = true;
                m_assert.assertTrue(bAddButtonNotFound,
                        " <b> Add Button </b> is not present in Sales Order as Policy is <b> Disabled </b> for user");
            }else{
                m_assert.assertTrue(bAddButtonNotFound, " Add Button is present in Sales Order , Either Policy is not disabled or not working");
            }

            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bAddButtonFound = false;
            if (Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.button_addNewButtonInOrder)) {
                bAddButtonFound = true;
                m_assert.assertTrue(bAddButtonFound,
                        " <b> Add Button </b> is present in Sales Order as Policy is <b> Enabled </b> for user");
            }else{
                m_assert.assertTrue(bAddButtonFound, " Add Button is not present in Sales Order , Either Policy is not Enabled or not working");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Inventory policy for Cancel Order In Sales Order Functionality")
    public void validatingCancelOrderInSalesOrderInventoryPolicyFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        Page_Master oPage_Master = new Page_Master(driver);

        try {

            // Disabling Sales Order Policy For User

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCancelOrderPolicyComponent, sCancelOrderPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            // creating order for cancel order button check
            boolean bCreateOrderForPolicy = createSaleOrderForPolicyCheck("Stockable");
            Cls_Generic_Methods.customWait();

            if(bCreateOrderForPolicy) {

                boolean bSalesOrderRecordFound = selectRecordFromListInInventoryStores(oPage_SalesOrder.list_salesOrderTableItemDataList,sSalesOrderStatusOnUI);
                m_assert.assertTrue(bSalesOrderRecordFound, "Sales Order Records Found and Clicked");

                if (bSalesOrderRecordFound) {

                    boolean bCancelOrderButtonNotFound = false;

                    if (!Cls_Generic_Methods.isElementClickable(driver, oPage_SalesOrder.button_cancelOrderInSalesOrder)) {

                        bCancelOrderButtonNotFound = true;
                        m_assert.assertTrue(bCancelOrderButtonNotFound,
                                "<b> Cancel Order Button </b> is not present in Sales Order as Policy is <b> Disabled </b> for user");

                    } else {
                        m_assert.assertTrue(bCancelOrderButtonNotFound, "Cancel Order Button is present in Sales Order , Either Policy is not disabled or not working");
                    }
                } else {
                    m_assert.assertTrue(bSalesOrderRecordFound, " Sales Order Records Not Present in the list , please create one");
                }

                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.switchToOtherTab();

                // Enabling Policy
                CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCancelOrderPolicyComponent, sCancelOrderPolicyDescription);
                Cls_Generic_Methods.customWait(3);

                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

                bSalesOrderRecordFound =selectRecordFromListInInventoryStores(oPage_SalesOrder.list_salesOrderTableItemDataList,sSalesOrderStatusOnUI);
                m_assert.assertTrue(bSalesOrderRecordFound, "Sales Order Records Found and Clicked");

                if (bSalesOrderRecordFound) {

                    boolean bCancelOrderButtonFound = false;

                    if (Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.button_cancelOrderInSalesOrder)) {

                        bCancelOrderButtonFound = true;
                        m_assert.assertTrue(bCancelOrderButtonFound,
                                "<b> Cancel Order Button </b>  is present in Sales Order as Policy is <b> Enabled <b> for user");

                    } else {
                        m_assert.assertTrue(bCancelOrderButtonFound, "Cancel Order Button is not present in Sales Order , Either Policy is not disabled or not working");
                    }
                } else {
                    m_assert.assertTrue(bSalesOrderRecordFound, " Sales Order Records Not Present in the list , please create one");
                }

            }else {
                m_assert.assertTrue(bCreateOrderForPolicy, " Sales Order Records Not Created in the list");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }
    
    @Test(enabled = true, description = "Validating Inventory policy for Create Bill Sales Order Functionality")
    public void validatingCreateBillInSalesOrderInventoryPolicyFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        Page_Master oPage_Master = new Page_Master(driver);

        try {

            // Disabling Create Bill Sales Order Policy For User

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCreateBillPolicyComponent, sCreateBillPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bSalesOrderRecordFound = selectRecordFromListInInventoryStores(oPage_SalesOrder.list_salesOrderTableItemDataList,sSalesOrderStatusOnUI);
            m_assert.assertTrue(bSalesOrderRecordFound, "Sales Order Records Found and Clicked");

            if(bSalesOrderRecordFound) {

                boolean bCreateBillButtonNotFound = false;

                if (!Cls_Generic_Methods.isElementClickable(driver ,oPage_SalesOrder.button_CreateBill)) {

                    bCreateBillButtonNotFound = true;
                    m_assert.assertTrue(bCreateBillButtonNotFound,
                            "<b> Create Bill Button </b> is not present in Sales Order as Policy is <b> Disabled </b> for user");

                } else {
                    m_assert.assertTrue(bCreateBillButtonNotFound, "Create Bill Button is present in Sales Order , Either Policy is not disabled or not working");
                }
            }else{
                m_assert.assertTrue(bSalesOrderRecordFound," Sales Order Records Not Present in the list , please create one");
            }

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();

            // Enabling Create Bill Policy

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCreateBillPolicyComponent, sCreateBillPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            bSalesOrderRecordFound = selectRecordFromListInInventoryStores(oPage_SalesOrder.list_salesOrderTableItemDataList,sSalesOrderStatusOnUI);
            m_assert.assertTrue(bSalesOrderRecordFound, "Sales Order Records Found and Clicked");

            if(bSalesOrderRecordFound) {

                boolean bCreateBillButtonFound = false;

                if (Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.button_CreateBill)) {

                    bCreateBillButtonFound = true;
                    m_assert.assertTrue(bCreateBillButtonFound,
                            " <b >Create Bill Button </b> is present in Sales Order as Policy is <b> Enabled </b> for user");

                } else {
                    m_assert.assertTrue(bCreateBillButtonFound, "Create Bill Button is not present in Sales Order , Either Policy is  disabled or not working");
                }
            }else{
                m_assert.assertTrue(bSalesOrderRecordFound," Sales Order Records Not Present in the list , please create one");
            }

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Inventory policy for Edit Payment Date & Time In Sales Order Functionality")
    public void validatingEditPaymentDateTimeSalesOrderInventoryPolicyFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        Page_Master oPage_Master = new Page_Master(driver);

        try {

            // Disabling Payment Date And Time Policy For User
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPaymentDateTimePolicyComponent, sEditPaymentDateTimePolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bEditPaymentDateTimeNotFoundInCreateBillTemplate = false;

            boolean bSalesOrderRecordFound = selectRecordFromListInInventoryStores(oPage_SalesOrder.list_salesOrderTableItemDataList,sSalesOrderStatusOnUI);
            m_assert.assertTrue(bSalesOrderRecordFound, "Sales Order Records Found and Clicked");

            if(bSalesOrderRecordFound) {

                Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_CreateBill);
                Cls_Generic_Methods.customWait(5);

                if (Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.input_paymentDateInCreateBill, "readonly") != null &&
                        Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.input_paymentTimeInCreateBill, "readonly") != null){
                    bEditPaymentDateTimeNotFoundInCreateBillTemplate = true;
                }
                m_assert.assertTrue(bEditPaymentDateTimeNotFoundInCreateBillTemplate,
                        "<b> Payment Date and Time Field is Disabled in Create Bill as Policy is Disabled for user for sales order </b>");

            }else {
                m_assert.assertTrue(bSalesOrderRecordFound, " Sales Order Records Not present in Sales Order Page , please add new");
            }

            Cls_Generic_Methods.clickElementByJS(driver,oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.customWait();

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPaymentDateTimePolicyComponent, sEditPaymentDateTimePolicyDescription);
            Cls_Generic_Methods.customWait(3);

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bEditPaymentDateTimeFoundInCreateBillTemplate = false;


            bSalesOrderRecordFound = selectRecordFromListInInventoryStores(oPage_SalesOrder.list_salesOrderTableItemDataList,sSalesOrderStatusOnUI);
            m_assert.assertTrue(bSalesOrderRecordFound, "Sales Order Records Found and Clicked");

            if(bSalesOrderRecordFound) {

                Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_CreateBill);
                Cls_Generic_Methods.customWait(5);

                if (Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.input_paymentDateInCreateBill, "readonly") == null &&
                        Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.input_paymentTimeInCreateBill, "readonly") == null){
                    bEditPaymentDateTimeFoundInCreateBillTemplate = true;
                    Cls_Generic_Methods.clickElementByJS(driver,oPage_CommonElements.button_closeTemplateWithoutSaving);
                    Cls_Generic_Methods.customWait(4);
                }
                m_assert.assertTrue(bEditPaymentDateTimeFoundInCreateBillTemplate,
                        "<b> Payment Date and Time Field is Enabled in Create Bill as Policy is Enabled for user for sales order </b>");

            }else {
                m_assert.assertTrue(bSalesOrderRecordFound, " Sales Order Records Not present in Sales Order Page , please add new");
            }

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Inventory policy for Edit Order Date & Time In Sales Order Functionality")
    public void validatingEditOrderDateTimeSalesOrderInventoryPolicyFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        Page_Master oPage_Master = new Page_Master(driver);

        try {

            // Disabling Payment Date And Time Policy For User
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditOrderDateTimePolicyComponent, sEditOrderDateTimePolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            // Searching for patient
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addNewButtonInOrder), "Sales Order New Button Clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.list_PatientSearch, 16);
            Cls_Generic_Methods.selectElementByVisibleText(oPage_SalesOrder.list_PatientSearch, "Name");
            Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_patientdetails, myPatientName);
            Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_Search);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.selectPatient, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.selectPatient), "Selected patient for Sales Order");
            Cls_Generic_Methods.customWait(5);


            boolean bEditOrderDateTimeNotFoundInCreateOrderTemplate = false;

            if (Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_TxnDate, "readonly") != null &&
                    Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderTime, "readonly") != null ){
                bEditOrderDateTimeNotFoundInCreateOrderTemplate = true;
            }
            m_assert.assertTrue(bEditOrderDateTimeNotFoundInCreateOrderTemplate,
                    "<b> Order Date and Time Field is Disabled in Sales Order as Policy is Disabled for user in New Template </b>");



            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.customWait();

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditOrderDateTimePolicyComponent, sEditOrderDateTimePolicyDescription);
            Cls_Generic_Methods.customWait(3);

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addNewButtonInOrder), "Sales Order New Button Clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.list_PatientSearch, 16);
            Cls_Generic_Methods.selectElementByVisibleText(oPage_SalesOrder.list_PatientSearch, "Name");
            Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_patientdetails, myPatientName);
            Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_Search);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.selectPatient, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.selectPatient), "Selected patient for Sales Order");
            Cls_Generic_Methods.customWait(5);


            boolean bEditOrderDateTimeFoundInCreateOrderTemplate = false;


            if (Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_TxnDate, "readonly") == null &&
                    Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderTime, "readonly") == null ){
                bEditOrderDateTimeFoundInCreateOrderTemplate = true;
            }
            m_assert.assertTrue(bEditOrderDateTimeFoundInCreateOrderTemplate,
                    "<b> Order Date and Time Field is Enabled in Sales Order as Policy is Enabled for user in New Template </b>");


            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Inventory policy for Create Requisition Sales Order Functionality")
    public void validatingCreateRequisitionSalesOrderInventoryPolicyFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        Page_Master oPage_Master = new Page_Master(driver);

        try {

            // Disabling Sales Order Policy For User

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCreateRequisitionPolicyComponent, sCreateRequisitionPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean createRequisitionRecord = createSaleOrderForPolicyCheck("Non-Stockable");

            if (createRequisitionRecord){
                boolean bCreateRequisitionButtonNotFound = false;

                boolean bSalesOrderRecordFound = selectRecordFromListInInventoryStores(oPage_SalesOrder.list_salesOrderTableItemDataList,"Fitting\n" +
                        "Supplier ABC");
                m_assert.assertTrue(bSalesOrderRecordFound, "Sales Order Records Found and Clicked");

            if (!Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.button_createRequisition)) {
                bCreateRequisitionButtonNotFound = true;
                m_assert.assertTrue(bCreateRequisitionButtonNotFound,
                        " <b> Create Requisition Button </b> is not present in Sales Order as Policy is <b> Disabled </b> for user");
            } else {
                m_assert.assertTrue(bCreateRequisitionButtonNotFound, " Create Requisition Button is present in Sales Order , Either Policy is not disabled or not working");
            }

            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCreateRequisitionPolicyComponent, sCreateRequisitionPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            bSalesOrderRecordFound = selectRecordFromListInInventoryStores(oPage_SalesOrder.list_salesOrderTableItemDataList,"Fitting\n" +
                    "Supplier ABC");
                m_assert.assertTrue(bSalesOrderRecordFound, "Sales Order Records Found and Clicked");

            boolean bCreateRequisitionButtonFound = false;
            if (Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.button_createRequisition)) {
                bCreateRequisitionButtonFound = true;
                m_assert.assertTrue(bCreateRequisitionButtonFound,
                        " <b> Create Requisition Button </b> is present in Sales Order as Policy is <b> Enabled </b> for user");
            } else {
                m_assert.assertTrue(bCreateRequisitionButtonFound, " Create Requisition Button is not present in Sales Order , Either Policy is not Enabled or not working");
            }

        }else{
                m_assert.assertFalse("Requisition record not created");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    public static boolean createSaleOrderForPolicyCheck(String itemFrom){

        boolean createSalesOrder = false ;
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        String sMedicationQty = "1";
        String sPatientSalutation = "Mr.";


        try{

             m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addNewButtonInOrder), "Sales Order New Button Clicked");
             Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.list_PatientSearch, 5);
             Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.button_addNewPatient);
             Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_orderTime,10);

                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                oPage_NewPatientRegisteration.select_salutationForPatient, sPatientSalutation),
                        "Salutation for Patient is selected as - " + sPatientSalutation);

               m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatientName),
                    "First Name is entered as - " + myPatientName);

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                            myPatientNumber),
                    "Mobile Number is entered as - " + myPatientNumber);

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm),
                    " Save Button Clicked in Patient Register Form");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_TxnDate,10);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_searchMedicineNameInDescription, myMedicationName), "Entering the medication name as" + myMedicationName + " in description textbox for sales order");
            Cls_Generic_Methods.pressEnter();
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult, 12);
            boolean myMedicationFound = false;
            Cls_Generic_Methods.customWait(5);

            if(itemFrom.equalsIgnoreCase("Non-Stockable"))
            {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_nonStockable),
                        "Non Stockable Button Clicked");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.textbox_vendorNameDropdown);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_FirstVendorNameInVendorList,2);
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.text_FirstVendorNameInVendorList);
                Cls_Generic_Methods.customWait();

                for (WebElement eMedication : oPage_SalesOrder.list_rawOfNonStockableItemOnLeftInSearchResult) {
                    if (Cls_Generic_Methods.isElementDisplayed(eMedication)) {
                            Cls_Generic_Methods.clickElement(eMedication);
                            Cls_Generic_Methods.customWait(5);
                            myMedicationFound = true;
                            break;
                    }
                }

                Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.textbox_mrpValue,"100");
                m_assert.assertTrue(myMedicationFound, "Non Stockable Item Found and clicked");


            } else {

                for (WebElement eMedication : oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult) {
                    if (Cls_Generic_Methods.isElementDisplayed(eMedication)) {
                        if (Cls_Generic_Methods.getTextInElement(eMedication).contains(myMedicationName)) {
                            Cls_Generic_Methods.clickElement(eMedication);
                            Cls_Generic_Methods.customWait(3);
                            myMedicationFound = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(myMedicationFound, "Required medication " + myMedicationName + " found for Sales Order");
            }

            m_assert.assertTrue(myMedicationFound, "Required medication " + myMedicationName + " found for Sales Order");


            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_quantityOfMedicine, sMedicationQty),
                    "Entered Quantity " + sMedicationQty + " for Sales Order");
            Cls_Generic_Methods.customWait(5);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_SaveChanges)," Save Changes Button CLicked In Sales Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_AdvancePaid, 15);
            if(Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.button_closeModalOfSalesOrder)) {
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_closeModalOfSalesOrder);
                Cls_Generic_Methods.customWait();
                createSalesOrder = true ;
            }

         m_assert.assertTrue(createSalesOrder, " Sale Order Created Successfully");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

       return createSalesOrder ;
    }

    public static boolean selectRecordFromListInInventoryStores(List<WebElement> listOfRecords, String expectedStatus) {

        boolean bStatusFound = false;

        try {
            for (WebElement row : listOfRecords) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(3));
                    if (purchaseStatus.equalsIgnoreCase(expectedStatus)) {
                        bStatusFound = true;
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(row), " Record Clicked In Record List");
                        Cls_Generic_Methods.customWait(3);
                        break;
                    }

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
        return bStatusFound;

    }



}
