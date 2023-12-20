package tests.departments.ipd;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.IPD_Data;
import net.bytebuddy.utility.RandomString;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.bills.Page_Bills;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.sprint69.financeChanges.Page_FinanceChanges;

import java.text.DecimalFormat;

import static pages.commonElements.CommonActions.getRandomUniqueString;

public class IPDSubpackageItemLevelDiscountTest extends TestBase {
    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_NewPatientRegisteration oPage_NewPatientRegisteration;
    Page_IPD oPage_IPD;
    Page_Bills oPage_Bills;

    @Test(enabled = true, description = "Create patient in ipd for SubPackage level discount validation")
    public void scheduleAdmissionInIpd() {
        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);

        String sAddNewCase = "Add New Case";

        boolean bPatientFound = false;

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectFacility("TST");
            CommonActions.selectDepartmentOnApp("IPD");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
            myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();

            try {

                try {
                    if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                        CommonActions.openPatientRegisterationAndAppointmentForm();
                    } else {
                        CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm, "Patient Details");
                        Cls_Generic_Methods.customWait();
                    }
                } catch (NoSuchElementException e1) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, 12);

                // Entering Essential Form Data
                if (!myPatient.getsSALUTATION().isEmpty()) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()), "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
                }

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()), "First Name is entered as - " + myPatient.getsFIRST_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, myPatient.getsMOBILE_NUMBER()), "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

                Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduleAdmission.radioBtn_emergency);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduleAdmission.radioBtn_cashlessYes);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.button_selectCase, 8);
                Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_selectCase);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.button_allCasesDropDown, 8);

                Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_allCasesDropDown);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_ScheduleAdmission.select_allCasesDropDown, sAddNewCase);
                Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_allCasesDropDown);
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_IPD.radio_yesCashlessHospitalisationUnderScheduleAdmissionForm), "Clicked on Cashless hospitalisation Radio button = <b>YES</b> ");
                Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_createAdmission);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.header_assignBed, 15);

                Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_closeAdmissionForm);
                oPage_IPD = new Page_IPD(driver);
                Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 30);
                Cls_Generic_Methods.customWait(4);
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today), "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");
                Cls_Generic_Methods.customWait(5);
                bPatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnScheduledTodayTab, concatPatientFullName);
                m_assert.assertTrue(bPatientFound, "Validate that the patient " + concatPatientFullName + " is created in IPD");

                Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 30);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "create cash bill and validate the Subpackage item level  Discount")
    public void createCashBillAndValidateSubPackageItemLevelDiscount() {
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills = new Page_Bills(driver);
        oPage_IPD = new Page_IPD(driver);
        boolean bPatientFound = false;
        String paymentMode = "Cash";
        String sPackageName = "AutomationPackageDataDisplaySelf";
        String sDiscountAmountOnFirstSubPackageItem = "4";
        String sDiscountAmountOnSecondSubPackageItem = "3";
        String sGlobalDiscount = "10";
        String sDiscountReason = "Reason" + getRandomUniqueString(6);
        String sSecondPackage = "Testing Facility";

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectDepartmentOnApp("IPD");
            myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
            m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today), "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");
            Cls_Generic_Methods.customWait(10);

            bPatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnScheduledTodayTab, concatPatientFullName);
            m_assert.assertTrue(bPatientFound, "Validate that the patient " + concatPatientFullName + " is created in IPD");

            double dFirstSubpackageItemDiscountOnBillPreview = 0;
            double dSecondSubpackageItemDiscountOnBillPreview = 0;
            double dOverDiscountAmountOnPackageInPreview = 0;
            double dGrossPrice = 0;
            double dPaymentReceivedOnBillPreview = 0;
            if (bPatientFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills), "Clicked on bills button");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_cashBill);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 30);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickIPDPlusActionButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Bills.button_newPackage);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_deleteItemPackage.get(0));
                Cls_Generic_Methods.clickElement(oPage_Bills.select_package.get(0));
                boolean myPackageFound = false;
                for (WebElement eItemList : oPage_Bills.list_packageOrServiceNameUnderDescriptionField) {
                    String sPackageNameOnUI = Cls_Generic_Methods.getTextInElement(eItemList);
                    if (sPackageNameOnUI.equals(sPackageName)) {
                        Cls_Generic_Methods.clickElement(eItemList);
                        myPackageFound = true;
                        break;
                    }

                }
                m_assert.assertTrue(myPackageFound, "Selected package name = <b>" + sPackageName + "</b> ");
                Cls_Generic_Methods.customWait(4);
                String sQTY = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_Quantity.get(0), "value");
                String sUnitPrice = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(0), "value");
                String sGrossPrice = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(0), "value");
                dGrossPrice = Double.parseDouble(sGrossPrice);
                String sNetPrice = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dOverAllNetPriceOnPackage = Double.parseDouble(sNetPrice);
                Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(0));
                Cls_Generic_Methods.customWait(4);

                Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_deleteItemLevelDiscount.get(0));
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_subpackageItemLevelDiscountField.get(0))) {
                    m_assert.assertWarn("First Subpackage item level Discount field is  displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("First Subpackage item level Discount field is not displaying as user deleted overall discount field ");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_subpackageItemLevelDiscountField.get(1))) {
                    m_assert.assertWarn("Second Subpackage item level Discount field is displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Second Subpackage item level Discount field is not displaying as user deleted overall discount field ");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_mainDiscountFieldOnPackage.get(0))) {
                    m_assert.assertWarn("Package level Discount field is  displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Package level Discount field is not displaying as user deleted overall discount field ");
                }
                Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(0));
                Cls_Generic_Methods.customWait(4);
                String sUnitCostOnFirstItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemUnitCost.get(0));
                String sNoOfUnitsCostPerDayOnUI = sUnitCostOnFirstItemUI.split(" ")[0];
                Double dNoOfUnitsCostPerDayOnUI = Double.parseDouble(sNoOfUnitsCostPerDayOnUI);
                // String sUnitCostOfFirstItem = sUnitCostOnFirstItemUI.split("\\(")[0];
                String sNoOfUnitsOrDays = sUnitCostOnFirstItemUI.split("\\(")[1];
                String sNoUnitsPerDayForFirstItem = sNoOfUnitsOrDays.split(" ")[0];
                Double dNoUnitsPerDayForFirstItem = Double.parseDouble(sNoUnitsPerDayForFirstItem);

                String sGrossSubPackageItemPriceForFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(0));
                String sNetPriceItemPriceForFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                Cls_Generic_Methods.customWait(4);
                double dNetPriceOfFirstItemBeforeDiscount = Double.parseDouble(sNetPriceItemPriceForFirstItem);
                double dGrossPriceOfFirstItemBeforeDiscount = Double.parseDouble(sGrossSubPackageItemPriceForFirstItem);
                double dCalculatedGrossPriceAndNetPriceForFirstItem = dNoOfUnitsCostPerDayOnUI * dNoUnitsPerDayForFirstItem;
                m_assert.assertTrue((dCalculatedGrossPriceAndNetPriceForFirstItem == dNetPriceOfFirstItemBeforeDiscount && dCalculatedGrossPriceAndNetPriceForFirstItem == dGrossPriceOfFirstItemBeforeDiscount), "Calculated gross price for first package level item is equal to net price and gross price on UI, Before applying a discount = <b>" + dNetPriceOfFirstItemBeforeDiscount + "</b>");
                String sUnitCostOnSecondItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemUnitCost.get(1));
                String sNoOfUnitsOrDaysOnUIForSecondItem = sUnitCostOnSecondItemUI.split(" ")[0];
                Double dNoOfUnitsOrDaysOnUIForSecondItem = Double.parseDouble(sNoOfUnitsOrDaysOnUIForSecondItem);
                String sNoOfUnitsOrDaysForSecondItem = sUnitCostOnSecondItemUI.split("\\(")[1];
                String sNoUnitsPerDayForSecondItems = sNoOfUnitsOrDaysForSecondItem.split(" ")[0];
                Double dNoUnitsPerDayForSecondItems = Double.parseDouble(sNoUnitsPerDayForSecondItems);
                String sGrossSubPackageItemPriceForSecondItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(1));
                String sNetPriceItemPriceForSecondItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dGrossSubPackageItemPriceForSecondItem = Double.parseDouble(sGrossSubPackageItemPriceForSecondItem);
                double dNetPriceItemPriceForSecondItem = Double.parseDouble(sNetPriceItemPriceForSecondItem);
                double dCalculatedGrossPriceAndNetPriceForSecondItem = dNoOfUnitsOrDaysOnUIForSecondItem * dNoUnitsPerDayForSecondItems;
                m_assert.assertTrue((dCalculatedGrossPriceAndNetPriceForSecondItem == dNetPriceItemPriceForSecondItem && dCalculatedGrossPriceAndNetPriceForSecondItem == dGrossSubPackageItemPriceForSecondItem),
                        "Calculated gross price for Second package level item is equal to net price and gross price on UI, Before applying a discount<b> " + dNetPriceItemPriceForSecondItem + "</b>");
                double dSumOfFistAndSecondSubpackageItemsNetPrice = dNetPriceItemPriceForSecondItem + dNetPriceOfFirstItemBeforeDiscount;
                m_assert.assertTrue((dSumOfFistAndSecondSubpackageItemsNetPrice == dOverAllNetPriceOnPackage),
                        "summation of first and second subpackage level items net price is equal to overall subpackage net price, Before applying a discount<b>  " + dOverAllNetPriceOnPackage + "</b>");
                // Apply discount on each subPackage item
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(0));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(0), sDiscountAmountOnFirstSubPackageItem), "Applied Sub package item level discount for first item = <b> " + sDiscountAmountOnFirstSubPackageItem + "</b>₹ ");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(1));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.list_subpackageItemLevelDiscountTypes.get(1), "%"), "Discount type for second subpackage level item =  <b>%</b>");
                Cls_Generic_Methods.customWait(4);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(1), sDiscountAmountOnSecondSubPackageItem), "Applied Sub package item level discount for second item = <b> " + sDiscountAmountOnSecondSubPackageItem + "</b>");
                double dDiscountAmountOnSecondSubPackageItem = Double.parseDouble(sDiscountAmountOnSecondSubPackageItem);
                double dDiscountPercentageForSecondItem = dDiscountAmountOnSecondSubPackageItem / 100;
                double dDiscountAmountAfterApplyingDiscountInPercentageForSecondItem = dNetPriceItemPriceForSecondItem * dDiscountPercentageForSecondItem;
                double dRoundingOffTheDiscountForSecondItem = roundOffFunctionUsingDouble(dDiscountAmountAfterApplyingDiscountInPercentageForSecondItem);
                //Multiplying with minus as we are showing discount value in negative
                double dRoundingOffTheDiscountOnSecondItem = dRoundingOffTheDiscountForSecondItem * (-1);
                String sFirstSubpackageItemDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dFirstSubpackageItemDiscountAmount = Double.parseDouble(sFirstSubpackageItemDiscountAmount);
                double dNetPriceOfFirstItemAfterDiscount = dNetPriceOfFirstItemBeforeDiscount + dFirstSubpackageItemDiscountAmount;
                String sNetAmountAfterDiscountForFirstItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                double dNetAmountAfterDiscountForFirstItemOnUI = Double.parseDouble(sNetAmountAfterDiscountForFirstItemOnUI);
                m_assert.assertTrue((dNetPriceOfFirstItemAfterDiscount == dNetAmountAfterDiscountForFirstItemOnUI),
                        "Net price amount for first subpackage item after applying Discount =  <b>" + dNetAmountAfterDiscountForFirstItemOnUI + "</b>");
                String sSecondSubpackageItemDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(1));
                double dSecondSubpackageItemDiscountAmount = Double.parseDouble(sSecondSubpackageItemDiscountAmount);
                double dNetPriceOfSecondItemAfterDiscount = dNetPriceItemPriceForSecondItem + dSecondSubpackageItemDiscountAmount;
                String sNetAmountAfterDiscountForSecondItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dNetAmountAfterDiscountForSecondItemOnUI = Double.parseDouble(sNetAmountAfterDiscountForSecondItemOnUI);
                m_assert.assertTrue((dRoundingOffTheDiscountOnSecondItem == dSecondSubpackageItemDiscountAmount),
                        "Discount amount for second subpackage item on UI is matched with calculated Discount amount= <b>" + dSecondSubpackageItemDiscountAmount + "</b>");
                m_assert.assertTrue((dNetPriceOfSecondItemAfterDiscount == dNetAmountAfterDiscountForSecondItemOnUI),
                        "Net price amount for second subpackage item after applying Discount= <b>" + dNetPriceOfSecondItemAfterDiscount + "</b>");
                // summation subpackage item level discount
                double dTotalDiscountAmountOnOverAllSubpackage = dFirstSubpackageItemDiscountAmount + dSecondSubpackageItemDiscountAmount;
                String sOverAllDiscountOnPackage = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_overallDiscountAmountOnPackage);
                String sOverAllDiscountPackage = sOverAllDiscountOnPackage.replaceAll("\\s", "");
                double dOverAllDiscountOnPackage = Double.parseDouble(sOverAllDiscountPackage);
                m_assert.assertTrue((dOverAllDiscountOnPackage == dTotalDiscountAmountOnOverAllSubpackage),
                        "Subpackage item level discounts are matching with overall subpackage discount field =  <b>" + dTotalDiscountAmountOnOverAllSubpackage + "</b>");
                //Over all package net price after discount
                double dNetAmountOnOverAllPackageAfterApplyingDiscount = dOverAllNetPriceOnPackage + dTotalDiscountAmountOnOverAllSubpackage;
                String sNetAmtAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetAmtAfterDiscount = Double.parseDouble(sNetAmtAfterDiscount);
                m_assert.assertTrue((dNetAmountOnOverAllPackageAfterApplyingDiscount == dNetAmtAfterDiscount), "Calculated Net Amount after discount matching with Net amount on UI <b>" + dNetAmtAfterDiscount + "</b>");

                // delete all subpackage item wise discounts
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_deleteSubPackageItemsDiscount.get(1)), "clicked on delete subpackage item level discount button");
                String sSecondSubPackageNetAmountAfterDeletingItemLevelDiscount = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dSecondSubPackageNetAmountAfterDeletingItemLevelDiscount = Double.parseDouble(sSecondSubPackageNetAmountAfterDeletingItemLevelDiscount);
                m_assert.assertTrue((dSecondSubPackageNetAmountAfterDeletingItemLevelDiscount == dCalculatedGrossPriceAndNetPriceForSecondItem), "Upon deleting a second subpackage level item discount calculated Net Amount is matching with Net amount on UI <b>" + dSecondSubPackageNetAmountAfterDeletingItemLevelDiscount + "</b>");

                //net price of first sub package level item after discount
                String sFirstSubpackageItemDiscountAfterRemovingSecondItemsDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dFirstSubpackageItemAfterRemovingSecondItemsDiscountAmount = Double.parseDouble(sFirstSubpackageItemDiscountAfterRemovingSecondItemsDiscountAmount);
                double dNetPriceOfFirstItemAfterRemovingSecondItemsDiscount = dNetPriceOfFirstItemBeforeDiscount + dFirstSubpackageItemAfterRemovingSecondItemsDiscountAmount;
                String sNetAmountAfterDiscountOnFirstItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                double dNetAmountAfterDiscountOnFirstItemOnUI = Double.parseDouble(sNetAmountAfterDiscountOnFirstItemOnUI);
                m_assert.assertTrue((dNetAmountAfterDiscountOnFirstItemOnUI == dNetPriceOfFirstItemAfterRemovingSecondItemsDiscount), "Upon deleting a second subpackage level discount calculated Net Amount of second item is matching with Net amount on UI of second item <b>" + dNetPriceOfFirstItemAfterRemovingSecondItemsDiscount + "</b>");
                //Over all package net price after deleting second items discount
                double dCalculatedNetAmountAfterRemovingSecondItemsDiscount = dNetAmountOnOverAllPackageAfterApplyingDiscount + dRoundingOffTheDiscountForSecondItem;
                String sNetAmtAfterRemovingSecondItemDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetAmtAfterRemovingSecondItemDiscount = Double.parseDouble(sNetAmtAfterRemovingSecondItemDiscount);
                m_assert.assertTrue((dCalculatedNetAmountAfterRemovingSecondItemsDiscount == dNetAmtAfterRemovingSecondItemDiscount), "Calculated Net Amount after removing second items discount is matching with Net amount on UI <b>" + dCalculatedNetAmountAfterRemovingSecondItemsDiscount + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Bills.button_addSubPackageItemsDiscount.get(1));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmount.get(0), sGlobalDiscount), "Entered amount in Global discount field = <b>" + sGlobalDiscount + "</b>");
                // m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);
                double dGlobalDiscount = Double.parseDouble(sGlobalDiscount);
                double dGlobalDiscountPercentage = dGlobalDiscount / dOverAllNetPriceOnPackage;
                double dDiscountOnFirstItemAfterApplyingGlobalDiscount = dGlobalDiscountPercentage * dGrossPriceOfFirstItemBeforeDiscount;
                double dRoundOffDiscount1 = roundOffFunctionUsingDouble(dDiscountOnFirstItemAfterApplyingGlobalDiscount);
                String sDiscountOn1stSubItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dDiscountOn1stSubItem = Double.parseDouble(sDiscountOn1stSubItem);
                double dDiscountOnSecondItemAfterApplyingGlobalDiscount = dGlobalDiscountPercentage * dGrossSubPackageItemPriceForSecondItem;
                double dRoundOffDiscount2 = roundOffFunctionUsingDouble(dDiscountOnSecondItemAfterApplyingGlobalDiscount);
                String sDiscountOn2ndSubItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(1));
                double dDiscountOn2ndSubItem = Double.parseDouble(sDiscountOn2ndSubItem);
                double dSummationOfSubpackageItemWiseDiscount = dDiscountOnFirstItemAfterApplyingGlobalDiscount + dDiscountOnSecondItemAfterApplyingGlobalDiscount;
                double dUpdateDiscount1stAmountToPositive = dDiscountOn1stSubItem * (-1);
                double dUpdateDiscount2ndAmountToPositive = dDiscountOn2ndSubItem * (-1);
                double dNetAmountOfFirstSubpackageItemAfterGlobalDiscount = dGrossPriceOfFirstItemBeforeDiscount - dDiscountOnFirstItemAfterApplyingGlobalDiscount;
                double dNetAmountOfSecondSubpackageItemAfterGlobalDiscount = dGrossSubPackageItemPriceForSecondItem - dDiscountOnSecondItemAfterApplyingGlobalDiscount;
                m_assert.assertTrue((dUpdateDiscount1stAmountToPositive == dRoundOffDiscount1 && dUpdateDiscount2ndAmountToPositive == dRoundOffDiscount2), "Discount on 1st subpackage is matching with discount calculations = <b>" + dRoundOffDiscount1 + "</b>, Discount on 2nd subpackage is matching with discount calculations  =  <b>" + dRoundOffDiscount2 + "</b>");
                m_assert.assertTrue((dSummationOfSubpackageItemWiseDiscount == dGlobalDiscount), "Subpackage item wise discounts sum is equal to Global discount amount <b>" + dGlobalDiscount + "</b>");
                Cls_Generic_Methods.customWait(3);
                //Apply GD in percentage
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_globalDiscountType, "%"), "Discount type for GD =  <b>%</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmount.get(0), sGlobalDiscount), "Entered amount in Global discount field = <b>" + sGlobalDiscount + "</b>");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_discountReason.get(0), sDiscountReason);
                double dGlobalDiscountInPercentage = dGlobalDiscount / 100;
                double dDiscountOnFirstItemAfterApplyingGlobalDiscountInPercentage = dGlobalDiscountInPercentage * dGrossPriceOfFirstItemBeforeDiscount;
                double dDiscountOnSecondItemAfterApplyingGlobalDiscountInPercentage = dGlobalDiscountInPercentage * dGrossSubPackageItemPriceForSecondItem;
                String sDiscount1 = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dDiscount1 = Double.parseDouble(sDiscount1);
                double dFirstSubpackageDiscountOnUI = dDiscount1 * (-1);
                String sDiscount2 = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(1));
                double dDiscount2 = Double.parseDouble(sDiscount2);
                double dSecondSubpackageDiscountOnUI = dDiscount2 * (-1);
                m_assert.assertTrue((dFirstSubpackageDiscountOnUI == dDiscountOnFirstItemAfterApplyingGlobalDiscountInPercentage && dSecondSubpackageDiscountOnUI == dDiscountOnSecondItemAfterApplyingGlobalDiscountInPercentage), "Discount amount is matching calculated subpackage item level discounts 1st item1 discount:  <b>" + dFirstSubpackageDiscountOnUI + "</b>and 2nd subpackage items discount is matching with calculated discount amount:  <b>" + dSecondSubpackageDiscountOnUI);
                double dSummationOfSubpackageItemWiseDiscountInPercentage = dFirstSubpackageDiscountOnUI + dSecondSubpackageDiscountOnUI;
                double dNetAmountOfFirstSubpackageItemAfterGlobalDiscountInPercentage = dGrossPriceOfFirstItemBeforeDiscount - dFirstSubpackageDiscountOnUI;
                double dNetAmountOfSecondSubpackageItemAfterGlobalDiscountInPercentage = dGrossSubPackageItemPriceForSecondItem - dSecondSubpackageDiscountOnUI;
                String sNetAmountForFirstSubItem = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetAmountForFirstSubItem = Double.parseDouble(sNetAmountForFirstSubItem);
                double dSumNetAmountAfterGD = dNetAmountOfFirstSubpackageItemAfterGlobalDiscountInPercentage + dNetAmountOfSecondSubpackageItemAfterGlobalDiscountInPercentage;
                m_assert.assertTrue((dNetAmountForFirstSubItem == dSumNetAmountAfterGD), "Net amount on UI equals to  calculated net amount after applying global discount <b>" + dSumNetAmountAfterGD + "</b>");
                Cls_Generic_Methods.customWait(3);
                String sNetAmountOnBillAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_netBillTotal, "value");
                double dNetAmountOnBillAfterApplyingGD = Double.parseDouble(sNetAmountOnBillAfterApplyingGD);
                String sGrossBillTotalOnBillAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_grossBillTotal, "value");
                double dGrossBillTotalOnBillAfterApplyingGD = Double.parseDouble(sGrossBillTotalOnBillAfterApplyingGD);
                String sDiscountOnItemsAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnItem, "value");
                double dDiscountOnItemsAfterApplyingGD = Double.parseDouble(sDiscountOnItemsAfterApplyingGD);
                m_assert.assertTrue((dNetAmountOnBillAfterApplyingGD == dSumNetAmountAfterGD && dGrossBillTotalOnBillAfterApplyingGD == dGrossPrice && dDiscountOnItemsAfterApplyingGD == dSummationOfSubpackageItemWiseDiscountInPercentage), "Net amount on bill = <b>" + dNetAmountOnBillAfterApplyingGD + "</b> , Gross amount on bill =  <b>" + dGrossBillTotalOnBillAfterApplyingGD + "</b> , Discount on item = <b> " + dDiscountOnItemsAfterApplyingGD + "</b>");
                Cls_Generic_Methods.customWait(4);
                String sTotalDiscountOnItemsAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnBill, "value");
                double dTotalDiscountOnItemsAfterApplyingGD = Double.parseDouble(sTotalDiscountOnItemsAfterApplyingGD);
                String sTotalOfAllDiscountOnItemsAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalOfAllDiscounts, "value");
                double dTotalOfAllDiscountOnItemsAfterApplyingGD = Double.parseDouble(sTotalOfAllDiscountOnItemsAfterApplyingGD);
                m_assert.assertTrue((dTotalDiscountOnItemsAfterApplyingGD == dTotalOfAllDiscountOnItemsAfterApplyingGD), "Total discount and total of all discount fields, values matching on UI = <b>" + dTotalOfAllDiscountOnItemsAfterApplyingGD + "</b>");
                Cls_Generic_Methods.scrollToElementByJS(oPage_Bills.select_modeOfPayment);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_modeOfPayment, paymentMode), paymentMode + " option is selected for Mode Of Payment");
                String sAmountReceivedOnUI = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_amountReceivedField.get(0), "value");
                double dAmountReceivedOnUI = Double.parseDouble(sAmountReceivedOnUI);
                m_assert.assertTrue((dAmountReceivedOnUI == dNetAmountOnBillAfterApplyingGD), "Net amount on bill equals to amount received against bill is matching = <b>" + dAmountReceivedOnUI + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills), "<b>Cash bill is created </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.customWait(4);
                //bill preview validation

                String sFirstSubpackageItemDiscountOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(0));
                dFirstSubpackageItemDiscountOnBillPreview = Double.parseDouble(sFirstSubpackageItemDiscountOnBillPreview);

                String sSecondSubpackageItemDiscountOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(1));
                dSecondSubpackageItemDiscountOnBillPreview = Double.parseDouble(sSecondSubpackageItemDiscountOnBillPreview);
                m_assert.assertTrue((dFirstSubpackageItemDiscountOnBillPreview == dDiscountOnFirstItemAfterApplyingGlobalDiscountInPercentage && dSecondSubpackageItemDiscountOnBillPreview == dDiscountOnSecondItemAfterApplyingGlobalDiscountInPercentage), "Discount amount is matching with calculated subpackage item level discounts 1st item1 on preview : <b>" + dFirstSubpackageItemDiscountOnBillPreview + "</b> 2nd item discount on bill preview : <b>" + dSecondSubpackageItemDiscountOnBillPreview);
                String sOverDiscountAmountOnPackageOnPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(0));
                String sOverDiscountAmountOnPackageInPreview = sOverDiscountAmountOnPackageOnPreview.replaceAll("\\s", "");
                double dOverDiscountAmountOnPackagePreview = Double.parseDouble(sOverDiscountAmountOnPackageInPreview);
                dOverDiscountAmountOnPackageInPreview = dOverDiscountAmountOnPackagePreview * (-1);
                m_assert.assertTrue((dTotalDiscountOnItemsAfterApplyingGD == dOverDiscountAmountOnPackageInPreview), "OverAll discount amount against package on bill preview = <b>" + dTotalDiscountOnItemsAfterApplyingGD + "</b>");
                String sGrossBillTotalOnPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_grossBillTotalOnBillPreview);
                double dGrossBillTotalOnPreview = Double.parseDouble(sGrossBillTotalOnPreview);
                m_assert.assertTrue((dGrossPrice == dGrossBillTotalOnPreview), "Gross bill total against package on bill preview = <b>" + dGrossPrice + "</b>");

                String sTotalDiscountOnItemsInBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalDiscountOnItemsOnBillPreview);
                double dTotalDiscountOnItemsInBillPreview = Double.parseDouble(sTotalDiscountOnItemsInBillPreview);

                String sTotalOfAllDiscountOnItemsInBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalOfAllDiscountOnBillPreview);
                double dTotalOfAllDiscountOnItemsInBillPreview = Double.parseDouble(sTotalOfAllDiscountOnItemsInBillPreview);

                String sPaymentReceivedOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_paymentReceivedOnBillPreview);
                dPaymentReceivedOnBillPreview = Double.parseDouble(sPaymentReceivedOnBillPreview);
                m_assert.assertTrue((dTotalDiscountOnItemsAfterApplyingGD == dTotalDiscountOnItemsInBillPreview && dTotalDiscountOnItemsAfterApplyingGD == dTotalOfAllDiscountOnItemsInBillPreview), "Total discount on item and total of all discounts on bill preview = <b>" + dTotalDiscountOnItemsAfterApplyingGD + "</b>");
                m_assert.assertTrue((dPaymentReceivedOnBillPreview == dAmountReceivedOnUI), "Payment Received on bill preview = <b>" + dAmountReceivedOnUI + "</b>");
                Cls_Generic_Methods.customWait(4);

                //Edit bill
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_editBill), "clicked on edit bill");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickIPDPlusActionButton, 30);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickIPDPlusActionButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Bills.button_newPackage);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.select_package.get(0));
                Cls_Generic_Methods.customWait(3);
                boolean PackageFound = false;
                for (WebElement eItemList : oPage_Bills.list_packageOrServiceNameUnderDescriptionField) {
                    if (Cls_Generic_Methods.getTextInElement(eItemList).equals(sSecondPackage)) {
                        Cls_Generic_Methods.clickElement(eItemList);
                        PackageFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(PackageFound, "Selected package name = <b>" + sSecondPackage + "</b> ");
            }

            Cls_Generic_Methods.customWait(4);
            String sQTY1 = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_Quantity.get(1), "value");
            String sUnitPrice1 = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(1), "value");
            Cls_Generic_Methods.customWait();
            String sGrossPrice1 = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(1), "value");
            double dGrossPrice1 = Double.parseDouble(sGrossPrice1);
            String sNetPrice1 = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
            double dOverAllNetPriceOnPackage1 = Double.parseDouble(sNetPrice1);
            Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(0));
            Cls_Generic_Methods.customWait(4);
            Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_mainDiscountFieldOnPackage.get(1));
            Cls_Generic_Methods.customWait();
            String sSecondItemDiscount = "2";
            double dSecondItemDiscount = Double.parseDouble(sSecondItemDiscount);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_mainDiscountFieldOnPackage.get(1), sSecondItemDiscount), "Applied discount for second package  = <b> " + sDiscountAmountOnFirstSubPackageItem + "</b>");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_discountReason.get(1), sDiscountReason);
            double dNetPriceOfSecondPackage = dGrossPrice1 - dSecondItemDiscount;
            String sNetPriceOfSecondPackage = String.valueOf(dNetPriceOfSecondPackage);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_internalComments, "InternalComments");
            Cls_Generic_Methods.clickElement(driver, oPage_Bills.link_addPayment);
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.scrollToElementByJS(oPage_Bills.list_modeOfPayment.get(1));
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_Bills.list_modeOfPayment.get(1), paymentMode), paymentMode + " option is selected for Mode Of Payment");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clearValuesInElement(oPage_Bills.input_amountReceivedField.get(1));
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_amountReceivedField.get(1), sNetPriceOfSecondPackage);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills), "<b>Cash bill is created </b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
            Cls_Generic_Methods.customWait(4);

            String sFirstSubpackageItemDiscountOnBillPreviewAfterUpdatingBill = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(0));
            double dFirstSubpackageItemDiscountOnBillPreviewAfterUpdatingBill = Double.parseDouble(sFirstSubpackageItemDiscountOnBillPreviewAfterUpdatingBill);

            String sSecondSubpackageItemDiscountOnBillPreviewAfterUpdatingBill = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(1));
            double dSecondSubpackageItemDiscountOnBillPreviewAfterUpdatingBill = Double.parseDouble(sSecondSubpackageItemDiscountOnBillPreviewAfterUpdatingBill);

            m_assert.assertTrue((dFirstSubpackageItemDiscountOnBillPreview == dFirstSubpackageItemDiscountOnBillPreviewAfterUpdatingBill && dSecondSubpackageItemDiscountOnBillPreview == dSecondSubpackageItemDiscountOnBillPreviewAfterUpdatingBill), "After updating a bill Discount amount is matching with calculated subpackage item level discounts 1st item1 on preview : <b>" + dFirstSubpackageItemDiscountOnBillPreview + "</b>After updating a bill, 2nd item discount on bill preview : <b>" + dSecondSubpackageItemDiscountOnBillPreview);
            String sOverDiscountAmountOnFirstPackageOnPreviewAfterUpdatingAbill = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(0));
            String sOverDiscountAmountOnFirstPackageInPreviewAfterUpdatingAbill = sOverDiscountAmountOnFirstPackageOnPreviewAfterUpdatingAbill.replaceAll("\\s", "");
            double dOverDiscountAmountOnFirstPackagePreviewAfterUpdatingBill = Double.parseDouble(sOverDiscountAmountOnFirstPackageInPreviewAfterUpdatingAbill);
            double dOverDiscountAmountOnFirstPackageInPreviewAfterUpdatingBill = dOverDiscountAmountOnFirstPackagePreviewAfterUpdatingBill * (-1);
            m_assert.assertTrue((dOverDiscountAmountOnPackageInPreview == dOverDiscountAmountOnFirstPackageInPreviewAfterUpdatingBill), "After updating a bill OverAll discount amount on 1st package on bill preview = <b>" + dOverDiscountAmountOnFirstPackageInPreviewAfterUpdatingBill + "</b>");
            // second package details
            String sOverDiscountAmountOnSecondPackageOnPreviewAfterUpdatingAbill = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(1));
            String sOverDiscountAmountOnSecondPackagOnPreviewAfterUpdatingAbill = sOverDiscountAmountOnSecondPackageOnPreviewAfterUpdatingAbill.replaceAll("\\s", "");
            double dOverDiscountAmountOnSecondPackagePreviewAfterUpdatingBill = Double.parseDouble(sOverDiscountAmountOnSecondPackagOnPreviewAfterUpdatingAbill);
            double dOverDiscountAmountOnSecondPackageInPreviewAfterUpdatingBill = dOverDiscountAmountOnSecondPackagePreviewAfterUpdatingBill * (-1);
            double dSumOfDiscountAmountOnEachPackageInPreview = dOverDiscountAmountOnSecondPackageInPreviewAfterUpdatingBill + dOverDiscountAmountOnFirstPackageInPreviewAfterUpdatingBill;
            String sGrossBillTotalOnPreviewAfterUpdate = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_grossBillTotalOnBillPreview);
            double dGrossBillTotalOnPreview = Double.parseDouble(sGrossBillTotalOnPreviewAfterUpdate);
            double dSumOfGrossAmountOfEachPackage = dGrossPrice1 + dGrossPrice;
            m_assert.assertTrue((dGrossBillTotalOnPreview == dSumOfGrossAmountOfEachPackage), "After updating a bill, Gross bill total on each bill preview = <b>" + dGrossBillTotalOnPreview + "</b>");
            //total discount on items
            String sTotalDiscountOnItemsInBillPreviewAfterBillUpdate = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalDiscountOnItemsOnBillPreview);
            double dTotalDiscountOnItemsInBillPreviewAfterBillUpdate = Double.parseDouble(sTotalDiscountOnItemsInBillPreviewAfterBillUpdate);
            //total of all discounts
            String sTotalOfAllDiscountOnItemsInBillPreviewAfterBillUpdate = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalOfAllDiscountOnBillPreview);
            double dTotalOfAllDiscountOnItemsInBillPreviewAfterBillUpdate = Double.parseDouble(sTotalOfAllDiscountOnItemsInBillPreviewAfterBillUpdate);

            m_assert.assertTrue((dTotalDiscountOnItemsInBillPreviewAfterBillUpdate == dSumOfDiscountAmountOnEachPackageInPreview && dTotalOfAllDiscountOnItemsInBillPreviewAfterBillUpdate == dTotalDiscountOnItemsInBillPreviewAfterBillUpdate), "After updating a bill, Total item discount and total of all discount on bill preview is matching with calulated value  = <b>" + dTotalDiscountOnItemsInBillPreviewAfterBillUpdate + "</b>");

            String sPaymentReceivedOnBillPreviewAfterBillUpdate = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_paymentReceivedOnBillPreview);
            double dPaymentReceivedOnBillPreviewAfterBillUpdate = Double.parseDouble(sPaymentReceivedOnBillPreviewAfterBillUpdate);
            double dCalculatedPaymentReceived = dOverAllNetPriceOnPackage1 + (dPaymentReceivedOnBillPreview - dSecondItemDiscount);
            m_assert.assertTrue((dPaymentReceivedOnBillPreviewAfterBillUpdate == dCalculatedPaymentReceived), "Payment Received on bill preview after bill update = <b>" + dCalculatedPaymentReceived + "</b>");
            Cls_Generic_Methods.customWait(4);
            Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_closeBill);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
            Cls_Generic_Methods.customWait();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }

    }

    @Test(enabled = true, description = "create Draft bill and validate the Subpackage item level  Discount")
    public void createCreditBillAndValidateSubPackageItemLevelDiscount() {
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills = new Page_Bills(driver);
        oPage_IPD = new Page_IPD(driver);
        boolean bPatientFound = false;
        String paymentMode = "Cash";
        String sPackageName = "AutomationPackageDataDisplaySelf";
        String sDiscountAmountOnFirstSubPackageItem = "4";
        String sDiscountAmountOnSecondSubPackageItem = "3";
        String sGlobalDiscount = "10";
        String sDiscountReason = "Reason" + getRandomUniqueString(6);
        String sSecondPackage = "Testing Facility";
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectFacility("TST");
            CommonActions.selectDepartmentOnApp("IPD");
            myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
            m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today), "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");
            Cls_Generic_Methods.customWait(10);

            bPatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnScheduledTodayTab, concatPatientFullName);
            m_assert.assertTrue(bPatientFound, "Validate that the patient " + concatPatientFullName + " is created in IPD");

            double dFirstSubpackageItemDiscountOnBillPreview = 0;
            double dSecondSubpackageItemDiscountOnBillPreview = 0;
            double dOverDiscountAmountOnPackageInPreview = 0;
            double dGrossPrice = 0;
            double dPaymentPendingOnBillPreview = 0;
            if (bPatientFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills), "Clicked on bills button");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_IPD.button_newCreditBill);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 30);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickIPDPlusActionButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Bills.button_newPackage);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_deleteItemPackage.get(0));
                Cls_Generic_Methods.clickElement(oPage_Bills.select_package.get(0));
                boolean myPackageFound = false;
                for (WebElement eItemList : oPage_Bills.list_packageOrServiceNameUnderDescriptionField) {
                    String sPackageNameOnUI = Cls_Generic_Methods.getTextInElement(eItemList);
                    if (sPackageNameOnUI.equals(sPackageName)) {
                        Cls_Generic_Methods.clickElement(eItemList);
                        myPackageFound = true;
                        break;
                    }

                }
                m_assert.assertTrue(myPackageFound, "Selected package name = <b>" + sPackageName + "</b> ");
                Cls_Generic_Methods.customWait(4);
                String sQTY = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_Quantity.get(0), "value");
                String sUnitPrice = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(0), "value");
                String sGrossPrice = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(0), "value");
                dGrossPrice = Double.parseDouble(sGrossPrice);
                String sNetPrice = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dOverAllNetPriceOnPackage = Double.parseDouble(sNetPrice);
                Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(0));
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_deleteItemLevelDiscount.get(0));
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_subpackageItemLevelDiscountField.get(0))) {
                    m_assert.assertWarn("First Subpackage item level Discount field is displaying even afetr user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("First Subpackage item level Discount field is not displaying as user deleted overall discount field ");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_subpackageItemLevelDiscountField.get(1))) {
                    m_assert.assertWarn("Second Subpackage item level Discount field is not displaying as user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Second Subpackage item level Discount field is  displaying even after user deleted overall discount field ");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_mainDiscountFieldOnPackage.get(0))) {
                    m_assert.assertWarn("Package level Discount field is not displaying as user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Package level Discount field is  displaying even after user deleted overall discount field ");
                }
                Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(0));
                Cls_Generic_Methods.customWait(4);
                String sUnitCostOnFirstItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemUnitCost.get(0));
                String sNoOfUnitsCostPerDayOnUI = sUnitCostOnFirstItemUI.split(" ")[0];
                Double dNoOfUnitsCostPerDayOnUI = Double.parseDouble(sNoOfUnitsCostPerDayOnUI);
                String sNoOfUnitsOrDays = sUnitCostOnFirstItemUI.split("\\(")[1];
                String sNoUnitsPerDayForFirstItem = sNoOfUnitsOrDays.split(" ")[0];
                Double dNoUnitsPerDayForFirstItem = Double.parseDouble(sNoUnitsPerDayForFirstItem);

                String sGrossSubPackageItemPriceForFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(0));
                String sNetPriceItemPriceForFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                Cls_Generic_Methods.customWait(4);
                double dNetPriceOfFirstItemBeforeDiscount = Double.parseDouble(sNetPriceItemPriceForFirstItem);
                double dGrossPriceOfFirstItemBeforeDiscount = Double.parseDouble(sGrossSubPackageItemPriceForFirstItem);
                double dCalculatedGrossPriceAndNetPriceForFirstItem = dNoOfUnitsCostPerDayOnUI * dNoUnitsPerDayForFirstItem;
                m_assert.assertTrue((dCalculatedGrossPriceAndNetPriceForFirstItem == dNetPriceOfFirstItemBeforeDiscount && dCalculatedGrossPriceAndNetPriceForFirstItem == dGrossPriceOfFirstItemBeforeDiscount),
                        "Calculated gross price for first package level item is equal to net price and gross price on UI, Before applying a discount = <b>  =  <b>" + dNetPriceOfFirstItemBeforeDiscount + "</b>");
                String sUnitCostOnSecondItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemUnitCost.get(1));
                // String sUnitCostOfSecondItem = sUnitCostOnSecondItemUI.split("\\(")[0];
                String sNoOfUnitsOrDaysOnUIForSecondItem = sUnitCostOnSecondItemUI.split(" ")[0];
                Double dNoOfUnitsOrDaysOnUIForSecondItem = Double.parseDouble(sNoOfUnitsOrDaysOnUIForSecondItem);
                String sNoOfUnitsOrDaysForSecondItem = sUnitCostOnSecondItemUI.split("\\(")[1];
                String sNoUnitsPerDayForSecondItems = sNoOfUnitsOrDaysForSecondItem.split(" ")[0];
                Double dNoUnitsPerDayForSecondItems = Double.parseDouble(sNoUnitsPerDayForSecondItems);
                String sGrossSubPackageItemPriceForSecondItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(1));
                String sNetPriceItemPriceForSecondItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dGrossSubPackageItemPriceForSecondItem = Double.parseDouble(sGrossSubPackageItemPriceForSecondItem);
                double dNetPriceItemPriceForSecondItem = Double.parseDouble(sNetPriceItemPriceForSecondItem);
                double dCalculatedGrossPriceAndNetPriceForSecondItem = dNoOfUnitsOrDaysOnUIForSecondItem * dNoUnitsPerDayForSecondItems;
                m_assert.assertTrue((dCalculatedGrossPriceAndNetPriceForSecondItem == dNetPriceItemPriceForSecondItem && dCalculatedGrossPriceAndNetPriceForSecondItem == dGrossSubPackageItemPriceForSecondItem),
                        "Calculated gross price for Second package level item is equal to net price and gross price on UI, Before applying a discount<b>   =  <b>" + dNetPriceItemPriceForSecondItem + "</b>");
                double dSumOfFistAndSecondSubpackageItemsNetPrice = dNetPriceItemPriceForSecondItem + dNetPriceOfFirstItemBeforeDiscount;
                m_assert.assertTrue((dSumOfFistAndSecondSubpackageItemsNetPrice == dOverAllNetPriceOnPackage),
                        " summation of first and second subpackage level items net price is equal to overall subpackage net price, Before applying a discount <b>" + dOverAllNetPriceOnPackage + "</b>");
                // Apply discount on each subPackage item
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(0));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(0), sDiscountAmountOnFirstSubPackageItem), "Applied Sub package item level discount for first item = <b> " + sDiscountAmountOnFirstSubPackageItem + "</b>");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(1));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.list_subpackageItemLevelDiscountTypes.get(1), "%"), "Discount type for second subpackage level item =  <b>%</b>");
                Cls_Generic_Methods.customWait(4);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(1), sDiscountAmountOnSecondSubPackageItem), "Applied Sub package item level discount for second item = <b> " + sDiscountAmountOnSecondSubPackageItem + "</b>");
                double dDiscountAmountOnSecondSubPackageItem = Double.parseDouble(sDiscountAmountOnSecondSubPackageItem);
                double dDiscountPercentageForSecondItem = dDiscountAmountOnSecondSubPackageItem / 100;
                double dDiscountAmountAfterApplyingDiscountInPercentageForSecondItem = dNetPriceItemPriceForSecondItem * dDiscountPercentageForSecondItem;
                double dRoundingOffTheDiscountForSecondItem = roundOffFunctionUsingDouble(dDiscountAmountAfterApplyingDiscountInPercentageForSecondItem);
                //Multiplying with minus as we are showing discount value in negative
                double dRoundingOffTheDiscountOnSecondItem = dRoundingOffTheDiscountForSecondItem * (-1);
                String sFirstSubpackageItemDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dFirstSubpackageItemDiscountAmount = Double.parseDouble(sFirstSubpackageItemDiscountAmount);
                double dNetPriceOfFirstItemAfterDiscount = dNetPriceOfFirstItemBeforeDiscount + dFirstSubpackageItemDiscountAmount;
                String sNetAmountAfterDiscountForFirstItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                double dNetAmountAfterDiscountForFirstItemOnUI = Double.parseDouble(sNetAmountAfterDiscountForFirstItemOnUI);
                m_assert.assertTrue((dNetPriceOfFirstItemAfterDiscount == dNetAmountAfterDiscountForFirstItemOnUI),
                        " Net price amount for first subpackage item after applying Discount = <b>" + dNetAmountAfterDiscountForFirstItemOnUI + "</b>");
                String sSecondSubpackageItemDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(1));
                double dSecondSubpackageItemDiscountAmount = Double.parseDouble(sSecondSubpackageItemDiscountAmount);
                double dNetPriceOfSecondItemAfterDiscount = dNetPriceItemPriceForSecondItem + dSecondSubpackageItemDiscountAmount;
                String sNetAmountAfterDiscountForSecondItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dNetAmountAfterDiscountForSecondItemOnUI = Double.parseDouble(sNetAmountAfterDiscountForSecondItemOnUI);
                m_assert.assertTrue((dRoundingOffTheDiscountOnSecondItem == dSecondSubpackageItemDiscountAmount),
                        " Discount amount for second subpackage item on UI is matched with calculated Discount amount= <b>" + dSecondSubpackageItemDiscountAmount + "</b>");
                m_assert.assertTrue((dNetPriceOfSecondItemAfterDiscount == dNetAmountAfterDiscountForSecondItemOnUI),
                        " Net price amount for second subpackage item after applying Discount= <b>" + dNetPriceOfSecondItemAfterDiscount + "</b>");
                // summation subpackage item level discount
                double dTotalDiscountAmountOnOverAllSubpackage = dFirstSubpackageItemDiscountAmount + dSecondSubpackageItemDiscountAmount;
                String sOverAllDiscountOnPackage = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_overallDiscountAmountOnPackage);
                String sOverAllDiscountPackage = sOverAllDiscountOnPackage.replaceAll("\\s", "");
                double dOverAllDiscountOnPackage = Double.parseDouble(sOverAllDiscountPackage);
                m_assert.assertTrue((dOverAllDiscountOnPackage == dTotalDiscountAmountOnOverAllSubpackage),
                        " Subpackage item level discounts are matching with overall subpackage discount field = " + dTotalDiscountAmountOnOverAllSubpackage + "</b>");
                //Over all package net price after discount
                double dNetAmountOnOverAllPackageAfterApplyingDiscount = dOverAllNetPriceOnPackage + dTotalDiscountAmountOnOverAllSubpackage;
                String sNetAmtAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetAmtAfterDiscount = Double.parseDouble(sNetAmtAfterDiscount);
                m_assert.assertTrue((dNetAmountOnOverAllPackageAfterApplyingDiscount == dNetAmtAfterDiscount), "Calculated Net Amount after discount matching with Net amount on UI <b>" + dNetAmtAfterDiscount + "</b>");

                // delete all subpackage item wise discounts
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_deleteSubPackageItemsDiscount.get(1)), "clicked on delete subpackage item level discount button");
                String sSecondSubPackageNetAmountAfterDeletingItemLevelDiscount = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dSecondSubPackageNetAmountAfterDeletingItemLevelDiscount = Double.parseDouble(sSecondSubPackageNetAmountAfterDeletingItemLevelDiscount);
                m_assert.assertTrue((dSecondSubPackageNetAmountAfterDeletingItemLevelDiscount == dCalculatedGrossPriceAndNetPriceForSecondItem), "Upon deleting a second subpackage level item discount calculated Net Amount is matching with Net amount on UI <b>" + dSecondSubPackageNetAmountAfterDeletingItemLevelDiscount + "</b>");

                //net price of first sub package level item after discount
                String sFirstSubpackageItemDiscountAfterRemovingSecondItemsDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dFirstSubpackageItemAfterRemovingSecondItemsDiscountAmount = Double.parseDouble(sFirstSubpackageItemDiscountAfterRemovingSecondItemsDiscountAmount);
                double dNetPriceOfFirstItemAfterRemovingSecondItemsDiscount = dNetPriceOfFirstItemBeforeDiscount + dFirstSubpackageItemAfterRemovingSecondItemsDiscountAmount;
                String sNetAmountAfterDiscountOnFirstItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                double dNetAmountAfterDiscountOnFirstItemOnUI = Double.parseDouble(sNetAmountAfterDiscountOnFirstItemOnUI);
                m_assert.assertTrue((dNetAmountAfterDiscountOnFirstItemOnUI == dNetPriceOfFirstItemAfterRemovingSecondItemsDiscount), "Upon deleting a second subpackage level discount calculated Net Amount of second item is matching with Net amount on UI of second item <b>" + dNetPriceOfFirstItemAfterRemovingSecondItemsDiscount + "</b>");
                //Over all package net price after deleting second items discount
                double dCalculatedNetAmountAfterRemovingSecondItemsDiscount = dNetAmountOnOverAllPackageAfterApplyingDiscount + dRoundingOffTheDiscountForSecondItem;
                String sNetAmtAfterRemovingSecondItemDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetAmtAfterRemovingSecondItemDiscount = Double.parseDouble(sNetAmtAfterRemovingSecondItemDiscount);
                m_assert.assertTrue((dCalculatedNetAmountAfterRemovingSecondItemsDiscount == dNetAmtAfterRemovingSecondItemDiscount), "Calculated Net Amount after removing second items discount is matching with Net amount on UI <b>" + dCalculatedNetAmountAfterRemovingSecondItemsDiscount + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Bills.button_addSubPackageItemsDiscount.get(1));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmount.get(0), sGlobalDiscount), "Entered amount in Global discount field = <b>" + sGlobalDiscount + "</b>");
                // m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);
                double dGlobalDiscount = Double.parseDouble(sGlobalDiscount);
                double dGlobalDiscountPercentage = dGlobalDiscount / dOverAllNetPriceOnPackage;
                double dDiscountOnFirstItemAfterApplyingGlobalDiscount = dGlobalDiscountPercentage * dGrossPriceOfFirstItemBeforeDiscount;
                double dDiscountOnSecondItemAfterApplyingGlobalDiscount = dGlobalDiscountPercentage * dGrossSubPackageItemPriceForSecondItem;
                double dSummationOfSubpackageItemWiseDiscount = dDiscountOnFirstItemAfterApplyingGlobalDiscount + dDiscountOnSecondItemAfterApplyingGlobalDiscount;
                double dNetAmountOfFirstSubpackageItemAfterGlobalDiscount = dGrossPriceOfFirstItemBeforeDiscount - dDiscountOnFirstItemAfterApplyingGlobalDiscount;
                double dNetAmountOfSecondSubpackageItemAfterGlobalDiscount = dGrossSubPackageItemPriceForSecondItem - dDiscountOnSecondItemAfterApplyingGlobalDiscount;
                m_assert.assertTrue((dSummationOfSubpackageItemWiseDiscount == dGlobalDiscount), "Subpackage item wise discounts sum is equal to Global discount amount <b>" + dGlobalDiscount + "</b>");
                Cls_Generic_Methods.customWait(3);
                //Apply GD in percentage
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_globalDiscountType, "%"), "Discount type for GD =  <b>%</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmount.get(0), sGlobalDiscount), "Entered amount in Global discount field = <b>" + sGlobalDiscount + "</b>");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_discountReason.get(0), sDiscountReason);
                double dGlobalDiscountInPercentage = dGlobalDiscount / 100;
                double dDiscountOnFirstItemAfterApplyingGlobalDiscountInPercentage = dGlobalDiscountInPercentage * dGrossPriceOfFirstItemBeforeDiscount;
                double dDiscountOnSecondItemAfterApplyingGlobalDiscountInPercentage = dGlobalDiscountInPercentage * dGrossSubPackageItemPriceForSecondItem;
                String sDiscount1 = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dDiscount1 = Double.parseDouble(sDiscount1);
                double dFirstSubpackageDiscountOnUI = dDiscount1 * (-1);
                String sDiscount2 = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(1));
                double dDiscount2 = Double.parseDouble(sDiscount2);
                double dSecondSubpackageDiscountOnUI = dDiscount2 * (-1);
                m_assert.assertTrue((dFirstSubpackageDiscountOnUI == dDiscountOnFirstItemAfterApplyingGlobalDiscountInPercentage && dSecondSubpackageDiscountOnUI == dDiscountOnSecondItemAfterApplyingGlobalDiscountInPercentage), "Discount amount is matching calculated subpackage item level discounts 1st item1 discount: <b>" + dFirstSubpackageDiscountOnUI + "</b> 2nd item discount: <b>" + dSecondSubpackageDiscountOnUI);
                double dSummationOfSubpackageItemWiseDiscountInPercentage = dFirstSubpackageDiscountOnUI + dSecondSubpackageDiscountOnUI;
                double dNetAmountOfFirstSubpackageItemAfterGlobalDiscountInPercentage = dGrossPriceOfFirstItemBeforeDiscount - dFirstSubpackageDiscountOnUI;
                double dNetAmountOfSecondSubpackageItemAfterGlobalDiscountInPercentage = dGrossSubPackageItemPriceForSecondItem - dSecondSubpackageDiscountOnUI;
                String sNetAmountForFirstSubItem = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetAmountForFirstSubItem = Double.parseDouble(sNetAmountForFirstSubItem);
                double dSumNetAmountAfterGD = dNetAmountOfFirstSubpackageItemAfterGlobalDiscountInPercentage + dNetAmountOfSecondSubpackageItemAfterGlobalDiscountInPercentage;
                m_assert.assertTrue((dNetAmountForFirstSubItem == dSumNetAmountAfterGD), "Net amount on UI equals calculated net amount after applying global discount <b>" + dSumNetAmountAfterGD + "</b>");
                Cls_Generic_Methods.customWait(3);
                String sNetAmountOnBillAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_netBillTotal, "value");
                double dNetAmountOnBillAfterApplyingGD = Double.parseDouble(sNetAmountOnBillAfterApplyingGD);
                String sGrossBillTotalOnBillAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_grossBillTotal, "value");
                double dGrossBillTotalOnBillAfterApplyingGD = Double.parseDouble(sGrossBillTotalOnBillAfterApplyingGD);
                String sDiscountOnItemsAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnItem, "value");
                double dDiscountOnItemsAfterApplyingGD = Double.parseDouble(sDiscountOnItemsAfterApplyingGD);
                m_assert.assertTrue((dNetAmountOnBillAfterApplyingGD == dSumNetAmountAfterGD && dGrossBillTotalOnBillAfterApplyingGD == dGrossPrice && dDiscountOnItemsAfterApplyingGD == dSummationOfSubpackageItemWiseDiscountInPercentage), "Net amount on bill = <b>" + dNetAmountOnBillAfterApplyingGD + "</b> , Gross amount on bill =  <b>" + dGrossBillTotalOnBillAfterApplyingGD + "</b> , Discount on item = <b> " + dDiscountOnItemsAfterApplyingGD + "</b>");
                Cls_Generic_Methods.customWait(4);
                String sTotalDiscountOnItemsAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnBill, "value");
                double dTotalDiscountOnItemsAfterApplyingGD = Double.parseDouble(sTotalDiscountOnItemsAfterApplyingGD);
                String sTotalOfAllDiscountOnItemsAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalOfAllDiscounts, "value");
                double dTotalOfAllDiscountOnItemsAfterApplyingGD = Double.parseDouble(sTotalOfAllDiscountOnItemsAfterApplyingGD);
                m_assert.assertTrue((dTotalDiscountOnItemsAfterApplyingGD == dTotalOfAllDiscountOnItemsAfterApplyingGD), "Total discount and total of all discount fields, values matching on UI = <b>" + dTotalOfAllDiscountOnItemsAfterApplyingGD + "</b>");
                Cls_Generic_Methods.clickElement(oPage_IPD.button_removePaymentReceivedDetailsUnderBills);
                String sAmountPendingOnUI = Cls_Generic_Methods.getElementAttribute(oPage_IPD.input_amountPendingField, "value");
                double dAmountPendingOnUI = Double.parseDouble(sAmountPendingOnUI);
                m_assert.assertTrue((dAmountPendingOnUI == dNetAmountOnBillAfterApplyingGD), "Net amount on bill equals to amount pending against bill is matching = <b>" + dAmountPendingOnUI + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills), "<b>Credit bill is created </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.customWait(4);
                //bill preview validation
                String sFirstSubpackageItemDiscountOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(0));
                dFirstSubpackageItemDiscountOnBillPreview = Double.parseDouble(sFirstSubpackageItemDiscountOnBillPreview);

                String sSecondSubpackageItemDiscountOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(1));
                dSecondSubpackageItemDiscountOnBillPreview = Double.parseDouble(sSecondSubpackageItemDiscountOnBillPreview);
                m_assert.assertTrue((dFirstSubpackageItemDiscountOnBillPreview == dDiscountOnFirstItemAfterApplyingGlobalDiscountInPercentage && dSecondSubpackageItemDiscountOnBillPreview == dDiscountOnSecondItemAfterApplyingGlobalDiscountInPercentage), "Discount amount is matching with calculated subpackage item level discounts 1st item1 on preview : <b>" + dFirstSubpackageItemDiscountOnBillPreview + "</b> 2nd item discount on bill preview : <b>" + dSecondSubpackageItemDiscountOnBillPreview);
                String sOverDiscountAmountOnPackageOnPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(0));
                String sOverDiscountAmountOnPackageInPreview = sOverDiscountAmountOnPackageOnPreview.replaceAll("\\s", "");
                double dOverDiscountAmountOnPackagePreview = Double.parseDouble(sOverDiscountAmountOnPackageInPreview);
                dOverDiscountAmountOnPackageInPreview = dOverDiscountAmountOnPackagePreview * (-1);
                m_assert.assertTrue((dTotalDiscountOnItemsAfterApplyingGD == dOverDiscountAmountOnPackageInPreview), "OverAll discount amount on package on bill preview = <b>" + dTotalDiscountOnItemsAfterApplyingGD + "</b>");
                String sGrossBillTotalOnPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_grossBillTotalOnBillPreview);
                double dGrossBillTotalOnPreview = Double.parseDouble(sGrossBillTotalOnPreview);
                m_assert.assertTrue((dGrossPrice == dGrossBillTotalOnPreview), "Gross bill total on package on bill preview = <b>" + dGrossPrice + "</b>");

                String sTotalDiscountOnItemsInBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalDiscountOnItemsOnBillPreview);
                double dTotalDiscountOnItemsInBillPreview = Double.parseDouble(sTotalDiscountOnItemsInBillPreview);

                String sTotalOfAllDiscountOnItemsInBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalOfAllDiscountOnBillPreview);
                double dTotalOfAllDiscountOnItemsInBillPreview = Double.parseDouble(sTotalOfAllDiscountOnItemsInBillPreview);

                String sPaymentPendingOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_paymentPendingOnBillPreview);
                dPaymentPendingOnBillPreview = Double.parseDouble(sPaymentPendingOnBillPreview);
                m_assert.assertTrue((dTotalDiscountOnItemsAfterApplyingGD == dTotalDiscountOnItemsInBillPreview && dTotalDiscountOnItemsAfterApplyingGD == dTotalOfAllDiscountOnItemsInBillPreview), "Total discount on item and total of all discounts on bill preview = <b>" + dTotalDiscountOnItemsAfterApplyingGD + "</b>");
                m_assert.assertTrue((dPaymentPendingOnBillPreview == dAmountPendingOnUI), "Payment pending on bill preview = <b>" + dAmountPendingOnUI + "</b>");
                Cls_Generic_Methods.customWait(4);

                //Edit bill
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_editBill), "clicked on edit bill");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickIPDPlusActionButton, 30);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickIPDPlusActionButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Bills.button_newPackage);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.select_package.get(0));
                Cls_Generic_Methods.customWait(3);
                boolean PackageFound = false;
                for (WebElement eItemList : oPage_Bills.list_packageOrServiceNameUnderDescriptionField) {
                    if (Cls_Generic_Methods.getTextInElement(eItemList).equals(sSecondPackage)) {
                        Cls_Generic_Methods.clickElement(eItemList);
                        PackageFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(PackageFound, "Selected package name = <b>" + sSecondPackage + "</b> ");
            }

            Cls_Generic_Methods.customWait(4);
            String sQTY1 = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_Quantity.get(1), "value");
            String sUnitPrice1 = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(1), "value");
            Cls_Generic_Methods.customWait();
            String sGrossPrice1 = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(1), "value");
            double dGrossPrice1 = Double.parseDouble(sGrossPrice1);
            String sNetPrice1 = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
            double dOverAllNetPriceOnPackage1 = Double.parseDouble(sNetPrice1);
            Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(0));
            Cls_Generic_Methods.customWait(4);
            Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_mainDiscountFieldOnPackage.get(1));
            Cls_Generic_Methods.customWait();
            String sSecondItemDiscount = "2";
            double dSecondItemDiscount = Double.parseDouble(sSecondItemDiscount);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_mainDiscountFieldOnPackage.get(1), sSecondItemDiscount), "Applied discount for second package  = <b> " + sDiscountAmountOnFirstSubPackageItem + "</b>");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_discountReason.get(1), sDiscountReason);
            double dNetPriceOfSecondPackage = dGrossPrice1 - dSecondItemDiscount;
            String sNetPriceOfSecondPackage = String.valueOf(dNetPriceOfSecondPackage);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_internalComments, "InternalComments");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.scrollToElementByJS(oPage_Bills.list_modeOfPayment.get(0));
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_Bills.list_modeOfPayment.get(0), paymentMode), paymentMode + " option is selected for Mode Of Payment");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clearValuesInElement(oPage_Bills.input_amountReceivedField.get(0));
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_amountReceivedField.get(0), sNetPriceOfSecondPackage);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills), "<b>CREDIT bill is updated </b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
            Cls_Generic_Methods.customWait(4);

            String sFirstSubpackageItemDiscountOnBillPreviewAfterUpdatingBill = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(0));
            double dFirstSubpackageItemDiscountOnBillPreviewAfterUpdatingBill = Double.parseDouble(sFirstSubpackageItemDiscountOnBillPreviewAfterUpdatingBill);

            String sSecondSubpackageItemDiscountOnBillPreviewAfterUpdatingBill = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(1));
            double dSecondSubpackageItemDiscountOnBillPreviewAfterUpdatingBill = Double.parseDouble(sSecondSubpackageItemDiscountOnBillPreviewAfterUpdatingBill);

            m_assert.assertTrue((dFirstSubpackageItemDiscountOnBillPreview == dFirstSubpackageItemDiscountOnBillPreviewAfterUpdatingBill && dSecondSubpackageItemDiscountOnBillPreview == dSecondSubpackageItemDiscountOnBillPreviewAfterUpdatingBill), "After updating a bill Discount amount is matching with calculated subpackage item level discounts 1st item1 on preview : <b>" + dFirstSubpackageItemDiscountOnBillPreview + "</b>After updating a bill, 2nd item discount on bill preview : <b>" + dSecondSubpackageItemDiscountOnBillPreview);
            String sOverDiscountAmountOnFirstPackageOnPreviewAfterUpdatingAbill = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(0));
            String sOverDiscountAmountOnFirstPackageInPreviewAfterUpdatingAbill = sOverDiscountAmountOnFirstPackageOnPreviewAfterUpdatingAbill.replaceAll("\\s", "");
            double dOverDiscountAmountOnFirstPackagePreviewAfterUpdatingBill = Double.parseDouble(sOverDiscountAmountOnFirstPackageInPreviewAfterUpdatingAbill);
            double dOverDiscountAmountOnFirstPackageInPreviewAfterUpdatingBill = dOverDiscountAmountOnFirstPackagePreviewAfterUpdatingBill * (-1);
            m_assert.assertTrue((dOverDiscountAmountOnPackageInPreview == dOverDiscountAmountOnFirstPackageInPreviewAfterUpdatingBill), "After updating a bill OverAll discount amount on 1st package on bill preview = <b>" + dOverDiscountAmountOnFirstPackageInPreviewAfterUpdatingBill + "</b>");
            // second package details
            String sOverDiscountAmountOnSecondPackageOnPreviewAfterUpdatingAbill = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(1));
            String sOverDiscountAmountOnSecondPackagOnPreviewAfterUpdatingAbill = sOverDiscountAmountOnSecondPackageOnPreviewAfterUpdatingAbill.replaceAll("\\s", "");
            double dOverDiscountAmountOnSecondPackagePreviewAfterUpdatingBill = Double.parseDouble(sOverDiscountAmountOnSecondPackagOnPreviewAfterUpdatingAbill);
            double dOverDiscountAmountOnSecondPackageInPreviewAfterUpdatingBill = dOverDiscountAmountOnSecondPackagePreviewAfterUpdatingBill * (-1);
            double dSumOfDiscountAmountOnEachPackageInPreview = dOverDiscountAmountOnSecondPackageInPreviewAfterUpdatingBill + dOverDiscountAmountOnFirstPackageInPreviewAfterUpdatingBill;
            String sGrossBillTotalOnPreviewAfterUpdate = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_grossBillTotalOnBillPreview);
            double dGrossBillTotalOnPreview = Double.parseDouble(sGrossBillTotalOnPreviewAfterUpdate);
            double dSumOfGrossAmountOfEachPackage = dGrossPrice1 + dGrossPrice;
            m_assert.assertTrue((dGrossBillTotalOnPreview == dSumOfGrossAmountOfEachPackage), "After updating a bill, Gross bill total on each bill preview = <b>" + dGrossBillTotalOnPreview + "</b>");
            //total discount on items
            String sTotalDiscountOnItemsInBillPreviewAfterBillUpdate = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalDiscountOnItemsOnBillPreview);
            double dTotalDiscountOnItemsInBillPreviewAfterBillUpdate = Double.parseDouble(sTotalDiscountOnItemsInBillPreviewAfterBillUpdate);
            //total of all discounts
            String sTotalOfAllDiscountOnItemsInBillPreviewAfterBillUpdate = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalOfAllDiscountOnBillPreview);
            double dTotalOfAllDiscountOnItemsInBillPreviewAfterBillUpdate = Double.parseDouble(sTotalOfAllDiscountOnItemsInBillPreviewAfterBillUpdate);

            m_assert.assertTrue((dTotalDiscountOnItemsInBillPreviewAfterBillUpdate == dSumOfDiscountAmountOnEachPackageInPreview && dTotalOfAllDiscountOnItemsInBillPreviewAfterBillUpdate == dTotalDiscountOnItemsInBillPreviewAfterBillUpdate), "After updating a bill, Total item discount and total of all discount on bill preview is matching with calulated value  = <b>" + dTotalDiscountOnItemsInBillPreviewAfterBillUpdate + "</b>");

            String sPaymentReceivedOnBillPreviewAfterBillUpdate = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_paymentReceivedOnBillPreview);
            double dPaymentReceivedOnBillPreviewAfterBillUpdate = Double.parseDouble(sPaymentReceivedOnBillPreviewAfterBillUpdate);
            m_assert.assertTrue((dPaymentReceivedOnBillPreviewAfterBillUpdate == dNetPriceOfSecondPackage), "Payment Received on bill preview after bill update = <b>" + dPaymentReceivedOnBillPreviewAfterBillUpdate + "</b>");
            Cls_Generic_Methods.customWait(4);

            String sPaymentPendingOnBillPreviewAfterBillUpdate = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_paymentPendingOnBillPreview);
            double dPaymentPendingOnBillPreviewAfterBillUpdate = Double.parseDouble(sPaymentPendingOnBillPreviewAfterBillUpdate);
            m_assert.assertTrue((dPaymentPendingOnBillPreviewAfterBillUpdate == dPaymentPendingOnBillPreview), "Payment Pending on bill preview after bill update = <b>" + dPaymentPendingOnBillPreviewAfterBillUpdate + "</b>");
            Cls_Generic_Methods.customWait(4);
            Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_cancelBill);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.input_refundReason, 30);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_refundReason, "Reason");
            Cls_Generic_Methods.selectElementByVisibleText(oPage_Bills.select_mopInRefundCancellationForm, "Cash");
            Cls_Generic_Methods.clearValuesInElement(oPage_Bills.input_cancellationAmount);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_cancellationAmount, sPaymentReceivedOnBillPreviewAfterBillUpdate);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_saveRefundReciept), "Credit Bill Cancelled");
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.clickElementByAction(driver, oPage_Bills.button_closeBill);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
            Cls_Generic_Methods.customWait(3);

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }

    }

    @Test(enabled = true, description = "create Credit bill and validate the Subpackage item level  Discount")
    public void createDraftBillAndValidateSubPackageItemLevelDiscount() {
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills = new Page_Bills(driver);
        oPage_IPD = new Page_IPD(driver);
        boolean bPatientFound = false;
        String paymentMode = "Cash";
        String sPackageName = "AutomationPackageDataDisplaySelf";
        String sDiscountAmountOnFirstSubPackageItem = "4";
        String sDiscountAmountOnSecondSubPackageItem = "3";
        String sGlobalDiscount = "10";
        String sDiscountReason = "Reason" + getRandomUniqueString(6);
        String sSecondPackage = "Testing Facility";
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectFacility("TST");
            CommonActions.selectDepartmentOnApp("IPD");
            myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
            m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today), "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");
            Cls_Generic_Methods.customWait(10);

            bPatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnScheduledTodayTab, concatPatientFullName);
            m_assert.assertTrue(bPatientFound, "Validate that the patient " + concatPatientFullName + " is created in IPD");

            double dFirstSubpackageItemDiscountOnBillPreview = 0;
            double dSecondSubpackageItemDiscountOnBillPreview = 0;
            double dOverDiscountAmountOnPackageInPreview = 0;
            double dGrossPrice = 0;
            double dPaymentPendingOnBillPreview = 0;
            if (bPatientFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills), "Clicked on bills button");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_IPD.button_newDraftBill);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 30);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickIPDPlusActionButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Bills.button_newPackage);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_deleteItemPackage.get(0));
                Cls_Generic_Methods.clickElement(oPage_Bills.select_package.get(0));
                boolean myPackageFound = false;
                for (WebElement eItemList : oPage_Bills.list_packageOrServiceNameUnderDescriptionField) {
                    String sPackageNameOnUI = Cls_Generic_Methods.getTextInElement(eItemList);
                    if (sPackageNameOnUI.equals(sPackageName)) {
                        Cls_Generic_Methods.clickElement(eItemList);
                        myPackageFound = true;
                        break;
                    }

                }
                m_assert.assertTrue(myPackageFound, "Selected package name = <b>" + sPackageName + "</b> ");
                Cls_Generic_Methods.customWait(4);
                String sQTY = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_Quantity.get(0), "value");
                String sUnitPrice = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(0), "value");
                String sGrossPrice = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(0), "value");
                dGrossPrice = Double.parseDouble(sGrossPrice);
                String sNetPrice = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dOverAllNetPriceOnPackage = Double.parseDouble(sNetPrice);
                Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(0));
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_deleteItemLevelDiscount.get(0));
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_subpackageItemLevelDiscountField.get(0))) {
                    m_assert.assertWarn("First Subpackage item level Discount field is  displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("First Subpackage item level Discount field is not displaying as user deleted overall discount field ");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_subpackageItemLevelDiscountField.get(1))) {
                    m_assert.assertWarn("Second Subpackage item level Discount field is not displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Second Subpackage item level Discount field is  displaying as user deleted overall discount field ");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_mainDiscountFieldOnPackage.get(0))) {
                    m_assert.assertWarn("Package level Discount field is displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Package level Discount field is displaying as user  deleted overall discount field ");
                }
                Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(0));
                Cls_Generic_Methods.customWait(4);
                String sUnitCostOnFirstItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemUnitCost.get(0));
                String sNoOfUnitsCostPerDayOnUI = sUnitCostOnFirstItemUI.split(" ")[0];
                Double dNoOfUnitsCostPerDayOnUI = Double.parseDouble(sNoOfUnitsCostPerDayOnUI);
                // String sUnitCostOfFirstItem = sUnitCostOnFirstItemUI.split("\\(")[0];
                String sNoOfUnitsOrDays = sUnitCostOnFirstItemUI.split("\\(")[1];
                String sNoUnitsPerDayForFirstItem = sNoOfUnitsOrDays.split(" ")[0];
                Double dNoUnitsPerDayForFirstItem = Double.parseDouble(sNoUnitsPerDayForFirstItem);

                String sGrossSubPackageItemPriceForFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(0));
                String sNetPriceItemPriceForFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                Cls_Generic_Methods.customWait(4);
                double dNetPriceOfFirstItemBeforeDiscount = Double.parseDouble(sNetPriceItemPriceForFirstItem);
                double dGrossPriceOfFirstItemBeforeDiscount = Double.parseDouble(sGrossSubPackageItemPriceForFirstItem);
                double dCalculatedGrossPriceAndNetPriceForFirstItem = dNoOfUnitsCostPerDayOnUI * dNoUnitsPerDayForFirstItem;
                if (dCalculatedGrossPriceAndNetPriceForFirstItem == dNetPriceOfFirstItemBeforeDiscount && dCalculatedGrossPriceAndNetPriceForFirstItem == dGrossPriceOfFirstItemBeforeDiscount) {
                    m_assert.assertTrue("Calculated gross price for first package level item is equal to net price and gross price on UI, Before applying a discount = <b>  " + dNetPriceOfFirstItemBeforeDiscount + "</b>");
                } else {
                    m_assert.assertWarn("Calculated gross price first package level item is not equal to net price on UI and gross price on UI, Before applying a discount");
                }

                String sUnitCostOnSecondItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemUnitCost.get(1));
                // String sUnitCostOfSecondItem = sUnitCostOnSecondItemUI.split("\\(")[0];
                String sNoOfUnitsOrDaysOnUIForSecondItem = sUnitCostOnSecondItemUI.split(" ")[0];
                Double dNoOfUnitsOrDaysOnUIForSecondItem = Double.parseDouble(sNoOfUnitsOrDaysOnUIForSecondItem);
                String sNoOfUnitsOrDaysForSecondItem = sUnitCostOnSecondItemUI.split("\\(")[1];
                String sNoUnitsPerDayForSecondItems = sNoOfUnitsOrDaysForSecondItem.split(" ")[0];
                Double dNoUnitsPerDayForSecondItems = Double.parseDouble(sNoUnitsPerDayForSecondItems);
                String sGrossSubPackageItemPriceForSecondItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(1));
                String sNetPriceItemPriceForSecondItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dGrossSubPackageItemPriceForSecondItem = Double.parseDouble(sGrossSubPackageItemPriceForSecondItem);
                double dNetPriceItemPriceForSecondItem = Double.parseDouble(sNetPriceItemPriceForSecondItem);
                double dCalculatedGrossPriceAndNetPriceForSecondItem = dNoOfUnitsOrDaysOnUIForSecondItem * dNoUnitsPerDayForSecondItems;
                if (dCalculatedGrossPriceAndNetPriceForSecondItem == dNetPriceItemPriceForSecondItem && dCalculatedGrossPriceAndNetPriceForSecondItem == dGrossSubPackageItemPriceForSecondItem) {
                    m_assert.assertTrue("Calculated gross price for Second package level item is equal to net price and gross price on UI, Before applying a discount<b> " + dNetPriceItemPriceForSecondItem + "</b>");
                } else {
                    m_assert.assertWarn("Calculated gross price second package level item is not equal to net price on UI and gross price on UI, Before applying a discount");
                }
                double dSumOfFistAndSecondSubpackageItemsNetPrice = dNetPriceItemPriceForSecondItem + dNetPriceOfFirstItemBeforeDiscount;
                if (dSumOfFistAndSecondSubpackageItemsNetPrice == dOverAllNetPriceOnPackage) {
                    m_assert.assertTrue("summation of first and second subpackage level items net price is equal to overall subpackage net price, Before applying a discount<b>  " + dOverAllNetPriceOnPackage + "</b>");
                } else {
                    m_assert.assertWarn("summation of first and second subpackage level items net price is equal to overall subpackage net price, Before applying a discount");
                }
                // Apply discount on each subPackage item
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(0));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(0), sDiscountAmountOnFirstSubPackageItem), "Applied Sub package item level discount for first item = <b> " + sDiscountAmountOnFirstSubPackageItem + "</b>");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(1));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.list_subpackageItemLevelDiscountTypes.get(1), "%"), "Discount type for second subpackage level item =  <b>%</b>");
                Cls_Generic_Methods.customWait(4);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(1), sDiscountAmountOnSecondSubPackageItem), "Applied Sub package item level discount for second item = <b> " + sDiscountAmountOnSecondSubPackageItem + "</b>");
                double dDiscountAmountOnSecondSubPackageItem = Double.parseDouble(sDiscountAmountOnSecondSubPackageItem);
                double dDiscountPercentageForSecondItem = dDiscountAmountOnSecondSubPackageItem / 100;
                double dDiscountAmountAfterApplyingDiscountInPercentageForSecondItem = dNetPriceItemPriceForSecondItem * dDiscountPercentageForSecondItem;
                double dRoundingOffTheDiscountForSecondItem = roundOffFunctionUsingDouble(dDiscountAmountAfterApplyingDiscountInPercentageForSecondItem);
                //Multiplying with minus as we are showing discount value in negative
                double dRoundingOffTheDiscountOnSecondItem = dRoundingOffTheDiscountForSecondItem * (-1);
                String sFirstSubpackageItemDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dFirstSubpackageItemDiscountAmount = Double.parseDouble(sFirstSubpackageItemDiscountAmount);
                double dNetPriceOfFirstItemAfterDiscount = dNetPriceOfFirstItemBeforeDiscount + dFirstSubpackageItemDiscountAmount;
                String sNetAmountAfterDiscountForFirstItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                double dNetAmountAfterDiscountForFirstItemOnUI = Double.parseDouble(sNetAmountAfterDiscountForFirstItemOnUI);
                if (dNetPriceOfFirstItemAfterDiscount == dNetAmountAfterDiscountForFirstItemOnUI) {
                    m_assert.assertTrue("Net price amount for first subpackage item after applying Discount =  <b>" + dNetAmountAfterDiscountForFirstItemOnUI + "</b>");
                } else {
                    m_assert.assertWarn("Calculated Net price amount for first subpackage item after applying Discount is not matching with UI net price");
                }
                String sSecondSubpackageItemDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(1));
                double dSecondSubpackageItemDiscountAmount = Double.parseDouble(sSecondSubpackageItemDiscountAmount);
                double dNetPriceOfSecondItemAfterDiscount = dNetPriceItemPriceForSecondItem + dSecondSubpackageItemDiscountAmount;
                String sNetAmountAfterDiscountForSecondItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dNetAmountAfterDiscountForSecondItemOnUI = Double.parseDouble(sNetAmountAfterDiscountForSecondItemOnUI);
                if (dRoundingOffTheDiscountOnSecondItem == dSecondSubpackageItemDiscountAmount) {
                    m_assert.assertTrue("Discount amount for second subpackage item on UI is matched with calculated Discount amount= <b>" + dSecondSubpackageItemDiscountAmount + "</b>");
                } else {
                    m_assert.assertWarn("Discount amount for second subpackage item on UI is not matching with calculated Discount amount");
                }
                if (dNetPriceOfSecondItemAfterDiscount == dNetAmountAfterDiscountForSecondItemOnUI) {
                    m_assert.assertTrue("Net price amount for second subpackage item after applying Discount =  <b>" + dNetAmountAfterDiscountForFirstItemOnUI + "</b>");
                } else {
                    m_assert.assertWarn("Calculated Net price  for second subpackage item after applying Discount is not matching with UI net price");
                }
                // summation subpackage item level discount
                double dTotalDiscountAmountOnOverAllSubpackage = dFirstSubpackageItemDiscountAmount + dSecondSubpackageItemDiscountAmount;
                String sOverAllDiscountOnPackage = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_overallDiscountAmountOnPackage);
                String sOverAllDiscountPackage = sOverAllDiscountOnPackage.replaceAll("\\s", "");
                double dOverAllDiscountOnPackage = Double.parseDouble(sOverAllDiscountPackage);
                if (dOverAllDiscountOnPackage == dTotalDiscountAmountOnOverAllSubpackage) {
                    m_assert.assertTrue("Subpackage item level discounts are matching with overall subpackage discount field =  <b>" + dTotalDiscountAmountOnOverAllSubpackage + "</b>");
                } else {
                    m_assert.assertWarn("Subpackage item level discounts are not matching with overall subpackage discount field");
                }
                //Over all package net price after discount
                double dNetAmountOnOverAllPackageAfterApplyingDiscount = dOverAllNetPriceOnPackage + dTotalDiscountAmountOnOverAllSubpackage;
                String sNetAmtAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetAmtAfterDiscount = Double.parseDouble(sNetAmtAfterDiscount);
                m_assert.assertTrue((dNetAmountOnOverAllPackageAfterApplyingDiscount == dNetAmtAfterDiscount), "First package's Calculated Net Amount after discount is matching with Net amount on UI = <b>" + dNetAmtAfterDiscount + "</b>");

                // delete all subpackage item wise discounts
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_deleteSubPackageItemsDiscount.get(1)), "Deleted second subpackage item's discount");
                String sSecondSubPackageNetAmountAfterDeletingItemLevelDiscount = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dSecondSubPackageNetAmountAfterDeletingItemLevelDiscount = Double.parseDouble(sSecondSubPackageNetAmountAfterDeletingItemLevelDiscount);
                m_assert.assertTrue((dSecondSubPackageNetAmountAfterDeletingItemLevelDiscount == dCalculatedGrossPriceAndNetPriceForSecondItem), "Upon deleting a second subpackage level item discount calculated Net Amount is matching with Net amount on UI = <b>" + dSecondSubPackageNetAmountAfterDeletingItemLevelDiscount + "</b>");

                //net price of first sub package level item after discount
                String sFirstSubpackageItemDiscountAfterRemovingSecondItemsDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dFirstSubpackageItemAfterRemovingSecondItemsDiscountAmount = Double.parseDouble(sFirstSubpackageItemDiscountAfterRemovingSecondItemsDiscountAmount);
                double dNetPriceOfFirstItemAfterRemovingSecondItemsDiscount = dNetPriceOfFirstItemBeforeDiscount + dFirstSubpackageItemAfterRemovingSecondItemsDiscountAmount;
                String sNetAmountAfterDiscountOnFirstItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                double dNetAmountAfterDiscountOnFirstItemOnUI = Double.parseDouble(sNetAmountAfterDiscountOnFirstItemOnUI);
                m_assert.assertTrue((dNetAmountAfterDiscountOnFirstItemOnUI == dNetPriceOfFirstItemAfterRemovingSecondItemsDiscount), "Upon deleting a second subpackage level discount calculated Net Amount of second item is matching with Net amount on UI of second item <b>" + dNetPriceOfFirstItemAfterRemovingSecondItemsDiscount + "</b>");
                //Over all package net price after deleting second items discount
                double dCalculatedNetAmountAfterRemovingSecondItemsDiscount = dNetAmountOnOverAllPackageAfterApplyingDiscount + dRoundingOffTheDiscountForSecondItem;
                String sNetAmtAfterRemovingSecondItemDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetAmtAfterRemovingSecondItemDiscount = Double.parseDouble(sNetAmtAfterRemovingSecondItemDiscount);
                m_assert.assertTrue((dCalculatedNetAmountAfterRemovingSecondItemsDiscount == dNetAmtAfterRemovingSecondItemDiscount), "Over All package Net Amount after removing second subpackage items discount is matching with Overall package Net amount on UI = <b>" + dCalculatedNetAmountAfterRemovingSecondItemsDiscount + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Bills.button_addSubPackageItemsDiscount.get(1));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmount.get(0), sGlobalDiscount), "Entered amount in Global discount field = <b>" + sGlobalDiscount + "</b>");
                // m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);
                double dGlobalDiscount = Double.parseDouble(sGlobalDiscount);
                double dGlobalDiscountPercentage = dGlobalDiscount / dOverAllNetPriceOnPackage;
                double dDiscountOnFirstItemAfterApplyingGlobalDiscount = dGlobalDiscountPercentage * dGrossPriceOfFirstItemBeforeDiscount;
                double dRoundOffDiscount1 = roundOffFunctionUsingDouble(dDiscountOnFirstItemAfterApplyingGlobalDiscount);
                String sDiscountOn1stSubItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dDiscountOn1stSubItem = Double.parseDouble(sDiscountOn1stSubItem);
                double dDiscountOnSecondItemAfterApplyingGlobalDiscount = dGlobalDiscountPercentage * dGrossSubPackageItemPriceForSecondItem;
                double dRoundOffDiscount2 = roundOffFunctionUsingDouble(dDiscountOnSecondItemAfterApplyingGlobalDiscount);
                String sDiscountOn2ndSubItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(1));
                double dDiscountOn2ndSubItem = Double.parseDouble(sDiscountOn2ndSubItem);
                double dSummationOfSubpackageItemWiseDiscount = dDiscountOnFirstItemAfterApplyingGlobalDiscount + dDiscountOnSecondItemAfterApplyingGlobalDiscount;
                double dUpdateDiscount1stAmountToPositive = dDiscountOn1stSubItem * (-1);
                double dUpdateDiscount2ndAmountToPositive = dDiscountOn2ndSubItem * (-1);
                double dNetAmountOfFirstSubpackageItemAfterGlobalDiscount = dGrossPriceOfFirstItemBeforeDiscount - dDiscountOnFirstItemAfterApplyingGlobalDiscount;
                double dNetAmountOfSecondSubpackageItemAfterGlobalDiscount = dGrossSubPackageItemPriceForSecondItem - dDiscountOnSecondItemAfterApplyingGlobalDiscount;
                m_assert.assertTrue((dUpdateDiscount1stAmountToPositive == dRoundOffDiscount1 && dUpdateDiscount2ndAmountToPositive == dRoundOffDiscount2), "Discount on 1st subpackage is matching with discount calculations = <b>" + dRoundOffDiscount1 + "</b>, Discount on 2nd subpackage is matching with discount calculations  =  <b>" + dRoundOffDiscount2 + "</b>");
                m_assert.assertTrue((dSummationOfSubpackageItemWiseDiscount == dGlobalDiscount), "Subpackage item wise discounts sum is equal to Global discount amount <b>" + dGlobalDiscount + "</b>");
                Cls_Generic_Methods.customWait(3);
                //Apply GD in percentage
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_globalDiscountType, "%"), "Discount type for GD =  <b>%</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmount.get(0), sGlobalDiscount), "Entered amount in Global discount field = <b>" + sGlobalDiscount + "</b>");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_discountReason.get(0), sDiscountReason);
                double dGlobalDiscountInPercentage = dGlobalDiscount / 100;
                double dDiscountOnFirstItemAfterApplyingGlobalDiscountInPercentage = dGlobalDiscountInPercentage * dGrossPriceOfFirstItemBeforeDiscount;
                double dDiscountOnSecondItemAfterApplyingGlobalDiscountInPercentage = dGlobalDiscountInPercentage * dGrossSubPackageItemPriceForSecondItem;
                String sDiscount1 = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dDiscount1 = Double.parseDouble(sDiscount1);
                double dFirstSubpackageDiscountOnUI = dDiscount1 * (-1);
                String sDiscount2 = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(1));
                double dDiscount2 = Double.parseDouble(sDiscount2);
                double dSecondSubpackageDiscountOnUI = dDiscount2 * (-1);
                m_assert.assertTrue((dFirstSubpackageDiscountOnUI == dDiscountOnFirstItemAfterApplyingGlobalDiscountInPercentage && dSecondSubpackageDiscountOnUI == dDiscountOnSecondItemAfterApplyingGlobalDiscountInPercentage), "Discount amount is matching calculated subpackage item level discounts 1st item1 discount: <b>" + dFirstSubpackageDiscountOnUI + "</b> 2nd item discount: <b>" + dSecondSubpackageDiscountOnUI);
                double dSummationOfSubpackageItemWiseDiscountInPercentage = dFirstSubpackageDiscountOnUI + dSecondSubpackageDiscountOnUI;
                double dNetAmountOfFirstSubpackageItemAfterGlobalDiscountInPercentage = dGrossPriceOfFirstItemBeforeDiscount - dFirstSubpackageDiscountOnUI;
                double dNetAmountOfSecondSubpackageItemAfterGlobalDiscountInPercentage = dGrossSubPackageItemPriceForSecondItem - dSecondSubpackageDiscountOnUI;
                String sNetAmountForFirstSubItem = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetAmountForFirstSubItem = Double.parseDouble(sNetAmountForFirstSubItem);
                double dSumNetAmountAfterGD = dNetAmountOfFirstSubpackageItemAfterGlobalDiscountInPercentage + dNetAmountOfSecondSubpackageItemAfterGlobalDiscountInPercentage;
                m_assert.assertTrue((dNetAmountForFirstSubItem == dSumNetAmountAfterGD), "Net amount on UI equals calculated net amount after applying global discount <b>" + dSumNetAmountAfterGD + "</b>");
                Cls_Generic_Methods.customWait(3);
                String sNetAmountOnBillAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_netBillTotal, "value");
                double dNetAmountOnBillAfterApplyingGD = Double.parseDouble(sNetAmountOnBillAfterApplyingGD);
                String sGrossBillTotalOnBillAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_grossBillTotal, "value");
                double dGrossBillTotalOnBillAfterApplyingGD = Double.parseDouble(sGrossBillTotalOnBillAfterApplyingGD);
                String sDiscountOnItemsAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnItem, "value");
                double dDiscountOnItemsAfterApplyingGD = Double.parseDouble(sDiscountOnItemsAfterApplyingGD);
                m_assert.assertTrue((dNetAmountOnBillAfterApplyingGD == dSumNetAmountAfterGD && dGrossBillTotalOnBillAfterApplyingGD == dGrossPrice && dDiscountOnItemsAfterApplyingGD == dSummationOfSubpackageItemWiseDiscountInPercentage), "Net amount on bill = <b>" + dNetAmountOnBillAfterApplyingGD + "</b> , Gross amount on bill =  <b>" + dGrossBillTotalOnBillAfterApplyingGD + "</b> , Discount on item = <b> " + dDiscountOnItemsAfterApplyingGD + "</b>");
                Cls_Generic_Methods.customWait(4);
                String sTotalDiscountOnItemsAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnBill, "value");
                double dTotalDiscountOnItemsAfterApplyingGD = Double.parseDouble(sTotalDiscountOnItemsAfterApplyingGD);
                String sTotalOfAllDiscountOnItemsAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalOfAllDiscounts, "value");
                double dTotalOfAllDiscountOnItemsAfterApplyingGD = Double.parseDouble(sTotalOfAllDiscountOnItemsAfterApplyingGD);
                m_assert.assertTrue((dTotalDiscountOnItemsAfterApplyingGD == dTotalOfAllDiscountOnItemsAfterApplyingGD), "Total discount and total of all discount fields, values matching on UI = <b>" + dTotalOfAllDiscountOnItemsAfterApplyingGD + "</b>");
                String sAmountRemainingOnUI = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_balanceRemainingAmount, "value");
                double dAmountRemainingOnUI = Double.parseDouble(sAmountRemainingOnUI);
                Cls_Generic_Methods.clearValuesInElement(oPage_IPD.input_amountPendingField);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_IPD.input_amountPendingField, sAmountRemainingOnUI), "Pending amount entered =  <b> " + sAmountRemainingOnUI + "</b>");
                m_assert.assertTrue((dAmountRemainingOnUI == dNetAmountOnBillAfterApplyingGD), "Net amount on bill equals to amount remaining against bill is matching = <b>" + dAmountRemainingOnUI + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_saveDraftBillButton), "<b>Draft bill is created </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.customWait(4);
                //bill preview validation
                String sFirstSubpackageItemDiscountOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(0));
                dFirstSubpackageItemDiscountOnBillPreview = Double.parseDouble(sFirstSubpackageItemDiscountOnBillPreview);

                String sSecondSubpackageItemDiscountOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(1));
                dSecondSubpackageItemDiscountOnBillPreview = Double.parseDouble(sSecondSubpackageItemDiscountOnBillPreview);
                m_assert.assertTrue((dFirstSubpackageItemDiscountOnBillPreview == dDiscountOnFirstItemAfterApplyingGlobalDiscountInPercentage && dSecondSubpackageItemDiscountOnBillPreview == dDiscountOnSecondItemAfterApplyingGlobalDiscountInPercentage), "Discount amount is matching with calculated subpackage item level discounts 1st item1 on preview : <b>" + dFirstSubpackageItemDiscountOnBillPreview + "</b> 2nd item discount on bill preview : <b>" + dSecondSubpackageItemDiscountOnBillPreview);
                String sOverDiscountAmountOnPackageOnPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(0));
                String sOverDiscountAmountOnPackageInPreview = sOverDiscountAmountOnPackageOnPreview.replaceAll("\\s", "");
                double dOverDiscountAmountOnPackagePreview = Double.parseDouble(sOverDiscountAmountOnPackageInPreview);
                dOverDiscountAmountOnPackageInPreview = dOverDiscountAmountOnPackagePreview * (-1);
                m_assert.assertTrue((dTotalDiscountOnItemsAfterApplyingGD == dOverDiscountAmountOnPackageInPreview), "OverAll discount amount on package on bill preview = <b>" + dTotalDiscountOnItemsAfterApplyingGD + "</b>");
                String sGrossBillTotalOnPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_grossBillTotalOnBillPreview);
                double dGrossBillTotalOnPreview = Double.parseDouble(sGrossBillTotalOnPreview);
                m_assert.assertTrue((dGrossPrice == dGrossBillTotalOnPreview), "Gross bill total on package on bill preview = <b>" + dGrossPrice + "</b>");

                String sTotalDiscountOnItemsInBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalDiscountOnItemsOnBillPreview);
                double dTotalDiscountOnItemsInBillPreview = Double.parseDouble(sTotalDiscountOnItemsInBillPreview);

                String sTotalOfAllDiscountOnItemsInBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalOfAllDiscountOnBillPreview);
                double dTotalOfAllDiscountOnItemsInBillPreview = Double.parseDouble(sTotalOfAllDiscountOnItemsInBillPreview);

                String sPaymentPendingOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_paymentPendingOnBillPreview);
                dPaymentPendingOnBillPreview = Double.parseDouble(sPaymentPendingOnBillPreview);
                m_assert.assertTrue((dTotalDiscountOnItemsAfterApplyingGD == dTotalDiscountOnItemsInBillPreview && dTotalDiscountOnItemsAfterApplyingGD == dTotalOfAllDiscountOnItemsInBillPreview), "Total discount on item and total of all discounts on bill preview = <b>" + dTotalDiscountOnItemsAfterApplyingGD + "</b>");
                m_assert.assertTrue((dPaymentPendingOnBillPreview == dAmountRemainingOnUI), "Payment pending on bill preview = <b>" + dAmountRemainingOnUI + "</b>");
                Cls_Generic_Methods.customWait(4);

                //Edit bill
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_editDraftBill), "clicked on edit draft bill");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickIPDPlusActionButton, 30);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickIPDPlusActionButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Bills.button_newPackage);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.select_package.get(0));
                Cls_Generic_Methods.customWait(3);
                boolean PackageFound = false;
                for (WebElement eItemList : oPage_Bills.list_packageOrServiceNameUnderDescriptionField) {
                    if (Cls_Generic_Methods.getTextInElement(eItemList).equals(sSecondPackage)) {
                        Cls_Generic_Methods.clickElement(eItemList);
                        PackageFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(PackageFound, "Selected package name = <b>" + sSecondPackage + "</b> ");
            }

            Cls_Generic_Methods.customWait(4);
            String sQTY1 = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_Quantity.get(1), "value");
            String sUnitPrice1 = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(1), "value");
            Cls_Generic_Methods.customWait();
            String sGrossPrice1 = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(1), "value");
            double dGrossPrice1 = Double.parseDouble(sGrossPrice1);
            String sNetPrice1 = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
            double dOverAllNetPriceOnPackage1 = Double.parseDouble(sNetPrice1);
            Cls_Generic_Methods.clickElementByAction(driver, oPage_Bills.input_itemWiseDiscountButton.get(1));
            Cls_Generic_Methods.customWait(4);
            String sSecondItemDiscount = "2";
            double dSecondItemDiscount = Double.parseDouble(sSecondItemDiscount);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_mainDiscountFieldOnPackage.get(1), sSecondItemDiscount), "Applied discount for second package  = <b> " + sDiscountAmountOnFirstSubPackageItem + "</b>");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_discountReason.get(1), sDiscountReason);
            double dNetPriceOfSecondPackage = dGrossPrice1 - dSecondItemDiscount;
            String sNetPriceOfSecondPackage = String.valueOf(dNetPriceOfSecondPackage);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_internalComments, "InternalComments");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_Bills.button_addCreditPayment);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_amountPendingField.get(1));
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_amountPendingField.get(1), sNetPriceOfSecondPackage);
            Cls_Generic_Methods.customWait();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_saveDraftBillButton), "<b>Draft bill is updated </b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
            Cls_Generic_Methods.customWait(4);

            String sFirstSubpackageItemDiscountOnBillPreviewAfterUpdatingBill = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(0));
            double dFirstSubpackageItemDiscountOnBillPreviewAfterUpdatingBill = Double.parseDouble(sFirstSubpackageItemDiscountOnBillPreviewAfterUpdatingBill);

            String sSecondSubpackageItemDiscountOnBillPreviewAfterUpdatingBill = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(1));
            double dSecondSubpackageItemDiscountOnBillPreviewAfterUpdatingBill = Double.parseDouble(sSecondSubpackageItemDiscountOnBillPreviewAfterUpdatingBill);

            m_assert.assertTrue((dFirstSubpackageItemDiscountOnBillPreview == dFirstSubpackageItemDiscountOnBillPreviewAfterUpdatingBill && dSecondSubpackageItemDiscountOnBillPreview == dSecondSubpackageItemDiscountOnBillPreviewAfterUpdatingBill), "After updating a bill Discount amount is matching with calculated subpackage item level discounts 1st item1 on preview : <b>" + dFirstSubpackageItemDiscountOnBillPreview + "</b>After updating a bill, 2nd item discount on bill preview : <b>" + dSecondSubpackageItemDiscountOnBillPreview);
            String sOverDiscountAmountOnFirstPackageOnPreviewAfterUpdatingAbill = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(0));
            String sOverDiscountAmountOnFirstPackageInPreviewAfterUpdatingAbill = sOverDiscountAmountOnFirstPackageOnPreviewAfterUpdatingAbill.replaceAll("\\s", "");
            double dOverDiscountAmountOnFirstPackagePreviewAfterUpdatingBill = Double.parseDouble(sOverDiscountAmountOnFirstPackageInPreviewAfterUpdatingAbill);
            double dOverDiscountAmountOnFirstPackageInPreviewAfterUpdatingBill = dOverDiscountAmountOnFirstPackagePreviewAfterUpdatingBill * (-1);
            m_assert.assertTrue((dOverDiscountAmountOnPackageInPreview == dOverDiscountAmountOnFirstPackageInPreviewAfterUpdatingBill), "After updating a bill OverAll discount amount on 1st package on bill preview = <b>" + dOverDiscountAmountOnFirstPackageInPreviewAfterUpdatingBill + "</b>");
            // second package details
            String sOverDiscountAmountOnSecondPackageOnPreviewAfterUpdatingAbill = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(1));
            String sOverDiscountAmountOnSecondPackagOnPreviewAfterUpdatingAbill = sOverDiscountAmountOnSecondPackageOnPreviewAfterUpdatingAbill.replaceAll("\\s", "");
            double dOverDiscountAmountOnSecondPackagePreviewAfterUpdatingBill = Double.parseDouble(sOverDiscountAmountOnSecondPackagOnPreviewAfterUpdatingAbill);
            double dOverDiscountAmountOnSecondPackageInPreviewAfterUpdatingBill = dOverDiscountAmountOnSecondPackagePreviewAfterUpdatingBill * (-1);
            double dSumOfDiscountAmountOnEachPackageInPreview = dOverDiscountAmountOnSecondPackageInPreviewAfterUpdatingBill + dOverDiscountAmountOnFirstPackageInPreviewAfterUpdatingBill;
            String sGrossBillTotalOnPreviewAfterUpdate = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_grossBillTotalOnBillPreview);
            double dGrossBillTotalOnPreview = Double.parseDouble(sGrossBillTotalOnPreviewAfterUpdate);
            double dSumOfGrossAmountOfEachPackage = dGrossPrice1 + dGrossPrice;
            m_assert.assertTrue((dGrossBillTotalOnPreview == dSumOfGrossAmountOfEachPackage), "After updating a bill, Gross bill total on each bill preview = <b>" + dGrossBillTotalOnPreview + "</b>");
            //total discount on items
            String sTotalDiscountOnItemsInBillPreviewAfterBillUpdate = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalDiscountOnItemsOnBillPreview);
            double dTotalDiscountOnItemsInBillPreviewAfterBillUpdate = Double.parseDouble(sTotalDiscountOnItemsInBillPreviewAfterBillUpdate);
            //total of all discounts
            String sTotalOfAllDiscountOnItemsInBillPreviewAfterBillUpdate = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalOfAllDiscountOnBillPreview);
            double dTotalOfAllDiscountOnItemsInBillPreviewAfterBillUpdate = Double.parseDouble(sTotalOfAllDiscountOnItemsInBillPreviewAfterBillUpdate);

            m_assert.assertTrue((dTotalDiscountOnItemsInBillPreviewAfterBillUpdate == dSumOfDiscountAmountOnEachPackageInPreview && dTotalOfAllDiscountOnItemsInBillPreviewAfterBillUpdate == dTotalDiscountOnItemsInBillPreviewAfterBillUpdate), "After updating a bill, Total item discount and total of all discount on bill preview is matching with calulated value  = <b>" + dTotalDiscountOnItemsInBillPreviewAfterBillUpdate + "</b>");

            String sPaymentReceivedOnBillPreviewAfterBillUpdate = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_paymentReceivedOnBillPreview);
            double dPaymentReceivedOnBillPreviewAfterBillUpdate = Double.parseDouble(sPaymentReceivedOnBillPreviewAfterBillUpdate);
            m_assert.assertTrue((dPaymentReceivedOnBillPreviewAfterBillUpdate == 0.00), "Payment Received on bill preview after bill update = <b>" + dPaymentReceivedOnBillPreviewAfterBillUpdate + "</b>");
            Cls_Generic_Methods.customWait(4);

            String sPaymentPendingOnBillPreviewAfterBillUpdate = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_paymentPendingOnBillPreview);
            double dPaymentPendingOnBillPreviewAfterBillUpdate = Double.parseDouble(sPaymentPendingOnBillPreviewAfterBillUpdate);
            double dSummationOfPaymentPending = dPaymentPendingOnBillPreview + dNetPriceOfSecondPackage;
            m_assert.assertTrue((dPaymentPendingOnBillPreviewAfterBillUpdate == dSummationOfPaymentPending), "Payment Pending on bill preview after bill update = <b>" + dSummationOfPaymentPending + "</b>");
            Cls_Generic_Methods.customWait(4);

            Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_removeDraftBill);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_confirmRemoveDraftBill);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_closeBill);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
            Cls_Generic_Methods.customWait();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }


    }

    @Test(enabled = true, description = "create a bill and validate the Subpackage item level Discount by adding invoice sets")
    public void createCashBillAndValidateSubPackageItemLevelDiscountByAddingInvoiceSets() {
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills = new Page_Bills(driver);
        oPage_IPD = new Page_IPD(driver);
        boolean bPatientFound = false;
        String paymentMode = "Cash";
        String sPackageName = "AutomationPackageDataDisplaySelf";
        String sDiscountAmountOnFirstSubPackageItem = "4";
        String sDiscountAmountOnSecondSubPackageItem = "3";
        String sGlobalDiscount = "10";
        String sDiscountReason = "Reason" + getRandomUniqueString(6);
        String sInvoiceSetName = "AutomationRecieptTemplateTestData";
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectDepartmentOnApp("IPD");
            myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
            m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today), "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");
            Cls_Generic_Methods.customWait(10);

            bPatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnScheduledTodayTab, concatPatientFullName);
            m_assert.assertTrue(bPatientFound, "Validate that the patient " + concatPatientFullName + " is created in IPD");

            double dFirstSubpackageItemDiscountOnBillPreview = 0;
            double dSecondSubpackageItemDiscountOnBillPreview = 0;
            double dOverDiscountAmountOnPackageInPreview = 0;
            double dGrossPrice = 0;
            double dPaymentReceivedOnBillPreview = 0;
            if (bPatientFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills), "Clicked on bills button");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_cashBill);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 30);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickIPDPlusActionButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Bills.button_newPackage);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_deleteItemPackage.get(0));
                Cls_Generic_Methods.clickElement(oPage_Bills.select_package.get(0));
                boolean myPackageFound = false;
                for (WebElement eItemList : oPage_Bills.list_packageOrServiceNameUnderDescriptionField) {
                    String sPackageNameOnUI = Cls_Generic_Methods.getTextInElement(eItemList);
                    if (sPackageNameOnUI.equals(sPackageName)) {
                        Cls_Generic_Methods.clickElement(eItemList);
                        myPackageFound = true;
                        break;
                    }

                }
                m_assert.assertTrue(myPackageFound, "Selected package name = <b>" + sPackageName + "</b> ");
                Cls_Generic_Methods.customWait(4);
                String sQTY = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_Quantity.get(0), "value");
                String sUnitPrice = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(0), "value");
                String sGrossPrice = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(0), "value");
                dGrossPrice = Double.parseDouble(sGrossPrice);
                String sNetPrice = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dOverAllNetPriceOnPackage = Double.parseDouble(sNetPrice);
                Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(0));
                Cls_Generic_Methods.customWait(4);

                Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_deleteItemLevelDiscount.get(0));
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_subpackageItemLevelDiscountField.get(0))) {
                    m_assert.assertWarn(" First Subpackage item level Discount field is  displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("First Subpackage item level Discount field is not displaying as user deleted overall discount field ");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_subpackageItemLevelDiscountField.get(1))) {
                    m_assert.assertWarn("Second Subpackage item level Discount field is displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Second Subpackage item level Discount field is not displaying as user deleted overall discount field ");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_mainDiscountFieldOnPackage.get(0))) {
                    m_assert.assertWarn("Package level Discount field is  displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Package level Discount field is not displaying as user deleted overall discount field ");
                }
                Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(0));
                Cls_Generic_Methods.customWait(4);
                String sUnitCostOnFirstItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemUnitCost.get(0));
                String sNoOfUnitsCostPerDayOnUI = sUnitCostOnFirstItemUI.split(" ")[0];
                Double dNoOfUnitsCostPerDayOnUI = Double.parseDouble(sNoOfUnitsCostPerDayOnUI);
                // String sUnitCostOfFirstItem = sUnitCostOnFirstItemUI.split("\\(")[0];
                String sNoOfUnitsOrDays = sUnitCostOnFirstItemUI.split("\\(")[1];
                String sNoUnitsPerDayForFirstItem = sNoOfUnitsOrDays.split(" ")[0];
                Double dNoUnitsPerDayForFirstItem = Double.parseDouble(sNoUnitsPerDayForFirstItem);

                String sGrossSubPackageItemPriceForFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(0));
                String sNetPriceItemPriceForFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                Cls_Generic_Methods.customWait(4);
                double dNetPriceOfFirstItemBeforeDiscount = Double.parseDouble(sNetPriceItemPriceForFirstItem);
                double dGrossPriceOfFirstItemBeforeDiscount = Double.parseDouble(sGrossSubPackageItemPriceForFirstItem);
                double dCalculatedGrossPriceAndNetPriceForFirstItem = dNoOfUnitsCostPerDayOnUI * dNoUnitsPerDayForFirstItem;
                if (dCalculatedGrossPriceAndNetPriceForFirstItem == dNetPriceOfFirstItemBeforeDiscount && dCalculatedGrossPriceAndNetPriceForFirstItem == dGrossPriceOfFirstItemBeforeDiscount) {
                    m_assert.assertTrue("Calculated gross price for first package level item is equal to net price and gross price on UI, Before applying a discount = <b>  " + dNetPriceOfFirstItemBeforeDiscount + "</b>");
                } else {
                    m_assert.assertWarn("Calculated gross price first package level item is not equal to net price on UI and gross price on UI, Before applying a discount");
                }

                String sUnitCostOnSecondItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemUnitCost.get(1));
                // String sUnitCostOfSecondItem = sUnitCostOnSecondItemUI.split("\\(")[0];
                String sNoOfUnitsOrDaysOnUIForSecondItem = sUnitCostOnSecondItemUI.split(" ")[0];
                Double dNoOfUnitsOrDaysOnUIForSecondItem = Double.parseDouble(sNoOfUnitsOrDaysOnUIForSecondItem);
                String sNoOfUnitsOrDaysForSecondItem = sUnitCostOnSecondItemUI.split("\\(")[1];
                String sNoUnitsPerDayForSecondItems = sNoOfUnitsOrDaysForSecondItem.split(" ")[0];
                Double dNoUnitsPerDayForSecondItems = Double.parseDouble(sNoUnitsPerDayForSecondItems);
                String sGrossSubPackageItemPriceForSecondItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(1));
                String sNetPriceItemPriceForSecondItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dGrossSubPackageItemPriceForSecondItem = Double.parseDouble(sGrossSubPackageItemPriceForSecondItem);
                double dNetPriceItemPriceForSecondItem = Double.parseDouble(sNetPriceItemPriceForSecondItem);
                double dCalculatedGrossPriceAndNetPriceForSecondItem = dNoOfUnitsOrDaysOnUIForSecondItem * dNoUnitsPerDayForSecondItems;
                if (dCalculatedGrossPriceAndNetPriceForSecondItem == dNetPriceItemPriceForSecondItem && dCalculatedGrossPriceAndNetPriceForSecondItem == dGrossSubPackageItemPriceForSecondItem) {
                    m_assert.assertTrue("Calculated gross price for Second package level item is equal to net price and gross price on UI, Before applying a discount<b> " + dNetPriceItemPriceForSecondItem + "</b>");
                } else {
                    m_assert.assertWarn("Calculated gross price second package level item is not equal to net price on UI and gross price on UI, Before applying a discount");
                }
                double dSumOfFistAndSecondSubpackageItemsNetPrice = dNetPriceItemPriceForSecondItem + dNetPriceOfFirstItemBeforeDiscount;
                if (dSumOfFistAndSecondSubpackageItemsNetPrice == dOverAllNetPriceOnPackage) {
                    m_assert.assertTrue("summation of first and second subpackage level items net price is equal to overall subpackage net price, Before applying a discount<b>  " + dOverAllNetPriceOnPackage + "</b>");
                } else {
                    m_assert.assertWarn("summation of first and second subpackage level items net price is equal to overall subpackage net price, Before applying a discount");
                }
                // Apply discount on each subPackage item
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(0));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(0), sDiscountAmountOnFirstSubPackageItem), "Applied Sub package item level discount for first item = <b> " + sDiscountAmountOnFirstSubPackageItem + "</b>");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(1));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.list_subpackageItemLevelDiscountTypes.get(1), "%"), "Discount type for second subpackage level item =  <b>%</b>");
                Cls_Generic_Methods.customWait(4);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(1), sDiscountAmountOnSecondSubPackageItem), "Applied Sub package item level discount for second item = <b> " + sDiscountAmountOnSecondSubPackageItem + "</b>");
                double dDiscountAmountOnSecondSubPackageItem = Double.parseDouble(sDiscountAmountOnSecondSubPackageItem);
                double dDiscountPercentageForSecondItem = dDiscountAmountOnSecondSubPackageItem / 100;
                double dDiscountAmountAfterApplyingDiscountInPercentageForSecondItem = dNetPriceItemPriceForSecondItem * dDiscountPercentageForSecondItem;
                double dRoundingOffTheDiscountForSecondItem = roundOffFunctionUsingDouble(dDiscountAmountAfterApplyingDiscountInPercentageForSecondItem);
                //Multiplying with minus as we are showing discount value in negative
                double dRoundingOffTheDiscountOnSecondItem = dRoundingOffTheDiscountForSecondItem * (-1);
                String sFirstSubpackageItemDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dFirstSubpackageItemDiscountAmount = Double.parseDouble(sFirstSubpackageItemDiscountAmount);
                double dNetPriceOfFirstItemAfterDiscount = dNetPriceOfFirstItemBeforeDiscount + dFirstSubpackageItemDiscountAmount;
                String sNetAmountAfterDiscountForFirstItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                double dNetAmountAfterDiscountForFirstItemOnUI = Double.parseDouble(sNetAmountAfterDiscountForFirstItemOnUI);
                if (dNetPriceOfFirstItemAfterDiscount == dNetAmountAfterDiscountForFirstItemOnUI) {
                    m_assert.assertTrue("Net price amount for first subpackage item after applying Discount =  <b>" + dNetAmountAfterDiscountForFirstItemOnUI + "</b>");
                } else {
                    m_assert.assertWarn("Calculated Net price amount for first subpackage item after applying Discount is not matching with UI net price");
                }
                String sSecondSubpackageItemDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(1));
                double dSecondSubpackageItemDiscountAmount = Double.parseDouble(sSecondSubpackageItemDiscountAmount);
                double dNetPriceOfSecondItemAfterDiscount = dNetPriceItemPriceForSecondItem + dSecondSubpackageItemDiscountAmount;
                String sNetAmountAfterDiscountForSecondItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dNetAmountAfterDiscountForSecondItemOnUI = Double.parseDouble(sNetAmountAfterDiscountForSecondItemOnUI);
                if (dRoundingOffTheDiscountOnSecondItem == dSecondSubpackageItemDiscountAmount) {
                    m_assert.assertTrue("Discount amount for second subpackage item on UI is matched with calculated Discount amount= <b>" + dSecondSubpackageItemDiscountAmount + "</b>");
                } else {
                    m_assert.assertWarn("Discount amount for second subpackage item on UI is not matching with calculated Discount amount");
                }
                if (dNetPriceOfSecondItemAfterDiscount == dNetAmountAfterDiscountForSecondItemOnUI) {
                    m_assert.assertTrue("Net price amount for second subpackage item after applying Discount =  <b>" + dNetAmountAfterDiscountForFirstItemOnUI + "</b>");
                } else {
                    m_assert.assertWarn("Calculated Net price  for second subpackage item after applying Discount is not matching with UI net price");
                }
                // summation subpackage item level discount
                double dTotalDiscountAmountOnOverAllSubpackage = dFirstSubpackageItemDiscountAmount + dSecondSubpackageItemDiscountAmount;
                String sOverAllDiscountOnPackage = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_overallDiscountAmountOnPackage);
                String sOverAllDiscountPackage = sOverAllDiscountOnPackage.replaceAll("\\s", "");
                double dOverAllDiscountOnPackage = Double.parseDouble(sOverAllDiscountPackage);
                if (dOverAllDiscountOnPackage == dTotalDiscountAmountOnOverAllSubpackage) {
                    m_assert.assertTrue("Subpackage item level discounts are matching with overall subpackage discount field =  <b>" + dTotalDiscountAmountOnOverAllSubpackage + "</b>");
                } else {
                    m_assert.assertWarn("Subpackage item level discounts are not matching with overall subpackage discount field");
                }
                //Over all package net price after discount
                double dNetAmountOnOverAllPackageAfterApplyingDiscount = dOverAllNetPriceOnPackage + dTotalDiscountAmountOnOverAllSubpackage;
                String sNetAmtAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetAmtAfterDiscount = Double.parseDouble(sNetAmtAfterDiscount);
                m_assert.assertTrue((dNetAmountOnOverAllPackageAfterApplyingDiscount == dNetAmtAfterDiscount), "Calculated Net Amount after discount matching with Net amount on UI <b>" + dNetAmtAfterDiscount + "</b>");

                // delete all subpackage item wise discounts
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_deleteSubPackageItemsDiscount.get(1)), "clicked on delete subpackage item level discount button");
                String sSecondSubPackageNetAmountAfterDeletingItemLevelDiscount = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dSecondSubPackageNetAmountAfterDeletingItemLevelDiscount = Double.parseDouble(sSecondSubPackageNetAmountAfterDeletingItemLevelDiscount);
                m_assert.assertTrue((dSecondSubPackageNetAmountAfterDeletingItemLevelDiscount == dCalculatedGrossPriceAndNetPriceForSecondItem), "Upon deleting a second subpackage level item discount calculated Net Amount is matching with Net amount on UI <b>" + dSecondSubPackageNetAmountAfterDeletingItemLevelDiscount + "</b>");

                //net price of first sub package level item after discount
                String sFirstSubpackageItemDiscountAfterRemovingSecondItemsDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dFirstSubpackageItemAfterRemovingSecondItemsDiscountAmount = Double.parseDouble(sFirstSubpackageItemDiscountAfterRemovingSecondItemsDiscountAmount);
                double dNetPriceOfFirstItemAfterRemovingSecondItemsDiscount = dNetPriceOfFirstItemBeforeDiscount + dFirstSubpackageItemAfterRemovingSecondItemsDiscountAmount;
                String sNetAmountAfterDiscountOnFirstItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                double dNetAmountAfterDiscountOnFirstItemOnUI = Double.parseDouble(sNetAmountAfterDiscountOnFirstItemOnUI);
                m_assert.assertTrue((dNetAmountAfterDiscountOnFirstItemOnUI == dNetPriceOfFirstItemAfterRemovingSecondItemsDiscount), "Upon deleting a second subpackage level discount calculated Net Amount of second item is matching with Net amount on UI of second item <b>" + dNetPriceOfFirstItemAfterRemovingSecondItemsDiscount + "</b>");
                //Over all package net price after deleting second items discount
                double dCalculatedNetAmountAfterRemovingSecondItemsDiscount = dNetAmountOnOverAllPackageAfterApplyingDiscount + dRoundingOffTheDiscountForSecondItem;
                String sNetAmtAfterRemovingSecondItemDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetAmtAfterRemovingSecondItemDiscount = Double.parseDouble(sNetAmtAfterRemovingSecondItemDiscount);
                m_assert.assertTrue((dCalculatedNetAmountAfterRemovingSecondItemsDiscount == dNetAmtAfterRemovingSecondItemDiscount), "Calculated Net Amount after removing second items discount is matching with Net amount on UI <b>" + dCalculatedNetAmountAfterRemovingSecondItemsDiscount + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Bills.button_addSubPackageItemsDiscount.get(1));
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmount.get(0), sGlobalDiscount), "Entered amount in Global discount field = <b>" + sGlobalDiscount + "</b>");
                // m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);
                double dGlobalDiscount = Double.parseDouble(sGlobalDiscount);
                double dGlobalDiscountPercentage = dGlobalDiscount / dOverAllNetPriceOnPackage;
                double dDiscountOnFirstItemAfterApplyingGlobalDiscount = dGlobalDiscountPercentage * dGrossPriceOfFirstItemBeforeDiscount;
                double dDiscountOnSecondItemAfterApplyingGlobalDiscount = dGlobalDiscountPercentage * dGrossSubPackageItemPriceForSecondItem;
                double dSummationOfSubpackageItemWiseDiscount = dDiscountOnFirstItemAfterApplyingGlobalDiscount + dDiscountOnSecondItemAfterApplyingGlobalDiscount;
                double dNetAmountOfFirstSubpackageItemAfterGlobalDiscount = dGrossPriceOfFirstItemBeforeDiscount - dDiscountOnFirstItemAfterApplyingGlobalDiscount;
                double dNetAmountOfSecondSubpackageItemAfterGlobalDiscount = dGrossSubPackageItemPriceForSecondItem - dDiscountOnSecondItemAfterApplyingGlobalDiscount;
                m_assert.assertTrue((dSummationOfSubpackageItemWiseDiscount == dGlobalDiscount), "Subpackage item wise discounts sum is equal to Global discount amount <b>" + dGlobalDiscount + "</b>");
                Cls_Generic_Methods.customWait(3);
                //Apply GD in percentage
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_globalDiscountType, "%"), "Discount type for GD =  <b>%</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmount.get(0), sGlobalDiscount), "Entered amount in Global discount field = <b>" + sGlobalDiscount + "</b>");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_discountReason.get(0), sDiscountReason);
                double dGlobalDiscountInPercentage = dGlobalDiscount / 100;
                double dDiscountOnFirstItemAfterApplyingGlobalDiscountInPercentage = dGlobalDiscountInPercentage * dGrossPriceOfFirstItemBeforeDiscount;
                double dDiscountOnSecondItemAfterApplyingGlobalDiscountInPercentage = dGlobalDiscountInPercentage * dGrossSubPackageItemPriceForSecondItem;
                String sDiscount1 = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dDiscount1 = Double.parseDouble(sDiscount1);
                double dFirstSubpackageDiscountOnUI = dDiscount1 * (-1);
                String sDiscount2 = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(1));
                double dDiscount2 = Double.parseDouble(sDiscount2);
                double dSecondSubpackageDiscountOnUI = dDiscount2 * (-1);
                m_assert.assertTrue((dFirstSubpackageDiscountOnUI == dDiscountOnFirstItemAfterApplyingGlobalDiscountInPercentage && dSecondSubpackageDiscountOnUI == dDiscountOnSecondItemAfterApplyingGlobalDiscountInPercentage), "Discount amount is matching with calculated subpackage item level discounts for subpackage 1st item discount: <b>" + dFirstSubpackageDiscountOnUI + "</b> 2nd item discount: <b>" + dSecondSubpackageDiscountOnUI);
                double dSummationOfSubpackageItemWiseDiscountInPercentage = dFirstSubpackageDiscountOnUI + dSecondSubpackageDiscountOnUI;
                double dNetAmountOfFirstSubpackageItemAfterGlobalDiscountInPercentage = dGrossPriceOfFirstItemBeforeDiscount - dFirstSubpackageDiscountOnUI;
                double dNetAmountOfSecondSubpackageItemAfterGlobalDiscountInPercentage = dGrossSubPackageItemPriceForSecondItem - dSecondSubpackageDiscountOnUI;
                String sNetAmountForFirstSubItem = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetAmountForFirstSubItem = Double.parseDouble(sNetAmountForFirstSubItem);
                double dSumNetAmountAfterGD = dNetAmountOfFirstSubpackageItemAfterGlobalDiscountInPercentage + dNetAmountOfSecondSubpackageItemAfterGlobalDiscountInPercentage;
                m_assert.assertTrue((dNetAmountForFirstSubItem == dSumNetAmountAfterGD), "Net amount on UI equals calculated net amount after applying global discount <b>" + dSumNetAmountAfterGD + "</b>");
                Cls_Generic_Methods.customWait(3);
                String sNetAmountOnBillAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_netBillTotal, "value");
                double dNetAmountOnBillAfterApplyingGD = Double.parseDouble(sNetAmountOnBillAfterApplyingGD);
                String sGrossBillTotalOnBillAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_grossBillTotal, "value");
                double dGrossBillTotalOnBillAfterApplyingGD = Double.parseDouble(sGrossBillTotalOnBillAfterApplyingGD);
                String sDiscountOnItemsAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnItem, "value");
                double dDiscountOnItemsAfterApplyingGD = Double.parseDouble(sDiscountOnItemsAfterApplyingGD);
                m_assert.assertTrue((dNetAmountOnBillAfterApplyingGD == dSumNetAmountAfterGD && dGrossBillTotalOnBillAfterApplyingGD == dGrossPrice && dDiscountOnItemsAfterApplyingGD == dSummationOfSubpackageItemWiseDiscountInPercentage), "Net amount on bill = <b>" + dNetAmountOnBillAfterApplyingGD + "</b> , Gross amount on bill =  <b>" + dGrossBillTotalOnBillAfterApplyingGD + "</b> , Discount on item = <b> " + dDiscountOnItemsAfterApplyingGD + "</b>");
                Cls_Generic_Methods.customWait(4);
                String sTotalDiscountOnItemsAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnBill, "value");
                double dTotalDiscountOnItemsAfterApplyingGD = Double.parseDouble(sTotalDiscountOnItemsAfterApplyingGD);
                String sTotalOfAllDiscountOnItemsAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalOfAllDiscounts, "value");
                double dTotalOfAllDiscountOnItemsAfterApplyingGD = Double.parseDouble(sTotalOfAllDiscountOnItemsAfterApplyingGD);
                m_assert.assertTrue((dTotalDiscountOnItemsAfterApplyingGD == dTotalOfAllDiscountOnItemsAfterApplyingGD), "Total discount and total of all discount fields, values matching on UI = <b>" + dTotalOfAllDiscountOnItemsAfterApplyingGD + "</b>");


                // select Invoice sets
                Cls_Generic_Methods.selectElementByVisibleText(oPage_Bills.option_invoiceSets, sInvoiceSetName);
                Cls_Generic_Methods.customWait(4);
                String sQTYForSecondPackage = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_Quantity.get(1), "value");
                String sUnitPriceForSecondPackage = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(1), "value");
                String sGrossPriceForSecondPackage = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(1), "value");
                dGrossPrice = Double.parseDouble(sGrossPriceForSecondPackage);
                String sNetPriceForSecondPackage = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value");
                double dOverAllNetPriceOnPackageForSecondPackage = Double.parseDouble(sNetPriceForSecondPackage);
                Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(1));
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_deleteItemLevelDiscount.get(1));
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_subpackageItemLevelDiscountField.get(2))) {
                    m_assert.assertWarn("Second package: First Subpackage item level Discount field is  displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Second package: First Subpackage item level Discount field is not displaying as user deleted overall discount field ");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_subpackageItemLevelDiscountField.get(3))) {
                    m_assert.assertWarn("Second package: Second Subpackage item level Discount field is displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Second package: Second Subpackage item level Discount field is not displaying as user deleted overall discount field ");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_mainDiscountFieldOnPackage.get(1))) {
                    m_assert.assertWarn("Second package: Package level Discount field is  displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Second package: Package item level Discount field is not displaying as user deleted overall discount field ");
                }
                Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(1));
                Cls_Generic_Methods.customWait(4);
                String sUnitCostOnFirstSubpackageItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemUnitCost.get(2));
                String sNoOfUnitsCostPerDayOnUIForFirstSubpackage = sUnitCostOnFirstSubpackageItemUI.split(" ")[0];
                Double dNoOfUnitsCostPerDayOnFirstSubpackageOnUI = Double.parseDouble(sNoOfUnitsCostPerDayOnUIForFirstSubpackage);
                // String sUnitCostOfFirstItem = sUnitCostOnFirstItemUI.split("\\(")[0];
                String sNoOfUnitsOrDaysForFirstSubpacakge = sUnitCostOnFirstSubpackageItemUI.split("\\(")[1];
                String sNoUnitsPerDayForFirstSubPackageItem = sNoOfUnitsOrDaysForFirstSubpacakge.split(" ")[0];
                Double dNoUnitsPerDayForFirstSubpackageItem = Double.parseDouble(sNoUnitsPerDayForFirstSubPackageItem);

                String sGrossSubPackageItemPriceONFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(2));
                String sNetPriceItemPriceOnFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(2));
                Cls_Generic_Methods.customWait(4);
                double dNetPriceOfFirstItemBeforeApplyingDiscount = Double.parseDouble(sNetPriceItemPriceOnFirstItem);
                double dGrossPriceOfFirstItemBeforeApplyingDiscount = Double.parseDouble(sGrossSubPackageItemPriceONFirstItem);
                double dCalculatedGrossPriceAndNetPriceOnFirstItem = dNoOfUnitsCostPerDayOnFirstSubpackageOnUI * dNoUnitsPerDayForFirstSubpackageItem;
                if (dCalculatedGrossPriceAndNetPriceOnFirstItem == dNetPriceOfFirstItemBeforeApplyingDiscount && dCalculatedGrossPriceAndNetPriceOnFirstItem == dGrossPriceOfFirstItemBeforeApplyingDiscount) {
                    m_assert.assertTrue("Calculated gross price for first package level item is equal to net price and gross price on UI, Before applying a discount = <b>  " + dNetPriceOfFirstItemBeforeApplyingDiscount + "</b>");
                } else {
                    m_assert.assertWarn("Calculated gross price first package level item is not equal to net price on UI and gross price on UI, Before applying a discount");
                }

                String sUnitCostOnSecondSubPackageItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemUnitCost.get(3));
                // String sUnitCostOfSecondItem = sUnitCostOnSecondItemUI.split("\\(")[0];
                String sNoOfUnitsOrDaysOnUIForSecondSubPackageItem = sUnitCostOnSecondSubPackageItemUI.split(" ")[0];
                Double dNoOfUnitsOrDaysOnUIForSecondSubPackageItem = Double.parseDouble(sNoOfUnitsOrDaysOnUIForSecondSubPackageItem);
                String sNoOfUnitsOrDaysForSecondSubPackageItem = sUnitCostOnSecondSubPackageItemUI.split("\\(")[1];
                String sNoUnitsPerDayForSecondSubPackageItems = sNoOfUnitsOrDaysForSecondSubPackageItem.split(" ")[0];
                Double dNoUnitsPerDayForSecondSubPackageItems = Double.parseDouble(sNoUnitsPerDayForSecondSubPackageItems);
                String sGrossSubPackageItemPriceForSecondSubPackageItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(3));
                String sNetPriceItemPriceForSecondSubPackageItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(3));
                double dGrossSubPackageItemPriceForSecondSubPackageItem = Double.parseDouble(sGrossSubPackageItemPriceForSecondSubPackageItem);
                double dNetPriceItemPriceForSecondSubPackageItem = Double.parseDouble(sNetPriceItemPriceForSecondSubPackageItem);
                double dCalculatedGrossPriceAndNetPriceForSecondSubPackageItem = dNoOfUnitsOrDaysOnUIForSecondSubPackageItem * dNoUnitsPerDayForSecondSubPackageItems;
                if (dCalculatedGrossPriceAndNetPriceForSecondSubPackageItem == dNetPriceItemPriceForSecondSubPackageItem && dCalculatedGrossPriceAndNetPriceForSecondSubPackageItem == dGrossSubPackageItemPriceForSecondSubPackageItem) {
                    m_assert.assertTrue("Calculated gross price for Second package level item is equal to net price and gross price on UI, Before applying a discount<b> " + dNetPriceItemPriceForSecondSubPackageItem + "</b>");
                } else {
                    m_assert.assertWarn("Calculated gross price second package level item is not equal to net price on UI and gross price on UI, Before applying a discount");
                }
                double dSumOfFistAndSecondSubpackageItemNetPrice = dNetPriceItemPriceForSecondSubPackageItem + dNetPriceOfFirstItemBeforeApplyingDiscount;
                if (dSumOfFistAndSecondSubpackageItemNetPrice == dOverAllNetPriceOnPackageForSecondPackage) {
                    m_assert.assertTrue("summation of first and second subpackage level items net price is equal to overall subpackage net price, Before applying a discount<b>  " + dOverAllNetPriceOnPackageForSecondPackage + "</b>");
                } else {
                    m_assert.assertWarn("summation of first and second subpackage level items net price is equal to overall subpackage net price, Before applying a discount");
                }
                // Apply discount on each subPackage item
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(2));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(2), sDiscountAmountOnFirstSubPackageItem), "Applied Sub package item level discount for first item = <b> " + sDiscountAmountOnFirstSubPackageItem + "</b>");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(3));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.list_subpackageItemLevelDiscountTypes.get(3), "%"), "Discount type for second subpackage level item =  <b>%</b>");
                Cls_Generic_Methods.customWait(4);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(3), sDiscountAmountOnSecondSubPackageItem), "Applied Sub package item level discount for second item = <b> " + sDiscountAmountOnSecondSubPackageItem + "</b>");
                double dDiscountAmountForSecondSubPackageItem = Double.parseDouble(sDiscountAmountOnSecondSubPackageItem);
                double dDiscountPercentageONSecondItem = dDiscountAmountForSecondSubPackageItem / 100;
                double dDiscountAmountAfterApplyingDiscountonPercentageForSecondItem = dNetPriceItemPriceForSecondSubPackageItem * dDiscountPercentageONSecondItem;
                double dRoundingOffTheDiscountAmountForSecondItem = roundOffFunctionUsingDouble(dDiscountAmountAfterApplyingDiscountonPercentageForSecondItem);
                //Multiplying with minus as we are showing discount value in negative
                double dRoundOffTheDiscountOnSecondItem = dRoundingOffTheDiscountAmountForSecondItem * (-1);
                String sFirstSubpackageItemsDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(2));
                double dFirstSubpackageItemsDiscountAmount = Double.parseDouble(sFirstSubpackageItemsDiscountAmount);
                double dNetPriceOfTheFirstItemAfterDiscount = dNetPriceOfFirstItemBeforeApplyingDiscount + dFirstSubpackageItemsDiscountAmount;
                String sNetAmountAfterApplyingDiscountForFirstItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(2));
                double dNetAmountAfterApplyingDiscountForFirstItemOnUI = Double.parseDouble(sNetAmountAfterApplyingDiscountForFirstItemOnUI);
                if (dNetPriceOfTheFirstItemAfterDiscount == dNetAmountAfterApplyingDiscountForFirstItemOnUI) {
                    m_assert.assertTrue("Net price amount for first subpackage item after applying Discount =  <b>" + dNetAmountAfterApplyingDiscountForFirstItemOnUI + "</b>");
                } else {
                    m_assert.assertWarn("Calculated Net price amount for first subpackage item after applying Discount is not matching with UI net price");
                }
                String sSecondSubpackageItemsDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(3));
                double dSecondSubpackageItemsDiscountAmount = Double.parseDouble(sSecondSubpackageItemsDiscountAmount);
                double dNetPriceOfSecondItemsAfterDiscount = dNetPriceItemPriceForSecondSubPackageItem + dSecondSubpackageItemsDiscountAmount;
                String sNetAmountAfterDiscountOnSecondItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(3));
                double dNetAmountAfterDiscountOnSecondItemOnUI = Double.parseDouble(sNetAmountAfterDiscountOnSecondItemOnUI);
                if (dRoundOffTheDiscountOnSecondItem == dSecondSubpackageItemsDiscountAmount) {
                    m_assert.assertTrue("Discount amount for second subpackage item on UI is matched with calculated Discount amount= <b>" + dSecondSubpackageItemsDiscountAmount + "</b>");
                } else {
                    m_assert.assertWarn("Discount amount for second subpackage item on UI is not matching with calculated Discount amount");
                }
                if (dNetPriceOfSecondItemsAfterDiscount == dNetAmountAfterDiscountOnSecondItemOnUI) {
                    m_assert.assertTrue("Net price amount for second subpackage item after applying Discount =  <b>" + dNetAmountAfterDiscountOnSecondItemOnUI + "</b>");
                } else {
                    m_assert.assertWarn("Calculated Net price  for second subpackage item after applying Discount is not matching with UI net price");
                }
                // summation subpackage item level discount
                double dTotalDiscountAmountForAllSubpackage = dFirstSubpackageItemsDiscountAmount + dSecondSubpackageItemsDiscountAmount;
                String sOverAllDiscountForSecondPackage = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_overallDiscountOnPackage.get(1));
                String sOverAllDiscountSecondPackage = sOverAllDiscountForSecondPackage.replaceAll("\\s", "");
                double dOverAllDiscountOnSecondPackage = Double.parseDouble(sOverAllDiscountSecondPackage);
                if (dOverAllDiscountOnSecondPackage == dTotalDiscountAmountForAllSubpackage) {
                    m_assert.assertTrue("Subpackage item level discounts are matching with overall subpackage discount field =  <b>" + dTotalDiscountAmountForAllSubpackage + "</b>");
                } else {
                    m_assert.assertWarn("Subpackage item level discounts are not matching with overall subpackage discount field");
                }
                //Over all package net price after discount
                double dOverAllNetAmountOnSecondPackageAfterApplyingDiscount = dOverAllNetPriceOnPackageForSecondPackage + dTotalDiscountAmountForAllSubpackage;
                String sNetAmtAfterApplyingDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value");
                double dNetAmtAfterApplyingDiscount = Double.parseDouble(sNetAmtAfterApplyingDiscount);
                m_assert.assertTrue((dOverAllNetAmountOnSecondPackageAfterApplyingDiscount == dNetAmtAfterApplyingDiscount), "Calculated Net Amount after discount matching with Net amount on UI <b>" + dNetAmtAfterApplyingDiscount + "</b>");

                // delete all subpackage item wise discounts
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_deleteSubPackageItemsDiscount.get(3)), "clicked on delete subpackage item level discount button");
                String sSecondSubPackageNetAmountAfterDeletingItemLevelDisc = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(3));
                double dSecondSubPackageNetAmountAfterDeletingItemLevelDisc = Double.parseDouble(sSecondSubPackageNetAmountAfterDeletingItemLevelDisc);
                m_assert.assertTrue((dSecondSubPackageNetAmountAfterDeletingItemLevelDisc == dCalculatedGrossPriceAndNetPriceForSecondSubPackageItem), "Upon deleting a second subpackage level item discount calculated Net Amount is matching with Net amount on UI <b>" + dSecondSubPackageNetAmountAfterDeletingItemLevelDisc + "</b>");

                //net price of first sub package level item after discount
                String sFirstSubpackageItemDiscountAfterRemovingSecondItemsDiscount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(2));
                double dFirstSubpackageItemAfterRemovingSecondItemsDiscount = Double.parseDouble(sFirstSubpackageItemDiscountAfterRemovingSecondItemsDiscount);
                double dNetPriceOfFirstItemAfterRemovingSecondItemDiscount = dNetPriceOfFirstItemBeforeApplyingDiscount + dFirstSubpackageItemAfterRemovingSecondItemsDiscount;
                String sNetAmountAfterDiscountOnFirstItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(2));
                double dNetAmountAfterDiscountOnFirstItemUI = Double.parseDouble(sNetAmountAfterDiscountOnFirstItemUI);
                m_assert.assertTrue((dNetAmountAfterDiscountOnFirstItemUI == dNetPriceOfFirstItemAfterRemovingSecondItemDiscount), "Upon deleting a second subpackage level discount calculated Net Amount of second item is matching with Net amount on UI of second item <b>" + dNetPriceOfFirstItemAfterRemovingSecondItemDiscount + "</b>");
                //Over all package net price after deleting second items discount
                double dCalculatedNetAmountAfterRemovingSecondItemDiscount = dOverAllNetAmountOnSecondPackageAfterApplyingDiscount + dRoundingOffTheDiscountAmountForSecondItem;
                String sNetAmtAfterRemovingSecondItemsDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value");
                double dNetAmtAfterRemovingSecondItemsDiscount = Double.parseDouble(sNetAmtAfterRemovingSecondItemsDiscount);
                m_assert.assertTrue((dCalculatedNetAmountAfterRemovingSecondItemDiscount == dNetAmtAfterRemovingSecondItemsDiscount), "Calculated Net Amount after removing second items discount is matching with Net amount on UI <b>" + dCalculatedNetAmountAfterRemovingSecondItemDiscount + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Bills.button_addSubPackageItemsDiscount.get(3));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmount.get(1), sGlobalDiscount), "Entered amount in Global discount field = <b>" + sGlobalDiscount + "</b>");
                // m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscountForSecondPackage), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);
                double dGlobalDiscountForSecondPackage = Double.parseDouble(sGlobalDiscount);
                double dGlobalDiscountPercentageForSecondPackage = dGlobalDiscountForSecondPackage / dOverAllNetPriceOnPackageForSecondPackage;
                double dDiscountOnFirstItemAfterGlobalDiscount = dGlobalDiscountPercentageForSecondPackage * dGrossPriceOfFirstItemBeforeApplyingDiscount;
                double dRoundingOffTheDiscountOnFirstItem = roundOffFunctionUsingDouble(dDiscountOnFirstItemAfterGlobalDiscount);
                double dDiscountOnSecondItemAfterGlobalDiscount = dGlobalDiscountPercentageForSecondPackage * dGrossSubPackageItemPriceForSecondSubPackageItem;
                double dRoundingOffTheDiscountOnForSecondItem = roundOffFunctionUsingDouble(dDiscountOnSecondItemAfterGlobalDiscount);
                double dSummationOfSubpackageItemDiscount = dDiscountOnFirstItemAfterGlobalDiscount + dDiscountOnSecondItemAfterGlobalDiscount;
                double dNetAmountOfFirstSubpackageItemAfterApplyingGlobalDiscount = dGrossPriceOfFirstItemBeforeApplyingDiscount - dDiscountOnFirstItemAfterGlobalDiscount;
                double dNetAmountOfSecondSubpackageItemAfterApplyingGlobalDiscount = dGrossSubPackageItemPriceForSecondSubPackageItem - dDiscountOnSecondItemAfterGlobalDiscount;
                m_assert.assertTrue((dSummationOfSubpackageItemDiscount == dGlobalDiscountForSecondPackage), "Subpackage item wise discounts sum is equal to Global discount amount <b>" + dGlobalDiscountForSecondPackage + "</b>");
                Cls_Generic_Methods.customWait(3);
                double dSumOfFirstAndSecondSubPackageItemNetAmount = dNetAmountOfFirstSubpackageItemAfterApplyingGlobalDiscount + dNetAmountOfSecondSubpackageItemAfterApplyingGlobalDiscount;
                double dRoundingOffTheNetAmountForSecondItem = roundOffFunctionUsingDouble(dSumOfFirstAndSecondSubPackageItemNetAmount);
                double dOverAllBillNetPrice = dNetAmountOnBillAfterApplyingGD + dRoundingOffTheNetAmountForSecondItem;
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_discountReason.get(1), sDiscountReason);
                Cls_Generic_Methods.scrollToElementByJS(oPage_Bills.select_modeOfPayment);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_modeOfPayment, paymentMode), paymentMode + " option is selected for Mode Of Payment");
                String sAmountReceivedOnUI = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_amountReceivedField.get(0), "value");
                double dAmountReceivedOnUI = Double.parseDouble(sAmountReceivedOnUI);
                m_assert.assertTrue((dAmountReceivedOnUI == dOverAllBillNetPrice), "Net amount on bill equals to amount received against bill is matching = <b>" + dAmountReceivedOnUI + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills), "<b>Cash bill is created </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.customWait(4);
                //bill preview validation

                String sFirstSubpackageItemDiscountOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(0));
                dFirstSubpackageItemDiscountOnBillPreview = Double.parseDouble(sFirstSubpackageItemDiscountOnBillPreview);

                String sSecondSubpackageItemDiscountOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(1));
                dSecondSubpackageItemDiscountOnBillPreview = Double.parseDouble(sSecondSubpackageItemDiscountOnBillPreview);
                m_assert.assertTrue((dFirstSubpackageItemDiscountOnBillPreview == dDiscountOnFirstItemAfterApplyingGlobalDiscountInPercentage && dSecondSubpackageItemDiscountOnBillPreview == dDiscountOnSecondItemAfterApplyingGlobalDiscountInPercentage), "Discount amount is matching with calculated subpackage item level discounts 1st item1 on preview : <b>" + dFirstSubpackageItemDiscountOnBillPreview + "</b> 2nd item discount on bill preview : <b>" + dSecondSubpackageItemDiscountOnBillPreview);
                String sOverDiscountAmountOnPackageOnPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(0));
                String sOverDiscountAmountOnPackageInPreview = sOverDiscountAmountOnPackageOnPreview.replaceAll("\\s", "");
                double dOverDiscountAmountOnPackagePreview = Double.parseDouble(sOverDiscountAmountOnPackageInPreview);
                dOverDiscountAmountOnPackageInPreview = dOverDiscountAmountOnPackagePreview * (-1);
                m_assert.assertTrue((dTotalDiscountOnItemsAfterApplyingGD == dOverDiscountAmountOnPackageInPreview), "OverAll discount amount on package on bill preview = <b>" + dTotalDiscountOnItemsAfterApplyingGD + "</b>");


                String sFirstSubpackageItemDiscountOnBillPreviewForSecondPackage = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(2));
                double dFirstSubpackageItemDiscountOnBillPreviewForSecondPackage = Double.parseDouble(sFirstSubpackageItemDiscountOnBillPreviewForSecondPackage);

                String sSecondSubpackageItemDiscountOnBillPreviewForSecondPackage = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(3));
                double dSecondSubpackageItemDiscountOnBillPreviewForSecondPackage = Double.parseDouble(sSecondSubpackageItemDiscountOnBillPreviewForSecondPackage);
                m_assert.assertTrue((dFirstSubpackageItemDiscountOnBillPreviewForSecondPackage == dRoundingOffTheDiscountOnFirstItem && dRoundingOffTheDiscountOnForSecondItem == dSecondSubpackageItemDiscountOnBillPreviewForSecondPackage), "Discount amount is matching with calculated subpackage item level discounts 1st item1 on preview : <b>" + dRoundingOffTheDiscountOnFirstItem + "</b> 2nd item discount on bill preview : <b>" + dSecondSubpackageItemDiscountOnBillPreviewForSecondPackage);
                String sOverDiscountAmountOnSecondPackageOnPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(1));
                String sOverDiscountAmountOnSecondPackageInPreview = sOverDiscountAmountOnSecondPackageOnPreview.replaceAll("\\s", "");
                double dOverDiscountAmountOnSecondPackagePreview = Double.parseDouble(sOverDiscountAmountOnSecondPackageInPreview);
                double dOverAllDiscountOnSecondItem = dOverDiscountAmountOnSecondPackagePreview * (-1);
                m_assert.assertTrue((dSummationOfSubpackageItemDiscount == dOverAllDiscountOnSecondItem), "OverAll discount amount on package on bill preview = <b>" + dSummationOfSubpackageItemDiscount + "</b>");

                String sGrossBillTotalOnPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_grossBillTotalOnBillPreview);
                double dGrossBillTotalOnPreview = Double.parseDouble(sGrossBillTotalOnPreview);
                double dGrossOnBill = dGrossBillTotalOnBillAfterApplyingGD + dGrossPrice;
                m_assert.assertTrue((dGrossOnBill == dGrossBillTotalOnPreview), "Gross bill total on package on bill preview = <b>" + dGrossOnBill + "</b>");

                String sTotalDiscountOnItemsInBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalDiscountOnItemsOnBillPreview);
                double dTotalDiscountOnItemsInBillPreview = Double.parseDouble(sTotalDiscountOnItemsInBillPreview);

                String sTotalOfAllDiscountOnItemsInBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalOfAllDiscountOnBillPreview);
                double dTotalOfAllDiscountOnItemsInBillPreview = Double.parseDouble(sTotalOfAllDiscountOnItemsInBillPreview);
                double dTotalDiscountDiscountOfBothSubPackage = dTotalDiscountOnItemsAfterApplyingGD + dSummationOfSubpackageItemDiscount;
                String sPaymentReceivedOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_paymentReceivedOnBillPreview);
                dPaymentReceivedOnBillPreview = Double.parseDouble(sPaymentReceivedOnBillPreview);
                m_assert.assertTrue((dTotalDiscountDiscountOfBothSubPackage == dTotalDiscountOnItemsInBillPreview && dTotalDiscountDiscountOfBothSubPackage == dTotalOfAllDiscountOnItemsInBillPreview), "Total discount on item and total of all discounts on bill preview = <b>" + dTotalOfAllDiscountOnItemsInBillPreview + "</b>");
                m_assert.assertTrue((dPaymentReceivedOnBillPreview == dAmountReceivedOnUI), "Payment Received on bill preview = <b>" + dAmountReceivedOnUI + "</b>");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_Bills.button_closeBill);
                Cls_Generic_Methods.customWait(4);

            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }

    }


    @Test(enabled = true, description = "create credit bill and validate the Subpackage item level Discount by adding invoice sets")
    public void createCreditBillForContactAndValidateSubPackageItemLevelDiscount() {
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills = new Page_Bills(driver);
        oPage_IPD = new Page_IPD(driver);
        boolean bPatientFound = false;
        String sPackageName = "AutomationPackageDataDisplayInsu";
        String sDiscountAmountOnFirstSubPackageItem = "4";
        String sDiscountAmountOnSecondSubPackageItem = "3";
        String sGlobalDiscount = "10";
        String sDiscountReason = "Reason" + getRandomUniqueString(6);
        String sSecondPackageName = "TestingTemplateBills";
        String sContactGroup = "Insurance";
        String sPayer = "Religare Health Insurance Company Limited";
//        double dDiscountAmountOnFirstSubPackageItem = 5;
//        double dDiscountAmountOnSecondSubPackageItem = 3;
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectDepartmentOnApp("IPD");
            myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
            m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today), "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");
            Cls_Generic_Methods.customWait(10);

            bPatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnScheduledTodayTab, concatPatientFullName);
            m_assert.assertTrue(bPatientFound, "Validate that the patient " + concatPatientFullName + " is created in IPD");

            double dFirstSubpackageItemDiscountOnBillPreview = 0;
            double dSecondSubpackageItemDiscountOnBillPreview = 0;
            double dOverDiscountAmountOnPackageInPreview = 0;
            double dGrossPrice = 0;
            //  double dPaymentReceivedOnBillPreview = 0;
            if (bPatientFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_editInsuranceDetailsButton, 30);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_editInsuranceDetailsButton),
                        "Clicked On edit Insurance details button ");
                Cls_Generic_Methods.customWait(4);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_IPD.select_payerFieldUnderInsuranceDetailsForm, sContactGroup),
                        "Selected payer = <b>" + sContactGroup + "</b>");
                Cls_Generic_Methods.customWait(4);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_IPD.select_contactFieldUnderInsuranceDetailsForm, sPayer),
                        "Selected contact = <b>" + sPayer + "</b>");
                Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_IPD.button_savePatientInsuranceForm, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.button_savePatientInsuranceForm), "Patient Insurance form saved");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills), "Clicked on bills button");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_IPD.button_newCreditBill);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 30);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickIPDPlusActionButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Bills.button_newPackage);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_deleteItemPackage.get(0));
                Cls_Generic_Methods.clickElement(oPage_Bills.select_package.get(0));
                boolean myPackageFound = false;
                for (WebElement eItemList : oPage_Bills.list_packageOrServiceNameUnderDescriptionField) {
                    String sPackageNameOnUI = Cls_Generic_Methods.getTextInElement(eItemList);
                    if (sPackageNameOnUI.equals(sPackageName)) {
                        Cls_Generic_Methods.clickElement(eItemList);
                        myPackageFound = true;
                        break;
                    }

                }
                m_assert.assertTrue(myPackageFound, "Selected package name = <b>" + sPackageName + "</b> ");
                Cls_Generic_Methods.customWait(4);
                String sQTY = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_Quantity.get(0), "value");
                String sUnitPrice = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(0), "value");
                String sGrossPrice = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(0), "value");
                dGrossPrice = Double.parseDouble(sGrossPrice);
                String sNetPrice = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dOverAllNetPriceOnPackage = Double.parseDouble(sNetPrice);
                Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(0));
                Cls_Generic_Methods.customWait(4);

                Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_deleteItemLevelDiscount.get(0));
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_subpackageItemLevelDiscountField.get(0))) {
                    m_assert.assertWarn(" First Subpackage item level Discount field is  displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("First Subpackage item level Discount field is not displaying as user deleted overall discount field ");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_subpackageItemLevelDiscountField.get(1))) {
                    m_assert.assertWarn("Second Subpackage item level Discount field is displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Second Subpackage item level Discount field is not displaying as user deleted overall discount field ");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_mainDiscountFieldOnPackage.get(0))) {
                    m_assert.assertWarn("Package level Discount field is  displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Package level Discount field is not displaying as user deleted overall discount field ");
                }
                Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(0));
                Cls_Generic_Methods.customWait(4);
                String sUnitCostOnFirstItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemUnitCost.get(0));
                String sNoOfUnitsCostPerDayOnUI = sUnitCostOnFirstItemUI.split(" ")[0];
                Double dNoOfUnitsCostPerDayOnUI = Double.parseDouble(sNoOfUnitsCostPerDayOnUI);
                // String sUnitCostOfFirstItem = sUnitCostOnFirstItemUI.split("\\(")[0];
                String sNoOfUnitsOrDays = sUnitCostOnFirstItemUI.split("\\(")[1];
                String sNoUnitsPerDayForFirstItem = sNoOfUnitsOrDays.split(" ")[0];
                Double dNoUnitsPerDayForFirstItem = Double.parseDouble(sNoUnitsPerDayForFirstItem);

                String sGrossSubPackageItemPriceForFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(0));
                String sNetPriceItemPriceForFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                Cls_Generic_Methods.customWait(4);
                double dNetPriceOfFirstItemBeforeDiscount = Double.parseDouble(sNetPriceItemPriceForFirstItem);
                double dGrossPriceOfFirstItemBeforeDiscount = Double.parseDouble(sGrossSubPackageItemPriceForFirstItem);
                double dCalculatedGrossPriceAndNetPriceForFirstItem = dNoOfUnitsCostPerDayOnUI * dNoUnitsPerDayForFirstItem;
                if (dCalculatedGrossPriceAndNetPriceForFirstItem == dNetPriceOfFirstItemBeforeDiscount && dCalculatedGrossPriceAndNetPriceForFirstItem == dGrossPriceOfFirstItemBeforeDiscount) {
                    m_assert.assertTrue("Calculated gross price for first package level item is equal to net price and gross price on UI, Before applying a discount = <b>  " + dNetPriceOfFirstItemBeforeDiscount + "</b>");
                } else {
                    m_assert.assertWarn("Calculated gross price first package level item is not equal to net price on UI and gross price on UI, Before applying a discount");
                }

                String sUnitCostOnSecondItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemUnitCost.get(1));
                // String sUnitCostOfSecondItem = sUnitCostOnSecondItemUI.split("\\(")[0];
                String sNoOfUnitsOrDaysOnUIForSecondItem = sUnitCostOnSecondItemUI.split(" ")[0];
                Double dNoOfUnitsOrDaysOnUIForSecondItem = Double.parseDouble(sNoOfUnitsOrDaysOnUIForSecondItem);
                String sNoOfUnitsOrDaysForSecondItem = sUnitCostOnSecondItemUI.split("\\(")[1];
                String sNoUnitsPerDayForSecondItems = sNoOfUnitsOrDaysForSecondItem.split(" ")[0];
                Double dNoUnitsPerDayForSecondItems = Double.parseDouble(sNoUnitsPerDayForSecondItems);
                String sGrossSubPackageItemPriceForSecondItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(1));
                String sNetPriceItemPriceForSecondItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dGrossSubPackageItemPriceForSecondItem = Double.parseDouble(sGrossSubPackageItemPriceForSecondItem);
                double dNetPriceItemPriceForSecondItem = Double.parseDouble(sNetPriceItemPriceForSecondItem);
                double dCalculatedGrossPriceAndNetPriceForSecondItem = dNoOfUnitsOrDaysOnUIForSecondItem * dNoUnitsPerDayForSecondItems;
                if (dCalculatedGrossPriceAndNetPriceForSecondItem == dNetPriceItemPriceForSecondItem && dCalculatedGrossPriceAndNetPriceForSecondItem == dGrossSubPackageItemPriceForSecondItem) {
                    m_assert.assertTrue("Calculated gross price for Second package level item is equal to net price and gross price on UI, Before applying a discount<b> " + dNetPriceItemPriceForSecondItem + "</b>");
                } else {
                    m_assert.assertWarn("Calculated gross price second package level item is not equal to net price on UI and gross price on UI, Before applying a discount");
                }
                double dSumOfFistAndSecondSubpackageItemsNetPrice = dNetPriceItemPriceForSecondItem + dNetPriceOfFirstItemBeforeDiscount;
                if (dSumOfFistAndSecondSubpackageItemsNetPrice == dOverAllNetPriceOnPackage) {
                    m_assert.assertTrue("summation of first and second subpackage level items net price is equal to overall subpackage net price, Before applying a discount<b>  " + dOverAllNetPriceOnPackage + "</b>");
                } else {
                    m_assert.assertWarn("summation of first and second subpackage level items net price is equal to overall subpackage net price, Before applying a discount");
                }
                // Apply discount on each subPackage item
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(0));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(0), sDiscountAmountOnFirstSubPackageItem), "Applied Sub package item level discount for first item = <b> " + sDiscountAmountOnFirstSubPackageItem + "</b>");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(1));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.list_subpackageItemLevelDiscountTypes.get(1), "%"), "Discount type for second subpackage level item =  <b>%</b>");
                Cls_Generic_Methods.customWait(4);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(1), sDiscountAmountOnSecondSubPackageItem), "Applied Sub package item level discount for second item = <b> " + sDiscountAmountOnSecondSubPackageItem + "</b>");
                double dDiscountAmountOnSecondSubPackageItem = Double.parseDouble(sDiscountAmountOnSecondSubPackageItem);
                double dDiscountPercentageForSecondItem = dDiscountAmountOnSecondSubPackageItem / 100;
                double dDiscountAmountAfterApplyingDiscountInPercentageForSecondItem = dNetPriceItemPriceForSecondItem * dDiscountPercentageForSecondItem;
                double dRoundingOffTheDiscountForSecondItem = roundOffFunctionUsingDouble(dDiscountAmountAfterApplyingDiscountInPercentageForSecondItem);
                //Multiplying with minus as we are showing discount value in negative
                double dRoundingOffTheDiscountOnSecondItem = dRoundingOffTheDiscountForSecondItem * (-1);
                String sFirstSubpackageItemDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dFirstSubpackageItemDiscountAmount = Double.parseDouble(sFirstSubpackageItemDiscountAmount);
                double dNetPriceOfFirstItemAfterDiscount = dNetPriceOfFirstItemBeforeDiscount + dFirstSubpackageItemDiscountAmount;
                String sNetAmountAfterDiscountForFirstItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                double dNetAmountAfterDiscountForFirstItemOnUI = Double.parseDouble(sNetAmountAfterDiscountForFirstItemOnUI);
                if (dNetPriceOfFirstItemAfterDiscount == dNetAmountAfterDiscountForFirstItemOnUI) {
                    m_assert.assertTrue("Net price amount for first subpackage item after applying Discount =  <b>" + dNetAmountAfterDiscountForFirstItemOnUI + "</b>");
                } else {
                    m_assert.assertWarn("Calculated Net price amount for first subpackage item after applying Discount is not matching with UI net price");
                }
                String sSecondSubpackageItemDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(1));
                double dSecondSubpackageItemDiscountAmount = Double.parseDouble(sSecondSubpackageItemDiscountAmount);
                double dNetPriceOfSecondItemAfterDiscount = dNetPriceItemPriceForSecondItem + dSecondSubpackageItemDiscountAmount;
                String sNetAmountAfterDiscountForSecondItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dNetAmountAfterDiscountForSecondItemOnUI = Double.parseDouble(sNetAmountAfterDiscountForSecondItemOnUI);
                if (dRoundingOffTheDiscountOnSecondItem == dSecondSubpackageItemDiscountAmount) {
                    m_assert.assertTrue("Discount amount for second subpackage item on UI is matched with calculated Discount amount= <b>" + dSecondSubpackageItemDiscountAmount + "</b>");
                } else {
                    m_assert.assertWarn("Discount amount for second subpackage item on UI is not matching with calculated Discount amount");
                }
                if (dNetPriceOfSecondItemAfterDiscount == dNetAmountAfterDiscountForSecondItemOnUI) {
                    m_assert.assertTrue("Net price amount for second subpackage item after applying Discount =  <b>" + dNetAmountAfterDiscountForFirstItemOnUI + "</b>");
                } else {
                    m_assert.assertWarn("Calculated Net price  for second subpackage item after applying Discount is not matching with UI net price");
                }
                // summation subpackage item level discount
                double dTotalDiscountAmountOnOverAllSubpackage = dFirstSubpackageItemDiscountAmount + dSecondSubpackageItemDiscountAmount;
                String sOverAllDiscountOnPackage = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_overallDiscountAmountOnPackage);
                String sOverAllDiscountPackage = sOverAllDiscountOnPackage.replaceAll("\\s", "");
                double dOverAllDiscountOnPackage = Double.parseDouble(sOverAllDiscountPackage);
                if (dOverAllDiscountOnPackage == dTotalDiscountAmountOnOverAllSubpackage) {
                    m_assert.assertTrue("Subpackage item level discounts are matching with overall subpackage discount field =  <b>" + dTotalDiscountAmountOnOverAllSubpackage + "</b>");
                } else {
                    m_assert.assertWarn("Subpackage item level discounts are not matching with overall subpackage discount field");
                }
                //Over all package net price after discount
                double dNetAmountOnOverAllPackageAfterApplyingDiscount = dOverAllNetPriceOnPackage + dTotalDiscountAmountOnOverAllSubpackage;
                String sNetAmtAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetAmtAfterDiscount = Double.parseDouble(sNetAmtAfterDiscount);
                m_assert.assertTrue((dNetAmountOnOverAllPackageAfterApplyingDiscount == dNetAmtAfterDiscount), "Calculated Net Amount after discount matching with Net amount on UI <b>" + dNetAmtAfterDiscount + "</b>");

                // delete all subpackage item wise discounts
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_deleteSubPackageItemsDiscount.get(1)), "clicked on delete subpackage item level discount button");
                String sSecondSubPackageNetAmountAfterDeletingItemLevelDiscount = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dSecondSubPackageNetAmountAfterDeletingItemLevelDiscount = Double.parseDouble(sSecondSubPackageNetAmountAfterDeletingItemLevelDiscount);
                m_assert.assertTrue((dSecondSubPackageNetAmountAfterDeletingItemLevelDiscount == dCalculatedGrossPriceAndNetPriceForSecondItem), "Upon deleting a second subpackage level item discount calculated Net Amount is matching with Net amount on UI <b>" + dSecondSubPackageNetAmountAfterDeletingItemLevelDiscount + "</b>");

                //net price of first sub package level item after discount
                String sFirstSubpackageItemDiscountAfterRemovingSecondItemsDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dFirstSubpackageItemAfterRemovingSecondItemsDiscountAmount = Double.parseDouble(sFirstSubpackageItemDiscountAfterRemovingSecondItemsDiscountAmount);
                double dNetPriceOfFirstItemAfterRemovingSecondItemsDiscount = dNetPriceOfFirstItemBeforeDiscount + dFirstSubpackageItemAfterRemovingSecondItemsDiscountAmount;
                String sNetAmountAfterDiscountOnFirstItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                double dNetAmountAfterDiscountOnFirstItemOnUI = Double.parseDouble(sNetAmountAfterDiscountOnFirstItemOnUI);
                m_assert.assertTrue((dNetAmountAfterDiscountOnFirstItemOnUI == dNetPriceOfFirstItemAfterRemovingSecondItemsDiscount), "Upon deleting a second subpackage level discount calculated Net Amount of second item is matching with Net amount on UI of second item <b>" + dNetPriceOfFirstItemAfterRemovingSecondItemsDiscount + "</b>");
                //Over all package net price after deleting second items discount
                double dCalculatedNetAmountAfterRemovingSecondItemsDiscount = dNetAmountOnOverAllPackageAfterApplyingDiscount + dRoundingOffTheDiscountForSecondItem;
                String sNetAmtAfterRemovingSecondItemDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetAmtAfterRemovingSecondItemDiscount = Double.parseDouble(sNetAmtAfterRemovingSecondItemDiscount);
                m_assert.assertTrue((dCalculatedNetAmountAfterRemovingSecondItemsDiscount == dNetAmtAfterRemovingSecondItemDiscount), "Calculated Net Amount after removing second items discount is matching with Net amount on UI <b>" + dCalculatedNetAmountAfterRemovingSecondItemsDiscount + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Bills.button_addSubPackageItemsDiscount.get(1));
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmount.get(0), sGlobalDiscount), "Entered amount in Global discount field = <b>" + sGlobalDiscount + "</b>");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);
                double dGlobalDiscount = Double.parseDouble(sGlobalDiscount);
                double dGlobalDiscountPercentage = dGlobalDiscount / dOverAllNetPriceOnPackage;
                double dDiscountOnFirstItemAfterApplyingGlobalDiscount = dGlobalDiscountPercentage * dGrossPriceOfFirstItemBeforeDiscount;
                double dDiscountOnSecondItemAfterApplyingGlobalDiscount = dGlobalDiscountPercentage * dGrossSubPackageItemPriceForSecondItem;
                double dSummationOfSubpackageItemWiseDiscount = dDiscountOnFirstItemAfterApplyingGlobalDiscount + dDiscountOnSecondItemAfterApplyingGlobalDiscount;
                double dNetAmountOfFirstSubpackageItemAfterGlobalDiscount = dGrossPriceOfFirstItemBeforeDiscount - dDiscountOnFirstItemAfterApplyingGlobalDiscount;
                double dNetAmountOfSecondSubpackageItemAfterGlobalDiscount = dGrossSubPackageItemPriceForSecondItem - dDiscountOnSecondItemAfterApplyingGlobalDiscount;
                m_assert.assertTrue((dSummationOfSubpackageItemWiseDiscount == dGlobalDiscount), "Subpackage item wise discounts sum is equal to Global discount amount <b>" + dGlobalDiscount + "</b>");
                Cls_Generic_Methods.customWait(3);
                //Apply GD in percentage
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_globalDiscountType, "%"), "Discount type for GD =  <b>%</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmount.get(0), sGlobalDiscount), "Entered amount in Global discount field = <b>" + sGlobalDiscount + "</b>");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_discountReason.get(0), sDiscountReason);
                double dGlobalDiscountInPercentage = dGlobalDiscount / 100;
                double dDiscountOnFirstItemAfterApplyingGlobalDiscountInPercentage = dGlobalDiscountInPercentage * dGrossPriceOfFirstItemBeforeDiscount;
                double dDiscountOnSecondItemAfterApplyingGlobalDiscountInPercentage = dGlobalDiscountInPercentage * dGrossSubPackageItemPriceForSecondItem;
                String sDiscount1 = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dDiscount1 = Double.parseDouble(sDiscount1);
                double dFirstSubpackageDiscountOnUI = dDiscount1 * (-1);
                String sDiscount2 = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(1));
                double dDiscount2 = Double.parseDouble(sDiscount2);
                double dSecondSubpackageDiscountOnUI = dDiscount2 * (-1);
                m_assert.assertTrue((dFirstSubpackageDiscountOnUI == dDiscountOnFirstItemAfterApplyingGlobalDiscountInPercentage && dSecondSubpackageDiscountOnUI == dDiscountOnSecondItemAfterApplyingGlobalDiscountInPercentage), "Discount amount is matching with calculated subpackage item level discounts for subpackage 1st item discount: <b>" + dFirstSubpackageDiscountOnUI + "</b> 2nd item discount: <b>" + dSecondSubpackageDiscountOnUI);
                double dSummationOfSubpackageItemWiseDiscountInPercentage = dFirstSubpackageDiscountOnUI + dSecondSubpackageDiscountOnUI;
                double dNetAmountOfFirstSubpackageItemAfterGlobalDiscountInPercentage = dGrossPriceOfFirstItemBeforeDiscount - dFirstSubpackageDiscountOnUI;
                double dNetAmountOfSecondSubpackageItemAfterGlobalDiscountInPercentage = dGrossSubPackageItemPriceForSecondItem - dSecondSubpackageDiscountOnUI;
                String sNetAmountForFirstSubItem = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetAmountForFirstSubItem = Double.parseDouble(sNetAmountForFirstSubItem);
                double dSumNetAmountAfterGD = dNetAmountOfFirstSubpackageItemAfterGlobalDiscountInPercentage + dNetAmountOfSecondSubpackageItemAfterGlobalDiscountInPercentage;
                m_assert.assertTrue((dNetAmountForFirstSubItem == dSumNetAmountAfterGD), "Net amount on UI equals calculated net amount after applying global discount <b>" + dSumNetAmountAfterGD + "</b>");
                Cls_Generic_Methods.customWait(3);
                String sNetAmountOnBillAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_netBillTotal, "value");
                double dNetAmountOnBillAfterApplyingGD = Double.parseDouble(sNetAmountOnBillAfterApplyingGD);
                String sGrossBillTotalOnBillAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_grossBillTotal, "value");
                double dGrossBillTotalOnBillAfterApplyingGD = Double.parseDouble(sGrossBillTotalOnBillAfterApplyingGD);
                String sDiscountOnItemsAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnItem, "value");
                double dDiscountOnItemsAfterApplyingGD = Double.parseDouble(sDiscountOnItemsAfterApplyingGD);
                m_assert.assertTrue((dNetAmountOnBillAfterApplyingGD == dSumNetAmountAfterGD && dGrossBillTotalOnBillAfterApplyingGD == dGrossPrice && dDiscountOnItemsAfterApplyingGD == dSummationOfSubpackageItemWiseDiscountInPercentage), "Net amount on bill = <b>" + dNetAmountOnBillAfterApplyingGD + "</b> , Gross amount on bill =  <b>" + dGrossBillTotalOnBillAfterApplyingGD + "</b> , Discount on item = <b> " + dDiscountOnItemsAfterApplyingGD + "</b>");
                Cls_Generic_Methods.customWait(4);
                String sTotalDiscountOnItemsAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnBill, "value");
                double dTotalDiscountOnItemsAfterApplyingGD = Double.parseDouble(sTotalDiscountOnItemsAfterApplyingGD);
                String sTotalOfAllDiscountOnItemsAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalOfAllDiscounts, "value");
                double dTotalOfAllDiscountOnItemsAfterApplyingGD = Double.parseDouble(sTotalOfAllDiscountOnItemsAfterApplyingGD);
                m_assert.assertTrue((dTotalDiscountOnItemsAfterApplyingGD == dTotalOfAllDiscountOnItemsAfterApplyingGD), "Total discount and total of all discount fields, values matching on UI = <b>" + dTotalOfAllDiscountOnItemsAfterApplyingGD + "</b>");

                Cls_Generic_Methods.customWait(3);
                // select second package
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickIPDPlusActionButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Bills.button_newPackage);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.select_package.get(1));
                boolean PackageFound = false;
                for (WebElement eItemList : oPage_Bills.list_packageOrServiceNameUnderDescriptionField) {
                    String sPackageNameOnUI = Cls_Generic_Methods.getTextInElement(eItemList);
                    if (sPackageNameOnUI.equals(sSecondPackageName)) {
                        Cls_Generic_Methods.clickElement(eItemList);
                        PackageFound = true;
                        break;
                    }
                    m_assert.assertTrue(true, "Selected package name = <b>" + sSecondPackageName + "</b> ");
                }
                // Cls_Generic_Methods.selectElementByVisibleText(oPage_Bills.option_invoiceSets, sInvoiceSetName);
                Cls_Generic_Methods.customWait(4);
                String sQTYForSecondPackage = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_Quantity.get(1), "value");
                String sUnitPriceForSecondPackage = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(1), "value");
                String sGrossPriceForSecondPackage = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(1), "value");
                dGrossPrice = Double.parseDouble(sGrossPriceForSecondPackage);
                String sNetPriceForSecondPackage = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value");
                double dOverAllNetPriceOnPackageForSecondPackage = Double.parseDouble(sNetPriceForSecondPackage);
                Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(1));
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_deleteItemLevelDiscount.get(1));
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_subpackageItemLevelDiscountField.get(2))) {
                    m_assert.assertWarn("Second package: First Subpackage item level Discount field is  displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Second package: First Subpackage item level Discount field is not displaying as user deleted overall discount field ");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_subpackageItemLevelDiscountField.get(3))) {
                    m_assert.assertWarn("Second package: Second Subpackage item level Discount field is displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Second package: Second Subpackage item level Discount field is not displaying as user deleted overall discount field ");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_mainDiscountFieldOnPackage.get(1))) {
                    m_assert.assertWarn("Second package: Package level Discount field is  displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Second package: Package item level Discount field is not displaying as user deleted overall discount field ");
                }
                Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(1));
                Cls_Generic_Methods.customWait(4);
                String sUnitCostOnFirstSubpackageItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemUnitCost.get(2));
                String sNoOfUnitsCostPerDayOnUIForFirstSubpackage = sUnitCostOnFirstSubpackageItemUI.split(" ")[0];
                Double dNoOfUnitsCostPerDayOnFirstSubpackageOnUI = Double.parseDouble(sNoOfUnitsCostPerDayOnUIForFirstSubpackage);
                // String sUnitCostOfFirstItem = sUnitCostOnFirstItemUI.split("\\(")[0];
                String sNoOfUnitsOrDaysForFirstSubpacakge = sUnitCostOnFirstSubpackageItemUI.split("\\(")[1];
                String sNoUnitsPerDayForFirstSubPackageItem = sNoOfUnitsOrDaysForFirstSubpacakge.split(" ")[0];
                Double dNoUnitsPerDayForFirstSubpackageItem = Double.parseDouble(sNoUnitsPerDayForFirstSubPackageItem);

                String sGrossSubPackageItemPriceONFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(2));
                String sNetPriceItemPriceOnFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(2));
                Cls_Generic_Methods.customWait(4);
                double dNetPriceOfFirstItemBeforeApplyingDiscount = Double.parseDouble(sNetPriceItemPriceOnFirstItem);
                double dGrossPriceOfFirstItemBeforeApplyingDiscount = Double.parseDouble(sGrossSubPackageItemPriceONFirstItem);
                double dCalculatedGrossPriceAndNetPriceOnFirstItem = dNoOfUnitsCostPerDayOnFirstSubpackageOnUI * dNoUnitsPerDayForFirstSubpackageItem;
                if (dCalculatedGrossPriceAndNetPriceOnFirstItem == dNetPriceOfFirstItemBeforeApplyingDiscount && dCalculatedGrossPriceAndNetPriceOnFirstItem == dGrossPriceOfFirstItemBeforeApplyingDiscount) {
                    m_assert.assertTrue("Calculated gross price for first package level item is equal to net price and gross price on UI, Before applying a discount = <b>  " + dNetPriceOfFirstItemBeforeApplyingDiscount + "</b>");
                } else {
                    m_assert.assertWarn("Calculated gross price first package level item is not equal to net price on UI and gross price on UI, Before applying a discount");
                }

                String sUnitCostOnSecondSubPackageItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemUnitCost.get(3));
                // String sUnitCostOfSecondItem = sUnitCostOnSecondItemUI.split("\\(")[0];
                String sNoOfUnitsOrDaysOnUIForSecondSubPackageItem = sUnitCostOnSecondSubPackageItemUI.split(" ")[0];
                Double dNoOfUnitsOrDaysOnUIForSecondSubPackageItem = Double.parseDouble(sNoOfUnitsOrDaysOnUIForSecondSubPackageItem);
                String sNoOfUnitsOrDaysForSecondSubPackageItem = sUnitCostOnSecondSubPackageItemUI.split("\\(")[1];
                String sNoUnitsPerDayForSecondSubPackageItems = sNoOfUnitsOrDaysForSecondSubPackageItem.split(" ")[0];
                Double dNoUnitsPerDayForSecondSubPackageItems = Double.parseDouble(sNoUnitsPerDayForSecondSubPackageItems);
                String sGrossSubPackageItemPriceForSecondSubPackageItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(3));
                String sNetPriceItemPriceForSecondSubPackageItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(3));
                double dGrossSubPackageItemPriceForSecondSubPackageItem = Double.parseDouble(sGrossSubPackageItemPriceForSecondSubPackageItem);
                double dNetPriceItemPriceForSecondSubPackageItem = Double.parseDouble(sNetPriceItemPriceForSecondSubPackageItem);
                double dCalculatedGrossPriceAndNetPriceForSecondSubPackageItem = dNoOfUnitsOrDaysOnUIForSecondSubPackageItem * dNoUnitsPerDayForSecondSubPackageItems;
                if (dCalculatedGrossPriceAndNetPriceForSecondSubPackageItem == dNetPriceItemPriceForSecondSubPackageItem && dCalculatedGrossPriceAndNetPriceForSecondSubPackageItem == dGrossSubPackageItemPriceForSecondSubPackageItem) {
                    m_assert.assertTrue("Calculated gross price for Second package level item is equal to net price and gross price on UI, Before applying a discount<b> " + dNetPriceItemPriceForSecondSubPackageItem + "</b>");
                } else {
                    m_assert.assertWarn("Calculated gross price second package level item is not equal to net price on UI and gross price on UI, Before applying a discount");
                }
                double dSumOfFistAndSecondSubpackageItemNetPrice = dNetPriceItemPriceForSecondSubPackageItem + dNetPriceOfFirstItemBeforeApplyingDiscount;
                if (dSumOfFistAndSecondSubpackageItemNetPrice == dOverAllNetPriceOnPackageForSecondPackage) {
                    m_assert.assertTrue("summation of first and second subpackage level items net price is equal to overall subpackage net price, Before applying a discount<b>  " + dOverAllNetPriceOnPackageForSecondPackage + "</b>");
                } else {
                    m_assert.assertWarn("summation of first and second subpackage level items net price is equal to overall subpackage net price, Before applying a discount");
                }
                // Apply discount on each subPackage item
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(2));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(2), sDiscountAmountOnFirstSubPackageItem), "Applied Sub package item level discount for first item = <b> " + sDiscountAmountOnFirstSubPackageItem + "</b>");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(3));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.list_subpackageItemLevelDiscountTypes.get(3), "%"), "Discount type for second subpackage level item =  <b>%</b>");
                Cls_Generic_Methods.customWait(4);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(3), sDiscountAmountOnSecondSubPackageItem), "Applied Sub package item level discount for second item = <b> " + sDiscountAmountOnSecondSubPackageItem + "</b>");
                double dDiscountAmountForSecondSubPackageItem = Double.parseDouble(sDiscountAmountOnSecondSubPackageItem);
                double dDiscountPercentageONSecondItem = dDiscountAmountForSecondSubPackageItem / 100;
                double dDiscountAmountAfterApplyingDiscountonPercentageForSecondItem = dNetPriceItemPriceForSecondSubPackageItem * dDiscountPercentageONSecondItem;
                double dRoundingOffTheDiscountAmountForSecondItem = roundOffFunctionUsingDouble(dDiscountAmountAfterApplyingDiscountonPercentageForSecondItem);
                //Multiplying with minus as we are showing discount value in negative
                double dRoundOffTheDiscountOnSecondItem = dRoundingOffTheDiscountAmountForSecondItem * (-1);
                String sFirstSubpackageItemsDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(2));
                double dFirstSubpackageItemsDiscountAmount = Double.parseDouble(sFirstSubpackageItemsDiscountAmount);
                double dNetPriceOfTheFirstItemAfterDiscount = dNetPriceOfFirstItemBeforeApplyingDiscount + dFirstSubpackageItemsDiscountAmount;
                String sNetAmountAfterApplyingDiscountForFirstItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(2));
                double dNetAmountAfterApplyingDiscountForFirstItemOnUI = Double.parseDouble(sNetAmountAfterApplyingDiscountForFirstItemOnUI);
                if (dNetPriceOfTheFirstItemAfterDiscount == dNetAmountAfterApplyingDiscountForFirstItemOnUI) {
                    m_assert.assertTrue("Net price amount for first subpackage item after applying Discount =  <b>" + dNetAmountAfterApplyingDiscountForFirstItemOnUI + "</b>");
                } else {
                    m_assert.assertWarn("Calculated Net price amount for first subpackage item after applying Discount is not matching with UI net price");
                }
                String sSecondSubpackageItemsDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(3));
                double dSecondSubpackageItemsDiscountAmount = Double.parseDouble(sSecondSubpackageItemsDiscountAmount);
                double dNetPriceOfSecondItemsAfterDiscount = dNetPriceItemPriceForSecondSubPackageItem + dSecondSubpackageItemsDiscountAmount;
                String sNetAmountAfterDiscountOnSecondItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(3));
                double dNetAmountAfterDiscountOnSecondItemOnUI = Double.parseDouble(sNetAmountAfterDiscountOnSecondItemOnUI);
                if (dRoundOffTheDiscountOnSecondItem == dSecondSubpackageItemsDiscountAmount) {
                    m_assert.assertTrue("Discount amount for second subpackage item on UI is matched with calculated Discount amount= <b>" + dSecondSubpackageItemsDiscountAmount + "</b>");
                } else {
                    m_assert.assertWarn("Discount amount for second subpackage item on UI is not matching with calculated Discount amount");
                }
                if (dNetPriceOfSecondItemsAfterDiscount == dNetAmountAfterDiscountOnSecondItemOnUI) {
                    m_assert.assertTrue("Net price amount for second subpackage item after applying Discount =  <b>" + dNetAmountAfterDiscountOnSecondItemOnUI + "</b>");
                } else {
                    m_assert.assertWarn("Calculated Net price  for second subpackage item after applying Discount is not matching with UI net price");
                }
                // summation subpackage item level discount
                double dTotalDiscountAmountForAllSubpackage = dFirstSubpackageItemsDiscountAmount + dSecondSubpackageItemsDiscountAmount;
                String sOverAllDiscountForSecondPackage = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_overallDiscountOnPackage.get(1));
                String sOverAllDiscountSecondPackage = sOverAllDiscountForSecondPackage.replaceAll("\\s", "");
                double dOverAllDiscountOnSecondPackage = Double.parseDouble(sOverAllDiscountSecondPackage);
                if (dOverAllDiscountOnSecondPackage == dTotalDiscountAmountForAllSubpackage) {
                    m_assert.assertTrue("Subpackage item level discounts are matching with overall subpackage discount field =  <b>" + dTotalDiscountAmountForAllSubpackage + "</b>");
                } else {
                    m_assert.assertWarn("Subpackage item level discounts are not matching with overall subpackage discount field");
                }
                //Over all package net price after discount
                double dOverAllNetAmountOnSecondPackageAfterApplyingDiscount = dOverAllNetPriceOnPackageForSecondPackage + dTotalDiscountAmountForAllSubpackage;
                String sNetAmtAfterApplyingDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value");
                double dNetAmtAfterApplyingDiscount = Double.parseDouble(sNetAmtAfterApplyingDiscount);
                m_assert.assertTrue((dOverAllNetAmountOnSecondPackageAfterApplyingDiscount == dNetAmtAfterApplyingDiscount), "Calculated Net Amount after discount matching with Net amount on UI <b>" + dNetAmtAfterApplyingDiscount + "</b>");

                // delete all subpackage item wise discounts
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_deleteSubPackageItemsDiscount.get(3)), "clicked on delete subpackage item level discount button");
                String sSecondSubPackageNetAmountAfterDeletingItemLevelDisc = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(3));
                double dSecondSubPackageNetAmountAfterDeletingItemLevelDisc = Double.parseDouble(sSecondSubPackageNetAmountAfterDeletingItemLevelDisc);
                m_assert.assertTrue((dSecondSubPackageNetAmountAfterDeletingItemLevelDisc == dCalculatedGrossPriceAndNetPriceForSecondSubPackageItem), "Upon deleting a second subpackage level item discount calculated Net Amount is matching with Net amount on UI <b>" + dSecondSubPackageNetAmountAfterDeletingItemLevelDisc + "</b>");

                //net price of first sub package level item after discount
                String sFirstSubpackageItemDiscountAfterRemovingSecondItemsDiscount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(2));
                double dFirstSubpackageItemAfterRemovingSecondItemsDiscount = Double.parseDouble(sFirstSubpackageItemDiscountAfterRemovingSecondItemsDiscount);
                double dNetPriceOfFirstItemAfterRemovingSecondItemDiscount = dNetPriceOfFirstItemBeforeApplyingDiscount + dFirstSubpackageItemAfterRemovingSecondItemsDiscount;
                String sNetAmountAfterDiscountOnFirstItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(2));
                double dNetAmountAfterDiscountOnFirstItemUI = Double.parseDouble(sNetAmountAfterDiscountOnFirstItemUI);
                m_assert.assertTrue((dNetAmountAfterDiscountOnFirstItemUI == dNetPriceOfFirstItemAfterRemovingSecondItemDiscount), "Upon deleting a second subpackage level discount calculated Net Amount of second item is matching with Net amount on UI of second item <b>" + dNetPriceOfFirstItemAfterRemovingSecondItemDiscount + "</b>");
                //Over all package net price after deleting second items discount
                double dCalculatedNetAmountAfterRemovingSecondItemDiscount = dOverAllNetAmountOnSecondPackageAfterApplyingDiscount + dRoundingOffTheDiscountAmountForSecondItem;
                String sNetAmtAfterRemovingSecondItemsDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value");
                double dNetAmtAfterRemovingSecondItemsDiscount = Double.parseDouble(sNetAmtAfterRemovingSecondItemsDiscount);
                m_assert.assertTrue((dCalculatedNetAmountAfterRemovingSecondItemDiscount == dNetAmtAfterRemovingSecondItemsDiscount), "Calculated Net Amount after removing second items discount is matching with Net amount on UI <b>" + dCalculatedNetAmountAfterRemovingSecondItemDiscount + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Bills.button_addSubPackageItemsDiscount.get(3));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmount.get(1), sGlobalDiscount), "Entered amount in Global discount field = <b>" + sGlobalDiscount + "</b>");
                // m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_receiptTemplateGlobalDiscount), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);
                double dGlobalDiscountForSecondPackage = Double.parseDouble(sGlobalDiscount);
                double dGlobalDiscountPercentageForSecondPackage = dGlobalDiscountForSecondPackage / dOverAllNetPriceOnPackageForSecondPackage;
                double dDiscountOnFirstItemAfterGlobalDiscount = dGlobalDiscountPercentageForSecondPackage * dGrossPriceOfFirstItemBeforeApplyingDiscount;
                double dRoundingOffTheDiscountOnFirstItem = roundOffFunctionUsingDouble(dDiscountOnFirstItemAfterGlobalDiscount);
                double dDiscountOnSecondItemAfterGlobalDiscount = dGlobalDiscountPercentageForSecondPackage * dGrossSubPackageItemPriceForSecondSubPackageItem;
                double dRoundingOffTheDiscountOnForSecondItem = roundOffFunctionUsingDouble(dDiscountOnSecondItemAfterGlobalDiscount);
                double dSummationOfSubpackageItemDiscount = dRoundingOffTheDiscountOnFirstItem + dRoundingOffTheDiscountOnForSecondItem;
                double dNetAmountOfFirstSubpackageItemAfterApplyingGlobalDiscount = dGrossPriceOfFirstItemBeforeApplyingDiscount - dDiscountOnFirstItemAfterGlobalDiscount;
                double dNetAmountOfSecondSubpackageItemAfterApplyingGlobalDiscount = dGrossSubPackageItemPriceForSecondSubPackageItem - dDiscountOnSecondItemAfterGlobalDiscount;
                m_assert.assertTrue((dSummationOfSubpackageItemDiscount == dGlobalDiscountForSecondPackage), "Subpackage item wise discounts sum is equal to Global discount amount <b>" + dGlobalDiscountForSecondPackage + "</b>");
                Cls_Generic_Methods.customWait(3);
                double dSumOfFirstAndSecondSubPackageItemNetAmount = dNetAmountOfFirstSubpackageItemAfterApplyingGlobalDiscount + dNetAmountOfSecondSubpackageItemAfterApplyingGlobalDiscount;
                double dRoundingOffTheNetAmountForSecondItem = roundOffFunctionUsingDouble(dSumOfFirstAndSecondSubPackageItemNetAmount);
                double dOverAllBillNetPrice = dNetAmountOnBillAfterApplyingGD + dRoundingOffTheNetAmountForSecondItem;
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_discountReason.get(1), sDiscountReason);
                Cls_Generic_Methods.clickElement(oPage_IPD.button_removePaymentReceivedDetailsUnderBills);
                String sAmountPendingOnUI = Cls_Generic_Methods.getElementAttribute(oPage_IPD.input_amountPendingField, "value");
                double dAmountPendingOnUI = Double.parseDouble(sAmountPendingOnUI);
                m_assert.assertTrue((dAmountPendingOnUI == dOverAllBillNetPrice), "Net amount on bill equals to amount pending against bill is matching = <b>" + dOverAllBillNetPrice + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills), "<b>Credit bill is created </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.customWait(4);
                //bill preview validation
                String sFirstSubpackageItemDiscountOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(0));
                dFirstSubpackageItemDiscountOnBillPreview = Double.parseDouble(sFirstSubpackageItemDiscountOnBillPreview);

                String sSecondSubpackageItemDiscountOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(1));
                dSecondSubpackageItemDiscountOnBillPreview = Double.parseDouble(sSecondSubpackageItemDiscountOnBillPreview);
                m_assert.assertTrue((dFirstSubpackageItemDiscountOnBillPreview == dDiscountOnFirstItemAfterApplyingGlobalDiscountInPercentage && dSecondSubpackageItemDiscountOnBillPreview == dDiscountOnSecondItemAfterApplyingGlobalDiscountInPercentage), "Discount amount is matching with calculated subpackage item level discounts 1st item1 on preview : <b>" + dFirstSubpackageItemDiscountOnBillPreview + "</b> 2nd item discount on bill preview : <b>" + dSecondSubpackageItemDiscountOnBillPreview);
                String sOverDiscountAmountOnPackageOnPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(0));
                String sOverDiscountAmountOnPackageInPreview = sOverDiscountAmountOnPackageOnPreview.replaceAll("\\s", "");
                double dOverDiscountAmountOnPackagePreview = Double.parseDouble(sOverDiscountAmountOnPackageInPreview);
                dOverDiscountAmountOnPackageInPreview = dOverDiscountAmountOnPackagePreview * (-1);
                m_assert.assertTrue((dTotalDiscountOnItemsAfterApplyingGD == dOverDiscountAmountOnPackageInPreview), "OverAll discount amount on package on bill preview for first package = <b>" + dTotalDiscountOnItemsAfterApplyingGD + "</b>");
                String sFirstSubpackageItemDiscountOnBillPreviewForSecondPackage = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(2));
                double dFirstSubpackageItemDiscountOnBillPreviewForSecondPackage = Double.parseDouble(sFirstSubpackageItemDiscountOnBillPreviewForSecondPackage);
                String sSecondSubpackageItemDiscountOnBillPreviewForSecondPackage = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(3));
                double dSecondSubpackageItemDiscountOnBillPreviewForSecondPackage = Double.parseDouble(sSecondSubpackageItemDiscountOnBillPreviewForSecondPackage);
                m_assert.assertTrue((dFirstSubpackageItemDiscountOnBillPreviewForSecondPackage == dRoundingOffTheDiscountOnFirstItem && dRoundingOffTheDiscountOnForSecondItem == dSecondSubpackageItemDiscountOnBillPreviewForSecondPackage), "Discount amount is matching with calculated subpackage item level discounts 1st item1 on preview : <b>" + dRoundingOffTheDiscountOnFirstItem + "</b> 2nd item discount on bill preview : <b>" + dSecondSubpackageItemDiscountOnBillPreviewForSecondPackage);
                String sOverDiscountAmountOnSecondPackageOnPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(1));
                String sOverDiscountAmountOnSecondPackageInPreview = sOverDiscountAmountOnSecondPackageOnPreview.replaceAll("\\s", "");
                double dOverDiscountAmountOnSecondPackagePreview = Double.parseDouble(sOverDiscountAmountOnSecondPackageInPreview);
                double dOverAllDiscountOnSecondItem = dOverDiscountAmountOnSecondPackagePreview * (-1);
                m_assert.assertTrue((dSummationOfSubpackageItemDiscount == dOverAllDiscountOnSecondItem), "OverAll discount amount on package on bill preview for second package = <b>" + dSummationOfSubpackageItemDiscount + "</b>");
                String sGrossBillTotalOnPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_grossBillTotalOnBillPreview);
                double dGrossBillTotalOnPreview = Double.parseDouble(sGrossBillTotalOnPreview);
                double dGrossOnBill = dGrossBillTotalOnBillAfterApplyingGD + dGrossPrice;
                m_assert.assertTrue((dGrossOnBill == dGrossBillTotalOnPreview), "Gross bill total on package on bill preview = <b>" + dGrossOnBill + "</b>");
                String sTotalDiscountOnItemsInBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalDiscountOnItemsOnBillPreview);
                double dTotalDiscountOnItemsInBillPreview = Double.parseDouble(sTotalDiscountOnItemsInBillPreview);
                String sTotalOfAllDiscountOnItemsInBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalOfAllDiscountOnBillPreview);
                double dTotalOfAllDiscountOnItemsInBillPreview = Double.parseDouble(sTotalOfAllDiscountOnItemsInBillPreview);
                double dTotalDiscountDiscountOfBothSubPackage = dTotalDiscountOnItemsAfterApplyingGD + dSummationOfSubpackageItemDiscount;
                m_assert.assertTrue((dTotalDiscountDiscountOfBothSubPackage == dTotalDiscountOnItemsInBillPreview && dTotalDiscountDiscountOfBothSubPackage == dTotalOfAllDiscountOnItemsInBillPreview), "Total discount on item and total of all discounts on bill preview = <b>" + dTotalOfAllDiscountOnItemsInBillPreview + "</b>");
                String sPaymentReceivedOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_paymentReceivedOnBillPreview);
                double dPaymentReceivedOnBillPreview = Double.parseDouble(sPaymentReceivedOnBillPreview);
                m_assert.assertTrue((dPaymentReceivedOnBillPreview == 0.00), "Payment Received on bill preview = <b>" + dPaymentReceivedOnBillPreview + "</b>");
                Cls_Generic_Methods.customWait(4);
                String sPaymentPendingOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_paymentPendingOnBillPreview);
                double dPaymentPendingOnBillPreview = Double.parseDouble(sPaymentPendingOnBillPreview);
                double dSummationOfPaymentPending = dSumNetAmountAfterGD + dRoundingOffTheNetAmountForSecondItem;
                m_assert.assertTrue((dPaymentPendingOnBillPreview == dSummationOfPaymentPending), "Payment Pending on bill preview = <b>" + dSummationOfPaymentPending + "</b>");
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_cancelBill);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_confirmCancelCreditBill);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_Bills.button_closeBill);
                Cls_Generic_Methods.customWait(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }

    }

    @Test(enabled = true, description = "create Template Bill And Validate SubPackage Item Level Discount")
    public void createTemplateBillAndValidateSubPackageItemLevelDiscount() {
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        oPage_Bills = new Page_Bills(driver);
        oPage_IPD = new Page_IPD(driver);
        boolean bPatientFound = false;
        String sDiscountAmountOnFirstSubPackageItem = "4";
        String sDiscountAmountOnSecondSubPackageItem = "3";
        String sGlobalDiscount = "10";
        String sDiscountReason = "Reason" + getRandomUniqueString(6);
        String sInvoiceSetName = "AutomationContactRecieptData";
//        double dDiscountAmountOnFirstSubPackageItem = 5;
//        double dDiscountAmountOnSecondSubPackageItem = 3;
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectDepartmentOnApp("IPD");
            myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
            m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today), "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");
            Cls_Generic_Methods.customWait(10);

            bPatientFound = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnScheduledTodayTab, concatPatientFullName);
            m_assert.assertTrue(bPatientFound, "Validate that the patient " + concatPatientFullName + " is created in IPD");

            double dFirstSubpackageItemDiscountOnBillPreview = 0;
            double dSecondSubpackageItemDiscountOnBillPreview = 0;
            double dOverDiscountAmountOnPackageInPreview = 0;
            double dGrossPrice = 0;
            double dPaymentReceivedOnBillPreview = 0;
            if (bPatientFound) {
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_ipdTemplateBills, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_ipdTemplateBills),
                        "<b>₹ Template Bills</b> Button is clicked");
                boolean TemplateBillFound = false;
                for (WebElement eTemplateBillList : oPage_Bills.list_templateBills) {
                    String sTemplateBillNameOnUI = Cls_Generic_Methods.getTextInElement(eTemplateBillList);
                    if (sTemplateBillNameOnUI.equals(sInvoiceSetName)) {
                        Cls_Generic_Methods.clickElement(eTemplateBillList);
                        TemplateBillFound = true;
                        break;
                    }
                    m_assert.assertTrue(true, "Selected template bill =  <b>" + sTemplateBillNameOnUI + "</b> ");
                }
                Cls_Generic_Methods.customWait(4);
                String sQTY = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_Quantity.get(0), "value");
                String sUnitPrice = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(0), "value");
                String sGrossPrice = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(0), "value");
                dGrossPrice = Double.parseDouble(sGrossPrice);
                String sNetPrice = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dOverAllNetPriceOnPackage = Double.parseDouble(sNetPrice);
                Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(0));
                Cls_Generic_Methods.customWait(4);

                Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_deleteItemLevelDiscount.get(0));
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_subpackageItemLevelDiscountField.get(0))) {
                    m_assert.assertWarn(" First Subpackage item level Discount field is  displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("First Subpackage item level Discount field is not displaying as user deleted overall discount field ");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_subpackageItemLevelDiscountField.get(1))) {
                    m_assert.assertWarn("Second Subpackage item level Discount field is displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Second Subpackage item level Discount field is not displaying as user deleted overall discount field ");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_mainDiscountFieldOnPackage.get(0))) {
                    m_assert.assertWarn("Package level Discount field is  displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Package level Discount field is not displaying as user deleted overall discount field ");
                }
                Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(0));
                Cls_Generic_Methods.customWait(4);
                String sUnitCostOnFirstItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemUnitCost.get(0));
                String sNoOfUnitsCostPerDayOnUI = sUnitCostOnFirstItemUI.split(" ")[0];
                Double dNoOfUnitsCostPerDayOnUI = Double.parseDouble(sNoOfUnitsCostPerDayOnUI);
                // String sUnitCostOfFirstItem = sUnitCostOnFirstItemUI.split("\\(")[0];
                String sNoOfUnitsOrDays = sUnitCostOnFirstItemUI.split("\\(")[1];
                String sNoUnitsPerDayForFirstItem = sNoOfUnitsOrDays.split(" ")[0];
                Double dNoUnitsPerDayForFirstItem = Double.parseDouble(sNoUnitsPerDayForFirstItem);

                String sGrossSubPackageItemPriceForFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(0));
                String sNetPriceItemPriceForFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                Cls_Generic_Methods.customWait(4);
                double dNetPriceOfFirstItemBeforeDiscount = Double.parseDouble(sNetPriceItemPriceForFirstItem);
                double dGrossPriceOfFirstItemBeforeDiscount = Double.parseDouble(sGrossSubPackageItemPriceForFirstItem);
                double dCalculatedGrossPriceAndNetPriceForFirstItem = dNoOfUnitsCostPerDayOnUI * dNoUnitsPerDayForFirstItem;
                if (dCalculatedGrossPriceAndNetPriceForFirstItem == dNetPriceOfFirstItemBeforeDiscount && dCalculatedGrossPriceAndNetPriceForFirstItem == dGrossPriceOfFirstItemBeforeDiscount) {
                    m_assert.assertTrue("Calculated gross price for first package level item is equal to net price and gross price on UI, Before applying a discount = <b>  " + dNetPriceOfFirstItemBeforeDiscount + "</b>");
                } else {
                    m_assert.assertWarn("Calculated gross price first package level item is not equal to net price on UI and gross price on UI, Before applying a discount");
                }

                String sUnitCostOnSecondItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemUnitCost.get(1));
                // String sUnitCostOfSecondItem = sUnitCostOnSecondItemUI.split("\\(")[0];
                String sNoOfUnitsOrDaysOnUIForSecondItem = sUnitCostOnSecondItemUI.split(" ")[0];
                Double dNoOfUnitsOrDaysOnUIForSecondItem = Double.parseDouble(sNoOfUnitsOrDaysOnUIForSecondItem);
                String sNoOfUnitsOrDaysForSecondItem = sUnitCostOnSecondItemUI.split("\\(")[1];
                String sNoUnitsPerDayForSecondItems = sNoOfUnitsOrDaysForSecondItem.split(" ")[0];
                Double dNoUnitsPerDayForSecondItems = Double.parseDouble(sNoUnitsPerDayForSecondItems);
                String sGrossSubPackageItemPriceForSecondItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(1));
                String sNetPriceItemPriceForSecondItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dGrossSubPackageItemPriceForSecondItem = Double.parseDouble(sGrossSubPackageItemPriceForSecondItem);
                double dNetPriceItemPriceForSecondItem = Double.parseDouble(sNetPriceItemPriceForSecondItem);
                double dCalculatedGrossPriceAndNetPriceForSecondItem = dNoOfUnitsOrDaysOnUIForSecondItem * dNoUnitsPerDayForSecondItems;
                if (dCalculatedGrossPriceAndNetPriceForSecondItem == dNetPriceItemPriceForSecondItem && dCalculatedGrossPriceAndNetPriceForSecondItem == dGrossSubPackageItemPriceForSecondItem) {
                    m_assert.assertTrue("Calculated gross price for Second package level item is equal to net price and gross price on UI, Before applying a discount<b> " + dNetPriceItemPriceForSecondItem + "</b>");
                } else {
                    m_assert.assertWarn("Calculated gross price second package level item is not equal to net price on UI and gross price on UI, Before applying a discount");
                }
                double dSumOfFistAndSecondSubpackageItemsNetPrice = dNetPriceItemPriceForSecondItem + dNetPriceOfFirstItemBeforeDiscount;
                if (dSumOfFistAndSecondSubpackageItemsNetPrice == dOverAllNetPriceOnPackage) {
                    m_assert.assertTrue("summation of first and second subpackage level items net price is equal to overall subpackage net price, Before applying a discount<b>  " + dOverAllNetPriceOnPackage + "</b>");
                } else {
                    m_assert.assertWarn("summation of first and second subpackage level items net price is equal to overall subpackage net price, Before applying a discount");
                }
                // Apply discount on each subPackage item
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(0));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(0), sDiscountAmountOnFirstSubPackageItem), "Applied Sub package item level discount for first item = <b> " + sDiscountAmountOnFirstSubPackageItem + "</b>");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(1));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.list_subpackageItemLevelDiscountTypes.get(1), "%"), "Discount type for second subpackage level item =  <b>%</b>");
                Cls_Generic_Methods.customWait(4);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(1), sDiscountAmountOnSecondSubPackageItem), "Applied Sub package item level discount for second item = <b> " + sDiscountAmountOnSecondSubPackageItem + "</b>");
                double dDiscountAmountOnSecondSubPackageItem = Double.parseDouble(sDiscountAmountOnSecondSubPackageItem);
                double dDiscountPercentageForSecondItem = dDiscountAmountOnSecondSubPackageItem / 100;
                double dDiscountAmountAfterApplyingDiscountInPercentageForSecondItem = dNetPriceItemPriceForSecondItem * dDiscountPercentageForSecondItem;
                double dRoundingOffTheDiscountForSecondItem = roundOffFunctionUsingDouble(dDiscountAmountAfterApplyingDiscountInPercentageForSecondItem);
                //Multiplying with minus as we are showing discount value in negative
                double dRoundingOffTheDiscountOnSecondItem = dRoundingOffTheDiscountForSecondItem * (-1);
                String sFirstSubpackageItemDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dFirstSubpackageItemDiscountAmount = Double.parseDouble(sFirstSubpackageItemDiscountAmount);
                double dNetPriceOfFirstItemAfterDiscount = dNetPriceOfFirstItemBeforeDiscount + dFirstSubpackageItemDiscountAmount;
                String sNetAmountAfterDiscountForFirstItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                double dNetAmountAfterDiscountForFirstItemOnUI = Double.parseDouble(sNetAmountAfterDiscountForFirstItemOnUI);
                if (dNetPriceOfFirstItemAfterDiscount == dNetAmountAfterDiscountForFirstItemOnUI) {
                    m_assert.assertTrue("Net price amount for first subpackage item after applying Discount =  <b>" + dNetAmountAfterDiscountForFirstItemOnUI + "</b>");
                } else {
                    m_assert.assertWarn("Calculated Net price amount for first subpackage item after applying Discount is not matching with UI net price");
                }
                String sSecondSubpackageItemDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(1));
                double dSecondSubpackageItemDiscountAmount = Double.parseDouble(sSecondSubpackageItemDiscountAmount);
                double dNetPriceOfSecondItemAfterDiscount = dNetPriceItemPriceForSecondItem + dSecondSubpackageItemDiscountAmount;
                String sNetAmountAfterDiscountForSecondItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dNetAmountAfterDiscountForSecondItemOnUI = Double.parseDouble(sNetAmountAfterDiscountForSecondItemOnUI);
                if (dRoundingOffTheDiscountOnSecondItem == dSecondSubpackageItemDiscountAmount) {
                    m_assert.assertTrue("Discount amount for second subpackage item on UI is matched with calculated Discount amount= <b>" + dSecondSubpackageItemDiscountAmount + "</b>");
                } else {
                    m_assert.assertWarn("Discount amount for second subpackage item on UI is not matching with calculated Discount amount");
                }
                if (dNetPriceOfSecondItemAfterDiscount == dNetAmountAfterDiscountForSecondItemOnUI) {
                    m_assert.assertTrue("Net price amount for second subpackage item after applying Discount =  <b>" + dNetAmountAfterDiscountForFirstItemOnUI + "</b>");
                } else {
                    m_assert.assertWarn("Calculated Net price  for second subpackage item after applying Discount is not matching with UI net price");
                }
                // summation subpackage item level discount
                double dTotalDiscountAmountOnOverAllSubpackage = dFirstSubpackageItemDiscountAmount + dSecondSubpackageItemDiscountAmount;
                String sOverAllDiscountOnPackage = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_overallDiscountAmountOnPackage);
                String sOverAllDiscountPackage = sOverAllDiscountOnPackage.replaceAll("\\s", "");
                double dOverAllDiscountOnPackage = Double.parseDouble(sOverAllDiscountPackage);
                if (dOverAllDiscountOnPackage == dTotalDiscountAmountOnOverAllSubpackage) {
                    m_assert.assertTrue("Subpackage item level discounts are matching with overall subpackage discount field =  <b>" + dTotalDiscountAmountOnOverAllSubpackage + "</b>");
                } else {
                    m_assert.assertWarn("Subpackage item level discounts are not matching with overall subpackage discount field");
                }
                //Over all package net price after discount
                double dNetAmountOnOverAllPackageAfterApplyingDiscount = dOverAllNetPriceOnPackage + dTotalDiscountAmountOnOverAllSubpackage;
                String sNetAmtAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetAmtAfterDiscount = Double.parseDouble(sNetAmtAfterDiscount);
                m_assert.assertTrue((dNetAmountOnOverAllPackageAfterApplyingDiscount == dNetAmtAfterDiscount), "Calculated Net Amount after discount matching with Net amount on UI <b>" + dNetAmtAfterDiscount + "</b>");

                // delete all subpackage item wise discounts
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_deleteSubPackageItemsDiscount.get(1)), "clicked on delete subpackage item level discount button");
                String sSecondSubPackageNetAmountAfterDeletingItemLevelDiscount = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dSecondSubPackageNetAmountAfterDeletingItemLevelDiscount = Double.parseDouble(sSecondSubPackageNetAmountAfterDeletingItemLevelDiscount);
                m_assert.assertTrue((dSecondSubPackageNetAmountAfterDeletingItemLevelDiscount == dCalculatedGrossPriceAndNetPriceForSecondItem), "Upon deleting a second subpackage level item discount calculated Net Amount is matching with Net amount on UI <b>" + dSecondSubPackageNetAmountAfterDeletingItemLevelDiscount + "</b>");

                //net price of first sub package level item after discount
                String sFirstSubpackageItemDiscountAfterRemovingSecondItemsDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dFirstSubpackageItemAfterRemovingSecondItemsDiscountAmount = Double.parseDouble(sFirstSubpackageItemDiscountAfterRemovingSecondItemsDiscountAmount);
                double dNetPriceOfFirstItemAfterRemovingSecondItemsDiscount = dNetPriceOfFirstItemBeforeDiscount + dFirstSubpackageItemAfterRemovingSecondItemsDiscountAmount;
                String sNetAmountAfterDiscountOnFirstItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                double dNetAmountAfterDiscountOnFirstItemOnUI = Double.parseDouble(sNetAmountAfterDiscountOnFirstItemOnUI);
                m_assert.assertTrue((dNetAmountAfterDiscountOnFirstItemOnUI == dNetPriceOfFirstItemAfterRemovingSecondItemsDiscount), "Upon deleting a second subpackage level discount calculated Net Amount of second item is matching with Net amount on UI of second item <b>" + dNetPriceOfFirstItemAfterRemovingSecondItemsDiscount + "</b>");
                //Over all package net price after deleting second items discount
                double dCalculatedNetAmountAfterRemovingSecondItemsDiscount = dNetAmountOnOverAllPackageAfterApplyingDiscount + dRoundingOffTheDiscountForSecondItem;
                String sNetAmtAfterRemovingSecondItemDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetAmtAfterRemovingSecondItemDiscount = Double.parseDouble(sNetAmtAfterRemovingSecondItemDiscount);
                m_assert.assertTrue((dCalculatedNetAmountAfterRemovingSecondItemsDiscount == dNetAmtAfterRemovingSecondItemDiscount), "Calculated Net Amount after removing second items discount is matching with Net amount on UI <b>" + dCalculatedNetAmountAfterRemovingSecondItemsDiscount + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Bills.button_addSubPackageItemsDiscount.get(1));
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmount.get(0), sGlobalDiscount), "Entered amount in Global discount field = <b>" + sGlobalDiscount + "</b>");
                // m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);
                double dGlobalDiscount = Double.parseDouble(sGlobalDiscount);
                double dGlobalDiscountPercentage = dGlobalDiscount / dOverAllNetPriceOnPackage;

                double dDiscountOnFirstItemAfterApplyingGlobalDiscount = dGlobalDiscountPercentage * dGrossPriceOfFirstItemBeforeDiscount;
                double dDiscountOnSecondItemAfterApplyingGlobalDiscount = dGlobalDiscountPercentage * dGrossSubPackageItemPriceForSecondItem;
                double dSummationOfSubpackageItemWiseDiscount = dDiscountOnFirstItemAfterApplyingGlobalDiscount + dDiscountOnSecondItemAfterApplyingGlobalDiscount;
                double dRoundOffSubpackageItemWiseDiscount = roundOffFunctionUsingDouble(dSummationOfSubpackageItemWiseDiscount);
                double dNetAmountOfFirstSubpackageItemAfterGlobalDiscount = dGrossPriceOfFirstItemBeforeDiscount - dDiscountOnFirstItemAfterApplyingGlobalDiscount;
                double dNetAmountOfSecondSubpackageItemAfterGlobalDiscount = dGrossSubPackageItemPriceForSecondItem - dDiscountOnSecondItemAfterApplyingGlobalDiscount;
                m_assert.assertTrue((dRoundOffSubpackageItemWiseDiscount == dGlobalDiscount), "Subpackage item wise discounts sum is equal to Global discount amount <b>" + dGlobalDiscount + "</b>");
                Cls_Generic_Methods.customWait(3);
                //Apply GD in percentage
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_globalDiscountType, "%"), "Discount type for GD =  <b>%</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmount.get(0), sGlobalDiscount), "Entered amount in Global discount field = <b>" + sGlobalDiscount + "</b>");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_discountReason.get(0), sDiscountReason);
                double dGlobalDiscountInPercentage = dGlobalDiscount / 100;
                double dDiscountOnFirstItemAfterApplyingGlobalDiscountInPercentage = dGlobalDiscountInPercentage * dGrossPriceOfFirstItemBeforeDiscount;
                double dDiscountOnSecondItemAfterApplyingGlobalDiscountInPercentage = dGlobalDiscountInPercentage * dGrossSubPackageItemPriceForSecondItem;
                String sDiscount1 = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0));
                double dDiscount1 = Double.parseDouble(sDiscount1);
                double dFirstSubpackageDiscountOnUI = dDiscount1 * (-1);
                String sDiscount2 = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(1));
                double dDiscount2 = Double.parseDouble(sDiscount2);
                double dSecondSubpackageDiscountOnUI = dDiscount2 * (-1);
                m_assert.assertTrue((dFirstSubpackageDiscountOnUI == dDiscountOnFirstItemAfterApplyingGlobalDiscountInPercentage && dSecondSubpackageDiscountOnUI == dDiscountOnSecondItemAfterApplyingGlobalDiscountInPercentage), "Discount amount is matching with calculated subpackage item level discounts for subpackage 1st item discount: <b>" + dFirstSubpackageDiscountOnUI + "</b> 2nd item discount: <b>" + dSecondSubpackageDiscountOnUI);
                double dSummationOfSubpackageItemWiseDiscountInPercentage = dFirstSubpackageDiscountOnUI + dSecondSubpackageDiscountOnUI;
                double dNetAmountOfFirstSubpackageItemAfterGlobalDiscountInPercentage = dGrossPriceOfFirstItemBeforeDiscount - dFirstSubpackageDiscountOnUI;
                double dNetAmountOfSecondSubpackageItemAfterGlobalDiscountInPercentage = dGrossSubPackageItemPriceForSecondItem - dSecondSubpackageDiscountOnUI;
                String sNetAmountForFirstSubItem = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetAmountForFirstSubItem = Double.parseDouble(sNetAmountForFirstSubItem);
                double dSumNetAmountAfterGD = dNetAmountOfFirstSubpackageItemAfterGlobalDiscountInPercentage + dNetAmountOfSecondSubpackageItemAfterGlobalDiscountInPercentage;
                m_assert.assertTrue((dNetAmountForFirstSubItem == dSumNetAmountAfterGD), "Net amount on UI equals calculated net amount after applying global discount <b>" + dSumNetAmountAfterGD + "</b>");
                Cls_Generic_Methods.customWait(3);
                String sNetAmountOnBillAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_netBillTotal, "value");
                double dNetAmountOnBillAfterApplyingGD = Double.parseDouble(sNetAmountOnBillAfterApplyingGD);
                String sGrossBillTotalOnBillAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_grossBillTotal, "value");
                double dGrossBillTotalOnBillAfterApplyingGD = Double.parseDouble(sGrossBillTotalOnBillAfterApplyingGD);
                String sDiscountOnItemsAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnItem, "value");
                double dDiscountOnItemsAfterApplyingGD = Double.parseDouble(sDiscountOnItemsAfterApplyingGD);
                m_assert.assertTrue((dNetAmountOnBillAfterApplyingGD == dSumNetAmountAfterGD && dGrossBillTotalOnBillAfterApplyingGD == dGrossPrice && dDiscountOnItemsAfterApplyingGD == dSummationOfSubpackageItemWiseDiscountInPercentage), "Net amount on bill = <b>" + dNetAmountOnBillAfterApplyingGD + "</b> , Gross amount on bill =  <b>" + dGrossBillTotalOnBillAfterApplyingGD + "</b> , Discount on item = <b> " + dDiscountOnItemsAfterApplyingGD + "</b>");
                Cls_Generic_Methods.customWait(4);
                String sTotalDiscountOnItemsAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnBill, "value");
                double dTotalDiscountOnItemsAfterApplyingGD = Double.parseDouble(sTotalDiscountOnItemsAfterApplyingGD);
                String sTotalOfAllDiscountOnItemsAfterApplyingGD = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalOfAllDiscounts, "value");
                double dTotalOfAllDiscountOnItemsAfterApplyingGD = Double.parseDouble(sTotalOfAllDiscountOnItemsAfterApplyingGD);
                m_assert.assertTrue((dTotalDiscountOnItemsAfterApplyingGD == dTotalOfAllDiscountOnItemsAfterApplyingGD), "Total discount and total of all discount fields, values matching on UI = <b>" + dTotalOfAllDiscountOnItemsAfterApplyingGD + "</b>");

                String sSecondPackageName = "TestingTemplateBills";
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickIPDPlusActionButton, 30);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickIPDPlusActionButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Bills.button_newPackage);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.select_package.get(0));
                Cls_Generic_Methods.customWait(3);
                boolean PackageFound = false;
                for (WebElement eItemList : oPage_Bills.list_packageOrServiceNameUnderDescriptionField) {
                    if (Cls_Generic_Methods.getTextInElement(eItemList).equals(sSecondPackageName)) {
                        Cls_Generic_Methods.clickElement(eItemList);
                        PackageFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(PackageFound, "Selected package name = <b>" + sSecondPackageName + "</b> ");


                Cls_Generic_Methods.customWait(4);
                String sQTYForSecondPackage = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_Quantity.get(1), "value");
                String sUnitPriceForSecondPackage = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(1), "value");
                String sGrossPriceForSecondPackage = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(1), "value");
                dGrossPrice = Double.parseDouble(sGrossPriceForSecondPackage);
                String sNetPriceForSecondPackage = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value");
                double dOverAllNetPriceOnPackageForSecondPackage = Double.parseDouble(sNetPriceForSecondPackage);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(1));
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_deleteItemLevelDiscount.get(1));
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_subpackageItemLevelDiscountField.get(2))) {
                    m_assert.assertWarn("Second package: First Subpackage item level Discount field is  displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Second package: First Subpackage item level Discount field is not displaying as user deleted overall discount field ");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_subpackageItemLevelDiscountField.get(3))) {
                    m_assert.assertWarn("Second package: Second Subpackage item level Discount field is displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Second package: Second Subpackage item level Discount field is not displaying as user deleted overall discount field ");
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Bills.list_mainDiscountFieldOnPackage.get(1))) {
                    m_assert.assertWarn("Second package: Package level Discount field is  displaying even after user deleted overall discount field ");
                } else {
                    m_assert.assertTrue("Second package: Package item level Discount field is not displaying as user deleted overall discount field ");
                }
                Cls_Generic_Methods.clickElement(oPage_Bills.input_itemWiseDiscountButton.get(1));
                Cls_Generic_Methods.customWait(4);
                String sUnitCostOnFirstSubpackageItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemUnitCost.get(2));
                String sNoOfUnitsCostPerDayOnUIForFirstSubpackage = sUnitCostOnFirstSubpackageItemUI.split(" ")[0];
                Double dNoOfUnitsCostPerDayOnFirstSubpackageOnUI = Double.parseDouble(sNoOfUnitsCostPerDayOnUIForFirstSubpackage);
                // String sUnitCostOfFirstItem = sUnitCostOnFirstItemUI.split("\\(")[0];
                String sNoOfUnitsOrDaysForFirstSubpacakge = sUnitCostOnFirstSubpackageItemUI.split("\\(")[1];
                String sNoUnitsPerDayForFirstSubPackageItem = sNoOfUnitsOrDaysForFirstSubpacakge.split(" ")[0];
                Double dNoUnitsPerDayForFirstSubpackageItem = Double.parseDouble(sNoUnitsPerDayForFirstSubPackageItem);

                String sGrossSubPackageItemPriceONFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(2));
                String sNetPriceItemPriceOnFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(2));
                Cls_Generic_Methods.customWait(4);
                double dNetPriceOfFirstItemBeforeApplyingDiscount = Double.parseDouble(sNetPriceItemPriceOnFirstItem);
                double dGrossPriceOfFirstItemBeforeApplyingDiscount = Double.parseDouble(sGrossSubPackageItemPriceONFirstItem);
                double dCalculatedGrossPriceAndNetPriceOnFirstItem = dNoOfUnitsCostPerDayOnFirstSubpackageOnUI * dNoUnitsPerDayForFirstSubpackageItem;
                if (dCalculatedGrossPriceAndNetPriceOnFirstItem == dNetPriceOfFirstItemBeforeApplyingDiscount && dCalculatedGrossPriceAndNetPriceOnFirstItem == dGrossPriceOfFirstItemBeforeApplyingDiscount) {
                    m_assert.assertTrue("Calculated gross price for first package level item is equal to net price and gross price on UI, Before applying a discount = <b>  " + dNetPriceOfFirstItemBeforeApplyingDiscount + "</b>");
                } else {
                    m_assert.assertWarn("Calculated gross price first package level item is not equal to net price on UI and gross price on UI, Before applying a discount");
                }

                String sUnitCostOnSecondSubPackageItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemUnitCost.get(3));
                // String sUnitCostOfSecondItem = sUnitCostOnSecondItemUI.split("\\(")[0];
                String sNoOfUnitsOrDaysOnUIForSecondSubPackageItem = sUnitCostOnSecondSubPackageItemUI.split(" ")[0];
                Double dNoOfUnitsOrDaysOnUIForSecondSubPackageItem = Double.parseDouble(sNoOfUnitsOrDaysOnUIForSecondSubPackageItem);
                String sNoOfUnitsOrDaysForSecondSubPackageItem = sUnitCostOnSecondSubPackageItemUI.split("\\(")[1];
                String sNoUnitsPerDayForSecondSubPackageItems = sNoOfUnitsOrDaysForSecondSubPackageItem.split(" ")[0];
                Double dNoUnitsPerDayForSecondSubPackageItems = Double.parseDouble(sNoUnitsPerDayForSecondSubPackageItems);
                String sGrossSubPackageItemPriceForSecondSubPackageItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(3));
                String sNetPriceItemPriceForSecondSubPackageItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(3));
                double dGrossSubPackageItemPriceForSecondSubPackageItem = Double.parseDouble(sGrossSubPackageItemPriceForSecondSubPackageItem);
                double dNetPriceItemPriceForSecondSubPackageItem = Double.parseDouble(sNetPriceItemPriceForSecondSubPackageItem);
                double dCalculatedGrossPriceAndNetPriceForSecondSubPackageItem = dNoOfUnitsOrDaysOnUIForSecondSubPackageItem * dNoUnitsPerDayForSecondSubPackageItems;
                if (dCalculatedGrossPriceAndNetPriceForSecondSubPackageItem == dNetPriceItemPriceForSecondSubPackageItem && dCalculatedGrossPriceAndNetPriceForSecondSubPackageItem == dGrossSubPackageItemPriceForSecondSubPackageItem) {
                    m_assert.assertTrue("Calculated gross price for Second package level item is equal to net price and gross price on UI, Before applying a discount<b> " + dNetPriceItemPriceForSecondSubPackageItem + "</b>");
                } else {
                    m_assert.assertWarn("Calculated gross price second package level item is not equal to net price on UI and gross price on UI, Before applying a discount");
                }
                double dSumOfFistAndSecondSubpackageItemNetPrice = dNetPriceItemPriceForSecondSubPackageItem + dNetPriceOfFirstItemBeforeApplyingDiscount;
                if (dSumOfFistAndSecondSubpackageItemNetPrice == dOverAllNetPriceOnPackageForSecondPackage) {
                    m_assert.assertTrue("summation of first and second subpackage level items net price is equal to overall subpackage net price, Before applying a discount<b>  " + dOverAllNetPriceOnPackageForSecondPackage + "</b>");
                } else {
                    m_assert.assertWarn("summation of first and second subpackage level items net price is equal to overall subpackage net price, Before applying a discount");
                }
                // Apply discount on each subPackage item
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(2));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(2), sDiscountAmountOnFirstSubPackageItem), "Applied Sub package item level discount for first item = <b> " + sDiscountAmountOnFirstSubPackageItem + "</b>");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(3));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.list_subpackageItemLevelDiscountTypes.get(3), "%"), "Discount type for second subpackage level item =  <b>%</b>");
                Cls_Generic_Methods.customWait(4);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.list_subpackageItemLevelDiscountField.get(3), sDiscountAmountOnSecondSubPackageItem), "Applied Sub package item level discount for second item = <b> " + sDiscountAmountOnSecondSubPackageItem + "</b>");
                double dDiscountAmountForSecondSubPackageItem = Double.parseDouble(sDiscountAmountOnSecondSubPackageItem);
                double dDiscountPercentageONSecondItem = dDiscountAmountForSecondSubPackageItem / 100;
                double dDiscountAmountAfterApplyingDiscountonPercentageForSecondItem = dNetPriceItemPriceForSecondSubPackageItem * dDiscountPercentageONSecondItem;
                double dRoundingOffTheDiscountAmountForSecondItem = roundOffFunctionUsingDouble(dDiscountAmountAfterApplyingDiscountonPercentageForSecondItem);
                //Multiplying with minus as we are showing discount value in negative
                double dRoundOffTheDiscountOnSecondItem = dRoundingOffTheDiscountAmountForSecondItem * (-1);
                String sFirstSubpackageItemsDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(2));
                double dFirstSubpackageItemsDiscountAmount = Double.parseDouble(sFirstSubpackageItemsDiscountAmount);
                double dNetPriceOfTheFirstItemAfterDiscount = dNetPriceOfFirstItemBeforeApplyingDiscount + dFirstSubpackageItemsDiscountAmount;
                String sNetAmountAfterApplyingDiscountForFirstItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(2));
                double dNetAmountAfterApplyingDiscountForFirstItemOnUI = Double.parseDouble(sNetAmountAfterApplyingDiscountForFirstItemOnUI);
                if (dNetPriceOfTheFirstItemAfterDiscount == dNetAmountAfterApplyingDiscountForFirstItemOnUI) {
                    m_assert.assertTrue("Net price amount for first subpackage item after applying Discount =  <b>" + dNetAmountAfterApplyingDiscountForFirstItemOnUI + "</b>");
                } else {
                    m_assert.assertWarn("Calculated Net price amount for first subpackage item after applying Discount is not matching with UI net price");
                }
                String sSecondSubpackageItemsDiscountAmount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(3));
                double dSecondSubpackageItemsDiscountAmount = Double.parseDouble(sSecondSubpackageItemsDiscountAmount);
                double dNetPriceOfSecondItemsAfterDiscount = dNetPriceItemPriceForSecondSubPackageItem + dSecondSubpackageItemsDiscountAmount;
                String sNetAmountAfterDiscountOnSecondItemOnUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(3));
                double dNetAmountAfterDiscountOnSecondItemOnUI = Double.parseDouble(sNetAmountAfterDiscountOnSecondItemOnUI);
                if (dRoundOffTheDiscountOnSecondItem == dSecondSubpackageItemsDiscountAmount) {
                    m_assert.assertTrue("Discount amount for second subpackage item on UI is matched with calculated Discount amount= <b>" + dSecondSubpackageItemsDiscountAmount + "</b>");
                } else {
                    m_assert.assertWarn("Discount amount for second subpackage item on UI is not matching with calculated Discount amount");
                }
                if (dNetPriceOfSecondItemsAfterDiscount == dNetAmountAfterDiscountOnSecondItemOnUI) {
                    m_assert.assertTrue("Net price amount for second subpackage item after applying Discount =  <b>" + dNetAmountAfterDiscountOnSecondItemOnUI + "</b>");
                } else {
                    m_assert.assertWarn("Calculated Net price  for second subpackage item after applying Discount is not matching with UI net price");
                }
                // summation subpackage item level discount
                double dTotalDiscountAmountForAllSubpackage = dFirstSubpackageItemsDiscountAmount + dSecondSubpackageItemsDiscountAmount;
                String sOverAllDiscountForSecondPackage = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_overallDiscountOnPackage.get(1));
                String sOverAllDiscountSecondPackage = sOverAllDiscountForSecondPackage.replaceAll("\\s", "");
                double dOverAllDiscountOnSecondPackage = Double.parseDouble(sOverAllDiscountSecondPackage);
                if (dOverAllDiscountOnSecondPackage == dTotalDiscountAmountForAllSubpackage) {
                    m_assert.assertTrue("Subpackage item level discounts are matching with overall subpackage discount field =  <b>" + dTotalDiscountAmountForAllSubpackage + "</b>");
                } else {
                    m_assert.assertWarn("Subpackage item level discounts are not matching with overall subpackage discount field");
                }
                //Over all package net price after discount
                double dOverAllNetAmountOnSecondPackageAfterApplyingDiscount = dOverAllNetPriceOnPackageForSecondPackage + dTotalDiscountAmountForAllSubpackage;
                String sNetAmtAfterApplyingDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value");
                double dNetAmtAfterApplyingDiscount = Double.parseDouble(sNetAmtAfterApplyingDiscount);
                m_assert.assertTrue((dOverAllNetAmountOnSecondPackageAfterApplyingDiscount == dNetAmtAfterApplyingDiscount), "Calculated Net Amount after discount matching with Net amount on UI <b>" + dNetAmtAfterApplyingDiscount + "</b>");

                // delete all subpackage item wise discounts
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_deleteSubPackageItemsDiscount.get(3)), "clicked on delete subpackage item level discount button");
                String sSecondSubPackageNetAmountAfterDeletingItemLevelDisc = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(3));
                double dSecondSubPackageNetAmountAfterDeletingItemLevelDisc = Double.parseDouble(sSecondSubPackageNetAmountAfterDeletingItemLevelDisc);
                m_assert.assertTrue((dSecondSubPackageNetAmountAfterDeletingItemLevelDisc == dCalculatedGrossPriceAndNetPriceForSecondSubPackageItem), "Upon deleting a second subpackage level item discount calculated Net Amount is matching with Net amount on UI <b>" + dSecondSubPackageNetAmountAfterDeletingItemLevelDisc + "</b>");

                //net price of first sub package level item after discount
                String sFirstSubpackageItemDiscountAfterRemovingSecondItemsDiscount = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(2));
                double dFirstSubpackageItemAfterRemovingSecondItemsDiscount = Double.parseDouble(sFirstSubpackageItemDiscountAfterRemovingSecondItemsDiscount);
                double dNetPriceOfFirstItemAfterRemovingSecondItemDiscount = dNetPriceOfFirstItemBeforeApplyingDiscount + dFirstSubpackageItemAfterRemovingSecondItemsDiscount;
                String sNetAmountAfterDiscountOnFirstItemUI = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(2));
                double dNetAmountAfterDiscountOnFirstItemUI = Double.parseDouble(sNetAmountAfterDiscountOnFirstItemUI);
                m_assert.assertTrue((dNetAmountAfterDiscountOnFirstItemUI == dNetPriceOfFirstItemAfterRemovingSecondItemDiscount), "Upon deleting a second subpackage level discount calculated Net Amount of second item is matching with Net amount on UI of second item <b>" + dNetPriceOfFirstItemAfterRemovingSecondItemDiscount + "</b>");
                //Over all package net price after deleting second items discount
                double dCalculatedNetAmountAfterRemovingSecondItemDiscount = dOverAllNetAmountOnSecondPackageAfterApplyingDiscount + dRoundingOffTheDiscountAmountForSecondItem;
                String sNetAmtAfterRemovingSecondItemsDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value");
                double dNetAmtAfterRemovingSecondItemsDiscount = Double.parseDouble(sNetAmtAfterRemovingSecondItemsDiscount);
                m_assert.assertTrue((dCalculatedNetAmountAfterRemovingSecondItemDiscount == dNetAmtAfterRemovingSecondItemsDiscount), "Calculated Net Amount after removing second items discount is matching with Net amount on UI <b>" + dCalculatedNetAmountAfterRemovingSecondItemDiscount + "</b>");
                Cls_Generic_Methods.clickElement(oPage_Bills.button_addSubPackageItemsDiscount.get(3));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmount.get(1), sGlobalDiscount), "Entered amount in Global discount field = <b>" + sGlobalDiscount + "</b>");
                // m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_applyGlobalDiscount), "Clicked on apply global discount button");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_receiptTemplateGlobalDiscount), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);
                double dGlobalDiscountForSecondPackage = Double.parseDouble(sGlobalDiscount);
                double dGlobalDiscountPercentageForSecondPackage = dGlobalDiscountForSecondPackage / dOverAllNetPriceOnPackageForSecondPackage;
                double dDiscountOnFirstItemAfterGlobalDiscount = dGlobalDiscountPercentageForSecondPackage * dGrossPriceOfFirstItemBeforeApplyingDiscount;
                double dRoundingOffTheDiscountOnFirstItem = roundOffFunctionUsingDouble(dDiscountOnFirstItemAfterGlobalDiscount);
                double dDiscountOnSecondItemAfterGlobalDiscount = dGlobalDiscountPercentageForSecondPackage * dGrossSubPackageItemPriceForSecondSubPackageItem;
                double dRoundingOffTheDiscountOnForSecondItem = roundOffFunctionUsingDouble(dDiscountOnSecondItemAfterGlobalDiscount);
                double dSummationOfSubpackageItemDiscount = dRoundingOffTheDiscountOnFirstItem + dRoundingOffTheDiscountOnForSecondItem;

                double dNetAmountOfFirstSubpackageItemAfterApplyingGlobalDiscount = dGrossPriceOfFirstItemBeforeApplyingDiscount - dDiscountOnFirstItemAfterGlobalDiscount;
                double dNetAmountOfSecondSubpackageItemAfterApplyingGlobalDiscount = dGrossSubPackageItemPriceForSecondSubPackageItem - dDiscountOnSecondItemAfterGlobalDiscount;
                m_assert.assertTrue((dSummationOfSubpackageItemDiscount == dGlobalDiscountForSecondPackage), "Subpackage item wise discounts sum is equal to Global discount amount <b>" + dGlobalDiscountForSecondPackage + "</b>");
                Cls_Generic_Methods.customWait(3);
                double dSumOfFirstAndSecondSubPackageItemNetAmount = dNetAmountOfFirstSubpackageItemAfterApplyingGlobalDiscount + dNetAmountOfSecondSubpackageItemAfterApplyingGlobalDiscount;
                double dRoundingOffTheNetAmountForSecondItem = roundOffFunctionUsingDouble(dSumOfFirstAndSecondSubPackageItemNetAmount);
                double dOverAllBillNetPrice = dNetAmountOnBillAfterApplyingGD + dRoundingOffTheNetAmountForSecondItem;
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_discountReason.get(1), sDiscountReason);
                Cls_Generic_Methods.clickElement(oPage_IPD.button_removePaymentReceivedDetailsUnderBills);
                Cls_Generic_Methods.customWait();
                String sAmountRemainingOnUI = Cls_Generic_Methods.getElementAttribute(oPage_IPD.input_amountRemainingField, "value");
                double dAmountRemainingOnUI = Double.parseDouble(sAmountRemainingOnUI);
                m_assert.assertTrue((dAmountRemainingOnUI == dOverAllBillNetPrice), "Net amount on bill equals to amount remaining against bill is matching = <b>" + dAmountRemainingOnUI + "</b>");
                Cls_Generic_Methods.clearValuesInElement(oPage_IPD.input_amountPendingField);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.sendKeysIntoElement(oPage_IPD.input_amountPendingField, sAmountRemainingOnUI);
                Cls_Generic_Methods.customWait(2);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_saveRecieptTemplate), "<b>Cash bill is created </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.customWait(4);
                //bill preview validation

                String sFirstSubpackageItemDiscountOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(0));
                dFirstSubpackageItemDiscountOnBillPreview = Double.parseDouble(sFirstSubpackageItemDiscountOnBillPreview);

                String sSecondSubpackageItemDiscountOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(1));
                dSecondSubpackageItemDiscountOnBillPreview = Double.parseDouble(sSecondSubpackageItemDiscountOnBillPreview);
                m_assert.assertTrue((dFirstSubpackageItemDiscountOnBillPreview == dDiscountOnFirstItemAfterApplyingGlobalDiscountInPercentage && dSecondSubpackageItemDiscountOnBillPreview == dDiscountOnSecondItemAfterApplyingGlobalDiscountInPercentage), "Discount amount is matching with calculated subpackage item level discounts 1st item1 on preview : <b>" + dFirstSubpackageItemDiscountOnBillPreview + "</b> 2nd item discount on bill preview : <b>" + dSecondSubpackageItemDiscountOnBillPreview);
                String sOverDiscountAmountOnPackageOnPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(0));
                String sOverDiscountAmountOnPackageInPreview = sOverDiscountAmountOnPackageOnPreview.replaceAll("\\s", "");
                double dOverDiscountAmountOnPackagePreview = Double.parseDouble(sOverDiscountAmountOnPackageInPreview);
                dOverDiscountAmountOnPackageInPreview = dOverDiscountAmountOnPackagePreview * (-1);
                m_assert.assertTrue((dTotalDiscountOnItemsAfterApplyingGD == dOverDiscountAmountOnPackageInPreview), "OverAll discount amount on package on bill preview = <b>" + dTotalDiscountOnItemsAfterApplyingGD + "</b>");


                String sFirstSubpackageItemDiscountOnBillPreviewForSecondPackage = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(2));
                double dFirstSubpackageItemDiscountOnBillPreviewForSecondPackage = Double.parseDouble(sFirstSubpackageItemDiscountOnBillPreviewForSecondPackage);

                String sSecondSubpackageItemDiscountOnBillPreviewForSecondPackage = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemDiscountOnBillPreview.get(3));
                double dSecondSubpackageItemDiscountOnBillPreviewForSecondPackage = Double.parseDouble(sSecondSubpackageItemDiscountOnBillPreviewForSecondPackage);
                m_assert.assertTrue((dFirstSubpackageItemDiscountOnBillPreviewForSecondPackage == dRoundingOffTheDiscountOnFirstItem && dRoundingOffTheDiscountOnForSecondItem == dSecondSubpackageItemDiscountOnBillPreviewForSecondPackage), "Discount amount is matching with calculated subpackage item level discounts 1st item1 on preview : <b>" + dRoundingOffTheDiscountOnFirstItem + "</b> 2nd item discount on bill preview : <b>" + dSecondSubpackageItemDiscountOnBillPreviewForSecondPackage);
                String sOverDiscountAmountOnSecondPackageOnPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(1));
                String sOverDiscountAmountOnSecondPackageInPreview = sOverDiscountAmountOnSecondPackageOnPreview.replaceAll("\\s", "");
                double dOverDiscountAmountOnSecondPackagePreview = Double.parseDouble(sOverDiscountAmountOnSecondPackageInPreview);
                double dOverAllDiscountOnSecondItem = dOverDiscountAmountOnSecondPackagePreview * (-1);
                m_assert.assertTrue((dRoundOffSubpackageItemWiseDiscount == dOverAllDiscountOnSecondItem), "OverAll discount amount on package on bill preview = <b>" + dRoundOffSubpackageItemWiseDiscount + "</b>");

                String sGrossBillTotalOnPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_grossBillTotalOnBillPreview);
                double dGrossBillTotalOnPreview = Double.parseDouble(sGrossBillTotalOnPreview);
                double dGrossOnBill = dGrossBillTotalOnBillAfterApplyingGD + dGrossPrice;
                m_assert.assertTrue((dGrossOnBill == dGrossBillTotalOnPreview), "Gross bill total on package on bill preview = <b>" + dGrossOnBill + "</b>");

                String sTotalDiscountOnItemsInBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalDiscountOnItemsOnBillPreview);
                double dTotalDiscountOnItemsInBillPreview = Double.parseDouble(sTotalDiscountOnItemsInBillPreview);

                String sTotalOfAllDiscountOnItemsInBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalOfAllDiscountOnBillPreview);
                double dTotalOfAllDiscountOnItemsInBillPreview = Double.parseDouble(sTotalOfAllDiscountOnItemsInBillPreview);
                double dTotalDiscountDiscountOfBothSubPackage = dTotalDiscountOnItemsAfterApplyingGD + dSummationOfSubpackageItemDiscount;
                String sPaymentReceivedOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_paymentReceivedOnBillPreview);
                String sPaymentReceivedOnBillPreviewAfterBillUpdate = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_paymentReceivedOnBillPreview);
                double dPaymentReceivedOnBillPreviewAfterBillUpdate = Double.parseDouble(sPaymentReceivedOnBillPreviewAfterBillUpdate);
                m_assert.assertTrue((dPaymentReceivedOnBillPreviewAfterBillUpdate == 0.00), "Payment Received on bill preview after bill update = <b>" + dPaymentReceivedOnBillPreviewAfterBillUpdate + "</b>");
                Cls_Generic_Methods.customWait(4);

                String sPaymentPendingOnBillPreviewAfterBillUpdate = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_paymentPendingOnBillPreview);
                double dPaymentPendingOnBillPreviewAfterBillUpdate = Double.parseDouble(sPaymentPendingOnBillPreviewAfterBillUpdate);
                double dSummationOfPaymentPending = dSumNetAmountAfterGD + dRoundingOffTheNetAmountForSecondItem;
                m_assert.assertTrue((dPaymentPendingOnBillPreviewAfterBillUpdate == dSummationOfPaymentPending), "Payment Pending on bill preview after bill update = <b>" + dSummationOfPaymentPending + "</b>");
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_closeBill);
                Cls_Generic_Methods.customWait(3);

            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }


    }

    public double roundOffFunctionUsingDouble(double d) {
        double dFinalValue = 0.00;
        try {
            DecimalFormat f = new DecimalFormat("##.00");
            dFinalValue = Double.parseDouble(f.format(d));
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }
        return dFinalValue;
    }

    public static String roundOffFunctionUsingFloat(double d) {
        String sFloatFinalValue = "0.00";
        try {
            sFloatFinalValue = String.format("%.2f", d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sFloatFinalValue;
    }
}
