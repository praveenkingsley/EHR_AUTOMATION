package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Test;

import com.aventstack.extentreports.ExtentTest;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.Page_CommonElements;
import pages.store.PharmacyStore.Transaction.Page_Adjustment;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.DriverFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.EHR_Helper;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ExtentFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ParallelTestBase;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdjustmentSequenceParallelTest extends ParallelTestBase {

    AtomicBoolean executed = new AtomicBoolean();

    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generateAdjustmentSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            if (executed.compareAndSet(false, true)) {
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "Stock Adjustment", sStore.split("-")[1]);
            }

            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

            EHR_Helper.login(m_assert, driver, user);
            barrier.await();

            m_assert.assertInfo(EHR_Helper.selectStoreOnApp(sStore, driver), "Selected Store - " + sStore);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            if (!createAdjustment(m_assert, driver)) {
                m_assert.assertFatal("Unable to create Adjustment Transaction");
            }

            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void validateAdjustmentSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);
            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
            Page_Purchase oPage_Purchase = new Page_Purchase(driver);
            Page_Adjustment oPage_Adjustment = new Page_Adjustment(driver);


            EHR_Helper.login(m_assert, driver, user);

            EHR_Helper.selectStoreOnApp(sStore, driver);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "Adjustment", driver);

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_transactionPurchaseList, 10);


            int rowNoTransaction = 0;

            boolean duplicateFound = false;
            Set<String> grnSet = new HashSet<>();

            for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {
                rowNoTransaction++;

                Cls_Generic_Methods.clickElement(row);
                EHR_Helper.waitForElementToBeDisplayed(oPage_Adjustment.text_transactionIdInAdjustment, 10);

                String adjustmentId = oPage_Adjustment.text_transactionIdInAdjustment.getText().split(":")[1];
                m_assert.assertInfo("Adjustment Transaction ID =<b>" + adjustmentId + "</b>");

                if (grnSet.contains(adjustmentId)) {
                    m_assert.assertFalse("Duplicate Adjustment ID found: " + adjustmentId);
                    duplicateFound = true;
                } else {
                    grnSet.add(adjustmentId);
                }

                if (rowNoTransaction >= noOfTabs) {
                    break;
                }
            }

            m_assert.assertTrue(!duplicateFound, "Validate Stock Adjustment ID Sequence - No Duplicates found");
            EHR_Helper.validateSequenceManager(m_assert, "Stock Adjustment", sStore.split("-")[1]);
            tearDown();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private boolean createAdjustment(SoftAssert m_assert, WebDriver driver) {

        Page_Adjustment oPage_Adjustment = new Page_Adjustment(driver);

        boolean flag = false;

        try {
            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "Adjustment", driver);
            Cls_Generic_Methods.clickElement(oPage_Adjustment.button_New);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Adjustment.header_Adjustment, 30);


            Cls_Generic_Methods.clickElement(driver, oPage_Adjustment.button_Description);
            Cls_Generic_Methods.customWait(7);

            for (WebElement eMedication : oPage_Adjustment.list_namesOfMedicinesOnLeftInSearchResult) {
                Cls_Generic_Methods.clickElement(driver, eMedication);
                break;
            }

            Cls_Generic_Methods.customWait(5);
            if(Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Adjustment.list_inputQtyList, 5)){
                Cls_Generic_Methods.clickElement(oPage_Adjustment.list_namesOfMedicinesOnLeftInSearchResult.get(1));
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Adjustment.list_inputQtyList, 5);
            }

            Cls_Generic_Methods.sendKeysIntoElement(oPage_Adjustment.list_inputQtyList.get(0), "1");
            Cls_Generic_Methods.selectElementByIndex(oPage_Adjustment.list_adjustmentAddDeductSelectList.get(0), 2);

            Cls_Generic_Methods.customWait();

            barrier.await();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Adjustment.input_SaveChanges), "Adjustment Transaction Created");
            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to create Adjustment" + e);
        }
        return flag;
    }

}
