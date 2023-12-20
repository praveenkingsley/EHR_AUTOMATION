package tests.departments.opd;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.Sprint88.Page_ShowMrnInInvestigationTemplateCreate;
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
import pages.ipd.forms.postOperative.Page_PainAssessment;
import pages.ipd.forms.preOperative.Page_AdmissionInPreOperative;
import pages.ipd.forms.preOperative.Page_CarePlan;
import pages.opd.Page_OPD;
import pages.optometrist.Pages_Optometrist;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.settings.organisationSettings.general.Page_PatientSettings;
import pages.store.Page_PatientQueue;
import pages.store.Page_Store;
import pages.store.PharmacyStore.Items.Page_Variant;

import java.util.ArrayList;
import java.util.List;

import static pages.commonElements.CommonActions.*;

public class RestrictAdvisingZeroStockTest extends TestBase {

    String sPolicyRequired = "OPD";

    String sViewPolicyComponent = "ADVISE MEDICINE (TEMPLATE)";
    String sViewPolicyDescription = "Allow advising zero stock pharmacy medicines";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String sStore = "Pharmacy automation- Pharmacy";
    List<String> medicationItem = new ArrayList<>();
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    static Model_Patient myPatient;
    ArrayList<String> checkedMandatoryFields= new ArrayList<>();



    @Test(enabled = true, description = "Validate Essential Details are filled in Create New Patient")
    public void createPatientInOpd() {

        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            Page_PatientSettings oPage_PatientSettings = new Page_PatientSettings(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.customWait(2);
            CommonActions.selectFacility("TST");



            try {
                //To find out selected mandatory field in Organisation setting
                Cls_Generic_Methods.customWait(5);
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

                m_assert.assertInfo("Selected mandatory field in Patient setting are "+
                        (checkedMandatoryFields.toString().replaceAll("\\[", "").replaceAll("\\]","")));

                CommonActions.selectDepartmentOnApp("OPD");

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


                // m_assert.assertTrue(
                //      Cls_Generic_Methods.sendKeysIntoElement(sMRNumber),
                //      " entered for Medical Report Number");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }
    @Test(enabled = true, description = "Click on Create Appointment button")
    public void clickOnCreateAppointment() {

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

    @Test(enabled = true, description = "Validating Advising Zero Stock For Enable Policy")
    public void validatingAdvisingZeroStockFunctionalityInOPD() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);

        try {
            // Disabling Adjustment Policy For User
            Page_Navbar oPage_Navbar = new Page_Navbar(driver);
            Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
            try {
                //Opening Organization Settings
                CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUsers, 16);
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_allUsers), "clicked on All users");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Unable to open organisation setting " + e);
            }

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening Store to validate the policy
            m_assert.assertTrue(CommonActions.selectStoreOnApp(sStore), sStore + " Store Opened For Validation Of Policy");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Variant");
            medicationItem.add(getItemNameOnStockBasis(0.0));
            medicationItem.add(getItemNameOnStockBasis(10.0));
            medicationItem.add("para_new");
            medicationItem.add("RABDEN-D");

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            Cls_Generic_Methods.customWait();
            CommonActions.selectDepartmentOnApp("OPD");
            Cls_Generic_Methods.customWait();

            String MyQueueTab = "My Queue";
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            m_assert.assertTrue(
                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                    "Validate " + MyQueueTab + " tab is selected");
            Cls_Generic_Methods.customWait(10);

            CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments,concatPatientFullName);
            Cls_Generic_Methods.customWait(4);
            String statusYes = "Yes";
            validateInTemplate("Eye",medicationItem,statusYes);
            validateInTemplate("Post OP",medicationItem,statusYes);
            validateInTemplate("Lens",medicationItem,statusYes);
            validateInTemplate("PMT",medicationItem,statusYes);
            validateInTemplate("Quick Eye",medicationItem,statusYes);
            validateInTemplate("Paediatrics",medicationItem,statusYes);
            validateInTemplate("Orthoptics",medicationItem,statusYes);
            validateInTemplate("Express",medicationItem,statusYes);
            validateInTemplate("Trauma",medicationItem,statusYes);
            validateInTemplate("Free Form",medicationItem,statusYes);



            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUsers, 16);
            Cls_Generic_Methods.scrollToTop();
            Cls_Generic_Methods.customWait();
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_allUsers), "clicked on All users");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectDepartmentOnApp("OPD");
            Cls_Generic_Methods.customWait(2);
            CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments,concatPatientFullName);
            Cls_Generic_Methods.customWait(4);

            String statusNo = "No";
            validateInTemplate("Eye",medicationItem,statusNo);
            validateInTemplate("Post OP",medicationItem,statusNo);
            validateInTemplate("Lens",medicationItem,statusNo);
            validateInTemplate("PMT",medicationItem,statusNo);
            validateInTemplate("Quick Eye",medicationItem,statusNo);
            validateInTemplate("Paediatrics",medicationItem,statusNo);
            validateInTemplate("Orthoptics",medicationItem,statusNo);
            validateInTemplate("Express",medicationItem,statusNo);
            validateInTemplate("Trauma",medicationItem,statusNo);
            validateInTemplate("Free Form",medicationItem,statusNo);



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

                Cls_Generic_Methods.customWait(8);
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



            } else {
                m_assert.assertTrue(false, "searched patient is not present in patient list");
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        }

    }

    @Test(enabled = true, description = "Validation Patient and Template Details In Pharmacy Store")
    public void validatingAdvisingZeroStockFunctionalityInIPD() {

        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_IPD oPage_IPD = new Page_IPD(driver);
        String myQueueTab = "My Queue";
        boolean bValidatePatientFound = false;
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        Page_CarePlan oPage_CarePlan = new Page_CarePlan(driver);
        Page_DischargeForm oPage_DischargeForm = new Page_DischargeForm(driver);
        Page_PainAssessment oPage_PainAssessment = new Page_PainAssessment(driver);
        Page_OperativeForm oPage_OperativeForm = new Page_OperativeForm(driver);
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);


        Page_AdmissionInPreOperative oPage_AdmissionInPreOperative = new Page_AdmissionInPreOperative(driver);


        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);

            try {
                try {
                    // Disabling Adjustment Policy For User
                    try {
                        //Opening Organization Settings
                        CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                        CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                        Cls_Generic_Methods.customWait(3);
                        CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUsers, 16);
                        Cls_Generic_Methods.scrollToTop();
                        Cls_Generic_Methods.customWait();
                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_allUsers), "clicked on All users");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("Unable to open organisation setting " + e);
                    }

                    CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
                    Cls_Generic_Methods.customWait(3);
                }catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to open organisation setting " + e);
                }
                CommonActions.selectDepartmentOnApp("IPD");
                Cls_Generic_Methods.customWait(10);

                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, myQueueTab),
                        "Validate " + myQueueTab + " tab is selected");
                Cls_Generic_Methods.customWait(3);

                bValidatePatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, concatPatientFullName);

                // Validate that Pre Operative forms section is visible
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_preOperativeSection, 16);
                Cls_Generic_Methods.scrollToElementByJS(oPage_IPD.text_preOperativeSection);

                try {
                    Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.button_admissionInPreOperative);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AdmissionInPreOperative.tab_AdminTabOnAdmissionUnderPreOperative, 16);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.button_saveOnModalHeader),
                            "Validate Save button is clicked on Admission under Pre-Operative");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);
                    Cls_Generic_Methods.customWait(4);
                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_preOperativeSection, 16);

                    m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_CarePlan.button_carePlanInPreOperative),
                            "<b>Validate Care Plan button is visible under the Pre-Operative forms</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CarePlan.button_carePlanInPreOperative),
                            "Validate Care Plan button is clicked");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CarePlan.button_saveOnModalHeader, 16);
                    validateInIPDTemplate ("Care Plan",medicationItem, "Yes");
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
                    validateInIPDTemplate ("Discharge Form",medicationItem, "Yes");

                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver,
                                    oPage_PainAssessment.input_painScaleInPainAssessmentTemplate),
                            "Clicked on pain assessment template");
                    Cls_Generic_Methods.customWait(4);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver,
                                    oPage_PainAssessment.tab_medicationTabInPainAssessmentTemplate),
                            "clicked on medication tab in pain assessment template");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(
                            oPage_PainAssessment.input_medicationNameInPainAssessmentTemplate, 20);
                    validateInIPDTemplate ("Pain Assessment",medicationItem, "Yes");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_preOperativeSection, 16);

                    CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                    Cls_Generic_Methods.customWait(3);
                    CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUsers, 16);
                    Cls_Generic_Methods.scrollToTop();
                    Cls_Generic_Methods.customWait();
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_allUsers), "clicked on All users");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

                    CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
                    Cls_Generic_Methods.customWait(3);
                    CommonActions.selectDepartmentOnApp("IPD");
                    Cls_Generic_Methods.customWait(2);
                    m_assert.assertTrue(
                            CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, myQueueTab),
                            "Validate " + myQueueTab + " tab is selected");
                    Cls_Generic_Methods.customWait(3);

                    bValidatePatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, concatPatientFullName);

                    // Validate that Pre Operative forms section is visible
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_preOperativeSection, 16);

                    m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_CarePlan.button_carePlanInPreOperative),
                            "<b>Validate Care Plan button is visible under the Pre-Operative forms</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CarePlan.button_carePlanInPreOperative),
                            "Validate Care Plan button is clicked");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CarePlan.button_saveOnModalHeader, 16);
                    validateInIPDTemplate ("Care Plan",medicationItem, "No");
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
                    validateInIPDTemplate ("Discharge Form",medicationItem, "No");

                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver,
                                    oPage_PainAssessment.input_painScaleInPainAssessmentTemplate),
                            "Clicked on pain assessment template");
                    Cls_Generic_Methods.customWait(4);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.clickElement(driver,
                                    oPage_PainAssessment.tab_medicationTabInPainAssessmentTemplate),
                            "clicked on medication tab in pain assessment template");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(
                            oPage_PainAssessment.input_medicationNameInPainAssessmentTemplate, 20);
                    validateInIPDTemplate ("Pain Assessment",medicationItem, "No");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_preOperativeSection, 16);



                } catch (Exception e) {
                    throw new RuntimeException(e);
                }




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


    public static String getItemNameOnStockBasis(Double stock) {

        Page_Variant oPage_Variant = new Page_Variant(driver);
        String medicationName = "";

        try {
            for (WebElement category : oPage_Variant.list_itemCategoryOnItemVariant) {

                int index = oPage_Variant.list_itemCategoryOnItemVariant.indexOf(category);
                String categoryTextUI = Cls_Generic_Methods.getTextInElement(category);
                String stockTextUI = Cls_Generic_Methods.getTextInElement(oPage_Variant.list_itemStockOnItemVariant.get(index));

                if(stock == 0.0){
                    if(categoryTextUI.equalsIgnoreCase("Medication") &&
                            Double.parseDouble(stockTextUI) == stock){
                        medicationName = Cls_Generic_Methods.getTextInElement(oPage_Variant.list_itemDescriptionNameOnItemVariant.get(index));
                        break;
                    }
                }else{
                    if(categoryTextUI.equalsIgnoreCase("Medication") &&
                            Double.parseDouble(stockTextUI) > stock){
                        medicationName = Cls_Generic_Methods.getTextInElement(oPage_Variant.list_itemDescriptionNameOnItemVariant.get(index));
                        break;
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  medicationName;
    }

    public void validateInTemplate(String templateName,List<String> medicineName,String policyStatus){

        Page_AdviceTab oPage_AdviceTab = new Page_AdviceTab(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        boolean isPresent = false ;


        try {
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
            Cls_Generic_Methods.customWait(3);
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, templateName), "Validate " + templateName + " template  is selected");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_SaveTemplate, 20);
            Cls_Generic_Methods.customWait();


            // Validate Advice Tab
            try {
                if (templateName.equalsIgnoreCase("Free Form")){
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.tab_adviceFreeForm), "Advice Tab Is selected");

                }else {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.tab_advice), "Advice Tab Is selected");
                }
                Cls_Generic_Methods.customWait();
                int index = 0;
                for(String item : medicineName) {
                    Cls_Generic_Methods.clearValuesInElement(oPage_AdviceTab.list_medicationNameInputBox.get(0));
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_AdviceTab.list_medicationNameInputBox.get(0), item),
                            "Medicine Name is Entered as : " + item);
                    Cls_Generic_Methods.customWait(3);
                    try {
                        String searchedResultText = Cls_Generic_Methods.getTextInElement(oPage_AdviceTab.text_medicationInSearchedList);
                        if (searchedResultText.contains(item)) {
                            isPresent = true;
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if (policyStatus.equalsIgnoreCase("No") &&
                            index==0){
                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_AdviceTab.text_zeroStockWarningMessage),
                                "Zero Stock Warning Message Displayed Correctly As : "+Cls_Generic_Methods.getTextInElement(oPage_AdviceTab.text_zeroStockWarningMessage));
                        m_assert.assertFalse(isPresent,"Item Name is Not Displaying Correctly in Template Advise Tab for Zero Stock Item");
                    }else{
                        m_assert.assertTrue(isPresent,"Item Name is Displaying Correctly in Template Advise Tab for Zero/ More than Zero Stock Item");

                    }

                    index++;
                }
                Cls_Generic_Methods.clearValuesInElement(oPage_AdviceTab.list_medicationNameInputBox.get(0));
                Cls_Generic_Methods.customWait(1);

                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_AdviceTab.select_medicationSets, "Yuvaraj Zero Stock Automation"),
                        "Selected Medication Set as " + "Yuvaraj Zero Stock Automation");
                Cls_Generic_Methods.customWait();

                for (int i = 0; i<4; i++){
                    if (policyStatus.equalsIgnoreCase("Yes")) {
                        String check = Cls_Generic_Methods.getElementAttribute(oPage_AdviceTab.list_medicationNameFilledInputBox.get(i),"value");
                        System.out.println(check);
                        System.out.println(check.isEmpty());

                        m_assert.assertTrue(!Cls_Generic_Methods.getElementAttribute(oPage_AdviceTab.list_medicationNameFilledInputBox.get(i),"value").isEmpty(),
                                "All Medicine with or without stock are Displaying Correctly on selection of Medication Set");
                    }
                    else {
                        String itemName = Cls_Generic_Methods.getElementAttribute(oPage_AdviceTab.list_medicationNameFilledInputBox.get(i),"value");
                        if (itemName.equalsIgnoreCase("Zero Stock Automation")){
                            m_assert.assertFatal(" Item with Zero Stock is still visible on Disable Setting");
                        }
                        m_assert.assertTrue(itemName+" Displaying Correctly on Selection Medication set on Disable Setting ");

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Error while validating Advice. \n" + e);
            }

            //Click On Save Button
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_closeTemplateWithoutSaving), "Save button is clicked");
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }


            //After Close wait till user drop down display

        } catch (Exception e) {
            m_assert.assertTrue(false, "<b>Eye Template is not selected. </b> " + e);
            e.printStackTrace();
        }

    }
    public void validateInIPDTemplate(String template ,List<String> medicineName,String policyStatus){

        Page_AdviceTab oPage_AdviceTab = new Page_AdviceTab(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_CarePlan oPage_CarePlan = new Page_CarePlan(driver);
        Page_DischargeForm oPage_DischargeForm = new Page_DischargeForm(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_PainAssessment oPage_PainAssessment = new Page_PainAssessment(driver);

        boolean isPresent = false ;


        try {


            // Validate Advice Tab
            try {

                Cls_Generic_Methods.customWait();
                int index = 0;
                for(String item : medicineName) {
                    if(template.equalsIgnoreCase("Discharge Form")){
                        Cls_Generic_Methods.clearValuesInElement(oPage_AdviceTab.list_medicationNameFilledInputBox.get(0));
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_AdviceTab.list_medicationNameFilledInputBox.get(0), item),
                                "Medicine Name is Entered as : " + item);
                    }else {
                        Cls_Generic_Methods.clearValuesInElement(oPage_AdviceTab.list_medicationNameInputBox.get(0));
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_AdviceTab.list_medicationNameInputBox.get(0), item),
                                "Medicine Name is Entered as : " + item);
                    }
                    Cls_Generic_Methods.customWait(3);
                    try {
                        String searchedResultText = Cls_Generic_Methods.getTextInElement(oPage_AdviceTab.text_medicationInSearchedList);
                        if (searchedResultText.contains(item)) {
                            isPresent = true;
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if (policyStatus.equalsIgnoreCase("No") &&
                            index==0){
                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_AdviceTab.text_zeroStockWarningMessage),
                                "Zero Stock Warning Message Displayed Correctly As : "+Cls_Generic_Methods.getTextInElement(oPage_AdviceTab.text_zeroStockWarningMessage));
                        m_assert.assertFalse(isPresent,"Item Name is Not Displaying Correctly in Template Advise Tab for Zero Stock Item");
                    }else{
                        m_assert.assertTrue(isPresent,"Item Name is Displaying Correctly in Template Advise Tab for Zero/ More than Zero Stock Item");

                    }

                    index++;
                }

                if(template.equalsIgnoreCase("Discharge Form")) {
                    Cls_Generic_Methods.clearValuesInElement(oPage_AdviceTab.list_medicationNameFilledInputBox.get(0));
                    Cls_Generic_Methods.customWait(1);
                }else {
                    Cls_Generic_Methods.clearValuesInElement(oPage_AdviceTab.list_medicationNameInputBox.get(0));
                    Cls_Generic_Methods.customWait(1);
                }

                if(template.equalsIgnoreCase("Care Plan")) {
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_CarePlan.select_medicationSetInCarePlan, "Yuvaraj Zero Stock Automation"),
                            "Selected Medication Set as " + "Yuvaraj Zero Stock Automation");
                }else if(template.equalsIgnoreCase("Discharge Form")){
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_DischargeForm.button_selectMedicationSetInDischargeTemplate, "Yuvaraj Zero Stock Automation"),
                            "Selected Medication Set as " + "Yuvaraj Zero Stock Automation");
                }else if(template.equalsIgnoreCase("Pain Assessment")){

                    Cls_Generic_Methods.selectElementByVisibleText(oPage_PainAssessment.select_medicationSetLevel, "All");
                    Cls_Generic_Methods.customWait();
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_CarePlan.select_medicationSetInCarePlan, "Yuvaraj Zero Stock Automation"),
                            "Selected Medication Set as " + "Yuvaraj Zero Stock Automation");
                }
                Cls_Generic_Methods.customWait();

                for (int i = 0; i<4; i++){
                    if (policyStatus.equalsIgnoreCase("Yes")) {

                        m_assert.assertTrue(!Cls_Generic_Methods.getElementAttribute(oPage_AdviceTab.list_medicationNameFilledInputBox.get(i),"value").isEmpty(),
                                "All Medicine with or without stock are Displaying Correctly on selection of Medication Set");
                    }
                    else {
                        String itemName = Cls_Generic_Methods.getElementAttribute(oPage_AdviceTab.list_medicationNameFilledInputBox.get(i),"value");
                        if (itemName.equalsIgnoreCase("Zero Stock Automation")){
                            m_assert.assertFatal(" Item with Zero Stock is still visible on Disable Setting");
                        }
                        m_assert.assertTrue(itemName+" Displaying Correctly on Selection Medication set on Disable Setting ");

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Error while validating Advice. \n" + e);
            }

            //Click On Save Button
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_closeTemplateWithoutSaving), "Save button is clicked");
                Cls_Generic_Methods.customWait(4);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertWarn("" + e);
            }


            //After Close wait till user drop down display

        } catch (Exception e) {
            m_assert.assertTrue(false, "<b>Eye Template is not selected. </b> " + e);
            e.printStackTrace();
        }

    }

}


