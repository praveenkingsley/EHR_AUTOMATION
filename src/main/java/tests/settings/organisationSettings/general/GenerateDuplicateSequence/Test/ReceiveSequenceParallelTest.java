package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Test;

import com.aventstack.extentreports.ExtentTest;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.Page_CommonElements;
import pages.store.PharmacyStore.Page_TaxInvoiceDeliveryChallan;
import pages.store.PharmacyStore.Transaction.Page_Receive;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.DriverFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.EHR_Helper;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ExtentFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ParallelTestBase;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReceiveSequenceParallelTest extends ParallelTestBase {


    Set<String> generatedReceiveNumber = new HashSet<>();
    int rowNo = 0;
    boolean duplicateFound = false;
    AtomicBoolean executed=new AtomicBoolean();
    SoftAssert expectedAssert;

    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generateReceiveTransactionSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());


        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            if (executed.compareAndSet(false, true)) {
                expectedAssert=m_assert;
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "Receive", getDepartment(true));
            }

            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
            Page_Receive oPage_Receive = new Page_Receive(driver);

            EHR_Helper.login(m_assert, driver, user);
            barrier.await();
            m_assert.assertInfo(EHR_Helper.selectStoreOnApp(sStore, driver), "Selected Store - " + sReceivingStore);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "Receive", driver);
            selectReceiveTransaction(driver);

            EHR_Helper.waitForElementToBeDisplayed(oPage_Receive.button_receiveStock, 10);
            Cls_Generic_Methods.clickElement(driver, oPage_Receive.button_receiveStock);

            Cls_Generic_Methods.customWait(5);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Receive.text_receiveQtyUnderReceiveTransactionPopUp, 10);
            boolean selectSubStore = EHR_Helper.waitForElementToBeDisplayed(oPage_Receive.select_subStore, 1);

            if (selectSubStore) {
                Cls_Generic_Methods.selectElementByIndex(oPage_Receive.select_subStore, 1);
            }
            oPage_Receive.text_receiveQtyUnderReceiveTransactionPopUp.clear();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Receive.text_receiveQtyUnderReceiveTransactionPopUp, "1");

            barrier.await();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Receive.button_saveChanges), "Receive Transaction created");

            EHR_Helper.waitForElementToBeDisplayed(oPage_Receive.text_receiveTransactionId, 10);

            m_assert.assertInfo("Receive Transaction ID : " + oPage_Receive.text_receiveTransactionId.getText());

            if (generatedReceiveNumber.contains(oPage_Receive.text_receiveTransactionId.getText())) {
                m_assert.assertFalse("Duplicate Receive Transaction ID found: " + oPage_Receive.text_receiveTransactionId.getText());
                duplicateFound = true;
            } else {
                generatedReceiveNumber.add(oPage_Receive.text_receiveTransactionId.getText());
            }
            Cls_Generic_Methods.customWait();

            if(m_assert==expectedAssert){
                m_assert.assertTrue(!duplicateFound, "Validate Receive ID Sequence - No Duplicates found");
                EHR_Helper.validateSequenceManager(m_assert, "Receive", getDepartment(true));
                tearDown();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    int count = 0;

    public void selectReceiveTransaction(WebDriver driver) {
        Page_Receive oPage_Receive = new Page_Receive(driver);
        try {
            for (WebElement row : oPage_Receive.list_text_transactionIdRow) {
                Cls_Generic_Methods.clickElement(row);
                if (EHR_Helper.waitForElementToBeDisplayed(oPage_Receive.button_receiveStock, 5)) {
                    if (count > 0) {
                        count = 0;
                        continue;
                    }
                    count++;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
