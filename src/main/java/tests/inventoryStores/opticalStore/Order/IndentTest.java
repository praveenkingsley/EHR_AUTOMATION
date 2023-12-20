package tests.inventoryStores.opticalStore.Order;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;

public class IndentTest extends TestBase {

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

}
