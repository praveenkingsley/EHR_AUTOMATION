package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Test;

import com.aventstack.extentreports.ExtentTest;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.Page_CommonElements;
import pages.store.PharmacyStore.Transaction.Page_Transfer;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.DriverFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.EHR_Helper;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ExtentFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ParallelTestBase;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class StockDirectTransferSequenceParallelTest extends ParallelTestBase {

    AtomicBoolean executed = new AtomicBoolean();

    //Validating for Store to Central Hub
    String sStore=pharmacy;
    String sReceivingStore=centralHub1;
    String sDepartment="Facility Store to Central Hub";

    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generateDirectTransferSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            if (executed.compareAndSet(false, true)) {
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "Stock Direct Transfer", sDepartment);
            }

            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

            EHR_Helper.login(m_assert, driver, user);
            barrier.await();

            m_assert.assertInfo(EHR_Helper.selectStoreOnApp(sStore, driver), "Selected Store - " + sStore);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            if (!createDirectTransfer(driver, m_assert)) {
                m_assert.assertFatal("Unable to create Transfer Transaction");
            }

            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void validateTransferSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);
            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
            Page_Transfer oPage_Transfer = new Page_Transfer(driver);

            EHR_Helper.login(m_assert, driver, user);

            EHR_Helper.selectStoreOnApp(sStore, driver);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue", driver);

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Transfer.list_transferTransactionRow, 30);

            int noTransferId = 0;

            boolean duplicateFound = false;
            Set<String> transferSet = new HashSet<>();

            for (WebElement row : oPage_Transfer.list_transferTransactionRow) {
                noTransferId++;
                Cls_Generic_Methods.clickElement(row);
                EHR_Helper.waitForElementToBeDisplayed(oPage_Transfer.button_approveTransfer, 20);

                Cls_Generic_Methods.clickElement(oPage_Transfer.button_approveTransfer);
                Cls_Generic_Methods.customWait();

                m_assert.assertInfo("Generated Transfer ID : "+oPage_Transfer.text_transferTransactionID.getText());
                if (transferSet.contains(oPage_Transfer.text_transferTransactionID.getText())) {
                    m_assert.assertFalse("Duplicate Transfer ID found: " + oPage_Transfer.text_transferTransactionID.getText());
                    duplicateFound = true;
                } else {
                    transferSet.add(oPage_Transfer.text_transferTransactionID.getText());
                }


                if (noTransferId >= noOfTabs) {
                    break;
                }
            }

            Cls_Generic_Methods.customWait();

            m_assert.assertTrue(!duplicateFound, "Validate Direct Transfer Sequence - No Duplicates found");
            EHR_Helper.validateSequenceManager(m_assert, "Stock Direct Transfer", sDepartment);
            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" "+e);
        }


    }

    public boolean createDirectTransfer(WebDriver driver, SoftAssert m_assert) {

        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        boolean status = false;

        try {

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue", driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Transfer.button_newTransaction, 10);
            Cls_Generic_Methods.clickElement(driver, oPage_Transfer.button_newTransaction);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Transfer.dropdown_receivingStore, 5);
            Cls_Generic_Methods.clickElement(driver, oPage_Transfer.dropdown_receivingStore);

            for (WebElement eReceivingStore : oPage_Transfer.list_receivingStore) {

                if (eReceivingStore.getText().equalsIgnoreCase(sReceivingStore.split("-")[0])) {
                    Cls_Generic_Methods.clickElement(driver, eReceivingStore);
                    break;
                }
            }
            EHR_Helper.waitForElementToBeDisplayed(oPage_Transfer.button_searchByDescription, 10);

            Cls_Generic_Methods.clickElement(driver, oPage_Transfer.button_searchByDescription);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Transfer.input_searchItemToBeTransferred, 10);

            for (WebElement eItemVariantCode : oPage_Transfer.list_itemDescriptionRow) {
                String stock = eItemVariantCode.findElement(By.xpath("./ancestor::td/div[2]/span[2]")).getText();

                if (Double.parseDouble(stock) >= 1) {
                    Cls_Generic_Methods.clickElement(eItemVariantCode);
                    break;
                }
            }

            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.list_quantityFieldForItemsToTransfer.get(0), "1");
            Cls_Generic_Methods.customWait();

            barrier.await();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Transfer.button_saveChanges), "Transfer Transaction Created");
            status = true;
            Cls_Generic_Methods.customWait(6);

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertInfo("Unable to create Transfer " + e);
        }
        return status;
    }
}
