package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Test;

import com.aventstack.extentreports.ExtentTest;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.store.OpticalStore.Page_SalesOrder;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.DriverFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.EHR_Helper;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ExtentFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ParallelTestBase;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class SalesOrderSequenceParallelTest extends ParallelTestBase {

    static Set<String> duplicates = new HashSet<>();
    static boolean duplicateFound = false;
    AtomicBoolean executed=new AtomicBoolean();
    SoftAssert expectedAssert;

    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generateSalesOrderSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);


            if (executed.compareAndSet(false, true)) {
                expectedAssert=m_assert;
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "Sales Order", getDepartment());
            }

            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

            EHR_Helper.login(m_assert, driver, user);
            barrier.await();
            m_assert.assertInfo(EHR_Helper.selectStoreOnApp(sStore, driver), "Selected Store - " + sStore);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order", driver);

            boolean status = createSaleOrderForPolicyCheck(driver, m_assert);

            if (!status) {
                m_assert.assertFatal("Sales Order Not Created");
            }

            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }


    }

    public boolean createSaleOrderForPolicyCheck(WebDriver driver, SoftAssert m_assert) {

        boolean createSalesOrder = false;
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        String sMedicationQty = "1";
        String myMedicationName = "SalesOrderTest1";


        try {

            EHR_Helper.waitForElementToBeDisplayed(oPage_SalesOrder.button_addNewButtonInOrder, 40);
            Cls_Generic_Methods.clickElement(driver, oPage_SalesOrder.button_addNewButtonInOrder);
            EHR_Helper.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.button_addNewPatient, 40);
            Cls_Generic_Methods.clickElement(driver, oPage_NewPatientRegisteration.button_addNewPatient);
            EHR_Helper.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, 30);

            String patientName = EHR_Helper.generateRandomName();
            Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, patientName);

            Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, "9876543210");

            Cls_Generic_Methods.clickElement(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm);
            EHR_Helper.waitForElementToBeDisplayed(oPage_SalesOrder.text_TxnDate, 30);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_searchMedicineNameInDescription, myMedicationName);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_searchMedicineNameInDescription, "" + Keys.ENTER);

            Cls_Generic_Methods.customWait(10);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult, 12);

            for (WebElement eMedication : oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult) {

                Cls_Generic_Methods.clickElement(eMedication);
                Cls_Generic_Methods.customWait(3);
                break;

            }

            Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_quantityOfMedicine, sMedicationQty);
            Cls_Generic_Methods.customWait(5);

            barrier.await();
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_SalesOrder.button_SaveChanges), " Sale Order Created Successfully");

            EHR_Helper.waitForElementToBeDisplayed(oPage_SalesOrder.text_orderNoOpticalOrder, 15);

            m_assert.assertInfo("Sales Order No = <b>"+oPage_SalesOrder.text_orderNoOpticalOrder.getText()+"</b>");
            if (duplicates.contains(oPage_SalesOrder.text_orderNoOpticalOrder.getText())) {
                m_assert.assertFalse("Duplicate Sales Order no found: " + oPage_SalesOrder.text_orderNoOpticalOrder.getText());
                duplicateFound = true;
            } else {
                duplicates.add(oPage_SalesOrder.text_orderNoOpticalOrder.getText());
            }

            barrier.await();
            Cls_Generic_Methods.customWait();

            if(m_assert==expectedAssert){
                m_assert.assertTrue(!duplicateFound, "Validate Sales Order ID Sequence - No Duplicates found");
                EHR_Helper.validateSequenceManager(m_assert, "Sales Order", getDepartment());
            }

            createSalesOrder = true;


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        return createSalesOrder;
    }
}
