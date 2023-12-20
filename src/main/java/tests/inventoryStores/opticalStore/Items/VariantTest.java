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
import pages.store.PharmacyStore.Items.Page_Variant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static pages.commonElements.CommonActions.oPage_InventoryFilterCommonElements;
import static tests.inventoryStores.opticalStore.Order.SalesOrderTest.sOpticalStore;

public class VariantTest extends TestBase {

	static Model_Patient myPatient;
	String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
	EHR_Data oEHR_Data = new EHR_Data();

	@Test(enabled = true, description = "Desc")
	public void demoTestCase() {

		Page_NewPatientRegisteration oPage_NewPatientRegisteration;
		String expectedLoggedInUser = oEHR_Data.user_mansa1;
		try {
			myPatient = map_PatientsInExcel.get(patientKey);
			oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
			CommonActions.loginFunctionality(expectedLoggedInUser);

			try {

				// Open the Search/Add patient dialog box
				try {
					if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
						CommonActions.openPatientRegisterationAndAppointmentForm();
					} else {
						CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
								"Patient Details");
						Thread.sleep(2000);
					}
				} catch (NoSuchElementException e1) {
					CommonActions.openPatientRegisterationAndAppointmentForm();
				}

				// Validate the tabs on Patient Registration Form
				if (oPage_NewPatientRegisteration.tabs_PatientRegForm
						.size() != oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.size()) {
					m_assert.assertTrue(false,
							"No. of Tabs on Patient Reg. Form is "
									+ oPage_NewPatientRegisteration.tabs_PatientRegForm.size() + ". Expected = "
									+ oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.size());
				} else {

					m_assert.assertTrue("No. of Tabs on Patient Reg. & Appointment Form is "
							+ oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.size());

					if (!Cls_Generic_Methods
							.getElementAttribute(oPage_NewPatientRegisteration.tabs_PatientRegForm.get(0), "class")
							.equals("active")) {
						m_assert.assertTrue(false, "Patient Details Tab is not selected on start by default.");
					} else {
						m_assert.assertTrue(true, "Patient Details Tab is selected on start by default.");

						try {
							for (int i = 0; i < oPage_NewPatientRegisteration.tabs_PatientRegForm.size(); i++) {

								if (oPage_NewPatientRegisteration.tabs_PatientRegForm.get(i).getText().trim()
										.equals(oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.get(i))) {

									m_assert.assertInfo(oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.get(i)
											+ " Tab is displayed on the form.");
								} else {
									m_assert.assertTrue(false, oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.get(i)
											+ " Tab is not displayed on the form.");
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							m_assert.assertFatal("" + e);
						}
					}
				}

				m_assert.assertTrue(
						Cls_Generic_Methods.waitForElementToBecomeVisible(
								oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage, 5),
						"Alert for compulsory field is visible by default on the empty form.");

				Cls_Generic_Methods.clickElement(driver,
						oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm);
				Thread.sleep(1000);

				// Validate the Compulsory Sections Message
				if (Cls_Generic_Methods
						.getTextInElement(oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage).trim()
						.equals(oEHR_Data.sCOMPULSORY_FIELDS_MESSAGE)) {
					m_assert.assertTrue(true,
							"Validate that the Compulsory sections message is " + oEHR_Data.sCOMPULSORY_FIELDS_MESSAGE);
				} else {
					m_assert.assertTrue(false,
							"Validate that the Compulsory sections message is " + oEHR_Data.sCOMPULSORY_FIELDS_MESSAGE);
				}

				// Validate the CSS of Compulsory Alert message
				if (Cls_Generic_Methods
						.getElementAttribute(oPage_NewPatientRegisteration.subText_requiredFieldsAlertMessage, "style")
						.equals(oEHR_Data.sSTYLE_OF_RED_ALERT_MSG_FOR_MANODATORY_FIELDS)) {
					m_assert.assertTrue(true,
							"Alert for compulsory field is highlighted in <b>Red</b> when trying to Create Appointment with empty form. Message = "
									+ oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage.getText());
				} else {
					m_assert.assertTrue(false,
							"Alert for compulsory field is highlighted in <b>Red</b> when trying to Create Appointment with empty form.<br>"
									+ "Expected = " + oEHR_Data.sSTYLE_OF_RED_ALERT_MSG_FOR_MANODATORY_FIELDS
									+ "<br>Actual = " + Cls_Generic_Methods.getElementAttribute(
									oPage_NewPatientRegisteration.subText_requiredFieldsAlertMessage, "style"));
				}

				m_assert.assertTrue(oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage.getText().trim()
						.contains("First Name"), "First Name is visible in the Compulsory Fields alert message.");

				m_assert.assertTrue(oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage.getText().trim()
						.contains("Mobile Number"), "Mobile Number is visible in the Compulsory Fields alert message.");

				// Entering Essential Form Data
				if (!myPatient.getsSALUTATION().isEmpty()) {
					m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
									oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()),
							"Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
				}

				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
								oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
						"First Name is entered as - " + myPatient.getsFIRST_NAME());

				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
								oPage_NewPatientRegisteration.input_middleNameOnPatientRegForm, myPatient.getsMIDDLE_NAME()),
						"Middle Name is entered as - " + myPatient.getsMIDDLE_NAME());

				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
								oPage_NewPatientRegisteration.input_lastNameOnPatientRegForm, myPatient.getsLAST_NAME()),
						"Last Name is entered as - " + myPatient.getsLAST_NAME());

				m_assert.assertTrue(
						Cls_Generic_Methods.sendKeysIntoElement(
								oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
								myPatient.getsMOBILE_NUMBER()),
						"Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

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
		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
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
				for (WebElement eCategoryItem : oPage_ItemMaster.list_searchItemsWithCategory){
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
