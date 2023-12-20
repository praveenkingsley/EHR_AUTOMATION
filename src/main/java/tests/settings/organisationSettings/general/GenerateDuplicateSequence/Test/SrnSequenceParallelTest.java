package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Test;

import com.aventstack.extentreports.ExtentTest;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.Page_CommonElements;
import pages.store.OpticalStore.Page_SalesOrder;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_SRN;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.DriverFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.EHR_Helper;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ExtentFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ParallelTestBase;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class SrnSequenceParallelTest extends ParallelTestBase {

    AtomicBoolean executed = new AtomicBoolean();

    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generateSrnSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            if (executed.compareAndSet(false, true)) {
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "SRN", getDepartment());
            }

            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

            EHR_Helper.login(m_assert, driver, user);
            barrier.await();
            m_assert.assertInfo(EHR_Helper.selectStoreOnApp(sStore, driver), "Selected Store - " + sStore);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            if (!createSRN(m_assert, driver)) {
                m_assert.assertFatal("Unable to create SRN Transaction");
            }

            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void validateSrnSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);
            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
            Page_SRN oPage_SRN = new Page_SRN(driver);
            Page_Purchase oPage_Purchase = new Page_Purchase(driver);


            EHR_Helper.login(m_assert, driver, user);

            EHR_Helper.selectStoreOnApp(sStore, driver);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "SRN", driver);

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_transactionPurchaseList, 10);


            int rowNoTransaction = 0;

            boolean duplicateFound = false;
            Set<String> grnSet = new HashSet<>();

            for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {
                rowNoTransaction++;

                Cls_Generic_Methods.clickElement(row);
                EHR_Helper.waitForElementToBeDisplayed(oPage_SRN.text_transactionIdRhs, 10);

                String srn_no = oPage_SRN.text_transactionIdRhs.getText();
                m_assert.assertInfo("SRN Transaction ID =<b>" + srn_no + "</b>");

                if (grnSet.contains(srn_no)) {
                    m_assert.assertFalse("Duplicate SRN ID found: " + srn_no);
                    duplicateFound = true;
                } else {
                    grnSet.add(srn_no);
                }

                if (rowNoTransaction >= noOfTabs) {
                    break;
                }
            }

            m_assert.assertTrue(!duplicateFound, "Validate SRN Transaction Sequence - No Duplicates found");
            EHR_Helper.validateSequenceManager(m_assert, "SRN", getDepartment());
            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    boolean clicked = false;

    private boolean createSRN(SoftAssert m_assert, WebDriver driver) {
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_SRN oPage_SRN = new Page_SRN(driver);
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        String vendorName = "Supplier ABC";

        boolean flag = false;

        try {
            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "SRN", driver);
            Cls_Generic_Methods.clickElement(oPage_SRN.button_addNew);

            EHR_Helper.waitForElementToBeDisplayed(oPage_SRN.header_stockReceiveNoteHeader, 20);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_SRN.input_srnNotes, "AUTO-TEST");

            for (WebElement srnOrderItem : oPage_SRN.list_salesOrderListInSRNTemplate) {

                if(clicked){
                    clicked=false;
                    continue;
                }
                srnOrderItem.click();
                clicked = true;
                Cls_Generic_Methods.customWait();
                break;
            }

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_SRN.list_itemDescriptionListInSrn,10);
            for (WebElement eDescription : oPage_SRN.list_itemDescriptionListInSrn) {
                int index = oPage_SRN.list_itemDescriptionListInSrn.indexOf(eDescription);

                Cls_Generic_Methods.sendKeysIntoElement(oPage_SRN.list_unitCostListInSrn.get(index), "10");
                Cls_Generic_Methods.customWait();
            }

            EHR_Helper.waitForElementToBeDisplayed(oPage_SRN.dropdown_vendorDropdownArrow, 2);
            Cls_Generic_Methods.clickElement(oPage_SRN.dropdown_vendorDropdownArrow);
            EHR_Helper.waitForElementToBeDisplayed(oPage_SalesOrder.input_searchInputForSelectField, 2);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_searchInputForSelectField, vendorName);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_SalesOrder.list_selectVendorInStore, 2);

            for (WebElement eVendor : oPage_SalesOrder.list_selectVendorInStore) {
                if (eVendor.getText().contains(vendorName)) {
                    Cls_Generic_Methods.clickElement(eVendor);
                    break;
                }
            }

            Cls_Generic_Methods.selectElementByIndex(oPage_SRN.select_otherCharges, 1);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_SRN.input_otherChargesAmount, "10");

            Cls_Generic_Methods.clickElement(oPage_SRN.dropdown_selectBillType);
            Cls_Generic_Methods.selectElementByVisibleText(oPage_SRN.dropdown_selectBillType, "Bill");
            Cls_Generic_Methods.sendKeysIntoElement(oPage_SRN.input_billNumber, generateRandomNumber());

            Cls_Generic_Methods.clickElement(oPage_SRN.input_billDate);
            EHR_Helper.waitForElementToBeDisplayed(oPage_SRN.input_todayBillDate, 3);
            Cls_Generic_Methods.clickElement(oPage_SRN.input_todayBillDate);


            barrier.await();
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver,oPage_Purchase.button_saveAddNewLot), "SRN Created");
            EHR_Helper.waitForElementToBeDisplayed(oPage_SRN.button_addNew,10);
            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to create SON" + e);
        }
        return flag;
    }

    private String generateRandomNumber() {

        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        return String.valueOf(randomNumber);
    }
}
