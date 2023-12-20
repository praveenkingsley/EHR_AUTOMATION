package tests.departments.opd;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.IPD_Data;
import data.OPD_Data;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.bills.Page_Bills;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.ipd.Page_IPD;
import pages.opd.Page_OPD;

import java.text.DecimalFormat;

import static pages.commonElements.CommonActions.*;
import static tests.departments.opd.AddPatientsTest.patientKey;
//import static tests.departments.opd.OPDGlobalLevelDiscountTest.RoundingExample.someOtherMethod;

public class OPDGlobalLevelDiscountTest extends TestBase {
    static Model_Patient myPatient;
    Page_NewPatientRegisteration oPage_NewPatientRegisteration;

    @Test(enabled = true, description = "Create patient in OPD for global level discount validation")
    public void scheduleAdmissionInOpdForGlobalDiscount() {

        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectFacility("TST");


            try {
                CommonActions.selectDepartmentOnApp("OPD");

                // Open the Search/Add patient dialog box
                Cls_Generic_Methods.customWait(3);
                try {
                    if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                        CommonActions.openPatientRegisterationAndAppointmentForm();
                    } else {
                        CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                                "Patient Details");
                        Cls_Generic_Methods.customWait(3);
                    }
                } catch (NoSuchElementException e1) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                }
                // Entering Essential Form Data
                if (!myPatient.getsSALUTATION().isEmpty()) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                    oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()),
                            "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
                }

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                        "First Name is entered as - " + myPatient.getsFIRST_NAME());

                m_assert.assertTrue(
                        Cls_Generic_Methods.sendKeysIntoElement(
                                oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                                myPatient.getsMOBILE_NUMBER()),
                        "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());
                m_assert.assertTrue(
                        Cls_Generic_Methods.clickElementByJS(driver,
                                oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm),
                        "Validate that Create Appointment button is clicked");

                Cls_Generic_Methods.customWait(10);


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Create cash bill and validate the Global level  Discount")
    public void createCashBillAndValidateOPDGlobalLevelDiscount() {
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_Bills oPage_Bills = new Page_Bills(driver);
        Page_IPD oPage_IPD = new Page_IPD(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        boolean bPatientFound = false;
        String paymentMode = "Cash";
        String sPackageName = "AutomationOPDPack";
        String sDiscountAmountOnFirstSubPackageItem = "4";
        String sDiscountAmountOnSecondSubPackageItem = "3";
        String sGlobalDiscount = "10";
        String sDiscountReason = "Reason" + getRandomUniqueString(6);
        String sSecondPackage = "SecondPack";
        String billOptionToSelect = "New Bill";

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectDepartmentOnApp("OPD");
            myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
            m_assert.assertTrue(
                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, OPD_Data.tab_MY_QUEUE),
                    "Validate " + OPD_Data.tab_MY_QUEUE + " tab is selected");
            Cls_Generic_Methods.customWait(10);

            bPatientFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
            m_assert.assertTrue(bPatientFound, "Validate that the patient " + concatPatientFullName + " is created in OPD");

            double dFirstSubpackageItemDiscountOnBillPreview = 0;
            double dSecondSubpackageItemDiscountOnBillPreview = 0;
            double dOverDiscountAmountOnPackageInPreview = 0;
            double dGrossPrice = 0;
            double dPaymentReceivedOnBillPreview = 0;
            if (bPatientFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills), "Clicked on bills button");
                Cls_Generic_Methods.customWait(3);
                try {
                    m_assert.assertTrue(CreateBillsTest.selectOptionFromBillsList(oPage_Bills.list_billTypeSelection, billOptionToSelect),
                            "Validate " + billOptionToSelect + " bill is selected");
                    Cls_Generic_Methods.customWait(5);
                } catch (Exception e) {
                    m_assert.assertTrue(false, "Bill type is not selected" + e);
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 30);
                Cls_Generic_Methods.clickElement(oPage_Bills.select_services);
                Cls_Generic_Methods.customWait(4);
                oPage_Bills.input_billService.sendKeys(Keys.DOWN, Keys.ENTER);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickIPDPlusActionButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Bills.button_newPackage);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.select_package.get(1));
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

                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickIPDPlusActionButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Bills.button_newConsultation);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.select_package.get(2));
                boolean ConsulationSelected = false;
                for (WebElement eItemList : oPage_Bills.list_packageOrServiceNameUnderDescriptionField) {
                    Cls_Generic_Methods.clickElement(eItemList);
                    ConsulationSelected = true;
                }
                m_assert.assertTrue(ConsulationSelected, "Selected Consultation");
                Cls_Generic_Methods.customWait(4);
                //getting values from ui for service,Package,consultation,net and gross total
                String sUnitPriceServiceUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(0), "value");
                String sGrossPriceServiceUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(0), "value");
                double dGrossPriceServiceUi = Double.parseDouble(sGrossPriceServiceUi);
                String sNetPriceServiceUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetPriceServiceUi = Double.parseDouble(sNetPriceServiceUi);

                String sUnitPricePackageUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(1), "value");
                String sGrossPricePackageUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(1), "value");
                double dGrossPricePackageUi = Double.parseDouble(sGrossPricePackageUi);
                String sNetPricePackageUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value");
                double dNetPricePackageUi = Double.parseDouble(sNetPricePackageUi);

                String sUnitPriceBomUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(2), "value");
                String sGrossPriceBomUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(2), "value");
                double dGrossPriceBomUi = Double.parseDouble(sGrossPriceBomUi);
                String sNetPriceBomUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(2), "value");
                double dNetPriceBomUi = Double.parseDouble(sNetPriceBomUi);

                String sGrossAmtBeforeApplyGDUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_grossBillTotal, "value");
                double dGrossAmtBeforeApplyGDUi = Double.parseDouble(sGrossAmtBeforeApplyGDUi);
                String sNetAmtBeforeApplyGDUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_netBillTotal, "value");
                double dNetAmtBeforeApplyGDUi = Double.parseDouble(sNetAmtBeforeApplyGDUi);

                String sGrossSubPackageItemPriceForFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(0));
                double dGrossPriceOfFirstItemBeforeDiscount = Double.parseDouble(sGrossSubPackageItemPriceForFirstItem);

                String sGrossSubPackageItemPriceForSecondItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(1));
                double dGrossPriceOfSecondItemBeforeDiscount = Double.parseDouble(sGrossSubPackageItemPriceForSecondItem);

                //validate before apply gd net and gross amt
                double totalGrossAmount = dGrossPriceBomUi + dGrossPricePackageUi + dGrossPriceServiceUi;
                m_assert.assertTrue((totalGrossAmount == dGrossAmtBeforeApplyGDUi) &&
                        (totalGrossAmount == dNetAmtBeforeApplyGDUi), "Gross amt and Net Amt is equal before applying global discount: " + totalGrossAmount);


                //Giving global discount in rs
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmt, sGlobalDiscount), "Entered amount in Global discount field = <b>" + sGlobalDiscount + "</b>");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.applyGlobalDiscount_button), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);

                //Calculate global discount on all items
                // (discount/gross amt) * item gross amt
                double dGlobalDiscount = Double.parseDouble(sGlobalDiscount);
                double discountOnService = (dGlobalDiscount / totalGrossAmount) * dGrossPriceServiceUi;
                double dRoundOffDiscountOnService = Double.parseDouble(roundOffFunctionUsingFloat(discountOnService));
                double discountOnPackage = (dGlobalDiscount / totalGrossAmount) * dGrossPricePackageUi;
               double dRoundOffDiscountOnPackage = Double.parseDouble(roundOffFunctionUsingFloat(discountOnPackage));
                double discountOnBom = (dGlobalDiscount / totalGrossAmount) * dGrossPriceBomUi;
                double dRoundOffDiscountOnBom = Double.parseDouble(roundOffFunctionUsingFloat(discountOnBom));
                double discountOnSubPack1 = (dGlobalDiscount / totalGrossAmount) * dGrossPriceOfFirstItemBeforeDiscount;
                double dRoundOffDiscountOnSubPack1 = Double.parseDouble(roundOffFunctionUsingFloat(discountOnSubPack1));
                double discountOnSubPack2 = (dGlobalDiscount / totalGrossAmount) * dGrossPriceOfSecondItemBeforeDiscount;
                double dRoundOffDiscountOnSubPack2 = Double.parseDouble(roundOffFunctionUsingFloat(discountOnSubPack2));
                double dTotalSubPackDiscount = dRoundOffDiscountOnSubPack1 + dRoundOffDiscountOnSubPack2;
                double totalDiscountOnAll = dRoundOffDiscountOnService + dRoundOffDiscountOnPackage + dRoundOffDiscountOnBom;

                //getting discount from ui
                String sOverAllDiscountOnServiceUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(0)).replaceAll("\\s", "");
                double dOverAllDiscountOnServiceUi = Double.parseDouble(sOverAllDiscountOnServiceUi) * (-1);
                String sOverAllDiscountOnPackageUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(1)).replaceAll("\\s", "");
                double dOverAllDiscountOnPackageUi = Double.parseDouble(sOverAllDiscountOnPackageUi) * (-1);
                String sOverAllDiscountOnBomUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(2)).replaceAll("\\s", "");
                double dOverAllDiscountOnBomUi = Double.parseDouble(sOverAllDiscountOnBomUi) * (-1);

                m_assert.assertTrue(dRoundOffDiscountOnService == dOverAllDiscountOnServiceUi,
                        "Discount of service equals " + dRoundOffDiscountOnService);
                m_assert.assertTrue(dRoundOffDiscountOnPackage == dOverAllDiscountOnPackageUi,
                        "Discount of package equals " + dRoundOffDiscountOnPackage);
                m_assert.assertTrue(dRoundOffDiscountOnBom == dOverAllDiscountOnBomUi,
                        "Discount of bom equals " + dRoundOffDiscountOnBom);
                m_assert.assertTrue(dTotalSubPackDiscount == dOverAllDiscountOnPackageUi,
                        "Discount of sub package equals " + dTotalSubPackDiscount);

                //calculating gross amt after discount
                double dNetAmtServiceAfterDiscount = Double.parseDouble(roundOffFunctionUsingFloat(dGrossPriceServiceUi - discountOnService));
                double dNetAmtPackageAfterDiscount = Double.parseDouble(roundOffFunctionUsingFloat(dGrossPricePackageUi - discountOnPackage));
                double dNetAmtBomAfterDiscount = Double.parseDouble(roundOffFunctionUsingFloat(dGrossPriceBomUi - dOverAllDiscountOnBomUi));
                double dNetAmtSubPackFirstAfterDiscount = Double.parseDouble(roundOffFunctionUsingFloat(dGrossPriceOfFirstItemBeforeDiscount - dRoundOffDiscountOnSubPack1));
                double dNetAmtSubPackSecondAfterDiscount = Double.parseDouble(roundOffFunctionUsingFloat(dGrossPriceOfSecondItemBeforeDiscount - dRoundOffDiscountOnSubPack2));
                double calculatedNetBillTotalAfterDiscount = Double.parseDouble(roundOffFunctionUsingFloat(dNetAmtServiceAfterDiscount + dNetAmtPackageAfterDiscount + dNetAmtBomAfterDiscount));



                //getting net amt from ui after apply discount
                String sNetPriceServiceUiAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetPriceServiceUiAfterDiscount = Double.parseDouble(sNetPriceServiceUiAfterDiscount);
                String sNetPricePackageUiAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value");
                double dNetPricePackageUiAfterDiscount = Double.parseDouble(sNetPricePackageUiAfterDiscount);
                String sNetPriceBomUiAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(2), "value");
                double dNetPriceBomUiAfterDiscount = Double.parseDouble(roundOffFunctionUsingFloat(Double.parseDouble(sNetPriceBomUiAfterDiscount)));
                String sNetPriceItemPriceForFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                String sNetPriceItemPriceForSecondItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dNetPriceItemPriceForFirstItemUI = Double.parseDouble(sNetPriceItemPriceForFirstItem);
                double dNetPriceItemPriceForSecondItemUI = Double.parseDouble(sNetPriceItemPriceForSecondItem);

                m_assert.assertTrue(dNetPriceServiceUiAfterDiscount == dNetAmtServiceAfterDiscount,
                        "Net Price of service equals: " + dNetPriceServiceUiAfterDiscount);
                m_assert.assertTrue(dNetPricePackageUiAfterDiscount == dNetAmtPackageAfterDiscount,
                        "Net Price of package equals: " + dNetAmtPackageAfterDiscount);
                m_assert.assertTrue(dNetPriceBomUiAfterDiscount == dNetAmtBomAfterDiscount,
                        "Net Price of bom equals: " + dNetAmtBomAfterDiscount);

                m_assert.assertTrue(dNetPriceItemPriceForFirstItemUI + dNetPriceItemPriceForSecondItemUI == dNetAmtSubPackFirstAfterDiscount + dNetAmtSubPackSecondAfterDiscount,
                        "Net Price of bom equals: " + dNetAmtPackageAfterDiscount);

                //getting gross amt,total discount and net from ui after apply discount from below table
                String sGrossAmtAfterApplyGDUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_grossBillTotal, "value");
                double dGrossAmtAfterApplyGDUi = Double.parseDouble(sGrossAmtAfterApplyGDUi);
                String sNetAmtAfterApplyGDUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_netBillTotal, "value");
                double dNetAmtAfterApplyGDUi = Double.parseDouble(sNetAmtAfterApplyGDUi);
                String sDiscountOnItemsAfterApplyGDUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnItem, "value");
                double dTotalDiscountOnItemsAfterApplyGDUi = Double.parseDouble(sDiscountOnItemsAfterApplyGDUi);
                String sTotalDiscountAfterApplyGDUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnBill, "value");
                double dTotalDiscountAfterApplyGDUi = Double.parseDouble(sTotalDiscountAfterApplyGDUi);
                String sTotalOfAllDiscountAfterApplyGDUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalOfAllDiscounts, "value");
                double dTotalOfAllDiscountAfterApplyGDUi = Double.parseDouble(sTotalOfAllDiscountAfterApplyGDUi);


                m_assert.assertTrue(dGrossAmtAfterApplyGDUi == totalGrossAmount
                                && dTotalDiscountOnItemsAfterApplyGDUi == totalDiscountOnAll
                                && dTotalDiscountAfterApplyGDUi == totalDiscountOnAll
                                && dTotalOfAllDiscountAfterApplyGDUi == totalDiscountOnAll
                                && dNetAmtAfterApplyGDUi == calculatedNetBillTotalAfterDiscount,
                        "Gross bill on bill = <b>" + totalGrossAmount + "</b>" +
                                " Total discount on items = <b>" + totalDiscountOnAll + "</b>" +
                                " Total discount on items after apply discount = <b>" + totalDiscountOnAll + "</b>" +
                                " Total of all discounts  = <b>" + totalDiscountOnAll + "</b>" +
                                " Net bill total = <b>" + calculatedNetBillTotalAfterDiscount + "</b>");

                String sAmountReceivedOnUI = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_amountReceivedField.get(0), "value");
                double dAmountReceivedOnUI = Double.parseDouble(sAmountReceivedOnUI);
                m_assert.assertTrue((dAmountReceivedOnUI == calculatedNetBillTotalAfterDiscount), "Net amount on bill equals to amount received against bill is matching = <b>" + dAmountReceivedOnUI + "</b>");

                //validate by giving discount in %
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.selectOption_globalDiscountType, "%"), "Discount type for GD =  <b>%</b>");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmt, "102");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_Bills.applyGlobalDiscount_button, "class").contains("disabled"),
                        "Discount button disabled when % entered is greater than 100");

                //validate by giving discount in %
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmt, sGlobalDiscount),
                        "Entered amount in % Global discount field = <b>" + sGlobalDiscount + "</b>");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.applyGlobalDiscount_button), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);


                //Calculate global discount on all items
                // (discount* item gross amt)/100
                double dGlobalDiscountPercentage = Double.parseDouble(sGlobalDiscount);
                double percentDiscountOnService = (dGlobalDiscountPercentage * dGrossPriceServiceUi) / 100;
                double dRoundOffPercentDiscountOnService = Double.parseDouble(roundOffFunctionUsingFloat(percentDiscountOnService));
                double percentDiscountOnPackage = (dGlobalDiscountPercentage * dGrossPricePackageUi) / 100;
                double dRoundOffPercentDiscountOnPackage = Double.parseDouble(roundOffFunctionUsingFloat(percentDiscountOnPackage));
                double percentDiscountOnBom = (dGlobalDiscountPercentage * dGrossPriceBomUi) / 100;
                double dRoundOffPercentDiscountOnBom = Double.parseDouble(roundOffFunctionUsingFloat(percentDiscountOnBom));
                double totalDiscountOnAllForPercent = dRoundOffPercentDiscountOnService + dRoundOffPercentDiscountOnPackage + dRoundOffPercentDiscountOnBom;

                //getting discount from ui of percentage
                String sOverAllPercentDiscountOnServiceUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(0)).replaceAll("\\s", "");
                double dOverAllPercentDiscountOnServiceUi = Double.parseDouble(sOverAllPercentDiscountOnServiceUi) * (-1);
                String sOverAllPercentDiscountOnPackageUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(1)).replaceAll("\\s", "");
                double dOverAllPercentDiscountOnPackageUi = Double.parseDouble(sOverAllPercentDiscountOnPackageUi) * (-1);
                String sOverAllPercentDiscountOnBomUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(2)).replaceAll("\\s", "");
                double dOverAllPercentDiscountOnBomUi = Double.parseDouble(sOverAllPercentDiscountOnBomUi) * (-1);


                m_assert.assertTrue(dRoundOffPercentDiscountOnService == dOverAllPercentDiscountOnServiceUi,
                        "Discount of service equals " + dOverAllPercentDiscountOnServiceUi);
                m_assert.assertTrue(dRoundOffPercentDiscountOnPackage == dOverAllPercentDiscountOnPackageUi,
                        "Discount of package equals " + dOverAllPercentDiscountOnPackageUi);
                m_assert.assertTrue(dRoundOffPercentDiscountOnBom == dOverAllPercentDiscountOnBomUi,
                        "Discount of bom equals " + dOverAllPercentDiscountOnBomUi);

                //calculating net amt after discount in percentage
                double dNetAmtServiceAfterPercentDiscount = dGrossPriceServiceUi - dOverAllPercentDiscountOnServiceUi;
                double dNetAmtPackageAfterPercentDiscount = dGrossPricePackageUi - dOverAllPercentDiscountOnPackageUi;
                double dNetAmtBomAfterPercentDiscount = dGrossPriceBomUi - dOverAllPercentDiscountOnBomUi;
                double calculatedNetBillTotalAfterPercentDiscount = dNetAmtServiceAfterPercentDiscount + dNetAmtPackageAfterPercentDiscount + dNetAmtBomAfterPercentDiscount;
                double dCalculatedNetBillTotalAfterPercentDiscount = Double.parseDouble(roundOffFunctionUsingFloat(calculatedNetBillTotalAfterPercentDiscount));

                //getting net amt from ui after apply discount
                String sNetPriceServiceUiAfterPercentDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetPriceServiceUiAfterPercentDiscount = Double.parseDouble(sNetPriceServiceUiAfterPercentDiscount);
                String sNetPricePackageUiAfterPercentDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value");
                double dNetPricePackageUiAfterPercentDiscount = Double.parseDouble(sNetPricePackageUiAfterPercentDiscount);
                String sNetPriceBomUiAfterPercentDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(2), "value");
                double dNetPriceBomUiAfterPercentDiscount = Double.parseDouble(sNetPriceBomUiAfterPercentDiscount);

                m_assert.assertTrue(dNetPriceServiceUiAfterPercentDiscount == dNetAmtServiceAfterPercentDiscount,
                        "Net Price of service equals: " + dNetAmtServiceAfterPercentDiscount);
                m_assert.assertTrue(dNetPricePackageUiAfterPercentDiscount == dNetAmtPackageAfterPercentDiscount,
                        "Net Price of package equals: " + dNetAmtPackageAfterPercentDiscount);
                m_assert.assertTrue(dNetPriceBomUiAfterPercentDiscount == dNetAmtBomAfterPercentDiscount,
                        "Net Price of bom equals: " + dNetAmtBomAfterPercentDiscount);

                //getting gross amt,total discount and net from ui after apply discount from below table
                String sGrossAmtAfterApplyGDPercentUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_grossBillTotal, "value");
                double dGrossAmtAfterApplyGDPercentUi = Double.parseDouble(sGrossAmtAfterApplyGDPercentUi);
                String sNetAmtAfterApplyGDPercentUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_netBillTotal, "value");
                double dNetAmtAfterApplyGDPercentUi = Double.parseDouble(sNetAmtAfterApplyGDPercentUi);
                String sDiscountOnItemsAfterApplyGDPercentUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnItem, "value");
                double dTotalDiscountOnItemsAfterApplyGDPercentUi = Double.parseDouble(sDiscountOnItemsAfterApplyGDPercentUi);
                String sTotalDiscountAfterApplyGDPercentUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnBill, "value");
                double dTotalDiscountAfterApplyGDPercentUi = Double.parseDouble(sTotalDiscountAfterApplyGDPercentUi);
                String sTotalOfAllDiscountAfterApplyGDPercentUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalOfAllDiscounts, "value");
                double dTotalOfAllDiscountAfterApplyGDPercentUi = Double.parseDouble(sTotalOfAllDiscountAfterApplyGDPercentUi);
                String sRoundOffValueUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_roundDiscountOnItem, "value");
                double dRoundOffValueUi = Double.parseDouble(sRoundOffValueUi);

                double dRoundOffDiscount = totalDiscountOnAllForPercent + (dRoundOffValueUi);
                dCalculatedNetBillTotalAfterPercentDiscount = totalGrossAmount - dRoundOffDiscount;

                m_assert.assertTrue(dGrossAmtAfterApplyGDPercentUi == totalGrossAmount
                                && dTotalDiscountOnItemsAfterApplyGDPercentUi == totalDiscountOnAllForPercent
                                && dTotalOfAllDiscountAfterApplyGDPercentUi == totalDiscountOnAllForPercent
                                && dNetAmtAfterApplyGDPercentUi == dCalculatedNetBillTotalAfterPercentDiscount,
                        "Gross bill on bill = <b>" + totalGrossAmount + "</b>" +
                                " Total discount on items = <b>" + totalDiscountOnAllForPercent + "</b>" +
                                " Total discount on items after apply discount = <b>" + totalDiscountOnAllForPercent + "</b>" +
                                " Total of all discounts  = <b>" + totalDiscountOnAllForPercent + "</b>" +
                                " Net bill total = <b>" + dCalculatedNetBillTotalAfterPercentDiscount + "</b>");


                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_discountReason.get(0), sDiscountReason);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_discountReason.get(1), sDiscountReason);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_discountReason.get(2), sDiscountReason);
                Cls_Generic_Methods.scrollToElementByJS(oPage_Bills.select_modeOfPayment);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_modeOfPayment, paymentMode), paymentMode + " option is selected for Mode Of Payment");
                String sAmountReceivedOnUIDiscountPercent = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_amountReceivedField.get(0), "value");
                double dAmountReceivedOnUIDiscountPercent = Double.parseDouble(sAmountReceivedOnUIDiscountPercent);
                m_assert.assertTrue((dAmountReceivedOnUIDiscountPercent == dCalculatedNetBillTotalAfterPercentDiscount), "Net amount on bill equals to amount received against bill is matching = <b>" + dCalculatedNetBillTotalAfterPercentDiscount + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills), "<b>Cash bill is created </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.customWait(4);

                //Validation on preview discount
                String sDiscountAmtOfServiceOnPreviewUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(0)).replaceAll("\\s", "");
                double dDiscountAmtOfServiceOnPreviewUi = Double.parseDouble(sDiscountAmtOfServiceOnPreviewUi) * (-1);
                String sDiscountAmtOfPackageOnPreviewUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(1)).replaceAll("\\s", "");
                double dDiscountAmtOfPackageOnPreviewUi = Double.parseDouble(sDiscountAmtOfPackageOnPreviewUi) * (-1);
                String sDiscountAmtOfBomOnPreviewUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(2)).replaceAll("\\s", "");
                double dDiscountAmtOfBomOnPreviewUi = Double.parseDouble(sDiscountAmtOfBomOnPreviewUi) * (-1);


                m_assert.assertTrue((dDiscountAmtOfServiceOnPreviewUi == dRoundOffPercentDiscountOnService),
                        "Service discount amount on bill preview equals = <b>" + dRoundOffPercentDiscountOnService + "</b>");
                m_assert.assertTrue((dDiscountAmtOfPackageOnPreviewUi == dRoundOffPercentDiscountOnPackage),
                        "Package discount amount on bill preview equals = <b>" + dRoundOffPercentDiscountOnPackage + "</b>");
                m_assert.assertTrue((dDiscountAmtOfBomOnPreviewUi == dRoundOffPercentDiscountOnBom),
                        "Bom discount amount on bill preview equals = <b>" + dRoundOffPercentDiscountOnBom + "</b>");

                //gross,net and total discount validation on preview
                String sGrossAmtOnPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_grossBillTotalOnBillPreview);
                double dGrossAmtOnPreview = Double.parseDouble(sGrossAmtOnPreview);
                String sTotalDiscountOnItemsInBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalDiscountOnItemsOnBillPreview);
                double dTotalDiscountOnItemsInBillPreview = Double.parseDouble(sTotalDiscountOnItemsInBillPreview);
                String sTotalOfAllDiscountOnItemsInBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalOfAllDiscountOnBillPreview);
                double dTotalOfAllDiscountOnItemsInBillPreview = Double.parseDouble(sTotalOfAllDiscountOnItemsInBillPreview);
                String sPaymentReceivedOnBillPreview = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_paymentReceivedOnBillPreview);
                dPaymentReceivedOnBillPreview = Double.parseDouble(sPaymentReceivedOnBillPreview);

                m_assert.assertTrue(dGrossAmtOnPreview == totalGrossAmount
                                && dTotalDiscountOnItemsInBillPreview == totalDiscountOnAllForPercent
                                && dTotalOfAllDiscountOnItemsInBillPreview == totalDiscountOnAllForPercent
                                && dPaymentReceivedOnBillPreview == dCalculatedNetBillTotalAfterPercentDiscount,
                        "Gross bill on bill preview = <b>" + totalGrossAmount + "</b>" +
                                " Total discount on items in bill preview = <b>" + totalDiscountOnAllForPercent + "</b>" +
                                " Total of all discounts on bill preview = <b>" + totalDiscountOnAllForPercent + "</b>" +
                                " Net bill total on bill preview = <b>" + dCalculatedNetBillTotalAfterPercentDiscount + "</b>");

                //Edit bill
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_editBill), "clicked on edit bill");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickIPDPlusActionButton, 30);
                try {
                    m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_Bills.input_globalDiscountAmt),
                            "Global discount button is not there on editing");
                }catch (Exception e)
                {
                    m_assert.assertFatal(" " + e);
                }
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
    @Test(enabled = true, description = "Create credit bill and validate the Global level  Discount")
    public void createDraftBillAndValidateGlobalLevelDiscount()
    {
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_Bills oPage_Bills = new Page_Bills(driver);
        Page_IPD oPage_IPD = new Page_IPD(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        boolean bPatientFound = false;
        String paymentMode = "Cash";
        String sPackageName = "Tesing Suregry Package";
        String sGlobalDiscount = "10";
        String sDiscountReason = "Reason" + getRandomUniqueString(6);

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectDepartmentOnApp("OPD");
            myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
            m_assert.assertTrue(
                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, OPD_Data.tab_MY_QUEUE),
                    "Validate " + OPD_Data.tab_MY_QUEUE + " tab is selected");
            Cls_Generic_Methods.customWait(10);

            bPatientFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
            m_assert.assertTrue(bPatientFound, "Validate that the patient " + concatPatientFullName + " is created in OPD");

            if (bPatientFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills), "Clicked on bills button");
                Cls_Generic_Methods.customWait(3);

                Cls_Generic_Methods.clickElement(oPage_IPD.button_newDraftBill);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 30);
                Cls_Generic_Methods.clickElement(oPage_Bills.select_services);
               Cls_Generic_Methods.customWait(6);
               oPage_Bills.input_billService.sendKeys(Keys.DOWN, Keys.ENTER);
                //Cls_Generic_Methods.customWait(4);
                //Cls_Generic_Methods.clickElement(oPage_Bills.button_clickIPDPlusActionButton);
                Cls_Generic_Methods.customWait(6);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Bills.button_newPackage);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.select_package.get(1));

                boolean myPackageFound1 = false;
                for (WebElement eItemList : oPage_Bills.list_packageOrServiceNameUnderDescriptionField) {
                    String sPackageNameOnUI = Cls_Generic_Methods.getTextInElement(eItemList);
                    if (sPackageNameOnUI.equals(sPackageName)) {
                        Cls_Generic_Methods.clickElement(eItemList);
                        myPackageFound1 = true;
                        break;
                    }

                }

                m_assert.assertTrue(myPackageFound1, "Selected package name = <b>" + sPackageName + "</b> ");
                Cls_Generic_Methods.customWait(4);
                //add new consultation service
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickIPDPlusActionButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Bills.button_newConsultation);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.select_package.get(2));
                Cls_Generic_Methods.customWait(3);
                boolean myPackageFound2 = false;
                String sInvoiceSetPackageName = "ASOCT";
                for (WebElement eItemList : oPage_Bills.list_packageOrServiceNameUnderDescriptionField) {
                    String sPackageNameOnUI = Cls_Generic_Methods.getTextInElement(eItemList);
                    if (sPackageNameOnUI.equals(sInvoiceSetPackageName)) {
                        Cls_Generic_Methods.clickElement(eItemList);
                        myPackageFound2 = true;
                        break;
                    }

                }
                m_assert.assertTrue(myPackageFound2, "Selected package name = <b>" + sInvoiceSetPackageName + "</b> ");

                //getting values from ui for service,Package,New consultation,net and gross total
                String sUnitPriceServiceUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(0), "value");
                double dGrossPriceServiceUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(0), "value"));
                String sNetPriceServiceUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetPriceServiceUi = Double.parseDouble(sNetPriceServiceUi);

                String sUnitPricePackageUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(1), "value");
                String sGrossPricePackageUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(1), "value");
                double dGrossPricePackageUi = Double.parseDouble(sGrossPricePackageUi);
                double dNetPricePackageUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value"));

                double dGrossPriceItem3Ui = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(2), "value"));
                double dNetPriceItem3Ui = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(2), "value"));

                double dGrossAmtBeforeApplyGDUi =  Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_grossBillTotal, "value"));
                double dNetAmtBeforeApplyGDUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_netBillTotal, "value"));
                double dGrossSubPackageItemPriceForFirstItemUi = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(0)));
               // double dGrossSubPackageItemPriceForSecondItemUi = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(1)));

                //validate before apply gd net and gross amt
                double totalGrossAmount =  dGrossPricePackageUi + dGrossPriceServiceUi+dGrossPriceItem3Ui;
                m_assert.assertTrue((totalGrossAmount == dGrossAmtBeforeApplyGDUi) &&
                        (totalGrossAmount == dNetAmtBeforeApplyGDUi),"Gross amt and Net Amt is equal before applying global discount: "+totalGrossAmount);

                double totalSubPackAmtBeforeGD = dGrossSubPackageItemPriceForFirstItemUi;
                m_assert.assertTrue(totalSubPackAmtBeforeGD == dGrossPricePackageUi,
                        "Sub package gross amt before discount is equal to package gross amt:  "+totalSubPackAmtBeforeGD);
                //Giving global discount in rs
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmt, sGlobalDiscount), "Entered amount in Global discount field = <b>" + sGlobalDiscount + "</b>");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.applyGlobalDiscount_button), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);

                //Calculate global discount on all items
                // (discount/gross amt) * item gross amt
                double dGlobalDiscount = Double.parseDouble(sGlobalDiscount);
                double dRoundOffDiscountOnService = roundOffFunctionUsingDouble((dGlobalDiscount/totalGrossAmount)* dGrossPriceServiceUi);
                double dRoundOffDiscountOnPackage = roundOffFunctionUsingDouble((dGlobalDiscount/totalGrossAmount)* dGrossPricePackageUi);
                double dRoundOffDiscountOnItem3 = roundOffFunctionUsingDouble((dGlobalDiscount/totalGrossAmount)* dGrossPriceItem3Ui);
                double dRoundOffDiscountOnSubPack1 = roundOffFunctionUsingDouble((dGlobalDiscount/totalGrossAmount)* dGrossSubPackageItemPriceForFirstItemUi);
                double dTotalSubPackDiscount = roundOffFunctionUsingDouble(dRoundOffDiscountOnSubPack1);
                double totalDiscountOnAll = dRoundOffDiscountOnService+dRoundOffDiscountOnPackage+dRoundOffDiscountOnItem3;
                totalDiscountOnAll = Math.round(totalDiscountOnAll/10.0) * 10;
                Cls_Generic_Methods.customWait(2);

                //getting discount from ui
                String sOverAllDiscountOnServiceUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(0)).replaceAll("\\s", "");
                double dOverAllDiscountOnServiceUi = Double.parseDouble(sOverAllDiscountOnServiceUi)*(-1);
                Cls_Generic_Methods.customWait(2);
                String sOverAllDiscountOnPackageUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(1)).replaceAll("\\s", "");
                double dOverAllDiscountOnPackageUi = Double.parseDouble(sOverAllDiscountOnPackageUi)*(-1);
                Cls_Generic_Methods.customWait(2);
                double dOverAllDiscountOnItem3Ui = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(2)).replaceAll("\\s", ""))*(-1);

                m_assert.assertTrue(dRoundOffDiscountOnService == dOverAllDiscountOnServiceUi,
                        "Discount of service equals "+dRoundOffDiscountOnService);
                m_assert.assertTrue(dRoundOffDiscountOnPackage == dOverAllDiscountOnPackageUi,
                        "Discount of package equals "+dRoundOffDiscountOnPackage);
                m_assert.assertTrue(dTotalSubPackDiscount == dOverAllDiscountOnPackageUi,
                        "Discount of sub package equals "+dTotalSubPackDiscount);
                m_assert.assertTrue(dRoundOffDiscountOnItem3 == dOverAllDiscountOnItem3Ui,
                        "Discount of item 3 equals "+dRoundOffDiscountOnItem3);

                //calculating net amt after discount
                double dNetAmtServiceAfterDiscount = dGrossPriceServiceUi - dRoundOffDiscountOnService;
                double dNetAmtPackageAfterDiscount = dGrossPricePackageUi - dRoundOffDiscountOnPackage;
                double dNetAmtItem3AfterDiscount = dGrossPriceItem3Ui - dRoundOffDiscountOnItem3;
                double dNetAmtSubPackFirstAfterDiscount = dGrossSubPackageItemPriceForFirstItemUi - dRoundOffDiscountOnSubPack1;
                double calculatedNetBillTotalAfterDiscount = dNetAmtServiceAfterDiscount+dNetAmtPackageAfterDiscount+dNetAmtItem3AfterDiscount;
                calculatedNetBillTotalAfterDiscount = Math.round(calculatedNetBillTotalAfterDiscount/10.0) * 10;

                //getting net amt from ui after apply discount
                double dNetPriceServiceUiAfterDiscount = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value"));
                double dNetPricePackageUiAfterDiscount = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value"));
                double dNetPriceItemPriceForFirstItemUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0)));
                double dNetPriceItem3UiAfterDiscount = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(2), "value"));

                m_assert.assertTrue(dNetPriceServiceUiAfterDiscount == dNetAmtServiceAfterDiscount,
                        "Net Price of service equals: "+dNetAmtServiceAfterDiscount);
                m_assert.assertTrue(dNetPricePackageUiAfterDiscount == dNetAmtPackageAfterDiscount,
                        "Net Price of package equals: "+dNetAmtPackageAfterDiscount);
                m_assert.assertTrue(dNetPriceItem3UiAfterDiscount == dNetAmtItem3AfterDiscount,
                        "Net Price of item 3 equals: "+dNetAmtItem3AfterDiscount);
                m_assert.assertTrue(dNetPriceItemPriceForFirstItemUI == dNetAmtSubPackFirstAfterDiscount ,
                        "Net Price of sub pack equals: "+dNetAmtPackageAfterDiscount);
                m_assert.assertTrue(dNetAmtPackageAfterDiscount == dNetAmtSubPackFirstAfterDiscount ,
                        "Net Price of sub package and package equals: "+dNetAmtPackageAfterDiscount);

                //getting gross amt,total discount and net from ui after apply discount from below table
                double dGrossAmtAfterApplyGDUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_grossBillTotal, "value"));
                double dNetAmtAfterApplyGDUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_netBillTotal, "value"));
                double dTotalDiscountOnItemsAfterApplyGDUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnItem, "value"));
                double dTotalDiscountAfterApplyGDUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnBill, "value"));
                double dTotalOfAllDiscountAfterApplyGDUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalOfAllDiscounts, "value"));

                m_assert.assertTrue(dGrossAmtAfterApplyGDUi == totalGrossAmount
                                && dTotalDiscountOnItemsAfterApplyGDUi ==totalDiscountOnAll
                                &&dTotalDiscountAfterApplyGDUi == totalDiscountOnAll
                                && dTotalOfAllDiscountAfterApplyGDUi==totalDiscountOnAll
                                && dNetAmtAfterApplyGDUi ==calculatedNetBillTotalAfterDiscount ,
                        "Gross bill on bill = <b>" + totalGrossAmount + "</b>"+
                                " Total discount on items = <b>" + totalDiscountOnAll + "</b>"+
                                " Total discount on items after apply discount = <b>" + totalDiscountOnAll + "</b>"+
                                " Total of all discounts  = <b>" + totalDiscountOnAll + "</b>"+
                                " Net bill total = <b>" + calculatedNetBillTotalAfterDiscount + "</b>");

                //validate by giving discount in %
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.selectOption_globalDiscountType, "%"), "Discount type for GD =  <b>%</b>");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmt, "102");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_Bills.applyGlobalDiscount_button, "class").contains("disabled"),
                        "Discount button disabled when % entered is greater than 100");

                //validate by giving discount in %
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_globalDiscountAmt, sGlobalDiscount),
                        "Entered amount in % Global discount field = <b>" + sGlobalDiscount + "</b>");
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.applyGlobalDiscount_button), "Clicked on apply global discount button");
                } catch (Exception e) {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println("Alert data: " + alertText);
                    alert.accept();
                }
                Cls_Generic_Methods.customWait(3);

                //Calculate global discount on all items
                // (discount* item gross amt)/100
                double dGlobalDiscountPercentage = Double.parseDouble(sGlobalDiscount);
                double dRoundOffPercentDiscountOnService = roundOffFunctionUsingDouble((dGlobalDiscountPercentage* dGrossPriceServiceUi)/100);
                double dRoundOffPercentDiscountOnPackage = roundOffFunctionUsingDouble((dGlobalDiscountPercentage* dGrossPricePackageUi)/100);
                double dRoundOffPercentDiscountOnItem3 = roundOffFunctionUsingDouble((dGlobalDiscountPercentage* dGrossPriceItem3Ui)/100);
                double totalDiscountOnAllForPercent = dRoundOffPercentDiscountOnService+dRoundOffPercentDiscountOnPackage+dRoundOffPercentDiscountOnItem3;
                double dRoundOffPercentDiscountOnSubPack1 = roundOffFunctionUsingDouble((dGlobalDiscountPercentage * dGrossSubPackageItemPriceForFirstItemUi)/100);
                double dTotalSubPackPercentDiscount = dRoundOffPercentDiscountOnSubPack1;

                //getting discount from ui of percentage
                double dOverAllPercentDiscountOnServiceUi = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(0)).replaceAll("\\s", ""))*(-1);
                double dOverAllPercentDiscountOnPackageUi = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(1)).replaceAll("\\s", ""))*(-1);
                double dOverAllPercentDiscountOnItem3Ui = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(2)).replaceAll("\\s", ""))*(-1);

                m_assert.assertTrue(dRoundOffPercentDiscountOnService == dOverAllPercentDiscountOnServiceUi,
                        "Discount of service equals "+dOverAllPercentDiscountOnServiceUi);
                m_assert.assertTrue(dRoundOffPercentDiscountOnPackage == dOverAllPercentDiscountOnPackageUi,
                        "Discount of package equals "+dOverAllPercentDiscountOnPackageUi);
                m_assert.assertTrue(dTotalSubPackPercentDiscount == dRoundOffPercentDiscountOnPackage,
                        "Discount of sub package equals "+dTotalSubPackPercentDiscount);
                m_assert.assertTrue(dRoundOffPercentDiscountOnItem3 == dOverAllPercentDiscountOnItem3Ui,
                        "Discount of item 3 equals "+dRoundOffPercentDiscountOnItem3);
                //calculating net amt after discount in percentage
                double dNetAmtServiceAfterPercentDiscount = dGrossPriceServiceUi - dRoundOffPercentDiscountOnService;
                double dNetAmtPackageAfterPercentDiscount = dGrossPricePackageUi - dRoundOffPercentDiscountOnPackage;
                double dNetAmtItem3AfterPercentDiscount = dGrossPriceItem3Ui - dRoundOffPercentDiscountOnItem3;
                double dNetAmtSubPackFirstAfterPercentDiscount = dGrossSubPackageItemPriceForFirstItemUi - dRoundOffPercentDiscountOnSubPack1;
                double calculatedNetBillTotalAfterPercentDiscount = dNetAmtServiceAfterPercentDiscount+dNetAmtPackageAfterPercentDiscount+dNetAmtItem3AfterPercentDiscount;

                //getting net amt from ui after apply discount
                double dNetPriceServiceUiAfterPercentDiscount =  Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value"));
                double dNetPricePackageUiAfterPercentDiscount =  Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value"));
                double dNetPriceItem3UiAfterPercentDiscount =  Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(2), "value"));
                double dNetPriceSubPackage1UiAfterPercentDiscount =  Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0)));

                m_assert.assertTrue(dNetPriceServiceUiAfterPercentDiscount == dNetAmtServiceAfterPercentDiscount,
                        "Net Price of service equals: "+dNetAmtServiceAfterPercentDiscount);
                m_assert.assertTrue(dNetPricePackageUiAfterPercentDiscount == dNetAmtPackageAfterPercentDiscount,
                        "Net Price of package equals: "+dNetAmtPackageAfterPercentDiscount);
                m_assert.assertTrue(dNetPriceItem3UiAfterPercentDiscount == dNetAmtItem3AfterPercentDiscount,
                        "Net Price of item 3 equals: "+dNetAmtItem3AfterPercentDiscount);
                m_assert.assertTrue(dNetPriceSubPackage1UiAfterPercentDiscount == dNetAmtSubPackFirstAfterPercentDiscount,
                        "Net Price of sub package first item equals: "+dNetAmtSubPackFirstAfterPercentDiscount);
                m_assert.assertTrue(dNetAmtSubPackFirstAfterPercentDiscount == dNetAmtPackageAfterPercentDiscount,
                        "Net Price of sub package and package equals: "+dNetAmtPackageAfterPercentDiscount);


                //getting gross amt,total discount and net from ui after apply discount from below table
                double dGrossAmtAfterApplyGDPercentUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_grossBillTotal, "value"));
                double dNetAmtAfterApplyGDPercentUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_netBillTotal, "value"));
                double dTotalDiscountOnItemsAfterApplyGDPercentUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnItem, "value"));
                double dTotalDiscountAfterApplyGDPercentUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnBill, "value"));
                double dTotalOfAllDiscountAfterApplyGDPercentUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalOfAllDiscounts, "value"));
                double dRoundOffValueUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_roundDiscountOnItem, "value"));

                double dRoundOffDiscount = totalDiscountOnAllForPercent + (dRoundOffValueUi);
                double dCalculatedNetBillTotalAfterPercentDiscount = totalGrossAmount - dRoundOffDiscount;

                m_assert.assertTrue(dCalculatedNetBillTotalAfterPercentDiscount == calculatedNetBillTotalAfterPercentDiscount,
                        "Net bill total by summing service and package equals gross-discount "+calculatedNetBillTotalAfterPercentDiscount);

                m_assert.assertTrue(dGrossAmtAfterApplyGDPercentUi == totalGrossAmount
                                && dTotalDiscountOnItemsAfterApplyGDPercentUi ==totalDiscountOnAllForPercent
                                && dTotalOfAllDiscountAfterApplyGDPercentUi==totalDiscountOnAllForPercent
                                && dNetAmtAfterApplyGDPercentUi ==dCalculatedNetBillTotalAfterPercentDiscount ,
                        "Gross bill on bill = <b>" + totalGrossAmount + "</b>"+
                                " Total discount on items = <b>" + totalDiscountOnAllForPercent + "</b>"+
                                " Total discount on items after apply discount = <b>" + totalDiscountOnAllForPercent + "</b>"+
                                " Total of all discounts  = <b>" + totalDiscountOnAllForPercent + "</b>"+
                                " Net bill total = <b>" + dCalculatedNetBillTotalAfterPercentDiscount + "</b>");
                //delete 3rd item
                Cls_Generic_Methods.clickElement(oPage_Bills.button_deleteItemPackage.get(2));
                Cls_Generic_Methods.customWait(4);
                totalGrossAmount = totalGrossAmount - dGrossPriceItem3Ui;
                double dCalculatedNetBillTotalAfterDeleting  = dCalculatedNetBillTotalAfterPercentDiscount - dNetPriceItem3UiAfterPercentDiscount;
                totalDiscountOnAllForPercent = totalDiscountOnAllForPercent - dRoundOffPercentDiscountOnItem3;

                //getting gross amt,total discount and net from ui after DELETING 2nd item
                double dGrossAmtAfterItemDeleteUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_grossBillTotal, "value"));
                double dNetAmtAfterItemDeleteUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_netBillTotal, "value"));
                double dTotalDiscountOnItemsAfterItemDeleteUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnItem, "value"));
                double dTotalDiscountAfterItemDeleteUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalDiscountOnBill, "value"));
                double dTotalOfAllDiscountAfterItemDeleteUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_totalOfAllDiscounts, "value"));
                double dRoundOffValueItemDeleteUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_roundDiscountOnItem, "value"));
                double dRoundOffItemDeleteDiscount = totalDiscountOnAllForPercent + (dRoundOffValueItemDeleteUi);
                double dCalculatedNetBillTotalAfterDeleteItem = totalGrossAmount - dRoundOffItemDeleteDiscount;
                m_assert.assertTrue(dCalculatedNetBillTotalAfterDeleteItem == dCalculatedNetBillTotalAfterDeleting,
                        "Net bill total by summing service and package equals gross-discount "+dCalculatedNetBillTotalAfterDeleting);
                m_assert.assertTrue(dGrossAmtAfterItemDeleteUi == totalGrossAmount
                                && dTotalDiscountOnItemsAfterItemDeleteUi ==totalDiscountOnAllForPercent
                                && dTotalOfAllDiscountAfterItemDeleteUi==totalDiscountOnAllForPercent
                                && dTotalDiscountAfterItemDeleteUi ==totalDiscountOnAllForPercent
                                && dNetAmtAfterItemDeleteUi ==dCalculatedNetBillTotalAfterDeleteItem ,
                        "Gross bill on bill after deleting = <b>" + totalGrossAmount + "</b>"+
                                " Total discount on items after deleting= <b>" + totalDiscountOnAllForPercent + "</b>"+
                                " Total discount on items after apply discount after deleting = <b>" + totalDiscountOnAllForPercent + "</b>"+
                                " Total of all discounts  after deleting= <b>" + totalDiscountOnAllForPercent + "</b>"+
                                " Net bill total after deleting = <b>" + dCalculatedNetBillTotalAfterDeleteItem + "</b>");


                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_discountReason.get(0), sDiscountReason);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_discountReason.get(1), sDiscountReason);
                Cls_Generic_Methods.scrollToElementByJS(oPage_Bills.button_clickSaveDraftBills);
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.input_Payment);
                Cls_Generic_Methods.customWait(2);
                String sPendingPayment=Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_pendingPayment, "value");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_Payment,sPendingPayment);
                Cls_Generic_Methods.customWait(2);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveDraftBills), "<b>Draft bill is created </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.customWait(4);

                //Validation on preview discount
                String sDiscountAmtOfServiceOnPreviewUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(0)).replaceAll("\\s", "");
                double dDiscountAmtOfServiceOnPreviewUi = Double.parseDouble(sDiscountAmtOfServiceOnPreviewUi)* (-1);
                String sDiscountAmtOfPackageOnPreviewUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(1)).replaceAll("\\s", "");
                double dDiscountAmtOfPackageOnPreviewUi = Double.parseDouble(sDiscountAmtOfPackageOnPreviewUi)* (-1);

                m_assert.assertTrue((dDiscountAmtOfServiceOnPreviewUi == dRoundOffPercentDiscountOnService),
                        "Service discount amount on bill preview equals = <b>" + dRoundOffPercentDiscountOnService + "</b>");
                m_assert.assertTrue((dDiscountAmtOfPackageOnPreviewUi == dRoundOffPercentDiscountOnPackage),
                        "Package discount amount on bill preview equals = <b>" + dRoundOffPercentDiscountOnPackage + "</b>");

                //gross,net and total discount validation on preview
                double dGrossAmtOnPreview = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_grossBillTotalOnBillPreview));
                double dTotalDiscountOnItemsInBillPreview = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalDiscountOnItemsOnBillPreview));
                double dTotalOfAllDiscountOnItemsInBillPreview = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalOfAllDiscountOnBillPreview));
                double dPaymentPendingOnBillPreview = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_paymentPendingOnBillPreview));


                m_assert.assertTrue(dGrossAmtOnPreview == totalGrossAmount
                                && dTotalDiscountOnItemsInBillPreview ==totalDiscountOnAllForPercent
                                && dTotalOfAllDiscountOnItemsInBillPreview==totalDiscountOnAllForPercent
                                && dPaymentPendingOnBillPreview == dCalculatedNetBillTotalAfterDeleteItem ,
                        "Gross bill on bill preview = <b>" + totalGrossAmount + "</b>"+
                                " Total discount on items in bill preview = <b>" + totalDiscountOnAllForPercent + "</b>"+
                                " Total of all discounts on bill preview = <b>" + totalDiscountOnAllForPercent + "</b>"+
                                " Net bill total on bill preview = <b>" + dCalculatedNetBillTotalAfterDeleteItem + "</b>");

                //Edit bill
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_editDraftBill), "clicked on edit bill");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickIPDPlusActionButton, 30);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Bills.input_globalDiscountAmt),
                        "Global discount button is there on editing");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_internalCommentsInOPD, "InternalComments");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveDraftBills), "<b>Draft bill is updated </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.customWait(4);

                //cancel bill
                Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_removeDraftBill);
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

    }


