package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Test;

import com.aventstack.extentreports.ExtentTest;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import org.openqa.selenium.*;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.bills.Page_Bills;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.opd.Page_OPD;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.DriverFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.EHR_Helper;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ExtentFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ParallelTestBase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class InvoiceSequenceParallelTest extends ParallelTestBase {

    Set<String> generatedBillNumber = new HashSet<>();
    boolean duplicateFound = false;
    AtomicBoolean executed=new AtomicBoolean();
    SoftAssert expectedAssert;

    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generateInvoiceNumberInOPD() {

        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {

            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            if(executed.compareAndSet(false,true)){
                expectedAssert=m_assert;
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "Invoice", "OPD");
            }

            Page_Navbar oPage_Navbar = new Page_Navbar(driver);
            Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            Page_Bills oPage_Bills = new Page_Bills(driver);
            Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

            EHR_Helper.login(m_assert, driver, user);

            EHR_Helper.waitForElementToBeDisplayed(oPage_Navbar.button_addAppointment, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_addAppointment), "Add Appointment Button Clicked");

            EHR_Helper.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.button_addNewPatient, 30);
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_addNewPatient), "Add New Patient Button Clicked");

            EHR_Helper.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, 30);
            String patientName = EHR_Helper.generateRandomName();
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, patientName), "Entered Patient Name : " + patientName);
            Cls_Generic_Methods.clearValuesInElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, "98765");
            Cls_Generic_Methods.clickElement(driver, oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, "43221");
            barrier.await();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm), "Click Save Button");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.getURL(driver, url);

            selectPatientFromTab(driver, patientName);

            if (EHR_Helper.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_markPatientArrived, 10)) {
                Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientArrived);
                Cls_Generic_Methods.customWait();
            }


            EHR_Helper.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.text_opdRhsAppointmentId, 30);
            m_assert.assertInfo("Appointment Created = Appointment ID = <b>" + oPage_PatientAppointmentDetails.text_opdRhsAppointmentId.getText() + "</b>");

            EHR_Helper.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 20);
            Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickBills);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Bills.button_newBill, 20);

            Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_newBill);

            EHR_Helper.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);

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
                                break;
                            }
                        }

                    } catch (ElementNotInteractableException e) {
                        e.printStackTrace();
                        m_assert.assertFalse("Services/Packages are not selected" + e);
                    }
                }
            }

            Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_modeOfPayment, "Cash");
            Cls_Generic_Methods.customWait();

            barrier.await();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills), "New Bill Created");

            Cls_Generic_Methods.customWait(10);

            m_assert.assertInfo("Created Bill No = " + oPage_Bills.text_billNumber.getText());

            if (generatedBillNumber.contains(oPage_Bills.text_billNumber.getText())) {
                m_assert.assertFalse("Duplicate Purchase Grn no found: " + oPage_Bills.text_billNumber.getText());
                duplicateFound = true;
            } else {
                generatedBillNumber.add(oPage_Bills.text_billNumber.getText());
            }
            Cls_Generic_Methods.customWait();

            if(m_assert==expectedAssert){
                m_assert.assertTrue(!duplicateFound, "Validate OPD Invoice Sequence - No Duplicates found");
                EHR_Helper.validateSequenceManager(m_assert, "Invoice", "OPD");
                tearDown();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generateInvoiceNumberInIPD() {

        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {

            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            if(executed.compareAndSet(false,true)){
                expectedAssert=m_assert;
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "Invoice", "IPD");
            }

            Page_Navbar oPage_Navbar = new Page_Navbar(driver);
            Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            Page_Bills oPage_Bills = new Page_Bills(driver);
            Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
            Page_ScheduleAdmission oPage_ScheduleAdmission=new Page_ScheduleAdmission(driver);
            EHR_Helper.login(m_assert, driver, user);
            barrier.await();
            EHR_Helper.waitForElementToBeDisplayed(oPage_Navbar.button_addAppointment, 30);
            EHR_Helper.selectDepartmentOnApp(driver, "IPD");

            EHR_Helper.waitForElementToBeDisplayed(oPage_Navbar.button_addAppointment, 30);
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_addAppointment), "Add Appointment Button Clicked");

            EHR_Helper.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.button_addNewPatient, 30);
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_addNewPatient), "Add New Patient Button Clicked");


            EHR_Helper.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, 60);

            String patientName = EHR_Helper.generateRandomName();
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, patientName), "Entered Patient Name : " + patientName);
            Cls_Generic_Methods.clearValuesInElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, "98765");
            Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, "43221");

            Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduleAdmission.radioBtn_emergency);

            EHR_Helper.waitForElementToBeDisplayed(oPage_ScheduleAdmission.button_selectCase, 8);
            Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_selectCase);
            EHR_Helper.waitForElementToBeDisplayed(oPage_ScheduleAdmission.button_allCasesDropDown, 8);

            Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_allCasesDropDown);
            Cls_Generic_Methods.selectElementByIndex(oPage_ScheduleAdmission.select_allCasesDropDown, 1);
            Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_allCasesDropDown);

            Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_createAdmission);
            EHR_Helper.waitForElementToBeDisplayed(oPage_ScheduleAdmission.header_assignBed, 15);

            Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_closeAdmissionForm);
            EHR_Helper.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);


            EHR_Helper.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 20);
            Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickBills);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Bills.button_cashBill, 20);

            Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_cashBill);

            EHR_Helper.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);

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
                                break;
                            }
                        }

                    } catch (ElementNotInteractableException e) {
                        e.printStackTrace();
                        m_assert.assertFalse("Services/Packages are not selected" + e);
                    }
                }
            }

            Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_modeOfPayment, "Cash");
            Cls_Generic_Methods.customWait();

            barrier.await();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills), "New Bill Created");

            EHR_Helper.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);

            m_assert.assertInfo("Created Bill No = " + oPage_Bills.text_billNumber.getText());

            if (generatedBillNumber.contains(oPage_Bills.text_billNumber.getText())) {
                m_assert.assertFalse("Duplicate Purchase Grn no found: " + oPage_Bills.text_billNumber.getText());
                duplicateFound = true;
            } else {
                generatedBillNumber.add(oPage_Bills.text_billNumber.getText());
            }
            Cls_Generic_Methods.customWait();

            if(m_assert==expectedAssert){
                m_assert.assertTrue(!duplicateFound, "Validate IPD Invoice Sequence - No Duplicates found");
                EHR_Helper.validateSequenceManager(m_assert, "Invoice", "IPD");
                tearDown();
            }



        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
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
