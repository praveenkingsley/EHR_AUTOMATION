package tests.inventoryStores.pharmacyStore.Order;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.python.icu.impl.UResource;
import org.python.jline.console.WCWidth;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Order.Page_Indent;
import pages.store.PharmacyStore.Order.Page_PurchaseOrder;
import pages.store.PharmacyStore.Order.Page_Requisition;
import pages.store.PharmacyStore.Order.Page_RequisitionReceived;
import pages.store.PharmacyStore.Page_TaxInvoiceDeliveryChallan;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_Receive;
import pages.store.PharmacyStore.Transaction.Page_Transfer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequisitionThroughIndentTest extends TestBase {

    public static String sROL_STORE = "Pharmacy automation- Pharmacy";
    public static String sCENTRAL_HUB = "CENTRAL HUB 01- Central Hub";
    public static String sROL_ITEM = "bcbc1";
    public static String sSubStore = "Default";
    public static boolean debugEnabled = false;
    public static String issueQty = "3";

    public static Map<String, String> mapTracker = new HashMap<String, String>();
    public static String key_CreatedAt_RequisitionOrderFromStore = "key_CreatedAt_RequisitionOrderFromStore";
    public static String key_RequisitionNumber_Created_InRequisition = "key_RequisitionNumber_Created_InRequisition";
    public static String key_CreatedAt_IndentOrderFromStore = "key_CreatedAt_IndentOrderFromStore";
    public static String key_NewTransactionInRequisitionReceived_1 = "key_NewTransactionInRequisitionReceived_1";
    public static String key_CreatedAt_PurchaseOrderTroughIndent = "key_CreatedAt_PurchaseOrderTroughIndent";
    public static String key_CreatedAt_PurchaseTransaction = "key_CreatedAt_PurchaseTransaction";
    String issueNumber = "";

    @Test(enabled = true, description = "Create requisition order and approve the same in store")
    public void validateRequisitionInStoreAndApproveOrder() {
        // 1	Order >> requisition
        // 2	Approve the same requisition order

        Page_Requisition oPage_Requisition = new Page_Requisition(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        boolean itemFoundInRequisition = false;
        String reqNum = "";
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
                Cls_Generic_Methods.clickElement(oPage_Requisition.input_itemSearchBox);
                Cls_Generic_Methods.sendKeysByAction(oPage_Requisition.input_itemSearchBox, sROL_ITEM);
                oPage_Requisition.input_itemSearchBox.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait();

                for (WebElement eItemName : oPage_Requisition.list_itemNameInPurchaseStore) {
                    if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(sROL_ITEM)) {
                        Cls_Generic_Methods.clickElement(eItemName);
                        Cls_Generic_Methods.customWait(3);
                        itemFoundInRequisition = true;
                        break;
                    }
                }

                m_assert.assertTrue(itemFoundInRequisition, "Item found in requisition: <b> " + sROL_ITEM + "</b>");

                if (itemFoundInRequisition) {
                    //getting rol_date and rol_time value
                    requisitionOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderTime, "value");
                    requisitionOrderTime = requisitionOrderTime.replaceAll("\\s+", "");
                  //  requisitionOrderTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", requisitionOrderTime);
                    requisitionOrderTime = requisitionOrderTime.replace("pm", "PM").replace("am", "AM");
                    if(requisitionOrderTime.length() == 6){
                        requisitionOrderTime  = "0"+requisitionOrderTime;
                    }
                    m_assert.assertTrue("Requisition order time:<b> " + requisitionOrderTime + "</b>");

                    requisitionOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderDate, "value");
                    requisitionOrderDate = requisitionOrderDate.replaceAll("/", "-");
                    m_assert.assertTrue("Requisition order date:<b> " + requisitionOrderDate + "</b>");

                    mapTracker.put(key_CreatedAt_RequisitionOrderFromStore, requisitionOrderDate + "  |  " + requisitionOrderTime);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_quantityForRequisition, "3");
                    Cls_Generic_Methods.customWait();
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_saveRequisition),
                            "Requisition saved");
                    Cls_Generic_Methods.customWait(4);
                }

                for (WebElement eDate : oPage_Requisition.list_dateTimeOfRequisition) {

                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate);
                    String dateAndTime = dateTimeFull.split("\n")[0].trim();
                    reqNum = dateTimeFull.split("\n")[1].trim();

                    if (mapTracker.get(key_CreatedAt_RequisitionOrderFromStore).contains(dateAndTime)) {
                        mapTracker.put(key_RequisitionNumber_Created_InRequisition, reqNum);
                        bRequisitionOrderFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 5);
                        break;
                    }
                }

                m_assert.assertTrue(bRequisitionOrderFound, "Requisition order found in the requisition page for number: " + mapTracker.get(key_RequisitionNumber_Created_InRequisition));

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
                }

            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        } finally {
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        }

    }

    @Test(enabled = true, description = "Create indent order for the respective requisition in central hub and create purchase order for the indent")
    public void validateCreatingIndentOrderForRequisitionInCentralHub() {
        // 3	order >> requisition received >> validate created requisition
        // 4	order >> indent >> create indent order for the requisition
        // 5    creating purchase order from the indent

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);


        boolean bRequisitionReceivedFound = false;
        String sVendorName = "Supplier ABC";
        boolean vendorFound = false;
        String sNote = "Remarks";
        String indentOrderTime = "";
        String indentOrderDate = "";
        String sIndentType = "Normal";
        String sStoreToShipOrToBillOrder = "CENTRAL HUB 01";
        boolean bStoreToShipOrderFound = false;
        boolean bStoreToBillOrderFound = false;
        String sRatePerUnit = "10.0";
        String purchaseOrderTime = "";
        String purchaseOrderDate = "";

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
                Cls_Generic_Methods.customWait(5);

                //find created requisition in requisition received
                for (WebElement e : oPage_RequisitionReceived.list_dateTimeOfRequisitionReceived) {
                    String dataTimeAndRequisition = Cls_Generic_Methods.getTextInElement(e);
                    String requisitionNumberInIndentUI = dataTimeAndRequisition.split("\n")[1].trim();
                    if (mapTracker.get(key_RequisitionNumber_Created_InRequisition).equals(requisitionNumberInIndentUI)) {
                        Cls_Generic_Methods.clickElement(e);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 4);
                        bRequisitionReceivedFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(bRequisitionReceivedFound, "Requisition Received found ");

                // creating indent order requisition
                if (bRequisitionReceivedFound) {
                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                            "Central hub popup closed");
                    Cls_Generic_Methods.customWait();
                    try {

                        CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_addNewIndent),
                                "New Button clicked in Order: Indent");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 10);

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_vendorField, 10);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.select_vendorField),
                                "clicked on Vendor selection field");
                        Cls_Generic_Methods.customWait();
                        boolean storeFound = false ;
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox,sStoreToShipOrToBillOrder);
                        Cls_Generic_Methods.customWait();
                        for (WebElement eStoreName : oPage_Indent.list_stores) {
                            if (Cls_Generic_Methods.getTextInElement(eStoreName).contains(sStoreToShipOrToBillOrder)) {
                                Cls_Generic_Methods.clickElement(eStoreName);
                                Cls_Generic_Methods.customWait();
                                storeFound = true;
                                break;
                            }
                        }

                        m_assert.assertTrue(storeFound, "Store found to do indent purchase : <b> " + sStoreToShipOrToBillOrder + "</b>");
                        Cls_Generic_Methods.customWait();

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_requisitionToSelect),
                                "Requisition button clicked under Indent ");
                        Cls_Generic_Methods.customWait();

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_noteUnderIndentForPurchase, 10);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_noteUnderIndentForPurchase, sNote),
                                "Note field value filled as =  <b>" + sNote + "</b> ");
                        Cls_Generic_Methods.customWait();

                        //find requisition in the indent list and save indent
                        boolean requisitionIndentFound = false;
                        for (WebElement eRequisitionNumberIndent : oPage_Indent.list_ReqNumberUnderIndentPurchase) {

                            String dataTimeAndRequisitionNumber = Cls_Generic_Methods.getTextInElement(eRequisitionNumberIndent);
                            String requisitionNumberInIndent = dataTimeAndRequisitionNumber.split("\n")[1];
                            if (mapTracker.get(key_RequisitionNumber_Created_InRequisition).equals(requisitionNumberInIndent)) {
                                Cls_Generic_Methods.clickElement(eRequisitionNumberIndent);
                                requisitionIndentFound = true;
                                break;
                            }
                        }
                        m_assert.assertTrue(requisitionIndentFound, "<b> Requisition under indent is found " + mapTracker.get(key_RequisitionNumber_Created_InRequisition) + "</b>");

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_indentOrderDate, 10);
                        indentOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderTime, "value");
                        indentOrderTime = indentOrderTime.replaceAll("\\s+", "");
                       // indentOrderTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", indentOrderTime);
                        indentOrderTime = indentOrderTime.replace("am", "AM").replace("pm", "PM");

                        if(indentOrderTime.length() == 6){
                            indentOrderTime = "0"+indentOrderTime;
                        }
                        m_assert.assertTrue("Indent order time:<b> " + indentOrderTime + "</b>");
                        indentOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderDate, "value");
                        indentOrderDate = indentOrderDate.replaceAll("/", "-");
                        m_assert.assertTrue("Indent order date:<b> " + indentOrderDate + "</b>");
                        mapTracker.put(key_CreatedAt_IndentOrderFromStore, indentOrderDate + "  |  " + indentOrderTime);
                        Cls_Generic_Methods.customWait();

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.dropdown_IndentType),
                                "Clicked on Indent Type Dropdown");

                        Cls_Generic_Methods.customWait(2);
                        for (WebElement eType : oPage_Indent.list_IndentTypeList) {
                            oPage_Indent = new Page_Indent(driver);
                            if (sIndentType.contains(Cls_Generic_Methods.getTextInElement(eType))) {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(eType),
                                        "Indent Type selected: <b> " + sIndentType + " </b>");
                                Cls_Generic_Methods.customWait(3);
                                break;
                            }
                        }

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder),
                                " Indent order saved");
                        Cls_Generic_Methods.customWait(3);

                    } catch (Exception e) {
                        m_assert.assertFatal(" " + e);
                        e.printStackTrace();
                    }
                }

                try {
                    boolean bIndentOrderFound = false;
                    for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                        String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                        String date = dateTimeFull.split("\\|")[0].trim();
                        String time = dateTimeFull.split("\\|")[1].trim();
                        if (mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(date) &&
                                mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(time)) {
                            bIndentOrderFound = true;
                            Cls_Generic_Methods.clickElement(eDate);
                            Cls_Generic_Methods.customWait();
                            break;
                        }
                    }
                    m_assert.assertTrue(bIndentOrderFound, "Order found in the Indent order page");


                    if (bIndentOrderFound) {

                        String sIndentNoOnUI = null;
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_IndentNumber, 10);
                        sIndentNoOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_IndentNumber);
                        sIndentNoOnUI = sIndentNoOnUI.replaceAll("Indent No.", "").trim();


                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newOrder, 10);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newOrder),
                                "clicked on New Order button to create PO");

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_IndentNumberInPurchaseOrderPopUp, 10);

                        String sIndentNoInPurchaseOrderPopUp = Cls_Generic_Methods.getTextInElement(oPage_Indent.link_indentNumberLinkInNewOrder);

                        if (sIndentNoOnUI.equalsIgnoreCase(sIndentNoInPurchaseOrderPopUp)) {
                            m_assert.assertTrue("Validated Indent Number while creating new purchase order =  <b>" + sIndentNoInPurchaseOrderPopUp + "</b>");
                        } else {
                            m_assert.assertTrue("displaying wrong Indent Number ");
                        }

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_OrderNote, 10);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_OrderNote, sNote),
                                "Order note field value filled while creating new purchase order =  <b>" + sNote + "</b> ");

                        purchaseOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderTime, "value");
                        purchaseOrderTime = purchaseOrderTime.replaceAll("\\s+", "");
                       // purchaseOrderTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseOrderTime);
                        purchaseOrderTime = purchaseOrderTime.replace("am", "AM").replace("pm", "PM");
                        if(purchaseOrderTime.length() == 6){
                            purchaseOrderTime = "0"+purchaseOrderTime;
                        }
                        m_assert.assertTrue(" order time:<b> " + purchaseOrderTime + "</b>");

                        purchaseOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderDate, "value");
                        purchaseOrderDate = purchaseOrderDate.replaceAll("/", "-");
                        m_assert.assertTrue(" order date:<b> " + purchaseOrderDate + "</b>");

                        mapTracker.put(key_CreatedAt_PurchaseOrderTroughIndent, purchaseOrderDate + "  |  " + purchaseOrderTime);
                        Cls_Generic_Methods.customWait(2);

                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_searchVendorAddress,sVendorName);
                        Cls_Generic_Methods.customWait(3);
                        String sSelectedVendor = Cls_Generic_Methods.getTextInElement(oPage_Indent.vendorAddressName);
                        if (sSelectedVendor.contains(sVendorName)) {
                            Cls_Generic_Methods.clickElement(oPage_Indent.vendorAddressName);
                            m_assert.assertTrue("Selected Vendor:  <b>" + sVendorName + "</b>");
                        } else {
                            m_assert.assertTrue("Required vendor <b>" + sVendorName + "</b> is NOT selected");
                        }

                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_RatePerUnit, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_RatePerUnit, sRatePerUnit),
                            "Rate per unit =  <b>" + sRatePerUnit + " </b>");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_cancelOtherCharges, 4);
                    Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_cancelOtherCharges);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                            "Indent purchase order Save Button clicked");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.option_purchaseOrder, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder),
                            "Purchase order option is selected");

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
        } finally {
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        }
    }

    @Test(enabled = true, description = "Validate created Indent order in Purchase Order and approve the purchase ")
    public void validateIndentOrderInPurchaseTransaction() {
        // 6	order >> purchase >> validate created indent order
        // 7	approve >> new transaction

        Page_PurchaseOrder oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        String billType = "Bill";
        String billNumber = "Bill"+CommonActions.getRandomString(5);
        String sellingPrice = "50";

        boolean bIndentOrderFound = false;
        String purchaseTransactionTime = "";
        String purchaseTransactionDate = "";
        initializationStep();
        try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sCENTRAL_HUB);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                for (WebElement eDate : oPage_PurchaseOrder.list_dateTimeOfPO) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate);
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();

                    if (mapTracker.get(key_CreatedAt_PurchaseOrderTroughIndent).contains(date) &&
                            mapTracker.get(key_CreatedAt_PurchaseOrderTroughIndent).contains(time)) {
                        bIndentOrderFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        break;
                    }
                }
                m_assert.assertTrue(bIndentOrderFound, "Indent order found in purchase order ");
                Cls_Generic_Methods.customWait();

                //Creating new transaction purchase for adding stock
                if (bIndentOrderFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_approveOrder, 10);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PurchaseOrder.button_approveOrder),
                            "Clicked on approve button");
                    Cls_Generic_Methods.customWait();
                    for (WebElement eYesButton : oPage_Purchase.button_yesButtonList) {
                        if (Cls_Generic_Methods.isElementDisplayed(eYesButton)) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(eYesButton),
                                    "Yes Button Clicked In Approved Confirmation");
                            Cls_Generic_Methods.customWait();
                            break;
                        }
                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newTransactionPO, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PurchaseOrder.button_newTransactionPO),
                            "Clicked on new transaction");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.dropdown_selectBillType, 10);

                    //get date and time of purchase transaction
                    purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_transactionOrderTime, "value");
                    purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                   // purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                    purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                    if(purchaseTransactionTime.length() == 6){
                        purchaseTransactionTime = "0"+purchaseTransactionTime;
                    }
                    m_assert.assertTrue("Purchase Transaction time: <b> " + purchaseTransactionTime + "</b>");

                    purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_transactionOrderDate, "value");
                    purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);
                    m_assert.assertTrue("Purchase Transaction date: <b> " + purchaseTransactionDate + "</b>");

                    mapTracker.put(key_CreatedAt_PurchaseTransaction, purchaseTransactionDate + "  |  " + purchaseTransactionTime);

                    //fill value in bill details
                    Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.dropdown_selectBillType);
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_PurchaseOrder.dropdown_selectBillType, billType),
                            "Bill Type Selected:<b> " + billType + " </b>");
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_billNumber, billNumber),
                            "Bill Number: <b> " + billNumber + " </b>");
                    Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.input_billDate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_todayBillDate, 3);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.input_todayBillDate),
                            "Date of bill selected:<b> " + oPage_PurchaseOrder.input_todayBillDate.getText() + " </b>");

                    if (Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseOrder.input_expiryDate)) {
                       // Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.input_expiryDate);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_expiryDate,Cls_Generic_Methods.getTodayDate()),
                                "Expiry date selected ");
                    } else {
                        m_assert.assertInfo("Expiry date calendar is not present");
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_sellingPrice, 7);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_sellingPrice, sellingPrice),
                            "Selling price entered: " + sellingPrice + "</b>");
                   m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.dropdown_subStore),
                            "Sub Store dropdown clicked");
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_PurchaseOrder.dropdown_subStore, sSubStore),
                            "Sub Store selected: " + sSubStore);
                //    Cls_Generic_Methods.clickElement(driver, oPage_PurchaseOrder.button_cancelOtherCharges);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_batchNOInNewTransaction,"BT1234");
                    m_assert.assertTrue("Batch No Entered while Adding New Stock to Inventory =  <b>" + "BT1234" + "</b>");

                    Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_saveOrder);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 7);

                    boolean bPurchaseTransactionFound = false;
                    for (WebElement eDate : oPage_Purchase.list_dateTimeOfPurchase) {
                        String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate);
                        String date = dateTimeFull.split("\\|")[0].trim();
                        String time = dateTimeFull.split("\\|")[1].trim();

                        if (mapTracker.get(key_CreatedAt_PurchaseTransaction).contains(date) &&
                                mapTracker.get(key_CreatedAt_PurchaseTransaction).contains(time)) {
                            bPurchaseTransactionFound = true;
                            Cls_Generic_Methods.clickElement(eDate);
                            Cls_Generic_Methods.customWait();
                            break;
                        }
                    }
                    m_assert.assertTrue(bPurchaseTransactionFound, "Purchase Order found in purchase Transaction page");

                    if (bPurchaseTransactionFound) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction),
                                "Approve Button Clicked For Selected Transaction In Purchase Transaction");
                        Cls_Generic_Methods.customWait();
                        for (WebElement eYesButton : oPage_Purchase.button_yesButtonList) {
                            if (Cls_Generic_Methods.isElementDisplayed(eYesButton)) {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(eYesButton),
                                        "Yes Button Clicked In Approved Confirmation");
                                Cls_Generic_Methods.customWait();
                                break;
                            }
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        } finally {
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        }
    }

    @Test(enabled = true, description = "Receive requisition in central hub and approve transfer")
    public void validateRequisitionReceivedAndTransferFromHub() {
        // 8	order >> requisition received
        // 9	transfer items
        // 10	Approve transfer from central hub

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        Page_TaxInvoiceDeliveryChallan oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        boolean bRequisitionReceivedFound = false;
        boolean bTransferEntryFound = false;
        initializationStep();

        try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sCENTRAL_HUB);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition Received");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
                Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_downArrowForToday);
                CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, "This Month");
                Cls_Generic_Methods.customWait(5);

                //find created requisition in requisition received
                for (WebElement e : oPage_RequisitionReceived.list_dateTimeOfRequisitionReceived) {
                    String dataTimeAndReqNo = Cls_Generic_Methods.getTextInElement(e);
                    String reqNumber = dataTimeAndReqNo.split("\n")[1].trim();
                    if (mapTracker.get(key_RequisitionNumber_Created_InRequisition).equals(reqNumber)) {
                        Cls_Generic_Methods.clickElement(e);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 4);
                        bRequisitionReceivedFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(bRequisitionReceivedFound, "Requisition Received found ");

                if (bRequisitionReceivedFound) {

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_newTransactionRequisition),
                            "New transaction button clicked in requisition received");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.list_transferItem, 15);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.list_transferItem),
                            "Transfer item clicked: <b> " + oPage_RequisitionReceived.list_transferItem.getText() + "</b>");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.input_issueQuantity, 10);
                    Cls_Generic_Methods.clearValuesInElement(oPage_RequisitionReceived.input_issueQuantity);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RequisitionReceived.input_issueQuantity, issueQty),
                            "Transfer quantity: <b> " + issueQty + "</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_confirmTransfer),
                            "Item Transfer confirmed from requisition");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.list_transferItem, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_saveTransfer),
                            "Transfer saved");
                    Cls_Generic_Methods.customWait(10);

                    for (WebElement eTransaction : oPage_RequisitionReceived.list_transferTransactions) {
                        String requisitionTransferDateTime = Cls_Generic_Methods.getTextInElement(eTransaction);
                        String dateNdTime = requisitionTransferDateTime.split("\\|")[1].trim();
                        List<String> sArray = List.of(dateNdTime.split("\\s"));
                        String date = sArray.get(0);
                        String time = sArray.get(sArray.size() - 1);
                        date = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd-MM-yyyy", date);

                        mapTracker.put(key_NewTransactionInRequisitionReceived_1, date + "  |  " + time);
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
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_newTransaction, 8);

                    for (WebElement eTransferredOn : oPage_Transfer.list_dateTimeOfRequisitionReceived) {
                        String dateTimeIssue = Cls_Generic_Methods.getTextInElement(eTransferredOn);
                        String dateTimeOnUI = dateTimeIssue.split("\n")[1];
                        String date = dateTimeOnUI.split(" ")[0];
                        String time = dateTimeOnUI.split(" ")[2];
                        if (mapTracker.get(key_NewTransactionInRequisitionReceived_1).contains(date)
                        &&mapTracker.get(key_NewTransactionInRequisitionReceived_1).contains(time) ) {
                            bTransferEntryFound = true;
                            Cls_Generic_Methods.clickElement(eTransferredOn);
                            Cls_Generic_Methods.customWait();
                            break;
                        }
                    }

                    m_assert.assertTrue(bTransferEntryFound, "Transfer Transaction Found");

                    if (bTransferEntryFound) {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_approveTransfer, 10);

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_approveTransfer),
                                "Transfer approved Clicked");
                        Cls_Generic_Methods.customWait(5);
                        issueNumber = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_issueNo);
                        Cls_Generic_Methods.customWait();

                        try {
                            if (Cls_Generic_Methods.isElementDisplayed(oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan)) {
                                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan), "Selected Option in the Left Panel = Tax Invoice / Delivery Challan");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_new, 5);

                                //Create New
                                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_new), "Clicked New Button");
                                Cls_Generic_Methods.customWait();
                                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_deliveryChallan), "Clicked <b>New Delivery Challan</b>");
                                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_headerCreateDeliveryChallan, 10), "Validated --> Navigated to Create Delivery Challan Page");

                                //Select To store
                                selectByOptions(oPage_TaxInvoiceDeliveryChallan.select_againstStore, sROL_STORE.split("-")[0]);
                                Cls_Generic_Methods.customWait(2);
                                for (WebElement lhsRow : oPage_TaxInvoiceDeliveryChallan.row_lhsCreateTaxInvoiceDeliveryChallan) {
                                    String transferDate = Cls_Generic_Methods.getTextInElement(lhsRow.findElement(By.xpath("./td[3]/div[2]")));
                                    String date = transferDate.split(" ")[0];
                                    String time = transferDate.split(" ")[1];

                                    if (mapTracker.get(key_NewTransactionInRequisitionReceived_1).contains(date) &&
                                            mapTracker.get(key_NewTransactionInRequisitionReceived_1).contains(time)) {
                                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(lhsRow), "Selected Transfer transaction : ");
                                        break;
                                    }
                                }

                                Cls_Generic_Methods.customWait(4);
                                m_assert.assertInfo(selectByOptions(oPage_TaxInvoiceDeliveryChallan.select_transportationMode, "Test"), "Selected <b>Test</b> in Transportation Mode");
                                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_TaxInvoiceDeliveryChallan.input_transactionId, "1234"), "Entered <b>1234</b> in Transaction Id");

                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_saveChanges), "Clicked <b>Save Changes</b>");
                                Cls_Generic_Methods.customWait(5);
                                for (WebElement ele : oPage_TaxInvoiceDeliveryChallan.list_transactionCreatedList) {
                                    String type = Cls_Generic_Methods.getTextInElement(ele.findElement(By.xpath("./td[3]")));
                                    String status = Cls_Generic_Methods.getTextInElement(ele.findElement(By.xpath("./td[6]")));

                                    if (type.equalsIgnoreCase("Delivery Challan") &&
                                            status.equalsIgnoreCase("Open")) {
                                        Cls_Generic_Methods.clickElement(ele);
                                        Cls_Generic_Methods.customWait(5);
                                        Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_approve);
                                        Cls_Generic_Methods.customWait();

                                    }


                                }
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("" + e);
                        }
                    }


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
        } finally {
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        }
    }

    @Test(enabled = true, description = "Receive in store and validate the stock in item master")
    public void receiveInStoreAndValidateLot() {
        // 11	receive transfer in store
        // 12	validate item master stock

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        Page_Receive oPage_Receive = new Page_Receive(driver);

        boolean bApproveOrderFound = false;

        initializationStep();

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sROL_STORE);

            m_assert.assertInfo(Cls_Generic_Methods.switchToOtherTab(), "Switched to the second tab - " + driver.getTitle());
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Closing the Store Status Window");
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Receive");
            //Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.button_todayButtonInTransaction, 4);
            Cls_Generic_Methods.customWait(5);
            if (!mapTracker.get(key_NewTransactionInRequisitionReceived_1).isEmpty()) {
                for (WebElement eOrderTransferredOnDetail : oPage_Receive.list_transferredOnValueInRowOnTransactionReceive) {
                    String sLocalOrderTransferredOnDetail = Cls_Generic_Methods.getTextInElement(eOrderTransferredOnDetail);

                    String dateNdTime[] = sLocalOrderTransferredOnDetail.split("\n");
                    String date = dateNdTime[0].split(" ")[0];
                    String issueOnUi = dateNdTime[1];


                    if (mapTracker.get(key_NewTransactionInRequisitionReceived_1).contains(date) &&
                            issueNumber.equalsIgnoreCase(issueOnUi)) {

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

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.header_receiveTransactionOnModal, 8);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_Receive.select_subStore, sSubStore);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Receive.button_receiveTransactionOnModal),
                        "Clicked on Save Changes on Receive Transaction Modal");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.button_todayButtonInTransaction, 8);

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Receive.text_receiveTransactionInTableOnTransactionReceive),
                        "Validate that order Receive Details section is displayed on table");

                // validate item master stock

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

                Cls_Generic_Methods.customWait(2);
                oPage_Master.input_itemNameSearchInItemMaster.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(3);

                for (WebElement eItemName : oPage_Master.list_itemDescriptionNameOnItemMaster) {
                    String sItemName = Cls_Generic_Methods.getTextInElement(eItemName);

                    if (sItemName.equals(sROL_ITEM)) {
                        Cls_Generic_Methods.clickElement(eItemName);
                        Cls_Generic_Methods.customWait();
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

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        } finally {
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        }
    }

    public static void initializationStep() {

        if (debugEnabled) {

            mapTracker.put(key_CreatedAt_RequisitionOrderFromStore, "11-11-2022  |  01:08PM");
            mapTracker.put(key_NewTransactionInRequisitionReceived_1, "22-11-2022  |  02:05PM");
            mapTracker.put(key_RequisitionNumber_Created_InRequisition, "RSO-152810-10130");
            mapTracker.put(key_CreatedAt_PurchaseOrderTroughIndent, "22-11-2022  |  02:45PM");
            mapTracker.put(key_CreatedAt_PurchaseTransaction, "2022-11-11 | 03:17PM");

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

}
