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
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Transaction.Page_Adjustment;

import java.util.List;

public class InventoryAdjustmentPolicyTest extends TestBase {
    String sPolicyRequired = "INVENTORY";
    String sViewPolicyComponent = "VIEW (ADJUSTMENT)";
    String sAddPolicyComponent = "ADD (ADJUSTMENT)";
    String sEditTxnDatePolicyComponent = "EDIT TXN DATE (ADJUSTMENT)";
    String sViewPolicyDescription = "Inventory Adjustment View Access";
    String sAddPolicyDescription = "Inventory Adjustment Creation Access";
    String sEditTxnDatePolicyDescription = "Inventory Adjustment Transaction Date Edit Access";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String sStore = "Pharmacy automation- Pharmacy";

    @BeforeMethod
    private void init() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        try {
            //Opening Organization Settings
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
           // CommonActions.selectFacility("OPTHA1");
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

    @Test(enabled = true, description = "Validating Inventory Policy for View Adjustment Functionality")
    public void validatingViewAdjustmentInventoryPolicyFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        try {


            // Disabling Adjustment Policy For User

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            boolean bAdjustmentPresentInTransaction = CommonActions.selectOptionFromLeftInInventoryStorePanel(
                    "Transaction", "Adjustment");
            m_assert.assertFalse(bAdjustmentPresentInTransaction,
                    " <b> Adjustment Feature is not present in Transaction as policy is disabled for user </b>");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();

            // Enabling Adjustment Policy For User

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            bAdjustmentPresentInTransaction = CommonActions.selectOptionFromLeftInInventoryStorePanel(
                    "Transaction", "Adjustment");
            m_assert.assertTrue(bAdjustmentPresentInTransaction,
                    "<b> Adjustment Feature is present in Transaction as policy is enabled for user </b>");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Inventory policy for Add Adjustment Functionality")
    public void validatingAddAdjustmentInventoryPolicyFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Adjustment oPage_Adjustment = new Page_Adjustment(driver);
        Page_Master oPage_Master = new Page_Master(driver);

        try {

            // Disabling Adjustment Policy For User

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Adjustment");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            boolean bAddButtonNotFound = false;
            if (!Cls_Generic_Methods.isElementDisplayed(oPage_Adjustment.button_New)) {
                bAddButtonNotFound = true;
                m_assert.assertTrue(bAddButtonNotFound,
                        " <b> Add Button is not present in Adjustment as Policy is Disabled for user </b>");
            }else{
                m_assert.assertTrue(bAddButtonNotFound, " Add Button is present in Adjustment , Either Policy is not disabled or not working");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();

            // Enabling Adjustment Policy For User

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Adjustment");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            boolean bAddButtonFound = false;
            if (Cls_Generic_Methods.isElementDisplayed(oPage_Adjustment.button_New)) {
                bAddButtonFound = true;
                m_assert.assertTrue(bAddButtonFound,
                        "<b> Add Button is present in Adjustment as Policy is Enabled for user </b>");
            }else{
                m_assert.assertTrue(bAddButtonFound, " Add Button is not present in Adjustment , Either Policy is not Enabled or not working");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Inventory policy for Edit TXN Date Adjustment Functionality")
    public void validatingEditTxnDateAdjustmentInventoryPolicyFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Adjustment oPage_Adjustment = new Page_Adjustment(driver);
        Page_Master oPage_Master = new Page_Master(driver);

        try {

            // Disabling Adjustment Policy For User

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTxnDatePolicyComponent, sEditTxnDatePolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Adjustment");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bEditTransactionDateNotFoundInNewTemplate = false;
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Adjustment.button_New),
                    " New Button Clicked in Adjustment");
            Cls_Generic_Methods.customWait(5);

            if (Cls_Generic_Methods.getElementAttribute(oPage_Adjustment.input_adjustmentTransactionDate, "readonly") != null){
                bEditTransactionDateNotFoundInNewTemplate = true;
            }
            m_assert.assertTrue(bEditTransactionDateNotFoundInNewTemplate,
                    "<b> Transaction Date Field is Disabled in Adjustment as Policy is Disabled for user in New Template </b>");


            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.customWait();

            //Enabling Adjustment policy

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTxnDatePolicyComponent, sEditTxnDatePolicyDescription);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Adjustment");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bEditTransactionDateFoundInNewTemplate = false;

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Adjustment.button_New), " New Button Clicked in Adjustment");
            Cls_Generic_Methods.customWait(5);

            if (Cls_Generic_Methods.getElementAttribute(oPage_Adjustment.input_adjustmentTransactionDate, "readonly") == null) {
                bEditTransactionDateFoundInNewTemplate = true;
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
                Cls_Generic_Methods.customWait(6);
            }
            m_assert.assertTrue(bEditTransactionDateFoundInNewTemplate,
                    "<b> Transaction Date Field is Enabled in Adjustment as Policy is Enabled for user in New Template </b>");

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

}
