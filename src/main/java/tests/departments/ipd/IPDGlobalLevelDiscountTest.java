package tests.departments.ipd;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.IPD_Data;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.bills.Page_Bills;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.ipd.forms.intraOperative.Page_BillOfMaterial;
import pages.ipd.forms.preOperative.Page_AdmissionInPreOperative;
import pages.sprint69.financeChanges.Page_FinanceChanges;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

import static pages.commonElements.CommonActions.getRandomUniqueString;

public class IPDGlobalLevelDiscountTest extends TestBase {
    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_NewPatientRegisteration oPage_NewPatientRegisteration;
    Page_IPD oPage_IPD;
    Page_Bills oPage_Bills;

    @Test(enabled = true, description = "Create patient in ipd for global level discount validation")
    public void scheduleAdmissionInIpdForGlobalDiscount() {
        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_IPD = new Page_IPD(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_AdmissionInPreOperative oPage_AdmissionInPreOperative = new Page_AdmissionInPreOperative(driver);
        String sAddNewCase = "Add New Case";
        Page_BillOfMaterial oPage_BillOfMaterial = new Page_BillOfMaterial(driver);
        boolean bPatientFound = false;
        String sBILLABLE_UNIT_PRICE = "21";
        String sCONSUMED_QUANTITY = "2";
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

                if(bPatientFound){
                    try {
                        Cls_Generic_Methods.scrollToElementByJS(oPage_IPD.text_preOperativeSection);
                        Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.button_admissionInPreOperative);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AdmissionInPreOperative.tab_AdminTabOnAdmissionUnderPreOperative, 16);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.button_saveOnModalHeader),
                                "Validate Save button is clicked on Admission under Pre-Operative");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);
                        Cls_Generic_Methods.customWait(4);
                        Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_BillOfMaterial.button_billOfMaterialTemplate, 16);

                        try {
                            Cls_Generic_Methods.clickElement(driver, oPage_BillOfMaterial.button_billOfMaterialTemplate);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_BillOfMaterial.button_addBillOfMaterial, 4);
                            Cls_Generic_Methods.clickElement(driver, oPage_BillOfMaterial.button_addBillOfMaterial);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_BillOfMaterial.button_saveBom, 8);
                            try {
                                for (WebElement buttonElement : oPage_BillOfMaterial.tr_itemInBom) {
                                    int index = oPage_BillOfMaterial.tr_itemInBom.indexOf(buttonElement);
                                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, buttonElement),
                                            "selected a item from the list for BOM");
                                    break;

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                m_assert.assertFatal("Exception while selecting bom item" + e);
                            }

                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_BillOfMaterial.input_billableUnitPrice, 20);
                            Cls_Generic_Methods.sendKeysIntoElement(oPage_BillOfMaterial.input_billableUnitPrice, sBILLABLE_UNIT_PRICE);

                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_BillOfMaterial.input_consumedQuantityInBom, 20);
                            Cls_Generic_Methods.sendKeysIntoElement(oPage_BillOfMaterial.input_consumedQuantityInBom, sCONSUMED_QUANTITY);
                            for (WebElement billableCheckbox : oPage_BillOfMaterial.list_inputBillableCheckboxListInBom) {
                                if (Cls_Generic_Methods.isElementDisplayed(billableCheckbox)) {
                                   Cls_Generic_Methods.clickElement(billableCheckbox);
                                }
                            }
                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_BillOfMaterial.button_saveBom),
                                    " BOM saved");

                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal("Exception while filling bill of material chart Template " + e);
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("" + e);
                    }
                }
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

    @Test(enabled = true, description = "Create cash bill and validate the Global level  Discount")
    public void createCashBillAndValidateGlobalLevelDiscount() {
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills = new Page_Bills(driver);
        oPage_IPD = new Page_IPD(driver);
        boolean bPatientFound = false;
        String paymentMode = "Cash";
        String sPackageName = "AutomationPackageDataDisplaySelf";
        String sGlobalDiscount = "10";
        String sDiscountReason = "Reason" + getRandomUniqueString(6);

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

            double dPaymentReceivedOnBillPreview = 0;
            if (bPatientFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills), "Clicked on bills button");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_cashBill);
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
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Bills.button_addBom);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.select_package.get(2));
                boolean bomSelected = false;
                for (WebElement eItemList : oPage_Bills.list_packageOrServiceNameUnderDescriptionField) {
                    Cls_Generic_Methods.clickElement(eItemList);
                    bomSelected = true;
                }
                m_assert.assertTrue(bomSelected, "Selected BOM");
                Cls_Generic_Methods.customWait(4);
                //getting values from ui for service,Package,bom,net and gross total
                //String sQTY = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_Quantity.get(0), "value");
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
                        (totalGrossAmount == dNetAmtBeforeApplyGDUi),"Gross amt and Net Amt is equal before applying global discount: "+totalGrossAmount);


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
                double discountOnService = (dGlobalDiscount/totalGrossAmount)* dGrossPriceServiceUi;
                double dRoundOffDiscountOnService = roundOffFunctionUsingDouble(discountOnService);
                double discountOnPackage = (dGlobalDiscount/totalGrossAmount)* dGrossPricePackageUi;
                double dRoundOffDiscountOnPackage = roundOffFunctionUsingDouble(discountOnPackage);
                double discountOnBom = (dGlobalDiscount/totalGrossAmount)* dGrossPriceBomUi;
                double dRoundOffDiscountOnBom = roundOffFunctionUsingDouble(discountOnBom);
                double discountOnSubPack1 = (dGlobalDiscount/totalGrossAmount)* dGrossPriceOfFirstItemBeforeDiscount;
                double dRoundOffDiscountOnSubPack1 = roundOffFunctionUsingDouble(discountOnSubPack1);
                double discountOnSubPack2 = (dGlobalDiscount/totalGrossAmount)* dGrossPriceOfSecondItemBeforeDiscount;
                double dRoundOffDiscountOnSubPack2 = roundOffFunctionUsingDouble(discountOnSubPack2);
                double dTotalSubPackDiscount = dRoundOffDiscountOnSubPack1+dRoundOffDiscountOnSubPack2;
                double totalDiscountOnAll = dRoundOffDiscountOnService+dRoundOffDiscountOnPackage+dRoundOffDiscountOnBom;

                //getting discount from ui
                String sOverAllDiscountOnServiceUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(0)).replaceAll("\\s", "");
                double dOverAllDiscountOnServiceUi = Double.parseDouble(sOverAllDiscountOnServiceUi)*(-1);
                String sOverAllDiscountOnPackageUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(1)).replaceAll("\\s", "");
                double dOverAllDiscountOnPackageUi = Double.parseDouble(sOverAllDiscountOnPackageUi)*(-1);
                String sOverAllDiscountOnBomUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(2)).replaceAll("\\s", "");
                double dOverAllDiscountOnBomUi = Double.parseDouble(sOverAllDiscountOnBomUi)*(-1);

                m_assert.assertTrue(dRoundOffDiscountOnService == dOverAllDiscountOnServiceUi,
                        "Discount of service equals "+dRoundOffDiscountOnService);
                m_assert.assertTrue(dRoundOffDiscountOnPackage == dOverAllDiscountOnPackageUi,
                        "Discount of package equals "+dRoundOffDiscountOnPackage);
                m_assert.assertTrue(dRoundOffDiscountOnBom == dOverAllDiscountOnBomUi,
                        "Discount of bom equals "+dRoundOffDiscountOnBom);
                m_assert.assertTrue(dTotalSubPackDiscount == dOverAllDiscountOnPackageUi,
                        "Discount of sub package equals "+dTotalSubPackDiscount);

                //calculating gross amt after discount
                double dNetAmtServiceAfterDiscount = dGrossPriceServiceUi - dOverAllDiscountOnServiceUi;
                double dNetAmtPackageAfterDiscount = dGrossPricePackageUi - dOverAllDiscountOnPackageUi;
                double dNetAmtBomAfterDiscount = dGrossPriceBomUi - dOverAllDiscountOnBomUi;
                double dNetAmtSubPackFirstAfterDiscount = dGrossPriceOfFirstItemBeforeDiscount - dRoundOffDiscountOnSubPack1;
                double dNetAmtSubPackSecondAfterDiscount = dGrossPriceOfSecondItemBeforeDiscount - dRoundOffDiscountOnSubPack2;
                double calculatedNetBillTotalAfterDiscount = dNetAmtServiceAfterDiscount+dNetAmtPackageAfterDiscount+dNetAmtBomAfterDiscount;

                //getting net amt from ui after apply discount
                String sNetPriceServiceUiAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetPriceServiceUiAfterDiscount = Double.parseDouble(sNetPriceServiceUiAfterDiscount);
                String sNetPricePackageUiAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value");
                double dNetPricePackageUiAfterDiscount = Double.parseDouble(sNetPricePackageUiAfterDiscount);
                String sNetPriceBomUiAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(2), "value");
                double dNetPriceBomUiAfterDiscount = Double.parseDouble(sNetPriceBomUiAfterDiscount);
                String sNetPriceItemPriceForFirstItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0));
                String sNetPriceItemPriceForSecondItem = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1));
                double dNetPriceItemPriceForFirstItemUI = Double.parseDouble(sNetPriceItemPriceForFirstItem);
                double dNetPriceItemPriceForSecondItemUI = Double.parseDouble(sNetPriceItemPriceForSecondItem);

                m_assert.assertTrue(dNetPriceServiceUiAfterDiscount == dNetAmtServiceAfterDiscount,
                        "Net Price of service equals: "+dNetAmtServiceAfterDiscount);
                m_assert.assertTrue(dNetPricePackageUiAfterDiscount == dNetAmtPackageAfterDiscount,
                        "Net Price of package equals: "+dNetAmtPackageAfterDiscount);
                m_assert.assertTrue(dNetPriceBomUiAfterDiscount == dNetAmtBomAfterDiscount,
                        "Net Price of bom equals: "+dNetAmtBomAfterDiscount);
                m_assert.assertTrue(dNetPriceItemPriceForFirstItemUI + dNetPriceItemPriceForSecondItemUI == dNetAmtSubPackFirstAfterDiscount +dNetAmtSubPackSecondAfterDiscount,
                        "Net Price of bom equals: "+dNetAmtPackageAfterDiscount);

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
                        && dTotalDiscountOnItemsAfterApplyGDUi ==totalDiscountOnAll
                        &&dTotalDiscountAfterApplyGDUi == totalDiscountOnAll
                        && dTotalOfAllDiscountAfterApplyGDUi==totalDiscountOnAll
                && dNetAmtAfterApplyGDUi ==calculatedNetBillTotalAfterDiscount ,
                        "Gross bill on bill = <b>" + totalGrossAmount + "</b>"+
                                " Total discount on items = <b>" + totalDiscountOnAll + "</b>"+
                                " Total discount on items after apply discount = <b>" + totalDiscountOnAll + "</b>"+
                                " Total of all discounts  = <b>" + totalDiscountOnAll + "</b>"+
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
                double percentDiscountOnService = (dGlobalDiscountPercentage* dGrossPriceServiceUi)/100;
                double dRoundOffPercentDiscountOnService= roundOffFunctionUsingDouble(percentDiscountOnService);
                double percentDiscountOnPackage = (dGlobalDiscountPercentage* dGrossPricePackageUi)/100;
                double dRoundOffPercentDiscountOnPackage = roundOffFunctionUsingDouble(percentDiscountOnPackage);
                double percentDiscountOnBom = (dGlobalDiscountPercentage* dGrossPriceBomUi)/100;
                double dRoundOffPercentDiscountOnBom = roundOffFunctionUsingDouble(percentDiscountOnBom);
                double totalDiscountOnAllForPercent = dRoundOffPercentDiscountOnService+dRoundOffPercentDiscountOnPackage+dRoundOffPercentDiscountOnBom;

                //getting discount from ui of percentage
                String sOverAllPercentDiscountOnServiceUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(0)).replaceAll("\\s", "");
                double dOverAllPercentDiscountOnServiceUi = Double.parseDouble(sOverAllPercentDiscountOnServiceUi)*(-1);
                String sOverAllPercentDiscountOnPackageUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(1)).replaceAll("\\s", "");
                double dOverAllPercentDiscountOnPackageUi = Double.parseDouble(sOverAllPercentDiscountOnPackageUi)*(-1);
                String sOverAllPercentDiscountOnBomUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(2)).replaceAll("\\s", "");
                double dOverAllPercentDiscountOnBomUi = Double.parseDouble(sOverAllPercentDiscountOnBomUi)*(-1);



                m_assert.assertTrue(dRoundOffPercentDiscountOnService == dOverAllPercentDiscountOnServiceUi,
                        "Discount of service equals "+dOverAllPercentDiscountOnServiceUi);
                m_assert.assertTrue(dRoundOffPercentDiscountOnPackage == dOverAllPercentDiscountOnPackageUi,
                        "Discount of package equals "+dOverAllPercentDiscountOnPackageUi);
                m_assert.assertTrue(dRoundOffPercentDiscountOnBom == dOverAllPercentDiscountOnBomUi,
                        "Discount of bom equals "+dOverAllPercentDiscountOnBomUi);

                //calculating net amt after discount in percentage
                double dNetAmtServiceAfterPercentDiscount = dGrossPriceServiceUi - dOverAllPercentDiscountOnServiceUi;
                double dNetAmtPackageAfterPercentDiscount = dGrossPricePackageUi - dOverAllPercentDiscountOnPackageUi;
                double dNetAmtBomAfterPercentDiscount = dGrossPriceBomUi - dOverAllPercentDiscountOnBomUi;
                double calculatedNetBillTotalAfterPercentDiscount = dNetAmtServiceAfterPercentDiscount+dNetAmtPackageAfterPercentDiscount+dNetAmtBomAfterPercentDiscount;
                double dCalculatedNetBillTotalAfterPercentDiscount = roundOffFunctionUsingDouble(calculatedNetBillTotalAfterPercentDiscount);

                //getting net amt from ui after apply discount
                String sNetPriceServiceUiAfterPercentDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value");
                double dNetPriceServiceUiAfterPercentDiscount = Double.parseDouble(sNetPriceServiceUiAfterPercentDiscount);
                String sNetPricePackageUiAfterPercentDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value");
                double dNetPricePackageUiAfterPercentDiscount = Double.parseDouble(sNetPricePackageUiAfterPercentDiscount);
                String sNetPriceBomUiAfterPercentDiscount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(2), "value");
                double dNetPriceBomUiAfterPercentDiscount = Double.parseDouble(sNetPriceBomUiAfterPercentDiscount);

                m_assert.assertTrue(dNetPriceServiceUiAfterPercentDiscount == dNetAmtServiceAfterPercentDiscount,
                        "Net Price of service equals: "+dNetAmtServiceAfterPercentDiscount);
                m_assert.assertTrue(dNetPricePackageUiAfterPercentDiscount == dNetAmtPackageAfterPercentDiscount,
                        "Net Price of package equals: "+dNetAmtPackageAfterPercentDiscount);
                m_assert.assertTrue(dNetPriceBomUiAfterPercentDiscount == dNetAmtBomAfterPercentDiscount,
                        "Net Price of bom equals: "+dNetAmtBomAfterPercentDiscount);

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
                                && dTotalDiscountOnItemsAfterApplyGDPercentUi ==totalDiscountOnAllForPercent
                                && dTotalOfAllDiscountAfterApplyGDPercentUi==totalDiscountOnAllForPercent
                                && dNetAmtAfterApplyGDPercentUi ==dCalculatedNetBillTotalAfterPercentDiscount ,
                        "Gross bill on bill = <b>" + totalGrossAmount + "</b>"+
                                " Total discount on items = <b>" + totalDiscountOnAllForPercent + "</b>"+
                                " Total discount on items after apply discount = <b>" + totalDiscountOnAllForPercent + "</b>"+
                                " Total of all discounts  = <b>" + totalDiscountOnAllForPercent + "</b>"+
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
                double dDiscountAmtOfServiceOnPreviewUi = Double.parseDouble(sDiscountAmtOfServiceOnPreviewUi)* (-1);
                String sDiscountAmtOfPackageOnPreviewUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(1)).replaceAll("\\s", "");
                double dDiscountAmtOfPackageOnPreviewUi = Double.parseDouble(sDiscountAmtOfPackageOnPreviewUi)* (-1);
                String sDiscountAmtOfBomOnPreviewUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(2)).replaceAll("\\s", "");
                double dDiscountAmtOfBomOnPreviewUi = Double.parseDouble(sDiscountAmtOfBomOnPreviewUi)* (-1);


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
                                && dTotalDiscountOnItemsInBillPreview ==totalDiscountOnAllForPercent
                                && dTotalOfAllDiscountOnItemsInBillPreview==totalDiscountOnAllForPercent
                                && dPaymentReceivedOnBillPreview ==dCalculatedNetBillTotalAfterPercentDiscount ,
                        "Gross bill on bill preview = <b>" + totalGrossAmount + "</b>"+
                                " Total discount on items in bill preview = <b>" + totalDiscountOnAllForPercent + "</b>"+
                                " Total of all discounts on bill preview = <b>" + totalDiscountOnAllForPercent + "</b>"+
                                " Net bill total on bill preview = <b>" + dCalculatedNetBillTotalAfterPercentDiscount + "</b>");

                //Edit bill
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_editBill), "clicked on edit bill");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickIPDPlusActionButton, 30);
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_Bills.input_globalDiscountAmt),
                        "Global discount button is not there on editing");
            }


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }

    }

    @Test(enabled = true, description = "Create credit bill and validate the Global level  Discount")
    public void createCreditBillAndValidateGlobalLevelDiscount() {
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills = new Page_Bills(driver);
        oPage_IPD = new Page_IPD(driver);
        boolean bPatientFound = false;
        String paymentMode = "Cash";
        String sPackageName = "AutomationPackageDataDisplaySelf";
        String sGlobalDiscount = "10";
        String sDiscountReason = "Reason" + getRandomUniqueString(6);

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

            if (bPatientFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills), "Clicked on bills button");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_IPD.button_newCreditBill);
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
                //add 2nd package
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickIPDPlusActionButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Bills.button_newPackage);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.select_package.get(2));
                boolean myPackageFound2 = false;
                String sInvoiceSetPackageName = "Testing Facility";
                for (WebElement eItemList : oPage_Bills.list_packageOrServiceNameUnderDescriptionField) {
                    String sPackageNameOnUI = Cls_Generic_Methods.getTextInElement(eItemList);
                    if (sPackageNameOnUI.equals(sInvoiceSetPackageName)) {
                        Cls_Generic_Methods.clickElement(eItemList);
                        myPackageFound2 = true;
                        break;
                    }

                }
                m_assert.assertTrue(myPackageFound2, "Selected package name = <b>" + sInvoiceSetPackageName + "</b> ");

                //getting values from ui for service,Package,bom,net and gross total
                //String sQTY = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_Quantity.get(0), "value");
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
                double dGrossSubPackageItemPriceForSecondItemUi = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(1)));

                //validate before apply gd net and gross amt
                double totalGrossAmount =  dGrossPricePackageUi + dGrossPriceServiceUi+dGrossPriceItem3Ui;
                m_assert.assertTrue((totalGrossAmount == dGrossAmtBeforeApplyGDUi) &&
                        (totalGrossAmount == dNetAmtBeforeApplyGDUi),"Gross amt and Net Amt is equal before applying global discount: "+totalGrossAmount);

                double totalSubPackAmtBeforeGD = dGrossSubPackageItemPriceForFirstItemUi +dGrossSubPackageItemPriceForSecondItemUi;
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
                double dRoundOffDiscountOnSubPack2 = roundOffFunctionUsingDouble((dGlobalDiscount/totalGrossAmount)* dGrossSubPackageItemPriceForSecondItemUi);
                double dTotalSubPackDiscount = roundOffFunctionUsingDouble(dRoundOffDiscountOnSubPack1+dRoundOffDiscountOnSubPack2);
                double totalDiscountOnAll = dRoundOffDiscountOnService+dRoundOffDiscountOnPackage+dRoundOffDiscountOnItem3;
                totalDiscountOnAll = Math.round(totalDiscountOnAll/10.0) * 10;

                //getting discount from ui
                String sOverAllDiscountOnServiceUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(0)).replaceAll("\\s", "");
                double dOverAllDiscountOnServiceUi = Double.parseDouble(sOverAllDiscountOnServiceUi)*(-1);
                String sOverAllDiscountOnPackageUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(1)).replaceAll("\\s", "");
                double dOverAllDiscountOnPackageUi = Double.parseDouble(sOverAllDiscountOnPackageUi)*(-1);
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
                double dNetAmtSubPackSecondAfterDiscount = dGrossSubPackageItemPriceForSecondItemUi - dRoundOffDiscountOnSubPack2;
                double calculatedNetBillTotalAfterDiscount = dNetAmtServiceAfterDiscount+dNetAmtPackageAfterDiscount+dNetAmtItem3AfterDiscount;
                calculatedNetBillTotalAfterDiscount = Math.round(calculatedNetBillTotalAfterDiscount/10.0) * 10;

                //getting net amt from ui after apply discount
                double dNetPriceServiceUiAfterDiscount = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value"));
                double dNetPricePackageUiAfterDiscount = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value"));
                double dNetPriceItemPriceForFirstItemUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0)));
                double dNetPriceItemPriceForSecondItemUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1)));
                double dNetPriceItem3UiAfterDiscount = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(2), "value"));

                m_assert.assertTrue(dNetPriceServiceUiAfterDiscount == dNetAmtServiceAfterDiscount,
                        "Net Price of service equals: "+dNetAmtServiceAfterDiscount);
                m_assert.assertTrue(dNetPricePackageUiAfterDiscount == dNetAmtPackageAfterDiscount,
                        "Net Price of package equals: "+dNetAmtPackageAfterDiscount);
                m_assert.assertTrue(dNetPriceItem3UiAfterDiscount == dNetAmtItem3AfterDiscount,
                        "Net Price of item 3 equals: "+dNetAmtItem3AfterDiscount);
                m_assert.assertTrue(dNetPriceItemPriceForFirstItemUI + dNetPriceItemPriceForSecondItemUI == dNetAmtSubPackFirstAfterDiscount +dNetAmtSubPackSecondAfterDiscount,
                        "Net Price of sub pack equals: "+dNetAmtPackageAfterDiscount);
                m_assert.assertTrue(dNetAmtPackageAfterDiscount == dNetAmtSubPackFirstAfterDiscount +dNetAmtSubPackSecondAfterDiscount,
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

                double dAmountPendingOnUI = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_IPD.input_amountPendingField, "value"));
                m_assert.assertTrue((dAmountPendingOnUI == calculatedNetBillTotalAfterDiscount), "Net amount on bill equals to amount pending against bill is matching = <b>" + dAmountPendingOnUI + "</b>");

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
                double dRoundOffPercentDiscountOnSubPack2 = roundOffFunctionUsingDouble((dGlobalDiscountPercentage * dGrossSubPackageItemPriceForSecondItemUi)/100);
                double dTotalSubPackPercentDiscount = dRoundOffPercentDiscountOnSubPack1+dRoundOffPercentDiscountOnSubPack2;

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
                double dNetAmtSubPackSecondAfterPercentDiscount = dGrossSubPackageItemPriceForSecondItemUi - dRoundOffPercentDiscountOnSubPack2;
                double calculatedNetBillTotalAfterPercentDiscount = dNetAmtServiceAfterPercentDiscount+dNetAmtPackageAfterPercentDiscount+dNetAmtItem3AfterPercentDiscount;

                //getting net amt from ui after apply discount
                double dNetPriceServiceUiAfterPercentDiscount =  Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value"));
                double dNetPricePackageUiAfterPercentDiscount =  Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value"));
                double dNetPriceItem3UiAfterPercentDiscount =  Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(2), "value"));
                double dNetPriceSubPackage1UiAfterPercentDiscount =  Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0)));
                double dNetPriceSubPackage2UiAfterPercentDiscount =  Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1)));

                m_assert.assertTrue(dNetPriceServiceUiAfterPercentDiscount == dNetAmtServiceAfterPercentDiscount,
                        "Net Price of service equals: "+dNetAmtServiceAfterPercentDiscount);
                m_assert.assertTrue(dNetPricePackageUiAfterPercentDiscount == dNetAmtPackageAfterPercentDiscount,
                        "Net Price of package equals: "+dNetAmtPackageAfterPercentDiscount);
                m_assert.assertTrue(dNetPriceItem3UiAfterPercentDiscount == dNetAmtItem3AfterPercentDiscount,
                        "Net Price of item 3 equals: "+dNetAmtItem3AfterPercentDiscount);
                m_assert.assertTrue(dNetPriceSubPackage1UiAfterPercentDiscount == dNetAmtSubPackFirstAfterPercentDiscount,
                        "Net Price of sub package first item equals: "+dNetAmtSubPackFirstAfterPercentDiscount);
                m_assert.assertTrue(dNetPriceSubPackage2UiAfterPercentDiscount == dNetAmtSubPackSecondAfterPercentDiscount,
                        "Net Price of sub package second item equals: "+dNetAmtSubPackSecondAfterPercentDiscount);
                m_assert.assertTrue(dNetAmtSubPackFirstAfterPercentDiscount+ dNetAmtSubPackSecondAfterPercentDiscount == dNetAmtPackageAfterPercentDiscount,
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
                Cls_Generic_Methods.scrollToElementByJS(oPage_Bills.select_modeOfPayment);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_modeOfPayment, paymentMode), paymentMode + " option is selected for Mode Of Payment");

                double dAmountPendingOnUIForPercentDiscount = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_IPD.input_amountPendingField, "value"));
                m_assert.assertTrue((dAmountPendingOnUIForPercentDiscount == dCalculatedNetBillTotalAfterDeleteItem), "Net amount on bill equals to amount pending against bill is matching = <b>" + dCalculatedNetBillTotalAfterDeleteItem + "</b>");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills), "<b>Credit bill is created </b>");
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
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_editBill), "clicked on edit bill");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickIPDPlusActionButton, 30);
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_Bills.input_globalDiscountAmt),
                        "Global discount button is not there on editing");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_internalComments, "InternalComments");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills), "<b>CREDIT bill is updated </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.customWait(4);

                //cancel bill
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

    @Test(enabled = true, description = "Create credit bill, add invoice set and validate the Global level  Discount")
    public void createCashBillAndValidateGlobalLevelDiscountByAddingInvoiceSets() {
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_Bills = new Page_Bills(driver);
        oPage_IPD = new Page_IPD(driver);
        boolean bPatientFound = false;
        String paymentMode = "Cash";
        String sPackageName = "AutomationPackageDataDisplaySelf";
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

            if (bPatientFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills), "Clicked on bills button");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.button_cashBill);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 30);

                //select invoice sets
                Cls_Generic_Methods.selectElementByVisibleText(oPage_Bills.option_invoiceSets, sInvoiceSetName);
                Cls_Generic_Methods.customWait(4);

                //add package
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickIPDPlusActionButton);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Bills.button_newPackage);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElement(oPage_Bills.select_package.get(0));
                boolean myPackageFound = false;
                String sInvoiceSetPackageName = "Testing Facility";
                for (WebElement eItemList : oPage_Bills.list_packageOrServiceNameUnderDescriptionField) {
                    String sPackageNameOnUI = Cls_Generic_Methods.getTextInElement(eItemList);
                    if (sPackageNameOnUI.equals(sInvoiceSetPackageName)) {
                        Cls_Generic_Methods.clickElement(eItemList);
                        myPackageFound = true;
                        break;
                    }

                }
                m_assert.assertTrue(myPackageFound, "Selected package name = <b>" + sInvoiceSetPackageName + "</b> ");


                //getting values from ui for service,Package,bom,net and gross total
                //String sQTY = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_Quantity.get(0), "value");
                String sUnitPriceServiceUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(0), "value");
                double dGrossPriceServiceUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(0), "value"));
                double dNetPriceServiceUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value"));
                double dGrossSubPackageItemPriceForFirstItemUi = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(0)));
                double dGrossSubPackageItemPriceForSecondItemUi = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(1)));
                double dGrossPriceItem2Ui = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(1), "value"));
                double dNetPriceItem2Ui = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value"));
                double dGrossTotalAmtBeforeApplyGDUi =  Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_grossBillTotal, "value"));
                double dNetAmtBeforeApplyGDUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_netBillTotal, "value"));

                double totalGrossAmount = dGrossPriceServiceUi + dGrossPriceItem2Ui;

                        //validate before apply gd net and gross amt
                m_assert.assertTrue((dGrossTotalAmtBeforeApplyGDUi == totalGrossAmount) &&
                        (dNetAmtBeforeApplyGDUi == totalGrossAmount),"Gross amt and Net Amt is equal before applying global discount: "+totalGrossAmount);

                double totalSubPackAmtBeforeGD = dGrossSubPackageItemPriceForFirstItemUi +dGrossSubPackageItemPriceForSecondItemUi;
                m_assert.assertTrue(totalSubPackAmtBeforeGD == dGrossPriceServiceUi,
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
                double dRoundOffDiscountOnService = roundOffFunctionUsingDouble((dGlobalDiscount/dGrossTotalAmtBeforeApplyGDUi)* dGrossPriceServiceUi);
                double dRoundOffDiscountOnSubPack1 = roundOffFunctionUsingDouble((dGlobalDiscount/dGrossTotalAmtBeforeApplyGDUi)* dGrossSubPackageItemPriceForFirstItemUi);
                double dRoundOffDiscountOnSubPack2 = roundOffFunctionUsingDouble((dGlobalDiscount/dGrossTotalAmtBeforeApplyGDUi)* dGrossSubPackageItemPriceForSecondItemUi);
                double dTotalSubPackDiscount = roundOffFunctionUsingDouble(dRoundOffDiscountOnSubPack1+dRoundOffDiscountOnSubPack2);
                double dRoundOffDiscountOnService2 = roundOffFunctionUsingDouble((dGlobalDiscount/dGrossTotalAmtBeforeApplyGDUi)* dGrossPriceItem2Ui);
                double totalDiscountOnAll = dRoundOffDiscountOnService+dRoundOffDiscountOnService2;

                //getting discount from ui
                double dOverAllDiscountOnServiceUi = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(0)).replaceAll("\\s", ""))*(-1);
                double sOverAllDiscountOnService2Ui = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(1)).replaceAll("\\s", ""))*(-1);

                m_assert.assertTrue(dRoundOffDiscountOnService == dOverAllDiscountOnServiceUi,
                        "Discount of invoice set equals "+dRoundOffDiscountOnService);
                m_assert.assertTrue(dTotalSubPackDiscount == dOverAllDiscountOnServiceUi,
                        "Discount of sub package equals "+dTotalSubPackDiscount);
                m_assert.assertTrue(dRoundOffDiscountOnService2 == sOverAllDiscountOnService2Ui,
                        "Discount of sub package equals "+dRoundOffDiscountOnService2);

                //calculating net amt after discount
                double dNetAmtServiceAfterDiscount = dGrossPriceServiceUi - dRoundOffDiscountOnService;
                double dNetAmtSubPackFirstAfterDiscount = dGrossSubPackageItemPriceForFirstItemUi - dRoundOffDiscountOnSubPack1;
                double dNetAmtSubPackSecondAfterDiscount = dGrossSubPackageItemPriceForSecondItemUi - dRoundOffDiscountOnSubPack2;
                double dNetAmtService2AfterDiscount = dGrossPriceItem2Ui - dRoundOffDiscountOnService2;
                double calculatedNetBillTotalAfterDiscount = dNetAmtServiceAfterDiscount + dNetAmtService2AfterDiscount;

                //getting net amt from ui after apply discount
                double dNetPriceServiceUiAfterDiscount = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value"));
                double dNetPriceItemPriceForFirstItemUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0)));
                double dNetPriceItemPriceForSecondItemUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1)));
                double dNetPriceService2UiAfterDiscount = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value"));

                m_assert.assertTrue(dNetPriceServiceUiAfterDiscount == dNetAmtServiceAfterDiscount,
                        "Net Price of service equals: "+dNetAmtServiceAfterDiscount);
                m_assert.assertTrue(dNetPriceItemPriceForFirstItemUI + dNetPriceItemPriceForSecondItemUI == dNetAmtSubPackFirstAfterDiscount +dNetAmtSubPackSecondAfterDiscount,
                        "Net Price of sub pack equals: "+dNetAmtServiceAfterDiscount);
                m_assert.assertTrue(dNetAmtServiceAfterDiscount == dNetAmtSubPackFirstAfterDiscount +dNetAmtSubPackSecondAfterDiscount,
                        "Net Price of sub package and package equals: "+dNetAmtServiceAfterDiscount);
                m_assert.assertTrue(dNetPriceService2UiAfterDiscount == dNetAmtService2AfterDiscount,
                        "Net Price of service equals: "+dNetAmtService2AfterDiscount);

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

                Cls_Generic_Methods.scrollToElementByJS(oPage_Bills.select_modeOfPayment);
                double dAmountPendingOnUI = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_amountReceivedField.get(0), "value"));
                m_assert.assertTrue((dAmountPendingOnUI == calculatedNetBillTotalAfterDiscount), "Net amount on bill equals to amount received is matching = <b>" + calculatedNetBillTotalAfterDiscount + "</b>");

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
                double dRoundOffPercentDiscountOnService2 = roundOffFunctionUsingDouble((dGlobalDiscountPercentage* dGrossPriceItem2Ui)/100);
                double dRoundOffPercentDiscountOnSubPack1 = roundOffFunctionUsingDouble((dGlobalDiscountPercentage * dGrossSubPackageItemPriceForFirstItemUi)/100);
                double dRoundOffPercentDiscountOnSubPack2 = roundOffFunctionUsingDouble((dGlobalDiscountPercentage * dGrossSubPackageItemPriceForSecondItemUi)/100);
                double dTotalSubPackPercentDiscount = dRoundOffPercentDiscountOnSubPack1+dRoundOffPercentDiscountOnSubPack2;
                double totalDiscountOnAllForPercent = dRoundOffPercentDiscountOnService+dRoundOffPercentDiscountOnService2;

                //getting discount from ui of percentage
                String sOverAllPercentDiscountOnServiceUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(0)).replaceAll("\\s", "");
                double dOverAllPercentDiscountOnServiceUi = Double.parseDouble(sOverAllPercentDiscountOnServiceUi)*(-1);
                double dOverAllPercentDiscountOnService2Ui = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(1)).replaceAll("\\s", ""))*(-1);
                double sFirstSubpackageItemDiscountAmountUi = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0)))*(-1);
                double sSecondSubpackageItemDiscountAmountUi =  Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(1)))*(-1);

                m_assert.assertTrue(dRoundOffPercentDiscountOnService == dOverAllPercentDiscountOnServiceUi,
                        "Discount of service equals in invoice set: "+dOverAllPercentDiscountOnServiceUi);
                m_assert.assertTrue(dRoundOffPercentDiscountOnService2 == dOverAllPercentDiscountOnService2Ui,
                        "Discount of service equals in invoice set: "+dOverAllPercentDiscountOnService2Ui);
                m_assert.assertTrue(dTotalSubPackPercentDiscount == sFirstSubpackageItemDiscountAmountUi+sSecondSubpackageItemDiscountAmountUi,
                        "Discount of sub package equals "+dTotalSubPackPercentDiscount);

                //calculating net amt after discount in percentage
                double dNetAmtServiceAfterPercentDiscount = dGrossPriceServiceUi - dRoundOffPercentDiscountOnService;
                double dNetAmtService2AfterPercentDiscount = dGrossPriceItem2Ui - dRoundOffPercentDiscountOnService2;
                double dNetAmtSubPackFirstAfterPercentDiscount = dGrossSubPackageItemPriceForFirstItemUi - dRoundOffPercentDiscountOnSubPack1;
                double dNetAmtSubPackSecondAfterPercentDiscount = dGrossSubPackageItemPriceForSecondItemUi - dRoundOffPercentDiscountOnSubPack2;
                double calculatedNetBillTotalAfterPercentDiscount = dNetAmtServiceAfterPercentDiscount + dNetAmtService2AfterPercentDiscount;

                //getting net amt from ui after apply discount
                double dNetPriceServiceUiAfterPercentDiscount =  Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value"));
                double dNetPriceService2UiAfterPercentDiscount =  Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(1), "value"));
                double dNetPriceSubPackage1UiAfterPercentDiscount =  Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0)));
                double dNetPriceSubPackage2UiAfterPercentDiscount =  Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1)));

                m_assert.assertTrue(dNetPriceServiceUiAfterPercentDiscount == dNetAmtServiceAfterPercentDiscount,
                        "Net Price of service equals: "+dNetAmtServiceAfterPercentDiscount);
                m_assert.assertTrue(dNetPriceService2UiAfterPercentDiscount == dNetAmtService2AfterPercentDiscount,
                        "Net Price of service equals: "+dNetAmtService2AfterPercentDiscount);
                m_assert.assertTrue(dNetPriceSubPackage1UiAfterPercentDiscount == dNetAmtSubPackFirstAfterPercentDiscount,
                        "Net Price of sub package first item equals: "+dNetAmtSubPackFirstAfterPercentDiscount);
                m_assert.assertTrue(dNetPriceSubPackage2UiAfterPercentDiscount == dNetAmtSubPackSecondAfterPercentDiscount,
                        "Net Price of sub package second item equals: "+dNetAmtSubPackSecondAfterPercentDiscount);
                m_assert.assertTrue(dNetAmtSubPackFirstAfterPercentDiscount+ dNetAmtSubPackSecondAfterPercentDiscount == dNetAmtServiceAfterPercentDiscount,
                        "Net Price of sub package and package equals: "+dNetAmtServiceAfterPercentDiscount);

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


                //delete 2nd item
                Cls_Generic_Methods.clickElement(oPage_Bills.button_deleteItemPackage.get(1));
                Cls_Generic_Methods.customWait(4);
                totalGrossAmount = totalGrossAmount - dGrossPriceItem2Ui;
                double dCalculatedNetBillTotalAfterDeleting  = dCalculatedNetBillTotalAfterPercentDiscount - dNetAmtService2AfterPercentDiscount;
                totalDiscountOnAllForPercent = totalDiscountOnAllForPercent - dRoundOffPercentDiscountOnService2;

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
                Cls_Generic_Methods.scrollToElementByJS(oPage_Bills.select_modeOfPayment);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_modeOfPayment, paymentMode), paymentMode + " option is selected for Mode Of Payment");
                double dAmountReceivedOnUIDiscountPercent = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_amountReceivedField.get(0), "value"));
                m_assert.assertTrue((dAmountReceivedOnUIDiscountPercent == dCalculatedNetBillTotalAfterDeleting), "Net amount on bill equals to amount received against bill is matching = <b>" + dCalculatedNetBillTotalAfterDeleteItem + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills), "<b>Cash bill is created </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.customWait(4);

                //Validation on preview discount
                String sDiscountAmtOfServiceOnPreviewUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(0)).replaceAll("\\s", "");
                double dDiscountAmtOfServiceOnPreviewUi = Double.parseDouble(sDiscountAmtOfServiceOnPreviewUi)* (-1);

                m_assert.assertTrue((dDiscountAmtOfServiceOnPreviewUi == dRoundOffPercentDiscountOnService),
                        "Service discount amount on bill preview equals = <b>" + dRoundOffPercentDiscountOnService + "</b>");

                //gross,net and total discount validation on preview
                double dGrossAmtOnPreview = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_grossBillTotalOnBillPreview));
                double dTotalDiscountOnItemsInBillPreview = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalDiscountOnItemsOnBillPreview));
                double dTotalOfAllDiscountOnItemsInBillPreview = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalOfAllDiscountOnBillPreview));
                double dPaymentReceivedOnBillPreview = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_paymentReceivedOnBillPreview));


                m_assert.assertTrue(dGrossAmtOnPreview == totalGrossAmount
                                && dTotalDiscountOnItemsInBillPreview ==totalDiscountOnAllForPercent
                                && dTotalOfAllDiscountOnItemsInBillPreview==totalDiscountOnAllForPercent
                                && dPaymentReceivedOnBillPreview == dCalculatedNetBillTotalAfterDeleting ,
                        "Gross bill on bill preview = <b>" + totalGrossAmount + "</b>"+
                                " Total discount on items in bill preview = <b>" + totalDiscountOnAllForPercent + "</b>"+
                                " Total of all discounts on bill preview = <b>" + totalDiscountOnAllForPercent + "</b>"+
                                " Net bill total on bill preview = <b>" + dCalculatedNetBillTotalAfterDeleting + "</b>");

                //Edit bill
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_editBill), "clicked on edit bill");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickIPDPlusActionButton, 30);
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_Bills.input_globalDiscountAmt),
                        "Global discount button is not there on editing");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_internalComments, "InternalComments");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills), "<b>CREDIT bill is updated </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.customWait(4);
            }


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }

    }
    @Test(enabled = true, description = "Create template bill and validate the Global level  Discount")
    public void createTemplateBillAndValidateGlobalLevelDiscount() {
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        oPage_Bills = new Page_Bills(driver);
        oPage_IPD = new Page_IPD(driver);
        boolean bPatientFound = false;
        String paymentMode = "Cash";
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

            if (bPatientFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_ipdTemplateBills, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_ipdTemplateBills),
                        "<b>₹ Template Bills</b> Button is clicked");
                boolean templateBillFound = false;
                for (WebElement eTemplateBillList : oPage_Bills.list_templateBills) {
                    if (Cls_Generic_Methods.getTextInElement(eTemplateBillList).equals(sInvoiceSetName)) {
                        Cls_Generic_Methods.clickElement(eTemplateBillList);
                        templateBillFound = true;
                        m_assert.assertTrue(templateBillFound, "Selected template bill =  <b>" + sInvoiceSetName + "</b> ");
                        break;
                    }
                }
                Cls_Generic_Methods.customWait(4);

                //getting values from ui for service,Package,bom,net and gross total
                //String sQTY = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_Quantity.get(0), "value");
                String sUnitPriceServiceUi = Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_unitPrice.get(0), "value");
                double dGrossPriceServiceUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_grossPrice.get(0), "value"));
                double dNetPriceServiceUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value"));
                double dGrossTotalAmtBeforeApplyGDUi =  Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_grossBillTotal, "value"));
                double dNetAmtBeforeApplyGDUi = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_netBillTotal, "value"));
                double dGrossSubPackageItemPriceForFirstItemUi = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(0)));
                double dGrossSubPackageItemPriceForSecondItemUi = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemGrossPrice.get(1)));
                double totalGrossAmount = dGrossTotalAmtBeforeApplyGDUi;

                //validate before apply gd net and gross amt
                m_assert.assertTrue((dGrossPriceServiceUi == dGrossTotalAmtBeforeApplyGDUi) &&
                        (dNetPriceServiceUi == dNetAmtBeforeApplyGDUi),"Gross amt and Net Amt is equal before applying global discount: "+dNetPriceServiceUi);

                double totalSubPackAmtBeforeGD = dGrossSubPackageItemPriceForFirstItemUi +dGrossSubPackageItemPriceForSecondItemUi;
                m_assert.assertTrue(totalSubPackAmtBeforeGD == dGrossTotalAmtBeforeApplyGDUi,
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
                double dRoundOffDiscountOnService = roundOffFunctionUsingDouble((dGlobalDiscount/dGrossTotalAmtBeforeApplyGDUi)* dGrossPriceServiceUi);
                double dRoundOffDiscountOnSubPack1 = roundOffFunctionUsingDouble((dGlobalDiscount/dGrossTotalAmtBeforeApplyGDUi)* dGrossSubPackageItemPriceForFirstItemUi);
                double dRoundOffDiscountOnSubPack2 = roundOffFunctionUsingDouble((dGlobalDiscount/dGrossTotalAmtBeforeApplyGDUi)* dGrossSubPackageItemPriceForSecondItemUi);
                double dTotalSubPackDiscount = roundOffFunctionUsingDouble(dRoundOffDiscountOnSubPack1+dRoundOffDiscountOnSubPack2);
                double totalDiscountOnAll = dRoundOffDiscountOnService;

                //getting discount from ui
                String sOverAllDiscountOnServiceUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(0)).replaceAll("\\s", "");
                double dOverAllDiscountOnServiceUi = Double.parseDouble(sOverAllDiscountOnServiceUi)*(-1);

                m_assert.assertTrue(dRoundOffDiscountOnService == dOverAllDiscountOnServiceUi,
                        "Discount of invoice set equals "+dRoundOffDiscountOnService);
                m_assert.assertTrue(dTotalSubPackDiscount == dOverAllDiscountOnServiceUi,
                        "Discount of sub package equals "+dTotalSubPackDiscount);

                //calculating net amt after discount
                double dNetAmtServiceAfterDiscount = dGrossPriceServiceUi - dRoundOffDiscountOnService;
                double dNetAmtSubPackFirstAfterDiscount = dGrossSubPackageItemPriceForFirstItemUi - dRoundOffDiscountOnSubPack1;
                double dNetAmtSubPackSecondAfterDiscount = dGrossSubPackageItemPriceForSecondItemUi - dRoundOffDiscountOnSubPack2;
                double calculatedNetBillTotalAfterDiscount = dNetAmtServiceAfterDiscount;

                //getting net amt from ui after apply discount
                double dNetPriceServiceUiAfterDiscount = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value"));
                double dNetPriceItemPriceForFirstItemUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0)));
                double dNetPriceItemPriceForSecondItemUI = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1)));

                m_assert.assertTrue(dNetPriceServiceUiAfterDiscount == dNetAmtServiceAfterDiscount,
                        "Net Price of service equals: "+dNetAmtServiceAfterDiscount);
                m_assert.assertTrue(dNetPriceItemPriceForFirstItemUI + dNetPriceItemPriceForSecondItemUI == dNetAmtSubPackFirstAfterDiscount +dNetAmtSubPackSecondAfterDiscount,
                        "Net Price of sub pack equals: "+dNetAmtServiceAfterDiscount);
                m_assert.assertTrue(dNetAmtServiceAfterDiscount == dNetAmtSubPackFirstAfterDiscount +dNetAmtSubPackSecondAfterDiscount,
                        "Net Price of sub package and package equals: "+dNetAmtServiceAfterDiscount);

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

                Cls_Generic_Methods.scrollToElementByJS(oPage_Bills.select_modeOfPayment);
                double sAmountRemainingOnUI = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_IPD.input_amountRemainingField, "value"));
                m_assert.assertTrue((sAmountRemainingOnUI == calculatedNetBillTotalAfterDiscount), "Net amount on bill equals to amount received is matching = <b>" + calculatedNetBillTotalAfterDiscount + "</b>");

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
                double totalDiscountOnAllForPercent = dRoundOffPercentDiscountOnService;
                double dRoundOffPercentDiscountOnSubPack1 = roundOffFunctionUsingDouble((dGlobalDiscountPercentage * dGrossSubPackageItemPriceForFirstItemUi)/100);
                double dRoundOffPercentDiscountOnSubPack2 = roundOffFunctionUsingDouble((dGlobalDiscountPercentage * dGrossSubPackageItemPriceForSecondItemUi)/100);
                double dTotalSubPackPercentDiscount = dRoundOffPercentDiscountOnSubPack1+dRoundOffPercentDiscountOnSubPack2;

                //getting discount from ui of percentage
                String sOverAllPercentDiscountOnServiceUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.list_text_overallDiscountAmountOnPackage.get(0)).replaceAll("\\s", "");
                double dOverAllPercentDiscountOnServiceUi = Double.parseDouble(sOverAllPercentDiscountOnServiceUi)*(-1);
                double sFirstSubpackageItemDiscountAmountUi = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(0)))*(-1);
                double sSecondSubpackageItemDiscountAmountUi =  Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.list_subpackageItemLevelDiscountAmount.get(1)))*(-1);

                m_assert.assertTrue(dRoundOffPercentDiscountOnService == dOverAllPercentDiscountOnServiceUi,
                        "Discount of service equals in invoice set: "+dOverAllPercentDiscountOnServiceUi);
                m_assert.assertTrue(dTotalSubPackPercentDiscount == sFirstSubpackageItemDiscountAmountUi+sSecondSubpackageItemDiscountAmountUi,
                        "Discount of sub package equals "+dTotalSubPackPercentDiscount);

                //calculating net amt after discount in percentage
                double dNetAmtServiceAfterPercentDiscount = dGrossPriceServiceUi - dRoundOffPercentDiscountOnService;
                double dNetAmtSubPackFirstAfterPercentDiscount = dGrossSubPackageItemPriceForFirstItemUi - dRoundOffPercentDiscountOnSubPack1;
                double dNetAmtSubPackSecondAfterPercentDiscount = dGrossSubPackageItemPriceForSecondItemUi - dRoundOffPercentDiscountOnSubPack2;
                double calculatedNetBillTotalAfterPercentDiscount = dNetAmtServiceAfterPercentDiscount;

                //getting net amt from ui after apply discount
                double dNetPriceServiceUiAfterPercentDiscount =  Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_Bills.input_netAmount.get(0), "value"));
                double dNetPriceSubPackage1UiAfterPercentDiscount =  Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(0)));
                double dNetPriceSubPackage2UiAfterPercentDiscount =  Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_subPackageItemNetPrice.get(1)));

                m_assert.assertTrue(dNetPriceServiceUiAfterPercentDiscount == dNetAmtServiceAfterPercentDiscount,
                        "Net Price of service equals: "+dNetAmtServiceAfterPercentDiscount);
                m_assert.assertTrue(dNetPriceSubPackage1UiAfterPercentDiscount == dNetAmtSubPackFirstAfterPercentDiscount,
                        "Net Price of sub package first item equals: "+dNetAmtSubPackFirstAfterPercentDiscount);
                m_assert.assertTrue(dNetPriceSubPackage2UiAfterPercentDiscount == dNetAmtSubPackSecondAfterPercentDiscount,
                        "Net Price of sub package second item equals: "+dNetAmtSubPackSecondAfterPercentDiscount);
                m_assert.assertTrue(dNetAmtSubPackFirstAfterPercentDiscount+ dNetAmtSubPackSecondAfterPercentDiscount == dNetAmtServiceAfterPercentDiscount,
                        "Net Price of sub package and package equals: "+dNetAmtServiceAfterPercentDiscount);

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


                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_discountReason.get(0), sDiscountReason);
                Cls_Generic_Methods.scrollToElementByJS(oPage_Bills.select_modeOfPayment);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_modeOfPayment, paymentMode), paymentMode + " option is selected for Mode Of Payment");
                double dAmountRemainingOnUIDiscountPercent = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_IPD.input_amountRemainingField, "value"));
                m_assert.assertTrue((dAmountRemainingOnUIDiscountPercent == dCalculatedNetBillTotalAfterPercentDiscount), "Net amount on bill equals to amount received against bill is matching = <b>" + dCalculatedNetBillTotalAfterPercentDiscount + "</b>");

                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.input_enteredAmountReceivedField);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_enteredAmountReceivedField, String.valueOf(dAmountRemainingOnUIDiscountPercent));
                Cls_Generic_Methods.customWait(2);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_saveRecieptTemplate), "<b>Cash bill is created </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.customWait(4);

                //Validation on preview discount
                String sDiscountAmtOfServiceOnPreviewUi = Cls_Generic_Methods.getTextInElement(oPage_Bills.List_overAllPackageItemDiscountOnBillPreview.get(0)).replaceAll("\\s", "");
                double dDiscountAmtOfServiceOnPreviewUi = Double.parseDouble(sDiscountAmtOfServiceOnPreviewUi)* (-1);

                m_assert.assertTrue((dDiscountAmtOfServiceOnPreviewUi == dRoundOffPercentDiscountOnService),
                        "Service discount amount on bill preview equals = <b>" + dRoundOffPercentDiscountOnService + "</b>");

                //gross,net and total discount validation on preview
                double dGrossAmtOnPreview = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_grossBillTotalOnBillPreview));
                double dTotalDiscountOnItemsInBillPreview = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalDiscountOnItemsOnBillPreview));
                double dTotalOfAllDiscountOnItemsInBillPreview = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_totalOfAllDiscountOnBillPreview));
                double dPaymentPendingOnBillPreview = Double.parseDouble(Cls_Generic_Methods.getTextInElement(oPage_Bills.text_paymentReceivedOnBillPreview));


                m_assert.assertTrue(dGrossAmtOnPreview == totalGrossAmount
                                && dTotalDiscountOnItemsInBillPreview ==totalDiscountOnAllForPercent
                                && dTotalOfAllDiscountOnItemsInBillPreview==totalDiscountOnAllForPercent
                                && dPaymentPendingOnBillPreview == dCalculatedNetBillTotalAfterPercentDiscount ,
                        "Gross bill on bill preview = <b>" + totalGrossAmount + "</b>"+
                                " Total discount on items in bill preview = <b>" + totalDiscountOnAllForPercent + "</b>"+
                                " Total of all discounts on bill preview = <b>" + totalDiscountOnAllForPercent + "</b>"+
                                " Net bill total on bill preview = <b>" + dCalculatedNetBillTotalAfterPercentDiscount + "</b>");

                //Edit bill
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_editBill), "clicked on edit bill");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickIPDPlusActionButton, 30);
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_Bills.input_globalDiscountAmt),
                        "Global discount button is not there on editing");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_internalComments, "InternalComments");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills), "<b>template bill is updated </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.customWait(4);
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
