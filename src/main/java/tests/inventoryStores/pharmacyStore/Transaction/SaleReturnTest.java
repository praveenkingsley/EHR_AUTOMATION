package tests.inventoryStores.pharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.python.antlr.ast.Str;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_VendorMaster;
import pages.store.OpticalStore.Page_SalesOrder;
import pages.store.Page_PatientQueue;
import pages.store.PharmacyStore.Items.Page_Lot;
import pages.store.PharmacyStore.Transaction.*;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static pages.commonElements.CommonActions.getRandomString;
import static tests.inventoryStores.pharmacyStore.Transaction.PurchaseGRNTest.convertStringToDouble;

public class SaleReturnTest extends TestBase {

	static Model_Patient myPatient;

	String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");

	String pharmacyStoreName = "Pharmacy automation- Pharmacy";
	String myMedicationName[] = {"SalesOrderTest1","Adjustment_Automation_01"};
	String modeOfPayment [] = {"Cash","Card"};
	String inputQty ="2",netAmountSaleOrder,grossAmount;
	String orderDate,orderTime,sTime,salesOrderDateAndTime,patientId;
	String createSaleReturnHeaderList[] = {"#","Description","Barcode","Batch No.","Return Qty","U.Price With Tax",
	"Tax","Discount","Return Amt.","Verified","Remarks","Action"};
	List<String> sAvailableStock = new ArrayList<>();
	List<String> listPrice = new ArrayList<>();
	String returnNotes = "Return_notes" + getRandomString(3);
	Double taxRate = 5.0;
	String remark[] ={"Remark_1","Remark_2"};
	List<String> taxAmountList = new ArrayList<>();
	List<String> taxableAmountList = new ArrayList<>();
	List<String> totalAmountItemList = new ArrayList<>();
	String viewSaleReturnTableHeaderList[] = {"Date","Recipient","Note","Amount","Action"};
	String viewSaleReturnPatientDetailsList[] = {"Return Receipt Id:","Bill No:","Patient:","Patient ID:","Patient Mobile"};
	List<String> saleReturnPatientDetails = new ArrayList<>();
	List<String> itemDetailsDataList = new ArrayList<>();
	String viewItemDetailsHeaderList[] = {"#","Code","Description","QTY","Batch","Exp.Date","U.Price with Tax","Discounts","Return Amt.","Remark"};

	String sTaxableAmount ,sSgst,sCgst,sNetReturn;
	String refundMOPReceiptTemplateHeaderList[] = {"No.","Mode Of Payment","Refund MOP ID","Amount"};
	String refundId = "",billNumber;

	@Test(enabled = true, description = "Creating Sale Return Transaction in Transaction ")
	public void validatingCreateSaleReturnTransaction() {

		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Lot oPage_Lot = new Page_Lot(driver);
		Page_Purchase oPage_Purchase = new Page_Purchase(driver);
		Page_Adjustment oPage_Adjustment = new Page_Adjustment(driver);
		Page_Sale oPage_Sale = new Page_Sale(driver);
		Page_SaleReturn oPage_SaleReturn = new Page_SaleReturn(driver);

		try {
			myPatient = map_PatientsInExcel.get(patientKey);
			CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
			CommonActions.selectStoreOnApp(pharmacyStoreName);
			Cls_Generic_Methods.switchToOtherTab();
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
			m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
					"Store pop up closed");
			Cls_Generic_Methods.customWait();
			try {

				CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
				Cls_Generic_Methods.customWait(3);
				for(String item : myMedicationName) {
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

					m_assert.assertTrue(bItemFoundInLot1, item+" Item found in Items Lot Page");
					Cls_Generic_Methods.customWait(5);

					 sAvailableStock.add(Cls_Generic_Methods.getTextInElement(oPage_Lot.value_AvailableStock));
					 listPrice.add(Cls_Generic_Methods.getTextInElement(oPage_Lot.text_mrpPrice));
				}
				CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
				Cls_Generic_Methods.customWait( );

				// creating order for cancel order button check
				boolean bCreateOrderForPolicy = createSaleOrderForPolicyCheck("Stockable");
				Cls_Generic_Methods.customWait();
				m_assert.assertTrue(bCreateOrderForPolicy," Sales Order Created");

				String recipientInfoSale = myPatient.getsFIRST_NAME();

				CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale Return");
				Cls_Generic_Methods.customWait( );

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_refreshPurchaseTransaction),
						" <b> Refresh Button </b> Displayed In Sale Return Page");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Adjustment.button_todayFilterButton),
						" <b> Today Filter Button </b> Displayed And Clicked In Sale Return Page");

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
						" <b> Report Button </b> Displayed In Sale Return Page");

				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_vendorSearchBox,"placeholder").
						equalsIgnoreCase("Search here...")," <b> Search Box Displayed and placeholder Displayed as : Search here... </b> ");

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SaleReturn.button_new),
						" <b> New Button </b> Displayed In Sale Return Page");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SaleReturn.input_searchPatient,5);
				Cls_Generic_Methods.selectElementByIndex(oPage_SaleReturn.input_searchTypeFilter,3);
				Cls_Generic_Methods.customWait();
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SaleReturn.input_searchPatient,patientId),
						"Patient id Entered In Search Box"+patientId);
				Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SaleReturn.button_searchPatient),
						"Search Button Clicked");
				Cls_Generic_Methods.customWait(5);
				boolean bCreatedPatientFound = false ;
               for (WebElement ePatientId : oPage_SaleReturn.list_searchedPatientIdList){

				   String searchedPatientId = Cls_Generic_Methods.getTextInElement(ePatientId);
				   if(searchedPatientId.equalsIgnoreCase(patientId)){
					   m_assert.assertTrue(Cls_Generic_Methods.clickElement(ePatientId),
							   searchedPatientId+ " Clicked From Search Patient List");
					   Cls_Generic_Methods.customWait(5);
					   bCreatedPatientFound = true ;
					   break;
				   }

			   }

			   m_assert.assertTrue(bCreatedPatientFound,"Sale Order Created Patient Found And Clicked In Return");

			   m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Adjustment.button_Description),
					   "Description Button Clicked");
			   Cls_Generic_Methods.customWait();

			   for(String item : myMedicationName) {

				   Cls_Generic_Methods.clearValuesInElement(oPage_Adjustment.input_LotSearch);
				   m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Adjustment.input_LotSearch, item),
						   " Search Lot By Description Displayed And Value Entered as " + item);
				   Cls_Generic_Methods.customWait();
				   oPage_Adjustment.input_LotSearch.sendKeys(Keys.ENTER);
				   Cls_Generic_Methods.customWait(3);
				   boolean myMedicationFound = false;

				   for (WebElement eMedication : oPage_Adjustment.list_namesOfMedicinesOnLeftInSearchResult) {

					   String medicationName = Cls_Generic_Methods.getTextInElement(eMedication);
					   if (medicationName.equalsIgnoreCase(item)) {
						   Cls_Generic_Methods.clickElement(eMedication);
						   myMedicationFound = true;
						   Cls_Generic_Methods.customWait();
						   break;
					   }

				   }

				   m_assert.assertTrue(myMedicationFound, "Validated medication description " + item + " found in sale return");

			   }

				for(WebElement eHeader : oPage_Adjustment.list_adjustmentTableHeaderList ){

					int index = oPage_Adjustment.list_adjustmentTableHeaderList.indexOf(eHeader);
					String eHeaderValue = Cls_Generic_Methods.getTextInElement(eHeader);
					if(eHeaderValue.equalsIgnoreCase(createSaleReturnHeaderList[index])){
						m_assert.assertTrue("<b>"+eHeaderValue+" </b> Header Present In Table In Add Sale Return Template");
					}else{
						m_assert.assertWarn(" Either Column Is Missing or Wrong Spelling In Table In Add Sale Return Template");
					}
				}

				for(WebElement eHeader : oPage_Adjustment.list_adjustmentItemNameList ){

					int index = oPage_Adjustment.list_adjustmentItemNameList.indexOf(eHeader);
					String eHeaderValue = Cls_Generic_Methods.getTextInElement(eHeader);
					if(eHeaderValue.equalsIgnoreCase(myMedicationName[index])){
						m_assert.assertTrue(eHeaderValue+" Item Present In Table In Add Sale Return Template");
					}else{
						m_assert.assertWarn(" Either Column Is Missing or Wrong Spelling In Table In Add Sale Return Template");
					}
				}

				for(WebElement eHeader : oPage_SaleReturn.list_saleReturnBarcodeList){

					String eHeaderValue = Cls_Generic_Methods.getTextInElement(eHeader);
					if(eHeaderValue.isEmpty() || (eHeaderValue.contains("RSO"))){
						m_assert.assertTrue(eHeaderValue+" Barcode Present In Table In Add Sale Return Template ");
					}else{
						m_assert.assertWarn(" Either Column Is Missing or Wrong Spelling In Table In Add Sale Return Template");
					}
				}
				for(WebElement eHeader : oPage_SaleReturn.list_saleReturnBatchNoList){

					String eHeaderValue = Cls_Generic_Methods.getTextInElement(eHeader);
					if(eHeaderValue.isEmpty() || (eHeaderValue.equalsIgnoreCase("BN1234578"))){
						m_assert.assertTrue(eHeaderValue+" Batch No Present In Table In Add Sale Return Template ");
					}else{
						m_assert.assertWarn(" Either Column Is Missing or Wrong Spelling In Table In Add Sale Return Template");
					}
				}

				for(WebElement eHeader : oPage_SaleReturn.list_saleReturnUPriceWithTaxList){

                    int index = oPage_SaleReturn.list_saleReturnUPriceWithTaxList.indexOf(eHeader);
					String eHeaderValue = Cls_Generic_Methods.getTextInElement(eHeader);
					if(Double.parseDouble(eHeaderValue) == Double.parseDouble(listPrice.get(index).replace("₹","").trim())){
						m_assert.assertTrue(eHeaderValue+" U.Price With Tax Present And Calculated In Table In Add Sale Return Template ");
					}else{
						m_assert.assertWarn(" Either Column Is Missing or Wrong Spelling In Table In Add Sale Return Template");
					}
				}


				for(WebElement eDelete : oPage_SaleReturn.list_saleReturnDeleteActionList){

					int index = oPage_SaleReturn.list_saleReturnDeleteActionList.indexOf(eDelete);
					if(Cls_Generic_Methods.isElementDisplayed(eDelete)){
						if( index == 1){
							Cls_Generic_Methods.clickElement(eDelete);
							Cls_Generic_Methods.customWait();
						}
						m_assert.assertTrue(" Delete Button Present In Sale Return Table In Table In Add Sale Return Template");
					}else{

						m_assert.assertWarn(" Either Column Is Missing or Wrong Spelling In Table In Add Sale Return Template");
					}
				}

				m_assert.assertTrue(oPage_SaleReturn.list_saleReturnDeleteActionList.size() == 1 ," Delete Button Is working");


				Cls_Generic_Methods.clearValuesInElement(oPage_Adjustment.input_LotSearch);
				Cls_Generic_Methods.sendKeysIntoElement(oPage_Adjustment.input_LotSearch, myMedicationName[1]);
				Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Adjustment.list_namesOfMedicinesOnLeftInSearchResult, 10);
				oPage_Adjustment.input_LotSearch.sendKeys(Keys.ENTER);
				Cls_Generic_Methods.customWait( 3);

				boolean myMedicationFound = false;
				Cls_Generic_Methods.customWait(5);

				for (WebElement eMedication : oPage_Adjustment.list_namesOfMedicinesOnLeftInSearchResult) {
					if (Cls_Generic_Methods.getTextInElement(eMedication).equalsIgnoreCase(myMedicationName[1])) {
						Cls_Generic_Methods.clickElement(eMedication);
						myMedicationFound = true;
						Cls_Generic_Methods.customWait(5);
						break;
					}

				}

				m_assert.assertTrue(myMedicationFound, "Validated Optical description " + myMedicationName[1] + " found in adjustment");

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SaleReturn.button_saveChanges),
						" Save Changes Button Clicked");
				Cls_Generic_Methods.customWait();

				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_returnNote, "class").contains("error"),
						" Required Error Message Displayed For Return Note");
				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.select_returnMop, "class").contains("error"),
						" Required Error Message Displayed For Return Mode Of Payment");
				for(WebElement eQty : oPage_Adjustment.list_inputQtyList) {
					int index = oPage_Adjustment.list_inputQtyList.indexOf(eQty);
					m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(eQty, "class").contains("error"),
							" Required Error Message Displayed For Input Quantity for item  "+myMedicationName[index]);
				}
				for(int i= 0 ;i<=1 ;i++) {
					m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.list_checkbox_verified.get(i), "class").contains("error"),
							" Required Error Message Displayed For Verified Checkbox for item "+myMedicationName[i]);
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SaleReturn.list_checkbox_verified.get(i)),
							"Verified Checkbox Clicked for item "+myMedicationName[i]);
				}
				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_grossAmountInReturn, "class").contains("error"),
						" Required Error Message Displayed For Gross Return Amount");

				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SaleReturn.input_returnNote,returnNotes),
						" Return Note Entered As : "+returnNotes);

				for(WebElement eQty : oPage_Adjustment.list_inputQtyList) {
					int index = oPage_Adjustment.list_inputQtyList.indexOf(eQty);
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(eQty, inputQty),
							" Return Quantity Entered as : "+inputQty+" for item  "+myMedicationName[index]);
				}
				for(WebElement eAmount : oPage_SaleReturn.list_saleReturnReturnAmountList) {

					int index = oPage_SaleReturn.list_saleReturnReturnAmountList.indexOf(eAmount);
					String returnAmountValue = Cls_Generic_Methods.getTextInElement(eAmount);
					totalAmountItemList.add(returnAmountValue);
					String uPrice = Cls_Generic_Methods.getTextInElement(oPage_SaleReturn.list_saleReturnUPriceWithTaxList.get(index));
					String returnAmountValueCalculated = String.format("%.2f",Double.parseDouble(inputQty)*Double.parseDouble(uPrice));
					m_assert.assertTrue(returnAmountValue.equalsIgnoreCase(returnAmountValueCalculated),
							" Return Amount Displayed Correctly as : "+returnAmountValue+" for item  "+myMedicationName[index]);
				}

				Double taxableAmountCalculated = 0.00 ;

				for(WebElement eAmount : oPage_SaleReturn.list_saleReturnReturnAmountList) {

					int index = oPage_SaleReturn.list_saleReturnReturnAmountList.indexOf(eAmount);
					String returnAmountValue = Cls_Generic_Methods.getTextInElement(eAmount);
					double taxableAmountPerItem = Double.parseDouble(returnAmountValue)*(100/(100+taxRate));
					String taxRateInReturn = Cls_Generic_Methods.getTextInElement(oPage_SaleReturn.list_saleReturnTaxRateList.get(index));
					String taxAmountPerItem = String.format("%.2f",Double.parseDouble(returnAmountValue) - taxableAmountPerItem) ;
					m_assert.assertTrue(taxRateInReturn.contains(taxAmountPerItem),
							" Tax Amount Calculated Correctly");

					m_assert.assertTrue(taxRateInReturn.replace("\n", " ").equalsIgnoreCase(taxAmountPerItem+" ("+taxRate+" - Inc.)"),
							" Tax Column Data Displayed Correctly as :"+taxRateInReturn);
					taxAmountList.add(taxAmountPerItem);
					taxableAmountCalculated = taxableAmountCalculated + taxableAmountPerItem;
					taxableAmountList.add(String.valueOf(taxableAmountPerItem));
				}
				for(WebElement eRemark : oPage_SaleReturn.list_saleReturnRemarkList) {
					int index = oPage_SaleReturn.list_saleReturnRemarkList.indexOf(eRemark);
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(eRemark, remark[index]),
							" Return Remark Entered as : "+remark[index]+" for item  "+myMedicationName[index]);
				}

				String taxableAmountOnUI = Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_taxableAmountInTaxCage,"value");
				m_assert.assertTrue(taxableAmountOnUI.equalsIgnoreCase(String.format("%.2f",taxableAmountCalculated)),
						"Taxable Amount Calculating And Displaying Correctly as : "+taxableAmountOnUI);

				Double sgstCalculated = (Double.parseDouble(taxAmountList.get(0))/2)+(Double.parseDouble(taxAmountList.get(1))/2);

                String sgst = Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_sgstInTaxCage,"value");
				String cgst = Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_cgstInTaxCage,"value");

				m_assert.assertTrue(sgst.equalsIgnoreCase(String.format("%.2f",sgstCalculated)),
						"SGST2.5 Amount Calculating And Displaying Correctly as :"+sgst);
				m_assert.assertTrue(cgst.equalsIgnoreCase(String.format("%.2f",sgstCalculated)),
						"CGST2.5 Amount Calculating And Displaying Correctly as :"+cgst);

				Double grossReturnAmountCalculated = Double.parseDouble(totalAmountItemList.get(0))+ Double.parseDouble(totalAmountItemList.get(1));
				String grossReturnAmount = Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_grossAmountInReturn,"value");
				m_assert.assertTrue(grossReturnAmount.equalsIgnoreCase(String.format("%.2f",grossReturnAmountCalculated)),
						"Gross Return Amount Calculating And Displaying Correctly as :"+grossReturnAmount);

				String netReturnValueUI = Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_netReturnAmountInReturn,"value");
				m_assert.assertTrue(netReturnValueUI.equalsIgnoreCase(grossReturnAmount),"Net Return Amount Displayed Correctly as "+netReturnValueUI);

				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SaleReturn.input_returnCharges,"10"),
						"Return Charges Entered as : 10");
				String grossReturnAmountAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_grossAmountInReturn,"value");
				m_assert.assertTrue(grossReturnAmount.equalsIgnoreCase(grossReturnAmountAfterDiscount),
						"Gross Return Amount Calculating And Displaying Correctly After Discount as :"+grossReturnAmountAfterDiscount);

				String netReturnValueUIAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_netReturnAmountInReturn,"value");

				Double netReturnCalculated = Double.parseDouble(grossReturnAmountAfterDiscount) - Double.parseDouble("10");
				m_assert.assertTrue(netReturnValueUIAfterDiscount.equalsIgnoreCase(String.format("%.2f",netReturnCalculated)),
						"Net Return Amount Calculating And Displaying Correctly After Discount as :"+netReturnValueUIAfterDiscount);

				String discountPercentageCal = validatingDiscountCalculation(grossReturnAmountAfterDiscount, "10");
				String TaxableAmountInCageAfterDiscountOnUI = Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_taxableAmountInTaxCage, "value");

				Double taxableAmountFirstItemAfterDiscount = Double.parseDouble(taxableAmountList.get(0)) -
						(Double.parseDouble(taxableAmountList.get(0)) * Double.parseDouble(discountPercentageCal) / 100);

				Double taxableAmountSecondItemAfterDiscount = Double.parseDouble(taxableAmountList.get(1)) -
						(Double.parseDouble(taxableAmountList.get(1)) * Double.parseDouble(discountPercentageCal) / 100);

				Double totalTaxableAmountAfterDiscount = taxableAmountFirstItemAfterDiscount + taxableAmountSecondItemAfterDiscount;

				m_assert.assertTrue(String.valueOf(totalTaxableAmountAfterDiscount).contains(TaxableAmountInCageAfterDiscountOnUI),
						" Taxable Amount in Tax Cage Calculated Correctly after Discount"+TaxableAmountInCageAfterDiscountOnUI);

				String sgstTaxValueAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_sgstInTaxCage, "value");
				String cgstTaxValueAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_cgstInTaxCage, "value");


				String sgstFirstItemAfterDiscount = String.format("%.2f",Double.parseDouble(sgst) -
						(Double.parseDouble(sgst) * Double.parseDouble(discountPercentageCal) / 100));


				String cgstFirstItemAfterDiscount = String.format("%.2f",Double.parseDouble(cgst) -
						(Double.parseDouble(cgst) * Double.parseDouble(discountPercentageCal) / 100));


				m_assert.assertTrue(sgstTaxValueAfterDiscount.equalsIgnoreCase(sgstFirstItemAfterDiscount),
						" Sgst2.5 in Tax Cage Calculated Correctly after Discount");
				m_assert.assertTrue(cgstTaxValueAfterDiscount.equalsIgnoreCase(cgstFirstItemAfterDiscount),
						" cgst2.5 in Tax Cage Calculated Correctly after Discount");

				m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_SaleReturn.select_returnMop,modeOfPayment[0]),
						"Mode Of Payment Selected as :"+modeOfPayment[0]);
				Cls_Generic_Methods.customWait();
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SaleReturn.input_returnRefundAmount,"10"),
						"Enter Refund Amount less than Net Return Amount");

				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.button_saveChanges,"class").contains("disabled"),
						" Save Changes Button Is Disabled as amount is less than Net Return Amount");
				Cls_Generic_Methods.clearValuesInElement(oPage_SaleReturn.input_returnRefundAmount);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SaleReturn.input_returnRefundAmount,netReturnValueUIAfterDiscount),
						"Enter Refund Amount equal to Net Return Amount as : "+netReturnValueUIAfterDiscount);
				Cls_Generic_Methods.customWait(1);

				orderDate = Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_dateSaleReturn,"value");
				orderTime = Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_timeSaleReturn,"value");

				grossAmount = grossReturnAmountAfterDiscount ;
				sTaxableAmount = TaxableAmountInCageAfterDiscountOnUI;
				sSgst = sgstTaxValueAfterDiscount;
				sCgst = cgstTaxValueAfterDiscount;
				sNetReturn = netReturnValueUIAfterDiscount;
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SaleReturn.button_saveChanges),
						"Save Changes Button Clicked");

				Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Sale.header_pharmacyReturnBill,10);
				refundId = Cls_Generic_Methods.getTextInElement(oPage_SaleReturn.text_returnRefundIdAfterSave);
				m_assert.assertTrue(refundId.contains("RSO"),
						" Refund Receipt Id Generated as : "+Cls_Generic_Methods.getTextInElement(oPage_SaleReturn.text_returnRefundIdAfterSave));
				boolean PrintA4InBill = validatePrintButtonFunctionality(oPage_Sale.button_printA4SizeButton,"Print4 Size");
				boolean PrintA5InBill = validatePrintButtonFunctionality(oPage_Sale.button_printA5SizeButton,"Print5 Size");

				m_assert.assertTrue(PrintA4InBill, " PrintA4 Work In Pharmacy Return Bill");
				m_assert.assertTrue(PrintA5InBill, " PrintA5 Work In Pharmacy Return Bill");

				Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
				Cls_Generic_Methods.customWait();

				for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
					int indexOfHeader = oPage_Purchase.list_purchaseTransactionHeaderList.indexOf(purchaseHeaderList);
					String sValueOfHeader = Cls_Generic_Methods.getTextInElement(purchaseHeaderList);
					if(sValueOfHeader.equalsIgnoreCase(viewSaleReturnTableHeaderList[indexOfHeader])){
						m_assert.assertTrue( "<b>"+sValueOfHeader + " </b> Header Present In View Sale Return Page");
					}else{
						m_assert.assertWarn(sValueOfHeader + " Header Not Present In Sale Return Data Table List");
					}
				}

				orderDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy","yyyy-MM-dd",orderDate);
				if(orderTime.length() == 7){
					orderTime = "0"+orderTime;
				}
				orderTime = orderTime.replace("am", "AM").replace("pm", "PM");
				salesOrderDateAndTime = orderDate+"  |  "+orderTime.substring(0,5)+orderTime.substring(6,8);

				boolean bSaleReturnFound = false ;
				for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

					if (Cls_Generic_Methods.isElementDisplayed(row)) {
						List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

						String dateAndTimeOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(0));
						String recipientOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(1));
						String noteOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(2));
						String amountOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(3));
						String actionOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(4));

						if (dateAndTimeOnUI.equals(salesOrderDateAndTime) &&
								recipientOnUI.contains(recipientInfoSale) &&
								noteOnUI.equalsIgnoreCase(returnNotes) &&
								String.valueOf(Double.parseDouble(grossReturnAmount)).equalsIgnoreCase(amountOnUI) &&
								actionOnUI.equalsIgnoreCase("Receive")
						) {
							bSaleReturnFound = true;
							m_assert.assertTrue(Cls_Generic_Methods.clickElement(row),
									"Sale Return Transaction Clicked In Transaction List");
							Cls_Generic_Methods.customWait(2);
							break;
						}

					}
				}


				m_assert.assertTrue(bSaleReturnFound," Sale Return Record Found And Clicked");

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
	@Test(enabled = true, description = "Validating Sale Return in Items Lot ")
	public void ValidateViewSaleReturnTransactionFunctionality() {

		Page_Purchase oPage_Purchase = new Page_Purchase(driver);
		Page_Sale oPage_Sale = new Page_Sale(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_SaleReturn oPage_SaleReturn = new Page_SaleReturn(driver);
		Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);

		try {
			myPatient = map_PatientsInExcel.get(patientKey);
			CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
			CommonActions.selectStoreOnApp(pharmacyStoreName);
			Cls_Generic_Methods.switchToOtherTab();
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
			m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
					"Store pop up closed");
			Cls_Generic_Methods.customWait();
			try {

				// Validating Return Purchase In Items Lots
				CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale Return");
				Cls_Generic_Methods.customWait();
				String recipientInfoSale = myPatient.getsSALUTATION()+" "+myPatient.getsFIRST_NAME();

				boolean bSaleReturnFound = false ;
				for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

					if (Cls_Generic_Methods.isElementDisplayed(row)) {
						List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

						String dateAndTimeOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(0));
						String recipientOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(1));
						String noteOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(2));
						String amountOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(3));
						String actionOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(4));


						if (dateAndTimeOnUI.equals(salesOrderDateAndTime) &&
								recipientOnUI.contains(recipientInfoSale) &&
								noteOnUI.equalsIgnoreCase(returnNotes) &&
								String.valueOf(Double.parseDouble(grossAmount)).equalsIgnoreCase(amountOnUI) &&
								actionOnUI.equalsIgnoreCase("Receive")
						) {
							bSaleReturnFound = true;
							m_assert.assertTrue(Cls_Generic_Methods.clickElement(row),
									"Sale Return Transaction Clicked In Transaction List");
							Cls_Generic_Methods.customWait(2);
							break;
						}

					}
				}

				m_assert.assertTrue(bSaleReturnFound," Sale Return Record Found And Clicked");

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.header_pharmacyReturnBill),
						"Pharmacy Return Bill Header Displayed In View RHS Side");
				boolean PrintA4InBill = validatePrintButtonFunctionality(oPage_SaleReturn.button_printA4SizeButton,"Print4 Size");
				boolean PrintA5InBill = validatePrintButtonFunctionality(oPage_SaleReturn.button_printA5SizeButton,"Print5 Size");

				m_assert.assertTrue(PrintA4InBill, " PrintA4 Work In Pharmacy Return Bill");
				m_assert.assertTrue(PrintA5InBill, " PrintA5 Work In Pharmacy Return Bill");

				addDataForPatientDetails();
				validatingDataInViewItemRHSSide(oPage_Sale.list_keysAndValuesInViewOrderList,viewSaleReturnPatientDetailsList,saleReturnPatientDetails);

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.header_itemDetailsSection),
						" <b> Item Details </b> Section Header Displayed");
				addDataForItemDetailsDataValue();

				String arrayValueConverted [] = new String[itemDetailsDataList.size()];
				arrayValueConverted = itemDetailsDataList.toArray(arrayValueConverted);
				validatingHeaderInTable(oPage_Sale.list_itemDetailsTableHeaderList,viewItemDetailsHeaderList," Item Details Of Sale Return ");
				validatingHeaderInTable(oPage_Sale.list_itemDetailsTableDataList,arrayValueConverted," Item Details Of Sale Return ");

				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SaleReturn.text_taxableAmountKeyAndValue).equalsIgnoreCase(sTaxableAmount),
						"Taxable Amount Label Displayed and Its Values as : "+sTaxableAmount);
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SaleReturn.text_sgstAmountKeyAndValue).equalsIgnoreCase(sSgst),
						"SGST2.5 Label Displayed and Its Value as : "+sSgst);
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SaleReturn.text_cgstAmountKeyAndValue).equalsIgnoreCase(sCgst),
						"CGST2.5 Label Displayed and Its Value as : "+sCgst);
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SaleReturn.text_grossAmountKeyAndValue).equalsIgnoreCase(grossAmount),
						"Gross Return Amt. Label Displayed and its Value as : "+grossAmount);
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SaleReturn.text_returnChargesAmountKeyAndValue).equalsIgnoreCase("10.00"),
						"Return charges Label Displayed and its Value as : 10.00");
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SaleReturn.text_netReturnValueAmountKeyAndValue).equalsIgnoreCase(sNetReturn),
						"Net Return Charges Label Displayed and its Value as : "+sNetReturn);
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.text_patientAllTransactionHeader),
						" Patient All Transactions Section Header Displayed");
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_refundPaymentSectionHeader).equalsIgnoreCase("Refund Payment : ₹"+Double.parseDouble(sNetReturn)),
						" Refund Payment Section displayed as : "+Cls_Generic_Methods.getTextInElement(oPage_Sale.text_refundPaymentSectionHeader));

				String firstRefundText = "₹"+Double.parseDouble(sNetReturn)+" on "+orderDate.substring(8,10)+" "+getMonthNameByNumber(Integer.parseInt(orderDate.substring(5,7)))+"'"
						+orderDate.substring(2,4);
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_firstRefundPayment).equalsIgnoreCase(firstRefundText),
						" First Refund Text Displayed correctly as "+firstRefundText);

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.text_firstRefundPayment),
						" First Refund Clicked");
				Cls_Generic_Methods.customWait();
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.header_refundReceiptViewTemplate),
						" Refund Receipt Templated Opened as Refund Receipt");
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_refundReceiptTemplateMessage).equalsIgnoreCase("Refunded a sum of ₹ "+sNetReturn+" to "+CommonActions.getFullPatientName(myPatient)+" dated "+Cls_Generic_Methods.getTodayDate()+"."),
						" Refund Message Displayed as : "+Cls_Generic_Methods.getTextInElement(oPage_Sale.text_refundReceiptTemplateMessage));
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.text_refundReceiptTemplateRemark),
						" Refund remark displayed as "+Cls_Generic_Methods.getTextInElement(oPage_Sale.text_refundReceiptTemplateRemark));

				boolean printRefund = validatePrintButtonFunctionality(oPage_Sale.button_printButtonInRefundTemplate," Print Button In Refund template");
				boolean emailRefund = validateEmailButtonFunctionality(oPage_Sale.button_emailButtonInRefundTemplate ," Refund Template");

				m_assert.assertTrue(printRefund ," Print Functionality Working In Refund");
				m_assert.assertTrue(emailRefund ," Email Functionality Working In Refund");

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SaleReturn.text_refundReceiptDetailsButton),
						"Details Button Clicked In Refund Receipt Template");
				Cls_Generic_Methods.customWait();

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SaleReturn.header_refundMopReceiptsViewTemplate),
						"Refund MOP Receipts Template Opened");


				validatingHeaderInTable(oPage_SaleReturn.list_keysRefundMOPReceiptHeaderList,refundMOPReceiptTemplateHeaderList,"Refund Mop Receipt");

				int sizeOfMop = oPage_SaleReturn.list_noValueRefundMOPReceiptList.size();
				for(WebElement eSR : oPage_SaleReturn.list_noValueRefundMOPReceiptList){

					int index = oPage_SaleReturn.list_noValueRefundMOPReceiptList.indexOf(eSR);
					String eTextValue = Cls_Generic_Methods.getTextInElement(eSR).trim();
					if(sizeOfMop -1 == index){
						m_assert.assertTrue(eTextValue.equalsIgnoreCase("Total:") ,
								" Refund MOP No. Column Value Displayed as : "+eTextValue+" at index "+index);

					}else {
						if (eTextValue.equalsIgnoreCase(String.valueOf(index + 1))) {
							m_assert.assertTrue( " Refund MOP No. Column Value Displayed as : "+eTextValue+" at index "+index);
						}else{
							m_assert.assertTrue( " Refund MOP No. Column Value Not Displayed as : "+eTextValue+" at index "+index);

						}
					}
				}
				String totalAmountOfAllMop = "";
				for(WebElement eSR : oPage_SaleReturn.list_modeOfPaymentValueRefundReceiptList){

					int index = oPage_SaleReturn.list_modeOfPaymentValueRefundReceiptList.indexOf(eSR);
					String eTextValue = Cls_Generic_Methods.getTextInElement(eSR).trim();
					if(sizeOfMop -1 == index){
						totalAmountOfAllMop = eTextValue;
					}else {
						if (eTextValue.equalsIgnoreCase(modeOfPayment[index])) {
							m_assert.assertTrue(" Refund MOP Mode Of Payment Column Value Displayed Correctly as : " + eTextValue + " at index " + index);
						} else {
							m_assert.assertTrue(" Refund MOP Mode Of Payment Column Value Not Displayed as : " + eTextValue + " at index " + index);
						}
					}
				}

				for(WebElement eSR : oPage_SaleReturn.list_refundMopIDValueRefundReceiptHeaderList){

					int index = oPage_SaleReturn.list_refundMopIDValueRefundReceiptHeaderList.indexOf(eSR);
					String eTextValue = Cls_Generic_Methods.getTextInElement(eSR).trim();
					if (eTextValue.equalsIgnoreCase(refundId+"-0"+index+1)) {
						m_assert.assertTrue(" Refund MOP  Id Column Value Displayed Correctly as : " + eTextValue + " at index " + index);
					} else {
						m_assert.assertTrue(" Refund MOP  Id Column Value Not Displayed as : " + eTextValue + " at index " + index);
					}
				}
				Double amountCalculated  = 0.0 ;
				for(WebElement eSR : oPage_SaleReturn.list_amountValueRefundReceiptHeaderList){

					int index = oPage_SaleReturn.list_amountValueRefundReceiptHeaderList.indexOf(eSR);
					String eTextValue = Cls_Generic_Methods.getTextInElement(eSR).trim();
					amountCalculated = amountCalculated+ Double.parseDouble(eTextValue);

					if (eTextValue.equalsIgnoreCase(modeOfPayment[index])) {
						m_assert.assertTrue(" Refund MOP Amount Column Value Displayed Correctly as : " + eTextValue + " at index " + index);
					} else {
						m_assert.assertTrue(" Refund MOP Amount Column Value Not Displayed as : " + eTextValue + " at index " + index);
					}

				}

				m_assert.assertTrue(Double.parseDouble(totalAmountOfAllMop) == amountCalculated ,
						" Total Amount Calculated correctly");

				Cls_Generic_Methods.clickElementByJS(driver,oPage_Sale.button_closeByXButtonInRefundMopReceipts);
				Cls_Generic_Methods.customWait();

				Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_closeAdvance);
				Cls_Generic_Methods.customWait();

				for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

					if (Cls_Generic_Methods.isElementDisplayed(row)) {
						List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

						String dateAndTimeOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(0));
						String actionOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(4));


						if (dateAndTimeOnUI.equals(salesOrderDateAndTime) &&
								actionOnUI.equalsIgnoreCase("Receive")
						) {
							WebElement receiveButton = purchaseRow.get(4).findElement(By.xpath("./button"));
							m_assert.assertTrue(Cls_Generic_Methods.clickElement(receiveButton),
									" Receive Button is Enabled and Clicked");
							DateFormat dateFormat = new SimpleDateFormat("hh:mmaa");
							sTime = dateFormat.format(new Date());
							sTime = sTime.replace("am", "AM").replace("pm", "PM");
							Cls_Generic_Methods.customWait(5);
							m_assert.assertTrue(!Cls_Generic_Methods.isElementClickable(driver,receiveButton),
									" Received Button Is Disabled Now");
							break;
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
	@Test(enabled = true, description = "Validating Sale Return in Items Lot ")
	public void ValidateSaleReturnTransactionInItemLotFunctionality() {

		Page_Purchase oPage_Purchase = new Page_Purchase(driver);
		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Lot oPage_Lot = new Page_Lot(driver);

		boolean bPurchaseTransactionFoundInLot = false;
		boolean bReturnPurchaseTransactionFoundInLot = false;
		Double stockAfterCalculated = 0.0;
		String stockBeforeReturnUI = "";
		try {
			myPatient = map_PatientsInExcel.get(patientKey);
			CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
			CommonActions.selectStoreOnApp(pharmacyStoreName);
			Cls_Generic_Methods.switchToOtherTab();
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
			m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
					"Store pop up closed");
			Cls_Generic_Methods.customWait();
			try {

				// Validating Return Purchase In Items Lots
				CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
				Cls_Generic_Methods.customWait();

				int index = 0;
				for(String item : myMedicationName) {
					Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_searchInventoryInStoreInventory);
					Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, item);
					Cls_Generic_Methods.customWait(5);
					oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
					Cls_Generic_Methods.customWait(5);

					int indexOfItemInLot = 0;

					for (WebElement items : oPage_ItemMaster.list_itemListInStoreInventory) {
						List<WebElement> itemNameRow = items.findElements(By.xpath("./child::*"));
						String itemDescriptionText = Cls_Generic_Methods.getTextInElement(itemNameRow.get((0)));
						if (itemDescriptionText.contains(item)) {
							Cls_Generic_Methods.clickElement(items);
							Cls_Generic_Methods.customWait(5);
							bPurchaseTransactionFoundInLot = true;
							break;
						}
					}
					m_assert.assertTrue(bPurchaseTransactionFoundInLot, item + " Clicked in Items Lot at index " + indexOfItemInLot);

					if (bPurchaseTransactionFoundInLot) {

						stockBeforeReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockBeforeInViewTemplate);
						String stockAfterReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockAfterInViewTemplate);
						String flowTextUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsFlowInViewTemplate);
						String dateAndTimeUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsDateAndTimeInViewTemplate);
						String availableStock = Cls_Generic_Methods.getTextInElement(oPage_Lot.value_AvailableStock);


						m_assert.assertTrue(Double.parseDouble(sAvailableStock.get(index))+2 == Double.parseDouble(availableStock) ||
										Double.parseDouble(sAvailableStock.get(index)) == Double.parseDouble(availableStock),
								"Available Stock Displayed Correctly after Return as :"+availableStock);
						String date = dateAndTimeUI.split("\\|")[0].trim();
						String time = dateAndTimeUI.split("\\|")[1].trim();
						String purchaseReturnDateAndTime = date + "|" + time;

						stockAfterCalculated = convertStringToDouble(stockBeforeReturnUI) + convertStringToDouble(inputQty);

						if (flowTextUI.equalsIgnoreCase("In (Return)") &&
								Double.toString(stockAfterCalculated).equals(stockAfterReturnUI) &&
								purchaseReturnDateAndTime.equalsIgnoreCase(orderDate + "|" + time)) {
							bReturnPurchaseTransactionFoundInLot = true;
						}
					}
					m_assert.assertTrue(bReturnPurchaseTransactionFoundInLot,
							" <b> Sale Return Working Fine, as initial stock was: " + stockBeforeReturnUI +
									" after return stock is: " + stockAfterCalculated + "</b> ");
					index++;
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
	@Test(enabled = true, description = "Creating Sale Return Transaction in Transaction ")
	public void validatingReceiveButtonFunctionalityInSaleReturnTransaction() {

		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Lot oPage_Lot = new Page_Lot(driver);
		Page_Purchase oPage_Purchase = new Page_Purchase(driver);
		Page_Adjustment oPage_Adjustment = new Page_Adjustment(driver);
		Page_SaleReturn oPage_SaleReturn = new Page_SaleReturn(driver);
		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);

		try {
			myPatient = map_PatientsInExcel.get(patientKey);
			CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
			boolean manualReceiveButtonStatus = getReceivedButton_valueFromStoreSetting("No");
            m_assert.assertTrue(manualReceiveButtonStatus," Manual Receive Set as No In Store");
			CommonActions.selectStoreOnApp(pharmacyStoreName);
			Cls_Generic_Methods.switchToOtherTab();
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
			m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
					"Store pop up closed");
			Cls_Generic_Methods.customWait();
			try {

				CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
				Cls_Generic_Methods.customWait();
				Cls_Generic_Methods.clearValuesInElement(oPage_Lot.input_InventorySearch);
				Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_InventorySearch, myMedicationName[1]);
				Cls_Generic_Methods.customWait(3);
				oPage_Lot.input_InventorySearch.sendKeys(Keys.ENTER);
				Cls_Generic_Methods.customWait(3);
				boolean bItemFoundInLot1 = false;
				for (WebElement eVariant : oPage_Lot.list_LotDetailsOnVariants) {
					if (Cls_Generic_Methods.getTextInElement(eVariant).contains(myMedicationName[1])) {
						Cls_Generic_Methods.clickElement(eVariant);
						bItemFoundInLot1 = true;
						break;
					}
				}

				m_assert.assertTrue(bItemFoundInLot1, myMedicationName[1]+" Item found in Items Lot Page");
				Cls_Generic_Methods.customWait(5);

				sAvailableStock.add(Cls_Generic_Methods.getTextInElement(oPage_Lot.value_AvailableStock));
				listPrice.add(Cls_Generic_Methods.getTextInElement(oPage_Lot.text_mrpPrice));
				CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
				Cls_Generic_Methods.customWait( );

				// creating order for cancel order button check
				boolean bCreateOrderForPolicy = createSaleOrderForPolicyCheck("Stockable");
				Cls_Generic_Methods.customWait();
				m_assert.assertTrue(bCreateOrderForPolicy," Sales Order Created");

				String recipientInfoSale = myPatient.getsFIRST_NAME();

				CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale Return");
				Cls_Generic_Methods.customWait( );
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SaleReturn.button_new),
						" <b> New Button </b> Displayed In Sale Return Page");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SaleReturn.input_searchPatient,5);
				Cls_Generic_Methods.selectElementByIndex(oPage_SaleReturn.input_searchTypeFilter,3);
				Cls_Generic_Methods.customWait();
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SaleReturn.input_searchPatient,patientId),
						"Patient id Entered In Search Box"+patientId);
				Cls_Generic_Methods.customWait();
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SaleReturn.button_searchPatient),
						"Search Button Clicked");
				Cls_Generic_Methods.customWait();
				boolean bCreatedPatientFound = false ;
				for (WebElement ePatientId : oPage_SaleReturn.list_searchedPatientIdList){

					String searchedPatientId = Cls_Generic_Methods.getTextInElement(ePatientId);
					if(searchedPatientId.equalsIgnoreCase(patientId)){
						m_assert.assertTrue(Cls_Generic_Methods.clickElement(ePatientId),
								searchedPatientId+ " Clicked From Search Patient List");
						Cls_Generic_Methods.customWait(5);
						bCreatedPatientFound = true ;
						break;
					}

				}

				m_assert.assertTrue(bCreatedPatientFound,"Sale Order Created Patient Found And Clicked In Return");

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Adjustment.button_Description),
						"Description Button Clicked");
				Cls_Generic_Methods.customWait();
				Cls_Generic_Methods.clearValuesInElement(oPage_Adjustment.input_LotSearch);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Adjustment.input_LotSearch, myMedicationName[1]),
						" Search Lot By Description Displayed And Value Entered as " + myMedicationName[1]);
				Cls_Generic_Methods.customWait(3);
				oPage_Adjustment.input_LotSearch.sendKeys(Keys.ENTER);
				Cls_Generic_Methods.customWait(3);
				boolean myMedicationFound = false;

				for (WebElement eMedication : oPage_Adjustment.list_namesOfMedicinesOnLeftInSearchResult) {

					String medicationName = Cls_Generic_Methods.getTextInElement(eMedication);
					if (medicationName.equalsIgnoreCase(myMedicationName[1])) {
						Cls_Generic_Methods.clickElement(eMedication);
						myMedicationFound = true;
						Cls_Generic_Methods.customWait();
						break;
					}

				}

				m_assert.assertTrue(myMedicationFound, "Validated medication description " + myMedicationName[1] + " found in sale return");

				for(WebElement eQty : oPage_Adjustment.list_inputQtyList) {
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(eQty, inputQty),
							" Return Quantity Entered as : "+inputQty+" for item  "+myMedicationName[1]);
				}
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SaleReturn.checkbox_verified),
						"Verified Checkbox Clicked for item "+myMedicationName[1]);
				m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_SaleReturn.select_returnMop,modeOfPayment[0]),
						"Mode Of Payment Selected as :"+modeOfPayment[1]);
				Cls_Generic_Methods.customWait();

				String netReturn = Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_netReturnAmountInReturn,"value");
				String grossAmountText = Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_grossAmountInReturn,"value");
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SaleReturn.input_returnRefundAmount,netReturn),
						"Enter Refund Amount equal to Net Return Amount as : "+netReturn);
				Cls_Generic_Methods.customWait(1);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SaleReturn.input_returnNote,returnNotes),
						" Return Notes Entered as "+returnNotes);
				Cls_Generic_Methods.customWait(1);
				orderDate = Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_dateSaleReturn,"value");
				orderTime = Cls_Generic_Methods.getElementAttribute(oPage_SaleReturn.input_timeSaleReturn,"value");

				Cls_Generic_Methods.clickElement(oPage_SaleReturn.button_saveChanges);
				Cls_Generic_Methods.customWait(5);
				Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
				Cls_Generic_Methods.customWait();



				orderDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy","yyyy-MM-dd",orderDate);
				if(orderTime.length() == 7){
					orderTime = "0"+orderTime;
				}
				orderTime = orderTime.replace("am", "AM").replace("pm", "PM");
				salesOrderDateAndTime = orderDate+"  |  "+orderTime.substring(0,5)+orderTime.substring(6,8);

				boolean bSaleReturnFound = false ;
				for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

					if (Cls_Generic_Methods.isElementDisplayed(row)) {
						List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

						String dateAndTimeOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(0));
						String recipientOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(1));
						String noteOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(2));
						String amountOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(3));
						String actionOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(4));


						if (dateAndTimeOnUI.equals(salesOrderDateAndTime) &&
								recipientOnUI.contains(recipientInfoSale) &&
								noteOnUI.equalsIgnoreCase(returnNotes) &&
								String.valueOf(Double.parseDouble(grossAmountText)).equalsIgnoreCase(amountOnUI) &&
								actionOnUI.equalsIgnoreCase("Received")
						) {
							WebElement receiveButton = purchaseRow.get(4).findElement(By.xpath("./button"));
							bSaleReturnFound = true ;
							m_assert.assertFalse(Cls_Generic_Methods.isElementClickable(driver,receiveButton),
									"Receive Button Not Enabled In Sale Return , Auto Received List");
							Cls_Generic_Methods.customWait(2);
							break;
						}

					}
				}

				m_assert.assertTrue(bSaleReturnFound," Sale Return Record Found And Clicked");
				CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
				Cls_Generic_Methods.customWait();

				Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_searchInventoryInStoreInventory);
				Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, myMedicationName[1]);
				Cls_Generic_Methods.customWait(5);
				oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
				Cls_Generic_Methods.customWait(5);

				boolean bPurchaseTransactionFoundInLot = false ,bReturnPurchaseTransactionFoundInLot= false;
				for (WebElement items : oPage_ItemMaster.list_itemListInStoreInventory) {
					List<WebElement> itemNameRow = items.findElements(By.xpath("./child::*"));
					String itemDescriptionText = Cls_Generic_Methods.getTextInElement(itemNameRow.get((0)));
					if (itemDescriptionText.contains(myMedicationName[1])) {
						Cls_Generic_Methods.clickElement(items);
						Cls_Generic_Methods.customWait(5);
						bPurchaseTransactionFoundInLot = true;
						break;
					}
				}
				m_assert.assertTrue(bPurchaseTransactionFoundInLot, myMedicationName[1]+" Clicked in Items Lot at index ");

				if (bPurchaseTransactionFoundInLot) {

					String stockBeforeReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockBeforeInViewTemplate);
					String stockAfterReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockAfterInViewTemplate);
					String flowTextUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsFlowInViewTemplate);
					String dateAndTimeUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsDateAndTimeInViewTemplate);
					String availableStock = Cls_Generic_Methods.getTextInElement(oPage_Lot.value_AvailableStock);


					m_assert.assertTrue(Double.parseDouble(sAvailableStock.get(2)) == Double.parseDouble(availableStock),
							"Available Stock Displayed Correctly after Return as :"+availableStock);
					String date = dateAndTimeUI.split("\\|")[0].trim();
					String time = dateAndTimeUI.split("\\|")[1].trim();
					String purchaseReturnDateAndTime = date + "|" + time;

					Double stockAfterCalculated = convertStringToDouble(stockBeforeReturnUI) + convertStringToDouble(inputQty);

					if (flowTextUI.equalsIgnoreCase("In (Return)") &&
							Double.toString(stockAfterCalculated).equals(stockAfterReturnUI) &&
							purchaseReturnDateAndTime.equalsIgnoreCase(orderDate + "|" + time)) {
						bReturnPurchaseTransactionFoundInLot = true;
					}
					m_assert.assertTrue(bReturnPurchaseTransactionFoundInLot,
							" <b> Sale Return Working Fine, as initial stock was: " + stockBeforeReturnUI +
									" after return stock is: " + stockAfterCalculated + "</b> ");

				}

				Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
				Cls_Generic_Methods.customWait();
				manualReceiveButtonStatus = getReceivedButton_valueFromStoreSetting("Yes");
				m_assert.assertTrue(manualReceiveButtonStatus," Manual Receive Set as Yes In Store");

			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}

	}

	public  boolean createSaleOrderForPolicyCheck(String itemFrom){

		boolean createSalesOrder = false ;
		Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
		Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
		Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);
		Page_Sale oPage_Sale = new Page_Sale(driver);
		String sPatientSalutation = "Mr.";



		try{
			myPatient = map_PatientsInExcel.get(patientKey);
			m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addNewButtonInOrder), "Sales Order New Button Clicked");
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.list_PatientSearch, 5);
			Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.button_addNewPatient);
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_orderTime,10);

			m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
							oPage_NewPatientRegisteration.select_salutationForPatient, sPatientSalutation),
					"Salutation for Patient is selected as - " + sPatientSalutation);

			m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
							oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
					"First Name is entered as - " + myPatient.getsFIRST_NAME());

			m_assert.assertTrue(
					Cls_Generic_Methods.sendKeysIntoElement(
							oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
							myPatient.getsMOBILE_NUMBER()),
					"Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

			m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm),
					" Save Button Clicked in Patient Register Form");
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_TxnDate,10);
			m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_searchMedicineNameInDescription, myMedicationName[1]),
					"Entering the medication name as" + myMedicationName[1] + " in description textbox for sales order");
			Cls_Generic_Methods.customWait(3);
			oPage_SalesOrder.input_searchMedicineNameInDescription.sendKeys(Keys.ENTER);
			Cls_Generic_Methods.customWait(3);
			boolean myMedicationFound = false;

			for (WebElement eMedication : oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult) {
				if(Cls_Generic_Methods.isElementDisplayed(eMedication)){
					if (Cls_Generic_Methods.getTextInElement(eMedication).contains(myMedicationName[1])) {
						Cls_Generic_Methods.clickElement(eMedication);
						myMedicationFound = true;
						Cls_Generic_Methods.customWait(4);
						break;
					}
				}
			}
			m_assert.assertTrue(myMedicationFound, "Required medication " + myMedicationName[1] + " found for Sales Order");

			m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_quantityOfMedicine, inputQty),
					"Entered Quantity " + inputQty + " for Sales Order");
			Cls_Generic_Methods.customWait(5);

			netAmountSaleOrder = Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_totalAmount,"value");

			m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_PatientQueue.select_modeOfPaymentInSalesOrder, modeOfPayment[0]),
					"Required mode of payment " + modeOfPayment[0]+ " selected");

			Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientQueue.input_totalAmountInPayment,netAmountSaleOrder);

			orderDate = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_TxnDate,"value");
			orderTime = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderTime,"value");

			DateFormat dateFormat = new SimpleDateFormat("hh:mmaa");
			sTime = dateFormat.format(new Date());
			sTime = sTime.replace("am", "AM").replace("pm", "PM");

			orderTime = orderTime.replaceAll("\\s+", "");
			//orderTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", orderTime);
			orderTime = orderTime.replace("am", "AM").replace("pm", "PM");
			orderDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd-MM-yyyy", orderDate);

			if(orderTime.length() == 6){
				orderTime = "0"+orderTime;
			}

			m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_SaveChanges),
					"Save Changes Button CLicked");
			Cls_Generic_Methods.customWait(5);

			salesOrderDateAndTime =orderDate+"  |  "+orderTime;


			if(Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.button_closeModalOfSalesOrder)) {
				Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_closeModalOfSalesOrder);
				Cls_Generic_Methods.customWait();
				createSalesOrder = true ;
			}

			m_assert.assertTrue(createSalesOrder, " Sale Order Created Successfully");
			String sDate = orderDate.replace("/", "-");
			String sTime = orderTime.replace(" ", "");
			boolean bSalesOrderFound = false ;
			for (WebElement eSalesOrder : oPage_SalesOrder.list_namesofSalesOrder) {
				if (eSalesOrder.getText().contains(sDate) && eSalesOrder.getText().contains(sTime) && eSalesOrder.getText().contains(myPatient.getsFIRST_NAME())) {
					eSalesOrder.click();
					Cls_Generic_Methods.customWait(5);
					bSalesOrderFound = true;
					break;
				}
			}
			m_assert.assertTrue(bSalesOrderFound, "Created and Selected the correct sales order");
			patientId = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_rhsPatientId);
			Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_MoreAction);
			Cls_Generic_Methods.customWait();
			billNumber = Cls_Generic_Methods.getTextInElement(oPage_Sale.text_rhsBillNumber);
			Cls_Generic_Methods.clickElement(oPage_Sale.button_PlaceOrder);
			Cls_Generic_Methods.customWait();





		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}

		return createSalesOrder ;
	}
	public String validatingDiscountCalculation(String totalAmtIncTax, String discount) {

		String bDiscountCalculation = "";

		try {

			Double discountPercentage = (Double.parseDouble(discount) * 100) / (Double.parseDouble(totalAmtIncTax));
			bDiscountCalculation = String.format("%.2f", discountPercentage);

		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}

		return bDiscountCalculation;
	}
	public boolean validatePrintButtonFunctionality(WebElement printButton , String printButtonName){

		Page_Sale oPage_Sale = new Page_Sale(driver);
		boolean bPrintWork = false ;

		try {
			int preWindowsCount = driver.getWindowHandles().size();
			String initialWindowHandle = driver.getWindowHandle();
			m_assert.assertInfo(Cls_Generic_Methods.clickElement(printButton),
					printButtonName+" Button Displayed and Clicked ");
			Cls_Generic_Methods.customWait(2);
			if(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.list_printDropdownFirstOptionInAdvanceReceipt)){
				Cls_Generic_Methods.clickElement(oPage_Sale.list_printDropdownFirstOptionInAdvanceReceipt);
				Cls_Generic_Methods.customWait();
			}else {
				if (Cls_Generic_Methods.isElementDisplayed(oPage_Sale.list_printDropdownFirstOption)) {
					Cls_Generic_Methods.clickElement(oPage_Sale.list_printDropdownFirstOption);
					Cls_Generic_Methods.customWait();
				}
			}
			Cls_Generic_Methods.customWait(6);
			int postWindowsCount = driver.getWindowHandles().size();

			bPrintWork = postWindowsCount > preWindowsCount ;
			m_assert.assertTrue(bPrintWork, "Validated Print --> Sale Transaction Print page opened");

			for (String currentWindowHandle : driver.getWindowHandles()) {
				if (!currentWindowHandle.equals(driver.getWindowHandle())) {
					driver.switchTo().window(currentWindowHandle);
				}
			}
			driver.close();
			driver.switchTo().window(initialWindowHandle);
			Cls_Generic_Methods.customWait(4);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return bPrintWork ;
	}
	public static void validatingDataInViewItemRHSSide(List<WebElement> eHeaderList , String [] actualHeaderList,List<String> actualValueList){
		try{

			int indexOfKey = 0,indexOfValue = 0 ;
			for(WebElement eViewItem : eHeaderList){

				if(Cls_Generic_Methods.isElementDisplayed(eViewItem)){
					int indexOfViewItem = eHeaderList.indexOf(eViewItem);
					String sViewItemText = Cls_Generic_Methods.getTextInElement(eViewItem);
					if(indexOfViewItem % 2 == 0) {
						m_assert.assertTrue(sViewItemText.equalsIgnoreCase(actualHeaderList[indexOfKey]),
								"<b> "+sViewItemText+ " </b> Label Present In View Sale Return RHS side ");
						indexOfKey ++ ;
					}else{
						m_assert.assertTrue(sViewItemText.contains(actualValueList.get(indexOfValue)),
								"<b> "+sViewItemText + "  </b> Value Present In View Sale Return RHS against label <b> " + actualHeaderList[indexOfValue]+" </b>");

						indexOfValue ++ ;
					}
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addDataForPatientDetails(){

		String recipientInfoSale = myPatient.getsSALUTATION()+" "+myPatient.getsFIRST_NAME();

		saleReturnPatientDetails.add("RSO");
		saleReturnPatientDetails.add("Return Against Multiple Invoice");
		saleReturnPatientDetails.add(recipientInfoSale);
		saleReturnPatientDetails.add(patientId);
		saleReturnPatientDetails.add(myPatient.getsMOBILE_NUMBER());

	}
	public void validatingHeaderInTable( List<WebElement> headerList, String actualHeaderList[] ,String place){

		try{
			for (WebElement purchaseHeaderList : headerList) {
				int indexOfHeader = headerList.indexOf(purchaseHeaderList);
				String sValueOfHeader = Cls_Generic_Methods.getTextInElement(purchaseHeaderList).replace("\n", " ");
				if(indexOfHeader == 2 || indexOfHeader == 12){
					if (sValueOfHeader.equalsIgnoreCase(actualHeaderList[indexOfHeader])) {
						m_assert.assertTrue("<b>" + sValueOfHeader + "  </b> Header/Value Present In " + place + " Table List");
					}
				}else {
					if (sValueOfHeader.contains(actualHeaderList[indexOfHeader])) {
						m_assert.assertTrue("<b>" + sValueOfHeader + "  </b> Header/Value Present In " + place + " Table List");
					} else {
						m_assert.assertWarn(sValueOfHeader + " Header/Value Not Present In " + place + " Table List");
					}
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void addDataForItemDetailsDataValue(){

		itemDetailsDataList.add("1");
		itemDetailsDataList.add("INV");
		itemDetailsDataList.add(myMedicationName[0]);
		itemDetailsDataList.add(inputQty);
		itemDetailsDataList.add("");
		itemDetailsDataList.add("N.A");
		itemDetailsDataList.add(listPrice.get(0).replace("₹","").trim());
		itemDetailsDataList.add("-");
		itemDetailsDataList.add(totalAmountItemList.get(0));
		itemDetailsDataList.add(remark[0]);
		itemDetailsDataList.add("2");
		itemDetailsDataList.add("INV");
		itemDetailsDataList.add(myMedicationName[1]);
		itemDetailsDataList.add(inputQty);
		itemDetailsDataList.add("BN1234578");
		itemDetailsDataList.add("N.A");
		itemDetailsDataList.add(listPrice.get(1).replace("₹","").trim());
		itemDetailsDataList.add("-");
		itemDetailsDataList.add(totalAmountItemList.get(1));
		itemDetailsDataList.add(remark[1]);


	}
	public String getMonthNameByNumber(int month) {

		String monthInString = "";

		try {

			switch (month) {
				case 1:
					monthInString = "Jan";
					break;
				case 2:
					monthInString = "Feb";
					break;
				case 3:
					monthInString = "Mar";
					break;
				case 4:
					monthInString = "Apr";
					break;
				case 5:
					monthInString = "May";
					break;
				case 6:
					monthInString = "Jun";
					break;
				case 7:
					monthInString = "Jul";
					break;
				case 8:
					monthInString = "Aug";
					break;
				case 9:
					monthInString = "Sep";
					break;
				case 10:
					monthInString = "Oct";
					break;
				case 11:
					monthInString = "Nov";
					break;
				case 12:
					monthInString = "Dec";
					break;
				default:
					break;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		return monthInString;
	}
	public boolean validateEmailButtonFunctionality(WebElement emailButton, String place){

		Page_Sale oPage_Sale = new Page_Sale(driver);
		boolean emailWork = false ;

		try {
			m_assert.assertTrue(Cls_Generic_Methods.clickElement(emailButton),
					"Email Button Clicked In "+ place);
			m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Sale.input_emailAddressInComposeEmail,4),
					" Compose Email Preview Template Opened and header Displayed as : Compose Email");
			if(!Cls_Generic_Methods.isElementDisplayed(oPage_Sale.span_emailIdExistInComposeEmail)) {
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_emailAddressInComposeEmail, "deepak.malviya@healthgraph.in"),
						" Email address entered in Send To box as : deepak.malviya@healthgraph.in");
			}
			m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_emailSubjectInComposeEmail, "Invoice Email 1"),
					" Email Subject entered in Subject as : Invoice Email 1");
			m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.input_previewButtonInComposeEmail),
					" Preview Button Clicked");
			Cls_Generic_Methods.customWait(4);
			m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.input_sendEmailButtonInComposeEmail),
					" Send Email Button Clicked");
			Cls_Generic_Methods.customWait(8);
			if(!Cls_Generic_Methods.isElementDisplayed(oPage_Sale.input_sendEmailButtonInComposeEmail)){
				emailWork = true ;
			}


		}catch (Exception e) {
			e.printStackTrace();
		}

		return  emailWork ;
	}
	public boolean getReceivedButton_valueFromStoreSetting(String action) {

		Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
		boolean bClick = false ;

		try {
			CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
			Cls_Generic_Methods.customWait(3);
			CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 3);

			int storeNo = 0;
			for (WebElement txtStoreName : oPage_StoreSetUp.list_text_storeName) {
				String storeName = Cls_Generic_Methods.getTextInElement(txtStoreName);
				if (storeName.contains(pharmacyStoreName.split("-")[0])) {
					Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.list_btn_editStore.get(storeNo));
					Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_StoreSetUp.list_btn_editStoreBillingAddress, 10);
					if (action.equalsIgnoreCase("Yes")) {
						m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver,oPage_StoreSetUp.input_yesManualReceiveOnSaleReturnButton),
								" Yes Button Clicked In Manual Receive");
						bClick = true;

					} else {
						if (action.equalsIgnoreCase("No")) {
							m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver,oPage_StoreSetUp.input_noManualReceiveOnSaleReturnButton),
									" No Button Clicked In Manual Receive");
							bClick = true;

						}
					}

					m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver,oPage_StoreSetUp.button_updateStore),
							" Update Button Clicked");
					Cls_Generic_Methods.customWait(4);

					break;
				}
				storeNo++;
			}


		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("Unable to get GST no. from Organisation Setting " + e);
		}
		 return bClick;
	}


}
