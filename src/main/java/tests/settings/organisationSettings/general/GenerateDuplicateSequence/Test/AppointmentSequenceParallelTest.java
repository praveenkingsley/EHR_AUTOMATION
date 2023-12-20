package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Test;

import com.aventstack.extentreports.ExtentTest;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.opd.Page_OPD;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.DriverFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.EHR_Helper;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ExtentFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ParallelTestBase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class AppointmentSequenceParallelTest extends ParallelTestBase {

    CountDownLatch latch = new CountDownLatch(noOfTabs);
    AtomicBoolean executed=new AtomicBoolean();
    SoftAssert expectedAssert;
    Set<String> generatedAppointmentId=new HashSet<>();

    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs + 1)
    public void generateAppointmentId() {

        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {

            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            if(executed.compareAndSet(false,true)){
                expectedAssert=m_assert;
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "Appointment ID", "OPD");
            }

            Page_Navbar oPage_Navbar = new Page_Navbar(driver);
            Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

            EHR_Helper.login(m_assert, driver, user);
            barrier.await();
            EHR_Helper.waitForElementToBeDisplayed(oPage_Navbar.button_addAppointment, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_addAppointment), "Add Appointment Button Clicked");

            EHR_Helper.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.button_addNewPatient, 60);
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_addNewPatient), "Add New Patient Button Clicked");

            EHR_Helper.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, 10);
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
            EHR_Helper.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.text_opdRhsAppointmentId, 10);

            m_assert.assertInfo("Appointment Created = Appointment ID = <b>" + oPage_PatientAppointmentDetails.text_opdRhsAppointmentId.getText() + "</b>");

            boolean duplicateFound=false;
            if (generatedAppointmentId.contains( oPage_PatientAppointmentDetails.text_opdRhsAppointmentId.getText())) {
                m_assert.assertFalse("Duplicate Appointment ID found: " + oPage_PatientAppointmentDetails.text_opdRhsAppointmentId.getText());
                duplicateFound = true;
            } else {
                generatedAppointmentId.add( oPage_PatientAppointmentDetails.text_opdRhsAppointmentId.getText());
            }

//            latch.countDown();
//            latch.await();
//            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientArrived), "Click Mark Patient Arrived Button");
//            EHR_Helper.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.text_opdRhsPatientId, 40);
//            m_assert.assertInfo("Patient ID = <b>" + oPage_PatientAppointmentDetails.text_opdRhsPatientId.getText() + "</b>");

            Cls_Generic_Methods.customWait();

            if(m_assert==expectedAssert){
                m_assert.assertTrue(!duplicateFound, "Validate Appointment ID Sequence - No Duplicates found");
                EHR_Helper.validateSequenceManager(m_assert, "Appointment ID", "OPD");
                tearDown();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generateAdmissionId() {

        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {

            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            if(executed.compareAndSet(false,true)){
                expectedAssert=m_assert;
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "Admission", "IPD");
            }

            Page_Navbar oPage_Navbar = new Page_Navbar(driver);
            Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
            Page_IPD oPage_IPD = new Page_IPD(driver);
            EHR_Helper.login(m_assert, driver, user);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Navbar.button_addAppointment, 20);
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_addAppointment), "Add Appointment Button Clicked");

            EHR_Helper.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.button_addNewPatient, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_addNewPatient), "Add New Patient Button Clicked");

            EHR_Helper.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, 10);
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

            if (EHR_Helper.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_markPatientArrived, 20)) {
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_markPatientArrived), "Click Mark Patient Arrived Button");
            }
            Cls_Generic_Methods.getURL(driver, url);

            selectPatientFromTab(driver, patientName);
            scheduleAdmission(m_assert, driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_IPD.text_admissionId, 20);
            String admissionId = oPage_IPD.text_admissionId.getText();

            m_assert.assertInfo("Admission ID : <b>" + admissionId + "</b>");
            Cls_Generic_Methods.customWait();

            boolean duplicateFound=false;
            if (generatedAppointmentId.contains( oPage_IPD.text_admissionId.getText())) {
                m_assert.assertFalse("Duplicate Admission ID found: " + oPage_IPD.text_admissionId.getText());
                duplicateFound = true;
            } else {
                generatedAppointmentId.add( oPage_IPD.text_admissionId.getText());
            }

            if(m_assert==expectedAssert){
                m_assert.assertTrue(!duplicateFound, "Validate Admission ID Sequence - No Duplicates found");
                EHR_Helper.validateSequenceManager(m_assert, "Admission", "IPD");
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

    public void scheduleAdmission(SoftAssert m_assert, WebDriver driver) {

        Page_ScheduleAdmission oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        try {
            EHR_Helper.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
            Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission);
            EHR_Helper.waitForElementToBeDisplayed(oPage_ScheduleAdmission.header_ScheduleAdmissionForm, 20);

            WebElement radioAdmissionType = oPage_ScheduleAdmission.list_radioAdmissionType.get(2);
            Cls_Generic_Methods.clickElementByJS(driver, radioAdmissionType);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduleAdmission.button_viewCaseDetails);


            latch.countDown();
            latch.await();

            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_createAdmission),"Scheduled Admission");
            EHR_Helper.waitForElementToBeDisplayed(oPage_ScheduleAdmission.header_assignBed, 15);
            Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.input_selectWard);
            Cls_Generic_Methods.selectElementByIndex(oPage_ScheduleAdmission.input_selectWard, 1);
            Cls_Generic_Methods.customWait(1);
            Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.input_selectRoom);
            Cls_Generic_Methods.selectElementByIndex(oPage_ScheduleAdmission.input_selectRoom, 1);
            Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_closeAdmissionForm);
            EHR_Helper.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);

            EHR_Helper.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_jumpToAdmission,
                    10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver,oPage_PatientAppointmentDetails.button_jumpToAdmission),
                    "Clicked Jump to Admission Button");
            Cls_Generic_Methods.customWait(2);

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        }
    }

}
