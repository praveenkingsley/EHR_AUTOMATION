package tests.authorizationpolicy.inventory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_PurchaseReturn;
import pages.store.PharmacyStore.Transaction.Page_SaleReturn;

public class InventoryPolicySalesReturnTest extends TestBase {
    String sPolicyRequired = "INVENTORY";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    static String sStore = "Pharmacy automation- Pharmacy";
    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_Master oPage_Master;
    Page_SaleReturn oPage_SaleReturn;
    String patientName="TEST1";


    String sViewPolicyComponent = "VIEW (SALE RETURN)";
    String sAddPolicyComponent = "ADD (SALE RETURN)";
    String sReceivePolicyComponent = "RECEIVE (SALE RETURN)";
    String sReturnTxnDatePolicyComponent = "EDIT SALE RETURN DATE (SALE RETURN)";

    String sViewPolicyDescription = "Inventory Sale Return View Access";
    String sAddPolicyDescription = "Inventory Sale Return Creation Access";
    String sReceivePolicyDescription = "Inventory sale Return Stock In/Receive Access";
    String sReturnTxnDatePolicyDescription = "Inventory Sale Return Transaction Date Edit Access";


    @BeforeMethod
    private void init() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
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

    @Test(enabled = true, description = "Validate View Sales Return inventory policy")
    public void validateViewSalesReturnPolicy() {
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
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            boolean bSaleReturnTab = CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale Return");
            m_assert.assertFalse(bSaleReturnTab, "Validated --> Inventory Sale Return View Access--> Sale Return Tab is disabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            bSaleReturnTab = CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale Return");
            m_assert.assertTrue(bSaleReturnTab, "Validated --> Inventory Sale Return View Access--> Sale Return Tab is enabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate view Sale Return policy " + e);
        }
    }
    @Test(enabled = true, description = "Validate Add Sales return inventory policy")
    public void validateAddSalesReturnPolicy() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_SaleReturn=new Page_SaleReturn(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale Return");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SaleReturn.button_new, 3);
            boolean bNewSaleReturn = Cls_Generic_Methods.isElementDisplayed(oPage_SaleReturn.button_new);
            m_assert.assertFalse(bNewSaleReturn, "Validated --> Inventory Sale Return Creation Access--> New Sale Return button is disabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale Return");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SaleReturn.button_new, 3);
            bNewSaleReturn = Cls_Generic_Methods.isElementDisplayed(oPage_SaleReturn.button_new);
            m_assert.assertTrue(bNewSaleReturn, "Validated --> Inventory Sale Return Creation Access--> New Sale Return button is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Add Sale Return policy " + e);
        }
    }
    @Test(enabled = true, description = "Validate Receive Sales return inventory policy")
    public void validateReceiveSalesReturnPolicy() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_SaleReturn=new Page_SaleReturn(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sReceivePolicyComponent, sReceivePolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale Return");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SaleReturn.button_new, 3);
            createSaleReturn();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SaleReturn.button_receive,3);
            boolean bReceiveButton = Cls_Generic_Methods.isElementDisplayed(oPage_SaleReturn.button_receive);
            m_assert.assertFalse(bReceiveButton, "Validated --> Inventory sale Return Stock In/Receive Access--> Receive button is disabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sReceivePolicyComponent, sReceivePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale Return");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SaleReturn.button_receive,10);
            bReceiveButton = Cls_Generic_Methods.isElementDisplayed(oPage_SaleReturn.button_receive);
            m_assert.assertTrue(bReceiveButton, "Validated --> Inventory sale Return Stock In/Receive Access--> Receive button is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Receive Sale Return policy " + e);
        }
    }

    @Test(enabled = true, description = "Validate Return Txn Date Sales return inventory policy")
    public void validateReturnTxnDateSalesReturnPolicy() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_SaleReturn=new Page_SaleReturn(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sReturnTxnDatePolicyComponent, sReturnTxnDatePolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale Return");
            boolean returnTxnDateIsEditable = checkTransactionDateTimeIsEditable();
            m_assert.assertFalse(returnTxnDateIsEditable, "Validated --> Inventory Sale Return Transaction Date Edit Access --> Sale Return date edit is disabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sReturnTxnDatePolicyComponent, sReturnTxnDatePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale Return");
            returnTxnDateIsEditable = checkTransactionDateTimeIsEditable();
            m_assert.assertTrue(returnTxnDateIsEditable, "Validated --> Inventory Sale Return Transaction Date Edit Access -->  Sale Return date edit is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Return Txn Date Sale Return policy " + e);
        }
    }

    public boolean checkTransactionDateTimeIsEditable() {
        boolean flag = false;

        oPage_SaleReturn=new Page_SaleReturn(driver);
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SaleReturn.button_new, 10);
            Cls_Generic_Methods.clickElement(oPage_SaleReturn.button_new);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SaleReturn.input_searchPatient,10);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_SaleReturn.input_searchPatient,patientName);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_SaleReturn.button_searchPatient);
            Cls_Generic_Methods.customWait();

            for (WebElement patientRow:oPage_SaleReturn.list_searchedPatientList){
                String value=Cls_Generic_Methods.getTextInElement(patientRow);

                if(value.contains(patientName)){
                    Cls_Generic_Methods.clickElement(patientRow);
                    break;
                }
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SaleReturn.input_returnNote,10);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SaleReturn.input_dateSaleReturn, 10);
            try {
                String readOnlyValueDate = Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_dateSaleReturn, "readonly");
                if (!readOnlyValueDate.isEmpty()) {
                    flag = false;
                }
            } catch (NullPointerException e) {
                flag = true;
            }

        } catch (Exception e) {
            m_assert.assertFatal("Unable to check transaction time editable status " + e);
        }
        return flag;
    }
    public void createSaleReturn(){
        oPage_SaleReturn=new Page_SaleReturn(driver);

        try{
            Cls_Generic_Methods.clickElement(oPage_SaleReturn.button_new);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SaleReturn.input_searchPatient,10);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_SaleReturn.input_searchPatient,patientName);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_SaleReturn.button_searchPatient);
            Cls_Generic_Methods.customWait();

            for (WebElement patientRow:oPage_SaleReturn.list_searchedPatientList){
                String value=Cls_Generic_Methods.getTextInElement(patientRow);

                if(value.contains(patientName)){
                    Cls_Generic_Methods.clickElement(patientRow);
                    break;
                }
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SaleReturn.input_returnNote,10);

            for (WebElement itemRow:oPage_SaleReturn.list_itemRowRequest) {
                Cls_Generic_Methods.clickElement(itemRow);
                break;
            }
            Cls_Generic_Methods.customWait(3);
            boolean bLot=Cls_Generic_Methods.isElementDisplayed(oPage_SaleReturn.checkbox_lot);
            if(bLot){
                Cls_Generic_Methods.clickElementByJS(driver,oPage_SaleReturn.checkbox_lot);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_SaleReturn.button_saveChangesLot);
            }else{
                Cls_Generic_Methods.sendKeysIntoElement(oPage_SaleReturn.input_itemQuantity,"1");
            }
            Cls_Generic_Methods.customWait(4);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_SaleReturn.input_returnNote,"TEST");
            Cls_Generic_Methods.clickElementByJS(driver,oPage_SaleReturn.checkbox_verified);
            Cls_Generic_Methods.selectElementByVisibleText(oPage_SaleReturn.select_returnMop,"Cash");
            Cls_Generic_Methods.customWait();
            String netReturnValueUIAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_netReturnAmountInReturn,"value");
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SaleReturn.input_returnRefundAmount,netReturnValueUIAfterDiscount),
                    "Enter Refund Amount equal to Net Return Amount as : "+netReturnValueUIAfterDiscount);
            Cls_Generic_Methods.customWait(1);
            Cls_Generic_Methods.clickElement(oPage_SaleReturn.button_saveChanges);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SaleReturn.button_close,10);
            Cls_Generic_Methods.clickElement(oPage_SaleReturn.button_close);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
