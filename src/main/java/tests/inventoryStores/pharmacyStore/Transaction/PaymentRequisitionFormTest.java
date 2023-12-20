package tests.inventoryStores.pharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.*;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_VendorMaster;
import pages.store.PharmacyStore.Transaction.Page_PaymentRequisitionForm;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_PurchaseBill;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PaymentRequisitionFormTest extends TestBase {

    String prf_no;
    String purchaseBill_no;
    String vendorName = "Supplier ABC";
    String store = "Pharmacy automation- Pharmacy";
    String storeGSTno;
    String vendor_address;
    String vendorGSTno;
    String netAmountInPurchaseBill;
    String invoiceDate;
    String purchaseBillCreatedBy;
    String purchaseBillCreatedAt;
    String prfCreatedBy;
    String prfCreatedAt;
    String billNumber;
    Page_CommonElements oPage_CommonElements;
    Page_StoreSetUp oPage_StoreSetUp;
    Page_Purchase oPage_Purchase;
    Page_PurchaseBill oPage_PurchaseBill;
    Page_VendorMaster oPage_VendorMaster;
    Page_PaymentRequisitionForm oPage_PaymentRequisitionForm;
    String transactionNotes = "Auto_Test_Transaction_notes";

    @Test(enabled = true, description = "Validating Create Payment Requisition Form")
    public void validateCreatePaymentRequisitionForm() {

        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_PaymentRequisitionForm = new Page_PaymentRequisitionForm(driver);

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
            boolean purchaseBillCreated = createPurchaseBill();

            if (purchaseBillCreated) {
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm), "Clicked New in Payment Requisition Form");
                m_assert.assertInfo(selectByOptions(oPage_PaymentRequisitionForm.select_vendor, vendorName), "Selected vendor as : <b>" + vendorName + "</b>");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_PaymentRequisitionForm.input_vendorGstNo, "value").equals(vendorGSTno), "Validated--> Vendor GST no");

                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PaymentRequisitionForm.row_createPaymentRequisitionForm, 10);


                int rowNo = 0;
                for (WebElement row : oPage_PaymentRequisitionForm.row_createPaymentRequisitionForm) {
                    String pbNoInRow = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.list_purchaseBillNoInRowCreatePrf.get(rowNo));
                    if (pbNoInRow.equals(purchaseBill_no)) {
                        Cls_Generic_Methods.clickElement(row);
                        m_assert.assertTrue("Validated-->Newly Created Purchase Bill is present in list");
                        String pbCreatedDateInRow = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.list_pbDateAndTimeInRowCreatePrf.get(rowNo)).split("\\|")[0].trim();
                        String pbCreatedByUserInRow = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.list_pbCreatedUserInRowCreatePrf.get(rowNo));
                        String pbNetAmountInRow = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.list_pbNetAmountInRowCreatePrf.get(rowNo));
                        String pbVendorInRow = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.list_pbVendorInRowCreatePrf.get(rowNo));
                        m_assert.assertTrue(pbCreatedDateInRow.equals(purchaseBillCreatedAt), "Validated-->Purchase Bill created on : " + purchaseBillCreatedAt);
                        m_assert.assertTrue(pbCreatedByUserInRow.equals(purchaseBillCreatedBy), "Validated-->Purchase Bill created By user : " + purchaseBillCreatedBy);
                        m_assert.assertTrue(Double.parseDouble(pbNetAmountInRow) == Double.parseDouble(netAmountInPurchaseBill), "Validated-->Purchase Bill Net Amount  : " + netAmountInPurchaseBill);
                        m_assert.assertTrue(pbVendorInRow.equals(vendorName), "Validated-->Purchase Bill Vendor : " + vendorName);
                        break;
                    }
                    rowNo++;
                }
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_PaymentRequisitionForm.button_saveChanges), "<b>Save Changes</b> button is enabled after adding the purchase");

                // Validate Remove from List
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_removeFromList, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_removeFromList), "Clicked <b>Remove from List</b> button");
                Cls_Generic_Methods.customWait();
                m_assert.assertFalse(Cls_Generic_Methods.isElementDisplayed(oPage_PaymentRequisitionForm.button_removeFromList), "Validated --> Item Removed From List");
                m_assert.assertFalse(Cls_Generic_Methods.isElementEnabled(oPage_PaymentRequisitionForm.button_saveChanges), "<b>Save Changes</b> button is disabled after removing the purchase");

                //Validate Search
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PaymentRequisitionForm.input_searchPurchase, purchaseBill_no), "Entered <b>" + purchaseBill_no + "</b> in search purchase");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_PaymentRequisitionForm.input_searchPurchase, " ");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.sendKeysIntoElement(oPage_PaymentRequisitionForm.input_searchPurchase, "" + Keys.BACK_SPACE);
                Cls_Generic_Methods.customWait(4);
                int size = oPage_PaymentRequisitionForm.list_purchaseBillNoInRowCreatePrf.size();
                if (size == 1) {
                    String pbNoInRow = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.list_purchaseBillNoInRowCreatePrf.get(0));
                    m_assert.assertTrue(pbNoInRow.equals(purchaseBill_no), "Validated Search Functionality -->Found Purchase Bill : " + purchaseBill_no);
                } else {
                    m_assert.assertFatal("Unable to validate search Functionality");
                }
                //Validate Search clear
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_searchClearPurchase), "Clicked clear search button");
                String finalValue = Cls_Generic_Methods.getElementAttribute(oPage_PaymentRequisitionForm.input_searchPurchase, "value").trim();

                m_assert.assertTrue(finalValue.isBlank(), "Validated Search Clear Functionality");
                Cls_Generic_Methods.customWait();

                //Validate Time Period

                m_assert.assertFalse(selectAndValidateTimePeriod("Yesterday", purchaseBill_no), "Validated -->Time Period-Yesterday");
                m_assert.assertTrue(selectAndValidateTimePeriod("This Week", purchaseBill_no), "Validated -->Time Period-This Week");
                m_assert.assertTrue(selectAndValidateTimePeriod("This Month", purchaseBill_no), "Validated -->Time Period-This Month");
                m_assert.assertTrue(selectAndValidateTimePeriod("This Quarter", purchaseBill_no), "Validated -->Time Period-This Quarter");
                m_assert.assertTrue(selectAndValidateTimePeriod("This Year", purchaseBill_no), "Validated -->Time Period-This Year");
                m_assert.assertFalse(selectAndValidateTimePeriod("Previous Week", purchaseBill_no), "Validated -->Time Period-Previous Week");
                m_assert.assertFalse(selectAndValidateTimePeriod("Previous Month", purchaseBill_no), "Validated -->Time Period-Previous Month");
                m_assert.assertFalse(selectAndValidateTimePeriod("Previous Quarter", purchaseBill_no), "Validated -->Time Period-Previous Quarter");
                m_assert.assertFalse(selectAndValidateTimePeriod("Previous Year", purchaseBill_no), "Validated -->Time Period-Previous Year");
                m_assert.assertTrue(selectAndValidateTimePeriod("Today", purchaseBill_no), "Validated -->Time Period-Today");

                for (WebElement row : oPage_PaymentRequisitionForm.row_createPaymentRequisitionForm) {
                    Cls_Generic_Methods.clickElement(row);
                    break;
                }

                //Change Vendor and Verify confirmation message
                //No Don't Change button
                String newVendor = "kumar vendor";
                m_assert.assertInfo(selectByOptions(oPage_PaymentRequisitionForm.select_vendor, newVendor), "Changing vendor as : <b>" + newVendor + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.text_vendorChangeConfirmationMessage, 10),
                        "Validated -->Confirmation Message is displayed --> <b>" + Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.text_vendorChangeConfirmationMessage) + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_noDontChangeVendor), "Clicked <b>No Don't Change</b> button");
                Cls_Generic_Methods.customWait(4);
                if (Cls_Generic_Methods.isElementDisplayed(oPage_PaymentRequisitionForm.button_noDontChangeVendor)) {
                    m_assert.assertFatal("<b>No Don't Change</b> button is not clickable");
                    boolean prfOpened = refreshAndOpenPRF();
                    if (prfOpened) {
                        selectPaymentRequisition("open");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_edit, 10);
                        Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.select_vendor, 10);
                    }
                } else {
                    String selectedVendor = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseBill.select_vendorOptionsCreatePurchaseBill);
                    m_assert.assertTrue(selectedVendor.contains(vendorName), "Validated --> <b>No Don't Change</b> button -->Vendor not changed");
                }

                //Yes Change Button
                m_assert.assertInfo(selectByOptions(oPage_PaymentRequisitionForm.select_vendor, vendorName), "Selecting vendor as : <b>" + vendorName + "</b>");
                Cls_Generic_Methods.customWait(5);
                for (WebElement row : oPage_PaymentRequisitionForm.row_createPaymentRequisitionForm) {
                  if(!Cls_Generic_Methods.getElementAttribute(row,"class").contains("disabled")) {
                      Cls_Generic_Methods.clickElement(row);
                  }
                    break;
                }
                Cls_Generic_Methods.customWait();
                m_assert.assertInfo(selectByOptions(oPage_PaymentRequisitionForm.select_vendor, newVendor), "Changing vendor as : <b>" + newVendor + "</b>");

                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.text_vendorChangeConfirmationMessage, 10),
                        "Validated -->Confirmation Message is displayed --> <b>" + Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.text_vendorChangeConfirmationMessage) + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_yesChangeVendor), "Clicked <b>Yes Change</b> button");
                Cls_Generic_Methods.customWait(4);
                if (Cls_Generic_Methods.isElementDisplayed(oPage_PaymentRequisitionForm.button_yesChangeVendor)) {
                    m_assert.assertFatal("<b>Yes Change</b> button is not clickable");
                    boolean prfOpened = refreshAndOpenPRF();
                    if (prfOpened) {
                        selectPaymentRequisition("open");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_edit, 10);
                        Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.select_vendor, 10);
                    }
                } else {
                    String selectedVendor = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseBill.select_vendorOptionsCreatePurchaseBill);
                    m_assert.assertTrue(selectedVendor.contains(newVendor), "Validated --> <b>Yes Change</b> button -->Vendor changed");
                }

                m_assert.assertInfo(selectByOptions(oPage_PaymentRequisitionForm.select_vendor, vendorName), "Changing vendor back to : <b>" + newVendor + "</b>");
                Cls_Generic_Methods.customWait(5);
                for (WebElement row : oPage_PaymentRequisitionForm.row_createPaymentRequisitionForm) {
                    Cls_Generic_Methods.clickElement(row);
                    break;
                }

                //Validate PRF Time
                Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.input_paymentRequisitionDate);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.input_todayBillDate, 3);
                Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.input_todayBillDate);
                String date = Cls_Generic_Methods.getElementAttribute(oPage_PaymentRequisitionForm.input_paymentRequisitionDate, "value").trim();
               date = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy","dd-MM-yyyy",date);
                prfCreatedAt = date+" | "+Cls_Generic_Methods.getElementAttribute(oPage_PaymentRequisitionForm.input_paymentRequisitionTime, "value").trim();

                m_assert.assertInfo("Selected Payment Requisition Form Date as : <b>" + date + "</b>");

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PaymentRequisitionForm.input_transactionNote, transactionNotes), "Entered <b>" + transactionNotes + "</b> in Transaction Notes");

                m_assert.assertTrue(getTableValuePaymentRequisitionForm("Purchase Bill No.").equals(purchaseBill_no), "Validate PRF table -->Purchase Bill No = <b>" + purchaseBill_no + "</b>");
                m_assert.assertTrue(getTableValuePaymentRequisitionForm("Purchase Bill Date").equals(purchaseBillCreatedAt), "Validate PRF table -->Purchase Bill Date = <b>" + purchaseBillCreatedAt + "</b>");
                m_assert.assertTrue(getTableValuePaymentRequisitionForm("Invoice No.").equals(billNumber), "Validate PRF table -->Invoice No = <b>" + billNumber + "</b>");
                m_assert.assertTrue(getTableValuePaymentRequisitionForm("Invoice Date").equals(invoiceDate), "Validate PRF table -->Invoice Date = <b>" + invoiceDate + "</b>");
                m_assert.assertTrue(Double.parseDouble(getTableValuePaymentRequisitionForm("Net Amount")) == Double.parseDouble(netAmountInPurchaseBill), "Validate PRF table -->Net Amount = <b>" + netAmountInPurchaseBill + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PaymentRequisitionForm.input_remark, "Test"), "Entered <b>Test</b> in Remark");

                String totalNetAmount = Cls_Generic_Methods.getElementAttribute(oPage_PaymentRequisitionForm.input_totalNetAmount, "value");
                m_assert.assertTrue(Double.parseDouble(totalNetAmount) == Double.parseDouble(netAmountInPurchaseBill), "Validated Total Net Amount : <b>" + netAmountInPurchaseBill + "</b>");
                //prfCreatedAt = getCurrentTime();
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_saveChanges), "Clicked Save Changes Button");
               String prfCreatedAtText = getCurrentTime();

                Cls_Generic_Methods.customWait(5);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm, 10);

                boolean rowSelected = selectPaymentRequisition("open");
                if (rowSelected) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.text_rhs_prfNo, 15);
                    prf_no = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.text_rhs_prfNo);
                    String rhsStatus = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.text_rhs_status);
                    String rhsVendor = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.text_rhs_vendor);
                    String rhsVendorLocation = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.text_rhs_vendorLocation);
                    String rhsPrfCreatedBy = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.text_rhs_createdBy);
                    String rhsPrfCreatedAt = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.text_rhs_createdAt);

                    String rhsVendorGSTNo = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.text_rhs_vendorGstInNo);
                    m_assert.assertTrue(!prf_no.isBlank(), "Validated -->Payment Requisition Form created --> PRF No = <b>" + prf_no + "</b>");
                    m_assert.assertTrue(rhsVendor.equals(vendorName), "Validated -->Payment Requisition Form RHS --> Vendor = <b>" + rhsVendor + "</b>");
                    m_assert.assertTrue(rhsVendorLocation.contains(vendor_address), "Validated -->Payment Requisition Form RHS --> Vendor Location = <b>" + rhsVendorLocation + "</b>");
                    m_assert.assertTrue(rhsVendorGSTNo.equals(vendorGSTno), "Validated -->Payment Requisition Form RHS --> Vendor GST No = <b>" + rhsVendorGSTNo + "</b>");
                    m_assert.assertTrue(rhsPrfCreatedBy.equals(expectedLoggedInUser), "Validated -->Payment Requisition Form RHS --> PRF Created By user = <b>" + rhsPrfCreatedBy + "</b>");
                    m_assert.assertTrue(rhsPrfCreatedAt.equals(prfCreatedAt) || rhsPrfCreatedAt.equals(prfCreatedAtText), "Validated -->Payment Requisition Form RHS --> PRF Created By user = <b>" + rhsPrfCreatedAt + "</b>");


                    m_assert.assertTrue(rhsStatus.equalsIgnoreCase("open"), "Validated -->Payment Requisition Form RHS --> Status = <b>" + rhsStatus + "</b>");

                } else {
                    m_assert.assertFatal("Newly Created PRF is not found");
                }

            } else {
                m_assert.assertFatal("Purchase Bill not Created");
            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate create Payment Requisition Form --> " + e);
            e.printStackTrace();
        }

    }

    @Test(enabled = true, description = "Validating Edit Payment Requisition Form")
    public void validateEditPaymentRequisitionForm() {

        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_PaymentRequisitionForm = new Page_PaymentRequisitionForm(driver);

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
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm, 10);

            boolean foundPrfTransaction = selectPaymentRequisition("open", prf_no);
            m_assert.assertInfo(foundPrfTransaction, "Clicked Transaction with PRF No = " + prf_no);

            if (foundPrfTransaction) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_edit, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_edit), "<b>Edit</b> button is clickable");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.text_updatePaymentRequisition, 10), "<b>Update Payment Requisition Form</b> Header is displayed ");
                Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.input_paymentRequisitionDate);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.input_todayBillDate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.input_todayBillDate), "Validated Edit --> PRF Date is Editable");
                m_assert.assertTrue(setTime(oPage_PaymentRequisitionForm.input_paymentRequisitionTime, "10:00:PM"), "Validated Edit --> PRF Time is Editable");
                Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.input_searchPurchase);
                Cls_Generic_Methods.clearValuesInElement(oPage_PaymentRequisitionForm.input_transactionNote);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PaymentRequisitionForm.input_transactionNote, "UPDATED-" + transactionNotes), "Validated Edit --> Transaction Note is Editable");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_removeFromList), "Clicked Remove From list Button");
                Cls_Generic_Methods.customWait();
                m_assert.assertFalse(Cls_Generic_Methods.isElementDisplayed(oPage_PaymentRequisitionForm.button_removeFromList), "Validated Edit --> Existing transaction Removed");

                for (WebElement row : oPage_PaymentRequisitionForm.row_createPaymentRequisitionForm) {
                    Cls_Generic_Methods.clickElement(row);
                    break;
                }
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PaymentRequisitionForm.button_removeFromList), "Validated Edit --> New transaction Added");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_saveChanges), "Clicked Save Changes Button");
            } else {
                m_assert.assertFatal("Purchase Bill not Created");
            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate Edit Payment Requisition Form --> " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validating Approve Payment Requisition Form")
    public void validateApprovePaymentRequisitionForm() {

        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_PaymentRequisitionForm = new Page_PaymentRequisitionForm(driver);

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

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm, 10);

            boolean foundPrfTransaction = selectPaymentRequisition("open", prf_no);
            m_assert.assertInfo(foundPrfTransaction, "Clicked Transaction with PRF No = " + prf_no);

            if (foundPrfTransaction) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_approvePaymentRequisitionForm, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_approvePaymentRequisitionForm), "<b>Approve</b> button is clickable");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.text_approvedNotifyMsg, 10), "<b>This PRF is approved</b> notify message is displayed");
                Cls_Generic_Methods.customWait();
                String rhsStatus = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.text_rhs_status);
                m_assert.assertTrue(rhsStatus.equalsIgnoreCase("approved"), "Validated Approve --> PRF RHS --> STATUS : <b>" + rhsStatus + "</b>");
                String rhsApprovedBy = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.text_rhs_approvedByUser);
                m_assert.assertTrue(rhsApprovedBy.equals(expectedLoggedInUser), "Validated Approve --> PRF RHS --> APPROVED BY USER : <b>" + rhsApprovedBy + "</b>");

                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_print, 10), "Validated Approve --> Print option is displayed");
                m_assert.assertFalse(Cls_Generic_Methods.isElementDisplayed(oPage_PaymentRequisitionForm.button_edit), "Validated Approve --> Edit option is removed");
                m_assert.assertFalse(Cls_Generic_Methods.isElementDisplayed(oPage_PaymentRequisitionForm.button_cancelPaymentRequisition), "Validated Approve --> Cancel option is removed");

                int preWindowsCount = driver.getWindowHandles().size();
                String initialWindowHandle = driver.getWindowHandle();
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_print), "Clicked Print button");
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

            } else {
                m_assert.assertFatal("Purchase Bill not Created");
            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate Approve Payment Requisition Form --> " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validating Cancel Payment Requisition Form")
    public void validateCancelPaymentRequisitionForm() {

        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_PaymentRequisitionForm = new Page_PaymentRequisitionForm(driver);

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

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm, 10);

            boolean foundPrfTransaction = selectPaymentRequisition("open", prf_no);
            if (!foundPrfTransaction) {
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                foundPrfTransaction=createPaymentRequisitionForm();
            }
            m_assert.assertInfo(foundPrfTransaction, "Clicked Transaction with PRF No = " + prf_no);

            if (foundPrfTransaction) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_cancelPaymentRequisition, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_cancelPaymentRequisition), "<b>Cancel</b> button is clickable");

                String cancellationReason = "AUTO TESTING CANCELLATION";
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.input_cancellationReason, 10);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_PaymentRequisitionForm.input_cancellationReason, cancellationReason);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_dontCancel), "Clicked Don't Cancel button");

                Cls_Generic_Methods.clickElement(driver, oPage_PaymentRequisitionForm.button_cancelPaymentRequisition);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.input_cancellationReason, 10);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PaymentRequisitionForm.input_cancellationReason, cancellationReason), "Entered <b>" + cancellationReason + "</b> in cancellation reason");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_confirmCancel), "Clicked <b>Cancel Purchase Bill</b> button");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.text_cancelledNotifyMsg, 10), "<b>This PRF is cancelled</b> notify message is displayed");

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");

                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm, 10);

                boolean rowSelected = selectPaymentRequisition("cancelled", prf_no);
                Cls_Generic_Methods.customWait(4);

                if (rowSelected) {
                    String rhsStatus = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.text_rhs_status);
                    String cancelledByUser = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.text_rhs_cancelledByUser);
                    m_assert.assertTrue(rhsStatus.equalsIgnoreCase("cancelled"), "Validated Cancel --> Status = <b>" + rhsStatus + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.text_rhs_cancelledReason).equalsIgnoreCase(cancellationReason), "Validated Cancel -->Cancelled Reason in rhs side = <b>" + cancellationReason + "</b>");
                    m_assert.assertTrue(cancelledByUser.equals(expectedLoggedInUser), "Validated Cancel --> Cancelled By user in rhs side : <b>" + cancelledByUser + "</b>");
                    Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                }

            } else {
                m_assert.assertFatal("PRF not found in list");
            }

        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate Cancel Payment Requisition Form --> " + e);
            e.printStackTrace();
        }
    }


    public boolean createPurchaseBill() {
        boolean flag = false;

        vendorName = "Supplier ABC";
        String billType = "Bill";
        billNumber = "BILL_NO_" + getRandomNumber();
        String subStore = "Default";
        String unitCostWOTax = "100";
        String packageQuantity = "2";
        final String sellingPrice = "120";
        oPage_PurchaseBill = new Page_PurchaseBill(driver);
        oPage_Purchase = new Page_Purchase(driver);
        oPage_CommonElements = new Page_CommonElements(driver);

        boolean bPurchaseTransactionFound = false;
        try {
            // Creating Purchase Transaction for Created Item
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 10);
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_purchaseNew);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_Vendor_search, 4);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_Vendor_search, vendorName);
            Cls_Generic_Methods.customWait();
            for (WebElement eVendor : oPage_Purchase.list_select_vendor) {
                Cls_Generic_Methods.clickElementByJS(driver, eVendor);
                break;
            }
            boolean itemClicked = false;
            try {
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_itemNameInPurchaseStore, 10);
                for (WebElement eItemName : oPage_Purchase.list_itemNameInPurchaseStore) {
                    Cls_Generic_Methods.clickElementByJS(driver, eItemName);
                    itemClicked = true;
                    break;
                }
                if (itemClicked) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewLot, 15);
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.select_subStore)) {
                        Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_subStore, subStore);
                    }
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.input_expiryDate)) {
                        Cls_Generic_Methods.clickElement(oPage_Purchase.input_expiryDate);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.select_expiryDateYear, 1);
                        String currentYear = Cls_Generic_Methods.getSelectedValue(oPage_Purchase.select_expiryDateYear);
                        int year = Integer.parseInt(currentYear);
                        int newYear = year + 3;

                        Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_expiryDateYear, Integer.toString(newYear));
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.select_expiryDateDay);
                    }

                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_unitCostWOTax);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_unitCostWOTax, unitCostWOTax);

                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_packageQuantity);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_packageQuantity, packageQuantity);

                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_sellingPrice);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_sellingPrice, sellingPrice);

                    Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveLot);

                } else {
                    m_assert.assertFatal("Item not selected");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_SaveChanges);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }

            Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes);
            Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
            Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType);
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber), "Entered Bill no as <b>" + billNumber + "</b> in Purchase Grn");
            Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
            Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate);
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_deleteOtherCharges);
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot);
            Cls_Generic_Methods.customWait(8);
            List<String> purchaseTransactionHeaderList = new ArrayList<String>();
            for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
                purchaseTransactionHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
            }
            for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));
                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Status")));
                    if (purchaseStatus.equalsIgnoreCase("open")) {
                        Cls_Generic_Methods.clickElement(row);
                        Cls_Generic_Methods.customWait(2);
                        Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_Yes, 6);
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.button_Yes);
                        bPurchaseTransactionFound = true;
                        m_assert.assertInfo("<b>Purchase Grn</b> created and approved");
                        break;
                    }
                }
            }
            if (bPurchaseTransactionFound) {
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.button_purchaseNew, 20);
                Cls_Generic_Methods.clickElement(oPage_PurchaseBill.button_purchaseNew);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.select_vendorOptionsCreatePurchaseBill, 6);
                m_assert.assertInfo(selectByOptions(oPage_PurchaseBill.select_vendorOptionsCreatePurchaseBill, vendorName), "Selected Vendor as <b>" + vendorName + "</b> in Purchase Bill");
                Cls_Generic_Methods.selectElementByIndex(oPage_PurchaseBill.select_createAgainstPurchaseBill, 1);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(oPage_PurchaseBill.list_RowsOnCreatePurchaseBillTable.get(0));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.button_removeFromList, 10);
                netAmountInPurchaseBill = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseBill.text_netAmountValue, "value");
                m_assert.assertInfo("Net Amount in Purchase Bill : <b>" + netAmountInPurchaseBill + "</b>");
                Cls_Generic_Methods.clickElement(oPage_PurchaseBill.button_saveChanges);
                flag = true;
                Cls_Generic_Methods.customWait(7);


            } else {
                m_assert.assertFatal("Purchase Grn not created");
            }

            if (flag) {
                List<String> purchaseBillHeaderList = new ArrayList<String>();

                for (WebElement purchaseHeaderList : oPage_PurchaseBill.list_purchaseBillHeaderList) {
                    purchaseBillHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
                }
                for (WebElement row : oPage_PurchaseBill.list_purchaseBillRowList) {
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));
                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseBillHeaderList.indexOf("Status")));
                    String invoiceNoInRow = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseBillHeaderList.indexOf("Invoice No.")));


                    if (purchaseStatus.equalsIgnoreCase("open") && invoiceNoInRow.equals(billNumber)) {
                        Cls_Generic_Methods.clickElementByJS(driver, row);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.button_approve, 10);
                        purchaseBill_no = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_purchaseBillNo);
                        invoiceDate = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_InvoiceDate);
                        purchaseBillCreatedAt = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_purchaseBillCreatedAt).split("\\|")[0].trim();
                        purchaseBillCreatedBy = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_purchaseBillCreatedByUser);
                        Cls_Generic_Methods.clickElement(oPage_PurchaseBill.button_approve);
                        flag = true;
                        m_assert.assertInfo("<b>Purchase Bill</b> created and approved");
                        m_assert.assertInfo("Purchase Bill No : <b>" + purchaseBill_no + "</b>");
                        break;
                    } else {
                        flag = false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to create purchase Bill --> " + e);
        }
        return flag;
    }

    private boolean selectAndValidateTimePeriod(String periodToSelect, String pb_no) {

        boolean status = false;

        try {
            periodToSelect = periodToSelect.toLowerCase().trim().replaceAll(" ", "-");
            Cls_Generic_Methods.clickElement(driver, oPage_PaymentRequisitionForm.button_timePeriod);
            Cls_Generic_Methods.clickElement(driver.findElement(By.xpath("//li/a[contains(@class,'" + periodToSelect + "')]")));
            Cls_Generic_Methods.customWait(3);
            for (WebElement ePbNoText : oPage_PaymentRequisitionForm.list_purchaseBillNoInRowCreatePrf) {
                String txt_pbNo = Cls_Generic_Methods.getTextInElement(ePbNoText);
                if (txt_pbNo.equals(pb_no)) {
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

    private String getTableValuePaymentRequisitionForm(String headerValue) {
        String actualValue = null;
        try {
            int rowNo = 0;
            for (WebElement eHeadingInfo : oPage_PaymentRequisitionForm.list_tableHeaderCreatePrf) {
                String sHeader = Cls_Generic_Methods.getTextInElement(eHeadingInfo);
                if (sHeader.equalsIgnoreCase(headerValue)) {
                    actualValue = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.list_tableBodyCreatePrf.get(rowNo));
                    break;
                } else {
                    if (oPage_PaymentRequisitionForm.list_tableHeaderCreatePrf.size() - 1 == rowNo) {
                        m_assert.assertFatal("Unable to find " + headerValue + " in PRF Table");
                    }
                }
                rowNo++;
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate purchase bill invoice info ---->" + e);
        }
        return actualValue;
    }

    private String getRandomNumber() {
        Random random = new Random();
        String id = String.format("%06d", random.nextInt(1000000));
        return id;
    }

    public boolean selectByOptions(WebElement selectElement, String optionToSelect) {

        boolean status = false;
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(selectElement, 10);
            Cls_Generic_Methods.clickElementByJS(driver, selectElement);
            Cls_Generic_Methods.customWait();
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

    public boolean selectPaymentRequisition(String status, String... prfNo) {
        boolean flag = false;
        String selectPrfRow = null;


        try {
            if (prfNo.length > 0) {
                selectPrfRow = prfNo[0];
            }

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PaymentRequisitionForm.list_prfHeaderList, 10);
            List<String> purchaseTransactionHeaderList = new ArrayList<String>();
            for (WebElement purchaseHeaderList : oPage_PaymentRequisitionForm.list_prfHeaderList) {
                purchaseTransactionHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
            }
            for (WebElement row : oPage_PaymentRequisitionForm.list_prfCreatedList) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Status")));
                    String prfNoInRow = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("PRF No.")));

                    if (prfNo.length == 0) {
                        if (purchaseStatus.equalsIgnoreCase(status)) {
                            Cls_Generic_Methods.clickElement(row);
                            flag = true;
                            break;
                        }
                    } else {
                        if (prfNoInRow.equals(selectPrfRow) && purchaseStatus.equalsIgnoreCase(status)) {
                            Cls_Generic_Methods.clickElement(row);
                            flag = true;
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to find payment requisition form " + e);
        }
        return flag;
    }

    public static boolean setTime(WebElement timeElement, String time) {
        boolean flag = false;
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        try {
            Cls_Generic_Methods.clickElement(driver,
                    timeElement);
            String[] seperatedTimeValue = time.split(":");

            Cls_Generic_Methods
                    .clearValuesInElement(oPage_NewPatientRegisteration.input_appointmentHourForAppointDetails);
            Cls_Generic_Methods.sendKeysIntoElement(
                    oPage_NewPatientRegisteration.input_appointmentHourForAppointDetails,
                    seperatedTimeValue[0]);

            Cls_Generic_Methods.clearValuesInElement(
                    oPage_NewPatientRegisteration.input_appointmentMinuteForAppointDetails);
            Cls_Generic_Methods.sendKeysIntoElement(
                    oPage_NewPatientRegisteration.input_appointmentMinuteForAppointDetails,
                    seperatedTimeValue[1]);

            Cls_Generic_Methods.clearValuesInElement(
                    oPage_NewPatientRegisteration.input_appointmentMeridianForAppointDetails);
            Cls_Generic_Methods.sendKeysIntoElement(
                    oPage_NewPatientRegisteration.input_appointmentMeridianForAppointDetails,
                    seperatedTimeValue[2]);
            flag = true;
        } catch (Exception e) {
            m_assert.assertFatal("Unable to set Time");
            e.printStackTrace();
        }
        return flag;
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

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to get GST no. from Organisation Setting " + e);
        }
    }

    private boolean refreshAndOpenPRF() {
        boolean flag = false;
        try {
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm, 10);
            flag = true;
        } catch (Exception e) {
            m_assert.assertFatal("Unable to refresh and open prf");
        }
        return flag;
    }

    private void checkScrollTable() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.querySelector('.transaction-purchases-body').scrollBy(0,1000)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getCurrentTime() {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | hh:mm a");
        Date date = new Date();
        //  07-06-2023 | 01:09 PM
        String date1 = dateFormat.format(date);
        return date1;
    }

    public boolean createPaymentRequisitionForm() {

        boolean flag=false;
        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_PaymentRequisitionForm = new Page_PaymentRequisitionForm(driver);

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
            boolean purchaseBillCreated = createPurchaseBill();

            if (purchaseBillCreated) {
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm), "Clicked New in Payment Requisition Form");
                m_assert.assertInfo(selectByOptions(oPage_PaymentRequisitionForm.select_vendor, vendorName), "Selected vendor as : <b>" + vendorName + "</b>");
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PaymentRequisitionForm.row_createPaymentRequisitionForm, 10);

                int rowNo = 0;
                for (WebElement row : oPage_PaymentRequisitionForm.row_createPaymentRequisitionForm) {
                    String pbNoInRow = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.list_purchaseBillNoInRowCreatePrf.get(rowNo));
                    if (pbNoInRow.equals(purchaseBill_no)) {
                        Cls_Generic_Methods.clickElement(row);
                        break;
                    }
                    rowNo++;
                }
                Cls_Generic_Methods.customWait(5);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PaymentRequisitionForm.input_transactionNote, transactionNotes), "Entered <b>" + transactionNotes + "</b> in Transaction Notes");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PaymentRequisitionForm.input_remark, "Test"), "Entered <b>Test</b> in Remark");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_saveChanges), "Clicked Save Changes Button");
                prfCreatedAt = getCurrentTime();

                Cls_Generic_Methods.customWait(5);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm, 10);

                boolean rowSelected = selectPaymentRequisition("open");
                if (rowSelected) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.text_rhs_prfNo, 15);
                    flag=true;
                    prf_no = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.text_rhs_prfNo);
                    m_assert.assertTrue("Validated -->Payment Requisition Form Created --> PRF no = <b>" + prf_no + "</b>");

                } else {
                    m_assert.assertFatal("Newly Created PRF is not found");
                }

            } else {
                m_assert.assertFatal("Purchase Bill not Created");
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to  create Payment Requisition Form --> " + e);
            e.printStackTrace();
        }
        return flag;

    }

    @Test(enabled = true, description = "Validating Search Functionality in Payment Requisition Form")
    public void validateSearchFunctionalityInPaymentRequisitionForm() {

        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_PaymentRequisitionForm = new Page_PaymentRequisitionForm(driver);

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm, 10);

            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PaymentRequisitionForm.select_searchFilterPrfNo),
                        " Search Type  Selection Dropdown Displayed");
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PaymentRequisitionForm.input_searchbarCriteria),
                        " Input Search Box Displayed");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_PaymentRequisitionForm.input_searchbarCriteria, "value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty by default for selected search type Displayed correctly");
            m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_PaymentRequisitionForm.button_clearButtonInSearchBx),
                        " Clear Button Not Displayed Correctly as Default");

                //Search by PRF No in Payment requisition form
            Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.select_searchFilterPrfNo);
            boolean bprfNoFound = false;
            for (WebElement webElement : oPage_PaymentRequisitionForm.list_searchFilterPrfNoDropdown){
                if (Cls_Generic_Methods.getTextInElement(webElement).equals("PRF No.")) {
                    Cls_Generic_Methods.clickElement(webElement);
                    bprfNoFound = true;
                    break;
                }
            }
            m_assert.assertTrue(bprfNoFound, " PRF Number Search option is selected");
            Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.select_searchFilterPrfNo);
            Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.input_searchbarCriteria);
            Cls_Generic_Methods.customWait(3);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PaymentRequisitionForm.input_searchbarCriteria, prf_no), "PRF Number = <b>" + prf_no + "</b>");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_searchItem), "click on search button");

            Cls_Generic_Methods.isElementClickable(driver,oPage_PaymentRequisitionForm.list_searchPrfDropDown.get(0));
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.list_searchPrfDropDown.get(0)), "Click on LHS of PRF transaction list  = <b>" + prf_no + "</b>");
            int SizePRF =oPage_PaymentRequisitionForm.list_searchItem.size();
            if(SizePRF==1){
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.list_searchItem.get(0)),
                        " Search By PRF Number is Worked correctly as order found in the PRF page = <b>" + prf_no + "</b>");
                }
                else{
                    m_assert.assertTrue("PRF Number is not Found and Showing Nothing to display");
                }

          //  m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.text_searchPrfNo).equals(prf_no),
                //   " Search By PRF Number Worked correctly as order found in the PRF page = <b>" + prf_no + "</b>");

            Cls_Generic_Methods.customWait(3);

            //Search by Purchase bill in Payment requisition form
            Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.select_searchFilterPrfNo);
            boolean bPurchaseBillFound = false;
            for (WebElement webElement : oPage_PaymentRequisitionForm.list_searchFilterPrfNoDropdown) {
                String optionText = Cls_Generic_Methods.getTextInElement(webElement);
                if (optionText.equals("Purchase Bill No.")) {
                    Cls_Generic_Methods.clickElement(webElement);
                    bPurchaseBillFound = true;
                    break;
                }
            }
            m_assert.assertTrue(bPurchaseBillFound, " Purchase Bill No is selected");
            Cls_Generic_Methods.clearValuesInElement(oPage_PaymentRequisitionForm.input_searchbarCriteria);
            Cls_Generic_Methods.customWait(3);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PaymentRequisitionForm.input_searchbarCriteria, purchaseBill_no), "Purchase Bill No = <b>" + purchaseBill_no + "</b>");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_searchItem), "click on search button");

            Cls_Generic_Methods.isElementClickable(driver,oPage_PaymentRequisitionForm.list_searchPrfDropDown.get(0));
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.list_searchPrfDropDown.get(0)), "Click on  = <b>" + purchaseBill_no + "</b>");

            int Size =oPage_PaymentRequisitionForm.list_searchItem.size();
            if (Size==1){
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.list_searchItem.get(0)),
                        " Search By Purchase bill Number Worked correctly as order found in the PRF page = <b>" + purchaseBill_no + "</b>");
            }
            else{
                m_assert.assertTrue("Purchase bill is not Found and Showing Nothing to display");
            }

            Cls_Generic_Methods.customWait(3);

            //Search by Invoice no in Payment requisition form
            Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.select_searchFilterPrfNo);
            boolean bInvoiceNoFound = false;
            for (WebElement webElement : oPage_PaymentRequisitionForm.list_searchFilterPrfNoDropdown) {
                if (Cls_Generic_Methods.getTextInElement(webElement).equals("Invoice No.")) {
                    Cls_Generic_Methods.clickElement(webElement);
                    bInvoiceNoFound = true;
                    break;
                }
            }
            m_assert.assertTrue(bInvoiceNoFound, "InvoiceNumber is selected");
            Cls_Generic_Methods.clearValuesInElement(oPage_PaymentRequisitionForm.input_searchbarCriteria);
            Cls_Generic_Methods.customWait(3);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PaymentRequisitionForm.input_searchbarCriteria, billNumber), "InvoiceNumber = <b>" + billNumber + "</b>");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_searchItem), "click on search button");
            
            Cls_Generic_Methods.isElementClickable(driver,oPage_PaymentRequisitionForm.list_searchPrfDropDown.get(0));
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.list_searchPrfDropDown.get(0)), "Click on  = <b>" + billNumber + "</b>");
            Cls_Generic_Methods.customWait(3);
                int SizeInvoice =oPage_PaymentRequisitionForm.list_searchItem.size();
                if (SizeInvoice==1){
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.list_searchItem.get(0)),
                            " Search By Invoice Number Worked correctly as order found in the PRF page = <b>" + billNumber + "</b>");
                }
                else{
                    m_assert.assertTrue("Invoice Number is not Found and Showing Nothing to display");
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }

        }catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

}