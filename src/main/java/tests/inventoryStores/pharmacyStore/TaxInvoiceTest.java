package tests.inventoryStores.pharmacyStore;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.*;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Page_TaxInvoiceDeliveryChallan;
import pages.store.PharmacyStore.Transaction.Page_Receive;
import pages.store.PharmacyStore.Transaction.Page_Transfer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TaxInvoiceTest extends TestBase {
    String store = "Pharmacy automation- Pharmacy";
    public String sReceivingStore = "OpticalStore- Optical";
    String stockBeforeTransfer;
    String stockAfterTransfer;
    String sTransactionId;
    public String sTransferId;
    String sTransferredAt;
    String storeGSTno;
    String receivingStoreGSTno;

    String sQuantity = "1";
    String sItemDescription = "Luxturna";
    String sItemHSN_code = "NYC-1000";
    String sItemUOM = "Capsules";
    String sItemExpiryDate = "2033-01-31";
    String sItemRate = "1000.0";
    String sItemBatchNumber = "310197";
    String sMRP = "2000.0";
    String sTaxableAmount = "1000.0";
    String sTaxPercentage = "28.0";
    String totalAmountInTransfer;
    Page_CommonElements oPage_CommonElements;
    Page_Transfer oPage_Transfer;
    Page_TaxInvoiceDeliveryChallan oPage_TaxInvoiceDeliveryChallan;
    Page_Receive oPage_Receive;
    String expectedLoggedInUser;
    boolean gstTypeIGST = true;
    String taxInvoiceCreatedAt;
    double expectedNetAmount;

    @Test(description = "Validate Create TaxInvoice")
    public void validateCreateTaxInvoice() {
        expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.customWait();
            getGST_valueFromSetting();
            Cls_Generic_Methods.driverRefresh();
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
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_taxInvoice), "Clicked <b>New Tax invoice</b>");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_headerCreateTaxInvoice, 10), "Validated --> Navigated to Create Tax Invoice Page");

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
                m_assert.assertTrue(Double.parseDouble(tableValue.get("Taxable Amt.")) == Double.parseDouble(sTaxableAmount), "Validated Item Taxable Amt : <b>" + tableValue.get("Taxable Amt.") + "</b>");
                String taxAmount = tableValue.get("Tax").split(sTaxPercentage)[0];

                double expectedTaxAmount = Double.parseDouble(sItemRate) * (Double.parseDouble(sTaxPercentage) / 100);
                m_assert.assertTrue(Double.parseDouble(taxAmount) == expectedTaxAmount, "Validated Item Tax Amount : <b>" + taxAmount + "</b>");
                expectedNetAmount = Double.parseDouble(sItemRate) + expectedTaxAmount;
                m_assert.assertTrue(Double.parseDouble(tableValue.get("Net Amt.")) == expectedNetAmount, "Validated Item Net Amt : <b>" + tableValue.get("Net Amt.") + "</b>");

                //Footer Calculations
                validateTaxBreakUp();

                String finalTaxableAmount = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_finalTaxableAmount, "value");
                String finalTaxAmount = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_finalTaxAmount, "value");
                String finalTotalAmount = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_finalTotalAmount, "value");

                m_assert.assertTrue(Double.parseDouble(finalTaxableAmount) == Double.parseDouble(sTaxableAmount), "Validated -> Final Taxable Amount = <b>" + finalTaxableAmount + "</b>");
                m_assert.assertTrue(Double.parseDouble(finalTaxAmount) == expectedTaxAmount, "Validated -> Final Tax Amount = <b>" + finalTaxAmount + "</b>");
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
                        m_assert.assertTrue("Tax Invoice created successfully -> Transaction ID : <b>" + sTransactionId + "</b>");
                        String rhsTransactionType = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_transactionType);
                        String rhsToStore = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_toStoreName);
                        String rhsCreatedByUser = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_createdByUser);
                        String rhsStatus = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_status);

                        //RHS Validations
                        m_assert.assertTrue(rhsTransactionType.equalsIgnoreCase("Tax Invoice"), "Validated RHS -> Transaction Type : <b>" + rhsTransactionType + "</b>");
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
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_sortByTaxInvoice),"Clicked sort by Tax Invoice");
                Cls_Generic_Methods.customWait();

                boolean sortStatus=true;
                for (WebElement transDetails:oPage_TaxInvoiceDeliveryChallan.list_transactionDetailsCreatedList) {
                    String value=Cls_Generic_Methods.getTextInElement(transDetails);
                    if(!value.equalsIgnoreCase("TAX INVOICE")){
                        m_assert.assertFalse("Unable to sort transactions by Tax Invoice");
                        sortStatus=false;
                        break;
                    }
                }
                m_assert.assertTrue(sortStatus,"Validated -> Sort transaction by Tax Invoice");


            } else {
                m_assert.assertFatal("Transfer transaction not created");
            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate create Tax Invoice");
        }
    }

    @Test(description = "Validate Edit TaxInvoice")
    public void validateEditTaxInvoice() {
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
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_edit, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_edit), "Clicked <b>Edit</b> button ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_headerCreateTaxInvoice, 10);
                m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_TaxInvoiceDeliveryChallan.select_againstStore).contains(sReceivingStore.split("-")[0]), "Validated Edit -> Receiving Store : " + sReceivingStore);
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_tableDescription).equals(sItemDescription), "Validated Edit -> Selected item  : " + sReceivingStore);
                String totalAmountInEdit = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_finalTotalAmount, "value");
                m_assert.assertTrue(Double.parseDouble(totalAmountInEdit) == expectedNetAmount, "Validated Edit -> Final Total Amount = <b>" + totalAmountInEdit + "</b>");

                validateTaxBreakUp();

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

    @Test(description = "Validate Approve TaxInvoice")
    public void validateApproveTaxInvoice() {
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
    public void validateTransactionReceivedTaxInvoice() {
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
                    boolean selectSubStore = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.select_subStore, 10);
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

    @Test(description = "Validating Cancel Tax Invoice")
    public void validateCancelTaxInvoice() {
        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            createTaxInvoiceDeliveryChallanWithMandatoryField("TAX INVOICE",false);
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

                } else {
                    m_assert.assertFatal("Unable to find Transaction : Transaction Status : " + status);
                }

            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Tax Invoice Cancel functionality-->" + e);
        }
    }

    public boolean createDirectTransfer() {
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Transfer = new Page_Transfer(driver);
        boolean status = false;
        try {
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(2);
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_newTransaction, 5);
            Cls_Generic_Methods.clickElement(oPage_Transfer.button_newTransaction);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.dropdown_receivingStore, 5);
            Cls_Generic_Methods.clickElement(oPage_Transfer.dropdown_receivingStore);

            boolean receivingStoreFound = false;
            Cls_Generic_Methods.customWait(3);
            for (WebElement eReceivingStore : oPage_Transfer.list_receivingStore) {
                if (Cls_Generic_Methods.getTextInElement(eReceivingStore).equalsIgnoreCase(sReceivingStore.split("-")[0])) {
                    Cls_Generic_Methods.clickElement(eReceivingStore);
                    receivingStoreFound = true;
                    break;
                }
            }

            m_assert.assertInfo(receivingStoreFound, "Selected Received Store as : <b>" + sReceivingStore.split("-")[0] + "</b>");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_searchByDescription, 5);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_searchByDescription), "Clicked on Description button");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItemToBeTransferred, 5);
            Cls_Generic_Methods.customWait(3);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_searchByDescription, 5);
            Cls_Generic_Methods.clickElement(oPage_Transfer.button_searchByDescription);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItemToBeTransferred, 5);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_searchItemToBeTransferred, sItemDescription);
            Cls_Generic_Methods.customWait(1);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_searchItemToBeTransferred, " ");
            Cls_Generic_Methods.customWait(3);
            for (WebElement eItemVariantCode : oPage_Transfer.list_itemDescriptionRow
            ) {
                if (Cls_Generic_Methods.getTextInElement(eItemVariantCode).equalsIgnoreCase(sItemDescription)) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(eItemVariantCode), "Selected Item : <b>" + sItemDescription + "</b>");
                    break;
                }
            }

            Cls_Generic_Methods.customWait(3);
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.list_quantityFieldForItemsToTransfer.get(0), sQuantity),
                    "Entered <b>" + sQuantity + "</b> in Quantity");

            Cls_Generic_Methods.customWait();
            totalAmountInTransfer = Cls_Generic_Methods.getElementAttribute(oPage_Transfer.input_totalAmountIncludingTax, "value");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_saveChanges), "Transfer Transaction saved");
            String currentTime = getCurrentDateTime().replaceAll("\\|", "");
            customisedWaitTillDisappear(oPage_Transfer.input_totalAmountIncludingTax);

            for (WebElement e : oPage_Transfer.list_transferTransactionRow) {
                sTransferId = Cls_Generic_Methods.getTextInElement(e.findElement(By.xpath("./child::div[1]")));
                sTransferredAt = Cls_Generic_Methods.getTextInElement(e.findElement(By.xpath("./child::div[2]")));

                if (sTransferredAt.contains(currentTime.split(":")[0])) {
                    Cls_Generic_Methods.clickElement(e);
                    m_assert.assertInfo("Transfer ID : <b>" + sTransferId + "</b> || Transfer Date : <b>" + sTransferredAt + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_approveTransferTransaction, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Transfer.button_approveTransferTransaction), "Approved Transfer Transaction");
                    status = true;
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertInfo("Unable to create Transfer " + e);
        }
        return status;
    }

    public void createTaxInvoiceDeliveryChallanWithMandatoryField(String transactionType,boolean bTransferCreated) {
        boolean status = false;
        oPage_CommonElements=new Page_CommonElements(driver);
        oPage_TaxInvoiceDeliveryChallan=new Page_TaxInvoiceDeliveryChallan(driver);

        try {
            if (!bTransferCreated) {
                bTransferCreated = createDirectTransfer();
            }
            if (bTransferCreated) {
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                Cls_Generic_Methods.customWait(2);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan), "Selected Option in the Left Panel = Tax Invoice / Delivery Challan");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_new, 5);

                //Create New
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_new), "Clicked New Button");
                Cls_Generic_Methods.customWait();
                if (transactionType.equalsIgnoreCase("DELIVERY CHALLAN")) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_deliveryChallan), "Clicked <b>New Delivery Challan</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_headerCreateDeliveryChallan, 10);

                } else if (transactionType.equalsIgnoreCase("TAX INVOICE")) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_taxInvoice), "Clicked <b>New Tax invoice</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_headerCreateTaxInvoice, 10);

                } else {
                    m_assert.assertFalse("Enter Valid Transaction Type");
                }

                //Select To store
                m_assert.assertInfo(selectByOptions(oPage_TaxInvoiceDeliveryChallan.select_againstStore, sReceivingStore.split("-")[0]), "Selected <b>To</b> store as : <b>" + sReceivingStore + "</b>");
                Cls_Generic_Methods.customWait(5);
                for (WebElement lhsRow : oPage_TaxInvoiceDeliveryChallan.row_lhsCreateTaxInvoiceDeliveryChallan) {
                    String transferId = Cls_Generic_Methods.getTextInElement(lhsRow.findElement(By.xpath("./td[3]/div[1]")));
                    if (transferId.equals(sTransferId)) {
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(lhsRow), "Selected Transfer transaction : " + transferId);
                        break;
                    }
                }

                //Dispatch Details
                Cls_Generic_Methods.customWait();
                m_assert.assertInfo(selectByOptions(oPage_TaxInvoiceDeliveryChallan.select_transportationMode, "Test"), "Selected <b>Test</b> in Transportation Mode");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_TaxInvoiceDeliveryChallan.input_transactionId, "1234"), "Entered <b>1234</b> in Transaction Id");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_saveChanges), "Clicked <b>Save Changes</b>");
                customisedWaitTillDisappear(oPage_TaxInvoiceDeliveryChallan.text_headerCreateTaxInvoice);
                taxInvoiceCreatedAt = getCurrentDateTime();

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_refresh), "Clicked <b>Refresh</b> button");
                Cls_Generic_Methods.customWait();
                String transactionStatus = selectTransaction();


                if (transactionStatus.equalsIgnoreCase("open")) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_rhs_transactionId, 10);
                    String createdAtDate = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_createdAt);
                    if (createdAtDate.contains(taxInvoiceCreatedAt.split(":")[0])) {
                        sTransactionId = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_transactionId);
                        m_assert.assertTrue("Tax Invoice created successfully -> Transaction ID : <b>" + sTransactionId + "</b>");
                        String rhsStatus = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_status);
                        m_assert.assertTrue(rhsStatus.equalsIgnoreCase("Open"), "Transaction Status : <b>" + rhsStatus + "</b>");
                    }

                } else {
                    m_assert.assertFatal("Unable to select Transaction");
                }

            } else {
                m_assert.assertFatal("Transfer transaction not created");
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to create Tax Invoice -> " + e);
        }

    }


    public String getCurrentDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | hh:mm a");
        Date date = new Date();
        //  07-06-2023 | 01:09 PM
        String date1 = dateFormat.format(date);
        return date1;
    }


    public void customisedWaitTillDisappear(WebElement ele) {
        try {
            int sec=0;
            do {
                Cls_Generic_Methods.customWait();
                sec++;
            } while (ele.isDisplayed()||sec==5);
        } catch (NoSuchElementException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean selectAndValidateTimePeriod(String periodToSelect) {
        boolean status = false;
        try {
            Cls_Generic_Methods.clickElement(driver, oPage_TaxInvoiceDeliveryChallan.button_timePeriod);
            Cls_Generic_Methods.clickElement(driver.findElement(By.xpath("//div[@class='items-variants-search']//li/a[text()='" + periodToSelect + "']")));
            Cls_Generic_Methods.customWait(3);

            for (WebElement row : oPage_TaxInvoiceDeliveryChallan.row_transferIdLhsCreateTaxInvoiceDeliveryChallan) {
                String transferId = Cls_Generic_Methods.getTextInElement(row);
                if (transferId.equals(sTransferId)) {
                    status = true;
                    break;
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to select " + periodToSelect);
        }
        return status;
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

    public static boolean setBootstrapTime(WebDriver driver, int hours, int minutes, String meridian) {
        boolean status = false;
        try {
            WebElement hourInput = driver.findElement(By.xpath("//input[@class='bootstrap-timepicker-hour']"));
            WebElement minuteInput = driver.findElement(By.xpath("//input[@class='bootstrap-timepicker-minute']"));

            // Click the up arrow button to increase the minutes
            WebElement minuteUpButton = driver.findElement(By.xpath("//a[@data-action='incrementMinute']"));
            while (true) {
                int value = Integer.parseInt(Cls_Generic_Methods.getElementAttribute(minuteInput, "value"));
                if (value != minutes) {
                    Cls_Generic_Methods.clickElement(driver, minuteUpButton);
                } else {
                    break;
                }
            }
            // Click the up arrow button to increase the hours
            WebElement hourUpButton = driver.findElement(By.xpath("//a[@data-action='incrementHour']"));
            while (true) {
                int value = Integer.parseInt(Cls_Generic_Methods.getElementAttribute(hourInput, "value"));
                if (value != hours) {
                    Cls_Generic_Methods.clickElement(driver, hourUpButton);
                } else {
                    break;
                }
            }
            // Locate the meridian input field and set AM or PM
            WebElement meridianInput = driver.findElement(By.xpath("//input[@class='bootstrap-timepicker-meridian']"));
            WebElement meridianUpButton = driver.findElement(By.xpath("//a[@data-action='toggleMeridian']"));

            while (true) {
                String value = Cls_Generic_Methods.getElementAttribute(meridianInput, "value");
                if (!value.equals(meridian)) {
                    Cls_Generic_Methods.clickElement(driver, hourUpButton);
                } else {
                    break;
                }
            }
            status = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public String selectTransaction(String... transactionId) {
        String status = "";
        String selectTransactionRow = null;


        try {
            if (transactionId.length > 0) {
                selectTransactionRow = transactionId[0];
            }

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.list_transactionHeaderList, 10);
            List<String> transactionHeaderList = new ArrayList<>();
            for (WebElement headerList : oPage_TaxInvoiceDeliveryChallan.list_transactionHeaderList) {
                transactionHeaderList.add(Cls_Generic_Methods.getTextInElement(headerList));
            }
            for (WebElement row : oPage_TaxInvoiceDeliveryChallan.list_transactionCreatedList) {
                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> transactionRow = row.findElements(By.xpath("./child::*"));

                    String sStatus = Cls_Generic_Methods.getTextInElement(transactionRow.get(transactionHeaderList.indexOf("Status")));
                    String sTransId = Cls_Generic_Methods.getTextInElement(transactionRow.get(transactionHeaderList.indexOf("Txn ID")));

                    System.out.println("----STATUS---" + sStatus);
                    if (transactionId.length == 0) {
                        if (sStatus.equalsIgnoreCase("open")) {
                            Cls_Generic_Methods.clickElement(row);
                            status = sStatus;
                            break;
                        }
                    } else {
                        if (sTransId.equals(selectTransactionRow)) {
                            Cls_Generic_Methods.clickElement(row);
                            status = sStatus;
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to find transaction " + e);
        }
        return status;
    }

    public void validateTaxBreakUp() {

        double taxBreakupPercentage = Double.parseDouble(sTaxPercentage);
        double totalAmountBeforeTax = (Double.parseDouble(sItemRate) * Double.parseDouble(sQuantity));
        double totalTaxAmount = (totalAmountBeforeTax * taxBreakupPercentage) / 100;
        System.out.println(taxBreakupPercentage + "-------" + totalAmountBeforeTax);

        if (gstTypeIGST) {
            try {
                String igstRate = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_igstRate, "value").replaceAll("[^0-9]", "");
                String igstAmount = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_igstAmount, "value");

                m_assert.assertTrue(Double.parseDouble(igstRate) == taxBreakupPercentage, "Validated IGST breakup percentage--> IGST - <b>" + igstRate + "%</b>");
                m_assert.assertTrue(Double.parseDouble(igstAmount) == totalTaxAmount, "Validated IGST breakup amount--> IGST - <b>" + igstAmount + "</b>");
            } catch (Exception e) {
                m_assert.assertFalse("IGST Value is empty - unable to validate tax breakup");
                e.printStackTrace();
            }
        } else {

            try {
                String cgstRate = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_cgstRate, "value").replaceAll("[^0-9]", "");
                String cgstAmount = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_cgstAmount, "value");
                String sgstRate = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_sgstRate, "value").replaceAll("[^0-9]", "");
                String sgstAmount = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_sgstAmount, "value");

                m_assert.assertTrue(Double.parseDouble(cgstRate) == taxBreakupPercentage / 2, "Validated CGST breakup percentage--> CGST - <b>" + cgstRate + "%</b>");
                m_assert.assertTrue(Double.parseDouble(cgstAmount) == totalTaxAmount / 2, "Validated CGST breakup amount--> CGST - <b>" + cgstAmount + "</b>");

                m_assert.assertTrue(Double.parseDouble(sgstRate) == taxBreakupPercentage / 2, "Validated SGST breakup percentage--> SGST - <b>" + sgstRate + "%</b>");
                m_assert.assertTrue(Double.parseDouble(sgstAmount) == totalTaxAmount / 2, "Validated SGST breakup amount--> SGST - <b>" + sgstAmount + "</b>");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFalse("CGST & SGST value is empty - unable to validate tax breakup ");
            }
        }
    }

    public void getGST_valueFromSetting() {
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);

        try {
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 3);

            int storeNo = 0;
            for (WebElement txtStoreName : oPage_StoreSetUp.list_text_storeName) {
                String storeName = Cls_Generic_Methods.getTextInElement(txtStoreName);
                if (storeName.contains(store.split("-")[0])) {
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.list_btn_editStore.get(storeNo));
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_StoreSetUp.list_btn_editStoreBillingAddress, 10);

                    for (WebElement btn_Edit : oPage_StoreSetUp.list_btn_editStoreBillingAddress) {
                        boolean defaultAddress = false;

                        try {
                            Cls_Generic_Methods.isElementDisplayed(btn_Edit.findElement(By.xpath("./parent::td/following-sibling::td/a[text()='Mark Default']")));
                        } catch (NoSuchElementException e) {
                            defaultAddress = true;
                        }

                        if (defaultAddress) {
                            Cls_Generic_Methods.clickElement(driver, btn_Edit);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.input_gstNo, 10);
                            storeGSTno = Cls_Generic_Methods.getElementAttribute(oPage_StoreSetUp.input_gstNo, "value");
                            m_assert.assertInfo("GST no. present in " + storeName + " is <b>" + storeGSTno + "</b>");
                            Cls_Generic_Methods.clickElement(oPage_StoreSetUp.button_close);
                            break;
                        }
                    }
                }
                if (storeName.contains(sReceivingStore.split("-")[0])) {
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.list_btn_editStore.get(storeNo));
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_StoreSetUp.list_btn_editStoreBillingAddress, 10);

                    for (WebElement btn_Edit : oPage_StoreSetUp.list_btn_editStoreBillingAddress) {
                        boolean defaultAddress = false;

                        try {
                            Cls_Generic_Methods.isElementDisplayed(btn_Edit.findElement(By.xpath("./parent::td/following-sibling::td/a[text()='Mark Default']")));
                        } catch (NoSuchElementException e) {
                            defaultAddress = true;
                        }

                        if (defaultAddress) {
                            Cls_Generic_Methods.clickElement(driver, btn_Edit);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.input_gstNo, 10);
                            receivingStoreGSTno = Cls_Generic_Methods.getElementAttribute(oPage_StoreSetUp.input_gstNo, "value");
                            m_assert.assertInfo("GST no. present in " + storeName + " is <b>" + receivingStoreGSTno + "</b>");
                            Cls_Generic_Methods.clickElement(oPage_StoreSetUp.button_close);
                            break;
                        }
                    }
                }
                storeNo++;
            }

            if (receivingStoreGSTno != null && storeGSTno != null) {
                if (receivingStoreGSTno.substring(0, 2).equals(storeGSTno.substring(0, 2))) {
                    gstTypeIGST = false;
                    m_assert.assertInfo("GST type = CGST + SGST");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to get GST no. from Organisation Setting " + e);
        }
    }

    public String getItemShock(String sItemDescription) {

        String itemAvailableStock = "";

        Page_Master oPage_Master = new Page_Master(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);


        try {
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 10);
            Cls_Generic_Methods.clearValuesInElement(oPage_Master.input_itemNameSearchInItemMaster);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, sItemDescription);
            Cls_Generic_Methods.customWait(4);
            oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);

            for (WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory) {
                if (Cls_Generic_Methods.isElementDisplayed(itemData)) {

                    List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./child::*"));

                    String itemDescriptionName = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));
                    String itemStock = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((2)));


                    if (itemDescriptionName.contains(sItemDescription)) {
                        itemAvailableStock = itemStock;
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemData),
                                "<b> " + sItemDescription + " </b> is present in Item Master List");
                        Cls_Generic_Methods.customWait(5);
                        m_assert.assertInfo("Available stock of item : " + sItemDescription + " --> <b>" + itemStock + "</b>");
                        break;
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemAvailableStock;
    }

    public void refreshStore() {
        try {
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
