package tests.inventoryStores.pharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.templates.Page_InventorySearchCommonElements;
import pages.store.PharmacyStore.Order.Page_WorkOrder;
import pages.store.PharmacyStore.Transaction.Page_Transfer;
import pages.store.PharmacyStore.Transaction.Page_WorkOrderFulfilment;
import tests.inventoryStores.pharmacyStore.InventorySearchTest;
import tests.inventoryStores.pharmacyStore.Order.WorkOrderTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static pages.commonElements.CommonActions.selectOptionAndSearch;

public class WorkOrderFulfilmentTest extends WorkOrderTest {
    String wofNo;
    String wofCreatedAt;
    String billNumber,challanNumber;
    Page_WorkOrder oPage_WorkOrder;
    Page_CommonElements oPage_CommonElements;
    Page_WorkOrderFulfilment oPage_WorkOrderFulfilment;
    String wofCancelledAt;
    String oldBillNumber = "BILL-NO-103847",oldChallanNumber = "Ch_Deep_1234";


    @Test(description = "Validating Create Work Order Fulfilment")
    public void validateCreateWorkOrderFulfilment() {
        oPage_WorkOrder = new Page_WorkOrder(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_WorkOrderFulfilment = new Page_WorkOrderFulfilment(driver);

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            createWorkOrderWithMandatoryFields(true);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);

            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 10);

            boolean foundWorkOrder = selectWorkOrder("pending", workOrderNo);

            if (foundWorkOrder) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_approve, 15);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_approve), "Clicked <b>Approve</b> button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_yes, 6);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_WorkOrder.button_yes);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.text_rhs_workOrderStatus, 10);

                //WorkOrder fulfilment
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_newTransaction, 15), "<b>New Transaction</b> button is displayed");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_newTransaction), "Clicked <b>New Transaction</b> button ");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.text_headerCreteWorkOrderFulfilment, 15), "Validated --> Work Order Fulfilment Page Displayed");

                //Validate LHS
                validateWorkOrderFulfilment_LHS_TableValue(sItem1Description, item1Details);
                validateWorkOrderFulfilment_LHS_TableValue(sItem2Description, item2Details);

                //alert
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.button_saveChanges), "Clicked <b>Save Change</b> without entering mandatory fields");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrderFulfilment.label_billTypeErrorMsg), "Validated Alert msg is displayed for Bill");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrderFulfilment.label_otherChargesErrorMsg), "Validated Alert msg is displayed for Pending Other Charges");

                //Item info
                HashMap<String, String> itemInfo = new HashMap<>();
                int infoNo = 0;
                for (WebElement columnValue : oPage_WorkOrderFulfilment.list_lhsItemInfoHeader) {
                    String key = Cls_Generic_Methods.getTextInElement(columnValue);
                    String value = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.list_lhsItemInfoValue.get(infoNo));
                    itemInfo.put(key, value);
                    infoNo++;
                }

                m_assert.assertTrue(Double.parseDouble(itemInfo.get("Gross Amount")) == formatDecimalNumber(grossAmount), "Validated LHS Item Information --> Gross Amount = <b>" + itemInfo.get("Gross Amount") + "</b>");
                m_assert.assertTrue(Double.parseDouble(itemInfo.get("Total Discount")) == formatDecimalNumber(totalDiscount), "Validated LHS Item Information --> Total Discount = <b>" + itemInfo.get("Total Discount") + "</b>");
                m_assert.assertTrue(Double.parseDouble(itemInfo.get("GST5")) == formatDecimalNumber(totalTaxGST5), "Validated LHS Item Information --> Total Tax Amount = <b>" + itemInfo.get("GST5") + "</b>");
                m_assert.assertTrue(Double.parseDouble(itemInfo.get("GST12")) == formatDecimalNumber(totalTaxGST12), "Validated LHS Item Information --> Total Tax Amount = <b>" + itemInfo.get("GST12") + "</b>");

                m_assert.assertTrue(Double.parseDouble(itemInfo.get("Other Charges")) == formatDecimalNumber(expectedTotalOtherCharges), "Validated LHS Item Information --> Other Charges = <b>" + itemInfo.get("Other Charges") + "</b>");
                m_assert.assertTrue(Double.parseDouble(itemInfo.get("Net Amount")) == (formatDecimalNumber(itemTotalNetAmount) + expectedTotalOtherCharges), "Validated LHS Item Information --> Net Amount = <b>" + itemInfo.get("Net Amount") + "</b>");

                //Transaction Notes
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrderFulfilment.input_transactionNotes_creteWorkOrderFulfilment, sInstruction), "Entered <b>" + sInstruction + "</b> in Transaction Notes");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_WorkOrderFulfilment.input_vendor_creteWorkOrderFulfilment, "value").equals(sVendorName), "Validated --> Displayed vendor : <b>" + sVendorName + "</b>");

                //Date And Time
                Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.input_woDate_creteWorkOrderFulfilment);
                boolean datePickerFound = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.input_todayBillDate, 3);
                if (datePickerFound) {
                    Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.input_todayBillDate);
                    String date = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrderFulfilment.input_woDate_creteWorkOrderFulfilment, "value").trim();
                    m_assert.assertInfo("Selected Work Order Fulfilment Date as : <b>" + date + "</b>");
                } else {
                    String date = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrderFulfilment.input_woDate_creteWorkOrderFulfilment, "value").trim();
                    m_assert.assertFatal("Date Picker not displayed to select Order Date - Found Date = " + date);
                }
                m_assert.assertInfo(setTime(oPage_WorkOrderFulfilment.input_woTime_creteWorkOrderFulfilment, "10:00:PM"), "Selected Work Order Fulfilment Time as : <b>10:00:PM</b>");

                //Bill
                billNumber = "BILL-NO-" + getRandomNumber();
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_WorkOrderFulfilment.select_billType_creteWorkOrderFulfilment, "Bill"), "Selected <b>Bill</b> in Bill Type");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrderFulfilment.input_billNo_creteWorkOrderFulfilment, billNumber), "Entered <b>" + billNumber + "</b> in Bill No");

                Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.input_billDate_creteWorkOrderFulfilment);
                datePickerFound = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.input_todayBillDate, 3);
                if (datePickerFound) {
                    Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.input_todayBillDate);
                    String date = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrderFulfilment.input_billDate_creteWorkOrderFulfilment, "value").trim();
                    m_assert.assertInfo("Selected Work Order Fulfilment --> Bill Date as : <b>" + date + "</b>");
                } else {
                    String date = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrderFulfilment.input_billDate_creteWorkOrderFulfilment, "value").trim();
                    m_assert.assertFatal("Date Picker not displayed to select Order Date - Found Date = " + date);
                }


                validateWorkOrderFulfilment_TableValue(sItem1Description, item1Details);
                validateWorkOrderFulfilment_TableValue(sItem2Description, item2Details);

                //Pending Other Charges
                m_assert.assertTrue(Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.text_displayedPendingOtherCharges)) == expectedTotalOtherCharges, "Validated Displayed Pending Charges to be paid to vendor");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrderFulfilment.input_otherCharges_creteWorkOrderFulfilment, String.valueOf(expectedTotalOtherCharges)), "Entered <b>" + expectedTotalOtherCharges + "</b> in Pending amount");


                //Item info
                HashMap<String, String> totalValue = new HashMap<>();
                int col = 0;
                for (WebElement columnValue : oPage_WorkOrderFulfilment.list_totalItemInfoHeader) {
                    String key = Cls_Generic_Methods.getTextInElement(columnValue);
                    String value = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrderFulfilment.list_totalItemInfoValue.get(col), "value");
                    totalValue.put(key, value);
                    col++;
                }

                m_assert.assertTrue(Double.parseDouble(totalValue.get("Gross Amount")) == formatDecimalNumber(grossAmount), "Validated Work Order Fulfilment --> Gross Amount = <b>" + totalValue.get("Gross Amount") + "</b>");
                m_assert.assertTrue(Double.parseDouble(totalValue.get("Total Discount")) == formatDecimalNumber(totalDiscount), "Validated Work Order Fulfilment --> Total Discount = <b>" + totalValue.get("Total Discount") + "</b>");
                m_assert.assertTrue(Double.parseDouble(totalValue.get("GST5")) == formatDecimalNumber(totalTaxGST5), "Validated Work Order Fulfilment --> Total Tax Amount GST 5.0= <b>" + totalValue.get("GST5") + "</b>");
                m_assert.assertTrue(Double.parseDouble(totalValue.get("GST12")) == formatDecimalNumber(totalTaxGST12), "Validated Work Order Fulfilment --> Total Tax Amount GST 12.0= <b>" + totalValue.get("GST12") + "</b>");
                m_assert.assertTrue(Double.parseDouble(totalValue.get("Other Charges")) == formatDecimalNumber(expectedTotalOtherCharges), "Validated Work Order Fulfilment --> Other Charges = <b>" + totalValue.get("Other Charges") + "</b>");
                m_assert.assertTrue(Double.parseDouble(totalValue.get("Net Amount")) == (formatDecimalNumber(itemTotalNetAmount) + expectedTotalOtherCharges), "Validated Work Order Fulfilment --> Net Amount = <b>" + totalValue.get("Net Amount") + "</b>");

            }
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.button_saveChanges), "Clicked <b>Save Changes</b>");
            wofCreatedAt = getCurrentDateTime();
            System.out.println("CURRENT TIME -------------------->" + wofCreatedAt);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_refresh, 10);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);

            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Work Order Fulfilment");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_refresh, 10);

            selectWorkOrderFulfilment("open");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.text_rhs_wofNo, 10);
            wofNo = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.text_rhs_wofNo);
            m_assert.assertTrue("Work Order Fulfilment Created successfully --> Work Order Fulfilment No : <b>" + wofNo + "</b>");


            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFalse("Unable to validate Create Work Order Fulfilment --> " + e);
        }
    }


    @Test(description = "Validating Work Order Fulfilment RHS")
    public void validateWorkOrderFulfilmentRHS() {
        oPage_WorkOrderFulfilment = new Page_WorkOrderFulfilment(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Work Order Fulfilment");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_refresh, 10);
            boolean bSelectWOF = selectWorkOrderFulfilment("open", wofNo);

            if (bSelectWOF) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.text_rhs_wofNo, 10);
                String wofCreatedBy = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.text_rhs_wofCreatedByUser);
                String wofCreatedAtDateAndTime = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.text_rhs_wofCreatedAt);
                String wofStatus = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.text_rhs_wofStatus);
                String vendorLocation = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.text_rhs_wofVendorLocation);
                String vendorName = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.text_rhs_wofVendor);
                String vendorGst = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.text_rhs_wofVendorGST);

                m_assert.assertTrue(wofCreatedBy.equals(expectedLoggedInUser), "Validated RHS --> Work Order Fulfilment Created By User <b>: " + wofCreatedBy + "</b>");
                m_assert.assertTrue(wofCreatedAtDateAndTime.contains(wofCreatedAt.split(":")[0]), "Validated RHS --> Work Order Fulfilment Created At <b>: " + wofCreatedAt + "</b>");
                m_assert.assertTrue(wofStatus.equals("Open"), "Validated Work Order Fulfilment RHS -->  Status <b>: open </b>");
                m_assert.assertTrue(vendorLocation.contains(vendor_address), "Validated Work Order Fulfilment RHS --> Vendor Location <b>: " + vendorLocation + "</b>");
                m_assert.assertTrue(vendorName.contains(sVendorName), "Validated Work Order Fulfilment RHS --> Vendor <b>: " + vendorName + "</b>");
                m_assert.assertTrue(vendorGst.equals(vendorGstNo), "Validated Work Order Fulfilment RHS --> Vendor GST No <b>: " + vendorGst + "</b>");

                //Table Validation
                validateWorkOrderFulfilment_RHS_TableValue(sItem1Description, item1Details);
                validateWorkOrderFulfilment_RHS_TableValue(sItem2Description, item2Details);

                //Item info
                HashMap<String, String> itemInfo = new HashMap<>();
                int infoNo = 0;
                for (WebElement columnValue : oPage_WorkOrderFulfilment.list_rhsItemInfoHeader) {
                    String key = Cls_Generic_Methods.getTextInElement(columnValue);
                    String value = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.list_rhsItemInfoValue.get(infoNo));
                    itemInfo.put(key, value);
                    infoNo++;
                }

                System.out.println(itemInfo);
                m_assert.assertTrue(Double.parseDouble(itemInfo.get("Gross Amount")) == formatDecimalNumber(grossAmount), "Validated RHS Item Information --> Gross Amount = <b>" + itemInfo.get("Gross Amount") + "</b>");
                m_assert.assertTrue(Double.parseDouble(itemInfo.get("Total Discount")) == formatDecimalNumber(totalDiscount), "Validated RHS Item Information --> Total Discount = <b>" + itemInfo.get("Total Discount") + "</b>");
                m_assert.assertTrue(Double.parseDouble(itemInfo.get("GST5")) == formatDecimalNumber(totalTaxGST5), "Validated RHS Item Information --> Total Tax Amount GST5 = <b>" + itemInfo.get("GST5") + "</b>");
                m_assert.assertTrue(Double.parseDouble(itemInfo.get("GST12")) == formatDecimalNumber(totalTaxGST12), "Validated RHS Item Information --> Total Tax Amount GST12= <b>" + itemInfo.get("GST12") + "</b>");
                m_assert.assertTrue(Double.parseDouble(itemInfo.get("Other Charges")) == formatDecimalNumber(expectedTotalOtherCharges), "Validated RHS Item Information --> Other Charges = <b>" + itemInfo.get("Other Charges") + "</b>");
                m_assert.assertTrue(Double.parseDouble(itemInfo.get("Net Amount")) == (formatDecimalNumber(itemTotalNetAmount) + expectedTotalOtherCharges), "Validated RHS Item Information --> Net Amount = <b>" + itemInfo.get("Net Amount") + "</b>");
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Work Order Fulfilment Rhs " + e);
        }
    }

    @Test(description = "Validating Edit Work Order Fulfilment")
    public void validateEditWorkOrderFulfilment() {
        oPage_WorkOrderFulfilment = new Page_WorkOrderFulfilment(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Work Order Fulfilment");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_refresh, 10);
            boolean bSelectWOF = selectWorkOrderFulfilment("open", wofNo);

            if (bSelectWOF) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_edit, 15);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.button_edit), "Clicked <b>Edit</b> button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.text_headerCreteWorkOrderFulfilment, 15);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrderFulfilment.input_updateReason, "TO"), "Entered lesser than minimum character in update reason");
                Cls_Generic_Methods.customWait();
                m_assert.assertFalse(Cls_Generic_Methods.isElementEnabled(oPage_WorkOrderFulfilment.button_saveChanges), "Validated Update Reason --> <b>Save Changes</b> button is disabled");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrderFulfilment.input_updateReason, "TEST"), "Entered <b>TEST</B> in update reason");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_WorkOrderFulfilment.button_saveChanges), "Validated Update Reason --> <b>Save Changes</b> button is enabled");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.button_saveChanges), "Clicked <b>Save Changes</b> button");
                wofCreatedAt = getCurrentDateTime();
                Cls_Generic_Methods.customWait(5);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_refresh, 15);
                selectWorkOrderFulfilment("open", wofNo);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.text_rhs_wofCreatedAt, 10);
                String createdAt = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.text_rhs_wofCreatedAt);
                m_assert.assertInfo(createdAt.contains(wofCreatedAt.split(":")[0]), "Validated Edit --> WOF No - " + wofNo + " -->Updated At - " + createdAt);

            } else {
                m_assert.assertFatal("Unable to select work order fulfilment");
            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate Edit functionality " + e);
            e.printStackTrace();
        }
    }

    @Test(description = "Validating Approve Work Order Fulfilment")
    public void validateApproveWorkOrderFulfilment() {
        oPage_WorkOrderFulfilment = new Page_WorkOrderFulfilment(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Work Order Fulfilment");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_refresh, 10);
            boolean bSelectWOF = selectWorkOrderFulfilment("open", wofNo);

            if (bSelectWOF) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_approve, 15);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.button_approve), "Clicked <b>Approve</b> button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_yes, 6);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_WorkOrderFulfilment.button_yes);

                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.label_approved, 10), "Validated -->Notify message --> Work Order Fulfilment is approved ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.text_rhs_wofStatus, 10);
                String status = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.text_rhs_wofStatus);
                m_assert.assertTrue(status.equals("Approved"), "Validated RHS Work Order Status : <b>" + status + "</b>");


                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_print, 10), "Validated Approve --> Print option is displayed");
                m_assert.assertFalse(Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrderFulfilment.button_edit), "Validated Approve --> Edit option is removed");
                m_assert.assertFalse(Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrderFulfilment.button_cancel), "Validated Approve --> Cancel option is removed");

                int preWindowsCount = driver.getWindowHandles().size();
                String initialWindowHandle = driver.getWindowHandle();
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.button_print), "Clicked Print button");
                Cls_Generic_Methods.customWait(8);
                int postWindowsCount = driver.getWindowHandles().size();

                m_assert.assertTrue(postWindowsCount > preWindowsCount, "Validated Print --> Work Order Fulfilment Print page opened");

                for (String currentWindowHandle : driver.getWindowHandles()) {
                    if (!currentWindowHandle.equals(driver.getWindowHandle())) {
                        driver.switchTo().window(currentWindowHandle);
                    }
                }
                driver.close();
                driver.switchTo().window(initialWindowHandle);
                Cls_Generic_Methods.customWait();

            } else {
                m_assert.assertFatal("Unable to select work order Fulfilment");
            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate approve Work Order Fulfilment " + e);
        }
    }

    @Test(description = "Validating Cancel Work Order Fulfilment")
    public void validateCancelWorkOrderFulfilment() {
        oPage_WorkOrderFulfilment = new Page_WorkOrderFulfilment(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Work Order Fulfilment");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_refresh, 10);
            boolean bSelectWOF = selectWorkOrderFulfilment("open", wofNo);

            if (!bSelectWOF) {
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.customWait();
                createWorkOrderFulfilmentWithMandatoryFields();
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                CommonActions.selectStoreOnApp(store);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Work Order Fulfilment");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_refresh, 10);
                bSelectWOF = selectWorkOrderFulfilment("open", wofNo);
            }

            if (bSelectWOF) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_cancel, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_WorkOrderFulfilment.button_cancel), "Clicked Cancel Work Order--> Work Order Fulfilment No=" + wofNo);
                String cancellationReason = "AUTO TESTING CANCELLATION";
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.input_cancellationReason, 10);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrderFulfilment.input_cancellationReason, cancellationReason);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.button_dontCancel), "Clicked Don't Cancel button");
                Cls_Generic_Methods.clickElement(driver, oPage_WorkOrderFulfilment.button_cancel);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.input_cancellationReason, 10);
                Cls_Generic_Methods.clearValuesInElement(oPage_WorkOrderFulfilment.input_cancellationReason);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrderFulfilment.input_cancellationReason, cancellationReason), "Entered <b>" + cancellationReason + "</b> in cancellation reason");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.button_confirmCancel), "Clicked <b>Cancel Work Order Fulfilment </b> button");
                wofCancelledAt = getCurrentDateTime();
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.label_cancelled, 10), "Validated -->Notify message --> Work Order Fulfilment is cancelled ");
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Work Order Fulfilment");

                boolean rowSelected = selectWorkOrderFulfilment("cancelled", wofNo);
                Cls_Generic_Methods.customWait(4);
                if (rowSelected) {
                    String rhsStatus = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.text_rhs_wofStatus);
                    m_assert.assertTrue(rhsStatus.equalsIgnoreCase("cancelled"), "Validated Cancel --> Status = <b>" + rhsStatus + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.text_rhs_wofCancelledByUser).equals(expectedLoggedInUser), "Validated RHS -->Cancelled By User  = <b>" + expectedLoggedInUser + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.text_rhs_wofCancelledAt).contains(wofCancelledAt.split(":")[0]), "Validated RHS -->Cancelled At = <b>" + wofCancelledAt + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.text_rhs_wofCancelledReason).equalsIgnoreCase(cancellationReason), "Validated RHS -->Cancelled Reason = <b>" + cancellationReason + "</b>");
                    Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                } else {
                    m_assert.assertFatal("Unable To validate RHS --> WO not Selected");
                }

            } else {
                m_assert.assertFatal("Unable to select work order fulfillment");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Search Functionality In Work Order Fulfillment")
    public void validateSearchFunctionalityInWorkOrderFulfilment(){

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
        InventorySearchTest oInventorySearchTest = new InventorySearchTest();

        String woFulfillmentSearchTypeList [] = {"WO Fulfilment No.","Work Order No.","Item Description","Bill No","Challan No"};
        String oldWOFNo = "";

        try{

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait();
            createWorkOrderFulfilmentWithChallan();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Work Order Fulfilment");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type  Selection Dropdown Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.input_searchBoxInput),
                        " Input Search Box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(woFulfillmentSearchTypeList[0]),
                        " By Default Search Type Selection Dropdown Displayed correctly as :" + woFulfillmentSearchTypeList[0]);
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput, "value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty by default for selected search type Displayed correctly");
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Not Displayed Correctly as Default");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput, "placeholder")
                                .contains("Search By " + woFulfillmentSearchTypeList[0]),
                        " Input Search Box Place holder for selected search type Displayed correctly");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_filterDropdownButton),
                        " Filter Dropdown Button Clicked");
                Cls_Generic_Methods.customWait(2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.text_thisYearFilter),
                        " This Year Selected as Filter");
                Cls_Generic_Methods.customWait(2);

                for (WebElement row : oPage_WorkOrderFulfilment.list_wofCreatedList) {
                    if (Cls_Generic_Methods.isElementDisplayed(row)) {
                        List<WebElement> workOrderRow = row.findElements(By.xpath("./child::*"));
                        String wofNoInRow = Cls_Generic_Methods.getTextInElement(workOrderRow.get(0)).split("\n")[1];

                        if (!wofNoInRow.endsWith(wofNo)) {
                            oldWOFNo = wofNoInRow;
                           break;
                        }
                    }
                }


                String searchValueList[] = {wofNo,oldWOFNo,workOrderNo,oldWorkOrderNo,sItem1Description,oldItem,billNumber,oldBillNumber,challanNumber,oldChallanNumber};

                for(int i = 0;i<2;i++) {
                    boolean searchResultByTransferNumber = CommonActions.selectOptionAndSearch(woFulfillmentSearchTypeList[0], searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByTransferNumber) {
                        boolean  bRequisitionOrderFound = oInventorySearchTest.getSearchedRecord(oPage_WorkOrderFulfilment.list_wofCreatedList, searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound,
                                "Search By WO Fulfilment No Worked correctly as order found in the Transfer page for number: " + searchValueList[i]);
                    }
                }


                for(int i = 2;i<4;i++) {
                    boolean searchResultByWONumber = CommonActions.selectOptionAndSearch(woFulfillmentSearchTypeList[1],searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if(searchResultByWONumber) {
                        boolean bRequisitionOrderFound = oPage_WorkOrderFulfilment.list_wofCreatedList.size() == 1;
                        m_assert.assertTrue(bRequisitionOrderFound,
                                "Search By Work Order Number Worked correctly as order found in the  page");
                    }

                }

                for(int i = 4;i<6;i++) {
                    boolean searchResultByDescription = CommonActions.selectOptionAndSearch(woFulfillmentSearchTypeList[2],searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if(searchResultByDescription) {
                        boolean bRequisitionOrderFound = oPage_WorkOrderFulfilment.list_wofCreatedList.size() == 1;
                        m_assert.assertTrue(bRequisitionOrderFound,
                                "Search By Item Description Worked correctly as order found in the  page");
                    }

                }
                for(int i = 6;i<8;i++) {
                    boolean searchResultByBillNumber = CommonActions.selectOptionAndSearch(woFulfillmentSearchTypeList[3],searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if(searchResultByBillNumber) {
                        boolean bRequisitionOrderFound = oPage_WorkOrderFulfilment.list_wofCreatedList.size() == 1;
                        m_assert.assertTrue(bRequisitionOrderFound,
                                "Search By Bill Number Worked correctly as order found in the  page");
                    }

                }
                for(int i = 8;i<10;i++) {
                    boolean searchResultByChallanNumber = CommonActions.selectOptionAndSearch(woFulfillmentSearchTypeList[5],searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if(searchResultByChallanNumber) {
                        boolean bRequisitionOrderFound = oPage_WorkOrderFulfilment.list_wofCreatedList.size() == 1;
                        m_assert.assertTrue(bRequisitionOrderFound,
                                "Search By Challan Number Worked correctly as order found in the  page");
                    }

                }

                for(int i =0 ;i<5;i++) {

                    boolean searchResultByWrongNumber = selectOptionAndSearch(woFulfillmentSearchTypeList[i],"incorrectReqNumber");
                    m_assert.assertFalse(searchResultByWrongNumber," WOrk Order Fullfilment Search With Incorrect Number Working Correct");
                    m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                    .equalsIgnoreCase("incorrectReqNumber"),
                            " Input Search Box is not empty by for selected search type Displayed correctly");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                            " Nothing To Display Text Displayed as Result");
                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Displayed and clicked");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty as clear button is working correctly");
                boolean selectOption = CommonActions.selectOption(woFulfillmentSearchTypeList[0]);
                m_assert.assertTrue(selectOption," Able to selected Search type Again to Default");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(woFulfillmentSearchTypeList[0]),
                        " Search Type Selected  as : "+woFulfillmentSearchTypeList[0]);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type Dropdown Clicked");
                Cls_Generic_Methods.customWait();
                for(WebElement type : oPage_InventorySearchCommonElements.list_searchTypeList){
                    String typeText = Cls_Generic_Methods.getTextInElement(type);
                    int index = oPage_InventorySearchCommonElements.list_searchTypeList.indexOf(type);
                    if(typeText.equalsIgnoreCase(woFulfillmentSearchTypeList[index])){
                        m_assert.assertTrue( typeText+" Search Type Present In Dropdown List");
                        Cls_Generic_Methods.customWait();
                    }else{
                        m_assert.assertFalse( typeText+" Search Type Not Present In Dropdown List");

                    }
                }

                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
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


    private void validateWorkOrderFulfilment_LHS_TableValue(String itemDescription, HashMap<String, String> itemDetails) {
        try {
            ArrayList<String> headerValue = new ArrayList<>();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.text_headerCreteWorkOrderFulfilment, 10);
            for (WebElement tableColumn : oPage_WorkOrderFulfilment.list_lhsTableHeader) {
                headerValue.add(Cls_Generic_Methods.getTextInElement(tableColumn));
            }

            for (WebElement tableRow : oPage_WorkOrderFulfilment.list_lhsTableRow) {
                String description = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Description")));
                String instruction = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Instruction")));
                String rate = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Rate")));
                String quantity = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Qty.")));
                String discount = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Discount")));
                String netAmount = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Net Amount")));

                if (description.contains(itemDescription)) {
                    m_assert.assertTrue("Validate Work Order RHS Table --> Item Description : <b>" + description + "</b>");
                    m_assert.assertTrue(instruction.equals(sInstruction), "Validate Work Order Fulfilment LHS Table --> Instruction : <b>" + instruction + "</b>");
                    m_assert.assertTrue(Double.parseDouble(rate) == formatDecimalNumber(Double.parseDouble(itemDetails.get("RATE"))), "Validate Work Order Fulfilment LHS Table --> Rate : <b>" + rate + "</b>");
                    m_assert.assertTrue(Double.parseDouble(quantity) == formatDecimalNumber(Double.parseDouble(itemDetails.get("QUANTITY"))), "Validate Work Order Fulfilment LHS Table --> Quantity : <b>" + quantity + "</b>");
                    m_assert.assertTrue(Double.parseDouble(discount) == formatDecimalNumber(Double.parseDouble(itemDetails.get("DISCOUNT"))), "Validate Work Order Fulfilment LHS Table --> Discount : <b>" + discount + "</b>");
                    m_assert.assertTrue(Double.parseDouble(netAmount) == formatDecimalNumber(Double.parseDouble(itemDetails.get("NET AMOUNT"))), "Validate Work Order Fulfilment LHS Table --> Net Amount : <b>" + netAmount + "</b>");
                    break;
                }
            }

        } catch (Exception e) {
            m_assert.assertFatal("Unable To validate Work Order Fulfilment LHS table -->" + e);
        }
    }

    private void validateWorkOrderFulfilment_RHS_TableValue(String itemDescription, HashMap<String, String> itemDetails) {
        try {
            ArrayList<String> headerValue = new ArrayList<>();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.text_rhs_wofNo, 10);
            for (WebElement tableColumn : oPage_WorkOrderFulfilment.list_rhsTableHeader) {
                headerValue.add(Cls_Generic_Methods.getTextInElement(tableColumn));
            }

            for (WebElement tableRow : oPage_WorkOrderFulfilment.list_rhsTableRow) {
                String description = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Description")));
                String instruction = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Instruction")));
                String rate = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Rate")));
                String quantity = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Qty")));
                String discount = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Discount")));
                String netAmount = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Net Amount")));
                String remark = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Remarks")));

                if (description.contains(itemDescription)) {
                    m_assert.assertTrue("Validate Work Order Fulfilment RHS Table --> Item Description : <b>" + description + "</b>");
                    m_assert.assertTrue(instruction.equals(sInstruction), "Validate Work Order Fulfilment RHS Table --> Instruction : <b>" + instruction + "</b>");
                    m_assert.assertTrue(Double.parseDouble(rate) == formatDecimalNumber(Double.parseDouble(itemDetails.get("RATE"))), "Validate Work Order Fulfilment RHS Table --> Rate : <b>" + rate + "</b>");
                    m_assert.assertTrue(Double.parseDouble(quantity) == formatDecimalNumber(Double.parseDouble(itemDetails.get("QUANTITY"))), "Validate Work Order Fulfilment RHS Table --> Quantity : <b>" + quantity + "</b>");
                    m_assert.assertTrue(Double.parseDouble(discount) == formatDecimalNumber(Double.parseDouble(itemDetails.get("DISCOUNT"))), "Validate Work Order Fulfilment RHS Table --> Discount : <b>" + discount + "</b>");
                    m_assert.assertTrue(Double.parseDouble(netAmount) == formatDecimalNumber(Double.parseDouble(itemDetails.get("NET AMOUNT"))), "Validate Work Order Fulfilment RHS Table --> Net Amount : <b>" + netAmount + "</b>");
                    m_assert.assertTrue(remark.equals("TEST"), "Validate Work Order Fulfilment RHS Table --> REMARK : <b>TEST</b>");
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable To validate Work Order Fulfilment table -->" + e);
        }
    }

    private void validateWorkOrderFulfilment_TableValue(String itemDescription, HashMap<String, String> itemDetails) {
        try {


            for (int rowNo = 0; rowNo < oPage_WorkOrderFulfilment.list_lhsTableRow.size(); rowNo++) {

                String description = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.list_textItemDescription_createWOF.get(rowNo));

                if (description.contains(itemDescription)) {
                    String instruction = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.list_textInstructions_createWOF.get(rowNo));
                    String rate = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.list_textRate_createWOF.get(rowNo));
                    String woQuantity = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.list_textWoQuantity_createWOF.get(rowNo));
                    String pendingQuantity = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.list_textPendingQty_createWOF.get(rowNo));
                    String quantity = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrderFulfilment.list_inputQuantity_createWOF.get(rowNo), "value");
                    String discount = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.list_textDiscount_createWOF.get(rowNo));
                    String netAmount = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.list_textNetAmount_createWOF.get(rowNo));

                    m_assert.assertTrue("Validate Work Order Fulfilment RHS Table --> Item Description : <b>" + description + "</b>");
                    m_assert.assertTrue(instruction.equals(sInstruction), "Validate Work Order Fulfilment RHS Table --> Instruction : <b>" + instruction + "</b>");
                    m_assert.assertTrue(Double.parseDouble(rate) == formatDecimalNumber(Double.parseDouble(itemDetails.get("RATE"))), "Validate Work Order Fulfilment RHS Table --> Rate : <b>" + rate + "</b>");
                    m_assert.assertTrue(Double.parseDouble(woQuantity) == formatDecimalNumber(Double.parseDouble(itemDetails.get("QUANTITY"))), "Validate Work Order Fulfilment RHS Table --> WO Quantity : <b>" + woQuantity + "</b>");
                    m_assert.assertTrue(Double.parseDouble(pendingQuantity) == formatDecimalNumber(Double.parseDouble(itemDetails.get("QUANTITY"))), "Validate Work Order Fulfilment RHS Table --> Pending Quantity : <b>" + pendingQuantity + "</b>");
                    m_assert.assertTrue(Double.parseDouble(quantity) == formatDecimalNumber(Double.parseDouble(itemDetails.get("QUANTITY"))), "Validate Work Order Fulfilment RHS Table --> Quantity : <b>" + quantity + "</b>");
                    m_assert.assertTrue(Double.parseDouble(discount) == formatDecimalNumber(Double.parseDouble(itemDetails.get("DISCOUNT"))), "Validate Work Order Fulfilment RHS Table --> Discount : <b>" + discount + "</b>");
                    m_assert.assertTrue(Double.parseDouble(netAmount) == formatDecimalNumber(Double.parseDouble(itemDetails.get("NET AMOUNT"))), "Validate Work Order Fulfilment RHS Table --> Net Amount : <b>" + netAmount + "</b>");

                    Cls_Generic_Methods.clearValuesInElement(oPage_WorkOrderFulfilment.list_inputQuantity_createWOF.get(rowNo));
                    Cls_Generic_Methods.customWait();
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrderFulfilment.list_inputQuantity_createWOF.get(rowNo), "1000"), "Entered value higher than wo quantity in Quantity");
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.input_otherCharges_creteWorkOrderFulfilment);
                    Cls_Generic_Methods.customWait();
                    String label = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.label_quantityErrorMsg.get(rowNo));
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrderFulfilment.label_quantityErrorMsg.get(rowNo)), "Validated Error message is displayed : --> " + label);
                    Cls_Generic_Methods.clearValuesInElement(oPage_WorkOrderFulfilment.list_inputQuantity_createWOF.get(rowNo));
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrderFulfilment.list_inputQuantity_createWOF.get(rowNo), woQuantity);

                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrderFulfilment.list_inputRemark_createWOF.get(rowNo), "TEST"), "Entered <b>TEST</b> in remark");

                    break;
                }


            }

        } catch (Exception e) {
            m_assert.assertFatal("Unable To validate Work Order Fulfilment  table -->" + e);
        }
    }

    private String getRandomNumber() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    public boolean selectWorkOrderFulfilment(String status, String... workOrderFulfilmentNo) {
        boolean flag = false;
        oPage_WorkOrderFulfilment = new Page_WorkOrderFulfilment(driver);
        String selectWORow = null;

        try {
            if (workOrderFulfilmentNo.length > 0) {
                selectWORow = workOrderFulfilmentNo[0];
            }
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_WorkOrderFulfilment.list_wofHeaderList, 10);
            List<String> workOrderHeaderList = new ArrayList<>();

            for (WebElement headerList : oPage_WorkOrderFulfilment.list_wofHeaderList) {
                workOrderHeaderList.add(Cls_Generic_Methods.getTextInElement(headerList));
            }


            for (WebElement row : oPage_WorkOrderFulfilment.list_wofCreatedList) {
                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> workOrderRow = row.findElements(By.xpath("./child::*"));
                    String woStatus = Cls_Generic_Methods.getTextInElement(workOrderRow.get(workOrderHeaderList.indexOf("Status")));
                    String wofNoInRow = Cls_Generic_Methods.getTextInElement(workOrderRow.get(workOrderHeaderList.indexOf("Date | Time")));
                    System.out.println(wofNoInRow + "----------------------<<<<<<<<<<<<<<<<");

                    if (workOrderFulfilmentNo.length == 0) {
                        if (woStatus.equalsIgnoreCase(status)) {
                            Cls_Generic_Methods.clickElement(row);
                            flag = true;
                            break;
                        }

                    } else {
                        if (wofNoInRow.contains(selectWORow) && woStatus.equalsIgnoreCase(status)) {
                            Cls_Generic_Methods.clickElement(row);
                            flag = true;
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to find Work Order Fulfilment " + e);
        }

        return flag;

    }

    public void createWorkOrderFulfilmentWithMandatoryFields() {
        oPage_WorkOrder = new Page_WorkOrder(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_WorkOrderFulfilment = new Page_WorkOrderFulfilment(driver);

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            createWorkOrderWithMandatoryFields(false);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);

            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 10);

            boolean foundWorkOrder = selectWorkOrder("pending", workOrderNo);

            if (foundWorkOrder) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_approve, 15);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_approve), "Clicked <b>Approve</b> button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_yes, 6);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_WorkOrder.button_yes);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.text_rhs_workOrderStatus, 10);

                //WorkOrder fulfilment
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_newTransaction, 15), "<b>New Transaction</b> button is displayed");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_newTransaction), "Clicked <b>New Transaction</b> button ");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.text_headerCreteWorkOrderFulfilment, 15), "Validated --> Work Order Fulfilment Page Displayed");


                //Transaction Notes
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrderFulfilment.input_transactionNotes_creteWorkOrderFulfilment, sInstruction), "Entered <b>" + sInstruction + "</b> in Transaction Notes");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_WorkOrderFulfilment.input_vendor_creteWorkOrderFulfilment, "value").equals(sVendorName), "Validated --> Displayed vendor : <b>" + sVendorName + "</b>");

                //Date And Time

                //Bill
                billNumber = "BILL-NO-" + getRandomNumber();
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_WorkOrderFulfilment.select_billType_creteWorkOrderFulfilment, "Bill"), "Selected <b>Bill</b> in Bill Type");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrderFulfilment.input_billNo_creteWorkOrderFulfilment, billNumber), "Entered <b>" + billNumber + "</b> in Bill No");

                Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.input_billDate_creteWorkOrderFulfilment);
                boolean datePickerFound = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.input_todayBillDate, 3);
                if (datePickerFound) {
                    Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.input_todayBillDate);
                    String date = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrderFulfilment.input_billDate_creteWorkOrderFulfilment, "value").trim();
                    m_assert.assertInfo("Selected Work Order Fulfilment --> Bill Date as : <b>" + date + "</b>");
                } else {
                    String date = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrderFulfilment.input_billDate_creteWorkOrderFulfilment, "value").trim();
                    m_assert.assertFatal("Date Picker not displayed to select Order Date - Found Date = " + date);
                }


                validateWorkOrderFulfilment_TableValue(sItem1Description, item1Details);
                validateWorkOrderFulfilment_TableValue(sItem2Description, item2Details);

                //Pending Other Charges
                m_assert.assertTrue(Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.text_displayedPendingOtherCharges)) == expectedTotalOtherCharges, "Validated Displayed Pending Charges to be paid to vendor");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrderFulfilment.input_otherCharges_creteWorkOrderFulfilment, String.valueOf(expectedTotalOtherCharges)), "Entered <b>" + expectedTotalOtherCharges + "</b> in Pending amount");

            }
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.button_saveChanges), "Clicked <b>Save Changes</b>");
            wofCreatedAt = getCurrentDateTime();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_refresh, 10);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);

            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Work Order Fulfilment");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_refresh, 10);

            selectWorkOrderFulfilment("open");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.text_rhs_wofNo, 10);
            wofNo = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.text_rhs_wofNo);
            m_assert.assertTrue("Work Order Fulfilment Created successfully --> Work Order Fulfilment No : <b>" + wofNo + "</b>");

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFalse("Unable to validate Create Work Order Fulfilment --> " + e);
        }
    }
    public void createWorkOrderFulfilmentWithChallan() {
        oPage_WorkOrder = new Page_WorkOrder(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_WorkOrderFulfilment = new Page_WorkOrderFulfilment(driver);

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            createWorkOrderWithMandatoryFields(false);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);

            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 10);

            for (WebElement row : oPage_WorkOrder.list_woCreatedList) {
                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    String workOrderNoInRow = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.list_workOrderNoList.get(1));
                    int index = oPage_WorkOrder.list_woCreatedList.indexOf(row);
                    if (index>2) {
                        Cls_Generic_Methods.clickElement(row);
                        Cls_Generic_Methods.customWait(5);
                        oldWorkOrderNo = workOrderNoInRow;
                        oldItem = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.list_rhsTableRow.get(0).findElement(By.xpath(".//td[2]")));
                        break;
                    }
                }
            }

            boolean foundWorkOrder = selectWorkOrder("pending", workOrderNo);

            if (foundWorkOrder) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_approve, 15);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_approve), "Clicked <b>Approve</b> button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_yes, 6);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_WorkOrder.button_yes);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.text_rhs_workOrderStatus, 10);

                //WorkOrder fulfilment
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_newTransaction, 15), "<b>New Transaction</b> button is displayed");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_newTransaction), "Clicked <b>New Transaction</b> button ");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.text_headerCreteWorkOrderFulfilment, 15), "Validated --> Work Order Fulfilment Page Displayed");


                //Transaction Notes
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrderFulfilment.input_transactionNotes_creteWorkOrderFulfilment, sInstruction), "Entered <b>" + sInstruction + "</b> in Transaction Notes");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_WorkOrderFulfilment.input_vendor_creteWorkOrderFulfilment, "value").equals(sVendorName), "Validated --> Displayed vendor : <b>" + sVendorName + "</b>");

                //Date And Time

                //Bill
                challanNumber = "Challan-NO-" + getRandomNumber();
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_WorkOrderFulfilment.select_billType_creteWorkOrderFulfilment, "Challan"), "Selected <b>Challan</b> in Bill Type");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrderFulfilment.input_challanNo_creteWorkOrderFulfilment, challanNumber), "Entered <b>" + challanNumber + "</b> in Challan No");

                Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.input_challanDate_creteWorkOrderFulfilment);
                boolean datePickerFound = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.input_todayBillDate, 3);
                if (datePickerFound) {
                    Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.input_todayBillDate);
                    String date = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrderFulfilment.input_billDate_creteWorkOrderFulfilment, "value").trim();
                    m_assert.assertInfo("Selected Work Order Fulfilment --> Bill Date as : <b>" + date + "</b>");
                } else {
                    String date = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrderFulfilment.input_billDate_creteWorkOrderFulfilment, "value").trim();
                    m_assert.assertFatal("Date Picker not displayed to select Order Date - Found Date = " + date);
                }


                validateWorkOrderFulfilment_TableValue(sItem1Description, item1Details);
                validateWorkOrderFulfilment_TableValue(sItem2Description, item2Details);

                //Pending Other Charges
                m_assert.assertTrue(Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.text_displayedPendingOtherCharges)) == expectedTotalOtherCharges, "Validated Displayed Pending Charges to be paid to vendor");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrderFulfilment.input_otherCharges_creteWorkOrderFulfilment, String.valueOf(expectedTotalOtherCharges)), "Entered <b>" + expectedTotalOtherCharges + "</b> in Pending amount");

            }
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrderFulfilment.button_saveChanges), "Clicked <b>Save Changes</b>");
            wofCreatedAt = getCurrentDateTime();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_refresh, 10);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);

            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Work Order Fulfilment");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.button_refresh, 10);

            selectWorkOrderFulfilment("open");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrderFulfilment.text_rhs_wofNo, 10);
            wofNo = Cls_Generic_Methods.getTextInElement(oPage_WorkOrderFulfilment.text_rhs_wofNo);
            m_assert.assertTrue("Work Order Fulfilment Created successfully --> Work Order Fulfilment No : <b>" + wofNo + "</b>");

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFalse("Unable to validate Create Work Order Fulfilment --> " + e);
        }
    }


}
