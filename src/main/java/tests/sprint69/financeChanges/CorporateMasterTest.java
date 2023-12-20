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

public class CorporateMasterTest extends TestBase {
    EHR_Data oEHR_Data = new EHR_Data();
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date date = new Date();
    String date1 = dateFormat.format(date);
    String sCorporateName = "TestAutoCorporateName" + date1;
    String sCorporateDescription = "TestAutoCorporateDescription" + date1;
    String sUpdatedCorporateName = "UpdatedTestAutoCorporateName" + date1;
    String sUpdatedCorporateDescription = "UpdatedTestAutoCorporateDescription" + date1;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    static Model_Patient myPatient;

    //corporate master
    @Test(enabled = true, description = "Validate Adding New Corporate ")
    public void validateAddingNewCorporateFromMaster() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Corporate Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addCorporate, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addCorporate),
                        "Add Corporate button clicked ");

                //adding new Corporate
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.text_addCorporateLabel, 20);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_FinanceChanges.text_addCorporateLabel),
                        "Add Corporate pop opened");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_corporateName, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_corporateName, sCorporateName), "Entered Corporate name  =  <b> " + sCorporateName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_corporateDescription, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_corporateDescription, sCorporateDescription), "Entered Corporate Description  =  <b> " + sCorporateDescription + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_saveChanges, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_saveChanges),
                        "Save changes button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addCorporate, 20);
            } catch (Exception e) {
                m_assert.assertFatal("Corporate not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Newly Created Corporate Name Found Under Corporate Dropdown Under Billing section ")
    public void validateCreatedCorporateNameFoundUnderCorporateDropdown() {

        try {
            myPatient = TestBase.map_PatientsInExcel.get(patientKey);
            Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
            Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
            Page_Bills oPage_Bills = new Page_Bills(driver);
            Page_OPD oPage_OPD = new Page_OPD(driver);
            String sSelectNewBill = "New Bill";
            String sDepartmentName = "OPD";
            String sInsuranceContactType = "Insurance";
            String sTpaContactType = "TPA";
            String sInsuranceContact = "TESTING_PAYER";
            String sTpaContact = "TPA contact";
            boolean bCorporateNameFound = false;


            try {
                String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
                CommonActions.loginFunctionality(expectedLoggedInUser);
                CommonActions.selectDepartmentOnApp(sDepartmentName);
                Cls_Generic_Methods.customWait(3);
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
                //Validate corporate name found against insurance
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickBills),
                        "<b>₹ Bills</b> Button is clicked");
                Cls_Generic_Methods.customWait(3);
                try {
                    m_assert.assertTrue(selectOptionFromBillsList(oPage_Bills.list_billTypeSelection, sSelectNewBill),
                            "Validate " + sSelectNewBill + " bill is selected");
                    Cls_Generic_Methods.customWait(3);
                } catch (Exception e) {
                    m_assert.assertTrue(false, "Bill type is not selected" + e);
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.radioButton_creditType, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.radioButton_creditType);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.select_payerType, 20);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_payerType, sInsuranceContactType),
                        "<b>Selected Contact type = <b>" + sInsuranceContactType + "</b>");
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactUnderBills, sInsuranceContact),
                        "<b>Selected Insurance Contact = <b>" + sInsuranceContact + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.field_corporateDropdown, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.field_corporateDropdown);
                Cls_Generic_Methods.customWait(3);
                //validate Corporate Name under billing section
                for (WebElement eCorporateName : oPage_FinanceChanges.list_corporateNameUnderBills) {
                    if (eCorporateName.getText().equalsIgnoreCase(sCorporateName)) {
                        bCorporateNameFound = true;
                        Cls_Generic_Methods.clickElement(eCorporateName);
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }
                if (bCorporateNameFound) {
                    m_assert.assertTrue(true, "Corporate Name Found in table <b> " + sCorporateName + " </b> ");
                } else {
                    m_assert.assertTrue(false, "Incorrect Corporate Name Found in table");
                }
                Cls_Generic_Methods.customWait(4);

                //Validate corporate name found against TPA
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.select_payerType, 20);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_payerType, sTpaContactType),
                        "<b>Selected Contact type = <b>" + sTpaContactType + "</b>");
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactUnderBills, sTpaContact),
                        "<b>Selected Contact = <b>" + sTpaContact + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.field_corporateDropdown, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.field_corporateDropdown);
                Cls_Generic_Methods.customWait();
                //validate Corporate Name under billing section
                for (WebElement eCorporateName : oPage_FinanceChanges.list_corporateNameUnderBills) {
                    if (eCorporateName.getText().equalsIgnoreCase(sCorporateName)) {
                        bCorporateNameFound = true;
                        Cls_Generic_Methods.clickElement(eCorporateName);
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }
                if (bCorporateNameFound) {
                    m_assert.assertTrue(true, "Corporate Name Found in table <b> " + sCorporateName + " </b> ");
                } else {
                    m_assert.assertTrue(false, "Incorrect Corporate Name Found in table");
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


    @Test(enabled = true, description = "Validate Edit And Disable Corporate Functionality")
    public void validateEditAndDisableCorporateMaster() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        boolean bCorporateNameFoundInTable = false;
        boolean bCorporateDescriptionFoundInTable = false;
        int indexOfCorporateName = -1;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Corporate Master");
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addCorporate, 20);
                //validate Corporate name in table
                for (WebElement eCorporateName : oPage_FinanceChanges.list_corporateName) {
                    if (eCorporateName.getText().equalsIgnoreCase(sCorporateName)) {
                        bCorporateNameFoundInTable = true;
                        indexOfCorporateName = oPage_FinanceChanges.list_corporateName.indexOf(eCorporateName);
                        break;
                    }
                }
                m_assert.assertTrue(bCorporateNameFoundInTable, "Corporate Name Found in table <b> " + sCorporateName + " </b> ");
                Cls_Generic_Methods.customWait(3);
                //Validate Corporate description
                String sCorporateDescriptionOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_corporateDescription.get(indexOfCorporateName));
                if (sCorporateDescriptionOnUI.equalsIgnoreCase(sCorporateDescription)) {
                    bCorporateDescriptionFoundInTable = true;
                }
                m_assert.assertTrue(bCorporateDescriptionFoundInTable, "Corporate Description Found in table <b> " + sCorporateDescriptionOnUI + " </b> ");
                Cls_Generic_Methods.customWait();
                //validate edit Corporate functionality
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_editCorporateButton.get(indexOfCorporateName));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_corporateName, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.inputField_corporateName), "Cleared values in Corporate name field");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_corporateName, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_corporateName, sUpdatedCorporateName), "Entered Corporate name  =  <b> " + sUpdatedCorporateName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_corporateDescription, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.inputField_corporateDescription), "Cleared values in Corporate description field");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_corporateDescription, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_corporateDescription, sUpdatedCorporateDescription), "Entered Corporate Description  =  <b> " + sUpdatedCorporateDescription + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_saveChanges, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_saveChanges),
                        "Save changes button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addCorporate, 20);

                //validate Updated Corporate Name in table
                for (WebElement eCorporateName : oPage_FinanceChanges.list_corporateName) {
                    if (eCorporateName.getText().equalsIgnoreCase(sUpdatedCorporateName)) {
                        bCorporateNameFoundInTable = true;
                        indexOfCorporateName = oPage_FinanceChanges.list_corporateName.indexOf(eCorporateName);
                        break;
                    }
                }
                m_assert.assertTrue(bCorporateNameFoundInTable, "Corporate Name Found in table <b> " + sUpdatedCorporateName + " </b> ");

                //Validate updated Corporate description in table
                String sUpdatedCorporateDescriptionOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_corporateDescription.get(indexOfCorporateName));
                if (sUpdatedCorporateDescriptionOnUI.equalsIgnoreCase(sUpdatedCorporateDescription)) {
                    bCorporateDescriptionFoundInTable = true;
                }
                m_assert.assertTrue(bCorporateDescriptionFoundInTable, "Updated corporate Description Found in table <b> " + sUpdatedCorporateDescriptionOnUI + " </b> ");

                //Validate Disable Corporate functionality
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addCorporate, 20);
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_disableAndActiveCorporateButton.get(indexOfCorporateName));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_confirm, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_confirm),
                        "Confirm button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addCorporate, 10);

            } catch (Exception e) {
                m_assert.assertFatal("Corporate is not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Activating Corporate Functionality")
    public void validateActiveCorporateMaster() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        boolean bCorporateNameFoundInTable = false;
        boolean bCorporateDescriptionFoundInTable = false;
        int indexOfCoporateName = -1;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Corporate Master");
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addCorporate, 20);

                //validate Corporate Name in table
                for (WebElement eCorporateName : oPage_FinanceChanges.list_corporateName) {
                    if (eCorporateName.getText().equalsIgnoreCase(sUpdatedCorporateName)) {
                        bCorporateNameFoundInTable = true;
                        indexOfCoporateName = oPage_FinanceChanges.list_corporateName.indexOf(eCorporateName);
                        break;
                    }
                }
                m_assert.assertTrue(bCorporateNameFoundInTable, "Corporate Name Found in table <b> " + sUpdatedCorporateName + " </b> ");

                //Validate description in table
                String sUpdatedCorporateDescriptionOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_corporateDescription.get(indexOfCoporateName));
                if (sUpdatedCorporateDescriptionOnUI.equalsIgnoreCase(sUpdatedCorporateDescription)) {
                    bCorporateDescriptionFoundInTable = true;
                }
                m_assert.assertTrue(bCorporateDescriptionFoundInTable, "Updated Corporate Description Found in table <b> " + sUpdatedCorporateDescriptionOnUI + " </b> ");

                //Validate Activate Corporate functionality
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addCorporate, 20);
                String sButtonName = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_disableAndActiveCorporateButton.get(indexOfCoporateName));
                if (sButtonName.equalsIgnoreCase("Active")) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_disableAndActiveCorporateButton.get(indexOfCoporateName)), "Clicked on <b> Active </b> button");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addCorporate, 20);

            } catch (Exception e) {
                m_assert.assertFatal("Corporate is not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Updated Corporate Name Found Under Corporate Dropdown Under Billing section ")
    public void validateUpdatedCorporateNameFoundUnderCorporateDropdown() {

        try {
            myPatient = TestBase.map_PatientsInExcel.get(patientKey);
            Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
            Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
            Page_Bills oPage_Bills = new Page_Bills(driver);
            Page_OPD oPage_OPD = new Page_OPD(driver);
            String sSelectNewBill = "New Draft Bill";
            String sDepartmentName = "OPD";
            String sInsuranceContactType = "Insurance";
            String sTpaContactType = "TPA";
            String sInsuranceContact = "TESTING_PAYER";
            String sTpaContact = "TPA contact";
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
                //Validate corporate name found against insurance
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickBills),
                        "<b>₹ Bills</b> Button is clicked");
                Cls_Generic_Methods.customWait(3);
                try {
                    m_assert.assertTrue(selectOptionFromBillsList(oPage_Bills.list_billTypeSelection, sSelectNewBill),
                            "Validate " + sSelectNewBill + " bill is selected");
                    Cls_Generic_Methods.customWait();
                } catch (Exception e) {
                    m_assert.assertTrue(false, "Bill type is not selected" + e);
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.radioButton_creditType, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.radioButton_creditType);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.select_payerType, 20);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_payerType, sInsuranceContactType),
                        "<b>Selected Contact type = <b>" + sInsuranceContactType + "</b>");
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactUnderBills, sInsuranceContact),
                        "<b>Selected Insurance Contact = <b>" + sInsuranceContact + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.field_corporateDropdown, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.field_corporateDropdown);
                Cls_Generic_Methods.customWait(3);
                //validate Corporate Name under billing section
                for (WebElement eCorporateName : oPage_FinanceChanges.list_corporateNameUnderBills) {
                    if (eCorporateName.getText().equalsIgnoreCase(sUpdatedCorporateName)) {
                        bCorporateNameFound = true;
                        Cls_Generic_Methods.clickElement(eCorporateName);
                        break;
                    }
                }
                if (bCorporateNameFound) {
                    m_assert.assertTrue(true, "Corporate Name Found in table <b> " + sUpdatedCorporateName + " </b> ");
                } else {
                    m_assert.assertTrue(false, "Incorrect Corporate Name Found in table");
                }
                Cls_Generic_Methods.customWait(4);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_payerType, sTpaContactType),
                        "<b>Selected Contact type = <b>" + sTpaContactType + "</b>");
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactUnderBills, sTpaContact),
                        "<b>Selected Contact = <b>" + sTpaContact + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.field_corporateDropdown, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.field_corporateDropdown);
                Cls_Generic_Methods.customWait(3);
                //validate Updated Corporate Name under billing section
                for (WebElement eCorporateName : oPage_FinanceChanges.list_corporateNameUnderBills) {
                    if (eCorporateName.getText().equalsIgnoreCase(sUpdatedCorporateName)) {
                        bCorporateNameFound = true;
                        Cls_Generic_Methods.clickElement(eCorporateName);
                        break;
                    }
                }
                if (bCorporateNameFound) {
                    m_assert.assertTrue(true, "Updated Corporate Name Found in table <b> " + sUpdatedCorporateName + " </b> ");
                } else {
                    m_assert.assertTrue(false, "Incorrect Corporate Name Found in table");
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
