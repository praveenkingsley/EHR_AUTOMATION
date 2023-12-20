package tests.authorizationpolicy.inventory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.store.Page_Store;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Transaction.Page_SON;

import java.text.DecimalFormat;
import java.util.List;

public class InventoryPolicySONTest extends TestBase {

    String sPolicyRequired = "INVENTORY";
    String sViewPolicyComponent = "VIEW (SON)";
    String sAddPolicyComponent = "ADD (SON)";
    String sApprovePolicyComponent = "APPROVE (SON)";
    String sEditPolicyComponent = "EDIT (SON)";
    String sCancelPolicyComponent = "CANCEL (SON)";
    String sEditTxnDateTimePolicyComponent = "EDIT TXN DATE & TIME (SON)";
    String sViewPolicyDescription = "Inventory SON View Access";
    String sAddPolicyDescription = "Inventory SON Creation Access";
    String sApprovePolicyDescription = "Inventory SON Approval Access";
    String sEditPolicyDescription = "Inventory SON Edit Access";
    String sCancelPolicyDescription = "Inventory SON Cancel Access";
    String sEditTxnDateTimePolicyDescription = "Inventory SON Transaction Date and Time Edit Access";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    static String myMedicationName = "SONTest";
    String sStore = "Pharmacy Viet- Pharmacy";
    static String sDocumentNumber = "Test123";
    static String subStore = "Default";
    static String sUnitCostWOTax = "100";
    static String packageQuantity = "10";
    static String sellingPrice = "200";

    static String sSonTransactionStatus = "Open";

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

    @Test(enabled = true, description = "Validating Inventory policy for View SON Functionality")
    public void validatingViewSONInventoryPolicyFunctionality() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        try {
            // Disabling  SON Policy For User
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
                    "Transaction", "SON");

            m_assert.assertFalse(bSonPresentInTransaction,
                    " <b> SON </b> Feature is not present in Transaction as policy is <b> Disabled </b> for user");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();

            // Enabling SON Policy For User
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
                    "Transaction", "SON");

            m_assert.assertTrue(bSonPresentInTransaction, " <b> SON </b> Feature is present in Transaction as policy is <b> Enabled </b> for user");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validating Inventory policy for Add SON Functionality")
    public void validatingAddSONInventoryPolicyFunctionality() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SON oPage_SON = new Page_SON(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        try {

            // Disabling SON Policy For User
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy
            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SON");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            boolean bAddButtonNotFound = false;
            if (!Cls_Generic_Methods.isElementDisplayed(oPage_SON.button_addNewButton)) {
                bAddButtonNotFound = true;
                m_assert.assertTrue(bAddButtonNotFound, " <b> Add Button </b> is not present in SON as Policy is <b> Disabled </b> for user");
            } else {
                m_assert.assertTrue(bAddButtonNotFound, " Add Button is present in SON , Either Policy is not disabled or not working");

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

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SON");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            boolean bAddButtonFound = false;
            if (Cls_Generic_Methods.isElementDisplayed(oPage_SON.button_addNewButton)) {
                bAddButtonFound = true;
                m_assert.assertTrue(bAddButtonFound, " <b> Add Button  </b> is present in SON as Policy is <b> Enabled </b> for user");
            } else {
                m_assert.assertTrue(bAddButtonFound, " Add Button is not present in SON , Either Policy is not Enabled or not working");

            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validating Inventory policy for Approve SON Functionality")
    public void validatingApproveSONInventoryPolicyFunctionality() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SON oPage_SON = new Page_SON(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        try {
            // Disabling SON Policy For User
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sApprovePolicyComponent, sApprovePolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy
            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SON");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bCreateSonTransaction = createSonForPolicyCheck();

            if (bCreateSonTransaction) {

                boolean bApproveButtonNotFound = false;
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_SON.button_Approve)) {
                    bApproveButtonNotFound = true;
                    m_assert.assertTrue(bApproveButtonNotFound,
                            " <b> Approve Button </b>  is not present in SON as Policy is <b> Disabled </b> for user");
                } else {
                    m_assert.assertTrue(bApproveButtonNotFound, " Approve Button is present in SON , Either Policy is not disabled or not working");

                }

                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.switchToOtherTab();

                CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sApprovePolicyComponent, sApprovePolicyDescription);
                Cls_Generic_Methods.customWait(3);

                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SON");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
                boolean bSONRecordFound = selectRecordFromListInInventoryStores(oPage_SON.list_SONTransactions, sSonTransactionStatus);

                m_assert.assertTrue(bSONRecordFound, "Son Records Found and Clicked");

                if (bSONRecordFound) {
                    boolean bApproveButtonFound = false;
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_SON.button_Approve)) {
                        bApproveButtonFound = true;
                        m_assert.assertTrue(bApproveButtonFound,
                                " <b> Approve Button </b>  is present in SON as Policy is <b> Enabled </b> for user");
                    } else {
                        m_assert.assertTrue(bApproveButtonFound, "Approve Button is Not present in SON , Either Policy is not Enabled or not working");
                    }
                } else {
                    m_assert.assertTrue(bSONRecordFound, " SON Records Not Present in the list , please create one");
                }
            } else {
                m_assert.assertTrue(bCreateSonTransaction, " SON Records Not Created In the list");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validating Inventory policy for Edit SON Functionality")
    public void validatingEditSONInventoryPolicyFunctionality() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SON oPage_SON = new Page_SON(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        try {

            // Disabling SON Policy For User
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent, sEditPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy
            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SON");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bSONRecordFound = selectRecordFromListInInventoryStores(oPage_SON.list_SONTransactions, sSonTransactionStatus);

            m_assert.assertTrue(bSONRecordFound, "Son Records Found and Clicked");

            if (bSONRecordFound) {
                boolean bEditButtonNotFound = false;
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_SON.button_editSONButton)) {
                    bEditButtonNotFound = true;
                    m_assert.assertTrue(bEditButtonNotFound, "Edit Button is not present in SON as Policy is Disabled for user");
                } else {
                    m_assert.assertTrue(bEditButtonNotFound, " Edit Button is present in SON , Either Policy is not disabled or not working");

                }
            } else {
                m_assert.assertTrue(bSONRecordFound, " Son Records are not there , please create new");

            }

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent, sEditPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SON");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            bSONRecordFound = selectRecordFromListInInventoryStores(oPage_SON.list_SONTransactions, sSonTransactionStatus);

            m_assert.assertTrue(bSONRecordFound, "Son Records Found and Clicked");

            if (bSONRecordFound) {
                boolean bEditButtonFound = false;
                if (Cls_Generic_Methods.isElementDisplayed(oPage_SON.button_editSONButton)) {
                    bEditButtonFound = true;
                    m_assert.assertTrue(bEditButtonFound, "Edit Button is present in SON as Policy is Enabled for user");
                } else {
                    m_assert.assertTrue(bEditButtonFound, "Edit Button is Not present in SON , Either Policy is not Enabled or not working");

                }
            } else {
                m_assert.assertTrue(bSONRecordFound, " Son Records are not there , please create new");

            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validating Inventory policy for Cancel SON Functionality")
    public void validatingCancelSONInventoryPolicyFunctionality() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SON oPage_SON = new Page_SON(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        try {


            // Disabling SON Policy For User

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCancelPolicyComponent, sCancelPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SON");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bSONRecordFound = selectRecordFromListInInventoryStores(oPage_SON.list_SONTransactions, sSonTransactionStatus);

            m_assert.assertTrue(bSONRecordFound, "Son Records Found and Clicked");

            if (bSONRecordFound) {
                boolean bCancelButtonNotFound = false;
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_SON.button_cancelSONButton)) {
                    bCancelButtonNotFound = true;
                    m_assert.assertTrue(bCancelButtonNotFound, "Cancel Button is not present in SON as Policy is Disabled for user");
                } else {
                    m_assert.assertTrue(bCancelButtonNotFound, "Cancel Button is  present in SON , Either Policy is  Disabled or not working");

                }
            } else {
                m_assert.assertTrue(bSONRecordFound, " Son Records are not there , please create new");

            }

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCancelPolicyComponent, sCancelPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SON");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            bSONRecordFound = selectRecordFromListInInventoryStores(oPage_SON.list_SONTransactions, sSonTransactionStatus);

            m_assert.assertTrue(bSONRecordFound, "Son Records Found and Clicked");

            if (bSONRecordFound) {
                boolean bCancelButtonFound = false;
                if (Cls_Generic_Methods.isElementDisplayed(oPage_SON.button_cancelSONButton)) {
                    bCancelButtonFound = true;
                    m_assert.assertTrue(bCancelButtonFound, "Cancel Button is present in SON as Policy is Enabled for user");
                } else {
                    m_assert.assertTrue(bCancelButtonFound, "Cancel Button is Not present in SON , Either Policy is not Enabled or not working");

                }
            } else {
                m_assert.assertTrue(bSONRecordFound, " Son Records are not there , please create new");

            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validating Inventory policy for Edit TXN Date & Time SON Functionality")
    public void validatingEditTxnDateTimeSONInventoryPolicyFunctionality() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SON oPage_SON = new Page_SON(driver);
        Page_Master oPage_Master = new Page_Master(driver);

        try {

            // Disabling SON Policy For User

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTxnDateTimePolicyComponent, sEditTxnDateTimePolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SON");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bEditTransactionDateTimeNotFoundInNewSonTemplate = false;

            // validation Policy in New SON Template

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SON.button_addNewButton), " New Button CLicked in SON");
            Cls_Generic_Methods.customWait(5);

            String disabledTransactionDateClassValue = "form-control p7_10 fullname";
            String disabledTransactionTimeClassValue = "form-control p7_10 transaction-timepicker";


            String expectedTransactionDateClassValue = Cls_Generic_Methods.getElementAttribute(oPage_SON.input_sonTransactionDate, "class");
            String expectedTransactionTimeClassValue = Cls_Generic_Methods.getElementAttribute(oPage_SON.input_sonTransactionTime, "class");

            if (expectedTransactionDateClassValue.equalsIgnoreCase(disabledTransactionDateClassValue) &&
                    expectedTransactionTimeClassValue.equalsIgnoreCase(disabledTransactionTimeClassValue)) {
                bEditTransactionDateTimeNotFoundInNewSonTemplate = true;
                m_assert.assertTrue(bEditTransactionDateTimeNotFoundInNewSonTemplate,
                        " <b> Transaction Date and Time </b> Field is Disabled in SON as Policy is <b> Disabled </b> for user in New Son Template");
            } else {
                m_assert.assertTrue(bEditTransactionDateTimeNotFoundInNewSonTemplate,
                        "Transaction Date and TIme Field Enabled in Add SON , Either Policy is not Disabled or not working");

            }

            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
            Cls_Generic_Methods.customWait();

            // Validating Policy In Son edit template
            boolean bSONRecordFound = selectRecordFromListInInventoryStores(oPage_SON.list_SONTransactions, sSonTransactionStatus);

            m_assert.assertTrue(bSONRecordFound, "Son Records Found and Clicked");

            if (bSONRecordFound) {

                boolean bEditTransactionDateTimeNotFoundInEditSonTemplate = false;
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SON.button_editSONButton), "Edit Button CLicked In Son");
                Cls_Generic_Methods.customWait(5);

                expectedTransactionDateClassValue = Cls_Generic_Methods.getElementAttribute(oPage_SON.input_sonTransactionDate, "class");
                expectedTransactionTimeClassValue = Cls_Generic_Methods.getElementAttribute(oPage_SON.input_sonTransactionTime, "class");


                if (expectedTransactionDateClassValue.equalsIgnoreCase(disabledTransactionDateClassValue) &&
                        expectedTransactionTimeClassValue.equalsIgnoreCase(disabledTransactionTimeClassValue)) {
                    bEditTransactionDateTimeNotFoundInEditSonTemplate = true;
                    m_assert.assertTrue(bEditTransactionDateTimeNotFoundInEditSonTemplate,
                            "<b> Transaction Date and Time </b> Field is Disabled in SON as Policy is </b> Disabled for user in Edit Son Template");
                } else {
                    m_assert.assertTrue(bEditTransactionDateTimeNotFoundInEditSonTemplate,
                            "Transaction Date and TIme Field Enabled in Edit SON , Either Policy is not Disabled or not working");

                }

                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
                Cls_Generic_Methods.customWait();

            } else {
                m_assert.assertTrue(bSONRecordFound, " Son Records Not present in son , please add new");
            }

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.customWait();

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTxnDateTimePolicyComponent, sEditTxnDateTimePolicyDescription);
            Cls_Generic_Methods.customWait(3);

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SON");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bEditTransactionDateTimeFoundInNewSonTemplate = false;

            //Validating Son Policy In New Son Template

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SON.button_addNewButton), " New Button CLicked in SON");
            Cls_Generic_Methods.customWait(5);

            String enabledTransactionDateClassValue = "form-control p7_10 fullname transaction-datepicker hasDatepicker";
            String enabledTransactionTimeClassValue = "form-control p7_10 transaction-timepicker";

            expectedTransactionDateClassValue = Cls_Generic_Methods.getElementAttribute(oPage_SON.input_sonTransactionDate, "class");
            expectedTransactionTimeClassValue = Cls_Generic_Methods.getElementAttribute(oPage_SON.input_sonTransactionTime, "class");

            if (expectedTransactionDateClassValue.equalsIgnoreCase(enabledTransactionDateClassValue) &&
                    expectedTransactionTimeClassValue.equalsIgnoreCase(enabledTransactionTimeClassValue)) {
                bEditTransactionDateTimeFoundInNewSonTemplate = true;
                m_assert.assertTrue(bEditTransactionDateTimeFoundInNewSonTemplate,
                        " <b> Transaction Date and Time </b> Field is Enabled in SON as Policy is <b> Enabled </b> for user in New Son Template");
            } else {
                m_assert.assertTrue(bEditTransactionDateTimeFoundInNewSonTemplate,
                        "Transaction Date and Time Field Disabled in Add SON , Either Policy is not Enabled or not working");

            }

            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
            Cls_Generic_Methods.customWait();

            bSONRecordFound = selectRecordFromListInInventoryStores(oPage_SON.list_SONTransactions, sSonTransactionStatus);

            m_assert.assertTrue(bSONRecordFound, "Son Records Found and Clicked");

            if (bSONRecordFound) {

                boolean bEditTransactionDateTimeFoundInEditSonTemplate = false;
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SON.button_editSONButton), "Edit Button CLicked In Son");
                Cls_Generic_Methods.customWait(5);

                expectedTransactionDateClassValue = Cls_Generic_Methods.getElementAttribute(oPage_SON.input_sonTransactionDate, "class");
                expectedTransactionTimeClassValue = Cls_Generic_Methods.getElementAttribute(oPage_SON.input_sonTransactionTime, "class");


                if (expectedTransactionDateClassValue.equalsIgnoreCase(enabledTransactionDateClassValue) &&
                        expectedTransactionTimeClassValue.equalsIgnoreCase(enabledTransactionTimeClassValue)) {
                    bEditTransactionDateTimeFoundInEditSonTemplate = true;
                    m_assert.assertTrue(bEditTransactionDateTimeFoundInEditSonTemplate,
                            " <b> Transaction Date and Time </b> Field is Enabled in SON as Policy is <b> Enabled </b> for user in Edit Son Template");
                } else {
                    m_assert.assertTrue(bEditTransactionDateTimeFoundInEditSonTemplate,
                            "Transaction Date and Time Field Disabled in Edit SON , Either Policy is Disabled or not working");

                }

                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
                Cls_Generic_Methods.customWait();

            } else {
                m_assert.assertTrue(bSONRecordFound, " Son Records Not present in son , please add new");
            }

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    public static boolean createSonForPolicyCheck() {

        boolean bSonCreated = false;
        Page_SON oPage_SON = new Page_SON(driver);
        try {

            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SON.button_addNewButton), "Clicked new button to add item");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SON.input_OrderTime, 8);
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_documentNumber, sDocumentNumber),
                    "Entered document number as <b> :" + sDocumentNumber);
            Cls_Generic_Methods.customWait();
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_variantDescription, myMedicationName),
                    "Entered variant name as <b>" + myMedicationName + "</b> to search");
            oPage_SON.input_variantDescription.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(5);
            boolean myMedicationFound = false;
            for (WebElement e : oPage_SON.list_medicationNameOnLeft) {
                if (Cls_Generic_Methods.getTextInElement(e).contains(myMedicationName)) {
                    Cls_Generic_Methods.clickElement(e);
                    myMedicationFound = true;
                    break;
                }
            }
            m_assert.assertInfo(myMedicationFound, "Required medication found in search");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SON.header_addNewLot, 15);
            if (Cls_Generic_Methods.isElementDisplayed(oPage_SON.select_subStore)) {
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_SON.select_subStore, subStore),
                        "Sub Store: <b> " + subStore + " </b>");
            }
            if (Cls_Generic_Methods.isElementDisplayed(oPage_SON.input_expiryDate)) {
                String sTodayDate = Cls_Generic_Methods.getTodayDate("dd/MM/yyyy");
                String sExpiryDate = Cls_Generic_Methods.getDifferenceInDays(sTodayDate, 60, "dd/MM/yyyy");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysByJS(driver, oPage_SON.input_expiryDate, sExpiryDate), "Entered expiry date as <b> " + sExpiryDate);
            }

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_unitCostWOTax, sUnitCostWOTax),
                    "Unit cost without tax entered as : <b> " + sUnitCostWOTax + "</b>");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clearValuesInElement(oPage_SON.input_packageQuantity);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_packageQuantity, packageQuantity),
                    "package entry entered as : <b> " + packageQuantity + "</b>");
            Cls_Generic_Methods.clearValuesInElement(oPage_SON.input_sellingPrice);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_sellingPrice, sellingPrice),
                    "Selling Price entered as : <b> " + sellingPrice + "</b>");
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SON.button_saveLot), "Saving the Lot details");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SON.button_saveChanges, 8);
            Cls_Generic_Methods.clickElement(oPage_SON.button_saveChanges);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SON.button_addNewButton, 8);
            for (WebElement row : oPage_SON.list_SONTransactions) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(4));
                    if (purchaseStatus.equalsIgnoreCase(sSonTransactionStatus)) {
                        bSonCreated = true;
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(row), "SON Record Clicked In Record List");
                        Cls_Generic_Methods.customWait(2);
                        break;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
        return bSonCreated;
    }

    public static boolean selectRecordFromListInInventoryStores(List<WebElement> listOfRecords, String expectedStatus) {

        boolean bStatusFound = false;

        try {
            for (WebElement row : listOfRecords) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(4));
                    if (purchaseStatus.equalsIgnoreCase(expectedStatus)) {
                        bStatusFound = true;
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(row), " Record Clicked In Record List");
                        Cls_Generic_Methods.customWait(2);
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
