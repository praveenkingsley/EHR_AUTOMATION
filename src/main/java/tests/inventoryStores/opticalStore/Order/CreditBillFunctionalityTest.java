package tests.inventoryStores.opticalStore.Order;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.common_tabs.Page_RefractionTab;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.store.OpticalStore.Page_Sale;
import pages.store.OpticalStore.Page_SalesOrder;
import pages.store.PharmacyStore.Items.Page_Master;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Pattern;

public class CreditBillFunctionalityTest extends TestBase {

    public String expectedLoggedInUser = EHR_Data.user_PRAkashTest;

    public static String sOpticalStore = "OpticalStore- Optical";
    public static String stockableItemName1 = "SalesOrderTest1";
    public static String stockableItemName2 = "SALES-ORDER-AUTO-TEST2";
    String vendorName = "Supplier ABC";

    HashMap<String, String> itemDetails1;
    HashMap<String, String> itemDetails2;

    static String modeOfPayment = "Cash";
    String salesOrderCreatedAt;
    static String sTxnDate = "";
    static String sTxnTime = "";
    String salesOrderId;
    String salesBillNumber;
    String patientId;
    String mrNo;
    static String sAdvanceReason = "Advance1";
    static String sAdvanceAmount = "12";
    static String sPartialPayment = "20";
    String sBalancePending;
    double settledAmount= 0;

    public static String sReadyDate = "";

    public static String sDeliveryDate = "";
    String claimProcessor = "Medi Assist India TPA Private Limited (TPA)";
    String orderType = "Credit";
    static String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    static Model_Patient myPatient;

    String concatPatientFullName;

    //GLASS PRESCRIPTION
    public static String sDistantSph = "+0.25";
    public static String sDistantCyl = "+0.25";
    public static String sDistantAxis = "10";
    public static String sDistantVision = "3/60";
    public static String sAddSph = "3.25";
    public static String sNearSph = "";
    public static String sNearCyl = "";
    public static String sNearAxis = "";
    public static String sNearVision = "N8";
    public static String sFrameMaterial = "Shell";
    public static String sIPD = "10";
    public static String sLensMaterial = "Mineral";
    public static String sLensTint = "White";
    public static String sTypeOfLens = "Bifocal";
    public static String sDia = "12";
    public static String sSize = "11";
    public static String sFittingHeight = "14";
    public static String sPrismBase = "13";
    public static String sGlassPrescriptionComment = "Glass Prescription Advised";
    String offerCode = "";
    String offerPercentage = "";

    Page_SalesOrder oPage_SalesOrder;
    Page_CommonElements oPage_CommonElements;
    Page_Sale oPage_Sale;
    Page_Master oPage_Master;

    //CALCULATIONS
    double totalGrossAmount = 0;
    double totalDiscount = 0;
    double totalOffer = 0;
    double totalTaxableAmount = 0;
    double totalGST5Amount = 0;
    double totalGST18Amount = 0;
    double totalNetAmount = 0;
    double remainingBalance = 0;
    double totalAdvancePaid = 0;

    String item1Qty = "2";
    String item2Qty = "4";
    String item1Discount = "21 %";
    String item2Discount = "12 ₹";
    boolean homeDeliveryStatus = true;
    boolean fittingStatus = true;
    String currentStatus;
    boolean bOneItem = true;


    @Test(enabled = true, description = "Validate Credit Bill Creation in sales order page for the pending delivery type")
    public void validateCreditBillCreationInSalesOrder() {

        try {
            //Opening Optical Store
            oPage_CommonElements = new Page_CommonElements(driver);
            oPage_Sale = new Page_Sale(driver);
            oPage_Master = new Page_Master(driver);
            oPage_SalesOrder = new Page_SalesOrder(driver);

            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectStoreOnApp(sOpticalStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 8);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

            //Opening the Sale Transaction Page
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 8);

            itemDetails1 = getStockableItemDetails(stockableItemName1);
            itemDetails2 = getStockableItemDetails(stockableItemName2);
            bOneItem=false;


            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_addNewButtonInOrder, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addNewButtonInOrder), "Sales Order New Button Clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.list_PatientSearch, 16);

            try {
                //Adding patient for Optical Sales Order
                createNewPatientInSalesOrder();
                //Validate Header
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_headerSalesOrder, 20);
                String headerValue = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_headerSalesOrder);
                m_assert.assertTrue(headerValue.contains(concatPatientFullName), "Validated Displayed Sales Order Header -><b>" + headerValue + "</b>");

                //Select Credit option from Invoice type
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_SalesOrder.radioButton_CreditOption),"Credit option is selected in the sales order form");
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.option_PayerTypeOption);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.list_payerDropdown.get(5)),"TPA type is selected from the drop down");
                Cls_Generic_Methods.customWait(6);
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.option_ContactSelectDropdown);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.list_payerDropdown.get(2), 12);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.list_payerDropdown.get(2)),"TPA Payer is selected from the drop down");
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.option_insurancePayerTypeOption);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.list_payerDropdown.get(1)),"Insurance Payer is selected from the drop down");
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.option_CorporatePayerTypeOption);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.list_payerDropdown.get(2)),"Corporate Payer is selected from the drop down");

                //Adding Stockable Item
                addItemFromListCreateSalesOrder(true, stockableItemName2);

                //Validate Remove Functionality
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_removeFromList, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_removeFromList), "Clicked Remove Item from list Button");
                Cls_Generic_Methods.customWait();
                boolean bRemoveStatus = Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.button_removeFromList);
                m_assert.assertTrue(bRemoveStatus, "Validated Remove Item Functionality ->Item Not Removed if only one item is selected");

                addItemFromListCreateSalesOrder(true, stockableItemName1);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_removeFromList, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_removeFromList), "Clicked Remove Item from list Button");
                Cls_Generic_Methods.customWait();
                if (oPage_SalesOrder.list_itemNameInTableCreateSalesOrder.size() == 1) {
                    m_assert.assertFalse(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.list_itemNameInTableCreateSalesOrder.get(0)).equals(stockableItemName2), "Validated Remove Item Functionality ->Item Not Removed");
                } else {
                    m_assert.assertFatal("Unable to validate Remove Item Functionality");
                }

                //Adding Removed Item Back
                addItemFromListCreateSalesOrder(true, stockableItemName2);
                Cls_Generic_Methods.customWait();

                //Enter Doctor Name
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_doctorName);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_doctorName, expectedLoggedInUser), "Entered <b>" + expectedLoggedInUser + "</b> in Doctor Name");

                //Set Date And Time
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.text_TxnDate);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_todayBillDate, 3);
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_todayBillDate);
                sTxnDate = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_TxnDate, "value").trim();
                m_assert.assertInfo("Selected Sales Order Date as : <b>" + sTxnDate + "</b>");
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_doctorName);
                sTxnTime = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderTime, "value");
                salesOrderCreatedAt = getCurrentDateTime();

                //Fill Glass Prescription
                fillGlassPrescription();

                //Validating Required field and tax,Taxable and Balance details
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_saveChangesOnSalesOrder);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.label_QtyRequired, 12);
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.label_QtyRequired, "class").contains("error"), "Quantity Required Error Displayed");


                //Validate Offer
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addOffer), "Clicked <b>Add Offer</b> Button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.header_showOffer, 10);
                offerCode = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_offerCode);
                offerPercentage = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_offerDiscountPercentage);
                String offerExpiry = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_offerValidTill);
                m_assert.assertTrue(checkIfDateIsFuture(offerExpiry), "Validated Offer Valid Till -> <b>" + offerExpiry + "</b>");
                Cls_Generic_Methods.clickElement(driver, oPage_SalesOrder.radio_selectOffer);
                Cls_Generic_Methods.clickElement(driver, oPage_SalesOrder.button_selectOffer);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.label_selectedOffer, 10);

                String labelValue = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.label_selectedOffer);
                m_assert.assertTrue(labelValue.contains(offerCode) && labelValue.contains(offerPercentage), "Offer : <b>" + offerCode + "</b> is selected -> Offer Discount Percentage : <b>" + offerPercentage + "</b>");

                fillStockableItemDetailsCreateSalesOrder(itemDetails1, item1Qty, item1Discount);
                fillStockableItemDetailsCreateSalesOrder(itemDetails2, item2Qty, item2Discount);

                validateTotalCalculation();
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_SaveChanges);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_closeModalOfSalesOrder,10);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_SalesOrder.button_closeModalOfSalesOrder);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_addNewButtonInOrder, 10);
                boolean bSalesOrderFound = false;
                String sDate = sTxnDate.replace("/", "-");
                String sTime = sTxnTime.replace(" ", "");

                for (WebElement eSalesOrder : oPage_SalesOrder.list_namesofSalesOrder) {
                    String eSalesOrderName = Cls_Generic_Methods.getTextInElement(eSalesOrder);
                    if (eSalesOrderName.contains(sDate) && eSalesOrderName.contains(sTime) && eSalesOrderName.contains(concatPatientFullName)) {
                        salesOrderId = Cls_Generic_Methods.getTextInElement(eSalesOrder.findElement(By.xpath("./td[2]"))).split("\n")[1];
                        Cls_Generic_Methods.clickElement(eSalesOrder);
                        bSalesOrderFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(bSalesOrderFound, "Sales order created -> ID : <b>" + salesOrderId + "</b>");
                Cls_Generic_Methods.customWait();

                //Validate Created Table List Values
                validateRHSSalesOrder();
                Cls_Generic_Methods.customWait(3);
                validateOpticalOrder();
                selectSalesOrder(salesOrderId);
                Cls_Generic_Methods.customWait();
                createBillSalesOrder();
                Cls_Generic_Methods.customWait(3);
                validateSettlePage();
                //Checking Stock
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 8);

                HashMap<String, String> itemDetails1AfterSales = getStockableItemDetails(stockableItemName1);
                HashMap<String, String> itemDetails2AfterSales = getStockableItemDetails(stockableItemName2);

                double stockAfterSales = CommonActions.convertStringToDouble(itemDetails1AfterSales.get("STOCK"));
                double stockBeforeSales = CommonActions.convertStringToDouble(itemDetails1.get("STOCK"));
                m_assert.assertTrue(stockAfterSales == stockBeforeSales - (CommonActions.convertStringToDouble(item1Qty)), "Validated Item (" + stockableItemName1 + ") Stock reduced from <b>" + stockBeforeSales + "</b> to <b>" + stockAfterSales + "</b>");

                stockAfterSales = CommonActions.convertStringToDouble(itemDetails2AfterSales.get("STOCK"));
                stockBeforeSales = CommonActions.convertStringToDouble(itemDetails2.get("STOCK"));
                m_assert.assertTrue(stockAfterSales == stockBeforeSales - (CommonActions.convertStringToDouble(item2Qty)), "Validated Item (" + stockableItemName2 + ") Stock reduced from <b>" + stockBeforeSales + "</b> to <b>" + stockAfterSales + "</b>");

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

    @Test(enabled = true, description = "Validate Credit Bill Creation in sales order page for the Completed delivery type")
    public void validateCreditBillForCompletedType() {
        try {
            //Opening Optical Store
            oPage_CommonElements = new Page_CommonElements(driver);
            oPage_Sale = new Page_Sale(driver);
            oPage_Master = new Page_Master(driver);
            oPage_SalesOrder = new Page_SalesOrder(driver);

            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectStoreOnApp(sOpticalStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 8);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

            //Opening the Sale Transaction Page
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 8);

            itemDetails1 = getStockableItemDetails(stockableItemName1);
            itemDetails2 = getStockableItemDetails(stockableItemName2);
            bOneItem=false;


            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_addNewButtonInOrder, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addNewButtonInOrder), "Sales Order New Button Clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.list_PatientSearch, 16);

            try {
                //Adding patient for Optical Sales Order
                createNewPatientInSalesOrder();
                //Validate Header
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_headerSalesOrder, 20);
                String headerValue = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_headerSalesOrder);
                m_assert.assertTrue(headerValue.contains(concatPatientFullName), "Validated Displayed Sales Order Header -><b>" + headerValue + "</b>");

                //Select Credit option from Invoice type
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_SalesOrder.radioButton_CreditOption),"Credit option is selected in the sales order form");
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.option_PayerTypeOption);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.list_payerDropdown.get(5)),"TPA type is selected from the drop down");
                Cls_Generic_Methods.customWait(6);
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.option_ContactSelectDropdown);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.list_payerDropdown.get(2), 12);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.list_payerDropdown.get(2)),"TPA Payer is selected from the drop down");
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.option_insurancePayerTypeOption);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.list_payerDropdown.get(1)),"Insurance Payer is selected from the drop down");
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.option_CorporatePayerTypeOption);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.list_payerDropdown.get(2)),"Corporate Payer is selected from the drop down");

                //Adding Stockable Item
                addItemFromListCreateSalesOrder(true, stockableItemName2);

                //Validate Remove Functionality
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_removeFromList, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_removeFromList), "Clicked Remove Item from list Button");
                Cls_Generic_Methods.customWait();
                boolean bRemoveStatus = Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.button_removeFromList);
                m_assert.assertTrue(bRemoveStatus, "Validated Remove Item Functionality ->Item Not Removed if only one item is selected");

                addItemFromListCreateSalesOrder(true, stockableItemName1);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_removeFromList, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_removeFromList), "Clicked Remove Item from list Button");
                Cls_Generic_Methods.customWait();
                if (oPage_SalesOrder.list_itemNameInTableCreateSalesOrder.size() == 1) {
                    m_assert.assertFalse(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.list_itemNameInTableCreateSalesOrder.get(0)).equals(stockableItemName2), "Validated Remove Item Functionality ->Item Not Removed");
                } else {
                    m_assert.assertFatal("Unable to validate Remove Item Functionality");
                }

                //Adding Removed Item Back
                addItemFromListCreateSalesOrder(true, stockableItemName2);
                Cls_Generic_Methods.customWait();

                //Enter Doctor Name
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_doctorName);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_doctorName, expectedLoggedInUser), "Entered <b>" + expectedLoggedInUser + "</b> in Doctor Name");

                //Set Date And Time
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.text_TxnDate);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_todayBillDate, 3);
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_todayBillDate);
                sTxnDate = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_TxnDate, "value").trim();
                m_assert.assertInfo("Selected Sales Order Date as : <b>" + sTxnDate + "</b>");
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_doctorName);
                sTxnTime = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderTime, "value");
                salesOrderCreatedAt = getCurrentDateTime();

                //Fill Glass Prescription
                fillGlassPrescription();

                //Validating Required field and tax,Taxable and Balance details
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_saveChangesOnSalesOrder);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.label_QtyRequired, 12);
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.label_QtyRequired, "class").contains("error"), "Quantity Required Error Displayed");


                //Validate Offer
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addOffer), "Clicked <b>Add Offer</b> Button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.header_showOffer, 10);
                offerCode = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_offerCode);
                offerPercentage = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_offerDiscountPercentage);
                String offerExpiry = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_offerValidTill);
                m_assert.assertTrue(checkIfDateIsFuture(offerExpiry), "Validated Offer Valid Till -> <b>" + offerExpiry + "</b>");
                Cls_Generic_Methods.clickElement(driver, oPage_SalesOrder.radio_selectOffer);
                Cls_Generic_Methods.clickElement(driver, oPage_SalesOrder.button_selectOffer);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.label_selectedOffer, 10);

                String labelValue = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.label_selectedOffer);
                m_assert.assertTrue(labelValue.contains(offerCode) && labelValue.contains(offerPercentage), "Offer : <b>" + offerCode + "</b> is selected -> Offer Discount Percentage : <b>" + offerPercentage + "</b>");

                fillStockableItemDetailsCreateSalesOrder(itemDetails1, item1Qty, item1Discount);
                fillStockableItemDetailsCreateSalesOrder(itemDetails2, item2Qty, item2Discount);

                try {
                    //Validating Total Values
                    String actualTotalTaxableAmount = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_totalTaxableAmount, "value");

                    if (totalGST5Amount > 0) {
                        String actualTotalGST5Amount = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_totalGST5Amount, "value");
                        m_assert.assertTrue(formatDecimalNumber(totalGST5Amount) == CommonActions.convertStringToDouble(actualTotalGST5Amount), "Validated GST5 Amount = <b>" + actualTotalGST5Amount + "</b>");
                    }
                    if (totalGST18Amount > 0) {
                        String actualTotalGST18Amount = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_totalGST18Amount, "value");
                        m_assert.assertTrue(formatDecimalNumber(totalGST18Amount) == CommonActions.convertStringToDouble(actualTotalGST18Amount), "Validated GST18 Amount = <b>" + actualTotalGST18Amount + "</b>");
                    }

                    String actualTotalGrossAmount = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_totalGrossAmount, "value");
                    String actualTotalDiscountAmount = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_totalDiscountAmount, "value");
                    String actualTotalOfferAmount = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_totalOfferAmount, "value");
                    String actualTotalNetAmount = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_totalNetAmount, "value");


                    m_assert.assertTrue(formatDecimalNumber(totalTaxableAmount) == CommonActions.convertStringToDouble(actualTotalTaxableAmount), "Validated Total Taxable Amount = <b>" + actualTotalTaxableAmount + "</b>");

                    m_assert.assertTrue(formatDecimalNumber(totalGrossAmount) == CommonActions.convertStringToDouble(actualTotalGrossAmount), "Validated Total Gross Amount = <b>" + actualTotalGrossAmount + "</b>");
                    m_assert.assertTrue(formatDecimalNumber(totalDiscount) == CommonActions.convertStringToDouble(actualTotalDiscountAmount), "Validated Total Discount Amount = <b>" + actualTotalDiscountAmount + "</b>");
                    m_assert.assertTrue(formatDecimalNumber(totalOffer) == CommonActions.convertStringToDouble(actualTotalOfferAmount), "Validated Total Offer Amount = <b>" + actualTotalOfferAmount + "</b>");
                    m_assert.assertTrue(formatDecimalNumber(totalNetAmount) == CommonActions.convertStringToDouble(actualTotalNetAmount), "Validated Total Net Amount = <b>" + actualTotalNetAmount + "</b>");

                    //Completed Delivery Option selected
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_SalesOrder.radioBtn_deliveryCompleted),"Completed Delivery radio button is selected");
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_SalesOrder.select_modeOfPaymentReceived.get(0), modeOfPayment), "Selected mode of payment as = <b>" + modeOfPayment + "</b>");
                    Cls_Generic_Methods.clearValuesInElement(oPage_SalesOrder.input_partialPaymentReceived);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_partialPaymentReceived, sPartialPayment), "Entered PartialPayment amount = <b> " + sPartialPayment + "</b>");
                    settledAmount = CommonActions.convertStringToDouble(sPartialPayment);

                    String sBalancePending1 = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_balancePendingAmount,"value");
                    Cls_Generic_Methods.clearValuesInElement(oPage_SalesOrder.input_paymentPendingAmount);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_paymentPendingAmount, sBalancePending1), "Amount pending against payer = <b>" + sBalancePending1 + "</b>");

                    Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_SaveChanges);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_closeModalOfSalesOrder,10);
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_SalesOrder.button_closeModalOfSalesOrder);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_addNewButtonInOrder, 10);
                    boolean bSalesOrderFound = false;
                    String sDate = sTxnDate.replace("/", "-");
                    String sTime = sTxnTime.replace(" ", "");

                    for (WebElement eSalesOrder : oPage_SalesOrder.list_namesofSalesOrder) {
                        String eSalesOrderName = Cls_Generic_Methods.getTextInElement(eSalesOrder);
                        if (eSalesOrderName.contains(sDate) && eSalesOrderName.contains(sTime) && eSalesOrderName.contains(concatPatientFullName)) {
                            salesOrderId = Cls_Generic_Methods.getTextInElement(eSalesOrder.findElement(By.xpath("./td[2]"))).split("\n")[1];
                            Cls_Generic_Methods.clickElement(eSalesOrder);
                            bSalesOrderFound = true;
                            break;
                        }
                    }
                    m_assert.assertTrue(bSalesOrderFound, "Sales order created -> ID : <b>" + salesOrderId + "</b>");
                    Cls_Generic_Methods.customWait();

                    //Validate Created Table List Values
                    String orderStatusInTable = getTableValueCreatedSalesOrder(salesOrderId, "Order Status").split("\n")[0];
                    m_assert.assertTrue(true, "Validated Order Status : <b>" + orderStatusInTable + "</b> in created sales order list");
                    //Sales order Rhs validation
                    //Validate Print and Email
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_printOrder,10);
                    m_assert.assertTrue(validatePrintPageOpened(oPage_SalesOrder.button_printOrder),"Validated Sales Order Print Page Opened");
                    m_assert.assertTrue(validatePrintPageOpened(oPage_SalesOrder.button_printInvoice),"Validated Sales Order Print Page Opened");
                    m_assert.assertTrue(validateEmail(oPage_SalesOrder.button_emailOrder,"[AUTO TEST] SALES ORDER PRINT"),"Validated Sales Order Email Sent");
                    Cls_Generic_Methods.customWait(5);
                    m_assert.assertTrue(validateEmail(oPage_SalesOrder.button_emailInvoice,"[AUTO TEST] SALES INVOICE PRINT"),"Validated Email Sent for Sales Invoice");
                    Cls_Generic_Methods.customWait(3);

                    sDeliveryDate = Cls_Generic_Methods.getTodayDate("dd/MM/yyyy");
                    m_assert.assertTrue(concatPatientFullName.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_Patient)), "Validated Sales Order RHS -> Patient name : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_Patient) + "</b>");
                    m_assert.assertTrue(expectedLoggedInUser.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsDoctorName)), "Validated Sales Order RHS -> Doctor name : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsDoctorName) + "</b>");
                    m_assert.assertTrue(orderType.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_orderType)), "Validated Sales Order RHS -> Order Type : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_orderType) + "</b>");
                    m_assert.assertTrue(claimProcessor.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_claimProcessorName)), "Validated Sales Order RHS -> ClaimProcessorName : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_claimProcessorName) + "</b>");
                    m_assert.assertTrue(salesOrderId.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsOrderNumber)), "Validated Sales Order RHS -> Order Number : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsOrderNumber) + "</b>");
                    m_assert.assertTrue(sDeliveryDate.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsDeliveryDate)), "Validated Sales Order RHS -> Delivery Date : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsDeliveryDate) + "</b>");
                    sBalancePending = (Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_PaymentPending));
                    m_assert.assertInfo("Balance pending against payer :<b>"+ sBalancePending + "</b>");
                    patientId = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsPatientId);
                    String homeDelivery = "";
                    if (homeDeliveryStatus) {
                        homeDelivery = "Normal";
                    }
                    m_assert.assertTrue(homeDelivery.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsHomeDelivery)), "Validated Sales Order RHS -> Home Delivery Status : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsHomeDelivery) + "</b>");

                    //RHS TABLE VALIDATION *ID-Item Detail

                    validatedSalesOrderRhsItemDetails(itemDetails1);

                    if (!bOneItem) {
                        validatedSalesOrderRhsItemDetails(itemDetails2);
                    }

                    Cls_Generic_Methods.customWait(3);
                    //settling the pending amount by settle page
                    validateSettlePage();
                    //Checking Stock
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 8);

                    HashMap<String, String> itemDetails1AfterSales = getStockableItemDetails(stockableItemName1);
                    HashMap<String, String> itemDetails2AfterSales = getStockableItemDetails(stockableItemName2);

                    double stockAfterSales = CommonActions.convertStringToDouble(itemDetails1AfterSales.get("STOCK"));
                    double stockBeforeSales = CommonActions.convertStringToDouble(itemDetails1.get("STOCK"));
                    m_assert.assertTrue(stockAfterSales == stockBeforeSales - (CommonActions.convertStringToDouble(item1Qty)), "Validated Item (" + stockableItemName1 + ") Stock reduced from <b>" + stockBeforeSales + "</b> to <b>" + stockAfterSales + "</b>");

                    stockAfterSales = CommonActions.convertStringToDouble(itemDetails2AfterSales.get("STOCK"));
                    stockBeforeSales = CommonActions.convertStringToDouble(itemDetails2.get("STOCK"));
                    m_assert.assertTrue(stockAfterSales == stockBeforeSales - (CommonActions.convertStringToDouble(item2Qty)), "Validated Item (" + stockableItemName2 + ") Stock reduced from <b>" + stockBeforeSales + "</b> to <b>" + stockAfterSales + "</b>");

                    Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal(" " + e);
                }

            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
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

    private void fillGlassPrescription() {

        Page_RefractionTab oPage_RefractionTab = new Page_RefractionTab(driver);

        try {
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_fillGlassPrescription), "Clicked Fill button in Glass Prescription");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.btn_rightEqualLeftFillRefraction, 10);
            m_assert.assertTrue(fillRefractionTable(), "Validated Fill functionality in Glass Prescription");
            Cls_Generic_Methods.clickElementByJS(driver, oPage_RefractionTab.btn_okFillRefraction);

            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_SalesOrder.select_rightSideOfLensGlassPrescription, sTypeOfLens),
                    "Selected <b>" + sTypeOfLens + "</b> in Type of Lens R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.select_rightSideOfIPDGlassPrescription, sIPD),
                    "Entered <b>" + sIPD + "</b> in IPD R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.select_rightSideOfSizeGlassPrescription, sSize),
                    "Entered <b>" + sSize + "</b> in Size R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_SalesOrder.select_rightSideLensOfMaterialGlassPrescription, sLensMaterial),
                    "Selected <b>" + sLensMaterial + "</b> in Lens Material R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_SalesOrder.select_rightSideLensTintGlassPrescription, sLensTint),
                    "Selected <b>" + sLensTint + "</b> in Lens Tint R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_SalesOrder.select_rightSideFrameMaterialGlassPrescription, sFrameMaterial),
                    "Selected <b>" + sFrameMaterial + "</b> in Frame Material R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_rightSideDiaGlassPrescription, sDia),
                    "Entered <b>" + sDia + "</b> in Dia R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_rightSidePrismBaseGlassPrescription, sPrismBase),
                    "Entered <b>" + sPrismBase + "</b> in Prism Base R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_rightSideFittingHeightGlassPrescription, sFittingHeight),
                    "Entered <b>" + sFittingHeight + "</b> in Fitting height R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.textarea_rightSideAdviceCommentGlassPrescription, sGlassPrescriptionComment),
                    "Entered <b>" + sGlassPrescriptionComment + "</b> in Advice R/OD-->Glass Prescription");

            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SalesOrder.btn_leftToRightCopyGlassPrescription),
                    "Clicked Copy Left to Right button in glass prescription");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Glass Prescriptions" + e);
        }
    }

    private static boolean fillRefractionTable() {
        boolean flag = false;
        Page_RefractionTab oPage_RefractionTab = new Page_RefractionTab(driver);
        try {
            clickButtonInFillRefraction(oPage_RefractionTab.list_btnSphDistant, sDistantSph);
            clickButtonInFillRefraction(oPage_RefractionTab.list_btnCylDistant, sDistantCyl);
            clickButtonInFillRefraction(oPage_RefractionTab.list_btnAxisDistant, sDistantAxis);
            clickButtonInFillRefraction(oPage_RefractionTab.list_btnVisionDistant, sDistantVision);
            clickButtonInFillRefraction(oPage_RefractionTab.list_btnSphAdd, sAddSph);
            clickButtonInFillRefraction(oPage_RefractionTab.list_btnVisionNear, sNearVision);
            Cls_Generic_Methods.customWait();
            sNearSph = Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_nearSph, "value");
            sNearCyl = Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_nearCyl, "value");
            sNearAxis = Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_nearAxis, "value");

            double expectedNearSph = CommonActions.convertStringToDouble(sDistantSph) + CommonActions.convertStringToDouble(sAddSph);
            double expectedNearCyl = CommonActions.convertStringToDouble(sNearCyl);
            double expectedNearAxis = CommonActions.convertStringToDouble(sNearAxis);

            m_assert.assertTrue(CommonActions.convertStringToDouble(sNearSph) == expectedNearSph, "Validated Glass Prescription -> Near Sph Value = " + sNearSph);
            m_assert.assertTrue(CommonActions.convertStringToDouble(sNearCyl) == expectedNearCyl, "Validated Glass Prescription -> Near Cyl Value = " + sNearSph);
            m_assert.assertTrue(CommonActions.convertStringToDouble(sNearAxis) == expectedNearAxis, "Validated Glass Prescription -> Near Axis Value = " + sNearSph);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    private static void clickButtonInFillRefraction(List<WebElement> allBtn, String btnToSelect) {
        Page_RefractionTab oPage_RefractionTab = new Page_RefractionTab(driver);

        try {
            if (btnToSelect.charAt(0) == '-') {
                Cls_Generic_Methods.clickElement(driver, oPage_RefractionTab.btn_minus);
            } else if (btnToSelect.charAt(0) == '+') {
                Cls_Generic_Methods.clickElement(driver, oPage_RefractionTab.btn_plus);
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(allBtn.get(0), 10);
            for (WebElement btn : allBtn) {
                String btnValue = Cls_Generic_Methods.getTextInElement(btn);
                String btnType = Cls_Generic_Methods.getElementAttribute(btn, "name").replaceAll("refraction_", "").toUpperCase();
                if (btnValue.contains(btnToSelect)) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(btn), "Clicked <b>" + btnValue + "</b> in " + btnType);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, String> getStockableItemDetails(String itemDescription) {
        String sItemTotalStock = "";
        String itemCode = "";
        String mrp = "";
        String expiryDate = "";
        String taxPercentage = "";
        String batchNo = "";
        String hsnCode = "";
        boolean taxInclusive = false;
        boolean itemFound = false;

        Page_Master oPage_Master = new Page_Master(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        ArrayList<String> lotHeader = new ArrayList<>();
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
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemData),
                                "<b> " + itemDescription + " </b> is present in Item Master List");
                        Cls_Generic_Methods.customWait(5);
                        m_assert.assertInfo("Available stock of item : " + itemDescription + " --> <b>" + itemStock + "</b>");
                        for (WebElement headerValue : oPage_Master.list_lotDetailsTableHeaderList) {
                            String value = Cls_Generic_Methods.getTextInElement(headerValue);
                            lotHeader.add(value);
                        }
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.button_editItem, 15);
                        itemCode = Cls_Generic_Methods.getTextInElement(oPage_Master.list_lotDetailsTableRowValuesList.get(lotHeader.indexOf("Variant Code")));
                        mrp = Cls_Generic_Methods.getTextInElement(oPage_Master.list_lotDetailsTableRowValuesList.get(lotHeader.indexOf("MRP")));
                        expiryDate = Cls_Generic_Methods.getTextInElement(oPage_Master.list_lotDetailsTableRowValuesList.get(lotHeader.indexOf("Expiry")));
                        batchNo = Cls_Generic_Methods.getTextInElement(oPage_Master.list_lotDetailsTableRowValuesList.get(lotHeader.indexOf("Batch No.")));

                        Cls_Generic_Methods.clickElement(oPage_Master.button_editItem);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.header_editItemHeader, 10);
                        taxPercentage = Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_itemPropertiesTaxList).split("-")[1].replaceAll("%", "").trim();
                        hsnCode = Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_itemHsnCode, "value");
                        taxInclusive = Cls_Generic_Methods.radioButtonIsSelected(oPage_ItemMaster.checkBox_taxInclusive);
                        Cls_Generic_Methods.clickElement(driver, oPage_ItemMaster.button_closeItemMasterTemplate);
                        break;
                    }

                }
            }
            if (expiryDate.isBlank()) {
                expiryDate = "N.A";
            }
            if (itemFound) {
                itemDetails.put("ITEM CODE", itemCode);
                itemDetails.put("MRP", mrp);
                itemDetails.put("EXPIRY", expiryDate);
                itemDetails.put("STOCK", sItemTotalStock);
                itemDetails.put("TAX", taxPercentage);
                itemDetails.put("BATCH", batchNo);
                itemDetails.put("DESCRIPTION", itemDescription);
                itemDetails.put("HSN", hsnCode);
                if (taxInclusive) {
                    itemDetails.put("TAX INCLUSIVE", "Yes");
                } else {
                    itemDetails.put("TAX INCLUSIVE", "No");
                }
            } else {
                m_assert.assertFatal("Unable to find item ->" + itemDescription);
            }


        } catch (Exception e) {
            m_assert.assertFatal("Unable to get Item Details -" + itemDetails + "  -->" + e);
            e.printStackTrace();
        }
        return itemDetails;
    }

    public void createNewPatientInSalesOrder() {
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        mrNo = "AUTO_PATIENT_" + getCurrentDateTime();
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.button_addNewPatient, 10);
            Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.button_addNewPatient);

            myPatient = map_PatientsInExcel.get(patientKey);
            concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            // Entering Essential Form Data
            if (!myPatient.getsSALUTATION().isEmpty()) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.select_salutationForPatient, 10);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()),
                        "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
            }

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                    "First Name is entered as - " + myPatient.getsFIRST_NAME());

            Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_middleNameOnPatientRegForm, myPatient.getsMIDDLE_NAME());
            Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_lastNameOnPatientRegForm, myPatient.getsLAST_NAME());

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, myPatient.getsMOBILE_NUMBER()),
                    "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_medicalRecordNumOnPatientRegForm, mrNo), "Medical Record (MR) No is entered as " + mrNo);

            Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm);

        } catch (Exception e) {
            m_assert.assertFatal("Unable to create patient in sales order ->" + e);
            e.printStackTrace();
        }
    }

    private void addItemFromListCreateSalesOrder(boolean stockable, String itemDescription) {
        try {

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_Stockable, 12);

            if (stockable) {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_Stockable), "<b>Stockable</b> button clicked in Sales Order");
            } else {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_nonStockable), "<b>Non Stockable</b> button clicked in Sales Order");
                m_assert.assertTrue(selectByOptions(oPage_SalesOrder.select_vendor, vendorName), "Selected Vendor as <b>" + vendorName + "</b>");
                Cls_Generic_Methods.customWait();
            }

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_description), "Search by <b>Description</b> button clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_searchMedicineNameInDescription, 8);
            Cls_Generic_Methods.clearValuesInElement(oPage_SalesOrder.input_searchMedicineNameInDescription);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_searchMedicineNameInDescription, itemDescription), "Entering the Item name as <b>" + itemDescription + "</b> in description");
            oPage_SalesOrder.input_searchMedicineNameInDescription.sendKeys(Keys.ENTER);
            boolean myMedicationFound = false;
            Cls_Generic_Methods.customWait(5);

            for (WebElement eMedication : oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult) {

                if (Cls_Generic_Methods.isElementDisplayed(eMedication)) {
                    if (Cls_Generic_Methods.getTextInElement(eMedication).contains(itemDescription)) {
                        Cls_Generic_Methods.clickElementByJS(driver, eMedication);
                        myMedicationFound = true;
                        break;
                    }
                }
            }
            m_assert.assertTrue(myMedicationFound, "Item : <b>" + itemDescription + "</b> Selected for Sales Order");


        } catch (Exception e) {
            m_assert.assertFatal("Unable to Add Item " + itemDescription);
            e.printStackTrace();
        }
    }

    public void fillStockableItemDetailsCreateSalesOrder(HashMap<String, String> itemDetails, String itemQuantity, String discount) {

        ArrayList<String> headerValue = new ArrayList<>();
        double itemGross = 0;
        double itemDiscount = 0;

        try {
            String itemDescription = itemDetails.get("DESCRIPTION");

            for (WebElement header : oPage_SalesOrder.list_createSalesOrderTableHeader) {
                String value = Cls_Generic_Methods.getTextInElement(header);
                headerValue.add(value);
            }


            boolean itemFound = false;
            for (WebElement row : oPage_SalesOrder.list_createSalesOrderTableRow) {
                String itemName = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.list_itemNameInTableCreateSalesOrder.get(oPage_SalesOrder.list_createSalesOrderTableRow.indexOf(row)));

                if (itemName.equals(itemDescription)) {
                    List<WebElement> columns = row.findElements(By.xpath(".//td"));
                    itemFound = true;
                    String hsnValueTable = Cls_Generic_Methods.getTextInElement(columns.get(headerValue.indexOf("HSN")));
                    String batchValueTable = Cls_Generic_Methods.getTextInElement(columns.get(headerValue.indexOf("Batch")));
                    String expiryValueTable = Cls_Generic_Methods.getTextInElement(columns.get(headerValue.indexOf("Expiry")));
                    String mrpValueTable = Cls_Generic_Methods.getTextInElement(columns.get(headerValue.indexOf("MRP")));
                    String taxPercentageValueTable = Cls_Generic_Methods.getTextInElement(columns.get(headerValue.indexOf("Tax %")));

                    m_assert.assertTrue("Displayed Item Description : <b>" + itemName + "</b>");
                    m_assert.assertTrue(hsnValueTable.equals(itemDetails.get("HSN")), "Validated Item HSN -><b>" + hsnValueTable + "</b>");
                    m_assert.assertTrue(batchValueTable.equals(itemDetails.get("BATCH")), "Validated Item Batch No -><b>" + batchValueTable + "</b>");
                    m_assert.assertTrue(expiryValueTable.equals(itemDetails.get("EXPIRY")), "Validated Item Expiry Date -><b>" + expiryValueTable + "</b>");
                    m_assert.assertTrue(mrpValueTable.equals(itemDetails.get("MRP")), "Validated Item MRP -><b>" + mrpValueTable + "</b>");
                    m_assert.assertTrue(taxPercentageValueTable.equals(itemDetails.get("TAX")), "Validated Item Tax Percentage -><b>" + itemDetails.get("TAX") + "</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(columns.get(headerValue.indexOf("Qty.")).findElement(By.xpath("./input")), itemQuantity), "Entered <b>" + itemQuantity + "</b> in Quantity");
                    Cls_Generic_Methods.customWait();

                    double totalMrp = CommonActions.convertStringToDouble(itemDetails.get("MRP")) * CommonActions.convertStringToDouble(itemQuantity);
                    if (itemDetails.get("TAX INCLUSIVE").equalsIgnoreCase("yes")) {
                        itemGross = totalMrp;
                    } else {
                        itemGross = totalMrp + (totalMrp * (CommonActions.convertStringToDouble(itemDetails.get("TAX")) / 100));
                    }

                    String grossValueTable = Cls_Generic_Methods.getTextInElement(columns.get(headerValue.indexOf("Gross")));
                    m_assert.assertTrue(CommonActions.convertStringToDouble(grossValueTable) == itemGross, "Validated Item Gross Amount -><b>" + grossValueTable + "</b>");
                    totalGrossAmount = totalGrossAmount + itemGross;

                    //Applying Discount
                    String discountInput = discount.split(" ")[0];
                    String discountType = discount.split(" ")[1];

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(columns.get(headerValue.indexOf("Discount")).findElement(By.xpath(".//input")), discountInput), "Entered <b>" + discountInput + "</b> in Discount");
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(columns.get(headerValue.indexOf("Discount")).findElement(By.xpath(".//select")), discountType), "Selected <b>" + discountType + "</b> in Discount Type");

                    if (discountType.equals("%")) {
                        itemDiscount = itemGross * (CommonActions.convertStringToDouble(discountInput) / 100);
                    } else {
                        itemDiscount = CommonActions.convertStringToDouble(discountInput);
                    }

                    double displayedDiscount = CommonActions.convertStringToDouble(Cls_Generic_Methods.getTextInElement(columns.get(headerValue.indexOf("Discount")).findElement(By.xpath(".//div[@class='discount-data']"))));
                    m_assert.assertTrue(displayedDiscount == itemDiscount, "Validated -> Displayed Discount Amount : <b>" + displayedDiscount + "</b>");
                    totalDiscount = totalDiscount + itemDiscount;

                    //Offer Amount
                    double offerAmount = (itemGross - itemDiscount) * (CommonActions.convertStringToDouble(offerPercentage) / 100);
                    totalOffer = totalOffer + offerAmount;
                    double discountAndOfferDiscount = itemDiscount + offerAmount;

                    //Taxable Amount
                    double itemTaxableAmount = getTaxableAmount(itemDetails, itemQuantity, discountAndOfferDiscount);
                    String actualTaxableAmount = Cls_Generic_Methods.getElementAttribute(columns.get(headerValue.indexOf("Taxable Amt.")).findElement(By.xpath("./input")), "value");

                    m_assert.assertTrue(formatDecimalNumber(CommonActions.convertStringToDouble(actualTaxableAmount)) == formatDecimalNumber(itemTaxableAmount), "Validated ->Displayed Taxable amount <b>" + actualTaxableAmount + "</b>");
                    totalTaxableAmount = totalTaxableAmount + itemTaxableAmount;

                    //Tax Amount
                    double itemTaxAmount = itemTaxableAmount * (CommonActions.convertStringToDouble(itemDetails.get("TAX")) / 100);
                    String actualTaxAmount = Cls_Generic_Methods.getElementAttribute(columns.get(headerValue.indexOf("Tax Amt.")).findElement(By.xpath("./input")), "value");
                    m_assert.assertTrue(formatDecimalNumber(CommonActions.convertStringToDouble(actualTaxAmount)) == formatDecimalNumber(itemTaxAmount), "Validated ->Displayed Tax amount <b>" + actualTaxAmount + "</b>");

                    switch (itemDetails.get("TAX")) {
                        case "5.0" -> totalGST5Amount = totalGST5Amount + itemTaxAmount;
                        case "18.0" -> totalGST18Amount = totalGST18Amount + itemTaxAmount;
                    }

                    //Net Amount
                    double itemNetAmount = itemTaxableAmount + itemTaxAmount;
                    String actualNetAmount = Cls_Generic_Methods.getElementAttribute(columns.get(headerValue.indexOf("Net Amount")).findElement(By.xpath("./input")), "value");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(actualNetAmount) == itemNetAmount, "Validated ->Displayed Net amount <b>" + actualNetAmount + "</b>");
                    totalNetAmount = totalNetAmount + itemNetAmount;

                    //Remark
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(columns.get(headerValue.indexOf("Remarks")).findElement(By.xpath(".//input")), "TEST"), "Entered <b>TEST</b> in Remark");


                    itemDetails.put("QTY", itemQuantity);
                    itemDetails.put("GROSS", String.valueOf(itemGross));
                    itemDetails.put("DISCOUNT", String.valueOf(itemGross));
                    itemDetails.put("TAX AMOUNT", String.valueOf(formatDecimalNumber(itemTaxAmount)));
                    itemDetails.put("NET AMOUNT", String.valueOf(formatDecimalNumber(itemNetAmount)));
                    itemDetails.put("TOTAL DISCOUNT", String.valueOf(formatDecimalNumber(discountAndOfferDiscount)));

                    System.out.println("----------BREAK UP------------" + itemDescription);
                    System.out.println("ITEM GROSS -->" + itemGross);
                    System.out.println("ITEM DISCOUNT -->" + itemDiscount);
                    System.out.println("TAXABLE AMOUNT  -->" + itemTaxableAmount);
                    System.out.println("TAX AMOUNT  -->" + itemTaxAmount);
                    System.out.println("NET AMOUNT  -->" + itemNetAmount);
                    break;
                }

            }

        } catch (Exception e) {
            m_assert.assertFatal("a" + e);
            e.printStackTrace();
        }

    }

    private String getCurrentDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | hh:mm a");
        Date date = new Date();
        //  07-06-2023 | 01:09 PM
        String date1 = dateFormat.format(date);
        return date1;
    }

    private void validateTotalCalculation() {
        try {
            //Validating Total Values
            String actualTotalTaxableAmount = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_totalTaxableAmount, "value");

            if (totalGST5Amount > 0) {
                String actualTotalGST5Amount = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_totalGST5Amount, "value");
                m_assert.assertTrue(formatDecimalNumber(totalGST5Amount) == CommonActions.convertStringToDouble(actualTotalGST5Amount), "Validated GST5 Amount = <b>" + actualTotalGST5Amount + "</b>");
            }
            if (totalGST18Amount > 0) {
                String actualTotalGST18Amount = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_totalGST18Amount, "value");
                m_assert.assertTrue(formatDecimalNumber(totalGST18Amount) == CommonActions.convertStringToDouble(actualTotalGST18Amount), "Validated GST18 Amount = <b>" + actualTotalGST18Amount + "</b>");
            }

            String actualTotalGrossAmount = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_totalGrossAmount, "value");
            String actualTotalDiscountAmount = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_totalDiscountAmount, "value");
            String actualTotalOfferAmount = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_totalOfferAmount, "value");
            String actualTotalNetAmount = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_totalNetAmount, "value");


            m_assert.assertTrue(formatDecimalNumber(totalTaxableAmount) == CommonActions.convertStringToDouble(actualTotalTaxableAmount), "Validated Total Taxable Amount = <b>" + actualTotalTaxableAmount + "</b>");

            m_assert.assertTrue(formatDecimalNumber(totalGrossAmount) == CommonActions.convertStringToDouble(actualTotalGrossAmount), "Validated Total Gross Amount = <b>" + actualTotalGrossAmount + "</b>");
            m_assert.assertTrue(formatDecimalNumber(totalDiscount) == CommonActions.convertStringToDouble(actualTotalDiscountAmount), "Validated Total Discount Amount = <b>" + actualTotalDiscountAmount + "</b>");
            m_assert.assertTrue(formatDecimalNumber(totalOffer) == CommonActions.convertStringToDouble(actualTotalOfferAmount), "Validated Total Offer Amount = <b>" + actualTotalOfferAmount + "</b>");
            m_assert.assertTrue(formatDecimalNumber(totalNetAmount) == CommonActions.convertStringToDouble(actualTotalNetAmount), "Validated Total Net Amount = <b>" + actualTotalNetAmount + "</b>");

            String sBalancePending = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_balancePendingAmountAdvance, "value");
            m_assert.assertTrue(formatDecimalNumber(totalNetAmount) == CommonActions.convertStringToDouble(sBalancePending), "Validated balance/pending amount " + sBalancePending + " on the sales order");
            remainingBalance = totalNetAmount;

            //Checking Delivery Option
            boolean pendingDelivery = Cls_Generic_Methods.radioButtonIsSelected(oPage_SalesOrder.radioBtn_deliveryPending);
            if (pendingDelivery) {
                m_assert.assertTrue("Pending Delivery radio button is selected by default");
            } else {
                m_assert.assertFatal("Pending Delivery radio button is not selected by default");
                Cls_Generic_Methods.clickElementByJS(driver, oPage_SalesOrder.radioBtn_deliveryPending);
            }

            //Entering values into required field and Validating payment Details
            if (fittingStatus && (!Cls_Generic_Methods.radioButtonIsSelected(oPage_SalesOrder.checkbox_fittingRequired))) {

                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_SalesOrder.checkbox_fittingRequired), "Clicked <b>Fitting Required</b>");
            }

            Cls_Generic_Methods.customWait();
            if (homeDeliveryStatus) {
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_SalesOrder.checkbox_homeDelivery), "Clicked <b>Home Delivery Required</b>");
            }

            Cls_Generic_Methods.customWait();
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(oPage_SalesOrder.select_Fitter, 1), "Selected Fitter : " + Cls_Generic_Methods.getSelectedValue(oPage_SalesOrder.select_Fitter));

            sReadyDate = Cls_Generic_Methods.getTodayDate("dd/MM/yyyy");
            sDeliveryDate = Cls_Generic_Methods.getDifferenceInDays(sReadyDate, 1, "dd/MM/yyyy");
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysByJS(driver, oPage_SalesOrder.input_ReadyDate, sReadyDate), "Entered ready date as " + sReadyDate + " in Est. Ready date ");
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysByJS(driver, oPage_SalesOrder.input_deliveryDate, sDeliveryDate), "Entered delivery date as " + sDeliveryDate + " in Est. delivery date ");
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_advanceReason, sAdvanceReason), "Entered advance reason as " + sAdvanceReason);
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_SalesOrder.select_modeOfPaymentAdvancePayment.get(0), modeOfPayment), "Selected mode of payment as " + modeOfPayment);
            Cls_Generic_Methods.clearValuesInElement(oPage_SalesOrder.input_advanceAmount.get(0));
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_advanceAmount.get(0), sAdvanceAmount), "Entered First advance amount " + sAdvanceAmount);

            //Adding new advance
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addPaymentAdvancePayment), "Clicked <b>Add Payment</b> button");
            Cls_Generic_Methods.customWait();
            if (oPage_SalesOrder.select_modeOfPaymentAdvancePayment.size() == 2) {
                m_assert.assertTrue("New Mode of Payment option Added");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_SalesOrder.select_modeOfPaymentAdvancePayment.get(1), "Card"), "Selected mode of payment as <b>Card</b>");
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(oPage_SalesOrder.select_cardMachineMop, 1), "Selected Card Machine as <b>" + Cls_Generic_Methods.getSelectedValue(oPage_SalesOrder.select_cardMachineMop) + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_transactionIdNo, "1234"), "Entered Transaction Id No : 1234");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_batchNoMop, "1234"), "Entered Batch No : 1234");
                Cls_Generic_Methods.clearValuesInElement(oPage_SalesOrder.input_advanceAmount.get(1));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_advanceAmount.get(1), sAdvanceAmount), "Entered Second advance amount " + sAdvanceAmount);
                totalAdvancePaid = CommonActions.convertStringToDouble(sAdvanceAmount) * 2;
            }

            Cls_Generic_Methods.customWait();
            String displayedTotalAdvance = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.input_displayedAdvanceAmount, "value");
            m_assert.assertTrue(CommonActions.convertStringToDouble(displayedTotalAdvance) == totalAdvancePaid, "Validated Displayed Advance amount " + displayedTotalAdvance);

            //Checking Remaining Balance After Advance
            remainingBalance = totalNetAmount - totalAdvancePaid;
            sBalancePending = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_balancePendingAmountAdvance, "value");
            m_assert.assertTrue(formatDecimalNumber(remainingBalance) == CommonActions.convertStringToDouble(sBalancePending), "Validated -> Balance/pending amount after Adding Advance <b>" + sBalancePending + "</b>");
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_paymentPendingAmount, sBalancePending), "Amount pending against payer " + sBalancePending);


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }
    }

    private void validateRHSSalesOrder() {

        try {
            //Validate Created Table List Values
            Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_Refresh);
            Cls_Generic_Methods.customWait();

            String deliveryDateInTable = getTableValueCreatedSalesOrder(salesOrderId, "Delivery Date").split("\n")[0];
            m_assert.assertTrue(deliveryDateInTable.equals(CommonActions.getRequiredFormattedDateTime("dd/mm/yyyy", "dd-mm-yyyy", sDeliveryDate)), "Validated Estimated Delivery Date : <b>" + deliveryDateInTable + "</b> in created sales order list");

            String orderStatusInTable = getTableValueCreatedSalesOrder(salesOrderId, "Order Status").split("\n")[0];
            String orderStatus = "";
            if (fittingStatus) {
                orderStatus = "Fitting";
            }
            m_assert.assertTrue(orderStatusInTable.contains(orderStatus), "Validated Order Status : <b>" + orderStatusInTable + "</b> in created sales order list");

            String amountInTable = getTableValueCreatedSalesOrder(salesOrderId, "Amount").split("Adv.")[0];
            m_assert.assertTrue(CommonActions.convertStringToDouble(amountInTable) == totalGrossAmount, "Validated Amount : <b>" + amountInTable + "</b> in created sales order list");
            String advanceInTable = getTableValueCreatedSalesOrder(salesOrderId, "Amount").split("Adv.")[1];
            m_assert.assertTrue(CommonActions.convertStringToDouble(advanceInTable) == totalAdvancePaid, "Validated Advance : <b>" + advanceInTable + "</b> in created sales order list");

            selectSalesOrder(salesOrderId);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_Patient, 10);
            currentStatus = getCurrentWorkFlowTimeLine();
            m_assert.assertTrue(currentStatus.equals(orderStatus), "Validated Sales Order Work Flow -> Current Status : <b>" + currentStatus + "</b>");

            //Validate Ready
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_Ready, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_Ready), "Clicked <b>Ready</b> Delivery Button");
            Cls_Generic_Methods.customWait(4);
            currentStatus = getCurrentWorkFlowTimeLine();
            m_assert.assertTrue(currentStatus.equals("Ready"), "Validated Sales Order Work Flow -> Current Status : <b>" + currentStatus + "</b>");

            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_undo), "Clicked <b>Undo</b> Button");
            Cls_Generic_Methods.customWait(4);
            currentStatus = getCurrentWorkFlowTimeLine();
            m_assert.assertTrue(currentStatus.equals(orderStatus), "Validated Sales Order Work Flow -> Current Action Status : <b>" + currentStatus + "</b>");
            m_assert.assertTrue((Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsCurrentStatus)).equalsIgnoreCase("fitting"), "Validated Sales Order RHS -> Current Status : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsCurrentStatus) + "</b>");

            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_Ready), "Clicked <b>Ready</b> Delivery Button");
            Cls_Generic_Methods.customWait(4);
            m_assert.assertTrue((Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsCurrentStatus)).equalsIgnoreCase("ready"), "Validated Sales Order RHS -> Current Status : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsCurrentStatus) + "</b>");

            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_fittingFlow), "Clicked <b>Fitting</b> Button");
            Cls_Generic_Methods.customWait(4);
            currentStatus = getCurrentWorkFlowTimeLine();
            m_assert.assertTrue(currentStatus.equals(orderStatus), "Validated Sales Order Work Flow -> Current Status : <b>" + currentStatus + "</b>");


            //Rhs validation
            m_assert.assertTrue(concatPatientFullName.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_Patient)), "Validated Sales Order RHS -> Patient name : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_Patient) + "</b>");
            m_assert.assertTrue(expectedLoggedInUser.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsDoctorName)), "Validated Sales Order RHS -> Doctor name : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsDoctorName) + "</b>");
            m_assert.assertTrue(orderType.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_orderType)), "Validated Sales Order RHS -> Order Type : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_orderType) + "</b>");
            m_assert.assertTrue(claimProcessor.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_claimProcessorName)), "Validated Sales Order RHS -> ClaimProcessorName : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_claimProcessorName) + "</b>");
            m_assert.assertTrue(salesOrderId.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsOrderNumber)), "Validated Sales Order RHS -> Order Number : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsOrderNumber) + "</b>");
            m_assert.assertTrue(sDeliveryDate.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsEstimatedDelivery)), "Validated Sales Order RHS -> Estimated Delivery Date : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsEstimatedDelivery) + "</b>");

            patientId = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsPatientId);
            String homeDelivery = "";
            if (homeDeliveryStatus) {
                homeDelivery = "Home Delivery";
            }
            m_assert.assertTrue(homeDelivery.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsHomeDelivery)), "Validated Sales Order RHS -> Home Delivery Status : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsHomeDelivery) + "</b>");

            //RHS TABLE VALIDATION *ID-Item Detail

            validatedSalesOrderRhsItemDetails(itemDetails1);

            if (!bOneItem) {
                validatedSalesOrderRhsItemDetails(itemDetails2);
            }
             sBalancePending = (Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_PaymentPending));

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }
    }

    private void createBillSalesOrder() {
        try {
            //Validate Print and Email
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_printOrder,10);
            m_assert.assertTrue(validatePrintPageOpened(oPage_SalesOrder.button_printOrder),"Validated Sales Order Print Page Opened");
            m_assert.assertTrue(validateEmail(oPage_SalesOrder.button_emailOrder,"[AUTO TEST] SALES ORDER PRINT"),"Validated Sales Order Email Sent");

            Cls_Generic_Methods.customWait(7);
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver,oPage_SalesOrder.button_CreateBill), "Clicked <b>Create bill</b> button");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.header_reviewOrder, 10);
            Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_removePaymentReceived);
            Cls_Generic_Methods.customWait(2);
            Cls_Generic_Methods.clickElement(driver, oPage_SalesOrder.button_CreateBillAfterPayment);
            Cls_Generic_Methods.customWait(3);
            selectSalesOrder(salesOrderId);
            Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_Ready);
            Cls_Generic_Methods.customWait(4);
            currentStatus = getCurrentWorkFlowTimeLine();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_Delivered, 10);

            m_assert.assertTrue(oPage_SalesOrder.button_Delivered.isEnabled(), "<b>Delivered</b> button is enabled after completing the payment");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_Delivered), "Clicked <b>Delivered</b> button");
            Cls_Generic_Methods.customWait();
            currentStatus = getCurrentWorkFlowTimeLine();
            m_assert.assertTrue(currentStatus.equals("Delivered"), "Validated Sales Order Work Flow -> Status : <b>" + currentStatus + "</b>");
            m_assert.assertTrue((Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsCurrentStatus)).equalsIgnoreCase("Delivered"), "Validated Sales Order RHS -> Current Status : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsCurrentStatus) + "</b>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }
    }

    private void validateOpticalOrder() {

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_order, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_order), "Clicked <b>Optical Order</b> button");
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_opticalOrderHistory, 10), "Validated <b>Optical Order</b> Page Displayed");

            //Validate Header
            m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_orderType).equalsIgnoreCase(orderType), "Validated Optical Order -> Bill type : <b>" + orderType + "</b>");
            m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_doctorNameOpticalOrder).equalsIgnoreCase(expectedLoggedInUser), "Validated Optical Order -> Doctor : <b>" + expectedLoggedInUser + "</b>");
            m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_patientIdInOpticalOrder).equals(patientId), "Validated Optical Order -> Patient ID : <b>" + patientId + "</b>");
            m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_mrnNoOpticalOrder).equals(mrNo), "Validated Optical Order -> MR No : <b>" + mrNo + "</b>");
            m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_patientMobileNoOpticalOrder).equals(myPatient.getsMOBILE_NUMBER()), "Validated Optical Order -> Patient Mobile No : <b>" + myPatient.getsMOBILE_NUMBER() + "</b>");

            String orderedOn = convertDateFormatFromMMMtoMM(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_orderedOnOpticalOrder));
            m_assert.assertTrue(compareDateTimesWithTolerance(orderedOn, salesOrderCreatedAt, 1), "Validated Optical Order -> Ordered On : <b>" + orderedOn + "</b>");

            m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_orderNoOpticalOrder).equals(salesOrderId), "Validated Optical Order -> Sales Order No : <b>" + salesOrderId + "</b>");
            m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_claimProcessorNameInOpticalOrder).equals(claimProcessor), "Validated Optical Order -> claimProcessor : <b>" + claimProcessor + "</b>");
            m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_estimatedDeliveryOpticalOrder).equals(sDeliveryDate), "Validated Optical Order -> Estimated Delivery : <b>" + sDeliveryDate + "</b>");
            m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_createdByOpticalOrder).equals(expectedLoggedInUser), "Validated Optical Order -> Order Created By : <b>" + expectedLoggedInUser + "</b>");
            m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_currentStatusOpticalOrder).equals(currentStatus), "Validated Optical Order -> Order Current Status : <b>" + currentStatus + "</b>");

            //Validate Glass Prescription

            m_assert.assertTrue(CommonActions.convertStringToDouble(getValueFromGlassPrescriptionTable("Sph", "Distant")) == CommonActions.convertStringToDouble(sDistantSph), "Validated Optical Order -> Glass Prescription : <b>Sph Distant = " + sDistantSph + "</b>");
            m_assert.assertTrue(CommonActions.convertStringToDouble(getValueFromGlassPrescriptionTable("Sph", "Add")) == CommonActions.convertStringToDouble(sAddSph), "Validated Optical Order -> Glass Prescription : <b>Sph Add = " + sAddSph + "</b>");
            m_assert.assertTrue(CommonActions.convertStringToDouble(getValueFromGlassPrescriptionTable("Sph", "Near")) == CommonActions.convertStringToDouble(sNearSph), "Validated Optical Order -> Glass Prescription : <b>Sph Near = " + sNearSph + "</b>");
            m_assert.assertTrue(CommonActions.convertStringToDouble(getValueFromGlassPrescriptionTable("Cyl", "Distant")) == CommonActions.convertStringToDouble(sDistantCyl), "Validated Optical Order -> Glass Prescription : <b>Cyl Distant = " + sDistantCyl + "</b>");
            m_assert.assertTrue(CommonActions.convertStringToDouble(getValueFromGlassPrescriptionTable("Cyl", "Near")) == CommonActions.convertStringToDouble(sNearCyl), "Validated Optical Order -> Glass Prescription : <b>Cyl Near = " + sDistantCyl + "</b>");
            m_assert.assertTrue(CommonActions.convertStringToDouble(getValueFromGlassPrescriptionTable("Axis", "Distant")) == CommonActions.convertStringToDouble(sDistantAxis), "Validated Optical Order -> Glass Prescription : <b>Axis Distant = " + sDistantAxis + "</b>");
            m_assert.assertTrue(CommonActions.convertStringToDouble(getValueFromGlassPrescriptionTable("Axis", "Near")) == CommonActions.convertStringToDouble(sNearAxis), "Validated Optical Order -> Glass Prescription : <b>Axis Near = " + sNearAxis + "</b>");
            m_assert.assertTrue(getValueFromGlassPrescriptionTable("Vision", "Distant").equals(sDistantVision), "Validated Optical Order -> Glass Prescription : <b>Vision Distant = " + sDistantVision + "</b>");
            m_assert.assertTrue(getValueFromGlassPrescriptionTable("Vision", "Near").equals(sNearVision), "Validated Optical Order -> Glass Prescription : <b>Vision Near = " + sNearVision + "</b>");

            //Validate Item Details
            validatedOpticalOrderItemDetails(itemDetails1);

            if (!bOneItem) {
                validatedOpticalOrderItemDetails(itemDetails2);
            }

            //Validate Print
            for (WebElement printBtn : oPage_SalesOrder.list_buttonPrintOpticalOrderItemDetails) {
                String printValue = Cls_Generic_Methods.getTextInElement(printBtn);
                m_assert.assertTrue(validatePrintPageOpened(printBtn), "Validated Optical Order -> <b>" + printValue + "</b> page opened");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_opticalOrderHistory, 10);
            }
            Cls_Generic_Methods.clickElement(driver, oPage_SalesOrder.button_closeOpticalOrder);
        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate Optical Order " + e);
            e.printStackTrace();
        }
    }

    private String getOpticalOrderItemDetails(String description, String columnHeaderName) {
        String returnValue = "";

        try {
            ArrayList<String> headerValue = new ArrayList<>();
            for (WebElement eHeaderSalesOrder : oPage_SalesOrder.list_textHeaderOpticalOrderItemDetails) {
                String value = Cls_Generic_Methods.getTextInElement(eHeaderSalesOrder);
                headerValue.add(value);
            }


            for (WebElement rhsRow : oPage_SalesOrder.list_textRowOpticalOrderItemDetails) {
                String descriptionValue = Cls_Generic_Methods.getTextInElement(rhsRow.findElements(By.xpath("./td")).get(headerValue.indexOf("Description")));
                if (descriptionValue.contains(description)) {
                    returnValue = Cls_Generic_Methods.getTextInElement(rhsRow.findElements(By.xpath("./td")).get(headerValue.indexOf(columnHeaderName)));
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    private void validatedOpticalOrderItemDetails(HashMap<String, String> itemDetails) {

        try {
            String qtyInItemDetails = getOpticalOrderItemDetails(itemDetails.get("DESCRIPTION"), "QTY");
            String batchInItemDetails = getOpticalOrderItemDetails(itemDetails.get("DESCRIPTION"), "Batch No.");
            String hsnInItemDetails = getOpticalOrderItemDetails(itemDetails.get("DESCRIPTION"), "HSN");
            String mrpInItemDetails = getOpticalOrderItemDetails(itemDetails.get("DESCRIPTION"), "MRP");
            String expDateInItemDetails = getOpticalOrderItemDetails(itemDetails.get("DESCRIPTION"), "Exp.Date");
            String grossAmountInItemDetails = getOpticalOrderItemDetails(itemDetails.get("DESCRIPTION"), "Gross");
            String discountInItemDetails = getOpticalOrderItemDetails(itemDetails.get("DESCRIPTION"), "Discounts");
            String taxAmountInItemDetails = getOpticalOrderItemDetails(itemDetails.get("DESCRIPTION"), "Tax Amt.");
            String taxInItemDetails = getOpticalOrderItemDetails(itemDetails.get("DESCRIPTION"), "Tax");
            String netAmountInItemDetails = getOpticalOrderItemDetails(itemDetails.get("DESCRIPTION"), "Net Amt.");

            m_assert.assertTrue(CommonActions.convertStringToDouble(qtyInItemDetails) == CommonActions.convertStringToDouble(itemDetails.get("QTY")), "Validated Item Details -> QTY = <b>" + qtyInItemDetails + "</b>");
            m_assert.assertTrue(batchInItemDetails.equals(itemDetails.get("BATCH")), "Validated Item Details -> BATCH NO = <b>" + batchInItemDetails + "</b>");
            m_assert.assertTrue(hsnInItemDetails.equals(itemDetails.get("HSN")), "Validated Item Details -> HSN = <b>" + hsnInItemDetails + "</b>");
            m_assert.assertTrue(CommonActions.convertStringToDouble(mrpInItemDetails) == CommonActions.convertStringToDouble(itemDetails.get("MRP")), "Validated Item Details -> MRP = <b>" + mrpInItemDetails + "</b>");


            m_assert.assertTrue(expDateInItemDetails.equals(itemDetails.get("EXPIRY")), "Validated Optical Order -> Item Details -> EXP. DATE = <b>" + expDateInItemDetails + "</b>");
            m_assert.assertTrue(grossAmountInItemDetails.equals(itemDetails.get("GROSS")), "Validated Optical Order -> Item Details -> GROSS AMOUNT = <b>" + grossAmountInItemDetails + "</b>");
            m_assert.assertTrue(discountInItemDetails.equals(itemDetails.get("TOTAL DISCOUNT")), "Validated Optical Order -> Item Details -> DISCOUNT AMOUNT = <b>" + discountInItemDetails + "</b>");
            m_assert.assertTrue(CommonActions.convertStringToDouble(taxInItemDetails) == CommonActions.convertStringToDouble(itemDetails.get("TAX")), "Validated Optical Order -> Item Details -> TAX PERCENTAGE = <b>" + taxInItemDetails + "</b>");
            m_assert.assertTrue(taxAmountInItemDetails.contains(itemDetails.get("TAX AMOUNT")), "Validated Optical Order -> Item Details -> TAX AMOUNT = <b>" + taxAmountInItemDetails + "</b>");
            m_assert.assertTrue(CommonActions.convertStringToDouble(netAmountInItemDetails) == CommonActions.convertStringToDouble(itemDetails.get("NET AMOUNT")), "Validated Optical Order -> Item Details -> NET AMOUNT = <b>" + netAmountInItemDetails + "</b>");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean checkIfDateIsFuture(String dateString) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate givenDate = LocalDate.parse(dateString, dateFormatter);
        LocalDate currentDate = LocalDate.now();
        return givenDate.isAfter(currentDate);
    }

    private void selectSalesOrder(String orderId) {
        try {
            for (WebElement eSalesOrder : oPage_SalesOrder.list_namesofSalesOrder) {
                String value = Cls_Generic_Methods.getTextInElement(eSalesOrder.findElement(By.xpath("./td[2]"))).split("\n")[1];

                if (value.equals(orderId)) {
                    Cls_Generic_Methods.clickElement(eSalesOrder);
                    break;
                }
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_order, 5);
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to select Sales Order " + e);
        }
    }
    public double getTaxableAmount(HashMap<String, String> itemDetails, String quantity, double itemTotalDiscountAmount) {
        double taxableAmount = 0;
        try {
            double itemMrp = CommonActions.convertStringToDouble(itemDetails.get("MRP")) * CommonActions.convertStringToDouble(quantity);
            double itemTax = CommonActions.convertStringToDouble(itemDetails.get("TAX"));


            if (itemDetails.get("TAX INCLUSIVE").equalsIgnoreCase("yes")) {
                //Formula for Calculating Taxable Amount
                //TaxableAmount=(100/(100+TaxPercentage))*MRP     if Discount --> TaxableAmount=(100/(100+TaxPercentage))*(MRP-Discount)

                taxableAmount = (100 / (100 + itemTax)) * (itemMrp - itemTotalDiscountAmount);
            } else {
                //Formula for Calculating Taxable Amount
                //TaxableAmount=MRP    if Discount --> TaxableAmount=MRP-((100/(100+TaxPercentage))*Discount)

                taxableAmount = itemMrp - ((100 / (100 + itemTax)) * itemTotalDiscountAmount);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return taxableAmount;
    }

   public double formatDecimalNumber(double number) {
       DecimalFormat decimalFormat = new DecimalFormat("#.##");
       decimalFormat.setRoundingMode(java.math.RoundingMode.HALF_UP);
       return Double.parseDouble(decimalFormat.format(number));
   }

    private String getTableValueCreatedSalesOrder(String orderId, String columnName) {
        String returnValue = "";
        try {
            ArrayList<String> headerValue = new ArrayList<>();
            for (WebElement eHeaderSalesOrder : oPage_SalesOrder.list_tableHeaderCreatedSalesOrder) {
                String value = Cls_Generic_Methods.getTextInElement(eHeaderSalesOrder);
                headerValue.add(value);
            }

            for (WebElement eSalesOrder : oPage_SalesOrder.list_tableRowCreatedSalesOrder) {
                String eSalesOrderId = Cls_Generic_Methods.getTextInElement(eSalesOrder.findElement(By.xpath("./td[2]"))).split("\n")[1];
                if (eSalesOrderId.contains(orderId)) {
                    returnValue = Cls_Generic_Methods.getTextInElement(eSalesOrder.findElements(By.xpath("./td")).get(headerValue.indexOf(columnName)));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public String getCurrentWorkFlowTimeLine() {
        String currentStatus = "";
        try {
            String lastStatus = "";
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_SalesOrder.list_labelOrderWorkFlowTimeline, 10);

            for (WebElement eWorkFlow : oPage_SalesOrder.list_labelOrderWorkFlowTimeline) {
                lastStatus = Cls_Generic_Methods.getTextInElement(eWorkFlow);
            }

            currentStatus = lastStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentStatus;
    }

    private void validatedSalesOrderRhsItemDetails(HashMap<String, String> itemDetails) {


        try {
            String qtyInItemDetails = getSalesOrderRhsItemDetails(itemDetails.get("DESCRIPTION"), "QTY");
            String batchInItemDetails = getSalesOrderRhsItemDetails(itemDetails.get("DESCRIPTION"), "Batch");
            String hsnInItemDetails = getSalesOrderRhsItemDetails(itemDetails.get("DESCRIPTION"), "HSN NO");
            String mrpInItemDetails = getSalesOrderRhsItemDetails(itemDetails.get("DESCRIPTION"), "MRP");
            String expDateInItemDetails = getSalesOrderRhsItemDetails(itemDetails.get("DESCRIPTION"), "Exp.Date");
            String grossAmountInItemDetails = getSalesOrderRhsItemDetails(itemDetails.get("DESCRIPTION"), "Gross Amt");
            String discountInItemDetails = getSalesOrderRhsItemDetails(itemDetails.get("DESCRIPTION"), "Discounts");
            String taxInItemDetails = getSalesOrderRhsItemDetails(itemDetails.get("DESCRIPTION"), "Tax").split("\n")[0];
            String netAmountInItemDetails = getSalesOrderRhsItemDetails(itemDetails.get("DESCRIPTION"), "Net Amt.");

            m_assert.assertTrue(CommonActions.convertStringToDouble(qtyInItemDetails) == CommonActions.convertStringToDouble(itemDetails.get("QTY")), "Validated Item Details -> QTY = <b>" + qtyInItemDetails + "</b>");
            m_assert.assertTrue(batchInItemDetails.equals(itemDetails.get("BATCH")), "Validated Item Details -> BATCH NO = <b>" + batchInItemDetails + "</b>");
            m_assert.assertTrue(hsnInItemDetails.equals(itemDetails.get("HSN")), "Validated Item Details -> HSN = <b>" + hsnInItemDetails + "</b>");
            m_assert.assertTrue(CommonActions.convertStringToDouble(mrpInItemDetails) == CommonActions.convertStringToDouble(itemDetails.get("MRP")), "Validated Item Details -> MRP = <b>" + mrpInItemDetails + "</b>");

            boolean containsNumber = Pattern.compile("\\d").matcher((itemDetails.get("EXPIRY"))).find();
            if (containsNumber) {
                itemDetails.put("EXPIRY", CommonActions.getRequiredFormattedDateTime("yyyy-mm-dd", "dd/mm/yyyy", (itemDetails.get("EXPIRY"))));
            }
            m_assert.assertTrue(expDateInItemDetails.equals(itemDetails.get("EXPIRY")), "Validated Item Details -> EXP. DATE = <b>" + expDateInItemDetails + "</b>");
            m_assert.assertTrue(grossAmountInItemDetails.equals(itemDetails.get("GROSS")), "Validated Item Details -> GROSS AMOUNT = <b>" + grossAmountInItemDetails + "</b>");
            m_assert.assertTrue(discountInItemDetails.equals(itemDetails.get("TOTAL DISCOUNT")), "Validated Item Details -> DISCOUNT AMOUNT = <b>" + discountInItemDetails + "</b>");
            m_assert.assertTrue(taxInItemDetails.contains(itemDetails.get("TAX AMOUNT")), "Validated Item Details -> TAX AMOUNT = <b>" + taxInItemDetails + "</b>");
            m_assert.assertTrue(CommonActions.convertStringToDouble(netAmountInItemDetails) == CommonActions.convertStringToDouble(itemDetails.get("NET AMOUNT")), "Validated Item Details -> NET AMOUNT = <b>" + netAmountInItemDetails + "</b>");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private String getSalesOrderRhsItemDetails(String description, String columnHeaderName) {
        String returnValue = "";

        try {
            ArrayList<String> headerValue = new ArrayList<>();
            for (WebElement eHeaderSalesOrder : oPage_SalesOrder.list_textHeaderRhsItemDetails) {
                String value = Cls_Generic_Methods.getTextInElement(eHeaderSalesOrder);
                headerValue.add(value);
            }


            for (WebElement rhsRow : oPage_SalesOrder.list_textRowRhsItemDetails) {
                String descriptionValue = Cls_Generic_Methods.getTextInElement(rhsRow.findElements(By.xpath("./td")).get(headerValue.indexOf("Description")));
                if (descriptionValue.contains(description)) {
                    returnValue = Cls_Generic_Methods.getTextInElement(rhsRow.findElements(By.xpath("./td")).get(headerValue.indexOf(columnHeaderName)));
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    private boolean validatePrintPageOpened(WebElement printButton) {
        boolean flag = false;
        try {
            int preWindowsCount = driver.getWindowHandles().size();
            String initialWindowHandle = driver.getWindowHandle();
            Cls_Generic_Methods.clickElement(printButton);
            Cls_Generic_Methods.customWait(8);
            int postWindowsCount = driver.getWindowHandles().size();

            for (String currentWindowHandle : driver.getWindowHandles()) {
                if (!currentWindowHandle.equals(driver.getWindowHandle())) {
                    driver.switchTo().window(currentWindowHandle);
                }
            }
            driver.close();
            driver.switchTo().window(initialWindowHandle);
            flag = postWindowsCount > preWindowsCount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    private boolean validateEmail(WebElement emailButton, String emailSubject) {
        boolean flag = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            Cls_Generic_Methods.clickElement(emailButton);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_composeEmail, 10);
            boolean cancelSentTo = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_clearSentTo, 2);
            if (cancelSentTo) {
                Cls_Generic_Methods.clickElementByJS(driver, oPage_CommonElements.button_clearSentTo);
            }
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_CommonElements.input_sentToEmail, myPatient.getsEMAIL()), "Entered Send To mail id as <b>" + myPatient.getsEMAIL() + "</b>");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_CommonElements.input_subjectEmail, emailSubject), "Entered mail Subject as <b>" + emailSubject + "</b>");
            Cls_Generic_Methods.customWait();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_previewEmail), "Clicked Preview button");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_sendEmail, 10);
            flag = Cls_Generic_Methods.clickElement(oPage_CommonElements.button_sendEmail);
            m_assert.assertTrue(flag, "Clicked <b>Send</b> mail button");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public String getValueFromGlassPrescriptionTable(String columnHeader, String rowHeader) {
        String value = "";

        ArrayList<String> valueInBothTable = new ArrayList<>();
        try {
            int i = 1;
            while (i < 3) {
                WebElement table = driver.findElement(By.xpath("(//table[@class='table table-bordered'])[" + i + "]"));
                WebElement headerRow = table.findElement(By.xpath("(//table[@class='table table-bordered'])[" + i + "]/tbody[1]/tr"));
                int columnIndex = getColumnIndex(headerRow, columnHeader);
                int rowIndex = getRowIndex(table, rowHeader);
                WebElement cell = table.findElement(By.xpath("//tbody[2]/tr[" + rowIndex + "]/td[" + columnIndex + "]"));
                valueInBothTable.add(Cls_Generic_Methods.getTextInElement(cell));
                i++;
            }

            //Used Same values in both tables , So comparing both and returning the value if both are equal
            boolean compareBothTable = valueInBothTable.get(0).equals(valueInBothTable.get(1));
            if (compareBothTable) {
                value = valueInBothTable.get(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
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

    public static String convertDateFormatFromMMMtoMM(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy|hh:mma");

        LocalDateTime dateTime = LocalDateTime.parse(date, inputFormatter);
        return dateTime.format(outputFormatter);
    }
    private static int getColumnIndex(WebElement headerRow, String columnHeader) {

        int index = 0;
        try {
            for (WebElement cell : headerRow.findElements(By.xpath("./th"))) {
                index++;
                if (Cls_Generic_Methods.getTextInElement(cell).equalsIgnoreCase(columnHeader)) {
                    return index;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Column header not found

    }

    private static int getRowIndex(WebElement table, String rowHeader) {
        int index = 0;
        try {
            for (WebElement row : table.findElements(By.xpath("./tbody[2]/tr"))) {
                index++;
                if (Cls_Generic_Methods.getTextInElement(row).contains(rowHeader)) {
                    return index;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void validateSettlePage(){
        try {
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_placeOrderInSale, 10);
            boolean bSalesBillFound = false;
            String sDate = sTxnDate.replace("/", "-");

            for (WebElement eSalesBill : oPage_SalesOrder.list_namesofSalesOrder) {
                String sSalesBillName = Cls_Generic_Methods.getTextInElement(eSalesBill);
                if (sSalesBillName.contains(sDate) && sSalesBillName.contains(concatPatientFullName)) {
                    salesBillNumber = Cls_Generic_Methods.getTextInElement(eSalesBill.findElement(By.xpath("./td[2]"))).split("\n")[1];
                    Cls_Generic_Methods.clickElement(eSalesBill);
                    bSalesBillFound = true;
                    break;
                }
            }
            m_assert.assertTrue(bSalesBillFound, "Sales bill is created -> ID : <b>" + salesBillNumber + "</b>");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_reports),"Clicked on Report in Sale transaction section ");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_settleBillReports),"Clicked on Settle Report in Sale transaction section");
            Cls_Generic_Methods.customWait(6);

            for (WebElement eBillNumber: oPage_SalesOrder.list_billNumberInSettle) {
                String sBillNumber = Cls_Generic_Methods.getTextInElement(eBillNumber);
                if (sBillNumber.contains(salesBillNumber)) {
                    Cls_Generic_Methods.clickElement(oPage_SalesOrder.list_viewBill.get(oPage_SalesOrder.list_billNumberInSettle.indexOf(eBillNumber)));
                    break;
                }
            }
            Cls_Generic_Methods.customWait(3);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_settleOButton),"Clicked on Settle button on optical bill page");
            Cls_Generic_Methods.customWait(3);
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_SalesOrder.select_modeOfPaymentInSettle, "Cash"),"In the MODE of Payment Cash type is selected");
            Cls_Generic_Methods.clearValuesInElement(oPage_SalesOrder.input_amountReceivedInSettle);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_amountReceivedInSettle,sBalancePending),"Pending Amount is entered in amount received box to settle the bill = <b> "+ sBalancePending + "</b>");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_saveButtonInSettle),"Clicked On Save button to save the settle payment");
            Cls_Generic_Methods.clickElementByJS(driver,oPage_SalesOrder.button_closeButtonInOpticalOrder);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.clickElementByJS(driver,oPage_SalesOrder.button_closeButtonInInventoryPendingBills);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_placeOrderInSale, 10);

            for (WebElement eSalesBill : oPage_SalesOrder.list_namesofSalesOrder) {
                String sSalesBillName = Cls_Generic_Methods.getTextInElement(eSalesBill);
                if (sSalesBillName.contains(sDate) && sSalesBillName.contains(concatPatientFullName)) {
                    salesBillNumber = Cls_Generic_Methods.getTextInElement(eSalesBill.findElement(By.xpath("./td[2]"))).split("\n")[1];
                    Cls_Generic_Methods.clickElement(eSalesBill);
                    break;
                }
            }

            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_paymentReceivedDetailsInSale,10),"Payment Received amount is displayed");
            String receivedAmount = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_paymentReceivedDetailsInSale).split(" ")[0];
            if (CommonActions.convertStringToDouble(receivedAmount)==(CommonActions.convertStringToDouble(sBalancePending)+settledAmount)) {
                m_assert.assertTrue("Amount is settled and Total Payment Received = <b> " + receivedAmount + "</b>");
            }
            else {
                m_assert.assertFalse("Amount is settled and payment received is not equal to Total Payment Received =  <b> "+ receivedAmount + "</b>");
            }

        } catch (Exception e) {
            e.printStackTrace();
      m_assert.assertFatal(e + "Patient is not available in the settle page");
        }
    }


}
