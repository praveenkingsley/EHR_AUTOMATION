package tests.departments.opd;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;

import data.EHR_Data;
import pages.commonElements.CommonActions;
import pages.commonElements.bills.Page_Bills;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;

public class CreateBillsTest extends TestBase {

    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_OPD oPage_OPD;
    Page_Bills oPage_Bills;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");

    EHR_Data oEHR_Data = new EHR_Data();
    static Model_Patient myPatient;
    public boolean createDraftBill=false;

    @Test(enabled = true, description = "Validate Bill creation")
    public void validateBills() throws Exception {

        try {
            myPatient = TestBase.map_PatientsInExcel.get(patientKey);
            oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
            oPage_Bills = new Page_Bills(driver);
            oPage_OPD = new Page_OPD(driver);
            String billOptionToSelect = "New Bill";

            if (!driver.getTitle().equals(oEHR_Data.sEXPECTED_TITLE)) {
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
            }
            String tabToSelect = "All";
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();

            m_assert.assertTrue(
                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
                    "Validate " + tabToSelect + " tab is selected");

            String patientName = null;
            Cls_Generic_Methods.customWait(5);
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

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickBills),
                    "<b>₹ Bills</b> Button is clicked");
            Thread.sleep(2000);

            try {
                m_assert.assertTrue(selectOptionFromBillsList(oPage_Bills.list_billTypeSelection, billOptionToSelect),
                        "Validate " + billOptionToSelect + " bill is selected");
                Cls_Generic_Methods.customWait(5);
            } catch (Exception e) {
                m_assert.assertTrue(false, "Bill type is not selected" + e);
            }

            fillBillForm();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 20);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickBills),
                    "<b>₹ Bills</b> Button is clicked");

            m_assert.assertInfo(oPage_Bills.list_billTypeSelection.size() + " options present");
//			for(WebElement eBillOption : oPage_Bills.list_billTypeSelection) {
//				String 
//			}


        } catch (Exception e) {
            m_assert.assertTrue(false, "Validate bills failed" + e);
            e.printStackTrace();
        }
    }

    public void fillBillForm() throws Exception {
        oPage_Bills=new Page_Bills(driver);
        oPage_PatientAppointmentDetails=new Page_PatientAppointmentDetails(driver);
        try {
            // String inputServiceValue = "Dressing";
            String paymentMode = "Cash";
            // String inputPackageValue = "Cornia";
            String remarkComments = "this is REMARK comment";
            String internalComments = "this is INTERNAL comment";
            String additionalDiscount = "10.0";
            String discountComments = "this is DISCOUNT comment";

            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Bills.text_billHeading, 20),
                    "Bill form is visible");
            // click plus button and select package
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickPlusButton,7);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickPlusButton),
                    "Plus button clicked for selecting package");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.link_newPackage,7);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.link_newPackage),
                    "click on new package");

            for (WebElement billRow : oPage_Bills.bill_rowsOfServices) {

                if (billRow.isDisplayed()) {

                    try {
                        List<WebElement> packageDetailsOnRow = billRow.findElements(By.xpath("./child::*"));
                        for (WebElement itemOnRow : packageDetailsOnRow) {
                            String classAttr = Cls_Generic_Methods.getElementAttribute(itemOnRow, "class");
                            if (classAttr.contains("field_for_description")) {
                                Cls_Generic_Methods.clickElement(driver, itemOnRow);
                                Cls_Generic_Methods.customWait(3);
                                oPage_Bills.input_billService.sendKeys(Keys.DOWN, Keys.ENTER);
                                // driver.findElement(By.xpath("//input[@role='textbox']")).sendKeys(Keys.DOWN,
                                // Keys.ENTER);
                                break;
                            }
                        }

                    } catch (ElementNotInteractableException e) {
                        e.printStackTrace();
                        m_assert.assertTrue(false, "Services/Packages are not selected" + e);
                    }
                }
            }

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_billRemarksComment, remarkComments),
                    "Remark comments are " + remarkComments);

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_billInternalComment, internalComments),
                    "Internal comments are  " + internalComments);

            Cls_Generic_Methods.clearValuesInElement(oPage_Bills.input_additionalDiscount);

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_additionalDiscount, additionalDiscount),
                    "additional discount is entered as " + additionalDiscount);

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_billDiscountComment, discountComments),
                    "Discount comments are  " + discountComments);

            // select mode of payment section

            if(!createDraftBill) {
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_modeOfPayment, paymentMode),
                        paymentMode + " option is selected for Mode Of Payment");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills),
                        "<b>Add</b> Save Final Bill Button is clicked");
            }else{
                String sRemainingAmount=Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_balanceRemainingAmount,"value");
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.input_amountPendingOpd);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_amountPendingOpd,sRemainingAmount);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_saveDraftBillButton),
                        "<b>Add</b> Save as Draft Bill Button is clicked");
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_closeBill),
                    "<b>Close</b> Button is clicked");

            Cls_Generic_Methods
                    .waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertTrue(false, "Error occurred while filling Bill form - " + e);
        }

    }

    public boolean selectPaymentFromListBasedOnTextOrValue(List<WebElement> listOfElements, String sOptionValue) {

        boolean valueSelected = false;

        try {
            for (WebElement eOption : listOfElements) {
                if (Cls_Generic_Methods.getTextInElement(eOption).equals(sOptionValue)) {
                    Cls_Generic_Methods.clickElement(driver, eOption);
                    valueSelected = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return valueSelected;

    }

    public static boolean selectOptionFromBillsList(List<WebElement> listOfBillsElement, String nameOfBillToSelect) {

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
