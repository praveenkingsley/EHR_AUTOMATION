package tests.departments.opd;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.ipd.Page_Discharge;
import pages.ipd.Page_IPD;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ValidateDischargePatientTest extends TestBase {

	Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;

	Page_IPD oPage_Ipd;
	Page_Discharge oPage_Discharge;
	String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
	Page_Navbar oPage_Navbar;
	static Model_Patient myPatient;

	@Test(enabled = true, description = "Validate Patient Arrived in MyQueue for Discharge")
	public void validatePatientArrivedInMyQueueForDischarge() {
		oPage_Navbar = new Page_Navbar(driver);
		oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
		oPage_Ipd = new Page_IPD(driver);
		myPatient = TestBase.map_PatientsInExcel.get(patientKey);

		String patientName = null;
		boolean bPatientNameFound = false;
		String expectedLoggedInUser = "PR.Akash test";
		try {
			CommonActions.loginFunctionality(expectedLoggedInUser);
			Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_departmentFromDropdownSelector);
			for (WebElement eDepartment : oPage_Navbar.list_departmentSelector) {
				if(eDepartment.getText().trim().equalsIgnoreCase("IPD")){
					Cls_Generic_Methods.clickElement(driver,eDepartment);
					Cls_Generic_Methods.waitForPageLoad(driver, 20);
					break;
				}
			}

			try {
				Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,10);
				String MyQueueTab = "My Queue";
				String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
				concatPatientFullName = concatPatientFullName.toUpperCase().trim();
				m_assert.assertTrue(
						CommonActions.selectTabOnDepartmentPage(oPage_Ipd.tabs_appointmentTabsOnIPDpage, MyQueueTab),
						"Validate " + MyQueueTab + " tab is selected");
				Thread.sleep(1000);

				for (WebElement patient : oPage_Ipd.rows_patientAppointmentsInIPD) {
					if (patient.isDisplayed()) {
						List<WebElement> patientDetailsOnRow = patient.findElements(By.xpath("./child::*"));
						//patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");

						patientName = Cls_Generic_Methods.getTextInElement(patientDetailsOnRow.get(0));
                        String getpatientName[] = patientName.split("\\n+");
						String spatientName = getpatientName[0];
						if (concatPatientFullName.equals(spatientName.trim())) {
							Cls_Generic_Methods.clickElement(driver, patient);
							bPatientNameFound = true;
							Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
							break;
						}
					}
				}
				m_assert.assertTrue(bPatientNameFound, "Validate Patient  " + concatPatientFullName + " is clicked in " + MyQueueTab + " of IPD");
				validateDischargePatient();
			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("Exception while getting patient" + e);
			}

		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("Application not loaded in the browser" + e);
		}

	}

	//@Test(enabled = true, description = "Validate Discharge Patient")
	public void validateDischargePatient() throws Exception {
		oPage_Discharge = new Page_Discharge(driver);
		String dischargeTodayTab = "Discharged Today";
		int initialDischargedCount = -1;
		int updatedDischargedCount = -1;
		initialDischargedCount = CommonActions.getCountOfPatientsInTab(oPage_Ipd.tabs_appointmentTabsOnIPDpage, dischargeTodayTab);


		try {
			m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Discharge.button_dischargePatient),
					"Discharge Patient button clicked on the page");

			if(Cls_Generic_Methods.isElementDisplayed(oPage_Discharge.handle_alert)){
				String errMessage = oPage_Discharge.handle_alert.getText();
				m_assert.assertInfo("Patient is been in process, OT is not completed" +errMessage);
			}
			else {
				Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Discharge.click_dischargeDate,10);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Discharge.click_dischargeDate),
						"<b> Discharge Date </b> Calendar Opened");

				DateFormat dateFormat = new SimpleDateFormat("d");
				/////////// for selecting today's date /////////

				Date todayDate = new Date();
				String newDate = dateFormat.format(todayDate);

				for (WebElement dateRow : oPage_Discharge.input_dischargeDate) {
					String date = dateRow.getText();
				//	date = "0" + date;

					if (date.contains(newDate)) {
						dateRow.click();
						m_assert.assertInfo("Date Selected for discharge " + date);
						break;
					}

				}
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Discharge.click_dischargeTime),
						"Discharge time Selected");

				//Cls_Generic_Methods.customWait(5);
				Cls_Generic_Methods.clickElement(driver, oPage_Discharge.form_discharge);

				m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Discharge.button_discharge),
						"Discharge Button on discharge form clicked");

				Cls_Generic_Methods.waitForPageLoad(driver,20);
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

				updatedDischargedCount = CommonActions.getCountOfPatientsInTab(oPage_Ipd.tabs_appointmentTabsOnIPDpage,
						dischargeTodayTab);

				System.out.println(initialDischargedCount + updatedDischargedCount);
				m_assert.assertTrue((updatedDischargedCount > initialDischargedCount),
						"Validate Discharged Patient Count has been increased from <b>" + initialDischargedCount + "</b> to <b>"
								+ updatedDischargedCount + "</b> in Discharged Today tab.");

			}
		} catch (Exception e) {
			m_assert.assertFatal("Patient has not been scheduled for discharge" + e);
		}
	}

}
