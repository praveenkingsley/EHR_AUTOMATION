package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Test;

import com.aventstack.extentreports.ExtentTest;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.Page_CommonElements;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_PurchaseReturn;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.DriverFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.EHR_Helper;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ExtentFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ParallelTestBase;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class PurchaseReturnSequenceParallelTest extends ParallelTestBase {
    String vendorName = "Supplier ABC";
    AtomicBoolean executed=new AtomicBoolean();
    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generatePurchaseReturnSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            if (executed.compareAndSet(false, true)) {
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "Purchase Return", getDepartment());
            }

            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

            EHR_Helper.login(m_assert, driver, user);
            barrier.await();
            m_assert.assertInfo(EHR_Helper.selectStoreOnApp(sStore, driver),"Selected Store - "+sStore);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();
            createPurchaseReturn(m_assert, driver);

            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void validatePurchaseReturnSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);
            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
            Page_Purchase oPage_Purchase = new Page_Purchase(driver);
            Page_PurchaseReturn oPage_PurchaseReturn=new Page_PurchaseReturn(driver);

            EHR_Helper.login(m_assert, driver, user);

            EHR_Helper.selectStoreOnApp(sStore, driver);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Return", driver);

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_purchaseTransactionHeaderList, 10);


            int purchaseReturnId = 0;

            boolean duplicateFound=false;
            Set<String> grnSet=new HashSet<>();

            for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

                purchaseReturnId++;

                Cls_Generic_Methods.clickElement(row);
                EHR_Helper.waitForElementToBeDisplayed(oPage_PurchaseReturn.text_transactionId,40);
                String return_id = oPage_PurchaseReturn.text_transactionId.getText().split(":")[1];



                m_assert.assertInfo("Purchase Return ID =<b>" + return_id + "</b>");

                if (grnSet.contains(return_id)) {
                    m_assert.assertFalse("Duplicate Purchase Return ID found: " + return_id);
                    duplicateFound=true;
                } else {
                    grnSet.add(return_id);
                }

                if (purchaseReturnId >= noOfTabs) {
                    break;
                }
            }

            m_assert.assertTrue(!duplicateFound, "Validate Purchase Return Sequence - No Duplicates found");
            EHR_Helper.validateSequenceManager(m_assert, "Purchase Return", getDepartment());
            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void createPurchaseReturn(SoftAssert m_assert, WebDriver driver) {
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_PurchaseReturn oPage_PurchaseReturn=new Page_PurchaseReturn(driver);

        try {
            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Return",driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 2);
            Cls_Generic_Methods.clickElement(driver, oPage_Purchase.button_purchaseNew);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Purchase.input_Vendor_search, 4);
            oPage_Purchase.input_Vendor_search.sendKeys(vendorName);
            Cls_Generic_Methods.customWait(5);

            for (WebElement eVendor : oPage_Purchase.list_select_vendor) {
                Cls_Generic_Methods.clickElementByJS(driver, eVendor);
                break;
            }

            EHR_Helper.waitForElementToBeDisplayed(oPage_PurchaseReturn.input_lotSearchBoxInPurchaseReturn, 5);


            oPage_PurchaseReturn.list_purchaseItemDescriptionList.get(0).click();
            EHR_Helper.waitForElementToBeDisplayed(oPage_PurchaseReturn.text_purchaseReturnQtyList,10);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseReturn.text_purchaseReturnQtyList, "1");

            Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseReturn.input_returnNotes, "AUTO-TEST");

            barrier.await();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver,oPage_Purchase.button_saveAddNewLot), "Purchase Return created");




        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to create Purchase Retuen" + e);
        }
    }

}
