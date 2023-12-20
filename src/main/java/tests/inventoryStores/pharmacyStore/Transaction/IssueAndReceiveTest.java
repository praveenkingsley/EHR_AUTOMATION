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
import pages.store.PharmacyStore.Items.Page_Lot;
import pages.store.PharmacyStore.Order.Page_Requisition;
import pages.store.PharmacyStore.Order.Page_RequisitionReceived;
import pages.store.PharmacyStore.Page_TaxInvoiceDeliveryChallan;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_Receive;
import pages.store.PharmacyStore.Transaction.Page_Sale;
import pages.store.PharmacyStore.Transaction.Page_Transfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssueAndReceiveTest extends TestBase {

    public static String sRequisition_STORE = "Pharmacy automation- Pharmacy";
    public static String sCENTRAL_HUB = "CENTRAL HUB 01- Central Hub";
    String sItemName = "Transfer Item 2";
    String myMedicationName[] = {"SalesOrderTest1",sItemName};
    String requisitionOrderTime = "";
    String requisitionOrderDate = "";
    String sRequisitionType = "Normal";
    public static String issueQty = "3";
    String issueId = "";
    public static Map<String, String> mapTracker = new HashMap<String, String>();
    public static String key_CreatedAt_RequisitionOrderFromStore = "key_CreatedAt_RequisitionOrderFromStore";
    public static String key_RequisitionNumber_Created_InRequisition = "key_RequisitionNumber_Created_InRequisition";
    public static String key_NewTransactionInRequisitionReceived_1 = "key_NewTransactionInRequisitionReceived_1";
    String transactionDetailsHeader[]={"#","Description","Batch No.","Expiry","Transfered Quantity","MRP","Total Price"};
    List<String> addTranssactionDetailsDataList = new ArrayList<>();
    List<String> amountPerItemList = new ArrayList<>();
    String receiveTransactionId = "";
    String reqNum = "",deliveryChallanId;






    @Test(enabled = true, description = "Create Transfer Transaction")
    public void createIssueTransaction() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);

        boolean bTransferEntryFound = false ;


        try {
            createRequisitionInStore();
            try {
                validateRequisitionReceivedAndTransferFromHub();
                try {
                    Cls_Generic_Methods.customWait();
                    CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
                    getTransferIdFromSetting();
                    CommonActions.selectStoreOnApp(sCENTRAL_HUB);
                    Cls_Generic_Methods.switchToOtherTab();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                            "Central hub popup closed");
                    Cls_Generic_Methods.customWait();
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
                    Cls_Generic_Methods.customWait( 6);

                    for (WebElement eValue : oPage_Transfer.list_tableDataTransfer) {
                        List<WebElement> purchaseRow = eValue.findElements(By.xpath("./td"));
                        String againstTxt = Cls_Generic_Methods.getTextInElement(purchaseRow.get(1));
                        String transferIssueInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(2));
                        String reqInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(3));
                        String recInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(4));
                        if(againstTxt.replace("\n"," ").equalsIgnoreCase("Requisition "+ sRequisition_STORE.split("-")[0]) &&
                                transferIssueInfo.contains(issueId) &&
                                transferIssueInfo.contains(mapTracker.get(key_NewTransactionInRequisitionReceived_1).split(" ")[0]) &&
                                transferIssueInfo.contains(EHR_Data.user_PRAkashTest) &&
                                reqInfo.contains(EHR_Data.user_PRAkashTest) &&
                                reqInfo.contains(mapTracker.get(key_CreatedAt_RequisitionOrderFromStore).split(" ")[0]) &&
                                reqInfo.contains(sRequisition_STORE.split("-")[0]) &&
                                recInfo.isEmpty()
                        ){
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(eValue),
                                    " Issue Record CLicked In Store");
                            bTransferEntryFound = true;
                            Cls_Generic_Methods.customWait(5);
                            break ;

                        }

                    }
                    m_assert.assertTrue(bTransferEntryFound, "Issue Type Transaction found");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.header_requisitionViewDetailsDropdown),
                            " Requisition Dropdown Clicked");
                    Cls_Generic_Methods.customWait();
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionNumber).contains(mapTracker.get(key_RequisitionNumber_Created_InRequisition)),
                            " Requisition Number Displayed Correctly ");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionDateAndTime).contains("MANUAL"),
                            " Requisition Number Manual Text Displayed Correctly");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionDateAndTime).contains(mapTracker.get(key_CreatedAt_RequisitionOrderFromStore).split(" ")[0]),
                            " Requisition  Date and time Displayed Correctly");
                    String eReqCreatedBy = EHR_Data.user_PRAkashTest+" | "+mapTracker.get(key_CreatedAt_RequisitionOrderFromStore).split(" ")[0]+" | ";
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionCreatedBy).contains(eReqCreatedBy),
                            " Requisition Created Displayed Correctly");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionCreatedBy).contains(sRequisition_STORE.split("-")[0]),
                            " Requisition Created Store Displayed Correctly");
                    String eReqApprovedBy = EHR_Data.user_PRAkashTest+" | "+mapTracker.get(key_CreatedAt_RequisitionOrderFromStore).split(" ")[0]+" | ";
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionApprovedBy).contains(eReqApprovedBy),
                            " Requisition Approved Displayed Correctly");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionTo).contains(sCENTRAL_HUB.split("-")[0]),
                            " Requisition To Store Displayed Correctly");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionStatus).contains("approved"),
                            " Requisition Status Displayed Correctly");
                    String issueIdTextInView = issueId +" | "+mapTracker.get(key_NewTransactionInRequisitionReceived_1).split(" ")[0]+" | ";
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_issueIdInTransfer).contains(issueIdTextInView),
                            " Issue Id and Date and time Displayed Correctly");
                    String issueCreatedByUi = EHR_Data.user_PRAkashTest+" | "+mapTracker.get(key_NewTransactionInRequisitionReceived_1).split(" ")[0]+" | ";
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_issueCreatedBy).contains(issueCreatedByUi),
                            " Issue Created By Displayed Correctly");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_issueCreatedBy).contains(sCENTRAL_HUB.split("-")[0]),
                            " Issue Created Store  Displayed Correctly");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_issueStatus).equalsIgnoreCase("open"),
                            " Issue Status Displayed as open");
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
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Edit Transfer Transaction")
    public void editIssueTransaction() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);

        boolean bTransferEntryFound = false ;

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            getTransferIdFromSetting();
            try {
                CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
                getTransferIdFromSetting();
                CommonActions.selectStoreOnApp(sCENTRAL_HUB);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Central hub popup closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
                Cls_Generic_Methods.customWait( 6);

                for (WebElement eValue : oPage_Transfer.list_tableDataTransfer) {
                    List<WebElement> purchaseRow = eValue.findElements(By.xpath("./td"));
                    String againstTxt = Cls_Generic_Methods.getTextInElement(purchaseRow.get(1));
                    String transferIssueInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(2));
                    String reqInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(3));
                    String recInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(4));
                    if(againstTxt.replace("\n"," ").equalsIgnoreCase("Requisition "+ sRequisition_STORE.split("-")[0]) &&
                            transferIssueInfo.contains(issueId) &&
                            transferIssueInfo.contains(mapTracker.get(key_NewTransactionInRequisitionReceived_1).split(" ")[0]) &&
                            transferIssueInfo.contains(EHR_Data.user_PRAkashTest) &&
                            reqInfo.contains(EHR_Data.user_PRAkashTest) &&
                            reqInfo.contains(mapTracker.get(key_CreatedAt_RequisitionOrderFromStore).split(" ")[0]) &&
                            reqInfo.contains(sRequisition_STORE.split("-")[0]) &&
                            recInfo.isEmpty()
                    ){
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(eValue),
                                " Issue Record CLicked In Store");
                        bTransferEntryFound = true;
                        Cls_Generic_Methods.customWait(5);
                        break ;

                    }

                }
                m_assert.assertTrue(bTransferEntryFound, "Issue Type Transaction found");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_edit),
                        " Edit Button Clicked");
                Cls_Generic_Methods.customWait(4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.list_transferItemList.get(1)),
                        "Transfer item clicked: <b> " + oPage_RequisitionReceived.list_transferItemList.get(1).getText() + "</b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.input_issueQuantity, 10);
                Cls_Generic_Methods.clearValuesInElement(oPage_RequisitionReceived.input_issueQuantity);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RequisitionReceived.input_issueQuantity, issueQty),
                        "Transfer quantity: <b> " + issueQty + "</b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_confirmTransfer),
                        "Item Transfer confirmed from requisition");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.list_transferItem, 10);

                for(WebElement item : oPage_RequisitionReceived.list_itemDescriptionInEditIssue){
                    String itemDescriptionUI = Cls_Generic_Methods.getTextInElement(item);
                    int index = oPage_RequisitionReceived.list_itemDescriptionInEditIssue.indexOf(item);
                    m_assert.assertTrue(itemDescriptionUI.equalsIgnoreCase(myMedicationName[index]),
                            " Item Name Displayed In Edit Issue Correctly as : "+itemDescriptionUI);
                }
                for(WebElement item : oPage_RequisitionReceived.list_itemQtyInEditIssue){
                    String itemDescriptionUI = Cls_Generic_Methods.getElementAttribute(item,"value");
                    m_assert.assertTrue(itemDescriptionUI.contains(issueQty),
                            " Item Quantity Displayed In Edit Issue Correctly as : "+issueQty);
                }
                for(WebElement editButton : oPage_RequisitionReceived.list_itemEditInEditIssue){
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(editButton),
                            " Edit Button CLicked In Edit Issue Template");
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.clearValuesInElement(oPage_RequisitionReceived.input_issueQuantity);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RequisitionReceived.input_issueQuantity,
                            String.valueOf(Double.parseDouble(issueQty)-1)), "Transfer quantity: <b> " + issueQty + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_confirmTransfer),
                            "Item Transfer confirmed from requisition");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.list_transferItem, 10);

                }

                for(WebElement item : oPage_RequisitionReceived.list_itemQtyInEditIssue){
                    if(Cls_Generic_Methods.isElementDisplayed(item)) {
                        String itemDescriptionUI = Cls_Generic_Methods.getElementAttribute(item, "value");
                        m_assert.assertTrue(itemDescriptionUI.contains(String.valueOf(Double.parseDouble(issueQty) - 1)),
                                " Item Quantity Displayed In Edit Issue Correctly After EditIcon as : " + String.valueOf(Double.parseDouble(issueQty) - 1));
                    }
                }
                for(WebElement item : oPage_RequisitionReceived.list_itemDeleteInEditIssue){
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(item),
                            " Item Delete Button Displayed  and Clicked In Edit Issue Correctly After EditIcon ");
                }

                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_RequisitionReceived.list_itemDeleteInEditIssue.get(0)),
                        " Delete Button Working fine");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_Transfer.button_edit);
                Cls_Generic_Methods.waitForElementsToBeDisplayed( oPage_RequisitionReceived.list_itemDeleteInEditIssue,10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.list_transferItemList.get(1)),
                        "Transfer item clicked: <b> " + oPage_RequisitionReceived.list_transferItemList.get(1).getText() + "</b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.input_issueQuantity, 10);
                Cls_Generic_Methods.clearValuesInElement(oPage_RequisitionReceived.input_issueQuantity);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RequisitionReceived.input_issueQuantity, issueQty),
                        "Transfer quantity: <b> " + issueQty + "</b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_confirmTransfer),
                        "Item Transfer confirmed from requisition");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.list_transferItem, 10);

                Double totalAmountCalculated = 0.0;
                for(WebElement item : oPage_RequisitionReceived.list_itemAmountInEditIssue){
                    String itemDescriptionUI = Cls_Generic_Methods.getElementAttribute(item,"value");
                    amountPerItemList.add(itemDescriptionUI);
                    totalAmountCalculated = totalAmountCalculated + Double.parseDouble(itemDescriptionUI);
                }

                String totalAmountIncTax = Cls_Generic_Methods.getElementAttribute(oPage_Transfer.input_totalAmountIncludingTax,"value");
                m_assert.assertTrue(totalAmountIncTax.contains(String.valueOf(totalAmountCalculated)),
                        " Total Amount Inc Tax Displayed Correctly and Edit Functionality Working fine");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_saveTransfer),
                        "Transfer saved");
                Cls_Generic_Methods.customWait(10);

                int k = 0;
                boolean editFound = false;
                for(WebElement data : oPage_Transfer.list_transactionDetailsItemDataList){
                    String itemText = Cls_Generic_Methods.getTextInElement(data);
                    String totalPrice = Cls_Generic_Methods.getTextInElement(oPage_Transfer.list_transactionDetailsTotalPriceDataList.get(k));
                    if(itemText.contains(myMedicationName[k]) && amountPerItemList.get(k).contains(totalPrice)){
                        editFound = true;
                        m_assert.assertTrue(itemText+ " Item present in View Issue");
                    }
                    k++;
                }

                m_assert.assertTrue(editFound, "Edit Issue transaction Data found");

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
    public void approveIssueTransaction() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        Page_TaxInvoiceDeliveryChallan oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);


        boolean bTransferEntryFound = false ;

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            getTransferIdFromSetting();
            try {
                CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
                getTransferIdFromSetting();
                CommonActions.selectStoreOnApp(sCENTRAL_HUB);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Central hub popup closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
                Cls_Generic_Methods.customWait( 6);

                for (WebElement eValue : oPage_Transfer.list_tableDataTransfer) {
                    List<WebElement> purchaseRow = eValue.findElements(By.xpath("./td"));
                    String againstTxt = Cls_Generic_Methods.getTextInElement(purchaseRow.get(1));
                    String transferIssueInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(2));
                    String reqInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(3));
                    String recInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(4));
                    if(againstTxt.replace("\n"," ").equalsIgnoreCase("Requisition "+ sRequisition_STORE.split("-")[0]) &&
                            transferIssueInfo.contains(issueId) &&
                            transferIssueInfo.contains(mapTracker.get(key_NewTransactionInRequisitionReceived_1).split(" ")[0]) &&
                            transferIssueInfo.contains(EHR_Data.user_PRAkashTest) &&
                            reqInfo.contains(EHR_Data.user_PRAkashTest) &&
                            reqInfo.contains(mapTracker.get(key_CreatedAt_RequisitionOrderFromStore).split(" ")[0]) &&
                            reqInfo.contains(sRequisition_STORE.split("-")[0]) &&
                            recInfo.isEmpty()
                    ){
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(eValue),
                                " Issue Record CLicked In Store");
                        bTransferEntryFound = true;
                        Cls_Generic_Methods.customWait(5);
                        break ;

                    }

                }
                m_assert.assertTrue(bTransferEntryFound, "Issue Type Transaction found");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_approveTransfer),
                        " Approve Button Clicked");
                Cls_Generic_Methods.customWait(4);

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_issueStatus).equalsIgnoreCase("approved"),
                        " Issue Status Displayed Correctly as : approved ");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_issueApprovedBy).contains(EHR_Data.user_PRAkashTest),
                        " Issue Approved By User Name Displayed Correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_issueApprovedBy).
                                contains(requisitionOrderDate),
                        "Issue Approved By Date Displayed as "+requisitionOrderDate);
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
                        selectByOptions(oPage_TaxInvoiceDeliveryChallan.select_againstStore, sRequisition_STORE.split("-")[0]);
                        Cls_Generic_Methods.customWait(2);
                        for (WebElement lhsRow : oPage_TaxInvoiceDeliveryChallan.row_lhsCreateTaxInvoiceDeliveryChallan) {
                            String transferDate = Cls_Generic_Methods.getTextInElement(lhsRow.findElement(By.xpath("./td[3]")));

                            if (transferDate.contains(issueId)) {
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
        boolean TransactionFound = false;
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            getTransferIdFromSetting();
            CommonActions.selectStoreOnApp(sRequisition_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait(3);
            try {
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

                    if (srNo.equalsIgnoreCase("1") &&
                            againstInRec.equalsIgnoreCase("Requisition\n" +
                                    sCENTRAL_HUB.split("-")[0]) &&
                            reqInfoInRec.contains(EHR_Data.user_PRAkashTest) &&
                            reqInfoInRec.contains(requisitionOrderDate) &&
                            reqInfoInRec.contains(sRequisition_STORE.split("-")[0]) &&
                            transferInfoInRec.contains(requisitionOrderDate) &&
                            transferInfoInRec.contains(issueId) &&
                            transferInfoInRec.contains(EHR_Data.user_PRAkashTest) &&
                            receiveInfoInRec.equalsIgnoreCase("Pending: None")) {

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(eTransaction),
                                " Issue Record Clicked In Receive Transaction");
                        Cls_Generic_Methods.customWait(5);
                        TransactionFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(TransactionFound, "Transfer Transaction under Receive Transaction Module");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.button_receiveStock, 5);

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.header_requisitionViewDetailsDropdown),
                        " Requisition Collapse/Expand Dropdown Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_Transfer.header_requisitionViewDetailsDropdown,"data-toggle").equalsIgnoreCase("collapse"),
                        " Requisition Collapse/Expand Dropdown Displayed and By Default its Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.header_requisitionViewDetailsDropdown).contains(reqNum),
                        " Requisition Number Displayed Correctly as "+reqNum);
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.header_requisitionViewDetailsDropdown).contains(requisitionOrderDate),
                        " Requisition  Date  Displayed Correctly as ");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.header_requisitionViewDetailsDropdown).contains("MANUAL"),
                        " Requisition Number Manual Text Displayed Correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.header_requisitionViewDetailsDropdown).contains(requisitionOrderTime.substring(0,2)),
                        " Requisition time Displayed Correctly as ");

                m_assert.assertFalse(Cls_Generic_Methods.isElementDisplayed(oPage_Transfer.text_requisitionCreatedBy),
                        " Requisition Created By Is not displayed correctly as its hidden");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.header_requisitionViewDetailsDropdown),
                        " Requisition Collapse/Expand Dropdown Clicked for Expand");
                Cls_Generic_Methods.customWait();

                String eReqCreatedBy = EHR_Data.user_PRAkashTest+" | "+requisitionOrderDate+" | ";
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionCreatedBy).contains(eReqCreatedBy),
                        " Requisition Created Displayed Correctly ");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionCreatedBy).contains(sRequisition_STORE.split("-")[0]),
                        " Requisition Created Store Displayed Correctly as : "+Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionCreatedBy));

                String eReqApprovedBy = EHR_Data.user_PRAkashTest+" | "+requisitionOrderDate+" | ";
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionApprovedBy).contains(eReqApprovedBy),
                        " Requisition Approved Displayed Correctly as "+ Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionApprovedBy));

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionStore).contains(sRequisition_STORE.split("-")[0]),
                        " Requisition Store Displayed Correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_requisitionStatus).contains("approved"),
                        " Requisition Status Displayed Correctly");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.header_requisitionViewDetailsDropdown),
                        " Requisition Collapse/Expand Dropdown Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_Receive.text_issueTransactionId,"data-toggle").equalsIgnoreCase("collapse"),
                        " Issue Collapse/Expand Dropdown Displayed and By Default its Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Receive.text_issueTransactionId),
                        " Issue Collapse/Expand Dropdown clicked");

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_issueTransactionId).contains(issueId),
                        " Issue Id Text Displayed correctly as Issue Transaction ID:   "+issueId);
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_issueTransactionId).contains(requisitionOrderDate),
                        " Issue Date And Time Text Displayed correctly as Transfer Transaction ID:   "+Cls_Generic_Methods.getTextInElement(oPage_Receive.text_issueTransactionId));

                String eIssueCreatedBy = EHR_Data.user_PRAkashTest+" | "+requisitionOrderDate+" | ";
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_issueCreatedBy).contains(eIssueCreatedBy),
                        " Issue Created Displayed Correctly ");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_issueCreatedBy).contains(sCENTRAL_HUB.split("-")[0]),
                        " Issue Created Store Displayed Correctly as : "+Cls_Generic_Methods.getTextInElement(oPage_Receive.text_issueCreatedBy));

                String eIssueApprovedBy = EHR_Data.user_PRAkashTest+" | "+requisitionOrderDate+" | ";
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_issueApprovedBy).contains(eIssueApprovedBy),
                        " Issue Approved Displayed Correctly as "+ Cls_Generic_Methods.getTextInElement(oPage_Receive.text_issueApprovedBy));

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_issueStore).contains(sCENTRAL_HUB.split("-")[0]),
                        " Issue Store Displayed Correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_issueStatus).contains("approved"),
                        " Issue Status Displayed Correctly");

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_deliveryChallanNumber).contains(deliveryChallanId),
                        " Delivery Challan Id Text Displayed correctly as Delivery Challan No: "+deliveryChallanId);

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_receiveFulfilmentStatus).contains("None"),
                        " Receive Status Text Displayed correctly as Receive Status: "+Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_requisitionStore));
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_receiveTransactionId).contains(receiveTransactionId),
                        " Receive Id Text Displayed correctly as "+receiveTransactionId);

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Receive.text_transferDetails),
                        " Transfer Details Text Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Receive.text_transferStatus),
                        " Transfer Status Text Displayed as approved");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Receive.button_receiveStock),
                        "Clicked on Receive button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.text_itemDescriptionUnderReceiveTransactionPopUp, 5);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Receive.header_receiveTransactionOnModal),
                        " Receive Transaction Popup Opened and Displayed as : Receive Transaction");

                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseModalWith_X);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Receive.button_receiveStock),
                        " Receive Button Displayed After Closing Popup Without Save");
                Cls_Generic_Methods.clickElement(oPage_Receive.button_receiveStock);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.header_receiveTransactionOnModal,5);
                for(WebElement select : oPage_Receive.list_selectSubStore ) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(select, "Default"),
                            " Sub Store Selected as : Default");
                    Cls_Generic_Methods.customWait(1);
                }
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

                    if (srNo.equalsIgnoreCase("1") &&
                            againstInRec.equalsIgnoreCase("Requisition\n" +
                                    sCENTRAL_HUB.split("-")[0]) &&
                            reqInfoInRec.contains(EHR_Data.user_PRAkashTest) &&
                            reqInfoInRec.contains(requisitionOrderDate) &&
                            reqInfoInRec.contains(sRequisition_STORE.split("-")[0]) &&
                            transferInfoInRec.contains(requisitionOrderDate) &&
                            transferInfoInRec.contains(issueId) &&
                            transferInfoInRec.contains(EHR_Data.user_PRAkashTest) &&
                            receiveInfoInRec.contains("Received: Completed") &&
                            receiveInfoInRec.contains(requisitionOrderDate) &&
                            receiveInfoInRec.contains(EHR_Data.user_PRAkashTest) ) {

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(eTransaction),
                                " Issue Record Clicked In Receive Transaction");
                        Cls_Generic_Methods.customWait();
                        receivedRecord = true;
                        break;
                    }
                }

                m_assert.assertTrue(receivedRecord," Received Record Found,Receive Info Updated In Column");

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_receiveFulfilmentStatus).contains("Completed"),
                        " Receive Status Text Displayed correctly as Receive Status: Completed");

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Receive.text_transferClosedStatus),
                        " Transfer Status Changed to Closed Successfully");

                //RECEIVED BY

                String receiveByUI = Cls_Generic_Methods.getTextInElement(oPage_Receive.text_receiveByText);
                m_assert.assertTrue(receiveByUI.contains(EHR_Data.user_PRAkashTest),
                        " Receive By User Name Displayed Correctly");
                m_assert.assertTrue(receiveByUI.contains(requisitionOrderDate),
                        "Receive  By  Date And Time Displayed as "+receiveByUI);

                m_assert.assertTrue(receiveByUI.contains(sRequisition_STORE.split("-")[0]),
                        "Receive  By Store Displayed as "+sRequisition_STORE);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.header_requisitionViewDetailsDropdown),
                        " Requisition Collapse/Expand Dropdown Clicked");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_requisitionStatus).contains("closed"),
                        " Requisition Status Displayed Correctly as closed");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Receive.text_issueTransactionId),
                        " Issue Collapse/Expand Dropdown Displayed and By Default its clicked");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Receive.text_issueStatus).contains("closed"),
                        " Issue Status Displayed Correctly as closed");


                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Receive.text_receivedDetails),
                        " Receive Details Section Displayed After Received");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Receive.text_receiveDetailsStatus),
                        " Received Status Displayed for Receive Details Section");

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

    @Test(enabled = true, description = "Cancel Transfer Transaction")
    public void cancelIssueTransaction() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);

        boolean bTransferEntryFound = false ;
        try {
            createRequisitionInStore();
            try {
                validateRequisitionReceivedAndTransferFromHub();
                try {
                    Cls_Generic_Methods.customWait();
                    CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
                    getTransferIdFromSetting();
                    CommonActions.selectStoreOnApp(sCENTRAL_HUB);
                    Cls_Generic_Methods.switchToOtherTab();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                            "Central hub popup closed");
                    Cls_Generic_Methods.customWait();
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
                    Cls_Generic_Methods.customWait( 6);

                    for (WebElement eValue : oPage_Transfer.list_tableDataTransfer) {
                        List<WebElement> purchaseRow = eValue.findElements(By.xpath("./td"));
                        String againstTxt = Cls_Generic_Methods.getTextInElement(purchaseRow.get(1));
                        String transferIssueInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(2));
                        String reqInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(3));
                        String recInfo = Cls_Generic_Methods.getTextInElement(purchaseRow.get(4));
                        if(againstTxt.replace("\n"," ").equalsIgnoreCase("Requisition "+ sRequisition_STORE.split("-")[0]) &&
                                transferIssueInfo.contains(issueId) &&
                                transferIssueInfo.contains(mapTracker.get(key_NewTransactionInRequisitionReceived_1).split(" ")[0]) &&
                                transferIssueInfo.contains(EHR_Data.user_PRAkashTest) &&
                                reqInfo.contains(EHR_Data.user_PRAkashTest) &&
                                reqInfo.contains(mapTracker.get(key_CreatedAt_RequisitionOrderFromStore).split(" ")[0]) &&
                                reqInfo.contains(sRequisition_STORE.split("-")[0]) &&
                                recInfo.isEmpty()
                        ){
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(eValue),
                                    " Issue Record CLicked In Store");
                            bTransferEntryFound = true;
                            Cls_Generic_Methods.customWait(5);
                            break ;

                        }

                    }
                    m_assert.assertTrue(bTransferEntryFound, "Issue Type Transaction found");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_cancel),
                            "Cancel Button clicked Displayed");
                    Cls_Generic_Methods.customWait();
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_cancelReason,"Checking for Cancel"),
                            "Cancel Reason Enter As: Checking for Cancel ");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.input_cancelButtonInCancel),
                            "Cancel Button Clicked in Cancel Templated");
                    Cls_Generic_Methods.customWait(3);
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_issueStatus).equalsIgnoreCase("cancelled"),
                            "Cancel Issue Working and Status Displayed Correctly as : Cancelled ");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_issueCancelledBy).contains(EHR_Data.user_PRAkashTest),
                            " Issue Cancelled By User Name Displayed Correctly");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_issueCancelledBy).
                                    contains(requisitionOrderDate),
                            "Issue Cancelled By Date Displayed as "+requisitionOrderDate);
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_issueCancelledReason).contains("Checking for Cancel"),
                            " Issue Cancelled Reason Displayed Correctly");


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
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    public void createRequisitionInStore() {
        // 1	Order >> requisition
        // 2	Approve the same requisition order

        Page_Requisition oPage_Requisition = new Page_Requisition(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        boolean itemFoundInRequisition = false;
        boolean bRequisitionOrderFound = false;
        boolean receivingStoreFound = false;


        try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sRequisition_STORE);
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
                for(String item : myMedicationName) {
                    Cls_Generic_Methods.clickElement(oPage_Requisition.input_itemSearchBox);
                    Cls_Generic_Methods.clearValuesInElement(oPage_Requisition.input_itemSearchBox);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_itemSearchBox, item);
                    Cls_Generic_Methods.customWait(3);
                    oPage_Requisition.input_itemSearchBox.sendKeys(Keys.ENTER);
                    Cls_Generic_Methods.customWait(3);

                    for (WebElement eItemName : oPage_Requisition.list_itemNameInPurchaseStore) {
                        if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(item)) {
                            Cls_Generic_Methods.clickElement(eItemName);
                            Cls_Generic_Methods.customWait();
                            itemFoundInRequisition = true;
                            break;
                        }
                    }

                    m_assert.assertTrue(itemFoundInRequisition, "Item found in requisition: <b> " + item + "</b>");
                }

                if (itemFoundInRequisition) {
                    //getting rol_date and rol_time value
                    requisitionOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderTime, "value");
                    requisitionOrderTime = requisitionOrderTime.replaceAll("\\s+", "");
                    requisitionOrderTime = requisitionOrderTime.replace("pm", "PM").replace("am", "AM");
                    if(requisitionOrderTime.length() == 6){
                        requisitionOrderTime  = "0"+requisitionOrderTime;
                    }
                    m_assert.assertTrue("Requisition order time:<b> " + requisitionOrderTime + "</b>");

                    requisitionOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderDate, "value");
                    requisitionOrderDate = requisitionOrderDate.replaceAll("/", "-");
                    m_assert.assertTrue("Requisition order date:<b> " + requisitionOrderDate + "</b>");

                    mapTracker.put(key_CreatedAt_RequisitionOrderFromStore, requisitionOrderDate + "  |  " + requisitionOrderTime);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_quantityForRequisitionList.get(0), "3");
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_quantityForRequisitionList.get(1), "3");
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
                        mapTracker.put(key_RequisitionNumber_Created_InRequisition,reqNum);
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
    public void validateRequisitionReceivedAndTransferFromHub() {
        // 8	order >> requisition received
        // 9	transfer items
        // 10	Approve transfer from central hub

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        boolean bRequisitionReceivedFound = false;

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
                //find created requisition in requisition received
                for (WebElement e : oPage_RequisitionReceived.list_dateTimeOfRequisitionReceived) {
                    String dataTimeAndRequisition = Cls_Generic_Methods.getTextInElement(e);
                    String requisitionNumberInIndentUI = dataTimeAndRequisition.split("\n")[1].trim();
                    if (mapTracker.get(key_RequisitionNumber_Created_InRequisition).equals(requisitionNumberInIndentUI)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(e),
                                " Requisition Record Clicked In Requisition Received");
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

                        mapTracker.put(key_NewTransactionInRequisitionReceived_1, date + "  " + time.substring(0,5)+" "+time.substring(5,7));
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
    public void getTransferIdFromSetting(){

        Page_Transfer oPage_Transfer = new Page_Transfer(driver);

        try{
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Sequence Manager");
            Cls_Generic_Methods.customWait(4);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.issueIdText,10);
            issueId = Cls_Generic_Methods.getTextInElement(oPage_Transfer.issueIdText).trim();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.receiveTransactionIdStoreText,10);
            receiveTransactionId = Cls_Generic_Methods.getTextInElement(oPage_Transfer.receiveTransactionIdStoreText).trim();
            deliveryChallanId = Cls_Generic_Methods.getTextInElement(oPage_Transfer.deliveryChallanIdPharmacyText).trim();


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
    public void addDataForTransactionDetails(){
        addTranssactionDetailsDataList.add("1");
        addTranssactionDetailsDataList.add(sItemName);
        addTranssactionDetailsDataList.add("BT12345");
        addTranssactionDetailsDataList.add("2033-09-30");
        addTranssactionDetailsDataList.add(issueQty);
        addTranssactionDetailsDataList.add("130");
        addTranssactionDetailsDataList.add("315");

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

}
