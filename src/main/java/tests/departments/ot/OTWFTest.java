package tests.departments.ot;

import data.OT_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;

import data.EHR_Data;
import pages.commonElements.CommonActions;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.ipd.Page_IPD;
import pages.opd.Page_OPD;
import pages.ot.Page_OT;

import java.util.List;

public class OTWFTest extends TestBase {
	
	Page_OPD oPage_OPD;
	Page_IPD oPage_IPD;
	Page_OT oPage_OT;
	Page_Navbar oPage_Navbar;
	Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
	String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
	static Model_Patient myPatient;

	@Test(enabled = true, description = "Validate schedule OT from IPD")
	public void validateScheduleOTFromIPD() throws Exception {

		oPage_IPD = new Page_IPD(driver);
		oPage_OPD = new Page_OPD(driver);
		oPage_OT = new Page_OT(driver);
		oPage_Navbar = new Page_Navbar(driver);
		oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
		myPatient = TestBase.map_PatientsInExcel.get(patientKey);
		String admittedPatientTab = "Admitted Patients";

		boolean bPatientNameFound = false;
		String expectedLoggedInUser = EHR_Data.user_PRAkashTest;

		try {
			CommonActions.loginFunctionality(expectedLoggedInUser);
			CommonActions.selectDepartmentOnApp("IPD");

			try {
				String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
				concatPatientFullName = concatPatientFullName.toUpperCase().trim();
				m_assert.assertTrue(
						CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, admittedPatientTab),
						"Validate " + admittedPatientTab + " tab is selected");
				Thread.sleep(1000);

				bPatientNameFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, concatPatientFullName);
				if (bPatientNameFound) {

					m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByAction(driver, oPage_OT.button_scheduleOt),
							"Scheduled OT button is displayed");
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OT.button_scheduleOt),
							"Schedule OT button clicked");

					m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OT.header_OTForm, 15),
							"New OT form is displayed");
					m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OT.checkbox_timeSlotOverlap),
							"Time Slot Overlap Checkbox marked");
					Cls_Generic_Methods.customWait(4);
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OT.button_viewCaseDetails),
							"View case details button clicked");

					m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OT.button_saveOtForm),
							"Schedule OT button is clicked on OT form");
					Cls_Generic_Methods.customWait(5);
					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,16);

					m_assert.assertInfo(Cls_Generic_Methods.scrollToElementByAction(driver, oPage_OT.link_Ot),
							"OT link is displayed");
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OT.link_Ot),
							"Clicked on OT link");
					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,16);
					m_assert.assertInfo(true,"OT page Displayed");
				}
			}catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("Exception while admitting patient to OT" + e);
			}
		}catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}
	}

	@Test(enabled = true, description = "Validate ready for OT from OT")
	public void validateReadyForOtInOTPage() throws Exception {

		oPage_IPD = new Page_IPD(driver);
		oPage_OPD = new Page_OPD(driver);
		oPage_OT = new Page_OT(driver);
		oPage_Navbar = new Page_Navbar(driver);
		oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
		myPatient = TestBase.map_PatientsInExcel.get(patientKey);
		String admittedTab = OT_Data.tab_ADMITTED;
		String sNurseName = EHR_Data.user_HGNurse2;
		String sReasonForPatient = "admitted to OT";
		boolean bPatientSent = false;

		boolean bPatientNameFound = false;
		String expectedLoggedInUser = EHR_Data.user_PRAkashTest;

		try {
			CommonActions.loginFunctionality(expectedLoggedInUser);
			CommonActions.selectDepartmentOnApp("OT");

			try {
				String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
				concatPatientFullName = concatPatientFullName.toUpperCase().trim();
				m_assert.assertTrue(
						CommonActions.selectTabOnDepartmentPage(oPage_OT.tabs_TabsOnOT, admittedTab),
						"Validate " + admittedTab + " tab is selected");
				Thread.sleep(1000);

				bPatientNameFound = CommonActions.selectPatientNameInOT(oPage_OT.rows_patientNamesOnOT, concatPatientFullName);
				if (bPatientNameFound) {
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver,oPage_OT.button_readyForOT),
							"Ready for OT Clicked");

					m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OT.header_sendPatientToForm, 15),
							"Send Patient To form is displayed");


					m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver,oPage_OT.button_sendToNurseModalForm),
							"Send To Nurse Clicked");
					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.listButtons_sendToNurseModalForm.get(0),8);

					for (WebElement nurseRow : oPage_OT.listButtons_sendToNurseModalForm) {
						if (nurseRow.isDisplayed()) {

							try {
								List<WebElement> nurseDetailsOnRow = nurseRow.findElements(By.xpath("./child::*"));
								for (WebElement itemOnRow : nurseDetailsOnRow) {
									System.out.println("jijij "+ itemOnRow.getText());
									if (Cls_Generic_Methods.getTextInElement(itemOnRow).equals(sNurseName)) {
										m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, nurseRow), sNurseName + "Nurse selected for the patient.");
										bPatientSent = true;
										Thread.sleep(2000);
										break;
									}
								}
							} catch (ElementNotInteractableException e) {
								m_assert.assertTrue(false, "<b>Nurse not selected for the patient</b> " + e);
								e.printStackTrace();
							}

						}
						if (bPatientSent == true) {
							m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OT.header_messageFormOt, 10),
									"Message form is displayed");

							m_assert.assertTrue(
									Cls_Generic_Methods.sendKeysIntoElement(oPage_OT.input_reasonForSendingPatient, sReasonForPatient),
									"Remark comments are entered: " + sReasonForPatient);

                           m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver,oPage_OT.button_sendToUser),
								   "Send to: <b> "+sNurseName+ "</b>  clicked. ");
							Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,16);

							break;
						}
					}

				}
			}catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("Exception while admitting patient to OT" + e);
			}
		}catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}
	}

}
