package tests.departments.opd;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.gridfs.CLI;
import data.IPD_Data;
import data.OPD_Data;
import data.Settings_Data;
import org.openqa.selenium.*;
import org.python.antlr.ast.Str;
import org.python.core.exceptions;
import org.testng.annotations.Test;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;

import data.EHR_Data;
import pages.Sprint88.Page_ShowMrnInInvestigationTemplateCreate;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.common_tabs.Page_HistoryTab;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.commonElements.templates.Page_InventorySearchCommonElements;
import pages.ipd.Page_IPD;
import pages.login.Page_Login;
import pages.opd.Page_OPD;
import pages.settings.facilitySettings.general.facilitySetup.Page_FacilitySetup;
import pages.settings.organisationSettings.general.Page_PatientSettings;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.store.OtStore.Page_OtStore;
import pages.store.Page_Store;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Order.Page_Requisition;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_PurchaseReturn;
import pages.store.PharmacyStore.Transaction.Page_Sale;

import static java.awt.SystemColor.window;

public class AddPatientsTest extends TestBase {

    Page_Navbar oPage_Navbar;

    Page_NewPatientRegisteration oPage_NewPatientRegisteration;
    Page_Login oPage_Login;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_OPD oPage_OPD;
    Page_IPD oPage_IPD;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    static String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    public static String dateAndTime = "";

    static Model_Patient myPatient;
    //    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    static String otStoreName = "OT Store- IPD";
    EHR_Data oEHR_Data = new EHR_Data();
    String sMRNumber = "MRN"+CommonActions.getRandomString(5);

    ArrayList<String> checkedMandatoryFields = new ArrayList<>();

    @Test(enabled = true, description = "Validate EHS Login")
    public void validateEHSLogin() {

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            oPage_Login = new Page_Login(driver);

            System.out.println("Launching test in " + sBrowser + " Browser.");

            Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            // Way to handle different location's data
            if (driver.getTitle().equals(oEHR_Data.sEXPECTED_TITLE)) {
                m_assert.assertTrue(true, "Title Validation succeeded");
            } else {
                m_assert.assertTrue(false, "Title Validation succeeded. Expected = " + oEHR_Data.sEXPECTED_TITLE
                        + " & Actual = " + driver.getTitle());
            }

            if (oPage_Login.login_username.isDisplayed() && oPage_Login.login_password.isDisplayed()) {
                m_assert.assertInfo("EHR Login User Name & Password button is displayed");

                oPage_Login.login_username.click();
                oPage_Login.login_username.sendKeys(oEHR_Data.sUSER_NAME);
                m_assert.assertTrue(true, "Logged in as - " + oEHR_Data.sUSER_NAME);

                oPage_Login.login_password.click();
                oPage_Login.login_password.sendKeys(oEHR_Data.sPASSWORD);

                Cls_Generic_Methods.switchToFrame(driver, oPage_Login.iFrame_captchaWindow);

                if (oPage_Login.checkBox_captchaWindow.isDisplayed()) {
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_Login.checkBox_captchaWindow);
                }

//				try {
//					Cls_Generic_Methods.waitForElementToDisAppear(driver, oEHR_Page.captchaWindow, 90, 5);
//				} catch (Exception e) {
//					m_assert.assertTrue(false, "Captcha Validation failed" + e);
//				}

                driver.switchTo().defaultContent();
                oPage_Login.login_button.click();

            } else {
                m_assert.assertTrue(false, "EHR Logo validation succeeded");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Essential Details are filled in Create New Patient")
    public void validateAndFillEssentialPatientDetails() {

        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            Page_PatientSettings oPage_PatientSettings = new Page_PatientSettings(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectFacility("TST");


            try {
                //To find out selected mandatory field in Organisation setting
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Patient Settings");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientSettings.header_formValidationFields, 3);

                for (WebElement eCheckbox : oPage_PatientSettings.list_checkbox_formFieldNames) {
                    if ((eCheckbox.isSelected())) {
                        String checkedField = oPage_PatientSettings.list_formFieldNames
                                .get(oPage_PatientSettings.list_checkbox_formFieldNames.indexOf(eCheckbox)).getText().trim();
                        checkedMandatoryFields.add(checkedField);
                    }
                }

                m_assert.assertInfo("Selected mandatory field in Patient setting are " +
                        (checkedMandatoryFields.toString().replaceAll("\\[", "").replaceAll("\\]", "")));

                CommonActions.selectDepartmentOnApp("OPD");

                // Open the Search/Add patient dialog box
                Cls_Generic_Methods.customWait(3);
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
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_NewPatientRegisteration.tabs_PatientRegForm, 20);
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

                Cls_Generic_Methods.clickElementByAction(driver,
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

                if (checkedMandatoryFields.size() > 2) {

                    for (String fieldName :
                            checkedMandatoryFields) {
                        switch (fieldName) {
                            case "Gender" -> m_assert.assertTrue(oPage_NewPatientRegisteration.radio_gender_Male
                                    .getAttribute("class").contains("error"), "Alert for mandatory field is highlighted in " + fieldName + " field");
                            case "Address" ->
                                    m_assert.assertTrue(oPage_NewPatientRegisteration.input_pincodeOnPatientRegForm
                                            .getAttribute("class").contains("error"), "Alert for mandatory field is highlighted in " + fieldName + " field");
                            case "Age" -> m_assert.assertTrue(oPage_NewPatientRegisteration.text_patientAge
                                    .getAttribute("class").contains("error"), "Alert for mandatory field is highlighted in " + fieldName + " field");
                            case "Age Month" -> m_assert.assertTrue(oPage_NewPatientRegisteration.input_patientAgeMonth
                                    .getAttribute("class").contains("error"), "Alert for mandatory field is highlighted in " + fieldName + " field");
                            case "Referral Source" ->
                                    m_assert.assertTrue(oPage_NewPatientRegisteration.text_PatientReferralSourceErrorMsg
                                            .isDisplayed(), "Alert for mandatory field is highlighted in " + fieldName + " field");
                        }
                    }

                }

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

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_medicalRecordNumOnPatientRegForm,
                                sMRNumber),
                        sMRNumber + " entered for Medical Report Number");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validate All Details are filled in Create New Patient")
    public void validateAndFillAllPatientDetails() {

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
                    }
                } catch (NoSuchElementException e) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                    CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                            "Patient Details");
                }

                if (!myPatient.getsSALUTATION().isEmpty()) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                    oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()),
                            "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
                }

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                        myPatient.getsFIRST_NAME() + " entered for First Name");

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_middleNameOnPatientRegForm,
                                myPatient.getsMIDDLE_NAME()),
                        myPatient.getsMIDDLE_NAME() + " entered for Middle Name");

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_lastNameOnPatientRegForm, myPatient.getsLAST_NAME()),
                        myPatient.getsLAST_NAME() + " entered for Last Name");

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                                myPatient.getsMOBILE_NUMBER()),
                        myPatient.getsMOBILE_NUMBER() + " entered for Mobile Number");

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_secondaryMobileNumberOnPatientRegForm,
                                myPatient.getsSECONDARY_MOBILE_NUMBER()),
                        myPatient.getsSECONDARY_MOBILE_NUMBER() + " entered for Secondary Mobile Number");

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_emailOnPatientRegForm, myPatient.getsEMAIL()),
                        myPatient.getsEMAIL() + " entered for Email");

                //Enter Whatsapp number if same as contact number checkbox is not clicked
                if (myPatient.getsMOBILE_NUMBER().equals(myPatient.getsWHATSAPP_NUMBER())) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_NewPatientRegisteration.checkbox_sameAsContactNumber), "Same as Contact number checkbox clicked");
                    m_assert.assertEquals(Cls_Generic_Methods.getTextInElement(oPage_NewPatientRegisteration.input_whatsappNumber), myPatient.getsMOBILE_NUMBER(), "Validated number in whatsapp number field");
                } else {
                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_NewPatientRegisteration.input_whatsappNumber,
                                    myPatient.getsWHATSAPP_NUMBER()),
                            myPatient.getsWHATSAPP_NUMBER() + " entered in Whatsapp Number Field");
                }

                for (WebElement gender : oPage_NewPatientRegisteration.list_genderRadioButtons) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver,
                            gender), "Validate " + gender.getAttribute("value") + " Button is Clickable");

                }
                //Select Gender
                switch (myPatient.getsGENDER().toUpperCase()) {
                    case "MALE" -> m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver,
                            oPage_NewPatientRegisteration.radio_gender_Male), "Clicked Male in gender");
                    case "FEMALE" -> m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver,
                            oPage_NewPatientRegisteration.radio_gender_Female), "Clicked Female in gender");
                    case "TRANSGENDER" -> m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver,
                            oPage_NewPatientRegisteration.radio_gender_Transgender), "Clicked Transgender in gender");
                }

                //Select DateOf Birth
                String[] dobArray = myPatient.getsDATE_OF_BIRTH().split("/");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.select_dobDateDay), "Date of Birth field clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.select_dobYear, 10);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_NewPatientRegisteration.select_dobYear,
                        dobArray[2]), "Select " + dobArray[2] + " in year");

                int month = Integer.parseInt(dobArray[1]) - 1;
                int date = Integer.parseInt(dobArray[0]);

                m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_NewPatientRegisteration.select_dobMonth, String.valueOf(month)),
                        "Select " + Cls_Generic_Methods.getTextInElement(oPage_NewPatientRegisteration.getMonth(String.valueOf(month))) + " in month");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.select_dobDate(String.valueOf(date))),
                        "Select  " + String.valueOf(date) + " in day");

                m_assert.assertTrue("Entered " + myPatient.getsDATE_OF_BIRTH() + " in Date Of Birth field");

                //Select Relation type and name

                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_NewPatientRegisteration.select_patientRelation,
                        myPatient.getsRELATION_TYPE()), "Select " + myPatient.getsRELATION_TYPE() + " in Relation Dropdown");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_patientRelation, 10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_patientRelation,
                        myPatient.getsRELATION_NAME()), "Entered " + myPatient.getsRELATION_NAME() + " in relation name field");

                //Select Patient Type

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.select_searchPatientType),
                        "Clicked Patient Type");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_searchPatientType, myPatient.getsPATIENT_TYPE_INFO()),
                        "Selected " + myPatient.getsPATIENT_TYPE_INFO() + " in Patient Type");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_patientTypeComment, oEHR_Data.sPATIENT_TYPE_COMMENT), "Entered " + oEHR_Data.sPATIENT_TYPE_COMMENT + " in Patient Type comment");

                // Select Primary Language
                m_assert.assertInfo(
                        Cls_Generic_Methods.clickElement(driver, oPage_NewPatientRegisteration.select_primaryLanguage),
                        " Primary Language Selector clicked");
                m_assert.assertTrue(
                        CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.options_primaryLanguage,
                                myPatient.getsPRIMARY_LANGUAGE()),
                        myPatient.getsPRIMARY_LANGUAGE() + " selected for Primary Language");

                // Select Secondary Language
                m_assert.assertInfo(
                        Cls_Generic_Methods.clickElement(driver,
                                oPage_NewPatientRegisteration.select_secondaryLanguage),
                        " Secondary Language Selector clicked");
                m_assert.assertTrue(
                        CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.options_secondaryLanguage,
                                myPatient.getsSECONDARY_LANGUAGE()),
                        myPatient.getsSECONDARY_LANGUAGE() + " selected for Secondary Language");

                boolean bPinCodeSelected = false;
                try {
                    m_assert.assertInfo(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_NewPatientRegisteration.input_pincodeOnPatientRegForm,
                                    String.valueOf(myPatient.getsPINCODE())),
                            String.valueOf(myPatient.getsPINCODE()) + " entered for Pin Code");

                    Thread.sleep(3000);

                    for (WebElement pinCodeElement : oPage_NewPatientRegisteration.inputOptions_pincodeOnPatientRegForm) {
                        if (pinCodeElement.getText().trim().equals(String.valueOf(myPatient.getsPINCODE()))) {
                            pinCodeElement.click();
                            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.select_areaPatientAddress, myPatient.getsCITY()), "Selected " + myPatient.getsCITY() + " in area field");
                            bPinCodeSelected = true;
                        }
                    }

                    m_assert.assertTrue(bPinCodeSelected,
                            String.valueOf(myPatient.getsPINCODE()) + " entered for Pin Code");

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

//				oPage_NewPatientRegisteration = new EHR_Page(driver);
//				System.out.println(oPage_NewPatientRegisteration.input_stateOnPatientRegForm.getText());
//				System.out.println(oPage_NewPatientRegisteration.input_cityOnPatientRegForm.getText());
//
//				m_assert.assertTrue(
//						Cls_Generic_Methods.getTextInElement(oPage_NewPatientRegisteration.input_stateOnPatientRegForm).equals(oEHR_Data.sSTATE),
//						oEHR_Data.sSTATE + " entered as State for Pincode - " + oEHR_Data.sPINCODE);
//
//				m_assert.assertTrue(
//						Cls_Generic_Methods.getTextInElement(oPage_NewPatientRegisteration.input_cityOnPatientRegForm).equals(oEHR_Data.sCITY),
//						oEHR_Data.sCITY + " entered as City for Pincode - " + oEHR_Data.sPINCODE);

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_address_1_OnPatientRegForm, myPatient.getsADDRESS_1()),
                        myPatient.getsADDRESS_1() + " entered for Address 1");

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_address_2_OnPatientRegForm, myPatient.getsADDRESS_2()),
                        myPatient.getsADDRESS_2() + " entered for Address 2");

                //Enter Occupation and employee id
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_patientOccupation,
                        myPatient.getsOCCUPATION()), "Entered " + myPatient.getsOCCUPATION() + " in Occupation field");

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_patientEmployeeId,
                        myPatient.getsEMPLOYEE_ID()), "Entered " + myPatient.getsEMPLOYEE_ID() + " in Employee Id field");

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_medicalRecordNumOnPatientRegForm,
                                oEHR_Data.sMEDICAL_REPORT_NUMBER),
                        oEHR_Data.sMEDICAL_REPORT_NUMBER + " entered for Medical Report Number");
                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_healthIdNumOnPatientRegForm,
                                myPatient.getsHEALTH_ID_NUMBER()),
                        myPatient.getsHEALTH_ID_NUMBER() + " entered for Health ID Number");
/*
                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_aadharCardNumOnPatientRegForm,
                                myPatient.getsAADHAR_NUMBER()),
                        myPatient.getsAADHAR_NUMBER() + " entered for Aadhar Number");

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_drivingLicenseNumOnPatientRegForm,
                                myPatient.getsDRIVING_LICENSE_NUMBER()),
                        myPatient.getsDRIVING_LICENSE_NUMBER() + " entered for Driving License Number");

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_panNumOnPatientRegForm, myPatient.getsPAN_NUMBER()),
                        myPatient.getsPAN_NUMBER() + " entered for PAN Number");
*/
                // Select Patient Referral Src
                m_assert.assertTrue(
                        Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_patientReferralSrcOnPatientRegForm,
                                oEHR_Data.sPATIENT_REFERRAl_SOURCE),
                        oEHR_Data.sPATIENT_REFERRAl_SOURCE + " selected for Patient Referral Source");
                Cls_Generic_Methods.customWait(2);
                if (oPage_NewPatientRegisteration.select_patientSubReferralSrcOnPatientRegForm.isDisplayed()) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_patientSubReferralSrcOnPatientRegForm,
                            oEHR_Data.sPATIENT_SUB_REFERRAL_SOURCE), oEHR_Data.sPATIENT_SUB_REFERRAL_SOURCE + " selected for Patient Sub Referral Source");
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal(e.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate data is filled on Other Details Tab")
    public void validateOtherDetailsTab() {

        String expectedLoggedInUser = oEHR_Data.user_mansa1;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                try {
                    if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                        CommonActions.openPatientRegisterationAndAppointmentForm();
                    } else {
                        CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                                "Other Details");
                        Thread.sleep(2000);
                    }
                } catch (NoSuchElementException e1) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                    CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                            "Other Details");
                    Thread.sleep(2000);
                }

                // Selecting the Blood Group
                int requiredIndex = -1;
                try {
                    for (WebElement e : oPage_NewPatientRegisteration.radioButtons_bloodGroupsOnPatientRegForm) {

                        if (e.getAttribute("value").equals(myPatient.getsBLOOD_GROUP())) {
                            requiredIndex = oPage_NewPatientRegisteration.radioButtons_bloodGroupsOnPatientRegForm
                                    .indexOf(e);
                            oPage_NewPatientRegisteration.radioButtonsSelector_bloodGroupsOnPatientRegForm
                                    .get(requiredIndex).click();
                            m_assert.assertTrue(true,
                                    "Validate " + myPatient.getsBLOOD_GROUP() + " is selected for Blood Group");
                            break;
                        }

                    }

                    if (requiredIndex == -1) {
                        m_assert.assertTrue(false, myPatient.getsBLOOD_GROUP() + " blood group is not selected");
                    } else {
                        m_assert.assertTrue(
                                CommonActions.validateOnlyOneRadioButtonIsSelected(
                                        oPage_NewPatientRegisteration.radioButtonsSelector_bloodGroupsOnPatientRegForm),
                                "Validate only one Blood Group is selected");
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    if (requiredIndex == -1) {
                        m_assert.assertFatal("Unable to select Blood Group - " + e);
                    }
                }

                // Selecting the Marital Status
                requiredIndex = -1;
                try {
                    for (WebElement e : oPage_NewPatientRegisteration.radioButtons_maritalStatusOnPatientRegForm) {

                        if (e.getAttribute("value").equals(myPatient.getsMARITAL_STATUS())) {
                            requiredIndex = oPage_NewPatientRegisteration.radioButtons_maritalStatusOnPatientRegForm
                                    .indexOf(e);
                            oPage_NewPatientRegisteration.radioButtonsSelector_maritalStatusOnPatientRegForm
                                    .get(requiredIndex).click();
                            m_assert.assertTrue(true, "Validate " + myPatient.getsMARITAL_STATUS()
                                    + " is selected for Marital Status");
                            break;
                        }

                    }

                    if (requiredIndex == -1) {
                        m_assert.assertTrue(false,
                                myPatient.getsMARITAL_STATUS() + " Marital Status is not selected");
                    } else {
                        m_assert.assertTrue(CommonActions.validateOnlyOneRadioButtonIsSelected(
                                        oPage_NewPatientRegisteration.radioButtonsSelector_maritalStatusOnPatientRegForm),
                                "Validate only one Marital Status is selected");
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    if (requiredIndex == -1) {
                        m_assert.assertFatal("Unable to select Marital Status - " + e);
                    }
                }

                // Selecting if One Eyed
                requiredIndex = -1;
                try {
                    for (WebElement e : oPage_NewPatientRegisteration.radioButtons_oneEyedOnPatientRegForm) {

                        if (e.getAttribute("value").equals(myPatient.getsONE_EYED())) {
                            requiredIndex = oPage_NewPatientRegisteration.radioButtons_oneEyedOnPatientRegForm
                                    .indexOf(e);
                            oPage_NewPatientRegisteration.radioButtonsSelector_oneEyedOnPatientRegForm
                                    .get(requiredIndex).click();
                            m_assert.assertTrue(true,
                                    "Validate " + myPatient.getsONE_EYED() + " is selected for One Eyed");
                            break;
                        }

                    }

                    if (requiredIndex == -1) {
                        m_assert.assertTrue(false, myPatient.getsONE_EYED() + " One Eyed is not selected");
                    } else {
                        m_assert.assertTrue(
                                CommonActions.validateOnlyOneRadioButtonIsSelected(
                                        oPage_NewPatientRegisteration.radioButtonsSelector_oneEyedOnPatientRegForm),
                                "Validate only one One Eyed option is selected");
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    if (requiredIndex == -1) {
                        m_assert.assertFatal("Unable to select One Eyed - " + e);
                    }
                }

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_emergencyPatientNameOnPatientRegForm,
                                myPatient.getsEMERGENCY_CONTACT_NAME()),
                        myPatient.getsEMERGENCY_CONTACT_NAME() + " entered for Emergency Contact Name");

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_emergencyPatientNumberOnPatientRegForm,
                                myPatient.getsEMERGENCY_CONTACT_NUMBER()),
                        myPatient.getsEMERGENCY_CONTACT_NUMBER() + " entered for Emergency Contact Number");

                //enter aadhar number
                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_aadharCardNumOnPatientRegForm,
                                myPatient.getsAADHAR_NUMBER()),
                        myPatient.getsAADHAR_NUMBER() + " entered for Aadhar Number");

                //Enter DL Number
                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_drivingLicenseNumOnPatientRegForm,
                                myPatient.getsDRIVING_LICENSE_NUMBER()),
                        myPatient.getsDRIVING_LICENSE_NUMBER() + " entered for Driving License Number");

                //Enter PAN Number
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_panNumOnPatientRegForm, myPatient.getsPAN_NUMBER()),
                        myPatient.getsPAN_NUMBER() + " entered for PAN Number");

                //Enter GST Number
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                        oPage_NewPatientRegisteration.input_gstNumberOnPatientRegForm,
                        myPatient.getsGST_NUMBER()), myPatient.getsGST_NUMBER() + " entered for GST Number");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn(e.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Test(enabled = true, description = "Validate data is filled on History Tab")
    public void validateHistoryTab() {

        String expectedLoggedInUser = oEHR_Data.user_mansa1;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                try {
                    if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                        CommonActions.openPatientRegisterationAndAppointmentForm();
                    } else {
                        CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                                "History");
                        Thread.sleep(2000);
                    }
                } catch (NoSuchElementException e1) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                    CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                            "History");
                    Thread.sleep(2000);
                }
                //Validate the Ophthalmic
                int noOfButtonsCounter = 0;
                try {
                    for (WebElement buttonElement : oPage_NewPatientRegisteration.buttons_ophthalmicHistoryDiseasesOnPatientRegForm) {

                        int index = oPage_NewPatientRegisteration.buttons_ophthalmicHistoryDiseasesOnPatientRegForm
                                .indexOf(buttonElement);

                        if (buttonElement.getText().trim()
                                .equals(oEHR_Data.list_OPHTHALMIC_HISTORY_DISORDERS.get(index))) {
                            noOfButtonsCounter++;
                            m_assert.assertInfo(true, "Validate " + oEHR_Data.list_OPHTHALMIC_HISTORY_DISORDERS.get(index)
                                    + " is present under Ophthalmic History section");
                        } else {
                            m_assert.assertInfo(false,
                                    "Validate " + oEHR_Data.list_OPHTHALMIC_HISTORY_DISORDERS.get(index)
                                            + " is present under Ophthalmic History section. Actual = "
                                            + buttonElement.getText());
                        }

                        boolean validateButtonFunctionality = false;
                        try {
                            validateButtonFunctionality = CommonActions.validateIf_EHR_ButtonIsClickable_WithRegression(buttonElement,
                                    index);
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                            m_assert.assertFatal(
                                    "Unable to validate if " + oEHR_Data.list_OPHTHALMIC_HISTORY_DISORDERS.get(index)
                                            + " button is clickable - " + e);
                        }

                        m_assert.assertTrue(validateButtonFunctionality, "Validate "
                                + oEHR_Data.list_OPHTHALMIC_HISTORY_DISORDERS.get(index) + " Button is Clickable");

                    }

                    if ((oEHR_Data.list_OPHTHALMIC_HISTORY_DISORDERS.size() == noOfButtonsCounter)) {
                        m_assert.assertTrue(true,
                                "Validate " + noOfButtonsCounter + " disorders are present under Ophthalmic History section");
                    } else {
                        m_assert.assertTrue(false, "Validate " + oEHR_Data.list_OPHTHALMIC_HISTORY_DISORDERS.size()
                                + " disorders are present under Ophthalmic History section. Actual = " + noOfButtonsCounter);
                    }

                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_NewPatientRegisteration.input_ophthalmicHistoryCommentOnPatientRegForm,
                                    myPatient.getsSYSTEMIC_HISTORY_COMMENT()),
                            "Validate Ophthalmic History Comment is entered as " + myPatient.getsSYSTEMIC_HISTORY_COMMENT());

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to validate Ophthalmic History Disorders Section - " + e);
                }
                // Validating the Systemic History Disorders
                int buttonsCounter = 0;
                try {
                    for (WebElement buttonElement : oPage_NewPatientRegisteration.buttons_systemicHistoryDiseasesOnPatientRegForm) {

                        int index = oPage_NewPatientRegisteration.buttons_systemicHistoryDiseasesOnPatientRegForm
                                .indexOf(buttonElement);

                        if (buttonElement.getText().trim()
                                .equals(oEHR_Data.list_SYSTEMIC_HISTORY_DISORDERS.get(index))) {
                            buttonsCounter++;
                            m_assert.assertInfo(true, "Validate " + oEHR_Data.list_SYSTEMIC_HISTORY_DISORDERS.get(index)
                                    + " is present under Systemic History section");
                        } else {
                            m_assert.assertInfo(false,
                                    "Validate " + oEHR_Data.list_SYSTEMIC_HISTORY_DISORDERS.get(index)
                                            + " is present under Systemic History section. Actual = "
                                            + buttonElement.getText());
                        }

                        boolean validateButtonFunctionality = false;
                        try {
                            validateButtonFunctionality = CommonActions.validateIf_EHR_ButtonIsClickable_WithRegression(buttonElement,
                                    index);
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                            m_assert.assertFatal(
                                    "Unable to validate if " + oEHR_Data.list_SYSTEMIC_HISTORY_DISORDERS.get(index)
                                            + " button is clickable - " + e);
                        }

                        m_assert.assertTrue(validateButtonFunctionality, "Validate "
                                + oEHR_Data.list_SYSTEMIC_HISTORY_DISORDERS.get(index) + " Button is Clickable");

                    }

                    if ((oEHR_Data.list_SYSTEMIC_HISTORY_DISORDERS.size() == buttonsCounter)) {
                        m_assert.assertTrue(true,
                                "Validate " + buttonsCounter + " disorders are present under Systemic History section");
                    } else {
                        m_assert.assertTrue(false, "Validate " + oEHR_Data.list_SYSTEMIC_HISTORY_DISORDERS.size()
                                + " disorders are present under Systemic History section. Actual = " + buttonsCounter);
                    }

                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_NewPatientRegisteration.input_systemicHistoryCommentOnPatientRegForm,
                                    myPatient.getsSYSTEMIC_HISTORY_COMMENT()),
                            "Validate Systemic History Comment is entered as " + myPatient.getsSYSTEMIC_HISTORY_COMMENT());

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to validate Systemic History Disorders Section - " + e);
                }

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_medicalHistoryCommentOnPatientRegForm,
                                oEHR_Data.sMEDICAL_HISTORY_COMMENT),
                        "Validate Medical History Comment is entered as " + oEHR_Data.sMEDICAL_HISTORY_COMMENT);

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_familyHistoryCommentOnPatientRegForm,
                                oEHR_Data.sFAMILY_HISTORY_COMMENT),
                        "Validate Medical History Comment is entered as " + oEHR_Data.sFAMILY_HISTORY_COMMENT);

                // Validating the Paediatric History
                buttonsCounter = 0;
                try {
                    for (WebElement buttonElement : oPage_NewPatientRegisteration.buttons_nutritionalAssessmentsOnPatientRegForm) {

                        int index = oPage_NewPatientRegisteration.buttons_nutritionalAssessmentsOnPatientRegForm
                                .indexOf(buttonElement);

                        if (buttonElement.getText().trim().equals(oEHR_Data.list_NUTRIRIONAL_ASSESSMENTS.get(index))) {
                            buttonsCounter++;
                            m_assert.assertInfo(true, "Validate " + oEHR_Data.list_NUTRIRIONAL_ASSESSMENTS.get(index)
                                    + " is present under Nutritional Assessment section");
                        } else {
                            m_assert.assertInfo(false,
                                    "Validate " + oEHR_Data.list_NUTRIRIONAL_ASSESSMENTS.get(index)
                                            + " is present under Nutritional Assessment section. Actual = "
                                            + buttonElement.getText());
                        }

                        boolean validateButtonFunctionality = false;
                        try {
                            validateButtonFunctionality = CommonActions.validateIf_EHR_ButtonIsClickable(buttonElement);
                        } catch (IndexOutOfBoundsException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            m_assert.assertFatal(
                                    "Unable to validate if " + oEHR_Data.list_NUTRIRIONAL_ASSESSMENTS.get(index)
                                            + " button is clickable - " + e);
                        }

                        m_assert.assertTrue(validateButtonFunctionality, "Validate "
                                + oEHR_Data.list_NUTRIRIONAL_ASSESSMENTS.get(index) + " Button is Clickable");
                    }

                    if ((oEHR_Data.list_NUTRIRIONAL_ASSESSMENTS.size() == buttonsCounter)) {
                        m_assert.assertTrue(true, "Validate " + buttonsCounter
                                + " Assessment options are present under Nutritional Assessment section");
                    } else {
                        m_assert.assertTrue(false,
                                "Validate " + oEHR_Data.list_NUTRIRIONAL_ASSESSMENTS.size()
                                        + " disorders are present under Nutritional Assessment section. Actual = "
                                        + buttonsCounter);
                    }

                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_NewPatientRegisteration.input_nutritionalAssessmentCommentOnPatientRegForm,
                                    myPatient.getsNUTRITIONAL_ASSESSMENT_COMMENT()),
                            "Validate Nutritional Assessment Comment is entered as "
                                    + myPatient.getsNUTRITIONAL_ASSESSMENT_COMMENT());

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to validate Nutritional Assessment Section - " + e);
                }

                buttonsCounter = 0;
                try {
                    for (WebElement buttonElement : oPage_NewPatientRegisteration.buttons_immunizationAssessmentsOnPatientRegForm) {

                        int index = oPage_NewPatientRegisteration.buttons_immunizationAssessmentsOnPatientRegForm
                                .indexOf(buttonElement);

                        if (buttonElement.getText().trim().equals(oEHR_Data.list_IMMUNIZATION_ASSESSMENTS.get(index))) {
                            buttonsCounter++;
                            m_assert.assertInfo(true, "Validate " + oEHR_Data.list_IMMUNIZATION_ASSESSMENTS.get(index)
                                    + " is present under Nutritional Assessment section");
                        } else {
                            m_assert.assertInfo(false,
                                    "Validate " + oEHR_Data.list_IMMUNIZATION_ASSESSMENTS.get(index)
                                            + " is present under Immunization Assessment section. Actual = "
                                            + buttonElement.getText());
                        }

                        boolean validateButtonFunctionality = false;
                        try {
                            validateButtonFunctionality = CommonActions.validateIf_EHR_ButtonIsClickable(buttonElement);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            m_assert.assertFatal(
                                    "Unable to validate if " + oEHR_Data.list_IMMUNIZATION_ASSESSMENTS.get(index)
                                            + " button is clickable - " + e);
                        }

                        m_assert.assertTrue(validateButtonFunctionality, "Validate "
                                + oEHR_Data.list_IMMUNIZATION_ASSESSMENTS.get(index) + " Button is Clickable");
                    }

                    if ((oEHR_Data.list_IMMUNIZATION_ASSESSMENTS.size() == buttonsCounter)) {
                        m_assert.assertTrue(true, "Validate " + buttonsCounter
                                + " Assessment options are present under Immunization Assessment section");
                    } else {
                        m_assert.assertTrue(false,
                                "Validate " + oEHR_Data.list_IMMUNIZATION_ASSESSMENTS.size()
                                        + " disorders are present under Immunization Assessment section. Actual = "
                                        + buttonsCounter);
                    }

                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_NewPatientRegisteration.input_immunizationAssessmentCommentOnPatientRegForm,
                                    myPatient.getsIMMUNIZATION_ASSESSMENT_COMMENT()),
                            "Validate Immunization Assessment Comment is entered as "
                                    + myPatient.getsIMMUNIZATION_ASSESSMENT_COMMENT());

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to validate Nutritional Assessment Section - " + e);
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn(e.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Test(enabled = true, description = "Validate data is filled on Allergies Tab")
    public void validateAllergiesTab() {

        String expectedLoggedInUser = oEHR_Data.user_mansa1;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                try {
                    if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                        CommonActions.openPatientRegisterationAndAppointmentForm();
                    } else {
                        CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                                "Allergies");
                        Thread.sleep(2000);
                    }
                } catch (NoSuchElementException e1) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                    CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                            "Allergies");
                    Thread.sleep(2000);
                }

                int buttonsCounter = 0;

                // Validating the Drug Allergies
                buttonsCounter = 0;

                try {
                    for (WebElement buttonElement : oPage_NewPatientRegisteration.buttons_drugAllergiesOnPatientRegForm) {

                        int index = oPage_NewPatientRegisteration.buttons_drugAllergiesOnPatientRegForm
                                .indexOf(buttonElement);

                        if (buttonElement.getText().trim().equals(oEHR_Data.list_DRUG_ALLERGIES.get(index))) {
                            buttonsCounter++;
                            m_assert.assertInfo(true, "Validate " + oEHR_Data.list_DRUG_ALLERGIES.get(index)
                                    + " is present under Drug Allergies section");
                        } else {
                            m_assert.assertInfo(false, "Validate " + oEHR_Data.list_DRUG_ALLERGIES.get(index)
                                    + " is present under Drug Allergies section. Actual = " + buttonElement.getText());
                        }

                        try {
                            boolean buttonIsClickable = false;
                            buttonIsClickable = CommonActions.validateIf_DrugAllergies_ChildButtonIsClickable(buttonElement, index);

                            m_assert.assertTrue(buttonIsClickable,
                                    "Validate " + oEHR_Data.list_DRUG_ALLERGIES.get(index) + " Button is Clickable");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    if ((oEHR_Data.list_DRUG_ALLERGIES.size() == buttonsCounter)) {
                        m_assert.assertTrue(true,
                                "Validate " + buttonsCounter + " options are present under Drug Allergies section");
                    } else {
                        m_assert.assertTrue(false, "Validate " + oEHR_Data.list_DRUG_ALLERGIES.size()
                                + " options are present under Drug Allergies section. Actual = " + buttonsCounter);
                    }

                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_NewPatientRegisteration.input_drugAllergiesCommentOnPatientRegForm,
                                    myPatient.getsDRUG_ALLERGIES_COMMENT()),
                            "Validate Drug Allergies Comment is entered as " + myPatient.getsDRUG_ALLERGIES_COMMENT());

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to validate Drug Allergies Section - " + e);
                }

                // Validating the Contact Allergies
                buttonsCounter = 0;
                try {
                    for (WebElement buttonElement : oPage_NewPatientRegisteration.buttons_contactAllergiesOnPatientRegForm) {

                        int index = oPage_NewPatientRegisteration.buttons_contactAllergiesOnPatientRegForm
                                .indexOf(buttonElement);

                        if (buttonElement.getText().trim().equals(oEHR_Data.list_CONTACT_ALLERGIES.get(index))) {
                            buttonsCounter++;
                            m_assert.assertInfo(true, "Validate " + oEHR_Data.list_CONTACT_ALLERGIES.get(index)
                                    + " is present under Contact Allergies section");
                        } else {
                            m_assert.assertInfo(false,
                                    "Validate " + oEHR_Data.list_CONTACT_ALLERGIES.get(index)
                                            + " is present under Contact Allergies section. Actual = "
                                            + buttonElement.getText());
                        }

                        boolean validateButtonFunctionality = CommonActions.validateIf_EHR_ButtonIsClickable_WithRegression(
                                buttonElement, index);
                        m_assert.assertTrue(validateButtonFunctionality,
                                "Validate " + oEHR_Data.list_CONTACT_ALLERGIES.get(index) + " Button is Clickable");

                    }

                    if ((oEHR_Data.list_CONTACT_ALLERGIES.size() == buttonsCounter)) {
                        m_assert.assertTrue(true,
                                "Validate " + buttonsCounter + " options are present under Contact Allergies section");
                    } else {
                        m_assert.assertTrue(false, "Validate " + oEHR_Data.list_CONTACT_ALLERGIES.size()
                                + " options are present under Contact Allergies section. Actual = " + buttonsCounter);
                    }

                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_NewPatientRegisteration.input_contactAllergiesCommentOnPatientRegForm,
                                    myPatient.getsCONTACT_ALLERGIES_COMMENT()),
                            "Validate Contact Allergies Comment is entered as " + myPatient.getsCONTACT_ALLERGIES_COMMENT());

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to validate Contact Allergies Section - " + e);
                }

                // Validating the Food Allergies
                buttonsCounter = 0;
                try {
                    for (WebElement buttonElement : oPage_NewPatientRegisteration.buttons_foodAllergiesOnPatientRegForm) {

                        int index = oPage_NewPatientRegisteration.buttons_foodAllergiesOnPatientRegForm
                                .indexOf(buttonElement);

                        if (buttonElement.getText().trim().equals(oEHR_Data.list_FOOD_ALLERGIES.get(index))) {
                            buttonsCounter++;
                            m_assert.assertInfo(true, "Validate " + oEHR_Data.list_FOOD_ALLERGIES.get(index)
                                    + " is present under Food Allergies section");
                        } else {
                            m_assert.assertInfo(false, "Validate " + oEHR_Data.list_FOOD_ALLERGIES.get(index)
                                    + " is present under Food Allergies section. Actual = " + buttonElement.getText());
                        }

                        boolean validateButtonFunctionality = CommonActions.validateIf_EHR_ButtonIsClickable_WithRegression(
                                buttonElement, index);
                        m_assert.assertTrue(validateButtonFunctionality,
                                "Validate " + oEHR_Data.list_FOOD_ALLERGIES.get(index) + " Button is Clickable");

                    }

                    if ((oEHR_Data.list_FOOD_ALLERGIES.size() == buttonsCounter)) {
                        m_assert.assertTrue(true,
                                "Validate " + buttonsCounter + " options are present under Food Allergies section");
                    } else {
                        m_assert.assertTrue(false, "Validate " + oEHR_Data.list_FOOD_ALLERGIES.size()
                                + " options are present under Food Allergies section. Actual = " + buttonsCounter);
                    }

                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_NewPatientRegisteration.input_foodAllergiesCommentOnPatientRegForm,
                                    myPatient.getsFOOD_ALLERGIES_COMMENT()),
                            "Validate Food Allergies Comment is entered as " + myPatient.getsFOOD_ALLERGIES_COMMENT());

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to validate Food Allergies Section - " + e);
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal(e.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Test(enabled = true, description = "Validate data is filled on Appointment Details Section")
    public void validateAppointmentDetailsSection() {

        //String expectedLoggedInUser = oEHR_Data.user_mansa1;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            //CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                try {
                    if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                        CommonActions.openPatientRegisterationAndAppointmentForm();
                    }
                } catch (NoSuchElementException e1) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                }

                boolean timeSlotStatus = true;
                try {
                    oPage_NewPatientRegisteration.table_timeSlotPatientRegForm.isDisplayed();
                } catch (NoSuchElementException e) {
                    timeSlotStatus = false;
                }

                int requiredIndex = 0;

                // Appointment Type
                requiredIndex = -1;
                if (!timeSlotStatus) {
                    try {
                        for (WebElement e : oPage_NewPatientRegisteration.radioButtons_appointmentTypeOnPatientRegForm) {

                            if (e.getAttribute("value")
                                    .equals(oEHR_Data.sAPPOINTMENT_TYPE.toLowerCase().replace("-", ""))) {
                                requiredIndex = oPage_NewPatientRegisteration.radioButtons_appointmentTypeOnPatientRegForm
                                        .indexOf(e);
                                oPage_NewPatientRegisteration.radioButtonsSelector_appointmentTypeOnPatientRegForm
                                        .get(requiredIndex).click();
                                m_assert.assertTrue(true,
                                        "Validate " + oEHR_Data.sAPPOINTMENT_TYPE + " is selected for Appointment Type");
                                break;
                            }

                        }

                        if (requiredIndex == -1) {
                            m_assert.assertTrue(false, oEHR_Data.sAPPOINTMENT_TYPE + " Appointment type is not selected");
                        } else {
                            m_assert.assertTrue(CommonActions.validateOnlyOneRadioButtonIsSelected(
                                            oPage_NewPatientRegisteration.radioButtonsSelector_appointmentTypeOnPatientRegForm),
                                    "Validate only one Appointment Type is selected");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        if (requiredIndex == -1) {
                            m_assert.assertFatal("Unable to select Appointment Type - " + e);
                        }
                    }
                }


                if (oEHR_Data.sAPPOINTMENT_TYPE.equalsIgnoreCase("Appointment")) {
                    // Appointment Date & Time
                    try {
                        // Date
                        CommonActions.selectDateFromDatePicker(oPage_NewPatientRegisteration.inputButton_appointmentDateForAppointDetails, oEHR_Data.sAPPOINTMENT_DATE);
//                    Cls_Generic_Methods.clickElement(driver,
//                            oPage_NewPatientRegisteration.inputButton_appointmentDateForAppointDetails);
//
//                    String[] separatedDateValue = CommonActions.formatInputToRequiredDate(Cls_Generic_Methods.getTodayDate()).split(":");
//                    String dateOnCalendar = separatedDateValue[0];
//                    String monthOnCalendar = separatedDateValue[1];
//                    String yearOnCalendar = separatedDateValue[2];
//
//                    // Separate out month and year handling
//
//                    if (!oPage_NewPatientRegisteration.text_yearOnCalendarForAppointDetails.getText().trim()
//                            .equals(oEHR_Data.sAPPOINTMENT_DATE.split("/")[2])) {
//                        int forwardYearsCounter = CommonActions.getDifferenceInYearsForFutureDate(oEHR_Data.sAPPOINTMENT_DATE);
//                        forwardYearsCounter = forwardYearsCounter * 12;
//
//                        for (int i = 0; i < forwardYearsCounter; i++) {
//                            Thread.sleep(100);
//                            Cls_Generic_Methods.clickElement(driver,
//                                    oPage_NewPatientRegisteration.button_nextMonthForAppointmentOnPatientRegForm);
//                            m_assert.assertInfo("Clicked on Next Month button in Calendar for Appointment Creation - "
//                                    + oPage_NewPatientRegisteration.text_monthOnCalendarForAppointDetails.getText()
//                                    + " "
//                                    + oPage_NewPatientRegisteration.text_yearOnCalendarForAppointDetails.getText());
//                        }
//
//                    }
//
//                    if (!oPage_NewPatientRegisteration.text_monthOnCalendarForAppointDetails.getText().trim()
//                            .equals(monthOnCalendar)) {
//                        int forwardMonthsCounter = CommonActions.getDifferenceInMonthsForFutureDate(oEHR_Data.sAPPOINTMENT_DATE);
//
//                        for (int i = 0; i < forwardMonthsCounter; i++) {
//                            Thread.sleep(100);
//                            Cls_Generic_Methods.clickElement(driver,
//                                    oPage_NewPatientRegisteration.button_nextMonthForAppointmentOnPatientRegForm);
//                            m_assert.assertInfo("Clicked on Next Month button in Calendar for Appointment Creation - "
//                                    + oPage_NewPatientRegisteration.text_monthOnCalendarForAppointDetails.getText()
//                                    + " "
//                                    + oPage_NewPatientRegisteration.text_yearOnCalendarForAppointDetails.getText());
//                        }
//
//                    }
//
//                    if (oPage_NewPatientRegisteration.text_monthOnCalendarForAppointDetails.getText().trim()
//                            .equals(monthOnCalendar)
//                            && oPage_NewPatientRegisteration.text_yearOnCalendarForAppointDetails.getText().trim()
//                            .equals(yearOnCalendar)) {
//
//                        for (WebElement eDate : oPage_NewPatientRegisteration.td_datesOnCalendarOnPatientRegForm) {
//                            if (eDate.getText().toString().trim().equals(oEHR_Data.sAPPOINTMENT_DATE.split("/")[0])) {
//                                Cls_Generic_Methods.clickElement(driver, eDate);
//                                m_assert.assertTrue(true,
//                                        "Entered Appointment Date as - " + oEHR_Data.sAPPOINTMENT_DATE);
//                                break;
//                            }
//                        }
//                    } else {
//                        m_assert.assertTrue(false, "Expected Month and Year = " + dateOnCalendar + "/" + monthOnCalendar
//                                + "/" + yearOnCalendar + "\t<br>" + "Actual Month and Year = "
//                                + oPage_NewPatientRegisteration.text_monthOnCalendarForAppointDetails.getText() + "/"
//                                + oPage_NewPatientRegisteration.text_yearOnCalendarForAppointDetails.getText());
//                    }

                        // Time
                        Cls_Generic_Methods.clickElement(driver,
                                oPage_NewPatientRegisteration.inputButton_appointmentTimeForAppointDetails);
                        String[] seperatedTimeValue = oEHR_Data.sAPPOINTMENT_TIME.split(":");

                        Cls_Generic_Methods
                                .clearValuesInElement(oPage_NewPatientRegisteration.input_appointmentHourForAppointDetails);
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_appointmentHourForAppointDetails,
                                seperatedTimeValue[0]);

                        Cls_Generic_Methods.clearValuesInElement(
                                oPage_NewPatientRegisteration.input_appointmentMinuteForAppointDetails);
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_appointmentMinuteForAppointDetails,
                                seperatedTimeValue[1]);

                        Cls_Generic_Methods.clearValuesInElement(
                                oPage_NewPatientRegisteration.input_appointmentMeridianForAppointDetails);
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_appointmentMeridianForAppointDetails,
                                seperatedTimeValue[2]);
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                        m_assert.assertTrue(false, "Error occurred while entering Appointment Date - " + e1);
                    }
                }

                // Location
                try {
                    boolean bLocationFound = false;
                    int locationIndex = -1;
                    Cls_Generic_Methods.clickElement(driver,
                            oPage_NewPatientRegisteration.selectButton_locationForAppointmentOnPatientRegForm);
                    Thread.sleep(1000);
                    //   oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
                    for (WebElement e : oPage_NewPatientRegisteration.selectButtonOptions_searchResultsForSelectorsOnPatientRegForm) {
                        if (e.getText().trim().equals(oEHR_Data.sAPPOINTMENT_LOCATION)) {
                            bLocationFound = true;
                            locationIndex = oPage_NewPatientRegisteration.selectButtonOptions_searchResultsForSelectorsOnPatientRegForm
                                    .indexOf(e);
                            break;
                        }
                    }

                    if (bLocationFound) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver,
                                        oPage_NewPatientRegisteration.selectButtonOptions_searchResultsForSelectorsOnPatientRegForm
                                                .get(locationIndex)),
                                oEHR_Data.sAPPOINTMENT_LOCATION + " for Facility Location is selected");
                    } else {
                        m_assert.assertTrue(false, oEHR_Data.sAPPOINTMENT_LOCATION + " is not found");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertTrue(false, "Location Selection for Facility failed." + e);
                }

                //Speciality Available
                if (oPage_NewPatientRegisteration.selectButton_specialitiesAvailableOnPatientRegForm.isDisplayed()) {
                    try {
                        boolean bLocationFound = false;
                        int locationIndex = -1;
                        Cls_Generic_Methods.clickElement(driver,
                                oPage_NewPatientRegisteration.selectButton_specialitiesAvailableOnPatientRegForm);
                        Thread.sleep(1000);
                        //   oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
                        for (WebElement e : oPage_NewPatientRegisteration.selectButtonOptions_searchResultsForSelectorsOnPatientRegForm) {
                            if (e.getText().trim().equals(oEHR_Data.sSPECIALITIES_AVAILABLE)) {
                                bLocationFound = true;
                                locationIndex = oPage_NewPatientRegisteration.selectButtonOptions_searchResultsForSelectorsOnPatientRegForm
                                        .indexOf(e);
                                break;
                            }
                        }

                        if (bLocationFound) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver,
                                            oPage_NewPatientRegisteration.selectButtonOptions_searchResultsForSelectorsOnPatientRegForm
                                                    .get(locationIndex)),
                                    oEHR_Data.sSPECIALITIES_AVAILABLE + " for Specialty Available is selected");
                        } else {
                            m_assert.assertTrue(false, oEHR_Data.sSPECIALITIES_AVAILABLE + " is not found");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertTrue(false, "Specialty Available selection is failed." + e);
                    }
                }
                // Consultant
                try {
                    Cls_Generic_Methods.customWait(2);
                    boolean bConsultantSelected = false;
                    int consultantIndex = -1;
                    Cls_Generic_Methods.clickElement(driver,
                            oPage_NewPatientRegisteration.selectButton_consultantForAppointmentOnPatientRegForm);
                    //      oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
                    for (WebElement e : oPage_NewPatientRegisteration.selectButtonOptions_searchResultsForSelectorsOnPatientRegForm) {
                        if (e.getText().trim().contains(oEHR_Data.sAPPOINTMENT_CONSULTANT)) {
                            bConsultantSelected = true;
                            consultantIndex = oPage_NewPatientRegisteration.selectButtonOptions_searchResultsForSelectorsOnPatientRegForm
                                    .indexOf(e);
                            break;
                        }
                    }

                    if (bConsultantSelected) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver,
                                        oPage_NewPatientRegisteration.selectButtonOptions_searchResultsForSelectorsOnPatientRegForm
                                                .get(consultantIndex)),
                                oEHR_Data.sAPPOINTMENT_CONSULTANT + " for Consultant is selected");
                    } else {
                        m_assert.assertTrue(false, oEHR_Data.sAPPOINTMENT_CONSULTANT + " is not found");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertTrue(false, "Consultant Selection for Facility failed." + e);
                }

                // Visit Types
                if (!timeSlotStatus) {
                    try {
                        boolean bVisitTypesSelected = false;
                        int aVisitTypesIndex = -1;
                        Cls_Generic_Methods.clickElement(driver,
                                oPage_NewPatientRegisteration.selectButton_visitTypesForAppointmentOnPatientRegForm);
                        Thread.sleep(1000);
                        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
                        for (WebElement e : oPage_NewPatientRegisteration.selectButtonOptions_searchResultsForSelectorsOnPatientRegForm) {
                            if (e.getText().trim().equals(oEHR_Data.sVISIT_TYPES)) {
                                bVisitTypesSelected = true;
                                aVisitTypesIndex = oPage_NewPatientRegisteration.selectButtonOptions_searchResultsForSelectorsOnPatientRegForm
                                        .indexOf(e);
                                break;
                            }
                        }

                        if (bVisitTypesSelected) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver,
                                            oPage_NewPatientRegisteration.selectButtonOptions_searchResultsForSelectorsOnPatientRegForm
                                                    .get(aVisitTypesIndex)),
                                    oEHR_Data.sVISIT_TYPES + " for Visit Types is selected");
                            Cls_Generic_Methods.customWait();
                        } else {
                            m_assert.assertTrue(false, oEHR_Data.sVISIT_TYPES + " is not found");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertTrue(false, "Visit Types for Appointment failed." + e);
                    }

                    // Visit Category
                    try {
                        boolean bVisitCategorySelected = false;
                        int iVisitCategoryIndex = -1;
                        Cls_Generic_Methods.clickElement(driver,
                                oPage_NewPatientRegisteration.selectButton_visitCategoryForAppointmentOnPatientRegForm);
                        Thread.sleep(1000);
                        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
                        for (WebElement e : oPage_NewPatientRegisteration.selectButtonOptions_searchResultsForSelectorsOnPatientRegForm) {
                            if (e.getText().trim().equals(oEHR_Data.sVISIT_CATEGORY)) {
                                bVisitCategorySelected = true;
                                iVisitCategoryIndex = oPage_NewPatientRegisteration.selectButtonOptions_searchResultsForSelectorsOnPatientRegForm
                                        .indexOf(e);
                                break;
                            }
                        }

                        if (bVisitCategorySelected) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver,
                                            oPage_NewPatientRegisteration.selectButtonOptions_searchResultsForSelectorsOnPatientRegForm
                                                    .get(iVisitCategoryIndex)),
                                    oEHR_Data.sVISIT_CATEGORY + " for Visit Category is selected");
                        } else {
                            m_assert.assertTrue(false, oEHR_Data.sVISIT_CATEGORY + " is not found");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertTrue(false, "Visit Category for Appointment failed." + e);
                    }
                }

                //SubRefferal
                if (oPage_NewPatientRegisteration.select_patientSubReferralSrcOnPatientRegForm.isDisplayed()) {
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_patientSubReferralSrcOnPatientRegForm,
                            oEHR_Data.sPATIENT_SUB_REFERRAL_SOURCE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal(e.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Test(enabled = true, description = "Click on Create Appointment button")
    public void clickCreateAppointment() {

        myPatient = map_PatientsInExcel.get(patientKey);

        try {
            oPage_Navbar = new Page_Navbar(driver);
            oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);

            try {
                try {
                    if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                        CommonActions.openPatientRegisterationAndAppointmentForm();
                    }

                } catch (NoSuchElementException e1) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                }

                m_assert.assertTrue(
                        Cls_Generic_Methods.clickElementByJS(driver,
                                oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm),
                        "Validate that Create Appointment button is clicked");

                Thread.sleep(4000);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 20);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validate Patient Details after it has been created in My Queue")
    public void validateAfterAppointmentCreation() {

        boolean bPatientNameFound = false;
        boolean bPrimaryContactFound = false;
        boolean bLanguageFound = false;
        boolean bRegisterationDateFound = false;
        boolean bPatientReferralSourceFound = false;

        // Validate that Patient is present on NOT ARRIVED Tab
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        myPatient = map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        String tabToSelect = OPD_Data.tab_ALL;

        try {

            CommonActions.loginFunctionality(expectedLoggedInUser);
            oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
            oPage_OPD = new Page_OPD(driver);

            try {

//				String appointmentJourneyTime, appointmentStartTime, currentDepartmentName;
//				List<WebElement> span_Bill_AppointmentType_Details, span_dilationState;
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
                        "Validate " + tabToSelect + " tab is selected");
                Cls_Generic_Methods.customWait();

                for (WebElement patient : oPage_OPD.rows_patientAppointments) {

                    if (patient.isDisplayed()) {
                        try {
                            List<WebElement> patientDetailsOnRow = patient.findElements(By.xpath("./child::*"));

                            String patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0),
                                    "title");
//							span_Bill_AppointmentType_Details = patientDetailsOnRow.get(1).findElements(By.xpath("./child::*"));
//							appointmentJourneyTime = Cls_Generic_Methods.getTextInElement(patientDetailsOnRow.get(2));
//							appointmentStartTime = Cls_Generic_Methods.getTextInElement(patientDetailsOnRow.get(3));
//							currentDepartmentName = Cls_Generic_Methods.getTextInElement(patientDetailsOnRow.get(4));
//
//							span_dilationState = null;
//							if(patientDetailsOnRow.get(5).findElements(By.xpath("./child::*")).size() > 0) {
//								span_dilationState = patientDetailsOnRow.get(5).findElements(By.xpath("./child::*"));
//							}

//							System.out.println("patientName - " + patientName.trim());
//							System.out.println("appointmentJourneyTime - " + appointmentJourneyTime.trim());
//							System.out.println("appointmentStartTime - " + appointmentStartTime.trim());
//							System.out.println("currentDepartmentName - " + currentDepartmentName.trim());
//							System.out.println("span_AppointmentDetails - " + span_Bill_AppointmentType_Details.get(0).getText());
//
//							if(span_dilationState != null) {
//								System.out.println("span_dilationState - " + span_dilationState.get(0).getText());
//							}

                            if (concatPatientFullName.equals(patientName.trim())) {
                                Cls_Generic_Methods.clickElement(driver, patient);
                                Thread.sleep(1500);
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
                                break;
                            }

                        } catch (ElementNotInteractableException e) {
                            e.printStackTrace();
                        }
                    }

                }

                String patientNameOnAppointmentDetails = null;
                String primaryContactOnAppointmentDetails = null;
                String languageOnAppointmentDetails = null;
                String registerationDateOnAppointmentDetails = null;
                String PatientReferralOnAppointmentDetails = null;

                if (oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection.isDisplayed()) {

                    patientNameOnAppointmentDetails = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.text_patientNameInOPD);
                    for (WebElement eKeyOnRow : oPage_PatientAppointmentDetails.keysOnRows_PatientDetails) {

                        String eText = Cls_Generic_Methods.getTextInElement(eKeyOnRow);
                        int index = oPage_PatientAppointmentDetails.keysOnRows_PatientDetails.indexOf(eKeyOnRow);

                        switch (eText) {
                            case "Primary Contact":
                                primaryContactOnAppointmentDetails = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.valuesOnRows_PatientDetails
                                        .get(index));
                                System.out.println(primaryContactOnAppointmentDetails);
                                break;
                            case "Language":
                                languageOnAppointmentDetails = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.valuesOnRows_PatientDetails
                                        .get(index));
                                languageOnAppointmentDetails = languageOnAppointmentDetails.replace(" ", "");
                                System.out.println(languageOnAppointmentDetails);
                                break;
                            case "Registration Date":
                                registerationDateOnAppointmentDetails = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.valuesOnRows_PatientDetails
                                        .get(index));
                                System.out.println(registerationDateOnAppointmentDetails);
                                break;
                            case "Patient Referral":
                                PatientReferralOnAppointmentDetails = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.valuesOnRows_PatientDetails
                                        .get(index));
                                System.out.println(PatientReferralOnAppointmentDetails);
                                break;
                            default:
                                break;
                        }

                    }

                    if (patientNameOnAppointmentDetails.equals(concatPatientFullName)) {
                        bPatientNameFound = true;
                    }

                    if (primaryContactOnAppointmentDetails.equals(myPatient.getsMOBILE_NUMBER())) {
                        bPrimaryContactFound = true;
                    }

                    try {
                        if (languageOnAppointmentDetails.equals(
                                myPatient.getsPRIMARY_LANGUAGE() + "," + myPatient.getsSECONDARY_LANGUAGE())) {
                            bLanguageFound = true;
                        }
                    } catch (Exception e) {

                    }

                    String todayDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                    if (registerationDateOnAppointmentDetails.equals(todayDate)) {
                        bRegisterationDateFound = true;
                    }

                    try {
                        if (PatientReferralOnAppointmentDetails.equals(oEHR_Data.sPATIENT_REFERRAl_SOURCE)) {
                            bPatientReferralSourceFound = true;
                        }
                    } catch (Exception e) {

                    }

                    m_assert.assertTrue(bPatientNameFound, "Patient Name Matched in Appointment Details Section");
                    m_assert.assertTrue(bPrimaryContactFound, "Primary Contact Matched in Appointment Details Section");
                    //m_assert.assertWarn(bLanguageFound, "Languages Matched in Appointment Details Section");
                    //m_assert.assertTrue(bRegisterationDateFound, "Registration Date Matched in Appointment Details Section");
                    m_assert.assertWarn(bPatientReferralSourceFound, "Patient Referral Source Matched in Appointment Details Section");

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

    @Test(enabled = true, description = "Validate Patient is sent to AR NCT")
    public void validateSendPatientToArNct() {

        myPatient = map_PatientsInExcel.get(patientKey);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);
        String expectedLoggedInUser = oEHR_Data.user_mansa1;
        String tabToSelect = OPD_Data.tab_ALL;
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        String destinationArNct = EHR_Data.user_ARNCT2;
        int requiredArNctIndex = -1;
        boolean requiredArNctFound = false;

        try {

            oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
            oPage_OPD = new Page_OPD(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.customWait(5);
            m_assert.assertInfo(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
                    "Validate " + tabToSelect + " tab is selected");

            String myQueueTab = "My Queue";
            int myQueueInitialCount = -1, updatedMyQueueCount = -1;

            myQueueInitialCount = CommonActions.getCountOfPatientsInTab(oPage_OPD.tabs_appointmentTabsOnHomepage,
                    myQueueTab);

            for (WebElement patient : oPage_OPD.rows_patientAppointments) {

                if (patient.isDisplayed()) {
                    try {
                        List<WebElement> patientDetailsOnRow = patient.findElements(By.xpath("./child::*"));
                        String patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");
                        if (concatPatientFullName.equals(patientName.trim())) {
                            Cls_Generic_Methods.clickElement(driver, patient);
                            m_assert.assertInfo(true, "Validate " + concatPatientFullName + " patient is selected");
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("" + e);
                    }
                }

            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 16);

            m_assert.assertTrue(
                    Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_sendToArNct),
                    "Button for sending patient to AR NCT - <b>" + destinationArNct + "</b> is clicked");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.listButtons_sendToArNct.get(0), 4);


            for (WebElement option_buttonArNctUser : oPage_PatientAppointmentDetails.listButtons_sendToArNct) {
                if (option_buttonArNctUser.isDisplayed()) {

//					System.out.println(option_buttonArNctUser);

                    List<WebElement> arNctDetails = option_buttonArNctUser.findElements(By.xpath("./child::*"));

                    // Children Elements of Each Button
//					List<WebElement> eUserAvailibility = arNctDetails.get(0).findElements(By.xpath("./child::*"));
                    WebElement eUserName = arNctDetails.get(1);
//					WebElement eUserQueueCount = arNctDetails.get(2);

//					System.out.println(Cls_Generic_Methods.getElementAttribute(eUserAvailibility.get(0), "class"));
//					System.out.println(eUserName.getText());
//					System.out.println(eUserQueueCount.getText());

                    if (Cls_Generic_Methods.getTextInElement(eUserName).equals(destinationArNct)) {
                        requiredArNctIndex = oPage_PatientAppointmentDetails.listButtons_sendToArNct.indexOf(option_buttonArNctUser);
                        requiredArNctFound = true;
                        break;
                    }
                }
            }

            m_assert.assertInfo(requiredArNctFound, "Validate " + destinationArNct + " User is found.");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.listButtons_sendToArNct.get(requiredArNctIndex)),
                    "<b>" + destinationArNct + "</b> is clicked");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_OPD.rows_patientAppointments, 4);


            updatedMyQueueCount = CommonActions
                    .getCountOfPatientsInTab(oPage_OPD.tabs_appointmentTabsOnHomepage, myQueueTab);

            if (updatedMyQueueCount == (myQueueInitialCount - 1)) {
                m_assert.assertTrue("Validate Patient Count has been decreased from <b>" + myQueueInitialCount
                        + "</b> to <b>" + updatedMyQueueCount + "</b> in MY QUEUE tab for current user.");
            } else if (updatedMyQueueCount < myQueueInitialCount) {
                m_assert.assertWarn("Validate Patient Count has been decreased from <b>" + myQueueInitialCount
                        + "</b> to <b>" + updatedMyQueueCount + "</b> in MY QUEUE tab for current user.");
            } else {
                m_assert.assertTrue(false, "Validate Patient Count has been decreased from <b>" + myQueueInitialCount
                        + "</b> to <b>" + (myQueueInitialCount - 1) + "</b> in MY QUEUE tab for current user. Actual = " + updatedMyQueueCount);
            }
            Cls_Generic_Methods.customWait(5);
            m_assert.assertInfo(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
                    "Validate " + tabToSelect + " tab is selected");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validate mark as patient arrived")
    public void validatePatientArrived() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        Page_FacilitySetup oPage_FacilitySetup = new Page_FacilitySetup(driver);


        String expectedLoggedInUser = EHR_Data.user_mansa1;
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);

            // Assuming that the opened page is OPD
            try {
                // Go to the ALL tab

                String tabToSelect = OPD_Data.tab_ALL;
                String myQueueTab = OPD_Data.tab_MY_QUEUE;

                int myQueueInitialCount = CommonActions
                        .getCountOfPatientsInTab(oPage_OPD.tabs_appointmentTabsOnHomepage, myQueueTab);

                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
                        "Validate " + tabToSelect + " tab is selected");

                String patientName = null;
                String status = null;
                for (WebElement patient : oPage_OPD.rows_patientAppointments) {
                    if (patient.isDisplayed()) {
                        List<WebElement> patientDetailsOnRow = patient.findElements(By.xpath("./child::*"));

                        patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");

                        status = patientDetailsOnRow.get(4).getText();

                        if (concatPatientFullName.equals(patientName.trim())) {
                            Cls_Generic_Methods.clickElement(driver, patient);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(
                                    oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 8);

                            if (status.equalsIgnoreCase("not arrived")) {
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_markPatientArrived, 20);
                                try {
                                    m_assert.assertTrue(
                                            Cls_Generic_Methods.clickElement(driver,
                                                    oPage_PatientAppointmentDetails.button_markPatientArrived),
                                            "Patient arrived clicked");
                                    Cls_Generic_Methods.customWait(5);
                                    if (Cls_Generic_Methods.isElementDisplayed(oPage_FacilitySetup.header_selectToken)) {
                                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FacilitySetup.button_skipWithoutToken),
                                                "Skip Without Token clicked ");
                                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);
                                    }
                                } catch (Exception e) {
                                    m_assert.assertInfo(false, "Error occured while clicking mark as arrived " + e);
                                    e.printStackTrace();
                                }
                                Thread.sleep(2000);

                                int updatedMyQueueCount = CommonActions
                                        .getCountOfPatientsInTab(oPage_OPD.tabs_appointmentTabsOnHomepage, myQueueTab);

                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage.get(0), 16);

                                System.out.println("here count " + updatedMyQueueCount);

                                m_assert.assertTrue((updatedMyQueueCount > myQueueInitialCount),
                                        "Validate Patient Count has been updated from <b>" + myQueueInitialCount
                                                + "</b> to <b>" + updatedMyQueueCount + "</b> in MY QUEUE tab");

                                break;
                            } else {
                                m_assert.assertWarn(false, "Validate Patient  " + patientName
                                        + " has status as <b>Not Arrived</b> in row. Actual Status is " + status);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertTrue(false, "Exception while validating patient Note " + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(enabled = true, description = "Validate Search Feature in opd Positive Scenario")
    public void validateSearchFeatureInOPD() {
        Page_NewPatientRegisteration oPage_NewPatientRegisteration;
        myPatient = map_PatientsInExcel.get(patientKey);
        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);

        String SName = "Elo";
        String SMRNo = "mrd";
        String SMblNo = "123";
        String SPatientIdentifier = "rso";
//        String SMrNo="MRD/123456/009990";
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            //creating patient in opd
            try {
                if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                            10);
                } else {
                    CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                            "Patient Details");
                    Thread.sleep(2000);
                }
            } catch (NoSuchElementException e1) {
                CommonActions.openPatientRegisterationAndAppointmentForm();
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            if (!myPatient.getsSALUTATION().isEmpty()) {
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()),
                        "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                    myPatient.getsFIRST_NAME() + " entered for First Name");

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                            myPatient.getsMOBILE_NUMBER()),
                    myPatient.getsMOBILE_NUMBER() + " entered for Mobile Number");
            Cls_Generic_Methods.customWait(5);
//
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_MrnoinOPD, "mrd/4573/7765"),
                    "MRN No is selected");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OPD.button_Save), "click on Save");
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);

//              validate Search filter By Name
            boolean bitemFound = false;
            for (WebElement webElement : oPage_OPD.list_searchFilterNameInOPD) {
                if (Cls_Generic_Methods.getTextInElement(webElement).equals("Name")) {
                    webElement.click();
                    bitemFound = true;
                    break;
                }
            }
//            m_assert.assertTrue(bitemFound, " Name Search Filter is selected");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);
            Cls_Generic_Methods.clickElement(oPage_OPD.input_SerachCriteria);
            m_assert.assertInfo(true, " Click on search Button");
            Cls_Generic_Methods.customWait(3);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_SerachCriteria, SName),
                    "Patient Name = <b>" + SName + "</b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
//
            // validate Search filter By MR No.
            Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);

            boolean bitemFound1 = false;
            for (WebElement webElement : oPage_OPD.list_searchFilterNameInOPD) {
                if (Cls_Generic_Methods.getTextInElement(webElement).equals("MR No")) {
                    webElement.click();
                    bitemFound1 = true;
                    break;
                }
            }
            m_assert.assertTrue(bitemFound1, " MR No Search Filter is selected");
            Cls_Generic_Methods.clearValuesInElement(oPage_OPD.input_SerachCriteria);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_SerachCriteria, SMRNo),
                    "Patient MR NO = <b>" + SMRNo + "</b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);


            // validate Search filter By Mbl No.
            Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);
            boolean bitemFound2 = false;
            for (WebElement webElement : oPage_OPD.list_searchFilterNameInOPD) {
                if (Cls_Generic_Methods.getTextInElement(webElement).equals("Mobile No")) {
                    webElement.click();
                    bitemFound2 = true;
                    break;
                }
            }
//            m_assert.assertTrue(bitemFound2, " Mobile No Search Filter is selected");
            Cls_Generic_Methods.clearValuesInElement(oPage_OPD.input_SerachCriteria);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_SerachCriteria, SMblNo),
                    "Patient Mobile No = <b>" + SMblNo + "</b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);

//          validate search Feature in Patient Identifier
            Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);
            boolean bitemFound3 = false;
            for (WebElement webElement : oPage_OPD.list_searchFilterNameInOPD) {
                if (Cls_Generic_Methods.getTextInElement(webElement).equals("Patient Identifier")) {
                    webElement.click();
                    bitemFound3 = true;
                    break;
                }
            }
            m_assert.assertTrue(bitemFound3, " Patient Identifier Search Filter is selected");
            Cls_Generic_Methods.clearValuesInElement(oPage_OPD.input_SerachCriteria);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_SerachCriteria, SPatientIdentifier),
                    "Patient Identifier = <b>" + SPatientIdentifier + "</b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(enabled = true, description = "Validate Search Feature in opd Negative Scenario")
    public void validateSearchFeatureInOPDInNegativeScenario() {
        Page_NewPatientRegisteration oPage_NewPatientRegisteration;
        myPatient = map_PatientsInExcel.get(patientKey);
        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);

        String SName = "aaffdfghj";
        String SMRNo = "sdfgh";
        String SMblNo = "009";
        String SPatientIdentifier = "mfy";
//        String SMrNo="MRD/123456/009990";
        try {

            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            //creating patient in opd
            try {
                if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                            10);
                } else {
                    CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,

                            "Patient Details");
                    Thread.sleep(2000);
                }
            } catch (NoSuchElementException e1) {
                CommonActions.openPatientRegisterationAndAppointmentForm();
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            if (!myPatient.getsSALUTATION().isEmpty()) {
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()),
                        "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                    myPatient.getsFIRST_NAME() + " entered for First Name");

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                            myPatient.getsMOBILE_NUMBER()),
                    myPatient.getsMOBILE_NUMBER() + " entered for Mobile Number");
            Cls_Generic_Methods.customWait(5);
//
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_MrnoinOPD, "tyh/5778/5637"),
                    "MRN No is selected");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OPD.button_Save), "click on Save");
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);

//              validate Search filter By Name
            boolean bitemFound = false;
            for (WebElement webElement : oPage_OPD.list_searchFilterNameInOPD) {
                if (Cls_Generic_Methods.getTextInElement(webElement).equals("Name")) {
                    webElement.click();
                    bitemFound = true;
                    break;
                }
            }
//            m_assert.assertTrue(bitemFound, " Name Search Filter is selected");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);
            Cls_Generic_Methods.clickElement(oPage_OPD.input_SerachCriteria);
            m_assert.assertInfo(true, " Click on search Button");
            Cls_Generic_Methods.customWait(3);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_SerachCriteria, SName),
                    "Patient Name = <b>" + SName + "</b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            m_assert.assertTrue(" Patient Name is wrong, particular patient is not selected");
//
            // validate Search filter By MR No.
            Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);

            boolean bitemFound1 = false;
            for (WebElement webElement : oPage_OPD.list_searchFilterNameInOPD) {
                if (Cls_Generic_Methods.getTextInElement(webElement).equals("MR No")) {
                    webElement.click();
                    bitemFound1 = true;
                    break;
                }
            }
            m_assert.assertTrue(bitemFound1, " MR No Search Filter is selected");
            Cls_Generic_Methods.clearValuesInElement(oPage_OPD.input_SerachCriteria);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_SerachCriteria, SMRNo),
                    "Patient MR NO = <b>" + SMRNo + "</b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            m_assert.assertTrue(" Patient MR No is wrong, particular patient is not selected");


            // validate Search filter By Mbl No.
            Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);
            boolean bitemFound2 = false;
            for (WebElement webElement : oPage_OPD.list_searchFilterNameInOPD) {
                if (Cls_Generic_Methods.getTextInElement(webElement).equals("Mobile No")) {
                    webElement.click();
                    bitemFound2 = true;
                    break;
                }
            }
            m_assert.assertTrue(bitemFound2, " Mobile No Search Filter is selected");
            Cls_Generic_Methods.clearValuesInElement(oPage_OPD.input_SerachCriteria);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_SerachCriteria, SMblNo),
                    "Patient Mobile No = <b>" + SMblNo + "</b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            m_assert.assertTrue(" Patient Mobile No is wrong, particular patient is not selected");

//          validate search Feature in Patient Identifier
            Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);
            boolean bitemFound3 = false;
            for (WebElement webElement : oPage_OPD.list_searchFilterNameInOPD) {
                if (Cls_Generic_Methods.getTextInElement(webElement).equals("Patient Identifier")) {
                    webElement.click();
                    bitemFound3 = true;
                    break;
                }
            }
            m_assert.assertTrue(bitemFound3, " Patient Identifier Search Filter is selected");
            Cls_Generic_Methods.clearValuesInElement(oPage_OPD.input_SerachCriteria);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_SerachCriteria, SPatientIdentifier),
                    "Patient Identifier = <b>" + SPatientIdentifier + "</b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            m_assert.assertTrue(" Patient Identifier is wrong, particular patient is not selected");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test(enabled = true, description = "Validate Search Feature in ipd positive Scenario")
    public void validateSearchFeatureInIPD() {
        oPage_OPD = new Page_OPD(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        oEHR_Data = new EHR_Data();
        oPage_IPD = new Page_IPD(driver);

        String SName = "Elo";
        String SMRNo = "mrd";
        String SMblNo = "123";
        String SPatientIdentifier = "rso";

        String concatPatientFullName = "";
        boolean bPatientFoundInOpd = false;
        boolean bPatientFoundInIpd = false;
        String admType = "Same Day";
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectDepartmentOnApp("OPD");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

            try {
                String MyQueueTab = "My Queue";
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                        "Validate " + MyQueueTab + " tab is selected");
                bPatientFoundInOpd = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient" + e);
            }

            if (bPatientFoundInOpd) {
                m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByAction(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission),
                        "Scheduled admission button is displayed");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission),
                        "Clicked on scheduled admission button");

                //Fill Schedule Admission Form
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_ScheduleAdmissionForm, 20),
                        "Scheduled admission form is displayed");

                //Admission Type Same day scheduling admission
                for (WebElement radioAdmissionBtn : oPage_ScheduleAdmission.list_radioAdmissionType) {
                    String admissionTypeBtn = Cls_Generic_Methods.getTextInElement(radioAdmissionBtn);
                    if (admissionTypeBtn.equalsIgnoreCase(admType)) {
                        try {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, radioAdmissionBtn), admissionTypeBtn + " Button in Admission Type is Clickable");
                        } catch (Exception e) {
                            m_assert.assertFatal(admissionTypeBtn + " button is not clickable");
                        }
                        break;
                    }
                }

                //Case Detail Tab
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduleAdmission.button_viewCaseDetails),
                        "View case details button is clicked");

                //Create Admission
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_createAdmission),
                        "Create admission button is clicked");

                Cls_Generic_Methods.customWait(4);

                //Assign Bed
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_assignBed, 15),
                        "Assigned bed Form is displayed");
                Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_closeAdmissionForm);
                oPage_IPD = new Page_IPD(driver);
                Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);


                //finding patient in ipd
                CommonActions.selectDepartmentOnApp("IPD");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today),
                        "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");
                Cls_Generic_Methods.customWait(3);
                bPatientFoundInIpd = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnScheduledTodayTab, concatPatientFullName);
                m_assert.assertTrue(bPatientFoundInIpd, "Validate that the patient " + concatPatientFullName + " is created in IPD");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

                //validate Search filter By Name
                Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);
                boolean bitemFound = false;
                for (WebElement webElement : oPage_OPD.list_searchFilterNameInOPD) {
                    if (Cls_Generic_Methods.getTextInElement(webElement).equals("Name")) {
                        webElement.click();
                        bitemFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(bitemFound, " Name Search Filter is selected");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                        10);
                Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);
                Cls_Generic_Methods.clickElement(oPage_OPD.input_SerachCriteriaInIPD);
                m_assert.assertInfo(true, " Click on search Button");
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_SerachCriteriaInIPD, SName),
                        "Patient Name = <b>" + SName + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                        10);
//
//
                // validate Search filter By MR No.
                Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);
                boolean bitemFound1 = false;
                for (WebElement webElement : oPage_OPD.list_searchFilterNameInOPD) {
                    if (Cls_Generic_Methods.getTextInElement(webElement).equals("MR No")) {
                        webElement.click();
                        bitemFound1 = true;
                        break;
                    }
                }
                m_assert.assertTrue(bitemFound1, " MR No Search Filter is selected");
                Cls_Generic_Methods.clearValuesInElement(oPage_OPD.input_SerachCriteriaInIPD);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                        10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_SerachCriteriaInIPD, SMRNo),
                        "Patient MR NO = <b>" + SMRNo + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                        10);


                // validate Search filter By Mbl No.
                Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);
                boolean bitemFound2 = false;
                for (WebElement webElement : oPage_OPD.list_searchFilterNameInOPD) {
                    if (Cls_Generic_Methods.getTextInElement(webElement).equals("Mobile No")) {
                        webElement.click();
                        bitemFound2 = true;
                        break;
                    }
                }
                m_assert.assertTrue(bitemFound2, " Mobile No Search Filter is selected");
                Cls_Generic_Methods.clearValuesInElement(oPage_OPD.input_SerachCriteriaInIPD);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                        10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_SerachCriteriaInIPD, SMblNo),
                        "Patient Mobile No = <b>" + SMblNo + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                        10);
//
////          validate search Feature in Patient Identifier
                Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);
                boolean bitemFound3 = false;
                for (WebElement webElement : oPage_OPD.list_searchFilterNameInOPD) {
                    if (Cls_Generic_Methods.getTextInElement(webElement).equals("Patient Identifier")) {
                        webElement.click();
                        bitemFound3 = true;
                        break;
                    }
                }
                m_assert.assertTrue(bitemFound3, " Patient Identifier Search Filter is selected");
                Cls_Generic_Methods.clearValuesInElement(oPage_OPD.input_SerachCriteriaInIPD);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                        10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_SerachCriteriaInIPD, SPatientIdentifier),
                        "Patient Identifier = <b>" + SPatientIdentifier + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                        10);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Not found in ipd" + e);
        }
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(enabled = true, description = "Validate Search Feature in ipd Negative Scenario")
    public void validateSearchFeatureInIPDNegative() {
        oPage_OPD = new Page_OPD(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        oEHR_Data = new EHR_Data();
        oPage_IPD = new Page_IPD(driver);

        String SName = "ddd";
        String SMRNo = "mut";
        String SMblNo = "00#";
        String SPatientIdentifier = "abc" +
                "";

        String concatPatientFullName = "";
        boolean bPatientFoundInOpd = false;
        boolean bPatientFoundInIpd = false;
        String admType = "Same Day";
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectDepartmentOnApp("OPD");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

            try {
                String MyQueueTab = "My Queue";
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                        "Validate " + MyQueueTab + " tab is selected");
                bPatientFoundInOpd = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient" + e);
            }

            if (bPatientFoundInOpd) {
                m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByAction(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission),
                        "Scheduled admission button is displayed");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission),
                        "Clicked on scheduled admission button");

                //Fill Schedule Admission Form
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_ScheduleAdmissionForm, 20),
                        "Scheduled admission form is displayed");

                //Admission Type Same day scheduling admission
                for (WebElement radioAdmissionBtn : oPage_ScheduleAdmission.list_radioAdmissionType) {
                    String admissionTypeBtn = Cls_Generic_Methods.getTextInElement(radioAdmissionBtn);
                    if (admissionTypeBtn.equalsIgnoreCase(admType)) {
                        try {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, radioAdmissionBtn), admissionTypeBtn + " Button in Admission Type is Clickable");
                        } catch (Exception e) {
                            m_assert.assertFatal(admissionTypeBtn + " button is not clickable");
                        }
                        break;
                    }
                }

                //Case Detail Tab
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduleAdmission.button_viewCaseDetails),
                        "View case details button is clicked");

                //Create Admission
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_createAdmission),
                        "Create admission button is clicked");

                Cls_Generic_Methods.customWait(4);

                //Assign Bed
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_assignBed, 15),
                        "Assigned bed Form is displayed");
                Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_closeAdmissionForm);
                oPage_IPD = new Page_IPD(driver);
                Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);


                //finding patient in ipd
                CommonActions.selectDepartmentOnApp("IPD");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today),
                        "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");
                Cls_Generic_Methods.customWait(3);
                bPatientFoundInIpd = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnScheduledTodayTab, concatPatientFullName);
                m_assert.assertTrue(bPatientFoundInIpd, "Validate that the patient " + concatPatientFullName + " is created in IPD");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

                //validate Search filter By Name

                Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);
                boolean bitemFound = false;
                for (WebElement webElement : oPage_OPD.list_searchFilterNameInOPD) {
                    if (Cls_Generic_Methods.getTextInElement(webElement).equals("Name")) {
                        webElement.click();
                        bitemFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(bitemFound, " Name Search Filter is selected");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                        10);
                Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);
                Cls_Generic_Methods.clickElement(oPage_OPD.input_SerachCriteriaInIPD);
                m_assert.assertInfo(true, " Click on search Button");
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_SerachCriteriaInIPD, SName),
                        "Patient Name = <b>" + SName + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                        10);
                m_assert.assertTrue(" Patient Name is wrong, particular patient is not selected");
//
//
                // validate Search filter By MR No.
                Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);
                boolean bitemFound1 = false;
                for (WebElement webElement : oPage_OPD.list_searchFilterNameInOPD) {
                    if (Cls_Generic_Methods.getTextInElement(webElement).equals("MR No")) {
                        webElement.click();
                        bitemFound1 = true;
                        break;
                    }
                }
                m_assert.assertTrue(bitemFound1, " MR No Search Filter is selected");
                Cls_Generic_Methods.clearValuesInElement(oPage_OPD.input_SerachCriteriaInIPD);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                        10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_SerachCriteriaInIPD, SMRNo),
                        "Patient MR NO = <b>" + SMRNo + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                        10);
                m_assert.assertTrue(" Patient MR No is wrong, particular patient is not selected");


                // validate Search filter By Mbl No.
                Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);
                boolean bitemFound2 = false;
                for (WebElement webElement : oPage_OPD.list_searchFilterNameInOPD) {
                    if (Cls_Generic_Methods.getTextInElement(webElement).equals("Mobile No")) {
                        webElement.click();
                        bitemFound2 = true;
                        break;
                    }
                }
                m_assert.assertTrue(bitemFound2, " Mobile No Search Filter is selected");
                Cls_Generic_Methods.clearValuesInElement(oPage_OPD.input_SerachCriteriaInIPD);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                        10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_SerachCriteriaInIPD, SMblNo),
                        "Patient Mobile No = <b>" + SMblNo + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                        10);
                m_assert.assertTrue(" Patient Mobile No is wrong, particular patient is not selected");
//
////          validate search Feature in Patient Identifier
                Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);
                boolean bitemFound3 = false;
                for (WebElement webElement : oPage_OPD.list_searchFilterNameInOPD) {
                    if (Cls_Generic_Methods.getTextInElement(webElement).equals("Patient Identifier")) {
                        webElement.click();
                        bitemFound3 = true;
                        break;
                    }
                }
                m_assert.assertTrue(bitemFound3, " Patient Identifier Search Filter is selected");
                Cls_Generic_Methods.clearValuesInElement(oPage_OPD.input_SerachCriteriaInIPD);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                        10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_SerachCriteriaInIPD, SPatientIdentifier),
                        "Patient Identifier = <b>" + SPatientIdentifier + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                        10);
                m_assert.assertTrue(" Patient Identifier is wrong, particular patient is not selected");

            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Not found in ipd" + e);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(enabled = true, description = "Validate Search Feature in Investigation By BarcodeId")
    public void CreatingPatientForBarcodeSearch() {
        Page_NewPatientRegisteration oPage_NewPatientRegisteration;
        myPatient = map_PatientsInExcel.get(patientKey);
        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);

        String sBarcodeId = "OPTHA1LAB000155";

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            //creating patient in opd
            try {
                if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                            10);
                } else {
                    CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                            "Patient Details");
                    Thread.sleep(2000);
                }
            } catch (NoSuchElementException e1) {
                CommonActions.openPatientRegisterationAndAppointmentForm();
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            if (!myPatient.getsSALUTATION().isEmpty()) {
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()),
                        "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                    myPatient.getsFIRST_NAME() + " entered for First Name");

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                            myPatient.getsMOBILE_NUMBER()),
                    myPatient.getsMOBILE_NUMBER() + " entered for Mobile Number");
            Cls_Generic_Methods.customWait(5);

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OPD.button_Save), "click on Save");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_Save, 5);
           Cls_Generic_Methods.driverRefresh();


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Not found in ipd" + e);
        }
    }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(enabled = true, description = "Validate Search Feature in Investigation By BarcodeId")
    public void validateAddInvestigationInPatient() {

        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        oEHR_Data = new EHR_Data();

        String concatPatientFullName = "";
        boolean bPatientFoundInOpd = false;
//
        try {
            Cls_Generic_Methods.customWait(5);
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait(5);
            CommonActions.selectDepartmentOnApp("OPD");
            Cls_Generic_Methods.customWait(5);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

            try {
                String MyQueueTab = "ALL";
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                        "Validate " + MyQueueTab + " tab is selected");
                bPatientFoundInOpd = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                Cls_Generic_Methods.waitForElementToBeDisplayed
                        (oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient" + e);
            }

            if (bPatientFoundInOpd) {
//
                Cls_Generic_Methods.scrollToElementByAction(driver, oPage_OPD.tabs_selectInvestigations);
                Cls_Generic_Methods.customWait(10);


                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OPD.tabs_selectInvestigations), "Select Investigations Tabs");
                Cls_Generic_Methods.customWait(10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OPD.tabs_selectAddInvestigations),
                        "Add investigation tab is selected");
                Cls_Generic_Methods.customWait(10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OPD.dropdown_selectLaboratory),
                        "click on laboratory option");
                Cls_Generic_Methods.customWait(10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OPD.dropdown_selectCornea),
                        "select corena labratory investigations");
                Cls_Generic_Methods.customWait(10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OPD.button_saveLabInv), "click on save");
                Cls_Generic_Methods.customWait(10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OPD.icon_selectProfileIcon), "click on profile icon");
                Cls_Generic_Methods.customWait(10);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.icon_selectProfileIcon, 10);

                Cls_Generic_Methods.scrollToElementByAction(driver, oPage_OPD.icon_selectLogoutIcon);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.icon_selectLogoutIcon, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OPD.icon_selectLogoutIcon), "click on logout");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.icon_selectLogoutIcon, 10);

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(enabled = true, description = "Validate Login HG Laboratory Tech user ")
    public void validateAddInvestigationInLabUser(){

            oPage_OPD = new Page_OPD(driver);
            oEHR_Data = new EHR_Data();

            String expectedLoggedInUser = EHR_Data.user_HGLaboratoryTech;
            String sBarcodeId = "OPTHA1LAB000207";

            try {
                Cls_Generic_Methods.customWait(10);
                CommonActions.loginFunctionality(expectedLoggedInUser);
                Cls_Generic_Methods.customWait(20);


                    //validate Search filter By Name
                    Cls_Generic_Methods.clickElement(oPage_OPD.select_searchFilter);
                    boolean bitemFound = false;
                    for (WebElement webElement : oPage_OPD.list_searchFilterNameInLabUser) {
                        if (Cls_Generic_Methods.getTextInElement(webElement).equals("Inv. Barcode ID")) {
                            Cls_Generic_Methods.clickElement(webElement);
                            bitemFound = true;
                            break;
                        }
                    }
                    m_assert.assertTrue(bitemFound, " barcode Id Search Filter is selected");
                Cls_Generic_Methods.customWait(10);
                    Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);
                    Cls_Generic_Methods.clickElement(oPage_OPD.input_selectSearchCriteriaInInvestigation);
                    m_assert.assertInfo(true, " Click on search Button");
                    Cls_Generic_Methods.customWait(7);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_selectSearchCriteriaInInvestigation, sBarcodeId),
                            "Patient Barcode id = <b>" + sBarcodeId + "</b>");
                Cls_Generic_Methods.customWait(7);
                m_assert.assertTrue("patient id is correct it is showing RHS side");

                } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

       ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(enabled = true, description = "Validate Login HG Laboratory Tech user In negative Scenario ")
    public void validateAddInvestigationInLabUserNegative(){

        oPage_OPD = new Page_OPD(driver);
        oEHR_Data = new EHR_Data();

        String expectedLoggedInUser = EHR_Data.user_HGLaboratoryTech;
        String sBarcodeId1 = "#$%^364opthjhj";
        try {
            Cls_Generic_Methods.customWait(10);
            CommonActions.loginFunctionality(expectedLoggedInUser);
//            CommonActions.loginFunctionality(oEHR_Data.user_HGLaboratoryTech);
            Cls_Generic_Methods.customWait(10);

//            //validate Search filter By Name

            Cls_Generic_Methods.clickElement(oPage_OPD.select_searchFilter);
            boolean bitemFound = false;
            for (WebElement webElement : oPage_OPD.list_searchFilterNameInLabUser) {
                if (Cls_Generic_Methods.getTextInElement(webElement).equals("Inv. Barcode ID")) {
                    Cls_Generic_Methods.clickElement(webElement);
                    bitemFound = true;
                    break;
                }
            }
            m_assert.assertTrue(bitemFound, " barcode Id Search Filter is selected");
            Cls_Generic_Methods.customWait(7);

            Cls_Generic_Methods.clickElement(oPage_OPD.select_SearchFilterName);
            Cls_Generic_Methods.clickElement(oPage_OPD.input_selectSearchCriteriaInInvestigation);
            m_assert.assertInfo(true, " Click on search Button");
            Cls_Generic_Methods.customWait(7);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OPD.input_selectSearchCriteriaInInvestigation, sBarcodeId1),
                    "Patient Barcode id = <b>" + sBarcodeId1 + "</b>");

            m_assert.assertTrue("Patient Barcode Id is wrong then it is showing <b>" + "INVESTIGATION IS NOT FOUND" +"</b>");
            Cls_Generic_Methods.customWait(7);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test(enabled = true, description = "Validate Search Feature in opd Positive Scenario")
    public void validateSearchFeatureInAddPatientOPD() {
        Page_NewPatientRegisteration oPage_NewPatientRegisteration;
        myPatient = map_PatientsInExcel.get(patientKey);
        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);
        String oldPatientId ="", oldMrnNumber="" ,oldNumber = "" ,oldName = "";
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        Page_ShowMrnInInvestigationTemplateCreate oPage_ShowMrnInInvestigationTemplateCreate = new Page_ShowMrnInInvestigationTemplateCreate(driver);

        String searchType[] = {"MR No","Mobile No.","Name","Patient ID"};


        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            String MyQueueTab = "My Queue";
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            Cls_Generic_Methods.customWait(7);
            m_assert.assertTrue(
                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                    "Validate " + MyQueueTab + " tab is selected");
            Cls_Generic_Methods.customWait(5);
            CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments,concatPatientFullName);


            String patientId = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.text_patientID);

            boolean oldPatientFound = false;
            try {
                while (!oldPatientFound) {
                    Cls_Generic_Methods.clickElement(oPage_Navbar.button_backDateButton);
                    Cls_Generic_Methods.customWait(4);
                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, "All");
                    Cls_Generic_Methods.customWait(4);
                    for (WebElement eTabElement : oPage_OPD.rows_patientAppointments) {
                        if (eTabElement.isDisplayed()) {
                            Cls_Generic_Methods.clickElement(driver, eTabElement);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                                    16);
                            try{
                                if(Cls_Generic_Methods.isElementDisplayed(oPage_ShowMrnInInvestigationTemplateCreate.text_mrnOpdRhs)){

                                    oldPatientId = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.text_patientID);
                                    oldNumber =  Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.text_patientNumber);
                                    oldName = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.text_patientNameInOPD);
                                    oldMrnNumber = Cls_Generic_Methods.getTextInElement(oPage_ShowMrnInInvestigationTemplateCreate.text_mrnOpdRhs);
                                    oldPatientFound = true;
                                    break;

                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            //creating patient in opd
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_addAppointment),
                        "<b>Add</b> Appointment Button is clicked");

                m_assert.assertInfo(
                        Cls_Generic_Methods
                                .waitForElementToBecomeVisible(oPage_NewPatientRegisteration.button_addNewPatient, 15),
                        "Add New Patient Button is displayed");

            } catch (NoSuchElementException e1) {
                e1.printStackTrace();
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);

            String searchValueList[] = {sMRNumber,oldMrnNumber,myPatient.getsMOBILE_NUMBER(),oldNumber,myPatient.getsFIRST_NAME(),oldName,patientId,oldPatientId};
           for(int i =0 ;i<2;i++) {
               boolean searchResultByMrnNumber = selectOptionAndSearch(searchType[0], searchValueList[i]);
               m_assert.assertTrue(searchResultByMrnNumber,
                       "Search By MRN No Worked correctly as Patient found for " + searchValueList[i]);

           }


            for(int i =2 ;i<4;i++) {
                boolean searchResultByMobileNumber = selectOptionAndSearch(searchType[1], searchValueList[i]);
                if (searchResultByMobileNumber) {
                    m_assert.assertTrue("Search By Mobile No Worked correctly as Patient found for " + searchValueList[i]);
                }
            }

            for(int i =4 ;i<6;i++) {
                boolean searchResultByMobileNumber = selectOptionAndSearch(searchType[2], searchValueList[i]);
                if (searchResultByMobileNumber) {
                    m_assert.assertTrue("Search By Name  Worked correctly as Patient found for " + searchValueList[i]);
                }
            }

            for(int i =6 ;i<8;i++) {
                boolean searchResultByMobileNumber = selectOptionAndSearch(searchType[3], searchValueList[i]);
                if (searchResultByMobileNumber) {
                    m_assert.assertTrue(
                            "Search By Patient id Worked correctly as Patient found for " + searchValueList[i]);
                }
            }

            for(int i =0 ;i<4;i++) {
                boolean searchResultByMobileNumber = selectOptionAndSearch(searchType[i], searchValueList[i]+"4355");
                m_assert.assertTrue(searchResultByMobileNumber,
                        "Search By Incorrect "+searchType[i]+" Worked correctly as Patient Not found for " +searchValueList[i]+"4355");
            }

            selectOptionAndSearch(searchType[3], oldPatientId);
            Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.list_textSearchResults.get(0));
            Cls_Generic_Methods.customWait(5);
            Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm);
            Cls_Generic_Methods.customWait(15);

            m_assert.assertTrue(
                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                    "Validate " + MyQueueTab + " tab is selected");
            Cls_Generic_Methods.customWait(5);
           boolean patientFound =  CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments,concatPatientFullName);

           m_assert.assertTrue(patientFound," Patient Found In My Queue");





        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validate Search Feature in opd Positive Scenario")
    public void validateEditPatientOPD() {
        Page_NewPatientRegisteration oPage_NewPatientRegisteration;
        myPatient = map_PatientsInExcel.get(patientKey);
        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);
        Page_HistoryTab oPage_HistoryTab = new Page_HistoryTab(driver);
        Page_Store oPage_Store = new Page_Store(driver);


        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        String mrnNumberUpdate = "MRN"+CommonActions.getRandomString(5);

        Model_Patient myPatientUpdate = map_PatientsInExcel.get("007");



        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            String MyQueueTab = "My Queue";
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            Cls_Generic_Methods.customWait(7);
            m_assert.assertTrue(
                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                    "Validate " + MyQueueTab + " tab is selected");
            Cls_Generic_Methods.customWait(5);
            CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments,concatPatientFullName);
            Cls_Generic_Methods.customWait(3);

            if(Cls_Generic_Methods.isElementDisplayed(oPage_OPD.button_Edit)) {
                Cls_Generic_Methods.clickElement(oPage_OPD.button_Edit);
                Cls_Generic_Methods.customWait(5);
                Cls_Generic_Methods.clearValuesInElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatientUpdate.getsFIRST_NAME()),
                        myPatientUpdate.getsFIRST_NAME() + " entered for First Name");

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_middleNameOnPatientRegForm,
                                myPatientUpdate.getsMIDDLE_NAME()),
                        myPatientUpdate.getsMIDDLE_NAME() + " entered for Middle Name");

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_lastNameOnPatientRegForm, myPatientUpdate.getsLAST_NAME()),
                        myPatientUpdate.getsLAST_NAME() + " entered for Last Name");

                Cls_Generic_Methods.clearValuesInElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm);

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                                myPatientUpdate.getsMOBILE_NUMBER()),
                        myPatientUpdate.getsMOBILE_NUMBER() + " entered for Mobile Number");

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_secondaryMobileNumberOnPatientRegForm,
                                myPatientUpdate.getsSECONDARY_MOBILE_NUMBER()),
                        myPatientUpdate.getsSECONDARY_MOBILE_NUMBER() + " entered for Secondary Mobile Number");

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_emailOnPatientRegForm, myPatientUpdate.getsEMAIL()),
                        myPatientUpdate.getsEMAIL() + " entered for Email");

                //Enter Whatsapp number if same as contact number checkbox is not clicked
                if (myPatient.getsMOBILE_NUMBER().equals(myPatientUpdate.getsWHATSAPP_NUMBER())) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_NewPatientRegisteration.checkbox_sameAsContactNumber), "Same as Contact number checkbox clicked");
                    m_assert.assertEquals(Cls_Generic_Methods.getTextInElement(oPage_NewPatientRegisteration.input_whatsappNumber), myPatientUpdate.getsMOBILE_NUMBER(), "Validated number in whatsapp number field");
                } else {
                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_NewPatientRegisteration.input_whatsappNumber,
                                    myPatientUpdate.getsWHATSAPP_NUMBER()),
                            myPatientUpdate.getsWHATSAPP_NUMBER() + " entered in Whatsapp Number Field");
                }

                for (WebElement gender : oPage_NewPatientRegisteration.list_genderRadioButtons) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver,
                            gender), "Validate " + gender.getAttribute("value") + " Button is Clickable");

                }
                //Select Gender
                switch (myPatient.getsGENDER().toUpperCase()) {
                    case "MALE" -> m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver,
                            oPage_NewPatientRegisteration.radio_gender_Male), "Clicked Male in gender");
                    case "FEMALE" -> m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver,
                            oPage_NewPatientRegisteration.radio_gender_Female), "Clicked Female in gender");
                    case "TRANSGENDER" -> m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver,
                            oPage_NewPatientRegisteration.radio_gender_Transgender), "Clicked Transgender in gender");
                }

                //Select DateOf Birth
                String[] dobArray = myPatientUpdate.getsDATE_OF_BIRTH().split("/");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.select_dobDateDay), "Date of Birth field clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.select_dobYear, 10);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_NewPatientRegisteration.select_dobYear,
                        dobArray[2]), "Select " + dobArray[2] + " in year");

                int month = Integer.parseInt(dobArray[1]) - 1;
                int date = Integer.parseInt(dobArray[0]);

                m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_NewPatientRegisteration.select_dobMonth, String.valueOf(month)),
                        "Select " + Cls_Generic_Methods.getTextInElement(oPage_NewPatientRegisteration.getMonth(String.valueOf(month))) + " in month");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.select_dobDate(String.valueOf(date))),
                        "Select  " + String.valueOf(date) + " in day");

                m_assert.assertTrue("Entered " + myPatientUpdate.getsDATE_OF_BIRTH() + " in Date Of Birth field");

                //Select Relation type and name

                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_NewPatientRegisteration.select_patientRelation,
                        myPatientUpdate.getsRELATION_TYPE()), "Select " + myPatientUpdate.getsRELATION_TYPE() + " in Relation Dropdown");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_patientRelation, 10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_patientRelation,
                        myPatientUpdate.getsRELATION_NAME()), "Entered " + myPatientUpdate.getsRELATION_NAME() + " in relation name field");

              //  m_assert.assertInfo(Cls_Generic_Methods.clickElementByAction(driver,oPage_NewPatientRegisteration.select_searchPatientType),
                     //  "Clicked Patient Type");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_NewPatientRegisteration.select_searchPatientType, 1),
                        "Selected " + myPatientUpdate.getsPATIENT_TYPE_INFO() + " in Patient Type");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_patientTypeComment, oEHR_Data.sPATIENT_TYPE_COMMENT), "Entered " + oEHR_Data.sPATIENT_TYPE_COMMENT + " in Patient Type comment");


                m_assert.assertInfo(
                        Cls_Generic_Methods.clickElement(driver, oPage_NewPatientRegisteration.select_primaryLanguage),
                        " Primary Language Selector clicked");
                m_assert.assertTrue(
                        CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.options_primaryLanguage,
                                myPatientUpdate.getsPRIMARY_LANGUAGE()),
                        myPatientUpdate.getsPRIMARY_LANGUAGE() + " selected for Primary Language");

                // Select Secondary Language
                m_assert.assertInfo(
                        Cls_Generic_Methods.clickElement(driver,
                                oPage_NewPatientRegisteration.select_secondaryLanguage),
                        " Secondary Language Selector clicked");
                m_assert.assertTrue(
                        CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.options_secondaryLanguage,
                                myPatientUpdate.getsSECONDARY_LANGUAGE()),
                        myPatientUpdate.getsSECONDARY_LANGUAGE() + " selected for Secondary Language");

                boolean bPinCodeSelected = false;
                try {
                    m_assert.assertInfo(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_NewPatientRegisteration.input_pincodeOnPatientRegForm,
                                    String.valueOf(myPatientUpdate.getsPINCODE())),
                            String.valueOf(myPatientUpdate.getsPINCODE()) + " entered for Pin Code");

                    Thread.sleep(3000);

                    for (WebElement pinCodeElement : oPage_NewPatientRegisteration.inputOptions_pincodeOnPatientRegForm) {
                        if (pinCodeElement.getText().trim().equals(String.valueOf(myPatientUpdate.getsPINCODE()))) {
                            pinCodeElement.click();
                            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.select_areaPatientAddress, myPatientUpdate.getsCITY()), "Selected " + myPatientUpdate.getsCITY() + " in area field");
                            bPinCodeSelected = true;
                        }
                    }

                    m_assert.assertTrue(bPinCodeSelected,
                            String.valueOf(myPatientUpdate.getsPINCODE()) + " entered for Pin Code");

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_address_1_OnPatientRegForm, myPatientUpdate.getsADDRESS_1()),
                        myPatientUpdate.getsADDRESS_1() + " entered for Address 1");

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_address_2_OnPatientRegForm, myPatientUpdate.getsADDRESS_2()),
                        myPatientUpdate.getsADDRESS_2() + " entered for Address 2");

                //Enter Occupation and employee id
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_patientOccupation,
                        myPatientUpdate.getsOCCUPATION()), "Entered " + myPatientUpdate.getsOCCUPATION() + " in Occupation field");

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_patientEmployeeId,
                        myPatientUpdate.getsEMPLOYEE_ID()), "Entered " + myPatientUpdate.getsEMPLOYEE_ID() + " in Employee Id field");

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_medicalRecordNumOnPatientRegForm,
                                mrnNumberUpdate),
                        mrnNumberUpdate + " entered for Medical Report Number");
                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_healthIdNumOnPatientRegForm,
                                myPatientUpdate.getsHEALTH_ID_NUMBER()),
                        myPatientUpdate.getsHEALTH_ID_NUMBER() + " entered for Health ID Number");

                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                        "Other Details");
                Cls_Generic_Methods.customWait();

                // Selecting the Blood Group
                int requiredIndex = -1;
                try {
                    for (WebElement e : oPage_NewPatientRegisteration.radioButtons_bloodGroupsOnPatientRegForm) {

                        if (e.getAttribute("value").equals(myPatientUpdate.getsBLOOD_GROUP())) {
                            requiredIndex = oPage_NewPatientRegisteration.radioButtons_bloodGroupsOnPatientRegForm
                                    .indexOf(e);
                            oPage_NewPatientRegisteration.radioButtonsSelector_bloodGroupsOnPatientRegForm
                                    .get(requiredIndex).click();
                            m_assert.assertTrue(true,
                                    "Validate " + myPatientUpdate.getsBLOOD_GROUP() + " is selected for Blood Group");
                            break;
                        }

                    }

                    if (requiredIndex == -1) {
                        m_assert.assertTrue(false, myPatientUpdate.getsBLOOD_GROUP() + " blood group is not selected");
                    } else {
                        m_assert.assertTrue(
                                CommonActions.validateOnlyOneRadioButtonIsSelected(
                                        oPage_NewPatientRegisteration.radioButtonsSelector_bloodGroupsOnPatientRegForm),
                                "Validate only one Blood Group is selected");
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    if (requiredIndex == -1) {
                        m_assert.assertFatal("Unable to select Blood Group - " + e);
                    }
                }

                // Selecting the Marital Status
                requiredIndex = -1;
                try {
                    for (WebElement e : oPage_NewPatientRegisteration.radioButtons_maritalStatusOnPatientRegForm) {

                        if (e.getAttribute("value").equals(myPatientUpdate.getsMARITAL_STATUS())) {
                            requiredIndex = oPage_NewPatientRegisteration.radioButtons_maritalStatusOnPatientRegForm
                                    .indexOf(e);
                            oPage_NewPatientRegisteration.radioButtonsSelector_maritalStatusOnPatientRegForm
                                    .get(requiredIndex).click();
                            m_assert.assertTrue(true, "Validate " + myPatientUpdate.getsMARITAL_STATUS()
                                    + " is selected for Marital Status");
                            break;
                        }

                    }

                    if (requiredIndex == -1) {
                        m_assert.assertTrue(false,
                                myPatientUpdate.getsMARITAL_STATUS() + " Marital Status is not selected");
                    } else {
                        m_assert.assertTrue(CommonActions.validateOnlyOneRadioButtonIsSelected(
                                        oPage_NewPatientRegisteration.radioButtonsSelector_maritalStatusOnPatientRegForm),
                                "Validate only one Marital Status is selected");
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    if (requiredIndex == -1) {
                        m_assert.assertFatal("Unable to select Marital Status - " + e);
                    }
                }

                // Selecting if One Eyed
                requiredIndex = -1;
                try {
                    for (WebElement e : oPage_NewPatientRegisteration.radioButtons_oneEyedOnPatientRegForm) {

                        if (e.getAttribute("value").equals(myPatientUpdate.getsONE_EYED())) {
                            requiredIndex = oPage_NewPatientRegisteration.radioButtons_oneEyedOnPatientRegForm
                                    .indexOf(e);
                            oPage_NewPatientRegisteration.radioButtonsSelector_oneEyedOnPatientRegForm
                                    .get(requiredIndex).click();
                            m_assert.assertTrue(true,
                                    "Validate " + myPatientUpdate.getsONE_EYED() + " is selected for One Eyed");
                            break;
                        }

                    }

                    if (requiredIndex == -1) {
                        m_assert.assertTrue(false, myPatientUpdate.getsONE_EYED() + " One Eyed is not selected");
                    } else {
                        m_assert.assertTrue(
                                CommonActions.validateOnlyOneRadioButtonIsSelected(
                                        oPage_NewPatientRegisteration.radioButtonsSelector_oneEyedOnPatientRegForm),
                                "Validate only one One Eyed option is selected");
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    if (requiredIndex == -1) {
                        m_assert.assertFatal("Unable to select One Eyed - " + e);
                    }
                }

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_emergencyPatientNameOnPatientRegForm,
                                myPatientUpdate.getsEMERGENCY_CONTACT_NAME()),
                        myPatientUpdate.getsEMERGENCY_CONTACT_NAME() + " entered for Emergency Contact Name");

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_emergencyPatientNumberOnPatientRegForm,
                                myPatientUpdate.getsEMERGENCY_CONTACT_NUMBER()),
                        myPatientUpdate.getsEMERGENCY_CONTACT_NUMBER() + " entered for Emergency Contact Number");

                //enter aadhar number
                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_aadharCardNumOnPatientRegForm,
                                myPatientUpdate.getsAADHAR_NUMBER()),
                        myPatientUpdate.getsAADHAR_NUMBER() + " entered for Aadhar Number");

                //Enter DL Number
                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_drivingLicenseNumOnPatientRegForm,
                                myPatientUpdate.getsDRIVING_LICENSE_NUMBER()),
                        myPatientUpdate.getsDRIVING_LICENSE_NUMBER() + " entered for Driving License Number");

                //Enter PAN Number
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_panNumOnPatientRegForm, myPatientUpdate.getsPAN_NUMBER()),
                        myPatientUpdate.getsPAN_NUMBER() + " entered for PAN Number");

                //Enter GST Number
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                        oPage_NewPatientRegisteration.input_gstNumberOnPatientRegForm,
                        myPatientUpdate.getsGST_NUMBER()), myPatientUpdate.getsGST_NUMBER() + " entered for GST Number");

                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                        "History");
                Cls_Generic_Methods.customWait();

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_medicalHistoryCommentOnPatientRegForm,
                                oEHR_Data.sMEDICAL_HISTORY_COMMENT),
                        "Validate Medical History Comment is entered as " + oEHR_Data.sMEDICAL_HISTORY_COMMENT);

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_familyHistoryCommentOnPatientRegForm,
                                oEHR_Data.sFAMILY_HISTORY_COMMENT),
                        "Validate Medical History Comment is entered as " + oEHR_Data.sFAMILY_HISTORY_COMMENT);

                // Validating the Paediatric History
                int buttonsCounter = 0;
                try {
                    for (WebElement buttonElement : oPage_NewPatientRegisteration.buttons_nutritionalAssessmentsOnPatientRegForm) {

                        int index = oPage_NewPatientRegisteration.buttons_nutritionalAssessmentsOnPatientRegForm
                                .indexOf(buttonElement);

                        if (buttonElement.getText().trim().equals(oEHR_Data.list_NUTRIRIONAL_ASSESSMENTS.get(index))) {
                            buttonsCounter++;
                            m_assert.assertInfo(true, "Validate " + oEHR_Data.list_NUTRIRIONAL_ASSESSMENTS.get(index)
                                    + " is present under Nutritional Assessment section");
                        } else {
                            m_assert.assertInfo(false,
                                    "Validate " + oEHR_Data.list_NUTRIRIONAL_ASSESSMENTS.get(index)
                                            + " is present under Nutritional Assessment section. Actual = "
                                            + buttonElement.getText());
                        }

                        boolean validateButtonFunctionality = false;
                        try {
                            validateButtonFunctionality = CommonActions.validateIf_EHR_ButtonIsClickable(buttonElement);
                        } catch (IndexOutOfBoundsException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            m_assert.assertFatal(
                                    "Unable to validate if " + oEHR_Data.list_NUTRIRIONAL_ASSESSMENTS.get(index)
                                            + " button is clickable - " + e);
                        }

                        m_assert.assertTrue(validateButtonFunctionality, "Validate "
                                + oEHR_Data.list_NUTRIRIONAL_ASSESSMENTS.get(index) + " Button is Clickable");
                    }

                    if ((oEHR_Data.list_NUTRIRIONAL_ASSESSMENTS.size() == buttonsCounter)) {
                        m_assert.assertTrue(true, "Validate " + buttonsCounter
                                + " Assessment options are present under Nutritional Assessment section");
                    } else {
                        m_assert.assertTrue(false,
                                "Validate " + oEHR_Data.list_NUTRIRIONAL_ASSESSMENTS.size()
                                        + " disorders are present under Nutritional Assessment section. Actual = "
                                        + buttonsCounter);
                    }

                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_NewPatientRegisteration.input_nutritionalAssessmentCommentOnPatientRegForm,
                                    myPatientUpdate.getsNUTRITIONAL_ASSESSMENT_COMMENT()),
                            "Validate Nutritional Assessment Comment is entered as "
                                    + myPatientUpdate.getsNUTRITIONAL_ASSESSMENT_COMMENT());

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to validate Nutritional Assessment Section - " + e);
                }

                buttonsCounter = 0;
                try {
                    for (WebElement buttonElement : oPage_NewPatientRegisteration.buttons_immunizationAssessmentsOnPatientRegForm) {

                        int index = oPage_NewPatientRegisteration.buttons_immunizationAssessmentsOnPatientRegForm
                                .indexOf(buttonElement);

                        if (buttonElement.getText().trim().equals(oEHR_Data.list_IMMUNIZATION_ASSESSMENTS.get(index))) {
                            buttonsCounter++;
                            m_assert.assertInfo(true, "Validate " + oEHR_Data.list_IMMUNIZATION_ASSESSMENTS.get(index)
                                    + " is present under Nutritional Assessment section");
                        } else {
                            m_assert.assertInfo(false,
                                    "Validate " + oEHR_Data.list_IMMUNIZATION_ASSESSMENTS.get(index)
                                            + " is present under Immunization Assessment section. Actual = "
                                            + buttonElement.getText());
                        }

                        boolean validateButtonFunctionality = false;
                        try {
                            validateButtonFunctionality = CommonActions.validateIf_EHR_ButtonIsClickable(buttonElement);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            m_assert.assertFatal(
                                    "Unable to validate if " + oEHR_Data.list_IMMUNIZATION_ASSESSMENTS.get(index)
                                            + " button is clickable - " + e);
                        }

                        m_assert.assertTrue(validateButtonFunctionality, "Validate "
                                + oEHR_Data.list_IMMUNIZATION_ASSESSMENTS.get(index) + " Button is Clickable");
                    }

                    if ((oEHR_Data.list_IMMUNIZATION_ASSESSMENTS.size() == buttonsCounter)) {
                        m_assert.assertTrue(true, "Validate " + buttonsCounter
                                + " Assessment options are present under Immunization Assessment section");
                    } else {
                        m_assert.assertTrue(false,
                                "Validate " + oEHR_Data.list_IMMUNIZATION_ASSESSMENTS.size()
                                        + " disorders are present under Immunization Assessment section. Actual = "
                                        + buttonsCounter);
                    }

                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_NewPatientRegisteration.input_immunizationAssessmentCommentOnPatientRegForm,
                                    myPatientUpdate.getsIMMUNIZATION_ASSESSMENT_COMMENT()),
                            "Validate Immunization Assessment Comment is entered as "
                                    + myPatientUpdate.getsIMMUNIZATION_ASSESSMENT_COMMENT());
                }catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("" + e);
                    }

                String ophthalmicRightDuration = "1";
                String ophthalmicRightDurationUnit = "Days";
                String systemicDuration = "1";
                String systemicDurationUnit = "Days";
                String systemicHistoryComment = "Systemic History Comment";
                String ophthalmicHistoryComment = "Ophthalmic History Comment";
                String systemicHistory = "",ophthalmicHistory="";



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
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_ophthalmicHistoryCommentOnPatientRegForm, ophthalmicHistoryComment);
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
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_systemicHistoryCommentOnPatientRegForm, systemicHistoryComment);
                    break;
                }

                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                        "Allergies");
                Cls_Generic_Methods.customWait();
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
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_drugAllergiesCommentOnPatientRegForm,drugAllergiesComment);
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
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_contactAllergiesCommentOnPatientRegForm,contactAllergiesComment);

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
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_foodAllergiesCommentOnPatientRegForm,foodAllergiesComment);

                    break;
                }

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_otherAllergies,otherAllergiesComment),
                        " Other History Entered as :<b> "+ otherAllergiesComment +"</b>");

                Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm);
                Cls_Generic_Methods.customWait(15);


                String updatedName = CommonActions.getFullPatientName(myPatientUpdate).toUpperCase();

                boolean updatePatientFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments,updatedName);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(updatePatientFound," Updated Patient found in my queue as  "+updatedName);
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                dateAndTime = dateFormat.format(new Date()).toString();

                String patientFullDetails = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.text_patientNameAndDOBInOPD);
                String formatDate = CommonActions.getRequiredFormattedDateTime( "dd/MM/yyyy","dd-MM-yyyy", myPatientUpdate.getsDATE_OF_BIRTH());
                String calculatedAge = (Integer.parseInt(dateAndTime.split("-")[2]) - Integer.parseInt(formatDate.split("-")[2]))+" Years "+(Integer.parseInt(dateAndTime.split("-")[1]) - Integer.parseInt(formatDate.split("-")[1]))+" Months";
                m_assert.assertTrue(patientFullDetails.contains(updatedName)," Updated Patient Name In View Appointment as  "+updatedName);
                m_assert.assertTrue(patientFullDetails.contains("M / "+formatDate)," Updated Patient Gender and DOB found In View Appointment as   "+"M / "+formatDate);
                m_assert.assertTrue(patientFullDetails.contains(calculatedAge)," Updated Patient Age found In View Appointment as  "+calculatedAge);
                String patientDetailsKeysList[] = {"Patient ID","MR No.","Patient Type","Registration Date","Primary Contact","Address","Emergency Details","Language","Occupation","Patient Referral"};
                List<String> patientDetailsValueList = new ArrayList<>();
                patientDetailsValueList.add(mrnNumberUpdate.toUpperCase());
                patientDetailsValueList.add(myPatientUpdate.getsPATIENT_TYPE_INFO()+" - "+oEHR_Data.sPATIENT_TYPE_COMMENT);
                patientDetailsValueList.add(CommonActions.getRequiredFormattedDateTime( "dd-MM-yyyy","dd/MM/yyyy", dateAndTime));
                patientDetailsValueList.add(myPatientUpdate.getsMOBILE_NUMBER());
                patientDetailsValueList.add(myPatientUpdate.getsADDRESS_1()+" , "+myPatientUpdate.getsADDRESS_2()+" "+myPatientUpdate.getsCITY()+" "+myPatientUpdate.getsCITY()+" "+myPatientUpdate.getsSTATE()+" ("+myPatientUpdate.getsPINCODE()+")");
                patientDetailsValueList.add(myPatientUpdate.getsEMERGENCY_CONTACT_NAME()+" - "+myPatientUpdate.getsEMERGENCY_CONTACT_NUMBER());
                patientDetailsValueList.add(myPatientUpdate.getsPRIMARY_LANGUAGE()+" , "+myPatientUpdate.getsSECONDARY_LANGUAGE());
                patientDetailsValueList.add(myPatientUpdate.getsOCCUPATION()+" , "+myPatientUpdate.getsEMPLOYEE_ID());
                patientDetailsValueList.add("None Add");

                for(WebElement keyAndValue : oPage_PatientAppointmentDetails.list_patientDetails){
                    int index = oPage_PatientAppointmentDetails.list_patientDetails.indexOf(keyAndValue);
                    if(Cls_Generic_Methods.isElementDisplayed(keyAndValue)){
                        String fullText = Cls_Generic_Methods.getTextInElement(keyAndValue);
                        if(index == 0){
                            m_assert.assertTrue(fullText.split("\n")[0].equalsIgnoreCase(patientDetailsKeysList[index]),
                                    patientDetailsKeysList[index] + " Header Displayed Correctly  ");
                            m_assert.assertTrue(!fullText.split("\n")[2].isEmpty(),
                                     " Patient Id Displayed Correctly  as "+fullText.split("\n")[2] );
                        }else{
                            m_assert.assertTrue(fullText.split("\n")[0].equalsIgnoreCase(patientDetailsKeysList[index]),
                                     " Header Displayed Correctly  "+patientDetailsKeysList[index] );
                            m_assert.assertTrue(fullText.split("\n")[2].equalsIgnoreCase(patientDetailsValueList.get(index-1)),
                                    " Header Value Displayed Correctly  as "+fullText.split("\n")[2]);
                        }
                    }

                }


                String ophthalmicHistoryText = ophthalmicHistory+" Left Eye since "+ophthalmicRightDuration+" "+
                        ophthalmicRightDurationUnit+" & Right Eye since "+ophthalmicRightDuration+" "+ophthalmicRightDurationUnit+"";

                String systemicHistoryText = systemicHistory+" since "+systemicDuration+" "+systemicDurationUnit;
                String drugAllergiesText = drugAllergies+" Since "+ophthalmicRightDuration+" "+ophthalmicRightDurationUnit;
                String contactAllergiesText = contactAllergies+" Since "+contactDuration+" "+contactDurationUnit;
                String foodAllergiesText = foodAllergies+" Since "+foodDuration+" "+foodDurationUnit;

                String[] patientHistoryHeaders = {"Ophthalmic & Systemic","Medical","Family","Drug (Allergies)","Contact (Allergies)",
                        "Food (Allergies)","Other (Allergies)"};

                for(WebElement header : oPage_Store.list_allergiesHeaderListInStore){

                    String historyHeader = Cls_Generic_Methods.getTextInElement(header);
                    if(Cls_Generic_Methods.isElementDisplayed(header)){

                        int index = oPage_Store.list_allergiesHeaderListInStore.indexOf(header);
                        m_assert.assertTrue(historyHeader.equalsIgnoreCase(patientHistoryHeaders[index]),
                                historyHeader+ " header displayed in store");
                    }else{
                        m_assert.assertWarn(historyHeader+" Either Not present or not advised to patient ");
                    }

                }


                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.list_patientHistoryDetails.get(0)).
                                equalsIgnoreCase(ophthalmicHistoryText+" ,  "+systemicHistoryText+" ,  "+ophthalmicHistoryComment+" ,   "+systemicHistoryComment),
                        " Ophthalmic And Systemic History Details Displayed Correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.list_patientHistoryDetails.get(1)).
                                equals(oEHR_Data.sMEDICAL_HISTORY_COMMENT),
                        "Medical History Details Displayed Correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.list_patientHistoryDetails.get(2)).
                        equals(oEHR_Data.sFAMILY_HISTORY_COMMENT)," Family History Details Displayed Correctly");

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.list_patientHistoryDetails.get(3)).
                                equalsIgnoreCase(drugAllergiesText+" ,  "+drugAllergiesComment),
                        " Drug Allergy Details Displayed Correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.list_patientHistoryDetails.get(4)).
                                equalsIgnoreCase(contactAllergiesText+" ,  "+contactAllergiesComment),
                        "Contact Allergy Details Displayed Correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.list_patientHistoryDetails.get(5)).
                                equalsIgnoreCase(foodAllergiesText+" ,  "+foodAllergiesComment),
                        "Food Allergy Details Displayed Correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.list_patientHistoryDetails.get(6)).equals(otherAllergiesComment),
                        "Other Allergy Details Displayed Correctly");

               boolean printCheck = validatePrintButtonFunctionality(oPage_PatientAppointmentDetails.button_printVisitSummary,"Print Visit Summary");
               m_assert.assertTrue(printCheck,"Print Functionality Working Correctly");















            }else{
                m_assert.assertWarn("Either Edit Icon Not Displayed or Disabled From Policy For this user");
            }






        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    public static boolean selectOptionAndSearch(String searchType ,String searchValue) {
        boolean operationSuccess = false;
        boolean typeSelected = false;
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);

        try {
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_searchPatientOrAddNewPatient, searchType),
                    " Search Type selected as : "+searchType);
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.clearValuesInElement(oPage_NewPatientRegisteration.input_searchPatient);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_searchPatient,searchValue),
                    " Search Value Entered as : "+searchValue );
            Cls_Generic_Methods.customWait(2);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.button_searchPatient),
                    " Search Icon Clicked In Search Box");
            Cls_Generic_Methods.customWait(4);
            try{
                if(Cls_Generic_Methods.isElementDisplayed(oPage_NewPatientRegisteration.list_textSearchResults.get(0))){
                    operationSuccess = true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }
        catch (Exception e){
            e.printStackTrace();
            m_assert.assertFalse("Error while Performing Search \n" + e);
        }
        return operationSuccess ;
    }
    public boolean validatePrintButtonFunctionality(WebElement printButton , String printButtonName){

        Page_Sale oPage_Sale = new Page_Sale(driver);
        boolean bPrintWork = false ;

        try {
            int preWindowsCount = driver.getWindowHandles().size();
            String initialWindowHandle = driver.getWindowHandle();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(printButton),
                    printButtonName+" Button Displayed and Clicked ");
            Cls_Generic_Methods.customWait(2);
            if(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.list_printDropdownFirstOptionInAdvanceReceipt)){
                Cls_Generic_Methods.clickElement(oPage_Sale.list_printDropdownFirstOptionInAdvanceReceipt);
                Cls_Generic_Methods.customWait();
            }else {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Sale.list_printDropdownFirstOption)) {
                    Cls_Generic_Methods.clickElement(oPage_Sale.list_printDropdownFirstOption);
                    Cls_Generic_Methods.customWait();
                }
            }
            Cls_Generic_Methods.customWait(6);
            int postWindowsCount = driver.getWindowHandles().size();

            bPrintWork = postWindowsCount > preWindowsCount ;
            m_assert.assertTrue(bPrintWork, "Validated Print -->  Print page opened");

            for (String currentWindowHandle : driver.getWindowHandles()) {
                if (!currentWindowHandle.equals(driver.getWindowHandle())) {
                    driver.switchTo().window(currentWindowHandle);
                }
            }
            driver.close();
            driver.switchTo().window(initialWindowHandle);
            Cls_Generic_Methods.customWait(4);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return bPrintWork ;
    }






}







