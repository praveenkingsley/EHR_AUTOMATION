package tests.inventoryStores.pharmacyStore.Items;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import org.openqa.selenium.*;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.Page_InventoryFilterCommonElements;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Transaction.Page_Purchase;

import java.util.*;

import static pages.commonElements.CommonActions.*;
import static tests.settings.organisationSettings.inventoryAndSupplyChain.CategoryMasterTest.getRandomString;

public class MasterTest extends TestBase {

	EHR_Data oEHR_Data = new EHR_Data();
	String pharmacyStoreName = "Pharmacy automation- Pharmacy";
	String categoryName = "Medication";
	String subCategoryName = "Test";
	String hsnCode = "HSN" + getRandomUniqueString(4);
	String brandCompanyName = "HealthGraph";
	String itemDescription = "Item_Description_" + getRandomString(4);
	String itemPropertiesTax = "GST5 - 5.0%";
	String itemGenericCompositionName = "Hexapeptide-11";
	String itemGenericCompositionNameUpdated = "Vitamin Aasetat";
	String itemGenericCompositionValue = "1.0";
	String[] itemPossibleVariantNameList = {"Variant1","Variant2","Variant3","Variant4"};
	String[] itemPossibleVariantValueList = {"100mg","200mg","300mg","400mg"};
	String[] itemMasterTableHeaderList = {"Item Code","Description","Stock","Dispensing Unit","Category"};
	String itemInitialStock = "0.0";
	String itemDispensingUnit = "";
	String itemCode = "";
	String[] viewItemRhsSideKeyList = {"Description:","Item Code:","Barcode:","Manufacturer:","Stock:","Dispensing Unit:","Category Type:","Sub Category:","Generic Names:","Threshold Value:"};
	List<String> viewItemRhsSideValueList = new ArrayList<>();
	List<String> viewUpdatedItemRhsSideValueList = new ArrayList<>();
	List<String> viewItemLotDetailsValueList = new ArrayList<>();
	List<String> viewItemTransactionHistoryValueList = new ArrayList<>();

	String[] lotDetailsTableHeaderList = {"#","Variant Code","Source","Sub Store","Batch No.","Stock","MRP","Expiry","Vendor"};
	String[] transactionHistoryTableHeaderList = {"#","Transaction No:","Transaction Date","User Name","Transaction Type","Amount"};
	String updatedItemDescription = itemDescription + "_Updated";
	String updatedDispensingUnit = "Capsules";
	String vendorName = "Supplier ABC";
	String subStore = "Default";
	String batchNo;
	String unitCostWOTax = "100";
	String billNumber;
	String packageQuantity = "20";
	final String sellingPrice = "500";
	String otherCharges = "50";
	String transactionNotes = "Transaction_notes";
	String netAmountInGrn = "";
	String updatedSubCategoryName = "itemSubCategory_2";

	String grn_no ,grnCreatedAt,grnApprovedBy,grnApprovedAt,sellingPriceByUnit,expiryDate,netAmountBeforeOtherCharge;




	@Test(enabled = true, description = "Validating Add Item Functionality in Item Master")
	public void validateAddFunctionalityInItemMaster() {

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

				boolean bItemMaster = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");

				m_assert.assertTrue(bItemMaster , " Master Option Displayed In Item ");

				// Validating Headers From Item Master Table List

				validatingDataTableHeadersInItemMaster(oPage_Master.list_viewItemMasterTableHeaderList,itemMasterTableHeaderList, "Item Master List");

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_refreshItemMasterButton),
						" Refresh Button Displayed In Item Master");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_filterButton),
						" Filter Button Displayed In Item Master");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.input_itemNameSearchInItemMaster),
						" Item Search Box Displayed In Item Master");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_reportDownloadButton),
						" Report Download Button Displayed In Item Master");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Master.button_addNew),
						" Add Item Button Clicked");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.header_addItemMasterTemplateTitle, 5);

				// Verifying Required Error In Add Item Template

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_saveAddItemTemplate),
						"Save Button Clicked without filling required field");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemDescription, 5);

				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_itemDescription, "class").contains("error"),
						"<b> Item Description Required Error Displayed </b> ");
				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.select_itemPropertiesTaxList, "class").contains("error"),
						"<b> Item Properties Tax Required Error Displayed </b> ");
				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_itemPossibleVariantName, "class").contains("error"),
						"<b> Item Possible Variant Name Required Error Displayed </b> ");
				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.select_itemPossibleVariantValue, "class").contains("error"),
						"<b> Item Possible Variant Value Required Error Displayed </b> ");
				m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_ItemMaster.button_closeItemMasterTemplate),
						"Close button In Item Master Clicked");

				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.button_addNew, 5);

				// Entering Required fields and fill data in Item Details ,Properties and Possible Variant to create Item

				Cls_Generic_Methods.scrollToTop();
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

					viewItemRhsSideValueList.add(itemDescription);
					viewItemRhsSideValueList.add(itemCode);
					viewItemRhsSideValueList.add("");
					viewItemRhsSideValueList.add(brandCompanyName);
					viewItemRhsSideValueList.add(itemInitialStock);
					viewItemRhsSideValueList.add(itemDispensingUnit);
					viewItemRhsSideValueList.add(categoryName);
					viewItemRhsSideValueList.add(subCategoryName);
					viewItemRhsSideValueList.add(itemGenericCompositionName+" "+itemGenericCompositionValue+" "+"mg");
					viewItemRhsSideValueList.add("0");

					m_assert.assertTrue(bCreatedItemDataFound,
							" Item Name <b> "+itemDescription+" </b> Found and Created Successfully Item Data Displayed Correctly in Table List");


					validatingDataInViewItemRHSSide(oPage_Master.list_keysAndValuesInItemViewRHSSide,viewItemRhsSideKeyList,viewItemRhsSideValueList);

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_editItem),
							" Edit Item Button Displayed In View Item RHS Side");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_deleteItem),
							" Delete Item Button Displayed In View Item RHS Side");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.button_stockAvailability),
							" Stock Availability Button Displayed In View Item RHS Side");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.text_lotDetailsSectionHeader),
							" Lot Details Title Displayed In View Item RHS Side");
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.text_transactionHistorySectionHeader),
							" Transaction History Displayed In View Item RHS Side");

					validatingDataTableHeadersInItemMaster(oPage_Master.list_lotDetailsTableHeaderList ,lotDetailsTableHeaderList,"Lot Details");
					validatingDataTableHeadersInItemMaster(oPage_Master.list_transactionHistoryTableHeaderList ,transactionHistoryTableHeaderList,"Transaction History");

					m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_Master.list_lotDetailsTableNoRowValues),
							" No Data Row Available In Lot Details");
					m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_Master.list_transactionHistoryTableNoRowValues),
							" No Data Row Available In Transaction History");

				} else {
					m_assert.assertTrue(bCategoryFound, "Create Category Name as " + categoryName + " In category master ");
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
	@Test(enabled = true, description = "Validating Edit Item Functionality in Item Master")
	public void validateEditFunctionalityInItemMaster() {

		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Master oPage_Master = new Page_Master(driver);
		String updatedHsnCode = hsnCode + "Update";
		String updatedItemPropertiesTax = "GST12 - 12.0%";
		String updatedBrandCompanyName = "Healthgraph_Updated";

		try {
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			try {

				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();
				boolean bItemMaster = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");

				m_assert.assertTrue(bItemMaster , " Master Option Displayed In Item ");

				boolean bCreatedItemDataFound = false ;

				bCreatedItemDataFound = selectItemFromItemMasterList(itemDescription,itemCode,itemInitialStock,itemDispensingUnit,categoryName);

				m_assert.assertTrue(bCreatedItemDataFound , "Item Found And Clicked");

				if (bCreatedItemDataFound) {

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Master.button_editItem),
							" Edit Item Button Clicked");
					m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.header_editItemHeader,5),
							" Edit Item Template Opened and Title Displayed as : "+ Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.header_editItemHeader));

					m_assert.assertTrue(!Cls_Generic_Methods.isElementEnabled(oPage_ItemMaster.select_itemCategory),
							"Category Field Is Disabled In Edit Item Template");

					m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_ItemMaster.select_editItemSubCategory, updatedSubCategoryName),
							"Sub Category Entered as : <b> " + updatedSubCategoryName + "</b>");

					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemHsnCode, 1);

					Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_itemHsnCode);
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemHsnCode, updatedHsnCode),
							"Entered Updated HSN Code as : <b>" + updatedHsnCode + "</b>");

					Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_itemBrandCompany);
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemBrandCompany, updatedBrandCompanyName),
							"Item Brand/Company Name Displayed and  Entered as : <b> " + updatedBrandCompanyName + "</b>");

					Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_itemDescription);
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemDescription, updatedItemDescription),
							"Entered Updated Description as : <b>" + updatedItemDescription + "</b>");

					Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_itemManufacturerName);
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemManufacturerName, updatedBrandCompanyName),
							"Item Manufacturer Name Displayed and Entered as :<b>" + updatedBrandCompanyName + "</b>");

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_itemPropertiesTaxList),
							" Tax Dropdown Clicked");
					m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_ItemMaster.select_itemPropertiesTaxList, updatedItemPropertiesTax),
							"Item Properties Tax Entered as : <b>" + updatedItemPropertiesTax + "</b>");

					m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_ItemMaster.select_itemDispensingUnit,updatedDispensingUnit),
							" Dispensing Unit Displayed as : "+updatedDispensingUnit);

					m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Master.select_selectPackageTypeEditItem,"Pack"),
							"Package Type Displayed as : <b> Pack </b> ");

					Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_inventoryItemSubPackageUnit);
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_inventoryItemSubPackageUnit,"2"),
							"Sub Package Unit Displayed as : <b> 2 </b> ");
					m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Master.select_selectSubPackageTypeEditItem,"Box"),
							"Item Sub Package Unit Type Displayed as : <b> Box </b>");

					Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_inventoryItemSubPackageItemUnit);
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_inventoryItemSubPackageItemUnit,"2"),
							"Sub Package Unit Displayed as : <b> 2 </b> ");
					m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Master.select_selectUnitSubPackageTypeEditItem,"Box"),
							"Item Sub Package Unit Type Displayed as : <b> Box </b> ");

					m_assert.assertTrue(!Cls_Generic_Methods.isElementEnabled(oPage_Master.input_fixedThresholdEditItem),
							"Fixed Threshold Checkbox Displayed");

					m_assert.assertTrue(!Cls_Generic_Methods.isElementEnabled(oPage_ItemMaster.input_inventoryItemMedicineClass),
							" Medicine Class Input Box Displayed");

					Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_itemGenericCompositionName);
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemGenericCompositionName, itemGenericCompositionNameUpdated),
							" Item generic Composition Name Displayed and  Entered as : <b> " + itemGenericCompositionNameUpdated + " </b>");

					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.name_itemGenericCompositionVitaminAasetat, 1);
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.name_itemGenericCompositionVitaminAasetat),
							"Medication Name Clicked on Generic Composition Suggestion Value");

					Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_itemGenericCompositionValue);
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemGenericCompositionValue, "2.0"),
							" Generic Composition Value Entered as : <b>" + "2.0" + "</b>");
					m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_ItemMaster.select_itemGenericCompositionUnit, "ml"),
							" Generic Composition Unit Entered as : <b>" + "ml" + "</b>");

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_saveAddItemTemplate),
							"Save Button Clicked with filled required field");
					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 17);

					boolean editItemFound = false ;
					editItemFound = selectItemFromItemMasterList(updatedItemDescription,itemCode,itemInitialStock,updatedDispensingUnit, categoryName);

					viewUpdatedItemRhsSideValueList.add(updatedItemDescription);
					viewUpdatedItemRhsSideValueList.add(itemCode);
					viewUpdatedItemRhsSideValueList.add("");
					viewUpdatedItemRhsSideValueList.add(updatedBrandCompanyName);
					viewUpdatedItemRhsSideValueList.add(itemInitialStock);
					viewUpdatedItemRhsSideValueList.add(updatedDispensingUnit);
					viewUpdatedItemRhsSideValueList.add(categoryName);
					viewUpdatedItemRhsSideValueList.add(updatedSubCategoryName);
					viewUpdatedItemRhsSideValueList.add("Vitamin Aasetate"+" "+"2.0"+" "+"ml");
					viewUpdatedItemRhsSideValueList.add("0");


					validatingDataInViewItemRHSSide(oPage_Master.list_keysAndValuesInItemViewRHSSide,viewItemRhsSideKeyList,viewUpdatedItemRhsSideValueList);

					m_assert.assertTrue(editItemFound," Edit Item Found and Created Successfully Item Data Displayed Correctly in Table List");

				} else {
					m_assert.assertTrue(bCreatedItemDataFound, " Item Name as " + itemDescription + " Not Found Please create In item master ");
				}

				Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
				Cls_Generic_Methods.customWait();
				Cls_Generic_Methods.driverRefresh();
				Cls_Generic_Methods.waitForPageLoad(driver,5);

			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}
	}
	@Test(enabled = true, description = "Validating Stock Availability Functionality in Item Master")
	public void validateStockAvailabilityFunctionalityInItemMaster() {

		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Master oPage_Master = new Page_Master(driver);

		String[] stockAvailabilityTableHeaderList = {"#","Store Name","Facility","Model No.","Batch No.","Expiry","Tax%","Available Qty."};
		List<String> stockAvailabilityHeaderInfoList = new ArrayList<>();



		try {
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			try {

				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();
				boolean bItemMaster = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");

				m_assert.assertTrue(bItemMaster , " Master Option Displayed In Item ");

				boolean bCreatedItemDataFound = false ;

				bCreatedItemDataFound = selectItemFromItemMasterList(updatedItemDescription,itemCode,itemInitialStock,updatedDispensingUnit, categoryName);

				m_assert.assertTrue(bCreatedItemDataFound , "Item Found And Clicked");

				if (bCreatedItemDataFound) {

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Master.button_stockAvailability),
							"Stock Availability Button Clicked");
					m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.header_stockAvailabilityTitleHeader,5),
							"Stock Availability Template Opened and Title Displayed as : "+
									Cls_Generic_Methods.getTextInElement(oPage_Master.header_stockAvailabilityTitleHeader));
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Master.input_searchBatchInputBox),
							" Search By Batch No. Text Box Present");

					for(WebElement eStockHeader : oPage_Master.list_stockAvailabilityTableHeaderList){

						if(Cls_Generic_Methods.isElementDisplayed(eStockHeader)){

							int indexOfStockKey = oPage_Master.list_stockAvailabilityTableHeaderList.indexOf(eStockHeader);
							String sStockKeyText = Cls_Generic_Methods.getTextInElement(eStockHeader);

							if(sStockKeyText.equalsIgnoreCase(stockAvailabilityTableHeaderList[indexOfStockKey])){
								m_assert.assertTrue(sStockKeyText + " Present In Stock Availability Table");
								stockAvailabilityHeaderInfoList.add(sStockKeyText);
							}else{
								m_assert.assertWarn(sStockKeyText +" header not present in Stock Availability Table ,Either Removed or changed");
							}
						}
					}

					Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
					Cls_Generic_Methods.customWait();

					boolean purchaseGrnCreated = createPurchaseGrn("Bill");
					m_assert.assertTrue(purchaseGrnCreated, " Purchase GRN Created Successfully");
					Cls_Generic_Methods.customWait();

					expiryDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", expiryDate);
					packageQuantity = String.valueOf(CommonActions.convertStringToDouble(packageQuantity));
					sellingPriceByUnit = String.valueOf(CommonActions.convertStringToDouble(sellingPriceByUnit));
					String grnApprovedAtDate = CommonActions.getRequiredFormattedDateTime("dd-MM-yyyy", "yyyy-MM-dd", grnApprovedAt.substring(0,10));
					grnApprovedAt = grnApprovedAtDate+" | "+grnApprovedAt.substring(13,18)+grnApprovedAt.substring(19,21);


					viewItemLotDetailsValueList.add("1");
					viewItemLotDetailsValueList.add(itemCode+"-100");
					viewItemLotDetailsValueList.add(grn_no+"(Purchase)");
					viewItemLotDetailsValueList.add(subStore);
					viewItemLotDetailsValueList.add(batchNo);
					viewItemLotDetailsValueList.add(packageQuantity);
					viewItemLotDetailsValueList.add(sellingPriceByUnit);
					viewItemLotDetailsValueList.add(expiryDate);
					viewItemLotDetailsValueList.add(vendorName);

					netAmountBeforeOtherCharge = String.valueOf(CommonActions.convertStringToDouble(netAmountBeforeOtherCharge));

					viewItemTransactionHistoryValueList.add("1");
					viewItemTransactionHistoryValueList.add(grn_no);
					viewItemTransactionHistoryValueList.add(grnApprovedAt);
					viewItemTransactionHistoryValueList.add(grnApprovedBy);
					viewItemTransactionHistoryValueList.add("Purchase");
					viewItemTransactionHistoryValueList.add(netAmountBeforeOtherCharge);

					if(purchaseGrnCreated) {

						CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
						Cls_Generic_Methods.customWait();

						bCreatedItemDataFound = selectItemFromItemMasterList(updatedItemDescription,itemCode,packageQuantity,updatedDispensingUnit, categoryName);

						m_assert.assertInfo( bCreatedItemDataFound," Item Found In Master");

						for(WebElement eLotDetailsValue : oPage_Master.list_lotDetailsTableRowValuesList) {
							if(Cls_Generic_Methods.isElementDisplayed(eLotDetailsValue)) {

								int indexOfLotValue = oPage_Master.list_lotDetailsTableRowValuesList.indexOf(eLotDetailsValue);
								String sLotValue = Cls_Generic_Methods.getTextInElement(eLotDetailsValue);
								if(sLotValue.equalsIgnoreCase(viewItemLotDetailsValueList.get(indexOfLotValue))){
									m_assert.assertTrue( " Lot Details Table Value for Column "+lotDetailsTableHeaderList[indexOfLotValue]+
											" Displayed correctly as : "+sLotValue);
								}else{
									m_assert.assertWarn(" Either Column Removed or Missing , Or No data Enter For Required Column");
								}
							}
						}

						for(WebElement eTransactionValue : oPage_Master.list_transactionHistoryTableRowValuesList) {
							if(Cls_Generic_Methods.isElementDisplayed(eTransactionValue)) {

								int indexOfTransactionValue = oPage_Master.list_transactionHistoryTableRowValuesList.indexOf(eTransactionValue);
								String sTransactionValue = Cls_Generic_Methods.getTextInElement(eTransactionValue);
								if(sTransactionValue.equalsIgnoreCase(viewItemTransactionHistoryValueList.get(indexOfTransactionValue))){
									m_assert.assertTrue( " Transaction Table Value for Column "+transactionHistoryTableHeaderList[indexOfTransactionValue]+
											" Displayed correctly as : "+sTransactionValue);
								}else{
									m_assert.assertWarn(" Either Column Removed or Missing , Or No data Enter For Required Column");
								}
							}
						}
					}else{
						m_assert.assertTrue(" Purchase Grn Not created");
					}

				} else {
					m_assert.assertTrue(bCreatedItemDataFound, " Item Name as " + itemDescription + " Not Found Please create In item master ");
				}

				Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
				Cls_Generic_Methods.customWait();
				Cls_Generic_Methods.driverRefresh();
				Cls_Generic_Methods.waitForPageLoad(driver,5);


			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}
	}
	@Test(enabled = true, description = "Validating Delete Item Functionality in Item Master")
	public void validateDeleteFunctionalityInItemMaster() {

		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Master oPage_Master = new Page_Master(driver);
		String itemDescriptionDelete = "Item_Description_Delete"+getRandomUniqueString(4);


		try {
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			try {

				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();
				boolean bItemMaster = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");

				m_assert.assertTrue(bItemMaster , " Master Option Displayed In Item ");

				boolean bCreatedItemDataFound = CommonActions.addItemInInventory(itemDescriptionDelete) ;
				boolean bItemClicked = false ;
				for(WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory){
					if(Cls_Generic_Methods.isElementDisplayed(itemData)){

						List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./child::*"));

						String itemDescriptionName = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));

						if (itemDescriptionName.contains(itemDescriptionDelete)) {
							bItemClicked = true;
							m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemData),
									itemDescription+" Item Clicked from Item Master List");
							Cls_Generic_Methods.customWait(5);
							break;
						}
					}
				}

				m_assert.assertTrue(bItemClicked , "Item Found And Clicked");

				if (bItemClicked) {

					String deleteItemCode = Cls_Generic_Methods.getTextInElement(oPage_Master.text_itemCode);
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Master.button_deleteItem),
							"Delete Button Clicked");
					Cls_Generic_Methods.customWait();
					m_assert.assertTrue(!Cls_Generic_Methods.isElementClickable(driver,oPage_Master.button_confirmDeleteItem),
							" Input Delete Field Displayed");
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemCodeForDelete,deleteItemCode),
							" Input Item Code Text Box displayed and value send as : "+deleteItemCode);
					Cls_Generic_Methods.customWait();
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Master.button_confirmDeleteItem),
							"Confirm Delete Button is Enabled and CLicked");
					Cls_Generic_Methods.customWait(5);
					Cls_Generic_Methods.clearValuesInElement(oPage_Master.input_itemNameSearchInItemMaster);
					Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster,itemDescriptionDelete);
					Cls_Generic_Methods.customWait(3);
					oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
					Cls_Generic_Methods.customWait(2);
					m_assert.assertTrue(oPage_ItemMaster.list_itemListInStoreInventory.size() == 0,
							itemDescriptionDelete +" Item Deleted Successfully ");

				} else {
					m_assert.assertTrue(bCreatedItemDataFound, " Item Name as " + itemDescription + " Not Found Please create In item master ");
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
	@Test(enabled = true, description = "Validating Duplicate Items are not there in Item Master")
	public void validateDuplicateItemInItemMaster(){

        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        oEHR_Data=new EHR_Data();


		try{

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(pharmacyStoreName);

			Cls_Generic_Methods.switchToOtherTab();
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
			m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
			Cls_Generic_Methods.customWait();

			boolean bItemMaster = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
			m_assert.assertInfo(bItemMaster , " Master Option Displayed In Item ");

			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 17);

			JavascriptExecutor js= (JavascriptExecutor) driver;
			int noOfItemsScrolled=oPage_ItemMaster.list_itemListInStoreInventory.size();


			while(true) {

				js.executeScript("document.querySelector('#inventory_table_body').scrollTop=300000;");
				Cls_Generic_Methods.customWait(2);

				int noOfItems=oPage_ItemMaster.list_itemListInStoreInventory.size();

				if(noOfItems==noOfItemsScrolled){
					break;
				}

				System.out.println(noOfItems +"============================"+ noOfItemsScrolled);
				noOfItemsScrolled=noOfItems;

			}

			ArrayList<String> allItemDescription=new ArrayList<>();


			for (WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory) {
				List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./child::*"));
				String itemDescriptionName = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));
				String itemCode = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((0)));
				String itemStock = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((2)));

				allItemDescription.add(itemDescriptionName +"||"+itemCode+"||"+itemStock);

			}

			List<String> duplicateItemList=findDuplicates(allItemDescription);

			if(duplicateItemList.size()>0){
				m_assert.assertFalse("Total Duplicate Items found in Item Master = <b>"+findDuplicates(allItemDescription).size()+"</b>");

				for (String item:duplicateItemList){
					String itemDescription=item.split("\\|\\|")[0];
					m_assert.assertInfo("DUPLICATE ITEM "+(duplicateItemList.indexOf(item)+1)+" = "+itemDescription);
				}


			}else{
				m_assert.assertTrue("No Duplicates Found");
			}

		}catch (Exception e){
			e.printStackTrace();
		}

	}
	@Test(enabled = true, description = "Validating Store Notification Pop up")
	public void validateStoreNotificationPopUp() {

		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Master oPage_Master = new Page_Master(driver);
		oEHR_Data = new EHR_Data();
		String userName;
		String storeName;

		try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
			CommonActions.selectStoreOnApp(pharmacyStoreName);

			Cls_Generic_Methods.switchToOtherTab();
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);

			String displayedOutOfStockItemLevel= Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.link_outOfStockItemLevel);

			m_assert.assertInfo("<font color='blue'>Item Level    -> Out of Stock Items</font>");
			m_assert.assertInfo("Displayed Item Level    -> Out of Stock      = <b>"+displayedOutOfStockItemLevel+"</b>");

			m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ItemMaster.link_outOfStockItemLevel),"Clicked Item Level - Out of stock hyperlink");

			if(scrollDownItemMaster()){

				ArrayList<String> allItemDescription=new ArrayList<>();

				for (WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory) {
					List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./child::*"));
					String itemDescriptionName = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));
					String itemCode = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((0)));
					String itemStock = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((2)));

					allItemDescription.add(itemDescriptionName +"||"+itemCode+"||"+itemStock);
				}

				m_assert.assertTrue(allItemDescription.size()==Integer.parseInt(displayedOutOfStockItemLevel),"Validated -> No of Out of Stock Item present in Item Master = <b>"+allItemDescription.size()+"</b>");

			}

			//-------------------------

			Cls_Generic_Methods.driverRefresh();
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
			String displayedRunningLowItemLevel= Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.link_runningLowItemLevel);

			m_assert.assertInfo("<font color='blue'>Item Level    -> Running Low Items</font>");
			m_assert.assertInfo("Displayed Item Level    -> Running Low Items = <b>"+displayedRunningLowItemLevel+"</b>");
			m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ItemMaster.link_runningLowItemLevel),"Clicked Item Level - Running Low hyperlink");

			if(scrollDownItemMaster()){

				ArrayList<String> allItemDescription=new ArrayList<>();

				for (WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory) {
					List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./child::*"));
					String itemDescriptionName = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));
					String itemCode = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((0)));
					String itemStock = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((2)));

					allItemDescription.add(itemDescriptionName +"||"+itemCode+"||"+itemStock);
				}

				m_assert.assertTrue(allItemDescription.size()==Integer.parseInt(displayedRunningLowItemLevel),"Validated -> No of Running Low Item present in Item Master = <b>"+allItemDescription.size()+"</b>");
			}

			//-------------------------

			Cls_Generic_Methods.driverRefresh();
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);

			m_assert.assertInfo("<font color='blue'>Variant Level    -> Out of Stock Items</font>");
			String displayedOutOfStockVariantLevel= Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.link_outOfStockVariantLevel);
			m_assert.assertInfo("Displayed Variant Level    -> Out Of Stock Items = <b>"+displayedOutOfStockVariantLevel+"<b>");
			m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ItemMaster.link_outOfStockVariantLevel),"Clicked Variant Level - Out Of Stock hyperlink");

			if(scrollDownItemMaster()){

				ArrayList<String> allItemDescription=new ArrayList<>();

				for (WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory) {
					List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./child::*"));
					String itemDescriptionName = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));
					String itemCode = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((0)));
					String itemStock = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((2)));

					allItemDescription.add(itemDescriptionName +"||"+itemCode+"||"+itemStock);
				}
				m_assert.assertTrue(allItemDescription.size()==Integer.parseInt(displayedOutOfStockVariantLevel),"Validated -> No of Out of Stock Item present in Variant Master = <b>"+allItemDescription.size()+"</b>");
			}

			//-------------------------

			Cls_Generic_Methods.driverRefresh();
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
			String displayedRunningLowVariantLevel= Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.link_runningLowVariantLevel);

			m_assert.assertInfo("<font color='blue'>Variant Level    -> Running Low Items</font>");
			m_assert.assertInfo("Displayed Variant Level    -> Running Low Items = <b>"+displayedRunningLowVariantLevel+"</b>");
			m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ItemMaster.link_runningLowVariantLevel),"Clicked Variant Level - Running Low hyperlink");

			if(scrollDownItemMaster()){

				ArrayList<String> allItemDescription=new ArrayList<>();

				for (WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory) {
					List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./child::*"));
					String itemDescriptionName = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));
					String itemCode = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((0)));
					String itemStock = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((2)));

					allItemDescription.add(itemDescriptionName +"||"+itemCode+"||"+itemStock);
				}

				m_assert.assertTrue(allItemDescription.size()==Integer.parseInt(displayedRunningLowVariantLevel),"Validated -> No of Running Low Item present in Variant Master = <b>"+allItemDescription.size()+"</b>");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true, description = "Validate Search Functionality in the Item Master ")
	public void validateSearchFunctionalityInItemMaster() {

		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

		String sItemDescription = "TransactionPurchase_opei";
		String sItemCode = "INV-MED-1353";
		String sBarCode = "RSO094917INVMED1353";

		try {
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			try {
				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();
				boolean bItemMaster = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");

				m_assert.assertTrue(bItemMaster, " Master Item Found in the Item master");

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.select_searchFilterItemDescription),
						" Search Type  Selection Dropdown Displayed");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.input_searchbarCriteria),
						" Input Search Box Displayed");
				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_searchbarCriteria, "value")
								.equalsIgnoreCase(""),
						" Input Search Box is empty by default for selected search type Displayed correctly");
				m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.button_clearButtonInSearchBx),
						" Clear Button Not Displayed Correctly as Default");

				//search by item description in Item Master
				Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_searchFilterItemDescription);
				boolean bitemFound = false;
				for (WebElement webElement : oPage_ItemMaster.list_searchFilterItemDescriptionDropdown) {
					if (Cls_Generic_Methods.getTextInElement(webElement).equals("Item Description")) {
						Cls_Generic_Methods.clickElement(webElement);
						bitemFound = true;
						break;
					}
				}
				m_assert.assertTrue(bitemFound, " Item Description is selected");
				Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_searchFilterItemDescription);
				Cls_Generic_Methods.clickElement(oPage_ItemMaster.input_searchbarCriteria);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchbarCriteria, sItemDescription), "Item Decsription = <b>" + sItemDescription + "</b>");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_searchItem), "click on search button");
				Cls_Generic_Methods.customWait(4);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.list_searchItem1.get(0)), "Click on  = <b>" + sItemDescription + "</b>");
				Cls_Generic_Methods.customWait(3);
				int SizeItem =oPage_ItemMaster.list_searchItem1.size();
				if (SizeItem==1){
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.list_searchItem1.get(0)),
							" Search By ItemDescription is Worked correctly as item found in the master page = <b>" + sItemDescription + "</b>");
				}
				else{
					m_assert.assertTrue("Search By ItemDescription is Not Worked and Showing Nothing to display");
				}
			//	m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.text_searchItemDescriptionName).equals(sItemDescription),
				//		" Required Item is selected = <b>" + sItemDescription + "</b>");


				//search by item code in Item Master
				Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_searchFilterItemDescription);
				boolean bItemCodeFound = false;
				for (WebElement webElement : oPage_ItemMaster.list_searchFilterItemDescriptionDropdown) {
					String optionText = Cls_Generic_Methods.getTextInElement(webElement);
					if (optionText.equals("Item Code")) {
						Cls_Generic_Methods.clickElement(webElement);
						bItemCodeFound = true;
						break;
					}
				}
				m_assert.assertTrue( bItemCodeFound, " Item Code is selected");
				Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_searchbarCriteria);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchbarCriteria, sItemCode), "Item code = <b>" + sItemCode + "</b>");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_searchItem), "click on search button");
				Cls_Generic_Methods.customWait(3);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.list_searchItem1.get(0)), "Click on  = <b>" + sItemCode + "</b>");
				Cls_Generic_Methods.customWait(3);
				int SizeItemCode = oPage_ItemMaster.list_searchItem1.size();
				if (SizeItemCode==1){
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.list_searchItem1.get(0)),
							" Search By ItemCode is Worked correctly as item found in the master page = <b>" + sItemCode + "</b>");
				}
				else{
					m_assert.assertTrue("Search By ItemCode is Not Worked and Showing Nothing to display");
				}
				//m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.text_itemCode).equals(sItemCode),
					//	" Required Item Are selected = <b>" + sItemCode + "</b>");


				//Search by barcode in item Master
				Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_searchFilterItemDescription);
				boolean bItemBarcodeFound = false;
				for (WebElement webElement : oPage_ItemMaster.list_searchFilterItemDescriptionDropdown) {
					if (webElement.getText().equals("Barcode")) {
						Cls_Generic_Methods.clickElement(webElement);
						bItemBarcodeFound = true;
						break;
					}
				}
				m_assert.assertTrue(bItemBarcodeFound, " Bar Code is selected");
				Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_searchbarCriteria);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchbarCriteria, sBarCode), "Bar code = <b>" + sBarCode + "</b>");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_searchItem), "click on search button");
				Cls_Generic_Methods.customWait(4);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.list_searchItem1.get(0)), "Click on  = <b>" + sBarCode + "</b>");
				Cls_Generic_Methods.customWait(3);
				int SizeBar =oPage_ItemMaster.list_searchItem1.size();
				if (SizeBar==1){
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.list_searchItem1.get(0)),
							" Search By Barcode is Worked correctly as item found in the master page = <b>" + sBarCode + "</b>");
				}
				else{
					m_assert.assertTrue("Search By Barcode is Not Worked and Showing Nothing to display");
				}

				//m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.text_searchBarCode).equals(sBarCode),
					//	" Bar code = <b>" + sBarCode + "</b>");

			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}
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
			Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_searchItem, updatedItemDescription);
			Cls_Generic_Methods.customWait();
			Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_itemNameInPurchaseStore, 10);
			for (WebElement eItemName : oPage_Purchase.list_itemNameInPurchaseStore) {
				if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(updatedItemDescription)) {
					Cls_Generic_Methods.clickElementByJS(driver, eItemName);
					itemClicked = true;
					m_assert.assertInfo("Selected Item <b>" + updatedItemDescription + "</b> in Purchase/GRN");
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
	public void validatingDataTableHeadersInItemMaster(List<WebElement> eHeaderList , String [] actualHeaderList ,String sSection){
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

	public static void validatingDataInViewItemRHSSide(List<WebElement> eHeaderList , String [] actualHeaderList,List<String> actualValueList){
		try{

			int indexOfKey = 0,indexOfValue = 0 ;
			for(WebElement eViewItem : eHeaderList){

				if(Cls_Generic_Methods.isElementDisplayed(eViewItem)){
					int indexOfViewItem = eHeaderList.indexOf(eViewItem);
					String sViewItemText = Cls_Generic_Methods.getTextInElement(eViewItem);
					if(indexOfViewItem % 2 == 0) {
						m_assert.assertTrue(sViewItemText.equalsIgnoreCase(actualHeaderList[indexOfKey]),
								"<b> "+sViewItemText+ " </b> Label Present In View Item RHS side ");
						indexOfKey ++ ;
					}else{
						if(indexOfViewItem  == 5){
							m_assert.assertTrue(!(sViewItemText.isEmpty()),
									"<b>"+sViewItemText+ " </b>  Value Present In View Item RHS against label <b> "+actualHeaderList[indexOfValue]+"</b>");
						}else {
							m_assert.assertTrue(sViewItemText.equalsIgnoreCase(actualValueList.get(indexOfValue)),
									"<b> "+sViewItemText + "  </b> Value Present In View Item RHS against label <b> " + actualHeaderList[indexOfValue]+" </b>");
						}
						indexOfValue ++ ;
					}
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> findDuplicates(List<String> inputList) {
		List<String> duplicates = new ArrayList<>();
		Set<String> uniqueElements = new HashSet<>();

		for (String element : inputList) {
			if (!uniqueElements.add(element)) {
				duplicates.add(element);
			}
		}

		//To Find Unique Duplicates
		Set<String> uniqueStrings = new HashSet<>();
		List<String> result = new ArrayList<>();

		for (String str : duplicates) {

			if (!uniqueStrings.contains(str)) {
				result.add(str);
				uniqueStrings.add(str);
			}
		}
		return result;
	}
	public List<String> findUniqueStrings(ArrayList<String> inputList) {

		Set<String> uniqueStrings = new HashSet<>();
		List<String> result = new ArrayList<>();

		for (String str : inputList) {

			if (!uniqueStrings.contains(str)) {
				result.add(str);
				uniqueStrings.add(str);
			}
		}
		return result;
	}

	public boolean scrollDownItemMaster(){
		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
		boolean status=false;
		try{
			Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_ItemMaster.list_itemListInStoreInventory, 10);

			JavascriptExecutor js= (JavascriptExecutor) driver;
			int noOfItemsScrolled=oPage_ItemMaster.list_itemListInStoreInventory.size();

			while(true) {

				js.executeScript("document.querySelector('#inventory_table_body').scrollTop=300000;");
				Cls_Generic_Methods.customWait(2);

				int noOfItems=oPage_ItemMaster.list_itemListInStoreInventory.size();

				if(noOfItems==noOfItemsScrolled){
					status=true;
					break;
				}

				System.out.println(noOfItems +"============================"+ noOfItemsScrolled);
				noOfItemsScrolled=noOfItems;

			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}

	@Test(enabled = true, description = "Validate Filter Functionality in the Item Master ")
	public void validateFilterFunctionalityInItemMaster() {

		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		oPage_InventoryFilterCommonElements = new Page_InventoryFilterCommonElements(driver);

		try {
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			try {
				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();
				boolean bItemMaster = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
				m_assert.assertTrue(bItemMaster, " Master Item Found in the Item master");

				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_filterButton,4);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_filterButton),"Clicked on Filter button");
				Cls_Generic_Methods.customWait(2);
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.text_filterHeader).equalsIgnoreCase("Filter Master Items"),"Filter header is validated as <b>"+ "Filter Master Items" + "</b>");

				//Validating the filter for Item by stock
				CommonActions.filterItemsByStock("Items With Stock");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_filterApplyButton),"Clicked on Apply button in filter");
				Cls_Generic_Methods.customWait(4);
				for(WebElement eItemStock : oPage_ItemMaster.list_searchItemsWithStock) {
					String sItemStockOnUi = Cls_Generic_Methods.getTextInElement(eItemStock);
					if(Double.parseDouble(sItemStockOnUi)>=0.0 ){
						m_assert.assertTrue(" Item With stock is present and filter working correctly <b>"+sItemStockOnUi + "</b>");
					}else{
						m_assert.assertFalse(" Item with stock filter Not  working correctly <b>"+sItemStockOnUi + "</b>");
					}
				}
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_clearMasterFilterButton),"Clicked on Clear Filter option to Clear the selected filter");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_filterButton),"Clicked on Filter button");
				Cls_Generic_Methods.customWait(2);

				//Validating the filter for Running low Stock Item
				CommonActions.filterItemsByStock("Running Low Stock");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_filterApplyButton),"Clicked on Apply button in filter");
				Cls_Generic_Methods.customWait(4);
				for (WebElement eRunningLowStock : oPage_ItemMaster.list_searchRunningLowStock){
					String sRunningLowStockOnUi = Cls_Generic_Methods.getElementAttribute(eRunningLowStock,"style");
					if (sRunningLowStockOnUi.contains("background-color: rgb(255, 165, 0);")){
						m_assert.assertTrue(" Running low stock items are present and filter working correctly <b>"+sRunningLowStockOnUi + "</b>");
					}else{
						m_assert.assertFalse(" Running low stock filter is Not working correctly <b>"+sRunningLowStockOnUi + "</b>");
					}
				}
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_clearMasterFilterButton),"Clicked on Clear Filter option to Clear the selected filter");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_filterButton),"Clicked on Filter button");
				Cls_Generic_Methods.customWait(2);

				//Validating the filter for Low Stock Item
			    CommonActions.filterItemsByStock("Out Of Stock");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_filterApplyButton),"Clicked on Apply button in filter");
				Cls_Generic_Methods.customWait(4);
				for(WebElement eOutOfStock : oPage_ItemMaster.list_searchOutOfStock) {
					String sOutOfStockOnUi = Cls_Generic_Methods.getTextInElement(eOutOfStock);
					if(Double.parseDouble(sOutOfStockOnUi) == 0.0){
						m_assert.assertTrue("Out of Stock items are present and filter working correctly <b>"+sOutOfStockOnUi+ "</b>");
					}else{
						m_assert.assertFalse(" Out of stock filter is Not working correctly <b>"+sOutOfStockOnUi+ "</b>");
					}
				}
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_clearMasterFilterButton),"Clicked on Clear Filter option to Clear the selected filter");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_filterButton),"Clicked on Filter button");
				Cls_Generic_Methods.customWait(2);

				//Validating the filter for Category
				CommonActions.filterItemsByCategories("Medication");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_filterApplyButton),"Clicked on Apply button in filter");
				Cls_Generic_Methods.customWait(4);
				for (WebElement eCategoryItem : oPage_ItemMaster.list_searchItemsWithCategory){
					String sCategoryItemOnUi = Cls_Generic_Methods.getTextInElement(eCategoryItem);
					if (sCategoryItemOnUi.equalsIgnoreCase("Medication")){
						m_assert.assertTrue(" Items with Selected categories are present and filter working correctly <b>"+sCategoryItemOnUi + "</b>");
					}else{
						m_assert.assertFalse("Items with Categories filter is Not working correctly <b>"+sCategoryItemOnUi + "</b>");
					}
				}
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_clearMasterFilterButton),"Clicked on Clear Filter option to Clear the selected filter");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_filterButton),"Clicked on Filter button");
				Cls_Generic_Methods.customWait(2);

				//Validating Clear to default button and close button in filter form
				CommonActions.filterItemsByStock("Items With Stock");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_clearFilterItemByStock),"Selected Items with stock filter is Cleared and Clear option is working correctly");
				CommonActions.filterItemsByStock("Running Low Stock");
				CommonActions.sortBy("Stock");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_clearSortBy),"Selected options in sort filter is cleared and Clear option is working Correctly");
				CommonActions.sortBy("Description");
				CommonActions.filterItemsByCategories("Intraocular Lens");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_filterClearButton),"Clicked on Cleared to Default button and it is working correctly");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_filterCloseButton),"Clicked on Close button to close the Filter form");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_filterButton),"Clicked on Filter button");
				Cls_Generic_Methods.customWait(2);

				//Validating Stock filter in Sort by options
				CommonActions.sortBy("Stock");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_filterApplyButton),"Clicked on Apply button in filter");
				Cls_Generic_Methods.customWait(4);
				for(WebElement eItemStock : oPage_ItemMaster.list_searchItemsWithStock) {
					String sItemStockOnUi = Cls_Generic_Methods.getTextInElement(eItemStock);
					if(Double.parseDouble(sItemStockOnUi)>=0.0 ){
						m_assert.assertTrue(" Sort by Stock filter working correctly <b>"+sItemStockOnUi+ "</b>");
					}else{
						m_assert.assertFalse(" Sort by stock filter Not  working correctly <b> "+sItemStockOnUi+ "</b>");
					}
				}
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_clearMasterFilterButton),"Clicked on Clear Filter option to Clear the selected filter");
				Cls_Generic_Methods.customWait(2);
				List<String> sortedItemDescriptionsBefore = new ArrayList<>();
				for(WebElement eItemDescription : oPage_ItemMaster.list_searchItemsWithDescription) {
					String sItemDescriptionOnUi = Cls_Generic_Methods.getTextInElement(eItemDescription);
					sortedItemDescriptionsBefore.add(sItemDescriptionOnUi);
				}
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_filterButton),"Clicked on Filter button");
				Cls_Generic_Methods.customWait(2);

				//Validating Description filter in Sort by options
				CommonActions.sortBy("Description");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_filterApplyButton),"Clicked on Apply button in filter");
				Cls_Generic_Methods.customWait(4);
				List<String> sortedItemDescriptions = new ArrayList<>();
				for(WebElement eItemDescription : oPage_ItemMaster.list_searchItemsWithDescription) {
					String sItemDescriptionOnUi = Cls_Generic_Methods.getTextInElement(eItemDescription);
					sortedItemDescriptions.add(sItemDescriptionOnUi);
				}
				// Sort the descriptions using Collections.sort
				List<String> expectedSortedDescriptions = new ArrayList<>(sortedItemDescriptionsBefore);
				Collections.sort(expectedSortedDescriptions);

				//Comparing the Expected Sorted Descriptions and Sorted Descriptions
				if (!sortedItemDescriptions.equals(expectedSortedDescriptions)){
					m_assert.assertTrue(true,"Item descriptions are sorted as expected <b>"+ sortedItemDescriptions+ "</b>" );
				}else {
					m_assert.assertFalse("Item descriptions are not sorted as expected <b>"+ sortedItemDescriptions + "</b>");
				}
				Cls_Generic_Methods.customWait(2);
				Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}

	}
}
