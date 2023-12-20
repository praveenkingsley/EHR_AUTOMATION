package tests.settings.facilitySetting.clinical;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.OPD_Data;
import data.Settings_Data;
import data.settingsData.FacilitySettings_Data;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.common_tabs.advice.Page_AdviceTab;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;
import pages.settings.facilitySettings.general.facilityContactList.Page_FacilityContactList;
import pages.settings.facilitySettings.clinical.facilityMedicationLists.Page_FacilityMedicationLists;
import pages.settings.facilitySettings.general.otSetup.Page_OtSetup;

import java.util.ArrayList;
import java.util.Arrays;

public class MedicationListTest extends TestBase {

    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();
    String sSpeciality = "Ophthalmology";
    String medicationListName = "Test Medication List Name", updatedMedicationListName = "UPDATED " + medicationListName;
    String sDispensingUnit = "Tablets", sUpdatedDispensingUnit = "Capsules";
    public static ArrayList<String> listMedClassNames = new ArrayList<String>(Arrays.asList("Antiemetics", "Antifungals", "Antihistamines & Antiallergics"));
    public static ArrayList<String> listMedGenericNames = new ArrayList<String>(Arrays.asList("Allantoin", "Acediasulfone", "Antazoline"));

    static int standardWaitTimeInSecs = 6;
//	String[] medClassNamesArray = {"Antiemetics", "Antifungals", "Antihistamines & Antiallergics"};
//	String[] medGenericNamesArray = {"Allantoin", "Acediasulfone", "Antazoline"};

    @Test(enabled = true, description = "Add Patient for Medication List")
    public void createPatientToValidateMedicationList() {

        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

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

                // Validate the tabs on Patient Registration Form
                if (oPage_NewPatientRegisteration.tabs_PatientRegForm.size() != oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.size()) {
                    m_assert.assertTrue(false, "No. of Tabs on Patient Reg. Form is " + oPage_NewPatientRegisteration.tabs_PatientRegForm.size() + ". Expected = " + oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.size());
                } else {

                    m_assert.assertTrue("No. of Tabs on Patient Reg. & Appointment Form is " + oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.size());

                    if (!Cls_Generic_Methods.getElementAttribute(oPage_NewPatientRegisteration.tabs_PatientRegForm.get(0), "class").equals("active")) {
                        m_assert.assertTrue(false, "Patient Details Tab is not selected on start by default.");
                    } else {
                        m_assert.assertTrue(true, "Patient Details Tab is selected on start by default.");

                        try {
                            for (int i = 0; i < oPage_NewPatientRegisteration.tabs_PatientRegForm.size(); i++) {

                                if (oPage_NewPatientRegisteration.tabs_PatientRegForm.get(i).getText().trim().equals(oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.get(i))) {

                                    m_assert.assertInfo(oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.get(i) + " Tab is displayed on the form.");
                                } else {
                                    m_assert.assertTrue(false, oEHR_Data.list_PATIENT_REGISTERATION_FORM_TABS.get(i) + " Tab is not displayed on the form.");
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("" + e);
                        }
                    }
                }

                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage, 5), "Alert for compulsory field is visible by default on the empty form.");

                Cls_Generic_Methods.clickElement(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm);
                Cls_Generic_Methods.customWait(1);

                // Validate the Compulsory Sections Message
                if (Cls_Generic_Methods.getTextInElement(oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage).trim().equals(oEHR_Data.sCOMPULSORY_FIELDS_MESSAGE)) {
                    m_assert.assertTrue(true, "Validate that the Compulsory sections message is " + oEHR_Data.sCOMPULSORY_FIELDS_MESSAGE);
                } else {
                    m_assert.assertTrue(false, "Validate that the Compulsory sections message is " + oEHR_Data.sCOMPULSORY_FIELDS_MESSAGE);
                }

                // Validate the CSS of Compulsory Alert message
                if (Cls_Generic_Methods.getElementAttribute(oPage_NewPatientRegisteration.subText_requiredFieldsAlertMessage, "style").equals(oEHR_Data.sSTYLE_OF_RED_ALERT_MSG_FOR_MANODATORY_FIELDS)) {
                    m_assert.assertTrue(true, "Alert for compulsory field is highlighted in <b>Red</b> when trying to Create Appointment with empty form. Message = " + oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage.getText());
                } else {
                    m_assert.assertTrue(false, "Alert for compulsory field is highlighted in <b>Red</b> when trying to Create Appointment with empty form.<br>" + "Expected = " + oEHR_Data.sSTYLE_OF_RED_ALERT_MSG_FOR_MANODATORY_FIELDS + "<br>Actual = " + Cls_Generic_Methods.getElementAttribute(oPage_NewPatientRegisteration.subText_requiredFieldsAlertMessage, "style"));
                }

                m_assert.assertTrue(oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage.getText().trim().contains("First Name"), "First Name is visible in the Compulsory Fields alert message.");

                m_assert.assertTrue(oPage_NewPatientRegisteration.text_compulsoryFieldsAlertMessage.getText().trim().contains("Mobile Number"), "Mobile Number is visible in the Compulsory Fields alert message.");

                // Entering Essential Form Data
                if (!myPatient.getsSALUTATION().isEmpty()) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()), "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
                }

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()), "First Name is entered as - " + myPatient.getsFIRST_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_middleNameOnPatientRegForm, myPatient.getsMIDDLE_NAME()), "Middle Name is entered as - " + myPatient.getsMIDDLE_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_lastNameOnPatientRegForm, myPatient.getsLAST_NAME()), "Last Name is entered as - " + myPatient.getsLAST_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, myPatient.getsMOBILE_NUMBER()), "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm), "Create Appointment button clicked");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 12);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Add new Medication List")
    public void validateAddNewMedicationList() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_FacilityMedicationLists oPage_FacilityMedicationLists = new Page_FacilityMedicationLists(driver);
        boolean bValidateMedicationListCreated = false;

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(1);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility Medication Lists");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationLists.button_CreateMedicationLists, 8);

                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_FacilityMedicationLists.button_CreateMedicationLists),
                        "<b>Add Medication List</b> button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_modal, 8);

                Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityMedicationLists.select_SpecialityForMedicationList, sSpeciality);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityMedicationLists.input_MedicationName, medicationListName);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityMedicationLists.select_DispensingUnit, sDispensingUnit);

                for (String sMedicationClass : listMedClassNames) {
                    m_assert.assertTrue(fillMedicineClassAndValidate(sMedicationClass),
                            "Medication Class Name <b>" + sMedicationClass + "</b> added successfully");
                }


                for (String sMedicationGeneric : listMedGenericNames) {
                    m_assert.assertTrue(fillGenericCompositionAndValidate(sMedicationGeneric, 1, "mg"),
                            "Medication Generic Name <b>" + sMedicationGeneric + "</b> added successfully");
                }

                Cls_Generic_Methods.clickElement(oPage_FacilityMedicationLists.button_SaveAndAddAnotherForAddMedicationList);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationLists.button_CreateMedicationLists, 8);

                Cls_Generic_Methods.clickElement(oPage_FacilityMedicationLists.button_CloseModalForAddMedicationList);

                for (WebElement eMedicationSetName : oPage_FacilityMedicationLists.list_rowsMedicationNameOnMedicineLists) {
                    System.out.println(Cls_Generic_Methods.getTextInElement(eMedicationSetName));
                    if (Cls_Generic_Methods.getTextInElement(eMedicationSetName).contains(medicationListName)) {
                        bValidateMedicationListCreated = true;
                        break;
                    }
                }
                m_assert.assertTrue(bValidateMedicationListCreated,
                        "Validate the Medication set <b>" + medicationListName + "</b> has been created and displayed");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validate search and edit the Medication List")
    public void validateSearchAndEditMedicationList() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_FacilityMedicationLists oPage_FacilityMedicationLists = new Page_FacilityMedicationLists(driver);
        boolean bValidateMedicationListFound = false;
        int iMedicationListIndex = -1;
        String preMedicationName = null, preGenericName = null, preMedicationClassName = null, preDispensingType = null;
        String postMedicationName = null, postGenericName = null, postMedicationClassName = null, postDispensingType = null;

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(1);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility Medication Lists");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationLists.button_CreateMedicationLists, 8);

                for (WebElement eMedicationSetName : oPage_FacilityMedicationLists.list_rowsMedicationNameOnMedicineLists) {
                    if (Cls_Generic_Methods.getTextInElement(eMedicationSetName).contains(medicationListName)) {
                        iMedicationListIndex = oPage_FacilityMedicationLists.list_rowsMedicationNameOnMedicineLists.indexOf(eMedicationSetName);
                        bValidateMedicationListFound = true;
                        break;
                    }
                }

                preMedicationName = Cls_Generic_Methods.getTextInElement(oPage_FacilityMedicationLists.list_rowsMedicationNameOnMedicineLists.get(iMedicationListIndex));
                preGenericName = Cls_Generic_Methods.getTextInElement(oPage_FacilityMedicationLists.list_rowsMedicationGenericNameOnMedicineLists.get(iMedicationListIndex));
                preMedicationClassName = Cls_Generic_Methods.getTextInElement(oPage_FacilityMedicationLists.list_rowsMedicationClassNameOnMedicineLists.get(iMedicationListIndex));
                preDispensingType = Cls_Generic_Methods.getTextInElement(oPage_FacilityMedicationLists.list_rowsMedicationDispensingTypeOnMedicineLists.get(iMedicationListIndex));

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FacilityMedicationLists.list_ButtonForEditMedicineLists.get(iMedicationListIndex)),
                        "Clicked on the Edit button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_modal, 8);

//                Speciality Setup -
//                Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityMedicationLists.select_SpecialityForMedicationList, sSpeciality);
//                String sPreSelectedValue = Cls_Generic_Methods.getSelectedValue(oPage_FacilityMedicationLists.select_SpecialityForMedicationList);

                Cls_Generic_Methods.clearValuesInElement(oPage_FacilityMedicationLists.input_MedicationName);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityMedicationLists.input_MedicationName, updatedMedicationListName),
                        "Updated the Medication List Name as <b>" + updatedMedicationListName + "</b>");

                String preSelectedDispensingUnit = Cls_Generic_Methods.getSelectedValue(oPage_FacilityMedicationLists.select_DispensingUnit);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityMedicationLists.select_DispensingUnit, sUpdatedDispensingUnit),
                        "Updated the Medication Dispensing Unit from <b>" + preSelectedDispensingUnit + "</b> to <b>" + sUpdatedDispensingUnit + "<b>");

                int medicineClassSizeCounter = oPage_FacilityMedicationLists.list_ButtonForDeleteMedicineClass.size();
                medicineClassSizeCounter = medicineClassSizeCounter - 1;
                String sFinalMedicineClass = Cls_Generic_Methods.getTextInElement(oPage_FacilityMedicationLists.list_textMedicineClassName.get(medicineClassSizeCounter));

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FacilityMedicationLists.list_ButtonForDeleteMedicineClass.get(medicineClassSizeCounter)),
                        "Removed the Medication Class <b>" + sFinalMedicineClass + "</b> from Medication List");

                int GenericNameSizeCounter = oPage_FacilityMedicationLists.list_ButtonForDeleteGenericComposition.size();
                GenericNameSizeCounter = GenericNameSizeCounter - 1;
                String sFinalGenericName = Cls_Generic_Methods.getElementAttribute(
                        oPage_FacilityMedicationLists.list_InputForItemNameOnGenericComposition.get(medicineClassSizeCounter), "value");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FacilityMedicationLists.list_ButtonForDeleteGenericComposition.get(medicineClassSizeCounter)),
                        "Removed the Generic Composition Medication <b>" + sFinalGenericName + "</b> from Medication List");

                Cls_Generic_Methods.customWait(1);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FacilityMedicationLists.button_UpdateForAddMedicationList),
                        "Clicked on the <b>Update</b> button on Medication List");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationLists.button_CreateMedicationLists, 8);

                for (WebElement eMedicationSetName : oPage_FacilityMedicationLists.list_rowsMedicationNameOnMedicineLists) {
                    if (Cls_Generic_Methods.getTextInElement(eMedicationSetName).contains(medicationListName)) {
                        iMedicationListIndex = oPage_FacilityMedicationLists.list_rowsMedicationNameOnMedicineLists.indexOf(eMedicationSetName);
                        bValidateMedicationListFound = true;
                        break;
                    }
                }

                postMedicationName = Cls_Generic_Methods.getTextInElement(oPage_FacilityMedicationLists.list_rowsMedicationNameOnMedicineLists.get(iMedicationListIndex));
                postGenericName = Cls_Generic_Methods.getTextInElement(oPage_FacilityMedicationLists.list_rowsMedicationGenericNameOnMedicineLists.get(iMedicationListIndex));
                postMedicationClassName = Cls_Generic_Methods.getTextInElement(oPage_FacilityMedicationLists.list_rowsMedicationClassNameOnMedicineLists.get(iMedicationListIndex));
                postDispensingType = Cls_Generic_Methods.getTextInElement(oPage_FacilityMedicationLists.list_rowsMedicationDispensingTypeOnMedicineLists.get(iMedicationListIndex));


                m_assert.assertTrue(preMedicationName != postMedicationName,
                        "Medication Name Updated from <br><b>" + preMedicationName + "</b> to <br><b>" + postMedicationName + "</b>");

                m_assert.assertTrue(preGenericName != postGenericName,
                        "Medication Generic Name Updated from <br><b>" + preGenericName + "</b> to <br><b>" + postGenericName + "</b>");

                m_assert.assertTrue(preMedicationClassName != postMedicationClassName,
                        "Medication Class Name Updated from <br><b>" + preMedicationClassName + "</b> to <br><b>" + postMedicationClassName + "</b>");

                m_assert.assertTrue(preDispensingType != postDispensingType,
                        "Dispensing Type Updated from <br><b>" + preDispensingType + "</b> to <br><b>" + postDispensingType + "</b>");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validate the Medication List in Template")
    public void validateMedicationListInTemplate() {

        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_AdviceTab oPage_AdviceTab = new Page_AdviceTab(driver);
        Page_FacilityMedicationLists oPage_FacilityMedicationLists = new Page_FacilityMedicationLists(driver);

        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        String sTemplateOption = "Post OP";
        String sMedicationSetLevel = "All";
        boolean bPatientNameFound = false;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                //go in opd and create template
                CommonActions.selectDepartmentOnApp("OPD");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);

                m_assert.assertInfo(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, OPD_Data.tab_ALL),
                        "Validate " + OPD_Data.tab_ALL + " tab is selected");
                Cls_Generic_Methods.customWait(1);

                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

                if (bPatientNameFound) {
                    //Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_markPatientArrived);
                    Cls_Generic_Methods.customWait();
                    //Cls_Generic_Methods.waitForPageLoad(driver, 5);

                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate),
                            "Clicked on + New template button");

                    m_assert.assertInfo(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate,
                            sTemplateOption), "Validate " + sTemplateOption + " template  is selected");

                    m_assert.assertInfo(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_templateName, 8),
                            "Templated Opened, Text Visible: <b> " + oPage_CommonElements.header_templateName.getText() + "</b>");

                    Cls_Generic_Methods.clickElement(oPage_AdviceTab.tab_advice);
                    Cls_Generic_Methods.clickElement(oPage_AdviceTab.tab_medicationUnderAdviceTab);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AdviceTab.select_medicationSetLevel, 8);

                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_AdviceTab.select_medicationSetLevel, sMedicationSetLevel),
                            "Medication Set level is set to " + sMedicationSetLevel);
                    Cls_Generic_Methods.customWait();

                    try {
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityMedicationLists.list_input_MedicineName.get(0), medicationListName),
                                "Entered Medication Listing as - <b>" + medicationListName + "</b>");
                        Cls_Generic_Methods.customWait(4);

                        new Actions(driver).sendKeys(Keys.ARROW_DOWN).perform();
                        Cls_Generic_Methods.customWait(1);

                        new Actions(driver).sendKeys(Keys.RETURN).perform();
                        Cls_Generic_Methods.customWait();

                        Platform platformName = ((HasCapabilities) driver).getCapabilities().getPlatformName();
                        Keys cmdCtrl = platformName.is(Platform.MAC) ? Keys.COMMAND : Keys.CONTROL;
                        new Actions(driver).sendKeys(Keys.TAB).perform();
                        Cls_Generic_Methods.customWait(1);

                        new Actions(driver).keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).build().perform();
                        Cls_Generic_Methods.customWait(1);

                        Cls_Generic_Methods.copyContentToClipboardByAction();
                        String copiedText = Cls_Generic_Methods.getClipboardContent();

                        if (!copiedText.equalsIgnoreCase(medicationListName)) {
                            m_assert.assertTrue(true,
                                    "Validate the Medication List <b>" + copiedText +
                                            "</b> is selected on entering <b>" + medicationListName +
                                            "</b> in the Templates");
                        } else if (copiedText.equalsIgnoreCase(medicationListName)) {
                            m_assert.assertWarn(true,
                                    "Validate the Medication List <b>" + copiedText +
                                            "</b> is selected on entering <b>" + medicationListName +
                                            "</b> in the Templates");
                        } else {
                            m_assert.assertTrue(false,
                                    "Validate the Medication List <b>" + copiedText +
                                            "</b> is selected on entering <b>" + medicationListName +
                                            "</b> in the Templates");
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertTrue(false, "Error while selecting the medication list - " + medicationListName + "\n" + e);
                    }

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.button_close), "Close template button is clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 4);

                    if (Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.button_markPatientNotArrived)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_markPatientNotArrived),
                                "Click on the Not Arrived button");
                        Cls_Generic_Methods.customWait();
                        Cls_Generic_Methods.waitForPageLoad(driver, 4);
                    } else {
                        m_assert.assertTrue(false, "Not Arrived button for patient is not displayed");
                    }

                } else {
                    m_assert.assertTrue(false, "Patient" + concatPatientFullName + " was not found on the page");
                }

            } catch (Exception e) {
                m_assert.assertFatal(" Exception while validating the medication Set in Advice Tab on OPD. " + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Remove Patient created for Medication List")
    public void removePatientCreatedForValidateMedicationList() {

        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_FacilityContactList oPage_FacilityContactList = new Page_FacilityContactList(driver);
        Page_OtSetup oPage_OtSetup = new Page_OtSetup(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;

        boolean bPatientNameFound = false;

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OPD");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage.get(0), 8);

            try {
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, OPD_Data.tab_ALL),
                        "Validate " + OPD_Data.tab_ALL + " tab is selected");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage.get(0), 8);

                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                m_assert.assertTrue(bPatientNameFound, "Validate Patient  " + concatPatientFullName + " is clicked in " + OPD_Data.tab_ALL);

                if (bPatientNameFound) {

                    if (Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.button_markPatientNotArrived)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_markPatientNotArrived),
                                "Clicked on Not Arrived for patient " + concatPatientFullName);
                        Cls_Generic_Methods.waitForPageLoad(driver, 8);
                        Cls_Generic_Methods.customWait();
                    }

                    Cls_Generic_Methods.scrollToElementByAction(driver, oPage_OtSetup.button_cancelAppointment);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtSetup.button_cancelAppointment, 8);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OtSetup.button_cancelAppointment), "Clicked on Cancel button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityContactList.button_cancelAppointmentButtonInForm, 8);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FacilityContactList.button_cancelAppointmentButtonInForm), "Appointment cancelled");
                    Cls_Generic_Methods.customWait(5);
                } else {
                    m_assert.assertTrue(false, "Patient " + concatPatientFullName + " not found");
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while validating patient in Not arrived tab" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Exception while cancelling appointment" + e);
        }
    }

    @Test(enabled = true, description = "Delete the created Medication List")
    public void deleteCreatedMedicationList() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_FacilityMedicationLists oPage_FacilityMedicationLists = new Page_FacilityMedicationLists(driver);
        boolean bValidateMedicationListFound = false;
        int iMedicationListIndex = -1;

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(1);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility Medication Lists");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationLists.button_CreateMedicationLists, 8);

                for (WebElement eMedicationSetName : oPage_FacilityMedicationLists.list_rowsMedicationNameOnMedicineLists) {
                    if (Cls_Generic_Methods.getTextInElement(eMedicationSetName).contains(medicationListName)) {
                        iMedicationListIndex = oPage_FacilityMedicationLists.list_rowsMedicationNameOnMedicineLists.indexOf(eMedicationSetName);
                        bValidateMedicationListFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FacilityMedicationLists.list_ButtonForDeleteMedicineLists.get(iMedicationListIndex)),
                        "Clicked on the Delete button for " +
                                Cls_Generic_Methods.getTextInElement(oPage_FacilityMedicationLists.list_rowsMedicationNameOnMedicineLists.get(iMedicationListIndex)));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationLists.button_ConfirmDeleteMedication, 8);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FacilityMedicationLists.button_ConfirmDeleteMedication),
                        "Validate the Medication list is deleted.");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityMedicationLists.button_CreateMedicationLists, 8);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    public boolean fillMedicineClassAndValidate(String sMedicationName) {

        boolean medicationClassAdded = false;
        try {
            Page_FacilityMedicationLists oPage_FacilityMedicationLists = new Page_FacilityMedicationLists(driver);
            Cls_Generic_Methods.clearValuesInElement(oPage_FacilityMedicationLists.input_MedicationClass);
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityMedicationLists.input_MedicationClass, sMedicationName),
                    "Entered Medication List Name = <b>" + sMedicationName + "</b>");
            Cls_Generic_Methods.customWait(1);

            for (WebElement eItemMedicineClass : oPage_FacilityMedicationLists.list_medicineClassNameList) {
                if (Cls_Generic_Methods.getTextInElement(eItemMedicineClass).trim().equalsIgnoreCase(sMedicationName)) {
                    Cls_Generic_Methods.clickElement(eItemMedicineClass);
                    medicationClassAdded = true;
                    Cls_Generic_Methods.customWait(1);
                    break;
                }
            }

            for (WebElement eItemMedicineClass : oPage_FacilityMedicationLists.list_textMedicineClassName) {
                if (Cls_Generic_Methods.getTextInElement(eItemMedicineClass).trim().equalsIgnoreCase(sMedicationName)) {
                    medicationClassAdded = true;
                    break;
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
        return medicationClassAdded;
    }

    public static boolean fillGenericCompositionAndValidate(String sGenericName, int genericMedCompositionQuantity, String sMedUnit) {

        Page_FacilityMedicationLists oPage_FacilityMedicationLists = new Page_FacilityMedicationLists(driver);
        boolean medicationGenericNameAdded = false;

        try {
            int medArrayItemCounter = listMedGenericNames.indexOf(sGenericName);
            int iItemsCount = oPage_FacilityMedicationLists.list_ItemsForGenericNames.size();
            iItemsCount = iItemsCount - 1;

            System.out.println("medArrayItemCounter = " + medArrayItemCounter + "\t iItemsCount = " + iItemsCount);
            if (medArrayItemCounter != iItemsCount) {
                Cls_Generic_Methods.clickElement(oPage_FacilityMedicationLists.button_AddGenericComposition);
            }

            iItemsCount = oPage_FacilityMedicationLists.list_ItemsForGenericNames.size();
            iItemsCount = iItemsCount - 1;

            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_FacilityMedicationLists.list_InputForItemNameOnGenericComposition.get(iItemsCount), sGenericName),
                    "Entered Generic Name = <b>" + sGenericName + "</b>");

            Cls_Generic_Methods.customWait(standardWaitTimeInSecs);
            new Actions(driver).sendKeys(Keys.ARROW_DOWN).perform();
            Cls_Generic_Methods.customWait(1);
            new Actions(driver).sendKeys(Keys.RETURN).perform();
            Cls_Generic_Methods.customWait(1);


            Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityMedicationLists.list_InputForItemQuantityOnGenericComposition.get(iItemsCount),
                    String.valueOf(genericMedCompositionQuantity));
            Cls_Generic_Methods.customWait(1);

            Cls_Generic_Methods.selectElementByVisibleText(oPage_FacilityMedicationLists.list_SelectForItemUnitOnGenericComposition.get(iItemsCount),
                    sMedUnit);

            for (WebElement eItemGenericName : oPage_FacilityMedicationLists.list_InputForItemNameOnGenericComposition) {
                if (Cls_Generic_Methods.getElementAttribute(eItemGenericName, "value").equalsIgnoreCase(sGenericName)) {
                    medicationGenericNameAdded = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
        return medicationGenericNameAdded;
    }

}