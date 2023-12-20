package tests.sprint69.scmChanges;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Order.Page_Indent;
import pages.store.PharmacyStore.Order.Page_PurchaseOrder;
import pages.store.PharmacyStore.Transaction.Page_Purchase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MultipleBatchesGRNTest extends TestBase {
    public static String sINDENT_STORE = "Pharmacy automation- Pharmacy";
    public static Map<String, String> mapTracker = new HashMap<String, String>();
    public static String key_CreatedAt_IndentOrderFromStore = "key_CreatedAt_IndentOrderFromStore";

    String sExpectedButtonToBeSelected = "Variant";
    String sVendorName = "Supplier ABC";
    String sNote = "Remarks";
    String sItemOnUI = "";
    String sTotalStockAfterTxnOnUI = "";
    double dPaidQtyOnUI = 0.0;

    String sShipToBillToValue = "5d9b8b8ccd29ba0b04bfe731";

    String sSelectStoreValue = "62e001d437d13d1b9c9e5f4d";
    String sSelectedItemName = " ";
    String sItem = "Indent Item";
    String sQTY = "10";
    String indentOrderTime = "";
    String indentOrderDate = "";

    String OrderTime = "";
    String OrderDate = "";
    String sGRNDate = "";
    String sGRNTime = "";
    String sIndentType = "Normal";

    String sBillType = "Bill";
    String sBillNumber = CommonActions.getRandomString(4);
    String sBillDate = Cls_Generic_Methods.getTodayDate("dd/MM/yyyy");
    String sStoreToShipOrToBillOrder = "Pharmacy automation";
    String sRatePerUnit = "10.0";
    String sTaxPercentage = "12%";
    String sFreeQty = "5";
    String sDiscount = "5";
    String sDiscountType = "₹";
    String sPartialPaidQty = "5";
    String sMRP = "25";

    String sBatchNo = "Batch123";
    String sDiffBatch = "1234";
    String sSubStore = "Default";

    String sOtherCharges = "10";

    boolean bIndentOrderFound = false;
    double dTaxInAmount = 0.0;
    double dGrossAmount = 0.0;
    double dTotalTaxAmount = 0.0;
    double dNetAmount = 0.0;
    double dTotalNetAmount = 0.0;
    double dPartialPaidQty = 0.0;
    double dPartialFreeQty = 0.0;
    double dPartialDiscount = 0.0;
    double dPartialNetAmount = 0.0;
    String sTotalStockBeforeTxn = "";


    @Test(enabled = true, description = "Create Indent From Variant")
    public void createIndentFromVariant() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_Master oPage_Master = new Page_Master(driver);

        try {
            //Opening Store for Indent
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sINDENT_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();
            //Getting total stock of the item from Master
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 8);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, sItem);
            Cls_Generic_Methods.pressEnter();
            Cls_Generic_Methods.pressEnter();
            Cls_Generic_Methods.customWait(5);
            for (WebElement element : oPage_Master.list_itemDescriptionNameOnItemMaster) {
                String elementName = Cls_Generic_Methods.getTextInElement(element);
                if (elementName.equalsIgnoreCase(sItem)) {
                    Cls_Generic_Methods.clickElement(element);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.text_totalStock, 10);
                    sTotalStockBeforeTxn = Cls_Generic_Methods.getTextInElement(oPage_Master.text_totalStock);
                    m_assert.assertInfo("Total Stock before transaction: "+sTotalStockBeforeTxn);
                    break;
                }
            }

            // creating new Indent Order
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_addNewIndent),
                    "New Button clicked in Order: Indent");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 10);
            Cls_Generic_Methods.selectElementByValue(oPage_Indent.select_StoreInIndent, sSelectStoreValue);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 10);
            String sSelectedButton = Cls_Generic_Methods.getTextInElement(oPage_Indent.button_variantOrRequisitionSelected);
            if (sSelectedButton.equalsIgnoreCase(sExpectedButtonToBeSelected)) {
                boolean ItemList = oPage_Indent.input_itemForIndent.isEmpty();
                if (ItemList) {
                    m_assert.assertTrue("ItemList is empty");
                } else {
                    m_assert.assertTrue(false, "ItemList is not empty");
                }
            } else {
                m_assert.assertTrue("Expected Button = <b>" + sExpectedButtonToBeSelected + "</b> is not selected");
            }
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
            Cls_Generic_Methods.pressEnter();
            Cls_Generic_Methods.pressEnter();
            Cls_Generic_Methods.customWait(7);
            sSelectedItemName = " ";
            for (WebElement eItem : oPage_Indent.list_ItemDescriptionsUnderIndentPurchase) {
                oPage_Indent = new Page_Indent(driver);
                if (Cls_Generic_Methods.getTextInElement(eItem).equalsIgnoreCase(sItem)) {
                    sSelectedItemName = Cls_Generic_Methods.getTextInElement(eItem);
                    Cls_Generic_Methods.clickElement(eItem);
                    break;
                }
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_indentOrderDate, 10);
            indentOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderTime, "value");
            indentOrderTime = indentOrderTime.replaceAll(" ", "");
            m_assert.assertTrue("Indent order time:<b> " + indentOrderTime + "</b>");
            indentOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderDate, "value");
            indentOrderDate = indentOrderDate.replaceAll("/", "-");
            m_assert.assertTrue("Indent order date:<b> " + indentOrderDate + "</b>");
            if(indentOrderTime.length() == 6){
                indentOrderTime= "0"+indentOrderTime;
            }
            mapTracker.put(key_CreatedAt_IndentOrderFromStore, indentOrderDate + "  |  " + indentOrderTime);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Indent.list_itemNameSelectedToCreateIndentPurchase, 8);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_indentType, 8);
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.select_indentType, sIndentType), "Selected indent type as <b>" + sIndentType + "</b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_substoreFromIndentPurchasePage, 8);
           try {
               if (Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.select_substoreFromIndentPurchasePage, sSubStore)) {
                   m_assert.assertTrue("Selected sub store = <b> " + sSubStore + "</b>");
               }
           }catch (Exception e){
               e.printStackTrace();
           }
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_quantityField, sQTY);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder),
                    " Indent order saved");
            Cls_Generic_Methods.customWait(30);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Create PO Against Indent")
    public void createPOAAgainstIndent() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_PurchaseOrder oPage_PO = new Page_PurchaseOrder(driver);

        try {
            //Opening Store for Indent
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            //CommonActions.selectFacility("OPTHA1");
            CommonActions.selectStoreOnApp(sINDENT_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            //Opening Created Indent on Indent Order Page
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Indent.list_dateTimeOfIndentOrder, 10);
            for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate);
                if (dateTimeFull.contains(indentOrderDate)
                       ) {
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
                        "clicked on New Order button to create PO");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_POHeader, 8);
                String sItemNameForOrder = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_itemNameInPurchaseOrderPopUp);
                if (sItemNameForOrder.equalsIgnoreCase(sSelectedItemName)) {
                    m_assert.assertTrue("Validated item name while creating new purchase order = <b>" + sSelectedItemName + "</b> ");
                } else {
                    m_assert.assertTrue("displaying wrong Item name");
                }
                String sOrderedQty = Cls_Generic_Methods.getElementAttribute(oPage_Indent.list_paidQuantityListOfIndentOrder.get(0),"value");
                if (Double.parseDouble(sOrderedQty) == Double.parseDouble(sQTY)) {
                    m_assert.assertTrue("Quantity validated while creating new purchase order = <b>" + sQTY + "</b>");
                } else {
                    m_assert.assertTrue("displaying wrong Quantity");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_OrderNote, 10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_OrderNote, sNote),
                        "Order note field value filled while creating new purchase order =  <b>" + sNote + "</b> ");
                OrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderTime, "value");
                OrderTime = OrderTime.replaceAll(" ", "");
                m_assert.assertTrue(" order time:<b> " + OrderTime + "</b>");

                OrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderDate, "value");
                OrderDate = OrderDate.replaceAll("/", "-");
                m_assert.assertTrue(" order date:<b> " + OrderDate + "</b>");
                Cls_Generic_Methods.customWait(2);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_searchVendorAddress,sVendorName);
                Cls_Generic_Methods.customWait();
                String sSelectedVendor = Cls_Generic_Methods.getTextInElement(oPage_Indent.vendorAddressName);
                if (sSelectedVendor.contains(sVendorName)) {
                    Cls_Generic_Methods.clickElement(oPage_Indent.vendorAddressName);
                    m_assert.assertTrue("Selected Vendor:  <b>" + sVendorName + "</b>");
                } else {
                    m_assert.assertTrue("Required vendor <b>" + sVendorName + "</b> is NOT selected");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_RatePerUnit, 10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_RatePerUnit, sRatePerUnit),
                        "Rate per unit =  <b>" + sRatePerUnit + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_FreeQty, 10);
                Cls_Generic_Methods.clearValuesInElement(oPage_Indent.input_FreeQty);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_FreeQty, sFreeQty),
                        "Free qty =  " + sFreeQty + "");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_Indent.select_OtherChargesType, 1), "Selected other charges Type");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_OtherCharges, sOtherCharges), "Enter other charges amount as <b>" + sOtherCharges);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_Discount, sDiscount),
                        "Discount =  " + sDiscount);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_DiscountType, 10);
                Cls_Generic_Methods.selectElementByValue(oPage_Indent.select_DiscountType, sDiscountType);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder);
                Cls_Generic_Methods.customWait(30);
                //CommonActions.selectOptionFromLeftInInventoryStorePanel("Order","Purchase");
               // Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PO.list_purchaseOrdertransactions, 10);
                boolean purchaseOrderFound = false;
                // Validating PO calculations
                for (WebElement element : oPage_PO.list_purchaseOrdertransactions) {
                    String pODateAndTime = Cls_Generic_Methods.getTextInElement(element);
                    if (pODateAndTime.contains(OrderDate) && pODateAndTime.contains(OrderTime)) {
                        Cls_Generic_Methods.clickElement(element);
                        purchaseOrderFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(purchaseOrderFound, "Purchase Order found on PO transaction page");
                if (purchaseOrderFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PO.text_itemOnUI, 8);
                    sItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_PO.text_itemOnUI);
                    sItemOnUI = sItemOnUI.replace("-", "");
                    m_assert.assertTrue(sItem.equalsIgnoreCase(sItemOnUI), "Validated the item <b>" + sItemOnUI + "</b> on PO page");
                    dPaidQtyOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PO.text_PaidQty));
                    m_assert.assertTrue(Double.parseDouble(sQTY) == dPaidQtyOnUI, "Validate the Paid Quanity <b>" + dPaidQtyOnUI + "</b> on PO Page");
                    double dFreeQtyOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PO.text_FreeQty));
                    double dDiscountOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PO.text_Discount));
                    double dItemRateOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PO.text_itemRate));
                    double dNetAmountOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PO.text_NetAmountOnTable));
                    double dGrossAmtOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PO.text_totalGrossAmt));
                    double dTaxAmountOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PO.text_TotalTaxAmt));
                    double dOtherChargesOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PO.text_TotalOtherCharges));
                    double dTotalNetAmountOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PO.text_TotalNetAmount));
                    m_assert.assertTrue(Double.parseDouble(sFreeQty) == dFreeQtyOnUI, "Validated the Free qty " + dFreeQtyOnUI + " on PO RHS page ");
                    m_assert.assertTrue(Double.parseDouble(sDiscount) == dDiscountOnUI, "Validated the Discount " + dDiscountOnUI + " on PO RHS page ");
                    m_assert.assertTrue(Double.parseDouble(sRatePerUnit) == dItemRateOnUI, "Validated the Item Rate " + dItemRateOnUI + " on PO RHS page ");
                    dTaxInAmount = CommonActions.convertTaxToAmount(sTaxPercentage);
                    dGrossAmount = Double.parseDouble(sQTY) * Double.parseDouble(sRatePerUnit);
                    dTotalTaxAmount = (dGrossAmount - Double.parseDouble(sDiscount)) * dTaxInAmount;
                    dNetAmount = (dGrossAmount - Double.parseDouble(sDiscount)) + dTotalTaxAmount;
                    dTotalNetAmount = dNetAmount + Double.parseDouble(sOtherCharges);
                    m_assert.assertTrue(dNetAmount == dNetAmountOnUI, "Validated the net amount <b>" + dNetAmountOnUI + "</b> on PO RHS Page");
                    m_assert.assertTrue(dGrossAmount == dGrossAmtOnUI, "Validated the Gross Amount <b>" + dGrossAmtOnUI + " </b> on PO RHS Page");
                    m_assert.assertTrue(dTotalTaxAmount == dTaxAmountOnUI, "Validated the Tax Amount <b>" + dTaxAmountOnUI + " </b> on PO RHS Page");
                    m_assert.assertTrue(Double.parseDouble(sOtherCharges) == dOtherChargesOnUI, "Validated the Other Charges Amount <b>" + dOtherChargesOnUI + " </b> on PO RHS Page");
                    m_assert.assertTrue(dTotalNetAmount == dTotalNetAmountOnUI, "Validated the Total Net Amount <b>" + dTotalNetAmountOnUI + " </b> on PO RHS Page");
                    Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

                } else {
                    m_assert.assertTrue(purchaseOrderFound, "Purchase Order not found");
                }
            } else {
                m_assert.assertTrue(bIndentOrderFound, "Indent Order not found");
            }


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Create Multiple Batches GRN Against PO")
    public void createMultipleBatchesGRNAgainstPO() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        Page_PurchaseOrder oPage_PO = new Page_PurchaseOrder(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);


        try {
            //Opening Store for PO
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sINDENT_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();
            //Opening Created PO on Purchase Order Page
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PO.list_purchaseOrdertransactions, 10);
            boolean purchaseOrderFound = false;
            for (WebElement element : oPage_PO.list_purchaseOrdertransactions) {
                String pODateAndTime = Cls_Generic_Methods.getTextInElement(element);
                if (pODateAndTime.contains(OrderDate) && pODateAndTime.contains(OrderTime)) {
                    Cls_Generic_Methods.clickElement(element);
                    purchaseOrderFound = true;
                    break;
                }
            }
            m_assert.assertTrue(purchaseOrderFound, "Purchase Order found on PO transaction page");
            if (purchaseOrderFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PO.button_ApproveOrder, 8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PO.button_ApproveOrder), "Approved Purchase Order");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PO.button_Yes, 10);
                Cls_Generic_Methods.clickElement(oPage_PO.button_Yes);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PO.button_NewTransaction, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PO.button_NewTransaction), "Clicked New transaction button to create GRN");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 10);

                Cls_Generic_Methods.clickElement(oPage_Purchase.button_SaveChanges);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.label_billTypeRequired, 12);
                m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_Purchase.label_billTypeRequired, "class").contains("error"), "Bill Type Required Error Displayed");
                m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_Purchase.label_MRPRequired, "class").contains("error"), "MRP Required Error Displayed");
                m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_Purchase.label_batchNumberRequired, "class").contains("error"), "Batch Number Required Error Displayed");
                m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_Purchase.label_otherChargesRequired, "class").contains("error"), "Other Charges Required Error Displayed");
                sGRNTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                sGRNTime = sGRNTime.replaceAll(" ", "");
                m_assert.assertTrue(" GRN time:<b> " + sGRNTime + "</b>");
                sGRNDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                m_assert.assertTrue(" GRN date:<b> " + sGRNDate + "</b>");
                //Adding and Deleting Child Row for the same Item
                int itemRowCount = oPage_Purchase.table_itemsRowWise.size();
                m_assert.assertInfo("Row Count in the Table before adding child row is <b>" + itemRowCount);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_addChildRow.get(0)), "Added one child Row");
                Cls_Generic_Methods.customWait();
                itemRowCount = oPage_Purchase.table_itemsRowWise.size();
                m_assert.assertInfo("Row Count in the Table after adding child row is <b>" + itemRowCount);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_deleteChildRow), "Deleted child Row");
                Cls_Generic_Methods.customWait();
                itemRowCount = oPage_Purchase.table_itemsRowWise.size();
                m_assert.assertInfo("Row Count in the Table after deleting child row is <b>" + itemRowCount);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_addChildRow.get(0));
                Cls_Generic_Methods.customWait();
                itemRowCount = oPage_Purchase.table_itemsRowWise.size();
                for (int i = 0; i < itemRowCount; i++) {
                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_paidQty.get(i));
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(i), sMRP);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_paidQty.get(i), sPartialPaidQty);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(i), sBatchNo);
                }
                Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, sBillType);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, sBillNumber);
                Cls_Generic_Methods.sendKeysByJS(driver, oPage_Purchase.input_billDate, sBillDate);
                m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_Purchase.label_duplicateBatch, "class").contains("error"), "Duplicate Batch Number Error Displayed");
                Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.list_batchNumber.get(1));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(1), sDiffBatch), "Entered different batch number <b>" + sDiffBatch + " </b>for child Row");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_OtherCharges, sOtherCharges);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_TransactionNote, sNote);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Purchase.button_SaveChanges), "Saved GRN Transaction");
                Cls_Generic_Methods.customWait(30);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_GRNTransaction, 10);
                Date txnDate = new SimpleDateFormat("dd/MM/yyyy").parse(sGRNDate);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String sDate = df.format(txnDate);
                String sTime = sGRNTime.replace(" ", "");
                //Validating Purchase Transaction and Calculations
                boolean bPurchaseTransactionFound = false;
                for (WebElement element : oPage_Purchase.list_transactionPurchaseList) {
                    String sPurchaseDetails = Cls_Generic_Methods.getTextInElement(element);
                    if (sPurchaseDetails.contains(sDate) && sPurchaseDetails.contains(sTime)) {
                        Cls_Generic_Methods.clickElement(element);
                        bPurchaseTransactionFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(bPurchaseTransactionFound, "Required Purchase Transaction Found");
                if (bPurchaseTransactionFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.table_itemDescription, 10);
                    sItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_itemDescription);
                    sItemOnUI = sItemOnUI.replace("-", "");
                    m_assert.assertTrue(sItem.equalsIgnoreCase(sItemOnUI), "Validated the item <b>" + sItemOnUI + "</b> on purchase transaction page");
                    int rowCountOnTable = oPage_Purchase.list_RowsOnUITable.size();
                    for (int i = 0; i < rowCountOnTable; i++) {
                        dPartialPaidQty = dPartialPaidQty + Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Purchase.list_PaidQty.get(i)));
                        dPartialFreeQty = dPartialFreeQty + Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Purchase.list_FreeQty.get(i)));
                        dPartialDiscount = dPartialDiscount + Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Purchase.list_Discount.get(i)));
                        dPartialNetAmount = dPartialNetAmount + Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Purchase.list_NetAmount.get(i)));
                    }
                    m_assert.assertTrue(Double.parseDouble(sQTY) == dPartialPaidQty, "Validated paid quantity <b>" + dPartialPaidQty + " </b> in Purchase Transaction RHS Page");
                    m_assert.assertTrue(Double.parseDouble(sFreeQty) == dPartialFreeQty, "Validated Free quantity <b>" + dPartialFreeQty + " </b> in Purchase Transaction RHS Page");
                    m_assert.assertTrue(Double.parseDouble(sDiscount) == dPartialDiscount, "Validated Discount amount <b>" + dPartialDiscount + " </b> in Purchase Transaction RHS Page");
                    m_assert.assertTrue(dNetAmount == dPartialNetAmount, "Validated Net Amount <b>" + dPartialNetAmount + " </b> in Purchase Transaction RHS Page");
                    double dGrossAmtOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_totalGrossAmt));
                    double dTaxAmountOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_TotalTaxAmt));
                    double dOtherChargesOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_TotalOtherCharges));
                    double dTotalNetAmountOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_TotalNetAmount));
                    m_assert.assertTrue(dGrossAmount == dGrossAmtOnUI, "Validated the Gross Amount <b>" + dGrossAmtOnUI + " </b> on Purchase Transaction RHS Page");
                    m_assert.assertTrue(dTotalTaxAmount == dTaxAmountOnUI, "Validated the Total Tax Amount <b>" + dTaxAmountOnUI + " </b> on Purchase Transaction RHS Page");
                    m_assert.assertTrue(Double.parseDouble(sOtherCharges) == dOtherChargesOnUI, "Validated the Other Charges Amount <b>" + dOtherChargesOnUI + " </b> on Purchase Transaction RHS Page");
                    m_assert.assertTrue(dTotalNetAmount == dTotalNetAmountOnUI, "Validated the Total Net Amount <b>" + dTotalNetAmountOnUI + " </b> on Purchase Transaction RHS Page");
                    //Opening and Validating the transaction in Master Page
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 8);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction), "Approved Purchase Transaction");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_Yes, 10);
                    Cls_Generic_Methods.pressEnter();
                    Cls_Generic_Methods.customWait(5);
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 8);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, sItem);
                    Cls_Generic_Methods.pressEnter();
                    Cls_Generic_Methods.pressEnter();
                    Cls_Generic_Methods.customWait(7);
                    m_assert.assertInfo("Total stock of the variant <b> " + sItem + "</b> before txn is <b>" + sTotalStockBeforeTxn + " </b>");
                    for (WebElement element : oPage_Master.list_itemDescriptionNameOnItemMaster) {
                        String elementName = Cls_Generic_Methods.getTextInElement(element);
                        if (elementName.equalsIgnoreCase(sItem)) {
                            Cls_Generic_Methods.clickElement(element);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.text_totalStock, 10);
                            sTotalStockAfterTxnOnUI = Cls_Generic_Methods.getTextInElement(oPage_Master.text_totalStock);
                            break;
                        }
                    }
                    double dTotalStockAfterTxn = Double.parseDouble(sTotalStockBeforeTxn) + Double.parseDouble(sQTY) + Double.parseDouble(sFreeQty);
                    m_assert.assertTrue(dTotalStockAfterTxn == Double.parseDouble(sTotalStockAfterTxnOnUI),
                            "Validated the total stock of variant <b>" + sItem + " </b>after txn is <b>" + sTotalStockAfterTxnOnUI);
                    Cls_Generic_Methods.customWait();


                } else {
                    m_assert.assertTrue("Purchase Transaction Not Found");
                }


                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            } else {
                m_assert.assertTrue(purchaseOrderFound, "Purchase Order not found");
            }


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

}



