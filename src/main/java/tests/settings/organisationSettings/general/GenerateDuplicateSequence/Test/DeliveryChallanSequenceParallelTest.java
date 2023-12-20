package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Test;

import com.aventstack.extentreports.ExtentTest;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.Page_CommonElements;
import pages.store.PharmacyStore.Page_TaxInvoiceDeliveryChallan;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.DriverFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.EHR_Helper;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ExtentFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ParallelTestBase;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class DeliveryChallanSequenceParallelTest extends ParallelTestBase {

    AtomicBoolean executed = new AtomicBoolean();
    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generateDeliveryChallanSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        String transactionType="DELIVERY CHALLAN";

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            if (executed.compareAndSet(false, true)) {
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "Delivery Challan", sStore.split("-")[1].trim());
            }

            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

            EHR_Helper.login(m_assert, driver, user);
            barrier.await();

            m_assert.assertInfo(EHR_Helper.selectStoreOnApp(sStore, driver), "Selected Store - " + sStore);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            if (!createTaxInvoice(transactionType,driver, m_assert)) {
                m_assert.assertFatal("Unable to create Delivery Challan Transaction");
            }

            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @Test
    public void validateDeliveryChallanSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);
            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
            Page_TaxInvoiceDeliveryChallan oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);


            EHR_Helper.login(m_assert, driver, user);

            EHR_Helper.selectStoreOnApp(sStore, driver);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.clickElement(driver,oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan);

            Cls_Generic_Methods.customWait(10);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.list_transactionHeaderList, 10);

            int noTransferId = 0;

            boolean duplicateFound = false;
            Set<String> taxInvoiceSet = new HashSet<>();

            for (WebElement row : oPage_TaxInvoiceDeliveryChallan.list_transactionCreatedList) {
                noTransferId++;
                Cls_Generic_Methods.clickElementByJS(driver,row);
                Cls_Generic_Methods.customWait(5);
                EHR_Helper.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_approve, 20);
                String taxInvoiceId = oPage_TaxInvoiceDeliveryChallan.text_rhs_transactionId.getText();

                m_assert.assertInfo("Generated Delivery Challan ID : "+ taxInvoiceId);
                if (taxInvoiceSet.contains(taxInvoiceId)) {
                    m_assert.assertFalse("Duplicate Delivery Challan ID found: " + taxInvoiceId);
                    duplicateFound = true;
                } else {
                    taxInvoiceSet.add(taxInvoiceId);
                }

                if (noTransferId >= noOfTabs) {
                    break;
                }
            }

            m_assert.assertTrue(!duplicateFound, "Validate Delivery Challan Sequence - No Duplicates found");
            EHR_Helper.validateSequenceManager(m_assert, "Delivery Challan", sStore.split("-")[1].trim());
            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" "+e);
        }


    }

    public boolean createTaxInvoice(String transactionType,WebDriver driver,SoftAssert m_assert){
        boolean status=false;
        Page_TaxInvoiceDeliveryChallan oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);

        try {
            Cls_Generic_Methods.clickElement(driver,oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan);
            EHR_Helper.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_new, 5);

            Cls_Generic_Methods.clickElement(driver,oPage_TaxInvoiceDeliveryChallan.button_new);
            Cls_Generic_Methods.customWait();

            if (transactionType.equalsIgnoreCase("DELIVERY CHALLAN")) {
                Cls_Generic_Methods.clickElement(driver,oPage_TaxInvoiceDeliveryChallan.button_deliveryChallan);
                EHR_Helper.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_headerCreateDeliveryChallan, 10);

            } else if (transactionType.equalsIgnoreCase("TAX INVOICE")) {
                Cls_Generic_Methods.clickElement(driver,oPage_TaxInvoiceDeliveryChallan.button_taxInvoice);
                EHR_Helper.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_headerCreateTaxInvoice, 10);

            } else {
                m_assert.assertFalse("Enter Valid Transaction Type");
            }

            //Select To store
            EHR_Helper.selectByOptions(oPage_TaxInvoiceDeliveryChallan.select_againstStore, sReceivingStore.split("-")[0],driver);
            Cls_Generic_Methods.customWait(5);

            for (WebElement lhsRow : oPage_TaxInvoiceDeliveryChallan.row_lhsCreateTaxInvoiceDeliveryChallan) {
                Cls_Generic_Methods.clickElement(lhsRow);
                break;
            }

            EHR_Helper.selectByOptions(oPage_TaxInvoiceDeliveryChallan.select_transportationMode, "Test",driver);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_TaxInvoiceDeliveryChallan.input_transactionId, "AUTO-TEST");

            barrier.await();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_saveChanges), "Created <b>Delivery Challan</b>");
            status=true;

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to create Delivery Challan -> " + e);
        }
        return status;
    }
}
