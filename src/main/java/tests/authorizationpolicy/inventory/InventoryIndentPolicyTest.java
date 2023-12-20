package tests.authorizationpolicy.inventory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_IndentPolicy;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Order.Page_Indent;
import pages.store.PharmacyStore.Order.Page_PurchaseOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryIndentPolicyTest extends TestBase {
    public static Map<String, String> mapTracker = new HashMap<String, String>();
    public static String key_CreatedAt_IndentOrderFromStore = "key_CreatedAt_IndentOrderFromStore";
    public static String sINDENT_STORE = "Pharmacy automation- Pharmacy";
    String sExpectedButtonToBeSelected = "Variant";
    String sPolicyRequired = "INVENTORY";
    String sAddPolicyComponent = "ADD (INDENT)";
    String sEditPolicyComponent = "EDIT INDENT DATE AND TIME (INDENT)";
    String sAddPolicyDescription = "Inventory Indent Creation Access";
    String sEDitPolicyDescription = "Inventory Indent Date and Time Edit Access";
    String sViewPolicyDescription = "Inventory Indent View Access";
    String sViewPolicyComponent = "VIEW (INDENT)";
    String sNewOrderPolicyComponent = "NEW ORDER (INDENT)";
    String sNewOrderPolicyDescription = "Inventory Purchase Order Creation Against Indent Access";
    String sSelectStoreValue = "5d9b8b8ccd29ba0b04bfe731";
    String sNote = "Remarks";
    String indentOrderTime = "";
    String indentOrderDate = "";
    String sQTY = "10";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String sIndentType = "Normal";

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

    @Test(enabled = true, description = "Validating Inventory policy for View Indent Functionality")
    public void validatingViewIndentInventoryPolicyFunctionality() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        try {
            // Disabling  Indent Policy For User
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy
            m_assert.assertTrue(CommonActions.selectStoreOnApp(sINDENT_STORE), sINDENT_STORE + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            boolean bIndentPresentInTransaction = CommonActions.selectOptionFromLeftInInventoryStorePanel(
                    "Order", "Indent");

            m_assert.assertFalse(bIndentPresentInTransaction,
                    " <b> Indent </b> Feature is not present in Order as policy is <b> Disabled </b> for user");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();

            // Enabling Indent Policy For User
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            bIndentPresentInTransaction = CommonActions.selectOptionFromLeftInInventoryStorePanel(
                    "Order", "Indent");

            m_assert.assertTrue(bIndentPresentInTransaction, " <b> Indent </b> Feature is present in Order as policy is <b> Enabled </b> for user");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validating Inventory Policy For Add Indent Functionality")
    public void validatingAddIndentInventoryPolicyFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_IndentPolicy oPage_IndentPolicy = new Page_IndentPolicy(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        try {

            // Disabling Indent Policy For User
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening Store to validate the policy
            m_assert.assertTrue(CommonActions.selectStoreOnApp(sINDENT_STORE), sINDENT_STORE + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 16);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 16);
            boolean bAddButtonNotFound = false;
            if (!Cls_Generic_Methods.isElementDisplayed(oPage_IndentPolicy.button_indentNew)) {
                bAddButtonNotFound = true;
                m_assert.assertTrue(bAddButtonNotFound, "<b>Add Button</b> is not present in Indent as Policy is Disabled for user");
            } else {
                m_assert.assertTrue(bAddButtonNotFound, " <b>Add Button</b> is present in Indent , Either Policy is not disabled or not working");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            // Enabling Indent Policy For User
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            boolean bAddButtonFound = false;
            if (Cls_Generic_Methods.isElementDisplayed(oPage_IndentPolicy.button_indentNew)) {
                bAddButtonFound = true;
                m_assert.assertTrue(bAddButtonFound, "<b>Add Button</b> is present in Indent as Policy is Enabled for user");
            } else {
                m_assert.assertTrue(bAddButtonFound, "<b>Add Button</b> is not present in Indent, Either Policy is not Enabled or not working");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Inventory Policy For Edit Indent Order Date And Time Functionality")
    public void validatingEditIndentDateAndTimeInventoryPolicyFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_IndentPolicy oPage_IndentPolicy = new Page_IndentPolicy(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        try {
            // Disabling Indent Policy For User
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent, sEDitPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening Store to validate the policy
            m_assert.assertTrue(CommonActions.selectStoreOnApp(sINDENT_STORE), sINDENT_STORE + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 16);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 16);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IndentPolicy.button_indentNew),
                    "Clicked on New button to create NEW Indent Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IndentPolicy.input_indentOrderDateField, 16);
            m_assert.assertTrue(oPage_IndentPolicy.input_indentOrderDateField.getAttribute("readonly").equals("true"),
                    "<b>Order Date field is non editable</b> in Indent as Policy is Disabled for user");
            m_assert.assertTrue(oPage_IndentPolicy.input_indentOrderTimeField.getAttribute("readonly").equals("true"),
                    "<b>Order Time field is non editable</b> in Indent as Policy is Disabled for user");
            Cls_Generic_Methods.customWait(4);
            Cls_Generic_Methods.switchToOtherTab();
            // Enabling Indent Policy For User
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent, sEDitPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 16);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IndentPolicy.button_indentNew),
                    "Clicked on New button to create NEW Indent Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IndentPolicy.input_indentOrderDateField, 16);
            boolean bOrderDateTimeFieldIsEnabled = false;
            if (Cls_Generic_Methods.isElementEnabled(oPage_IndentPolicy.input_indentOrderDateField) &&
                    Cls_Generic_Methods.isElementEnabled(oPage_IndentPolicy.input_indentOrderTimeField)) {
                bOrderDateTimeFieldIsEnabled = true;
                m_assert.assertTrue(bOrderDateTimeFieldIsEnabled, "<b>Order Date and Time field is editable</b> in Indent as Policy is Enabled for user");
            } else {
                m_assert.assertTrue(bOrderDateTimeFieldIsEnabled, "<b>Order Date and Time field is non editable</b> in Indent , Either Policy is not Enabled or not working");
            }

            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Create Indent order")
    public void createIndentOrderToValidateNewIndentOrderPolicy() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        try {
            // Disabling Edit Indent order date and time  field Policy For User
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent, sEDitPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening Store to validate the policy
            m_assert.assertTrue(CommonActions.selectStoreOnApp(sINDENT_STORE), sINDENT_STORE + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 16);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait(3);
            // creating new Indent Order
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 16);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_addNewIndent),
                    "New Button clicked in Order: Indent");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 16);
            Cls_Generic_Methods.selectElementByValue(oPage_Indent.select_StoreInIndent, sSelectStoreValue);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 16);
            String sSelectedButton = Cls_Generic_Methods.getTextInElement(oPage_Indent.button_variantOrRequisitionSelected);
            if (sSelectedButton.equalsIgnoreCase(sExpectedButtonToBeSelected)) {
                boolean ItemList = oPage_Indent.input_itemForIndent.isEmpty();
                if (ItemList) {
                    m_assert.assertTrue("ItemList is empty");
                } else {
                    m_assert.assertTrue(false, "ItemList is not empty");
                }
            } else {
                m_assert.assertTrue("Expected Button = <b>" + sExpectedButtonToBeSelected + "</b> is not selected");
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_noteUnderIndentForPurchase, 10);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_noteUnderIndentForPurchase, sNote),
                    "Note field value filled as =  <b>" + sNote + "</b> ");
            Cls_Generic_Methods.customWait();
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_ItemDescriptionsUnderIndentPurchase.get(0)),
                    "Selected item for indent purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 20);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                    "Clicked on save button");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_validationForQuantity, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.text_validationForQuantity),
                    "Validation for quantity field = <b>This field is required.</b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_indentOrderDate, 10);
            indentOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderTime, "value");
            indentOrderTime = indentOrderTime.replaceAll(" ", "");
            m_assert.assertTrue("Indent order time:<b> " + indentOrderTime + "</b>");
            indentOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderDate, "value");
            indentOrderDate = indentOrderDate.replaceAll("/", "-");
            m_assert.assertTrue("Indent order date:<b> " + indentOrderDate + "</b>");
            mapTracker.put(key_CreatedAt_IndentOrderFromStore, indentOrderDate + "  |  " + indentOrderTime);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_indentType, 8);
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.select_indentType, sIndentType), "Selected indent type as <b>" + sIndentType + "</b>");
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_quantityField, sQTY);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder),
                    " Indent order saved");
            Cls_Generic_Methods.customWait(5);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validate New Indent Order Policy")
    public void validatingNewIndentOrderInventoryPolicyFunctionality() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_PurchaseOrder oPage_PO = new Page_PurchaseOrder(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        Page_IndentPolicy oPage_IndentPolicy = new Page_IndentPolicy(driver);

        try {
            // Disabling Indent New order Policy For User
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sNewOrderPolicyComponent, sNewOrderPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening Store to validate the policy
            m_assert.assertTrue(CommonActions.selectStoreOnApp(sINDENT_STORE), sINDENT_STORE + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 16);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Indent.list_dateTimeOfIndentOrder, 10);
            boolean bOrderFound = selectIndentOrderFromListInInventoryStores(oPage_Indent.list_dateTimeOfIndentOrder, indentOrderDate, indentOrderTime);
            m_assert.assertTrue(bOrderFound, "Order found in the Indent order page");
            if(bOrderFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 16);
                boolean bNewOrderButtonNotFound = false;
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_IndentPolicy.button_newIndentOrder)) {
                    bNewOrderButtonNotFound = true;
                    m_assert.assertTrue(bNewOrderButtonNotFound, "<b>New Order Button</b> is not present in Indent as Policy is Disabled for user");
                } else {
                    m_assert.assertTrue(bNewOrderButtonNotFound, " <b>New Order Button</b> is present in Indent , Either Policy is not disabled or not working");
                }
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.switchToOtherTab();
            } else {
                m_assert.assertTrue(bOrderFound, "Indent Order not found");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.switchToOtherTab();
            }
            // Enabling Indent Policy For User
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sNewOrderPolicyComponent, sNewOrderPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 16);
            boolean bIndentOrders = selectIndentOrderFromListInInventoryStores(oPage_Indent.list_dateTimeOfIndentOrder, indentOrderDate, indentOrderTime);
            m_assert.assertTrue(bIndentOrders, "Order found in the Indent order page");
            Cls_Generic_Methods.customWait(3);
            if (bIndentOrders) {
                boolean bAddButtonFound = false;
                if (Cls_Generic_Methods.isElementDisplayed(oPage_IndentPolicy.button_newIndentOrder)) {
                    bAddButtonFound = true;
                    m_assert.assertTrue(bAddButtonFound, "<b>New order Button</b> is present in Indent as Policy is Enabled for user");
                } else {
                    m_assert.assertTrue(bAddButtonFound, "<b>New order Button</b> is not present in Indent, Either Policy is not Enabled or not working");
                }
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            } else {
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    public static boolean selectIndentOrderFromListInInventoryStores(List<WebElement> listOfIndentOrders, String expectedIndentOrderDate, String expectedIndentOrderTime) {

        boolean bIndentOrderFound = false;

        try {
            for (WebElement eIndentOrderDateAndTime : listOfIndentOrders) {
                String dateTimeFull = Cls_Generic_Methods.getTextInElement(eIndentOrderDateAndTime);
                if (dateTimeFull.contains(expectedIndentOrderDate) &&
                        dateTimeFull.contains(expectedIndentOrderTime)) {
                    bIndentOrderFound = true;
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(eIndentOrderDateAndTime), "Clicked on Indent order");
                    Cls_Generic_Methods.customWait(2);
                    break;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
        return bIndentOrderFound;

    }
}
