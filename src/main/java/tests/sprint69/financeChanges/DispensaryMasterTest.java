package tests.sprint69.financeChanges;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.bills.Page_Bills;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;
import pages.sprint69.financeChanges.Page_FinanceChanges;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DispensaryMasterTest extends TestBase {
    EHR_Data oEHR_Data = new EHR_Data();
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date date = new Date();
    String date1 = dateFormat.format(date);
    String sDispensaryName = "TestAutoDispensaryName" + date1;
    String sDispensaryDescription = "TestAutoDispensaryDescription" + date1;
    String sUpdatedDispensaryName = "UpdatedTestAutoDispensaryName" + date1;
    String sUpdatedDispensaryDescription = "UpdatedTestAutoDispensaryDescription" + date1;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    static Model_Patient myPatient;

    //Dispensary Master
    @Test(enabled = true, description = "Validate Adding New Dispensary")
    public void validateAddingNewDispensaryFromMaster() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectFacility("TST");
            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Dispensary Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addDispensary, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addDispensary),
                        "Add Dispensary button clicked ");
                Cls_Generic_Methods.customWait(3);
                //adding new Dispensary
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.text_addDispensaryLabel, 20);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_FinanceChanges.text_addDispensaryLabel),
                        "Add Dispensary pop opened");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_dispensaryName, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_dispensaryName, sDispensaryName), "Entered Dispensary name  =  <b> " + sDispensaryName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_dispensaryDescription, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_dispensaryDescription, sDispensaryDescription), "Entered Dispensary Description  =  <b> " + sDispensaryDescription + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_saveChanges, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_saveChanges),
                        "Save changes button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addDispensary, 20);


            } catch (Exception e) {
                m_assert.assertFatal("Dispensary not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Newly Created Corporate Name Found Under Dispensary Dropdown Under Billing section ")
    public void validateCreatedCorporateNameFoundUnderDispensaryDropdown() {

        try {
            myPatient = TestBase.map_PatientsInExcel.get(patientKey);
            Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
            Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
            Page_Bills oPage_Bills = new Page_Bills(driver);
            Page_OPD oPage_OPD = new Page_OPD(driver);
            String sSelectNewBill = "New Bill";
            String sDepartmentName = "OPD";
            String sPanelContactType = "Panel";
            String sPanelContact = "PANEL TEST";
            boolean bCorporateNameFound = false;


            try {
                String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
                CommonActions.loginFunctionality(expectedLoggedInUser);
                CommonActions.selectDepartmentOnApp(sDepartmentName);
                Cls_Generic_Methods.customWait(2);
                String tabToSelect = "All";
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
                        "Validate " + tabToSelect + " tab is selected");

                String patientName = null;

                for (WebElement patient : oPage_OPD.rows_patientAppointments) {
                    if (patient.isDisplayed()) {
                        List<WebElement> patientDetailsOnRow = patient.findElements(By.xpath("./child::*"));
                        patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");

                        if (concatPatientFullName.equals(patientName.trim())) {
                            Cls_Generic_Methods.clickElement(driver, patient);
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 8);
                            break;
                        }
                    }
                }
                //Validate dispensary name found against panel
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickBills),
                        "<b>₹ Bills</b> Button is clicked");
                Cls_Generic_Methods.customWait(3);
                try {
                    m_assert.assertTrue(selectOptionFromBillsList(oPage_Bills.list_billTypeSelection, sSelectNewBill),
                            "Validate " + sSelectNewBill + " bill is selected");
                    Cls_Generic_Methods.customWait(2);
                } catch (Exception e) {
                    m_assert.assertTrue(false, "Bill type is not selected" + e);
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.radioButton_creditType, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.radioButton_creditType);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.select_payerType, 20);
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_payerType, sPanelContactType),
                        "<b>Selected Contact type = <b>" + sPanelContactType + "</b>");
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactUnderBills, sPanelContact),
                        "<b>Selected Panel Contact = <b>" + sPanelContact + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.field_dispensaryDropdownUnderBillingSection, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.field_dispensaryDropdownUnderBillingSection);
                Cls_Generic_Methods.customWait(3);
                //validate Corporate Name under billing section
                for (WebElement eCorporateName : oPage_FinanceChanges.list_dispensaryNameUnderBills) {
                    if (eCorporateName.getText().equalsIgnoreCase(sDispensaryName)) {
                        bCorporateNameFound = true;
                        Cls_Generic_Methods.clickElement(eCorporateName);
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }
                if (bCorporateNameFound) {
                    m_assert.assertTrue(true, "Dispensary Name Found in table <b> " + sDispensaryName + " </b> ");
                } else {
                    m_assert.assertFalse(false, "Incorrect Dispensary Name Found in table");
                }
                Cls_Generic_Methods.customWait(2);
                Cls_Generic_Methods.driverRefresh();
            } catch (Exception e) {
                m_assert.assertFatal("Error loading the application " + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertTrue(false, "Validate bills failed" + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Edit and Disable Dispensary Functionality")
    public void validateEditAndDisableDispensaryMaster() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        boolean bDispensaryNameFoundInTable = false;
        boolean bDispensaryDescriptionFoundInTable = false;
        int indexOfDispensaryName = -1;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Dispensary Master");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addDispensary, 20);

                //validate Dispensary name in table
                for (WebElement eDispensaryName : oPage_FinanceChanges.list_dispensaryName) {
                    if (eDispensaryName.getText().equalsIgnoreCase(sDispensaryName)) {
                        bDispensaryNameFoundInTable = true;
                        indexOfDispensaryName = oPage_FinanceChanges.list_dispensaryName.indexOf(eDispensaryName);
                        break;
                    }
                }
                m_assert.assertTrue(bDispensaryNameFoundInTable, "Dispensary Name Found in table <b> " + sDispensaryName + " </b> ");
                Cls_Generic_Methods.customWait();

                //Validate Dispensary description
                String sDispensaryDescriptionOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_dispensaryDescription.get(indexOfDispensaryName));
                if (sDispensaryDescriptionOnUI.equalsIgnoreCase(sDispensaryDescription)) {
                    bDispensaryDescriptionFoundInTable = true;
                }
                m_assert.assertTrue(bDispensaryDescriptionFoundInTable, "Dispensary Description Found in table <b> " + sDispensaryDescriptionOnUI + " </b> ");

                //validate edit Dispensary functionality
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_editDispensaryButton.get(indexOfDispensaryName));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_dispensaryName, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.inputField_dispensaryName), "Cleared values in Dispensary name field");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_dispensaryName, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_dispensaryName, sUpdatedDispensaryName), "Entered Dispensary name  =  <b> " + sUpdatedDispensaryName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_dispensaryDescription, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.inputField_dispensaryDescription), "Cleared values in Dispensary description field");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_dispensaryDescription, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_dispensaryDescription, sUpdatedDispensaryDescription), "Entered dispensary Description  =  <b> " + sUpdatedDispensaryDescription + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_saveChanges, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_saveChanges),
                        "Save changes button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addDispensary, 20);

                //validate Updated Dispensary Name in table
                for (WebElement eDispensaryName : oPage_FinanceChanges.list_dispensaryName) {
                    if (eDispensaryName.getText().equalsIgnoreCase(sUpdatedDispensaryName)) {
                        bDispensaryNameFoundInTable = true;
                        indexOfDispensaryName = oPage_FinanceChanges.list_dispensaryName.indexOf(eDispensaryName);
                        break;
                    }
                }
                m_assert.assertTrue(bDispensaryNameFoundInTable, "Dispensary Name Found in table <b> " + sUpdatedDispensaryName + " </b> ");

                //Validate updated Dispensary description in table
                String sUpdatedDispensaryDescriptionOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_dispensaryDescription.get(indexOfDispensaryName));
                if (sUpdatedDispensaryDescriptionOnUI.equalsIgnoreCase(sUpdatedDispensaryDescription)) {
                    bDispensaryDescriptionFoundInTable = true;
                }
                m_assert.assertTrue(bDispensaryDescriptionFoundInTable, "Updated dispensary Description Found in table <b> " + sUpdatedDispensaryDescriptionOnUI + " </b> ");

                //Validate Disable Dispensary functionality
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addDispensary, 20);
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_disableAndActiveDispensaryButton.get(indexOfDispensaryName));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_confirm, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_confirm),
                        "Confirm button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addDispensary, 20);

            } catch (Exception e) {
                m_assert.assertFatal("Dispensary is not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Activating Dispensary functionality")
    public void validateActiveDispensaryMaster() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        boolean bDispensaryNameFoundInTable = false;
        boolean bDispensaryDescriptionFoundInTable = false;
        int indexOfDispensaryName = -1;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Dispensary Master");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addDispensary, 20);

                //validate Dispensary Name in table
                for (WebElement eDispensaryName : oPage_FinanceChanges.list_dispensaryName) {
                    if (eDispensaryName.getText().equalsIgnoreCase(sUpdatedDispensaryName)) {
                        bDispensaryNameFoundInTable = true;
                        indexOfDispensaryName = oPage_FinanceChanges.list_dispensaryName.indexOf(eDispensaryName);
                        break;
                    }
                }
                m_assert.assertTrue(bDispensaryNameFoundInTable, "Dispensary Name Found in table <b> " + sUpdatedDispensaryName + " </b> ");

                //Validate description in table
                String sUpdatedDispensaryDescriptionOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_dispensaryDescription.get(indexOfDispensaryName));
                if (sUpdatedDispensaryDescriptionOnUI.equalsIgnoreCase(sUpdatedDispensaryDescription)) {
                    bDispensaryDescriptionFoundInTable = true;
                }
                m_assert.assertTrue(bDispensaryDescriptionFoundInTable, "Updated Dispensary Description Found in table <b> " + sUpdatedDispensaryDescriptionOnUI + " </b> ");

                //Validate Activate Dispensary functionality
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addDispensary, 20);
                String sButtonName = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_disableAndActiveDispensaryButton.get(indexOfDispensaryName));
                if (sButtonName.equalsIgnoreCase("Active")) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_disableAndActiveDispensaryButton.get(indexOfDispensaryName)), "Clicked on <b> Active </b> button");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addDispensary, 20);

            } catch (Exception e) {
                m_assert.assertFatal("Dispensary is not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Updated Created Corporate Name Found Under Dispensary Dropdown Under Billing section ")
    public void validateUpdatedCorporateNameFoundUnderDispensaryDropdown() {
        try {
            myPatient = TestBase.map_PatientsInExcel.get(patientKey);
            Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
            Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
            Page_Bills oPage_Bills = new Page_Bills(driver);
            Page_OPD oPage_OPD = new Page_OPD(driver);
            String sSelectNewBill = "New Draft Bill";
            String sDepartmentName = "OPD";
            String sPanelContactType = "Panel";
            String sPanelContact = "PANEL TEST";
            boolean bCorporateNameFound = false;


            try {
                String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
                CommonActions.loginFunctionality(expectedLoggedInUser);
                CommonActions.selectDepartmentOnApp(sDepartmentName);
                Cls_Generic_Methods.customWait();
                String tabToSelect = "All";
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
                        "Validate " + tabToSelect + " tab is selected");

                String patientName = null;

                for (WebElement patient : oPage_OPD.rows_patientAppointments) {
                    if (patient.isDisplayed()) {
                        List<WebElement> patientDetailsOnRow = patient.findElements(By.xpath("./child::*"));
                        patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");

                        if (concatPatientFullName.equals(patientName.trim())) {
                            Cls_Generic_Methods.clickElement(driver, patient);
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 8);
                            break;
                        }
                    }
                }
                //Validate dispensary name found against panel
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickBills),
                        "<b>₹ Bills</b> Button is clicked");
                Cls_Generic_Methods.customWait(3);
                try {
                    m_assert.assertTrue(selectOptionFromBillsList(oPage_Bills.list_billTypeSelection, sSelectNewBill),
                            "Validate " + sSelectNewBill + " bill is selected");
                    Cls_Generic_Methods.customWait(2);
                } catch (Exception e) {
                    m_assert.assertTrue(false, "Bill type is not selected" + e);
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.select_payerType, 20);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_payerType, sPanelContactType),
                        "<b>Selected Contact type = <b>" + sPanelContactType + "</b>");
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactUnderBills, sPanelContact),
                        "<b>Selected Panel Contact = <b>" + sPanelContact + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.field_dispensaryDropdownUnderBillingSection, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.field_dispensaryDropdownUnderBillingSection);
                Cls_Generic_Methods.customWait();
                //validate Corporate Name under billing section
                for (WebElement eDispensaryName : oPage_FinanceChanges.list_dispensaryNameUnderBills) {
                    if (eDispensaryName.getText().equalsIgnoreCase(sUpdatedDispensaryName)) {
                        bCorporateNameFound = true;
                        Cls_Generic_Methods.clickElement(eDispensaryName);
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }
                if (bCorporateNameFound) {
                    m_assert.assertTrue(true, "Updated Dispensary Name Found in table <b> " + sUpdatedDispensaryName + " </b> ");
                } else {
                    m_assert.assertFalse(false, "Incorrect Dispensary Name Found in table");
                }
                Cls_Generic_Methods.customWait(2);
                Cls_Generic_Methods.driverRefresh();
            } catch (Exception e) {
                m_assert.assertFatal("Error loading the application " + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertTrue(false, "Validate bills failed" + e);
            e.printStackTrace();
        }
    }

    public boolean selectOptionFromBillsList(List<WebElement> listOfBillsElement, String nameOfBillToSelect) {

        boolean optionIsSelected = false;
        try {
            for (WebElement billElement : listOfBillsElement) {
                String billValue = null;
                billValue = Cls_Generic_Methods.getTextInElement(billElement);
                if (nameOfBillToSelect.equals(billValue)) {
                    Cls_Generic_Methods.clickElement(driver, billElement);
                    optionIsSelected = true;
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return optionIsSelected;
    }
}
