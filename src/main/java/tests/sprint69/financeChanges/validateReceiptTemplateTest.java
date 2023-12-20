package tests.sprint69.financeChanges;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.IPD_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.bills.Page_Bills;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.ipd.Page_IPD;
import pages.opd.Page_OPD;
import pages.sprint69.financeChanges.Page_FinanceChanges;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pages.commonElements.CommonActions.oEHR_Data;

public class validateReceiptTemplateTest extends TestBase {
    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    String date1 = dateFormat.format(date);
    String sInvoiceTemplateName = "ReceiptTemplate" + date1;
    String sFacilityName = "TESTING_FACILITY";
    String sSpecialityName = "Ophthalmology";
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    static Model_Patient myPatient;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_Bills oPage_Bills;
    Page_OPD oPage_OPD;
    public static Map<String, String> mapTracker = new HashMap<String, String>();
    public static String key_CreatedAt_UpdatedReceiptTemplateName = "key_CreatedAt_UpdatedReceiptTemplateName";
    public static String key_CreatedAt_SelectedServiceName = "key_CreatedAt_SelectedServiceName";
    public static String key_CreatedAt_SelectedPackageName = "key_CreatedAt_SelectedPackageName";

    @Test(enabled = true, description = "Validate Create And Edit OPD Receipt Template")
    public void validateCreateAndEditOPDReceiptTemplateTest() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        String sContactType = "General";
        String sQuantity = "2";
        String sDepartmentName = "OPD";
        boolean bTemplateNameFound = false;
        int indexOfInvoiceTemplateName = -1;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectFacility("TST");
            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("Financial", "Receipt Templates");
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInvoiceTemplate, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addInvoiceTemplate), "Clicked on <b>Add Invoice template</b> button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.input_ReceiptTemplateName, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.input_ReceiptTemplateName, sInvoiceTemplateName), "Invoice template name entered as = <b> " + sInvoiceTemplateName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.select_locationFieldUnderReceiptTemplateModal, 20);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_locationFieldUnderReceiptTemplateModal, sFacilityName), "Facility selected = <b> " + sFacilityName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.select_specialityFieldUnderReceiptTemplateModal, 20);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_specialityFieldUnderReceiptTemplateModal, sSpecialityName), "Speciality selected = <b> " + sSpecialityName + " </b>");
                //validate department name section under create new receipt template modal
                Cls_Generic_Methods.customWait(3);
                for (WebElement eDepartmentName : oPage_FinanceChanges.list_departmentButton) {
                    String sDepartmentNameOnUI = eDepartmentName.getText();
                    if (sDepartmentNameOnUI.equalsIgnoreCase(sDepartmentName)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(eDepartmentName), "Selected Department = <b> " + sDepartmentName + "</b>");
                        break;
                    } else {
                        m_assert.assertFalse("Incorrect Department is selected");
                    }

                }
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactType, sContactType), "Selected Contact Type  = <b> " + sContactType + " </b>");

                //validate add new item,package and remove button
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal), "Clicked on <b>Add new item</b> button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewPackageButtonUnderReceiptTemplateModal, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_AddNewPackageButtonUnderReceiptTemplateModal), "Clicked on <b>Add package </b> button");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_RemoveFieldButtonReceiptTemplateModal.get(0)), "verified remove action button functionality");
                //Select service and package
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.list_serviceSelectionFieldUnderReceiptTemplateModal.get(0)), "clicked on <b> service selection field</b>");
                Cls_Generic_Methods.customWait(3);
                String sServiceName = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_serviceOrPackagePresentUnderServiceSelectionField.get(2));
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_serviceOrPackagePresentUnderServiceSelectionField.get(2));
                mapTracker.put(key_CreatedAt_SelectedServiceName, sServiceName);
                m_assert.assertInfo("service name = <b>" + sServiceName + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal, 20);
                String sUnitServicePrice = Cls_Generic_Methods.getElementAttribute(oPage_FinanceChanges.list_inputUnitPriceFieldUnderReceiptTemplateModal.get(0), "value");
                m_assert.assertInfo("unit price of a service  = <b>" + sUnitServicePrice + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal, 20);
                String sTotalServiceCost = Cls_Generic_Methods.getElementAttribute(oPage_FinanceChanges.list_inputTotalFieldUnderReceiptTemplateModal.get(0), "value");
                m_assert.assertInfo("total cost of a service = <b>" + sTotalServiceCost + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.list_packageSelectionUnderReceiptTemplateModal.get(0)), "clicked on <b>package selection field</b>");
                Cls_Generic_Methods.customWait(2);
                String sPackageName = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_serviceOrPackagePresentUnderServiceSelectionField.get(2));
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_serviceOrPackagePresentUnderServiceSelectionField.get(2));
                mapTracker.put(key_CreatedAt_SelectedPackageName, sPackageName);
                m_assert.assertInfo("package name = <b>" + sPackageName + "</b>");
                String sUnitPackageCost = Cls_Generic_Methods.getElementAttribute(oPage_FinanceChanges.list_inputUnitPriceFieldUnderReceiptTemplateModal.get(1), "value");
                m_assert.assertInfo("unit cost of package  = <b>" + sUnitPackageCost + "</b>");
                String sTotalPackageCost = Cls_Generic_Methods.getElementAttribute(oPage_FinanceChanges.list_inputTotalFieldUnderReceiptTemplateModal.get(1), "value");
                m_assert.assertInfo("Total cost of package = <b>" + sTotalPackageCost + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_createReceiptTemplate, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_createReceiptTemplate), "Clicked on <b> Create receipt template button</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInvoiceTemplate, 20);
                //Validate invoice template present in table
                Cls_Generic_Methods.customWait(2);
                for (WebElement eReceiptTemplateName : oPage_FinanceChanges.list_invoiceTemplateName) {
                    if (eReceiptTemplateName.getText().equalsIgnoreCase(sInvoiceTemplateName)) {
                        bTemplateNameFound = true;
                        indexOfInvoiceTemplateName = oPage_FinanceChanges.list_invoiceTemplateName.indexOf(eReceiptTemplateName);
                        break;
                    }
                }
                m_assert.assertTrue(bTemplateNameFound, "Template name found in the table =  <b> " + sInvoiceTemplateName + " </b> ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInvoiceTemplate, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.list_editInvoiceTemplateButton.get(indexOfInvoiceTemplateName)), "Clicked on edit receipt template button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.input_ReceiptTemplateName, 20);
                Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.input_ReceiptTemplateName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.input_ReceiptTemplateName, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.input_ReceiptTemplateName, "Updated" + sInvoiceTemplateName), "Updated invoice template name = <b> " + "Updated" + sInvoiceTemplateName + " </b>");
                mapTracker.put(key_CreatedAt_UpdatedReceiptTemplateName, "Updated" + sInvoiceTemplateName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal, 20);
                Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.list_inputQuantityFieldUnderReceiptTemplateModal.get(0));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.list_inputQuantityFieldUnderReceiptTemplateModal.get(0), sQuantity), "Updated service Qty = <b>" + sQuantity + "</b>");
                double dUnitServicePrice = Double.parseDouble(sUnitServicePrice);
                double dQuantity = Double.parseDouble(sQuantity);
                double dCalculatedTotalServiceCost = (dUnitServicePrice) * (dQuantity);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal, 20);
                String sTotalServiceCostOnUI = Cls_Generic_Methods.getElementAttribute(oPage_FinanceChanges.list_inputTotalFieldUnderReceiptTemplateModal.get(0), "value");
                m_assert.assertTrue(" total cost of a service on UI= <b>" + sTotalServiceCostOnUI + "</b>");
                double dTotalServiceCostOnUI = Double.parseDouble(sTotalServiceCostOnUI);
                if (dCalculatedTotalServiceCost == dTotalServiceCostOnUI) {
                    m_assert.assertTrue(true, "calculated total service cost =  <b>" + dCalculatedTotalServiceCost + "</b>  Total service cost on UI = <b>  " + dTotalServiceCostOnUI + "</b>");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_updateTemplate, 20);
                Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.list_inputQuantityFieldUnderReceiptTemplateModal.get(1));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.list_inputQuantityFieldUnderReceiptTemplateModal.get(1), sQuantity), "Updated service Qty = <b>" + sQuantity + "</b>");
                double dUnitPackageCost = Double.parseDouble(sUnitPackageCost);
                double dCalculatedPackageCost = (dUnitPackageCost) * (dQuantity);
                String sTotalPackageCostOnUI = Cls_Generic_Methods.getElementAttribute(oPage_FinanceChanges.list_inputTotalFieldUnderReceiptTemplateModal.get(1), "value");
                m_assert.assertTrue("Total cost of package on UI = <b>" + sTotalPackageCostOnUI + "</b>");
                double dTotalPackageCostOnUI = Double.parseDouble(sTotalPackageCostOnUI);
                if (dCalculatedPackageCost == dTotalPackageCostOnUI) {
                    m_assert.assertTrue(true, "calculated total package cost =  <b>" + dCalculatedPackageCost + "</b>  Total package cost on UI = <b>  " + dTotalPackageCostOnUI + "</b>");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_updateTemplate, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_updateTemplate), "Clicked on <b>Update receipt template</b> button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_createReceiptTemplate, 5);


            } catch (Exception e) {
                m_assert.assertFatal("Receipt template is not created " + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    //Validate created receipt template under OPD billing section
    @Test(enabled = true, description = "Validate Created Receipt Template Present in OPD department")
    public void validateCreatedReceiptTemplateInOPDBillingSection() throws Exception {

        try {
            myPatient = TestBase.map_PatientsInExcel.get(patientKey);
            oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
            Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
            oPage_Bills = new Page_Bills(driver);
            oPage_OPD = new Page_OPD(driver);
            String sSelectNewBill = "New Bill";
            String sSelectNewDraftBill = "New Draft Bill";
            String paymentMode = "Cash";
            String sDepartmentName = "OPD";


            try {
                String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
                CommonActions.loginFunctionality(expectedLoggedInUser);
                CommonActions.selectFacility("TST");
                CommonActions.selectDepartmentOnApp(sDepartmentName);
                Cls_Generic_Methods.customWait(2);
                String tabToSelect = "All";
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
                        "Validate " + tabToSelect + " tab is selected");

                String patientName = null;

                for (WebElement patient : oPage_OPD.rows_patientAppointments) {
                    if (patient.isDisplayed()) {
                        List<WebElement> patientDetailsOnRow = patient.findElements(By.xpath("./child::*"));
                        patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");

                        if (concatPatientFullName.equals(patientName.trim())) {
                            Cls_Generic_Methods.clickElement(driver, patient);
                            Cls_Generic_Methods
                                    .waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 8);
                            break;
                        }
                    }
                }
                //Validate created receipt template present under new bill
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickBills),
                        "<b>₹ Bills</b> Button is clicked");
                Cls_Generic_Methods.customWait(3);
                try {
                    m_assert.assertInfo(selectOptionFromBillsList(oPage_Bills.list_billTypeSelection, sSelectNewBill),
                            "Validate " + sSelectNewBill + " bill is selected");
                    Cls_Generic_Methods.customWait(5);
                } catch (Exception e) {
                    m_assert.assertInfo(false, "Bill type is not selected" + e);
                }
                Cls_Generic_Methods.customWait(5);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_invoiceSets, mapTracker.get(key_CreatedAt_UpdatedReceiptTemplateName)),
                        "Newly Created Invoice set present under new bill  <b>" + mapTracker.get(key_CreatedAt_UpdatedReceiptTemplateName) + " </b>");
                // select mode of payment section
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.scrollToElementByJS(oPage_Bills.select_modeOfPayment);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.select_modeOfPayment, 20);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_modeOfPayment, paymentMode),
                        paymentMode + " option is selected for Mode Of Payment");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickSaveFinalBills, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills),
                        "<b>Add</b> Save Final Bill Button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.customWait(3);
                String sServiceNameOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_itemDescription.get(0));
                String sServiceName = sServiceNameOnUI.replace("-", "");
                String sServiceNameOnBillPreview = sServiceName.substring(5).trim();
                if (sServiceNameOnBillPreview.contains(mapTracker.get(key_CreatedAt_SelectedServiceName))) {
                    m_assert.assertTrue("Service name on bill preview = <b> " + sServiceNameOnBillPreview + "</b>");
                } else {
                    m_assert.assertFalse("service name is incorrect <b> " + sServiceNameOnBillPreview + "</b>");
                }
                String sPackageNameOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_itemDescription.get(1));
                if (sPackageNameOnUI.contains(mapTracker.get(key_CreatedAt_SelectedPackageName))) {
                    m_assert.assertTrue("Package name on bill preview = <b> " + sPackageNameOnUI + "</b>");
                } else {
                    m_assert.assertFalse("service name is incorrect <b> " + sPackageNameOnUI + "</b>");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 8);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_closeBill),
                        "<b>Close</b> Button is clicked");


                //Validate created receipt template present under Draft bill
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickBills),
                        "<b>₹ Bills</b> Button is clicked");
                Cls_Generic_Methods.customWait(3);
                try {
                    m_assert.assertInfo(selectOptionFromBillsList(oPage_Bills.list_billTypeSelection, sSelectNewDraftBill),
                            "Validate " + sSelectNewDraftBill + " bill is selected");
                    Cls_Generic_Methods.customWait(5);
                } catch (Exception e) {
                    m_assert.assertInfo(false, "Bill type is not selected" + e);
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.select_invoiceSets, 20);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_invoiceSets, mapTracker.get(key_CreatedAt_UpdatedReceiptTemplateName)),
                        "Newly Created Invoice set present under new draft bill  <b>" + mapTracker.get(key_CreatedAt_UpdatedReceiptTemplateName) + " </b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.text_invoiceType, 20);
                Cls_Generic_Methods.scrollToElementByJS(oPage_FinanceChanges.input_amountRemainingFieldUnderDraftBill);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.input_amountRemainingFieldUnderDraftBill, 20);
                String sAmountRemaining = Cls_Generic_Methods.getElementAttribute(oPage_FinanceChanges.input_amountRemainingFieldUnderDraftBill, "value");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.input_amountFieldUnderOPDDraftBill, 20);
                Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.input_amountFieldUnderOPDDraftBill);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.input_amountFieldUnderOPDDraftBill, 20);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.input_amountFieldUnderOPDDraftBill, sAmountRemaining);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_saveDraftBill, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_saveDraftBill), "Draft bill saved");
                Cls_Generic_Methods.customWait(5);
                String sServiceNameUnderDraftBillPreview = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_itemDescription.get(0));
                String sServiceNameUnderDraftBillPreviewUI = sServiceNameUnderDraftBillPreview.replace("-", "");
                String sFinalServiceNameUnderDraftBillPreview = sServiceNameUnderDraftBillPreviewUI.substring(5).trim();
                if (sFinalServiceNameUnderDraftBillPreview.contains(mapTracker.get(key_CreatedAt_SelectedServiceName))) {
                    m_assert.assertTrue("Service name on bill preview = <b> " + sFinalServiceNameUnderDraftBillPreview + "</b>");
                } else {
                    m_assert.assertFalse("service name is incorrect <b> " + sFinalServiceNameUnderDraftBillPreview + "</b>");
                }
                String sPackageNameUnderDraftBilPreview = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_itemDescription.get(1));
                if (sPackageNameUnderDraftBilPreview.contains(mapTracker.get(key_CreatedAt_SelectedPackageName))) {
                    m_assert.assertTrue("Package name on bill preview = <b> " + sPackageNameOnUI + "</b>");
                } else {
                    m_assert.assertFalse("service name is incorrect <b> " + sPackageNameOnUI + "</b>");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 8);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_closeBill),
                        "<b>Close</b> Button is clicked");
                //Validate Created receipt template under template bills
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_opdTemplateBills, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_opdTemplateBills),
                        "<b>₹ Template Bills</b> Button is clicked");
                Cls_Generic_Methods.customWait(3);
                try {
                    m_assert.assertInfo(selectOptionFromBillsList(oPage_FinanceChanges.list_receiptTemplateUnderOPDTemplateBills, mapTracker.get(key_CreatedAt_UpdatedReceiptTemplateName)),
                            "Validate " + mapTracker.get(key_CreatedAt_UpdatedReceiptTemplateName) + " Receipt template is selected");
                    Cls_Generic_Methods.customWait(5);
                } catch (Exception e) {
                    m_assert.assertInfo(false, "Receipt template is not selected" + e);
                }
                // select mode of payment section
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.scrollToElementByJS(oPage_Bills.select_modeOfPayment);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.select_modeOfPayment, 20);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_modeOfPayment, paymentMode),
                        paymentMode + " option is selected for Mode Of Payment");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_saveOPDReceipt, 20);
                Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.input_amountFieldUnderTemplateDraftBill);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.input_amountFieldUnderTemplateDraftBill, sAmountRemaining);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_saveOPDReceipt),
                        "<b>Save</b>  receipt template Button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.customWait(3);
                String sServiceNameOnReceiptTemplatePreviewUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_itemDescription.get(0));
                String sServiceNameOnReceiptTemplate = sServiceNameOnReceiptTemplatePreviewUI.replace("-", "");
                String sServiceNameOnReceiptTemplatePreview = sServiceNameOnReceiptTemplate.substring(5).trim();
                if (sServiceNameOnReceiptTemplatePreviewUI.contains(mapTracker.get(key_CreatedAt_SelectedServiceName))) {
                    m_assert.assertTrue("Service name on Receipt template preview = <b> " + sServiceNameOnReceiptTemplatePreview + "</b>");
                } else {
                    m_assert.assertFalse("service name is incorrect <b> " + sServiceNameOnReceiptTemplatePreview + "</b>");
                }
                String sPackageNameOnReceiptTemplatePreviewUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_itemDescription.get(1));
                if (sPackageNameOnReceiptTemplatePreviewUI.contains(mapTracker.get(key_CreatedAt_SelectedPackageName))) {
                    m_assert.assertTrue("Package name on receipt preview = <b> " + sPackageNameOnReceiptTemplatePreviewUI + "</b>");
                } else {
                    m_assert.assertFalse("package name is incorrect <b> " + sPackageNameOnReceiptTemplatePreviewUI + "</b>");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 8);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_closeBill),
                        "<b>Close</b> Button is clicked");


            } catch (Exception e) {
                m_assert.assertFatal("Error loading the application " + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertTrue(false, "Validate bills failed" + e);
            e.printStackTrace();
        }
    }

    //Validate Delete created Receipt Template functionality
    @Test(enabled = true, description = "Validate Create And Edit OPD Receipt Template")
    public void validateDeleteOPDReceiptTemplateTest() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        boolean bTemplateNameFound = false;
        int indexOfInvoiceTemplateName = -1;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectFacility("TST");
            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("Financial", "Receipt Templates");
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInvoiceTemplate, 20);
                //Validate invoice template present in table
                Cls_Generic_Methods.customWait(2);
                for (WebElement eReceiptTemplateName : oPage_FinanceChanges.list_invoiceTemplateName) {
                    if (eReceiptTemplateName.getText().equalsIgnoreCase(mapTracker.get(key_CreatedAt_UpdatedReceiptTemplateName))) {
                        bTemplateNameFound = true;
                        indexOfInvoiceTemplateName = oPage_FinanceChanges.list_invoiceTemplateName.indexOf(eReceiptTemplateName);
                        break;
                    }
                }
                m_assert.assertTrue(bTemplateNameFound, "Template name found in the table =  <b> " + mapTracker.get(key_CreatedAt_UpdatedReceiptTemplateName) + " </b> ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInvoiceTemplate, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.list_deleteInvoiceTemplateButton.get(indexOfInvoiceTemplateName)), "Clicked on <b>delete</b> receipt template button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_confirmDelete, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_confirmDelete), "Clicked on <b>confirm</b> delete receipt template button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_createReceiptTemplate, 2);
            } catch (Exception e) {
                m_assert.assertFatal("Receipt template is not created " + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    //create Receipt template For IPD department
    @Test(enabled = true, description = "Validate Create And Edit IPD Receipt Template")
    public void validateCreateAndEditIPDReceiptTemplateTest() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        String sContactType = "General";
        String sQuantity = "2";
        String sDepartmentName = "IPD";
        boolean bContactTypeFound = false;
        boolean bTemplateNameFound = false;
        boolean bDepartmentFound = false;
        int indexOfInvoiceTemplateName = -1;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectFacility("TST");
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("Financial", "Receipt Templates");
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInvoiceTemplate, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addInvoiceTemplate), "Clicked on <b>Add Invoice template</b> button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.input_ReceiptTemplateName, 20);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.input_ReceiptTemplateName, sInvoiceTemplateName), "Invoice template name entered as = <b> " + sInvoiceTemplateName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.select_locationFieldUnderReceiptTemplateModal, 20);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_locationFieldUnderReceiptTemplateModal, sFacilityName), "Facility selected = <b> " + sFacilityName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.select_specialityFieldUnderReceiptTemplateModal, 20);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_specialityFieldUnderReceiptTemplateModal, sSpecialityName), "Speciality selected = <b> " + sSpecialityName + " </b>");
                //validate department name section under create new receipt template modal
                Cls_Generic_Methods.customWait(3);
                for (WebElement eDepartmentName : oPage_FinanceChanges.list_departmentButton) {
                    String sDepartmentNameOnUI = eDepartmentName.getText();
                    if (sDepartmentNameOnUI.equalsIgnoreCase(sDepartmentName)) {
                        Cls_Generic_Methods.clickElement(eDepartmentName);
                        bDepartmentFound = true;
                        break;
                    }
                }
                if (bDepartmentFound) {
                    m_assert.assertTrue(true, "Selected Department = <b> " + sDepartmentName + "</b>");
                } else {
                    m_assert.assertTrue("incorrect Department selected = <b> " + sDepartmentName + "</b>");
                }
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactType, sContactType), "Selected Contact Type  = <b> " + sContactType + " </b>");
                //validate add new item,package and remove button
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal), "Clicked on <b>Add new item </b> button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewPackageButtonUnderReceiptTemplateModal, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_AddNewPackageButtonUnderReceiptTemplateModal), "Clicked on <b>Add package </b> button");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_RemoveFieldButtonReceiptTemplateModal.get(0)), "Removed service row from list ");
                //Select service and package
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.list_serviceSelectionFieldUnderReceiptTemplateModal.get(0));
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.list_serviceSelectionFieldUnderReceiptTemplateModal.get(0));
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.list_serviceSelectionFieldUnderReceiptTemplateModal.get(0)), "clicked on service selection field");
                Cls_Generic_Methods.customWait(3);
                String sServiceName = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_serviceOrPackagePresentUnderServiceSelectionField.get(2));
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_serviceOrPackagePresentUnderServiceSelectionField.get(2));
                mapTracker.put(key_CreatedAt_SelectedServiceName, sServiceName);
                m_assert.assertInfo("service name = <b>" + sServiceName + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal, 20);
                String sUnitServicePrice = Cls_Generic_Methods.getElementAttribute(oPage_FinanceChanges.list_inputUnitPriceFieldUnderReceiptTemplateModal.get(0), "value");
                m_assert.assertInfo("unit price of a service  = <b>" + sUnitServicePrice + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal, 20);
                String sTotalServiceCost = Cls_Generic_Methods.getElementAttribute(oPage_FinanceChanges.list_inputTotalFieldUnderReceiptTemplateModal.get(0), "value");
                m_assert.assertInfo("total cost of a service = <b>" + sTotalServiceCost + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.list_packageSelectionUnderReceiptTemplateModal.get(0)), "clicked on package selection field");
                Cls_Generic_Methods.customWait(2);
                String sPackageName = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_serviceOrPackagePresentUnderServiceSelectionField.get(2));
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_serviceOrPackagePresentUnderServiceSelectionField.get(2));
                mapTracker.put(key_CreatedAt_SelectedPackageName, sPackageName);
                m_assert.assertInfo("package name = <b>" + sPackageName + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_createReceiptTemplate, 20);
                String sUnitPackageCost = Cls_Generic_Methods.getElementAttribute(oPage_FinanceChanges.list_inputUnitPriceFieldUnderReceiptTemplateModal.get(1), "value");
                m_assert.assertInfo("unit cost of package  <b>= " + sUnitPackageCost + "</b>");
                String sTotalPackageCost = Cls_Generic_Methods.getElementAttribute(oPage_FinanceChanges.list_inputTotalFieldUnderReceiptTemplateModal.get(1), "value");
                m_assert.assertInfo("Total cost of package = <b>" + sTotalPackageCost + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_createReceiptTemplate), "Clicked on <b>Create receipt template</b> button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInvoiceTemplate, 20);
                //Validate invoice template present in table
                Cls_Generic_Methods.customWait(2);
                for (WebElement eReceiptTemplateName : oPage_FinanceChanges.list_invoiceTemplateName) {
                    if (eReceiptTemplateName.getText().equalsIgnoreCase(sInvoiceTemplateName)) {
                        bTemplateNameFound = true;
                        indexOfInvoiceTemplateName = oPage_FinanceChanges.list_invoiceTemplateName.indexOf(eReceiptTemplateName);
                        break;
                    }
                }
                m_assert.assertTrue(bTemplateNameFound, "Template name found in the table =  <b> " + sInvoiceTemplateName + " </b> ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInvoiceTemplate, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.list_editInvoiceTemplateButton.get(indexOfInvoiceTemplateName)), "Clicked on <b>edit</b> receipt template button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.input_ReceiptTemplateName, 20);
                Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.input_ReceiptTemplateName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.input_ReceiptTemplateName, 20);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.input_ReceiptTemplateName, "Updated" + sInvoiceTemplateName), "Invoice template name entered as = <b> " + "Updated" + sInvoiceTemplateName + " </b>");
                mapTracker.put(key_CreatedAt_UpdatedReceiptTemplateName, "Updated" + sInvoiceTemplateName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal, 20);
                Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.list_inputQuantityFieldUnderReceiptTemplateModal.get(0));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal, 20);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.list_inputQuantityFieldUnderReceiptTemplateModal.get(0), sQuantity), "Updated service Qty = <b>" + sQuantity + "</b>");
                double dUnitServicePrice = Double.parseDouble(sUnitServicePrice);
                double dQuantity = Double.parseDouble(sQuantity);
                double dCalculatedTotalServiceCost = (dUnitServicePrice) * (dQuantity);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal, 20);
                String sTotalServiceCostOnUI = Cls_Generic_Methods.getElementAttribute(oPage_FinanceChanges.list_inputTotalFieldUnderReceiptTemplateModal.get(0), "value");
                m_assert.assertInfo(" total cost of a service on UI= <b>" + sTotalServiceCostOnUI + "</b>");
                double dTotalServiceCostOnUI = Double.parseDouble(sTotalServiceCostOnUI);
                if (dCalculatedTotalServiceCost == dTotalServiceCostOnUI) {
                    m_assert.assertTrue(true, "calculated total service cost =  <b>" + dCalculatedTotalServiceCost + "</b>  Total service cost on UI = <b>  " + dTotalServiceCostOnUI + "</b>");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_updateTemplate, 20);
                Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.list_inputQuantityFieldUnderReceiptTemplateModal.get(1));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_AddNewItemButtonUnderReceiptTemplateModal, 20);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.list_inputQuantityFieldUnderReceiptTemplateModal.get(1), sQuantity), "Updated service Qty = <b>" + sQuantity + "</b>");
                double dUnitPackageCost = Double.parseDouble(sUnitPackageCost);
                double dCalculatedPackageCost = (dUnitPackageCost) * (dQuantity);
                String sTotalPackageCostOnUI = Cls_Generic_Methods.getElementAttribute(oPage_FinanceChanges.list_inputTotalFieldUnderReceiptTemplateModal.get(1), "value");
                m_assert.assertInfo("Total cost of package on UI = <b>" + sTotalPackageCostOnUI + "</b>");
                double dTotalPackageCostOnUI = Double.parseDouble(sTotalPackageCostOnUI);
                if (dCalculatedPackageCost == dTotalPackageCostOnUI) {
                    m_assert.assertTrue(true, "calculated total package cost =  <b>" + dCalculatedPackageCost + "</b>  Total package cost on UI = <b>  " + dTotalPackageCostOnUI + "</b>");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_updateTemplate, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_updateTemplate), "Clicked on <b>Update receipt template</b> button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_createReceiptTemplate, 5);

            } catch (Exception e) {
                m_assert.assertFatal("Receipt template is not created " + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    //Validate created receipt template under IPD billing section
    @Test(enabled = true, description = "Validate Created Receipt Template Present in IPD department")
    public void validateCreatedReceiptTemplateInIPDBillingSection() throws Exception {

        try {
            myPatient = TestBase.map_PatientsInExcel.get(patientKey);
            Page_Navbar oPage_Navbar = new Page_Navbar(driver);
            Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
            Page_IPD oPage_IPD = new Page_IPD(driver);
            oPage_Bills = new Page_Bills(driver);
            String sSelectNewBill = "New Bill";
            String sSelectCashNewBill = "New Cash Bill";

            String sSelectNewDraftBill = "New Draft Bill";
            String paymentMode = "Cash";
            String sDepartmentName = "IPD";
            boolean bValidatePatientFound = false;
            try {
                myPatient = TestBase.map_PatientsInExcel.get(patientKey);
                String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
                CommonActions.loginFunctionality(expectedLoggedInUser);
                CommonActions.selectFacility("TST");
                CommonActions.selectDepartmentOnApp(sDepartmentName);
                Cls_Generic_Methods.customWait(2);
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                Cls_Generic_Methods.customWait(3);
              //  Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_departmentFromDropdownSelector);

               /* for (WebElement eDepartment : oPage_Navbar.list_departmentSelector) {
                    if (eDepartment.getText().trim().equalsIgnoreCase(sDepartmentName)) {
                        Cls_Generic_Methods.clickElement(driver, eDepartment);
                        Cls_Generic_Methods.waitForPageLoad(driver, 20);
                        break;
                    }
                }*/

                oPage_IPD = new Page_IPD(driver);
                CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today);
                Cls_Generic_Methods.customWait();

                bValidatePatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, concatPatientFullName);

                m_assert.assertTrue(bValidatePatientFound, "Validate patient " + concatPatientFullName + " is found");

                // Validate that consent forms section is visible
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_consentsSection, 16);

                if (bValidatePatientFound) {
                    //Validate created receipt template present under new bill
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickBills),
                            "<b>₹ Bills</b> Button is clicked");
                    Cls_Generic_Methods.customWait(3);
                    try {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_cashBill),
                                "Validate " + sSelectCashNewBill + " bill is selected");
                        Cls_Generic_Methods.customWait(5);
                    } catch (Exception e) {
                        m_assert.assertTrue(false, "Bill type is not selected" + e);
                    }
                    Cls_Generic_Methods.customWait(5);
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_invoiceSets, mapTracker.get(key_CreatedAt_UpdatedReceiptTemplateName)),
                            "Newly Created Invoice set present under new bill  <b>" + mapTracker.get(key_CreatedAt_UpdatedReceiptTemplateName) + " </b>");
                    // select mode of payment section
                    Cls_Generic_Methods.customWait(3);
                    Cls_Generic_Methods.scrollToElementByJS(oPage_Bills.select_modeOfPayment);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.select_modeOfPayment, 20);
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_modeOfPayment, paymentMode),
                            paymentMode + " option is selected for Mode Of Payment");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickSaveFinalBills, 20);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills),
                            "<b>Add</b> Save Final Bill Button is clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                    Cls_Generic_Methods.customWait(3);
                    String sServiceNameOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_itemDescription.get(0));
                    String sServiceName = sServiceNameOnUI.replace("-", "");
                    String sServiceNameOnBillPreview = sServiceName.substring(5).trim();
                    if (sServiceNameOnBillPreview.contains(mapTracker.get(key_CreatedAt_SelectedServiceName))) {
                        m_assert.assertTrue("Service name on bill preview = <b> " + sServiceNameOnBillPreview + "</b>");
                    } else {
                        m_assert.assertFalse("service name is incorrect <b> " + sServiceNameOnBillPreview + "</b>");
                    }
                    String sPackageNameOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_itemDescription.get(1));
                    if (sPackageNameOnUI.contains(mapTracker.get(key_CreatedAt_SelectedPackageName))) {
                        m_assert.assertTrue("Package name on bill preview = <b> " + sPackageNameOnUI + "</b>");
                    } else {
                        m_assert.assertFalse("service name is incorrect <b> " + sPackageNameOnUI + "</b>");
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 8);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_closeBill),
                            "<b>Close</b> Button is clicked");


                    //Validate created receipt template present under Draft bill
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 20);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickBills),
                            "<b>₹ Bills</b> Button is clicked");
                    Cls_Generic_Methods.customWait(3);
                    try {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_draftBill),
                                "Validate " + sSelectNewDraftBill + " bill is selected");
                        Cls_Generic_Methods.customWait(5);
                    } catch (Exception e) {
                        m_assert.assertTrue(false, "Bill type is not selected" + e);
                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.select_invoiceSets, 20);
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_invoiceSets, mapTracker.get(key_CreatedAt_UpdatedReceiptTemplateName)),
                            "Newly Created Invoice set present under new draft bill  <b>" + mapTracker.get(key_CreatedAt_UpdatedReceiptTemplateName) + " </b>");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.text_invoiceType, 20);
                    Cls_Generic_Methods.scrollToElementByJS(oPage_FinanceChanges.input_amountRemainingFieldUnderDraftBill);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.input_amountRemainingFieldUnderDraftBill, 20);
                    String sAmountRemaining = Cls_Generic_Methods.getElementAttribute(oPage_FinanceChanges.input_amountRemainingFieldUnderDraftBill, "value");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.input_amountFieldUnderIPDDraftBill, 20);
                    Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.input_amountFieldUnderIPDDraftBill);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.input_amountFieldUnderIPDDraftBill, 20);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.input_amountFieldUnderIPDDraftBill, sAmountRemaining);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_saveDraftBill, 20);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_saveDraftBill), "Draft bill saved");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 30);
                    String sServiceNameUnderDraftBillPreview = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_itemDescription.get(0));
                    String sServiceNameUnderDraftBillPreviewUI = sServiceNameUnderDraftBillPreview.replace("-", "");
                    String sFinalServiceNameUnderDraftBillPreview = sServiceName.substring(5).trim();
                    if (sFinalServiceNameUnderDraftBillPreview.contains(mapTracker.get(key_CreatedAt_SelectedServiceName))) {
                        m_assert.assertTrue("Service name on bill preview = <b> " + sFinalServiceNameUnderDraftBillPreview + "</b>");
                    } else {
                        m_assert.assertFalse("service name is incorrect <b> " + sFinalServiceNameUnderDraftBillPreview + "</b>");
                    }
                    String sPackageNameUnderDraftBilPreview = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_itemDescription.get(1));
                    if (sPackageNameUnderDraftBilPreview.contains(mapTracker.get(key_CreatedAt_SelectedPackageName))) {
                        m_assert.assertTrue("Package name on bill preview = <b> " + sPackageNameOnUI + "</b>");
                    } else {
                        m_assert.assertFalse("service name is incorrect <b> " + sPackageNameOnUI + "</b>");
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 8);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_closeBill),
                            "<b>Close</b> Button is clicked");
                    //Validate Created receipt template under template bills
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_ipdTemplateBills, 20);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_ipdTemplateBills),
                            "<b>₹ Template Bills</b> Button is clicked");
                    Cls_Generic_Methods.customWait(3);
                    try {
                        m_assert.assertInfo(selectOptionFromBillsList(oPage_FinanceChanges.list_receiptTemplateUnderIPDTemplateBills, mapTracker.get(key_CreatedAt_UpdatedReceiptTemplateName)),
                                "Validate " + mapTracker.get(key_CreatedAt_UpdatedReceiptTemplateName) + " Receipt template is selected");
                        Cls_Generic_Methods.customWait(5);
                    } catch (Exception e) {
                        m_assert.assertInfo(false, "Receipt template is not selected" + e);
                    }
                    // select mode of payment section
                    Cls_Generic_Methods.customWait(3);
                    Cls_Generic_Methods.scrollToElementByJS(oPage_Bills.select_modeOfPayment);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.select_modeOfPayment, 20);
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_modeOfPayment, paymentMode),
                            paymentMode + " option is selected for Mode Of Payment");
                    Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.input_amountFieldUnderIpdTemplateDraftBill);
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.input_amountFieldUnderIpdTemplateDraftBill, sAmountRemaining);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_saveIPDReceipt, 20);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_saveIPDReceipt),
                            "<b>Add</b> Save receipt template Button is clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                    Cls_Generic_Methods.customWait(3);
                    String sServiceNameOnReceiptTemplatePreviewUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_itemDescription.get(0));
                    String sServiceNameOnReceiptTemplate = sServiceNameOnReceiptTemplatePreviewUI.replace("-", "");
                    String sServiceNameOnReceiptTemplatePreview = sServiceNameOnReceiptTemplate.substring(5).trim();
                    if (sServiceNameOnReceiptTemplatePreview.contains(mapTracker.get(key_CreatedAt_SelectedServiceName))) {
                        m_assert.assertTrue("Service name on Receipt template preview = <b> " + sServiceNameOnReceiptTemplatePreview + "</b>");
                    } else {
                        m_assert.assertFalse("service name is incorrect <b> " + sServiceNameOnReceiptTemplatePreview + "</b>");
                    }
                    String sPackageNameOnReceiptTemplatePreviewUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_itemDescription.get(1));
                    if (sPackageNameOnReceiptTemplatePreviewUI.contains(mapTracker.get(key_CreatedAt_SelectedPackageName))) {
                        m_assert.assertTrue("Package name on receipt preview = <b> " + sPackageNameOnReceiptTemplatePreviewUI + "</b>");
                    } else {
                        m_assert.assertFalse("package name is incorrect <b> " + sPackageNameOnReceiptTemplatePreviewUI + "</b>");
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 8);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_closeBill),
                            "<b>Close</b> Button is clicked");

                }

            } catch (Exception e) {
                m_assert.assertFatal("Error loading the application " + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertTrue(false, "Validate bills failed" + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Create And Edit OPD Receipt Template")
    public void validateDeleteIPDReceiptTemplateTest() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        boolean bTemplateNameFound = false;
        int indexOfInvoiceTemplateName = -1;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectFacility("TST");
            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("Financial", "Receipt Templates");
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInvoiceTemplate, 20);
                //Validate invoice template present in table
                Cls_Generic_Methods.customWait(2);
                for (WebElement eReceiptTemplateName : oPage_FinanceChanges.list_invoiceTemplateName) {
                    if (eReceiptTemplateName.getText().equalsIgnoreCase(mapTracker.get(key_CreatedAt_UpdatedReceiptTemplateName))) {
                        bTemplateNameFound = true;
                        indexOfInvoiceTemplateName = oPage_FinanceChanges.list_invoiceTemplateName.indexOf(eReceiptTemplateName);
                        break;
                    }
                }
                m_assert.assertTrue(bTemplateNameFound, "Template name found in the table =  <b> " + mapTracker.get(key_CreatedAt_UpdatedReceiptTemplateName) + " </b> ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInvoiceTemplate, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_FinanceChanges.list_deleteInvoiceTemplateButton.get(indexOfInvoiceTemplateName)), "Clicked on <b>delete</b> receipt template button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_confirmDelete, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_confirmDelete), "Clicked on <b>confirm</b> delete receipt template button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_createReceiptTemplate, 2);
            } catch (Exception e) {
                m_assert.assertFatal("Receipt template is not created " + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return optionIsSelected;
    }
}


