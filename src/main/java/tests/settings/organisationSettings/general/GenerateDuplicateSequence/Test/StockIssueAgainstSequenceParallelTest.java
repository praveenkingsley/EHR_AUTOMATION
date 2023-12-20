package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Test;

import com.aventstack.extentreports.ExtentTest;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.Page_CommonElements;
import pages.store.PharmacyStore.Order.Page_RequisitionReceived;
import pages.store.PharmacyStore.Transaction.Page_Transfer;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.DriverFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.EHR_Helper;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ExtentFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ParallelTestBase;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class StockIssueAgainstSequenceParallelTest extends ParallelTestBase {
    AtomicBoolean executed = new AtomicBoolean();

    //Validating for Store to Central Hub
    String sStore=pharmacy;
    String sReceivingStore=centralHub1;
    String sDepartment="Central Hub to Facility Store";
    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generateIssueAgainstSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            if (executed.compareAndSet(false, true)) {
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "Stock Issue Against", sDepartment);
            }

            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

            EHR_Helper.login(m_assert, driver, user);
            barrier.await();
            m_assert.assertInfo(EHR_Helper.selectStoreOnApp(sReceivingStore, driver), "Selected Store - " + sStore);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition Received",driver);

            if (!receiveRequisition(driver,m_assert)) {
                m_assert.assertFatal("Unable to create Issue Against Transaction");
            }

            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void validateIssueAgainstSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);
            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
            Page_Transfer oPage_Transfer = new Page_Transfer(driver);

            EHR_Helper.login(m_assert, driver, user);

            EHR_Helper.selectStoreOnApp(sReceivingStore, driver);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue", driver);

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Transfer.list_transferTransactionRow, 10);

            int noTransferId = 0;

            boolean duplicateFound = false;
            Set<String> transferSet = new HashSet<>();

            for (WebElement row : oPage_Transfer.list_transferTransactionRow) {
                noTransferId++;
                Cls_Generic_Methods.clickElement(row);
                EHR_Helper.waitForElementToBeDisplayed(oPage_Transfer.button_approveTransfer, 5);
                Cls_Generic_Methods.clickElement(oPage_Transfer.button_approveTransfer);
                String transferId = oPage_Transfer.text_issueNo.getText();

                m_assert.assertInfo("Generated Issue Against ID : "+transferId);
                if (transferSet.contains(transferId)) {
                    m_assert.assertFalse("Duplicate Issue Against ID found: " + transferId);
                    duplicateFound = true;
                } else {
                    transferSet.add(transferId);
                }

                if (noTransferId >= noOfTabs) {
                    break;
                }
            }

            m_assert.assertTrue(!duplicateFound, "Validate Issue Against Sequence - No Duplicates found");
            EHR_Helper.validateSequenceManager(m_assert, "Stock Issue Against", sDepartment);
            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" "+e);
        }


    }


    int count=0;

    public boolean receiveRequisition(WebDriver driver,SoftAssert m_assert) {

        boolean flag=false;
        Page_RequisitionReceived oPage_RequisitionReceived=new Page_RequisitionReceived(driver);
        try {
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_RequisitionReceived.list_statusOfRequisitionReceived,10);

            for (WebElement e : oPage_RequisitionReceived.list_statusOfRequisitionReceived) {

                Cls_Generic_Methods.clickElement(e);

                boolean newTransactionBtn=EHR_Helper.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 4);

                if(!newTransactionBtn){
                    continue;
                }

                if (count > 0) {
                    count=0;
                    continue;
                }
                count++;

                Cls_Generic_Methods.clickElement(driver,oPage_RequisitionReceived.button_newTransactionRequisition);
                EHR_Helper.waitForElementToBeDisplayed(oPage_RequisitionReceived.text_headerIssueItems,10);
                Cls_Generic_Methods.clickElement(driver,oPage_RequisitionReceived.list_requisitionItems.get(0));
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_RequisitionReceived.list_inputLotStock,10);
                Cls_Generic_Methods.clickElement(driver,oPage_RequisitionReceived.list_inputLotStock.get(0));
                Cls_Generic_Methods.sendKeysIntoElement(oPage_RequisitionReceived.list_inputLotStock.get(0),"1");
                Cls_Generic_Methods.clickElement(driver,oPage_RequisitionReceived.button_confirmTransfer);
                EHR_Helper.waitForElementToBeDisplayed(oPage_RequisitionReceived.text_headerIssueItems,10);

                barrier.await();
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver,oPage_RequisitionReceived.button_saveTransfer),"Issue Against Created");
                flag=true;
                EHR_Helper.waitForElementToBeDisplayed(oPage_RequisitionReceived.text_rhsIssueNo,10);

                m_assert.assertInfo("Generated Issue Against ID : "+oPage_RequisitionReceived.text_rhsIssueNo.getText());
                break;
            }

            Cls_Generic_Methods.customWait();
        } catch (Exception e) {
            m_assert.assertFatal("Unable to create issue against transaction "+e);
            e.printStackTrace();
        }
        return flag;
    }
}
