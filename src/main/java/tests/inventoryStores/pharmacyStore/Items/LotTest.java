package tests.inventoryStores.pharmacyStore.Items;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.store.PharmacyStore.Items.Page_Lot;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Transaction.Page_Purchase;

import java.util.ArrayList;
import java.util.List;

import static pages.commonElements.CommonActions.getRandomUniqueString;
import static tests.settings.organisationSettings.inventoryAndSupplyChain.CategoryMasterTest.getRandomString;

public class LotTest extends TestBase {

	EHR_Data oEHR_Data = new EHR_Data();
	String pharmacyStoreName = "TESTING_STORE- IPD";
	String categoryName = "Medication";
	String subCategoryName = "Test";
	String hsnCode = "HSN" + getRandomUniqueString(4);
	String brandCompanyName = "HealthGraph";
	String itemDescription = "Item_Description_" + getRandomString(4);
	String itemPropertiesTax = "GST5 - 5.0%";
	String itemGenericCompositionName = "Hexapeptide-11";
	String itemGenericCompositionValue = "1.0";
	String[] itemPossibleVariantNameList = {"Variant1","Variant2","Variant3","Variant4"};
	String[] itemPossibleVariantValueList = {"100mg","200mg","300mg","400mg"};
	String itemInitialStock = "0.0";
	String itemDispensingUnit = "";
	String itemCode = "";
	String vendorName = "Supplier ABC";
	String subStore = "Default";
	String batchNo;
	String unitCostWOTax = "100";
	String billNumber;
	String packageQuantity = "20";
	String sellingPrice = "500";
	String otherCharges = "50";
	String transactionNotes = "Transaction_notes";
	String netAmountInGrn = "";
	String grn_no ,grnCreatedAt,grnApprovedBy,grnApprovedAt,sellingPriceByUnit,expiryDate,netAmountBeforeOtherCharge,unitCostTax;
	String[] itemLotTableHeaderList = {"Description","Source","Available Stock","Variant Code"};

	List<String> viewItemRhsSideValueList = new ArrayList<>();
	List<String> viewItemTransactionDetailsValueList = new ArrayList<>();

	List<String> editItemLotDetailsValueList = new ArrayList<>();
	List<String> blockedItemLotDetailsValueList = new ArrayList<>();

	String[] viewItemRhsSideKeyList = {"Description:","Variant Code:","Item Code:","Stock:","Available Stock:","Blocked Stock:","Cost Price:","List Price:"
			,"Identifier:","Vendor:","Barcode:","Sub Store:","Lot Code:","Unit level:","Generic Names:","Source:","Source Txn No:","Category:","Batch No:"};

	String[] transactionDetailsTableHeaderList = {"#.","Transaction No","Transaction Date","Flow","Stock Before","Stock After","Amount Before","Amount After"};
	String[] lotDetailsTemplateKeysList = {"Item code:","Variant Code:","Description:","Stock:","Old Selling Price:","New Selling Price:"};


	@Test(enabled = true, description = "Creating Item For Lot")
	public void creatingItemForLotFunctionality() {

		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Master oPage_Master = new Page_Master(driver);

		boolean bCategoryFound = false;

		try {
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			try {

				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();

				boolean bItemMaster = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");

				m_assert.assertTrue(bItemMaster , " Master Option Displayed In Item ");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Master.button_addNew),
						" Add Item Button Displayed and Clicked");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.header_addItemMasterTemplateTitle, 3);

				m_assert.assertTrue("Add Item Template Header Open and  Displayed as : "+Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.header_addItemMasterTemplateTitle));
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.text_itemDetailsSection),
						" Item Details Section Heading  Present In Add Item Template");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.dropdown_categoryArrow),
						"Category Dropdown Displayed and Clicked in add item ");

				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
				Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, categoryName);
				Cls_Generic_Methods.customWait(1);

				for (WebElement itemCategory : oPage_ItemMaster.list_inventoryItemCategoryList) {
					if (Cls_Generic_Methods.getTextInElement(itemCategory).contains(categoryName)) {
						m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemCategory), "Category selected: <b> " + categoryName + "</b>");
						bCategoryFound = true;
						break;
					}
				}

				if (bCategoryFound) {
					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_subCategoryDropdownArrow, 2);
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_subCategoryDropdownArrow),
							"Sub Category Dropdown Arrow Clicked In Add Item");
					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
					Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, subCategoryName);
					Cls_Generic_Methods.customWait(1);

					for (WebElement subCategory : oPage_ItemMaster.list_inventoryItemSubCategoryList) {
						if (Cls_Generic_Methods.getTextInElement(subCategory).contains(subCategoryName)) {
							m_assert.assertTrue(Cls_Generic_Methods.clickElement(subCategory), "Sub Category selected: <b> " + subCategoryName + "</b>");
							break;

						}
					}

					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemHsnCode, 1);
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemHsnCode, hsnCode),
							" Item HSN Code Displayed and  Entered as : <b>" + hsnCode + "</b>");
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemBrandCompany, brandCompanyName),
							"Item Brand/Company Name Displayed and  Entered as : <b> " + brandCompanyName + "</b>");
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemDescription, itemDescription),
							"Item Description Displayed and Entered as : <b>" + itemDescription + "</b>");
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemManufacturerName, "HealthGraph"),
							"Item Manufacturer Name Displayed and Entered as :<b>" + brandCompanyName + "</b>");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.text_propertiesSection),
							" Properties Section Heading Displayed");

					m_assert.assertTrue(Cls_Generic_Methods.radioButtonIsSelected(oPage_Master.input_taxInclusive),
							" Tax Inclusive Checkbox Displayed and Selected");

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_itemPropertiesTaxList),
							" Tax Dropdown Clicked");
					m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_ItemMaster.select_itemPropertiesTaxList, itemPropertiesTax),
							"Item Properties Tax Entered as : <b>" + itemPropertiesTax + "</b>");

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.checkbox_propertiesExpiryPresent),
							"Item Properties Expiry Present Checkbox Displayed and Clicked");

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.checkbox_propertiesPrescriptionMandatory),
							"Item Properties Prescription Mandatory Checkbox Displayed and Clicked");

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.checkbox_propertiesUnitLevel),
							"Item Properties Unit Level Checkbox Displayed and Clicked");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.text_dispensingUnitSection),
							" Dispensing Unit Section Displayed");

					itemDispensingUnit =  Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.textbox_dispensingUnitDropdownBox);
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.textbox_dispensingUnitDropdownBox),
							" Dispensing Unit Displayed as : <b> "+itemDispensingUnit +" </b>");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.span_inventoryItemPackageType),
							"Package Type Displayed as : <b> "+ Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.span_inventoryItemPackageType) +"</b>");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.input_inventoryItemSubPackageUnit),
							"Sub Package Unit Displayed as : <b> "+ Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_inventoryItemSubPackageUnit, "value")+" </b>");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.input_inventoryItemSubPackageType),
							"Sub Package Unit Type Displayed as : <b> "+ Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.input_inventoryItemSubPackageType)+"</b>");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.input_inventoryItemSubPackageItemUnit),
							"Item Sub Package Unit Displayed as : <b> "+ Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_inventoryItemSubPackageItemUnit, "value")+"</b>");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.input_inventoryItemSubPackageType),
							"Item Sub Package Unit Type Displayed as : <b> "+ Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.input_inventoryItemSubPackageType)+"</b>");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.text_lowScoreWarningSection),
							"Low Scoring Warning Section Displayed");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.input_inventoryItemFixedThreshold),
							"Fixed Threshold Checkbox Displayed");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.text_miniStock),
							"Mini Stock Label Displayed");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.text_medicineClassSection),
							" Class Section Displayed");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.input_inventoryItemMedicineClass),
							" Medicine Class Input Box Displayed");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.button_classFilterButton),
							"Class Filter Displayed");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.text_genericCompositionSection),
							" Generic Composition Section Displayed");

					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemGenericCompositionName, itemGenericCompositionName),
							" Item generic Composition Name Displayed and  Entered as : <b> " + itemGenericCompositionName + " </b>");

					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.name_itemGenericCompositionHexapeptide, 1);
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.name_itemGenericCompositionHexapeptide),
							"Medication Name Clicked on Generic Composition Suggestion Value");
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemGenericCompositionValue, itemGenericCompositionValue),
							" Generic Composition Value Entered as : <b>" + itemGenericCompositionValue + "</b>");
					m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_ItemMaster.select_itemGenericCompositionUnit, "mg"),
							" Generic Composition Unit Entered as : <b>" + "mg" + "</b>");


					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.text_possibleVariantSection),
							" Possible Variant Section Text Present ");
					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemPossibleVariantName, 2);

					for(int i = 0 ;i<= 2;i++ ){
						Cls_Generic_Methods.clickElement(oPage_Master.button_addPossibleVariantButton);
						Cls_Generic_Methods.customWait();
					}

					for(WebElement ePossibleVariantName : oPage_ItemMaster.list_input_itemPossibleVariantNameList) {

						int indexOfVariant = oPage_ItemMaster.list_input_itemPossibleVariantNameList.indexOf(ePossibleVariantName);
						m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(ePossibleVariantName, itemPossibleVariantNameList[indexOfVariant]),
								"Item Possible Variant Name Entered as : <b>" + itemPossibleVariantNameList[indexOfVariant] + "</b>");
						m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.input_itemPossibleVariantValue),
								"Item Possible Variant Value Clicked");
						Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemPossibleVariantValue, itemPossibleVariantValueList[indexOfVariant]);
						m_assert.assertTrue(CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_ItemMaster.list_itemPossibleVariantValuesList, itemPossibleVariantValueList[indexOfVariant]),
								"Item Possible Variant Value Entered as : <b>" + itemPossibleVariantValueList[indexOfVariant] + "</b>");
					}
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_saveAddItemTemplate),
							"Save Button Clicked with filled required field");
					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 17);

					Cls_Generic_Methods.clearValuesInElement(oPage_Master.input_itemNameSearchInItemMaster);
					Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, itemDescription);
					Cls_Generic_Methods.customWait(3);
					oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
					Cls_Generic_Methods.customWait(2);

					String rowColor = Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.list_firstItemListInStoreInventory, "style");
					m_assert.assertTrue(rowColor.equalsIgnoreCase("background-color: rgb(255, 182, 193);"),
							" Empty Stock Item Row Color Displayed In Red ");

					boolean bCreatedItemDataFound = selectItemFromItemMasterList(itemDescription ,"",itemInitialStock,itemDispensingUnit,categoryName);

					m_assert.assertTrue(bCreatedItemDataFound,
							" Item Name <b> "+itemDescription+" </b> Found and Created Successfully Item Data Displayed Correctly in Table List");

					boolean purchaseGrnCreated = createPurchaseGrn("Bill");
					m_assert.assertTrue(purchaseGrnCreated, " Purchase GRN Created Successfully");
					Cls_Generic_Methods.customWait();

					packageQuantity = String.valueOf(CommonActions.convertStringToDouble(packageQuantity));
					sellingPrice = String.valueOf(CommonActions.convertStringToDouble(sellingPrice));
					unitCostTax = String.valueOf(CommonActions.convertStringToDouble(unitCostTax));

					viewItemRhsSideValueList.add(itemDescription);
					viewItemRhsSideValueList.add(itemCode+"-100");
					viewItemRhsSideValueList.add(itemCode);
					viewItemRhsSideValueList.add(packageQuantity);
					viewItemRhsSideValueList.add(packageQuantity);
					viewItemRhsSideValueList.add(itemInitialStock);
					viewItemRhsSideValueList.add("₹"+unitCostTax);
					viewItemRhsSideValueList.add("₹"+sellingPrice);
					viewItemRhsSideValueList.add("NA");
					viewItemRhsSideValueList.add(vendorName);
					viewItemRhsSideValueList.add("");
					viewItemRhsSideValueList.add(subStore);
					viewItemRhsSideValueList.add(itemCode+"-100-1000");
					viewItemRhsSideValueList.add("YES");
					viewItemRhsSideValueList.add(itemGenericCompositionName+" "+itemGenericCompositionValue+" "+"mg");
					viewItemRhsSideValueList.add("Purchase");
					viewItemRhsSideValueList.add(grn_no);
					viewItemRhsSideValueList.add(categoryName);
					viewItemRhsSideValueList.add(batchNo);


					String grnApprovedAtDate = CommonActions.getRequiredFormattedDateTime("dd-MM-yyyy", "yyyy-MM-dd", grnApprovedAt.substring(0,10));
					grnApprovedAt = grnApprovedAtDate+" | "+grnApprovedAt.substring(13,18)+grnApprovedAt.substring(19,21);
					netAmountBeforeOtherCharge = String.valueOf(CommonActions.convertStringToDouble(netAmountBeforeOtherCharge));


					viewItemTransactionDetailsValueList.add("1");
					viewItemTransactionDetailsValueList.add(grn_no);
					viewItemTransactionDetailsValueList.add(grnApprovedAt);
					viewItemTransactionDetailsValueList.add("In (Purchase)");
					viewItemTransactionDetailsValueList.add(itemInitialStock);
					viewItemTransactionDetailsValueList.add(packageQuantity);
					viewItemTransactionDetailsValueList.add(itemInitialStock);
					viewItemTransactionDetailsValueList.add(netAmountBeforeOtherCharge);

					editItemLotDetailsValueList.add(itemCode);
					editItemLotDetailsValueList.add(itemCode+"-100");
					editItemLotDetailsValueList.add(itemDescription);
					editItemLotDetailsValueList.add(packageQuantity);
					editItemLotDetailsValueList.add(sellingPrice);


				} else {
					m_assert.assertTrue(bCategoryFound, "Crate Category Name as " + categoryName + " In category master ");
				}

				Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
				Cls_Generic_Methods.customWait();
				Cls_Generic_Methods.driverRefresh();
				Cls_Generic_Methods.waitForPageLoad(driver , 5);

			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}
	}

	@Test(enabled = true, description = "Validation View Lot Functionality")
	public void validateViewLotFunctionalityInItemLot() {

		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Master oPage_Master = new Page_Master(driver);
		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
		Page_Lot oPage_Lot = new Page_Lot(driver);


		boolean bItemFound = false;

		try {
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			try {

				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();

				boolean bItemVariant = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");

				m_assert.assertTrue(bItemVariant , " Lot Option Displayed and Clicked In Item ");

				// Validating Headers From Item Lot Table List

				validatingDataTableHeadersInItemLot(oPage_Master.list_viewItemMasterTableHeaderList,itemLotTableHeaderList, "Item Lot List");

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_refreshItemMasterButton),
						"Refresh Button Displayed In Item Lot");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.input_itemNameSearchInItemMaster),
						"Search Box Displayed In Item Lot");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Lot.button_filterByButton),
						" Filter By Button Displayed In Item Lot");

				Cls_Generic_Methods.clearValuesInElement(oPage_Master.input_itemNameSearchInItemMaster);
				Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, itemDescription);
				Cls_Generic_Methods.customWait(3);
				oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
				Cls_Generic_Methods.customWait(2);

				for(WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory){

					if(Cls_Generic_Methods.isElementDisplayed(itemData)){

						List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./td/div"));

						String itemDescriptionNameAndVariantCode = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((0))).replace("\n", " ");
						String itemGRNDetails = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1))).replace("\n", " ");
						String itemBatchAndSubStore= Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((2))).replace("\n", " ");

						expiryDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd-MM-yyyy", expiryDate);
						m_assert.assertTrue(itemDescriptionNameAndVariantCode.equalsIgnoreCase(itemDescription+" "+itemCode+"-100"),
								" Item Description and Variant Code Displayed Correctly In Lot List");
						System.out.println(itemGRNDetails);
						System.out.println("Exp. "+expiryDate+" Purchase "+packageQuantity+"  ₹"+sellingPrice);

						m_assert.assertTrue(itemGRNDetails.contains("Exp. "+expiryDate+" Purchase "+packageQuantity+"  ₹"+sellingPrice),
								"GRN Expiry Date ,Type,Cost,MRP Displayed Correctly In Lot List");
						m_assert.assertTrue(itemBatchAndSubStore.equalsIgnoreCase(batchNo+" "+subStore),
								"GRN Expiry Date ,Type,Cost,MRP Displayed Correctly In Lot List");

						if (itemDescriptionNameAndVariantCode.contains(itemDescription)) {
							bItemFound = true;
							m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemData),
									itemDescription+" Item Clicked from Item Lot List");
							Cls_Generic_Methods.customWait(5);
							break;
						}
					}
				}

				m_assert.assertTrue(bItemFound," Item Found and Clicked");

				int indexOfKey = 0,indexOfValue = 0 ;
				for(WebElement eViewItem : oPage_Master.list_keysAndValuesInItemViewRHSSide){

					if(Cls_Generic_Methods.isElementDisplayed(eViewItem)){
						int indexOfViewItem = oPage_Master.list_keysAndValuesInItemViewRHSSide.indexOf(eViewItem);
						String sViewItemText = Cls_Generic_Methods.getTextInElement(eViewItem);
						if(indexOfViewItem % 2 == 0) {
							m_assert.assertTrue(sViewItemText.equalsIgnoreCase(viewItemRhsSideKeyList[indexOfKey]),
									"<b> "+sViewItemText+ " </b> Label Present In View Item Lot RHS side ");
							indexOfKey ++ ;
						}else{
							if(indexOfViewItem  == 20){
								m_assert.assertTrue(!(sViewItemText.isEmpty()),
										"<b> Empty </b>  Value Present In View Item Lot RHS against label <b> "+viewItemRhsSideKeyList[indexOfValue]+"</b>");
							}else {

								System.out.println(sViewItemText);
								System.out.println(viewItemRhsSideValueList.get(indexOfValue));

								m_assert.assertTrue(sViewItemText.equalsIgnoreCase(viewItemRhsSideValueList.get(indexOfValue)),
										"<b> " + sViewItemText + "  </b> Value Present In View Item RHS against label <b> " + viewItemRhsSideKeyList[indexOfValue] + " </b>");
							}
							indexOfValue ++ ;
						}
					}
				}

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Lot.button_editLot),
						" Edit Lot Button Displayed In View Item Lot");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Lot.button_blockLot),
						" Block Lot Button Displayed In View Item Lot");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Lot.text_transactionDetailsSectionText),
						" Transaction Details Section Displayed In View Item Lot");

				validatingDataTableHeadersInItemLot(oPage_Lot.list_transactionDetailsTableHeaderList,transactionDetailsTableHeaderList,"Transaction Details");

				for(WebElement eTransactionDetailsValue : oPage_Lot.list_transactionDetailsTableValueList) {
					if(Cls_Generic_Methods.isElementDisplayed(eTransactionDetailsValue)) {

						int indexOfTransactionValue = oPage_Lot.list_transactionDetailsTableValueList.indexOf(eTransactionDetailsValue);
						String sTransactionValue = Cls_Generic_Methods.getTextInElement(eTransactionDetailsValue);
						if(sTransactionValue.equalsIgnoreCase(viewItemTransactionDetailsValueList.get(indexOfTransactionValue))){
							m_assert.assertTrue( " Transaction Details Table Value for Column <b> "+transactionDetailsTableHeaderList[indexOfTransactionValue]+
									" </b> Displayed correctly as : <b> "+sTransactionValue+"</b>");
						}else{
							m_assert.assertWarn(" Either Column Removed or Missing , Or No data Enter For Required Column");
						}
					}
				}

				Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
				Cls_Generic_Methods.customWait();
				Cls_Generic_Methods.driverRefresh();
				Cls_Generic_Methods.waitForPageLoad(driver , 5);

			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}
	}

	@Test(enabled = true, description = "Validation Edit Lot Functionality")
	public void validateEditLotFunctionalityInItemLot() {

		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Master oPage_Master = new Page_Master(driver);
		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
		Page_Lot oPage_Lot = new Page_Lot(driver);


		boolean bItemFound = false;

		try {
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			try {

				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();

				boolean bItemVariant = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");

				m_assert.assertTrue(bItemVariant , " Lot Option Displayed and Clicked In Item ");


				Cls_Generic_Methods.clearValuesInElement(oPage_Master.input_itemNameSearchInItemMaster);
				Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, itemDescription);
				Cls_Generic_Methods.customWait(3);
				oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
				Cls_Generic_Methods.customWait(2);

				for(WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory){

					if(Cls_Generic_Methods.isElementDisplayed(itemData)){

						List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./td/div"));

						String itemDescriptionNameAndVariantCode = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((0))).replace("\n", " ");

						if (itemDescriptionNameAndVariantCode.contains(itemDescription)) {
							bItemFound = true;
							m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemData),
									itemDescription+" Item Clicked from Item Lot List");
							Cls_Generic_Methods.customWait(5);
							break;
						}
					}
				}

				m_assert.assertTrue(bItemFound," Item Found and Clicked");

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Lot.button_editLot),
						" Edit Lot Button Displayed and Clicked In Lot");
				m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.header_lotDetailsHeaderInEditLot,5),
						" Edit Lot Template Displayed and Opened");

				int indexOfKey = 0,indexOfValue = 0 ;
				for(WebElement eEditItem : oPage_Lot.list_lotDetailsTableKeysAndValuesInEditLot){

					if(Cls_Generic_Methods.isElementDisplayed(eEditItem)){

						int indexOfEditItem = oPage_Lot.list_lotDetailsTableKeysAndValuesInEditLot.indexOf(eEditItem);

						if(indexOfEditItem % 2 == 0) {
							String sEditItemText = Cls_Generic_Methods.getTextInElement(eEditItem);
							m_assert.assertTrue(sEditItemText.equalsIgnoreCase(lotDetailsTemplateKeysList[indexOfKey]),
									"<b> "+sEditItemText+ " </b> Label Present In Edit Item Lot Template ");
							indexOfKey ++ ;
						}else{
							String sEditItemText = Cls_Generic_Methods.getElementAttribute(eEditItem.findElement(By.xpath("./input")),"value");
							if(indexOfEditItem  == 11){
								m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(eEditItem.findElement(By.xpath("./input"))),
										lotDetailsTemplateKeysList[indexOfValue]+" Field is Enabled");
								m_assert.assertTrue(sEditItemText.equalsIgnoreCase(editItemLotDetailsValueList.get(indexOfValue-1)),
										"<b> Empty </b>  Value Present In View Item Lot RHS against label <b> "+lotDetailsTemplateKeysList[indexOfValue]+"</b>");
							}else {
								m_assert.assertFalse(Cls_Generic_Methods.isElementClickable(driver,eEditItem.findElement(By.xpath("./input"))),
										lotDetailsTemplateKeysList[indexOfValue]+" Field is Disabled");
								m_assert.assertTrue(sEditItemText.equalsIgnoreCase(editItemLotDetailsValueList.get(indexOfValue)),
										"<b> " + sEditItemText + "  </b> Value Present In Edit Item against label <b> " + lotDetailsTemplateKeysList[indexOfValue] + " </b>");
							}
							indexOfValue ++ ;
						}
					}
				}

				String newSellingPrice = "600.0";
				Cls_Generic_Methods.clearValuesInElement(oPage_Lot.input_newMRP);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_newMRP,newSellingPrice),
						" New Selling Price Entered As : "+newSellingPrice);
				Cls_Generic_Methods.customWait(1);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Lot.button_updateLot),
						" Update Lot Button Displayed and Clicked");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.button_editLot,5);

				for(WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory){

					if(Cls_Generic_Methods.isElementDisplayed(itemData)){

						List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./td/div"));

						String itemDescriptionNameAndVariantCode = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((0))).replace("\n", " ");
						String itemGRNDetails = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1))).replace("\n", " ");
						String itemBatchAndSubStore= Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((2))).replace("\n", " ");

						m_assert.assertTrue(itemDescriptionNameAndVariantCode.equalsIgnoreCase(itemDescription+" "+itemCode+"-100"),
								" Item Description and Variant Code Displayed Correctly In Lot List");
						m_assert.assertTrue(itemGRNDetails.contains("Exp. "+expiryDate+" Purchase "+packageQuantity+"  ₹"+newSellingPrice),
								"GRN Expiry Date ,Type,Cost,MRP Displayed Correctly In Lot List");
						m_assert.assertTrue(itemBatchAndSubStore.equalsIgnoreCase(batchNo+" Price Adjust "+subStore),
								"Sub Store and Batch No. Displayed Correctly In Lot List");

						if (itemDescriptionNameAndVariantCode.contains(itemDescription)) {
							bItemFound = true;
							m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemData),
									itemDescription+" Item Clicked from Item Lot List");
							Cls_Generic_Methods.customWait(5);
							break;
						}
					}
				}


				m_assert.assertTrue(bItemFound ," Price Updated Successfully In Lot List");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Lot.strong_updatedSellingPrice),
						"Updated Selling Price Displayed In View Lot as : "+Cls_Generic_Methods.getTextInElement(oPage_Lot.strong_updatedSellingPrice));

				Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
				Cls_Generic_Methods.customWait();
				Cls_Generic_Methods.driverRefresh();
				Cls_Generic_Methods.waitForPageLoad(driver , 5);

			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}
	}

	@Test(enabled = true, description = "Validation Block and Unblock Lot Functionality")
	public void validateBlockAndUnblockLotFunctionalityInItemLot() {

		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Master oPage_Master = new Page_Master(driver);
		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
		Page_Lot oPage_Lot = new Page_Lot(driver);
		String sBlockReason = "Validating Block Item Lot";
		String sUnblockReason = "Validating Un-Block Item Lot";

		String[] blockLotDetailsTemplateKeysList = {"User:","Date:","Blocked Stock:","Comment:","Category:","Batch No:"};



		boolean bItemFound = false;

		try {
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			try {

				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();

				boolean bItemVariant = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");

				m_assert.assertTrue(bItemVariant , " Lot Option Displayed and Clicked In Item ");

				String date = CommonActions.getRequiredFormattedDateTime("dd/mm/yyyy","dd-mm-yyyy",Cls_Generic_Methods.getTodayDate());

				Cls_Generic_Methods.clearValuesInElement(oPage_Master.input_itemNameSearchInItemMaster);
				Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, itemDescription);
				Cls_Generic_Methods.customWait(3);
				oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
				Cls_Generic_Methods.customWait(2);

				for(WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory){

					if(Cls_Generic_Methods.isElementDisplayed(itemData)){

						List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./td/div"));

						String itemDescriptionNameAndVariantCode = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((0))).replace("\n", " ");

						if (itemDescriptionNameAndVariantCode.contains(itemDescription)) {
							bItemFound = true;
							m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemData),
									itemDescription+" Item Clicked from Item Lot List");
							Cls_Generic_Methods.customWait(5);
							break;
						}
					}
				}

				m_assert.assertTrue(bItemFound," Item Found and Clicked");

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Lot.button_blockLot),
						" Block Lot Button Displayed and Clicked In Lot");
				m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.header_blockLotHeader,5),
						" Block Lot Template Displayed and Opened");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Lot.label_areYouSureMessage),
						"Are you Sure Message Label Displayed as : "+ Cls_Generic_Methods.getTextInElement(oPage_Lot.label_areYouSureMessage));

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Lot.label_closerReasonMessage),
						"Closer Message Label Displayed as : "+ Cls_Generic_Methods.getTextInElement(oPage_Lot.label_closerReasonMessage));

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Lot.button_dontBlockButton),
						"Don't Block Button Displayed");

				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_blockLotComment,sBlockReason),
						"Block Comment Entered as : <b> "+ sBlockReason);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Lot.button_blockLotConfirmation),
						"Block Lot Button Clicked ");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.button_unblockLot,5);

				packageQuantity = String.valueOf(CommonActions.convertStringToDouble(packageQuantity));

				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Lot.text_blockedLotCount).equalsIgnoreCase(packageQuantity),
						" Block Button Working Correctly and Blocked Stock Updated as : <b> "+ Cls_Generic_Methods.getTextInElement(oPage_Lot.text_blockedLotCount));

				blockedItemLotDetailsValueList.add(EHR_Data.user_PRAkashTest);
				blockedItemLotDetailsValueList.add(date);
				blockedItemLotDetailsValueList.add(Cls_Generic_Methods.getTextInElement(oPage_Lot.text_blockedLotCount));
				blockedItemLotDetailsValueList.add(sBlockReason);
				blockedItemLotDetailsValueList.add(categoryName);
				blockedItemLotDetailsValueList.add(batchNo);



				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Lot.text_blockedLotUSerDetails),
						" Blocked Lot By User Details Section Displayed");

				int indexOfKey = 0,indexOfValue = 0 ;
				for(WebElement eBlockedItem : oPage_Lot.list_blockedLotUserDetailsTableKeysAndValuesList){

					if(Cls_Generic_Methods.isElementDisplayed(eBlockedItem)){

						int indexOfBlockedItem = oPage_Lot.list_blockedLotUserDetailsTableKeysAndValuesList.indexOf(eBlockedItem);
						String sBlockedItemText = Cls_Generic_Methods.getTextInElement(eBlockedItem);

						if(indexOfBlockedItem % 2 == 0) {
							m_assert.assertTrue(sBlockedItemText.equalsIgnoreCase(blockLotDetailsTemplateKeysList[indexOfKey]),
									"<b> "+sBlockedItemText+ " </b> Label Present In Blocked Item Lot");
							indexOfKey ++ ;
						}else{

							m_assert.assertTrue(sBlockedItemText.equalsIgnoreCase(blockedItemLotDetailsValueList.get(indexOfValue)),
									"<b> " + sBlockedItemText + "  </b> Value Present In Blocked Item against label <b> " + blockLotDetailsTemplateKeysList[indexOfValue] + " </b>");
							indexOfValue ++ ;
						}
					}
				}

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Lot.button_unblockLot),
						"Unblock Button Displayed and Clicked");

				m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.header_unblockLotHeader,5),
						" Un-Block Lot Template Displayed and Opened");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Lot.label_areYouSureMessageUnblock),
						"Are you Sure Message Label Displayed In unblock template as : "+ Cls_Generic_Methods.getTextInElement(oPage_Lot.label_areYouSureMessage));

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Lot.label_closerReasonMessageUnblock),
						"Closer Message Label Displayed in unblock template as : "+ Cls_Generic_Methods.getTextInElement(oPage_Lot.label_closerReasonMessage));

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Lot.button_dontUnblockButton),
						"Don't Un-Block Button Displayed");

				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_unblockLotComment,sUnblockReason),
						"Un-Block Comment Bix displayed and value Entered as : <b> "+ sUnblockReason);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Lot.button_unblockLotConfirmation),
						"Block Lot Button Clicked ");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.button_editLot,5);

				packageQuantity = String.valueOf(CommonActions.convertStringToDouble(packageQuantity));

				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Lot.text_blockedLotCount).equalsIgnoreCase(itemInitialStock),
						" Unblocked button Working Correctly and Block Stock Updated as : <b> "+ Cls_Generic_Methods.getTextInElement(oPage_Lot.text_blockedLotCount));

				Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
				Cls_Generic_Methods.customWait();
				Cls_Generic_Methods.driverRefresh();
				Cls_Generic_Methods.waitForPageLoad(driver , 5);

			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test(enabled = true, description = "Creating Item For Lot")
	public void validateSearchFeatureInLotFunction() {

		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

		String sItemDescription = "Item_Description_vxrn_Updated";
		String SBatchNo = "H0S1Q";
		String sVarintCode = "INV-MISC-1010-100";
		String sTransactionNo = "RSO-GRN-200723-101236";
		String sItemCode = "INV-OP-ITC-1161";
		String sBarCode = "RSO175702INVMED11781001096";


//		boolean bItemFound = false;

		try {
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			try {

				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();
				boolean bItemMaster = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
				m_assert.assertTrue(bItemMaster, " Lot Option Displayed In Item ");


				//search by item description
				Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_searchFilterItemDescription);
				boolean bitemFound = false;
				for (WebElement webElement : oPage_ItemMaster.list_searchFilterItemDescriptionDropdown) {
					if (Cls_Generic_Methods.getTextInElement(webElement).equals("Item Description")) {
						webElement.click();
						bitemFound = true;
						break;
					}
				}
				m_assert.assertTrue(bitemFound, " Item Description is selected");
				Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_searchFilterItemDescription);
				Cls_Generic_Methods.clickElement(oPage_ItemMaster.input_searchbarCriteria);
				m_assert.assertInfo(true, " Click on search Button");
				Cls_Generic_Methods.customWait(3);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchbarCriteria, sItemDescription),
						"Item Decsription = <b>" + sItemDescription + "</b>");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_searchItem), "click on search button");
				Cls_Generic_Methods.customWait(4);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.list_searchItem1.get(0)), "Click on  = <b>" + sItemDescription + "</b>");
				Cls_Generic_Methods.customWait(3);

				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.text_searchItemDescriptionName).equals(sItemDescription),
						" Required Item is selected = <b>" + sItemDescription + "</b>");

				Cls_Generic_Methods.customWait(3);


				//search by Batch No.
				Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_searchFilterItemDescription);
				boolean bitemFound1 = false;
				for (WebElement webElement : oPage_ItemMaster.list_searchFilterItemDescriptionDropdown) {
					if (Cls_Generic_Methods.getTextInElement(webElement).equals("Batch No.")) {
						webElement.click();
						bitemFound1 = true;
						break;
					}
				}
				m_assert.assertTrue(bitemFound1, " Batch no is selected");
				Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_searchbarCriteria);
				Cls_Generic_Methods.customWait(3);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchbarCriteria, SBatchNo), "Batch No = <b>" + SBatchNo + "</b>");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_searchItem), "click on search button");
				Cls_Generic_Methods.customWait(3);
				oPage_ItemMaster = new Page_ItemMaster(driver);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.list_searchItem1.get(0)), "Click on  = <b>" + SBatchNo + "</b>");
				Cls_Generic_Methods.customWait(3);
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.text_searchBatchNo).equals(SBatchNo),
						" Required Item Are selected = <b>" + SBatchNo + "</b>");
				Cls_Generic_Methods.customWait(3);


				//search by Variant Code
				Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_searchFilterItemDescription);
				boolean bitemFound2 = false;
				for (WebElement webElement : oPage_ItemMaster.list_searchFilterItemDescriptionDropdown) {
					if (Cls_Generic_Methods.getTextInElement(webElement).equals("Variant Code")) {
						webElement.click();
						bitemFound2 = true;
						break;
					}
				}
				m_assert.assertTrue(bitemFound2, " Variant code is selected");
				Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_searchbarCriteria);
				Cls_Generic_Methods.customWait(3);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchbarCriteria, sVarintCode), "Batch No = <b>" + sVarintCode + "</b>");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_searchItem), "click on search button");
				Cls_Generic_Methods.customWait(3);
				oPage_ItemMaster = new Page_ItemMaster(driver);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.list_searchItem1.get(0)), "Click on  = <b>" + sVarintCode + "</b>");
				Cls_Generic_Methods.customWait(3);
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.text_searchVariantCode).equals(sVarintCode),
						" Required Item Are selected = <b>" + sVarintCode + "</b>");
				Cls_Generic_Methods.customWait(3);

				//search by Transaction No
				Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_searchFilterItemDescription);
				boolean bitemFound3 = false;
				for (WebElement webElement : oPage_ItemMaster.list_searchFilterItemDescriptionDropdown) {
					if (Cls_Generic_Methods.getTextInElement(webElement).equals("Transaction No.")) {
						webElement.click();
						bitemFound3 = true;
						break;
					}
				}
				m_assert.assertTrue(bitemFound3, " Transaction No. is selected");
				Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_searchbarCriteria);
				Cls_Generic_Methods.customWait(3);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchbarCriteria, sTransactionNo), "Transaction No = <b>" + sTransactionNo + "</b>");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_searchItem), "click on search button");
				Cls_Generic_Methods.customWait(3);
				oPage_ItemMaster = new Page_ItemMaster(driver);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.list_searchItem1.get(0)), "Click on  = <b>" + sTransactionNo + "</b>");
				Cls_Generic_Methods.customWait(3);
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.text_searchTransactionNo).equals(sTransactionNo),
						" Required Item Are selected = <b>" + sTransactionNo + "</b>");
				Cls_Generic_Methods.customWait(3);


				//search by Item Code
				Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_searchFilterItemDescription);
				boolean bitemFound4 = false;
				for (WebElement webElement : oPage_ItemMaster.list_searchFilterItemDescriptionDropdown) {
					if (Cls_Generic_Methods.getTextInElement(webElement).equals("Item Code")) {
						webElement.click();
						bitemFound4 = true;
						break;
					}
				}
				m_assert.assertTrue(bitemFound4, " item code is selected");
				Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_searchbarCriteria);
				Cls_Generic_Methods.customWait(3);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchbarCriteria, sItemCode), "Item code = <b>" + sItemCode + "</b>");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_searchItem), "click on search button");
				Cls_Generic_Methods.customWait(3);
				oPage_ItemMaster = new Page_ItemMaster(driver);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.list_searchItem1.get(0)), "Click on  = <b>" + sItemCode + "</b>");
				Cls_Generic_Methods.customWait(3);
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.text_itemCode).equals(sItemCode),
						" Required Item Are selected = <b>" + sItemCode + "</b>");
				Cls_Generic_Methods.customWait(3);


				//search by Bar Code
				Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_searchFilterItemDescription);
				boolean bitemFound5 = false;
				for (WebElement webElement : oPage_ItemMaster.list_searchFilterItemDescriptionDropdown) {
					if (Cls_Generic_Methods.getTextInElement(webElement).equals("Barcode")) {
						webElement.click();
						bitemFound5 = true;
						break;
					}
				}
				m_assert.assertTrue(bitemFound5, " bar code is selected");
				Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_searchbarCriteria);
				Cls_Generic_Methods.customWait(3);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchbarCriteria, sBarCode), "bar code = <b>" + sBarCode + "</b>");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_searchItem), "click on search button");
				Cls_Generic_Methods.customWait(3);
				oPage_ItemMaster = new Page_ItemMaster(driver);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.list_searchItem1.get(0)), "Click on  = <b>" + sBarCode + "</b>");
				Cls_Generic_Methods.customWait(3);
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.text_searchBarCode).equals(sBarCode),
						" Required Item Are selected = <b>" + sBarCode + "</b>");
				Cls_Generic_Methods.customWait(3);


			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
			Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_searchItem, itemDescription);
			Cls_Generic_Methods.customWait();
			Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_itemNameInPurchaseStore, 10);
			for (WebElement eItemName : oPage_Purchase.list_itemNameInPurchaseStore) {
				if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(itemDescription)) {
					Cls_Generic_Methods.clickElementByJS(driver, eItemName);
					itemClicked = true;
					m_assert.assertInfo("Selected Item <b>" + itemDescription + "</b> in Purchase/GRN");
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
					Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.select_expiryDateDay);
				}

				expiryDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_expiryDate,"value");


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
				unitCostTax = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_unitCostWithTax,"value");
				sellingPriceByUnit = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_sellingPriceByUnit,"value");
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

			netAmountBeforeOtherCharge = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalNetAmount, "value");
			Cls_Generic_Methods.selectElementByIndex(oPage_Purchase.select_otherCharges, 1);
			m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_otherChargesAmount, otherCharges), "Entered<b> " + otherCharges + " </b> in other charges");
			Cls_Generic_Methods.customWait();
			netAmountInGrn = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalNetAmount, "value");
			m_assert.assertInfo("Net amount in Purchase Grn = <b>" + netAmountInGrn + "</b>");
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
						Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 10);
						Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
						Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_Yes, 6);
						Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.button_Yes);
						purchaseGrnCreated = true;
						m_assert.assertInfo("Purchase Grn created and approved");
						Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_assignBarcodePurchaseTransaction, 15);
						grn_no = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_transactionID);
						m_assert.assertInfo("Purchase Grn no =<b>" + grn_no + "</b>");
						grnCreatedAt = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_grnCreatedAt);
						m_assert.assertInfo("Purchase Grn Created At =<b>" + grnCreatedAt + "</b>");
						grnApprovedBy = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_grnApprovedByUser);
						m_assert.assertInfo("Purchase Grn Approved By user =<b>" + grnApprovedBy + "</b>");
						grnApprovedAt = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_grnApprovedAt);
						m_assert.assertInfo("Purchase Grn Approved At =<b>" + grnApprovedAt + "</b>");
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
	public boolean  selectItemFromItemMasterList(String sItemDescription ,String sItemCode ,String sItemInitialStock,
												 String sDispensingUnit ,String sCategoryName) {

		boolean bCreatedItemDataFound = false;

		Page_Master oPage_Master = new Page_Master(driver);
		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);


		try {
			Cls_Generic_Methods.clearValuesInElement(oPage_Master.input_itemNameSearchInItemMaster);
			Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, itemDescription);
			Cls_Generic_Methods.customWait(4);
			oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
			Cls_Generic_Methods.customWait(3);

			for (WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory) {
				if (Cls_Generic_Methods.isElementDisplayed(itemData)) {

					List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./child::*"));

					itemCode = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((0)));
					String itemDescriptionName = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));
					String itemStock = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((2)));
					String itemDispensingUnitUI = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((3)));
					String itemCategory = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((4)));

					if (itemDescriptionName.contains(sItemDescription)
							&& (!itemCode.equalsIgnoreCase(sItemCode) || itemCode.equalsIgnoreCase(sItemCode))
							&& itemStock.equalsIgnoreCase(sItemInitialStock)
							&& itemDispensingUnitUI.equalsIgnoreCase(sDispensingUnit)
							&& itemCategory.contains(sCategoryName)
					) {
						bCreatedItemDataFound = true;
						m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemData),
								"<b> "+itemDescription + " </b> Item Clicked from Item Master List");
						Cls_Generic_Methods.customWait(5);

						m_assert.assertInfo(" <b> Item Code </b> of Created/Updated Item Displayed as : <b> " + itemCode +" </b> In Item Master List");
						m_assert.assertInfo(" <b> Item Name </b> of Created/Updated  Item Displayed as : <b> " + itemDescription+" </b> In Item Master List");
						m_assert.assertInfo(" <b> Item Stock </b> of Created/Updated  Item Displayed as : <b> " + itemInitialStock+" </b> ");
						m_assert.assertInfo(" <b> Item Dispensing Unit </b> of Created/Updated  Item Displayed as : <b> " + itemDispensingUnit+" </b> In Item Master List");
						m_assert.assertInfo(" <b> Item Category </b> of Created/Updated  Item Displayed as : <b> " + categoryName+" </b> In Item master List ");

						break;
					}

				}
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return bCreatedItemDataFound ;
	}

	public void validatingDataTableHeadersInItemLot(List<WebElement> eHeaderList , String [] actualHeaderList ,String sSection){
		try{
			for(WebElement itemTableKeys : eHeaderList){

				if(Cls_Generic_Methods.isElementDisplayed(itemTableKeys)){

					int indexOfLotDetailsKey = eHeaderList.indexOf(itemTableKeys);
					String sItemKeyText = Cls_Generic_Methods.getTextInElement(itemTableKeys);
					if(sItemKeyText.equalsIgnoreCase(actualHeaderList[indexOfLotDetailsKey])){

						m_assert.assertTrue( "<b> " +sItemKeyText + " </b> Header Present In "+sSection+" Table at index : <b> "+indexOfLotDetailsKey +" </b>");

					}else{
						m_assert.assertWarn(sItemKeyText +" header not present in "+sSection +"Table ,Either Removed or changed");
					}
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
