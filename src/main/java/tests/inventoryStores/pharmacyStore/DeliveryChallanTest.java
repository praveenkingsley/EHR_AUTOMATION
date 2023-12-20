package tests.inventoryStores.pharmacyStore;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import org.openqa.selenium.*;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.store.PharmacyStore.Page_TaxInvoiceDeliveryChallan;
import pages.store.PharmacyStore.Transaction.Page_Receive;


import java.util.HashMap;


public class DeliveryChallanTest extends TaxInvoiceTest {

    @Test(description = "Validate Create Delivery Challan")
    public void validateCreateDeliveryChallan() {
        expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            stockBeforeTransfer = getItemShock(sItemDescription);
            refreshStore();
            boolean bTransferStatus = createDirectTransfer();

            if (bTransferStatus) {
                refreshStore();
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan), "Selected Option in the Left Panel = Tax Invoice / Delivery Challan");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_new, 5);

                //Create New
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_new), "Clicked New Button");
                Cls_Generic_Methods.customWait();
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_deliveryChallan), "Clicked <b>New Delivery Challan</b>");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_headerCreateDeliveryChallan, 10), "Validated --> Navigated to Create Delivery Challan Page");

                //Select To store
                m_assert.assertInfo(selectByOptions(oPage_TaxInvoiceDeliveryChallan.select_againstStore, sReceivingStore.split("-")[0]), "Selected <b>To</b> store as : <b>" + sReceivingStore + "</b>");
                Cls_Generic_Methods.customWait(5);
                for (WebElement lhsRow : oPage_TaxInvoiceDeliveryChallan.row_lhsCreateTaxInvoiceDeliveryChallan) {
                    String transferId = Cls_Generic_Methods.getTextInElement(lhsRow.findElement(By.xpath("./td[3]/div[1]")));
                    if (transferId.equals(sTransferId)) {
                        String transferType = Cls_Generic_Methods.getTextInElement(lhsRow.findElement(By.xpath("./td[2]/div[1]")));
                        String transferAgainstStore = Cls_Generic_Methods.getTextInElement(lhsRow.findElement(By.xpath("./td[2]/div[2]")));
                        String transferDate = Cls_Generic_Methods.getTextInElement(lhsRow.findElement(By.xpath("./td[3]/div[2]")));
                        String transferByUser = Cls_Generic_Methods.getTextInElement(lhsRow.findElement(By.xpath("./td[3]/div[3]")));

                        m_assert.assertTrue("Transfer Transaction found");
                        m_assert.assertTrue(transferType.equalsIgnoreCase("direct"), "Validated Transfer Type : <b>Direct</b>");
                        m_assert.assertTrue(transferAgainstStore.contains(sReceivingStore.split("-")[0]), "Validated Transfer Against Store : <b>" + transferAgainstStore + "</b>");
                        m_assert.assertTrue(transferDate.equalsIgnoreCase(sTransferredAt), "Validated Transfer Date & Time : <b>" + transferDate + "</b>");
                        m_assert.assertTrue(transferByUser.equalsIgnoreCase(expectedLoggedInUser), "Validated Transfer Type : <b>" + transferByUser + "</b>");

                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(lhsRow), "Selected Transfer transaction : " + transferId);
                        break;
                    }
                }

                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.list_rowCreateTiDc, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_deleteItem), "Clicked Remove Item From list");
                Cls_Generic_Methods.customWait();
                m_assert.assertFalse(oPage_TaxInvoiceDeliveryChallan.list_rowCreateTiDc.size() > 0, "Validated --> Remove Functionality -->Item Removed from list");

                //   Validate Time Period
                m_assert.assertFalse(selectAndValidateTimePeriod("Yesterday"), "Validated -->Time Period-Yesterday");
                m_assert.assertTrue(selectAndValidateTimePeriod("This Week"), "Validated -->Time Period-This Week");
                m_assert.assertTrue(selectAndValidateTimePeriod("This Month"), "Validated -->Time Period-This Month");
                m_assert.assertTrue(selectAndValidateTimePeriod("This Quarter"), "Validated -->Time Period-This Quarter");
                m_assert.assertTrue(selectAndValidateTimePeriod("This Year"), "Validated -->Time Period-This Year");
                m_assert.assertFalse(selectAndValidateTimePeriod("Previous Week"), "Validated -->Time Period-Previous Week");
                m_assert.assertFalse(selectAndValidateTimePeriod("Previous Month"), "Validated -->Time Period-Previous Month");
                m_assert.assertFalse(selectAndValidateTimePeriod("Previous Quarter"), "Validated -->Time Period-Previous Quarter");
                m_assert.assertFalse(selectAndValidateTimePeriod("Previous Year"), "Validated -->Time Period-Previous Year");
                m_assert.assertTrue(selectAndValidateTimePeriod("Today"), "Validated -->Time Period-Today");
                Cls_Generic_Methods.customWait(5);

                //add item back
                for (WebElement lhsRow : oPage_TaxInvoiceDeliveryChallan.row_lhsCreateTaxInvoiceDeliveryChallan) {
                    String transferId = Cls_Generic_Methods.getTextInElement(lhsRow.findElement(By.xpath("./td[3]/div[1]")));
                    if (transferId.equals(sTransferId)) {
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(lhsRow), "Selected Transfer transaction : " + transferId);
                        break;
                    }
                }

                //Table Validation
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_deleteItem, 10);
                HashMap<String, String> tableValue = new HashMap<>();
                for (int i = 0; i < oPage_TaxInvoiceDeliveryChallan.list_tableHeaderCreateTiDc.size(); i++) {
                    String headerValue = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.list_tableHeaderCreateTiDc.get(i));
                    String rowValue = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.list_textBodyValueCreateTiDc.get(i));
                    tableValue.put(headerValue, rowValue);
                }

                m_assert.assertTrue(tableValue.get("Description").equalsIgnoreCase(sItemDescription), "Validated Item Description : <b>" + tableValue.get("Description") + "</b>");
                m_assert.assertTrue(Double.parseDouble(tableValue.get("Qty.")) == Double.parseDouble(sQuantity), "Validated Item Quantity : <b>" + tableValue.get("Qty.") + "</b>");
                m_assert.assertTrue(tableValue.get("HSN").equalsIgnoreCase(sItemHSN_code), "Validated Item HSN : <b>" + tableValue.get("HSN") + "</b>");
                m_assert.assertTrue(tableValue.get("UOM").equalsIgnoreCase(sItemUOM), "Validated Item UOM : <b>" + tableValue.get("UOM") + "</b>");
                m_assert.assertTrue(tableValue.get("Batch No.").equalsIgnoreCase(sItemBatchNumber), "Validated Item Batch No : <b>" + tableValue.get("Batch No.") + "</b>");
                m_assert.assertTrue(tableValue.get("Expiry Dt.").equalsIgnoreCase(sItemExpiryDate), "Validated Item Expiry Dt. : <b>" + tableValue.get("Expiry Dt.") + "</b>");
                m_assert.assertTrue(Double.parseDouble(tableValue.get("Rate")) == Double.parseDouble(sItemRate), "Validated Item Rate : <b>" + tableValue.get("Rate") + "</b>");
                m_assert.assertTrue(Double.parseDouble(tableValue.get("MRP")) == Double.parseDouble(sMRP), "Validated Item MRP : <b>" + tableValue.get("MRP") + "</b>");
                expectedNetAmount = Double.parseDouble(sItemRate);
                m_assert.assertTrue(Double.parseDouble(tableValue.get("Net Amt.")) == expectedNetAmount, "Validated Item Net Amt : <b>" + tableValue.get("Net Amt.") + "</b>");

                //Footer Calculations
                String finalTotalAmount = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_finalTotalAmount, "value");

                m_assert.assertTrue(Double.parseDouble(finalTotalAmount) == expectedNetAmount, "Validated -> Final Total Amount = <b>" + finalTotalAmount + "</b>");

                //Set Date & Time

                Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.input_date);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.input_todayBillDate, 3);
                Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.input_todayBillDate);
                String date = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_date, "value").trim();
                m_assert.assertInfo("Selected Transaction Date as : <b>" + date + "</b>");

                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.input_time);
                m_assert.assertInfo(setBootstrapTime(driver, 8, 8, "PM"), "Selected Transaction Time as : <b>08:08 PM</b>");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.input_dispatchRemark);
                //Dispatch Details
                m_assert.assertInfo(selectByOptions(oPage_TaxInvoiceDeliveryChallan.select_transportationMode, "Test"), "Selected <b>Test</b> in Transportation Mode");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_TaxInvoiceDeliveryChallan.input_transactionId, "1234"), "Entered <b>1234</b> in Transaction Id");

                Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.input_deliverydate);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.input_todayBillDate, 3);
                Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.input_todayBillDate);
                String deliveryDate = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_deliverydate, "value").trim();
                m_assert.assertInfo("Selected Delivery Date as : <b>" + deliveryDate + "</b>");

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_TaxInvoiceDeliveryChallan.input_dispatchRemark, "AUTO"), "Entered <b>AUTO</b> in Dispatch Remark");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_saveChanges), "Clicked <b>Save Changes</b>");
                customisedWaitTillDisappear(oPage_TaxInvoiceDeliveryChallan.text_headerCreateTaxInvoice);
                taxInvoiceCreatedAt = getCurrentDateTime();

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_refresh), "Clicked <b>Refresh</b> button");
                Cls_Generic_Methods.customWait(5);
                String status = selectTransaction();


                if (status.equalsIgnoreCase("open")) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_rhs_transactionId, 10);
                    String createdAtDate = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_createdAt);
                    if (createdAtDate.contains(taxInvoiceCreatedAt.split(":")[0])) {
                        sTransactionId = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_transactionId);
                        m_assert.assertTrue("Delivery Challan created successfully -> Transaction ID : <b>" + sTransactionId + "</b>");
                        String rhsTransactionType = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_transactionType);
                        String rhsToStore = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_toStoreName);
                        String rhsCreatedByUser = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_createdByUser);
                        String rhsStatus = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_status);

                        //RHS Validations
                        m_assert.assertTrue(rhsTransactionType.equalsIgnoreCase("Delivery Challan"), "Validated RHS -> Transaction Type : <b>" + rhsTransactionType + "</b>");
                        m_assert.assertTrue(rhsToStore.equalsIgnoreCase(sReceivingStore.replaceAll("-", " |")), "Validated RHS -> Receiving Store : <b>" + rhsToStore + "</b>");
                        m_assert.assertTrue(rhsCreatedByUser.equalsIgnoreCase(expectedLoggedInUser), "Validated RHS -> Transaction Created by user : <b>" + rhsCreatedByUser + "</b>");
                        m_assert.assertTrue(rhsStatus.equalsIgnoreCase("Open"), "Validated RHS -> Status : <b>" + rhsStatus + "</b>");
                    }

                } else {
                    m_assert.assertFatal("Unable to select Transaction");
                }
                //Search Validation
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_TaxInvoiceDeliveryChallan.input_globalSearch, sTransactionId), "Entered <b>" + sTransactionId + "</b> in Transaction global search");
                Cls_Generic_Methods.customWait(4);
                oPage_TaxInvoiceDeliveryChallan.input_globalSearch.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(3);

                if (oPage_TaxInvoiceDeliveryChallan.list_transactionCreatedList.size() == 1) {
                    String transactionIdInRow = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.list_transactionCreatedList.get(0).findElement(By.xpath("./td[4]")));
                    m_assert.assertTrue(transactionIdInRow.equals(sTransactionId), "Validated Search -> Transaction Found");
                } else {
                    m_assert.assertFatal("Unable to validate search");
                }

                Cls_Generic_Methods.customWait();
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_clearGlobalSearch),"Clicked clear search button");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_sortTaxInvoiceAndDeliveryChallan);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_sortByDeliveryChallan),"Clicked sort by Delivery Challan");
                Cls_Generic_Methods.customWait();

                boolean sortStatus=true;
                for (WebElement transDetails:oPage_TaxInvoiceDeliveryChallan.list_transactionDetailsCreatedList) {
                    String value=Cls_Generic_Methods.getTextInElement(transDetails);
                    if(!value.equalsIgnoreCase("DELIVERY CHALLAN")){
                        m_assert.assertFalse("Unable to sort transactions by Delivery Challan");
                        sortStatus=false;
                        break;
                    }
                }
                m_assert.assertTrue(sortStatus,"Validated -> Sort transaction by Delivery Challan");


            } else {
                m_assert.assertFatal("Transfer transaction not created");
            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate create Tax Invoice");
        }
    }

    @Test(description = "Validate Edit Delivery Challan")
    public void validateEditDeliveryChallan() {
        expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan), "Selected Option in the Left Panel = Tax Invoice / Delivery Challan");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_sortTaxInvoiceAndDeliveryChallan, 20);
            String status = selectTransaction(sTransactionId);

            if (status.equalsIgnoreCase("open")) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_edit, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_edit), "Clicked <b>Edit</b> button ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_headerCreateTaxInvoice, 10);
                m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_TaxInvoiceDeliveryChallan.select_againstStore).contains(sReceivingStore.split("-")[0]), "Validated Edit -> Receiving Store : " + sReceivingStore);
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_tableDescription).equals(sItemDescription), "Validated Edit -> Selected item  : " + sReceivingStore);
                String totalAmountInEdit = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_finalTotalAmount, "value");
                m_assert.assertTrue(Double.parseDouble(totalAmountInEdit) == expectedNetAmount, "Validated Edit -> Final Total Amount = <b>" + totalAmountInEdit + "</b>");

                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.list_rowCreateTiDc, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_deleteItem), "Clicked Remove Item From list");
                Cls_Generic_Methods.customWait();
                m_assert.assertFalse(oPage_TaxInvoiceDeliveryChallan.list_rowCreateTiDc.size() > 0, "Validated Edit--> Item Removed from list");

                for (WebElement lhsRow : oPage_TaxInvoiceDeliveryChallan.row_lhsCreateTaxInvoiceDeliveryChallan) {
                    String transferId = Cls_Generic_Methods.getTextInElement(lhsRow.findElement(By.xpath("./td[3]/div[1]")));
                    if (transferId.equals(sTransferId)) {
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(lhsRow), "Selected Transfer transaction : " + transferId);
                        break;
                    }
                }
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_saveChanges), "Clicked <b>Save Changes</b>");
                taxInvoiceCreatedAt = getCurrentDateTime();
                Cls_Generic_Methods.customWait(3);

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_refresh), "Clicked <b>Refresh</b> button");
                Cls_Generic_Methods.customWait();
                status = selectTransaction(sTransactionId);


                if (status.equalsIgnoreCase("open")) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_rhs_transactionId, 10);
                    String updatedDate = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_createdAt);
                    m_assert.assertTrue(updatedDate.contains(taxInvoiceCreatedAt.split(":")[0]), "Transaction time updated : <b>" + updatedDate + "</b>");

                } else {
                    m_assert.assertFatal("Unable to find Transaction : Transaction Status : " + status);
                }


            } else {
                m_assert.assertFatal("Unable to find Transaction : Transaction Status : " + status);
            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Edit" + e);
        }
    }

    @Test(description = "Validate Approve Delivery Challan")
    public void validateApproveDeliveryChallan() {
        expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan), "Selected Option in the Left Panel = Tax Invoice / Delivery Challan");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_sortTaxInvoiceAndDeliveryChallan, 20);
            String status = selectTransaction(sTransactionId);

            if (status.equalsIgnoreCase("open")) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_approve, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_approve), "<b>Approve</b> button is clickable");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_approvedNotifyMsg, 10), "<b>This Transaction is approved</b> notify message is displayed");
                Cls_Generic_Methods.customWait();
                String rhsStatus = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_status);
                m_assert.assertTrue(rhsStatus.equalsIgnoreCase("approved"), "Validated Approve --> Transaction RHS --> STATUS : <b>" + rhsStatus + "</b>");
                String rhsApprovedBy = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_approvedByUser);
                m_assert.assertTrue(rhsApprovedBy.equals(expectedLoggedInUser), "Validated Approve --> Transaction RHS --> APPROVED BY USER : <b>" + rhsApprovedBy + "</b>");

                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_print, 10), "Validated Approve --> Print option is displayed");
                m_assert.assertFalse(Cls_Generic_Methods.isElementDisplayed(oPage_TaxInvoiceDeliveryChallan.button_edit), "Validated Approve --> Edit option is removed");

                int preWindowsCount = driver.getWindowHandles().size();
                String initialWindowHandle = driver.getWindowHandle();
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_print), "Clicked Print button");
                Cls_Generic_Methods.customWait(8);
                int postWindowsCount = driver.getWindowHandles().size();
                m_assert.assertTrue(postWindowsCount > preWindowsCount, "Validated Print -->Transaction Print page opened");

                for (String currentWindowHandle : driver.getWindowHandles()) {
                    if (!currentWindowHandle.equals(driver.getWindowHandle())) {
                        driver.switchTo().window(currentWindowHandle);
                    }

                }
                driver.close();
                driver.switchTo().window(initialWindowHandle);

            } else {
                m_assert.assertFatal("Unable to find Transaction : Transaction Status : " + status);
            }

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Approve functionality -->" + e);
        }
    }

    @Test(description = "Validate Transaction displayed in receiving store")
    public void validateTransactionReceivedDeliveryChallan() {
        expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);
        oPage_Receive = new Page_Receive(driver);
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sReceivingStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Receive");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.button_todayButtonInTransaction, 10);

            boolean bTransactionStatus = false;
            String rhsStatus = "";
            for (WebElement row : oPage_Receive.list_text_transactionIdRow) {
                String value = Cls_Generic_Methods.getTextInElement(row);
                if (value.equals(sTransferId)) {
                    bTransactionStatus = true;
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(row), "Found Transaction : <b>" + value + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.button_receiveStock, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Receive.button_receiveStock), "Clicked <b>Receive</b>");
                    boolean selectSubStore = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.select_subStore, 5);
                    if (selectSubStore) {
                        Cls_Generic_Methods.selectElementByIndex(oPage_Receive.select_subStore, 0);
                    }
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Receive.button_saveChanges), "Clicked <b>Save Changes</b>");
                    customisedWaitTillDisappear(oPage_Receive.select_subStore);
                    rhsStatus = Cls_Generic_Methods.getTextInElement(oPage_Receive.text_rhsStatus);
                    m_assert.assertTrue(rhsStatus.contains("Received"), "Transaction received successfully");
                    break;
                }
            }
            if (!bTransactionStatus) {
                m_assert.assertFatal("Unable to find transaction in receiving store");
            }

            if (rhsStatus.contains("Received")) {
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.driverRefresh();
                CommonActions.selectStoreOnApp(store);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                Cls_Generic_Methods.customWait();

                stockAfterTransfer = getItemShock(sItemDescription);
                m_assert.assertTrue((Double.parseDouble(stockBeforeTransfer) - Double.parseDouble(sQuantity)) == Double.parseDouble(stockAfterTransfer), "Validated Receive -> Available Stock reduced from <b>" + stockBeforeTransfer + "</b> to <b>" + stockAfterTransfer + "</b>");
            }

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate Receive " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validating Cancel Delivery Challan")
    public void validateCancelDeliveryChallan() {
        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            createTaxInvoiceDeliveryChallanWithMandatoryField("DELIVERY CHALLAN",false);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan), "Selected Option in the Left Panel = Tax Invoice / Delivery Challan");
            Cls_Generic_Methods.customWait();
            String status = selectTransaction(sTransactionId);

            if (status.equalsIgnoreCase("open")) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_cancel, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_TaxInvoiceDeliveryChallan.button_cancel), "Clicked Cancel Transaction--> Transaction ID =" + sTransactionId);
                String cancellationReason = "AUTO TESTING CANCELLATION";
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.input_cancellationReason, 10);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_TaxInvoiceDeliveryChallan.input_cancellationReason, cancellationReason);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_dontCancel), "Clicked Don't Cancel button");
                Cls_Generic_Methods.clickElement(driver, oPage_TaxInvoiceDeliveryChallan.button_cancel);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.input_cancellationReason, 10);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_TaxInvoiceDeliveryChallan.input_cancellationReason, cancellationReason), "Entered <b>" + cancellationReason + "</b> in cancellation reason");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_confirmCancel), "Clicked <b>Cancel Tax Invoice</b> button");
                String currentTime = getCurrentDateTime();
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_cancelledNotifyMsg, 10), "<b>Transaction is cancelled</b> notify message is displayed");

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_sortTaxInvoiceAndDeliveryChallan, 10);
                status = selectTransaction(sTransactionId);
                Cls_Generic_Methods.customWait(4);
                if (status.equalsIgnoreCase("cancelled")) {
                    String rhsStatus = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_status);
                    String rhsCancelledBy = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_cancelledByUser);
                    String rhsCancelledAt = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_cancelledAt);

                    m_assert.assertTrue(rhsStatus.equalsIgnoreCase("cancelled"), "Validated Cancel --> RHS Status = <b>" + rhsStatus + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_cancelReason).equalsIgnoreCase(cancellationReason), "Validated RHS -->Cancelled Reason = <b>" + cancellationReason + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_cancelledByUser).equalsIgnoreCase(expectedLoggedInUser), "Validated RHS -->Cancelled By User = <b>" + rhsCancelledBy + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_cancelledAt).contains(currentTime.split(":")[0]), "Validated RHS -->Cancelled Reason = <b>" + rhsCancelledAt + "</b>");

                    Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                } else {
                    m_assert.assertFatal("Unable to find Transaction : Transaction Status : " + status);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Delivery Challan Cancel functionality-->" + e);
        }
    }

}
