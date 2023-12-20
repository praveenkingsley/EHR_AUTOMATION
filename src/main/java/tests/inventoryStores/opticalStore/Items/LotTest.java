package tests.inventoryStores.opticalStore.Items;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.store.PharmacyStore.Items.Page_Lot;
import pages.store.PharmacyStore.Items.Page_Master;

public class LotTest extends TestBase {

	static Model_Patient myPatient;
	String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
	EHR_Data oEHR_Data = new EHR_Data();

	@Test(enabled = true, description = "Desc")
	public void validateLotFunctionalityInItemLot() {

		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Master oPage_Master = new Page_Master(driver);
		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
		Page_Lot oPage_Lot = new Page_Lot(driver);
		String sBlockReason = "Validating Block Item Lot";
		String sUnblockReason = "Validating Un-Block Item Lot";

		String[] blockLotDetailsTemplateKeysList = {"User:", "Date:", "Blocked Stock:", "Comment:"};

		boolean bItemFound = false;

		try {
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

			try {
				String pharmacyStoreName = "pharmacy viet";
				CommonActions.selectStoreOnApp(pharmacyStoreName);
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();

//				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_Master.input_itemNameSearchInItemMaster, requestedAttribute. "placeholder").equalsIgnoreCase( anotherstring "Search By Item Description") message "Item description placeholder value display");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}


		} catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}





