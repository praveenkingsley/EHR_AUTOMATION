package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Test;
import com.aventstack.extentreports.ExtentTest;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.Page_CommonElements;
import pages.store.PharmacyStore.Transaction.Page_PaymentRequisitionForm;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.DriverFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.EHR_Helper;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ExtentFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ParallelTestBase;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;


public class PrfSequenceParallelTest extends ParallelTestBase {

    int rowNo = 0;
    AtomicBoolean executed=new AtomicBoolean();

    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generatePrfSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            if (executed.compareAndSet(false, true)) {
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "PRF", getDepartment());
            }

            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
            Page_PaymentRequisitionForm oPage_PaymentRequisitionForm = new Page_PaymentRequisitionForm(driver);

            EHR_Helper.login(m_assert, driver, user);
            barrier.await();
            m_assert.assertInfo(EHR_Helper.selectStoreOnApp(sStore, driver),"Selected Store - "+sStore);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 30);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form",driver);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PaymentRequisitionForm.list_prfHeaderList, 60);

            Cls_Generic_Methods.clickElement(driver, oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm);
            EHR_Helper.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.select_vendor, 4);
            Cls_Generic_Methods.selectElementByIndex(oPage_PaymentRequisitionForm.select_vendor, 1);
            Cls_Generic_Methods.customWait(3);

            Cls_Generic_Methods.clickElement(driver, oPage_PaymentRequisitionForm.row_createPaymentRequisitionForm.get(rowNo));
            rowNo++;
            Cls_Generic_Methods.customWait();

            barrier.await();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_PaymentRequisitionForm.button_saveChanges),"<b>PRF</b> created");

            Cls_Generic_Methods.customWait(4);

            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void validatePrfSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);
            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
            Page_PaymentRequisitionForm oPage_PaymentRequisitionForm=new Page_PaymentRequisitionForm(driver);

            EHR_Helper.login(m_assert, driver, user);

            EHR_Helper.selectStoreOnApp(sStore, driver);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form",driver);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PaymentRequisitionForm.list_prfHeaderList, 6);


            int noPrfId = 0;

            boolean duplicateFound=false;
            Set<String> duplicates=new HashSet<>();
            for (WebElement row : oPage_PaymentRequisitionForm.list_prfCreatedList) {
                noPrfId++;

                Cls_Generic_Methods.clickElementByJS(driver,row);
                EHR_Helper.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.text_rhs_prfNo,10);
                String prfId=oPage_PaymentRequisitionForm.text_rhs_prfNo.getText();
                String prfCreatedAt=oPage_PaymentRequisitionForm.text_rhs_createdAt.getText();

                m_assert.assertInfo("PRF created at " + prfCreatedAt + " --> PRF ID : <b>" + prfId + "</b>");

                if (duplicates.contains(prfId)) {
                    m_assert.assertFalse("Duplicate Purchase Grn no found: " + prfId);
                    duplicateFound=true;
                } else {
                    duplicates.add(prfId);
                }

                if (noPrfId >= noOfTabs) {
                    break;
                }
            }

            m_assert.assertTrue(!duplicateFound, "Validate PRF Sequence - No Duplicates found");
            EHR_Helper.validateSequenceManager(m_assert, "PRF", getDepartment());
            tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
