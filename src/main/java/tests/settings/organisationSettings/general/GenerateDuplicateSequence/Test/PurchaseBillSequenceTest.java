package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Test;

import com.aventstack.extentreports.ExtentTest;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.Page_CommonElements;
import pages.store.PharmacyStore.Transaction.Page_PurchaseBill;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.DriverFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.EHR_Helper;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ExtentFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ParallelTestBase;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;


public class PurchaseBillSequenceTest extends ParallelTestBase {

    String vendorName = "Supplier ABC";
    int rowNo=0;

    AtomicBoolean executed=new AtomicBoolean();

    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generatePurchaseBillSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            if (executed.compareAndSet(false, true)) {
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "Purchase Bill", getDepartment());
            }

            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
            Page_PurchaseBill oPage_PurchaseBill = new Page_PurchaseBill(driver);

            EHR_Helper.login(m_assert, driver, user);
            barrier.await();
            m_assert.assertInfo(EHR_Helper.selectStoreOnApp(sStore, driver),"Selected Store - "+sStore);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            //------------------

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills", driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_PurchaseBill.button_purchaseNew, 20);
            Cls_Generic_Methods.clickElement(driver, oPage_PurchaseBill.button_purchaseNew);
            EHR_Helper.waitForElementToBeDisplayed(oPage_PurchaseBill.select_vendorOptionsCreatePurchaseBill, 6);
            EHR_Helper.selectByOptions(oPage_PurchaseBill.select_vendorOptionsCreatePurchaseBill, vendorName, driver);
            Cls_Generic_Methods.selectElementByIndex(oPage_PurchaseBill.select_createAgainstPurchaseBill, 1);
            Cls_Generic_Methods.customWait(4);
            Cls_Generic_Methods.clickElement(driver, oPage_PurchaseBill.list_RowsOnCreatePurchaseBillTable.get(rowNo));
            rowNo++;

            EHR_Helper.waitForElementToBeDisplayed(oPage_PurchaseBill.button_removeFromList, 10);

            barrier.await();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_PurchaseBill.button_saveChanges),"<b>Purchase Bill</b> created");

            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void validatePurchaseBillSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);
            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
            Page_PurchaseBill oPage_PurchaseBill=new Page_PurchaseBill(driver);

            EHR_Helper.login(m_assert, driver, user);

            EHR_Helper.selectStoreOnApp(sStore, driver);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills", driver);

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PurchaseBill.list_purchaseBillHeaderList, 10);


            int purchaseBillId = 0;

            boolean duplicateFound=false;
            Set<String> duplicates=new HashSet<>();
            for (WebElement row : oPage_PurchaseBill.list_purchaseBillRowList) {
                purchaseBillId++;

                Cls_Generic_Methods.clickElementByJS(driver, row);
                EHR_Helper.waitForElementToBeDisplayed(oPage_PurchaseBill.button_approve, 10);
                String purchaseBill_no = oPage_PurchaseBill.text_rhs_purchaseBillNo.getText();
                String purchaseBillCreatedAt = oPage_PurchaseBill.text_rhs_purchaseBillCreatedAt.getText().split("\\|")[0].trim();

                m_assert.assertInfo("Purchase Bill created at "+purchaseBillCreatedAt+" --> Purchase Bill No : <b>" + purchaseBill_no + "</b>");

                if (duplicates.contains(purchaseBill_no)) {
                    m_assert.assertFalse("Duplicate Purchase Grn no found: " + purchaseBill_no);
                    duplicateFound=true;
                } else {
                    duplicates.add(purchaseBill_no);
                }

                if (purchaseBillId >= noOfTabs) {
                    break;
                }

            }

            purchaseBillId=0;

            List<WebElement> rowToClick=oPage_PurchaseBill.list_purchaseBillRowList;

            while (true) {
                Cls_Generic_Methods.clickElement(rowToClick.get(purchaseBillId));
                purchaseBillId++;

                boolean notApproved=EHR_Helper.waitForElementToBeDisplayed(oPage_PurchaseBill.button_approve, 5);
                if(notApproved){
                    Cls_Generic_Methods.clickElement(driver,oPage_PurchaseBill.button_approve);
                }

                if (purchaseBillId >= noOfTabs) {
                    break;
                }

                driver.navigate().refresh();
                EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                oPage_CommonElements.button_closeTemplateWithoutSaving.click();
                Cls_Generic_Methods.customWait();
                EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills", driver);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PurchaseBill.list_purchaseBillRowList, 10);
                rowToClick=oPage_PurchaseBill.list_purchaseBillRowList;
            }

            m_assert.assertTrue(!duplicateFound, "Validate Purchase Bill Sequence - No Duplicates found");
            EHR_Helper.validateSequenceManager(m_assert, "Purchase Bill", getDepartment());
            tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
