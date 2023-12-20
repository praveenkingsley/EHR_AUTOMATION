package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Test;

import com.aventstack.extentreports.ExtentTest;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.Page_CommonElements;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_SON;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.DriverFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.EHR_Helper;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ExtentFactory;
import tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util.ParallelTestBase;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class SonSequenceParallelTest extends ParallelTestBase {
    AtomicBoolean executed = new AtomicBoolean();

    @Test(invocationCount = noOfTabs, threadPoolSize = noOfTabs)
    public void generateSonSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);

            if (executed.compareAndSet(false, true)) {
                EHR_Helper.launchDriverInInstalledBrowser();
                EHR_Helper.validateSequenceManager(m_assert, "Son Transaction", getDepartment());
            }

            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

            EHR_Helper.login(m_assert, driver, user);
            barrier.await();
            m_assert.assertInfo(EHR_Helper.selectStoreOnApp(sStore, driver), "Selected Store - " + sStore);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            if (!createSON(m_assert,driver)) {
                m_assert.assertFatal("Unable to create SON Transaction");
            }

            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void validateSonSequence() {
        System.out.println("-------------->>>>" + DriverFactory.getInstance().getDriver().hashCode());

        try {
            WebDriver driver = DriverFactory.getInstance().getDriver();
            ExtentTest test = ExtentFactory.getInstance().getExtent();

            SoftAssert m_assert = new SoftAssert(driver, extent, test, className);
            Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
            Page_Purchase oPage_Purchase = new Page_Purchase(driver);
            Page_SON oPage_SON=new Page_SON(driver);

            EHR_Helper.login(m_assert, driver, user);

            EHR_Helper.selectStoreOnApp(sStore, driver);
            EHR_Helper.switchToOtherTab(driver);
            EHR_Helper.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            oPage_CommonElements.button_closeTemplateWithoutSaving.click();
            Cls_Generic_Methods.customWait();

            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "SON", driver);

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_SON.list_SONTransactions, 10);


            int rowNoTransaction = 0;

            boolean duplicateFound = false;
            Set<String> grnSet = new HashSet<>();

            for (WebElement row : oPage_SON.list_SONTransactions) {
                rowNoTransaction++;

                Cls_Generic_Methods.clickElement(row);
                EHR_Helper.waitForElementToBeDisplayed(oPage_SON.button_Approve, 10);

                String son_no = oPage_SON.text_transactionIDKeyAndValue.getText();
                m_assert.assertInfo("Son Transaction ID =<b>" + son_no + "</b>");

                if (grnSet.contains(son_no)) {
                    m_assert.assertFalse("Duplicate Son ID found: " + son_no);
                    duplicateFound = true;
                } else {
                    grnSet.add(son_no);
                }

                if (rowNoTransaction >= noOfTabs) {
                    break;
                }
            }

            barrier.await();
            m_assert.assertTrue(!duplicateFound, "Validate Son Transaction Sequence - No Duplicates found");
            EHR_Helper.validateSequenceManager(m_assert, "Son Transaction", getDepartment());
            tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private boolean createSON(SoftAssert m_assert, WebDriver driver) {
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_SON oPage_SON = new Page_SON(driver);
        String batchNo = generateRandomNumber();

        boolean flag=false;

        try {
            EHR_Helper.selectOptionFromLeftInInventoryStorePanel("Transaction", "SON", driver);
            Cls_Generic_Methods.clickElement(oPage_SON.button_addNewButton);

            EHR_Helper.waitForElementToBeDisplayed(oPage_SON.header_addStockOpeningNoteHeader, 8);

            for (WebElement e : oPage_SON.list_medicationNameOnLeft) {
                Cls_Generic_Methods.clickElement(e);
                Cls_Generic_Methods.customWait();
                break;
            }

            boolean itemClicked = EHR_Helper.waitForElementToBeDisplayed(oPage_SON.header_addNewLot, 15);

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
                Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_sellingPrice);
                oPage_Purchase.input_sellingPrice.sendKeys("150");

                Cls_Generic_Methods.clickElement(driver, oPage_Purchase.button_saveLot);
                EHR_Helper.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);
                Cls_Generic_Methods.clickElement(driver, oPage_Purchase.button_SaveChanges);

            } else {
                m_assert.assertFatal("Item not selected");
            }

            Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_documentNumber, generateRandomNumber());
            Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_transactionNotes, "AUTO-TEST");

            barrier.await();
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SON.button_saveChanges), "SON Transaction Created ");
            flag=true;

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
