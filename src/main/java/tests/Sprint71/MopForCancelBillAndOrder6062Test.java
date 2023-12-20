package tests.Sprint71;



import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.Sprint71.Page_MopForCanceBillAndOrder6062;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.store.PharmacyStore.Order.Page_SalesOrder;
import pages.store.PharmacyStore.Transaction.Page_Sale;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

    public class MopForCancelBillAndOrder6062Test extends TestBase {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Date date = new Date();

        String date1 = dateFormat.format(date);
        static String myPatientName = "Virat";
        static String mobileNumber = "9902109662";
        static String input_Qty = "1.0";
        static String modeOfPayment = "Cash";

        static String sTransactionID = "T123ID";
        static String sTransactionNote = "T123Note";

        static String sReasonForCancellation = "Patient requesting to cancel";

        @Test(enabled = true, description = "Validate Mode Of Payment selection For Cancel Pharmacy Bill ")
        public void pharmacyBillCancellationWithMopSelectionTest() {
            String sPHARMACY_STORE = "Pharmacy automation- Pharmacy";
            try {
                Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
                Page_Sale oPage_Sale = new Page_Sale(driver);
                Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
                Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
                Page_MopForCanceBillAndOrder6062 oPage_MopForCanceBillAndOrder6062 = new Page_MopForCanceBillAndOrder6062(driver);
                CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
                CommonActions.selectStoreOnApp(sPHARMACY_STORE);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();

                try {
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Sale.button_PlaceOrder, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.button_PlaceOrder), "<b>Place Order</b> button clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_addNewButtonInOrder, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addNewButtonInOrder), "Clicked on <b>New</b> Button to do sales order");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.list_PatientSearch, 20);
                    try {
                        //Adding patient for sales order
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.button_addNewPatientButton, 20);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_MopForCanceBillAndOrder6062.button_addNewPatientButton), "Clicked on add new patient");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, 20);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatientName + date1), "First name entered as = <b>" + myPatientName + date1 + "</b>");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, 20);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, mobileNumber), "Mobile number entered as = <b>" + mobileNumber + "</b>");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.button_savePatient, 20);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_MopForCanceBillAndOrder6062.button_savePatient), "Clicked on save button to save patient");
                        Cls_Generic_Methods.customWait(3);
                        try {
                            //Selecting medication for Sales Order
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_description, 20);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_description), "Sales Order page opened and Description button clicked");
                            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_SalesOrder.getList_namesOfMedicinesOnLeftInSearchResult, 10);
                            boolean myMedicationFound = false;
                            Cls_Generic_Methods.customWait(3);
                            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_searchMedicineNameInDescription, "SalesOrderTest1"),
                                    "Entering the medication name as" + "SalesOrderTest1" + " in description textbox for sales order");
                            Cls_Generic_Methods.customWait(3);
                            oPage_SalesOrder.input_searchMedicineNameInDescription.sendKeys(Keys.ENTER);
                            Cls_Generic_Methods.customWait(3);
                            Cls_Generic_Methods.customWait(5);

                            for (WebElement eMedication : oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult) {
                                if(Cls_Generic_Methods.isElementDisplayed(eMedication)){
                                    if (Cls_Generic_Methods.getTextInElement(eMedication).contains("SalesOrderTest1")) {
                                        Cls_Generic_Methods.clickElement(eMedication);
                                        myMedicationFound = true;
                                        Cls_Generic_Methods.customWait(4);
                                        break;
                                    }
                                }
                            }
                            m_assert.assertTrue(myMedicationFound, "Required medication " + "SalesOrderTest1" + " found for Sales Order");

                            try {
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_quantityOfMedicine, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_quantityOfMedicine, input_Qty), "Entered required quantity <b>" + input_Qty + "</b> for sales Order");
                                String sBalancePending = oPage_SalesOrder.text_balancePendingAmount.getAttribute("value");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.select_modeOfPayment, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_SalesOrder.select_modeOfPayment, modeOfPayment), "Required mode of payment <b>(" + modeOfPayment + ")</b> selected");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_amountPaidInCash, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_amountPaidInCash, sBalancePending), "Entered the amount <b>" + sBalancePending + "</b> to be paid in cash");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_saveChangesOnSalesOrder, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_saveChangesOnSalesOrder), "Clicked on <b>save</b> sales order button");
                                Cls_Generic_Methods.customWait(15);
                                Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_SalesOrder.button_closeModalOfSalesOrder, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_closeModalOfSalesOrder), "Closing the sales order modal");
                                Cls_Generic_Methods.customWait(3);
                                //selecting sales order by validating patient name
                                boolean salesOrderFound = false;
                                for (WebElement ePatientName : oPage_MopForCanceBillAndOrder6062.list_salesTransactionInInventory) {
                                    Cls_Generic_Methods.customWait(5);
                                    String PatientNameAndMobileNum = ePatientName.getText();
                                    System.out.println(PatientNameAndMobileNum);
                                    String[] GetPatientName = PatientNameAndMobileNum.split("\\n");
                                    String PatientName = GetPatientName[0];
                                    if (PatientName.equalsIgnoreCase(myPatientName + date1)) {
                                        ePatientName.click();
                                        salesOrderFound = true;
                                        break;
                                    }

                                }
                                m_assert.assertTrue(salesOrderFound, "Select Mode Of Payment for Cancel Optical bill");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.button_moreAction, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_MoreAction), "Clicked on <b>more action</b> button");
                                //Validate sales transaction is present under sales transaction folder
                                boolean salesTransactionFound = false;
                                Cls_Generic_Methods.customWait(3);
                                for (WebElement ePatientName : oPage_MopForCanceBillAndOrder6062.list_salesTransactionInInventory) {
                                    Cls_Generic_Methods.customWait(5);
                                    String PatientNameAndMobileNum = ePatientName.getText();
                                    System.out.println(PatientNameAndMobileNum);
                                    String[] GetPatientName = PatientNameAndMobileNum.split("\\n");
                                    String PatientName = GetPatientName[0];
                                    if (PatientName.equalsIgnoreCase(myPatientName + date1)) {
                                        ePatientName.click();
                                        salesTransactionFound = true;
                                        break;
                                    }

                                }
                                m_assert.assertTrue(salesTransactionFound, "sale transaction found under sales page");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.button_cancelBill, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_MopForCanceBillAndOrder6062.button_cancelBill), "Clicked on <b>Cancel bill</b> button");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.button_cancelBillInBillDetailsModal, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.checkbox_acceptCheckBox),
                                        " CheckBox Clicked In Cancel");
                                Cls_Generic_Methods.customWait();
                                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Sale.select_modeOfPaymentInCancel,"Cash"),
                                        " Mode Of Payment Selected In Cancel as Cash");
                                String sNetAmount = Cls_Generic_Methods.getTextInElement(oPage_MopForCanceBillAndOrder6062.text_netAmount);

                                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_amountReceivedInCancel,sNetAmount.trim()),
                                        "Amount Entered as  In Cancel as "+sNetAmount);
                                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_cancelReason,"Checking For Cancellation"),
                                        "Cancel Reason Entered as : Checking For Cancellation");
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.button_cancelBillInCancel),
                                        " Cancel Bill Button Clicked");
                                Cls_Generic_Methods.customWait(2);
                                Cls_Generic_Methods.clickElement(oPage_Sale.button_cancelBillYesInCancel);
                                Cls_Generic_Methods.customWait(5);
                               // Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.text_netAmount, 20);
                               // double dNetAmount = Double.parseDouble(sNetAmount);
                               /* String sAmountToBeRefunded = Cls_Generic_Methods.getElementAttribute(oPage_MopForCanceBillAndOrder6062.input_amountToBeRefund, "value");
                                double dAmountToBeRefunded = Double.parseDouble(sAmountToBeRefunded);
                                m_assert.assertTrue((dNetAmount == dAmountToBeRefunded),
                                        "Net Amount  : <b>" + dNetAmount + "</b> " + "<b> = </b> Amount to be refunded :  " + dAmountToBeRefunded + "</b> ");*/
                               /* Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.text_allItemsInTheBillTakenBackCheckbox, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_MopForCanceBillAndOrder6062.text_allItemsInTheBillTakenBackCheckbox), "all items taken box checkbox clicked");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.select_modeOfPaymentToCancelBill, 20);
                                String modeOfPaymentToRefund = "Others";
                                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_MopForCanceBillAndOrder6062.select_modeOfPaymentToCancelBill, modeOfPaymentToRefund), "Required mode of payment <b>(" + modeOfPaymentToRefund + ")</b> selected");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.input_transactionId, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_MopForCanceBillAndOrder6062.input_transactionId, sTransactionID), "Transaction ID =<b>" + sTransactionID + "</b>");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.input_transactionNote, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_MopForCanceBillAndOrder6062.input_transactionNote, sTransactionNote), "Transaction Note =<b>" + sTransactionNote + "</b>");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.input_cancelReason, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_MopForCanceBillAndOrder6062.input_cancelReason, sReasonForCancellation), "Bill cancellation reason = <b>" + sReasonForCancellation + "</b>");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.button_cancelBillInBillDetailsModal, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_MopForCanceBillAndOrder6062.button_cancelBillInBillDetailsModal), "Clicked on <b>cancel bill</b> button");*/
                                //Validate sales transaction is present under sales transaction folder
                                boolean salesTransactionFoundAfterCancel = false;
                                Cls_Generic_Methods.customWait(3);
                                for (WebElement ePatientName : oPage_MopForCanceBillAndOrder6062.list_salesTransactionInInventory) {
                                    Cls_Generic_Methods.customWait(5);
                                    String PatientNameAndMobileNum = ePatientName.getText();
                                    System.out.println(PatientNameAndMobileNum);
                                    String[] GetPatientName = PatientNameAndMobileNum.split("\\n");
                                    String PatientName = GetPatientName[0];
                                    if (PatientName.equalsIgnoreCase(myPatientName + date1)) {
                                        ePatientName.click();
                                        salesTransactionFoundAfterCancel = true;
                                        break;
                                    }

                                }
                                m_assert.assertTrue(salesTransactionFoundAfterCancel, "After cancelling the bill sale transaction found under sales page");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.text_currentStatusOfOrderAndBill, 20);
                                String sCurrentStatus = Cls_Generic_Methods.getTextInElement(oPage_MopForCanceBillAndOrder6062.text_currentStatusOfOrderAndBill);
                                if (sCurrentStatus.equalsIgnoreCase("Canceled")) {
                                    m_assert.assertTrue("Current status of a bill = <b>" + sCurrentStatus + "</b>");
                                } else {
                                    m_assert.assertFalse("Current status of a bill is incorrect= <b>" + sCurrentStatus + "</b>");
                                }
                                Cls_Generic_Methods.customWait(2);
                                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                                Cls_Generic_Methods.driverRefresh();
                                Cls_Generic_Methods.customWait(2);
                            } catch (Exception e) {
                                m_assert.assertFatal("" + e);
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            m_assert.assertFatal("" + e);
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        m_assert.assertFatal("" + e);
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    m_assert.assertFatal(" " + e);
                    e.printStackTrace();
                }
            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }
        }

        @Test(enabled = true, description = "Validate Mode Of Payment selection For Cancel Optical Bill ")
        public void opticalBillCancellationWithMopSelectionTest() {
            String sOPTICAL_STORE = "OpticalStore- Optical";
            try {
                Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
                Page_Sale oPage_Sale = new Page_Sale(driver);
                Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
                Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
                Page_MopForCanceBillAndOrder6062 oPage_MopForCanceBillAndOrder6062 = new Page_MopForCanceBillAndOrder6062(driver);
                CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
                CommonActions.selectStoreOnApp(sOPTICAL_STORE);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();

                try {
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Sale.button_PlaceOrder, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.button_PlaceOrder), "<b>Place Order</b> button clicked");
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_addNewButtonInOrder, 20), "Sales Order New Button Displayed");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addNewButtonInOrder), "Clicked on <b>New</b> sales oder button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.list_PatientSearch, 20);
                    try {
                        //Adding patient for sales order
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.button_addNewPatientButton, 20);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_MopForCanceBillAndOrder6062.button_addNewPatientButton), "Clicked on add new patient");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, 20);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatientName + date1), "First name entered as = <b>" + myPatientName + date1 + "</b>");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, 20);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, mobileNumber), "Mobile number entered as = <b>" + mobileNumber + "</b>");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.button_savePatient, 20);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_MopForCanceBillAndOrder6062.button_savePatient), "Clicked on save button to save patient");
                        Cls_Generic_Methods.customWait(3);
                        try {
                            //Selecting medication for Sales Order
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_description, 20);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_description), "Sales Order page opened and Description button clicked");
                            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_SalesOrder.getList_namesOfMedicinesOnLeftInSearchResult, 10);
                            Cls_Generic_Methods.customWait(3);
                            boolean myMedicationFound = false;
                            for (WebElement eMedication : oPage_MopForCanceBillAndOrder6062.list_expiredItems) {
                                Cls_Generic_Methods.customWait(5);
                                String Medication = eMedication.getText();
                                System.out.println(Medication);
                                if (Medication.equalsIgnoreCase("Exp. N.A")) {
                                    eMedication.click();
                                    myMedicationFound = true;
                                    break;
                                }

                            }
                            m_assert.assertTrue(myMedicationFound, "Selected a item from list to do sale");
                            try {
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.radioButton_completedDeliveryUnderOpticalSalesOrderForm, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_MopForCanceBillAndOrder6062.radioButton_completedDeliveryUnderOpticalSalesOrderForm), "clicked on <b>delivery completed</b> radio button");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_quantityOfMedicine, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_quantityOfMedicine, input_Qty), "Entered required quantity <b>" + input_Qty + "</b> for sales Order");
                                String sBalancePending = oPage_SalesOrder.text_balancePendingAmount.getAttribute("value");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.select_modeOfPayment, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_SalesOrder.select_modeOfPayment, modeOfPayment), "Required mode of payment <b>(" + modeOfPayment + ")</b> selected");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_amountPaidInCash, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_amountPaidInCash, sBalancePending), "Entered the amount <b>" + sBalancePending + "</b> to be paid in cash");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_saveChangesOnSalesOrder, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_saveChangesOnSalesOrder), "saved sales order form");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.button_cancelBillInBillDetailsModal, 20);
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_closeModalOfSalesOrder, 10);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_closeModalOfSalesOrder), "sales order modal closed");
                                Cls_Generic_Methods.customWait(3);
                                //selecting sales order by validating patient name
                                boolean salesOrderFound = false;
                                for (WebElement ePatientName : oPage_MopForCanceBillAndOrder6062.list_salesTransactionInInventory) {
                                    Cls_Generic_Methods.customWait(5);
                                    String PatientNameAndMobileNum = ePatientName.getText();
                                    System.out.println(PatientNameAndMobileNum);
                                    String[] GetPatientName = PatientNameAndMobileNum.split("\\n");
                                    String PatientName = GetPatientName[0];
                                    if (PatientName.equalsIgnoreCase(myPatientName + date1)) {
                                        ePatientName.click();
                                        salesOrderFound = true;
                                        break;
                                    }

                                }
                                m_assert.assertTrue(salesOrderFound, "Sales order found");

                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.button_moreAction, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_MoreAction), "Clicked on <b>more action</b> button");
                                Cls_Generic_Methods.customWait(3);
                                //Validate sales transaction is present under sales transaction folder
                                boolean salesTransactionFound = false;
                                for (WebElement ePatientName : oPage_MopForCanceBillAndOrder6062.list_salesTransactionInInventory) {
                                    Cls_Generic_Methods.customWait(5);
                                    String PatientNameAndMobileNum = ePatientName.getText();
                                    System.out.println(PatientNameAndMobileNum);
                                    String[] GetPatientName = PatientNameAndMobileNum.split("\\n");
                                    String PatientName = GetPatientName[0];
                                    if (PatientName.equalsIgnoreCase(myPatientName + date1)) {
                                        ePatientName.click();
                                        salesTransactionFound = true;
                                        break;
                                    }

                                }
                                m_assert.assertTrue(salesTransactionFound, "sale transaction found under sales page");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.button_cancelBill, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_MopForCanceBillAndOrder6062.button_cancelBill), "Clicked on <b>Cancel bill</b> button");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.button_cancelBillInBillDetailsModal, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.checkbox_acceptCheckBox),
                                        " CheckBox Clicked In Cancel");
                                Cls_Generic_Methods.customWait();
                                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Sale.select_modeOfPaymentInCancel,"Cash"),
                                        " Mode Of Payment Selected In Cancel as Cash");
                                String sNetAmount = Cls_Generic_Methods.getTextInElement(oPage_MopForCanceBillAndOrder6062.text_netAmount);

                                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_amountReceivedInCancel,sNetAmount.trim()),
                                        "Amount Entered as  In Cancel as "+sNetAmount);
                                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_cancelReason,"Checking For Cancellation"),
                                        "Cancel Reason Entered as : Checking For Cancellation");
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.button_cancelBillInCancel),
                                        " Cancel Bill Button Clicked");
                                Cls_Generic_Methods.customWait(2);
                                Cls_Generic_Methods.clickElement(oPage_Sale.button_cancelBillYesInCancel);
                                Cls_Generic_Methods.customWait(5);
                                //Validate sales transaction is present under sales transaction folder
                                boolean salesTransactionFoundAfterCancel = false;
                                Cls_Generic_Methods.customWait(3);
                                for (WebElement ePatientName : oPage_MopForCanceBillAndOrder6062.list_salesTransactionInInventory) {
                                    Cls_Generic_Methods.customWait(5);
                                    String PatientNameAndMobileNum = ePatientName.getText();
                                    System.out.println(PatientNameAndMobileNum);
                                    String[] GetPatientName = PatientNameAndMobileNum.split("\\n");
                                    String PatientName = GetPatientName[0];
                                    if (PatientName.equalsIgnoreCase(myPatientName + date1)) {
                                        ePatientName.click();
                                        salesTransactionFoundAfterCancel = true;
                                        break;
                                    }

                                }
                                m_assert.assertTrue(salesTransactionFoundAfterCancel, "After cancelling the bill sale transaction found under sales page");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.text_currentStatusOfOrderAndBill, 20);
                                String sCurrentStatus = Cls_Generic_Methods.getTextInElement(oPage_MopForCanceBillAndOrder6062.text_currentStatusOfOrderAndBill);
                                if (sCurrentStatus.equalsIgnoreCase("Canceled")) {
                                    m_assert.assertTrue("Current status of a bill = <b>" + sCurrentStatus + "</b>");
                                } else {
                                    m_assert.assertFalse("Current status of a bill is incorrect= <b>" + sCurrentStatus + "</b>");
                                }
                                Cls_Generic_Methods.customWait(2);
                                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                                Cls_Generic_Methods.driverRefresh();
                                Cls_Generic_Methods.customWait(2);
                            } catch (Exception e) {
                                m_assert.assertFatal("" + e);
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            m_assert.assertFatal("" + e);
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        m_assert.assertFatal("" + e);
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    m_assert.assertFatal(" " + e);
                    e.printStackTrace();
                }
            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }
        }

        @Test(enabled = true, description = "Validate Mode Of Payment selection For Cancel Optical Order")
        public void opticalOrderCancellationWithMopSelectionTest() {
            String sOPTICAL_STORE = "OpticalStore- Optical";
            String sREASON_FOR_ADVANCE = "Testing";
            try {
                Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
                Page_Sale oPage_Sale = new Page_Sale(driver);
                Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
                pages.store.OpticalStore.Page_SalesOrder oPage_SalesOrderOpto = new pages.store.OpticalStore.Page_SalesOrder(driver);

                Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
                Page_MopForCanceBillAndOrder6062 oPage_MopForCanceBillAndOrder6062 = new Page_MopForCanceBillAndOrder6062(driver);
                CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
                CommonActions.selectStoreOnApp(sOPTICAL_STORE);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();

                try {
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Sale");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Sale.button_PlaceOrder, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.button_PlaceOrder), " clicked on <b>Place Order</b> button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_addNewButtonInOrder, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addNewButtonInOrder), " Clicked on <b> New sales Order</b> Button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.list_PatientSearch, 20);
                    try {
                        //Adding patient for sales order
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.button_addNewPatientButton, 20);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_MopForCanceBillAndOrder6062.button_addNewPatientButton), "Clicked on add new patient");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, 20);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatientName + date1), "First name entered as = <b>" + myPatientName + date1 + "</b>");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, 20);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, mobileNumber), "Mobile number entered as = <b>" + mobileNumber + "</b>");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.button_savePatient, 20);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_MopForCanceBillAndOrder6062.button_savePatient), "Clicked on save button to save patient");
                        Cls_Generic_Methods.customWait(5);
                        try {
                            //Selecting item for Sales Order
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_description, 20);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_description), "Sales Order page opened and Description button clicked");
                            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_SalesOrder.getList_namesOfMedicinesOnLeftInSearchResult, 10);
                            boolean myMedicationFound = false;
                            Cls_Generic_Methods.customWait(2);
                            for (WebElement eMedication : oPage_MopForCanceBillAndOrder6062.list_expiredItems) {
                                String Medication = eMedication.getText();
                                System.out.println(Medication);
                                if (Medication.equalsIgnoreCase("Exp. N.A")) {
                                    eMedication.click();
                                    myMedicationFound = true;
                                    break;
                                }

                            }
                            m_assert.assertTrue(myMedicationFound, "Selected a item from list to do sale");
                            try {
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_quantityOfMedicine, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_quantityOfMedicine, input_Qty), "Entered required quantity <b>" + input_Qty + "</b> for sales Order");
                                String sBalancePending = oPage_MopForCanceBillAndOrder6062.input_balanceOrPending.getAttribute("value");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.input_reasonForAdvance, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_MopForCanceBillAndOrder6062.input_reasonForAdvance, sREASON_FOR_ADVANCE), " reason for taking advance =  <b>" + sREASON_FOR_ADVANCE + "</b> ");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrderOpto.select_ModeOfPaymentInReceipt, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_SalesOrderOpto.select_ModeOfPaymentInReceipt, 2), "Required mode of payment <b>(" + modeOfPayment + ")</b> selected");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.input_advanceAmount, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_MopForCanceBillAndOrder6062.input_advanceAmount, sBalancePending), "Entered the amount <b>" + sBalancePending + "</b> to be paid in cash");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_saveChangesOnSalesOrder, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_saveChangesOnSalesOrder), "clicked on <b>save</b> sales order button");
                                Cls_Generic_Methods.customWait(15);
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_closeModalOfSalesOrder, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_closeModalOfSalesOrder), "Closing the sales order details modal");
                                Cls_Generic_Methods.customWait(2);
                                //selecting sales order by validating patient name
                                boolean salesOrderFound = false;
                                for (WebElement ePatientName : oPage_MopForCanceBillAndOrder6062.list_salesTransactionInInventory) {
                                    Cls_Generic_Methods.customWait();
                                    String PatientNameAndMobileNum = ePatientName.getText();
                                    System.out.println(PatientNameAndMobileNum);
                                    String[] GetPatientName = PatientNameAndMobileNum.split("\\n");
                                    String PatientName = GetPatientName[0];
                                    if (PatientName.equalsIgnoreCase(myPatientName + date1)) {
                                        ePatientName.click();
                                        salesOrderFound = true;
                                        break;
                                    }

                                }
                                m_assert.assertTrue(salesOrderFound, "Sales order found");

                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.button_cancelOrder, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_MopForCanceBillAndOrder6062.button_cancelOrder), "Clicked on <b>Cancel oder</b> button");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.button_cancelBillInBillDetailsModal, 20);
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.checkbox_acceptCheckBox),
                                        " CheckBox Clicked In Cancel");
                                Cls_Generic_Methods.customWait();
                                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Sale.select_modeOfPaymentInCancel,"Cash"),
                                        " Mode Of Payment Selected In Cancel as Cash");
                                String sNetAmount = Cls_Generic_Methods.getTextInElement(oPage_MopForCanceBillAndOrder6062.text_netAmount);

                                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_amountReceivedInCancel,sNetAmount.trim()),
                                        "Amount Entered as  In Cancel as "+sNetAmount);
                                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_cancelReason,"Checking For Cancellation"),
                                        "Cancel Reason Entered as : Checking For Cancellation");
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_MopForCanceBillAndOrder6062.button_cancelOrderInCancel),
                                        " Cancel Bill Button Clicked");
                                Cls_Generic_Methods.customWait(2);
                                Cls_Generic_Methods.clickElement(oPage_Sale.button_cancelBillYesInCancel);
                                Cls_Generic_Methods.customWait(5);
                                boolean salesOrderFoundAfterCancel = false;
                                for (WebElement ePatientName : oPage_MopForCanceBillAndOrder6062.list_salesTransactionInInventory) {
                                    Cls_Generic_Methods.customWait();
                                    String PatientNameAndMobileNum = ePatientName.getText();
                                    System.out.println(PatientNameAndMobileNum);
                                    String[] GetPatientName = PatientNameAndMobileNum.split("\\n");
                                    String PatientName = GetPatientName[0];
                                    if (PatientName.equalsIgnoreCase(myPatientName + date1)) {
                                        ePatientName.click();
                                        salesOrderFoundAfterCancel = true;
                                        break;
                                    }

                                }
                                m_assert.assertTrue(salesOrderFoundAfterCancel, "After cancelling the Order, sales order found under sales order page");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_MopForCanceBillAndOrder6062.text_currentStatusOfOrderAndBill, 20);
                                String sCurrentStatus = Cls_Generic_Methods.getTextInElement(oPage_MopForCanceBillAndOrder6062.text_currentStatusOfOrderAndBill);
                                if (sCurrentStatus.equalsIgnoreCase("Canceled")) {
                                    m_assert.assertTrue("Current status of a order = <b>" + sCurrentStatus + "</b>");
                                } else {
                                    m_assert.assertFalse("Current status of a order is incorrect= <b>" + sCurrentStatus + "</b>");
                                }
                            } catch (Exception e) {
                                m_assert.assertFatal("" + e);
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            m_assert.assertFatal("" + e);
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        m_assert.assertFatal("" + e);
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    m_assert.assertFatal(" " + e);
                    e.printStackTrace();
                }
            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }
        }


}
