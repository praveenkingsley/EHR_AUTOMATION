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
import pages.store.PharmacyStore.Order.Page_Requisition;
import pages.store.PharmacyStore.Order.Page_RequisitionReceived;


public class InventoryRequisitionReceivedPolicyTest extends TestBase {

    String sPolicyRequired = "INVENTORY";
    String sNewTransactionPolicyComponent = "NEW TRANSACTION (REQUISITION RECEIVED)";
    String sViewPolicyComponent = "VIEW (REQUISITION RECEIVED)";
    String sClosePolicyComponent = "CLOSE (REQUISITION RECEIVED)";

    String sViewPolicyDescription = "Inventory Requisition Received View Access";
    String sNewTransactionPolicyDescription = "Inventory Requisition Fulfilment Access";
    String sClosePolicyDescription = "Inventory Requisition Received Close Access";


    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String sStore = "CENTRAL HUB 01- Central Hub";
    String sStore1 = "pharmacy viet- Pharmacy";
    String filterByText = "This Year";

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

    @Test(enabled = true, description = "Policy For Requisition Received View")
    public void inventoryPolicyForRequisitionReceivedView() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        boolean bRequisitionReceivedViewInOrder = true;

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
            bRequisitionReceivedViewInOrder = CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition Received");
            m_assert.assertFalse(bRequisitionReceivedViewInOrder, "<b> Requisition Received Can't be viewed from order. It's Disabled </b>");
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
            bRequisitionReceivedViewInOrder = CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition Received");
            m_assert.assertTrue(bRequisitionReceivedViewInOrder, "<b> Requisition Received Can be viewed from order. It's enabled </b>");

            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Policy For Requisition Received New Transaction")
    public void inventoryPolicyForRequisitionReceivedNewTransaction() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        boolean bNewTransactionButtonNotFound = false;
        boolean bNewTransactionButtonFound = false;
        boolean bRequisitionReceivedFound = true;


        try {
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sNewTransactionPolicyComponent, sNewTransactionPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition Received");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterByText);
            Cls_Generic_Methods.customWait(4);
            bRequisitionReceivedFound = getStatusOfRequisition();

            if (bRequisitionReceivedFound) {
                Cls_Generic_Methods.customWait(7);
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition)) {
                    bNewTransactionButtonNotFound = true;
                }
            }
            m_assert.assertTrue(bNewTransactionButtonNotFound, "<b> New Transaction button in Requisition received is disabled </b>");

            //enabling for PO View
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sNewTransactionPolicyComponent, sNewTransactionPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition Received");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterByText);
            Cls_Generic_Methods.customWait(4);
            bRequisitionReceivedFound = getStatusOfRequisition();

            if (bRequisitionReceivedFound) {
                Cls_Generic_Methods.customWait(7);
                if (Cls_Generic_Methods.isElementDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition)) {
                    bNewTransactionButtonFound = true;
                }
            }
            m_assert.assertTrue(bNewTransactionButtonFound, "<b> NewTransaction button in Requisition received is enabled </b>");

            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Policy For Requisition Received Close access")
    public void inventoryPolicyForRequisitionReceivedClose() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        boolean bCloseButtonNotFound = false;
        boolean bCloseButtonFound = false;
        boolean bRequisitionReceivedFound = true;


        try {
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sClosePolicyComponent, sClosePolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition Received");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterByText);
            Cls_Generic_Methods.customWait(4);
            bRequisitionReceivedFound = getStatusOfRequisition();

            if (bRequisitionReceivedFound) {
                Cls_Generic_Methods.customWait(7);
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_RequisitionReceived.button_closeRequisitionReceived)) {
                    bCloseButtonNotFound = true;
                }
            }
            m_assert.assertTrue(bCloseButtonNotFound, "<b> Close button in Requisition received is disabled </b>");

            //enabling for PO View
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sClosePolicyComponent, sClosePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition Received");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
            Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_downArrowForToday);
            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, filterByText);
            Cls_Generic_Methods.customWait(4);
            bRequisitionReceivedFound = getStatusOfRequisition();

            if (bRequisitionReceivedFound) {
                Cls_Generic_Methods.customWait(7);
                if (Cls_Generic_Methods.isElementDisplayed(oPage_RequisitionReceived.button_closeRequisitionReceived)) {
                    bCloseButtonFound = true;
                }
            }
            m_assert.assertTrue(bCloseButtonFound, "<b> Close button in Requisition received is enabled </b>");

            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    public static boolean getStatusOfRequisition() {
        boolean bRequisitionReceivedFound = false;
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        String sStatus = "Transferred";
        String sStatus1 = "Pending";
        try {
            for (WebElement eStatus : oPage_RequisitionReceived.list_statusOfRequisitionReceived) {
                if (Cls_Generic_Methods.getTextInElement(eStatus).contains(sStatus) ||
                        Cls_Generic_Methods.getTextInElement(eStatus).contains(sStatus1)) {
                    bRequisitionReceivedFound = true;
                    Cls_Generic_Methods.clickElement(eStatus);
                    Cls_Generic_Methods.customWait(5);
                    break;
                }
            }
            if (bRequisitionReceivedFound) {
                m_assert.assertTrue(bRequisitionReceivedFound, "Requisition Received Found with status: " + sStatus);
            } else {
                m_assert.assertFalse(bRequisitionReceivedFound, "Requisition Received not Found with status: " + sStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to find requisition " + e);
        }
        return bRequisitionReceivedFound;
    }
}
