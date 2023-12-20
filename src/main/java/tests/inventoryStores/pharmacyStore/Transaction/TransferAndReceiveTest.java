package tests.inventoryStores.pharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.templates.Page_InventorySearchCommonElements;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.store.PharmacyStore.Items.Page_Lot;
import pages.store.PharmacyStore.Order.Page_Requisition;
import pages.store.PharmacyStore.Page_TaxInvoiceDeliveryChallan;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_Sale;
import pages.store.PharmacyStore.Transaction.Page_Transfer;
import pages.store.PharmacyStore.Transaction.Page_Receive;
import tests.inventoryStores.pharmacyStore.InventorySearchTest;

import java.util.*;

import static pages.commonElements.CommonActions.selectOptionAndSearch;
import static tests.inventoryStores.pharmacyStore.Transaction.PurchaseGRNTest.convertStringToDouble;
import static tests.inventoryStores.pharmacyStore.Transaction.PurchaseGRNTest.itemDescription;

public class TransferAndReceiveTest extends TestBase {
    public static String sTRANSFER_STORE = "Pharmacy automation- Pharmacy";
    String sItemName = "Transfer Item 2";

    public static Map<String, String> mapTracker = new HashMap<String, String>();
    public static String key_ReceivingStore = "key_ReceivingStore";
    public static String key_sVariantCode = "key_sVariantCode";
    public static String key_sCostPrice = "key_sCostPrice";
    String transferDate = "";
    String transferId = "";
    String editItemName = "SalesOrderTest1";
    String sUpdatedTransferQty = "2";
    List<String> availableStockList = new ArrayList<>();
    List<String> stockList = new ArrayList<>();
    List<String> blockStockList = new ArrayList<>();

    List<String> costPriceList = new ArrayList<>();
    List<String> listPriceList = new ArrayList<>();
    List<String> amountPerItemList = new ArrayList<>();
    String addTransferHeaderList[] = {"#","Description","Barcode","GRN No.","GRN Date","Batch","Expiry","MRP","Rate",
            "Qty","Amount","Action"};
    List<String> addTransferDataList = new ArrayList<>();
    List<String> addTranssactionDetailsDataList = new ArrayList<>();
    String sReceivingStore = "OpticalStore";
    List<String> itemVariantCodeList = new ArrayList<>();
    String sTransactionNote = "TranNote"+CommonActions.getRandomString(4);
    String receiveTransactionId,deliveryChallanId;
    String oldReceiveNo = "";
    String receiveTransactionHeaderList [] = {"#","Description","Batch No.","MRP","Expiry","Transfered Quantity",
            "Qty. Received","Qty. Not Received","Not Received Comment"};
    String receiveTransferDetailsHeaderList [] = {"#","Description","Code","Batch No.","Expiry","Transfered Quantity"
            ,"Quantity Not Received","Not Received Comment","Total Price"};
    String receiveDetailsHeaderList [] = {"#","Description","Code","Batch No.","Expiry","Quantity",
            "MRP","Total Price"};
    List<String> receiveTransactionDataList = new ArrayList<>();
    List<String> receiveTransferDetailsDataList = new ArrayList<>();
    List<String> receiveDetailsDataList = new ArrayList<>();
    String myMedicationName[] = {sItemName,editItemName};
    List<String> stockBeforeList = new ArrayList<>();


    @Test(enabled = true, description = "Create Transfer Transaction")
    public void createTransferTransaction() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        Page_Lot oPage_Lot = new Page_Lot(driver);
        String sQuantity1 = "10000";
        boolean itemFound = false;
        boolean TransactionFound = false;
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sTRANSFER_STORE);
            Cls_Generic_Methods.customWait(2);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();
            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItem, 10);

                m_assert.assertTrue(searchAndSelectItem(oPage_Transfer.input_searchItem,sItemName,oPage_Transfer.list_itemsUnderLot),
                        "Selected a item to store lot details: <b> " + sItemName + "</b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_availableStock, 5);
                String sAvailableStock = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_availableStock);
                m_assert.assertInfo("Available stock before transfer = <b>" + sAvailableStock + "</b>");
                availableStockList.add(sAvailableStock);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_itemVarientCode, 5);
                String sVariantCode = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_itemVarientCode);
                itemVariantCodeList.add(sVariantCode);
                mapTracker.put(key_sVariantCode, sVariantCode);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_costPriceOfItem, 5);
                String sCostPriceOfItem = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_costPriceOfItem);
                mapTracker.put(key_sCostPrice, sCostPriceOfItem);
                costPriceList.add(sCostPriceOfItem);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_stock, 5);
                String sStockBeforeTransfer = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_stock);
                stockList.add(sStockBeforeTransfer);
                m_assert.assertInfo("Stock before transfer = <b>" + sStockBeforeTransfer + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_blockedStock, 5);
                String sBlockedStock = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_blockedStock);
                m_assert.assertInfo("Blocked Stock before transfer = <b>" + sBlockedStock + "</b>");
                blockStockList.add(sBlockedStock);
                String mrpPrice = Cls_Generic_Methods.getTextInElement(oPage_Lot.text_mrpPrice);
                listPriceList.add(mrpPrice);

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_newTransaction, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_newTransaction),
                        "Clicked on New button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.header_addTransferHeader, 5);

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.header_addTransferHeader),
                        " Transfer Item Header Displayed");

                mapTracker.put(key_ReceivingStore, sReceivingStore + "- Optical");
                m_assert.assertTrue(selectByOptions(oPage_Transfer.dropdown_receivingStore, sReceivingStore,oPage_Transfer.list_receivingStore),
                        "Receiving store selected as : "+ sReceivingStore);

                transferDate = Cls_Generic_Methods.getElementAttribute(oPage_Transfer.input_transactionDate,"value");
                transferDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy","dd-MM-yyyy",transferDate);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_searchByDescription, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_searchByDescription),
                        "Clicked on Description button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItemToBeTransferred, 5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_searchItemToBeTransferred, sItemName),
                        "<b> " + sItemName + "</b> Entered in search item field");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItemToBeTransferred, 5);
                oPage_Transfer.input_searchItemToBeTransferred.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(3);
                for (WebElement eItemVariantCode : oPage_Transfer.list_itemsCanBeTransferred) {
                    if (Cls_Generic_Methods.getTextInElement(eItemVariantCode).equalsIgnoreCase(sVariantCode)) {
                        Cls_Generic_Methods.clickElement(eItemVariantCode);
                        itemFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(itemFound, "Found a item having variant code = <b> " + sVariantCode + "</b>");
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.list_quantityFieldForItemsToTransfer.get(0), sQuantity1),
                        "Entered in Quantity field = <b> " + sQuantity1 + "</b>");

                Cls_Generic_Methods.clickElement(oPage_Transfer.button_saveChanges);
                Cls_Generic_Methods.customWait(3);
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.text_ValidationForTransferQuantity)) {
                    m_assert.assertTrue("Validation displayed: " + Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_ValidationForTransferQuantity));
                } else {
                    m_assert.assertTrue(false, "Validation for quantity: <b>Cannot checkout more than stock</b>");
                }

                Cls_Generic_Methods.clearValuesInElement(oPage_Transfer.list_quantityFieldForItemsToTransfer.get(0));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.list_quantityFieldForItemsToTransfer.get(0), sUpdatedTransferQty),
                        " Entered in Quantity field =  <b>" + sUpdatedTransferQty + "</b>");

                Cls_Generic_Methods.customWait(2);
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.text_ValidationForTransferQuantity), "Validations are not displaying");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_transactionNote, 5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_transactionNote, sTransactionNote),
                        "<b> " + sTransactionNote + "</b> is Entered as Transaction note");
                String cost = sCostPriceOfItem.replace("₹","");
                Double totalAmountIncTaxCal = Double.parseDouble(cost)*Double.parseDouble(sUpdatedTransferQty);
                String totalAmountIncTaxOnUI = Cls_Generic_Methods.getElementAttribute(oPage_Transfer.input_totalAmountIncludingTax, "value");
                m_assert.assertTrue(totalAmountIncTaxOnUI.equalsIgnoreCase(String.format("%.2f",totalAmountIncTaxCal)),
                        "Total Amount Including Tax Displayed Correctly as : "+totalAmountIncTaxOnUI);

                for(WebElement header: oPage_Transfer.list_transactionHeaderList){
                    String headerText = Cls_Generic_Methods.getTextInElement(header);
                    int index = oPage_Transfer.list_transactionHeaderList.indexOf(header);
                    if(headerText.equalsIgnoreCase(addTransferHeaderList[index])){
                        m_assert.assertTrue(headerText+" Header Present In Add Transfer");
                    }else{
                        m_assert.assertWarn("Either Column Missing or Changed");
                    }
                }
                addDataInTransfer();
                int dataIndex  = 0 ;
                for(WebElement data: oPage_Transfer.list_transactionDataList){
                    String headerText = Cls_Generic_Methods.getTextInElement(data);
                    if(dataIndex == 11){
                        m_assert.assertTrue("Delete Button Present In Add Transfer");
                    }
                    else if(headerText.contains(addTransferDataList.get(dataIndex))){
                        m_assert.assertTrue(headerText+" Value Present In Add Transfer");
                    }
                    if(dataIndex == 10){
                        amountPerItemList.add(Cls_Generic_Methods.getElementAttribute(data.findElement(By.xpath("./input[1]")),"value"));
                    }
                    dataIndex++;
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_saveChanges, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_saveChanges), "Transfer Transaction saved");
                Cls_Generic_Methods.customWait(5);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItem, 5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_searchItem, sItemName),
                        "<b> " + sItemName + "</b> Entered in search item field");;
                Cls_Generic_Methods.customWait(3);
                oPage_Transfer.input_searchItem.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait();

                for (WebElement eItem : oPage_Transfer.list_variantCodeUnderLot) {
                    if (Cls_Generic_Methods.getTextInElement(eItem).equalsIgnoreCase(sVariantCode)) {
                        Cls_Generic_Methods.clickElement(eItem);
                        itemFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(itemFound, "Selected a item having a Variant code = <b> " + sVariantCode + "</b> " + "to validate lot details");

                double dBlockedStock = Double.parseDouble(sBlockedStock);
                double dUpdatedTransferQty = Double.parseDouble(sUpdatedTransferQty);
                double dAvailableStock = Double.parseDouble(sAvailableStock);
                double dBlockedStockAfterTransfer;
                double dAvailableStockAfterTransfer;
                dAvailableStockAfterTransfer = dAvailableStock - dUpdatedTransferQty;
                dBlockedStockAfterTransfer = dBlockedStock + dUpdatedTransferQty;

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_stock, 10);
                String sStockAfterTransfer = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_stock);
                double dStockAfterTransfer = Double.parseDouble(sStockAfterTransfer);
                double dStockBeforeTransfer = Double.parseDouble(sStockBeforeTransfer);
                m_assert.assertTrue(dStockAfterTransfer == dStockBeforeTransfer, "stock after transfer =  <b>" + dStockAfterTransfer + "<b>");
                String sBlockedStockAfterTransferOnUI = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_blockedStock);
                double dBlockedStockAfterTransferOnUI = Double.parseDouble(sBlockedStockAfterTransferOnUI);
                m_assert.assertTrue(dBlockedStockAfterTransferOnUI == dBlockedStockAfterTransfer, "Calculated blocked stock after transfer =  <b>" + dBlockedStockAfterTransferOnUI + "<b>");
                String sAvailableStockAfterTransferOnUI = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_availableStock);
                double dAvailableStockAfterTransferOnUI = Double.parseDouble(sAvailableStockAfterTransferOnUI);
                m_assert.assertTrue(dAvailableStockAfterTransfer == dAvailableStockAfterTransferOnUI, "Calculated Available stock after transfer =  <b>" + dAvailableStockAfterTransferOnUI + "<b>");
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");

                Cls_Generic_Methods.customWait(3);

                //Update as per sprint 69
                //validate the add new stock table columns
                String[] columns = {"Against", "Transfer / Issue Info", "Requisition Info", "Receive Info"};

                for (WebElement eHeader : oPage_Transfer.list_tableHeaderTransfer) {
                    int index = oPage_Transfer.list_tableHeaderTransfer.indexOf(eHeader);
                    String headerText = Cls_Generic_Methods.getTextInElement(eHeader);
                    m_assert.assertTrue(headerText.equalsIgnoreCase(columns[index]),
                            "Transfer columns verified and displayed as : "+columns[index]);

                }

                for (WebElement eValue : oPage_Transfer.list_tableDataTransfer) {
                    List<WebElement> purchaseRow = eValue.findElements(By.xpath("./td"));
                    String againstTxt = Cls_Generic_Methods.getTextInElement(purchaseRow.get(1));
                    String transferIssueInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(2));
                    String reqInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(3));
                    String recInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(4));
                    if(againstTxt.replace("\n"," ").equalsIgnoreCase("Direct "+ sReceivingStore) &&
                            transferIssueInfo.contains(transferDate) &&
                            reqInfo.isEmpty() &&
                            recInfo.isEmpty()
                    ){
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(eValue),
                                "Transfer Record Clicked And Its Direct Store Type Transfer");
                        TransactionFound = true;
                        Cls_Generic_Methods.customWait(5);
                        break ;

                    }

                }
                m_assert.assertTrue(TransactionFound, "Transfer transaction found");
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.customWait();

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }
    @Test(enabled = true, description = "View Transfer Transaction")
    public void viewTransferTransaction() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        String sRecievingStore = "OpticalStore";
        boolean TransactionFound = false;
        String transferInfoInTable = "";
        String transactionDetailsHeader[]={"#","Description","Batch No.","Expiry","Transfered Quantity","MRP","Total Price"};

        try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            getTransferIdFromSetting();
            CommonActions.selectStoreOnApp(sTRANSFER_STORE);
            Cls_Generic_Methods.customWait(2);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();
            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
                Cls_Generic_Methods.customWait();
                for (WebElement eValue : oPage_Transfer.list_tableDataTransfer) {
                    List<WebElement> purchaseRow = eValue.findElements(By.xpath("./td"));
                    String againstTxt = Cls_Generic_Methods.getTextInElement(purchaseRow.get(1));
                    String transferIssueInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(2));
                    String reqInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(3));
                    String recInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(4));
                    transferInfoInTable = transferIssueInfo;
                    if(againstTxt.replace("\n"," ").equalsIgnoreCase("Direct "+sRecievingStore) &&
                            transferIssueInfo.contains(transferDate) &&
                            transferIssueInfo.contains(transferId)&&
                            transferIssueInfo.contains(EHR_Data.user_PRAkashTest)&&
                            reqInfo.isEmpty() &&
                            recInfo.isEmpty()
                    ){
                        Cls_Generic_Methods.clickElement(eValue);
                        TransactionFound = true;
                        Cls_Generic_Methods.customWait(5);
                        break ;

                    }


                }

                m_assert.assertTrue(TransactionFound, "Transfer transaction found and clicked");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.button_refreshButton),
                        " Refresh Button Displayed In Transfer");

                String dateAndTime = transferInfoInTable.split("\n")[1];

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_transferText).
                                contains(transferInfoInTable.split("\n")[0]),
                        " Transfer Id Present In View Transfer as "+transferInfoInTable.split("\n")[0]);
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_transferText).
                                contains(dateAndTime.split(" ")[0]),
                        "Transfer Date Displayed as "+dateAndTime.split(" ")[0]);
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_transferText).
                                contains(dateAndTime.split(" ")[1]),
                        "Transfer Time Displayed as "+dateAndTime.split(" ")[1]);

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_transferCreatedBy).contains(EHR_Data.user_PRAkashTest),
                        " Transfer Created By User Name Displayed Correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_transferCreatedBy).
                                contains(dateAndTime.split(" ")[0]),
                        "Transfer Created By  Date Displayed as "+dateAndTime.split(" ")[0]);
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_transferCreatedBy).
                                contains(dateAndTime.split(" ")[1]),
                        "Transfer Created By Time Displayed as "+dateAndTime.split(" ")[1]);
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_transferCreatedBy).
                                contains("Pharmacy automation - Pharmacy"),
                        "Transfer Created By Store Displayed as "+sTRANSFER_STORE);

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_transactionStatus).equalsIgnoreCase("open"),
                        " Transfer Status Displayed Correctly as : open ");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_receiveStatus).equalsIgnoreCase("Receive Status : None"),
                        " Receive Status Displayed as None");

                boolean printA4InSale = validatePrintButtonFunctionality(oPage_Purchase.button_printA4SizeButton,"Print4 Size In Sale Transaction");
                boolean printA5InSale = validatePrintButtonFunctionality(oPage_Purchase.button_printA5SizeButton,"Print5 Size In Sale Transaction");

                m_assert.assertTrue(printA4InSale," PrintA4 Working  In Transfer Transaction");
                m_assert.assertTrue(printA5InSale," PrintA5 Working  In Transfer Transaction");

                int i = 0 ;
                for(WebElement header : oPage_Transfer.list_transactionDetailsHeaderList){
                    String headerText = Cls_Generic_Methods.getTextInElement(header);
                    if(headerText.equalsIgnoreCase(transactionDetailsHeader[i])){
                        m_assert.assertTrue(headerText+" Header Text Displayed");
                    }
                    i++;
                }
                addDataForTransactionDetails();
                int j = 0 ;
                for(WebElement header : oPage_Transfer.list_transactionDetailsDataList){
                    String headerText = Cls_Generic_Methods.getTextInElement(header);
                    if(headerText.contains(addTranssactionDetailsDataList.get(j))){
                        m_assert.assertTrue(headerText+" Value Text Displayed");
                    }
                    i++;
                }

                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.customWait();

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }
    @Test(enabled = true, description = "Edit Transfer Transaction")
    public void editTransferTransaction() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_Lot oPage_Lot = new Page_Lot(driver);
        String sRecievingStore = "OpticalStore";
        boolean TransactionFound = false;
        boolean itemFound = false;


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            getTransferIdFromSetting();
            CommonActions.selectStoreOnApp(sTRANSFER_STORE);
            Cls_Generic_Methods.customWait(2);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();
            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItem, 10);

                m_assert.assertTrue(searchAndSelectItem(oPage_Transfer.input_searchItem,editItemName,oPage_Transfer.list_itemsUnderLot),
                        "Selected a item to store lot details: <b> " + editItemName + "</b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_availableStock, 5);
                String sAvailableStock = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_availableStock);
                m_assert.assertInfo("Available stock before transfer = <b>" + sAvailableStock + "</b>");
                availableStockList.add(sAvailableStock);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_itemVarientCode, 5);
                String sVariantCode = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_itemVarientCode);
                itemVariantCodeList.add(sVariantCode);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_costPriceOfItem, 5);
                String sCostPriceOfItem = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_costPriceOfItem);
                mapTracker.put(key_sCostPrice, sCostPriceOfItem);
                costPriceList.add(sCostPriceOfItem);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_stock, 5);
                String sStockBeforeTransfer = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_stock);
                m_assert.assertInfo("Stock before transfer = <b>" + sStockBeforeTransfer + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_blockedStock, 5);
                String sBlockedStock = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_blockedStock);
                m_assert.assertInfo("Blocked Stock before transfer = <b>" + sBlockedStock + "</b>");
                stockList.add(sStockBeforeTransfer);
                blockStockList.add(sBlockedStock);

                String mrpPrice = Cls_Generic_Methods.getTextInElement(oPage_Lot.text_mrpPrice);
                listPriceList.add(mrpPrice);

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
                Cls_Generic_Methods.customWait();
                for (WebElement eValue : oPage_Transfer.list_tableDataTransfer) {
                    List<WebElement> purchaseRow = eValue.findElements(By.xpath("./td"));
                    String againstTxt = Cls_Generic_Methods.getTextInElement(purchaseRow.get(1));
                    String transferIssueInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(2));
                    String reqInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(3));
                    String recInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(4));
                    if(againstTxt.replace("\n"," ").equalsIgnoreCase("Direct "+sRecievingStore) &&
                            transferIssueInfo.contains(transferDate) &&
                            transferIssueInfo.contains(transferId)&&
                            transferIssueInfo.contains(EHR_Data.user_PRAkashTest)&&
                            reqInfo.isEmpty() &&
                            recInfo.isEmpty()
                    ){
                        Cls_Generic_Methods.clickElement(eValue);
                        TransactionFound = true;
                        Cls_Generic_Methods.customWait(5);
                        break ;

                    }

                }

                m_assert.assertTrue(TransactionFound, "Transfer transaction found and clicked");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_editPurchaseTransaction),
                        "Edit Button Displayed and Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.header_addTransferHeader,10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_searchByDescription), "Clicked on Description button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItemToBeTransferred, 5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_searchItemToBeTransferred, editItemName),
                        "<b> " + editItemName + "</b> Entered in search item field");
                Cls_Generic_Methods.customWait( 3);
                oPage_Transfer.input_searchItemToBeTransferred.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(3);
                for (WebElement eItemVariantCode : oPage_Transfer.list_itemDescriptionRow) {
                    if (Cls_Generic_Methods.getTextInElement(eItemVariantCode).equalsIgnoreCase(editItemName)) {
                        Cls_Generic_Methods.clickElement(eItemVariantCode);
                        itemFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(itemFound, "Found a item = <b> " + editItemName + "</b> and clicked");
                Cls_Generic_Methods.customWait(3);

                for(WebElement delete : oPage_Transfer.list_deleteItemButtons){
                    int index = oPage_Transfer.list_deleteItemButtons.indexOf(delete);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(delete),
                            "Delete Button Clicked at "+index);
                }

                int totalItemAfterDelete = oPage_Transfer.list_deleteItemButtons.size();
                m_assert.assertTrue(totalItemAfterDelete == 1,"Last item not deleted" );

                Cls_Generic_Methods.clearValuesInElement(oPage_Transfer.input_searchItemToBeTransferred);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_searchItemToBeTransferred, sItemName),
                        "<b> " + sItemName + "</b> Entered in search item field");
                Cls_Generic_Methods.customWait( 3);
                oPage_Transfer.input_searchItemToBeTransferred.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(3);
                for (WebElement eItemVariantCode : oPage_Transfer.list_itemDescriptionRow) {
                    if (Cls_Generic_Methods.getTextInElement(eItemVariantCode).equalsIgnoreCase(sItemName)) {
                        Cls_Generic_Methods.clickElement(eItemVariantCode);
                        itemFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(itemFound, "Found a item = <b> " + sItemName + "</b> and clicked");
                Cls_Generic_Methods.customWait(3);

                Double totalAmountIncTaxCal = 0.0;
                String costPriceList [] = {"105.0","105.0"};
                for( WebElement qty : oPage_Transfer.list_quantityFieldForItemsToTransfer){
                    int index = oPage_Transfer.list_quantityFieldForItemsToTransfer.indexOf(qty);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(qty, sUpdatedTransferQty),
                            "Entered in Quantity field = <b> " + sUpdatedTransferQty + "</b> at index "+index);
                    totalAmountIncTaxCal = totalAmountIncTaxCal + (Double.parseDouble(costPriceList[index])*Double.parseDouble(sUpdatedTransferQty));

                }

                amountPerItemList.add(Cls_Generic_Methods.getElementAttribute(oPage_Transfer.list_transactionDataAmountList.get(1).findElement(By.xpath("./input[1]")),"value"));
                String totalAmtIncTaxUI = Cls_Generic_Methods.getElementAttribute(oPage_Transfer.input_totalAmountIncludingTax,"value");
                m_assert.assertTrue(totalAmtIncTaxUI.equalsIgnoreCase(String.format("%.2f",totalAmountIncTaxCal)),
                        " Total Amount Including tax calculated properly");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_saveChanges),
                        "Save Button Clicked In Edit ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_newTransaction,10);

                boolean editTransfer = false ;
                for (WebElement eValue : oPage_Transfer.list_tableDataTransfer) {
                    List<WebElement> purchaseRow = eValue.findElements(By.xpath("./td"));
                    String againstTxt = Cls_Generic_Methods.getTextInElement(purchaseRow.get(1));
                    String transferIssueInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(2));
                    String reqInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(3));
                    String recInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(4));
                    if(againstTxt.replace("\n"," ").equalsIgnoreCase("Direct "+sRecievingStore) &&
                            transferIssueInfo.contains(transferDate) &&
                            transferIssueInfo.contains(transferId)&&
                            transferIssueInfo.contains(EHR_Data.user_PRAkashTest)&&
                            reqInfo.isEmpty() &&
                            recInfo.isEmpty()
                    ){
                        Cls_Generic_Methods.clickElement(eValue);
                        editTransfer = true;
                        Cls_Generic_Methods.customWait(5);
                        break ;

                    }

                }

                m_assert.assertTrue(editTransfer, "Edit Transfer transaction found and clicked");

                int k = 0;
                boolean editFound = false;
                for(WebElement data : oPage_Transfer.list_transactionDetailsItemDataList){
                    String itemText = Cls_Generic_Methods.getTextInElement(data);
                    String totalPrice = Cls_Generic_Methods.getTextInElement(oPage_Transfer.list_transactionDetailsTotalPriceDataList.get(k));
                    if(itemText.contains(sItemName) || itemText.contains(editItemName)
                            && amountPerItemList.get(k).contains(totalPrice)){
                        editFound = true;
                        m_assert.assertTrue(itemText+ " Item present in View Transfer");
                    }
                    k++;
                }

                m_assert.assertTrue(editFound, "Edit Transfer transaction Data found");

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItem, 5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_searchItem, editItemName),
                        "<b> " + editItemName + "</b> Entered in search item field");;
                Cls_Generic_Methods.customWait(3);
                oPage_Transfer.input_searchItem.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait();

                for (WebElement eItem : oPage_Transfer.list_variantCodeUnderLot) {
                    if (Cls_Generic_Methods.getTextInElement(eItem).equalsIgnoreCase(sVariantCode)) {
                        Cls_Generic_Methods.clickElement(eItem);
                        itemFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(itemFound, "Selected a item having a Variant code = <b> " + sVariantCode + "</b> " + "to validate lot details");

                double dBlockedStock = Double.parseDouble(sBlockedStock);
                double dUpdatedTransferQty = Double.parseDouble(sUpdatedTransferQty);
                double dAvailableStock = Double.parseDouble(sAvailableStock);
                double dBlockedStockAfterTransfer;
                double dAvailableStockAfterTransfer;
                dAvailableStockAfterTransfer = dAvailableStock - dUpdatedTransferQty;
                dBlockedStockAfterTransfer = dBlockedStock + dUpdatedTransferQty;

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_stock, 10);
                String sStockAfterTransfer = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_stock);
                double dStockAfterTransfer = Double.parseDouble(sStockAfterTransfer);
                double dStockBeforeTransfer = Double.parseDouble(sStockBeforeTransfer);
                m_assert.assertTrue(dStockAfterTransfer == dStockBeforeTransfer, "stock after transfer =  <b>" + dStockAfterTransfer + "<b>");
                String sBlockedStockAfterTransferOnUI = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_blockedStock);
                double dBlockedStockAfterTransferOnUI = Double.parseDouble(sBlockedStockAfterTransferOnUI);
                m_assert.assertTrue(dBlockedStockAfterTransferOnUI == dBlockedStockAfterTransfer, "Calculated blocked stock after transfer =  <b>" + dBlockedStockAfterTransferOnUI + "<b>");
                String sAvailableStockAfterTransferOnUI = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_availableStock);
                double dAvailableStockAfterTransferOnUI = Double.parseDouble(sAvailableStockAfterTransferOnUI);
                m_assert.assertTrue(dAvailableStockAfterTransfer == dAvailableStockAfterTransferOnUI, "Calculated Available stock after transfer =  <b>" + dAvailableStockAfterTransferOnUI + "<b>");

                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.customWait();

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }
    @Test(enabled = true, description = "Approve Transfer Transaction")
    public void approveTransferTransaction() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        String sRecievingStore = "OpticalStore";
        boolean TransactionFound = false;
        Page_TaxInvoiceDeliveryChallan oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            getTransferIdFromSetting();
            CommonActions.selectStoreOnApp(sTRANSFER_STORE);
            Cls_Generic_Methods.customWait(2);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();
            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
                Cls_Generic_Methods.customWait();
                for (WebElement eValue : oPage_Transfer.list_tableDataTransfer) {
                    List<WebElement> purchaseRow = eValue.findElements(By.xpath("./td"));
                    String againstTxt = Cls_Generic_Methods.getTextInElement(purchaseRow.get(1));
                    String transferIssueInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(2));
                    String reqInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(3));
                    String recInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(4));
                    if(againstTxt.replace("\n"," ").equalsIgnoreCase("Direct "+sRecievingStore) &&
                            transferIssueInfo.contains(transferDate) &&
                            transferIssueInfo.contains(transferId)&&
                            transferIssueInfo.contains(EHR_Data.user_PRAkashTest)&&
                            reqInfo.isEmpty() &&
                            recInfo.isEmpty()
                    ){
                        Cls_Generic_Methods.clickElement(eValue);
                        TransactionFound = true;
                        Cls_Generic_Methods.customWait(5);
                        break ;

                    }

                }

                m_assert.assertTrue(TransactionFound, "Transfer transaction found and clicked");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_approveTransferTransaction), "Clicked on Approve button");
                Cls_Generic_Methods.customWait( 5);
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_transactionStatus).equalsIgnoreCase("approved"),
                        " Transfer Status Displayed Correctly as : approved ");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_transferApprovedBy).contains(EHR_Data.user_PRAkashTest),
                        " Transfer Approved By User Name Displayed Correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_transferCreatedBy).
                                contains(transferDate),
                        "Transfer Approved By Date Displayed as "+transferDate);
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
                        selectByOptions(oPage_TaxInvoiceDeliveryChallan.select_againstStore, sReceivingStore);
                        Cls_Generic_Methods.customWait(2);
                        for (WebElement lhsRow : oPage_TaxInvoiceDeliveryChallan.row_lhsCreateTaxInvoiceDeliveryChallan) {
                            String transferDate = Cls_Generic_Methods.getTextInElement(lhsRow.findElement(By.xpath("./td[3]")));

                            if (transferDate.contains(transferId)) {
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
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_approve),
                                        " Approve Button Clicked in Tax");
                                Cls_Generic_Methods.customWait();
                            }
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }



                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.customWait();

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }
    @Test(enabled = true, description = "Receive Stock Transaction")
    public void receiveStockTransaction() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Receive oPage_Receive = new Page_Receive(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        boolean VariantCodeFound = false;
        boolean TransactionFound = false;
        String receiveInfoInTable = "";
        String trsnsferInfoInTable = "";
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            getTransferIdFromSetting();
            CommonActions.selectStoreOnApp(mapTracker.get(key_ReceivingStore));
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait(3);
            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Variant");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.input_searchItem, 5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Receive.input_searchItem, sItemName),
                        "<b> " + sItemName + "</b> Entered in search item field");
                Cls_Generic_Methods.customWait(3);
                oPage_Receive.input_searchItem.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(3);
                for (WebElement eVariantCode : oPage_Receive.list_variantCode) {
                    if (Cls_Generic_Methods.getTextInElement(eVariantCode).equalsIgnoreCase(mapTracker.get(key_sVariantCode))) {
                        Cls_Generic_Methods.clickElement(eVariantCode);
                        VariantCodeFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(VariantCodeFound, "Exact Variant code of a item found = <b>" + mapTracker.get(key_sVariantCode) + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.text_itemDescriptionUnderVariantFolder, 5);
                String sItemDescription = Cls_Generic_Methods.getTextInElement(oPage_Receive.text_itemDescriptionUnderVariantFolder);
                Cls_Generic_Methods.customWait(3);
                if (sItemDescription.equalsIgnoreCase(sItemName)) {
                    m_assert.assertTrue(true, "Selected exact item = <b>" + sItemDescription + "</b>");
                } else {
                    m_assert.assertTrue(false, "Selected Wrong item = <b>" + sItemDescription + "</b>");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.text_stockCountUnderVariantFolder, 5);
                String sStockCountBeforeReceive = Cls_Generic_Methods.getTextInElement(oPage_Receive.text_stockCountUnderVariantFolder);
                m_assert.assertInfo("stock before count Receive = <b>" + sStockCountBeforeReceive + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_itemVarientCode, 5);
                stockBeforeList.add(sStockCountBeforeReceive);

                Cls_Generic_Methods.clearValuesInElement(oPage_Receive.input_searchItem);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Receive.input_searchItem, editItemName),
                        "<b> " + editItemName + "</b> Entered in search item field");
                Cls_Generic_Methods.customWait(3);
                oPage_Receive.input_searchItem.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(3);
                for (WebElement eVariantCode : oPage_Transfer.list_variantCode) {
                    if (Cls_Generic_Methods.getTextInElement(eVariantCode).equalsIgnoreCase("INV-MED-1114-100")) {
                        Cls_Generic_Methods.clickElement(eVariantCode);
                        Cls_Generic_Methods.customWait(5);
                        VariantCodeFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(VariantCodeFound, "Exact Variant code of a item found = <b>" + mapTracker.get(key_sVariantCode) + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.text_itemDescriptionUnderVariantFolder, 5);
                String sItemDescription2 = Cls_Generic_Methods.getTextInElement(oPage_Receive.text_itemDescriptionUnderVariantFolder);
                Cls_Generic_Methods.customWait(3);
                if (sItemDescription2.equalsIgnoreCase(editItemName)) {
                    m_assert.assertTrue(true, "Selected exact item = <b>" + sItemDescription + "</b>");
                } else {
                    m_assert.assertTrue(false, "Selected Wrong item = <b>" + sItemDescription + "</b>");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.text_stockCountUnderVariantFolder, 5);
                String sStockCountBeforeReceive2 = Cls_Generic_Methods.getTextInElement(oPage_Receive.text_stockCountUnderVariantFolder);
                m_assert.assertInfo("stock before count Receive = <b>" + sStockCountBeforeReceive + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_itemVarientCode, 5);
                stockBeforeList.add(sStockCountBeforeReceive2);

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait(2);

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Receive");
                Cls_Generic_Methods.customWait(3);

                String[] columns = {"#","Against", "Requisition Info","Transfer / Issue Info", "Receive Info"};

                for (WebElement eHeader : oPage_Transfer.list_tableHeaderTransfer) {
                    int index = oPage_Transfer.list_tableHeaderTransfer.indexOf(eHeader);
                    String headerText = Cls_Generic_Methods.getTextInElement(eHeader);
                    m_assert.assertTrue(headerText.equalsIgnoreCase(columns[index]),
                            "Receive columns Header verified and displayed as : "+columns[index]);

                }

                for (WebElement eTransaction : oPage_Transfer.list_tableDataTransfer) {

                    List<WebElement> allOptions = eTransaction.findElements(By.xpath("./td"));
                    String srNo = Cls_Generic_Methods.getTextInElement(allOptions.get(0));
                    String againstInRec = Cls_Generic_Methods.getTextInElement(allOptions.get(1));
                    String reqInfoInRec = Cls_Generic_Methods.getTextInElement(allOptions.get(2));
                    String transferInfoInRec = Cls_Generic_Methods.getTextInElement(allOptions.get(3));
                    String receiveInfoInRec = Cls_Generic_Methods.getTextInElement(allOptions.get(4));
                    trsnsferInfoInTable = transferInfoInRec;

                    if (srNo.equalsIgnoreCase("1") &&
                            againstInRec.equalsIgnoreCase("Direct\n" +
                                    "Pharmacy automation") &&
                            reqInfoInRec.isEmpty() &&
                            transferInfoInRec.contains(transferDate) &&
                            transferInfoInRec.contains(transferId) &&
                            transferInfoInRec.contains(EHR_Data.user_PRAkashTest) &&
                            receiveInfoInRec.equalsIgnoreCase("Pending: None")) {

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(eTransaction),
                                " Transfer Record Clicked In Receive Transaction");
                        Cls_Generic_Methods.customWait(5);
                        TransactionFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(TransactionFound, "Transfer Transaction under Receive Transaction Module");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.button_receiveStock, 5);

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.header_requisitionViewDetailsDropdown),
                        " Transfer Collapse/Expand Dropdown Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_Transfer.header_requisitionViewDetailsDropdown,"data-toggle").equalsIgnoreCase("collapse"),
                        " Transfer Collapse/Expand Dropdown Displayed and By Default its Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionNumber).equalsIgnoreCase(transferId),
                        " Transfer Number Displayed Correctly as "+transferId);
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_transferDateAndTime).contains(transferDate),
                        " Transfer  Date and time Displayed Correctly as ");

                m_assert.assertFalse(Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.text_requisitionCreatedBy),
                        " Transfer Created By Is not displayed correctly as its hidden");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.header_requisitionViewDetailsDropdown),
                        " Transfer Collapse/Expand Dropdown Clicked for Expand");

                String eTransferCreatedBy = EHR_Data.user_PRAkashTest+" | "+transferDate+" | ";
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionCreatedBy).contains(eTransferCreatedBy),
                        " Transfer Created Displayed Correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionCreatedBy).contains(sTRANSFER_STORE.split("-")[0]),
                        " Transfer Created Store Displayed Correctly as : "+Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionCreatedBy));

                String eTransferApprovedBy = EHR_Data.user_PRAkashTest+" | "+transferDate+" | ";
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionApprovedBy).contains(eTransferApprovedBy),
                        " Transfer Approved Displayed Correctly as "+ Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionApprovedBy));

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_transferStore).contains(sTRANSFER_STORE.split("-")[0]),
                        " Transfer To Store Displayed Correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_transferStatus).contains("approved"),
                        " Transfer Status Displayed Correctly");


                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_receiveTransactionId).contains(receiveTransactionId),
                        " Receive Transaction Id Text Displayed correctly as Receive Transaction ID:   "+receiveTransactionId);

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_deliveryChallanNumber).contains(deliveryChallanId),
                        " Delivery Challan Id Text Displayed correctly as Delivery Challan No: "+deliveryChallanId);

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_receiveFulfilmentStatus).equalsIgnoreCase("None"),
                        " Receive Status Text Displayed correctly as Receive Status: None");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Receive.button_receiveStock),
                        "Clicked on Receive button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.text_itemDescriptionUnderReceiveTransactionPopUp, 5);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Receive.header_receiveTransactionOnModal),
                        " Receive Transaction Popup Opened and Displayed as : Receive Transaction");

                for(WebElement header: oPage_Transfer.list_transactionHeaderList){
                    String headerText = Cls_Generic_Methods.getTextInElement(header);
                    int index = oPage_Transfer.list_transactionHeaderList.indexOf(header);
                    if(headerText.equalsIgnoreCase(receiveTransactionHeaderList[index])){
                        m_assert.assertTrue(headerText+" Header Present In Receive Transaction");
                    }else{
                        m_assert.assertWarn("Either Column Missing or Changed");
                    }
                }

                addDataReceiveTransactionData();
                for(WebElement header: oPage_Transfer.list_transactionDataList){
                    String headerText = Cls_Generic_Methods.getTextInElement(header);
                    int index = oPage_Transfer.list_transactionDataList.indexOf(header);
                    if(headerText.equalsIgnoreCase(receiveTransactionDataList.get(index))){
                        m_assert.assertTrue(headerText+ " Value Present In Receive Transaction as Sent From Transfer ");
                    }else{
                        m_assert.assertWarn("Either Column Missing or Changed");
                    }
                }

                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Receive.button_receiveStock),
                        " Receive Button Displayed After Closing Popup Without Save");
                Cls_Generic_Methods.clickElement(oPage_Receive.button_receiveStock);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.header_receiveTransactionOnModal,5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Receive.button_saveChanges),
                        " Save Changes Button Clicked In Receive Transaction ");
                Cls_Generic_Methods.customWait(5);

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForPageLoad(driver,6);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait(3);

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Receive");
                Cls_Generic_Methods.customWait(3);
                boolean receivedRecord = false ;
                for (WebElement eTransaction : oPage_Transfer.list_tableDataTransfer) {

                    List<WebElement> allOptions = eTransaction.findElements(By.xpath("./td"));
                    String srNo = Cls_Generic_Methods.getTextInElement(allOptions.get(0));
                    String againstInRec = Cls_Generic_Methods.getTextInElement(allOptions.get(1));
                    String reqInfoInRec = Cls_Generic_Methods.getTextInElement(allOptions.get(2));
                    String transferInfoInRec = Cls_Generic_Methods.getTextInElement(allOptions.get(3));
                    String receiveInfoInRec = Cls_Generic_Methods.getTextInElement(allOptions.get(4));
                    receiveInfoInTable = receiveInfoInRec;

                    if (srNo.equalsIgnoreCase("1") &&
                            againstInRec.equalsIgnoreCase("Direct\n" +
                                    "Pharmacy automation") &&
                            reqInfoInRec.isEmpty() &&
                            transferInfoInRec.contains(transferDate) &&
                            transferInfoInRec.contains(transferId) &&
                            transferInfoInRec.contains(EHR_Data.user_PRAkashTest) &&
                            receiveInfoInRec.contains("Received: Completed") &&
                            receiveInfoInRec.contains(transferDate) &&
                            receiveInfoInRec.contains(EHR_Data.user_PRAkashTest) ) {

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(eTransaction),
                                " Transfer Record Clicked In Receive Transaction");
                        Cls_Generic_Methods.customWait();
                        receivedRecord = true;
                        break;
                    }
                }

                m_assert.assertTrue(receivedRecord," Received Record Found,Receive Info Updated In Column");
                String recStatus =  receiveInfoInTable.split("\n")[0];
                String dateAndTime = receiveInfoInTable.split("\n")[1];

                //RECEIVED BY
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_receivedBy).contains(EHR_Data.user_PRAkashTest),
                        " Receive By User Name Displayed Correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_receivedBy).
                                contains(dateAndTime.split(" ")[0]),
                        "Receive  By  Date Displayed as "+dateAndTime.split(" ")[0]);
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_receivedBy).
                                contains(dateAndTime.split(" ")[1]),
                        "Receive  By Time Displayed as "+dateAndTime.split(" ")[1]);
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_receivedBy).
                                contains(sReceivingStore),
                        "Receive  By Store Displayed as "+sReceivingStore);

                //receive status
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Receive.text_receivedStatusInReceive),
                        " Receive Status Changed to Received Successfully");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_receivedStatusInReceive).
                                contains(recStatus.split(" ")[1]),
                        "Receive status Displayed as "+recStatus.split(" ")[1]);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Receive.text_transferClosedStatus),
                        " Transfer Status Changed to Closed Successfully");


                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Receive.text_receivedDetails),
                        " Receive Details Section Displayed After Received");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Receive.text_receiveDetailsStatus),
                        " Received Status Displayed for Receive Details Section");

                for(WebElement header: oPage_Transfer.list_transferDetailsHeaderList){
                    String headerText = Cls_Generic_Methods.getTextInElement(header);
                    int index = oPage_Transfer.list_transferDetailsHeaderList.indexOf(header);
                    if(headerText.equalsIgnoreCase(receiveTransferDetailsHeaderList[index])){
                        m_assert.assertTrue(headerText+" Header Present In Transfer Details Section");
                    }else{
                        m_assert.assertWarn("Either Column Missing or Changed");
                    }
                }

                for(WebElement header: oPage_Transfer.list_receiveDetailsHeaderList){
                    String headerText = Cls_Generic_Methods.getTextInElement(header);
                    int index = oPage_Transfer.list_receiveDetailsHeaderList.indexOf(header);
                    if(headerText.equalsIgnoreCase(receiveDetailsHeaderList[index])){
                        m_assert.assertTrue(headerText+" Header Present In Receive Details Section");
                    }else{
                        m_assert.assertWarn("Either Column Missing or Changed");
                    }
                }

                addDataTransferDetailsData();
                addDataReceiveDetailsData();

                for(WebElement header: oPage_Transfer.list_transferDetailsDataList){
                    String headerText = Cls_Generic_Methods.getTextInElement(header);
                    int index = oPage_Transfer.list_transferDetailsDataList.indexOf(header);
                    if( index == 8 || index == 17){
                        if (receiveTransferDetailsDataList.get(index).contains(headerText)) {
                            m_assert.assertTrue(headerText + " Value Present In Transfer Details Section");
                        }
                    }else {
                        if (headerText.contains(receiveTransferDetailsDataList.get(index))) {
                            m_assert.assertTrue(headerText + " Value Present In Transfer Details Section");
                        } else {
                            m_assert.assertWarn("Either Column Missing or Changed");
                        }
                    }
                }
                for(WebElement header: oPage_Transfer.list_receiveDetailsDataList){
                    String headerText = Cls_Generic_Methods.getTextInElement(header);
                    int index = oPage_Transfer.list_receiveDetailsDataList.indexOf(header);
                    if( index == 7 || index == 15){
                        if (receiveDetailsDataList.get(index).contains(headerText)) {
                            m_assert.assertTrue(headerText + " Value Present In Transfer Details Section");
                        }
                    }else {
                        if (headerText.contains(receiveDetailsDataList.get(index))) {
                            m_assert.assertTrue(headerText + " Value Present In Receive Details Section");
                        } else {
                            m_assert.assertWarn("Either Column Missing or Changed");
                        }
                    }
                }


                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.customWait();

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Sale Return in Items Lot ")
    public void ValidateInItemLotTransfer() {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Lot oPage_Lot = new Page_Lot(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);

        boolean bPurchaseTransactionFoundInLot = false;
        boolean bReturnPurchaseTransactionFoundInLot = false;
        Double stockAfterCalculated = 0.0;
        String stockBeforeReturnUI = "";
        try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sTRANSFER_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            try {

                // Validating Return Purchase In Items Lots
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
                Cls_Generic_Methods.customWait();

                int index = 0;
                for(String item : myMedicationName) {
                    Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_searchInventoryInStoreInventory);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, item);
                    Cls_Generic_Methods.customWait(5);
                    oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
                    Cls_Generic_Methods.customWait(5);

                    int indexOfItemInLot = 0;

                    for (WebElement items : oPage_ItemMaster.list_itemListInStoreInventory) {
                        List<WebElement> itemNameRow = items.findElements(By.xpath("./child::*"));
                        String itemDescriptionText = Cls_Generic_Methods.getTextInElement(itemNameRow.get((0)));
                        if (itemDescriptionText.contains(item)) {
                            Cls_Generic_Methods.clickElement(items);
                            Cls_Generic_Methods.customWait(5);
                            bPurchaseTransactionFoundInLot = true;
                            break;
                        }
                    }
                    m_assert.assertTrue(bPurchaseTransactionFoundInLot, item + " Clicked in Items Lot at index " + indexOfItemInLot);

                    if (bPurchaseTransactionFoundInLot) {

                        stockBeforeReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockBeforeInViewTemplate);
                        String stockAfterReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockAfterInViewTemplate);
                        String flowTextUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsFlowInViewTemplate);
                        String dateAndTimeUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsDateAndTimeInViewTemplate);
                        String availableStock = Cls_Generic_Methods.getTextInElement(oPage_Lot.value_AvailableStock);
                        String stock = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_stock);
                        String blockStock = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_blockedStock);


                        m_assert.assertTrue(Double.parseDouble(availableStockList.get(index))-2.0 == Double.parseDouble(availableStock),
                                "Available Stock Displayed Correctly after Receive as :"+availableStock);

                        m_assert.assertTrue(Double.parseDouble(stockList.get(index))-2.0 == Double.parseDouble(stock),
                                "Stock Displayed Correctly after Receive as :"+stock);
                        m_assert.assertTrue(Double.parseDouble(blockStockList.get(index)) == Double.parseDouble(blockStock),
                                "Block Stock Displayed Correctly after Receive as :"+blockStock);

                        String date = dateAndTimeUI.split("\\|")[0].trim();
                        String time = dateAndTimeUI.split("\\|")[1].trim();
                        String purchaseReturnDateAndTime = date + "|" + time;

                        stockAfterCalculated = convertStringToDouble(stockBeforeReturnUI) - convertStringToDouble(sUpdatedTransferQty);

                        if (flowTextUI.equalsIgnoreCase("Out (Transfer)") &&
                                Double.toString(stockAfterCalculated).equals(stockAfterReturnUI)) {
                            bReturnPurchaseTransactionFoundInLot = true;
                        }
                    }
                    m_assert.assertTrue(bReturnPurchaseTransactionFoundInLot,
                            " <b> Receive and Transfer Working Fine, as initial stock was: " + stockBeforeReturnUI +
                                    " after Receive stock is: " + stockAfterCalculated + "</b> ");
                    index++;
                }

                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.customWait();
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Sale Return in Items Lot ")
    public void ValidateInItemLotReceive() {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

        boolean bPurchaseTransactionFoundInLot = false;
        boolean bReturnPurchaseTransactionFoundInLot = false;
        Double stockAfterCalculated = 0.0;
        String stockBeforeReturnUI = "";
        try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(mapTracker.get(key_ReceivingStore));
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            try {

                // Validating Return Purchase In Items Lots
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
                Cls_Generic_Methods.customWait();

                int index = 0;
                for(String item : myMedicationName) {
                    Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_searchInventoryInStoreInventory);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, item);
                    Cls_Generic_Methods.customWait(5);
                    oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
                    Cls_Generic_Methods.customWait(5);

                    int indexOfItemInLot = 0;

                    for (WebElement items : oPage_ItemMaster.list_itemListInStoreInventory) {
                        List<WebElement> itemNameRow = items.findElements(By.xpath("./child::*"));
                        String itemDescriptionText = Cls_Generic_Methods.getTextInElement(itemNameRow.get((0)));
                        if (itemDescriptionText.contains(item)) {
                            Cls_Generic_Methods.clickElement(items);
                            Cls_Generic_Methods.customWait(5);
                            bPurchaseTransactionFoundInLot = true;
                            break;
                        }
                    }
                    m_assert.assertTrue(bPurchaseTransactionFoundInLot, item + " Clicked in Items Lot at index " + indexOfItemInLot);

                    if (bPurchaseTransactionFoundInLot) {

                        stockBeforeReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockBeforeInViewTemplate);
                        String stockAfterReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockAfterInViewTemplate);
                        String flowTextUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsFlowInViewTemplate);
                        String dateAndTimeUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsDateAndTimeInViewTemplate);

                        String date = dateAndTimeUI.split("\\|")[0].trim();
                        String time = dateAndTimeUI.split("\\|")[1].trim();
                        String purchaseReturnDateAndTime = date + "|" + time;

                        stockAfterCalculated = convertStringToDouble(stockBeforeReturnUI) + convertStringToDouble(sUpdatedTransferQty);

                        if (flowTextUI.equalsIgnoreCase("In (Receive)") &&
                                Double.toString(stockAfterCalculated).equals(stockAfterReturnUI) ) {
                            bReturnPurchaseTransactionFoundInLot = true;
                        }
                    }
                    m_assert.assertTrue(bReturnPurchaseTransactionFoundInLot,
                            " <b> Receive and Transfer Working Fine, as initial stock was: " + stockBeforeReturnUI +
                                    " after Receive stock is: " + stockAfterCalculated + "</b> ");
                    index++;
                }

                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.customWait();
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }


    @Test(enabled = true, description = "Create Transfer Transaction")
    public void cancelTransferTransaction() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        boolean itemFound = false;
        boolean TransactionFound = false;
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sTRANSFER_STORE);
            Cls_Generic_Methods.customWait(2);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();
            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItem, 10);

                m_assert.assertTrue(searchAndSelectItem(oPage_Transfer.input_searchItem,sItemName,oPage_Transfer.list_itemsUnderLot),
                        "Selected a item to store lot details: <b> " + sItemName + "</b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_availableStock, 5);
                String sAvailableStock = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_availableStock);
                m_assert.assertInfo("Available stock before transfer = <b>" + sAvailableStock + "</b>");
                availableStockList.add(sAvailableStock);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_itemVarientCode, 5);
                String sVariantCode = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_itemVarientCode);
                itemVariantCodeList.add(sVariantCode);
                mapTracker.put(key_sVariantCode, sVariantCode);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_costPriceOfItem, 5);
                String sCostPriceOfItem = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_costPriceOfItem);
                mapTracker.put(key_sCostPrice, sCostPriceOfItem);
                costPriceList.add(sCostPriceOfItem);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_stock, 5);
                String sStockBeforeTransfer = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_stock);
                m_assert.assertInfo("Stock before transfer = <b>" + sStockBeforeTransfer + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_blockedStock, 5);
                String sBlockedStock = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_blockedStock);
                m_assert.assertInfo("Blocked Stock before transfer = <b>" + sBlockedStock + "</b>");

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_newTransaction, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_newTransaction),
                        "Clicked on New button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.header_addTransferHeader, 5);
                m_assert.assertTrue(selectByOptions(oPage_Transfer.dropdown_receivingStore, sReceivingStore,oPage_Transfer.list_receivingStore),
                        "Receiving store selected as : "+ sReceivingStore);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_searchByDescription, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_searchByDescription),
                        "Clicked on Description button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItemToBeTransferred, 5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_searchItemToBeTransferred, sItemName),
                        "<b> " + sItemName + "</b> Entered in search item field");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItemToBeTransferred, 5);
                oPage_Transfer.input_searchItemToBeTransferred.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(3);
                for (WebElement eItemVariantCode : oPage_Transfer.list_itemsCanBeTransferred) {
                    if (Cls_Generic_Methods.getTextInElement(eItemVariantCode).equalsIgnoreCase(sVariantCode)) {
                        Cls_Generic_Methods.clickElement(eItemVariantCode);
                        itemFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(itemFound, "Found a item having variant code = <b> " + sVariantCode + "</b>");
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.list_quantityFieldForItemsToTransfer.get(0), sUpdatedTransferQty),
                        "Entered in Quantity field = <b> " + sUpdatedTransferQty + "</b>");

                Cls_Generic_Methods.clickElement(oPage_Transfer.button_saveChanges);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait(2);

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
                Cls_Generic_Methods.customWait(3);

                for (WebElement eValue : oPage_Transfer.list_tableDataTransfer) {
                    List<WebElement> purchaseRow = eValue.findElements(By.xpath("./td"));
                    String againstTxt = Cls_Generic_Methods.getTextInElement(purchaseRow.get(1));
                    String transferIssueInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(2));
                    String reqInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(3));
                    String recInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(4));
                    if(againstTxt.replace("\n"," ").equalsIgnoreCase("Direct "+ sReceivingStore) &&
                            transferIssueInfo.contains(transferDate) &&
                            reqInfo.isEmpty() &&
                            recInfo.isEmpty()
                    ){
                        Cls_Generic_Methods.clickElement(eValue);
                        TransactionFound = true;
                        Cls_Generic_Methods.customWait(5);
                        break ;

                    }

                }
                m_assert.assertTrue(TransactionFound, "Transfer transaction found");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_cancel),
                        "Cancel Button Clicked");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.label_cancelReason),
                        "Cancel Reason Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_cancelReason,"Checking for Cancel"),
                        "Cancel Reason Enter As: Checking for Cancel ");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.input_cancelButtonInCancel),
                        "Cancel Button Clicked in Cancel Templated");
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_transactionStatus).equalsIgnoreCase("cancelled"),
                        "Cancel Transfer Working and Status Displayed Correctly as : Cancelled ");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_transferCancelledBy).contains(EHR_Data.user_PRAkashTest),
                        " Transfer Cancelled By User Name Displayed Correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_transferCreatedBy).
                                contains(transferDate),
                        "Transfer Cancelled By Date Displayed as "+transferDate);

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItem, 10);

                m_assert.assertTrue(searchAndSelectItem(oPage_Transfer.input_searchItem,sItemName,oPage_Transfer.list_itemsUnderLot),
                        "Selected a item to store lot details: <b> " + sItemName + "</b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.text_availableStock, 5);
                String sAvailableStockAfterCancel = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_availableStock);
                m_assert.assertTrue(sAvailableStock.equalsIgnoreCase(sAvailableStockAfterCancel),
                        " Cancel Transfer Working fine in lot");


                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.customWait();

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Desc")
    public void validateSearchFunctionalityInTransfer(){

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        String transferSearchTypeList [] = {"Transfer","Item Description","Issue No","Requisition No","Receive No"};


        try{
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sTRANSFER_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type  Selection Dropdown Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.input_searchBoxInput),
                        " Input Search Box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(transferSearchTypeList[0]),
                        " By Default Search Type Selection Dropdown Displayed correctly as :" + transferSearchTypeList[0]);
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput, "value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty by default for selected search type Displayed correctly");
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Not Displayed Correctly as Default");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput, "placeholder")
                                .contains("Search By " + transferSearchTypeList[0]),
                        " Input Search Box Place holder for selected search type Displayed correctly");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_filterDropdownButton),
                        " Filter Dropdown Button Clicked");
                Cls_Generic_Methods.customWait(2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.text_thisYearFilter),
                        " This Year Selected as Filter");
                Cls_Generic_Methods.customWait(2);

                for (WebElement eTransaction : oPage_Transfer.list_tableDataTransfer) {

                    int index = oPage_Transfer.list_tableDataTransfer.indexOf(eTransaction);
                    Cls_Generic_Methods.clickElement(eTransaction);
                    Cls_Generic_Methods.customWait(4);
                    if(index>1) {
                        if (Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_receiveStatus).contains("Completed")) {
                            Cls_Generic_Methods.clickElement(oPage_Transfer.text_receiveStatus);
                            Cls_Generic_Methods.customWait();
                            oldReceiveNo = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_receiveIdInTransfer);
                            break;
                        }
                    }

                }

                String receiveValue[] = {receiveTransactionId, oldReceiveNo};
                InventorySearchTest oInventorySearchTest = new InventorySearchTest();
                for (int i = 0; i < receiveValue.length; i++) {
                    boolean searchResultByReceiveNumber = CommonActions.selectOptionAndSearch(transferSearchTypeList[4], receiveValue[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByReceiveNumber) {
                        boolean recordFound = oInventorySearchTest.getSearchedRecordAndValidateInView(
                                oPage_Transfer.list_tableDataTransfer, oPage_Transfer.text_receiveIdInTransfer, receiveValue[i]);
                        m_assert.assertTrue(recordFound,
                                "Search By old/new "+transferSearchTypeList[4]+" Worked correctly as Purchase Order found " +
                                        "in the Order page for Number: " + receiveValue[i]);

                    }
                }

                for (int i = 0; i < receiveValue.length; i++) {
                    boolean searchResultByWrongTransactionId = CommonActions.selectOptionAndSearch(transferSearchTypeList[4],receiveTransactionId+"23");
                    m_assert.assertFalse(searchResultByWrongTransactionId," Transaction Search With Incorrect Transaction ID Working Correct");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                            " Nothing To Display Text Displayed as Result");
                }

                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab() ;
                Cls_Generic_Methods.customWait();

            }catch (Exception e){
                e.printStackTrace();
                m_assert.assertFatal(e.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
            m_assert.assertFalse(e.toString());
        }
    }
    @Test(enabled = true, description = "Desc")
    public void validateSearchFunctionalityInReceive(){

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        String transferSearchTypeList [] = {"Receive No","Item Description","Issue No","Requisition No","Transfer No"};


        try{
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sReceivingStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type  Selection Dropdown Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.input_searchBoxInput),
                        " Input Search Box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(transferSearchTypeList[0]+"."),
                        " By Default Search Type Selection Dropdown Displayed correctly as :" + transferSearchTypeList[0]);
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput, "value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty by default for selected search type Displayed correctly");
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Not Displayed Correctly as Default");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput, "placeholder")
                                .contains("Search By " + transferSearchTypeList[0]),
                        " Input Search Box Place holder for selected search type Displayed correctly");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_filterDropdownButton),
                        " Filter Dropdown Button Clicked");
                Cls_Generic_Methods.customWait(2);
                String receiveValue[] = {receiveTransactionId, oldReceiveNo};
                InventorySearchTest oInventorySearchTest = new InventorySearchTest();
                for (int i = 0; i < receiveValue.length; i++) {
                    boolean searchResultByReceiveNumber = CommonActions.selectOptionAndSearch(transferSearchTypeList[0], receiveValue[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByReceiveNumber) {
                        boolean recordFound = oInventorySearchTest.getSearchedRecordAndValidateInView(
                                oPage_Transfer.list_tableDataTransfer, oPage_Transfer.text_receiveIdInTransfer, receiveValue[i]);
                        m_assert.assertTrue(recordFound,
                                "Search By old/new "+transferSearchTypeList[0]+" Worked correctly as Purchase Order found " +
                                        "in the Order page for Number: " + receiveValue[i]);

                    }
                }

                for (int i = 0; i < receiveValue.length; i++) {
                    boolean searchResultByWrongTransactionId = CommonActions.selectOptionAndSearch(transferSearchTypeList[0],receiveTransactionId+"23");
                    m_assert.assertFalse(searchResultByWrongTransactionId," Transaction Search With Incorrect Transaction ID Working Correct");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                            " Nothing To Display Text Displayed as Result");
                }

                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab() ;
                Cls_Generic_Methods.customWait();

            }catch (Exception e){
                e.printStackTrace();
                m_assert.assertFatal(e.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
            m_assert.assertFalse(e.toString());
        }
    }


    public void getTransferIdFromSetting(){

        Page_Transfer oPage_Transfer = new Page_Transfer(driver);

        try{
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Sequence Manager");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.transferIdText,10);
            transferId = Cls_Generic_Methods.getTextInElement(oPage_Transfer.transferIdText).trim();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.receiveTransactionIdText,10);
            receiveTransactionId = Cls_Generic_Methods.getTextInElement(oPage_Transfer.receiveTransactionIdText).trim();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.deliveryChallanIdText,10);
            deliveryChallanId = Cls_Generic_Methods.getTextInElement(oPage_Transfer.deliveryChallanIdText).trim();



        }catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }
    public boolean validatePrintButtonFunctionality(WebElement printButton , String printButtonName){

        Page_Sale oPage_Sale = new Page_Sale(driver);
        boolean bPrintWork = false ;

        try {
            int preWindowsCount = driver.getWindowHandles().size();
            String initialWindowHandle = driver.getWindowHandle();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(printButton),
                    printButtonName+" Button Displayed and Clicked ");
            Cls_Generic_Methods.customWait(2);
            if(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.list_printDropdownFirstOptionInAdvanceReceipt)){
                Cls_Generic_Methods.clickElement(oPage_Sale.list_printDropdownFirstOptionInAdvanceReceipt);
                Cls_Generic_Methods.customWait();
            }else {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Sale.list_printDropdownFirstOption)) {
                    Cls_Generic_Methods.clickElement(oPage_Sale.list_printDropdownFirstOption);
                    Cls_Generic_Methods.customWait();
                }
            }
            Cls_Generic_Methods.customWait(6);
            int postWindowsCount = driver.getWindowHandles().size();

            bPrintWork = postWindowsCount > preWindowsCount ;
            m_assert.assertTrue(bPrintWork, "Validated Print --> Sale Transaction Print page opened");

            for (String currentWindowHandle : driver.getWindowHandles()) {
                if (!currentWindowHandle.equals(driver.getWindowHandle())) {
                    driver.switchTo().window(currentWindowHandle);
                }
            }
            driver.close();
            driver.switchTo().window(initialWindowHandle);
            Cls_Generic_Methods.customWait(4);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return bPrintWork ;
    }
    private boolean searchAndSelectItem(WebElement selectElement, String optionToSelect,List<WebElement> itemList) {
        boolean status = false;
        try {
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(selectElement, optionToSelect),
                    "<b> " + optionToSelect + "</b> Entered in search item field");
            Cls_Generic_Methods.customWait();
            selectElement.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);
            for (WebElement eItemName : itemList) {
                if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(optionToSelect)) {
                    Cls_Generic_Methods.clickElement(eItemName);
                    status = true;
                    break;
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to select " + optionToSelect + " -->" + e);
        }
        return status;
    }
    private boolean selectByOptions(WebElement selectElement, String optionToSelect,List<WebElement> itemList) {
        boolean status = false;
        try {
            Cls_Generic_Methods.clickElement(selectElement);
            Cls_Generic_Methods.customWait(3);
            for (WebElement eReceivingStore : itemList) {
                if (Cls_Generic_Methods.getTextInElement(eReceivingStore).equalsIgnoreCase(optionToSelect)) {
                    Cls_Generic_Methods.clickElement(eReceivingStore);
                    status = true;
                    Cls_Generic_Methods.customWait();
                    break;
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to select " + optionToSelect + " -->" + e);
        }
        return status;
    }
    public void addDataInTransfer(){
        addTransferDataList.add("1");
        addTransferDataList.add(sItemName);
        addTransferDataList.add("RSO201023INVMED12921");
        addTransferDataList.add("RSO-GRN");
        addTransferDataList.add("05/09/2023");
        addTransferDataList.add("BT12345");
        addTransferDataList.add("2033-09-30");
        addTransferDataList.add(listPriceList.get(0));
        addTransferDataList.add("100.0");
        addTransferDataList.add("");
        addTransferDataList.add(String.valueOf(Double.parseDouble(listPriceList.get(0).replace("₹",""))*2));

    }
    public void addDataForTransactionDetails(){
        addTranssactionDetailsDataList.add("1");
        addTranssactionDetailsDataList.add(sItemName);
        addTranssactionDetailsDataList.add("BT12345");
        addTranssactionDetailsDataList.add("2033-09-30");
        addTranssactionDetailsDataList.add(sUpdatedTransferQty);
        addTranssactionDetailsDataList.add(listPriceList.get(0));
        addTranssactionDetailsDataList.add(amountPerItemList.get(0));

    }
    private boolean selectByOptions(WebElement selectElement, String optionToSelect) {
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

    public void addDataReceiveTransactionData(){
        receiveTransactionDataList.add("1");
        receiveTransactionDataList.add(editItemName);
        receiveTransactionDataList.add("");
        receiveTransactionDataList.add("300");
        receiveTransactionDataList.add("");
        receiveTransactionDataList.add(sUpdatedTransferQty);
        receiveTransactionDataList.add(sUpdatedTransferQty);
        receiveTransactionDataList.add("0");
        receiveTransactionDataList.add("");
        receiveTransactionDataList.add("2");
        receiveTransactionDataList.add(sItemName);
        receiveTransactionDataList.add("BT12345");
        receiveTransactionDataList.add("130");
        receiveTransactionDataList.add("2033-09-30");
        receiveTransactionDataList.add(sUpdatedTransferQty);
        receiveTransactionDataList.add(sUpdatedTransferQty);
        receiveTransactionDataList.add("0");
        receiveTransactionDataList.add("");

    }
    public void addDataTransferDetailsData(){
        receiveTransferDetailsDataList.add("1");
        receiveTransferDetailsDataList.add(editItemName);
        receiveTransferDetailsDataList.add(itemVariantCodeList.get(1));
        receiveTransferDetailsDataList.add("");
        receiveTransferDetailsDataList.add("");
        receiveTransferDetailsDataList.add(sUpdatedTransferQty);
        receiveTransferDetailsDataList.add("0");
        receiveTransferDetailsDataList.add("");
        receiveTransferDetailsDataList.add(amountPerItemList.get(0));

        receiveTransferDetailsDataList.add("2");
        receiveTransferDetailsDataList.add(sItemName);
        receiveTransferDetailsDataList.add(itemVariantCodeList.get(0));
        receiveTransferDetailsDataList.add("BT12345");
        receiveTransferDetailsDataList.add("2033-09-30");
        receiveTransferDetailsDataList.add(sUpdatedTransferQty);
        receiveTransferDetailsDataList.add("0");
        receiveTransferDetailsDataList.add("");
        receiveTransferDetailsDataList.add(amountPerItemList.get(1));

    }
    public void addDataReceiveDetailsData(){

        receiveDetailsDataList.add("1");
        receiveDetailsDataList.add(editItemName);
        receiveDetailsDataList.add(itemVariantCodeList.get(1));
        receiveDetailsDataList.add("");
        receiveDetailsDataList.add("");
        receiveDetailsDataList.add(sUpdatedTransferQty);
        receiveDetailsDataList.add("300");
        receiveDetailsDataList.add(amountPerItemList.get(0));
        receiveDetailsDataList.add("2");
        receiveDetailsDataList.add(sItemName);
        receiveDetailsDataList.add(itemVariantCodeList.get(0));
        receiveDetailsDataList.add("BT12345");
        receiveDetailsDataList.add("2033-09-30");
        receiveDetailsDataList.add(sUpdatedTransferQty);
        receiveDetailsDataList.add("130");
        receiveDetailsDataList.add(amountPerItemList.get(1));


    }


}
