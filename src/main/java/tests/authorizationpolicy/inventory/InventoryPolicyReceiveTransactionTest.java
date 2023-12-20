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
import pages.store.PharmacyStore.Page_TaxInvoiceDeliveryChallan;
import pages.store.PharmacyStore.Transaction.Page_Receive;
import pages.store.PharmacyStore.Transaction.Page_Transfer;

import java.util.List;

public class InventoryPolicyReceiveTransactionTest extends TestBase {

    String sPolicyRequired = "INVENTORY";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    static String sStore = "Pharmacy automation- Pharmacy";
    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_Master oPage_Master;
    Page_Receive oPage_Receive;


    String sViewPolicyComponent = "VIEW (RECEIVE TRANSACTION)";
    String sReceivePolicyComponent = "RECEIVE (RECEIVE TRANSACTION)";

    String sViewPolicyDescription = "Inventory Receive Transaction View Access";
    String sReceivePolicyDescription = "Inventory Receive Transaction Stock Receive Access";


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

    @Test(enabled = true, description = "Validate View Receive Transaction inventory policy")
    public void validateViewReceiveTransactionPolicy() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Receive = new Page_Receive(driver);

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
            boolean bReceiveTab = CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Receive");
            m_assert.assertFalse(bReceiveTab, "Validated --> Inventory Receive Transaction View Access--> Receive Tab is disabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            bReceiveTab = CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Receive");
            m_assert.assertTrue(bReceiveTab, "Validated --> Inventory Receive Transaction View Access--> Receive Tab is enabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate view Receive policy " + e);
        }
    }

    @Test(enabled = true, description = "Validate Receive Transaction inventory policy")
    public void validateReceiveStockPolicy() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Receive = new Page_Receive(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sReceivePolicyComponent, sReceivePolicyDescription);
            Cls_Generic_Methods.customWait(3);
            createDirectTransfer();
            //Opening store to validate the policy

            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Receive");
            Cls_Generic_Methods.customWait(5);
            selectReceiveTransaction();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.button_receiveStock,10);
            boolean bReceiveButton =Cls_Generic_Methods.isElementDisplayed(oPage_Receive.button_receiveStock);
            m_assert.assertFalse(bReceiveButton, "Validated --> Inventory Receive Transaction Stock Receive Access--> Receive Stock Button is disabled in store");
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sReceivePolicyComponent, sReceivePolicyDescription);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Receive");
            Cls_Generic_Methods.customWait(5);
            selectReceiveTransaction();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.button_receiveStock,10);
            bReceiveButton =Cls_Generic_Methods.isElementDisplayed(oPage_Receive.button_receiveStock);

            m_assert.assertTrue(bReceiveButton, "Validated --> Inventory Receive Transaction Stock Receive Access--> Receive Stock Button is enabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Receive Stock policy " + e);
        }
    }

    public void createDirectTransfer() {

        oPage_CommonElements = new Page_CommonElements(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        Page_TaxInvoiceDeliveryChallan oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);
        String sItemDescription = "Luxturna";
        String sQuantity = "1";
        String sTransferStore = "OpticalStore- Optical";

        try {
            CommonActions.selectStoreOnApp(sTransferStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(2);
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_newTransaction, 5);
            Cls_Generic_Methods.clickElement(oPage_Transfer.button_newTransaction);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.dropdown_receivingStore, 5);
            Cls_Generic_Methods.clickElement(oPage_Transfer.dropdown_receivingStore);


            Cls_Generic_Methods.customWait(3);

            for (WebElement eReceivingStore : oPage_Transfer.list_receivingStore) {

                if (Cls_Generic_Methods.getTextInElement(eReceivingStore).equalsIgnoreCase(sStore.split("-")[0])) {
                    Cls_Generic_Methods.clickElement(eReceivingStore);
                    break;
                }
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_searchByDescription, 5);
            Cls_Generic_Methods.clickElement(oPage_Transfer.button_searchByDescription);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItemToBeTransferred, 5);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_searchByDescription, 5);
            Cls_Generic_Methods.clickElement(oPage_Transfer.button_searchByDescription);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItemToBeTransferred, 5);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_searchItemToBeTransferred, sItemDescription);
            Cls_Generic_Methods.customWait(1);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_searchItemToBeTransferred, " ");
            Cls_Generic_Methods.customWait(3);

            for (WebElement eItemVariantCode : oPage_Transfer.list_itemDescriptionRow) {
                if (Cls_Generic_Methods.getTextInElement(eItemVariantCode).equalsIgnoreCase(sItemDescription)) {
                    Cls_Generic_Methods.clickElement(eItemVariantCode);
                    break;
                }
            }

            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.list_quantityFieldForItemsToTransfer.get(0), sQuantity);
            Cls_Generic_Methods.customWait();

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_saveChanges), "Transfer Transaction saved");
            Cls_Generic_Methods.customWait(6);


            for (WebElement row : oPage_Transfer.list_transferTransactionRow) {
                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    Cls_Generic_Methods.clickElement(row);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_approveTransfer, 10);
                    Cls_Generic_Methods.clickElement(oPage_Transfer.button_approveTransfer);
                    Cls_Generic_Methods.customWait();
                    if(Cls_Generic_Methods.isElementDisplayed(oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan)) {
                        createTaxInvoiceDeliveryChallanWithMandatoryField("TAX INVOICE");
                    }
                    break;
                }else{
                    m_assert.assertFatal("Unable to find transfer transaction");
                }
            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertInfo("Unable to create Transfer " + e);
        }

    }

    public void createTaxInvoiceDeliveryChallanWithMandatoryField(String transactionType) {

        Page_TaxInvoiceDeliveryChallan oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);

        try {
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);

            Cls_Generic_Methods.customWait(2);
            Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_new, 5);

            //Create New

            Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_new);
            Cls_Generic_Methods.customWait();

            if (transactionType.equalsIgnoreCase("DELIVERY CHALLAN")) {
                Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_deliveryChallan);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_headerCreateDeliveryChallan, 10);

            } else if (transactionType.equalsIgnoreCase("TAX INVOICE")) {
                Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_taxInvoice);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_headerCreateTaxInvoice, 10);
            } else {
                m_assert.assertFalse("Enter Valid Transaction Type");
            }

            //Select To store

            selectByOptions(oPage_TaxInvoiceDeliveryChallan.select_againstStore, sStore.split("-")[0]);
            Cls_Generic_Methods.customWait(5);

            for (WebElement lhsRow : oPage_TaxInvoiceDeliveryChallan.row_lhsCreateTaxInvoiceDeliveryChallan) {
                Cls_Generic_Methods.clickElement(lhsRow);
                break;
            }

            //Dispatch Details

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.sendKeysIntoElement(oPage_TaxInvoiceDeliveryChallan.select_transportationMode, "Test");
            Cls_Generic_Methods.sendKeysIntoElement(oPage_TaxInvoiceDeliveryChallan.input_transactionId, "1234");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_saveChanges);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_refresh,10);

            Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_refresh);

            Cls_Generic_Methods.customWait();
            for (WebElement row : oPage_TaxInvoiceDeliveryChallan.list_transactionCreatedList) {
                Cls_Generic_Methods.clickElement(row);
                break;
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_approve, 10);
            Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_approve);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to create Tax Invoice -> " + e);
        }

    }

    public boolean selectByOptions(WebElement selectElement, String optionToSelect) {
        boolean status = false;
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(selectElement, 10);
            Cls_Generic_Methods.clickElementByJS(driver, selectElement);
            List<WebElement> allOptions = selectElement.findElements(By.xpath("./option"));
            for (WebElement option : allOptions) {
                String optionValue = Cls_Generic_Methods.getTextInElement(option);
                if (optionValue.contains((optionToSelect))) {
                    Cls_Generic_Methods.clickElement(option);
                    status = true;
                    break;
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to select " + optionToSelect + " -->" + e);
        }
        return status;
    }

    public void selectReceiveTransaction(){
        try{
            for (WebElement row : oPage_Receive.list_text_transactionIdRow) {
                Cls_Generic_Methods.clickElement(row);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
