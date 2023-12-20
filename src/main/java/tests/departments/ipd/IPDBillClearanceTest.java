package tests.departments.ipd;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.IPD_Data;
import data.Settings_Data;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;

public class IPDBillClearanceTest extends TestBase {
    Page_IPD oPage_IPD;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_NewPatientRegisteration oPage_NewPatientRegisteration;

    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    static Model_Patient myPatient;
    EHR_Data oEHR_Data;
    Page_OrganisationSetup oPage_OrganisationSetup;


    @Test(enabled = true, description = "Create patient in ipd for bill clearance validation")
    public void scheduleAdmissionToIpdForBillClearance() {

        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        String sAddNewCase = "Add New Case";

        boolean bPatientFound = false;

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectDepartmentOnApp("IPD");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
            myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();

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

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, 12);

                // Entering Essential Form Data
                if (!myPatient.getsSALUTATION().isEmpty()) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()), "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
                }

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                        "First Name is entered as - " + myPatient.getsFIRST_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, myPatient.getsMOBILE_NUMBER()),
                        "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

                Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduleAdmission.radioBtn_emergency);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.button_selectCase, 8);
                Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_selectCase);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.button_allCasesDropDown, 8);

                Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_allCasesDropDown);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_ScheduleAdmission.select_allCasesDropDown, sAddNewCase);
                Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_allCasesDropDown);

                Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_createAdmission);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.header_assignBed, 15);

                Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_closeAdmissionForm);
                oPage_IPD = new Page_IPD(driver);
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today),
                        "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");
                Cls_Generic_Methods.customWait(10);

                bPatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnScheduledTodayTab, concatPatientFullName);
                m_assert.assertTrue(bPatientFound, "Validate that the patient " + concatPatientFullName + " is created in IPD");

                Cls_Generic_Methods.clickElement(oPage_IPD.button_readyForAdmission);
                Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_IPD.button_admitPatient, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_admitPatient),
                        "Admit Patient Button clicked ");

                Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_IPD.header_admissionForm, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_saveAdmissionForm),
                        "Admission Form Saved. ");
                Cls_Generic_Methods.customWait(8);
                Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Verify by enabling and disabling the bill clearance module accordingly perform actions ")
    public void validateBillClearanceSetting() {
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        String billClearanceText = "Enable Bill Clearance in IPD";
        boolean bModuleFound = false;
        String radioBtnValue = "";
        boolean billClearanceEnable = false;
        boolean billClearanceDisable = false;
        String originalWindowHandle = null;
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUsers, 16);
            try {
                Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_orgSettings);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.header_orgSettings, 5);

                CommonActions.selectOptionUnderOrgSettings("Finance Settings");
                bModuleFound = Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.label_billClearanceSetting).equalsIgnoreCase(billClearanceText);

                if (bModuleFound) {
                    //get the radio button status
                    for (WebElement radioButtonStatus : oPage_OrganisationSetup.radioBtn_billClearanceStatus) {
                        radioBtnValue = Cls_Generic_Methods.getElementAttribute(radioButtonStatus, "checked");
                        if (radioBtnValue != null) {
                            if (oPage_OrganisationSetup.radBtn_billClearanceYes.isSelected()) {
                                billClearanceEnable = true;
                            } else {
                                Cls_Generic_Methods.clickElementByAction(driver, oPage_OrganisationSetup.radBtn_billClearanceYes);
                                Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.button_saveOrgSetting);
                                Cls_Generic_Methods.customWait(5);
                                billClearanceEnable = true;
                            }
                        } else if (radioBtnValue == null) {
                            if (oPage_OrganisationSetup.radBtn_billClearanceNo.isSelected()) {
                                billClearanceDisable = true;
                            } else {
                                Cls_Generic_Methods.clickElementByAction(driver, oPage_OrganisationSetup.radBtn_billClearanceNo);
                                Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.button_saveOrgSetting);
                                // Cls_Generic_Methods.waitForPageLoad(driver, 20);
                                Cls_Generic_Methods.customWait(5);
                                billClearanceDisable = true;
                            }
                        }

                        if (billClearanceEnable) {
                            m_assert.assertTrue(billClearanceEnable, "Radio button Option = <b>" + "Yes" + "</b>");
                            originalWindowHandle = driver.getWindowHandle();
                            Cls_Generic_Methods.switchToNewTab(originalWindowHandle);
                            Cls_Generic_Methods.waitForPageLoad(driver, 8);
                            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
                            CommonActions.selectDepartmentOnApp("IPD");
                            // Cls_Generic_Methods.customWait(10);
                            boolean patientFound = statusOfPatient();
                            if (patientFound) {
                                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_IPD.button_billClearance),
                                        "Bill Clearance button is visible");
                                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_preOperativeSection),
                                        "Pre operative section is not displayed");

                                //validate by click bill clearance btn
                                Cls_Generic_Methods.clickElement(oPage_IPD.button_billClearance);
                                Cls_Generic_Methods.customWait();
                                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_IPD.button_dischargePatient),
                                        "Bill clearance btn is clicked as discharge patient button is displayed");
                                Cls_Generic_Methods.scrollToElementByJS(oPage_IPD.text_preOperativeSection);
                                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_preOperativeSection),
                                        "Pre operative section is displayed");
                                Cls_Generic_Methods.clickElement(oPage_IPD.button_undoBill);
                                billClearanceEnable = false;
                                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                            }

                        }
                        if (billClearanceDisable) {
                            m_assert.assertTrue(billClearanceDisable, "Radio button Option = <b>" + "No" + "</b>");
                            originalWindowHandle = driver.getWindowHandle();
                            Cls_Generic_Methods.switchToNewTab(originalWindowHandle);
                            Cls_Generic_Methods.waitForPageLoad(driver, 8);
                            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
                            CommonActions.selectDepartmentOnApp("IPD");
                            boolean patientFound = statusOfPatient();
                            if (patientFound) {
                                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_IPD.button_billClearance),
                                        "Bill Clearance button is not visible");

                                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_IPD.button_dischargePatient),
                                        "discharge patient button is displayed");
                                Cls_Generic_Methods.scrollToElementByJS(oPage_IPD.text_preOperativeSection);
                                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_preOperativeSection),
                                        "Pre operative section is displayed");
                                billClearanceDisable = false;
                                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                            }
                        }
                    }
                } else {
                    m_assert.assertTrue("Module not found");
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

    public boolean statusOfPatient() {
        String myQueueTab = "My Queue";
        boolean bPatientNameFound = false;
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_IPD = new Page_IPD(driver);
        try {

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
            myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();

            CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, myQueueTab);
            Cls_Generic_Methods.customWait(5);
            bPatientNameFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnMyQueueTab, concatPatientFullName);

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }
        return bPatientNameFound;
    }
}



