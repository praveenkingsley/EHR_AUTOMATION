package tests.inventoryStores.pharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.templates.Page_InventorySearchCommonElements;
import pages.store.PharmacyStore.Items.Page_Lot;
import pages.store.PharmacyStore.Transaction.Page_Adjustment;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_SON;
import pages.store.PharmacyStore.Transaction.Page_Sale;
import tests.inventoryStores.pharmacyStore.InventorySearchTest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static pages.commonElements.CommonActions.selectOptionAndSearch;

public class AdjustmentTest extends TestBase {

    String itemName[] = {"Adjustment_Automation_01","Adjustment_Automation_02"};
    String batchNo[] = {"BN1234578","BN12345789"};
    String addDeductAction[] = {"Deduct From Lot","Add to Lot"};
    List<Double> dAvailableStock = new ArrayList<>();
    String adjustedQuantity = "1";
    String pharmacyStoreName = "Pharmacy automation- Pharmacy";
    List<String> sCostPrice = new ArrayList<>();
    String adjustmentHeaderList[] = {"Description","Batch No.","Barcode","Model No.","Expiry","Add/Deduct","Quantity","Total Cost Price","Action"};

    String transactionNote = "Adjustment_Note_"+CommonActions.getRandomString(5);
    String adjustmentDate,transactionIdSetting ;
    Double totalCostForAllItem = 0.00 ;
    String transactionDetailsHeaderList[] = {"Description","Batch No.","Model No.","Expiry","Add/Deduct","Quantity","Total Price"};

    List<String> transactionDetailsValueArrayList = new ArrayList<>();
    List<String> totalCostList = new ArrayList<>();


    @Test(enabled = true, description = "Open Pharmacy Store and select Lot and validate the available stock for the given item and create adjustment transaction")
    public void validatingAdjustmentTransactionAndValidateLOT() {

        Page_Adjustment oPage_Adjustment = new Page_Adjustment(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Lot oPage_Lot = new Page_Lot(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_Sale oPage_Sale = new Page_Sale(driver);

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(pharmacyStoreName);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 20);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
                Cls_Generic_Methods.customWait(3);

                for(String item : itemName) {

                    Cls_Generic_Methods.clearValuesInElement(oPage_Lot.input_InventorySearch);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_InventorySearch, item);
                    oPage_Lot.input_InventorySearch.sendKeys(Keys.ENTER);
                    Cls_Generic_Methods.customWait(3);
                    boolean bItemFoundInLot = false;
                    for (WebElement eVariant : oPage_Lot.list_LotDetailsOnVariants) {
                        if (Cls_Generic_Methods.getTextInElement(eVariant).contains(item)) {
                            Cls_Generic_Methods.clickElement(eVariant);
                            bItemFoundInLot = true;
                            break;
                        }
                    }
                    m_assert.assertTrue(bItemFoundInLot, "<b>"+item+" </b> found in Items Lot Page");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Adjustment.link_Edit, 10);

                    //Validate the available stock is greater than 0 or not
                    String sAvailableStock = Cls_Generic_Methods.getTextInElement(oPage_Lot.value_AvailableStock);
                    dAvailableStock.add(Double.parseDouble(sAvailableStock));
                    sCostPrice.add(Cls_Generic_Methods.getTextInElement(oPage_Lot.text_costPrice).replace("₹", ""));

                }

                if (dAvailableStock.get(0) > 0 && dAvailableStock.get(1) > 0) {

                    m_assert.assertTrue("<b> The Available stock for the "+itemName[0]+" is : " + dAvailableStock.get(0) + "</b>");
                    m_assert.assertTrue("<b> The Available stock for the "+itemName[1]+" is : " + dAvailableStock.get(1) + "</b>");
                    m_assert.assertTrue("<b> The Cost Price for the "+itemName[0]+" is : " + sCostPrice.get(0) + "</b>");
                    m_assert.assertTrue("<b> The Cost Price for the "+itemName[1]+" is : " + sCostPrice.get(1) + "</b>");

                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Adjustment");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Adjustment.button_New, 10);

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_refreshPurchaseTransaction),
                            " <b> Refresh Button </b> Displayed In Adjustment Page");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Adjustment.button_todayFilterButton),
                            " <b> Today Filter Button </b> Displayed And Clicked In Adjustment Page");

                    Cls_Generic_Methods.customWait(4);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.text_thisMonthFilter),
                            " This Month Selected as Filter");
                    Cls_Generic_Methods.customWait(2);

                    int lengthOfRecords = oPage_Adjustment.list_transactionDateColumnList.size();
                    for(WebElement eDate : oPage_Adjustment.list_transactionDateColumnList){

                        String todayDate = Cls_Generic_Methods.getTodayDate();
                        int index = oPage_Adjustment.list_transactionDateColumnList.indexOf(eDate);
                        if((lengthOfRecords-1) == index){

                            String deliveryDate = Cls_Generic_Methods.getTextInElement(eDate).trim();

                            m_assert.assertTrue(deliveryDate.substring(5,7).equalsIgnoreCase(todayDate.substring(3,5)),
                                    " <b> This Month Filter Working </b> ");
                            Cls_Generic_Methods.customWait();
                        }
                    }

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_reportButtonInPurchaseGRN),
                            " <b> Report Button </b> Displayed In Adjustment Page");

                    m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_vendorSearchBox,"placeholder").
                            equalsIgnoreCase("Search here...")," <b> Search Box Displayed and placeholder Displayed as : Search here... </b> ");


                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Adjustment.button_New),
                            " <b> New Button </b> Clicked In adjustment");
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Adjustment.header_Adjustment, 6),
                            " Adjustment Header Displayed");

                    m_assert.assertTrue(!Cls_Generic_Methods.isElementEnabled(oPage_Adjustment.input_SaveChanges),
                            " Save Changes Button is Disabled");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Adjustment.button_Description),
                            "<b> Description Button </b> Clicked In New Adjustment Template");
                    Cls_Generic_Methods.customWait();

                    for(String item : itemName) {

                        Cls_Generic_Methods.clearValuesInElement(oPage_Adjustment.input_LotSearch);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Adjustment.input_LotSearch, item);
                        Cls_Generic_Methods.customWait( 3);
                        oPage_Adjustment.input_LotSearch.sendKeys(Keys.BACK_SPACE);
                        Cls_Generic_Methods.customWait( 3);

                        boolean myMedicationFound = false;

                        for (WebElement eMedication : oPage_Adjustment.list_namesOfMedicinesOnLeftInSearchResult) {

                            String medicationName = Cls_Generic_Methods.getTextInElement(eMedication);
                            if (medicationName.equalsIgnoreCase(item)) {
                                Cls_Generic_Methods.clickElement(eMedication);
                                myMedicationFound = true;
                                break;
                            }

                        }

                        m_assert.assertTrue(myMedicationFound, "Validated medication description " + item + " found in adjustment");
                        Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Adjustment.list_inputQtyList, 5);

                    }

                    for(WebElement eHeader : oPage_Adjustment.list_adjustmentTableHeaderList ){

                        int index = oPage_Adjustment.list_adjustmentTableHeaderList.indexOf(eHeader);
                        String eHeaderValue = Cls_Generic_Methods.getTextInElement(eHeader);
                        if(eHeaderValue.equalsIgnoreCase(adjustmentHeaderList[index])){
                            m_assert.assertTrue("<b>"+eHeaderValue+" </b> Header Present In Table In Add Adjustment Template");
                        }else{
                            m_assert.assertWarn(" Either Column Is Missing or Wrong Spelling In Table In Add Adjustment Template");
                        }
                    }

                    for(WebElement eHeader : oPage_Adjustment.list_adjustmentItemNameList ){

                        int index = oPage_Adjustment.list_adjustmentItemNameList.indexOf(eHeader);
                        String eHeaderValue = Cls_Generic_Methods.getTextInElement(eHeader);
                        if(eHeaderValue.equalsIgnoreCase(itemName[index])){
                            m_assert.assertTrue(eHeaderValue+" Item Present In Table In Add Adjustment Template");
                        }else{
                            m_assert.assertWarn(" Either Column Is Missing or Wrong Spelling In Table In Add Adjustment Template");
                        }
                    }


                    for(WebElement eHeader : oPage_Adjustment.list_adjustmentBatchNoList ){

                        int index = oPage_Adjustment.list_adjustmentBatchNoList.indexOf(eHeader);
                        String eHeaderValue = Cls_Generic_Methods.getTextInElement(eHeader);
                        if(eHeaderValue.equalsIgnoreCase(batchNo[index])){
                            m_assert.assertTrue(eHeaderValue+" Batch No Present In Table In Add Adjustment Template");
                        }else{
                            m_assert.assertWarn(" Either Column Is Missing or Wrong Spelling In Table In Add Adjustment Template");
                        }
                    }

                    for(WebElement eHeader : oPage_Adjustment.list_adjustmentBarcodeList){

                        String eHeaderValue = Cls_Generic_Methods.getTextInElement(eHeader);
                        if( !eHeaderValue.isEmpty()){
                            m_assert.assertTrue(eHeaderValue+" Barcode Present In Table In Add Adjustment Template ");
                        }else{
                            m_assert.assertWarn(" Either Column Is Missing or Wrong Spelling In Table In Add Adjustment Template");
                        }
                    }
                    for(WebElement eHeader : oPage_Adjustment.list_adjustmentModelNoList){

                        String eHeaderValue = Cls_Generic_Methods.getTextInElement(eHeader);
                        if(eHeaderValue.equalsIgnoreCase("Mod12345") || eHeaderValue.isEmpty()){
                            m_assert.assertTrue(eHeaderValue+" Model No Present In Add Adjustment Template");
                        }else{
                            m_assert.assertWarn(" Either Column Is Missing or Wrong Spelling Table In Add Adjustment Template");
                        }
                    }

                    for(WebElement eHeader : oPage_Adjustment.list_adjustmentExpiryList){

                        String eHeaderValue = Cls_Generic_Methods.getTextInElement(eHeader);
                        if(eHeaderValue.equalsIgnoreCase("N.A") || (!eHeaderValue.isEmpty())){
                            m_assert.assertTrue(eHeaderValue+" Expiry Date Present In Table In Add Adjustment Template");
                        }else{
                            m_assert.assertWarn(" Either Column Is Missing or Wrong Spelling In  Table In Add Adjustment Template");
                        }
                    }

                    for(WebElement eDelete : oPage_Adjustment.list_adjustmentDeleteButtonList){

                        int index = oPage_Adjustment.list_adjustmentDeleteButtonList.indexOf(eDelete);
                        if(Cls_Generic_Methods.isElementDisplayed(eDelete)){
                            if( index == 1){
                                Cls_Generic_Methods.clickElement(eDelete);
                                Cls_Generic_Methods.customWait();
                            }
                            m_assert.assertTrue(" Delete Button Present In Adjustment Table In Table In Add Adjustment Template");
                        }else{

                            m_assert.assertWarn(" Either Column Is Missing or Wrong Spelling In Table In Add Adjustment Template");
                        }
                    }

                    m_assert.assertTrue(oPage_Adjustment.list_adjustmentDeleteButtonList.size() == 1 ," Delete Button Is working");


                    Cls_Generic_Methods.clearValuesInElement(oPage_Adjustment.input_LotSearch);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Adjustment.input_LotSearch, itemName[1]);
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Adjustment.list_namesOfMedicinesOnLeftInSearchResult, 10);
                    oPage_Adjustment.input_LotSearch.sendKeys(Keys.BACK_SPACE);
                    Cls_Generic_Methods.customWait( 3);

                    boolean myMedicationFound = false;
                    Cls_Generic_Methods.customWait(5);

                    for (WebElement eMedication : oPage_Adjustment.list_namesOfMedicinesOnLeftInSearchResult) {
                        if (Cls_Generic_Methods.getTextInElement(eMedication).equalsIgnoreCase(itemName[1])) {
                            Cls_Generic_Methods.clickElement(eMedication);
                            myMedicationFound = true;
                            break;
                        }

                    }

                    m_assert.assertTrue(myMedicationFound, "Validated medication description " + itemName[1] + " found in adjustment");
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Adjustment.list_inputQtyList, 5);

                    //Validate the error message if qty is more than available stock

                    for(WebElement eSelect : oPage_Adjustment.list_adjustmentAddDeductSelectList) {
                        int index = oPage_Adjustment.list_adjustmentAddDeductSelectList.indexOf(eSelect);
                        m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(eSelect,addDeductAction[index]),
                                addDeductAction[index]+"Selected For "+itemName[index]);
                    }

                    for(WebElement eQty : oPage_Adjustment.list_inputQtyList) {

                        int index = oPage_Adjustment.list_inputQtyList.indexOf(eQty);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(eQty,String.valueOf(dAvailableStock.get(index) + 1)),
                                " Qty Entered as "+dAvailableStock.get(index) + 1);
                        Cls_Generic_Methods.clickElement(oPage_Adjustment.input_SaveChanges);

                        if (Cls_Generic_Methods.getElementAttribute(eQty, "class").contains("error")) {
                            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Adjustment.text_adjustmentError),
                                    "<b> Validation message displayed :" + Cls_Generic_Methods.getTextInElement(oPage_Adjustment.text_adjustmentError) + "</b> ");
                        } else {
                            m_assert.assertTrue(false,"No quantity validation message displayed for");
                        }
                        break;
                    }

                    for(WebElement eQty : oPage_Adjustment.list_inputQtyList) {

                        int index = oPage_Adjustment.list_inputQtyList.indexOf(eQty);
                        Cls_Generic_Methods.clearValuesInElement(eQty);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(eQty,adjustedQuantity),
                                " Qty Entered as : "+adjustedQuantity+"for item "+itemName[index]);
                    }


                    for(WebElement eTotalCost : oPage_Adjustment.list_adjustmentTotalCostList) {

                        int index = oPage_Adjustment.list_adjustmentTotalCostList.indexOf(eTotalCost);

                        String totalCostValue = Cls_Generic_Methods.getElementAttribute(eTotalCost,"value");
                        String totalCostCal = String.format("%.2f",Double.parseDouble(adjustedQuantity)*Double.parseDouble(sCostPrice.get(index)));
                        totalCostList.add(totalCostCal);
                        totalCostForAllItem = totalCostForAllItem+ Double.parseDouble(totalCostCal);
                        m_assert.assertTrue(totalCostValue.equalsIgnoreCase(totalCostCal),
                                "Total Cost Calculated Correctly as :"+totalCostValue+" For Item : "+itemName[index]);
                    }

                    String totalAmountIncludingTaxUI = Cls_Generic_Methods.getElementAttribute(oPage_Adjustment.input_totalAmountIncludingTax,"value");

                    m_assert.assertTrue(totalAmountIncludingTaxUI.equalsIgnoreCase(String.format("%.2f",totalCostForAllItem)),
                            "Total Amount Including Tax Calculated Correctly as :"+totalAmountIncludingTaxUI);

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Adjustment.input_transactionNote,transactionNote),
                    " Transaction Notes Entered As :"+transactionNote);

                    adjustmentDate = Cls_Generic_Methods.getElementAttribute(oPage_Adjustment.input_transactionDate,"value");
                    adjustmentDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy","yyyy-MM-dd",adjustmentDate);
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Adjustment.input_SaveChanges),
                            " Save Changes Button Clicked");


                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Adjustment.row_AdjustmentTransaction, 10);
                    m_assert.assertTrue("<b> Adjustment transaction is created for " + itemName + " of quantity: " + adjustedQuantity + "</b> ");
                } else {
                    m_assert.assertWarn("Available stock is not > 0 (" + dAvailableStock + ") for item = " + itemName + ". So can't do adjustment for the LOT");
                }

                //Validate stock after adjustment
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
                Cls_Generic_Methods.customWait(3);

                for(String item : itemName) {

                    Cls_Generic_Methods.clearValuesInElement(oPage_Lot.input_InventorySearch);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_InventorySearch, item);
                    Cls_Generic_Methods.customWait(3);
                    oPage_Lot.input_InventorySearch.sendKeys(Keys.ENTER);
                    Cls_Generic_Methods.customWait(3);
                    boolean bItemFoundInLot1 = false;
                    for (WebElement eVariant : oPage_Lot.list_LotDetailsOnVariants) {
                        if (Cls_Generic_Methods.getTextInElement(eVariant).contains(item)) {
                            Cls_Generic_Methods.clickElement(eVariant);
                            bItemFoundInLot1 = true;
                            break;
                        }
                    }

                    m_assert.assertTrue(bItemFoundInLot1, "Item found in Items Lot Page");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Adjustment.link_Edit, 10);
                    String sCurrentAvailableStock = Cls_Generic_Methods.getTextInElement(oPage_Lot.value_AvailableStock);
                    double dCurrentAvailableStock = Double.parseDouble(sCurrentAvailableStock);
                    double dAdjustedQuantity = Double.parseDouble(adjustedQuantity);

                    //Validate previous stock - qty should equals to current stock.

                    if (dAvailableStock.get(0) - dAdjustedQuantity == dCurrentAvailableStock) {

                        m_assert.assertTrue("<b> The final stock available after adjustment for the item " + item + " is: " + dCurrentAvailableStock + "</b> ");
                    } else {
                        if (dAvailableStock.get(1) + dAdjustedQuantity == dCurrentAvailableStock) {
                            m_assert.assertTrue("<b> The final stock available after adjustment for the item " + item + " is: " + dCurrentAvailableStock + "</b> ");
                        } else {
                            m_assert.assertFalse("Stock is mismatched. Current Stock = " + sCurrentAvailableStock + " | Adjusted Stock = " + String.valueOf(dAdjustedQuantity));
                        }
                    }
                }

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

    @Test(enabled = true, description = "View Created Adjustment")
    public void validatingViewAdjustmentTransactionAndValidateLOT() {

        Page_Adjustment oPage_Adjustment = new Page_Adjustment(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        String viewAdjustmentTableHeaderList[] = {"Transaction Date","Store","Note","Amount"};
        boolean bAdjustmentFound = false ;

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Adjustment.button_New, 10);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(2);
            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Sequence Manager");
            Cls_Generic_Methods.customWait(5);
            transactionIdSetting = Cls_Generic_Methods.getTextInElement(oPage_Adjustment.text_transactionIdInSetting);

            CommonActions.selectStoreOnApp(pharmacyStoreName);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 20);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait(4);
            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Adjustment");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Adjustment.button_New, 10);

                for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
                    int indexOfHeader = oPage_Purchase.list_purchaseTransactionHeaderList.indexOf(purchaseHeaderList);
                    String sValueOfHeader = Cls_Generic_Methods.getTextInElement(purchaseHeaderList);
                    if(sValueOfHeader.equalsIgnoreCase(viewAdjustmentTableHeaderList[indexOfHeader])){
                        m_assert.assertTrue( "<b>"+sValueOfHeader + " </b> Header Present In View Adjustment Page");
                    }else{
                        m_assert.assertWarn(sValueOfHeader + " Header Not Present In SON Data Table List");
                    }
                }


                for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

                    if (Cls_Generic_Methods.isElementDisplayed(row)) {
                        List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

                        String dateOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(0));
                        String storeOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(1));
                        String noteOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(2));
                        String amountOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(3));


                        if (dateOnUI.equals(adjustmentDate) &&
                                pharmacyStoreName.contains(storeOnUI) &&
                                noteOnUI.equalsIgnoreCase(transactionNote) &&
                                String.valueOf(totalCostForAllItem).equalsIgnoreCase(amountOnUI)
                        ) {
                            bAdjustmentFound = true;
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(row),
                                    "Adjustment Transaction Clicked In Transaction List");
                            Cls_Generic_Methods.customWait(2);
                            break;
                        }

                    }
                }


                m_assert.assertTrue(bAdjustmentFound," Adjustment Record Found And Clicked");

                if(bAdjustmentFound) {

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Adjustment.header_viewAdjustmentHeader).equalsIgnoreCase(transactionNote),
                            " View Adjustment Header Displayed as :" + transactionNote);

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Adjustment.text_transactionIdInAdjustment).contains(transactionIdSetting),
                            " Transaction Id Displayed as :" + transactionIdSetting);
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Adjustment.text_transactionDetails),
                            " Transaction Details  Header Displayed ");

                    for (WebElement adjustmentHeaderList : oPage_Adjustment.list_transactionDetailsHeaderList) {
                        int indexOfHeader = oPage_Adjustment.list_transactionDetailsHeaderList.indexOf(adjustmentHeaderList);
                        String sValueOfHeader = Cls_Generic_Methods.getTextInElement(adjustmentHeaderList);
                        if (sValueOfHeader.equalsIgnoreCase(transactionDetailsHeaderList[indexOfHeader])) {
                            m_assert.assertTrue("<b>" + sValueOfHeader + " </b> Header Present In Transaction Details Table");
                        } else {
                            m_assert.assertWarn(sValueOfHeader + " Header Not Present In Transaction Details Table");
                        }
                    }


                    addDataInViewAdjustment();

                    for (WebElement eValue : oPage_Adjustment.list_transactionDetailsValueList) {

                        int indexOfHeader = oPage_Adjustment.list_transactionDetailsValueList.indexOf(eValue);
                        String sValueOfHeader = Cls_Generic_Methods.getTextInElement(eValue);
                        if (sValueOfHeader.contains(transactionDetailsValueArrayList.get(indexOfHeader))) {
                            m_assert.assertTrue("<b>" + sValueOfHeader + " </b> Value Present In Transaction Details Table");
                        } else {
                            m_assert.assertWarn(sValueOfHeader + " Value Not Present Transaction Details Table");
                        }
                    }

                }
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

    @Test(enabled = true, description = "Search Functionality In Adjustment Transaction")
    public void validateSearchFunctionalityInAdjustment(){

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
        InventorySearchTest oInventorySearchTest = new InventorySearchTest();
        Page_SON oPage_SON = new Page_SON(driver);
        Page_Adjustment oPage_Adjustment = new Page_Adjustment(driver);



        String adjustmentSearchTypeList [] = {"Transaction ID","Item Description","Batch No."};
        String oldTransactionId="" ,oldItem="",oldBatchNo="";

        try{

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(pharmacyStoreName);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SON");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type  Selection Dropdown Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.input_searchBoxInput),
                        " Input Search Box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(adjustmentSearchTypeList[0]),
                        " By Default Search Type Selection Dropdown Displayed correctly as :" + adjustmentSearchTypeList[0]);
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput, "value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty by default for selected search type Displayed correctly");
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Not Displayed Correctly as Default");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput, "placeholder")
                                .contains("Search By " + adjustmentSearchTypeList[0]),
                        " Input Search Box Place holder for selected search type Displayed correctly");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_filterDropdownButton),
                        " Filter Dropdown Button Clicked");
                Cls_Generic_Methods.customWait(2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.text_thisYearFilter),
                        " This Year Selected as Filter");
                Cls_Generic_Methods.customWait(2);

                for (WebElement row : oPage_SON.list_SONTransactions) {
                    int index = oPage_SON.list_SONTransactions.indexOf(row);
                    if (index>1) {

                        Cls_Generic_Methods.clickElement(row);
                        Cls_Generic_Methods.customWait(5);
                        oldTransactionId = Cls_Generic_Methods.getTextInElement(oPage_Adjustment.text_transactionIdInAdjustment).split(":")[1].trim();
                        oldItem = Cls_Generic_Methods.getTextInElement(oPage_SON.text_variantDescription);
                        oldBatchNo = Cls_Generic_Methods.getTextInElement(oPage_SON.text_batchNoText);
                        break;

                    }
                }


                String searchValueList[] = {transactionIdSetting,oldTransactionId,itemName[0],oldItem,batchNo[0],oldBatchNo};

                for(int i = 0;i<2;i++) {
                    boolean searchResultByTransactionId = CommonActions.selectOptionAndSearch(adjustmentSearchTypeList[0], searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByTransactionId) {
                        boolean  bRequisitionOrderFound = oInventorySearchTest.getSearchedRecordAndValidateInView(oPage_SON.list_SONTransactions,oPage_Adjustment.text_transactionIdInAdjustment,searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound && oPage_SON.list_SONTransactions.size() == 1,
                                "Search By Transaction Id Worked correctly as order found in the  page for number: " + searchValueList[i]);
                    }
                }

                for(int i = 2;i<4;i++) {
                    boolean searchResultByDescription = CommonActions.selectOptionAndSearch(adjustmentSearchTypeList[1], searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByDescription) {
                        boolean  bRequisitionOrderFound = oInventorySearchTest.getSearchedRecordAndValidateInView(oPage_SON.list_SONTransactions,oPage_SON.text_variantDescription,searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound && oPage_SON.list_SONTransactions.size() == 1,
                                "Search By Item Description Worked correctly as order found in the  page for number: " + searchValueList[i]);
                    }

                }

                for(int i = 4;i<6;i++) {
                    boolean searchResultByBatchNo = CommonActions.selectOptionAndSearch(adjustmentSearchTypeList[2], searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByBatchNo) {
                        boolean  bRequisitionOrderFound = oInventorySearchTest.getSearchedRecordAndValidateInView(oPage_SON.list_SONTransactions,oPage_SON.text_batchNoText,searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound && oPage_SON.list_SONTransactions.size() == 1,
                                "Search By Batch No Worked correctly as order found in the  page for number: " + searchValueList[i]);
                    }

                }


                for(int i =0 ;i<3;i++) {

                    boolean searchResultByWrongNumber = selectOptionAndSearch(adjustmentSearchTypeList[i],"incorrectReqNumber");
                    m_assert.assertFalse(searchResultByWrongNumber," Adjustment Search With Incorrect Number Working Correct");
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
                boolean selectOption = CommonActions.selectOption(adjustmentSearchTypeList[0]);
                m_assert.assertTrue(selectOption," Able to selected Search type Again to Default");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(adjustmentSearchTypeList[0]),
                        " Search Type Selected  as : "+adjustmentSearchTypeList[0]);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type Dropdown Clicked");
                Cls_Generic_Methods.customWait();
                for(WebElement type : oPage_InventorySearchCommonElements.list_searchTypeList){
                    String typeText = Cls_Generic_Methods.getTextInElement(type);
                    int index = oPage_InventorySearchCommonElements.list_searchTypeList.indexOf(type);
                    if(typeText.equalsIgnoreCase(adjustmentSearchTypeList[index])){
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

    public void addDataInViewAdjustment(){

        transactionDetailsValueArrayList.add(itemName[0]);
        transactionDetailsValueArrayList.add(batchNo[0]);
        transactionDetailsValueArrayList.add("Mod12345");
        transactionDetailsValueArrayList.add("");
        transactionDetailsValueArrayList.add("Deduct");
        transactionDetailsValueArrayList.add(String.valueOf(Double.parseDouble(adjustedQuantity)));
        transactionDetailsValueArrayList.add(String.valueOf(Double.parseDouble(totalCostList.get(0))));
        transactionDetailsValueArrayList.add(itemName[1]);
        transactionDetailsValueArrayList.add(batchNo[1]);
        transactionDetailsValueArrayList.add("");
        transactionDetailsValueArrayList.add("2032-07-29");
        transactionDetailsValueArrayList.add("Add");
        transactionDetailsValueArrayList.add(String.valueOf(Double.parseDouble(adjustedQuantity)));
        transactionDetailsValueArrayList.add(String.valueOf(Double.parseDouble(totalCostList.get(1))));

    }

}
