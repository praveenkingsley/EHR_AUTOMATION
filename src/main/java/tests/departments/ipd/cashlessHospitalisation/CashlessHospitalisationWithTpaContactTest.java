package tests.departments.ipd.cashlessHospitalisation;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.IPD_Data;
import data.Settings_Data;
import org.openqa.selenium.*;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.ipd.forms.intraOperative.Page_OperativeForm;
import pages.ipd.forms.intraOperative.Page_PreAnaesthesiaTemplate;
import pages.ipd.forms.postOperative.Page_DischargeForm;
import pages.ipd.forms.preOperative.Page_AdmissionInPreOperative;
import pages.sprint69.financeChanges.Page_FinanceChanges;
import pages.store.OpticalStore.Page_SalesOrder;
import pages.store.Page_PatientQueue;
import pages.store.Page_Store;
import pages.store.PharmacyStore.Transaction.Page_Sale;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pages.commonElements.CommonActions.getRandomUniqueString;

public class CashlessHospitalisationWithTpaContactTest extends TestBase {
    EHR_Data oEHR_Data = new EHR_Data();
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    String sFacilityName = "TESTING_FACILITY";
    Date date = new Date();
    String date1 = dateFormat.format(date);
    String sServiceMasterType = "Organisational";
    String sSpeciality = "Ophthalmology";
    String sSubSpeciality = "General Ophthalmology";
    String sServiceMasterName = "Service" + getRandomUniqueString(6);
    String sServiceMasterAmount = "1000";
    String sServiceMasterCode = "General Ophthalmology";
    String sUpdatedPolicyNumber = "6789";
    String sUpdatedCCN = "7643";
    String sUpdatedCardNumber = "7365";
    String sUpdatedPatientPayerData = "Reason";
    public static Map<String, String> mapTracker = new HashMap<String, String>();
    public static String key_CreatedAt_ServiceName = "key_CreatedAt_ServiceName";
    public static String key_CreatedAt_TpaDisplayName = "key_CreatedAt_TpaDisplayName";
    public static String key_CreatedAt_TpaPatientPayerData = "key_CreatedAt_TpaPatientPayerData";
    public static String key_CreatedAt_TpaPayerTypeMaster = "key_CreatedAt_TpaPayerTypeMaster";
    public static String key_CreatedAt_PolicyNumber = "key_CreatedAt_PolicyNumber";
    public static String key_CreatedAt_CcnNumber = "key_CreatedAt_CcnNumber";
    public static String key_CreatedAt_CardNumber = "key_CreatedAt_CardNumber";
    public static String key_CreatedAt_PolicyExpiryDate = "key_CreatedAt_PolicyExpiryDate";
    public static String key_CreatedAt_PatientPayerDataInput = "key_CreatedAt_PatientPayerDataInput";
    public static String key_CreatedAt_InsuranceName = "key_CreatedAt_InsuranceName";
    public static String key_CreatedAt_CorporateName = "key_CreatedAt_CorporateName";
    public static String key_CreatedAt_UpdatedCorporateName = "key_CreatedAt_UpdatedCorporateName";
    public static String key_CreatedAt_PatientContactPerson = "key_CreatedAt_PatientContactPerson";
    public static String key_CreatedAt_CardNumbe1r = "key_CreatedAt_CardNumber";
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    static Model_Patient myPatient;
    String admissionTypes = "Emergency";
    String sCashlessHospitalisationStatus = "";
    String sPatientContactPerson = "Ashok";
    String concatPatientFullName = "";

    @Test(enabled = true, description = "Create New Service Data")
    public void createNewServiceToValidateCashlessHospitalisationFeature() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(5);
                CommonActions.selectOptionFromLeftInSettingsPanel("Financial", "Service Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addServiceMaster, 20);
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.customWait();
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_addServiceMaster), "Clicked on Add Service master button");
                Cls_Generic_Methods.customWait(4);
                for (WebElement eServiceMasterDropdown : oPage_FinanceChanges.list_serviceMasterDropdownContent) {
                    String sServiceMasterDropdown = eServiceMasterDropdown.getText();
                    if (sServiceMasterDropdown.equals("  Add Single Service Master")) {
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(eServiceMasterDropdown), "Clicked on single service master option");
                        break;
                    } else {
                        m_assert.assertFalse("Single service master option is not present");
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.select_serviceMasterSpeciality, 20);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_serviceMasterSpeciality, sSpeciality), "selected speciality= <b>" + sSpeciality + "</b>");
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_IpdDepartment);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_serviceMasterSubSpeciality, sSubSpeciality), "selected subSpeciality  = <b>" + sSubSpeciality + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_serviceMasterType, sServiceMasterType), "selected speciality= <b>" + sServiceMasterType + "</b>");
                Cls_Generic_Methods.selectElementByIndex(oPage_FinanceChanges.select_serviceMasterGroup, 1);
                Cls_Generic_Methods.selectElementByIndex(oPage_FinanceChanges.select_serviceMasterSubGroup, 1);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.input_serviceMasterName, sServiceMasterName), "Service name  = <b>" + sServiceMasterName + "</b>");
                mapTracker.put(key_CreatedAt_ServiceName, sServiceMasterName);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.input_serviceMasterCode, sServiceMasterCode), "Service master code  = <b>" + sServiceMasterAmount + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.input_serviceMasterAmount, sServiceMasterAmount), "Service master amount  = <b>" + sServiceMasterCode + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.hyperlink_selectAllFacility), "Clicked on Select all facility");
                Cls_Generic_Methods.customWait(3);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_saveServiceMasterForm), "Clicked on save service master form");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_Close);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addServiceMaster, 20);
            } catch (Exception e) {
                m_assert.assertFatal("Error loading the application " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Adding New Corporate ")
    public void addNewPayerForTPAContactGroup() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            String sContactGroupName = "TPA";
            String sDisplayName = "TPA" + getRandomUniqueString(5);
            int indexOfFacilityName = -1;
            boolean bFacilityNameFoundInTable = false;
            boolean bPayerDisplayNameFoundInTable = false;

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(5);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPayerMaster, 20);
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.customWait();
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_addPayerMaster), "Clicked on Add payer master button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.select_facilityToCreatePayer, 20);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_facilityToCreatePayer, sFacilityName), "selected facility = <b>" + sFacilityName + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactGroup, sContactGroupName), "selected Contact group name  = <b>" + sContactGroupName + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.select_payerTypeMaster, 20);
                Cls_Generic_Methods.selectElementByIndex(oPage_FinanceChanges.select_payerTypeMaster, 1);
                String sPayerTypeMaster = Cls_Generic_Methods.getSelectedValue(oPage_FinanceChanges.select_payerTypeMaster);
                mapTracker.put(key_CreatedAt_TpaPayerTypeMaster, sPayerTypeMaster);
                m_assert.assertInfo("selected payer type master = <b>" + sPayerTypeMaster + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_savePayerForm, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.list_patientPayerDataButton.get(1));
                String sPatientPayerData = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_patientPayerDataButton.get(1));
                mapTracker.put(key_CreatedAt_TpaPatientPayerData, sPatientPayerData);
                m_assert.assertInfo("selected patient payer data  = <b>" + sPatientPayerData + "</b>");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.input_payerMasterDisplayName, sDisplayName);
                mapTracker.put(key_CreatedAt_TpaDisplayName, sDisplayName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_savePayerForm, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_savePayerForm);
                Cls_Generic_Methods.customWait(4);
                for (WebElement eFacilityNameList : oPage_FinanceChanges.list_facilityNameUnderPayerMasterSection) {
                    String FacilityNameOnUI = eFacilityNameList.getText();
                    if (FacilityNameOnUI.equals(sFacilityName)) {
                        bFacilityNameFoundInTable = true;
                        indexOfFacilityName = oPage_FinanceChanges.list_facilityNameUnderPayerMasterSection.indexOf(eFacilityNameList);
                        break;
                    }
                }
                m_assert.assertTrue(bFacilityNameFoundInTable, "Facility Name Found in table <b> " + sFacilityName + " </b> ");
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_countOfPayersUnderEachFacilitysection.get(indexOfFacilityName));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.text_facilityName, 20);
                String sSelectedFacilityName = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.text_facilityName);
                if (sSelectedFacilityName.equals(sFacilityName)) {
                    m_assert.assertTrue(true, "Expected facility is selected =  <b> " + sFacilityName + " </b> ");
                } else {
                    m_assert.assertTrue(false, "Expected facility is selected =  <b> " + sFacilityName + " </b> ");
                }
                Cls_Generic_Methods.customWait(5);
                for (WebElement ePayerDisplayName : oPage_FinanceChanges.list_payersPresentUnderSelectedFacility) {
                    String PayerNameOnUI = ePayerDisplayName.getText();
                    if (PayerNameOnUI.equals(sDisplayName)) {
                        Cls_Generic_Methods.customWait();
                        bPayerDisplayNameFoundInTable = true;

                        break;
                    }
                }
                m_assert.assertTrue(bPayerDisplayNameFoundInTable, "Payer Name Found in table <b> " + sDisplayName + " </b> ");

            } catch (Exception e) {
                m_assert.assertFatal("Error loading the application " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    //Link services for Newly created TPA contact
    @Test(enabled = true, description = "Link Service For New Tpa Contact")
    public void linkServiceForNewTPAContact() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            int indexOfFacilityName = -1;
            boolean bFacilityNameFoundInTable = false;
            String sContactGroupName = "TPA";

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(5);
                CommonActions.selectOptionFromLeftInSettingsPanel("Financial", "Pricing Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.text_pricingMaster, 20);
                Cls_Generic_Methods.customWait(4);
                for (WebElement eFacilityNameList : oPage_FinanceChanges.list_facilityNameUnderPricingMasterSection) {
                    String FacilityNameOnUI = eFacilityNameList.getText();
                    if (FacilityNameOnUI.equals(sFacilityName)) {
                        bFacilityNameFoundInTable = true;
                        indexOfFacilityName = oPage_FinanceChanges.list_facilityNameUnderPricingMasterSection.indexOf(eFacilityNameList);
                        break;
                    }
                }
                m_assert.assertTrue(bFacilityNameFoundInTable, "Facility Name Found in table <b> " + sFacilityName + " </b> ");
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_countOfPricingMastersUnderEachFacility.get(indexOfFacilityName));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPricingMaster, 30);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.input_searchServiceName, mapTracker.get(key_CreatedAt_ServiceName)), "Searched Service master name = <b>" + mapTracker.get(key_CreatedAt_ServiceName) + "</b>");
                Cls_Generic_Methods.customWait(5);
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_editPricingDetailsButtonUnderPricingMasterSection.get(0));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addContactPricingUnderUpdatePricingMasterForm, 30);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_addContactPricingUnderUpdatePricingMasterForm), "Clicked on Add contact Pricing button");
                Cls_Generic_Methods.customWait(3);
                for (WebElement eContactGroup : oPage_FinanceChanges.list_contactGroupFieldUnderUpdatePricingDetailsButtonUnderUpdatePricingMaster) {
                    if (!eContactGroup.isSelected()) {
                        Cls_Generic_Methods.selectElementByVisibleText(eContactGroup, sContactGroupName);
                        m_assert.assertTrue(true, "Selected contact group = <b>" + sContactGroupName + "</b>");
                        break;
                    }
                }
                Cls_Generic_Methods.customWait(3);
                for (WebElement eContactGroup : oPage_FinanceChanges.list_payeeListFieldUnderUpdatePricingDetailsButtonUnderUpdatePricingMaster) {
                    if (!eContactGroup.isSelected()) {
                        Cls_Generic_Methods.selectElementByVisibleText(eContactGroup, mapTracker.get(key_CreatedAt_TpaDisplayName));
                        m_assert.assertTrue(true, "selected Payer   = <b>" + mapTracker.get(key_CreatedAt_TpaDisplayName) + "</b>");
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_saveUpdatedPricingMasterForm, 30);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_saveUpdatedPricingMasterForm), "Updated pricing master form saved");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPricingMaster, 30);
            } catch (Exception e) {
                m_assert.assertFatal("Error loading the application " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    //Add new patient in IPD and validate Insurance details
    @Test(enabled = true, description = "Schedule admission for a patient in ipd")
    public void scheduleAdmissionIpdBySelectingCashlessHospitalisationTrue() throws Exception {
        Page_IPD oPage_IPD = new Page_IPD(driver);
        Page_ScheduleAdmission oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        myPatient = map_PatientsInExcel.get(patientKey);
        oEHR_Data = new EHR_Data();
        Page_AdmissionInPreOperative oPage_AdmissionInPreOperative = new Page_AdmissionInPreOperative(driver);
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_DischargeForm oPage_DischargeForm = new Page_DischargeForm(driver);
        Page_OperativeForm oPage_OperativeForm = new Page_OperativeForm(driver);
        Page_PreAnaesthesiaTemplate oPage_PreAnaesthesiaTemplate = new Page_PreAnaesthesiaTemplate(driver);
        String expectedLoggedInUser = "PR.Akash test";
        String sAddNewCase = "Add New Case";
        String sContactGroup = "TPA";
        String sPatientPayerData = "PayerData";
        String sPolicyNumber = "1234";
        String sCCN = "5678";
        String sCardNumber = "4447";
        String sSumInsured = "1000";
        String sComments = "Abtr";
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectFacility("TST");
            CommonActions.selectDepartmentOnApp("IPD");
            try {
                // Open the Search/Add patient dialog box
                try {
                    Cls_Generic_Methods.customWait(10);
                    if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                        CommonActions.openPatientRegisterationAndAppointmentForm();
                        m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_ScheduleAdmissionForm, 20),
                                "Scheduled admission form is displayed");
                    }
                } catch (NoSuchElementException e) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                    CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                            "Patient Details");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                        myPatient.getsFIRST_NAME() + " entered for First Name");
                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                                myPatient.getsMOBILE_NUMBER()),
                        myPatient.getsMOBILE_NUMBER() + " entered for Mobile Number");
                //Admission Type
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_appointmentType, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.button_appointmentType), "Admission type = <b> " + admissionTypes + "</b>");
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduleAdmission.button_selectCase),
                        "View select case button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.select_allCasesDropDown, 20);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_ScheduleAdmission.select_allCasesDropDown, sAddNewCase);
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_IPD.radio_yesCashlessHospitalisationUnderScheduleAdmissionForm), "Clicked on Cashless hospitalisation Radio button = <b>YES</b> ");
                sCashlessHospitalisationStatus = Cls_Generic_Methods.getElementAttribute(oPage_IPD.radio_yesCashlessHospitalisationUnderScheduleAdmissionForm, "value");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IPD.tab_insuranceDetailsUnderScheduleAdmissionButton), "Clicked on Insurance details Tab under schedule admission form ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.input_patientContactPersonFieldUnderInsuranceDetailsForm, 30);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IPD.input_patientContactPersonFieldUnderInsuranceDetailsForm), "Clicked on Insurance details Tab under schedule admission form ");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_IPD.input_patientContactPersonFieldUnderInsuranceDetailsForm, sPatientContactPerson),
                        "Patient contact person = <b>" + sPatientContactPerson + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_IPD.select_payerFieldUnderInsuranceDetailsForm, sContactGroup),
                        "Selected payer = <b>" + sContactGroup + "</b>");
                String sContactField = Cls_Generic_Methods.getTextInElement(oPage_IPD.input_contactFieldUnderInsuranceDetails);
                ////
                if (sContactField.equals("Select Contact")) {
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.input_tpaFieldUnderInsuranceDetailsForm)) {
                        m_assert.assertFalse("TPA field is displayed");
                    } else {
                        m_assert.assertTrue("TPA field is not displayed");
                    }
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.select_corporateUnderInsuranceDetailsForm)) {
                        m_assert.assertFalse("Corporate field is displayed");
                    } else {
                        m_assert.assertTrue("Corporate field is not displayed");
                    }
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.select_insuranceFieldUnderInsuranceDetailsForm)) {
                        m_assert.assertFalse("Insurance field is displayed");
                    } else {
                        m_assert.assertTrue("Insurance field is not displayed");
                    }

                    Cls_Generic_Methods.customWait(5);
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_patientPayerDataFieldName)) {
                        m_assert.assertFalse("Patient payer information field is displayed");
                    } else {
                        m_assert.assertTrue("Patient payer information field is not displayed");
                    }
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.input_policyNumberUnderInsuranceDetailsForm)) {
                        m_assert.assertFalse("Policy Number field is displayed");
                    } else {
                        m_assert.assertTrue("Policy Number field is not displayed");
                    }
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.input_ccnNumberUnderInsuranceDetailsForm)) {
                        m_assert.assertFalse("CCN Number field is displayed");
                    } else {
                        m_assert.assertTrue("CCN Number field is not displayed");
                    }
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.input_cardNumberUnderInsuranceDetailsForm)) {
                        m_assert.assertFalse("Card Number field is displayed");
                    } else {
                        m_assert.assertTrue("Card Number field is not displayed");
                    }
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.input_policyExpiryDateFieldUnderInsuranceDetailsForm)) {
                        m_assert.assertFalse("Policy expiry field is displayed");
                    } else {
                        m_assert.assertTrue("Policy expiry is not displayed");
                    }
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.checkbox_pharmacyCreditIncludedCheckboxUnderInsuranceDetailsForm)) {
                        m_assert.assertFalse("Pharmacy credit included checkbox is displayed");
                    } else {
                        m_assert.assertTrue("Pharmacy credit included checkbox is not displayed");
                    }


                }

                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_IPD.select_contactFieldUnderInsuranceDetailsForm, mapTracker.get(key_CreatedAt_TpaDisplayName)),
                        "Selected contact = <b>" + mapTracker.get(key_CreatedAt_TpaDisplayName) + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.input_insuranceFieldUnderInsuranceDetailsForm, 20);
                if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.input_tpaFieldUnderInsuranceDetailsForm)) {
                    String sTPAFieldValue = Cls_Generic_Methods.getElementAttribute(oPage_IPD.input_tpaFieldUnderInsuranceDetailsForm, "value");
                    if (sTPAFieldValue.equals(mapTracker.get(key_CreatedAt_TpaPayerTypeMaster))) {
                        m_assert.assertTrue("Payer type master value present under insurance field = <b>" + mapTracker.get(key_CreatedAt_TpaPayerTypeMaster) + "</b>");
                    } else {
                        m_assert.assertFalse("Expected Payer type master value is not present under insurance field = <b>" + mapTracker.get(key_CreatedAt_TpaPayerTypeMaster) + "</b>");
                    }
                } else {
                    m_assert.assertFalse("TPA field is not displayed");
                }
                Cls_Generic_Methods.customWait(3);
                if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.select_corporateUnderInsuranceDetailsForm)) {
                    Cls_Generic_Methods.selectElementByIndex(oPage_IPD.select_corporateUnderInsuranceDetailsForm, 1);
                    String sCorporateNameOnUI = Cls_Generic_Methods.getSelectedValue(oPage_IPD.select_corporateUnderInsuranceDetailsForm);
                    mapTracker.put(key_CreatedAt_CorporateName, sCorporateNameOnUI);
                    m_assert.assertTrue("Selected corporate name = <b>" + sCorporateNameOnUI + "</b>");
                } else {
                    m_assert.assertFalse("Corporate field is not displayed");
                }

                Cls_Generic_Methods.customWait(3);
                if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.select_insuranceFieldUnderInsuranceDetailsForm)) {
                    Cls_Generic_Methods.selectElementByIndex(oPage_IPD.select_insuranceFieldUnderInsuranceDetailsForm, 1);
                    String sInsuranceNameOnUI = Cls_Generic_Methods.getSelectedValue(oPage_IPD.select_insuranceFieldUnderInsuranceDetailsForm);
                    mapTracker.put(key_CreatedAt_InsuranceName, sInsuranceNameOnUI);
                    m_assert.assertTrue("Selected Insurance name = <b>" + sInsuranceNameOnUI + "</b>");
                } else {
                    m_assert.assertFalse("Insurance field is not displayed");
                }

                if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_patientPayerInfoUnderInsuranceDetails)) {
                    String sPatientPayerDataOnUI = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_patientPayerDataFieldName);
                    if (sPatientPayerDataOnUI.equals(mapTracker.get(key_CreatedAt_TpaPatientPayerData))) {
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_IPD.input_patientPayerDataInfoFieldUnderInsuranceDetails, sPatientPayerData),
                                "Patient payer data  = <b>" + sPatientPayerData + "</b>");
                        mapTracker.put(key_CreatedAt_PatientPayerDataInput, sPatientPayerData);
                    } else {
                        m_assert.assertFalse("Expected Patient payer data field is not present");
                    }

                } else {
                    m_assert.assertTrue("Patient payer information field is not visible");
                }
                Cls_Generic_Methods.customWait(3);
                if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.input_policyNumberUnderInsuranceDetailsForm)) {
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_IPD.input_policyNumberUnderInsuranceDetailsForm, sPolicyNumber),
                            "Policy number = <b>" + sPolicyNumber + "</b>");
                    mapTracker.put(key_CreatedAt_PolicyNumber, sPolicyNumber);
                } else {
                    m_assert.assertFalse("Policy number field is not displayed");

                }
                Cls_Generic_Methods.customWait(3);
                if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.input_ccnNumberUnderInsuranceDetailsForm)) {
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_IPD.input_ccnNumberUnderInsuranceDetailsForm, sCCN),
                            "CCN number = <b>" + sCCN + "</b>");
                    mapTracker.put(key_CreatedAt_CcnNumber, sCCN);
                } else {
                    m_assert.assertFalse("CCN number field is not displayed");

                }
                Cls_Generic_Methods.customWait(3);
                if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.input_cardNumberUnderInsuranceDetailsForm)) {
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_IPD.input_cardNumberUnderInsuranceDetailsForm, sCardNumber),
                            "Card number = <b>" + sCardNumber + "</b>");
                    mapTracker.put(key_CreatedAt_CardNumber, sCardNumber);
                } else {
                    m_assert.assertFalse("Card number field is not displayed");

                }
                Cls_Generic_Methods.customWait(3);
                if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.input_policyExpiryDateFieldUnderInsuranceDetailsForm)) {
                    Cls_Generic_Methods.clickElement(oPage_IPD.input_policyExpiryDateFieldUnderInsuranceDetailsForm);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.input_policyExpiryDate, 20);
                    Cls_Generic_Methods.clickElement(oPage_IPD.input_policyExpiryDate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.input_policyExpiryDateFieldUnderInsuranceDetailsForm, 20);
                    String sExpDate = Cls_Generic_Methods.getElementAttribute(oPage_IPD.input_policyExpiryDateFieldUnderInsuranceDetailsForm, "value");
                    mapTracker.put(key_CreatedAt_PolicyExpiryDate, sExpDate);
                    m_assert.assertTrue("Policy expiry date = <b>" + sExpDate + "</b>");
                } else {
                    m_assert.assertFalse("Policy expiry date field is not visible");

                }

                Cls_Generic_Methods.customWait(3);
                if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.checkbox_pharmacyCreditIncludedCheckboxUnderInsuranceDetailsForm)) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IPD.checkbox_pharmacyCreditIncludedCheckboxUnderInsuranceDetailsForm),
                            "Clicked on  Pharmacy credit included checkbox");

                } else {
                    m_assert.assertFalse("Pharmacy credit included checkbox is not visible");
                }
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_IPD.input_sumInsuredFieldUnderInsuranceDetailsForm, sSumInsured), "Sum Insured = <b>" + sSumInsured + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_IPD.input_commentsFieldUnderInsuranceDetailsForm, sComments), "Comments Under Insurance Form = <b>" + sComments + "</b>");
                ////
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_createAdmission),
                        "Create admission button is clicked");
                Cls_Generic_Methods.customWait(10);
                //Assign Bed
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_IPD.button_closeAssignBedForm, 15),
                        "Assigned bed Form is displayed");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.button_closeAssignBedForm), "Assigned bed Form is closed");
                Cls_Generic_Methods.customWait(15);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_readyForAdmission),
                        "Ready for admission Button clicked ");
                Cls_Generic_Methods.customWait(5);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 30);
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

                Cls_Generic_Methods.customWait(10);


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "validate Credit Bill Option Should Not Display If Cashless Hospitalisation Is False")
    public void validateCreditBillOptionShouldNotDisplayIfCashlessHospitalisationIsTrue() throws Exception {
        Page_IPD oPage_IPD = new Page_IPD(driver);
        myPatient = map_PatientsInExcel.get(patientKey);
        oEHR_Data = new EHR_Data();
        String patientName = null;
        boolean bPatientNameFound = false;
        String expectedLoggedInUser = "PR.Akash test";
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectFacility("TST");
            CommonActions.selectDepartmentOnApp("IPD");
            try {

                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                Cls_Generic_Methods.customWait(10);
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_MY_QUEUE),
                        "Validate " + IPD_Data.tab_MY_QUEUE + " tab is selected");
                Cls_Generic_Methods.customWait(5);
                bPatientNameFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, concatPatientFullName);
                if (bPatientNameFound) {
                    if (sCashlessHospitalisationStatus.equals("Yes")) {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_billsUnderIPD, 20);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.button_billsUnderIPD), "Clicked on bills button");
                        Cls_Generic_Methods.customWait(3);
                        if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.button_newDraftBill)) {
                            m_assert.assertTrue("As expected: New draft bill is displaying");
                        } else {
                            m_assert.assertWarn("Issue: New draft bill is not displaying");
                        }
                        if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.button_newCreditBill)) {
                            m_assert.assertTrue("As expected: New credit bill is displaying");
                        } else {
                            m_assert.assertWarn("Issue: New credit bill is not displaying");
                        }
                    } else {
                        m_assert.assertWarn("Cashless hospitalisation status is not captured");
                    }


                } else {
                    m_assert.assertTrue(false, "Admission is not Scheduled for patient: " + patientName);
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

    @Test(enabled = true, description = "validate TPA Details For Ipd Patient")
    public void validateTpaDetailsForIpdPatient() {
        Page_IPD oPage_IPD = new Page_IPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String patientName = null;
        boolean bPatientNameFound = false;
        String expectedLoggedInUser = "PR.Akash test";
        String sContactGroup = "TPA";
        String sSumInsured = "1000";
        String sComments = "Abtr";


        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("IPD");
            try {

                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                Cls_Generic_Methods.customWait(15);
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_MY_QUEUE),
                        "Validate " + IPD_Data.tab_MY_QUEUE + " tab is selected");
                Cls_Generic_Methods.customWait(5);
                bPatientNameFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, concatPatientFullName);
                if (bPatientNameFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_editInsuranceDetailsButton, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_editInsuranceDetailsButton),
                            "Clicked On edit Insurance details button ");
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_IPD.input_patientContactPersonFieldUnderInsuranceDetailsForm, 20);
                    Cls_Generic_Methods.customWait(3);
                    if (Cls_Generic_Methods.radioButtonIsSelected(oPage_IPD.checkbox_pharmacyCreditIncludedCheckboxUnderInsuranceDetailsForm)) {
                        m_assert.assertTrue("As expected: Pharmacy credit included checkbox is in checked state");

                    } else {
                        m_assert.assertTrue(" credit included checkbox is in unchecked state");
                    }
                    Cls_Generic_Methods.customWait(3);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_IPD.checkbox_preAuthFormCheckboxUnderInsuranceDetailsForm),
                            "Document received: Pre Auth Form Filled is in checked state ");
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_IPD.checkbox_passportCheckboxUnderInsuranceDetailsForm),
                            "Document received: Passport checkbox  checked ");
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_IPD.checkbox_electionCardCheckboxUnderInsuranceDetailsForm),
                            "Document received: Election Card checkbox checked ");
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_IPD.checkbox_othersCheckboxUnderInsuranceDetailsForm),
                            "Document received: Others checkbox checked ");
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_IPD.checkbox_cancelledChequeCheckboxUnderInsuranceDetailsForm),
                            "Document received: Cancelled cheque checkbox checked");
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_IPD.checkbox_insuranceCardCheckboxUnderInsuranceDetailsForm),
                            "Document received: Insurance card checkbox checked");
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_IPD.checkbox_aadharCardCheckboxUnderInsuranceDetailsForm),
                            "Document received: Aadhar card checkbox checked");
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_IPD.checkbox_employeeCardCheckboxUnderInsuranceDetailsForm),
                            "Document received: Employee ID checkbox checked  ");
                    Cls_Generic_Methods.customWait(2);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_IPD.button_savePatientInsuranceForm, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.button_savePatientInsuranceForm), "Patient Insurance form saved");
                    Cls_Generic_Methods.customWait(3);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_payerGroupUnderInsuranceDetailsInIpdRHS, 30);
                    // validate payer group under insurance details in IPD RHS
                    Cls_Generic_Methods.customWait(4);
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_payerGroupUnderInsuranceDetailsInIpdRHS)) {
                        String sPayerContactGroup = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_payerGroupUnderInsuranceDetailsInIpdRHS);
                        if (sPayerContactGroup.equals(sContactGroup)) {
                            m_assert.assertTrue("<b>" + sContactGroup + "</b> contact group name is displaying");
                        } else {
                            m_assert.assertFalse("Expected Payer contact group name is not displaying");
                        }
                    } else {
                        m_assert.assertFalse("Contact group is not visible");
                    }
                    // validate payer name under insurance details in IPD RHS
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_insuranceDisplayNameUnderInsuranceDetailsInIpdRHS)) {
                        String sTpaDisplayName = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_insuranceDisplayNameUnderInsuranceDetailsInIpdRHS);
                        if (sTpaDisplayName.equals(mapTracker.get(key_CreatedAt_TpaDisplayName))) {
                            m_assert.assertTrue("Expected TPA display name Displayed =  <b>" + sTpaDisplayName + "</b>");
                        } else {
                            m_assert.assertFalse("Expected TPA display name is not displaying");
                        }
                    } else {
                        m_assert.assertFalse("TPA Display name is not visible");
                    }
                    // validate TPA name under insurance details in IPD RHS
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_tpaNameUnderInsuranceDetailsInIpdRHS)) {
                        String sTpaName = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_tpaNameUnderInsuranceDetailsInIpdRHS);
                        if (sTpaName.equals(mapTracker.get(key_CreatedAt_TpaPayerTypeMaster))) {
                            m_assert.assertTrue("Expected TPA Name name Displayed =  <b>" + sTpaName + "</b>");
                        } else {
                            m_assert.assertFalse("Expected TPA Name name is not displaying");
                        }
                    } else {
                        m_assert.assertFalse("TPA Name name is not visible");
                    }

                    // validate Corporate name under insurance details in IPD RHS
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_corporateNameUnderInsuranceDetailsInIpdRHS)) {
                        String sCorporateName = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_corporateNameUnderInsuranceDetailsInIpdRHS);
                        if (sCorporateName.equals(mapTracker.get(key_CreatedAt_CorporateName))) {
                            m_assert.assertTrue("Expected Corporate  name Displayed =  <b>" + sCorporateName + "</b>");
                        } else {
                            m_assert.assertFalse("Expected Corporate  name is not displaying");
                        }
                    } else {
                        m_assert.assertFalse("Corporate name is not visible");
                    }

                    // validate Insurance name under insurance details in IPD RHS
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_insuranceNameUnderInsuranceDetailsInIpdRHS)) {
                        String sInsuranceName = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_insuranceNameUnderInsuranceDetailsInIpdRHS);
                        if (sInsuranceName.equals(mapTracker.get(key_CreatedAt_InsuranceName))) {
                            m_assert.assertTrue("Expected Insurance  name Displayed =  <b>" + sInsuranceName + "</b>");
                        } else {
                            m_assert.assertFalse("Expected Insurance  name is not displaying");
                        }
                    } else {
                        m_assert.assertFalse("Insurance name is not visible");
                    }

                    // validate policy number under insurance details in IPD RHS
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_policyNumberUnderInsuranceDetailsInIpdRHS)) {
                        String sPolicyNumberonUI = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_policyNumberUnderInsuranceDetailsInIpdRHS);
                        if (sPolicyNumberonUI.equals(mapTracker.get(key_CreatedAt_PolicyNumber))) {
                            m_assert.assertTrue("Expected policy number Displayed =  <b>" + sPolicyNumberonUI + "</b>");
                        } else {
                            m_assert.assertFalse("Expected policy number is not displaying");
                        }
                    } else {
                        m_assert.assertFalse("Policy number is not visible");
                    }

                    // validate CCN number under insurance details in IPD RHS
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_ccnNumberUnderInsuranceDetailsInIpdRHS)) {
                        String sCcnNumberonUI = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_ccnNumberUnderInsuranceDetailsInIpdRHS);
                        if (sCcnNumberonUI.equals(mapTracker.get(key_CreatedAt_CcnNumber))) {
                            m_assert.assertTrue("Expected ccn number Displayed =  <b>" + sCcnNumberonUI + "</b>");
                        } else {
                            m_assert.assertFalse("Expected ccn number is not displaying");
                        }
                    } else {
                        m_assert.assertFalse("ccn number is not visible");
                    }

                    // validate card number under insurance details in IPD RHS
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_cardNumberUnderInsuranceDetailsInIpdRHS)) {
                        String sCardNumberonUI = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_cardNumberUnderInsuranceDetailsInIpdRHS);
                        if (sCardNumberonUI.equals(mapTracker.get(key_CreatedAt_CardNumber))) {
                            m_assert.assertTrue("Expected card number Displayed =  <b>" + sCardNumberonUI + "</b>");
                        } else {
                            m_assert.assertFalse("Expected card number is not displaying");
                        }
                    } else {
                        m_assert.assertFalse("card number is not visible");
                    }

                    // validate pharmacy credit info under insurance details in IPD RHS
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_pharmacyCreditInfoUnderInsuranceDetailsInIpdRHS)) {
                        String sPharmacyCreditInfonUI = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_pharmacyCreditInfoUnderInsuranceDetailsInIpdRHS);
                        String sCreditInfo = "Yes";
                        if (sPharmacyCreditInfonUI.equals(sCreditInfo)) {
                            m_assert.assertTrue("Pharmacy Credit Included =  <b>" + sPharmacyCreditInfonUI + "</b>");
                        } else {
                            m_assert.assertFalse("Pharmacy Credit is not Included");
                        }
                    } else {
                        m_assert.assertFalse("Pharmacy Credit info field is not visible");
                    }

                    // validate sum insured under insurance details in IPD RHS
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_sumInsuredUnderInsuranceDetailsInIpdRHS)) {
                        String sSumInsuredOnUI = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_sumInsuredUnderInsuranceDetailsInIpdRHS);
                        if (sSumInsuredOnUI.equals(sSumInsured)) {
                            m_assert.assertTrue("Expected sum insured displayed  =  <b>" + sSumInsuredOnUI + "</b>");
                        } else {
                            m_assert.assertFalse("sum insured field is not Displayed");
                        }
                    } else {
                        m_assert.assertFalse("sum insured field is not visible");
                    }

                    // validate patient payer data info under insurance details in IPD RHS
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_patientPayerForTpaFieldNameUnderInsuranceDetailsInIpdRHS)) {
                        String sPatientPayerDataFieldNameOnUI = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_patientPayerForTpaFieldNameUnderInsuranceDetailsInIpdRHS);
                        if (sPatientPayerDataFieldNameOnUI.equals(mapTracker.get(key_CreatedAt_TpaPatientPayerData))) {
                            m_assert.assertTrue("Patient Payer data field's expected display name  =  <b>" + sPatientPayerDataFieldNameOnUI + "</b>");
                        } else {
                            m_assert.assertFalse("Incorrect patient payer data field name ");
                        }
                        // validating patient payer data input value
                        if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_patientPayerForTpaInputInfoUnderInsuranceDetailsInIpdRHS)) {
                            String sPatientPayerDataInputInfo = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_patientPayerForTpaInputInfoUnderInsuranceDetailsInIpdRHS);
                            if (sPatientPayerDataInputInfo.equals(mapTracker.get(key_CreatedAt_PatientPayerDataInput))) {
                                m_assert.assertTrue(" Expected patient payer input displaying under insurance details in IPD RHS  =  <b>" + sPatientPayerDataInputInfo + "</b>");
                            } else {
                                m_assert.assertFalse("Expected patient payer input is not displaying under insurance details in IPD RHS ");
                            }
                        } else {
                            m_assert.assertFalse("patient payer input info is not visible");
                        }
                        // validating sum insured data in IPD RHS(Insurance details)
                        if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_sumInsuredUnderInsuranceDetailsInIpdRHS)) {
                            String sPatientPayerDataInputInfo = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_sumInsuredUnderInsuranceDetailsInIpdRHS);
                            if (sPatientPayerDataInputInfo.equals(sSumInsured)) {
                                m_assert.assertTrue(" Expected Sum insured amount is displaying under insurance details in IPD RHS  =  <b>" + sSumInsured + "</b>");
                            } else {
                                m_assert.assertFalse("Expected Sum insured amount is not displaying under insurance details in IPD RHS ");
                            }
                        } else {
                            m_assert.assertFalse("sum insured info is not visible");
                        }
                        // validating comments data in IPD RHS(Insurance details)
                        if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_commentsUnderInsuranceDetailsInIpdRHS)) {
                            String sPatientPayerDataInputInfo = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_commentsUnderInsuranceDetailsInIpdRHS);
                            if (sPatientPayerDataInputInfo.equals(sComments)) {
                                m_assert.assertTrue(" Expected comments is displaying under insurance details in IPD RHS  =  <b>" + sComments + "</b>");
                            } else {
                                m_assert.assertFalse("Expected comments is not displaying under insurance details in IPD RHS ");
                            }
                        } else {
                            m_assert.assertFalse("comment is not visible");
                        }

                    } else {
                        m_assert.assertFalse("Incorrect patient payer data field is not visible");
                    }


                } else {
                    m_assert.assertTrue(false, "Admission is not Scheduled for patient: " + patientName);
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while scheduling admission of patient to IPD" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }


    //Validate Insurance Details under draft bill
    @Test(enabled = true, description = "Validate TPA Details In Draft Bill")
    public void ValidateTpaDetailsInDraftBill() throws Exception {
        Page_IPD oPage_IPD = new Page_IPD(driver);
        myPatient = map_PatientsInExcel.get(patientKey);
        oEHR_Data = new EHR_Data();
        String patientName = null;
        String sContactGroup = "TPA";
        boolean bPatientNameFound = false;
        String expectedLoggedInUser = "PR.Akash test";
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectFacility("TST");
            CommonActions.selectDepartmentOnApp("IPD");
            try {

                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                Cls_Generic_Methods.customWait(10);
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_MY_QUEUE),
                        "Validate " + IPD_Data.tab_MY_QUEUE + " tab is selected");
                Cls_Generic_Methods.customWait(5);
                bPatientNameFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, concatPatientFullName);
                if (bPatientNameFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_billsUnderIPD, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.button_billsUnderIPD), "Clicked on bills button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_newDraftBill, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.button_newDraftBill), "Clicked on new draft bill button");
                    Cls_Generic_Methods.customWait(5);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_payerContactGroupUnderBills, 20);
                    String sContactGroupUnderBills = Cls_Generic_Methods.getTextInElement(oPage_IPD.button_payerContactGroupUnderBills);
                    if (sContactGroupUnderBills.equals(sContactGroup)) {
                        m_assert.assertTrue("Expected contact group found under bills = <b> " + sContactGroup + "</b>");
                    } else {
                        m_assert.assertWarn("Issue: Contact Group is not matching");
                    }
                    String sPayerNameUnderBills = Cls_Generic_Methods.getTextInElement(oPage_IPD.button_payerNameUnderBills);
                    if (sPayerNameUnderBills.equals(mapTracker.get(key_CreatedAt_TpaDisplayName))) {
                        m_assert.assertTrue("Expected payer name found under bills = <b> " + sPayerNameUnderBills + "</b>");
                    } else {
                        m_assert.assertWarn("Issue: Expected payer name is not Found");
                    }
                    try {
                        String sCorporateNameUnderBills = Cls_Generic_Methods.getTextInElement(oPage_IPD.input_corporateFieldInBill);
                        if (sCorporateNameUnderBills.equals(mapTracker.get(key_CreatedAt_CorporateName))) {
                            m_assert.assertTrue("Expected Corporate name found under bills = <b> " + sCorporateNameUnderBills + "</b>");
                        } else {
                            m_assert.assertWarn("Issue: Expected Corporate name is not Found");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("Exception while getting the text from corporate field in draft bill" + e);
                    }
                    String sInsuranceNameUnderBills = Cls_Generic_Methods.getTextInElement(oPage_IPD.button_insuranceFieldUnderBills);
                    if (sInsuranceNameUnderBills.equals(mapTracker.get(key_CreatedAt_InsuranceName))) {
                        m_assert.assertTrue("Expected Insurance name found under bills = <b> " + sInsuranceNameUnderBills + "</b>");
                    } else {
                        m_assert.assertWarn("Issue: Expected Insurance name is not Found");
                    }
                    String sTPANameUnderBills = Cls_Generic_Methods.getElementAttribute(oPage_IPD.button_tpaNameUnderBills, "value");
                    if (sTPANameUnderBills.equals(mapTracker.get(key_CreatedAt_TpaPayerTypeMaster))) {
                        m_assert.assertTrue("Expected Insurance name found under bills = <b> " + sTPANameUnderBills + "</b>");
                    } else {
                        m_assert.assertWarn("Issue: Expected TPA name is not Found");
                    }
                    String sPolicyNumberUnderBills = Cls_Generic_Methods.getElementAttribute(oPage_IPD.input_policyNumberUnderBills, "value");
                    if (sPolicyNumberUnderBills.equals(mapTracker.get(key_CreatedAt_PolicyNumber))) {
                        m_assert.assertTrue("Expected Policy number found under bills = <b> " + sPolicyNumberUnderBills + "</b>");
                    } else {
                        m_assert.assertWarn("Issue: Expected Policy number is not Found");
                    }
                    String sCCNNumberUnderBills = Cls_Generic_Methods.getElementAttribute(oPage_IPD.input_cnnNumberUnderBills, "value");
                    if (sCCNNumberUnderBills.equals(mapTracker.get(key_CreatedAt_CcnNumber))) {
                        m_assert.assertTrue("Expected CCN number found under bills = <b> " + sCCNNumberUnderBills + "</b>");
                    } else {
                        m_assert.assertWarn("Issue: Expected CCN number is not Found");
                    }
                    String sCardNumberUnderBills = Cls_Generic_Methods.getElementAttribute(oPage_IPD.input_cardNumberUnderBills, "value");
                    if (sCardNumberUnderBills.equals(mapTracker.get(key_CreatedAt_CardNumber))) {
                        m_assert.assertTrue("Expected Card number found under bills = <b> " + sCardNumberUnderBills + "</b>");
                    } else {
                        m_assert.assertWarn("Issue: Expected Card number is not Found");
                    }
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_patientPayerInfoFieldNameUnderBills)) {
                        String sPatientPayerDataFieldNameUnderBills = Cls_Generic_Methods.getTextInElement(oPage_IPD.text_patientPayerInfoFieldNameUnderBills);
                        if (sPatientPayerDataFieldNameUnderBills.equals(mapTracker.get(key_CreatedAt_TpaPatientPayerData))) {
                            m_assert.assertTrue("Expected Patient Payer data field found under bills = <b> " + sPatientPayerDataFieldNameUnderBills + "</b>");
                        } else {
                            m_assert.assertWarn("Issue: Expected Patient Payer data field  is not Found");
                        }
                        String sPatientPayerDataInputUnderBills = Cls_Generic_Methods.getElementAttribute(oPage_IPD.text_patientPayerDataInputFieldNameUnderBills, "value");
                        if (sPatientPayerDataInputUnderBills.equals(mapTracker.get(key_CreatedAt_PatientPayerDataInput))) {
                            m_assert.assertTrue("Expected Patient Payer data input field found under bills = <b> " + sPatientPayerDataInputUnderBills + "</b>");
                        } else {
                            m_assert.assertWarn("Issue: Expected Patient Payer data input field  is not Found");
                        }
                        m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_IPD.text_patientPayerDataInputFieldNameUnderBills), "Cleared Patient payer data field");
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_IPD.text_patientPayerDataInputFieldNameUnderBills, sUpdatedPatientPayerData), "Updated Patient payer data = <b>" + sUpdatedPatientPayerData + "</b>");
                    } else {
                        m_assert.assertWarn("Issue: patient payer data field is not visible");
                    }

                    m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_IPD.input_policyNumberUnderBills), "Cleared policy number field");
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_IPD.input_policyNumberUnderBills, sUpdatedPolicyNumber), "Updated Policy Number= <b>" + sUpdatedPolicyNumber + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_IPD.input_cnnNumberUnderBills), "Cleared CCN number field");
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_IPD.input_cnnNumberUnderBills, sUpdatedCCN), "Updated CCN Number= <b>" + sUpdatedCCN + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_IPD.input_cardNumberUnderBills), "Cleared CCN number field");
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_IPD.input_cardNumberUnderBills, sUpdatedCardNumber), "Updated Card Number= <b>" + sUpdatedCardNumber + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.select_itemUnderBills, 20);
                    Cls_Generic_Methods.selectElementByIndex(oPage_IPD.select_corporateNameUnderBills, 2);
                    String sCorporateNameOnBillUI = Cls_Generic_Methods.getTextInElement(oPage_IPD.input_corporateFieldInBill);
                    mapTracker.put(key_CreatedAt_UpdatedCorporateName, sCorporateNameOnBillUI);
                    m_assert.assertTrue("Selected corporate name under = <b>" + sCorporateNameOnBillUI + "</b>");

                    for (WebElement billRow : oPage_IPD.bill_rowsOfServices) {

                        if (billRow.isDisplayed()) {

                            try {
                                List<WebElement> packageDetailsOnRow = billRow.findElements(By.xpath("./child::*"));
                                for (WebElement itemOnRow : packageDetailsOnRow) {
                                    String classAttr = Cls_Generic_Methods.getElementAttribute(itemOnRow, "class");
                                    if (classAttr.contains("field_for_description")) {
                                        Cls_Generic_Methods.clickElement(driver, itemOnRow);
                                        Thread.sleep(500);
                                        oPage_IPD.input_billService.sendKeys(Keys.DOWN, Keys.ENTER);
                                        break;
                                    }
                                }

                            } catch (ElementNotInteractableException e) {
                                e.printStackTrace();
                                m_assert.assertTrue(false, "Services are not selected" + e);
                            }
                        }
                    }
                    Cls_Generic_Methods.customWait(4);
                    String sRemainingAmount = Cls_Generic_Methods.getElementAttribute(oPage_IPD.input_amountRemainingField, "value");
                    Cls_Generic_Methods.clearValuesInElement(oPage_IPD.input_amountPendingField);
                    Cls_Generic_Methods.customWait();
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_IPD.input_amountPendingField, sRemainingAmount), "Amount pending field filled = <b>" + sRemainingAmount + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.button_saveDraftBillButton), "Clicked on save draft bill button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_closeForm, 30);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.button_closeForm), "Clicked on Close bill");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_coverLetter, 30);
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.button_coverLetter)) {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_billsUnderIPD, 20);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.button_billsUnderIPD), "Clicked on bills button");
                        Cls_Generic_Methods.customWait(3);
                        if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.button_newDraftBill)) {
                            m_assert.assertWarn("Issue : New draft bill is displaying");
                        } else {
                            m_assert.assertTrue("As expected: New draft bill is not displaying");
                        }
                        if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.button_newCreditBill)) {
                            m_assert.assertWarn("Issue: New credit bill is displaying");
                        } else {
                            m_assert.assertTrue("As expected: New credit bill is not displaying");
                        }
                    } else {
                        m_assert.assertWarn("issue: claim bill is not showing");
                    }

                } else {
                    m_assert.assertTrue(false, "Admission is not Scheduled for patient: " + patientName);
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

    //Validation TPA Details In Pharmacy Store
    @Test(enabled = true, description = "Validation Patient and Template Details In Pharmacy Store")
    public void validatingTPADetailsInPharmacyStore() {
        Page_IPD oPage_IPD = new Page_IPD(driver);
        Page_Store oPage_Store = new Page_Store(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);
        Page_Sale oPage_Sale = new Page_Sale(driver);
        String sTabToSelect = "My Queue";
        String sItemName = "AutomationUniqueIntem";
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        String sContactGroup = "TPA";
        String sStorePharmacy = "Pharmacy automation- Pharmacy";
        boolean bPatientNameFound = false;


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

                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_Store.tabs_storeTabsIPharmacyPatientQueuePage, sTabToSelect),
                        "Validate " + sTabToSelect + " tab is selected");
                Cls_Generic_Methods.customWait(5);
                concatPatientFullName = CommonActions.getFullPatientName(myPatient).toUpperCase().trim();
                boolean bValidatePatientFound = CommonActions.selectPatientNameInPatientQueue(oPage_PatientQueue.rows_patientAppointmentsInPharmacyPatientQueue, concatPatientFullName);

                m_assert.assertTrue(bValidatePatientFound, "Validate patient " + concatPatientFullName + " is found");

                String patientStatus = Cls_Generic_Methods.getTextInElement(oPage_PatientQueue.text_patientStatusInPharmacyStore);

                m_assert.assertTrue(patientStatus.equalsIgnoreCase("Advised"),
                        " Patient Status Showed as Advised correctly");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Store.button_markPatientVisitedButton), "Clicked on mark patient visited button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.button_yesMarkConvertedButton, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Store.button_yesMarkConvertedButton), "Clicked <b>YES</b> button to convert the patient");
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Sale.radio_creditBill, 30);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_Sale.radio_creditBill), "Bill type <b>Credit</b> is selected");
                Cls_Generic_Methods.customWait(5);
                String sPayerContactGroupUnderSales = Cls_Generic_Methods.getTextInElement(oPage_Sale.input_contactGroupFieldUnderSaleOrder);
                if (sPayerContactGroupUnderSales.equals(sContactGroup)) {
                    m_assert.assertTrue("Expected contact group present in sales order form <b>" + sContactGroup + "</b>");
                } else {
                    m_assert.assertWarn("Expected contact group is not displaying");
                }
                String sPayerNameUnderSales = Cls_Generic_Methods.getTextInElement(oPage_Sale.input_payerNameFieldUnderSaleOrder);
                if (sPayerNameUnderSales.equals(mapTracker.get(key_CreatedAt_TpaDisplayName))) {
                    m_assert.assertTrue("Expected payer name present in sales order form <b>" + sPayerNameUnderSales + "</b>");
                } else {
                    m_assert.assertWarn("Expected payer name is not displaying");
                }
                String sInsuranceFieldUnderSales = Cls_Generic_Methods.getTextInElement(oPage_Sale.input_insuranceFieldUnderSalesOrderForTpaContact);
                if (sInsuranceFieldUnderSales.equals(mapTracker.get(key_CreatedAt_InsuranceName))) {
                    m_assert.assertTrue("Expected insurance present in sales order form <b>" + sInsuranceFieldUnderSales + "</b>");
                } else {
                    m_assert.assertWarn("Expected insurance is not displaying");
                }
                String sTpaNameUnderSales = Cls_Generic_Methods.getElementAttribute(oPage_Sale.input_tpaNameFieldUnderSalesOrder, "value");
                if (sTpaNameUnderSales.equals(mapTracker.get(key_CreatedAt_TpaPayerTypeMaster))) {
                    m_assert.assertTrue("Expected TPA name present in sales order form <b>" + sTpaNameUnderSales + "</b>");
                } else {
                    m_assert.assertWarn("Expected TPA name is not displaying");
                }
                String sCorporateUnderSales = Cls_Generic_Methods.getTextInElement(oPage_Sale.input_corporateFieldUnderSalesOrder);
                if (sCorporateUnderSales.equals(mapTracker.get(key_CreatedAt_UpdatedCorporateName))) {
                    m_assert.assertTrue("Expected Corporate name present in sales order form <b>" + sCorporateUnderSales + "</b>");
                } else {
                    m_assert.assertWarn("Expected Corporate name is not displaying");
                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_description), "Search by <b>Description</b> button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_searchMedicineNameInDescription, 8);
                Cls_Generic_Methods.clearValuesInElement(oPage_SalesOrder.input_searchMedicineNameInDescription);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_searchMedicineNameInDescription, sItemName), "Entering the Item name as <b>" + sItemName + "</b> in description");
                oPage_SalesOrder.input_searchMedicineNameInDescription.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(5);
                boolean myMedicationFound = false;
                Cls_Generic_Methods.customWait(5);
                for (WebElement eMedication : oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult) {

                    if (Cls_Generic_Methods.isElementDisplayed(eMedication)) {
                        if (Cls_Generic_Methods.getTextInElement(eMedication).contains(sItemName)) {
                            Cls_Generic_Methods.clickElementByJS(driver, eMedication);
                            myMedicationFound = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(myMedicationFound, "Item : <b>" + sItemName + "</b> Selected for Sales ");
                Cls_Generic_Methods.customWait(5);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_quantityOfMedicine, "1"), "Qty selected");
                Cls_Generic_Methods.customWait(3);
                String sBalancePending = Cls_Generic_Methods.getElementAttribute(oPage_Sale.input_balancePendingAmountFieldUnderSalesOrder, "value");
                Cls_Generic_Methods.clickElement(oPage_Sale.button_closeReceivedDetailsSection);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clearValuesInElement(oPage_Sale.input_paymentPendingAmountFieldUnderSalesOrder);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_paymentPendingAmountFieldUnderSalesOrder, sBalancePending);
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_saveChangesOnSalesOrder);
                Cls_Generic_Methods.customWait(10);
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.customWait(5);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    // Prod issue we can validate this testcase once after the issue resolved
    @Test(enabled = true, description = "validate Print Claim Bill Functionality")
    public void validatePrintClaimBillFunctionality() throws Exception {
        Page_IPD oPage_IPD = new Page_IPD(driver);
        myPatient = map_PatientsInExcel.get(patientKey);
        oEHR_Data = new EHR_Data();
        String concatPatientFullName = "";
        boolean bPatientNameFound = false;
        String expectedLoggedInUser = "PR.Akash test";
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectFacility("TST");
            CommonActions.selectDepartmentOnApp("IPD");

            try {
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                Cls_Generic_Methods.customWait(10);
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_MY_QUEUE),
                        "Validate " + IPD_Data.tab_MY_QUEUE + " tab is selected");
                Cls_Generic_Methods.customWait(5);
                bPatientNameFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, concatPatientFullName);
                if (bPatientNameFound) {

                    if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.button_printClaimBill)) {
                        m_assert.assertTrue("Claim Bill button is displaying");

                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_IPD.button_printClaimBill), "clicked on Print claim bill button");
                        Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_IPD.list_printOptionsUnderClaimBill, 20);
                        int preWindowsCount = driver.getWindowHandles().size();
                        String initialWindowHandle = driver.getWindowHandle();
                        Cls_Generic_Methods.clickElement(driver, oPage_IPD.list_printOptionsUnderClaimBill.get(0));
                        Cls_Generic_Methods.customWait(8);
                        int postWindowsCount = driver.getWindowHandles().size();
                        m_assert.assertTrue(postWindowsCount > preWindowsCount, "Validate New tab is opened with Consent form when clicking on Print button");
                        // Closing the Second Window
                        for (String currentWindowHandle : driver.getWindowHandles()) {
                            if (!currentWindowHandle.equals(driver.getWindowHandle())) {
                                driver.switchTo().window(currentWindowHandle);
                            }
                        }
                        driver.close();
                        driver.switchTo().window(initialWindowHandle);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_printClaimBill, 16);

                    } else {
                        m_assert.assertTrue(false,
                                "Validate Claim bill window is visible");
                    }


                } else {
                    m_assert.assertWarn("Expected patient is not present in IPD Patient queue");
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
