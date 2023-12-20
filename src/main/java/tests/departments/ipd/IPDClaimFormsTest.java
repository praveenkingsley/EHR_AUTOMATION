package tests.departments.ipd;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.IPD_Data;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.bills.Page_Bills;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.ipd.forms.Page_ClaimForms;

import java.util.List;

public class IPDClaimFormsTest extends TestBase {

    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();
    Page_IPD oPage_IPD;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_NewPatientRegisteration oPage_NewPatientRegisteration;
    Page_Bills oPage_Bills;
    static Page_ClaimForms oPage_ClaimForms;

    @Test(enabled = true, description = "Create patient in ipd for claim forms validation")
    public void scheduleAdmissionInIpdForClaimForm() {

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
                Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduleAdmission.radioBtn_cashlessYes);

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
                Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today),
                        "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");
                Cls_Generic_Methods.customWait(3);
                bPatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnScheduledTodayTab, concatPatientFullName);
                m_assert.assertTrue(bPatientFound, "Validate that the patient " + concatPatientFullName + " is created in IPD");

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

    @Test(enabled = true, description = "create cash bill and validate the claim form button be not displayed")
    public void createCashBillAndValidateClaimForms() {
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills = new Page_Bills(driver);
        oPage_ClaimForms = new Page_ClaimForms(driver);
        oPage_IPD = new Page_IPD(driver);
        boolean bPatientFound = false;
        String paymentMode = "Cash";
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectDepartmentOnApp("IPD");
            myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
            m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today),
                    "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");
            Cls_Generic_Methods.customWait(10);

            bPatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnScheduledTodayTab, concatPatientFullName);
            m_assert.assertTrue(bPatientFound, "Validate that the patient " + concatPatientFullName + " is created in IPD");

            if (bPatientFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills), "Clicked on bills button");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_cashBill);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 10);
                Cls_Generic_Methods.clickElement(oPage_Bills.select_services);
                Cls_Generic_Methods.customWait(4);
                oPage_Bills.input_billService.sendKeys(Keys.DOWN, Keys.ENTER);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.scrollToElementByJS(oPage_Bills.select_modeOfPayment);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_modeOfPayment, paymentMode),
                        paymentMode + " option is selected for Mode Of Payment");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills),
                        "<b>Cash bill is created </b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_closeBill);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_ClaimForms.button_claimForms),
                        "<b> Claim forms button not visible when created cash bill </b> ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }

    }

    @Test(enabled = true, description = "Create Draft bill and validate the claim form button be displayed")
    public void createDraftBillAndValidateClaimForms() {
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills = new Page_Bills(driver);
        oPage_ClaimForms = new Page_ClaimForms(driver);
        oPage_IPD = new Page_IPD(driver);
        boolean bPatientFound = false;

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectDepartmentOnApp("IPD");
            Cls_Generic_Methods.customWait(10);

            myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
            m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today),
                    "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");

            bPatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnScheduledTodayTab, concatPatientFullName);
            m_assert.assertTrue(bPatientFound, "Validate that the patient " + concatPatientFullName + " is created in IPD");

            if (bPatientFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills), "Clicked on bills button");
                Cls_Generic_Methods.customWait(5);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_draftBill);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 10);
                Cls_Generic_Methods.clickElement(oPage_Bills.select_services);
                Cls_Generic_Methods.customWait(3);
                oPage_Bills.input_billService.sendKeys(Keys.DOWN, Keys.ENTER);

                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToElementByJS(oPage_Bills.button_saveDraftBillButton);
                Cls_Generic_Methods.doubleClickElement(oPage_Bills.text_balanceRemainingAmount);
                Cls_Generic_Methods.copyContentToClipboardByAction();
                String sPendingBalance = Cls_Generic_Methods.getClipboardContent();

                Cls_Generic_Methods.clickElement(oPage_Bills.input_amountPending);
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.input_amountPending);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_amountPending, sPendingBalance);
                Cls_Generic_Methods.customWait(1);


                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Bills.button_saveDraftBillButton),
                        "Draft bill created");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_closeBill);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_ClaimForms.button_claimForms),
                        "<b> Claim forms button visible when created draft bill </b> ");


            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }
    }

    @Test(enabled = true, description = "Open all the claim forms one by one and validate its opening in other window")
    public void validateClaimForms() {
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills = new Page_Bills(driver);
        oPage_ClaimForms = new Page_ClaimForms(driver);
        oPage_IPD = new Page_IPD(driver);
        boolean bPatientFound = false;
        String formsType[] = {"TPA Covering Letter", "Panel Covering Letter", "Raksha TPA Covering Letter", "DGEHS Panel Document",
                "Essentiality Certificate", "GIPSA Declaration", "Claim Form Part-A", "Claim Form Part-B"};

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectDepartmentOnApp("IPD");
            Cls_Generic_Methods.customWait(10);

            myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
            m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today),
                    "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");

            bPatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnScheduledTodayTab, concatPatientFullName);
            m_assert.assertTrue(bPatientFound, "Validate that the patient " + concatPatientFullName + " is created in IPD");

            if (bPatientFound) {
                //validate claim forms
                Cls_Generic_Methods.clickElement(oPage_ClaimForms.button_claimForms);
                m_assert.assertTrue(openClaimForm(oPage_ClaimForms.list_typeOfClaimForms, formsType[0]),
                        "<b>" + formsType[0] + " Opened </b> ");

                Cls_Generic_Methods.clickElement(oPage_ClaimForms.button_claimForms);
                m_assert.assertTrue(openClaimForm(oPage_ClaimForms.list_typeOfClaimForms, formsType[1]),
                        "<b>" + formsType[1] + " Opened </b> ");

                Cls_Generic_Methods.clickElement(oPage_ClaimForms.button_claimForms);
                m_assert.assertTrue(openClaimForm(oPage_ClaimForms.list_typeOfClaimForms, formsType[2]),
                        "<b>" + formsType[2] + " Opened </b> ");

                Cls_Generic_Methods.clickElement(oPage_ClaimForms.button_claimForms);
                m_assert.assertTrue(openClaimForm(oPage_ClaimForms.list_typeOfClaimForms, formsType[3]),
                        "<b>" + formsType[3] + " Opened </b> ");

                Cls_Generic_Methods.clickElement(oPage_ClaimForms.button_claimForms);
                m_assert.assertTrue(openClaimForm(oPage_ClaimForms.list_typeOfClaimForms, formsType[4]),
                        "<b>" + formsType[4] + " Opened </b> ");

                Cls_Generic_Methods.clickElement(oPage_ClaimForms.button_claimForms);
                m_assert.assertTrue(openClaimForm(oPage_ClaimForms.list_typeOfClaimForms, formsType[5]),
                        "<b>" + formsType[5] + " Opened </b> ");

                Cls_Generic_Methods.clickElement(oPage_ClaimForms.button_claimForms);
                m_assert.assertTrue(openClaimForm(oPage_ClaimForms.list_typeOfClaimForms, formsType[6]),
                        "<b>" + formsType[6] + " Opened </b> ");

                Cls_Generic_Methods.clickElement(oPage_ClaimForms.button_claimForms);
                m_assert.assertTrue(openClaimForm(oPage_ClaimForms.list_typeOfClaimForms, formsType[7]),
                        "<b>" + formsType[7] + " Opened </b> ");

            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }
    }

    public static boolean openClaimForm(List<WebElement> claimForms, String formTye) {
        oPage_ClaimForms = new Page_ClaimForms(driver);
        boolean status = false;
        try {
            for (WebElement eClaimForms : claimForms) {
                int preWindowsCount = driver.getWindowHandles().size();
                String initialWindowHandle = driver.getWindowHandle();

                if (Cls_Generic_Methods.getTextInElement(eClaimForms).equalsIgnoreCase(formTye)) {
                    Cls_Generic_Methods.clickElement(eClaimForms);
                    status = true;
                    Cls_Generic_Methods.customWait(10);
                    int postWindowsCount = driver.getWindowHandles().size();
                    m_assert.assertTrue(postWindowsCount > preWindowsCount, "Validate New tab is opened when clicking on "+formTye);
                    // Closing the Second Window
                    for (String currentWindowHandle : driver.getWindowHandles()) {
                        if (!currentWindowHandle.equals(driver.getWindowHandle())) {
                            driver.switchTo().window(currentWindowHandle);
                        }
                    }
                    driver.close();
                    driver.switchTo().window(initialWindowHandle);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ClaimForms.button_claimForms, 16);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}