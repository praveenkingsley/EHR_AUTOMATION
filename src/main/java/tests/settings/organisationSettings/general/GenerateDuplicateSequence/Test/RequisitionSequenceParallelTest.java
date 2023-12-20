package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Test;

import com.aventstack.extentreports.ExtentTest;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import data.EHR_Data;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.Page_CommonElements;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ROLRules;
import pages.store.PharmacyStore.Order.Page_Requisition;
import pages.store.PharmacyStore.Transaction.Page_Transfer;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.DriverFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.EHR_Helper;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ExtentFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ParallelTestBase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class RequisitionSequenceParallelTest extends ParallelTestBase {

    AtomicBoolean executed=new AtomicBoolean();
    String sStore=pharmacy;
    String sReceivingStore=centralHub1;
    String sDepartment="Facility Store to Central Hub";

    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generateRequisitionSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            if (executed.compareAndSet(false, true)) {
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "Requisition", sDepartment);
            }

            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

            EHR_Helper.login(m_assert, driver, user);
            barrier.await();
            m_assert.assertInfo(EHR_Helper.selectStoreOnApp(sStore, driver), "Selected Store - " + sStore);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition",driver);

            if (!createRequisition(driver, m_assert)) {
                m_assert.assertFatal("Unable to create Requisition Order");
            }

            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void validateRequisitionSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);
            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
            Page_Requisition oPage_Requisition=new Page_Requisition(driver);

            EHR_Helper.login(m_assert, driver, user);

            EHR_Helper.selectStoreOnApp(sStore, driver);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition",driver);

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Requisition.list_dateTimeOfRequisition, 10);

            int noReqId = 0;

            boolean duplicateFound = false;
            Set<String> reqSet = new HashSet<>();

            for (WebElement row :  oPage_Requisition.list_dateTimeOfRequisition) {
                noReqId++;
                Cls_Generic_Methods.clickElement(row);
                EHR_Helper.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 5);

                String requisitionId = row.getText().split("\\r?\\n")[1];

                m_assert.assertInfo("Generated Requisition ID : " + requisitionId);
                if (reqSet.contains(requisitionId)) {
                    m_assert.assertFalse("Duplicate Requisition ID found: " + requisitionId);
                    duplicateFound = true;
                } else {
                    reqSet.add(requisitionId);
                }

                if (noReqId >= noOfTabs) {
                    break;
                }
            }

            noReqId=0;

            List<WebElement> rowToClick=oPage_Requisition.list_dateTimeOfRequisition;

            while (true) {
                Cls_Generic_Methods.clickElement(rowToClick.get(noReqId));
                noReqId++;

                EHR_Helper.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 5);
                Cls_Generic_Methods.clickElement(driver,oPage_Requisition.button_viewOrderRequisition);

                boolean notApproved=EHR_Helper.waitForElementToBeDisplayed(oPage_Requisition.button_approveRequisition, 5);
                if(notApproved){
                    Cls_Generic_Methods.clickElement(oPage_Requisition.button_approveRequisition);
                    EHR_Helper.waitForElementToBeDisplayed(oPage_Requisition.button_confirmRequisition, 15);
                    Cls_Generic_Methods.clickElementByJS(driver,oPage_Requisition.button_confirmRequisition);
                }else{
                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);

                }

                if (noReqId >= noOfTabs) {
                    break;
                }

                driver.navigate().refresh();
                EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                oPage_CommonElements.button_closeTemplateWithoutSaving.click();
                Cls_Generic_Methods.customWait();
                EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition",driver);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Requisition.list_statusOfRequisition, 10);
                rowToClick=oPage_Requisition.list_dateTimeOfRequisition;
            }


            m_assert.assertTrue(!duplicateFound, "Validate Requisition Sequence - No Duplicates found");
            EHR_Helper.validateSequenceManager(m_assert, "Requisition", sDepartment);
            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }


    }

    public boolean createRequisition(WebDriver driver, SoftAssert m_assert) {

        Page_Requisition oPage_Requisition = new Page_Requisition(driver);
        Page_ROLRules oPage_ROLRules = new Page_ROLRules(driver);

        String sRequisitionType = "Normal";
        String sROL_ITEM = "Horlicks123";
        String sReqQuantity = "1";
        boolean bRequisitionOrderFound = false;

        try {

            EHR_Helper.waitForElementToBeDisplayed(oPage_Requisition.button_newRequisition, 3);
            Cls_Generic_Methods.clickElement(driver,oPage_Requisition.button_newRequisition);

            EHR_Helper.waitForElementToBeDisplayed(oPage_Requisition.dropdown_receivingStoreInRequisition, 3);
            Cls_Generic_Methods.selectElementByVisibleText(oPage_Requisition.select_receivingStore, sReceivingStore.split("-")[0]);

            Cls_Generic_Methods.selectElementByVisibleText(oPage_Requisition.select_reqType, sRequisitionType);


            Cls_Generic_Methods.clickElement(driver,oPage_Requisition.input_searchMedicineName);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_searchMedicineName, sROL_ITEM);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_searchMedicineName, ""+ Keys.ENTER);

            Cls_Generic_Methods.customWait();
            for (WebElement eItemName : oPage_Requisition.list_itemNameInPurchaseStore) {
                if (eItemName.getText().equalsIgnoreCase(sROL_ITEM)) {
                    Cls_Generic_Methods.clickElement(eItemName);
                    break;
                }
            }
            EHR_Helper.waitForElementToBeDisplayed(oPage_ROLRules.input_quantityRequisition, 10);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_ROLRules.input_quantityRequisition, sReqQuantity);


            barrier.await();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Requisition.button_saveRequisition),"New Requisition Created");
            bRequisitionOrderFound=true;
            EHR_Helper.waitForElementToBeDisplayed(oPage_Requisition.button_newRequisition, 10);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bRequisitionOrderFound;
    }
}
