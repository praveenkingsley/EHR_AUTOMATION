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
import pages.store.PharmacyStore.Transaction.Page_DirectStockEntry;

import java.util.List;
import java.util.NoSuchElementException;

import static pages.commonElements.CommonActions.getRandomString;

public class InventoryDirectStockEntryPolicyTest extends TestBase {
    String sPolicyRequired = "INVENTORY";
    String sViewPolicyComponent = "VIEW (DIRECT STOCK ENTRY)";
    String sAddPolicyComponent = "ADD (DIRECT STOCK ENTRY)";
    String sApprovePolicyComponent = "APPROVE (DIRECT STOCK ENTRY)";
    String sEditPolicyComponent = "EDIT (DIRECT STOCK ENTRY)";
    String sCancelPolicyComponent = "CANCEL (DIRECT STOCK ENTRY)";
    String sEditTxnDateTimePolicyComponent = "EDIT TXN DATE & TIME (DIRECT STOCK ENTRY)";
    String sViewPolicyDescription = "Inventory Direct Stock Entry View Access";
    String sAddPolicyDescription = "Inventory Direct Stock Entry Creation Access";
    String sApprovePolicyDescription = "Inventory Direct Stock Entry Approval Access";
    String sEditPolicyDescription = "Inventory Direct Stock Entry Edit Access";
    String sCancelPolicyDescription = "Inventory Direct Stock Entry Cancellation Access";
    String sEditTxnDateTimePolicyDescription = "Inventory Direct Stock Entry Transaction Date and Time Edit Access";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String sStore = "Pharmacy automation- Pharmacy";
    String sStockStatus = "Open";
    public static final String itemDescription = "DirectStockEntry_" + getRandomString(4);

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

    @Test(enabled = true, description = "Validating Inventory Policy for View Direct Stock Entry Functionality")
    public void validatingViewDirectStockEntryInventoryPolicyFunctionality() {

        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        try {

            // Disabling Direct Stock Entry Policy For User

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUsers, 5);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            boolean bDirectStockEntryPresentInTransaction = CommonActions.selectOptionFromLeftInInventoryStorePanel(
                    "Transaction", "Direct Stock Entry");
            m_assert.assertFalse(bDirectStockEntryPresentInTransaction,
                    "<b> Direct Stock Entry Feature is not present in Transaction as policy is disabled for user </b>");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();

            // Enabling Direct Stock Entry Policy For User

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUsers, 5);

            //Opening Store to validate the policy

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            bDirectStockEntryPresentInTransaction = CommonActions.selectOptionFromLeftInInventoryStorePanel(
                    "Transaction", "Direct Stock Entry");
            m_assert.assertTrue(bDirectStockEntryPresentInTransaction,
                    "<b> Direct Stock Entry Feature is present in Transaction as policy is enabled for user </b>");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Inventory Policy for Add Direct Stock Entry Functionality")
    public void validatingAddDirectStockEntryInventoryPolicyFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_DirectStockEntry oPage_DirectStockEntry = new Page_DirectStockEntry(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        try {

            // Disabling Direct Stock Entry Policy For User

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Direct Stock Entry");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            boolean bAddButtonNotFound = false;
            if (!Cls_Generic_Methods.isElementDisplayed(oPage_DirectStockEntry.button_addNew)) {
                bAddButtonNotFound = true;
                m_assert.assertTrue(bAddButtonNotFound,
                        " <b> Add Button is not present in Direct Stock Entry as Policy is Disabled for user </b>");
            }else{
                m_assert.assertTrue(bAddButtonNotFound, " Add Button is present in Direct Stock Entry , Either Policy is not disabled or not working");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();

            // Enabling Direct Stock Entry Policy For User

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Direct Stock Entry");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            boolean bAddButtonFound = false;
            if (Cls_Generic_Methods.isElementDisplayed(oPage_DirectStockEntry.button_addNew)) {
                bAddButtonFound = true;
                m_assert.assertTrue(bAddButtonFound,
                        "<b> Add Button is present in Direct Stock Entry as Policy is Enabled for user </b>");
            }else{
                m_assert.assertTrue(bAddButtonFound, " Add Button is not present in Direct Stock Entry , Either Policy is not Enabled or not working");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Inventory Policy for Approve Direct Stock Entry Functionality")
    public void validatingApproveDirectStockEntryInventoryPolicyFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_DirectStockEntry oPage_DirectStockEntry = new Page_DirectStockEntry(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        try {

            // Disabling Direct Stock Entry Policy For User

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sApprovePolicyComponent, sApprovePolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Direct Stock Entry");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean createDirectStockEntryRecord = false ;
            createDirectStockEntryRecord = addDirectStockEntryForPolicy();

            if(createDirectStockEntryRecord) {
                boolean bDirectStockRecordFound =
                        selectRecordFromListInInventoryStores(oPage_DirectStockEntry.list_directStockEntryTransactions,sStockStatus);
                m_assert.assertTrue(bDirectStockRecordFound,"Direct Stock Entry Record Clicked In Record List");
                if (bDirectStockRecordFound) {
                    boolean bApproveButtonNotFound = false;
                    if (!Cls_Generic_Methods.isElementDisplayed(oPage_DirectStockEntry.button_Approve)) {
                        bApproveButtonNotFound = true;
                        m_assert.assertTrue(bApproveButtonNotFound,
                                "<b> Approve Button is not present in Direct Stock Entry as Policy is Disabled for user </b>");
                    } else {
                        m_assert.assertTrue(bApproveButtonNotFound, " Approve Button is present in Direct Stock Entry , Either Policy is not disabled or not working");
                    }
                } else {
                    m_assert.assertTrue(bDirectStockRecordFound, " Direct Stock Entry Records Not Present in the list , please create one");
                }

                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.switchToOtherTab();

                // Enabling Direct Stock Entry Policy

                CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sApprovePolicyComponent, sApprovePolicyDescription);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Direct Stock Entry");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
                bDirectStockRecordFound = selectRecordFromListInInventoryStores(oPage_DirectStockEntry.list_directStockEntryTransactions,sStockStatus);
                m_assert.assertTrue(bDirectStockRecordFound,"Direct Stock Entry Record Clicked In Record List");;
                if (bDirectStockRecordFound) {
                    boolean bApproveButtonFound = false;
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_DirectStockEntry.button_Approve)) {
                        bApproveButtonFound = true;
                        m_assert.assertTrue(bApproveButtonFound,
                                "<b> Approve Button is present in Direct Stock Entry as Policy is Enabled for user </b> ");
                    } else {
                        m_assert.assertTrue(bApproveButtonFound, "Approve Button is Not present in Direct Stock Entry , Either Policy is not Enabled or not working");
                    }
                } else {
                    m_assert.assertTrue(bDirectStockRecordFound, "Direct Stock Entry Records Not Present in the list , please create one");
                }
            }else{
                m_assert.assertFalse(" Direct Stock Entry Record is not created");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Inventory Policy for Edit Direct Stock Entry Functionality")
    public void validatingEditDirectStockEntryInventoryPolicyFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_DirectStockEntry oPage_DirectStockEntry = new Page_DirectStockEntry(driver);
        Page_Master oPage_Master = new Page_Master(driver);

        try {
            // Disabling Direct Stock Entry Policy For User

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent, sEditPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Direct Stock Entry");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bDirectStockEntryRecordFound = selectRecordFromListInInventoryStores(oPage_DirectStockEntry.list_directStockEntryTransactions,sStockStatus);
            m_assert.assertTrue(bDirectStockEntryRecordFound,"Direct Stock Entry Record Clicked In Record List");

            if(bDirectStockEntryRecordFound) {
                boolean bEditButtonNotFound = false;
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_DirectStockEntry.button_editDirectStockEntryButton)) {
                    bEditButtonNotFound = true;
                    m_assert.assertTrue(bEditButtonNotFound,
                            "<b> Edit Button is not present in Direct Stock Entry as Policy is Disabled for user </b>");
                } else {
                    m_assert.assertTrue(bEditButtonNotFound, " Edit Button is present in Direct Stock Entry , Either Policy is not disabled or not working");
                }
            }else{
                m_assert.assertTrue(bDirectStockEntryRecordFound," Direct Stock Entry Records are not there , please create new");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();

            // Enabling Direct Stock Entry Policy For User

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent, sEditPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Direct Stock Entry");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            bDirectStockEntryRecordFound = selectRecordFromListInInventoryStores(oPage_DirectStockEntry.list_directStockEntryTransactions,sStockStatus);
            m_assert.assertTrue(bDirectStockEntryRecordFound,"Direct Stock Entry Record Clicked In Record List");

            if(bDirectStockEntryRecordFound) {
                boolean bEditButtonFound = false;
                if (Cls_Generic_Methods.isElementDisplayed(oPage_DirectStockEntry.button_editDirectStockEntryButton)) {
                    bEditButtonFound = true;
                    m_assert.assertTrue(bEditButtonFound,
                            "<b> Edit Button is present in Direct Stock Entry as Policy is Enabled for user </b> ");
                } else {
                    m_assert.assertTrue(bEditButtonFound, "Edit Button is Not present in Direct Stock Entry , Either Policy is not Enabled or not working");
                }
            }else{
                m_assert.assertTrue(bDirectStockEntryRecordFound,"Direct Stock Entry Records are not there , please create new");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Inventory Policy for Cancel Direct Stock Entry Functionality")
    public void validatingCancelDirectStockEntryInventoryPolicyFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_DirectStockEntry oPage_DirectStockEntry = new Page_DirectStockEntry(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        try {

            // Disabling Direct Stock Entry Policy For User

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCancelPolicyComponent, sCancelPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Direct Stock Entry");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bDirectStockEntryRecordFound = selectRecordFromListInInventoryStores(oPage_DirectStockEntry.list_directStockEntryTransactions,sStockStatus);
            m_assert.assertTrue(bDirectStockEntryRecordFound,"Direct Stock Entry Record Clicked In Record List");

            if(bDirectStockEntryRecordFound) {
                boolean bCancelButtonNotFound = false;
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_DirectStockEntry.button_cancelDirectStockEntryButton)) {
                    bCancelButtonNotFound = true;
                    m_assert.assertTrue(bCancelButtonNotFound,
                            "<b> Cancel Button is not present in Direct Stock Entry as Policy is Disabled for user </b>");
                } else {
                    m_assert.assertTrue(bCancelButtonNotFound, "Cancel Button is  present in Direct Stock Entry , Either Policy is  Disabled or not working");
                }
            }else{
                m_assert.assertTrue(bDirectStockEntryRecordFound,"Direct Stock Entry Records are not there , please create new");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();

            // Enabling Direct Stock Policy

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCancelPolicyComponent, sCancelPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Direct Stock Entry");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);
            bDirectStockEntryRecordFound = selectRecordFromListInInventoryStores(oPage_DirectStockEntry.list_directStockEntryTransactions,sStockStatus);
            m_assert.assertTrue(bDirectStockEntryRecordFound,"Direct Stock Entry Record Clicked In Record List");

            if(bDirectStockEntryRecordFound) {
                boolean bCancelButtonFound = false;
                if (Cls_Generic_Methods.isElementDisplayed(oPage_DirectStockEntry.button_cancelDirectStockEntryButton)) {
                    bCancelButtonFound = true;
                    m_assert.assertTrue(bCancelButtonFound,
                            " <b> Cancel Button is present in Direct Stock Entry as Policy is Enabled for user </b> ");
                } else {
                    m_assert.assertTrue(bCancelButtonFound, "Cancel Button is Not present in Direct Stock Entry , Either Policy is not Enabled or not working");
                }
            }else{
                m_assert.assertTrue(bDirectStockEntryRecordFound,"Direct Stock Entry Records are not there , please create new");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Inventory Policy for Edit TXN Date & Time Direct Stock Entry Functionality")
    public void validatingEditTxnDateTimeDirectStockEntryInventoryPolicyFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_DirectStockEntry oPage_DirectStockEntry = new Page_DirectStockEntry(driver);
        Page_Master oPage_Master = new Page_Master(driver);


        try {

            // Disabling Direct Stock Entry Policy For User

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTxnDateTimePolicyComponent, sEditTxnDateTimePolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy

            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Direct Stock Entry");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bEditTransactionDateTimeNotFoundInNewTemplate = false;
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_DirectStockEntry.button_addNew),
                    " New Button CLicked in Direct Stock Entry");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DirectStockEntry.input_searchVendorBox,10);

            Cls_Generic_Methods.sendKeysIntoElement(oPage_DirectStockEntry.input_searchVendorBox,"vendor");
            Cls_Generic_Methods.customWait(2);
            Cls_Generic_Methods.clickElement(oPage_DirectStockEntry.text_vendorNameInVendorDropdown);
            Cls_Generic_Methods.customWait(5);


            if (Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_transactionDate, "readonly") != null &&
                    Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_transactionTime, "disabled") != null) {
                bEditTransactionDateTimeNotFoundInNewTemplate = true;
            }
            m_assert.assertTrue(bEditTransactionDateTimeNotFoundInNewTemplate,
                    "<b> Transaction Date and Time Field is Disabled in Direct Stock Entry as Policy is Disabled for user in New Template </b>");

            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
            Cls_Generic_Methods.customWait();

            boolean bDirectStockEntryRecordFound = selectRecordFromListInInventoryStores(oPage_DirectStockEntry.list_directStockEntryTransactions,sStockStatus);
            m_assert.assertTrue(bDirectStockEntryRecordFound,"Direct Stock Entry Record Clicked In Record List");

            if (bDirectStockEntryRecordFound) {

                boolean bEditTransactionDateTimeNotFoundInEditTemplate = false;
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_DirectStockEntry.button_editDirectStockEntryButton), "Edit Button CLicked In Direct Stock Entry");
                Cls_Generic_Methods.customWait(5);

                if (Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_transactionDate, "readonly") != null &&
                        Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_transactionTime, "disabled") != null) {
                    bEditTransactionDateTimeNotFoundInEditTemplate = true;
                }
                m_assert.assertTrue(bEditTransactionDateTimeNotFoundInEditTemplate,
                        "<b> Transaction Date and TIme Field is Disabled in Direct Stock Entry as Policy is Disabled for user in Edit Template </b>");

                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
                Cls_Generic_Methods.customWait();
            } else {
                m_assert.assertTrue(bDirectStockEntryRecordFound, " Direct Stock Entry Records Not present in Direct Stock Entry , please add new");
            }

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.customWait();

            //Enabling Direct Stock Entry policy

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTxnDateTimePolicyComponent, sEditTxnDateTimePolicyDescription);
            Cls_Generic_Methods.customWait(3);

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Direct Stock Entry");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 6);

            boolean bEditTransactionDateTimeFoundInNewTemplate = false;

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_DirectStockEntry.button_addNew), " New Button CLicked in Direct Stock Entry");
            Cls_Generic_Methods.customWait(5);

            Cls_Generic_Methods.sendKeysIntoElement(oPage_DirectStockEntry.input_searchVendorBox,"vendor");
            Cls_Generic_Methods.customWait(2);
            Cls_Generic_Methods.clickElement(oPage_DirectStockEntry.text_vendorNameInVendorDropdown);
            Cls_Generic_Methods.customWait(5);

            if (Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_transactionDate, "readonly") == null &&
                    Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_transactionTime, "disabled") == null) {
                bEditTransactionDateTimeFoundInNewTemplate = true;
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
                Cls_Generic_Methods.customWait(6);
            }

            m_assert.assertTrue(bEditTransactionDateTimeFoundInNewTemplate,
                    "<b> Transaction Date and Time Field is Enabled in Direct Stock Entry as Policy is Enabled for user in New Template </b>");

            bDirectStockEntryRecordFound = selectRecordFromListInInventoryStores(oPage_DirectStockEntry.list_directStockEntryTransactions,sStockStatus);
            m_assert.assertTrue(bDirectStockEntryRecordFound,"Direct Stock Entry Record Clicked In Record List");

            if (bDirectStockEntryRecordFound) {

                boolean bEditTransactionDateTimeFoundInEditTemplate = false;

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_DirectStockEntry.button_editDirectStockEntryButton), "Edit Button CLicked In Direct Stock Entry");
                Cls_Generic_Methods.customWait(5);

                if (Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_transactionDate, "readonly") == null &&
                        Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_transactionTime, "readonly") == null) {
                    bEditTransactionDateTimeFoundInEditTemplate = true;
                   /* Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
                    Cls_Generic_Methods.customWait(6);*/
                }
                m_assert.assertTrue(bEditTransactionDateTimeFoundInEditTemplate,
                        "<b> Transaction Date and Time Field is Enabled in Direct Stock Entry as Policy is Enabled for user in Edit Template </b>");

                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
                Cls_Generic_Methods.customWait();
            } else {
                m_assert.assertTrue(bDirectStockEntryRecordFound, " Direct Stock Entry Records Not present in Direct Stock Entry , please add new");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    public static boolean addDirectStockEntryForPolicy() throws Exception {

        boolean addDirectStockEntry = false ;
        boolean itemAvailable = false ;
        boolean itemClicked = false ;
        Page_DirectStockEntry oPage_DirectStockEntry  = new Page_DirectStockEntry(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

        String transactionNotes = "Transaction_notes_" + getRandomString(4);
        String sTodayDate = Cls_Generic_Methods.getTodayDate("dd/MM/yyyy");
        String billType = "Bill";
        String billNumber = "1111";
        final String subStore = "Default";
        final String sUnitCostWOTax = "100";
        final String packageQuantity = "10";
        final String sellingPrice = "120";


        try{

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DirectStockEntry.button_addNew, 8);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_DirectStockEntry.button_addNew),
                    "New Button clicked in Direct Stock Entry Transaction");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DirectStockEntry.input_searchVendorBox,10);

            Cls_Generic_Methods.sendKeysIntoElement(oPage_DirectStockEntry.input_searchVendorBox,"vendor");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DirectStockEntry.text_vendorNameInVendorDropdown,2);
            Cls_Generic_Methods.clickElement(oPage_DirectStockEntry.text_vendorNameInVendorDropdown);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DirectStockEntry.input_transactionDate, 8);

        }catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        try{

            if(oPage_DirectStockEntry.list_itemNameInStore.size()>0){
                itemAvailable = true ;
            }

        }catch (NoSuchElementException e) {

            m_assert.assertInfo("Item Need To Add");

            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DirectStockEntry.button_addNew, 2);
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            itemAvailable = CommonActions.addItemInInventory(itemDescription);
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Direct Stock Entry");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DirectStockEntry.button_addNew, 8);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_DirectStockEntry.button_addNew),
                    "New Button clicked in Direct Stock Entry Transaction");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DirectStockEntry.input_searchVendorBox,10);

            Cls_Generic_Methods.sendKeysIntoElement(oPage_DirectStockEntry.input_searchVendorBox,"vendor");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DirectStockEntry.text_vendorNameInVendorDropdown,2);
            Cls_Generic_Methods.clickElement(oPage_DirectStockEntry.text_vendorNameInVendorDropdown);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DirectStockEntry.input_transactionDate, 8);
        }

        try{

            m_assert.assertInfo(itemAvailable," Item is available for direct stock entry ");
            for (WebElement eItemName : oPage_DirectStockEntry.list_itemNameInStore) {
                if (Cls_Generic_Methods.isElementDisplayed(eItemName)) {
                    Cls_Generic_Methods.clickElement(eItemName);
                    itemClicked = true ;
                    Cls_Generic_Methods.customWait();
                    break;
                }
            }

            if(itemClicked){

                if (Cls_Generic_Methods.isElementDisplayed(oPage_DirectStockEntry.select_subStore)) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_DirectStockEntry.select_subStore, subStore),
                            "Sub Store: <b> " + subStore + " </b>");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_DirectStockEntry.input_expiryDate)) {
                    String sExpiryDate = Cls_Generic_Methods.getDifferenceInDays(sTodayDate, 60, "dd/MM/yyyy");
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysByJS(driver, oPage_DirectStockEntry.input_expiryDate, sExpiryDate), "Entered expiry date as <b> " + sExpiryDate);
                }
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_DirectStockEntry.input_unitCostWOTax, sUnitCostWOTax),
                        "Unit cost without tax entered as : <b> " + sUnitCostWOTax + "</b>");
                Cls_Generic_Methods.customWait();

                Cls_Generic_Methods.clearValuesInElement(oPage_DirectStockEntry.input_packageQuantity);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_DirectStockEntry.input_packageQuantity, packageQuantity),
                        "package entry entered as : <b> " + packageQuantity + "</b>");
                Cls_Generic_Methods.clearValuesInElement(oPage_DirectStockEntry.input_sellingPrice);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_DirectStockEntry.input_sellingPrice, sellingPrice),
                        "Selling Price entered as : <b> " + sellingPrice + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_DirectStockEntry.button_saveLot), "Saving the Lot details");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DirectStockEntry.button_saveChanges, 8);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_DirectStockEntry.input_transactionNote, transactionNotes),
                        " Transaction Notes Entered as :<b> " + transactionNotes + "</b>");
                Cls_Generic_Methods.clickElement(oPage_DirectStockEntry.select_billType);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_DirectStockEntry.select_billType, billType),
                        "Bill Type Selected:<b> " + billType + " </b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_DirectStockEntry.input_billNumber, billNumber),
                        "Bill Number: <b> " + billNumber + " </b>");

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysByJS(driver, oPage_DirectStockEntry.input_billDate, sTodayDate),
                        "Date of bill selected:<b> " + sTodayDate + " </b>");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_DirectStockEntry.button_saveChanges), "Clicked save changes and created direct stock entry transaction");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DirectStockEntry.button_addNew, 8);
                if(oPage_DirectStockEntry.list_directStockEntryTransactions.size()>0) {
                    addDirectStockEntry = true;
                }
            }
            else{
                m_assert.assertFalse("Item Not clicked in Direct Stock Entry Template");
            }


        }catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }


        return addDirectStockEntry;
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
