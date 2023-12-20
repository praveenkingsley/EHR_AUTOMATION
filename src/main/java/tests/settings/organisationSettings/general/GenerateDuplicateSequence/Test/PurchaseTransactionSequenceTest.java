package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Test;

import com.aventstack.extentreports.ExtentTest;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.Page_CommonElements;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.DriverFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.EHR_Helper;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ExtentFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ParallelTestBase;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;


public class PurchaseTransactionSequenceTest extends ParallelTestBase {

    String vendorName = "Supplier ABC";

    AtomicBoolean executed=new AtomicBoolean();

    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generatePurchaseTransactionSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            if (executed.compareAndSet(false, true)) {
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "Purchase Transaction", getDepartment());
            }

            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

            EHR_Helper.login(m_assert, driver, user);
            barrier.await();
            m_assert.assertInfo(EHR_Helper.selectStoreOnApp(sStore, driver),"Selected Store - "+sStore);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();
            createPurchaseGrn(m_assert, driver);

            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void validatePurchaseGrnSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);
            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
            Page_Purchase oPage_Purchase = new Page_Purchase(driver);

            EHR_Helper.login(m_assert, driver, user);

            EHR_Helper.selectStoreOnApp(sStore, driver);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN", driver);

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_purchaseTransactionHeaderList, 10);


            int purchaseGrnId = 0;

            boolean duplicateFound=false;
            Set<String> grnSet=new HashSet<>();

            for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

                purchaseGrnId++;

                Cls_Generic_Methods.clickElement(row);
                EHR_Helper.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 10);

                String grnCreatedAt = oPage_Purchase.text_grnCreatedAt.getText();
                String grn_no = oPage_Purchase.text_transactionID.getText();
                m_assert.assertInfo("Purchase Grn Created At =" + grnCreatedAt + " ->>> Purchase Grn no =<b>" + grn_no + "</b>");

                if (grnSet.contains(grn_no)) {
                    m_assert.assertFalse("Duplicate Purchase Grn no found: " + grn_no);
                    duplicateFound=true;
                } else {
                    grnSet.add(grn_no);
                }

                if (purchaseGrnId >= noOfTabs) {
                    break;
                }
            }

            purchaseGrnId=0;

            List<WebElement> rowToClick=oPage_Purchase.list_transactionPurchaseList;

            while (true) {
                Cls_Generic_Methods.clickElement(rowToClick.get(purchaseGrnId));
                purchaseGrnId++;

                boolean notApproved=EHR_Helper.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 5);
                if(notApproved){
                    Cls_Generic_Methods.clickElement(driver,oPage_Purchase.button_approvePurchaseTransaction);
                    EHR_Helper.waitForElementToBeDisplayed(oPage_Purchase.button_Yes, 10);
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.button_Yes);
                }

                if (purchaseGrnId >= noOfTabs) {
                    break;
                }

                driver.navigate().refresh();
                EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                oPage_CommonElements.button_closeTemplateWithoutSaving.click();
                Cls_Generic_Methods.customWait();
                EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN", driver);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_purchaseTransactionHeaderList, 10);
                rowToClick=oPage_Purchase.list_transactionPurchaseList;
            }

            m_assert.assertTrue(!duplicateFound, "Validate Purchase/GRN Sequence - No Duplicates found");
            EHR_Helper.validateSequenceManager(m_assert, "Purchase Transaction", getDepartment());
            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void createPurchaseGrn(SoftAssert m_assert, WebDriver driver) {
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        String billNumber = "BILL_" + generateRandomNumber();
        String batchNo = generateRandomNumber();
        String billType = "Bill";


        try {
            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN", driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 10);
            Cls_Generic_Methods.clickElement(driver, oPage_Purchase.button_purchaseNew);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Purchase.input_Vendor_search, 4);
            oPage_Purchase.input_Vendor_search.sendKeys(vendorName);
            Cls_Generic_Methods.customWait(5);

            for (WebElement eVendor : oPage_Purchase.list_select_vendor) {
                Cls_Generic_Methods.clickElementByJS(driver, eVendor);
                break;
            }

            EHR_Helper.waitForElementToBeDisplayed(oPage_Purchase.input_billNumber, 10);
            boolean itemClicked = false;

            for (WebElement eItemName : oPage_Purchase.list_itemNameInPurchaseStore) {
                Cls_Generic_Methods.clickElementByJS(driver, eItemName);
                itemClicked = true;
                break;
            }


            if (itemClicked) {
                EHR_Helper.waitForElementToBeDisplayed(oPage_Purchase.header_addNewLot, 15);
                oPage_Purchase.input_batchNumber.sendKeys(batchNo);

                if (EHR_Helper.waitForElementToBeDisplayed(oPage_Purchase.select_subStore, 2)) {
                    Cls_Generic_Methods.selectElementByIndex(oPage_Purchase.select_subStore, 1);
                }

                if (EHR_Helper.waitForElementToBeDisplayed(oPage_Purchase.input_expiryDate, 5) && Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_expiryDate, "value").isEmpty()) {
                    Cls_Generic_Methods.clickElement(driver, oPage_Purchase.input_expiryDate);
                    EHR_Helper.waitForElementToBeDisplayed(oPage_Purchase.select_expiryDateYear, 1);
                    String currentYear = Cls_Generic_Methods.getSelectedValue(oPage_Purchase.select_expiryDateYear);
                    int year = Integer.parseInt(currentYear);
                    int newYear = year + 3;

                    Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_expiryDateYear, Integer.toString(newYear));
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.select_expiryDateDay);
                }

                Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_unitCostWOTax);
                oPage_Purchase.input_unitCostWOTax.sendKeys("100");
                Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_packageQuantity);

                oPage_Purchase.input_packageQuantity.sendKeys("1");
                Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_sellingPrice);
                oPage_Purchase.input_sellingPrice.sendKeys("150");

                Cls_Generic_Methods.clickElement(driver, oPage_Purchase.button_saveLot);
                EHR_Helper.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);
                Cls_Generic_Methods.clickElement(driver, oPage_Purchase.button_SaveChanges);

            } else {
                m_assert.assertFatal("Item not selected");
            }

            oPage_Purchase.input_transactionNotes.sendKeys("AUTO - TEST");
            Cls_Generic_Methods.clickElement(driver, oPage_Purchase.dropdown_selectBillType);
            Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType);

            oPage_Purchase.input_billNumber.sendKeys(billNumber);
            Cls_Generic_Methods.clickElement(driver, oPage_Purchase.input_billDate);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
            Cls_Generic_Methods.clickElement(driver, oPage_Purchase.input_todayBillDate);

            Cls_Generic_Methods.selectElementByIndex(oPage_Purchase.select_otherCharges, 1);
            oPage_Purchase.input_otherChargesAmount.sendKeys("10");
            Cls_Generic_Methods.customWait();

            barrier.await();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot), "Purchase Transaction created");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to create Purchase GRN" + e);
        }
    }

    private String generateRandomNumber(){

        Random random=new Random();
        int randomNumber =random.nextInt(10000);
        return String.valueOf(randomNumber);
    }

}
