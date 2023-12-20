package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Test;

import com.aventstack.extentreports.ExtentTest;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.Page_CommonElements;
import pages.store.PharmacyStore.Order.Page_Indent;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.DriverFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.EHR_Helper;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ExtentFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ParallelTestBase;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;


public class IndentOrderSequenceParallelTest extends ParallelTestBase {

    AtomicBoolean executed=new AtomicBoolean();
    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generateIndentOrderSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            if (executed.compareAndSet(false, true)) {
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "Indent Order", getDepartment());
            }
            Page_Indent oPage_Indent = new Page_Indent(driver);
            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

            EHR_Helper.login(m_assert, driver, user);
            barrier.await();
            EHR_Helper.selectStoreOnApp(sStore, driver);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Order", "Indent", driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
            Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_addNewIndent);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 10);

            EHR_Helper.waitForElementToBeDisplayed(oPage_Indent.select_vendorField, 10);
            EHR_Helper.selectByOptions(oPage_Indent.select_StoreInIndent, sStore.split("-")[0], driver);

            Cls_Generic_Methods.customWait();
            EHR_Helper.selectByOptions(oPage_Indent.select_indentType, "Normal", driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Indent.input_noteUnderIndentForPurchase, 10);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_noteUnderIndentForPurchase, "AUTO-TEST");
            Cls_Generic_Methods.customWait(5);

            //---------
            Cls_Generic_Methods.clickElement(oPage_Indent.list_ItemDescriptionsUnderIndentPurchase.get(0));

            EHR_Helper.waitForElementToBeDisplayed(oPage_Indent.input_quantityField, 10);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_quantityField, "1");

            Cls_Generic_Methods.customWait();

            barrier.await();

            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder), "Indent Order created");

            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    Set<String> indentOrderIdSet = new HashSet<>();
    boolean duplicateFound=false;
    @Test
    public void validateIndentOrderSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);
            Page_Indent oPage_Indent = new Page_Indent(driver);
            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

            EHR_Helper.login(m_assert, driver, user);

            EHR_Helper.selectStoreOnApp(sStore, driver);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Order", "Indent", driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
            int indentNo=0;
            for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                indentNo++;
                String createdAt=eDate.findElement(By.xpath("./td[1]")).getText();
                String indentOrderId=eDate.findElement(By.xpath("./td[5]")).getText();

                m_assert.assertInfo("Indent Order Created at "+createdAt+" | Indent Order ID : <b>"+indentOrderId+"</b>");

                if (indentOrderIdSet.contains(indentOrderId)) {
                    m_assert.assertFalse("Duplicate Indent Order Found: " + indentOrderId);
                    duplicateFound = true;
                } else {
                    indentOrderIdSet.add(indentOrderId);
                }
                if(indentNo>=noOfTabs) {
                    break;
                }
            }

            m_assert.assertTrue(!duplicateFound, "Validate Indent Order Sequence - No Duplicates found");
            EHR_Helper.validateSequenceManager(m_assert, "Indent Order", getDepartment());
            tearDown();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
