package pages.commonElements;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.Settings_Data;
import data.storeData.InventoryStore_Data;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.python.antlr.ast.Str;
import org.testng.annotations.Test;
import pages.commonElements.bills.Page_Bills;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.templates.Page_InventorySearchCommonElements;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_TermsAndConditions;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_VendorMaster;
import pages.store.OpticalStore.Page_SalesOrder;
import pages.store.Page_PatientQueue;
import pages.store.Page_Store;
import pages.store.PharmacyStore.Items.Page_Lot;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Order.Page_Indent;
import pages.store.PharmacyStore.Order.Page_PurchaseOrder;
import pages.store.PharmacyStore.Order.Page_Requisition;
import pages.store.PharmacyStore.Order.Page_RequisitionReceived;
import pages.store.PharmacyStore.Page_TaxInvoiceDeliveryChallan;
import pages.store.PharmacyStore.Transaction.*;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static pages.commonElements.CommonActions.*;
import static tests.inventoryStores.pharmacyStore.Transaction.PurchaseGRNTest.*;

public class SequenceCommonActions extends TestBase {
    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    static String myMedicationName = "SalesOrderTest1";
    static String inputQty = "2";
    String settleAdvanceValue = "100";
    static String netAmountSaleOrder;
    String amountAfterAdvance;
    static String sItemDescription = "Transfer Item 2";
    static String sTransferId = "";

    public static String sReceivingStore = "Pharmacy automation- Pharmacy";
    static String storeGSTno;
    static String vendorName = "Supplier ABC";
    static String vendor_address;
    static String vendorGSTno;
    static String billNumber;
    static String transactionNotes = "Auto_Test_Transaction_notes";
    static String netAmountInPurchaseBill;
    static String invoiceDate;
    static String purchaseBillCreatedBy;
    static String purchaseBillCreatedAt;
    String prfCreatedBy;
    static String prfCreatedAt;
    //String billNumber;
    Page_CommonElements oPage_CommonElements;
    Page_StoreSetUp oPage_StoreSetUp;
    Page_Purchase oPage_Purchase;
    Page_PurchaseBill oPage_PurchaseBill;
    Page_VendorMaster oPage_VendorMaster;
    Page_PaymentRequisitionForm oPage_PaymentRequisitionForm;
    static String prf_no;
    static String purchaseBill_no;
    static String storeDefaultShippingAddress = "";
    static String storeDefaultBillingAddress = "";
    static String vendorCreditDays = "";
    static String vendorPOExpiry = "";
    String expectedPOExpiryDay = "";
    static String storeEntityGroup = "";
    static String deliveryTerms = "";
    static String paymentTerms = "";
    static HashMap<String, String> itemDetails1;


    public static String createAdvance(){

        Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Bills oPage_Bills = new Page_Bills(driver);
        String sAdvanceId = "";

        try {
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills),
                    "Bill Button Displayed and Clicked");
            Cls_Generic_Methods.customWait( 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_advanceReceiptButton),
                    "Advance Button Displayed and Clicked ");

            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_advanceReceiptTemplate, 5),
                    " Advance Receipt Template Opened and Header Displayed as : Advance Receipt");

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientQueue.input_reasonAdvance,"Advance Reason"),
                    " Advance Reason Text box Displayed and Value Entered as : Advance Reason");

            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_PatientQueue.select_mop, "Cash"),
                    " Mode of Payment Selected as : "+"Cash" + " at index 1");
            Cls_Generic_Methods.clearValuesInElement(oPage_PatientQueue.input_amountAdvance);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientQueue.input_amountAdvance, "100"),
                    " Amount Entered as : "+"100"+" at index 0");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_saveAdvance,4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_saveAdvance),
                    " Save Advance Button Clicked");
            Cls_Generic_Methods.customWait( 7);
            sAdvanceId = Cls_Generic_Methods.getTextInElement(oPage_CommonElements.text_advanceId);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

        }catch (Exception e) {
            e.printStackTrace();
        }

        return sAdvanceId;

    }

    public static String validateSaleTransactionFunctionality(String sStore) {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        String orderId = "";


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            try {

                CommonActions.selectStoreOnApp(sStore);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
                Cls_Generic_Methods.customWait();

                // creating order for cancel order button check
                boolean bCreateOrderForPolicy = createSaleOrderForPolicyCheck("Stockable");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(bCreateOrderForPolicy, " Sales Order Created");

                orderId = Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.text_orderNoOpticalOrder);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_MoreAction),
                        " More Action Button Clicked");
                Cls_Generic_Methods.customWait(5);


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        return  orderId;
    }

    public static void validatePharmacySaleTransactionFunctionality(String sStore) {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);



        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            try {

                CommonActions.selectStoreOnApp(sStore);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
                Cls_Generic_Methods.customWait();

                // creating order for cancel order button check
                boolean bCreateOrderForPolicy = createPharmacySaleOrderForPolicyCheck("Stockable");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(bCreateOrderForPolicy, " Sales Order Created");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_MoreAction),
                        " More Action Button Clicked");
                Cls_Generic_Methods.customWait(5);




            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }
    
    public static boolean createSaleOrderForPolicyCheck(String itemFrom){

        boolean createSalesOrder = false ;
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);
        String sPatientSalutation = "Mr.";


        try{

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addNewButtonInOrder), "Sales Order New Button Clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.list_PatientSearch, 5);
            Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.button_addNewPatient);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_orderTime,10);

            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                            oPage_NewPatientRegisteration.select_salutationForPatient, sPatientSalutation),
                    "Salutation for Patient is selected as - " + sPatientSalutation);

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                    "First Name is entered as - " + myPatient.getsFIRST_NAME());

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                            myPatient.getsMOBILE_NUMBER()),
                    "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm),
                    " Save Button Clicked in Patient Register Form");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_TxnDate,10);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_searchMedicineNameInDescription, myMedicationName), "Entering the medication name as" + myMedicationName + " in description textbox for sales order");
            Cls_Generic_Methods.customWait(4);
            oPage_SalesOrder.input_searchMedicineNameInDescription.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);
            boolean myMedicationFound = false;

            for (WebElement eMedication : oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult) {
                if(Cls_Generic_Methods.isElementDisplayed(eMedication)){
                    if (Cls_Generic_Methods.getTextInElement(eMedication).contains(myMedicationName)) {
                        Cls_Generic_Methods.clickElement(eMedication);
                        myMedicationFound = true;
                        Cls_Generic_Methods.customWait(4);
                        break;
                    }
                }
            }
            m_assert.assertTrue(myMedicationFound, "Required medication " + myMedicationName + " found for Sales Order");

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_quantityOfMedicine, inputQty),
                    "Entered Quantity " + inputQty + " for Sales Order");
            Cls_Generic_Methods.customWait(5);

            netAmountSaleOrder = Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_totalAmount,"value");

            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SalesOrder.label_completedDeliveryRadioButtonLabel),
                    "Completed Radio Button Clicked FOr Delivery Type ");
            Cls_Generic_Methods.customWait(2);

            m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_PatientQueue.select_modeOfPaymentInSalesOrder, "Cash"),
                    "Required mode of payment " + "Cash  selected");

            Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientQueue.input_totalAmountInPayment,netAmountSaleOrder);

            String orderDate = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_TxnDate,"value");
            String orderTime = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderTime,"value");

            DateFormat dateFormat = new SimpleDateFormat("hh:mmaa");
            String time = dateFormat.format(new Date());
            time = time.replace("am", "AM").replace("pm", "PM");

            orderTime = orderTime.replaceAll("\\s+", "");
            //orderTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", orderTime);
            orderTime = orderTime.replace("am", "AM").replace("pm", "PM");
            orderDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd-MM-yyyy", orderDate);

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_SaveChanges),
                    "Save Changes Button CLicked");
            Cls_Generic_Methods.customWait(5);
            if(orderTime.length() == 6){
                orderTime = "0"+orderTime;
            }
            String salesOrderDateAndTime =orderDate+"  |  "+orderTime;


            if(Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.button_closeModalOfSalesOrder)) {
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_closeModalOfSalesOrder);
                Cls_Generic_Methods.customWait();
                createSalesOrder = true ;
            }

            String sDate = orderDate.replace("/", "-");
            String sTime = orderTime.replace(" ", "");
            boolean bSalesOrderFound = false ;
            for (WebElement eSalesOrder : oPage_SalesOrder.list_namesofSalesOrder) {
                if (eSalesOrder.getText().contains(sDate) && eSalesOrder.getText().contains(sTime) && eSalesOrder.getText().contains(myPatient.getsFIRST_NAME())) {
                    eSalesOrder.click();
                    Cls_Generic_Methods.customWait(5);
                    bSalesOrderFound = true;
                    break;
                }
            }
            m_assert.assertTrue(bSalesOrderFound, "Created and Selected the correct sales order");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        return createSalesOrder ;
    }
    public static boolean createPharmacySaleOrderForPolicyCheck(String itemFrom){

        boolean createSalesOrder = false ;
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);
        String sPatientSalutation = "Mr.";


        try{
            myPatient = map_PatientsInExcel.get("002");

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addNewButtonInOrder), "Sales Order New Button Clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.list_PatientSearch, 5);
            Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.button_addNewPatient);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_orderTime,10);

            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                            oPage_NewPatientRegisteration.select_salutationForPatient, sPatientSalutation),
                    "Salutation for Patient is selected as - " + sPatientSalutation);

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                    "First Name is entered as - " + myPatient.getsFIRST_NAME());

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                            myPatient.getsMOBILE_NUMBER()),
                    "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm),
                    " Save Button Clicked in Patient Register Form");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_TxnDate,10);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_searchMedicineNameInDescription, myMedicationName), "Entering the medication name as" + myMedicationName + " in description textbox for sales order");
            Cls_Generic_Methods.customWait(4);
            oPage_SalesOrder.input_searchMedicineNameInDescription.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);
            boolean myMedicationFound = false;

            for (WebElement eMedication : oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult) {
                if(Cls_Generic_Methods.isElementDisplayed(eMedication)){
                    if (Cls_Generic_Methods.getTextInElement(eMedication).contains(myMedicationName)) {
                        Cls_Generic_Methods.clickElement(eMedication);
                        myMedicationFound = true;
                        Cls_Generic_Methods.customWait(4);
                        break;
                    }
                }
            }
            m_assert.assertTrue(myMedicationFound, "Required medication " + myMedicationName + " found for Sales Order");

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_quantityOfMedicine, inputQty),
                    "Entered Quantity " + inputQty + " for Sales Order");
            Cls_Generic_Methods.customWait(5);

            netAmountSaleOrder = Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_totalAmount,"value");

            m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_PatientQueue.select_modeOfPaymentInSalesOrder, "Cash"),
                    "Required mode of payment Cash  selected");

            Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientQueue.input_totalAmountInPayment,netAmountSaleOrder);

           String orderDate = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_TxnDate,"value");
            String orderTime = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderTime,"value");

            DateFormat dateFormat = new SimpleDateFormat("hh:mmaa");
            String time = dateFormat.format(new Date());
            time = time.replace("am", "AM").replace("pm", "PM");

            orderTime = orderTime.replaceAll("\\s+", "");
            //orderTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", orderTime);
            orderTime = orderTime.replace("am", "AM").replace("pm", "PM");
            orderDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd-MM-yyyy", orderDate);

            if(orderTime.length() == 6){
                orderTime = "0"+orderTime;
            }

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_SaveChanges),
                    "Save Changes Button CLicked");
            Cls_Generic_Methods.customWait(5);

            String salesOrderDateAndTime =orderDate+"  |  "+orderTime;


            if(Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.button_closeModalOfSalesOrder)) {
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_closeModalOfSalesOrder);
                Cls_Generic_Methods.customWait();
                createSalesOrder = true ;
            }

            m_assert.assertTrue(createSalesOrder, " Sale Order Created Successfully");
            String sDate = orderDate.replace("/", "-");
            String sTime = orderTime.replace(" ", "");
            boolean bSalesOrderFound = false ;
            for (WebElement eSalesOrder : oPage_SalesOrder.list_namesofSalesOrder) {
                if (eSalesOrder.getText().contains(sDate) && eSalesOrder.getText().contains(sTime) && eSalesOrder.getText().contains(myPatient.getsFIRST_NAME())) {
                    eSalesOrder.click();
                    Cls_Generic_Methods.customWait(5);
                    bSalesOrderFound = true;
                    break;
                }
            }
            m_assert.assertTrue(bSalesOrderFound, "Created and Selected the correct sales order");



        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        return createSalesOrder ;
    }

    public static String createIndent(String sINDENT_STORE,String sShipToStore) {


        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        List<String> itemList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();
        String indentId = "";



        boolean bIndentOrderFound = false;


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sINDENT_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_addNewIndent),
                        "New Button clicked in Order: Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.select_vendorField),
                        "clicked on Store selection field");
                Cls_Generic_Methods.customWait();
                boolean storeFound = false;
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, sShipToStore);
                Cls_Generic_Methods.customWait();
                for (WebElement eStoreName : oPage_Indent.list_stores) {
                    if (Cls_Generic_Methods.getTextInElement(eStoreName).contains(sShipToStore)) {
                        Cls_Generic_Methods.clickElement(eStoreName);
                        storeFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(storeFound, "Store found to do indent purchase : <b> " + sINDENT_STORE + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_noteUnderIndentForPurchase, 10);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.dropdown_IndentType),
                        "Clicked on Indent Type Dropdown");
                String sIndentType = "Normal";
                Cls_Generic_Methods.customWait(2);
                for (WebElement eType : oPage_Indent.list_IndentTypeList) {
                    oPage_Indent = new Page_Indent(driver);
                    if (sIndentType.contains(Cls_Generic_Methods.getTextInElement(eType))) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(eType),
                                "Indent Type selected: <b> " + sIndentType + " </b>");
                        Cls_Generic_Methods.customWait(3);
                        break;
                    }
                }


                Cls_Generic_Methods.clickElement(oPage_Indent.list_ItemDescriptionsUnderIndentPurchase.get(0));
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_indentOrderDate, 10);
                String indentOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderTime, "value");
                indentOrderTime = indentOrderTime.replaceAll("\\s+", "");
                indentOrderTime = indentOrderTime.replace("am", "AM").replace("pm", "PM");
                if (indentOrderTime.length() == 6) {
                    indentOrderTime = "0" + indentOrderTime;
                }
                m_assert.assertTrue("Indent order time:<b> " + indentOrderTime + "</b>");
                String indentOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderDate, "value");
                indentOrderDate = indentOrderDate.replaceAll("/", "-");
                m_assert.assertTrue("Indent order date:<b> " + indentOrderDate + "</b>");
                String indentOrderDateAndTime = indentOrderDate + "  |  " + indentOrderTime;
                Cls_Generic_Methods.customWait();

                for (WebElement eItem : oPage_Indent.list_itemNameSelectedToCreateIndentPurchase) {
                    int index = oPage_Indent.list_itemNameSelectedToCreateIndentPurchase.indexOf(eItem);

                    Cls_Generic_Methods.scrollToElementByJS(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index));
                    Cls_Generic_Methods.customWait(1);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index), "1");
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Indent.text_multipleOfItemText)) {
                        String multipleOf = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_multipleOfItemText).split(" ")[6];
                        Cls_Generic_Methods.clearValuesInElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index));
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.list_quantitySelectedToCreateIndentPurchase.get(index), multipleOf);
                        quantityList.add(multipleOf);

                    } else {
                        quantityList.add("1");
                    }
                    m_assert.assertInfo(" Quantity Entered as <b> " + quantityList.get(index) + " </b> for item name <b> " + itemList.get(index) + " </b> at index " + index);
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.list_subStoreSelectedToCreateIndentPurchase.get(index), "Default");
                    Cls_Generic_Methods.customWait(1);

                }

                Cls_Generic_Methods.customWait();



                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder),
                        " Indent order saved");
                Cls_Generic_Methods.customWait(5);

                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    if (indentOrderDateAndTime.contains(date) &&
                            indentOrderDateAndTime.contains(time)) {
                        bIndentOrderFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }
                m_assert.assertTrue(bIndentOrderFound, "Order found in the Indent order page");

                if (bIndentOrderFound) {

                    indentId = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_IndentNumber);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return indentId;
    }

    public static String createReceive(String sRequisition_STORE,String sReceive_Store){

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Receive oPage_Receive = new Page_Receive(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        String receiveId = "";

        try{
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sRequisition_STORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait(3);

            try{
                 validateCreateDeliveryChallan(sRequisition_STORE,sReceive_Store);
                 CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Receive");
                 Cls_Generic_Methods.customWait(3);

                for (WebElement eTransaction : oPage_Transfer.list_tableDataTransfer) {

                    List<WebElement> allOptions = eTransaction.findElements(By.xpath("./td"));
                    String transferInfoInRec = Cls_Generic_Methods.getTextInElement(allOptions.get(3));

                    if (transferInfoInRec.contains(sTransferId)) {

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(eTransaction),
                                " Issue Record Clicked In Receive Transaction");
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                receiveId = Cls_Generic_Methods.getTextInElement(oPage_Receive.text_receiveTransactionId.findElement(By.xpath("./span")));


            }catch (Exception e){
                e.printStackTrace();
                m_assert.assertFalse(" Receive Not Found");
            }

        }catch (Exception e){
            e.printStackTrace();
            m_assert.assertFalse(" Receive Not Found");
        }
        return  receiveId;
    }

    public static String createBill(){

        Page_Bills oPage_Bills = new Page_Bills(driver);
        String sBillId = "";

        try {
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills),
                    "Bill Button Displayed and Clicked");
            Cls_Generic_Methods.customWait( 4);
            try {
                m_assert.assertTrue(selectOptionFromBillsList(oPage_Bills.list_billTypeSelection, "New Bill"),
                        "Validate " + "New Bill" + " bill is selected");
                Cls_Generic_Methods.customWait(5);
            } catch (Exception e) {
                m_assert.assertTrue(false, "Bill type is not selected" + e);
            }
            sBillId = fillBillForm();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 20);


        }catch (Exception e) {
            e.printStackTrace();
        }

        return sBillId;

    }

    public static String validateCreateDeliveryChallan(String sStore,String sReceivingStore1) {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_TaxInvoiceDeliveryChallan oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);
        String sTransactionId ="";
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            String bTransferStatus = createDirectTransfer(sReceivingStore1);

            if (!bTransferStatus.isEmpty()) {
                try{
                if (Cls_Generic_Methods.isElementDisplayed(oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan)) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan), "Selected Option in the Left Panel = Tax Invoice / Delivery Challan");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_new, 5);

                    //Create New
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_new), "Clicked New Button");
                    Cls_Generic_Methods.customWait();
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_deliveryChallan), "Clicked <b>New Delivery Challan</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_headerCreateDeliveryChallan, 10), "Validated --> Navigated to Create Delivery Challan Page");

                    //Select To store
                    m_assert.assertInfo(selectByOptions(oPage_TaxInvoiceDeliveryChallan.select_againstStore, sReceivingStore.split("-")[0]), "Selected <b>To</b> store as : <b>" + sReceivingStore + "</b>");
                    Cls_Generic_Methods.customWait(5);
                    for (WebElement lhsRow : oPage_TaxInvoiceDeliveryChallan.row_lhsCreateTaxInvoiceDeliveryChallan) {
                        String transferId = Cls_Generic_Methods.getTextInElement(lhsRow.findElement(By.xpath("./td[3]/div[1]")));
                        if (transferId.equals(sTransferId)) {
                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(lhsRow), "Selected Transfer transaction : " + transferId);
                            break;
                        }
                    }

                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.list_rowCreateTiDc, 10);

                    String orderTime = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_time, "value");
                    String orderDate = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_date, "value");

                    //Dispatch Details
                    m_assert.assertInfo(selectByOptions(oPage_TaxInvoiceDeliveryChallan.select_transportationMode, "Test"), "Selected <b>Test</b> in Transportation Mode");
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_TaxInvoiceDeliveryChallan.input_transactionId, "1234"), "Entered <b>1234</b> in Transaction Id");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_saveChanges), "Clicked <b>Save Changes</b>");

                    Cls_Generic_Methods.customWait(15);
                    String sDate = orderDate.replace("/", "-");
                    String sTime = orderTime.replace(" ", "");
                    boolean bSalesOrderFound = false;
                    for (WebElement eSalesOrder : oPage_TaxInvoiceDeliveryChallan.list_transactionCreatedList) {
                        if (eSalesOrder.getText().contains(sDate) && eSalesOrder.getText().contains(sTime)) {
                            Cls_Generic_Methods.clickElement(eSalesOrder);
                            Cls_Generic_Methods.customWait(5);
                            sTransactionId = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_transactionId);
                            bSalesOrderFound = true;
                            Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_approve);
                            Cls_Generic_Methods.customWait();
                            break;
                        }
                    }
                    m_assert.assertTrue(bSalesOrderFound, "Created and Selected the delivery challan");


                } else {
                    m_assert.assertFatal("Transfer transaction not created");
                }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate create Tax Invoice");
        }
        return sTransactionId;
    }

    public static String validateCreateTaxInvoice(String StoreA,String StoreB) {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_TaxInvoiceDeliveryChallan oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);
        String sTransactionId ="";

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(StoreA);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            String bTransferStatus = createDirectTransfer(StoreB);

            if (!bTransferStatus.isEmpty()) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan)) {

                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan), "Selected Option in the Left Panel = Tax Invoice / Delivery Challan");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_new, 5);

                    //Create New
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_new), "Clicked New Button");
                    Cls_Generic_Methods.customWait();
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_taxInvoice), "Clicked <b>New Tax invoice</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.text_headerCreateTaxInvoice, 10), "Validated --> Navigated to Create Tax Invoice Page");

                    //Select To store
                    m_assert.assertInfo(selectByOptions(oPage_TaxInvoiceDeliveryChallan.select_againstStore, sReceivingStore.split("-")[0]), "Selected <b>To</b> store as : <b>" + sReceivingStore + "</b>");
                    Cls_Generic_Methods.customWait(5);
                    for (WebElement lhsRow : oPage_TaxInvoiceDeliveryChallan.row_lhsCreateTaxInvoiceDeliveryChallan) {
                        String transferId = Cls_Generic_Methods.getTextInElement(lhsRow.findElement(By.xpath("./td[3]/div[1]")));
                        if (transferId.equals(sTransferId)) {
                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(lhsRow), "Selected Transfer transaction : " + transferId);
                            break;
                        }
                    }

                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.list_rowCreateTiDc, 10);


                    //Table Validation

                    String orderTime = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_time, "value");
                    String orderDate = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_date, "value");

                    //Dispatch Details
                    m_assert.assertInfo(selectByOptions(oPage_TaxInvoiceDeliveryChallan.select_transportationMode, "Test"), "Selected <b>Test</b> in Transportation Mode");
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_TaxInvoiceDeliveryChallan.input_transactionId, "1234"), "Entered <b>1234</b> in Transaction Id");

                    Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.input_deliverydate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.input_todayBillDate, 3);
                    Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.input_todayBillDate);
                    String deliveryDate = Cls_Generic_Methods.getElementAttribute(oPage_TaxInvoiceDeliveryChallan.input_deliverydate, "value").trim();
                    m_assert.assertInfo("Selected Delivery Date as : <b>" + deliveryDate + "</b>");

                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_TaxInvoiceDeliveryChallan.input_dispatchRemark, "AUTO"), "Entered <b>AUTO</b> in Dispatch Remark");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_saveChanges), "Clicked <b>Save Changes</b>");

                    Cls_Generic_Methods.customWait(8);

                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_refresh), "Clicked <b>Refresh</b> button");
                    Cls_Generic_Methods.customWait(5);

                    String sDate = orderDate.replace("/", "-");
                    String sTime = orderTime.replace(" ", "");
                    boolean bSalesOrderFound = false;
                    for (WebElement eSalesOrder : oPage_TaxInvoiceDeliveryChallan.list_transactionCreatedList) {
                        if (eSalesOrder.getText().contains(sDate) && eSalesOrder.getText().contains(sTime)) {
                            Cls_Generic_Methods.clickElement(eSalesOrder);
                            Cls_Generic_Methods.customWait(5);
                            sTransactionId = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.text_rhs_transactionId);
                            bSalesOrderFound = true;
                            Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_approve);
                            Cls_Generic_Methods.customWait();
                            break;
                        }
                    }
                    m_assert.assertTrue(bSalesOrderFound, "Created and Selected the delivery challan");
                }
            }
            else {
                m_assert.assertFatal("Transfer transaction not created");
            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate create Tax Invoice");
        }

        return sTransactionId;
    }


    public static String createDirectTransfer(String sReceivingStore) {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        boolean status = false;
        String sQuantity = "1";

        try {
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(2);
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_newTransaction, 5);
            Cls_Generic_Methods.clickElement(oPage_Transfer.button_newTransaction);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.dropdown_receivingStore, 5);
            Cls_Generic_Methods.clickElement(oPage_Transfer.dropdown_receivingStore);

            boolean receivingStoreFound = false;
            Cls_Generic_Methods.customWait(3);
            for (WebElement eReceivingStore : oPage_Transfer.list_receivingStore) {
                if (Cls_Generic_Methods.getTextInElement(eReceivingStore).equalsIgnoreCase(sReceivingStore.split("-")[0])) {
                    Cls_Generic_Methods.clickElement(eReceivingStore);
                    receivingStoreFound = true;
                    break;
                }
            }

            m_assert.assertInfo(receivingStoreFound, "Selected Received Store as : <b>" + sReceivingStore.split("-")[0] + "</b>");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_searchByDescription, 5);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_searchByDescription), "Clicked on Description button");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItemToBeTransferred, 5);
            Cls_Generic_Methods.customWait(3);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_searchByDescription, 5);
            Cls_Generic_Methods.clickElement(oPage_Transfer.button_searchByDescription);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.input_searchItemToBeTransferred, 5);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_searchItemToBeTransferred, sItemDescription);
            Cls_Generic_Methods.customWait(1);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_searchItemToBeTransferred, " ");
            Cls_Generic_Methods.customWait(3);
            for (WebElement eItemVariantCode : oPage_Transfer.list_itemDescriptionRow
            ) {
                if (Cls_Generic_Methods.getTextInElement(eItemVariantCode).equalsIgnoreCase(sItemDescription)) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(eItemVariantCode), "Selected Item : <b>" + sItemDescription + "</b>");
                    break;
                }
            }

            Cls_Generic_Methods.customWait(3);
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.list_quantityFieldForItemsToTransfer.get(0), sQuantity),
                    "Entered <b>" + sQuantity + "</b> in Quantity");

            Cls_Generic_Methods.customWait();
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_saveChanges), "Transfer Transaction saved");
            Cls_Generic_Methods.customWait(10);

            for (WebElement e : oPage_Transfer.list_transferTransactionRow) {
                sTransferId = Cls_Generic_Methods.getTextInElement(e.findElement(By.xpath("./child::div[1]")));
                Cls_Generic_Methods.clickElement(e);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_approveTransferTransaction, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Transfer.button_approveTransferTransaction), "Approved Transfer Transaction");
                status = true;
                break;

            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertInfo("Unable to create Transfer " + e);
        }
        return sTransferId;
    }

    public static String validateCreatePaymentRequisitionForm(String store) {

        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_PaymentRequisitionForm oPage_PaymentRequisitionForm = new Page_PaymentRequisitionForm(driver);

        try {

            CommonActions.loginFunctionality(expectedLoggedInUser);
            getGST_valueFromSetting(store);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");

            Cls_Generic_Methods.customWait();
            boolean purchaseBillCreated = createPurchaseBill();

            if (purchaseBillCreated) {
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Payment Requisition Form");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_newPaymentRequisitionForm), "Clicked New in Payment Requisition Form");
                m_assert.assertInfo(selectByOptions(oPage_PaymentRequisitionForm.select_vendor, vendorName), "Selected vendor as : <b>" + vendorName + "</b>");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_PaymentRequisitionForm.input_vendorGstNo, "value").equals(vendorGSTno), "Validated--> Vendor GST no");

                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PaymentRequisitionForm.row_createPaymentRequisitionForm, 10);


                int rowNo = 0;
                for (WebElement row : oPage_PaymentRequisitionForm.row_createPaymentRequisitionForm) {
                    String pbNoInRow = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.list_purchaseBillNoInRowCreatePrf.get(rowNo));
                    if (pbNoInRow.equals(purchaseBill_no)) {
                        Cls_Generic_Methods.clickElement(row);
                        m_assert.assertTrue("Validated-->Newly Created Purchase Bill is present in list");
                        break;
                    }
                    rowNo++;
                }
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_PaymentRequisitionForm.button_saveChanges), "<b>Save Changes</b> button is enabled after adding the purchase");


                //Validate PRF Time
                Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.input_paymentRequisitionDate);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.input_todayBillDate, 3);
                Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.input_todayBillDate);
                String date = Cls_Generic_Methods.getElementAttribute(oPage_PaymentRequisitionForm.input_paymentRequisitionDate, "value").trim();
                date = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy","dd-MM-yyyy",date);
                prfCreatedAt = date+" | "+Cls_Generic_Methods.getElementAttribute(oPage_PaymentRequisitionForm.input_paymentRequisitionTime, "value").trim().replace(" ","");

                m_assert.assertInfo("Selected Payment Requisition Form Date as : <b>" + date + "</b>");

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PaymentRequisitionForm.input_transactionNote, transactionNotes), "Entered <b>" + transactionNotes + "</b> in Transaction Notes");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PaymentRequisitionForm.button_saveChanges), "Clicked Save Changes Button");
                Cls_Generic_Methods.customWait(5);

                boolean rowSelected = false ;
                for (WebElement row : oPage_PaymentRequisitionForm.list_prfCreatedList) {

                    if (Cls_Generic_Methods.isElementDisplayed(row)) {
                        List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

                        String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(0));
                        String prfNoInRow = Cls_Generic_Methods.getTextInElement(purchaseRow.get(1));
                        if(purchaseStatus.contains(prfCreatedAt)){
                            Cls_Generic_Methods.clickElement(row);
                            Cls_Generic_Methods.customWait(5);
                        }

                    }
                }


                if (rowSelected) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PaymentRequisitionForm.text_rhs_prfNo, 15);
                    prf_no = Cls_Generic_Methods.getTextInElement(oPage_PaymentRequisitionForm.text_rhs_prfNo);

                } else {
                    m_assert.assertFatal("Newly Created PRF is not found");
                }

            } else {
                m_assert.assertFatal("Purchase Bill not Created");
            }



        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate create Payment Requisition Form --> " + e);
            e.printStackTrace();
        }
         return prf_no;
    }

    public static String validateCreatePurchaseBill(String store){
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

        String purchaseBill = "";
        try{
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            getGST_valueFromSetting(store);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");

            Cls_Generic_Methods.customWait();
            createPurchaseBill();
            purchaseBill = purchaseBill_no;

        }catch (Exception e){
            e.printStackTrace();
        }

        return purchaseBill;
    }

    public static String validatePurchaseOrderCreation(String sStore) {
        Page_PurchaseOrder oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        String itemName1 = "Midodrine";
        String item1Discount = "21 %";
        String purchaseOrderId = "";


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            getStoreAndVendorDetails(sStore);
            Cls_Generic_Methods.driverRefresh();
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 8);

                //Getting Item Details
                itemDetails1 = getStockableItemDetails(itemName1);


                //Create Purchase Order
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 3);

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_newOrder), "Clicked <b>New</b> Button in  Purchase Order");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_headerCreatePurchaseOrder, 10), "Validated Create Purchase Order Page displayed");

                String orderTime = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_purchaseOrderTime, "value").replace(" ","");
                orderTime = CommonActions.getRequiredFormattedDateTime("h:mm a", "hh:mm a", orderTime);
                m_assert.assertTrue("Purchase Order time:<b> " + orderTime + "</b>");

                String orderDate = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_purchaseOrderDate, "value");
                m_assert.assertTrue("Purchase Order date:<b> " + orderDate + "</b>");
                orderDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd-MM-yyyy", orderDate);
                String purchaseOrderDateAndTime = orderDate + "  |  " + orderTime;


                //select vendor, store, type
                m_assert.assertTrue(selectByOptions(oPage_PurchaseOrder.dropdown_store, sStore.split("-")[0]), "Store selected : <b>" + sStore.split("-")[0] + " </b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_Vendor_search, 4);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, (vendorName + " " + vendor_address));
                Cls_Generic_Methods.customWait();

                Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, "" + Keys.DOWN + Keys.ENTER);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, "" + Keys.DOWN + Keys.ENTER),
                        "Vendor selected : <b>" + vendorName + "</b>");
                Cls_Generic_Methods.customWait();

                m_assert.assertTrue(selectByOptions(oPage_PurchaseOrder.dropdown_poType, "Normal"), "PO Type selected : <b>" + "Normal" + " </b>");
                Cls_Generic_Methods.customWait();

                //Select Item
                selectItemAndAddLot(itemDetails1, item1Discount);

                //Validating UI Header Elements
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_orderNote, 7);

                //Other Charges Validation


                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_removeOtherCharges_createPo.get(1)), "Clicked Remove Other Charges button");
                Cls_Generic_Methods.customWait();

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_saveOrder), "Purchase Order Saved");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 10);
                Cls_Generic_Methods.customWait();
                boolean selectPO = false ;
                for (WebElement row : oPage_PurchaseOrder.list_purchaseOrdertransactions) {
                    int index = oPage_PurchaseOrder.list_purchaseOrdertransactions.indexOf(row);
                    if (Cls_Generic_Methods.isElementDisplayed(row)) {
                        String purchaseOrderCreatedAtInRow = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.list_orderInfoTransactionList.get(index)).split("\n")[0];
                       if(purchaseOrderCreatedAtInRow.contains(purchaseOrderDateAndTime)){
                           Cls_Generic_Methods.clickElement(row);
                           Cls_Generic_Methods.customWait(5);
                       }

                    }
                }

                if (selectPO) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_purchaseOrderId, 10);
                     purchaseOrderId = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_purchaseOrderId);
                }

            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }

        return purchaseOrderId;

    }

    public static String createPurchaseTransactionForReturn(String sStore) {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_PurchaseReturn oPage_PurchaseReturn = new Page_PurchaseReturn(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        String itemDescription = "itemDescription"+getRandomString(5);
        String returnTransactionId = "";

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            getGST_valueFromSetting(sStore);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait(5);

            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
                // Creating New Item In Item Master
                boolean addItemStatus = CommonActions.addItemInInventory(itemDescription);
                m_assert.assertTrue(addItemStatus, "Item is Added in Item Master Description as: <b> " + itemDescription + "</b>");

                if (addItemStatus) {
                    // Creating New Purchase To Return
                    boolean bPurchaseTransactionFound = createPurchaseTransaction();
                    m_assert.assertTrue(bPurchaseTransactionFound, "Purchase transaction found for item : <b> " + itemDescription + "</b>");

                    if (bPurchaseTransactionFound) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction),
                                " Approve Button Clicked In Purchase Transaction");
                        Cls_Generic_Methods.customWait();
                        try {
                            if (Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseReturn.button_confirmYesTemplate)) {
                                Cls_Generic_Methods.clickElement(oPage_PurchaseReturn.button_confirmYesTemplate);
                                Cls_Generic_Methods.customWait(3);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            m_assert.assertFatal(" Confirmation Popup is Not coming" + e);
                        }

                        String grnNo = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_transactionID);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_returnPurchaseTransaction),
                                "Return Button Clicked For Selected Transaction In Purchase Transaction");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_returnPurchaseTransactionTemplate, 2);

                        Cls_Generic_Methods.selectElementByIndex(oPage_Purchase.select_vendorList, 1);
                        Cls_Generic_Methods.customWait();
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_returnQuantityListInReturnPurchaseTemplate.get(0), "1"),
                                " Return Quantity Enter as <b> " + "1" + "</b> for Item No. ");
                        Cls_Generic_Methods.customWait(1);

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                                "Save Changes Button CLicked In Return Purchase");
                        Cls_Generic_Methods.customWait();

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_confirmButton),
                                "Confirm Button Clicked");
                        Cls_Generic_Methods.customWait(4);
                        String  returnTransactionIdUI = Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_transactionId);
                        returnTransactionId  = returnTransactionIdUI.split(":")[1].trim();
                        Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 5);

                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
       return  returnTransactionId;
    }

    public static String createPurchaseTransaction(String sStore) {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        String itemDescription = "itemDescription"+getRandomString(5);
        String transactionId = "";

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            getGST_valueFromSetting(sStore);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait(5);

            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
                // Creating New Item In Item Master
                boolean addItemStatus = CommonActions.addItemInInventory(itemDescription);
                m_assert.assertTrue(addItemStatus, "Item is Added in Item Master Description as: <b> " + itemDescription + "</b>");

                if (addItemStatus) {
                    // Creating New Purchase To Return
                    boolean bPurchaseTransactionFound = createPurchaseTransaction();
                    m_assert.assertTrue(bPurchaseTransactionFound, "Purchase transaction found for item : <b> " + itemDescription + "</b>");
                    transactionId = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_transactionID);

                }


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
        return  transactionId;
    }

    public static boolean createPurchaseTransaction() {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        boolean vendorFound = false;
        boolean bPurchaseTransactionFound = false;


        try {

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
            Cls_Generic_Methods.waitForPageLoad(driver, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_purchaseNew),
                    "New Button clicked in Purchase Transaction");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.dropdown_selectVendorInStore, 2);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_Vendor_search, 4);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_Vendor_search, (vendorName + " " + vendor_address));
            Cls_Generic_Methods.customWait();
            for (WebElement eVendor : oPage_Purchase.list_select_vendor) {
                Cls_Generic_Methods.clickElementByJS(driver, eVendor);
                vendorFound = true;
                break;
            }
            String vendorFullNameAndAddress = vendorName + " - " + vendor_address;

            m_assert.assertTrue(vendorFound, "Vendor present in dropdown: <b> " + vendorName + "</b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);

            // Adding New Item Stock to Inventory
            boolean bItemStockAdded = addNewItemStockToPurchase(itemDescription);

            if (bItemStockAdded) {

                // Verifying Added Item Stock Calculation

                boolean bItemStockCalculation = getAddedItemStockCalculation();

                if (bItemStockCalculation) {

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);

                    // Filling Other Mandatory Fields Like Bill date , Notes Etc

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes),
                            " Transaction Notes Entered as :<b> " + transactionNotes + "</b>");
                    Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, "Bill"),
                            "Bill Type Selected:<b> " + "Bill" + " </b>");
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber),
                            "Bill Number: <b> " + billNumber + " </b>");
                    Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                            "Date of bill selected:<b> " + Cls_Generic_Methods.getTextInElement(oPage_Purchase.input_todayBillDate) + " </b>");

                    String purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                    purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                    m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                    //  purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                    purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                    if(purchaseTransactionTime.length() == 6){
                        purchaseTransactionTime = "0"+purchaseTransactionTime;
                    }
                    m_assert.assertTrue("Requisition order time:<b> " + purchaseTransactionTime + "</b>");

                    String purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                    purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);

                    m_assert.assertTrue("Purchase Transaction date:<b> " + purchaseTransactionDate + "|" + purchaseTransactionTime + "</b>");
                    String totalNetAmountBeforeOtherCharges = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalNetAmount, "value");

                    // Verifying Net Amount Calculation After Adding other Charges

                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_otherCharges, "Item_other"),
                            "Other Charges: <b> " + "Item_other" + " </b>");
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_otherChargesAmount, "10");

                    String totalNetAmountAfterOtherChargesAddition = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalNetAmount, "value");


                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                            "Save Changes Button Clicked In A Lot Inventory Template");
                    Cls_Generic_Methods.customWait(7);

                    //Verifying Created Purchase Transaction In List

                    bPurchaseTransactionFound = CommonActions.getPurchaseTransactionFromTransactionList(purchaseTransactionDate + "|" + purchaseTransactionTime,
                            totalNetAmountAfterOtherChargesAddition, vendorFullNameAndAddress, transactionNotes, "Open");

                    m_assert.assertTrue(bPurchaseTransactionFound, "Purchase Transaction Found, Created Successfully for date " + purchaseTransactionDate + "and " + purchaseTransactionTime);
                    Cls_Generic_Methods.customWait();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
        return bPurchaseTransactionFound;
    }

    public static String createRefund(String type){

        Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Bills oPage_Bills = new Page_Bills(driver);
        Page_Sale oPage_Sale = new Page_Sale(driver);
        String sRefundId = "";

        try {
             if(type.equalsIgnoreCase("Advance")){
                 advanceForRefund();

             }else if(type.equalsIgnoreCase("Bill")){
                 createBillForRefund();
             }

             Cls_Generic_Methods.clickElement(oPage_Bills.button_refundButton);
             Cls_Generic_Methods.customWait();
             m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_refundReason, "Refund Check"),
                    " Refund Reason Text Box Displayed and value entered as : Refund Check ");

             m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Sale.select_modeOfPaymentInRefund, "Cash"),
                    " Mode of Payment Selected as "+"Cash");

             Cls_Generic_Methods.clearValuesInElement(oPage_Sale.input_refundAmount);
             m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_refundAmount,"10"),
                    " Refund Amount Entered as :"+"10");
             m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_saveRefund),
                    " Save Button Clicked In Refund");
             Cls_Generic_Methods.customWait(5);
             sRefundId = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_refundId);
             Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
             Cls_Generic_Methods.customWait();






        }catch (Exception e) {
            e.printStackTrace();
        }

        return sRefundId;

    }

    public static String returnBill(){

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Sale oPage_Sale = new Page_Sale(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        String sRefundId = "";

        try {

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_returnPurchaseTransaction),
                    "Return Button Displayed And Clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_saveAddNewLot,10);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_returnQty,"1"),
                    " Return Qty Entered as as "+"1");

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_returnItemRemark,"Return Remark 1"),
                    " Return Remark Entered as : Return Remark 1");
            String netAmount = Cls_Generic_Methods.getElementAttribute(oPage_Sale.input_returnNetReturnValue,"value");
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Sale.input_modeOfPaymentInReturnTemplate,"Cash"),
                    " Return Mode Of Payment Selected as : "+"Cash");

            Cls_Generic_Methods.clearValuesInElement(oPage_Sale.input_refundAmountBox);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_refundAmountBox,netAmount),
                    "Refund Amount Entered as "+netAmount);
            Cls_Generic_Methods.customWait();
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_returnNoteTextBox,"sReturnNote"+getRandomString(6)),
                    "Return Note Entered as : "+"sReturnNote");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_SaveChanges),
                    " Save Button Clicked In Return");
            Cls_Generic_Methods.customWait(5);
            sRefundId = Cls_Generic_Methods.getTextInElement(oPage_Sale.text_refundId).trim();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();


        }catch (Exception e) {
            e.printStackTrace();
        }

        return sRefundId;

    }
    public static void advanceForRefund(){

        Page_PatientQueue oPage_PatientQueue = new Page_PatientQueue(driver);
        Page_Bills oPage_Bills = new Page_Bills(driver);

        try {
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills),
                    "Bill Button Displayed and Clicked");
            Cls_Generic_Methods.customWait( 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_advanceReceiptButton),
                    "Advance Button Displayed and Clicked ");

            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_advanceReceiptTemplate, 5),
                    " Advance Receipt Template Opened and Header Displayed as : Advance Receipt");

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientQueue.input_reasonAdvance,"Advance Reason"),
                    " Advance Reason Text box Displayed and Value Entered as : Advance Reason");

            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_PatientQueue.select_mop, "Cash"),
                    " Mode of Payment Selected as : "+"Cash" + " at index 1");
            Cls_Generic_Methods.clearValuesInElement(oPage_PatientQueue.input_amountAdvance);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientQueue.input_amountAdvance, "100"),
                    " Amount Entered as : "+"100"+" at index 0");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_saveAdvance,4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_saveAdvance),
                    " Save Advance Button Clicked");
            Cls_Generic_Methods.customWait( 7);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  static void createBillForRefund(){
        Page_Bills oPage_Bills=new Page_Bills(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails=new Page_PatientAppointmentDetails(driver);
        String billNumber = "";

        try {
            // String inputServiceValue = "Dressing";
            String paymentMode = "Cash";
            // String inputPackageValue = "Cornia";
            String remarkComments = "this is REMARK comment";
            String internalComments = "this is INTERNAL comment";
            String additionalDiscount = "10.0";
            String discountComments = "this is DISCOUNT comment";

            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Bills.text_billHeading, 20),
                    "Bill form is visible");
            // click plus button and select package
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickPlusButton,7);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickPlusButton),
                    "Plus button clicked for selecting package");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.link_newPackage,7);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.link_newPackage),
                    "click on new package");

            for (WebElement billRow : oPage_Bills.bill_rowsOfServices) {

                if (billRow.isDisplayed()) {

                    try {
                        List<WebElement> packageDetailsOnRow = billRow.findElements(By.xpath("./child::*"));
                        for (WebElement itemOnRow : packageDetailsOnRow) {
                            String classAttr = Cls_Generic_Methods.getElementAttribute(itemOnRow, "class");
                            if (classAttr.contains("field_for_description")) {
                                Cls_Generic_Methods.clickElement(driver, itemOnRow);
                                Cls_Generic_Methods.customWait(3);
                                oPage_Bills.input_billService.sendKeys(Keys.DOWN, Keys.ENTER);
                                // driver.findElement(By.xpath("//input[@role='textbox']")).sendKeys(Keys.DOWN,
                                // Keys.ENTER);
                                break;
                            }
                        }

                    } catch (ElementNotInteractableException e) {
                        e.printStackTrace();
                        m_assert.assertTrue(false, "Services/Packages are not selected" + e);
                    }
                }
            }

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_billRemarksComment, remarkComments),
                    "Remark comments are " + remarkComments);

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_billInternalComment, internalComments),
                    "Internal comments are  " + internalComments);

            Cls_Generic_Methods.clearValuesInElement(oPage_Bills.input_additionalDiscount);

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_additionalDiscount, additionalDiscount),
                    "additional discount is entered as " + additionalDiscount);

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_billDiscountComment, discountComments),
                    "Discount comments are  " + discountComments);

            // select mode of payment section
            boolean createDraftBill=false;


            if(!createDraftBill) {
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_modeOfPayment, paymentMode),
                        paymentMode + " option is selected for Mode Of Payment");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills),
                        "<b>Add</b> Save Final Bill Button is clicked");
            }else{
                String sRemainingAmount=Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_balanceRemainingAmount,"value");
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.input_amountPendingOpd);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_amountPendingOpd,sRemainingAmount);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_saveDraftBillButton),
                        "<b>Add</b> Save as Draft Bill Button is clicked");
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertTrue(false, "Error occurred while filling Bill form - " + e);
        }
    }

    public static String createRequisitionInStore(String sStoreA,String sStoreB) {

        Page_Requisition oPage_Requisition = new Page_Requisition(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new Page_InventorySearchCommonElements(driver);
        boolean itemFoundInRequisition = false;
        boolean bRequisitionOrderFound = false;
        boolean receivingStoreFound = false;
        String requisitionDateAndTime = "";
        String requisitionId = "";
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sStoreA);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_newRequisition),
                        "New Button clicked in Order Requisition");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.dropdown_receivingStoreInRequisition, 3);

                //Select receiving store
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.dropdown_receivingStoreInRequisition),
                        "Receiving Store Dropdown clicked");
                for (WebElement receivingStore : oPage_Requisition.list_storesListInReceivingStoresRequisition) {
                    if (sStoreB.contains(Cls_Generic_Methods.getTextInElement(receivingStore))) {
                        Cls_Generic_Methods.clickElement(receivingStore);
                        receivingStoreFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(receivingStoreFound, "Receiving store selected: <b>" + sStoreB + " </b>");
                Cls_Generic_Methods.customWait();

                // Select requisition type
                String sRequisitionType = "Normal";
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.dropdown_requisitionType),
                        "Requisition Type Dropdown clicked");
                for (WebElement eType : oPage_Requisition.list_requisitionType) {
                    oPage_Requisition = new Page_Requisition(driver);
                    if (sRequisitionType.contains(Cls_Generic_Methods.getTextInElement(eType))) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(eType),
                                "Requisition Type selected: <b> " + sRequisitionType + " </b>");
                        Cls_Generic_Methods.customWait(3);
                        break;
                    }
                }

                Cls_Generic_Methods.clickElement(oPage_Requisition.input_itemSearchBox);
                Cls_Generic_Methods.clearValuesInElement(oPage_Requisition.input_itemSearchBox);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_itemSearchBox, sItemDescription);
                Cls_Generic_Methods.customWait(3);
                oPage_Requisition.input_itemSearchBox.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(3);

                for (WebElement eItemName : oPage_Requisition.list_itemNameInPurchaseStore) {
                    if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(sItemDescription)) {
                        Cls_Generic_Methods.clickElement(eItemName);
                        Cls_Generic_Methods.customWait();
                        itemFoundInRequisition = true;
                        break;
                    }
                }

                m_assert.assertTrue(itemFoundInRequisition, "Item found in requisition: <b> " + sItemDescription + "</b>");


                if (itemFoundInRequisition) {
                    //getting rol_date and rol_time value
                    String requisitionOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderTime, "value");
                    requisitionOrderTime = requisitionOrderTime.replaceAll("\\s+", "");
                    requisitionOrderTime = requisitionOrderTime.replace("pm", "PM").replace("am", "AM");
                    if (requisitionOrderTime.length() == 6) {
                        requisitionOrderTime = "0" + requisitionOrderTime;
                    }
                    m_assert.assertTrue("Requisition order time:<b> " + requisitionOrderTime + "</b>");

                    String requisitionOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderDate, "value");
                    requisitionOrderDate = requisitionOrderDate.replaceAll("/", "-");
                    m_assert.assertTrue("Requisition order date:<b> " + requisitionOrderDate + "</b>");

                    requisitionDateAndTime = requisitionOrderDate + "  |  " + requisitionOrderTime;
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_quantityForRequisition, "1");
                    Cls_Generic_Methods.customWait();
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_saveRequisition),
                            "Requisition saved");

                    Cls_Generic_Methods.customWait(4);
                }

                for (WebElement eDate : oPage_Requisition.list_dateTimeOfRequisition) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate);
                    String dateAndTime = dateTimeFull.split("\n")[0].trim();
                    //requisitionId = dateTimeFull.split("\n")[1].trim();

                    if (requisitionDateAndTime.contains(dateAndTime)) {
                        bRequisitionOrderFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 5);
                        break;
                    }
                }

                m_assert.assertTrue(bRequisitionOrderFound, "Requisition order found in the requisition page for number: ");
                if (bRequisitionOrderFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 5);

                    requisitionId = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_viewOrder_requisitionNo);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_viewOrderRequisition),
                            "View Order clicked for requisition");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_approveRequisition, 15);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_cancelRequisition),
                            "Cancel requisition Order clicked");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.input_cancelReason, 15);

                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Requisition.input_cancelReason)) {
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_cancelReason,"Cancel");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.input_cancelRequisition),
                                "<b> Requisition Order Cancel clicked </b> ");
                    }

                    Cls_Generic_Methods.customWait(5);
                }

            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return  requisitionId;
    }

    public static String createRequisitionInStoreForIssue(String sStoreA,String sStoreB) {

        Page_Requisition oPage_Requisition = new Page_Requisition(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new Page_InventorySearchCommonElements(driver);
        boolean itemFoundInRequisition = false;
        boolean bRequisitionOrderFound = false;
        boolean receivingStoreFound = false;
        String requisitionDateAndTime = "";
        String requisitionId = "";
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sStoreA);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_newRequisition),
                        "New Button clicked in Order Requisition");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.dropdown_receivingStoreInRequisition, 3);

                //Select receiving store
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.dropdown_receivingStoreInRequisition),
                        "Receiving Store Dropdown clicked");
                for (WebElement receivingStore : oPage_Requisition.list_storesListInReceivingStoresRequisition) {
                    if (sStoreB.contains(Cls_Generic_Methods.getTextInElement(receivingStore))) {
                        Cls_Generic_Methods.clickElement(receivingStore);
                        receivingStoreFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(receivingStoreFound, "Receiving store selected: <b>" + sStoreB + " </b>");
                Cls_Generic_Methods.customWait();

                // Select requisition type
                String sRequisitionType = "Normal";
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.dropdown_requisitionType),
                        "Requisition Type Dropdown clicked");
                for (WebElement eType : oPage_Requisition.list_requisitionType) {
                    oPage_Requisition = new Page_Requisition(driver);
                    if (sRequisitionType.contains(Cls_Generic_Methods.getTextInElement(eType))) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(eType),
                                "Requisition Type selected: <b> " + sRequisitionType + " </b>");
                        Cls_Generic_Methods.customWait(3);
                        break;
                    }
                }

                Cls_Generic_Methods.clickElement(oPage_Requisition.input_itemSearchBox);
                Cls_Generic_Methods.clearValuesInElement(oPage_Requisition.input_itemSearchBox);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_itemSearchBox, sItemDescription);
                Cls_Generic_Methods.customWait(3);
                oPage_Requisition.input_itemSearchBox.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(3);

                for (WebElement eItemName : oPage_Requisition.list_itemNameInPurchaseStore) {
                    if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(sItemDescription)) {
                        Cls_Generic_Methods.clickElement(eItemName);
                        Cls_Generic_Methods.customWait();
                        itemFoundInRequisition = true;
                        break;
                    }
                }

                m_assert.assertTrue(itemFoundInRequisition, "Item found in requisition: <b> " + sItemDescription + "</b>");


                if (itemFoundInRequisition) {
                    //getting rol_date and rol_time value
                    String requisitionOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderTime, "value");
                    requisitionOrderTime = requisitionOrderTime.replaceAll("\\s+", "");
                    requisitionOrderTime = requisitionOrderTime.replace("pm", "PM").replace("am", "AM");
                    if (requisitionOrderTime.length() == 6) {
                        requisitionOrderTime = "0" + requisitionOrderTime;
                    }
                    m_assert.assertTrue("Requisition order time:<b> " + requisitionOrderTime + "</b>");

                    String requisitionOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderDate, "value");
                    requisitionOrderDate = requisitionOrderDate.replaceAll("/", "-");
                    m_assert.assertTrue("Requisition order date:<b> " + requisitionOrderDate + "</b>");

                    requisitionDateAndTime = requisitionOrderDate + "  |  " + requisitionOrderTime;
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_quantityForRequisition, "1");
                    Cls_Generic_Methods.customWait();
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_saveRequisition),
                            "Requisition saved");

                    Cls_Generic_Methods.customWait(4);
                }

                for (WebElement eDate : oPage_Requisition.list_dateTimeOfRequisition) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate);
                    String dateAndTime = dateTimeFull.split("\n")[0].trim();
                    //requisitionId = dateTimeFull.split("\n")[1].trim();

                    if (requisitionDateAndTime.contains(dateAndTime)) {
                        bRequisitionOrderFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 5);
                        break;
                    }
                }

                m_assert.assertTrue(bRequisitionOrderFound, "Requisition order found in the requisition page for number: ");
                if (bRequisitionOrderFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 5);

                    requisitionId = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_viewOrder_requisitionNo);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_viewOrderRequisition),
                            "View Order clicked for requisition");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_approveRequisition, 15);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_approveRequisition),
                            "Approve requisition Order clicked");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_confirmRequisition, 15);

                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Requisition.button_confirmRequisition)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_confirmRequisition),
                                "<b> Requisition Order Confirmed clicked </b> ");
                    }

                    Cls_Generic_Methods.customWait(5);
                }

            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return  requisitionId;
    }

    public static String validatingCreateSONTransaction(String sStoreName) {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SON oPage_SON = new Page_SON(driver);

        String sonId = "";

        try {

            //Opening Pharmacy Store

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sStoreName);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 8);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            //Opening the SON Transaction Page and validate page

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SON");


            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SON.button_addNewButton),
                    "New Button Displayed and Clicked");

            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SON.header_addStockOpeningNoteHeader, 8),
                    " Add Son Template Opened and Header Displayed as : <b> "+ Cls_Generic_Methods.getTextInElement(oPage_SON.header_addStockOpeningNoteHeader)+"</b>");

            String sTxnDate = Cls_Generic_Methods.getElementAttribute(oPage_SON.input_TransitionDate, "value");
            String sTxnTime = Cls_Generic_Methods.getElementAttribute(oPage_SON.input_OrderTime, "value");
            sTxnTime = sTxnTime.replaceAll("\\s+", "");
            sTxnTime = CommonActions.getRequiredFormattedDateTime("K:mma","hh:mma",sTxnTime);
            sTxnTime = sTxnTime.replace("am", "AM").replace("pm","PM");
            sTxnDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", sTxnDate);
            m_assert.assertTrue("SON Transaction date:<b> " + sTxnDate + "|" +sTxnTime+"</b>");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SON.input_documentNumber, 4);

            //Adding medication for SON transaction

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_variantDescription, myMedicationName),
                    " Search Box Displayed and Description Values Entered  as <b>" + myMedicationName + "</b> to search");
            Cls_Generic_Methods.customWait(2);
            oPage_SON.input_variantDescription.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(5);
            boolean myMedicationFound = false;
            for (WebElement e : oPage_SON.list_medicationNameOnLeft) {
                if (Cls_Generic_Methods.getTextInElement(e).contains(myMedicationName)) {
                    Cls_Generic_Methods.clickElement(e);
                    myMedicationFound = true;
                    Cls_Generic_Methods.customWait();
                    break;
                }
            }

            m_assert.assertInfo(myMedicationFound, "Required medication found in search box");
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SON.header_addNewLot, 15),
                    "Add Lot Template Opened and Displayed as : <b> "+Cls_Generic_Methods.getTextInElement(oPage_SON.header_addNewLot)+"</b>");

            boolean bLotCreated = createLot("100","batchNo"+getRandomString(5), "5%",packageQuantity,"5","5","5","120");
            if(bLotCreated){


                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_documentNumber,"documentNumber"+getRandomString(4)),
                        " Document Number Entered as : <b> "+"documentNumber"+getRandomString(4)+"</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_transactionNotes, transactionNotes),
                        " Transaction Notes Entered as :<b> " +transactionNotes+ "</b>");
                String totalCostIncludingTax = Cls_Generic_Methods.getElementAttribute(oPage_SON.input_totalAmountIncludingTax,"value");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SON.button_saveChanges),
                        " Save Changes Button Clicked In SON Template ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SON.button_addNewButton, 15);

                boolean bSONTransactionFound = getSONTransactionFromTransactionList(sTxnDate + "|" +sTxnTime,
                        transactionNotes,"documentNumber"+getRandomString(4),totalCostIncludingTax,"Open");

                m_assert.assertTrue(bSONTransactionFound, "Son Transaction Found, Created Successfully for date and time :<b> "
                        +sTxnDate + "|" +sTxnTime +"</b>" );

                sonId = Cls_Generic_Methods.getTextInElement(oPage_SON.text_transactionIDKeyAndValue).split(" : ")[1];

            }else{
                m_assert.assertTrue(bLotCreated,"Lot Not added In Son Template");
            }

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            Cls_Generic_Methods.customWait();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
       return sonId;
    }

    public static void createSalesOrderForSRNTransaction(String sStore) {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        String sItemDescription[] = {"NONSTOCKTESTITEM", "Xion"};


        myPatient = map_PatientsInExcel.get("002");
        boolean bSalesOrderFound = false;

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {

                // Open Optical Store

                CommonActions.selectStoreOnApp(sStore);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");

                // Creating New Item In Item Master

                boolean addFirstItemStatus = addItemForSRNTransaction(sItemDescription[0]);

                if (addFirstItemStatus) {

                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
                    Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_addNewButtonInOrder);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.button_addNewPatient, 4);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_NewPatientRegisteration.button_addNewPatient),
                            "<b>Add New Patient</b> Button is clicked");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, 8);

                    // Entering Essential Form Data

                    if (!myPatient.getsSALUTATION().isEmpty()) {
                        m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                        oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()),
                                "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
                    }

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                            "First Name is entered as - " + myPatient.getsFIRST_NAME());

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_NewPatientRegisteration.input_middleNameOnPatientRegForm, myPatient.getsMIDDLE_NAME()),
                            "Middle Name is entered as - " + myPatient.getsMIDDLE_NAME());

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_NewPatientRegisteration.input_lastNameOnPatientRegForm, myPatient.getsLAST_NAME()),
                            "Last Name is entered as - " + myPatient.getsLAST_NAME());

                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                                    myPatient.getsMOBILE_NUMBER()),
                            "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm),
                            " Save Button Clicked in Patient Register Form");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_TxnDate, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_nonStockable),
                            "Non Stockable Button Clicked IN Sales Order Template");
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_SalesOrder.list_rawOfNonStockableItemOnLeftInSearchResult, 2);

                    Cls_Generic_Methods.clickElement(oPage_SalesOrder.textbox_vendorNameDropdown);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.text_FirstVendorNameInVendorList, 2);
                    Cls_Generic_Methods.clickElement(oPage_SalesOrder.text_FirstVendorNameInVendorList);
                    Cls_Generic_Methods.customWait();

                    boolean firstItem = validatingSearchedItemInList(oPage_SalesOrder.list_rawOfNonStockableItemOnLeftInSearchResult, sItemDescription[0]);
                    enteringValueItemInList(oPage_SalesOrder.list_inputQty.get(0), "1",
                            oPage_SalesOrder.input_itemMRP.get(0), "100");



                    m_assert.assertTrue(firstItem, "Non Stockable Item Found and clicked as " + sItemDescription[0]);

                    String salesOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderTime, "value");
                    salesOrderTime = salesOrderTime.replaceAll("\\s+", "");
                    salesOrderTime = CommonActions.getRequiredFormattedDateTime("h:mma", "hh:mma", salesOrderTime);

                    m_assert.assertTrue("Sales order time:<b> " + salesOrderTime + "</b>");

                    String salesOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_TxnDate, "value");
                    salesOrderDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd-MM-yyyy", salesOrderDate);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_estimatedOrderDeliveryDate),
                            "Estimated Delivery Date Clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_currentEstimatedOrderDeliveryDate, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_currentEstimatedOrderDeliveryDate),
                            "Estimated Delivery Date entered as :" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.input_currentEstimatedOrderDeliveryDate));
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_netAmount, 1);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                            "Save Changes Button Clicked In Sales Order Template");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 10);
                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 4);




                } else {
                    m_assert.assertInfo(addFirstItemStatus, "Item is not Added in Item Master Description as: <b>" + sItemDescription[0] + "</b>");

                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    public static String validateCreateSRNTransactionFunctionality(String sStore) {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        Page_SRN oPage_SRN = new Page_SRN(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

        boolean bSrnSalesOrderFound = false;
        String srnId = "";

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {
                // Creating SRN Transaction For Sales Order

                createSalesOrderForSRNTransaction(sStore);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait(5);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SRN");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SRN.button_addNew),
                        "New Button CLicked In SRN Transaction");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SRN.header_stockReceiveNoteHeader, 5);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SRN.header_stockReceiveNoteHeader),
                        " SRN Template Open With Heading " + Cls_Generic_Methods.getTextInElement(oPage_SRN.header_stockReceiveNoteHeader));

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SRN.input_srnNotes, "srnTransactionNotes"),
                        "SRN Notes Entered as : " + "srnTransactionNotes");


                int noOfSrnItem = oPage_SRN.list_salesOrderListInSRNTemplate.size();
                for (WebElement srnOrderItem : oPage_SRN.list_salesOrderListInSRNTemplate) {
                    if (Cls_Generic_Methods.isElementDisplayed(srnOrderItem)) {

                       int index = oPage_SRN.list_salesOrderListInSRNTemplate.indexOf(srnOrderItem);
                        List<WebElement> srnOrderRow = srnOrderItem.findElements(By.xpath("./child::td"));

                        if (noOfSrnItem == index) {

                            bSrnSalesOrderFound = true;
                            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, srnOrderRow.get(1)),
                                    " SRN Order Clicked from Left Panel");
                            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_SRN.list_mrpListInSrn, 5);
                            break;

                        }

                    }
                }

                m_assert.assertTrue(bSrnSalesOrderFound, "SRN Order Found in left panel and Clicked");

                for (WebElement eDescription : oPage_SRN.list_itemDescriptionListInSrn) {

                    int index = oPage_SRN.list_itemDescriptionListInSrn.indexOf(eDescription);


                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SRN.list_unitCostListInSrn.get(index), "100"),
                            "Unit cost entered as : <b>" + "100" + "</b> for item " + sItemDescription);

                    Cls_Generic_Methods.customWait();

                }



                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SRN.dropdown_vendorDropdownArrow, 2);
                Cls_Generic_Methods.clickElement(oPage_SRN.dropdown_vendorDropdownArrow);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_searchInputForSelectField, 2);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_searchInputForSelectField, vendorName);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_SalesOrder.list_selectVendorInStore, 2);
                for (WebElement eVendor : oPage_SalesOrder.list_selectVendorInStore) {
                    if (Cls_Generic_Methods.getTextInElement(eVendor).contains(vendorName)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(eVendor),
                                "Vendor Name Selected as :" + vendorName);
                        break;
                    }
                }


                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_SRN.select_otherCharges, "Item_other"),
                        "Other Charges: <b> " + "Item_other" + " </b>");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_SRN.input_otherChargesAmount, "10");


                String totalNetAmountAfterOtherChargesAddition = Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnNetAmountValue, "value");

                Cls_Generic_Methods.clickElement(oPage_SRN.dropdown_selectBillType);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_SRN.dropdown_selectBillType, "Bill"),
                        "Bill Type Selected:<b> " + "Bill" + " </b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SRN.input_billNumber, billNumber),
                        "Bill Number: <b> " + billNumber + " </b>");
                Cls_Generic_Methods.clickElement(oPage_SRN.input_billDate);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SRN.input_todayBillDate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SRN.input_todayBillDate),
                        "Date of bill selected:<b> " + Cls_Generic_Methods.getTextInElement(oPage_Purchase.input_todayBillDate) + " </b>");

                String srnDate = Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnTransactionDateValue, "value");
                String srnTime = Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnTransactionTimeValue, "value");

                if (srnTime.length() == 7) {
                    srnTime = "0" + srnTime;
                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "Save Changes Button Clicked In A Lot Inventory Template");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 15);

                srnTime = srnTime.replaceAll("\\s+", "");
                srnTime = srnTime.replace("am", "AM").replace("pm", "PM");
                srnDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", srnDate);


                for (WebElement eValue : oPage_Purchase.list_transactionPurchaseList) {

                    if (Cls_Generic_Methods.isElementDisplayed(eValue)) {

                        List<WebElement> salesOrderRow = eValue.findElements(By.xpath("./child::*"));

                        String dateTimeFullSrn = Cls_Generic_Methods.getTextInElement(salesOrderRow.get(0));
                        String note = Cls_Generic_Methods.getTextInElement(salesOrderRow.get(1));
                        String srnNo = Cls_Generic_Methods.getTextInElement(salesOrderRow.get(2));
                        String amount = Cls_Generic_Methods.getTextInElement(salesOrderRow.get(3));

                        if (dateTimeFullSrn.equalsIgnoreCase(srnDate + "  |  " + srnTime) &&
                                note.equalsIgnoreCase("srnTransactionNotes") &&
                                (!srnNo.isEmpty()) &&
                                amount.equalsIgnoreCase(String.valueOf(Double.parseDouble(totalNetAmountAfterOtherChargesAddition)))
                        ) {
                            Cls_Generic_Methods.clickElement(eValue);
                            Cls_Generic_Methods.customWait(5);

                            break;
                        }
                    }
                }

                srnId = Cls_Generic_Methods.getTextInElement(oPage_SRN.text_transactionIdRhs).split(":")[1].trim();



            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        return srnId;
    }

    public static String validatingAdjustmentTransaction(String sStore) {

        Page_Adjustment oPage_Adjustment = new Page_Adjustment(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        String adjustmentId = "";

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 20);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {

                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Adjustment");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Adjustment.button_New, 10);
                    Cls_Generic_Methods.customWait(2);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Adjustment.button_New),
                            " <b> New Button </b> Clicked In adjustment");
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Adjustment.header_Adjustment, 6),
                            " Adjustment Header Displayed");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Adjustment.button_Description),
                            "<b> Description Button </b> Clicked In New Adjustment Template");
                    Cls_Generic_Methods.customWait();

                    for (WebElement eMedication : oPage_Adjustment.list_namesOfMedicinesOnLeftInSearchResult) {

                                Cls_Generic_Methods.clickElement(eMedication);
                                Cls_Generic_Methods.customWait(5);
                                break;

                    }

                for(WebElement eSelect : oPage_Adjustment.list_adjustmentAddDeductSelectList) {
                    int index = oPage_Adjustment.list_adjustmentAddDeductSelectList.indexOf(eSelect);
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(eSelect,"Add to Lot"),
                            "Add to Lot"+"Selected");
                }

                    for(WebElement eQty : oPage_Adjustment.list_inputQtyList) {

                        int index = oPage_Adjustment.list_inputQtyList.indexOf(eQty);
                        Cls_Generic_Methods.clearValuesInElement(eQty);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(eQty,"1"),
                                " Qty Entered as : "+"1");
                    }



                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Adjustment.input_transactionNote,"transactionNote"+getRandomString(5)),
                            " Transaction Notes Entered As :"+"transactionNote");

                    Cls_Generic_Methods.customWait(1);
                    Cls_Generic_Methods.clickElement(oPage_Adjustment.input_SaveChanges);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Adjustment.row_AdjustmentTransaction, 10);

                for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

                    if (Cls_Generic_Methods.isElementDisplayed(row)) {
                        List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

                        String noteOnUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(2));


                        if (
                                noteOnUI.equalsIgnoreCase("transactionNote"+getRandomString(5))
                        ) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(row),
                                    "Adjustment Transaction Clicked In Transaction List");
                            Cls_Generic_Methods.customWait(3);
                            break;
                        }

                    }
                }

                adjustmentId = Cls_Generic_Methods.getTextInElement(oPage_Adjustment.text_transactionIdInAdjustment).split(":")[1].trim();


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        return adjustmentId;

    }

    public static String createIssueTransaction(String sStoreA,String sStoreB) {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        String issueId = "";

        boolean bTransferEntryFound = false ;
        try {
            String reqId = createRequisitionInStoreForIssue(sStoreA,sStoreB);
            try {
                validateRequisitionReceivedAndTransferFromHub(sStoreB,reqId);
                try {
                    Cls_Generic_Methods.customWait();
                    CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
                    CommonActions.selectStoreOnApp(sStoreB);
                    Cls_Generic_Methods.switchToOtherTab();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                            "Central hub popup closed");
                    Cls_Generic_Methods.customWait();
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
                    Cls_Generic_Methods.customWait( 6);

                    for (WebElement eValue : oPage_Transfer.list_tableDataTransfer) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(eValue),
                                    " Issue Record CLicked In Store");
                            bTransferEntryFound = true;
                            Cls_Generic_Methods.customWait(5);
                            break ;

                    }
                    issueId = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_issueIdInTransfer.findElement(By.xpath(".span[1]")));

                    Cls_Generic_Methods.customWait();


                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        return issueId;
    }
    public static void validateRequisitionReceivedAndTransferFromHub(String sStore, String requisitionId) {
        // 8	order >> requisition received
        // 9	transfer items
        // 10	Approve transfer from central hub

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        boolean bRequisitionReceivedFound = false;

        try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition Received");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
                //find created requisition in requisition received
                for (WebElement e : oPage_RequisitionReceived.list_dateTimeOfRequisitionReceived) {
                    String dataTimeAndRequisition = Cls_Generic_Methods.getTextInElement(e);
                    String requisitionNumberInIndentUI = dataTimeAndRequisition.split("\n")[1].trim();
                    if (requisitionId.equals(requisitionNumberInIndentUI)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(e),
                                " Requisition Record Clicked In Requisition Received");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 4);
                        bRequisitionReceivedFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(bRequisitionReceivedFound, "Requisition Received found ");

                if (bRequisitionReceivedFound) {

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_newTransactionRequisition),
                            "New transaction button clicked in requisition received");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.list_transferItem, 15);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.list_transferItem),
                            "Transfer item clicked: <b> " + oPage_RequisitionReceived.list_transferItem.getText() + "</b>");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.input_issueQuantity, 10);
                    Cls_Generic_Methods.clearValuesInElement(oPage_RequisitionReceived.input_issueQuantity);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RequisitionReceived.input_issueQuantity, "1"),
                            "Transfer quantity: <b> " + "1" + "</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_confirmTransfer),
                            "Item Transfer confirmed from requisition");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.list_transferItem, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_saveTransfer),
                            "Transfer saved");
                    Cls_Generic_Methods.customWait(10);

                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        } finally {
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        }
    }

    public static String fillBillForm() throws Exception {
        Page_Bills oPage_Bills=new Page_Bills(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails=new Page_PatientAppointmentDetails(driver);
        String billNumber = "";

        try {
            // String inputServiceValue = "Dressing";
            String paymentMode = "Cash";
            // String inputPackageValue = "Cornia";
            String remarkComments = "this is REMARK comment";
            String internalComments = "this is INTERNAL comment";
            String additionalDiscount = "10.0";
            String discountComments = "this is DISCOUNT comment";

            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Bills.text_billHeading, 20),
                    "Bill form is visible");
            // click plus button and select package
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickPlusButton,7);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickPlusButton),
                    "Plus button clicked for selecting package");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.link_newPackage,7);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.link_newPackage),
                    "click on new package");
             Cls_Generic_Methods.customWait();
             Cls_Generic_Methods.scrollToTop();
             Cls_Generic_Methods.customWait();

            for (WebElement billRow : oPage_Bills.bill_rowsOfServices) {

                if (billRow.isDisplayed()) {

                    try {
                        List<WebElement> packageDetailsOnRow = billRow.findElements(By.xpath("./child::*"));
                        for (WebElement itemOnRow : packageDetailsOnRow) {
                            String classAttr = Cls_Generic_Methods.getElementAttribute(itemOnRow, "class");
                            if (classAttr.contains("field_for_description")) {
                                Cls_Generic_Methods.scrollToElementByJS(itemOnRow);
                                Cls_Generic_Methods.customWait();
                                Cls_Generic_Methods.clickElement(driver, itemOnRow);
                                Cls_Generic_Methods.customWait(3);
                                oPage_Bills.input_billService.sendKeys(Keys.DOWN, Keys.ENTER);
                                // driver.findElement(By.xpath("//input[@role='textbox']")).sendKeys(Keys.DOWN,
                                // Keys.ENTER);
                                break;
                            }
                        }

                    } catch (ElementNotInteractableException e) {
                        e.printStackTrace();
                        m_assert.assertTrue(false, "Services/Packages are not selected" + e);
                    }
                }
            }

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_billRemarksComment, remarkComments),
                    "Remark comments are " + remarkComments);

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_billInternalComment, internalComments),
                    "Internal comments are  " + internalComments);

            Cls_Generic_Methods.clearValuesInElement(oPage_Bills.input_additionalDiscount);

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_additionalDiscount, additionalDiscount),
                    "additional discount is entered as " + additionalDiscount);

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_billDiscountComment, discountComments),
                    "Discount comments are  " + discountComments);

            // select mode of payment section
             boolean createDraftBill=false;


            if(!createDraftBill) {
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_modeOfPayment, paymentMode),
                        paymentMode + " option is selected for Mode Of Payment");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills),
                        "<b>Add</b> Save Final Bill Button is clicked");
            }else{
                String sRemainingAmount=Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_balanceRemainingAmount,"value");
                Cls_Generic_Methods.clearValuesInElement(oPage_Bills.input_amountPendingOpd);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_amountPendingOpd,sRemainingAmount);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_saveDraftBillButton),
                        "<b>Add</b> Save as Draft Bill Button is clicked");
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);

            Cls_Generic_Methods.customWait(5);
            String fullBillNumberText = Cls_Generic_Methods.getTextInElement(oPage_Bills.text_billNumber);
            billNumber = fullBillNumberText.split(" : ")[1];
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_closeBill),
                    "<b>Close</b> Button is clicked");
            Cls_Generic_Methods.customWait(3);

            Cls_Generic_Methods
                    .waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertTrue(false, "Error occurred while filling Bill form - " + e);
        }
      return billNumber;
    }
    public static boolean addItemForSRNTransaction(String itemDescription) {

        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_Store oPage_Store = new Page_Store(driver);

        String categoryName = "Non Stockable Automation";
        String hsnCode = "HSN" + getRandomUniqueString(4);
        String itemPossibleVariantName = "Variant1";
        String itemPossibleVariantValue = "1";

        boolean bCategoryFound = false;
        boolean bAddItemStatus = false;

        try {

            Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_searchInventoryInStoreInventory);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, itemDescription);
            Cls_Generic_Methods.customWait(3);
            oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);
            for (WebElement items : oPage_ItemMaster.list_itemListInStoreInventory) {
                List<WebElement> itemNameRow = items.findElements(By.xpath("./child::*"));
                String itemDescriptionText = Cls_Generic_Methods.getTextInElement(itemNameRow.get((1)));

                if (itemDescriptionText.contains(itemDescription)) {
                    bAddItemStatus = true;
                    break;
                }

            }

            if (!bAddItemStatus) {

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Store.button_addNewButtonInventory),
                        " Add Item Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.header_addItemMasterTemplateTitle, 8);

                // Entering Required fields and fill data in Item Details ,Properties and Possible Variant to create Item

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.dropdown_categoryArrow),
                        "Category Dropdown Clicked in add item ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, categoryName);
                Cls_Generic_Methods.customWait(1);

                for (WebElement itemCategory : oPage_ItemMaster.list_inventoryItemCategoryList) {
                    if (Cls_Generic_Methods.getTextInElement(itemCategory).contains(categoryName)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemCategory), "Category selected: <b> " + categoryName + "</b>");
                        bCategoryFound = true;
                        break;
                    }
                }

                if (bCategoryFound) {

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemHsnCode, 1);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemHsnCode, hsnCode),
                            " Item HSN code Entered as : <b>" + hsnCode + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemDescription, itemDescription),
                            "Item Description Entered as : <b>" + itemDescription + "</b>");
                    Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_itemPropertiesTaxList);

                    if (itemDescription.contains("Xion")) {
                        m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_ItemMaster.select_itemPropertiesTaxList, InventoryStore_Data.sSTORE_TAX_RATE_GST18),
                                "Item Properties Tax Entered as : <b>" + InventoryStore_Data.sSTORE_TAX_RATE_GST18 + "</b>");
                    } else {
                        m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_ItemMaster.select_itemPropertiesTaxList, InventoryStore_Data.sSTORE_TAX_RATE_GST5),
                                "Item Properties Tax Entered as : <b>" + InventoryStore_Data.sSTORE_TAX_RATE_GST5 + "</b>");
                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemPossibleVariantName, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemPossibleVariantName, itemPossibleVariantName),
                            "Item Possible Variant Name Entered as : <b>" + itemPossibleVariantName + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.input_itemPossibleVariantValue),
                            "Item Possible Variant Value Clicked");

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemPossibleVariantValue, itemPossibleVariantValue);
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertTrue(CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_ItemMaster.list_itemPossibleVariantValuesList, itemPossibleVariantValue),
                            "Item Possible Variant Value Entered as : <b>" + itemPossibleVariantValue + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_saveAddItemTemplate),
                            "Save Button Clicked with filled required field");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.button_addNewButtonInventory, 17);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, itemDescription);
                    Cls_Generic_Methods.customWait(3);
                    oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
                    Cls_Generic_Methods.customWait(3);
                    for (WebElement items : oPage_ItemMaster.list_itemListInStoreInventory) {
                        List<WebElement> itemNameRow = items.findElements(By.xpath("./child::*"));
                        String itemDescriptionText = Cls_Generic_Methods.getTextInElement(itemNameRow.get((1)));

                        if (itemDescriptionText.contains(itemDescription)) {
                            bAddItemStatus = true;
                            break;
                        }

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }


        return bAddItemStatus;
    }

    public boolean selectItemFromItemMasterList(String sItemDescription) {

        boolean bCreatedItemDataFound = false;

        Page_Master oPage_Master = new Page_Master(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);


        try {
            Cls_Generic_Methods.clearValuesInElement(oPage_Master.input_itemNameSearchInItemMaster);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, sItemDescription);
            Cls_Generic_Methods.customWait(4);
            oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);

            for (WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory) {
                if (Cls_Generic_Methods.isElementDisplayed(itemData)) {

                    List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./child::*"));

                    String itemDescriptionName = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));

                    if (itemDescriptionName.contains(sItemDescription)) {
                        bCreatedItemDataFound = true;
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemData),
                                "<b> "+sItemDescription + " </b> Item Clicked from Item Master List");
                        Cls_Generic_Methods.customWait(5);

                        break;
                    }
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return bCreatedItemDataFound ;
    }
    public static void enteringValueItemInList(WebElement eQty, String sQty, WebElement eMrp, String sMrp) {

        try {
            Cls_Generic_Methods.clearValuesInElement(eQty);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(eQty, sQty), "Quantity Entered as : " + sQty + " for item ");
            String vendorRateCheck = Cls_Generic_Methods.getElementAttribute(eMrp,"value");
            if(vendorRateCheck.isEmpty()) {
                Cls_Generic_Methods.clearValuesInElement(eMrp);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(eMrp, sMrp), "MRP Entered as : " + sMrp + " for item ");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    public static boolean validatingSearchedItemInList(List<WebElement> elementList, String sDescription) {

        boolean bFound = false;
        try {
            for (WebElement eMedication : elementList) {
                if (Cls_Generic_Methods.isElementDisplayed(eMedication)) {
                    String medName = Cls_Generic_Methods.getTextInElement(eMedication.findElement(By.xpath("./td[2]/b")));
                    if (medName.equalsIgnoreCase(sDescription)) {
                        Cls_Generic_Methods.clickElement(eMedication);
                        Cls_Generic_Methods.customWait(5);
                        bFound = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        return bFound;
    }

    public static boolean getSONTransactionFromTransactionList(String dateAndTime, String transactionNotes, String docNumber,
                                                               String totalNetAmount,String status) {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        boolean bSONTransactionFound = false;
        List<String> sonTransactionHeaderList = new ArrayList<String>();


        try {

            for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
                sonTransactionHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
            }

            for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(purchaseRow.get(sonTransactionHeaderList.indexOf("Date | Time")));
                    String sonDocNumber = Cls_Generic_Methods.getTextInElement(purchaseRow.get(sonTransactionHeaderList.indexOf("Doc. Number")));
                    String purchaseNote = Cls_Generic_Methods.getTextInElement(purchaseRow.get(sonTransactionHeaderList.indexOf("Note")));
                    String purchaseAmount = Cls_Generic_Methods.getTextInElement(purchaseRow.get(sonTransactionHeaderList.indexOf("Amount")));
                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(sonTransactionHeaderList.indexOf("Status")));

                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    String purchaseDateAndTime = date + "|" + time;

                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    String purchaseNetAmountUI = decimalFormat.format(Double.parseDouble(purchaseAmount));

                    if (purchaseDateAndTime.equals(dateAndTime) &&
                            purchaseStatus.equalsIgnoreCase(status) &&
                            purchaseNetAmountUI.equalsIgnoreCase(totalNetAmount) &&
                            sonDocNumber.equalsIgnoreCase(docNumber) &&
                            purchaseNote.equalsIgnoreCase(transactionNotes)
                    ) {
                        bSONTransactionFound = true;
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(row),
                                "SON Transaction Clicked  In Transaction List");
                        Cls_Generic_Methods.customWait(2);
                        break;
                    }

                }
            }

            // If Purchase Not Found In Purchase Transaction List on basis of Date and Time and Vendor
            if (!bSONTransactionFound) {
                m_assert.assertTrue(" SON Order Is not found for date : " + dateAndTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        return bSONTransactionFound;

    }
    public static boolean createLot(String unitCostWOTax, String sBatchNo, String taxPercent, String packageUnit, String subPackageUnit, String unit,
                                    String freeUnit, String sSellingPrice){

        Page_SON oPage_SON = new Page_SON(driver);
        boolean bLotCreated = false ;

        try {

            if(Cls_Generic_Methods.isElementDisplayed(oPage_SON.select_selectVariantInAddLot)){
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_SON.select_selectVariantInAddLot, 1),
                        "Variant : <b> " + "500mg" + " </b>");
            }
            if(Cls_Generic_Methods.isElementDisplayed(oPage_SON.input_batchNumberInputField)){
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_batchNumberInputField, sBatchNo),
                        "Batch Number : <b> " + sBatchNo + " </b>");
            }

            if (Cls_Generic_Methods.isElementDisplayed(oPage_SON.select_subStore)) {
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_SON.select_subStore, subStore),
                        "Sub Store: <b> " + subStore + " </b>");
            }
            if (Cls_Generic_Methods.isElementDisplayed(oPage_SON.input_expiryDate)) {
                String sTodayDate = Cls_Generic_Methods.getTodayDate("dd/MM/yyyy");
                String sExpiryDate = Cls_Generic_Methods.getDifferenceInDays(sTodayDate, 60, "dd/MM/yyyy");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysByJS(driver, oPage_SON.input_expiryDate, sExpiryDate), "Entered expiry date as <b> " + sExpiryDate+"</b>");
            }

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_unitCostWOTax, unitCostWOTax),
                    "Unit cost without tax entered as : <b> " + unitCostWOTax + "</b>");
            Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.clearValuesInElement(oPage_SON.input_packageStockField);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_packageStockField, packageUnit),
                    "Package entry entered as : <b> " + packageUnit + "</b>");
            Cls_Generic_Methods.clearValuesInElement(oPage_SON.input_subPackageStockField);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_subPackageStockField, subPackageUnit),
                    "SubPackage entry entered as : <b> " + subPackageUnit + "</b>");
            Cls_Generic_Methods.clearValuesInElement(oPage_SON.input_packageQuantity);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_packageQuantity, unit),
                    "Unit entry entered as : <b> " + unit + "</b>");
            Cls_Generic_Methods.clearValuesInElement(oPage_SON.input_freeUnitStockField);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_freeUnitStockField, freeUnit),
                    "Free unit entry entered as : <b> " + freeUnit + "</b>");

            Cls_Generic_Methods.clearValuesInElement(oPage_SON.input_sellingPrice);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_sellingPrice, sSellingPrice),
                    "Selling Price entered as : <b> " + sSellingPrice + "</b>");

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SON.button_saveLot), "Saving the Lot details");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SON.button_saveChanges, 15);
            if(oPage_SON.list_itemStockNameListInStockInventoryTemplate.size()>0){
                bLotCreated = true ;
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return bLotCreated;
    }
    private static HashMap<String, String> getStockableItemDetails(String itemDescription) {

        String sItemTotalStock = "";
        String itemCode = "";
        String category = "";
        String dispensingUnit = "";
        String taxPercentage = "";
        String packageType = "";
        String subPackageType = "";
        String unitType = "";
        String subPackageValue = "";
        String unitValue = "";
        String hsnCode = "";


        boolean itemFound = false;
        Page_Master oPage_Master = new Page_Master(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);

        HashMap<String, String> itemDetails = new HashMap<>();

        try {

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 8);
            Cls_Generic_Methods.clearValuesInElement(oPage_Master.input_itemNameSearchInItemMaster);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, itemDescription);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_Master.input_itemNameSearchInItemMaster);
            oPage_Master.input_itemNameSearchInItemMaster.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(5);

            for (WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory) {
                if (Cls_Generic_Methods.isElementDisplayed(itemData)) {
                    List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./child::*"));
                    String itemDescriptionName = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));
                    String itemStock = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((2)));

                    if (itemDescriptionName.contains(itemDescription)) {
                        itemFound = true;
                        sItemTotalStock = itemStock;
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemData), "<b> " + itemDescription + " </b> is present in Item Master List");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.button_editItem, 15);
                        m_assert.assertInfo("Available stock of item : " + itemDescription + " --> <b>" + itemStock + "</b>");
                        itemCode = Cls_Generic_Methods.getTextInElement(oPage_Master.text_itemCode);


                        Cls_Generic_Methods.clickElement(oPage_Master.button_editItem);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.header_editItemHeader, 10);
                        taxPercentage = Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_itemPropertiesTaxList).split("-")[1].replaceAll("%", "").trim();
                        hsnCode = Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_itemHsnCode, "value");
                        category = Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_itemCategory);
                        dispensingUnit = Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_itemDispensingUnit);
                        packageType = Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_inventoryItemPackageType);
                        subPackageType = Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_inventoryItemSubPackageType);
                        unitType = Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_inventoryItemUnitType);
                        unitValue = Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_inventoryItemSubPackageItemUnit, "value");
                        subPackageValue = Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_inventoryItemSubPackageUnit, "value");

                        int rowNo = 0;
                        for (WebElement eVariantName : oPage_ItemMaster.list_input_itemPossibleVariantNameList) {
                            String variantValue = "";

                            String variantName = Cls_Generic_Methods.getElementAttribute(eVariantName, "value");

                            List<WebElement> eVariantValue = oPage_ItemMaster.list_selectItemPossibleVariantValue.get(rowNo).findElements(By.xpath("./option"));
                            for (WebElement variants : eVariantValue) {
                                String sValue = Cls_Generic_Methods.getTextInElement(variants);
                                if (variantValue.isEmpty()) {
                                    variantValue = sValue;
                                } else {
                                    variantValue = variantValue.concat("|").concat(sValue);
                                }
                            }
                            rowNo++;
                            itemDetails.put("VARIANT" + rowNo, variantName + "=" + variantValue);
                        }
                        Cls_Generic_Methods.clickElement(driver, oPage_ItemMaster.button_closeItemMasterTemplate);
                        break;
                    }
                }
            }


            if (itemFound) {
                itemDetails.put("ITEM CODE", itemCode);
                itemDetails.put("CATEGORY", category);
                itemDetails.put("DISPENSING UNIT", dispensingUnit);
                itemDetails.put("STOCK", sItemTotalStock);
                itemDetails.put("TAX", taxPercentage);
                itemDetails.put("PACKAGE TYPE", packageType);
                itemDetails.put("SUB PACKAGE TYPE", subPackageType);
                itemDetails.put("UNIT TYPE", unitType);
                itemDetails.put("SUB PACKAGE VALUE", subPackageValue);
                itemDetails.put("UNIT VALUE", unitValue);
                itemDetails.put("DESCRIPTION", itemDescription);
                itemDetails.put("HSN", hsnCode);

            } else {
                m_assert.assertFatal("Unable to find item ->" + itemDescription);
            }

        } catch (Exception e) {
            m_assert.assertFatal("Unable to get Item Details -" + itemDetails + "  -->" + e);
            e.printStackTrace();
        }

        return itemDetails;

    }
    private static void selectItemAndAddLot(HashMap<String, String> itemDetails, String discount) {
        Page_PurchaseOrder oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        String costPrice = getRandomDecimal(3);
        String unitQuantity = "";
        String subStoreName = "Default";
        String expiryDate = "";

        try {
            String itemDescription = itemDetails.get("DESCRIPTION");
            Cls_Generic_Methods.clearValuesInElement(oPage_PurchaseOrder.input_searchMedicineNamePO);
            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.input_searchMedicineNamePO);
            Cls_Generic_Methods.sendKeysByAction(oPage_PurchaseOrder.input_searchMedicineNamePO, itemDescription);
            Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.input_searchMedicineNamePO);
            Cls_Generic_Methods.customWait();
            oPage_PurchaseOrder.input_searchMedicineNamePO.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait();
            boolean bPO_Item_Found = false;

            int rowNo = 0;
            for (WebElement eMedicineName : oPage_PurchaseOrder.list_namesOfMedicinesOnLeftInSearchResultPO) {

                String sMedicineName = Cls_Generic_Methods.getTextInElement(eMedicineName);
                String itemCode = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.list_itemCodeOnLeftInSearchResultPO.get(rowNo));

                if (sMedicineName.equals(itemDescription) && itemCode.contains(itemDetails.get("ITEM CODE"))) {
                    bPO_Item_Found = true;
                    Cls_Generic_Methods.clickElement(eMedicineName);
                    break;
                }
                rowNo++;
            }

            if (bPO_Item_Found) {
                m_assert.assertTrue("Validate the PO item:<b> " + itemDescription + " </b> is found & selected");

                //Validate Lot Header
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.header_addNewLot, 15);





                //Select Possible Variants
                for (Map.Entry<String, String> entry : itemDetails.entrySet()) {
                    String key = entry.getKey();

                    if (key.contains("VARIANT")) {
                        String value = entry.getValue();
                        String variantName = value.split("=")[0];
                        String variantValue = value.split("=")[1];
                        String optionValue = "";

                        WebElement eSelectVariant = driver.findElement(By.xpath("//select[contains(@id,'" + variantName + "')]"));
                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(eSelectVariant), "Validated ->Displayed Possible Variant Name <b>" + variantName + "</b> for the item " + itemDescription);

                        if (Cls_Generic_Methods.isElementDisplayed(eSelectVariant)) {
                            for (WebElement eVariant : eSelectVariant.findElements(By.xpath("./option"))) {
                                String option = Cls_Generic_Methods.getTextInElement(eVariant);
                                if (optionValue.isEmpty()) {
                                    if (!option.equalsIgnoreCase("select")) {
                                        optionValue = option;
                                    }
                                } else {
                                    optionValue = optionValue.concat("|").concat(option);
                                }
                            }
                        }
                        m_assert.assertTrue(variantValue.equals(optionValue), "Validated Displayed variants for " + variantName + " : <b>" + optionValue.replaceAll("\\|", ",") + "</b>");
                        String optionToSelect = optionValue.split("\\|")[0];
                        m_assert.assertInfo(selectByOptions(eSelectVariant, optionToSelect), "Selected <b>" + optionToSelect + "</b> in variant " + variantName);
                    }
                }

                //Select Sub-Store
                if (Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseOrder.dropdown_subStore)) {
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_PurchaseOrder.dropdown_subStore, subStoreName),
                            "Sub Store selected: " + subStoreName);
                }
                if (Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseOrder.input_expiryDateCreatePO)) {
                    Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.input_expiryDateCreatePO);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.select_expiryDateYear, 1);
                    String currentYear = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseOrder.select_expiryDateYear);
                    int year = Integer.parseInt(currentYear);
                    int newYear = year + 3;

                    Cls_Generic_Methods.selectElementByVisibleText(oPage_PurchaseOrder.select_expiryDateYear, Integer.toString(newYear));
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_PurchaseOrder.select_expiryDateDay);
                    expiryDate = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_expiryDateCreatePO, "value");

                    m_assert.assertInfo("Selected Expiry Date for item " + itemDescription + " as <b>" + expiryDate + "</b>");
                }

                //Validate Unit Cost w/o tax
                Cls_Generic_Methods.clearValuesInElement(oPage_PurchaseOrder.input_unitCostWOTax);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_unitCostWOTax, String.valueOf(costPrice)),
                        "Entered Unit Cost price w/o Tax as : <b>" + costPrice + "</b>");




                //Package SubPackage Unit
                double dUnit = CommonActions.convertStringToDouble(itemDetails.get("UNIT VALUE"));
                double dSubPackageUnit = CommonActions.convertStringToDouble(itemDetails.get("SUB PACKAGE VALUE"));

                if (dUnit > 1) {
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_packageQuantity, String.valueOf(dUnit - 1));
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.label_unitError, 5), "Validated Error message is displayed if unit value is lesser than unit in Sub package");
                    Cls_Generic_Methods.clearValuesInElement(oPage_PurchaseOrder.input_packageQuantity);

                    Cls_Generic_Methods.customWait();

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_freeUnitQuantity, String.valueOf(dUnit - 1));
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.label_unitError, 5), "Validated Error message is displayed if Free Qty value is lesser than unit in Sub package");
                    Cls_Generic_Methods.clearValuesInElement(oPage_PurchaseOrder.input_freeUnitQuantity);
                }

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_subPackage, "1"), "Entered <b>1</b> in Sub Package");
                Cls_Generic_Methods.customWait();
                double totalPaidUnit = CommonActions.convertStringToDouble(Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_paidStock, "value"));
                m_assert.assertTrue(totalPaidUnit == dUnit, "Validated Displayed Paid qty ->Units in a Sub Package= " + totalPaidUnit);
                Cls_Generic_Methods.clearValuesInElement(oPage_PurchaseOrder.input_subPackage);

                double expectedUnitInPackage = dUnit * dSubPackageUnit;
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_package, "1"), "Entered <b>1</b> in Package");
                Cls_Generic_Methods.customWait();
                totalPaidUnit = CommonActions.convertStringToDouble(Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_paidStock, "value"));
                m_assert.assertTrue(totalPaidUnit == expectedUnitInPackage, "Validated Displayed Paid qty ->Units in a Package= " + totalPaidUnit);
                Cls_Generic_Methods.clearValuesInElement(oPage_PurchaseOrder.input_package);

                //Entering Quantity
                unitQuantity = String.valueOf(3);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_packageQuantity, unitQuantity), "Entered <b>" + unitQuantity + "</b> in Unit Quantity");
                double itemGross = CommonActions.convertStringToDouble(costPrice) * CommonActions.convertStringToDouble(unitQuantity);

                //Free Qty
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_freeUnitQuantity, itemDetails.get("UNIT VALUE")), "Entered <b>" + dUnit + "</b> in Free Quantity");

                //Applying Discount
                String discountInput = discount.split(" ")[0];
                String discountType = discount.split(" ")[1];

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_discountAmount, discountInput), "Entered <b>" + discountInput + "</b> in Discount input");
                m_assert.assertInfo(selectByOptions(oPage_PurchaseOrder.select_discountType, discountType), "Selected <b>" + discountType + "</b> as Discount Type");

                Cls_Generic_Methods.customWait();


                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_saveLot), "Clicked <b>Save</b> Lot");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.dropdown_otherCharge, 7);


            } else {
                m_assert.assertFatal("Unable to select Item : " + itemDescription);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean selectByOptions(WebElement selectElement, String optionToSelect) {
        boolean status = false;
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(selectElement, 10);
            Cls_Generic_Methods.clickElementByJS(driver, selectElement);
            List<WebElement> allOptions = selectElement.findElements(By.xpath("./option"));
            for (WebElement option : allOptions) {
                String optionValue = Cls_Generic_Methods.getTextInElement(option);
                if (optionValue.contains((optionToSelect))) {
                    Cls_Generic_Methods.clickElement(option);
                    status = true;
                    break;
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to select " + optionToSelect + " -->" + e);
        }
        return status;
    }

    public static boolean selectOptionFromBillsList(List<WebElement> listOfBillsElement, String nameOfBillToSelect) {

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
    private static void getGST_valueFromSetting(String store) {

        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        Page_VendorMaster oPage_VendorMaster = new Page_VendorMaster(driver);
        try {
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 3);

            int storeNo = 0;
            for (WebElement txtStoreName : oPage_StoreSetUp.list_text_storeName) {
                String storeName = Cls_Generic_Methods.getTextInElement(txtStoreName);
                if (storeName.contains(store.split("-")[0])) {
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.list_btn_editStore.get(storeNo));
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_StoreSetUp.list_btn_editStoreBillingAddress, 10);

                    for (WebElement btn_Edit : oPage_StoreSetUp.list_btn_editStoreBillingAddress) {
                        boolean defaultAddress = false;

                        try {
                            Cls_Generic_Methods.isElementDisplayed(btn_Edit.findElement(By.xpath("./parent::td/following-sibling::td/a[text()='Mark Default']")));
                        } catch (NoSuchElementException e) {
                            defaultAddress = true;
                        }

                        if (defaultAddress) {
                            Cls_Generic_Methods.clickElement(driver, btn_Edit);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.input_gstNo, 10);
                            storeGSTno = Cls_Generic_Methods.getElementAttribute(oPage_StoreSetUp.input_gstNo, "value");
                            m_assert.assertInfo("GST no. present in " + storeName + " is <b>" + storeGSTno + "</b>");
                            Cls_Generic_Methods.clickElement(oPage_StoreSetUp.button_close);
                        }
                    }
                    break;
                }
                storeNo++;
            }
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();

            if (storeGSTno != null) {
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.header_vendorMasterTitle, 10);

                int vendorNo = 0;
                for (WebElement txtStoreName : oPage_VendorMaster.list_text_vendorName) {

                    String vendorNameInList = Cls_Generic_Methods.getTextInElement(txtStoreName);
                    if (vendorNameInList.equalsIgnoreCase(vendorName)) {
                        Cls_Generic_Methods.clickElement(driver, oPage_VendorMaster.list_btn_vendorLocation.get(vendorNo));

                        Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_VendorMaster.list_btn_vendorLocationHeader, 10);

                        int headerColumn = 0;
                        for (WebElement locationHeader : oPage_VendorMaster.list_btn_vendorLocationHeader) {

                            String headerValue = Cls_Generic_Methods.getTextInElement(locationHeader);
                            if (headerValue.equalsIgnoreCase("Address")) {
                                WebElement txt_address = driver.findElement(By.xpath("//td[contains(text(),'" + vendorNameInList + "')]/parent::tr")).findElements(By.xpath("./td")).get(headerColumn);
                                vendor_address = Cls_Generic_Methods.getTextInElement(txt_address);
                            }
                            if (headerValue.equalsIgnoreCase("Actions")) {
                                WebElement btn_edit = driver.findElement(By.xpath("//td[contains(text(),'" + vendorNameInList + "')]/parent::tr")).findElement(By.xpath("./td//a[text()='Edit']"));
                                Cls_Generic_Methods.clickElement(driver, btn_edit);

                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.input_gstNo, 10);
                                vendorGSTno = Cls_Generic_Methods.getElementAttribute(oPage_VendorMaster.input_gstNo, "value");
                                m_assert.assertInfo("GST no. present in " + vendorName + " is <b>" + vendorGSTno + "</b>");
                            }
                            headerColumn++;
                        }
                    }
                    vendorNo++;
                }
            }
            Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_close);

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to get GST no. from Organisation Setting " + e);
        }
    }
    public static boolean createPurchaseBill() {
        boolean flag = false;

        vendorName = "Supplier ABC";
        String billType = "Bill";
        billNumber = "BILL_NO_" + getRandomNumber();
        String subStore = "Default";
        String unitCostWOTax = "100";
        String packageQuantity = "2";
        final String sellingPrice = "120";
        Page_PurchaseBill oPage_PurchaseBill = new Page_PurchaseBill(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

        boolean bPurchaseTransactionFound = false;
        try {
            // Creating Purchase Transaction for Created Item
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 10);
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_purchaseNew);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_Vendor_search, 4);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_Vendor_search, vendorName);
            Cls_Generic_Methods.customWait();
            for (WebElement eVendor : oPage_Purchase.list_select_vendor) {
                Cls_Generic_Methods.clickElementByJS(driver, eVendor);
                break;
            }
            boolean itemClicked = false;
            try {
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_itemNameInPurchaseStore, 10);
                for (WebElement eItemName : oPage_Purchase.list_itemNameInPurchaseStore) {
                    Cls_Generic_Methods.clickElementByJS(driver, eItemName);
                    itemClicked = true;
                    break;
                }
                if (itemClicked) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewLot, 15);
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.select_subStore)) {
                        Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_subStore, subStore);
                    }
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.input_expiryDate)) {
                        Cls_Generic_Methods.clickElement(oPage_Purchase.input_expiryDate);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.select_expiryDateYear, 1);
                        String currentYear = Cls_Generic_Methods.getSelectedValue(oPage_Purchase.select_expiryDateYear);
                        int year = Integer.parseInt(currentYear);
                        int newYear = year + 3;

                        Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_expiryDateYear, Integer.toString(newYear));
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.select_expiryDateDay);
                    }

                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_unitCostWOTax);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_unitCostWOTax, unitCostWOTax);

                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_packageQuantity);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_packageQuantity, packageQuantity);

                    Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_sellingPrice);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_sellingPrice, sellingPrice);

                    Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveLot);

                } else {
                    m_assert.assertFatal("Item not selected");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_SaveChanges);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }

            Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes);
            Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
            Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType);
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber), "Entered Bill no as <b>" + billNumber + "</b> in Purchase Grn");
            Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
            Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate);
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_deleteOtherCharges);
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot);
            Cls_Generic_Methods.customWait(8);
            List<String> purchaseTransactionHeaderList = new ArrayList<String>();
            for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
                purchaseTransactionHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
            }
            for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));
                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Status")));
                    if (purchaseStatus.equalsIgnoreCase("open")) {
                        Cls_Generic_Methods.clickElement(row);
                        Cls_Generic_Methods.customWait(2);
                        Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_Yes, 6);
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.button_Yes);
                        bPurchaseTransactionFound = true;
                        m_assert.assertInfo("<b>Purchase Grn</b> created and approved");
                        break;
                    }
                }
            }
            if (bPurchaseTransactionFound) {
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Bills");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.button_purchaseNew, 20);
                Cls_Generic_Methods.clickElement(oPage_PurchaseBill.button_purchaseNew);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.select_vendorOptionsCreatePurchaseBill, 6);
                m_assert.assertInfo(selectByOptions(oPage_PurchaseBill.select_vendorOptionsCreatePurchaseBill, vendorName), "Selected Vendor as <b>" + vendorName + "</b> in Purchase Bill");
                Cls_Generic_Methods.selectElementByIndex(oPage_PurchaseBill.select_createAgainstPurchaseBill, 1);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(oPage_PurchaseBill.list_RowsOnCreatePurchaseBillTable.get(0));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.button_removeFromList, 10);
                netAmountInPurchaseBill = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseBill.text_netAmountValue, "value");
                m_assert.assertInfo("Net Amount in Purchase Bill : <b>" + netAmountInPurchaseBill + "</b>");
                Cls_Generic_Methods.clickElement(oPage_PurchaseBill.button_saveChanges);
                flag = true;
                Cls_Generic_Methods.customWait(7);


            } else {
                m_assert.assertFatal("Purchase Grn not created");
            }

            if (flag) {
                List<String> purchaseBillHeaderList = new ArrayList<String>();

                for (WebElement purchaseHeaderList : oPage_PurchaseBill.list_purchaseBillHeaderList) {
                    purchaseBillHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
                }
                for (WebElement row : oPage_PurchaseBill.list_purchaseBillRowList) {
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));
                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseBillHeaderList.indexOf("Status")));
                    String invoiceNoInRow = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseBillHeaderList.indexOf("Invoice No.")));


                    if (purchaseStatus.equalsIgnoreCase("open") && invoiceNoInRow.equals(billNumber)) {
                        Cls_Generic_Methods.clickElementByJS(driver, row);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseBill.button_approve, 10);
                        purchaseBill_no = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_purchaseBillNo);
                        invoiceDate = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_InvoiceDate);
                        purchaseBillCreatedAt = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_purchaseBillCreatedAt).split("\\|")[0].trim();
                        purchaseBillCreatedBy = Cls_Generic_Methods.getTextInElement(oPage_PurchaseBill.text_rhs_purchaseBillCreatedByUser);
                        Cls_Generic_Methods.clickElement(oPage_PurchaseBill.button_approve);
                        flag = true;
                        m_assert.assertInfo("<b>Purchase Bill</b> created and approved");
                        m_assert.assertInfo("Purchase Bill No : <b>" + purchaseBill_no + "</b>");
                        break;
                    } else {
                        flag = false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to create purchase Bill --> " + e);
        }
        return flag;
    }
    private static String getRandomNumber() {
        Random random = new Random();
        String id = String.format("%06d", random.nextInt(1000000));
        return id;
    }
    public boolean selectPaymentRequisition(String status, String... prfNo) {
        boolean flag = false;
        String selectPrfRow = null;


        try {
            if (prfNo.length > 0) {
                selectPrfRow = prfNo[0];
            }

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PaymentRequisitionForm.list_prfHeaderList, 10);
            List<String> purchaseTransactionHeaderList = new ArrayList<String>();
            for (WebElement purchaseHeaderList : oPage_PaymentRequisitionForm.list_prfHeaderList) {
                purchaseTransactionHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
            }
            for (WebElement row : oPage_PaymentRequisitionForm.list_prfCreatedList) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Status")));
                    String prfNoInRow = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("PRF No.")));

                    if (prfNo.length == 0) {
                        if (purchaseStatus.equalsIgnoreCase(status)) {
                            Cls_Generic_Methods.clickElement(row);
                            flag = true;
                            break;
                        }
                    } else {
                        if (prfNoInRow.equals(selectPrfRow) && purchaseStatus.equalsIgnoreCase(status)) {
                            Cls_Generic_Methods.clickElement(row);
                            flag = true;
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to find payment requisition form " + e);
        }
        return flag;
    }
    private static void getStoreAndVendorDetails(String sStoreName) {
        Page_StoreSetUp oPage_StoreSetUp = new Page_StoreSetUp(driver);
        Page_VendorMaster oPage_VendorMaster = new Page_VendorMaster(driver);
        Page_TermsAndConditions oPage_TermsAndConditions = new Page_TermsAndConditions(driver);
        storeDefaultShippingAddress = "";
        storeDefaultBillingAddress = "";
        storeGSTno = "";
        vendor_address = "";
        vendorGSTno = "";
        vendorCreditDays = "";
        storeEntityGroup = "";
        vendorPOExpiry = "";



        try {
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 3);

            int storeNo = 0;
            for (WebElement txtStoreName : oPage_StoreSetUp.list_text_storeName) {
                String storeName = Cls_Generic_Methods.getTextInElement(txtStoreName);
                if (storeName.contains(sStoreName.split("-")[0])) {
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.list_btn_editStore.get(storeNo));
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_StoreSetUp.list_btn_editStoreBillingAddress, 10);

                    for (WebElement btn_Edit : oPage_StoreSetUp.list_btn_editStoreBillingAddress) {
                        boolean defaultAddress = false;

                        try {
                            Cls_Generic_Methods.isElementDisplayed(btn_Edit.findElement(By.xpath("./parent::td/following-sibling::td/a[text()='Mark Default']")));
                        } catch (NoSuchElementException e) {
                            defaultAddress = true;
                        }

                        if (defaultAddress) {
                            Cls_Generic_Methods.clickElement(driver, btn_Edit);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.input_gstNo, 10);
                            storeGSTno = Cls_Generic_Methods.getElementAttribute(oPage_StoreSetUp.input_gstNo, "value");
                            storeDefaultBillingAddress = Cls_Generic_Methods.getElementAttribute(oPage_StoreSetUp.input_billingAddress, "value");
                            m_assert.assertInfo("Default Billing Address for " + storeName + " is <b>" + storeDefaultBillingAddress + "</b>");
                            m_assert.assertInfo("GST no. present in " + storeName + " is <b>" + storeGSTno + "</b>");
                            storeEntityGroup = Cls_Generic_Methods.getSelectedValue(oPage_StoreSetUp.select_entityGroup);
                        }
                    }

                    for (WebElement btn_Edit : oPage_StoreSetUp.list_btn_editStoreShippingAddress) {
                        boolean defaultAddress = false;

                        try {
                            Cls_Generic_Methods.isElementDisplayed(btn_Edit.findElement(By.xpath("./parent::td/following-sibling::td/a[text()='Mark Default']")));
                        } catch (NoSuchElementException e) {
                            defaultAddress = true;
                        }

                        if (defaultAddress) {
                            Cls_Generic_Methods.clickElement(driver, btn_Edit);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.input_billingAddress, 10);
                            storeDefaultShippingAddress = Cls_Generic_Methods.getElementAttribute(oPage_StoreSetUp.input_shippingAddress, "value");
                            m_assert.assertInfo("Default Shipping Address for " + storeName + " is <b>" + storeDefaultShippingAddress + "</b>");
                            Cls_Generic_Methods.clickElement(oPage_StoreSetUp.button_close);
                        }
                    }


                    break;
                }
                storeNo++;
            }
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();

            if (storeGSTno != null) {
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.header_vendorMasterTitle, 10);

                int vendorNo = 0;
                for (WebElement txtStoreName : oPage_VendorMaster.list_text_vendorName) {

                    String vendorNameInList = Cls_Generic_Methods.getTextInElement(txtStoreName);
                    if (vendorNameInList.equalsIgnoreCase(vendorName)) {
                        Cls_Generic_Methods.clickElement(driver, oPage_VendorMaster.list_btn_vendorLocation.get(vendorNo));

                        Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_VendorMaster.list_btn_vendorLocationHeader, 10);

                        int headerColumn = 0;
                        for (WebElement locationHeader : oPage_VendorMaster.list_btn_vendorLocationHeader) {

                            String headerValue = Cls_Generic_Methods.getTextInElement(locationHeader);
                            if (headerValue.equalsIgnoreCase("Address")) {
                                WebElement txt_address = driver.findElement(By.xpath("//td[contains(text(),'" + vendorNameInList + "')]/parent::tr")).findElements(By.xpath("./td")).get(headerColumn);
                                vendor_address = Cls_Generic_Methods.getTextInElement(txt_address);
                            }
                            if (headerValue.equalsIgnoreCase("Actions")) {
                                WebElement btn_edit = driver.findElement(By.xpath("//td[contains(text(),'" + vendorNameInList + "')]/parent::tr")).findElement(By.xpath("./td//a[text()='Edit']"));
                                Cls_Generic_Methods.clickElement(driver, btn_edit);

                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.input_gstNo, 10);
                                vendorGSTno = Cls_Generic_Methods.getElementAttribute(oPage_VendorMaster.input_gstNo, "value");
                                vendorCreditDays = Cls_Generic_Methods.getElementAttribute(oPage_VendorMaster.input_inventoryVendorCreditDays, "value");
                                vendorPOExpiry = Cls_Generic_Methods.getElementAttribute(oPage_VendorMaster.input_expiryPOAfterDays, "value");

                                m_assert.assertInfo("Vendor Credit days present in " + vendorName + " is <b>" + vendorCreditDays + "</b>");
                                m_assert.assertInfo("Vendor GST No present in " + vendorName + " is <b>" + vendorGSTno + "</b>");
                            }
                            headerColumn++;
                        }
                    }
                    vendorNo++;
                }
            }
            Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_close);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Terms & Conditions");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TermsAndConditions.button_addTermsAndConditions, 10);

            for (WebElement eDeliveryTerms : oPage_TermsAndConditions.text_descriptionsDeliveryTerms) {
                String description = Cls_Generic_Methods.getTextInElement(eDeliveryTerms);
                boolean activeStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(driver.findElement(By.xpath("//div[@data-sub-category='" + description + "']")), 2);
                if (activeStatus) {
                    if (deliveryTerms.isEmpty()) {
                        deliveryTerms = description;
                    } else {
                        deliveryTerms = deliveryTerms.concat("|").concat(description);
                    }
                }
            }
            for (WebElement eDeliveryTerms : oPage_TermsAndConditions.text_descriptionsPaymentTerms) {
                String description = Cls_Generic_Methods.getTextInElement(eDeliveryTerms);
                boolean activeStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(driver.findElement(By.xpath("//div[@data-sub-category='" + description + "']")), 2);
                if (activeStatus) {
                    if (paymentTerms.isEmpty()) {
                        paymentTerms = description;
                    } else {
                        paymentTerms = paymentTerms.concat("|").concat(description);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to get Store and Vendor Details from Organisation Setting " + e);
        }
    }
    public static String getRandomDecimal(int numDigits) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        Random random = new Random();
        double randomValue = random.nextDouble();
        double scaledValue = randomValue * Math.pow(10, numDigits);
        return decimalFormat.format(scaledValue);
    }







}
