package tests.inventoryStores.pharmacyStore;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.store.OpticalStore.Page_SalesOrder;
import pages.store.Page_PatientQueue;
import pages.store.Page_Store;

public class PatientQueueTest extends TestBase {

	static Model_Patient myPatient;
	String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
	EHR_Data oEHR_Data = new EHR_Data();
	String sStore = "Pharmacy automation- Pharmacy";
	String concatPatientFullName = "";



}
