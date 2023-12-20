package tests.inventoryStores.pharmacyStore.Order;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.InventoryCommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.store.OpticalStore.Page_Sale;
import pages.store.OpticalStore.Page_SalesOrder;
import pages.store.PharmacyStore.Items.Page_Lot;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Order.*;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class SalesOrderTest extends TestBase {

    public static String oPharmacyStore = "Pharmacy automation- Pharmacy";
    public static String itemName1 = "SalesOrderTest1";
    public static String itemName2 = "SALES-ORDER-AUTO-TEST2";

    HashMap<String, String> itemDetails1;
    HashMap<String, String> itemDetails2;
    static String modeOfPayment = "Cash";
    String salesOrderCreatedAt;
    static String sTxnDate = "";
    static String sTxnTime = "";
    String salesOrderId;
    String transferId;
    String patientId;
    String mrNo;
    static String sAdvanceReason = "Advance1";
    static String sAdvanceReason2 = "Advance2";

    static String sPaidAmount1 = "";
    static String sPaidAmount2 = "200";
    static String sAdvanceAmount2 = "200";


    public static String input_Qty = "5";
    public static double dAdvanceAmount = 0.0;
    public static double dRemainingAmount = 0.0;


    public String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
    static String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    static Model_Patient myPatient;

    String concatPatientFullName;

    String offerCode = "";
    String offerPercentage = "";

    pages.store.OpticalStore.Page_SalesOrder oPage_SalesOrder;
    Page_CommonElements oPage_CommonElements;
    pages.store.OpticalStore.Page_Sale oPage_Sale;
    Page_Master oPage_Master;
    Page_NewPatientRegisteration oPage_NewPatientRegisteration;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;

    //CALCULATIONS
    double totalGrossAmount = 0;
    double totalDiscount = 0;
    double totalOffer = 0;
    double totalTaxableAmount = 0;
    double totalGST5Amount = 0;
    double totalGST18Amount = 0;
    double totalNetAmount = 0;
    double remainingBalance = 0;
    double totalAmountPaid = 0;

    String item1Qty = "2";
    String item2Qty = "4";
    String item1Discount = "21 %";
    String item2Discount = "12 ₹";
    boolean homeDeliveryStatus = true;
    boolean fittingStatus = true;
    String currentStatus;
    boolean bOneItem = true;

    String existingPatientName = "Gunther";
    String cancellationReason = "AUTO-CANCELLATION-TEST";

    @Test(enabled = true, description = "Create Store Sales Order")
    public void validateCreatingSalesOrder() {

        try {
            //Opening Optical Store
            oPage_CommonElements = new Page_CommonElements(driver);
            oPage_Sale = new pages.store.OpticalStore.Page_Sale(driver);
            oPage_Master = new Page_Master(driver);
            oPage_SalesOrder = new pages.store.OpticalStore.Page_SalesOrder(driver);

            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectStoreOnApp(oPharmacyStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 8);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

            //Opening the Sale Transaction Page
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 8);

            itemDetails1 = getItemDetails(itemName1);
            itemDetails2 = getItemDetails(itemName2);
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

                //Adding Item 1
                addItemFromListCreateSalesOrder(itemName2);

                //Validate Remove Functionality
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_removeFromList, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_removeFromList), "Clicked Remove Item from list Button");
                Cls_Generic_Methods.customWait();
                boolean bRemoveStatus = Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.button_removeFromList);
                m_assert.assertTrue(bRemoveStatus, "Validated Remove Item Functionality ->Item Not Removed if only one item is selected");

                addItemFromListCreateSalesOrder( itemName1);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_removeFromList, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_removeFromList), "Clicked Remove Item from list Button");
                Cls_Generic_Methods.customWait();
                if (oPage_SalesOrder.list_itemNameInTableCreateSalesOrder.size() == 1) {
                    m_assert.assertFalse(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.list_itemNameInTableCreateSalesOrder.get(0)).equals(itemName2), "Validated Remove Item Functionality ->Item Not Removed");
                } else {
                    m_assert.assertFatal("Unable to validate Remove Item Functionality");
                }

                //Adding Removed Item Back
                addItemFromListCreateSalesOrder( itemName2);
                Cls_Generic_Methods.customWait();

                //Enter Doctor Name
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_doctorName, expectedLoggedInUser), "Entered <b>" + expectedLoggedInUser + "</b> in Doctor Name");

                //Set Date And Time
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.text_TxnDate);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_todayBillDate, 3);
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_todayBillDate);
                Cls_Generic_Methods.customWait();
                sTxnDate = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_TxnDate, "value").trim();
                m_assert.assertInfo("Selected Sales Order Date as : <b>" + sTxnDate + "</b>");
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_doctorName);
                sTxnTime = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderTime, "value");
                salesOrderCreatedAt = getCurrentDateTime();

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

                fillItemDetailsCreateSalesOrder(itemDetails1, item1Qty, item1Discount);
                fillItemDetailsCreateSalesOrder(itemDetails2, item2Qty, item2Discount);

                validateTotalCalculation();
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_SaveChanges);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_closeModalOfSalesOrder,10);
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_closeModalOfSalesOrder);
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

                validateRHSSalesOrder();
                Cls_Generic_Methods.customWait(3);

                selectSalesOrder(salesOrderId);
                Cls_Generic_Methods.customWait();

                //Validate Print and Email
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_printOrder,10);
                m_assert.assertTrue(validatePrintPageOpened(oPage_SalesOrder.button_printOrder),"Validated Sales Order Print Page Opened");
                m_assert.assertTrue(validateEmail(oPage_SalesOrder.button_emailOrder,"[AUTO TEST] SALES ORDER PRINT"),"Validated Sales Order Email Sent");

                Cls_Generic_Methods.customWait(7);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_printInvoice,10);
                m_assert.assertTrue(validatePrintPageOpened(oPage_SalesOrder.button_printInvoice),"Validated Sales Order Invoice Print Page Opened");
                m_assert.assertTrue(validateEmail(oPage_SalesOrder.button_emailInvoice,"[AUTO TEST] SALES ORDER INVOICE PRINT"),"Validated Sales Order Invoice Email Sent");
                Cls_Generic_Methods.customWait(7);

                //Checking Stock
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 8);

                HashMap<String, String> itemDetails1AfterSales = getItemDetails(itemName1);
                HashMap<String, String> itemDetails2AfterSales = getItemDetails(itemName2);

                double stockAfterSales = CommonActions.convertStringToDouble(itemDetails1AfterSales.get("STOCK"));
                double stockBeforeSales = CommonActions.convertStringToDouble(itemDetails1.get("STOCK"));
                m_assert.assertTrue(stockAfterSales == stockBeforeSales - (CommonActions.convertStringToDouble(item1Qty)), "Validated Item (" + itemName1 + ") Stock reduced from <b>" + stockBeforeSales + "</b> to <b>" + stockAfterSales + "</b>");

                stockAfterSales = CommonActions.convertStringToDouble(itemDetails2AfterSales.get("STOCK"));
                stockBeforeSales = CommonActions.convertStringToDouble(itemDetails2.get("STOCK"));
                m_assert.assertTrue(stockAfterSales == stockBeforeSales - (CommonActions.convertStringToDouble(item2Qty)), "Validated Item (" + itemName2 + ") Stock reduced from <b>" + stockBeforeSales + "</b> to <b>" + stockAfterSales + "</b>");

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

    @Test(enabled = true, description = "Validate Global Discount in Sales Order")
    public void validateGlobalDiscountInSalesOrder() {

        try {
            //Opening Optical Store
            oPage_CommonElements = new Page_CommonElements(driver);
            oPage_Sale = new Page_Sale(driver);
            oPage_Master = new Page_Master(driver);
            oPage_SalesOrder = new Page_SalesOrder(driver);
            Page_Indent oPage_Indent = new Page_Indent(driver);
            String qty = "2";
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectStoreOnApp(oPharmacyStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 8);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();


            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_addNewButtonInOrder, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addNewButtonInOrder), "Sales Order New Button Clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.list_PatientSearch, 16);

            try {
                //Adding patient for Optical Sales Order
                createNewPatientInSalesOrder();
                Cls_Generic_Methods.customWait(10);
                //Validate Header
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_headerSalesOrder, 20);
                String headerValue = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_headerSalesOrder);
                m_assert.assertTrue(headerValue.contains(concatPatientFullName), "Validated Displayed Sales Order Header -><b>" + headerValue + "</b>");
                int index = 0;

                List<String> stockList = new ArrayList<>();

                for(WebElement itemName : oPage_SalesOrder.list_namesOfMedicinesRawOnLeftInSearchResult){

                    String expiredText = Cls_Generic_Methods.getElementAttribute(itemName,"class");
                    String stockOnUi = Cls_Generic_Methods.getTextInElement(itemName.findElement(By.xpath("./td/div[2]/span[2]/b")));
                    stockList.add(stockOnUi);
                    if((!expiredText.contains("expired")) && index <10
                            && Double.parseDouble(stockOnUi) > 0){
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemName),
                                " Item Selected as "+ Cls_Generic_Methods.getTextInElement(itemName));
                        Cls_Generic_Methods.customWait(2);
                        try{
                            if(Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.input_selectUnitItem)){
                                Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_selectUnitItem);
                                Cls_Generic_Methods.scrollToElementByJS(oPage_SalesOrder.button_saveChangesUnitLevel);
                                Cls_Generic_Methods.customWait(1);
                                Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_saveChangesUnitLevel);
                                Cls_Generic_Methods.customWait(2);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        index++;
                    }
                    else if(index >= 10){
                        break;
                    }
                }


                List<String> qtyList = new ArrayList<>();
                List<String> mrpList = new ArrayList<>();
                List<String> discountList = new ArrayList<>();


                for(WebElement qtyRow : oPage_SalesOrder.list_createSalesOrderTableRow){

                    int indexOfQty = oPage_SalesOrder.list_createSalesOrderTableRow.indexOf(qtyRow);
                    WebElement qtyInput = qtyRow.findElement(By.xpath("./td[6]/input[1]"));
                    String qtyDefaultValue = Cls_Generic_Methods.getElementAttribute(qtyInput,"value");
                    boolean stockCheck = Double.parseDouble(stockList.get(indexOfQty))> Double.parseDouble(qty);
                    if(qtyDefaultValue.isEmpty() && stockCheck){
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(qtyInput,qty),
                                " Quantity Entered as : "+qty);
                        qtyList.add(qty);
                    }else if(qtyDefaultValue.isEmpty() && !stockCheck){
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(qtyInput,stockList.get(indexOfQty)),
                                "Quantity Entered as : "+stockList.get(indexOfQty));
                        qtyList.add(stockList.get(indexOfQty));
                    }
                    else{
                        m_assert.assertTrue("Quantity Entered as : "+qtyDefaultValue);
                        qtyList.add(qtyDefaultValue);
                    }
                }

                for(WebElement mrpRow : oPage_SalesOrder.list_createSalesOrderTableRow){

                    WebElement mrpInput = mrpRow.findElement(By.xpath("./td[8]"));
                    String mrpDefaultValue = Cls_Generic_Methods.getTextInElement(mrpInput);
                    mrpList.add(mrpDefaultValue);
                }

                Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_globalDiscountField,"10");
                Cls_Generic_Methods.customWait(1);
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_SalesOrder.input_globalDiscountApplyButton),
                            "Clicked on apply global discount button");
                    Cls_Generic_Methods.customWait(1);

                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                    Cls_Generic_Methods.customWait();
                }

                String totalGrossAmount = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_totalGrossAmount,"value");
                DecimalFormat decimalFormat = new DecimalFormat("0.00");

                for( int i =0 ;i<mrpList.size() ;i++) {
                    String grossPerItemCalculated = decimalFormat.format(Double.parseDouble(mrpList.get(i))* Double.parseDouble(qtyList.get(i)));
                    String globalDiscountPerItemCalculated = getGlobalDiscount("10"+"%", totalGrossAmount, grossPerItemCalculated);
                    String globalDiscountPerItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.list_discountSalesOrderTableRow.get(i));
                    discountList.add(globalDiscountPerItemOnUI);
                    m_assert.assertTrue(globalDiscountPerItemOnUI.equalsIgnoreCase(globalDiscountPerItemCalculated),
                            " Global Discount Working correctly as discount apply correctly for item as : "+globalDiscountPerItemOnUI+" at index "+i);
                }

                Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_saveChangesOnSalesOrder);
                Cls_Generic_Methods.customWait(5);


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


    private HashMap<String, String> getItemDetails(String itemDescription) {
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
    private String getCurrentDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | hh:mm a");
        Date date = new Date();
        //  07-06-2023 | 01:09 PM
        String date1 = dateFormat.format(date);
        return date1;
    }
    private void addItemFromListCreateSalesOrder(String itemDescription) {
        try {

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_searchMedicineNameInDescription, 8);
            Cls_Generic_Methods.clearValuesInElement(oPage_SalesOrder.input_searchMedicineNameInDescription);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_searchMedicineNameInDescription, itemDescription), "Entering the Item name as <b>" + itemDescription + "</b> in description");
            Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_searchMedicineNameInDescription);
            Cls_Generic_Methods.customWait();
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
    public boolean checkIfDateIsFuture(String dateString) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate givenDate = LocalDate.parse(dateString, dateFormatter);
        LocalDate currentDate = LocalDate.now();
        return givenDate.isAfter(currentDate);
    }
    public void fillItemDetailsCreateSalesOrder(HashMap<String, String> itemDetails, String itemQuantity, String discount) {

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

            double dPaidAmount2 =CommonActions.convertStringToDouble(sPaidAmount2);
            sPaidAmount1=String.valueOf(formatDecimalNumber(remainingBalance)- dPaidAmount2);
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_SalesOrder.select_modeOfPaymentReceived.get(0), modeOfPayment), "Selected mode of payment as " + modeOfPayment);
            Cls_Generic_Methods.clearValuesInElement(oPage_SalesOrder.input_amountPaymentReceived.get(0));
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_amountPaymentReceived.get(0), sPaidAmount1), "Entered Payment Received by "+modeOfPayment+" amount = <b>" + sPaidAmount1+"</b>");

            //Adding new advance
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addPaymentReceived), "Clicked <b>Add Payment</b> button");
            Cls_Generic_Methods.customWait();
            if (oPage_SalesOrder.select_modeOfPaymentReceived.size() == 2) {
                m_assert.assertTrue("New Mode of Payment option Added");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_SalesOrder.select_modeOfPaymentReceived.get(1), "Card"), "Selected mode of payment as <b>Card</b>");
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(oPage_SalesOrder.select_cardMachineMop, 1), "Selected Card Machine as <b>" + Cls_Generic_Methods.getSelectedValue(oPage_SalesOrder.select_cardMachineMop) + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_transactionIdNo, "1234"), "Entered Transaction Id No : 1234");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_batchNoMop, "1234"), "Entered Batch No : 1234");
                Cls_Generic_Methods.clearValuesInElement(oPage_SalesOrder.input_amountPaymentReceived.get(1));
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_amountPaymentReceived.get(1), sPaidAmount2), "Entered Payment Received by Card amount " + sPaidAmount2);
                totalAmountPaid = CommonActions.convertStringToDouble(sPaidAmount1) + dPaidAmount2;
            }

            Cls_Generic_Methods.customWait();
            String displayedTotalAmountReceived = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.input_displayedTotalAmount, "value");
            m_assert.assertTrue(CommonActions.convertStringToDouble(displayedTotalAmountReceived) == totalAmountPaid, "Validated Displayed Total Amount Received : " + displayedTotalAmountReceived);

            //Checking Remaining Balance After Advance
            remainingBalance =0;
            sBalancePending = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_balancePendingAmountPaymentReceived, "value");
            m_assert.assertTrue(remainingBalance == CommonActions.convertStringToDouble(sBalancePending), "Validated -> Balance/pending amount after Payment <b>" + sBalancePending + "</b>");

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

            String deliveryDateInTable = getTableValueCreatedSalesOrder(salesOrderId, "Delivery Date");
            m_assert.assertTrue(CommonActions.getRequiredFormattedDateTime("dd-mm-yyyy","dd/mm/yyyy",deliveryDateInTable).equals(sTxnDate), "Validated Estimated Delivery Date : <b>" + deliveryDateInTable + "</b> in created sales order list");

            String orderStatusInTable = getTableValueCreatedSalesOrder(salesOrderId, "Order Status");

            m_assert.assertTrue(orderStatusInTable.equalsIgnoreCase("Delivered"), "Validated Order Status : <b>" + orderStatusInTable + "</b> in created sales order list");

            String amountInTable = getTableValueCreatedSalesOrder(salesOrderId, "Amount");
            m_assert.assertTrue(CommonActions.convertStringToDouble(amountInTable) == totalGrossAmount, "Validated Amount : <b>" + amountInTable + "</b> in created sales order list");

            selectSalesOrder(salesOrderId);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_Patient, 10);

            //Rhs validation
            m_assert.assertTrue(concatPatientFullName.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_Patient)), "Validated Sales Order RHS -> Patient name : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_Patient) + "</b>");
            m_assert.assertTrue(expectedLoggedInUser.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsDoctorName)), "Validated Sales Order RHS -> Doctor name : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsDoctorName) + "</b>");
            m_assert.assertTrue(salesOrderId.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsOrderNumber)), "Validated Sales Order RHS -> Order Number : <b>" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsOrderNumber) + "</b>");
            patientId = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsPatientId);

            //RHS TABLE VALIDATION *ID-Item Detail

            validatedSalesOrderRhsItemDetails(itemDetails1);

            if (!bOneItem) {
                validatedSalesOrderRhsItemDetails(itemDetails2);
            }

            double actualTotalAmountIncTaxInID = CommonActions.convertStringToDouble(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_totalAmountIncTaxItemDetails));
            double actualDiscountInID = CommonActions.convertStringToDouble(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_discountItemDetails));
            double actualOfferInID = CommonActions.convertStringToDouble(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_offerItemDetails));
            double actualNetAmountInID = CommonActions.convertStringToDouble(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_netAmountItemDetails));
            double actualPaymentReceived = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_PaymentReceived).split(" ")[0]);
            double actualTaxableAmountInID = CommonActions.convertStringToDouble(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_taxableAmountItemDetails));

            if (totalGST5Amount > 0) {
                double actualGst5InID = CommonActions.convertStringToDouble(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_gst5ItemDetails));
                m_assert.assertTrue(formatDecimalNumber(totalGST5Amount) == actualGst5InID, "Validated Item Details -> GST5 Amount = <b>" + actualGst5InID + "</b>");

            }
            if (totalGST18Amount > 0) {
                double actualGst18InID = CommonActions.convertStringToDouble(Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_gst18ItemDetails));
                m_assert.assertTrue(formatDecimalNumber(totalGST18Amount) == actualGst18InID, "Validated Item Details -> GST18 Amount = <b>" + actualGst18InID + "</b>");

            }

            m_assert.assertTrue(formatDecimalNumber(totalTaxableAmount) == actualTaxableAmountInID, "Validated Item Details -> Taxable Amount = <b>" + actualTaxableAmountInID + "</b>");
            m_assert.assertTrue(totalGrossAmount == actualTotalAmountIncTaxInID, "Validated Item Details -> Total Amount Including Tax = <b>" + actualTotalAmountIncTaxInID + "</b>");
            m_assert.assertTrue(totalDiscount == actualDiscountInID, "Validated Item Details -> Discount Amount = <b>" + actualDiscountInID + "</b>");
            m_assert.assertTrue(formatDecimalNumber(totalOffer) == actualOfferInID, "Validated Item Details -> Offer Amount = <b>" + actualOfferInID + "</b>");
            m_assert.assertTrue(formatDecimalNumber(totalNetAmount) == actualNetAmountInID, "Validated Item Details -> Net Amount = <b>" + actualNetAmountInID + "</b>");
            m_assert.assertTrue(totalAmountPaid == actualPaymentReceived, "Validated Item Details -> Payment Received amount = <b>" + actualPaymentReceived + "</b>");

            Cls_Generic_Methods.customWait(4);

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }
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

    public String getGlobalDiscount(String discount, String totalGrossAmount,String grossAmountPerItem){

        String globalDiscountValue = "";
        Double globalDiscountCalculated =0.00;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        try{
            if (discount.contains("%")){
                discount = discount.replace("%","").trim();
                globalDiscountCalculated = ((Double.parseDouble(discount)) *
                        Double.parseDouble(grossAmountPerItem))/100;
                globalDiscountValue = decimalFormat.format(globalDiscountCalculated);

            }else {
                globalDiscountCalculated = (Double.parseDouble(discount) /Double.parseDouble(totalGrossAmount)) *
                        Double.parseDouble(grossAmountPerItem);
                globalDiscountValue = decimalFormat.format(formatDecimalNumber(globalDiscountCalculated));

            }



        }catch (Exception e){
            e.printStackTrace();
        }
        return globalDiscountValue;
    }

}




