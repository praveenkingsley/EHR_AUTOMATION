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
import pages.commonElements.Page_InventoryFilterCommonElements;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Items.Page_Variant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static pages.commonElements.CommonActions.getRandomUniqueString;
import static pages.commonElements.CommonActions.oPage_InventoryFilterCommonElements;
import static tests.settings.organisationSettings.inventoryAndSupplyChain.CategoryMasterTest.getRandomString;

public class VariantTest extends TestBase {

	EHR_Data oEHR_Data = new EHR_Data();
	String pharmacyStoreName = "Pharmacy automation- Pharmacy";
	String categoryName = "Medication";
	String subCategoryName = "Test";
	String hsnCode = "HSN" + getRandomUniqueString(4);
	String brandCompanyName = "HealthGraph";
	String itemDescription = "Item_Description_" + getRandomString(4);
	String itemPropertiesTax = "GST5 - 5.0%";
	String itemGenericCompositionName = "Hexapeptide-11";
	String itemGenericCompositionValue = "1.0";
	String[] itemPossibleVariantNameList = {"Variant1", "Variant2", "Variant3", "Variant4"};
	String[] itemPossibleVariantValueList = {"100mg", "200mg", "300mg", "400mg"};
	String[] itemVariantTableHeaderList = {"Variant Code", "Description", "Stock", "Category"};
	String itemInitialStock = "0.0";
	String itemDispensingUnit = "";
	String itemCode = "", variantCode = "";
	String[] viewItemRhsSideKeyList = {"Description:", "Item Code:", "Barcode:", "Stock:", "Identifier:", "Category:", "Variant Code:", "Threshold Value:", "Generic Names:"};
	List<String> viewItemRhsSideValueList = new ArrayList<>();

	@Test(enabled = true, description = "Creating Item For Variant")
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

				m_assert.assertTrue(bItemMaster, " Master Option Displayed In Item ");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Master.button_addNew),
						" Add Item Button Displayed and Clicked");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.header_addItemMasterTemplateTitle, 3);

				m_assert.assertTrue("Add Item Template Header Open and  Displayed as : " + Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.header_addItemMasterTemplateTitle));
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

					itemDispensingUnit = Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.textbox_dispensingUnitDropdownBox);
					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.textbox_dispensingUnitDropdownBox),
							" Dispensing Unit Displayed as : <b> " + itemDispensingUnit + " </b>");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.span_inventoryItemPackageType),
							"Package Type Displayed as : <b> " + Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.span_inventoryItemPackageType) + "</b>");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.input_inventoryItemSubPackageUnit),
							"Sub Package Unit Displayed as : <b> " + Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_inventoryItemSubPackageUnit, "value") + " </b>");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.input_inventoryItemSubPackageType),
							"Sub Package Unit Type Displayed as : <b> " + Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.input_inventoryItemSubPackageType) + "</b>");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.input_inventoryItemSubPackageItemUnit),
							"Item Sub Package Unit Displayed as : <b> " + Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_inventoryItemSubPackageItemUnit, "value") + "</b>");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.input_inventoryItemSubPackageType),
							"Item Sub Package Unit Type Displayed as : <b> " + Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.input_inventoryItemSubPackageType) + "</b>");

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

					for (int i = 0; i <= 2; i++) {
						Cls_Generic_Methods.clickElement(oPage_Master.button_addPossibleVariantButton);
						Cls_Generic_Methods.customWait();
					}

					for (WebElement ePossibleVariantName : oPage_ItemMaster.list_input_itemPossibleVariantNameList) {

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

					boolean bCreatedItemDataFound = selectItemFromItemMasterList(itemDescription, "", itemInitialStock, itemDispensingUnit, categoryName);

					m_assert.assertTrue(bCreatedItemDataFound,
							" Item Name <b> " + itemDescription + " </b> Found and Created Successfully Item Data Displayed Correctly in Table List");


				} else {
					m_assert.assertTrue(bCategoryFound, "Crate Category Name as " + categoryName + " In category master ");
				}

				Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
				Cls_Generic_Methods.customWait();
				Cls_Generic_Methods.driverRefresh();
				Cls_Generic_Methods.waitForPageLoad(driver, 5);

			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}
	}

	@Test(enabled = true, description = "Validation View Variant Functionality")
	public void validateViewVariantFunctionalityInItemVariant() {

		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Master oPage_Master = new Page_Master(driver);

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

				boolean bItemVariant = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Variant");

				m_assert.assertTrue(bItemVariant, " Variant Option Displayed and Clicked In Item ");

				// Validating Headers From Item Master Table List

				validatingDataTableHeadersInItemVariant(oPage_Master.list_viewItemMasterTableHeaderList, itemVariantTableHeaderList, "Item Variant List");
				variantCode = itemCode + "-100";

				viewItemRhsSideValueList.add(itemDescription);
				viewItemRhsSideValueList.add(itemCode);
				viewItemRhsSideValueList.add("");
				viewItemRhsSideValueList.add(itemInitialStock);
				viewItemRhsSideValueList.add("");
				viewItemRhsSideValueList.add(categoryName);
				viewItemRhsSideValueList.add(variantCode);
				viewItemRhsSideValueList.add("0");
				viewItemRhsSideValueList.add(itemGenericCompositionName + " " + itemGenericCompositionValue + " " + "mg");

				bItemFound = selectItemFromItemVariantList(itemDescription, variantCode, itemInitialStock, categoryName);

				m_assert.assertTrue(bItemFound, " Item Found In Item Variant List");

				int indexOfKey = 0, indexOfValue = 0;
				for (WebElement eViewItem : oPage_Master.list_keysAndValuesInItemViewRHSSide) {

					if (Cls_Generic_Methods.isElementDisplayed(eViewItem)) {
						int indexOfViewItem = oPage_Master.list_keysAndValuesInItemViewRHSSide.indexOf(eViewItem);
						String sViewItemText = Cls_Generic_Methods.getTextInElement(eViewItem);
						if (indexOfViewItem % 2 == 0) {
							m_assert.assertTrue(sViewItemText.equalsIgnoreCase(viewItemRhsSideKeyList[indexOfKey]),
									"<b> " + sViewItemText + " </b> Label Present In View Item RHS side ");
							indexOfKey++;
						} else {
							if (indexOfViewItem == 5) {
								m_assert.assertTrue(!(sViewItemText.isEmpty()),
										"<b>" + sViewItemText + " </b>  Value Present In View Item RHS against label <b> " + viewItemRhsSideKeyList[indexOfValue] + "</b>");
							} else {
								if (indexOfViewItem == 9) {
									m_assert.assertTrue("<b>" + sViewItemText + " </b>  Value Present In View Item RHS against label <b> " + viewItemRhsSideKeyList[indexOfValue] + "</b>");
								} else {
									m_assert.assertTrue(sViewItemText.equalsIgnoreCase(viewItemRhsSideValueList.get(indexOfValue)),
											"<b> " + sViewItemText + "  </b> Value Present In View Item RHS against label <b> " + viewItemRhsSideKeyList[indexOfValue] + " </b>");
								}
							}
							indexOfValue++;
						}
					}
				}

				Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
				Cls_Generic_Methods.customWait();
				Cls_Generic_Methods.driverRefresh();
				Cls_Generic_Methods.waitForPageLoad(driver, 5);

			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}
	}

	@Test(enabled = true, description = "Validating Delete Variant Functionality in Item Variant")
	public void validateDeleteFunctionalityInItemVariant() {

		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Master oPage_Master = new Page_Master(driver);
		Page_Variant oPage_Variant = new Page_Variant(driver);

		try {
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			try {

				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();
				boolean bItemMaster = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Variant");

				m_assert.assertTrue(bItemMaster, " Master Option Displayed In Item ");
				boolean bItemClicked = false;

				Cls_Generic_Methods.clearValuesInElement(oPage_Master.input_itemNameSearchInItemMaster);
				Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, itemDescription);
				Cls_Generic_Methods.customWait(3);
				oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
				Cls_Generic_Methods.customWait(2);

				for (WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory) {
					if (Cls_Generic_Methods.isElementDisplayed(itemData)) {

						List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./child::*"));

						String itemDescriptionName = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));

						if (itemDescriptionName.contains(itemDescription)) {
							bItemClicked = true;
							m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemData),
									itemDescription + " Item Clicked from Item Variant List");
							Cls_Generic_Methods.customWait(5);
							break;
						}
					}
				}

				m_assert.assertTrue(bItemClicked, "Item Found And Clicked");

				if (bItemClicked) {

					String deleteVariantCode = Cls_Generic_Methods.getTextInElement(oPage_Variant.text_variantCode);
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Master.button_deleteItem),
							"Delete Button Clicked");
					Cls_Generic_Methods.customWait();
					m_assert.assertTrue(!Cls_Generic_Methods.isElementClickable(driver, oPage_Master.button_confirmDeleteItem),
							" Input Delete Field Displayed");
					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemCodeForDelete, deleteVariantCode),
							" Input Variant Code Text Box displayed and value send as : " + deleteVariantCode);
					Cls_Generic_Methods.customWait();
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Master.button_confirmDeleteItem),
							"Confirm Delete Button is Enabled and CLicked");
					Cls_Generic_Methods.customWait(5);
					Cls_Generic_Methods.clearValuesInElement(oPage_Master.input_itemNameSearchInItemMaster);
					Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, itemDescription);
					Cls_Generic_Methods.customWait(3);
					oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
					Cls_Generic_Methods.customWait(2);
					m_assert.assertTrue(oPage_ItemMaster.list_itemListInStoreInventory.size() == 0,
							itemDescription + " Item Variant Deleted Successfully ");

					Cls_Generic_Methods.customWait();
					Cls_Generic_Methods.driverRefresh();
					Cls_Generic_Methods.waitForPageLoad(driver, 6);

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
							"Store pop up closed");
					Cls_Generic_Methods.customWait();
					boolean bItemFoundAfterVariantDelete = false;

					bItemFoundAfterVariantDelete = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Variant");

					for (WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory) {
						if (Cls_Generic_Methods.isElementDisplayed(itemData)) {

							List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./child::*"));

							String itemDescriptionName = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));

							if (itemDescriptionName.contains(itemDescription)) {
								bItemFoundAfterVariantDelete = true;
								m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemData),
										itemDescription + " Item Clicked from Item Master List");
								Cls_Generic_Methods.customWait(5);
								break;
							}
						}
					}

					m_assert.assertTrue(bItemFoundAfterVariantDelete, " Item Found In Master , Even After Deleting Variant");


				} else {
					m_assert.assertTrue(bItemClicked, " Item Name as " + itemDescription + " Not Found Please create In item master ");
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


	public boolean selectItemFromItemVariantList(String sItemDescription, String sVariantCode, String sItemInitialStock, String sCategoryName) {

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

					variantCode = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((0)));
					String itemDescriptionName = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));
					String itemStock = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((2)));
					String itemCategory = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((3)));

					if (itemDescriptionName.contains(sItemDescription)
							&& (!variantCode.equalsIgnoreCase(sVariantCode) || variantCode.equalsIgnoreCase(sVariantCode))
							&& itemStock.equalsIgnoreCase(sItemInitialStock)
							&& itemCategory.contains(sCategoryName)
					) {
						bCreatedItemDataFound = true;
						m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemData),
								"<b> " + itemDescription + " </b> Item Clicked from Item Variant List");
						Cls_Generic_Methods.customWait(5);

						m_assert.assertInfo(" <b> Item Code </b> of Created/Updated Item Displayed as : <b> " + variantCode + " </b> In Item Variant List");
						m_assert.assertInfo(" <b> Item Name </b> of Created/Updated  Item Displayed as : <b> " + itemDescription + " </b> In Item Variant List");
						m_assert.assertInfo(" <b> Item Stock </b> of Created/Updated  Item Displayed as : <b> " + itemInitialStock + " </b> in Item variant");
						m_assert.assertInfo(" <b> Item Category </b> of Created/Updated  Item Displayed as : <b> " + categoryName + " </b> In Item Variant List ");

						break;
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bCreatedItemDataFound;
	}

	public boolean selectItemFromItemMasterList(String sItemDescription, String sItemCode, String sItemInitialStock,
												String sDispensingUnit, String sCategoryName) {

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
								"<b> " + itemDescription + " </b> Item Clicked from Item Master List");
						Cls_Generic_Methods.customWait(5);

						m_assert.assertInfo(" <b> Item Code </b> of Created/Updated Item Displayed as : <b> " + itemCode + " </b> In Item Master List");
						m_assert.assertInfo(" <b> Item Name </b> of Created/Updated  Item Displayed as : <b> " + itemDescription + " </b> In Item Master List");
						m_assert.assertInfo(" <b> Item Stock </b> of Created/Updated  Item Displayed as : <b> " + itemInitialStock + " </b> ");
						m_assert.assertInfo(" <b> Item Dispensing Unit </b> of Created/Updated  Item Displayed as : <b> " + itemDispensingUnit + " </b> In Item Master List");
						m_assert.assertInfo(" <b> Item Category </b> of Created/Updated  Item Displayed as : <b> " + categoryName + " </b> In Item master List ");

						break;
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bCreatedItemDataFound;
	}

	public void validatingDataTableHeadersInItemVariant(List<WebElement> eHeaderList, String[] actualHeaderList, String sSection) {
		try {
			for (WebElement itemTableKeys : eHeaderList) {

				if (Cls_Generic_Methods.isElementDisplayed(itemTableKeys)) {

					int indexOfLotDetailsKey = eHeaderList.indexOf(itemTableKeys);
					String sItemKeyText = Cls_Generic_Methods.getTextInElement(itemTableKeys);
					if (sItemKeyText.equalsIgnoreCase(actualHeaderList[indexOfLotDetailsKey])) {

						m_assert.assertTrue("<b> " + sItemKeyText + " </b> Header Present In " + sSection + " Table at index : <b> " + indexOfLotDetailsKey + " </b>");

					} else {
						m_assert.assertWarn(sItemKeyText + " header not present in " + sSection + "Table ,Either Removed or changed");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test(enabled = true, description = "Search by item description, item code, var code ")
	public void validateSearchFeatureInVariantFunction() {

		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Variant oPage_Variant = new Page_Variant(driver);

		String sItemDescription = "Item_Description_vxrn_Updated";
		String sItemCode = "INV-OP-ITC-1171";
		String sVariantCode = "INV-MED-1258-100";
		String sBarCode = "RSO174627INVOPITC1170100";

		try {
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			try {

				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();
				boolean bItemMaster = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Variant");

				m_assert.assertTrue(bItemMaster, " Variant Option Displayed In Item ");

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
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchbarCriteria, sItemDescription), "Item Decsription = <b>" + sItemDescription + "</b>");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_searchItem), "click on search button");
				Cls_Generic_Methods.customWait(4);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.list_searchItem1.get(0)), "Click on  = <b>" + sItemDescription + "</b>");
				Cls_Generic_Methods.customWait(3);

				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_ItemMaster.text_searchItemDescriptionName).equals(sItemDescription),
						" Required Item is selected = <b>" + sItemDescription + "</b>");

				Cls_Generic_Methods.customWait(3);

				//search by item code
				Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_searchFilterItemDescription);
				boolean bVariantItemFound1 = false;
				for (WebElement webElement : oPage_ItemMaster.list_searchFilterItemDescriptionDropdown) {
					String optionText = webElement.getText();
					if (optionText.equals("Item Code")) {
						webElement.click();
						bVariantItemFound1 = true;
						break;
					}
				}
				m_assert.assertTrue(bVariantItemFound1, " Item Code is selected");
				Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_searchbarCriteria);
				Cls_Generic_Methods.customWait(3);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchbarCriteria, sItemCode), "Item code = <b>" + sItemCode + "</b>");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_searchItem), "click on search button");
				Cls_Generic_Methods.customWait(3);
				oPage_ItemMaster = new Page_ItemMaster(driver);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.list_searchItem1.get(0)), "Click on  = <b>" + sItemCode + "</b>");
				Cls_Generic_Methods.customWait(3);
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Variant.text_itemCode).equals(sItemCode),
						" Required Item Are selected = <b>" + sItemCode + "</b>");
				Cls_Generic_Methods.customWait(3);


				// search by variant code
				Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_searchFilterItemDescription);
				boolean bVariantItemFound2 = false;
				for (WebElement webElement : oPage_ItemMaster.list_searchFilterItemDescriptionDropdown) {
					if (webElement.getText().equals("Variant Code")) {
						webElement.click();
						bVariantItemFound2 = true;
						break;
					}
				}
				m_assert.assertTrue(bVariantItemFound2, " Variant Code is selected");
				Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_searchbarCriteria);
				Cls_Generic_Methods.customWait(3);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchbarCriteria, sVariantCode), "Variant code = <b>" + sVariantCode + "</b>");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_searchItem), "click on search button");
				Cls_Generic_Methods.customWait(4);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.list_searchItem1.get(0)), "Click on  = <b>" + sVariantCode + "</b>");
				Cls_Generic_Methods.customWait(3);

				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Variant.text_variantCode).equals(sVariantCode),
						" Required Item is selected = <b>" + sVariantCode + "</b>");

				Cls_Generic_Methods.customWait(3);

				//Search by bar code
				Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_searchFilterItemDescription);
				boolean bVariantItemFound3 = false;
				for (WebElement webElement : oPage_ItemMaster.list_searchFilterItemDescriptionDropdown) {
					if (webElement.getText().equals("Barcode")) {
						webElement.click();
						bVariantItemFound3 = true;
						break;
					}
				}
				m_assert.assertTrue(bVariantItemFound3, " Bar Code is selected");
				Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_searchbarCriteria);
				Cls_Generic_Methods.customWait(3);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchbarCriteria, sBarCode), "Bar code = <b>" + sBarCode + "</b>");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_searchItem), "click on search button");
				Cls_Generic_Methods.customWait(4);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.list_searchItem1.get(0)), "Click on  = <b>" + sBarCode + "</b>");
				Cls_Generic_Methods.customWait(3);
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Variant.text_barCode).equals(sBarCode),
						" Bar code = <b>" + sBarCode + "</b>");
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

	@Test(enabled = true, description = "Validate Filter Functionality in the Items Variant ")
	public void validateFilterFunctionalityInItemVariant() {

		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Variant oPage_Variant = new Page_Variant(driver);
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
				boolean bItemMaster = CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Variant");
				m_assert.assertTrue(bItemMaster, " Variant Item Found in the Item variant");

				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Variant.button_filterButton,6);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_filterButton),"Clicked on Filter button");
				Cls_Generic_Methods.customWait(2);
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Variant.text_filterHeader).equalsIgnoreCase("Filter Variant Items"),"Filter header is validated as <b>"+ "Filter Variant Items" + "</b>");

				//Validating the filter for Item by stock
				CommonActions.filterItemsByStock("Items With Stock");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_filterApplyButton),"Clicked on Apply button in filter");
				Cls_Generic_Methods.customWait(4);
				for(WebElement eItemStock : oPage_Variant.list_searchItemsWithStock) {
					String sItemStockOnUi = Cls_Generic_Methods.getTextInElement(eItemStock);
					if(Double.parseDouble(sItemStockOnUi)>=0.0 ){
						m_assert.assertTrue(" Item With stock is present and filter working correctly <b>"+sItemStockOnUi + "</b>");
					}else{
						m_assert.assertFalse(" Item with stock filter Not  working correctly <b>"+sItemStockOnUi + "</b>");
					}
				}
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_clearVariantFilterButton),"Clicked on Clear Filter option to Clear the selected filter");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_filterButton),"Clicked on Filter button");
				Cls_Generic_Methods.customWait(2);

				//Validating the filter for Running low Stock Item
				CommonActions.filterItemsByStock("Running Low Stock");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_filterApplyButton),"Clicked on Apply button in filter");
				Cls_Generic_Methods.customWait(4);
				for (WebElement eRunningLowStock : oPage_Variant.list_searchRunningLowStock){
					String sRunningLowStockOnUi = Cls_Generic_Methods.getElementAttribute(eRunningLowStock,"style");
					if (sRunningLowStockOnUi.contains("background-color: rgb(255, 165, 0);")){
						m_assert.assertTrue(" Running low stock items are present and filter working correctly <b>"+sRunningLowStockOnUi + "</b>");
					}else{
						m_assert.assertFalse(" Running low stock filter is Not working correctly <b>"+sRunningLowStockOnUi + "</b>");
					}
				}
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_clearVariantFilterButton),"Clicked on Clear Filter option to Clear the selected filter");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_filterButton),"Clicked on Filter button");
				Cls_Generic_Methods.customWait(2);

				//Validating the filter for Low Stock Item
				CommonActions.filterItemsByStock("Out Of Stock");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_filterApplyButton),"Clicked on Apply button in filter");
				Cls_Generic_Methods.customWait(4);
				for(WebElement eOutOfStock : oPage_Variant.list_searchOutOfStock) {
					String sOutOfStockOnUi = Cls_Generic_Methods.getTextInElement(eOutOfStock);
					if(Double.parseDouble(sOutOfStockOnUi) == 0.0){
						m_assert.assertTrue("Out of Stock items are present and filter working correctly <b>"+sOutOfStockOnUi+ "</b>");
					}else{
						m_assert.assertFalse(" Out of stock filter is Not working correctly <b>"+sOutOfStockOnUi+ "</b>");
					}
				}
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_clearVariantFilterButton),"Clicked on Clear Filter option to Clear the selected filter");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_filterButton),"Clicked on Filter button");
				Cls_Generic_Methods.customWait(2);

				//Validating the filter for Category
				CommonActions.filterItemsByCategories("Intraocular Lens");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_filterApplyButton),"Clicked on Apply button in filter");
				Cls_Generic_Methods.customWait(4);
				for (WebElement eCategoryItem : oPage_Variant.list_searchItemsWithCategory){
					String sCategoryItemOnUi = Cls_Generic_Methods.getTextInElement(eCategoryItem);
					if (sCategoryItemOnUi.contains("Intraocular Lens")){
						m_assert.assertTrue(" Items with Selected categories are present and filter working correctly <b>"+sCategoryItemOnUi + "</b>");
					}else{
						m_assert.assertFalse("Items with Categories filter is Not working correctly <b>"+sCategoryItemOnUi + "</b>");
					}
				}
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_clearVariantFilterButton),"Clicked on Clear Filter option to Clear the selected filter");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_filterButton),"Clicked on Filter button");
				Cls_Generic_Methods.customWait(2);

				//Validating Clear to default button and close button in filter form
				CommonActions.filterItemsByStock("Items With Stock");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_clearFilterItemByStock),"Selected Items with stock filter is Cleared and Clear option is working correctly");
				CommonActions.filterItemsByStock("Running Low Stock");
				CommonActions.sortBy("Stock");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_clearSortBy),"Selected options in sort filter is cleared and Clear option is working Correctly");
				CommonActions.sortBy("Description");
				CommonActions.filterItemsByCategories("Medication");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_filterClearButton),"Clicked on Cleared to Default button and it is working correctly");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_filterCloseButton),"Clicked on Close button to close the Filter form");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_filterButton),"Clicked on Filter button");
				Cls_Generic_Methods.customWait(2);

				//Validating Stock filter in Sort by options
				CommonActions.sortBy("Stock");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_filterApplyButton),"Clicked on Apply button in filter");
				Cls_Generic_Methods.customWait(4);
				for(WebElement eItemStock : oPage_Variant.list_searchItemsWithStock) {
					String sItemStockOnUi = Cls_Generic_Methods.getTextInElement(eItemStock);
					if(Double.parseDouble(sItemStockOnUi)>=0.0 ){
						m_assert.assertTrue(" Sort by Stock filter working correctly <b>"+sItemStockOnUi+ "</b>");
					}else{
						m_assert.assertFalse(" Sort by stock filter Not  working correctly <b> "+sItemStockOnUi+ "</b>");
					}
				}
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_clearVariantFilterButton),"Clicked on Clear Filter option to Clear the selected filter");
				List<String> sortedItemDescriptionsBefore = new ArrayList<>();
				for(WebElement eItemDescription : oPage_Variant.list_searchItemsWithDescription) {
					String sItemDescriptionOnUi = Cls_Generic_Methods.getTextInElement(eItemDescription);
					sortedItemDescriptionsBefore.add(sItemDescriptionOnUi);
				}
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_filterButton),"Clicked on Filter button");
				Cls_Generic_Methods.customWait(2);

				//Validating Description filter in Sort by options
				CommonActions.sortBy("Description");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Variant.button_filterApplyButton),"Clicked on Apply button in filter");
				Cls_Generic_Methods.customWait(4);
				List<String> sortedItemDescriptions = new ArrayList<>();
				for(WebElement eItemDescription : oPage_Variant.list_searchItemsWithDescription) {
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
					m_assert.assertFalse("Item descriptions are not sorted as expected <b>"+ sortedItemDescriptions+ "</b>");
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

}

