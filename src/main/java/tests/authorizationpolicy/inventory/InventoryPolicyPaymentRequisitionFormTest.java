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
import pages.store.PharmacyStore.Transaction.Page_PaymentRequisitionForm;
import pages.store.PharmacyStore.Transaction.Page_Purchase;

import java.util.ArrayList;
import java.util.List;

public class InventoryPolicyPaymentRequisitionFormTest extends TestBase {
    String sPolicyRequired = "INVENTORY";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String vendorName = "Supplier ABC";

    static String sStore = "Pharmacy automation- Pharmacy";
    static Page_Navbar oPage_Navbar;
    static Page_OrganisationSetup oPage_OrganisationSetup;
    static Page_InventoryPolicy oPage_InventoryPolicy;
    static Page_CommonElements oPage_CommonElements;
    static Page_PaymentRequisitionForm oPage_PaymentRequisitionForm;
    static Page_Master oPage_Master;
    static Page_Purchase oPage_Purchase;

    //POLICY
    String sViewPolicyComponent = "VIEW (PAYMENT REQUISITION FORM)";
    String sAddPolicyComponent = "ADD (PAYMENT REQUISITION FORM)";
    String sApprovePolicyComponent = "APPROVE (PAYMENT REQUISITION FORM)";
    String sEditPolicyComponent = "EDIT (PAYMENT REQUISITION FORM)";
    String sCancelPolicyComponent = "CANCEL (PAYMENT REQUISITION FORM)";
    String sEditTxnDateTimePolicyComponent = "EDIT TXN DATE & TIME (PAYMENT REQUISITION FORM)";
    String sViewPolicyDescription = "Payment Requisition Form View access";
    String sAddPolicyDescription = "Payment Requisition Form Creation access";
    String sApprovePolicyDescription = "Payment Requisition Form Approval access";
    String sEditPolicyDescription = "Payment Requisition Form Edit access";
    String sCancelPolicyDescription = "Payment Requisition Form Cancel access";
    String sEditTxnDateTimePolicyDescription = "Payment Requisition Form Txn date and time Edit access";


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

    @Test(enabled = true, description = "Validate View Payment Requisition Form inventory policy")
    public void validatePolicy_ViewPaymentRequisitionForm() {
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
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            boolean paymentRequisitionFormTab = CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
            m_assert.assertTrue(!paymentRequisitionFormTab, "Validated -->" + sViewPolicyDescription + "-->Payment Requisition Form Tab is disabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            paymentRequisitionFormTab = CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
            m_assert.assertTrue(paymentRequisitionFormTab, "Validated -->" + sViewPolicyDescription + "-->Payment Requisition Form Tab is enabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate view Payment Requisition Form policy " + e);
        }

    }

    @Test(enabled = true, description = "Validate Add Payment Requisition Form policy")
    public void validatePolicy_AddPaymentRequisitionForm() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Purchase = new Page_Purchase(driver);
        oPage_PaymentRequisitionForm = new Page_PaymentRequisitionForm(driver);


        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            boolean bAddButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm);
            m_assert.assertTrue(!bAddButtonFound, "Validated -->" + sAddPolicyDescription + "-->Add functionality is disabled in Payment Requisition Form");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            bAddButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm);
            m_assert.assertTrue(bAddButtonFound, "Validated -->" + sAddPolicyDescription + "-->Add functionality is enabled in Payment Requisition Form");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate add Payment Requisition Form policy" + e);
        }
    }

    @Test(enabled = true, description = "Validate Approve Payment Requisition Form inventory policy")
    public void validatePolicy_ApprovePaymentRequisitionForm() {
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        oPage_Purchase = new Page_Purchase(driver);
        oPage_PaymentRequisitionForm = new Page_PaymentRequisitionForm(driver);
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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            try {

                boolean createPurchaseBillStatus = InventoryPolicyPurchaseBillsTest.createNewPurchaseBill(true);

                if (createPurchaseBillStatus) {
                    m_assert.assertTrue("New Purchase Bill created");
                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                            "Store pop up closed");
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
                    Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.select_vendor, 4);
                    Cls_Generic_Methods.selectElementByIndex(oPage_PaymentRequisitionForm.select_vendor, 1);
                    Cls_Generic_Methods.customWait(3);

                    for (WebElement row : oPage_PaymentRequisitionForm.row_createPaymentRequisitionForm) {
                        Cls_Generic_Methods.clickElement(row);
                        break;
                    }
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_saveChanges);
                    Cls_Generic_Methods.customWait();
                } else {
                    m_assert.assertFatal("Unable to create New Purchase Bill -Purchase Grn is not created");
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Unable to create payment requistion form" + e);
            }
            boolean createPaymentRequisitionForm = selectPaymentRequisition();


            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_edit, 5);
            if (createPaymentRequisitionForm) {
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_PaymentRequisitionForm.button_approvePaymentRequisitionForm), "Validated -->" + sApprovePolicyDescription + " --> Approve functionality is disabled in Payment Requisition form");
            }

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sApprovePolicyComponent, sApprovePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_purchaseTransactionHeaderList, 10);
            boolean clickedPaymentRequisitionForm = selectPaymentRequisition();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_approvePaymentRequisitionForm,10);
            if (clickedPaymentRequisitionForm) {
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PaymentRequisitionForm.button_approvePaymentRequisitionForm), "Validated -->" + sApprovePolicyDescription + " --> Approve functionality is enabled in Payment Requisition Form");
            } else {
                m_assert.assertFatal("Payment Requisition form is not selected from the list");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate add PaymentRequisitionForm policy" + e);
        }

    }

    @Test(enabled = true, description = "Validate Edit Payment Requisition Form inventory policy")
    public void validatePolicy_EditPaymentRequisitionForm() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Purchase = new Page_Purchase(driver);
        oPage_PaymentRequisitionForm = new Page_PaymentRequisitionForm(driver);

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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            selectPaymentRequisition();
            Cls_Generic_Methods.customWait();
            boolean bEditButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_PaymentRequisitionForm.button_edit);
            m_assert.assertTrue(!bEditButtonFound, "Validated -->" + sEditPolicyDescription + "-->Edit functionality is disabled in Payment Requisition Form");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent,sEditPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            Cls_Generic_Methods.customWait();
            selectPaymentRequisition();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_edit,10);
            bEditButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_PaymentRequisitionForm.button_edit);
            m_assert.assertTrue(bEditButtonFound, "Validated -->" + sEditPolicyDescription + "-->Edit functionality is enabled in Payment Requisition Form");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Edit Payment Requisition Form policy" + e);
        }
    }

    @Test(enabled = true, description = "Validate Cancel Payment Requisition Form inventory policy")
    public void validatePolicy_CancelPaymentRequisitionForm() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Purchase = new Page_Purchase(driver);
        oPage_PaymentRequisitionForm = new Page_PaymentRequisitionForm(driver);

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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            selectPaymentRequisition();
            Cls_Generic_Methods.customWait();
            boolean bCancelButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_PaymentRequisitionForm.button_cancelPaymentRequisition);
            m_assert.assertTrue(!bCancelButtonFound, "Validated -->" + sCancelPolicyDescription + "-->Cancel functionality is disabled in Payment Requisition Form");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCancelPolicyComponent,sCancelPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            Cls_Generic_Methods.customWait();
            selectPaymentRequisition();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_cancelPaymentRequisition,10);
            bCancelButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_PaymentRequisitionForm.button_cancelPaymentRequisition);
            m_assert.assertTrue(bCancelButtonFound, "Validated -->" + sCancelPolicyDescription + "-->Cancel functionality is enabled in Payment Requisition Form");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Cancel Payment Requisition Form policy" + e);
        }
    }

    @Test(enabled = true, description = "Validate Edit Transaction Date And Time Payment Requisition Form inventory policy")
    public void validatePolicy_EditTransactionDateAndTimePaymentRequisitionForm() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Purchase = new Page_Purchase(driver);
        oPage_PaymentRequisitionForm = new Page_PaymentRequisitionForm(driver);

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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            selectPaymentRequisition();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_edit,20);
            boolean bDateAndTimeEditable = checkDateTimeIsEditable();
            m_assert.assertTrue(!bDateAndTimeEditable, "Validated -->" + sEditTxnDateTimePolicyDescription + "-->Transaction date & time edit functionality is disabled in Payment Requisition Form");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTxnDateTimePolicyComponent,sEditTxnDateTimePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            Cls_Generic_Methods.customWait();
            selectPaymentRequisition();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_edit,10);
            bDateAndTimeEditable = checkDateTimeIsEditable();
            m_assert.assertTrue(bDateAndTimeEditable, "Validated -->" + sEditPolicyDescription + "-->Transaction date & time edit functionality is enabled in Payment Requisition Form");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Transaction date & time edit Payment Requisition Form policy" + e);
        }
    }

    private static boolean selectPaymentRequisition(String... status) {
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

                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Status")));

                    if (purchaseStatus.equalsIgnoreCase(selectStatus)) {
                        Cls_Generic_Methods.clickElement(row);
                        flag = true;
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to find newly created payment requisition form " + e);
        }
        return flag;
    }

    public static boolean checkDateTimeIsEditable() {
        boolean flag = false;
        oPage_PaymentRequisitionForm = new Page_PaymentRequisitionForm(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_edit, 10);
            Cls_Generic_Methods.clickElementByJS(driver,oPage_PaymentRequisitionForm.button_edit);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.select_vendor, 10);
            try {
                String readOnlyValueTime = Cls_Generic_Methods.getElementAttribute(oPage_PaymentRequisitionForm.input_paymentRequisitionTime, "readonly");
                String readOnlyValueDate = Cls_Generic_Methods.getElementAttribute(oPage_PaymentRequisitionForm.input_paymentRequisitionDate, "readonly");
                if (!readOnlyValueDate.isEmpty() && !readOnlyValueTime.isEmpty()) {
                    flag = false;
                }
            } catch (NullPointerException e) {
                flag = true;
            }
            Cls_Generic_Methods.clickElementByAction(driver, oPage_PaymentRequisitionForm.button_closeWithoutSaving);

        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate time and date editable status " + e);
        }
        return flag;
    }

}

