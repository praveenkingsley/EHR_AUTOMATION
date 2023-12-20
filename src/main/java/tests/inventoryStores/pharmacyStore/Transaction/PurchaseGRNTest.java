package tests.inventoryStores.pharmacyStore.Transaction;

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
import pages.commonElements.InventoryCommonActions;
import pages.commonElements.Page_CommonElements;

import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_VendorMaster;
import pages.store.PharmacyStore.Transaction.Page_Purchase;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static pages.commonElements.CommonActions.*;
import static pages.commonElements.CommonActions.getRandomString;

public class PurchaseGRNTest extends TestBase {


	String pharmacyStoreName = "Pharmacy automation- Pharmacy";
	public static final String itemDescription = "TransactionPurchase_" + getRandomString(4);
	String vendorName = "Supplier ABC";
	String billType = "Bill";
	String billNumber = "BILL_" + getRandomNumber();
	public static final String subStore = "Default";
	String purchaseTransactionTime = "";
	String purchaseTransactionDate = "";
	public static String unitCostWOTax = "100";
	public static String packageQuantity = "2";
	public static String sQuantity = "";
	public static String sellingPrice = "120";
	public static String taxPercentage = "5%";
	public static String discountAmount = "10";
	String otherChargesAmount = "10.0";
	String otherChargesName = "Item_other";
	String transactionNotes = "Transaction_notes" + getRandomString(4);
	String returnNotes = "Return_notes" + getRandomString(3);
	String totalNetAmountAfterOtherChargesAddition = "";
	String returnQuantityValue = "1";
	String otherCharges = "10";
	String storeGSTno;
	String vendor_address;
	String vendorGSTno;
	String vendorFullNameAndAddress;
	boolean gstTypeIGST;
	public static String hsnCode, expiryDate;
	String batchNo = "";
	List<String> grnTransactionDetailsList = new ArrayList<>();
	String itemDescriptionCancel = "TransactionPurchaseCancel_" + getRandomString(4);


	@Test(enabled = true, description = "Validating Create Purchase In Pharmacy Store")
	public void validateCreatePurchaseFunctionality() {

		Page_Purchase oPage_Purchase = new Page_Purchase(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

		boolean vendorFound = false;
		boolean bPurchaseTransactionFound = false;

		try {
			CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
			getGST_valueFromSetting();
			Cls_Generic_Methods.driverRefresh();
			Cls_Generic_Methods.customWait(5);

			try {

				// Open Pharmacy Store

				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				// Dynamic Wait
				Cls_Generic_Methods.customWait();
				CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");

				// Creating New Item In Item Master

				boolean addItemStatus = CommonActions.addItemInInventory(itemDescription);

				if (addItemStatus) {

					// Creating Purchase Transaction for Created Item

					CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
					Cls_Generic_Methods.waitForPageLoad(driver, 4);
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_purchaseNew),
							"New Button Displayed and Clicked in Purchase Transaction");
					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_Vendor_search, 10);
					Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_Vendor_search, (vendorName + " " + vendor_address));
					Cls_Generic_Methods.customWait(3);
					for (WebElement eVendor : oPage_Purchase.list_select_vendor) {
						Cls_Generic_Methods.clickElementByJS(driver, eVendor);
						vendorFound = true;
						break;
					}

					vendorFullNameAndAddress = vendorName + " - " + vendor_address;
					m_assert.assertTrue(vendorFound, "Vendor present in dropdown: <b> " + vendorFullNameAndAddress + "</b>");

					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);

					// Adding New Item Stock to Inventory

					boolean bItemStockAdded = addNewItemStockToPurchase(itemDescription);

					if (bItemStockAdded) {

						// Verifying Added Item Stock Calculation

						boolean bItemStockCalculation = getAddedItemStockCalculation();

						if (bItemStockCalculation) {

							Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);

							// Filling Other Mandatory Fields Like Bill date , Notes Etc

							m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes),
									" Transaction Notes Entered as :<b> " + transactionNotes + "</b>");
							Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
							m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType),
									"Bill Type Selected:<b> " + billType + " </b>");
							m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber),
									"Bill Number: <b> " + billNumber + " </b>");
							Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
							Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
							m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
									"Date of bill selected:<b> " + oPage_Purchase.input_todayBillDate.getText() + " </b>");

							purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
							purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
							m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
							//purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
							purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
							if (purchaseTransactionTime.length() == 6) {
								purchaseTransactionTime = "0" + purchaseTransactionTime;
							}
							m_assert.assertTrue("Requisition order time:<b> " + purchaseTransactionTime + "</b>");

							purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
							purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);

							m_assert.assertTrue("Purchase Transaction date:<b> " + purchaseTransactionDate + "|" + purchaseTransactionTime + "</b>");
							String totalNetAmountBeforeOtherCharges = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalNetAmount, "value");

							// Verifying Net Amount Calculation After Adding other Charges

							m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_otherCharges, otherChargesName),
									"Other Charges: <b> " + otherChargesName + " </b>");
							Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_otherChargesAmount, otherCharges);

							String netAmountCalculatedAfterOtherCharges = InventoryCommonActions.getOtherChargesNetAmount(otherChargesAmount, totalNetAmountBeforeOtherCharges, "plus");
							totalNetAmountAfterOtherChargesAddition = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalNetAmount, "value");

							m_assert.assertTrue(netAmountCalculatedAfterOtherCharges.equalsIgnoreCase(totalNetAmountAfterOtherChargesAddition),
									"Other Charges addition working correctly and adding successfully in net amount");

							m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
									"Save Changes Button Clicked In A Lot Inventory Template");
							Cls_Generic_Methods.customWait(8);

							//Verifying Created Purchase Transaction In List

							bPurchaseTransactionFound = CommonActions.getPurchaseTransactionFromTransactionList(purchaseTransactionDate + "|" + purchaseTransactionTime,
									totalNetAmountAfterOtherChargesAddition, vendorFullNameAndAddress, transactionNotes, "Open");

							m_assert.assertTrue(bPurchaseTransactionFound, "Purchase Transaction Found, Created Successfully for date " + purchaseTransactionDate + " and Time " + purchaseTransactionTime);
							DecimalFormat decimalFormat = new DecimalFormat("0.00");

							sellingPrice = decimalFormat.format((CommonActions.convertStringToDouble(sellingPrice)));
							sQuantity = String.valueOf(CommonActions.convertStringToDouble(packageQuantity));
							unitCostWOTax = String.valueOf(CommonActions.convertStringToDouble(unitCostWOTax));
							discountAmount = String.valueOf(CommonActions.convertStringToDouble(discountAmount));
							taxPercentage = taxPercentage.replaceAll(" ", "").replace("tax", "").replace("%", "");
							taxPercentage = String.valueOf(CommonActions.convertStringToDouble(taxPercentage));
							expiryDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", expiryDate);


							totalNetAmountBeforeOtherCharges = String.valueOf(CommonActions.convertStringToDouble(totalNetAmountBeforeOtherCharges));


							grnTransactionDetailsList.add("1");
							grnTransactionDetailsList.add(hsnCode);
							grnTransactionDetailsList.add(itemDescription + "-");
							grnTransactionDetailsList.add(batchNo);
							grnTransactionDetailsList.add(expiryDate);
							grnTransactionDetailsList.add(sellingPrice);
							grnTransactionDetailsList.add(sQuantity);
							grnTransactionDetailsList.add("0.0");
							grnTransactionDetailsList.add(unitCostWOTax);
							grnTransactionDetailsList.add(discountAmount);
							grnTransactionDetailsList.add(taxPercentage);
							grnTransactionDetailsList.add(totalNetAmountBeforeOtherCharges);
							grnTransactionDetailsList.add("");

							Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
							Cls_Generic_Methods.customWait();

						} else {
							m_assert.assertInfo(bItemStockCalculation, " Calculation Error In Stock Inventory");
						}
					} else {
						m_assert.assertInfo(bItemStockAdded, " Stock is not Added in Stock Inventory");
					}

				} else {
					m_assert.assertInfo(addItemStatus, "Item is not Added in Item Master Description as: <b>" + itemDescription + "</b>");
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

	@Test(enabled = true, description = "Validating View Purchase GRN Purchase In Pharmacy Store")
	public void validateViewPurchaseFunctionality() {

		Page_Purchase oPage_Purchase = new Page_Purchase(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		String grnTableHeaderList[] = {"GRN Info.", "Vendor", "Note", "Amount", "Status"};
		String grnTransactionDetailsTableHeaderList[] = {"S No.", "HSN", "Description", "Batch No.", "Expiry", "MRP", "GRN Qty", "Free Qty", "Rate", "Discount", "Tax%", "Total", "Remark"};

		String viewPurchaseDate, viewPurchaseTime;

		try {
			CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
			getGST_valueFromSetting();
			Cls_Generic_Methods.driverRefresh();
			Cls_Generic_Methods.customWait(5);

			try {

				// Open Pharmacy Store

				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				// Dynamic Wait
				Cls_Generic_Methods.customWait();

				CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
				Cls_Generic_Methods.waitForPageLoad(driver, 4);

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_refreshPurchaseTransaction),
						"Refresh Button Displayed In Purchase GRN Table");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_purchaseNew),
						"New Button Displayed In Purchase GRN Table");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_todayFilterButton),
						" Today Filter Button Displayed In Purchase GRN Table");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_vendorDropdownButtonList),
						" Vendor Filter Button Displayed In Purchase GRN Table");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.input_vendorSearchBox),
						" Vendor Name Search Text Box Displayed In Purchase GRN Table");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_reportButtonInPurchaseGRN),
						" Report Button Displayed In Purchase GRN Table");

				for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
					int indexOfHeader = oPage_Purchase.list_purchaseTransactionHeaderList.indexOf(purchaseHeaderList);
					String sValueOfHeader = Cls_Generic_Methods.getTextInElement(purchaseHeaderList);
					if (sValueOfHeader.equalsIgnoreCase(grnTableHeaderList[indexOfHeader])) {
						m_assert.assertTrue(sValueOfHeader + " Header Present In GRN Data Table List");
					} else {
						m_assert.assertWarn(sValueOfHeader + " Header Not Present In GRN Data Table List");
					}
				}

				boolean bPurchaseTransactionFound = CommonActions.getPurchaseTransactionFromTransactionList(purchaseTransactionDate + "|" + purchaseTransactionTime,
						totalNetAmountAfterOtherChargesAddition, vendorFullNameAndAddress, transactionNotes, "Open");

				if (bPurchaseTransactionFound) {

					String transactionNotesInViewGRN = Cls_Generic_Methods.getTextInElement(oPage_Purchase.header_viewGrnTransactionNotesHeader);
					m_assert.assertTrue(transactionNotesInViewGRN.equalsIgnoreCase(transactionNotes),
							" Transaction Details Title Displayed In View GRN as : <b> " + transactionNotesInViewGRN + " </b> ");

					String vendorNameInViewGRN = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_viewGrnVendorName);
					m_assert.assertTrue(vendorNameInViewGRN.contains(vendorFullNameAndAddress),
							" Vendor Label and its Value Displayed In View Purchase GRN as : <b> " + vendorNameInViewGRN + " </b>");

					String transactionIDInViewGRN = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_transactionID);
					m_assert.assertTrue(!transactionIDInViewGRN.isEmpty(),
							" Transaction ID Label and its Value Displayed In View Purchase GRN as : <b> " + transactionIDInViewGRN + " </b>");

					String billNumberInViewGRN = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_viewGrnBillNumber);
					viewPurchaseDate = CommonActions.getRequiredFormattedDateTime("yyyy-MM-dd", "dd-MM-yyyy", purchaseTransactionDate);

					m_assert.assertTrue(billNumberInViewGRN.equalsIgnoreCase(billNumber + "  | " + viewPurchaseDate),
							" Bill Number Label and its Value Displayed In View Purchase GRN as : <b> " + billNumberInViewGRN + " </b>");

					String statusInViewGRN = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_viewGrnStatus);
					m_assert.assertTrue(statusInViewGRN.equalsIgnoreCase("Open"),
							" Status Label and its Value Displayed In View Purchase GRN as : <b> " + statusInViewGRN + " </b>");

					String gstNumberInViewGRN = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_viewGrnGSTNumber);
					m_assert.assertTrue(gstNumberInViewGRN.equalsIgnoreCase(vendorGSTno),
							" GST Label and its Value Displayed In View Purchase GRN as : <b> " + gstNumberInViewGRN + " </b>");

					String grnCreatedByInViewGRN = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_viewGrnCreatedBy);

					viewPurchaseTime = purchaseTransactionTime.substring(0, 5) + " " + purchaseTransactionTime.substring(5, 7);


					m_assert.assertTrue(grnCreatedByInViewGRN.equalsIgnoreCase(EHR_Data.user_PRAkashTest + " | " + viewPurchaseDate + " | " + viewPurchaseTime),
							" GRN Created By Label and its Value Displayed In View Purchase GRN as : <b> " + grnCreatedByInViewGRN + " </b>");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_printA4SizeButton),
							"Print A4 Button Displayed In View GRN");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_printA5SizeButton),
							"Print A5 Button Displayed In View GRN");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_approveTransaction),
							" Approve Button Displayed In View GRN");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_editPurchaseTransaction),
							" Edit Button Displayed In View GRN");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_cancelPurchaseTransaction),
							" Cancel Button Displayed In View GRN");


					for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
						int indexOfHeader = oPage_Purchase.list_purchaseTransactionHeaderList.indexOf(purchaseHeaderList);
						String sValueOfHeader = Cls_Generic_Methods.getTextInElement(purchaseHeaderList);
						if (sValueOfHeader.equalsIgnoreCase(grnTableHeaderList[indexOfHeader])) {
							m_assert.assertTrue(sValueOfHeader + " Header Present In GRN Data Table List");
						} else {
							m_assert.assertWarn(sValueOfHeader + " Header Not Present In GRN Data Table List");
						}
					}

					for (WebElement transactionHeaderList : oPage_Purchase.list_keysInTransactionDetailsTable) {
						int indexOfHeader = oPage_Purchase.list_keysInTransactionDetailsTable.indexOf(transactionHeaderList);
						String sValueOfHeader = Cls_Generic_Methods.getTextInElement(transactionHeaderList);
						if (sValueOfHeader.equalsIgnoreCase(grnTransactionDetailsTableHeaderList[indexOfHeader])) {
							m_assert.assertTrue(sValueOfHeader + " Header Present In GRN Transaction Details Table List");
						} else {
							m_assert.assertWarn(sValueOfHeader + " Header Not Present In GRN Transaction Details Table List");
						}
					}

				}

				for (WebElement transactionValueList : oPage_Purchase.list_valuesInTransactionDetailsTable) {

					int indexOfHeader = oPage_Purchase.list_valuesInTransactionDetailsTable.indexOf(transactionValueList);
					String sValueOfTransactionDetails = Cls_Generic_Methods.getTextInElement(transactionValueList);

					if (sValueOfTransactionDetails.equalsIgnoreCase(grnTransactionDetailsList.get(indexOfHeader))) {
						m_assert.assertTrue(sValueOfTransactionDetails + " Value Present In GRN Transaction Details Table List");
					} else {
						m_assert.assertWarn(sValueOfTransactionDetails + " Value Not Present In GRN Transaction Details Table List");
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

	@Test(enabled = true, description = "Validating Edit Purchase In Pharmacy Store")
	public void validateEditPurchaseFunctionality() {

		Page_Purchase oPage_Purchase = new Page_Purchase(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

		boolean bEditPurchaseTransactionFound = false;

		try {
			CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

			try {

				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();
				CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");

				boolean bClickOnTransactionInList = CommonActions.getPurchaseTransactionFromTransactionList(purchaseTransactionDate + "|" + purchaseTransactionTime,
						totalNetAmountAfterOtherChargesAddition, vendorFullNameAndAddress, transactionNotes, "Open");

				if (bClickOnTransactionInList) {

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_editPurchaseTransaction),
							"Edit Button Clicked For Selected Transaction In Purchase Transaction");
					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);

					purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
					purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
					m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
					//purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
					purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
					if (purchaseTransactionTime.length() == 6) {
						purchaseTransactionTime = "0" + purchaseTransactionTime;
					}
					m_assert.assertTrue("Requisition order time:<b> " + purchaseTransactionTime + "</b>");

					purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
					purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);

					m_assert.assertTrue("Purchase Transaction date:<b> " + purchaseTransactionDate + "</b>");

					boolean addNewStockStatus = addNewItemStockToPurchase(itemDescription);

					if (addNewStockStatus) {

						boolean bEditItemCalculation = getAddedItemStockCalculation();
						if (bEditItemCalculation) {

							String totalNetAmount = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalNetAmount, "value");
							m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
									"Save Changes button Clicked In A Lot Inventory Template ");
							Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 4);

							Cls_Generic_Methods.driverRefresh();
							Cls_Generic_Methods.waitForPageLoad(driver, 10);
							m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
									"Store pop up closed");
							Cls_Generic_Methods.customWait();
							CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");

							bEditPurchaseTransactionFound = CommonActions.getPurchaseTransactionFromTransactionList(purchaseTransactionDate + "|" + purchaseTransactionTime,
									totalNetAmount, vendorFullNameAndAddress, transactionNotes, "Open");

							m_assert.assertTrue(bEditPurchaseTransactionFound, "Purchase Transaction Found, Created Successfully for date " + purchaseTransactionDate + "and " + purchaseTransactionTime);

							totalNetAmountAfterOtherChargesAddition = totalNetAmount;
							Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
							Cls_Generic_Methods.customWait();

						} else {
							m_assert.assertTrue(bEditItemCalculation, "Edit Stock Calculation not working properly");

						}

					} else {
						m_assert.assertTrue(addNewStockStatus, "Stock not added successfully");
					}

				} else {
					m_assert.assertTrue("Transaction Not found or not clicked");
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

	@Test(enabled = true, description = "Validating Approve and Complete Payment Purchase In Pharmacy Store")
	public void validateApproveAndCompletePaymentPurchaseFunctionality() {

		Page_Purchase oPage_Purchase = new Page_Purchase(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);

		boolean bApprovePurchaseTransactionFound = false;
		boolean bCompletePaymentPurchaseTransactionFound = false;
		boolean bPurchaseTransactionFoundInLot = false;

		try {
			CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

			try {

				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();
				CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");

				boolean bClickOnTransactionInList = CommonActions.getPurchaseTransactionFromTransactionList(purchaseTransactionDate + "|" + purchaseTransactionTime,
						totalNetAmountAfterOtherChargesAddition, vendorFullNameAndAddress, transactionNotes, "Open");

				if (bClickOnTransactionInList) {

					// Verifying Approve Functionality

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction),
							"Approve Button Clicked For Selected Transaction In Purchase Transaction");
					Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.button_yesButtonList, 4);
					for (WebElement eHeader : oPage_Purchase.header_areYouSureHeaderList) {
						if (Cls_Generic_Methods.isElementDisplayed(eHeader)) {
							m_assert.assertTrue("Approved Confirmation Dialogue Opened ,and Header displayed as : "
									+ Cls_Generic_Methods.getTextInElement(eHeader));
							break;
						}
					}
					for (WebElement eYesButton : oPage_Purchase.button_yesButtonList) {
						if (Cls_Generic_Methods.isElementDisplayed(eYesButton)) {
							m_assert.assertTrue(Cls_Generic_Methods.clickElement(eYesButton),
									"Yes Button Clicked In Approved Confirmation");
							Cls_Generic_Methods.customWait();
							break;
						}
					}
					/*if (Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_completePaymentPurchaseTransaction)) {
						m_assert.assertTrue(" Button Changed From Approve TO Complete Payment In Transaction Description");
					}

*/
					Cls_Generic_Methods.driverRefresh();
					Cls_Generic_Methods.waitForPageLoad(driver, 10);
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
							"Store pop up closed");
					Cls_Generic_Methods.customWait();
					CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");

					bApprovePurchaseTransactionFound = CommonActions.getPurchaseTransactionFromTransactionList(purchaseTransactionDate + "|" + purchaseTransactionTime,
							totalNetAmountAfterOtherChargesAddition, vendorFullNameAndAddress, transactionNotes, "Approved");

					if (bApprovePurchaseTransactionFound) {

						m_assert.assertTrue(" Transaction Status Changed To Approved Successfully for date " + purchaseTransactionDate + " and time " + purchaseTransactionTime);

						// Verifying Approved Purchase Transaction In Lot Section of Item Master

						CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");


						Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, itemDescription);
						Cls_Generic_Methods.customWait(3);
						oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
						Cls_Generic_Methods.customWait(3);
						int indexOfItemInLot = 0;
						for (WebElement items : oPage_ItemMaster.list_itemListInStoreInventory) {
							List<WebElement> itemNameRow = items.findElements(By.xpath("./child::*"));
							String itemDescriptionText = Cls_Generic_Methods.getTextInElement(itemNameRow.get((0)));

							if (itemDescriptionText.contains(itemDescription)) {
								bPurchaseTransactionFoundInLot = true;
							}

							m_assert.assertTrue(bPurchaseTransactionFoundInLot, "Approved Purchase Transaction Found In Lot at index " + indexOfItemInLot);
							indexOfItemInLot++;

						}


						/*// Verifying Complete Payment Functionality For Purchase Transaction
						CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");

						bApprovePurchaseTransactionFound = CommonActions.getPurchaseTransactionFromTransactionList(purchaseTransactionDate + "|" + purchaseTransactionTime,
								totalNetAmountAfterOtherChargesAddition, vendorFullNameAndAddress, transactionNotes, "Approved");

						Cls_Generic_Methods.customWait();

						if (bApprovePurchaseTransactionFound) {

							m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_completePaymentPurchaseTransaction),
									"Completed Payment Button Clicked For Selected Transaction In Purchase Transaction");
							Cls_Generic_Methods.customWait();
							m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.header_completePaymentTemplate),
									" Confirm Complete Payment Transaction Template Opened");
							m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_completePaymentInCompletePaymentTemplate),
									" Completed Payment Button Clicked In Confirm Purchase Transaction Template");
							Cls_Generic_Methods.customWait();
							m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_returnPurchaseTransaction),
									"Return Button Displayed In View Purchase Transaction");
							Cls_Generic_Methods.clickElement(oPage_Purchase.button_refreshPurchaseTransaction);
							Cls_Generic_Methods.customWait(2);

							bCompletePaymentPurchaseTransactionFound = CommonActions.getPurchaseTransactionFromTransactionList(purchaseTransactionDate + "|" + purchaseTransactionTime,
									totalNetAmountAfterOtherChargesAddition, vendorFullNameAndAddress, transactionNotes, "Completed");

							m_assert.assertTrue(bCompletePaymentPurchaseTransactionFound, " Transaction Status Changed To Completed Successfully for date " + purchaseTransactionDate + " and time " + purchaseTransactionTime);

							Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
							Cls_Generic_Methods.customWait();

						} else {
							m_assert.assertTrue("Approved Transaction Not Found");
						}*/

						Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
						Cls_Generic_Methods.customWait();

					} else {
						m_assert.assertTrue("Transaction Not Approved");
					}
				} else {
					m_assert.assertTrue("Transaction Not found or not clicked");
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

	@Test(enabled = true, description = "Validating Return Purchase In Pharmacy Store")
	public void validateReturnPaymentPurchaseFunctionality() {

		Page_Purchase oPage_Purchase = new Page_Purchase(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);


		boolean bReturnPurchaseTransactionFoundInLot = false;
		boolean bPurchaseTransactionFoundInLot = false;

		List<String> unitCostWithoutTaxList = new ArrayList<String>();
		List<String> netUnitCostWithoutTaxList = new ArrayList<String>();
		List<String> taxRateList = new ArrayList<String>();
		List<String> paidQuantityList = new ArrayList<String>();
		List<String> freeQuantityList = new ArrayList<String>();
		List<String> discountList = new ArrayList<String>();
		List<String> purchaseTransactionHeaderList = new ArrayList<String>();


		try {
			CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
			try {

				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();
				CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");

				boolean bClickOnTransactionInList = CommonActions.getPurchaseTransactionFromTransactionList(purchaseTransactionDate + "|" + purchaseTransactionTime,
						totalNetAmountAfterOtherChargesAddition, vendorFullNameAndAddress, transactionNotes, "Approved");

				if (bClickOnTransactionInList) {

					// Verifying Approve Functionality

					for (WebElement discount : oPage_Purchase.list_discountListInTransactionDetailsTable) {
						if (Cls_Generic_Methods.isElementDisplayed(discount)) {
							discountList.add(Cls_Generic_Methods.getTextInElement(discount));
						}
					}
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_returnPurchaseTransaction),
							"Return Button Clicked For Selected Transaction In Purchase Transaction");

					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_returnPurchaseTransactionTemplate, 2);
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.header_returnPurchaseTransactionTemplate),
							" Return Purchase Template Opened");
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_returnNotesInReturnPurchaseTransactionTemplate, returnNotes),
							" Return Notes Present In Return Purchase Template ");

					// Validating Net Unit(WO) Tax In Return Purchase

					for (WebElement unitCostWOTax : oPage_Purchase.list_unitCostWOTaxListInReturnPurchaseTemplate) {
						unitCostWithoutTaxList.add(Cls_Generic_Methods.getTextInElement(unitCostWOTax));
					}

					for (WebElement paidQuantity : oPage_Purchase.list_paidQuantityListInReturnPurchaseTemplate) {
						paidQuantityList.add(Cls_Generic_Methods.getTextInElement(paidQuantity));
					}

					for (WebElement freeQuantity : oPage_Purchase.list_freeQuantityListInReturnPurchaseTemplate) {
						freeQuantityList.add(Cls_Generic_Methods.getTextInElement(freeQuantity));
					}

					for (WebElement taxRate : oPage_Purchase.list_taxRateListInReturnPurchaseTemplate) {
						taxRateList.add(Cls_Generic_Methods.getTextInElement(taxRate));
					}

					int indexOfItem = 0;
					// Validating Net Unit Cost In Return Template
					for (WebElement netUnitCostWOTax : oPage_Purchase.list_netUnitCostWOTaxListInReturnPurchaseTemplate) {

						String netUnitCostWoTaxValuePerItem = Cls_Generic_Methods.getElementAttribute(netUnitCostWOTax, "value");
						netUnitCostWithoutTaxList.add(netUnitCostWoTaxValuePerItem);

						Double grossAmount = convertStringToDouble(unitCostWithoutTaxList.get(indexOfItem)) *
								convertStringToDouble(paidQuantityList.get(indexOfItem));
						Double discount = convertStringToDouble(discountList.get(indexOfItem));
						Double totalQuantity = convertStringToDouble(paidQuantityList.get(indexOfItem)) + convertStringToDouble(freeQuantityList.get(indexOfItem));

						Double netUnitCostWOTaxCalculated = (grossAmount - discount) / totalQuantity;

						m_assert.assertTrue(convertStringToDouble(netUnitCostWoTaxValuePerItem) == netUnitCostWOTaxCalculated,
								" Net Unit Cost Without Tax Calculated Correctly for Item No.<b> " + indexOfItem + "</b>");

						indexOfItem++;
					}

					// Validating Margin Calculation in Return Purchase

					int indexOfMargin = 0;

					for (WebElement marginAmount : oPage_Purchase.list_marginListInReturnPurchaseTemplate) {

						String marginAmountPerItem = Cls_Generic_Methods.getTextInElement(marginAmount);

						Double unitCostWOTaxPerItem = convertStringToDouble(unitCostWithoutTaxList.get(indexOfMargin));
						Double netUnitCostWithoutTaxPerItem = convertStringToDouble(netUnitCostWithoutTaxList.get(indexOfMargin));

						Double marginAmountCalculated = unitCostWOTaxPerItem - netUnitCostWithoutTaxPerItem;

						m_assert.assertTrue(convertStringToDouble(marginAmountPerItem) == marginAmountCalculated,
								"Margin Amount Calculated Correctly for Item No.<b> " + indexOfMargin + "</b>");

						indexOfMargin++;

					}

					// Entering Return Quantity and Verify Calculation After That

					int indexOfReturnQuantity = 0;
					Double amountBeforeTaxPerItemCalculated = 0.0;

					for (WebElement returnQuantity : oPage_Purchase.list_returnQuantityListInReturnPurchaseTemplate) {

						m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(returnQuantity, returnQuantityValue),
								" Return Quantity Enter as <b> " + returnQuantityValue + "</b> for Item No. <b>" + indexOfReturnQuantity + "</b>");
						Cls_Generic_Methods.customWait(1);

						// Validating Amount Before Tax Per Item and Tax Calculation Per Item

						amountBeforeTaxPerItemCalculated = amountBeforeTaxPerItemCalculated + convertStringToDouble(netUnitCostWithoutTaxList.get(indexOfReturnQuantity)) *
								convertStringToDouble(returnQuantityValue);

						String amountBeforeTax = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_amountBeforeTax, "value");

						m_assert.assertTrue(amountBeforeTaxPerItemCalculated == convertStringToDouble(amountBeforeTax),
								" Amount Per Item Calculated Correctly");

						indexOfReturnQuantity++;

					}

					// Validating Net Amount Per Item

					int indexOfNetAmountPerItem = 0;

					for (WebElement netAmount : oPage_Purchase.list_netAmountListInReturnPurchaseTemplate) {

						String netAmountPerItem = Cls_Generic_Methods.getElementAttribute(netAmount, "value");
						Double gstAmount = getGStCalculation(netUnitCostWithoutTaxList.get(indexOfNetAmountPerItem),
								taxRateList.get(indexOfNetAmountPerItem), returnQuantityValue, "0");

						Double netAmountPerItemCalculated = convertStringToDouble(netUnitCostWithoutTaxList.get(indexOfNetAmountPerItem)) *
								convertStringToDouble(returnQuantityValue) + gstAmount;


						m_assert.assertTrue(convertStringToDouble(netAmountPerItem) == netAmountPerItemCalculated,
								"Net Amount Per Item Calculated Correctly for Item No.<b> " + indexOfNetAmountPerItem + "</b>");

						indexOfNetAmountPerItem++;

					}

					// Validating Tax Calculation Per Item

					int indexGST = 0;
					Double GSTPerItemCalculated = 0.0;
					String gstAmountPerItem = "";

					for (WebElement gstAmount : oPage_Purchase.list_perItemVATListInReturnPurchaseTemplate) {

						gstAmountPerItem = Cls_Generic_Methods.getElementAttribute(gstAmount, "value");

						Double gstAmountPerItemCalculated = getGStCalculation(netUnitCostWithoutTaxList.get(indexGST),
								taxRateList.get(indexGST), returnQuantityValue, "0");

						GSTPerItemCalculated = GSTPerItemCalculated + gstAmountPerItemCalculated;

						indexGST++;

					}

					m_assert.assertTrue(convertStringToDouble(gstAmountPerItem) == GSTPerItemCalculated * 2,
							"GST Amount Item Calculated Correctly for Item No.<b> " + indexGST + "</b>");

					// Validating Net Return With Tax

					String netReturnWithTax = Cls_Generic_Methods.getElementAttribute(
							oPage_Purchase.input_netReturnWithTaxAmount, "value");

					Double netReturnWithTaxCalculated = (GSTPerItemCalculated * 2) + amountBeforeTaxPerItemCalculated;

					m_assert.assertTrue(convertStringToDouble(netReturnWithTax) == netReturnWithTaxCalculated,
							"Net Return With Tax Calculated Correctly");

					Cls_Generic_Methods.selectElementByIndex(oPage_Purchase.select_vendorList, 1);
					Cls_Generic_Methods.customWait();

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
							"Save Changes Button CLicked In Return Purchase");
					Cls_Generic_Methods.customWait();

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_confirmButton),
							"Confirm Button Clicked");
					Cls_Generic_Methods.customWait(4);

					Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 5);
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.header_ReturnDetailsSection),
							" Return Details Section Displayed After Return Purchase");

					// Validating Return Purchase In Items Lots

					CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");


					Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, itemDescription);
					Cls_Generic_Methods.customWait(5);
					oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
					Cls_Generic_Methods.customWait(5);
					int indexOfItemInLot = 0;
					for (WebElement items : oPage_ItemMaster.list_itemListInStoreInventory) {
						List<WebElement> itemNameRow = items.findElements(By.xpath("./child::*"));
						String itemDescriptionText = Cls_Generic_Methods.getTextInElement(itemNameRow.get((0)));

						if (itemDescriptionText.contains(itemDescription)) {
							m_assert.assertTrue(Cls_Generic_Methods.clickElement(items),
									itemDescriptionText + " Clicked in Items Lot at index " + indexOfItemInLot);
							Cls_Generic_Methods.customWait(5);
							bPurchaseTransactionFoundInLot = true;
							if (bPurchaseTransactionFoundInLot) {

								String stockBeforeReturn = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockBeforeInViewTemplate);
								String stockAvailable = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_stockAvailableInItemDetailsInViewTemplate);
								String flowText = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsFlowInViewTemplate);

								Double stockCalculated = convertStringToDouble(stockBeforeReturn) - convertStringToDouble(returnQuantityValue);

								if (flowText.equalsIgnoreCase("Out (Purchase Return)") &&
										Double.toString(stockCalculated).contains(stockAvailable)) {
									bReturnPurchaseTransactionFoundInLot = true;
								}
							}

							m_assert.assertTrue(bReturnPurchaseTransactionFoundInLot,
									" Return Purchase Working Fine , Validate In Lot Correctly at index " + indexOfItemInLot);

							indexOfItemInLot++;

						}
					}

					CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Return");

					boolean bPurchaseReturnTransactionFound = false;
					for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
						purchaseTransactionHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
					}

					for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {
						if (Cls_Generic_Methods.isElementDisplayed(row)) {
							List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

							String dateTimeFull = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Date | Time")));
							String purchaseVendorName = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Vendor")));
							String purchaseNote = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Note")));
							String purchaseAmount = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Amount")));

							String date = dateTimeFull.split("\\|")[0].trim();
							String time = dateTimeFull.split("\\|")[1].trim();
							String purchaseDateAndTime = date + "|" + time;

							DecimalFormat decimalFormat = new DecimalFormat("0.00");
							String purchaseNetAmountUI = decimalFormat.format(convertStringToDouble(purchaseAmount));

							if (purchaseDateAndTime.equals(purchaseTransactionDate + "|" + purchaseTransactionTime) &&
									purchaseNetAmountUI.equalsIgnoreCase(netReturnWithTax) &&
									purchaseVendorName.equalsIgnoreCase(vendorName) ||
									purchaseNote.equalsIgnoreCase(returnNotes)) {
								bPurchaseReturnTransactionFound = true;
								Cls_Generic_Methods.clickElement(row);
								Cls_Generic_Methods.customWait(10);
								break;
							}
						}
					}
					m_assert.assertTrue(bPurchaseReturnTransactionFound, " Purchase return transaction found for date : " + purchaseTransactionDate + "|" + purchaseTransactionTime + " and vendor name :" + vendorName);


				} else {
					m_assert.assertTrue("Transaction Not found or not clicked");
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

	@Test(enabled = true, description = "Validating Cancel Purchase In Pharmacy Store")
	public void validateCancelPurchaseFunctionality() {

		Page_Purchase oPage_Purchase = new Page_Purchase(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		String dateAndTime = "";
		String cancelReason = "Checking For Cancel GRN";


		try {
			CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
			getGST_valueFromSetting();
			Cls_Generic_Methods.driverRefresh();
			Cls_Generic_Methods.customWait(5);

			try {

				// Open Pharmacy Store

				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				// Dynamic Wait
				Cls_Generic_Methods.customWait();

				CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");

				// Creating New Item In Item Master

				boolean addItemStatus = CommonActions.addItemInInventory(itemDescriptionCancel);

				if (addItemStatus) {

					boolean purchaseGrnCreated = createPurchaseGrn("Bill");
					m_assert.assertTrue(purchaseGrnCreated, " Purchase GRN Created Successfully");
					Cls_Generic_Methods.customWait();
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_cancelPurchaseTransaction),
							"Cancel Purchase GRN Button Displayed and Clicked");
					Cls_Generic_Methods.customWait();
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_cancelReasonInput, cancelReason),
							"Cancel GRN Reason Entered as : <b> " + cancelReason + "</b>");


					DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | hh:mmaa");
					dateAndTime = dateFormat.format(new Date()).toString();

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_cancelGRNButton),
							"Cancel GRN Button Clicked In Cancel Template");
					Cls_Generic_Methods.customWait(5);
					List<String> purchaseTransactionHeaderList = new ArrayList<String>();
					boolean purchaseGrnCancelled = false;
					for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
						purchaseTransactionHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
					}
					for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

						if (Cls_Generic_Methods.isElementDisplayed(row)) {
							List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));
							String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Status")));
							if (purchaseStatus.equalsIgnoreCase("cancelled")) {
								Cls_Generic_Methods.clickElement(row);
								purchaseGrnCancelled = true;
								m_assert.assertInfo("Purchase Grn Cancelled");
								break;
							}
						}
					}

					m_assert.assertTrue(purchaseGrnCancelled, " Cancel GRN Working Correctly");
				}

				String cancelledByFullText = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_grnCancelledAt);

				m_assert.assertTrue(cancelledByFullText.equalsIgnoreCase(EHR_Data.user_PRAkashTest + " | " + dateAndTime + " | " + cancelReason),
						" GRN Cancelled By Label and its Value Displayed In View Purchase GRN as : <b> " + cancelledByFullText + " </b>");


			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}

	}

	public static double convertStringToDouble(String sToConvert) {

		double convertedDouble = 0.0;
		try {
			convertedDouble = Double.parseDouble(sToConvert);
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}
		return convertedDouble;
	}

	public static boolean addNewItemStockToPurchase(String itemDescription) {

		Page_Purchase oPage_Purchase = new Page_Purchase(driver);

		boolean addStockFound = false;
		boolean itemFoundInPurchase = false;

		try {

			for (WebElement eItemName : oPage_Purchase.list_itemNameInPurchaseStore) {
				if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(itemDescription)) {
					Cls_Generic_Methods.clickElement(eItemName);
					itemFoundInPurchase = true;
					break;
				}
			}

			m_assert.assertTrue(itemFoundInPurchase, "Item found in purchase: <b> " + itemDescription + "</b>");

			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewLot, 15);

			hsnCode = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_hsnCodeValue);

			if (Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.select_subStore)) {
				m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_subStore, subStore),
						"Sub Store: <b> " + subStore + " </b>");
			}

			m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_saveLot), "Save button is present in A lot Template");
			m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_closeTemplateWithoutSaving), " Cancel button is present in A lot Template");
			m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_previousRate), "Previous Rate Button is present in A lot Template");
			m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_stockAvailability), "Stock Availability button is present in A lot Template");

			m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_expiryDate), "Expiry Date Input box clicked");
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.activeDateInExpiryDate, 1);
			String currentYear = Cls_Generic_Methods.getSelectedValue(oPage_Purchase.select_expiryDateYear);
			int year = Integer.parseInt(currentYear);
			int newYear = year + 3;

			Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_expiryDateYear, Integer.toString(newYear));

			if (!Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.select_expiryDateDay)) {
				Cls_Generic_Methods.clickElement(oPage_Purchase.button_doneButtonInExpiry);
				Cls_Generic_Methods.customWait(1);
			} else {
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.select_expiryDateDay),
						"Current date selected as expiry date as <b>" + Cls_Generic_Methods.getTextInElement(oPage_Purchase.select_expiryDateDay) + " </b>");
			}
			expiryDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_expiryDate, "value");
			Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_unitCostWOTax);
			m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_unitCostWOTax, unitCostWOTax),
					"Unit cost without tax entered as : <b> " + unitCostWOTax + "</b>");

			Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_packageQuantity);
			m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_packageQuantity, packageQuantity),
					"package entry entered as : <b> " + packageQuantity + "</b>");

			Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_discountAmount);
			m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_discountAmount, discountAmount),
					"Discount entered as : <b> " + discountAmount + "</b>");

			Cls_Generic_Methods.customWait(1);
			String totalCostCalculated = CommonActions.getFinalPriceCalculationWithTaxAndDiscount(unitCostWOTax, packageQuantity, taxPercentage, discountAmount);
			Cls_Generic_Methods.customWait(2);

			Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_sellingPrice);
			m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_sellingPrice, sellingPrice),
					"Selling Price entered as : <b> " + sellingPrice + "</b>");

			String sellingPriceCalculated = InventoryCommonActions.getSellingPriceAmountWithoutTax(sellingPrice, taxPercentage);

			String totalCost = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalCost, "value");
			String sellingPrice = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_sellingPriceWOTax, "value");

			m_assert.assertInfo(totalCostCalculated.equalsIgnoreCase(totalCost),
					"Total Cost calculated by function ,present in box are same ");
			m_assert.assertInfo(sellingPriceCalculated.equalsIgnoreCase(sellingPrice),
					"Selling calculated by function ,present in box are same ");

			m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveLot),
					" Save Changes Button CLicked In Inventory Template");

			Cls_Generic_Methods.customWait(2);
			for (WebElement itemName : oPage_Purchase.list_itemStockNameListInStockInventoryTemplate) {
				if (Cls_Generic_Methods.getTextInElement(itemName).contains(itemDescription))
					addStockFound = true;
			}

			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);
			m_assert.assertTrue(addStockFound, "Stock added successfully");

		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}

		return addStockFound;
	}

	public static Double getGStCalculation(String unitCostWOTax, String taxRate,
										   String paidQuantity, String discount) {

		Double gstAmount = 0.00;

		try {

			gstAmount = ((convertStringToDouble(unitCostWOTax) * convertStringToDouble(paidQuantity)
					- convertStringToDouble(discount)) * convertStringToDouble(taxRate)) / 100;
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}
		return gstAmount;
	}

	public static boolean getAddedItemStockCalculation() {

		Page_Purchase oPage_Purchase = new Page_Purchase(driver);

		List<String> unitCostWithoutTaxList = new ArrayList<String>();
		List<String> taxRateList = new ArrayList<String>();
		List<String> paidQuantityList = new ArrayList<String>();
		List<String> discountList = new ArrayList<String>();
		List<String> netAmountList = new ArrayList<String>();

		boolean netAmountPerItemStatus = false;
		boolean bTotalNetAmountStatus = false;

		try {

			// Storing  All item Stocks Cost , Rate , Quantity , NetAmount

			for (WebElement unitCostWOTax : oPage_Purchase.list_unitCostWOTaxListInStockInventoryTemplate) {
				unitCostWithoutTaxList.add(Cls_Generic_Methods.getElementAttribute(unitCostWOTax, "value"));
			}
			for (WebElement taxRate : oPage_Purchase.list_taxRateListInStockInventoryTemplate) {
				taxRateList.add(Cls_Generic_Methods.getElementAttribute(taxRate, "value"));
			}
			for (WebElement paidQuantity : oPage_Purchase.list_paidQuantityListInStockInventoryTemplate) {
				paidQuantityList.add(Cls_Generic_Methods.getElementAttribute(paidQuantity, "value"));
			}
			for (WebElement discount : oPage_Purchase.list_discountListInStockInventoryTemplate) {
				discountList.add(Cls_Generic_Methods.getElementAttribute(discount, "value"));
			}
			for (WebElement netAmount : oPage_Purchase.list_netAmountListInStockInventoryTemplate) {
				netAmountList.add(Cls_Generic_Methods.getElementAttribute(netAmount, "value"));
			}

			// Verifying Net Amount Calculation for each item stock in table

			DecimalFormat decimalFormat = new DecimalFormat("0.00");

			for (int i = 0; i < netAmountList.size(); i++) {

				String netAmountCalculated = CommonActions.getFinalPriceCalculationWithTaxAndDiscount
						(unitCostWithoutTaxList.get(i), paidQuantityList.get(i), taxRateList.get(i), discountList.get(i));

				if (netAmountCalculated.equals(netAmountList.get(i))) {
					m_assert.assertTrue(" Net amount calculated correctly for Item Stock No. <b>" + i + "</b>");
					netAmountPerItemStatus = true;
				}
			}

			m_assert.assertTrue(netAmountPerItemStatus, "Net amount calculated correctly for each item in table");

			// Verifying Total  Gross Amount for All Stock Item

			String totalGrossAmount = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalGrossAmountInLotInventory, "value");
			Double totalGrossAmountCalculated = 0.0;

			for (int i = 0; i < netAmountList.size(); i++) {
				Double grossAmount = convertStringToDouble(unitCostWithoutTaxList.get(i)) *
						convertStringToDouble(paidQuantityList.get(i));
				totalGrossAmountCalculated = totalGrossAmountCalculated + grossAmount;

			}
			m_assert.assertTrue(decimalFormat.format(totalGrossAmountCalculated).equals(totalGrossAmount),
					" Gross Amount Calculated Correctly");

			// Verifying Total  Discount Amount for All Stock Item

			String totalDiscountAmount = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalDiscount, "value");
			Double totalDiscountAmountCalculated = 0.0;

			for (int i = 0; i < discountList.size(); i++) {
				totalDiscountAmountCalculated = totalDiscountAmountCalculated + convertStringToDouble(discountList.get(i));
			}

			m_assert.assertTrue(totalDiscountAmountCalculated.equals(convertStringToDouble(totalDiscountAmount)),
					" Discount Amount Calculated Correctly");

			// Verifying Total GST Amount for All Stock Item as well as on individual level

			String totalGSTAmount = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalGST, "value");
			Double totalGSTAmountCalculated = 0.0;
			int indexOfGst = 0;

			for (WebElement gstAmount : oPage_Purchase.list_perItemGstListInStockInventoryTemplate) {

				String gstAmountPerStock = Cls_Generic_Methods.getElementAttribute(gstAmount, "value");
				Double gstAmountCalculated = getGStCalculation(unitCostWithoutTaxList.get(indexOfGst), taxRateList.get(indexOfGst),
						paidQuantityList.get(indexOfGst), discountList.get(indexOfGst));

				if (gstAmountPerStock.equals(decimalFormat.format(gstAmountCalculated))) {
					m_assert.assertTrue(" GST Amount Calculated Per Item Stock Correctly");
				}
				totalGSTAmountCalculated = totalGSTAmountCalculated + convertStringToDouble(gstAmountPerStock);
				indexOfGst++;

			}

			m_assert.assertTrue(totalGSTAmount.equals(decimalFormat.format(totalGSTAmountCalculated)),
					" GST Amount Calculated Correctly For All Item ");

			// Verifying Total Net amount for all stock item

			String totalOtherCharges = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalOtherCharges, "value");
			String totalNetAmount = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalNetAmount, "value");

			Double totalNetAmountCalculated;
			if (totalOtherCharges.isEmpty()) {
				totalNetAmountCalculated = convertStringToDouble(totalGrossAmount) - convertStringToDouble(totalDiscountAmount)
						+ convertStringToDouble(totalGSTAmount);
			} else {

				totalNetAmountCalculated = convertStringToDouble(totalGrossAmount) - convertStringToDouble(totalDiscountAmount)
						+ convertStringToDouble(totalGSTAmount) + convertStringToDouble(totalOtherCharges);
			}

			if (totalNetAmount.equals(decimalFormat.format(totalNetAmountCalculated))) {
				m_assert.assertTrue(true, " Net amount calculating correctly");
				bTotalNetAmountStatus = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}

		return bTotalNetAmountStatus;
	}

	private void getGST_valueFromSetting() {
		Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
		Page_VendorMaster oPage_VendorMaster = new Page_VendorMaster(driver);
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

	private String getRandomNumber() {
		Random random = new Random();
		String id = String.format("%06d", random.nextInt(1000000));
		return id;
	}

	private boolean createPurchaseGrn(String billType) {
		Page_Purchase oPage_Purchase = new Page_Purchase(driver);
		billNumber = "BILL_" + getRandomUniqueString(5);
		batchNo = getRandomUniqueString(5);
		boolean purchaseGrnCreated = false;


		try {
			CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 10);
			Cls_Generic_Methods.clickElement(oPage_Purchase.button_purchaseNew);
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_Vendor_search, 4);
			Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_Vendor_search, (vendorName));
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
			Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_searchItem, itemDescriptionCancel);
			Cls_Generic_Methods.customWait();
			Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_itemNameInPurchaseStore, 10);
			for (WebElement eItemName : oPage_Purchase.list_itemNameInPurchaseStore) {
				if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(itemDescriptionCancel)) {
					Cls_Generic_Methods.clickElementByJS(driver, eItemName);
					itemClicked = true;
					m_assert.assertInfo("Selected Item <b>" + itemDescriptionCancel + "</b> in Purchase/GRN");
					break;
				}
			}


			if (itemClicked) {
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewLot, 15);

				m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_batchNumber, batchNo),
						"Entered batch number as :<b> " + batchNo + "</b>");
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
					if (!Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.select_expiryDateDay)) {
						Cls_Generic_Methods.clickElement(oPage_Purchase.button_doneButtonInExpiry);
						Cls_Generic_Methods.customWait(1);
					} else {
						m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.select_expiryDateDay),
								"Current date selected as expiry date as <b>" + Cls_Generic_Methods.getTextInElement(oPage_Purchase.select_expiryDateDay) + " </b>");
					}
				}

				expiryDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_expiryDate, "value");


				Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_unitCostWOTax);
				m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_unitCostWOTax, unitCostWOTax),
						"Unit cost without tax entered as : <b> " + unitCostWOTax + "</b>");
				Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_packageQuantity);
				m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_packageQuantity, packageQuantity),
						"package entry entered as : <b> " + packageQuantity + "</b>");
				Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_sellingPrice);
				m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_sellingPrice, sellingPrice),
						"Selling Price entered as : <b> " + sellingPrice + "</b>");
				Cls_Generic_Methods.customWait(1);
				Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveLot);
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);
				Cls_Generic_Methods.clickElement(oPage_Purchase.button_SaveChanges);

			} else {
				m_assert.assertFatal("Item not selected");
			}

			Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes);
			Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
			m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType),
					"Selected Bill Type as <b>" + billType + "</b>");

			m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber), "Entered<b> " + billNumber + "</b> in Bill Number");
			Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
			Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate);

			Cls_Generic_Methods.selectElementByIndex(oPage_Purchase.select_otherCharges, 1);
			m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_otherChargesAmount, otherCharges), "Entered<b> " + otherCharges + " </b> in other charges");
			Cls_Generic_Methods.customWait();
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
						purchaseGrnCreated = true;
						m_assert.assertInfo("Purchase Grn created ");
						Cls_Generic_Methods.customWait(4);
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


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test(enabled = true, description = "Validating Date and other filter enhancements for Purchase GRN In Pharmacy Store")
	public void validateDateAndOtherFilterEnhancementsForGRN() {

		Page_Purchase oPage_Purchase = new Page_Purchase(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);

		String pharmacyStoreName1 = "Pharmacy Viet- Pharmacy";
		String sGRNno="RSO-GRN-201023-101599";


		try {
			CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

			try {

				CommonActions.selectStoreOnApp(pharmacyStoreName1);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 15);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();
//				CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
				boolean bItemMaster = CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");

				m_assert.assertTrue(bItemMaster , " Purchase GRN Option Displayed In Item ");


				//validate Date filter By GRN no.
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.select_dateFilter),"select Date Filter Option");
				Cls_Generic_Methods.clickElement(oPage_Purchase.button_searchFilterOptionGRNno);
				Cls_Generic_Methods.clickElement(oPage_Purchase.input_searchCriteriaforPurchaseGRN);
				m_assert.assertInfo(true, " Click on search Button");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_searchCriteriaforPurchaseGRN,7);

				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_searchCriteriaforPurchaseGRN, sGRNno),
						"Patient GRNno. = <b>" + sGRNno + "</b>");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_searchCriteriaforPurchaseGRN,7);

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_searchItem), "click on search button");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_searchItem,4);

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.list_purchaseGRNDateFilterDataDetails.get(0)), "Click on  = <b>" + sGRNno + "</b>");
				Cls_Generic_Methods.customWait(3);

				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_searchPurchaseGRN).equals(sGRNno),
						" Required GRN no. is selected = <b>" + sGRNno + "</b>");


				//validate Date filter By GRN no.
				Cls_Generic_Methods.clickElement(oPage_Purchase.button_dateFilterExpandOption);
				boolean bitemFound = false;
				for (WebElement webElement : oPage_Purchase.list_purchaseGRNDateFilter) {
					if (Cls_Generic_Methods.getTextInElement(webElement).equals("This Month")) {
						Cls_Generic_Methods.clickElement(webElement);
						bitemFound = true;
						break;
					}
				}
				m_assert.assertTrue(bitemFound, " This month option Filter is selected");
				Cls_Generic_Methods.customWait(5);

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_vendorExpandbutton),
						"click on vendor Expand option");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_vendorExpandbutton,7);

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.list_purchaseGRNvendorList.get(0)),
						"select NEW VENDOR KK option");
				Cls_Generic_Methods.customWait(5);
				Cls_Generic_Methods.clickElement(oPage_Purchase.button_searchFilterOptionGRNno);
				Cls_Generic_Methods.clickElement(oPage_Purchase.input_searchCriteriaforPurchaseGRN);
				m_assert.assertInfo(true, " Click on search Button");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_searchCriteriaforPurchaseGRN,7);

				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_searchCriteriaforPurchaseGRN, sGRNno),
						"Patient GRNno. = <b>" + sGRNno + "</b>");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_searchCriteriaforPurchaseGRN,5);

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_searchItem), "click on search button");
				Cls_Generic_Methods.customWait(4);

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.list_purchaseGRNDateFilterDataDetails.get(0)), "Click on  = <b>" + sGRNno + "</b>");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_searchItem,3);

				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_searchPurchaseGRN).equals(sGRNno),
						" Required GRN no. is selected = <b>" + sGRNno + "</b>");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.text_searchPurchaseGRN,3);

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_dateFilterCrossoption),
						"click on cross mark option, select DATE FILTER option");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_dateFilterCrossoption,3);

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_vendorCrossoption
						),
						"click on cross mark option, select VENDOR option");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_vendorCrossoption,3);


			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("Unable to create Purchase GRN" + e);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

