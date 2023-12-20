package tests.inventoryStores.opticalStore;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.Optometrist_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.common_tabs.Page_DiagnosisTab;
import pages.commonElements.common_tabs.Page_HistoryTab;
import pages.commonElements.common_tabs.Page_RefractionTab;
import pages.commonElements.common_tabs.advice.Page_AdviceTab;
import pages.commonElements.common_tabs.investigation.Page_InvestigationTab;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.commonElements.templates.eye.Page_EyeTemplate;
import pages.ipd.Page_IPD;
import pages.ipd.forms.intraOperative.Page_OperativeForm;
import pages.ipd.forms.intraOperative.Page_PreAnaesthesiaTemplate;
import pages.ipd.forms.postOperative.Page_DischargeForm;
import pages.ipd.forms.preOperative.Page_AdmissionInPreOperative;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.store.OpticalStore.Page_SalesOrder;
import pages.store.Page_PatientQueue;
import pages.store.Page_Store;

import java.util.ArrayList;
import java.util.List;

public class PatientQueueTest extends TestBase {

	static Model_Patient myPatient;

	public static String myMedicationName = "SalesOrderTest1";
	String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
	EHR_Data oEHR_Data = new EHR_Data();
	boolean patientSelectedOPD = false;
	String concatPatientFullName = "";
	String sStore = "OpticalStore- Optical";
	int myQueueTabCount , allTabCount , convertedTabCount , notConvertedTabCount = 0;
	int myQueueTabCountPharmacy , allTabCountPharmacy , convertedTabCountPharmacy , notConvertedTabCountPharmacy = 0;
	int updatedMyQueueTabCount , updatedAllTabCount , updatedConvertedTabCount , updatedNotConvertedTabCount = 0;
	List<String> patientInformationList = new ArrayList<>();
	List<String> patientHistoryDetailsList = new ArrayList<>();

	public static String sDistantSph = "+0.25";
	public static String sDistantCyl = "+0.25";
	public static String sDistantAxis = "10";
	public static String sDistantVision = "3/60";
	public static String sSphAdd = "+0.50";
	public static String sNearSph = "";
	public static String sNearVision = "N8";
	public static String sFrameMaterial = "Shell";
	public static String sIPD = "10";
	public static String sLensMaterial = "Mineral";
	public static String sLensTint = "White";
	public static String sTypeOfLens = "Bifocal";
	public static String sDia = "12";
	public static String sSize = "11";
	public static String sFittingHeight = "14";
	public static String sPrismBase = "13";
	public static String sGlassPrescriptionComment = "Glass Prescription Advised";
	public static String input_Qty = "1";
	String sStorePharmacy = "Pharmacy automation- Pharmacy";
	static String sTxnDate = "";
	static String sTxnTime = "";


	@Test(enabled = true, description = "Creating Patient TO Validate Patient Flow To Inventory")
	public void createPatientToValidatePatientInventoryFollow() {
		Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
		Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
		Page_OPD oPage_OPD = new Page_OPD(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_Store oPage_Store = new Page_Store(driver);
		Page_Navbar oPage_Navbar = new Page_Navbar(driver);



		try {
			myPatient = map_PatientsInExcel.get(patientKey);
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

			m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Patient Queue");
			Cls_Generic_Methods.switchToOtherTab();
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
			m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
					"Store pop up closed");
			Cls_Generic_Methods.customWait();
			myQueueTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeMyQueueTabCountsInPatientQueuePage));
			allTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeAllTabCountsInPatientQueuePage));
			convertedTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeConvertedCountsInPatientQueuePage));
			notConvertedTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeNotConvertedCountsInPatientQueuePage));
			Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
			Cls_Generic_Methods.customWait(5);

			Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_storesSelector);
			Cls_Generic_Methods.customWait();
			m_assert.assertTrue(CommonActions.selectStoreOnApp(sStorePharmacy), sStorePharmacy + " Store Opened For Validation Of Patient Queue");
			Cls_Generic_Methods.switchToOtherTab();
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
			m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
					"Store pop up closed");
			Cls_Generic_Methods.customWait();
			myQueueTabCountPharmacy = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeMyQueueTabCountsPharmacyInPatientQueuePage));
			allTabCountPharmacy = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeAllTabCountsPharmacyInPatientQueuePage));
			convertedTabCountPharmacy = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeConvertedCountsPharmacyInPatientQueuePage));
			notConvertedTabCountPharmacy = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeNotConvertedCountsPharmacyInPatientQueuePage));
			Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
			Cls_Generic_Methods.customWait(5);

			try {

				// Open the Search/Add patient dialog box
				try {
					if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
						CommonActions.openPatientRegisterationAndAppointmentForm();
					} else {
						CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm, "Patient Details");
						Cls_Generic_Methods.customWait();
					}
				} catch (NoSuchElementException e1) {
					CommonActions.openPatientRegisterationAndAppointmentForm();
				}

				// Entering Essential Form Data
				if (!myPatient.getsSALUTATION().isEmpty()) {
					m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()), "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
				}

				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()), "First Name is entered as - " + myPatient.getsFIRST_NAME());
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, myPatient.getsMOBILE_NUMBER()), "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

				//Select DateOf Birth
				String[] dobArray= myPatient.getsDATE_OF_BIRTH().split("/");

				m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.select_dobDateDay),"Date of Birth field clicked");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.select_dobYear,10);
				m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_NewPatientRegisteration.select_dobYear,
						dobArray[2]),"Select "+dobArray[2]+" in year");

				int month= Integer.parseInt(dobArray[1])-1;
				int date=Integer.parseInt(dobArray[0]);

				m_assert.assertInfo( Cls_Generic_Methods.selectElementByValue(oPage_NewPatientRegisteration.select_dobMonth,String.valueOf(month)),
						"Select "+Cls_Generic_Methods.getTextInElement(oPage_NewPatientRegisteration.getMonth(String.valueOf(month)))+" in month");
				m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.select_dobDate(String.valueOf(date))),
						"Select  "+String.valueOf(date)+" in day");

				m_assert.assertTrue("Entered "+myPatient.getsDATE_OF_BIRTH()+" in Date Of Birth field");

				m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm), "Validate that Create Appointment button is clicked");

				Cls_Generic_Methods.customWait(6);
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 20);

				concatPatientFullName = CommonActions.getFullPatientName(myPatient).toUpperCase().trim();
				patientSelectedOPD = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
				m_assert.assertTrue(patientSelectedOPD, " Patient Created Successfully In OPD");

				String age = Cls_Generic_Methods.getTextInElement(oPage_OPD.text_patientAgeInAppointmentDetails);
				String dateOfBirth = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd-MM-yyyy", myPatient.getsDATE_OF_BIRTH());

				concatPatientFullName = concatPatientFullName+" (M)";
                String patientId = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.text_patientID);
				patientInformationList.add(concatPatientFullName);
				patientInformationList.add(patientId);
				patientInformationList.add(dateOfBirth+" | "+age);
				patientInformationList.add(myPatient.getsMOBILE_NUMBER());

			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}

	}

	@Test(enabled = true, description = "Create Eye Template With Details To Flow Patient TO Store")
	public void createEyeTemplate() {

		Page_EyeTemplate oPage_EyeTemplate = new Page_EyeTemplate(driver);
		Page_HistoryTab oPage_HistoryTab = new Page_HistoryTab(driver);
		Page_RefractionTab oPage_RefractionTab = new Page_RefractionTab(driver);
		Page_AdviceTab oPage_AdviceTab = new Page_AdviceTab(driver);
		Page_OPD oPage_OPD = new Page_OPD(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_DiagnosisTab oPage_DiagnosisTab = new Page_DiagnosisTab(driver);
		Page_InvestigationTab oPage_InvestigationTab = new Page_InvestigationTab(driver);
		String EyeTemplate = "Eye";
		myPatient = TestBase.map_PatientsInExcel.get(patientKey);
		Optometrist_Data oOptometrist_Data = new Optometrist_Data();
		String ophthalmicHistory = "";
		String ophthalmicHistoryComment = "Ophthalmic History Comment";
		String systemicHistory = "";
		String systemicHistoryComment = "Systemic History Comment";
		String ophthalmicRightDuration = "1";
		String ophthalmicRightDurationUnit = "Days";
		String systemicDuration = "1";
		String systemicDurationUnit = "Days";
		String familyHistoryComment = "Family History Comment";
		String medicalHistoryComment = "Medical History Comment";
        String drugAllergies = "";
		String contactAllergies = "";
		String foodAllergies = "";
		String drugAllergiesComment = "Drug Allergies Comment";
		String contactAllergiesComment = "Contact Allergies Comment";
		String foodAllergiesComment = "Food Allergies Comment";
		String otherAllergiesComment = "Other Allergies Comment";
		String contactDuration = "2";
		String contactDurationUnit = "Weeks";
		String foodDuration = "3";
		String foodDurationUnit = "Weeks";




		try {

			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			concatPatientFullName = CommonActions.getFullPatientName(myPatient).toUpperCase().trim();
			patientSelectedOPD = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

			if (patientSelectedOPD) {
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
				m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, EyeTemplate), "Validate " + EyeTemplate + " template  is selected");

				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.tab_RefractionTab, 20);

				m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.tab_HistoryTab), "clicked on history tab");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_HistoryTab.checkbox_NIL_Ophthalmic_History,5);
				Cls_Generic_Methods.clickElementByJS(driver,oPage_HistoryTab.checkbox_NIL_Chief_Complaints);
				Cls_Generic_Methods.customWait();

				for (WebElement ophthalmicHistoryBtn : oPage_HistoryTab.list_buttonOphthalmicHistory) {
					ophthalmicHistory = Cls_Generic_Methods.getTextInElement(ophthalmicHistoryBtn);
					m_assert.assertInfo(Cls_Generic_Methods.clickElement(ophthalmicHistoryBtn),
							ophthalmicHistory + " in Ophthalmic History is clicked");
					Cls_Generic_Methods.customWait();
					m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_rightDurationOphthalmicList,ophthalmicRightDuration),
							" Right Eye Duration entered as : "+ophthalmicRightDuration);
					Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_rightDurationUnitOphthalmicList,ophthalmicRightDurationUnit);
					Cls_Generic_Methods.clickElement(oPage_HistoryTab.button_copyButtonInGlaucoma);
					Cls_Generic_Methods.customWait();
					Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.textarea_ophthalmicHistoryCommentBox, ophthalmicHistoryComment);
					break;
				}

				for (WebElement systemicHistoryBtn : oPage_HistoryTab.list_buttonSystemicHistory) {
					systemicHistory = Cls_Generic_Methods.getTextInElement(systemicHistoryBtn);
					Cls_Generic_Methods.scrollToElementByJS(systemicHistoryBtn);
					Cls_Generic_Methods.customWait();
					m_assert.assertInfo(Cls_Generic_Methods.clickElement(systemicHistoryBtn),
							systemicHistory + " in Systemic History is clicked");
					Cls_Generic_Methods.customWait();
					Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_durationSystemicList,systemicDuration);
					Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_durationUnitSystemicList,systemicDurationUnit);
					Cls_Generic_Methods.customWait();
					Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.textarea_systemicHistoryCommentBox, systemicHistoryComment);
					break;
				}

				m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_familyHistory,familyHistoryComment),
						" Family History Entered as :<b> "+ familyHistoryComment +"</b>" );
				m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_medicalHistory,medicalHistoryComment),
						" Medical History Entered as :<b> "+ medicalHistoryComment +"</b>");


				for (WebElement drugAllergiesBtn : oPage_HistoryTab.list_buttonDrugAllergies) {
					drugAllergies = Cls_Generic_Methods.getTextInElement(drugAllergiesBtn);
					m_assert.assertInfo(Cls_Generic_Methods.clickElement(drugAllergiesBtn),
							drugAllergies + " as Drug Allergies in All Allergies is clicked");
					Cls_Generic_Methods.customWait();
					Cls_Generic_Methods.clickElement(oPage_HistoryTab.button_ampicillinAntimicrobialAgents);
					Cls_Generic_Methods.customWait(1);
					drugAllergies = Cls_Generic_Methods.getTextInElement(oPage_HistoryTab.button_ampicillinAntimicrobialAgents);
					Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_durationAntimicrobialAgentsList,ophthalmicRightDuration);
					Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_durationUnitAntimicrobialAgentsList,ophthalmicRightDurationUnit);
					Cls_Generic_Methods.customWait();
					Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.textarea_drugAllergiesCommentBox,drugAllergiesComment);
					break;
				}

				for (WebElement contactAllergiesBtn : oPage_HistoryTab.list_buttonContactAllergies) {
					contactAllergies = Cls_Generic_Methods.getTextInElement(contactAllergiesBtn);
					m_assert.assertInfo(Cls_Generic_Methods.clickElement(contactAllergiesBtn),
							contactAllergies + " as Contact Allergies in All Allergies is clicked");
					Cls_Generic_Methods.customWait();
					Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_durationContactAllergiesList,contactDuration);
					Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_durationUnitContactAllergiesList,contactDurationUnit);
					Cls_Generic_Methods.customWait();
					Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.textarea_contactAllergiesCommentBox,contactAllergiesComment);

					break;
				}

				for (WebElement foodAllergiesBtn : oPage_HistoryTab.list_buttonFoodAllergies) {
					foodAllergies = Cls_Generic_Methods.getTextInElement(foodAllergiesBtn);
					m_assert.assertInfo(Cls_Generic_Methods.clickElement(foodAllergiesBtn),
							foodAllergies + " as Food Allergies in All Allergies is clicked");
					Cls_Generic_Methods.customWait();
					Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_durationFoodAllergiesList,foodDuration);
					Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_durationUnitFoodAllergiesList,foodDurationUnit);
					Cls_Generic_Methods.customWait();
					Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.textarea_foodAllergiesCommentBox,foodAllergiesComment);

					break;
				}

				m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_otherAllergies,otherAllergiesComment),
						" Other History Entered as :<b> "+ otherAllergiesComment +"</b>");

				String ophthalmicHistoryText = ophthalmicHistory+" Left Eye since "+ophthalmicRightDuration+" "+
						ophthalmicRightDurationUnit+" Right Eye since "+ophthalmicRightDuration+" "+ophthalmicRightDurationUnit+"";

				String systemicHistoryText = systemicHistory+" "+systemicDuration+" "+systemicDurationUnit;
				String drugAllergiesText = drugAllergies+" Since "+ophthalmicRightDuration+" "+ophthalmicRightDurationUnit;
				String contactAllergiesText = contactAllergies+" Since "+contactDuration+" "+contactDurationUnit;
				String foodAllergiesText = foodAllergies+" Since "+foodDuration+" "+foodDurationUnit;



				patientHistoryDetailsList.add(ophthalmicHistoryText+" ,   "+systemicHistoryText+" ,  "+ophthalmicHistoryComment+" ,  "+systemicHistoryComment);
				/*patientHistoryDetailsList.add(systemicHistoryText);
				patientHistoryDetailsList.add(ophthalmicHistoryComment);
				patientHistoryDetailsList.add(systemicHistoryComment);*/
				patientHistoryDetailsList.add(medicalHistoryComment);
				patientHistoryDetailsList.add(familyHistoryComment);
				patientHistoryDetailsList.add(drugAllergiesText+" ,  "+drugAllergiesComment);
				//patientHistoryDetailsList.add(drugAllergiesComment);
				patientHistoryDetailsList.add(contactAllergiesText+" ,  "+contactAllergiesComment);
			//	patientHistoryDetailsList.add(contactAllergiesComment);
				patientHistoryDetailsList.add(foodAllergiesText+" ,  "+foodAllergiesComment);
			//	patientHistoryDetailsList.add(foodAllergiesComment);
				patientHistoryDetailsList.add(otherAllergiesComment);


				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.tab_RefractionTab, 8);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_RefractionTab.tab_RefractionTab), "Validate clicked on Refraction tab");

				try {
					for (WebElement buttonElement : oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityRight) {

						int index = oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityRight.indexOf(buttonElement);

						boolean validateButtonFunctionality = false;
						validateButtonFunctionality = CommonActions.validateIf_EHR_ButtonIsClickable(buttonElement);
						m_assert.assertInfo(validateButtonFunctionality, "Validate "
								+ oOptometrist_Data.list_UCVA_ABSENT.get(index) + " R/OD Button is Clickable");

						m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, buttonElement), "Validate "
								+ oOptometrist_Data.list_UCVA_ABSENT.get(index) + " R/OD Button is Clicked");
						break;
					}
					m_assert.assertTrue(
							Cls_Generic_Methods.sendKeysIntoElement(
									oPage_RefractionTab.input_ucvaCommentUnderVisualAcuityRightSide,
									oOptometrist_Data.sUCVA_COMMENT),
							"Validate Visual Acuity for right eye Comment is entered as "
									+ oOptometrist_Data.sUCVA_COMMENT);

					m_assert.assertTrue(
							Cls_Generic_Methods.sendKeysIntoElement(
									oPage_RefractionTab.input_CommentUnderVisualAcuityRightSide,
									oOptometrist_Data.sUCVA_COMMENT),
							"Validate R/OD Visual Acuity Comment is entered as "
									+ oOptometrist_Data.sVISUAL_ACUITY_COMMENT);

				} catch (Exception e) {
					e.printStackTrace();
					m_assert.assertFatal("Unable to validate R/OD Section under visual acuity - \n" + e);
				}

				try {
					for (WebElement buttonElement : oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityLeft) {

						int index = oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityLeft.indexOf(buttonElement);

						boolean validateButtonFunctionality = CommonActions.validateIf_EHR_ButtonIsClickable(buttonElement);

						m_assert.assertTrue(validateButtonFunctionality, "Validate "
								+ oOptometrist_Data.list_UCVA_ABSENT.get(index) + " L/OS Button is Clickable");
						m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, buttonElement), "Validate "
								+ oOptometrist_Data.list_UCVA_ABSENT.get(index) + " L/OS Button is Clicked");
						break;
					}


					m_assert.assertTrue(
							Cls_Generic_Methods.sendKeysIntoElement(
									oPage_RefractionTab.input_ucvaCommentUnderVisualAcuityLeftSide,
									oOptometrist_Data.sUCVA_COMMENT),
							"Validate Visual Acuity for left eye Comment is entered as "
									+ oOptometrist_Data.sUCVA_COMMENT);
					m_assert.assertTrue(
							Cls_Generic_Methods.sendKeysIntoElement(
									oPage_RefractionTab.input_CommentUnderVisualAcuityLefttSide,
									oOptometrist_Data.sUCVA_COMMENT),
							"Validate L/OD Visual Acuity Comment is entered as "
									+ oOptometrist_Data.sVISUAL_ACUITY_COMMENT);
				} catch (Exception e) {
					e.printStackTrace();
					m_assert.assertFatal("Unable to validate L/OS side absent Section under visual acuity - \n" + e);
				}

				Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_rightIop);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_rightIop, myPatient.getsIOP_RIGHT()), "Right Eye intraocularPressure is " + myPatient.getsIOP_RIGHT());

				Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_leftIop);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_leftIop, myPatient.getsIOP_LEFT()), "Left Eye intraocularPressure is " + myPatient.getsIOP_RIGHT());

				validateRefraction_GLASS_PRESCRIPTION();

				m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_InvestigationTab.tab_investigation),
						"Investigation Tab Is selected");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InvestigationTab.checkBox_NoInvestigation,2);
				m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver,oPage_InvestigationTab.checkBox_NoInvestigation),
						" No Investigation Checkbox Selected");

				String sProvisionalDiagnosisComment = "Demo Provisional Diagnosis Comment";

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_DiagnosisTab.tab_diagnosis), "Diagnosis Tab Is selected");
				m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_DiagnosisTab.checkbox_provisionalDiagnosis), "Provisional diagnosis checkbox is clicked");
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_DiagnosisTab.input_provisionalDiagnosisComments, sProvisionalDiagnosisComment),
						"Validate provisional diagnosis Comment is entered as Smoke Test");

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.tab_advice), "Advice Tab Is selected");
				m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_AdviceTab.select_medicationSets, "automation set"),
						"Selected Medication Set as - " + "cataract");
				Cls_Generic_Methods.customWait();

				try {
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.tab_proceduresUnderAdviceTab), "Procedures Tab Is selected");
					m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_AdviceTab.checkbox_noProceduresAdvised), "No Procedures check box is checked");
					Cls_Generic_Methods.customWait();
				} catch (Exception e) {
					e.printStackTrace();
					m_assert.assertFatal("Error while validating Procedures. \n" + e);
				}

				try {
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.tab_adviceUnderAdviceTab), "Advice Tab Is selected");

				} catch (Exception e) {
					e.printStackTrace();
					m_assert.assertFatal("Error while validating Advice. \n" + e);
				}


				//Click On Save Button
				try {
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Save button is clicked");
					Cls_Generic_Methods.customWait(4);
				} catch (Exception e) {
					e.printStackTrace();
					m_assert.assertWarn("" + e);
				}

				m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OPD.text_headerOPDSummary, 15), "Upon clicking Save template, opd summary is displayed");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");

				//After Close wait till user drop down display
				m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_EyeTemplate.text_sendToDepartmentSection, 15), "Upon clicking close template send to user dropdown is displayed");


			}
			else{
				m_assert.assertTrue("Patient Not Found");
			}


		} catch (Exception e) {
			m_assert.assertTrue(false, "<b>Eye Template is not selected. </b> " + e);
			e.printStackTrace();
		}


	}

	@Test(enabled = true, description = "Validation Patient and Template Details In Store")
	public void validatingPatientDetailsInOpticalStore() {

		Page_Store oPage_Store = new Page_Store(driver);
		Page_Navbar oPage_Navbar = new Page_Navbar(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);
		Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);

		String[] glassPrescriptionTableHeaderList = {"Sph","Cyl","Axis","Vision"};
		String[] glassPrescriptionTableDistantValueList = {"Distant",sDistantSph,sDistantCyl,sDistantAxis,sDistantVision};
		String[] glassPrescriptionTableNearValueList = {"Near",sNearSph,sDistantCyl,sDistantAxis,sNearVision};

		String[] glassPrescriptionHeaderListInSalesOrder = {"Type Of Lens","IPD","Size","Lens Material","Lens Tint","Frame Material",
		                                                  "Dia","Fitting Height","Prism Base","Comments"};
		String[] storeTabList = {"My Queue","All","Converted","Not Converted"};



		try {
			myPatient = map_PatientsInExcel.get(patientKey);
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);

			try {

				m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();

				m_assert.assertInfo(storeTabList[0] + " Tab Count Before Glass Prescription Advised Was : "+myQueueTabCount);
				m_assert.assertInfo(storeTabList[1] + " Tab Count Before Glass Prescription Advised Was : "+allTabCount);
				m_assert.assertInfo(storeTabList[2] + " Tab Count Before Glass Prescription Advised Was : "+convertedTabCount);
				m_assert.assertInfo(storeTabList[3] + " Tab Count Before Glass Prescription Advised Was : "+notConvertedTabCount);

				updatedMyQueueTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeMyQueueTabCountsInPatientQueuePage));
				updatedAllTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeAllTabCountsInPatientQueuePage));
				updatedConvertedTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeConvertedCountsInPatientQueuePage));
				updatedNotConvertedTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeNotConvertedCountsInPatientQueuePage));

				if(updatedMyQueueTabCount == myQueueTabCount && updatedAllTabCount == (allTabCount+1) && updatedConvertedTabCount == convertedTabCount
				       && notConvertedTabCount == updatedNotConvertedTabCount){
					m_assert.assertTrue(" Store Tab Count Increased Correct , As Only All Tab Count Increased By One "+ updatedAllTabCount);
				} else{
					m_assert.assertWarn( " Either All Tab Count Not Increasing or something wrong ");
				}
				m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_Store.tabs_storeTabsInPatientQueuePage, storeTabList[1]),
						"Validate " + storeTabList[1] + " tab is selected");

				Cls_Generic_Methods.customWait(3);

				concatPatientFullName = CommonActions.getFullPatientName(myPatient).toUpperCase().trim();
				boolean bValidatePatientFound = CommonActions.selectPatientNameInPatientQueue(oPage_PatientQueue.rows_patientAppointmentsInPatientQueue,concatPatientFullName);

				m_assert.assertTrue(bValidatePatientFound, "Validate patient " + concatPatientFullName + " is found");

				String patientStatus = Cls_Generic_Methods.getTextInElement(oPage_PatientQueue.text_patientStatusInStore);

				m_assert.assertTrue(patientStatus.equalsIgnoreCase("Advised"),
						" Patient Status Showed as Advised correctly");

				CommonActions.validatingCommonElementsInStorePatientDetailsRHS(patientInformationList,patientHistoryDetailsList);

				Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.title_glassPrescriptionTitle);
				String glassPrescriptionTitleText = Cls_Generic_Methods.getTextInElement(oPage_PatientQueue.title_glassPrescriptionTitle);
				m_assert.assertTrue(" Glass Prescription Header Present as : "+ glassPrescriptionTitleText);

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.text_glassAdvisedROD),
						" ROD Header Present ");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.text_glassAdvisedLOS),
						" LOS Header Present ");

				for(WebElement eGlassAdvisedHeader : oPage_PatientQueue.list_advisedGlassTableHeader){
					int indexOfeGlassAdvisedHeader = oPage_PatientQueue.list_advisedGlassTableHeader.indexOf(eGlassAdvisedHeader);
					String glassPrescriptionEyeSide = "";
					int indexOfROD = 1 ;
					int indexOfLOS = 6 ;
					int indexOfActualTableHeader = 0 ;
					if(indexOfeGlassAdvisedHeader < 5){
						indexOfActualTableHeader = indexOfeGlassAdvisedHeader-indexOfROD ;
						glassPrescriptionEyeSide = "ROD";
					}else{
						indexOfActualTableHeader = indexOfeGlassAdvisedHeader-indexOfLOS ;
						glassPrescriptionEyeSide = "LOS";

					}

					String textOfGlassAdvisedTableHeader = Cls_Generic_Methods.getTextInElement(eGlassAdvisedHeader);
                   if( indexOfeGlassAdvisedHeader != 0 &&  indexOfeGlassAdvisedHeader != 5)
					   if(textOfGlassAdvisedTableHeader.equalsIgnoreCase(glassPrescriptionTableHeaderList[indexOfActualTableHeader])){
						   m_assert.assertTrue(textOfGlassAdvisedTableHeader +" Header Present In Glass Prescription Table "+ glassPrescriptionEyeSide);
					   }else{
						   m_assert.assertFalse(textOfGlassAdvisedTableHeader +" Not Header Present In Glass Prescription Table ");
					   }
				}

				for(WebElement eGlassAdvisedValue : oPage_PatientQueue.list_advisedGlassTableValue) {
					int indexOfeGlassAdvisedValue = oPage_PatientQueue.list_advisedGlassTableValue.indexOf(eGlassAdvisedValue);
					String textOfGlassAdvisedValue = Cls_Generic_Methods.getTextInElement(eGlassAdvisedValue);

					if (indexOfeGlassAdvisedValue < 5 ) {
						if (textOfGlassAdvisedValue.equalsIgnoreCase(glassPrescriptionTableDistantValueList[indexOfeGlassAdvisedValue])) {
							m_assert.assertTrue(textOfGlassAdvisedValue + " Value Present In Glass Prescription Table ");
						} else {
							m_assert.assertFalse(textOfGlassAdvisedValue + " Not Value Present In Glass Prescription Table ");
						}
					}
					 else {
						 if (indexOfeGlassAdvisedValue < 10 ) {
							 indexOfeGlassAdvisedValue = indexOfeGlassAdvisedValue - 5;

						if (textOfGlassAdvisedValue.equalsIgnoreCase(glassPrescriptionTableNearValueList[indexOfeGlassAdvisedValue])) {
							m_assert.assertTrue(textOfGlassAdvisedValue + " Value Present In Glass Prescription Table ");
						} else {
							m_assert.assertFalse(textOfGlassAdvisedValue + " Not Value Present In Glass Prescription Table ");
						}
					}
						 else{
							 if (indexOfeGlassAdvisedValue < 15 ) {
								 indexOfeGlassAdvisedValue = indexOfeGlassAdvisedValue - 10;
								 if (textOfGlassAdvisedValue.equalsIgnoreCase(glassPrescriptionTableDistantValueList[indexOfeGlassAdvisedValue])) {
								 m_assert.assertTrue(textOfGlassAdvisedValue + " Value Present In Glass Prescription Table ");
							 } else {
								 m_assert.assertFalse(textOfGlassAdvisedValue + " Not Value Present In Glass Prescription Table ");
							 }
						 }
							 else {
								 indexOfeGlassAdvisedValue = indexOfeGlassAdvisedValue - 15;
								 if (textOfGlassAdvisedValue.equalsIgnoreCase(glassPrescriptionTableNearValueList[indexOfeGlassAdvisedValue])) {
									 m_assert.assertTrue(textOfGlassAdvisedValue + " Value Present In Glass Prescription Table ");
								 } else {
									 m_assert.assertFalse(textOfGlassAdvisedValue + " Not Value Present In Glass Prescription Table ");
								 }
							 }
						 }
					}
				}

					for(WebElement eFrame : oPage_PatientQueue.glassPrescription_FrameMaterialDetails){
						int index = oPage_PatientQueue.glassPrescription_FrameMaterialDetails.indexOf(eFrame);
						String glassPrescriptionEyeSide = "LOS";
						if(index == 0){
							glassPrescriptionEyeSide = "ROD" ;
						}
						String frameMaterialText = Cls_Generic_Methods.getTextInElement(eFrame);
						if(frameMaterialText.contains(sFrameMaterial)){
							m_assert.assertTrue(frameMaterialText + " Present In Glass Prescription Section In "+ glassPrescriptionEyeSide);
						}else {
							m_assert.assertFalse(frameMaterialText + " Not Present in Glass Prescription");
						}
					}

					for(WebElement eIpd : oPage_PatientQueue.glassPrescription_IpdDetails){
						int index = oPage_PatientQueue.glassPrescription_IpdDetails.indexOf(eIpd);
						String glassPrescriptionEyeSide = "LOS";
						if(index == 0){
							glassPrescriptionEyeSide = "ROD" ;
						}
						String ipdText = Cls_Generic_Methods.getTextInElement(eIpd).replace("(\r\n|\n|\r)/gm", "");
						if(ipdText.contains(sIPD)){
							m_assert.assertTrue(ipdText + " Present In Glass Prescription Section "+ glassPrescriptionEyeSide);
						}else {
							m_assert.assertFalse(ipdText + " Not Present in Glass Prescription");
						}
					}

					for(WebElement eLensMaterial : oPage_PatientQueue.glassPrescription_LensMaterialDetails){
						int index = oPage_PatientQueue.glassPrescription_LensMaterialDetails.indexOf(eLensMaterial);
						String glassPrescriptionEyeSide = "LOS";
						if(index == 0){
							glassPrescriptionEyeSide = "ROD" ;
						}
						String lensMaterialText = Cls_Generic_Methods.getTextInElement(eLensMaterial).replace("(\r\n|\n|\r)/gm", "");
						if(lensMaterialText.contains(sLensMaterial)){
							m_assert.assertTrue(lensMaterialText + " Present In Glass Prescription Section "+ glassPrescriptionEyeSide);
						}else {
							m_assert.assertFalse(lensMaterialText + " Not Present in Glass Prescription");
						}
					}

					for(WebElement eLensTint : oPage_PatientQueue.glassPrescription_LensTintDetails){
						int index = oPage_PatientQueue.glassPrescription_LensTintDetails.indexOf(eLensTint);
						String glassPrescriptionEyeSide = "LOS";
						if(index == 0){
							glassPrescriptionEyeSide = "ROD" ;
						}
						String lensTintText = Cls_Generic_Methods.getTextInElement(eLensTint).replace("(\r\n|\n|\r)/gm", "");
						if(lensTintText.contains(sLensTint)){
							m_assert.assertTrue(lensTintText + " Present In Glass Prescription Section " + glassPrescriptionEyeSide);
						}else {
							m_assert.assertFalse(lensTintText + " Not Present in Glass Prescription");
						}
					}

					for(WebElement eTypeOfLens : oPage_PatientQueue.glassPrescription_TypeOfLensDetails){

						int index = oPage_PatientQueue.glassPrescription_TypeOfLensDetails.indexOf(eTypeOfLens);
						String glassPrescriptionEyeSide = "LOS";
						if(index == 0){
							glassPrescriptionEyeSide = "ROD" ;
						}
						String typeOfLensText = Cls_Generic_Methods.getTextInElement(eTypeOfLens).replace("(\r\n|\n|\r)/gm", "");
						if(typeOfLensText.contains(sTypeOfLens)){
							m_assert.assertTrue(typeOfLensText + " Present In Glass Prescription Section "+glassPrescriptionEyeSide);
						}else {
							m_assert.assertFalse(typeOfLensText + " Not Present in Glass Prescription");
						}
					}

					for(WebElement eDia : oPage_PatientQueue.glassPrescription_DiaDetails){

						int index = oPage_PatientQueue.glassPrescription_DiaDetails.indexOf(eDia);
						String glassPrescriptionEyeSide = "LOS";
						if(index == 2){
							glassPrescriptionEyeSide = "ROD" ;
						}
						if(index>0) {
							String diaText = Cls_Generic_Methods.getTextInElement(eDia).replace("(\r\n|\n|\r)/gm", "");
							if (diaText.contains(sDia)) {
								m_assert.assertTrue(diaText + " Present In Glass Prescription Section " + glassPrescriptionEyeSide);
							} else {
								m_assert.assertFalse(diaText + " Not Present in Glass Prescription");
							}
						}
					}

					for(WebElement eSize : oPage_PatientQueue.glassPrescription_SizeDetails){
						int index = oPage_PatientQueue.glassPrescription_SizeDetails.indexOf(eSize);
						String glassPrescriptionEyeSide = "LOS";
						if(index == 0){
							glassPrescriptionEyeSide = "ROD" ;
						}
						String sizeText = Cls_Generic_Methods.getTextInElement(eSize).replace("(\r\n|\n|\r)/gm", "");
						if(sizeText.contains(sSize)){
							m_assert.assertTrue(sizeText + " Present In Glass Prescription Section "+ glassPrescriptionEyeSide);
						}else {
							m_assert.assertFalse(sizeText + " Not Present in Glass Prescription");
						}
					}

					for(WebElement eFittingHeight : oPage_PatientQueue.glassPrescription_FittingHeightDetails){

						int index = oPage_PatientQueue.glassPrescription_FittingHeightDetails.indexOf(eFittingHeight);
						String glassPrescriptionEyeSide = "LOS";
						if(index == 0){
							glassPrescriptionEyeSide = "ROD" ;
						}

						String fittingHeightText = Cls_Generic_Methods.getTextInElement(eFittingHeight).replace("(\r\n|\n|\r)/gm", "");
						if(fittingHeightText.contains(sFittingHeight)){
							m_assert.assertTrue(fittingHeightText + " Present In Glass Prescription Section "+glassPrescriptionEyeSide);
						}else {
							m_assert.assertFalse(fittingHeightText + " Not Present in Glass Prescription");
						}
					}

					for(WebElement ePrismBase : oPage_PatientQueue.glassPrescription_PrismBaseDetails){

						int index = oPage_PatientQueue.glassPrescription_PrismBaseDetails.indexOf(ePrismBase);
						String glassPrescriptionEyeSide = "LOS";
						if(index == 0){
							glassPrescriptionEyeSide = "ROD" ;
						}

						String prismBaseText = Cls_Generic_Methods.getTextInElement(ePrismBase).replace("(\r\n|\n|\r)/gm", "");
						if(prismBaseText.contains(sPrismBase)){
							m_assert.assertTrue(prismBaseText + " Present In Glass Prescription Section "+glassPrescriptionEyeSide);
						}else {
							m_assert.assertFalse(prismBaseText + " Not Present in Glass Prescription");
						}
					}

					for(WebElement eComments : oPage_PatientQueue.list_advisedGlassPrescriptionComments){

						int index = oPage_PatientQueue.glassPrescription_DiaDetails.indexOf(eComments);
						String glassPrescriptionEyeSide = "LOS";
						if(index == 0){
							glassPrescriptionEyeSide = "ROD" ;
						}

						String commentsText = Cls_Generic_Methods.getTextInElement(eComments).replace("(\r\n|\n|\r)/gm", "");
						if(commentsText.contains(sGlassPrescriptionComment)){
							m_assert.assertTrue(commentsText + " Present In Glass Prescription Section "+glassPrescriptionEyeSide);
						}else {
							m_assert.assertFalse(commentsText + " Not Present in Glass Prescription");
						}
					}


					if(!Cls_Generic_Methods.isElementDisplayed(oPage_Store.button_yesMarkConvertedButton)){
						Cls_Generic_Methods.clickElement(oPage_Store.button_markPatientVisitedButton);
						Cls_Generic_Methods.customWait(2);
					}

					Cls_Generic_Methods.clickElement(oPage_Store.button_yesMarkConvertedButton);
					Cls_Generic_Methods.customWait(5);
				sTxnDate = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_TxnDate, "value");
				sTxnTime = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderTime, "value");

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.input_salesOrderDoctorName),
							" Doctor Name Field Displayed On Sales Order template");
					String salesOrderDoctorName = Cls_Generic_Methods.getElementAttribute(oPage_Store.input_salesOrderDoctorName,"value");

					m_assert.assertTrue(salesOrderDoctorName.equalsIgnoreCase(EHR_Data.user_PRAkashTest),
							"Doctor Name is Displayed in Sales Order Template as : "+ salesOrderDoctorName);

					m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.text_glassPrescriptionAdvisedOn),
							" Glass Prescription heading present as "+Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_glassPrescriptionAdvisedOn));

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.text_glassAdvisedROD),
						" ROD Header Present In Sales Order ");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.text_glassAdvisedLOS),
						" LOS Header Present In Sales Order");

					for(WebElement headers : oPage_PatientQueue.list_advisedGlassPrescriptionHeadersInSalesOrder){

						int index = oPage_PatientQueue.list_advisedGlassPrescriptionHeadersInSalesOrder.indexOf(headers);
						String glassPrescriptionEyeSide = "ROD";
						if(index >= 10){
							index = index - 10 ;
							glassPrescriptionEyeSide = "LOS" ;
						}

						String headerTextInSalesOrder = Cls_Generic_Methods.getTextInElement(headers);
						if(headerTextInSalesOrder.equalsIgnoreCase(glassPrescriptionHeaderListInSalesOrder[index])){

							m_assert.assertTrue(headerTextInSalesOrder+" header present in glass prescription table in sales order in "+ glassPrescriptionEyeSide + " side");
						}else{
							m_assert.assertFalse(headerTextInSalesOrder+" header not present in glass prescription table in sales order in "+ glassPrescriptionEyeSide + " side");
						}

					}

				  for(WebElement eGlassAdvisedHeader : oPage_PatientQueue.list_advisedGlassTableHeaderInSalesOrder) {
					  int indexOfeGlassAdvisedHeader = oPage_PatientQueue.list_advisedGlassTableHeaderInSalesOrder.indexOf(eGlassAdvisedHeader);
					  if (indexOfeGlassAdvisedHeader > 1){
						  int indexOfROD = 2;
					  int indexOfLOS = 7;
					  int indexOfActualTableHeader = 0;
						  String glassPrescriptionEyeSide = "";
						  if (indexOfeGlassAdvisedHeader < 6) {
						  indexOfActualTableHeader = indexOfeGlassAdvisedHeader - indexOfROD;
							   glassPrescriptionEyeSide = "ROD";

						  } else {
						  indexOfActualTableHeader = indexOfeGlassAdvisedHeader - indexOfLOS;
						  glassPrescriptionEyeSide = "LOS";

						  }
					  String textOfGlassAdvisedTableHeader = Cls_Generic_Methods.getTextInElement(eGlassAdvisedHeader);
					  if (indexOfeGlassAdvisedHeader != 6 ){
						  if (textOfGlassAdvisedTableHeader.equalsIgnoreCase(glassPrescriptionTableHeaderList[indexOfActualTableHeader])) {
							  m_assert.assertTrue(textOfGlassAdvisedTableHeader + " Header Present In Glass Prescription Table "+glassPrescriptionEyeSide);
						  } else {
							  m_assert.assertFalse(textOfGlassAdvisedTableHeader + " Not Header Present In Glass Prescription Table ");
						  }
					  }
				  }

				}

				 for(WebElement eGlassAdvisedValue : oPage_PatientQueue.list_advisedGlassTableValueInSalesOrder) {

					int indexOfeGlassAdvisedValue = oPage_PatientQueue.list_advisedGlassTableValueInSalesOrder.indexOf(eGlassAdvisedValue);

					 if( indexOfeGlassAdvisedValue == 0  || indexOfeGlassAdvisedValue == 6 || indexOfeGlassAdvisedValue == 12){

						 WebElement printCheckbox = eGlassAdvisedValue.findElement(By.xpath("./input"));
						 m_assert.assertTrue(Cls_Generic_Methods.radioButtonIsSelected(printCheckbox),
								 "Print Button Displayed and Selected In Sales Order at index :"+indexOfeGlassAdvisedValue);
					 }
					 else {

						 String textOfGlassAdvisedValue = Cls_Generic_Methods.getTextInElement(eGlassAdvisedValue);
						 if (indexOfeGlassAdvisedValue < 6 ) {
							 indexOfeGlassAdvisedValue = indexOfeGlassAdvisedValue - 1;
							 if (textOfGlassAdvisedValue.equalsIgnoreCase(glassPrescriptionTableDistantValueList[indexOfeGlassAdvisedValue])) {
								 m_assert.assertTrue(textOfGlassAdvisedValue + " Value Present In Glass Prescription Table ");
							 } else {
								 m_assert.assertFalse(textOfGlassAdvisedValue + " Not Value Present In Glass Prescription Table ");
							 }
						 }
						 else {
							 if (indexOfeGlassAdvisedValue >= 13 && indexOfeGlassAdvisedValue < 18) {
								 indexOfeGlassAdvisedValue = indexOfeGlassAdvisedValue - 13;

								 if (textOfGlassAdvisedValue.equalsIgnoreCase(glassPrescriptionTableNearValueList[indexOfeGlassAdvisedValue])) {
									 m_assert.assertTrue(textOfGlassAdvisedValue + " Value Present In Glass Prescription Table ");
								 } else {
									 m_assert.assertFalse(textOfGlassAdvisedValue + " Not Value Present In Glass Prescription Table ");
								 }
							 }
							 else{
								 if (indexOfeGlassAdvisedValue >= 18 &&  indexOfeGlassAdvisedValue < 23) {
									 indexOfeGlassAdvisedValue = indexOfeGlassAdvisedValue - 18;
									 if (textOfGlassAdvisedValue.equalsIgnoreCase(glassPrescriptionTableDistantValueList[indexOfeGlassAdvisedValue])) {
										 m_assert.assertTrue(textOfGlassAdvisedValue + " Value Present In Glass Prescription Table ");
									 } else {
										 m_assert.assertFalse(textOfGlassAdvisedValue + " Not Value Present In Glass Prescription Table ");
									 }
								 }
								 else if ( indexOfeGlassAdvisedValue >= 28 && indexOfeGlassAdvisedValue < 32){
									 indexOfeGlassAdvisedValue = indexOfeGlassAdvisedValue - 28;
									 if (textOfGlassAdvisedValue.equalsIgnoreCase(glassPrescriptionTableNearValueList[indexOfeGlassAdvisedValue])) {
										 m_assert.assertTrue(textOfGlassAdvisedValue + " Value Present In Glass Prescription Table ");
									 } else {
										 m_assert.assertFalse(textOfGlassAdvisedValue + " Not Value Present In Glass Prescription Table ");
									 }
								 }
								 else{ if(indexOfeGlassAdvisedValue > 32){
									 break ;
								 }
								 }
							 }
						 }
					 }
				}

				 m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_PatientQueue.input_rightSideGlassPrescriptionTypeOfLens).
						 equalsIgnoreCase(sTypeOfLens)," Type Of Lens Displayed Correctly In Sales Order as : "+sTypeOfLens);
				m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_PatientQueue.input_leftSideGlassPrescriptionTypeOfLens).
						equalsIgnoreCase(sTypeOfLens)," Type Of Lens Displayed Correctly In Sales Order as : "+sTypeOfLens);

				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_rightSideGlassPrescriptionIpd,"value").
						equalsIgnoreCase(sIPD)," IPD Value Displayed Correctly In Sales Order as : "+sIPD);
				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_leftSideGlassPrescriptionIpd,"value").
						equalsIgnoreCase(sIPD)," IPD Value Displayed Correctly In Sales Order as : "+sIPD);

				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_rightSideGlassPrescriptionSize,"value").
						equalsIgnoreCase(sSize)," Size Value Displayed Correctly In Sales Order as : "+sSize);
				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_leftSideGlassPrescriptionSize,"value").
						equalsIgnoreCase(sSize)," Size Value Displayed Correctly In Sales Order as : "+sSize);

				m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_PatientQueue.input_rightSideGlassPrescriptionLensMaterial).
						equalsIgnoreCase(sLensMaterial)," Lens Material Displayed Correctly In Sales Order as : "+sLensMaterial);
				m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_PatientQueue.input_leftSideGlassPrescriptionLensMaterial).
						equalsIgnoreCase(sLensMaterial)," Lens Material Displayed Correctly In Sales Order as : "+sLensMaterial);

				m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_PatientQueue.input_rightSideGlassPrescriptionLensTint).
						equalsIgnoreCase(sLensTint)," Lens Tint Displayed Correctly In Sales Order as : "+sLensTint);
				m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_PatientQueue.input_leftSideGlassPrescriptionLensTint).
						equalsIgnoreCase(sLensTint)," Lens Tint Displayed Correctly In Sales Order as : "+sLensTint);


				m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_PatientQueue.input_rightSideGlassPrescriptionFrameMaterial).
						equalsIgnoreCase(sFrameMaterial)," Frame Material Displayed Correctly In Sales Order as : "+sFrameMaterial);
				m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_PatientQueue.input_leftSideGlassPrescriptionFrameMaterial).
						equalsIgnoreCase(sFrameMaterial)," Frame Material  Displayed Correctly In Sales Order as : "+sFrameMaterial);

				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_rightSideGlassPrescriptionDia,"value").
						equalsIgnoreCase(sDia)," Dia Displayed Correctly In Sales Order as : "+sDia);
				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_leftSideGlassPrescriptionDia,"value").
						equalsIgnoreCase(sDia)," Dia Displayed Correctly In Sales Order as : "+sDia);

				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_rightSideGlassPrescriptionFittingHeight,"value").
						equalsIgnoreCase(sFittingHeight)," Fitting Height Displayed Correctly In Sales Order as : "+sFittingHeight);
				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_leftSideGlassPrescriptionFittingHeight,"value").
						equalsIgnoreCase(sFittingHeight)," Fitting Height Displayed Correctly In Sales Order as : "+sFittingHeight);

				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_rightSideGlassPrescriptionPrismBase,"value").
						equalsIgnoreCase(sPrismBase)," Prism Base Displayed Correctly In Sales Order as : "+sPrismBase);
				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_leftSideGlassPrescriptionPrismBase,"value").
						equalsIgnoreCase(sPrismBase)," Prism Base  Displayed Correctly In Sales Order as : "+sPrismBase);

				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_rightSideGlassPrescriptionComment,"value").
						equalsIgnoreCase(sGlassPrescriptionComment)," Glass Comment Displayed Correctly In Sales Order as : "+sGlassPrescriptionComment);
				m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_leftSideGlassPrescriptionComment ,"value").
						equalsIgnoreCase(sGlassPrescriptionComment)," Glass Comment  Displayed Correctly In Sales Order as : "+sGlassPrescriptionComment);


				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_searchMedicineNameInDescription, myMedicationName), "Entering the medication name as" + myMedicationName + " in description textbox for sales order");
				Cls_Generic_Methods.customWait(3);
				oPage_SalesOrder.input_searchMedicineNameInDescription.sendKeys(Keys.ENTER);
				Cls_Generic_Methods.customWait(3);
					boolean myMedicationFound = false;
					Cls_Generic_Methods.customWait(5);

					for (WebElement eMedication : oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult) {
//                    Cls_Generic_Methods.customWait(5);
						if(Cls_Generic_Methods.isElementDisplayed(eMedication)){
							if (Cls_Generic_Methods.getTextInElement(eMedication).contains(myMedicationName)) {
								Cls_Generic_Methods.clickElement(eMedication);
								myMedicationFound = true;
								Cls_Generic_Methods.customWait(4);
								break;
							}
						}
					}
					m_assert.assertTrue(myMedicationFound, "Required medication " + myMedicationName + " found for Sales Order");

					m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_quantityOfMedicine, input_Qty),
							"Entered Quantity " + input_Qty + " for Sales Order");
					Cls_Generic_Methods.customWait(5);

				m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SalesOrder.label_completedDeliveryRadioButtonLabel),
						"Completed Radio Button Clicked FOr Delivery Type ");
				Cls_Generic_Methods.customWait(2);
				m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_PatientQueue.select_modeOfPaymentInSalesOrder, "Cash"), "Required mode of payment (" + "Cash"+ ") selected");

				String totalAmountValue = Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_totalAmount,"value");
				Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientQueue.input_totalAmountInPayment,totalAmountValue);

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_SaveChanges),
							"Save Changes Button CLicked");
					Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_AdvancePaid, 20);
				    Cls_Generic_Methods.customWait(4);
					Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_closeModalOfSalesOrder);
					Cls_Generic_Methods.customWait(2);

					String patientUpdatedStatus = Cls_Generic_Methods.getTextInElement(oPage_PatientQueue.text_patientStatusInStore);

					m_assert.assertTrue(patientUpdatedStatus.equalsIgnoreCase("Converted"),
							" Patient Status Changed to Converted Successfully");
				 updatedConvertedTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeConvertedCountsInPatientQueuePage));
				m_assert.assertTrue(updatedConvertedTabCount == convertedTabCount +1 ,
						" Converted Tab Count Increased By 1  Successfully");

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.button_emailButton),
						"Email Button displayed Successfully");

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.button_printButton),
						"Print Button displayed Successfully");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.checkbox_printFollowupButton),
						"Print Followup Button displayed Successfully");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.label_patientState),
						"Patient State Label displayed Successfully");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.text_patientStatus),
						"Converted patent State displayed Successfully");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.button_validatePrescription),
						"Validate Prescription Button displayed Successfully");

				CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_addNewButtonInOrder, 8);
				String patientName  = CommonActions.getFullPatientName(myPatient).trim();
				Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory,patientName);
				Cls_Generic_Methods.customWait(3);
				oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
				Cls_Generic_Methods.customWait(3);
				boolean bSalesOrderFound = false;
				String sDate = sTxnDate.replace("/", "-");
				String sTime = sTxnTime.replace(" ", "");
				for (WebElement eSalesOrder : oPage_SalesOrder.list_namesofSalesOrder) {
					String eSalesOrderName = Cls_Generic_Methods.getTextInElement(eSalesOrder);
					if (eSalesOrderName.contains(sDate) && eSalesOrderName.contains(sTime) && eSalesOrderName.contains(patientName)) {
						Cls_Generic_Methods.clickElement(eSalesOrder);
						bSalesOrderFound = true;
						break;
					}
				}

                m_assert.assertTrue(bSalesOrderFound , "Order For Converted Patient Found In Sales Order");
				Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
				Cls_Generic_Methods.customWait();
				Cls_Generic_Methods.driverRefresh();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 20);
				Cls_Generic_Methods.customWait(5);

			}
			catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}

	}

	@Test(enabled = true, description = "Validation Patient and Template Details In Pharmacy Store")
	public void validatingPatientDetailsInPharmacyStore() {

		Page_Store oPage_Store = new Page_Store(driver);
		Page_Navbar oPage_Navbar = new Page_Navbar(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);
		Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
		Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
		String[] storeTabList = {"My Queue","All","Converted","Not Converted"};
		String[]  medicationTableHeaderList = {"Sr No.","Name","Quantity","Frequency","Duration","Eye","Instruction"};
		String[]  medicationTableDataList = {"1","Dolo - TAB","1","1","1 Day","Left","One time a day"};



		try {
			myPatient = map_PatientsInExcel.get(patientKey);
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);

			try {

				m_assert.assertTrue(CommonActions.selectStoreOnApp(sStorePharmacy), sStorePharmacy + " Store Opened For Validation Of Patient Queue");
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait();

				m_assert.assertInfo(storeTabList[0] + " Tab Count Before Medication  Advised Was : "+myQueueTabCountPharmacy);
				m_assert.assertInfo(storeTabList[1] + " Tab Count Before Medication Advised Was : "+allTabCountPharmacy);
				m_assert.assertInfo(storeTabList[2] + " Tab Count Before Medication Advised Was : "+convertedTabCountPharmacy);
				m_assert.assertInfo(storeTabList[3] + " Tab Count Before Medication Advised Was : "+notConvertedTabCountPharmacy);

				updatedMyQueueTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeMyQueueTabCountsPharmacyInPatientQueuePage));
				updatedAllTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeAllTabCountsPharmacyInPatientQueuePage));
				updatedConvertedTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeConvertedCountsPharmacyInPatientQueuePage));
				updatedNotConvertedTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeNotConvertedCountsPharmacyInPatientQueuePage));

				if(updatedMyQueueTabCount == myQueueTabCountPharmacy && updatedAllTabCount == (allTabCountPharmacy+1) && updatedConvertedTabCount == convertedTabCountPharmacy
						&& notConvertedTabCountPharmacy == updatedNotConvertedTabCount){
					m_assert.assertTrue(" Store Tab Count Increased Correct , As Only All Tab Count Increased By One "+ updatedAllTabCount);
				} else{
					m_assert.assertWarn( " Either All Tab Count Not Increasing or something wrong ");
				}
				m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_Store.tabs_storeTabsIPharmacyPatientQueuePage, storeTabList[1]),
						"Validate " + storeTabList[1] + " tab is selected");

				Cls_Generic_Methods.customWait(3);

				concatPatientFullName = CommonActions.getFullPatientName(myPatient).toUpperCase().trim();
				boolean bValidatePatientFound = CommonActions.selectPatientNameInPatientQueue(oPage_PatientQueue.rows_patientAppointmentsInPharmacyPatientQueue,concatPatientFullName);

				m_assert.assertTrue(bValidatePatientFound, "Validate patient " + concatPatientFullName + " is found");

				String patientStatus = Cls_Generic_Methods.getTextInElement(oPage_PatientQueue.text_patientStatusInPharmacyStore);

				m_assert.assertTrue(patientStatus.equalsIgnoreCase("Advised"),
						" Patient Status Showed as Advised correctly");

				CommonActions.validatingCommonElementsInStorePatientDetailsRHS(patientInformationList,patientHistoryDetailsList);

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_billButton),
						"Bill Button Displayed and Clicked");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_advanceReceiptButton, 4);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_advanceReceiptButton),
						"Advance Button Displayed and Clicked ");
				m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_advanceReceiptTemplate, 5),
						" Advance Receipt Template Opened");
				Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
				Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.button_medicationPrescriptionHeader),
						" Medication Prescription Header displayed as : "+Cls_Generic_Methods.getTextInElement(oPage_PatientQueue.button_medicationPrescriptionHeader));

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.label_langaugeLabelDisplayed),
						"Language Label Displayed");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.list_LangaugeSelectDropdown),
						"Language Dropdown Displayed and Clickable");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.text_newLabelText),
						"New Label Displayed");

				for(WebElement header : oPage_PatientQueue.rows_medicationTableHeaderListInPatientQueue){

					int index = oPage_PatientQueue.rows_medicationTableHeaderListInPatientQueue.indexOf(header);
					String headerText = Cls_Generic_Methods.getTextInElement(header);
					if(headerText.equalsIgnoreCase(medicationTableHeaderList[index])){
						m_assert.assertTrue(headerText + " Present in Medication Prescription Table");
					}else{
						m_assert.assertTrue(headerText + " Not Present in Medication Prescription Table");
					}
				}

				for(WebElement header : oPage_PatientQueue.rows_medicationTabledataListInPatientQueue){

					int index = oPage_PatientQueue.rows_medicationTabledataListInPatientQueue.indexOf(header);
					String valueText = Cls_Generic_Methods.getTextInElement(header);
					if(valueText.equalsIgnoreCase(medicationTableDataList[index])){
						m_assert.assertTrue(valueText + " Present in Medication Prescription Table as Advised In Patient");
					}else{
						m_assert.assertTrue(valueText + " Value Not Present in Medication Prescription Table");
					}
				}

				if(!Cls_Generic_Methods.isElementDisplayed(oPage_Store.button_yesMarkConvertedButton)){
					Cls_Generic_Methods.clickElement(oPage_Store.button_markPatientVisitedButton);
					Cls_Generic_Methods.customWait(2);
				}

				Cls_Generic_Methods.clickElement(oPage_Store.button_yesMarkConvertedButton);
				Cls_Generic_Methods.customWait(5);

                sTxnTime = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderTime, "value");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.input_salesOrderDoctorName),
						" Doctor Name Field Displayed On Sales Order template");
				String salesOrderDoctorName = Cls_Generic_Methods.getElementAttribute(oPage_Store.input_salesOrderDoctorName,"value");

				m_assert.assertTrue(salesOrderDoctorName.equalsIgnoreCase(EHR_Data.user_PRAkashTest),
						"Doctor Name is Displayed in Sales Order Template as : "+ salesOrderDoctorName);

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.button_medicationPrescriptionHeaderInSalesOrder),
						" Medication Prescription Header Displayed In Sales Order as :"+Cls_Generic_Methods.getTextInElement(oPage_PatientQueue.button_medicationPrescriptionHeaderInSalesOrder));

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.text_newLabelText),
						"New Label Displayed in sales order");


				for(WebElement header : oPage_PatientQueue.rows_medicationTableHeaderListInSalesOrder){

					int index = oPage_PatientQueue.rows_medicationTableHeaderListInSalesOrder.indexOf(header);
					String headerText = Cls_Generic_Methods.getTextInElement(header);
					if(headerText.equalsIgnoreCase(medicationTableHeaderList[index])){
						m_assert.assertTrue(headerText + " Present in Medication Prescription Table in sales order");
					}else{
						m_assert.assertTrue(headerText + " Not Present in Medication Prescription Table in sales order");
					}
				}

				for(WebElement header : oPage_PatientQueue.rows_medicationTabledataListInSalesOrder){


					int index = oPage_PatientQueue.rows_medicationTabledataListInSalesOrder.indexOf(header);
					String valueText = Cls_Generic_Methods.getTextInElement(header);
					if(valueText.equalsIgnoreCase(medicationTableDataList[index])){
						m_assert.assertTrue(valueText + " Present in Medication Prescription Table as Advised In Sales Order");
					}else{
						m_assert.assertTrue(valueText + " Value Not Present in Medication Prescription Table in Sales Order");
					}
				}

				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_searchMedicineNameInDescription, myMedicationName), "Entering the medication name as" + myMedicationName + " in description textbox for sales order");
				Cls_Generic_Methods.customWait(3);
				oPage_SalesOrder.input_searchMedicineNameInDescription.sendKeys(Keys.ENTER);
				Cls_Generic_Methods.customWait(3);
				boolean myMedicationFound = false;
				Cls_Generic_Methods.customWait(5);

				for (WebElement eMedication : oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult) {
//                    Cls_Generic_Methods.customWait(5);
					if(Cls_Generic_Methods.isElementDisplayed(eMedication)){
						if (Cls_Generic_Methods.getTextInElement(eMedication).contains(myMedicationName)) {
							Cls_Generic_Methods.clickElement(eMedication);
							myMedicationFound = true;
							Cls_Generic_Methods.customWait(4);
							break;
						}
					}
				}
				m_assert.assertTrue(myMedicationFound, "Required medication " + myMedicationName + " found for Sales Order");

				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_quantityOfMedicine, input_Qty),
						"Entered Quantity " + input_Qty + " for Sales Order");
				Cls_Generic_Methods.customWait(5);
				m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_PatientQueue.select_modeOfPaymentInSalesOrder, "Cash"), "Required mode of payment (" + "Cash"+ ") selected");

				String totalAmountValue = Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_totalAmount,"value");
				Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientQueue.input_totalAmountInPayment,totalAmountValue);

				sTxnDate = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_TxnDate, "value");
				sTxnTime = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderTime, "value");

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_SaveChanges),
						"Save Changes Button CLicked");
				Cls_Generic_Methods.customWait(5);
				Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_closeModalOfSalesOrder);
				Cls_Generic_Methods.customWait(2);

				String patientUpdatedStatus = Cls_Generic_Methods.getTextInElement(oPage_PatientQueue.text_patientStatusInPharmacyStore);

				m_assert.assertTrue(patientUpdatedStatus.equalsIgnoreCase("Converted"),
						" Patient Status Changed to Converted Successfully");
				updatedConvertedTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeConvertedCountsPharmacyInPatientQueuePage));
				m_assert.assertTrue(updatedConvertedTabCount == convertedTabCountPharmacy +1 ,
						" Converted Tab Count Increased By 1  Successfully");

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.button_emailButton),
						"Email Button displayed Successfully");

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.button_printButton),
						"Print Button displayed Successfully");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.checkbox_printFollowupButton),
						"Print Followup Button displayed Successfully");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.button_invoiceButton),
						"Invoice Button displayed Successfully");
				CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_addNewButtonInOrder, 8);
				String patientName  = CommonActions.getFullPatientName(myPatient).trim();
				Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory,patientName);
				Cls_Generic_Methods.customWait(5);
				boolean bSalesOrderFound = false;
				String sDate = sTxnDate.replace("/", "-");
				String sTime = sTxnTime.replace(" ", "");
				for (WebElement eSalesOrder : oPage_SalesOrder.list_namesofSalesOrder) {
					String eSalesOrderName = Cls_Generic_Methods.getTextInElement(eSalesOrder);
					if (eSalesOrderName.contains(sDate) && eSalesOrderName.contains(sTime) && eSalesOrderName.contains(patientName)) {
						Cls_Generic_Methods.clickElement(eSalesOrder);
						bSalesOrderFound = true;
						break;
					}
				}

				m_assert.assertTrue(bSalesOrderFound , "Order For Converted Patient Found In Sales Order");

				Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
				Cls_Generic_Methods.customWait();


			}
			catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}

	}

	@Test(enabled = true, description = "Validate schedule admission for patient")
	public void scheduleAdmissionFromOPD() throws Exception {

		Page_OPD oPage_OPD = new Page_OPD(driver);
		Page_ScheduleAdmission oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
		Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
		myPatient = TestBase.map_PatientsInExcel.get(patientKey);
		oEHR_Data = new EHR_Data();
		Page_IPD oPage_IPD  = new Page_IPD(driver);
		Page_AdmissionInPreOperative oPage_AdmissionInPreOperative = new Page_AdmissionInPreOperative(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_DischargeForm oPage_DischargeForm = new Page_DischargeForm(driver);
		Page_OperativeForm oPage_OperativeForm = new Page_OperativeForm(driver);
		Page_PreAnaesthesiaTemplate oPage_PreAnaesthesiaTemplate = new Page_PreAnaesthesiaTemplate(driver);




		String concatPatientFullName = "";
		boolean bPatientNameFound = false;
		String expectedLoggedInUser = "PR.Akash test";

		try {
			CommonActions.loginFunctionality(expectedLoggedInUser);

			try {
				String MyQueueTab = "My Queue";
				concatPatientFullName = CommonActions.getFullPatientName(myPatient);
				concatPatientFullName = concatPatientFullName.toUpperCase().trim();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage.get(0), 10);
				m_assert.assertTrue(
						CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
						"Validate " + MyQueueTab + " tab is selected");
				Thread.sleep(1000);
				bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

			} catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("Exception while getting patient" + e);
			}
			if (bPatientNameFound) {

				CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

				m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByAction(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission),
						"Scheduled admission button is displayed");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission),
						"Clicked on scheduled admission button");

				//Fill Schedule Admission Form
				m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_ScheduleAdmissionForm, 20),
						"Scheduled admission form is displayed");

				m_assert.assertEquals(Cls_Generic_Methods.getTextInElement(oPage_ScheduleAdmission.tab_activeScheduleAdmissionForm),"Admission Details","Admission Details Tab is selected on start by default.");

				//Admission Type Button Clickable validation
				for (WebElement radioAdmissionBtn : oPage_ScheduleAdmission.list_radioAdmissionType) {
					String admissionTypeBtn = Cls_Generic_Methods.getTextInElement(radioAdmissionBtn);
					if (admissionTypeBtn.equalsIgnoreCase("Same Day")) {
						m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, radioAdmissionBtn), admissionTypeBtn + " Button in Admission Type is Clickable");
						break;
					}
				}

				Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_viewCaseDetails);
				Cls_Generic_Methods.customWait();
				//Create Admission
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_createAdmission),
						"Create admission button is clicked");

				Cls_Generic_Methods.customWait(5);

				//Assign Bed
				m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_assignBed, 15),
						"Assigned bed Form is displayed");

				try {
					if (Cls_Generic_Methods.isElementDisplayed(oPage_ScheduleAdmission.header_assignBed)) {
						m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.input_selectWard),
								"Ward dropdown Clicked");
						m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_ScheduleAdmission.input_selectWard, 1),
								"Ward Value Selected");
						Cls_Generic_Methods.customWait(1);
						m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.input_selectRoom),
								"SelectRoom dropdown Clicked");
						m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_ScheduleAdmission.input_selectRoom, 1),
								"SelectRoom value Selected");
						Cls_Generic_Methods.customWait(1);
						m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_saveBed),
								"Clicked on Save bed");
						Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);

					} else {
						m_assert.assertTrue(false, "Assign Bed Form Not displayed. ");
					}
				} catch (Exception e) {
					e.printStackTrace();
					m_assert.assertFatal("Unable to Select Room and assign Bed" + e);
				}

				Cls_Generic_Methods.customWait(3);
				CommonActions.selectDepartmentOnApp("OPD");

				Cls_Generic_Methods.customWait(5);
				CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_jumpToAdmissionForSingleAdmission,
						30);

				Cls_Generic_Methods.scrollToElementByJS(oPage_PatientAppointmentDetails.button_jumpToAdmissionForSingleAdmission);
				Cls_Generic_Methods.customWait(2);
				Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_jumpToAdmissionForSingleAdmission);
				Cls_Generic_Methods.customWait(10);

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_readyForAdmission),
						"Ready for admission Button clicked ");

				Cls_Generic_Methods.customWait(15);
				/*m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_IPD.button_admitPatient, 20),
						"Admit Patient Button visible");*/

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_admitPatient),
						"Admit Patient Button clicked and visible ");

				m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_IPD.header_admissionForm, 20),
						"Admission form is displayed");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_saveAdmissionForm),
						"Admission Form Saved. ");
				Cls_Generic_Methods.customWait(15);

				Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.button_admissionInPreOperative);

				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AdmissionInPreOperative.tab_AdminTabOnAdmissionUnderPreOperative, 16);

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.button_saveOnModalHeader),
						"Validate Save button is clicked on Admission under Pre-Operative");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);
				Cls_Generic_Methods.customWait(6);
				Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_preOperativeSection, 16);
				m_assert.assertTrue(
						Cls_Generic_Methods.clickElement(driver, oPage_DischargeForm.button_dischargeTemplate),
						"clicked on Discharge template ");
				Cls_Generic_Methods.customWait(4);
				m_assert.assertTrue(
						Cls_Generic_Methods.clickElementByJS(driver,
								oPage_DischargeForm.checkbox_checkAllInDischargeTemplate),
						"selected all discharge checklist ");
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OperativeForm.button_next, 20);

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_next),
						" clicked on next button");
				Cls_Generic_Methods.customWait();
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_next),
						" clicked on next button");
				m_assert.assertTrue(
						Cls_Generic_Methods.selectElementByIndex(oPage_DischargeForm.button_selectMedicationSetInDischargeTemplate, 0),
						Cls_Generic_Methods.getSelectedValue(oPage_DischargeForm.button_selectMedicationSetInDischargeTemplate)
								+ " medication set selected in discharge template");
				Cls_Generic_Methods.customWait();
				m_assert.assertTrue(
						Cls_Generic_Methods.clickElement(driver, oPage_OperativeForm.button_saveOperativeForm),
						" Discharge template successfully saved");
				Cls_Generic_Methods.customWait(4);
				Cls_Generic_Methods.waitForElementToBeDisplayed(
						oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate, 20);
				m_assert.assertTrue(
						Cls_Generic_Methods.clickElement(driver,
								oPage_PreAnaesthesiaTemplate.button_closePreAnsthesiaTemplate),
						"Discharge form closed ");

				Cls_Generic_Methods.customWait(5);




			} else {
				m_assert.assertTrue(false, "searched patient is not present in patient list");
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("Application not loaded in the browser" + e);
		}

	}

	@Test(enabled = true, description = "Validation Patient and Template Details In Pharmacy Store")
	public void validatingDischargePatientDetailsInPharmacyStore() {

		Page_Store oPage_Store = new Page_Store(driver);
		Page_Navbar oPage_Navbar = new Page_Navbar(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);
		String[] storeTabList = {"My Queue","All","Converted","Not Converted"};



		try {
			myPatient = map_PatientsInExcel.get(patientKey);
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);

			try {

				m_assert.assertTrue(CommonActions.selectStoreOnApp(sStorePharmacy), sStorePharmacy + " Store Opened For Validation Of Patient Queue");
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait(4);

				m_assert.assertInfo(storeTabList[0] + " Tab Count Before Medication  Advised Was : "+myQueueTabCountPharmacy);
				m_assert.assertInfo(storeTabList[1] + " Tab Count Before Medication Advised Was : "+allTabCountPharmacy);
				m_assert.assertInfo(storeTabList[2] + " Tab Count Before Medication Advised Was : "+convertedTabCountPharmacy);
				m_assert.assertInfo(storeTabList[3] + " Tab Count Before Glass Medication Advised Was : "+notConvertedTabCountPharmacy);

				updatedMyQueueTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeMyQueueTabCountsPharmacyInPatientQueuePage));
				updatedAllTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeAllTabCountsPharmacyInPatientQueuePage));
				updatedConvertedTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeConvertedCountsPharmacyInPatientQueuePage));
				updatedNotConvertedTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeNotConvertedCountsPharmacyInPatientQueuePage));

				if(updatedMyQueueTabCount == myQueueTabCountPharmacy + 1 && updatedAllTabCount == (allTabCountPharmacy+2) && updatedConvertedTabCount == convertedTabCountPharmacy+1
						&& notConvertedTabCountPharmacy == updatedNotConvertedTabCount){
					m_assert.assertTrue(" Store Tab Count Increased Correct , As Only All Tab Count Increased By One "+ updatedAllTabCount);
				} else{
					m_assert.assertWarn( " Either All Tab Count Not Increasing or something wrong ");
				}
				m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_Store.tabs_storeTabsIPharmacyPatientQueuePage, storeTabList[0]),
						"Validate " + storeTabList[0] + " tab is selected");

				Cls_Generic_Methods.customWait(3);

				concatPatientFullName = CommonActions.getFullPatientName(myPatient).toUpperCase().trim();
				boolean bValidatePatientFound = CommonActions.selectPatientNameInPatientQueue(oPage_PatientQueue.rows_patientAppointmentsInPharmacyPatientQueue,concatPatientFullName);

				m_assert.assertTrue(bValidatePatientFound, "Validate patient " + concatPatientFullName + " is found");

				String patientStatus = Cls_Generic_Methods.getTextInElement(oPage_PatientQueue.text_patientStatusInPharmacyStore);

				m_assert.assertTrue(patientStatus.equalsIgnoreCase("Advised"),
						" Patient Status Showed as Advised correctly");

				Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
				Cls_Generic_Methods.customWait();


			}
			catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}

	}

	@Test(enabled = true, description = "Create Eye Template With Details To Flow Patient TO Store")
	public void createPostOPTemplate() {

		Page_EyeTemplate oPage_EyeTemplate = new Page_EyeTemplate(driver);
		Page_RefractionTab oPage_RefractionTab = new Page_RefractionTab(driver);
		Page_OPD oPage_OPD = new Page_OPD(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		String PastOPTemplate = "Post OP";
		myPatient = TestBase.map_PatientsInExcel.get(patientKey);
		Optometrist_Data oOptometrist_Data = new Optometrist_Data();

		try {

			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			CommonActions.selectDepartmentOnApp("OPD");
			Cls_Generic_Methods.customWait();
			concatPatientFullName = CommonActions.getFullPatientName(myPatient).toUpperCase().trim();
			patientSelectedOPD = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
			Cls_Generic_Methods.customWait();

			if (patientSelectedOPD) {
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
				m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, PastOPTemplate),
						"Validate " + PastOPTemplate + " template  is selected");

				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.tab_RefractionTab, 20);


				try {
					for (WebElement buttonElement : oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityRight) {

						int index = oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityRight.indexOf(buttonElement);

						boolean validateButtonFunctionality = false;
						validateButtonFunctionality = CommonActions.validateIf_EHR_ButtonIsClickable(buttonElement);
						m_assert.assertInfo(validateButtonFunctionality, "Validate "
								+ oOptometrist_Data.list_UCVA_ABSENT.get(index) + " R/OD Button is Clickable");

						m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, buttonElement), "Validate "
								+ oOptometrist_Data.list_UCVA_ABSENT.get(index) + " R/OD Button is Clicked");
						break;
					}
					m_assert.assertTrue(
							Cls_Generic_Methods.sendKeysIntoElement(
									oPage_RefractionTab.input_ucvaCommentUnderVisualAcuityRightSide,
									oOptometrist_Data.sUCVA_COMMENT),
							"Validate Visual Acuity for right eye Comment is entered as "
									+ oOptometrist_Data.sUCVA_COMMENT);

					m_assert.assertTrue(
							Cls_Generic_Methods.sendKeysIntoElement(
									oPage_RefractionTab.input_CommentUnderVisualAcuityRightSide,
									oOptometrist_Data.sUCVA_COMMENT),
							"Validate R/OD Visual Acuity Comment is entered as "
									+ oOptometrist_Data.sVISUAL_ACUITY_COMMENT);

				} catch (Exception e) {
					e.printStackTrace();
					m_assert.assertFatal("Unable to validate R/OD Section under visual acuity - \n" + e);
				}

				try {
					for (WebElement buttonElement : oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityLeft) {

						int index = oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityLeft.indexOf(buttonElement);

						boolean validateButtonFunctionality = CommonActions.validateIf_EHR_ButtonIsClickable(buttonElement);

						m_assert.assertTrue(validateButtonFunctionality, "Validate "
								+ oOptometrist_Data.list_UCVA_ABSENT.get(index) + " L/OS Button is Clickable");
						m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, buttonElement), "Validate "
								+ oOptometrist_Data.list_UCVA_ABSENT.get(index) + " L/OS Button is Clicked");
						break;
					}


					m_assert.assertTrue(
							Cls_Generic_Methods.sendKeysIntoElement(
									oPage_RefractionTab.input_ucvaCommentUnderVisualAcuityLeftSide,
									oOptometrist_Data.sUCVA_COMMENT),
							"Validate Visual Acuity for left eye Comment is entered as "
									+ oOptometrist_Data.sUCVA_COMMENT);
					m_assert.assertTrue(
							Cls_Generic_Methods.sendKeysIntoElement(
									oPage_RefractionTab.input_CommentUnderVisualAcuityLefttSide,
									oOptometrist_Data.sUCVA_COMMENT),
							"Validate L/OD Visual Acuity Comment is entered as "
									+ oOptometrist_Data.sVISUAL_ACUITY_COMMENT);
				} catch (Exception e) {
					e.printStackTrace();
					m_assert.assertFatal("Unable to validate L/OS side absent Section under visual acuity - \n" + e);
				}

				Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_rightIop);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_rightIop, myPatient.getsIOP_RIGHT()), "Right Eye intraocularPressure is " + myPatient.getsIOP_RIGHT());

				Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_leftIop);
				m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_leftIop, myPatient.getsIOP_LEFT()), "Left Eye intraocularPressure is " + myPatient.getsIOP_RIGHT());

				Cls_Generic_Methods.customWait(1);
				Cls_Generic_Methods.scrollToElementByJS(oPage_RefractionTab.btn_fillGlassPrescription);
				Cls_Generic_Methods.customWait(2);
				validateRefraction_GLASS_PRESCRIPTION();

				//Click On Save Button
				try {
					m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Save button is clicked");
					Cls_Generic_Methods.customWait(4);
				} catch (Exception e) {
					e.printStackTrace();
					m_assert.assertWarn("" + e);
				}

				m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OPD.text_headerOPDSummary, 15), "Upon clicking Save template, opd summary is displayed");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Close template button is clicked");

				//After Close wait till user drop down display
				m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_EyeTemplate.text_sendToDepartmentSection, 15), "Upon clicking close template send to user dropdown is displayed");


			}
			else{
				m_assert.assertTrue("Patient Not Found");
			}


		} catch (Exception e) {
			m_assert.assertTrue(false, "<b>Eye Template is not selected. </b> " + e);
			e.printStackTrace();
		}


	}

	@Test(enabled = true, description = "Validation Patient and Template Details In Store")
	public void validatingNotConvertedPatientDetailsInOpticalStore() {

		Page_Store oPage_Store = new Page_Store(driver);
		Page_Navbar oPage_Navbar = new Page_Navbar(driver);
		Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
		Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);


		String[] storeTabList = {"My Queue","All","Converted","Not Converted"};



		try {
			myPatient = map_PatientsInExcel.get(patientKey);
			CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);

			try {

				m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
				Cls_Generic_Methods.switchToOtherTab();
				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
						"Store pop up closed");
				Cls_Generic_Methods.customWait(4);

				m_assert.assertInfo(storeTabList[0] + " Tab Count Before Glass Prescription Advised Was : "+myQueueTabCount);
				m_assert.assertInfo(storeTabList[1] + " Tab Count Before Glass Prescription Advised Was : "+allTabCount);
				m_assert.assertInfo(storeTabList[2] + " Tab Count Before Glass Prescription Advised Was : "+convertedTabCount);
				m_assert.assertInfo(storeTabList[3] + " Tab Count Before Glass Prescription Advised Was : "+notConvertedTabCount);

				updatedMyQueueTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeMyQueueTabCountsInPatientQueuePage));
				updatedAllTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeAllTabCountsInPatientQueuePage));
				updatedConvertedTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeConvertedCountsInPatientQueuePage));
				updatedNotConvertedTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeNotConvertedCountsInPatientQueuePage));

				if(updatedMyQueueTabCount == myQueueTabCount && updatedAllTabCount == (allTabCount+2) && updatedConvertedTabCount == convertedTabCount+1
						&& notConvertedTabCount == updatedNotConvertedTabCount){
					m_assert.assertTrue(" Store Tab Count Increased Correct , As All Tab and converted tab Count Increased By One "+ updatedAllTabCount);
				} else{
					m_assert.assertWarn( " Either All Tab Count Not Increasing or something wrong ");
				}
				m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_Store.tabs_storeTabsInPatientQueuePage, storeTabList[1]),
						"Validate " + storeTabList[1] + " tab is selected");

				Cls_Generic_Methods.customWait(3);

				concatPatientFullName = CommonActions.getFullPatientName(myPatient).toUpperCase().trim();
				boolean bValidatePatientFound = CommonActions.selectPatientNameInPatientQueue(oPage_PatientQueue.rows_patientAppointmentsInPatientQueue,concatPatientFullName);

				m_assert.assertTrue(bValidatePatientFound, "Validate patient " + concatPatientFullName + " is found");

				String patientStatus = Cls_Generic_Methods.getTextInElement(oPage_PatientQueue.text_patientStatusInStore);

				m_assert.assertTrue(patientStatus.equalsIgnoreCase("Advised"),
						" Patient Status Showed as Advised correctly");


				if(!Cls_Generic_Methods.isElementDisplayed(oPage_Store.button_yesMarkConvertedButton)){
					Cls_Generic_Methods.clickElement(oPage_Store.button_markPatientVisitedButton);
					Cls_Generic_Methods.customWait(2);
				}

				Cls_Generic_Methods.clickElement(oPage_Store.button_noMarkConvertedButton);
				Cls_Generic_Methods.customWait(5);

				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.header_markNotConvertedTemplateHeader),
						" Mark Not Converted Template Opened");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.text_attendedBy),
						"Attended By Label displayed");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.select_selectedOpticianName),
						"Attended By displayed as : "+ Cls_Generic_Methods.getSelectedValue(oPage_PatientQueue.select_selectedOpticianName));

				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.label_dilatedReason),
						"Dilated Reason Displayed and Clicked");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_saveNotConverted),
						"Save Button Clicked In Not Converted Template");
				Cls_Generic_Methods.customWait(4);
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.label_status),
						"Status Label displayed");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.label_notConvertedStatus),
						"Not Converted Label displayed ");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.label_reason),
						"Reason Label displayed ");
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.text_dilatedReason),
						" Dilated Reason Displayed in Reason Box");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_editReason),
						"Edit Reason Button");
				Cls_Generic_Methods.customWait();
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.label_knownOpticalShopReason),
						"Know Optical Shop Reason Displayed and Clicked");
				m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_saveNotConverted),
						"Save Button Clicked In Not Converted Template");
				Cls_Generic_Methods.customWait(4);
				m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.text_knowOpticalShopReason),
						" Know Optical Shop Reason Displayed in Reason Box");

				String patientUpdatedStatus = Cls_Generic_Methods.getTextInElement(oPage_PatientQueue.text_patientStatusInStore);

				m_assert.assertTrue(patientUpdatedStatus.equalsIgnoreCase("Not Converted"),
						" Patient Status Changed to Not Converted Successfully");
				updatedNotConvertedTabCount = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_Store.tabs_storeNotConvertedCountsInPatientQueuePage));
				m_assert.assertTrue(updatedNotConvertedTabCount == notConvertedTabCount +1 ,
						" Not Converted Tab Count Increased By 1  Successfully");

				Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
				Cls_Generic_Methods.customWait();

			}
			catch (Exception e) {
				e.printStackTrace();
				m_assert.assertFatal("" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("" + e);
		}

	}


	public static void validateRefraction_GLASS_PRESCRIPTION() {

		Page_RefractionTab oPage_RefractionTab = new Page_RefractionTab(driver);

		try {
			m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_fillGlassPrescription), "Clicked Fill button in Glass Prescription");
			Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.btn_rightEqualLeftFillRefraction, 10);
			m_assert.assertTrue(fillRefractionTable(), "Validated Fill functionality in Glass Prescription");
			Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_okFillRefraction);

			m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_RefractionTab.select_rightSideOfLensGlassPrescription, sTypeOfLens),
					"Selected <b>" + sTypeOfLens + "</b> in Type of Lens R/OD-->Glass Prescription");
			m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.select_rightSideOfIPDGlassPrescription, sIPD),
					"Entered <b>" + sIPD + "</b> in IPD R/OD-->Glass Prescription");
			m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.select_rightSideOfSizeGlassPrescription, sSize),
					"Entered <b>" + sSize + "</b> in Size R/OD-->Glass Prescription");
			m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_RefractionTab.select_rightSideLensOfMaterialGlassPrescription, sLensMaterial),
					"Selected <b>" + sLensMaterial + "</b> in Lens Material R/OD-->Glass Prescription");
			m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_RefractionTab.select_rightSideLensTintGlassPrescription, sLensTint),
					"Selected <b>" + sLensTint + "</b> in Lens Tint R/OD-->Glass Prescription");
			m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_RefractionTab.select_rightSideFrameMaterialGlassPrescription, sFrameMaterial),
					"Selected <b>" + sFrameMaterial + "</b> in Frame Material R/OD-->Glass Prescription");
			m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_rightSideDiaGlassPrescription, sDia),
					"Entered <b>" + sDia + "</b> in Dia R/OD-->Glass Prescription");
			m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_rightSidePrismBaseGlassPrescription, sPrismBase),
					"Entered <b>" + sPrismBase + "</b> in Prism Base R/OD-->Glass Prescription");
			m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_rightSideFittingHeightGlassPrescription, sFittingHeight),
					"Entered <b>" + sFittingHeight + "</b> in Fitting height R/OD-->Glass Prescription");
			m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.textarea_rightSideAdviceCommentGlassPrescription, sGlassPrescriptionComment),
					"Entered <b>" + sGlassPrescriptionComment + "</b> in Advice R/OD-->Glass Prescription");

			m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_leftToRightCopyGlassPrescription),
					"Clicked Copy Left to Right button in glass prescription");


		} catch (Exception e) {
			e.printStackTrace();
			m_assert.assertFatal("Unable to validate Glass Prescriptions" + e);
		}
	}

	public static boolean fillRefractionTable() {
		boolean flag = false;
		Page_RefractionTab oPage_RefractionTab = new Page_RefractionTab(driver);
		try {
			clickButtonInFillRefraction(oPage_RefractionTab.list_btnSphDistant, sDistantSph);
			clickButtonInFillRefraction(oPage_RefractionTab.list_btnCylDistant, sDistantCyl);
			clickButtonInFillRefraction(oPage_RefractionTab.list_btnAxisDistant, sDistantAxis);
			clickButtonInFillRefraction(oPage_RefractionTab.list_btnVisionDistant, sDistantVision);
			Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_0_75_SphAdd);
			clickButtonInFillRefraction(oPage_RefractionTab.list_btnVisionNear, sNearVision);
			Cls_Generic_Methods.customWait();
			sNearSph = Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_nearSph,"value");
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	private static void clickButtonInFillRefraction(List<WebElement> allBtn, String btnToSelect) {
		Page_RefractionTab oPage_RefractionTab = new Page_RefractionTab(driver);

		try {
			if (btnToSelect.charAt(0) == '-') {
				Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_minus);
			} else if (btnToSelect.charAt(0) == '+') {
				Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_plus);
			}
			Cls_Generic_Methods.waitForElementToBeDisplayed(allBtn.get(0), 10);
			for (WebElement btn : allBtn) {
				String btnValue = Cls_Generic_Methods.getTextInElement(btn);
				String btnType = Cls_Generic_Methods.getElementAttribute(btn, "name").replaceAll("refraction_", "").toUpperCase();
				if (btnValue.contains(btnToSelect)) {
					m_assert.assertInfo(Cls_Generic_Methods.clickElement(btn), "Clicked <b>" + btnValue + "</b> in " + btnType);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
