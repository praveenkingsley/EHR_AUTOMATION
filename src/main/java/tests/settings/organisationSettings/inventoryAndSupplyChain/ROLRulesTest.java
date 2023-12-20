package tests.settings.organisationSettings.inventoryAndSupplyChain;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.settings.Page_Settings;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ROLRules;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Order.Page_Requisition;
import pages.store.PharmacyStore.Order.Page_RequisitionReceived;
import pages.store.PharmacyStore.Order.Page_SalesOrder;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_Receive;
import pages.store.PharmacyStore.Transaction.Page_Transfer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
ROL Order Validation
Creating test cases as mentioned in the document -
https://docs.google.com/spreadsheets/d/1V5STteAv6DH_F8wYe3LfU02zp267fgxg_yR1yDMuolQ/edit#gid=0
*/

public class ROLRulesTest extends TestBase {

    public static boolean bEnableDebugMode = false;

    // ROL Calculations
    public static int iPresentQuantity = 0;
    public static int iPendingTransferredQuantity = 0;
    public static int iRequestedQuantity = 0;
    public static int iEffectiveStock = 0;

    // ROL Item Details
    public static String sROL_ITEM = "testDemoMed";
    public static int iROL_ITEM_VALUE = 50;
    public static int iROL_ITEM_MAX_VALUE = 100;
    public static int iROL_ITEM_MIN_VALUE = 10;

    public static String sROL_STORE = "Automation_Store Updated- Pharmacy";
    public static String sCENTRAL_HUB = "CENTRAL HUB 01- Central Hub";
    public static String sVENDOR_NAME = "Supplier ABC";


    public static int i_CENTRAL_HUB_ISSUE_QTY_1 = 20;
    public static int i_CENTRAL_HUB_ISSUE_QTY_2 = 50;

    public static int i_1st_SALE_QUANTITY = 5;
    public static int i_2ND_SALE_QUANTITY = 50;
    public static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");



    public static Map<String, String> mapTracker = new HashMap<String, String>();
    public static String key_CreatedAt_RequisitionOrderFromStore = "key_CreatedAt_RequisitionOrderFromStore";
    public static String key_CreatedAt_ItemPurchaseInCentralHub = "key_CreatedAt_ItemPurchaseInCentralHub";
    public static String key_NewTransactionInRequisitionReceived_1 = "key_NewTransactionInRequisitionReceived_1";
    public static String key_NewTransactionInRequisitionReceived_2 = "key_NewTransactionInRequisitionReceived_2";


    @Test(enabled = true, description = "1, 2, 3, 4 - Validate assigning rol, max, min value in negative and positive scenario")
    public void assignROLRule() {
        // 1	Create new ROL item
        // 2	create rol rule
        // 3	edit rol rule(negative scenario)
        // 4	edit rol rule(positive scenario)

        Page_ROLRules oPage_ROLRules = new Page_ROLRules(driver);
        Page_Settings oPage_Settings = new Page_Settings(driver);

        int indexOfItemName = -1;
        int indexOfItemInTable = -1;
        int negativeRolValue = 50;
        int negativeRolMaxValue = 10;
        int negativeRolMinValue = 10;

        boolean itemFound = false;
        boolean itemFoundInTable = false;
        boolean negativeScenarioStatus = false;

        initializationStep();

        try  {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Settings.list_ParentOptionsOnLeft.get(0), 16);

            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "ROL Rules");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ROLRules.button_AddROLRule, 10);
            Cls_Generic_Methods.scrollToTop();
            Cls_Generic_Methods.clickElementByJS(driver, oPage_ROLRules.button_AddROLRule);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ROLRules.header_AddRolRules, 10);

            try {
                Cls_Generic_Methods.clickElement(oPage_ROLRules.select_RolStore);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_ROLRules.select_RolStore, "Automation_Store Updated");
                Cls_Generic_Methods.customWait(10);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ROLRules.input_searchItemName, 8);
                Cls_Generic_Methods.doubleClickElement(oPage_ROLRules.input_searchItemName);
                Cls_Generic_Methods.sendKeysByAction(oPage_ROLRules.input_searchItemName, sROL_ITEM);
                Cls_Generic_Methods.customWait(4);

                for (WebElement eItemName : oPage_ROLRules.text_ItemVariantName) {
                    if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(sROL_ITEM)) {
                        indexOfItemName = oPage_ROLRules.text_ItemVariantName.indexOf(eItemName);
                        itemFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(itemFound, "Found Item Variant Name: <b> " + sROL_ITEM + " at index: " + indexOfItemName + " </b> ");

                if (itemFound) {
                    //negative scenario
                    Cls_Generic_Methods.clearValuesInElement(oPage_ROLRules.input_RolItemValue.get(indexOfItemName));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ROLRules.input_RolItemValue.get(indexOfItemName), String.valueOf(negativeRolValue)),
                            "Entered ROL Value for negative scenario: <b> " + negativeRolValue + " </b> ");

                    Cls_Generic_Methods.clearValuesInElement(oPage_ROLRules.input_RolItemMaxValue.get(indexOfItemName));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ROLRules.input_RolItemMaxValue.get(indexOfItemName), String.valueOf(negativeRolMaxValue)),
                            "Entered MAX Value for negative scenario:<b>  " + negativeRolMaxValue + " </b> ");

                    Cls_Generic_Methods.clearValuesInElement(oPage_ROLRules.input_RolItemMinValue.get(indexOfItemName));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ROLRules.input_RolItemMinValue.get(indexOfItemName), String.valueOf(negativeRolMinValue)),
                            "Entered MIN Value for negative scenario:<b>  " + negativeRolMinValue + " </b> ");
                    Cls_Generic_Methods.clickElement(oPage_ROLRules.button_SaveRol);
                    Cls_Generic_Methods.customWait(7);

                    // REVIEW COMMENTS
                    if(Cls_Generic_Methods.getElementAttribute(oPage_ROLRules.input_RolItemValue.get(indexOfItemName), "class").contains("error") &&
                            Cls_Generic_Methods.getElementAttribute(oPage_ROLRules.input_RolItemMaxValue.get(indexOfItemName), "class").contains("error")){
                        negativeScenarioStatus = true;
                    }
                    m_assert.assertTrue(negativeScenarioStatus, "Values are not able to save as ROL value is greater then Max value. ");

                    // Positive scenario
                    Cls_Generic_Methods.clearValuesInElement(oPage_ROLRules.input_RolItemValue.get(indexOfItemName));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ROLRules.input_RolItemValue.get(indexOfItemName), String.valueOf(iROL_ITEM_VALUE)),
                            "Entered ROL Value: <b> " + iROL_ITEM_VALUE + " </b> ");

                    Cls_Generic_Methods.clearValuesInElement(oPage_ROLRules.input_RolItemMaxValue.get(indexOfItemName));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ROLRules.input_RolItemMaxValue.get(indexOfItemName), String.valueOf(iROL_ITEM_MAX_VALUE)),
                            "Entered MAX Value:<b>  " + iROL_ITEM_MAX_VALUE + " </b> ");

                    Cls_Generic_Methods.clearValuesInElement(oPage_ROLRules.input_RolItemMinValue.get(indexOfItemName));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ROLRules.input_RolItemMinValue.get(indexOfItemName), String.valueOf(iROL_ITEM_MIN_VALUE)),
                            "Entered MIN Value:<b>  " + iROL_ITEM_MIN_VALUE + " </b> ");
                }

                Cls_Generic_Methods.clickElement(oPage_ROLRules.button_SaveRol);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ROLRules.button_AddROLRule, 16);

            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }

            // Validate in table
            Cls_Generic_Methods.customWait(1);
            oPage_ROLRules = new Page_ROLRules(driver);
            for (WebElement eItemTable : oPage_ROLRules.list_itemNameTable) {
                if (Cls_Generic_Methods.getTextInElement(eItemTable).equalsIgnoreCase(sROL_ITEM)) {
                    indexOfItemInTable = oPage_ROLRules.list_itemNameTable.indexOf(eItemTable);
                    itemFoundInTable = true;
                    break;
                }
            }

            m_assert.assertTrue(itemFoundInTable, "Item name found in table: <b> " + sROL_ITEM + " at index " + (indexOfItemInTable + 1) + " </b> ");
            Cls_Generic_Methods.customWait();
            if (itemFoundInTable) {
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_ROLRules.list_rolValueTable.get(indexOfItemInTable)).equals(String.valueOf(iROL_ITEM_VALUE)),
                        "ROL Value found in table:<b> " + iROL_ITEM_VALUE + " </b> ");

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_ROLRules.list_maxValueTable.get(indexOfItemInTable)).equals(String.valueOf(iROL_ITEM_MAX_VALUE)),
                        "MAX Value found in table:<b> " + iROL_ITEM_MAX_VALUE + " </b> ");

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_ROLRules.list_minValueTable.get(indexOfItemInTable)).equals(String.valueOf(iROL_ITEM_MIN_VALUE)),
                        "MIN Value found in table:<b> " + iROL_ITEM_MIN_VALUE + " </b> ");
            }

            logAllQuantities();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "5, 6, 7 - Create requisition for rol item and approve the same in pharmacy store")
    public void validateROLItemRequisitionInStoreAndApproveOrder() {
        // 5	open store >> order >> requisition
        // 6	validate autofill quantity should be max rol
        // 7	view order >> approve the order

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        Page_ROLRules oPage_ROLRules = new Page_ROLRules(driver);
        Page_Requisition oPage_Requisition = new Page_Requisition(driver);

        boolean itemFoundInRequisition = false;
        boolean quantityDefinedAsMaxValue = false;
        boolean bRequisitionOrderFound = false;
        boolean receivingStoreFound = false;

        String sRequisitionType = "Normal";
        String requisitionOrderTime = "";
        String requisitionOrderDate = "";

        initializationStep();

        try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sROL_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_newRequisition, 3);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_newRequisition),
                        "New Button clicked in Order Requisition");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.dropdown_receivingStoreInRequisition, 3);


                //Select receiving store
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.dropdown_receivingStoreInRequisition),
                        "Receiving Store Dropdown clicked");
                for (WebElement receivingStore : oPage_Requisition.list_storesListInReceivingStoresRequisition) {
                    if (sCENTRAL_HUB.contains(Cls_Generic_Methods.getTextInElement(receivingStore))) {
                        Cls_Generic_Methods.clickElement(receivingStore);
                        receivingStoreFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(receivingStoreFound, "Receiving store selected: <b>" + sCENTRAL_HUB + " </b>");
                Cls_Generic_Methods.customWait();

                // Select requisition type
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.dropdown_requisitionType),
                        "Requisition Type Dropdown clicked");
                for (WebElement eType : oPage_Requisition.list_requisitionType) {
                    oPage_Requisition = new Page_Requisition(driver);
                    if (sRequisitionType.contains(Cls_Generic_Methods.getTextInElement(eType))) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(eType),
                                "Requisition Type selected: <b> " + sRequisitionType + " </b>");
                        Cls_Generic_Methods.customWait(3);
                        break;
                    }
                }

                // find rol item from list table
                for (WebElement eItemName : oPage_Requisition.list_itemNameInPurchaseStore) {
                    if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(sROL_ITEM)) {
                        Cls_Generic_Methods.clickElement(eItemName);
                        itemFoundInRequisition = true;
                        break;
                    }
                }

                m_assert.assertTrue(itemFoundInRequisition, "Item found in requisition: <b> " + sROL_ITEM + "</b>");

                if (itemFoundInRequisition) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ROLRules.input_quantityRequisition, 5);

                    //getting rol_date and rol_time value
                    requisitionOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderTime, "value");
                    requisitionOrderTime = requisitionOrderTime.replaceAll("\\s+", "");
                    requisitionOrderTime = CommonActions.getRequiredFormattedDateTime("K:mma","hh:mma",requisitionOrderTime);
                    requisitionOrderTime = requisitionOrderTime.replace("am", "AM").replace("pm","PM");
                    m_assert.assertTrue("Requisition order time:<b> " + requisitionOrderTime + "</b>");

                    requisitionOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderDate, "value");
                    requisitionOrderDate = requisitionOrderDate.replaceAll("/", "-");
                    m_assert.assertTrue("Requisition order date:<b> " + requisitionOrderDate + "</b>");

                    mapTracker.put(key_CreatedAt_RequisitionOrderFromStore, requisitionOrderDate + "  |  " + requisitionOrderTime);

                    //verifying rol_max value
                    String quantityOfItem = Cls_Generic_Methods.getElementAttribute(oPage_ROLRules.input_quantityRequisition, "value");
                    if (quantityOfItem.equalsIgnoreCase(String.valueOf(iROL_ITEM_MAX_VALUE))) {
                        quantityDefinedAsMaxValue = true;
                    }

                    m_assert.assertTrue(quantityDefinedAsMaxValue, "Max Value defined is visible in Quantity Requisition:<b> " + iROL_ITEM_MAX_VALUE + "</b>");

                    // =======================================================================================================================================
                    // =======================================================================================================================================
                    iRequestedQuantity = iROL_ITEM_MAX_VALUE;
                    logAllQuantities();
                    // =======================================================================================================================================
                    // =======================================================================================================================================


                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_saveRequisition),
                            "Requisition saved");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_newRequisition, 5);
                }

                for (WebElement eDate : oPage_Requisition.list_dateTimeOfRequisition) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate);

                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();

                    if(mapTracker.get(key_CreatedAt_RequisitionOrderFromStore).contains(date) &&
                            mapTracker.get(key_CreatedAt_RequisitionOrderFromStore).contains(time)){
                        bRequisitionOrderFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        break;
                    }


                }
                m_assert.assertTrue(bRequisitionOrderFound, "Requisition order found in the requisition page");

                if (bRequisitionOrderFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 5);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_viewOrderRequisition),
                            "View Order clicked for requisition");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_approveRequisition, 15);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_approveRequisition),
                            "Approve requisition Order clicked");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_confirmRequisition, 15);

                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Requisition.button_confirmRequisition)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_confirmRequisition),
                                "<b> Requisition Order Confirmed clicked </b> ");
                    }
                    Cls_Generic_Methods.customWait(5);
                    Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

                }

            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "8, 9 - Validate Purchase the rol item in central hub")
    public void validateItemPurchaseInCentralHubAndApprove() {
        // 8	go in central hub
        // 9	purchase new item

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);

        boolean vendorFound = false;
        boolean itemFoundInPurchase = false;
        boolean bPurchaseTransactionFound = false;

        String costPrice = "10.0";
        String sellingPrice = "10.0";
        String packageQuantity = "200";
        String subStore = "Default";
        String billType = "Bill";
        String billNumber = "1111";
        String purchaseTransactionTime = "";
        String purchaseTransactionDate = "";

        initializationStep();

        try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sCENTRAL_HUB);

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store popup closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 3);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_purchaseNew),
                        "New Button clicked in purchase transaction");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.dropdown_selectVendorInStore, 5);
                Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectVendorInStore);

                for (WebElement eVendor : oPage_Purchase.list_selectVendorInStore) {
                    if (Cls_Generic_Methods.getTextInElement(eVendor).equalsIgnoreCase(sVENDOR_NAME)) {
                        Cls_Generic_Methods.clickElement(eVendor);
                        vendorFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(vendorFound, "Vendor present in dropdown: <b> " + sVENDOR_NAME + "</b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);

                for (WebElement eItemName : oPage_Purchase.list_itemNameInPurchaseStore) {
                    if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(sROL_ITEM)) {
                        Cls_Generic_Methods.clickElement(eItemName);
                        itemFoundInPurchase = true;
                        break;
                    }
                }

                m_assert.assertTrue(itemFoundInPurchase, "Item found in purchase: <b> " + sROL_ITEM + "</b>");

                if (itemFoundInPurchase) {

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewLot, 15);

                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_subStore, subStore),
                            "Sub Store: <b> " + subStore + " </b>");

                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_unitCostWOTax);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_unitCostWOTax, costPrice),
                            "Cost price input: <b> " + costPrice + " </b>");

                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_packageQuantity);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_packageQuantity, packageQuantity),
                            "Package quantity: <b> " + packageQuantity + " </b>");

                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_sellingPrice);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_sellingPrice, sellingPrice),
                            "Selling price input: <b> " + sellingPrice + " </b>");
                    Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveLot);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);

                    Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType),
                            "Bill Type Selected:<b> " + billType + " </b>");
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber),
                            "Bill Number: <b> " + billNumber + " </b>");
                    Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                            "Date of bill selected:<b> " + oPage_Purchase.input_todayBillDate.getText() + " </b>");

                    //getting purchase date and time value
                    purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                    purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                    purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma","hh:mma",purchaseTransactionTime);
                    purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm","PM");
                    m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");

                    purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                    purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);

                    mapTracker.put(key_CreatedAt_ItemPurchaseInCentralHub, purchaseTransactionDate + "  |  " + purchaseTransactionTime);

                    m_assert.assertTrue("Purchase Transaction date:<b> " + purchaseTransactionDate + "</b>");

                    Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 4);

                }else{
                    m_assert.assertTrue(false, "Item not found in Purchase in Central Hub");
                }

                //setting date time in format
                for (WebElement eDateTime : oPage_Purchase.list_dateTimeOfPurchase) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDateTime);

                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();

                    if (mapTracker.get(key_CreatedAt_ItemPurchaseInCentralHub).contains(date) &&
                            mapTracker.get(key_CreatedAt_ItemPurchaseInCentralHub).contains(time)) {
                        bPurchaseTransactionFound = true;
                        Cls_Generic_Methods.clickElement(eDateTime);
                        break;
                    }
                }

                m_assert.assertTrue(bPurchaseTransactionFound, "Purchase Transaction Found");
                if (bPurchaseTransactionFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approveTransaction, 5);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_approveTransaction),
                            "Approve Purchase clicked");
                    Cls_Generic_Methods.customWait(1);
                }

                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "10, 11, 12 - Validate requisition received in central hub and approve the transfer of item from central hub")
    public void validateOrderRequisitionReceivedInCentralHub() {
        // 10	order >> requisition received
        // 11	transfer items
        // 12	Approve transfer from central hub

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);

        boolean bRequisitionReceivedFound = false;
        boolean bTransferEntryFound = false;

        initializationStep();

        try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sCENTRAL_HUB);

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Central hub popup closed");
            Cls_Generic_Methods.customWait();

            try {

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition Received");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
                Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_downArrowForToday);
                CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, "This Month");
                Cls_Generic_Methods.customWait();

//                m_assert.assertInfo("Requisition Order created at : <b> " + mapTracker.get(key_CreatedAt_RequisitionOrderFromStore) + " </b> ");

                for (WebElement eDate : oPage_RequisitionReceived.list_dateTimeOfRequisitionReceived) {
                    Cls_Generic_Methods.clickElement(eDate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 4);

                    String createdAt = Cls_Generic_Methods.getTextInElement(oPage_RequisitionReceived.text_dateTimeCreatedAt);
                    createdAt = createdAt.split("Created AT:")[1].trim();

                    String dateNdTime[] = createdAt.split("\\|");
                    String date = dateNdTime[0];
                    String time = dateNdTime[1];

                    if (mapTracker.get(key_CreatedAt_RequisitionOrderFromStore).contains(date) &&
                            mapTracker.get(key_CreatedAt_RequisitionOrderFromStore).contains(time)) {
                        bRequisitionReceivedFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(bRequisitionReceivedFound, "Requisition Received found");

                if (bRequisitionReceivedFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_newTransactionRequisition),
                            "New transaction button clicked");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.list_transferItem, 15);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.list_transferItem),
                            "Transfer item clicked: <b> " + oPage_RequisitionReceived.list_transferItem.getText() + "</b>");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.input_issueQuantity, 10);
                    Cls_Generic_Methods.clearValuesInElement(oPage_RequisitionReceived.input_issueQuantity);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RequisitionReceived.input_issueQuantity, String.valueOf(i_CENTRAL_HUB_ISSUE_QTY_1)),
                            "Transfer quantity: <b> " + i_CENTRAL_HUB_ISSUE_QTY_1 + "</b>");

                    // =======================================================================================================================================
                    // =======================================================================================================================================
                    iPendingTransferredQuantity = i_CENTRAL_HUB_ISSUE_QTY_1;
                    iRequestedQuantity = iRequestedQuantity - iPendingTransferredQuantity;
                    logAllQuantities();
                    // =======================================================================================================================================
                    // =======================================================================================================================================

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_confirmTransfer),
                            "Item Transfer confirmed");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.list_transferItem, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_saveTransfer),
                            "Transfer saved");
                    Cls_Generic_Methods.customWait(10);

                    int listSize = oPage_RequisitionReceived.list_transferTransactions.size();

                    if (listSize > 0) {
                        WebElement eTransaction = oPage_RequisitionReceived.list_transferTransactions.get(listSize - 1);
                        if (eTransaction.isDisplayed()) {
                            String requisitionTransferDateTime = Cls_Generic_Methods.getTextInElement(eTransaction);
                            String dateNdTime = requisitionTransferDateTime.split("\\|")[1].trim();
                            List<String> sArray = List.of(dateNdTime.split("\\s"));
                            String date = sArray.get(0);
                            String time = sArray.get(sArray.size() - 1);
                            date = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", date);

                            mapTracker.put(key_NewTransactionInRequisitionReceived_1, date + "  |  " + time);
                        }

                    } else {
                        m_assert.assertTrue(false, "Item Transfer Transactions confirmation details not found.");
                    }
                }

                // Approving transfer from central hub

                try {

                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                            "Central hub popup closed");
                    Cls_Generic_Methods.customWait();
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_newTransaction, 8);

                    for (WebElement eTransferredOn : oPage_Transfer.list_dateTimeOfRequisitionReceived) {
                        String dateTimeOnUI = Cls_Generic_Methods.getTextInElement(eTransferredOn);
                        String date = dateTimeOnUI.split("\\|")[0].trim();
                        String time = dateTimeOnUI.split("\\|")[1].trim();
                        date = CommonActions.getRequiredFormattedDateTime("dd-MM-yyyy", "yyyy-MM-dd", date);

                        if (mapTracker.get(key_NewTransactionInRequisitionReceived_1).contains(date) &&
                                mapTracker.get(key_NewTransactionInRequisitionReceived_1).contains(time)) {

                            bTransferEntryFound = true;
                            Cls_Generic_Methods.clickElement(eTransferredOn);
                            break;
                        }
                    }

                    m_assert.assertTrue(bTransferEntryFound, "Transfer Transaction Found");

                    if (bTransferEntryFound) {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_approveTransfer, 10);

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_approveTransfer),
                                "Transfer approved Clicked");
                        Cls_Generic_Methods.customWait(5);
                    }
                    Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "13, 14 - Validate that the order is Received in Store, Approved & Check the updated stock in Item Master")
    public void validateOrderReceiveInStore() {
        // 13	receive transfer in store
        // 14	validate item master stock

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        Page_Receive oPage_Receive = new Page_Receive(driver);

        boolean bApproveOrderFound = false;

        initializationStep();

        try {
            // Step 13 = receive transfer in store

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sROL_STORE);

            m_assert.assertInfo(Cls_Generic_Methods.switchToOtherTab(), "Switched to the second tab - " + driver.getTitle());
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Closing the Store Status Window");
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Receive");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.button_todayButtonInTransaction, 4);


            // Need for loop to get correct order
            // (Need to store the Transferred On details from Central Hub Test case)

            if (!mapTracker.get(key_NewTransactionInRequisitionReceived_1).isEmpty()) {
                for (WebElement eOrderTransferredOnDetail : oPage_Receive.list_transferredOnValueInRowOnTransactionReceive) {
                    String sLocalOrderTransferredOnDetail = Cls_Generic_Methods.getTextInElement(eOrderTransferredOnDetail);

                    String dateNdTime[] = sLocalOrderTransferredOnDetail.split("\\|");

                    String date = dateNdTime[0].trim();
                    String time = dateNdTime[1].trim();

                    if (mapTracker.get(key_NewTransactionInRequisitionReceived_1).contains(date) &&
                            mapTracker.get(key_NewTransactionInRequisitionReceived_1).contains(time)) {

                        Cls_Generic_Methods.clickElement(eOrderTransferredOnDetail);
                        bApproveOrderFound = true;
                        Cls_Generic_Methods.customWait(4);
                        break;
                    }
                }

                m_assert.assertTrue(bApproveOrderFound, "Validate Order from " + sCENTRAL_HUB + " is found in store " + sROL_STORE +
                        ".<br><b>Quantity = " + Cls_Generic_Methods.getTextInElement(oPage_Receive.text_itemQuantity) + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.button_receiveButtonInTransaction, 8);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Receive.button_receiveButtonInTransaction),
                        "Receive button clicked for Receiving the order from " + sCENTRAL_HUB);

                // =======================================================================================================================================
                // =======================================================================================================================================
                iPresentQuantity = iPendingTransferredQuantity;
                iPendingTransferredQuantity = 0;
                logAllQuantities();
                // =======================================================================================================================================
                // =======================================================================================================================================


                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.header_receiveTransactionOnModal, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Receive.button_receiveTransactionOnModal),
                        "Clicked on Save Changes on Receive Transaction Modal");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.button_todayButtonInTransaction, 8);

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Receive.text_receiveTransactionInTableOnTransactionReceive),
                        "Validate that order Receive Details section is displayed on table");

                // Step 14 = validate item master stock

                boolean bItemFoundInItemMaster = false;
                String sItemStockAfterOrderReceive = "-1";
                Cls_Generic_Methods.customWait();

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForPageLoad(driver, 16);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Closing the Store Status Window");

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 4);

                Cls_Generic_Methods.clickElement(oPage_Master.input_itemNameSearchInItemMaster);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysByAction(oPage_Master.input_itemNameSearchInItemMaster, sROL_ITEM),
                        "Search for the item " + sROL_ITEM + " in Store Item Master");

                Cls_Generic_Methods.customWait(4);

                for (WebElement eItemName : oPage_Master.list_itemDescriptionNameOnItemMaster) {
                    String sItemName = Cls_Generic_Methods.getTextInElement(eItemName);

                    if (sItemName.equals(sROL_ITEM)) {
                        Cls_Generic_Methods.clickElement(eItemName);
                        int iItemIndex = oPage_Master.list_itemDescriptionNameOnItemMaster.indexOf(eItemName);

                        bItemFoundInItemMaster = true;
                        sItemStockAfterOrderReceive = Cls_Generic_Methods.getTextInElement(oPage_Master.list_itemStockOnItemMaster.get(iItemIndex));
                        break;
                    }
                }

                if (bItemFoundInItemMaster) {
                    if (!sItemStockAfterOrderReceive.equals("-1")) {
                        m_assert.assertTrue(true, "Validate that the stock of the Item after Order Receive is " + sItemStockAfterOrderReceive);
                    } else {
                        m_assert.assertTrue(false, "Order found but not able to fetch stock. Stock on UI = " + sItemStockAfterOrderReceive);
                    }
                } else {
                    m_assert.assertTrue(false, "Validate that the stock of the Item after Order Receive is " + sItemStockAfterOrderReceive);
                }

            } else {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Validate correct Order found to approve in Transaction >> Receive");
            }

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "15, 16 - Create <<1st>> sales order & validate that item is not present in Requisition")
    public void validateCreatingSalesOrderForROLItem() {
        // 15	order >> sales order	quantity = 5
        // 16	rol item will not be present in requisition as effective_stock < rol value

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        Page_Requisition oPage_Requisition = new Page_Requisition(driver);

        myPatient = map_PatientsInExcel.get(patientKey);

        String sDateOnOrderInvoice = "";
        String sTimeOnOrderInvoice = "";
        String sRequisitionType = "Normal";

        initializationStep();

        try {

            // Step 15 - order >> sales order
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sROL_STORE);

            m_assert.assertInfo(Cls_Generic_Methods.switchToOtherTab(), "Switched to the other tab - " + driver.getTitle());
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 4);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Closing the Store Status Window");
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_addNewButtonInOrder, 4);

            Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addNewButtonInOrder);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.button_addNewPatient, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_NewPatientRegisteration.button_addNewPatient),
                    "<b>Add New Patient</b> Button is clicked");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, 8);
            // Entering Essential Form Data
            if (!myPatient.getsSALUTATION().isEmpty()) {
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()),
                        "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
            }

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                    "First Name is entered as - " + myPatient.getsFIRST_NAME());

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_middleNameOnPatientRegForm, myPatient.getsMIDDLE_NAME()),
                    "Middle Name is entered as - " + myPatient.getsMIDDLE_NAME());

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_lastNameOnPatientRegForm, myPatient.getsLAST_NAME()),
                    "Last Name is entered as - " + myPatient.getsLAST_NAME());

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                            myPatient.getsMOBILE_NUMBER()),
                    "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

            Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_saveOnNewPatientFormInSalesOrderOnOrder);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_salesOrderTitleOnModal, 16);

            Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_description);
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_searchMedicineNameInDescription);
            Cls_Generic_Methods.sendKeysByAction(oPage_SalesOrder.input_searchMedicineNameInDescription, sROL_ITEM);

            Cls_Generic_Methods.customWait();
            boolean bROL_Item_Found = false;

            for (WebElement eMedicineName : oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult) {
                String sMedicineName = Cls_Generic_Methods.getTextInElement(eMedicineName);

                if (sMedicineName.equals(sROL_ITEM)) {
                    bROL_Item_Found = true;
                    Cls_Generic_Methods.clickElement(eMedicineName);
                    break;
                }
            }

            m_assert.assertTrue(bROL_Item_Found, "Validate the ROL Item " + sROL_ITEM + " is found & selected");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_quantityOfMedicine, 8);

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_quantityOfMedicine, String.valueOf(i_1st_SALE_QUANTITY)),
                    "Quantity for " + sROL_ITEM + " is entered as " + i_1st_SALE_QUANTITY);
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.doubleClickElement(oPage_SalesOrder.text_balancePendingAmount);
            Cls_Generic_Methods.copyContentToClipboardByAction();
            String sPendingBalance = Cls_Generic_Methods.getClipboardContent();

            Cls_Generic_Methods.selectElementByVisibleText(oPage_SalesOrder.select_modeOfPayment, "Cash");
            Cls_Generic_Methods.customWait(1);

            Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_amountPaidInCash);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_amountPaidInCash, sPendingBalance);
            Cls_Generic_Methods.customWait(1);

            Cls_Generic_Methods.clickElement(oPage_SalesOrder.text_balancePendingAmount);

            sDateOnOrderInvoice = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderDateOnInvoice, "value").replace("/", "-");
            sTimeOnOrderInvoice = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderTimeOnInvoice, "value").replace(" ", "");

            Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_saveChangesOnSalesOrder);
            Cls_Generic_Methods.customWait();

            // =======================================================================================================================================
            // =======================================================================================================================================
            iPresentQuantity = iPresentQuantity - i_1st_SALE_QUANTITY;
            logAllQuantities();
            // =======================================================================================================================================
            // =======================================================================================================================================

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_closeModalOfSalesOrder, 8);
            Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_closeModalOfSalesOrder);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_addNewButtonInOrder, 8);

            boolean bOrderFound = false;
            int iOrderIndex = -1;
            for (WebElement eTransactionInfo : oPage_SalesOrder.list_transactionsInfoOnSalesOrderTable) {
                String sTransactionInfo = Cls_Generic_Methods.getTextInElement(eTransactionInfo).split("\n")[0];

                // ACTUAL GENUINE APPLICATION ISSUE -----------------------
                // ACTUAL GENUINE APPLICATION ISSUE -----------------------
                // ACTUAL GENUINE APPLICATION ISSUE -----------------------
                // ACTUAL GENUINE APPLICATION ISSUE -----------------------
                // Creating Sales Order always has time as 12:00AM

//                if (sTransactionInfo.contains(sDateOnOrderInvoice) && sTransactionInfo.contains(sTimeOnOrderInvoice)) {
                if (sTransactionInfo.contains(sDateOnOrderInvoice) && sTransactionInfo.contains(sTimeOnOrderInvoice)) {
                    Cls_Generic_Methods.clickElement(eTransactionInfo);
                    iOrderIndex = oPage_SalesOrder.list_transactionsInfoOnSalesOrderTable.indexOf(eTransactionInfo);
                    bOrderFound = true;
                    break;
                }
            }

            if (bOrderFound) {
                m_assert.assertTrue(true,
                        "Validate the Sales Order is found on the Table with Invoice Details as " +
                                Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.list_transactionsInfoOnSalesOrderTable.get(iOrderIndex)));
            } else {
                m_assert.assertTrue(false,
                        "Validate the Sales Order is found on the Table with Invoice Date & Time as " + sDateOnOrderInvoice + " | " + sTimeOnOrderInvoice);
            }

            // Step 16 - rol item will not be present in Order > Requisition as effective_stock < rol value
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_newRequisition, 4);

            Cls_Generic_Methods.clickElement(oPage_Requisition.button_newRequisition);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.dropdown_receivingStoreInRequisition, 8);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.dropdown_receivingStoreInRequisition),
                    "Receiving Store Dropdown clicked");
            for (WebElement receivingStore : oPage_Requisition.list_storesListInReceivingStoresRequisition) {
                if (sCENTRAL_HUB.contains(Cls_Generic_Methods.getTextInElement(receivingStore))) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(receivingStore),
                            "Receiving store selected: " + sCENTRAL_HUB);
                    Cls_Generic_Methods.customWait(3);
                    break;
                }
            }


            // select requisition type
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.dropdown_requisitionType),
                    "Requisition Type Dropdown clicked");
            for (WebElement eType : oPage_Requisition.list_requisitionType) {
                oPage_Requisition = new Page_Requisition(driver);
                if (sRequisitionType.contains(Cls_Generic_Methods.getTextInElement(eType))) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(eType), "Type selected: " + sRequisitionType);
                    Cls_Generic_Methods.customWait(4);
                    break;
                }
            }


            Cls_Generic_Methods.clickElement(oPage_Requisition.input_itemSearchBox);
            Cls_Generic_Methods.sendKeysByAction(oPage_Requisition.input_itemSearchBox, sROL_ITEM);
            Cls_Generic_Methods.customWait();

            boolean bItemInRequisitionListFound = false;
            for (WebElement eItemNameInList : oPage_Requisition.list_itemNameInPurchaseStore) {
                String sItemName = Cls_Generic_Methods.getTextInElement(eItemNameInList);
                if (sItemName.equals(sROL_ITEM)) {
                    Cls_Generic_Methods.clickElement(eItemNameInList);
                    bItemInRequisitionListFound = true;
                    break;
                }
            }

            // Expectation is that item should NOT be present in the list
            if (bItemInRequisitionListFound == false) {
                m_assert.assertTrue(true, "Validate " + sROL_ITEM + " Item is NOT found in Medication Items list");
            } else {
                m_assert.assertTrue(false, "Validate " + sROL_ITEM + " Item must NOT be found in Medication Items list");
            }

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_newRequisition, 4);

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "17 - Acquire more stock from Central Hub & Approve it")
    public void getAdditionalStockFromCentralHub() {
        // 17	get more stock from central hub (approved)

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);

        boolean bRequisitionReceivedFound = false;

        initializationStep();

        try {

            // Step 17 - get more stock from central hub (approved)

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sCENTRAL_HUB);

            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Central hub popup closed");
            Cls_Generic_Methods.customWait();

            try {

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition Received");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
                Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_downArrowForToday);
                CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, "This Month");
                Cls_Generic_Methods.customWait();

                for (WebElement eDate : oPage_RequisitionReceived.list_dateTimeOfRequisitionReceived) {
                    Cls_Generic_Methods.clickElement(eDate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 4);

                    String createdAt = Cls_Generic_Methods.getTextInElement(oPage_RequisitionReceived.text_dateTimeCreatedAt);
                    createdAt = createdAt.split("Created AT:")[1].trim();

                    String dateNdTime[] = createdAt.split("\\|");
                    String date = dateNdTime[0];
                    String time = dateNdTime[1];

                    if (mapTracker.get(key_CreatedAt_RequisitionOrderFromStore).contains(date) &&
                            mapTracker.get(key_CreatedAt_RequisitionOrderFromStore).contains(time)) {
                        bRequisitionReceivedFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(bRequisitionReceivedFound, "Requisition Received found for Item " + sROL_ITEM);

                if (bRequisitionReceivedFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_newTransactionRequisition),
                            "New transaction button clicked");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.list_transferItem, 15);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.list_transferItem),
                            "Transfer item clicked: <b> " + Cls_Generic_Methods.getTextInElement(oPage_RequisitionReceived.list_transferItem) + "</b>");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.input_issueQuantity, 10);
                    Cls_Generic_Methods.clearValuesInElement(oPage_RequisitionReceived.input_issueQuantity);

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RequisitionReceived.input_issueQuantity, String.valueOf(i_CENTRAL_HUB_ISSUE_QTY_2)),
                            "Transfer quantity: <b> " + i_CENTRAL_HUB_ISSUE_QTY_2 + "</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_confirmTransfer),
                            "Item Transfer confirmed");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.list_transferItem, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_saveTransfer),
                            "Save Changes button is clicked");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.list_transferTransactions.get(0), 8);
                    int iTransferTransactionsCount = oPage_RequisitionReceived.list_transferTransactions.size();

//                    sCentralHubTransferTransactionTimestamp = Cls_Generic_Methods.getTextInElement(oPage_RequisitionReceived.list_transferTransactions.get(iTransferTransactionsCount - 1));

                    String requisitionTransferDateTime = Cls_Generic_Methods.getTextInElement(oPage_RequisitionReceived.list_transferTransactions.get(iTransferTransactionsCount - 1));
                    String dateNdTime = requisitionTransferDateTime.split("\\|")[1].trim();
                    List<String> sArray = List.of(dateNdTime.split("\\s"));
                    String date = sArray.get(0);
                    String time = sArray.get(sArray.size() - 1);
                    date = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", date);

                    mapTracker.put(key_NewTransactionInRequisitionReceived_2, date + "  |  " + time);

                }

                if (!mapTracker.get(key_NewTransactionInRequisitionReceived_2).isEmpty()) {
                    Cls_Generic_Methods.customWait();

                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                            "Central hub popup closed");
                    Cls_Generic_Methods.customWait();

                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.list_dateTimeOfRequisitionReceived.get(0), 4);

                    boolean bTransferOrderFound = false;
                    for (WebElement eTransferredOn : oPage_Transfer.list_dateTimeOfRequisitionReceived) {
                        String sTransferredOn = Cls_Generic_Methods.getTextInElement(eTransferredOn);
                        String splitValuesDate = sTransferredOn.split("\\|")[0].trim();
                        String splitValuesTime = sTransferredOn.split("\\|")[1].trim();
                        splitValuesDate = CommonActions.getRequiredFormattedDateTime("dd-MM-yyyy", "yyyy-MM-dd", splitValuesDate);

                        if (mapTracker.get(key_NewTransactionInRequisitionReceived_2).contains(splitValuesDate) && mapTracker.get(key_NewTransactionInRequisitionReceived_2).contains(splitValuesTime)) {
                            bTransferOrderFound = true;
                            Cls_Generic_Methods.clickElement(eTransferredOn);
                            Cls_Generic_Methods.customWait(1);
                            break;
                        }
                    }

                    m_assert.assertTrue(bTransferOrderFound,
                            "Validate the Transfer of Requisition Order from " + sCENTRAL_HUB + " to " + sROL_STORE + " found for " + sROL_ITEM);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_approveTransfer, 8);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_approveTransfer),
                            "Approve button from "+sCENTRAL_HUB + " is clicked");

                    // =======================================================================================================================================
                    // =======================================================================================================================================
                    iPendingTransferredQuantity = i_CENTRAL_HUB_ISSUE_QTY_2;
                    iRequestedQuantity = iRequestedQuantity - iPendingTransferredQuantity;
                    logAllQuantities();
                    // =======================================================================================================================================
                    // =======================================================================================================================================


                } else {
                    m_assert.assertTrue(false, "Timestamp for the latest Transfer Transaction found as " + mapTracker.get(key_NewTransactionInRequisitionReceived_2));
                }

                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "18, 19 - Receive the stock in Store, check the stock in Item Master & Create <<2nd>> Sales Order")
    public void validateCreatingSalesOrderForROLItemWithUpdatedStock() {
        // 18	receive transfer in store & check stock in store master
        // 19	make a sales order

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        Page_Receive oPage_Receive = new Page_Receive(driver);

        boolean bApproveOrderFound = false;
        myPatient = map_PatientsInExcel.get(patientKey);
        initializationStep();

        try {

            // Step 18 - approve receive transfer in store & check stock in store master

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sROL_STORE);

            m_assert.assertInfo(Cls_Generic_Methods.switchToOtherTab(), "Switched to the second tab - " + driver.getTitle());
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Closing the Store Status Window");
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Receive");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.button_todayButtonInTransaction, 4);

            if (!mapTracker.get(key_NewTransactionInRequisitionReceived_2).isEmpty()) {

                for (WebElement eOrderTransferredOnDetail : oPage_Receive.list_transferredOnValueInRowOnTransactionReceive) {
                    String sLocalOrderTransferredOnDetail = Cls_Generic_Methods.getTextInElement(eOrderTransferredOnDetail);

                    String dateNdTime[] = sLocalOrderTransferredOnDetail.split("\\|");
                    String date = dateNdTime[0].trim();
                    String time = dateNdTime[1].trim();

                    if (mapTracker.get(key_NewTransactionInRequisitionReceived_2).contains(date) &&
                            mapTracker.get(key_NewTransactionInRequisitionReceived_2).contains(time)) {
                        Cls_Generic_Methods.clickElement(eOrderTransferredOnDetail);
                        bApproveOrderFound = true;
                        Cls_Generic_Methods.customWait(1);
                        break;
                    }
                }

                m_assert.assertTrue(bApproveOrderFound, "Validate Order from " + sCENTRAL_HUB + " is found in store " + sROL_STORE +
                        ".<br><b>Quantity = " + Cls_Generic_Methods.getTextInElement(oPage_Receive.text_itemQuantity) + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.button_receiveButtonInTransaction, 8);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Receive.button_receiveButtonInTransaction),
                        "Receive button clicked for Receiving the order from " + sCENTRAL_HUB);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.header_receiveTransactionOnModal, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Receive.button_receiveTransactionOnModal),
                        "Clicked on Save Changes on Receive Transaction Modal");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.button_todayButtonInTransaction, 8);

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Receive.text_receiveTransactionInTableOnTransactionReceive),
                        "Validate that order Receive Details section is displayed on table");

                // =======================================================================================================================================
                // =======================================================================================================================================
                iPresentQuantity = iPresentQuantity + iPendingTransferredQuantity;
                iPendingTransferredQuantity = 0;
                logAllQuantities();
                // =======================================================================================================================================
                // =======================================================================================================================================

            } else {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Validate correct Order found to approve in Transaction >> Receive");
            }

            boolean bItemFoundInItemMaster = false;
            String sItemStockAfterOrderReceive = "-1";
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 16);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Closing the Store Status Window");

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 4);

            Cls_Generic_Methods.clickElement(oPage_Master.input_itemNameSearchInItemMaster);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysByAction(oPage_Master.input_itemNameSearchInItemMaster, sROL_ITEM),
                    "Search for the item " + sROL_ITEM + " in Store Item Master");

            Cls_Generic_Methods.customWait(4);

            for (WebElement eItemName : oPage_Master.list_itemDescriptionNameOnItemMaster) {
                String sItemName = Cls_Generic_Methods.getTextInElement(eItemName);
                if (sItemName.equals(sROL_ITEM)) {
                    Cls_Generic_Methods.clickElement(eItemName);
                    int iItemIndex = oPage_Master.list_itemDescriptionNameOnItemMaster.indexOf(eItemName);

                    bItemFoundInItemMaster = true;
                    sItemStockAfterOrderReceive = Cls_Generic_Methods.getTextInElement(oPage_Master.list_itemStockOnItemMaster.get(iItemIndex));
                    break;
                }
            }

            if (bItemFoundInItemMaster) {
                if (!sItemStockAfterOrderReceive.equals("-1")) {
                    m_assert.assertTrue(true, "Validate that the stock of the Item after Order Receive is " + sItemStockAfterOrderReceive);
                } else {
                    m_assert.assertTrue(false, "Order found but not able to fetch stock. Stock on UI = " + sItemStockAfterOrderReceive);
                }
            } else {
                m_assert.assertTrue(false, "Validate that the stock of the Item after Order Receive is " + sItemStockAfterOrderReceive);
            }



            // Step 19 - make a sales order

            Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
            Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 16);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Closing the Store Status Window");

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_addNewButtonInOrder, 4);

            Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addNewButtonInOrder);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.button_addNewPatient, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_NewPatientRegisteration.button_addNewPatient),
                    "<b>Add New Patient</b> Button is clicked");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, 8);
            // Entering Essential Form Data
            if (!myPatient.getsSALUTATION().isEmpty()) {
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()),
                        "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
            }

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                    "First Name is entered as - " + myPatient.getsFIRST_NAME());

            if (!myPatient.getsMIDDLE_NAME().isEmpty()) {
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_middleNameOnPatientRegForm, myPatient.getsMIDDLE_NAME()),
                        "Middle Name is entered as - " + myPatient.getsMIDDLE_NAME());
            }

            if (!myPatient.getsLAST_NAME().isEmpty()) {
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_lastNameOnPatientRegForm, myPatient.getsLAST_NAME()),
                        "Last Name is entered as - " + myPatient.getsLAST_NAME());
            }


            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                            myPatient.getsMOBILE_NUMBER()),
                    "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

            Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_saveOnNewPatientFormInSalesOrderOnOrder);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_salesOrderTitleOnModal, 16);
            Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_description);
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_searchMedicineNameInDescription);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_searchMedicineNameInDescription, sROL_ITEM);

            Cls_Generic_Methods.customWait();
            boolean bROL_Item_Found = false;

            for (WebElement eMedicineName : oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult) {
                String sMedicineName = Cls_Generic_Methods.getTextInElement(eMedicineName);

                if (sMedicineName.equals(sROL_ITEM)) {
                    bROL_Item_Found = true;
                    Cls_Generic_Methods.clickElement(eMedicineName);
                    break;
                }
            }

            m_assert.assertTrue(bROL_Item_Found, "Validate the ROL Item " + sROL_ITEM + " is found & selected");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_quantityOfMedicine, 8);

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_quantityOfMedicine, String.valueOf(i_2ND_SALE_QUANTITY)),
                    "Quantity for " + sROL_ITEM + " is entered as " + i_2ND_SALE_QUANTITY);
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.doubleClickElement(oPage_SalesOrder.text_balancePendingAmount);
            Cls_Generic_Methods.copyContentToClipboardByAction();
            String sPendingBalance = Cls_Generic_Methods.getClipboardContent();

            Cls_Generic_Methods.selectElementByVisibleText(oPage_SalesOrder.select_modeOfPayment, "Cash");
            Cls_Generic_Methods.customWait(1);

            Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_amountPaidInCash);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_amountPaidInCash, sPendingBalance);
            Cls_Generic_Methods.customWait(1);

            Cls_Generic_Methods.clickElement(oPage_SalesOrder.text_balancePendingAmount);

            String sDateOnOrderInvoice = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderDateOnInvoice, "value").replace("/", "-");
            String sTimeOnOrderInvoice = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderTimeOnInvoice, "value").replace(" ", "");

            Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_saveChangesOnSalesOrder);
            Cls_Generic_Methods.customWait();

            // =======================================================================================================================================
            // =======================================================================================================================================
            iPresentQuantity = iPresentQuantity - i_2ND_SALE_QUANTITY;
            logAllQuantities();
            // =======================================================================================================================================
            // =======================================================================================================================================

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_closeModalOfSalesOrder, 8);
            Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_closeModalOfSalesOrder);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_addNewButtonInOrder, 8);

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "20, 21 - Validate the default populated Qty for Item vs (Effective Stock & ROL Value) under Requisition Order")
    public void validateDefaultRequisitionValueAfter2ndSale(){
        // 20	under requisition now the rol_item will be displayed as effective < rol value
        // 21	Creating new requisition for rol_item, the quantity should be (max-effective)

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Requisition oPage_Requisition = new Page_Requisition(driver);

        String sRequisitionType = "Normal";

        initializationStep();

        try {

            // Step 20 - under requisition now the rol_item will be displayed as effective < rol value
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sROL_STORE);

            m_assert.assertInfo(Cls_Generic_Methods.switchToOtherTab(), "Switched to the second tab - " + driver.getTitle());
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Closing the Store Status Window");
            Cls_Generic_Methods.customWait();


            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_newRequisition, 4);

            Cls_Generic_Methods.clickElement(oPage_Requisition.button_newRequisition);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.dropdown_receivingStoreInRequisition, 8);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.dropdown_receivingStoreInRequisition),
                    "Receiving Store Dropdown clicked");

            for (WebElement receivingStore : oPage_Requisition.list_storesListInReceivingStoresRequisition) {
                if (sCENTRAL_HUB.contains(Cls_Generic_Methods.getTextInElement(receivingStore))) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(receivingStore),
                            "Receiving store selected: " + sCENTRAL_HUB);
                    Cls_Generic_Methods.customWait(3);
                    break;
                }
            }


            // Select requisition type
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.dropdown_requisitionType),
                    "Requisition Type Dropdown clicked");
            for (WebElement eType : oPage_Requisition.list_requisitionType) {
                oPage_Requisition = new Page_Requisition(driver);
                if (sRequisitionType.contains(Cls_Generic_Methods.getTextInElement(eType))) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(eType), "Type selected: " + sRequisitionType);
                    Cls_Generic_Methods.customWait(4);
                    break;
                }
            }


            Cls_Generic_Methods.clickElement(oPage_Requisition.input_itemSearchBox);
            Cls_Generic_Methods.sendKeysByAction(oPage_Requisition.input_itemSearchBox, sROL_ITEM);
            Cls_Generic_Methods.customWait();

            boolean bItemInRequisitionListFound = false;
            for (WebElement eItemNameInList : oPage_Requisition.list_itemNameInPurchaseStore) {
                String sItemName = Cls_Generic_Methods.getTextInElement(eItemNameInList);
                if (sItemName.equals(sROL_ITEM)) {
                    Cls_Generic_Methods.clickElement(eItemNameInList);
                    Cls_Generic_Methods.customWait();
                    bItemInRequisitionListFound = true;
                    break;
                }
            }

            // Step 21 - Creating new requisition for rol_item, the quantity should be (ROL max Qty - effective stock)
            // Expectation is that item should be present in the list && Quantity displayed by default must be (ROL max value - Effective Stock)

            int iExpected_UI_value = iROL_ITEM_MAX_VALUE - iEffectiveStock;

            if (bItemInRequisitionListFound) {
                m_assert.assertTrue(true, "Validate " + sROL_ITEM + " Item is found in Medication Items list");

                String sDefaultUIValueForQtyAfterSecondRequisition = Cls_Generic_Methods.getElementAttribute(
                        oPage_Requisition.input_quantityForRequisition, "value");

                int iDefaultUIValueForQtyAfterSecondRequisition = Integer.parseInt(sDefaultUIValueForQtyAfterSecondRequisition);

                if (iDefaultUIValueForQtyAfterSecondRequisition == iExpected_UI_value) {
                    m_assert.assertTrue(true,
                            "Validate the auto filled Qty value for Item is <b>equal</b> than Item's (ROL Max Value - Effective Stock)" +
                                    "<br>" +
                                    "Expected Value for Item = " + iExpected_UI_value + " " +
                                    "Current Value on UI = " + iDefaultUIValueForQtyAfterSecondRequisition
                    );
                } else if (iDefaultUIValueForQtyAfterSecondRequisition > iExpected_UI_value) {
                    m_assert.assertWarn(true,
                            "Validate the auto filled Qty value for Item is <b>equal</b> than Item's (ROL Max Value - Effective Stock)" +
                                    "<br>" +
                                    "Expected Value for Item = " + iExpected_UI_value + " " +
                                    "Current Value on UI = " + iDefaultUIValueForQtyAfterSecondRequisition
                    );
                } else {
                    m_assert.assertTrue(false,
                            "Validate the auto filled Qty value for Item is <b>equal</b> than Item's (ROL Max Value - Effective Stock)" +
                                    "<br>" +
                                    "Expected Value for Item = " + iExpected_UI_value + " " +
                                    "Current Value on UI = " + iDefaultUIValueForQtyAfterSecondRequisition
                    );
                }

            } else {
                m_assert.assertTrue(false, "Validate " + sROL_ITEM + " Item is NOT found in Medication Items list");
            }

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            logAllQuantities();

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }

    }

    public static int calcEffectiveStock() {

        iEffectiveStock = iPresentQuantity + iPendingTransferredQuantity + iRequestedQuantity;
        return iEffectiveStock;

    }

    public static void logAllQuantities() {

        calcEffectiveStock();

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("iPresentQuantity = " + iPresentQuantity);
        System.out.println("iPendingTransferredQuantity = " + iPendingTransferredQuantity);
        System.out.println("iRequestedQuantity = " + iRequestedQuantity);
        System.out.println("iEffectiveStock = " + iEffectiveStock);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");

        String finalMessage =         "Current Present Quantity for <b>" + sROL_ITEM + "</b> is = <b>" + iPresentQuantity + "</b><br>";
        finalMessage = finalMessage + "Current Pending Transferred Quantity for <b>" + sROL_ITEM + "</b> is = <b>" + iPendingTransferredQuantity + "</b><br>";
        finalMessage = finalMessage + "Current Requested Quantity for <b>" + sROL_ITEM + "</b> is = <b>" + iRequestedQuantity + "</b><br>";
        finalMessage = finalMessage + "Current Effective Stock for <b>" + sROL_ITEM + "</b> is = <b>" + iEffectiveStock + "</b>";

        m_assert.assertInfo("+++++++++++++++++++++++++++++++++++++++++++++");
        m_assert.assertInfo(finalMessage);
        m_assert.assertInfo("+++++++++++++++++++++++++++++++++++++++++++++");

    }

    public static void initializationStep() {

        if (bEnableDebugMode) {

            iPresentQuantity = 15;
            iPendingTransferredQuantity = 0;
            iRequestedQuantity = 30;
            calcEffectiveStock();

            mapTracker.put(key_CreatedAt_RequisitionOrderFromStore, "31-08-2022  |  05:51AM");
            mapTracker.put(key_CreatedAt_ItemPurchaseInCentralHub, "2022-08-31  |  05:52AM");
            mapTracker.put(key_NewTransactionInRequisitionReceived_1, "2022-08-31  |  05:52AM");
            mapTracker.put(key_NewTransactionInRequisitionReceived_2, "2022-08-31  |  06:03AM");

        }
    }

}