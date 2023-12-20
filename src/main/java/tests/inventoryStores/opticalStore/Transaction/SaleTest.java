package tests.inventoryStores.opticalStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.store.OpticalStore.Page_SalesOrder;
import pages.store.Page_PatientQueue;
import pages.store.Page_Store;
import pages.store.PharmacyStore.Items.Page_Lot;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_Sale;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static tests.inventoryStores.pharmacyStore.Transaction.PurchaseGRNTest.convertStringToDouble;

public class SaleTest extends TestBase {

    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();
    String sStore = "OpticalStore- Optical";
    String myMedicationName = "SalesOrderTest1", inputQty = "2", settleAdvanceValue = "100", netAmountSaleOrder, amountAfterAdvance;
    String modeOfPayment[] = {"Cash", "Card"};
    String orderDate, orderDateDifferentFormat ,orderTime, time, salesOrderDateAndTime, advanceIdInSaleOrder,patientId, billNumber = "";
    List<String> saleTransactionHeaderList = new ArrayList<>();
	List<String> saleOrderPatientDetailsDataList = new ArrayList<>();
	List<String> saleOrderPatientDetailsListInViewBill = new ArrayList<>();

	String concatPatientFullName = "";
	String viewSaleTransactionPatientDetailsList[] = {"Patient:","Patient Mobile:","Patient ID:","Order date:","Bill Number:","Doctor:",
			"MR No.:","Optometrist:","Delivery date:","Created By:","Current Status:","Home Delivery:"};
	String patientDetailsListInViewBill[] = {"Patient:","Patient Mobile:","Patient ID:","Order date:","Bill Number:","Doctor:",
			"MR No.:","Delivery date:","Created By:","Current Status:","Home Delivery:"};
	List<String> saleItemDetailsDataList = new ArrayList<>();
	String itemVariantCode ="INV-MED-1114-100";
	List<String> billItemDetailsDataList = new ArrayList<>();
	List<String> itemDetailsLabelValue = new ArrayList<>();
	List<String> saleItemDetailsActualHeaderList,billItemDetailsActualHeaderList;
	List<String> receiveFromAdvanceValueList = new ArrayList<>();
	List<String> paymentReceivedValueList = new ArrayList<>();
	List<String> advanceValueList = new ArrayList<>();
	List<String> secondAdvanceReceiptTemplateValueList = new ArrayList<>();
	List<String> advanceReceiptTemplateValueList = new ArrayList<>();
	List<String> advanceHistoryTemplateValueList = new ArrayList<>();


	String saleItemDetailsHeaderList[] = {"#","Description","HSN","Model No.","Batch","Expiry","QTY", "MRP","Taxable Amt.","Net Amt.","Remark"};
	String saleItemDetailsHeaderListAfterNewRelease[] = {"#","Description","HSN","Model No.","Batch","Expiry","QTY", "MRP","Gross Amt.","Discounts",
			"Taxable Amt.","Net Amt.","Remark"};
	String billItemDetailsHeaderListAfterNewRelease[] = {"#","Code","Description","HSN","Model No.","Exp.Date","Batch No.","QTY", "MRP","Gross","Discounts",
			"Taxable Amt.","Tax","Tax Amt.","Net Amt.","Remark"};
	String billItemDetailsHeaderList[] = {"#","Code","Description","HSN","Model No.","Exp.Date","Batch No.","QTY", "MRP", "Taxable Amt.","Tax","Tax Amt.",
			"Net Amt.","Remark"};
	String discount = "0.0";
	String receiveFromAdvanceHeaderList[] = {"No.","Date/Time","Paid By","Amount"};
	String amountSettleDetailsHeaderList[] = {"No.","Date/Time","Paid By","Paid To","Mode Of Payment","Amount Received","Taxes Deducted",
			"Payer Difference","Revenue Spillage","Total Amount Settled"};
	String advanceDate ,advanceTime ,refundDate,refundTime,remAmountInRefund,returnDate,returnTime,paymentTime,orderNumber;
	String advanceReason = "Adding Advance In Optical";
	String cashPay = "1000",cardPay = "2000",secondAdvanceId;
	String advanceReceiptTemplateHeaderList[] = {"Advance ID:","Date:","Reason:","Amount Received:","Amount Remaining:"};
	String advanceMOPReceiptTemplateHeaderList[] = {"No.","Mode Of Payment","Advance MOP ID","Amount"};
	String advanceHistoryTemplateHeaderList[] = {"Bill Number","Advance Settled ID","Department","Type","Created By","Time","Amount","Actions"};

	String sReturnNote = "ReturnNote_"+CommonActions.getRandomString(5);



	@Test(enabled = true, description = "Validate the Sales Order Creation")
    public void createSaleOrderForSaleTransaction() {

        boolean createSalesOrder = false;
        Page_Store oPage_Store = new Page_Store(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
		Page_Sale oPage_Sale = new Page_Sale(driver);


        String sPatientSalutation = "Mr.";


        try {

			myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);

            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
            Cls_Generic_Methods.customWait();

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

            Cls_Generic_Methods.sendKeysIntoElement(oPage_Store.input_salesOrderDoctorName, EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait(1);

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_searchMedicineNameInDescription, myMedicationName), "Entering the medication name as" + myMedicationName + " in description textbox for sales order");
            Cls_Generic_Methods.customWait(4);
            oPage_SalesOrder.input_searchMedicineNameInDescription.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);
            boolean myMedicationFound = false;

            for (WebElement eMedication : oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult) {
                if (Cls_Generic_Methods.isElementDisplayed(eMedication)) {
                    if (Cls_Generic_Methods.getTextInElement(eMedication).contains(myMedicationName)) {
                        Cls_Generic_Methods.clickElement(eMedication);
                        myMedicationFound = true;
                        Cls_Generic_Methods.customWait(4);
                        break;
                    }
                }
            }
            m_assert.assertTrue(myMedicationFound, "Required medication " + myMedicationName + " found for Sales Order");


            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_quantityOfMedicine, inputQty),
                    "Entered Quantity " + inputQty + " for Sales Order");
            Cls_Generic_Methods.customWait(5);

            netAmountSaleOrder = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_balancePendingAmountAdvance, "value");

            Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_advanceReason, advanceReason);
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_SalesOrder.select_modeOfPaymentAdvancePayment.get(0), modeOfPayment[0]),
                    "Required mode of payment " + modeOfPayment[0] + " selected");

            Cls_Generic_Methods.clearValuesInElement(oPage_SalesOrder.input_advanceAmount.get(0));
            Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_advanceAmount.get(0), settleAdvanceValue);
            Cls_Generic_Methods.customWait(1);

            amountAfterAdvance = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_balancePendingAmountAdvance, "value");


            orderDate = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_TxnDate, "value");
            orderTime = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderTime, "value");
			orderDateDifferentFormat = orderDate;
            DateFormat dateFormat = new SimpleDateFormat("hh:mmaa");
            time = dateFormat.format(new Date());
            time = time.replace("am", "AM").replace("pm", "PM");

            orderTime = orderTime.replaceAll("\\s+", "");
            orderTime = orderTime.replace("am", "AM").replace("pm", "PM");
            orderDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd-MM-yyyy", orderDate);

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_SaveChanges),
                    "Save Changes Button CLicked");
            Cls_Generic_Methods.customWait(5);
			if(orderTime.length() == 6){
				orderTime = "0"+orderTime;
			}

            salesOrderDateAndTime = orderDate + "  |  " + orderTime;

			advanceIdInSaleOrder = Cls_Generic_Methods.getTextInElement(oPage_Sale.text_advanceIdInSalesOrderOptical);
            if (Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.button_closeModalOfSalesOrder)) {
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_closeModalOfSalesOrder);
                Cls_Generic_Methods.customWait();
                createSalesOrder = true;
            }

            m_assert.assertTrue(createSalesOrder, " Sale Order Created Successfully");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validate the Sales Order Creation")
    public void validateTheSalesOrderCreation() {

        try {

            Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
			Page_Sale oPage_Sale = new Page_Sale(driver);


			try {

                boolean bSalesOrderFound = false;
                String sDate = orderDate.replace("/", "-");
                String sTime = orderTime.replace(" ", "");
                for (WebElement eSalesOrder : oPage_SalesOrder.list_namesofSalesOrder) {
                    if (eSalesOrder.getText().contains(sDate) && eSalesOrder.getText().contains(sTime) && eSalesOrder.getText().contains(myPatient.getsFIRST_NAME())) {
                        eSalesOrder.click();
                        Cls_Generic_Methods.customWait(5);
                        bSalesOrderFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(bSalesOrderFound, "Selected the correct sales order");

                patientId = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_patientIdOpticalOrder);

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale");
                String recipientInfoSale = myPatient.getsSALUTATION() + " " + myPatient.getsFIRST_NAME() + "" + myPatient.getsMOBILE_NUMBER();

                boolean bSaleTransactionFoundAndClicked = getSaleTransactionFromTransactionList(
                        "1", salesOrderDateAndTime, orderDate, "Delivered", recipientInfoSale, netAmountSaleOrder);

                m_assert.assertFalse(bSaleTransactionFoundAndClicked, " Sale Order Not Displayed Correctly as Its Not in Delivered");

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
                Cls_Generic_Methods.customWait();
				for (WebElement eSalesOrder : oPage_SalesOrder.list_namesofSalesOrder) {
					if (eSalesOrder.getText().contains(sDate) && eSalesOrder.getText().contains(sTime) && eSalesOrder.getText().contains(myPatient.getsFIRST_NAME())) {
						eSalesOrder.click();
						Cls_Generic_Methods.customWait(5);
						bSalesOrderFound = true;
						break;
					}
				}
				m_assert.assertTrue(bSalesOrderFound, "Selected the correct sales order");

				orderNumber = Cls_Generic_Methods.getTextInElement(oPage_Sale.text_orderNumber);

				Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_CreateBill);
                Cls_Generic_Methods.customWait();

                Cls_Generic_Methods.selectElementByVisibleText(oPage_SalesOrder.select_ModeOfPaymentInCreateBill, modeOfPayment[0]);
                Cls_Generic_Methods.clearValuesInElement(oPage_SalesOrder.input_FinalAmount);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_FinalAmount, amountAfterAdvance);
				Cls_Generic_Methods.customWait();
				DateFormat dateFormat = new SimpleDateFormat("hh:mmaa");

				paymentTime = dateFormat.format(new Date());
				paymentTime = paymentTime.replace("am", "AM").replace("pm", "PM");
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_CreateBillAfterPayment);
                Cls_Generic_Methods.customWait(5);

                for (WebElement eSalesOrder : oPage_SalesOrder.list_namesofSalesOrder) {
                    if (eSalesOrder.getText().contains(sDate) && eSalesOrder.getText().contains(sTime) && eSalesOrder.getText().contains(myPatient.getsFIRST_NAME())) {
                        eSalesOrder.click();
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }

                Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_Delivered);
                Cls_Generic_Methods.customWait();
				Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_MoreAction);
				Cls_Generic_Methods.customWait();
				Cls_Generic_Methods.isElementDisplayed(oPage_Sale.button_PlaceOrder);

                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.customWait();


            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }

    }

	@Test(enabled = true, description = "Validation Patient and Template Details In Optical Store")
	public void validatingSalesTransactionInOpticalStore() {

		Page_Navbar oPage_Navbar = new Page_Navbar(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
		Page_Purchase oPage_Purchase = new Page_Purchase(driver);
		Page_Sale oPage_Sale = new Page_Sale(driver);
		Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);

		String saleTableHeaderList[] = {"Sl.No", "Txn.Info", "Delivery Date", "Order Status", "Recipient Info","Amount"};
		String billTableHeaderList[] = {"Taxable Amount :","SGST2.5 :","CGST2.5 :","Total Amt Incl. Tax :","Discount :","Offer :",
				"Total of all discounts :","Net Amount (₹) :","Received from Advance :","Payment Received :"};



		try {
			myPatient = map_PatientsInExcel.get(patientKey);
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);

			try {

				m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore),
						sStore + " Store Opened For Validation Of Patient Queue");
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();

				CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale");
				Cls_Generic_Methods.customWait(4);
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_refreshPurchaseTransaction),
						" Refresh Button Displayed In Sale Transaction");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.button_PlaceOrder),
						"Place Order Button Displayed and Clicked In Sale Transaction");
				Cls_Generic_Methods.customWait();
				if(Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.button_addNewButtonInOrder)){
					m_assert.assertTrue(" Place Order Button Navigation Working , Navigate TO Sales Order To Place Order");
				}else{
					m_assert.assertTrue(" Place Order Button Not Working , Either Not Clicked or Not Navigated To Sales Order");
				}

				CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale");

				Cls_Generic_Methods.customWait(2);

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_reportButtonInPurchaseGRN),
						" Report Download Button Displayed");

				validatingHeaderInTable(oPage_Purchase.list_purchaseTransactionHeaderList,saleTableHeaderList,"Sale Transaction ");

				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_vendorSearchBox,myPatient.getsFIRST_NAME()),
						" Search Box Displayed and value entered as to search :"+myPatient.getsFIRST_NAME());
				Cls_Generic_Methods.customWait();

				String recipientInfoSale = myPatient.getsSALUTATION()+" "+myPatient.getsFIRST_NAME()+""+myPatient.getsMOBILE_NUMBER();

				boolean bSaleOrderFoundAndClicked = getSaleTransactionFromTransactionList(
						"1",salesOrderDateAndTime,orderDate,"Delivered",recipientInfoSale,netAmountSaleOrder);

				m_assert.assertTrue(bSaleOrderFoundAndClicked," Sale Order Found and Clicked In Sale Transaction");

				addDataFromSalesOrderForSaleTransaction();

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.header_opticalBillHeader),
						" View Sale Transaction Displayed and Header as : <b> Optical Bill </b>");

				boolean printA4InSale = validatePrintButtonFunctionality(oPage_Purchase.button_printA4SizeButton,"Print4 Size In Sale Transaction");
				boolean printA5InSale = validatePrintButtonFunctionality(oPage_Purchase.button_printA5SizeButton,"Print5 Size In Sale Transaction");

				boolean emailSaleTransaction = validateEmailButtonFunctionality(oPage_Sale.button_emailSaleTransactionButton,"Sale Transaction Preview");

				m_assert.assertTrue(printA4InSale," PrintA4 Working  In Sale Transaction");
				m_assert.assertTrue(printA5InSale," PrintA5 Working  In Sale Transaction");
				m_assert.assertTrue(emailSaleTransaction," Email Working In Sale Transaction");

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_returnPurchaseTransaction),
						" Return Button Displayed In Sale Transaction Order Preview RHS Side");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.button_cancelBillInSale),
						" Cancel Bill Button Displayed In Sale Transaction Order Preview RHS Side");

				validatingDataInViewItemRHSSide(oPage_Sale.list_keysAndValuesInViewOrderList,viewSaleTransactionPatientDetailsList,saleOrderPatientDetailsDataList);

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.header_itemDetailsSection),
						" <b> Item Details </b> Section Header Displayed");

				String taxRate = "5";
				Double taxableAmountCalculated  = (Double.parseDouble(netAmountSaleOrder)*100)/(100+Double.parseDouble(taxRate));
				Double taxAmount = Double.parseDouble(netAmountSaleOrder) - taxableAmountCalculated;

				addDataForItemDetailsTableInSaleTransaction();

				String arrayConverted [] = new String[saleItemDetailsActualHeaderList.size()];
				arrayConverted = saleItemDetailsActualHeaderList.toArray(arrayConverted);
				String arrayValueConverted [] = new String[saleItemDetailsDataList.size()];
				arrayValueConverted = saleItemDetailsDataList.toArray(arrayValueConverted);

				validatingHeaderInTable(oPage_Sale.list_itemDetailsTableHeaderList,arrayConverted," Item Details Of Sale Transaction ");

				validatingHeaderInTable(oPage_Sale.list_itemDetailsTableDataList,arrayValueConverted," Item Details Of Sale Transaction ");

				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_taxableAmountValue).equalsIgnoreCase(String.format("%.2f", taxableAmountCalculated)),
						"Taxable Amount Label Displayed and Its Values as : "+String.format("%.2f", taxableAmountCalculated));
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_sgstAmountValue).equalsIgnoreCase(String.format("%.2f",taxAmount/2)),
						"SGST2.5 Label Displayed and Its Value as : "+String.format("%.2f",taxAmount/2));
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_cgstAmountValue).equalsIgnoreCase(String.format("%.2f",taxAmount/2)),
						"CGST2.5 Label Displayed and Its Value as : "+String.format("%.2f",taxAmount/2));
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_totalAmtIncAmountValue).equalsIgnoreCase(netAmountSaleOrder),
						"Total Amt Incl. Tax Label Displayed and its Value as : "+netAmountSaleOrder);
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_discountValue).equalsIgnoreCase(discount),
						"Discount: Label Displayed and its Value as : "+discount);
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_offerValue).equalsIgnoreCase(discount),
						"Offer: Label Displayed and its Value as : "+discount);
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_totalOfDiscountValue).equalsIgnoreCase(discount),
						"Total of all discount: Label Displayed and its Value as : "+discount);
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_netAmountValue).equalsIgnoreCase(netAmountSaleOrder),
						"Net Amount (₹): Label Displayed and its Value as : "+netAmountSaleOrder);

				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_receiveFromAdvanceValue).equalsIgnoreCase(settleAdvanceValue+".00 Details"),
						"Received from Advance : Label Displayed and its Value as : "+Cls_Generic_Methods.getTextInElement(oPage_Sale.text_receiveFromAdvanceValue));

				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_paymentReceiveValue).equalsIgnoreCase(amountAfterAdvance+" Details"),
						"Payment Received : Label Displayed and its Value as : "+Cls_Generic_Methods.getTextInElement(oPage_Sale.text_paymentReceiveValue));

				addItemDetailsLabelValueSaleTransaction();

				validateReceiveFromAdvanceDetailsSection(oPage_Sale.text_receiveFromAdvanceDetailsButtonInViewSale);
				validatePaymentReceivedDetailsSection(oPage_Sale.text_paymentReceiveValueDetailsButtonInViewSale);

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.text_patientAllTransactionHeader),
						" Patient All Transactions Section Header Displayed");

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_billButton),
						" Bill Button Displayed And Clicked In Sale Transaction ");
				m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_advanceReceiptButton,5),
						" Advance Receipt Button Displayed In View Bill Dropdown");

				String billCreatedTextOnUI = Cls_Generic_Methods.getTextInElement(oPage_PatientQueue.text_previousBills);


				String expectedBillCreatedTextInList = orderDateDifferentFormat.substring(0,2)+" "+getMonthNameByNumber(Integer.parseInt(orderDateDifferentFormat.substring(3,5)))+"'"
						+orderDateDifferentFormat.substring(8,10)+" at "+orderTime.substring(0,5)+" "+orderTime.substring(5,7)+" Opt";


				String expectedBillCreatedTextInListSecond = orderDateDifferentFormat.substring(0,2)+" "+getMonthNameByNumber(Integer.parseInt(orderDateDifferentFormat.substring(3,5)))+"'"
						+orderDateDifferentFormat.substring(8,10)+" at "+time.substring(0,5)+" "+time.substring(5,7)+" Opt";


				m_assert.assertTrue(billCreatedTextOnUI.equalsIgnoreCase(expectedBillCreatedTextInList) || billCreatedTextOnUI.equalsIgnoreCase(expectedBillCreatedTextInListSecond),
						"Bill Name displayed Correctly as Combination of Date and Time In Bill Dropdown");

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.text_previousBills),
						"Previous Bill Text Displayed And Previously Created Bill Clicked");

				m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Sale.text_previewBillOptical,5),
						" Optical Bill Opened And Header Displayed as : Optical Bill");

				validatingDataInViewItemRHSSide(oPage_Sale.list_keysAndValuesInPharmacyBillList,patientDetailsListInViewBill,saleOrderPatientDetailsListInViewBill);

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.header_itemDetailsSectionInPharmacyBill),
						"Item Details: Label Displayed In View Optical Bill");

				String arrayConvertedItemHeader [] = new String[billItemDetailsActualHeaderList.size()];
				arrayConvertedItemHeader = billItemDetailsActualHeaderList.toArray(arrayConvertedItemHeader);
				String arrayConvertedItemValue [] = new String[billItemDetailsDataList.size()];
				arrayConvertedItemValue = billItemDetailsDataList.toArray(arrayConvertedItemValue);
				String arrayConvertedItemLabelValue [] = new String[itemDetailsLabelValue.size()];
				arrayConvertedItemLabelValue = itemDetailsLabelValue.toArray(arrayConvertedItemLabelValue);

				validatingHeaderInTable(oPage_Sale.list_keysOfItemDetailsInPharmacyBillList,arrayConvertedItemHeader," Item Details Of View Bill ");

				validatingHeaderInTable(oPage_Sale.list_valueOfItemDetailsInPharmacyBillList,arrayConvertedItemValue," Item Details Of View Bill ");

				validatingHeaderInTable(oPage_Sale.list_labelOfItemDetailsInPharmacyBillList,billTableHeaderList," Item Details Label Of View Bill ");

				validatingValueInTable(oPage_Sale.list_valueOfLabelOfItemDetailsInPharmacyBillList,arrayConvertedItemLabelValue," Item Details Label Value Of View Bill ");

				validateReceiveFromAdvanceDetailsSection(oPage_Sale.button_receiveFromAdvanceDetailsButton);

				validatePaymentReceivedDetailsSection(oPage_Sale.button_paymentReceivedDetailsButton);

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.header_opticalBillHistoryHeader),
						" Optical Bill Header Displayed ");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.text_createdLabelInHistory),
						"Created Label Displayed In Optical History Section RHS Side");


				String actualCreatedOnInHistory = EHR_Data.user_PRAkashTest+" "+time.substring(0,5)+time.substring(5,7).toLowerCase()+", "+orderDateDifferentFormat.substring(0,2)+" "+getMonthNameByNumber(Integer.parseInt(orderDateDifferentFormat.substring(3,5)));

				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_createdByAndOnLabelInHistory).replace("\n", " ").contains(actualCreatedOnInHistory),
						" Created By User Name and Time Displayed In History as :<b> "+Cls_Generic_Methods.getTextInElement(oPage_Sale.text_createdByAndOnLabelInHistory)+"</b>");

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.header_advancePaymentSectionHeaderInHistory),
						" Bill Settled Using Advance Displayed amd Header Displayed as : Advance Payment");

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.header_paymentReceivedSectionHeaderInHistory),
						" Bill Settled By MOP Displayed amd Header Displayed as : Payment Received ");

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.list_paidByHeaderAdvancePaymentSectionInHistoryList),
						"Paid By Header Displayed In Advance Payment Table");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.list_amountHeaderAdvancePaymentSectionInHistoryList),
						"Amount Header Displayed In Advance Payment Table");

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.list_paidByPaymentReceivedSectionInHistoryList),
						"Paid By Header Displayed In Payment Received  Table");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.list_amountHeaderPaymentReceivedSectionInHistoryList),
						"Amount Header Displayed In Payment Received  Table");

				String advanceIdInAdvancePayment = "Advance No: "+advanceIdInSaleOrder;
				String settleIdInAdvancePayment = "Settle Receipt ID: "+advanceIdInSaleOrder+"-SE-01";
				String byAndOnInAdvancePayment = "By "+EHR_Data.user_PRAkashTest+" on "+time.substring(0,5)+" "+time.substring(5,7)+", "+orderDateDifferentFormat;
				String paidByValueInAdvancePayment = advanceIdInAdvancePayment+" "+settleIdInAdvancePayment+" "+byAndOnInAdvancePayment;

				String paidByArray[] = Cls_Generic_Methods.getTextInElement(oPage_Sale.list_paidByValuePaymentReceivedSectionInHistoryList).split("\n");
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.list_paidByValueAdvancePaymentSectionInHistoryList).replaceAll("\n", " ").contains(paidByValueInAdvancePayment),
						"Paid By Column Value Displayed In Advance Payment Table as : "+ Cls_Generic_Methods.getTextInElement(oPage_Sale.list_paidByValueAdvancePaymentSectionInHistoryList));

				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.list_amountValueAdvancePaymentSectionInHistoryList).
								equalsIgnoreCase(settleAdvanceValue+".00/-"),
						"Amount Column Value Displayed Correctly In Advance Payment Table as : "+settleAdvanceValue+".00/-");

				m_assert.assertTrue(paidByArray[0].equalsIgnoreCase(CommonActions.getFullPatientName(myPatient)),
						"In Paid By Column User Value Displayed In Payment Received  Table as : "+paidByArray[0]);

				m_assert.assertTrue(paidByArray[1].equalsIgnoreCase(paymentTime.substring(0,5)+" "+paymentTime.substring(5,7)+", "+orderDateDifferentFormat),
						"In Paid By Column Date And Time Value Displayed In Payment Received  Table as : "+paidByArray[1]);

				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.list_amountHeaderValuePaymentReceivedSectionInHistoryList).replaceAll("\n", " ").
								contains(amountAfterAdvance+"/-  ("+modeOfPayment[0]+")"),
						"Amount Column Value Displayed Correctly In Payment Received Table as : "+amountAfterAdvance+"/-  ("+modeOfPayment[0]+")");

				boolean PrintA4InBill = validatePrintButtonFunctionality(oPage_Sale.button_printA4SizeButton,"Print4 Size");
				boolean PrintA5InBill = validatePrintButtonFunctionality(oPage_Sale.button_printA5SizeButton,"Print5 Size");

				m_assert.assertTrue(PrintA4InBill, " PrintA4 Work In Optical Bill");
				m_assert.assertTrue(PrintA5InBill, " PrintA5 Work In Optical Bill");

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.button_cancelBillInPharmacyBill),
						" Cancel Bill Button Displayed");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						" Close Button Displayed and Clicked In Optical Bill");
				Cls_Generic_Methods.customWait();

				String advanceCreatedSecond = createAdvance("");
				advanceValueList.add(settleAdvanceValue);
				advanceValueList.add(advanceCreatedSecond);

				bSaleOrderFoundAndClicked = getSaleTransactionFromTransactionList(
						"1",salesOrderDateAndTime,orderDate,"Delivered",recipientInfoSale,netAmountSaleOrder);

				Cls_Generic_Methods.customWait();
				m_assert.assertTrue(bSaleOrderFoundAndClicked ," Sale Record Clicked");

				Double totalAdvanceCal = Double.parseDouble(advanceValueList.get(0))+Double.parseDouble(advanceValueList.get(1));
				Double remainingAmt = totalAdvanceCal - Double.parseDouble(settleAdvanceValue);
				String totalAndRemainingAmountInAdvanceSection = "₹"+totalAdvanceCal+" ("+remainingAmt+" )";

				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_advancePaymentSectionHeader).equalsIgnoreCase("Advance Payment (Remaining Adv. Amt.): "+totalAndRemainingAmountInAdvanceSection),
						" Advance Payment Section Displayed and Header as : "+Cls_Generic_Methods.getTextInElement(oPage_Sale.text_advancePaymentSectionHeader));

				String firstAdvanceText = "₹"+Double.parseDouble(advanceValueList.get(1))+" (₹"+Double.parseDouble(advanceValueList.get(1))+") on "+orderDateDifferentFormat.substring(0,2)+" "+getMonthNameByNumber(Integer.parseInt(orderDateDifferentFormat.substring(3,5)))+"'"
						+orderDateDifferentFormat.substring(8,10);


				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_advancePaymentFirst).equalsIgnoreCase(firstAdvanceText),
						"First Advance Text Displayed correctly In Advance Section");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.text_advancePaymentFirst),
						"First Advance Clicked In Advance List In Advance Section");

				Cls_Generic_Methods.customWait();

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.header_AdvanceReceiptTemplateHeader),
						" Created Advance Opened and Header Displayed as : Advance Receipt");

				addDataForViewSecondCreatedAdvanceDetails();

				validatingDataInViewItemRHSSide(oPage_Sale.list_advanceReceiptKeysAndValueList,advanceReceiptTemplateHeaderList,secondAdvanceReceiptTemplateValueList);

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_cancellationAdvance),
						"Cancellation Button Clicked");
				Cls_Generic_Methods.customWait();


				createRefund("RSO-ADV-"+secondAdvanceId,cashPay);

				m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_closeAdvance),
						" Close Button Clicked");
				Cls_Generic_Methods.customWait();


				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.text_advancePaymentSecond),
						"Second Advance Clicked ");
				Cls_Generic_Methods.customWait();
				addDataForViewCreatedAdvanceDetails();

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.button_advanceReceivedDetailsButtonInViewCreatedAdvance),
						" Details Button CLicked In View Advance Template");
				Cls_Generic_Methods.customWait(4);

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.header_advanceMopReceiptsViewTemplate),
						"Advance MOP Receipts Template Opened");


				validatingHeaderInTable(oPage_Sale.list_keysAdvanceMOPReceiptHeaderList,advanceMOPReceiptTemplateHeaderList,"Advance Mop Receipt");

				int sizeOfMop = oPage_Sale.list_noValueAdvanceMOPReceiptList.size();
				for(WebElement eSR : oPage_Sale.list_noValueAdvanceMOPReceiptList){

					int index = oPage_Sale.list_noValueAdvanceMOPReceiptList.indexOf(eSR);
					String eTextValue = Cls_Generic_Methods.getTextInElement(eSR).trim();
					if(sizeOfMop -1 == index){
						m_assert.assertTrue(eTextValue.equalsIgnoreCase("Total:") ,
								" Advance MOP No. Column Value Displayed as : "+eTextValue+" at index "+index);

					}else {
						if (eTextValue.equalsIgnoreCase(String.valueOf(index + 1))) {
							m_assert.assertTrue( " Advance MOP No. Column Value Displayed as : "+eTextValue+" at index "+index);
						}else{
							m_assert.assertTrue( " Advance MOP No. Column Value Not Displayed as : "+eTextValue+" at index "+index);

						}
					}
				}
				String totalAmountOfAllMop = "";
				for(WebElement eSR : oPage_Sale.list_modeOfPaymentValueAdvanceReceiptList){

					int index = oPage_Sale.list_modeOfPaymentValueAdvanceReceiptList.indexOf(eSR);
					String eTextValue = Cls_Generic_Methods.getTextInElement(eSR).trim();
					if(sizeOfMop -1 == index){
						totalAmountOfAllMop = eTextValue;
					}else {
						if (eTextValue.equalsIgnoreCase(modeOfPayment[index])) {
							m_assert.assertTrue(" Advance MOP Mode Of Payment Column Value Displayed Correctly as : " + eTextValue + " at index " + index);
						} else {
							m_assert.assertTrue(" Advance MOP Mode Of Payment Column Value Not Displayed as : " + eTextValue + " at index " + index);
						}
					}
				}

				for(WebElement eSR : oPage_Sale.list_advanceMopIDValueAdvanceReceiptHeaderList){

					int index = oPage_Sale.list_advanceMopIDValueAdvanceReceiptHeaderList.indexOf(eSR);
					String eTextValue = Cls_Generic_Methods.getTextInElement(eSR).trim();
					if (eTextValue.equalsIgnoreCase(advanceIdInSaleOrder+"-0"+index+1)) {
						m_assert.assertTrue(" Advance MOP Advance Id Column Value Displayed Correctly as : " + eTextValue + " at index " + index);
					} else {
						m_assert.assertTrue(" Advance MOP Advance Id Column Value Not Displayed as : " + eTextValue + " at index " + index);
					}
				}
				Double amountCalculated  = 0.0 ;
				for(WebElement eSR : oPage_Sale.list_amountValueAdvanceReceiptHeaderList){

					int index = oPage_Sale.list_amountValueAdvanceReceiptHeaderList.indexOf(eSR);
					String eTextValue = Cls_Generic_Methods.getTextInElement(eSR).trim();
					amountCalculated = amountCalculated+ Double.parseDouble(eTextValue);

					if (eTextValue.equalsIgnoreCase(modeOfPayment[index])) {
						m_assert.assertTrue(" Advance MOP Amount Column Value Displayed Correctly as : " + eTextValue + " at index " + index);
					} else {
						m_assert.assertTrue(" Advance MOP Amount Column Value Not Displayed as : " + eTextValue + " at index " + index);
					}

				}

				m_assert.assertTrue(Double.parseDouble(totalAmountOfAllMop) == amountCalculated ," Total Amount Calculated correctly");

				Cls_Generic_Methods.clickElementByJS(driver,oPage_Sale.button_closeByXButtonInAdvanceMopReceipts);
				Cls_Generic_Methods.customWait();

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.text_advanceHistoryLabel),
						"Advance History  Label Displayed and As : Advance History (Note: This will not be printed)");

				validatingHeaderInTable(oPage_Sale.list_keysAdvanceReceiptHeaderList,advanceHistoryTemplateHeaderList,"Advance History Table ");

				addDataForAdvanceHistory();
				for(WebElement eHeader : oPage_Sale.list_valueAdvanceReceiptHeaderList){

					int index =  oPage_Sale.list_valueAdvanceReceiptHeaderList.indexOf(eHeader);
					String valueText = Cls_Generic_Methods.getTextInElement(eHeader);
					if(index == 7){
						WebElement print = eHeader.findElement(By.xpath(".//button"));
						WebElement email = eHeader.findElement(By.xpath(".//a[@id='record-mail-html']"));
						boolean settledPrint = validatePrintButtonFunctionality(print," Print Button For Settled Amount In History Table");
						boolean settledEmail = validateEmailButtonFunctionality(email,"Email Button For Settled Amount In History Table");
						m_assert.assertTrue(settledEmail , " Email Working For Settled Amount");
						m_assert.assertTrue(settledPrint , " Print Working For Settled Amount");


					}else {
						if(index == 8) {
							break;
						}else {
							if (valueText.equalsIgnoreCase(advanceHistoryTemplateValueList.get(index))) {
								m_assert.assertTrue(valueText + " Value Name Present In Item Details Section");
							} else {
								m_assert.assertWarn(" Either Column Missing or Removed In New Releases");
							}
						}
					}
				}

				boolean printAdvance = validatePrintButtonFunctionality(oPage_Sale.button_printAdvanceReceiptTemplateButton,
						" Print Button For Advance Receipt");
				boolean emailAdvance = validateEmailButtonFunctionality(oPage_Sale.button_emailAdvanceReceiptTemplateButton,"Email Button For Advance Receipt");

				m_assert.assertTrue(printAdvance ," Print Functionality Working In Advance Template");
				m_assert.assertTrue(emailAdvance ," Email Functionality Working In Advance Template");

				Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_closeAdvance);
				Cls_Generic_Methods.customWait();

				bSaleOrderFoundAndClicked = getSaleTransactionFromTransactionList(
						"1",salesOrderDateAndTime,orderDate,"Delivered",recipientInfoSale,netAmountSaleOrder);
				Cls_Generic_Methods.customWait();

				m_assert.assertTrue(bSaleOrderFoundAndClicked," Sale Transaction Found");

				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_refundPaymentSectionHeader).equalsIgnoreCase("Refund Payment : ₹"+Double.parseDouble(cashPay)),
						" Refund Payment Section displayed as : "+Cls_Generic_Methods.getTextInElement(oPage_Sale.text_refundPaymentSectionHeader));

				String firstRefundText = "₹"+Double.parseDouble(cashPay)+" on "+orderDate.substring(0,2)+" "+getMonthNameByNumber(Integer.parseInt(orderDate.substring(3,5)))+"'"
						+orderDate.substring(8,10);

				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_firstRefundPayment).equalsIgnoreCase(firstRefundText),
						" First Refund Text Displayed correctly as "+firstRefundText);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.text_firstRefundPayment),
						" First Refund Clicked");
				Cls_Generic_Methods.customWait();
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.header_refundReceiptViewTemplate),
						" Refund Receipt Templated Opened as Refund Receipt");

				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_refundReceiptTemplateMessage).equalsIgnoreCase("Refunded a sum of ₹ 1000.00 to "+CommonActions.getFullPatientName(myPatient)+" dated "+orderDateDifferentFormat+"."),
						" Refund Message Displayed as : Refunded a sum of ₹ 1000.00 to "+CommonActions.getFullPatientName(myPatient)+" dated "+orderDateDifferentFormat+".");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.text_refundReceiptTemplateRemark),
						" Refund remark displayed ");

				boolean printRefund = validatePrintButtonFunctionality(oPage_Sale.button_printButtonInRefundTemplate," Print Button In Refund template");
				boolean emailRefund = validateEmailButtonFunctionality(oPage_Sale.button_emailButtonInRefundTemplate ," Refund Template");

				m_assert.assertTrue(printRefund ," Print Functionality Working In Refund");
				m_assert.assertTrue(emailRefund ," Email Functionality Working In Refund");

				Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_closeAdvance);
				Cls_Generic_Methods.customWait();

				Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
				Cls_Generic_Methods.customWait();


			}
			catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}

	}

	@Test(enabled = true, description = "Validating Return In Sale Transaction")
	public void validateReturnSaleTransactionFunctionality() {

		Page_Purchase oPage_Purchase = new Page_Purchase(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Sale oPage_Sale = new Page_Sale(driver);
		Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);
		Page_Lot oPage_Lot = new Page_Lot(driver);


		String returnItemDetailsHeaderList[] = {"#","Description","Billed Qty.","Returnable Qty.","Return Qty.","Batch No.","Model No.",
				"U.Price With Tax","Tax","Discount","Return Amt.","Remark"};

		String returnSaveHeaderList[] = {"Return Receipt Id:","Bill No:","Patient:","Patient ID:","Patient Mobile"};
		String billTableHeaderList[] = {"Taxable Amount :","SGST2.5 :","CGST2.5 :","Total Amt Incl. Tax :","Discount :","Offer :",
				"Total of all discounts :","Net Amount (₹) :","Received from Advance :","Payment Received :"};


		try {
			CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
			try {

				CommonActions.selectStoreOnApp(sStore);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();

				CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
				Cls_Generic_Methods.customWait(3);
				Cls_Generic_Methods.clearValuesInElement(oPage_Lot.input_InventorySearch);
				Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_InventorySearch, myMedicationName);
				Cls_Generic_Methods.customWait(3);
				oPage_Lot.input_InventorySearch.sendKeys(Keys.ENTER);
				Cls_Generic_Methods.customWait(3);
				boolean bItemFoundInLot1 = false;
				for (WebElement eVariant : oPage_Lot.list_LotDetailsOnVariants) {
					if (Cls_Generic_Methods.getTextInElement(eVariant).contains(myMedicationName)) {
						Cls_Generic_Methods.clickElement(eVariant);
						bItemFoundInLot1 = true;
						break;
					}
				}

				m_assert.assertTrue(bItemFoundInLot1, "Item found in Items Lot Page");
				Cls_Generic_Methods.customWait(5);


				String sAvailableStock = Cls_Generic_Methods.getTextInElement(oPage_Lot.value_AvailableStock);

				CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale");

				String recipientInfoSale = myPatient.getsSALUTATION()+" "+myPatient.getsFIRST_NAME()+""+myPatient.getsMOBILE_NUMBER();

				boolean bSaleTransactionFoundAndClicked = getSaleTransactionFromTransactionList(
						"1",salesOrderDateAndTime,orderDate,"Delivered",recipientInfoSale,netAmountSaleOrder);
				Cls_Generic_Methods.customWait(5);

				if (bSaleTransactionFoundAndClicked) {

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.button_cancelBillInSale),
							" Cancel Bill Button Clicked In Sale Transaction");
					m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Sale.header_opticalBillDetailsHeader,5),
							" Cancel Bill Template Opened and Header Displayed As : Optical Bill Details");

					validatingDataInViewItemRHSSide(oPage_Sale.list_keysAndValuesInPharmacyBillList,patientDetailsListInViewBill,saleOrderPatientDetailsListInViewBill);

					String arrayConvertedItemHeader [] = new String[billItemDetailsActualHeaderList.size()];
					arrayConvertedItemHeader = billItemDetailsActualHeaderList.toArray(arrayConvertedItemHeader);
					String arrayConvertedItemValue [] = new String[billItemDetailsDataList.size()];
					arrayConvertedItemValue = billItemDetailsDataList.toArray(arrayConvertedItemValue);
					String arrayConvertedItemLabelValue [] = new String[itemDetailsLabelValue.size()];
					arrayConvertedItemLabelValue = itemDetailsLabelValue.toArray(arrayConvertedItemLabelValue);

					validatingHeaderInTable(oPage_Sale.list_keysOfItemDetailsInPharmacyBillList,arrayConvertedItemHeader," Item Details Of View Bill ");

					validatingHeaderInTable(oPage_Sale.list_valueOfItemDetailsInPharmacyBillList,arrayConvertedItemValue," Item Details Of View Bill ");

					validatingHeaderInTable(oPage_Sale.list_labelOfItemDetailsInPharmacyBillList,billTableHeaderList," Item Details Label Of View Bill ");

					validatingValueInTable(oPage_Sale.list_valueOfLabelOfItemDetailsInPharmacyBillList,arrayConvertedItemLabelValue," Item Details Label Value Of View Bill ");

					validateReceiveFromAdvanceDetailsSection(oPage_Sale.button_receiveFromAdvanceDetailsButton);

					validatePaymentReceivedDetailsSection(oPage_Sale.button_paymentReceivedDetailsButton);

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.header_opticalBillHistoryHeader),
							" Optical Bill Header Displayed ");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.text_createdLabelInHistory),
							"Created Label Displayed In Optical History Section RHS Side");


					String actualCreatedOnInHistory = EHR_Data.user_PRAkashTest+" "+time.substring(0,5)+time.substring(5,7).toLowerCase()+", "+orderDateDifferentFormat.substring(0,2)+" "+getMonthNameByNumber(Integer.parseInt(orderDateDifferentFormat.substring(3,5)));

					m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_createdByAndOnLabelInHistory).replace("\n", " ").contains(actualCreatedOnInHistory),
							" Created By User Name and Time Displayed In History as :<b> "+Cls_Generic_Methods.getTextInElement(oPage_Sale.text_createdByAndOnLabelInHistory)+"</b>");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.header_advancePaymentSectionHeaderInHistory),
							" Bill Settled Using Advance Displayed amd Header Displayed as : Advance Payment");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.header_paymentReceivedSectionHeaderInHistory),
							" Bill Settled By MOP Displayed amd Header Displayed as : Payment Received ");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.list_paidByHeaderAdvancePaymentSectionInHistoryList),
							"Paid By Header Displayed In Advance Payment Table");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.list_amountHeaderAdvancePaymentSectionInHistoryList),
							"Amount Header Displayed In Advance Payment Table");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.list_paidByPaymentReceivedSectionInHistoryList),
							"Paid By Header Displayed In Payment Received  Table");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.list_amountHeaderPaymentReceivedSectionInHistoryList),
							"Amount Header Displayed In Payment Received  Table");

					String advanceIdInAdvancePayment = "Advance No: "+advanceIdInSaleOrder;
					String settleIdInAdvancePayment = "Settle Receipt ID: "+advanceIdInSaleOrder+"-SE-01";
					String byAndOnInAdvancePayment = "By "+EHR_Data.user_PRAkashTest+" on "+time.substring(0,5)+" "+time.substring(5,7)+", "+orderDateDifferentFormat;
					String paidByValueInAdvancePayment = advanceIdInAdvancePayment+" "+settleIdInAdvancePayment+" "+byAndOnInAdvancePayment;

					String paidByArray[] = Cls_Generic_Methods.getTextInElement(oPage_Sale.list_paidByValuePaymentReceivedSectionInHistoryList).split("\n");

					m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.list_paidByValueAdvancePaymentSectionInHistoryList).replaceAll("\n", " ").contains(paidByValueInAdvancePayment),
							"Paid By Column Value Displayed In Advance Payment Table as : "+ Cls_Generic_Methods.getTextInElement(oPage_Sale.list_paidByValueAdvancePaymentSectionInHistoryList));

					m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.list_amountValueAdvancePaymentSectionInHistoryList).
									equalsIgnoreCase(settleAdvanceValue+".00/-"),
							"Amount Column Value Displayed Correctly In Advance Payment Table as : "+settleAdvanceValue+".00/-");

					m_assert.assertTrue(paidByArray[0].equalsIgnoreCase(CommonActions.getFullPatientName(myPatient)),
							"In Paid By Column User Value Displayed In Payment Received  Table as : "+paidByArray[0]);



					m_assert.assertTrue(paidByArray[1].equalsIgnoreCase(paymentTime.substring(0,5)+" "+paymentTime.substring(5,7)+", "+orderDateDifferentFormat),
							"In Paid By Column Date And Time Value Displayed In Payment Received  Table as : "+paidByArray[1]);

					m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.list_amountHeaderValuePaymentReceivedSectionInHistoryList).replaceAll("\n", " ").
									contains(amountAfterAdvance+"/-  ("+modeOfPayment[0]+")"),
							"Amount Column Value Displayed Correctly In Payment Received Table as : "+amountAfterAdvance+"/-  ("+modeOfPayment[0]+")");

					Cls_Generic_Methods.clickElement(oPage_Sale.button_closeByXButton);
					Cls_Generic_Methods.customWait(5);

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_returnPurchaseTransaction),
							"Return Button Displayed And Clicked");
					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_saveAddNewLot,10);

					m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
							" Save Changes Button Clicked");
					Cls_Generic_Methods.customWait(4);

					m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_Sale.input_returnNoteTextBox, "class").contains("error"),
							"Validated the Return Note is Required and Displayed");
					m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_Sale.input_modeOfPaymentInReturnTemplate, "class").contains("error"),
							"Validated the Mode Of Payment is Required And Displayed");
					m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_Sale.input_grossReturnAmountInReturnTemplate, "class").contains("error"),
							"Validated the Gross amount is Required and Displayed");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.strong_noteLabel),
							"Return Note Label Displayed as  : Note");
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_returnNoteTextBox,sReturnNote),
							"Return Note Entered as : "+sReturnNote);
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.strong_returnDateLabel),
							"Return Date Label Displayed as  : Return Date");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.strong_returnTimeLabel),
							"Return Time Label Displayed as  : Return Time");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.label_itemDetailsInReturn),
							"Item Details:  Label Displayed in return ");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.label_modeOfPaymentLabel),
							"Mode of Payment Label Displayed in Return ");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.label_grossAmountLabel),
							"Gross Return Amt. Label Displayed in Return ");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.label_returnChargesLabel),
							"Return charges Label Displayed in Return ");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.label_netReturnValueLabel),
							"Net Return Value Label Displayed in Return ");

					validatingHeaderInTable(oPage_Sale.list_itemDetailsTableHeaderListInReturn,returnItemDetailsHeaderList,"Item Details In Return");

					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_returnQty,String.valueOf(Integer.parseInt(inputQty))+1),
							" Return Qty Entered More Than Returnable Qty as "+Integer.parseInt(inputQty)+1);

					Cls_Generic_Methods.clickElement(oPage_Sale.input_returnNoteTextBox);
					Cls_Generic_Methods.customWait();

					m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_Sale.input_returnQty, "class").contains("error"),
							"Validated the Return Quantity Can Not Entered Greater Than Qty Error and Displayed ");
					m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.label_returnQtyError).equalsIgnoreCase("Cannot Checkout more than stock"),
							" More than Return Qty Error Message Displayed as Cannot Checkout more than stock ");

					Cls_Generic_Methods.clearValuesInElement(oPage_Sale.input_returnQty);

					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_returnQty,inputQty),
							" Return Qty Entered as as "+inputQty);

					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_returnItemRemark,"Return Remark 1"),
							" Return Remark Entered as : Return Remark 1");

					m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.list_slNoInReturn).equalsIgnoreCase("1"),
							" Sl No. Header value Displaying Correctly In Table as : 1");
					m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.list_descriptionInReturn).equalsIgnoreCase(myMedicationName),
							" Description Header value Displaying Correctly In Table as "+myMedicationName);

					m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_Sale.input_billedQty,"value").
							equalsIgnoreCase(String.valueOf(Double.parseDouble(inputQty))), " Billed Qty Header value Displaying Correctly In Table");

					m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_Sale.input_returnableQty,"value").
							equalsIgnoreCase(String.valueOf(Double.parseDouble(inputQty))), " Returnable Qty Header value Displaying Correctly In Table");

					m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_uPriceWithTaxInReturn).equalsIgnoreCase(String.format("%.2f", Double.parseDouble(netAmountSaleOrder)/2)),
							" Unit Price Column Value Displayed Correctly as : "+String.format("%.2f", Double.parseDouble(netAmountSaleOrder)/2));

					String tax = String.format("%.2f",Double.parseDouble(billItemDetailsDataList.get(billItemDetailsDataList.size()-3)));

					m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_taxInReturn).replace("\n"," ").equalsIgnoreCase(tax+" (5.0% - Inc.)"),
							" Tax Column Value Displayed Correctly as : "+tax+" (5.0% - Inc.)");

					m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_discountInReturn).replace("\n"," ").equalsIgnoreCase("0.00 (0.0%)"),
							" Tax Column Value Displayed Correctly as : 0.00 (0.0%)");

					m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_returnAmtInReturn).equalsIgnoreCase(String.valueOf(Double.parseDouble(netAmountSaleOrder))),
							" Return Amount Value Displayed Correctly as : "+String.valueOf(Double.parseDouble(netAmountSaleOrder)));

					m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_Sale.input_grossReturnAmountInReturnTemplate,"value").equalsIgnoreCase(String.format("%.2f",Double.parseDouble(netAmountSaleOrder))),
							" Gross Return Amt Calculated Correctly and Displayed as : "+netAmountSaleOrder);

					String netReturnChargesBeforeReturnCharge = Cls_Generic_Methods.getElementAttribute(oPage_Sale.input_returnNetReturnValue,"value");

					m_assert.assertTrue(netReturnChargesBeforeReturnCharge.equalsIgnoreCase(String.format("%.2f",Double.parseDouble(netAmountSaleOrder))),
							" Net Return Charges Calculated Correctly and Displayed as :"+String.format("%.2f",Double.parseDouble(netAmountSaleOrder)));
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_returnCharges,"10"),
							" Return Charges Entered as : 10");
					Cls_Generic_Methods.customWait();

					String netReturnChargesAfterReturnCharge = Cls_Generic_Methods.getElementAttribute(oPage_Sale.input_returnNetReturnValue,"value");

					Double netReturnChargesAfterReturnChargeCalculated  = Double.parseDouble(netAmountSaleOrder) - Double.parseDouble("10");

					m_assert.assertTrue(netReturnChargesAfterReturnCharge.equalsIgnoreCase(String.format("%.2f", netReturnChargesAfterReturnChargeCalculated)),
							" Net Return Charges Calculated Correctly After Return Charges and Displayed as :"+netReturnChargesAfterReturnCharge);

					m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Sale.input_modeOfPaymentInReturnTemplate,modeOfPayment[0]),
							" Return Mode Of Payment Selected as : "+modeOfPayment[0]);

					Cls_Generic_Methods.clearValuesInElement(oPage_Sale.input_refundAmountBox);
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_refundAmountBox,netReturnChargesAfterReturnCharge),
							"Refund Amount Entered as "+netReturnChargesAfterReturnCharge);

					returnDate = Cls_Generic_Methods.getElementAttribute(oPage_Sale.input_returnDate,"value");
					returnTime = Cls_Generic_Methods.getElementAttribute(oPage_Sale.input_returnTime,"value");

					DateFormat dateFormat = new SimpleDateFormat("hh:mmaa");
					String returnSavetime = dateFormat.format(new Date());
					returnSavetime = returnSavetime.replace("am", "AM").replace("pm", "PM");
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_SaveChanges),
							" Save Button Clicked In Return");
					Cls_Generic_Methods.customWait(5);

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.header_opticalReturnBill),
							" Return Saved Header Displayed as Optical Return Bill");

					Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
					Cls_Generic_Methods.customWait();

					m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_returnPurchaseTransaction),
							" Return Functionality Working and Return Button Disabled Now");
					m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_Sale.button_returnDisabledButton,"data-title").equalsIgnoreCase("Cannot be return. A return is already collected against this invoice."),
							"Return Button Displayed as Disabled Correctly");
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_billButton),
							"Bill Button Displayed and Clicked");
					Cls_Generic_Methods.customWait( 4);

					String expectedReturnCreatedTextInList = returnDate.substring(0,2)+" "+getMonthNameByNumber(Integer.parseInt(returnDate.substring(3,5)))+"'"
							+returnDate.substring(8,10)+" at "+returnSavetime.substring(0,5)+" "+returnSavetime.substring(5,7)+" Opt Ref";

					String billCreatedTextOnUI = Cls_Generic_Methods.getTextInElement(oPage_Sale.text_returnBills);
					m_assert.assertTrue(billCreatedTextOnUI.equalsIgnoreCase(expectedReturnCreatedTextInList),
							" Return Bill Displayed In Bill List");

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.text_returnBills),
							"Return Bill Clicked in Bill List");
					Cls_Generic_Methods.customWait( 4);


					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.header_opticalReturnBill),
							" Return Bill Opened And  Header Displayed as Optical Return Bill");

					validatePrintButtonFunctionality(oPage_Sale.button_printA4ButtonInReturn,"Return Print");
					validatePrintButtonFunctionality(oPage_Sale.button_printA5ButtonInReturn," Return Print");

					Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
					Cls_Generic_Methods.customWait();

					Cls_Generic_Methods.driverRefresh();
					Cls_Generic_Methods.customWait(5);
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
							"Store pop up closed");
					Cls_Generic_Methods.customWait();
					CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale Return");
					Cls_Generic_Methods.customWait();

					String cancelDate = CommonActions.getRequiredFormattedDateTime("dd-MM-yyyy","yyyy-MM-dd",orderDate);
					//String salesOrderDateAndTimeText = cancelDate+"  |  "+cancelSaveTime.substring(0,5)+cancelSaveTime.substring(6,8);

					String salesOrderDateAndTimeText = cancelDate+"  |  "+returnSavetime;

					boolean bSaleReturnFound = false ;
					for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

						if (Cls_Generic_Methods.isElementDisplayed(row)) {
							List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

							String dateAndTimeOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(0));
							String recipientOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(1));
							String noteOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(2));
							String amountOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(3));
							String actionOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(4));


							if (dateAndTimeOnUI.contains(salesOrderDateAndTimeText) &&
									recipientInfoSale.contains(recipientOnUI) &&
									noteOnUI.equalsIgnoreCase(sReturnNote) &&
									String.valueOf(Double.parseDouble(netAmountSaleOrder)).equalsIgnoreCase(amountOnUI) &&
									actionOnUI.equalsIgnoreCase("Receive")
							) {
								WebElement receiveButton = purchaseRow.get(4).findElement(By.xpath("./button"));
								bSaleReturnFound = true ;
								m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver,receiveButton),
										"Receive Button Clicked In Sale Return , Auto Received List");
								Cls_Generic_Methods.customWait(2);
								break;
							}

						}
					}


					m_assert.assertTrue(bSaleReturnFound," Sale Return Record Found And Clicked");

					boolean bReturnPurchaseTransactionFoundInLot = false ;
					bItemFoundInLot1 = false ;
					CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
					Cls_Generic_Methods.customWait(3);
					Cls_Generic_Methods.clearValuesInElement(oPage_Lot.input_InventorySearch);
					Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_InventorySearch, myMedicationName);
					Cls_Generic_Methods.customWait(3);
					oPage_Lot.input_InventorySearch.sendKeys(Keys.ENTER);
					Cls_Generic_Methods.customWait(3);
					for (WebElement eVariant : oPage_Lot.list_LotDetailsOnVariants) {
						if (Cls_Generic_Methods.getTextInElement(eVariant).contains(myMedicationName)) {
							Cls_Generic_Methods.clickElement(eVariant);
							bItemFoundInLot1 = true;
							Cls_Generic_Methods.customWait(5);
							break;
						}
					}

					m_assert.assertTrue(bItemFoundInLot1, "Item found in Items Lot Page");

					if (bItemFoundInLot1) {

						String stockBeforeReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockBeforeInViewTemplate);
						String stockAfterReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockAfterInViewTemplate);
						String flowTextUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsFlowInViewTemplate);
						String dateAndTimeUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsDateAndTimeInViewTemplate);
						String availableStock = Cls_Generic_Methods.getTextInElement(oPage_Lot.value_AvailableStock);


						m_assert.assertTrue(Double.parseDouble(sAvailableStock)+2 == Double.parseDouble(availableStock),
								"Available Stock Displayed Correctly after Return as :"+availableStock);
						String date = dateAndTimeUI.split("\\|")[0].trim();
						String time = dateAndTimeUI.split("\\|")[1].trim();
						String purchaseReturnDateAndTime = date + "|" + time;

						Double stockAfterCalculated = convertStringToDouble(stockBeforeReturnUI) + convertStringToDouble(inputQty);

						if (flowTextUI.equalsIgnoreCase("In (Return)") &&
								Double.toString(stockAfterCalculated).equals(stockAfterReturnUI) &&
								purchaseReturnDateAndTime.equalsIgnoreCase(cancelDate + "|" + time)) {

							bReturnPurchaseTransactionFoundInLot = true;
						}
						m_assert.assertTrue(bReturnPurchaseTransactionFoundInLot,
								" <b> Sale Return Working Fine, as initial stock was: " + stockBeforeReturnUI +
										" after return stock is: " + stockAfterCalculated + "</b> ");

					}

					Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
					Cls_Generic_Methods.customWait();


				} else {
					m_assert.assertTrue("Sale Transaction Not found or not clicked");
				}

				Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
				Cls_Generic_Methods.customWait(4);

			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}

	}

	@Test(enabled = true, description = "Validating Cancel In Sale Transaction")
	public void validateCancelSaleTransactionFunctionality() {

		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Sale oPage_Sale = new Page_Sale(driver);
		Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);
		Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
		Page_Lot oPage_Lot = new Page_Lot(driver);
		Page_Purchase oPage_Purchase = new Page_Purchase(driver);


		try {
			CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
			try {

				CommonActions.selectStoreOnApp(sStore);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();
				CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
				Cls_Generic_Methods.customWait(3);
				Cls_Generic_Methods.clearValuesInElement(oPage_Lot.input_InventorySearch);
				Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_InventorySearch, myMedicationName);
				Cls_Generic_Methods.customWait(3);
				oPage_Lot.input_InventorySearch.sendKeys(Keys.ENTER);
				Cls_Generic_Methods.customWait(3);
				boolean bItemFoundInLot1 = false;
				for (WebElement eVariant : oPage_Lot.list_LotDetailsOnVariants) {
					if (Cls_Generic_Methods.getTextInElement(eVariant).contains(myMedicationName)) {
						Cls_Generic_Methods.clickElement(eVariant);
						bItemFoundInLot1 = true;
						break;
					}
				}

				m_assert.assertTrue(bItemFoundInLot1, "Item found in Items Lot Page");
				Cls_Generic_Methods.customWait(5);

				String sAvailableStock = Cls_Generic_Methods.getTextInElement(oPage_Lot.value_AvailableStock);

				CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
				Cls_Generic_Methods.customWait( );

				// creating order for cancel order button check
				boolean bCreateOrderForPolicy = createSaleOrderForPolicyCheck("Stockable");
				Cls_Generic_Methods.customWait();
				m_assert.assertTrue(bCreateOrderForPolicy," Sales Order Created");

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_MoreAction),
						" More Action Button Clicked");
				Cls_Generic_Methods.customWait(5);

				String recipientInfoSale = myPatient.getsSALUTATION()+" "+myPatient.getsFIRST_NAME()+""+myPatient.getsMOBILE_NUMBER();

				if (Cls_Generic_Methods.isElementDisplayed(oPage_Sale.button_cancelBillInSale)) {

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.button_cancelBillInSale),
							" Cancel Bill Button Clicked In Sale Transaction");
					m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Sale.header_opticalBillDetailsHeader,5),
							" Cancel Bill Template Opened and Header Displayed As : Optical Bill Details");

					m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.checkbox_acceptCheckBox).equalsIgnoreCase("All the items in the bill have been returned/taken back."),
							" Accept Message Displayed and as : All the items in the bill have been returned/taken back.");
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.checkbox_acceptCheckBox),
							" CheckBox Clicked In Cancel");
					Cls_Generic_Methods.customWait();
					m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Sale.select_modeOfPaymentInCancel,modeOfPayment[0]),
							" Mode Of Payment Selected In Cancel as "+modeOfPayment[0]);
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_amountReceivedInCancel,netAmountSaleOrder),
							"Amount Entered as  In Cancel as "+netAmountSaleOrder);
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_cancelReason,"Checking For Cancellation"),
							"Cancel Reason Entered as : Checking For Cancellation");
					DateFormat dateFormat = new SimpleDateFormat("hh:mmaa");
					String cancelSaveTime = dateFormat.format(new Date());
					cancelSaveTime = cancelSaveTime.replace("am", "AM").replace("pm", "PM");
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.button_cancelBillInCancel),
							" Cancel Bill Button Clicked");
					Cls_Generic_Methods.customWait(5);

					boolean bSaleTransactionFoundAndClicked = getSaleTransactionFromTransactionList(
							"1",salesOrderDateAndTime,orderDate,"Canceled",recipientInfoSale,netAmountSaleOrder);
					Cls_Generic_Methods.customWait();

					m_assert.assertTrue(bSaleTransactionFoundAndClicked, " Record Found After Cancel");

					try {
						m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_Sale.button_cancelBillInSale),
								" Cancel Bill Button Not Displayed Successfully");
					}catch (Exception e){

					}

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_billButton),
							"Bill Button Displayed and Clicked");
					Cls_Generic_Methods.customWait( 4);

					String expectedCancelCreatedTextInList = returnDate.substring(0,2)+" "+getMonthNameByNumber(Integer.parseInt(returnDate.substring(3,5)))+"'"
							+returnDate.substring(8,10)+" at "+cancelSaveTime.substring(0,5)+" "+cancelSaveTime.substring(5,7)+" Opt Ref";

					String billCreatedTextOnUI = Cls_Generic_Methods.getTextInElement(oPage_Sale.text_returnBills);

					m_assert.assertTrue(billCreatedTextOnUI.equalsIgnoreCase(expectedCancelCreatedTextInList),
							" Return Bill Displayed In Bill List");

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.text_returnBills),
							"Return Bill Clicked in Bill List");
					Cls_Generic_Methods.customWait();
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.header_opticalReturnBill),
							" Return Bill Opened And  Header Displayed as Optical Return Bill");
					Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
					Cls_Generic_Methods.customWait();

					Cls_Generic_Methods.driverRefresh();
					Cls_Generic_Methods.customWait(5);
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
							"Store pop up closed");
					Cls_Generic_Methods.customWait();
					CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale Return");
					Cls_Generic_Methods.customWait();

					String cancelDate = CommonActions.getRequiredFormattedDateTime("dd-MM-yyyy","yyyy-MM-dd",orderDate);

					String salesOrderDateAndTimeText = cancelDate+"  |  "+cancelSaveTime;

					boolean bSaleReturnFound = false ;
					for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

						if (Cls_Generic_Methods.isElementDisplayed(row)) {
							List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

							String dateAndTimeOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(0));
							String recipientOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(1));
							String noteOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(2));
							String amountOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(3));
							String actionOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(4));


							if (dateAndTimeOnUI.equals(salesOrderDateAndTimeText) &&
									recipientInfoSale.contains(recipientOnUI) &&
									noteOnUI.equalsIgnoreCase("Checking For Cancellation") &&
									String.valueOf(Double.parseDouble(netAmountSaleOrder)).equalsIgnoreCase(amountOnUI) &&
									actionOnUI.equalsIgnoreCase("Receive")
							) {
								WebElement receiveButton = purchaseRow.get(4).findElement(By.xpath("./button"));
								bSaleReturnFound = true ;
								m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver,receiveButton),
										"Receive Button Clicked In Sale Return , Auto Received List");
								Cls_Generic_Methods.customWait(2);
								break;
							}

						}
					}


					m_assert.assertTrue(bSaleReturnFound," Sale Return Record Found And Clicked");

					boolean bReturnPurchaseTransactionFoundInLot = false ;

					CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
					Cls_Generic_Methods.customWait(3);
					Cls_Generic_Methods.clearValuesInElement(oPage_Lot.input_InventorySearch);
					Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_InventorySearch, myMedicationName);
					Cls_Generic_Methods.customWait(3);
					oPage_Lot.input_InventorySearch.sendKeys(Keys.ENTER);
					Cls_Generic_Methods.customWait(3);
					for (WebElement eVariant : oPage_Lot.list_LotDetailsOnVariants) {
						if (Cls_Generic_Methods.getTextInElement(eVariant).contains(myMedicationName)) {
							Cls_Generic_Methods.clickElement(eVariant);
							bItemFoundInLot1 = true;
							Cls_Generic_Methods.customWait(5);
							break;
						}
					}

					m_assert.assertTrue(bItemFoundInLot1, "Item found in Items Lot Page");

					if (bItemFoundInLot1) {

						String stockBeforeReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockBeforeInViewTemplate);
						String stockAfterReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockAfterInViewTemplate);
						String flowTextUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsFlowInViewTemplate);
						String dateAndTimeUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsDateAndTimeInViewTemplate);
						String availableStock = Cls_Generic_Methods.getTextInElement(oPage_Lot.value_AvailableStock);


						m_assert.assertTrue(Double.parseDouble(sAvailableStock) == Double.parseDouble(availableStock),
								"Available Stock Displayed Correctly after Return as :"+availableStock);
						String date = dateAndTimeUI.split("\\|")[0].trim();
						String time = dateAndTimeUI.split("\\|")[1].trim();
						String purchaseReturnDateAndTime = date + "|" + time;

						Double stockAfterCalculated = convertStringToDouble(stockBeforeReturnUI) + convertStringToDouble(inputQty);

						if (flowTextUI.equalsIgnoreCase("In (Return)") &&
								Double.toString(stockAfterCalculated).equals(stockAfterReturnUI) &&
								purchaseReturnDateAndTime.equalsIgnoreCase(cancelDate + "|" + time)) {

							bReturnPurchaseTransactionFoundInLot = true;
						}
						m_assert.assertTrue(bReturnPurchaseTransactionFoundInLot,
								" <b> Sale Return Working Fine, as initial stock was: " + stockBeforeReturnUI +
										" after return stock is: " + stockAfterCalculated + "</b> ");

					}

					Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
					Cls_Generic_Methods.customWait();




				} else {
					m_assert.assertTrue("Sale Transaction Not found or not clicked");
				}

				Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
				Cls_Generic_Methods.customWait(4);

			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}

	}

    public boolean getSaleTransactionFromTransactionList(String sl, String txnInfo, String deliveryDate,
                                                         String orderStatus, String recipientInfo, String amount) {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        boolean bPurchaseTransactionFound = false;


        try {

            for (WebElement eHeader : oPage_Purchase.list_purchaseTransactionHeaderList) {
                saleTransactionHeaderList.add(Cls_Generic_Methods.getTextInElement(eHeader));
            }
            for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

                    String slNoText = Cls_Generic_Methods.getTextInElement(purchaseRow.get(saleTransactionHeaderList.indexOf("Sl.No")));
                    String txnInfoText = Cls_Generic_Methods.getTextInElement(purchaseRow.get(saleTransactionHeaderList.indexOf("Txn.Info"))).replace("\n", " ").replace("\r", " ");
                    String deliveryDateText = Cls_Generic_Methods.getTextInElement(purchaseRow.get(saleTransactionHeaderList.indexOf("Delivery Date")));
                    String orderStatusText = Cls_Generic_Methods.getTextInElement(purchaseRow.get(saleTransactionHeaderList.indexOf("Order Status")));
                    String recipientInfoText = Cls_Generic_Methods.getTextInElement(purchaseRow.get(saleTransactionHeaderList.indexOf("Recipient Info"))).replace("\n", "").replace("\r", "");
                    String amountText = Cls_Generic_Methods.getTextInElement(purchaseRow.get(saleTransactionHeaderList.indexOf("Amount")));

                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    String purchaseNetAmountUI = decimalFormat.format(Double.parseDouble(amountText));

                    String txtInfoList[] = txnInfoText.split(" ");
                    billNumber = txtInfoList[5];

                    if (slNoText.equals(sl) &&
                            txnInfoText.contains(txnInfo) &&
                            deliveryDateText.equalsIgnoreCase(deliveryDate) &&
                            orderStatusText.contains(orderStatus) &&
                            recipientInfoText.equalsIgnoreCase(recipientInfo) &&
                            purchaseNetAmountUI.contains(amount)
                    ) {
                        bPurchaseTransactionFound = true;
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(row),
                                "Purchase Transaction Clicked  In Transaction List");
                        Cls_Generic_Methods.customWait(2);
                        break;
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        return bPurchaseTransactionFound;

    }
	public void validatingHeaderInTable( List<WebElement> headerList, String actualHeaderList[] ,String place){

		try{
			for (WebElement purchaseHeaderList : headerList) {
				int indexOfHeader = headerList.indexOf(purchaseHeaderList);
				String sValueOfHeader = Cls_Generic_Methods.getTextInElement(purchaseHeaderList).replace("\n", " ");

				if (sValueOfHeader.equalsIgnoreCase(actualHeaderList[indexOfHeader])) {
					m_assert.assertTrue( "<b>"+sValueOfHeader + "  </b> Header/Value Present In "+place+" Table List");
					saleTransactionHeaderList.add(sValueOfHeader);
				} else {
					m_assert.assertWarn(sValueOfHeader + " Header/Value Not Present In "+place+" Table List");
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addDataFromSalesOrderForSaleTransaction(){

		concatPatientFullName = CommonActions.getFullPatientName(myPatient).toUpperCase().trim();

		saleOrderPatientDetailsDataList.add(concatPatientFullName);
		saleOrderPatientDetailsDataList.add(myPatient.getsMOBILE_NUMBER());
		saleOrderPatientDetailsDataList.add(patientId);
		saleOrderPatientDetailsDataList.add(orderDateDifferentFormat);
		saleOrderPatientDetailsDataList.add(billNumber);
		saleOrderPatientDetailsDataList.add(EHR_Data.user_PRAkashTest);
		saleOrderPatientDetailsDataList.add("NA");
		saleOrderPatientDetailsDataList.add("NA");
		saleOrderPatientDetailsDataList.add(orderDateDifferentFormat);
		saleOrderPatientDetailsDataList.add(EHR_Data.user_PRAkashTest);
		saleOrderPatientDetailsDataList.add("Delivered");
		saleOrderPatientDetailsDataList.add("Normal");
		saleOrderPatientDetailsListInViewBill.add(concatPatientFullName);
		saleOrderPatientDetailsListInViewBill.add(myPatient.getsMOBILE_NUMBER());
		saleOrderPatientDetailsListInViewBill.add(patientId);
		saleOrderPatientDetailsListInViewBill.add(orderDateDifferentFormat);
		saleOrderPatientDetailsListInViewBill.add(billNumber);
		saleOrderPatientDetailsListInViewBill.add(EHR_Data.user_PRAkashTest);
		saleOrderPatientDetailsListInViewBill.add("NA");
		saleOrderPatientDetailsListInViewBill.add(orderDateDifferentFormat);
		saleOrderPatientDetailsListInViewBill.add(EHR_Data.user_PRAkashTest);
		saleOrderPatientDetailsListInViewBill.add("Delivered");
		saleOrderPatientDetailsListInViewBill.add("Normal");


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
	public static void validatingDataInViewItemRHSSide(List<WebElement> eHeaderList , String [] actualHeaderList,List<String> actualValueList){
		try{

			int indexOfKey = 0,indexOfValue = 0 ;
			for(WebElement eViewItem : eHeaderList){

				if(Cls_Generic_Methods.isElementDisplayed(eViewItem)){
					int indexOfViewItem = eHeaderList.indexOf(eViewItem);
					String sViewItemText = Cls_Generic_Methods.getTextInElement(eViewItem);
					if(indexOfViewItem % 2 == 0) {
						m_assert.assertTrue(sViewItemText.equalsIgnoreCase(actualHeaderList[indexOfKey]),
								"<b> "+sViewItemText+ " </b> Label Present In View Sale Transaction RHS side ");
						indexOfKey ++ ;
					}else{
						m_assert.assertTrue(sViewItemText.equalsIgnoreCase(actualValueList.get(indexOfValue)),
								"<b> "+sViewItemText + "  </b> Value Present In View Sale Transaction RHS against label <b> " + actualHeaderList[indexOfValue]+" </b>");

						indexOfValue ++ ;
					}
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addDataForItemDetailsTableInSaleTransaction(){

		Page_Sale oPage_Sale = new Page_Sale(driver);

		netAmountSaleOrder = String.valueOf(Double.parseDouble(netAmountSaleOrder));

		saleItemDetailsDataList.add("1");
		saleItemDetailsDataList.add(myMedicationName+" "+itemVariantCode);
		saleItemDetailsDataList.add("12345");
		saleItemDetailsDataList.add("NA");
		saleItemDetailsDataList.add("");
		saleItemDetailsDataList.add("N.A");
		saleItemDetailsDataList.add("2.0");
		saleItemDetailsDataList.add(String.valueOf(Double.parseDouble(netAmountSaleOrder)/2));

		billItemDetailsDataList.add("1");
		billItemDetailsDataList.add(itemVariantCode);
		billItemDetailsDataList.add(myMedicationName);
		billItemDetailsDataList.add("12345");
		billItemDetailsDataList.add("NA");
		billItemDetailsDataList.add("N.A");
		billItemDetailsDataList.add("");
		billItemDetailsDataList.add("2.0");
		billItemDetailsDataList.add(String.valueOf(Double.parseDouble(netAmountSaleOrder)/2));

		if(oPage_Sale.list_itemDetailsTableHeaderList.size()>10){

			saleItemDetailsActualHeaderList =  Arrays.asList(saleItemDetailsHeaderListAfterNewRelease);
			billItemDetailsActualHeaderList = Arrays.asList(billItemDetailsHeaderListAfterNewRelease);
			saleItemDetailsDataList.add(netAmountSaleOrder);
			saleItemDetailsDataList.add("0.0");
			billItemDetailsDataList.add(netAmountSaleOrder);
			billItemDetailsDataList.add("0.0");


		}else{
			saleItemDetailsActualHeaderList =  Arrays.asList(saleItemDetailsHeaderList);
			billItemDetailsActualHeaderList =  Arrays.asList(billItemDetailsHeaderList);
		}

		String taxRate = "5";
		Double taxableAmountCalculated  = (Double.parseDouble(netAmountSaleOrder)*100)/(100+Double.parseDouble(taxRate));
		Double taxAmount = Double.parseDouble(netAmountSaleOrder) - taxableAmountCalculated;

		saleItemDetailsDataList.add(String.format("%.2f", taxAmount)+" "+taxRate+"%   Inc.");
		saleItemDetailsDataList.add(netAmountSaleOrder);
		saleItemDetailsDataList.add("");
		billItemDetailsDataList.add(String.format("%.2f",taxableAmountCalculated));
		billItemDetailsDataList.add("5.00");
		billItemDetailsDataList.add(String.format("%.2f", taxAmount));
		billItemDetailsDataList.add(netAmountSaleOrder);
		billItemDetailsDataList.add("");

	}

	public void addItemDetailsLabelValueSaleTransaction(){

		String taxRate = "5";
		Double taxableAmountCalculated  = (Double.parseDouble(netAmountSaleOrder)*100)/(100+Double.parseDouble(taxRate));
		Double taxAmount = Double.parseDouble(netAmountSaleOrder) - taxableAmountCalculated;

		itemDetailsLabelValue.add(String.format("%.2f",taxableAmountCalculated));
		itemDetailsLabelValue.add(String.format("%.2f",taxAmount/2));
		itemDetailsLabelValue.add(String.format("%.2f",taxAmount/2));
		itemDetailsLabelValue.add(String.valueOf(netAmountSaleOrder));
		itemDetailsLabelValue.add("0.0");
		itemDetailsLabelValue.add("0.0");
		itemDetailsLabelValue.add("0.0");
		itemDetailsLabelValue.add(netAmountSaleOrder);
		itemDetailsLabelValue.add(settleAdvanceValue);
		itemDetailsLabelValue.add(String.valueOf(Double.parseDouble(netAmountSaleOrder) - Double.parseDouble(settleAdvanceValue)));

	}

	public void validateReceiveFromAdvanceDetailsSection(WebElement eDetailsButton){
		Page_Sale oPage_Sale = new Page_Sale(driver);

		try{

			m_assert.assertTrue(Cls_Generic_Methods.clickElement(eDetailsButton),
					" Details Button Clicked");
			m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Sale.header_receiveAdvanceTemplateHeader,5),
					" Details Template Opened and Header Displayed as : RECEIVED FROM ADVANCE");
			validatingHeaderInTable(oPage_Sale.list_keysInReceivedAdvanceViewTemplateList,receiveFromAdvanceHeaderList,
					"Receive From Advance In View Sale");
			addDataForReceiveFromAdvance();
			String arr [] = new String[receiveFromAdvanceValueList.size()];
			arr = receiveFromAdvanceValueList.toArray(arr);
			validatingHeaderInTable(oPage_Sale.list_valueInReceivedAdvanceViewTemplateList,arr,"Receive From AdvanceIn View Sale");

			Cls_Generic_Methods.clickElement(oPage_Sale.button_closeByXButtonInReceiveAdvance);
			Cls_Generic_Methods.customWait();


		}catch (Exception e) {
			e.printStackTrace();
		}



	}
	public void validatePaymentReceivedDetailsSection(WebElement eDetailsButton){
		Page_Sale oPage_Sale = new Page_Sale(driver);

		try{

			m_assert.assertTrue(Cls_Generic_Methods.clickElement(eDetailsButton),
					" Details Button Clicked");
			m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Sale.header_paymentReceivedViewTemplateHeader,5),
					" Details Template Opened and Header Displayed as : AMOUNT SETTLED");
			validatingHeaderInTable(oPage_Sale.list_keysInReceivedAdvanceViewTemplateList,amountSettleDetailsHeaderList,
					"Payment Received In View Sale");
			for (WebElement purchaseHeaderList : oPage_Sale.list_valueInReceivedAdvanceViewTemplateList) {

				int indexOfHeader = oPage_Sale.list_valueInReceivedAdvanceViewTemplateList.indexOf(purchaseHeaderList);
				String sValueOfHeader = Cls_Generic_Methods.getTextInElement(purchaseHeaderList);
				if(indexOfHeader == 5){
					WebElement printButton = purchaseHeaderList.findElement(By.xpath(".//button"));
					validatePrintButtonFunctionality(printButton," Amount Settle Template Print Button");

				}else {
					if (sValueOfHeader.equalsIgnoreCase(paymentReceivedValueList.get(indexOfHeader))) {
						m_assert.assertTrue(sValueOfHeader + " Value Present In  Table List");
						saleTransactionHeaderList.add(sValueOfHeader);
					} else {
						m_assert.assertWarn(sValueOfHeader + " Value Not Present In Table List");
					}
				}
			}

			Cls_Generic_Methods.clickElement(oPage_Sale.button_closeByXButtonInAmountSettled);
			Cls_Generic_Methods.customWait();

		}catch (Exception e) {
			e.printStackTrace();
		}



	}

	public void addDataForReceiveFromAdvance(){

		receiveFromAdvanceValueList.add("1");
		receiveFromAdvanceValueList.add(time.substring(0,5)+" "+time.substring(5,7)+", "+orderDateDifferentFormat);
		receiveFromAdvanceValueList.add(concatPatientFullName);
		receiveFromAdvanceValueList.add(settleAdvanceValue+".00/-");
		paymentReceivedValueList.add("1");
		paymentReceivedValueList.add(paymentTime.substring(0,5)+" "+paymentTime.substring(5,7)+", "+orderDateDifferentFormat);
		paymentReceivedValueList.add(concatPatientFullName);
		paymentReceivedValueList.add(EHR_Data.user_PRAkashTest);
		paymentReceivedValueList.add("Cash");
		paymentReceivedValueList.add(amountAfterAdvance);
		paymentReceivedValueList.add("0.00");
		paymentReceivedValueList.add("0.00");
		paymentReceivedValueList.add("0.00");
		paymentReceivedValueList.add(amountAfterAdvance);


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
	public void validatingValueInTable( List<WebElement> headerList, String actualHeaderList[] ,String place){

		try{
			for (WebElement purchaseHeaderList : headerList) {
				int indexOfHeader = headerList.indexOf(purchaseHeaderList);
				String sValueOfHeader = Cls_Generic_Methods.getTextInElement(purchaseHeaderList).replace("\n", " ");

				if (Double.parseDouble(sValueOfHeader) == Double.parseDouble(actualHeaderList[indexOfHeader])) {
					m_assert.assertTrue( "<b>"+sValueOfHeader + "  </b> Header/Value Present In "+place+" Table List");
				} else {
					m_assert.assertWarn(sValueOfHeader + " Header/Value Not Present In "+place+" Table List");
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String createAdvance(String Multiple){

		Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Sale oPage_Sale = new Page_Sale(driver);

		String totalAmountOfAdvance = "";
		Double totalAmountOfAdvanceCalculated = 0.0 ;

		try {
			m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_billButton),
					"Bill Button Displayed and Clicked");
			Cls_Generic_Methods.customWait( 4);
			m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_advanceReceiptButton),
					"Advance Button Displayed and Clicked ");

			m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_advanceReceiptTemplate, 5),
					" Advance Receipt Template Opened and Header Displayed as : Advance Receipt");

			advanceDate = Cls_Generic_Methods.getElementAttribute(oPage_Sale.input_advanceDate,"value");
			advanceTime = Cls_Generic_Methods.getElementAttribute(oPage_Sale.input_advanceTime,"value");

			m_assert.assertTrue(" Advance Date Field Displayed and Default value as :"+advanceDate);
			m_assert.assertTrue(" Advance Time Field Displayed and Default value as :"+advanceTime);

			m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientQueue.input_reasonAdvance,advanceReason),
					" Advance Reason Text box Displayed and Value Entered as : "+advanceReason);


			m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_PatientQueue.select_mop, modeOfPayment[0]),
					" Mode of Payment Selected as : "+modeOfPayment[0] + " at index 1");
			Cls_Generic_Methods.clearValuesInElement(oPage_PatientQueue.input_amountAdvance);
			m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientQueue.input_amountAdvance, cashPay),
					" Amount Entered as : "+cashPay+" at index 1");
			totalAmountOfAdvanceCalculated = totalAmountOfAdvanceCalculated + Double.parseDouble(cashPay);


			if(Multiple.equalsIgnoreCase("Multiple")){

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.button_addPaymentButton),
						" Add Payment Method Button Clicked In Advance");
				Cls_Generic_Methods.customWait(3);
				m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_PatientQueue.select_mopSecond, modeOfPayment[1]),
						" Mode of Payment Selected as : "+modeOfPayment[1] + " at index 1");
				Cls_Generic_Methods.clearValuesInElement(oPage_PatientQueue.input_amountAdvanceSecond);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientQueue.input_amountAdvanceSecond, cardPay),
						" Amount Entered as : "+cardPay+" at index 1");

				totalAmountOfAdvanceCalculated = totalAmountOfAdvanceCalculated + Double.parseDouble(cardPay);


			}

			totalAmountOfAdvance = Cls_Generic_Methods.getTextInElement(oPage_Sale.label_totalAmountReceivedLabelInAdvanceTemplate).replace("₹","");
			m_assert.assertTrue(totalAmountOfAdvanceCalculated == Double.parseDouble(totalAmountOfAdvance),
					"Total Amount Received:  Label Displayed and Its values as :  "+totalAmountOfAdvance);

			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_saveAdvance,4);
			m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_saveAdvance),
					" Save Advance Button Clicked");
			Cls_Generic_Methods.customWait( 5);
			Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
			Cls_Generic_Methods.customWait();

		}catch (Exception e) {
			e.printStackTrace();
		}

		return totalAmountOfAdvance;

	}

	public void addDataForViewSecondCreatedAdvanceDetails(){

		secondAdvanceId = String.valueOf(Integer.parseInt(advanceIdInSaleOrder.substring(8,13))+1);
		String advancePrefix = advanceIdInSaleOrder.substring(0,8)+secondAdvanceId;
		secondAdvanceReceiptTemplateValueList.add(advancePrefix);
		secondAdvanceReceiptTemplateValueList.add(advanceDate);
		secondAdvanceReceiptTemplateValueList.add(advanceReason);
		secondAdvanceReceiptTemplateValueList.add("₹ "+ advanceValueList.get(1) +" Details");

	}

	public void createRefund(String advanceId ,String amount){

		Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);
		Page_Sale oPage_Sale = new Page_Sale(driver);

		try {

			refundDate = Cls_Generic_Methods.getElementAttribute(oPage_Sale.input_refundDate,"value");
			refundTime = Cls_Generic_Methods.getElementAttribute(oPage_Sale.input_refundTime,"value");

			m_assert.assertTrue(!refundDate.isEmpty()," Refund Date and Time Field Displayed");

			String advanceIdInRefund = Cls_Generic_Methods.getTextInElement(oPage_Sale.label_advanceIdLabelInRefund).replace("\n", "");
			m_assert.assertTrue(advanceIdInRefund.contains("Adv. Receipt No.: "+advanceId),
					" Adv. Receipt No. Label Displayed and Its Value as : "+advanceId);

			remAmountInRefund = Cls_Generic_Methods.getTextInElement(oPage_Sale.label_remainingAmountLabelInRefund).replace("\n", "");
			m_assert.assertTrue(remAmountInRefund.contains(amount),
					" Remaining Amount Label Displayed and Its Value as : "+remAmountInRefund);


			m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_refundReason, "Refund Check"),
					" Refund Reason Text Box Displayed and value entered as : Refund Check ");

			m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Sale.select_modeOfPaymentInRefund, modeOfPayment[0]),
					" Mode of Payment Selected as "+modeOfPayment[0]);

			Cls_Generic_Methods.clearValuesInElement(oPage_Sale.input_refundAmount);
			m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_refundAmount,amount),
					" Refund Amount Entered as :"+amount);

			String remAmountRefArr[] = remAmountInRefund.split(" ");
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_saveRefund,4);
			m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_saveRefund),
					" Save Button Clicked In Refund");
			Cls_Generic_Methods.customWait(5);
			m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_billRefundedLabel).equalsIgnoreCase("Bill Refunded"),
					" Bill Refunded Text Displayed Successfully");
			m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_paymentRefundedLabel).equalsIgnoreCase("Payment Refunded "+remAmountRefArr[2]),
					"Payment Refunded Label Displayed and Value as : "+remAmountRefArr[2]);
			m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Sale.text_refundRemarkLabel).equalsIgnoreCase("Refund Remarks: Refund Check"),
					" Refund Remarks: Text Displayed correctly and Its value as : Refund Remarks: Refund Check");


		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addDataForViewCreatedAdvanceDetails(){

		String remainingAmount = String.format("%.2f",Double.parseDouble(advanceValueList.get(0)) - Double.parseDouble(settleAdvanceValue));
		advanceReceiptTemplateValueList.add(advanceIdInSaleOrder);
		advanceReceiptTemplateValueList.add(advanceDate);
		advanceReceiptTemplateValueList.add(advanceReason);
		advanceReceiptTemplateValueList.add("₹ "+ advanceValueList.get(0) +" Details");
		advanceReceiptTemplateValueList.add("₹ "+remainingAmount);

	}
	public void addDataForAdvanceHistory() throws Exception {

		advanceHistoryTemplateValueList.add(orderNumber);
		advanceHistoryTemplateValueList.add(advanceIdInSaleOrder+"-SE-01");
		advanceHistoryTemplateValueList.add("Optical");
		advanceHistoryTemplateValueList.add("Adjusted");
		advanceHistoryTemplateValueList.add(EHR_Data.user_PRAkashTest);
		advanceHistoryTemplateValueList.add(time.substring(0,5)+" "+time.substring(5,7)+", "+advanceDate);
		advanceHistoryTemplateValueList.add("100.00");

	}

	public  boolean createSaleOrderForPolicyCheck(String itemFrom){

		boolean createSalesOrder = false ;
		Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
		Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
		Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);
		String sPatientSalutation = "Mr.";


		try{

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
			m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_searchMedicineNameInDescription, myMedicationName), "Entering the medication name as" + myMedicationName + " in description textbox for sales order");
			Cls_Generic_Methods.customWait(4);
			oPage_SalesOrder.input_searchMedicineNameInDescription.sendKeys(Keys.ENTER);
			Cls_Generic_Methods.customWait(3);
			boolean myMedicationFound = false;

			for (WebElement eMedication : oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult) {
				if(Cls_Generic_Methods.isElementDisplayed(eMedication)){
					if (Cls_Generic_Methods.getTextInElement(eMedication).contains(myMedicationName)) {
						Cls_Generic_Methods.clickElement(eMedication);
						myMedicationFound = true;
						Cls_Generic_Methods.customWait(4);
						break;
					}
				}
			}
			m_assert.assertTrue(myMedicationFound, "Required medication " + myMedicationName + " found for Sales Order");

			m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_quantityOfMedicine, inputQty),
					"Entered Quantity " + inputQty + " for Sales Order");
			Cls_Generic_Methods.customWait(5);

			netAmountSaleOrder = Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_totalAmount,"value");

			m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SalesOrder.label_completedDeliveryRadioButtonLabel),
					"Completed Radio Button Clicked FOr Delivery Type ");
			Cls_Generic_Methods.customWait(2);

			m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_PatientQueue.select_modeOfPaymentInSalesOrder, modeOfPayment[0]),
					"Required mode of payment " + modeOfPayment[0]+ " selected");

			Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientQueue.input_totalAmountInPayment,netAmountSaleOrder);

			orderDate = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_TxnDate,"value");
			orderTime = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderTime,"value");

			DateFormat dateFormat = new SimpleDateFormat("hh:mmaa");
			time = dateFormat.format(new Date());
			time = time.replace("am", "AM").replace("pm", "PM");

			orderTime = orderTime.replaceAll("\\s+", "");
			//orderTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", orderTime);
			orderTime = orderTime.replace("am", "AM").replace("pm", "PM");
			orderDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd-MM-yyyy", orderDate);

			m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_SaveChanges),
					"Save Changes Button CLicked");
			Cls_Generic_Methods.customWait(5);
			if(orderTime.length() == 6){
				orderTime = "0"+orderTime;
			}
			salesOrderDateAndTime =orderDate+"  |  "+orderTime;


			if(Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.button_closeModalOfSalesOrder)) {
				Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_closeModalOfSalesOrder);
				Cls_Generic_Methods.customWait();
				createSalesOrder = true ;
			}

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


		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}

		return createSalesOrder ;
	}

}
