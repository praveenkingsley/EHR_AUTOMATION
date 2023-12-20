package tests.settings.facilitySetting.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.Settings_Data;
import data.settingsData.FacilitySettings_Data;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.settings.facilitySettings.general.referralSources.Page_ReferralSources;

public class ReferralSourceTest extends TestBase {

	static Model_Patient myPatient;
	String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
	EHR_Data oEHR_Data = new EHR_Data();
	FacilitySettings_Data oFacilitySettings_Data = new FacilitySettings_Data();

	@Test(enabled = true, description = "Desc")
	public void validateAddSubReferral() {

		Page_ReferralSources oPage_ReferralSources = new Page_ReferralSources(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
//		Page_Settings oPage_Settings = new Page_Settings(driver);
		String sFacilityName = "TESTING_FACILITY";

		try {
			myPatient = map_PatientsInExcel.get(patientKey);
			String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
			CommonActions.loginFunctionality(expectedLoggedInUser);

			try {
				CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
				Cls_Generic_Methods.customWait(1);
				CommonActions.selectOptionFromLeftInSettingsPanel("General", "Referral Sources");

				for(String sReferralType: oFacilitySettings_Data.list_AvailableReferralOptions){

					Cls_Generic_Methods.scrollToTop();
					Cls_Generic_Methods.clickElement(oPage_ReferralSources.button_AddSubReferral);
					m_assert.assertInfo("Clicked on <b>Add Sub Referral</b> Button ");
					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_modal, 8);

					Cls_Generic_Methods.selectElementByVisibleText(oPage_ReferralSources.select_ReferralTypeOnModal ,sReferralType);
					m_assert.assertTrue("Selected Referral Type Option = <b>" + sReferralType + "</b>");
					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ReferralSources.select_FacilityOnModal, 8);


					Cls_Generic_Methods.selectElementByVisibleText(oPage_ReferralSources.select_FacilityOnModal, sFacilityName);
					if(Cls_Generic_Methods.isElementDisplayed(oPage_ReferralSources.input_SubReferral_Name)){
						Cls_Generic_Methods.sendKeysIntoElement(oPage_ReferralSources.input_SubReferral_Name,
								FacilitySettings_Data.sREFERRAL_NAME + sReferralType);
						m_assert.assertTrue("Entered Sub Referral Name = <b>" + FacilitySettings_Data.sREFERRAL_NAME + sReferralType + "</b>");
					}

					fillEssentialDataOnModalBasedOnReferralType(sReferralType);

					Cls_Generic_Methods.clickElement(oPage_ReferralSources.button_SaveOnModal);
					m_assert.assertInfo("Clicked on Save Button ");
					Cls_Generic_Methods.customWait(5);

					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ReferralSources.select_ReferralType, 16);
					Select select = new Select(oPage_ReferralSources.select_ReferralType);
					WebElement selectedOption = select.getFirstSelectedOption();
					String selectedReferralTypeText = Cls_Generic_Methods.getTextInElement(selectedOption);

					if(!selectedReferralTypeText.equalsIgnoreCase(sReferralType)){
						Cls_Generic_Methods.selectElementByVisibleText(oPage_ReferralSources.select_ReferralType, sReferralType);
					}

					boolean subReferralMatched = false;
					for(WebElement eValueInSubSourceOnTable : oPage_ReferralSources.list_SubSourceValuesOnTable){
						String sTableValue = Cls_Generic_Methods.getTextInElement(eValueInSubSourceOnTable);
						System.out.println((FacilitySettings_Data.sREFERRAL_NAME + sReferralType).toUpperCase());

						if(sTableValue.contains((FacilitySettings_Data.sREFERRAL_NAME + sReferralType).toUpperCase())){
							subReferralMatched = true;
							break;
						}
						if(subReferralMatched){
							break;
						}
					}
					m_assert.assertTrue(subReferralMatched, "Validate Sub Referral = <b>" + FacilitySettings_Data.sREFERRAL_NAME + sReferralType + "</b> added");
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

	@Test(enabled = true, description = "Desc")
	public void validateEditReferralFunctionality() {

		Page_ReferralSources oPage_ReferralSources = new Page_ReferralSources(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		myPatient = map_PatientsInExcel.get(patientKey);

		try {
			String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
			CommonActions.loginFunctionality(expectedLoggedInUser);

			try {
				CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
				Cls_Generic_Methods.customWait(1);
				CommonActions.selectOptionFromLeftInSettingsPanel("General", "Referral Sources");

				for(String sReferralType: oFacilitySettings_Data.list_AvailableReferralOptions){
					Cls_Generic_Methods.scrollToTop();

					Select select = new Select(oPage_ReferralSources.select_ReferralType);
					WebElement selectedOption = select.getFirstSelectedOption();
					String selectedReferralTypeText = Cls_Generic_Methods.getTextInElement(selectedOption);

					if(!selectedReferralTypeText.equalsIgnoreCase(sReferralType)){
						Cls_Generic_Methods.selectElementByVisibleText(oPage_ReferralSources.select_ReferralType, sReferralType);
						Cls_Generic_Methods.customWait(1);
					}
					oPage_ReferralSources = new Page_ReferralSources(driver);

					boolean bEditActionPerformed = false;
					int requiredIndexOfRow = -1;

					for(WebElement eValueInSubSourceOnTable : oPage_ReferralSources.list_SubSourceValuesOnTable){
						Cls_Generic_Methods.customWait(5);
						oPage_ReferralSources = new Page_ReferralSources(driver);

						String sTableValue = Cls_Generic_Methods.getTextInElement(eValueInSubSourceOnTable);

						if(sTableValue.contains((FacilitySettings_Data.sREFERRAL_NAME + sReferralType).toUpperCase())){
							requiredIndexOfRow = oPage_ReferralSources.list_SubSourceValuesOnTable.indexOf(eValueInSubSourceOnTable);
							break;
						}
					}

					Cls_Generic_Methods.clickElement(oPage_ReferralSources.list_EditButtonsOnTable.get(requiredIndexOfRow));
					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_modal , 12);

					Cls_Generic_Methods.clearValuesInElement(oPage_ReferralSources.input_SubReferral_Name);
					Cls_Generic_Methods.sendKeysIntoElement(oPage_ReferralSources.input_SubReferral_Name,
							"UPDATED " + FacilitySettings_Data.sREFERRAL_NAME + sReferralType);
					Cls_Generic_Methods.clickElement(oPage_ReferralSources.button_SaveOnModal);
					m_assert.assertInfo("Clicked on Save Button ");
					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ReferralSources.select_ReferralType, 16);
                    Cls_Generic_Methods.customWait(5);


					for(WebElement eValueInSubSourceOnTable : oPage_ReferralSources.list_SubSourceValuesOnTable){
						String sTableValue = Cls_Generic_Methods.getTextInElement(eValueInSubSourceOnTable);
						System.out.println(("UPDATED " + FacilitySettings_Data.sREFERRAL_NAME + sReferralType).toUpperCase());

						if(sTableValue.contains(("UPDATED " + FacilitySettings_Data.sREFERRAL_NAME + sReferralType).toUpperCase())){
							bEditActionPerformed = true;
							break;
						}
						if(bEditActionPerformed){
							break;
						}
					}
					m_assert.assertTrue(bEditActionPerformed, "Validate Edited the Sub Referral = <b>" +
							"UPDATED " + FacilitySettings_Data.sREFERRAL_NAME + sReferralType + "</b>");

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

	@Test(enabled = true, description = "Desc")
	public void validateAddedReferralInOPD() {

		Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
		boolean addedSubReferralFound = false;
		String settingsTabHandle = driver.getWindowHandle();

		try {
			myPatient = map_PatientsInExcel.get(patientKey);
			String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;

			if(driver.getWindowHandles().size() == 1){
				driver.switchTo().newWindow(WindowType.TAB);
			}
			CommonActions.loginFunctionality(expectedLoggedInUser);

			try {

				try {
					if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
						CommonActions.openPatientRegisterationAndAppointmentForm();
					}
				} catch (NoSuchElementException e) {
					CommonActions.openPatientRegisterationAndAppointmentForm();
					CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
							"Patient Details");
				}
				Cls_Generic_Methods.scrollToElementByJS(oPage_NewPatientRegisteration.text_PatientReferralSource);

				for(String sReferralType: oFacilitySettings_Data.list_AvailableReferralOptions) {
					addedSubReferralFound = false;
					String updatedReferralValue = ("UPDATED " + FacilitySettings_Data.sREFERRAL_NAME).toUpperCase() + sReferralType;

					m_assert.assertInfo(
							Cls_Generic_Methods.clickElement(driver,
									oPage_NewPatientRegisteration.span_patientReferralSrcOnPatientRegForm),
							" Patient Referral Source Selector clicked");
					Cls_Generic_Methods.customWait(1);


					m_assert.assertInfo(
							CommonActions.selectOptionFromListBasedOnTextOrValue(
									oPage_NewPatientRegisteration.options_patientReferralSrcOnPatientRegForm,
									sReferralType),
							sReferralType + " selected for Patient Referral Source");

					if(oFacilitySettings_Data.list_AvailableReferralOptions.indexOf(sReferralType)==0){
						Cls_Generic_Methods.customWait(6);
					}else{
						Cls_Generic_Methods.customWait(4);
					}

					try {
						Cls_Generic_Methods.selectElementByVisibleText(
								oPage_NewPatientRegisteration.select_patientSubReferralSrcOnPatientRegForm, updatedReferralValue);
						addedSubReferralFound = true;

					} catch (NoSuchElementException e) {

						try {
							Cls_Generic_Methods.customWait();
							Cls_Generic_Methods.selectElementByVisibleText(
									oPage_NewPatientRegisteration.select_patientSubReferralSrcOnPatientRegForm, updatedReferralValue);
							addedSubReferralFound = true;
						} catch (Exception ex) {
							ex.printStackTrace();
							m_assert.assertFatal("" + ex);
						}
					}

					m_assert.assertTrue(addedSubReferralFound, updatedReferralValue + " selected for Sub Patient Referral Source");

				}

			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}

			driver.close();
			driver.switchTo().window(settingsTabHandle);

		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}
	}

	@Test(enabled = true, description = "Desc")
	public void clearAddedReferral() {

		Page_ReferralSources oPage_ReferralSources = new Page_ReferralSources(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		myPatient = map_PatientsInExcel.get(patientKey);
		boolean bDeleteActionPerformed = false;
		String sTableValue = null;

		try {
			String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
			CommonActions.loginFunctionality(expectedLoggedInUser);

			try {
				CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
				Cls_Generic_Methods.customWait(1);
				CommonActions.selectOptionFromLeftInSettingsPanel("General", "Referral Sources");

				for(String sReferralType: oFacilitySettings_Data.list_AvailableReferralOptions){
					Cls_Generic_Methods.scrollToTop();

					Select select = new Select(oPage_ReferralSources.select_ReferralType);
					WebElement selectedOption = select.getFirstSelectedOption();
					String selectedReferralTypeText = Cls_Generic_Methods.getTextInElement(selectedOption);

					if(!selectedReferralTypeText.equalsIgnoreCase(sReferralType)){
						Cls_Generic_Methods.selectElementByVisibleText(oPage_ReferralSources.select_ReferralType, sReferralType);
						Cls_Generic_Methods.customWait(1);
					}

					oPage_ReferralSources = new Page_ReferralSources(driver);
					bDeleteActionPerformed = false;
					int requiredIndexOfRow = -1;

					for(WebElement eValueInSubSourceOnTable : oPage_ReferralSources.list_SubSourceValuesOnTable){
						Cls_Generic_Methods.customWait();
						oPage_ReferralSources = new Page_ReferralSources(driver);

						sTableValue = Cls_Generic_Methods.getTextInElement(eValueInSubSourceOnTable);
//						System.out.println((Settings_Data.sREFERRAL_NAME + sReferralType).toUpperCase());
//						System.out.println(sTableValue);

						if(sTableValue.contains((FacilitySettings_Data.sREFERRAL_NAME + sReferralType).toUpperCase())){
							requiredIndexOfRow = oPage_ReferralSources.list_SubSourceValuesOnTable.indexOf(eValueInSubSourceOnTable);
							break;
						}
					}

					oPage_ReferralSources = new Page_ReferralSources(driver);
//					Cls_Generic_Methods.clickElement(oPage_ReferralSources.list_DeleteButtonsOnTable.get(requiredIndexOfRow));
					m_assert.assertInfo(Cls_Generic_Methods.clickElement(
							oPage_ReferralSources.list_DeleteButtonsOnTable.get(requiredIndexOfRow)),
							"Validate Delete button for the Sub Referral = <b>" + sTableValue + "</b> is clicked.");
					Cls_Generic_Methods.customWait();

					for(WebElement eConfirmBtn: oPage_ReferralSources.button_DeleteOnModal){
						System.out.println();
						if(Cls_Generic_Methods.isElementDisplayed(eConfirmBtn)){
							bDeleteActionPerformed = Cls_Generic_Methods.clickElementByAction (driver, eConfirmBtn);
							Cls_Generic_Methods.customWait(4);
							m_assert.assertTrue(bDeleteActionPerformed, "Validate Confirm for Delete of the Sub Referral = <b>" +
									sTableValue + "</b> is clicked.");
							sTableValue = null;
						}
					}

					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ReferralSources.select_ReferralType, 16);

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

	/*
	* Added To Handle extra input fields after name for different Referral Types
	*/
	public boolean fillEssentialDataOnModalBasedOnReferralType(String sInputReferralType){
		boolean essentialDataFilled = false;
		Page_ReferralSources oPage_ReferralSources = new Page_ReferralSources(driver);

		try {

			switch (sInputReferralType) {

				case "Referring Doctor":
					break;
				case "Staff Referral":
					break;
				case "Campaign":
					Cls_Generic_Methods.clickElement(oPage_ReferralSources.selectButton_CampaignOnModal);
					m_assert.assertTrue("Selected Campaign Type Option = <b>" +
							Cls_Generic_Methods.getTextInElement(oPage_ReferralSources.list_selectOptionsForCampaignOnModal.get(1)) +
							"</b>");
					Cls_Generic_Methods.clickElement(oPage_ReferralSources.list_selectOptionsForCampaignOnModal.get(1));
					break;
				case "Camp":
					break;
				case "Institutional Referral":
					break;
				case "Agent":
					break;
				case "Online":
					break;
				case "Third Party":
					break;
				case "Consultant":
					break;
				default:
					break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}

		return essentialDataFilled;
	}

}
