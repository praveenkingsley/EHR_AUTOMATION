package tests.inventoryStores.pharmacyStore;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.templates.Page_InventorySearchCommonElements;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_CategoryMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_VendorMaster;
import pages.store.Page_Store;
import pages.store.PharmacyStore.Transaction.Page_Adjustment;
import pages.store.PharmacyStore.Transaction.Page_SON;

import java.util.List;

import static pages.commonElements.CommonActions.*;
import static tests.settings.organisationSettings.inventoryAndSupplyChain.CategoryMasterTest.getRandomString;
import static tests.settings.organisationSettings.inventoryAndSupplyChain.CategoryMasterTest.selectCategoryFromDropdown;

public class VendorTest extends TestBase {

	EHR_Data oEHR_Data = new EHR_Data();
	String newVendorName = "Test_" + getRandomString(3);
	String newVendorGroup = "Deepak_Automation_Group_1";
	String newVendorCompanyName = "Vendor Group";
	String newVendorMobileNumber = "7860000000";
	String storeGSTno;
	String newVendorCountry = "India (IN)";
	String newVendorAddress = "Address1";
	String selectedCategoryName = "Medication";
	String gstNo = "GST"+CommonActions.getRandomString(4);
	String dlNumber  =  "DL"+CommonActions.getRandomString(4);
	String oldDlNumber="";
	String vendorName = "Supplier ABC",vendor_address,vendorGSTno;
	String vendorNumber = "",companyName;
	boolean gstTypeIGST;
	String pharmacyStoreName = "Pharmacy automation- Pharmacy";


	@Test(enabled = true, description = "Validating Add Vendor Functionality in Vendor Master")
	public void validateAddVendorFunctionality() {

		Page_VendorMaster oPage_VendorMaster = new Page_VendorMaster(driver);
		Page_CategoryMaster oPage_CategoryMaster = new Page_CategoryMaster(driver);
		Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
		Page_Navbar oPage_Navbar = new Page_Navbar(driver);

		boolean bVendorFound = false;
		boolean bCategoryLinkedInStoreInventory = false ;

		try {
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			try {

				// Select Vendor Master In Inventory And Supply Chain

				CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
				CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Master");

				// Opening Add Item Template ,Verify Add Item Template

				Cls_Generic_Methods.waitForPageLoad(driver, 4);

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_VendorMaster.header_vendorMasterTitle),
						" Verifying Vendor Master Header ");
				Cls_Generic_Methods.scrollToTop();
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_addVendorButton),
						" Add Vendor Button Clicked");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.header_addVendorMasterTemplateTitle, 5);

				//Entering All Fields Values and Creating New Vendor

				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorName, newVendorName),
						"Vendor Name Entered as : <b>" + newVendorName + "</b>");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_inventoryVendorGroupArrow),
						"Vendor Group Dropdown Arrow Clicked");

				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.input_inventoryDropdownCommonInputBox, 1);
				Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryDropdownCommonInputBox, newVendorGroup);
				Cls_Generic_Methods.customWait(1);
				m_assert.assertTrue(CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_VendorMaster.list_inventoryVendorDropdownResultOptions,
						newVendorGroup), "Vendor Group Entered as : <b>" + newVendorGroup + "</b>");


				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorCompanyName, newVendorCompanyName),
						"Vendor Company Name Entered as : <b>" + newVendorCompanyName + "</b>");
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorMobileNumber, newVendorMobileNumber),
						"Vendor Mobile Number Entered as : <b>" + newVendorMobileNumber + "</b>");

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_inventoryVendorCountryArrow),
						"Vendor Group Dropdown Arrow Clicked");

				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.input_inventoryDropdownCommonInputBox, 1);
				Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryDropdownCommonInputBox, newVendorCountry);
				Cls_Generic_Methods.customWait(1);

				m_assert.assertTrue(CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_VendorMaster.list_inventoryVendorDropdownResultOptions,
						newVendorCountry), "Vendor Country Entered as : <b>" + newVendorCountry + "</b>");

				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorAddress, newVendorAddress),
						"Vendor GST Entered as : <b>" + newVendorAddress + "</b>");

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CategoryMaster.button_saveChanges),
						" Save Changes button clicked ");

				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.button_addVendorButton, 5);

				// Validation Newly Created Vendor Using Search In Vendor Master

				Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_searchVendorNameBox, newVendorName);
				Cls_Generic_Methods.customWait();
				for (WebElement vendor : oPage_VendorMaster.list_vendorRowForVendorMaster) {
					if (Cls_Generic_Methods.isElementDisplayed(vendor)) {
						List<WebElement> vendorNameRow = vendor.findElements(By.xpath("./child::*"));
						String vendorName = Cls_Generic_Methods.getTextInElement(vendorNameRow.get((0)));
						String vendorGroup = Cls_Generic_Methods.getTextInElement(vendorNameRow.get((1)));
						if (vendorName.equalsIgnoreCase(newVendorName) && vendorGroup.equalsIgnoreCase(newVendorGroup)) {
							bVendorFound = true;
							break;
						}
					}
				}
				m_assert.assertTrue(bVendorFound, "New Vendor Found Successfully in Vendor Master List as :<b> " + newVendorName + "</b>");

				CommonActions.getActionToPerformInInventorySetting(oPage_VendorMaster.list_vendorNameForVendorMaster,
						newVendorName, oPage_VendorMaster.list_vendorActionsForVendorName, " Location");
				Cls_Generic_Methods.customWait(4);
				Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_addVendorLocation);
				Cls_Generic_Methods.customWait(4);
				Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_inventoryVendorDlNumber,dlNumber);
				Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_gstNo,gstNo);


				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CategoryMaster.button_saveChanges),
						" Save Changes button clicked ");

				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.button_addVendorButton, 5);

				CommonActions.getActionToPerformInInventorySetting(oPage_VendorMaster.list_vendorNameForVendorMaster,
						newVendorName, oPage_VendorMaster.list_vendorActionsForVendorName, "Link Category");

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_VendorMaster.input_selectCategorySearchBox),
						" Link Existing Category Template opened in Vendor Master");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorMaster.input_selectCategorySearchBox),
						"Select Category Input Box Clicked");
				Cls_Generic_Methods.customWait(1);

				Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_selectCategorySearchBox, selectedCategoryName);
				for (WebElement categoryName : oPage_VendorMaster.list_selectCategoryValuesList) {
					if (Cls_Generic_Methods.getTextInElement(categoryName).contains(selectedCategoryName)) {
						m_assert.assertTrue(Cls_Generic_Methods.clickElement(categoryName),
								"Category clicked as : <b>" + selectedCategoryName + "</b>");
						break;
					}
				}
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.button_saveLinkCategoryInVendorMaster, 1);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_saveLinkCategoryInVendorMaster),
						" Save button In Link Existing Category Clicked ");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.button_addVendorButton, 5);

				// Verify Vendor to linked Category In Linked Category Story Inventory

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_facilitySelector),
						"Facility Selector Button Clicked");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.text_selectedFacilityName, 2);
				String facilityName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_selectedFacilityName);

				CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
				CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");

				Cls_Generic_Methods.waitForPageLoad(driver, 4);

				boolean clickOnLinkActions = CommonActions.getActionsOfSelectedStoreOfFacility(oPage_StoreSetUp.list_facilityNamesForInventory, facilityName,
						oPage_StoreSetUp.list_storeColumnLinkedToFacility, oPage_StoreSetUp.list_buttonColumnLinkedToStores, "Link", "Pharmacy automation", "Disable");

				Cls_Generic_Methods.customWait();
				m_assert.assertTrue(clickOnLinkActions, "Link Category Button is Clicked in Store Setup List");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.dropdown_actionPerformedOnLinkDropdown, 2);
				m_assert.assertTrue(selectCategoryFromDropdown(oPage_StoreSetUp.list_linkActionDropdown, "Category"),
						"Category Clicked In Link Dropdown");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.input_selectStoreInLinkExistingStore, 2);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore),
						"Store input Box clicked in Link Existing Store");
				Cls_Generic_Methods.sendKeysIntoElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore, selectedCategoryName);
				Cls_Generic_Methods.customWait(2);

				for (WebElement category : oPage_StoreSetUp.list_categoriesInDropdown) {
					if (Cls_Generic_Methods.getTextInElement(category).equalsIgnoreCase(selectedCategoryName)) {
						m_assert.assertTrue(Cls_Generic_Methods.clickElement(category),
								"Category Entered as : <b> " + selectedCategoryName + "</b>");
						bCategoryLinkedInStoreInventory = true;
						break;
					}
				}
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_StoreSetUp.button_saveLinkCategory),
						" Save Category Button Clicked");

				if (bCategoryLinkedInStoreInventory) {

					m_assert.assertInfo(" Category is already linked ");
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
							"Closing Link Existing Dispensing Unit ");
				}

				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 3);
				Cls_Generic_Methods.driverRefresh();
				Cls_Generic_Methods.customWait(5);
				getGST_valueFromSetting();


			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}
	}

	public void getGST_valueFromSetting() {
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
				if (storeName.contains(storeName.split("-")[0])) {
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
								oldDlNumber = Cls_Generic_Methods.getElementAttribute(oPage_VendorMaster.input_inventoryVendorDlNumber, "value");
								vendorNumber = Cls_Generic_Methods.getElementAttribute(oPage_VendorMaster.input_inventoryVendorMobileNumber, "value");
								companyName = Cls_Generic_Methods.getElementAttribute(oPage_VendorMaster.input_inventoryVendorCompanyName, "value");


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

	@Test(enabled = true, description = "Search Functionality In Adjustment Transaction")
	public void validateSearchFunctionalityInAdjustment(){

		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
		InventorySearchTest oInventorySearchTest = new InventorySearchTest();
		Page_SON oPage_SON = new Page_SON(driver);
		Page_Adjustment oPage_Adjustment = new Page_Adjustment(driver);



		String vendorSearchTypeList [] = {"Vendor Name","Company Name","Vendor Mobile No","Vendor GST No","Vendor DL No."};
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
								equalsIgnoreCase(vendorSearchTypeList[0]),
						" By Default Search Type Selection Dropdown Displayed correctly as :" + vendorSearchTypeList[0]);
				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput, "value")
								.equalsIgnoreCase(""),
						" Input Search Box is empty by default for selected search type Displayed correctly");
				m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
						" Clear Button Not Displayed Correctly as Default");
				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput, "placeholder")
								.contains("Search By " + vendorSearchTypeList[0]),
						" Input Search Box Place holder for selected search type Displayed correctly");

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_filterDropdownButton),
						" Filter Dropdown Button Clicked");
				Cls_Generic_Methods.customWait(2);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.text_thisYearFilter),
						" This Year Selected as Filter");
				Cls_Generic_Methods.customWait(2);

				String searchValueList[] = {newVendorName,vendorName,newVendorCompanyName,companyName,newVendorMobileNumber,vendorNumber,
				gstNo,vendorGSTno,dlNumber,oldDlNumber};

				for(int i = 0;i<10;i++) {
					boolean searchResultByTransactionId = CommonActions.selectOptionAndSearch(vendorSearchTypeList[0], searchValueList[i]);
					Cls_Generic_Methods.customWait();
					if (searchResultByTransactionId) {
						m_assert.assertTrue(  oPage_SON.list_SONTransactions.size() == 1,
								"Search By "+vendorSearchTypeList+" Worked correctly as order found in the  page for number: " + searchValueList[i]);
					}
				}




				for(int i =0 ;i<5;i++) {

					boolean searchResultByWrongNumber = selectOptionAndSearch(vendorSearchTypeList[i],"incorrectReqNumber");
					m_assert.assertFalse(searchResultByWrongNumber," vendor Search With Incorrect Number Working Correct");
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
				boolean selectOption = CommonActions.selectOption(vendorSearchTypeList[0]);
				m_assert.assertTrue(selectOption," Able to selected Search type Again to Default");
				m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
								equalsIgnoreCase(vendorSearchTypeList[0]),
						" Search Type Selected  as : "+vendorSearchTypeList[0]);

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
						" Search Type Dropdown Clicked");
				Cls_Generic_Methods.customWait();
				for(WebElement type : oPage_InventorySearchCommonElements.list_searchTypeList){
					String typeText = Cls_Generic_Methods.getTextInElement(type);
					int index = oPage_InventorySearchCommonElements.list_searchTypeList.indexOf(type);
					if(typeText.equalsIgnoreCase(vendorSearchTypeList[index])){
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




}
