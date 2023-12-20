package tests.inventoryStores.pharmacyStore.Order;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import io.opentelemetry.sdk.metrics.data.DoubleExemplarData;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.python.antlr.ast.Str;
import org.python.jline.console.completer.CandidateListCompletionHandler;
import org.testng.annotations.CustomAttribute;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.InventoryCommonActions;
import pages.commonElements.Page_CommonElements;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.store.PharmacyStore.Order.Page_Indent;
import pages.store.PharmacyStore.Transaction.Page_Purchase;

import java.text.DecimalFormat;
import java.util.*;

import static pages.commonElements.CommonActions.getRandomString;


public class IndentTest extends TestBase {
    public static String sINDENT_STORE = "Pharmacy automation- Pharmacy";
    String sExpectedButtonToBeSelected = "Variant";
    String sStoreToShipOrToBillOrder = "Pharmacy automation";
    static String purchaseTransactionTime = "";
    static String purchaseTransactionDate = "";
    static String transactionNotes = "Transaction_notes" + getRandomString(4);
    static String billType = "Bill";
    static String billNumber = "BILL_" + getRandomNumber();
    String indentOrderTime = "";
    String indentOrderDate = "";
    String sQTY = "10";
    String sSubStore = "Default";
    String sIndentType = "Normal";
    String OrderTime = "";
    String OrderDate = "";
    String sRatePerUnit = "10.0";
    String sPaidQty = "5";
    String sFreeQty = "4";
    String sDiscount = "5";
    String sDiscountType = "%";
    String sComments = "Comments";
    String sOtherCharges = "10";
    String netAmountAfterOtherCharges = "";
    String sStatusAfterPurchaseOrder = "Partially Completed";
    String sStatusBeforePurchaseOrder = "Open";
    String sIndentNoOnUI = null;
    String sVendorName = "Supplier ABC";
    String sNote = "Remarks";
    String sItem = "Indent item";
    String partialQty = "2";
    String sBatchNumber = "ABCD" + getRandomString(3);


    public static Map<String, String> mapTracker = new HashMap<String, String>();
    public static String key_CreatedAt_IndentOrderFromStore = "key_CreatedAt_IndentOrderFromStore";
    public static String key_CreatedAt_PurchaseOrderTroughIndent = "key_CreatedAt_PurchaseOrderTroughIndent";

    @Test(enabled = true, description = "Desc")
    public void CreateIndentFromVariant() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);


        int indexOfOrderDate = -1;
        int indexOfItem = -1;

        boolean vendorFound = false;
        boolean bIndentOrderFound = false;
        boolean bStoreToShipOrderFound = false;
        boolean bStoreToBillOrderFound = false;

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sINDENT_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_addNewIndent),
                        "New Button clicked in Order: Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 10);
                String sSelectedButton = Cls_Generic_Methods.getTextInElement(oPage_Indent.button_variantOrRequisitionSelected);
                if (sSelectedButton.equalsIgnoreCase(sExpectedButtonToBeSelected)) {
                    boolean ItemList = oPage_Indent.list_ItemDescriptionsUnderIndentPurchase.isEmpty();
                    if (ItemList) {
                        m_assert.assertTrue("ItemList is empty");
                    } else {
                        m_assert.assertTrue(false, "ItemList is not empty");
                    }
                } else {
                    m_assert.assertTrue("Expected Button = <b>" + sExpectedButtonToBeSelected + "</b> is not selected");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_vendorField, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.select_vendorField),
                        "clicked on Store selection field");
                Cls_Generic_Methods.customWait();
                boolean storeFound = false;
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, sStoreToShipOrToBillOrder);
                Cls_Generic_Methods.customWait();
                for (WebElement eStoreName : oPage_Indent.list_stores) {
                    if (Cls_Generic_Methods.getTextInElement(eStoreName).contains(sStoreToShipOrToBillOrder)) {
                        Cls_Generic_Methods.clickElement(eStoreName);
                        storeFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(storeFound, "Store found to do indent purchase : <b> " + sINDENT_STORE + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_noteUnderIndentForPurchase, 10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_noteUnderIndentForPurchase, sNote),
                        "Note field value filled as =  <b>" + sNote + "</b> ");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_ItemDescriptionsUnderIndentPurchase.get(0)),
                        "Selected item for indent purchase");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                        "Clicked on save button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_validationForQuantity, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.text_validationForQuantity),
                        "Validation for quantity field = <b>This field is required.</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_deleteItem, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_deleteItem),
                        "Delete item action working");

                m_assert.assertTrue(!Cls_Generic_Methods.isElementEnabled(oPage_Indent.button_saveIndentPurchaseOrder), "Save button should NOT be enabled");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_variantSearch, 10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_variantSearch, sItem),
                        "<b> " + sItem + "</b> Entered in search item field");
                Cls_Generic_Methods.customWait(2);
                oPage_Indent.input_variantSearch.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(3);
                String sSelectedItemName = " ";
                for (WebElement eItem : oPage_Indent.list_ItemDescriptionsUnderIndentPurchase) {
                    oPage_Indent = new Page_Indent(driver);
                    if (Cls_Generic_Methods.getTextInElement(eItem).contains(sItem)) {
                        sSelectedItemName = Cls_Generic_Methods.getTextInElement(eItem);
                        Cls_Generic_Methods.clickElement(eItem);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_indentOrderDate, 10);
                indentOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderTime, "value");
                indentOrderTime = indentOrderTime.replaceAll("\\s+", "");
                // indentOrderTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", indentOrderTime);
                indentOrderTime = indentOrderTime.replace("am", "AM").replace("pm", "PM");
                if (indentOrderTime.length() == 6) {
                    indentOrderTime = "0" + indentOrderTime;
                }
                m_assert.assertTrue("Indent order time:<b> " + indentOrderTime + "</b>");
                indentOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderDate, "value");
                indentOrderDate = indentOrderDate.replaceAll("/", "-");
                m_assert.assertTrue("Indent order date:<b> " + indentOrderDate + "</b>");
                mapTracker.put(key_CreatedAt_IndentOrderFromStore, indentOrderDate + "  |  " + indentOrderTime);
                Cls_Generic_Methods.customWait();

                for (WebElement eItem : oPage_Indent.list_itemNameSelectedToCreateIndentPurchase) {
                    if (Cls_Generic_Methods.getTextInElement(eItem).contains(sItem)) {
                        indexOfItem = oPage_Indent.list_itemNameSelectedToCreateIndentPurchase.indexOf(eItem);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_quantityField, sQTY);
                    }
                }

                Cls_Generic_Methods.customWait();
                if (Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.select_substoreFromIndentPurchasePage, sSubStore)) {
                    m_assert.assertTrue("Selected sub store = <b> " + sSubStore + "</b>");
                }

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
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_IndentNumber, 10);
                    sIndentNoOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_IndentNumber);
                    sIndentNoOnUI = sIndentNoOnUI.replaceAll("Indent No.", "").trim();

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_statusOfOrder, 10);
                    String sOrderStatusBeforePo = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder).trim();

                    if (sOrderStatusBeforePo.equalsIgnoreCase(sStatusBeforePurchaseOrder)) {
                        m_assert.assertTrue("Order Status = <b>" + sOrderStatusBeforePo + " </b>");
                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newOrder, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newOrder),
                            "Clicked on New Order button to create PO");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_IndentNumberInPurchaseOrderPopUp, 10);
                    String sIndentNoInPurchaseOrderPopUp = Cls_Generic_Methods.getTextInElement(oPage_Indent.link_indentNumberLinkInNewOrder);
                    // sIndentNoInPurchaseOrderPopUp = sIndentNoInPurchaseOrderPopUp.replaceAll("Indent No.", "").trim();

                    if (sIndentNoOnUI.equalsIgnoreCase(sIndentNoInPurchaseOrderPopUp)) {
                        m_assert.assertTrue("Validated Indent Number while creating new purchase order =  <b>" + sIndentNoInPurchaseOrderPopUp + "</b>");
                    } else {
                        m_assert.assertTrue("displaying wrong Indent Number ");
                    }

                    String sItemNameForOrder = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_itemNameInPurchaseOrderPopUp);
                    if (sItemNameForOrder.equalsIgnoreCase(sSelectedItemName)) {
                        m_assert.assertTrue("Validated item name while creating new purchase order = <b>" + sSelectedItemName + "</b> ");
                    } else {
                        m_assert.assertTrue("displaying wrong Item name");
                    }

                    String sOrderedQty = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_quantityInPurchaseOrderPopUp, "value");
                    if (sOrderedQty.contains(sQTY)) {
                        m_assert.assertTrue("Quantity validated while creating new purchase order = <b>" + sQTY + "</b>");
                    } else {
                        m_assert.assertTrue("displaying wrong Quantity");
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_OrderNote, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_OrderNote, sNote),
                            "Order note field value filled while creating new purchase order =  <b>" + sNote + "</b> ");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_IndentOrderType, 10);
                    OrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderTime, "value");
                    OrderTime = OrderTime.replaceAll("\\s+", "");
                    // OrderTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", OrderTime);
                    OrderTime = OrderTime.replace("am", "AM").replace("pm", "PM");
                    if (OrderTime.length() == 6) {
                        OrderTime = "0" + OrderTime;
                    }
                    m_assert.assertTrue(" order time:<b> " + OrderTime + "</b>");

                    OrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderDate, "value");
                    OrderDate = OrderDate.replaceAll("/", "-");
                    m_assert.assertTrue(" order date:<b> " + OrderDate + "</b>");

                    mapTracker.put(key_CreatedAt_PurchaseOrderTroughIndent, OrderDate + "  |  " + OrderTime);
                    Cls_Generic_Methods.customWait(2);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_searchVendorAddress, sVendorName);
                    Cls_Generic_Methods.customWait();
                    String sSelectedVendor = Cls_Generic_Methods.getTextInElement(oPage_Indent.vendorAddressName);
                    if (sSelectedVendor.contains(sVendorName)) {
                        Cls_Generic_Methods.clickElement(oPage_Indent.vendorAddressName);
                        m_assert.assertTrue("Selected Vendor:  <b>" + sVendorName + "</b>");
                    } else {
                        m_assert.assertTrue("Required vendor <b>" + sVendorName + "</b> is NOT selected");
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_BillToStore, 10);

                }


                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_RatePerUnit, 10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_RatePerUnit, sRatePerUnit),
                        "Rate per unit =  <b>" + sRatePerUnit + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.value_PendingQty, 10);
                String sPendingQtyUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.value_PendingQty);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_PaidQty, 10);
                String sPaidQtyUI = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_PaidQty, "value");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.value_TaxRate, 10);
                String sTaxRate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.value_TaxRate, "value");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_FreeQty, 10);
                Cls_Generic_Methods.clearValuesInElement(oPage_Indent.input_FreeQty);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_FreeQty, sFreeQty),
                        "Free qty =  " + sFreeQty + "");
                double sSummationOfPaidAndFreeQty = Double.parseDouble(sPaidQtyUI) + Double.parseDouble(sFreeQty);

                m_assert.assertTrue("Paid Qty = " + sPaidQtyUI + " + " + "  Free Qty  = " + sFreeQty + " =  <b>" + sSummationOfPaidAndFreeQty + "</b> ");
                m_assert.assertTrue("Pending Quantity =  <b>" + sPendingQtyUI + "</b> " + "<b>is Less than(<)</b> Sum of paid and free qty = <b>" + sSummationOfPaidAndFreeQty + " </b> ");

                Cls_Generic_Methods.clearValuesInElement(oPage_Indent.input_PaidQty);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_PaidQty, String.valueOf(sSummationOfPaidAndFreeQty));
                Cls_Generic_Methods.customWait();

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder);
                Cls_Generic_Methods.customWait(1);

                if (Cls_Generic_Methods.isElementDisplayed(oPage_Indent.validation_quantity)) {
                    m_assert.assertTrue("Validation for quantity: <b>Cannot checkout more than Pending Qty</b>");
                } else {
                    m_assert.assertTrue(false, "Validation for quantity: <b>Cannot checkout more than Pending Qty</b>");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_PaidQty, 10);
                Cls_Generic_Methods.clearValuesInElement(oPage_Indent.input_PaidQty);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_PaidQty, 10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_PaidQty, sPaidQty),
                        "Paid qty value updated =  <b>" + sPaidQty + "</b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder);
                Cls_Generic_Methods.customWait(1);

                if (Cls_Generic_Methods.isElementDisplayed(oPage_Indent.validation_quantity)) {
                    m_assert.assertInfo(false, "Validation for quantity: Cannot checkout more than Pending Qty");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_PaidQty, 10);
                String sUpdatedPaidQty = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_PaidQty, "value");

                int iUpdatedSummationOfPaidAndFreeQty = Integer.parseInt(sUpdatedPaidQty) + Integer.parseInt(sFreeQty);
                String sUpdatedSummationOfPaidAndFreeQty = String.valueOf(iUpdatedSummationOfPaidAndFreeQty);

                if (sPendingQtyUI.equals(sUpdatedSummationOfPaidAndFreeQty)) {
                    m_assert.assertTrue("Paid Qty =  <b>" + sUpdatedPaidQty + " + " + "</b>  Free Qty  = <b>" + sFreeQty + "</b> = <b>" + sUpdatedSummationOfPaidAndFreeQty + "</b> ");
                    m_assert.assertTrue("Pending Quantity =  <b>" + sPendingQtyUI + " </b>is Equals to  " + "Sum of paid and free qty = <b>" + sUpdatedSummationOfPaidAndFreeQty + " </b> ");
                } else {
                    m_assert.assertTrue("Sum of Paid Qty = " + sUpdatedPaidQty + " Free Qty  = " + sFreeQty + " = " + sUpdatedSummationOfPaidAndFreeQty + "</b> ");
                    m_assert.assertTrue("Pending Quantity =  <b>" + sPendingQtyUI + "</b> is Greater than (>) " + "Sum of paid and free qty = <b>" + sUpdatedSummationOfPaidAndFreeQty + " </b> ");
                }

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_Discount, sDiscount),
                        "Discount =  " + sDiscount);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_DiscountType, 10);
                Cls_Generic_Methods.selectElementByValue(oPage_Indent.select_DiscountType, sDiscountType);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_DiscountValueFromUI, 10);
                String sDiscountOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_DiscountValueFromUI);
                sDiscountOnUI = sDiscountOnUI.replaceAll("-", "");

                double dRatePerUnit = Double.parseDouble(sRatePerUnit);
                double dUpdatedPaidQty = Double.parseDouble(sUpdatedPaidQty);
                double dDiscountOnUI = Double.parseDouble(sDiscountOnUI);
                double dTaxRate = Double.parseDouble(sTaxRate);
                double dGST;
                double dNetAmountOnTable;
                double dGrossAmount;
                double dCalculatedNetAmount;
                double dFinalGST;

                dGST = ((dRatePerUnit * dUpdatedPaidQty) - dDiscountOnUI) * (dTaxRate / 100);
                dFinalGST = (Math.round(dGST * 100) / (double) 100);

                dCalculatedNetAmount = ((dRatePerUnit * dUpdatedPaidQty) - dDiscountOnUI) + dFinalGST;
                String sNetAmountOnTable = Cls_Generic_Methods.getElementAttribute(oPage_Indent.value_NetAmountTable, "value");
                dNetAmountOnTable = Double.parseDouble(sNetAmountOnTable);

                dGrossAmount = (dRatePerUnit) * (dUpdatedPaidQty);

                m_assert.assertTrue((dCalculatedNetAmount == dNetAmountOnTable),
                        "Calculated Net Amount  : <b>" + dCalculatedNetAmount + "</b> " + "<b> = </b> Net Amount in table :  " + dNetAmountOnTable + "</b> ");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_Remarks, 10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_Remarks, sNote),
                        "REMARK =  " + sNote);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_SubStore, 10);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.select_SubStore, sSubStore);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_Comments, 10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.text_Comments, sNote),
                        "Comments =  " + sComments + "");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_validationMessageForOtherChargesNameField, 10);

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Indent.text_validationMessageForOtherChargesNameField),
                        "Other charges Name field is mandatory :<b> REQUIRED </b> ");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_validationMessageForOtherChargesNetField, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Indent.text_validationMessageForOtherChargesNetField),
                        "Other charges Net field is mandatory :<b> REQUIRED </b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_validationMessageForOtherChargesAmountField, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Indent.text_validationMessageForOtherChargesAmountField),
                        "Other charges amount field is mandatory :<b> REQUIRED </b> ");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_cancelOtherCharges, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_cancelOtherCharges);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addOtherCharges, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_addOtherCharges);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_otherCharges, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.dropdown_otherCharges),
                        "Other Charge Dropdown clicked");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_otherCharges, 10);

                m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_Indent.dropdown_otherCharges, 1),
                        "Selected the Second option for Other charges");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_otherChargesValue, 10);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_otherChargesValue, sOtherCharges),
                        "Other Charge entered = <b> " + sOtherCharges + "</b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_otherChargesMinus, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_otherChargesMinus),
                        "Minus button clicked for other charge");

                netAmountAfterOtherCharges = InventoryCommonActions.getOtherChargesNetAmount(sOtherCharges, sNetAmountOnTable, "minus");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_netAmountOnUI, 10);

                String sNetAmountAfterOtherChargesUI = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_netAmountOnUI, "value");
                m_assert.assertTrue(netAmountAfterOtherCharges.equalsIgnoreCase(sNetAmountAfterOtherChargesUI),
                        "Calculated net amount after applying other charges = <b>" + netAmountAfterOtherCharges + "</b>  is EQUALS TO " + " Net amount on UI = <b>" + sNetAmountAfterOtherChargesUI + "</b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_otherChargesPlus),
                        "Plus button clicked for other charge");
                netAmountAfterOtherCharges = InventoryCommonActions.getOtherChargesNetAmount(sOtherCharges, sNetAmountOnTable, "plus");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_netAmountOnUI, 10);

                String sNetAmountAfterOtherChargeUI = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_netAmountOnUI, "value");
                m_assert.assertTrue(netAmountAfterOtherCharges.equalsIgnoreCase(sNetAmountAfterOtherChargeUI),
                        "Calculated net amount after applying other charges = <b>" + netAmountAfterOtherCharges + "</b>  is EQUALS TO " + " Net amount on UI = <b>" + sNetAmountAfterOtherChargeUI + "</b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_grossAmountOnUI, 10);
                String sGrossAmountOnUI = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");

                double dGrossAmountOnUI = Double.parseDouble(sGrossAmountOnUI);
                m_assert.assertTrue(dGrossAmount == dGrossAmountOnUI, "Calculated gross amount  = <b>" + dGrossAmount + "</b>  is EQUALS TO " + " Gross amount on UI = <b>" + dGrossAmountOnUI + "</b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_discountOnUI, 10);
                String sTotalDiscountOnUI = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_discountOnUI, "value");
                double dTotalDiscountOnUI = Double.parseDouble(sTotalDiscountOnUI);
                m_assert.assertTrue(dDiscountOnUI == dTotalDiscountOnUI, "Calculated Discount  = <b>" + dDiscountOnUI + "</b>  is EQUALS TO " + " Total Discount on UI = <b>" + dTotalDiscountOnUI + "</b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_GSTOnUI, 10);
                String sGSTOnUI = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_GSTOnUI, "value");
                double dGSTOnUI = Double.parseDouble(sGSTOnUI);
                m_assert.assertTrue(dFinalGST == dGSTOnUI, "Calculated GST  = <b>" + dFinalGST + "</b>  is EQUALS TO " + " GST on UI = <b>" + dGSTOnUI + "</b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_otherChargesOnUI, 10);
                String sOtherChargesOnUI = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_otherChargesOnUI, "value");
                double dOtherChargesOnUI = Double.parseDouble(sOtherChargesOnUI);
                m_assert.assertTrue(sOtherCharges.equalsIgnoreCase(sOtherChargesOnUI), "Other charges   = <b>" + sOtherCharges + "</b>  is EQUALS TO " + " Other charges on UI = <b>" + sOtherChargesOnUI + "</b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                        "Save Button clicked");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.option_purchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder),
                        "Purchase order option is selected");
                Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder);
                Cls_Generic_Methods.customWait(3);
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (mapTracker.get(key_CreatedAt_PurchaseOrderTroughIndent).contains(date) &&
                            mapTracker.get(key_CreatedAt_PurchaseOrderTroughIndent).contains(time)) {
                        bIndentOrderFound = true;
                        indexOfOrderDate = oPage_Indent.list_dateTimeOfIndentOrder.indexOf(eDate);
                        Cls_Generic_Methods.clickElement(eDate);
                        break;
                    }
                }

                Cls_Generic_Methods.customWait();

                if (bIndentOrderFound) {

                    m_assert.assertTrue(true, "Order found in the Indent order page");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approve, 10);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_approve),
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
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newTransaction, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_newTransaction),
                            "Clicked on new transaction");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_itemNameInPoTable, 10);
                    String sItemNameForPo = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_itemNameInPoTable);
                    sItemNameForPo = sItemNameForPo.replaceAll("-", "");
                    m_assert.assertTrue(sItemNameForPo.equalsIgnoreCase(sSelectedItemName), "Item name validated while Adding New Stock to Inventory= <b>" + sSelectedItemName + "</b>");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_quantityInPoTable, 10);
                    String sItemQtyForPo = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_quantityInPoTable, "value");
                    m_assert.assertTrue(sItemQtyForPo.contains(sUpdatedPaidQty), "Quantity validated while adding New Stock to Inventory = <b> " + sUpdatedPaidQty + "</b>");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_freeQuantityInPoTable, 10);
                    String sFreeQtyForPo = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_freeQuantityInPoTable, "value");
                    m_assert.assertTrue(sFreeQtyForPo.contains(sFreeQty), "Free Quantity validated While Adding New Stock to Inventory = <b>" + sFreeQty + "</b>");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_taxRateInPoTable, 10);
                    String sTaxRateForPo = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_taxRateInPoTable);
                    m_assert.assertTrue(sTaxRate.equalsIgnoreCase(sTaxRateForPo), "Tax rate validated while Adding New Stock to Inventory = <b>" + sTaxRateForPo + "</b> ");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_mrpInNewTransaction, 10);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_mrpInNewTransaction, "100");
                    m_assert.assertTrue("MRP Entered while Adding New Stock to Inventory =  <b>" + "100" + "</b>");

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_batchNOInNewTransaction, "BT1234");
                    m_assert.assertTrue("Batch No Entered while Adding New Stock to Inventory =  <b>" + "BT1234" + "</b>");


                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_taxRateInPoTable, 10);
                    String sDiscountForPo = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_DiscountInPoTable);
                    double dDiscountForPo = Double.parseDouble(sDiscountForPo);
                    m_assert.assertTrue(dDiscountForPo == dTotalDiscountOnUI, "Discount amount validated while Adding New Stock to Inventory =  <b>" + dTotalDiscountOnUI + "<b>");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_TotalAmountInPoTable, 10);
                    String sNetAmountForPo = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_TotalAmountInPoTable, "value");
                    double dNetAmountForPo = Double.parseDouble(sNetAmountForPo);
                    m_assert.assertTrue(dNetAmountForPo == dCalculatedNetAmount, "Net amount validated while Adding New Stock to Inventory = <b>" + dCalculatedNetAmount + "</b>");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_remarksInPoTable, 10);
                    String sRemarkForPo = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_remarksInPoTable, "value");
                    m_assert.assertTrue(sRemarkForPo.equalsIgnoreCase(sNote), "Remark text validated while Adding New Stock to Inventory = <b>" + sNote + "<b>");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_GrossAmountForPO, 10);
                    String sGrossAmountForPo = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_GrossAmountForPO, "value");
                    double dGrossAmountForPo = Double.parseDouble(sGrossAmountForPo);
                    m_assert.assertTrue(dGrossAmountForPo == dGrossAmountOnUI, "Gross amount validated while Adding New Stock to Inventory = <b>" + dGrossAmountOnUI + "<b>");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_GSTForPo, 10);
                    String sGSTForPo = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_GSTForPo, "value");
                    double dGSTForPo = Double.parseDouble(sGSTForPo);
                    m_assert.assertTrue(dGSTForPo == dFinalGST, "GST amount validated while Adding New Stock to Inventory =  <b>" + sGSTForPo + "</b>");


                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_discountForPo, 10);
                    String sTotalDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_discountForPo, "value");
                    double dTotalDiscount = Double.parseDouble(sTotalDiscount);
                    m_assert.assertTrue(dTotalDiscount == dTotalDiscountOnUI, "Discount amount validated while Adding New Stock to Inventory =  <b>" + sGSTForPo + "</b>");

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_otherCharges, sOtherCharges);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_otherChargesForPo, 10);
                    String sOtherForPo = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_otherChargesForPo, "value");
                    double dOtherForPo = Double.parseDouble(sOtherForPo);
                    m_assert.assertTrue(dOtherForPo == dOtherChargesOnUI, "Other charges amount validated while Adding New Stock to Inventory = <b>" + sOtherForPo + "</b>");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_netAmountForPo, 10);
                    String sNetAmountForPoUnderOtherDetailsTable = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_netAmountForPo, "value");
                    m_assert.assertTrue(sNetAmountForPoUnderOtherDetailsTable.equalsIgnoreCase(sNetAmountAfterOtherChargeUI), "Net amount validated while Adding New Stock to Inventory = <b>" + sNetAmountAfterOtherChargeUI + "</b>");

                } else {
                    m_assert.assertTrue(bIndentOrderFound, "Indent order is NOT found");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_closePoPopUp, 10);
                Cls_Generic_Methods.clickElement(oPage_Indent.button_closePoPopUp);
                Cls_Generic_Methods.customWait();

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");

                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(date) &&
                            mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(time)) {
                        bIndentOrderFound = true;
                        Cls_Generic_Methods.clickElementByJS(driver, eDate);
                        break;
                    }
                }

                m_assert.assertTrue(true, "Order found in the Indent order page");
                if (bIndentOrderFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_IndentNumber, 10);
                    String sIndentNo = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_IndentNumber);
                    sIndentNo = sIndentNo.replaceAll("Indent No.", "").trim();

                    m_assert.assertTrue(sIndentNoOnUI.equalsIgnoreCase(sIndentNo), "Indent Number =  <b>" + sIndentNoOnUI + "</b>");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_statusOfOrder, 10);
                    String sOrderStatusAfterPo = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);
                    sOrderStatusAfterPo = sOrderStatusAfterPo.replaceAll("Status:", "").trim();

                    m_assert.assertTrue(sOrderStatusAfterPo.equalsIgnoreCase(sStatusAfterPurchaseOrder), "Order Status =  <b>" + sOrderStatusAfterPo + "</b>");
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

    @Test(enabled = true, description = "Desc")
    public void createIndentPOForPartiallyCompletedSingleItem() {


        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        List<String> itemList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();
        List<Double> rateList = new ArrayList<>();
        List<String> discountList = new ArrayList<>();


        int numberOfItem = 1;
        boolean bIndentOrderFound = false;
        String globalDiscountValue = "10";


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sINDENT_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_addNewIndent),
                        "New Button clicked in Order: Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 10);
                String sSelectedButton = Cls_Generic_Methods.getTextInElement(oPage_Indent.button_variantOrRequisitionSelected);
                if (sSelectedButton.equalsIgnoreCase(sExpectedButtonToBeSelected)) {
                    boolean ItemList = oPage_Indent.list_ItemDescriptionsUnderIndentPurchase.isEmpty();
                    if (ItemList) {
                        m_assert.assertTrue("ItemList is empty");
                    } else {
                        m_assert.assertTrue(false, "ItemList is not empty");
                    }
                } else {
                    m_assert.assertTrue("Expected Button = <b>" + sExpectedButtonToBeSelected + "</b> is not selected");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_vendorField, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.select_vendorField),
                        "clicked on Store selection field");
                Cls_Generic_Methods.customWait();
                boolean storeFound = false;
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, sStoreToShipOrToBillOrder);
                Cls_Generic_Methods.customWait();
                for (WebElement eStoreName : oPage_Indent.list_stores) {
                    if (Cls_Generic_Methods.getTextInElement(eStoreName).contains(sStoreToShipOrToBillOrder)) {
                        Cls_Generic_Methods.clickElement(eStoreName);
                        storeFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(storeFound, "Store found to do indent purchase : <b> " + sINDENT_STORE + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_noteUnderIndentForPurchase, 10);

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
                boolean bItemSelected = CommonActions.selectItemInInventory(oPage_Indent.list_ItemDescriptionsUnderIndentPurchase, itemList, numberOfItem, oPage_Indent.list_itemNameSelectedToCreateIndentPurchase);

                m_assert.assertTrue(bItemSelected, numberOfItem + " Item Selected From Left Panel Correctly ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_indentOrderDate, 10);
                indentOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderTime, "value");
                indentOrderTime = indentOrderTime.replaceAll("\\s+", "");
                indentOrderTime = indentOrderTime.replace("am", "AM").replace("pm", "PM");
                if (indentOrderTime.length() == 6) {
                    indentOrderTime = "0" + indentOrderTime;
                }
                m_assert.assertTrue("Indent order time:<b> " + indentOrderTime + "</b>");
                indentOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderDate, "value");
                indentOrderDate = indentOrderDate.replaceAll("/", "-");
                m_assert.assertTrue("Indent order date:<b> " + indentOrderDate + "</b>");
                mapTracker.put(key_CreatedAt_IndentOrderFromStore, indentOrderDate + "  |  " + indentOrderTime);
                Cls_Generic_Methods.customWait();

                for (WebElement eItem : oPage_Indent.list_itemNameSelectedToCreateIndentPurchase) {
                    int index = oPage_Indent.list_itemNameSelectedToCreateIndentPurchase.indexOf(eItem);

                    Cls_Generic_Methods.scrollToElementByJS(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index));
                    Cls_Generic_Methods.customWait(1);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index), sQTY);
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Indent.text_multipleOfItemText)) {
                        String multipleOf = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_multipleOfItemText).split(" ")[6];
                        Cls_Generic_Methods.clearValuesInElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index));
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index), multipleOf);
                        quantityList.add(multipleOf);

                    } else {
                        quantityList.add(sQTY);
                    }
                    m_assert.assertInfo(" Quantity Entered as <b> " + quantityList.get(index) + " </b> for item name <b> " + itemList.get(index) + " </b> at index " + index);
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.list_subStoreSelectedToCreateIndentPurchase.get(index), sSubStore);
                    Cls_Generic_Methods.customWait(1);

                }

                Cls_Generic_Methods.customWait();

                int totalQuantity = 0;
                for (int i = 0; i < quantityList.size(); i++) {
                    totalQuantity = totalQuantity + Integer.parseInt(quantityList.get(i));
                }

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Indent.text_totalQuantity).equalsIgnoreCase("Total Quantity :" + totalQuantity),
                        " Total Quantity For all Item displayed correctly as : " + totalQuantity);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder),
                        " Indent order saved");
                Cls_Generic_Methods.customWait(3);

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

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approveIndent, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_approveIndent), "Approve Button Cliked to approve Indent");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approveConfirmation, 20);
                    Cls_Generic_Methods.clickElement(oPage_Indent.button_approveConfirmation);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newOrder, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newOrder),
                            "Clicked on New Order button to create PO");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_IndentNumberInPurchaseOrderPopUp, 10);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_IndentOrderType, 10);
                    OrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderTime, "value");
                    OrderTime = OrderTime.replaceAll("\\s+", "");
                    OrderTime = OrderTime.replace("am", "AM").replace("pm", "PM");
                    if (OrderTime.length() == 6) {
                        OrderTime = "0" + OrderTime;
                    }
                    m_assert.assertTrue(" order time:<b> " + OrderTime + "</b>");

                    OrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderDate, "value");
                    OrderDate = OrderDate.replaceAll("/", "-");
                    m_assert.assertTrue(" order date:<b> " + OrderDate + "</b>");

                    mapTracker.put(key_CreatedAt_PurchaseOrderTroughIndent, OrderDate + "  |  " + OrderTime);
                    Cls_Generic_Methods.customWait(2);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_searchVendorAddress, sVendorName);
                    Cls_Generic_Methods.customWait();
                    String sSelectedVendor = Cls_Generic_Methods.getTextInElement(oPage_Indent.vendorAddressName);
                    if (sSelectedVendor.contains(sVendorName)) {
                        Cls_Generic_Methods.clickElement(oPage_Indent.vendorAddressName);
                        m_assert.assertTrue("Selected Vendor:  <b>" + sVendorName + "</b>");
                    } else {
                        m_assert.assertTrue("Required vendor <b>" + sVendorName + "</b> is NOT selected");
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_BillToStore, 10);

                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_RatePerUnit, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_addActionListOfIndentOrder.get(0)),
                        " Add Button Clicked");
                Cls_Generic_Methods.customWait(1);


                for (WebElement rate : oPage_Indent.list_rateListOfIndentOrder) {
                    Double rateValue = nextDoubleBetween2(100.0d, 500.0d);
                    rateList.add(rateValue);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(rate, String.valueOf(rateValue)),
                            "Rate per unit  Entered as =  <b>" + rateValue + " </b>");
                }


                for (WebElement paidQty : oPage_Indent.list_paidQuantityListOfIndentOrder) {
                    Cls_Generic_Methods.clearValuesInElement(paidQty);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(paidQty, partialQty),
                            "QTY per unit Entered as =  <b>" + partialQty + " </b>");
                }

                Cls_Generic_Methods.clearValuesInElement(oPage_Indent.input_globalDiscount);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_globalDiscount, globalDiscountValue);
                Cls_Generic_Methods.customWait(1);
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_applyGlobalDiscount),
                            "Clicked on apply global discount button");
                    Cls_Generic_Methods.customWait(1);

                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                    Cls_Generic_Methods.customWait();
                }

                String totalGrossAmount = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");
                DecimalFormat decimalFormat = new DecimalFormat("0.00");

                for (int i = 0; i < 2; i++) {
                    String grossPerItemCalculated = decimalFormat.format(rateList.get(i) * Double.parseDouble(partialQty));
                    String globalDiscountPerItemCalculated = getGlobalDiscount(globalDiscountValue, totalGrossAmount, grossPerItemCalculated);
                    String globalDiscountPerItemOnUI = Cls_Generic_Methods.getElementAttribute(oPage_Indent.list_discountListOfIndentOrder.get(i), "value");
                    discountList.add(globalDiscountPerItemOnUI);
                    m_assert.assertTrue(globalDiscountPerItemOnUI.equalsIgnoreCase(globalDiscountPerItemCalculated),
                            " Global Discount Working correctly as discount apply correctly for item as : " + globalDiscountPerItemOnUI + " at index " + i);
                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(1)),
                        " Delete Button CLicked");
                Cls_Generic_Methods.customWait();

                String totalGrossAmountAfterDelete = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");

                String x[] = rateList.get(0).toString().replace(".", " ").split(" ");
                String grossPerItemCalculated = decimalFormat.format(Double.parseDouble(x[0] + "." + x[1].substring(0, 2)) * Double.parseDouble(partialQty));

                m_assert.assertTrue(totalGrossAmountAfterDelete.equalsIgnoreCase(grossPerItemCalculated),
                        " Gross Amount Calculated Correctly After Deleted as " + totalGrossAmountAfterDelete + "----" + grossPerItemCalculated);
                String sTaxRate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.list_taxRateListOfIndentOrder.get(0), "value");

                String gstCalculated = getGST(totalGrossAmountAfterDelete, sTaxRate, discountList.get(0));
                String gstOnUI = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_GSTOnUIText, "value");
                m_assert.assertTrue(gstOnUI.equalsIgnoreCase(gstCalculated),
                        " GST Amount Calculated Correctly After Deleted as " + gstOnUI + "----" + gstCalculated);

                String netAmountOnUi = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_netAmountOnUI, "value");

                String netAmountCalculated = decimalFormat.format(Double.parseDouble(totalGrossAmountAfterDelete) + Double.parseDouble(gstOnUI) - Double.parseDouble(discountList.get(0)));

                m_assert.assertTrue(netAmountOnUi.equalsIgnoreCase(netAmountCalculated),
                        " Net Amount Calculated Correctly After Deleted as " + netAmountOnUi);

                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElement(oPage_Indent.button_cancelOtherCharges);
                Cls_Generic_Methods.customWait(1);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                        " Save Button Clicked In PO ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.option_purchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder),
                        "Purchase order option is selected");
                Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder);
                Cls_Generic_Methods.customWait(3);
                boolean recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }


                m_assert.assertTrue(recordFound, " PO Created Using Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approve, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_approve),
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
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newTransaction, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newTransaction), "New Transaction button clicked to create GRN");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 30);
                int mrpSize = oPage_Purchase.list_MRPPrice.size();
                for (int i = 0; i < mrpSize; i++) {
                    Double dMRP = nextDoubleBetween2(100.0d, 500.0d);
                    String sMRP = String.valueOf(dMRP);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(i), sMRP), "Entered MRP <b>" + sMRP + " </b>for Item" + i);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(i), sBatchNumber + i);
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_inputPaidQty.get(i));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_inputPaidQty.get(i), String.valueOf((Double.parseDouble(partialQty)) / 2)), "Entered qty <b>" + ((Double.parseDouble(partialQty)) / 2) + " </b> to create partial GRN");
                }
                int expSize = oPage_Purchase.list_expiryDate.size();
                for (int i = 0; i < expSize; i++) {
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_expiryDate.get(i), "31/10/2024");
                }
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes),
                        " Transaction Notes Entered as :<b> " + transactionNotes + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType),
                        "Bill Type Selected:<b> " + billType + " </b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber),
                        "Bill Number: <b> " + billNumber + " </b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                //purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                if (purchaseTransactionTime.length() == 6) {
                    purchaseTransactionTime = "0" + purchaseTransactionTime;
                }
                purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);


                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                        "Date of bill selected:<b> " + oPage_Purchase.input_todayBillDate.getText() + " </b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "GRN created successfully for " + numberOfItem + " items");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 40);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                for (WebElement element : oPage_Purchase.list_purchaseTransactionDateandTimeList) {
                    String sDateAndTime = Cls_Generic_Methods.getTextInElement(element);
                    if (sDateAndTime.contains(purchaseTransactionDate) && sDateAndTime.contains(purchaseTransactionTime)) {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 8);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approveGRN, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_approveGRN), "GRN transaction approved successfully");
                Cls_Generic_Methods.customWait(15);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                Cls_Generic_Methods.customWait(8);
                recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(recordFound, " Order found in the purchase order page");
                String sPOStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);
                m_assert.assertTrue(sPOStatus.equalsIgnoreCase("Partially Completed"),
                        " Purchase Order Status Showing correctly as Partially Completed");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newTransaction), "New Transaction button clicked to create GRN");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 30);
                mrpSize = oPage_Purchase.list_MRPPrice.size();
                for (int i = 0; i < mrpSize; i++) {
                    Double dMRP = nextDoubleBetween2(100.0d, 500.0d);
                    String sMRP = String.valueOf(dMRP);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(i), sMRP), "Entered MRP <b>" + sMRP + " </b>for Item" + i);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(i), (sBatchNumber + i).concat(getRandomString(2)));
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_inputPaidQty.get(i));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_inputPaidQty.get(i), String.valueOf((Double.parseDouble(partialQty)) / 2)), "Entered qty <b>" + ((Double.parseDouble(partialQty)) / 2) + " </b> to create partial GRN");
                }
                expSize = oPage_Purchase.list_expiryDate.size();
                for (int i = 0; i < expSize; i++) {
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_expiryDate.get(i), "31/10/2024");
                }
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes),
                        " Transaction Notes Entered as :<b> " + transactionNotes + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType),
                        "Bill Type Selected:<b> " + billType + " </b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, (billNumber.concat(getRandomString(2)))),
                        "Bill Number: <b> " + (billNumber.concat(getRandomString(2))) + " </b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                //purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                if (purchaseTransactionTime.length() == 6) {
                    purchaseTransactionTime = "0" + purchaseTransactionTime;
                }
                purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);


                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                        "Date of bill selected:<b> " + oPage_Purchase.input_todayBillDate.getText() + " </b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "GRN created successfully for " + numberOfItem + " items");
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                for (WebElement element : oPage_Purchase.list_purchaseTransactionDateandTimeList) {
                    String sDateAndTime = Cls_Generic_Methods.getTextInElement(element);
                    if (sDateAndTime.contains(purchaseTransactionDate) && sDateAndTime.contains(purchaseTransactionTime)) {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 8);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approveGRN, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_approveGRN), "GRN transaction approved successfully");
                Cls_Generic_Methods.customWait(15);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 40);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                Cls_Generic_Methods.customWait(8);
                recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(recordFound, " Order found in the purchase order page");
                sPOStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);
                m_assert.assertTrue(sPOStatus.equalsIgnoreCase("Completed"),
                        " Purchase Order Status Showing correctly as Completed");
                Cls_Generic_Methods.customWait();
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
    public void createIndentPOForCompletedSingleItem() {


        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        List<Double> rateList = new ArrayList<>();
        List<String> discountList = new ArrayList<>();

        boolean bIndentOrderFound = false;
        String globalDiscountValue = "10";


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sINDENT_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);

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

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newOrder, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newOrder),
                            "Clicked on New Order button to create PO");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_IndentNumberInPurchaseOrderPopUp, 10);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_IndentOrderType, 10);
                    OrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderTime, "value");
                    OrderTime = OrderTime.replaceAll("\\s+", "");
                    OrderTime = OrderTime.replace("am", "AM").replace("pm", "PM");
                    if (OrderTime.length() == 6) {
                        OrderTime = "0" + OrderTime;
                    }
                    m_assert.assertTrue(" order time:<b> " + OrderTime + "</b>");

                    OrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderDate, "value");
                    OrderDate = OrderDate.replaceAll("/", "-");
                    m_assert.assertTrue(" order date:<b> " + OrderDate + "</b>");

                    mapTracker.put(key_CreatedAt_PurchaseOrderTroughIndent, OrderDate + "  |  " + OrderTime);
                    Cls_Generic_Methods.customWait(2);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_searchVendorAddress, sVendorName);
                    Cls_Generic_Methods.customWait(3);
                    String sSelectedVendor = Cls_Generic_Methods.getTextInElement(oPage_Indent.vendorAddressName);
                    if (sSelectedVendor.contains(sVendorName)) {
                        Cls_Generic_Methods.clickElement(oPage_Indent.vendorAddressName);
                        m_assert.assertTrue("Selected Vendor:  <b>" + sVendorName + "</b>");
                    } else {
                        m_assert.assertTrue("Required vendor <b>" + sVendorName + "</b> is NOT selected");
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_BillToStore, 10);

                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_RatePerUnit, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_addActionListOfIndentOrder.get(0)),
                        " Add Button Clicked");
                Cls_Generic_Methods.customWait(1);


                for (WebElement rate : oPage_Indent.list_rateListOfIndentOrder) {
                    Double rateValue = nextDoubleBetween2(100.0d, 500.0d);
                    rateList.add(rateValue);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(rate, String.valueOf(rateValue)),
                            "Rate per unit  Entered as =  <b>" + rateValue + " </b>");
                }

                String partialQty = "2";
                for (WebElement paidQty : oPage_Indent.list_paidQuantityListOfIndentOrder) {
                    Cls_Generic_Methods.clearValuesInElement(paidQty);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(paidQty, partialQty),
                            "QTY per unit Entered as =  <b>" + partialQty + " </b>");
                }

                Cls_Generic_Methods.clearValuesInElement(oPage_Indent.input_globalDiscount);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_globalDiscount, globalDiscountValue);
                Cls_Generic_Methods.customWait(1);
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_applyGlobalDiscount),
                            "Clicked on apply global discount button");
                    Cls_Generic_Methods.customWait(1);

                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                    Cls_Generic_Methods.customWait();
                }

                String totalGrossAmount = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");

                for (int i = 0; i < 2; i++) {
                    String grossPerItemCalculated = String.valueOf(rateList.get(i) * Double.parseDouble(partialQty));
                    String globalDiscountPerItemCalculated = getGlobalDiscount(globalDiscountValue, totalGrossAmount, grossPerItemCalculated);
                    String globalDiscountPerItemOnUI = Cls_Generic_Methods.getElementAttribute(oPage_Indent.list_discountListOfIndentOrder.get(i), "value");
                    discountList.add(globalDiscountPerItemOnUI);
                    m_assert.assertTrue(globalDiscountPerItemOnUI.equalsIgnoreCase(globalDiscountPerItemCalculated),
                            " Global Discount Working correctly as discount apply correctly for item as : " + globalDiscountPerItemOnUI + " at index " + i);
                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(1)),
                        " Delete Button CLicked");
                Cls_Generic_Methods.customWait();

                String remainingQty = String.valueOf(Double.parseDouble(sQTY) - Double.parseDouble(partialQty));
                Cls_Generic_Methods.clearValuesInElement(oPage_Indent.list_paidQuantityListOfIndentOrder.get(0));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_paidQuantityListOfIndentOrder.get(0), remainingQty),
                        "QTY per unit Entered as =  <b>" + remainingQty + " </b>");
                Cls_Generic_Methods.customWait(1);

                String totalGrossAmountAfterDelete = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");

                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String x[] = rateList.get(0).toString().replace(".", " ").split(" ");
                String grossPerItemCalculated = decimalFormat.format(Double.parseDouble(x[0] + "." + x[1].substring(0, 2)) * Double.parseDouble(remainingQty));

                m_assert.assertTrue(totalGrossAmountAfterDelete.equalsIgnoreCase(grossPerItemCalculated),
                        " Gross Amount Calculated Correctly After Deleted as " + totalGrossAmountAfterDelete);
                String sTaxRate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.list_taxRateListOfIndentOrder.get(0), "value");

                String gstCalculated = getGST(totalGrossAmountAfterDelete, sTaxRate, "0");
                String gstOnUI = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_GSTOnUIText, "value");
                m_assert.assertTrue(gstOnUI.equalsIgnoreCase(gstCalculated),
                        " GST Amount Calculated Correctly After Deleted as " + gstOnUI);

                String netAmountOnUi = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_netAmountOnUI, "value");

                String netAmountCalculated = decimalFormat.format(Double.parseDouble(totalGrossAmountAfterDelete) + Double.parseDouble(gstOnUI));

                m_assert.assertTrue(netAmountOnUi.equalsIgnoreCase(netAmountCalculated),
                        " Net Amount Calculated Correctly After Deleted as " + netAmountOnUi);

                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElement(oPage_Indent.button_cancelOtherCharges);
                Cls_Generic_Methods.customWait(1);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                        " Save Button Clicked In PO ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.option_purchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder),
                        "Purchase order option is selected");
                Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.customWait(3);
                boolean recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }


                m_assert.assertTrue(recordFound, " PO Created Using Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approve, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_approve),
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
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newTransaction, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newTransaction), "New Transaction button clicked to create GRN");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 30);
                int mrpSize = oPage_Purchase.list_MRPPrice.size();
                for (int i = 0; i < mrpSize; i++) {
                    Double dMRP = nextDoubleBetween2(100.0d, 500.0d);
                    String sMRP = String.valueOf(dMRP);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(i), sMRP), "Entered MRP <b>" + sMRP + " </b>for Item" + i);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(i), (sBatchNumber + i).concat(getRandomString(2)));
                }
                int expSize = oPage_Purchase.list_expiryDate.size();
                for (int i = 0; i < expSize; i++) {
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_expiryDate.get(i), "31/10/2024");
                }
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes),
                        " Transaction Notes Entered as :<b> " + transactionNotes + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType),
                        "Bill Type Selected:<b> " + billType + " </b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, (billNumber.concat(getRandomString(2)))),
                        "Bill Number: <b> " + (billNumber.concat(getRandomString(2))) + " </b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                //purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                if (purchaseTransactionTime.length() == 6) {
                    purchaseTransactionTime = "0" + purchaseTransactionTime;
                }
                purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);


                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                        "Date of bill selected:<b> " + oPage_Purchase.input_todayBillDate.getText() + " </b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "GRN created successfully for " + remainingQty + " Quantities");
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                for (WebElement element : oPage_Purchase.list_purchaseTransactionDateandTimeList) {
                    String sDateAndTime = Cls_Generic_Methods.getTextInElement(element);
                    if (sDateAndTime.contains(purchaseTransactionDate) && sDateAndTime.contains(purchaseTransactionTime)) {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 8);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approveGRN, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_approveGRN), "GRN transaction approved successfully");
                Cls_Generic_Methods.customWait(15);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 40);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                Cls_Generic_Methods.customWait(8);
                recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(recordFound, " Order found in the purchase order page");
                String sPOStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);
                m_assert.assertTrue(sPOStatus.equalsIgnoreCase("Completed"),
                        " Purchase Order Status Showing correctly as Completed");

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(date) &&
                            mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(time)) {
                        bIndentOrderFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(bIndentOrderFound, "Order found in the Indent order page");

                String indentStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);

                m_assert.assertTrue(indentStatus.equalsIgnoreCase("Completed"),
                        " Indent Status Showing correctly as Completed");
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
    public void createIndentPOForPartialCompletedMultipleItem() {


        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        List<String> itemList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();
        List<Double> rateList = new ArrayList<>();
        List<String> discountList = new ArrayList<>();


        int numberOfItem = 20;
        boolean bIndentOrderFound = false;
        String globalDiscountValue = "10";


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sINDENT_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_addNewIndent),
                        "New Button clicked in Order: Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 10);
                String sSelectedButton = Cls_Generic_Methods.getTextInElement(oPage_Indent.button_variantOrRequisitionSelected);
                if (sSelectedButton.equalsIgnoreCase(sExpectedButtonToBeSelected)) {
                    boolean ItemList = oPage_Indent.list_ItemDescriptionsUnderIndentPurchase.isEmpty();
                    if (ItemList) {
                        m_assert.assertTrue("ItemList is empty");
                    } else {
                        m_assert.assertTrue(false, "ItemList is not empty");
                    }
                } else {
                    m_assert.assertTrue("Expected Button = <b>" + sExpectedButtonToBeSelected + "</b> is not selected");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_vendorField, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.select_vendorField),
                        "clicked on Store selection field");
                Cls_Generic_Methods.customWait();
                boolean storeFound = false;
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, sStoreToShipOrToBillOrder);
                Cls_Generic_Methods.customWait();
                for (WebElement eStoreName : oPage_Indent.list_stores) {
                    if (Cls_Generic_Methods.getTextInElement(eStoreName).contains(sStoreToShipOrToBillOrder)) {
                        Cls_Generic_Methods.clickElement(eStoreName);
                        storeFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(storeFound, "Store found to do indent purchase : <b> " + sINDENT_STORE + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_noteUnderIndentForPurchase, 10);

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
                boolean bItemSelected = CommonActions.selectItemInInventory(oPage_Indent.list_ItemDescriptionsUnderIndentPurchase, itemList, numberOfItem, oPage_Indent.list_itemNameSelectedToCreateIndentPurchase);

                m_assert.assertTrue(bItemSelected, numberOfItem + " Item Selected From Left Panel Correctly ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_indentOrderDate, 10);
                indentOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderTime, "value");
                indentOrderTime = indentOrderTime.replaceAll("\\s+", "");
                indentOrderTime = indentOrderTime.replace("am", "AM").replace("pm", "PM");
                if (indentOrderTime.length() == 6) {
                    indentOrderTime = "0" + indentOrderTime;
                }
                m_assert.assertTrue("Indent order time:<b> " + indentOrderTime + "</b>");
                indentOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderDate, "value");
                indentOrderDate = indentOrderDate.replaceAll("/", "-");
                m_assert.assertTrue("Indent order date:<b> " + indentOrderDate + "</b>");
                mapTracker.put(key_CreatedAt_IndentOrderFromStore, indentOrderDate + "  |  " + indentOrderTime);
                Cls_Generic_Methods.customWait();

                for (WebElement eItem : oPage_Indent.list_itemNameSelectedToCreateIndentPurchase) {
                    int index = oPage_Indent.list_itemNameSelectedToCreateIndentPurchase.indexOf(eItem);

                    Cls_Generic_Methods.scrollToElementByJS(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index));
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index), sQTY);
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Indent.text_multipleOfItemText)) {
                        String multipleOf = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_multipleOfItemText).split(" ")[6];
                        Cls_Generic_Methods.clearValuesInElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index));
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index), multipleOf);
                        quantityList.add(multipleOf);

                    } else {
                        quantityList.add(sQTY);
                    }
                    m_assert.assertInfo(" Quantity Entered as <b> " + quantityList.get(index) + " </b> for item name <b> " + itemList.get(index) + " </b> at index " + index);
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.list_subStoreSelectedToCreateIndentPurchase.get(index), sSubStore);

                }

                Cls_Generic_Methods.customWait();

                int totalQuantity = 0;
                for (int i = 0; i < quantityList.size(); i++) {
                    totalQuantity = totalQuantity + Integer.parseInt(quantityList.get(i));
                }

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Indent.text_totalQuantity).equalsIgnoreCase("Total Quantity :" + totalQuantity),
                        " Total Quantity For all Item displayed correctly as : " + totalQuantity);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder),
                        " Indent order saved");
                Cls_Generic_Methods.customWait(10);

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
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approveIndent, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_approveIndent), "Approve Button Cliked to approve Indent");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approveConfirmation, 20);
                    Cls_Generic_Methods.clickElement(oPage_Indent.button_approveConfirmation);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newOrder, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newOrder),
                            "Clicked on New Order button to create PO");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_IndentNumberInPurchaseOrderPopUp, 10);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_IndentOrderType, 10);
                    OrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderTime, "value");
                    OrderTime = OrderTime.replaceAll("\\s+", "");
                    OrderTime = OrderTime.replace("am", "AM").replace("pm", "PM");
                    if (OrderTime.length() == 6) {
                        OrderTime = "0" + OrderTime;
                    }
                    m_assert.assertTrue(" order time:<b> " + OrderTime + "</b>");

                    OrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderDate, "value");
                    OrderDate = OrderDate.replaceAll("/", "-");
                    m_assert.assertTrue(" order date:<b> " + OrderDate + "</b>");

                    mapTracker.put(key_CreatedAt_PurchaseOrderTroughIndent, OrderDate + "  |  " + OrderTime);
                    Cls_Generic_Methods.customWait(2);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_searchVendorAddress, sVendorName);
                    Cls_Generic_Methods.customWait();
                    String sSelectedVendor = Cls_Generic_Methods.getTextInElement(oPage_Indent.vendorAddressName);
                    if (sSelectedVendor.contains(sVendorName)) {
                        Cls_Generic_Methods.clickElement(oPage_Indent.vendorAddressName);
                        m_assert.assertTrue("Selected Vendor:  <b>" + sVendorName + "</b>");
                    } else {
                        m_assert.assertTrue("Required vendor <b>" + sVendorName + "</b> is NOT selected");
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_BillToStore, 10);

                }

                int j = 0;
                for (WebElement addButton : oPage_Indent.list_addActionListOfIndentOrder) {
                    if (j < 10) {
                        m_assert.assertTrue(Cls_Generic_Methods.doubleClickElement(addButton),
                                " Add Button Clicked");
                        Cls_Generic_Methods.customWait(1);
                    } else {
                        if (j > 19) {
                            break;
                        }
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(addButton),
                                " Add Button Clicked");
                        Cls_Generic_Methods.customWait(1);
                    }
                    j++;
                }

                for (WebElement rate : oPage_Indent.list_rateListOfIndentOrder) {
                    Double rateValue = nextDoubleBetween2(100.0d, 500.0d);
                    rateList.add(rateValue);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(rate, String.valueOf(rateValue)),
                            "Rate per unit  Entered as =  <b>" + rateValue + " </b>");
                }

                for (WebElement paidQty : oPage_Indent.list_paidQuantityListOfIndentOrder) {
                    Cls_Generic_Methods.clearValuesInElement(paidQty);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(paidQty, partialQty),
                            "QTY per unit Entered as =  <b>" + partialQty + " </b>");
                }

                Cls_Generic_Methods.clearValuesInElement(oPage_Indent.input_globalDiscount);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_globalDiscount, globalDiscountValue);
                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.selectElementByIndex(oPage_Indent.input_globalDiscountType, 1);
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_applyGlobalDiscount),
                            "Clicked on apply global discount button");
                    Cls_Generic_Methods.customWait(1);

                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Indent.list_discountValueListOfIndentOrder, 15);
                }

                String totalGrossAmount = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");

                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                for (int i = 0; i < oPage_Indent.list_rateListOfIndentOrder.size(); i++) {


                    String grossPerItemCalculated = String.valueOf(rateList.get(i) * Double.parseDouble(partialQty));
                    grossPerItemCalculated = decimalNumberUptoTwo(Double.parseDouble(grossPerItemCalculated));
                    String globalDiscountPerItemCalculated = getGlobalDiscount(globalDiscountValue + "%", totalGrossAmount, grossPerItemCalculated);
                    String globalDiscountPerItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.list_discountValueListOfIndentOrder.get(i)).replace("-", "").trim();
                    discountList.add(globalDiscountPerItemOnUI);
                    Float newGlobalDiscountPerItemCalculatedFloat = Float.parseFloat(globalDiscountPerItemCalculated) - 00.01f;
                    Double newGlobalDiscountPerItemCalculated = newGlobalDiscountPerItemCalculatedFloat.doubleValue();
                    m_assert.assertTrue(globalDiscountPerItemOnUI.equalsIgnoreCase(globalDiscountPerItemCalculated) ||
                                    globalDiscountPerItemOnUI.equalsIgnoreCase(decimalFormat.format(newGlobalDiscountPerItemCalculated)),
                            " Global Discount Working correctly as discount apply correctly for item as : " + globalDiscountPerItemOnUI + " at index " + i);
                }

                int deleteItemIndex[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
                for (int i = 0; i < oPage_Indent.list_rateListOfIndentOrder.size(); i++) {

                    if (i > 0 && i < 50) {
                        if (getNumberPresentInArray(deleteItemIndex, i) && i < 11) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                    "Delete Button Clicked on " + i);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                    "Delete Button Clicked on " + i);
                        } else {
                            if (i > 20) {
                                break;
                            } else {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                        "Delete Button Clicked on " + i);
                            }
                        }

                    }

                }


                String totalGrossAmountAfterDelete = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");
                Double grossPerItemCalculatedDouble = 0.00;
                Float grossPerItem0CalculatedDouble = 0.00f;
                Float grossPerItem5CalculatedDouble = 0.00f;
                Float grossPerItem12CalculatedDouble = 0.00f;
                Float grossPerItem18CalculatedDouble = 0.00f;
                Float grossPerItem28CalculatedDouble = 0.00f;
                Float discount0CalculatedDouble = 0.00f;
                Float discount5CalculatedDouble = 0.00f;
                Float discount12CalculatedDouble = 0.00f;
                Float discount18CalculatedDouble = 0.00f;
                Float discount28CalculatedDouble = 0.00f;

                Double gstCalculatedDouble = 0.00;
                Double gst5CalculatedDouble = 0.00;
                Double gst12CalculatedDouble = 0.00;
                Double gst18CalculatedDouble = 0.00;
                Double gst28CalculatedDouble = 0.00;

                String taxRateList[] = {"0", "5", "12", "18", "28"};
                for (WebElement eRate : oPage_Indent.list_rateListOfIndentOrder) {
                    int index = oPage_Indent.list_rateListOfIndentOrder.indexOf(eRate);
                    String rate = Cls_Generic_Methods.getElementAttribute(eRate, "value");
                    String sTaxRate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.list_taxRateListOfIndentOrder.get(index), "value");
                    String globalDiscountPerItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.list_discountValueListOfIndentOrder.get(index)).replace("-", "").trim();

                    if (sTaxRate.contains("5")) {
                        grossPerItem5CalculatedDouble = grossPerItem5CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(partialQty));
                        discount5CalculatedDouble = discount5CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);
                    } else if (sTaxRate.contains("12")) {
                        grossPerItem12CalculatedDouble = grossPerItem12CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(partialQty));
                        discount12CalculatedDouble = discount12CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);

                    } else if (sTaxRate.contains("18")) {
                        grossPerItem18CalculatedDouble = grossPerItem18CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(partialQty));
                        discount18CalculatedDouble = discount18CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);


                    } else if (sTaxRate.contains("28")) {
                        grossPerItem28CalculatedDouble = grossPerItem28CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(partialQty));
                        discount28CalculatedDouble = discount28CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);

                    } else {
                        grossPerItem0CalculatedDouble = grossPerItem0CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(partialQty));
                        discount0CalculatedDouble = discount0CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);
                    }

                    grossPerItemCalculatedDouble = grossPerItemCalculatedDouble + (Double.parseDouble(rate) * Double.parseDouble(partialQty));

                }


                String grossPerItemCalculated = decimalFormat.format(grossPerItemCalculatedDouble);
                Float totalDiscountCalculated = discount0CalculatedDouble + discount5CalculatedDouble + discount12CalculatedDouble + discount18CalculatedDouble + discount28CalculatedDouble;

                m_assert.assertTrue(totalGrossAmountAfterDelete.equalsIgnoreCase(grossPerItemCalculated),
                        " Gross Amount Calculated Correctly After Deleted as " + totalGrossAmountAfterDelete + "----" + grossPerItemCalculated);


                gst5CalculatedDouble = gst5CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem5CalculatedDouble), taxRateList[1], String.valueOf(discount5CalculatedDouble)));
                gst12CalculatedDouble = gst12CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem12CalculatedDouble), taxRateList[2], String.valueOf(discount12CalculatedDouble)));
                gst18CalculatedDouble = gst18CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem18CalculatedDouble), taxRateList[3], String.valueOf(discount18CalculatedDouble)));
                gst28CalculatedDouble = gst28CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem28CalculatedDouble), taxRateList[4], String.valueOf(discount28CalculatedDouble)));
                gstCalculatedDouble = gstCalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem0CalculatedDouble), taxRateList[0], String.valueOf(discount0CalculatedDouble)));


                String gstCalculated = decimalFormat.format(gstCalculatedDouble);
                String gst5Calculated = decimalFormat.format(gst5CalculatedDouble);
                String gst12Calculated = decimalFormat.format(gst12CalculatedDouble);
                String gst18Calculated = decimalFormat.format(gst18CalculatedDouble);
                String gst28Calculated = decimalFormat.format(gst28CalculatedDouble);

                List<String> gstValuesInView = new ArrayList<>();
                String gstOnUI = getGstOnUI(oPage_Indent.text_GST0OnUI);
                String gst5OnUI = getGstOnUI(oPage_Indent.text_GST5OnUI);
                String gst12OnUI = getGstOnUI(oPage_Indent.text_GST12OnUI);
                String gst18OnUI = getGstOnUI(oPage_Indent.text_GST18OnUI);
                String gst28OnUI = getGstOnUI(oPage_Indent.text_GST28OnUI);

                gstValuesInView.add(gstOnUI);
                gstValuesInView.add(gst5OnUI);
                gstValuesInView.add(gst12OnUI);
                gstValuesInView.add(gst18OnUI);
                gstValuesInView.add(gst28OnUI);


                Double totalGST = 0.00;
                for (String gstText : gstValuesInView) {
                    if (!gstText.isEmpty()) {
                        totalGST = totalGST + Double.parseDouble(gstText);
                    }
                }


               /* String gstOnUI = getGstOnUI(oPage_Indent.text_GST0OnUI);
                String gst5OnUI = getGstOnUI(oPage_Indent.text_GST5OnUI);
                String gst12OnUI = getGstOnUI(oPage_Indent.text_GST12OnUI);
                String gst18OnUI = getGstOnUI(oPage_Indent.text_GST18OnUI);
                String gst28OnUI = getGstOnUI(oPage_Indent.text_GST28OnUI);

                Double totalGST = 0.00;*/
                // totalGST = Double.parseDouble(gstOnUI) + Double.parseDouble(gst5OnUI )+Double.parseDouble( gst12OnUI) + Double.parseDouble(gst18OnUI) + Double.parseDouble(gst28OnUI);


                if (!gstOnUI.isEmpty()) {
                    String gstCalculatedValue[] = gstCalculated.replace(".", " ").split(" ");
                    m_assert.assertTrue(gstOnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST Amount Calculated Correctly After Deleted as " + gstOnUI + "----" + gstCalculated);
                }
                if (!gst5OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst5Calculated.replace(".", " ").split(" ");
                    m_assert.assertTrue(gst5OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST5 Amount Calculated Correctly After Deleted as " + gst5OnUI + "----" + gst5Calculated);
                }
                if (!gst12OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst12Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst12OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST12 Amount Calculated Correctly After Deleted as " + gst12OnUI + "----" + gst12Calculated);
                }
                if (!gst18OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst18Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst18OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST18 Amount Calculated Correctly After Deleted as " + gst18OnUI + "----" + gst18Calculated);
                }
                if (!gst28OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst28Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst28OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST28 Amount Calculated Correctly After Deleted as " + gst28OnUI + "----" + gst28Calculated);
                }

                String netAmountOnUi = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_netAmountOnUI, "value");
                String discountOnUi = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_discountOnUIText, "value");
                String gstCalculatedValue[] = String.valueOf(formatDecimalNumber(totalDiscountCalculated)).replace(".", " ").split(" ");

                m_assert.assertTrue(discountOnUi.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                        " Discount Calculated Correctly ");
                String netAmountCalculated = decimalFormat.format(Double.parseDouble(totalGrossAmountAfterDelete) + totalGST - Double.parseDouble(discountOnUi));

                m_assert.assertTrue(netAmountOnUi.equalsIgnoreCase(netAmountCalculated),
                        " Net Amount Calculated Correctly After Deleted as " + netAmountOnUi);

                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElement(oPage_Indent.button_cancelOtherCharges);
                Cls_Generic_Methods.customWait(1);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                        " Save Button Clicked In PO ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.option_purchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder),
                        "Purchase order option is selected");
                Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder);
                Cls_Generic_Methods.customWait(8);
                boolean recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }


                m_assert.assertTrue(recordFound, " PO Created Using Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approve, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_approve),
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
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newTransaction, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newTransaction), "New Transaction button clicked to create GRN");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 50);
                int mrpSize = oPage_Purchase.list_MRPPrice.size();
                for (int i = 0; i < mrpSize; i++) {
                    Double dMRP = nextDoubleBetween2(100.0d, 500.0d);
                    String sMRP = String.valueOf(dMRP);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(i), sMRP), "Entered MRP <b>" + sMRP + " </b>for Item" + i);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(i), (sBatchNumber + i).concat(getRandomString(2)));
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_inputPaidQty.get(i));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_inputPaidQty.get(i), String.valueOf((Double.parseDouble(partialQty)) / 2)), "Entered qty <b>" + ((Double.parseDouble(partialQty)) / 2) + " </b> to create partial GRN");
                }
                int expSize = oPage_Purchase.list_expiryDate.size();
                for (int i = 0; i < expSize; i++) {
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_expiryDate.get(i), "31/10/2024");
                }
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes),
                        " Transaction Notes Entered as :<b> " + transactionNotes + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType),
                        "Bill Type Selected:<b> " + billType + " </b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, (billNumber.concat(getRandomString(2)))),
                        "Bill Number: <b> " + (billNumber.concat(getRandomString(2))) + " </b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                //purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                if (purchaseTransactionTime.length() == 6) {
                    purchaseTransactionTime = "0" + purchaseTransactionTime;
                }
                purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);


                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                        "Date of bill selected:<b> " + oPage_Purchase.input_todayBillDate.getText() + " </b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "GRN created successfully for " + numberOfItem + " items");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 40);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                for (WebElement element : oPage_Purchase.list_purchaseTransactionDateandTimeList) {
                    String sDateAndTime = Cls_Generic_Methods.getTextInElement(element);
                    if (sDateAndTime.contains(purchaseTransactionDate) && sDateAndTime.contains(purchaseTransactionTime)) {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 8);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approveGRN, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_approveGRN), "GRN transaction approved successfully");
                Cls_Generic_Methods.customWait(15);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 40);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                Cls_Generic_Methods.customWait(8);
                recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(recordFound, " Order found in the purchase order page");
                String sPOStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);
                m_assert.assertTrue(sPOStatus.equalsIgnoreCase("Partially Completed"),
                        " Purchase Order Status Showing correctly as Partially Completed");
                Cls_Generic_Methods.customWait();
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
    public void createIndentPOForCompletedMultipleItem() {


        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        List<String> itemList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();
        List<String> updatedQuantityList = new ArrayList<>();
        List<Double> rateList = new ArrayList<>();
        List<String> discountList = new ArrayList<>();


        int numberOfItem = 20;
        boolean bIndentOrderFound = false;
        String globalDiscountValue = "10";


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sINDENT_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_addNewIndent),
                        "New Button clicked in Order: Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 10);
                String sSelectedButton = Cls_Generic_Methods.getTextInElement(oPage_Indent.button_variantOrRequisitionSelected);
                if (sSelectedButton.equalsIgnoreCase(sExpectedButtonToBeSelected)) {
                    boolean ItemList = oPage_Indent.list_ItemDescriptionsUnderIndentPurchase.isEmpty();
                    if (ItemList) {
                        m_assert.assertTrue("ItemList is empty");
                    } else {
                        m_assert.assertTrue(false, "ItemList is not empty");
                    }
                } else {
                    m_assert.assertTrue("Expected Button = <b>" + sExpectedButtonToBeSelected + "</b> is not selected");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_vendorField, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.select_vendorField),
                        "clicked on Store selection field");
                Cls_Generic_Methods.customWait();
                boolean storeFound = false;
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, sStoreToShipOrToBillOrder);
                Cls_Generic_Methods.customWait();
                for (WebElement eStoreName : oPage_Indent.list_stores) {
                    if (Cls_Generic_Methods.getTextInElement(eStoreName).contains(sStoreToShipOrToBillOrder)) {
                        Cls_Generic_Methods.clickElement(eStoreName);
                        storeFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(storeFound, "Store found to do indent purchase : <b> " + sINDENT_STORE + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_noteUnderIndentForPurchase, 10);

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
                boolean bItemSelected = CommonActions.selectItemInInventory(oPage_Indent.list_ItemDescriptionsUnderIndentPurchase, itemList, numberOfItem, oPage_Indent.list_itemNameSelectedToCreateIndentPurchase);

                m_assert.assertTrue(bItemSelected, numberOfItem + " Item Selected From Left Panel Correctly ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_indentOrderDate, 10);
                indentOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderTime, "value");
                indentOrderTime = indentOrderTime.replaceAll("\\s+", "");
                indentOrderTime = indentOrderTime.replace("am", "AM").replace("pm", "PM");
                if (indentOrderTime.length() == 6) {
                    indentOrderTime = "0" + indentOrderTime;
                }
                m_assert.assertTrue("Indent order time:<b> " + indentOrderTime + "</b>");
                indentOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderDate, "value");
                indentOrderDate = indentOrderDate.replaceAll("/", "-");
                m_assert.assertTrue("Indent order date:<b> " + indentOrderDate + "</b>");
                mapTracker.put(key_CreatedAt_IndentOrderFromStore, indentOrderDate + "  |  " + indentOrderTime);
                Cls_Generic_Methods.customWait();

                for (WebElement eItem : oPage_Indent.list_itemNameSelectedToCreateIndentPurchase) {
                    int index = oPage_Indent.list_itemNameSelectedToCreateIndentPurchase.indexOf(eItem);

                    Cls_Generic_Methods.scrollToElementByJS(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index));
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index), sQTY);
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Indent.text_multipleOfItemText)) {
                        String multipleOf = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_multipleOfItemText).split(" ")[6];
                        Cls_Generic_Methods.clearValuesInElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index));
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index), multipleOf);
                        quantityList.add(multipleOf);

                    } else {
                        quantityList.add(sQTY);
                    }
                    m_assert.assertInfo(" Quantity Entered as <b> " + quantityList.get(index) + " </b> for item name <b> " + itemList.get(index) + " </b> at index " + index);
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.list_subStoreSelectedToCreateIndentPurchase.get(index), sSubStore);

                }

                Cls_Generic_Methods.customWait();

                int totalQuantity = 0;
                for (int i = 0; i < quantityList.size(); i++) {
                    totalQuantity = totalQuantity + Integer.parseInt(quantityList.get(i));
                }

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Indent.text_totalQuantity).equalsIgnoreCase("Total Quantity :" + totalQuantity),
                        " Total Quantity For all Item displayed correctly as : " + totalQuantity);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder),
                        " Indent order saved");
                Cls_Generic_Methods.customWait(10);

                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(date) &&
                            mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(time)) {
                        bIndentOrderFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(bIndentOrderFound, "Order found in the Indent order page");

                if (bIndentOrderFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approveIndent, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_approveIndent), "Approve Button Cliked to approve Indent");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approveConfirmation, 20);
                    Cls_Generic_Methods.clickElement(oPage_Indent.button_approveConfirmation);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newOrder, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newOrder),
                            "Clicked on New Order button to create PO");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_IndentNumberInPurchaseOrderPopUp, 10);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_IndentOrderType, 10);
                    OrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderTime, "value");
                    OrderTime = OrderTime.replaceAll("\\s+", "");
                    OrderTime = OrderTime.replace("am", "AM").replace("pm", "PM");
                    if (OrderTime.length() == 6) {
                        OrderTime = "0" + OrderTime;
                    }
                    m_assert.assertTrue(" order time:<b> " + OrderTime + "</b>");

                    OrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderDate, "value");
                    OrderDate = OrderDate.replaceAll("/", "-");
                    m_assert.assertTrue(" order date:<b> " + OrderDate + "</b>");

                    mapTracker.put(key_CreatedAt_PurchaseOrderTroughIndent, OrderDate + "  |  " + OrderTime);
                    Cls_Generic_Methods.customWait(2);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_searchVendorAddress, sVendorName);
                    Cls_Generic_Methods.customWait(3);
                    String sSelectedVendor = Cls_Generic_Methods.getTextInElement(oPage_Indent.vendorAddressName);
                    if (sSelectedVendor.contains(sVendorName)) {
                        Cls_Generic_Methods.clickElement(oPage_Indent.vendorAddressName);
                        m_assert.assertTrue("Selected Vendor:  <b>" + sVendorName + "</b>");
                    } else {
                        m_assert.assertTrue("Required vendor <b>" + sVendorName + "</b> is NOT selected");
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_BillToStore, 10);

                }

                int j = 0;
                for (WebElement addButton : oPage_Indent.list_addActionListOfIndentOrder) {
                    if (j < 10) {
                        m_assert.assertTrue(Cls_Generic_Methods.doubleClickElement(addButton),
                                " Add Button Clicked");
                        Cls_Generic_Methods.customWait(1);
                    } else {
                        if (j > 19) {
                            break;
                        }
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(addButton),
                                " Add Button Clicked");
                        Cls_Generic_Methods.customWait(1);
                    }
                    j++;
                }

                for (WebElement rate : oPage_Indent.list_rateListOfIndentOrder) {
                    Double rateValue = nextDoubleBetween2(100.0d, 500.0d);
                    rateList.add(rateValue);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(rate, String.valueOf(rateValue)),
                            "Rate per unit  Entered as =  <b>" + rateValue + " </b>");
                }

                for (WebElement paidQty : oPage_Indent.list_paidQuantityListOfIndentOrder) {
                    String qty = Cls_Generic_Methods.getElementAttribute(paidQty, "value");
                    Double qtyDouble = Double.parseDouble(qty);
                    if (qty.isEmpty() || qtyDouble < 1) {
                        Cls_Generic_Methods.clearValuesInElement(paidQty);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(paidQty, sQTY),
                                "QTY per unit Entered as =  <b>" + sQTY + " </b>");
                        updatedQuantityList.add(sQTY);
                    } else {
                        updatedQuantityList.add(qty);
                    }
                }

                Cls_Generic_Methods.clearValuesInElement(oPage_Indent.input_globalDiscount);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_globalDiscount, globalDiscountValue);
                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.selectElementByIndex(oPage_Indent.input_globalDiscountType, 1);
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_applyGlobalDiscount),
                            "Clicked on apply global discount button");
                    Cls_Generic_Methods.customWait(5);

                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Indent.list_discountValueListOfIndentOrder, 15);
                }

                String totalGrossAmount = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");

                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                for (int i = 0; i < oPage_Indent.list_rateListOfIndentOrder.size(); i++) {


                    String grossPerItemCalculated = String.valueOf(rateList.get(i) * Double.parseDouble(updatedQuantityList.get(i)));
                    grossPerItemCalculated = decimalNumberUptoTwo(Double.parseDouble(grossPerItemCalculated));
                    String globalDiscountPerItemCalculated = getGlobalDiscount(globalDiscountValue + "%", totalGrossAmount, grossPerItemCalculated);
                    String globalDiscountPerItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.list_discountValueListOfIndentOrder.get(i)).replace("-", "").trim();
                    discountList.add(globalDiscountPerItemOnUI);
                    Float newGlobalDiscountPerItemCalculatedFloat = Float.parseFloat(globalDiscountPerItemCalculated) - 00.01f;
                    Double newGlobalDiscountPerItemCalculated = newGlobalDiscountPerItemCalculatedFloat.doubleValue();
                    m_assert.assertTrue(globalDiscountPerItemOnUI.equalsIgnoreCase(globalDiscountPerItemCalculated) ||
                                    globalDiscountPerItemOnUI.equalsIgnoreCase(decimalFormat.format(newGlobalDiscountPerItemCalculated)),
                            " Global Discount Working correctly as discount apply correctly for item as : " + globalDiscountPerItemOnUI + " at index " + i);
                }

                int deleteItemIndex[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
                for (int i = 0; i < oPage_Indent.list_rateListOfIndentOrder.size(); i++) {

                    if (i > 0 && i < 50) {
                        if (getNumberPresentInArray(deleteItemIndex, i) && i < 11) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                    "Delete Button Clicked on " + i);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                    "Delete Button Clicked on " + i);
                        } else {
                            if (i > 20) {
                                break;
                            } else {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                        "Delete Button Clicked on " + i);
                            }
                        }

                    }

                }


                String totalGrossAmountAfterDelete = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");
                Double grossPerItemCalculatedDouble = 0.00;
                Float grossPerItem0CalculatedDouble = 0.00f;
                Float grossPerItem5CalculatedDouble = 0.00f;
                Float grossPerItem12CalculatedDouble = 0.00f;
                Float grossPerItem18CalculatedDouble = 0.00f;
                Float grossPerItem28CalculatedDouble = 0.00f;
                Float discount0CalculatedDouble = 0.00f;
                Float discount5CalculatedDouble = 0.00f;
                Float discount12CalculatedDouble = 0.00f;
                Float discount18CalculatedDouble = 0.00f;
                Float discount28CalculatedDouble = 0.00f;

                Double gstCalculatedDouble = 0.00;
                Double gst5CalculatedDouble = 0.00;
                Double gst12CalculatedDouble = 0.00;
                Double gst18CalculatedDouble = 0.00;
                Double gst28CalculatedDouble = 0.00;

                String taxRateList[] = {"0", "5", "12", "18", "28"};
                for (WebElement eRate : oPage_Indent.list_rateListOfIndentOrder) {
                    int index = oPage_Indent.list_rateListOfIndentOrder.indexOf(eRate);
                    String rate = Cls_Generic_Methods.getElementAttribute(eRate, "value");
                    String sTaxRate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.list_taxRateListOfIndentOrder.get(index), "value");
                    String globalDiscountPerItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.list_discountValueListOfIndentOrder.get(index)).replace("-", "").trim();

                    if (sTaxRate.contains("5")) {
                        grossPerItem5CalculatedDouble = grossPerItem5CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(quantityList.get(index)));
                        discount5CalculatedDouble = discount5CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);
                    } else if (sTaxRate.contains("12")) {
                        grossPerItem12CalculatedDouble = grossPerItem12CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(quantityList.get(index)));
                        discount12CalculatedDouble = discount12CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);

                    } else if (sTaxRate.contains("18")) {
                        grossPerItem18CalculatedDouble = grossPerItem18CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(quantityList.get(index)));
                        discount18CalculatedDouble = discount18CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);


                    } else if (sTaxRate.contains("28")) {
                        grossPerItem28CalculatedDouble = grossPerItem28CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(quantityList.get(index)));
                        discount28CalculatedDouble = discount28CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);

                    } else {
                        grossPerItem0CalculatedDouble = grossPerItem0CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(quantityList.get(index)));
                        discount0CalculatedDouble = discount0CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);
                    }

                    grossPerItemCalculatedDouble = grossPerItemCalculatedDouble + (Double.parseDouble(rate) * Double.parseDouble(quantityList.get(index)));

                }


                String grossPerItemCalculated = decimalFormat.format(grossPerItemCalculatedDouble);
                Float totalDiscountCalculated = discount0CalculatedDouble + discount5CalculatedDouble + discount12CalculatedDouble + discount18CalculatedDouble + discount28CalculatedDouble;

                m_assert.assertTrue(totalGrossAmountAfterDelete.equalsIgnoreCase(grossPerItemCalculated),
                        " Gross Amount Calculated Correctly After Deleted as " + totalGrossAmountAfterDelete + "----" + grossPerItemCalculated);


                gst5CalculatedDouble = gst5CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem5CalculatedDouble), taxRateList[1], String.valueOf(discount5CalculatedDouble)));
                gst12CalculatedDouble = gst12CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem12CalculatedDouble), taxRateList[2], String.valueOf(discount12CalculatedDouble)));
                gst18CalculatedDouble = gst18CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem18CalculatedDouble), taxRateList[3], String.valueOf(discount18CalculatedDouble)));
                gst28CalculatedDouble = gst28CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem28CalculatedDouble), taxRateList[4], String.valueOf(discount28CalculatedDouble)));
                gstCalculatedDouble = gstCalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem0CalculatedDouble), taxRateList[0], String.valueOf(discount0CalculatedDouble)));


                String gstCalculated = decimalFormat.format(gstCalculatedDouble);
                String gst5Calculated = decimalFormat.format(gst5CalculatedDouble);
                String gst12Calculated = decimalFormat.format(gst12CalculatedDouble);
                String gst18Calculated = decimalFormat.format(gst18CalculatedDouble);
                String gst28Calculated = decimalFormat.format(gst28CalculatedDouble);


                List<String> gstValuesInView = new ArrayList<>();
                String gstOnUI = getGstOnUI(oPage_Indent.text_GST0OnUI);
                String gst5OnUI = getGstOnUI(oPage_Indent.text_GST5OnUI);
                String gst12OnUI = getGstOnUI(oPage_Indent.text_GST12OnUI);
                String gst18OnUI = getGstOnUI(oPage_Indent.text_GST18OnUI);
                String gst28OnUI = getGstOnUI(oPage_Indent.text_GST28OnUI);

                gstValuesInView.add(gstOnUI);
                gstValuesInView.add(gst5OnUI);
                gstValuesInView.add(gst12OnUI);
                gstValuesInView.add(gst18OnUI);
                gstValuesInView.add(gst28OnUI);


                Double totalGST = 0.00;
                for (String gstText : gstValuesInView) {
                    if (!gstText.isEmpty()) {
                        totalGST = totalGST + Double.parseDouble(gstText);
                    }
                }

                if (!gstOnUI.isEmpty()) {
                    String gstCalculatedValue[] = gstCalculated.replace(".", " ").split(" ");
                    m_assert.assertTrue(gstOnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST Amount Calculated Correctly After Deleted as " + gstOnUI + "----" + gstCalculated);
                }
                if (!gst5OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst5Calculated.replace(".", " ").split(" ");
                    m_assert.assertTrue(gst5OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST5 Amount Calculated Correctly After Deleted as " + gst5OnUI + "----" + gst5Calculated);
                }
                if (!gst12OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst12Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst12OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST12 Amount Calculated Correctly After Deleted as " + gst12OnUI + "----" + gst12Calculated);
                }
                if (!gst18OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst18Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst18OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST18 Amount Calculated Correctly After Deleted as " + gst18OnUI + "----" + gst18Calculated);
                }
                if (!gst28OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst28Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst28OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST28 Amount Calculated Correctly After Deleted as " + gst28OnUI + "----" + gst28Calculated);
                }


                String netAmountOnUi = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_netAmountOnUI, "value");
                String discountOnUi = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_discountOnUIText, "value");
                String gstCalculatedValue[] = String.valueOf(formatDecimalNumber(totalDiscountCalculated)).replace(".", " ").split(" ");

                m_assert.assertTrue(discountOnUi.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                        " Discount Calculated Correctly ");
                String netAmountCalculated = decimalFormat.format(Double.parseDouble(totalGrossAmountAfterDelete) + totalGST - Double.parseDouble(discountOnUi));

                m_assert.assertTrue(netAmountOnUi.equalsIgnoreCase(netAmountCalculated),
                        " Net Amount Calculated Correctly After Deleted as " + netAmountOnUi);

                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElement(oPage_Indent.button_cancelOtherCharges);
                Cls_Generic_Methods.customWait(1);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                        " Save Button Clicked In PO ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.option_purchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder),
                        "Purchase order option is selected");
                Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder);
                Cls_Generic_Methods.customWait(8);
                boolean recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }


                m_assert.assertTrue(recordFound, " PO Created Using Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approve, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_approve),
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
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newTransaction, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newTransaction), "New Transaction button clicked to create GRN");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 30);
                int mrpSize = oPage_Purchase.list_MRPPrice.size();
                for (int i = 0; i < mrpSize; i++) {
                    Double dMRP = nextDoubleBetween2(100.0d, 500.0d);
                    String sMRP = String.valueOf(dMRP);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(i), sMRP), "Entered MRP <b>" + sMRP + " </b>for Item" + i);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(i), sBatchNumber + i);
                }
                int expSize = oPage_Purchase.list_expiryDate.size();
                for (int i = 0; i < expSize; i++) {
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_expiryDate.get(i), "31/10/2024");
                }
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes),
                        " Transaction Notes Entered as :<b> " + transactionNotes + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType),
                        "Bill Type Selected:<b> " + billType + " </b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber),
                        "Bill Number: <b> " + billNumber + " </b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                //purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                if (purchaseTransactionTime.length() == 6) {
                    purchaseTransactionTime = "0" + purchaseTransactionTime;
                }
                purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);


                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                        "Date of bill selected:<b> " + oPage_Purchase.input_todayBillDate.getText() + " </b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "GRN created successfully for " + numberOfItem + " items");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 40);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                for (WebElement element : oPage_Purchase.list_purchaseTransactionDateandTimeList) {
                    String sDateAndTime = Cls_Generic_Methods.getTextInElement(element);
                    if (sDateAndTime.contains(purchaseTransactionDate) && sDateAndTime.contains(purchaseTransactionTime)) {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 8);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approveGRN, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_approveGRN), "GRN transaction approved successfully");
                Cls_Generic_Methods.customWait(15);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                Cls_Generic_Methods.customWait(8);
                recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(recordFound, " Order found in the purchase order page");
                String sPOStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);
                m_assert.assertTrue(sPOStatus.equalsIgnoreCase("Completed"),
                        " Purchase Order Status Showing correctly as Completed");
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(date) &&
                            mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(time)) {
                        bIndentOrderFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(bIndentOrderFound, "Order found in the Indent order page");

                String indentStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);

                m_assert.assertTrue(indentStatus.equalsIgnoreCase("Completed"),
                        " Indent Status Showing correctly as Completed");

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
    public void createIndentPOForPartialItem() {


        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        List<String> itemList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();
        List<String> updatedQuantityList = new ArrayList<>();

        List<Double> rateList = new ArrayList<>();
        List<String> discountList = new ArrayList<>();


        int numberOfItem = 40;
        boolean bIndentOrderFound = false;
        String globalDiscountValue = "10";


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sINDENT_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_addNewIndent),
                        "New Button clicked in Order: Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 10);
                String sSelectedButton = Cls_Generic_Methods.getTextInElement(oPage_Indent.button_variantOrRequisitionSelected);
                if (sSelectedButton.equalsIgnoreCase(sExpectedButtonToBeSelected)) {
                    boolean ItemList = oPage_Indent.list_ItemDescriptionsUnderIndentPurchase.isEmpty();
                    if (ItemList) {
                        m_assert.assertTrue("ItemList is empty");
                    } else {
                        m_assert.assertTrue(false, "ItemList is not empty");
                    }
                } else {
                    m_assert.assertTrue("Expected Button = <b>" + sExpectedButtonToBeSelected + "</b> is not selected");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_vendorField, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.select_vendorField),
                        "clicked on Store selection field");
                Cls_Generic_Methods.customWait();
                boolean storeFound = false;
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, sStoreToShipOrToBillOrder);
                Cls_Generic_Methods.customWait();
                for (WebElement eStoreName : oPage_Indent.list_stores) {
                    if (Cls_Generic_Methods.getTextInElement(eStoreName).contains(sStoreToShipOrToBillOrder)) {
                        Cls_Generic_Methods.clickElement(eStoreName);
                        storeFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(storeFound, "Store found to do indent purchase : <b> " + sINDENT_STORE + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_noteUnderIndentForPurchase, 10);

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
                boolean bItemSelected = CommonActions.selectItemInInventory(oPage_Indent.list_ItemDescriptionsUnderIndentPurchase, itemList, numberOfItem, oPage_Indent.list_itemNameSelectedToCreateIndentPurchase);

                m_assert.assertTrue(bItemSelected, numberOfItem + " Item Selected From Left Panel Correctly ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_indentOrderDate, 10);
                indentOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderTime, "value");
                indentOrderTime = indentOrderTime.replaceAll("\\s+", "");
                indentOrderTime = indentOrderTime.replace("am", "AM").replace("pm", "PM");
                if (indentOrderTime.length() == 6) {
                    indentOrderTime = "0" + indentOrderTime;
                }
                m_assert.assertTrue("Indent order time:<b> " + indentOrderTime + "</b>");
                indentOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderDate, "value");
                indentOrderDate = indentOrderDate.replaceAll("/", "-");
                m_assert.assertTrue("Indent order date:<b> " + indentOrderDate + "</b>");
                mapTracker.put(key_CreatedAt_IndentOrderFromStore, indentOrderDate + "  |  " + indentOrderTime);
                Cls_Generic_Methods.customWait();

                for (WebElement eItem : oPage_Indent.list_itemNameSelectedToCreateIndentPurchase) {
                    int index = oPage_Indent.list_itemNameSelectedToCreateIndentPurchase.indexOf(eItem);

                    Cls_Generic_Methods.scrollToElementByJS(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index));
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index), sQTY);
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Indent.text_multipleOfItemText)) {
                        String multipleOf = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_multipleOfItemText).split(" ")[6];
                        Cls_Generic_Methods.clearValuesInElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index));
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index), multipleOf);
                        quantityList.add(multipleOf);

                    } else {
                        quantityList.add(sQTY);
                    }
                    m_assert.assertInfo(" Quantity Entered as <b> " + quantityList.get(index) + " </b> for item name <b> " + itemList.get(index) + " </b> at index " + index);
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.list_subStoreSelectedToCreateIndentPurchase.get(index), sSubStore);

                }

                Cls_Generic_Methods.customWait();

                int totalQuantity = 0;
                for (int i = 0; i < quantityList.size(); i++) {
                    totalQuantity = totalQuantity + Integer.parseInt(quantityList.get(i));
                }

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Indent.text_totalQuantity).equalsIgnoreCase("Total Quantity :" + totalQuantity),
                        " Total Quantity For all Item displayed correctly as : " + totalQuantity);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder),
                        " Indent order saved");
                Cls_Generic_Methods.customWait(10);

                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(date) &&
                            mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(time)) {
                        bIndentOrderFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(bIndentOrderFound, "Order found in the Indent order page");

                if (bIndentOrderFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approveIndent, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_approveIndent), "Approve Button Cliked to approve Indent");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approveConfirmation, 20);
                    Cls_Generic_Methods.clickElement(oPage_Indent.button_approveConfirmation);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newOrder, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newOrder),
                            "Clicked on New Order button to create PO");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_IndentNumberInPurchaseOrderPopUp, 10);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_IndentOrderType, 10);
                    OrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderTime, "value");
                    OrderTime = OrderTime.replaceAll("\\s+", "");
                    OrderTime = OrderTime.replace("am", "AM").replace("pm", "PM");
                    if (OrderTime.length() == 6) {
                        OrderTime = "0" + OrderTime;
                    }
                    m_assert.assertTrue(" order time:<b> " + OrderTime + "</b>");

                    OrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderDate, "value");
                    OrderDate = OrderDate.replaceAll("/", "-");
                    m_assert.assertTrue(" order date:<b> " + OrderDate + "</b>");

                    mapTracker.put(key_CreatedAt_PurchaseOrderTroughIndent, OrderDate + "  |  " + OrderTime);
                    Cls_Generic_Methods.customWait(2);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_searchVendorAddress, sVendorName);
                    Cls_Generic_Methods.customWait(3);
                    String sSelectedVendor = Cls_Generic_Methods.getTextInElement(oPage_Indent.vendorAddressName);
                    if (sSelectedVendor.contains(sVendorName)) {
                        Cls_Generic_Methods.clickElement(oPage_Indent.vendorAddressName);
                        m_assert.assertTrue("Selected Vendor:  <b>" + sVendorName + "</b>");
                    } else {
                        m_assert.assertTrue("Required vendor <b>" + sVendorName + "</b> is NOT selected");
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_BillToStore, 10);

                }

                int deleteItemNumber = numberOfItem / 2;
                for (int i = deleteItemNumber; i < oPage_Indent.list_rateListOfIndentOrder.size(); i++) {

                    m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByJS(oPage_Indent.list_deleteItemButton.get(i)),
                            " Scroll to Element");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                            "Delete Button Clicked on " + i);

                }


                int j = 0;
                for (WebElement addButton : oPage_Indent.list_addActionListOfIndentOrder) {
                    if (j < 10) {
                        m_assert.assertTrue(Cls_Generic_Methods.doubleClickElement(addButton),
                                " Add Button Clicked");
                        Cls_Generic_Methods.customWait(1);
                    } else {
                        if (j > 19) {
                            break;
                        }
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(addButton),
                                " Add Button Clicked");
                        Cls_Generic_Methods.customWait(1);
                    }
                    j++;
                }

                for (WebElement rate : oPage_Indent.list_rateListOfIndentOrder) {
                    Double rateValue = nextDoubleBetween2(100.0d, 500.0d);
                    rateList.add(rateValue);
                    if (Cls_Generic_Methods.isElementEnabled(rate)) {
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(rate, String.valueOf(rateValue)),
                                "Rate per unit  Entered as =  <b>" + rateValue + " </b>");
                    }
                }

                for (WebElement paidQty : oPage_Indent.list_paidQuantityListOfIndentOrder) {
                    String qty = Cls_Generic_Methods.getElementAttribute(paidQty, "value");
                    Double qtyDouble = Double.parseDouble(qty);
                    if (Cls_Generic_Methods.isElementEnabled(paidQty)) {
                        if (qty.isEmpty() || qtyDouble < 1) {
                            Cls_Generic_Methods.clearValuesInElement(paidQty);
                            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(paidQty, sQTY),
                                    "QTY per unit Entered as =  <b>" + sQTY + " </b>");
                            updatedQuantityList.add(sQTY);
                        } else {
                            updatedQuantityList.add(qty);
                        }
                    }
                }

                Cls_Generic_Methods.clearValuesInElement(oPage_Indent.input_globalDiscount);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_globalDiscount, globalDiscountValue);
                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.selectElementByIndex(oPage_Indent.input_globalDiscountType, 1);
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_applyGlobalDiscount),
                            "Clicked on apply global discount button");
                    Cls_Generic_Methods.customWait(1);

                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Indent.list_discountValueListOfIndentOrder, 15);
                }

                String totalGrossAmount = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");

                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                for (int i = 0; i < oPage_Indent.list_rateListOfIndentOrder.size() - deleteItemNumber; i++) {


                    String grossPerItemCalculated = String.valueOf(rateList.get(i) * Double.parseDouble(updatedQuantityList.get(i)));
                    grossPerItemCalculated = decimalNumberUptoTwo(Double.parseDouble(grossPerItemCalculated));
                    String globalDiscountPerItemCalculated = getGlobalDiscount(globalDiscountValue + "%", totalGrossAmount, grossPerItemCalculated);
                    String globalDiscountPerItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.list_discountValueListOfIndentOrder.get(i)).replace("-", "").trim();
                    discountList.add(globalDiscountPerItemOnUI);
                    Float newGlobalDiscountPerItemCalculatedFloat = Float.parseFloat(globalDiscountPerItemCalculated) - 00.01f;
                    Double newGlobalDiscountPerItemCalculated = newGlobalDiscountPerItemCalculatedFloat.doubleValue();
                    m_assert.assertTrue(globalDiscountPerItemOnUI.equalsIgnoreCase(globalDiscountPerItemCalculated) ||
                                    globalDiscountPerItemOnUI.equalsIgnoreCase(decimalFormat.format(newGlobalDiscountPerItemCalculated)),
                            " Global Discount Working correctly as discount apply correctly for item as : " + globalDiscountPerItemOnUI + " at index " + i);
                }

                int deleteItemIndex[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
                for (int i = 0; i < oPage_Indent.list_rateListOfIndentOrder.size(); i++) {

                    if (i > 0 && i < 50) {
                        if (getNumberPresentInArray(deleteItemIndex, i) && i < 11) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                    "Delete Button Clicked on " + i);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                    "Delete Button Clicked on " + i);
                        } else {
                            if (i > 20) {
                                break;
                            } else {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                        "Delete Button Clicked on " + i);
                            }
                        }

                    }

                }


                String totalGrossAmountAfterDelete = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");
                Double grossPerItemCalculatedDouble = 0.00;
                Float grossPerItem0CalculatedDouble = 0.00f;
                Float grossPerItem5CalculatedDouble = 0.00f;
                Float grossPerItem12CalculatedDouble = 0.00f;
                Float grossPerItem18CalculatedDouble = 0.00f;
                Float grossPerItem28CalculatedDouble = 0.00f;
                Float discount0CalculatedDouble = 0.00f;
                Float discount5CalculatedDouble = 0.00f;
                Float discount12CalculatedDouble = 0.00f;
                Float discount18CalculatedDouble = 0.00f;
                Float discount28CalculatedDouble = 0.00f;

                Double gstCalculatedDouble = 0.00;
                Double gst5CalculatedDouble = 0.00;
                Double gst12CalculatedDouble = 0.00;
                Double gst18CalculatedDouble = 0.00;
                Double gst28CalculatedDouble = 0.00;

                String taxRateList[] = {"0", "5", "12", "18", "28"};
                for (WebElement eRate : oPage_Indent.list_rateListOfIndentOrder) {
                    int index = oPage_Indent.list_rateListOfIndentOrder.indexOf(eRate);
                    if (Cls_Generic_Methods.isElementEnabled(eRate)) {
                        String rate = Cls_Generic_Methods.getElementAttribute(eRate, "value");
                        String sTaxRate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.list_taxRateListOfIndentOrder.get(index), "value");
                        String globalDiscountPerItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.list_discountValueListOfIndentOrder.get(index)).replace("-", "").trim();

                        if (sTaxRate.contains("5")) {
                            grossPerItem5CalculatedDouble = grossPerItem5CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(quantityList.get(index)));
                            discount5CalculatedDouble = discount5CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);
                        } else if (sTaxRate.contains("12")) {
                            grossPerItem12CalculatedDouble = grossPerItem12CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(quantityList.get(index)));
                            discount12CalculatedDouble = discount12CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);

                        } else if (sTaxRate.contains("18")) {
                            grossPerItem18CalculatedDouble = grossPerItem18CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(quantityList.get(index)));
                            discount18CalculatedDouble = discount18CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);


                        } else if (sTaxRate.contains("28")) {
                            grossPerItem28CalculatedDouble = grossPerItem28CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(quantityList.get(index)));
                            discount28CalculatedDouble = discount28CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);

                        } else {
                            grossPerItem0CalculatedDouble = grossPerItem0CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(quantityList.get(index)));
                            discount0CalculatedDouble = discount0CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);
                        }

                        grossPerItemCalculatedDouble = grossPerItemCalculatedDouble + (Double.parseDouble(rate) * Double.parseDouble(quantityList.get(index)));
                    }
                }


                String grossPerItemCalculated = decimalFormat.format(grossPerItemCalculatedDouble);
                Float totalDiscountCalculated = discount0CalculatedDouble + discount5CalculatedDouble + discount12CalculatedDouble + discount18CalculatedDouble + discount28CalculatedDouble;

                m_assert.assertTrue(totalGrossAmountAfterDelete.equalsIgnoreCase(grossPerItemCalculated),
                        " Gross Amount Calculated Correctly After Deleted as " + totalGrossAmountAfterDelete + "----" + grossPerItemCalculated);


                gst5CalculatedDouble = gst5CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem5CalculatedDouble), taxRateList[1], String.valueOf(discount5CalculatedDouble)));
                gst12CalculatedDouble = gst12CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem12CalculatedDouble), taxRateList[2], String.valueOf(discount12CalculatedDouble)));
                gst18CalculatedDouble = gst18CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem18CalculatedDouble), taxRateList[3], String.valueOf(discount18CalculatedDouble)));
                gst28CalculatedDouble = gst28CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem28CalculatedDouble), taxRateList[4], String.valueOf(discount28CalculatedDouble)));
                gstCalculatedDouble = gstCalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem0CalculatedDouble), taxRateList[0], String.valueOf(discount0CalculatedDouble)));


                String gstCalculated = decimalFormat.format(gstCalculatedDouble);
                String gst5Calculated = decimalFormat.format(gst5CalculatedDouble);
                String gst12Calculated = decimalFormat.format(gst12CalculatedDouble);
                String gst18Calculated = decimalFormat.format(gst18CalculatedDouble);
                String gst28Calculated = decimalFormat.format(gst28CalculatedDouble);


                List<String> gstValuesInView = new ArrayList<>();
                String gstOnUI = getGstOnUI(oPage_Indent.text_GST0OnUI);
                String gst5OnUI = getGstOnUI(oPage_Indent.text_GST5OnUI);
                String gst12OnUI = getGstOnUI(oPage_Indent.text_GST12OnUI);
                String gst18OnUI = getGstOnUI(oPage_Indent.text_GST18OnUI);
                String gst28OnUI = getGstOnUI(oPage_Indent.text_GST28OnUI);

                gstValuesInView.add(gstOnUI);
                gstValuesInView.add(gst5OnUI);
                gstValuesInView.add(gst12OnUI);
                gstValuesInView.add(gst18OnUI);
                gstValuesInView.add(gst28OnUI);

                //  Double totalGST = 0.00;
                //  totalGST = Double.parseDouble(gstOnUI) + Double.parseDouble(gst5OnUI )+Double.parseDouble( gst12OnUI) + Double.parseDouble(gst18OnUI) + Double.parseDouble(gst28OnUI);
                Double totalGST = 0.00;
                for (String gstText : gstValuesInView) {
                    if (!gstText.isEmpty()) {
                        totalGST = totalGST + Double.parseDouble(gstText);
                    }
                }

                if (!gstOnUI.isEmpty()) {
                    String gstCalculatedValue[] = gstCalculated.replace(".", " ").split(" ");
                    m_assert.assertTrue(gstOnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST Amount Calculated Correctly After Deleted as " + gstOnUI + "----" + gstCalculated);
                }
                if (!gst5OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst5Calculated.replace(".", " ").split(" ");
                    m_assert.assertTrue(gst5OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST5 Amount Calculated Correctly After Deleted as " + gst5OnUI + "----" + gst5Calculated);
                }
                if (!gst12OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst12Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst12OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST12 Amount Calculated Correctly After Deleted as " + gst12OnUI + "----" + gst12Calculated);
                }
                if (!gst18OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst18Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst18OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST18 Amount Calculated Correctly After Deleted as " + gst18OnUI + "----" + gst18Calculated);
                }
                if (!gst28OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst28Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst28OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST28 Amount Calculated Correctly After Deleted as " + gst28OnUI + "----" + gst28Calculated);
                }

                String netAmountOnUi = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_netAmountOnUI, "value");
                String discountOnUi = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_discountOnUIText, "value");
                String gstCalculatedValue[] = String.valueOf(formatDecimalNumber(totalDiscountCalculated)).replace(".", " ").split(" ");

                m_assert.assertTrue(discountOnUi.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                        " Discount Calculated Correctly ");
                String netAmountCalculated = decimalFormat.format(Double.parseDouble(totalGrossAmountAfterDelete) + totalGST - Double.parseDouble(discountOnUi));

                m_assert.assertTrue(netAmountOnUi.equalsIgnoreCase(netAmountCalculated),
                        " Net Amount Calculated Correctly After Deleted as " + netAmountOnUi);

                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElement(oPage_Indent.button_cancelOtherCharges);
                Cls_Generic_Methods.customWait(1);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                        " Save Button Clicked In PO ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.option_purchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder),
                        "Purchase order option is selected");
                Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder);
                Cls_Generic_Methods.customWait(8);
                boolean recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }


                m_assert.assertTrue(recordFound, " PO Created Using Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approve, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_approve),
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
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newTransaction, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newTransaction), "New Transaction button clicked to create GRN");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 30);
                int mrpSize = oPage_Purchase.list_MRPPrice.size();
                int deleteItem = mrpSize / 2;
                for (int i = deleteItem; i < mrpSize; i++) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)), "Deleted the item <b>" + i);
                }
                Cls_Generic_Methods.customWait(3);
                for (int i = 0; i < mrpSize; i++) {
                    Double dMRP = nextDoubleBetween2(100.0d, 500.0d);
                    String sMRP = String.valueOf(decimalNumberUptoTwo(dMRP));
                    if (Cls_Generic_Methods.isElementEnabled(oPage_Purchase.list_MRPPrice.get(i))) {
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(i), sMRP), "Entered MRP <b>" + sMRP + " </b>for Item" + i);
                    }
                    if (Cls_Generic_Methods.isElementEnabled(oPage_Purchase.list_batchNumber.get(i))) {
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(i), sBatchNumber + i);
                    }
                }
                int expSize = oPage_Purchase.list_expiryDate.size();
                for (int i = 0; i < expSize; i++) {
                    if (Cls_Generic_Methods.isElementEnabled(oPage_Purchase.list_expiryDate.get(i))) {
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_expiryDate.get(i), "31/10/2024");
                    }
                }
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes),
                        " Transaction Notes Entered as :<b> " + transactionNotes + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType),
                        "Bill Type Selected:<b> " + billType + " </b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber),
                        "Bill Number: <b> " + billNumber + " </b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                //purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                if (purchaseTransactionTime.length() == 6) {
                    purchaseTransactionTime = "0" + purchaseTransactionTime;
                }
                purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);


                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                        "Date of bill selected:<b> " + oPage_Purchase.input_todayBillDate.getText() + " </b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "GRN created successfully for " + numberOfItem + " items");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 40);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                for (WebElement element : oPage_Purchase.list_purchaseTransactionDateandTimeList) {
                    String sDateAndTime = Cls_Generic_Methods.getTextInElement(element);
                    if (sDateAndTime.contains(purchaseTransactionDate) && sDateAndTime.contains(purchaseTransactionTime)) {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 8);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approveGRN, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_approveGRN), "GRN transaction approved successfully");
                Cls_Generic_Methods.customWait(15);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                Cls_Generic_Methods.customWait(8);
                recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(recordFound, " Order found in the purchase order page");
                String sPOStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);
                m_assert.assertTrue(sPOStatus.equalsIgnoreCase("Partially Completed"),
                        " Purchase Order Status Showing correctly as Partially Completed");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newTransaction), "New Transaction button clicked to create GRN");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 30);
                mrpSize = oPage_Purchase.list_MRPPrice.size();
                for (int i = 0; i < mrpSize; i++) {
                    Double dMRP = nextDoubleBetween2(100.0d, 500.0d);
                    String sMRP = String.valueOf(dMRP);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(i), sMRP), "Entered MRP <b>" + sMRP + " </b>for Item" + i);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(i), (sBatchNumber + i).concat(getRandomString(2)));
                }
                expSize = oPage_Purchase.list_expiryDate.size();
                for (int i = 0; i < expSize; i++) {
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_expiryDate.get(i), "31/10/2024");
                }
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes),
                        " Transaction Notes Entered as :<b> " + transactionNotes + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType),
                        "Bill Type Selected:<b> " + billType + " </b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, (billNumber.concat(getRandomString(2)))),
                        "Bill Number: <b> " + (billNumber.concat(getRandomString(2))) + " </b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                //purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                if (purchaseTransactionTime.length() == 6) {
                    purchaseTransactionTime = "0" + purchaseTransactionTime;
                }
                purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);


                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                        "Date of bill selected:<b> " + oPage_Purchase.input_todayBillDate.getText() + " </b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "GRN created successfully for " + numberOfItem + " items");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 30);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                for (WebElement element : oPage_Purchase.list_purchaseTransactionDateandTimeList) {
                    String sDateAndTime = Cls_Generic_Methods.getTextInElement(element);
                    if (sDateAndTime.contains(purchaseTransactionDate) && sDateAndTime.contains(purchaseTransactionTime)) {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 8);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approveGRN, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_approveGRN), "GRN transaction approved successfully");
                Cls_Generic_Methods.customWait(15);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 40);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                Cls_Generic_Methods.customWait(8);
                recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(recordFound, " Order found in the purchase order page");
                sPOStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);
                m_assert.assertTrue(sPOStatus.equalsIgnoreCase("Completed"),
                        " Purchase Order Status Showing correctly as Completed");
                Cls_Generic_Methods.customWait();
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
    public void createPOForRemainingPartialItem() {


        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        List<String> itemList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();
        List<String> updatedQuantityList = new ArrayList<>();

        List<Double> rateList = new ArrayList<>();
        List<String> discountList = new ArrayList<>();


        int numberOfItem = 40;
        boolean bIndentOrderFound = false;
        String globalDiscountValue = "10";


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sINDENT_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);

                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(date) &&
                            mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(time)) {
                        bIndentOrderFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(bIndentOrderFound, "Order found in the Indent order page");

                if (bIndentOrderFound) {

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newOrder, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newOrder),
                            "Clicked on New Order button to create PO");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_IndentNumberInPurchaseOrderPopUp, 10);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_IndentOrderType, 10);
                    OrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderTime, "value");
                    OrderTime = OrderTime.replaceAll("\\s+", "");
                    OrderTime = OrderTime.replace("am", "AM").replace("pm", "PM");
                    if (OrderTime.length() == 6) {
                        OrderTime = "0" + OrderTime;
                    }
                    m_assert.assertTrue(" order time:<b> " + OrderTime + "</b>");

                    OrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderDate, "value");
                    OrderDate = OrderDate.replaceAll("/", "-");
                    m_assert.assertTrue(" order date:<b> " + OrderDate + "</b>");

                    mapTracker.put(key_CreatedAt_PurchaseOrderTroughIndent, OrderDate + "  |  " + OrderTime);
                    Cls_Generic_Methods.customWait(2);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_searchVendorAddress, sVendorName);
                    Cls_Generic_Methods.customWait(3);
                    String sSelectedVendor = Cls_Generic_Methods.getTextInElement(oPage_Indent.vendorAddressName);
                    if (sSelectedVendor.contains(sVendorName)) {
                        Cls_Generic_Methods.clickElement(oPage_Indent.vendorAddressName);
                        m_assert.assertTrue("Selected Vendor:  <b>" + sVendorName + "</b>");
                    } else {
                        m_assert.assertTrue("Required vendor <b>" + sVendorName + "</b> is NOT selected");
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_BillToStore, 10);

                }


                int j = 0;
                for (WebElement addButton : oPage_Indent.list_addActionListOfIndentOrder) {
                    if (j < 10) {
                        m_assert.assertTrue(Cls_Generic_Methods.doubleClickElement(addButton),
                                " Add Button Clicked");
                        Cls_Generic_Methods.customWait(1);
                    } else {
                        if (j > 19) {
                            break;
                        }
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(addButton),
                                " Add Button Clicked");
                        Cls_Generic_Methods.customWait(1);
                    }
                    j++;
                }

                for (WebElement rate : oPage_Indent.list_rateListOfIndentOrder) {
                    Double rateValue = nextDoubleBetween2(100.0d, 500.0d);
                    rateList.add(rateValue);
                    if (Cls_Generic_Methods.isElementEnabled(rate)) {
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(rate, String.valueOf(rateValue)),
                                "Rate per unit  Entered as =  <b>" + rateValue + " </b>");
                    }
                }

                for (WebElement paidQty : oPage_Indent.list_paidQuantityListOfIndentOrder) {
                    String qty = Cls_Generic_Methods.getElementAttribute(paidQty, "value");
                    Double qtyDouble = Double.parseDouble(qty);
                    if (Cls_Generic_Methods.isElementEnabled(paidQty)) {
                        if (qty.isEmpty() || qtyDouble < 1) {
                            Cls_Generic_Methods.clearValuesInElement(paidQty);
                            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(paidQty, sQTY),
                                    "QTY per unit Entered as =  <b>" + sQTY + " </b>");
                            updatedQuantityList.add(sQTY);
                        } else {
                            updatedQuantityList.add(qty);
                        }
                    }
                }

                Cls_Generic_Methods.clearValuesInElement(oPage_Indent.input_globalDiscount);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_globalDiscount, globalDiscountValue);
                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.selectElementByIndex(oPage_Indent.input_globalDiscountType, 1);
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_applyGlobalDiscount),
                            "Clicked on apply global discount button");
                    Cls_Generic_Methods.customWait(1);

                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Indent.list_discountValueListOfIndentOrder, 15);
                }

                String totalGrossAmount = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");

                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                for (int i = 0; i < oPage_Indent.list_rateListOfIndentOrder.size(); i++) {


                    String grossPerItemCalculated = String.valueOf(rateList.get(i) * Double.parseDouble(updatedQuantityList.get(i)));
                    grossPerItemCalculated = decimalNumberUptoTwo(Double.parseDouble(grossPerItemCalculated));
                    String globalDiscountPerItemCalculated = getGlobalDiscount(globalDiscountValue + "%", totalGrossAmount, grossPerItemCalculated);
                    String globalDiscountPerItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.list_discountValueListOfIndentOrder.get(i)).replace("-", "").trim();
                    discountList.add(globalDiscountPerItemOnUI);
                    Float newGlobalDiscountPerItemCalculatedFloat = Float.parseFloat(globalDiscountPerItemCalculated) - 00.01f;
                    Double newGlobalDiscountPerItemCalculated = newGlobalDiscountPerItemCalculatedFloat.doubleValue();
                    m_assert.assertTrue(globalDiscountPerItemOnUI.equalsIgnoreCase(globalDiscountPerItemCalculated) ||
                                    globalDiscountPerItemOnUI.equalsIgnoreCase(decimalFormat.format(newGlobalDiscountPerItemCalculated)),
                            " Global Discount Working correctly as discount apply correctly for item as : " + globalDiscountPerItemOnUI + " at index " + i);
                }

                int deleteItemIndex[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
                for (int i = 0; i < oPage_Indent.list_rateListOfIndentOrder.size(); i++) {

                    if (i > 0 && i < 50) {
                        if (getNumberPresentInArray(deleteItemIndex, i) && i < 11) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                    "Delete Button Clicked on " + i);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                    "Delete Button Clicked on " + i);
                        } else {
                            if (i > 20) {
                                break;
                            } else {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                        "Delete Button Clicked on " + i);
                            }
                        }

                    }

                }


                String totalGrossAmountAfterDelete = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");
                Double grossPerItemCalculatedDouble = 0.00;
                Float grossPerItem0CalculatedDouble = 0.00f;
                Float grossPerItem5CalculatedDouble = 0.00f;
                Float grossPerItem12CalculatedDouble = 0.00f;
                Float grossPerItem18CalculatedDouble = 0.00f;
                Float grossPerItem28CalculatedDouble = 0.00f;
                Float discount0CalculatedDouble = 0.00f;
                Float discount5CalculatedDouble = 0.00f;
                Float discount12CalculatedDouble = 0.00f;
                Float discount18CalculatedDouble = 0.00f;
                Float discount28CalculatedDouble = 0.00f;

                Double gstCalculatedDouble = 0.00;
                Double gst5CalculatedDouble = 0.00;
                Double gst12CalculatedDouble = 0.00;
                Double gst18CalculatedDouble = 0.00;
                Double gst28CalculatedDouble = 0.00;

                String taxRateList[] = {"0", "5", "12", "18", "28"};
                for (WebElement eRate : oPage_Indent.list_rateListOfIndentOrder) {
                    int index = oPage_Indent.list_rateListOfIndentOrder.indexOf(eRate);
                    if (Cls_Generic_Methods.isElementEnabled(eRate)) {
                        String rate = Cls_Generic_Methods.getElementAttribute(eRate, "value");
                        String sTaxRate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.list_taxRateListOfIndentOrder.get(index), "value");
                        String globalDiscountPerItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.list_discountValueListOfIndentOrder.get(index)).replace("-", "").trim();

                        if (sTaxRate.contains("5")) {
                            grossPerItem5CalculatedDouble = grossPerItem5CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(updatedQuantityList.get(index)));
                            discount5CalculatedDouble = discount5CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);
                        } else if (sTaxRate.contains("12")) {
                            grossPerItem12CalculatedDouble = grossPerItem12CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(updatedQuantityList.get(index)));
                            discount12CalculatedDouble = discount12CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);

                        } else if (sTaxRate.contains("18")) {
                            grossPerItem18CalculatedDouble = grossPerItem18CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(updatedQuantityList.get(index)));
                            discount18CalculatedDouble = discount18CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);


                        } else if (sTaxRate.contains("28")) {
                            grossPerItem28CalculatedDouble = grossPerItem28CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(updatedQuantityList.get(index)));
                            discount28CalculatedDouble = discount28CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);

                        } else {
                            grossPerItem0CalculatedDouble = grossPerItem0CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(updatedQuantityList.get(index)));
                            discount0CalculatedDouble = discount0CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);
                        }

                        grossPerItemCalculatedDouble = grossPerItemCalculatedDouble + (Double.parseDouble(rate) * Double.parseDouble(updatedQuantityList.get(index)));
                    }
                }


                String grossPerItemCalculated = decimalFormat.format(grossPerItemCalculatedDouble);
                Float totalDiscountCalculated = discount0CalculatedDouble + discount5CalculatedDouble + discount12CalculatedDouble + discount18CalculatedDouble + discount28CalculatedDouble;

                m_assert.assertTrue(totalGrossAmountAfterDelete.equalsIgnoreCase(grossPerItemCalculated),
                        " Gross Amount Calculated Correctly After Deleted as " + totalGrossAmountAfterDelete + "----" + grossPerItemCalculated);


                gst5CalculatedDouble = gst5CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem5CalculatedDouble), taxRateList[1], String.valueOf(discount5CalculatedDouble)));
                gst12CalculatedDouble = gst12CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem12CalculatedDouble), taxRateList[2], String.valueOf(discount12CalculatedDouble)));
                gst18CalculatedDouble = gst18CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem18CalculatedDouble), taxRateList[3], String.valueOf(discount18CalculatedDouble)));
                gst28CalculatedDouble = gst28CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem28CalculatedDouble), taxRateList[4], String.valueOf(discount28CalculatedDouble)));
                gstCalculatedDouble = gstCalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem0CalculatedDouble), taxRateList[0], String.valueOf(discount0CalculatedDouble)));


                String gstCalculated = decimalFormat.format(gstCalculatedDouble);
                String gst5Calculated = decimalFormat.format(gst5CalculatedDouble);
                String gst12Calculated = decimalFormat.format(gst12CalculatedDouble);
                String gst18Calculated = decimalFormat.format(gst18CalculatedDouble);
                String gst28Calculated = decimalFormat.format(gst28CalculatedDouble);


                List<String> gstValuesInView = new ArrayList<>();
                String gstOnUI = getGstOnUI(oPage_Indent.text_GST0OnUI);
                String gst5OnUI = getGstOnUI(oPage_Indent.text_GST5OnUI);
                String gst12OnUI = getGstOnUI(oPage_Indent.text_GST12OnUI);
                String gst18OnUI = getGstOnUI(oPage_Indent.text_GST18OnUI);
                String gst28OnUI = getGstOnUI(oPage_Indent.text_GST28OnUI);

                gstValuesInView.add(gstOnUI);
                gstValuesInView.add(gst5OnUI);
                gstValuesInView.add(gst12OnUI);
                gstValuesInView.add(gst18OnUI);
                gstValuesInView.add(gst28OnUI);


                Double totalGST = 0.00;
                for (String gstText : gstValuesInView) {
                    if (!gstText.isEmpty()) {
                        totalGST = totalGST + Double.parseDouble(gstText);
                    }
                }

                if (!gstOnUI.isEmpty()) {
                    String gstCalculatedValue[] = gstCalculated.replace(".", " ").split(" ");
                    m_assert.assertTrue(gstOnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST Amount Calculated Correctly After Deleted as " + gstOnUI + "----" + gstCalculated);
                }
                if (!gst5OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst5Calculated.replace(".", " ").split(" ");
                    m_assert.assertTrue(gst5OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST5 Amount Calculated Correctly After Deleted as " + gst5OnUI + "----" + gst5Calculated);
                }
                if (!gst12OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst12Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst12OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST12 Amount Calculated Correctly After Deleted as " + gst12OnUI + "----" + gst12Calculated);
                }
                if (!gst18OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst18Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst18OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST18 Amount Calculated Correctly After Deleted as " + gst18OnUI + "----" + gst18Calculated);
                }
                if (!gst28OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst28Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst28OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST28 Amount Calculated Correctly After Deleted as " + gst28OnUI + "----" + gst28Calculated);
                }

                String netAmountOnUi = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_netAmountOnUI, "value");
                String discountOnUi = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_discountOnUIText, "value");
                String gstCalculatedValue[] = String.valueOf(formatDecimalNumber(totalDiscountCalculated)).replace(".", " ").split(" ");

                m_assert.assertTrue(discountOnUi.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                        " Discount Calculated Correctly ");
                String netAmountCalculated = decimalFormat.format(Double.parseDouble(totalGrossAmountAfterDelete) + totalGST - Double.parseDouble(discountOnUi));

                m_assert.assertTrue(netAmountOnUi.equalsIgnoreCase(netAmountCalculated),
                        " Net Amount Calculated Correctly After Deleted as " + netAmountOnUi);

                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElement(oPage_Indent.button_cancelOtherCharges);
                Cls_Generic_Methods.customWait(1);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                        " Save Button Clicked In PO ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.option_purchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder),
                        "Purchase order option is selected");
                Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder);
                Cls_Generic_Methods.customWait(8);
                boolean recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }


                m_assert.assertTrue(recordFound, " PO Created Using Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approve, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_approve),
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
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newTransaction, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newTransaction), "New Transaction button clicked to create GRN");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 30);
                int mrpSize = oPage_Purchase.list_MRPPrice.size();
                for (int i = 0; i < mrpSize; i++) {
                    Double dMRP = nextDoubleBetween2(100.0d, 500.0d);
                    String sMRP = String.valueOf(dMRP);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(i), sMRP), "Entered MRP <b>" + sMRP + " </b>for Item" + i);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(i), (sBatchNumber + i).concat(getRandomString(2)));
                }
                int expSize = oPage_Purchase.list_expiryDate.size();
                for (int i = 0; i < expSize; i++) {
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_expiryDate.get(i), "31/10/2024");
                }
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes),
                        " Transaction Notes Entered as :<b> " + transactionNotes + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType),
                        "Bill Type Selected:<b> " + billType + " </b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, (billNumber.concat(getRandomString(2)))),
                        "Bill Number: <b> " + (billNumber.concat(getRandomString(2))) + " </b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                //purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                if (purchaseTransactionTime.length() == 6) {
                    purchaseTransactionTime = "0" + purchaseTransactionTime;
                }
                purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);


                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                        "Date of bill selected:<b> " + oPage_Purchase.input_todayBillDate.getText() + " </b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "GRN created successfully for " + numberOfItem + " items");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 30);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                for (WebElement element : oPage_Purchase.list_purchaseTransactionDateandTimeList) {
                    String sDateAndTime = Cls_Generic_Methods.getTextInElement(element);
                    if (sDateAndTime.contains(purchaseTransactionDate) && sDateAndTime.contains(purchaseTransactionTime)) {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 8);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approveGRN, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_approveGRN), "GRN transaction approved successfully");
                Cls_Generic_Methods.customWait(15);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 40);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                Cls_Generic_Methods.customWait(8);
                recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(recordFound, " Order found in the purchase order page");
                String sPOStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);
                m_assert.assertTrue(sPOStatus.equalsIgnoreCase("Completed"),
                        " Purchase Order Status Showing correctly as Completed");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);


                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(date) &&
                            mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(time)) {
                        bIndentOrderFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(bIndentOrderFound, "Order found in the Indent order page");

                String indentStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);

                m_assert.assertTrue(indentStatus.equalsIgnoreCase("Completed"),
                        " Indent Status Showing correctly as Completed");

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
    public void createEditIndentPOForSingleItem() {


        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        List<String> itemList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();
        List<Double> rateList = new ArrayList<>();
        List<String> discountList = new ArrayList<>();
        List<Double> updateRateList = new ArrayList<>();
        String editQty = "3";


        int numberOfItem = 1;
        boolean bIndentOrderFound = false;
        String globalDiscountValue = "10";


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sINDENT_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_addNewIndent),
                        "New Button clicked in Order: Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 10);
                String sSelectedButton = Cls_Generic_Methods.getTextInElement(oPage_Indent.button_variantOrRequisitionSelected);
                if (sSelectedButton.equalsIgnoreCase(sExpectedButtonToBeSelected)) {
                    boolean ItemList = oPage_Indent.list_ItemDescriptionsUnderIndentPurchase.isEmpty();
                    if (ItemList) {
                        m_assert.assertTrue("ItemList is empty");
                    } else {
                        m_assert.assertTrue(false, "ItemList is not empty");
                    }
                } else {
                    m_assert.assertTrue("Expected Button = <b>" + sExpectedButtonToBeSelected + "</b> is not selected");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_vendorField, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.select_vendorField),
                        "clicked on Store selection field");
                Cls_Generic_Methods.customWait();
                boolean storeFound = false;
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, sStoreToShipOrToBillOrder);
                Cls_Generic_Methods.customWait();
                for (WebElement eStoreName : oPage_Indent.list_stores) {
                    if (Cls_Generic_Methods.getTextInElement(eStoreName).contains(sStoreToShipOrToBillOrder)) {
                        Cls_Generic_Methods.clickElement(eStoreName);
                        storeFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(storeFound, "Store found to do indent purchase : <b> " + sINDENT_STORE + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_noteUnderIndentForPurchase, 10);

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
                boolean bItemSelected = CommonActions.selectItemInInventory(oPage_Indent.list_ItemDescriptionsUnderIndentPurchase, itemList, numberOfItem, oPage_Indent.list_itemNameSelectedToCreateIndentPurchase);

                m_assert.assertTrue(bItemSelected, numberOfItem + " Item Selected From Left Panel Correctly ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_indentOrderDate, 10);
                indentOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderTime, "value");
                indentOrderTime = indentOrderTime.replaceAll("\\s+", "");
                indentOrderTime = indentOrderTime.replace("am", "AM").replace("pm", "PM");
                if (indentOrderTime.length() == 6) {
                    indentOrderTime = "0" + indentOrderTime;
                }
                m_assert.assertTrue("Indent order time:<b> " + indentOrderTime + "</b>");
                indentOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderDate, "value");
                indentOrderDate = indentOrderDate.replaceAll("/", "-");
                m_assert.assertTrue("Indent order date:<b> " + indentOrderDate + "</b>");
                mapTracker.put(key_CreatedAt_IndentOrderFromStore, indentOrderDate + "  |  " + indentOrderTime);
                Cls_Generic_Methods.customWait();

                for (WebElement eItem : oPage_Indent.list_itemNameSelectedToCreateIndentPurchase) {
                    int index = oPage_Indent.list_itemNameSelectedToCreateIndentPurchase.indexOf(eItem);

                    Cls_Generic_Methods.scrollToElementByJS(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index));
                    Cls_Generic_Methods.customWait(1);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index), sQTY);
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Indent.text_multipleOfItemText)) {
                        String multipleOf = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_multipleOfItemText).split(" ")[6];
                        Cls_Generic_Methods.clearValuesInElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index));
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index), multipleOf);
                        quantityList.add(multipleOf);

                    } else {
                        quantityList.add(sQTY);
                    }
                    m_assert.assertInfo(" Quantity Entered as <b> " + quantityList.get(index) + " </b> for item name <b> " + itemList.get(index) + " </b> at index " + index);
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.list_subStoreSelectedToCreateIndentPurchase.get(index), sSubStore);
                    Cls_Generic_Methods.customWait(1);

                }

                Cls_Generic_Methods.customWait();

                int totalQuantity = 0;
                for (int i = 0; i < quantityList.size(); i++) {
                    totalQuantity = totalQuantity + Integer.parseInt(quantityList.get(i));
                }

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Indent.text_totalQuantity).equalsIgnoreCase("Total Quantity :" + totalQuantity),
                        " Total Quantity For all Item displayed correctly as : " + totalQuantity);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder),
                        " Indent order saved");
                Cls_Generic_Methods.customWait(5);

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
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_editIndent, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_editIndent), "Edit button clicked to edit Indent");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 30);
                    int itemSize = oPage_Indent.list_quantityPresentForItemInEditPage.size();
                    for (int i = 0; i < itemSize; i++) {
                        Double itemQtyCount = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Indent.list_quantityPresentForItemInEditPage.get(i), "value"));
                        itemQtyCount = itemQtyCount * 2;
                        Cls_Generic_Methods.clearValuesInElement(oPage_Indent.list_quantityPresentForItemInEditPage.get(i));
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantityPresentForItemInEditPage.get(i), String.valueOf(itemQtyCount)), "Entered updated qty for Indent <b>" + itemQtyCount);

                    }
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_updateChanges), "Clicked Update Changes button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approveIndent, 20);
                    Cls_Generic_Methods.customWait();
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_approveIndent), "Approve Button Cliked to approve Indent");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approveConfirmation, 20);
                    Cls_Generic_Methods.clickElement(oPage_Indent.button_approveConfirmation);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newOrder, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newOrder),
                            "Clicked on New Order button to create PO");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_IndentNumberInPurchaseOrderPopUp, 10);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_IndentOrderType, 10);
                    OrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderTime, "value");
                    OrderTime = OrderTime.replaceAll("\\s+", "");
                    OrderTime = OrderTime.replace("am", "AM").replace("pm", "PM");
                    if (OrderTime.length() == 6) {
                        OrderTime = "0" + OrderTime;
                    }
                    m_assert.assertTrue(" order time:<b> " + OrderTime + "</b>");

                    OrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderDate, "value");
                    OrderDate = OrderDate.replaceAll("/", "-");
                    m_assert.assertTrue(" order date:<b> " + OrderDate + "</b>");

                    mapTracker.put(key_CreatedAt_PurchaseOrderTroughIndent, OrderDate + "  |  " + OrderTime);
                    Cls_Generic_Methods.customWait(2);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_searchVendorAddress, sVendorName);
                    Cls_Generic_Methods.customWait(4);
                    String sSelectedVendor = Cls_Generic_Methods.getTextInElement(oPage_Indent.vendorAddressName);
                    if (sSelectedVendor.contains(sVendorName)) {
                        Cls_Generic_Methods.clickElement(oPage_Indent.vendorAddressName);
                        m_assert.assertTrue("Selected Vendor:  <b>" + sVendorName + "</b>");
                    } else {
                        m_assert.assertTrue("Required vendor <b>" + sVendorName + "</b> is NOT selected");
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_BillToStore, 10);

                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_RatePerUnit, 10);

                for (WebElement rate : oPage_Indent.list_rateListOfIndentOrder) {
                    Double rateValue = nextDoubleBetween2(100.0d, 500.0d);
                    rateList.add(rateValue);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(rate, String.valueOf(rateValue)),
                            "Rate per unit  Entered as =  <b>" + rateValue + " </b>");
                }


                for (WebElement paidQty : oPage_Indent.list_paidQuantityListOfIndentOrder) {
                    Cls_Generic_Methods.clearValuesInElement(paidQty);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(paidQty, partialQty),
                            "QTY per unit Entered as =  <b>" + partialQty + " </b>");
                }

                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElement(oPage_Indent.button_cancelOtherCharges);
                Cls_Generic_Methods.customWait(1);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                        " Save Button Clicked In PO ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.option_purchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder),
                        "Purchase order option is selected");
                Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder);
                Cls_Generic_Methods.customWait(8);
                boolean recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }


                m_assert.assertTrue(recordFound, " PO Created Using Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approve, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Purchase.button_editPOTransaction),
                        "Clicked on Edit button");
                Cls_Generic_Methods.customWait(5);


                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_addActionListOfIndentOrder.get(0)),
                        " Add Button Clicked");
                Cls_Generic_Methods.customWait(2);


                for (WebElement rate : oPage_Indent.list_rateListOfIndentOrder) {
                    Double rateValue = nextDoubleBetween2(100.0d, 500.0d);
                    updateRateList.add(rateValue);
                    Cls_Generic_Methods.clearValuesInElement(rate);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(rate, String.valueOf(rateValue)),
                            "Rate per unit  Entered as =  <b>" + rateValue + " </b>");
                }


                for (WebElement paidQty : oPage_Indent.list_paidQuantityListOfIndentOrder) {
                    Cls_Generic_Methods.clearValuesInElement(paidQty);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(paidQty, editQty),
                            "QTY per unit Entered as =  <b>" + editQty + " </b>");
                }

                Cls_Generic_Methods.clearValuesInElement(oPage_Indent.input_globalDiscount);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_globalDiscount, globalDiscountValue);
                Cls_Generic_Methods.customWait(1);
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_applyGlobalDiscount),
                            "Clicked on apply global discount button");
                    Cls_Generic_Methods.customWait(1);

                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                    Cls_Generic_Methods.customWait();
                }

                String totalGrossAmount = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_IndentOrderType, 10);
                OrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderTime, "value");
                OrderTime = OrderTime.replaceAll("\\s+", "");
                OrderTime = OrderTime.replace("am", "AM").replace("pm", "PM");
                if (OrderTime.length() == 6) {
                    OrderTime = "0" + OrderTime;
                }
                m_assert.assertTrue(" order time:<b> " + OrderTime + "</b>");

                OrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderDate, "value");
                OrderDate = OrderDate.replaceAll("/", "-");
                m_assert.assertTrue(" order date:<b> " + OrderDate + "</b>");

                mapTracker.put(key_CreatedAt_PurchaseOrderTroughIndent, OrderDate + "  |  " + OrderTime);
                Cls_Generic_Methods.customWait(2);

                for (int i = 0; i < 2; i++) {
                    String grossPerItemCalculated = decimalFormat.format(updateRateList.get(i) * Double.parseDouble(editQty));
                    String globalDiscountPerItemCalculated = getGlobalDiscount(globalDiscountValue, totalGrossAmount, grossPerItemCalculated);
                    String globalDiscountPerItemOnUI = Cls_Generic_Methods.getElementAttribute(oPage_Indent.list_discountListOfIndentOrder.get(i), "value");
                    discountList.add(globalDiscountPerItemOnUI);
                    m_assert.assertTrue(globalDiscountPerItemOnUI.equalsIgnoreCase(globalDiscountPerItemCalculated),
                            " Global Discount Working correctly as discount apply correctly for item as : " + globalDiscountPerItemOnUI + " at index " + i);
                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(1)),
                        " Delete Button CLicked");
                Cls_Generic_Methods.customWait();

                String totalGrossAmountAfterDelete = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");

                String x[] = updateRateList.get(0).toString().replace(".", " ").split(" ");
                String grossPerItemCalculated = decimalFormat.format(Double.parseDouble(x[0] + "." + x[1].substring(0, 2)) * Double.parseDouble(editQty));

                m_assert.assertTrue(totalGrossAmountAfterDelete.equalsIgnoreCase(grossPerItemCalculated),
                        " Gross Amount Calculated Correctly After Deleted as " + totalGrossAmountAfterDelete + "----" + grossPerItemCalculated);
                String sTaxRate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.list_taxRateListOfIndentOrder.get(0), "value");

                String gstCalculated = getGST(totalGrossAmountAfterDelete, sTaxRate, discountList.get(0));
                String gstOnUI = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_GSTOnUIText, "value");
                m_assert.assertTrue(gstOnUI.equalsIgnoreCase(gstCalculated),
                        " GST Amount Calculated Correctly After Deleted as " + gstOnUI + "----" + gstCalculated);

                String netAmountOnUi = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_netAmountOnUI, "value");

                String netAmountCalculated = decimalFormat.format(Double.parseDouble(totalGrossAmountAfterDelete) + Double.parseDouble(gstOnUI) - Double.parseDouble(discountList.get(0)));

                m_assert.assertTrue(netAmountOnUi.equalsIgnoreCase(netAmountCalculated),
                        " Net Amount Calculated Correctly After Deleted as " + netAmountOnUi);

                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElement(oPage_Indent.button_cancelOtherCharges);
                Cls_Generic_Methods.customWait(1);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                        " Save Button Clicked In PO ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.option_purchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder),
                        "Purchase order option is selected");
                Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder);
                Cls_Generic_Methods.customWait(3);
                /* recordFound = false ;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }*/


                // m_assert.assertTrue(recordFound," PO Created Using Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approve, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_approve),
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
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newTransaction, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newTransaction), "New Transaction button clicked to create GRN");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 30);
                int mrpSize = oPage_Purchase.list_MRPPrice.size();
                for (int i = 0; i < mrpSize; i++) {
                    Double dMRP = nextDoubleBetween2(100.0d, 500.0d);
                    String sMRP = String.valueOf(dMRP);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(i), sMRP), "Entered MRP <b>" + sMRP + " </b>for Item" + i);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(i), (sBatchNumber + i).concat(getRandomString(2)));
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_inputPaidQty.get(i));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_inputPaidQty.get(i), partialQty), "Entered Quantity <b>" + partialQty + " </b> for the item");
                }
                int expSize = oPage_Purchase.list_expiryDate.size();
                for (int i = 0; i < expSize; i++) {
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_expiryDate.get(i), "31/10/2024");
                }
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes),
                        " Transaction Notes Entered as :<b> " + transactionNotes + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType),
                        "Bill Type Selected:<b> " + billType + " </b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, (billNumber.concat(getRandomString(2)))),
                        "Bill Number: <b> " + (billNumber.concat(getRandomString(2))) + " </b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                //purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                if (purchaseTransactionTime.length() == 6) {
                    purchaseTransactionTime = "0" + purchaseTransactionTime;
                }
                purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);


                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                        "Date of bill selected:<b> " + oPage_Purchase.input_todayBillDate.getText() + " </b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "GRN created successfully for " + numberOfItem + " items");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 30);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                for (WebElement element : oPage_Purchase.list_purchaseTransactionDateandTimeList) {
                    String sDateAndTime = Cls_Generic_Methods.getTextInElement(element);
                    if (sDateAndTime.contains(purchaseTransactionDate) && sDateAndTime.contains(purchaseTransactionTime)) {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_editPurchaseTransaction, 8);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_editPurchaseTransaction);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 30);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_deleteOtherCharges);
                Cls_Generic_Methods.customWait();
                purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                //purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                if (purchaseTransactionTime.length() == 6) {
                    purchaseTransactionTime = "0" + purchaseTransactionTime;
                }
                purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);

                mrpSize = oPage_Purchase.list_MRPPrice.size();
                for (int i = 0; i < mrpSize; i++) {
                    Double dMRP = nextDoubleBetween2(100.0d, 500.0d);
                    String sMRP = String.valueOf(dMRP);
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_MRPPrice.get(i));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(i), sMRP), "Entered MRP <b>" + sMRP + " </b>for Item" + i);
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_batchNumber.get(i));
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(i), (sBatchNumber + i).concat(getRandomString(2)));
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_inputPaidQty.get(i));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_inputPaidQty.get(i), editQty), "Entered Quantity <b>" + partialQty + " </b> for the item");
                }
                expSize = oPage_Purchase.list_expiryDate.size();
                for (int i = 0; i < expSize; i++) {
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_expiryDate.get(i));
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_expiryDate.get(i), "30/11/2024");
                }
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "GRN created successfully for " + numberOfItem + " items");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 30);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                for (WebElement element : oPage_Purchase.list_purchaseTransactionDateandTimeList) {
                    String sDateAndTime = Cls_Generic_Methods.getTextInElement(element);
                    if (sDateAndTime.contains(purchaseTransactionDate) && sDateAndTime.contains(purchaseTransactionTime)) {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 8);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approveGRN, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_approveGRN), "GRN transaction approved successfully");
                Cls_Generic_Methods.customWait(15);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 40);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                Cls_Generic_Methods.customWait(8);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Indent.list_dateTimeOfIndentOrder, 30);
                recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(recordFound, " Order found in the purchase order page");
                String sPOStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);
                m_assert.assertTrue(sPOStatus.equalsIgnoreCase("Completed"),
                        " Purchase Order Status Showing correctly as Completed");
                Cls_Generic_Methods.customWait();

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
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

                String indentStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);

                m_assert.assertTrue(indentStatus.equalsIgnoreCase("Partially Completed"),
                        " Indent Status Showing correctly as Partially Completed");

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
    public void createEditPOForPartialCompletedMultipleItem() {


        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        List<String> itemList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();
        List<Double> rateList = new ArrayList<>();
        List<String> discountList = new ArrayList<>();
        List<Double> rateBeforeEditList = new ArrayList<>();


        int numberOfItem = 20;
        boolean bIndentOrderFound = false;
        String globalDiscountValue = "10";
        String editQty = "3";


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sINDENT_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_addNewIndent),
                        "New Button clicked in Order: Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 10);
                String sSelectedButton = Cls_Generic_Methods.getTextInElement(oPage_Indent.button_variantOrRequisitionSelected);
                if (sSelectedButton.equalsIgnoreCase(sExpectedButtonToBeSelected)) {
                    boolean ItemList = oPage_Indent.list_ItemDescriptionsUnderIndentPurchase.isEmpty();
                    if (ItemList) {
                        m_assert.assertTrue("ItemList is empty");
                    } else {
                        m_assert.assertTrue(false, "ItemList is not empty");
                    }
                } else {
                    m_assert.assertTrue("Expected Button = <b>" + sExpectedButtonToBeSelected + "</b> is not selected");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_vendorField, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.select_vendorField),
                        "clicked on Store selection field");
                Cls_Generic_Methods.customWait();
                boolean storeFound = false;
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, sStoreToShipOrToBillOrder);
                Cls_Generic_Methods.customWait();
                for (WebElement eStoreName : oPage_Indent.list_stores) {
                    if (Cls_Generic_Methods.getTextInElement(eStoreName).contains(sStoreToShipOrToBillOrder)) {
                        Cls_Generic_Methods.clickElement(eStoreName);
                        storeFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(storeFound, "Store found to do indent purchase : <b> " + sINDENT_STORE + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_noteUnderIndentForPurchase, 10);

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
                boolean bItemSelected = CommonActions.selectItemInInventory(oPage_Indent.list_ItemDescriptionsUnderIndentPurchase, itemList, numberOfItem, oPage_Indent.list_itemNameSelectedToCreateIndentPurchase);

                m_assert.assertTrue(bItemSelected, numberOfItem + " Item Selected From Left Panel Correctly ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_indentOrderDate, 10);
                indentOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderTime, "value");
                indentOrderTime = indentOrderTime.replaceAll("\\s+", "");
                indentOrderTime = indentOrderTime.replace("am", "AM").replace("pm", "PM");
                if (indentOrderTime.length() == 6) {
                    indentOrderTime = "0" + indentOrderTime;
                }
                m_assert.assertTrue("Indent order time:<b> " + indentOrderTime + "</b>");
                indentOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderDate, "value");
                indentOrderDate = indentOrderDate.replaceAll("/", "-");
                m_assert.assertTrue("Indent order date:<b> " + indentOrderDate + "</b>");
                mapTracker.put(key_CreatedAt_IndentOrderFromStore, indentOrderDate + "  |  " + indentOrderTime);
                Cls_Generic_Methods.customWait();

                for (WebElement eItem : oPage_Indent.list_itemNameSelectedToCreateIndentPurchase) {
                    int index = oPage_Indent.list_itemNameSelectedToCreateIndentPurchase.indexOf(eItem);

                    Cls_Generic_Methods.scrollToElementByJS(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index));
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index), sQTY);
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Indent.text_multipleOfItemText)) {
                        String multipleOf = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_multipleOfItemText).split(" ")[6];
                        Cls_Generic_Methods.clearValuesInElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index));
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index), multipleOf);
                        quantityList.add(multipleOf);

                    } else {
                        quantityList.add(sQTY);
                    }
                    m_assert.assertInfo(" Quantity Entered as <b> " + quantityList.get(index) + " </b> for item name <b> " + itemList.get(index) + " </b> at index " + index);
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.list_subStoreSelectedToCreateIndentPurchase.get(index), sSubStore);

                }

                Cls_Generic_Methods.customWait();

                int totalQuantity = 0;
                for (int i = 0; i < quantityList.size(); i++) {
                    totalQuantity = totalQuantity + Integer.parseInt(quantityList.get(i));
                }

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Indent.text_totalQuantity).equalsIgnoreCase("Total Quantity :" + totalQuantity),
                        " Total Quantity For all Item displayed correctly as : " + totalQuantity);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder),
                        " Indent order saved");
                Cls_Generic_Methods.customWait(10);

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
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approveIndent, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_approveIndent), "Approve Button Cliked to approve Indent");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approveConfirmation, 20);
                    Cls_Generic_Methods.clickElement(oPage_Indent.button_approveConfirmation);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newOrder, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newOrder),
                            "Clicked on New Order button to create PO");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_IndentNumberInPurchaseOrderPopUp, 10);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_IndentOrderType, 10);
                    OrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderTime, "value");
                    OrderTime = OrderTime.replaceAll("\\s+", "");
                    OrderTime = OrderTime.replace("am", "AM").replace("pm", "PM");
                    if (OrderTime.length() == 6) {
                        OrderTime = "0" + OrderTime;
                    }
                    m_assert.assertTrue(" order time:<b> " + OrderTime + "</b>");

                    OrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderDate, "value");
                    OrderDate = OrderDate.replaceAll("/", "-");
                    m_assert.assertTrue(" order date:<b> " + OrderDate + "</b>");

                    mapTracker.put(key_CreatedAt_PurchaseOrderTroughIndent, OrderDate + "  |  " + OrderTime);
                    Cls_Generic_Methods.customWait(2);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_searchVendorAddress, sVendorName);
                    Cls_Generic_Methods.customWait();
                    String sSelectedVendor = Cls_Generic_Methods.getTextInElement(oPage_Indent.vendorAddressName);
                    if (sSelectedVendor.contains(sVendorName)) {
                        Cls_Generic_Methods.clickElement(oPage_Indent.vendorAddressName);
                        m_assert.assertTrue("Selected Vendor:  <b>" + sVendorName + "</b>");
                    } else {
                        m_assert.assertTrue("Required vendor <b>" + sVendorName + "</b> is NOT selected");
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_BillToStore, 10);

                }

                for (WebElement rate : oPage_Indent.list_rateListOfIndentOrder) {
                    Double rateValue = nextDoubleBetween2(100.0d, 500.0d);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(rate, String.valueOf(rateValue)),
                            "Rate per unit  Entered as =  <b>" + rateValue + " </b>");
                }

                for (WebElement paidQty : oPage_Indent.list_paidQuantityListOfIndentOrder) {
                    Cls_Generic_Methods.clearValuesInElement(paidQty);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(paidQty, partialQty),
                            "QTY per unit Entered as =  <b>" + partialQty + " </b>");
                }

                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElement(oPage_Indent.button_cancelOtherCharges);
                Cls_Generic_Methods.customWait(1);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                        " Save Button Clicked In PO ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.option_purchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder),
                        "Purchase order option is selected");
                Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder);
                Cls_Generic_Methods.customWait(8);
                boolean recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }


                m_assert.assertTrue(recordFound, " PO Created Using Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approve, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_editPOTransaction),
                        "Clicked on Edit button");
                Cls_Generic_Methods.customWait(10);


                int j = 0;
                for (WebElement addButton : oPage_Indent.list_addActionListOfIndentOrder) {
                    if (j < 10) {
                        m_assert.assertTrue(Cls_Generic_Methods.doubleClickElement(addButton),
                                " Add Button Clicked");
                        Cls_Generic_Methods.customWait(1);
                    } else {
                        if (j > 19) {
                            break;
                        }
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(addButton),
                                " Add Button Clicked");
                        Cls_Generic_Methods.customWait(1);
                    }
                    j++;
                }
                Cls_Generic_Methods.customWait(10);
                int rateSize = oPage_Indent.list_rateListOfIndentOrder.size();
                m_assert.assertTrue(String.valueOf(rateSize));
                for (int i = 0; i < rateSize; i++) {
                    Double rateValue = nextDoubleBetween2(100.0d, 500.0d);
                    rateList.add(rateValue);
                    Cls_Generic_Methods.scrollToElementByJS(oPage_Indent.list_rateListOfIndentOrder.get(i));
                    Cls_Generic_Methods.clearValuesInElement(oPage_Indent.list_rateListOfIndentOrder.get(i));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_rateListOfIndentOrder.get(i), String.valueOf(rateValue)),
                            "Rate per unit  Entered as =  <b>" + rateValue + " </b>");
                }

                for (WebElement paidQty : oPage_Indent.list_paidQuantityListOfIndentOrder) {
                    Cls_Generic_Methods.scrollToElementByJS(paidQty);
                    Cls_Generic_Methods.clearValuesInElement(paidQty);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(paidQty, editQty),
                            "QTY per unit Entered as =  <b>" + editQty + " </b>");
                }

                Cls_Generic_Methods.clearValuesInElement(oPage_Indent.input_globalDiscount);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_globalDiscount, globalDiscountValue);
                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.selectElementByIndex(oPage_Indent.input_globalDiscountType, 1);
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_applyGlobalDiscount),
                            "Clicked on apply global discount button");
                    Cls_Generic_Methods.customWait(1);

                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Indent.list_discountValueListOfIndentOrder, 15);
                }

                String totalGrossAmount = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");

                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                for (int i = 0; i < oPage_Indent.list_rateListOfIndentOrder.size(); i++) {


                    String grossPerItemCalculated = String.valueOf(rateList.get(i) * Double.parseDouble(editQty));
                    grossPerItemCalculated = decimalNumberUptoTwo(Double.parseDouble(grossPerItemCalculated));
                    String globalDiscountPerItemCalculated = getGlobalDiscount(globalDiscountValue + "%", totalGrossAmount, grossPerItemCalculated);
                    String globalDiscountPerItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.list_discountValueListOfIndentOrder.get(i)).replace("-", "").trim();
                    discountList.add(globalDiscountPerItemOnUI);
                    Float newGlobalDiscountPerItemCalculatedFloat = Float.parseFloat(globalDiscountPerItemCalculated) - 00.01f;
                    Double newGlobalDiscountPerItemCalculated = newGlobalDiscountPerItemCalculatedFloat.doubleValue();
                    m_assert.assertTrue(globalDiscountPerItemOnUI.equalsIgnoreCase(globalDiscountPerItemCalculated) ||
                                    globalDiscountPerItemOnUI.equalsIgnoreCase(decimalFormat.format(newGlobalDiscountPerItemCalculated)),
                            " Global Discount Working correctly as discount apply correctly for item as : " + globalDiscountPerItemOnUI + " at index " + i + "---" + globalDiscountPerItemCalculated);
                }
                OrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderTime, "value");
                OrderTime = OrderTime.replaceAll("\\s+", "");
                OrderTime = OrderTime.replace("am", "AM").replace("pm", "PM");
                if (OrderTime.length() == 6) {
                    OrderTime = "0" + OrderTime;
                }
                m_assert.assertTrue(" order time:<b> " + OrderTime + "</b>");

                OrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderDate, "value");
                OrderDate = OrderDate.replaceAll("/", "-");
                m_assert.assertTrue(" order date:<b> " + OrderDate + "</b>");

                mapTracker.put(key_CreatedAt_PurchaseOrderTroughIndent, OrderDate + "  |  " + OrderTime);
                Cls_Generic_Methods.customWait(2);

                int deleteItemIndex[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
                for (int i = 0; i < oPage_Indent.list_rateListOfIndentOrder.size(); i++) {

                    if (i > 0 && i < 50) {
                        if (getNumberPresentInArray(deleteItemIndex, i) && i < 11) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                    "Delete Button Clicked on " + i);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                    "Delete Button Clicked on " + i);
                        } else {
                            if (i > 20) {
                                break;
                            } else {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                        "Delete Button Clicked on " + i);
                            }
                        }

                    }

                }


                String totalGrossAmountAfterDelete = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");
                Double grossPerItemCalculatedDouble = 0.00;
                Float grossPerItem0CalculatedDouble = 0.00f;
                Float grossPerItem5CalculatedDouble = 0.00f;
                Float grossPerItem12CalculatedDouble = 0.00f;
                Float grossPerItem18CalculatedDouble = 0.00f;
                Float grossPerItem28CalculatedDouble = 0.00f;
                Float discount0CalculatedDouble = 0.00f;
                Float discount5CalculatedDouble = 0.00f;
                Float discount12CalculatedDouble = 0.00f;
                Float discount18CalculatedDouble = 0.00f;
                Float discount28CalculatedDouble = 0.00f;

                Double gstCalculatedDouble = 0.00;
                Double gst5CalculatedDouble = 0.00;
                Double gst12CalculatedDouble = 0.00;
                Double gst18CalculatedDouble = 0.00;
                Double gst28CalculatedDouble = 0.00;

                String taxRateList[] = {"0", "5", "12", "18", "28"};
                for (WebElement eRate : oPage_Indent.list_rateListOfIndentOrder) {
                    int index = oPage_Indent.list_rateListOfIndentOrder.indexOf(eRate);
                    String rate = Cls_Generic_Methods.getElementAttribute(eRate, "value");
                    String sTaxRate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.list_taxRateListOfIndentOrder.get(index), "value");
                    String globalDiscountPerItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.list_discountValueListOfIndentOrder.get(index)).replace("-", "").trim();

                    if (sTaxRate.contains("5")) {
                        grossPerItem5CalculatedDouble = grossPerItem5CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(editQty));
                        discount5CalculatedDouble = discount5CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);
                    } else if (sTaxRate.contains("12")) {
                        grossPerItem12CalculatedDouble = grossPerItem12CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(editQty));
                        discount12CalculatedDouble = discount12CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);

                    } else if (sTaxRate.contains("18")) {
                        grossPerItem18CalculatedDouble = grossPerItem18CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(editQty));
                        discount18CalculatedDouble = discount18CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);


                    } else if (sTaxRate.contains("28")) {
                        grossPerItem28CalculatedDouble = grossPerItem28CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(editQty));
                        discount28CalculatedDouble = discount28CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);

                    } else {
                        grossPerItem0CalculatedDouble = grossPerItem0CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(editQty));
                        discount0CalculatedDouble = discount0CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);
                    }

                    grossPerItemCalculatedDouble = grossPerItemCalculatedDouble + (Double.parseDouble(rate) * Double.parseDouble(editQty));

                }


                String grossPerItemCalculated = decimalFormat.format(grossPerItemCalculatedDouble);
                Float totalDiscountCalculated = discount0CalculatedDouble + discount5CalculatedDouble + discount12CalculatedDouble + discount18CalculatedDouble + discount28CalculatedDouble;

                m_assert.assertTrue(totalGrossAmountAfterDelete.equalsIgnoreCase(grossPerItemCalculated),
                        " Gross Amount Calculated Correctly After Deleted as " + totalGrossAmountAfterDelete + "----" + grossPerItemCalculated);


                gst5CalculatedDouble = gst5CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem5CalculatedDouble), taxRateList[1], String.valueOf(discount5CalculatedDouble)));
                gst12CalculatedDouble = gst12CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem12CalculatedDouble), taxRateList[2], String.valueOf(discount12CalculatedDouble)));
                gst18CalculatedDouble = gst18CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem18CalculatedDouble), taxRateList[3], String.valueOf(discount18CalculatedDouble)));
                gst28CalculatedDouble = gst28CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem28CalculatedDouble), taxRateList[4], String.valueOf(discount28CalculatedDouble)));
                gstCalculatedDouble = gstCalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem0CalculatedDouble), taxRateList[0], String.valueOf(discount0CalculatedDouble)));


                String gstCalculated = decimalFormat.format(gstCalculatedDouble);
                String gst5Calculated = decimalFormat.format(gst5CalculatedDouble);
                String gst12Calculated = decimalFormat.format(gst12CalculatedDouble);
                String gst18Calculated = decimalFormat.format(gst18CalculatedDouble);
                String gst28Calculated = decimalFormat.format(gst28CalculatedDouble);


                List<String> gstValuesInView = new ArrayList<>();
                String gstOnUI = getGstOnUI(oPage_Indent.text_GST0OnUI);
                String gst5OnUI = getGstOnUI(oPage_Indent.text_GST5OnUI);
                String gst12OnUI = getGstOnUI(oPage_Indent.text_GST12OnUI);
                String gst18OnUI = getGstOnUI(oPage_Indent.text_GST18OnUI);
                String gst28OnUI = getGstOnUI(oPage_Indent.text_GST28OnUI);

                gstValuesInView.add(gstOnUI);
                gstValuesInView.add(gst5OnUI);
                gstValuesInView.add(gst12OnUI);
                gstValuesInView.add(gst18OnUI);
                gstValuesInView.add(gst28OnUI);


                Double totalGST = 0.00;
                for (String gstText : gstValuesInView) {
                    if (!gstText.isEmpty()) {
                        totalGST = totalGST + Double.parseDouble(gstText);
                    }
                }

                if (!gstOnUI.isEmpty()) {
                    String gstCalculatedValue[] = gstCalculated.replace(".", " ").split(" ");
                    m_assert.assertTrue(gstOnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST Amount Calculated Correctly After Deleted as " + gstOnUI + "----" + gstCalculated);
                }
                if (!gst5OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst5Calculated.replace(".", " ").split(" ");
                    m_assert.assertTrue(gst5OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST5 Amount Calculated Correctly After Deleted as " + gst5OnUI + "----" + gst5Calculated);
                }
                if (!gst12OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst12Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst12OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST12 Amount Calculated Correctly After Deleted as " + gst12OnUI + "----" + gst12Calculated);
                }
                if (!gst18OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst18Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst18OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST18 Amount Calculated Correctly After Deleted as " + gst18OnUI + "----" + gst18Calculated);
                }
                if (!gst28OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst28Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst28OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST28 Amount Calculated Correctly After Deleted as " + gst28OnUI + "----" + gst28Calculated);
                }

                String netAmountOnUi = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_netAmountOnUI, "value");
                String discountOnUi = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_discountOnUIText, "value");
                String gstCalculatedValue[] = String.valueOf(formatDecimalNumber(totalDiscountCalculated)).replace(".", " ").split(" ");

                m_assert.assertTrue(discountOnUi.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                        " Discount Calculated Correctly ");
                String netAmountCalculated = decimalFormat.format(Double.parseDouble(totalGrossAmountAfterDelete) + totalGST - Double.parseDouble(discountOnUi));

                m_assert.assertTrue(netAmountOnUi.equalsIgnoreCase(netAmountCalculated),
                        " Net Amount Calculated Correctly After Deleted as " + netAmountOnUi);

                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElement(oPage_Indent.button_cancelOtherCharges);
                Cls_Generic_Methods.customWait(1);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                        " Save Button Clicked In PO ");
                Cls_Generic_Methods.customWait(10);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.option_purchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder),
                        "Purchase order option is selected");
                Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder);
                Cls_Generic_Methods.customWait(10);
             /*   recordFound = false ;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }


                m_assert.assertTrue(recordFound," PO Created Using Indent");*/
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approve, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_approve),
                        "Clicked on approve button");
                Cls_Generic_Methods.customWait(3);
                for (WebElement eYesButton : oPage_Purchase.button_yesButtonList) {
                    if (Cls_Generic_Methods.isElementDisplayed(eYesButton)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(eYesButton),
                                "Yes Button Clicked In Approved Confirmation");
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newTransaction, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newTransaction), "New Transaction button clicked to create GRN");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 30);
                int mrpSize = oPage_Purchase.list_MRPPrice.size();
                for (int i = 0; i < mrpSize; i++) {
                    Double dMRP = nextDoubleBetween2(100.0d, 500.0d);
                    String sMRP = String.valueOf(dMRP);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(i), sMRP), "Entered MRP <b>" + sMRP + " </b>for Item" + i);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(i), (sBatchNumber + i).concat(getRandomString(2)));
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_inputPaidQty.get(i));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_inputPaidQty.get(i), partialQty), "Entered Quantity <b>" + partialQty + " </b> for the item");
                }
                int expSize = oPage_Purchase.list_expiryDate.size();
                for (int i = 0; i < expSize; i++) {
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_expiryDate.get(i), "31/10/2024");
                }
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes),
                        " Transaction Notes Entered as :<b> " + transactionNotes + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType),
                        "Bill Type Selected:<b> " + billType + " </b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, (billNumber.concat(getRandomString(2)))),
                        "Bill Number: <b> " + (billNumber.concat(getRandomString(2))) + " </b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                //purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                if (purchaseTransactionTime.length() == 6) {
                    purchaseTransactionTime = "0" + purchaseTransactionTime;
                }
                purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);


                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                        "Date of bill selected:<b> " + oPage_Purchase.input_todayBillDate.getText() + " </b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "GRN created successfully for " + numberOfItem + " items");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 30);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                for (WebElement element : oPage_Purchase.list_purchaseTransactionDateandTimeList) {
                    String sDateAndTime = Cls_Generic_Methods.getTextInElement(element);
                    if (sDateAndTime.contains(purchaseTransactionDate) && sDateAndTime.contains(purchaseTransactionTime)) {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_editPurchaseTransaction, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_editPurchaseTransaction), "GRN Edit button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 30);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_deleteOtherCharges);
                Cls_Generic_Methods.customWait();
                purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                //purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                if (purchaseTransactionTime.length() == 6) {
                    purchaseTransactionTime = "0" + purchaseTransactionTime;
                }
                purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);

                mrpSize = oPage_Purchase.list_MRPPrice.size();
                for (int i = 0; i < mrpSize; i++) {
                    Double dMRP = nextDoubleBetween2(100.0d, 500.0d);
                    String sMRP = String.valueOf(dMRP);
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_MRPPrice.get(i));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(i), sMRP), "Entered MRP <b>" + sMRP + " </b>for Item" + i);
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_batchNumber.get(i));
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(i), (sBatchNumber + i).concat(getRandomString(2)));
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_inputPaidQty.get(i));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_inputPaidQty.get(i), editQty), "Entered Quantity <b>" + editQty + " </b> for the item");
                }
                expSize = oPage_Purchase.list_expiryDate.size();
                for (int i = 0; i < expSize; i++) {
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_expiryDate.get(i));
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_expiryDate.get(i), "30/11/2024");
                }
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "GRN created successfully for " + numberOfItem + " items");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 30);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                for (WebElement element : oPage_Purchase.list_purchaseTransactionDateandTimeList) {
                    String sDateAndTime = Cls_Generic_Methods.getTextInElement(element);
                    if (sDateAndTime.contains(purchaseTransactionDate) && sDateAndTime.contains(purchaseTransactionTime)) {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 8);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approveGRN, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_approveGRN), "GRN transaction approved successfully");
                Cls_Generic_Methods.customWait(15);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 50);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                Cls_Generic_Methods.customWait(8);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Indent.list_dateTimeOfIndentOrder, 30);
                recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(recordFound, " Order found in the purchase order page");
                String sPOStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);
                m_assert.assertTrue(sPOStatus.equalsIgnoreCase("Completed"),
                        " Purchase Order Status Showing correctly as Completed");
                Cls_Generic_Methods.customWait();

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);


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

                String indentStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);

                m_assert.assertTrue(indentStatus.equalsIgnoreCase("Partially Completed"),
                        " Indent Status Showing correctly as Partially Completed");

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
    public void createEditIndentPOForPartialItem() {


        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        List<String> itemList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();
        List<String> updatedQuantityList = new ArrayList<>();

        List<Double> rateList = new ArrayList<>();
        List<String> discountList = new ArrayList<>();


        int numberOfItem = 40;
        boolean bIndentOrderFound = false;
        String globalDiscountValue = "10";


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sINDENT_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_addNewIndent),
                        "New Button clicked in Order: Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 10);
                String sSelectedButton = Cls_Generic_Methods.getTextInElement(oPage_Indent.button_variantOrRequisitionSelected);
                if (sSelectedButton.equalsIgnoreCase(sExpectedButtonToBeSelected)) {
                    boolean ItemList = oPage_Indent.list_ItemDescriptionsUnderIndentPurchase.isEmpty();
                    if (ItemList) {
                        m_assert.assertTrue("ItemList is empty");
                    } else {
                        m_assert.assertTrue(false, "ItemList is not empty");
                    }
                } else {
                    m_assert.assertTrue("Expected Button = <b>" + sExpectedButtonToBeSelected + "</b> is not selected");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_vendorField, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.select_vendorField),
                        "clicked on Store selection field");
                Cls_Generic_Methods.customWait();
                boolean storeFound = false;
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, sStoreToShipOrToBillOrder);
                Cls_Generic_Methods.customWait();
                for (WebElement eStoreName : oPage_Indent.list_stores) {
                    if (Cls_Generic_Methods.getTextInElement(eStoreName).contains(sStoreToShipOrToBillOrder)) {
                        Cls_Generic_Methods.clickElement(eStoreName);
                        storeFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(storeFound, "Store found to do indent purchase : <b> " + sINDENT_STORE + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_noteUnderIndentForPurchase, 10);

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
                boolean bItemSelected = CommonActions.selectItemInInventory(oPage_Indent.list_ItemDescriptionsUnderIndentPurchase, itemList, numberOfItem, oPage_Indent.list_itemNameSelectedToCreateIndentPurchase);

                m_assert.assertTrue(bItemSelected, numberOfItem + " Item Selected From Left Panel Correctly ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_indentOrderDate, 10);
                indentOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderTime, "value");
                indentOrderTime = indentOrderTime.replaceAll("\\s+", "");
                indentOrderTime = indentOrderTime.replace("am", "AM").replace("pm", "PM");
                if (indentOrderTime.length() == 6) {
                    indentOrderTime = "0" + indentOrderTime;
                }
                m_assert.assertTrue("Indent order time:<b> " + indentOrderTime + "</b>");
                indentOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderDate, "value");
                indentOrderDate = indentOrderDate.replaceAll("/", "-");
                m_assert.assertTrue("Indent order date:<b> " + indentOrderDate + "</b>");
                mapTracker.put(key_CreatedAt_IndentOrderFromStore, indentOrderDate + "  |  " + indentOrderTime);
                Cls_Generic_Methods.customWait();

                for (WebElement eItem : oPage_Indent.list_itemNameSelectedToCreateIndentPurchase) {
                    int index = oPage_Indent.list_itemNameSelectedToCreateIndentPurchase.indexOf(eItem);

                    Cls_Generic_Methods.scrollToElementByJS(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index));
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index), sQTY);
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Indent.text_multipleOfItemText)) {
                        String multipleOf = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_multipleOfItemText).split(" ")[6];
                        Cls_Generic_Methods.clearValuesInElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index));
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index), multipleOf);
                        quantityList.add(multipleOf);

                    } else {
                        quantityList.add(sQTY);
                    }
                    m_assert.assertInfo(" Quantity Entered as <b> " + quantityList.get(index) + " </b> for item name <b> " + itemList.get(index) + " </b> at index " + index);
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.list_subStoreSelectedToCreateIndentPurchase.get(index), sSubStore);

                }

                Cls_Generic_Methods.customWait();

                int totalQuantity = 0;
                for (int i = 0; i < quantityList.size(); i++) {
                    totalQuantity = totalQuantity + Integer.parseInt(quantityList.get(i));
                }

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Indent.text_totalQuantity).equalsIgnoreCase("Total Quantity :" + totalQuantity),
                        " Total Quantity For all Item displayed correctly as : " + totalQuantity);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder),
                        " Indent order saved");
                Cls_Generic_Methods.customWait(10);

                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(date) &&
                            mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(time)) {
                        bIndentOrderFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(bIndentOrderFound, "Order found in the Indent order page");

                if (bIndentOrderFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approveIndent, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_approveIndent), "Approve Button Cliked to approve Indent");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approveConfirmation, 20);
                    Cls_Generic_Methods.clickElement(oPage_Indent.button_approveConfirmation);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newOrder, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newOrder),
                            "Clicked on New Order button to create PO");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_IndentNumberInPurchaseOrderPopUp, 10);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_IndentOrderType, 10);
                    OrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderTime, "value");
                    OrderTime = OrderTime.replaceAll("\\s+", "");
                    OrderTime = OrderTime.replace("am", "AM").replace("pm", "PM");
                    if (OrderTime.length() == 6) {
                        OrderTime = "0" + OrderTime;
                    }
                    m_assert.assertTrue(" order time:<b> " + OrderTime + "</b>");

                    OrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderDate, "value");
                    OrderDate = OrderDate.replaceAll("/", "-");
                    m_assert.assertTrue(" order date:<b> " + OrderDate + "</b>");

                    mapTracker.put(key_CreatedAt_PurchaseOrderTroughIndent, OrderDate + "  |  " + OrderTime);
                    Cls_Generic_Methods.customWait(2);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_searchVendorAddress, sVendorName);
                    Cls_Generic_Methods.customWait(3);
                    String sSelectedVendor = Cls_Generic_Methods.getTextInElement(oPage_Indent.vendorAddressName);
                    if (sSelectedVendor.contains(sVendorName)) {
                        Cls_Generic_Methods.clickElement(oPage_Indent.vendorAddressName);
                        m_assert.assertTrue("Selected Vendor:  <b>" + sVendorName + "</b>");
                    } else {
                        m_assert.assertTrue("Required vendor <b>" + sVendorName + "</b> is NOT selected");
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_BillToStore, 10);

                }

                Double rateValue = nextDoubleBetween2(100.0d, 500.0d);

                for (WebElement rate : oPage_Indent.list_rateListOfIndentOrder) {
                    if (Cls_Generic_Methods.isElementEnabled(rate)) {
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(rate, String.valueOf(rateValue)),
                                "Rate per unit  Entered as =  <b>" + rateValue + " </b>");
                    }
                }


                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElement(oPage_Indent.button_cancelOtherCharges);
                Cls_Generic_Methods.customWait(1);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                        " Save Button Clicked In PO ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.option_purchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder),
                        "Purchase order option is selected");
                Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder);
                Cls_Generic_Methods.customWait(8);
                boolean recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }


                m_assert.assertTrue(recordFound, " PO Created Using Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approve, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_editPOTransaction),
                        "Clicked on Edit button");
                Cls_Generic_Methods.customWait(15);


                int deleteItemNumber = numberOfItem / 2;
                for (int i = deleteItemNumber; i < oPage_Indent.list_rateListOfIndentOrder.size(); i++) {

                    m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByJS(oPage_Indent.list_deleteItemButton.get(i)),
                            " Scroll to Element");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                            "Delete Button Clicked on " + i);

                }
                OrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderTime, "value");
                OrderTime = OrderTime.replaceAll("\\s+", "");
                OrderTime = OrderTime.replace("am", "AM").replace("pm", "PM");
                if (OrderTime.length() == 6) {
                    OrderTime = "0" + OrderTime;
                }
                m_assert.assertTrue(" order time:<b> " + OrderTime + "</b>");

                OrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderDate, "value");
                OrderDate = OrderDate.replaceAll("/", "-");
                m_assert.assertTrue(" order date:<b> " + OrderDate + "</b>");

                mapTracker.put(key_CreatedAt_PurchaseOrderTroughIndent, OrderDate + "  |  " + OrderTime);
                Cls_Generic_Methods.customWait(2);


                int j = 0;
                for (WebElement addButton : oPage_Indent.list_addActionListOfIndentOrder) {
                    if (j < 10) {
                        m_assert.assertTrue(Cls_Generic_Methods.doubleClickElement(addButton),
                                " Add Button Clicked");
                        Cls_Generic_Methods.customWait(1);
                    } else {
                        if (j > 19) {
                            break;
                        }
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(addButton),
                                " Add Button Clicked");
                        Cls_Generic_Methods.customWait(1);
                    }
                    j++;
                }

                for (WebElement rate : oPage_Indent.list_rateListOfIndentOrder) {
                    Double rateValueNew = nextDoubleBetween2(100.0d, 500.0d);
                    rateList.add(rateValueNew);
                    if (Cls_Generic_Methods.isElementEnabled(rate)) {
                        Cls_Generic_Methods.scrollToElementByJS(rate);
                        Cls_Generic_Methods.clearValuesInElement(rate);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(rate, String.valueOf(rateValueNew)),
                                "Rate per unit  Entered as =  <b>" + rateValueNew + " </b>");
                    }
                }

                for (WebElement paidQty : oPage_Indent.list_paidQuantityListOfIndentOrder) {
                    String qty = Cls_Generic_Methods.getElementAttribute(paidQty, "value");
                    Double qtyDouble = Double.parseDouble(qty);
                    if (Cls_Generic_Methods.isElementEnabled(paidQty)) {
                        if (qty.isEmpty() || qtyDouble < 1) {
                            Cls_Generic_Methods.scrollToElementByJS(paidQty);
                            Cls_Generic_Methods.clearValuesInElement(paidQty);
                            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(paidQty, sQTY),
                                    "QTY per unit Entered as =  <b>" + sQTY + " </b>");
                            updatedQuantityList.add(sQTY);
                        } else {
                            updatedQuantityList.add(qty);
                        }
                    }
                }

                Cls_Generic_Methods.clearValuesInElement(oPage_Indent.input_globalDiscount);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_globalDiscount, globalDiscountValue);
                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.selectElementByIndex(oPage_Indent.input_globalDiscountType, 1);
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_applyGlobalDiscount),
                            "Clicked on apply global discount button");
                    Cls_Generic_Methods.customWait(1);

                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Indent.list_discountValueListOfIndentOrder, 15);
                }

                String totalGrossAmount = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");

                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                for (int i = 0; i < oPage_Indent.list_rateListOfIndentOrder.size() - deleteItemNumber; i++) {


                    String grossPerItemCalculated = String.valueOf(rateList.get(i) * Double.parseDouble(updatedQuantityList.get(i)));
                    grossPerItemCalculated = decimalNumberUptoTwo(Double.parseDouble(grossPerItemCalculated));
                    String globalDiscountPerItemCalculated = getGlobalDiscount(globalDiscountValue + "%", totalGrossAmount, grossPerItemCalculated);
                    String globalDiscountPerItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.list_discountValueListOfIndentOrder.get(i)).replace("-", "").trim();
                    discountList.add(globalDiscountPerItemOnUI);
                    Float newGlobalDiscountPerItemCalculatedFloat = Float.parseFloat(globalDiscountPerItemCalculated) - 00.01f;
                    Double newGlobalDiscountPerItemCalculated = newGlobalDiscountPerItemCalculatedFloat.doubleValue();
                    m_assert.assertTrue(globalDiscountPerItemOnUI.equalsIgnoreCase(globalDiscountPerItemCalculated) ||
                                    globalDiscountPerItemOnUI.equalsIgnoreCase(decimalFormat.format(newGlobalDiscountPerItemCalculated)),
                            " Global Discount Working correctly as discount apply correctly for item as : " + globalDiscountPerItemOnUI + " at index " + i);
                }

                int deleteItemIndex[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
                for (int i = 0; i < oPage_Indent.list_rateListOfIndentOrder.size(); i++) {

                    if (i > 0 && i < 50) {
                        if (getNumberPresentInArray(deleteItemIndex, i) && i < 11) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                    "Delete Button Clicked on " + i);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                    "Delete Button Clicked on " + i);
                        } else {
                            if (i > 20) {
                                break;
                            } else {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                        "Delete Button Clicked on " + i);
                            }
                        }

                    }

                }


                String totalGrossAmountAfterDelete = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");
                Double grossPerItemCalculatedDouble = 0.00;
                Float grossPerItem0CalculatedDouble = 0.00f;
                Float grossPerItem5CalculatedDouble = 0.00f;
                Float grossPerItem12CalculatedDouble = 0.00f;
                Float grossPerItem18CalculatedDouble = 0.00f;
                Float grossPerItem28CalculatedDouble = 0.00f;
                Float discount0CalculatedDouble = 0.00f;
                Float discount5CalculatedDouble = 0.00f;
                Float discount12CalculatedDouble = 0.00f;
                Float discount18CalculatedDouble = 0.00f;
                Float discount28CalculatedDouble = 0.00f;

                Double gstCalculatedDouble = 0.00;
                Double gst5CalculatedDouble = 0.00;
                Double gst12CalculatedDouble = 0.00;
                Double gst18CalculatedDouble = 0.00;
                Double gst28CalculatedDouble = 0.00;

                String taxRateList[] = {"0", "5", "12", "18", "28"};
                for (WebElement eRate : oPage_Indent.list_rateListOfIndentOrder) {
                    int index = oPage_Indent.list_rateListOfIndentOrder.indexOf(eRate);
                    if (Cls_Generic_Methods.isElementEnabled(eRate)) {
                        String rate = Cls_Generic_Methods.getElementAttribute(eRate, "value");
                        String sTaxRate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.list_taxRateListOfIndentOrder.get(index), "value");
                        String globalDiscountPerItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.list_discountValueListOfIndentOrder.get(index)).replace("-", "").trim();

                        if (sTaxRate.contains("5")) {
                            grossPerItem5CalculatedDouble = grossPerItem5CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(quantityList.get(index)));
                            discount5CalculatedDouble = discount5CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);
                        } else if (sTaxRate.contains("12")) {
                            grossPerItem12CalculatedDouble = grossPerItem12CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(quantityList.get(index)));
                            discount12CalculatedDouble = discount12CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);

                        } else if (sTaxRate.contains("18")) {
                            grossPerItem18CalculatedDouble = grossPerItem18CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(quantityList.get(index)));
                            discount18CalculatedDouble = discount18CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);


                        } else if (sTaxRate.contains("28")) {
                            grossPerItem28CalculatedDouble = grossPerItem28CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(quantityList.get(index)));
                            discount28CalculatedDouble = discount28CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);

                        } else {
                            grossPerItem0CalculatedDouble = grossPerItem0CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(quantityList.get(index)));
                            discount0CalculatedDouble = discount0CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);
                        }

                        grossPerItemCalculatedDouble = grossPerItemCalculatedDouble + (Double.parseDouble(rate) * Double.parseDouble(quantityList.get(index)));
                    }
                }


                String grossPerItemCalculated = decimalFormat.format(grossPerItemCalculatedDouble);
                Float totalDiscountCalculated = discount0CalculatedDouble + discount5CalculatedDouble + discount12CalculatedDouble + discount18CalculatedDouble + discount28CalculatedDouble;

                m_assert.assertTrue(totalGrossAmountAfterDelete.equalsIgnoreCase(grossPerItemCalculated),
                        " Gross Amount Calculated Correctly After Deleted as " + totalGrossAmountAfterDelete + "----" + grossPerItemCalculated);


                gst5CalculatedDouble = gst5CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem5CalculatedDouble), taxRateList[1], String.valueOf(discount5CalculatedDouble)));
                gst12CalculatedDouble = gst12CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem12CalculatedDouble), taxRateList[2], String.valueOf(discount12CalculatedDouble)));
                gst18CalculatedDouble = gst18CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem18CalculatedDouble), taxRateList[3], String.valueOf(discount18CalculatedDouble)));
                gst28CalculatedDouble = gst28CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem28CalculatedDouble), taxRateList[4], String.valueOf(discount28CalculatedDouble)));
                gstCalculatedDouble = gstCalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem0CalculatedDouble), taxRateList[0], String.valueOf(discount0CalculatedDouble)));


                String gstCalculated = decimalFormat.format(gstCalculatedDouble);
                String gst5Calculated = decimalFormat.format(gst5CalculatedDouble);
                String gst12Calculated = decimalFormat.format(gst12CalculatedDouble);
                String gst18Calculated = decimalFormat.format(gst18CalculatedDouble);
                String gst28Calculated = decimalFormat.format(gst28CalculatedDouble);


                List<String> gstValuesInView = new ArrayList<>();
                String gstOnUI = getGstOnUI(oPage_Indent.text_GST0OnUI);
                String gst5OnUI = getGstOnUI(oPage_Indent.text_GST5OnUI);
                String gst12OnUI = getGstOnUI(oPage_Indent.text_GST12OnUI);
                String gst18OnUI = getGstOnUI(oPage_Indent.text_GST18OnUI);
                String gst28OnUI = getGstOnUI(oPage_Indent.text_GST28OnUI);

                gstValuesInView.add(gstOnUI);
                gstValuesInView.add(gst5OnUI);
                gstValuesInView.add(gst12OnUI);
                gstValuesInView.add(gst18OnUI);
                gstValuesInView.add(gst28OnUI);


                Double totalGST = 0.00;
                for (String gstText : gstValuesInView) {
                    if (!gstText.isEmpty()) {
                        totalGST = totalGST + Double.parseDouble(gstText);
                    }
                }

                if (!gstOnUI.isEmpty()) {
                    String gstCalculatedValue[] = gstCalculated.replace(".", " ").split(" ");
                    m_assert.assertTrue(gstOnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST Amount Calculated Correctly After Deleted as " + gstOnUI + "----" + gstCalculated);
                }
                if (!gst5OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst5Calculated.replace(".", " ").split(" ");
                    m_assert.assertTrue(gst5OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST5 Amount Calculated Correctly After Deleted as " + gst5OnUI + "----" + gst5Calculated);
                }
                if (!gst12OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst12Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst12OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST12 Amount Calculated Correctly After Deleted as " + gst12OnUI + "----" + gst12Calculated);
                }
                if (!gst18OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst18Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst18OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST18 Amount Calculated Correctly After Deleted as " + gst18OnUI + "----" + gst18Calculated);
                }
                if (!gst28OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst28Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst28OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST28 Amount Calculated Correctly After Deleted as " + gst28OnUI + "----" + gst28Calculated);
                }

                String netAmountOnUi = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_netAmountOnUI, "value");
                String discountOnUi = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_discountOnUIText, "value");
                String gstCalculatedValue[] = String.valueOf(formatDecimalNumber(totalDiscountCalculated)).replace(".", " ").split(" ");

                m_assert.assertTrue(discountOnUi.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                        " Discount Calculated Correctly ");
                String netAmountCalculated = decimalFormat.format(Double.parseDouble(totalGrossAmountAfterDelete) + totalGST - Double.parseDouble(discountOnUi));

                m_assert.assertTrue(netAmountOnUi.equalsIgnoreCase(netAmountCalculated),
                        " Net Amount Calculated Correctly After Deleted as " + netAmountOnUi);

                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElement(oPage_Indent.button_cancelOtherCharges);
                Cls_Generic_Methods.customWait(1);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                        " Save Button Clicked In PO ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.option_purchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder),
                        "Purchase order option is selected");
                Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder);
                Cls_Generic_Methods.customWait(8);
               /* recordFound = false ;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }*/


                m_assert.assertTrue(" PO Created Using Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approve, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_approve),
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
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newTransaction, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newTransaction), "New Transaction button clicked to create GRN");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 30);
                int mrpSize = oPage_Purchase.list_MRPPrice.size();
                for (int i = 0; i < mrpSize; i++) {
                    Double dMRP = nextDoubleBetween2(100.0d, 500.0d);
                    String sMRP = String.valueOf(dMRP);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(i), sMRP), "Entered MRP <b>" + sMRP + " </b>for Item" + i);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(i), (sBatchNumber + i).concat(getRandomString(2)));
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_inputPaidQty.get(i));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_inputPaidQty.get(i), partialQty), "Entered Quantity <b>" + partialQty + " </b> for the item");
                }
                int expSize = oPage_Purchase.list_expiryDate.size();
                for (int i = 0; i < expSize; i++) {
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_expiryDate.get(i), "31/10/2024");
                }
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes),
                        " Transaction Notes Entered as :<b> " + transactionNotes + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType),
                        "Bill Type Selected:<b> " + billType + " </b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, (billNumber.concat(getRandomString(2)))),
                        "Bill Number: <b> " + (billNumber.concat(getRandomString(2))) + " </b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                //purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                if (purchaseTransactionTime.length() == 6) {
                    purchaseTransactionTime = "0" + purchaseTransactionTime;
                }
                purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);


                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                        "Date of bill selected:<b> " + oPage_Purchase.input_todayBillDate.getText() + " </b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "GRN created successfully for " + (numberOfItem / 2) + " items");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 30);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                for (WebElement element : oPage_Purchase.list_purchaseTransactionDateandTimeList) {
                    String sDateAndTime = Cls_Generic_Methods.getTextInElement(element);
                    if (sDateAndTime.contains(purchaseTransactionDate) && sDateAndTime.contains(purchaseTransactionTime)) {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_editPurchaseTransaction, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_editPurchaseTransaction), "GRN Edit button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 30);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_deleteOtherCharges);
                Cls_Generic_Methods.customWait();
                purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                //purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                if (purchaseTransactionTime.length() == 6) {
                    purchaseTransactionTime = "0" + purchaseTransactionTime;
                }
                purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);

                mrpSize = oPage_Purchase.list_MRPPrice.size();
                for (int i = 0; i < mrpSize; i++) {
                    Double dMRP = nextDoubleBetween2(100.0d, 500.0d);
                    String sMRP = String.valueOf(dMRP);
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_MRPPrice.get(i));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(i), sMRP), "Entered MRP <b>" + sMRP + " </b>for Item" + i);
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_batchNumber.get(i));
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(i), (sBatchNumber + i).concat(getRandomString(2)));
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_inputPaidQty.get(i));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_inputPaidQty.get(i), sQTY), "Entered Quantity <b>" + sQTY + " </b> for the item");
                }
                expSize = oPage_Purchase.list_expiryDate.size();
                for (int i = 0; i < expSize; i++) {
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_expiryDate.get(i));
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_expiryDate.get(i), "30/11/2024");
                }
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "GRN created successfully for " + (numberOfItem / 2) + " items");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 30);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                for (WebElement element : oPage_Purchase.list_purchaseTransactionDateandTimeList) {
                    String sDateAndTime = Cls_Generic_Methods.getTextInElement(element);
                    if (sDateAndTime.contains(purchaseTransactionDate) && sDateAndTime.contains(purchaseTransactionTime)) {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 8);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approveGRN, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_approveGRN), "GRN transaction Edited and approved successfully");
                Cls_Generic_Methods.customWait(15);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 50);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                Cls_Generic_Methods.customWait(8);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Indent.list_dateTimeOfIndentOrder, 30);
                recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(recordFound, " Order found in the purchase order page");
                String sPOStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);
                m_assert.assertTrue(sPOStatus.equalsIgnoreCase("Completed"),
                        " Purchase Order Status Showing correctly as Completed");
                Cls_Generic_Methods.customWait();

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);


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

                String indentStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);

                m_assert.assertTrue(indentStatus.equalsIgnoreCase("Partially Completed"),
                        " Indent Status Showing correctly as Partially Completed");
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
    public void createEditPOForRemainingPartialItem() {


        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        List<String> itemList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();
        List<String> updatedQuantityList = new ArrayList<>();

        List<Double> rateList = new ArrayList<>();
        List<String> discountList = new ArrayList<>();


        int numberOfItem = 40;
        boolean bIndentOrderFound = false;
        String globalDiscountValue = "10";


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sINDENT_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);

                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(date) &&
                            mapTracker.get(key_CreatedAt_IndentOrderFromStore).contains(time)) {
                        bIndentOrderFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(bIndentOrderFound, "Order found in the Indent order page");

                if (bIndentOrderFound) {

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newOrder, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newOrder),
                            "Clicked on New Order button to create PO");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_IndentNumberInPurchaseOrderPopUp, 10);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_IndentOrderType, 10);
                    OrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderTime, "value");
                    OrderTime = OrderTime.replaceAll("\\s+", "");
                    OrderTime = OrderTime.replace("am", "AM").replace("pm", "PM");
                    if (OrderTime.length() == 6) {
                        OrderTime = "0" + OrderTime;
                    }
                    m_assert.assertTrue(" order time:<b> " + OrderTime + "</b>");

                    OrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderDate, "value");
                    OrderDate = OrderDate.replaceAll("/", "-");
                    m_assert.assertTrue(" order date:<b> " + OrderDate + "</b>");

                    mapTracker.put(key_CreatedAt_PurchaseOrderTroughIndent, OrderDate + "  |  " + OrderTime);
                    Cls_Generic_Methods.customWait(2);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_searchVendorAddress, sVendorName);
                    Cls_Generic_Methods.customWait(3);
                    String sSelectedVendor = Cls_Generic_Methods.getTextInElement(oPage_Indent.vendorAddressName);
                    if (sSelectedVendor.contains(sVendorName)) {
                        Cls_Generic_Methods.clickElement(oPage_Indent.vendorAddressName);
                        m_assert.assertTrue("Selected Vendor:  <b>" + sVendorName + "</b>");
                    } else {
                        m_assert.assertTrue("Required vendor <b>" + sVendorName + "</b> is NOT selected");
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.dropdown_BillToStore, 10);

                }

                Double rateValue = nextDoubleBetween2(100.0d, 500.0d);

                for (WebElement rate : oPage_Indent.list_rateListOfIndentOrder) {
                    if (Cls_Generic_Methods.isElementEnabled(rate)) {
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(rate, String.valueOf(rateValue)),
                                "Rate per unit  Entered as =  <b>" + rateValue + " </b>");
                    }
                }


                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElement(oPage_Indent.button_cancelOtherCharges);
                Cls_Generic_Methods.customWait(1);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                        " Save Button Clicked In PO ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.option_purchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder),
                        "Purchase order option is selected");
                Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder);
                Cls_Generic_Methods.customWait(8);
                boolean recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }


                m_assert.assertTrue(recordFound, " PO Created Using Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approve, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_editPOTransaction),
                        "Clicked on Edit button");
                Cls_Generic_Methods.customWait(10);


                int j = 0;
                for (WebElement addButton : oPage_Indent.list_addActionListOfIndentOrder) {
                    if (j < 10) {
                        m_assert.assertTrue(Cls_Generic_Methods.doubleClickElement(addButton),
                                " Add Button Clicked");
                        Cls_Generic_Methods.customWait(1);
                    } else {
                        if (j > 19) {
                            break;
                        }
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(addButton),
                                " Add Button Clicked");
                        Cls_Generic_Methods.customWait(1);
                    }
                    j++;
                }

                for (WebElement rate : oPage_Indent.list_rateListOfIndentOrder) {
                    rateValue = nextDoubleBetween2(100.0d, 500.0d);
                    rateList.add(rateValue);
                    if (Cls_Generic_Methods.isElementEnabled(rate)) {
                        Cls_Generic_Methods.scrollToElementByJS(rate);
                        Cls_Generic_Methods.clearValuesInElement(rate);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(rate, String.valueOf(rateValue)),
                                "Rate per unit  Entered as =  <b>" + rateValue + " </b>");
                    }
                }

                for (WebElement paidQty : oPage_Indent.list_paidQuantityListOfIndentOrder) {
                    String qty = Cls_Generic_Methods.getElementAttribute(paidQty, "value");
                    Double qtyDouble = Double.parseDouble(qty);
                    if (Cls_Generic_Methods.isElementEnabled(paidQty)) {
                        if (qty.isEmpty() || qtyDouble < 1) {
                            Cls_Generic_Methods.scrollToElementByJS(paidQty);
                            Cls_Generic_Methods.clearValuesInElement(paidQty);
                            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(paidQty, sQTY),
                                    "QTY per unit Entered as =  <b>" + sQTY + " </b>");
                            updatedQuantityList.add(sQTY);
                        } else {
                            updatedQuantityList.add(qty);
                        }
                    }
                }

                Cls_Generic_Methods.clearValuesInElement(oPage_Indent.input_globalDiscount);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_globalDiscount, globalDiscountValue);
                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.selectElementByIndex(oPage_Indent.input_globalDiscountType, 1);
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_applyGlobalDiscount),
                            "Clicked on apply global discount button");
                    Cls_Generic_Methods.customWait(1);

                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Indent.list_discountValueListOfIndentOrder, 15);
                }

                String totalGrossAmount = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");

                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                for (int i = 0; i < oPage_Indent.list_rateListOfIndentOrder.size(); i++) {


                    String grossPerItemCalculated = String.valueOf(rateList.get(i) * Double.parseDouble(updatedQuantityList.get(i)));
                    grossPerItemCalculated = decimalNumberUptoTwo(Double.parseDouble(grossPerItemCalculated));
                    String globalDiscountPerItemCalculated = getGlobalDiscount(globalDiscountValue + "%", totalGrossAmount, grossPerItemCalculated);
                    String globalDiscountPerItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.list_discountValueListOfIndentOrder.get(i)).replace("-", "").trim();
                    discountList.add(globalDiscountPerItemOnUI);
                    Float newGlobalDiscountPerItemCalculatedFloat = Float.parseFloat(globalDiscountPerItemCalculated) - 00.01f;
                    Double newGlobalDiscountPerItemCalculated = newGlobalDiscountPerItemCalculatedFloat.doubleValue();
                    m_assert.assertTrue(globalDiscountPerItemOnUI.equalsIgnoreCase(globalDiscountPerItemCalculated) ||
                                    globalDiscountPerItemOnUI.equalsIgnoreCase(decimalFormat.format(newGlobalDiscountPerItemCalculated)),
                            " Global Discount Working correctly as discount apply correctly for item as : " + globalDiscountPerItemOnUI + " at index " + i);
                }

                int deleteItemIndex[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
                for (int i = 0; i < oPage_Indent.list_rateListOfIndentOrder.size(); i++) {

                    if (i > 0 && i < 50) {
                        if (getNumberPresentInArray(deleteItemIndex, i) && i < 11) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                    "Delete Button Clicked on " + i);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                    "Delete Button Clicked on " + i);
                        } else {
                            if (i > 20) {
                                break;
                            } else {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_deleteItemButton.get(i)),
                                        "Delete Button Clicked on " + i);
                            }
                        }

                    }

                }


                String totalGrossAmountAfterDelete = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_grossAmountOnUI, "value");
                Double grossPerItemCalculatedDouble = 0.00;
                Float grossPerItem0CalculatedDouble = 0.00f;
                Float grossPerItem5CalculatedDouble = 0.00f;
                Float grossPerItem12CalculatedDouble = 0.00f;
                Float grossPerItem18CalculatedDouble = 0.00f;
                Float grossPerItem28CalculatedDouble = 0.00f;
                Float discount0CalculatedDouble = 0.00f;
                Float discount5CalculatedDouble = 0.00f;
                Float discount12CalculatedDouble = 0.00f;
                Float discount18CalculatedDouble = 0.00f;
                Float discount28CalculatedDouble = 0.00f;

                Double gstCalculatedDouble = 0.00;
                Double gst5CalculatedDouble = 0.00;
                Double gst12CalculatedDouble = 0.00;
                Double gst18CalculatedDouble = 0.00;
                Double gst28CalculatedDouble = 0.00;

                String taxRateList[] = {"0", "5", "12", "18", "28"};
                for (WebElement eRate : oPage_Indent.list_rateListOfIndentOrder) {
                    int index = oPage_Indent.list_rateListOfIndentOrder.indexOf(eRate);
                    if (Cls_Generic_Methods.isElementEnabled(eRate)) {
                        String rate = Cls_Generic_Methods.getElementAttribute(eRate, "value");
                        String sTaxRate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.list_taxRateListOfIndentOrder.get(index), "value");
                        String globalDiscountPerItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.list_discountValueListOfIndentOrder.get(index)).replace("-", "").trim();

                        if (sTaxRate.contains("5")) {
                            grossPerItem5CalculatedDouble = grossPerItem5CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(updatedQuantityList.get(index)));
                            discount5CalculatedDouble = discount5CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);
                        } else if (sTaxRate.contains("12")) {
                            grossPerItem12CalculatedDouble = grossPerItem12CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(updatedQuantityList.get(index)));
                            discount12CalculatedDouble = discount12CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);

                        } else if (sTaxRate.contains("18")) {
                            grossPerItem18CalculatedDouble = grossPerItem18CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(updatedQuantityList.get(index)));
                            discount18CalculatedDouble = discount18CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);


                        } else if (sTaxRate.contains("28")) {
                            grossPerItem28CalculatedDouble = grossPerItem28CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(updatedQuantityList.get(index)));
                            discount28CalculatedDouble = discount28CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);

                        } else {
                            grossPerItem0CalculatedDouble = grossPerItem0CalculatedDouble + (Float.parseFloat(rate) * Float.parseFloat(updatedQuantityList.get(index)));
                            discount0CalculatedDouble = discount0CalculatedDouble + Float.parseFloat(globalDiscountPerItemOnUI);
                        }

                        grossPerItemCalculatedDouble = grossPerItemCalculatedDouble + (Double.parseDouble(rate) * Double.parseDouble(updatedQuantityList.get(index)));
                    }
                }


                String grossPerItemCalculated = decimalFormat.format(grossPerItemCalculatedDouble);
                Float totalDiscountCalculated = discount0CalculatedDouble + discount5CalculatedDouble + discount12CalculatedDouble + discount18CalculatedDouble + discount28CalculatedDouble;

                m_assert.assertTrue(totalGrossAmountAfterDelete.equalsIgnoreCase(grossPerItemCalculated),
                        " Gross Amount Calculated Correctly After Deleted as " + totalGrossAmountAfterDelete + "----" + grossPerItemCalculated);


                gst5CalculatedDouble = gst5CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem5CalculatedDouble), taxRateList[1], String.valueOf(discount5CalculatedDouble)));
                gst12CalculatedDouble = gst12CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem12CalculatedDouble), taxRateList[2], String.valueOf(discount12CalculatedDouble)));
                gst18CalculatedDouble = gst18CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem18CalculatedDouble), taxRateList[3], String.valueOf(discount18CalculatedDouble)));
                gst28CalculatedDouble = gst28CalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem28CalculatedDouble), taxRateList[4], String.valueOf(discount28CalculatedDouble)));
                gstCalculatedDouble = gstCalculatedDouble + Double.parseDouble(getGST(String.valueOf(grossPerItem0CalculatedDouble), taxRateList[0], String.valueOf(discount0CalculatedDouble)));


                String gstCalculated = decimalFormat.format(gstCalculatedDouble);
                String gst5Calculated = decimalFormat.format(gst5CalculatedDouble);
                String gst12Calculated = decimalFormat.format(gst12CalculatedDouble);
                String gst18Calculated = decimalFormat.format(gst18CalculatedDouble);
                String gst28Calculated = decimalFormat.format(gst28CalculatedDouble);


                List<String> gstValuesInView = new ArrayList<>();
                String gstOnUI = getGstOnUI(oPage_Indent.text_GST0OnUI);
                String gst5OnUI = getGstOnUI(oPage_Indent.text_GST5OnUI);
                String gst12OnUI = getGstOnUI(oPage_Indent.text_GST12OnUI);
                String gst18OnUI = getGstOnUI(oPage_Indent.text_GST18OnUI);
                String gst28OnUI = getGstOnUI(oPage_Indent.text_GST28OnUI);

                gstValuesInView.add(gstOnUI);
                gstValuesInView.add(gst5OnUI);
                gstValuesInView.add(gst12OnUI);
                gstValuesInView.add(gst18OnUI);
                gstValuesInView.add(gst28OnUI);


                Double totalGST = 0.00;
                for (String gstText : gstValuesInView) {
                    if (!gstText.isEmpty()) {
                        totalGST = totalGST + Double.parseDouble(gstText);
                    }
                }

                if (!gstOnUI.isEmpty()) {
                    String gstCalculatedValue[] = gstCalculated.replace(".", " ").split(" ");
                    m_assert.assertTrue(gstOnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST Amount Calculated Correctly After Deleted as " + gstOnUI + "----" + gstCalculated);
                }
                if (!gst5OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst5Calculated.replace(".", " ").split(" ");
                    m_assert.assertTrue(gst5OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST5 Amount Calculated Correctly After Deleted as " + gst5OnUI + "----" + gst5Calculated);
                }
                if (!gst12OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst12Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst12OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST12 Amount Calculated Correctly After Deleted as " + gst12OnUI + "----" + gst12Calculated);
                }
                if (!gst18OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst18Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst18OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST18 Amount Calculated Correctly After Deleted as " + gst18OnUI + "----" + gst18Calculated);
                }
                if (!gst28OnUI.isEmpty()) {
                    String gstCalculatedValue[] = gst28Calculated.replace(".", " ").split(" ");

                    m_assert.assertTrue(gst28OnUI.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                            " GST28 Amount Calculated Correctly After Deleted as " + gst28OnUI + "----" + gst28Calculated);
                }

                String netAmountOnUi = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_netAmountOnUI, "value");
                String discountOnUi = Cls_Generic_Methods.getElementAttribute(oPage_Indent.text_discountOnUIText, "value");
                String gstCalculatedValue[] = String.valueOf(formatDecimalNumber(totalDiscountCalculated)).replace(".", " ").split(" ");

                m_assert.assertTrue(discountOnUi.contains(gstCalculatedValue[0] + "." + gstCalculatedValue[1].substring(0, 1)),
                        " Discount Calculated Correctly ");
                String netAmountCalculated = decimalFormat.format(Double.parseDouble(totalGrossAmountAfterDelete) + totalGST - Double.parseDouble(discountOnUi));

                m_assert.assertTrue(netAmountOnUi.equalsIgnoreCase(netAmountCalculated),
                        " Net Amount Calculated Correctly After Deleted as " + netAmountOnUi);

                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clickElement(oPage_Indent.button_cancelOtherCharges);
                Cls_Generic_Methods.customWait(1);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                        " Save Button Clicked In PO ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.option_purchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder),
                        "Purchase order option is selected");
                Cls_Generic_Methods.isElementEnabled(oPage_Indent.option_purchaseOrder);
                Cls_Generic_Methods.customWait(8);
                recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }


                m_assert.assertTrue(recordFound, " PO Created Using Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_approve, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_approve),
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
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newTransaction, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newTransaction), "New Transaction button clicked to create GRN");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 30);
                int mrpSize = oPage_Purchase.list_MRPPrice.size();
                for (int i = 0; i < mrpSize; i++) {
                    Double dMRP = nextDoubleBetween2(100.0d, 500.0d);
                    String sMRP = String.valueOf(dMRP);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(i), sMRP), "Entered MRP <b>" + sMRP + " </b>for Item" + i);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(i), (sBatchNumber + i).concat(getRandomString(2)));
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_inputPaidQty.get(i));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_inputPaidQty.get(i), partialQty), "Entered Quantity <b>" + partialQty + " </b> for the item");
                }
                int expSize = oPage_Purchase.list_expiryDate.size();
                for (int i = 0; i < expSize; i++) {
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_expiryDate.get(i), "31/10/2024");
                }
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes),
                        " Transaction Notes Entered as :<b> " + transactionNotes + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType),
                        "Bill Type Selected:<b> " + billType + " </b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, (billNumber.concat(getRandomString(2)))),
                        "Bill Number: <b> " + (billNumber.concat(getRandomString(2))) + " </b>");
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                //purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                if (purchaseTransactionTime.length() == 6) {
                    purchaseTransactionTime = "0" + purchaseTransactionTime;
                }
                purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);


                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                        "Date of bill selected:<b> " + oPage_Purchase.input_todayBillDate.getText() + " </b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "GRN created successfully for " + (numberOfItem / 2) + " items");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 30);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                for (WebElement element : oPage_Purchase.list_purchaseTransactionDateandTimeList) {
                    String sDateAndTime = Cls_Generic_Methods.getTextInElement(element);
                    if (sDateAndTime.contains(purchaseTransactionDate) && sDateAndTime.contains(purchaseTransactionTime)) {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_editPurchaseTransaction, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_editPurchaseTransaction), "GRN Edit button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 30);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_deleteOtherCharges);
                Cls_Generic_Methods.customWait();
                purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                //purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                if (purchaseTransactionTime.length() == 6) {
                    purchaseTransactionTime = "0" + purchaseTransactionTime;
                }
                purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);

                mrpSize = oPage_Purchase.list_MRPPrice.size();
                for (int i = 0; i < mrpSize; i++) {
                    Double dMRP = nextDoubleBetween2(100.0d, 500.0d);
                    String sMRP = String.valueOf(dMRP);
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_MRPPrice.get(i));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(i), sMRP), "Entered MRP <b>" + sMRP + " </b>for Item" + i);
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_batchNumber.get(i));
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(i), (sBatchNumber + i).concat(getRandomString(2)));
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_inputPaidQty.get(i));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_inputPaidQty.get(i), sQTY), "Entered Quantity <b>" + sQTY + " </b> for the item");
                }
                expSize = oPage_Purchase.list_expiryDate.size();
                for (int i = 0; i < expSize; i++) {
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_expiryDate.get(i));
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_expiryDate.get(i), "30/11/2024");
                }
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "GRN created successfully for " + (numberOfItem / 2) + " items");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 30);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                for (WebElement element : oPage_Purchase.list_purchaseTransactionDateandTimeList) {
                    String sDateAndTime = Cls_Generic_Methods.getTextInElement(element);
                    if (sDateAndTime.contains(purchaseTransactionDate) && sDateAndTime.contains(purchaseTransactionTime)) {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 8);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approveGRN, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_approveGRN), "GRN transaction Edited and approved successfully");
                Cls_Generic_Methods.customWait(15);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 50);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                Cls_Generic_Methods.customWait(8);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Indent.list_dateTimeOfIndentOrder, 30);
                recordFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (OrderDate.contains(date) &&
                            OrderTime.contains(time)) {
                        recordFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(recordFound, " Order found in the purchase order page");
                String sPOStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);
                m_assert.assertTrue(sPOStatus.equalsIgnoreCase("Completed"),
                        " Purchase Order Status Showing correctly as Completed");
                Cls_Generic_Methods.customWait();

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);


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

                String indentStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder);

                m_assert.assertTrue(indentStatus.equalsIgnoreCase("Completed"),
                        " Indent Status Showing correctly as Completed");

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
    public void createAndCancelIndent() {


        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        List<String> itemList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();
        List<Double> rateList = new ArrayList<>();
        List<String> discountList = new ArrayList<>();
        List<Double> updateRateList = new ArrayList<>();
        String editQty = "3";


        int numberOfItem = 1;
        boolean bIndentOrderFound = false;
        String sCancelReason = "CancelReasonTest" + getRandomString(3);


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sINDENT_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_addNewIndent),
                        "New Button clicked in Order: Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 10);
                String sSelectedButton = Cls_Generic_Methods.getTextInElement(oPage_Indent.button_variantOrRequisitionSelected);
                if (sSelectedButton.equalsIgnoreCase(sExpectedButtonToBeSelected)) {
                    boolean ItemList = oPage_Indent.list_ItemDescriptionsUnderIndentPurchase.isEmpty();
                    if (ItemList) {
                        m_assert.assertTrue("ItemList is empty");
                    } else {
                        m_assert.assertTrue(false, "ItemList is not empty");
                    }
                } else {
                    m_assert.assertTrue("Expected Button = <b>" + sExpectedButtonToBeSelected + "</b> is not selected");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_vendorField, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.select_vendorField),
                        "clicked on Store selection field");
                Cls_Generic_Methods.customWait();
                boolean storeFound = false;
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, sStoreToShipOrToBillOrder);
                Cls_Generic_Methods.customWait();
                for (WebElement eStoreName : oPage_Indent.list_stores) {
                    if (Cls_Generic_Methods.getTextInElement(eStoreName).contains(sStoreToShipOrToBillOrder)) {
                        Cls_Generic_Methods.clickElement(eStoreName);
                        storeFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(storeFound, "Store found to do indent purchase : <b> " + sINDENT_STORE + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_noteUnderIndentForPurchase, 10);

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
                boolean bItemSelected = CommonActions.selectItemInInventory(oPage_Indent.list_ItemDescriptionsUnderIndentPurchase, itemList, numberOfItem, oPage_Indent.list_itemNameSelectedToCreateIndentPurchase);

                m_assert.assertTrue(bItemSelected, numberOfItem + " Item Selected From Left Panel Correctly ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_indentOrderDate, 10);
                indentOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderTime, "value");
                indentOrderTime = indentOrderTime.replaceAll("\\s+", "");
                indentOrderTime = indentOrderTime.replace("am", "AM").replace("pm", "PM");
                if (indentOrderTime.length() == 6) {
                    indentOrderTime = "0" + indentOrderTime;
                }
                m_assert.assertTrue("Indent order time:<b> " + indentOrderTime + "</b>");
                indentOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderDate, "value");
                indentOrderDate = indentOrderDate.replaceAll("/", "-");
                m_assert.assertTrue("Indent order date:<b> " + indentOrderDate + "</b>");
                mapTracker.put(key_CreatedAt_IndentOrderFromStore, indentOrderDate + "  |  " + indentOrderTime);
                Cls_Generic_Methods.customWait();

                for (WebElement eItem : oPage_Indent.list_itemNameSelectedToCreateIndentPurchase) {
                    int index = oPage_Indent.list_itemNameSelectedToCreateIndentPurchase.indexOf(eItem);

                    Cls_Generic_Methods.scrollToElementByJS(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index));
                    Cls_Generic_Methods.customWait(1);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index), sQTY);
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Indent.text_multipleOfItemText)) {
                        String multipleOf = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_multipleOfItemText).split(" ")[6];
                        Cls_Generic_Methods.clearValuesInElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index));
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index), multipleOf);
                        quantityList.add(multipleOf);

                    } else {
                        quantityList.add(sQTY);
                    }
                    m_assert.assertInfo(" Quantity Entered as <b> " + quantityList.get(index) + " </b> for item name <b> " + itemList.get(index) + " </b> at index " + index);
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.list_subStoreSelectedToCreateIndentPurchase.get(index), sSubStore);
                    Cls_Generic_Methods.customWait(1);

                }

                Cls_Generic_Methods.customWait();

                int totalQuantity = 0;
                for (int i = 0; i < quantityList.size(); i++) {
                    totalQuantity = totalQuantity + Integer.parseInt(quantityList.get(i));
                }

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Indent.text_totalQuantity).equalsIgnoreCase("Total Quantity :" + totalQuantity),
                        " Total Quantity For all Item displayed correctly as : " + totalQuantity);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder),
                        " Indent order saved");
                Cls_Generic_Methods.customWait(5);

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
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_cancelIndent, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_cancelIndent), "Cancel button clicked to cancel Indent");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_indentCancelReason, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_indentCancelReason, sCancelReason), "Entered Cancellation reason as <b>" + sCancelReason + "</b>");
                    Cls_Generic_Methods.clickElement(oPage_Indent.button_cancelIndentConfirmation);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_cancelReasonOnUI, 20);
                    String sCancelReasonOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_cancelReasonOnUI).trim();
                    m_assert.assertTrue((sCancelReason.equalsIgnoreCase(sCancelReasonOnUI)), "Validated cancellation reason on UI :<b> " + sCancelReasonOnUI);
                    String sIndentStatus = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_statusOfOrder).trim();
                    m_assert.assertTrue(sIndentStatus.equalsIgnoreCase("Cancelled"), "Validated indent status : <b>" + sIndentStatus);
                    Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                    Cls_Generic_Methods.customWait();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getGlobalDiscount(String discount, String totalGrossAmount, String grossAmountPerItem) {

        String globalDiscountValue = "";
        Double globalDiscountCalculated = 0.00;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        try {
            if (discount.contains("%")) {
                discount = discount.replace("%", "").trim();
                globalDiscountCalculated = ((Double.parseDouble(discount)) *
                        Double.parseDouble(grossAmountPerItem)) / 100;
                globalDiscountValue = decimalFormat.format(globalDiscountCalculated);

            } else {
                globalDiscountCalculated = (Double.parseDouble(discount) / Double.parseDouble(totalGrossAmount)) *
                        Double.parseDouble(grossAmountPerItem);
                globalDiscountValue = decimalFormat.format(formatDecimalNumber(globalDiscountCalculated));

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return globalDiscountValue;
    }

    public double formatDecimalNumber(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(java.math.RoundingMode.HALF_UP);
        return Double.parseDouble(decimalFormat.format(number));
    }

    public String formatDecimalNumberUptoTwo(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(number);
    }

    public String decimalNumberUptoTwo(Double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String x[] = number.toString().replace(".", " ").split(" ");
        String y = String.valueOf(Double.parseDouble(x[0] + "." + x[1].substring(0, 2)));
        return y;
    }

    public static double nextDoubleBetween2(double min, double max) {
        return (new Random().nextDouble() * (max - min)) + min;
    }

    public String getGST(String totalGrossAmount, String taxRate, String discount) {

        String gstValue = "";
        try {

            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            Double globalDiscountCalculated = (Double.parseDouble(taxRate) * (Double.parseDouble(totalGrossAmount) - Double.parseDouble(discount))) / 100;
            gstValue = decimalFormat.format(globalDiscountCalculated);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return gstValue;
    }

    public boolean getNumberPresentInArray(int arr[], int number) {
        boolean bPresent = false;
        for (int i = 0; i < arr.length; i++) {
            if (number == arr[i]) {
                bPresent = true;
                break;
            }
        }
        return bPresent;
    }

    public String getGstOnUI(WebElement element) {
        String gstValue = "";
        try {
            gstValue = Cls_Generic_Methods.getElementAttribute(element, "value");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return gstValue;

    }

    private static String getRandomNumber() {
        Random random = new Random();
        String id = String.format("%06d", random.nextInt(1000000));
        return id;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(enabled = true, description = "Desc")
    public void CreateIndentFormEnhancementVariant() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);

        String sRemarks = "Remarks";

        int indexOfOrderDate = -1;
        int indexOfItem = -1;

        boolean vendorFound = false;
        boolean bIndentOrderFound = false;
        boolean bStoreToShipOrderFound = false;
        boolean bStoreToBillOrderFound = false;

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectFacility("TST");
            CommonActions.selectStoreOnApp(sINDENT_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_addNewIndent),
                        "New Button clicked in Order: Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 10);
                String sSelectedButton = Cls_Generic_Methods.getTextInElement(oPage_Indent.button_variantOrRequisitionSelected);
                if (sSelectedButton.equalsIgnoreCase(sExpectedButtonToBeSelected)) {
                    boolean ItemList = oPage_Indent.list_ItemDescriptionsUnderIndentPurchase.isEmpty();
                    if (ItemList) {
                        m_assert.assertTrue("ItemList is empty");
                    } else {
                        m_assert.assertTrue(false, "ItemList is not empty");
                    }
                } else {
                    m_assert.assertTrue("Expected Button = <b>" + sExpectedButtonToBeSelected + "</b> is not selected");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_vendorField, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.select_vendorField),
                        "clicked on Store selection field");
                Cls_Generic_Methods.customWait();
                boolean storeFound = false;
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, sStoreToShipOrToBillOrder);
                Cls_Generic_Methods.customWait();
                for (WebElement eStoreName : oPage_Indent.list_stores) {
                    if (Cls_Generic_Methods.getTextInElement(eStoreName).contains(sStoreToShipOrToBillOrder)) {
                        Cls_Generic_Methods.clickElement(eStoreName);
                        storeFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(storeFound, "Store found to do indent purchase : <b> " + sINDENT_STORE + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_noteUnderIndentForPurchase, 10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_noteUnderIndentForPurchase, sNote),
                        "Note field value filled as =  <b>" + sNote + "</b> ");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.list_ItemDescriptionsUnderIndentPurchase.get(0)),
                        "Selected item for indent purchase");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder),
                        "Clicked on save button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_validationForQuantity, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.text_validationForQuantity),
                        "Validation for quantity field = <b>This field is required.</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_deleteItem, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_deleteItem),
                        "Delete item action working");

                m_assert.assertTrue(!Cls_Generic_Methods.isElementEnabled(oPage_Indent.button_saveIndentPurchaseOrder), "Save button should NOT be enabled");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_variantSearch, 10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_variantSearch, sItem),
                        "<b> " + sItem + "</b> Entered in search item field");
                Cls_Generic_Methods.customWait(2);
                oPage_Indent.input_variantSearch.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(3);
                String sSelectedItemName = " ";
                for (WebElement eItem : oPage_Indent.list_ItemDescriptionsUnderIndentPurchase) {
                    oPage_Indent = new Page_Indent(driver);
                    if (Cls_Generic_Methods.getTextInElement(eItem).contains(sItem)) {
                        sSelectedItemName = Cls_Generic_Methods.getTextInElement(eItem);
                        Cls_Generic_Methods.clickElement(eItem);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_indentOrderDate, 10);
                indentOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderTime, "value");
                indentOrderTime = indentOrderTime.replaceAll("\\s+", "");
                // indentOrderTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", indentOrderTime);
                indentOrderTime = indentOrderTime.replace("am", "AM").replace("pm", "PM");
                if (indentOrderTime.length() == 6) {
                    indentOrderTime = "0" + indentOrderTime;
                }
                m_assert.assertTrue("Indent order time:<b> " + indentOrderTime + "</b>");
                indentOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderDate, "value");
                indentOrderDate = indentOrderDate.replaceAll("/", "-");
                m_assert.assertTrue("Indent order date:<b> " + indentOrderDate + "</b>");
                mapTracker.put(key_CreatedAt_IndentOrderFromStore, indentOrderDate + "  |  " + indentOrderTime);
                Cls_Generic_Methods.customWait();

                for (WebElement eItem : oPage_Indent.list_itemNameSelectedToCreateIndentPurchase) {
                    if (Cls_Generic_Methods.getTextInElement(eItem).contains(sItem)) {
                        indexOfItem = oPage_Indent.list_itemNameSelectedToCreateIndentPurchase.indexOf(eItem);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_quantityField, sQTY);
                    }
                }

                Cls_Generic_Methods.customWait();
                if (Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.select_substoreFromIndentPurchasePage, sSubStore)) {
                    m_assert.assertTrue("Selected sub store = <b> " + sSubStore + "</b>");
                }

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

                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_requisitionIndent),
                        "select requisition Button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_requisitionIndent, 5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.select_requisitionItemForIndent),
                        "selected requisition Item");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_requisitionItemForIndent, 5);

//                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_RemarksforRequisitionItemForIndent, sRemarks));
//                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_RemarksforRequisitionItemForIndent, 10);
//
////                if (Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.select_substoreFromIndentPurchasePage, sSubStore)) {
//                    m_assert.assertTrue("Selected sub store = <b> " + sSubStore + "</b>");
//                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder),
                        " Indent order saved");
                Cls_Generic_Methods.customWait(5);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.select_indentItem)
                        ,"Selected Required Indent Item");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_indentItem, 10);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_editForIndentItem)
                            ,"click On edit button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_editForIndentItem, 10);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_updateChangesInIndent)
                            ,"click On Update Changes Button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_updateChangesInIndent, 10);


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
