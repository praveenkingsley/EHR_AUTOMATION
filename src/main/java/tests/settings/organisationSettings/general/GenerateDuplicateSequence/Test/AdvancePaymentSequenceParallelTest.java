package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Test;

import com.aventstack.extentreports.ExtentTest;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import data.EHR_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.bills.Page_Bills;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;
import pages.store.Page_PatientQueue;
import pages.store.PharmacyStore.Transaction.Page_Sale;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.DriverFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.EHR_Helper;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ExtentFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ParallelTestBase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdvancePaymentSequenceParallelTest extends ParallelTestBase {
    String patientName = "JONATHAN";
    AtomicBoolean executed = new AtomicBoolean();
    SoftAssert eAssert;

    @Test
    public void addNewAppointmentForAdvanceReceipt() {

        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {

            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            EHR_Helper.launchDriverInInstalledBrowser();
            EHR_Helper.validateSequenceManager(m_assert, "Advance Payment", "OPD");

            Page_Navbar oPage_Navbar = new Page_Navbar(driver);
            Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

            EHR_Helper.login(m_assert, driver, user);

            EHR_Helper.waitForElementToBeDisplayed(oPage_Navbar.button_addAppointment, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_addAppointment), "Add Appointment Button Clicked");

            EHR_Helper.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.button_addNewPatient, 30);
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_addNewPatient), "Add New Patient Button Clicked");

            EHR_Helper.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, 30);
            patientName = EHR_Helper.generateRandomName();
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, patientName), "Entered Patient Name : " + patientName);
            Cls_Generic_Methods.clearValuesInElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, "98765");
            Cls_Generic_Methods.clickElement(driver, oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, "43221");
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm), "Click Save Button");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.getURL(driver, url);

            selectPatientFromTab(driver, patientName);

            if (EHR_Helper.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_markPatientArrived, 10)) {
                Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientArrived);
                Cls_Generic_Methods.customWait();
            }

            m_assert.assertInfo("Appointment Created = Appointment ID = <b>" + oPage_PatientAppointmentDetails.text_opdRhsAppointmentId.getText() + "</b>");


            Cls_Generic_Methods.customWait();
            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Set<String> generatedAdvancePayment =new HashSet<>();
    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generateAdvanceReceipt() {

        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {

            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            Page_Bills oPage_Bills = new Page_Bills(driver);
            Page_Sale oPage_Sale = new Page_Sale(driver);
            Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);

            EHR_Helper.login(m_assert, driver, user);

            selectPatientFromTab(driver, patientName);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 20);
            Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickBills);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Bills.button_advanceReceiptBill, 20);

            Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_advanceReceiptBill);

            EHR_Helper.waitForElementToBeDisplayed(oPage_PatientQueue.button_advanceReceiptTemplate, 5);
            oPage_PatientQueue.input_reasonAdvance.sendKeys("Test");
            Cls_Generic_Methods.selectElementByVisibleText(oPage_PatientQueue.select_mop, "Cash");
            oPage_PatientQueue.input_amountAdvance.sendKeys("100");

            barrier.await();
            Cls_Generic_Methods.clickElement(driver, oPage_PatientQueue.button_saveAdvance);
            EHR_Helper.waitForElementToBeDisplayed(oPage_PatientQueue.text_advanceId, 10);

            String advanceId = oPage_PatientQueue.text_advanceId.getText();
            m_assert.assertInfo("Generated Advance ID : <b>" + advanceId + "</b>");

            boolean duplicateFound=false;
            if (generatedAdvancePayment.contains(oPage_PatientQueue.text_advanceId.getText())) {
                m_assert.assertFalse("Duplicate Advance ID found: " + oPage_PatientQueue.text_advanceId.getText());
                duplicateFound = true;
            } else {
                generatedAdvancePayment.add(oPage_PatientQueue.text_advanceId.getText());
            }

            if (executed.compareAndSet(false, true)) {
                m_assert.assertTrue(!duplicateFound, "Validate Advance ID Sequence - No Duplicates found");
                EHR_Helper.validateSequenceManager(m_assert, "Advance Payment", "OPD");
                executed=new AtomicBoolean();
            }

            //Refund Id

            barrier.await();
            Cls_Generic_Methods.customWait();

            if (executed.compareAndSet(false, true)) {
                eAssert=m_assert;
                EHR_Helper.validateSequenceManager(m_assert, "Refund Payment", "OPD");
            }

            barrier.await();
            EHR_Helper.waitForElementToBeDisplayed(oPage_PatientQueue.button_refundAdvance, 10);
            Cls_Generic_Methods.clickElement(driver, oPage_PatientQueue.button_refundAdvance);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Sale.input_refundReason, 10);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_refundReason, "Auto Refund");

            Cls_Generic_Methods.selectElementByVisibleText(oPage_Sale.select_modeOfPaymentInRefund, "Cash");
            Cls_Generic_Methods.clearValuesInElement(oPage_Sale.input_refundAmount);

            Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_refundAmount, "1");

            EHR_Helper.waitForElementToBeDisplayed(oPage_PatientQueue.button_saveRefund, 10);

            barrier.await();
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_saveRefund), "Refund completed");
            Cls_Generic_Methods.customWait(10);
            WebElement eRefundId = driver.findElement(By.xpath("//div[@class='row advance-breakup']//tbody/tr[1]/td[1]"));
            m_assert.assertInfo("Generated Refund ID : <b>" + eRefundId.getText() + "</b>");

            if (generatedAdvancePayment.contains( eRefundId.getText())) {
                m_assert.assertFalse("Duplicate Appointment ID found: " + eRefundId.getText());
                duplicateFound = true;
            } else {
                generatedAdvancePayment.add(eRefundId.getText());
            }

            if(m_assert==eAssert){
                m_assert.assertTrue(!duplicateFound, "Validate Refund ID Sequence - No Duplicates found");
                EHR_Helper.validateSequenceManager(m_assert, "Refund Payment", "OPD");
                tearDown();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean selectPatientFromTab(WebDriver driver, String sPatientName, String... tab) {

        String tabToSelect;

        if (tab.length == 0) {
            tabToSelect = "ALL";
        } else {
            tabToSelect = tab[0];
        }

        boolean bPatientNameFound = false;
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            EHR_Helper.waitForElementToBeDisplayed(oPage_OPD.tabs_patientCount, 30);
            CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect);

            Cls_Generic_Methods.customWait();

            for (WebElement eTabElement : oPage_OPD.rows_patientAppointments) {

                if (eTabElement.isDisplayed()) {
                    List<WebElement> patientDetailsOnRow = eTabElement.findElements(By.xpath("./child::*"));
                    String patientName = patientDetailsOnRow.get(0).getAttribute("title");

                    if (sPatientName.equalsIgnoreCase(patientName.trim())) {
                        System.out.println("Patient : " + patientName + " -------> Selected");
                        bPatientNameFound = Cls_Generic_Methods.clickElement(driver, eTabElement);
                        EHR_Helper.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                                16);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bPatientNameFound;
    }

}
