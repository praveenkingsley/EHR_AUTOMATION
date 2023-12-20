package tests.inventoryStores.opticalStore.Items;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.Page_InventoryFilterCommonElements;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static pages.commonElements.CommonActions.oPage_InventoryFilterCommonElements;
import static tests.inventoryStores.opticalStore.Order.SalesOrderTest.sOpticalStore;

public class MasterTest extends TestBase {

	static Model_Patient myPatient;
	String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
	EHR_Data oEHR_Data = new EHR_Data();

	@Test(enabled = true, description = "Validate Filter Functionality in the Item Master ")
	public void validateFilterFunctionalityInItemMaster() {

		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		oPage_InventoryFilterCommonElements = new Page_InventoryFilterCommonElements(driver);

		try {
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			try {
				CommonActions.selectStoreOnApp(sOpticalStore);
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
