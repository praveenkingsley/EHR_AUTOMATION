package tests.settings.facilitySetting.clinical;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.OPD_Data;
import data.Settings_Data;
import data.settingsData.FacilitySettings_Data;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.common_tabs.advice.Page_AdviceTab;
import pages.commonElements.common_tabs.Page_RefractionTab;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;
import pages.settings.facilitySettings.clinical.facilityAdviceSet.Page_FacilityAdviceSet;
import pages.settings.facilitySettings.general.facilitySetup.Page_FacilitySetup;
import pages.settings.facilitySettings.general.wardSetup.Page_WardSetup;

public class AdviceSetTest extends TestBase {

    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();

    @Test(enabled = true, description = "Validate Patient for Advice Set")
    public void createPatientToValidateAdviceSet() {
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_WardSetup oPage_WardSetUp = new Page_WardSetup(driver);
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);

        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(expectedLoggedInUser);

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

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_middleNameOnPatientRegForm, myPatient.getsMIDDLE_NAME()), "Middle Name is entered as - " + myPatient.getsMIDDLE_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_lastNameOnPatientRegForm, myPatient.getsLAST_NAME()), "Last Name is entered as - " + myPatient.getsLAST_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, myPatient.getsMOBILE_NUMBER()), "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm), "Validate that Create Appointment button is clicked");

             //   m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_WardSetUp.tab_appointmentDetails, 20), "Wait until appointment details is displayed ");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20), "Patient details displayed ");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Add and Validate new Advice Set")
    public void validateAddNewAdviceSet() throws Exception {
        // fill the details and save
        Page_FacilityAdviceSet oPage_FacilityAdviceSet = new Page_FacilityAdviceSet(driver);
        boolean bSpecialitySelected = false;
        boolean bLanguageSelected = false;
        boolean bAdviceSetFound = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            Cls_Generic_Methods.customWait(5);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.customWait(5);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();

            try {
                //get ward settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility Advice Sets");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityAdviceSet.button_adviceSet, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_FacilityAdviceSet.button_adviceSet),
                        "Advice Set Button clicked");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityAdviceSet.header_adviceSet, 5);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityAdviceSet.input_adviceSetName, FacilitySettings_Data.sADVICE_SET_NAME)
                        , "Advice Set Name entered: <b> " + FacilitySettings_Data.sADVICE_SET_NAME + " </b>");

                //select speciality from the list
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FacilityAdviceSet.dropdown_selectSpeciality), "Select Speciality dropdown Clicked");

                for (WebElement listSpecialityName : oPage_FacilityAdviceSet.list_dropdownSpeciality) {
                    if (listSpecialityName.isDisplayed()) {
                        if (Cls_Generic_Methods.getTextInElement(listSpecialityName).equalsIgnoreCase(FacilitySettings_Data.sADVICE_SPECIALITY_NAME)) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, listSpecialityName), "Speciality selected from dropdown <b> " + listSpecialityName.getText() + " </b> ");
                            bSpecialitySelected = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bSpecialitySelected, "Speciality Selected");

                //select language from the list
                for (WebElement listLanguageElement : oPage_FacilityAdviceSet.list_LanguageSet) {
                    if (listLanguageElement.isDisplayed()) {
                        if (Cls_Generic_Methods.getTextInElement(listLanguageElement).equalsIgnoreCase(FacilitySettings_Data.sADVICE_LANGUAGE)) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, listLanguageElement), "Language selected from list <b> " + listLanguageElement.getText() + " </b> ");
                            bLanguageSelected = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bLanguageSelected, "Language Selected");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityAdviceSet.input_adviceSetContent, 3);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityAdviceSet.input_adviceSetContent, FacilitySettings_Data.sADVICE_SET_CONTENT),
                        "Advice Set Content entered: <b> " + FacilitySettings_Data.sADVICE_SET_CONTENT + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FacilityAdviceSet.button_createAdviceSet),
                        "Create Advice Set button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityAdviceSet.button_adviceSet, 5);
                //Find the created set in the table
                for (WebElement eRowAdviceSetName : oPage_FacilityAdviceSet.list_adviceSetsName) {
                    if (eRowAdviceSetName.isDisplayed()) {
                        if (Cls_Generic_Methods.getTextInElement(eRowAdviceSetName).equalsIgnoreCase(FacilitySettings_Data.sADVICE_SET_NAME)) {
                            m_assert.assertTrue(true, "Created Advice Set found in the tables: <b> " + eRowAdviceSetName.getText() + " </b> ");
                            bAdviceSetFound = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bAdviceSetFound, "Advice Set Found");


            } catch (Exception e) {
                m_assert.assertFatal("Error loading the application " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate the advice set in template ")
    public void validateAdviceSetInTemplate() throws Exception {
        // go under advice tab,   created advice set should be visible
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = "";
        boolean bPatientNameFound = false;
        boolean adviceSetFound = false;
        String AllTab = OPD_Data.tab_ALL;
        String PostOpTemplate = "Post OP";
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_FacilitySetup oPage_FacilitySetup = new Page_FacilitySetup(driver);
        Page_RefractionTab oPage_RefractionTab = new Page_RefractionTab(driver);
        Page_AdviceTab oPage_AdviceTab = new Page_AdviceTab(driver);
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                CommonActions.selectDepartmentOnApp("OPD");

                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, AllTab),
                        "Validate " + AllTab + " tab is selected");
                Cls_Generic_Methods.customWait(1);
                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

                if (bPatientNameFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

                    //m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientArrived),
                         //   "Mark Patient arrived clicked ");
                   // Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);
                    //open postop template
                    try {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
                        m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, PostOpTemplate), "Validate " + PostOpTemplate + " template  is selected");

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.tab_RefractionTab, 20);

                        m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FacilitySetup.checkbox_rightVision),
                                "VA not examined for right clicked ");
                        m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FacilitySetup.checkbox_leftVision),
                                "VA not examined for left clicked ");

                        Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_rightIop);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_rightIop, myPatient.getsIOP_RIGHT()), "Right Eye intraocularPressure is " + myPatient.getsIOP_RIGHT());

                        Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_leftIop);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_leftIop, myPatient.getsIOP_LEFT()), "Left Eye intraocularPressure is " + myPatient.getsIOP_RIGHT());

                        //check advice set under advice tab
                        try {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.tab_advice), "Advice Tab Is selected");
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.tab_adviceUnderAdviceTab), "Advice Tab under advice Is selected");

                            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_AdviceTab.dropdown_advice),
                                    "Advice dropdown clicked ");

                            for (WebElement e : oPage_AdviceTab.list_adviceSetDropdown) {
                                if (Cls_Generic_Methods.getTextInElement(e).contains(FacilitySettings_Data.sADVICE_SET_NAME)) {
                                    adviceSetFound = true;
                                    break;
                                }
                            }
                            m_assert.assertTrue(adviceSetFound, "Advice set found in template under advice tab");
                            Cls_Generic_Methods.customWait();
                            Cls_Generic_Methods.scrollToTop();
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_closeTemplateWithoutSaving), "Close template button is clicked");

                        } catch (Exception e) {
                            m_assert.assertFatal("Advice Set not found in template " + e);
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        m_assert.assertFatal("Error while opening template " + e);
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                m_assert.assertFatal("Error while getting patient" + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Not Loading application" + e);
            e.printStackTrace();
        }

    }


    @Test(enabled = true, description = "Validate Searching and Editing the added advice set")
    public void validateSearchAndEditAdviceSet() {
        Page_FacilityAdviceSet oPage_FacilityAdviceSet = new Page_FacilityAdviceSet(driver);
        int indexOfEditButton = -1;
        int indexOfAdviceSet = -1;
        String editAdviceSetName = "Automation advice set updated";
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                //get advice set settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility Advice Sets");

                //Search advice set
                Cls_Generic_Methods.clickElementByJS(driver, oPage_FacilityAdviceSet.input_searchAdviceSet);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityAdviceSet.input_searchAdviceSet, FacilitySettings_Data.sADVICE_SET_NAME);
                Cls_Generic_Methods.customWait(5);

                //Find the created advice set on the table
                for (WebElement eRowAdviceSetName : oPage_FacilityAdviceSet.list_adviceSetsName) {
                    if (eRowAdviceSetName.isDisplayed()) {
                        if (Cls_Generic_Methods.getTextInElement(eRowAdviceSetName).equalsIgnoreCase(FacilitySettings_Data.sADVICE_SET_NAME)) {
                            indexOfAdviceSet = oPage_FacilityAdviceSet.list_adviceSetsName.indexOf(eRowAdviceSetName);
                            m_assert.assertTrue(true, "Advice Set found to be search and edit <b> " + eRowAdviceSetName.getText() + " </b>");
                            break;
                        }
                    }
                }

                if (indexOfAdviceSet < 0) {
                    m_assert.assertTrue(false, "<b>" + FacilitySettings_Data.sADVICE_SET_NAME + " </b> not found in Advice Set by Search");
                } else {
                    m_assert.assertTrue(true, "<b>" + FacilitySettings_Data.sADVICE_SET_NAME + " </b> found in Advice Set by Search");

                }

                //Find the respected edit button and edit content
                for (WebElement btn_edit : oPage_FacilityAdviceSet.list_adviceSetEditButton) {
                    if (btn_edit.isDisplayed()) {
                        indexOfEditButton = oPage_FacilityAdviceSet.list_adviceSetEditButton.indexOf(btn_edit);
                    }

                    if (indexOfAdviceSet == indexOfEditButton) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, btn_edit), "<b> Advice Set edit </b> button clicked ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityAdviceSet.button_updateAdviceSet, 5);

                        for (WebElement listLanguageElement : oPage_FacilityAdviceSet.list_LanguageSet) {
                            if (listLanguageElement.isDisplayed()) {
                                if (Cls_Generic_Methods.getTextInElement(listLanguageElement).equalsIgnoreCase(FacilitySettings_Data.sADVICE_LANGUAGE)) {
                                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, listLanguageElement), "Language selected from list <b> " + listLanguageElement.getText() + " </b> ");
                                    break;
                                }
                            }
                        }
                        Cls_Generic_Methods.customWait();
                        Cls_Generic_Methods.clearValuesInElement(oPage_FacilityAdviceSet.input_adviceSetName);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FacilityAdviceSet.input_adviceSetName, editAdviceSetName),
                                "Advice Set name updated: <b> " + editAdviceSetName + "</b>");

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FacilityAdviceSet.button_updateAdviceSet), "<b> Advice Set update </b> clicked ");
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityAdviceSet.button_adviceSet, 5);
                Cls_Generic_Methods.clearValuesInElement(oPage_FacilityAdviceSet.input_searchAdviceSet);

            } catch (Exception e) {
                m_assert.assertFatal("Advice Set edit button not found " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }


    @Test(enabled = true, description = "Validate Removing Patient")
    public void removePatientCreatedForAdviceSet() {
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = "";
        String NotArrivedTab = OPD_Data.tab_NOT_ARRIVED;
        String myQueueTab = OPD_Data.tab_MY_QUEUE;
        boolean bPatientFound = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OPD");
            try {
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();

                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, myQueueTab),
                        "Validate " + myQueueTab + " tab is selected");
                Cls_Generic_Methods.customWait(1);
                bPatientFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                if (bPatientFound) {


                    Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientNotArrived);
                    m_assert.assertTrue(
                            CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, NotArrivedTab),
                            "Validate " + NotArrivedTab + " tab is selected");
                    Cls_Generic_Methods.customWait(1);

                    CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                            10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_cancelAppointment),
                            "<b> Cancel Button </b> " + " clicked on page for patient <b> " + concatPatientFullName + " </b>");
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.header_cancelAppointment, 5),
                            "<b> Cancel appointment form displayed </b> ");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_cancelAppointmentForm),
                            "Appointment cancel button clicked for patient <b> " + concatPatientFullName + " </b> ");
                    Cls_Generic_Methods.waitForPageLoad(driver, 5);
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while deleting patient" + e);
            }
        } catch (Exception e) {
            m_assert.assertTrue(false, "Exception while running application" + e);
            e.printStackTrace();
        }
    }


    @Test(enabled = true, description = "Validate deleting the created advice set")
    public void deleteCreatedAdviceSet() {
        Page_FacilityAdviceSet oPage_FacilityAdviceSet = new Page_FacilityAdviceSet(driver);
        int indexOfDeleteButton = -1;
        int indexOfAdviceSet = -1;
        boolean bDeleteButtonFound = false;
        boolean bAdviceSetFound = false;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                //get advice set settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Facility Advice Sets");

                //Find the created advice set on the table
                for (WebElement eRowAdviceSetName : oPage_FacilityAdviceSet.list_adviceSetsName) {
                    if (eRowAdviceSetName.isDisplayed()) {
                        if (Cls_Generic_Methods.getTextInElement(eRowAdviceSetName).contains(FacilitySettings_Data.sADVICE_SET_NAME)) {
                            indexOfAdviceSet = oPage_FacilityAdviceSet.list_adviceSetsName.indexOf(eRowAdviceSetName);
                            m_assert.assertTrue(true, "Advice Set found to be deleted <b> " + eRowAdviceSetName.getText() + " </b>");
                            bAdviceSetFound = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bAdviceSetFound, "Advice Set Status <b> " + bAdviceSetFound + "</b> ");

                //Find the respected delete button and delete
                for (WebElement btn_del : oPage_FacilityAdviceSet.list_adviceSetDeleteButton) {
                    if (btn_del.isDisplayed()) {
                        indexOfDeleteButton = oPage_FacilityAdviceSet.list_adviceSetDeleteButton.indexOf(btn_del);
                    }

                    if (indexOfAdviceSet == indexOfDeleteButton) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, btn_del), "<b> Advice Set delete </b> button clicked ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FacilityAdviceSet.button_confirmDeleteAdviceSet, 5);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FacilityAdviceSet.button_confirmDeleteAdviceSet), "<b> Advice Set delete </b> confirmation clicked ");
                        Cls_Generic_Methods.customWait(3);
                        bDeleteButtonFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(bDeleteButtonFound, "Delete button status <b> " + bDeleteButtonFound + " </b> for the advice set");

            } catch (Exception e) {
                m_assert.assertFatal("Advice Set delete button not found " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }
}

