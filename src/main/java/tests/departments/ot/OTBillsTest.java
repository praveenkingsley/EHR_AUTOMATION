package tests.departments.ot;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.OT_Data;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.bills.Page_Bills;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.ipd.Page_IPD;
import pages.opd.Page_OPD;
import pages.ot.Page_OTBills;

public class OTBillsTest extends TestBase {

    Page_Navbar oPage_Navbar;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_IPD oPage_IPD;
    Page_OPD oPage_OPD;
    Page_OTBills oPage_OTBills;
    Page_CommonElements oPage_CommonElements;
    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();
    Page_Bills oPage_Bills;

    @Test(enabled = true, description = "Validate All Bills should display for Patient in OT")
    public void validateAllBills() {

        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_IPD = new Page_IPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        boolean bValidatePatientFound = false;
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();

        try {

            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OT");

            oPage_OTBills = new Page_OTBills(driver);
            String tabToSelect = OT_Data.tab_ALL;
            CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, tabToSelect);
            bValidatePatientFound = CommonActions.selectPatientNameInOT(oPage_OTBills.rows_patientNamesOnOT, concatPatientFullName);
            m_assert.assertTrue(bValidatePatientFound, "Validate patient " + concatPatientFullName + " is found");

            // Validate that Bills is visible

            if (bValidatePatientFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OTBills.button_Bills, 16);
                if (Cls_Generic_Methods.isElementDisplayed(oPage_OTBills.button_Bills)) {
                    m_assert.assertTrue(true, "Validate the Bills section is displayed");

                    Cls_Generic_Methods.clickElement(driver, oPage_OTBills.button_Bills);

                    // Validate all bill types are displaying in the bill drop down or not.

                    List<WebElement> allOptions = oPage_OTBills.rows_AllBills;
                    System.out.println(allOptions.size());
                    m_assert.assertTrue(allOptions.size() == 3, "Validate the Bills section is displayed");

                    for (int i = 0; i <= allOptions.size() - 1; i++) {

                        m_assert.assertInfo(Cls_Generic_Methods.getTextInElement(allOptions.get(i)));
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        } finally {

        }

    }

    @Test(enabled = true, description = "Validate New bill creation on OT")
    public void createNewBills() {

        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_IPD = new Page_IPD(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        oPage_Bills = new Page_Bills(driver);
        boolean bValidatePatientFound = false;
        String billOptionToSelect = "New Bill";
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();

        try {

            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OT");
            oPage_OTBills = new Page_OTBills(driver);
            String tabToSelect = OT_Data.tab_ALL;
            CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, tabToSelect);
            Cls_Generic_Methods.customWait();
            bValidatePatientFound = CommonActions.selectPatientNameInOT(oPage_OTBills.rows_patientNamesOnOT, concatPatientFullName);

            m_assert.assertTrue(bValidatePatientFound, "Validate patient " + concatPatientFullName + " is found");
            // Validate that Pre Operative forms section is visible

            if (bValidatePatientFound) {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickBills), "<b>₹ Bills</b> Button is clicked");
                Thread.sleep(2000);
                try {
                    m_assert.assertTrue(selectOptionFromBillsList(oPage_Bills.list_billTypeSelection, billOptionToSelect), "Validate " + billOptionToSelect + " bill is selected");
                } catch (Exception e) {
                    m_assert.assertFatal("Bill type is not selected");
                }
                fillBillForm();
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickBills), "<b>₹ Bills</b> Button is clicked");
                m_assert.assertInfo(oPage_Bills.list_billTypeSelection.size() + " options present");
            }

        } catch (Exception e) {
            m_assert.assertFatal("Validate bills failed");
            e.printStackTrace();
        } finally {

        }
    }

    public void fillBillForm() throws Exception {
        try {
            // String inputServiceValue = "Dressing";
            String paymentMode = "Cash";
            // String inputPackageValue = "Cornia";
            String remarkComments = "this is REMARK comment";
            String internalComments = "this is INTERNAL comment";
            String additionalDiscount = "10.0";
            String discountComments = "this is DISCOUNT comment";

            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Bills.text_billHeading, 5), "Bill form is visible");
            // click plus button and select package
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OTBills.button_clickPlusButton), "Plus button clicked for selecting package");

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.link_newPackage), "click on new package");

            for (WebElement billRow : oPage_Bills.bill_rowsOfServices) {

                if (billRow.isDisplayed()) {

                    try {
                        List<WebElement> packageDetailsOnRow = billRow.findElements(By.xpath("./child::*"));
                        for (WebElement itemOnRow : packageDetailsOnRow) {
                            String classAttr = Cls_Generic_Methods.getElementAttribute(itemOnRow, "class");
                            if (classAttr.contains("field_for_description")) {
                                Cls_Generic_Methods.clickElement(driver, itemOnRow);
                                Thread.sleep(500);
                                oPage_Bills.input_billService.sendKeys(Keys.DOWN, Keys.ENTER);
                                break;
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("Services/Packages are not selected");
                    }
                }
            }

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OTBills.input_billRemarksComment, remarkComments), "Remark comments are " + remarkComments);

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OTBills.input_billInternalComment, internalComments), "Internal comments are  " + internalComments);

            m_assert.assertTrue(Cls_Generic_Methods.clearValuesInElement(oPage_Bills.input_additionalDiscount));

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_additionalDiscount, additionalDiscount), "additional discount is entered as " + additionalDiscount);

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OTBills.input_billDiscountComment, discountComments), "Discount comments are  " + discountComments);

            // select mode of payment section
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_modeOfPayment, paymentMode), paymentMode + " option is selected for Mode Of Payment");

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills), "<b>Add</b> Save Final Bill Button is clicked");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 8);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_closeBill), "<b>Close</b> Button is clicked");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 8);

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Error occurred while filling Bill form - ");
        }

    }

    @Test(enabled = true, description = "Validate MarkAsDone button clicked and patient should send to My Queue Tab")
    public void validateMarkAsDone() {
        oPage_IPD = new Page_IPD(driver);
        oPage_OTBills = new Page_OTBills(driver);
        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String patientName = null;
        boolean bPatientNameFound = false;
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        ;
        // String AllTab = "All";
        String ComplateTab = "Completed";
        int initialMyQueueCount = -1;
        int updatedMyQueueCount = -1;

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("OT");

            oPage_OTBills = new Page_OTBills(driver);
            String tabToSelect = OT_Data.tab_ALL;
            CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, tabToSelect);
            Cls_Generic_Methods.customWait();
            try {
                initialMyQueueCount = CommonActions.getCountOfPatientsInTab(oPage_IPD.tabs_TabsOnIPD, ComplateTab);
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, tabToSelect), "Validate " + tabToSelect + " tab is selected");
                Thread.sleep(1000);
                bPatientNameFound = CommonActions.selectPatientNameInIpd(oPage_OTBills.rows_patientNamesOnOT, concatPatientFullName);

                if (bPatientNameFound) {
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OTBills.button_markAsDone, 10), "Mark As Done Button visible ");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OTBills.button_markAsDone), "Mark As Done Button clicked ");

                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OTBills.button_sendToWard, 10), "Send To Ward Button visible");

                    m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, ComplateTab), "Validate " + ComplateTab + " tab is selected");
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

                    updatedMyQueueCount = CommonActions.getCountOfPatientsInTab(oPage_IPD.tabs_TabsOnIPD, ComplateTab);

                    m_assert.assertTrue((updatedMyQueueCount > initialMyQueueCount), "Validate Patient Count has been increased from <b>" + initialMyQueueCount + "</b> to <b>" + updatedMyQueueCount + "</b> in " + ComplateTab);
                } else {
                    m_assert.assertTrue(false, "Admission is not Scheduled for patient: " + patientName);
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while scheduling admission of patient to IPD" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    public boolean selectOptionFromBillsList(List<WebElement> listOfBillsElement, String nameOfBillToSelect) {

        boolean optionIsSelected = false;
        try {
            for (WebElement billElement : listOfBillsElement) {
                String billValue = null;
                billValue = Cls_Generic_Methods.getTextInElement(billElement);
                if (nameOfBillToSelect.equals(billValue)) {
                    Cls_Generic_Methods.clickElement(driver, billElement);
                    optionIsSelected = true;
                    break;
                }
            }

        } catch (Exception e) {
            m_assert.assertFatal("Bill option is not selected ");
        }
        return optionIsSelected;
    }

}
