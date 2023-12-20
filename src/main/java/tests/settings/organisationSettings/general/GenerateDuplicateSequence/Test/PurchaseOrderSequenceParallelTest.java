package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Test;

import com.aventstack.extentreports.ExtentTest;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.Page_CommonElements;
import pages.store.PharmacyStore.Order.Page_PurchaseOrder;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.DriverFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.EHR_Helper;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ExtentFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ParallelTestBase;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class PurchaseOrderSequenceParallelTest extends ParallelTestBase {

    String vendorName = "Supplier ABC";

    AtomicBoolean executed=new AtomicBoolean();

    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generatePurchaseOrderSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            if (executed.compareAndSet(false, true)) {
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "Purchase Order", getDepartment());
            }

            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
            Page_PurchaseOrder oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
            Page_Purchase oPage_Purchase=new Page_Purchase(driver);

            EHR_Helper.login(m_assert, driver, user);
            barrier.await();
            m_assert.assertInfo(EHR_Helper.selectStoreOnApp(sStore, driver), "Selected Store - " + sStore);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            //------------------

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase", driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 20);
            Cls_Generic_Methods.clickElement(driver, oPage_PurchaseOrder.button_newOrder);
            EHR_Helper.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_headerCreatePurchaseOrder, 10);

            EHR_Helper.selectByOptions(oPage_PurchaseOrder.dropdown_store, sStore.split("-")[0], driver);

            EHR_Helper.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_Vendor_search, 4);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, vendorName);
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, "" + Keys.DOWN + Keys.ENTER);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, "" + Keys.DOWN + Keys.ENTER);
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectByOptions(oPage_PurchaseOrder.dropdown_poType, "Normal", driver);
            Cls_Generic_Methods.customWait();

            boolean itemClicked=false;
            for (WebElement eMedicineName : oPage_PurchaseOrder.list_namesOfMedicinesOnLeftInSearchResultPO) {
                Cls_Generic_Methods.clickElement(eMedicineName);
                itemClicked=true;
                break;
            }

            if (itemClicked) {
                EHR_Helper.waitForElementToBeDisplayed(oPage_Purchase.header_addNewLot, 15);

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

                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(driver, oPage_Purchase.button_saveLot);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PurchaseOrder.button_removeOtherCharges_createPo, 15);

            } else {
                m_assert.assertFatal("Item not selected");
            }

            Cls_Generic_Methods.clickElement(driver,oPage_PurchaseOrder.button_removeOtherCharges_createPo.get(0));

            barrier.await();

            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_PurchaseOrder.button_saveOrder), "<b>Purchase Order</b> created");
            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" "+e);
        }


    }

    @Test
    public void validatePurchaseOrderSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);
            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
            Page_PurchaseOrder oPage_PurchaseOrder=new Page_PurchaseOrder(driver);
            Page_Purchase oPage_Purchase=new Page_Purchase(driver);

            EHR_Helper.login(m_assert, driver, user);

            EHR_Helper.selectStoreOnApp(sStore, driver);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase", driver);

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PurchaseOrder.list_purchaseOrderHeaderList, 10);

            int purchaseOrderId = 0;

            boolean duplicateFound = false;
            Set<String> duplicates = new HashSet<>();
            for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {
                purchaseOrderId++;

                Cls_Generic_Methods.clickElementByJS(driver, row);
                EHR_Helper.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_approveOrder, 30);
                String purchaseOrder_no = oPage_PurchaseOrder.text_purchaseOrderId.getText();
                String purchaseOrderCreatedAt = oPage_PurchaseOrder.text_poCreatedAt.getText().split("\\|")[0].trim();

                m_assert.assertInfo("Purchase Bill created at " + purchaseOrderCreatedAt + " --> Purchase Bill No : <b>" + purchaseOrder_no + "</b>");

                if (duplicates.contains(purchaseOrder_no)) {
                    m_assert.assertFalse("Duplicate Purchase Grn no found: " + purchaseOrder_no);
                    duplicateFound = true;
                } else {
                    duplicates.add(purchaseOrder_no);
                }

                if (purchaseOrderId >= noOfTabs) {
                    break;
                }

            }

            m_assert.assertTrue(!duplicateFound, "Validate Purchase Order Sequence - No Duplicates found");
            EHR_Helper.validateSequenceManager(m_assert, "Purchase Order", getDepartment());
            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
