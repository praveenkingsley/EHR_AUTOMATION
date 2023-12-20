package tests.inventoryStores.opticalStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.templates.Page_InventorySearchCommonElements;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_VendorMaster;
import pages.store.PharmacyStore.Order.Page_Indent;
import pages.store.PharmacyStore.Order.Page_PurchaseOrder;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_PurchaseBill;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PurchaseBillTest extends TestBase {
    String purchaseBill_no;
    String vendorName = "Supplier ABC";
    String store = "Pharmacy automation- Pharmacy";
    String storeGSTno;
    String vendor_address;
    String vendorGSTno;
    String netAmountInGrn;
    String grnApprovedBy;
    String grnApprovedAt;
    String poCreatedBy;
    String poCreatedAt;
    String grnCreatedAt;
    boolean gstTypeIGST;
    String pbCreatedAt;

    //GRN
    String purchaseItem = "Horlicks";
    String subStore = "Default";
    String batchNo;
    String unitCostWOTax = "100";
    String discountAmount = "10";
    String sTotalAmountBeforeTax;
    String freeQuantity = "1";
    String packageQuantity = "2";
    final String sellingPrice = "120";
    String otherCharges = "50";
    String transactionNotes = "Transaction_notes";
    String taxPercentage;
    String billNumber;
    String challanNumber;
    String dispensingUnit;
    String grn_no;
    String purchaseOrder_no;
    Page_CommonElements oPage_CommonElements;
    Page_StoreSetUp oPage_StoreSetUp;
    Page_Purchase oPage_Purchase;
    Page_VendorMaster oPage_VendorMaster;
    Page_PurchaseBill oPage_PurchaseBill;
    Page_PurchaseOrder oPage_PurchaseOrder;
    boolean uomValidation = true;
    String expectedLoggedInUser;
    String billNumberSearch = "",challanNumberSearch,grnNumber;
    @Test(enabled = true, description = "Validating Create Purchase Bill")
    public void validateCreatePurchaseBillWithGrn() {
        expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_PurchaseBill = new Page_PurchaseBill(driver);
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            getGST_valueFromSetting();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            boolean purchaseGrnCreated = createPurchaseGrn("Bill");
            billNumberSearch = billNumber;
            grnNumber = grn_no;
            m_assert.assertInfo("Search Bill check"+ billNumberSearch);

            if (purchaseGrnCreated) {
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                validateAndCreatePurchaseBill();
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

            } else {
                m_assert.assertFatal("Purchase Grn not Created");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(enabled = true, description = "Validating Create Purchase Bill with Purchase Order")
    public void validateCreatePurchaseBillWithPO() {
        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_PurchaseBill = new Page_PurchaseBill(driver);
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            getGST_valueFromSetting();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            boolean purchaseOrderCreated = createPurchaseOrder("Bill");

            if (purchaseOrderCreated) {
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                validateAndCreatePurchaseBill();
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            } else {
                m_assert.assertFatal("Purchase Order not Created");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validating Edit Purchase Bill")
    public void validateEditPurchaseBill() {
        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_PurchaseBill = new Page_PurchaseBill(driver);
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

            boolean createdGRN = createPurchaseGrn("Challan");
            challanNumberSearch = challanNumber;
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.button_purchaseNew, 10);

            List<String> purchaseBillHeaderList = new ArrayList<String>();

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PurchaseBill.list_purchaseBillHeaderList, 10);
            for (WebElement purchaseHeaderList : oPage_PurchaseBill.list_purchaseBillHeaderList) {
                purchaseBillHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
            }
            int rowNo = 0;
            for (WebElement row : oPage_PurchaseBill.list_purchaseBillRowList) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    String sPB_number = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_purchaseBillRowPBnoList.get(rowNo));
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));
                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseBillHeaderList.indexOf("Status")));
                    if (sPB_number.equalsIgnoreCase(purchaseBill_no)) {
                        if (purchaseStatus.equalsIgnoreCase("open")) {
                            Cls_Generic_Methods.clickElement(row);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.button_edit, 10);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PurchaseBill.button_edit), "Clicked Edit Purchase Bill");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.input_transactionNotePurchaseBill, 10);
                            Cls_Generic_Methods.clickElement(oPage_PurchaseBill.button_removeFromList);
                            Cls_Generic_Methods.customWait();
                            m_assert.assertFalse(oPage_PurchaseBill.list_bodyPurchaseBillTable.size() > 0, "Validated Edit -->Removed item from list");

                            for (WebElement rowCreatedPB : oPage_PurchaseBill.list_textGrnBillNoPurchaseBillTable) {
                                Cls_Generic_Methods.clickElement(rowCreatedPB);
                                break;
                            }
                            m_assert.assertTrue(Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PurchaseBill.list_bodyPurchaseBillTable, 10), "Validated Edit -->Added New Transaction to the list");
                            m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_PurchaseBill.input_purchaseBillDate), "Validated Edit -->Purchase Bill date is enabled");
                            m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_PurchaseBill.input_purchaseBillTime), "Validated Edit -->Purchase Bill time is enabled");
                            m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_PurchaseBill.input_invoiceNoPurchaseBill), "Validated Edit -->Invoice number is enabled");
                            m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_PurchaseBill.input_invoiceDatePurchaseBill), "Validated Edit -->Invoice Date is enabled");
                            m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_PurchaseBill.input_transactionNotePurchaseBill), "Validated Edit -->Transaction Note is enabled");

                            if (createdGRN) {
                                Cls_Generic_Methods.clickElement(oPage_PurchaseBill.button_removeFromList);
                                Cls_Generic_Methods.customWait();
                                selectByOptionsPurchaseBills(oPage_PurchaseBill.select_vendorOptionsCreatePurchaseBill, (vendorName + " - " + vendor_address));
                                Cls_Generic_Methods.customWait();

                                Cls_Generic_Methods.selectElementByVisibleText(oPage_PurchaseBill.select_createAgainstPurchaseBill, "GRN with Challan No.");

                                Cls_Generic_Methods.customWait();
                                validateSearchPurchaseBill("Challan No", challanNumber);
                                Cls_Generic_Methods.customWait();
                                for (WebElement rowCreatedGrn : oPage_PurchaseBill.list_textGrnBillNoPurchaseBillTable) {
                                    Cls_Generic_Methods.clickElement(rowCreatedGrn);
                                    break;
                                }
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.button_removeFromList, 10);

                                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseBill.input_invoiceNoPurchaseBill, challanNumber), "Entered Invoice number as : <b>" + challanNumber + "</b>");
                                Cls_Generic_Methods.clickElement(oPage_PurchaseBill.input_invoiceDatePurchaseBill);
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.input_todayBillDate, 3);
                                Cls_Generic_Methods.clickElement(oPage_PurchaseBill.input_todayBillDate);

                            }

                            Cls_Generic_Methods.customWait();
                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseBill.button_saveChanges), "Clicked <b>Save Changes</b> button in Purchase Bill");
                            Cls_Generic_Methods.customWait(8);
                            boolean rowSelected = selectRowWithPurchaseBillNumber(purchaseBill_no);
                            if (rowSelected) {
                                String invoiceNo = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_InvoiceNo);
                                if (invoiceNo.equals(challanNumber)) {
                                    m_assert.assertTrue("Validated Edit -->Invoice no changed to <b>" + invoiceNo + "</b>");
                                    m_assert.assertTrue("Validated -->Purchase Bill Edit Functionality");
                                } else {
                                    m_assert.assertFatal("Unable to validate edit functionality ");
                                }
                            } else {
                                m_assert.assertFatal("Unable to find Purchase Bill with pb no =" + purchaseBill_no);
                            }
                            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

                        } else {
                            m_assert.assertFatal("Unable to edit purchase bill no " + purchaseBill_no + " -->Status is not open");
                        }
                        break;
                    }
                }
                rowNo++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate purchase bill edit functionality-->" + e);
        }
    }

    @Test(enabled = true, description = "Validating Approve Purchase Bill")
    public void validateApprovePurchaseBill() {
        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_PurchaseBill = new Page_PurchaseBill(driver);
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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.button_edit, 10);

            List<String> purchaseBillHeaderList = new ArrayList<String>();

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PurchaseBill.list_purchaseBillHeaderList, 10);
            for (WebElement purchaseHeaderList : oPage_PurchaseBill.list_purchaseBillHeaderList) {
                purchaseBillHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
            }
            int rowNo = 0;
            for (WebElement row : oPage_PurchaseBill.list_purchaseBillRowList) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    String sPB_number = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_purchaseBillRowPBnoList.get(rowNo));
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));
                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseBillHeaderList.indexOf("Status")));
                    if (sPB_number.equalsIgnoreCase(purchaseBill_no)) {
                        if (purchaseStatus.equalsIgnoreCase("open")) {
                            Cls_Generic_Methods.clickElement(row);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.button_approve, 10);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PurchaseBill.button_approve), "Clicked Approve Purchase Bill--> PB No=" + purchaseBill_no);
                            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.notifyMsg_Approved, 10), "<b>Approved</b> notification msg is displayed ");
                        } else {
                            m_assert.assertFatal("Unable to approve purchase bill no " + purchaseBill_no + " -->Status is not open");
                        }
                        break;
                    }
                }
                rowNo++;
            }
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");

            boolean rowSelected = selectRowWithPurchaseBillNumber(purchaseBill_no);
            if (rowSelected) {
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.button_print, 10), "Validated Approve --> Print option is displayed");
                m_assert.assertFalse(Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseBill.button_edit), "Validated Approve --> Edit option is removed");
                m_assert.assertFalse(Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseBill.button_cancel), "Validated Approve --> Cancel option is removed");


                int preWindowsCount = driver.getWindowHandles().size();
                String initialWindowHandle = driver.getWindowHandle();
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseBill.button_print), "Clicked Print button");
                Cls_Generic_Methods.customWait(8);
                int postWindowsCount = driver.getWindowHandles().size();

                m_assert.assertTrue(postWindowsCount > preWindowsCount, "Validated Print -->Purchase Bill Print page opened");

                for (String currentWindowHandle : driver.getWindowHandles()) {
                    if (!currentWindowHandle.equals(driver.getWindowHandle())) {
                        driver.switchTo().window(currentWindowHandle);
                    }
                }
                driver.close();
                driver.switchTo().window(initialWindowHandle);

                Cls_Generic_Methods.customWait(5);
                rowSelected = selectRowWithPurchaseBillNumber(purchaseBill_no);
                if (rowSelected) {
                    String rhsStatus = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_purchaseBillStatus);
                    m_assert.assertTrue(rhsStatus.equalsIgnoreCase("Approved"), "Validated Approve --> Status = <b>" + rhsStatus + "</b>");
                    Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate purchase bill Approve functionality-->" + e);
        }

    }

    @Test(enabled = true, description = "Validating Cancel Purchase Bill")
    public void validateCancelPurchaseBill() {
        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_PurchaseBill = new Page_PurchaseBill(driver);
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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.button_purchaseNew, 10);
            boolean rowSelected = selectRowWithPurchaseBillNumber(purchaseBill_no);
            Cls_Generic_Methods.customWait();
            String status=Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_purchaseBillStatus);
            if(!status.equals("open")){
                rowSelected=selectFromListBasedOnStatus("open");
            }

            if (rowSelected) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.button_cancel, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PurchaseBill.button_cancel), "Clicked Cancel Purchase Bill--> PB No=" + purchaseBill_no);
                String cancellationReason = "AUTO TESTING CANCELLATION";
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.input_cancellationReason, 10);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseBill.input_cancellationReason, cancellationReason);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PurchaseBill.button_dontCancel), "Clicked Don't Cancel button");
                Cls_Generic_Methods.clickElement(driver, oPage_PurchaseBill.button_cancel);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.input_cancellationReason, 10);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseBill.input_cancellationReason, cancellationReason), "Entered <b>" + cancellationReason + "</b> in cancellation reason");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseBill.button_confirmCancel), "Clicked <b>Cancel Purchase Bill</b> button");


                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");

                rowSelected = selectRowWithPurchaseBillNumber(purchaseBill_no);
                Cls_Generic_Methods.customWait(4);
                if (rowSelected) {
                    String rhsStatus = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_purchaseBillStatus);
                    m_assert.assertTrue(rhsStatus.equalsIgnoreCase("cancelled"), "Validated Cancel --> Status = <b>" + rhsStatus + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_purchaseBillCancelledReason).equalsIgnoreCase(cancellationReason), "Validated Cancel -->Cancelled Reason in rhs side = <b>" + cancellationReason + "</b>");
                    Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate purchase bill Cancel functionality-->" + e);
        }
    }

    @Test(enabled = true, description = "Validating Search Functionality")
    public void validateSearchFunctionalityInPurchaseBill(){

        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
        String purchaseBillSearchTypeList[] =  {"Purchase Bill No.","Invoice No.","GRN No","Challan No.","Bill No."};

        boolean bPurchaseOrderFound = false;

            try{
                CommonActions.loginFunctionality(expectedLoggedInUser);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                CommonActions.selectStoreOnApp(store);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type  Selection Dropdown Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.input_searchBoxInput),
                        " Input Search Box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(purchaseBillSearchTypeList[0]),
                        " By Default Search Type Selection Dropdown Displayed correctly as :"+purchaseBillSearchTypeList[0]);
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty by default for selected search type Displayed correctly");
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Not Displayed Correctly as Default");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"placeholder")
                                .contains("Search By "+purchaseBillSearchTypeList[0]),
                        " Input Search Box Place holder for selected search type Displayed correctly");

                boolean searchResultByPurchaseBillNumber = CommonActions.selectOptionAndSearch(purchaseBillSearchTypeList[0],purchaseBill_no);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(searchResultByPurchaseBillNumber,purchaseBillSearchTypeList[0] +" Search Not Working");
                if(searchResultByPurchaseBillNumber) {
                    bPurchaseOrderFound = selectRowWithPurchaseBillNumber(purchaseBill_no);
                    m_assert.assertTrue(bPurchaseOrderFound && oPage_PurchaseBill.list_purchaseBillRowList.size() == 1,
                            "Search By Purchase Bill Number Worked correctly as Record found " +
                                    "in the page for Purchase Bill Number: "+purchaseBill_no);
                }

                boolean searchResultByInvoice = CommonActions.selectOptionAndSearch(purchaseBillSearchTypeList[1],billNumberSearch);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(searchResultByInvoice,purchaseBillSearchTypeList[1] +" Search Not Working");

                if(searchResultByInvoice) {

                    selectRowWithPurchaseBillNumber(purchaseBill_no);
                    String invoiceNoUI = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_InvoiceNo);
                    m_assert.assertTrue(oPage_PurchaseBill.list_purchaseBillRowList.size() == 1 && invoiceNoUI.equalsIgnoreCase(billNumberSearch),
                            "Search By Invoice No Worked correctly as Purchase Order found " +
                                    "in the Order page for Indent Number: "+billNumberSearch);
                }

                String searchValue[] = {grnNumber,challanNumberSearch,billNumberSearch};
                for(int i = 2 ; i<purchaseBillSearchTypeList.length;i++){

                    boolean searchResult = CommonActions.selectOptionAndSearch(purchaseBillSearchTypeList[i],searchValue[i-2]);
                    Cls_Generic_Methods.customWait();
                    m_assert.assertTrue(searchResult,purchaseBillSearchTypeList[i] +" Search Not Working");

                    if(searchResult) {
                        m_assert.assertTrue(oPage_PurchaseBill.list_purchaseBillRowList.size() == 1,
                                "Search By "+purchaseBillSearchTypeList[i]+" Worked correctly as Order found " +
                                        "in the page for Number : "+searchValue[i-2]);
                    }
                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_filterDropdownButton),
                        " Filter Dropdown Button Clicked");
                Cls_Generic_Methods.customWait(2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.text_thisYearFilter),
                        " This Year Selected as Filter");
                Cls_Generic_Methods.customWait(2);

                String oldPurchaseBillNo = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_purchaseBillRowPBnoList.get(3));
                String oldInvoice = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_purchaseBillRowInvoicenoList.get(3));
                String oldChallan = "";
                for(WebElement ele :oPage_PurchaseBill.list_purchaseBillRowInvoicenoList ){
                    String text = Cls_Generic_Methods.getTextInElement(ele);
                    if(text.contains("CHALLAN")){
                        oldChallan = text ;
                        break;
                    }
                }

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                CommonActions.selectOptionAndSearch(purchaseBillSearchTypeList[4],billNumberSearch);
                String grnText = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_purchaseBillRowList.get(0).findElement(By.xpath(".//td[1]")));
                String oldGrn = grnText.split("\n")[1];
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);

                boolean searchResultByOldPurchaseBillNumber = CommonActions.selectOptionAndSearch(purchaseBillSearchTypeList[0],oldPurchaseBillNo);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(searchResultByOldPurchaseBillNumber,purchaseBillSearchTypeList[0] +" Search Not Working");

                if(searchResultByOldPurchaseBillNumber) {
                    bPurchaseOrderFound = selectRowWithPurchaseBillNumber(oldPurchaseBillNo);
                    m_assert.assertTrue(bPurchaseOrderFound && oPage_PurchaseBill.list_purchaseBillRowList.size() == 1,
                            "Search By Old Purchase Bill Number Worked correctly as Record found " +
                                    "in the page for Purchase Bill Number: "+purchaseBill_no);
                }

                boolean searchResultByOldInvoice = CommonActions.selectOptionAndSearch(purchaseBillSearchTypeList[1],oldInvoice);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(searchResultByOldInvoice,purchaseBillSearchTypeList[1] +" Search Not Working");

                if(searchResultByOldInvoice) {

                    selectRowWithPurchaseBillNumber(oldPurchaseBillNo);
                    String invoiceNoUI = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_InvoiceNo);
                    m_assert.assertTrue(oPage_PurchaseBill.list_purchaseBillRowList.size() == 1 && invoiceNoUI.equalsIgnoreCase(oldInvoice),
                            "Search By Old Invoice Worked correctly as Purchase Order found " +
                                    "in the Order page for Indent Number: "+oldInvoice);
                }

                String searchOldValue[] = {oldGrn,oldChallan,oldInvoice};
                for(int i = 2 ; i<purchaseBillSearchTypeList.length;i++){

                    boolean searchResult = CommonActions.selectOptionAndSearch(purchaseBillSearchTypeList[i],searchOldValue[i-2]);
                    Cls_Generic_Methods.customWait();
                    m_assert.assertTrue(searchResult,purchaseBillSearchTypeList[i] +" Search Not Working");

                    if(searchResult) {
                        m_assert.assertTrue(oPage_PurchaseBill.list_purchaseBillRowList.size() == 1,
                                "Search By  Old "+purchaseBillSearchTypeList[i]+" Worked correctly as Order found " +
                                        "in the page for Number : "+searchValue[i-2]);
                    }
                }


                for( int i = 0 ; i<purchaseBillSearchTypeList.length ; i++){

                boolean searchResultByWrongValue = CommonActions.selectOptionAndSearch(purchaseBillSearchTypeList[0],purchaseBill_no+"23");
                m_assert.assertFalse(searchResultByWrongValue," Transaction Search With Incorrect Number Working Correct");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                        " Nothing To Display Text Displayed as Result");
                }


                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Displayed and clicked");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty as clear button is working correctly");
                boolean selectOption = CommonActions.selectOption(purchaseBillSearchTypeList[0]);
                m_assert.assertTrue(selectOption," Able to selected Search type Again to Default");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(purchaseBillSearchTypeList[0]),
                        " Search Type Selected  as : "+purchaseBillSearchTypeList[0]);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type Dropdown Clicked");
                Cls_Generic_Methods.customWait();
                for(WebElement type : oPage_InventorySearchCommonElements.list_searchTypeList){
                    String typeText = Cls_Generic_Methods.getTextInElement(type);
                    int index = oPage_InventorySearchCommonElements.list_searchTypeList.indexOf(type);
                    if(typeText.equalsIgnoreCase(purchaseBillSearchTypeList[index])){
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
    }

    private boolean createPurchaseGrn(String billType) {
        oPage_Purchase = new Page_Purchase(driver);
        billNumber = "BILL_" + getRandomNumber();
        challanNumber = "CHALLAN_" + getRandomNumber();
        batchNo = getRandomNumber();
        boolean purchaseGrnCreated = false;

        try {
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 10);
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_purchaseNew);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_Vendor_search, 4);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_Vendor_search, (vendorName + " " + vendor_address));
            Cls_Generic_Methods.customWait();
            for (WebElement eVendor : oPage_Purchase.list_select_vendor) {
                Cls_Generic_Methods.clickElementByJS(driver, eVendor);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean itemClicked = false;
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_searchItem, 20);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_searchItem, purchaseItem);
            Cls_Generic_Methods.customWait(3);
            oPage_Purchase.input_searchItem.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_itemNameInPurchaseStore, 10);
            for (WebElement eItemName : oPage_Purchase.list_itemNameInPurchaseStore) {
                if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(purchaseItem)) {
                    Cls_Generic_Methods.clickElementByJS(driver, eItemName);
                    itemClicked = true;
                    m_assert.assertInfo("Selected Item <b>" + purchaseItem + "</b> in Purchase/GRN");
                    break;
                }
            }


            if (itemClicked) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewLot, 15);

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_batchNumber, batchNo), "Entered batch number as :<b> " + batchNo + "</b>");
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.select_subStore)) {
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_subStore, subStore);
                }

                if (Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.input_expiryDate) && Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_expiryDate, "value").isEmpty()) {
                    Cls_Generic_Methods.clickElement(driver, oPage_Purchase.input_expiryDate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.select_expiryDateYear, 1);
                    String currentYear = Cls_Generic_Methods.getSelectedValue(oPage_Purchase.select_expiryDateYear);
                    int year = Integer.parseInt(currentYear);
                    int newYear = year + 3;

                    Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_expiryDateYear, Integer.toString(newYear));
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.select_expiryDateDay);
                }

                Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_unitCostWOTax);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_unitCostWOTax, unitCostWOTax),
                        "Unit cost without tax entered as : <b> " + unitCostWOTax + "</b>");
                Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_packageQuantity);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_packageQuantity, packageQuantity),
                        "package entry entered as : <b> " + packageQuantity + "</b>");
                Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_sellingPrice);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_sellingPrice, sellingPrice),
                        "Selling Price entered as : <b> " + sellingPrice + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_discountAmount, discountAmount),
                        "Entered Discount as : <b> " + discountAmount + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_addFreeQuantity, freeQuantity),
                        "Entered Free Quantity as : <b> " + discountAmount + "</b>");
                taxPercentage = Cls_Generic_Methods.getTextInElement(oPage_Purchase.label_taxPercentage).split("%")[0];
                m_assert.assertInfo("Tax percentage for the selected item :<b> " + taxPercentage + " </b>");
                dispensingUnit = Cls_Generic_Methods.getTextInElement(oPage_Purchase.label_dispensingUnit);

                Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveLot);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_SaveChanges);

            } else {
                m_assert.assertFatal("Item not selected");
            }

            Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes);
            Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType), "Selected Bill Type as <b>" + billType + "</b>");
            if (billType.equalsIgnoreCase("CHALLAN")) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_challanNumber, challanNumber), "Entered<b> " + challanNumber + "</b> in Challan Number");
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_challanDate);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate);
            } else {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber), "Entered<b> " + billNumber + "</b> in Bill Number");
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate);
            }
            Cls_Generic_Methods.selectElementByIndex(oPage_Purchase.select_otherCharges, 1);
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_otherChargesAmount, otherCharges), "Entered<b> " + otherCharges + " </b> in other charges");
            Cls_Generic_Methods.customWait();
            netAmountInGrn = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalNetAmount, "value");
            m_assert.assertInfo("Net amount in Purchase Grn = <b>" + netAmountInGrn + "</b>");
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot);

            List<String> purchaseTransactionHeaderList = new ArrayList<String>();

            Cls_Generic_Methods.customWait(8);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_purchaseTransactionHeaderList, 10);
            for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
                purchaseTransactionHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
            }
            for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));
                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Status")));
                    if (purchaseStatus.equalsIgnoreCase("open")) {
                        Cls_Generic_Methods.clickElement(row);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 10);
                        Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_Yes, 6);
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.button_Yes);
                        purchaseGrnCreated = true;
                        m_assert.assertInfo("Purchase Grn created and approved");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_assignBarcodePurchaseTransaction, 15);
                        grn_no = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_transactionID);
                        m_assert.assertInfo("Purchase Grn no =<b>" + grn_no + "</b>");
                        grnCreatedAt = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_grnCreatedAt);
                        m_assert.assertInfo("Purchase Grn Created At =<b>" + grnCreatedAt + "</b>");
                        grnApprovedBy = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_grnApprovedByUser);
                        m_assert.assertInfo("Purchase Grn Approved By user =<b>" + grnApprovedBy + "</b>");
                        grnApprovedAt = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_grnApprovedAt);
                        m_assert.assertInfo("Purchase Grn Approved At =<b>" + grnApprovedAt + "</b>");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to create Purchase GRN" + e);
        }

        return purchaseGrnCreated;
    }

    private boolean createPurchaseOrder(String billType) {
        boolean purchaseOrderCreated = false;
        uomValidation = false;
        oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        oPage_Purchase = new Page_Purchase(driver);
        String sPoType = "Normal";
        batchNo = getRandomNumber();
        billNumber = "BILL_NO_" + getRandomNumber();

        try {
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 3);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_newOrder), "New Button clicked in Order Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.dropdown_poType, 10);
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_PurchaseOrder.dropdown_poType, sPoType),
                    "PO Type selected: <b>" + sPoType + " </b>");
            Cls_Generic_Methods.customWait(3);
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_PurchaseOrder.dropdown_store, store.split("-")[0].trim()),
                    "Store selected: <b>" + store + " </b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_Vendor_search, 4);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, (vendorName + " " + vendor_address));
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, "" + Keys.DOWN + Keys.ENTER);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, "" + Keys.DOWN + Keys.ENTER),
                    "Vendor selected: <b>" + vendorName + "</b>");
            Cls_Generic_Methods.customWait(3);

            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.input_searchMedicineNamePO);
            Cls_Generic_Methods.sendKeysByAction(oPage_PurchaseOrder.input_searchMedicineNamePO, purchaseItem);

            Cls_Generic_Methods.customWait(5);
            boolean bPO_Item_Found = false;

            for (WebElement eMedicineName : oPage_PurchaseOrder.list_namesOfMedicinesOnLeftInSearchResultPO) {
                String sMedicineName = Cls_Generic_Methods.getTextInElement(eMedicineName);

                if (sMedicineName.equals(purchaseItem)) {
                    bPO_Item_Found = true;
                    Cls_Generic_Methods.clickElement(eMedicineName);
                    break;
                }
            }

            m_assert.assertTrue(bPO_Item_Found, "Validate the PO item:<b> " + purchaseItem + " </b> is found & selected");
            if (bPO_Item_Found) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.header_addNewLot, 15);

                Cls_Generic_Methods.clearValuesInElement(oPage_PurchaseOrder.input_unitCostWOTax);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_unitCostWOTax, unitCostWOTax),
                        "Cost price input: <b> " + unitCostWOTax + " </b>");

                Cls_Generic_Methods.clearValuesInElement(oPage_PurchaseOrder.input_packageQuantity);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_packageQuantity, packageQuantity),
                        "Package quantity entered: <b> " + packageQuantity + " </b>");

                Cls_Generic_Methods.clearValuesInElement(oPage_PurchaseOrder.input_discountAmount);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_discountAmount, discountAmount),
                        "Discount entered: <b> " + discountAmount + " </b>");

                taxPercentage = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.input_taxPercentage).split("%")[0];

                Cls_Generic_Methods.clearValuesInElement(oPage_PurchaseOrder.input_freeUnitQuantity);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_freeUnitQuantity, freeQuantity),
                        "Free Unit quantity: <b> " + freeQuantity + " </b>");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_saveLot),
                        "Lot Saved");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.dropdown_otherCharge, 7);
                Cls_Generic_Methods.selectElementByIndex(oPage_PurchaseOrder.dropdown_otherCharges, 1);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_otherChargeAmount, otherCharges), "Entered<b> " + otherCharges + " </b> in other charges");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_saveOrder),
                        "Order Saved");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 10);
                Cls_Generic_Methods.customWait(6);
                List<String> purchaseOrderHeaderList = new ArrayList<>();
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PurchaseOrder.list_purchaseOrderHeaderList, 10);

                for (WebElement purchaseHeaderList : oPage_PurchaseOrder.list_purchaseOrderHeaderList) {
                    purchaseOrderHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
                }

                for (WebElement row : oPage_PurchaseOrder.list_purchaseOrdertransactions) {

                    if (Cls_Generic_Methods.isElementDisplayed(row)) {
                        List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));
                        String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseOrderHeaderList.indexOf("Status")));
                        if (purchaseStatus.equalsIgnoreCase("pending")) {
                            Cls_Generic_Methods.clickElement(row);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_approveOrder, 10);
                            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_approveOrder);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_Yes, 10);
                            Cls_Generic_Methods.clickElementByJS(driver, oPage_PurchaseOrder.button_Yes);

                            m_assert.assertInfo("Purchase Order created and approved");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_NewTransaction, 15);
                            purchaseOrder_no = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_purchaseOrderId);
                            m_assert.assertInfo("Purchase Order no =<b>" + purchaseOrder_no + "</b>");
                            poCreatedBy = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_poCreatedByUser);
                            m_assert.assertInfo("Purchase Order Created By user =<b>" + poCreatedBy + "</b>");
                            poCreatedAt = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_poCreatedAt);
                            m_assert.assertInfo("Purchase Order Created At =<b>" + poCreatedAt + "</b>");
                            break;
                        }
                    }
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_NewTransaction, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_NewTransaction), "Clicked New transaction button to create GRN");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 10);

                Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType), "Selected Bill Type as <b>" + billType + "</b>");
                if (billType.equalsIgnoreCase("CHALLAN")) {
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_challanNumber, challanNumber), "Entered<b> " + challanNumber + "</b> in Challan Number");
                    Cls_Generic_Methods.clickElement(oPage_Purchase.input_challanDate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                    Cls_Generic_Methods.clickElement(oPage_Purchase.input_challanDate);
                } else {
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber), "Entered<b> " + billNumber + "</b> in Bill Number");
                    Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                    Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate);
                }

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(0), sellingPrice), "Entered  MRP :<b>" + sellingPrice + " </b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(0), batchNo), "Entered  batch number :<b>" + batchNo + " </b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_expiryDateGRN);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.select_expiryDateYear, 1);
                String currentYear = Cls_Generic_Methods.getSelectedValue(oPage_Purchase.select_expiryDateYear);
                int year = Integer.parseInt(currentYear);
                int newYear = year + 3;
                Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_expiryDateYear, Integer.toString(newYear));
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.select_expiryDateDay);
                Cls_Generic_Methods.selectElementByIndex(oPage_Purchase.select_subStore, 1);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_vendorOtherChargesToBePaid, otherCharges);
                Cls_Generic_Methods.customWait();
                netAmountInGrn = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalNetAmount, "value");
                m_assert.assertInfo("Net amount in Purchase Grn = <b>" + netAmountInGrn + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_SaveChanges);

                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 10);
                List<String> purchaseTransactionHeaderList = new ArrayList<String>();

                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_purchaseTransactionHeaderList, 10);
                for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
                    purchaseTransactionHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
                }
                for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

                    if (Cls_Generic_Methods.isElementDisplayed(row)) {
                        List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));
                        String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Status")));
                        if (purchaseStatus.equalsIgnoreCase("open")) {
                            Cls_Generic_Methods.clickElement(row);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 10);
                            Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_Yes, 6);
                            Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.button_Yes);
                            purchaseOrderCreated = true;
                            m_assert.assertInfo("Purchase Grn created and approved");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_assignBarcodePurchaseTransaction, 15);
                            grn_no = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_transactionID);
                            m_assert.assertInfo("Purchase Grn no =<b>" + grn_no + "</b>");
                            grnCreatedAt = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_grnCreatedAt);
                            m_assert.assertInfo("Purchase Grn Created At =<b>" + grnCreatedAt + "</b>");
                            grnApprovedBy = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_grnApprovedByUser);
                            m_assert.assertInfo("Purchase Grn Approved By user =<b>" + grnApprovedBy + "</b>");
                            grnApprovedAt = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_grnApprovedAt);
                            m_assert.assertInfo("Purchase Grn Approved At =<b>" + grnApprovedAt + "</b>");
                            break;
                        }
                    }
                }
            } else {
                m_assert.assertFatal("Item " + purchaseItem + " not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to create Purchase Order-->" + e);
        }
        return purchaseOrderCreated;
    }


    private void getGST_valueFromSetting() {
        oPage_StoreSetUp = new Page_StoreSetUp(driver);
        oPage_VendorMaster = new Page_VendorMaster(driver);
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
                        }
                    }
                    break;
                }
                storeNo++;
            }
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();

            if (storeGSTno != null) {
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.header_vendorMasterTitle, 10);

                int vendorNo = 0;
                for (WebElement txtStoreName : oPage_VendorMaster.list_text_vendorName) {

                    String vendorNameInList = Cls_Generic_Methods.getTextInElement(txtStoreName);
                    if (vendorNameInList.equalsIgnoreCase(vendorName)) {
                        Cls_Generic_Methods.clickElement(driver, oPage_VendorMaster.list_btn_vendorLocation.get(vendorNo));

                        Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_VendorMaster.list_btn_vendorLocationHeader, 10);

                        int headerColumn = 0;
                        for (WebElement locationHeader : oPage_VendorMaster.list_btn_vendorLocationHeader) {

                            String headerValue = Cls_Generic_Methods.getTextInElement(locationHeader);
                            if (headerValue.equalsIgnoreCase("Address")) {
                                WebElement txt_address = driver.findElement(By.xpath("//td[contains(text(),'" + vendorNameInList + "')]/parent::tr")).findElements(By.xpath("./td")).get(headerColumn);
                                vendor_address = Cls_Generic_Methods.getTextInElement(txt_address);
                            }
                            if (headerValue.equalsIgnoreCase("Actions")) {
                                WebElement btn_edit = driver.findElement(By.xpath("//td[contains(text(),'" + vendorNameInList + "')]/parent::tr")).findElement(By.xpath("./td//a[text()='Edit']"));
                                Cls_Generic_Methods.clickElement(driver, btn_edit);

                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.input_gstNo, 10);
                                vendorGSTno = Cls_Generic_Methods.getElementAttribute(oPage_VendorMaster.input_gstNo, "value");
                                m_assert.assertInfo("GST no. present in " + vendorName + " is <b>" + vendorGSTno + "</b>");
                            }
                            headerColumn++;
                        }
                    }
                    vendorNo++;
                }
            }
            Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_close);

            if (vendorGSTno != null && storeGSTno != null) {
                if (vendorGSTno.substring(0, 2).equals(storeGSTno.substring(0, 2))) {
                    gstTypeIGST = false;
                    m_assert.assertInfo("GST type = CGST + SGST");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to get GST no. from Organisation Setting " + e);
        }
    }

    private void validateSearchPurchaseBill(String searchType, String value) {
        try {
            WebElement eSearchType = null;
            switch (searchType.toUpperCase()) {
                case "GRN NO" -> eSearchType = oPage_PurchaseBill.btn_searchTypeGRNnoPurchaseBill;
                case "BILL NO" -> eSearchType = oPage_PurchaseBill.btn_searchTypeBillNoPurchaseBill;
                case "PO NO" -> eSearchType = oPage_PurchaseBill.btn_searchTypePOnoPurchaseBill;
                case "CHALLAN NO" -> eSearchType = oPage_PurchaseBill.btn_searchTypeChallanNoPurchaseBill;
            }

            Cls_Generic_Methods.clickElementByJS(driver,oPage_PurchaseBill.btn_searchTypePurchaseBill);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(eSearchType), "Selected search criteria as " + searchType);
            Cls_Generic_Methods.customWait(1);
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseBill.input_searchPurchaseBill, value), "Entered <b>" + value + "</b> in search purchase bill");

            Cls_Generic_Methods.clickElement(oPage_PurchaseBill.btn_submitSearchPurchaseBill);
            Cls_Generic_Methods.customWait();
            List<WebElement> listOfRows = null;
            switch (searchType.toUpperCase()) {
                case "GRN NO" -> listOfRows = oPage_PurchaseBill.list_textGRNnoPurchaseBillTable;
                case "BILL NO" -> listOfRows = oPage_PurchaseBill.list_textGrnBillNoPurchaseBillTable;
                case "PO NO" -> listOfRows = oPage_PurchaseBill.list_textGrnPONoPurchaseBillTable;
                case "CHALLAN NO" -> listOfRows = oPage_PurchaseBill.list_textGrnChallanNoPurchaseBillTable;
            }
            int noOfRow = listOfRows.size();
            if (noOfRow == 1) {
                String txtInRow = Cls_Generic_Methods.getTextInElement(listOfRows.get(0));
                m_assert.assertEquals(txtInRow, value, "Validated-->" + searchType + " search functionality");
            } else {
                m_assert.assertFatal("Unable to validate " + searchType + " search functionality");
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate search purchase bill with " + searchType + " ----->" + e);
        }
    }

    private void validateTaxBreakUp() {

        double taxBreakupPercentage = Double.parseDouble(taxPercentage);
        double totalAmountBeforeTax = (Double.parseDouble(unitCostWOTax) * Double.parseDouble(packageQuantity)) - 10;
        double totalTaxAmount = (totalAmountBeforeTax * taxBreakupPercentage) / 100;
        sTotalAmountBeforeTax = String.valueOf(totalAmountBeforeTax);
        System.out.println(taxBreakupPercentage + "-------" + totalAmountBeforeTax);

        if (gstTypeIGST) {
            try {
                String igstRate = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseBill.input_igstRate, "value").replaceAll("[^0-9]", "");
                String igstAmount = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseBill.input_igstAmount, "value");

                m_assert.assertTrue(Double.parseDouble(igstRate) == taxBreakupPercentage, "Validated IGST breakup percentage--> IGST - <b>" + igstRate + "%</b>");
                m_assert.assertTrue(Double.parseDouble(igstAmount) == totalTaxAmount, "Validated IGST breakup amount--> IGST - <b>" + igstAmount + "</b>");
            } catch (Exception e) {
                m_assert.assertFalse("IGST Value is empty - unable to validate tax breakup");
                e.printStackTrace();
            }
        } else {

            try {
                String cgstRate = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseBill.input_cgstRate, "value").replaceAll("[^0-9]", "");
                String cgstAmount = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseBill.input_cgstAmount, "value");
                String sgstRate = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseBill.input_sgstRate, "value").replaceAll("[^0-9]", "");
                String sgstAmount = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseBill.input_sgstAmount, "value");

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

    private String getRandomNumber() {
        Random random = new Random();
        String id = String.format("%06d", random.nextInt(1000000));
        return id;
    }

    private boolean selectAndValidateTimePeriod(String periodToSelect, String grn_no) {
        boolean status = false;
        try {
            periodToSelect = periodToSelect.toLowerCase().trim().replaceAll(" ", "-");
            Cls_Generic_Methods.clickElement(driver, oPage_PurchaseBill.button_timePeriod);
            Cls_Generic_Methods.clickElement(driver.findElement(By.xpath("//li/a[contains(@class,'" + periodToSelect + "')]")));
            Cls_Generic_Methods.customWait(3);

            for (WebElement eGrnNoText : oPage_PurchaseBill.list_textGRNnoPurchaseBillTable) {
                String txt_grnNo = Cls_Generic_Methods.getTextInElement(eGrnNoText);
                if (txt_grnNo.equals(grn_no)) {
                    status = true;
                    break;
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to select " + periodToSelect);
            e.printStackTrace();
        }
        return status;
    }

    private void validatePurchaseBillInvoiceInfo(String headerValue, String expectedValue) {

        try {
            int rowNo = 0;
            for (WebElement eInvoiceHeadingInfo : oPage_PurchaseBill.list_textHeadingInvoiceInfoPurchaseBill) {
                String sHeader = Cls_Generic_Methods.getTextInElement(eInvoiceHeadingInfo);
                if (sHeader.equalsIgnoreCase(headerValue)) {
                    String actualValue = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseBill.list_inputValueInvoiceInfoPurchaseBill.get(rowNo), "value");
                    m_assert.assertTrue((Double.parseDouble(expectedValue) == Double.parseDouble(actualValue)), "Validated-->Purchase Bill Invoice Info--> " + headerValue + " = <b>" + actualValue + "</b>");
                    break;
                } else {
                    if (oPage_PurchaseBill.list_textHeadingInvoiceInfoPurchaseBill.size() - 1 == rowNo) {
                        m_assert.assertFatal("Unable to find " + headerValue + " in purchase bill invoice info");
                    }
                }
                rowNo++;
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate purchase bill invoice info ---->" + e);
        }
    }

    private void validateAndCreatePurchaseBill() {
        Page_InventorySearchCommonElements  oPage_InventorySearchCommonElements = new Page_InventorySearchCommonElements(driver);
        try {
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.button_purchaseNew, 10);
            Cls_Generic_Methods.clickElement(oPage_PurchaseBill.button_purchaseNew);

            //Selecting vendor
            boolean vendorSelected = selectByOptionsPurchaseBills(oPage_PurchaseBill.select_vendorOptionsCreatePurchaseBill, (vendorName + " - " + vendor_address));
            m_assert.assertInfo(vendorSelected, "Selected <i>vendor</i> as <b>" + vendorName + "</b>");
            Cls_Generic_Methods.customWait();

            if (vendorSelected) {
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_PurchaseBill.select_createAgainstPurchaseBill, "GRN with Bill No."), "Selected <i>create against</i> as <b>GRN with Bill No.</b>");
            }
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PurchaseBill.list_textGRNnoPurchaseBillTable, 20);
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_PurchaseBill.text_vendorGstNoPurchaseBill, "value").equals(vendorGSTno), "Validated--> Vendor GST no");

            int grnRow = 0;
            for (WebElement eGrnNoText : oPage_PurchaseBill.list_textGRNnoPurchaseBillTable) {
                String txt_grnNo = Cls_Generic_Methods.getTextInElement(eGrnNoText);
                if (txt_grnNo.equals(grn_no)) {
                    m_assert.assertTrue("Validated-->Newly Created Purchase Grn is present");
                    String row_grnApprovedAt = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_textGRNApprovedAtPurchaseBillTable.get(grnRow)).replaceAll(" ", "");
                    m_assert.assertEquals(grnApprovedAt.replaceAll(" ", ""), row_grnApprovedAt, "Validated-->Purchase Grn approved at <b>" + row_grnApprovedAt + "</b>");
                    m_assert.assertEquals(grnApprovedBy, Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_textGRNApprovedByUserPurchaseBillTable.get(grnRow)), "Validated-->Purchase Grn approved by user <b>" + grnApprovedBy + "</b>");
                    m_assert.assertEquals(billNumber, Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_textGrnBillNoPurchaseBillTable.get(grnRow)), "Validated-->Purchase Grn Bill no. <b>" + billNumber + "</b>");
                    break;
                }
                grnRow++;
            }
            validateSearchPurchaseBill("GRN No", grn_no);
            validateSearchPurchaseBill("Bill No", billNumber);
            if (purchaseOrder_no != null) {
                validateSearchPurchaseBill("PO No", purchaseOrder_no);
            }

            Cls_Generic_Methods.customWait();
            for (WebElement rowCreatedGrn : oPage_PurchaseBill.list_textGrnBillNoPurchaseBillTable) {
                Cls_Generic_Methods.clickElement(rowCreatedGrn);
                break;
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.button_removeFromList, 10);

            Cls_Generic_Methods.clickElementByJS(driver, oPage_PurchaseBill.select_vendorOptionsCreatePurchaseBill);
            List<WebElement> options = oPage_PurchaseBill.select_vendorOptionsCreatePurchaseBill.findElements(By.xpath("./option"));
            for (WebElement option : options) {
                String optionValue = Cls_Generic_Methods.getTextInElement(option);
                if (!optionValue.contains(vendorName) && !optionValue.contains("Select")) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(option), "Changing the <i>vendor</i> as <b>" + optionValue + "</b> ,after selecting the item with " + vendorName);
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.text_vendorChangeConfirmationMessage, 10),
                            "Validated -->Confirmation Message is displayed --> <b>" + Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_vendorChangeConfirmationMessage) + "</b>");
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseBill.button_noDontChangeVendor), "Clicked <b>No Don't Change</b> button");

                    Cls_Generic_Methods.customWait();

                    String selectedVendor = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseBill.select_vendorOptionsCreatePurchaseBill);
                    if (!selectedVendor.contains(vendorName)) {
                        m_assert.assertFalse("Vendor should not change after clicking <b> No don't change </b> button .Selected vendor = <b>" + selectedVendor + "</b>");
                    }
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_PurchaseBill.select_createAgainstPurchaseBill, "GRN with Bill No."), "Selected <i>create against</i> as <b>GRN with Bill No.</b>");
                    break;
                }
            }
            Cls_Generic_Methods.clickElementByJS(driver, oPage_PurchaseBill.select_vendorOptionsCreatePurchaseBill);
            for (WebElement option : options) {
                String optionValue = Cls_Generic_Methods.getTextInElement(option);
                if (optionValue.contains("kumar vendor")) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(option), "Changing the <i>vendor</i> as <b>" + optionValue + "</b> ,after selecting the item with " + vendorName);
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.text_vendorChangeConfirmationMessage, 10),
                            "Validated -->Confirmation Message is displayed --> <b>" + Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_vendorChangeConfirmationMessage) + "</b>");
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseBill.button_yesChangeVendor), "Clicked <b>Yes Change</b> button");

                    Cls_Generic_Methods.customWait();

                    String selectedVendor = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseBill.select_vendorOptionsCreatePurchaseBill);
                    if (selectedVendor.contains("kumar vendor")) {
                        m_assert.assertTrue("Validated--> Change Vendor -->Selected vendor = <b>" + vendorName + "</b>");
                        m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseBill.button_removeFromList), "Validated--> Change Vendor -->Previously Selected Transaction is removed from list");
                    }
                    selectByOptionsPurchaseBills(oPage_PurchaseBill.select_createAgainstPurchaseBill, "GRN with Bill No.");
                    break;
                }
            }
            selectByOptionsPurchaseBills(oPage_PurchaseBill.select_vendorOptionsCreatePurchaseBill, (vendorName + " - " + vendor_address));
            Cls_Generic_Methods.customWait(4);
            Cls_Generic_Methods.selectElementByVisibleText(oPage_PurchaseBill.select_createAgainstPurchaseBill, "GRN with Bill No.");

            //Validate Time Period
            m_assert.assertFalse(selectAndValidateTimePeriod("Yesterday", grn_no), "Validated -->Time Period-Yesterday");
            m_assert.assertTrue(selectAndValidateTimePeriod("This Week", grn_no), "Validated -->Time Period-This Week");
            m_assert.assertTrue(selectAndValidateTimePeriod("This Month", grn_no), "Validated -->Time Period-This Month");
            m_assert.assertTrue(selectAndValidateTimePeriod("This Quarter", grn_no), "Validated -->Time Period-This Quarter");
            m_assert.assertTrue(selectAndValidateTimePeriod("This Year", grn_no), "Validated -->Time Period-This Year");
            m_assert.assertFalse(selectAndValidateTimePeriod("Previous Week", grn_no), "Validated -->Time Period-Previous Week");
            m_assert.assertFalse(selectAndValidateTimePeriod("Previous Month", grn_no), "Validated -->Time Period-Previous Month");
            m_assert.assertFalse(selectAndValidateTimePeriod("Previous Quarter", grn_no), "Validated -->Time Period-Previous Quarter");
            m_assert.assertFalse(selectAndValidateTimePeriod("Previous Year", grn_no), "Validated -->Time Period-Previous Year");
            m_assert.assertTrue(selectAndValidateTimePeriod("Today", grn_no), "Validated -->Time Period-Today");

            Cls_Generic_Methods.customWait();
            for (WebElement rowCreatedGrn : oPage_PurchaseBill.list_textGrnBillNoPurchaseBillTable) {
                Cls_Generic_Methods.clickElement(rowCreatedGrn);
                break;
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.button_removeFromList, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseBill.button_removeFromList), "Clicked Remove from List button");
            m_assert.assertFalse(Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseBill.button_removeFromList), "Validated-->Transaction Remove From List functionality");

            for (WebElement rowCreatedGrn : oPage_PurchaseBill.list_textGrnBillNoPurchaseBillTable) {
                Cls_Generic_Methods.clickElement(rowCreatedGrn);
                break;
            }

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PurchaseBill.list_bodyPurchaseBillTable, 10);
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_PurchaseBill.input_invoiceNoPurchaseBill, "value").equals(billNumber), "Validated--> Invoice no. auto-filled if GRN created with Bill");
            String[] expectedInvoiceDateAndTime = grnApprovedAt.split("\\|");
            String expectedInvoiceDate = CommonActions.getRequiredFormattedDateTime("dd-MM-yyyy", "dd/MM/yyyy", expectedInvoiceDateAndTime[0]);
            m_assert.assertEquals(Cls_Generic_Methods.getElementAttribute(oPage_PurchaseBill.input_invoiceDatePurchaseBill, "value"), expectedInvoiceDate, "Validated--> Invoice date in purchase bill");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseBill.input_transactionNotePurchaseBill, transactionNotes), "Entered transaction note as <b>" + transactionNotes + "</b>");

            //Purchase Table Validation
            int columnNo = 0;
            for (WebElement headerPurchaseBill : oPage_PurchaseBill.list_headerPurchaseBillTable) {
                String headerValue = Cls_Generic_Methods.getTextInElement(headerPurchaseBill);
                if (headerValue.equalsIgnoreCase("DESCRIPTION")) {
                    String descriptionValue = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_bodyPurchaseBillTable.get(columnNo));
                    m_assert.assertEquals(descriptionValue, purchaseItem, "Validated-->Purchase Bill Description -->Displayed Item : <b>" + descriptionValue + "</b>");
                }
                if (headerValue.equalsIgnoreCase("UOM") && uomValidation) {
                    String uomValue = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_bodyPurchaseBillTable.get(columnNo));
                    m_assert.assertEquals(uomValue, dispensingUnit, "Validated-->Purchase Bill UOM Value -->Displayed UOM : <b>" + uomValue + "</b>");
                }
                if (headerValue.equalsIgnoreCase("BATCH NO.")) {
                    String batchNoValue = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_bodyPurchaseBillTable.get(columnNo));
                    m_assert.assertEquals(batchNoValue, batchNo, "Validated-->Purchase Bill Batch No. -->Displayed Batch no. : <b>" + batchNoValue + "</b>");
                }
                if (headerValue.equalsIgnoreCase("QTY.")) {
                    String qtyValue = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_bodyPurchaseBillTable.get(columnNo));
                    m_assert.assertTrue(Double.parseDouble(qtyValue) == Double.parseDouble(packageQuantity), "Validated-->Purchase Bill Quantity -->Displayed Qty. : <b>" + qtyValue + "</b>");
                }
                if (headerValue.equalsIgnoreCase("FREE QTY.")) {
                    String freeQtyValue = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_bodyPurchaseBillTable.get(columnNo));
                    m_assert.assertTrue(Double.parseDouble(freeQtyValue) == Double.parseDouble(freeQuantity), "Validated-->Purchase Bill Free Quantity -->Displayed Free Qty. : <b>" + freeQtyValue + "</b>");
                }
                if (headerValue.equalsIgnoreCase("DISCOUNT")) {
                    String discountValue = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_bodyPurchaseBillTable.get(columnNo));
                    m_assert.assertTrue(Double.parseDouble(discountValue) == Double.parseDouble(discountAmount), "Validated-->Purchase Bill Discount -->Displayed Discount amount : <b>" + discountValue + "</b>");
                }
                if (headerValue.equalsIgnoreCase("RATE")) {
                    String rateValue = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_bodyPurchaseBillTable.get(columnNo));
                    m_assert.assertTrue(Double.parseDouble(rateValue) == Double.parseDouble(unitCostWOTax), "Validated-->Purchase Bill Rate -->Displayed Rate : <b>" + rateValue + "</b>");
                }
                if (headerValue.equalsIgnoreCase("MRP")) {
                    String mrpValue = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_bodyPurchaseBillTable.get(columnNo));
                    m_assert.assertTrue(Double.parseDouble(mrpValue) == Double.parseDouble(sellingPrice), "Validated-->Purchase Bill MRP -->Displayed MRP : <b>" + mrpValue + "</b>");
                }
                if (headerValue.equalsIgnoreCase("TAX %")) {
                    String taxValue = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_bodyPurchaseBillTable.get(columnNo));
                    m_assert.assertTrue(Double.parseDouble(taxValue) == Double.parseDouble(taxPercentage), "Validated-->Purchase Bill Tax % -->Displayed Tax % : <b>" + taxValue + "</b>");
                }
                if (headerValue.equalsIgnoreCase("NET AMOUNT")) {
                    String netAmountValue = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_bodyPurchaseBillTable.get(columnNo));
                    m_assert.assertTrue(Double.parseDouble(netAmountValue) == (Double.parseDouble(netAmountInGrn) - Double.parseDouble(otherCharges)), "Validated-->Purchase Bill Net Amount -->Displayed Net Amount : <b>" + netAmountValue + "</b>");
                }
                columnNo++;
            }

            validateTaxBreakUp();


            double expectedBillAmount = Double.parseDouble(unitCostWOTax) * Double.parseDouble(packageQuantity);
            double expectedTotalDiscount = Double.parseDouble(discountAmount);
            double expectedTotalAmtBeforeTax = expectedBillAmount - expectedTotalDiscount;
            double expectedTotalTax = (Double.parseDouble(taxPercentage) * expectedTotalAmtBeforeTax) / 100;
            double expectedOtherCharges = Double.parseDouble(otherCharges);
            double expectedNetAmount = expectedTotalAmtBeforeTax + expectedTotalTax + expectedOtherCharges;

            validatePurchaseBillInvoiceInfo("Bill Amount", String.valueOf(expectedBillAmount));
            validatePurchaseBillInvoiceInfo("Total Discount", String.valueOf(expectedTotalDiscount));
            validatePurchaseBillInvoiceInfo("Total Amt. Before Tax", String.valueOf(expectedTotalAmtBeforeTax));
            validatePurchaseBillInvoiceInfo("Total Tax", String.valueOf(expectedTotalTax));
            validatePurchaseBillInvoiceInfo("Other Charges", String.valueOf(expectedOtherCharges));
            validatePurchaseBillInvoiceInfo("Net Amount", String.valueOf(expectedNetAmount));

            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseBill.button_saveChanges), "Clicked <b>Save Changes</b> button in Purchase Bill");
            pbCreatedAt=getCurrentDateTime();
            Cls_Generic_Methods.customWait(8);
            boolean rowSelected = selectRowWithInvoiceNumber(billNumber);
            if (rowSelected) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.text_rhs_purchaseBillNo, 10);
                purchaseBill_no = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_purchaseBillNo);
                String rhsStatus = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_purchaseBillStatus);
                String rhsVendor = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_purchaseBillVendor);
                String rhsVendorGSTNo = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_purchaseBillVendorGSTno);
                String rhsPbCreatedBy = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_purchaseBillCreatedByUser);
                String rhsPbCreatedAt = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_purchaseBillCreatedAt);

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseBill.text_rhs_searchInvoiceNo,billNumber),"Entered <b>"+billNumber+"</b> in RHS Search Invoice");
                Cls_Generic_Methods.customWait();

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox),
                        " Search Icon Clicked In Search Box");              //  Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseBill.text_rhs_searchInvoiceNo," ");
               // Cls_Generic_Methods.customWait();
               // Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseBill.text_rhs_searchInvoiceNo,""+Keys.BACK_SPACE);
                Cls_Generic_Methods.customWait(4);
                while(true){
                    if(oPage_PurchaseBill.list_purchaseBillRowList.size()!=1){
                        Cls_Generic_Methods.customWait();
                    }else{
                        break;
                    }
                }
                int rowCount=oPage_PurchaseBill.list_purchaseBillRowInvoicenoList.size();
                if(rowCount==1){
                    String value=Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_purchaseBillRowInvoicenoList.get(0));
                    m_assert.assertTrue(value.equals(billNumber), "Validated --> Purchase Bill RHS Search --> Found Purchase Bill with Invoice No = <b>" + value + "</b>");

                }else{
                    m_assert.assertFatal("Unable to validate Purchase Bill RHS search");
                }

                m_assert.assertTrue(!purchaseBill_no.isBlank(), "Validated --> Purchase Bill created --> Purchase Bill No = <b>" + purchaseBill_no + "</b>");
                m_assert.assertTrue(rhsVendor.contains(vendorName), "Validated --> Purchase Bill RHS --> Vendor = <b>" + rhsVendor + "</b>");
                m_assert.assertTrue(rhsVendorGSTNo.equals(vendorGSTno), "Validated --> Purchase Bill RHS --> Vendor GST No = <b>" + rhsVendorGSTNo + "</b>");
                m_assert.assertTrue(rhsPbCreatedBy.equals(expectedLoggedInUser), "Validated --> Purchase Bill RHS --> PB Created By user = <b>" + rhsPbCreatedBy + "</b>");
                m_assert.assertTrue(rhsPbCreatedAt.contains(pbCreatedAt.split("|")[0]), "Validated --> Purchase Bill RHS --> PB Created At = <b>" + rhsPbCreatedAt + "</b>");
                m_assert.assertTrue(rhsStatus.equalsIgnoreCase("open"), "Validated -->Purchase Bill RHS --> Status = <b>" + rhsStatus + "</b>");

            } else {
                m_assert.assertFatal("Unable to find Purchase Bill with invoice no =" + billNumber);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to create/Validate Purchase Bill");
        }
    }

    private boolean selectRowWithPurchaseBillNumber(String purchaseBillNumber) {
        boolean status = false;
        try {
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PurchaseBill.list_purchaseBillHeaderList, 10);
            int rowNo = 0;
            for (WebElement row : oPage_PurchaseBill.list_purchaseBillRowList) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    String sPB_number = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_purchaseBillRowPBnoList.get(rowNo));
                    if (sPB_number.equalsIgnoreCase(purchaseBillNumber)) {
                        Cls_Generic_Methods.clickElement(row);
                        Cls_Generic_Methods.customWait(5);
                        status = true;
                        break;
                    }
                }
                rowNo++;
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to select purchase Bill -->PB No=" + purchaseBillNumber);
        }
        return status;
    }

    private boolean selectRowWithInvoiceNumber(String invoiceNumber) {
        boolean status = false;
        try {
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PurchaseBill.list_purchaseBillHeaderList, 10);
            int rowNo = 0;
            for (WebElement row : oPage_PurchaseBill.list_purchaseBillRowList) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    String sInvoice_number = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.list_purchaseBillRowInvoicenoList.get(rowNo));
                    if (sInvoice_number.equalsIgnoreCase(invoiceNumber)) {
                        Cls_Generic_Methods.clickElement(row);
                        status = true;
                        break;
                    }
                }
                rowNo++;
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to select purchase Bill -->Invoice No=" + invoiceNumber);
        }
        return status;
    }

    public boolean selectByOptionsPurchaseBills(WebElement selectElement, String optionToSelect) {
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

    public boolean selectFromListBasedOnStatus(String status){
        boolean flag=false;
        try{
            List<String> purchaseBillHeaderList = new ArrayList<String>();

            for (WebElement purchaseHeaderList : oPage_PurchaseBill.list_purchaseBillHeaderList) {
                purchaseBillHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
            }
            for (WebElement row : oPage_PurchaseBill.list_purchaseBillRowList) {

                List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));
                String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseBillHeaderList.indexOf("Status")));

                if (purchaseStatus.equalsIgnoreCase("open")) {
                    Cls_Generic_Methods.clickElementByJS(driver, row);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.button_edit, 10);
                    purchaseBill_no=Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_purchaseBillNo);
                    flag = true;
                    break;
                }
            }
            if(!flag){
                createPurchaseGrn("Bill");
                Cls_Generic_Methods.customWait(5);
                validateAndCreatePurchaseBill();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;

    }

    private String getCurrentDateTime(){

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | hh:mm a");
        Date date = new Date();
        //  07-06-2023 | 01:09 PM
        String date1= dateFormat.format(date);
        return  date1;
    }



}
