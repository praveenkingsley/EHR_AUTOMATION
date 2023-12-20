package tests.inventoryStores.pharmacyStore.Order;

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
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_TermsAndConditions;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_VendorMaster;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Order.Page_PurchaseOrder;
import pages.store.PharmacyStore.Transaction.Page_Purchase;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class PurchaseOrderTest extends TestBase {

    public String sStore = "Pharmacy automation- Pharmacy";
    String vendorName = "Supplier ABC";
    String sPurchaseOrderNo;
    String sPurchaseGrnNo;
    String itemName1 = "Midodrine";
    String itemName2 = "Ciplox Eye";
    String item1Discount = "21 %";
    String item2Discount = "12 ₹";
    public String sStorePO = "OpticalStore- Optical";
    public String sPoType = "Normal";
    public String orderTime = "";
    public String orderDate = "";

    public Map<String, String> mapTracker = new HashMap<>();
    public String key_CreatedAt_PurchaseOrder = "key_CreatedAt_PurchaseOrder";
    String storeDefaultShippingAddress = "";
    String storeDefaultBillingAddress = "";
    String storeGSTno = "";
    String orderNote = "AUTO-TEST";
    String storeEntityGroup = "";
    String vendor_address = "";
    String vendorGSTno = "";
    String vendorCreditDays = "";
    String vendorPOExpiry = "";
    String expectedPOExpiryDay = "";
    String deliveryTerms = "";
    String paymentTerms = "";

    HashMap<String, String> itemDetails1;
    HashMap<String, String> itemDetails2;
    Page_PurchaseOrder oPage_PurchaseOrder;
    Page_Purchase oPage_Purchase;
    Page_CommonElements oPage_CommonElements;
    Page_Master oPage_Master;

    //Calculation
    double otherCharge = 50;
    double otherChargePercentage = 100;
    double totalGrossAmount = 0;
    double totalDiscount = 0;
    double amountPaid = 0;
    double totalGST5Amount = 0;
    double totalGST28Amount = 0;
    double totalNetAmount = 0;

    @Test(enabled = true, description = "Create Purchase Order", priority = 0)
    public void validatePurchaseOrderCreation() {
        oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            getStoreAndVendorDetails(sStore);
            Cls_Generic_Methods.driverRefresh();
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 8);

                //Getting Item Details
                itemDetails1 = getStockableItemDetails(itemName1);
                itemDetails2 = getStockableItemDetails(itemName2);


                //Create Purchase Order
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 3);

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_newOrder), "Clicked <b>New</b> Button in  Purchase Order");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_headerCreatePurchaseOrder, 10), "Validated Create Purchase Order Page displayed");

                String currentTimeAndDate = getCurrentDateTime();
                orderTime = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_purchaseOrderTime, "value");
                orderTime = CommonActions.getRequiredFormattedDateTime("h:mm a", "hh:mm a", orderTime);
                m_assert.assertTrue("Purchase Order time:<b> " + orderTime + "</b>");

                orderDate = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_purchaseOrderDate, "value");
                m_assert.assertTrue("Purchase Order date:<b> " + orderDate + "</b>");
                mapTracker.put(key_CreatedAt_PurchaseOrder, CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd-MM-yyyy", orderDate) + "  |  " + orderTime);

                m_assert.assertTrue(compareDateTimesWithTolerance(mapTracker.get(key_CreatedAt_PurchaseOrder), currentTimeAndDate, 1), "Validated Purchase Order Date and Time");

                //select vendor, store, type
                m_assert.assertTrue(selectByOptions(oPage_PurchaseOrder.dropdown_store, sStore.split("-")[0]), "Store selected : <b>" + sStore.split("-")[0] + " </b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_Vendor_search, 4);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, (vendorName + " " + vendor_address));
                Cls_Generic_Methods.customWait();

                Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, "" + Keys.DOWN + Keys.ENTER);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, "" + Keys.DOWN + Keys.ENTER),
                        "Vendor selected : <b>" + vendorName + "</b>");
                Cls_Generic_Methods.customWait();

                m_assert.assertTrue(selectByOptions(oPage_PurchaseOrder.dropdown_poType, sPoType), "PO Type selected : <b>" + sPoType + " </b>");
                Cls_Generic_Methods.customWait();

                //Select Item
                selectItemAndAddLot(itemDetails1, item1Discount);
                selectItemAndAddLot(itemDetails2, item2Discount);

                //Validating UI Header Elements
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_orderNote, 7);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_orderNote, orderNote), "Entered " + orderNote + " in Order Note");
                String storeGstNoInPo = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_storeGstNo, "value");
                m_assert.assertTrue(storeGstNoInPo.equals(storeGSTno), "Validated displayed Store GST No : <b>" + storeGstNoInPo + "</b>");

                //PO EXPIRY
                LocalDate date = LocalDate.now().plusDays(Integer.parseInt(vendorPOExpiry));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                expectedPOExpiryDay = date.format(formatter);

                String poExpiryDateInPo = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_expiryDatePO, "value");
                m_assert.assertTrue(poExpiryDateInPo.equals(expectedPOExpiryDay), "Validated displayed PO expiry Date : <b>" + poExpiryDateInPo + "</b>");

                //CREDIT DAYS
                String creditDaysInPo = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_creditDays, "value");
                m_assert.assertTrue(creditDaysInPo.equals(vendorCreditDays), "Validated displayed Vendor Credit Days : <b>" + creditDaysInPo + "</b>");

                //ADDRESS
                String selectedBillAddressInPo = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseOrder.select_billToStore);
                m_assert.assertTrue(selectedBillAddressInPo.contains(storeDefaultBillingAddress), "Validated displayed Bill To Address : <b>" + selectedBillAddressInPo + "</b>");

                String selectedShipAddressInPo = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseOrder.select_shipToStore);
                m_assert.assertTrue(selectedShipAddressInPo.contains(storeDefaultShippingAddress), "Validated displayed Ship To Address : <b>" + selectedShipAddressInPo + "</b>");

                //Create PO Table

                validateItemDetailsInCreatePO(itemDetails1);
                validateItemDetailsInCreatePO(itemDetails2);

                //Other Charges Validation

                m_assert.assertInfo(selectByOptions(oPage_PurchaseOrder.select_otherCharges_createPo.get(0), "Fitting Charges"), "Selected <b>Fitting Charges</b> on Other Charges");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_otherCharges_createPo.get(0), String.valueOf(otherCharge)), "Entered <b>" + otherCharge + "</b> on Other Charges");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_otherChargesPercent_createPo, String.valueOf(otherChargePercentage)), "Entered <b>" + otherChargePercentage + "</b> on Other Charges Percentage");
                Cls_Generic_Methods.customWait();

                double expectedOtherCharges = otherCharge + ((otherCharge * otherChargePercentage) / 100);
                double actualOtherCharges = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.text_displayOtherCharges_createPo, "value"));
                m_assert.assertTrue(actualOtherCharges == expectedOtherCharges, "Validated Displayed Other Charges : <b> " + actualOtherCharges + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_addOtherCharges_createPo), "Clicked Add new Other Charges button");
                Cls_Generic_Methods.customWait();
                int noOfOtherCharges = oPage_PurchaseOrder.input_otherCharges_createPo.size();
                m_assert.assertTrue(noOfOtherCharges == 2, "Validated --> Add Other Charges");
                m_assert.assertInfo(selectByOptions(oPage_PurchaseOrder.select_otherCharges_createPo.get(1), "Item_other"), "Selected <b>Item_other</b> on Newly added Other Charges");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_otherCharges_createPo.get(1), String.valueOf(otherCharge)), "Entered <b>" + otherCharge + "</b> on Newly added Other Charges");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_minusOtherCharges_createPo.get(1)), "Clicked <b>-</b> in other charge");
                Cls_Generic_Methods.customWait();
                String actualTotalOtherCharges = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.text_finalOtherCharges_createPo, "value").trim();
                double expectedTotalOtherCharges = expectedOtherCharges - otherCharge;
                m_assert.assertTrue(Double.parseDouble(actualTotalOtherCharges) == expectedTotalOtherCharges, "Validated Total Other Charges : <b>" + expectedTotalOtherCharges + "</b>");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_removeOtherCharges_createPo.get(1)), "Clicked Remove Other Charges button");
                Cls_Generic_Methods.customWait();
                int noOfOtherChargesAfterDelete = oPage_PurchaseOrder.input_otherCharges_createPo.size();
                m_assert.assertTrue(noOfOtherChargesAfterDelete == 1, "Validated --> Delete Other Charges");
                Cls_Generic_Methods.customWait();
                actualTotalOtherCharges = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.text_finalOtherCharges_createPo, "value").trim();
                expectedTotalOtherCharges = expectedTotalOtherCharges + otherCharge;
                m_assert.assertTrue(Double.parseDouble(actualTotalOtherCharges) == expectedTotalOtherCharges, "Validated Total Other Charges after deleting other charge : <b>" + expectedTotalOtherCharges + "</b>");

                otherCharge = expectedOtherCharges;
                totalNetAmount = totalNetAmount + otherCharge;

                //Validating Total Calculations

                m_assert.assertTrue(getStatusOfUi(oPage_PurchaseOrder.text_grossAmountOnUIPO, String.valueOf(formatDecimalNumber(totalGrossAmount))), "Gross Amount Validated:<b>  " + formatDecimalNumber(totalGrossAmount) + "</b>");
                m_assert.assertTrue(getStatusOfUi(oPage_PurchaseOrder.text_discountOnUI, String.valueOf(formatDecimalNumber(totalDiscount))), "Discount Amount Validated:<b> " + formatDecimalNumber(totalDiscount) + "</b>");
                if (totalGST5Amount > 0) {
                    m_assert.assertTrue(getStatusOfUi(oPage_PurchaseOrder.text_gst5AmountOnUI, String.valueOf(formatDecimalNumber(totalGST5Amount))), "GST5 Amount Validated: <b> " + formatDecimalNumber(totalGST5Amount) + "</b>");
                }
                if (totalGST28Amount > 0) {
                    m_assert.assertTrue(getStatusOfUi(oPage_PurchaseOrder.text_gst28AmountOnUI, String.valueOf(formatDecimalNumber(totalGST28Amount))), "GST28 Amount Validated: <b> " + formatDecimalNumber(totalGST28Amount) + "</b>");
                }

                m_assert.assertTrue(getStatusOfUi(oPage_PurchaseOrder.text_netAmountOnUI, String.valueOf(formatDecimalNumber(totalNetAmount))), "Net Amount Validated:<b> " + formatDecimalNumber(totalNetAmount) + "</b>");

                //Terms and Conditions
                String optionValues = "";
                for (WebElement option : oPage_PurchaseOrder.select_paymentTerms.findElements(By.xpath("./option"))) {
                    String value = Cls_Generic_Methods.getTextInElement(option);
                    if (!value.isEmpty()) {
                        if (optionValues.isEmpty()) {
                            optionValues = value;
                        } else {
                            optionValues = optionValues.concat("|").concat(value);
                        }
                    }
                }
                m_assert.assertTrue(optionValues.equals(paymentTerms), "Validated Payment Terms -> Displayed Terms : <b>" + optionValues.replaceAll("\\|", ",") + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_PurchaseOrder.select_paymentTerms, optionValues.split("\\|")[0]), "Selected Payment Terms as : <b>" + optionValues.split("\\|")[0] + "</b>");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_deliveryTerms), "Clicked <b>Delivery Terms</b> button");

                optionValues = "";
                for (WebElement option : oPage_PurchaseOrder.select_deliveryTerms.findElements(By.xpath("./option"))) {
                    String value = Cls_Generic_Methods.getTextInElement(option);
                    if (!value.isEmpty()) {
                        if (optionValues.isEmpty()) {
                            optionValues = value;
                        } else {
                            optionValues = optionValues.concat("|").concat(value);
                        }
                    }
                }
                m_assert.assertTrue(optionValues.equals(deliveryTerms), "Validated Delivery Terms -> Displayed Terms : <b>" + optionValues.replaceAll("\\|", ",") + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_PurchaseOrder.select_deliveryTerms, optionValues.split("\\|")[0]), "Selected Delivery Terms as : <b>" + optionValues.split("\\|")[0] + "</b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_saveOrder), "Purchase Order Saved");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 10);
                Cls_Generic_Methods.customWait();
                boolean selectPO = selectPurchaseOrder();

                if (selectPO) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_purchaseOrderId, 10);
                    String poCreatedAt = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_poCreatedAt);
                    m_assert.assertTrue(compareDateTimesWithTolerance(poCreatedAt, mapTracker.get(key_CreatedAt_PurchaseOrder), 1), "Validated PO Created At : " + poCreatedAt);
                }
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }

    }

    @Test(enabled = true, description = "Validate Purchase Order RHS", dependsOnMethods = "validatePurchaseOrderCreation", priority = 1)
    public void validatePurchaseOrderRHS() throws Exception {
        oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        oPage_CommonElements = new Page_CommonElements(driver);


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.driverRefresh();
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 10);

            selectPurchaseOrder(sPurchaseOrderNo);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_purchaseOrderId, 10);

            m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_purchaseOrderId).equals(sPurchaseOrderNo), "Validated Purchase Order RHS -> PO No :<b> " + sPurchaseOrderNo + "</b>");
            String poCreatedBy = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_poCreatedByUser);
            String poCreatedAt = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_poCreatedAt);
            m_assert.assertTrue(compareDateTimesWithTolerance(mapTracker.get(key_CreatedAt_PurchaseOrder), poCreatedAt, 0), "Purchase Order Created By <b>" + poCreatedBy + "</b> at -> <b>" + poCreatedAt + "</b>");

            String rhsStatus = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoStatus);
            String rhsPOType = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoType);
            String rhsPOExpiry = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoExpiry);
            String rhsVendor = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoVendor);
            String rhsVendorGSTNo = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoVendorGSTNo);
            String rhsVendorCreditDays = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoVendorCreditDays);
            String rhsStoreShipTo = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoStoreShipTo);
            String rhsStoreBillTo = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoStoreBillTo);
            String rhsStoreGSTNo = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoStoreGSTNo);
            String rhsStoreEntity = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoEntityGroup);

            m_assert.assertTrue(rhsStatus.equalsIgnoreCase("pending"), "Validated Purchase Order RHS -> PO Status :<b> " + rhsStatus + "</b>");
            m_assert.assertTrue(rhsPOType.equalsIgnoreCase(sPoType), "Validated Purchase Order RHS -> PO Type :<b> " + rhsPOType + "</b>");
            m_assert.assertTrue(rhsPOExpiry.equals(expectedPOExpiryDay), "Validated Purchase Order RHS -> PO Type :<b> " + rhsPOExpiry + "</b>");
            m_assert.assertTrue(rhsVendor.contains(vendorName), "Validated Purchase Order RHS -> Vendor Name :<b> " + vendorName + "</b>");
            m_assert.assertTrue(rhsVendor.contains(vendor_address), "Validated Purchase Order RHS -> Vendor Address :<b> " + vendor_address + "</b>");
            m_assert.assertTrue(rhsVendorGSTNo.equals(vendorGSTno), "Validated Purchase Order RHS -> Vendor GST No :<b> " + rhsVendorGSTNo + "</b>");
            m_assert.assertTrue(rhsVendorCreditDays.equals(vendorCreditDays), "Validated Purchase Order RHS -> Vendor Credit Days :<b> " + rhsVendorCreditDays + "</b>");
            m_assert.assertTrue(rhsStoreShipTo.contains(storeDefaultShippingAddress), "Validated Purchase Order RHS -> Ship To Address :<b> " + rhsStoreShipTo + "</b>");
            m_assert.assertTrue(rhsStoreBillTo.contains(storeDefaultBillingAddress), "Validated Purchase Order RHS -> Bill To Address :<b> " + rhsStoreBillTo + "</b>");
            m_assert.assertTrue(rhsStoreGSTNo.equals(storeGSTno), "Validated Purchase Order RHS -> Store GST No :<b> " + rhsStoreGSTNo + "</b>");
            m_assert.assertTrue(storeEntityGroup.contains(rhsStoreEntity), "Validated Purchase Order RHS -> Store Entity Group :<b> " + storeEntityGroup + "</b>");

            //RHS TABLE VALIDATION
            validatePoRHSItemDetails(itemDetails1);
            validatePoRHSItemDetails(itemDetails2);

            //FOOTER RHS
            double dGrossAmtOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_totalGrossAmt));
            double dOtherChargesOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_TotalOtherCharges));
            double dTotalNetAmountOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_TotalNetAmount));

            m_assert.assertTrue(formatDecimalNumber(totalGrossAmount) == dGrossAmtOnUI, "Validated PO -> Gross Amount <b>" + dGrossAmtOnUI + " </b>");

            if (totalGST5Amount > 0) {
                double dTax5AmountOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_totalGST5TaxAmt));
                m_assert.assertTrue(formatDecimalNumber(totalGST5Amount) == dTax5AmountOnUI, "Validated PO -> GST5 Tax Amount <b>" + dTax5AmountOnUI + " </b>");
            }
            if (totalGST5Amount > 0) {
               // double dTax28AmountOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_totalGST28TaxAmt));
                //m_assert.assertTrue(formatDecimalNumber(totalGST28Amount) == dTax28AmountOnUI, "Validated PO -> GST28 Tax Amount <b>" + dTax28AmountOnUI + " </b>");
            }

            m_assert.assertTrue(otherCharge == dOtherChargesOnUI, "Validated PO -> Other Charges Amount <b>" + dOtherChargesOnUI + " </b>");
            m_assert.assertTrue(formatDecimalNumber(totalNetAmount) == dTotalNetAmountOnUI, "Validated PO -> Total Net Amount <b>" + dTotalNetAmountOnUI + " </b>");
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        }
    }

    @Test(enabled = true, description = "Validate Purchase Order Edit Functionality", dependsOnMethods = "validatePurchaseOrderCreation", priority = 2)
    public void validatePurchaseOrderEditFunctionality() {
        Page_PurchaseOrder oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        mapTracker = new HashMap<>();

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 3);
                selectPurchaseOrder(sPurchaseOrderNo);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_editPO, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_editPO), "Clicked <b>Edit</b> Purchase Order");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_orderNote, 10);

                String currentTimeAndDate = getCurrentDateTime();
                orderTime = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_purchaseOrderTime, "value");
                orderTime = CommonActions.getRequiredFormattedDateTime("h:mm a", "hh:mm a", orderTime);

                orderDate = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_purchaseOrderDate, "value");
                mapTracker.put(key_CreatedAt_PurchaseOrder, CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd-MM-yyyy", orderDate) + "  |  " + orderTime);
                m_assert.assertTrue(compareDateTimesWithTolerance(mapTracker.get(key_CreatedAt_PurchaseOrder), currentTimeAndDate, 1), "Validated Purchase Order Edit Date and Time");

                String editOrderNote = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_orderNote, "value");
                String editOrderType = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseOrder.dropdown_poType);
                String editStore = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseOrder.dropdown_store);
                String editStoreGSTNo = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_storeGstNo, "value");
                String editVendor = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_Vendor_search, "value");
                String editPOExpiry = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_expiryDatePO, "value");
                String editCreditDays = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_creditDays, "value");
                String editBillTo = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseOrder.select_billToStore);
                String editShipTo = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseOrder.select_shipToStore);

                m_assert.assertTrue(editOrderNote.equals(orderNote), "Validated Edit -> Order Note = " + editOrderNote);
                m_assert.assertTrue(editOrderType.equals(sPoType), "Validated Edit -> PO Type = " + editOrderType);
                m_assert.assertTrue(sStore.contains(editStore), "Validated Edit -> PO Store = " + editStore);
                m_assert.assertTrue(editStoreGSTNo.equals(storeGSTno), "Validated Edit -> PO GST No = " + editStoreGSTNo);
                m_assert.assertTrue(editVendor.contains(vendorName), "Validated Edit -> PO Vendor = " + editVendor);
                m_assert.assertTrue(editPOExpiry.equals(expectedPOExpiryDay), "Validated Edit -> PO Expiry Date = " + editPOExpiry);
                m_assert.assertTrue(editCreditDays.equals(vendorCreditDays), "Validated Edit -> PO Vendor Credit Days = " + editCreditDays);
                m_assert.assertTrue(editShipTo.contains(storeDefaultShippingAddress), "Validated Edit -> PO Store Ship To = " + editShipTo);
                m_assert.assertTrue(editBillTo.contains(storeDefaultBillingAddress), "Validated Edit -> PO Store Bill To = " + editBillTo);

                //Validate Selected Item

                validateItemDetailsInCreatePO(itemDetails1);
                validateItemDetailsInCreatePO(itemDetails2);


                m_assert.assertTrue(getStatusOfUi(oPage_PurchaseOrder.text_grossAmountOnUIPO, String.valueOf(formatDecimalNumber(totalGrossAmount))), "Validate PO Edit -> Gross Amount :<b>  " + formatDecimalNumber(totalGrossAmount) + "</b>");
                m_assert.assertTrue(getStatusOfUi(oPage_PurchaseOrder.text_discountOnUI, String.valueOf(formatDecimalNumber(totalDiscount))), "Validate PO Edit -> Discount Amount :<b> " + formatDecimalNumber(totalDiscount) + "</b>");
                m_assert.assertTrue(getStatusOfUi(oPage_PurchaseOrder.text_netAmountOnUI, String.valueOf(formatDecimalNumber(totalNetAmount))), "Validate PO Edit -> Net Amount Validated:<b> " + formatDecimalNumber(totalNetAmount) + "</b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_saveOrder), "Purchase Order Saved");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 10);
                Cls_Generic_Methods.customWait();
                boolean selectPO = selectPurchaseOrder(sPurchaseOrderNo);

                if (selectPO) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_purchaseOrderId, 10);
                    String poCreatedAt = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_poCreatedAt);
                    m_assert.assertTrue(compareDateTimesWithTolerance(poCreatedAt, mapTracker.get(key_CreatedAt_PurchaseOrder), 1), "Validated PO Updated At : " + poCreatedAt);
                }


                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        }
    }

    @Test(enabled = true, description = "Validate Created Purchase Order In Purchase Transaction", dependsOnMethods = "validatePurchaseOrderCreation", priority = 3)
    public void validatePartiallyCompletedNewTransaction() {
        oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Purchase = new Page_Purchase(driver);


        String billType = "Bill";
        String billNumber = "PO_BILL_NO_" + getRandomNumber();
        //   String
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 3);
            selectPurchaseOrder(sPurchaseOrderNo);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_approveOrder, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_approveOrder), "Clicked <b>Approve</b> button");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_Yes, 10);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_PurchaseOrder.button_Yes);
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.label_approved, 5), "Validated Purchase Order Approved Notify Msg displayed");
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 3);
            selectPurchaseOrder(sPurchaseOrderNo);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_rhsPoStatus, 10);
            String poStatus = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoStatus);
            m_assert.assertTrue(poStatus.equals("Approved"), "Validated Purchase Order : " + sPurchaseOrderNo + " is Approved");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_NewTransaction, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_NewTransaction), "Clicked <b>New Transaction</b> button");
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 10), "Goods Receive Note Page is Displayed");

            //TIME AND DATE
            String grnDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
            String grnTime = CommonActions.getRequiredFormattedDateTime("h:mm a", "hh:mm a", Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value"));

            m_assert.assertTrue(compareDateTimesWithTolerance(grnDate + "|" + grnTime, getCurrentDateTime(), 1), "Validated GRN Date -> " + grnDate + " and Time -> " + grnTime);

            //Validate PO Link
            String poNumberInGrn = Cls_Generic_Methods.getTextInElement(oPage_Purchase.link_showPODetails);
            m_assert.assertTrue(poNumberInGrn.equals(sPurchaseOrderNo), "Validated Displayed Purchase Order No : " + poNumberInGrn);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Purchase.link_showPODetails), "Clicked Purchase Order Link");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_poLinkTableRowPurchaseOrder, 10);
            validatePOLinkItemDetails(itemDetails1);
            validatePOLinkItemDetails(itemDetails2);

            //FOOTER
            double dGrossAmtOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_poLinkTotalGrossAmt));
            double dOtherChargesOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_poLinkTotalOtherCharges));
            double dTotalNetAmountOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_poLinkTotalNetAmount));

            m_assert.assertTrue(formatDecimalNumber(totalGrossAmount) == dGrossAmtOnUI, "Validated PO -> Gross Amount <b>" + dGrossAmtOnUI + " </b>");

            if (totalGST5Amount > 0) {
                double dTax5AmountOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_poLinkTotalGST5TaxAmt));
                m_assert.assertTrue(formatDecimalNumber(totalGST5Amount) == dTax5AmountOnUI, "Validated PO -> GST5 Tax Amount <b>" + dTax5AmountOnUI + " </b>");
            }
            if (totalGST5Amount > 0) {
              //  double dTax28AmountOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_poLinkTotalGST28TaxAmt));
               // m_assert.assertTrue(formatDecimalNumber(totalGST28Amount) == dTax28AmountOnUI, "Validated PO -> GST28 Tax Amount <b>" + dTax28AmountOnUI + " </b>");
            }

            m_assert.assertTrue(otherCharge == dOtherChargesOnUI, "Validated PO -> Other Charges Amount <b>" + dOtherChargesOnUI + " </b>");
            m_assert.assertTrue(formatDecimalNumber(totalNetAmount) == dTotalNetAmountOnUI, "Validated PO -> Total Net Amount <b>" + dTotalNetAmountOnUI + " </b>");
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_closePOLink);

            //HEADER GRN
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, orderNote), "Entered " + orderNote + " in Transaction Note");
            String vendorInGrn = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_vendorPOGrn, "value");
            m_assert.assertTrue(vendorInGrn.contains(vendorName), "Validated Displayed vendor in GRN : " + vendorInGrn);

            Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType), "Selected Bill Type as <b>" + billType + "</b>");

            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber), "Entered<b> " + billNumber + "</b> in Bill Number");
            Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
            Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate);
            String billDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_billDate, "value");
            m_assert.assertInfo("Selected Bill Date as " + billDate);
            Cls_Generic_Methods.customWait(3);

            validateCreateGRNItemDetails(itemDetails1, item1Discount, true);
            validateCreateGRNItemDetails(itemDetails2, item2Discount, false);

            //Calculation

            String grossAmountInGRN = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.text_totalGrossGRNPo, "value");
            String discountAmountInGRN = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.text_totalDiscountGRNPo, "value");
            String netAmount = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.text_totalNetAmountGRNPo, "value");

            m_assert.assertTrue(CommonActions.convertStringToDouble(grossAmountInGRN) == formatDecimalNumber(totalGrossAmount), "Validated GRN -> Total Gross Amount : <b>" + grossAmountInGRN + "</b>");
            m_assert.assertTrue(CommonActions.convertStringToDouble(discountAmountInGRN) == formatDecimalNumber(totalDiscount), "Validated GRN -> Total Discount Amount : <b>" + discountAmountInGRN + "</b>");
            m_assert.assertTrue(CommonActions.convertStringToDouble(netAmount) == formatDecimalNumber(totalNetAmount), "Validated GRN -> Total Net Amount : <b>" + netAmount + "</b>");

            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.dropdown_subStore),
                    "Sub Store dropdown clicked");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_PurchaseOrder.dropdown_subStore, "Default"),
                    "Sub Store selected: Default"  );
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.dropdown_subStore2),
                    "Sub Store dropdown clicked");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_PurchaseOrder.dropdown_subStore2, "Default"),
                    "Sub Store selected: Default"  );


            String displayedOtherCharge = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_pendingOtherCharges);
            m_assert.assertTrue(CommonActions.convertStringToDouble(displayedOtherCharge) == otherCharge, "Validated Displayed Pending amount to be paid : " + displayedOtherCharge);
            Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_vendorOtherChargesToBePaid);
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_vendorOtherChargesToBePaid, String.valueOf(otherCharge)), "Entering <b>" + otherCharge + "</b> in Pending amount to be paid ");

            Cls_Generic_Methods.customWait();
            String otherChargesInGRN = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.text_totalOtherChargesGRNPo, "value");
            m_assert.assertTrue(CommonActions.convertStringToDouble(otherChargesInGRN) == otherCharge, "Validated GRN -> Total Other Charges after paying vendor charges : <b>" + otherChargesInGRN + "</b>");
            Cls_Generic_Methods.customWait(3);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Purchase.button_SaveChanges), "Clicked Save Changes");
            Cls_Generic_Methods.customWait(5);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 10);
            List<String> purchaseTransactionHeaderList = new ArrayList<>();


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
                        m_assert.assertInfo("Purchase Grn created and approved");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_assignBarcodePurchaseTransaction, 15);
                        sPurchaseGrnNo = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_transactionID);
                        String grnApprovedAt = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_grnApprovedAt);
                        m_assert.assertInfo("Purchase Grn no =<b>" + sPurchaseGrnNo + "</b>");

                        validateGrnRHSItemDetails(itemDetails1);
                        validateGrnRHSItemDetails(itemDetails2);

                        //FOOTER RHS
                        dGrossAmtOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_totalGrossAmt));
                        dOtherChargesOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_TotalOtherCharges));
                        dTotalNetAmountOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_TotalNetAmount));
                        double dDiscountOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_TotalDiscount));
                       // double dTax28AmountOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_totalGST28TaxAmt));
                        double dTax5AmountOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_totalGST5TaxAmt));
                        double dAmountRemainingOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_grnRhsAmountRemaining));
                        double dAmountPaidOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_grnRhsAmountPaid));


                        m_assert.assertTrue(formatDecimalNumber(totalGrossAmount) == dGrossAmtOnUI, "Validated Created GRN -> Gross Amount <b>" + dGrossAmtOnUI + " </b>");
                        m_assert.assertTrue(formatDecimalNumber(totalDiscount) == dDiscountOnUI, "Validated Created GRN -> Total Discount Amount <b>" + dDiscountOnUI + " </b>");
                        m_assert.assertTrue(formatDecimalNumber(totalGST5Amount) == dTax5AmountOnUI, "Validated Created GRN -> GST5 Tax Amount <b>" + dTax5AmountOnUI + " </b>");
                       // m_assert.assertTrue(formatDecimalNumber(totalGST28Amount) == dTax28AmountOnUI, "Validated Created GRN -> GST28 Tax Amount <b>" + dTax28AmountOnUI + " </b>");
                        m_assert.assertTrue(otherCharge == dOtherChargesOnUI, "Validated Created GRN -> Other Charges Amount <b>" + dOtherChargesOnUI + " </b>");
                        m_assert.assertTrue(amountPaid == dAmountPaidOnUI, "Validated Created GRN -> Amount Paid <b>" + dAmountPaidOnUI + " </b>");
                        m_assert.assertTrue(formatDecimalNumber(totalNetAmount) == dAmountRemainingOnUI, "Validated Created GRN-> Amount Remaining <b>" + dAmountRemainingOnUI + " </b>");
                        m_assert.assertTrue(formatDecimalNumber(totalNetAmount) == dTotalNetAmountOnUI, "Validated Created GRN -> Total Net Amount <b>" + dTotalNetAmountOnUI + " </b>");

                        Cls_Generic_Methods.driverRefresh();
                        Cls_Generic_Methods.customWait();
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                        Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                        Cls_Generic_Methods.customWait();
                        CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 10);

                        selectPurchaseOrder(sPurchaseOrderNo);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_rhsPoStatus, 10);
                        String status = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoStatus);
                        m_assert.assertTrue(status.equalsIgnoreCase("Partially Completed"), "Validated partially completed Transaction | PO Status  - <b>" + status + "</b>");

                        if (oPage_PurchaseOrder.list_transactionAgainstOrder.size() > 0) {
                            for (WebElement transaction : oPage_PurchaseOrder.list_transactionAgainstOrder) {
                                String value = Cls_Generic_Methods.getTextInElement(transaction);

                                if (value.contains(sPurchaseGrnNo)) {
                                    Cls_Generic_Methods.clickElementByJS(driver, transaction);
                                    Cls_Generic_Methods.customWait(5);
                                    m_assert.assertInfo("Validated Transaction against Order -> " + value);

                                    //Item 2 is completed
                                    totalGrossAmount = totalGrossAmount - CommonActions.convertStringToDouble(itemDetails2.get("GROSS"));
                                    totalDiscount = totalDiscount - CommonActions.convertStringToDouble(itemDetails2.get("DISCOUNT"));
                                    if (CommonActions.convertStringToDouble(itemDetails2.get("TAX AMOUNT")) == 5) {
                                        totalGST5Amount = totalGST5Amount - CommonActions.convertStringToDouble(itemDetails2.get("TAX AMOUNT"));
                                    } else {
                                        totalGST28Amount = totalGST28Amount - CommonActions.convertStringToDouble(itemDetails2.get("TAX AMOUNT"));
                                    }
                                    totalNetAmount = totalNetAmount - CommonActions.convertStringToDouble(itemDetails2.get("NET AMOUNT"));

                                } else {
                                    m_assert.assertFatal("Transaction Against Order Not found -> " + sPurchaseGrnNo);
                                }
                            }

                        } else {
                            m_assert.assertFatal("Transaction Against Order Details is not displayed");
                        }
                        break;
                    }

                }
            }

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        }
    }

    @Test(enabled = true, description = "Validate Completed Transaction Purchase Order ", dependsOnMethods = "validatePurchaseOrderCreation", priority = 4)
    public void validateCompletedNewTransaction() {
        oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Purchase = new Page_Purchase(driver);


        String billType = "Bill";
        String billNumber = "PO_BILL_NO_" + getRandomNumber();
        //   String
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 3);
            selectPurchaseOrder(sPurchaseOrderNo);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_NewTransaction, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_NewTransaction), "Clicked <b>New Transaction</b> button");
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 10), "Goods Receive Note Page is Displayed");

            //TIME AND DATE
            String grnDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
            String grnTime = CommonActions.getRequiredFormattedDateTime("h:mm a", "hh:mm a", Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value"));

            m_assert.assertTrue(compareDateTimesWithTolerance(grnDate + "|" + grnTime, getCurrentDateTime(), 1), "Validated GRN Date -> " + grnDate + " and Time -> " + grnTime);

            //Validate PO Link
            String poNumberInGrn = Cls_Generic_Methods.getTextInElement(oPage_Purchase.link_showPODetails);
            m_assert.assertTrue(poNumberInGrn.equals(sPurchaseOrderNo), "Validated Displayed Purchase Order No : " + poNumberInGrn);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Purchase.link_showPODetails), "Clicked Purchase Order Link");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_poLinkTableRowPurchaseOrder, 10);

            Cls_Generic_Methods.clickElement(oPage_Purchase.button_closePOLink);

            //HEADER GRN
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, orderNote), "Entered " + orderNote + " in Transaction Note");
            String vendorInGrn = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_vendorPOGrn, "value");
            m_assert.assertTrue(vendorInGrn.contains(vendorName), "Validated Displayed vendor in GRN : " + vendorInGrn);

            Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType), "Selected Bill Type as <b>" + billType + "</b>");

            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber), "Entered<b> " + billNumber + "</b> in Bill Number");
            Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
            Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate);
            String billDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_billDate, "value");
            m_assert.assertInfo("Selected Bill Date as " + billDate);

            validateCreateGRNItemDetails(itemDetails1, item1Discount, false);

            Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_vendorOtherChargesToBePaid, "0");

            //Calculation
            Cls_Generic_Methods.customWait();
            String grossAmountInGRN = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.text_totalGrossGRNPo, "value");
            String discountAmountInGRN = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.text_totalDiscountGRNPo, "value");
            String netAmount = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.text_totalNetAmountGRNPo, "value");

            m_assert.assertTrue(CommonActions.convertStringToDouble(grossAmountInGRN) == formatDecimalNumber(CommonActions.convertStringToDouble(itemDetails1.get("GROSS"))), "Validated GRN -> Total Gross Amount : <b>" + grossAmountInGRN + "</b>");
            m_assert.assertTrue(CommonActions.convertStringToDouble(discountAmountInGRN) == formatDecimalNumber(CommonActions.convertStringToDouble(itemDetails1.get("DISCOUNT"))), "Validated GRN -> Total Discount Amount : <b>" + discountAmountInGRN + "</b>");
            if (CommonActions.convertStringToDouble(itemDetails1.get("TAX")) == 5) {
                String gst5InGRN = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.text_totalGST5GRNPo, "value");
                m_assert.assertTrue(CommonActions.convertStringToDouble(gst5InGRN) == formatDecimalNumber(CommonActions.convertStringToDouble(itemDetails1.get("TAX AMOUNT"))), "Validated GRN -> Total GST5 Amount : <b>" + gst5InGRN + "</b>");
            } else {
                String gst28InGRN = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.text_totalGST28GRNPo, "value");
                m_assert.assertTrue(CommonActions.convertStringToDouble(gst28InGRN) == formatDecimalNumber(CommonActions.convertStringToDouble(itemDetails1.get("TAX AMOUNT"))), "Validated GRN -> Total GST28 Amount : <b>" + gst28InGRN + "</b>");
            }
            m_assert.assertTrue(CommonActions.convertStringToDouble(netAmount) == formatDecimalNumber(CommonActions.convertStringToDouble(itemDetails1.get("NET AMOUNT"))), "Validated GRN -> Total Net Amount : <b>" + netAmount + "</b>");

            String displayedOtherCharge = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_pendingOtherCharges);
            m_assert.assertTrue(CommonActions.convertStringToDouble(displayedOtherCharge) == 0, "Validated Displayed Pending amount to be paid : " + displayedOtherCharge);

            Cls_Generic_Methods.customWait();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Purchase.button_SaveChanges), "Clicked Save Changes");
            Cls_Generic_Methods.customWait(7);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 10);
            List<String> purchaseTransactionHeaderList = new ArrayList<>();

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
                        m_assert.assertInfo("Purchase Grn created and approved");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_assignBarcodePurchaseTransaction, 15);
                        sPurchaseGrnNo = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_transactionID);
                        String grnApprovedAt = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_grnApprovedAt);
                        m_assert.assertInfo("Purchase Grn no =<b>" + sPurchaseGrnNo + "</b>");


                        //FOOTER RHS
                        double dGrossAmtOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_totalGrossAmt));
                        double dTotalNetAmountOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_TotalNetAmount));
                        double dDiscountOnUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_TotalDiscount));


                        m_assert.assertTrue(formatDecimalNumber(totalGrossAmount) == dGrossAmtOnUI, "Validated Created GRN -> Gross Amount <b>" + dGrossAmtOnUI + " </b>");
                        m_assert.assertTrue(formatDecimalNumber(totalDiscount) == dDiscountOnUI, "Validated Created GRN -> Total Discount Amount <b>" + dDiscountOnUI + " </b>");
                        m_assert.assertTrue(formatDecimalNumber(totalNetAmount) == dTotalNetAmountOnUI, "Validated Created GRN -> Total Net Amount <b>" + dTotalNetAmountOnUI + " </b>");

                        Cls_Generic_Methods.driverRefresh();
                        Cls_Generic_Methods.customWait();
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                        Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                        Cls_Generic_Methods.customWait();
                        CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 10);

                        selectPurchaseOrder(sPurchaseOrderNo);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_rhsPoStatus, 10);
                        String status = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoStatus);
                        m_assert.assertTrue(status.equalsIgnoreCase("Completed"), "Validated Completed Transaction | PO Status  - <b>" + status + "</b>");

                        if (oPage_PurchaseOrder.list_transactionAgainstOrder.size() > 0) {
                            for (WebElement transaction : oPage_PurchaseOrder.list_transactionAgainstOrder) {
                                String value = Cls_Generic_Methods.getTextInElement(transaction);

                                if (value.contains(sPurchaseGrnNo)) {
                                    Cls_Generic_Methods.clickElementByJS(driver, transaction);
                                    Cls_Generic_Methods.customWait(5);
                                    m_assert.assertInfo("Validated Transaction against Order -> " + value);

                                }
                            }

                        } else {
                            m_assert.assertFatal("Transaction Against Order Details is not displayed");
                        }

                        Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                        break;
                    }

                }
            }

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Purchase Order Centralization ", priority = 5)
    public void validatePurchaseOrderCentralization() {
        oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);


        try {
            resetCounter();
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            getStoreAndVendorDetails(sStorePO);
            Cls_Generic_Methods.driverRefresh();
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 8);

                //Getting Item Details
                itemDetails1 = getStockableItemDetails(itemName1);

                //Create Purchase Order
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 3);

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_newOrder), "Clicked <b>New</b> Button in  Purchase Order");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_headerCreatePurchaseOrder, 10), "Validated Create Purchase Order Page displayed");

                String currentTimeAndDate = getCurrentDateTime();
                orderTime = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_purchaseOrderTime, "value");
                orderTime = CommonActions.getRequiredFormattedDateTime("h:mm a", "hh:mm a", orderTime);
                m_assert.assertTrue("Purchase Order time:<b> " + orderTime + "</b>");

                orderDate = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_purchaseOrderDate, "value");
                m_assert.assertTrue("Purchase Order date:<b> " + orderDate + "</b>");
                mapTracker.put(key_CreatedAt_PurchaseOrder, CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd-MM-yyyy", orderDate) + "  |  " + orderTime);

                m_assert.assertTrue(compareDateTimesWithTolerance(mapTracker.get(key_CreatedAt_PurchaseOrder), currentTimeAndDate, 1), "Validated Purchase Order Date and Time");

                //select vendor, store, type
                m_assert.assertTrue(selectByOptions(oPage_PurchaseOrder.dropdown_store, sStorePO.split("-")[0]), "Store selected : <b>" + sStorePO.split("-")[0] + " </b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_Vendor_search, 4);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, (vendorName + " " + vendor_address));
                Cls_Generic_Methods.customWait();

                Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, "" + Keys.DOWN + Keys.ENTER);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, "" + Keys.DOWN + Keys.ENTER),
                        "Vendor selected : <b>" + vendorName + "</b>");
                Cls_Generic_Methods.customWait();

                m_assert.assertTrue(selectByOptions(oPage_PurchaseOrder.dropdown_poType, sPoType), "PO Type selected : <b>" + sPoType + " </b>");
                Cls_Generic_Methods.customWait();

                //Select Item
                selectItemAndAddLot(itemDetails1, item1Discount);

                //Validating UI Header Elements
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_orderNote, 7);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_orderNote, orderNote), "Entered " + orderNote + " in Order Note");
                String storeGstNoInPo = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_storeGstNo, "value");
                m_assert.assertTrue(storeGstNoInPo.equals(storeGSTno), "Validated displayed Store GST No : <b>" + storeGstNoInPo + "</b>");

                //PO EXPIRY
                LocalDate date = LocalDate.now().plusDays(Integer.parseInt(vendorPOExpiry));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                expectedPOExpiryDay = date.format(formatter);

                String poExpiryDateInPo = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_expiryDatePO, "value");
                m_assert.assertTrue(poExpiryDateInPo.equals(expectedPOExpiryDay), "Validated displayed PO expiry Date : <b>" + poExpiryDateInPo + "</b>");

                //CREDIT DAYS
                String creditDaysInPo = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_creditDays, "value");
                m_assert.assertTrue(creditDaysInPo.equals(vendorCreditDays), "Validated displayed Vendor Credit Days : <b>" + creditDaysInPo + "</b>");

                //ADDRESS
                String selectedBillAddressInPo = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseOrder.select_billToStore);
                m_assert.assertTrue(selectedBillAddressInPo.contains(storeDefaultBillingAddress), "Validated displayed Bill To Address : <b>" + selectedBillAddressInPo + "</b>");

                String selectedShipAddressInPo = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseOrder.select_shipToStore);
                m_assert.assertTrue(selectedShipAddressInPo.contains(storeDefaultShippingAddress), "Validated displayed Ship To Address : <b>" + selectedShipAddressInPo + "</b>");

                //Create PO Table

                validateItemDetailsInCreatePO(itemDetails1);

                //Other Charges Validation

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_removeOtherCharges_createPo.get(0)), "Clicked Remove Other Charges button");
                Cls_Generic_Methods.customWait();


                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_saveOrder), "Purchase Order Saved");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 10);
                Cls_Generic_Methods.customWait();
                boolean selectPO = selectPurchaseOrder();

                if (selectPO) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_purchaseOrderId, 10);
                    String poCreatedAt = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_poCreatedAt);
                    m_assert.assertTrue(compareDateTimesWithTolerance(poCreatedAt, mapTracker.get(key_CreatedAt_PurchaseOrder), 1), "Validated PO Created At : " + poCreatedAt);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_purchaseOrderId, 10);

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_purchaseOrderId).equals(sPurchaseOrderNo), "Validated Purchase Order RHS -> PO No :<b> " + sPurchaseOrderNo + "</b>");
                    String poCreatedBy = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_poCreatedByUser);
                    m_assert.assertTrue(compareDateTimesWithTolerance(mapTracker.get(key_CreatedAt_PurchaseOrder), poCreatedAt, 0), "Purchase Order Created By <b>" + poCreatedBy + "</b> at -> <b>" + poCreatedAt + "</b>");

                    String rhsStatus = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoStatus);
                    String rhsPOType = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoType);
                    String rhsPOExpiry = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoExpiry);
                    String rhsVendor = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoVendor);
                    String rhsVendorGSTNo = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoVendorGSTNo);
                    String rhsVendorCreditDays = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoVendorCreditDays);
                    String rhsStoreShipTo = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoStoreShipTo);
                    String rhsStoreBillTo = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoStoreBillTo);
                    String rhsStoreGSTNo = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoStoreGSTNo);
                    String rhsStoreEntity = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoEntityGroup);

                    m_assert.assertTrue(rhsStatus.equalsIgnoreCase("pending"), "Validated Purchase Order RHS -> PO Status :<b> " + rhsStatus + "</b>");
                    m_assert.assertTrue(rhsPOType.equalsIgnoreCase(sPoType), "Validated Purchase Order RHS -> PO Type :<b> " + rhsPOType + "</b>");
                    m_assert.assertTrue(rhsPOExpiry.equals(expectedPOExpiryDay), "Validated Purchase Order RHS -> PO Type :<b> " + rhsPOExpiry + "</b>");
                    m_assert.assertTrue(rhsVendor.contains(vendorName), "Validated Purchase Order RHS -> Vendor Name :<b> " + vendorName + "</b>");
                    m_assert.assertTrue(rhsVendor.contains(vendor_address), "Validated Purchase Order RHS -> Vendor Address :<b> " + vendor_address + "</b>");
                    m_assert.assertTrue(rhsVendorGSTNo.equals(vendorGSTno), "Validated Purchase Order RHS -> Vendor GST No :<b> " + rhsVendorGSTNo + "</b>");
                    m_assert.assertTrue(rhsVendorCreditDays.equals(vendorCreditDays), "Validated Purchase Order RHS -> Vendor Credit Days :<b> " + rhsVendorCreditDays + "</b>");
                    m_assert.assertTrue(rhsStoreShipTo.contains(storeDefaultShippingAddress), "Validated Purchase Order RHS -> Ship To Address :<b> " + rhsStoreShipTo + "</b>");
                    m_assert.assertTrue(rhsStoreBillTo.contains(storeDefaultBillingAddress), "Validated Purchase Order RHS -> Bill To Address :<b> " + rhsStoreBillTo + "</b>");
                    m_assert.assertTrue(rhsStoreGSTNo.equals(storeGSTno), "Validated Purchase Order RHS -> Store GST No :<b> " + rhsStoreGSTNo + "</b>");
                    m_assert.assertTrue(storeEntityGroup.contains(rhsStoreEntity), "Validated Purchase Order RHS -> Store Entity Group :<b> " + storeEntityGroup + "</b>");

                    validatePoRHSItemDetails(itemDetails1);
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseOrder.button_editPO), "Edit Button is displayed");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseOrder.button_cancelPO), "Cancel Button is displayed");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_approveOrder, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_approveOrder), "Clicked <b>Approve</b> button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_Yes, 10);
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_PurchaseOrder.button_Yes);
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.label_approved, 5), "Validated Purchase Order Approved Notify Msg displayed");
                    Cls_Generic_Methods.customWait();

                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 3);
                    selectPurchaseOrder(sPurchaseOrderNo);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_rhsPoStatus, 10);
                    String poStatus = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoStatus);
                    m_assert.assertTrue(poStatus.equals("Approved"), "Validated Purchase Order : " + sPurchaseOrderNo + " is Approved");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_closeOrder, 10);
                    m_assert.assertFalse(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_NewTransaction, 1), "<b>New Transaction</b> button is not displayed in store : " + sStore);

                    Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

                    CommonActions.selectStoreOnApp(sStorePO);
                    Cls_Generic_Methods.switchToOtherTab();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                    Cls_Generic_Methods.customWait();

                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 3);

                    boolean poCreatedInStore = selectPurchaseOrder(sPurchaseOrderNo);

                    if (poCreatedInStore) {
                        String closeReason = "AUTO-CLOSE";
                        m_assert.assertTrue("Purchase Order(" + sPurchaseOrderNo + ")found in " + sStorePO);
                        m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newTransactionPO, 10), "<b>New Transaction</b> button is displayed in store : " + sStorePO);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_closeOrder), "<b>Close order</b> button clicked");
                        boolean closeStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_closeReasonPo, 8);
                        if (closeStatus) {
                            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_closeReasonPo, closeReason), "Entered <b>" + closeReason + "</b> in Close Reason");
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_confirmCloseOrder), "<b>Order close confirmed</b>");
                            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.label_closed, 10), "Validated Close ->Closed Notify msg displayed");
                        } else {
                            m_assert.assertFatal("Unable to validate close functionality");
                        }
                        if (closeStatus) {
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_rhsPoStatus, 10);
                            String status = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoStatus);
                            m_assert.assertTrue(status.equalsIgnoreCase("closed"), "Validated Purchase Order Close | Status = <b>" + status + "</b>");
                        }
                    }
                }
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Purchase Order Cancel functionality ", priority = 6)
    public void validatePurchaseOrderCancel() {
        try {
            resetCounter();
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            getStoreAndVendorDetails(sStore);
            Cls_Generic_Methods.driverRefresh();
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 8);

                //Getting Item Details
                itemDetails1 = getStockableItemDetails(itemName1);

                //Create Purchase Order
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 3);

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_newOrder), "Clicked <b>New</b> Button in  Purchase Order");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_headerCreatePurchaseOrder, 10), "Validated Create Purchase Order Page displayed");

                String currentTimeAndDate = getCurrentDateTime();
                orderTime = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_purchaseOrderTime, "value");
                orderTime = CommonActions.getRequiredFormattedDateTime("h:mm a", "hh:mm a", orderTime);
                m_assert.assertTrue("Purchase Order time:<b> " + orderTime + "</b>");

                orderDate = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_purchaseOrderDate, "value");
                m_assert.assertTrue("Purchase Order date:<b> " + orderDate + "</b>");
                mapTracker.put(key_CreatedAt_PurchaseOrder, CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd-MM-yyyy", orderDate) + "  |  " + orderTime);

                m_assert.assertTrue(compareDateTimesWithTolerance(mapTracker.get(key_CreatedAt_PurchaseOrder), currentTimeAndDate, 1), "Validated Purchase Order Date and Time");

                //select vendor, store, type
                m_assert.assertTrue(selectByOptions(oPage_PurchaseOrder.dropdown_store, sStore.split("-")[0]), "Store selected : <b>" + sStore.split("-")[0] + " </b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_Vendor_search, 4);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, (vendorName + " " + vendor_address));
                Cls_Generic_Methods.customWait();

                Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, "" + Keys.DOWN + Keys.ENTER);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, "" + Keys.DOWN + Keys.ENTER),
                        "Vendor selected : <b>" + vendorName + "</b>");
                Cls_Generic_Methods.customWait();

                m_assert.assertTrue(selectByOptions(oPage_PurchaseOrder.dropdown_poType, sPoType), "PO Type selected : <b>" + sPoType + " </b>");
                Cls_Generic_Methods.customWait();

                //Select Item
                selectItemAndAddLot(itemDetails1, item1Discount);

                //Validating UI Header Elements
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_orderNote, 7);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_orderNote, orderNote), "Entered " + orderNote + " in Order Note");
                String storeGstNoInPo = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_storeGstNo, "value");
                m_assert.assertTrue(storeGstNoInPo.equals(storeGSTno), "Validated displayed Store GST No : <b>" + storeGstNoInPo + "</b>");

                //PO EXPIRY
                LocalDate date = LocalDate.now().plusDays(Integer.parseInt(vendorPOExpiry));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                expectedPOExpiryDay = date.format(formatter);

                String poExpiryDateInPo = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_expiryDatePO, "value");
                m_assert.assertTrue(poExpiryDateInPo.equals(expectedPOExpiryDay), "Validated displayed PO expiry Date : <b>" + poExpiryDateInPo + "</b>");

                //CREDIT DAYS
                String creditDaysInPo = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_creditDays, "value");
                m_assert.assertTrue(creditDaysInPo.equals(vendorCreditDays), "Validated displayed Vendor Credit Days : <b>" + creditDaysInPo + "</b>");

                //ADDRESS
                String selectedBillAddressInPo = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseOrder.select_billToStore);
                m_assert.assertTrue(selectedBillAddressInPo.contains(storeDefaultBillingAddress), "Validated displayed Bill To Address : <b>" + selectedBillAddressInPo + "</b>");

                String selectedShipAddressInPo = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseOrder.select_shipToStore);
                m_assert.assertTrue(selectedShipAddressInPo.contains(storeDefaultShippingAddress), "Validated displayed Ship To Address : <b>" + selectedShipAddressInPo + "</b>");

                //Create PO Table

                validateItemDetailsInCreatePO(itemDetails1);

                //Other Charges Validation

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_removeOtherCharges_createPo.get(0)), "Clicked Remove Other Charges button");
                Cls_Generic_Methods.customWait();


                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_saveOrder), "Purchase Order Saved");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 10);
                Cls_Generic_Methods.customWait();
                boolean selectPO = selectPurchaseOrder();

                if (selectPO) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_purchaseOrderId, 10);
                    String poCreatedAt = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_poCreatedAt);
                    m_assert.assertTrue(compareDateTimesWithTolerance(poCreatedAt, mapTracker.get(key_CreatedAt_PurchaseOrder), 1), "Validated PO Created At : " + poCreatedAt);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_purchaseOrderId, 10);
                    String poCreatedBy = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_poCreatedByUser);
                    m_assert.assertTrue(compareDateTimesWithTolerance(mapTracker.get(key_CreatedAt_PurchaseOrder), poCreatedAt, 0), "Purchase Order Created By <b>" + poCreatedBy + "</b> at -> <b>" + poCreatedAt + "</b>");

                    String rhsStatus = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoStatus);
                    String cancelReason = "AUTO-CANCEL";

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_cancelPO, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_cancelPO), "<b>Cancel order</b> button clicked");
                    boolean cancelStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_cancelReason, 8);
                    if (cancelStatus) {
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_cancelReason, cancelReason), "Entered <b>" + cancelReason + "</b> in Cancellation Reason");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_confirmCancelOrder), "<b>Order Cancel confirmed</b>");
                        m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.label_cancelled, 10), "Validated Cancel ->Cancelled Notify msg displayed");
                    } else {
                        m_assert.assertFatal("Unable to validate cancel functionality");
                    }
                    if (cancelStatus) {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_rhsPoStatus, 10);
                        String status = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsPoStatus);
                        String cancelReasonInRhs = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsCancellationReason);
                        m_assert.assertTrue(cancelReasonInRhs.equalsIgnoreCase(cancelReason), "Validated Purchase Order Rhs -> Cancellation Reason = <b>" + cancelReasonInRhs + "</b>");
                        m_assert.assertTrue(status.equalsIgnoreCase("cancelled"), "Validated Purchase Order Cancel | Status = <b>" + status + "</b>");
                    }

                }
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }
    }

    public static boolean getStatusOfUi(WebElement e, String value) {
        boolean status = false;
        try {
            if (Cls_Generic_Methods.getElementAttribute(e, "value").equals(value)) {
                status = true;
            } else if (Cls_Generic_Methods.getElementAttribute(e, "value").contains(value)) {
                status = true;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return status;
    }

    private void getStoreAndVendorDetails(String sStoreName) {
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        Page_VendorMaster oPage_VendorMaster = new Page_VendorMaster(driver);
        Page_TermsAndConditions oPage_TermsAndConditions = new Page_TermsAndConditions(driver);
        storeDefaultShippingAddress = "";
        storeDefaultBillingAddress = "";
        storeGSTno = "";
        vendor_address = "";
        vendorGSTno = "";
        vendorCreditDays = "";
        storeEntityGroup = "";
        vendorPOExpiry = "";


        try {
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 3);

            int storeNo = 0;
            for (WebElement txtStoreName : oPage_StoreSetUp.list_text_storeName) {
                String storeName = Cls_Generic_Methods.getTextInElement(txtStoreName);
                if (storeName.contains(sStoreName.split("-")[0])) {
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
                            storeDefaultBillingAddress = Cls_Generic_Methods.getElementAttribute(oPage_StoreSetUp.input_billingAddress, "value");
                            m_assert.assertInfo("Default Billing Address for " + storeName + " is <b>" + storeDefaultBillingAddress + "</b>");
                            m_assert.assertInfo("GST no. present in " + storeName + " is <b>" + storeGSTno + "</b>");
                            storeEntityGroup = Cls_Generic_Methods.getSelectedValue(oPage_StoreSetUp.select_entityGroup);
                        }
                    }

                    for (WebElement btn_Edit : oPage_StoreSetUp.list_btn_editStoreShippingAddress) {
                        boolean defaultAddress = false;

                        try {
                            Cls_Generic_Methods.isElementDisplayed(btn_Edit.findElement(By.xpath("./parent::td/following-sibling::td/a[text()='Mark Default']")));
                        } catch (NoSuchElementException e) {
                            defaultAddress = true;
                        }

                        if (defaultAddress) {
                            Cls_Generic_Methods.clickElement(driver, btn_Edit);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.input_billingAddress, 10);
                            storeDefaultShippingAddress = Cls_Generic_Methods.getElementAttribute(oPage_StoreSetUp.input_shippingAddress, "value");
                            m_assert.assertInfo("Default Shipping Address for " + storeName + " is <b>" + storeDefaultShippingAddress + "</b>");
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
                                vendorCreditDays = Cls_Generic_Methods.getElementAttribute(oPage_VendorMaster.input_inventoryVendorCreditDays, "value");
                                vendorPOExpiry = Cls_Generic_Methods.getElementAttribute(oPage_VendorMaster.input_expiryPOAfterDays, "value");

                                m_assert.assertInfo("Vendor Credit days present in " + vendorName + " is <b>" + vendorCreditDays + "</b>");
                                m_assert.assertInfo("Vendor GST No present in " + vendorName + " is <b>" + vendorGSTno + "</b>");
                            }
                            headerColumn++;
                        }
                    }
                    vendorNo++;
                }
            }
            Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_close);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Terms & Conditions");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TermsAndConditions.button_addTermsAndConditions, 10);

            for (WebElement eDeliveryTerms : oPage_TermsAndConditions.text_descriptionsDeliveryTerms) {
                String description = Cls_Generic_Methods.getTextInElement(eDeliveryTerms);
                boolean activeStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(driver.findElement(By.xpath("//div[@data-sub-category='" + description + "']")), 2);
                if (activeStatus) {
                    if (deliveryTerms.isEmpty()) {
                        deliveryTerms = description;
                    } else {
                        deliveryTerms = deliveryTerms.concat("|").concat(description);
                    }
                }
            }
            for (WebElement eDeliveryTerms : oPage_TermsAndConditions.text_descriptionsPaymentTerms) {
                String description = Cls_Generic_Methods.getTextInElement(eDeliveryTerms);
                boolean activeStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(driver.findElement(By.xpath("//div[@data-sub-category='" + description + "']")), 2);
                if (activeStatus) {
                    if (paymentTerms.isEmpty()) {
                        paymentTerms = description;
                    } else {
                        paymentTerms = paymentTerms.concat("|").concat(description);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to get Store and Vendor Details from Organisation Setting " + e);
        }
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

    private void selectItemAndAddLot(HashMap<String, String> itemDetails, String discount) {
        String costPrice = getRandomDecimal(3);
        String unitQuantity = "";
        String subStoreName = "Default";
        String expiryDate = "";

        try {
            String itemDescription = itemDetails.get("DESCRIPTION");
            Cls_Generic_Methods.clearValuesInElement(oPage_PurchaseOrder.input_searchMedicineNamePO);
            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.input_searchMedicineNamePO);
            Cls_Generic_Methods.sendKeysByAction(oPage_PurchaseOrder.input_searchMedicineNamePO, itemDescription);
            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.input_searchMedicineNamePO);
            Cls_Generic_Methods.customWait();
            oPage_PurchaseOrder.input_searchMedicineNamePO.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait();
            boolean bPO_Item_Found = false;

            int rowNo = 0;
            for (WebElement eMedicineName : oPage_PurchaseOrder.list_namesOfMedicinesOnLeftInSearchResultPO) {

                String sMedicineName = Cls_Generic_Methods.getTextInElement(eMedicineName);
                String itemCode = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.list_itemCodeOnLeftInSearchResultPO.get(rowNo));

                if (sMedicineName.equals(itemDescription) && itemCode.contains(itemDetails.get("ITEM CODE"))) {
                    bPO_Item_Found = true;
                    Cls_Generic_Methods.clickElement(eMedicineName);
                    break;
                }
                rowNo++;
            }

            if (bPO_Item_Found) {
                m_assert.assertTrue("Validate the PO item:<b> " + itemDescription + " </b> is found & selected");

                //Validate Lot Header
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.header_addNewLot, 15);
                String displayedLotHeader = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.header_addNewLot);
                m_assert.assertTrue(displayedLotHeader.contains(itemDescription), "Validated Displayed Add Lot Header : <b>" + displayedLotHeader + "</b>");

                String description = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_descriptionHeaderAddLot);
                String hsnNo = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_hsnNoHeaderAddLot);
                String itemCode = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_itemCodeHeaderAddLot);
                String category = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_categoryHeaderAddLot);
                String dispensingUnit = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_dispensingUnitHeaderAddLot);
                String packageType = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_packageTypeHeaderAddLot);
                String subPackage = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_subPackageHeaderAddLot);
                String unit = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_unitHeaderAddLot);

                m_assert.assertTrue(description.equals(itemDescription), "Validated Add Lot Header -> Description : <b>" + description + "</b>");
                m_assert.assertTrue(hsnNo.equals(itemDetails.get("HSN")), "Validated Add Lot Header -> HSN No : <b>" + hsnNo + "</b>");
                m_assert.assertTrue(itemCode.equals(itemDetails.get("ITEM CODE")), "Validated Add Lot Header -> Item Code : <b>" + itemCode + "</b>");
                m_assert.assertTrue(category.equalsIgnoreCase(itemDetails.get("CATEGORY")), "Validated Add Lot Header -> Category : <b>" + category + "</b>");
                m_assert.assertTrue(dispensingUnit.equals(itemDetails.get("DISPENSING UNIT")), "Validated Add Lot Header -> Dispensing Unit : <b>" + dispensingUnit + "</b>");
                m_assert.assertTrue(packageType.equals(itemDetails.get("PACKAGE TYPE")), "Validated Add Lot Header -> Package Type : <b>" + packageType + "</b>");

                m_assert.assertTrue(subPackage.split(" ")[1].equals(itemDetails.get("SUB PACKAGE TYPE")), "Validated Add Lot Header -> Sub Package Type : <b>" + subPackage.split(" ")[1] + "</b>");
                m_assert.assertTrue(subPackage.split(" ")[0].equals(itemDetails.get("SUB PACKAGE VALUE")), "Validated Add Lot Header -> Sub Package in Package : <b>" + subPackage.split(" ")[0] + "</b>");

                m_assert.assertTrue(unit.split(" ")[1].equals(itemDetails.get("UNIT TYPE")), "Validated Add Lot Header -> Unit Type : <b>" + unit.split(" ")[1] + "</b>");
                m_assert.assertTrue(unit.split(" ")[0].equals(itemDetails.get("UNIT VALUE")), "Validated Add Lot Header -> Unit in Sub Package : <b>" + unit.split(" ")[0] + "</b>");


                //Validate Error Message
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_saveLot), "Clicked <b>Save</b> without entering any value");

                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.label_totalPaidError, 5),
                        "Validated Error Msg is displayed for Total Paid Unit -> <b>" + Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.label_totalPaidError) + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.label_unitCostError, 5),
                        "Validated Error Msg is displayed for Unit Cost -> <b>" + Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.label_unitCostError) + "</b>");

                //Select Possible Variants
                for (Map.Entry<String, String> entry : itemDetails.entrySet()) {
                    String key = entry.getKey();

                    if (key.contains("VARIANT")) {
                        String value = entry.getValue();
                        String variantName = value.split("=")[0];
                        String variantValue = value.split("=")[1];
                        String optionValue = "";

                        WebElement eSelectVariant = driver.findElement(By.xpath("//select[contains(@id,'" + variantName + "')]"));
                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(eSelectVariant), "Validated ->Displayed Possible Variant Name <b>" + variantName + "</b> for the item " + itemDescription);

                        if (Cls_Generic_Methods.isElementDisplayed(eSelectVariant)) {
                            for (WebElement eVariant : eSelectVariant.findElements(By.xpath("./option"))) {
                                String option = Cls_Generic_Methods.getTextInElement(eVariant);
                                if (optionValue.isEmpty()) {
                                    if (!option.equalsIgnoreCase("select")) {
                                        optionValue = option;
                                    }
                                } else {
                                    optionValue = optionValue.concat("|").concat(option);
                                }
                            }
                        }
                        m_assert.assertTrue(variantValue.equals(optionValue), "Validated Displayed variants for " + variantName + " : <b>" + optionValue.replaceAll("\\|", ",") + "</b>");
                        String optionToSelect = optionValue.split("\\|")[0];
                        m_assert.assertInfo(selectByOptions(eSelectVariant, optionToSelect), "Selected <b>" + optionToSelect + "</b> in variant " + variantName);
                    }
                }

                //Select Sub-Store
                if (Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseOrder.dropdown_subStore)) {
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_PurchaseOrder.dropdown_subStore, subStoreName),
                            "Sub Store selected: " + subStoreName);
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseOrder.input_expiryDateCreatePO)) {
                    Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.input_expiryDateCreatePO);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.select_expiryDateYear, 1);
                    String currentYear = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseOrder.select_expiryDateYear);
                    int year = Integer.parseInt(currentYear);
                    int newYear = year + 3;

                    Cls_Generic_Methods.selectElementByVisibleText(oPage_PurchaseOrder.select_expiryDateYear, Integer.toString(newYear));
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_PurchaseOrder.select_expiryDateDay);
                    expiryDate = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_expiryDateCreatePO, "value");

                    m_assert.assertInfo("Selected Expiry Date for item " + itemDescription + " as <b>" + expiryDate + "</b>");
                }

                //Validate Unit Cost w/o tax
                Cls_Generic_Methods.clearValuesInElement(oPage_PurchaseOrder.input_unitCostWOTax);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_unitCostWOTax, String.valueOf(costPrice)),
                        "Entered Unit Cost price w/o Tax as : <b>" + costPrice + "</b>");

                //Calculation
                double dCostPrice = CommonActions.convertStringToDouble(costPrice);
                double dTaxAmount = (CommonActions.convertStringToDouble(itemDetails.get("TAX")) / 100) * dCostPrice;

                String actualUnitCostWithTax = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_unitCost, "value");
                double expectedUnitCostWithTax = dCostPrice + dTaxAmount;
                m_assert.assertTrue(formatDecimalNumber(expectedUnitCostWithTax) == CommonActions.convertStringToDouble(actualUnitCostWithTax),
                        "Validated Displayed Unit Cost price with Tax : <b>" + actualUnitCostWithTax + "</b>");


                //Package SubPackage Unit
                double dUnit = CommonActions.convertStringToDouble(itemDetails.get("UNIT VALUE"));
                double dSubPackageUnit = CommonActions.convertStringToDouble(itemDetails.get("SUB PACKAGE VALUE"));

                if (dUnit > 1) {
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_packageQuantity, String.valueOf(dUnit - 1));
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.label_unitError, 5), "Validated Error message is displayed if unit value is lesser than unit in Sub package");
                    Cls_Generic_Methods.clearValuesInElement(oPage_PurchaseOrder.input_packageQuantity);

                    Cls_Generic_Methods.customWait();

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_freeUnitQuantity, String.valueOf(dUnit - 1));
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.label_unitError, 5), "Validated Error message is displayed if Free Qty value is lesser than unit in Sub package");
                    Cls_Generic_Methods.clearValuesInElement(oPage_PurchaseOrder.input_freeUnitQuantity);
                }

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_subPackage, "1"), "Entered <b>1</b> in Sub Package");
                Cls_Generic_Methods.customWait();
                double totalPaidUnit = CommonActions.convertStringToDouble(Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_paidStock, "value"));
                m_assert.assertTrue(totalPaidUnit == dUnit, "Validated Displayed Paid qty ->Units in a Sub Package= " + totalPaidUnit);
                Cls_Generic_Methods.clearValuesInElement(oPage_PurchaseOrder.input_subPackage);

                double expectedUnitInPackage = dUnit * dSubPackageUnit;
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_package, "1"), "Entered <b>1</b> in Package");
                Cls_Generic_Methods.customWait();
                totalPaidUnit = CommonActions.convertStringToDouble(Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_paidStock, "value"));
                m_assert.assertTrue(totalPaidUnit == expectedUnitInPackage, "Validated Displayed Paid qty ->Units in a Package= " + totalPaidUnit);
                Cls_Generic_Methods.clearValuesInElement(oPage_PurchaseOrder.input_package);

                //Entering Quantity
                unitQuantity = String.valueOf(3);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_packageQuantity, unitQuantity), "Entered <b>" + unitQuantity + "</b> in Unit Quantity");
                double itemGross = CommonActions.convertStringToDouble(costPrice) * CommonActions.convertStringToDouble(unitQuantity);
                totalGrossAmount = totalGrossAmount + itemGross;

                //Free Qty
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_freeUnitQuantity, itemDetails.get("UNIT VALUE")), "Entered <b>" + dUnit + "</b> in Free Quantity");

                //Applying Discount
                String discountInput = discount.split(" ")[0];
                String discountType = discount.split(" ")[1];

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_discountAmount, discountInput), "Entered <b>" + discountInput + "</b> in Discount input");
                m_assert.assertInfo(selectByOptions(oPage_PurchaseOrder.select_discountType, discountType), "Selected <b>" + discountType + "</b> as Discount Type");

                Cls_Generic_Methods.customWait();
                double actualDiscountAmount = CommonActions.convertStringToDouble(Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.label_discountValue).replace("-", ""));

                double itemDiscount = 0;
                if (discountType.equals("%")) {
                    itemDiscount = itemGross * (CommonActions.convertStringToDouble(discountInput) / 100);
                } else {
                    itemDiscount = CommonActions.convertStringToDouble(discountInput);
                }

                m_assert.assertTrue(actualDiscountAmount == formatDecimalNumber(itemDiscount), "Validated -> Displayed Discount Amount : <b>" + actualDiscountAmount + "</b>");
                totalDiscount = totalDiscount + itemDiscount;
                double taxPercentage = CommonActions.convertStringToDouble(itemDetails.get("TAX"));
                dTaxAmount = (taxPercentage / 100) * (itemGross - itemDiscount);

                if (taxPercentage == 5) {
                    totalGST5Amount = totalGST5Amount + dTaxAmount;
                } else {
                    totalGST28Amount = totalGST28Amount + dTaxAmount;
                }

                double dItemNetAmount = itemGross + dTaxAmount - formatDecimalNumber(itemDiscount);
                totalNetAmount = totalNetAmount + formatDecimalNumber(dItemNetAmount);
                double actualNetAmount = CommonActions.convertStringToDouble(Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.text_totalCost, "value"));
                m_assert.assertTrue(actualNetAmount == formatDecimalNumber(dItemNetAmount), "Validated -> Displayed Total Cost Amount : <b>" + actualNetAmount + "</b>");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_saveLot), "Clicked <b>Save</b> Lot");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.dropdown_otherCharge, 7);

                itemDetails.put("RATE", costPrice);
                itemDetails.put("FREE QTY", String.valueOf(dUnit));
                itemDetails.put("EXPIRY", expiryDate);
                itemDetails.put("PAID QTY", unitQuantity);
                itemDetails.put("GROSS", String.valueOf(itemGross));
                itemDetails.put("DISCOUNT", String.valueOf(formatDecimalNumber(itemDiscount)));
                itemDetails.put("TAX AMOUNT", String.valueOf(formatDecimalNumber(dTaxAmount)));
                itemDetails.put("NET AMOUNT", String.valueOf(formatDecimalNumber(dItemNetAmount)));
                itemDetails.put("PENDING PAID QTY", unitQuantity);
                itemDetails.put("PENDING FREE QTY", String.valueOf(dUnit));


                System.out.println("---------- BREAK UP " + itemDescription + " ------------");
                System.out.println("ITEM GROSS ----->" + itemGross);
                System.out.println("ITEM DISCOUNT -->" + itemDiscount);
                System.out.println("TAX AMOUNT ----->" + dTaxAmount);
                System.out.println("NET AMOUNT ----->" + dItemNetAmount);

            } else {
                m_assert.assertFatal("Unable to select Item : " + itemDescription);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRandomNumber() {
        Random random = new Random();
        String id = String.format("%04d", random.nextInt(10000));
        return id;
    }

    public String getRandomDecimal(int numDigits) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        Random random = new Random();
        double randomValue = random.nextDouble();
        double scaledValue = randomValue * Math.pow(10, numDigits);
        return decimalFormat.format(scaledValue);
    }

    private HashMap<String, String> getStockableItemDetails(String itemDescription) {

        String sItemTotalStock = "";
        String itemCode = "";
        String category = "";
        String dispensingUnit = "";
        String taxPercentage = "";
        String packageType = "";
        String subPackageType = "";
        String unitType = "";
        String subPackageValue = "";
        String unitValue = "";
        String hsnCode = "";


        boolean itemFound = false;
        Page_Master oPage_Master = new Page_Master(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);

        HashMap<String, String> itemDetails = new HashMap<>();

        try {

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 8);
            Cls_Generic_Methods.clearValuesInElement(oPage_Master.input_itemNameSearchInItemMaster);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, itemDescription);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_Master.input_itemNameSearchInItemMaster);
            oPage_Master.input_itemNameSearchInItemMaster.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(5);

            for (WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory) {
                if (Cls_Generic_Methods.isElementDisplayed(itemData)) {
                    List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./child::*"));
                    String itemDescriptionName = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));
                    String itemStock = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((2)));

                    if (itemDescriptionName.contains(itemDescription)) {
                        itemFound = true;
                        sItemTotalStock = itemStock;
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemData), "<b> " + itemDescription + " </b> is present in Item Master List");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.button_editItem, 15);
                        m_assert.assertInfo("Available stock of item : " + itemDescription + " --> <b>" + itemStock + "</b>");
                        itemCode = Cls_Generic_Methods.getTextInElement(oPage_Master.text_itemCode);


                        Cls_Generic_Methods.clickElement(oPage_Master.button_editItem);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.header_editItemHeader, 10);
                        taxPercentage = Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_itemPropertiesTaxList).split("-")[1].replaceAll("%", "").trim();
                        hsnCode = Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_itemHsnCode, "value");
                        category = Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_itemCategory);
                        dispensingUnit = Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_itemDispensingUnit);
                        packageType = Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_inventoryItemPackageType);
                        subPackageType = Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_inventoryItemSubPackageType);
                        unitType = Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_inventoryItemUnitType);
                        unitValue = Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_inventoryItemSubPackageItemUnit, "value");
                        subPackageValue = Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_inventoryItemSubPackageUnit, "value");

                        int rowNo = 0;
                        for (WebElement eVariantName : oPage_ItemMaster.list_input_itemPossibleVariantNameList) {
                            String variantValue = "";

                            String variantName = Cls_Generic_Methods.getElementAttribute(eVariantName, "value");

                            List<WebElement> eVariantValue = oPage_ItemMaster.list_selectItemPossibleVariantValue.get(rowNo).findElements(By.xpath("./option"));
                            for (WebElement variants : eVariantValue) {
                                String sValue = Cls_Generic_Methods.getTextInElement(variants);
                                if (variantValue.isEmpty()) {
                                    variantValue = sValue;
                                } else {
                                    variantValue = variantValue.concat("|").concat(sValue);
                                }
                            }
                            rowNo++;
                            itemDetails.put("VARIANT" + rowNo, variantName + "=" + variantValue);
                        }
                        Cls_Generic_Methods.clickElement(driver, oPage_ItemMaster.button_closeItemMasterTemplate);
                        break;
                    }
                }
            }


            if (itemFound) {
                itemDetails.put("ITEM CODE", itemCode);
                itemDetails.put("CATEGORY", category);
                itemDetails.put("DISPENSING UNIT", dispensingUnit);
                itemDetails.put("STOCK", sItemTotalStock);
                itemDetails.put("TAX", taxPercentage);
                itemDetails.put("PACKAGE TYPE", packageType);
                itemDetails.put("SUB PACKAGE TYPE", subPackageType);
                itemDetails.put("UNIT TYPE", unitType);
                itemDetails.put("SUB PACKAGE VALUE", subPackageValue);
                itemDetails.put("UNIT VALUE", unitValue);
                itemDetails.put("DESCRIPTION", itemDescription);
                itemDetails.put("HSN", hsnCode);

            } else {
                m_assert.assertFatal("Unable to find item ->" + itemDescription);
            }

        } catch (Exception e) {
            m_assert.assertFatal("Unable to get Item Details -" + itemDetails + "  -->" + e);
            e.printStackTrace();
        }

        return itemDetails;

    }

    private String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | hh:mm a");
        Date date = new Date();
        String date1 = dateFormat.format(date);
        return date1;
    }

    public double formatDecimalNumber(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(java.math.RoundingMode.HALF_UP);
        return Double.parseDouble(decimalFormat.format(number));
    }

    private static boolean compareDateTimesWithTolerance(String dateTime1, String dateTime2, int minuteTolerance) {
        dateTime1 = dateTime1.replaceAll(" ", "").replaceAll("/", "-");
        dateTime2 = dateTime2.replaceAll(" ", "").replaceAll("/", "-");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy|hh:mma");
        LocalDateTime dt1 = LocalDateTime.parse(dateTime1, formatter);
        LocalDateTime dt2 = LocalDateTime.parse(dateTime2, formatter);

        long differenceInMinutes = Math.abs(ChronoUnit.MINUTES.between(dt1, dt2));
        return differenceInMinutes <= minuteTolerance;
    }

    public void validateItemDetailsInCreatePO(HashMap<String, String> itemDetails) {

        try {
            int itemRow = 0;
            for (WebElement description : oPage_PurchaseOrder.list_itemDescriptionCreatePO) {
                String descriptionInPO = Cls_Generic_Methods.getElementAttribute(description, "value");

                if (descriptionInPO.equalsIgnoreCase(itemDetails.get("DESCRIPTION"))) {

                    itemRow = oPage_PurchaseOrder.list_itemDescriptionCreatePO.indexOf(description);
                    m_assert.assertTrue("Validated Item : " + descriptionInPO + " found in Create PO");

                    String rateInPO = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.list_itemRateCreatePO.get(itemRow), "value");
                    String taxRateInPO = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.list_itemTaxRateCreatePO.get(itemRow), "value");
                    String paidQtyInPO = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.list_itemPaidQtyCreatePO.get(itemRow), "value");
                    String freeQtyInPO = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.list_itemFreeQtyCreatePO.get(itemRow), "value");
                    String discountInPO = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.list_itemDiscountCreatePO.get(itemRow), "value");
                    String netAmountInPO = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.list_itemNetAmountCreatePO.get(itemRow), "value");
                    String subStoreInPO = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.list_itemSubStoreCreatePO.get(itemRow), "value");

                    m_assert.assertTrue(CommonActions.convertStringToDouble(itemDetails.get("RATE")) == CommonActions.convertStringToDouble(rateInPO), "Validated -> Rate for " + descriptionInPO + " : <b>" + rateInPO + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(itemDetails.get("TAX")) == CommonActions.convertStringToDouble(taxRateInPO), "Validated -> Tax Rate for " + descriptionInPO + " : <b>" + taxRateInPO + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(itemDetails.get("PAID QTY")) == CommonActions.convertStringToDouble(paidQtyInPO), "Validated -> Paid Qty for " + descriptionInPO + " : <b>" + paidQtyInPO + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(itemDetails.get("FREE QTY")) == CommonActions.convertStringToDouble(freeQtyInPO), "Validated -> Free Qty for " + descriptionInPO + " : <b>" + freeQtyInPO + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(itemDetails.get("DISCOUNT")) == CommonActions.convertStringToDouble(discountInPO), "Validated -> Discount Amount for " + descriptionInPO + " : <b>" + discountInPO + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(itemDetails.get("NET AMOUNT")) == CommonActions.convertStringToDouble(netAmountInPO), "Validated -> Net Amount for " + descriptionInPO + " : <b>" + netAmountInPO + "</b>");
                    m_assert.assertTrue(subStoreInPO.equalsIgnoreCase("N/A"), "Validated -> Selected Sub-Store for " + descriptionInPO + " : <b>" + subStoreInPO + "</b>");
                    String remark = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.list_itemRemarkCreatePO.get(itemRow), "value");
                    if (remark.isEmpty()) {
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.list_itemRemarkCreatePO.get(itemRow), "TEST"), "Entered TEST in remark");
                    } else {
                        m_assert.assertTrue(remark.equals("TEST"), "Validated -> Remark for " + descriptionInPO + " : <b>" + remark + "</b>");
                    }

                }
            }


        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate item details " + e);
            e.printStackTrace();
        }
    }

    public boolean selectPurchaseOrder(String... purchaseOrderNo) {
        boolean flag = false;
        String selectPORow = null;

        try {
            if (purchaseOrderNo.length > 0) {
                selectPORow = purchaseOrderNo[0];
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 10);
            List<String> purchaseOrderHeaderList = new ArrayList<>();

            for (WebElement purchaseHeaderList : oPage_PurchaseOrder.list_purchaseOrderHeaderList) {
                purchaseOrderHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
            }

            int rowNo = 0;
            for (WebElement row : oPage_PurchaseOrder.list_purchaseOrdertransactions) {
                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));
                    String purchaseOrderCreatedAtInRow = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.list_orderInfoTransactionList.get(rowNo)).split("\n")[0];
                    String purchaseOrderNoInRow = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.list_orderInfoTransactionList.get(rowNo)).split("\n")[1];
                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseOrderHeaderList.indexOf("Status")));

                    if (purchaseOrderNo.length > 0) {
                        if (purchaseOrderNoInRow.equals(selectPORow)) {
                            Cls_Generic_Methods.clickElement(row);
                            flag = true;
                            sPurchaseOrderNo = purchaseOrderNoInRow;
                            m_assert.assertInfo("Selected Purchase Order Transaction -> PO No : " + purchaseOrderNoInRow + " | Status : " + purchaseStatus);
                            break;
                        }
                    } else {
                        Cls_Generic_Methods.clickElement(row);
                        flag = true;
                        sPurchaseOrderNo = purchaseOrderNoInRow;
                        m_assert.assertInfo("Selected Purchase Order Transaction -> PO No : " + purchaseOrderNoInRow + " | Status : " + purchaseStatus);
                        break;
                    }
                }
                rowNo++;
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to find Purchase Order " + e);
        }
        return flag;
    }

    private void validatePoRHSItemDetails(HashMap<String, String> itemDetails) {

        try {
            ArrayList<String> sHeaderValue = new ArrayList<>();
            for (WebElement eHeader : oPage_PurchaseOrder.list_rhsTableHeaderPurchaseOrder) {
                String value = Cls_Generic_Methods.getTextInElement(eHeader);
                sHeaderValue.add(value);
            }

            for (WebElement row : oPage_PurchaseOrder.list_rhsTableRowPurchaseOrder) {
                String description = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Description")));
                if (description.contains(itemDetails.get("DESCRIPTION"))) {
                    String hsn = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("HSN")));
                    String paidQuantity = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Qty.")));
                    String freeQuantity = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Free Qty.")));
                    String taxPercentage = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Tax%")));
                    String rate = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Rate")));
                    String discount = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Discount")));
                    String amount = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Amount")));
                    String remark = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Remark")));

                    m_assert.assertTrue(description.contains(itemDetails.get("DESCRIPTION")), "Validated -> PO Created Item Description : <b>" + description + "</b>");
                    if (!itemDetails.get("HSN").isEmpty()) {
                        m_assert.assertTrue(hsn.equals(itemDetails.get("HSN")), "Validated -> PO Created Item HSN : <b>" + hsn + "</b>");
                    }
                    m_assert.assertTrue(CommonActions.convertStringToDouble(paidQuantity) == CommonActions.convertStringToDouble(itemDetails.get("PAID QTY")), "Validated ->PO Created Item Paid Qty : <b>" + paidQuantity + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(freeQuantity) == CommonActions.convertStringToDouble(itemDetails.get("FREE QTY")), "Validated ->PO Created Item Free Qty : <b>" + freeQuantity + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(taxPercentage) == CommonActions.convertStringToDouble(itemDetails.get("TAX")), "Validated ->PO Created Item Tax Percentage : <b>" + taxPercentage + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(rate) == CommonActions.convertStringToDouble(itemDetails.get("RATE")), "Validated ->PO Created Item Rate : <b>" + rate + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(discount) == CommonActions.convertStringToDouble(itemDetails.get("DISCOUNT")), "Validated ->PO Created Item Discount : <b>" + discount + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(amount) == CommonActions.convertStringToDouble(itemDetails.get("NET AMOUNT")), "Validated ->PO Created Item Net Amount : <b>" + amount + "</b>");
                    m_assert.assertTrue(remark.equals("TEST"), "Validated -> PO Created Item Remark : <b>TEST</b>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validateGrnRHSItemDetails(HashMap<String, String> itemDetails) {

        try {
            ArrayList<String> sHeaderValue = new ArrayList<>();
            for (WebElement eHeader : oPage_PurchaseOrder.list_rhsTableHeaderPurchaseGrn) {
                String value = Cls_Generic_Methods.getTextInElement(eHeader);
                sHeaderValue.add(value);
            }

            for (WebElement row : oPage_PurchaseOrder.list_rhsTableRowPurchaseGrn) {
                String description = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Description")));
                if (description.contains(itemDetails.get("DESCRIPTION"))) {
                    String hsn = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("HSN")));
                    String batch = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Batch No.")));
                    String expiry = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Expiry")));
                    String mrp = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("MRP")));
                    String grnQuantity = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("GRN Qty")));
                    String freeQuantity = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Free Qty")));
                    String rate = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Rate")));
                    String taxPercentage = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Tax%")));
                    String discount = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Discount")));
                    String amount = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Total")));
                    String remark = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Remark")));

                    m_assert.assertTrue(description.contains(itemDetails.get("DESCRIPTION")), "Validated Purchase GRN -> Item Description : <b>" + description + "</b>");
                    if (!itemDetails.get("HSN").isEmpty()) {
                        m_assert.assertTrue(hsn.equals(itemDetails.get("HSN")), "Validated Purchase GRN -> Item(" + description + ") -> HSN : <b>" + hsn + "</b>");
                    }
                    m_assert.assertTrue(batch.equals(itemDetails.get("BATCH")), "Validated Purchase GRN -> Item(" + description + ") -> Batch No : <b>" + batch + "</b>");

                    if (!itemDetails.get("EXPIRY").isEmpty()) {
                        m_assert.assertTrue(CommonActions.getRequiredFormattedDateTime("yyyy-mm-dd", "dd/mm/yyyy", expiry).equals(itemDetails.get("EXPIRY")), "Validated Purchase GRN -> Item(" + description + ") -> Expiry : <b>" + expiry + "</b>");
                    } else {
                        m_assert.assertTrue(expiry.equals(itemDetails.get("EXPIRY")), "Validated Purchase GRN -> Item(" + description + ") -> Expiry : <b>" + expiry + "</b>");
                    }
                    m_assert.assertTrue(CommonActions.convertStringToDouble(mrp) == CommonActions.convertStringToDouble(itemDetails.get("MRP")), "Validated Purchase GRN -> Item(" + description + ") -> MRP : <b>" + mrp + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(grnQuantity) == ((CommonActions.convertStringToDouble(itemDetails.get("PAID QTY")) - CommonActions.convertStringToDouble(itemDetails.get("PENDING PAID QTY")))), "Validated Purchase GRN -> Item(" + description + ") -> GRN Qty : <b>" + grnQuantity + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(freeQuantity) == ((CommonActions.convertStringToDouble(itemDetails.get("FREE QTY")) - CommonActions.convertStringToDouble(itemDetails.get("PENDING FREE QTY")))), "Validated Purchase GRN -> Item(" + description + ") -> Free Qty : <b>" + freeQuantity + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(rate) == CommonActions.convertStringToDouble(itemDetails.get("RATE")), "Validated Purchase GRN -> Item(" + description + ") -> Rate : <b>" + rate + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(taxPercentage) == CommonActions.convertStringToDouble(itemDetails.get("TAX")), "Validated Purchase GRN -> Item(" + description + ") -> Tax% : <b>" + taxPercentage + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(discount) == CommonActions.convertStringToDouble(itemDetails.get("DISCOUNT")), "Validated Purchase GRN -> Item(" + description + ") -> Discount : <b>" + discount + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(amount) == CommonActions.convertStringToDouble(itemDetails.get("NET AMOUNT")), "Validated Purchase GRN -> Item(" + description + ") -> Total Amount : <b>" + amount + "</b>");
                    m_assert.assertTrue(remark.equals("TEST"), "Validated Purchase GRN -> Item(" + description + ") -> Remark : <b>" + remark + "</b>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validatePOLinkItemDetails(HashMap<String, String> itemDetails) {

        try {
            ArrayList<String> sHeaderValue = new ArrayList<>();
            for (WebElement eHeader : oPage_Purchase.list_poLinkTableHeaderPurchaseOrder) {
                String value = Cls_Generic_Methods.getTextInElement(eHeader);
                sHeaderValue.add(value);
            }

            for (WebElement row : oPage_Purchase.list_poLinkTableRowPurchaseOrder) {
                String description = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Description")));
                if (description.contains(itemDetails.get("DESCRIPTION"))) {
                    String hsn = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("HSN")));
                    String paidQuantity = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Qty.")));
                    String freeQuantity = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Free Qty.")));
                    String taxPercentage = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Tax%")));
                    String rate = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Rate")));
                    String discount = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Discount")));
                    String amount = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Amount")));
                    String remark = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Remark")));

                    m_assert.assertTrue(description.contains(itemDetails.get("DESCRIPTION")), "Validated PO Link-> Item Description : <b>" + description + "</b>");
                    if (!itemDetails.get("HSN").isEmpty()) {
                        m_assert.assertTrue(hsn.equals(itemDetails.get("HSN")), "Validated PO Link-> Item HSN : <b>" + hsn + "</b>");
                    }
                    m_assert.assertTrue(CommonActions.convertStringToDouble(paidQuantity) == CommonActions.convertStringToDouble(itemDetails.get("PAID QTY")), "Validated PO Link-> Item Paid Qty : <b>" + paidQuantity + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(freeQuantity) == CommonActions.convertStringToDouble(itemDetails.get("FREE QTY")), "Validated PO Link-> Item Free Qty : <b>" + freeQuantity + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(taxPercentage) == CommonActions.convertStringToDouble(itemDetails.get("TAX")), "Validated PO Link-> Item Tax Percentage : <b>" + taxPercentage + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(rate) == CommonActions.convertStringToDouble(itemDetails.get("RATE")), "Validated PO Link-> Item Rate : <b>" + rate + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(discount) == CommonActions.convertStringToDouble(itemDetails.get("DISCOUNT")), "Validated PO Link-> Item Discount : <b>" + discount + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(amount) == CommonActions.convertStringToDouble(itemDetails.get("NET AMOUNT")), "Validated PO Link-> Item Net Amount : <b>" + amount + "</b>");
                    m_assert.assertTrue(remark.equals("TEST"), "Validated PO Link-> Item Remark : <b>TEST</b>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validateCreateGRNItemDetails(HashMap<String, String> itemDetails, String appliedDiscount,
                                              boolean partiallyComplete) {

        try {
            double paidQty = CommonActions.convertStringToDouble(itemDetails.get("PAID QTY"));
            double freeQty = CommonActions.convertStringToDouble(itemDetails.get("FREE QTY"));
            double pendingPaidQty = CommonActions.convertStringToDouble(itemDetails.get("PENDING PAID QTY"));
            double pendingFreeQty = CommonActions.convertStringToDouble(itemDetails.get("PENDING FREE QTY"));
            double partialQty = paidQty / 2;
            double partialFreeQty = freeQty - 1;

            String mrp = getRandomDecimal(3);
            String batchNo = "PO-" + getRandomNumber();

            ArrayList<String> sHeaderValue = new ArrayList<>();
            for (WebElement eHeader : oPage_Purchase.list_createGRNTableHeaderPurchaseOrder) {
                String value = Cls_Generic_Methods.getTextInElement(eHeader);
                sHeaderValue.add(value);
            }

            for (WebElement row : oPage_Purchase.list_createGRNTableRowPurchaseOrder) {
                String description = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Description")));
                if (description.contains(itemDetails.get("DESCRIPTION"))) {
                    String rate = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Rate")));
                    String poQuantity = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("PO Qty.")));
                    String pendingQuantity = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Pending Qty.")).findElement(By.xpath(".//span")));
                    String taxPercentage = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Tax")));
                    String expiry = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Expiry")));
                    String paidQuantity = Cls_Generic_Methods.getElementAttribute(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Paid Qty.")).findElement(By.xpath(".//input")), "value");
                    String freeQuantity = Cls_Generic_Methods.getElementAttribute(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Free Qty.")).findElement(By.xpath(".//input")), "value");
                    String discount = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Discount")));
                    String amount = Cls_Generic_Methods.getElementAttribute(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Net Amount")).findElement(By.xpath(".//input")), "value");
                    String subStore = Cls_Generic_Methods.getSelectedValue(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Sub Store")).findElement(By.xpath(".//select")));
                    String remark = Cls_Generic_Methods.getElementAttribute(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Remarks")).findElement(By.xpath(".//input")), "value");

                    m_assert.assertTrue(description.contains(itemDetails.get("DESCRIPTION")), "Validated Create GRN -> Item Description : <b>" + description + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(rate) == CommonActions.convertStringToDouble(itemDetails.get("RATE")), "Validated Create GRN for " + description + "-> Item Rate : <b>" + rate + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(poQuantity) == (paidQty + freeQty), "Validated Create GRN " + description + "-> Total PO Qty : <b>" + poQuantity + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(pendingQuantity) == CommonActions.convertStringToDouble(itemDetails.get("PENDING PAID QTY")), "Validated Create GRN " + description + "-> Pending Qty : <b>" + pendingQuantity + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(taxPercentage) == CommonActions.convertStringToDouble(itemDetails.get("TAX")), "Validated Create GRN " + description + "-> Tax Percentage : <b>" + taxPercentage + "</b>");


                    m_assert.assertTrue(CommonActions.convertStringToDouble(paidQuantity) == CommonActions.convertStringToDouble(itemDetails.get("PENDING PAID QTY")), "Validated Create GRN " + description + "-> Item Paid Qty : <b>" + paidQty + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(freeQuantity) == CommonActions.convertStringToDouble(itemDetails.get("PENDING FREE QTY")), "Validated Create GRN " + description + "-> Item Free Qty : <b>" + freeQuantity + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(discount) == CommonActions.convertStringToDouble(itemDetails.get("DISCOUNT")), "Validated Create GRN " + description + "-> Item Discount : <b>" + discount + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(amount) == CommonActions.convertStringToDouble(itemDetails.get("NET AMOUNT")), "Validated Create GRN " + description + "-> Item Net Amount : <b>" + amount + "</b>");
                   // m_assert.assertTrue(subStore.equals("Default"), "Validated Create GRN -> Sub Store : <b>" + subStore + "</b>");
                    m_assert.assertTrue(remark.equals("TEST"), "Validated Create GRN -> Item Remark : <b>TEST</b>");


                    //Validate Entering more qty
                    Cls_Generic_Methods.clearValuesInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Paid Qty.")).findElement(By.xpath(".//input")));
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Paid Qty.")).findElement(By.xpath(".//input")), String.valueOf(pendingPaidQty + 1)), "Entering value greater than paid qty");
                    Cls_Generic_Methods.clickElement(oPage_Purchase.input_TransactionNote);
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.label_paidQtyError, 10), "Validated displayed error message : " + Cls_Generic_Methods.getTextInElement(oPage_Purchase.label_paidQtyError));

                    //Validate Entering less free qty
                    Cls_Generic_Methods.clearValuesInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Free Qty.")).findElement(By.xpath(".//input")));
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Free Qty.")).findElement(By.xpath(".//input")), String.valueOf(pendingFreeQty - 1)), "Entering value lesser than free qty");
                    Cls_Generic_Methods.clickElement(oPage_Purchase.input_TransactionNote);
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.label_freeQtyError, 10), "Validated displayed error message : " + Cls_Generic_Methods.getTextInElement(oPage_Purchase.label_freeQtyError));

                    if (partiallyComplete) {
                        Cls_Generic_Methods.clearValuesInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Paid Qty.")).findElement(By.xpath(".//input")));
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Paid Qty.")).findElement(By.xpath(".//input")), String.valueOf(partialQty)), "Entered " + partialQty + " in Paid qty for item " + description);

                        Cls_Generic_Methods.clearValuesInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Free Qty.")).findElement(By.xpath(".//input")));
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Free Qty.")).findElement(By.xpath(".//input")), String.valueOf(partialFreeQty)), "Entered " + partialFreeQty + " in Free qty for item " + description);

                        pendingPaidQty = paidQty - partialQty;
                        Cls_Generic_Methods.customWait();
                        pendingFreeQty = freeQty - partialFreeQty;
                        Cls_Generic_Methods.clickElement(oPage_Purchase.input_TransactionNote);

                        //Calculation
                        discount = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Discount")));
                        amount = Cls_Generic_Methods.getElementAttribute(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Net Amount")).findElement(By.xpath(".//input")), "value");

                        //GROSS
                        double expectedGross = (paidQty - pendingPaidQty) * CommonActions.convertStringToDouble(itemDetails.get("RATE"));
                        totalGrossAmount = totalGrossAmount - CommonActions.convertStringToDouble(itemDetails.get("GROSS"));
                        totalGrossAmount = totalGrossAmount + expectedGross;

                        //DISCOUNT
                        String discountInput = appliedDiscount.split(" ")[0];
                        String discountType = appliedDiscount.split(" ")[1];

                        double itemDiscount = 0;
                        if (discountType.equals("%")) {
                            itemDiscount = expectedGross * (CommonActions.convertStringToDouble(discountInput) / 100);
                        } else {
                            itemDiscount = CommonActions.convertStringToDouble(discountInput);
                        }
                        m_assert.assertTrue(CommonActions.convertStringToDouble(discount) == formatDecimalNumber(itemDiscount), "Validated Create GRN -> Discount after changing the quantity : <b>" + discount + "</b>");
                        totalDiscount = totalDiscount - (CommonActions.convertStringToDouble(itemDetails.get("DISCOUNT")));
                        totalDiscount = totalDiscount + itemDiscount;

                        //TAX
                        double taxPercent = CommonActions.convertStringToDouble(itemDetails.get("TAX"));
                        double dTaxAmount = (taxPercent / 100) * (expectedGross - formatDecimalNumber(itemDiscount));

                        if (taxPercent == 5) {
                            totalGST5Amount =dTaxAmount;
                        } else {
                            totalGST28Amount =dTaxAmount;
                        }

                        //NET AMOUNT
                        double dItemNetAmount = expectedGross + dTaxAmount - formatDecimalNumber(itemDiscount);
                        totalNetAmount = totalNetAmount - (CommonActions.convertStringToDouble(itemDetails.get("NET AMOUNT")));
                        totalNetAmount = totalNetAmount + dItemNetAmount;
                        m_assert.assertTrue(CommonActions.convertStringToDouble(amount) == formatDecimalNumber(dItemNetAmount), "Validated Create GRN -> Item Net Amount after changing the quantity : <b>" + amount + "</b>");


                        itemDetails.put("GROSS", String.valueOf(expectedGross));
                        itemDetails.put("DISCOUNT", String.valueOf(formatDecimalNumber(itemDiscount)));
                        itemDetails.put("TAX AMOUNT", String.valueOf(formatDecimalNumber(dTaxAmount)));
                        itemDetails.put("NET AMOUNT", String.valueOf(formatDecimalNumber(dItemNetAmount)));


                    } else {
                        Cls_Generic_Methods.clearValuesInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Paid Qty.")).findElement(By.xpath(".//input")));
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Paid Qty.")).findElement(By.xpath(".//input")), String.valueOf(pendingPaidQty)), "Entered " + pendingPaidQty + " in Paid qty for item " + description);

                        Cls_Generic_Methods.clearValuesInElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Free Qty.")).findElement(By.xpath(".//input")));
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Free Qty.")).findElement(By.xpath(".//input")), String.valueOf(pendingFreeQty)), "Entered " + pendingFreeQty + " in Free qty for item " + description);
                        pendingPaidQty = 0;
                        pendingFreeQty = 0;
                    }

                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("MRP")).findElement(By.xpath(".//input")), mrp), "Entered <b>" + mrp + "</b> in MRP for Item " + description);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Batch No.")).findElement(By.xpath(".//input")), batchNo), "Entered <b>" + batchNo + "</b> in Batch No for Item " + description);
                    if (!itemDetails.get("EXPIRY").isEmpty()) {
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(row.findElements(By.xpath("./td")).get(sHeaderValue.indexOf("Expiry")).findElement(By.xpath(".//input")), itemDetails.get("EXPIRY")), "Entered Item Expiry Date as : <b>" + itemDetails.get("EXPIRY") + "</b>");
                    }
                }
            }

            itemDetails.put("BATCH", batchNo);
            itemDetails.put("MRP", mrp);

            itemDetails.put("PENDING PAID QTY", String.valueOf(pendingPaidQty));
            itemDetails.put("PENDING FREE QTY", String.valueOf(pendingFreeQty));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetCounter() {
        totalGrossAmount = 0;
        totalDiscount = 0;
        amountPaid = 0;
        totalGST5Amount = 0;
        totalGST28Amount = 0;
        totalNetAmount = 0;
        itemDetails1 = new HashMap<>();
        itemDetails2 = new HashMap<>();

    }


}
