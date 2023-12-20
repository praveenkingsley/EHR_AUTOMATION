package tests.authorizationpolicy.inventory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Transaction.Page_SRN;

public class InventorySRNPolicyTest extends TestBase {

    String sPolicyRequired = "INVENTORY";
    String sViewPolicyComponent = "VIEW (SRN)";
    String sAddPolicyComponent = "ADD (SRN)";
    String sEditTxnDatePolicyComponent = "EDIT TXN DATE & TIME (SRN)";
    String sViewPolicyDescription = "Inventory SRN View Access";
    String sAddPolicyDescription = "Inventory SRN Creation Access";
    String sEditTxnDatePolicyDescription = "Inventory SRN Transaction Date and Time Edit Access";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String sStore = "OpticalStore- Optical";

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

    @Test(enabled = true, description = "Validating Inventory Policy for View SRN Functionality")
    public void validatingViewSRNInventoryPolicyFunctionality() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

        try {

            // Disabling SRN Policy For User

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            boolean bSRNPresentInTransaction = CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SRN");

            m_assert.assertFalse(bSRNPresentInTransaction,
                    "SRN Feature is not present in Transaction as policy is disabled for user");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();

            // Enabling SRN Policy For User

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            bSRNPresentInTransaction = CommonActions.selectOptionFromLeftInInventoryStorePanel(
                    "Transaction", "SRN");
            m_assert.assertTrue(bSRNPresentInTransaction,
                    "SRN Feature is present in Transaction as policy is enabled for user");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Inventory Policy for Add SRN Functionality")
    public void validatingAddSRNInventoryPolicyFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SRN oPage_SRN = new Page_SRN(driver);
        Page_Master oPage_Master = new Page_Master(driver);

        try {

            // Disabling SRN Policy For User

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SRN");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bAddButtonNotFound = false;
            if (!Cls_Generic_Methods.isElementDisplayed(oPage_SRN.button_addNew)) {
                bAddButtonNotFound = true;
                m_assert.assertTrue(bAddButtonNotFound, "Add Button is not present in SRN as Policy is Disabled for user");
            }else{
                m_assert.assertTrue(bAddButtonNotFound, " Add Button is present in SRN , Either Policy is not disabled or not working");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();

            // Enabling SRN Policy For User

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SRN");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            boolean bAddButtonFound = false;
            if (Cls_Generic_Methods.isElementDisplayed(oPage_SRN.button_addNew)) {
                bAddButtonFound = true;
                m_assert.assertTrue(bAddButtonFound, "Add Button is present in SRN as Policy is Enabled for user");
            }else{
                m_assert.assertTrue(bAddButtonFound, " Add Button is not present in SRN , Either Policy is not Enabled or not working");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Inventory Policy for Edit TXN Date & Time SRN Functionality")
    public void validatingEditTxnDateSRNInventoryPolicyFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SRN oPage_SRN = new Page_SRN(driver);
        Page_Master oPage_Master = new Page_Master(driver);

        try {

            // Disabling SRN Policy For User

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTxnDatePolicyComponent, sEditTxnDatePolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SRN");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bEditTransactionDateTimeNotFoundInNewTemplate = false;

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SRN.button_addNew), " New Button Clicked in SRN");
            Cls_Generic_Methods.customWait(5);

            if (Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnTransactionDateValue, "readonly") != null &&
                    Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnTransactionTimeValue, "readonly") != null) {
                bEditTransactionDateTimeNotFoundInNewTemplate = true;
            }
            m_assert.assertTrue(bEditTransactionDateTimeNotFoundInNewTemplate,
                    "<b> Transaction Date Field is Disabled in SRN as Policy is Disabled for user in New SRN Template </b>");

            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.customWait();

            //Enabling SRN policy

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTxnDatePolicyComponent, sEditTxnDatePolicyDescription);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SRN");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bEditTransactionDateTimeFoundInNewTemplate = false;

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SRN.button_addNew), " New Button Clicked in SRN");
            Cls_Generic_Methods.customWait(5);

            if (Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnTransactionDateValue, "readonly") == null &&
                    Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnTransactionTimeValue, "readonly") == null) {
                bEditTransactionDateTimeFoundInNewTemplate = true;
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
                Cls_Generic_Methods.customWait(6);

            }
            m_assert.assertTrue(bEditTransactionDateTimeFoundInNewTemplate,
                    "<b> Transaction Date Field is Enabled in SRN as Policy is Enabled for user in New SRN Template </b>");
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }
    

}
